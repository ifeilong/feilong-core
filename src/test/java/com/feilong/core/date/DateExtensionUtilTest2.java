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

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.StringUtil;

import static com.feilong.core.TimeInterval.MILLISECOND_PER_MINUTE;
import static com.feilong.core.date.DateExtensionUtil.getIntervalDay;
import static com.feilong.core.date.DateExtensionUtil.getIntervalHour;
import static com.feilong.core.date.DateExtensionUtil.getIntervalSecond;
import static com.feilong.core.date.DateExtensionUtil.getIntervalTime;
import static com.feilong.core.date.DateExtensionUtil.getIntervalWeek;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateExtensionUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.8
 */
public class DateExtensionUtilTest2 extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateExtensionUtilTest2.class);

    /**
     * Test get interval hour.
     */
    @Test
    public void testGetIntervalHour(){
        Date beginDate = toDate("2013-12-21 00:00:00", COMMON_DATE_AND_TIME);
        Date endDate = toDate("2013-12-21 01:00:00", COMMON_DATE_AND_TIME);

        // 相差小时
        int ihour = DateExtensionUtil.getIntervalHour(beginDate, endDate);

        for (int i = 0; i < ihour; ++i){
            for (int j = 0; j < 60; ++j){
                LOGGER.debug("0" + i + ":" + StringUtil.format("%02d", j));
            }
        }
    }

    /**
     * Test get interval second.
     */
    @Test
    public void testGetIntervalSecond(){
        Date startDate = toDate("2013-01-01 00:00:00", COMMON_DATE_AND_TIME);

        LOGGER.debug(getIntervalSecond(startDate, NOW) + "");
        LOGGER.debug(getIntervalSecond(startDate, toDate("2113-01-01 00:00:00", COMMON_DATE_AND_TIME)) + "");

        LOGGER.debug(getIntervalSecond(161986) + "");
        LOGGER.debug(Integer.MAX_VALUE + "");
    }

    /**
     * Test get interval week.
     */
    @Test
    public void testGetIntervalWeek(){
        LOGGER.debug(
                        "" + getIntervalWeek(
                                        toDate("2014-01-01 00:00:00", COMMON_DATE_AND_TIME),
                                        toDate("2014-02-01 00:00:00", COMMON_DATE_AND_TIME)));
        LOGGER.debug(
                        "" + getIntervalWeek(
                                        toDate("2014-10-28 00:00:00", COMMON_DATE_AND_TIME),
                                        toDate("2015-06-25 00:00:00", COMMON_DATE_AND_TIME)));

    }

    /**
     * Test get interval day.
     */
    @Test
    public void testGetIntervalDay(){
        LOGGER.debug("" + getIntervalDay(toDate("2008-12-1", COMMON_DATE), toDate("2008-9-29", COMMON_DATE)));
    }

    //*****************************************************************************************************

    /**
     * Test get interval time0.
     */
    @Test
    public void testGetIntervalTime0(){
        assertEquals(
                        1 * MILLISECOND_PER_MINUTE,
                        getIntervalTime(
                                        toDate("2016-07-16 15:21:00", COMMON_DATE_AND_TIME),
                                        toDate("2016-07-16 15:22:00", COMMON_DATE_AND_TIME)));
        assertEquals(
                        61 * MILLISECOND_PER_MINUTE,
                        getIntervalTime(
                                        toDate("2016-07-16 15:21:00", COMMON_DATE_AND_TIME),
                                        toDate("2016-07-16 16:22:00", COMMON_DATE_AND_TIME)));
        assertEquals(
                        61 * MILLISECOND_PER_MINUTE,
                        getIntervalTime(
                                        toDate("2016-07-16 16:22:00", COMMON_DATE_AND_TIME),
                                        toDate("2016-07-16 15:21:00", COMMON_DATE_AND_TIME)));
    }

    /**
     * Test get interval time1.
     */
    @Test(expected = NullPointerException.class)
    public void testGetIntervalTime1(){
        getIntervalTime(null, toDate("2016-07-16 15:51:00", COMMON_DATE_AND_TIME));
    }

    /**
     * Test get interval time2.
     */
    @Test(expected = NullPointerException.class)
    public void testGetIntervalTime2(){
        getIntervalTime(toDate("2016-07-16 15:21:00", COMMON_DATE_AND_TIME), null);
    }

    //*****************************************************************************************************
    /**
     * Test get interval day2.
     */
    @Test
    public void testGetIntervalDay2(){
        assertSame(3, getIntervalDay(toDate("2008-08-24", COMMON_DATE), toDate("2008-08-27", COMMON_DATE)));
    }

    /**
     * Test get interval hour1.
     */
    @Test
    public void testGetIntervalHour1(){
        LOGGER.debug(
                        StringUtil.format("%05d", getIntervalHour(
                                        toDate("2014-01-01 00:00:00", COMMON_DATE_AND_TIME),
                                        toDate("2014-02-01 00:00:00", COMMON_DATE_AND_TIME))));
    }

}