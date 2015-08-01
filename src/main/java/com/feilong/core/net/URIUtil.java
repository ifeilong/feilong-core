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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.CharsetType;
import com.feilong.core.log.Slf4jUtil;
import com.feilong.core.util.StringUtil;
import com.feilong.core.util.Validator;

/**
 * 处理{@link java.net.URI}(Uniform Resource Locator) {@link java.net.URL}(Uniform Resource Identifier) 等.
 * 
 * <h3>{@link java.net.URI}和 {@link java.net.URL}的区别:</h3>
 * 
 * <blockquote>
 * 
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * 
 * <tr valign="top">
 * <td>{@link java.net.URI URI}<br>
 * Uniform Resource Identifier</td>
 * <td>统一资源标识符，用来唯一的标识一个资源<br>
 * There are two types of URIs: URLs and URNs. <br>
 * See RFC 1630: Universal Resource Identifiers in WWW: A Unifying Syntax for the Expression of Names and Addresses of Objects on the
 * Network as used in the WWW.</td>
 * </tr>
 * 
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.net.URL URL}<br>
 * Uniform Resource Locator</td>
 * <td>统一资源定位器，它是一种具体的URI，即URL可以用来标识一个资源，而且还指明了如何locate这个资源。 <br>
 * See RFC 1738: Uniform Resource Locators (URL)</td>
 * </tr>
 * 
 * <tr valign="top">
 * <td>URN<br>
 * uniform resource name</td>
 * <td>统一资源命名，是通过名字来标识资源， <br>
 * 比如mailto:java-net@java.sun.com。</td>
 * </tr>
 * </table>
 * 
 * <p>
 * 也就是说，{@link java.net.URI URI}是以一种抽象的，高层次概念定义统一资源标识，而URL和URN则是具体的资源标识的方式。URL和URN都是一种URI。
 * </p>
 * 
 * <p>
 * 一个{@link URI}实例可以代表绝对的，也可以是相对的，只要它符合URI的语法规则;<br>
 * 而{@link URL}类则不仅符合语义，还包含了定位该资源的信息，<span style="color:red">因此它不能是相对的，schema必须被指定</span>。
 * </p>
 * </blockquote>
 * 
 * <h3>关于 {@link URI} 组成部分:</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>getQuery() 返回此 URI 的已解码的查询组成部分。</li>
 * <li>getRawQuery() 返回此 URI 的原始查询组成部分。 URI 的查询组成部分（如果定义了）只包含合法的 URI 字符。</li>
 * </ul>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.0 2010-6-11 上午02:06:43
 * @see java.net.URI
 * @see java.net.URL
 * @see URIComponents
 * @since 1.0.0
 */
