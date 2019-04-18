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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.util.RegexUtil;

public class MatchesTest{

    private static final String EMAIL         = "feilong@163.com";

    private static final String REGEX_PATTERN = "(.*?)@(.*?)";

    @Test
    public void testDecimalTwoDigit(){
        String decimalTwoDigit = "^[0-9]+(.[0-9]{2})?$";
        assertEquals(false, RegexUtil.matches(decimalTwoDigit, "2000阿.00"));
        assertEquals(false, RegexUtil.matches(decimalTwoDigit, "2000.0"));
        assertEquals(true, RegexUtil.matches(decimalTwoDigit, "2000.99"));
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testMatchesNullRegexPattern(){
        RegexUtil.matches(null, EMAIL);
    }

    @Test
    public void testMatchesNullInput(){
        assertEquals(false, RegexUtil.matches(REGEX_PATTERN, null));
    }
}
