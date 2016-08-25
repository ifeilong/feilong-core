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
package com.feilong.core.util.aggregateutiltest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.AggregateUtil;

public class AggregateUtilGetMinValueTest{
    //*************************************************************************************************************

    /**
     * Test get min value.
     */
    @Test
    public void testGetMinValue(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("d", 3003);
        map.put("e", 3004);
        map.put("f", 3005);
        map.put("g", -1005);

        assertThat(AggregateUtil.getMinValue(map, "a", "b", "d", "g", "m"), is(-1005));
    }

    @Test
    public void testGetMinValueNullMap(){
        assertEquals(null, AggregateUtil.getMinValue(null, "a", "b", "d", "g", "m"));
    }

    @Test
    public void testGetMinValueEmptyMap(){
        assertEquals(null, AggregateUtil.getMinValue(new HashMap<String, Integer>(), "a", "b", "d", "g", "m"));
    }

    @Test
    public void testGetMinValueEmptyKeys(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("d", 3003);
        map.put("e", 3004);
        map.put("f", 3005);

        assertThat(AggregateUtil.getMinValue(map), is(3001));
    }

    @Test
    public void testGetMinValueNullKeys1(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("d", 3003);
        map.put("e", 3004);
        map.put("f", 3005);

        assertThat(AggregateUtil.getMinValue(map, null), is(3001));
    }

    @Test
    public void testGetMinValueEmptyKeys1(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("d", 3003);
        map.put("e", 3004);
        map.put("f", 3005);

        assertThat(AggregateUtil.getMinValue(map, new String[] {}), is(3001));
    }

    @Test
    public void testGetMinValueNotExistKey(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("d", 3003);
        map.put("e", 3004);
        map.put("f", 3005);

        assertEquals(null, AggregateUtil.getMinValue(map, "c1"));
    }
}
