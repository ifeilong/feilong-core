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

import java.io.Serializable;
import java.util.Map;

/**
 * http 请求信息.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.2.0 2015年5月23日 上午12:43:38
 * @see "org.apache.http.HttpRequest"
 * @since 1.2.0
 */
public class HttpRequest implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long   serialVersionUID = -6122597808127208467L;

    /** 请求的uri地址. */
    private String              uri;

    /** 请求method 类型,默认 {@link HttpMethodType#GET}. */
    private HttpMethodType      httpMethodType   = HttpMethodType.GET;

    /** 请求参数. */
    private Map<String, String> paramMap;

    /** 请求头 信息. */
    private Map<String, String> headerMap;

    /**
     * 获得 请求的uri地址.
     *
     * @return the 请求的uri地址
     */
    public String getUri(){
        return uri;
    }

    /**
     * 设置 请求的uri地址.
     *
     * @param uri
     *            the new 请求的uri地址
     */
    public void setUri(String uri){
        this.uri = uri;
    }

    /**
     * 获得 请求method 类型,默认 {@link HttpMethodType#GET}.
     *
     * @return the httpMethodType
     */
    public HttpMethodType getHttpMethodType(){
        return httpMethodType;
    }

    /**
     * 设置 请求method 类型,默认 {@link HttpMethodType#GET}.
     *
     * @param httpMethodType
     *            the httpMethodType to set
     */
    public void setHttpMethodType(HttpMethodType httpMethodType){
        this.httpMethodType = httpMethodType;
    }

    /**
     * 获得 请求参数.
     *
     * @return the paramMap
     */
    public Map<String, String> getParamMap(){
        return paramMap;
    }

    /**
     * 设置 请求参数.
     *
     * @param paramMap
     *            the paramMap to set
     */
    public void setParamMap(Map<String, String> paramMap){
        this.paramMap = paramMap;
    }

    /**
     * 获得 请求头 信息.
     *
     * @return the headerMap
     */
    public Map<String, String> getHeaderMap(){
        return headerMap;
    }

    /**
     * 设置 请求头 信息.
     *
     * @param headerMap
     *            the headerMap to set
     */
    public void setHeaderMap(Map<String, String> headerMap){
        this.headerMap = headerMap;
    }

}
