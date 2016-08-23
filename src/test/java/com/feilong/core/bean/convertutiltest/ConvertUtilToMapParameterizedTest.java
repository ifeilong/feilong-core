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
package com.feilong.core.bean.convertutiltest;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.tuple.Pair;
import org.hamcrest.Matchers;
import org.junit.Test;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;

/**
 * The Class ConvertUtilToMapParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ConvertUtilToMapParameterizedTest{

    //************com.feilong.core.bean.ConvertUtil.toMap(Entry<String, String>...)*****************************
    /**
     * Test to map.
     */
    @Test
    public void testToMap(){
        Map<String, String> map = toMap(//
                        Pair.of("张飞", "丈八蛇矛"),
                        Pair.of("关羽", "青龙偃月刀"),
                        Pair.of("赵云", "龙胆枪"),
                        Pair.of("刘备", "双股剑"));

        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));
    }

    /**
     * Test to map 11.
     */
    @Test
    public void testToMap11(){
        Map<String, String> map = toMap(toArray(
                        new SimpleEntry<>("张飞", "丈八蛇矛"),
                        new SimpleEntry<>("关羽", "青龙偃月刀"),
                        new SimpleEntry<>("赵云", "龙胆枪"),
                        new SimpleEntry<>("刘备", "双股剑")));
        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));
    }

    /**
     * Test to map with null element.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToMapWithNullElement(){
        toMap((Map.Entry<String, String>) null);
    }

    /**
     * Test to map with null element 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToMapWithNullElement1(){
        toMap(Pair.of("张飞", "丈八蛇矛"), Pair.of("关羽", "青龙偃月刀"), null, Pair.of("刘备", "双股剑"));
    }

    /**
     * Test to map null array 3.
     */
    @Test
    public void testToMapNullArray3(){
        assertEquals(emptyMap(), toMap((Map.Entry<String, String>[]) null));
    }

    /**
     * Test to map null array 2.
     */
    @Test
    public void testToMapNullArray2(){
        Map.Entry<String, String>[] entries = null;
        assertEquals(emptyMap(), toMap(entries));
    }

    /**
     * Test to map null array 1.
     */
    @Test
    public void testToMapNullArray1(){
        assertEquals(emptyMap(), toMap());
    }

    //******com.feilong.core.bean.ConvertUtil.toMap(Collection<SimpleEntry<String, String>>)**********************
    /**
     * Test to map3.
     */
    @Test
    public void testToMap3(){
        Map<String, String> map = toMap(toList(//
                        Pair.of("张飞", "丈八蛇矛"),
                        Pair.of("关羽", "青龙偃月刀"),
                        Pair.of("赵云", "龙胆枪"),
                        Pair.of("刘备", "双股剑")));
        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));
    }

    /**
     * Test to map 33.
     */
    @Test
    public void testToMap33(){
        Map<String, String> map = toMap(toList(
                        new SimpleEntry<>("张飞", "丈八蛇矛"),
                        new SimpleEntry<>("关羽", "青龙偃月刀"),
                        new SimpleEntry<>("赵云", "龙胆枪"),
                        new SimpleEntry<>("刘备", "双股剑")));
        assertThat(map, allOf(hasEntry("张飞", "丈八蛇矛"), hasEntry("关羽", "青龙偃月刀"), hasEntry("赵云", "龙胆枪"), hasEntry("刘备", "双股剑")));
    }

    /**
     * Test to map null collection.
     */
    @Test
    public void testToMapNullCollection(){
        assertEquals(emptyMap(), toMap((List<SimpleEntry<String, String>>) null));
    }

    /**
     * Test to map collection null element.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToMapCollectionNullElement(){
        toMap(toList(new SimpleEntry<>("张飞", "丈八蛇矛"), null, new SimpleEntry<>("赵云", "龙胆枪"), new SimpleEntry<>("刘备", "双股剑")));
    }

    //******************com.feilong.core.bean.ConvertUtil.toMap(String, String)*************************

    /**
     * Test to map1.
     */
    @Test
    public void testToMap1(){
        Map<String, String> map = toMap("张飞", "丈八蛇矛");
        assertThat(map, allOf(notNullValue(), hasEntry("张飞", "丈八蛇矛")));

    }

    /**
     * Test to map2.
     */
    @Test
    public void testToMap2(){
        Map<String, String> map = toMap(null, "丈八蛇矛");
        assertThat(map, allOf(notNullValue(), hasEntry(null, "丈八蛇矛")));
    }

    //*********************com.feilong.core.bean.ConvertUtil.toMap(Properties)*************

    /**
     * Test to properties.
     */
    @Test
    public void testToProperties(){
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("name", "feilong");
        map.put("age", 18);
        map.put("country", "china");

        Properties properties = org.apache.commons.collections4.MapUtils.toProperties(map);

        assertThat(toMap(properties), allOf(//
                        Matchers.<String, Object> hasEntry("name", "feilong"),
                        Matchers.<String, Object> hasEntry("age", 18),
                        Matchers.<String, Object> hasEntry("country", "china")));
    }

    /**
     * Test to properties 1.
     */
    @Test
    public void testToProperties1(){
        Properties properties = new Properties();

        properties.setProperty("name", "feilong");
        properties.setProperty("age", "18");
        properties.setProperty("country", "china");

        assertThat(toMap(properties), allOf(//
                        hasEntry("name", "feilong"),
                        hasEntry("age", "18"),
                        hasEntry("country", "china")));
    }

    /**
     * Test to map null properties.
     */
    @Test
    public void testToMapNullProperties(){
        toMap((Properties) null);
    }

    /**
     * Test to map empty properties.
     */
    @Test
    public void testToMapEmptyProperties(){
        assertEquals(emptyMap(), toMap(new Properties()));
    }
}
