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
import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

public class GetAddValueParameterizedTest extends Abstract1ParamAndResultParameterizedTest<Number[], BigDecimal>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:NumberUtil.getAddValue({0}, {1})={2}")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(toArray(new BigDecimal(6), 5), toBigDecimal(11)),

                        toArray(toArray(2, 4, 5), toBigDecimal(11)),

                        toArray(toArray(null, 4, 5), toBigDecimal(9)),
                        toArray(toArray(2, null, 5), toBigDecimal(7)),
                        toArray(toArray(2, 4, null), toBigDecimal(6)),

                        toArray(toArray(null, null, 5), toBigDecimal(5)),
                        toArray(toArray(null, 4, null), toBigDecimal(4)),
                        toArray(toArray(2, null, null), toBigDecimal(2))

        //
        );
    }

    /**
     * Test.
     */
    @Test
    public void testGetAddValue(){
        assertEquals(expectedValue, NumberUtil.getAddValue(input1));
    }

}
