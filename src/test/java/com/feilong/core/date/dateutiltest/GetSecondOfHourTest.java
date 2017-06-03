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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static com.feilong.core.date.DateUtil.getSecondOfHour;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateUtilGetSecondOfHourTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetSecondOfHourTest{

    //*************com.feilong.core.date.DateUtil.getSecondOfHour(Date)***********************************

    /**
     * Test get second of hour null date.
     */
    @Test(expected = NullPointerException.class)
    public void testGetSecondOfHourNullDate(){
        getSecondOfHour(null);
    }

    /**
     * Gets the second of hour.
     */
    @Test
    public void testGetSecondOfHour(){
        assertEquals(923, getSecondOfHour(toDate("2013-09-15 01:15:23", COMMON_DATE_AND_TIME)));
    }

}