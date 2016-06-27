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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.DatePattern;
import com.feilong.core.bean.ConvertUtil;
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
        assertEquals("25秒841毫秒", DateExtensionUtil.getIntervalForView(25841));
        assertEquals("0", DateExtensionUtil.getIntervalForView(0));
        LOGGER.debug(DurationFormatUtils.formatDurationWords(25841, true, true));
    }

    /**
     * Test get interval day list.
     */
    @Test
    public void testGetIntervalDayList(){
        String pattern = DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;
        String begin = "2011-03-05 23:31:25.456";
        String end = "2011-03-10 01:30:24.895";

        List<Date> intervalDayList = DateExtensionUtil.getIntervalDayList(DateUtil.toDate(begin, pattern), DateUtil.toDate(end, pattern));
        LOGGER.debug(JsonUtil.format(intervalDayList));

        List<Date> intervalDayList2 = DateExtensionUtil.getIntervalDayList(DateUtil.toDate(end, pattern), DateUtil.toDate(begin, pattern));
        LOGGER.debug(JsonUtil.format(intervalDayList2));
    }

    /**
     * Test get interval for view.
     */
    @Test
    public void testGetIntervalForView(){
        Date now = new Date();
        Date date = DateUtil.toDate("2012-12-03 00:00:00", DatePattern.COMMON_DATE_AND_TIME);
        LOGGER.debug(DateExtensionUtil.getIntervalForView(now, date));
        LOGGER.debug(DateExtensionUtil.getIntervalTime(now, date) + "");
    }

    /**
     * Test get extent yesterday.
     */
    @Test
    public void testGetResetYesterdayAndToday(){
        assertArrayEquals(
                        ConvertUtil.toArray(DateUtil.getFirstDateOfThisDay(DateUtil.addDay(NOW, -1)), DateUtil.getFirstDateOfThisDay(NOW)),
                        DateExtensionUtil.getResetYesterdayAndToday());
    }

    /**
     * Test get extent today.
     */
    @Test
    public void testGetResetTodayAndTomorrow(){
        assertArrayEquals(
                        ConvertUtil.toArray(DateUtil.getFirstDateOfThisDay(NOW), DateUtil.getFirstDateOfThisDay(DateUtil.addDay(NOW, 1))),
                        DateExtensionUtil.getResetTodayAndTomorrow());
    }

}