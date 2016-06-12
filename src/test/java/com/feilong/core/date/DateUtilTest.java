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
import static org.junit.Assert.assertSame;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.DatePattern;

/**
 * The Class DateUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DateUtilTest extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilTest.class);

    /**
     * Test get first date of this day.
     */
    @Test
    public void testGetFirstDateOfThisDay(){
        logDate(DateUtil.getFirstDateOfThisDay(NOW));
        logDate(DateUtils.truncate(NOW, Calendar.DAY_OF_MONTH));
    }

    /**
     * Test get last date of this day.
     */
    @Test
    public void testGetLastDateOfThisDay(){
        logDate(DateUtil.getLastDateOfThisDay(NOW));
        LOGGER.debug(StringUtils.repeat("*", 20));

        logDate(DateUtils.ceiling(NOW, Calendar.DAY_OF_MONTH));
        logDate(DateUtils.round(NOW, Calendar.DAY_OF_MONTH));
        logDate(DateUtils.truncate(NOW, Calendar.DAY_OF_MONTH));
        LOGGER.debug(StringUtils.repeat("*", 20));
        logDate(DateUtils.ceiling(NOW, Calendar.MONTH));
        logDate(DateUtils.round(NOW, Calendar.MONTH));
        logDate(DateUtils.truncate(NOW, Calendar.MONTH));
    }

    /**
     * TestDateUtilTest.
     */
    @Test
    public void testDateUtil(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.DECEMBER, 29);
        Date time = calendar.getTime();

        assertEquals("2014-12-29", DateUtil.toString(time, "yyyy-MM-dd"));
        try{
            //jdk7- throw exception
            assertEquals("2015-12-29", DateUtil.toString(time, "YYYY-MM-dd"));
        }catch (Exception e){
            LOGGER.error("", e);
        }
    }

    /**
     * Test1.
     */
    @Test
    public void test1(){
        Calendar calendar = DateUtil.toCalendar(TESTDATE_20141231013024);
        LOGGER.debug(calendar.getActualMaximum(Calendar.SECOND) + "");
        LOGGER.debug(calendar.getTimeInMillis() + "");
        LOGGER.debug(calendar.hashCode() + "");
        LOGGER.debug(DateUtil.getDayOfMonth(NOW) + "");
    }

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
        assertEquals(1, DateUtil.getDayOfYear(DateUtil.toDate("2013-01-01", DatePattern.COMMON_DATE)));
        LOGGER.debug(DateUtil.getDayOfYear(NOW) + "");
    }

    /**
     * Gets the hour of year.
     * 
     */
    @Test
    public void testGetHourOfYear(){
        assertEquals(0, DateUtil.getHourOfYear(DateUtil.toDate("2013-01-01 00:00:05", DatePattern.COMMON_DATE_AND_TIME)));
        assertEquals(31 * 24, DateUtil.getHourOfYear(DateUtil.toDate("2016-02-01 00:00:05", DatePattern.COMMON_DATE_AND_TIME)));
        assertEquals(24, DateUtil.getHourOfYear(DateUtil.toDate("2013-01-02 00:00:05", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateUtil.getHourOfYear(DateUtil.toDate("2013-01-05 12:00:05", DatePattern.COMMON_DATE_AND_TIME)) + "");

        LOGGER.debug(DateUtil.getHourOfYear(DateUtil.toDate("2013-09-16 11:42:22", DatePattern.COMMON_DATE_AND_TIME)) + "");
        LOGGER.debug(DateUtil.getHourOfYear(NOW) + "");
    }

    /**
     * Gets the first date of this month.
     * 
     */
    @Test
    public void testGetFirstDateOfThisMonth(){
        logDate(DateUtil.getFirstDateOfThisMonth(NOW));
        logDate(DateUtil.getFirstDateOfThisMonth(DateUtil.addMonth(NOW, +1)));
        logDate(DateUtil.getFirstDateOfThisMonth(DateUtil.addMonth(NOW, -1)));
    }

    /**
     * Gets the last date of this month.
     * 
     */
    @Test
    public void testGetLastDateOfThisMonth(){
        logDate(DateUtil.getLastDateOfThisMonth(NOW));
        logDate(DateUtil.getLastDateOfThisMonth(DateUtil.toDate("2012-02-01", DatePattern.COMMON_DATE)));
        logDate(DateUtil.getLastDateOfThisMonth(DateUtil.addMonth(NOW, +1)));
        logDate(DateUtil.getLastDateOfThisMonth(DateUtil.addMonth(NOW, -1)));
    }

    /**
     * Test get first week of specify date year.
     */
    @Test
    public void testGetFirstWeekOfSpecifyDateYear(){
        logDate(DateUtil.getFirstWeekOfSpecifyDateYear(NOW, Calendar.FRIDAY));
        logDate(DateUtil.getFirstWeekOfSpecifyDateYear(NOW, Calendar.MONDAY));
    }

    /**
     * Gets the first date of this year.
     */
    @Test
    public void testGetFirstDateOfThisYear(){
        logDate(DateUtil.getFirstDateOfThisYear(NOW));
        logDate(DateUtil.getFirstDateOfThisYear(DateUtil.addYear(NOW, +1)));
        logDate(DateUtil.getFirstDateOfThisYear(DateUtil.addYear(NOW, -1)));
    }

    /**
     * Test get last date of this year.
     */
    @Test
    public void testGetLastDateOfThisYear(){
        logDate(DateUtil.getLastDateOfThisYear(NOW));
        logDate(DateUtil.getLastDateOfThisYear(DateUtil.addYear(NOW, +1)));
        logDate(DateUtil.getLastDateOfThisYear(DateUtil.addYear(NOW, -1)));
    }

    /**
     * Gets the first date of this week.
     */
    @Test
    public void testGetFirstDateOfThisWeek(){
        Date date = DateUtil.addDay(NOW, -2);
        LOGGER.debug("the param date:{}", DateUtil.toString(date, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        Date now3 = DateUtil.getFirstDateOfThisWeek(date);
        LOGGER.debug(DateUtil.toString(now3, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug(
                        "今天所在week 第一天:{}",
                        DateUtil.toString(DateUtil.getFirstDateOfThisWeek(NOW), DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug(
                        "getFirstDateOfThisWeek:{}",
                        DateUtil.toString(
                                        DateUtil.getFirstDateOfThisWeek(
                                                        DateUtil.toDate("2014-01-01 05:00:00", DatePattern.COMMON_DATE_AND_TIME)),
                                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

    }

    /**
     * Gets the last date of this week.
     */
    @Test
    public void testGetLastDateOfThisWeek(){
        Date date = DateUtil.addDay(NOW, -2);
        LOGGER.debug("the param date:{}", DateUtil.toString(date, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        Date now3 = DateUtil.getLastDateOfThisWeek(date);
        LOGGER.debug(DateUtil.toString(now3, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug(
                        "getLastDateOfThisWeek:{}",
                        DateUtil.toString(
                                        DateUtil.getLastDateOfThisWeek(
                                                        DateUtil.toDate("2014-12-31 05:00:00", DatePattern.COMMON_DATE_AND_TIME)),
                                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));
    }

    /**
     * TestDateUtilTest.
     */
    @Test
    public void testDateUtilTest(){
        Date test = new Date();
        String dayString = test.toString();
        String timeString = dayString.substring(11, 19);
        LOGGER.debug(timeString);
    }

    /**
     * Test get add minute.
     */
    @Test
    public void testGetAddMinute(){
        logDate(DateUtil.addMinute(NOW, 180));
        logDate(DateUtil.addMinute(NOW, -180));
    }

    /**
     * 添加 month.
     */
    @Test
    public void addMonth(){
        Date beginDate = DateUtil.toDate("2013-10-28", DatePattern.COMMON_DATE);
        logDate(DateUtil.addMonth(beginDate, 6));
        logDate(DateUtil.addMonth(NOW, 3));
        logDate(DateUtil.addMonth(NOW, -3));

        logDate(DateUtil.addMonth(NOW, 5));
        logDate(DateUtil.addMonth(NOW, -5));
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
     * Test is before.
     */
    @Test
    public void testIsBefore(){
        assertEquals(
                        true,
                        DateUtil.isBefore(
                                        DateUtil.toDate(FROMSTRING, DatePattern.COMMON_DATE),
                                        DateUtil.toDate(TOSTRING, DatePattern.COMMON_DATE)));
        assertEquals(
                        false,
                        DateUtil.isBefore(
                                        DateUtil.toDate("2011-05-01", DatePattern.COMMON_DATE),
                                        DateUtil.toDate("2011-04-01", DatePattern.COMMON_DATE)));
    }

    /**
     * Test string2 date.
     */
    @Test
    public void testToDate(){
        logDate(DateUtil.toDate(FROMSTRING, DatePattern.COMMON_DATE));

        // 商品上线时间
        logDate(DateUtil.toDate("20130102140806000", DatePattern.TIMESTAMP_WITH_MILLISECOND));
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
        LOGGER.debug(
                        DateUtil.getWeekOfYear(
                                        DateUtil.toDate("2013-12-31 01:30:24.895", DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND))
                                        + "");
        LOGGER.debug(DateUtil.getWeekOfYear(CURRENT_YEAR_BEGIN) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(CURRENT_YEAR_END) + "");
        LOGGER.debug(
                        DateUtil.getWeekOfYear(
                                        DateUtil.toDate("2014-12-31 01:30:24.895", DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND))
                                        + "");
        LOGGER.debug(
                        DateUtil.getWeekOfYear(
                                        DateUtil.toDate("2014-12-30 01:30:24.895", DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND))
                                        + "");
        LOGGER.debug(
                        DateUtil.getWeekOfYear(
                                        DateUtil.toDate("2014-12-20 01:30:24.895", DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND))
                                        + "");
        LOGGER.debug(
                        DateUtil.getWeekOfYear(
                                        DateUtil.toDate("2014-12-26 01:30:24.895", DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND))
                                        + "");
        LOGGER.debug(
                        DateUtil.getWeekOfYear(
                                        DateUtil.toDate("2011-03-10 01:30:24.895", DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND))
                                        + "");
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
        LOGGER.debug(DateUtil.getYear(NOW) + "");
    }

    /**
     * Gets the hour of day.
     * 
     */
    @Test
    public void testGetHourOfDay(){
        LOGGER.debug(DateUtil.getHourOfDay(NOW) + "");
    }

    /**
     * Gets the minute.
     * 
     */
    @Test
    public void testGetMinute(){
        LOGGER.debug(DateUtil.getMinute(NOW) + "");
    }

    /**
     * Gets the second.
     * 
     */
    @Test
    public void testGetSecond(){
        LOGGER.debug(DateUtil.getSecond(NOW) + "");
    }

    /**
     * Gets the time.
     * 
     */
    @Test
    public void testGetTime(){
        LOGGER.debug(DateUtil.getTime(NOW) + "");
    }

    /**
     * Test is leap year.
     */
    @Test
    public void testIsLeapYear(){
        int year = -3;
        LOGGER.debug(new GregorianCalendar(-3, 1, 1).isLeapYear(year) + "");
        LOGGER.debug(DateUtil.isLeapYear(year) + "");
    }

    /**
     * Adds the year.
     */
    @Test
    public void addYear(){
        logDate(DateUtil.addYear(NOW, 5));
        logDate(NOW);
        logDate(DateUtils.addYears(NOW, 5));
        logDate(NOW);
        logDate(DateUtil.addYear(NOW, -5));
        logDate(NOW);
    }

    /**
     * Adds the day.
     */
    @Test
    public void addDay(){
        logDate(DateUtil.addDay(NOW, 5));
        logDate(DateUtil.addDay(NOW, -5));
        logDate(DateUtil.addDay(DateUtil.toDate("2014-12-31 02:10:05", DatePattern.COMMON_DATE_AND_TIME), 5));
        logDate(DateUtil.addDay(DateUtil.toDate("2014-01-01 02:10:05", DatePattern.COMMON_DATE_AND_TIME), -5));
    }

    /**
     * Adds the week.
     */
    @Test
    public void addWeek(){
        logDate(DateUtil.addWeek(NOW, 1));
        logDate(DateUtil.addWeek(NOW, -1));
    }

    /**
     * Test add hour.
     */
    @Test
    public void testAddHour(){
        logDate(DateUtil.addHour(NOW, 5));
        logDate(DateUtil.addHour(NOW, -5));
    }

    /**
     * Test is in time.
     */
    @Test
    public void testIsInTime(){
        assertSame(
                        false,
                        DateUtil.isInTime(
                                        NOW,
                                        DateUtil.toDate("2012-10-10 22:59:00", DatePattern.COMMON_DATE_AND_TIME),
                                        DateUtil.toDate("2012-10-16 22:59:00", DatePattern.COMMON_DATE_AND_TIME)));
        assertSame(
                        true,
                        DateUtil.isInTime(
                                        DateUtil.toDate("2016-06-12", DatePattern.COMMON_DATE),
                                        DateUtil.toDate("2016-06-11 22:59:00", DatePattern.COMMON_DATE_AND_TIME),
                                        DateUtil.toDate("2016-06-16 22:59:00", DatePattern.COMMON_DATE_AND_TIME)));

    }

    /**
     * Test is in time2.
     */
    @Test
    public void testIsInTime2(){
        assertSame(
                        false,
                        DateUtil.isInTime(
                                        DateUtil.toDate("2016-06-12", DatePattern.COMMON_DATE),
                                        DateUtil.toDate("2016-06-12 00:00:00", DatePattern.COMMON_DATE_AND_TIME),
                                        DateUtil.toDate("2016-06-16 22:59:00", DatePattern.COMMON_DATE_AND_TIME)));
    }

    /**
     * Test get time length.
     */
    @Test
    public void testGetTimeLength(){
        LOGGER.debug((NOW.getTime() + "").length() + "");
    }

}