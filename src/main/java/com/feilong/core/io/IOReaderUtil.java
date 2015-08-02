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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.feilong.core.lang.CharsetType;
import com.feilong.core.util.Validator;

/**
 * 读文件.
 * 
 * @author feilong
 * @version 1.0.6 Dec 23, 2013 10:27:08 PM
 * @version 1.0.8 2014-11-25 20:04 add {@link #getFileContent(String, String)}
 * @since 1.0.6
 */
public final class IOReaderUtil{

    /** 默认编码. */
    private static final String DEFAULT_CHARSET_NAME = CharsetType.UTF8;

    /** Don't let anyone instantiate this class. */
    private IOReaderUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 读取文件内容.
     *
     * @param path
     *            路径
     * @return 文件内容string
     */
    public static String getFileContent(String path){
        return getFileContent(path, DEFAULT_CHARSET_NAME);
    }

    /**
     * 获得 file content.
     *
     * @param path
     *            the path
     * @param charsetName
     *            字符编码,如果是isNullOrEmpty,那么默认使用 {@link CharsetType#UTF8}
     * @return the file content
     * @since 1.0.8
     */
    public static String getFileContent(String path,String charsetName){
        File file = new File(path);
        return getFileContent(file, charsetName);
    }

    /**
     * 读取文件内容.
     *
     * @param file
     *            文件
     * @param charsetName
     *            字符编码,如果是isNullOrEmpty,那么默认使用 {@link CharsetType#UTF8}
     * @return the file content
     */
    public static String getFileContent(File file,String charsetName){
        if (Validator.isNullOrEmpty(file)){
            throw new NullPointerException("the file is null or empty!");
        }
        // 分配新的直接字节缓冲区
        final int capacity = 186140;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(capacity);
        StringBuilder sb = new StringBuilder(capacity);

        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream(file);

            // 用于读取、写入、映射和操作文件的通道.
            FileChannel fileChannel = fileInputStream.getChannel();
            String useCharsetName = Validator.isNullOrEmpty(charsetName) ? DEFAULT_CHARSET_NAME : charsetName;
            Charset charset = Charset.forName(useCharsetName);
            while (fileChannel.read(byteBuffer) != -1){
                // 反转此缓冲区
                byteBuffer.flip();
                CharBuffer charBuffer = charset.decode(byteBuffer);
                sb.append(charBuffer.toString());
                byteBuffer.clear();
            }
            return sb.toString();

        }catch (FileNotFoundException e){
            throw new UncheckedIOException(e);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            // 用完关闭流 是个好习惯,^_^
            IOUtils.closeQuietly(fileInputStream);
        }
    }
}