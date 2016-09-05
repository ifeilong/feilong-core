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
package com.feilong.core.net.uriutiltest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.net.URIUtil;

import static com.feilong.core.CharsetType.UTF8;

/**
 * The Class URIUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class URIUtilEncodeTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URIUtilEncodeTest.class);

    //******************************************************************************************************
    /**
     * Test encode.
     */
    @Test
    public void testEncode(){
        String value = "={}[]今天天气很不错今天天气很不错今天天气很不错今天天气很不错今天天气很不错";
        value = "http://xy2.cbg.163.com/cgi-bin/equipquery.py?server_name=风花雪月&query_order=selling_time DESC&search_page&areaid=2&server_id=63&act=search_browse&equip_type_ids&search_text=斩妖剑";
        value = "斩妖剑";
        value = "风花雪月";
        LOGGER.debug(URIUtil.encode(value, UTF8));
        value = "景儿,么么哒";
        LOGGER.debug(URIUtil.encode(value, UTF8));
        LOGGER.debug(URIUtil.encode("白色/黑色/纹理浅麻灰", UTF8));
        LOGGER.debug(URIUtil.encode("Lifestyle / Graphic,", UTF8));
    }

    /**
     * Test encode2.
     */
    @Test
    public void testEncode2(){
        LOGGER.debug(URIUtil.encode("%", UTF8));
        LOGGER.debug(URIUtil.encode("%25", UTF8));
    }
}
