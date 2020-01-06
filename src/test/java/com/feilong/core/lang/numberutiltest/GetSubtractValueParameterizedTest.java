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

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.NumberUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class GetSubtractValueParameterizedTest.
 */
public class GetSubtractValueParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Number, Number[], BigDecimal>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:NumberUtil.getSubtractValue({0}, {1})={2}")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(0, toArray(2, 3), toBigDecimal(-5)),

                        toArray(0, null, toBigDecimal(0)),
                        toArray(0, new Integer[5], toBigDecimal(0)),

                        toArray(1000, toArray(50, null), toBigDecimal(950)),
                        toArray(1000, toArray(50, 100), toBigDecimal(850)),
                        toArray(-1000, toArray(-50, 100), toBigDecimal(-1050)),
                        toArray(1000, toArray(99.5, 99.0), toBigDecimal(801.5)),

                        toArray(2, toArray(1.1), toBigDecimal(0.9))

        //
        );
    }

    /**
     * Test.
     */
    @Test
    public void testGetSubtractValue(){
        assertEquals(expectedValue, NumberUtil.getSubtractValue(input1, input2));
    }

}
