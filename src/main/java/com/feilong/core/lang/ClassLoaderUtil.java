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

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.URLUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * {@link java.lang.ClassLoader ClassLoader}工具类.
 * 
 * <h3>关于查找资源:</h3>
 * 
 * <blockquote>
 * 
 * <ul>
 * <li>{@link #getResource(String)}</li>
 * <li>{@link #getResourceInAllClassLoader(String, Class)}</li>
 * </ul>
 * 
 * </blockquote>
 * 
 * <h3>关于 {@link java.lang.Class#getResourceAsStream(String) Class#getResourceAsStream(String)} VS
 * {@link java.lang.ClassLoader#getResourceAsStream(String) ClassLoader#getResourceAsStream(String)}</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * 基本上,两个都可以用于从 classpath 里面进行资源读取,classpath包含classpath中的路径和classpath中的jar
 * </p>
 * 
 * <p>
 * 假设配置文件在 src/main/resources下面,比如 messages/feilong-core-message_en_US.properties,
 * </p>
 * 
 * <ol>
 * <li>{@link java.lang.Class#getResourceAsStream(String) Class#getResourceAsStream(String)} 需要这么写
 * <b>"/messages/feilong-core-message_en_US.properties"</b>,<br>
 * 路径可以写成相对路径或者绝对路径; 以 / 开头,则这样的路径是指定绝对路径, 如果不以 / 开头, 则路径是相对与这个class所在的包的</li>
 * <li>{@link java.lang.ClassLoader#getResourceAsStream(String) ClassLoader#getResourceAsStream(String)} 需要这么写
 * <b>"messages/feilong-core-message_en_US.properties"</b>,<br>
 * {@link ClassLoader} JVM会使用BootstrapLoader去加载资源文件.<br>
 * 所以路径还是这种相对于工程的根目录即"messages/feilong-core-message_en_US.properties" <span style="color:red">不需要"/"</span></li>
 * <li>如果你的项目使用了spring,建议条件允许的话,使用 <code>org.springframework.core.io.ClassPathResource</code>,这个类是基于{@link ClassLoader}的,同时会
 * <code>org.springframework.util.StringUtils#cleanPath(String)</code>,并且如果发现首字符是/斜杆, 会去掉,这样使用起来很方便</li>
 * </ol>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see java.lang.ClassLoader
 * @see java.net.URLClassLoader
 * @see "org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)"
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

    //---------------------------------------------------------------

    /**
     * 获得给定名称 <code>resourceName</code> 的资源.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 比如,在 <code>src/test/resources</code> 目录下面有 <code>messages/feilong-core-test.properties</code> 资源文件,编译之后,地址会出现在
     * <code>target/test-classes/messages/feilong-core-test.properties</code>
     * </p>
     * 
     * <pre class="code">
     * ClassLoaderUtil.getResource("/messages/feilong-core-test.properties") 
     * 或者 ClassLoaderUtil.getResource("messages/feilong-core-test.properties")
     * </pre>
     * 
     * 返回相同的结果
     * 
     * <pre class="code">
     * file:/E:/Workspaces/feilong/feilong-core/target/test-classes/messages/feilong-core-test.properties
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果 <code>resourceName</code> 是以斜杆 "/" 开头,那么会被截取,因为 {@link ClassLoader} 解析方式不需要开头的斜杆, 请参见
     * <code>org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)</code></li>
     * <li>"",表示classes 的根目录</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>不同环境结果不一样:</h3>
     * 
     * <blockquote>
     * 
     * <table border="1" cellspacing="0" cellpadding="4" summary="">
     * <tr style="background-color:#ccccff">
     * <th align="left">示例</th>
     * <th align="left">(maven)测试</th>
     * <th align="left">在web环境中,(即使打成jar的情形)</th>
     * </tr>
     * 
     * <tr valign="top">
     * <td><code>getResource("")</code></td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-core/target/test-classes/</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-web-test/src/main/webapp/WEB-INF/classes/</td>
     * </tr>
     * 
     * <tr valign="top" style="background-color:#eeeeff">
     * <td><code>getResource("com")</code></td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-core/target/test-classes/com</td>
     * <td>file:/E:/Workspaces/feilong/feilong-platform/feilong-web-test/src/main/webapp/WEB-INF/classes/com/</td>
     * </tr>
     * </table>
     * </blockquote>
     *
     * @param resourceName
     *            the resource name
     * @return 如果 <code>resourceName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果找不到该资源,或者调用者没有足够的权限获取该资源,则返回 null
     * @see org.apache.commons.lang3.ClassPathUtils#toFullyQualifiedPath(Package, String)
     * @see #getResource(ClassLoader, String)
     * @see #getClassLoaderByClass(Class)
     */
    public static URL getResource(String resourceName){
        return getResource(getClassLoaderByClass(ClassLoaderUtil.class), resourceName);
    }

    /**
     * 查找具有给定名称的资源,资源是可以通过类代码以与代码基无关的方式访问的一些数据(图像、声音、文本等).
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果 <code>resourceName</code> 是以 斜杆 "/" 开头,那么会被截取, 因为 ClassLoader解析方式不需要 开头的斜杆, 请参见
     * <code>org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)</code></li>
     * <li>"",表示classes 的根目录</li>
     * </ol>
     * </blockquote>
     *
     * @param classLoader
     *            the class loader
     * @param resourceName
     *            the resource name
     * @return 如果 <code>classLoader</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>resourceName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果找不到该资源,或者调用者没有足够的权限获取该资源,则返回 null
     * @since 1.2.1
     */
    private static URL getResource(ClassLoader classLoader,String resourceName){
        Validate.notNull(classLoader, "classLoader can't be null!");
        Validate.notNull(resourceName, "resourceName can't be null!");

        boolean startsWithSlash = resourceName.startsWith("/");
        String usePath = startsWithSlash ? StringUtil.substring(resourceName, 1) : resourceName;
        URL result = classLoader.getResource(usePath);

        LOGGER.trace("search resource:[\"{}\"] in [{}],result:[{}]", resourceName, classLoader, result);
        return result;
    }

    //---------------------------------------------------------------
    /**
     * 加载资源 <code>resourceName</code>为 InputStream.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果 <code>resourceName</code> 是以 斜杆 "/" 开头,那么会被截取, 因为 ClassLoader解析方式不需要 开头的斜杆, 请参见
     * <code>org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)</code></li>
     * <li>"",表示classes 的根目录</li>
     * </ol>
     * </blockquote>
     * 
     * @param resourceName
     *            The name of the resource to load
     * @param callingClass
     *            The Class object of the calling object
     * @return 如果 <code>resourceName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果查找不到资源,那么返回 null
     * @see #getResourceInAllClassLoader(String, Class)
     * @see "org.apache.velocity.util.ClassUtils#getResourceAsStream(Class, String)"
     */
    public static InputStream getResourceAsStream(String resourceName,Class<?> callingClass){
        URL url = getResourceInAllClassLoader(resourceName, callingClass);
        return URLUtil.openStream(url);
    }

    /**
     * Load a given resource.
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>如果 <code>resourceName</code> 是以 斜杆 "/" 开头,那么会被截取,因为 ClassLoader解析方式不需要开头的斜杆, 请参见
     * <code>org.springframework.core.io.ClassPathResource#ClassPathResource(String, ClassLoader)</code></li>
     * <li>"",表示classes 的根目录</li>
     * <li>
     * <p>
     * This method will try to load the resource using the following methods (in order):
     * </p>
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From {@link Class#getClassLoader() callingClass.getClassLoader() } (如果 callingClass 不是null)
     * </ul>
     * </li>
     * </ol>
     * </blockquote>
     * 
     * @param resourceName
     *            The name of the resource to load
     * @param callingClass
     *            The Class object of the calling object
     * @return 如果 <code>resourceName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>callingClass</code> 是null,将会忽略此参数<br>
     *         如果在所有的{@link ClassLoader}里面都查不到资源,那么返回null
     * @since 1.6.2
     */
    public static URL getResourceInAllClassLoader(String resourceName,Class<?> callingClass){
        Validate.notNull(resourceName, "resourceName can't be null!");

        List<ClassLoader> classLoaderList = getAllClassLoaderList(callingClass);
        for (ClassLoader classLoader : classLoaderList){
            URL url = getResource(classLoader, resourceName);
            if (null == url){
                LOGGER.trace(getLogInfo(resourceName, classLoader, false));
            }else{
                if (LOGGER.isTraceEnabled()){
                    LOGGER.trace(getLogInfo(resourceName, classLoader, true));
                }
                return url;
            }
        }
        LOGGER.debug("not found:[{}] in all ClassLoader,return null", resourceName);
        return null;
    }

    //---------------------------------------------------------------

    /**
     * 获得 all class loader list.
     * 
     * <p>
     * This method will try to get ClassLoader list using the following methods (in order):
     * </p>
     * <ul>
     * <li>From {@link Thread#getContextClassLoader() Thread.currentThread().getContextClassLoader()}
     * <li>From {@link Class#getClassLoader() ClassLoaderUtil.class.getClassLoader()}
     * <li>From {@link Class#getClassLoader() callingClass.getClassLoader() } (如果 <code>callingClass</code> 不是null)
     * </ul>
     *
     * @param callingClass
     *            the calling class
     * @return the all class loader
     * @since 1.6.2
     */
    private static List<ClassLoader> getAllClassLoaderList(Class<?> callingClass){
        List<ClassLoader> list = toList(getClassLoaderByCurrentThread(), getClassLoaderByClass(ClassLoaderUtil.class));
        if (null != callingClass){
            list.add(getClassLoaderByClass(callingClass));
        }
        return list;
    }

    /**
     * 通过 {@link Thread#getContextClassLoader()} 获得 {@link ClassLoader}.
     * 
     * @return the class loader by current thread
     */
    private static ClassLoader getClassLoaderByCurrentThread(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("[Thread.currentThread()].getContextClassLoader:{}", formatClassLoader(classLoader));
        }
        return classLoader;
    }

    /**
     * 通过类来获得 {@link ClassLoader}.
     * 
     * @param callingClass
     *            the calling class
     * @return 如果 <code>callingClass</code> 是null,抛出 {@link NullPointerException}<br>
     * @see java.lang.Class#getClassLoader()
     */
    private static ClassLoader getClassLoaderByClass(Class<?> callingClass){
        Validate.notNull(callingClass, "callingClass can't be null!");
        ClassLoader classLoader = callingClass.getClassLoader();
        if (LOGGER.isTraceEnabled()){
            LOGGER.trace("[{}].getClassLoader():{}", callingClass.getSimpleName(), formatClassLoader(classLoader));
        }
        return classLoader;
    }

    /**
     * 获得 log info.
     *
     * @param resourceName
     *            the resource name
     * @param classLoader
     *            the class loader
     * @param isFouned
     *            the is founed
     * @return the log info
     * @since 1.6.2
     */
    private static String getLogInfo(String resourceName,ClassLoader classLoader,boolean isFouned){
        String message = "{}found [{}],in ClassLoader:[{}]";
        return Slf4jUtil.format(message, isFouned ? "" : "not ", resourceName, formatClassLoader(classLoader));
    }

    /**
     * Format class loader.
     *
     * @param classLoader
     *            the class loader
     * @return the string
     * @since 1.6.2
     */
    private static String formatClassLoader(ClassLoader classLoader){
        Map<String, Object> map = newLinkedHashMap(2);
        map.put("classLoader[CanonicalName]", classLoader.getClass().getCanonicalName());
        map.put("classLoader[Root Classpath]", "" + getResource(classLoader, ""));
        return map.toString();
    }

}