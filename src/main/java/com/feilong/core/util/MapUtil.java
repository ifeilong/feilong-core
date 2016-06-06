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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.comparators.ReverseComparator;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.Validator;
import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.PropertyUtil;
import com.feilong.core.util.comparator.PropertyComparator;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * {@link Map}工具类.
 * 
 * <h3>hashCode与equals:</h3>
 * <blockquote>
 * 
 * <p>
 * hashCode重要么?<br>
 * 不重要,对于List集合、数组而言,他就是一个累赘,但是对于HashMap、HashSet、HashTable而言,它变得异常重要.
 * </p>
 * 
 * <p>
 * 在Java中hashCode的实现总是伴随着equals,他们是紧密配合的,你要是自己设计了其中一个,就要设计另外一个。
 * </p>
 * <p>
 * <img src="http://venusdrogon.github.io/feilong-platform/mysource/hashCode-and-equals.jpg"/>
 * </p>
 * 
 * 整个处理流程是:
 * <ol>
 * <li>判断两个对象的hashcode是否相等,若不等,则认为两个对象不等,完毕,若相等,则比较equals。</li>
 * <li>若两个对象的equals不等,则可以认为两个对象不等,否则认为他们相等。</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.Map }:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">interface/class</th>
 * <th align="left">说明</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.util.Map Map}</td>
 * <td>
 * <ol>
 * <li>An object that maps keys to values.</li>
 * <li>A map cannot contain duplicate keys</li>
 * <li>Takes the place of the Dictionary class</li>
 * </ol>
 * </td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.util.HashMap HashMap}</td>
 * <td>
 * <ol>
 * <li>Hash table based implementation of the Map interface.</li>
 * <li>permits null values and the null key.</li>
 * <li>makes no guarantees as to the order of the map</li>
 * </ol>
 * <p>
 * 扩容:
 * </p>
 * <blockquote>
 * <ol>
 * <li>HashMap 初始容量 {@link java.util.HashMap#DEFAULT_INITIAL_CAPACITY }是16,DEFAULT_LOAD_FACTOR 是0.75
 * {@link java.util.HashMap#addEntry } 是 2 * table.length 2倍<br>
 * </ol>
 * </blockquote>
 * </td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.util.LinkedHashMap LinkedHashMap}</td>
 * <td>
 * <ol>
 * <li>Hash table and linked list implementation of the Map interface,</li>
 * <li>with predictable iteration order.</li>
 * </ol>
 * Note that: insertion order is not affected if a key is re-inserted into the map.
 * </td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.util.TreeMap TreeMap}</td>
 * <td>
 * <ol>
 * <li>A Red-Black tree based NavigableMap implementation</li>
 * <li>sorted according to the natural ordering of its keys, or by a Comparator.</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.util.Hashtable Hashtable}</td>
 * <td>
 * <ol>
 * <li>This class implements a hashtable, which maps keys to values.</li>
 * <li>synchronized.</li>
 * <li>Any non-null object can be used as a key or as a value.</li>
 * </ol>
 * </td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.util.Properties Properties}</td>
 * <td>
 * <ol>
 * <li>The Properties class represents a persistent set of properties.</li>
 * <li>can be saved to a stream or loaded from a stream.</li>
 * <li>Each key and its corresponding value in the property list is a string.</li>
 * </ol>
 * </td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.util.IdentityHashMap IdentityHashMap}</td>
 * <td>
 * <ol>
 * <li>using reference-equality in place of object-equality when comparing keys (and values).</li>
 * <li>使用==代替equals()对key进行比较的散列表.专为特殊问题而设计的</li>
 * </ol>
 * <p style="color:red">
 * 注意:此类不是 通用 Map 实现！它有意违反 Map 的常规协定,此类设计仅用于其中需要引用相等性语义的罕见情况
 * </p>
 * </td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.util.WeakHashMap WeakHashMap}</td>
 * <td>
 * <ol>
 * <li>A hashtable-based Map implementation with weak keys.</li>
 * <li>它对key实行"弱引用",如果一个key不再被外部所引用,那么该key可以被GC回收</li>
 * </ol>
 * </td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.util.EnumMap EnumMap}</td>
 * <td>
 * <ol>
 * <li>A specialized Map implementation for use with enum type keys.</li>
 * <li>Enum maps are maintained in the natural order of their keys</li>
 * <li>不允许空的key</li>
 * </ol>
 * </td>
 * </tr>
 * </table>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.util.AbstractMap.SimpleEntry
 * @see org.apache.commons.collections4.MapUtils
 * @since 1.0.0
 */
