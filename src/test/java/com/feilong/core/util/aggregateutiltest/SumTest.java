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

import org.junit.Test;

import com.feilong.core.util.AggregateUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class AggregateUtilSumTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SumTest{
    //***************AggregateUtil.sum(Collection<User>, String)*******************************

    /**
     * Test sum.
     */
    @Test
    public void testSum(){
        List<User> list = toList(//
                        new User(2L),
                        new User(5L),
                        new User(5L));
        assertEquals(new BigDecimal(12L), AggregateUtil.sum(list, "id"));
    }

    /**
     * Test sum null collection.
     */
    @Test
    public void testSumNullCollection(){
        assertEquals(null, AggregateUtil.sum(null, "id"));
    }

    /**
     * Test sum null property name.
     */
    @Test(expected = NullPointerException.class)
    public void testSumNullPropertyName(){
        AggregateUtil.sum(toList(new User(2L), new User(5L), new User(5L)), (String) null);
    }

    /**
     * Test sum empty property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSumEmptyPropertyName(){
        AggregateUtil.sum(toList(new User(2L), new User(5L), new User(5L)), "");
    }

    /**
     * Test sum blank property name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSumBlankPropertyName(){
        AggregateUtil.sum(toList(new User(2L), new User(5L), new User(5L)), " ");
    }
}
