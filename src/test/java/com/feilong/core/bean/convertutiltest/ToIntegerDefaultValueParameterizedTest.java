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

import static com.feilong.core.bean.ConvertUtil.toInteger;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class ConvertUtilToIntegerDefaultValueParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToIntegerDefaultValueParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Object, Integer, Integer>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ConvertUtil.toInteger({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { null, null, null },
                                              { null, 1, 1 },
                                              { "aaaa", 1, 1 },

                                              { "1", 2, 1 },
                                              { 8L, 1, 8 },
                                              { "8", 1, 8 },
                                              { new BigDecimal("8"), 1, 8 },

                                              { new String[] { "1", "2", "3" }, 2, 1 },
                                              { new String[] { "1", null, "2", "3" }, 2, 1 },

                                              { toList("1", "2"), 5, 1 },

                                              { "1,2,3", 5, 5 },

                //
        };
        return toList(objects);
    }

    /**
     * Test to integer.
     */
    @Test
    public void testToInteger(){
        assertEquals(expectedValue, toInteger(input1, input2));
    }

}
