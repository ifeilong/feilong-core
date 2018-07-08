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
package com.feilong.core.lang.reflect.fieldutiltest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.feilong.core.lang.reflect.FieldUtil;
import com.feilong.store.member.Person;

/**
 * The Class FieldUtilGetAllFieldListTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetFieldValueTest{

    @Test(expected = InvocationTargetException.class)
    @SuppressWarnings("static-method")
    public void testBeanUtilNullFromBean() throws NoSuchMethodException,SecurityException,IllegalAccessException,IllegalArgumentException,
                    InvocationTargetException{
        Method declaredMethod = FieldUtil.class.getDeclaredMethod("getFieldValue", Object.class, String.class);
        declaredMethod.setAccessible(true);

        declaredMethod.invoke(FieldUtil.class, new Person(), "name11");
    }
}
