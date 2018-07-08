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

import org.junit.Test;

import com.feilong.core.bean.BeanUtil;
import com.feilong.core.lang.reflect.MethodUtil;
import com.feilong.core.lang.reflect.ReflectException;
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
     *             the security exception
     * @throws IllegalArgumentException
     *             the illegal argument exception
     */
    @Test(expected = ReflectException.class)
    @SuppressWarnings("static-method")
    public void testBeanUtilNullToBean(){
        MethodUtil.invokeStaticMethod(BeanUtil.class, "getProperty", toArray(null, "name"), toArray(Object.class, String.class));

    }

    /**
     * Test bean util null from bean 2.
     */
    @Test
    @SuppressWarnings("static-method")
    public void testBeanUtilNullFromBean2(){
        MethodUtil.invokeStaticMethod(BeanUtil.class, "getProperty", new Person(), "name11");
    }

}
