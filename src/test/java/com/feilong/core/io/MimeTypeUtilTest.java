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
package com.feilong.core.io;

import static com.feilong.core.io.MimeTypeUtil.getContentTypeByFileName;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class MimeTypeUtilTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.8 2014年11月19日 上午12:15:27
 * @since 1.0.8
 */
public class MimeTypeUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(MimeTypeUtilTest.class);

    /**
     * Test method for {@link com.feilong.core.io.MimeTypeUtil#getContentTypeByFileName(java.lang.String)}.
     */
    @Test
    public final void testGetContentTypeByFileName(){
        log.debug(getContentTypeByFileName("E:\\2009 阿凡达 詹姆斯·卡梅隆 178分钟加长收藏版.mkv"));
        log.debug(getContentTypeByFileName("E:\\2009 阿凡达 詹姆斯·卡梅隆 178分钟加长收藏版.oxt"));
        log.debug(getContentTypeByFileName("E:\\2009 阿凡达 詹姆斯·卡梅隆 178分钟加长收藏版.jpg"));
        log.debug(getContentTypeByFileName("E:\\2009 阿凡达 詹姆斯·卡梅隆 178分钟加长收藏版.js"));
        log.debug(getContentTypeByFileName("E:\\2009 阿凡达 詹姆斯·卡梅隆 178分钟加长收藏版.css"));
        log.debug(getContentTypeByFileName("E:\\2009 阿凡达 詹姆斯·卡梅隆 178分钟加长收藏版.ppt"));
        log.debug(getContentTypeByFileName("E:\\2009 阿凡达 詹姆斯·卡梅隆 178分钟加长收藏版.pdf"));
    }
}
