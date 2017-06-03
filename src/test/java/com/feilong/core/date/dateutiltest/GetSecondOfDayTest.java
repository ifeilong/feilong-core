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

import static com.feilong.core.date.DateUtil.getSecondOfDay;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateUtilGetSecondOfDayTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetSecondOfDayTest{

    //**********com.feilong.core.date.DateUtil.getSecondOfDay(Date)************************************

    /**
     * Test get second of day null date.
     */
    @Test(expected = NullPointerException.class)
    public void testGetSecondOfDayNullDate(){
        getSecondOfDay(null);
    }

    /**
     * Gets the second of day.
     */
    @Test
    public void testGetSecondOfDay(){
        assertEquals(60161, getSecondOfDay(toDate("2013-09-09 16:42:41", COMMON_DATE_AND_TIME)));
    }

}