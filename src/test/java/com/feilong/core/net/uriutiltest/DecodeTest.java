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
package com.feilong.core.net.uriutiltest;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.net.URIUtil;

import static com.feilong.core.CharsetType.UTF8;

/**
 * The Class URIUtilDecodeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class DecodeTest{

    /**
     * Test decode.
     */
    @Test
    public void testDecode(){
        assertEquals("飞天奔月", URIUtil.decode("%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", UTF8));
    }

    //************************************************

    /**
     * Test decode null value.
     */
    @Test
    public void testDecodeNullValue(){
        assertEquals(EMPTY, URIUtil.decode(null, UTF8));
    }

    /**
     * Test decode empty value.
     */
    @Test
    public void testDecodeEmptyValue(){
        assertEquals(EMPTY, URIUtil.decode("", UTF8));
    }

    /**
     * Test decode blank value.
     */
    @Test
    public void testDecodeBlankValue(){
        assertEquals(EMPTY, URIUtil.decode(" ", UTF8));
    }

    //*******************************************************

    /**
     * Test decode null charset type.
     */
    @Test
    public void testDecodeNullCharsetType(){
        assertEquals("%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", URIUtil.decode("%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", null));
    }

    /**
     * Test decode empty charset type.
     */
    @Test
    public void testDecodeEmptyCharsetType(){
        assertEquals("%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", URIUtil.decode("%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", ""));
    }

    /**
     * Test decode blank charset type.
     */
    @Test
    public void testDecodeBlankCharsetType(){
        assertEquals("%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", URIUtil.decode("%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88", " "));
    }

    /**
     * Test last percent.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLastPercent(){
        URIUtil.decode("%", UTF8);
    }

    /**
     * Test last percent 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLastPercent1(){
        URIUtil.decode("%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88%", UTF8);
    }

    /**
     * Decode2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void decode2(){
        URIUtil.decode("aaaaa%chu111", UTF8);
        // java.lang.IllegalArgumentException: URLDecoder: Illegal hex characters in escape (%) pattern - For input string: "ch"
    }

    /**
     * Decode3.
     */
    @Test(expected = IllegalArgumentException.class)
    public void decode3(){
        URIUtil.decode("%c", UTF8);
    }
}
