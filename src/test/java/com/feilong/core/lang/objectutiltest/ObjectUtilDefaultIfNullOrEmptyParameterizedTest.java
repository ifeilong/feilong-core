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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.AbstractTwoParamsAndOneResultParameterizedTest;

import static com.feilong.core.lang.ObjectUtil.defaultIfNullOrEmpty;

/**
 * The Class ObjectUtilDefaultIfNullOrEmptyParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ObjectUtilDefaultIfNullOrEmptyParameterizedTest extends AbstractTwoParamsAndOneResultParameterizedTest<Object, Object, Object>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ObjectUtil.defaultIfNullOrEmpty({0},{1})={2}")
    public static Iterable<Object[]> data(){
        List<Object[]> list = new ArrayList<>();
        list.add(new Object[] { null, 1, 1 });
        list.add(new Object[] { null, null, null });
        list.add(new Object[] { new ArrayList<>(), 1, 1 });
        list.add(new Object[] { "", "", "" });
        list.add(new Object[] { "", " ", " " });
        list.add(new Object[] { "", "fei", "fei" });
        list.add(new Object[] { "  ", "feilong", "feilong" });
        list.add(new Object[] { "fl", "feilong", "fl" });
        list.add(new Object[] { " fl", "feilong", " fl" });
        list.add(new Object[] { new int[] {}, "feilong", "feilong" });
        return list;
    }

    /**
     * Test default if null or empty.
     */
    @Test
    public void testDefaultIfNullOrEmpty(){
        assertEquals(expectedValue, defaultIfNullOrEmpty(input1, input2));
    }

}