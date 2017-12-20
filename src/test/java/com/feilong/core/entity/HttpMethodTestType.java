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
package com.feilong.core.entity;

import com.feilong.core.lang.EnumUtil;

/**
 * http请求方法,目前仅支持通用的get和post 其他不支持.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "org.springframework.http.HttpMethod"
 * @since 1.7.3 move from feilong-core
 */
public enum HttpMethodTestType{

    /** get方式. */
    GET("get"),

    /** post方式. */
    POST("post");

    //---------------------------------------------------------------
    /** The method. */
    private String method;

    /**
     * Instantiates a new http method type.
     * 
     * @param method
     *            the method
     */
    private HttpMethodTestType(String method){
        this.method = method;
    }

    /**
     * Gets the by method value ignore case.
     *
     * @param methodValue
     *            the method value
     * @return the by method value ignore case
     * @see EnumUtil#getEnumByPropertyValueIgnoreCase(Class, String, Object)
     * @since 1.0.8
     */
    public static HttpMethodTestType getByMethodValueIgnoreCase(String methodValue){
        String propertyName = "method";
        return EnumUtil.getEnumByPropertyValueIgnoreCase(HttpMethodTestType.class, propertyName, methodValue);
    }

    /**
     * Gets the method.
     * 
     * @return the method
     */
    public String getMethod(){
        return method;
    }

}
