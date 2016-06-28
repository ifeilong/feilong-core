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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ClassLoaderUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ClassLoaderUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtilTemp.class);

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
