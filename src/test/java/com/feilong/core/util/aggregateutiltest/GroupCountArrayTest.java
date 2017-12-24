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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static java.util.Collections.emptyMap;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.AggregateUtil;
import com.feilong.store.member.User;

/**
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GroupCountArrayTest{

    /**
     * Test group count null predicate.
     */
    @Test
    public void testGroupCountNullPredicate(){
        List<User> list = toList(//
                        new User("张飞", 20),
                        new User("关羽", 30),
                        new User("赵云", 50),
                        new User("刘备", 40),
                        new User("刘备", 30),
                        new User("赵云", 50));

        Map<String, Map<Object, Integer>> map = AggregateUtil.groupCount(list, toArray("name"));
        assertThat(
                        map.get("name"),
                        allOf(//
                                        hasEntry((Object) "刘备", 2),
                                        hasEntry((Object) "赵云", 2),
                                        hasEntry((Object) "张飞", 1),
                                        hasEntry((Object) "关羽", 1)));
    }

    @Test
    public void testGroupCountNullPredicate1(){
        List<User> list = toList(//
                        new User("张飞", 20),
                        new User("关羽", 30),
                        new User("赵云", 50),
                        new User("刘备", 40),
                        new User("刘备", 30),
                        new User("赵云", 50));

        Map<String, Map<Object, Integer>> map = AggregateUtil.groupCount(list, toArray("name", "age"));
        assertThat(
                        map.get("name"),
                        allOf(//
                                        hasEntry((Object) "刘备", 2),
                                        hasEntry((Object) "赵云", 2),
                                        hasEntry((Object) "张飞", 1),
                                        hasEntry((Object) "关羽", 1)));
        assertThat(
                        map.get("age"),
                        allOf(//
                                        hasEntry((Object) 20, 1),
                                        hasEntry((Object) 30, 2),
                                        hasEntry((Object) 50, 2),
                                        hasEntry((Object) 40, 1)));
    }

    //---------------------------------------------------------------
    /**
     * Test group count null collection.
     */
    @Test
    public void testGroupCountNullCollection(){
        assertEquals(emptyMap(), AggregateUtil.groupCount(null, toArray("name")));
    }

    /**
     * Test group count empty collection.
     */
    @Test
    public void testGroupCountEmptyCollection(){
        assertEquals(emptyMap(), AggregateUtil.groupCount(toList(), toArray("name")));
    }

    /**
     * Test group count null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testGroupCountNullPropertyNames(){
        User user1 = new User(2L);
        user1.setAge(18);

        AggregateUtil.groupCount(toList(user1), (String[]) null);
    }

    //---------------------------------------------------------------

    /**
     * Test group count blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGroupCountEmptyPropertyNames(){
        User user1 = new User(2L);
        user1.setAge(18);

        AggregateUtil.groupCount(toList(user1), ConvertUtil.<String> toArray());
    }

    @Test(expected = NullPointerException.class)
    public void testGroupCountNullPropertyName(){
        User user1 = new User(2L);
        user1.setAge(18);

        AggregateUtil.groupCount(toList(user1), toArray((String) null));
    }

    /**
     * Test group count empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGroupCountEmptyPropertyName(){
        User user1 = new User(2L);
        user1.setAge(18);

        AggregateUtil.groupCount(toList(user1), toArray(""));
    }

}
