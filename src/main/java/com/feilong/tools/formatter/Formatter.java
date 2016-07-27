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
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.tools.formatter.entity.BeanFormatterConfig;
import com.feilong.tools.formatter.table.SimpleTableFormatter;

/**
 * 格式化.
 * 
 * <h3>初衷:</h3>
 * 
 * <blockquote>
 * <p>
 * 在做开发的时候,我们经常会记录一些日志,使用log,但是对于 list map ,bean的日志输出一直很难做得很好,为了格式化输出,我们可能会使用 json来输出,比如:
 * </p>
 * 
 * <pre class="code">
 * List{@code <Address>} list = toList(
 *                 new Address("china", "shanghai", "wenshui wanrong.lu 888", "216000"),
 *                 new Address("china", "beijing", "wenshui wanrong.lu 666", "216001"),
 *                 new Address("china", "nantong", "wenshui wanrong.lu 222", "216002"),
 *                 new Address("china", "tianjing", "wenshui wanrong.lu 999", "216600"));
 * 
 * LOGGER.debug(JsonUtil.format(list));
 * </pre>
 * 
 * 结果:
 * 
 * <pre class="code">
[{
            "zipCode": "wenshui wanrong.lu 888",
            "addr": "216000",
            "country": "china",
            "city": "shanghai"
        },
                {
            "zipCode": "wenshui wanrong.lu 666",
            "addr": "216001",
            "country": "china",
            "city": "beijing"
        },
                {
            "zipCode": "wenshui wanrong.lu 222",
            "addr": "216002",
            "country": "china",
            "city": "nantong"
        },
                {
            "zipCode": "wenshui wanrong.lu 999",
            "addr": "216600",
            "country": "china",
            "city": "tianjing"
}]
 * </pre>
 * 
 * <p>
 * 可以看出,结果难以阅读,如果list元素更多一些,那么更加难以阅读;
 * </p>
 * 
 * </blockquote>
 * 
 * 
 * <h3>解决方案:</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * 这个时候可以使用
 * </p>
 * 
 * <pre class="code">
 * List{@code <Address>} list = toList(
 *                 new Address("china", "shanghai", "wenshui wanrong.lu 888", "216000"),
 *                 new Address("china", "beijing", "wenshui wanrong.lu 666", "216001"),
 *                 new Address("china", "nantong", "wenshui wanrong.lu 222", "216002"),
 *                 new Address("china", "tianjing", "wenshui wanrong.lu 999", "216600"));
 * 
 * LOGGER.debug(formatToSimpleTable(list));
 * </pre>
 * 
 * 
 * 结果:
 * 
 * <pre class="code">
addr   city     country zipCode                
------ -------- ------- ---------------------- 
216000 shanghai china   wenshui wanrong.lu 888 
216001 beijing  china   wenshui wanrong.lu 666 
216002 nantong  china   wenshui wanrong.lu 222 
216600 tianjing china   wenshui wanrong.lu 999
 * </pre>
 * 
 * <p>
 * 可以看出,输出的结果会更加友好
 * </p>
 * 
 * </blockquote>
 * 
 * <p>
 * 该类提供通用格式化的接口,实现可以有 {@link SimpleTableFormatter}
 * </p>
 * 
 * <p>
 * 为了方便使用,可以直接使用 {@link FormatterUtil} 工具类
 * </p>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.2
 */
public interface Formatter{

    /**
     * 对java <code>bean</code>格式化.
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            the bean
     * @return 如果 <code>bean</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     */
    <T> String format(T bean);

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
    <K, V> String format(Map<K, V> map);

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
    <T> String format(Iterable<T> iterable);

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
