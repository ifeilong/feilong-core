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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DateUtil;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;

/**
 * The Class DateUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtilTest.class);

    /**
     * Prints the.
     * 
     * @param date
     *            the date
     */
    protected void logDate(Date date){
        LOGGER.debug(DateUtil.toString(date, COMMON_DATE_AND_TIME_WITH_MILLISECOND));
    }

    /**
     * Test get last date of this day 1.
     */
    @Test
    public void testGetLastDateOfThisDay1(){
        Date NOW = new Date();
        logDate(DateUtils.ceiling(NOW, Calendar.DAY_OF_MONTH));
        logDate(DateUtils.round(NOW, Calendar.DAY_OF_MONTH));
        logDate(DateUtils.truncate(NOW, Calendar.DAY_OF_MONTH));
        LOGGER.debug(StringUtils.repeat("*", 20));
        logDate(DateUtils.ceiling(NOW, Calendar.MONTH));
        logDate(DateUtils.round(NOW, Calendar.MONTH));
        logDate(DateUtils.truncate(NOW, Calendar.MONTH));
    }

    /**
     * Test get first date of this day 1.
     */
    @Test
    public void testGetFirstDateOfThisDay1(){
        logDate(DateUtil.getFirstDateOfThisDay(new Date()));
        logDate(DateUtils.truncate(new Date(), DAY_OF_MONTH));
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
        Date NOW = new Date();
        LOGGER.debug((NOW.getTime() + "").length() + "");
    }
}