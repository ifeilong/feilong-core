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

import static com.feilong.core.util.MapUtil.newHashMap;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

/**
 * The Class MapUtilGetTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetTest{

    /**
     * Test get index.
     */
    @Test
    public void testGetIndex(){
        Map<String, String> map = newLinkedHashMap();
        map.put("name", "jim");
        map.put("address", "shanghai");
        map.put("age", "18");

        Entry<String, String> entry = MapUtil.get(map, 2);

        assertEquals("age", entry.getKey());
        assertEquals("18", entry.getValue());
    }

    /**
     * Test get index more index.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexMoreIndex(){
        MapUtil.get(newHashMap(0), 2);
    }

    /**
     * Test get index 1.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndex1(){
        MapUtil.get(newHashMap(0), -1);
    }

    /**
     * Test get null map.
     */
    @Test(expected = NullPointerException.class)
    public void testGetNullMap(){
        MapUtil.get(null, 0);
    }
}
