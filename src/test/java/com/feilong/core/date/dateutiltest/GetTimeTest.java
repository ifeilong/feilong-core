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

import static com.feilong.core.date.DateUtil.getTime;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateUtilGetTimeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetTimeTest{

    //************com.feilong.core.date.DateUtil.getTime(Date)***********************

    /**
     * Test get time null date.
     */
    @Test(expected = NullPointerException.class)
    public void testGetTimeNullDate(){
        getTime(null);
    }

    /**
     * Gets the time.
     */
    @Test
    public void testGetTime(){
        assertEquals(1340900880000L, getTime(toDate("2012-06-29 00:28:00", COMMON_DATE_AND_TIME)));
    }
}