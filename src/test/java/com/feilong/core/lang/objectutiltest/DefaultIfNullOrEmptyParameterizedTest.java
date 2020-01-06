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
package com.feilong.core.lang.objectutiltest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.lang.ObjectUtil.defaultIfNullOrEmpty;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class ObjectUtilDefaultIfNullOrEmptyParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DefaultIfNullOrEmptyParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<Object, Object, Object>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ObjectUtil.defaultIfNullOrEmpty({0},{1})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = build();
        return toList(objects);
    }

    /**
     * @return
     * @since 1.10.3
     */
    private static Object[][] build(){
        return new Object[][] {
                                new Object[] { null, 1, 1 },
                                new Object[] { null, null, null },
                                new Object[] { new ArrayList<>(), 1, 1 },
                                new Object[] { "", "", "" },
                                new Object[] { "", " ", " " },
                                new Object[] { "", "fei", "fei" },
                                new Object[] { "  ", "feilong", "feilong" },
                                new Object[] { "fl", "feilong", "fl" },
                                new Object[] { " fl", "feilong", " fl" },
                                new Object[] { new int[] {}, "feilong", "feilong" }

        };
    }

    /**
     * Test default if null or empty.
     */
    @Test
    public void testDefaultIfNullOrEmpty(){
        assertEquals(expectedValue, defaultIfNullOrEmpty(input1, input2));
    }

}