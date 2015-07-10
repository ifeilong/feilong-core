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
package com.feilong.core.tools.jsonlib;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DatePattern;
import com.feilong.core.tools.jsonlib.processor.DateJsonValueProcessor;
import com.feilong.core.tools.jsonlib.propertyFilter.ArrayContainsPropertyNamesPropertyFilter;
import com.feilong.core.tools.jsonlib.propertySetStrategy.PropertyStrategyWrapper;
import com.feilong.core.util.Validator;

/**
 * json 工具类.
 * 
 * <h3>依赖于下面的jar:</h3>
 * 
 * <blockquote>
 * 
 * <pre>
 * {@code
 * <groupId>net.sf.json-lib</groupId>
 * <artifactId>json-lib</artifactId>
 * 
 * 如果要使用xml部分功能,需要
 * <groupId>xom</groupId> 
 * <artifactId>xom</artifactId>
 * }
 * </pre>
 * 
 * </blockquote>
 *
 * @author feilong
 * @version 1.0.5 Jan 26, 2013 8:02:09 PM
 * @see net.sf.json.JSONSerializer#toJSON(Object, JsonConfig)
 * @since 1.0.5
 * @deprecated net.sf.json-lib Non-maintenance,will use Jackson instead
 */
@Deprecated
public final class JsonUtil{

    /** The Constant LOGGER. */
    private static final Logger     LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    /** The Constant DEFAULT_JSON_CONFIG. */
    private static final JsonConfig DEFAULT_JSON_CONFIG;

    //***********************************************************************************

    /** Don't let anyone instantiate this class. */
    private JsonUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //***********************************************************************************
    /**
     * 设置日期转换格式
     */
    static{
        // 可转换的日期格式，即Json串中可以出现以下格式的日期与时间
        DateMorpher dateMorpher = new DateMorpher(new String[] {
            DatePattern.COMMON_DATE_AND_TIME,
            DatePattern.COMMON_TIME,
            DatePattern.COMMON_DATE });

        // 注册器
        MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
        morpherRegistry.registerMorpher(dateMorpher);

        DEFAULT_JSON_CONFIG = getDefaultJsonConfig();
    }

    //***************************format********************************************************

    // [start] format

    /**
     * 格式化输出,将对象转成toJSON,并且 toString(4, 4) 输出.
     *
     * @param obj
     *            任何对象
     * @return the string
     * @see #format(Object, JsonFormatConfig)
     */
    public static String format(Object obj){
        JsonFormatConfig jsonFormatConfig = null;
        return format(obj, jsonFormatConfig);
    }

    /**
     * 格式化输出,将对象转成toJSON,并且 toString(4, 4) 输出.
     *
     * @param obj
     *            对象
     * @param excludes
     *            排除需要序列化成json的属性,如果 excludes isNotNullOrEmpty,那么不会setExcludes
     * @return if null==obj will return {@code null}; {@link #format(Object, JsonConfig)}
     * @see #format(Object, JsonConfig)
     * @see <a href="http://feitianbenyue.iteye.com/blog/2046877">java.lang.ClassCastException: JSON keys must be strings</a>
     */
    public static String format(Object obj,String[] excludes){
        return format(obj, excludes, 4, 4);
    }

    /**
     * 只包含这些key才被format出json格式.
     *
     * @param obj
     *            the obj
     * @param includes
     *            the includes
     * @return the string
     * @since 1.0.8
     */
    public static String formatWithIncludes(Object obj,final String...includes){
        if (null == obj){
            return null;
        }
        JsonFormatConfig jsonFormatConfig = null;
        if (Validator.isNotNullOrEmpty(includes)){
            jsonFormatConfig = new JsonFormatConfig();
            jsonFormatConfig.setIncludes(includes);
        }
        return format(obj, jsonFormatConfig);
    }

