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

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.date.DateExtensionUtil.formatDuration;
import static com.feilong.core.date.DateExtensionUtil.getIntervalTime;
import static com.feilong.core.date.DateExtensionUtil.getResetTodayAndTomorrow;
import static com.feilong.core.date.DateExtensionUtil.getResetYesterdayAndToday;
import static com.feilong.core.date.DateUtil.addDay;
import static com.feilong.core.date.DateUtil.getFirstDateOfThisDay;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;

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
    public void testFormatDurationLong(){
        assertEquals("25秒841毫秒", formatDuration(25841));

        LOGGER.debug(String.format("%d:%02d:%02d", 25841000 / 3600, (25841000 % 3600) / 60, (25841000 % 60)));
        //25 seconds
        LOGGER.debug(DurationFormatUtils.formatDurationWords(25841, true, false));
        //43 minutes 4 seconds
        LOGGER.debug(DurationFormatUtils.formatDurationWords(2584100, true, true));
        //0000-00-00 00:00:25
        LOGGER.debug(DurationFormatUtils.formatDuration(25841, COMMON_DATE_AND_TIME));
        //0000-00-00 00:00:25
        LOGGER.debug(DurationFormatUtils.formatDuration(25841, COMMON_DATE_AND_TIME, true));
        //0-0-0 0:0:25
        LOGGER.debug(DurationFormatUtils.formatDuration(25841, COMMON_DATE_AND_TIME, false));
        //0000-00-00 00:00:25.841
        LOGGER.debug(DurationFormatUtils.formatDuration(25841, COMMON_DATE_AND_TIME_WITH_MILLISECOND));
        //0000年00月00日 00小时00分钟25秒841毫秒
        LOGGER.debug(DurationFormatUtils.formatDuration(25841, "yyyy年MM月dd日 HH小时mm分钟ss秒SSS毫秒"));
        //00:00:25.841
        LOGGER.debug(DurationFormatUtils.formatDurationHMS(25841));
        //P0Y0M0DT0H0M25.841S
        LOGGER.debug(DurationFormatUtils.formatDurationISO(25841));
        //P0Y0M0DT0H58M49.714S
        LOGGER.debug(DurationFormatUtils.formatPeriodISO(25841, 3555555));
    }

    @Test
    public void testFormatDuration(){
        Date now = new Date();
        Date date = toDate("2012-12-03 00:00:00", COMMON_DATE_AND_TIME);
        LOGGER.debug(formatDuration(now, date));
        LOGGER.debug(getIntervalTime(now, date) + "");
    }

    @Test
    public void testFormatDuration1(){
        Date date = toDate("2016-07-03 00:00:00", COMMON_DATE_AND_TIME);
        LOGGER.debug(formatDuration(date));
    }

    @Test(expected = NullPointerException.class)
    public void testFormatDuration2(){
        formatDuration(null);
    }

    @Test(expected = NullPointerException.class)
    public void testFormatDuration23(){
        formatDuration(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testFormatDuration233(){
        formatDuration(new Date(), null);
    }

    @Test(expected = NullPointerException.class)
    public void testFormatDuration2333(){
        formatDuration(null, new Date());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFormatDuration3(){
        formatDuration(-1);
    }

    @Test
    public void testFormatDuration_zero(){
        assertEquals("0", formatDuration(0));
    }

    //************************************************************************************************

    /**
     * Test get extent yesterday.
     */
    @Test
    public void testGetResetYesterdayAndToday(){
        assertArrayEquals(toArray(getFirstDateOfThisDay(addDay(NOW, -1)), getFirstDateOfThisDay(NOW)), getResetYesterdayAndToday());
    }

    /**
     * Test get extent today.
     */
    @Test
    public void testGetResetTodayAndTomorrow(){
        assertArrayEquals(toArray(getFirstDateOfThisDay(NOW), getFirstDateOfThisDay(addDay(NOW, 1))), getResetTodayAndTomorrow());
    }

}