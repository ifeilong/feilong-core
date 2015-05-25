/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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
package com.feilong.core.tools.json;

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
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;
import net.sf.json.util.PropertySetStrategy;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DatePattern;
import com.feilong.core.tools.json.processor.DateJsonValueProcessor;
import com.feilong.core.util.ArrayUtil;
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
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0.5 Jan 26, 2013 8:02:09 PM
 * @since 1.0.5
 */
public final class JsonUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    /** Don't let anyone instantiate this class. */
    private JsonUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 设置日期转换格式
     * 
     */
    static{
        // 可转换的日期格式，即Json串中可以出现以下格式的日期与时间
        String[] formats = { DatePattern.COMMON_DATE_AND_TIME, DatePattern.COMMON_TIME, DatePattern.COMMON_DATE };
        DateMorpher dateMorpher = new DateMorpher(formats);

        // 注册器
        MorpherRegistry morpherRegistry = JSONUtils.getMorpherRegistry();
        morpherRegistry.registerMorpher(dateMorpher);
    }

    //***************************format********************************************************

    // [start] format

    /**
     * 格式化输出,将对象转成toJSON,并且 toString(4, 4) 输出.
     *
     * @param obj
     *            任何对象
     * @return the string
     * @throws JSONException
     *             the JSON exception
     * @see #format(Object, String[])
     * @see #format(Object, JsonConfig)
     */
    public static String format(Object obj) throws JSONException{
        String[] excludes = null;
        return format(obj, excludes);
    }

    /**
     * 格式化输出,将对象转成toJSON,并且 toString(4, 4) 输出.
     *
     * @param obj
     *            对象
     * @param excludes
     *            排除需要序列化成json的属性,如果 excludes isNotNullOrEmpty,那么不会setExcludes
     * @return if null==obj will return {@code null}; {@link #format(Object, JsonConfig)}
     * @throws JSONException
     *             the JSON exception
     * @see #format(Object, JsonConfig)
     * @see <a href="http://feitianbenyue.iteye.com/blog/2046877">java.lang.ClassCastException: JSON keys must be strings</a>
     */
    public static String format(Object obj,String[] excludes) throws JSONException{
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
     * @return the string
     * @throws JSONException
     *             the JSON exception
     */
    public static String format(Object obj,String[] excludes,int indentFactor,int indent) throws JSONException{
        if (null == obj){
            return null;
        }
        JsonConfig jsonConfig = getDefaultJsonConfig();

        if (Validator.isNotNullOrEmpty(excludes)){
            jsonConfig.setExcludes(excludes);
        }

        JSON json = toJSON(obj, jsonConfig);
        String string = json.toString(indentFactor, indent);
        return string;
    }

    /**
     * 只包含这些key才被format出json格式.
     *
     * @param obj
     *            the obj
     * @param includes
     *            the includes
     * @return the string
     * @throws JSONException
     *             the JSON exception
     * @since 1.0.8
     */
    public static String formatWithIncludes(Object obj,final String...includes) throws JSONException{

        if (null == obj){
            return null;
        }

        JsonConfig jsonConfig = getDefaultJsonConfig();

        if (Validator.isNotNullOrEmpty(includes)){

            jsonConfig.setJsonPropertyFilter(new PropertyFilter(){

                @Override
                public boolean apply(Object source,String name,Object value){
                    return !ArrayUtil.isContain(includes, name);
                }
            });

        }
        return format(obj, jsonConfig);
    }

    /**
     * 默认的JsonConfig.
     *
     * @return the default json config
     */
    private static JsonConfig getDefaultJsonConfig(){
        JsonConfig jsonConfig = new JsonConfig();

        // 排除,避免循环引用 There is a cycle in the hierarchy!
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

        //see net.sf.json.JsonConfig.DEFAULT_EXCLUDES    "class", "declaringClass","metaClass"  // 默认会过滤的几个key  
        jsonConfig.setIgnoreDefaultExcludes(true);
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());

        // java.lang.ClassCastException: JSON keys must be strings
        // see http://feitianbenyue.iteye.com/blog/2046877
        jsonConfig.setAllowNonStringKeys(true);

        return jsonConfig;
    }

    /**
     * 格式化输出,将对象转成toJSON,并且 toString(4, 4) 输出.<br>
     *
     * @param obj
     *            the obj
     * @param jsonConfig
     *            the json config
     * @return if null==obj will return {@code null}; else return toJSON(obj, jsonConfig).toString(4, 4)
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JsonConfig
     * @see #toJSON(Object, JsonConfig)
     * @see net.sf.json.JSON#toString(int, int)
     * @see #format(Object, JsonConfig, int, int)
     * @since 1.0.7
     */
    public static String format(Object obj,JsonConfig jsonConfig) throws JSONException{
        return format(obj, jsonConfig, 4, 4);
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
     * @throws JSONException
     *             the JSON exception
     * @since 1.0.8
     */
    public static String format(Object obj,JsonConfig jsonConfig,int indentFactor,int indent) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     */
    public static <T> T toBean(Object json,Class<T> rootClass) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see #toBean(Object, JsonConfig)
     */
    // TODO
    @SuppressWarnings("unchecked")
    public static <T> T toBean(Object json,Class<T> rootClass,Map<String, Class<?>> classMap) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JSONObject#toBean(JSONObject, JsonConfig)
     */
    public static Object toBean(Object json,JsonConfig jsonConfig) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see net.sf.json.JSONArray#toArray()
     */
    public static Object[] toArray(String json) throws JSONException{
        return JSONArray.fromObject(json).toArray();
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
     * @throws JSONException
     *             the JSON exception
     * @see #toArray(String, Class, Map)
     */
    public static <T> T[] toArray(String json,Class<T> clazz) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see net.sf.json.JSONArray#getJSONObject(int)
     * @see #toBean(Object, Class, Map)
     * @see java.lang.reflect.Array#newInstance(Class, int)
     */
    public static <T> T[] toArray(String json,Class<T> clazz,Map<String, Class<?>> classMap) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JSONArray#get(int)
     */
    public static List<Object> toList(String json) throws JSONException{
        JSONArray jsonArr = JSONArray.fromObject(json);
        int size = jsonArr.size();

        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < size; i++){
            Object e = jsonArr.get(i);
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
     * @throws JSONException
     *             the JSON exception
     * @see #toList(String, Class, Map)
     */
    public static <T> List<T> toList(String json,Class<T> clazz) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JSONArray#getJSONObject(int)
     * @see net.sf.json.JSONArray#fromObject(Object)
     * @see #toBean(Object, Class, Map)
     */
    // TODO
    public static <T> List<T> toList(String json,Class<T> clazz,Map<String, Class<?>> classMap) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JSONObject#get(String)
     * @see net.sf.json.JSONObject#fromObject(Object)
     */
    public static Map<String, Object> toMap(String json) throws JSONException{
        JSONObject jsonObject = JSONObject.fromObject(json);

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
     * @throws JSONException
     *             the JSON exception
     * @see #toMap(String, Class, Map)
     */
    public static <T> Map<String, T> toMap(String json,Class<T> clazz) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JSONObject#keys()
     * @see #toBean(Object, Class, Map)
     */
    // TODO
    public static <T> Map<String, T> toMap(String json,Class<T> clazz,Map<String, Class<?>> classMap) throws JSONException{
        if (log.isDebugEnabled()){
            log.debug("in json:{}", json);
        }

        JSONObject jsonObject = JSONObject.fromObject(json);

        Map<String, T> map = new HashMap<String, T>();
        @SuppressWarnings("unchecked")
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()){
            String key = keys.next();
            Object value = jsonObject.get(key);
            if (log.isDebugEnabled()){
                log.debug("key:{} value:{}", key, value);
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
     * @throws JSONException
     *             the JSON exception
     * @see #toJSON(Object, JsonConfig)
     */
    public static JSON toJSON(Object obj) throws JSONException{
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
     * @throws JSONException
     *             the JSON exception
     * @see net.sf.json.JSONArray#fromObject(Object, JsonConfig)
     * @see net.sf.json.JSONObject#fromObject(Object, JsonConfig)
     * @see net.sf.json.util.JSONUtils#isArray(Object)
     * @see java.lang.Class#isEnum()
     * @see net.sf.json.JsonConfig#registerJsonValueProcessor(Class, JsonValueProcessor)
     * @see org.apache.commons.collections.IteratorUtils#toList(Iterator)
     * @see org.apache.commons.collections.IteratorUtils#toList(Iterator, int)
     */
    public static JSON toJSON(Object obj,JsonConfig jsonConfig) throws JSONException{
        if (null == jsonConfig){
            jsonConfig = getDefaultJsonConfig();
            // 注册日期处理器
            DateJsonValueProcessor jsonValueProcessor = new DateJsonValueProcessor(DatePattern.COMMON_DATE_AND_TIME);
            jsonConfig.registerJsonValueProcessor(Date.class, jsonValueProcessor);
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
     * Object to xml<br>
     * 缺点:
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
        // xmlSerializer.setTypeHintsEnabled(true);
        // xmlSerializer.setTypeHintsCompatibility(true);
        // xmlSerializer.setSkipWhitespace(false);
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
}
