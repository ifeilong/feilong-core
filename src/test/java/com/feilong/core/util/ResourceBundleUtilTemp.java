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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.UncheckedIOException;
import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.StringUtil;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.Validator.isNullOrEmpty;

/**
 * The Class ResourceBundleUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ResourceBundleUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger  LOGGER         = LoggerFactory.getLogger(ResourceBundleUtilTemp.class);

    /** The base name. */
    private static final String  BASE_NAME      = "messages/feilong-core-test";

    /** The resource bundle. */
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(BASE_NAME);

    /**
     * 获取Properties配置文件键值,按照typeClass 返回对应的类型.
     * 
     * @param <T>
     *            the generic type
     * @param resourceBundle
     *            the resource bundle
     * @param key
     *            the key
     * @param typeClass
     *            指明返回类型,<br>
     *            如果是String.class,则返回的是String <br>
     *            如果是Integer.class,则返回的是Integer
     * @return 如果 <code>key</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>key</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>typeClass</code> 是null,抛出 {@link NullPointerException}<br>
     * @see #getValue(ResourceBundle, String)
     * @see com.feilong.core.bean.ConvertUtil#convert(Object, Class)
     */
    public static <T> T getValue(ResourceBundle resourceBundle,String key,Class<T> typeClass){
        String value = ResourceBundleUtil.getValue(resourceBundle, key);
        return ConvertUtil.convert(value, typeClass);
    }

    // **************************************************************************
    /**
     * Read prefix as map({@link TreeMap}).
     * 
     * <p>
     * 注意:JDK实现{@link java.util.PropertyResourceBundle},内部是使用 hashmap来存储数据的,<br>
     * 本方法出于log以及使用方便,返回的是<span style="color:red"> TreeMap</span>
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * 比如 messages/feilong-core-test.properties 里面有
     * 
     * <pre class="code">
     * FileType.image=Image
     * FileType.video=Video
     * FileType.audio=Audio
     * </pre>
     * 
     * 配置,此时调用
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = ResourceBundleUtil.readPrefixAsMap(BASE_NAME, "FileType", ".", Locale.CHINA);
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * 返回 :
     * 
     * <pre class="code">
     * {
     * "audio": "Audio",
     * "image": "Image",
     * "video": "Video"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @param prefix
     *            前缀
     * @param delimiters
     *            the delimiters
     * @param locale
     *            the locale for which a resource bundle is desired,如果是null,将使用 {@link Locale#getDefault()}
     * @return 如果 <code>baseName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>baseName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果资源文件 <code>baseName</code> 不存在,抛出 <code>MissingResourceException</code><br>
     *         如果 <code>baseName</code> 没有key value,则返回{@link java.util.Collections#emptyMap()}<br>
     *         否则解析所有的key和value转成HashMap
     * @see #readToMap(String, Locale)
     */
    public static Map<String, String> readPrefixAsMap(String baseName,String prefix,String delimiters,Locale locale){
        Map<String, String> propertyMap = ResourceBundleUtil.readToMap(baseName, locale);
        if (isNullOrEmpty(propertyMap)){
            return Collections.emptyMap();
        }

        Map<String, String> result = new TreeMap<String, String>();
        for (Map.Entry<String, String> entry : propertyMap.entrySet()){
            String key = entry.getKey();
            // 以 prefix 开头
            if (key.startsWith(prefix)){
                String[] values = StringUtil.tokenizeToStringArray(key, delimiters);
                if (values.length >= 2){
                    result.put(values[1], entry.getValue());
                }
            }
        }
        return result;
    }

    /**
     * Read prefix as map.
     */
    @Test
    public void readPrefixAsMap(){
        Map<String, String> map = readPrefixAsMap(BASE_NAME, "FileType", ".", Locale.CHINA);
        LOGGER.debug(JsonUtil.format(map));
    }

    /**
     * 读取指定 <code>key</code> 的值,使用分隔符 <code>delimiters</code> 以及指定的转换类型 <code>typeClass</code> 转成数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * 在 <code>messages/feilong-core-test.properties</code> 配置文件中, 有以下配置
     * 
     * <pre class="code">
     * config_test_array=5,8,7,6
     * </pre>
     * 
     * <p>
     * 解析:
     * </p>
     * 
     * <pre class="code">
     * ResourceBundleUtil.getArray("messages.feilong-core-test", "config_test_array", ",", String.class);    =   ["5","8","7","6"]
     * ResourceBundleUtil.getArray("messages.feilong-core-test", "config_test_array", ",", Integer.class);   =   [5,8,7,6]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @param key
     *            the key
     * @param delimiters
     *            分隔符,参见 {@link StringUtil#tokenizeToStringArray(String, String)} <code>delimiters</code> 参数
     * @param typeClass
     *            指明返回类型,<br>
     *            如果是String.class,则返回的是String []数组<br>
     *            如果是Integer.class,则返回的是Integer [] 数组
     * @return 如果 <code>baseName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>baseName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果资源文件 <code>baseName</code> 不存在,抛出 <code>MissingResourceException</code><br>
     * @see #getResourceBundle(String)
     * @see #getArray(ResourceBundle, String, String, Class)
     */
    public static <T> T[] getArray(String baseName,String key,String delimiters,Class<T> typeClass){
        ResourceBundle resourceBundle = ResourceBundleUtil.getResourceBundle(baseName);
        return getArray(resourceBundle, key, delimiters, typeClass);
    }

    /**
     * 读取指定 <code>key</code> 的值,使用分隔符 <code>delimiters</code> 转成字符串数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * 在 <code>messages/feilong-core-test.properties</code> 配置文件中, 有以下配置
     * 
     * <pre class="code">
     * config_test_array=5,8,7,6
     * </pre>
     * 
     * <pre class="code">
     * ResourceBundleUtil.getArray("messages.feilong-core-test", "config_test_array", ",");    =   ["5","8","7","6"]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @param key
     *            the key
     * @param delimiters
     *            分隔符,参见 {@link StringUtil#tokenizeToStringArray(String, String)} <code>delimiters</code> 参数
     * @return 如果 <code>baseName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>baseName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果资源文件 <code>baseName</code> 不存在,抛出 <code>MissingResourceException</code><br>
     * @see #getArray(ResourceBundle, String, String, Class)
     */
    public static String[] getArray(String baseName,String key,String delimiters){
        return getArray(baseName, key, delimiters, String.class);
    }

    // *****************************************************************************

    /**
     * 读取指定 <code>key</code> 的值,使用分隔符 <code>delimiters</code> 转成字符串数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * 在 <code>messages/feilong-core-test.properties</code> 配置文件中, 有以下配置
     * 
     * <pre class="code">
     * config_test_array=5,8,7,6
     * </pre>
     * 
     * <pre class="code">
     * ResourceBundleUtil.getArray(resourceBundle, "config_test_array", ",") =   ["5","8","7","6"]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param resourceBundle
     *            the resource bundle
     * @param key
     *            the key
     * @param delimiters
     *            分隔符,参见 {@link StringUtil#tokenizeToStringArray(String, String)} <code>delimiters</code> 参数
     * @return 如果 资源值不存在,返回null
     * @see #getArray(ResourceBundle, String, String, Class)
     */
    public static String[] getArray(ResourceBundle resourceBundle,String key,String delimiters){
        return getArray(resourceBundle, key, delimiters, String.class);
    }

    /**
     * 读取指定 <code>key</code> 的值,使用分隔符 <code>delimiters</code> 以及指定的转换类型 <code>typeClass</code> 转成数组.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * 在 <code>messages/feilong-core-test.properties</code> 配置文件中, 有以下配置
     * 
     * <pre class="code">
     * config_test_array=5,8,7,6
     * </pre>
     * 
     * <p>
     * 解析:
     * </p>
     * 
     * <pre class="code">
     * ResourceBundleUtil.getArray(resourceBundle, "config_test_array", ",", String.class);    =   ["5","8","7","6"]
     * ResourceBundleUtil.getArray(resourceBundle, "config_test_array", ",", Integer.class);   =   [5,8,7,6]
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param resourceBundle
     *            the resource bundle
     * @param key
     *            the key
     * @param delimiters
     *            分隔符,参见 {@link StringUtil#tokenizeToStringArray(String, String)} <code>delimiters</code> 参数
     * @param typeClass
     *            指明返回类型,<br>
     *            如果是String.class,则返回的是String []数组<br>
     *            如果是Integer.class,则返回的是Integer [] 数组
     * @return 如果 资源值不存在,返回null
     * @see #getValue(ResourceBundle, String)
     * @see StringUtil#tokenizeToStringArray(String, String)
     */
    public static <T> T[] getArray(ResourceBundle resourceBundle,String key,String delimiters,Class<T> typeClass){
        String value = ResourceBundleUtil.getValue(resourceBundle, key);
        String[] array = StringUtil.tokenizeToStringArray(value, delimiters);
        return ConvertUtil.toArray(array, typeClass);
    }

    /**
     * 获取Properties配置文件键值,转换成指定的 <code>typeClass</code> 类型返回.
     * 
     * @param <T>
     *            the generic type
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @param key
     *            the key
     * @param typeClass
     *            指明返回类型,<br>
     *            如果是String.class,则转换成String返回;<br>
     *            如果是Integer.class,则转换成Integer返回
     * @return 如果 <code>baseName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>baseName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果资源文件 <code>baseName</code> 不存在,抛出 <code>MissingResourceException</code><br>
     *         如果 <code>key</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>key</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     *         如果 <code>typeClass</code> 是null,抛出 {@link NullPointerException}<br>
     * @see #getValue(String, String)
     * @see ConvertUtil#convert(Object, Class)
     */
    public static <T> T getValue(String baseName,String key,Class<T> typeClass){
        String value = ResourceBundleUtil.getValue(baseName, key);
        return ConvertUtil.convert(value, typeClass);
    }

    /**
     * Read properties as array.
     */
    @Test
    public void readPropertiesAsArray(){
        assertArrayEquals(ConvertUtil.toStrings("5,8,7,6"), getArray(resourceBundle, "config_test_array", ",", String.class));
        assertArrayEquals(ConvertUtil.toIntegers("5,8,7,6"), getArray(resourceBundle, "config_test_array", ",", Integer.class));
    }

    /**
     * Test get value11.
     */
    @Test
    public void testGetValue11(){
        assertEquals((Integer) Integer.parseInt("0"), getValue(BASE_NAME, "wo_bu_cun_zai", Integer.class));
    }

    /**
     * 获得ResourceBundle({@link PropertyResourceBundle}),新增这个方法的初衷是为了能读取任意的资源(包括本地file等).
     * 
     * <p>
     * 参数 <code>fileName</code>是路径全地址
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * String mailReadFile = "E:\\DataCommon\\Files\\Java\\config\\mail-read.properties";
     * 
     * ResourceBundle resourceBundleRead = ResourceBundleUtil.getResourceBundleByFileName(mailReadFile);
     * 
     * String mailServerHost = resourceBundleRead.getString("incoming.pop.hostname");
     * </pre>
     * 
     * </blockquote>
     * 
     * @param fileName
     *            文件地址,比如: "E:\\DataCommon\\Files\\Java\\config\\mail-read.properties"
     * @return the resource bundle,may be null<br>
     *         如果 <code>fileName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>fileName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see java.util.PropertyResourceBundle#PropertyResourceBundle(InputStream)
     * @see ResourceBundleUtil#getResourceBundle(InputStream)
     * @since 1.0.9
     */
    public static ResourceBundle getResourceBundleByFileName(String fileName){
        Validate.notBlank(fileName, "fileName can't be null/empty!");
        try{
            return ResourceBundleUtil.getResourceBundle(new FileInputStream(fileName));
        }catch (FileNotFoundException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 获得 resource bundle({@link PropertyResourceBundle}),新增这个方法的初衷是为了能读取任意的资源(包括本地file等).
     *
     * @param reader
     *            the reader
     * @return 如果 <code>reader</code> 是null,抛出 {@link NullPointerException}<br>
     *         否则返回 {@link java.util.PropertyResourceBundle#PropertyResourceBundle(Reader)}
     * @see java.util.PropertyResourceBundle#PropertyResourceBundle(Reader)
     * @since 1.0.9
     */
    public static ResourceBundle getResourceBundle(Reader reader){
        Validate.notNull(reader, "reader can't be null!");
        try{
            return new PropertyResourceBundle(reader);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
}
