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

import static com.feilong.core.URIComponents.AMPERSAND;
import static com.feilong.core.URIComponents.QUESTIONMARK;
import static com.feilong.core.Validator.isNotNullOrEmpty;
import static com.feilong.core.Validator.isNullOrEmpty;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toMap;
import static com.feilong.core.net.URIUtil.decode;
import static com.feilong.core.net.URIUtil.encode;
import static com.feilong.core.util.CollectionsUtil.newArrayList;
import static com.feilong.core.util.MapUtil.newLinkedHashMap;
import static com.feilong.core.util.SortUtil.sortMapByKeyAsc;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.ArrayUtils.EMPTY_STRING_ARRAY;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.defaultString;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.CharsetType;
import com.feilong.core.lang.StringUtil;
import com.feilong.core.util.MapUtil;
import com.feilong.core.util.SortUtil;

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

    // -------------------------------addParameter-------------------------------

    /**
     * 给指定的<code>uriString</code>添加指定的参数 <code>paramName</code> 和值 <code>paramValue</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果原来的<code>uriString</code>没有指定的参数<code>paramName</code>,那么追加新的参数<code>paramName</code>和值<code>paramValue</code>.</li>
     * <li>如果原来的<code>uriString</code>有指定的参数<code>paramName</code>,那么会被新的值替换<code>paramValue</code>.</li>
     * <li>如果原来的<code>uriString</code>有指定的参数<code>paramName</code>,并且是多值类型(参数数组),那么多值参数中第一个会被新的值替换<code>paramValue</code>,其他的被丢弃.</li>
     * <li>如果原来的<code>uriString</code>有参数,不管是拼接还是替换都会保持参数的原始顺序.</li>
     * <li>如果<code>uriString</code>带有? 和参数,会先被截取,最后再拼接.</li>
     * <li>如果<code>uriString</code>不带?,则自动增加?</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String uri = "http://www.feilong.com:8888/esprit-frontend/search.htm?{@code keyword=%E6%81%A4&page=}";
     * String pageParamName = "label";
     * String prePageNo = "2-5-8-12";
     * LOGGER.info(ParamUtil.addParameter(uri, pageParamName, prePageNo, UTF8));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code http://www.feilong.com:8888/esprit-frontend/search.htm?keyword=%E6%81%A4&page=&label=2-5-8-12}
     * </pre>
     * 
     * </blockquote>
     *
     * @param uriString
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动增加?
     * @param paramName
     *            添加的参数名称
     * @param paramValue
     *            添加的参数值
     * @param charsetType
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>paramName</code> 是null,以 {@link StringUtils#EMPTY}替代<br>
     *         如果 <code>paramValue</code> 是null,以 {@link StringUtils#EMPTY}替代<br>
     * @see #addParameterSingleValueMap(String, Map, String)
     * @since 1.9.0 change paramValue type from Object to String
     */
    public static String addParameter(String uriString,String paramName,String paramValue,String charsetType){
        return addParameterSingleValueMap(uriString, toMap(paramName, paramValue), charsetType);
    }

    /**
     * 给指定的<code>uriString</code>添加指定的参数 <code>singleValueMap</code>.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果原来的<code>uriString</code>没有指定的参数<code>paramName</code>,那么循环<code>arrayValueMap</code>,追加新的参数<code>paramName</code>和值
     * <code>paramValue</code>.</li>
     * <li>如果原来的<code>uriString</code>有指定的参数<code>paramName</code>,那么会被新的值替换<code>paramValue</code>.</li>
     * <li>如果原来的<code>uriString</code>有指定的参数<code>paramName</code>,并且是多值类型(参数数组),那么多值参数中第一个会被新的值替换<code>paramValue</code>,其他的被丢弃.</li>
     * <li>如果原来的<code>uriString</code>有参数,不管是拼接还是替换都会保持参数的原始顺序.</li>
     * <li>如果<code>uriString</code>带有? 和参数,会先被截取,最后再拼接.</li>
     * <li>如果<code>uriString</code>不带?,则自动增加?</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String beforeUrl = "www.baidu.com";
     * Map{@code <String, String>} keyAndArrayMap = new LinkedHashMap{@code <>}();
     * 
     * keyAndArrayMap.put("province", "江苏省");
     * keyAndArrayMap.put("city", "南通市");
     * 
     * LOGGER.info(ParamUtil.addParameterSingleValueMap(beforeUrl, keyAndArrayMap, UTF8));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code www.baidu.com?province=%E6%B1%9F%E8%8B%8F%E7%9C%81&city=%E5%8D%97%E9%80%9A%E5%B8%82}
     * </pre>
     * 
     * </blockquote>
     * <h3>示例2:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String beforeUrl = "www.baidu.com?a=b";
     * Map{@code <String, String>} keyAndArrayMap = new LinkedHashMap{@code <>}();
     * 
     * keyAndArrayMap.put("province", "江苏省");
     * keyAndArrayMap.put("city", "南通市");
     * 
     * LOGGER.info(ParamUtil.addParameterSingleValueMap(beforeUrl, keyAndArrayMap, UTF8));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code www.baidu.com?a=b&province=%E6%B1%9F%E8%8B%8F%E7%9C%81&city=%E5%8D%97%E9%80%9A%E5%B8%82}
     * </pre>
     * 
     * </blockquote>
     *
     * @param uriString
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动增加?
     * @param singleValueMap
     *            singleValueMap param name 和value 的键值对
     * @param charsetType
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>singleValueMap</code> 是null,当作empty map处理<br>
     * @see #addParameterArrayValueMap(String, Map, String)
     */
    public static String addParameterSingleValueMap(String uriString,Map<String, String> singleValueMap,String charsetType){
        return addParameterArrayValueMap(uriString, MapUtil.toArrayValueMap(singleValueMap), charsetType);
    }

    /**
     * 给指定的 <code>uriString</code>添加参数 <code>arrayValueMap</code>,如果uri包含指定的参数名字,那么会被新的值替换.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果原来的<code>uriString</code>没有指定的参数<code>paramName</code>,那么循环<code>arrayValueMap</code>,追加新的参数<code>paramName</code>和值
     * <code>paramValue</code>.</li>
     * <li>如果原来的<code>uriString</code>有指定的参数<code>paramName</code>,那么会被新的值替换<code>paramValue</code>.</li>
     * <li>如果原来的<code>uriString</code>有指定的参数<code>paramName</code>,并且是多值类型(参数数组),那么多值参数中第一个会被新的值替换<code>paramValue</code>,其他的被丢弃.</li>
     * <li>如果原来的<code>uriString</code>有参数,不管是拼接还是替换都会保持参数的原始顺序.</li>
     * <li>如果<code>uriString</code>带有? 和参数,会先被截取,最后再拼接.</li>
     * <li>如果<code>uriString</code>不带?,则自动增加?</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String beforeUrl = "www.baidu.com";
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <>}();
     * 
     * keyAndArrayMap.put("receiver", new String[] { "鑫哥", "feilong" });
     * keyAndArrayMap.put("province", new String[] { "江苏省" });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code www.baidu.com?receiver=%E9%91%AB%E5%93%A5&receiver=feilong&province=%E6%B1%9F%E8%8B%8F%E7%9C%81&city=%E5%8D%97%E9%80%9A%E5%B8%82}
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
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <>}();
     * keyAndArrayMap.put("province", new String[] { "江苏省" });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(ParamUtil.addParameterArrayValueMap(beforeUrl, keyAndArrayMap, UTF8));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code www.baidu.com?a=b&province=%E6%B1%9F%E8%8B%8F%E7%9C%81&city=%E5%8D%97%E9%80%9A%E5%B8%82}
     * </pre>
     * 
     * </blockquote>
     *
     * @param uriString
     *            如果带有? 和参数,会先被截取,最后再拼接,<br>
     *            如果不带?,则自动增加?
     * @param arrayValueMap
     *            the name and array value map
     * @param charsetType
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>uriString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果 <code>arrayValueMap</code> 是null,当作empty map处理<br>
     *         如果 <code>charsetType</code> 是null或者empty,那么参数部分原样拼接处理,自行处理兼容性问题<br>
     * @see #addParameterArrayValueMap(String, String, Map, String)
     * @since 1.4.0
     */
    public static String addParameterArrayValueMap(String uriString,Map<String, String[]> arrayValueMap,String charsetType){
        return addParameterArrayValueMap(uriString, URIUtil.getQueryString(uriString), arrayValueMap, charsetType);
    }

    //---------------------------------------------------------------

    /**
     * 将{@code a=1&b=2}这样格式的 <code>queryString</code>数据转换成map.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>如果参数里面有相同名字的参数,那么转换的时候取第一个值</li>
     * <li>内部使用 {@link LinkedHashMap},map顺序依照 <code>queryString</code> 分隔的顺序</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * String queryString = "{@code sec_id=MD5&format=xml&sign=cc945983476d615ca66cee41a883f6c1&v=2.0&req_data=%3Cauth_and_execute_req%3E%3Crequest_token%3E201511191eb5762bd0150ab33ed73976f7639893%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E&service=alipay.wap.auth.authAndExecute&partner=2088011438559510}";
     * LOGGER.info(JsonUtil.format(ParamUtil.toSingleValueMap(queryString, UTF8)));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {
     * "sec_id": "MD5",
     * "format": "xml",
     * "sign": "cc945983476d615ca66cee41a883f6c1",
     * "v": "2.0",
     * "req_data":"%3Cauth_and_execute_req%3E%3Crequest_token%3E201511191eb5762bd0150ab33ed73976f7639893%3C%2Frequest_token%3E%3C%2Fauth_and_execute_req%3E",
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
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>queryString</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see #toSafeArrayValueMap(String, String)
     * @since 1.4.0
     */
    public static Map<String, String> toSingleValueMap(String queryString,String charsetType){
        return MapUtil.toSingleValueMap(toSafeArrayValueMap(queryString, charsetType));
    }

    /**
     * 将{@code a=1&b=2}这样格式的数据转换成map (如果<code>charsetType</code> 不是null或者empty 返回安全的 key和value).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>内部使用 {@link LinkedHashMap},map顺序依照 <code>queryString</code> 逗号分隔的顺序</li>
     * <li>解析方式:参数和参数之间是以 {@code &} 分隔, 参数的key和value 是以 = 号分隔</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(ParamUtil.toSafeArrayValueMap("{@code a=1&b=2&a=5}", UTF8)));
     * </pre>
     * 
     * <b>返回:</b>
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
     * <hr>
     * 
     * <pre class="code">
     * LOGGER.info(JsonUtil.format(ParamUtil.toSafeArrayValueMap("{@code a=&b=2&a}", UTF8)));
     * </pre>
     * 
     * <b>返回:</b>
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
     *            {@code a=1&b=2}类型的数据,支持{@code a=1&a=1}的形式, 返回map的值是数组
     * @param charsetType
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>queryString</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     * @see org.apache.commons.lang3.ArrayUtils#add(Object[], Object)
     * @see com.feilong.core.lang.StringUtil#split(String, String)
     * @since 1.4.0
     */
    static Map<String, String[]> toSafeArrayValueMap(String queryString,String charsetType){
        if (isNullOrEmpty(queryString)){
            return emptyMap();
        }

        String[] nameAndValueArray = StringUtil.split(queryString, AMPERSAND);
        int length = nameAndValueArray.length;

        Map<String, String[]> safeArrayValueMap = newLinkedHashMap(length);//使用 LinkedHashMap 保证元素的顺序
        for (int i = 0; i < length; ++i){
            String[] tempArray = nameAndValueArray[i].split("=", 2);

            String key = decodeAndEncode(tempArray[0], charsetType);
            String value = tempArray.length == 2 ? tempArray[1] : EMPTY;//有可能参数中,只有名字没有值或者值是空,处理的时候不能遗失掉
            value = decodeAndEncode(value, charsetType);

            safeArrayValueMap.put(key, ArrayUtils.add(safeArrayValueMap.get(key), value));
        }
        return safeArrayValueMap;
    }

    //---------------------------------------------------------------------------------------------

    /**
     * 将 <code>singleValueMap</code> 转成<code>自然排序</code>, 然后将key和value直接拼接成字符串(<span style="color:red">不使用 {@code =} 和{@code &} 分隔</span>).
     * 
     * <p>
     * 已知,适用于 网易易盾验证码
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = newHashMap();
     * map.put("service", "create_salesorder");
     * map.put("_input_charset", "gbk");
     * map.put("totalActual", "210.00");
     * map.put("address", "江苏南通市通州区888组888号");
     * LOGGER.debug(ParamUtil.toNaturalOrderingKeyJoinValue(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code _input_charsetgbkaddress江苏南通市通州区888组888号servicecreate_salesordertotalActual210.00}
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>规则:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * <li>首先将<code>singleValueMap</code> 使用 {@link SortUtil#sortMapByKeyAsc(Map)} 进行排序,</li>
     * <li>然后将map的key和value 直接连接</li>
     * </ol>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * 
     * <li>常用于和第三方对接数据(比如网易易盾验证码,生成 <b>待签名的字符串</b>)</li>
     * <li>该方法不会执行encode操作,<b>使用原生值进行拼接</b></li>
     * 
     * <li>
     * <h4>对于 null key或者null value的处理:</h4>
     * 
     * <blockquote>
     * <p>
     * 如果 <code>singleValueMap</code> 中,<br>
     * 如果有 <code>key</code> 是<code>null</code>,那么会使用 {@link StringUtils#EMPTY} 进行拼接;<br>
     * 如果有 <code>value</code> 是 <code>null</code>,那么会使用 {@link StringUtils#EMPTY} 进行拼接
     * </p>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = newHashMap();
     * map.put("totalActual", <span style="color:red">null</span>);
     * map.put(<span style="color:red">null</span>, "create_salesorder");
     * map.put("province", "江苏省");
     * 
     * LOGGER.debug(ParamUtil.toNaturalOrderingKeyJoinValue(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code create_salesorderprovince江苏省totalActual}
     * </pre>
     * 
     * </blockquote>
     * </li>
     * 
     * </ol>
     * </blockquote>
     * 
     * @param singleValueMap
     *            用于拼接签名的参数
     * @return 如果 <code>singleValueMap</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则将<code>singleValueMap</code>排序之后,循环直接拼接key和value
     * @since 1.10.6
     */
    public static String toNaturalOrderingKeyJoinValue(Map<String, String> singleValueMap){
        if (isNullOrEmpty(singleValueMap)){
            return EMPTY;
        }

        //---------------------------------------------------------------
        Map<String, String> sortMapByKeyAsc = sortMapByKeyAsc(singleValueMap);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : sortMapByKeyAsc.entrySet()){//有顺序的参数
            String key = entry.getKey();
            String value = entry.getValue();
            //注意:如果 value是null,StringBuilder将拼接 "null" 字符串, 详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(defaultString(key));
            sb.append(defaultString(value));
        }
        return sb.toString();
    }

    /**
     * 将 <code>singleValueMap</code> 转成<code>自然排序</code>, 然后仅将value直接拼接成字符串(<span style="color:red">不使用 {@code =} 和{@code &} 分隔</span>).
     * 
     * <p>
     * 已知,适用于 银联大华捷通
     * </p>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = newHashMap();
     * map.put("service", "create_salesorder");
     * map.put("_input_charset", "gbk");
     * map.put("totalActual", "210.00");
     * map.put("address", "江苏南通市通州区888组888号");
     * LOGGER.debug(ParamUtil.toNaturalOrderingJoinValue(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code gbk江苏南通市通州区888组888号create_salesorder210.00}
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>规则:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * <li>首先将<code>singleValueMap</code> 使用 {@link SortUtil#sortMapByKeyAsc(Map)} 进行排序,</li>
     * <li>然后将map的value 直接连接</li>
     * </ol>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * 
     * <li>常用于和第三方对接数据(比如银联大华捷通,生成 <b>待签名的字符串</b>)</li>
     * <li>该方法不会执行encode操作,<b>使用原生值进行拼接</b></li>
     * 
     * <li>
     * <h4>对于 null key或者null value的处理:</h4>
     * 
     * <blockquote>
     * <p>
     * 如果 <code>singleValueMap</code> 中,<br>
     * 如果有 <code>value</code> 是 <code>null</code>,那么会使用 {@link StringUtils#EMPTY} 进行拼接
     * </p>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = newHashMap();
     * map.put("totalActual", <span style="color:red">null</span>);
     * map.put("province", "江苏省");
     * 
     * LOGGER.debug(ParamUtil.toNaturalOrderingKeyJoinValue(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code 江苏省}
     * </pre>
     * 
     * </blockquote>
     * </li>
     * 
     * </ol>
     * </blockquote>
     * 
     * @param singleValueMap
     *            用于拼接签名的参数
     * @return 如果 <code>singleValueMap</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则将<code>singleValueMap</code>排序之后,循环直接拼接value
     * @since 2.0.1
     */
    public static String toNaturalOrderingJoinValue(Map<String, String> singleValueMap){
        if (isNullOrEmpty(singleValueMap)){
            return EMPTY;
        }

        //---------------------------------------------------------------
        Map<String, String> sortMapByKeyAsc = sortMapByKeyAsc(singleValueMap);

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : sortMapByKeyAsc.entrySet()){//有顺序的参数
            String value = entry.getValue();
            //注意:如果 value是null,StringBuilder将拼接 "null" 字符串, 详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(defaultString(value));
        }
        return sb.toString();
    }

    /**
     * 将 <code>singleValueMap</code> 转成<code>自然排序</code>的 <code>queryString</code> 字符串.
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = newHashMap();
     * map.put("service", "create_salesorder");
     * map.put("_input_charset", "gbk");
     * map.put("totalActual", "210.00");
     * map.put("address", "江苏南通市通州区888组888号");
     * LOGGER.debug(ParamUtil.toNaturalOrderingQueryString(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code _input_charset=gbk&address=江苏南通市通州区888组888号&service=create_salesorder&totalActual=210.00}
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>规则:</h3>
     * 
     * <blockquote>
     * 
     * <ol>
     * <li>首先将<code>singleValueMap</code> 使用 {@link SortUtil#sortMapByKeyAsc(Map)} 进行排序,</li>
     * <li>然后将map的key和value 使用= 符号 连接,不同的entry之间再使用{@code &} 符号进行连接,最终格式类似于 url 的queryString</li>
     * </ol>
     * 
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * 
     * <li>常用于和第三方对接数据(比如支付宝,生成 <b>待签名的字符串</b>)</li>
     * <li>该方法不会执行encode操作,<b>使用原生值进行拼接</b></li>
     * 
     * <li>
     * <h4>对于 null key或者null value的处理:</h4>
     * 
     * <blockquote>
     * <p>
     * 如果 <code>singleValueMap</code> 中,<br>
     * 如果有 <code>key</code> 是<code>null</code>,那么会使用 {@link StringUtils#EMPTY} 进行拼接;<br>
     * 如果有 <code>value</code> 是 <code>null</code>,那么会使用 {@link StringUtils#EMPTY} 进行拼接
     * </p>
     * 
     * <h4>示例:</h4>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = newHashMap();
     * map.put("totalActual", <span style="color:red">null</span>);
     * map.put(<span style="color:red">null</span>, "create_salesorder");
     * map.put("province", "江苏省");
     * 
     * LOGGER.debug(ParamUtil.toNaturalOrderingQueryString(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code =create_salesorder&province=江苏省&totalActual=}
     * </pre>
     * 
     * </blockquote>
     * </li>
     * 
     * </ol>
     * </blockquote>
     * 
     * @param singleValueMap
     *            用于拼接签名的参数
     * @return 如果 <code>singleValueMap</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则将<code>singleValueMap</code>排序之后,调用 {@link #toQueryStringUseSingleValueMap(Map)}
     * @since 1.4.0
     */
    public static String toNaturalOrderingQueryString(Map<String, String> singleValueMap){
        return isNullOrEmpty(singleValueMap) ? EMPTY : toQueryStringUseSingleValueMap(sortMapByKeyAsc(singleValueMap));
    }

    /**
     * 将<code>singleValueMap</code>转成 queryString.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>
     * 只是简单的将map的key value 按照 <code>singleValueMap</code>的顺序 连接起来,最终格式类似于 url 的queryString,<br>
     * 比如,参数名字<code>param Name=name</code>,<code>param Value=zhangfei</code>,那么返回值是 <code>name=zhangfei</code>
     * </li>
     * <li>如果<code>singleValueMap</code>有key 是null,将使用 {@link StringUtils#EMPTY} 进行拼接</li>
     * <li>如果<code>singleValueMap</code>有value 是null,将使用 {@link StringUtils#EMPTY} 进行拼接</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} singleValueMap = new LinkedHashMap{@code <>}();
     * 
     * singleValueMap.put("province", "江苏省");
     * singleValueMap.put("city", "南通市");
     * 
     * LOGGER.info(ParamUtil.joinSingleValueMap(singleValueMap));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code province=江苏省&city=南通市}
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>示例2:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = new LinkedHashMap{@code <>}();
     * map.put(null, null);
     * map.put("a", "");
     * map.put("b", null);
     * map.put("c", "jim");
     * 
     * LOGGER.info(ParamUtil.toQueryStringUseSingleValueMap(map));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code =&a=&b=&c=jim}
     * </pre>
     * 
     * </blockquote>
     *
     * @param singleValueMap
     *            the params map
     * @return 如果<code>singleValueMap</code>是 null或者empty,返回 {@link StringUtils#EMPTY} <br>
     *         否则将 <code>singleValueMap</code> 转成 {@link MapUtil#toArrayValueMap(Map)},再调用 {@link #toQueryStringUseArrayValueMap(Map)}
     * @see MapUtil#toArrayValueMap(Map)
     * @see #toQueryStringUseArrayValueMap(Map)
     * @see <a href="http://www.leveluplunch.com/java/examples/build-convert-map-to-query-string/">build-convert-map-to-query-string</a>
     * @since 1.5.5
     */
    public static String toQueryStringUseSingleValueMap(Map<String, String> singleValueMap){
        return toQueryStringUseArrayValueMap(MapUtil.toArrayValueMap(singleValueMap));
    }

    /**
     * 只是简单的将map的key value 连接起来,最终格式类似于 url 的queryString.
     * 
     * <h3>注意:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>该方法<span style="color:red">不会执行encode操作</span>,使用原生值进行拼接</li>
     * <li>按照传入的map key顺序进行排序,不会自行自动排序转换;如有有业务需求,先行排序完传入进来</li>
     * <li>如果<code>arrayValueMap</code>有key 是null,将使用 {@link StringUtils#EMPTY} 进行拼接</li>
     * <li>如果<code>arrayValueMap</code>有value的元素是null,将使用 {@link StringUtils#EMPTY} 进行拼接</li>
     * </ul>
     * </blockquote>
     * 
     * <h3>示例1:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <>}();
     * 
     * keyAndArrayMap.put("province", new String[] { "江苏省", "浙江省" });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(ParamUtil.joinArrayValueMap(keyAndArrayMap));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code province=江苏省&province=浙江省&city=南通市}
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>示例2:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * Map{@code <String, String[]>} keyAndArrayMap = new LinkedHashMap{@code <>}();
     * 
     * keyAndArrayMap.put("province", new String[] { "江苏省", <span style="color:red">null</span> });
     * keyAndArrayMap.put("city", new String[] { "南通市" });
     * LOGGER.info(ParamUtil.joinArrayValueMap(keyAndArrayMap));
     * </pre>
     * 
     * <b>返回:</b>
     * 
     * <pre class="code">
     * {@code province=江苏省&province=&city=南通市}
     * </pre>
     * 
     * </blockquote>
     *
     * @param arrayValueMap
     *            the array value map
     * @return 如果 <code>arrayValueMap</code> 是 null或者Empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则循环 <code>arrayValueMap</code> 拼接成QueryString
     * @see <a href="http://www.leveluplunch.com/java/examples/build-convert-map-to-query-string/">build-convert-map-to-query-string</a>
     * @since 1.5.5
     */
    public static String toQueryStringUseArrayValueMap(Map<String, String[]> arrayValueMap){
        if (isNullOrEmpty(arrayValueMap)){
            return EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : arrayValueMap.entrySet()){
            sb.append(joinParamNameAndValues(entry.getKey(), entry.getValue()));
            sb.append(AMPERSAND); //放心大胆的拼接 &, 不判断是否是最后一个,最后会截取
        }
        return StringUtil.substringWithoutLast(sb, AMPERSAND);
    }

    /**
     * 将参数名称<code>paramName</code>和多值 <code>paramValues</code> 连接起来.
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>比如,参数名字 {@code paramName=name},{@code paramValues 为 zhangfei,guanyu},那么返回值是{@code name=zhangfei&name=guanyu}</li>
     * <li>paramName和每个值都会调用 {@link StringUtils#defaultString(String)}转换后才进行拼接</li>
     * </ol>
     * </blockquote>
     *
     * @param paramName
     *            参数名字
     * @param paramValues
     *            参数多值
     * @return 如果<code>paramValues</code>是null,那么直接返回 <code>paramName=</code>
     * @see java.lang.AbstractStringBuilder#append(String)
     * @see org.apache.commons.lang3.StringUtils#defaultString(String)
     * @see "org.springframework.web.servlet.view.RedirectView#appendQueryProperties(StringBuilder,Map, String)"
     * @since 1.4.0
     */
    private static String joinParamNameAndValues(String paramName,String[] paramValues){
        //see https://github.com/venusdrogon/feilong-core/issues/372
        if (null == paramValues){
            return defaultString(paramName) + "=";
        }
        StringBuilder sb = new StringBuilder();

        for (String paramValue : paramValues){
            //注意:如果 value 是null ,StringBuilder将拼接 "null" 字符串, 详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(defaultString(paramName)).append("=").append(defaultString(paramValue));
            sb.append(AMPERSAND);//放心大胆的拼接 &, 不判断是否是最后一个,最后会截取
        }

        return StringUtil.substringWithoutLast(sb, AMPERSAND);
    }

    /**
     * 在<code>singleValueMap</code>中取到指定<code>includeKeys</code> key的<b>value</b>,连接起来(<span style="color:red">不使用任何连接符</span>).
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>拼接的顺序按照 <code>includeKeys</code> 的顺序,目前适用于个别银行(比如汇付天下)需要将值拼接起来加密</li>
     * <li>如果<code>singleValueMap</code>中的value是null,那么会以{@link StringUtils#EMPTY}替代,进行拼接</li>
     * </ol>
     * </blockquote>
     * 
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <p>
     * <b>场景:</b> 拼接map中 key是 "service"以及 "paymentType"的 value
     * </p>
     * 
     * <pre class="code">
     * Map{@code <String, String>} map = newHashMap();
     * map.put("service", "create_salesorder");
     * map.put("paymentType", "unionpay_mobile");
     * 
     * ParamUtil.joinValuesOrderByIncludeKeys(map, "service", "paymentType")    =   create_salesorderunionpay_mobile
     * </pre>
     * 
     * </blockquote>
     *
     * @param <K>
     *            the key type
     * @param singleValueMap
     *            the map
     * @param includeKeys
     *            包含的key
     * @return 如果 <code>singleValueMap</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>includeKeys</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         否则循环 <code>includeKeys</code>,依次从 <code>singleValueMap</code>中取到值,连接起来;<br>
     * @see org.apache.commons.lang3.StringUtils#defaultString(String)
     * @since 1.5.5
     */
    @SafeVarargs
    public static <K> String joinValuesOrderByIncludeKeys(Map<K, String> singleValueMap,K...includeKeys){
        Validate.notNull(singleValueMap, "singleValueMap can't be null!");
        if (isNullOrEmpty(includeKeys)){
            return EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (K key : includeKeys){//有顺序的参数
            //注意:如果 value是null,StringBuilder将拼接 "null" 字符串, 详见  java.lang.AbstractStringBuilder#append(String)
            sb.append(defaultString(singleValueMap.get(key)));
        }
        return sb.toString();
    }
    //---------------------------------------------------------------------------------------------

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
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return the string
     * @since 1.4.0
     */
    static String addParameterArrayValueMap(String uriString,String queryString,Map<String, String[]> arrayValueMap,String charsetType){
        Map<String, String[]> safeArrayValueMap = defaultIfNull(arrayValueMap, Collections.<String, String[]> emptyMap());

        Map<String, String[]> arrayParamValuesMap = newLinkedHashMap(safeArrayValueMap.size());
        //先提取queryString map
        if (isNotNullOrEmpty(queryString)){
            arrayParamValuesMap.putAll(toSafeArrayValueMap(queryString, null));
        }
        arrayParamValuesMap.putAll(safeArrayValueMap);
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
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>arrayValueMap</code> 是null或者empty,返回 {@link Collections#emptyMap()}<br>
     */
    private static Map<String, String[]> toSafeArrayValueMap(Map<String, String[]> arrayValueMap,String charsetType){
        if (isNullOrEmpty(arrayValueMap)){
            return emptyMap();
        }
        Map<String, String[]> safeArrayValueMap = newLinkedHashMap(arrayValueMap.size()); //使用 LinkedHashMap,保持map元素顺序
        for (Map.Entry<String, String[]> entry : arrayValueMap.entrySet()){
            String key = entry.getKey();
            String[] paramValues = entry.getValue();
            if (isNullOrEmpty(paramValues)){
                LOGGER.warn("the param key:[{}] value is null", key);
                paramValues = EMPTY_STRING_ARRAY;//赋予 empty数组,为了下面的转换
            }
            safeArrayValueMap.put(decodeAndEncode(key, charsetType), toSafeValueArray(paramValues, charsetType));
        }
        return safeArrayValueMap;
    }

    /**
     * To safe value array.
     *
     * @param paramValues
     *            the param values
     * @param charsetType
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量
     * @return the string[]
     * @since 1.6.2
     */
    private static String[] toSafeValueArray(String[] paramValues,String charsetType){
        if (isNullOrEmpty(charsetType)){
            return paramValues;
        }
        List<String> paramValueList = newArrayList();
        for (String value : paramValues){
            paramValueList.add(decodeAndEncode(value, charsetType));
        }
        return toArray(paramValueList, String.class);
    }

    /**
     * 浏览器传递queryString()参数差别;
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>主要是为了处理浏览器兼容问题,参见
     * <a href="http://stackoverflow.com/questions/15004593/java-request-getquerystring-value-different-between-chrome-and-ie-browser">
     * java-request-getquerystring-value-different-between-chrome-and-ie-browser</a>,chrome会将query进行 encoded再发送请求;而ie原封不动的发送.</li>
     * <li>由于暂时不能辨别是否encoded过,所以先强制decode再encode;</li>
     * <li>此处不能先转decode(query,charsetType),参数就是想传 =是转义符</li>
     * </ol>
     * </blockquote>
     * 
     * @param value
     *            the value
     * @param charsetType
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
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
        if (isNullOrEmpty(value)){
            return EMPTY;
        }
        return isNullOrEmpty(charsetType) ? value : encode(decode(value, charsetType), charsetType);
    }

    /**
     * 拼接url.
     *
     * @param beforePathWithoutQueryString
     *            the before path without query string
     * @param arrayValueMap
     *            the array value map
     * @param charsetType
     *            字符编码,建议使用 {@link CharsetType} 定义好的常量<br>
     *            <span style="color:green">如果是null或者 empty,那么参数部分原样返回,自行处理兼容性问题</span><br>
     *            否则会先解码,再加码,因为ie浏览器和chrome浏览器 url中访问路径 ,带有中文情况下不一致
     * @return 如果 <code>beforePathWithoutQueryString</code> 是null或者empty,返回 {@link StringUtils#EMPTY}<br>
     *         如果<code>arrayValueMap</code> 是null或者empty,返回 <code>beforePathWithoutQueryString</code>
     * @since 1.4.0
     */
    static String combineUrl(String beforePathWithoutQueryString,Map<String, String[]> arrayValueMap,String charsetType){
        if (isNullOrEmpty(beforePathWithoutQueryString)){
            return EMPTY;
        }
        if (isNullOrEmpty(arrayValueMap)){//没有参数 直接return
            return beforePathWithoutQueryString;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(beforePathWithoutQueryString);
        sb.append(QUESTIONMARK);
        sb.append(toQueryStringUseArrayValueMap(toSafeArrayValueMap(arrayValueMap, charsetType)));

        return sb.toString();
    }

}
