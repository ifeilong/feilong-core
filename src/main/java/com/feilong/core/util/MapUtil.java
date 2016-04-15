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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
 * <h3>关于 {@link java.util.Map }:</h3>
 * <blockquote>
 * <ol>
 * <li>An object that maps keys to values.</li>
 * <li>A map cannot contain duplicate keys</li>
 * <li>Takes the place of the Dictionary class</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.HashMap }:</h3>
 * <blockquote>
 * <ol>
 * <li>Hash table based implementation of the Map interface.</li>
 * <li>permits null values and the null key.</li>
 * <li>makes no guarantees as to the order of the map</li>
 * </ol>
 * 
 * <h3>扩容:</h3>
 * <blockquote>
 * <ol>
 * <li>HashMap 初始容量 {@link java.util.HashMap#DEFAULT_INITIAL_CAPACITY }是16,DEFAULT_LOAD_FACTOR 是0.75
 * {@link java.util.HashMap#addEntry } 是 2 * table.length 2倍<br>
 * </ol>
 * </blockquote>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.LinkedHashMap }:</h3>
 * <blockquote>
 * <ol>
 * <li>Hash table and linked list implementation of the Map interface,</li>
 * <li>with predictable iteration order.</li>
 * </ol>
 * 
 * Note that: insertion order is not affected if a key is re-inserted into the map.
 * 
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.TreeMap }:</h3>
 * <blockquote>
 * <ol>
 * <li>A Red-Black tree based NavigableMap implementation</li>
 * <li>sorted according to the natural ordering of its keys, or by a Comparator.</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.Hashtable}:</h3>
 * <blockquote>
 * <ol>
 * <li>This class implements a hashtable, which maps keys to values.</li>
 * <li>synchronized.</li>
 * <li>Any non-null object can be used as a key or as a value.</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.Properties}:</h3>
 * <blockquote>
 * <ol>
 * <li>The Properties class represents a persistent set of properties.</li>
 * <li>can be saved to a stream or loaded from a stream.</li>
 * <li>Each key and its corresponding value in the property list is a string.</li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.IdentityHashMap}:</h3>
 * <blockquote>
 * <ol>
 * <li>using reference-equality in place of object-equality when comparing keys (and values).</li>
 * <li>使用==代替equals()对key进行比较的散列表.专为特殊问题而设计的</li>
 * </ol>
 * 
 * <p style="color:red">
 * 注意:此类不是 通用 Map 实现！它有意违反 Map 的常规协定,此类设计仅用于其中需要引用相等性语义的罕见情况
 * </p>
 * </blockquote>
 * 
 * <h3>关于 {@link java.util.WeakHashMap}:</h3>
 * <blockquote>
 * <ol>
 * <li>A hashtable-based Map implementation with weak keys.</li>
 * <li>它对key实行"弱引用",如果一个key不再被外部所引用,那么该key可以被GC回收</li>
 * </ol>
 * </blockquote>
 * 
 * 
 * <h3>关于 {@link java.util.EnumMap}:</h3>
 * <blockquote>
 * <ol>
 * <li>A specialized Map implementation for use with enum type keys.</li>
 * <li>Enum maps are maintained in the natural order of their keys</li>
 * <li>Null keys are not permitted.不允许空的key</li>
 * </ol>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.0 Sep 8, 2012 8:02:44 PM
 * @see org.apache.commons.collections4.MapUtils
 * @see java.util.AbstractMap.SimpleEntry
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
     * 仅当 {@code null != map && null != value} 才将key/value put到map中.
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
     * @since 1.4.0
     */
    public static <K, V> void putIfValueNotNull(final Map<K, V> map,final K key,final V value){
        if (null != map && null != value){
            map.put(key, value);
        }
    }

    /**
     * 指定一个map,指定特定的keys,取得其中的 value 最小值.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            Map<String, Integer> map = new HashMap<String, Integer>();
    
            map.put("a", 3007);
            map.put("b", 3001);
            map.put("c", 3002);
            map.put("d", 3003);
            map.put("e", 3004);
            map.put("f", 3005);
            map.put("g", -1005);
    
            LOGGER.info("" + MapUtil.getMinValue(map, "a", "b", "d", "g", "m"));
    }
    
    返回:
    -1005
     * 
     * </pre>
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
     * @return 如果 keys 中的所有的key 都不在 map 中出现 ,那么返回null
     * 
     * @see #getSubMap(Map, Object...)
     * @see java.util.Collection#toArray()
     * @see java.util.Arrays#sort(Object[])
     */
    @SuppressWarnings("unchecked")
    public static <K, T extends Number> T getMinValue(Map<K, T> map,K...keys){
        Map<K, T> subMap = getSubMap(map, keys);

        if (Validator.isNullOrEmpty(subMap)){
            return null;
        }

        Collection<T> values = subMap.values();
        Object[] array = values.toArray();
        Arrays.sort(array);
        return (T) array[0];
    }

    /**
     * 获得 一个map 中的 按照指定的key 整理成新的map.
     * <p>
     * 注意:如果循环的 key 不在map key 里面 ,则返回的map中忽略该key
     * </p>
     * 
     * <p>
     * 返回的map为 {@link LinkedHashMap},key的顺序 按照参数 <code>keys</code>的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("a", 3007);
            map.put("b", 3001);
            map.put("c", 3001);
            map.put("d", 3003);
            LOGGER.debug(JsonUtil.format(MapUtil.getSubMap(map, "a", "c")));
    }
    
    返回:
    
    {
            "a": 3007,
            "c": 3001
        }
     * 
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
     *            指定keys,如果key 不在map key 里面 ,则返回的map 中忽略该key
     * @return
     *         <ul>
     *         <li>if map isNullOrEmpty,will return {@link Collections#emptyMap()};</li>
     *         <li>if Validator.isNullOrEmpty(keys), return map</li>
     *         <li>如果key 不在map key 里面 ,则返回的map 中忽略该key</li>
     *         </ul>
     */
    @SafeVarargs
    public static <K, T> Map<K, T> getSubMap(Map<K, T> map,K...keys){
        if (Validator.isNullOrEmpty(map)){
            return Collections.emptyMap();
        }
        if (Validator.isNullOrEmpty(keys)){
            return map;
        }
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
     * <pre>
    {@code
            Map<String, Integer> map = new LinkedHashMap<String, Integer>();
    
            map.put("a", 3007);
            map.put("b", 3001);
            map.put("c", 3002);
            map.put("g", -1005);
    
            LOGGER.debug(JsonUtil.format(MapUtil.getSubMapExcludeKeys(map, "a", "g", "m")));
    }
    
    返回:
    
    {
            "b": 3001,
            "c": 3002
        }
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
     * @return
     *         <ul>
     *         <li>if map isNullOrEmpty,will return {@link Collections#emptyMap()};</li>
     *         <li>if Validator.isNullOrEmpty(excludeKeys), return map</li>
     *         </ul>
     * 
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
     * <pre>
    {@code
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("a", 3007);
            map.put("b", 3001);
            map.put("c", 3001);
            map.put("d", 3003);
            LOGGER.debug(JsonUtil.format(MapUtil.invertMap(map)));
    }
    
    返回:
    {
        "3001": "c",
        "3007": "a",
        "3003": "d"
    }
     可以看出 b元素被覆盖了
     * 
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
     * @return 如果map是Empty ,返回 一个empty map
     * @see org.apache.commons.collections4.MapUtils#invertMap(Map)
     * @since 1.2.2
     */
    public static <K, V> Map<V, K> invertMap(Map<K, V> map){
        //注意,返回的是 {@link HashMap}
        return MapUtils.invertMap(map);
    }

    /**
     * 抽取map value <code>T</code>的 <code>extractPropertyName</code>属性值,拼装成新的map返回.
     * 
     * <p>
     * 注意,在抽取的过程中, 如果 <code>map</code> 没有 某个 <code>includeKeys</code>,将会输出 warn log
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            Map<Long, User> map = new LinkedHashMap<Long, User>();
            map.put(1L, new User(1L));
            map.put(2L, new User(2L));
            map.put(53L, new User(3L));
            map.put(5L, new User(5L));
            map.put(6L, new User(6L));
            map.put(4L, new User(4L));
    
            LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, "id", Long.class)));
    }
    
    返回:
    {
            "1": 1,
            "2": 2,
            "4": 4,
            "5": 5,
            "6": 6,
            "53": 3
        }
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
     *            the extract property name
     * @param keysClass
     *            map key 的class 类型
     * @return
     *         <ul>
     *         <li>if Validator.isNullOrEmpty(map),return {@link Collections#emptyMap()}</li>
     *         <li>if Validator.isNullOrEmpty(extractPropertyName),throw NullPointerException</li>
     *         <li>if Validator.isNullOrEmpty(includeKeys), then will extract map total keys</li>
     *         <li>抽取map value <code>T</code>的 <code>extractPropertyName</code>属性值,拼装成新的map返回</li>
     *         </ul>
     * @since 1.3.0
     */
    public static <K, O, V> Map<K, V> extractSubMap(Map<K, O> map,String extractPropertyName,Class<K> keysClass){
        return extractSubMap(map, null, extractPropertyName, keysClass);
    }

    /**
     * 抽取map value <code>T</code>的 <code>extractPropertyName</code>属性值,拼装成新的map返回.
     * 
     * <p>
     * 注意,在抽取的过程中, 如果 <code>map</code> 没有 某个 <code>includeKeys</code>,将会输出 warn log
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            Map<Long, User> map = new LinkedHashMap<Long, User>();
            map.put(1L, new User(1L));
            map.put(2L, new User(2L));
            map.put(53L, new User(3L));
            map.put(5L, new User(5L));
            map.put(6L, new User(6L));
            map.put(4L, new User(4L));
            Long[] includeKeys = }{ 5L, 4L };
     {@code       LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, includeKeys, "id", Long.class)));
    }
    
    返回:
     {
            "4": 4,
            "5": 5
        }
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
     *            the value type
     * @param map
     *            the map
     * @param includeKeys
     *            the include keys
     * @param extractPropertyName
     *            待提取的 {@code O} 的属性名称
     * @param keysClass
     *            map key 的class 类型
     * @return
     *         <ul>
     *         <li>if Validator.isNullOrEmpty(map),return {@link Collections#emptyMap()}</li>
     *         <li>if Validator.isNullOrEmpty(extractPropertyName),throw NullPointerException</li>
     *         <li>if Validator.isNullOrEmpty(includeKeys), then will extract map total keys</li>
     *         <li>抽取map value <code>T</code>的 <code>extractPropertyName</code>属性值,拼装成新的map返回</li>
     *         </ul>
     * @since 1.3.0
     */
    public static <K, O, V> Map<K, V> extractSubMap(Map<K, O> map,K[] includeKeys,String extractPropertyName,Class<K> keysClass){
        if (Validator.isNullOrEmpty(map)){
            return Collections.emptyMap();
        }

        Validate.notEmpty(extractPropertyName, "extractPropertyName can't be null/empty!");
        //如果excludeKeys是null ,那么抽取所有的key
        K[] useIncludeKeys = Validator.isNullOrEmpty(includeKeys) ? ConvertUtil.toArray(map.keySet(), keysClass) : includeKeys;

        Map<K, V> returnMap = new HashMap<K, V>();
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
     * <pre>
    {@code
            Map<String, Comparable> map = new HashMap<String, Comparable>();
    
            map.put("a", 123);
            map.put("c", 345);
            map.put("b", 8);
    
            LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyAsc(map)));
    }
    
    返回:
    {
            "a": 123,
            "b": 8,
            "c": 345
        }
     * 
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
     * @return if null==map,throw NullPointerException
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
     * <pre>
    {@code
            Map<String, Comparable> map = new HashMap<String, Comparable>();
    
            map.put("a", 123);
            map.put("c", 345);
            map.put("b", 8);
    
            LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyDesc(map)));
    }
    
    返回:
    {
            "c": 345,
            "b": 8,
            "a": 123
        }
     * 
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
     * @return if null==map,throw NullPointerException
     * @see ReverseComparator#ReverseComparator(Comparator)
     * @see PropertyComparator#PropertyComparator(String)
     * @since 1.2.0
     */
    public static <K, V> Map<K, V> sortByKeyDesc(Map<K, V> map){
        Validate.notNull(map, "map can't be null!");
        PropertyComparator<Entry<K, V>> propertyComparator = new PropertyComparator<Map.Entry<K, V>>("key");
        Comparator<Entry<K, V>> comparator = new ReverseComparator<Map.Entry<K, V>>(propertyComparator);
        return sort(map, comparator);
    }

    /**
     * 根据value 来顺序排序(asc).
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre>
    {@code
            Map<String, Comparable> map = new HashMap<String, Comparable>();
            map.put("a", 123);
            map.put("c", 345);
            map.put("b", 8);
            LOGGER.debug(JsonUtil.format(MapUtil.sortByValueAsc(map)));
    }
    
    返回:
    
    {
            "b": 8,
            "a": 123,
            "c": 345
        }
     * 
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
     * @return the map< k, v>
     * @see PropertyComparator#PropertyComparator(String)
     * @see java.util.Map.Entry
     * @see #sortByValueDesc(Map)
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
     * <pre>
    {@code
            Map<String, Comparable> map = new LinkedHashMap<String, Comparable>();
    
            map.put("a", 123);
            map.put("c", 345);
            map.put("b", 8);
    
            LOGGER.debug(JsonUtil.format(MapUtil.sortByValueDesc(map)));
    }
    
    返回:
    {
        "c": 345,
        "a": 123,
        "b": 8
    }
     * 
     * 
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
     * @return the map< k, v>
     * @see ReverseComparator#ReverseComparator(Comparator)
     * @see PropertyComparator#PropertyComparator(String)
     * @see java.util.Map.Entry
     * @see #sortByValueAsc(Map)
     * @since 1.2.0
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueDesc(Map<K, V> map){
        PropertyComparator<Entry<K, V>> propertyComparator = new PropertyComparator<Map.Entry<K, V>>("value");
        Comparator<Entry<K, V>> comparator = new ReverseComparator<Map.Entry<K, V>>(propertyComparator);
        return sort(map, comparator);
    }

    /**
     * 使用 基于 {@link java.util.Map.Entry} 的 <code>mapEntryComparator</code> 来对 <code>map</code>进行排序.
     * 
     * <p>
     * 由于是对{@link java.util.Map.Entry}排序的, 既可以按照key来排序,也可以按照value来排序哦
     * </p>
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @param mapEntryComparator
     *            基于 {@link java.util.Map.Entry} 的 {@link Comparator}
     * @return 排序之后的map
     * @since 1.2.0
     */
    public static <K, V> Map<K, V> sort(Map<K, V> map,Comparator<Map.Entry<K, V>> mapEntryComparator){
        Validate.notNull(map, "map can't be null!");
        Validate.notNull(mapEntryComparator, "mapEntryComparator can't be null!");

        final int size = map.size();
        List<Map.Entry<K, V>> mapEntryList = new ArrayList<Map.Entry<K, V>>(size);
        for (Map.Entry<K, V> entry : map.entrySet()){
            mapEntryList.add(entry);
        }

        //**********************排序************************************
        Collections.sort(mapEntryList, mapEntryComparator);

        //**********************************************************
        Map<K, V> returnMap = new LinkedHashMap<K, V>(size);

        for (Map.Entry<K, V> entry : mapEntryList){
            K key = entry.getKey();
            V value = entry.getValue();
            returnMap.put(key, value);
        }
        return returnMap;
    }
}
