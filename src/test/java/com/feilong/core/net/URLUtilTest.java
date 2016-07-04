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

import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class URLUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.4.0
 */
public class URLUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URLUtilTest.class);

    @Test
    public void testGetUnionUrl1(){
        LOGGER.debug(getUnionFileUrl("E:\\test", "sanguo"));
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
     * @see #toFileURL(String)
     * @see #getUnionUrl(URL, String)
     * @since 1.4.0
     */
    public static String getUnionFileUrl(String context,String spec){
        URL parentUrl = URLUtil.toFileURL(context);
        return URLUtil.getUnionUrl(parentUrl, spec);
    }

    /**
     * Test get union url2.
     */
    @Test
    public void testGetUnionUrl2(){
        URL url = URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing/");
        LOGGER.debug(URLUtil.getUnionUrl(url, "/jinyiyexing/1173348/"));
    }

    @Test
    public void testNewURL(){
        String spec = "C:\\Users\\feilong\\feilong\\train\\新员工\\warmReminder\\20160704141057.html";
        URL url = URLUtil.newURL(spec);
        LOGGER.debug("the param url:{}", url);
    }

}
