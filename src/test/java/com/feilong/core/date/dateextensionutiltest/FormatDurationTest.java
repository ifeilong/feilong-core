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
package com.feilong.core.date.dateextensionutiltest;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.bean.ConvertUtil.toLong;
import static com.feilong.core.date.DateExtensionUtil.formatDuration;
import static com.feilong.core.date.DateUtil.now;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.feilong.core.TimeInterval;

/**
 * The Class DateExtensionUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.8
 */
public class FormatDurationTest{

    /**
     * Test get interval for view long.
     */
    @Test
    public void testFormatDurationLong(){
        assertEquals("25秒841毫秒", formatDuration(25841));
    }

    @Test
    public void testFormatDurationLong1(){
        assertEquals("365天", formatDuration(toLong(TimeInterval.SECONDS_PER_YEAR) * 1000));
    }

    /**
     * Test format duration 1.
     */
    //**************com.feilong.core.date.DateExtensionUtil.formatDuration(Date)*****************
    @Test
    public void testFormatDuration1(){
        Date date = toDate("2016-07-03 00:00:00", COMMON_DATE_AND_TIME);
        formatDuration(date);
    }

    /**
     * Test format duration 2.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatDuration2(){
        formatDuration(null);
    }

    //***************com.feilong.core.date.DateExtensionUtil.formatDuration(Date, Date)*****************

    /**
     * Test format duration 23.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatDuration23(){
        formatDuration(null, null);
    }

    /**
     * Test format duration 233.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatDuration233(){
        formatDuration(now(), null);
    }

    /**
     * Test format duration 2333.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatDuration2333(){
        formatDuration(null, now());
    }

    /**
     * Test format duration 3.
     */
    //***************com.feilong.core.date.DateExtensionUtil.formatDuration(long)****************
    @Test(expected = IllegalArgumentException.class)
    public void testFormatDuration3(){
        formatDuration(-1);
    }

    /**
     * Test format duration zero.
     */
    @Test
    public void testFormatDuration_zero(){
        assertEquals("0", formatDuration(0));
    }
}