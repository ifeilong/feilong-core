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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.Abstract1ParamAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toLong;

/**
 * The Class ConvertUtilToLongParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToLongParameterizedTest extends Abstract1ParamAndResultParameterizedTest<Object, Long>{

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}: ConvertUtil.toLong({0})={1}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              { null, null },
                                              { "aaaa", null },

                                              { "1", 1L },
                                              { 8, 8L },
                                              { "8", 8L },
                                              { new BigDecimal("8"), 8L },

                                              { new String[] { "1", "2", "3" }, 1L },
                                              { new String[] { "1", null, "2", "3" }, 1L },

                                              { toList("1", "2"), 1L },

                                              { "1,2,3", null },

                //
        };
        return toList(objects);
    }

    /**
     * Test to long.
     */
    @Test
    public void testToLong(){
        assertEquals(expectedValue, toLong(input1));
    }

}
