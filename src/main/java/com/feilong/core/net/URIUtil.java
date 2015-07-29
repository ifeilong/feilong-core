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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.CharsetType;
import com.feilong.core.io.IOWriteUtil;
import com.feilong.core.log.Slf4jUtil;
import com.feilong.core.util.ArrayUtil;
import com.feilong.core.util.CollectionsUtil;
import com.feilong.core.util.StringUtil;
import com.feilong.core.util.Validator;

/**
 * 处理{@link java.net.URI}(Uniform Resource Locator) {@link java.net.URL}(Uniform Resource Identifier) 等.
 * 
 * <h3>{@link java.net.URI}和 {@link java.net.URL}的区别:</h3>
 * 
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>{@link java.net.URL URL}<br>
 * (Uniform Resource Locator)</td>
 * <td>统一资源标识符，用来唯一的标识一个资源<br>
 * 统一资源定位器，它是一种具体的URI，即URL可以用来标识一个资源，而且还指明了如何locate这个资源。 <br>
 * See RFC 1738: Uniform Resource Locators (URL)</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>{@link java.net.URI URI}<br>
 * (Uniform Resource Identifier)</td>
 * <td>
 * There are two types of URIs: URLs and URNs. <br>
 * See RFC 1630: Universal Resource Identifiers in WWW: A Unifying Syntax for the Expression of Names and Addresses of Objects on the
 * Network as used in the WWW.</td>
 * </tr>
 * </table>
 * 
 * 一个URI实例可以代表绝对的，也可以是相对的，只要它符合URI的语法规则;而URL类则不仅符合语义，还包含了定位该资源的信息，因此它不能是相对的，schema必须被指定。
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
     * 将网络文件下载到文件夹.
     * 
     * <p>
     * 取到网络文件的文件名 原样下载到目标文件夹.
     * </p>
     *
     * @param urlString
     *            网络任意文件<br>
     *            url 不能带参数
     * @param directoryName
     *            目标文件夹
     * @throws IOException
     *             the IO exception
     * @see IOWriteUtil#write(InputStream, String, String)
     */
    public static void download(String urlString,String directoryName) throws IOException{
        if (Validator.isNullOrEmpty(urlString)){
            throw new NullPointerException("urlString can't be null/empty!");
        }
        if (Validator.isNullOrEmpty(directoryName)){
            throw new NullPointerException("directoryName can't be null/empty!");
        }

        LOGGER.info("begin download,urlString:[{}],directoryName:[{}]", urlString, directoryName);

        URL url = URLUtil.newURL(urlString);
        InputStream inputStream = url.openStream();

        File file = new File(urlString);
        String fileName = file.getName();

        IOWriteUtil.write(inputStream, directoryName, fileName);

        LOGGER.info("end download,url:[{}],directoryName:[{}]", urlString, directoryName);
    }

    /**
     * 基于 url字符串和charset创建 {@link URI}.
     * 
     * <p>
     * 内部调用 {@link URI#create(String)}方法
     * </p>
     * 
     * <p>
     * 如果url中不含?等参数,直接调用 {@link URI#create(String)}创建<br>
     * 如果如果url中含?等参数,那么内部会调用 {@link #getEncodedUrlByArrayMap(String, Map, String)}获得新的url,再调用 调用 {@link URI#create(String)}创建
     * </p>
     *
     * @param url
     *            url
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return if isNullOrEmpty(url),return null;<br>
     *         if Exception,return null
     * @see <a
     *      href="http://stackoverflow.com/questions/15004593/java-request-getquerystring-value-different-between-chrome-and-ie-browser">java-request-getquerystring-value-different-between-chrome-and-ie-browser</a>
     * @see URI#create(String)
     * @see #getEncodedUrlByArrayMap(String, Map, String)
     */
    public static URI create(String url,String charsetType){
        try{
            String encodeUrl = encodeUrl(url, charsetType);
            URI uri = URI.create(encodeUrl);
            return uri;
        }catch (Exception e){
            LOGGER.error("Exception:", e);
            throw new URIParseException(e);
        }
    }

    /**
     * 基于 url字符串和charset创建 {@link URI}.
     * 
     * <p>
     * 内部调用 {@link URI#create(String)}方法
     * </p>
     * 
     * <p>
     * 如果url中不含?等参数,直接调用 {@link URI#create(String)}创建<br>
     * 如果如果url中含?等参数,那么内部会调用 {@link #getEncodedUrlByArrayMap(String, Map, String)}获得新的url,再调用 调用 {@link URI#create(String)}创建
     * </p>
     *
     * @param url
     *            url
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return the string
     * @see <a
     *      href="http://stackoverflow.com/questions/15004593/java-request-getquerystring-value-different-between-chrome-and-ie-browser">java-request-getquerystring-value-different-between-chrome-and-ie-browser</a>
     * @see URI#create(String)
     * @see #getEncodedUrlByArrayMap(String, Map, String)
     */
    public static String encodeUrl(String url,String charsetType){
        if (Validator.isNullOrEmpty(url)){
            throw new NullPointerException("the url is null or empty!");
        }
        if (Validator.isNullOrEmpty(charsetType)){
            throw new NullPointerException("the charsetType is null or empty!");
        }

        LOGGER.debug("in url:[{}],charsetType:{}", url, charsetType);

        // 暂不处理 这种路径报错的情况
        // cmens/t-b-f-a-c-s-f-p400-600,0-200,200-400,600-up-gCold Gear-eBase Layer-i1-o.htm

        if (StringUtil.isContain(url, URIComponents.QUESTIONMARK)){
            // 问号前面的部分
            String before = getBeforePath(url);

            String query = StringUtil.substring(url, URIComponents.QUESTIONMARK, 1);

            Map<String, String[]> map = parseQueryToArrayMap(query, charsetType);
            String encodeUrl = getEncodedUrlByArrayMap(before, map, charsetType);

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("after url:{}", encodeUrl);
            }
            return encodeUrl;
        }else{
            // 不带参数 一般不需要处理
            return url;
        }
        // 如果知道URI是有效的，不会产生URISyntaxException，可以使用静态的create(String uri)方法
        // 调用的 new URI(str) 方法
    }

    /**
     * call {@link java.net.URI#URI(String)}.
     * 
     * <p>
     * 如果String对象的URI违反了RFC 2396的语法规则，将会产生一个java.net.URISyntaxException.
     * </p>
     *
     * @param path
     *            the path
     * @return {@link java.net.URI#URI(String)}
     * @see java.net.URI#URI(String)
     */
    public static URI getURI(String path){
        try{
            // 如果String对象的URI违反了RFC 2396的语法规则，将会产生一个java.net.URISyntaxException.
            return new URI(path);
        }catch (URISyntaxException e){
            LOGGER.error(Slf4jUtil.formatMessage("path:[{}]", path), e);
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
     * @param path
     *            路径
     * @return <tt>true</tt> if, and only if, this URI is absolute
     * @see java.net.URI#isAbsolute()
     */
    public static boolean isAbsolutePath(String path){
        URI uri = getURI(path);

        if (null == uri){
            return false;
        }
        return uri.isAbsolute();
    }

    /**
     * 拼接url(如果charsetType 是null,则原样拼接,如果不是空,则返回安全的url).
     *
     * @param beforeUrl
     *            支持带?的形式, 内部自动解析,如果 是 ？ 形似 ，参数会自动混合到 keyAndValueMap
     * @param keyAndValueMap
     *            单值Map，会转成 数组值Map
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return the encoded url1
     */
    public static String getEncodedUrlByValueMap(String beforeUrl,Map<String, String> keyAndValueMap,String charsetType){
        Map<String, String[]> keyAndArrayMap = new HashMap<String, String[]>();

        if (Validator.isNotNullOrEmpty(keyAndValueMap)){
            for (Map.Entry<String, String> entry : keyAndValueMap.entrySet()){
                String key = entry.getKey();
                String value = entry.getValue();
                keyAndArrayMap.put(key, new String[] { value });
            }
        }

        return getEncodedUrlByArrayMap(beforeUrl, keyAndArrayMap, charsetType);
    }

    /**
     * 拼接url(如果charsetType 是null,则原样拼接,如果不是空,则返回安全的url).
     *
     * @param beforeUrl
     *            支持带?的形式, 内部自动解析,如果 是 ？ 形似 ，参数会自动混合到 keyAndArrayMap
     * @param keyAndArrayMap
     *            参数map value支持数组，类似于 request.getParameterMap
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return if isNullOrEmpty(keyAndArrayMap) return beforeUrl;
     * @see #combineQueryString(Map, String)
     */
    public static String getEncodedUrlByArrayMap(String beforeUrl,Map<String, String[]> keyAndArrayMap,String charsetType){
        if (Validator.isNullOrEmpty(keyAndArrayMap)){
            return beforeUrl;
        }

        // map 不是空 表示 有参数
        Map<String, String[]> appendMap = new HashMap<String, String[]>();
        appendMap.putAll(keyAndArrayMap);

        // 注意 action before 可能带参数
        // "action": "https://202.6.215.230:8081/purchasing/purchase.do?action=loginRequest",
        // "fullEncodedUrl":
        // "https://202.6.215.230:8081/purchasing/purchase.do?action=loginRequest?miscFee=0&descp=&klikPayCode=03BELAV220&callback=%2Fpatment1url&totalAmount=60000.00&payType=01&transactionNo=20140323024019&signature=1278794012&transactionDate=23%2F03%2F2014+02%3A40%3A19&currency=IDR",

        // *******************************************
        String beforePath = beforeUrl;

        // 如果包含?
        if (StringUtil.isContain(beforeUrl, URIComponents.QUESTIONMARK)){
            // 问号前面的部分
            beforePath = getBeforePath(beforeUrl);
            String query = StringUtil.substring(beforeUrl, URIComponents.QUESTIONMARK, 1);

            Map<String, String[]> map = parseQueryToArrayMap(query, null);
            appendMap.putAll(map);
        }

        StringBuilder builder = new StringBuilder("");
        builder.append(beforePath);
        builder.append(URIComponents.QUESTIONMARK);

        // *******************************************
        String queryString = combineQueryString(appendMap, charsetType);
        builder.append(queryString);

        return builder.toString();
    }

    /**
     * 将map 混合成 queryString.
     *
     * @param appendMap
     *            类似于 request.getParamMap
     * @param charsetType
     *            {@link CharsetType} 何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return if isNullOrEmpty(appendMap) ,return ""
     * @see CharsetType
     */
    public static String combineQueryString(Map<String, String[]> appendMap,String charsetType){
        if (Validator.isNullOrEmpty(appendMap)){
            return StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder();

        int i = 0;
        int size = appendMap.size();
        for (Map.Entry<String, String[]> entry : appendMap.entrySet()){
            String key = entry.getKey();
            String[] paramValues = entry.getValue();

            // **************************************************************
            if (Validator.isNotNullOrEmpty(charsetType)){
                // 统统先强制 decode 再 encode
                // 浏览器兼容问题
                key = encode(decode(key, charsetType), charsetType);
            }

            // **************************************************************

            if (Validator.isNullOrEmpty(paramValues)){
                LOGGER.warn("the param key:[{}] value is null", key);
                sb.append(key);
                sb.append("=");
                sb.append("");
            }else{
                List<String> paramValueList = null;
                // value isNotNullOrEmpty
                if (Validator.isNotNullOrEmpty(charsetType)){
                    paramValueList = new ArrayList<String>();
                    for (String value : paramValues){
                        if (Validator.isNotNullOrEmpty(value)){
                            // 浏览器传递queryString()参数差别
                            // chrome 会将query 进行 encoded 再发送请求
                            // 而ie 原封不动的发送

                            // 由于暂时不能辨别是否encoded过,所以 先强制decode 再 encode
                            // 此处不能先转 decode(query, charsetType)   ,参数就是想传 =是转义符
                            paramValueList.add(encode(decode(value.toString(), charsetType), charsetType));
                        }else{
                            paramValueList.add("");
                        }
                    }
                }else{
                    paramValueList = ArrayUtil.toList(paramValues);
                }

                for (int j = 0, z = paramValueList.size(); j < z; ++j){
                    String value = paramValueList.get(j);
                    sb.append(key);
                    sb.append("=");
                    sb.append(value);
                    // 最后一个& 不拼接
                    if (j != z - 1){
                        sb.append(URIComponents.AMPERSAND);
                    }
                }
            }

            // 最后一个& 不拼接
            if (i != size - 1){
                sb.append(URIComponents.AMPERSAND);
            }
            ++i;
        }
        return sb.toString();
    }

    /**
     * 将{@code a=1&b=2}这样格式的数据转换成map (如果charsetType 不是null或者empty 返回安全的 key和value).
     *
     * @param query
     *            the query
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return the {@code map<string, string>}
     * @see #parseQueryToArrayMap(String, String)
     */
    public static Map<String, String> parseQueryToValueMap(String query,String charsetType){
        Map<String, String> returnMap = new HashMap<String, String>();
        Map<String, String[]> map = parseQueryToArrayMap(query, charsetType);
        if (Validator.isNotNullOrEmpty(map)){
            for (Map.Entry<String, String[]> entry : map.entrySet()){
                String key = entry.getKey();
                String[] value = entry.getValue();
                returnMap.put(key, value[0]);
            }
        }
        return returnMap;
    }

    /**
     * 将{@code a=1&b=2}这样格式的数据转换成map (如果charsetType 不是null或者empty 返回安全的 key和value).
     *
     * @param query
     *            {@code a=1&b=2}类型的数据,支持{@code a=1&a=1}的形式， 返回map的值是数组
     * @param charsetType
     *            何种编码，如果是null或者 empty,那么参数部分原样返回,自己去处理兼容性问题<br>
     *            否则会先解码,再加码,因为ie浏览器和chrome 浏览器 url中访问路径 ,带有中文情况下 不一致
     * @return map value的处理 （{@link LinkedHashMap#LinkedHashMap(int, float)}
     *         <ul>
     *         <li>没有Validator.isNullOrEmpty(bianma) 那么就原样返回</li>
     *         <li>如果有编码,统统先强制 decode 再 encode</li>
     *         </ul>
     */
    public static Map<String, String[]> parseQueryToArrayMap(String query,String charsetType){
        if (Validator.isNotNullOrEmpty(query)){
            String[] nameAndValueArray = query.split(URIComponents.AMPERSAND);

            if (Validator.isNotNullOrEmpty(nameAndValueArray)){

                Map<String, String[]> map = new LinkedHashMap<String, String[]>();

                for (int i = 0, j = nameAndValueArray.length; i < j; ++i){

                    String nameAndValue = nameAndValueArray[i];
                    String[] tempArray = nameAndValue.split("=", 2);

                    if (tempArray != null && tempArray.length == 2){
                        String key = tempArray[0];
                        String value = tempArray[1];

                        if (Validator.isNullOrEmpty(charsetType)){
                            // 没有编码 原样返回
                        }else{

                            // 浏览器传递queryString()参数差别
                            // chrome 会将query 进行 encoded 再发送请求
                            // 而ie 原封不动的发送

                            // 由于暂时不能辨别是否encoded过,所以 先强制decode 再 encode
                            // 此处不能先转  decode(query, charsetType) ,参数就是想传 =是转义符

                            // 统统先强制 decode 再 encode
                            // 浏览器兼容问题
                            key = encode(decode(key, charsetType), charsetType);
                            value = encode(decode(value, charsetType), charsetType);
                        }

                        String[] valuesArrayInMap = map.get(key);

                        List<String> list = null;
                        if (Validator.isNullOrEmpty(valuesArrayInMap)){
                            list = new ArrayList<String>();
                        }else{
                            list = ArrayUtil.toList(valuesArrayInMap);
                        }
                        list.add(value);
                        map.put(key, CollectionsUtil.toArray(list, String.class));
                    }
                }
                return map;
            }
        }
        return null;
    }

    /**
     * 获取链接?前面的连接(不包含?).
     * 
     * @param url
     *            the url
     * @return if isNullOrEmpty(url),renturn ""
     * @deprecated will rename 名字不好
     */
    //TODO will rename
    @Deprecated
    public static String getBeforePath(String url){
        if (Validator.isNullOrEmpty(url)){
            return StringUtils.EMPTY;
        }
        // 判断url中是否含有?
        int index = url.indexOf(URIComponents.QUESTIONMARK);
        if (index == -1){
            return url;
        }
        return url.substring(0, index);
    }

    /**
     * 获取联合url,通过在指定的上下文中对给定的 spec进行解析创建 URL. 新的 URL从给定的上下文 URL和 spec参数创建.
     * 
     * <p style="color:red">
     * 网站地址拼接,请使用{@link #getUnionUrl(URL, String)}
     * </p>
     * 
     * <p>
     * 示例: URIUtil.getUnionUrl("E:\\test", "sanguo")-------------{@code >}file:/E:/test/sanguo
     *
     * @param context
     *            要解析规范的上下文
     * @param spec
     *            the <code>String</code> to parse as a URL.
     * @return 获取联合url
     * @see com.feilong.core.net.URIUtil#getFileURL(String)
     * @see com.feilong.core.net.URIUtil#getUnionUrl(URL, String)
     */
    public static String getUnionFileUrl(String context,String spec){
        URL parentUrl = getFileURL(context);
        return getUnionUrl(parentUrl, spec);
    }

    /**
     * 获取联合url,通过在指定的上下文中对给定的 spec 进行解析创建 URL. 新的 URL 从给定的上下文 URL 和 spec 参数创建.
     * 
     * <p style="color:red">
     * 网站地址拼接,请使用这个method
     * </p>
     * 
     * <pre>
     * {@code
     * 示例: 
     * 
     * URIUtil.getUnionUrl("E:\\test", "sanguo")------------->file:/E:/test/sanguo
     * 
     * URL url = new URL("http://www.exiaoshuo.com/jinyiyexing/");
     * result = URIUtil.getUnionUrl(url, "/jinyiyexing/1173348/");
     * http://www.exiaoshuo.com/jinyiyexing/1173348/
     * }
     * </pre>
     *
     * @param context
     *            要解析规范的上下文
     * @param spec
     *            the <code>String</code> to parse as a URL.
     * @return 获取联合url
     */
    public static String getUnionUrl(URL context,String spec){
        try{
            URL unionUrl = new URL(context, spec);
            return unionUrl.toString();
        }catch (MalformedURLException e){
            LOGGER.error("MalformedURLException:", e);
            throw new URIParseException(e);
        }
    }

    /**
     * 将字符串路径转成url.
     *
     * @param filePathName
     *            字符串路径
     * @return url
     * @see java.io.File#toURI()
     * @see java.net.URI#toURL()
     */
    public static URL getFileURL(String filePathName){
        if (Validator.isNullOrEmpty(filePathName)){
            throw new NullPointerException("filePathName can't be null/empty!");
        }
        File file = new File(filePathName);
        try{
            // file.toURL() 已经过时,它不会自动转义 URL 中的非法字符
            return file.toURI().toURL();
        }catch (MalformedURLException e){
            LOGGER.error("MalformedURLException:", e);
            throw new URIParseException(e);
        }
    }

    // [start] encode/decode

    /**
     * iso-8859的方式去除乱码.
     * 
     * @param str
     *            字符串
     * @param bianma
     *            使用的编码
     * @return 原来的字符串<br>
     *         if isNullOrEmpty(str) return ""
     * @deprecated
     */
    @Deprecated
    public static String decodeLuanMaISO8859(String str,String bianma){
        if (Validator.isNullOrEmpty(str)){
            return StringUtils.EMPTY;
        }
        byte[] bytes = StringUtil.getBytes(str.trim(), CharsetType.ISO_8859_1);
        return StringUtil.newString(bytes, bianma);
    }

    //**************************************************************************************

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
    //*********************************************************************************

    /**
     * url中的特殊字符转为16进制代码,用于url传递.
     * 
     * @param specialCharacter
     *            特殊字符
     * @return 特殊字符url编码
     * @deprecated 将来会重构
     */
    @Deprecated
    public static String specialCharToHexString(String specialCharacter){

        Map<String, String> specialCharacterMap = new HashMap<String, String>();

        specialCharacterMap.put("+", "%2B");// URL 中+号表示空格
        specialCharacterMap.put(" ", "%20");// URL中的空格可以用+号或者编码
        specialCharacterMap.put("/", "%2F");// 分隔目录和子目录
        specialCharacterMap.put(URIComponents.QUESTIONMARK, "%3F");// 分隔实际的 URL 和参数
        specialCharacterMap.put("%", "%25");// 指定特殊字符
        specialCharacterMap.put("#", "%23");// 表示书签
        specialCharacterMap.put("&", "%26");// URL 中指定的参数间的分隔符
        specialCharacterMap.put("=", "%3D");// URL 中指定参数的值

        if (specialCharacterMap.containsKey(specialCharacter)){
            return specialCharacterMap.get(specialCharacter);
        }
        // 不是 url 特殊字符 原样输出
        return specialCharacter;
    }
}