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
package com.feilong.core.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.AbstractTwoParamsAndOneResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;

public class NumberFormatUtilFormatParameterizedTest extends AbstractTwoParamsAndOneResultParameterizedTest<Number, String, String>{

    @Parameters(name = "index:{index}:NumberFormatUtil.format({0}, \"{1}\")=\"{2}\"")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(25.5, "#####", "26"),
                        toArray(25.5, "RP #####", "RP 26"),

                        toArray(toBigDecimal(1.15), "#####.#", "1.2"),
                        toArray(toBigDecimal(1.25), "#####.#", "1.3"),
                        toArray(toBigDecimal(1.251), "#####.#", "1.3"),

                        toArray(toBigDecimal(-1.15), "#####.#", "-1.2"),
                        toArray(toBigDecimal(-1.25), "#####.#", "-1.3"),
                        toArray(toBigDecimal(-1.251), "#####.#", "-1.3")
        //  
        );
    }

    @Test
    public void testFormat(){
        assertEquals(expectedValue, NumberFormatUtil.format(input1, input2));
    }
}