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
package com.feilong.core.bean.beanutiltest;

import static com.feilong.core.bean.ConvertUtil.toArray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.feilong.core.bean.BeanUtil;
import com.feilong.core.lang.reflect.MethodUtil;
import com.feilong.store.member.Person;

/**
 * The Class BeanUtilCopyPropertiesTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetPropertyExceptionTest{

    /**
     * Test bean util null to bean.
     * 
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @Test(expected = NullPointerException.class)
    @SuppressWarnings("static-method")
    public void testBeanUtilNullToBean() throws NoSuchMethodException,SecurityException,IllegalAccessException,IllegalArgumentException,
                    InvocationTargetException{

        // MethodUtil.invokeStaticMethod(BeanUtil.class, "getProperty", null, "name");
        MethodUtil.invokeStaticMethod(BeanUtil.class, "getProperty", toArray(null, "name"), toArray(Object.class, String.class));

    }

    /**
     * Test bean util null from bean.
     * 
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    //@Test(expected = NullPointerException.class)
    @Test
    @SuppressWarnings("static-method")
    public void testBeanUtilNullFromBean() throws NoSuchMethodException,SecurityException,IllegalAccessException,IllegalArgumentException,
                    InvocationTargetException{
        Method declaredMethod = BeanUtil.class.getDeclaredMethod("getProperty", Object.class, String.class);
        declaredMethod.setAccessible(true);

        declaredMethod.invoke(BeanUtil.class, new Person(), "name11");
    }
}
