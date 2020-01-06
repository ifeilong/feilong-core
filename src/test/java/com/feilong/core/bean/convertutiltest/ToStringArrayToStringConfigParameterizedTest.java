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
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.bean.ToStringConfig;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class ConvertUtilToStringArrayToStringConfigParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToStringArrayToStringConfigParameterizedTest
                extends Abstract2ParamsAndResultParameterizedTest<Object[], ToStringConfig, String>{

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

    /**
     * @return
     * @since 1.10.3
     */
    private static Object[][] build(){
        int[] int1 = { 2, 1 };
        //        Object[] array = toArray(int1);
        Object[] array = ArrayUtils.toObject(int1);

        ToStringConfig toStringConfig = new ToStringConfig(",");
        Object[] arrays = { "222", "1111" };
        Integer[] array1 = { 2, 1 };

        ToStringConfig toStringConfig1 = new ToStringConfig(",", false);

        Integer[] array2 = { 2, 1, null };
        Integer[] array3 = { 2, null, 1, null };

        String[] ss = { null };

        return new Object[][] {
                                { array, null, "2,1" },

                                { toArray(2), toStringConfig, "2" },
                                { toArray(",", ","), new ToStringConfig(",", true), ",,," },

                                { null, toStringConfig, EMPTY },
                                { toArray(), toStringConfig, EMPTY },

                                //---------------------------------------------------------------

                                //since 1.12.9
                                { toArray("189", "150"), new ToStringConfig(",", false, "code:"), "code:189,code:150" },
                                { toArray("189", null, "150"), new ToStringConfig(",", false, "code:"), "code:189,code:150" },
                                { toArray("189", "", "150"), new ToStringConfig(",", false, "code:"), "code:189,code:150" },
                                { toArray("189", "150"), new ToStringConfig(" OR ", false, "code:"), "code:189 OR code:150" },
                                { toArray("189", "", "150"), new ToStringConfig(" OR ", false, "code:"), "code:189 OR code:150" },
                                { toArray("189", "", "150", ""), new ToStringConfig(" OR ", false, "code:"), "code:189 OR code:150" },
                                { toArray("189", "", "150", ""), new ToStringConfig(" OR ", false, null), "189 OR 150" },
                                { toArray("189", "", "150", ""), new ToStringConfig(" OR ", false, ""), "189 OR 150" },
                                { toArray("189", "", "150", ""), new ToStringConfig(" OR ", false, " "), " 189 OR  150" },
                                { toArray("", "189", "", "150", ""), new ToStringConfig(" OR ", false, "code:"), "code:189 OR code:150" },
                                { toArray("189", "", "150"), new ToStringConfig(",", true, "code:"), "code:189,code:,code:150" },
                                { toArray("189", " ", "150"), new ToStringConfig(",", true, "code:"), "code:189,code: ,code:150" },

                                //---------------------------------------------------------------

                                { arrays, toStringConfig, "222,1111" },
                                { array1, toStringConfig, "2,1" },

                                { array2, toStringConfig1, "2,1" },
                                { array3, toStringConfig1, "2,1" },

                                { toArray(new Integer(2), null), new ToStringConfig(",", true), "2," },
                                { toArray(new Integer(2), null), new ToStringConfig(",", true), "2," },
                                { ss, new ToStringConfig(",", true), EMPTY }, };
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString(){
        assertEquals(expectedValue, ConvertUtil.toString(input1, input2));
    }

}
