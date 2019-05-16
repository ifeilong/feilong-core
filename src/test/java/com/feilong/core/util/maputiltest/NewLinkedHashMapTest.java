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

import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

public class NewLinkedHashMapTest{

    /**
     * Test new linked hash map.
     */
    @Test
    public void testNewLinkedHashMap(){
        Map<Object, Object> map = MapUtil.newLinkedHashMap(100);
        assertThat(map.size(), is(0));
    }

    /**
     * Test new linked hash map 2.
     */
    @Test
    public void testNewLinkedHashMap2(){
        Map<String, String> map = MapUtil.newLinkedHashMap(3);
        map.put("name", "feilong");
        map.put("age", "18");
        map.put("address", "shanghai");

        assertThat(map.size(), is(3));
    }

    @Test
    public void testNewHashMap2333(){
        Map<String, String> map = MapUtil.newLinkedHashMap(3);
        map.put("name", "feilong");
        map.put("age", "18");
        map.put("address", "shanghai");

        Map<String, String> newHashMap1 = newLinkedHashMap(map);
        assertThat(newHashMap1.size(), is(3));
    }

    /**
     * TestNewHashMapTest.
     */
    @Test(expected = NullPointerException.class)
    public void testNewHashMapTest(){
        newLinkedHashMap(null);
    }
    //---------------------------------------------------------------

    /**
     * Test new linked hash map 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNewLinkedHashMap1(){
        MapUtil.newLinkedHashMap(-1);
    }
}
