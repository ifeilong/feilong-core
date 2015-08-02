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

import com.feilong.core.date.TimeInterval;
import com.feilong.core.lang.CharsetType;

/**
 * 链接参数.
 * 
 * @author feilong
 * @version 1.3.0 2015年7月29日 上午12:31:58
 * @since 1.3.0
 */
public final class ConnectionConfig{

    /** 代理地址. */
    private String  proxyAddress;

    /**
     * 代理端口 <br>
     * A valid port value is between 0 ~ 65535. <br>
     * A port number of zero will let the system pick up an ephemeral port in a bind operation.
     */
    private Integer proxyPort;

    /**
     * 设置一个指定的超时值（以毫秒为单位），该值将在打开到此 URLConnection 引用的资源的通信链接时使用.<br>
     * 超时时间为零表示无穷大超时.<br>
     * 如果在建立连接之前超时期满，则会引发一个 {@link java.net.SocketTimeoutException}.
     */
    private int     connectTimeout = TimeInterval.SECONDS_PER_MINUTE * 1000;

    /**
     * 将读超时设置为指定的超时值，以毫秒为单位.用一个非零值指定在建立到资源的连接后从 Input 流读入时的超时时间.<br>
     * 超时时间为零表示无穷大超时.<br>
     * 如果在数据可读取之前超时期满，则会引发一个 {@link java.net.SocketTimeoutException}.
     */
    private int     readTimeout    = TimeInterval.SECONDS_PER_MINUTE * 1000;

    /** 内容的字符集. */
    private String  contentCharset = CharsetType.UTF8;

    /**
     * Gets the 设置一个指定的超时值（以毫秒为单位），该值将在打开到此 URLConnection 引用的资源的通信链接时使用.<br>
     * 超时时间为零表示无穷大超时.<br>
     * 如果在建立连接之前超时期满，则会引发一个 java.
     * 
     * @return the connectTimeout
     */
    public int getConnectTimeout(){
        return connectTimeout;
    }

    /**
     * Sets the 设置一个指定的超时值（以毫秒为单位），该值将在打开到此 URLConnection 引用的资源的通信链接时使用.<br>
     * 超时时间为零表示无穷大超时.<br>
     * 如果在建立连接之前超时期满，则会引发一个 java.
     * 
     * @param connectTimeout
     *            the connectTimeout to set
     */
    public void setConnectTimeout(int connectTimeout){
        this.connectTimeout = connectTimeout;
    }

    /**
     * Gets the 将读超时设置为指定的超时值，以毫秒为单位.用一个非零值指定在建立到资源的连接后从 Input 流读入时的超时时间.<br>
     * 超时时间为零表示无穷大超时.<br>
     * 如果在数据可读取之前超时期满，则会引发一个 java.
     * 
     * @return the readTimeout
     */
    public int getReadTimeout(){
        return readTimeout;
    }

    /**
     * Sets the 将读超时设置为指定的超时值，以毫秒为单位.用一个非零值指定在建立到资源的连接后从 Input 流读入时的超时时间.<br>
     * 超时时间为零表示无穷大超时.<br>
     * 如果在数据可读取之前超时期满，则会引发一个 java.
     * 
     * @param readTimeout
     *            the readTimeout to set
     */
    public void setReadTimeout(int readTimeout){
        this.readTimeout = readTimeout;
    }

    /**
     * 获得 内容的字符集.
     *
     * @return the contentCharset
     */
    public String getContentCharset(){
        return contentCharset;
    }

    /**
     * 设置 内容的字符集.
     *
     * @param contentCharset
     *            the contentCharset to set
     */
    public void setContentCharset(String contentCharset){
        this.contentCharset = contentCharset;
    }

    /**
     * 获得 the proxy address.
     *
     * @return the proxyAddress
     */
    public String getProxyAddress(){
        return proxyAddress;
    }

    /**
     * 设置 the proxy address.
     *
     * @param proxyAddress
     *            the proxyAddress to set
     */
    public void setProxyAddress(String proxyAddress){
        this.proxyAddress = proxyAddress;
    }

    /**
     * 获得 代理端口 <br>
     * A valid port value is between 0 ~ 65535.
     *
     * @return the proxyPort
     */
    public Integer getProxyPort(){
        return proxyPort;
    }

    /**
     * 设置 代理端口 <br>
     * A valid port value is between 0 ~ 65535.
     *
     * @param proxyPort
     *            the proxyPort to set
     */
    public void setProxyPort(Integer proxyPort){
        this.proxyPort = proxyPort;
    }
}
