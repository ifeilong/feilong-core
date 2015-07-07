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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.json.JsonUtil;

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
     * Test get sub map exclude keys.
     */
    @Test
    public void testGetSubMapExcludeKeys(){

        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("d", 3003);
        map.put("e", 3004);
        map.put("f", 3005);
        map.put("g", -1005);

        String[] keys = { "a", "g", "m" };
        Map<String, Integer> subMapExcludeKeys = MapUtil.getSubMapExcludeKeys(map, keys);

        LOGGER.debug(JsonUtil.format(subMapExcludeKeys));
    }

    /**
     * Test method for {@link com.feilong.core.util.MapUtil#getMinValue(java.util.Map, java.lang.String[])}.
     */
    @Test
    public void testGetMinValue(){

        Map<String, Integer> object = new HashMap<String, Integer>();

        object.put("a", 3007);
        object.put("b", 3001);
        object.put("c", 3002);
        object.put("d", 3003);
        object.put("e", 3004);
        object.put("f", 3005);
        object.put("g", -1005);

        String[] keys = { "a", "b", "d", "g", "m" };
        Integer minValue = MapUtil.getMinValue(object, keys);

        LOGGER.info(minValue + "");
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
        Map<String, Comparable> map = new LinkedHashMap<String, Comparable>();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);
        LOGGER.debug(JsonUtil.format(MapUtil.sortByValueAsc(map)));
    }

    /**
     * Test sort by value desc.
     */
    @Test
    public void testSortByValueDESC(){
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
        Map<String, Comparable> map = new LinkedHashMap<String, Comparable>();

        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyAsc(map)));
    }

    /**
     * Test sort by key desc.
     */
    @Test
    public void testSortByKeyDesc(){
        Map<String, Comparable> map = new LinkedHashMap<String, Comparable>();

        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        LOGGER.debug(JsonUtil.format(MapUtil.sortByKeyDesc(map)));
    }
}