public final class URIUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URIUtil.class);

    /** Don't let anyone instantiate this class. */
    private URIUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 基于 uri字符串和charset创建 {@link URI}.
     * 
     * <p>
     * 内部调用 {@link URI#create(String)}方法
     * </p>
     * 
     * <p>
     * 如果url中不含?等参数,直接调用 {@link URI#create(String)}创建<br>
     * 如果如果url中含?等参数,那么内部会调用 {@link #getEncodedUrlByArrayValueMap(String, Map, String)}获得新的url,再调用 调用 {@link URI#create(String)}创建
     * </p>
     *
     * @param uriString
     *            the uri string
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return if isNullOrEmpty(uri),return null;<br>
     *         if Exception,throw URIParseException
     * @see <a
     *      href="http://stackoverflow.com/questions/15004593/java-request-getquerystring-value-different-between-chrome-and-ie-browser">java-request-getquerystring-value-different-between-chrome-and-ie-browser</a>
     * @see URI#create(String)
     * @see #getEncodedUrlByArrayValueMap(String, Map, String)
     */
    public static URI create(String uriString,String charsetType){
        try{
            String encodeUrl = encodeUrl(uriString, charsetType);
            return URI.create(encodeUrl);
        }catch (Exception e){
            LOGGER.error("Exception:", e);
            throw new URIParseException(e);
        }
    }

    /**
     * 基于 uriString和charset创建 {@link URI}.
     * 
     * <p>
     * 内部调用 {@link URI#create(String)}方法
     * </p>
     * 
     * <p>
     * 如果uriString中不含?等参数,直接调用 {@link URI#create(String)}创建<br>
     * 如果如果uriString中含?等参数,那么内部会调用 {@link #getEncodedUrlByArrayValueMap(String, Map, String)}获得新的url,再调用 调用 {@link URI#create(String)}创建
     * </p>
     *
     * @param uriString
     *            the uri string
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return the string
     * @see <a
     *      href="http://stackoverflow.com/questions/15004593/java-request-getquerystring-value-different-between-chrome-and-ie-browser">java-request-getquerystring-value-different-between-chrome-and-ie-browser</a>
     * @see URI#create(String)
     * @see #getEncodedUrlByArrayValueMap(String, Map, String)
     */
    public static String encodeUrl(String uriString,String charsetType){
        if (Validator.isNullOrEmpty(uriString)){
            throw new NullPointerException("the url is null or empty!");
        }
        if (Validator.isNullOrEmpty(charsetType)){
            throw new NullPointerException("the charsetType is null or empty!");
        }
        LOGGER.debug("in uriString:[{}],charsetType:{}", uriString, charsetType);

        if (!StringUtil.isContain(uriString, URIComponents.QUESTIONMARK)){
            return uriString;// 不带参数 一般不需要处理
        }

        // XXX 暂不处理 这种路径报错的情况
        // cmens/t-b-f-a-c-s-f-p400-600,0-200,200-400,600-up-gCold Gear-eBase Layer-i1-o.htm

        // 问号前面的部分
        String before = getFullPathWithoutQueryString(uriString);
        String queryString = StringUtil.substring(uriString, URIComponents.QUESTIONMARK, 1);

        Map<String, String[]> map = ParamUtil.parseQueryStringToArrayValueMap(queryString, charsetType);
        String encodeUrl = getEncodedUrlByArrayValueMap(before, map, charsetType);
        LOGGER.debug("after url:{}", encodeUrl);
        return encodeUrl;
    }

    /**
     * call {@link java.net.URI#URI(String)}.
     * 
     * <p>
     * 如果String对象的URI违反了RFC 2396的语法规则，将会产生一个 {@link URISyntaxException}.<br>
     * 如果知道URI是有效的，不会产生 {@link URISyntaxException}，可以使用静态的create(String uri)方法
     * </p>
     *
     * @param uri
     *            the uri
     * @return {@link java.net.URI#URI(String)}
     * @see java.net.URI#URI(String)
     * @since 1.3.0
     */
    public static URI newURI(String uri){
        try{
            return new URI(uri);
        }catch (URISyntaxException e){
            LOGGER.error(Slf4jUtil.formatMessage("uri:[{}]", uri), e);
            throw new URIParseException(e);
        }
    }

    /**
     * 验证path是不是绝对路径.
     * 
     * <p>
     * (调用了 {@link java.net.URI#isAbsolute()},原理是{@code url's scheme !=null} ).
     * </p>
     *
     * @param uriString
     *            路径
     * @return <tt>true</tt> if, and only if, this URI is absolute
     * @see java.net.URI#isAbsolute()
     */
    public static boolean isAbsolutePath(String uriString){
        URI uri = newURI(uriString);

        if (null == uri){
            return false;
        }
        return uri.isAbsolute();
    }

    /**
     * 拼接uri(如果charsetType 是null,则原样拼接,如果不是空,则返回安全的url).
     *
     * @param beforeUri
     *            支持带?的形式, 内部自动解析,如果 是 ？ 形似 ，参数会自动混合到 keyAndValueMap
     * @param keyAndValueMap
     *            单值Map，会转成 数组值Map
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return the encoded url1
     * @since 1.3.1
     */
    public static String getEncodedUrlBySingleValueMap(String beforeUri,Map<String, String> keyAndValueMap,String charsetType){
        Map<String, String[]> keyAndArrayMap = new HashMap<String, String[]>();

        if (Validator.isNotNullOrEmpty(keyAndValueMap)){
            for (Map.Entry<String, String> entry : keyAndValueMap.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                keyAndArrayMap.put(key, new String[] { value });
            }
        }

        return getEncodedUrlByArrayValueMap(beforeUri, keyAndArrayMap, charsetType);
    }

    /**
     * 拼接uri(如果charsetType 是null,则原样拼接,如果不是空,则返回安全的url).
     *
     * @param beforeUri
     *            支持带?的形式, 内部自动解析,如果 是 ？ 形似 ，参数会自动混合到 keyAndArrayMap
     * @param keyAndValueArrayMap
     *            参数map value支持数组，类似于 request.getParameterMap
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return if isNullOrEmpty(keyAndArrayMap) return beforeUrl;
     * @see ParamUtil#combineQueryString(Map, String)
     * @since 1.3.1
     */
    public static String getEncodedUrlByArrayValueMap(String beforeUri,Map<String, String[]> keyAndValueArrayMap,String charsetType){
        if (Validator.isNullOrEmpty(keyAndValueArrayMap)){
            return beforeUri;
        }

        Map<String, String[]> paramsMap = new HashMap<String, String[]>();
        paramsMap.putAll(keyAndValueArrayMap);

        // 注意 action before 可能带参数
        // "action": "https://202.6.215.230:8081/purchasing/purchase.do?action=loginRequest",
        // "fullEncodedUrl":"https://202.6.215.230:8081/purchasing/purchase.do?action=loginRequest?miscFee=0&descp=&klikPayCode=03BELAV220&transactionDate=23%2F03%2F2014+02%3A40%3A19&currency=IDR",

        // *******************************************
        String beforePath = beforeUri;
        if (StringUtil.isContain(beforeUri, URIComponents.QUESTIONMARK)){
            // 问号前面的部分
            beforePath = getFullPathWithoutQueryString(beforeUri);
            String queryString = StringUtil.substring(beforeUri, URIComponents.QUESTIONMARK, 1);

            paramsMap.putAll(ParamUtil.parseQueryStringToArrayValueMap(queryString, null));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(beforePath);
        sb.append(URIComponents.QUESTIONMARK);
        sb.append(ParamUtil.combineQueryString(paramsMap, charsetType));
        return sb.toString();
    }

    /**
     * 获得不含queryString的path,即链接?前面的连接( <span style="color:red">不包含?</span>).
     *
     * @param uri
     *            the uri
     * @return if isNullOrEmpty(url),renturn {@link StringUtils#EMPTY}
     * @since 1.3.1
     */
    public static String getFullPathWithoutQueryString(String uri){
        if (Validator.isNullOrEmpty(uri)){
            return StringUtils.EMPTY;
        }
        // 判断url中是否含有?  XXX 有待严谨
        int index = uri.indexOf(URIComponents.QUESTIONMARK);
        if (index == -1){
            return uri;
        }
        return uri.substring(0, index);
    }

    // [start] encode/decode

    /**
     * iso-8859的方式去除乱码.
     * 
     * @param str
     *            字符串
     * @param bianma
     *            使用的编码
     * @return 原来的字符串,if isNullOrEmpty(str) return ""
     * @deprecated
     */
    @Deprecated
    public static String decodeLuanMaISO8859(String str,String bianma){
        if (Validator.isNullOrEmpty(str)){
            return StringUtils.EMPTY;
        }
        byte[] bytes = StringUtil.getBytes(str, CharsetType.ISO_8859_1);
        return StringUtil.newString(bytes, bianma);
    }

    /**
     * 加码,对参数值进行编码 .
     * 
     * <p>
     * 使用以下规则：
     * </p>
     * 
     * <ul>
     * <li>字母数字字符 "a" 到 "z"、"A" 到 "Z" 和 "0" 到 "9" 保持不变.</li>
     * <li>特殊字符 "."、"-"、"*" 和 "_" 保持不变.</li>
     * <li>空格字符 " " 转换为一个加号 "+".</li>
     * <li>所有其他字符都是不安全的，因此首先使用一些编码机制将它们转换为一个或多个字节.<br>
     * 然后每个字节用一个包含 3 个字符的字符串 "%xy" 表示，其中 xy 为该字节的两位十六进制表示形式.<br>
     * 推荐的编码机制是 UTF-8.<br>
     * 但是，出于兼容性考虑，如果未指定一种编码，则使用相应平台的默认编码.</li>
     * </ul>
     *
     * @param value
     *            the value
     * @param charsetType
     *            charsetType {@link CharsetType}
     * @return {@link java.net.URLEncoder#encode(String, String)}<br>
     *         if isNullOrEmpty(charsetType), 原样返回 value<br>
     * @see URLEncoder#encode(String, String)
     * @see CharsetType
     */
    public static String encode(String value,String charsetType){
        if (Validator.isNullOrEmpty(charsetType)){
            return value;
        }
        try{
            return URLEncoder.encode(value, charsetType);
        }catch (UnsupportedEncodingException e){
            LOGGER.error("UnsupportedEncodingException:", e);
            throw new URIParseException(e);
        }
    }

    /**
     * 解码,对参数值进行解码.
     * 
     * <p>
     * Decodes a <code>application/x-www-form-urlencoded</code> string using a specific encoding scheme. The supplied encoding is used to
     * determine what characters are represented by any consecutive sequences of the form "<code>%<i>xy</i></code>".
     * </p>
     * 
     * <p>
     * Not doing so may introduce incompatibilites.<br>
     * <em><strong>Note:</strong> 
     * 注：<a href="http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars">World Wide Web Consortium Recommendation</a>建议指出，UTF-8应该被使用. 不这样做可能会带来兼容性能.</em>
     * </p>
     *
     * @param value
     *            需要被解码的值
     * @param charsetType
     *            charsetType {@link CharsetType}
     * @return the newly {@link java.net.URLDecoder#decode(String, String)} 解码之后的值<br>
     *         if isNullOrEmpty(charsetType) ,原样返回 value<br>
     * @see URLEncoder#encode(java.lang.String, java.lang.String)
     * @see CharsetType
     */
    public static String decode(String value,String charsetType){
        if (Validator.isNullOrEmpty(charsetType)){
            return value;
        }
        try{
            return URLDecoder.decode(value, charsetType);
        }catch (UnsupportedEncodingException e){
            LOGGER.error("UnsupportedEncodingException:", e);
            throw new URIParseException(e);
        }
    }

    // [end]
}