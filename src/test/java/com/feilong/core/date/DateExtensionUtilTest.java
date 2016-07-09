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

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.date.DateExtensionUtil.getIntervalDayList;
import static com.feilong.core.date.DateExtensionUtil.getIntervalForView;
import static com.feilong.core.date.DateExtensionUtil.getIntervalTime;
import static com.feilong.core.date.DateExtensionUtil.getResetTodayAndTomorrow;
import static com.feilong.core.date.DateExtensionUtil.getResetYesterdayAndToday;
import static com.feilong.core.date.DateUtil.addDay;
import static com.feilong.core.date.DateUtil.getFirstDateOfThisDay;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        assertEquals("25秒841毫秒", getIntervalForView(25841));
        assertEquals("0", getIntervalForView(0));
        LOGGER.debug(DurationFormatUtils.formatDurationWords(25841, true, true));
    }

    /**
     * Test get interval day list.
     */
    @Test
    public void testGetIntervalDayList(){
        String pattern = COMMON_DATE_AND_TIME_WITH_MILLISECOND;
        String begin = "2011-03-05 23:31:25.456";
        String end = "2011-03-10 01:30:24.895";

        List<Date> intervalDayList = getIntervalDayList(toDate(begin, pattern), toDate(end, pattern));
        LOGGER.debug(JsonUtil.format(intervalDayList));

        List<Date> intervalDayList2 = getIntervalDayList(toDate(end, pattern), toDate(begin, pattern));
        LOGGER.debug(JsonUtil.format(intervalDayList2));
    }

    @Test
    public void testGetIntervalForView(){
        Date now = new Date();
        Date date = toDate("2012-12-03 00:00:00", COMMON_DATE_AND_TIME);
        LOGGER.debug(getIntervalForView(now, date));
        LOGGER.debug(getIntervalTime(now, date) + "");
    }

    @Test
    public void testGetIntervalForView1(){
        Date date = toDate("2016-07-03 00:00:00", COMMON_DATE_AND_TIME);
        LOGGER.debug(getIntervalForView(date));
    }

    @Test(expected = NullPointerException.class)
    public void testGetIntervalForView2(){
        getIntervalForView(null);
    }

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
        assertArrayEquals(
                        ConvertUtil.toArray(getFirstDateOfThisDay(NOW), getFirstDateOfThisDay(addDay(NOW, 1))),
                        getResetTodayAndTomorrow());
    }

}