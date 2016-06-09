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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang3.Validate;

import com.feilong.core.bean.ConvertUtil;

/**
 * The Class URLUtil.
 * 
 * <h3>URL 的长度上限</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * URL 的最大长度是多少？W3C 的 HTTP 协议 并没有限定,然而,在实际应用中,经过试验,不同浏览器和 Web 服务器有不同的约定:
 * </p>
 * 
 * <ul>
 * <li>IE 的 URL 长度上限是 2083 字节,其中纯路径部分不能超过 2048 字节.</li>
 * <li>Firefox 浏览器的地址栏中超过 65536 字符后就不再显示.</li>
 * <li>Safari 浏览器一致测试到 80000 字符还工作得好好的.</li>
 * <li>Opera 浏览器测试到 190000 字符的时候,还正常工作.</li>
 * </ul>
 * 
 * Web 服务器:
 * <ul>
 * <li>Apache Web 服务器在接收到大约 4000 字符长的 URL 时候产生 413 Entity Too Large" 错误.</li>
 * <li>IIS 默认接收的最大 URL 是 16384 字符.</li>
 * </ul>
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.1
 */
public final class URLUtil{

    /** Don't let anyone instantiate this class. */
    private URLUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * To string array.
     *
     * @param urls
     *            the urls
     * @return 如果 <code>urls</code> 是null,抛出 {@link NullPointerException}<br>
     * @see com.feilong.core.bean.ConvertUtil#toStrings(Object)
     * @since 1.2.1
     */
    public static String[] toStringArray(URL...urls){
        Validate.notNull(urls, "urls can't be null!");
        return ConvertUtil.toStrings(urls);
    }

    /**
     * 将 {@link URL}转成 {@link URI}.
     *
     * @param url
     *            the url
     * @return 如果 <code>url</code> 是null,抛出 {@link NullPointerException}<br>
     * @see "org.springframework.util.ResourceUtils#toURI(URL)"
     * @see java.net.URL#toURI()
     * @since 1.2.2
     */
    public static URI toURI(URL url){
        Validate.notNull(url, "url can't be null!");
        try{
            return url.toURI();
        }catch (URISyntaxException e){
            throw new URIParseException(e);
        }
    }

    /**
     * New url.
     *
     * @param spec
     *            the <code>String</code> to parse as a URL.
     * @return 如果 <code>spec</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>spec</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see java.net.URL#URL(String)
     * @since 1.3.0
     */
    public static URL newURL(String spec){
        Validate.notBlank(spec, "spec can't be blank!");
        try{
            return new URL(spec);
        }catch (MalformedURLException e){
            throw new URIParseException(e);
        }
    }

    /**
     * 获取联合url,通过在指定的上下文中对给定的 spec进行解析创建 URL,新的 URL从给定的上下文 URL和 spec参数创建.
     * 
     * <p style="color:red">
     * 网站地址拼接,请使用{@link #getUnionUrl(URL, String)}
     * </p>
     * 
     * <p>
     * URIUtil.getUnionUrl("E:\\test", "sanguo") = file:/E:/test/sanguo
     * </p>
     * 
     * @param context
     *            要解析规范的上下文
     * @param spec
     *            the <code>String</code> to parse as a URL.
     * @return 如果 <code>context</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>context</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see #getFileURL(String)
     * @see #getUnionUrl(URL, String)
     * @since 1.4.0
     */
    public static String getUnionFileUrl(String context,String spec){
        URL parentUrl = getFileURL(context);
        return getUnionUrl(parentUrl, spec);
    }

    /**
     * 获取联合url,通过在指定的上下文中对给定的 spec 进行解析创建 URL,新的 URL 从给定的上下文 URL 和 spec 参数创建.
     * 
     * <p style="color:red">
     * 网站地址拼接,请使用这个method
     * </p>
     * 
     * <h3>示例:</h3>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * URIUtil.getUnionUrl("E:\\test", "sanguo")            =   file:/E:/test/sanguo
     * 
     * URL url = new URL("http://www.exiaoshuo.com/jinyiyexing/");
     * URIUtil.getUnionUrl(url, "/jinyiyexing/1173348/")    =  http://www.exiaoshuo.com/jinyiyexing/1173348/
     * </pre>
     * 
     * </blockquote>
     * 
     * @param context
     *            要解析规范的上下文
     * @param spec
     *            the <code>String</code> to parse as a URL.
     * @return 如果 <code>spec</code> 是null,抛出 {@link NullPointerException}<br>
     * @since 1.4.0
     */
    public static String getUnionUrl(URL context,String spec){
        Validate.notNull(spec, "spec can't be null!");
        try{
            URL unionUrl = new URL(context, spec);
            return unionUrl.toString();
        }catch (MalformedURLException e){
            throw new URIParseException(e);
        }
    }

    /**
     * 将字符串路径 <code>filePathName</code> 转成url.
     *
     * @param filePathName
     *            字符串路径
     * @return 如果 <code>filePathName</code> 是null,抛出 {@link NullPointerException}<br>
     *         如果 <code>filePathName</code> 是blank,抛出 {@link IllegalArgumentException}<br>
     * @see java.io.File#toURI()
     * @see java.net.URI#toURL()
     * @see "org.apache.commons.io.FileUtils#toURLs(File[])"
     * @since 1.4.0
     */
    public static URL getFileURL(String filePathName){
        Validate.notBlank(filePathName, "filePathName can't be null/empty!");
        File file = new File(filePathName);
        try{
            return file.toURI().toURL();// file.toURL() 已经过时,它不会自动转义 URL 中的非法字符
        }catch (MalformedURLException e){
            throw new URIParseException(e);
        }
    }
}
