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
import static com.feilong.core.date.DateUtil.addWeek;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;

/**
 * The Class DateUtilAddWeekParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class AddWeekParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<String, Integer, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: DateUtil.addWeek({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] {
                                              { "2016-01-01", 0, "2016-01-01" },
                                              { "2016-08-16", 1, "2016-08-23" },
                                              { "2016-08-16", -1, "2016-08-09" },
                                              { "2016-12-31", 3, "2017-01-21" },
                                              { "2016-01-01", -5, "2015-11-27" },
                //
        };

        return toList(objects);
    }

    /**
     * Test add week.
     */
    @Test
    public void testAddWeek(){
        Date date = toDate(input1, COMMON_DATE);
        assertEquals(toDate(expectedValue, COMMON_DATE), addWeek(date, input2));
    }
}