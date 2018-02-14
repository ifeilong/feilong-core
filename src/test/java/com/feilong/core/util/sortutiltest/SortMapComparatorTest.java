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

import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static com.feilong.core.util.SortUtil.sortMap;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.feilong.core.util.comparator.PropertyComparator;
import com.feilong.core.util.comparator.RegexGroupNumberComparator;

public class SortMapComparatorTest{

    private final static PropertyComparator<Entry<String, Integer>> KEY_REGEX_PROPERTYCOMPARATOR = new PropertyComparator<>(
                    "key",
                    new RegexGroupNumberComparator("a(\\d*)"));

    //---------------------------------------------------------------

    /**
     * Test sort.
     */
    @Test
    public void testSort(){
        Map<String, Integer> map = newLinkedHashMap();

        map.put("a8", 8);
        map.put("a13", 123);
        map.put("a2", 345);

        Map<String, Integer> sort = sortMap(map, KEY_REGEX_PROPERTYCOMPARATOR);
        assertThat(sort.keySet(), contains("a2", "a8", "a13"));
    }

    //---------------------------------------------------------------

    /**
     * Test sort null comparator.
     */
    @Test(expected = NullPointerException.class)
    public void testSortNullComparator(){
        Map<String, Integer> map = newLinkedHashMap();

        map.put("a8", 8);
        map.put("a13", 123);
        map.put("a2", 345);

        sortMap(map, null);
    }

    //---------------------------------------------------------------

    /**
     * Test sort null map.
     */
    @Test
    public void testSortNullMap(){
        Map<String, Integer> map = sortMap(null, KEY_REGEX_PROPERTYCOMPARATOR);
        assertEquals(emptyMap(), map);
    }
}
