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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.feilong.core.CharsetType;
import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.MapUtil;

import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * 处理参数相关.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "org.springframework.web.util.UriComponentsBuilder"
 * @see "org.apache.http.client.utils.URIBuilder"
 * @since 1.0.0
 */
public final class ParamExtensionUtil{

    /** Don't let anyone instantiate this class. */
    private ParamExtensionUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
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
     * @return 添加参数,如果uri包含指定的参数名字,那么会被新的值替换<br>
     *         如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     * @see #addParameterArrayValueMap(URI, Map, String)
     */
    public static String addParameter(URI uri,String paramName,Object parameValue,String charsetType){
        return addParameterArrayValueMap(uri, ConvertUtil.toMap(paramName, new String[] { "" + parameValue }), charsetType);
    }

    /**
     * 添加参数.
     * 
     * <p>
     * 如果uri包含指定的参数名字,那么会被新的值替换,比如原来是{@code a=1&a=2},现在使用a,[3,4]调用这个方法,会返回{@code a=3&a=4}.
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
        return null == uri ? StringUtils.EMPTY
                        : ParamUtil.addParameterArrayValueMap(uri.toString(), uri.getRawQuery(), arrayValueMap, charsetType);
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
     * String uriString = "http://www.feilong.com:8888/search.htm?{@code keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller"};
     * String pageParamName = "label";
     * LOGGER.info(ParamUtil.removeParameter(uriString, pageParamName, UTF8));
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * http://www.feilong.com:8888/search.htm?keyword={@code %E4%B8%AD%E5%9B%BD&page=&categoryCode=2-5-3-11}
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
     *         否则 构造 list,调用 {@link #removeParameterList(String, List, String)}
     * @see #removeParameterList(String, List, String)
     */
    public static String removeParameter(String uriString,String paramName,String charsetType){
        return removeParameterList(uriString, toList(paramName), charsetType);
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
        return removeParameterList(uri, toList(paramName), charsetType);
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
     * String uriString = "http://www.feilong.com:8888/search.htm?{@code keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller"};
     * List{@code <String>} paramNameList = new ArrayList{@code <String>}();
     * paramNameList.add("label");
     * paramNameList.add("keyword");
     * LOGGER.info(ParamUtil.removeParameterList(uriString, paramNameList, UTF8));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * http://www.feilong.com:8888/search.htm?{@code page=&categoryCode=2-5-3-11}
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
     *         如果 <code>paramNameList</code> 是null或者empty,返回 <code>uriString</code><br>
     */
    public static String removeParameterList(String uriString,List<String> paramNameList,String charsetType){
        return removeParameterList(uriString, URIUtil.getQueryString(uriString), paramNameList, charsetType);
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
     *         如果 <code>paramNameList</code> 是null或者empty,返回 <code>uri.toString()</code><br>
     */
    public static String removeParameterList(URI uri,List<String> paramNameList,String charsetType){
        return null == uri ? StringUtils.EMPTY : removeParameterList(uri.toString(), uri.getRawQuery(), paramNameList, charsetType);
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
        if (isNullOrEmpty(uriString)){
            return StringUtils.EMPTY;
        }
        if (isNullOrEmpty(queryString) || isNullOrEmpty(paramNameList)){
            return uriString;// 不带参数原样返回
        }
        Map<String, String[]> map = MapUtil
                        .removeKeys(ParamUtil.toSafeArrayValueMap(queryString, null), ConvertUtil.toArray(paramNameList, String.class));
        return ParamUtil.combineUrl(URIUtil.getFullPathWithoutQueryString(uriString), map, charsetType);
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
     * String uriString = "http://www.feilong.com:8888/search.htm?{@code keyword=中国&page=&categoryCode=2-5-3-11&label=TopSeller}";
     * List{@code <String>} paramNameList = new ArrayList{@code <String>}();
     * paramNameList.add("label");
     * paramNameList.add("keyword");
     * LOGGER.info(ParamUtil.retentionParamList(uriString, paramNameList, UTF8));
     * 
     * </pre>
     * 
     * 返回:
     * 
     * <pre class="code">
     * http://www.feilong.com:8888/search.htm?{@code label=TopSeller&keyword=%E4%B8%AD%E5%9B%BD}
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
     *         否则调用 {@link #retentionParamList(URI, List, String)}
     * @see #retentionParamList(URI, List, String)
     */
    public static String retentionParamList(String uriString,List<String> paramNameList,String charsetType){
        return retentionParamList(uriString, URIUtil.getQueryString(uriString), paramNameList, charsetType);
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
     *         否则调用 {@link #retentionParamList(String, String, List, String)}
     * @see #retentionParamList(String, String, List, String)
     */
    public static String retentionParamList(URI uri,List<String> paramNameList,String charsetType){
        return null == uri ? StringUtils.EMPTY : retentionParamList(uri.toString(), uri.getRawQuery(), paramNameList, charsetType);
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
        if (isNullOrEmpty(uriString)){
            return StringUtils.EMPTY;
        }
        if (isNullOrEmpty(queryString)){
            return uriString; //不带参数原样返回
        }
        Map<String, String[]> originalArrayValueMap = ParamUtil.toSafeArrayValueMap(queryString, null);
        Map<String, String[]> map = MapUtil.getSubMap(originalArrayValueMap, ConvertUtil.toArray(paramNameList, String.class));
        return ParamUtil.combineUrl(URIUtil.getFullPathWithoutQueryString(uriString), map, charsetType);
    }

    //*********************************************************************************
    /**
     * Sub.
     *
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param originalArrayValueMap
     *            the original array value map
     * @param paramNameList
     *            the param name list
     * @return the map< k, v>
     * @since 1.6.2
     */
    private static <K, V> Map<K, V> sub(Map<K, V> originalArrayValueMap,List<K> paramNameList){
        Map<K, V> singleValueMap = new LinkedHashMap<K, V>();
        for (K paramName : paramNameList){
            singleValueMap.put(paramName, originalArrayValueMap.get(paramName));
        }
        return singleValueMap;
    }
}
