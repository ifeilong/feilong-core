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

import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateUtil.getYear;
import static com.feilong.core.date.DateUtil.toDate;

import static com.feilong.core.DatePattern.COMMON_DATE;

/**
 * The Class DateUtilGetYearParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetYearParameterizedTest extends Abstract1ParamAndResultParameterizedTest<String, Integer>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: DateUtil.getYear({0})={1}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { "2012-06-29", 2012 },
                                              { "2016-07-16", 2016 },
                                              { "2016-13-16", 2017 },
                //
        };
        return toList(objects);
    }

    /**
     * Test get year.
     */
    @Test
    public void testGetYear(){
        Date date = toDate(input1, COMMON_DATE);
        assertEquals(expectedValue, (Integer) getYear(date));
    }

}