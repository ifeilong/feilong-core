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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.io.FileUtil;
import com.feilong.core.io.UncheckedIOException;

/**
 * The Class FileUtilTest.
 * 
 * @author feilong
 * @version 1.0 2012-5-23 下午5:04:22
 */
public class FileUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER       = LoggerFactory.getLogger(FileUtilTest.class);

    /** The file name1. */
    private String              fileName1 = "F:/pie2.png";

    /** The string. */
    private final String        fString   = "/home/webuser/nike_int/johnData/${date}/nikeid_pix_${typeName}.csv";

    /**
     * Test get content length.
     */
    @Test
    public void testGetContentLength(){
        try{
            URL url = new URL("http://www.jinbaowang.cn/images//20110722/096718c3d1c9b4a1.jpg");
            URLConnection urlConnection = url.openConnection();
            int contentLength = urlConnection.getContentLength();
            LOGGER.info(FileUtil.formatSize(contentLength));
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
        try{
            URL url = new URL("http://localhost:8080/TestHttpURLConnectionPro/index.jsp");
            url.openConnection();
        }catch (MalformedURLException e){
            LOGGER.error(e.getClass().getName(), e);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Test get p.
     */
    @Test
    // @Ignore
    public void testGetP(){
        File file = new File(fString);
        LOGGER.info(file.getAbsolutePath());
        LOGGER.info(file.getParent());
    }

    /**
     * Test get new file name.
     */
    @Test
    public void testGetNewFileName(){
        assertEquals("F:/pie2.gif", FileUtil.getNewFileName(fileName1, "gif"));
    }

    /**
     * List files.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void listFiles() throws IOException{
        String localPath = "E:\\Workspaces\\baozun-else\\mp2-new\\mp2-configuration\\project\\mp2-web\\mp2-livechat\\dev";
        // 读取localPath目录下的全部properties文件
        File file = new File(localPath);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++){
            LOGGER.info("File:" + files[i].getCanonicalPath());
        }
    }

    /**
     * Checks if is empty directory.
     */
    @Test
    public void isEmptyDirectory(){
        // 不存在的文件
        try{
            FileUtil.isEmptyDirectory("E:\\test\\1\\2011-07-07\\test\\1\\2011-07-07");
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }

        // 文件
        try{
            FileUtil.isEmptyDirectory("E:\\1.txt");
            Assert.fail();
        }catch (IllegalArgumentException e){
            Assert.assertTrue(true);
        }

        // 非空目录
        Assert.assertEquals(false, FileUtil.isEmptyDirectory("E:\\Workspaces"));

        // 正确的 空目录
        Assert.assertEquals(true, FileUtil.isEmptyDirectory("E:\\empty"));

    }

    /**
     * Creates the directory.
     */
    @Test
    public void testCreateDirectory(){
        String folderPath = "E:\\test\\1\\2011-07-07\\test\\1\\2011-07-07";
        FileUtil.createDirectory(folderPath);
    }

    /**
     * Test create directory by file path.
     */
    @Test
    public void testCreateDirectoryByFilePath(){
        FileUtil.createDirectoryByFilePath("E:\\test\\1\\2011-07-07\\test\\1\\2011-07-07");
        FileUtil.createDirectoryByFilePath("E:\\test\\1\\2011-07-07\\test\\2\\2011-07-07");
    }

    /**
     * Test get file top parent name.
     */
    @Test
    public void testGetFileTopParentName(){
        assertEquals("E:/", FileUtil.getFileTopParentName("E:/"));

        assertEquals(
                        "mp2-product",
                        FileUtil.getFileTopParentName("mp2-product\\mp2-product-impl\\src\\main\\java\\com\\baozun\\mp2\\rpc\\impl\\item\\repo\\package-info.java"));

        assertEquals(
                        "mp2-product",
                        FileUtil.getFileTopParentName("mp2-product\\mp2-product-impl\\src\\..\\java\\com\\baozun\\mp2\\rpc\\impl\\item\\repo\\package-info.java"));

        assertEquals("package-info.java", FileUtil.getFileTopParentName("package-info.java"));

    }

    /**
     * Gets the file sizes.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testGetFileSizes() throws IOException{

        String testFile = "E:\\DataCommon\\test\\1.png";
        testFile = "E:\\DataCommon\\Java\\JDK API 1.6.0 中文版.CHM";
        testFile = "E:\\迅雷下载\\飞鸟娱乐(bbs.hdbird.com).小叮当与海盗仙子.720p.国英双语\\飞鸟娱乐(bbs.hdbird.com).小叮当与海盗仙子.720p.国英双语.mkv";

        File file = new File(testFile);

        long fileSizes = FileUtil.getFileSize(file);
        LOGGER.info(fileSizes + "");
        LOGGER.info(FileUtil.formatSize(fileSizes) + "");
        LOGGER.info(FileUtil.formatSize(file.length()) + "");
        LOGGER.info("比如文件 {} 字节, 格式化大小 : {}", fileSizes, FileUtil.getFileFormatSize(file));
    }

    /**
     * {@link com.feilong.core.io.FileUtil#formatSize(long)} 的测试方法。
     */
    @Test
    public final void formatFileSize(){
        LOGGER.info(FileUtil.formatSize(8981528));
        LOGGER.info(org.apache.commons.io.FileUtils.byteCountToDisplaySize(8981528));
    }

    /**
     * Test delete file or directory.
     */
    @Test
    @Ignore
    public void testDeleteFileOrDirectory(){
        FileUtil.deleteFileOrDirectory("D:\\test");
    }

    /**
     * 获得后缀名 {@link com.feilong.core.io.FileUtil#getFilePostfixName(java.lang.String)} 的测试方法。
     */
    @Test
    @Ignore
    public void testGetFilePostfixName(){
        assertEquals("png", FileUtil.getFilePostfixName(fileName1));
        LOGGER.info(fileName1.substring(fileName1.lastIndexOf(".")));
        LOGGER.info(fileName1.substring(fileName1.lastIndexOf("\\") + 1));
    }

    /**
     * Test get file pre name.
     */
    @Test
    @Ignore
    public void testGetFilePreName(){
        assertEquals("F:/pie2", FileUtil.getFilePreName(fileName1));
    }

    /**
     * Test get file name.
     */
    @Test
    public void testGetFileName(){
        LOGGER.info(FileUtil.getFileName(fileName1));
    }

    /**
     * Checks for postfix name.
     */
    @Test
    public void hasPostfixName(){
        fileName1 = "a";
        LOGGER.debug(FileUtil.hasPostfixName(fileName1) + "");
    }

    /**
     * Gets the file postfix name lower case.
     * 
     */
    @Test
    public void tstGetFilePostfixNameLowerCase(){
        fileName1 = "a.A";
        LOGGER.debug(FileUtil.getFilePostfixNameLowerCase(fileName1) + "");
    }
}
