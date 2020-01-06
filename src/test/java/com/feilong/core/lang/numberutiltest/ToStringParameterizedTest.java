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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.NumberUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

import static com.feilong.core.NumberPattern.NO_SCALE;
import static com.feilong.core.NumberPattern.PERCENT_WITH_1POINT;
import static com.feilong.core.NumberPattern.PERCENT_WITH_2POINT;
import static com.feilong.core.NumberPattern.PERCENT_WITH_NOPOINT;
import static com.feilong.core.NumberPattern.TWO_DECIMAL_POINTS;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class NumberUtilToStringParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToStringParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Number, String, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:NumberUtil.toString({0},\"{1}\")=\"{2}\"")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(0, PERCENT_WITH_NOPOINT, "0%"),
                        toArray(1, PERCENT_WITH_NOPOINT, "100%"),
                        toArray(100, PERCENT_WITH_NOPOINT, "10000%"),

                        toArray(0, PERCENT_WITH_1POINT, "0.0%"),
                        toArray(1, PERCENT_WITH_1POINT, "100.0%"),
                        toArray(100, PERCENT_WITH_1POINT, "10000.0%"),

                        toArray(0, PERCENT_WITH_2POINT, "0.00%"),
                        toArray(1, PERCENT_WITH_2POINT, "100.00%"),
                        toArray(100, PERCENT_WITH_2POINT, "10000.00%"),

                        toArray(88.02, TWO_DECIMAL_POINTS, "88.02"),
                        toArray(88.028, TWO_DECIMAL_POINTS, "88.03"),

                        toArray(88.02, "#######.########", "88.02"),
                        toArray(88.020, "#######.########", "88.02"),
                        toArray(88.02002, "#######.########", "88.02002"),
                        toArray(88, "#######.########", "88"),
                        toArray(88.02000005, "#######.########", "88.02000005"),
                        toArray(88.02500000, "#######.########", "88.025"),
                        toArray(88.0200005, "#######.########", "88.0200005"),
                        toArray(88.002, "#######.########", "88.002"),

                        toArray(8, "C00000000", "C00000008"),

                        toArray(0.24f, PERCENT_WITH_NOPOINT, "24%"),
                        toArray(0.24f, PERCENT_WITH_2POINT, "24.00%"),

                        toArray(-88.067, TWO_DECIMAL_POINTS, "-88.07"),
                        toArray(-88.6, TWO_DECIMAL_POINTS, "-88.60"),

                        toArray(88.6, NO_SCALE, "89"),
                        toArray(-88.6, NO_SCALE, "-89"),

                        toArray(0.8, NO_SCALE, "1"),
                        toArray(-0.8, NO_SCALE, "-1"),
                        toArray(-1.8, NO_SCALE, "-2"),
                        toArray(1.8, NO_SCALE, "2"),

                        toArray((double) 1 / 400L, PERCENT_WITH_2POINT, "0.25%"),
                        toArray((double) 5 / 8 * 100, "#######.###", "62.5"),
                        toArray((double) 5 / 8 * 100, "######0", "63"),

                        toArray(111111.5, NO_SCALE, "111112"),
                        toArray(111112.5, NO_SCALE, "111113"),
                        toArray(88888888, NO_SCALE, "88888888"),

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

    /**
     * Test get progress.
     */
    @Test
    public void testGetProgress(){
        assertEquals(expectedValue, NumberUtil.toString(input1, input2));
    }
}
