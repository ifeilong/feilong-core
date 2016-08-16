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

import static com.feilong.core.date.DateUtil.addMonth;
import static com.feilong.core.date.DateUtil.addYear;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DateUtilAddTest extends BaseDateUtilTest{
    //********com.feilong.core.date.DateUtil.addYear(Date, int)****************************

    /**
     * Adds the year.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddYearNullDate(){
        DateUtil.addYear(null, 5);
    }

    @Test
    public void testAddYear3(){
        Date date = toDate("0001-01-01 01:21:00", COMMON_DATE_AND_TIME);
        Date addYear = addYear(date, -3);
        //System.out.println(DateUtil.toString(date, "G yyyy-MM-dd HH:mm:ss"));//TODO:remove
        //System.out.println(DateUtil.toString(addYear, "G yyyy-MM-dd HH:mm:ss"));//TODO:remove
        assertEquals(toDate("公元前 0003-01-01 01:21:00", "G yyyy-MM-dd HH:mm:ss"), addYear);
    }

    //**********com.feilong.core.date.DateUtil.addMonth(Date, int)********************
    @Test(expected = IllegalArgumentException.class)
    public void testAddMonthNullDate(){
        DateUtil.addMonth(null, 5);
    }

    @Test
    public void testAddMonth(){
        Date beginDate = toDate("2013-10-28", COMMON_DATE);
        logDate(addMonth(beginDate, 6));
        logDate(addMonth(NOW, 3));
        logDate(addMonth(NOW, -3));

        logDate(addMonth(NOW, 5));
        logDate(addMonth(NOW, -5));
    }

    //************com.feilong.core.date.DateUtil.addWeek(Date, int)****************************

    @Test(expected = IllegalArgumentException.class)
    public void testAddWeekNullDate(){
        DateUtil.addWeek(null, 5);
    }

    @Test
    public void testAddWeek(){
        logDate(DateUtil.addWeek(NOW, 1));
        logDate(DateUtil.addWeek(NOW, -1));
    }

    //**************com.feilong.core.date.DateUtil.addDay(Date, int)******************************
    @Test(expected = IllegalArgumentException.class)
    public void testAddDayNullDate(){
        DateUtil.addDay(null, 5);
    }

    //**********com.feilong.core.date.DateUtil.addHour(Date, int)**********
    /**
     * Test add hour.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddHourNullDate(){
        DateUtil.addHour(null, 5);
    }

    //***********com.feilong.core.date.DateUtil.addMinute(Date, int)*****************

    @Test(expected = IllegalArgumentException.class)
    public void testAddMinuteNullDate(){
        DateUtil.addMinute(null, 5);
    }

    @Test
    public void testAddMinute(){
        logDate(DateUtil.addMinute(NOW, 180));
        logDate(DateUtil.addMinute(NOW, -180));
    }

    //*************com.feilong.core.date.DateUtil.addSecond(Date, int)********************

    @Test(expected = IllegalArgumentException.class)
    public void testAddSecondNullDate(){
        DateUtil.addSecond(null, 5);
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

    //*************com.feilong.core.date.DateUtil.addMillisecond(Date, int)******************

    @Test(expected = IllegalArgumentException.class)
    public void testAddMillisecondNullDate(){
        DateUtil.addMillisecond(null, 5);
    }

}