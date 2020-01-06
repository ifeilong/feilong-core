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
package com.feilong.core.lang.stringutiltest;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.lang.StringUtil;
import com.feilong.test.Abstract3ParamsAndResultParameterizedTest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class StringUtilSubstringStartIndexAndLengthParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SubstringStartIndexAndLengthParameterizedTest
                extends Abstract3ParamsAndResultParameterizedTest<String, Integer, Integer, String>{

    /** <code>{@value}</code>. */
    private static final String TEXT = "jinxin.feilong";

    /**
     * Test substring.
     */
    @Test
    public void testSubstring(){
        assertEquals(expectedValue, StringUtil.substring(input1, input2, input3));
    }

    /**
     * Data.
     *
     * @return the iterable
     */
    //************************
    @Parameters(name = "index:{index}:StringUtil.substring({0}, {1}, {2})={3}")
    public static Iterable<Object[]> data(){
        return toList(//
                        toArray(null, 6, 8, null),
                        toArray(TEXT, 0, 5, "jinxi"),
                        new Object[] { TEXT, 6, 2, ".f" },

                        toArray(TEXT, TEXT.length(), 8, EMPTY),
                        toArray(TEXT, TEXT.length() - 1, 8, "g"),
                        toArray(TEXT, 1, 0, EMPTY),
                        toArray(TEXT, 6, 20, ".feilong")
        //           
        );
    }
}