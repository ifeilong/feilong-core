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

import static com.feilong.core.date.DateUtil.getFirstDateOfThisWeek;
import static com.feilong.core.date.DateUtil.getLastDateOfThisWeek;
import static com.feilong.core.date.DateUtil.isBefore;
import static com.feilong.core.date.DateUtil.isInTime;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;
import static com.feilong.core.DatePattern.TIMESTAMP_WITH_MILLISECOND;

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
    }

    /**
     * TestDateUtilTest.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDateUtilTest8(){
        //jdk7- throw exception
        assertEquals("2015-12-29", DateUtil.toString(NOW, "YYYY-MM-dd"));
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
        logDate(DateUtil.getLastDateOfThisMonth(toDate("2012-02-01", COMMON_DATE)));
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
        LOGGER.debug("the param date:{}", DateUtil.toString(date, COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        Date now3 = DateUtil.getFirstDateOfThisWeek(date);
        LOGGER.debug(DateUtil.toString(now3, COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug("今天所在week 第一天:{}", DateUtil.toString(DateUtil.getFirstDateOfThisWeek(NOW), COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug(
                        "getFirstDateOfThisWeek:{}",
                        DateUtil.toString(
                                        getFirstDateOfThisWeek(toDate("2014-01-01 05:00:00", COMMON_DATE_AND_TIME)),
                                        COMMON_DATE_AND_TIME_WITH_MILLISECOND));

    }

    /**
     * Gets the last date of this week.
     */
    @Test
    public void testGetLastDateOfThisWeek(){
        Date date = DateUtil.addDay(NOW, -2);
        LOGGER.debug("the param date:{}", DateUtil.toString(date, COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        Date now3 = DateUtil.getLastDateOfThisWeek(date);
        LOGGER.debug(DateUtil.toString(now3, COMMON_DATE_AND_TIME_WITH_MILLISECOND));

        LOGGER.debug(
                        "getLastDateOfThisWeek:{}",
                        DateUtil.toString(
                                        getLastDateOfThisWeek(toDate("2014-12-31 05:00:00", COMMON_DATE_AND_TIME)),
                                        COMMON_DATE_AND_TIME_WITH_MILLISECOND));
    }

    /**
     * Test is before.
     */
    @Test
    public void testIsBefore(){
        assertEquals(true, isBefore(toDate("2011-03-05", COMMON_DATE), toDate("2011-03-10", COMMON_DATE)));
        assertEquals(false, isBefore(toDate("2011-05-01", COMMON_DATE), toDate("2011-04-01", COMMON_DATE)));
    }

    /**
     * Test string2 date.
     * 
     */
    @Test
    public void testToDate(){
        logDate(toDate("2016-06-28T01:21:12-0800", "yyyy-MM-dd'T'HH:mm:ssZ"));
        logDate(toDate("2016-06-28T01:21:12+0800", "yyyy-MM-dd'T'HH:mm:ssZ"));

        logDate(toDate("2016-02-33", COMMON_DATE));

        // 商品上线时间
        logDate(toDate("20130102140806000", TIMESTAMP_WITH_MILLISECOND));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToDate1(){
        DateUtil.toDate("2016-06-30 15:36 ", COMMON_DATE_AND_TIME_WITHOUT_SECOND);
    }

    @Test
    public void testToDate2(){
        DateUtil.toDate(StringUtils.trimToEmpty("2016-06-30 15:36 "), COMMON_DATE_AND_TIME_WITHOUT_SECOND);
    }

    //***************************************************************************************************
    /**
     * Test is in time.
     */
    @Test
    public void testIsInTime(){
        assertSame(
                        false,
                        isInTime(
                                        NOW,
                                        toDate("2012-10-10 22:59:00", COMMON_DATE_AND_TIME),
                                        toDate("2012-10-16 22:59:00", COMMON_DATE_AND_TIME)));
        assertSame(
                        true,
                        isInTime(
                                        toDate("2016-06-12", COMMON_DATE),
                                        toDate("2016-06-11 22:59:00", COMMON_DATE_AND_TIME),
                                        toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME)));

    }

    /**
     * Test is in time2.
     */
    @Test
    public void testIsInTime2(){
        assertSame(
                        false,
                        isInTime(
                                        toDate("2016-06-12", COMMON_DATE),
                                        toDate("2016-06-12 00:00:00", COMMON_DATE_AND_TIME),
                                        toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME)));
    }

    @Test(expected = NullPointerException.class)
    public void testIsInTime3(){
        isInTime(null, toDate("2016-06-12 00:00:00", COMMON_DATE_AND_TIME), toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME));
    }

    @Test(expected = NullPointerException.class)
    public void testIsInTime4(){
        isInTime(toDate("2016-06-12", COMMON_DATE), null, toDate("2016-06-16 22:59:00", COMMON_DATE_AND_TIME));
    }

    @Test(expected = NullPointerException.class)
    public void testIsInTime5(){
        isInTime(toDate("2016-06-12", COMMON_DATE), toDate("2016-06-12 00:00:00", COMMON_DATE_AND_TIME), null);
    }

    //***************************************************************************************************

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
     * Test get time length.
     */
    @Test
    public void testGetTimeLength(){
        LOGGER.debug((NOW.getTime() + "").length() + "");
    }

}