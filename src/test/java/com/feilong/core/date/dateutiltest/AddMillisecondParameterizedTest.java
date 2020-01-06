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

import java.util.Date;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateUtil.addMillisecond;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;

/**
 * The Class DateUtilAddMillisecondParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class AddMillisecondParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<String, Integer, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: DateUtil.addMillisecond({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] {
                                              { "2016-08-16 01:21:00.000", 0, "2016-08-16 01:21:00.000" },
                                              { "2016-08-16 01:21:00.000", 5000, "2016-08-16 01:21:05.000" },
                                              { "2016-08-16 01:21:00.000", -5000, "2016-08-16 01:20:55.000" },
                                              { "2016-08-16 01:21:00.325", -5, "2016-08-16 01:21:00.320" },
                                              { "2016-08-16 01:21:00.325", 15, "2016-08-16 01:21:00.340" },
                                              { "2016-08-16 23:59:59.000", 5000, "2016-08-17 00:00:04.000" },
                //
        };
        return toList(objects);
    }

    /**
     * Test add millisecond.
     */
    @Test
    public void testAddMillisecond(){
        Date date = toDate(input1, COMMON_DATE_AND_TIME_WITH_MILLISECOND);
        assertEquals(toDate(expectedValue, COMMON_DATE_AND_TIME_WITH_MILLISECOND), addMillisecond(date, input2));
    }

}