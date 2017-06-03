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

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

import static com.feilong.core.util.MapUtil.newLinkedHashMap;

/**
 * The Class MapUtilRemoveKeysTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class RemoveKeysTest{

    /**
     * Test remove keys.
     */
    @Test
    public void testRemoveKeys(){
        Map<String, String> map = newLinkedHashMap(3);
        map.put("name", "feilong");
        map.put("age", "18");
        map.put("country", "china");

        Map<String, String> removeKeys = MapUtil.removeKeys(map, "country");
        assertThat(removeKeys, allOf(hasEntry("name", "feilong"), hasEntry("age", "18"), not(hasEntry("country", "china"))));
    }

    /**
     * Test remove keys null keys.
     */
    @Test
    public void testRemoveKeysNullKeys(){
        Map<String, String> map = newLinkedHashMap(3);
        map.put("name", "feilong");
        map.put("age", "18");
        map.put("country", "china");
        assertEquals(map, MapUtil.removeKeys(map, null));
    }

    /**
     * Test remove keys empty keys.
     */
    @Test
    public void testRemoveKeysEmptyKeys(){
        Map<String, String> map = newLinkedHashMap(3);
        map.put("name", "feilong");
        map.put("age", "18");
        map.put("country", "china");
        assertEquals(map, MapUtil.removeKeys(map));
    }

    /**
     * Test remove keys not exist keys.
     */
    @Test
    public void testRemoveKeysNotExistKeys(){
        Map<String, String> map = newLinkedHashMap(3);
        map.put("name", "feilong");
        map.put("age", "18");
        map.put("country", "china");
        assertEquals(map, MapUtil.removeKeys(map, (String) null));
        assertEquals(map, MapUtil.removeKeys(map, "name1"));
    }

    /**
     * Test remove keys null map.
     */
    @Test
    public void testRemoveKeysNullMap(){
        assertEquals(null, MapUtil.removeKeys(null, "country"));
    }
}
