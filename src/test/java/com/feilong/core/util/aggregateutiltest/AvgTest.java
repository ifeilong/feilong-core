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
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.feilong.core.util.AggregateUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class AggregateUtilAvgTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class AvgTest{
    //*******************AggregateUtil.avg(Collection<User>, String, int)********************************

    /**
     * Test avg.
     */
    @Test
    public void testAvg(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));

        assertEquals(new BigDecimal("4.00"), AggregateUtil.avg(list, "id", 2));
    }

    /**
     * Test avg null collection.
     */
    @Test
    public void testAvgNullCollection(){
        assertEquals(null, AggregateUtil.avg(null, "id", 2));
    }

    /**
     * Test avg empty collection.
     */
    @Test
    public void testAvgEmptyCollection(){
        assertEquals(null, AggregateUtil.avg(new ArrayList<>(), "id", 2));
    }

    /**
     * Test avg null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testAvgNullPropertyName(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        AggregateUtil.avg(toList(user1, user2), (String) null, 2);
    }

    /**
     * Test avg blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAvgBlankPropertyName(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        AggregateUtil.avg(toList(user1, user2), "   ", 2);
    }

    /**
     * Test avg empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAvgEmptyPropertyName(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        AggregateUtil.avg(toList(user1, user2), "", 2);
    }
}
