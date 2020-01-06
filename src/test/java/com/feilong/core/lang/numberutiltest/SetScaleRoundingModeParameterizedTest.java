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

import static java.math.RoundingMode.HALF_UP;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.NumberUtil;
import com.feilong.test.Abstract3ParamsAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class NumberUtilSetScaleRoundingModeParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SetScaleRoundingModeParameterizedTest
                extends Abstract3ParamsAndResultParameterizedTest<Number, Integer, RoundingMode, BigDecimal>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:NumberUtil.setScale({0}, {1}, {2})={3}")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(5, 5, HALF_UP, toBigDecimal("5.00000")), //
                        toArray(5.2, 3, HALF_UP, toBigDecimal("5.200")),
                        toArray(5.26, 1, HALF_UP, toBigDecimal("5.3")),

                        toArray(-0, 1, HALF_UP, toBigDecimal("0.0")),

                        toArray(0, 1, HALF_UP, toBigDecimal("0.0")),
                        toArray(0, 2, HALF_UP, toBigDecimal("0.00")),

                        toArray(5, 5, null, toBigDecimal("5.00000")), //
                        toArray(5.2, 3, null, toBigDecimal("5.200")),
                        toArray(5.26, 1, null, toBigDecimal("5.3")),

                        toArray(-0, 1, null, toBigDecimal("0.0")),

                        toArray(0, 1, null, toBigDecimal("0.0")),
                        toArray(0, 2, null, toBigDecimal("0.00"))
        //  
        );
    }

    /**
     * Test set scale.
     */
    @Test
    public void testSetScale(){
        assertEquals(expectedValue, NumberUtil.setScale(input1, input2, input3));
    }
}
