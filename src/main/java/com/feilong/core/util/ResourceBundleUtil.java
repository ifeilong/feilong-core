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
import java.io.Reader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.io.FileUtil;
import com.feilong.core.io.UncheckedIOException;
import com.feilong.core.lang.StringUtil;
import com.feilong.core.text.MessageFormatUtil;

/**
 * {@link java.util.ResourceBundle} 工具类.
 * 
 * <h3>如果现在多种资源文件一起出现，该如何访问？</h3>
 * 
 * <blockquote>
 * <p>
 * 如果一个项目中同时存在Message.properties、Message_zh_CN.properties、Message_zh_ CN.class 3个类型的文件，那最终使用的是哪一个?<br>
 * 只会使用一个，按照优先级使用。<br>
 * 顺序为Message_zh_CN.class、Message_zh_CN.properties、Message.properties。<br>
 * </p>
 * <p>
 * 解析原理，参见:<br>
 * {@link "java.util.ResourceBundle#loadBundle(CacheKey, List, Control, boolean)"}<br>
 * {@link java.util.ResourceBundle.Control#newBundle(String, Locale, String, ClassLoader, boolean)}
 * </p>
 * </blockquote>
 * 
 * @author feilong
 * @see MessageFormatUtil#format(String, Object...)
 * @see java.util.ResourceBundle
 * 
 * @see java.util.PropertyResourceBundle
 * @see java.util.ListResourceBundle
 * @version 1.4.0 2015年8月3日 上午3:18:50
 * @since 1.4.0
 */
