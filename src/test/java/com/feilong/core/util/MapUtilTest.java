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

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
 * @author feilong
 * @version 1.0 Sep 8, 2012 8:55:30 PM
 */
public class MapUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MapUtilTest.class);

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
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3001);
        map.put("d", 3003);
        LOGGER.debug(JsonUtil.format(MapUtil.invertMap(map)));
    }

    @Test
    public void testGetSubMap(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3001);
        map.put("d", 3003);
        LOGGER.debug(JsonUtil.format(MapUtil.getSubMap(map, "a", "c")));
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
    @Test(expected = NullPointerException.class)
    public void testConstructSubMap1(){
        LOGGER.debug(JsonUtil.format(MapUtil.extractSubMap(null, "id", Long.class)));
    }

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testMapUtilTest1(){
        Map<String, String> map = new TreeMap<String, String>();
        map.put(null, "111");

        for (Map.Entry<String, String> entry : map.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            LOGGER.debug("key:{},value:{}", key, value);
        }

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

        LOGGER.info("" + MapUtil.getMinValue(map, "a", "b", "d", "g", "m"));
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
        Map<String, Comparable> map = new LinkedHashMap<String, Comparable>();

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
        Map<String, Comparable> map = new HashMap<String, Comparable>();

        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyAsc(map)));
    }

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
}
