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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.IOWriteUtil;
import com.feilong.core.util.Validator;

/**
 * The Class URLUtil.
 * 
 * <h3>URL 的长度上限</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * URL 的最大长度是多少？W3C 的 HTTP 协议 并没有限定,然而,在实际应用中,经过试验,不同浏览器和 Web 服务器有不同的约定：
 * </p>
 * 
 * <ul>
 * <li>IE 的 URL 长度上限是 2083 字节,其中纯路径部分不能超过 2048 字节。</li>
 * <li>Firefox 浏览器的地址栏中超过 65536 字符后就不再显示。</li>
 * <li>Safari 浏览器一致测试到 80000 字符还工作得好好的。</li>
 * <li>Opera 浏览器测试到 190000 字符的时候,还正常工作。</li>
 * </ul>
 * 
 * Web 服务器：
 * <ul>
 * <li>Apache Web 服务器在接收到大约 4000 字符长的 URL 时候产生 413 Entity Too Large" 错误。</li>
 * <li>IIS 默认接收的最大 URL 是 16384 字符。</li>
 * </ul>
 * </blockquote>
 *
 * @author feilong
 * @version 1.2.1 2015年6月21日 上午12:54:15
 * @since 1.2.1
 */
public final class URLUtil{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URLUtil.class);

    /** Don't let anyone instantiate this class. */
    private URLUtil(){
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
     * 
     * @see org.apache.commons.io.FileUtils#copyURLToFile(URL, File)
     * @see org.apache.commons.io.FileUtils#copyURLToFile(URL, File, int, int)
     * 
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
     * To string array.
     *
     * @param urls
     *            the urls
     * @return the string[]
     * @since 1.2.1
     */
    public static String[] toStringArray(URL[] urls){
        if (Validator.isNullOrEmpty(urls)){
            throw new NullPointerException("urls can't be null/empty!");
        }

        String[] stringArray = new String[urls.length];

        int i = 0;
        for (URL url : urls){
            stringArray[i] = url.toString();
            i++;
        }
        return stringArray;
    }

    /**
     * 将 {@link URL}转成 {@link URI}.
     *
     * @param url
     *            the url
     * @return the uri
     * @see "org.springframework.util.ResourceUtils#toURI(URL)"
     * @since 1.2.2
     */
    public static URI toURI(URL url){
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
     * @return the url
     * @since 1.3.0
     */
    public static URL newURL(String spec){
        try{
            return new URL(spec);
        }catch (MalformedURLException e){
            LOGGER.error("MalformedURLException:", e);
            throw new URIParseException(e);
        }
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
     * </p>
     * 
     * @param context
     *            要解析规范的上下文
     * @param spec
     *            the <code>String</code> to parse as a URL.
     * @return 获取联合url
     * @see #getFileURL(String)
     * @see #getUnionUrl(URL, String)
     * @since 1.4.0
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
     * @since 1.4.0
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
     * @since 1.4.0
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
}
