/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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

import java.util.Calendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.CalendarUtil;
import com.feilong.core.date.DatePattern;
import com.feilong.core.tools.json.JsonUtil;

/**
 * The Class CalendarUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-2-19 下午4:16:30
 */
public class CalendarUtilTest extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarUtilTest.class);

    /**
     * Gets the max day of month.
     * 
     */
    @Test
    public void testGetMaxDayOfMonth(){
        int year = 2012;
        int month = 2;

        Object[] objects = { year, month, CalendarUtil.getMaxDayOfMonth(year, month) };
        LOGGER.debug("{} 年 {}月, 最大天:{}", objects);
    }

    /**
     * Gets the actual maximum.
     * 
     */
    @Test
    public void testGetActualMaximum(){
        Calendar calendar = CalendarUtil.string2Calendar("2007-02-20", DatePattern.COMMON_DATE);
        LOGGER.debug("the param objects:{}", calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
    }

    /**
     * Test get day of year.
     */
    @Test
    public void testGetDayOfYear(){
        LOGGER.debug(CalendarUtil.getDayOfYear(2013, 9, 5) + "");
        LOGGER.debug(CalendarUtil.getDayOfYear(2013, 9, 5) + "");
        LOGGER.debug(CalendarUtil.getDayOfYear(2014, 12, 31) + "");
    }

    /**
     * TestCalendarUtilTest.
     */
    @Test
    public void testCalendarUtilTest(){
        LOGGER.debug(JsonUtil.format(CalendarUtil.getWeekDateStringList(5, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND)));
    }
}
