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
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.MapUtil;

import static com.feilong.core.bean.ConvertUtil.toMapUseEntrys;

/**
 * The Class MapUtilInvertMapTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class InvertMapTest{

    /**
     * Test invert map.
     */
    @Test
    public void testInvertMap(){
        Map<String, Integer> map = toMapUseEntrys(
                        new SimpleEntry<>("a", 3007),
                        new SimpleEntry<>("b", 3001),
                        new SimpleEntry<>("c", 3001),
                        new SimpleEntry<>("d", 3003));
        Map<Integer, String> invertMap = MapUtil.invertMap(map);
        assertThat(invertMap, allOf(hasEntry(3007, "a"), hasEntry(3001, "c"), hasEntry(3003, "d"), not(hasEntry(3001, "b"))));
    }

    /**
     * Test invert map null map.
     */
    @Test
    public void testInvertMapNullMap(){
        assertEquals(null, MapUtil.invertMap(null));
    }

    /**
     * Test invert map empty map.
     */
    @Test
    public void testInvertMapEmptyMap(){
        assertEquals(emptyMap(), MapUtil.invertMap(emptyMap()));
        assertEquals(new HashMap<>(), MapUtil.invertMap(emptyMap()));
    }

}
