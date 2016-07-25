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

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.feilong.core.bean.ConvertUtil;

/**
 * The Interface Formatter.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.2
 */
public interface Formatter{

    /**
     * 将迭代对象格式化.
     * 
     * @param <T>
     *            the generic type
     * @param iterable
     *            the iterable
     * @return 如果 <code>iterable</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     * @see org.apache.commons.beanutils.ConvertUtils#convert(Object)
     */
    <T> String format(Iterable<T> iterable);

    /**
     * 将迭代对象格式化.
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
    <T> String format(Iterable<T> iterable,BeanFormatterConfig<T> beanFormatterConfig);

    //**********************array***************************************************************

    /**
     * 格式化.
     *
     * @param columnTitles
     *            列标题, columnTitles和dataList 不能同时为null或者empty
     * @param dataList
     *            数据数组list, columnTitles和dataList 不能同时为null或者empty;<br>
     *            object对象会调用 {@link ConvertUtil#toString(Object)} 转成字符串输出
     * @return the string
     */
    String format(String[] columnTitles,List<Object[]> dataList);
}
