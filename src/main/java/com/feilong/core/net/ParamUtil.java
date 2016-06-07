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
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.CharsetType;
import com.feilong.core.URIComponents;
import com.feilong.core.Validator;
import com.feilong.core.bean.ConvertUtil;

/**
 * 处理参数相关.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "org.springframework.web.util.UriComponentsBuilder"
 * @see "org.apache.http.client.utils.URIBuilder"
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
     * 返回的是 {@link LinkedHashMap},保证顺序和 参数 <code>arrayValueMap</code>顺序相同
     * </p>
     * 
     * <p>
     * 和该方法正好相反的是 {@link #toArrayValueMap(Map)}
     * </p>
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <String, String[]>}();
     * 
     * keyAndArrayMap.put("province", new String[] { "江苏省" });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap(keyAndArrayMap)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "province": "江苏省",
     * "city": "南通市"
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * <p>
     * 如果arrayValueMap其中有key的值是多值的数组,那么转换到新的map中的时候,value取第一个值,
     * </p>
     * 
     * <h3>示例2:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <String, String[]>}();
     * 
     * keyAndArrayMap.put("province", new String[] { "浙江省", "江苏省" });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap(keyAndArrayMap)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "province": "浙江省",
     * "city": "南通市"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param arrayValueMap
     *            the array value map
     * @return 如果参数<code>arrayValueMap</code>是null或者empty,那么return {@link Collections#emptyMap()},<br>
     *         如果<code>arrayValueMap</code>其中有key的值是多值的数组,那么转换到新的map中的时候,value取第一个值,<br>
     *         如果<code>arrayValueMap</code>其中有key的值是null或者empty,那么转换到新的map中的时候,value以 {@link StringUtils#EMPTY}替代
     * @since 1.4.0
     */
    public static Map<String, String> toSingleValueMap(Map<String, String[]> arrayValueMap){
        if (Validator.isNullOrEmpty(arrayValueMap)){
            return Collections.emptyMap();
        }

        //保证顺序和 参数 arrayValueMap 顺序相同
        Map<String, String> singleValueMap = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String[]> entry : arrayValueMap.entrySet()){
            String[] values = entry.getValue();
            singleValueMap.put(entry.getKey(), Validator.isNotNullOrEmpty(values) ? values[0] : StringUtils.EMPTY);
        }
        return singleValueMap;
    }

    /**
     * 将单值的参数map转成多值的参数map.
     * 
     * <p style="color:green">
     * 返回的是 {@link LinkedHashMap},保证顺序和 参数 <code>singleValueMap</code>顺序相同
     * </p>
     * 
     * <p>
     * 和该方法正好相反的是 {@link #toSingleValueMap(Map)}
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} singleValueMap = new LinkedHashMap{@code <String, String>}();
     * 
     * singleValueMap.put("province", "江苏省");
     * singleValueMap.put("city", "南通市");
     * 
     * LOGGER.info(JsonUtil.format(ParamUtil.toArrayValueMap(singleValueMap)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "province": ["江苏省"],
     * "city": ["南通市"]
     * }
     * </pre>
     * 
     * </blockquote>
     * 
     * @param singleValueMap
     *            the name and value map
     * @return 如果参数 <code>singleValueMap</code> 是null或者empty,那么return {@link Collections#emptyMap()}<br>
     *         否则 迭代 <code>singleValueMap</code> 将value转成数组,返回新的 <code>arrayValueMap</code>
     * @since 1.4.0
     */
    public static Map<String, String[]> toArrayValueMap(Map<String, String> singleValueMap){
        if (Validator.isNullOrEmpty(singleValueMap)){
            return Collections.emptyMap();
        }

        //保证顺序和 参数 singleValueMap顺序相同
        Map<String, String[]> arrayValueMap = new LinkedHashMap<String, String[]>();
        for (Map.Entry<String, String> entry : singleValueMap.entrySet()){
            arrayValueMap.put(entry.getKey(), new String[] { entry.getValue() });
        }
        return arrayValueMap;
    }

    // ************************************addParameter******************************************************

    /**
     * 添加参数,如果uri包含指定的参数名字,那么会被新的值替换.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String uri = "http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=";
     * String pageParamName = "label";
     * String prePageNo = "2-5-8-12";
     * LOGGER.info(ParamUtil.addParameter(uri, pageParamName, prePageNo, CharsetType.UTF8));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=&label=2-5-8-12
     * </pre>
     * 
     * </blockquote>
     *
     * @param uriString
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动 增加?
     * @param paramName
     *            添加的参数名称
     * @param parameValue
     *            添加的参数值,会被转成 {@link String} 进行拼接
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 添加参数,如果uri包含指定的参数名字,那么会被新的值替换
     * @see #addParameterSingleValueMap(String, Map, String)
     */
    public static String addParameter(String uriString,String paramName,Object parameValue,String charsetType){
        //反正这里就一个值, 所以没有必要声明为 LinkedHashMap
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
     *            添加的参数值,会被转成 {@link String} 进行拼接
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 添加参数,如果uri包含指定的参数名字,那么会被新的值替换
     * @see #addParameterArrayValueMap(URI, Map, String)
     */
    public static String addParameter(URI uri,String paramName,Object parameValue,String charsetType){
        //反正这里就一个值, 所以没有必要声明为 LinkedHashMap
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(paramName, new String[] { "" + parameValue });
        return addParameterArrayValueMap(uri, map, charsetType);
    }

    /**
     * 添加参数,如果uri包含指定的参数名字,那么会被新的值替换.
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String beforeUrl = "www.baidu.com";
     * Map{@code <String, String>} keyAndArrayMap = new LinkedHashMap{@code <String, String>}();
     * 
     * keyAndArrayMap.put("province", "江苏省");
     * keyAndArrayMap.put("city", "南通市");
     * 
     * LOGGER.info(ParamUtil.addParameterSingleValueMap(beforeUrl, keyAndArrayMap, CharsetType.UTF8));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * www.baidu.com?province=%E6%B1%9F%E8%8B%8F%E7%9C%81&city=%E5%8D%97%E9%80%9A%E5%B8%82
     * </pre>
     * 
     * </blockquote>
     * <h3>示例2:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String beforeUrl = "www.baidu.com?a=b";
     * Map{@code <String, String>} keyAndArrayMap = new LinkedHashMap{@code <String, String>}();
     * 
     * keyAndArrayMap.put("province", "江苏省");
     * keyAndArrayMap.put("city", "南通市");
     * 
     * LOGGER.info(ParamUtil.addParameterSingleValueMap(beforeUrl, keyAndArrayMap, CharsetType.UTF8));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * www.baidu.com?a=b&province=%E6%B1%9F%E8%8B%8F%E7%9C%81&city=%E5%8D%97%E9%80%9A%E5%B8%82
     * </pre>
     * 
     * </blockquote>
     *
     * @param uriString
     *            the uri string
     * @param singleValueMap
     *            singleValueMap param name 和value 的键值对
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
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
     * <p>
     * 如果 解析的<code>queryString</code> 不为空,那么会解析成map,此后再拼接 <code>arrayValueMap</code>;<br>
     * 内部使用 {@link LinkedHashMap},保持map元素顺序
     * </p>
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String beforeUrl = "www.baidu.com";
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <String, String[]>}();
     * 
     * keyAndArrayMap.put("receiver", new String[] { "鑫哥", "feilong" });
     * keyAndArrayMap.put("province", new String[] { "江苏省" });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, CharsetType.UTF8));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * www.baidu.com?receiver=%E9%91%AB%E5%93%A5&receiver=feilong&province=%E6%B1%9F%E8%8B%8F%E7%9C%81&city=%E5%8D%97%E9%80%9A%E5%B8%82
     * </pre>
     * 
     * </blockquote>
     * 
     * 
     * <h3>示例2:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String beforeUrl = "www.baidu.com?a=b";
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <String, String[]>}();
     * keyAndArrayMap.put("province", new String[] { "江苏省" });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, CharsetType.UTF8));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * www.baidu.com?a=b&province=%E6%B1%9F%E8%8B%8F%E7%9C%81&city=%E5%8D%97%E9%80%9A%E5%B8%82
     * </pre>
     * 
     * </blockquote>
     *
     * @param uriString
     *            the uri string
     * @param arrayValueMap
     *            the name and array value map
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
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
     * 如果uri包含指定的参数名字,那么会被新的值替换,比如原来是a=1&a=2,现在使用a,[3,4]调用这个方法,会返回a=3&a=4.
     * </p>
     * 
     * @param uri
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动 增加?
     * @param arrayValueMap
     *            singleValueMap 类似于 request.getParameterMap
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uri</code> 是null,返回 {@link StringUtils#EMPTY}<br>
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

    // ********************************removeParameter*********************************************************************

    /**
     * 删除参数.
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * String uriString = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
     * String pageParamName = "label";
     * LOGGER.info(ParamUtil.removeParameter(uriString, pageParamName, CharsetType.UTF8));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * http://www.feilong.com:8888/search.htm?keyword=%E4%B8%AD%E5%9B%BD&page=&categoryCode=2-5-3-11
     * </pre>
     * 
     * </blockquote>
     *
     * @param uriString
     *            the uri string
     * @param paramName
     *            参数名称
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>paramName</code> 是null或者empty,返回 <code>uriString</code><br>
     *         否则 构造 list ,调用 {@link #removeParameterList(String, List, String)}
     * @see #removeParameterList(String, List, String)
     */
    public static String removeParameter(String uriString,String paramName,String charsetType){
        if (Validator.isNullOrEmpty(uriString)){
            return StringUtils.EMPTY;
        }
        if (Validator.isNullOrEmpty(paramName)){
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
     *            参数名称
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uri</code> 是null,返回 {@link StringUtils#EMPTY}<br>
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
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * String uriString = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
     * List{@code <String>} paramNameList = new ArrayList{@code <String>}();
     * paramNameList.add("label");
     * paramNameList.add("keyword");
     * LOGGER.info(ParamUtil.removeParameterList(uriString, paramNameList, CharsetType.UTF8));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * http://www.feilong.com:8888/search.htm?page=&categoryCode=2-5-3-11
     * </pre>
     * 
     * </blockquote>
     * 
     * @param uriString
     *            the url
     * @param paramNameList
     *            参数名称 list
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     */
    public static String removeParameterList(String uriString,List<String> paramNameList,String charsetType){
        if (Validator.isNullOrEmpty(uriString)){
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
     *            参数名称 list
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uri</code> 是null,返回 {@link StringUtils#EMPTY}<br>
     */
    public static String removeParameterList(URI uri,List<String> paramNameList,String charsetType){
        if (null == uri){
            return StringUtils.EMPTY;
        }
        String uriString = uri.toString();
        if (Validator.isNullOrEmpty(paramNameList)){// 如果 paramNameList是null原样返回
            return uriString;
        }
        String queryString = uri.getRawQuery();// 返回此URI的原始查询组成部分. URI的查询组成部分(如果定义了)只包含合法的 URI字符.
        return removeParameterList(uri.toString(), queryString, paramNameList, charsetType);
    }

    /**
     * 删除 parameter list.
     *
     * @param uriString
     *            the uri string
     * @param queryString
     *            the query string
     * @param paramNameList
     *            参数名称 list
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>queryString</code> 是null或者empty,返回 <code>uriString</code><br>
     *         如果 <code>paramNameList</code> 是null或者empty,返回 <code>uriString</code>
     * @since 1.4.0
     */
    private static String removeParameterList(String uriString,String queryString,List<String> paramNameList,String charsetType){
        if (Validator.isNullOrEmpty(uriString)){
            return StringUtils.EMPTY;
        }
        if (Validator.isNullOrEmpty(queryString)){
            return uriString;// 不带参数原样返回
        }
        if (Validator.isNullOrEmpty(paramNameList)){
            return uriString;
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
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * 
     * String uriString = "http://www.feilong.com:8888/search.htm?keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller";
     * List{@code <String>} paramNameList = new ArrayList{@code <String>}();
     * paramNameList.add("label");
     * paramNameList.add("keyword");
     * LOGGER.info(ParamUtil.retentionParamList(uriString, paramNameList, CharsetType.UTF8));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * http://www.feilong.com:8888/search.htm?label=TopSeller&keyword=%E4%B8%AD%E5%9B%BD
     * </pre>
     * 
     * </blockquote>
     *
     * @param uriString
     *            the uri string
     * @param paramNameList
     *            要保留的参数名字list
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>paramNameList</code> 是null或者empty,返回 <code>uriString</code><br>
     *         否则调用 {@link #retentionParamList(URI, List, String)}
     * @see #retentionParamList(URI, List, String)
     */
    public static String retentionParamList(String uriString,List<String> paramNameList,String charsetType){
        if (Validator.isNullOrEmpty(uriString)){
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
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uri</code> 是null,返回 {@link StringUtils#EMPTY}<br>
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
        String queryString = uri.getRawQuery(); // 返回此 URI的原始查询组成部分. URI的查询组成部分(如果定义了)只包含合法的 URI字符.
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
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>queryString</code> 是null或者empty,返回 <code>uriString</code>
     * @since 1.4.0
     */
    private static String retentionParamList(String uriString,String queryString,List<String> paramNameList,String charsetType){
        if (Validator.isNullOrEmpty(uriString)){
            return StringUtils.EMPTY;
        }

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
     * 将<code>a=1&b=2</code>这样格式的数据转换成map (如果charsetType不是null或者empty 返回安全的 key和value).
     * 
     * <p>
     * 内部使用 {@link LinkedHashMap},map顺序依照 <code>queryString</code> 逗号分隔的顺序
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String queryString = "sec_id=MD5&format=xml&sign=cc945983476d615ca66cee41a883f6c1&v=2.0&req_data=%3Cauth_and_execute_req%3E%3Crequest_token%3E201511191eb5762bd0150ab33ed73976f7639893%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E&service=alipay.wap.auth.authAndExecute&partner=2088011438559510";
     * LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap(queryString, CharsetType.UTF8)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * {
     * "sec_id": "MD5",
     * "format": "xml",
     * "sign": "cc945983476d615ca66cee41a883f6c1",
     * "v": "2.0",
     * "req_data":
     * "%3Cauth_and_execute_req%3E%3Crequest_token%3E201511191eb5762bd0150ab33ed73976f7639893%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E",
     * "service": "alipay.wap.auth.authAndExecute",
     * "partner": "2088011438559510"
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param queryString
     *            the query string
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>queryString</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see #toSafeArrayValueMap(String, String)
     * @since 1.4.0
     */
    public static Map<String, String> toSingleValueMap(String queryString,String charsetType){
        Map<String, String[]> arrayValueMap = toSafeArrayValueMap(queryString, charsetType);
        return toSingleValueMap(arrayValueMap);
    }

    /**
     * 将<code>a=1&b=2</code>这样格式的数据转换成map (如果charsetType 不是null或者empty 返回安全的 key和value).
     * 
     * <p>
     * 内部使用 {@link LinkedHashMap},map顺序依照 <code>queryString</code> 逗号分隔的顺序
     * </p>
     * 
     * <p>
     * 解析方式:参数和参数之间是以 & 分隔 参数的key和value 是以 = 号分隔
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=1&b=2&a=5", CharsetType.UTF8)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     {"a": [
                 "1",
                 "5"
             ],
             "b": ["2"]
      }
     * </pre>
     * 
     * 参数和参数之间是以 & 分隔 参数的key和value 是以 = 号分隔
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(ParamUtil.toSafeArrayValueMap("a=&b=2&a", CharsetType.UTF8)));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     {"a": [
             "",
             ""
         ],
      "b": ["2"]
     }
     * </pre>
     * 
     * </blockquote>
     *
     * @param queryString
     *            <code>a=1&b=2</code>类型的数据,支持<code>a=1&a=1</code>的形式, 返回map的值是数组
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>queryString</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
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

        //使用 LinkedHashMap 保证元素的顺序
        Map<String, String[]> map = new LinkedHashMap<String, String[]>();
        for (int i = 0, j = nameAndValueArray.length; i < j; ++i){
            String nameAndValue = nameAndValueArray[i];
            if (null == nameAndValue){
                continue;
            }
            String[] tempArray = nameAndValue.split("=", 2);

            String key = tempArray[0];
            String value = tempArray.length == 2 ? tempArray[1] : StringUtils.EMPTY;//有可能 参数中 只有名字没有值 或者值是空,处理的时候不能遗失掉

            if (Validator.isNotNullOrEmpty(charsetType)){
                key = decodeAndEncode(key, charsetType);
                value = decodeAndEncode(value, charsetType);
            }
            String[] valuesArrayInMap = map.get(key);

            List<String> list = null == valuesArrayInMap ? new ArrayList<String>() : ConvertUtil.toList(valuesArrayInMap);
            list.add(value);

            map.put(key, ConvertUtil.toArray(list, String.class));
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
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * 对于以下的map,
     * 
     * <pre class="code">
     * Map{@code <String, String[]>} keyAndArrayMap = new HashMap{@code <String, String[]>}();
     * keyAndArrayMap.put("name", new String[] { "jim", "feilong", "鑫哥" });
     * keyAndArrayMap.put("age", new String[] { "18" });
     * keyAndArrayMap.put("love", new String[] { "sanguo" });
     * </pre>
     * 
     * 如果使用的是:
     * 
     * <pre class="code">
     * LOGGER.info(ParamUtil.toSafeQueryString(keyAndArrayMap, CharsetType.UTF8));
     * </pre>
     * 
     * 那么返回:
     * 
     * <pre class="code">
     * love=sanguo&age=18&name=jim&name=feilong&name=%E9%91%AB%E5%93%A5
     * </pre>
     * 
     * 如果使用的是:
     * 
     * <pre class="code">
     * LOGGER.info(ParamUtil.toSafeQueryString(keyAndArrayMap, null));
     * </pre>
     * 
     * 那么返回:
     * 
     * <pre class="code">
     * love=sanguo&age=18&name=jim&name=feilong&name=鑫哥
     * </pre>
     * 
     * </blockquote>
     *
     * @param arrayValueMap
     *            类似于 request.getParamMap
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>arrayValueMap</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     * @see #toQueryStringUseArrayValueMap(Map)
     * @since 1.4.0
     */
    public static String toSafeQueryString(Map<String, String[]> arrayValueMap,String charsetType){
        return Validator.isNullOrEmpty(arrayValueMap) ? StringUtils.EMPTY
                        : toQueryStringUseArrayValueMap(toSafeArrayValueMap(arrayValueMap, charsetType));
    }

    //*********************************************************************************************

    /**
     * 转成自然排序的字符串,生成待签名的字符串.
     * 
     * <p style="color:red">
     * 该方法不会执行encode操作,<b>使用原生值进行拼接</b>,一般用于和第三方对接数据, 比如支付宝
     * </p>
     * 
     * <ol>
     * <li>对数组里的每一个值从 a 到 z 的顺序排序,若遇到相同首字母,则看第二个字母, 以此类推.</li>
     * <li>排序完成之后,再把所有数组值以"&"字符连接起来</li>
     * <li>没有值的参数无需传递,也无需包含到待签名数据中.</li>
     * <li><span style="color:red">注意: 待签名数据应该是原生值而不是 encoding 之后的值</span></li>
     * </ol>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = new HashMap{@code <String, String>}();
     * map.put("service", "create_salesorder");
     * map.put("_input_charset", "gbk");
     * map.put("totalActual", "210.00");
     * map.put("receiver", "鑫哥");
     * map.put("province", "江苏省");
     * map.put("city", "南通市");
     * map.put("district", "通州区");
     * map.put("address", "江苏南通市通州区888组888号");
     * map.put(
     *                 "lines_data",
     *                 "[{\"extentionCode\":\"00887224869169\",\"count\":\"2\",\"unitPrice\":\"400.00\"},{\"extentionCode\":\"00887224869170\",\"count\":\"1\",\"unitPrice\":\"500.00\"}]");
     * LOGGER.info(ParamUtil.toNaturalOrderingQueryString(map));
     * </pre>
     * 
     * 返回 :
     * 
     * <pre class="code">
     * _input_charset=gbk&address=江苏南通市通州区888组888号&city=南通市&district=通州区&lines_data=[{"extentionCode":"00887224869169","count":"2",
     * "unitPrice":"400.00"},{"extentionCode":"00887224869170","count":"1","unitPrice":"500.00"}]&province=江苏省&receiver=鑫哥&service=
     * create_salesorder&totalActual=210.00
     * </pre>
     * 
     * </blockquote>
     * 
     * 
     * <h3>代码流程:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * <li>{@code if isNullOrEmpty(filePath)---->} return {@link StringUtils#EMPTY}</li>
     * <li>singleValueMap to naturalOrderingMap(TreeMap)</li>
     * <li>for naturalOrderingMap's entrySet(),join key and value use =,join each entry use {@code &}</li>
     * </ol>
     * </blockquote>
     *
     * @param singleValueMap
     *            用于拼接签名的参数
     * @return 如果 <code>singleValueMap</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则调用 {@link #toQueryStringUseSingleValueMap(Map)}
     * @see #toSafeQueryString(Map, String)
     * @since 1.4.0
     */
    public static String toNaturalOrderingQueryString(Map<String, String> singleValueMap){
        return Validator.isNullOrEmpty(singleValueMap) ? StringUtils.EMPTY
                        : toQueryStringUseSingleValueMap(new TreeMap<String, String>(singleValueMap));
    }

    /**
     * 将<code>singleValueMap</code>转成 queryString.
     * 
     * <p>
     * 只是简单的将map的key value 按照 <code>singleValueMap</code>的顺序 链接起来,最终格式类似于 url 的queryString,
     * 比如,参数名字<code>param Name=name</code>,<code>param Value=zhangfei</code>,那么返回值是 <code>name=zhangfei</code>
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} singleValueMap = new LinkedHashMap{@code <String, String>}();
     * 
     * singleValueMap.put("province", "江苏省");
     * singleValueMap.put("city", "南通市");
     * 
     * LOGGER.info(ParamUtil.joinSingleValueMap(singleValueMap));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * province=江苏省&city=南通市
     * </pre>
     * 
     * </blockquote>
     *
     * @param singleValueMap
     *            the params map
     * @return 如果<code>singleValueMap</code>是 null或者empty,返回 {@link StringUtils#EMPTY} <br>
     *         否则 将 <code>singleValueMap</code> 转成 {@link #toArrayValueMap(Map)},再调用 {@link #toQueryStringUseArrayValueMap(Map)}
     * @see #toArrayValueMap(Map)
     * @see #toQueryStringUseArrayValueMap(Map)
     * @see <a href="http://www.leveluplunch.com/java/examples/build-convert-map-to-query-string/">build-convert-map-to-query-string</a>
     * @since 1.5.5
     */
    public static String toQueryStringUseSingleValueMap(Map<String, String> singleValueMap){
        return Validator.isNullOrEmpty(singleValueMap) ? StringUtils.EMPTY : toQueryStringUseArrayValueMap(toArrayValueMap(singleValueMap));
    }

    /**
     * 只是简单的将map的key value 链接起来,最终格式类似于 url 的queryString.
     * 
     * <h3>注意点:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>该方法<span style="color:red">不会执行encode操作</span>,使用原生值进行拼接</li>
     * <li>按照传入的map key顺序进行排序,不会自行自动排序转换;如有有业务需求,先行排序完传入进来</li>
     * </ul>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <String, String[]>}();
     * 
     * keyAndArrayMap.put("province", new String[] { "江苏省", "浙江省" });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(ParamUtil.joinArrayValueMap(keyAndArrayMap));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * province=江苏省&province=浙江省&city=南通市
     * </pre>
     * 
     * </blockquote>
     *
     * @param arrayValueMap
     *            the array value map
     * @return 如果 <code>arrayValueMap</code> 是 Null或者Empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则循环 <code>arrayValueMap</code> 拼接成QueryString
     * @see #joinParamNameAndValues(String, String[])
     * @see <a href="http://www.leveluplunch.com/java/examples/build-convert-map-to-query-string/">build-convert-map-to-query-string</a>
     * @since 1.5.5
     */
    public static String toQueryStringUseArrayValueMap(Map<String, String[]> arrayValueMap){
        if (Validator.isNullOrEmpty(arrayValueMap)){
            return StringUtils.EMPTY;
        }

        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : arrayValueMap.entrySet()){
            sb.append(joinParamNameAndValues(entry.getKey(), entry.getValue()));

            if (i != arrayValueMap.size() - 1){// 最后一个& 不拼接
                sb.append(URIComponents.AMPERSAND);
            }
            ++i;
        }
        return sb.toString();
    }

    /**
     * 取到指定keys的value,连接起来(<span style="color:red">不使用任何连接符</span>).
     * 
     * <p>
     * 会按照includeKeys的顺序拼接,目前适用于 个别银行(比如汇付天下) 需要将值拼接起来加密
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = new HashMap{@code <String, String>}();
     * map.put("service", "create_salesorder");
     * map.put("paymentType", "unionpay_mobile");
     * 
     * LOGGER.info(ParamUtil.joinValues(map, "service", "paymentType"));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * create_salesorderunionpay_mobile
     * </pre>
     * 
     * </blockquote>
     *
     * @param singleValueMap
     *            the map
     * @param includeKeys
     *            包含的key
     * @return 如果 <code>singleValueMap</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>includeKeys</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则循环 <code>includeKeys</code>,依次从 <code>singleValueMap</code>中取到值,连接起来;<br>
     *         (如果 <code>singleValueMap</code>指定key的值是null,会使用{@link StringUtils#defaultString(String)} 转成{@link StringUtils#EMPTY}拼接)<br>
     * @see org.apache.commons.lang3.StringUtils#defaultString(String)
     * @since 1.5.5
     */
    public static String joinValuesOrderByIncludeKeys(Map<String, String> singleValueMap,String...includeKeys){
        Validate.notNull(singleValueMap, "singleValueMap can't be null!");

        if (Validator.isNullOrEmpty(includeKeys)){
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        //有顺序的参数
        for (String key : includeKeys){
            String value = singleValueMap.get(key);

            //value转换,注意:如果 value是null ,StringBuilder将拼接 "null" 字符串, 详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(StringUtils.defaultString(value));
        }
        return sb.toString();
    }
    //*******************************************************************************************

    /**
     * 添加 parameter array value map.
     * 
     * <p>
     * 如果 <code>queryString</code> 参数不为空,那么会解析成map,此后再拼接 <code>arrayValueMap</code>;<br>
     * 内部使用 {@link LinkedHashMap},保持map元素顺序
     * </p>
     * 
     * @param uriString
     *            the uri string
     * @param queryString
     *            the query
     * @param arrayValueMap
     *            the name and array value map
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
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
        Map<String, String[]> arrayParamValuesMap = new LinkedHashMap<String, String[]>(arrayValueMap.size());
        //先提取queryString map
        if (Validator.isNotNullOrEmpty(queryString)){
            // 注意 action before 可能带参数
            // "action": "https://202.6.215.230:8081/purchasing/purchase.do?action=loginRequest",
            // "fullEncodedUrl":"https://202.6.215.230:8081/purchasing/purchase.do?action=loginRequest?miscFee=0&descp=&klikPayCode=03BELAV220&transactionDate=23%2F03%2F2014+02%3A40%3A19&currency=IDR",
            Map<String, String[]> originalMap = toSafeArrayValueMap(queryString, null);
            arrayParamValuesMap.putAll(originalMap);
        }
        arrayParamValuesMap.putAll(arrayValueMap);
        return combineUrl(URIUtil.getFullPathWithoutQueryString(uriString), arrayParamValuesMap, charsetType);
    }

    /**
     * To safe array value map.
     * 
     * <p>
     * 内部使用 {@link LinkedHashMap},保持map元素顺序
     * </p>
     *
     * @param arrayValueMap
     *            the array value map
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>arrayValueMap</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     */
    private static Map<String, String[]> toSafeArrayValueMap(Map<String, String[]> arrayValueMap,String charsetType){
        if (Validator.isNullOrEmpty(arrayValueMap)){
            return Collections.emptyMap();
        }

        //内部使用 LinkedHashMap,保持map元素顺序
        Map<String, String[]> safeArrayValueMap = new LinkedHashMap<String, String[]>(arrayValueMap.size());

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

    /**
     * 将参数和多值链接起来.
     * 
     * <p>
     * 比如,参数名字 {@code paramName=name}, {@code paramValues 为 zhangfei,guanyu},那么返回值是{@code name=zhangfei&name=guanyu}
     * </p>
     * 
     * <h3>注意:</h3>
     * <blockquote>
     * <ol>
     * <li>paramName 和每个值 都会调用 {@link StringUtils#defaultString(String)}转换后才进行拼接</li>
     * </ol>
     * </blockquote>
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
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>value</code>是 null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果<code>charsetType</code>是 null或者empty,直接返回 <code>value</code><br>
     *         否则先 {@link URIUtil#decode(String, String)} 再 {@link URIUtil#encode(String, String)}值
     * @see <a
     *      href="http://stackoverflow.com/questions/15004593/java-request-getquerystring-value-different-between-chrome-and-ie-browser">
     *      java-request-getquerystring-value-different-between-chrome-and-ie-browser</a>
     * @since 1.4.0
     */
    private static String decodeAndEncode(String value,String charsetType){
        if (Validator.isNullOrEmpty(value)){
            return StringUtils.EMPTY;
        }
        return Validator.isNullOrEmpty(charsetType) ? value : URIUtil.encode(URIUtil.decode(value, charsetType), charsetType);
    }

    /**
     * 拼接url.
     *
     * @param beforePathWithoutQueryString
     *            the before path without query string
     * @param arrayValueMap
     *            the array value map
     * @param charsetType
     *            何种编码, {@link CharsetType}<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 (Validator.isNullOrEmpty(beforePathWithoutQueryString)),返回 {@link StringUtils#EMPTY}<br>
     *         如果 (Validator.isNullOrEmpty(arrayValueMap)),返回 beforePathWithoutQueryString
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
