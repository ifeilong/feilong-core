/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core.lang.reflect;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.entity.BackWarnEntity;
import com.feilong.core.lang.reflect.ConstructorUtil;
import com.feilong.core.lang.reflect.MethodUtil;
import com.feilong.core.lang.reflect.ReflectException;
import com.feilong.core.util.StringUtil;

/**
 * The Class MethodUtilTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014年7月15日 下午2:47:29
 * @since 1.0.7
 */
public class MethodUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(MethodUtilTest.class);

    /**
     * Name.
     *
     * @param name
     *            the name
     * @return the string
     */
    private String name(int name){
        return "name int";
    }

    //    public String name(Integer name){
    //        return "name Integer";
    //    }

    /**
     * Test invoke method1.
     */
    @Test
    public final void testInvokeMethod1(){
        log.info("" + MethodUtil.invokeMethod(ConstructorUtil.newInstance(MethodUtilTest.class), "name", 5));
        log.info("" + MethodUtil.invokeMethod(new MethodUtilTest(), "name", Integer.parseInt("5")));
    }

    /**
     * Test.
     */
    @Test
    public final void testInvokeMethod(){
        BackWarnEntity backWarnEntity = new BackWarnEntity();
        String methodName = "getIsSuccess";
        Object[] params = new Object[0];
        log.info("" + MethodUtil.invokeMethod(backWarnEntity, methodName, params));
        log.info("" + MethodUtil.invokeMethod(backWarnEntity, methodName));
    }

    /**
     * Test invoke static method.
     */
    @Test(expected = ReflectException.class)
    public final void testInvokeStaticMethod(){
        MethodUtil.invokeStaticMethod(BackWarnEntity.class, "getIsSuccess", new Object[0]);
    }

    /**
     * Test invoke static method.
     */
    @Test()
    public final void testInvokeStaticMethod1(){
        assertEquals("eilong", MethodUtil.invokeStaticMethod(StringUtil.class, "substring", "feilong", "ei"));
    }

    /**
     * Test invoke static method.
     *
     * @throws NoSuchMethodException
     *             the no such method exception
     * @throws IllegalAccessException
     *             the illegal access exception
     * @throws InvocationTargetException
     *             the invocation target exception
     */
    @Test()
    public final void testInvokeStaticMethod2() throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        assertEquals("fjinxinlong", MethodUtils.invokeStaticMethod(StringUtil.class, "replace", "feilong", "ei", "jinxin"));
    }
}
