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
 * The Class MapUtilToArrayValueMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToArrayValueMapTest{

    /**
     * Test to array value map null.
     */
    //*******************com.feilong.core.util.MapUtil.toArrayValueMap(Map<String, String>)************************************************************
    @Test
    public void testToArrayValueMapNull(){
        assertEquals(emptyMap(), MapUtil.toArrayValueMap(null));
    }

    /**
     * Test to array value map empty.
     */
    @Test
    public void testToArrayValueMapEmpty(){
        assertEquals(emptyMap(), MapUtil.toArrayValueMap(new HashMap<String, String>()));
    }

    /**
     * Test to array value map.
     */
    @Test
    public void testToArrayValueMap(){
        Map<String, String> singleValueMap = newLinkedHashMap(2);
        singleValueMap.put("province", "江苏省");
        singleValueMap.put("city", "南通市");

        Map<String, String[]> arrayValueMap = MapUtil.toArrayValueMap(singleValueMap);
        assertThat(arrayValueMap, allOf(hasEntry("province", toArray("江苏省")), hasEntry("city", toArray("南通市"))));
    }

    /**
     * Test to array value map 1.
     */
    @Test
    public void testToArrayValueMap1(){
        Map<String, String> singleValueMap = newLinkedHashMap(2);
        singleValueMap.put("province", null);
        singleValueMap.put("city", "南通市");

        Map<String, String[]> arrayValueMap = MapUtil.toArrayValueMap(singleValueMap);
        assertThat(arrayValueMap, allOf(hasEntry("province", toArray((String) null)), hasEntry("city", toArray("南通市"))));
    }

}
