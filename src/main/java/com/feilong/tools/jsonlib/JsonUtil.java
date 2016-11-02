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
package com.feilong.tools.jsonlib;

import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.ArrayUtil;
import com.feilong.core.lang.reflect.FieldUtil;
import com.feilong.tools.jsonlib.processor.SensitiveWordsJsonValueProcessor;

import static com.feilong.core.Validator.isNotNullOrEmpty;
import static com.feilong.core.Validator.isNullOrEmpty;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_TIME;

import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.processors.PropertyNameProcessor;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.JavaIdentifierTransformer;
import net.sf.json.util.PropertySetStrategy;

/**
 * json处理工具类.
 * 
 * <h3>提供以下主要方法:</h3>
 * <blockquote>
 * 
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">方法:</th>
 * <th align="left">说明:</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link #format(Object)}</td>
 * <td>将对象格式化成json字符串</td>
 * </tr>
 * 
 * </table>
 * </blockquote>
 * 
 * <h3>json-lib format map的时候或者 json转成对象/数组/map等的时候</h3>
 * 
 * <blockquote>
 * <p>
 * see {@link net.sf.json.JSONObject#_fromMap(Map, JsonConfig)}
 * </p>
 * <ul>
 * <li>key不能是null</li>
 * <li>key也不能是"null" 字符串</li>
 * </ul>
 * </blockquote>
 * 
 * <h3>依赖于下面的jar:</h3>
 * 
 * <blockquote>
 * 
 * <pre class="code">
 * {@code
 * <groupId>net.sf.json-lib</groupId>
 * <artifactId>json-lib</artifactId>
 * }
 * 
 * 如果要使用 xml部分功能,需要
 * 
 * {@code
 * <groupId>xom</groupId> 
 * <artifactId>xom</artifactId>
 * }
 * 
 * 目前本工具类不再提供处理XML的方法, 请使用 xstream或者原生的XML来处理
 * </pre>
 * 
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see net.sf.json.JSONSerializer#toJSON(Object, JsonConfig)
 * 
 * @see net.sf.json.JSONObject
 * @see net.sf.json.JSONArray
 * @see net.sf.json.JSONNull
 * @since 1.0.5
 */
