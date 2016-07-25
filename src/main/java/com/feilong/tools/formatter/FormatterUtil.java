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

import com.feilong.tools.formatter.table.SimpleTableFormatter;

/**
 * 处理将对象格式化的工具类.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.2
 */
public final class FormatterUtil{

    /** The Constant SIMPLE_TABLE_FORMATTER. */
    private static final Formatter SIMPLE_TABLE_FORMATTER = new SimpleTableFormatter();

    /** Don't let anyone instantiate this class. */
    private FormatterUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Format to simple table.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * User id12_age18 = new User(12L, 18);
     * User id1_age8 = new User(1L, 8);
     * User id2_age30 = new User(2L, 30);
     * User id2_age2 = new User(2L, 2);
     * User id2_age36 = new User(2L, 36);
     * List{@code <User>} list = toList(id12_age18, id2_age36, id2_age2, id2_age30, id1_age8);
     * 
     * LOGGER.debug(formatToSimpleTable(list));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    age attrMap date id loves money
    --- ------- ---- -- ----- -----
    18               12            
    36               2             
    2                2             
    30               2             
    8                1
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param iterable
     *            the iterable
     * @return the string
     */
    public static final <T> String formatToSimpleTable(Iterable<T> iterable){
        return SIMPLE_TABLE_FORMATTER.format(iterable);
    }

    /**
     * Format to simple table.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * User id12_age18 = new User(12L, 18);
     * User id1_age8 = new User(1L, 8);
     * User id2_age30 = new User(2L, 30);
     * User id2_age2 = new User(2L, 2);
     * User id2_age36 = new User(2L, 36);
     * List{@code <User>} list = toList(id12_age18, id2_age36, id2_age2, id2_age30, id1_age8);
     * 
     * BeanFormatterConfig{@code <User>} beanFormatterConfig = new BeanFormatterConfig{@code <>}(User.class);
     * beanFormatterConfig.setIncludePropertyNames("id", "age");
     * beanFormatterConfig.setSorts("id", "age");
     * LOGGER.debug(formatToSimpleTable(list, beanFormatterConfig));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    id age 
    -- --- 
    12 18  
    2  36  
    2  2   
    2  30  
    1  8
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param iterable
     *            the iterable
     * @param beanFormatterConfig
     *            the bean formatter config
     * @return the string
     */
    public static final <T> String formatToSimpleTable(Iterable<T> iterable,BeanFormatterConfig<T> beanFormatterConfig){
        return SIMPLE_TABLE_FORMATTER.format(iterable, beanFormatterConfig);
    }

    /**
     * 格式化成简单的table格式.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * String[] columnTitles = { "name", "age", "address" };
     * 
     * List<Object[]> list = new ArrayList<>();
     * for (int i = 0; i < 5; i++){
     *     list.add(ConvertUtil.toArray("feilong" + i, 18 + i, "shanghai"));
     * }
     * 
     * LOGGER.debug(FormatterUtil.formatToSimpleTable(columnTitles, list));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    name     age      address  
    -------- -------- -------- 
    feilong0 18       shanghai 
    feilong1 19       shanghai 
    feilong2 20       shanghai 
    feilong3 21       shanghai 
    feilong4 22       shanghai
     * </pre>
     * 
     * </blockquote>
     *
     * @param columnTitles
     *            标题行
     * @param dataList
     *            数据集
     * @return 格式化之后的字符串
     */
    public static final String formatToSimpleTable(String[] columnTitles,List<Object[]> dataList){
        return SIMPLE_TABLE_FORMATTER.format(columnTitles, dataList);
    }
}
