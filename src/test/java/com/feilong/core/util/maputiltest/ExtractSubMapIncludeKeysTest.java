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
package com.feilong.core.util.maputiltest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.MapUtil;
import com.feilong.store.member.User;

/**
 * The Class MapUtilExtractSubMapIncludeKeysTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ExtractSubMapIncludeKeysTest{

    /**
     * Test extract sub map2.
     */
    @Test
    public void testExtractSubMap2(){
        Map<Long, User> map = newLinkedHashMap();
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

    /**
     * Test extract sub map null include keys.
     */
    @Test
    public void testExtractSubMapNullIncludeKeys(){
        Map<Long, User> map = newLinkedHashMap();
        map.put(5L, new User(500L));
        map.put(4L, new User(400L));

        Map<Long, Long> extractSubMap = MapUtil.extractSubMap(map, null, "id");
        assertThat(extractSubMap, allOf(hasEntry(5L, 500L), hasEntry(4L, 400L)));
    }

    /**
     * Test extract sub map empty include keys.
     */
    @Test
    public void testExtractSubMapEmptyIncludeKeys(){
        Map<Long, User> map = newLinkedHashMap();
        map.put(5L, new User(500L));
        map.put(4L, new User(400L));

        Map<Long, Long> extractSubMap = MapUtil.extractSubMap(map, ConvertUtil.<Long> toArray(), "id");
        assertThat(extractSubMap, allOf(hasEntry(5L, 500L), hasEntry(4L, 400L)));
    }

    /**
     * Test extract sub map null map.
     */
    @Test
    public void testExtractSubMapNullMap(){
        assertEquals(emptyMap(), MapUtil.extractSubMap(null, toArray(5L, 4L), "id"));
    }

    /**
     * Test extract sub map empty map.
     */
    @Test
    public void testExtractSubMapEmptyMap(){
        assertEquals(emptyMap(), MapUtil.extractSubMap(new HashMap<>(), toArray(5L, 4L), "id"));
    }

    /**
     * Test extract sub map null extract property name.
     */
    @Test(expected = NullPointerException.class)
    public void testExtractSubMapNullExtractPropertyName(){
        Map<Long, User> map = newLinkedHashMap();
        map.put(1L, new User(1L));
        map.put(2L, new User(2L));
        MapUtil.extractSubMap(map, toArray(5L, 4L), null);
    }

    /**
     * Test extract sub map empty extract property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExtractSubMapEmptyExtractPropertyName(){
        Map<Long, User> map = newLinkedHashMap();
        map.put(1L, new User(1L));
        map.put(2L, new User(2L));
        MapUtil.extractSubMap(map, toArray(5L, 4L), "");
    }

    /**
     * Test extract sub map empty extract property name 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testExtractSubMapEmptyExtractPropertyName1(){
        Map<Long, User> map = newLinkedHashMap();
        map.put(1L, new User(1L));
        map.put(2L, new User(2L));
        MapUtil.extractSubMap(map, toArray(5L, 4L), " ");
    }
}
