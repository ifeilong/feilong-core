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
package com.feilong.core;

/**
 * uri中使用的字符常量.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "org.springframework.web.util.HierarchicalUriComponents.Type"
 * @see "org.springframework.web.util.UriComponentsBuilder"
 * @see "org.springframework.web.util.UriComponents"
 * @since 1.0.9
 */
public final class URIComponents{

    /** 查询片段 <code>{@value}</code>. */
    public static final String FRAGMENT     = "#";

    /** <code>{@value}</code> The question mark is used as a separator and is not part of the query string. */
    public static final String QUESTIONMARK = "?";

    /** The Constant ampersand<code>{@value}</code>. */
    public static final String AMPERSAND    = "&";

    //---------------------------------------------------------------

    /**
     * http协议<code>{@value}</code>.
     * 
     * <p>
     * 超文本传输协议(HTTP,HyperText Transfer Protocol)是互联网上应用最为广泛的一种网络协议。
     * </p>
     */
    public static final String SCHEME_HTTP  = "http";

    /**
     * https协议<code>{@value}</code>.
     * 
     * <p>
     * 全称：Hyper Text Transfer Protocol over Secure Socket Layer; <br>
     * 是以安全为目标的HTTP通道,简单讲是HTTP的安全版。<br>
     * 即HTTP下加入SSL层,HTTPS的安全基础是SSL,因此加密的详细内容就需要SSL。
     * </p>
     * 
     * <h3>HTTPS和HTTP的区别主要为以下四点:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>https协议需要到ca申请证书,一般免费证书很少,需要交费。</li>
     * <li>http是超文本传输协议,信息是明文传输,https 则是具有安全性的ssl加密传输协议。</li>
     * <li>http和https使用的是完全不同的连接方式,用的端口也不一样,前者是80,后者是443。</li>
     * <li>http的连接很简单,是无状态的；HTTPS协议是由SSL+HTTP协议构建的可进行加密传输、身份认证的网络协议,比http协议安全。</li>
     * </ol>
     * </blockquote>
     */
    public static final String SCHEME_HTTPS = "https";

    //---------------------------------------------------------------

    /** Don't let anyone instantiate this class. */
    private URIComponents(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
