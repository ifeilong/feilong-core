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

import static com.feilong.core.date.DateExtensionUtil.getIntervalTime;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateExtensionUtilGetIntervalTimeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetIntervalTimeTest{

    /**
     * Test get interval time1.
     */
    @Test(expected = NullPointerException.class)
    public void testGetIntervalTime1(){
        getIntervalTime(null, toDate("2016-07-16 15:51:00", COMMON_DATE_AND_TIME));
    }

    /**
     * Test get interval time2.
     */
    @Test(expected = NullPointerException.class)
    public void testGetIntervalTime2(){
        getIntervalTime(toDate("2016-07-16 15:21:00", COMMON_DATE_AND_TIME), null);
    }

}