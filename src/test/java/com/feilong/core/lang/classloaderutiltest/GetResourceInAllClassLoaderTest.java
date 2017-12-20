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
package com.feilong.core.lang.classloaderutiltest;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.feilong.core.lang.ClassLoaderUtil;

/**
 * The Class ClassLoaderUtilGetResourceInAllClassLoaderTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class GetResourceInAllClassLoaderTest{

    /**
     * Test get resource not found.
     */
    @Test
    public void testGetResourceNotFound(){
        assertEquals(null, ClassLoaderUtil.getResourceInAllClassLoader("slf4j-log4j12-1.7.21", this.getClass()));
        assertEquals(null, ClassLoaderUtil.getResourceInAllClassLoader("slf4j-log4j12-1.7.21.jar", this.getClass()));
    }

    /**
     * Test get resource null resource name.
     */
    @Test(expected = NullPointerException.class)
    public void testGetResourceNullResourceName(){
        ClassLoaderUtil.getResourceInAllClassLoader(null, this.getClass());
    }

    //---------------------------------------------------------------

    /**
     * Test get resource null calling class.
     */
    @Test
    public void testGetResourceNullCallingClass(){

        //https://github.com/venusdrogon/feilong-core/issues/557
        //ClassLoaderUtilGetResourceInAllClassLoaderTest 单元测试 如果项目更改了目录那么执行不成功
        //String path = "file:/E:/Workspaces/feilong/feilong-core/target/classes/com/feilong/core/lang/ArrayUtil.class";

        String resourceName = "com/feilong/core/lang/ArrayUtil.class";

        String string = ClassLoaderUtil.getResourceInAllClassLoader(resourceName, null).toString();
        assertThat(string, containsString(resourceName));
    }

    /**
     * Test get resource 232.
     */
    @Test
    public void testGetResource232(){

        //https://github.com/venusdrogon/feilong-core/issues/557
        //ClassLoaderUtilGetResourceInAllClassLoaderTest 单元测试 如果项目更改了目录那么执行不成功
        //String path = "file:/E:/Workspaces/feilong/feilong-core/target/classes/com/feilong/core/lang/ArrayUtil.class";

        String resourceName = "com/feilong/core/lang/ArrayUtil.class";

        String string = ClassLoaderUtil.getResourceInAllClassLoader(resourceName, this.getClass()).toString();
        assertThat(string, containsString(resourceName));

        //assertEquals(path, ClassLoaderUtil.getResourceInAllClassLoader(resourceName, this.getClass()).toString());
    }

    /**
     * Test get resource slan.
     */
    @Test
    public void testGetResourceSlan(){

        //https://github.com/venusdrogon/feilong-core/issues/557
        //ClassLoaderUtilGetResourceInAllClassLoaderTest 单元测试 如果项目更改了目录那么执行不成功
        //String path = "file:/E:/Workspaces/feilong/feilong-core/target/classes/com/feilong/core/lang/ArrayUtil.class";

        String resourceName = "com/feilong/core/lang/ArrayUtil.class";

        String string = ClassLoaderUtil.getResourceInAllClassLoader("/" + resourceName, this.getClass()).toString();
        assertThat(string, containsString(resourceName));

        //assertEquals(path, ClassLoaderUtil.getResourceInAllClassLoader(resourceName, this.getClass()).toString());

    }
}
