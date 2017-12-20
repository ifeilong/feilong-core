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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * The Class ConvertUtilToMapTargetTypeClassTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToMapTargetTypeClassTest{

    /**
     * Test same class type.
     */
    @Test
    public void testSameClassType(){
        Map<String, String> map = toMap("1", "2");

        //key和value 都转成integer 使用相同的转换器
        Map<Integer, Integer> returnMap = toMap(map, Integer.class, Integer.class);

        assertThat(returnMap, allOf(hasEntry(1, 2)));
    }

    /**
     * Test array.
     */
    @Test
    public void testMap(){
        Map<String, String> map = toMap("1", "2,2");

        //key和value转成不同的类型
        Map<Integer, Integer[]> returnMap = toMap(map, Integer.class, Integer[].class);

        assertThat(returnMap, allOf(hasEntry(1, toArray(2, 2))));
    }

    @Test
    public void testMapNullKeyClass1(){
        Map<String, String> map = toMap("1", "2,2");

        //key和value转成不同的类型
        Map<String, Integer[]> returnMap = toMap(map, null, Integer[].class);

        assertThat(returnMap, allOf(hasEntry("1", toArray(2, 2))));
    }

    @Test
    public void testMapNullValueClass1(){
        Map<String, String> map = toMap("1", "2,2");

        //key和value转成不同的类型
        Map<Integer, String> returnMap = toMap(map, Integer.class, null);

        assertThat(returnMap, allOf(hasEntry(1, "2,2")));
    }

    /**
     * Test array to array.
     */
    @Test
    public void testMapToArray(){
        Map<String[], String[]> map = toMap(toArray("1"), toArray("2", "8"));

        //key和value转成不同的类型
        Map<Integer[], Long[]> returnMap = toMap(map, Integer[].class, Long[].class);

        assertThat(returnMap, allOf(hasEntry(toArray(1), toArray(2L, 8L))));
    }

    //---------------------------------------------------------------

    /**
     * Test array null key class.
     */
    @Test
    public void testMapNullKeyClass(){
        Map<String, String> map = toMap("1", "2,2");

        Map<String, Integer[]> returnMap = toMap(map, null, Integer[].class);

        assertThat(returnMap, allOf(hasEntry("1", toArray(2, 2))));
    }

    /**
     * Test array null value class.
     */
    @Test
    public void testMapNullValueClass(){
        Map<String, String> map = toMap("1", "2,2");

        Map<Integer, String> returnMap = toMap(map, Integer.class, null);

        assertThat(returnMap, allOf(hasEntry(1, "2,2")));
    }

    //---------------------------------------------------------------

    /**
     * Test null input map.
     */
    @Test
    public void testNullInputMap(){
        //key和value 都转成integer 使用相同的转换器
        Map<Integer, Integer> returnMap = toMap(null, Integer.class, Integer.class);
        assertEquals(emptyMap(), returnMap);
    }

    /**
     * Test empty input map.
     */
    @Test
    public void testEmptyInputMap(){
        //key和value 都转成integer 使用相同的转换器
        Map<Integer, Integer> returnMap = toMap(new HashMap<String, String>(), Integer.class, Integer.class);
        assertEquals(emptyMap(), returnMap);
    }
}
