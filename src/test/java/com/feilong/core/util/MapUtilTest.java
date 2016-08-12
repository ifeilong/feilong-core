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

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.ComparatorUtils;
import org.junit.Test;

import com.feilong.test.User;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;

/**
 * The Class MapUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class MapUtilTest{

    /**
     * Test put multi value.
     */
    @Test
    public void testPutMultiValue(){
        Map<String, List<String>> mutiMap = newLinkedHashMap(2);
        MapUtil.putMultiValue(mutiMap, "name", "张飞");
        MapUtil.putMultiValue(mutiMap, "name", "关羽");
        MapUtil.putMultiValue(mutiMap, "age", "30");

        assertThat(mutiMap, allOf(hasEntry("name", toList("张飞", "关羽")), hasEntry("age", toList("30"))));
    }

    /**
     * Test remove keys.
     */
    @Test
    public void testRemoveKeys(){
        Map<String, String> map = newLinkedHashMap(3);
        map.put("name", "feilong");
        map.put("age", "18");
        map.put("country", "china");

        Map<String, String> removeKeys = MapUtil.removeKeys(map, "country");
        assertThat(removeKeys, allOf(hasEntry("name", "feilong"), hasEntry("age", "18"), not(hasEntry("country", "china"))));
    }

    /**
     * Test to single value map.
     */
    @Test
    public void testToSingleValueMap(){
        Map<String, String[]> arrayValueMap = newLinkedHashMap(2);
        arrayValueMap.put("province", toArray("浙江省", "江苏省"));
        arrayValueMap.put("city", toArray("南通市"));

        Map<String, String> singleValueMap = MapUtil.toSingleValueMap(arrayValueMap);
        assertThat(singleValueMap, allOf(hasEntry("province", "浙江省"), hasEntry("city", "南通市")));
    }

    @Test
    public void testToSingleValueMap1(){
        Map<String, String[]> arrayValueMap = newLinkedHashMap(2);
        arrayValueMap.put("province", null);
        arrayValueMap.put("city", toArray("南通市"));

        Map<String, String> singleValueMap = MapUtil.toSingleValueMap(arrayValueMap);
        assertThat(singleValueMap, allOf(hasEntry("province", null), hasEntry("city", "南通市")));
    }

    /**
     * Test to array value map.
     */
    @Test
    public void testToArrayValueMap(){
        Map<String, String> singleValueMap = newLinkedHashMap(2);
        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        Map<String, String[]> arrayValueMap = MapUtil.toArrayValueMap(singleValueMap);
        assertThat(arrayValueMap, allOf(hasEntry("province", toArray("江苏省")), hasEntry("city", toArray("南通市"))));
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

    //*********************************************************************************************
    @Test
    public void testExtractSubMap(){
        assertEquals(emptyMap(), MapUtil.extractSubMap(null, "id"));
    }

    @Test(expected = NullPointerException.class)
    public void testExtractSubMap1(){
        Map<Long, User> map = new LinkedHashMap<Long, User>();
        map.put(1L, new User(1L));
        map.put(2L, new User(2L));
        MapUtil.extractSubMap(map, null);
    }

    /**
     * Test extract sub map2.
     */
    @Test
    public void testExtractSubMap2(){
        Map<Long, User> map = new LinkedHashMap<Long, User>();
        map.put(1L, new User(100L));
        map.put(2L, new User(200L));
        map.put(53L, new User(300L));
        map.put(5L, new User(500L));
        map.put(6L, new User(600L));
        map.put(4L, new User(400L));

        Map<Long, Long> extractSubMap = MapUtil.extractSubMap(map, toArray(5L, 4L), "id");
        assertThat(extractSubMap, allOf(hasEntry(5L, 500L), hasEntry(4L, 400L)));

        Map<Long, Long> extractSubMap2 = MapUtil.extractSubMap(map, toArray(5L, 4L), "userInfo.age");
        assertThat(extractSubMap2, allOf(hasEntry(5L, null), hasEntry(4L, null)));

    }

    @Test
    public void testExtractSubMap3(){
        Map<Long, User> map = new LinkedHashMap<Long, User>();
        map.put(1L, new User(100L));
        map.put(2L, new User(200L));
        map.put(5L, new User(500L));
        map.put(4L, new User(400L));

        Map<Long, Long> extractSubMap = MapUtil.extractSubMap(map, "id");
        assertThat(extractSubMap, allOf(hasEntry(1L, 100L), hasEntry(2L, 200L), hasEntry(5L, 500L), hasEntry(4L, 400L)));
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

    //******************com.feilong.core.util.MapUtil.newHashMap(int)*****************************************

    /**
     * TestMapUtilTest.
     */
    @Test
    public void testNewHashMap(){
        Map<Object, Object> newHashMap = MapUtil.newHashMap(100);
        assertThat(newHashMap.size(), is(0));
    }

}
