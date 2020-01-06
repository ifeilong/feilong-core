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

import static com.feilong.core.bean.ConvertUtil.toBoolean;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

/**
 * The Class ConvertUtilToBooleanParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToBooleanParameterizedTest extends Abstract1ParamAndResultParameterizedTest<Object, Boolean>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ConvertUtil.toBoolean({0})={1}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { null, null },

                                              { true, true },

                                              { "true", true },
                                              { "yes", true },
                                              { "y", true },
                                              { "on", true },
                                              { "1", true },
                                              { 1L, true },

                                              //---------------------------------------------------------------

                                              { "TRUE", true },
                                              { "YES", true },
                                              { "Y", true },
                                              { "ON", true },

                                              //---------------------------------------------------------------

                                              { false, false },

                                              { "false", false },
                                              { "no", false },
                                              { "n", false },
                                              { "off", false },

                                              { "FALSE", false },
                                              { "NO", false },
                                              { "N", false },
                                              { "OFF", false },

                                              //---------------------------------------------------------------

                                              { "0", false },
                                              { "9", null },

                                              { new String[] { "0", "1", "2", "3" }, false },
                                              { new String[] { "1", null, "2", "3" }, true },
                                              { "1,2,3", null },//false
                //
        };
        return toList(objects);
    }

    /**
     * Test to boolean.
     */
    @Test
    public void testToBoolean(){
        assertEquals(expectedValue, toBoolean(input1));
    }

}
