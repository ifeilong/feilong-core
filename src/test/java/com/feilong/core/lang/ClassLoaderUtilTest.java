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

import org.apache.commons.lang3.ClassUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ClassLoaderUtilTest.
 * 
 * @author feilong
 * @version 1.0 2011-4-27 上午12:42:55
 */
public class ClassLoaderUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtilTest.class);

    /**
     * {@link com.feilong.core.lang.ClassLoaderUtil#getResource(java.lang.String)} 的测试方法。
     */
    @Test
    public void testGetResource(){
        LOGGER.info(ClassLoaderUtil.getResource("") + "");
        LOGGER.info("" + ClassLoaderUtil.getResource("com"));
        ClassLoaderUtil.getResource("jstl-1.2", this.getClass());
    }

    /**
     * Test get class path.
     */
    @Test
    public void testGetClassPath(){
        LOGGER.info("" + ClassLoaderUtil.getClassPath());
    }

    /**
     * Test get class.
     *
     * @throws ClassNotFoundException
     *             the class not found exception
     */
    @Test
    public void testGetClass() throws ClassNotFoundException{
        LOGGER.info("" + ClassLoaderUtil.getClass("com.feilong.core.FeiLongVersion"));
        LOGGER.info("" + ClassUtils.getClass("com.feilong.core.FeiLongVersion"));
    }

    /**
     * Prints the.
     */
    @Test
    public void testPrint(){
        // /E:/Workspaces/eclipse3.5/feilong-platform/feilong-common/target/classes/
        String a = this.getClass().getClassLoader().getResource(".").getPath();
        // /E:/Workspaces/eclipse3.5/feilong-platform/feilong-common/target/classes/temple/io/
        String b = this.getClass().getResource("").getPath();
        // /E:/Workspaces/eclipse3.5/feilong-platform/feilong-common/target/classes/temple/io/%20
        String c = this.getClass().getResource(" ").getPath();
        // 获得编译类根目录
        // /E:/Workspaces/eclipse3.5/feilong-platform/feilong-common/target/classes/
        String d = this.getClass().getResource("/").getPath();
        // 获得应用程序完整路径
        // E:\Workspaces\eclipse3.5\feilong-platform\feilong-common
        LOGGER.info(a);
        LOGGER.info(b);
        LOGGER.info(c);
        LOGGER.info(d);
    }
}
