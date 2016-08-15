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

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理将对象格式化的工具类.
 * 
 * <p>
 * 提供静态的方法,方便调用或者方便使用 import static
 * </p>
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
     * 使用 {@link SimpleTableFormatter}来格式化.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>不建议format 太多的数据,以容易查看为原则</li>
     * <li>如果字段含有中文,显示可能会错位,你可以尝试将结果进行 replace(SPACE, "\u3000") 处理,参见
     * <a href="http://stackoverflow.com/questions/18961628/how-can-i-align-the-next-lines-in-java#answer-18962279">format Chinese
     * characters</a></li>
     * </ol>
     * </blockquote>
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
     * @return 如果 <code>iterable</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     */
    public static final <T> String formatToSimpleTable(Iterable<T> iterable){
        return SIMPLE_TABLE_FORMATTER.format(iterable);
    }

    /**
     * 使用 {@link SimpleTableFormatter}来格式化.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>不建议format 太多的数据,以容易查看为原则</li>
     * <li>如果字段含有中文,显示可能会错位,你可以尝试将结果进行 replace(SPACE, "\u3000") 处理,参见
     * <a href="http://stackoverflow.com/questions/18961628/how-can-i-align-the-next-lines-in-java#answer-18962279">format Chinese
     * characters</a></li>
     * </ol>
     * </blockquote>
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
     * @return 如果 <code>iterable</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     */
    public static final <T> String formatToSimpleTable(Iterable<T> iterable,BeanFormatterConfig<T> beanFormatterConfig){
        return SIMPLE_TABLE_FORMATTER.format(iterable, beanFormatterConfig);
    }

    /**
     * 格式化成简单的table格式.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>不建议format 太多的数据,以容易查看为原则</li>
     * <li>如果字段含有中文,显示可能会错位,你可以尝试将结果进行 replace(SPACE, "\u3000") 处理,参见
     * <a href="http://stackoverflow.com/questions/18961628/how-can-i-align-the-next-lines-in-java#answer-18962279">format Chinese
     * characters</a></li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, String>} map = toMap(//
     *                 Pair.of("Loading entityengine.xml from", "file:/opt/atlassian/jira/atlassian-jira/WEB-INF/classes/entityengine.xml"),
     *                 Pair.of("Entity model field type name", "postgres72"),
     *                 Pair.of("Entity model schema name", "public"),
     *                 Pair.of("Database Version", "PostgreSQL - 9.2.8"),
     *                 Pair.of("Database Driver", "PostgreSQL Native Driver - PostgreSQL 9.0 JDBC4 (build 801)"),
     *                 Pair.of("Database Version", "PostgreSQL - 9.2.8"),
     *                 Pair.of((String) null, "PostgreSQL - 9.2.8"),
     *                 Pair.of("Database URL", "jdbc:postgresql://127.0.0.1:5432/db_feilong_jira"),
     *                 Pair.of("Database JDBC config", "postgres72 jdbc:postgresql://127.0.0.1:5432/db_feilong_jira"));
     * 
     * LOGGER.debug(formatToSimpleTable(map));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
                              : PostgreSQL - 9.2.8                                                       
    Database Driver               : PostgreSQL Native Driver - PostgreSQL 9.0 JDBC4 (build 801)              
    Database JDBC config          : postgres72 jdbc:postgresql://127.0.0.1:5432/db_feilong_jira              
    Database URL                  : jdbc:postgresql://127.0.0.1:5432/db_feilong_jira                         
    Database Version              : PostgreSQL - 9.2.8                                                       
    Entity model field type name  : postgres72                                                               
    Entity model schema name      : public                                                                   
    Loading entityengine.xml from : file:/opt/atlassian/jira/atlassian-jira/WEB-INF/classes/entityengine.xml
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     */
    public static final <K, V> String formatToSimpleTable(Map<K, V> map){
        return SIMPLE_TABLE_FORMATTER.format(map);
    }

    /**
     * 格式化成简单的table格式.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>不建议format 太多的数据,以容易查看为原则</li>
     * <li>如果字段含有中文,显示可能会错位,你可以尝试将结果进行 replace(SPACE, "\u3000") 处理,参见
     * <a href="http://stackoverflow.com/questions/18961628/how-can-i-align-the-next-lines-in-java#answer-18962279">format Chinese
     * characters</a></li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * User user = new User();
     * user.setAge(15);
     * user.setId(88L);
     * user.setAttrMap(toMap("love", "sanguo"));
     * user.setDate(new Date());
     * user.setMoney(toBigDecimal(999));
     * user.setName("xinge");
     * user.setNickNames(toArray("jinxin", "feilong"));
     * LOGGER.debug(formatToSimpleTable(user));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    age              : 15                                
    attrMap          : {love=sanguo}                     
    class            : com.feilong.test.User             
    date             : Tue Jul 26 00:02:16 CST 2016      
    id               : 88                                
    loves            :                                   
    money            : 999                               
    name             : xinge                             
    nickNames        : jinxin                            
    password         :                                   
    userAddresseList :                                   
    userAddresses    :                                   
    userInfo         : com.feilong.test.UserInfo@1c21535
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param bean
     *            the bean
     * @return 如果 <code>bean</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     */
    public static final <T> String formatToSimpleTable(T bean){
        return SIMPLE_TABLE_FORMATTER.format(bean);
    }
}
