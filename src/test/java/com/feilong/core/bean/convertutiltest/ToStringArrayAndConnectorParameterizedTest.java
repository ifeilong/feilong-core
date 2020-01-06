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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ToStringConfig.DEFAULT_CONNECTOR;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class ToStringArrayAndConnectorParameterizedTest.
 */
public class ToStringArrayAndConnectorParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Object[], String, String>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ConvertUtil.toString({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = build();
        return toList(objects);
    }

    //---------------------------------------------------------------

    /**
     * Builds the.
     *
     * @return the object[][]
     */
    private static Object[][] build(){
        int[] int1 = { 2, 1 };
        Object[] array = ArrayUtils.toObject(int1);

        Object[] arrays = { "222", "1111" };
        Integer[] array1 = { 2, 1 };

        Integer[] array2 = { 2, 1, null };
        Integer[] array3 = { 2, null, 1, null };

        String[] ss = { null };

        return new Object[][] {
                                { null, ",", EMPTY },
                                { toArray(), DEFAULT_CONNECTOR, EMPTY },

                                //---------------------------------------------------------------

                                { array, null, "21" },

                                { toArray(2), DEFAULT_CONNECTOR, "2" },
                                { toArray(",", ","), DEFAULT_CONNECTOR, ",,," },

                                { arrays, DEFAULT_CONNECTOR, "222,1111" },
                                { array1, DEFAULT_CONNECTOR, "2,1" },

                                { array2, DEFAULT_CONNECTOR, "2,1," },
                                { array3, DEFAULT_CONNECTOR, "2,,1," },

                                { toArray(new Integer(2), null), DEFAULT_CONNECTOR, "2," },
                                { toArray(new Integer(2), null), DEFAULT_CONNECTOR, "2," },
                                { ss, DEFAULT_CONNECTOR, EMPTY }, };
    }

    //---------------------------------------------------------------

    /**
     * Test to string.
     */
    @Test
    public void testToString(){
        assertEquals(expectedValue, ConvertUtil.toString(input1, input2));
    }

}
