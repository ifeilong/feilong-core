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
package com.feilong.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.Validate;

import com.feilong.core.UncheckedIOException;
import com.feilong.core.lang.ClassLoaderUtil;

/**
 * 操作{@link Properties}配置文件.
 * 
 * <h3>关于{@link "Properties#load0(LineReader)"}核心方法:</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>注释符为:'#'或者'!'.<br>
 * 空白字符为:' ', '\t', '\f'.<br>
 * key/value分隔符为:'='或者':'.<br>
 * 行分隔符为:'\r','\n','\r\n'. </li>
 * <li>自然行是使用行分隔符或者流的结尾来分割的行.<br>
 * 逻辑行可能分割到多个自然行中,使用反斜杠'\'来连接多个自然行;</li>
 * <li>注释行是使用注释符作为首个非空白字符的自然行;</li>
 * <li>空白字符的自然行会被认为是空行而被忽略;</li>
 * <li>key为<span style="color:red">从首个非空白字符开始</span>直到(但不包括)首个非转义的'=', ':'或者非行结束符的空白字符为止;</li>
 * <li>key后面的第一个非空白字符如果是"="或者":",那么这个字符后面所有空白字符都会被忽略掉;</li>
 * <li>可以使用转义序列表示key和value;</li>
 * <li>value的界定为:逻辑行中,非转义的key/value分隔符(此处不仅仅包括'=',':',还包括' ', '\t', '\f')后面的<span style="color:red">第一个非空白字符(非' ', '\t', '\f'字符)</span>
 * 开始到逻辑行结束的所有字符;</li>
 * <li>如果空白字符后续没有key/value分隔符("="或者":"),<br>
 * 那么该空白字符会被当作key/value分隔符,从分隔符后的第一个非空白字符起到逻辑行结束所有的字符都当作是value.<br>
 * 也就是说:"key1 value1",读取出来之后的key和value分别为"key1", "value1". 
 * </li>
 * <li>如果空白字符后续有key/value分隔符("="或者":"),<br>
 * 那么该空白字符会被忽略,key/value分隔符后的第一个非空白字符起到逻辑行结束所有的字符都当作是value.<br>
 * 也就是说:"key1 :value1",读取出来之后的key和value分别为"key1"和"value1",而不是"key1"和":value1". 
 * </li>
 * </ol>
 * </blockquote>
 * 
 * <h3>关于properties文件 value 换行:</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * 我们经常在properties文件中设置属性的时候,如果某一个属性的值太长,那么查看就不太方便,但是又不能直接的换行,否则读取属性的值的时候其换行部分就被忽略了.
 * 其实我们可以通过增加一个\符号来达到换行的效果.如下:
 * </p>
 * 
 * <pre class="code">
    name=Hello world \
    My Name is ferreousbox
 * </pre>
 * 
 * <p>
 * 那么我们在读取name属性的时其值就变成了:Hello world My Name is ferreousbox.也就解决了在properties文件中换行书写的问题,
 * 只需要在每一行的最后增加一个\符号,注意其下一行必须是接着的一行,即中间不能空行,否则也会被忽略的:-).
 * </p>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "org.apache.velocity.texen.util.PropertiesUtil"
 * @see "org.apache.cxf.common.util.PropertiesLoaderUtils"
 * @see "org.springframework.core.io.support.PropertiesLoaderUtils"
 * @see "org.springframework.core.io.support.PropertiesLoaderSupport"
 * @see "org.apache.commons.configuration.PropertiesConfiguration"
 * @since 1.4.0
 */
public final class PropertiesUtil{

    /** Don't let anyone instantiate this class. */
    private PropertiesUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // [start] getProperties

