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

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
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
        assertEquals(new BigDecimal(100L), StatisticsUtil.sum(list, "id", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getId() > 10L;
            }
        }));
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
        User user1 = new User(10L);
        user1.setName("刘备");
        user1.setAge(50);

        User user2 = new User(20L);
        user1.setName("关羽");
        user2.setAge(50);

        User user3 = new User(100L);
        user3.setName("张飞");
        user3.setAge(null);

        User user4 = new User((Long) null);
        user4.setName("赵云");
        user4.setAge(100);

        List<User> list = toList(user1, user2, user3, user4);
        Map<String, BigDecimal> map = StatisticsUtil.sum(list, ConvertUtil.toArray("id", "age"), new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return !"张飞".equals(user.getName());
            }
        });
        LOGGER.debug(JsonUtil.format(map));
    }

    /**
     * Test group count.
     */
    @Test
    public void testGroupCount(){
        List<User> list = toList(//
                        new User("张飞", 20),
                        new User("关羽", 30),
                        new User("刘备", 40),
                        new User("赵云", 50));

        Map<String, Integer> map = StatisticsUtil.groupCount(list, "name", new Predicate<User>(){

            @Override
            public boolean evaluate(User user){
                return user.getAge() > 30;
            }
        });
        assertThat(map, allOf(hasEntry("刘备", 1), hasEntry("赵云", 1)));
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
}
