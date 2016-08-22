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
package com.feilong.core.lang;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ClassLoaderUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ClassLoaderUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtilTest.class);

    /**
     * Test get resource.
     */
    @Test
    public void testGetResource(){
        LOGGER.debug(ClassLoaderUtil.getResource("") + "");
        LOGGER.debug("" + ClassLoaderUtil.getResource("com"));
    }

    /**
     * Test get resource 3.
     */
    @Test
    public void testGetResource3(){
        assertEquals(null, ClassLoaderUtil.getResource("feilong-core-test.properties"));
        assertThat(ClassLoaderUtil.getResource("/messages/feilong-core-test.properties"), is(notNullValue()));
    }

    /**
     * Test get resource2.
     */
    @Test
    public void testGetResource2(){
        assertEquals(null, ClassLoaderUtil.getResourceInAllClassLoader("slf4j-log4j12-1.7.21", this.getClass()));
        assertEquals(null, ClassLoaderUtil.getResourceInAllClassLoader("slf4j-log4j12-1.7.21.jar", this.getClass()));
    }

    /**
     * Test get resource232.
     */
    @Test
    public void testGetResource232(){
        String path = "file:/E:/Workspaces/feilong/feilong-core/target/classes/com/feilong/core/lang/ArrayUtil.class";
        String resourceName = "com/feilong/core/lang/ArrayUtil.class";
        assertEquals(path, ClassLoaderUtil.getResourceInAllClassLoader(resourceName, this.getClass()).toString());
        assertEquals(path, ClassLoaderUtil.getResourceInAllClassLoader("/" + resourceName, this.getClass()).toString());
    }

    /**
     * Prints the.
     */
    @Test
    public void testPrint(){
        Class klass = this.getClass();
        LOGGER.debug(klass.getClassLoader().getResource(".").getPath()); ///E:/Workspaces/feilong/feilong-core/target/test-classes/
        LOGGER.debug(klass.getResource("").getPath());///E:/Workspaces/feilong/feilong-core/target/test-classes/com/feilong/core/lang/
        LOGGER.debug(klass.getResource(" ").getPath());///E:/Workspaces/feilong/feilong-core/target/test-classes/com/feilong/core/lang/%20

        // 获得编译类根目录
        LOGGER.debug(klass.getResource("/").getPath());///E:/Workspaces/feilong/feilong-core/target/test-classes/
    }
}
