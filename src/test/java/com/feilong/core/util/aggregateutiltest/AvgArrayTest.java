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
import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.AggregateUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class AggregateUtilAvgArrayTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class AvgArrayTest{
    //****************AggregateUtil.avg(Collection<User>, String[], int)*******************************

    /**
     * Test avg 2.
     */
    @Test
    public void testAvg2(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        Map<String, BigDecimal> map = AggregateUtil.avg(toList(user1, user2), ConvertUtil.toArray("id", "age"), 2);
        assertThat(map, allOf(hasEntry("id", toBigDecimal("2.50")), hasEntry("age", toBigDecimal("24.00"))));
    }

    /**
     * Test avg null collection.
     */
    @Test
    public void testAvgNullCollection(){
        assertEquals(emptyMap(), AggregateUtil.avg(null, ConvertUtil.toArray("id", "age"), 2));
    }

    /**
     * Test avg empty collection.
     */
    @Test
    public void testAvgEmptyCollection(){
        assertEquals(emptyMap(), AggregateUtil.avg(toList(), ConvertUtil.toArray("id", "age"), 2));
    }

    /**
     * Test avg null property names.
     */
    @Test(expected = NullPointerException.class)
    public void testAvgNullPropertyNames(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        AggregateUtil.avg(toList(user1, user2), (String[]) null, 2);
    }

    /**
     * Test avg with empty property name element.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAvgWithEmptyPropertyNameElement(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        AggregateUtil.avg(toList(user1, user2), toArray("id", null), 2);
    }
}
