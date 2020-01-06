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

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateExtensionUtil.getIntervalDay;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.Abstract3ParamsAndResultParameterizedTest;

/**
 * The Class DateExtensionUtilGetIntervalDayParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetIntervalDayParameterizedTest extends Abstract3ParamsAndResultParameterizedTest<String, String, String, Integer>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:DateExtensionUtil.getIntervalDay(toDate(\"{0}\",\"{2}\"), toDate(\"{1}\",\"{2}\"))={3}")
    public static Iterable<Object[]> data(){
        return toList(//
                        ConvertUtil.<Object> toArray("2008-08-24", "2008-08-27", COMMON_DATE, 3),

                        toArray("2017-07-29 03:22:44", "2017-07-29 05:22:44", COMMON_DATE_AND_TIME, 0),
                        toArray("2016-08-21", "2016-08-21", COMMON_DATE, 0),
                        toArray("2016-08-21", "2016-08-22", COMMON_DATE, 1),

                        toArray("2016-02-28", "2016-03-02", COMMON_DATE, 3),
                        toArray("2016-08-31", "2016-09-02", COMMON_DATE, 2),

                        toArray("2016-08-21 12:00:00", "2016-08-22 11:00:00", COMMON_DATE_AND_TIME, 0),

                        toArray("2008-12-1", "2008-9-29", COMMON_DATE, 63)
        //  
        );
    }

    /**
     * Test get interval day.
     */
    @Test
    public void testGetIntervalDay(){
        assertEquals(expectedValue, (Integer) getIntervalDay(toDate(input1, input3), toDate(input2, input3)));
    }
}