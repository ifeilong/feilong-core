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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.InputStreamUtil;
import com.feilong.core.io.ReaderUtil;
import com.feilong.core.io.UncheckedIOException;
import com.feilong.core.tools.json.JsonUtil;
import com.feilong.core.util.Validator;

/**
 * {@link java.net.HttpURLConnection}工具类(支持代理 {@link java.net.Proxy}).
 * 
 * <h3>关于超时时间:</h3>
 * 
 * <blockquote>
 * <p>
 * {@link java.net.HttpURLConnection}是基于HTTP协议的,其底层通过socket通信实现。<br>
 * <span style="color:red"> 如果不设置超时（timeout）,在网络异常的情况下,可能会导致程序僵死而不继续往下执行。</span> <br>
 * 在JDK1.5- 版本中，只能通过以下两个语句来设置相应的超时：
 * </p>
 * <ul>
 * <li>System.setProperty("sun.net.client.defaultConnectTimeout", 超时毫秒数字符串);</li>
 * <li>System.setProperty("sun.net.client.defaultReadTimeout", 超时毫秒数字符串);</li>
 * </ul>
 * <p>
 * 在JDK1.5+,还可以使用HttpURLConnection的父类URLConnection的以下两个方法：
 * </p>
 * <ul>
 * <li>{@link URLConnection#setConnectTimeout(int)}：设置连接主机超时（单位：毫秒）</li>
 * <li>{@link URLConnection#setReadTimeout(int)}：设置从主机读取数据超时（单位：毫秒）</li>
 * </ul>
 *
 * </blockquote>
 * 
 * 
 * <h3>关于使用代理:</h3>
 * 
 * <blockquote>
 * <p>
 * 在JDK5-如果在URLHttpConnection中使用代理服务器的话，只要在URL.openConnection()之前加入以下代码就可以：
 * </p>
 * 
 * <pre>
 * Properties properties = System.getProperties();
 * properties.put(&quot;http.proxyHost&quot;, &quot;120.0.0.1&quot;);
 * properties.put(&quot;http.proxyPort&quot;, &quot;1080&quot;);
 * </pre>
 * 
 * 不过JDK5+ URL增加了一个新的方法 {@link URL#openConnection(Proxy)}，这样就可以直接设置代理地址了，代码如下：
 * 
 * <pre>
 * URL url = new URL("http://www.javatang.com");
 * <span style="color:green">// 设置代理服务</span>
 * SocketAddress add = new InetSocketAddress("120.0.0.1", "1080");
 * Proxy proxy = new Proxy(Proxy.Type.SOCKS , add);
 * <span style="color:green">// 打开连接</span>
 * HttpURLConnection conn = (HttpURLConnection)url.openConnection(proxy);                                                                                                                                                                       .openConnection(p);
 * </pre>
 * 
 * </blockquote>
 *
 * @author feilong
 * @version 1.0 Sep 26, 2013 11:10:59 AM
 * @see java.net.HttpURLConnection
 * @see java.net.URLConnection
 * @since 1.0.2
 */
