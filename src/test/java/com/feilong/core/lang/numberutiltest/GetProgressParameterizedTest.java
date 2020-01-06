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
import com.feilong.test.Abstract3ParamsAndResultParameterizedTest;

import static com.feilong.core.NumberPattern.PERCENT_WITH_1POINT;
import static com.feilong.core.NumberPattern.PERCENT_WITH_2POINT;
import static com.feilong.core.NumberPattern.PERCENT_WITH_NOPOINT;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class NumberUtilGetProgressParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetProgressParameterizedTest
                extends Abstract3ParamsAndResultParameterizedTest<Number, Number, String, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:NumberUtil.getProgress({0}, {1}, \"{2}\")=\"{3}\"")
    public static Iterable<Object[]> data(){
        return toList(
                        ConvertUtil.<Object> toArray(5, 5, PERCENT_WITH_NOPOINT, "100%"),
                        toArray(5, 5, PERCENT_WITH_1POINT, "100.0%"),
                        toArray(5, 5, PERCENT_WITH_2POINT, "100.00%"),

                        toArray(5, 10, PERCENT_WITH_NOPOINT, "50%"),
                        toArray(5, 10, PERCENT_WITH_1POINT, "50.0%"),
                        toArray(5, 10, PERCENT_WITH_2POINT, "50.00%"),

                        toArray(3, 10, PERCENT_WITH_1POINT, "30.0%"),

                        toArray(1, 3, PERCENT_WITH_1POINT, "33.3%"),
                        toArray(2, 3, PERCENT_WITH_1POINT, "66.7%"),

                        toArray(1L, toBigDecimal(3), PERCENT_WITH_1POINT, "33.3%"),

                        toArray(1L, 3L, PERCENT_WITH_1POINT, "33.3%"),
                        toArray(1L, 3, PERCENT_WITH_1POINT, "33.3%"),
                        toArray(1L, 3f, PERCENT_WITH_1POINT, "33.3%"),
                        toArray(1d, 3f, PERCENT_WITH_1POINT, "33.3%")
        //  
        );
    }

    /**
     * Test get progress.
     */
    @Test
    public void testGetProgress(){
        assertEquals(expectedValue, NumberUtil.getProgress(input1, input2, input3));
    }
}
