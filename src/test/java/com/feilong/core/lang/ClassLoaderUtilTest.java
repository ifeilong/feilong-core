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

import static org.junit.Assert.assertEquals;

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
     * {@link com.feilong.core.lang.ClassLoaderUtil#getResource(java.lang.String)} 的测试方法.
     */
    @Test
    public void testGetResource(){
        LOGGER.debug(ClassLoaderUtil.getResource("") + "");
        LOGGER.debug("" + ClassLoaderUtil.getResource("com"));

    }

    /**
     * Test get resource1.
     */
    @Test
    public void testGetResource1(){
        assertEquals(null, ClassLoaderUtil.getResourceInAllClassLoader("jstl-1.2", this.getClass()));
    }

    /**
     * Test get resource2.
     */
    @Test
    public void testGetResource2(){
        assertEquals(null, ClassLoaderUtil.getResourceInAllClassLoader("slf4j-log4j12-1.7.21", this.getClass()));
    }

    /**
     * Test get resource23.
     */
    @Test
    public void testGetResource23(){
        assertEquals(null, ClassLoaderUtil.getResourceInAllClassLoader("slf4j-log4j12-1.7.21.jar", this.getClass()));
    }

    /**
     * Test get resource232.
     */
    @Test
    public void testGetResource232(){
        assertEquals(
                        "file:/E:/Workspaces/feilong/feilong-core/target/classes/com/feilong/core/lang/ArrayUtil.class",
                        ClassLoaderUtil.getResourceInAllClassLoader("com/feilong/core/lang/ArrayUtil.class", this.getClass()).toString());
    }

    /**
     * Test get class path.
     */
    @Test
    public void testGetClassPath(){
        LOGGER.debug("" + ClassLoaderUtil.getRootClassPath());
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
        LOGGER.debug(a);
        LOGGER.debug(b);
        LOGGER.debug(c);
        LOGGER.debug(d);
    }

    /**
     * Load resources.
     * 
     * <p>
     * This method will try to load the resource using the following methods (in order):
     * </p>
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From {@link Class#getClassLoader() callingClass.getClassLoader() }
     * </ul>
     * 
     * @param resourceName
     *            the resource name
     * @param callingClass
     *            the calling class
     * @return the resources
     * @see java.lang.ClassLoader#getResources(String)
     */
    //    public static Enumeration<URL> getResources(String resourceName,Class<?> callingClass){
    //        try{
    //            List<ClassLoader> classLoaderList = getAllClassLoaderList(callingClass);
    //            for (ClassLoader classLoader : classLoaderList){
    //                Enumeration<URL> resourcesEnumeration = classLoader.getResources(resourceName);
    //                if (null == resourcesEnumeration){
    //                    LOGGER.warn(getLogInfo(resourceName, classLoader, false));
    //                }else{
    //                    LOGGER.debug(getLogInfo(resourceName, classLoader, true));
    //                    return resourcesEnumeration;
    //                }
    //            }
    //            LOGGER.warn("resourceName:[{}] in all ClassLoader not found", resourceName);
    //            return null;
    //        }catch (IOException e){
    //            throw new UncheckedIOException(e);
    //        }
    //    }
}
