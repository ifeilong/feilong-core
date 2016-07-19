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
package com.feilong.core.util.regexpattern;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.AbstractBooleanParameterizedTest;
import com.feilong.core.util.RegexUtil;

import static com.feilong.core.RegexPattern.TELEPHONE_MUST_AREACODE;

/**
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.5.3
 */
public class TelephoneMustAreaCodePatternTest extends AbstractBooleanParameterizedTest<String, Boolean>{

    /**
     * Data.
     *
     * @return the collection
     */
    //@Parameters(name = "index:{index}: matches({0})={1}")
    @Parameters(name = "RegexUtil.matches(RegexPattern.TELEPHONE_MUST_AREACODE, {0})={1}")
    public static Iterable<Object[]> data(){
        String[] valids = { "021-86771588", "021-867715", "021-86771588-888" };
        String[] invalids = {
                              //null,
                              "",
                              "   ",
                              "86771588", //没有区号
                              "02021-86771588-888", //区号3-4位 太长了
                              "86771588-888", //没有区号
                              "020-86771588888", //电话号码6-8位 太长了
                              "021-86775" //电话号码 需要 6-8位
        };
        return toList(valids, invalids);
    }

    @Test
    public void matches(){
        assertEquals(expectedValue, RegexUtil.matches(TELEPHONE_MUST_AREACODE, input));
    }
}