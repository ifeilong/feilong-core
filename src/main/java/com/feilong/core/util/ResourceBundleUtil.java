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

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.UncheckedIOException;
import com.feilong.core.bean.BeanUtil;
import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.text.MessageFormatUtil;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.bean.ConvertUtil.toProperties;
import static com.feilong.core.lang.reflect.ConstructorUtil.newInstance;

/**
 * {@link java.util.ResourceBundle ResourceBundle} 工具类.
 * 
 * <p>
 * 该类专注于解析配置文件,至于解析到的结果你可以使用 {@link ConvertUtil}来进行转换成你需要的类型
 * </p>
 * 
 * <h3>如果现在多种资源文件一起出现,该如何访问？</h3>
 * 
 * <blockquote>
 * <p>
 * 如果一个项目中同时存在Message.properties、Message_zh_CN.properties、Message_zh_ CN.class 3个类型的文件,那最终使用的是哪一个?<br>
 * 只会使用一个,按照优先级使用.<br>
 * 顺序为Message_zh_CN.class、Message_zh_CN.properties、Message.properties.<br>
 * </p>
 * 
 * <p>
 * 解析原理,参见:<br>
 * {@link "java.util.ResourceBundle#loadBundle(CacheKey, List, Control, boolean)"}<br>
 * {@link java.util.ResourceBundle.Control#newBundle(String, Locale, String, ClassLoader, boolean)}
 * </p>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see MessageFormatUtil#format(String, Object...)
 * @see java.util.ResourceBundle
 * @see java.util.PropertyResourceBundle
 * @see java.util.ListResourceBundle
 * @see "org.springframework.core.io.support.LocalizedResourceHelper"
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

    // ****************************getValue*************************************************

    /**
     * 获取Properties配置文件键值 ,采用 {@link java.util.ResourceBundle#getBundle(String)} 方法来读取.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>支持配置文件含参数信息 <code>arguments</code> ,使用 {@link MessageFormatUtil#format(String, Object...)} 来解析</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 如果有配置文件 messages\feilong-core-test.properties ,内容如下:
     * </p>
     * 
     * <pre class="code">
     * test.arguments=my name is {0},age is {1}
     * </pre>
     * 
     * 此时调用方法:
     * 
     * <pre class="code">
     * ResourceBundleUtil.getValueWithArguments(resourceBundle, "test.arguments", "feilong", "18")
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * my name is feilong,age is 18
     * </pre>
     * 
     * </blockquote>
     *
     * @param resourceBundle
     *            the resource bundle
     * @param key
     *            Properties配置文件键名
     * @param arguments
     *            此处可以传递Object[]数组过来
     * @return 如果配置文件中,key不存在,LOGGER.warn警告输出,并返回 {@link StringUtils#EMPTY}<br>
     * @throws NullPointerException
     *             如果 <code>resourceBundle</code> 或者 <code>key</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>key</code> 是blank,抛出 {@link IllegalArgumentException}
     * @see java.util.ResourceBundle#getString(String)
     * @see MessageFormatUtil#format(String, Object...)
     * @since 1.8.1 support arguments param
     */
    public static String getValue(ResourceBundle resourceBundle,String key,Object...arguments){
        Validate.notNull(resourceBundle, "resourceBundle can't be null!");
        Validate.notBlank(key, "key can't be null/empty!");

        if (!resourceBundle.containsKey(key)){
            LOGGER.warn("resourceBundle:[{}] don't containsKey:[{}]", resourceBundle, key);
            return EMPTY;
        }

        String value = resourceBundle.getString(key);
        if (isNullOrEmpty(value)){
            LOGGER.trace("resourceBundle has key:[{}],but value is null/empty", key);
        }
        return isNullOrEmpty(value) ? EMPTY : MessageFormatUtil.format(value, arguments);// 支持 arguments 为null,原样返回
    }

    //***************************************************************************

    /**
     * 读取配置文件,将k/v 统统转成map.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>JDK实现{@link java.util.PropertyResourceBundle},内部是使用 hashmap来存储数据的,<br>
     * 本方法出于log以及使用方便,返回的是<span style="color:red">TreeMap</span></li>
     * </ol>
     * </blockquote>
     * 
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @return 如果 <code>baseName</code> 没有key value,则返回{@link java.util.Collections#emptyMap()}<br>
     *         否则,解析所有的key和value转成 {@link TreeMap}
     * @throws NullPointerException
     *             如果 <code>baseName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>baseName</code> 是 blank
     * @throws MissingResourceException
     *             如果资源文件 <code>baseName</code> 不存在
     * @see #readToMap(String, Locale)
     * @since 1.8.1 change name
     */
    public static Map<String, String> readToMap(String baseName){
        return readToMap(baseName, null);
    }

    /**
     * 读取配置文件,将k/v 统统转成Properties.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 在 classpath messages 目录下面有 memcached.properties,内容如下:
     * </p>
     * 
     * <pre class="code">
     * #expiretime period in minutes
     * #逗号 分隔
     * 
     * # 注意此处 ip出现 - 横杆 仅作测试使用
     * memcached.serverlist=172.20.3-1.23:11211,172.20.31.22:11211 
     * memcached.poolname=sidsock2
     * #单位分钟
     * memcached.expiretime=180
     * 
     * memcached.serverweight=2
     * 
     * memcached.initconnection=10
     * memcached.minconnection=5
     * memcached.maxconnection=250
     * 
     * #设置主线程睡眠时间,每30秒苏醒一次,维持连接池大小
     * memcached.maintSleep=30
     * 
     * #关闭套接字缓存
     * memcached.nagle=false
     * 
     * #连接建立后的超时时间
     * memcached.socketto=3000
     * memcached.alivecheck=false
     * 
     * </pre>
     * 
     * <b>
     * 此时你可以如此调用代码:
     * </b>
     * 
     * <pre class="code">
     * Properties properties = ResourceBundleUtil.readToProperties("messages.memcached");
     * LOGGER.debug(JsonUtil.format(properties));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "memcached.serverlist": "172.20.3-1.23:11211,172.20.31.22:11211",
        "memcached.maxconnection": "250",
        "memcached.socketto": "3000",
        "memcached.initconnection": "10",
        "memcached.nagle": "false",
        "memcached.expiretime": "180",
        "memcached.maintSleep": "30",
        "memcached.alivecheck": "false",
        "memcached.serverweight": "2",
        "memcached.poolname": "sidsock2",
        "memcached.minconnection": "5"
    }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @return 如果 <code>baseName</code> 没有key value,则返回 <code>new Properties</code><br>
     *         否则,解析所有的key和value转成 {@link Properties}
     * @throws NullPointerException
     *             如果 <code>baseName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>baseName</code> 是 blank
     * @throws MissingResourceException
     *             如果资源文件 <code>baseName</code> 不存在
     * @see #readToMap(String)
     * @see ConvertUtil#toProperties(Map)
     * @since 1.8.1
     */
    public static Properties readToProperties(String baseName){
        return toProperties(readToMap(baseName));
    }

    /**
     * 读取配置文件,将k/v 统统转成map.
     * 
     * <p>
     * 注意:JDK实现{@link java.util.PropertyResourceBundle},内部是使用 hashmap来存储数据的,<br>
     * 本方法出于log以及使用方便,返回的是<span style="color:red"> TreeMap</span>
     * </p>
     * 
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @param locale
     *            the locale for which a resource bundle is desired,如果是null,将使用 {@link Locale#getDefault()}
     * @return 如果 <code>baseName</code> 没有key value,则返回{@link java.util.Collections#emptyMap()}<br>
     *         否则,解析所有的key和value转成 {@link TreeMap}<br>
     * 
     * @throws NullPointerException
     *             如果 <code>baseName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>baseName</code> 是 blank
     * @throws MissingResourceException
     *             如果资源文件 <code>baseName</code> 不存在
     * @see #getResourceBundle(String, Locale)
     * @see java.util.ResourceBundle#getKeys()
     * @see MapUtils#toMap(ResourceBundle)
     * @since 1.8.1 change name
     */
    public static Map<String, String> readToMap(String baseName,Locale locale){
        ResourceBundle resourceBundle = getResourceBundle(baseName, locale);
        return toMap(resourceBundle);
    }

    /**
     * 读取properties配置文件,直接转换成aliasBean.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * 在 classpath messages 目录下面有 memcached.properties,内容如下:
     * </p>
     * 
     * <pre class="code">
     * #expiretime period in minutes
     * #逗号 分隔
     * 
     * # 注意此处 ip出现 - 横杆 仅作测试使用
     * memcached.serverlist=172.20.3-1.23:11211,172.20.31.22:11211 
     * memcached.poolname=sidsock2
     * #单位分钟
     * memcached.expiretime=180
     * 
     * memcached.serverweight=2
     * 
     * memcached.initconnection=10
     * memcached.minconnection=5
     * memcached.maxconnection=250
     * 
     * #设置主线程睡眠时间,每30秒苏醒一次,维持连接池大小
     * memcached.maintSleep=30
     * 
     * #关闭套接字缓存
     * memcached.nagle=false
     * 
     * #连接建立后的超时时间
     * memcached.socketto=3000
     * memcached.alivecheck=false
     * 
     * </pre>
     * 
     * <p>
     * 有以下<b>aliasBean</b>信息:
     * </p>
     * 
     * <pre class="code">
     * 
     * public class DangaMemCachedConfig{
     * 
     *     <span style="color:green">//** The serverlist.</span>
     *     &#64;Alias(name = "memcached.serverlist",sampleValue = "172.20.31.23:11211,172.20.31.22:11211")
     *     private String[] serverList;
     * 
     *     <span style="color:green">//@Alias(name = "memcached.poolname",sampleValue = "sidsock2")</span>
     *     private String poolName;
     * 
     *     <span style="color:green">//** The expire time 单位分钟.</span>
     *     &#64;Alias(name = "memcached.expiretime",sampleValue = "180")
     *     private Integer expireTime;
     * 
     *     <span style="color:green">//** 权重. </span>
     *     &#64;Alias(name = "memcached.serverweight",sampleValue = "2,1")
     *     private Integer[] weight;
     * 
     *     <span style="color:green">//** The init connection. </span>
     *     &#64;Alias(name = "memcached.initconnection",sampleValue = "10")
     *     private Integer initConnection;
     * 
     *     <span style="color:green">//** The min connection.</span>
     *     &#64;Alias(name = "memcached.minconnection",sampleValue = "5")
     *     private Integer minConnection;
     * 
     *     <span style="color:green">//** The max connection. </span>
     *     &#64;Alias(name = "memcached.maxconnection",sampleValue = "250")
     *     private Integer maxConnection;
     * 
     *     <span style="color:green">//** 设置主线程睡眠时间,每30秒苏醒一次,维持连接池大小.</span>
     *     &#64;Alias(name = "memcached.maintSleep",sampleValue = "30")
     *     private Integer maintSleep;
     * 
     *     <span style="color:green">//** 关闭套接字缓存. </span>
     *     &#64;Alias(name = "memcached.nagle",sampleValue = "false")
     *     private Boolean nagle;
     * 
     *     <span style="color:green">//** 连接建立后的超时时间.</span>
     *     &#64;Alias(name = "memcached.socketto",sampleValue = "3000")
     *     private Integer socketTo;
     * 
     *     <span style="color:green">//** The alive check.</span>
     *     &#64;Alias(name = "memcached.alivecheck",sampleValue = "false")
     *     private Boolean aliveCheck;
     * 
     *     <span style="color:green">//setter getter 略</span>
     * }
     * 
     * </pre>
     * 
     * <b>
     * 此时你可以如此调用代码:
     * </b>
     * 
     * <pre class="code">
     * DangaMemCachedConfig dangaMemCachedConfig = ResourceBundleUtil
     *                 .readPropertiesToAliasBean("messages.memcached", DangaMemCachedConfig.class);
     * LOGGER.debug(JsonUtil.format(dangaMemCachedConfig));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "maxConnection": 250,
        "expireTime": 180,
        "serverList":         [
            "172.20.3-1.23",
            "11211",
            "172.20.31.22",
            "11211"
        ],
        "weight": [2],
        "nagle": false,
        "initConnection": 10,
        "aliveCheck": false,
        "poolName": "sidsock2",
        "maintSleep": 30,
        "socketTo": 3000,
        "minConnection": 5
    }
     * 
     * </pre>
     * 
     * <p>
     * 你会发现类型会自动转换, 虽然properties里面存储key和value都是string,但是使用该方法, 可以自动类型转换,转成bean里面声明的类型
     * </p>
     * 
     * <p>
     * 但是同时,你也会发现,上面的 serverList 期望值是 ["172.20.3-1.23:11211","172.20.31.22:11211"],但是和你的期望值不符合,<br>
     * 因为, {@link ArrayConverter} 默认允许的字符 allowedChars 只有 <code>'.', '-'</code>,其他都会被做成分隔符
     * </p>
     * 
     * <p>
     * 你需要如此这般:
     * </p>
     *
     * <pre class="code">
     * ArrayConverter arrayConverter = new ArrayConverter(String[].class, new StringConverter(), 2);
     * char[] allowedChars = { ':' };
     * arrayConverter.setAllowedChars(allowedChars);
     * 
     * BeanUtil.register(arrayConverter, String[].class);
     * 
     * DangaMemCachedConfig dangaMemCachedConfig = ResourceBundleUtil
     *                 .readPropertiesToAliasBean("messages.memcached", DangaMemCachedConfig.class);
     * LOGGER.debug(JsonUtil.format(dangaMemCachedConfig));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "maxConnection": 250,
        "expireTime": 180,
        "serverList":         [
            "172.20.3-1.23:11211",
            "172.20.31.22:11211"
        ],
        "weight": [2],
        "nagle": false,
        "initConnection": 10,
        "aliveCheck": false,
        "poolName": "sidsock2",
        "maintSleep": 30,
        "socketTo": 3000,
        "minConnection": 5
    }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @param aliasBeanClass
     *            the alias bean class
     * @return the t
     * @throws NullPointerException
     *             如果 <code>baseName</code>或者 <code>aliasBean</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>baseName</code> 是 blank
     * @throws MissingResourceException
     *             如果资源文件 <code>baseName</code> 不存在
     * @see com.feilong.core.bean.BeanUtil#populateAliasBean(Object, Map)
     * @since 1.8.1
     */
    public static <T> T readToAliasBean(String baseName,Class<T> aliasBeanClass){
        Validate.notBlank(baseName, "baseName can't be null/empty!");
        Validate.notNull(aliasBeanClass, "aliasBeanClass can't be null!");
        return BeanUtil.populateAliasBean(newInstance(aliasBeanClass), readToMap(baseName));
    }

    //********************************getResourceBundle**********************************************

    /**
     * 使用 {@link Locale#getDefault()} 获得{@link ResourceBundle}.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 如项目的classpath下面有 messages/feilong-core-test.properties,内容如下
     * </p>
     * 
     * <pre class="code">
     * config_test_array=5,8,7,6
     * test.arguments=my name is {0},age is {1}
     * </pre>
     * 
     * <p>
     * 你可以使用以下代码来读取内容:
     * </p>
     * 
     * <pre class="code">
     * ResourceBundle resourceBundle = getResourceBundle("messages/feilong-core-test");
     * LOGGER.debug(JsonUtil.format(toMap(resourceBundle)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "config_test_array": "5,8,7,6",
     * "test.arguments": "my name is {0},age is {1}",
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @return 如果资源文件 <code>baseName</code> 里面没有任何内容,返回不是null的 {@link ResourceBundle}
     * @throws NullPointerException
     *             如果 <code>baseName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>baseName</code> 是 blank
     * @throws MissingResourceException
     *             如果资源文件 <code>baseName</code> 不存在
     * @see java.util.Locale#getDefault()
     * @see #getResourceBundle(String, Locale)
     */
    public static ResourceBundle getResourceBundle(String baseName){
        return getResourceBundle(baseName, null);
    }

    /**
     * 获得{@link ResourceBundle}.
     * 
     * @param baseName
     *            一个完全限定类名,<b>配置文件的包+类全名</b>,比如 <b>message.feilong-core-test</b> <span style="color:red">(不要尾缀)</span>;<br>
     *            但是,为了和早期版本兼容,也可使用路径名来访问,比如<b>message/feilong-core-test</b><span style="color:red">(使用 "/")</span>
     * @param locale
     *            the locale for which a resource bundle is desired,如果是null,将使用 {@link Locale#getDefault()}
     * @return 如果资源文件 <code>baseName</code> 里面没有任何内容,返回不是null的 {@link ResourceBundle}
     * @throws NullPointerException
     *             如果 <code>baseName</code> 是null
     * @throws IllegalArgumentException
     *             如果 <code>baseName</code> 是 blank
     * @throws MissingResourceException
     *             如果资源文件 <code>baseName</code> 不存在
     * @see java.util.ResourceBundle#getBundle(String, Locale)
     */
    public static ResourceBundle getResourceBundle(String baseName,Locale locale){
        Validate.notBlank(baseName, "baseName can't be null/empty!");
        return ResourceBundle.getBundle(baseName, ObjectUtils.defaultIfNull(locale, Locale.getDefault()));
    }

    //*****************************************************************************

    /**
     * 获得ResourceBundle({@link PropertyResourceBundle}),新增这个方法的初衷是为了能读取任意的资源(包括本地file等).
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 有配置文件在 E:\\DataCommon\\Files\\Java\\config\\mail-read.properties (通常敏感的配置文件不会随着项目走),现在需要读取里面的信息
     * </p>
     * 
     * <pre class="code">
     * 
     * ResourceBundle resourceBundle = getResourceBundle(new FileInputStream("E:\\DataCommon\\Files\\Java\\config\\mail-read.properties"));
     * LOGGER.debug(JsonUtil.format(toMap(resourceBundle)));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "incoming.imap.hostname": "imap.exmail.qq.com",
     * "incoming.pop.hostname": "pop.exmail.qq.com",
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param inputStream
     *            the input stream
     * @return 如果 <code>inputStream</code> 是null,抛出 {@link NullPointerException}<br>
     *         否则返回 {@link java.util.PropertyResourceBundle#PropertyResourceBundle(InputStream)}
     * @see java.util.PropertyResourceBundle#PropertyResourceBundle(InputStream)
     * @since 1.0.9
     */
    public static ResourceBundle getResourceBundle(InputStream inputStream){
        Validate.notNull(inputStream, "inputStream can't be null!");
        try{
            return new PropertyResourceBundle(inputStream);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

}