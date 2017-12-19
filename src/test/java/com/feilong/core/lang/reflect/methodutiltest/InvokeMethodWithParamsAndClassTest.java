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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_CLASS_ARRAY;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_OBJECT_ARRAY;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import com.feilong.core.lang.reflect.MethodUtil;
import com.feilong.core.lang.reflect.ReflectException;
import com.feilong.store.member.User;

/**
 * The Class MethodUtilInvokeMethodWithParamsAndClassTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class InvokeMethodWithParamsAndClassTest{

    /**
     * Test invoke method all pack type.
     */
    @Test
    public void testInvokeMethodAllPackType(){
        Class<?>[] parameterTypes1 = { Integer.TYPE };
        assertEquals("age int:5", MethodUtil.invokeMethod(new OverloadMethod(), "age", toArray(5), parameterTypes1));

        Class<?>[] parameterTypes2 = { Integer.class };
        assertEquals(
                        "age Integer:5",
                        MethodUtil.invokeMethod(new OverloadMethod(), "age", toArray(Integer.parseInt("5")), parameterTypes2));
    }

    /**
     * Test invoke method null params.
     */
    @Test
    public void testInvokeMethodNullParams(){
        assertEquals(0L, MethodUtil.invokeMethod(new User(), "getId", null, EMPTY_CLASS_ARRAY));
    }

    /**
     * Test invoke method null params and null classs.
     */
    @Test
    public void testInvokeMethodNullParamsAndNullClasss(){
        assertEquals(0L, MethodUtil.invokeMethod(new User(), "getId", null, null));
    }

    /**
     * Test invoke method empty params.
     */
    @Test
    public void testInvokeMethodEmptyParams(){
        assertEquals(0L, MethodUtil.invokeMethod(new User(), "getId", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY));
    }

    /**
     * Test invoke method private empty params.
     */
    //*******************PrivateMethod********************************************************
    @Test(expected = ReflectException.class)
    public void testInvokeMethodPrivateEmptyParams(){
        assertEquals(0L, MethodUtil.invokeMethod(new PrivateMethod(), "name", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY));
    }

    /**
     * Test invoke method private method empty params.
     *
     * @throws NoSuchMethodException
     *             the no such method exception
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     */
    @Test(expected = NoSuchMethodException.class)
    public void testInvokeMethodPrivateMethodEmptyParams() throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        org.apache.commons.lang3.reflect.MethodUtils.invokeMethod(new PrivateMethod(), "name", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY);
    }

    /**
     * Test invoke method parent empty params.
     */
    //*******************parent********************************************************
    @Test
    public void testInvokeMethodParentEmptyParams(){
        assertEquals("parent method", MethodUtil.invokeMethod(new SimpleChild(), "getMessage", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY));
    }

    /**
     * Test invoke static method parent empty params.
     */
    @Test
    public void testInvokeStaticMethodParentEmptyParams(){
        assertEquals(
                        "parent static method",
                        MethodUtil.invokeMethod(new SimpleChild(), "getStaticMessage", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY));
    }

    /**
     * Test invoke method method not exist.
     */
    //**********************MethodNotExist*****************************************************
    @Test(expected = ReflectException.class)
    public void testInvokeMethodMethodNotExist(){
        MethodUtil.invokeMethod(new User(), "getId1", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY);
    }

    /**
     * Test invoke method null obj.
     */
    @Test(expected = NullPointerException.class)
    public void testInvokeMethodNullObj(){
        MethodUtil.invokeMethod(null, "getId", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY);
    }

    /**
     * Test invoke method null method name.
     */
    @Test(expected = NullPointerException.class)
    public void testInvokeMethodNullMethodName(){
        MethodUtil.invokeMethod(new User(), null, EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY);
    }

    /**
     * Test invoke method empty method name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvokeMethodEmptyMethodName(){
        MethodUtil.invokeMethod(new User(), "", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY);
    }

    /**
     * Test invoke method blank method name.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvokeMethodBlankMethodName(){
        MethodUtil.invokeMethod(new User(), " ", EMPTY_OBJECT_ARRAY, EMPTY_CLASS_ARRAY);
    }
}
