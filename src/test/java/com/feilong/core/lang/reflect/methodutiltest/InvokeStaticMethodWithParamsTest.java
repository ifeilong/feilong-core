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
package com.feilong.core.lang.reflect.methodutiltest;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_OBJECT_ARRAY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.lang.StringUtil;
import com.feilong.core.lang.reflect.MethodUtil;
import com.feilong.core.lang.reflect.ReflectException;

public class InvokeStaticMethodWithParamsTest{

    /**
     * Test invoke method all pack type.
     */
    @Test
    public void testInvokeMethodAllPackType(){
        assertEquals("static age Integer:5", MethodUtil.invokeStaticMethod(OverloadStaticMethod.class, "age", 5));
        assertEquals("static age Integer:5", MethodUtil.invokeStaticMethod(OverloadStaticMethod.class, "age", Integer.parseInt("5")));
    }

    /**
     * Test invoke static method.
     */
    @Test
    public void testInvokeStaticMethod1(){
        assertEquals("eilong", MethodUtil.invokeStaticMethod(StringUtil.class, "substring", "feilong", 1));
    }

    /**
     * Test invoke static parent method.
     */
    @Test
    public void testInvokeStaticParentMethod(){
        assertEquals("parent static method", MethodUtil.invokeStaticMethod(SimpleChild.class, "getStaticMessage"));
    }

    /**
     * Test invoke static parent null params.
     */
    @Test
    public void testInvokeStaticParentNullParams(){
        assertEquals("parent static method", MethodUtil.invokeStaticMethod(SimpleChild.class, "getStaticMessage", null));
    }

    /**
     * Test invoke static parent empty params.
     */
    @Test
    public void testInvokeStaticParentEmptyParams(){
        assertEquals("parent static method", MethodUtil.invokeStaticMethod(SimpleChild.class, "getStaticMessage", EMPTY_OBJECT_ARRAY));
    }

    /**
     * Test invoke method not static.
     */
    @Test(expected = ReflectException.class)
    public void testInvokeMethodNotStatic(){
        MethodUtil.invokeStaticMethod(SimpleChild.class, "getMessage");
    }

    /**
     * Test invoke static not exist method.
     */
    @Test(expected = ReflectException.class)
    public void testInvokeStaticNotExistMethod(){
        MethodUtil.invokeStaticMethod(StringUtil.class, "substring11", EMPTY_OBJECT_ARRAY);
    }

    /**
     * Test invoke static method null class.
     */
    @Test(expected = NullPointerException.class)
    public void testInvokeStaticMethodNullClass(){
        MethodUtil.invokeStaticMethod(null, "substring", "feilong", "ei");
    }

    /**
     * Test invoke static method null method.
     */
    @Test(expected = NullPointerException.class)
    public void testInvokeStaticMethodNullMethod(){
        MethodUtil.invokeStaticMethod(StringUtil.class, null, "feilong", "ei");
    }

    /**
     * Test invoke static method empty method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvokeStaticMethodEmptyMethod(){
        MethodUtil.invokeStaticMethod(StringUtil.class, "", "feilong", "ei");
    }

    /**
     * Test invoke static method blank method.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvokeStaticMethodBlankMethod(){
        MethodUtil.invokeStaticMethod(StringUtil.class, "", "feilong", "ei");
    }
}
