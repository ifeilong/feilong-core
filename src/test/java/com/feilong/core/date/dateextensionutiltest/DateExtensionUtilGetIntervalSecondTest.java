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

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.BaseDateUtilTest;

import static com.feilong.core.date.DateExtensionUtil.getIntervalSecond;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

public class DateExtensionUtilGetIntervalSecondTest extends BaseDateUtilTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DateExtensionUtilGetIntervalSecondTest.class);

    /**
     * Test get interval second.
     */
    @Test
    public void testGetIntervalSecond(){
        Date startDate = toDate("2013-01-01 00:00:00", COMMON_DATE_AND_TIME);

        LOGGER.debug(getIntervalSecond(startDate, NOW) + "");
        LOGGER.debug(getIntervalSecond(startDate, toDate("2113-01-01 00:00:00", COMMON_DATE_AND_TIME)) + "");

        LOGGER.debug(Integer.MAX_VALUE + "");
    }

}