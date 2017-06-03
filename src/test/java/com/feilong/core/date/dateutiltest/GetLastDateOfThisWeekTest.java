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

import static com.feilong.core.date.DateUtil.getLastDateOfThisWeek;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;

/**
 * The Class DateUtilGetLastDateOfThisWeekTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetLastDateOfThisWeekTest{

    /**
     * Test get last date of this week.
     */
    @Test
    public void testGetLastDateOfThisWeek(){
        assertEquals(
                        toDate("2016-08-27 23:59:59.999", COMMON_DATE_AND_TIME_WITH_MILLISECOND),
                        getLastDateOfThisWeek(toDate("2016-08-22 01:00:00", COMMON_DATE_AND_TIME)));
        assertEquals(
                        toDate("2015-01-03 23:59:59.999", COMMON_DATE_AND_TIME_WITH_MILLISECOND),
                        getLastDateOfThisWeek(toDate("2014-12-31 05:00:00", COMMON_DATE_AND_TIME)));
    }

    /**
     * Test get last date of this week null.
     */
    @Test(expected = NullPointerException.class)
    public void testGetLastDateOfThisWeekNull(){
        getLastDateOfThisWeek(null);
    }

}