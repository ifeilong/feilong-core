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

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

public class ToStringParameterizedTest extends Abstract1ParamAndResultParameterizedTest<String, String>{

    private static Date date = toDate("2018-01-02 01:53:00", COMMON_DATE_AND_TIME);

    @Parameters(name = "index:{index}, DateUtil.toString(toDate(\"2018-01-02 01:53:00\", COMMON_DATE_AND_TIME), {0})={1}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { DatePattern.CHINESE_DATE, "2018年01月02日" },
                                              { "M月d日 HH:mm", "1月2日 01:53" },
                                              { DatePattern.CHINESE_DATE_AND_TIME, "2018年01月02日 01:53:00" },

                                              { DatePattern.COMMON_DATE, "2018-01-02" },
                                              { DatePattern.COMMON_DATE_AND_TIME, "2018-01-02 01:53:00" },
                                              { DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND, "2018-01-02 01:53:00.000" },
                                              { DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND, "2018-01-02 01:53" },
                                              { DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND, "01-02 01:53" },
                                              { DatePattern.COMMON_TIME, "01:53:00" },
                                              { DatePattern.COMMON_TIME_WITHOUT_SECOND, "01:53" },

                                              { DatePattern.INDONESIA_DATE, "02/01/2018" },
                                              { DatePattern.INDONESIA_DATE_AND_TIME, "02/01/2018 01:53:00" },

                                              { DatePattern.DOTS_DATE, "2018.01.02" },
                                              { DatePattern.DOTS_DATE_AND_TIME, "2018.01.02 01:53:00" },
                                              { DatePattern.DOTS_DATE_AND_TIME_WITH_MILLISECOND, "2018.01.02 01:53:00.000" },
                                              { DatePattern.DOTS_DATE_AND_TIME_WITHOUT_SECOND, "2018.01.02 01:53" },

                                              { DatePattern.HH, "01" },
                                              { DatePattern.MM, "01" },

                                              { DatePattern.MONTH_AND_DAY, "01-02" },
                                              { DatePattern.MONTH_AND_DAY_WITH_WEEK, "01-02(星期二)" },

                                              { DatePattern.TIMESTAMP, "20180102015300" },
                                              { DatePattern.TIMESTAMP_WITH_MILLISECOND, "20180102015300000" },

                                              { DatePattern.TO_STRING_STYLE, "星期二 一月 02 01:53:00 CST 2018" },

                                              { DatePattern.YEAR_AND_MONTH, "2018-01" },

                                              { DatePattern.BASIC_ISO_DATE, "20180102" },
                //
        };
        return toList(objects);
    }

    //---------------------------------------------------------------

    /**
     * Test to string.
     */
    @Test
    public void testToString(){
        assertEquals(expectedValue, DateUtil.toString(date, input1));
    }

}