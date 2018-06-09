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
package com.feilong.core.util.predicate.beanpredicateutil;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.util.CollectionsUtil.find;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.junit.Test;

import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.store.member.User;

/**
 * The Class BeanPredicateUtilEqualMapPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class EqualMapPredicateTest{

    /**
     * Test find2.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testFind2(){
        User guanyu30 = new User("关羽", 30);
        List<User> list = toList(//
                        new User("张飞", 23),
                        new User("关羽", 24),
                        new User("刘备", 25),
                        guanyu30);

        //在list中查找 名字是 关羽,并且 年龄是30 的user
        Map<String, ?> map = toMap("name", "关羽", "age", 30);
        assertEquals(guanyu30, find(list, BeanPredicateUtil.<User> equalPredicate(map)));
    }

    /**
     * Test equal predicate.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testEqualPredicate(){
        User user = new User(2L);
        Predicate<User> equalPredicate = BeanPredicateUtil.equalPredicate(toMap("id", 2L));
        assertEquals(true, equalPredicate.evaluate(user));
    }

    /**
     * Test equal predicate 1.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testEqualPredicate1(){
        User user = new User(2L);
        Predicate<User> equalPredicate = BeanPredicateUtil.equalPredicate(toMap("id", null));
        assertEquals(false, equalPredicate.evaluate(user));
    }

    //---------------------------------------------------------------------------

    /**
     * Test equal predicate null map.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testEqualPredicateNullMap(){
        BeanPredicateUtil.equalPredicate(null);
    }

    /**
     * Test equal predicate empty map.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("static-method")
    public void testEqualPredicateEmptyMap(){
        BeanPredicateUtil.equalPredicate(new HashMap<String, Object>());
    }

}
