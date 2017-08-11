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
package com.feilong.core.util.regexutiltest;

import static com.feilong.core.RegexPattern.MOBILEPHONE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.util.RegexUtil;
import com.feilong.test.AbstractBooleanParameterizedTest;
import com.feilong.test.TestUtil;

/**
 * The Class MobilephonePatternTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public class MobilephonePatternParameterizedTest extends AbstractBooleanParameterizedTest<String, Boolean>{

    /**
     * Data.
     *
     * @return the collection
     */
    @Parameters(name = "RegexUtil.matches(RegexPattern.MOBILEPHONE, {0})={1}")
    public static Iterable<Object[]> data(){
        String[] valids = { "18501646315", "15001841317", "14701841318", "16201841318", "19201841318" };
        String[] invalids = { //
                              "",
                              " ",
                              "1500184131", // count not match
                              " 18501646315", // count not match
                              "18501646315 ", // count not match
                              "10201841318", //no 10
                              "11201841318", //no 11
                              "12201841318" //no 12
        };

        return TestUtil.toDataList(valids, invalids);
    }

    /**
     * Matches.
     */
    @Test
    public void matches(){
        assertEquals(expectedValue, RegexUtil.matches(MOBILEPHONE, input));
    }
}