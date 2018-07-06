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
package com.feilong.core.lang.classutiltest;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.feilong.core.Alphabet;
import com.feilong.core.lang.ClassUtil;
import com.feilong.core.lang.reflect.ReflectException;

/**
 * The Class ClassUtilGetClassTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetClassTest{

    //****************com.feilong.core.lang.ClassUtil.getClass(String)*************************************

    /**
     * Test get class.
     */
    @Test
    public void testGetClass(){
        assertSame(Alphabet.class, ClassUtil.getClass(Alphabet.class.getName()));
    }

    /**
     * Test get class null class name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetClassNullClassName(){
        ClassUtil.getClass(null);
    }

    /**
     * Test get class empty class name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetClassEmptyClassName(){
        ClassUtil.getClass("");
    }

    /**
     * Test get class blank class name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetClassBlankClassName(){
        ClassUtil.getClass(" ");
    }

    /**
     * Test get class not exist class name.
     */
    @Test(expected = ReflectException.class)
    //@Test
    public void testGetClassNotExistClassName(){
        ClassUtil.getClass("com.feilong.lalala");
    }
}
