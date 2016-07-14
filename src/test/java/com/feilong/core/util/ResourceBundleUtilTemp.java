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

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * @see #readAllPropertiesToMap(String, Locale)
     */
    public static Map<String, String> readPrefixAsMap(String baseName,String prefix,String delimiters,Locale locale){
        Map<String, String> propertyMap = ResourceBundleUtil.readAllPropertiesToMap(baseName, locale);
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
        return ResourceBundleUtil.getArray(resourceBundle, key, delimiters, typeClass);
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
}
