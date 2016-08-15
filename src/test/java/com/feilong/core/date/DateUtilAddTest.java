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
package com.feilong.core.date;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import static com.feilong.core.date.DateUtil.addHour;
import static com.feilong.core.date.DateUtil.addMonth;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DateUtilAddTest extends BaseDateUtilTest{

    //********************
    /**
     * Test add hour.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddHourNullDate(){
        DateUtil.addHour(null, 5);
    }

    @Test
    public void testAddHour(){
        Date date = toDate("2016-08-16 01:21:00", COMMON_DATE_AND_TIME);
        assertEquals(toDate("2016-08-16 06:21:00", COMMON_DATE_AND_TIME), addHour(date, 5));
    }

    @Test
    public void testAddHour1(){
        Date date = toDate("2016-08-16 01:21:00", COMMON_DATE_AND_TIME);
        assertEquals(toDate("2016-08-15 20:21:00", COMMON_DATE_AND_TIME), addHour(date, -5));
    }

    @Test
    public void testAddHour2(){
        Date date = toDate("2016-12-31 23:21:00", COMMON_DATE_AND_TIME);
        assertEquals(toDate("2017-01-01 04:21:00", COMMON_DATE_AND_TIME), addHour(date, 5));
    }

    @Test
    public void testAddHour3(){
        Date date = toDate("2016-01-01 01:21:00", COMMON_DATE_AND_TIME);
        assertEquals(toDate("2015-12-31 20:21:00", COMMON_DATE_AND_TIME), addHour(date, -5));
    }

    //***********************************************************

    /**
     * Adds the year.
     */
    @Test
    public void testAddYear(){
        logDate(DateUtil.addYear(NOW, 5));
        logDate(NOW);
        logDate(DateUtil.addYear(NOW, -5));
        logDate(NOW);
    }

    /**
     * Adds the day.
     */
    @Test
    public void testAddDay(){
        logDate(DateUtil.addDay(NOW, 5));
        logDate(DateUtil.addDay(NOW, -5));
        logDate(DateUtil.addDay(toDate("2014-12-31 02:10:05", COMMON_DATE_AND_TIME), 5));
        logDate(DateUtil.addDay(toDate("2014-01-01 02:10:05", COMMON_DATE_AND_TIME), -5));
    }

    /**
     * Adds the week.
     */
    @Test
    public void testAddWeek(){
        logDate(DateUtil.addWeek(NOW, 1));
        logDate(DateUtil.addWeek(NOW, -1));
    }

    /**
     * Test add millisecond.
     */
    @Test
    public void testAddMillisecond(){
        logDate(NOW);
        logDate(DateUtil.addMillisecond(NOW, 5000));
        logDate(DateUtil.addMillisecond(NOW, -5000));
    }

    /**
     * 添加 month.
     */
    @Test
    public void testAddMonth(){
        Date beginDate = toDate("2013-10-28", COMMON_DATE);
        logDate(addMonth(beginDate, 6));
        logDate(addMonth(NOW, 3));
        logDate(addMonth(NOW, -3));

        logDate(addMonth(NOW, 5));
        logDate(addMonth(NOW, -5));
    }

    /**
     * Test add second.
     */
    @Test
    public void testAddSecond(){
        logDate(NOW);
        logDate(DateUtil.addSecond(NOW, 180));
        logDate(DateUtil.addSecond(NOW, -180));
    }

    @Test
    public void testAddMinute(){
        logDate(DateUtil.addMinute(NOW, 180));
        logDate(DateUtil.addMinute(NOW, -180));
    }
}