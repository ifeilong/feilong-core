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

import org.apache.commons.collections4.Transformer;
import org.junit.Test;

import com.feilong.core.util.transformer.SimpleClassTransformer;

/**
 * The Class ConvertUtilToMapTransformerTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToMapTransformerTest{

    /**
     * Test same transformer.
     */
    @Test
    public void testSameTransformer(){
        Map<String, String> map = toMap("1", "2");

        Transformer<String, Integer> transformer = new SimpleClassTransformer<>(Integer.class);

        //key和value 都转成integer 使用相同的转换器
        Map<Integer, Integer> returnMap = toMap(map, transformer, transformer);

        assertThat(returnMap, allOf(hasEntry(1, 2)));
    }

    /**
     * Test array.
     */
    @Test
    public void testMap(){
        Map<String, String> map = toMap("1", "2,2");

        Transformer<String, Integer> keyTransformer = new SimpleClassTransformer<>(Integer.class);
        Transformer<String, Integer[]> valueTransformer = new SimpleClassTransformer<>(Integer[].class);

        //key和value转成不同的类型
        Map<Integer, Integer[]> returnMap = toMap(map, keyTransformer, valueTransformer);

        assertThat(returnMap, allOf(hasEntry(1, toArray(2, 2))));
    }

    /**
     * Test array to array.
     */
    @Test
    public void testMapToArray(){
        Map<String[], String[]> map = toMap(toArray("1"), toArray("2", "8"));

        Transformer<String[], Integer[]> keyTransformer = new SimpleClassTransformer<>(Integer[].class);
        Transformer<String[], Long[]> valueTransformer = new SimpleClassTransformer<>(Long[].class);

        //key和value转成不同的类型
        Map<Integer[], Long[]> returnMap = toMap(map, keyTransformer, valueTransformer);

        assertThat(returnMap, allOf(hasEntry(toArray(1), toArray(2L, 8L))));
    }

    //---------------------------------------------------------------

    /**
     * Test array null key transformer.
     */
    @Test
    public void testMapNullKeyTransformer(){
        Map<String, String> map = toMap("1", "2,2");

        Transformer<String, Integer[]> valueTransformer = new SimpleClassTransformer<>(Integer[].class);
        Map<String, Integer[]> returnMap = toMap(map, null, valueTransformer);

        assertThat(returnMap, allOf(hasEntry("1", toArray(2, 2))));
    }

    /**
     * Test array null value transformer.
     */
    @Test
    public void testMapNullValueTransformer(){
        Map<String, String> map = toMap("1", "2,2");

        Transformer<String, Integer> keyTransformer = new SimpleClassTransformer<>(Integer.class);
        Map<Integer, String> returnMap = toMap(map, keyTransformer, null);

        assertThat(returnMap, allOf(hasEntry(1, "2,2")));
    }

    /**
     * Test transformer null target type.
     */
    @Test(expected = NullPointerException.class)
    public void testTransformerNullTargetType(){
        Map<String, String> map = toMap("1", "2,2");

        Transformer<String, Integer> keyTransformer = new SimpleClassTransformer<>(null);
        Map<Integer, String> returnMap = toMap(map, keyTransformer, null);

        assertThat(returnMap, allOf(hasEntry(1, "2,2")));
    }

    //---------------------------------------------------------------

    /**
     * Test null input map.
     */
    @Test
    public void testNullInputMap(){
        Transformer<String, Integer> transformer = new SimpleClassTransformer<>(Integer.class);

        //key和value 都转成integer 使用相同的转换器
        Map<Integer, Integer> returnMap = toMap(null, transformer, transformer);

        assertEquals(emptyMap(), returnMap);
    }

    /**
     * Test empty input map.
     */
    @Test
    public void testEmptyInputMap(){
        Transformer<String, Integer> transformer = new SimpleClassTransformer<>(Integer.class);

        //key和value 都转成integer 使用相同的转换器
        Map<Integer, Integer> returnMap = toMap(new HashMap<String, String>(), transformer, transformer);

        assertEquals(emptyMap(), returnMap);
    }
}