public final class ResourceBundleUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceBundleUtil.class);

    /** Don't let anyone instantiate this class. */
    private ResourceBundleUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获取Properties配置文件键值,按照typeClass 返回对应的类型.
     * 
     * @param <T>
     *            the generic type
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param key
     *            the key
     * @param typeClass
     *            指明返回类型,<br>
     *            如果是String.class,则返回的是String <br>
     *            如果是Integer.class,则返回的是Integer
     * @return the value
     * @see #getValue(String, String)
     * @see com.feilong.core.bean.ConvertUtil#convert(Object, Class)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(String baseName,String key,Class<?> typeClass){
        String value = getValue(baseName, key);
        return (T) ConvertUtil.convert(value, typeClass);
    }

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
     * @return the value
     * @see #getValue(ResourceBundle, String)
     * @see com.feilong.core.bean.ConvertUtil#convert(Object, Class)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValue(ResourceBundle resourceBundle,String key,Class<?> typeClass){
        String value = getValue(resourceBundle, key);
        return (T) ConvertUtil.convert(value, typeClass);
    }

    /**
     * 获取Properties配置文件键值 ,采用 {@link java.util.ResourceBundle#getBundle(String)} 方法来读取.
     *
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param key
     *            Properties配置文件键名
     * @return 该键的值
     * @see #getResourceBundle(String)
     * @see #getValue(ResourceBundle, String)
     * @since 1.0
     */
    public static String getValue(String baseName,String key){
        ResourceBundle resourceBundle = getResourceBundle(baseName);
        return getValue(resourceBundle, key);
    }

    /**
     * 获取Properties配置文件键值 ,采用 {@link java.util.ResourceBundle#getBundle(String)} 方法来读取.
     *
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param key
     *            Properties配置文件键名
     * @param locale
     *            the locale
     * @return 该键的值
     * @see #getResourceBundle(String, Locale)
     * @see #getValue(ResourceBundle, String)
     * @since 1.0.5
     */
    public static String getValue(String baseName,String key,Locale locale){
        ResourceBundle resourceBundle = getResourceBundle(baseName, locale);
        return getValue(resourceBundle, key);
    }

    /**
     * 带参数的 配置文件.
     * 
     * <p>
     * 格式如:name={0}.
     * </p>
     * 
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param key
     *            the key
     * @param locale
     *            the locale
     * @param arguments
     *            此处可以传递Object[]数组过来
     * @return the value with arguments
     * @see #getResourceBundle(String, Locale)
     * @see #getValueWithArguments(ResourceBundle, String, Object...)
     */
    public static String getValueWithArguments(String baseName,String key,Locale locale,Object...arguments){
        ResourceBundle resourceBundle = getResourceBundle(baseName, locale);
        return getValueWithArguments(resourceBundle, key, arguments);
    }

    /**
     * 获取Properties配置文件键值 ,采用 {@link java.util.ResourceBundle#getBundle(String)} 方法来读取.
     * 
     * @param resourceBundle
     *            配置文件的包+类全名(不要尾缀)
     * @param key
     *            Properties配置文件键名
     * @return 该键的值<br>
     *         如果配置文件中,
     *         <ul>
     *         <li>key不存在,LOGGER.warn 输出警告,然后返回null</li>
     *         <li>key存在,但value是null 或者 empty,LOGGER.warn 输出警告,然后返回value</li>
     *         </ul>
     * @see java.util.ResourceBundle#getString(String)
     */
    public static String getValue(ResourceBundle resourceBundle,String key){
        if (!resourceBundle.containsKey(key)){
            LOGGER.debug("resourceBundle:[{}] don't containsKey:[{}]", resourceBundle, key);
            return StringUtils.EMPTY;
        }

        try{
            String value = resourceBundle.getString(key);
            if (Validator.isNullOrEmpty(value)){
                LOGGER.debug("resourceBundle has key:[{}],but value is null/empty", key);
            }
            return value;
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 带参数的 配置文件.
     * <p>
     * 格式如:name={0}.
     * </p>
     * 
     * @param resourceBundle
     *            the resource bundle
     * @param key
     *            如上面的 name
     * @param arguments
     *            此处可以传递Object[]数组过来
     * @return 支持 arguments 为null,原样返回
     * @see MessageFormatUtil
     * @see MessageFormatUtil#format(String, Object...)
     */
    public static String getValueWithArguments(ResourceBundle resourceBundle,String key,Object...arguments){
        String value = getValue(resourceBundle, key);
        if (Validator.isNullOrEmpty(value)){
            return StringUtils.EMPTY;
        }
        // 支持 arguments 为null,原样返回
        return MessageFormatUtil.format(value, arguments);
    }

    // *****************************************************************************
    /**
     * 读取值,转成数组.
     * <p>
     * 默认调用 {@link #getArray(ResourceBundle, String, String, Class)} 形式
     * </p>
     * 
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param key
     *            the key
     * @param spliter
     *            分隔符
     * @return 以value.split(spliter),如果 资源值不存在,返回null
     * @see #getArray(ResourceBundle, String, String, Class)
     */
    public static String[] getArray(String baseName,String key,String spliter){
        return getArray(baseName, key, spliter, String.class);
    }

    /**
     * 读取值,转成数组.
     * <p>
     * 默认调用 {@link #getArray(ResourceBundle, String, String, Class)} 形式
     * </p>
     * 
     * @param resourceBundle
     *            the resource bundle
     * @param key
     *            the key
     * @param spliter
     *            分隔符
     * @return 以value.split(spliter),如果 资源值不存在,返回null
     * @see #getArray(ResourceBundle, String, String, Class)
     */
    public static String[] getArray(ResourceBundle resourceBundle,String key,String spliter){
        return getArray(resourceBundle, key, spliter, String.class);
    }

    /**
     * 读取值,转成数组.
     * 
     * @param <T>
     *            the generic type
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param key
     *            the key
     * @param spliter
     *            分隔符
     * @param typeClass
     *            指明返回类型,<br>
     *            如果是String.class,则返回的是String []数组<br>
     *            如果是Integer.class,则返回的是Integer [] 数组
     * @return 以value.split(spliter),如果 资源值不存在,返回null
     * @see #getResourceBundle(String)
     * @see #getArray(ResourceBundle, String, String, Class)
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] getArray(String baseName,String key,String spliter,Class<?> typeClass){
        ResourceBundle resourceBundle = getResourceBundle(baseName);
        return (T[]) getArray(resourceBundle, key, spliter, typeClass);
    }

    /**
     * 读取值,转成数组.
     * 
     * @param <T>
     *            the generic type
     * @param resourceBundle
     *            the resource bundle
     * @param key
     *            the key
     * @param spliter
     *            分隔符
     * @param typeClass
     *            指明返回类型,<br>
     *            如果是String.class,则返回的是String []数组<br>
     *            如果是Integer.class,则返回的是Integer [] 数组
     * @return 以value.split(spliter),如果 资源值不存在,返回null
     * @see #getValue(ResourceBundle, String)
     * @see com.feilong.core.lang.StringUtil#tokenizeToStringArray(String, String)
     */
    public static <T> T[] getArray(ResourceBundle resourceBundle,String key,String spliter,Class<T> typeClass){
        String value = getValue(resourceBundle, key);
        String[] array = StringUtil.tokenizeToStringArray(value, spliter);
        return ConvertUtil.convert(array, typeClass);
    }

    // **************************************************************************
    /**
     * Read prefix as map(HashMap).
     * 
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param prefix
     *            前缀
     * @param spliter
     *            the spliter
     * @param locale
     *            the locale
     * @return 如果 baseName 没有key value,则返回null,否则,解析所有的key和value转成HashMap
     * @see #readAllPropertiesToMap(String, Locale)
     */
    public static Map<String, String> readPrefixAsMap(String baseName,String prefix,String spliter,Locale locale){
        Map<String, String> propertyMap = readAllPropertiesToMap(baseName, locale);
        if (Validator.isNullOrEmpty(propertyMap)){
            return Collections.emptyMap();
        }

        Map<String, String> result = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : propertyMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            // 以 prefix 开头
            if (key.startsWith(prefix)){
                // 分隔
                String[] values = key.split(spliter);
                if (values.length >= 2){
                    result.put(values[1], value);
                }
            }
        }
        return result;
    }

    /**
     * 读取配置文件,将k/v 统统转成map(HashMap).
     * 
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @return 如果 baseName 没有key value,则返回null,否则,解析所有的key和value转成HashMap
     * @see #readAllPropertiesToMap(String, Locale)
     * @since 1.2.1
     */
    public static Map<String, String> readAllPropertiesToMap(String baseName){
        final Locale defaultLocale = Locale.getDefault();
        return readAllPropertiesToMap(baseName, defaultLocale);

    }

    /**
     * 读取配置文件,将k/v 统统转成map(HashMap).
     * 
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param locale
     *            the locale 支持国际化
     * @return 如果 baseName 没有key value,则返回null,否则,解析所有的key和value转成HashMap
     * @see #getResourceBundle(String, Locale)
     * @see java.util.ResourceBundle#getKeys()
     * @see MapUtils#toMap(ResourceBundle)
     */
    public static Map<String, String> readAllPropertiesToMap(String baseName,Locale locale){
        ResourceBundle resourceBundle = getResourceBundle(baseName, locale);
        Enumeration<String> enumeration = resourceBundle.getKeys();
        if (Validator.isNullOrEmpty(enumeration)){
            return Collections.emptyMap();
        }

        Map<String, String> propertyMap = new HashMap<String, String>();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            String value = resourceBundle.getString(key);
            propertyMap.put(key, value);
        }
        return propertyMap;
    }

    /**
     * 获得ResourceBundle.
     * 
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @return the resource bundle
     * @see java.util.Locale#getDefault()
     * @see #getResourceBundle(String, Locale)
     */
    public static ResourceBundle getResourceBundle(String baseName){
        // Locale enLoc = new Locale("en", "US"); // 表示美国地区
        return getResourceBundle(baseName, Locale.getDefault());
    }

    /**
     * 获得ResourceBundle.
     * 
     * @param baseName
     *            配置文件的包+类全名<span style="color:red">(不要尾缀)</span>,the base name of the resource bundle, a fully qualified class name
     * @param locale
     *            the locale for which a resource bundle is desired
     * @return the resource bundle,may be null
     * @see java.util.ResourceBundle#getBundle(String, Locale)
     */
    public static ResourceBundle getResourceBundle(String baseName,Locale locale){
        if (Validator.isNullOrEmpty(baseName)){
            throw new IllegalArgumentException("baseName can't be null/empty!");
        }
        if (Validator.isNullOrEmpty(locale)){
            throw new IllegalArgumentException("locale can't be null/empty!");
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale);
        if (null == resourceBundle){
            LOGGER.warn("resourceBundle is null,baseName:{},locale:{}", resourceBundle, baseName, locale);
        }
        return resourceBundle;
    }

    //*****************************************************************************

    /**
     * 获得ResourceBundle({@link PropertyResourceBundle}),新增这个方法的初衷是为了能读取任意的资源(包括本地file等).
     *
     * @param fileName
     *            the file name
     * @return the resource bundle,may be null
     * @see com.feilong.core.io.FileUtil#getFileInputStream(String)
     * @see java.util.PropertyResourceBundle#PropertyResourceBundle(InputStream)
     * @see ResourceBundleUtil#getResourceBundle(InputStream)
     * @since 1.0.9
     */
    public static ResourceBundle getResourceBundleByFileName(String fileName){
        if (Validator.isNullOrEmpty(fileName)){
            throw new IllegalArgumentException("fileName can't be null/empty!");
        }
        InputStream inputStream = FileUtil.getFileInputStream(fileName);
        return getResourceBundle(inputStream);
    }

    /**
     * 获得ResourceBundle({@link PropertyResourceBundle}),新增这个方法的初衷是为了能读取任意的资源(包括本地file等).
     *
     * @param inputStream
     *            the input stream
     * @return the resource bundle,may be null
     * @see java.util.PropertyResourceBundle#PropertyResourceBundle(InputStream)
     * @since 1.0.9
     */
    public static ResourceBundle getResourceBundle(InputStream inputStream){
        if (Validator.isNullOrEmpty(inputStream)){
            throw new IllegalArgumentException("inputStream can't be null/empty!");
        }
        try{
            return new PropertyResourceBundle(inputStream);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 获得 resource bundle({@link PropertyResourceBundle}),新增这个方法的初衷是为了能读取任意的资源(包括本地file等).
     *
     * @param reader
     *            the reader
     * @return the resource bundle
     * @see java.util.PropertyResourceBundle#PropertyResourceBundle(Reader)
     * @since 1.0.9
     */
    public static ResourceBundle getResourceBundle(Reader reader){
        if (Validator.isNullOrEmpty(reader)){
            throw new IllegalArgumentException("reader can't be null/empty!");
        }
        try{
            return new PropertyResourceBundle(reader);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }
}