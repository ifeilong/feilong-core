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
package com.feilong.core.util.aggregateutiltest;

import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.junit.Test;

import com.feilong.core.util.AggregateUtil;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.test.User;

import static com.feilong.core.bean.ConvertUtil.toList;

public class AggregateUtilGroupCountPredicateTest{

    //********************AggregateUtil.groupCount(Collection<User>, String, Predicate<User>)******************************************************************
    /**
     * Test group count.
     */
    @Test
    public void testGroupCount(){
        List<User> list = toList(//
                        new User("张飞", 20),
                        new User("关羽", 30),
                        new User("赵云", 50),
                        new User("刘备", 40),
                        new User("刘备", 30),
                        new User("赵云", 50));

        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.LESS);
        Map<String, Integer> map = AggregateUtil.groupCount(list, "name", comparatorPredicate);
        assertThat(map, allOf(hasEntry("刘备", 1), hasEntry("赵云", 2)));
    }

    @Test
    public void testGroupCountNullCollection(){
        assertEquals(emptyMap(), AggregateUtil.groupCount(null, "name", BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.LESS)));
    }

    @Test
    public void testGroupCountEmptyCollection(){
        assertEquals(
                        emptyMap(),
                        AggregateUtil.groupCount(toList(), "name", BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.LESS)));
    }

    @Test(expected = NullPointerException.class)
    public void testGroupCountNullPropertyName(){
        User user1 = new User(2L);
        user1.setAge(18);

        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.LESS);
        AggregateUtil.groupCount(toList(user1), (String) null, comparatorPredicate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGroupCountBlankPropertyName(){
        User user1 = new User(2L);
        user1.setAge(18);

        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.LESS);
        AggregateUtil.groupCount(toList(user1), "   ", comparatorPredicate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGroupCountEmptyPropertyName(){
        User user1 = new User(2L);
        user1.setAge(18);

        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 30, Criterion.LESS);
        AggregateUtil.groupCount(toList(user1), "", comparatorPredicate);
    }

    @Test
    public void testGroupCountNullPredicate(){
        List<User> list = toList(//
                        new User("张飞", 20),
                        new User("关羽", 30),
                        new User("赵云", 50),
                        new User("刘备", 40),
                        new User("刘备", 30),
                        new User("赵云", 50));

        Map<String, Integer> map = AggregateUtil.groupCount(list, "name", null);
        assertThat(map, allOf(//
                        hasEntry("刘备", 2),
                        hasEntry("赵云", 2),
                        hasEntry("张飞", 1),
                        hasEntry("关羽", 1)));
    }
}
