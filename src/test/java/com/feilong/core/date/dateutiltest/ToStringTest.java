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
package com.feilong.core.date.dateutiltest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.date.DateUtil;

import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateUtilToStringTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToStringTest{

    /**
     * Test to string.
     */
    @Test
    public void testToString(){
        assertEquals("2016-06-11", DateUtil.toString(toDate("2016-06-11 22:59:00", COMMON_DATE_AND_TIME), COMMON_DATE));
    }

    /**
     * Test to string null date.
     */
    @Test(expected = NullPointerException.class)
    public void testToStringNullDate(){
        DateUtil.toString(null, "yyyy-MM-dd");
    }

    /**
     * Test to string null pattern.
     */
    @Test(expected = NullPointerException.class)
    public void testToStringNullPattern(){
        DateUtil.toString(toDate("2016-06-12", COMMON_DATE), null);
    }

    /**
     * Test to string empty pattern.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToStringEmptyPattern(){
        DateUtil.toString(toDate("2016-06-12", COMMON_DATE), "");
    }

    /**
     * Test to string empty pattern 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testToStringEmptyPattern1(){
        DateUtil.toString(toDate("2016-06-12", COMMON_DATE), " ");
    }
}