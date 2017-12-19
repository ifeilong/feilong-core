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

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.CollectionsUtil.group;
import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.TransformerUtils;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.store.member.User;

/**
 * The Class CollectionsUtilGroupWithTransformerAndPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GroupWithTransformerAndPredicateTest{

    /**
     * Test group2.
     */
    @Test
    public void testGroup(){
        User mateng55 = new User("马腾", 55);
        User machao28 = new User("马超", 28);
        User madai27 = new User("马岱", 27);
        User maxiu25 = new User("马休", 25);
        User zhangfei28 = new User("张飞", 28);
        User liubei32 = new User("刘备", 32);
        User guanyu50 = new User("关羽", 50);
        User guanping32 = new User("关平", 32);
        User guansuo31 = new User("关索", 31);
        User guanxing20 = new User("关兴", 18);

        //---------------------------------------------------------------

        List<User> list = toList(mateng55, machao28, madai27, maxiu25, zhangfei28, liubei32, guanyu50, guanping32, guansuo31, guanxing20);

        //---------------------------------------------------------------

        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
        Map<String, List<User>> map = CollectionsUtil.group(list, comparatorPredicate, new Transformer<User, String>(){

            @Override
            public String transform(User user){
                //提取名字 的姓
                return user.getName().substring(0, 1);
            }
        });

        assertSame(4, map.size());
        assertThat(map.keySet(), contains("马", "张", "刘", "关"));
        assertThat(
                        map,
                        allOf(//
                                        hasEntry(is("马"), hasItems(mateng55, machao28, madai27, maxiu25)),
                                        hasEntry(is("张"), hasItems(zhangfei28)),
                                        hasEntry(is("刘"), hasItems(liubei32)),
                                        hasEntry(is("关"), hasItems(guanping32, guansuo31))
                        //
                        ));
    }

    //---------------------------------------------------------------

    /**
     * Test group null collection.
     */
    @Test
    public void testGroupNullCollection(){
        assertEquals(
                        emptyMap(),
                        group(
                                        null,
                                        BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS),
                                        TransformerUtils.constantTransformer(5)));
    }

    /**
     * Test group empty collection.
     */
    @Test
    public void testGroupEmptyCollection(){
        assertEquals(
                        emptyMap(),
                        group(
                                        new ArrayList<>(),
                                        BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS),
                                        TransformerUtils.constantTransformer(5)));
    }

    /**
     * Test group null transformer.
     */
    //*****
    @Test(expected = NullPointerException.class)
    public void testGroupNullTransformer(){
        List<User> list = toList(new User("张飞", 10), new User("刘备", 10));
        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.LESS);
        CollectionsUtil.group(list, comparatorPredicate, null);
    }

    //*****
    /**
     * Test group null predicate.
     */
    @Test
    public void testGroupNullPredicate(){
        User zhangfei28 = new User("张飞", 28);
        User liubei32 = new User("刘备", 32);
        User liubei30 = new User("刘备", 30);
        List<User> list = toList(zhangfei28, liubei32, liubei30);

        Map<Integer, List<User>> map = CollectionsUtil.group(list, null, TransformerUtils.<User, Integer> constantTransformer(5));

        assertEquals(1, map.size());
        assertThat(map, allOf(hasEntry(5, toList(zhangfei28, liubei32, liubei30))));
    }

    /**
     * Test group not predicate.
     */
    @Test
    public void testGroupNotPredicate(){
        List<User> list = toList(new User("张飞", 10), new User("刘备", 10));
        Predicate<User> comparatorPredicate = BeanPredicateUtil.comparatorPredicate("age", 20, Criterion.EQUAL);
        assertEquals(emptyMap(), CollectionsUtil.group(list, comparatorPredicate, TransformerUtils.<User, Integer> constantTransformer(5)));
    }
}
