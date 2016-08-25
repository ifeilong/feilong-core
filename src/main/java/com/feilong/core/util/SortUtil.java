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
package com.feilong.core.util;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.comparators.ReverseComparator;
import org.apache.commons.lang3.Validate;

import com.feilong.core.util.comparator.BeanComparatorUtil;
import com.feilong.core.util.comparator.PropertyComparator;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;

/**
 * 专注于排序的工具类.
 * 
 * <h3>说明:</h3>
 * <blockquote>
 * <ol>
 * <li>该类的初衷之一,通常如果直接使用jdk里面的sort方法,比如
 * 
 * <pre class="code">
 * {@link java.util.Arrays#sort(Object[])}
 * </pre>
 * 
 * 或者
 * 
 * <pre class="code">
 * {@link java.util.Collections#sort(List)}
 * </pre>
 * 
 * 由于这些方法是 void类型的,通常我们需要写2至3行代码
 * 
 * </li>
 * <li>第二,该类对bean list的排序更加简单便捷</li>
 * </ol>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public final class SortUtil{

    /** Don't let anyone instantiate this class. */
    private SortUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //*****************************************************************************************
    /**
     * 对 数组 <code>arrays</code> 进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * LOGGER.debug(JsonUtil.format(sort(5, 10, 3, 2), 0, 0));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [2,3,5,10]
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>以前代码需要写成:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * public static String toSalesPropertiesIdsJson(Long...itemPropertiesIdLongs){
     *     Arrays.sort(itemPropertiesIdLongs);
     *     return JsonUtil.format(itemPropertiesIdLongs, 0, 0);
     * }
     * 
     * </pre>
     * 
     * 现在可以重构成:
     * 
     * <pre class="code">
     * 
     * public static String toSalesPropertiesIdsJson(Long...itemPropertiesIdLongs){
     *     return JsonUtil.format(sort(itemPropertiesIdLongs), 0, 0);
     * }
     * </pre>
     * 
     * <b>再如:</b>
     * 
     * <pre class="code">
     * 
     * <span style="color:green">// 得到默认分类,目前是最小的</span>
     * private Long getDefaultCategoryId(Long[] categoriesIds){
     *     Arrays.sort(categoriesIds);
     *     return categoriesIds[0];
     * }
     * 
     * </pre>
     * 
     * 可以重构成:
     * 
     * <pre class="code">
     * 
     * <span style="color:green">// 得到默认分类,目前是最小的</span>
     * private Long getDefaultCategoryId(Long[] categoriesIds){
     *     return sort(categoriesIds)[0];
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param arrays
     *            the arrays
     * @return 如果 <code>array</code> 是null,返回 empty array<br>
     * @see java.util.Arrays#sort(Object[])
     */
    @SafeVarargs
    public static <T> T[] sort(T...arrays){
        if (null == arrays){
            return toArray();
        }
        Arrays.sort(arrays);
        return arrays;
    }

    /**
     * 对 数组 <code>arrays</code>使用 <code>comparator</code> 进行排序.
     *
     * @param <T>
     *            the generic type
     * @param arrays
     *            the arrays
     * @param comparators
     *            the comparators
     * @return 如果 <code>array</code> 是null,返回 empty array<br>
     *         如果 <code>comparators</code> 是null或者empty,直接返回 <code>arrays</code><br>
     * @see java.util.Arrays#sort(Object[], Comparator)
     * @since 1.8.2 change to varargs parameter comparator
     */
    @SafeVarargs
    public static <T> T[] sort(T[] arrays,Comparator<T>...comparators){
        if (null == arrays){
            return toArray();
        }
        if (isNullOrEmpty(comparators)){
            return arrays;
        }
        Arrays.sort(arrays, toComparator(comparators));
        return arrays;
    }

    //*****************************************************************************************

    /**
     * 对 集合 <code>list</code> 进行排序.
     * 
     * <p>
     * {@link java.util.Collections#sort(List) Collections.sort} 底层就是调用的是 {@link java.util.Arrays#sort(Object[]) Arrays.sort}
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <Integer>} list = toList(5, 10, 3, 2);
     * LOGGER.debug(JsonUtil.format(sort(list), 0, 0));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [2,3,5,10]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     * @see java.util.Collections#sort(List)
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> list){
        if (null == list){
            return emptyList();
        }
        Collections.sort(list);
        return list;
    }

    /**
     * 对集合 <code>list</code>,使用指定的 <code>comparator</code> 进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User(12L, 18));
     * list.add(new User(2L, 36));
     * list.add(new User(5L, 22));
     * list.add(new User(1L, 8));
     * SortUtil.sort(list, new PropertyComparator{@code <User>}("id"));
     * LOGGER.debug(JsonUtil.format(list));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [{"id": 1,"age": 8},
     * {"id": 2,"age": 36},
     * {"id": 5,"age": 22},
     * {"id": 12,"age": 18}]
     * </pre>
     * 
     * 当然对于上述示例,你可以直接调用:
     * 
     * <pre class="code">
     * SortUtil.sort(list, "id");
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param list
     *            the list
     * @param comparators
     *            the comparators
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     *         如果 <code>comparators</code> 是null或者empty,直接返回 <code>list</code><br>
     * @see java.util.Collections#sort(List, Comparator)
     * @since 1.8.2
     */
    @SafeVarargs
    public static <O> List<O> sort(List<O> list,Comparator<O>...comparators){
        if (null == list){
            return emptyList();
        }

        if (isNullOrEmpty(comparators)){
            return list;
        }
        Collections.sort(list, toComparator(comparators));
        return list;
    }

    /**
     * 如果 <code>comparators length ==1</code>,返回 comparators[0]; 否则返回 {@link ComparatorUtils#chainedComparator(Comparator...)};
     *
     * @param <O>
     *            the generic type
     * @param comparators
     *            the comparators
     * @return the comparator
     * @since 1.8.2
     */
    @SafeVarargs
    private static <O> Comparator<O> toComparator(Comparator<O>...comparators){
        return 1 == comparators.length ? comparators[0] : ComparatorUtils.chainedComparator(comparators);
    }

    //*****************************************************************************************

    /**
     * 对 集合 <code>list</code>,指定属性(组合)进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * List{@code <User>} list = new ArrayList{@code <User>}();
     * list.add(new User(12L, 18));
     * list.add(new User(2L, 36));
     * list.add(new User(2L, 2));
     * list.add(new User(2L, 30));
     * list.add(new User(1L, 8));
     * SortUtil.sort(list, "id", "age");
     * LOGGER.debug(JsonUtil.formatWithIncludes(list, "id", "age"));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
        [{"id": 1,"age": 8},
        {"id": 2,"age": 2},
        {"id": 2,"age": 30},
        {"id": 2,"age": 36},
        {"id": 12,"age": 18}]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param list
     *            the list
     * @param propertyNames
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     * @throws NullPointerException
     *             如果 <code>propertyNames</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyNames</code> 是empty ,或者有 null元素
     * @see BeanComparatorUtil#chainedComparator(String...)
     * @see org.apache.commons.collections4.ComparatorUtils#chainedComparator(java.util.Comparator...)
     * @see #sort(List, Comparator...)
     */
    public static <O> List<O> sort(List<O> list,String...propertyNames){
        if (null == list){
            return emptyList();
        }
        Validate.notEmpty(propertyNames, "propertyNames can't be null/empty!");
        Validate.noNullElements(propertyNames, "propertyName:%s has empty value", propertyNames);

        return sort(
                        list,
                        1 == propertyNames.length ? BeanComparatorUtil.<O> propertyComparator(propertyNames[0])
                                        : BeanComparatorUtil.<O> chainedComparator(propertyNames));
    }

    //*************************************************************************************************

    /**
     * 对 集合 <code>list</code>,属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 进行排序.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * User zhangfei = new User("张飞", 23);
     * User guanyu = new User("关羽", 30);
     * User liubei = new User("刘备", 25);
     * List{@code <User>} list = toList(zhangfei, guanyu, liubei);
     * 
     * List{@code <User>} select = CollectionsUtil.select(list, "name", "刘备", "关羽");
     * Collections.sort(select, new PropertyComparator{@code <User>}("name", new FixedOrderComparator{@code <>}("刘备", "关羽")));
     * LOGGER.debug(JsonUtil.formatWithIncludes(select, "name", "age"));
     * 
     * </pre>
     * 
     * 此时你可以直接调用:
     * 
     * <pre class="code">
     * 
     * List{@code <User>} select2 = CollectionsUtil.select(list, "name", "刘备", "关羽");
     * LOGGER.debug(JsonUtil.formatWithIncludes(SortUtil.sortByFixedOrderPropertyValues(select2, "name", "刘备", "关羽"), "name", "age"));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    [{
                "age": 25,
                "name": "刘备"
            },{
                "age": 30,
                "name": "关羽"
     }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param list
     *            the list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the property values
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see BeanComparatorUtil#propertyComparator(String, Object...)
     * @see #sort(List, Comparator...)
     */
    @SafeVarargs
    public static <O, V> List<O> sortByFixedOrderPropertyValues(List<O> list,String propertyName,V...propertyValues){
        if (null == list){
            return emptyList();
        }
        Validate.notBlank(propertyName, "propertyName can't be blank!");
        Comparator<O> propertyComparator = BeanComparatorUtil.propertyComparator(propertyName, propertyValues);
        return sort(list, propertyComparator);
    }

    /**
     * 对 集合 <code>list</code>,属性 <code>propertyName</code> 按照固定顺序值 <code>propertyValues</code> 进行排序.
     *
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param list
     *            the list
     * @param propertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            <a href="../bean/BeanUtil.html#propertyName">propertyName</a>
     * @param propertyValues
     *            the property values
     * @return 如果 <code>list</code> 是null,返回 {@link Collections#emptyList()}<br>
     * @throws NullPointerException
     *             如果 <code>propertyName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>propertyName</code> 是blank
     * @see BeanComparatorUtil#propertyComparator(String, List)
     * @see #sort(List, Comparator...)
     */
    public static <O, V> List<O> sortByFixedOrderPropertyValues(List<O> list,String propertyName,List<V> propertyValues){
        if (null == list){
            return emptyList();
        }
        Validate.notBlank(propertyName, "propertyName can't be blank!");

        Comparator<O> propertyComparator = BeanComparatorUtil.propertyComparator(propertyName, propertyValues);
        return sort(list, propertyComparator);
    }

    //*******************************排序****************************************************
    /**
     * 按照key asc顺序排序.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>原 <code>map</code> 的顺序不变</li>
     * <li>该方法使用了 {@link PropertyComparator},允许 null key,<b>null key排在最前面</b></li>
     * <li>
     * 如果直接使用 {@link java.util.TreeMap#TreeMap(Map)},TreeMap不允许 key是null,如果有key是null,那么将会抛出{@link NullPointerException}<br>
     * </li>
     * 
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put(null, 1345);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(SortUtil.sortByKeyAsc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * null: 1345,
     * "a": 123,
     * "b": 8,
     * "c": 345
     * }
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
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see java.util.TreeMap#TreeMap(Map)
     * @since 1.8.0 move from MapUtil
     */
    public static <K, V> Map<K, V> sortByKeyAsc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return sort(map, new PropertyComparator<Map.Entry<K, V>>("key"));
    }

    /**
     * 按照key desc 倒序排序.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>原 <code>map</code> 的顺序不变</li>
     * <li>该方法使用了 {@link PropertyComparator},允许 null key,<b>null key排在最后面</b></li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put(null, 88);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(SortUtil.sortByKeyDesc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "c": 345,
     * "b": 8,
     * "a": 123,
     * null: 88
     * }
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
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see ReverseComparator#ReverseComparator(Comparator)
     * @see PropertyComparator#PropertyComparator(String)
     * @see #sort(Map, Comparator)
     * @since 1.8.0 move from MapUtil
     */
    public static <K, V> Map<K, V> sortByKeyDesc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return sort(map, new ReverseComparator<Map.Entry<K, V>>(new PropertyComparator<Map.Entry<K, V>>("key")));
    }

    /**
     * 根据value 来顺序排序(asc).
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>原 <code>map</code> 的顺序不变</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <>}();
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put("b", 8);
     * LOGGER.debug(JsonUtil.format(SortUtil.sortByValueAsc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "b": 8,
     * "a": 123,
     * "c": 345
     * }
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
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see #sort(Map, Comparator)
     * @since 1.8.0 move from MapUtil
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueAsc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return sort(map, new PropertyComparator<Map.Entry<K, V>>("value"));
    }

    /**
     * 根据value 来倒序排序(desc).
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>原 <code>map</code> 的顺序不变</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new LinkedHashMap{@code <>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(SortUtil.sortByValueDesc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "c": 345,
     * "a": 123,
     * "b": 8
     * }
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
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     * @see #sort(Map, Comparator)
     * @since 1.8.0 move from MapUtil
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueDesc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return sort(map, new ReverseComparator<Map.Entry<K, V>>(new PropertyComparator<Map.Entry<K, V>>("value")));
    }

    /**
     * 使用 基于 {@link java.util.Map.Entry Entry} 的 <code>mapEntryComparator</code> 来对 <code>map</code>进行排序.
     * 
     * <p>
     * 由于是对{@link java.util.Map.Entry Entry}排序的, 既可以按照key来排序,也可以按照value来排序哦
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * 比如有以下的map
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = new HashMap{@code <String, Integer>}();
     * 
     * map.put("a13", 123);
     * map.put("a2", 345);
     * map.put("a8", 8);
     * </pre>
     * 
     * 如果我们只是使用 :
     * 
     * <pre class="code">
     * LOGGER.debug(JsonUtil.format(SortUtil.sortByKeyAsc(map)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "a13": 123,
     * "a2": 345,
     * "a8": 8
     * }
     * </pre>
     * 
     * 此时可以看出 a13是以字符串的形式进行比较的,我们可以使用以下的自定义的 Comparator,来达到排序的效果
     * 
     * <pre class="code">
     * PropertyComparator{@code <Entry<String, Integer>>} propertyComparator = new PropertyComparator{@code <Map.Entry<String, Integer>>}(
     *                 "key",
     *                 new RegexGroupNumberComparator("a(\\d*)"));
     * LOGGER.debug(JsonUtil.format(SortUtil.sort(map, propertyComparator)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "a2": 345,
     * "a8": 8,
     * "a13": 123
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>原 <code>map</code> 的顺序不变</li>
     * </ol>
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @param mapEntryComparator
     *            基于 {@link java.util.Map.Entry Entry} 的 {@link Comparator}
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>mapEntryComparator</code> 是null,抛出 {@link NullPointerException}<br>
     * @see java.util.Collections#sort(List, Comparator)
     * @since 1.8.0 move from MapUtil
     */
    public static <K, V> Map<K, V> sort(Map<K, V> map,Comparator<Map.Entry<K, V>> mapEntryComparator){
        if (null == map){
            return emptyMap();
        }
        Validate.notNull(mapEntryComparator, "mapEntryComparator can't be null!");

        List<Map.Entry<K, V>> mapEntryList = toList(map.entrySet());
        return toMap(sort(mapEntryList, mapEntryComparator));
    }

}
