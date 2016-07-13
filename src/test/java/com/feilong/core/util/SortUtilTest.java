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
package com.feilong.core.util;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.util.comparator.PropertyComparator;
import com.feilong.core.util.comparator.RegexGroupNumberComparator;

import static com.feilong.core.util.SortUtil.sort;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public class SortUtilTest{

    @Test
    public final void testSortTArray(){
        assertEquals(null, sort((Object[]) null));
    }

    @Test
    public final void testSortListOfT(){
        assertEquals(null, sort((List) null));
    }

    @Test
    public final void testSortListOfTString(){
        assertEquals(null, sort((List) null, "name"));
    }

    @Test
    public final void testSortListOfTStringArray(){
        assertEquals(null, sort((List) null, "name", "age"));
    }

    @Test
    public final void testSortListOfTStringVArray(){
        assertEquals(null, sort((List) null, "name", "age"));
    }

    @Test
    public final void testSortListOfTStringListOfV(){
        assertEquals(null, sort((List) null, "name", "age"));
    }

    /**
     * Test sort by value asc.
     */
    @Test
    public void testSortByValueASC(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);
        Map<String, Integer> sortByValueAsc = SortUtil.sortByValueAsc(map);
        assertThat(sortByValueAsc.keySet(), contains("b", "a", "c"));
    }

    /**
     * Test sort by value desc.
     */
    @Test
    public void testSortByValueDesc(){
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByValueDesc = SortUtil.sortByValueDesc(map);
        assertThat(sortByValueDesc.keySet(), contains("c", "a", "b"));
    }

    /**
     * Test sort by key asc.
     */
    @Test
    public void testSortByKeyAsc(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByKeyAsc = SortUtil.sortByKeyAsc(map);
        assertThat(sortByKeyAsc.keySet(), contains("a", "b", "c"));
    }

    /**
     * Test sort.
     */
    @Test
    public void testSort(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a8", 8);
        map.put("a13", 123);
        map.put("a2", 345);

        Map<String, Integer> sortByKeyAsc = SortUtil.sortByKeyAsc(map);
        assertThat(sortByKeyAsc.keySet(), contains("a13", "a2", "a8"));

        Map<String, Integer> sort = sort(
                        map,
                        new PropertyComparator<Map.Entry<String, Integer>>("key", new RegexGroupNumberComparator("a(\\d*)")));
        assertThat(sort.keySet(), contains("a2", "a8", "a13"));
    }

    /**
     * Test sort by key desc.
     */
    @Test
    public void testSortByKeyDesc(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a", 123);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByKeyDesc = SortUtil.sortByKeyDesc(map);
        assertThat(sortByKeyDesc.keySet(), contains("c", "b", "a"));
    }
}
