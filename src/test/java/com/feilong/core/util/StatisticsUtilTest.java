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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.collections4.comparators.FixedOrderComparator;
import org.apache.commons.collections4.functors.ComparatorPredicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.predicate.BeanPredicate;
import com.feilong.core.util.predicate.BeanPredicateUtil;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public class StatisticsUtilTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsUtilTest.class);

    /**
     * Test avg.
     */
    @Test
    public void testAvg(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        assertEquals(new BigDecimal("4.00"), StatisticsUtil.avg(list, "id", 2));
    }

    /**
     * Test avg2.
     */
    @Test
    public void testAvg2(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        Map<String, BigDecimal> map = StatisticsUtil.avg(toList(user1, user2), ConvertUtil.toArray("id", "age"), 2);
        assertThat(map, allOf(hasEntry("id", toBigDecimal("2.50")), hasEntry("age", toBigDecimal("24.00"))));
    }

    /**
     * Test sum.
     */
    @Test
    public void testSum(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        assertEquals(new BigDecimal(12L), StatisticsUtil.sum(list, "id"));
        assertEquals(null, StatisticsUtil.sum(null, "id"));
    }

    /**
     * Test sum4.
     */
    @Test
    public void testSum4(){
        List<User> list = toList(//
                        new User(2L),
                        new User(50L),
                        new User(50L));

        BigDecimal expected = new BigDecimal(100L);
        assertEquals(expected, StatisticsUtil.sum(list, "id", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));

        //*****************************************************************

        Predicate<Long> predicate = new ComparatorPredicate<Long>(10L, ComparatorUtils.<Long> naturalComparator(), Criterion.LESS);
        BigDecimal sum = StatisticsUtil.sum(list, "id", new BeanPredicate<User>("id", predicate));
        assertEquals(new BigDecimal(100L), sum);
    }

    /**
     * TestStatisticsUtilTest.
     */
    @Test
    public void testStatisticsUtilTest(){
        String[] planets = { "Mercury", "Venus", "Earth", "Mars" };
        Comparator<String> distanceFromSun = new FixedOrderComparator<>(planets);
        Predicate<String> predicate = new ComparatorPredicate<String>("Venus", distanceFromSun, Criterion.GREATER);

        LOGGER.debug("{}", predicate.evaluate("Earth"));
    }

    @Test
    public void testStatisticsUtilTest1(){
        Predicate<Integer> predicate = new ComparatorPredicate<Integer>(10, ComparatorUtils.<Integer> naturalComparator(), Criterion.LESS);

        List<Integer> select = CollectionsUtil.select(toList(1, 5, 10, 30, 55, 88, 1, 12, 3), predicate);
        LOGGER.debug(JsonUtil.format(select, 0, 0));
    }

    /**
     * Test sum2.
     */
    @Test
    public void testSum2(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        Map<String, BigDecimal> map = StatisticsUtil.sum(toList(user1, user2), "id", "age");
        assertThat(map, allOf(hasEntry("id", toBigDecimal(5)), hasEntry("age", toBigDecimal(48))));
    }

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
        Map<String, BigDecimal> map = StatisticsUtil.sum(list, toArray("id", "age"), notPredicate);

        assertThat(map, allOf(hasEntry("id", toBigDecimal(30)), hasEntry("age", toBigDecimal(200))));
    }

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
        Map<String, Integer> map = StatisticsUtil.groupCount(list, "name", comparatorPredicate);
        assertThat(map, allOf(hasEntry("刘备", 1), hasEntry("赵云", 2)));
    }

    /**
     * Test group count1.
     */
    @Test
    public void testGroupCount1(){
        List<User> list = toList(//
                        new User("张飞"),
                        new User("关羽"),
                        new User("刘备"),
                        new User("刘备"));

        Map<String, Integer> map = StatisticsUtil.groupCount(list, "name");
        assertThat(map, allOf(hasEntry("刘备", 2), hasEntry("张飞", 1), hasEntry("关羽", 1)));
    }

    /**
     * Test get min value.
     */
    @Test
    public void testGetMinValue(){
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("a", 3007);
        map.put("b", 3001);
        map.put("c", 3002);
        map.put("d", 3003);
        map.put("e", 3004);
        map.put("f", 3005);
        map.put("g", -1005);

        assertThat(StatisticsUtil.getMinValue(map, "a", "b", "d", "g", "m"), is(-1005));
    }
}
