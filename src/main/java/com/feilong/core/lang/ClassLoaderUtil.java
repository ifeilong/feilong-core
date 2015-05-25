/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.UncheckedIOException;
import com.feilong.core.tools.json.JsonUtil;

/**
 * 根据类的class文件位置来定位的方法.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-4-27 上午12:40:08
 * @since 1.0.0
 */
public final class ClassLoaderUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ClassLoaderUtil.class);

    /** Don't let anyone instantiate this class. */
    private ClassLoaderUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 查找具有给定名称的资源.<br>
     * "",表示classes 的根目录<br>
     * e.q:<br>
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4">
     * <tr style="background-color:#ccccff">
     * <th align="left"></th>
     * <th align="left">(maven)测试</th>
     * <th align="left">在web环境中,(即使打成jar的情形)</th>
     * </tr>
     * <tr valign="top">
     * <td>{@code getResource("")}</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-common/target/test-classes/</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-spring-test-2.5/src/main/webapp/WEB-INF/classes/</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td>{@code getResource("com")}</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-common/target/test-classes/com</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-spring-test-2.5/src/main/webapp/WEB-INF/classes/com/</td>
     * </tr>
     * </table>
     * </blockquote>
     * 
     * @param name
     *            资源名称
     * @return 查找具有给定名称的资源
     */
    public static URL getResource(String name){
        ClassLoader classLoader = ClassLoaderUtil.class.getClassLoader();
        URL url = classLoader.getResource(name);
        return url;
    }

    /**
     * 获得 项目的 classpath,及classes编译的根目录.
     * 
     * @return 获得 项目的 classpath
     * @see #getResource(String)
     */
    public static URL getClassPath(){
        URL url = getResource("");
        return url;
    }

    // *****************************************************
    /**
     * This is a convenience method to load a resource as a stream. <br>
     * The algorithm used to find the resource is given in getResource()
     * 
     * @param resourceName
     *            The name of the resource to load
     * @param callingClass
     *            The Class object of the calling object
     * @return the resource as stream
     * @see #getResource(String, Class)
     */
    public static InputStream getResourceAsStream(String resourceName,Class<?> callingClass){
        URL url = getResource(resourceName, callingClass);
        try{
            return (url != null) ? url.openStream() : null;
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Load a given resource. <br>
     * This method will try to load the resource using the following methods (in order):
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From the {@link Class#getClassLoader() callingClass.getClassLoader() }
     * </ul>
     * 
     * @param resourceName
     *            The name of the resource to load
     * @param callingClass
     *            The Class object of the calling object
     * @return the resource
     */
    public static URL getResource(String resourceName,Class<?> callingClass){
        ClassLoader classLoader = getClassLoaderByCurrentThread();
        URL url = classLoader.getResource(resourceName);
        if (url == null){
            log.warn("In ClassLoader:[{}],not found the resourceName:[{}]", classLoader.getResource(""), resourceName);

            classLoader = getClassLoaderByClass(ClassLoaderUtil.class);
            url = classLoader.getResource(resourceName);
            if (url == null){

                log.warn("In ClassLoader:[{}],not found the resourceName:[{}]", classLoader.getResource(""), resourceName);
                classLoader = getClassLoaderByClass(callingClass);
                url = classLoader.getResource(resourceName);
            }
        }
        if (url == null){
            log.warn("resourceName:[{}] in all ClassLoader is null", resourceName);
        }else{
            log.debug("found the resourceName:[{}],In ClassLoader :[{}] ", resourceName, classLoader.getResource(""));
        }
        return url;
    }

    /**
     * Load resources.
     * 
     * @param resourceName
     *            the resource name
     * @param callingClass
     *            the calling class
     * @return the resources
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @see java.lang.ClassLoader#getResources(String)
     */
    public static Enumeration<URL> getResources(String resourceName,Class<?> callingClass) throws IOException{
        ClassLoader classLoader = getClassLoaderByCurrentThread();
        Enumeration<URL> urls = classLoader.getResources(resourceName);
        if (urls == null){
            classLoader = getClassLoaderByClass(ClassLoaderUtil.class);
            urls = classLoader.getResources(resourceName);
            if (urls == null){
                classLoader = getClassLoaderByClass(callingClass);
                urls = classLoader.getResources(resourceName);
            }
        }
        if (urls == null){
            log.warn("resourceName:[{}] in all ClassLoader is null", resourceName);
        }else{
            log.debug("In ClassLoader :[{}] found the resourceName:[{}]", classLoader.getResource(""), resourceName);
        }
        return urls;
    }

    /**
     * Load a class with a given name. <br>
     * It will try to load the class in the following order:
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>Using the basic {@link Class#forName(java.lang.String) }
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From the {@link Class#getClassLoader() callingClass.getClassLoader() }
     * </ul>
     * 
     * @param className
     *            The name of the class to load
     * @param callingClass
     *            The Class object of the calling object
     * @return the class
     * @throws ClassNotFoundException
     *             If the class cannot be found anywhere.
     * @see java.lang.ClassLoader#loadClass(String)
     */
    public static Class<?> loadClass(String className,Class<?> callingClass) throws ClassNotFoundException{
        ClassLoader classLoader = null;
        try{
            classLoader = getClassLoaderByCurrentThread();
            return classLoader.loadClass(className);
        }catch (ClassNotFoundException e){
            try{
                return Class.forName(className);
            }catch (ClassNotFoundException ex){
                try{
                    classLoader = getClassLoaderByClass(ClassLoaderUtil.class);
                    return classLoader.loadClass(className);
                }catch (ClassNotFoundException exc){
                    classLoader = getClassLoaderByClass(callingClass);
                    return classLoader.loadClass(className);
                }
            }
        }
    }

    /**
     * 通过Thread.currentThread().getContextClassLoader() 获得ClassLoader
     * 
     * @return the class loader by current thread
     */
    public static ClassLoader getClassLoaderByCurrentThread(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (log.isDebugEnabled()){
            Map<String, Object> classLoaderInfoMap = getClassLoaderInfoMapForLog(classLoader);
            log.debug("Thread.currentThread().getContextClassLoader:{}", JsonUtil.format(classLoaderInfoMap));
        }
        return classLoader;
    }

    /**
     * 通过类来获得 classLoader.
     * 
     * @param callingClass
     *            the calling class
     * @return the class loader by class
     * @see java.lang.Class#getClassLoader()
     */
    public static ClassLoader getClassLoaderByClass(Class<?> callingClass){
        ClassLoader classLoader = callingClass.getClassLoader();
        if (log.isDebugEnabled()){
            Map<String, Object> classLoaderInfoMap = getClassLoaderInfoMapForLog(classLoader);
            log.debug("callingClass.getClassLoader():{}", JsonUtil.format(classLoaderInfoMap));
        }
        return classLoader;
    }

    /**
     * 获得 class loader info map for log.
     *
     * @param classLoader
     *            the class loader
     * @return the class loader info map for log
     * @since 1.1.1
     */
    private static Map<String, Object> getClassLoaderInfoMapForLog(ClassLoader classLoader){
        Map<String, Object> classLoaderInfoMap = new LinkedHashMap<String, Object>();
        classLoaderInfoMap.put("classLoader", "" + classLoader);
        classLoaderInfoMap.put("classLoader CanonicalName", classLoader.getClass().getCanonicalName());
        classLoaderInfoMap.put("classLoader root classpath", "" + classLoader.getResource(""));
        return classLoaderInfoMap;
    }
}