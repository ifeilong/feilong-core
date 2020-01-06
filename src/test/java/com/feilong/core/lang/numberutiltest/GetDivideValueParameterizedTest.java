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

import static java.math.BigDecimal.ZERO;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.NumberUtil;
import com.feilong.test.Abstract3ParamsAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class NumberUtilGetDivideValueParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetDivideValueParameterizedTest
                extends Abstract3ParamsAndResultParameterizedTest<Number, Number, Integer, BigDecimal>{

    /**
     * Test get divide value.
     */
    @Test
    public void testGetDivideValue(){
        assertEquals(expectedValue, NumberUtil.getDivideValue(input1, input2, input3));
    }

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:NumberUtil.getDivideValue({0}, {1}, {2})={3}")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(0, 2, 0, ZERO),
                        toArray(6, 4, 0, toBigDecimal(2)),

                        toArray(10, 3, 2, toBigDecimal("3.33")),
                        toArray(5, 3, 2, toBigDecimal("1.67"))
        //  
        );
    }

}
