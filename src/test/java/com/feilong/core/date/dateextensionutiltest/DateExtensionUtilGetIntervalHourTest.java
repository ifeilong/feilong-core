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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.BaseDateUtilTest;
import com.feilong.core.lang.StringUtil;

import static com.feilong.core.date.DateExtensionUtil.getIntervalHour;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

public class DateExtensionUtilGetIntervalHourTest extends BaseDateUtilTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateExtensionUtilGetIntervalHourTest.class);

    @Test
    public void testGetIntervalHour(){
        LOGGER.debug(
                        StringUtil.format("%05d", getIntervalHour(
                                        toDate("2014-01-01 00:00:00", COMMON_DATE_AND_TIME),
                                        toDate("2014-02-01 00:00:00", COMMON_DATE_AND_TIME))));
    }
}