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
import java.util.TreeMap;

import org.apache.commons.collections4.comparators.ReverseComparator;
import org.apache.commons.lang3.Validate;

import com.feilong.core.util.comparator.BeanComparatorUtil;
import com.feilong.core.util.comparator.PropertyComparator;

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
 * 由于 这些方法是 void类型的,通常我们需要写2至3行代码
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
     * 对 数组 <code>array</code> 进行排序.
     *
     * @param <T>
     *            the generic type
     * @param arrays
     *            the array
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

    //*****************************************************************************************

    /**
     * 对 集合 <code>list</code> 进行排序.
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

    //*****************************************************************************************

    /**
     * 对 集合 <code>list</code>,指定属性(组合)进行排序.
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
     */
    public static <O> List<O> sort(List<O> list,String...propertyNames){
        if (null == list){
            return emptyList();
        }
        Validate.notEmpty(propertyNames, "propertyNames can't be null/empty!");
        Validate.noNullElements(propertyNames, "propertyName:%s has empty value", propertyNames);

        Collections.sort(list, BeanComparatorUtil.chainedComparator(propertyNames));
        return list;
    }

    //*************************************************************************************************

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
     * @see BeanComparatorUtil#propertyComparator(String, Object...)
     */
    @SafeVarargs
    public static <O, V> List<O> sortByFixedOrder(List<O> list,String propertyName,V...propertyValues){
        if (null == list){
            return emptyList();
        }
        Validate.notBlank(propertyName, "propertyName can't be blank!");

        Collections.sort(list, BeanComparatorUtil.propertyComparator(propertyName, propertyValues));
        return list;
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
     */
    public static <O, V> List<O> sortByFixedOrder(List<O> list,String propertyName,List<V> propertyValues){
        if (null == list){
            return emptyList();
        }
        Validate.notBlank(propertyName, "propertyName can't be blank!");

        Collections.sort(list, BeanComparatorUtil.propertyComparator(propertyName, propertyValues));
        return list;
    }

    //*******************************排序****************************************************
    /**
     * 按照key asc顺序排序.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <String, Comparable>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyAsc(map)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "a": 123,
     * "b": 8,
     * "c": 345
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <p>
     * 由于排序使用的是 {@link java.util.TreeMap#TreeMap(Map)},而TreeMap是不允许 key是null, 如果传入的参数 <code>map</code>中,如果有key是null,那么将会抛出
     * {@link NullPointerException}
     * </p>
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,返回 {@link Collections#emptyMap()}<br>
     *         否则直接构造 {@link TreeMap}返回
     * @see java.util.TreeMap#TreeMap(Map)
     * @since 1.8.0 move from MapUtil
     */
    public static <K, V> Map<K, V> sortByKeyAsc(Map<K, V> map){
        if (null == map){
            return emptyMap();
        }
        return new TreeMap<K, V>(map);
    }

    /**
     * 按照key desc 倒序排序.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <String, Comparable>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyDesc(map)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "c": 345,
     * "b": 8,
     * "a": 123
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
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new HashMap{@code <String, Comparable>}();
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put("b", 8);
     * LOGGER.debug(JsonUtil.format(MapUtil.sortByValueAsc(map)));
     * </pre>
     * 
     * 返回:
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
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Comparable>} map = new LinkedHashMap{@code <String, Comparable>}();
     * 
     * map.put("a", 123);
     * map.put("c", 345);
     * map.put("b", 8);
     * 
     * LOGGER.debug(JsonUtil.format(MapUtil.sortByValueDesc(map)));
     * </pre>
     * 
     * 返回:
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
     * LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyAsc(map)));
     * </pre>
     * 
     * 返回:
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
     * LOGGER.debug(JsonUtil.format(MapUtil.sort(map, propertyComparator)));
     * </pre>
     * 
     * 返回:
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
        Collections.sort(mapEntryList, mapEntryComparator);
        return toMap(mapEntryList);
    }

}