//XXX @deprecated net.sf.json-lib Non-maintenance,will use Jackson instead
public final class JsonUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /** Don't let anyone instantiate this class. */
    private JsonUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 设置日期转换格式.
     */
    static{
        // 可转换的日期格式,即Json串中可以出现以下格式的日期与时间
        // 注意:此处的代码不能移到 JsonHelper,否则json转成 java的时候 日期格式会出错
        MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
        morpherRegistry.registerMorpher(new DateMorpher(ConvertUtil.toArray(COMMON_DATE_AND_TIME, COMMON_TIME, COMMON_DATE)));

    }
    //***************************format********************************************************

    // [start] format

    /**
     * 将对象 <code>obj</code> 格式化成json字符串.
     * 
     * <p>
     * 该方法还可以格式化json字符串成缩进形式的格式
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
    {"userAddresseList":[{"address":"上海市闸北区万荣路1188号H座109-118室"},{"address":"上海市闸北区阳城路280弄25号802室(阳城贵都)"}],"userAddresses":[{"address":"上海市闸北区万荣路1188号H座109-118室"},{"address":"上海市闸北区阳城路280弄25号802室(阳城贵都)"}],"date":"2016-06-09 17:40:28","password":"******","id":8,"nickName":[],"age":0,"name":"feilong","money":99999999,"attrMap":null,"userInfo":{"age":10},"loves":["桔子","香蕉"]}
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
            "userAddresseList":         [
                {"address": "上海市闸北区万荣路1188号H座109-118室"},
                {"address": "上海市闸北区阳城路280弄25号802室(阳城贵都)"}
            ],
            "userAddresses":         [
                {"address": "上海市闸北区万荣路1188号H座109-118室"},
                {"address": "上海市闸北区阳城路280弄25号802室(阳城贵都)"}
            ],
            "date": "2016-06-09 17:40:28",
            "password": "******",
            "id": 8,
            "nickName": [],
            "age": 0,
            "name": "feilong",
            "money": 99999999,
            "attrMap": null,
            "userInfo": {"age": 10},
            "loves":         [
                "桔子",
                "香蕉"
            ]
        }
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>关于 <code>indent</code>缩进:</h3>
     * 
     * <blockquote>
     * <p>
     * 默认使用 toString(4,4) 缩进
     * </p>
     * 
     * <p>
     * 如果不需要 <code>indent</code>缩进,你可以调用 {@link #format(Object, int, int)}或者 {@link #format(Object, JsonConfig, int, int)}或者
     * {@link #format(Object, JavaToJsonConfig, int, int)}
     * </p>
     * </blockquote>
     * 
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see #format(Object, JavaToJsonConfig)
     */
    public static String format(Object obj){
        return format(obj, (JavaToJsonConfig) null);
    }

    /**
     * 有些map 值很复杂,比如带有request信息, 这样的map转成json很可能由于一些级联关系而抛异常.
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>此方法 将inputMap转成 simpleMap(<span style="color:red">原始inputMap不会变更</span>)</li>
     * <li>此方法转换的simpleMap是 {@link TreeMap}类型,转换的json key经过排序的</li>
     * </ul>
     * </blockquote>
     * 
     * <h3>转换规则:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>如果value 是isPrimitiveOrWrapper类型, 那么会直接取到值 设置到 新的simpleMap中</li>
     * <li>否则 使用 {@link String#valueOf(Object)} 转换到simpleMap中</li>
     * </ul>
     * </blockquote>.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param inputMap
     *            the input map
     * @return 如果 <code>inputMap</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @since 1.3.0
     */
    public static <K, V> String formatSimpleMap(Map<K, V> inputMap){
        return null == inputMap ? EMPTY : formatSimpleMap(inputMap, (Class<?>) null);
    }

    /**
     * 有些map 值很复杂,比如带有request信息, 这样的map转成json很可能由于一些级联关系而抛异常.
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>此方法 将inputMap转成 simpleMap(<span style="color:red">原始inputMap不会变更</span>)</li>
     * <li>此方法转换的simpleMap是 {@link TreeMap}类型,转换的json key经过排序的</li>
     * </ul>
     * </blockquote>
     * 
     * <h3>转换规则:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>如果value是isPrimitiveOrWrapper类型,那么会直接取到值设置到新的simpleMap中</li>
     * <li>否则使用{@link String#valueOf(Object)}转换到simpleMap中</li>
     * </ul>
     * </blockquote>.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param inputMap
     *            the input map
     * @param allowFormatClassTypes
     *            除了基本类型,数组之外允许的类型,请确保该类型可以被json format输出
     * @return 如果 <code>inputMap</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @since 1.3.0
     */
    public static <K, V> String formatSimpleMap(Map<K, V> inputMap,Class<?>...allowFormatClassTypes){
        if (null == inputMap){
            return EMPTY;
        }
        Map<K, Object> simpleMap = new TreeMap<>();
        for (Map.Entry<K, V> entry : inputMap.entrySet()){
            V value = entry.getValue();
            simpleMap.put(entry.getKey(), JsonHelper.isAllowFormatType(value, allowFormatClassTypes) ? value : String.valueOf(value)); //注意 String.valueOf(value)如果value是null 那么会输出 "null"字符串
        }
        return format(simpleMap);
    }

    /**
     * 将对象格式化 成json字符串(<b>排除</b>指定名称的属性 <code>excludes</code>),并且 toString(4, 4) 输出.
     * 
     * <p>
     * 和该方法对应的有,仅仅<b>包含</b>某些属性,see {@link #formatWithIncludes(Object, String...)}
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * User user = new User();
     * 
     * user.setPassword("123456");
     * user.setId(8L);
     * user.setName("feilong");
     * user.setDate(new Date());
     * user.setMoney(toBigDecimal("99999999.00"));
     * 
     * user.setLoves(toArray("桔子", "香蕉"));
     * user.setUserInfo(new UserInfo(10));
     * 
     * UserAddress userAddress1 = new UserAddress("上海市闸北区万荣路1188号H座109-118室");
     * UserAddress userAddress2 = new UserAddress("上海市闸北区阳城路280弄25号802室(阳城贵都)");
     * 
     * user.setUserAddresses(toArray(userAddress1, userAddress2));
     * user.setUserAddresseList(toList(userAddress1, userAddress2));
     * 
     * LOGGER.debug(JsonUtil.format(USER, toArray("name", "loves", "attrMap", "userInfo", "userAddresses")));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     *  {
        "userAddresseList":         [
            {"address": "上海市闸北区万荣路1188号H座109-118室"},
            {"address": "上海市闸北区阳城路280弄25号802室(阳城贵都)"}
        ],
        "date": "2016-07-17 16:04:35",
        "password": "******",
        "id": 8,
        "age": 0,
        "money": 99999999,
        "nickNames": []
    }
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
     * @param excludes
     *            排除需要序列化成json的属性,如果 excludes isNotNullOrEmpty,那么不会setExcludes
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see <a href="http://feitianbenyue.iteye.com/blog/2046877">java.lang.ClassCastException: JSON keys must be strings</a>
     */
    public static String format(Object obj,String[] excludes){
        return format(obj, excludes, 4, 4);
    }

    /**
     * 将对象格式化 成json字符串(<b>排除</b>指定名称的属性 <code>excludes</code>),并且按照指定的缩进(<code>indentFactor</code>和 <code>indent</code>) 输出.
     * 
     * <p>
     * 和该方法对应的有,仅仅<b>包含</b>某些属性,see {@link #formatWithIncludes(Object, String...)}
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * User user = new User();
     * 
     * user.setPassword("123456");
     * user.setId(8L);
     * user.setName("feilong");
     * user.setDate(new Date());
     * user.setMoney(toBigDecimal("99999999.00"));
     * 
     * user.setLoves(toArray("桔子", "香蕉"));
     * user.setUserInfo(new UserInfo(10));
     * 
     * UserAddress userAddress1 = new UserAddress("上海市闸北区万荣路1188号H座109-118室");
     * UserAddress userAddress2 = new UserAddress("上海市闸北区阳城路280弄25号802室(阳城贵都)");
     * 
     * user.setUserAddresses(toArray(userAddress1, userAddress2));
     * user.setUserAddresseList(toList(userAddress1, userAddress2));
     * 
     * LOGGER.debug(JsonUtil.format(USER, toArray("name", "loves", "attrMap", "userInfo", "userAddresses"), 0, 0));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {"userAddresseList":[{"address":"上海市闸北区万荣路1188号H座109-118室"},{"address":"上海市闸北区阳城路280弄25号802室(阳城贵都)"}],"date":"2016-07-17 16:05:34","password":"******","id":8,"age":0,"money":99999999,"nickNames":[]}
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
     * @param excludes
     *            排除需要序列化成json的属性,如果 excludes isNotNullOrEmpty,那么不会setExcludes
     * @param indentFactor
     *            the indent factor
     * @param indent
     *            the indent
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see #format(Object, JavaToJsonConfig)
     * @see JsonHelper#toJavaToJsonConfig(String[], String[])
     */
    public static String format(Object obj,String[] excludes,int indentFactor,int indent){
        return null == obj ? EMPTY : format(obj, JsonHelper.toJavaToJsonConfig(excludes, null), indentFactor, indent);
    }

    /**
     * 将对象格式化成json字符串(<b>仅仅包含</b>指定名称的属性 <code>includes</code>).
     * 
     * <p>
     * 和该方法对应的有,仅仅<b>排除</b>某些属性,see {@link #format(Object, String[])}以及 {@link #format(Object, String[], int, int)}
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * User user1 = new User("feilong1", 24);
     * user1.setNickNames(toArray("xin.jin", "shuai.ge"));
     * User user2 = new User("feilong2", 240);
     * user2.setNickNames(toArray("xin.jin", "shuai.ge"));
     * 
     * List{@code <User>} list = toList(user1, user2);
     * 
     * LOGGER.debug(JsonUtil.formatWithIncludes(list, "name", "age"));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    [{
                "age": 24,
                "name": "feilong1"
            },
                    {
                "age": 240,
                "name": "feilong2"
            }
        ]
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
     * @param includes
     *            the includes
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see #format(Object, JavaToJsonConfig)
     * @see JsonHelper#toJavaToJsonConfig(String[], String[])
     * @since 1.0.8
     */
    public static String formatWithIncludes(Object obj,final String...includes){
        return null == obj ? EMPTY : format(obj, JsonHelper.toJavaToJsonConfig(null, includes));
    }

    /**
     * 将<code>obj</code>格式化成json字符串,并且按照指定的缩进(<code>indentFactor</code>和 <code>indent</code>) 输出.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * //查询{@code  >10} 的元素
     * Predicate{@code <Integer>} predicate = new ComparatorPredicate{@code <Integer>}(10, ComparatorUtils.{@code <Integer>} naturalComparator(), Criterion.LESS);
     * 
     * List{@code <Integer>} result = CollectionsUtil.select(toList(1, 5, 10, 30, 55, 88, 1, 12, 3), predicate);
     * LOGGER.debug(JsonUtil.format(result, 0, 0));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [30,55,88,12]
     * </pre>
     * 
     * <hr>
     * 
     * <pre class="code">
     * LOGGER.debug(JsonUtil.format(result, 4, 4));// = LOGGER.debug(JsonUtil.format(result))
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * [
     * 30,
     * 55,
     * 88,
     * 12
     * ]
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
     * @param indentFactor
     *            the indent factor
     * @param indent
     *            the indent
     * @return the string
     * @since 1.2.2
     */
    public static String format(Object obj,int indentFactor,int indent){
        return format(obj, (JsonConfig) null, indentFactor, indent);
    }

    /**
     * 使用配置 <code>JavaToJsonConfig</code> 来格式化成json字符串.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * User user = new User("feilong1", 24);
     * user.setPassword("123456");
     * user.setMoney(toBigDecimal("99999999.00"));
     * 
     * Map{@code <String, JsonValueProcessor>} propertyNameAndJsonValueProcessorMap = new HashMap{@code <>}();
     * propertyNameAndJsonValueProcessorMap.put("password", new SensitiveWordsJsonValueProcessor());
     * propertyNameAndJsonValueProcessorMap.put("money", new BigDecimalJsonValueProcessor());
     * 
     * JavaToJsonConfig javaToJsonConfig = new JavaToJsonConfig();
     * javaToJsonConfig.setPropertyNameAndJsonValueProcessorMap(propertyNameAndJsonValueProcessorMap);
     * 
     * LOGGER.debug(JsonUtil.format(user, javaToJsonConfig));
     * 
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "userAddresseList": [],
     * "userAddresses": [],
     * "date": null,
     * "password": "******",
     * "id": 0,
     * "age": 24,
     * "name": "feilong1",
     * "money": "99999999.00",
     * "attrMap": null,
     * "userInfo": {"age": 0},
     * "nickNames": [],
     * "loves": []
     * }
     * </pre>
     * 
     * <p>
     * 当然上述示例中,<code>password</code>属性默认就是由 {@link SensitiveWordsJsonValueProcessor} 来处理
     * </p>
     * 
     * </blockquote>
     *
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
     * @param javaToJsonConfig
     *            the json format config
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @since 1.2.2
     */
    public static String format(Object obj,JavaToJsonConfig javaToJsonConfig){
        return format(obj, javaToJsonConfig, 4, 4);
    }

    /**
     * Format.
     *
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
     * @param javaToJsonConfig
     *            the json format config
     * @param indentFactor
     *            the indent factor
     * @param indent
     *            the indent
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @since 1.2.2
     */
    public static String format(Object obj,JavaToJsonConfig javaToJsonConfig,int indentFactor,int indent){
        if (null == javaToJsonConfig){
            return format(obj, (JsonConfig) null, indentFactor, indent);
        }
        JsonConfig jsonConfig = JsonHelper.buildDefaultJavaToJsonConfig();

        //****************************************************************************************

        //property name处理器
        Map<Class<?>, PropertyNameProcessor> targetClassAndPropertyNameProcessorMap = javaToJsonConfig
                        .getJsonTargetClassAndPropertyNameProcessorMap();
        if (isNotNullOrEmpty(targetClassAndPropertyNameProcessorMap)){
            for (Map.Entry<Class<?>, PropertyNameProcessor> entry : targetClassAndPropertyNameProcessorMap.entrySet()){
                jsonConfig.registerJsonPropertyNameProcessor(entry.getKey(), entry.getValue());
            }
        }

        //****************************************************************************************
        //value处理器
        Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap = javaToJsonConfig.getPropertyNameAndJsonValueProcessorMap();
        if (isNotNullOrEmpty(propertyNameAndJsonValueProcessorMap)){
            for (Map.Entry<String, JsonValueProcessor> entry : propertyNameAndJsonValueProcessorMap.entrySet()){
                jsonConfig.registerJsonValueProcessor(entry.getKey(), entry.getValue());
            }
        }

        //****************************************************************************************

        //排除
        if (isNotNullOrEmpty(javaToJsonConfig.getExcludes())){
            jsonConfig.setExcludes(javaToJsonConfig.getExcludes());
        }

        //包含
        if (isNotNullOrEmpty(javaToJsonConfig.getIncludes())){
            jsonConfig.setJsonPropertyFilter(new ArrayContainsPropertyNamesPropertyFilter(javaToJsonConfig.getIncludes()));
        }

        return format(obj, jsonConfig, indentFactor, indent);
    }

    /**
     * 格式化一个对象 <code>obj</code> 里面所有的field 的名字和值.
     * 
     * <h3>代码流程:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>如果field上 标识了 {@link SensitiveWords}注解,那么会使用 {@link SensitiveWordsJsonValueProcessor}混淆敏感数据的输出</li>
     * </ol>
     * </blockquote>
     * 
     * @param obj
     *            可以是Java bean
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     *         否则取到该对象 所有field 的name 和value值 map {@link FieldUtil#getAllFieldNameAndValueMap(Object, String...)} 调用
     *         {@link #format(Object, JavaToJsonConfig)},再次过程中,会处理 {@link SensitiveWords}
     * @see FieldUtil#getAllFieldNameAndValueMap(Object, String...)
     * @see org.apache.commons.lang3.reflect.FieldUtils#getFieldsListWithAnnotation(Class, Class)
     * @since 1.5.6
     */
    public static String formatObjectFieldsNameAndValueMap(Object obj){
        return null == obj ? EMPTY : format(FieldUtil.getAllFieldNameAndValueMap(obj), JsonHelper.buildJavaBeanToJsonConfig(obj));
    }

    /**
     * Make a prettyprinted JSON text.
     * 
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     * </p>
     *
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
     * @param jsonConfig
     *            the json config
     * @param indentFactor
     *            The number of spaces to add to each level of indentation.
     * @param indent
     *            The indentation of the top level.
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     *         a printable,displayable,transmittable representation of the object,<br>
     *         beginning with{ (left brace) and ending with }(right brace).
     * @since 1.0.8
     */
    private static String format(Object obj,JsonConfig jsonConfig,int indentFactor,int indent){
        return null == obj ? EMPTY : JsonHelper.toJSON(obj, jsonConfig).toString(indentFactor, indent);
    }

    // [end]

    // *****************************Array******************************************************
    // [start]toArray

    /**
     * 把一个json数组串转换成实体数组,且数组元素的属性含有另外实例Bean.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
     * Map{@code <String, Class<?>>} classMap = new HashMap{@code <String, Class<?>>}();
     * classMap.put("data", Person.class);
     * 
     * MyBean[] objArr = JsonUtil.toArray(json, MyBean.class, classMap);
     * LOGGER.info(JsonUtil.format(objArr));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     *        [{
     *                    "id": 0,
     *                    "data": [            {
     *                        "dateAttr": null,
     *                        "name": "get"
     *                    }]
     *          },{
     *                    "id": 0,
     *                    "data": [            {
     *                        "dateAttr": null,
     *                        "name": "set"
     *                   }]
     *        }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param jsonToJavaConfig
     *            the json to java config
     * @return T[]
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see net.sf.json.JSONArray#getJSONObject(int)
     * @see #toBean(Object, JsonToJavaConfig)
     * @see java.lang.reflect.Array#newInstance(Class, int)
     * @since 1.9.4
     */
    public static <T> T[] toArray(Object json,JsonToJavaConfig jsonToJavaConfig){
        JSONArray jsonArray = JsonHelper.toJSONArray(json, null);

        int size = jsonArray.size();
        @SuppressWarnings("unchecked")
        T[] t = (T[]) ArrayUtil.newArray(jsonToJavaConfig.getRootClass(), size);
        for (int i = 0; i < size; i++){
            t[i] = toBean(jsonArray.getJSONObject(i), jsonToJavaConfig);
        }
        return t;
    }

    // [end]

    // *****************************List********************************************************
    // [start]toList
    /**
     * 把一个json数组串转换成集合,且集合里存放的为实例Bean.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "[{'name':'get'},{'name':'set'}]";
     * List{@code <Person>} list = JsonUtil.toList(json, Person.class);
     * 
     * LOGGER.info(JsonUtil.format(list));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
       [{
               "dateAttr": null,
               "name": "get"
           },
                   {
               "dateAttr": null,
               "name": "set"
           }
       ]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'name':'get'},{'name':'set'}]
     * @param rootClass
     *            the klass,see {@link net.sf.json.JsonConfig#setRootClass(Class)}
     * @return List
     * @see #toList(Object, JsonToJavaConfig)
     */
    public static <T> List<T> toList(Object json,Class<T> rootClass){
        JsonToJavaConfig jsonToJavaConfig = new JsonToJavaConfig(rootClass);
        return toList(json, jsonToJavaConfig);
    }

    /**
     * 把一个json数组串转换成集合,且集合里的对象的属性含有另外实例Bean.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
     * Map{@code <String, Class<?>>} classMap = new HashMap{@code <>}();
     * classMap.put("data", Person.class);
     * 
     * List{@code <MyBean>} list = JsonUtil.toList(json, MyBean.class, classMap);
     * 
     * LOGGER.debug(JsonUtil.format(list));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     *      [{
     *              "id": 0,
     *              "data": [            {
     *                  "dateAttr": null,
     *                  "name": "get"
     *              }]
     *          },
     *                  {
     *              "id": 0,
     *              "data": [            {
     *                  "dateAttr": null,
     *                  "name": "set"
     *              }]
     *       }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param jsonToJavaConfig
     *            the json to java config
     * @return List
     * @see net.sf.json.JSONArray#getJSONObject(int)
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see #toBean(Object, JsonToJavaConfig)
     */
    public static <T> List<T> toList(Object json,JsonToJavaConfig jsonToJavaConfig){
        JSONArray jsonArray = JsonHelper.toJSONArray(json, null);
        List<T> list = new ArrayList<>();
        for (int i = 0, j = jsonArray.size(); i < j; i++){
            list.add(JsonUtil.<T> toBean(jsonArray.getJSONObject(i), jsonToJavaConfig));
        }
        return list;
    }

    // [end]

    // ********************************Map******************************************************

    // [start]toMap

    /**
     * 将简单的json字符串转成<code>map</code>.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "{'brandCode':'UA'}";
     * Map{@code <String, Object>} map = JsonUtil.toMap(json);
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * key是 brandCode,value 是 UA 的map
     * </pre>
     * 
     * <hr>
     * 
     * <pre class="code">
     * Map{@code <String, Integer>} map = JsonUtil.toMap("{'brandCode':55555}");
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {"brandCode": 55555}
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 
     * <ol>
     * <li>返回的map是 {@link LinkedHashMap}</li>
     * 
     * <li>
     * <p>
     * 由于泛型 unchecked,所以可能返回的结果,泛型里面有其他类型的值
     * </p>
     * 
     * <pre class="code">
     * Map{@code <String, Long>} map = JsonUtil.toMap("{'brandCode':55.555}");
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {"brandCode": 55.555}
     * </pre>
     * 
     * </li>
     * 
     * </ol>
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            the json
     * @return 如果 <code>json</code> 是null或者empty,返回 {@link Collections#emptyMap()}
     * @see #toMap(Object, JsonToJavaConfig)
     * @since 1.5.0
     */
    public static <T> Map<String, T> toMap(Object json){
        return toMap(json, null);
    }

    /**
     * 把json对象串转换成map对象,map对象里可以存放的其他实体Bean还含有另外实体Bean.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * 比如有 Person.class,代码如下
     * 
     * <pre class="code">
     * public class Person{
     * 
     *     private String name;
     * 
     *     private Date dateAttr;
     * 
     *     // setter /getter 略
     * }
     * </pre>
     * 
     * 此时,
     * 
     * <pre class="code">
     * String json = "{'data1':{'name':'get'},'data2':{'name':'set'}}";
     * Map<String, Person> map = JsonUtil.toMap(json, new JsonToJavaConfig(Person.class));
     * 
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
    {
        "data1":         {
            "dateAttr": null,
            "name": "get"
        },
        "data2":         {
            "dateAttr": null,
            "name": "set"
        }
    }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'data1':{'name':'get'},'data2':{'name':'set'}}
     * @param jsonToJavaConfig
     *            the json to java config
     * @return 如果 <code>json</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     *         如果 <code>rootClass</code> 是null,那么直接将json里面的value 作为map 的value
     * @see net.sf.json.JSONObject#keys()
     * @see #toBean(Object, JsonToJavaConfig)
     * @since 1.9.2 use LinkedHashMap instead of HashMap
     * @since 1.9.4
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> toMap(Object json,JsonToJavaConfig jsonToJavaConfig){
        LOGGER.trace("input json:[{}],jsonToJavaConfig:[{}]", json, jsonToJavaConfig);

        if (isNullOrEmpty(json)){
            return emptyMap();
        }

        Map<String, T> map = new LinkedHashMap<>();

        JSONObject jsonObject = JsonHelper.toJSONObject(json, null);
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()){
            String key = keys.next();
            Object value = jsonObject.get(key);
            LOGGER.trace("key:[{}],value:[{}],value type is:[{}]", key, value, value.getClass().getName());

            //如果rootClass是null,表示不需要转换
            map.put(key, JsonHelper.<T> transformerValue(value, jsonToJavaConfig));
        }
        return map;
    }

    // [end]

    //***********************************************************************************
    // [start]toBean

    /**
     * json串,转换成实体对象.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * 比如有 Person.class,代码如下
     * 
     * <pre class="code">
     * public class Person{
     * 
     *     private String name;
     * 
     *     private Date dateAttr;
     * 
     *     // setter /getter 略
     * }
     * </pre>
     * 
     * 此时,
     * 
     * <pre class="code">
     * String json = "{'name':'get','dateAttr':'2009-11-12'}";
     * LOGGER.debug(JsonUtil.format(JsonUtil.toBean(json, Person.class)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "dateAttr": "2009-11-12 00:00:00",
     * "name": "get"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'name':'get','dateAttr':'2009-11-12'}<br>
     *            可以是 json字符串,也可以是JSONObject<br>
     *            Accepts JSON formatted strings, Maps, DynaBeans and JavaBeans. <br>
     *            支持的格式有: {@link JSONObject#fromObject(Object, JsonConfig)}
     * @param rootClass
     *            e.g. Person.class,see {@link net.sf.json.JsonConfig#setRootClass(Class)}
     * @return 如果<code>json</code> 是null,那么返回 null <br>
     *         如果 <code>rootClass</code> 是null,抛出 {@link NullPointerException}<br>
     * @see JSONObject#fromObject(Object, JsonConfig)
     * @see net.sf.json.JsonConfig#setRootClass(Class)
     * @see #toBean(Object, JsonToJavaConfig)
     */
    public static <T> T toBean(Object json,Class<T> rootClass){
        if (null == json){
            return null;
        }
        Validate.notNull(rootClass, "rootClass can't be null!");

        JsonToJavaConfig jsonToJavaConfig = new JsonToJavaConfig();
        jsonToJavaConfig.setRootClass(rootClass);
        return toBean(json, jsonToJavaConfig);
    }

    /**
     * 从json串转换成 javabean 实体对象,支持实体集合属性可能存有另外实体Bean.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * 如果有以下的bean:
     * 
     * <pre class="code">
     * public class MyBean{
     * 
     *     private Long id;
     * 
     *     private List{@code <Object>} data = new ArrayList{@code <>}();
     *     //setter /getter 略
     * }
     * </pre>
     * 
     * <pre class="code">
     * public class Person{
     * 
     *     private String name;
     * 
     *     private Date dateAttr;
     *     //setter /getter 略
     * }
     * </pre>
     * 
     * 使用以下的代码:
     * 
     * <pre class="code">
     * String json = "{'data':[{'name':'get'},{'name':'set'}],'id':5}";
     * Map{@code <String, Class<?>>} classMap = new HashMap{@code <>}();
     * classMap.put("data", Person.class);
     * 
     * JsonToJavaConfig jsonToJavaConfig = new JsonToJavaConfig(MyBean.class);
     * jsonToJavaConfig.setClassMap(classMap);
     * 
     * MyBean myBean = JsonUtil.toBean(json, jsonToJavaConfig);
     * LOGGER.debug(JsonUtil.format(myBean));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     *              "id": 5,
     *              "data":[{
     *                      "dateAttr": null,
     *                      "name": "get"
     *                  },{
     *                      "dateAttr": null,
     *                      "name": "set"
     *                  }
     *              ]
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'data':[{'name':'get'},{'name':'set'}]}
     * @param jsonToJavaConfig
     *            the json to java config
     * @return 如果<code>json</code> 是null,那么返回 null<br>
     *         如果 <code>jsonToJavaConfig</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>jsonToJavaConfig.getRootClass()</code> 是null,抛出 {@link NullPointerException}<br>
     * @see JSONObject#fromObject(Object, JsonConfig)
     * @see net.sf.json.JsonConfig#setRootClass(Class)
     * @since 1.9.4
     */
    @SuppressWarnings("unchecked")
    public static <T> T toBean(Object json,JsonToJavaConfig jsonToJavaConfig){
        if (null == json){
            return null;
        }
        Validate.notNull(jsonToJavaConfig, "jsonToJavaConfig can't be null!");

        Class<?> rootClass = jsonToJavaConfig.getRootClass();
        Validate.notNull(rootClass, "rootClass can't be null!");

        //**********************************************************************************

        JSONObject jsonObject = JSONObject.fromObject(json);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setRootClass(rootClass);

        //--------------JavaIdentifierTransformer-------------------------------
        JavaIdentifierTransformer javaIdentifierTransformer = jsonToJavaConfig.getJavaIdentifierTransformer();
        if (isNotNullOrEmpty(javaIdentifierTransformer)){
            jsonConfig.setJavaIdentifierTransformer(javaIdentifierTransformer);
        }

        //--------------setClassMap-------------------------------
        //Sets the current attribute/Class Map [JSON -> Java]
        //classMap a Map of classes, every key identifies a property or a regexp
        Map<String, Class<?>> classMap = jsonToJavaConfig.getClassMap();
        if (isNotNullOrEmpty(classMap)){
            jsonConfig.setClassMap(classMap);
        }

        // Ignore missing properties with Json-Lib

        // 避免出现 Unknown property 'orderIdAndCodeMap' on class 'class
        // com.baozun.trade.web.controller.payment.result.command.PaymentResultEntity' 异常
        jsonConfig.setPropertySetStrategy(new PropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
        return (T) JSONObject.toBean(jsonObject, jsonConfig);
    }

    // [end]
}