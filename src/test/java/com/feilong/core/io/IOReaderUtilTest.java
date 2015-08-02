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
package com.feilong.core.io;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.CharsetType;

/**
 * The Class IOReaderUtilTest.
 * 
 * @author feilong
 * @version 1.0 Dec 23, 2013 10:28:59 PM
 */
public class IOReaderUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IOReaderUtilTest.class);

    /**
     * Testname.
     *
     * @throws IOException
     *             the IO exception
     */
    @Test
    public void testname() throws IOException{
        String fileName = "F:\\Life 生活\\Job 工作\\淘宝开店\\商家编码.txt";
        String content = IOReaderUtil.getFileContent(fileName, CharsetType.UTF8);
        // 将内容以换行符转成数组
        String[] rowsContents = content.split("\r\n");
        LOGGER.info(content);
        LOGGER.info("" + rowsContents.length);
    }

    /**
     * Parses the nginx.
     *
     * @throws IOException
     *             the IO exception
     */
    @Test
    public void parseNginx() throws IOException{
        String fileName = "C:\\Users\\feilong\\Documents\\AJ11\\AJ11\\1.txt";
        String content = IOReaderUtil.getFileContent(fileName, CharsetType.UTF8);

        content.split("");

        // 将内容以换行符转成数组
        // String[] rowsContents = content.split("\r\n");
        LOGGER.info(content);
    }

    /**
     * Gets the file content.
     *
     * @throws IOException
     *             the IO exception
     */
    @Test
    public void testGetFileContent() throws IOException{
        String propertiesPath = "I:/Ebook/book.properties";
        LOGGER.info(IOReaderUtil.getFileContent(propertiesPath, CharsetType.UTF8));
    }
}