public final class URLConnectionUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URLConnectionUtil.class);

    //********************************************************************************************
    /** Don't let anyone instantiate this class. */
    private URLConnectionUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //********************************************************************************************

    /**
     * Read line with proxy.
     *
     * @param urlString
     *            the url string
     * @return 读取一个文本行.通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行.
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #readLine(String, HttpURLConnectionParam)
     */
    public static String readLine(String urlString) throws UncheckedIOException{
        return readLine(urlString, null);
    }

    /**
     * Read line with proxy.
     *
     * @param urlString
     *            the url string
     * @param httpURLConnectionParam
     *            the http url connection param
     * @return 读取一个文本行.通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行.
     * @throws UncheckedIOException
     *             the unchecked io exception
     */
    public static String readLine(String urlString,HttpURLConnectionParam httpURLConnectionParam) throws UncheckedIOException{
        InputStream inputStream = getInputStream(urlString, httpURLConnectionParam);
        BufferedReader bufferedReader = InputStreamUtil.toBufferedReader(inputStream, httpURLConnectionParam.getContentCharset());
        return ReaderUtil.readLine(bufferedReader);
    }

    //********************************************************************************************
    /**
     * Gets the response body as string.
     *
     * @param urlString
     *            the url string
     * @return the response body as string
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #getResponseBodyAsString(String, HttpURLConnectionParam)
     */
    public static String getResponseBodyAsString(String urlString) throws UncheckedIOException{
        return getResponseBodyAsString(urlString, null);
    }

    /**
     * 获得 response body as string.
     *
     * @param urlString
     *            the url string
     * @param httpURLConnectionParam
     *            httpURLConnectionParam
     * @return the response body as string
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #getInputStream(String, HttpURLConnectionParam)
     * @see InputStreamUtil#inputStream2String(InputStream, String)
     */
    public static String getResponseBodyAsString(String urlString,HttpURLConnectionParam httpURLConnectionParam)
                    throws UncheckedIOException{
        if (null == httpURLConnectionParam){
            httpURLConnectionParam = new HttpURLConnectionParam();
        }
        InputStream inputStream = getInputStream(urlString, httpURLConnectionParam);
        String inputStream2String = InputStreamUtil.inputStream2String(inputStream, httpURLConnectionParam.getContentCharset());
        return inputStream2String;

    }

    /**
     * 获得 input stream.
     *
     * @param urlString
     *            the url string
     * @return the input stream
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #getInputStream(String, HttpURLConnectionParam)
     */
    public static InputStream getInputStream(String urlString) throws UncheckedIOException{
        return getInputStream(urlString, null);
    }

    /**
     * 获得 input stream.
     *
     * @param urlString
     *            the url string
     * @param httpURLConnectionParam
     *            the http url connection param
     * @return the input stream
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #getInputStream(HttpRequest, HttpURLConnectionParam)
     */
    public static InputStream getInputStream(String urlString,HttpURLConnectionParam httpURLConnectionParam) throws UncheckedIOException{
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUri(urlString);
        return getInputStream(httpRequest, httpURLConnectionParam);
    }

    /**
     * 获得 input stream.
     *
     * @param httpRequest
     *            the http request
     * @param httpURLConnectionParam
     *            the http url connection param
     * @return the input stream
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @since 1.2.0
     */
    public static InputStream getInputStream(HttpRequest httpRequest,HttpURLConnectionParam httpURLConnectionParam)
                    throws UncheckedIOException{
        HttpURLConnection httpURLConnection = getHttpURLConnection(httpRequest, httpURLConnectionParam);
        try{
            InputStream inputStream = httpURLConnection.getInputStream();
            return inputStream;
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            //TODO
            // 指示近期服务器不太可能有其他请求.调用 disconnect() 并不意味着可以对其他请求重用此 HttpURLConnection 实例.
            // httpURLConnection.disconnect();
        }
    }

    // ****************************************************************************************
    /**
     * 获得 {@link java.net.HttpURLConnection}.
     *
     * @param httpRequest
     *            the http request
     * @param httpURLConnectionParam
     *            httpURLConnectionParam
     * @return {@link java.net.HttpURLConnection}
     * @throws UncheckedIOException
     *             the unchecked io exception
     */
    private static HttpURLConnection getHttpURLConnection(HttpRequest httpRequest,HttpURLConnectionParam httpURLConnectionParam)
                    throws UncheckedIOException{
        if (Validator.isNullOrEmpty(httpRequest)){
            throw new NullPointerException("httpRequest can't be null/empty!");
        }

        if (null == httpURLConnectionParam){
            httpURLConnectionParam = new HttpURLConnectionParam();
        }
        try{
            HttpURLConnection httpURLConnection = openConnection(httpRequest, httpURLConnectionParam);

            // **************************************************************************
            int connectTimeout = httpURLConnectionParam.getConnectTimeout();
            int readTimeout = httpURLConnectionParam.getReadTimeout();

            // 一定要为HttpUrlConnection设置connectTimeout属性以防止连接被阻塞
            //设置连接主机超时（单位：毫秒）
            httpURLConnection.setConnectTimeout(connectTimeout);
            //设置从主机读取数据超时（单位：毫秒）
            httpURLConnection.setReadTimeout(readTimeout);

            //这里要大写
            //否则会报  java.net.ProtocolException: Invalid HTTP method: get
            String httpMethod = httpRequest.getHttpMethodType().getMethod().toUpperCase();
            httpURLConnection.setRequestMethod(httpMethod);

            Map<String, String> headerMap = httpRequest.getHeaderMap();
            if (Validator.isNotNullOrEmpty(headerMap)){
                for (Map.Entry<String, String> entry : headerMap.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpURLConnection.setRequestProperty(key, value);
                }
            }

            // httpURLConnection.setDoOutput(true);

            //  此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，所以在开发中不调用上述的connect()也可以). 

            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）.  如果在已打开连接（此时 connected 字段的值为 true）的情况下调用 connect 方法，则忽略该调用.

            // 实际上只是建立了一个与服务器的tcp连接,并没有实际发送http请求. 
            // 无论是post还是get,http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去. 
            // httpURLConnection.connect();
            return httpURLConnection;
        }catch (MalformedURLException e){
            throw new UncheckedIOException(e);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Open connection.
     *
     * @param httpRequest
     *            the http request
     * @param httpURLConnectionParam
     *            the http url connection param
     * @return the http url connection
     * @throws MalformedURLException
     *             the malformed url exception
     * @throws IOException
     *             the IO exception
     * @since 1.2.0
     */
    private static HttpURLConnection openConnection(HttpRequest httpRequest,HttpURLConnectionParam httpURLConnectionParam)
                    throws MalformedURLException,IOException{

        if (null == httpURLConnectionParam){
            httpURLConnectionParam = new HttpURLConnectionParam();
        }

        LOGGER.debug("httpRequest:[{}],httpURLConnectionParam:[{}]", JsonUtil.format(httpRequest), JsonUtil.format(httpURLConnectionParam));

        URL url = new URL(httpRequest.getUri());

        Proxy proxy = getProxy(httpURLConnectionParam.getProxyAddress(), httpURLConnectionParam.getProxyPort());

        HttpURLConnection httpURLConnection = null;
        // 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,
        // 故此处最好将其转化 为HttpURLConnection类型的对象,以便用到 HttpURLConnection更多的API.
        if (Validator.isNotNullOrEmpty(proxy)){
            LOGGER.debug("use proxy:{}", proxy.toString());
            httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
        }else{
            // 每次调用此 URL 的协议处理程序的 openConnection 方法都打开一个新的连接.
            httpURLConnection = (HttpURLConnection) url.openConnection();
        }
        return httpURLConnection;
    }

    // ******************************************************************************************

    /**
     * 获得代理.
     * 
     * @param proxyAddress
     *            the proxy address
     * @param proxyPort
     *            代理端口 <br>
     *            A valid port value is between 0 ~ 65535. <br>
     *            A port number of zero will let the system pick up an ephemeral port in a bind operation.
     * @return the proxy
     * @see java.net.Proxy.Type.HTTP
     * @see java.net.InetSocketAddress#InetSocketAddress(String, int)
     */
    private static Proxy getProxy(String proxyAddress,Integer proxyPort){
        Proxy proxy = null;
        if (Validator.isNotNullOrEmpty(proxyAddress) && Validator.isNotNullOrEmpty(proxyPort)){
            SocketAddress socketAddress = new InetSocketAddress(proxyAddress, proxyPort);
            proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
        }
        return proxy;
    }
}
