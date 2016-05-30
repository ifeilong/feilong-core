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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.UncheckedIOException;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * {@link java.lang.ClassLoader}工具类.
 * 
 * <h3>关于查找资源:</h3>
 * 
 * <blockquote>
 * 
 * <ul>
 * <li>{@link #getResource(String)}</li>
 * <li>{@link #getClassPath()}</li>
 * <li>{@link #getClassPath(ClassLoader)}</li>
 * <li>{@link #getResource(String, Class)}</li>
 * <li>{@link #getResource(ClassLoader, String)}</li>
 * <li>{@link #getResources(String, Class)}</li>
 * </ul>
 * 
 * <p>
 * "",表示classes 的根目录
 * </p>
 * e.q:<br>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left"></th>
 * <th align="left">(maven)测试</th>
 * <th align="left">在web环境中(即使打成jar的情形)</th>
 * </tr>
 * <tr valign="top">
 * <td><code>getResource("")</code></td>
 * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-common/target/test-classes/</td>
 * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-spring-test-2.5/src/main/webapp/WEB-INF/classes/</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td><code>getResource("com")</code></td>
 * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-common/target/test-classes/com</td>
 * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-spring-test-2.5/src/main/webapp/WEB-INF/classes/com/</td>
 * </tr>
 * </table>
 * </blockquote>
 * </blockquote>
 * 
 * <h3>关于 {@link java.lang.Class#getResourceAsStream(String) Class#getResourceAsStream(String)} VS
 * {@link java.lang.ClassLoader#getResourceAsStream(String) ClassLoader#getResourceAsStream(String)}</h3>
 * 
 * <blockquote>
 * <p>
 * 基本上,两个都可以用于从 classpath 里面进行资源读取,classpath包含classpath中的路径和classpath中的jar
 * </p>
 * <p>
 * 假设配置文件在 src/main/resources下面,比如 messages/feilong-core-message_en_US.properties,
 * </p>
 * <ul>
 * <li>{@link java.lang.Class#getResourceAsStream(String) Class#getResourceAsStream(String)} 需要这么写
 * <b>"/messages/feilong-core-message_en_US.properties"</b>, 路径可以写成相对路径或者绝对路径;<br>
 * 以 / 开头,则这样的路径是指定绝对路径, 如果不以 / 开头, 则路径是相对与这个class所在的包的</li>
 * <li>{@link java.lang.ClassLoader#getResourceAsStream(String) ClassLoader#getResourceAsStream(String)} 需要这么写
 * <b>"messages/feilong-core-message_en_US.properties"</b>,ClassLoader JVM会使用BootstrapLoader去加载资源文件.<br>
 * 所以路径还是这种相对于工程的根目录即"messages/feilong-core-message_en_US.properties" <span style="color:red">不需要"/"</span></li>
 * </ul>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.lang.ClassLoader
 * @see java.net.URLClassLoader
 * @since 1.0.0
 */
public final class ClassLoaderUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtil.class);

    /** Don't let anyone instantiate this class. */
    private ClassLoaderUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 查找具有给定名称的资源.
     * 
     * <p>
     * "",表示classes 的根目录
     * </p>
     * e.q:<br>
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left"></th>
     * <th align="left">(maven)测试</th>
     * <th align="left">在web环境中,(即使打成jar的情形)</th>
     * </tr>
     * <tr valign="top">
     * <td><code>getResource("")</code></td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-common/target/test-classes/</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-spring-test-2.5/src/main/webapp/WEB-INF/classes/</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td><code>getResource("com")</code></td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-common/target/test-classes/com</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-spring-test-2.5/src/main/webapp/WEB-INF/classes/com/</td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * @param resourceName
     *            the resource name
     * @return 查找具有给定名称的资源
     */
    public static URL getResource(String resourceName){
        ClassLoader classLoader = getClassLoaderByClass(ClassLoaderUtil.class);
        return getResource(classLoader, resourceName);
    }

    /**
     * 获得 项目的 classpath,及classes编译的根目录.
     * 
     * @return 获得 项目的 classpath
     * @see #getResource(String)
     */
    public static URL getClassPath(){
        ClassLoader classLoader = getClassLoaderByClass(ClassLoaderUtil.class);
        return getClassPath(classLoader);
    }

    /**
     * 获得 class path.
     *
     * @param classLoader
     *            the class loader
     * @return the class path
     * @see #getResource(ClassLoader, String)
     */
    public static URL getClassPath(ClassLoader classLoader){
        return getResource(classLoader, "");
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
     * @see "org.apache.velocity.util.ClassUtils#getResourceAsStream(Class, String)"
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
     * Load a given resource.
     * <p>
     * This method will try to load the resource using the following methods (in order):
     * </p>
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
            LOGGER.warn(
                            "In ClassLoader:[{}],not found resourceName:[{}]",
                            JsonUtil.format(getClassLoaderInfoMapForLog(classLoader)),
                            resourceName);

            classLoader = getClassLoaderByClass(ClassLoaderUtil.class);
            url = getResource(classLoader, resourceName);

            if (url == null){
                LOGGER.warn(
                                "In ClassLoader:[{}],not found resourceName:[{}]",
                                JsonUtil.format(getClassLoaderInfoMapForLog(classLoader)),
                                resourceName);
                classLoader = getClassLoaderByClass(callingClass);
                url = getResource(classLoader, resourceName);
            }
        }

        if (url == null){
            LOGGER.warn("resourceName:[{}] in all ClassLoader not found", resourceName);
            return null;
        }

        LOGGER.debug(
                        "found resourceName:[{}],In ClassLoader :[{}] ",
                        resourceName,
                        JsonUtil.format(getClassLoaderInfoMapForLog(classLoader)));
        return url;
    }

    /**
     * 查找具有给定名称的资源.
     * <p>
     * "",表示classes 的根目录
     * </p>
     * e.q:<br>
     * 
     * <blockquote>
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left"></th>
     * <th align="left">(maven)测试</th>
     * <th align="left">在web环境中,(即使打成jar的情形)</th>
     * </tr>
     * <tr valign="top">
     * <td><code>getResource("")</code></td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-common/target/test-classes/</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-spring-test-2.5/src/main/webapp/WEB-INF/classes/</td>
     * </tr>
     * <tr valign="top" style="background-color:#eeeeff">
     * <td><code>getResource("com")</code></td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-common/target/test-classes/com</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-spring-test-2.5/src/main/webapp/WEB-INF/classes/com/</td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * @param classLoader
     *            the class loader
     * @param resourceName
     *            the resource name
     * @return the resource
     * @since 1.2.1
     */
    public static URL getResource(ClassLoader classLoader,String resourceName){
        return classLoader.getResource(resourceName);
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
            LOGGER.warn("resourceName:[{}] in all ClassLoader not found!", resourceName);
            return null;
        }

        LOGGER.debug(
                        "In ClassLoader :[{}] found the resourceName:[{}]",
                        JsonUtil.format(getClassLoaderInfoMapForLog(classLoader)),
                        resourceName);
        return urls;
    }

    /**
     * Load a class with a given name.
     * 
     * <p>
     * It will try to load the class in the following order:
     * </p>
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * </ul>
     * 
     * <p>
     * Returns the class represented by {@code className} using the {@code classLoader}. <br>
     * This implementation supports the syntaxes " {@code java.util.Map.Entry[]}", "{@code java.util.Map$Entry[]}", "
     * {@code [Ljava.util.Map.Entry;}", and "{@code [Ljava.util.Map$Entry;} ".
     * </p>
     * 
     * @param className
     *            The name of the class to load
     * @return the class
     * @throws ClassNotFoundException
     *             If the class cannot be found anywhere.
     * @see java.lang.ClassLoader#loadClass(String)
     * @see java.lang.Class#forName(String)
     * @see java.lang.Class#forName(String, boolean, ClassLoader)
     * @see org.apache.commons.lang3.ClassUtils#getClass(String)
     * @see org.apache.commons.lang3.ClassUtils#getClass(ClassLoader, String, boolean)
     * @see "org.springframework.util.ClassUtils#forName(String, ClassLoader)"
     * @since 1.4.0
     */
    public static Class<?> getClass(String className) throws ClassNotFoundException{
        return org.apache.commons.lang3.ClassUtils.getClass(className);
    }

    /**
     * 通过 {@link Thread#getContextClassLoader()} 获得 {@link ClassLoader}.
     * 
     * @return the class loader by current thread
     */
    public static ClassLoader getClassLoaderByCurrentThread(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("Thread.currentThread().getContextClassLoader:{}", JsonUtil.format(getClassLoaderInfoMapForLog(classLoader)));
        }
        return classLoader;
    }

    /**
     * 通过类来获得 {@link ClassLoader}.
     * 
     * @param callingClass
     *            the calling class
     * @return the class loader by class
     * @see java.lang.Class#getClassLoader()
     */
    public static ClassLoader getClassLoaderByClass(Class<?> callingClass){
        ClassLoader classLoader = callingClass.getClassLoader();
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("{}.getClassLoader():{}", callingClass.getSimpleName(), JsonUtil.format(getClassLoaderInfoMapForLog(classLoader)));
        }
        return classLoader;
    }

    /**
     * 获得 {@link ClassLoader} info map for LOGGER.
     *
     * @param classLoader
     *            the class loader
     * @return the class loader info map for log
     * @since 1.1.1
     */
    private static Map<String, Object> getClassLoaderInfoMapForLog(ClassLoader classLoader){
        Map<String, Object> classLoaderInfoMap = new LinkedHashMap<String, Object>();
        classLoaderInfoMap.put("classLoader", "" + classLoader);
        classLoaderInfoMap.put("classLoader[CanonicalName]", classLoader.getClass().getCanonicalName());
        classLoaderInfoMap.put("classLoader[Root Classpath]", "" + getClassPath(classLoader));
        return classLoaderInfoMap;
    }
}