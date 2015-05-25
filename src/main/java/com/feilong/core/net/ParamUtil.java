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
package com.feilong.core.net;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.Validator;

/**
 * 处理参数相关.
 * 
 * @author 金鑫 2010-4-15 下午04:01:29
 */
public final class ParamUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(ParamUtil.class);

    /** Don't let anyone instantiate this class. */
    private ParamUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 转成自然排序的字符串,生成待签名的字符串. <br>
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
     * <li>{@code if isNullOrEmpty(filePath)---->} return {@link org.apache.commons.lang3.StringUtils#EMPTY}</li>
     * <li>paramsMap to naturalOrderingMap(TreeMap)</li>
     * <li>for naturalOrderingMap's entrySet(),join key and value use =,join each entry use &</li>
     * </ol>
     * </blockquote>
     *
     * @param paramsMap
     *            用于拼接签名的参数
     * @return the string
     * @since 1.1.2
     */
    public static String toNaturalOrderingString(Map<String, String> paramsMap){
        if (Validator.isNullOrEmpty(paramsMap)){
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder();

        Map<String, String> naturalOrderingMap = new TreeMap<String, String>(paramsMap);

        int i = 0;
        int size = naturalOrderingMap.size();
        for (Map.Entry<String, String> entry : naturalOrderingMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();

            sb.append(key + "=" + value);

            // 最后一个不要拼接 &
            if (i != size - 1){
                sb.append("&");
            }
            ++i;
        }

        String naturalOrderingString = sb.toString();
        if (log.isDebugEnabled()){
            log.debug(naturalOrderingString);
        }
        return naturalOrderingString;
    }

    // ************************************addParameter******************************************************

    /**
     * 添加参数 加入含有该参数会替换掉.
     * 
     * @param url
     *            the url
     * @param paramName
     *            添加的参数名称
     * @param parameValue
     *            添加的参数值
     * @param charsetType
     *            the charset type
     * @return 添加参数 加入含有该参数会替换掉
     */
    public static String addParameter(String url,String paramName,Object parameValue,String charsetType){
        URI uri = URIUtil.create(url, charsetType);
        return addParameter(uri, paramName, parameValue, charsetType);
    }

    /**
     * 添加参数 加入含有该参数会替换掉.
     * 
     * @param url
     *            the url
     * @param nameAndValuesMap
     *            nameAndValueMap param name 和value 的键值对
     * @param charsetType
     *            the charset type
     * @return 添加参数 加入含有该参数会替换掉
     */
    public static String addParameterArrayMap(String url,Map<String, String[]> nameAndValuesMap,String charsetType){
        URI uri = URIUtil.create(url, charsetType);
        return addParameterArrayMap(uri, nameAndValuesMap, charsetType);
    }

    /**
     * 添加参数 加入含有该参数会替换掉.
     * 
     * @param url
     *            the url
     * @param nameAndValueMap
     *            nameAndValueMap param name 和value 的键值对
     * @param charsetType
     *            the charset type
     * @return the string
     */
    public static String addParameterValueMap(String url,Map<String, String> nameAndValueMap,String charsetType){
        Map<String, String[]> keyAndArrayMap = new HashMap<String, String[]>();

        if (Validator.isNotNullOrEmpty(nameAndValueMap)){
            for (Map.Entry<String, String> entry : nameAndValueMap.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                keyAndArrayMap.put(key, new String[] { value });
            }
        }
        return addParameterArrayMap(url, keyAndArrayMap, charsetType);
    }

    /**
     * 添加参数 加入含有该参数会替换掉.
     * 
     * @param uri
     *            URI 统一资源标识符 (URI),<br>
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动 增加?
     * @param paramName
     *            添加的参数名称
     * @param parameValue
     *            添加的参数值
     * @param charsetType
     *            编码
     * @return 添加参数 加入含有该参数会替换掉
     */
    public static String addParameter(URI uri,String paramName,Object parameValue,String charsetType){
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put(paramName, new String[] { parameValue.toString() });
        return addParameterArrayMap(uri, map, charsetType);
    }

    /**
     * 添加参数 <br>
     * 假如含有该参数会替换掉，比如原来是a=1&a=2,现在使用a,[3,4]调用这个方法， 会返回a=3&a=4.
     * 
     * @param uri
     *            URI 统一资源标识符 (URI),<br>
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动 增加?
     * @param nameAndValueMap
     *            nameAndValueMap 类似于 request.getParameterMap
     * @param charsetType
     *            编码
     * @return 添加参数 加入含有该参数会替换掉
     */
    public static String addParameterArrayMap(URI uri,Map<String, String[]> nameAndValueMap,String charsetType){
        if (null == uri){
            throw new IllegalArgumentException("uri can not be null!");
        }
        if (Validator.isNullOrEmpty(nameAndValueMap)){
            throw new IllegalArgumentException("nameAndValueMap can not be null!");
        }
        // ***********************************************************************
        String url = uri.toString();
        String before = URIUtil.getBeforePath(url);
        // ***********************************************************************
        // getQuery() 返回此 URI 的已解码的查询组成部分。
        // getRawQuery() 返回此 URI 的原始查询组成部分。 URI 的查询组成部分（如果定义了）只包含合法的 URI 字符。
        String query = uri.getRawQuery();
        // ***********************************************************************
        Map<String, String[]> map = new LinkedHashMap<String, String[]>();
        // 传入的url不带参数的情况
        if (Validator.isNullOrEmpty(query)){
            // nothing to do
        }else{
            Map<String, String[]> originalMap = URIUtil.parseQueryToArrayMap(query, null);
            map.putAll(originalMap);
        }
        map.putAll(nameAndValueMap);
        // **************************************************************
        return URIUtil.getEncodedUrlByArrayMap(before, map, charsetType);
    }

    // ********************************removeParameter*********************************************************************

    /**
     * 删除参数.
     * 
     * @param url
     *            the url
     * @param paramName
     *            the param name
     * @param charsetType
     *            编码
     * @return the string
     */
    public static String removeParameter(String url,String paramName,String charsetType){
        URI uri = URIUtil.create(url, charsetType);
        return removeParameter(uri, paramName, charsetType);
    }

    /**
     * 删除参数.
     * 
     * @param uri
     *            the uri
     * @param paramName
     *            the param name
     * @param charsetType
     *            编码
     * @return the string
     */
    private static String removeParameter(URI uri,String paramName,String charsetType){
        List<String> paramNameList = null;
        if (Validator.isNotNullOrEmpty(paramName)){
            paramNameList = new ArrayList<String>();
            paramNameList.add(paramName);
        }
        return removeParameterList(uri, paramNameList, charsetType);
    }

    /**
     * 删除参数.
     * 
     * @param url
     *            the url
     * @param paramNameList
     *            the param name list
     * @param charsetType
     *            编码
     * @return the string
     */
    public static String removeParameterList(String url,List<String> paramNameList,String charsetType){
        URI uri = URIUtil.create(url, charsetType);
        return removeParameterList(uri, paramNameList, charsetType);
    }

    /**
     * 删除参数.
     * 
     * @param uri
     *            the uri
     * @param paramNameList
     *            the param name list
     * @param charsetType
     *            编码
     * @return the string
     */
    public static String removeParameterList(URI uri,List<String> paramNameList,String charsetType){
        if (null == uri){
            return "";
        }
        String url = uri.toString();
        // 如果 paramNameList 是null 原样返回
        if (Validator.isNullOrEmpty(paramNameList)){
            return url;
        }
        // ***********************************************************************
        String before = URIUtil.getBeforePath(url);
        // ***********************************************************************
        // 返回此 URI 的原始查询组成部分。 URI 的查询组成部分（如果定义了）只包含合法的 URI 字符。
        String query = uri.getRawQuery();
        // ***********************************************************************
        // 传入的url不带参数的情况
        if (Validator.isNullOrEmpty(query)){
            // 不带参数原样返回
            return url;
        }else{
            Map<String, String[]> map = URIUtil.parseQueryToArrayMap(query, null);
            for (String paramName : paramNameList){
                map.remove(paramName);
            }
            return URIUtil.getEncodedUrlByArrayMap(before, map, charsetType);
        }
    }

    // **************************************retentionParams********************************************************

    /**
     * url里面仅保留 指定的参数.
     * 
     * @param url
     *            the url
     * @param paramNameList
     *            the param name list
     * @param charsetType
     *            编码
     * @return the string
     */
    public static String retentionParamList(String url,List<String> paramNameList,String charsetType){
        URI uri = URIUtil.create(url, charsetType);
        return retentionParamList(uri, paramNameList, charsetType);
    }

    /**
     * url里面仅保留 指定的参数.
     * 
     * @param uri
     *            the uri
     * @param paramNameList
     *            the param name list
     * @param charsetType
     *            编码
     * @return the string
     */
    public static String retentionParamList(URI uri,List<String> paramNameList,String charsetType){
        if (null == uri){
            return "";
        }else{
            String url = uri.toString();
            // 如果 paramNameList 是null 原样返回
            if (Validator.isNullOrEmpty(paramNameList)){
                return url;
            }
            String before = URIUtil.getBeforePath(url);
            // ***********************************************************************
            // 返回此 URI 的原始查询组成部分。 URI 的查询组成部分（如果定义了）只包含合法的 URI 字符。
            String query = uri.getRawQuery();
            // ***********************************************************************
            // 传入的url不带参数的情况
            if (Validator.isNullOrEmpty(query)){
                // 不带参数原样返回
                return url;
            }else{
                Map<String, String[]> map = new LinkedHashMap<String, String[]>();

                Map<String, String[]> originalMap = URIUtil.parseQueryToArrayMap(query, null);

                for (String paramName : paramNameList){
                    map.put(paramName, originalMap.get(paramName));
                }
                return URIUtil.getEncodedUrlByArrayMap(before, map, charsetType);
            }
        }
    }
}
