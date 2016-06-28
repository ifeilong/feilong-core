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

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.CharsetType;
import com.feilong.core.URIComponents;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class URIUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class URIUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URIUtilTest.class);

    /**
     * Test encode.
     */
    @Test
    public void testEncode(){
        String value = "={}[]今天天气很不错今天天气很不错今天天气很不错今天天气很不错今天天气很不错";
        value = "http://xy2.cbg.163.com/cgi-bin/equipquery.py?server_name=风花雪月&query_order=selling_time DESC&search_page&areaid=2&server_id=63&act=search_browse&equip_type_ids&search_text=斩妖剑";
        value = "斩妖剑";
        value = "风花雪月";
        LOGGER.debug(URIUtil.encode(value, CharsetType.UTF8));
        value = "景儿,么么哒";
        LOGGER.debug(URIUtil.encode(value, CharsetType.UTF8));
        LOGGER.debug(URIUtil.encode("白色/黑色/纹理浅麻灰", CharsetType.UTF8));
        LOGGER.debug(URIUtil.encode("Lifestyle / Graphic,", CharsetType.UTF8));
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString(){
        String value = "http://xy2.cbg.163.com/cgi-bin/equipquery.py?server_name=风花雪月&query_order=selling_time DESC&search_page&areaid=2&server_id=63&act=search_browse&equip_type_ids&search_text=斩妖剑";
        value = "http://xy2.cbg.163.com/cgi-bin/equipquery.py?server_name=风花雪月&query_order=selling_time";
        URI uri = URIUtil.newURI(value);
        LOGGER.debug("{}", uri.toString());

    }

    /**
     * Test encode2.
     */
    @Test
    public void testEncode2(){
        LOGGER.debug(URIUtil.encode("%", CharsetType.UTF8));
        LOGGER.debug(URIUtil.encode("%25", CharsetType.UTF8));
    }

    /**
     * Decode.
     */
    @Test
    public void decode(){
        LOGGER.debug(
                        URIUtil.decode(
                                        "%E9%87%91%E6%80%BB%EF%BC%8C%E4%BD%A0%E6%83%B3%E6%80%8E%E4%B9%88%E4%B9%88%EF%BC%8C%E5%B0%B1%E6%80%8E%E4%B9%88%E4%B9%88",
                                        CharsetType.UTF8));

    }

    /**
     * Decode2.
     */
    @Test
    public void decode2(){
        LOGGER.debug(URIUtil.decode("aaaaa%chu111", CharsetType.UTF8));

    }

    /**
     * Decode3.
     */
    @Test
    public void decode3(){
        LOGGER.debug(URIUtil.decode("%c", CharsetType.UTF8));
    }

    /**
     * Special char to hex string.
     */
    @Test
    public void specialCharToHexString(){
        LOGGER.debug(specialCharToHexString(" "));
    }

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
        specialCharacterMap.put(URIComponents.AMPERSAND, "%26");// URL 中指定的参数间的分隔符
        specialCharacterMap.put("=", "%3D");// URL 中指定参数的值

        if (specialCharacterMap.containsKey(specialCharacter)){
            return specialCharacterMap.get(specialCharacter);
        }
        // 不是 url 特殊字符 原样输出
        return specialCharacter;
    }

    /**
     * Creates the.
     */
    @Test
    public void create(){
        String url = "http://127.0.0.1/cmens/t-b-f-a-c-s-f-p-g-e-i-o.htm?a=1&a=2";
        URI uri = URIUtil.create(url, CharsetType.UTF8);
        LOGGER.debug(uri.toString());
    }

    /**
     * Test create2.
     */
    @Test
    public void testCreate2(){
        URI uri = URIUtil.create("http://127.0.0.1/cmens/t-b-f-a-c-s-f-p-g-e-i-o;a=2,4;p=3", CharsetType.UTF8);
        LOGGER.debug(JsonUtil.format(uri));
    }

    /**
     * Test create3.
     */
    @Test
    public void testCreate3(){
        String uriString = "http://127.0.0.1/cmens?a=%";
        LOGGER.debug("" + URI.create(uriString));
    }

    /**
     * Test get query string.
     */
    @Test
    public void testGetQueryString(){
        assertEquals("a=1&a=2", URIUtil.getQueryString("http://127.0.0.1/cmens/t-b-f-a-c-s-f-p-g-e-i-o.htm?a=1&a=2"));
        assertEquals("a=1&a=2?a", URIUtil.getQueryString("http://127.0.0.1/cmens/t-b-f-a-c-s-f-p-g-e-i-o.htm?a=1&a=2?a"));
        assertEquals("", URIUtil.getQueryString("?"));
        assertEquals("a", URIUtil.getQueryString("?a"));
    }

    /**
     * Test create4.
     */
    @Test
    public void testCreate4(){
        String uriString = "http://127.0.0.1/cmens?a=%";
        URI uri = URIUtil.create(uriString, CharsetType.UTF8);
        LOGGER.debug(uri.toString());
    }
}
