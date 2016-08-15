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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.reflect.FieldUtil;
import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.comparator.BeanComparatorUtil;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.Validator.isNotNullOrEmpty;
import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.util.SortUtil.sort;

/**
 * {@link FormatterColumnEntity} 提取器.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.5
 */
class FormatterColumnEntityExtractor{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FormatterColumnEntityExtractor.class);

    /** Don't let anyone instantiate this class. */
    private FormatterColumnEntityExtractor(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //********************************************************************************************************

    /**
     * 从 <code>BeanFormatterConfig</code> 来提取 FormatterColumnEntity list.
     *
     * @param <T>
     *            the generic type
     * @param beanFormatterConfig
     *            the bean formatter config
     * @return the formatter column entity list
     * @see org.apache.commons.lang3.reflect.FieldUtils#getAllFieldsList(Class)
     */
    public static <T> List<FormatterColumnEntity> getFormatterColumnEntityList(BeanFormatterConfig<T> beanFormatterConfig){
        Validate.notNull(beanFormatterConfig, "beanFormatterConfig can't be null!");

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
     *            the bean formatter config
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

    //*********************************************************************************************

    /**
     * 获得 order.
     *
     * @param formatterColumn
     *            the formatter column
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
     *            the formatter column
     * @return the name
     */
    private static String getName(Field field,FormatterColumn formatterColumn){
        if (null != formatterColumn && isNotNullOrEmpty(formatterColumn.name())){
            return formatterColumn.name().trim();
        }
        return field.getName();
    }
}
