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

/**
 * The Class RegexUtilGroupIntTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GroupIntTest{

    private static final String EMAIL         = "feilong@163.com";

    private static final String REGEX_PATTERN = "(.*?)@(.*?)";

    /**
     * Test group.
     */
    @Test
    public void testGroup(){
        assertEquals(EMAIL, RegexUtil.group(REGEX_PATTERN, EMAIL, 0));
        assertEquals("feilong", RegexUtil.group(REGEX_PATTERN, EMAIL, 1));
        assertEquals("163.com", RegexUtil.group(REGEX_PATTERN, EMAIL, 2));
    }

    @Test
    public void testGroupNotMatch(){
        //不匹配
        assertEquals(null, RegexUtil.group(REGEX_PATTERN, "feilong", 0));
    }

    @Test
    public void testGroupOut(){
        //超出
        assertEquals(null, RegexUtil.group(REGEX_PATTERN, EMAIL, 3));
    }

    //---------------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void testGroup1(){
        RegexUtil.group(REGEX_PATTERN, EMAIL, -1);
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testGroupNullRegexPattern(){
        RegexUtil.group(null, EMAIL, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testGroupNullInput(){
        RegexUtil.group(REGEX_PATTERN, null, 1);
    }
}
