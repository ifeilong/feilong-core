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

import com.feilong.core.bean.ConvertUtil;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class URLUtilTest.
 *
 * @author feilong
 * @version 1.4.0 2015年8月1日 下午7:53:36
 * @since 1.4.0
 */
public class URLUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(URLUtilTest.class);

    /**
     * Test get union url.
     */
    @Test
    public void testGetUnionUrl(){
        LOGGER.info(URLUtil.getUnionFileUrl("E:\\test", "sanguo"));
    }

    /**
     * Test get union url2.
     */
    @Test
    public void testGetUnionUrl2(){
        URL url = URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing/");
        LOGGER.info(URLUtil.getUnionUrl(url, "/jinyiyexing/1173348/"));
    }

    /**
     * Test get union url3.
     */
    @Test
    public void testGetUnionUrl3(){
        URL[] urls = {
                       URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing0/"),
                       URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing1/"),
                       URLUtil.newURL("http://www.exiaoshuo.com/jinyiyexing2/") };

        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings(urls)));
        LOGGER.debug(JsonUtil.format(URLUtil.toStringArray(urls)));

    }
}
