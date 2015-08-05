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
package com.feilong.core.net;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.lang.ArrayUtil;
import com.feilong.core.lang.CharsetType;
import com.feilong.core.util.Validator;

/**
 * 处理参数相关.
 * 
 * @author feilong
 * @version 1.0.0 2010-4-15 下午04:01:29
 * @version 1.4.0 2015-8-1 22:08
 * @since 1.0.0
 */
public final class ParamUtil{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamUtil.class);

    /** Don't let anyone instantiate this class. */
    private ParamUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将多值的参数map转成单值的参数map.
     * 
     * <p style="color:green">
     * 返回的是 {@link TreeMap},key自然排序
     * </p>
     *
     * @param arrayValueMap
     *            the array value map
     * @return {@link TreeMap}
     * @since 1.4.0
     */
    public static Map<String, String> toSingleValueMap(Map<String, String[]> arrayValueMap){//返回 TreeMap 方便log 显示
        if (Validator.isNullOrEmpty(arrayValueMap)){
            return Collections.emptyMap();
        }

        Map<String, String> singleValueMap = new TreeMap<String, String>();
        for (Map.Entry<String, String[]> entry : arrayValueMap.entrySet()){
            String key = entry.getKey();
            String[] values = entry.getValue();
            String singleValue = Validator.isNotNullOrEmpty(values) ? values[0] : StringUtils.EMPTY;
            singleValueMap.put(key, singleValue);
        }
        return singleValueMap;
    }

    /**
     * To array value map.
     * 
     * <p style="color:green">
     * 返回的是 {@link TreeMap},key自然排序
     * </p>
     * 
     * @param singleValueMap
     *            the name and value map
     * @return {@link TreeMap}
     * @since 1.4.0
     */
    public static Map<String, String[]> toArrayValueMap(Map<String, String> singleValueMap){
        if (Validator.isNullOrEmpty(singleValueMap)){
            return Collections.emptyMap();
        }

        Map<String, String[]> arrayValueMap = new TreeMap<String, String[]>();//返回 TreeMap 方便log 显示
        for (Map.Entry<String, String> entry : singleValueMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            arrayValueMap.put(key, new String[] { value });
        }
        return arrayValueMap;
    }

    // ************************************addParameter******************************************************

    /**
     * 添加参数,如果uri包含指定的参数名字,那么会被新的值替换.
     *
     * @param uriString
     *            the uri string
     * @param paramName
     *            添加的参数名称
     * @param parameValue
     *            添加的参数值
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 添加参数,如果uri包含指定的参数名字,那么会被新的值替换
     * @see #addParameter(URI, String, Object, String)
     */
    public static String addParameter(String uriString,String paramName,Object parameValue,String charsetType){
        Map<String, String> singleValueMap = new HashMap<String, String>();
        singleValueMap.put(paramName, "" + parameValue);
        return addParameterSingleValueMap(uriString, singleValueMap, charsetType);
    }

    /**
     * 添加参数,如果uri包含指定的参数名字,那么会被新的值替换.
     * 
     * @param uri
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动 增加?
     * @param paramName
     *            添加的参数名称
     * @param parameValue
     *            添加的参数值
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 添加参数,如果uri包含指定的参数名字,那么会被新的值替换
     * @see #addParameterArrayValueMap(URI, Map, String)
     */
    public static String addParameter(URI uri,String paramName,Object parameValue,String charsetType){
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(paramName, new String[] { "" + parameValue });
        return addParameterArrayValueMap(uri, map, charsetType);
    }

    /**
     * 添加参数,如果uri包含指定的参数名字,那么会被新的值替换.
     *
     * @param uriString
     *            the uri string
     * @param singleValueMap
     *            singleValueMap param name 和value 的键值对
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @see #addParameterArrayValueMap(String, Map, String)
     */
    public static String addParameterSingleValueMap(String uriString,Map<String, String> singleValueMap,String charsetType){
        Map<String, String[]> arrayValueMap = toArrayValueMap(singleValueMap);
        return addParameterArrayValueMap(uriString, arrayValueMap, charsetType);
    }

    /**
     * 添加参数,如果uri包含指定的参数名字,那么会被新的值替换.
     *
     * @param uriString
     *            the uri string
     * @param arrayValueMap
     *            the name and array value map
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 添加参数,如果uri包含指定的参数名字,那么会被新的值替换
     * @see #addParameterArrayValueMap(URI, Map, String)
     * @since 1.4.0
     */
    public static String addParameterArrayValueMap(String uriString,Map<String, String[]> arrayValueMap,String charsetType){
        //此处不能直接调用  addParameterArrayValueMap(URI uri 方法, 因为 uriString可能不是个符合规范的uri
        if (null == uriString){
            return StringUtils.EMPTY;
        }

        if (Validator.isNullOrEmpty(arrayValueMap)){
            return uriString;
        }
        String queryString = URIUtil.getQueryString(uriString);
        return addParameterArrayValueMap(uriString, queryString, arrayValueMap, charsetType);
    }

    /**
     * 添加参数.
     * 
     * <p>
     * 如果uri包含指定的参数名字,那么会被新的值替换，比如原来是a=1&a=2,现在使用a,[3,4]调用这个方法， 会返回a=3&a=4.
     * </p>
     * 
     * @param uri
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动 增加?
     * @param arrayValueMap
     *            singleValueMap 类似于 request.getParameterMap
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 添加参数,如果uri包含指定的参数名字,那么会被新的值替换
     */
    public static String addParameterArrayValueMap(URI uri,Map<String, String[]> arrayValueMap,String charsetType){
        if (null == uri){
            return StringUtils.EMPTY;
        }

        String uriString = uri.toString();
        if (Validator.isNullOrEmpty(arrayValueMap)){
            return uriString;
        }
        String queryString = uri.getRawQuery();
        return addParameterArrayValueMap(uriString, queryString, arrayValueMap, charsetType);
    }

    /**
     * 添加 parameter array value map.
     *
     * @param uriString
     *            the uri string
     * @param queryString
     *            the query
     * @param arrayValueMap
     *            the name and array value map
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @since 1.4.0
     */
    private static String addParameterArrayValueMap(
                    String uriString,
                    String queryString,
                    Map<String, String[]> arrayValueMap,
                    String charsetType){
        Map<String, String[]> singleValueMap = new HashMap<String, String[]>();
        singleValueMap.putAll(arrayValueMap);
        if (Validator.isNotNullOrEmpty(queryString)){
            // 注意 action before 可能带参数
            // "action": "https://202.6.215.230:8081/purchasing/purchase.do?action=loginRequest",
            // "fullEncodedUrl":"https://202.6.215.230:8081/purchasing/purchase.do?action=loginRequest?miscFee=0&descp=&klikPayCode=03BELAV220&transactionDate=23%2F03%2F2014+02%3A40%3A19&currency=IDR",
            Map<String, String[]> originalMap = toSafeArrayValueMap(queryString, null);
            singleValueMap.putAll(originalMap);
        }
        singleValueMap.putAll(arrayValueMap);
        return combineUrl(URIUtil.getFullPathWithoutQueryString(uriString), singleValueMap, charsetType);
    }

    // ********************************removeParameter*********************************************************************

    /**
     * 删除参数.
     *
     * @param uriString
     *            the uri string
     * @param paramName
     *            the param name
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @see #removeParameterList(String, List, String)
     */
    public static String removeParameter(String uriString,String paramName,String charsetType){
        if (null == paramName){
            return uriString;
        }
        List<String> list = new ArrayList<String>();
        list.add(paramName);
        return removeParameterList(uriString, list, charsetType);
    }

    /**
     * 删除参数.
     * 
     * @param uri
     *            the uri
     * @param paramName
     *            the param name
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @see #removeParameterList(URI, List, String)
     */
    public static String removeParameter(URI uri,String paramName,String charsetType){
        if (null == uri){
            return StringUtils.EMPTY;
        }
        if (Validator.isNullOrEmpty(paramName)){// 如果 paramNameList 是null 原样返回
            return uri.toString();
        }
        List<String> paramNameList = new ArrayList<String>();
        paramNameList.add(paramName);

        return removeParameterList(uri, paramNameList, charsetType);
    }

    /**
     * 删除参数.
     * 
     * @param uriString
     *            the url
     * @param paramNameList
     *            the param name list
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     */
    public static String removeParameterList(String uriString,List<String> paramNameList,String charsetType){
        if (null == uriString){
            return StringUtils.EMPTY;
        }
        if (Validator.isNullOrEmpty(paramNameList)){// 如果 paramNameList 是null 原样返回
            return uriString;
        }
        String queryString = URIUtil.getQueryString(uriString);
        return removeParameterList(uriString, queryString, paramNameList, charsetType);
    }

    /**
     * 删除参数.
     * 
     * @param uri
     *            the uri
     * @param paramNameList
     *            the param name list
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     */
    public static String removeParameterList(URI uri,List<String> paramNameList,String charsetType){
        if (null == uri){
            return StringUtils.EMPTY;
        }
        String uriString = uri.toString();
        if (Validator.isNullOrEmpty(paramNameList)){// 如果 paramNameList是null原样返回
            return uriString;
        }
        String queryString = uri.getRawQuery();// 返回此URI的原始查询组成部分。 URI的查询组成部分（如果定义了）只包含合法的 URI字符。
        return removeParameterList(uriString, queryString, paramNameList, charsetType);
    }

    /**
     * 删除 parameter list.
     *
     * @param uriString
     *            the uri string
     * @param queryString
     *            the query string
     * @param paramNameList
     *            the param name list
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @since 1.4.0
     */
    private static String removeParameterList(String uriString,String queryString,List<String> paramNameList,String charsetType){
        if (Validator.isNullOrEmpty(uriString)){
            return StringUtils.EMPTY;
        }
        if (Validator.isNullOrEmpty(queryString)){
            return uriString;// 不带参数原样返回
        }
        Map<String, String[]> singleValueMap = toSafeArrayValueMap(queryString, null);
        for (String paramName : paramNameList){
            singleValueMap.remove(paramName);
        }
        return combineUrl(URIUtil.getFullPathWithoutQueryString(uriString), singleValueMap, charsetType);
    }

    // **************************************retentionParams********************************************************

    /**
     * url里面仅保留指定的参数.
     *
     * @param uriString
     *            the uri string
     * @param paramNameList
     *            要保留的参数名字list
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @see #retentionParamList(URI, List, String)
     */
    public static String retentionParamList(String uriString,List<String> paramNameList,String charsetType){
        if (null == uriString){
            return StringUtils.EMPTY;
        }

        if (Validator.isNullOrEmpty(paramNameList)){ // 如果 paramNameList 是null 原样返回
            return uriString;
        }
        String queryString = URIUtil.getQueryString(uriString);
        return retentionParamList(uriString, queryString, paramNameList, charsetType);
    }

    /**
     * url里面仅保留指定的参数.
     * 
     * @param uri
     *            the uri
     * @param paramNameList
     *            要保留的参数名字list
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @see #toSafeArrayValueMap(String, String)
     */
    public static String retentionParamList(URI uri,List<String> paramNameList,String charsetType){
        if (null == uri){
            return StringUtils.EMPTY;
        }

        String uriString = uri.toString();

        if (Validator.isNullOrEmpty(paramNameList)){ // 如果 paramNameList 是null 原样返回
            return uriString;
        }
        String queryString = uri.getRawQuery(); // 返回此 URI的原始查询组成部分。 URI的查询组成部分（如果定义了）只包含合法的 URI字符。
        return retentionParamList(uriString, queryString, paramNameList, charsetType);
    }

    /**
     * 保留指定的参数.
     *
     * @param uriString
     *            the uri string
     * @param queryString
     *            the query string
     * @param paramNameList
     *            要保留的参数名字list
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @since 1.4.0
     */
    private static String retentionParamList(String uriString,String queryString,List<String> paramNameList,String charsetType){
        if (Validator.isNullOrEmpty(queryString)){
            return uriString; //不带参数原样返回
        }
        Map<String, String[]> originalArrayValueMap = toSafeArrayValueMap(queryString, null);

        Map<String, String[]> singleValueMap = new LinkedHashMap<String, String[]>();
        for (String paramName : paramNameList){
            singleValueMap.put(paramName, originalArrayValueMap.get(paramName));
        }
        return combineUrl(URIUtil.getFullPathWithoutQueryString(uriString), singleValueMap, charsetType);
    }

    //*********************************************************************************

    /**
     * 将{@code a=1&b=2}这样格式的数据转换成map (如果charsetType 不是null或者empty 返回安全的 key和value).
     *
     * @param queryString
     *            the query string
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the {@code map<string, string>}
     * @see #toSafeArrayValueMap(String, String)
     * @since 1.4.0
     */
    public static Map<String, String> toSingleValueMap(String queryString,String charsetType){
        Map<String, String[]> arrayValueMap = toSafeArrayValueMap(queryString, charsetType);
        return toSingleValueMap(arrayValueMap);
    }

    /**
     * 将{@code a=1&b=2}这样格式的数据转换成map (如果charsetType 不是null或者empty 返回安全的 key和value).
     *
     * @param queryString
     *            {@code a=1&b=2}类型的数据,支持{@code a=1&a=1}的形式， 返回map的值是数组
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 将{@code a=1&b=2}这样格式的数据转换成map (如果charsetType 不是null或者empty 返回安全的 key和value)
     * @since 1.4.0
     */
    public static Map<String, String[]> toSafeArrayValueMap(String queryString,String charsetType){
        if (Validator.isNullOrEmpty(queryString)){
            return Collections.emptyMap();
        }

        String[] nameAndValueArray = queryString.split(URIComponents.AMPERSAND);
        if (Validator.isNullOrEmpty(nameAndValueArray)){
            return Collections.emptyMap();
        }

        Map<String, String[]> map = new LinkedHashMap<String, String[]>();
        for (int i = 0, j = nameAndValueArray.length; i < j; ++i){
            String nameAndValue = nameAndValueArray[i];
            if (null != nameAndValue){
                String[] tempArray = nameAndValue.split("=", 2);

                String key = tempArray[0];
                String value = tempArray.length == 2 ? tempArray[1] : StringUtils.EMPTY;//有可能 参数中 只有名字没有值 或者值是空,处理的时候不能遗失掉

                if (Validator.isNotNullOrEmpty(charsetType)){
                    key = decodeAndEncode(key, charsetType);
                    value = decodeAndEncode(value, charsetType);
                }
                String[] valuesArrayInMap = map.get(key);

                List<String> list = null == valuesArrayInMap ? new ArrayList<String>() : ArrayUtil.toList(valuesArrayInMap);
                list.add(value);

                map.put(key, ConvertUtil.toArray(list, String.class));
            }
        }
        return map;
    }

    /**
     * 将map混合成 queryString.
     * 
     * <p>
     * 返回的queryString参数顺序,按照传入的singleValueMap key顺序排列,可以考虑传入 {@link TreeMap},{@link LinkedHashMap}等以适应不同业务的需求
     * </p>
     *
     * @param arrayValueMap
     *            类似于 request.getParamMap
     * @param charsetType
     *            {@link CharsetType} 何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return if isNullOrEmpty(appendMap) ,return ""
     * @see CharsetType
     * @see #toNaturalOrderingQueryString(Map)
     * @since 1.4.0
     */
    public static String toSafeQueryString(Map<String, String[]> arrayValueMap,String charsetType){
        if (Validator.isNullOrEmpty(arrayValueMap)){
            return StringUtils.EMPTY;
        }
        Map<String, String[]> safeArrayValueMap = toSafeArrayValueMap(arrayValueMap, charsetType);
        return joinArrayValueMap(safeArrayValueMap);
    }

    /**
     * To safe array value map.
     *
     * @param arrayValueMap
     *            the array value map
     * @param charsetType
     *            the charset type
     * @return if Validator.isNullOrEmpty(arrayValueMap) ,return emptyMap
     */
    private static Map<String, String[]> toSafeArrayValueMap(Map<String, String[]> arrayValueMap,String charsetType){
        if (Validator.isNullOrEmpty(arrayValueMap)){
            return Collections.emptyMap();
        }

        Map<String, String[]> safeArrayValueMap = new LinkedHashMap<String, String[]>();

        for (Map.Entry<String, String[]> entry : arrayValueMap.entrySet()){
            String key = entry.getKey();
            String[] paramValues = entry.getValue();
            if (Validator.isNullOrEmpty(paramValues)){
                LOGGER.warn("the param key:[{}] value is null", key);
                paramValues = ArrayUtils.EMPTY_STRING_ARRAY;//赋予 empty数组,为了下面的转换
            }

            if (Validator.isNotNullOrEmpty(charsetType)){
                key = decodeAndEncode(key, charsetType);
                List<String> paramValueList = new ArrayList<String>();
                for (String value : paramValues){
                    paramValueList.add(Validator.isNotNullOrEmpty(value) ? decodeAndEncode(value, charsetType) : StringUtils.EMPTY);
                }
                safeArrayValueMap.put(key, ConvertUtil.toArray(paramValueList, String.class));
            }else{
                safeArrayValueMap.put(key, paramValues);
            }
        }
        return safeArrayValueMap;
    }

    //*********************************************************************************************

    /**
     * 转成自然排序的字符串,生成待签名的字符串.
     * 
     * <p style="color:red">
     * 该方法不会执行encode操作,使用原生值进行拼接,一般用于和第三方对接数据, 比如支付宝
     * </p>
     * 
     * <ol>
     * <li>对数组里的每一个值从 a 到 z 的顺序排序，若遇到相同首字母，则看第二个字母， 以此类推。</li>
     * <li>排序完成之后，再把所有数组值以“&”字符连接起来</li>
     * <li>没有值的参数无需传递，也无需包含到待签名数据中.</li>
     * <li><span style="color:red">注意: 待签名数据应该是原生值而不是 encoding 之后的值</span></li>
     * </ol>
     * 
     * <h3>代码流程:</h3> <blockquote>
     * <ol>
     * <li>{@code if isNullOrEmpty(filePath)---->} return {@link StringUtils#EMPTY}</li>
     * <li>singleValueMap to naturalOrderingMap(TreeMap)</li>
     * <li>for naturalOrderingMap's entrySet(),join key and value use =,join each entry use &</li>
     * </ol>
     * </blockquote>
     *
     * @param singleValueMap
     *            用于拼接签名的参数
     * @return the string
     * @see #toSafeQueryString(Map, String)
     * @since 1.4.0
     */
    public static String toNaturalOrderingQueryString(Map<String, String> singleValueMap){
        if (Validator.isNullOrEmpty(singleValueMap)){
            return StringUtils.EMPTY;
        }
        Map<String, String> naturalParamsMapMap = new TreeMap<String, String>(singleValueMap);
        return joinSingleValueMap(naturalParamsMapMap);
    }

    /**
     * 将map链接起来.
     * 
     * <p>
     * 比如,参数名字 {@code paramName=name}, {@code paramValues 为 zhangfei},那么返回值是{@code name=zhangfei}
     * </p>
     *
     * @param singleValueMap
     *            the params map
     * @return the string
     * @see #toArrayValueMap(Map)
     * @see #joinArrayValueMap(Map)
     * @since 1.4.0
     */
    public static String joinSingleValueMap(Map<String, String> singleValueMap){
        if (Validator.isNullOrEmpty(singleValueMap)){
            return StringUtils.EMPTY;
        }
        Map<String, String[]> arrayValueMap = toArrayValueMap(singleValueMap);
        return joinArrayValueMap(arrayValueMap);
    }

    /**
     * 取到指定keys的value,连接起来.
     * 
     * <p>
     * 会按照includeKeys的顺序拼接
     * </p>
     *
     * @param singleValueMap
     *            the map
     * @param includeKeys
     *            包含的key
     * @return the mer data
     * @since 1.4.0
     */
    public static String joinValues(Map<String, String> singleValueMap,String...includeKeys){
        if (Validator.isNullOrEmpty(singleValueMap)){
            throw new NullPointerException("map can't be null/empty!");
        }
        StringBuilder sb = new StringBuilder();
        //有顺序的参数
        for (String key : includeKeys){
            String value = singleValueMap.get(key);

            //value转换, 注意:如果 value 是null ,StringBuilder将拼接 "null" 字符串, 详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(StringUtils.defaultString(value));
        }
        return sb.toString();
    }

    /**
     * Join array value map.
     * 
     * <h3>注意点:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>该方法不会执行encode操作,使用原生值进行拼接</li>
     * <li>按照传入的map key顺序进行排序,不会自行自动排序转换;如有有业务需求,先行排序完传入进来</li>
     * </ul>
     * </blockquote>
     *
     * @param arrayValueMap
     *            the array value map
     * @return the string
     * @see #joinParamNameAndValues(String, String[])
     * @since 1.4.0
     */
    public static String joinArrayValueMap(Map<String, String[]> arrayValueMap){
        if (Validator.isNullOrEmpty(arrayValueMap)){
            return StringUtils.EMPTY;
        }

        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : arrayValueMap.entrySet()){
            String key = entry.getKey();
            String[] paramValues = entry.getValue();
            sb.append(joinParamNameAndValues(key, paramValues));

            if (i != arrayValueMap.size() - 1){// 最后一个& 不拼接
                sb.append(URIComponents.AMPERSAND);
            }
            ++i;
        }
        return sb.toString();
    }

    /**
     * 将参数和多值链接起来.
     * 
     * <p>
     * 比如,参数名字 {@code paramName=name}, {@code paramValues 为 zhangfei,guanyu},那么返回值是{@code name=zhangfei&name=guanyu}
     * </p>
     *
     * @param paramName
     *            参数名字
     * @param paramValues
     *            参数多值
     * @return the string
     * @see java.lang.AbstractStringBuilder#append(String)
     * @see org.apache.commons.lang3.StringUtils#defaultString(String)
     * @see "org.springframework.web.servlet.view.RedirectView#appendQueryProperties(StringBuilder,Map, String)"
     * @since 1.4.0
     */
    private static String joinParamNameAndValues(String paramName,String[] paramValues){
        StringBuilder sb = new StringBuilder();
        for (int i = 0, j = paramValues.length; i < j; ++i){
            String value = paramValues[i];
            //value转换, 注意:如果 value 是null ,StringBuilder将拼接 "null" 字符串, 详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(StringUtils.defaultString(paramName)).append("=").append(StringUtils.defaultString(value));
            if (i != j - 1){// 最后一个& 不拼接
                sb.append(URIComponents.AMPERSAND);
            }
        }
        return sb.toString();
    }

    /**
     * 浏览器传递queryString()参数差别(浏览器兼容问题);chrome会将query进行 encoded再发送请求;而ie原封不动的发送.
     * 
     * <p>
     * 由于暂时不能辨别是否encoded过,所以先强制decode再encode;
     * </p>
     * 
     * <p>
     * 此处不能先转decode(query,charsetType),参数就是想传 =是转义符
     * </p>
     *
     * @param value
     *            the value
     * @param charsetType
     *            the charset type,pls use {@link CharsetType}
     * @return the string
     * @see <a
     *      href="http://stackoverflow.com/questions/15004593/java-request-getquerystring-value-different-between-chrome-and-ie-browser">java-request-getquerystring-value-different-between-chrome-and-ie-browser</a>
     * @since 1.4.0
     */
    private static String decodeAndEncode(String value,String charsetType){
        if (Validator.isNullOrEmpty(value)){
            return StringUtils.EMPTY;
        }
        if (Validator.isNullOrEmpty(charsetType)){
            return value;
        }
        return URIUtil.encode(URIUtil.decode(value, charsetType), charsetType);
    }

    /**
     * Combine url.
     *
     * @param beforePathWithoutQueryString
     *            the before path without query string
     * @param arrayValueMap
     *            the array value map
     * @param charsetType
     *            何种编码， {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @since 1.4.0
     */
    private static String combineUrl(String beforePathWithoutQueryString,Map<String, String[]> arrayValueMap,String charsetType){
        if (Validator.isNullOrEmpty(beforePathWithoutQueryString)){
            return StringUtils.EMPTY;
        }
        if (Validator.isNullOrEmpty(arrayValueMap)){//没有参数 直接return
            return beforePathWithoutQueryString;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(beforePathWithoutQueryString);
        sb.append(URIComponents.QUESTIONMARK);
        sb.append(toSafeQueryString(arrayValueMap, charsetType));

        return sb.toString();
    }
}
