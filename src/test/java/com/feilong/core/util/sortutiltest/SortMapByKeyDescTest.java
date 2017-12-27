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
package com.feilong.core.util.sortutiltest;

import static com.feilong.core.util.MapUtil.newHashMap;
import static com.feilong.core.util.SortUtil.sortMapByKeyDesc;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Test;

/**
 * The Class SortUtilSortMapByKeyDescTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SortMapByKeyDescTest{

    /**
     * Test sort by key desc.
     */
    @Test
    public void testSortByKeyDesc(){
        Map<String, Integer> map = newHashMap();
        map.put("a", 123);
        map.put(null, 8);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByKeyDesc = sortMapByKeyDesc(map);
        assertThat(sortByKeyDesc.keySet(), contains("c", "b", "a", null));
        assertThat(map, allOf(hasEntry("c", 345), hasEntry("b", 8), hasEntry("a", 123), hasEntry(null, 8)));
    }

    //---------------------------------------------------------------

    /**
     * Test sort by key desc null map.
     */
    @Test
    public void testSortByKeyDescNullMap(){
        assertEquals(emptyMap(), sortMapByKeyDesc(null));
    }

    @Test
    public void testSortByKeyDescEmptyMap(){
        assertEquals(emptyMap(), sortMapByKeyDesc(emptyMap()));
    }

}
