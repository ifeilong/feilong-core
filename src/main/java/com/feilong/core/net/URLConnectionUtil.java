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

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.InputStreamUtil;
import com.feilong.core.io.ReaderUtil;
import com.feilong.core.io.UncheckedIOException;
import com.feilong.core.tools.jsonlib.JsonUtil;
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
 * 在JDK1.5- 版本中,只能通过以下两个语句来设置相应的超时：
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
 * 在JDK5-如果在URLHttpConnection中使用代理服务器的话,只要在URL.openConnection()之前加入以下代码就可以：
 * </p>
 * 
 * <pre>
 * Properties properties = System.getProperties();
 * properties.put(&quot;http.proxyHost&quot;, &quot;120.0.0.1&quot;);
 * properties.put(&quot;http.proxyPort&quot;, &quot;1080&quot;);
 * </pre>
 * 
 * 不过JDK5+ URL增加了一个新的方法 {@link URL#openConnection(Proxy)},这样就可以直接设置代理地址了,代码如下：
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
 * 
 * <h3>关于 {@link HttpURLConnection#setDoInput(boolean)} && {@link HttpURLConnection#setDoOutput(boolean)}</h3>
 * 
 * 
 * <blockquote>
 * 
 * 
 * <table border="1" cellspacing="0" cellpadding="4">
 * <tr style="background-color:#ccccff">
 * <th align="left">字段</th>
 * <th align="left">说明</th>
 * </tr>
 * <tr valign="top">
 * <td>httpUrlConnection.setDoOutput(true);</td>
 * <td>以后就可以使用conn.getOutputStream().write()<br>
 * get请求用不到conn.getOutputStream(),因为参数直接追加在地址后面,因此默认是false<br>
 * post请求（比如：文件上传）需要往服务区传输大量的数据,这些数据是放在http的body里面的,因此需要在建立连接以后,往服务端写数据<br>
 * URL连接可用于输出。如果打算使用URL连接进行输出,则将DoOutput标志设置为true；如果不打算使用,则设置为 false。默认值为 false。</td>
 * </tr>
 * <tr valign="top" style="background-color:#eeeeff">
 * <td>httpUrlConnection.setDoInput(true);</td>
 * <td>以后就可以使用conn.getInputStream().read(); <br>
 * 因为总是使用conn.getInputStream()获取服务端的响应,因此默认值是true<br>
 * URL连接可用于输入。如果打算使用URL连接进行输入,则将DoInput标志设置为true；如果不打算使用,则设置为 false。默认值为 true。 </td>
 * </tr>
 * </table>
 * 
 * <p>
 * 您也可以参见 {@link "org.springframework.http.client.SimpleClientHttpRequestFactory#prepareConnection(HttpURLConnection, String)"} 的实现细节
 * </p>
 * </blockquote>
 * 
 * 
 * <h3>关于 {@link HttpURLConnection#connect()} && {@link HttpURLConnection#getOutputStream()}</h3>
 * 
 * <blockquote>
 * <p>
 * getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法,所以在开发中不调用 httpURLConnection.connect(); 也可以).<br>
 * 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）.如果在已打开连接（此时 connected 字段的值为 true）的情况下调用 connect 方法,则忽略该调用.<br>
 * 实际上只是建立了一个与服务器的tcp连接,并没有实际发送http请求.<br>
 * 无论是post还是get,http请求实际上直到HttpURLConnection的getInputStream()这个函数里面才正式发送出去. 
 * </p>
 * </blockquote>
 *
 * @author feilong
 * @version 1.0 Sep 26, 2013 11:10:59 AM
 * @see java.net.HttpURLConnection
 * @see java.net.URLConnection
 * @see "org.springframework.http.client.SimpleClientHttpRequestFactory"
 * @since 1.0.2
 */
public final class URLConnectionUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URLConnectionUtil.class);

    /** Don't let anyone instantiate this class. */
    private URLConnectionUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Read line with proxy.
     *
     * @param urlString
     *            the url string
     * @return 读取一个文本行.通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行.
     * @see #readLine(String, ConnectionConfig)
     */
    public static String readLine(String urlString){
        return readLine(urlString, null);
    }

    /**
     * Read line with proxy.
     *
     * @param urlString
     *            the url string
     * @param connectionConfig
     *            the connection config
     * @return 读取一个文本行.通过下列字符之一即可认为某行已终止：换行 ('\n')、回车 ('\r') 或回车后直接跟着换行.
     */
    public static String readLine(String urlString,ConnectionConfig connectionConfig){
        InputStream inputStream = getInputStream(urlString, connectionConfig);
        BufferedReader bufferedReader = InputStreamUtil.toBufferedReader(inputStream, connectionConfig.getContentCharset());
        return ReaderUtil.readLine(bufferedReader);
    }

    //********************************************************************************************
    /**
     * Gets the response body as string.
     *
     * @param urlString
     *            the url string
     * @return the response body as string
     * @see #getResponseBodyAsString(String, ConnectionConfig)
     */
    public static String getResponseBodyAsString(String urlString){
        return getResponseBodyAsString(urlString, null);
    }

    /**
     * 获得 response body as string.
     *
     * @param urlString
     *            the url string
     * @param connectionConfig
     *            connectionConfig
     * @return the response body as string
     * @see #getInputStream(String, ConnectionConfig)
     * @see InputStreamUtil#inputStream2String(InputStream, String)
     */
    public static String getResponseBodyAsString(String urlString,ConnectionConfig connectionConfig){
        ConnectionConfig useConnectionConfig = null == connectionConfig ? new ConnectionConfig() : connectionConfig;
        InputStream inputStream = getInputStream(urlString, useConnectionConfig);
        return InputStreamUtil.inputStream2String(inputStream, useConnectionConfig.getContentCharset());

    }

    /**
     * 获得 response body as string.
     *
     * @param httpRequest
     *            the http request
     * @param connectionConfig
     *            the connection config
     * @return the response body as string
     * @see #getInputStream(HttpRequest, ConnectionConfig)
     * @see InputStreamUtil#inputStream2String(InputStream, String)
     * @since 1.5.0
     */
    public static String getResponseBodyAsString(HttpRequest httpRequest,ConnectionConfig connectionConfig){
        ConnectionConfig useConnectionConfig = null == connectionConfig ? new ConnectionConfig() : connectionConfig;
        InputStream inputStream = getInputStream(httpRequest, connectionConfig);
        return InputStreamUtil.inputStream2String(inputStream, useConnectionConfig.getContentCharset());

    }

    /**
     * 获得 input stream.
     *
     * @param urlString
     *            the url string
     * @return the input stream
     * @see #getInputStream(String, ConnectionConfig)
     */
    public static InputStream getInputStream(String urlString){
        return getInputStream(urlString, null);
    }

    /**
     * 获得 input stream.
     *
     * @param urlString
     *            the url string
     * @param connectionConfig
     *            the connection config
     * @return the input stream
     * @see #getInputStream(HttpRequest, ConnectionConfig)
     */
    public static InputStream getInputStream(String urlString,ConnectionConfig connectionConfig){
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setUri(urlString);
        return getInputStream(httpRequest, connectionConfig);
    }

    /**
     * 获得 input stream.
     *
     * @param httpRequest
     *            the http request
     * @param connectionConfig
     *            the connection config
     * @return the input stream
     * @see "org.springframework.core.io.UrlResource#getInputStream()"
     * @since 1.2.0
     */
    public static InputStream getInputStream(HttpRequest httpRequest,ConnectionConfig connectionConfig){
        HttpURLConnection httpURLConnection = getHttpURLConnection(httpRequest, connectionConfig);
        try{
            return httpURLConnection.getInputStream();
        }catch (IOException e){
            //指示近期服务器不太可能有其他请求.调用 disconnect() 并不意味着可以对其他请求重用此 HttpURLConnection 实例.
            //不能写在 finally 里面, 否则调用者拿不到inputstream

            // per Java's documentation, this is not necessary, and precludes keepalives. However in practise,
            // connection errors will not be released quickly enough and can cause a too many open files error.
            IOUtils.close(httpURLConnection); // Close the HTTP connection (if applicable). 

            throw new UncheckedIOException(e);
        }
    }

    // ****************************************************************************************
    /**
     * 获得 {@link java.net.HttpURLConnection}.
     *
     * @param httpRequest
     *            the http request
     * @param connectionConfig
     *            the connection config
     * @return {@link java.net.HttpURLConnection}
     */
    private static HttpURLConnection getHttpURLConnection(HttpRequest httpRequest,ConnectionConfig connectionConfig){
        if (Validator.isNullOrEmpty(httpRequest)){
            throw new NullPointerException("httpRequest can't be null/empty!");
        }

        ConnectionConfig useConnectionConfig = null == connectionConfig ? new ConnectionConfig() : connectionConfig;
        try{
            HttpURLConnection httpURLConnection = openConnection(httpRequest, useConnectionConfig);
            prepareConnection(httpURLConnection, httpRequest, useConnectionConfig);
            return httpURLConnection;
        }catch (MalformedURLException e){
            throw new UncheckedIOException(e);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Prepare connection.
     *
     * @param httpURLConnection
     *            the http url connection
     * @param httpRequest
     *            the http request
     * @param useConnectionConfig
     *            the use connection config
     * @throws IOException
     *             the IO exception
     * @see "org.springframework.http.client.SimpleClientHttpRequestFactory#prepareConnection(HttpURLConnection, String)"
     * @since 1.4.0
     */
    private static void prepareConnection(HttpURLConnection httpURLConnection,HttpRequest httpRequest,ConnectionConfig useConnectionConfig)
                    throws IOException{
        HttpMethodType httpMethodType = httpRequest.getHttpMethodType();

        // 一定要为HttpUrlConnection设置connectTimeout属性以防止连接被阻塞
        httpURLConnection.setConnectTimeout(useConnectionConfig.getConnectTimeout());
        httpURLConnection.setReadTimeout(useConnectionConfig.getReadTimeout());

        httpURLConnection.setRequestMethod(httpMethodType.getMethod().toUpperCase());//这里要大写,否则会报  java.net.ProtocolException: Invalid HTTP method: get

        httpURLConnection.setDoOutput(HttpMethodType.POST == httpMethodType);

        //**********************************************
        //设置默认的UA， 你可以使用headerMap来覆盖
        httpURLConnection.setRequestProperty("User-Agent", HttpRequest.DEFAULT_USER_AGENT);
        //**********************************************

        Map<String, String> headerMap = httpRequest.getHeaderMap();
        if (null != headerMap){
            for (Map.Entry<String, String> entry : headerMap.entrySet()){
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Open connection.
     *
     * @param httpRequest
     *            the http request
     * @param connectionConfig
     *            the connection config
     * @return the http url connection
     * @throws IOException
     *             the IO exception
     * @since 1.2.0
     */
    private static HttpURLConnection openConnection(HttpRequest httpRequest,ConnectionConfig connectionConfig) throws IOException{
        ConnectionConfig useConnectionConfig = null == connectionConfig ? new ConnectionConfig() : connectionConfig;
        LOGGER.debug("httpRequest:[{}],useConnectionConfig:[{}]", JsonUtil.format(httpRequest), JsonUtil.format(useConnectionConfig));
        URL url = new URL(httpRequest.getUri());

        Proxy proxy = getProxy(useConnectionConfig.getProxyAddress(), useConnectionConfig.getProxyPort());

        // 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,
        // 故此处最好将其转化 为HttpURLConnection类型的对象,以便用到 HttpURLConnection更多的API.
        if (Validator.isNotNullOrEmpty(proxy)){
            LOGGER.debug("use proxy:{}", proxy.toString());
            return (HttpURLConnection) url.openConnection(proxy);
        }
        // 每次调用此 URL 的协议处理程序的 openConnection 方法都打开一个新的连接.
        return (HttpURLConnection) url.openConnection();
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
        if (Validator.isNotNullOrEmpty(proxyAddress) && Validator.isNotNullOrEmpty(proxyPort)){
            SocketAddress socketAddress = new InetSocketAddress(proxyAddress, proxyPort);
            return new Proxy(Proxy.Type.HTTP, socketAddress);
        }
        return null;
    }
}
