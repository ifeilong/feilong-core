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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.Validate;

import com.feilong.core.UncheckedIOException;
import com.feilong.core.lang.ClassLoaderUtil;

/**
 * 操作properties配置文件.
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
 * 
 * <h3>关于properties文件 value 换行:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
{@code
我们经常在properties文件中设置属性的时候,如果某一个属性的值太长,那么查看就不太方便,但是又不能直接的换行,否则读取属性的值的时候其换行部分就被忽略了.
其实我们可以通过增加一个\符号来达到换行的效果.如下:

name=Hello world \
My Name is ferreousbox
    
那么我们在读取name属性的时其值就变成了:Hello world My Name is ferreousbox.也就解决了在properties文件中换行书写的问题,
只需要在每一行的最后增加一个\符号,注意其下一行必须是接着的一行,即中间不能空行,否则也会被忽略的:-).

}
 * </pre>
 * 
 * </blockquote>
 *
 * @author feilong
 * @see "org.apache.velocity.texen.util.PropertiesUtil"
 * @see "org.apache.cxf.common.util.PropertiesLoaderUtils"
 * @see "org.springframework.core.io.support.PropertiesLoaderUtils"
 * @see "org.springframework.core.io.support.PropertiesLoaderSupport"
 * @see "org.apache.commons.configuration.PropertiesConfiguration"
 * @version 1.4.0 2015年8月3日 上午3:18:50
 * @since 1.4.0
 */
public final class PropertiesUtil{

    /** Don't let anyone instantiate this class. */
    private PropertiesUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 转换成map.
     * 
     * <p>
     * Create a new HashMap and pass an instance of Properties.<br>
     * Properties is an implementation of a Map which keys and values stored as in a string.
     * </p>
     * 
     * @param properties
     *            the properties
     * @return the map
     * @see org.apache.commons.collections4.MapUtils#toProperties(Map)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, String> toMap(Properties properties){
        return new HashMap<String, String>((Map) properties);
    }

    // [start] getPropertiesValue

    /**
     * 获取Properties配置文件键值.
     * 
     * @param clz
     *            当前类
     * @param propertiesPath
     *            Properties文件路径 如"/WEB-INF/classes/feilong.user.properties"
     * @param key
     *            键
     * @return 获取Properties配置文件键值
     * @see #getProperties(Class, String)
     */
    public static String getPropertiesValue(Class<?> clz,String propertiesPath,String key){
        Properties properties = getProperties(clz, propertiesPath);
        return properties.getProperty(key);
    }

    /**
     * 通过ClassLoader获得properties值.
     * 
     * @param clz
     *            当前Class
     * @param propertiesPath
     *            Properties文件路径 如"/WEB-INF/classes/feilong.user.properties"
     * @param key
     *            用指定的键在此属性列表中搜索属性.如果在此属性列表中未找到该键,则接着递归检查默认属性列表及其默认值.如果未找到属性,则此方法返回 null.
     * @return 通过ClassLoader获得properties值
     * @see #getPropertiesWithClassLoader(Class, String)
     * @see java.util.Properties#getProperty(String)
     */
    public static String getPropertiesValueWithClassLoader(Class<?> clz,String propertiesPath,String key){
        Properties properties = getPropertiesWithClassLoader(clz, propertiesPath);
        return properties.getProperty(key);
    }

    // [end]

    // [start] getProperties

    /**
     * klass调用ClassLoader,通过ClassLoader 获取Properties.
     * 
     * @param klass
     *            通过 klass 获得 ClassLoader,然后获得 getResourceAsStream
     * @param propertiesPath
     *            假设 class是 {@link PropertiesUtil},而配置文件在 src/main/resources下面,比如 messages/feilong-core-message_en_US.properties<br>
     *            <ul>
     *            <li>如果使用 {@link #getProperties(Class, String)} 需要这么写
     *            {@code getProperties(PropertiesUtil.class,"/messages/feilong-core-message_en_US.properties")} <br>
     *            注意此处的propertiesPath 要写成 <b>"/messages/feilong-core-message_en_US.properties"</b>, 路径可以写成相对路径或者绝对路径</li>
     *            <li>如果使用 {@link #getPropertiesWithClassLoader(Class, String)} 需要这么写
     *            {@code getPropertiesWithClassLoader(PropertiesUtil.class,"messages/feilong-core-message_en_US.properties")}<br>
     *            注意此处的propertiesPath 要写成 <b>"messages/feilong-core-message_en_US.properties"</b>,ClassLoader JVM会使用BootstrapLoader去加载资源文件.
     *            <br>
     *            所以路径还是这种相对于工程的根目录即"messages/feilong-core-message_en_US.properties" 不需要"/")</li>
     *            </ul>
     * @return Properties
     * @see ClassLoaderUtil#getClassLoaderByClass(Class)
     * @see java.lang.ClassLoader#getResourceAsStream(String)
     * @see #getProperties(InputStream)
     * @see #getProperties(Class, String)
     */
    public static Properties getPropertiesWithClassLoader(Class<?> klass,String propertiesPath){
        ClassLoader classLoader = ClassLoaderUtil.getClassLoaderByClass(klass);
        InputStream inputStream = classLoader.getResourceAsStream(propertiesPath);
        return getProperties(inputStream);
    }

    /**
     * 通过{@link Class#getResourceAsStream(String)} 方法获得 InputStream,再调用 {@link #getProperties(InputStream)}.
     * 
     * @param klass
     *            类,会通过 klass 调用
     * @param propertiesPath
     *            假设 class是 {@link PropertiesUtil},而配置文件在 src/main/resources下面,比如 messages/feilong-core-message_en_US.properties<br>
     *            <ul>
     *            <li>如果使用 {@link #getProperties(Class, String)} 需要这么写
     *            {@code getProperties(PropertiesUtil.class,"/messages/feilong-core-message_en_US.properties")} <br>
     *            注意此处的propertiesPath 要写成 <b>"/messages/feilong-core-message_en_US.properties"</b>, 路径可以写成相对路径或者绝对路径</li>
     *            <li>如果使用 {@link #getPropertiesWithClassLoader(Class, String)} 需要这么写
     *            {@code getPropertiesWithClassLoader(PropertiesUtil.class,"messages/feilong-core-message_en_US.properties")}<br>
     *            注意此处的propertiesPath 要写成 <b>"messages/feilong-core-message_en_US.properties"</b>,ClassLoader JVM会使用BootstrapLoader去加载资源文件.
     *            <br>
     *            所以路径还是这种相对于工程的根目录即"messages/feilong-core-message_en_US.properties" 不需要"/")</li>
     *            </ul>
     * @return 获取Properties
     * @see java.lang.Class#getResourceAsStream(String)
     * @see #getProperties(InputStream)
     * @see #getPropertiesWithClassLoader(Class, String)
     */
    public static Properties getProperties(Class<?> klass,String propertiesPath){
        // klass.getResourceAsStream方法内部会调用classLoader.getResourceAsStream
        // 之所以这样做无疑还是方便客户端的调用,省的每次获取ClassLoader才能加载资源文件的麻烦.
        InputStream inputStream = klass.getResourceAsStream(propertiesPath);
        return getProperties(inputStream);
    }

    /**
     * 获取Properties.
     *
     * @param inputStream
     *            inputStream
     * @return
     *         <ul>
     *         <li>正常情况,返回 {@link java.util.Properties#load(InputStream)}</li>
     *         </ul>
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