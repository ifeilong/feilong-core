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

import static org.junit.Assert.assertSame;

import org.junit.Test;

import static com.feilong.core.date.DateUtil.isEquals;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DateUtilIsEqualsTest{

    @Test
    public void testIsEquals(){
        assertSame(true, isEquals(toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME), toDate("2016-06-16", COMMON_DATE), COMMON_DATE));

    }

    //**************************************

    @Test(expected = NullPointerException.class)
    public void testIsEqualsNullDate(){
        isEquals(null, null, COMMON_DATE_AND_TIME);
    }

    @Test(expected = NullPointerException.class)
    public void testIsEqualsNullDate1(){
        isEquals(null, toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME), COMMON_DATE_AND_TIME);
    }

    @Test(expected = NullPointerException.class)
    public void testIsEqualsNullDate2(){
        isEquals(toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME), null, COMMON_DATE_AND_TIME);
    }

    @Test(expected = NullPointerException.class)
    public void testIsEqualsNullPattern(){
        isEquals(toDate("2016-06-12", COMMON_DATE), toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEqualsEmptyPattern(){
        isEquals(toDate("2016-06-12", COMMON_DATE), toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsEqualsEmptyPattern1(){
        isEquals(toDate("2016-06-12", COMMON_DATE), toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME), " ");
    }
}