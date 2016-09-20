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
package com.feilong.tools.jsonlib;

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

/**
 * The Class JsonUtilToMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JsonUtilToMapTest{

    @Test
    public void testToMapOrder(){
        Map<String, String> map = JsonUtil.toMap("{'brandCode':'UA','name':'feilong','age':'18','type':'1'}");
        assertThat(map, allOf(//
                        hasEntry("brandCode", "UA"),
                        hasEntry("name", "feilong"),
                        hasEntry("age", "18"),
                        hasEntry("type", "1")));

        assertThat(map.keySet(), contains("brandCode", "name", "age", "type"));

    }

    /**
     * To map 12.
     */
    @Test
    public void toMap12(){
        Map<String, String> map = JsonUtil.toMap("{'brandCode':'UA'}");
        assertThat(map, allOf(hasEntry("brandCode", "UA")));

        Map<String, Integer> map2 = JsonUtil.toMap("{'brandCode':55555}");
        assertThat(map2, allOf(hasEntry("brandCode", 55555)));
    }

    /**
     * Test to map 122.
     */
    @Test
    public void testToMap122(){
        //泛型擦除
        Map<String, Long> map3 = JsonUtil.toMap("{'brandCode':55.555}");
        assertThat(map3, allOf(hasEntry("brandCode", (Object) 55.555)));
    }

    /**
     * Test to map null json.
     */
    @Test
    public void testToMapNullJson(){
        assertEquals(emptyMap(), JsonUtil.toMap(null));
    }

    /**
     * Test to map empty json.
     */
    @Test
    public void testToMapEmptyJson(){
        assertEquals(emptyMap(), JsonUtil.toMap(""));
    }

    /**
     * Test to map blank json.
     */
    @Test
    public void testToMapBlankJson(){
        assertEquals(emptyMap(), JsonUtil.toMap(" "));
    }

    /**
     * Test to map blank json 1.
     */
    @Test
    public void testToMapBlankJson1(){
        assertEquals(emptyMap(), JsonUtil.toMap("{}"));
    }
}
