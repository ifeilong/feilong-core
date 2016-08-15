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
 * AbstractFormatter.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.5
 */
abstract class AbstractFormatter{

    /**
     * 对java <code>bean</code>格式化.
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            the bean
     * @return 如果 <code>bean</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     */
    public <T> String format(T bean){
        return isNullOrEmpty(bean) ? EMPTY : format(PropertyUtil.describe(bean));
    }

    /**
     * 将<code>map</code> 格式化成字符串.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     */
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

    //**********************Iterable***************************************************************

    /**
     * 将迭代对象 <code>iterable</code> 格式化.
     * 
     * @param <T>
     *            the generic type
     * @param iterable
     *            the iterable
     * @return 如果 <code>iterable</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object)
     */
    public <T> String format(Iterable<T> iterable){
        return format(iterable, null);
    }

    /**
     * 将迭代对象 <code>iterable</code> 格式化.
     *
     * @param <T>
     *            the generic type
     * @param iterable
     *            the iterable
     * @param beanFormatterConfig
     *            the bean formatter config
     * @return 如果 <code>iterable</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object)
     */
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
