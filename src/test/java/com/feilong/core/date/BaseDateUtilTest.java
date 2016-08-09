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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;

/**
 * The Class BaseDateUtil.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.7
 */
abstract class BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER                  = LoggerFactory.getLogger(BaseDateUtilTest.class);

    /** <code>{@value}</code>. */
    static final String         FROMSTRING              = "2011-03-5 23:31:25.456";

    /** The to string. */
    static final String         TOSTRING                = "2011-03-10 01:30:24.895";

    /**
     * The now.
     * 
     * @deprecated
     */
    @Deprecated
    static final Date           NOW                     = new Date();

    /** The current year begin. */
    static final Date           CURRENT_YEAR_BEGIN      = DateUtil.getFirstDateOfThisYear(NOW);

    /** The current year end. */
    static final Date           CURRENT_YEAR_END        = DateUtil.getLastDateOfThisYear(NOW);

    /** The current year end. */
    static final Date           TESTDATE_20141231013024 = toDate("2014-12-31 01:30:24.895", COMMON_DATE_AND_TIME_WITH_MILLISECOND);

    /**
     * Prints the.
     * 
     * @param date
     *            the date
     */
    protected void logDate(Date date){
        LOGGER.debug(DateUtil.toString(date, COMMON_DATE_AND_TIME_WITH_MILLISECOND));
    }
}
