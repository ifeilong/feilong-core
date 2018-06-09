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

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;
import static com.feilong.core.date.DateUtil.getLastDateOfThisYear;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class DateUtilGetLastDateOfThisYearTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetLastDateOfThisYearTest{

    /**
     * Test get last date of this year.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testGetLastDateOfThisYear(){
        assertEquals(
                        toDate("2016-12-31 23:59:59.999", COMMON_DATE_AND_TIME_WITH_MILLISECOND),
                        getLastDateOfThisYear(toDate("2016-08-22 01:00:00", COMMON_DATE_AND_TIME)));
        assertEquals(
                        toDate("2015-12-31 23:59:59.999", COMMON_DATE_AND_TIME_WITH_MILLISECOND),
                        getLastDateOfThisYear(toDate("2015-02-22 01:00:00", COMMON_DATE_AND_TIME)));
    }

    /**
     * Test get last date of this year null.
     */
    @Test(expected = NullPointerException.class)
    public void testGetLastDateOfThisYearNull(){
        getLastDateOfThisYear(null);
    }
}