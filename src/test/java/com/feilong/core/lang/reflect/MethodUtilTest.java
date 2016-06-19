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
package com.feilong.core.lang.reflect;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.StringUtil;
import com.feilong.test.User;

/**
 * The Class MethodUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.7
 */
public class MethodUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodUtilTest.class);

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

    /**
     * Name.
     *
     * @param name
     *            the name
     * @return the string
     */
    public String name(Integer name){
        return "name Integer";
    }

    /**
     * Test invoke method1.
     */
    @Test
    public void testInvokeMethod1(){
        LOGGER.debug("" + MethodUtil.invokeMethod(ConstructorUtil.newInstance(MethodUtilTest.class), "name", 5));
        LOGGER.debug("" + MethodUtil.invokeMethod(new MethodUtilTest(), "name", Integer.parseInt("5")));
    }

    /**
     * Test.
     */
    @Test
    public void testInvokeMethod(){
        User user = new User();
        String methodName = "getId";
        Object[] params = new Object[0];
        LOGGER.debug("" + MethodUtil.invokeMethod(user, methodName, params));
        LOGGER.debug("" + MethodUtil.invokeMethod(user, methodName));
    }

    /**
     * Test invoke static method.
     */
    @Test(expected = ReflectException.class)
    public void testInvokeStaticMethod(){
        MethodUtil.invokeStaticMethod(User.class, "getId", new Object[0]);
    }

    /**
     * Test invoke static method.
     */
    @Test
    public void testInvokeStaticMethod1(){
        assertEquals("eilong", MethodUtil.invokeStaticMethod(StringUtil.class, "substring", "feilong", "ei"));
    }

    /**
     * Test invoke static method.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void testInvokeStaticMethod2() throws Exception{
        assertEquals("fjinxinlong", MethodUtils.invokeStaticMethod(StringUtil.class, "replace", "feilong", "ei", "jinxin"));
    }
}
