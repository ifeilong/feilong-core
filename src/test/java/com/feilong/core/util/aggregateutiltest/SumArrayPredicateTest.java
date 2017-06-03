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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.ComparatorPredicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.AggregateUtil;
import com.feilong.core.util.predicate.BeanPredicate;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class AggregateUtilSumArrayPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SumArrayPredicateTest{

    //**************AggregateUtil.sum(Collection<User>, String, Predicate<User>)*******************************

    /**
     * Test sum predicate.
     */
    @Test
    public void testSumPredicate(){
        List<User> list = toList(//
                        new User(2L),
                        new User(50L),
                        new User(50L));

        assertEquals(new BigDecimal(100L), AggregateUtil.sum(list, "id", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));

        Predicate<Long> predicate = new ComparatorPredicate<Long>(10L, ComparatorUtils.<Long> naturalComparator(), Criterion.LESS);
        BigDecimal sum = AggregateUtil.sum(list, "id", new BeanPredicate<User>("id", predicate));
        assertEquals(new BigDecimal(100L), sum);
    }

    /**
     * Test sum predicate null value.
     */
    @Test
    public void testSumPredicateNullValue(){
        List<User> list = toList(//
                        new User(2L),
                        new User((Long) null),
                        new User(50L),
                        new User(50L));

        assertEquals(new BigDecimal(102L), AggregateUtil.sum(list, "id", null));
    }

    /**
     * Test sum null collection.
     */
    @Test
    public void testSumNullCollection(){
        assertEquals(null, AggregateUtil.sum(null, "id", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));
    }

    /**
     * Test sum empty collection.
     */
    @Test
    public void testSumEmptyCollection(){
        assertEquals(null, AggregateUtil.sum(ConvertUtil.<User> toList(), "id", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));
    }

    /**
     * Test sum null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testSumNullPropertyName(){
        assertEquals(null, AggregateUtil.sum(ConvertUtil.<User> toList(), (String) null, new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));
    }

    /**
     * Test sum empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSumEmptyPropertyName(){
        assertEquals(null, AggregateUtil.sum(ConvertUtil.<User> toList(), "", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));
    }

    /**
     * Test sum blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSumBlankPropertyName(){
        assertEquals(null, AggregateUtil.sum(ConvertUtil.<User> toList(), " ", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));
    }

    /**
     * Test sum no match predicate.
     */
    @Test
    public void testSumNoMatchPredicate(){
        List<User> list = toList(//
                        new User(2L),
                        new User(50L),
                        new User(50L));

        assertEquals(null, AggregateUtil.sum(list, "id", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 100L;
            }
        }));
    }
}