    /**
     * klass调用ClassLoader,通过ClassLoader 获取Properties.
     * 
     * <h3>关于路径 <code>propertiesPath</code>:</h3>
     * <blockquote>
     * 假设 class是 {@link PropertiesUtil},而配置文件在 src/main/resources下面,比如 messages/feilong-core-message_en_US.properties<br>
     * 
     * <p>
     * 需要写作
     * <code>getPropertiesWithClassLoader(PropertiesUtil.class,<b>"messages/feilong-core-message_en_US.properties"</b>)</code>
     * <br>
     * 注意此处要写成 <b>"messages/feilong-core-message_en_US.properties"</b> (不需要"/"),ClassLoader
     * JVM会使用BootstrapLoader去加载资源文件.所以路径还是这种相对于工程的根目录
     * </p>
     * 
     * <p>
     * 注意和 {@link #getProperties(Class, String)} 的区别
     * </p>
     * </blockquote>
     *
     * @param klass
     *            通过 klass 获得 ClassLoader,然后获得 getResourceAsStream
     * @param propertiesPath
     *            the properties path
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertiesPath</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertiesPath</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see ClassLoaderUtil#getClassLoaderByClass(Class)
     * @see java.lang.ClassLoader#getResourceAsStream(String)
     * @see #getProperties(InputStream)
     * @see #getProperties(Class, String)
     */
    public static Properties getPropertiesWithClassLoader(Class<?> klass,String propertiesPath){
        Validate.notBlank(propertiesPath, "propertiesPath can't be blank!");
        ClassLoader classLoader = null;

        //ClassLoaderUtil.getClassLoaderByClass(klass);
        return getProperties(classLoader.getResourceAsStream(propertiesPath));
    }

    /**
     * 通过{@link Class#getResourceAsStream(String)} 方法获得 InputStream,再调用 {@link #getProperties(InputStream)}.
     * 
     * <h3>关于路径 <code>propertiesPath</code>:</h3>
     * 
     * <blockquote>
     * 假设 class是 {@link PropertiesUtil},而配置文件在 src/main/resources下面,比如 messages/feilong-core-message_en_US.properties<br>
     * <p>
     * 需要写作 <code>getProperties(PropertiesUtil.class,<b>"/messages/feilong-core-message_en_US.properties"</b>)</code> <br>
     * 注意此处要写成 <b>"/messages/feilong-core-message_en_US.properties"</b>, 路径可以写成相对路径或者绝对路径
     * </p>
     * 
     * <p>
     * 注意和 {@link #getPropertiesWithClassLoader(Class, String)} 的区别
     * </p>
     * </blockquote>
     * 
     * @param klass
     *            类,会通过 klass 调用
     * @param propertiesPath
     *            the properties path
     * @return 如果 <code>klass</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertiesPath</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>propertiesPath</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see java.lang.Class#getResourceAsStream(String)
     * @see #getProperties(InputStream)
     * @see #getPropertiesWithClassLoader(Class, String)
     */
    public static Properties getProperties(Class<?> klass,String propertiesPath){
        Validate.notNull(klass, "klass can't be null!");
        Validate.notBlank(propertiesPath, "propertiesPath can't be blank!");
        // klass.getResourceAsStream方法 内部会调用classLoader.getResourceAsStream
        // 之所以这样做无疑还是方便客户端的调用,省的每次获取ClassLoader才能加载资源文件的麻烦.
        return getProperties(klass.getResourceAsStream(propertiesPath));
    }

    /**
     * 获取{@link Properties}.
     *
     * @param inputStream
     *            inputStream
     * @return 如果 <code>inputStream</code> 是null,抛出 {@link NullPointerException}<br>
     *         正常情况,返回 {@link java.util.Properties#load(InputStream)}
     * @see java.util.Properties#load(InputStream)
     * @see "org.springframework.core.io.support.PropertiesLoaderUtils#loadProperties(Resource)"
     */
    public static Properties getProperties(InputStream inputStream){
        Validate.notNull(inputStream, "inputStream can't be null!");
        Properties properties = new Properties();
        try{
            properties.load(inputStream);
            return properties;
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
    // [end]
}