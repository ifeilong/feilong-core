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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.junit.Test;

import com.feilong.core.util.AggregateUtil;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.store.member.User;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class AggregateUtilSumPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SumPredicateTest{
    //************AggregateUtil.sum(Collection<User>, String[], Predicate<User>)*****************************

    /**
     * Test sum3.
     */
    @Test
    public void testSum3(){
        User liubei = new User(10L);
        liubei.setName("刘备");
        liubei.setAge(50);

        User guanyu = new User(20L);
        liubei.setName("关羽");
        guanyu.setAge(50);

        User zhangfei = new User(100L);
        zhangfei.setName("张飞");
        zhangfei.setAge(null);

        User zhaoyun = new User((Long) null);
        zhaoyun.setName("赵云");
        zhaoyun.setAge(100);

        List<User> list = toList(liubei, guanyu, zhangfei, zhaoyun);

        Predicate<User> notPredicate = PredicateUtils.notPredicate(BeanPredicateUtil.equalPredicate("name", "张飞"));
        Map<String, BigDecimal> map = AggregateUtil.sum(list, toArray("id", "age"), notPredicate);

        assertThat(map, allOf(hasEntry("id", toBigDecimal(30)), hasEntry("age", toBigDecimal(200))));
    }

    /**
     * Test sum 31.
     */
    @Test
    public void testSum31(){
        assertEquals(emptyMap(), AggregateUtil.sum(null, toArray("id", "age"), BeanPredicateUtil.equalPredicate("name", "张飞")));
    }

    /**
     * Test sum 311.
     */
    @Test
    public void testSum311(){
        assertEquals(emptyMap(), AggregateUtil.sum(toList(), toArray("id", "age"), BeanPredicateUtil.equalPredicate("name", "张飞")));
    }

    /**
     * Test sum 3111.
     */
    @Test
    public void testSum3111(){
        User zhangfei = new User(100L);
        zhangfei.setName("张飞");
        zhangfei.setAge(null);

        List<User> list = toList(zhangfei);

        Predicate<User> notPredicate = PredicateUtils.notPredicate(BeanPredicateUtil.equalPredicate("name", "张飞"));
        Map<String, BigDecimal> map = AggregateUtil.sum(list, toArray("id", "age"), notPredicate);

        assertEquals(true, isNullOrEmpty(map));
    }
}
