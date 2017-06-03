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
package com.feilong.core.util.collectionsutiltest;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionsUtilGroupWithPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GroupWithPropertyNameAndPredicateTest{

    /**
     * Test group2.
     */
    @Test
    public void testGroup2(){
        User zhangfei28 = new User("张飞", 28);
        User liubei32 = new User("刘备", 32);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(//
                        new User("张飞", 10),
                        zhangfei28,
                        liubei32,
                        liubei30,
                        new User("刘备", 10));

        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
        Map<String, List<User>> map = CollectionsUtil.group(list, "name", comparatorPredicate);

        assertThat(map, allOf(//
                        hasKey("张飞"),
                        hasKey("刘备"),
                        hasEntry(is("张飞"), hasItem(zhangfei28)),
                        hasEntry(is("刘备"), hasItems(liubei32, liubei30))));
        assertSame(2, map.size());
    }

    /**
     * Test group null predicate.
     */
    @Test
    public void testGroupNullPredicate(){
        User zhangfei28 = new User("张飞", 28);
        User liubei32 = new User("刘备", 32);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei28, liubei32, liubei30);

        Map<String, List<User>> map = CollectionsUtil.group(list, "name", null);

        assertEquals(2, map.size());
        assertThat(map, allOf(//
                        hasEntry("张飞", toList(zhangfei28)),
                        hasEntry("刘备", toList(liubei32, liubei30))));
    }

    /**
     * Test group null collection.
     */
    @Test
    public void testGroupNullCollection(){
        assertEquals(emptyMap(), CollectionsUtil.group(null, "name", BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS)));
    }

    /**
     * Test group empty collection.
     */
    @Test
    public void testGroupEmptyCollection(){
        assertEquals(
                        emptyMap(),
                        CollectionsUtil.group(new ArrayList<>(), "name", BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS)));
    }

    /**
     * Test group null property name.
     */
    //*****
    @Test(expected = NullPointerException.class)
    public void testGroupNullPropertyName(){
        List<User> list = toList(new User("张飞", 10), new User("刘备", 10));
        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
        CollectionsUtil.group(list, null, comparatorPredicate);
    }

    /**
     * Test group empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGroupEmptyPropertyName(){
        List<User> list = toList(new User("张飞", 10), new User("刘备", 10));
        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
        CollectionsUtil.group(list, "", comparatorPredicate);
    }

    /**
     * Test group blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGroupBlankPropertyName(){
        List<User> list = toList(new User("张飞", 10), new User("刘备", 10));
        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
        CollectionsUtil.group(list, " ", comparatorPredicate);
    }

    /**
     * Test group not predicate.
     */
    @Test
    public void testGroupNotPredicate(){
        List<User> list = toList(new User("张飞", 10), new User("刘备", 10));
        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.EQUAL);
        assertEquals(emptyMap(), CollectionsUtil.group(list, "name", comparatorPredicate));
    }
}
