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

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.feilong.core.DatePattern;
import com.feilong.core.lang.ClassUtil;
import com.feilong.core.lang.ObjectUtil;
import com.feilong.tools.jsonlib.processor.DateJsonValueProcessor;
import com.feilong.tools.jsonlib.processor.SensitiveWordsJsonValueProcessor;

import static com.feilong.core.Validator.isNotNullOrEmpty;
import static com.feilong.core.Validator.isNullOrEmpty;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONTokener;
import net.sf.json.util.JSONUtils;

/**
 * json处理的辅助类.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.9.4
 */
final class JsonHelper{

    /** 单利模式. */
    private static final JsonConfig DEFAULT_JSON_CONFIG_INSTANCE   = new JsonConfig();

    /** The Constant DEFAULT_JAVA_TO_JSON_CONFIG. */
    private static final JsonConfig DEFAULT_JAVA_TO_JSON_CONFIG    = buildDefaultJavaToJsonConfig();

    //***********************************************************************************
    /** The Constant SENSITIVE_WORDS_PROPERTY_NAMES. */
    private static final String[]   SENSITIVE_WORDS_PROPERTY_NAMES = { "password", "key" };
    //***********************************************************************************

    /** Don't let anyone instantiate this class. */
    private JsonHelper(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 转换value的值.
     *
     * @param <T>
     *            the generic type
     * @param value
     *            the value
     * @param jsonToJavaConfig
     *            the json to java config
     * @return 如果null == jsonToJavaConfig 或者 null == jsonToJavaConfig.getRootClass() 返回value,<br>
     *         否则,使用 {@link JsonUtil#toBean(Object, JsonToJavaConfig)} 转成对应的bean
     */
    @SuppressWarnings("unchecked")
    static <T> T transformerValue(Object value,JsonToJavaConfig jsonToJavaConfig){
        return null == jsonToJavaConfig || null == jsonToJavaConfig.getRootClass() ? (T) value
                        : JsonUtil.<T> toBean(value, jsonToJavaConfig);
    }
    // [start]toJSON

    /**
     * 把实体Bean、Map对象、数组、列表集合转换成{@link JSON}.
     * 
     * <h3>说明:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>如果 <code>null==jsonConfig</code>,将使用 {@link #DEFAULT_JAVA_TO_JSON_CONFIG}</li>
     * 
     * <li>
     * 
     * <p>
     * 以下类型将转成{@link JSONArray}:
     * </p>
     * 
     * <ul>
     * <li>如果是字符串,当是以"["符号开头,"]"符号结尾的时候</li>
     * <li>数组 obj.getClass().isArray()||obj instanceof Object[]</li>
     * <li>集合 obj instanceof Collection</li>
     * <li>枚举 obj instanceof Enum</li>
     * <li>迭代器 obj instanceof Iterator</li>
     * </ul>
     * 
     * </li>
     * <li>其他类型转成 {@link JSONObject}</li>
     * </ol>
     * </blockquote>
     *
     * @param obj
     *            可以是数组,字符串,枚举,集合,map,Java bean,Iterator等类型,内部自动识别转成{@link JSONArray}还是{@link JSONObject}
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
        JsonConfig useJsonConfig = defaultIfNull(jsonConfig, DEFAULT_JAVA_TO_JSON_CONFIG);
        registerDefaultJsonValueProcessor(useJsonConfig);

        if (isNeedConvertToJSONArray(obj)){
            Object arrayJsonObject = obj instanceof Iterator ? IteratorUtils.toList((Iterator<?>) obj) : obj;
            return JsonHelper.toJSONArray(arrayJsonObject, useJsonConfig);
        }
        return JsonHelper.toJSONObject(obj, useJsonConfig);
    }

    /**
     * 是否需要转成 {@link JSONArray}类型.
     * 
     * <h3>以下类型将转成{@link JSONArray}:</h3>
     * <blockquote>
     * <ol>
     * <li>如果是字符串,当是以"["符号开头,"]"符号结尾的时候</li>
     * <li>数组 obj.getClass().isArray()||obj instanceof Object[]</li>
     * <li>集合 obj instanceof Collection</li>
     * <li>枚举 obj instanceof Enum</li>
     * <li>迭代器 obj instanceof Iterator</li>
     * </ol>
     * </blockquote>
     *
     * @param obj
     *            the obj
     * @return true, if is need convert to JSON array
     * @see net.sf.json.JSONArray#_fromJSONTokener(net.sf.json.util.JSONTokener, JsonConfig)
     * @see net.sf.json.util.JSONUtils#isArray(Object)
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

    /**
     * 默认的处理器.
     *
     * @param jsonConfig
     *            the json config
     */
    private static void registerDefaultJsonValueProcessor(JsonConfig jsonConfig){
        for (String propertyName : SENSITIVE_WORDS_PROPERTY_NAMES){
            jsonConfig.registerJsonValueProcessor(propertyName, SensitiveWordsJsonValueProcessor.INSTANCE);
        }
    }
    // [end]

    //********************************************************************************************

    /**
     * 将 <code>obj</code>转成 {@link JSONArray}.
     *
     * @param obj
     *            Accepts JSON formatted strings, arrays, Collections and Enums.
     * @param useJsonConfig
     *            如果是null,将使用 {@link #DEFAULT_JSON_CONFIG_INSTANCE}
     * @return the JSON array
     * @see net.sf.json.JSONArray#fromObject(Object, JsonConfig)
     */
    static JSONArray toJSONArray(Object obj,JsonConfig useJsonConfig){
        return JSONArray.fromObject(obj, defaultIfNull(useJsonConfig, DEFAULT_JSON_CONFIG_INSTANCE));
    }

    //**************toJSONObject********************

    /**
     * 将 <code>object</code>转成 {@link JSONObject}.
     *
     * @param object
     *            可以是 <code>null</code>,{@link JSONObject},{@link DynaBean} ,{@link JSONTokener},{@link JSONString},
     *            {@link Map},{@link String},<code>JavaBeans</code>
     * @param useJsonConfig
     *            如果是null,将使用 {@link #DEFAULT_JSON_CONFIG_INSTANCE}
     * @return the JSON object
     * @see net.sf.json.JSONObject#fromObject(Object, JsonConfig)
     */
    static JSONObject toJSONObject(Object object,JsonConfig useJsonConfig){
        return JSONObject.fromObject(object, defaultIfNull(useJsonConfig, DEFAULT_JSON_CONFIG_INSTANCE));
    }

    /**
     * Builds the java to json config.
     *
     * @param excludes
     *            the excludes
     * @param includes
     *            the includes
     * @return 如果<code>excludes</code>是null或者是empty,并且<code>includes</code>是null或者是empty将返回null
     */
    static JavaToJsonConfig toJavaToJsonConfig(String[] excludes,String[] includes){
        boolean noNeedBuild = isNullOrEmpty(excludes) && isNullOrEmpty(includes);
        return noNeedBuild ? null : new JavaToJsonConfig(excludes, includes);
    }

    /**
     * Builds the java bean to json config.
     *
     * @param javaBean
     *            the obj
     * @return 取到该javabean的Field,解析是否有 {@link SensitiveWords}注解,如果有,那么添加 {@link SensitiveWordsJsonValueProcessor}
     */
    static JavaToJsonConfig buildJavaBeanToJsonConfig(Object javaBean){
        List<Field> fieldsList = FieldUtils.getFieldsListWithAnnotation(javaBean.getClass(), SensitiveWords.class);
        if (isNotNullOrEmpty(fieldsList)){
            //敏感字段
            Map<String, JsonValueProcessor> propertyNameAndJsonValueProcessorMap = new HashMap<>();
            for (Field field : fieldsList){
                propertyNameAndJsonValueProcessorMap.put(field.getName(), SensitiveWordsJsonValueProcessor.INSTANCE);
            }
            return new JavaToJsonConfig(propertyNameAndJsonValueProcessorMap);
        }
        return null;
    }
    //***********************************************************************************

    /**
     * 默认的java to json JsonConfig.
     * 
     * <h3>含有以下的特性:</h3>
     * <blockquote>
     * <ol>
     * <li>{@link CycleDetectionStrategy#LENIENT} 避免循环引用</li>
     * <li>no IgnoreDefaultExcludes,默认过滤几个key "class", "declaringClass","metaClass"</li>
     * <li>
     * {@link DateJsonValueProcessor},如果是日期,自动渲染成 {@link DatePattern#COMMON_DATE_AND_TIME} 格式类型,如有需要可以使用
     * {@link JavaToJsonConfig#setPropertyNameAndJsonValueProcessorMap(Map)}覆盖此属性
     * </li>
     * <li>AllowNonStringKeys,允许非 string类型的key</li>
     * </ol>
     * </blockquote>
     *
     * @return the default json config
     * @see see net.sf.json.JsonConfig#DEFAULT_EXCLUDES
     * @see net.sf.json.util.CycleDetectionStrategy#LENIENT
     * 
     * @see <a href="http://feitianbenyue.iteye.com/blog/2046877">通过setAllowNonStringKeys解决java.lang.ClassCastException: JSON keys must be
     *      strings</a>
     */
    static JsonConfig buildDefaultJavaToJsonConfig(){
        JsonConfig jsonConfig = new JsonConfig();

        //see net.sf.json.JsonConfig#DEFAULT_EXCLUDES
        //默认会过滤的几个key "class", "declaringClass","metaClass"  
        jsonConfig.setIgnoreDefaultExcludes(false);

        // java.lang.ClassCastException: JSON keys must be strings
        // see http://feitianbenyue.iteye.com/blog/2046877
        jsonConfig.setAllowNonStringKeys(true);

        //排除,避免循环引用 There is a cycle in the hierarchy!
        //Returns empty array and null object
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

        // 注册日期处理器
        jsonConfig.registerJsonValueProcessor(Date.class, DateJsonValueProcessor.DEFAULT_INSTANCE);
        return jsonConfig;
    }

    /**
     * 是否允许被json format的类型.
     * 
     * <h3>如果是以下类型是被允许的:</h3>
     * <blockquote>
     * <ol>
     * <li>{@link ClassUtils#isPrimitiveOrWrapper(Class)}</li>
     * <li>{@link String}</li>
     * <li>{@link ObjectUtil#isArray(Object)}</li>
     * <li>{@link ClassUtil#isInstanceAnyClass(Object, Class...)}</li>
     * </ol>
     * </blockquote>
     *
     * @param <V>
     *            the value type
     * @param value
     *            the value
     * @param allowClassTypes
     *            the allow class types
     * @return true, if checks if is allow format type
     */
    static <V> boolean isAllowFormatType(V value,Class<?>...allowClassTypes){
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
