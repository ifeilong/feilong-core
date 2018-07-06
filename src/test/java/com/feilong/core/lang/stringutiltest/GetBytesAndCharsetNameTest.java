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

import static com.feilong.core.CharsetType.UTF8;

import org.junit.Test;

import com.feilong.core.UncheckedIOException;
import com.feilong.core.lang.StringUtil;

public class GetBytesAndCharsetNameTest{

    @Test(expected = NullPointerException.class)
    public void testGetBytesTestNull(){
        StringUtil.getBytes(null, UTF8);
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testGetBytesAndCharsetNameTestNull(){
        StringUtil.getBytes("", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBytesAndCharsetNameTestEmpty(){
        StringUtil.getBytes("", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetBytesAndCharsetNameTestBlank(){
        StringUtil.getBytes("", " ");
    }

    //---------------------------------------------------------------

    @Test(expected = UncheckedIOException.class)
    //@Test
    public void testGetBytesAndCharsetNameTestBlank1(){
        StringUtil.getBytes("", "aaaaa");
    }
}