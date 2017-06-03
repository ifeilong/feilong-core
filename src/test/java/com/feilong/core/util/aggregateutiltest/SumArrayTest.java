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

import com.feilong.core.util.AggregateUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class AggregateUtilSumArrayTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SumArrayTest{

    //*************AggregateUtil.sum(Collection<User>, String...)*******************************
    /**
     * Test sum2.
     */
    @Test
    public void testSum2(){
        User user1 = new User(2L);
        user1.setAge(18);

        User user2 = new User(3L);
        user2.setAge(30);

        Map<String, BigDecimal> map = AggregateUtil.sum(toList(user1, user2), "id", "age");
        assertThat(map, allOf(hasEntry("id", toBigDecimal(5)), hasEntry("age", toBigDecimal(48))));
    }

    /**
     * Test sum null collection.
     */
    @Test
    public void testSumNullCollection(){
        assertEquals(emptyMap(), AggregateUtil.sum(null, "id", "age"));
    }

    /**
     * Test sum empty collection.
     */
    @Test
    public void testSumEmptyCollection(){
        assertEquals(emptyMap(), AggregateUtil.sum(toList(), "id", "age"));
    }

    /**
     * Test sum null property names.
     */
    @Test(expected = NullPointerException.class)
    public void testSumNullPropertyNames(){
        User user = new User(2L);
        user.setAge(18);
        AggregateUtil.sum(toList(user), (String[]) null);
    }

    /**
     * Test sum null property names element.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSumNullPropertyNamesElement(){
        User user = new User(2L);
        user.setAge(18);
        AggregateUtil.sum(toList(user), "id", (String) null);
    }
}
