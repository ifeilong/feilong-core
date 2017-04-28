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
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.feilong.core.lang.StringUtil;

/**
 * The Class StringUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class TokenizeToStringArrayTest{

    //*****************com.feilong.core.lang.StringUtil.tokenizeToStringArray(String, String)************************************************************************************

    /**
     * Tokenize to string array1.
     */
    @Test
    public void tokenizeToStringArray(){
        String str = "jin.xin  feilong ,jinxin;venusdrogon;jim ";
        String delimiters = ";, .";
        assertArrayEquals(
                        toArray("jin", "xin", "feilong", "jinxin", "venusdrogon", "jim"),
                        StringUtil.tokenizeToStringArray(str, delimiters));
    }

    /**
     * Tokenize to string array 1.
     */
    @Test
    public void tokenizeToStringArray1(){
        String delimiters = ";, .";
        assertArrayEquals(EMPTY_STRING_ARRAY, StringUtil.tokenizeToStringArray(null, delimiters));
    }

    /**
     * Tokenize to string array 11.
     */
    @Test
    public void tokenizeToStringArray11(){
        String delimiters = " ";
        assertArrayEquals(EMPTY_STRING_ARRAY, StringUtil.tokenizeToStringArray("   ", delimiters));
    }

    /**
     * Tokenize to string array 112.
     */
    @Test
    public void tokenizeToStringArray112(){
        String delimiters = ",";
        assertArrayEquals(EMPTY_STRING_ARRAY, StringUtil.tokenizeToStringArray("   ", delimiters));
    }
}