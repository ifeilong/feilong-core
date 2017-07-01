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
package com.feilong.core;

import static com.feilong.core.CharsetType.GB18030;
import static com.feilong.core.CharsetType.GB2312;
import static com.feilong.core.CharsetType.GBK;
import static com.feilong.core.CharsetType.ISO_8859_1;
import static com.feilong.core.CharsetType.UTF8;
import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import org.junit.Test;

/**
 * The Class CharsetTypeTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CharsetTypeTest{

    /**
     * Checks if is supported.
     */
    @Test
    public void testIsSupported(){
        assertEquals(true, Charset.isSupported(ISO_8859_1));
        assertEquals(true, Charset.isSupported(GB18030));
        assertEquals(true, Charset.isSupported(GB2312));
        assertEquals(true, Charset.isSupported(GBK));
        assertEquals(true, Charset.isSupported(UTF8));
    }

    @Test
    public void testUtf8(){
        assertEquals("UTF-8", CharsetType.UTF8);
    }

    @Test
    public void testIso88591(){
        assertEquals("ISO-8859-1", CharsetType.ISO_8859_1);
    }
}