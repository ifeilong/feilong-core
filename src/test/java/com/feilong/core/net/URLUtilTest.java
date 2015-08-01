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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.SpecialFolder;

/**
 *
 * @author feilong
 * @version 1.3.1 2015年8月1日 下午7:53:36
 * @since 1.3.1
 */
public class URLUtilTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(URLUtilTest.class);

    /**
     * Down.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void down() throws IOException{
        String url = "http://www.kenwen.com/egbk/31/31186/4395342.txt";
        String directoryName = SpecialFolder.getDesktop();
        URLUtil.download(url, directoryName);
    }

    /**
     * Test get union url.
     */
    @Test
    public void testGetUnionUrl(){
        LOGGER.info(URLUtil.getUnionFileUrl("E:\\test", "sanguo"));
    }

    /**
     * Test get union url2.
     *
     * @throws MalformedURLException
     *             the malformed url exception
     */
    @Test
    public void testGetUnionUrl2() throws MalformedURLException{
        URL url = new URL("http://www.exiaoshuo.com/jinyiyexing/");
        LOGGER.info(URLUtil.getUnionUrl(url, "/jinyiyexing/1173348/"));
    }
}
