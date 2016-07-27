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

import static com.feilong.tools.formatter.FormatterUtil.formatToSimpleTable;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.comparator.PropertyComparator;
import com.feilong.core.util.comparator.RegexGroupNumberComparator;
import com.feilong.test.User;
import com.feilong.tools.formatter.entity.BeanFormatterConfig;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.SortUtil.sort;
import static com.feilong.core.util.SortUtil.sortByFixedOrderPropertyValues;
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

    @Test
    public void testSelectArray(){
        User zhangfei = new User("张飞", 23);
        User guanyu = new User("关羽", 30);
        User liubei = new User("刘备", 25);
        List<User> list = toList(zhangfei, guanyu, liubei);

        String[] names = { "刘备", "关羽" };
        List<User> select = CollectionsUtil.select(list, "name", names);
        sort(select, new PropertyComparator<User>("name", new FixedOrderComparator<>(names)));
        LOGGER.debug(JsonUtil.formatWithIncludes(select, "name", "age"));

        List<User> select2 = CollectionsUtil.select(list, "name", names);
        LOGGER.debug(JsonUtil.formatWithIncludes(sortByFixedOrderPropertyValues(select2, "name", names), "name", "age"));
    }

    /**
     * Test property comparator.
     */
    @Test
    public void testPropertyComparator(){
        User u_null_id = new User((Long) null);
        User id12 = new User(12L);
        User id2 = new User(2L);
        User u_null = null;
        User id1 = new User(1L);

        List<User> list = toList(u_null_id, id12, id2, u_null, id1, u_null_id);
        sort(list, "id");

        //        LOGGER.debug(JsonUtil.format(list));
        assertThat(list, contains(u_null_id, u_null_id, id1, id2, id12, u_null));
    }

    /**
     * Test property comparator1.
     */
    @Test
    public void testPropertyComparator1(){
        User id12 = new User(12L, 18);
        User id2 = new User(2L, 36);
        User id5 = new User(5L, 22);
        User id1 = new User(1L, 8);
        List<User> list = toList(id12, id2, id5, id1);
        sort(list, "id");
        assertThat(list, contains(id1, id2, id5, id12));
    }

    @Test
    public void testPropertyComparator3(){
        User id12_age18 = new User(12L, 18);
        User id1_age8 = new User(1L, 8);
        User id2_age30 = new User(2L, 30);
        User id2_age2 = new User(2L, 2);
        User id2_age36 = new User(2L, 36);
        List<User> list = toList(id12_age18, id2_age36, id2_age2, id2_age30, id1_age8);
        sort(list, "id", "age");
        assertThat(list, contains(id1_age8, id2_age2, id2_age30, id2_age36, id12_age18));
    }

    @Test
    public void testPropertyComparator4(){
        User id12_age18 = new User(12L, 18);
        User id1_age8 = new User(1L, 8);
        User id2_age30 = new User(2L, 30);
        User id2_age2 = new User(2L, 2);
        User id2_age36 = new User(2L, 36);
        List<User> list = toList(id12_age18, id2_age36, id2_age2, id2_age30, id1_age8);

        LOGGER.debug(formatToSimpleTable(list));

        BeanFormatterConfig<User> beanFormatterConfig = new BeanFormatterConfig<>(User.class);
        beanFormatterConfig.setIncludePropertyNames("id", "age");
        beanFormatterConfig.setSorts("id", "age");
        LOGGER.debug(formatToSimpleTable(list, beanFormatterConfig));

        sort(list, "age");

        LOGGER.debug(formatToSimpleTable(list));
        assertThat(list, contains(id2_age2, id1_age8, id12_age18, id2_age30, id2_age36));
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
        map.put("d", 3455);
        map.put(null, 1345);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByKeyAsc = sortByKeyAsc(map);
        assertThat(sortByKeyAsc.keySet(), contains(null, "a", "b", "c", "d"));
        assertThat(
                        sortByKeyAsc,
                        allOf(hasEntry("a", 123), hasEntry("b", 8), hasEntry("c", 345), hasEntry("d", 3455), hasEntry(null, 1345)));
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
        map.put(null, 8);
        map.put("c", 345);
        map.put("b", 8);

        Map<String, Integer> sortByKeyDesc = sortByKeyDesc(map);
        LOGGER.debug(formatToSimpleTable(sortByKeyDesc));

        assertThat(map, allOf(hasEntry("c", 345), hasEntry("b", 8), hasEntry("a", 123), hasEntry(null, 8)));
    }

    @Test
    public void testSortByKeyDesc1(){
        assertEquals(emptyMap(), sortByKeyDesc(null));
    }
}
