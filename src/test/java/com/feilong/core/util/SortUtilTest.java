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

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.comparator.PropertyComparator;
import com.feilong.core.util.comparator.RegexGroupNumberComparator;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.SortUtil.sort;
import static com.feilong.core.util.SortUtil.sortByKeyAsc;
import static com.feilong.core.util.SortUtil.sortByKeyDesc;
import static com.feilong.core.util.SortUtil.sortByValueAsc;
import static com.feilong.core.util.SortUtil.sortByValueDesc;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public class SortUtilTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SortUtilTest.class);

    @Test
    public final void testSortTArray(){
        assertEquals(ArrayUtils.EMPTY_OBJECT_ARRAY, sort((Object[]) null));
    }

    @Test
    public void testSortList(){
        List<Integer> list = toList(5, 10, 3, 2);
        assertThat(sort(list), contains(2, 3, 5, 10));
    }

    @Test
    public final void testSortListOfT(){
        assertEquals(emptyList(), sort((List) null));
    }

    @Test
    public final void testSortListOfTString(){
        assertEquals(emptyList(), sort((List) null, "name"));
    }

    @Test
    public final void testSortListOfTStringArray(){
        assertEquals(emptyList(), sort((List) null, "name", "age"));
    }

    @Test
    public final void testSortListOfTStringVArray(){
        assertEquals(emptyList(), sort((List) null, "name", "age"));
    }

    @Test
    public final void testSortListOfTStringListOfV(){
        assertEquals(emptyList(), sort((List) null, "name", "age"));
    }

    /**
     * Test property comparator.
     */
    @Test
    public void testPropertyComparator(){
        List<User> list = new ArrayList<User>();
        Long id = null;
        list.add(new User(id));
        list.add(new User(12L));
        list.add(new User(2L));
        list.add(new User(5L));
        list.add(null);
        list.add(new User(1L));
        list.add(new User(id));
        sort(list, new PropertyComparator<User>("id"));
        LOGGER.debug(JsonUtil.format(list));
    }

    /**
     * Test property comparator1.
     */
    @Test
    public void testPropertyComparator1(){
        List<User> list = new ArrayList<User>();
        list.add(new User(12L, 18));
        list.add(new User(2L, 36));
        list.add(new User(5L, 22));
        list.add(new User(1L, 8));
        sort(list, new PropertyComparator<User>("id"));
        LOGGER.debug(JsonUtil.format(list));
    }

    @Test
    public void testPropertyComparator2(){
        List<User> list = new ArrayList<User>();
        list.add(new User(12L, 18));
        list.add(new User(2L, 36));
        list.add(new User(5L, 22));
        list.add(new User(1L, 8));
        sort(list, "id");
        LOGGER.debug(JsonUtil.format(list));
    }

    @Test
    public void testPropertyComparator3(){
        List<User> list = toList(//
                        new User(12L, 18),
                        new User(2L, 36),
                        new User(2L, 2),
                        new User(2L, 30),
                        new User(1L, 8));
        sort(list, "id", "age");
        LOGGER.debug(JsonUtil.formatWithIncludes(list, "id", "age"));
    }

    @Test
    public void testPropertyComparator4(){
        List<User> list = toList(//
                        new User(12L, 18),
                        new User(2L, 36),
                        new User(2L, 2),
                        new User(2L, 30),
                        new User(1L, 8));
        sort(list, "age");
        LOGGER.debug(JsonUtil.formatWithIncludes(list, "id", "age"));
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
        Map<String, Integer> sortByValueAsc = sortByValueAsc(map);
        assertThat(sortByValueAsc.keySet(), contains("b", "a", "c"));
    }

    @Test
    public void testSortByValueASC1(){
        assertEquals(emptyMap(), sortByValueAsc(null));
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

        assertThat(sortByValueDesc(map).keySet(), contains("c", "a", "b"));
    }

    @Test
    public void testSortByValueDesc1(){
        assertEquals(emptyMap(), sortByValueDesc(null));
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

        Map<String, Integer> sortByKeyAsc = sortByKeyAsc(map);
        assertThat(sortByKeyAsc.keySet(), contains("a", "b", "c"));
    }

    @Test
    public void testSortByKeyAsc1(){
        assertEquals(emptyMap(), sortByKeyAsc(null));
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

        Map<String, Integer> sortByKeyAsc = sortByKeyAsc(map);
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

        Map<String, Integer> sortByKeyDesc = sortByKeyDesc(map);
        assertThat(sortByKeyDesc.keySet(), contains("c", "b", "a"));
    }

    @Test
    public void testSortByKeyDesc1(){
        assertEquals(emptyMap(), sortByKeyDesc(null));
    }
}
