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
package com.feilong.core.bean.convertutiltest;

import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toLong;
import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

/**
 * The Class ToStringParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToStringParameterizedTest extends Abstract1ParamAndResultParameterizedTest<Object, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ConvertUtil.toString({0})={1}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { null, null },

                                              { 1L, "1" },
                                              { toLong(8L), "8" },

                                              { new Double(1.0), "1.00" },
                                              { new Float(1.0), "1.00" },
                                              { toBigDecimal(1.0), "1.00" },
                                              {
                                                toDate("2019-06-28 12:00:00.666", COMMON_DATE_AND_TIME_WITH_MILLISECOND),
                                                "2019-06-28 12:00:00" },
                                              {
                                                DateUtils.toCalendar(
                                                                toDate("2019-06-28 12:00:00.666", COMMON_DATE_AND_TIME_WITH_MILLISECOND)),
                                                "2019-06-28 12:00:00" },

                                              { toList("张飞", "关羽", "", "赵云"), "张飞,关羽,,赵云" },
                                              { toArray("张飞", "关羽", "", "赵云"), "张飞,关羽,,赵云" },
                                              { toArray(null, "关羽", "", "赵云"), ",关羽,,赵云" },
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
        assertEquals(expectedValue, ConvertUtil.toString(input1));
    }

}
