/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.tools.formatter;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.lang.reflect.FieldUtil;
import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.comparator.BeanComparatorUtil;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.Validator.isNotNullOrEmpty;
import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.util.SortUtil.sort;

/**
 * The Class PropertyValueMapExtractor.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.2
 */
class PropertyValueMapExtractor{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyValueMapExtractor.class);

    /** Don't let anyone instantiate this class. */
    private PropertyValueMapExtractor(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //********************************************************************************************************

    /**
     * Gets the formatter column entity list.
     *
     * @param <T>
     *            the generic type
     * @param beanFormatterConfig
     *            the bean csv config
     * @return the formatter column entity list
     * @see com.feilong.core.lang.reflect.FieldUtil#getAllFieldList(Object, String[])
     * @see org.apache.commons.lang3.reflect.FieldUtils#getAllFieldsList(Class)
     */
    static <T> List<FormatterColumnEntity> getFormatterColumnEntityList(BeanFormatterConfig<T> beanFormatterConfig){
        Validate.notNull(beanFormatterConfig, "beanCsvConfig can't be null!");

        List<FormatterColumnEntity> formatterColumnEntityList = buildFormatterColumnEntityList(beanFormatterConfig);
        if (isNotNullOrEmpty(beanFormatterConfig.getIncludePropertyNames())){
            formatterColumnEntityList = CollectionsUtil
                            .select(formatterColumnEntityList, "name", beanFormatterConfig.getIncludePropertyNames());
        }

        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("before sort:{}", JsonUtil.format(formatterColumnEntityList));
        }

        //**********************************************************************
        List<FormatterColumnEntity> result = sortFormatterColumnEntityList(formatterColumnEntityList, beanFormatterConfig);

        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("after sort:{}", JsonUtil.format(result));
        }
        return result;
    }

    /**
     * Sort formatter column entity list.
     *
     * @param <T>
     *            the generic type
     * @param formatterColumnEntityList
     *            the formatter column entity list
     * @param beanFormatterConfig
     *            the bean formatter config
     * @return the list
     */
    private static <T> List<FormatterColumnEntity> sortFormatterColumnEntityList(
                    List<FormatterColumnEntity> formatterColumnEntityList,
                    BeanFormatterConfig<T> beanFormatterConfig){
        String[] sorts = beanFormatterConfig.getSorts();
        Comparator<FormatterColumnEntity> chainedComparator = BeanComparatorUtil.chainedComparator("order", "propertyName");

        if (isNullOrEmpty(sorts)){
            return sort(formatterColumnEntityList, chainedComparator);
        }

        Comparator<FormatterColumnEntity> propertyComparator = BeanComparatorUtil.propertyComparator("propertyName", sorts);
        return sort(formatterColumnEntityList, propertyComparator, chainedComparator);
    }

    /**
     * Builds the formatter column entity list.
     *
     * @param <T>
     *            the generic type
     * @param beanFormatterConfig
     *            the bean csv config
     * @return the list
     */
    private static <T> List<FormatterColumnEntity> buildFormatterColumnEntityList(BeanFormatterConfig<T> beanFormatterConfig){
        List<Field> fieldsList = FieldUtil
                        .getAllFieldList(beanFormatterConfig.getBeanClass(), beanFormatterConfig.getExcludePropertyNames());

        List<FormatterColumnEntity> list = new ArrayList<>();
        for (Field field : fieldsList){
            list.add(buildFormatterColumnEntity(field));
        }
        return list;
    }

    /**
     * Builds the formatter column entity.
     *
     * @param field
     *            the field
     * @return the formatter column entity
     */
    private static FormatterColumnEntity buildFormatterColumnEntity(Field field){
        FormatterColumn formatterColumn = field.getAnnotation(FormatterColumn.class);
        return new FormatterColumnEntity(getName(field, formatterColumn), field.getName(), getOrder(formatterColumn));
    }

    /**
     * Builds the data list.
     *
     * @param <T>
     *            the generic type
     * @param iterable
     *            the iterable
     * @param formatterColumnEntityList
     *            the csv column entity list
     * @return the list
     */
    static <T> List<Object[]> buildDataList(Iterable<T> iterable,List<FormatterColumnEntity> formatterColumnEntityList){
        List<String> propertyNameList = CollectionsUtil.getPropertyValueList(formatterColumnEntityList, "propertyName");
        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("propertyNameList:{}", JsonUtil.format(propertyNameList, 0, 0));
        }

        List<Object[]> dataList = new ArrayList<>();
        for (T bean : iterable){
            Object[] objectArray = toObjectArray(bean, propertyNameList);

            if (isNotNullOrEmpty(objectArray)){
                dataList.add(objectArray);
            }
        }
        return dataList;
    }

    /**
     * To object array.
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            the t
     * @param propertyNameList
     *            the property name list
     * @return the object[]
     */
    private static <T> Object[] toObjectArray(T bean,List<String> propertyNameList){
        Map<String, Object> propertyValueMap = new LinkedHashMap<>();
        PropertyUtil.copyProperties(propertyValueMap, bean, ConvertUtil.toStrings(propertyNameList));

        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("propertyValueMap:{}", JsonUtil.format(propertyValueMap, 0, 0));
        }

        int j = 0;
        Object[] rowData = new Object[propertyNameList.size()];
        for (Map.Entry<String, Object> entry : propertyValueMap.entrySet()){
            Object value = entry.getValue();
            rowData[j] = isNullOrEmpty(value) ? EMPTY : ConvertUtil.toString(value);
            j++;
        }
        return rowData;
    }

    //*********************************************************************************************

    /**
     * 获得 order.
     *
     * @param formatterColumn
     *            the csv column
     * @return the order
     */
    private static int getOrder(FormatterColumn formatterColumn){
        return null != formatterColumn ? formatterColumn.order() : 0;
    }

    /**
     * 获得 name.
     *
     * @param field
     *            the field
     * @param formatterColumn
     *            the csv column
     * @return the name
     */
    private static String getName(Field field,FormatterColumn formatterColumn){
        if (null != formatterColumn && isNotNullOrEmpty(formatterColumn.name())){
            return formatterColumn.name().trim();
        }
        return field.getName();
    }
}
