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

import static com.feilong.core.util.MapUtil.newConcurrentHashMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

public class NewConcurrentHashMapTest{

    @Test
    public void testNewConcurrentHashMapSize(){
        Map<Object, Object> newHashMap = MapUtil.newConcurrentHashMap(100);
        assertThat(newHashMap.size(), is(0));
    }

    @Test
    public void testNewConcurrentHashMapSize1(){
        Map<String, String> newHashMap = MapUtil.newConcurrentHashMap(3);
        newHashMap.put("name", "feilong");
        newHashMap.put("age", "18");
        newHashMap.put("address", "shanghai");

        assertThat(newHashMap.size(), is(3));
    }

    @Test
    public void testNewHashMap2333(){
        Map<String, String> map = MapUtil.newLinkedHashMap(3);
        map.put("name", "feilong");
        map.put("age", "18");
        map.put("address", "shanghai");

        Map<String, String> newHashMap1 = newConcurrentHashMap(map);
        assertThat(newHashMap1.size(), is(3));
    }

    /**
     * TestNewHashMapTest.
     */
    @Test(expected = NullPointerException.class)
    public void testNewHashMapTest(){
        newConcurrentHashMap(null);
    }

    //---------------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void testNewConcurrentHashMap1(){
        MapUtil.newConcurrentHashMap(-1);
    }
}
