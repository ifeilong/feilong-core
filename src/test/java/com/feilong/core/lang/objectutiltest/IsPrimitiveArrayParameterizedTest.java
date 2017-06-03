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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.lang.ObjectUtil;
import com.feilong.test.AbstractBooleanParameterizedTest;
import com.feilong.test.TestUtil;

/**
 * The Class ObjectUtilIsPrimitiveArrayParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class IsPrimitiveArrayParameterizedTest extends AbstractBooleanParameterizedTest<Object, Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ObjectUtil.isPrimitiveArray({0})={1}")
    public static Iterable<Object[]> data(){
        Object[] valids = {
                            new int[] {}, //
                            new int[] { 1, 2 },
                            new byte[] { 1, 2 } };

        Object[] invalids = {
                              1, //
                              1L,
                              "1",
                              new String[] { "1", "2" } };
        return TestUtil.toDataList(valids, invalids);
    }

    /**
     * Test is array.
     */
    @Test
    public void testIsPrimitiveArray(){
        assertEquals(expectedValue, ObjectUtil.isPrimitiveArray(input));
    }
}