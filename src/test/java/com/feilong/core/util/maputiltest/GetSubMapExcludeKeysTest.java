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
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

/**
 * The Class MapUtilGetSubMapExcludeKeysTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetSubMapExcludeKeysTest{

    /**
     * Test get sub map exclude keys.
     */
    @Test
    public void testGetSubMapExcludeKeys(){
        Map<String, Integer> map = newLinkedHashMap();

        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("g", -1005);

        Map<String, Integer> subMapExcludeKeys = MapUtil.getSubMapExcludeKeys(map, "a", "g", "m");
        assertThat(subMapExcludeKeys, allOf(hasEntry("b", 3001), hasEntry("c", 3002), not(hasKey("a")), not(hasKey("g"))));

        assertThat(map, allOf(hasEntry("a", 3007), hasEntry("b", 3001), hasEntry("c", 3002), hasEntry("g", -1005)));
    }

    /**
     * Test get sub map null map.
     */
    @Test
    public void testGetSubMapNullMap(){
        assertEquals(emptyMap(), MapUtil.getSubMapExcludeKeys(null, "a", "c"));
    }

    /**
     * Test get sub map empty map.
     */
    @Test
    public void testGetSubMapEmptyMap(){
        assertEquals(emptyMap(), MapUtil.getSubMapExcludeKeys(new HashMap<>(), "a", "c"));
        assertEquals(emptyMap(), MapUtil.getSubMapExcludeKeys(emptyMap(), "a", "c"));
    }

    /**
     * Test get sub map null exclude keys.
     */
    @Test
    public void testGetSubMapNullExcludeKeys(){
        Map<String, Integer> map = newLinkedHashMap();

        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("g", -1005);
        assertEquals(map, MapUtil.getSubMapExcludeKeys(map, (String[]) null));
    }

}