public final class MapUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MapUtil.class);

    /** Don't let anyone instantiate this class. */
    private MapUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 仅当 <code>null != map && null != value</code>才将key/value put到map中.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map to add to, may not be null
     * @param key
     *            the key
     * @param value
     *            the value
     * @see org.apache.commons.collections4.MapUtils#safeAddToMap(Map, Object, Object)
     * @since 1.4.0
     */
    public static <K, V> void putIfValueNotNull(final Map<K, V> map,final K key,final V value){
        if (null != map && null != value){
            map.put(key, value);
        }
    }

    /**
     * 如果map中存在key,那么累加value值;如果不存在那么直接put.
     * 
     * <p>
     * 如果 <code>map</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>value</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * Map{@code <String, Integer>} map = new HashMap{@code <String, Integer>}();
     * MapUtil.putSumValue(map, "1000001", 5);
     * MapUtil.putSumValue(map, "1000002", 5);
     * MapUtil.putSumValue(map, "1000002", 5);
     * LOGGER.debug(JsonUtil.format(map));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "1000001": 5,
     * "1000002": 10
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param map
     *            the map
     * @param key
     *            the key
     * @param value
     *            the value
     * @see org.apache.commons.lang3.mutable.MutableInt
     * @see <a href="http://stackoverflow.com/questions/81346/most-efficient-way-to-increment-a-map-value-in-java">most-efficient-way-to-
     *      increment-a-map-value-in-java</a>
     * @see "java.util.Map#getOrDefault(Object, Object)"
     * @see org.apache.commons.collections4.bag.HashBag
     * @since 1.5.5
     */
    public static <K> void putSumValue(Map<K, Integer> map,K key,Integer value){
        Validate.notNull(map, "map can't be null!");
        Validate.notNull(value, "value can't be null!");

        Integer v = map.get(key);//这里不要使用 map.containsKey(key),否则会有2次  two potentially expensive operations
        map.put(key, null == v ? value : value + v);//Suggestion: you should care about code readability more than little performance gain in most of the time.
    }

    /**
     * 指定一个map,指定特定的keys,取得其中的 value 最小值.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = new HashMap{@code <String, Integer>}();
     * 
     * map.put("a", 3007);
     * map.put("b", 3001);
     * map.put("c", 3002);
     * map.put("d", 3003);
     * map.put("e", 3004);
     * map.put("f", 3005);
     * map.put("g", -1005);
     * 
     * LOGGER.info("" + MapUtil.getMinValue(map, "a", "b", "d", "g", "m"));
     * </pre>
     * 
     * 返回:
     * -1005
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <T>
     *            the generic type
     * @param map
     *            指定一个map
     * @param keys
     *            指定特定的key
     * @return 如果 <code>map</code> 是null或者empty,返回 null;<br>
     *         如果 <code>keys</code> 是null或者empty,返回<code>map</code>所有value的最小值<br>
     *         如果循环的 key不在map key里面,则返回的map中忽略该key,并输出warn level log<br>
     *         如果 keys 中的所有的key 都不在 map 中出现 ,那么返回null
     * @see #getSubMap(Map, Object...)
     * @see java.util.Collections#min(Collection)
     */
    @SuppressWarnings("unchecked")
    public static <K, T extends Number & Comparable<? super T>> T getMinValue(Map<K, T> map,K...keys){
        Map<K, T> subMap = getSubMap(map, keys);
        //注意 Number本身 没有实现Comparable接口
        return Validator.isNullOrEmpty(subMap) ? null : Collections.min(subMap.values());
    }

    /**
     * 获得一个map 中的按照指定的key 整理成新的map.
     * 
     * <p>
     * 注意:如果循环的 key不在map key里面,则返回的map中忽略该key,并输出warn level log
     * </p>
     * 
     * <p>
     * 返回的map为 {@link LinkedHashMap},key的顺序 按照参数 <code>keys</code>的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = new HashMap{@code <String, Integer>}();
     * map.put("a", 3007);
     * map.put("b", 3001);
     * map.put("c", 3001);
     * map.put("d", 3003);
     * LOGGER.debug(JsonUtil.format(MapUtil.getSubMap(map, "a", "c")));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "a": 3007,
     * "c": 3001
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <T>
     *            the generic type
     * @param map
     *            the map
     * @param keys
     *            如果循环的 key不在map key里面,则返回的map中忽略该key,并输出warn level log
     * @return 如果 <code>map</code> 是null或者empty,返回 {@link Collections#emptyMap()};<br>
     *         如果 <code>keys</code> 是null或者empty,返回 <code>map</code><br>
     *         如果循环的 key不在map key里面,则返回的map中忽略该key,并输出warn level log
     */
    @SafeVarargs
    public static <K, T> Map<K, T> getSubMap(Map<K, T> map,K...keys){
        if (Validator.isNullOrEmpty(map)){
            return Collections.emptyMap();
        }
        if (Validator.isNullOrEmpty(keys)){
            return map;
        }
        //保证元素的顺序 ,key的顺序 按照参数 <code>keys</code>的顺序
        Map<K, T> returnMap = new LinkedHashMap<K, T>();

        for (K key : keys){
            if (map.containsKey(key)){
                returnMap.put(key, map.get(key));
            }else{
                LOGGER.warn("map don't contains key:[{}]", key);
            }
        }
        return returnMap;
    }

    /**
     * 获得 sub map(去除不需要的keys).
     * 
     * <p>
     * 返回值为 {@link LinkedHashMap},key的顺序 按照参数 <code>map</code>的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = new LinkedHashMap{@code <String, Integer>}();
     * 
     * map.put("a", 3007);
     * map.put("b", 3001);
     * map.put("c", 3002);
     * map.put("g", -1005);
     * 
     * LOGGER.debug(JsonUtil.format(MapUtil.getSubMapExcludeKeys(map, "a", "g", "m")));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "b": 3001,
     * "c": 3002
     * }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * 如果 <code>excludeKeys</code>中含有 map 中不存在的key,将会输出warn级别的log
     * </p>
     * 
     * @param <K>
     *            the key type
     * @param <T>
     *            the generic type
     * @param map
     *            the map
     * @param excludeKeys
     *            the keys
     * @return 如果 <code>map</code> 是null或者empty,返回 {@link Collections#emptyMap()};<br>
     *         如果 <code>excludeKeys</code> 是null或者empty,直接返回 <code>map</code>
     * @since 1.0.9
     */
    @SafeVarargs
    public static <K, T> Map<K, T> getSubMapExcludeKeys(Map<K, T> map,K...excludeKeys){
        if (Validator.isNullOrEmpty(map)){
            return Collections.emptyMap();
        }
        if (Validator.isNullOrEmpty(excludeKeys)){
            return map;
        }

        //保证元素的顺序 
        Map<K, T> returnMap = new LinkedHashMap<K, T>(map);

        for (K key : excludeKeys){
            if (map.containsKey(key)){
                returnMap.remove(key);
            }else{
                if (LOGGER.isWarnEnabled()){
                    LOGGER.warn("map:{} don't contains key:[{}]", JsonUtil.format(map), key);
                }
            }
        }
        return returnMap;
    }

    /**
     * map的key和value互转.
     * 
     * <p>
     * <span style="color:red">这个操作map预先良好的定义</span>.如果传过来的map,不同的key有相同的value,那么返回的map(key)只会有一个(value),其他重复的key被丢掉了
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = new HashMap{@code <String, Integer>}();
     * map.put("a", 3007);
     * map.put("b", 3001);
     * map.put("c", 3001);
     * map.put("d", 3003);
     * LOGGER.debug(JsonUtil.format(MapUtil.invertMap(map)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "3001": "c",
     * "3007": "a",
     * "3003": "d"
     * }
     * </pre>
     * 
     * 可以看出 b元素被覆盖了
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果<code>map</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果<code>map</code> 是empty,返回 一个 new HashMap
     * @see org.apache.commons.collections4.MapUtils#invertMap(Map)
     * @since 1.2.2
     */
    public static <K, V> Map<V, K> invertMap(Map<K, V> map){
        return MapUtils.invertMap(map);//返回的是 HashMap
    }

    /**
     * 以参数 <code>map</code>的key为key,以参数 <code>map</code> value的指定<code>extractPropertyName</code>属性值为值,拼装成新的map返回.
     * 
     * <p>
     * 如果在抽取的过程中,<code>map</code>没有某个 <code>includeKeys</code>,将会输出 warn log
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <Long, User>} map = new LinkedHashMap{@code <Long, User>}();
     * map.put(1L, new User(1L));
     * map.put(2L, new User(2L));
     * map.put(53L, new User(3L));
     * map.put(5L, new User(5L));
     * map.put(6L, new User(6L));
     * map.put(4L, new User(4L));
     * 
     * LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, "id", Long.class)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "1": 1,
     * "2": 2,
     * "4": 4,
     * "5": 5,
     * "6": 6,
     * "53": 3
     * }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <K>
     *            the key type
     * @param <O>
     *            the generic type
     * @param <V>
     *            the generic type
     * @param map
     *            the map
     * @param extractPropertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param keysClass
     *            map key 的class 类型
     * @return 如果 <code>map</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keysClass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>extractPropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>extractPropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         抽取map value 的 <code>extractPropertyName</code>属性值,拼装成新的map返回
     * @since 1.3.0
     */
    public static <K, O, V> Map<K, V> extractSubMap(Map<K, O> map,String extractPropertyName,Class<K> keysClass){
        return extractSubMap(map, null, extractPropertyName, keysClass);
    }

    /**
     * 以参数 <code>map</code>的key为key(key需要在 <code>includeKeys</code>范围内),以参数 <code>map</code> value的 指定<code>extractPropertyName</code>
     * 属性值为值,拼装成新的map返回.
     * 
     * <p>
     * 如果在抽取的过程中,<code>map</code>没有某个 <code>includeKeys</code>,将会输出 warn log
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <Long, User>} map = new LinkedHashMap{@code <Long, User>}();
     * map.put(1L, new User(1L));
     * map.put(2L, new User(2L));
     * map.put(53L, new User(3L));
     * map.put(5L, new User(5L));
     * map.put(6L, new User(6L));
     * map.put(4L, new User(4L));
     * Long[] includeKeys = { 5L, 4L };
     * LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, includeKeys, "id", Long.class)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "5": 5
     * "4": 4
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param <O>
     *            the generic type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @param includeKeys
     *            the include keys
     * @param extractPropertyName
     *            泛型O对象指定的属性名称,Possibly indexed and/or nested name of the property to be modified,参见
     *            {@link <a href="../bean/BeanUtil.html#propertyName">propertyName</a>}
     * @param keysClass
     *            map key 的class 类型
     * @return 如果 <code>map</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>keysClass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>extractPropertyName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>extractPropertyName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>includeKeys</code> 是null或者empty, then will extract map total keys<br>
     *         抽取map value 的 <code>extractPropertyName</code>属性值,拼装成新的map返回
     * @since 1.3.0
     */
    public static <K, O, V> Map<K, V> extractSubMap(Map<K, O> map,K[] includeKeys,String extractPropertyName,Class<K> keysClass){
        if (Validator.isNullOrEmpty(map)){
            return Collections.emptyMap();
        }

        Validate.notBlank(extractPropertyName, "extractPropertyName can't be null/empty!");
        Validate.notNull(keysClass, "keysClass can't be null!");

        //如果excludeKeys是null,那么抽取所有的key
        K[] useIncludeKeys = Validator.isNullOrEmpty(includeKeys) ? ConvertUtil.toArray(map.keySet(), keysClass) : includeKeys;

        //保证元素的顺序  顺序是参数  includeKeys的顺序
        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (K key : useIncludeKeys){
            if (map.containsKey(key)){
                O o = map.get(key);
                V v = PropertyUtil.getProperty(o, extractPropertyName);
                returnMap.put(key, v);
            }else{
                LOGGER.warn("map:{} don't contains key:[{}]", JsonUtil.format(map), key);
            }
        }
        return returnMap;
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
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return 如果 <code>map</code> 是null,抛出 {@link NullPointerException}<br>
     *         否则直接构造 {@link TreeMap}返回
     * @see java.util.TreeMap#TreeMap(Map)
     * @since 1.2.0
     */
    public static <K, V> Map<K, V> sortByKeyAsc(Map<K, V> map){
        Validate.notNull(map, "map can't be null!");
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
     * @return 如果 <code>map</code> 是null,抛出 {@link NullPointerException}<br>
     * @see ReverseComparator#ReverseComparator(Comparator)
     * @see PropertyComparator#PropertyComparator(String)
     * @see #sort(Map, Comparator)
     * @since 1.2.0
     */
    public static <K, V> Map<K, V> sortByKeyDesc(Map<K, V> map){
        Validate.notNull(map, "map can't be null!");
        PropertyComparator<Entry<K, V>> propertyComparator = new PropertyComparator<Map.Entry<K, V>>("key");
        return sort(map, new ReverseComparator<Map.Entry<K, V>>(propertyComparator));
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
     * @return 如果<code>map</code>是null,抛出异常<br>
     * @see #sort(Map, Comparator)
     * @since 1.2.0
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueAsc(Map<K, V> map){
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
     * @return 如果<code>map</code>是null,抛出异常<br>
     * @see #sort(Map, Comparator)
     * @since 1.2.0
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueDesc(Map<K, V> map){
        PropertyComparator<Entry<K, V>> propertyComparator = new PropertyComparator<Map.Entry<K, V>>("value");
        Comparator<Entry<K, V>> comparator = new ReverseComparator<Map.Entry<K, V>>(propertyComparator);
        return sort(map, comparator);
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
     * @return 如果<code>map</code>是null,抛出异常<br>
     *         如果<code>mapEntryComparator</code>是null,抛出异常
     * @see java.util.Collections#sort(List, Comparator)
     * @since 1.2.0
     */
    public static <K, V> Map<K, V> sort(Map<K, V> map,Comparator<Map.Entry<K, V>> mapEntryComparator){
        Validate.notNull(map, "map can't be null!");
        Validate.notNull(mapEntryComparator, "mapEntryComparator can't be null!");

        List<Map.Entry<K, V>> mapEntryList = ConvertUtil.toList(map.entrySet());
        Collections.sort(mapEntryList, mapEntryComparator);
        return toMap(mapEntryList);
    }

    /**
     * To map.
     *
     * @param <V>
     *            the value type
     * @param <K>
     *            the key type
     * @param mapEntryList
     *            the map entry list
     * @return the map< k, v>
     * @since 1.6.1
     * @see org.apache.commons.lang3.ArrayUtils#toMap(Object[])
     */
    private static <V, K> Map<K, V> toMap(List<Map.Entry<K, V>> mapEntryList){
        Map<K, V> returnMap = new LinkedHashMap<K, V>(mapEntryList.size());

        for (Map.Entry<K, V> entry : mapEntryList){
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }
}
