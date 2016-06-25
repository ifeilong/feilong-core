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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections4.ComparatorUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
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

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MapUtilTest.class);

    /**
     * Test to single value map.
     */
    @Test
    public void testToSingleValueMap(){
        Map<String, String[]> keyAndArrayMap = new LinkedHashMap<String, String[]>();

        keyAndArrayMap.put("province", new String[] { "浙江省", "江苏省" });
        keyAndArrayMap.put("city", new String[] { "南通市" });

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
        LOGGER.debug(JsonUtil.format(map));
        LOGGER.debug(map.getKey());
        LOGGER.debug(map.getValue());
    }

    /**
     * Test invert map.
     */
    @Test
    public void testInvertMap(){
        Map<String, Integer> map = ConvertUtil.toMap(
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
        map.put(1L, new User(1L));
        map.put(2L, new User(2L));
        map.put(53L, new User(3L));
        map.put(5L, new User(5L));
        map.put(6L, new User(6L));
        map.put(4L, new User(4L));
        Long[] includeKeys = { 5L, 4L };
        LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, includeKeys, "id", Long.class)));

        Long[] includeKeys1 = { 5L, 4L };
        LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(map, includeKeys1, "userInfo.age", Long.class)));

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

        LOGGER.debug(JsonUtil.format(MapUtil.getSubMapExcludeKeys(map, "a", "g", "m")));
    }

    /**
     * Test get min value.
     */
    @Test
    public void testGetMinValue(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("d", 3003);
        map.put("e", 3004);
        map.put("f", 3005);
        map.put("g", -1005);

        LOGGER.debug("" + MapUtil.getMinValue(map, "a", "b", "d", "g", "m"));
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testMapUtilTest(){
        Map<String, String> object = new LinkedHashMap<String, String>();

        object.put("1", "1");
        object.put("2", "2");
        object.put("3", "3");
        object.put("3", "4");
        object.put("2", "7");
        object.put("3", "6");
        object.put("4", "8");

        LOGGER.debug(JsonUtil.format(object));
    }

    /**
     * Test linked hash map.
     */
    @Test
    public void testLinkedHashMap(){
        Map<String, String> object = new LinkedHashMap<String, String>();

        object.put("a", "123");
        object.put("b", "234");
        object.put("c", "345");
        object.put("b", "8910");

        //2
        //a
        //c
        //b 8910

        //a
        //b 8910
        //c
        LOGGER.debug(JsonUtil.format(object));
    }

    /**
     * Test sort by value asc.
     */
    @Test
    public void testSortByValueASC(){
        Map<String, Comparable> map = new HashMap<String, Comparable>();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);
        LOGGER.debug(JsonUtil.format(MapUtil.sortByValueAsc(map)));
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

        LOGGER.debug(JsonUtil.format(MapUtil.sortByValueDesc(map)));
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

        LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyAsc(map)));
    }

    /**
     * Test sort.
     */
    @Test
    public void testSort(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a13", 123);
        map.put("a2", 345);
        map.put("a8", 8);

        LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyAsc(map)));

        PropertyComparator<Entry<String, Integer>> propertyComparator = new PropertyComparator<Map.Entry<String, Integer>>(
                        "key",
                        new RegexGroupNumberComparator("a(\\d*)"));
        LOGGER.debug(JsonUtil.format(MapUtil.sort(map, propertyComparator)));
    }

    /**
     * Test sort by key desc.
     */
    @Test
    public void testSortByKeyDesc(){
        Map<String, Comparable> map = new HashMap<String, Comparable>();

        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyDesc(map)));
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
