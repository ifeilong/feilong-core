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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.lang.StringUtil;
import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * The Class StringUtilFormatParameterizedTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class FormatParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<String, Object[], String>{

    /**
     * Test format.
     */
    @Test
    public void testFormat(){
        assertEquals(expectedValue, StringUtil.format(input1, input2));
    }

    /**
     * Data.
     *
     * @return the iterable
     */
    @Parameters(name = "index:{index}:StringUtil.format(\"{0}\", {1})=\"{2}\"")
    public static Iterable<Object[]> data(){
        Object[] arrays = new Object[] { "%d", toArray(5), "5" };
        return toList(
                        arrays,
                        toArray(null, toArray(), EMPTY), // null
                        toArray("%+d", toArray(5), "+5"),
                        toArray("%+d", toArray(-5), "-5"),
                        toArray("%-5d", toArray(-5), "-5   "),
                        toArray("% 5d", toArray(-5), "   -5"),
                        toArray("%05d", toArray(-5), "-0005"),
                        toArray("%03d", toArray(1), "001"),
                        toArray("今天天气不错", toArray(1), "今天天气不错")
        //        
        );
    }
}