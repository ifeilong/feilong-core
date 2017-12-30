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

import static org.junit.Assert.assertEquals;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.junit.Test;

import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.store.member.User;

public class ContainsPredicateTest{

    @Test
    public void testContainsPredicate12(){
        User guanyu30 = new User("关羽", 30);

        Predicate<User> predicate = PredicateUtils.andPredicate(
                        BeanPredicateUtil.containsPredicate("name", "关羽", "刘备"),
                        BeanPredicateUtil.containsPredicate("age", 30));

        assertEquals(true, predicate.evaluate(guanyu30));
    }

    //---------------------------------------------------------------

    /**
     * Test equal predicate.
     */
    @Test
    public void testContainsPredicate(){
        User user = new User(2L);
        Predicate<User> containsPredicate = BeanPredicateUtil.containsPredicate("id", 2L);
        assertEquals(true, containsPredicate.evaluate(user));
    }

    /**
     * Test equal predicate 1.
     */
    @Test
    public void testContainsPredicate1(){
        User user = new User(2L);
        Predicate<User> containsPredicate = BeanPredicateUtil.containsPredicate("id", (String) null);
        assertEquals(false, containsPredicate.evaluate(user));
    }

    //---------------------------------------------------------------------------

    /**
     * Test equal predicate null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsPredicateNullPropertyName(){
        BeanPredicateUtil.containsPredicate((String) null, (String) null);
    }

    /**
     * Test equal predicate empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsPredicateEmptyPropertyName(){
        BeanPredicateUtil.containsPredicate("", (String) null);
    }

    /**
     * Test equal predicate blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testContainsPredicateBlankPropertyName(){
        BeanPredicateUtil.containsPredicate("", (String) null);
    }
}
