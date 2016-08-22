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
package com.feilong.core.date.dateutiltest;

import static java.util.Calendar.DAY_OF_MONTH;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.BaseDateUtilTest;
import com.feilong.core.date.DateUtil;

import static com.feilong.core.date.DateUtil.isBefore;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;

/**
 * The Class DateUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DateUtilTest extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilTest.class);

    @Test
    public void testGetLastDateOfThisDay1(){
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

    @Test
    public void testGetFirstDateOfThisDay1(){
        logDate(DateUtil.getFirstDateOfThisDay(new Date()));
        logDate(DateUtils.truncate(new Date(), DAY_OF_MONTH));
    }

    /**
     * Test is before.
     */
    @Test
    public void testIsBefore(){
        assertEquals(true, isBefore(toDate("2011-03-05", COMMON_DATE), toDate("2011-03-10", COMMON_DATE)));
        assertEquals(false, isBefore(toDate("2011-05-01", COMMON_DATE), toDate("2011-04-01", COMMON_DATE)));
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