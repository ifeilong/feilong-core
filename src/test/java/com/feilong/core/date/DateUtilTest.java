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
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.StringUtil;

/**
 * The Class DateUtilTest.
 * 
 * @author feilong
 * @version 1.0 2012-2-19 下午4:17:03
 */
public class DateUtilTest extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilTest.class);

    /**
     * TestDateUtilTest.
     */
    @Test
    public void testDateUtil(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(2014, Calendar.DECEMBER, 29);
        Date time = calendar.getTime();

        assertEquals("2014-12-29", DateUtil.date2String(time, "yyyy-MM-dd"));
        try{
            //jdk7- throw exception
            assertEquals("2015-12-29", DateUtil.date2String(time, "YYYY-MM-dd"));
        }catch (Exception e){
            LOGGER.error("", e);
        }
    }

    /**
     * Test get interval hour.
     */
    @Test
    public void testGetIntervalHour(){
        Date beginDate = DateUtil.string2Date("2013-12-21 00:00:00", DatePattern.COMMON_DATE_AND_TIME);
        Date endDate = DateUtil.string2Date("2013-12-21 05:00:00", DatePattern.COMMON_DATE_AND_TIME);

        // 相差小时
        int ihour = DateUtil.getIntervalHour(beginDate, endDate);

        for (int i = 0; i < ihour; ++i){
            for (int j = 0; j < 60; ++j){
                LOGGER.debug("0" + i + ":" + StringUtil.format("%02d", j));
            }
        }
    }

    /**
     * Test get interval hour1.
     */
    @Test
    public void testGetIntervalHour1(){
        LOGGER.debug(StringUtil.format(
                        "%05d",
                        DateUtil.getIntervalHour(
                                        DateUtil.string2Date("2014-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME),
                                        DateUtil.string2Date("2014-02-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME)))

                        + "");
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
        Date date1 = DateUtil.string2Date("2013-01-05", DatePattern.COMMON_DATE);
        LOGGER.debug(DateUtil.getDayOfYear(date1) + "");
        LOGGER.debug(DateUtil.getDayOfYear(NOW) + "");
    }

    /**
     * Gets the hour of year.
     * 
     */
    @Test
    public void testGetHourOfYear(){
        LOGGER.debug(DateUtil.getHourOfYear(DateUtil.string2Date("2013-01-05 12:00:05", DatePattern.COMMON_DATE_AND_TIME)) + "");
        LOGGER.debug(DateUtil.getHourOfYear(DateUtil.string2Date("2013-01-01 00:00:05", DatePattern.COMMON_DATE_AND_TIME)) + "");
        LOGGER.debug(DateUtil.getHourOfYear(DateUtil.string2Date("2013-09-16 11:42:22", DatePattern.COMMON_DATE_AND_TIME)) + "");
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
        logDate(DateUtil.getLastDateOfThisMonth(DateUtil.string2Date("2012-02-01", DatePattern.COMMON_DATE)));
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
        LOGGER.debug("the param date:{}", DateUtil.date2String(date, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        Date now3 = DateUtil.getFirstDateOfThisWeek(date);
        LOGGER.debug(DateUtil.date2String(now3, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug("今天所在week 第一天:{}", DateUtil.date2String(
                        DateUtil.getFirstDateOfThisWeek(new Date()),
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug("getFirstDateOfThisWeek:{}", DateUtil.date2String(
                        DateUtil.getFirstDateOfThisWeek(DateUtil.string2Date("2014-01-01 05:00:00", DatePattern.COMMON_DATE_AND_TIME)),
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

    }

    /**
     * Gets the last date of this week.
     */
    @Test
    public void testGetLastDateOfThisWeek(){
        Date date = DateUtil.addDay(NOW, -2);

        LOGGER.debug("the param date:{}", DateUtil.date2String(date, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));
        Date now3 = DateUtil.getLastDateOfThisWeek(date);
        LOGGER.debug(DateUtil.date2String(now3, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug("getLastDateOfThisWeek:{}", DateUtil.date2String(
                        DateUtil.getLastDateOfThisWeek(DateUtil.string2Date("2014-12-31 05:00:00", DatePattern.COMMON_DATE_AND_TIME)),
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));
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
        Date beginDate = DateUtil.string2Date("2013-10-28", DatePattern.COMMON_DATE);
        logDate(DateUtil.addMonth(beginDate, 6));
        logDate(DateUtil.addMonth(new Date(), 3));
        logDate(DateUtil.addMonth(new Date(), -3));

        Date date = DateUtil.addMonth(new Date(), 5);
        logDate(date);

        date = DateUtil.addMonth(new Date(), -5);
        logDate(date);
    }

    /**
     * Test add second.
     */
    @Test
    public void testAddSecond(){
        logDate(DateUtil.addSecond(NOW, 180));
        logDate(DateUtil.addSecond(NOW, -180));
    }

    /**
     * Test is before.
     */
    @Test
    public void testIsBefore(){
        boolean isBefore = DateUtil.isBefore(FROMSTRING, TOSTRING, DatePattern.COMMON_DATE);
        LOGGER.debug(String.valueOf(isBefore));
    }

    /**
     * Test get interval day.
     */
    @Test
    public void testGetIntervalDay(){
        String fromString = "2008-12-1";
        String toString = "2008-9-29";
        int intervalDay = DateUtil.getIntervalDay(fromString, toString, DatePattern.COMMON_DATE);
        LOGGER.debug(intervalDay + "");
    }

    /**
     * Test string2 date.
     */
    @Test
    public void testString2Date(){
        Date date = DateUtil.string2Date(FROMSTRING, DatePattern.COMMON_DATE);
        LOGGER.debug(date.toString());
        String dateToString = DateUtil.date2String(date, DatePattern.COMMON_DATE_AND_TIME);
        LOGGER.debug(dateToString);

        Date onlineTime = DateUtil.string2Date("20130102140806000", DatePattern.TIMESTAMP_WITH_MILLISECOND);// 商品上线时间

        LOGGER.debug(onlineTime.toString());
    }

    /**
     * Test get interval second.
     */
    @Test
    public void testGetIntervalSecond(){
        Date startDate = DateUtil.string2Date("2013-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME);

        LOGGER.debug(DateUtil.getIntervalSecond(startDate, NOW) + "");
        LOGGER.debug(DateUtil.getIntervalSecond(startDate, DateUtil.string2Date("2113-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME))
                        + "");

        LOGGER.debug(DateUtil.getIntervalSecond(161986) + "");
        LOGGER.debug(Integer.MAX_VALUE + "");
    }

    /**
     * Test get day of week.
     */
    @Test
    public void testGetDayOfWeek(){
        LOGGER.debug(DateUtil.getDayOfWeek(new Date()) + "");
        LOGGER.debug(DateUtil.getDayOfWeek(CURRENT_YEAR_BEGIN) + "");
        LOGGER.debug(DateUtil.getDayOfWeek(CURRENT_YEAR_END) + "");
    }

    /**
     * Test get month.
     */
    @Test
    public void testGetMonth(){
        LOGGER.debug(DateUtil.getMonth(new Date()) + "");
    }

    /**
     * Test get week of year.
     */
    @Test
    public void testGetWeekOfYear(){
        LOGGER.debug(DateUtil.getWeekOfYear(new Date()) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(DateUtil.string2Date(
                        "2013-12-31 01:30:24.895",
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(CURRENT_YEAR_BEGIN) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(CURRENT_YEAR_END) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(DateUtil.string2Date(
                        "2014-12-31 01:30:24.895",
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(DateUtil.string2Date(
                        "2014-12-30 01:30:24.895",
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(DateUtil.string2Date(
                        "2014-12-20 01:30:24.895",
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(DateUtil.string2Date(
                        "2014-12-26 01:30:24.895",
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
        LOGGER.debug(DateUtil.getWeekOfYear(DateUtil.string2Date(
                        "2011-03-10 01:30:24.895",
                        DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND)) + "");
    }

    /**
     * Test get day of month.
     */
    @Test
    public void testGetDayOfMonth(){
        LOGGER.debug(DateUtil.getDayOfMonth(new Date()) + "");
    }

    /**
     * Test get year.
     */
    @Test
    public void testGetYear(){
        LOGGER.debug(DateUtil.getYear(new Date()) + "");
    }

    /**
     * Gets the hour of day.
     * 
     */
    @Test
    public void testGetHourOfDay(){
        LOGGER.debug(DateUtil.getHourOfDay(new Date()) + "");
    }

    /**
     * Gets the minute.
     * 
     */
    @Test
    public void testGetMinute(){
        LOGGER.debug(DateUtil.getMinute(new Date()) + "");
    }

    /**
     * Gets the second.
     * 
     */
    @Test
    public void testGetSecond(){
        LOGGER.debug(DateUtil.getSecond(new Date()) + "");
    }

    /**
     * Gets the time.
     * 
     */
    @Test
    public void testGetTime(){
        LOGGER.debug(DateUtil.getTime(new Date()) + "");
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
        Date date = DateUtil.addYear(NOW, 5);
        logDate(date);

        date = DateUtil.addYear(NOW, -5);
        logDate(date);
    }

    /**
     * Adds the day.
     */
    @Test
    public void addDay(){
        Date date = DateUtil.addDay(new Date(), 5);
        logDate(date);

        date = DateUtil.addDay(new Date(), -5);
        logDate(date);

        date = DateUtil.addDay(DateUtil.string2Date("2014-12-31 02:10:05", DatePattern.COMMON_DATE_AND_TIME), 5);
        logDate(date);

        logDate(DateUtil.addDay(DateUtil.string2Date("2014-01-01 02:10:05", DatePattern.COMMON_DATE_AND_TIME), -5));
    }

    /**
     * Adds the week.
     */
    @Test
    public void addWeek(){
        Date date = DateUtil.addWeek(new Date(), 1);
        logDate(date);

        date = DateUtil.addWeek(new Date(), -1);
        logDate(date);
    }

    /**
     * Test add hour.
     */
    @Test
    public void testAddHour(){
        LOGGER.debug("the param NewConstructorTypeMunger :{}", NOW);
        logDate(DateUtil.addHour(NOW, 5));
        logDate(DateUtil.addHour(NOW, -5));
    }

    /**
     * Test get yesterday.
     */
    @Test
    public void testGetYesterday(){
        logDate(DateUtil.getYesterday(NOW));
    }

    /**
     * Test is in time.
     */
    @Test
    public void testIsInTime(){
        LOGGER.debug("{}", DateUtil.isInTime(NOW, "2012-10-10 22:59:00", "2012-10-16 22:59:00", DatePattern.COMMON_DATE_AND_TIME));
    }

    /**
     * Test get interval week.
     */
    @Test
    public void testGetIntervalWeek(){
        LOGGER.debug("" + DateUtil.getIntervalWeek("2014-01-01 00:00:00", "2014-02-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME));
        LOGGER.debug("" + DateUtil.getIntervalWeek("2014-10-28 00:00:00", "2015-06-25 00:00:00", DatePattern.COMMON_DATE_AND_TIME));
    }

    /**
     * Test get interval time.
     */
    @Test
    public void testGetIntervalTime(){
        Date startDate = DateUtil.string2Date("2013-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME);
        LOGGER.debug(DateUtil.getIntervalTime(startDate, NOW) + "");
        LOGGER.debug(DateUtil.getIntervalTime(startDate, DateUtil.string2Date("2113-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME))
                        + "");

    }

    /**
     * Test get time length.
     */
    @Test
    public void testGetTimeLength(){
        LOGGER.debug((new Date().getTime() + "").length() + "");
    }

    /**
     * Test get interval day2.
     */
    @Test
    public void testGetIntervalDay2(){
        LOGGER.debug("" + DateUtil.getIntervalDay("2008-08-24", "2008-08-27", "yyyy-MM-dd"));
    }
}