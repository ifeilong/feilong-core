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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.ArrayUtil;
import com.feilong.core.lang.ClassUtil;
import com.feilong.core.lang.ObjectUtil;
import com.feilong.core.lang.reflect.FieldUtil;
import com.feilong.tools.jsonlib.filters.ArrayContainsPropertyNamesPropertyFilter;
import com.feilong.tools.jsonlib.processor.DateJsonValueProcessor;
import com.feilong.tools.jsonlib.processor.SensitiveWordsJsonValueProcessor;
import com.feilong.tools.jsonlib.util.PropertyStrategyWrapper;

import static com.feilong.core.Validator.isNotNullOrEmpty;
import static com.feilong.core.Validator.isNullOrEmpty;

import static com.feilong.core.DatePattern.COMMON_DATE;
import static com.feilong.core.DatePattern.COMMON_DATE_AND_TIME;
import static com.feilong.core.DatePattern.COMMON_TIME;

import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertySetStrategy;

/**
 * json处理工具类.
 * 
 * <h3>提供以下主要方法:</h3>
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4" summary="">
 * <tr style="background-color:#ccccff">
 * <th align="left">方法:</th>
 * <th align="left">说明:</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link #format(Object)}</td>
 * <td>将对象格式化成json字符串</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link #toJSON(Object)}</td>
 * <td>把实体Bean、Map对象、数组、列表集合转换成Json串.</td>
 * </tr>
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
    private static final Logger                           LOGGER                              = LoggerFactory.getLogger(JsonUtil.class);

    /** The Constant DEFAULT_JSON_CONFIG. */
    private static final JsonConfig                       DEFAULT_JSON_CONFIG;

    //***********************************************************************************
    /** The Constant SENSITIVE_WORDS_JSONVALUE_PROCESSOR. */
    private static final SensitiveWordsJsonValueProcessor SENSITIVE_WORDS_JSONVALUE_PROCESSOR = new SensitiveWordsJsonValueProcessor();

    /** The Constant SENSITIVE_WORDS_PROPERTY_NAMES. */
    private static final String[]                         SENSITIVE_WORDS_PROPERTY_NAMES      = { "password", "key" };

    /** Don't let anyone instantiate this class. */
    private JsonUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //***********************************************************************************
    /**
     * 设置日期转换格式.
     */
    static{
        // 注册器
        MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
        // 可转换的日期格式,即Json串中可以出现以下格式的日期与时间
        morpherRegistry.registerMorpher(new DateMorpher(ConvertUtil.toArray(COMMON_DATE_AND_TIME, COMMON_TIME, COMMON_DATE)));

        DEFAULT_JSON_CONFIG = getDefaultJsonConfig();
    }

    //***************************format********************************************************

    // [start] format

    /**
     * 将对象 <code>obj</code> 格式化成json字符串.
     * 
     * <p>
     * 该方法还可以格式化json字符串成缩进形式的格式
     * </p>
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
     * {@link #format(Object, JsonFormatConfig, int, int)}
     * </p>
     * </blockquote>
     * 
     * @param obj
     *            任何对象
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see #format(Object, JsonFormatConfig)
     */
    public static String format(Object obj){
        return format(obj, (JsonFormatConfig) null);
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
        return null == inputMap ? StringUtils.EMPTY : formatSimpleMap(inputMap, (Class<?>) null);
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
            return StringUtils.EMPTY;
        }
        Map<K, Object> simpleMap = new TreeMap<K, Object>();
        for (Map.Entry<K, V> entry : inputMap.entrySet()){
            V value = entry.getValue();
            simpleMap.put(entry.getKey(), isAllowFormatType(value, allowFormatClassTypes) ? value : String.valueOf(value)); //注意 String.valueOf(value)如果value是null 那么会输出 "null"字符串
        }
        return format(simpleMap);
    }

    /**
     * 格式化输出,将对象转成toJSON,并且 toString(4, 4) 输出.
     *
     * @param obj
     *            对象
     * @param excludes
     *            排除需要序列化成json的属性,如果 excludes isNotNullOrEmpty,那么不会setExcludes
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see <a href="http://feitianbenyue.iteye.com/blog/2046877">java.lang.ClassCastException: JSON keys must be strings</a>
     */
    public static String format(Object obj,String[] excludes){
        return format(obj, excludes, 4, 4);
    }

    /**
     * Format.
     *
     * @param obj
     *            the obj
     * @param excludes
     *            the excludes
     * @param indentFactor
     *            the indent factor
     * @param indent
     *            the indent
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see #format(Object, JsonFormatConfig)
     * @see #buildJsonFormatConfig(String[], String[])
     */
    public static String format(Object obj,String[] excludes,Integer indentFactor,Integer indent){
        return null == obj ? StringUtils.EMPTY : format(obj, buildJsonFormatConfig(excludes, null), indentFactor, indent);
    }

    /**
     * 只包含这些key才被format出json格式.
     *
     * @param obj
     *            the obj
     * @param includes
     *            the includes
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @see #format(Object, JsonFormatConfig)
     * @see #buildJsonFormatConfig(String[], String[])
     * @since 1.0.8
     */
    public static String formatWithIncludes(Object obj,final String...includes){
        return null == obj ? StringUtils.EMPTY : format(obj, buildJsonFormatConfig(null, includes));
    }

    /**
     * Builds the json format config.
     *
     * @param excludes
     *            the excludes
     * @param includes
     *            the includes
     * @return the json format config
     * @since 1.6.3
     */
    private static JsonFormatConfig buildJsonFormatConfig(String[] excludes,String[] includes){
        boolean noNeedBuild = isNullOrEmpty(excludes) && isNullOrEmpty(includes);
        return noNeedBuild ? null : new JsonFormatConfig(excludes, includes);
    }

    /**
     * Format.
     *
     * @param obj
     *            the obj
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
     * Format.
     *
     * @param obj
     *            the obj
     * @param jsonFormatConfig
     *            the json format config
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @since 1.2.2
     */
    public static String format(Object obj,JsonFormatConfig jsonFormatConfig){
        return format(obj, jsonFormatConfig, 4, 4);
    }

    /**
     * Format.
     *
     * @param obj
     *            the obj
     * @param jsonFormatConfig
     *            the json format config
     * @param indentFactor
     *            the indent factor
     * @param indent
     *            the indent
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     * @since 1.2.2
     */
    public static String format(Object obj,JsonFormatConfig jsonFormatConfig,int indentFactor,int indent){
        if (null == jsonFormatConfig){
            return format(obj, (JsonConfig) null, indentFactor, indent);
        }
        JsonConfig jsonConfig = getDefaultJsonConfig();

        //value处理器
        Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap = jsonFormatConfig.getPropertyNameAndJsonValueProcessorMap();
        if (isNotNullOrEmpty(propertyNameAndJsonValueProcessorMap)){
            for (Map.Entry<String, JsonValueProcessor> entry : propertyNameAndJsonValueProcessorMap.entrySet()){
                jsonConfig.registerJsonValueProcessor(entry.getKey(), entry.getValue());
            }
        }
        //排除
        if (isNotNullOrEmpty(jsonFormatConfig.getExcludes())){
            jsonConfig.setExcludes(jsonFormatConfig.getExcludes());
        }
        //包含
        if (isNotNullOrEmpty(jsonFormatConfig.getIncludes())){
            jsonConfig.setJsonPropertyFilter(new ArrayContainsPropertyNamesPropertyFilter(jsonFormatConfig.getIncludes()));
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
     *            the obj
     * @return 如果 <code>obj</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     *         否则取到该对象 所有field 的name 和value值 map {@link FieldUtil#getAllFieldNameAndValueMap(Object, String...)} 调用
     *         {@link #format(Object, JsonFormatConfig)},再次过程中,会处理 {@link SensitiveWords}
     * @see FieldUtil#getAllFieldNameAndValueMap(Object, String...)
     * @see org.apache.commons.lang3.reflect.FieldUtils#getFieldsListWithAnnotation(Class, Class)
     * @since 1.5.6
     */
    public static String formatObjectFieldsNameAndValueMap(Object obj){
        return null == obj ? StringUtils.EMPTY : format(FieldUtil.getAllFieldNameAndValueMap(obj), buildJsonFormatConfig(obj));
    }

    /**
     * Builds the json format config.
     *
     * @param obj
     *            the obj
     * @return the json format config
     * @since 1.6.3
     */
    private static JsonFormatConfig buildJsonFormatConfig(Object obj){
        List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(obj.getClass(), SensitiveWords.class);
        if (isNotNullOrEmpty(fieldsListWithAnnotation)){
            Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap = new HashMap<String, JsonValueProcessor>();
            for (Field field : fieldsListWithAnnotation){
                propertyNameAndJsonValueProcessorMap.put(field.getName(), SENSITIVE_WORDS_JSONVALUE_PROCESSOR);
            }
            return new JsonFormatConfig(propertyNameAndJsonValueProcessorMap);
        }
        return null;
    }

    /**
     * Make a prettyprinted JSON text.
     * 
     * <p>
     * Warning: This method assumes that the data structure is acyclical.
     * </p>
     *
     * @param obj
     *            the obj
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
        return null == obj ? StringUtils.EMPTY : toJSON(obj, jsonConfig).toString(indentFactor, indent);
    }

    // [end]

    // [start]toJSON

    /**
     * 把实体Bean、Map对象、数组、列表集合转换成Json串.
     *
     * @param obj
     *            the obj
     * @return the jSON
     * @see #toJSON(Object, JsonConfig)
     */
    static JSON toJSON(Object obj){
        return toJSON(obj, null);
    }

    /**
     * 把实体Bean、Map对象、数组、列表集合转换成Json.
     * 
     * <p>
     * 如果 <code>null==jsonConfig</code>,将使用 {@link #DEFAULT_JSON_CONFIG}
     * </p>
     *
     * @param obj
     *            the obj
     * @param jsonConfig
     *            the json config
     * @return the jSON
     * @see net.sf.json.JSONArray#fromObject(Object, JsonConfig)
     * @see net.sf.json.JSONObject#fromObject(Object, JsonConfig)
     * @see net.sf.json.util.JSONUtils#isArray(Object)
     * @see java.lang.Class#isEnum()
     * @see net.sf.json.JsonConfig#registerJsonValueProcessor(Class, JsonValueProcessor)
     * @see org.apache.commons.collections4.IteratorUtils#toList(Iterator)
     * @see org.apache.commons.collections4.IteratorUtils#toList(Iterator, int)
     * @see net.sf.json.JSONSerializer#toJSON(Object)
     */
    static JSON toJSON(Object obj,JsonConfig jsonConfig){
        JsonConfig useJsonConfig = ObjectUtils.defaultIfNull(jsonConfig, DEFAULT_JSON_CONFIG);
        registerDefaultJsonValueProcessor(useJsonConfig);

        if (isNeedConvertToJSONArray(obj)){
            Object arrayJsonObject = obj instanceof Iterator ? IteratorUtils.toList((Iterator<?>) obj) : obj;
            return toJSONArray(arrayJsonObject, useJsonConfig);
        }
        return toJSONObject(obj, useJsonConfig);
    }

    /**
     * 是否需要转成 JSONArray类型.
     *
     * @param obj
     *            the obj
     * @return true, if is array
     * @see net.sf.json.JSONArray#_fromJSONTokener(net.sf.json.util.JSONTokener, JsonConfig)
     * @since 1.7.2
     */
    private static boolean isNeedConvertToJSONArray(Object obj){
        if (obj instanceof String){
            String str = (String) obj;
            if (str.startsWith("[") && str.endsWith("]")){// [] 格式的字符串 
                return true;
            }
        }
        return JSONUtils.isArray(obj) || //obj.getClass().isArray() || obj instanceof Collection || obj instanceof Object[]
                        obj instanceof Enum || // obj.getClass().isEnum()这么写 null会报错// object' is an Enum. Use JSONArray instead
                        obj instanceof Iterator;
    }

    // [end]

    /**
     * 默认的处理器.
     *
     * @param jsonConfig
     *            the json config
     * @since 1.5.3
     */
    private static void registerDefaultJsonValueProcessor(JsonConfig jsonConfig){
        for (String propertyName : SENSITIVE_WORDS_PROPERTY_NAMES){
            jsonConfig.registerJsonValueProcessor(propertyName, SENSITIVE_WORDS_JSONVALUE_PROCESSOR);
        }
    }

    //********************************************************************************************

    /**
     * To json array.
     *
     * @param json
     *            the json
     * @return the JSON array
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @since 1.4.0
     */
    private static JSONArray toJSONArray(String json){
        return toJSONArray(json, new JsonConfig());
    }

    /**
     * To json array.
     *
     * @param obj
     *            Accepts JSON formatted strings, arrays, Collections and Enums.
     * @param useJsonConfig
     *            the use json config
     * @return the JSON array
     * @see net.sf.json.JSONArray#fromObject(Object, JsonConfig)
     * @since 1.4.0
     */
    private static JSONArray toJSONArray(Object obj,JsonConfig useJsonConfig){
        return JSONArray.fromObject(obj, useJsonConfig);
    }

    //**************toJSONObject********************

    /**
     * To json object.
     *
     * @param json
     *            the json
     * @return the JSON object
     * @see net.sf.json.JSONObject#fromObject(Object)
     * @since 1.4.0
     */
    private static JSONObject toJSONObject(String json){
        return toJSONObject(json, new JsonConfig());
    }

    /**
     * To json object.
     *
     * @param obj
     *            Accepts JSON formatted strings, Maps, DynaBeans and JavaBeans
     * @param useJsonConfig
     *            the use json config
     * @return the JSON object
     * @see net.sf.json.JSONObject#fromObject(Object, JsonConfig)
     * @since 1.4.0
     */
    private static JSONObject toJSONObject(Object obj,JsonConfig useJsonConfig){
        return JSONObject.fromObject(obj, useJsonConfig);
    }

    // *****************************Array******************************************************
    // [start]toArray

    /**
     * 把一个json数组串,转换成实体数组.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "[{'name':'get'},{'name':'set'}]";
     * Person[] objArr = JsonUtil.toArray(json, Person.class);
     * 
     * LOGGER.info(JsonUtil.format(objArr));
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
        }]
     * 
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'name':'get'},{'name':'set'}]
     * @param rootClass
     *            e.g. Person.class,see {@link net.sf.json.JsonConfig#setRootClass(Class)}
     * @return Object[]
     * @see #toArray(String, Class, Map)
     */
    public static <T> T[] toArray(String json,Class<T> rootClass){
        return toArray(json, rootClass, null);
    }

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
       [{
                   "id": 0,
                   "data": [            {
                       "dateAttr": null,
                       "name": "get"
                   }]
         },{
                   "id": 0,
                   "data": [            {
                       "dateAttr": null,
                       "name": "set"
                  }]
       }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param rootClass
     *            e.g. MyBean.class,see {@link net.sf.json.JsonConfig#setRootClass(Class)}
     * @param classMap
     *            e.g. classMap.put("data", Person.class)
     * @return T[]
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see net.sf.json.JSONArray#getJSONObject(int)
     * @see #toBean(Object, Class, Map)
     * @see java.lang.reflect.Array#newInstance(Class, int)
     */
    public static <T> T[] toArray(String json,Class<T> rootClass,Map<String, Class<?>> classMap){
        JSONArray jsonArray = toJSONArray(json);

        int size = jsonArray.size();
        T[] t = ArrayUtil.newArray(rootClass, size);
        for (int i = 0; i < size; i++){
            t[i] = toBean(jsonArray.getJSONObject(i), rootClass, classMap);
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
     * @see #toList(String, Class, Map)
     */
    public static <T> List<T> toList(String json,Class<T> rootClass){
        return toList(json, rootClass, null);
    }

    /**
     * 把一个json数组串转换成集合,且集合里的对象的属性含有另外实例Bean.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "[{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]";
     * Map{@code <String, Class<?>>} classMap = new HashMap{@code <String, Class<?>>}();
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
     [{
             "id": 0,
             "data": [            {
                 "dateAttr": null,
                 "name": "get"
             }]
         },
                 {
             "id": 0,
             "data": [            {
                 "dateAttr": null,
                 "name": "set"
             }]
      }]
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param rootClass
     *            e.g. MyBean.class,see {@link net.sf.json.JsonConfig#setRootClass(Class)}
     * @param classMap
     *            e.g. classMap.put("data", Person.class)
     * @return List
     * @see net.sf.json.JSONArray#getJSONObject(int)
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see #toBean(Object, Class, Map)
     */
    public static <T> List<T> toList(String json,Class<T> rootClass,Map<String, Class<?>> classMap){
        JSONArray jsonArray = toJSONArray(json);
        List<T> list = new ArrayList<T>();
        for (int i = 0, j = jsonArray.size(); i < j; i++){
            list.add(toBean(jsonArray.getJSONObject(i), rootClass, classMap));
        }
        return list;
    }

    // [end]

    // ********************************Map******************************************************

    // [start]toMap

    /**
     * 这是简单的 将json字符串转成map.
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
     * Map{@code <String, Integer>} map2 = JsonUtil.toMap("{'brandCode':55555}");
     * LOGGER.info(JsonUtil.format(map2));
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
     * <h3>注意:</h3>
     * 
     * <p>
     * 由于泛型 unchecked,所以可能返回的结果泛型里面有其他类型的值
     * </p>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, Long>} map3 = JsonUtil.toMap("{'brandCode':55.555}");
     * LOGGER.info(JsonUtil.format(map3));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {"brandCode": 55.555}
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            the json
     * @return 如果 <code>json</code> 是null或者empty,返回 {@link Collections#emptyMap()}
     * @see #toMap(String, Class)
     * @since 1.5.0
     */
    public static <T> Map<String, T> toMap(String json){
        return toMap(json, null);
    }

    /**
     * 把json对象串转换成map对象,且map对象里存放的为其他实体Bean.
     * 
     * <p>
     * 如果 null==klass,那么直接将json里面的value 作为map 的value
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "{'data1':{'name':'get'},'data2':{'name':'set'}}";
     * Map{@code <String, Person>} map = JsonUtil.toMap(json, Person.class);
     * LOGGER.info(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     {"data1":{
                 "name": "get"
             },
      "data2":{
                 "name": "set"
     }}
     * </pre>
     * 
     * <hr>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map1 = JsonUtil.toMap("{'data1':{'name':'get'},'data2':{'name':'set'}}", null);
     * LOGGER.info(JsonUtil.format(map1));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "data1": {"name": "get"},
     * "data2": {"name": "set"}
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'data1':{'name':'get'}, 'data2':{'name':'set'}}
     * @param rootClass
     *            e.g. Person.class ,see {@link net.sf.json.JsonConfig#setRootClass(Class)}
     * @return 如果 <code>json</code> 是null或者empty,返回 {@link Collections#emptyMap()}
     * @see #toMap(String, Class, Map)
     */
    public static <T> Map<String, T> toMap(String json,Class<T> rootClass){
        return toMap(json, rootClass, null);
    }

    /**
     * 把json对象串转换成map对象,且map对象里存放的其他实体Bean还含有另外实体Bean.
     * 
     * <p>
     * 如果 null==klass,那么直接将json里面的value 作为map 的value
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "{'mybean':{'data':[{'name':'get'}]}}";
     * Map{@code <String, Class<?>>} classMap = new HashMap{@code <String, Class<?>>}();
     * classMap.put("data", Person.class);
     * 
     * Map{@code <String, MyBean>} map = JsonUtil.toMap(json, MyBean.class, classMap);
     * LOGGER.debug(JsonUtil.format(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
       {"mybean":{
                 "id": 0,
                 "data": [{
                     "name": "get"
                 }]
       }}
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'mybean':{'data':[{'name':'get'}]}}
     * @param rootClass
     *            e.g. MyBean.class ,see {@link net.sf.json.JsonConfig#setRootClass(Class)}
     * @param classMap
     *            e.g. classMap.put("data", Person.class)
     * @return 如果 <code>json</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see net.sf.json.JSONObject#keys()
     * @see #toBean(Object, Class, Map)
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> toMap(String json,Class<T> rootClass,Map<String, Class<?>> classMap){
        LOGGER.trace("input json:[{}],rootClass:[{}]", json, rootClass);

        if (isNullOrEmpty(json)){
            return Collections.emptyMap();
        }

        Map<String, T> map = new HashMap<String, T>();

        JSONObject jsonObject = toJSONObject(json);
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()){
            String key = keys.next();
            Object value = jsonObject.get(key);
            LOGGER.debug("key:[{}],value:[{}],value type is:[{}]", key, value, value.getClass().getName());
            map.put(key, null == rootClass ? (T) value : toBean(value, rootClass, classMap));//如果klass是null,表示不需要转换
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
     * @return the t
     */
    public static <T> T toBean(Object json,Class<T> rootClass){
        return toBean(json, rootClass, null);
    }

    /**
     * 从json串转换成实体对象,并且实体集合属性存有另外实体Bean.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String json = "{'data':[{'name':'get'},{'name':'set'}],'id':5}";
     * Map{@code <String, Class<?>>} classMap = new HashMap{@code <String, Class<?>>}();
     * classMap.put("data", Person.class);
     * 
     * MyBean myBean = JsonUtil.toBean(json, MyBean.class, classMap);
     * LOGGER.info(JsonUtil.format(myBean));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     {
             "id": 5,
             "data":[{
                     "dateAttr": null,
                     "name": "get"
                 },{
                     "dateAttr": null,
                     "name": "set"
                 }
             ]
      }
     * </pre>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'data':[{'name':'get'},{'name':'set'}]}
     * @param rootClass
     *            e.g. MyBean.class,see {@link net.sf.json.JsonConfig#setRootClass(Class)}
     * @param classMap
     *            e.g. classMap.put("data", Person.class)
     * @return Object
     * @see #toBean(Object, JsonConfig)
     */
    public static <T> T toBean(Object json,Class<T> rootClass,Map<String, Class<?>> classMap){
        JSONObject jsonObject = JSONObject.fromObject(json);

        JsonConfig jsonConfig = getDefaultJsonConfig();
        jsonConfig.setRootClass(rootClass);

        if (isNotNullOrEmpty(classMap)){
            jsonConfig.setClassMap(classMap);
        }
        return toBean(jsonObject, jsonConfig);
    }

    /**
     * json串,转换成对象.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            the json
     * @param jsonConfig
     *            the json config
     * @return the object
     * @see net.sf.json.JSONObject#toBean(JSONObject, JsonConfig)
     */
    @SuppressWarnings("unchecked")
    private static <T> T toBean(Object json,JsonConfig jsonConfig){
        JSONObject jsonObject = JSONObject.fromObject(json);

        // Ignore missing properties with Json-Lib

        // 避免出现 Unknown property 'orderIdAndCodeMap' on class 'class
        // com.baozun.trade.web.controller.payment.result.command.PaymentResultEntity' 异常
        jsonConfig.setPropertySetStrategy(new PropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
        return (T) JSONObject.toBean(jsonObject, jsonConfig);
    }

    // [end]
    //***********************************************************************************
    /**
     * 默认的JsonConfig.
     *
     * @return the default json config
     * @see see net.sf.json.JsonConfig#DEFAULT_EXCLUDES
     * 
     * @see net.sf.json.util.CycleDetectionStrategy#LENIENT
     */
    private static JsonConfig getDefaultJsonConfig(){
        JsonConfig jsonConfig = new JsonConfig();

        // 排除,避免循环引用 There is a cycle in the hierarchy!
        //Returns empty array and null object
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

        //see net.sf.json.JsonConfig#DEFAULT_EXCLUDES
        //默认会过滤的几个key "class", "declaringClass","metaClass"  
        jsonConfig.setIgnoreDefaultExcludes(false);

        //See javax.persistence.Transient
        //jsonConfig.setIgnoreJPATransient(true);

        //see Modifier.TRANSIENT
        //jsonConfig.setIgnoreTransientFields(true);

        //jsonConfig.setIgnorePublicFields(false);

        // 注册日期处理器
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(COMMON_DATE_AND_TIME));

        // java.lang.ClassCastException: JSON keys must be strings
        // see http://feitianbenyue.iteye.com/blog/2046877
        jsonConfig.setAllowNonStringKeys(true);
        return jsonConfig;
    }

    /**
     * 是否是可以被json format的类型.
     *
     * @param <V>
     *            the value type
     * @param value
     *            the value
     * @param allowClassTypes
     *            the allow class types
     * @return true, if checks if is allow format type
     * @since 1.4.0
     */
    private static <V> boolean isAllowFormatType(V value,Class<?>...allowClassTypes){
        if (null == value){//null 是可以 format的
            return true;
        }
        Class<?> klassClass = value.getClass();
        return ClassUtils.isPrimitiveOrWrapper(klassClass) //
                        || String.class == klassClass //
                        || ObjectUtil.isArray(value)//XXX 数组一般 是可以转换的 
                        || ClassUtil.isInstanceAnyClass(value, allowClassTypes)//
        ;
    }
}
