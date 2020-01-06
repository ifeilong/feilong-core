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

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_TIME;
import static com.feilong.core.date.DateUtil.isInTime;
import static com.feilong.core.date.DateUtil.now;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * The Class DateUtilIsInTimeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IsInTimeDateStringTest{

    /**
     * Test is in time.
     */
    @Test
    public void testIsInTime(){
        assertSame(true, isInTime(toDate("2020-01-06 10:00:00", COMMON_DATE_AND_TIME), "08:00:00", "16:00:00", COMMON_TIME));
        assertSame(true, isInTime(toDate("10:00:00", COMMON_TIME), "08:00:00", "16:00:00", COMMON_TIME));
        assertSame(false, isInTime(toDate("2020-01-06 01:00:00", COMMON_DATE_AND_TIME), "08:00:00", "16:00:00", COMMON_TIME));
    }

    /**
     * Test is in time null date.
     */
    @Test(expected = NullPointerException.class)
    public void testIsInTimeNullDateNull(){
        isInTime(null, "2016-06-12 00:00:00", "2016-06-12 00:10:00", COMMON_DATE_AND_TIME);
    }

    //---------------------------------------------------------------

    /**
     * Test is in time null date.
     */
    @Test(expected = NullPointerException.class)
    public void testIsInTimeNullDate(){
        isInTime(now(), null, "2016-06-12 00:00:00", COMMON_DATE_AND_TIME);
    }

    /**
     * Test is in time null begin date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsInTimeNullBeginDate(){
        isInTime(now(), "", "2016-06-12 00:00:00", COMMON_DATE_AND_TIME);
    }

    /**
     * Test is in time null end date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsInTimeNullEndDate(){
        isInTime(now(), " ", "2016-06-12 00:00:00", COMMON_DATE_AND_TIME);
    }

    //---------------------------------------------------------------

    /**
     * Test is in time null date.
     */
    @Test(expected = NullPointerException.class)
    public void testIsInTimeNullDateEnd(){
        isInTime(now(), "2016-06-12 00:00:00", null, COMMON_DATE_AND_TIME);
    }

    /**
     * Test is in time null begin date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsInTimeNullBeginDateEnd(){
        isInTime(now(), "2016-06-12 00:00:00", "", COMMON_DATE_AND_TIME);
    }

    /**
     * Test is in time null end date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsInTimeNullEndDateEnd(){
        isInTime(now(), "2016-06-12 00:00:00", " ", COMMON_DATE_AND_TIME);
    }

    //---------------------------------------------------------------
    /**
     * Test is in time null date.
     */
    @Test(expected = NullPointerException.class)
    public void testIsInTimeNullDateEndPattern(){
        isInTime(now(), "2016-06-12 00:00:00", "2016-06-12 00:00:01", null);
    }

    /**
     * Test is in time null begin date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsInTimeNullBeginDateEndPattern(){
        isInTime(now(), "2016-06-12 00:00:00", "2016-06-12 00:00:01", "");
    }

    /**
     * Test is in time null end date.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIsInTimeNullEndDateEndPattern(){
        isInTime(now(), "2016-06-12 00:00:00", "2016-06-12 00:00:01", " ");
    }

}