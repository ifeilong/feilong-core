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

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;

/**
 * The Class MapUtilToSingleValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToSingleValueMapTest{

    /**
     * Test to single value map null.
     */
    //*****************com.feilong.core.util.MapUtil.toSingleValueMap(Map<String, String[]>)*************************************************
    @Test
    public void testToSingleValueMapNull(){
        assertEquals(emptyMap(), MapUtil.toSingleValueMap(null));
    }

    /**
     * Test to single value map empty.
     */
    @Test
    public void testToSingleValueMapEmpty(){
        assertEquals(emptyMap(), MapUtil.toSingleValueMap(new HashMap<String, String[]>()));
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

    /**
     * Test to single value map 1.
     */
    @Test
    public void testToSingleValueMap1(){
        Map<String, String[]> arrayValueMap = newLinkedHashMap(2);
        arrayValueMap.put("province", null);
        arrayValueMap.put("city", toArray("南通市"));

        Map<String, String> singleValueMap = MapUtil.toSingleValueMap(arrayValueMap);
        assertThat(singleValueMap, allOf(hasEntry("province", null), hasEntry("city", "南通市")));
    }
}
