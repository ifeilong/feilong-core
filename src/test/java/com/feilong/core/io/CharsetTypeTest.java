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
package com.feilong.core.io;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import org.junit.Test;

import com.feilong.core.lang.CharsetType;

/**
 * The Class CharsetTypeTest.
 * 
 * @author feilong
 * @version 1.0 2012-4-5 下午5:27:11
 */
public class CharsetTypeTest{

    /**
     * Checks if is supported.
     */
    @Test
    public void isSupported(){
        assertEquals(true, Charset.isSupported(CharsetType.ISO_8859_1));
    }
}