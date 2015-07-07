/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.comparator.PropertyComparator;

/**
 * {@link Map}工具类.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0.0 Sep 8, 2012 8:02:44 PM
 * @see org.apache.commons.collections.MapUtils
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
     * 取到指定keys的value,连接起来,如果value是null or empty将会被排除,不参与拼接.
     *
     * @param map
     *            the map
     * @param includeKeys
     *            包含的key
     * @return the mer data
     * @since 1.2.1
     */
    public static String joinKeysValue(Map<String, String> map,String[] includeKeys){
        if (Validator.isNullOrEmpty(map)){
            throw new NullPointerException("map can't be null/empty!");
        }
        StringBuilder sb = new StringBuilder();
        //有顺序的参数
        for (String key : includeKeys){
            String value = map.get(key);

            //不判断的话,null会输出成null字符串
            if (Validator.isNotNullOrEmpty(value)){
                sb.append(value);
            }
        }
        String merData = sb.toString();
        return merData;
    }

    /**
     * 指定一个map,指定特定的keys,取得其中的 value 最小值.
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
     */
    @SuppressWarnings("unchecked")
    public static <K, T extends Number> T getMinValue(Map<K, T> map,K[] keys){
        Map<K, T> subMap = getSubMap(map, keys);

        if (null != subMap){
            Collection<T> values = subMap.values();
            Object[] array = values.toArray();
            // 冒泡排序
            Algorithm.bubbleSort(array, false);
            return (T) array[0];
        }
        return null;
    }

    /**
     * 获得 一个map 中的 按照指定的key 整理成新的map.
     *
     * @param <K>
     *            the key type
     * @param <T>
     *            the generic type
     * @param map
     *            the map
     * @param keys
     *            指定key,如果key 不在map key 里面 ,则返回的map 中忽略该key
     * @return the sub map<br>
     *         if (Validator.isNullOrEmpty(keys)) 返回map<br>
     * @throws NullPointerException
     *             if (Validator.isNullOrEmpty(map))
     */
    public static <K, T> Map<K, T> getSubMap(Map<K, T> map,K[] keys) throws NullPointerException{
        if (Validator.isNullOrEmpty(map)){
            throw new NullPointerException("the map is null or empty!");
        }
        if (Validator.isNullOrEmpty(keys)){
            return map;
        }
        Map<K, T> returnMap = new HashMap<K, T>();

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
     * @param <K>
     *            the key type
     * @param <T>
     *            the generic type
     * @param map
     *            the map
     * @param excludeKeys
     *            the keys
     * @return the sub map<br>
     *         if (Validator.isNullOrEmpty(keys)) 返回map<br>
     * @throws NullPointerException
     *             if (Validator.isNullOrEmpty(map))
     * @since 1.0.9
     */
    public static <K, T> Map<K, T> getSubMapExcludeKeys(Map<K, T> map,K[] excludeKeys) throws NullPointerException{
        if (Validator.isNullOrEmpty(map)){
            throw new NullPointerException("the map is null or empty!");
        }
        if (Validator.isNullOrEmpty(excludeKeys)){
            return map;
        }

        Map<K, T> returnMap = new HashMap<K, T>(map);

        for (K key : excludeKeys){
            if (map.containsKey(key)){
                returnMap.remove(key);
            }else{
                LOGGER.warn("map don't contains key:[{}]", key);
            }
        }
        return returnMap;
    }

    //*******************************排序****************************************************
    /**
     * Sort by key asc.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return the map< k, v>
     * @throws NullPointerException
     *             the null pointer exception
     * @see java.util.TreeMap#TreeMap(Map)
     * @since 1.2.0
     */
    public static <K, V> Map<K, V> sortByKeyAsc(Map<K, V> map) throws NullPointerException{
        if (Validator.isNullOrEmpty(map)){
            throw new NullPointerException("map can't be null/empty!");
        }
        return new TreeMap<K, V>(map);
    }

    /**
     * Sort by key desc.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return the map< k, v>
     * @throws NullPointerException
     *             the null pointer exception
     * @see org.apache.commons.collections.comparators.ReverseComparator#ReverseComparator(Comparator)
     * @see PropertyComparator#PropertyComparator(String)
     * @since 1.2.0
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> sortByKeyDesc(Map<K, V> map) throws NullPointerException{
        if (Validator.isNullOrEmpty(map)){
            throw new NullPointerException("map can't be null/empty!");
        }
        return sort(map, new ReverseComparator(new PropertyComparator<Map.Entry<K, V>>("key")));
    }

    /**
     * 根据value 来顺序排序（asc）.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return the map< k, v>
     * @throws NullPointerException
     *             if Validator.isNullOrEmpty(map) or if Validator.isNullOrEmpty(mapEntryComparator)
     * @see PropertyComparator#PropertyComparator(String)
     * @see java.util.Map.Entry
     * @see #sortByValueDesc(Map)
     * @since 1.2.0
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueAsc(Map<K, V> map) throws NullPointerException{
        return sort(map, new PropertyComparator<Map.Entry<K, V>>("value"));
    }

    /**
     * 根据value 来倒序排序（desc）.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param map
     *            the map
     * @return the map< k, v>
     * @throws NullPointerException
     *             if Validator.isNullOrEmpty(map) or if Validator.isNullOrEmpty(mapEntryComparator)
     * @see org.apache.commons.collections.comparators.ReverseComparator#ReverseComparator(Comparator)
     * @see PropertyComparator#PropertyComparator(String)
     * @see java.util.Map.Entry
     * @see #sortByValueAsc(Map)
     * @since 1.2.0
     */
    @SuppressWarnings("unchecked")
    public static <K, V extends Comparable<V>> Map<K, V> sortByValueDesc(Map<K, V> map) throws NullPointerException{
        return sort(map, new ReverseComparator(new PropertyComparator<Map.Entry<K, V>>("value")));
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
     * @throws NullPointerException
     *             if Validator.isNullOrEmpty(map) or if Validator.isNullOrEmpty(mapEntryComparator)
     * @since 1.2.0
     */
    public static <K, V> Map<K, V> sort(Map<K, V> map,Comparator<Map.Entry<K, V>> mapEntryComparator) throws NullPointerException{

        if (Validator.isNullOrEmpty(map)){
            throw new NullPointerException("the map is null or empty!");
        }

        if (Validator.isNullOrEmpty(mapEntryComparator)){
            throw new NullPointerException("mapEntryComparator is null or empty!");
        }

        //**********************************************************

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
