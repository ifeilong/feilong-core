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
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toMapUseEntrys;

/**
 * The Class ConvertUtilToMapArrayTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToMapArrayTest{

    //************com.feilong.core.bean.ConvertUtil.toMap(Entry<String, String>...)*****************************
    /**
     * Test to map.
     */
    @Test
    public void testToMap(){
        Map<String, String> map = toMapUseEntrys(//
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
        Map<String, String> map = toMapUseEntrys(toArray(
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
        toMapUseEntrys((Map.Entry<String, String>) null);
    }

    /**
     * Test to map with null element 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToMapWithNullElement1(){
        toMapUseEntrys(Pair.of("张飞", "丈八蛇矛"), Pair.of("关羽", "青龙偃月刀"), null, Pair.of("刘备", "双股剑"));
    }

    /**
     * Test to map null array 3.
     */
    @Test
    public void testToMapNullArray3(){
        assertEquals(emptyMap(), toMapUseEntrys((Map.Entry<String, String>[]) null));
    }

    /**
     * Test to map null array 2.
     */
    @Test
    public void testToMapNullArray2(){
        Map.Entry<String, String>[] entries = null;
        assertEquals(emptyMap(), toMapUseEntrys(entries));
    }

    /**
     * Test to map null array 1.
     */
    @Test
    public void testToMapNullArray1(){
        assertEquals(emptyMap(), toMapUseEntrys());
    }
}