    /**
     * 格式化输出,将对象转成toJSON,并且 toString(4, 4) 输出.<br>
     *
     * @param obj
     *            the obj
     * @param jsonConfig
     *            the json config
     * @return if null==obj will return {@code null}; else return toJSON(obj, jsonConfig).toString(4, 4)
     * @see net.sf.json.JsonConfig
     * @see #toJSON(Object, JsonConfig)
     * @see net.sf.json.JSON#toString(int, int)
     * @see #format(Object, JsonConfig, int, int)
     * @since 1.0.7
     */
    public static String format(Object obj,JsonConfig jsonConfig){
        return format(obj, jsonConfig, 4, 4);
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
        JsonConfig jsonConfig = null;
        return format(obj, jsonConfig, indentFactor, indent);
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
     * @return the string
     */
    public static String format(Object obj,String[] excludes,Integer indentFactor,Integer indent){
        if (null == obj){
            return null;
        }
        JsonFormatConfig jsonFormatConfig = null;
        if (Validator.isNotNullOrEmpty(excludes)){
            jsonFormatConfig = new JsonFormatConfig();
            jsonFormatConfig.setExcludes(excludes);
        }
        return format(obj, jsonFormatConfig, indentFactor, indent);
    }

    /**
     * Format.
     *
     * @param obj
     *            the obj
     * @param jsonFormatConfig
     *            the json format config
     * @return the string
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
     * @return the string
     * @since 1.2.2
     */
    public static String format(Object obj,JsonFormatConfig jsonFormatConfig,int indentFactor,int indent){
        JsonConfig jsonConfig = null;

        if (null == jsonFormatConfig){
            return format(obj, jsonConfig);
        }

        jsonConfig = getDefaultJsonConfig();

        Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap = jsonFormatConfig.getPropertyNameAndJsonValueProcessorMap();

        if (Validator.isNotNullOrEmpty(propertyNameAndJsonValueProcessorMap)){
            for (Map.Entry<String, JsonValueProcessor> entry : propertyNameAndJsonValueProcessorMap.entrySet()){
                String propertyName = entry.getKey();
                JsonValueProcessor jsonValueProcessor = entry.getValue();
                jsonConfig.registerJsonValueProcessor(propertyName, jsonValueProcessor);
            }
        }

        String[] excludes = jsonFormatConfig.getExcludes();
        if (Validator.isNotNullOrEmpty(excludes)){
            jsonConfig.setExcludes(excludes);
        }

        String[] includes = jsonFormatConfig.getIncludes();
        if (Validator.isNotNullOrEmpty(includes)){
            jsonConfig.setJsonPropertyFilter(new ArrayContainsPropertyNamesPropertyFilter(includes));
        }

        return format(obj, jsonConfig, indentFactor, indent);
    }

    /**
     * Make a prettyprinted JSON text.
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
     * @return a printable, displayable, transmittable representation of the object, beginning with { (left brace) and ending with } (right
     *         brace).
     * @since 1.0.8
     */
    public static String format(Object obj,JsonConfig jsonConfig,int indentFactor,int indent){
        if (null == obj){
            return null;
        }
        JSON json = toJSON(obj, jsonConfig);
        String string = json.toString(indentFactor, indent);
        return string;
    }

    // [end]

    // [start]toBean

    /**
     * json串,转换成实体对象.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'name':'get','dateAttr':'2009-11-12'}<br>
     *            可以是 json字符串,也可以是JSONObject<br>
     *            Accepts JSON formatted strings, Maps, DynaBeans and JavaBeans. <br>
     *            支持的格式有: {@link JSONObject#fromObject(Object, JsonConfig)}
     * @param rootClass
     *            e.g. Person.class
     * @return the t
     */
    public static <T> T toBean(Object json,Class<T> rootClass){
        return toBean(json, rootClass, null);
    }

    /**
     * 从json串转换成实体对象，并且实体集合属性存有另外实体Bean.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'data':[{'name':'get'},{'name':'set'}]}
     * @param rootClass
     *            e.g. MyBean.class
     * @param classMap
     *            e.g. classMap.put("data", Person.class)
     * @return Object
     * @see #toBean(Object, JsonConfig)
     */
    // TODO
    @SuppressWarnings("unchecked")
    public static <T> T toBean(Object json,Class<T> rootClass,Map<String, Class<?>> classMap){
        JSONObject jsonObject = JSONObject.fromObject(json);

        JsonConfig jsonConfig = getDefaultJsonConfig();
        jsonConfig.setRootClass(rootClass);

        if (Validator.isNotNullOrEmpty(classMap)){
            jsonConfig.setClassMap(classMap);
        }
        return (T) toBean(jsonObject, jsonConfig);
    }

    /**
     * json串,转换成对象.
     *
     * @param json
     *            the json
     * @param jsonConfig
     *            the json config
     * @return the object
     * @see net.sf.json.JSONObject#toBean(JSONObject, JsonConfig)
     */
    public static Object toBean(Object json,JsonConfig jsonConfig){
        JSONObject jsonObject = JSONObject.fromObject(json);

        // Ignore missing properties with Json-Lib

        // 避免出现 Unknown property 'orderIdAndCodeMap' on class 'class
        // com.baozun.trade.web.controller.payment.result.command.PaymentResultEntity' 异常
        jsonConfig.setPropertySetStrategy(new PropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
        return JSONObject.toBean(jsonObject, jsonConfig);
    }

    // [end]

    // *****************************Array******************************************************
    // [start]toArray

    /**
     * 把一个json数组串转换成普通数组.
     *
     * @param json
     *            e.g. ['get',1,true,null]
     * @return Object[]
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see net.sf.json.JSONArray#toArray()
     */
    public static Object[] toArray(String json){
        JSONArray jsonArray = JSONArray.fromObject(json);
        return jsonArray.toArray();
    }

    /**
     * 把一个json数组串,转换成实体数组.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'name':'get'},{'name':'set'}]
     * @param clazz
     *            e.g. Person.class
     * @return Object[]
     * @see #toArray(String, Class, Map)
     */
    public static <T> T[] toArray(String json,Class<T> clazz){
        return toArray(json, clazz, null);
    }

    /**
     * 把一个json数组串转换成实体数组，且数组元素的属性含有另外实例Bean.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param clazz
     *            e.g. MyBean.class
     * @param classMap
     *            e.g. classMap.put("data", Person.class)
     * @return T[]
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see net.sf.json.JSONArray#getJSONObject(int)
     * @see #toBean(Object, Class, Map)
     * @see java.lang.reflect.Array#newInstance(Class, int)
     */
    public static <T> T[] toArray(String json,Class<T> clazz,Map<String, Class<?>> classMap){
        JSONArray jsonArray = JSONArray.fromObject(json);
        int size = jsonArray.size();

        @SuppressWarnings("unchecked")
        T[] t = (T[]) Array.newInstance(clazz, size);

        for (int i = 0; i < size; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            t[i] = toBean(jsonObject, clazz, classMap);
        }
        return t;
    }

    // [end]

    // *****************************List********************************************************
    // [start]toList

    /**
     * 把一个json数组串转换成存放普通类型元素的集合.
     *
     * @param json
     *            e.g. ['get',1,true,null]
     * @return List
     * @see net.sf.json.JSONArray#get(int)
     */
    public static List<Object> toList(String json){
        JSONArray jsonArray = JSONArray.fromObject(json);
        int size = jsonArray.size();

        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < size; i++){
            Object e = jsonArray.get(i);
            list.add(e);
        }
        return list;
    }

    /**
     * 把一个json数组串转换成集合，且集合里存放的为实例Bean.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'name':'get'},{'name':'set'}]
     * @param clazz
     *            the clazz
     * @return List
     * @see #toList(String, Class, Map)
     */
    public static <T> List<T> toList(String json,Class<T> clazz){
        return toList(json, clazz, null);
    }

    /**
     * 把一个json数组串转换成集合，且集合里的对象的属性含有另外实例Bean.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}]
     * @param clazz
     *            e.g. MyBean.class
     * @param classMap
     *            e.g. classMap.put("data", Person.class)
     * @return List
     * @see net.sf.json.JSONArray#getJSONObject(int)
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see #toBean(Object, Class, Map)
     */
    // TODO
    public static <T> List<T> toList(String json,Class<T> clazz,Map<String, Class<?>> classMap){
        JSONArray jsonArray = JSONArray.fromObject(json);
        List<T> list = new ArrayList<T>();

        int size = jsonArray.size();
        for (int i = 0; i < size; i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(toBean(jsonObject, clazz, classMap));
        }
        return list;
    }

    // [end]

    // ********************************Map******************************************************

    // [start]toMap

    /**
     * 把json对象串转换成map对象.
     *
     * @param json
     *            e.g. {'name':'get','int':1,'double',1.1,'null':null}
     * @return Map
     * @see net.sf.json.JSONObject#get(String)
     * @see net.sf.json.JSONObject#fromObject(Object)
     */
    public static Map<String, Object> toMap(String json){
        JSONObject jsonObject = JSONObject.fromObject(json);

        //处理不了key 是 null 的情况
        //java.lang.ClassCastException: net.sf.json.JSONNull cannot be cast to java.lang.String
        //Map<String, Object> map = (Map<String, Object>) JSONObject.toBean(jsonObject, Map.class);

        Map<String, Object> map = new HashMap<String, Object>();

        @SuppressWarnings("unchecked")
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()){
            String key = keys.next();
            Object value = jsonObject.get(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 把json对象串转换成map对象，且map对象里存放的为其他实体Bean.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'data1':{'name':'get'}, 'data2':{'name':'set'}}
     * @param clazz
     *            e.g. Person.class
     * @return Map
     * @see #toMap(String, Class, Map)
     */
    public static <T> Map<String, T> toMap(String json,Class<T> clazz){
        return toMap(json, clazz, null);
    }

    /**
     * 把json对象串转换成map对象，且map对象里存放的其他实体Bean还含有另外实体Bean.
     *
     * @param <T>
     *            the generic type
     * @param json
     *            e.g. {'mybean':{'data':[{'name':'get'}]}}
     * @param clazz
     *            e.g. MyBean.class
     * @param classMap
     *            e.g. classMap.put("data", Person.class)
     * @return Map
     * @see net.sf.json.JSONObject#keys()
     * @see #toBean(Object, Class, Map)
     */
    // TODO
    public static <T> Map<String, T> toMap(String json,Class<T> clazz,Map<String, Class<?>> classMap){
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("in json:{}", json);
        }

        JSONObject jsonObject = JSONObject.fromObject(json);

        Map<String, T> map = new HashMap<String, T>();
        @SuppressWarnings("unchecked")
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()){
            String key = keys.next();
            Object value = jsonObject.get(key);
            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("key:{} value:{}", key, value);
            }
            map.put(key, toBean(value, clazz, classMap));
        }
        return map;
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
    public static JSON toJSON(Object obj){
        return toJSON(obj, null);
    }

