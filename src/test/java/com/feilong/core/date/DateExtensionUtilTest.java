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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.jsonlib.JsonUtil;

/**
 * The Class DateExtensionUtilTest.
 *
 * @author feilong
 * @version 1.0.8 2014年7月31日 下午2:48:22
 * @since 1.0.8
 */
public class DateExtensionUtilTest extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateExtensionUtilTest.class);

    /**
     * Test get interval for view long.
     */
    @Test
    public void testGetIntervalForViewLong(){
        LOGGER.debug(DateExtensionUtil.getIntervalForView(25841));
        LOGGER.debug(DateExtensionUtil.getIntervalForView(0));
    }

    /**
     * Test get chinese week.
     */
    @Test
    public void testGetChineseWeek(){
        LOGGER.debug(DateExtensionUtil.getChineseWeek(0));
    }

    /**
     * Test get interval day list.
     */
    @Test
    public void testGetIntervalDayList(){
        List<Date> dates = DateExtensionUtil.getIntervalDayList(FROMSTRING, TOSTRING, DatePattern.COMMON_DATE_AND_TIME);
        LOGGER.debug(JsonUtil.format(dates));
    }

    /**
     * Test get interval for view.
     */
    @Test
    public void testGetIntervalForView(){
        Date now = DateUtil.string2Date("2011-05-19 11:31:25.456", DatePattern.COMMON_DATE_AND_TIME);
        now = new Date();
        Date date = DateUtil.string2Date("2012-12-03 00:00:00", DatePattern.COMMON_DATE_AND_TIME);
        LOGGER.debug(DateExtensionUtil.getIntervalForView(now, date));
        LOGGER.debug(DateUtil.getIntervalTime(now, date) + "");
    }

    /**
     * TestCalendarUtilTest.
     */
    @Test
    public void testCalendarUtilTest(){
        List<String> weekDateStringList = DateExtensionUtil
                        .getWeekDateStringList(Calendar.THURSDAY, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND);
        LOGGER.debug(JsonUtil.format(weekDateStringList));
    }

    /**
     * Test to humanization date string.
     */
    @Test
    public void testToPrettyDateString(){
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2012-10-18 13:55:00", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2012-10-18 14:14:22", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2012-10-18 14:15:22", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2012-10-17 14:15:02", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2012-10-16 14:15:02", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2012-10-15 14:15:02", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2012-09-15 14:15:02", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2015-08-02 14:15:02", DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(DateExtensionUtil.toPrettyDateString(DateUtil.string2Date("2015-7-30 13:00:00", DatePattern.COMMON_DATE_AND_TIME)));
    }

    /**
     * TestDateUtilTest.
     */
    @Test
    public void testDateUtilTest(){
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(new Date());
        dateList.add(new Date());
        dateList.add(new Date());
        dateList.add(new Date());
        LOGGER.debug(JsonUtil.format(DateExtensionUtil.toStringList(dateList, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND)));
    }

    /**
     * Test get extent yesterday.
     */
    @Test
    public void testGetResetYesterdayAndToday(){
        LOGGER.debug(JsonUtil.format(DateExtensionUtil.getResetYesterdayAndToday()));
    }

    /**
     * Test get extent today.
     */
    @Test
    public void testGetResetTodayAndTomorrow(){
        LOGGER.debug(JsonUtil.format(DateExtensionUtil.getResetTodayAndTomorrow()));
    }
}