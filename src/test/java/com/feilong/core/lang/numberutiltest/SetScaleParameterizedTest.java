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

/**
 * The Class NumberUtilSetScaleParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SetScaleParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Number, Integer, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:NumberUtil.setScale({0}, {1})=\"{2}\"")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(5, 5, toBigDecimal("5.00000")), //
                        toArray(5.2, 3, toBigDecimal("5.200")),
                        toArray(5.26, 1, toBigDecimal("5.3")),

                        toArray(-0, 1, toBigDecimal("0.0")),

                        toArray(0, 1, toBigDecimal("0.0")),
                        toArray(2.5, 0, toBigDecimal("3")),
                        toArray(0, 2, toBigDecimal("0.00"))

        //  
        );
    }

    /**
     * Test get progress.
     */
    @Test
    public void testGetProgress(){
        assertEquals(expectedValue, NumberUtil.setScale(input1, input2));
    }
}
