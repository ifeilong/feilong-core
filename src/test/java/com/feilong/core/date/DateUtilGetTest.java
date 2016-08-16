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

import java.util.Calendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;

/**
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DateUtilGetTest extends BaseDateUtilTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilGetTest.class);

    /**
     * Gets the second of day.
     */
    @Test
    public void testGetSecondOfDay(){
        LOGGER.debug(DateUtil.getSecondOfDay(NOW) + "");
    }

    /**
     * Gets the second of hour.
     */
    @Test
    public void testGetSecondOfHour(){
        LOGGER.debug(DateUtil.getSecondOfHour(NOW) + "");
    }

    /**
     * Gets the day of year.
     */
    @Test
    public void testGetDayOfYear(){
        assertEquals(1, DateUtil.getDayOfYear(toDate("2013-01-01", COMMON_DATE)));
        LOGGER.debug(DateUtil.getDayOfYear(NOW) + "");
    }

    /**
     * Test1.
     */
    @Test
    public void testGetDayOfMonth1(){
        Calendar calendar = DateUtil.toCalendar(TESTDATE_20141231013024);
        LOGGER.debug(calendar.getActualMaximum(Calendar.SECOND) + "");
        LOGGER.debug(calendar.getTimeInMillis() + "");
        LOGGER.debug(calendar.hashCode() + "");
        LOGGER.debug(DateUtil.getDayOfMonth(NOW) + "");
    }

    /**
     * Gets the hour of year.
     * 
     */
    @Test
    public void testGetHourOfYear(){
        assertEquals(0, DateUtil.getHourOfYear(toDate("2013-01-01 00:00:05", COMMON_DATE_AND_TIME)));
        assertEquals(31 * 24, DateUtil.getHourOfYear(toDate("2016-02-01 00:00:05", COMMON_DATE_AND_TIME)));
        assertEquals(24, DateUtil.getHourOfYear(toDate("2013-01-02 00:00:05", COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateUtil.getHourOfYear(toDate("2013-01-05 12:00:05", COMMON_DATE_AND_TIME)) + "");

        LOGGER.debug(DateUtil.getHourOfYear(toDate("2013-09-16 11:42:22", COMMON_DATE_AND_TIME)) + "");
        LOGGER.debug(DateUtil.getHourOfYear(NOW) + "");
    }

    /**
     * Test get day of week.
     */
    @Test
    public void testGetDayOfWeek(){
        LOGGER.debug(DateUtil.getDayOfWeek(NOW) + "");
        LOGGER.debug(DateUtil.getDayOfWeek(CURRENT_YEAR_BEGIN) + "");
        LOGGER.debug(DateUtil.getDayOfWeek(CURRENT_YEAR_END) + "");
    }

    /**
     * Test get month.
     */
    @Test
    public void testGetMonth(){
        LOGGER.debug(DateUtil.getMonth(NOW) + "");
    }

    /**
     * Test get week of year.
     */
    @Test
    public void testGetWeekOfYear(){
        LOGGER.debug(DateUtil.getWeekOfYear(NOW) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(toDate("2013-12-31 01:30:24.895", COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(CURRENT_YEAR_BEGIN) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(CURRENT_YEAR_END) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(toDate("2014-12-31 01:30:24.895", COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(toDate("2014-12-30 01:30:24.895", COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(toDate("2014-12-20 01:30:24.895", COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(toDate("2014-12-26 01:30:24.895", COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(toDate("2011-03-10 01:30:24.895", COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
    }

    /**
     * Test get day of month.
     */
    @Test
    public void testGetDayOfMonth(){
        LOGGER.debug(DateUtil.getDayOfMonth(NOW) + "");
    }

    /**
     * Test get year.
     */
    @Test
    public void testGetYear(){
        assertEquals(2012, DateUtil.getYear(toDate("2012-06-29 00:26:53", COMMON_DATE_AND_TIME)));
        assertEquals(2016, DateUtil.getYear(toDate("2016-07-16", COMMON_DATE)));
        assertEquals(2017, DateUtil.getYear(toDate("2016-13-16", COMMON_DATE)));
    }

    @Test(expected = NullPointerException.class)
    public void testGetYear1(){
        DateUtil.getYear(null);
    }

    /**
     * Gets the hour of day.
     */
    @Test
    public void testGetHourOfDay(){
        assertEquals(0, DateUtil.getHourOfDay(toDate("2012-06-29 00:26:53", COMMON_DATE_AND_TIME)));
        assertEquals(22, DateUtil.getHourOfDay(toDate("2016-07-16 22:34:00", COMMON_DATE_AND_TIME)));
        assertEquals(0, DateUtil.getHourOfDay(toDate("2016-07-16 24:34:00", COMMON_DATE_AND_TIME)));
    }

    /**
     * Gets the minute.
     */
    @Test
    public void testGetMinute(){
        LOGGER.debug(DateUtil.getMinute(NOW) + "");
    }

    /**
     * Gets the second.
     */
    @Test
    public void testGetSecond(){
        LOGGER.debug(DateUtil.getSecond(NOW) + "");
    }

    /**
     * Gets the time.
     */
    @Test
    public void testGetTime(){
        LOGGER.debug(DateUtil.getTime(NOW) + "");
    }
}