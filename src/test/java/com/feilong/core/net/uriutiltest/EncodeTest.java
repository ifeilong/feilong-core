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
 * The Class URIUtilEncodeTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class EncodeTest{

    /**
     * Test encode.
     */
    @Test
    public void testEncode(){
        assertEquals("Lifestyle+%2F+Graphic%2C", URIUtil.encode("Lifestyle / Graphic,", UTF8));
    }

    /**
     * Test encode2.
     */
    @Test
    public void testEncode2(){

        assertEquals("%25", URIUtil.encode("%", UTF8));
        assertEquals("%2525", URIUtil.encode("%25", UTF8));
    }

    //**************************************************************************

    /**
     * Test encode null value.
     */
    @Test
    public void testEncodeNullValue(){
        assertEquals(EMPTY, URIUtil.encode(null, UTF8));
    }

    /**
     * Test encode empty value.
     */
    @Test
    public void testEncodeEmptyValue(){
        assertEquals(EMPTY, URIUtil.encode("", UTF8));
    }

    /**
     * Test encode blank value.
     */
    @Test
    public void testEncodeBlankValue(){
        assertEquals(EMPTY, URIUtil.encode(" ", UTF8));
    }

    /**
     * Test encode null charset type.
     */
    @Test
    public void testEncodeNullCharsetType(){
        assertEquals("白色/黑色/纹理浅麻灰", URIUtil.encode("白色/黑色/纹理浅麻灰", null));
    }

    /**
     * Test encode empty charset type.
     */
    @Test
    public void testEncodeEmptyCharsetType(){
        assertEquals("白色/黑色/纹理浅麻灰", URIUtil.encode("白色/黑色/纹理浅麻灰", ""));
    }

    /**
     * Test encode blank charset type.
     */
    @Test
    public void testEncodeBlankCharsetType(){
        assertEquals("白色/黑色/纹理浅麻灰", URIUtil.encode("白色/黑色/纹理浅麻灰", " "));
    }
}
