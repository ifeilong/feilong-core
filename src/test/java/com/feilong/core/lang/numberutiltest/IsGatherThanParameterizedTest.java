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
package com.feilong.core.lang.numberutiltest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.NumberUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

public class IsGatherThanParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Number, Number, Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index},NumberUtil.isGatherThan({0}, {1})={2}")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(0, 0, false),

                        toArray(-1, 0.0f, false),

                        //int equals
                        toArray(5, 5, false), //int
                        toArray(5, 5L, false), //long
                        toArray(5, 5.0f, false), //float
                        toArray(5, 5.0d, false), //double
                        toArray(5, toBigDecimal(5.0d), false), //bigDecimal
                        toArray(new Integer(5), toBigDecimal(5.0d), false), //Integer

                        toArray(5, 4, true), //int
                        toArray(5, 4L, true), //long
                        toArray(5, 4.0f, true), //float
                        toArray(5, 4.0d, true), //double
                        toArray(5, toBigDecimal(4.0d), true), //bigDecimal

                        //long 
                        toArray(5L, 5, false), //int
                        toArray(5L, 5L, false), //long
                        toArray(5L, 5.0f, false), //float
                        toArray(5L, 5.0d, false), //double
                        toArray(5L, toBigDecimal(5.0d), false), //bigDecimal

                        toArray(5L, 4, true), //int
                        toArray(5L, 4L, true), //long
                        toArray(5L, 4.0f, true), //float
                        toArray(5L, 4.0d, true), //double
                        toArray(5L, toBigDecimal(4.0d), true), //bigDecimal

                        //float

                        toArray(5.0f, 5, false), //int
                        toArray(5.0f, 5L, false), //long
                        toArray(5.0f, 5.0f, false), //float
                        toArray(5.0f, 5.0d, false), //double
                        toArray(5.0f, toBigDecimal(5.0d), false), //bigDecimal

                        toArray(5.0f, 4, true), //int
                        toArray(5.0f, 4L, true), //long
                        toArray(5.0f, 4.0f, true), //float
                        toArray(5.0f, 4.0d, true), //double
                        toArray(5.0f, toBigDecimal(4.0d), true), //bigDecimal

                        //double
                        toArray(5.0d, 5, false), //int
                        toArray(5.0d, 5L, false), //long
                        toArray(5.0d, 5.0f, false), //float
                        toArray(5.0d, 5.0d, false), //double
                        toArray(5.0d, toBigDecimal(5.0d), false), //bigDecimal

                        toArray(5.0d, 4, true), //int
                        toArray(5.0d, 4L, true), //long
                        toArray(5.0d, 4.0f, true), //float
                        toArray(5.0d, 4.0d, true), //double
                        toArray(5.0d, toBigDecimal(4.0d), true), //bigDecimal

                        //bigdecimal

                        toArray(toBigDecimal(5.0d), 5, false), //int
                        toArray(toBigDecimal(5.0d), 5L, false), //long
                        toArray(toBigDecimal(5.0d), 5.0f, false), //float
                        toArray(toBigDecimal(5.0d), 5.0d, false), //double
                        toArray(toBigDecimal(5.0d), toBigDecimal(5.0d), false), //bigDecimal

                        toArray(toBigDecimal(5.0d), 4, true), //int
                        toArray(toBigDecimal(5.0d), 4L, true), //long
                        toArray(toBigDecimal(5.0d), 4.0f, true), //float
                        toArray(toBigDecimal(5.0d), 4.0d, true), //double
                        toArray(toBigDecimal(5.0d), toBigDecimal(4.0d), true)//bigDecimal

        //
        );
    }

    /**
     * Test.
     */
    @Test
    public void testGetSubtractValue(){
        assertEquals(expectedValue, NumberUtil.isGatherThan(input1, input2));
    }

}