    /**
     * 把实体Bean、Map对象、数组、列表集合转换成Json串.
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
     * @see org.apache.commons.collections.IteratorUtils#toList(Iterator)
     * @see org.apache.commons.collections.IteratorUtils#toList(Iterator, int)
     * @see net.sf.json.JSONSerializer#toJSON(Object)
     */
    public static JSON toJSON(Object obj,JsonConfig jsonConfig){
        if (null == jsonConfig){
            jsonConfig = DEFAULT_JSON_CONFIG;
        }

        // obj instanceof Collection || obj instanceof Object[]
        if (JSONUtils.isArray(obj) || //
                        obj instanceof Enum || // obj.getClass().isEnum()这么些 null会报错// object' is an Enum. Use JSONArray instead
                        obj instanceof Iterator){

            if (obj instanceof Iterator){
                Collection<?> list = IteratorUtils.toList((Iterator<?>) obj);
                obj = list;
            }
            //Accepts JSON formatted strings, arrays, Collections and Enums.
            return JSONArray.fromObject(obj, jsonConfig);
        }
        //Accepts JSON formatted strings, Maps, DynaBeans and JavaBeans.
        return JSONObject.fromObject(obj, jsonConfig);
    }

    // [end]

    //*******************************objectToXML*************************************************

    // [start]objectToXML

