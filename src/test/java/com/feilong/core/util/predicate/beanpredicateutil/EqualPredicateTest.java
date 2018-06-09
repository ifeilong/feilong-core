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
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.store.member.User;

/**
 * The Class BeanPredicateUtilEqualPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.9.5
 */
public class EqualPredicateTest{

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

        Predicate<User> predicate = PredicateUtils
                        .andPredicate(BeanPredicateUtil.equalPredicate("name", "关羽"), BeanPredicateUtil.equalPredicate("age", 30));

        assertEquals(guanyu30, CollectionsUtil.find(list, predicate));
    }

    /**
     * Test equal predicate.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testEqualPredicate(){
        User user = new User(2L);
        Predicate<User> equalPredicate = BeanPredicateUtil.equalPredicate("id", 2L);
        assertEquals(true, equalPredicate.evaluate(user));
    }

    /**
     * Test equal predicate 1.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testEqualPredicate1(){
        User user = new User(2L);
        Predicate<User> equalPredicate = BeanPredicateUtil.equalPredicate("id", (String) null);
        assertEquals(false, equalPredicate.evaluate(user));
    }

    //---------------------------------------------------------------------------

    /**
     * Test equal predicate null property name.
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testEqualPredicateNullPropertyName(){
        BeanPredicateUtil.equalPredicate((String) null, (String) null);
    }

    /**
     * Test equal predicate empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("static-method")
    public void testEqualPredicateEmptyPropertyName(){
        BeanPredicateUtil.equalPredicate("", (String) null);
    }

    /**
     * Test equal predicate blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("static-method")
    public void testEqualPredicateBlankPropertyName(){
        BeanPredicateUtil.equalPredicate("", (String) null);
    }
}
