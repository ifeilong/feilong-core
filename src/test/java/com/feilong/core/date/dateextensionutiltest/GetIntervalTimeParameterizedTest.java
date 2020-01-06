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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.Abstract3ParamsAndResultParameterizedTest;

import static com.feilong.core.TimeInterval.MILLISECOND_PER_MINUTE;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateExtensionUtil.getIntervalTime;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateExtensionUtilGetIntervalTimeParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetIntervalTimeParameterizedTest
                extends Abstract3ParamsAndResultParameterizedTest<String, String, String, Integer>{

    /**
     * Test get interval time.
     */
    @Test
    public void testGetIntervalTime(){
        assertEquals((Long) expectedValue.longValue(), (Long) getIntervalTime(toDate(input1, input3), toDate(input2, input3)));
    }

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:DateExtensionUtil.getIntervalTime(toDate(\"{0}\",\"{2}\"), toDate(\"{1}\",\"{2}\"))={3}")
    public static Iterable<Object[]> data(){
        return toList(//
                        ConvertUtil.<Object> toArray(
                                        "2016-07-16 15:21:00",
                                        "2016-07-16 15:22:00",
                                        COMMON_DATE_AND_TIME,
                                        1 * MILLISECOND_PER_MINUTE),

                        toArray("2016-07-16 15:21:00", "2016-07-16 16:22:00", COMMON_DATE_AND_TIME, 61 * MILLISECOND_PER_MINUTE),
                        toArray("2016-07-16 16:22:00", "2016-07-16 15:21:00", COMMON_DATE_AND_TIME, 61 * MILLISECOND_PER_MINUTE)
        //  
        );
    }

}