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
package com.feilong.core.net.urlutiltest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.feilong.core.net.URLUtil;

/**
 * The Class URLUtilToURLTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToFileURLTest{

    // @Test(expected = InvocationTargetException.class)
    @Test
    @SuppressWarnings("static-method")
    public void testBeanUtilNullFromBean() throws NoSuchMethodException,SecurityException,IllegalAccessException,IllegalArgumentException,
                    InvocationTargetException{

        //com.feilong.core.net.URLUtil.toFileURL(String)
        Method declaredMethod = URLUtil.class.getDeclaredMethod("toFileURL", String.class);
        declaredMethod.setAccessible(true);

        String string = "https://www.zhihu.com/";
        string = "h1t://^&*name11";
        // string = "/a/b";
        Object invoke = declaredMethod.invoke(URLUtil.class, string);

        System.out.println(invoke);//TODO:remove
    }
}
