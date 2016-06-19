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

import static org.junit.Assert.assertSame;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.DatePattern;
import com.feilong.core.lang.StringUtil;

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
        Date beginDate = DateUtil.toDate("2013-12-21 00:00:00", DatePattern.COMMON_DATE_AND_TIME);
        Date endDate = DateUtil.toDate("2013-12-21 01:00:00", DatePattern.COMMON_DATE_AND_TIME);

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
        Date startDate = DateUtil.toDate("2013-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME);

        LOGGER.debug(DateExtensionUtil.getIntervalSecond(startDate, NOW) + "");
        LOGGER.debug(
                        DateExtensionUtil.getIntervalSecond(
                                        startDate,
                                        DateUtil.toDate("2113-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME)) + "");

        LOGGER.debug(DateExtensionUtil.getIntervalSecond(161986) + "");
        LOGGER.debug(Integer.MAX_VALUE + "");
    }

    /**
     * Test get interval week.
     */
    @Test
    public void testGetIntervalWeek(){
        LOGGER.debug(
                        "" + DateExtensionUtil.getIntervalWeek(
                                        DateUtil.toDate("2014-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME),
                                        DateUtil.toDate("2014-02-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(
                        "" + DateExtensionUtil.getIntervalWeek(
                                        DateUtil.toDate("2014-10-28 00:00:00", DatePattern.COMMON_DATE_AND_TIME),
                                        DateUtil.toDate("2015-06-25 00:00:00", DatePattern.COMMON_DATE_AND_TIME)));

    }

    /**
     * Test get interval day.
     */
    @Test
    public void testGetIntervalDay(){
        LOGGER.debug(
                        "" + DateExtensionUtil.getIntervalDay(
                                        DateUtil.toDate("2008-12-1", DatePattern.COMMON_DATE),
                                        DateUtil.toDate("2008-9-29", DatePattern.COMMON_DATE)));
    }

    /**
     * Test get interval time.
     */
    @Test
    public void testGetIntervalTime(){
        Date startDate = DateUtil.toDate("2016-06-01 15:21:00", DatePattern.COMMON_DATE_AND_TIME);
        LOGGER.debug(DateExtensionUtil.getIntervalTime(startDate, NOW) + "");
        LOGGER.debug(
                        DateExtensionUtil.getIntervalTime(
                                        startDate,
                                        DateUtil.toDate("2113-01-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME)) + "");

    }

    /**
     * Test get interval day2.
     */
    @Test
    public void testGetIntervalDay2(){
        assertSame(
                        3,
                        DateExtensionUtil.getIntervalDay(
                                        DateUtil.toDate("2008-08-24", DatePattern.COMMON_DATE),
                                        DateUtil.toDate("2008-08-27", DatePattern.COMMON_DATE)));
    }

    /**
     * Test get interval hour1.
     */
    @Test
    public void testGetIntervalHour1(){
        LOGGER.debug(
                        StringUtil.format("%05d", DateExtensionUtil.getIntervalHour(
                                        DateUtil.toDate(
                                                        "2014-01-01 00:00:00",
                                                        DatePattern.COMMON_DATE_AND_TIME),
                                        DateUtil.toDate("2014-02-01 00:00:00", DatePattern.COMMON_DATE_AND_TIME))));
    }

}