    /**
     * 把json串、数组、集合(collection map)、实体Bean转换成XML<br>
     * XMLSerializer API： http://json-lib.sourceforge.net/apidocs/net/sf/json/xml/XMLSerializer.html 具体实例请参考：<br>
     * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html<br>
     * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html
     * 
     * @param object
     *            the object
     * @return xml
     * @see #objectToXML(Object, String)
     */
    public static String objectToXML(Object object){
        return objectToXML(object, null);
    }

    /**
     * Object to xml.
     * <p>
     * 缺点:
     * </p>
     * <ul>
     * <li>不能去掉 {@code <?xml version="1.0" encoding="UTF-8"? >}</li>
     * <li>不能格式化输出</li>
     * <li>对于空元素,不能输出 {@code <additionalData></additionalData>} ,只能输出 {@code <additionalData/>}</li>
     * </ul>
     * 
     * @param object
     *            the object
     * @param encoding
     *            the encoding
     * @return the string
     * @see #toJSON(Object)
     * @see net.sf.json.xml.XMLSerializer#write(JSON)
     * @see net.sf.json.xml.XMLSerializer#write(JSON, String)
     */
    public static String objectToXML(Object object,String encoding){
        JSON json = toJSON(object);
        XMLSerializer xmlSerializer = new XMLSerializer();
        // xmlSerializer.setRootName("outputPaymentPGW");
        // xmlSerializer.setTypeHintsCompatibility(true);
        // xmlSerializer.setSkipWhitespace(false);
        // xmlSerializer.setTypeHintsEnabled(true);//是否保留元素类型标识，默认true
        // xmlSerializer.setElementName("e");//设置元素标签，默认e
        // xmlSerializer.setArrayName("a");//设置数组标签，默认a
        // xmlSerializer.setObjectName("o");//设置对象标签，默认o
        if (Validator.isNotNullOrEmpty(encoding)){
            return xmlSerializer.write(json, encoding);
        }
        return xmlSerializer.write(json);

    }

    // [end]

    // [start]xmlToJSON

    /**
     * XML转成json串.
     * 
     * @param xml
     *            the xml
     * @return String
     * @see net.sf.json.xml.XMLSerializer#read(String)
     */
    public static JSON xmlToJSON(String xml){
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xml);
        return json;
    }

    // [end]

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
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor(DatePattern.COMMON_DATE_AND_TIME));

        // java.lang.ClassCastException: JSON keys must be strings
        // see http://feitianbenyue.iteye.com/blog/2046877
        jsonConfig.setAllowNonStringKeys(true);

        return jsonConfig;
    }
}
