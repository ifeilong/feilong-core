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

import static com.feilong.core.date.DateUtil.getHourOfYear;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateUtilGetHourOfYearTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetHourOfYearTest{

    /**
     * Test get hour of year null date.
     */
    //************com.feilong.core.date.DateUtil.getHourOfYear(Date)***************************
    @Test(expected = NullPointerException.class)
    public void testGetHourOfYearNullDate(){
        getHourOfYear(null);
    }

    /**
     * Gets the hour of year.
     * 
     */
    @Test
    public void testGetHourOfYear(){
        assertEquals(0, getHourOfYear(toDate("2013-01-01 00:00:05", COMMON_DATE_AND_TIME)));
        assertEquals(31 * 24, getHourOfYear(toDate("2016-02-01 00:00:05", COMMON_DATE_AND_TIME)));
        assertEquals(24, getHourOfYear(toDate("2013-01-02 00:00:05", COMMON_DATE_AND_TIME)));
        assertEquals(24 * 4 + 12, getHourOfYear(toDate("2013-01-05 12:00:05", COMMON_DATE_AND_TIME)));
        assertEquals(6203, getHourOfYear(toDate("2013-09-16 11:42:22", COMMON_DATE_AND_TIME)));
    }
}