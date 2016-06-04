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

import java.util.Date;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.DatePattern;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class DateExtensionUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
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
        LOGGER.debug(DurationFormatUtils.formatDurationWords(25841, true, true));
        LOGGER.debug(DateExtensionUtil.getIntervalForView(0));
    }

    /**
     * Test get interval day list.
     */
    @Test
    public void testGetIntervalDayList(){
        LOGGER.debug(JsonUtil.format(DateExtensionUtil.getIntervalDayList(FROMSTRING, TOSTRING, DatePattern.COMMON_DATE_AND_TIME)));
        LOGGER.debug(JsonUtil.format(DateExtensionUtil.getIntervalDayList(TOSTRING, FROMSTRING, DatePattern.COMMON_DATE_AND_TIME)));
    }

    /**
     * Test get interval day list1.
     */
    @Test
    public void testGetIntervalDayList1(){
        Date fromDate = DateUtil.toDate(FROMSTRING, DatePattern.COMMON_DATE_AND_TIME);
        Date toDate = DateUtil.toDate(TOSTRING, DatePattern.COMMON_DATE_AND_TIME);
        LOGGER.debug(JsonUtil.format(DateExtensionUtil.getIntervalDayList(fromDate, toDate)));
    }

    /**
     * Test get interval for view.
     */
    @Test
    public void testGetIntervalForView(){
        Date now = DateUtil.toDate("2011-05-19 11:31:25.456", DatePattern.COMMON_DATE_AND_TIME);
        now = new Date();
        Date date = DateUtil.toDate("2012-12-03 00:00:00", DatePattern.COMMON_DATE_AND_TIME);
        LOGGER.debug(DateExtensionUtil.getIntervalForView(now, date));
        LOGGER.debug(DateExtensionUtil.getIntervalTime(now, date) + "");
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