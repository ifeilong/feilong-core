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

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author feilong
 * @version 1.4.0 2015年8月4日 上午11:50:29
 * @since 1.4.0
 */
public class FilenameUtilTest{

    private static final Logger LOGGER    = LoggerFactory.getLogger(FilenameUtilTest.class);

    /** The file name1. */
    private static String       fileName1 = "F:/pie2.png";

    /**
     * Gets the file postfix name lower case.
     * 
     */
    @Test
    public void tstGetFilePostfixNameLowerCase(){
        fileName1 = "a.A";
        LOGGER.debug(FilenameUtil.getFilePostfixNameLowerCase(fileName1) + "");
    }

    /**
     * Test get extension.
     */
    @Test
    public void testGetExtension(){
        assertEquals("png", FilenameUtil.getExtension(fileName1));
        LOGGER.info(fileName1.substring(fileName1.lastIndexOf(".")));
        LOGGER.info(fileName1.substring(fileName1.lastIndexOf("\\") + 1));
    }

    /**
     * Checks for postfix name.
     */
    @Test
    public void hasPostfixName(){
        fileName1 = "a";
        LOGGER.debug(FilenameUtil.hasExtension(fileName1) + "");
    }

    /**
     * Test get new file name.
     */
    @Test
    public void testGetNewFileName(){
        assertEquals("F:/pie2.gif", FilenameUtil.getNewFileName(fileName1, "gif"));
    }

    /**
     * Test get file name.
     */
    @Test
    public void testGetFileName(){
        LOGGER.info(FilenameUtil.getFileName(fileName1));
    }

    /**
     * Test get file pre name.
     */
    @Test
    @Ignore
    public void testGetFilePreName(){
        assertEquals("F:/pie2", FilenameUtil.getFilePreName(fileName1));
    }

    /**
     * 常用图片格式.
     * 
     * @deprecated 表述不清晰,将会重构
     */
    @Deprecated
    private static final String[] COMMON_IMAGES = { "gif", "bmp", "jpg", "png" };

    /**
     * 上传的文件是否是常用图片格式.
     * 
     * @param fileName
     *            文件名称,可以是全路径 ,也可以是 部分路径,会解析取到后缀名
     * @return 上传的文件是否是常用图片格式
     */
    public static boolean isCommonImage(String fileName){
        return isInAppointTypes(fileName, COMMON_IMAGES);
    }

    /**
     * 上传的文件是否在指定的文件类型里面.
     * 
     * @param fileName
     *            文件名称
     * @param appointTypes
     *            指定的文件类型数组
     * @return 上传的文件是否在指定的文件类型里面
     */
    // XXX 忽视大小写
    public static boolean isInAppointTypes(String fileName,String[] appointTypes){
        String filePostfixName = FilenameUtil.getExtension(fileName);
        return ArrayUtils.contains(appointTypes, filePostfixName);
    }

    /**
     * Test get file top parent name.
     */
    @Test
    public void testGetFileTopParentName(){
        assertEquals("E:/", FilenameUtil.getFileTopParentName("E:/"));
        assertEquals(
                        "mp2-product",
                        FilenameUtil.getFileTopParentName(
                                        "mp2-product\\mp2-product-impl\\src\\main\\java\\com\\baozun\\mp2\\rpc\\impl\\item\\repo\\package-info.java"));
        assertEquals(
                        "mp2-product",
                        FilenameUtil.getFileTopParentName(
                                        "mp2-product\\mp2-product-impl\\src\\..\\java\\com\\baozun\\mp2\\rpc\\impl\\item\\repo\\package-info.java"));
        assertEquals("package-info.java", FilenameUtil.getFileTopParentName("package-info.java"));
    }
}
