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

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toIntegers;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class ConvertUtilToIntegerParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToIntegersParameterizedTest extends Abstract1ParamAndResultParameterizedTest<Object, Integer[]>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ConvertUtil.toIntegers({0})={1}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { null, null },
                                              //{ "aaaa", new Integer[] {} },

                                              { "1,2,3", new Integer[] { 1, 2, 3 } },
                                              { "{1,2,3}", new Integer[] { 1, 2, 3 } },
                                              { "{ 1 ,2,3}", new Integer[] { 1, 2, 3 } },
                                              { "1,2, 3", new Integer[] { 1, 2, 3 } },
                                              { "1,2 , 3", new Integer[] { 1, 2, 3 } },
                                              { new String[] { "1", "2", "3" }, new Integer[] { 1, 2, 3 } },
                                              { toList("1", "2", "3"), new Integer[] { 1, 2, 3 } },
                                              { toList("1", "2", " 3"), new Integer[] { 1, 2, 3 } },

                                              { toArray(true, false, false), new Integer[] { 1, 0, 0 } },
                                              { new String[] { "1", null, "2", "3" }, new Integer[] {} },
                //
        };
        return toList(objects);
    }

    /**
     * Test to integers.
     */
    @Test
    public void testToIntegers(){
        assertArrayEquals(expectedValue, toIntegers(input1));
    }

}
