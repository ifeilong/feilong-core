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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.ComparatorUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.comparator.PropertyComparator;
import com.feilong.core.util.comparator.RegexGroupNumberComparator;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class MapUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class MapUtilTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(MapUtilTest.class);

    @Test
    public void testPutMultiValue(){
        Map<String, List<String>> mutiMap = MapUtil.newLinkedHashMap(2);
        MapUtil.putMultiValue(mutiMap, "name", "张飞");
        MapUtil.putMultiValue(mutiMap, "name", "关羽");
        MapUtil.putMultiValue(mutiMap, "age", "30");

        LOGGER.debug(JsonUtil.format(mutiMap));
    }

    @Test
    public void testRemoveKeys(){
        Map<String, String> map = MapUtil.newLinkedHashMap(3);

        map.put("name", "feilong");
        map.put("age", "18");
        map.put("country", "china");

        LOGGER.debug(JsonUtil.format(MapUtil.removeKeys(map, "country")));
    }

    /**
     * Test to single value map.
     */
    @Test
    public void testToSingleValueMap(){
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();
        keyAndArrayMap.put("province", toArray("浙江省", "江苏省"));
        keyAndArrayMap.put("city", toArray("南通市"));

        Map<String, String> singleValueMap = MapUtil.toSingleValueMap(keyAndArrayMap);
        assertThat(singleValueMap, allOf(hasEntry("province", "浙江省"), hasEntry("city", "南通市")));
    }

    /**
     * Test to array value map.
     */
    @Test
    public void testToArrayValueMap(){
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();
        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        Map<String, String[]> arrayValueMap = MapUtil.toArrayValueMap(singleValueMap);
        LOGGER.debug(JsonUtil.format(arrayValueMap));
    }

    /**
     * Test put sum value.
     */
    @Test
    public void testPutSumValue(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        MapUtil.putSumValue(map, "1000001", 5);
        MapUtil.putSumValue(map, "1000002", 5);
        MapUtil.putSumValue(map, "1000002", 5);

        assertThat(map, allOf(hasEntry("1000001", 5), hasEntry("1000002", 10)));
    }

    /**
     * Test simple entry.
     */
    @Test
    public void testSimpleEntry(){
        SimpleEntry<String, String> map = new SimpleEntry<String, String>("name", "jinxin");
        assertThat(map, allOf(hasProperty("key"), hasProperty("value")));
        assertThat(map.getKey(), is(equalTo("name")));
        assertThat(map.getValue(), is(equalTo("jinxin")));
    }

    /**
     * Test invert map.
     */
    @Test
    public void testInvertMap(){
        Map<String, Integer> map = toMap(
                        new SimpleEntry<>("a", 3007),
                        new SimpleEntry<>("b", 3001),
                        new SimpleEntry<>("c", 3001),
                        new SimpleEntry<>("d", 3003));
        Map<Integer, String> invertMap = MapUtil.invertMap(map);
        assertThat(invertMap, allOf(hasEntry(3007, "a"), hasEntry(3001, "c"), hasEntry(3003, "d"), not(hasEntry(3001, "b"))));
    }

    /**
     * Test get sub map.
     */
    @Test
    public void testGetSubMap(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3001);
        map.put("d", 3003);
        Map<String, Integer> subMap = MapUtil.getSubMap(map, "a", "c");
        assertThat(subMap, allOf(hasEntry("a", 3007), hasEntry("c", 3001), not(hasKey("b")), not(hasKey("d"))));
    }

    /**
     * Test construct sub map.
     */
    @Test
    public void testConstructSubMap(){
        Map<Long, User> map = new LinkedHashMap<Long, User>();
        map.put(1L, new User(100L));
        map.put(2L, new User(200L));
        map.put(53L, new User(300L));
        map.put(5L, new User(500L));
        map.put(6L, new User(600L));
        map.put(4L, new User(400L));

        LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, toArray(5L, 4L), "id", Long.class)));
        LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, toArray(5L, 4L), "userInfo.age", Long.class)));
        LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, "id", Long.class)));

    }

    /**
     * Test construct sub map1.
     */
    @Test
    public void testExtractSubMap(){
        assertEquals(Collections.emptyMap(), MapUtil.extractSubMap(null, "id", Long.class));
    }

    /**
     * Test extract sub map1.
     */
    @Test(expected = NullPointerException.class)
    public void testExtractSubMap1(){
        Map<Long, User> map = new LinkedHashMap<Long, User>();
        map.put(1L, new User(1L));
        map.put(2L, new User(2L));
        MapUtil.extractSubMap(map, "id", null);
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testMapUtilTest1(){
        Comparator<String> naturalComparator = ComparatorUtils.naturalComparator();
        Comparator<String> nullLowComparator = ComparatorUtils.nullLowComparator(naturalComparator);
        Map<String, String> map = new TreeMap<String, String>(nullLowComparator);
        map.put(null, "111");

        assertThat(map, hasEntry(null, "111"));

    }

    /**
     * Test map util test3.
     */
    @Test(expected = NullPointerException.class)
    public void testMapUtilTest3(){
        Map<String, String> map = new TreeMap<String, String>();
        map.put(null, "111");
    }

    /**
     * Test get sub map exclude keys.
     */
    @Test
    public void testGetSubMapExcludeKeys(){
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();

        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("g", -1005);

        Map<String, Integer> subMapExcludeKeys = MapUtil.getSubMapExcludeKeys(map, "a", "g", "m");
        assertThat(subMapExcludeKeys, allOf(hasEntry("b", 3001), hasEntry("c", 3002), not(hasKey("a")), not(hasKey("g"))));
    }

    /**
     * Test sort by value asc.
     */
    @Test
    public void testSortByValueASC(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);
        Map<String, Integer> sortByValueAsc = MapUtil.sortByValueAsc(map);
        assertThat(sortByValueAsc.keySet(), contains("b", "a", "c"));
    }

    /**
     * Test sort by value desc.
     */
    @Test
    public void testSortByValueDesc(){
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByValueDesc = MapUtil.sortByValueDesc(map);
        assertThat(sortByValueDesc.keySet(), contains("c", "a", "b"));
    }

    /**
     * Test sort by key asc.
     */
    @Test
    public void testSortByKeyAsc(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByKeyAsc = MapUtil.sortByKeyAsc(map);
        assertThat(sortByKeyAsc.keySet(), contains("a", "b", "c"));
    }

    /**
     * Test sort.
     */
    @Test
    public void testSort(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a8", 8);
        map.put("a13", 123);
        map.put("a2", 345);

        Map<String, Integer> sortByKeyAsc = MapUtil.sortByKeyAsc(map);
        assertThat(sortByKeyAsc.keySet(), contains("a13", "a2", "a8"));

        Map<String, Integer> sort = MapUtil
                        .sort(map, new PropertyComparator<Map.Entry<String, Integer>>("key", new RegexGroupNumberComparator("a(\\d*)")));
        assertThat(sort.keySet(), contains("a2", "a8", "a13"));
    }

    /**
     * Test sort by key desc.
     */
    @Test
    public void testSortByKeyDesc(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByKeyDesc = MapUtil.sortByKeyDesc(map);
        assertThat(sortByKeyDesc.keySet(), contains("c", "b", "a"));
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testMapUtilTest2(){
        LOGGER.debug("{}", Integer.highestOneBit((125 - 1) << 1));

        for (int i = 0, j = 10; i < j; ++i){
            LOGGER.debug("{},{},{}", i, capacity(i), (int) (i / 0.75f) + 1);
        }
    }

    /**
     * Returns a capacity that is sufficient to keep the map from being resized as
     * long as it grows no larger than expectedSize and the load factor is >= its
     * default (0.75).
     *
     * @param expectedSize
     *            the expected size
     * @return the int
     */
    static int capacity(int expectedSize){
        if (expectedSize < 3){
            return expectedSize + 1;
        }
        if (expectedSize < 1073741824){
            // This is the calculation used in JDK8 to resize when a putAll happens; 
            //it seems to be the most conservative calculation we can make.  
            //0.75 is the default load factor.
            return (int) (expectedSize / 0.75F + 1.0F);
        }
        return Integer.MAX_VALUE; // any large value
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testNewHashMap(){
        Map<Object, Object> newHashMap = MapUtil.newHashMap(100);
        assertThat(newHashMap.size(), is(0));
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testNewHashMap1(){
        Map<Integer, Integer> map = MapUtil.newHashMap(100);
        for (int i = 0, j = 100; i < j; ++i){
            map.put(i, i);
        }

        //  Map<Object, Object> newHashMap = MapUtil.newHashMap(map.size());
        Map<Object, Object> newHashMap = new HashMap<>(256);
        newHashMap.putAll(map);
        LOGGER.debug("{}", newHashMap.size());
    }
}
