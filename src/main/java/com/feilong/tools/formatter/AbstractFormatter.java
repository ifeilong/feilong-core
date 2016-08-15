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

import static java.lang.Math.max;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.util.CollectionsUtil;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.util.SortUtil.sortByKeyAsc;

/**
 * Formatter 的base 实现.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.2
 */
public abstract class AbstractFormatter implements Formatter{

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.tools.formatter.Formatter#format(java.lang.Object)
     */
    @Override
    public <T> String format(T bean){
        return isNullOrEmpty(bean) ? EMPTY : format(PropertyUtil.describe(bean));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.tools.formatter.Formatter#format(java.util.Map)
     */
    @Override
    public <K, V> String format(Map<K, V> map){
        if (isNullOrEmpty(map)){
            return EMPTY;
        }

        //*******************************************************
        int maxKeyLength = -1;
        for (K key : map.keySet()){
            maxKeyLength = max(maxKeyLength, StringUtils.length(ConvertUtil.toString(key)));
        }
        //*******************************************************

        List<Object[]> dataList = new ArrayList<>(map.size());

        Map<K, V> useMap = sortByKeyAsc(map);//不影响原map

        String separator = SPACE + ":" + SPACE;

        //*******************************************************
        for (Map.Entry<K, V> entry : useMap.entrySet()){
            K key = entry.getKey();
            V value = entry.getValue();
            //StringUtils.leftPad(ConvertUtil.toString(key), maxKeyLength)
            dataList.add(toArray(ConvertUtil.toString(key), separator, value));
        }
        return format(null, dataList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.coreextension.formatter.Formatter#format(java.lang.Iterable)
     */
    @Override
    public <T> String format(Iterable<T> iterable){
        return format(iterable, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.feilong.coreextension.formatter.Formatter#format(java.lang.Iterable,
     * com.feilong.coreextension.formatter.BeanFormatterConfig)
     */
    @Override
    public <T> String format(Iterable<T> iterable,BeanFormatterConfig<T> beanFormatterConfig){
        if (isNullOrEmpty(iterable)){
            return EMPTY;
        }
        BeanFormatterConfig<T> useBeanFormatterConfig = null == beanFormatterConfig ? buildBeanFormatterConfig(iterable)
                        : beanFormatterConfig;
        Validate.notNull(useBeanFormatterConfig.getBeanClass(), "useBeanFormatterConfig.getBeanClass() can't be null!");

        List<FormatterColumnEntity> formatterColumnEntityList = FormatterColumnEntityExtractor
                        .getFormatterColumnEntityList(useBeanFormatterConfig);

        String[] columnTitles = ConvertUtil.toStrings(CollectionsUtil.getPropertyValueList(formatterColumnEntityList, "name"));
        List<Object[]> dataList = DataListBuilder.buildDataList(iterable, formatterColumnEntityList);
        return format(columnTitles, dataList);
    }

    //**********************array****************************************

    /**
     * 格式化.
     *
     * @param columnTitles
     *            列标题, columnTitles和dataList 不能同时为null或者empty
     * @param dataList
     *            数据数组list, columnTitles和dataList 不能同时为null或者empty;<br>
     *            object对象会调用 {@link ConvertUtil#toString(Object)} 转成字符串输出
     * @return the string
     * @since 1.8.3
     */
    protected abstract String format(String[] columnTitles,List<Object[]> dataList);

    /**
     * Builds the bean formatter config.
     *
     * @param <T>
     *            the generic type
     * @param iterable
     *            the iterable
     * @return the bean formatter config
     */
    private static <T> BeanFormatterConfig<T> buildBeanFormatterConfig(Iterable<T> iterable){
        T t = IterableUtils.get(iterable, 0);
        Class<T> klass = (Class<T>) t.getClass();
        return new BeanFormatterConfig<T>(klass);
    }
}
