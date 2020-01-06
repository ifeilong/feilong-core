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
import static com.feilong.core.date.DateUtil.addDay;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;

/**
 * The Class DateUtilAddDayParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class AddDayParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<String, Integer, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: DateUtil.addDay({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] {
                                              { "2016-02-28 02:10:05", 5, "2016-03-04 02:10:05" }, // 28 29
                                              { "2014-12-31 02:10:05", 5, "2015-01-05 02:10:05" },
                                              { "2016-01-01 02:10:05", -5, "2015-12-27 02:10:05" },
                //
        };
        return toList(objects);
    }

    /**
     * Test add day.
     */
    @Test
    public void testAddDay(){
        //28 29
        Date date = toDate(input1, COMMON_DATE_AND_TIME);
        assertEquals(toDate(expectedValue, COMMON_DATE_AND_TIME), addDay(date, input2));
    }

}