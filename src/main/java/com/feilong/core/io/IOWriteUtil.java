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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DateExtensionUtil;
import com.feilong.core.lang.CharsetType;
import com.feilong.core.util.Validator;

/**
 * 提供写文件操作.
 * 
 * <ul>
 * <li>{@link #write(InputStream, OutputStream)} 写资源,速度最快的方法,速度比较请看 电脑资料 {@code <<压缩解压性能探究>>}</li>
 * <li>{@link #write(String, String)} 将字符串写到文件中</li>
 * <li>{@link #write(InputStream, String, String)} 将inputStream 写到 某个文件夹,名字为fileName</li>
 * <li>{@link #write(String, String, String)} 将字符串/文字写到文件中</li>
 * <li>{@link #write(String, String, String, FileWriteMode)} 将字符串写到文件中</li>
 * </ul>
 * 
 * 如果需要覆盖写文件,可以调用 {@link #write(String, String, String, FileWriteMode)}.
 *
 * @author feilong
 * @version 1.0.6 Dec 23, 2013 10:23:23 PM
 * @see "org.springframework.util.StreamUtils"
 * @see "org.springframework.util.FileCopyUtils"
 * @see org.apache.commons.io.IOUtils
 * @since 1.0.6
 */
public final class IOWriteUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IOWriteUtil.class);

    /** Don't let anyone instantiate this class. */
    private IOWriteUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将inputStream 写到 某个文件夹(文件夹路径 最后不带"/"),名字为fileName.
     * 
     * <h3>最终的fileAllName 格式是:</h3>
     * 
     * <blockquote>
     * <p>
     * {@code directoryName + "/" + fileName}
     * </p>
     * </blockquote>
     * 
     * <p>
     * 拼接文件路径.<br>
     * 如果拼接完的文件路径 父路径不存在,则自动创建(支持级联创建 文件夹)
     * </p>
     *
     * @param inputStream
     *            上传得文件流
     * @param directoryName
     *            文件夹路径 最后不带"/"
     * @param fileName
     *            文件名称
     * @see com.feilong.core.io.IOWriteUtil#write(InputStream, OutputStream)
     */
    public static void write(InputStream inputStream,String directoryName,String fileName){
        String filePath = directoryName + "/" + fileName;
        FileUtil.createDirectory(directoryName);
        OutputStream outputStream = FileUtil.getFileOutputStream(filePath);
        write(inputStream, outputStream);
    }

    /**
     * 将字符串写到文件中
     * 
     * <h3>相关规则:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>如果 <code>Validator.isNullOrEmpty(filePath)</code>,那么抛出 {@link NullPointerException}</li>
     * <li>如果文件不存在,自动创建,包括其父文件夹 (支持级联创建 文件夹)</li>
     * <li>如果文件存在则覆盖旧文件,可以设置{@link FileWriteMode#APPEND}表示追加内容而非覆盖</li>
     * <li>如果不设置 <code>charsetType</code>,则默认使用{@link CharsetType#UTF8}编码</li>
     * </ul>
     * </blockquote>
     *
     * @param filePath
     *            文件路径
     * @param content
     *            字符串内容
     * @see FileWriteMode
     * @see CharsetType
     */
    public static void write(String filePath,String content){
        String encode = null;
        write(filePath, content, encode);
    }

    /**
     * 将字符串/文字写到文件中.
     * 
     * <h3>相关规则:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>如果 <code>Validator.isNullOrEmpty(filePath)</code>,那么抛出 {@link NullPointerException}</li>
     * <li>如果文件不存在,自动创建,包括其父文件夹 (支持级联创建 文件夹)</li>
     * <li>如果文件存在则覆盖旧文件,可以设置{@link FileWriteMode#APPEND}表示追加内容而非覆盖</li>
     * <li>如果不设置 <code>charsetType</code>,则默认使用{@link CharsetType#UTF8}编码</li>
     * </ul>
     * </blockquote>
     *
     * @param filePath
     *            文件路径
     * @param content
     *            字符串内容
     * @param encode
     *            编码,如果isNullOrEmpty,则默认使用 {@link CharsetType#UTF8}编码 {@link CharsetType}
     * @see FileWriteMode
     * @see CharsetType
     * @see #write(String, String, String, FileWriteMode)
     */
    public static void write(String filePath,String content,String encode){
        write(filePath, content, encode, FileWriteMode.COVER);//default_fileWriteMode
    }

    /**
     * 将字符串写到文件中.
     * 
     * <h3>相关规则:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>如果 <code>Validator.isNullOrEmpty(filePath)</code>,那么抛出 {@link NullPointerException}</li>
     * <li>如果文件不存在,自动创建,包括其父文件夹 (支持级联创建 文件夹)</li>
     * <li>如果文件存在则覆盖旧文件,可以设置{@link FileWriteMode#APPEND}表示追加内容而非覆盖</li>
     * <li>如果不设置 <code>charsetType</code>,则默认使用{@link CharsetType#UTF8}编码</li>
     * </ul>
     * </blockquote>
     *
     * @param filePath
     *            文件路径
     * @param content
     *            字符串内容
     * @param charsetType
     *            {@link CharsetType} 编码,如果isNullOrEmpty,则默认使用 {@link CharsetType#UTF8}编码
     * @param fileWriteMode
     *            写模式
     * @see FileWriteMode
     * @see CharsetType
     * @see java.io.FileOutputStream#FileOutputStream(File, boolean)
     * @see #write(InputStream, OutputStream)
     */
    public static void write(String filePath,String content,String charsetType,FileWriteMode fileWriteMode){
        if (Validator.isNullOrEmpty(filePath)){
            throw new NullPointerException("filePath can't be null/empty!");
        }
        Date beginDate = new Date();

        String useEncode = Validator.isNullOrEmpty(charsetType) ? CharsetType.UTF8 : charsetType;
        FileWriteMode useFileWriteMode = Validator.isNullOrEmpty(fileWriteMode) ? FileWriteMode.COVER : fileWriteMode;

        FileUtil.createDirectoryByFilePath(filePath);

        InputStream inputStream = IOUtils.toInputStream(content, Charset.forName(useEncode));
        OutputStream outputStream = FileUtil.getFileOutputStream(filePath, useFileWriteMode);

        write(inputStream, outputStream);

        if (LOGGER.isInfoEnabled()){
            File file = new File(filePath);
            LOGGER.info(
                            "fileWriteMode:[{}],encode:[{}],contentLength:[{}],fileSize:[{}],absolutePath:[{}],time:{}",
                            useFileWriteMode,
                            useEncode,
                            content.length(),
                            FileUtil.getFileFormatSize(file),
                            file.getAbsolutePath(),
                            DateExtensionUtil.getIntervalForView(beginDate, new Date()));
        }
    }

    // *******************************************************************************************

    /**
     * 写资源,速度最快的方法,速度比较请看 电脑资料 {@code  <<压缩解压性能探究>>}.
     * 
     * <p span style="color:red">
     * <b>(注意，本方法最终会关闭 <code>inputStream</code>以及 <code>outputStream</code>).</b>
     * </p>
     * 
     * <p>
     * Just write in blocks instead of copying it entirely into Java's memory first.<br>
     * The below basic example writes it in blocks of 10KB.<br>
     * This way you end up with a consistent memory usage of only 10KB instead of the complete content length.<br>
     * Also the enduser will start getting parts of the content much sooner.
     * </p>
     * 
     * @param inputStream
     *            inputStream
     * @param outputStream
     *            outputStream
     * @see java.io.OutputStream#write(byte[], int, int)
     * @see #write(int, InputStream, OutputStream)
     * @see org.apache.commons.io.IOUtils#copyLarge(InputStream, OutputStream)
     */
    public static void write(InputStream inputStream,OutputStream outputStream){
        write(IOConstants.DEFAULT_BUFFER_LENGTH, inputStream, outputStream);
    }

    /**
     * 写资源,速度最快的方法,速度比较请看 电脑资料 {@code  <<压缩解压性能探究>>} .
     * 
     * <p span style="color:red">
     * <b>(注意，本方法最终会关闭 <code>inputStream</code>以及 <code>outputStream</code>).</b>
     * </p>
     *
     * @param bufferLength
     *            每次循环buffer大小
     * @param inputStream
     *            inputStream
     * @param outputStream
     *            outputStream
     * @see java.io.OutputStream#write(byte[], int, int)
     * @see org.apache.commons.io.IOUtils#copyLarge(InputStream, OutputStream)
     * @see <a href="http://stackoverflow.com/questions/10142409/write-an-inputstream-to-an-httpservletresponse">As creme de la creme with
     *      regard to performance, you could use NIO Channels and ByteBuffer. Create the following utility/helper method in some custom
     *      utility class,</a>
     * @see #writeUseNIO(int, InputStream, OutputStream)
     */
    public static void write(int bufferLength,InputStream inputStream,OutputStream outputStream){
        writeUseNIO(bufferLength, inputStream, outputStream);
    }

    /**
     * 使用NIO API 来写数据 (效率高).
     * 
     * <h3>关于NIO</h3>
     * 
     * <blockquote>
     * <p>
     * nio是new io的简称，从jdk1.4就被引入了，可以说不是什么新东西了。<br>
     * nio的主要作用就是用来解决速度差异的。<br>
     * </p>
     * 
     * <p>
     * 举个例子：计算机处理的速度，和用户按键盘的速度。这两者的速度相差悬殊。<br>
     * 如果按照经典的方法：一个用户设定一个线程，专门等待用户的输入，无形中就造成了严重的资源浪费 ：每一个线程都需要珍贵的cpu时间片，由于速度差异造成了在这个交互线程中的cpu都用来等待。 <br>
     * 在以前的 Java IO 中，都是阻塞式 IO，NIO 引入了非阻塞式 IO。
     * </p>
     * 
     * <p>
     * The new I/O (NIO) APIs introduced in v 1.4 provide new features and improved performance in the areas of buffer management, scalable
     * network and file I/O, character-set support, and regular-expression matching. The NIO APIs supplement the I/O facilities in the
     * java.io package.
     * </p>
     * 
     * <p>
     * The NIO APIs include the following features:
     * </p>
     * 
     * <ol>
     * <li>Buffers for data of primitive types</li>
     * <li>Character-set encoders and decoders</li>
     * <li>A pattern-matching facility based on Perl-style regular expressions</li>
     * <li>Channels, a new primitive I/O abstraction</li>
     * <li>A file interface that supports locks and memory mapping</li>
     * <li>A multiplexed, non-blocking I/O facility for writing scalable servers</li>
     * </ol>
     * </blockquote>
     * 
     * <p>
     * As creme de la creme with regard to performance,you could use NIO {@link java.nio.channels.Channels} and {@link java.nio.ByteBuffer}.
     * </p>
     *
     * @param bufferLength
     *            the buffer length
     * @param inputStream
     *            the input stream
     * @param outputStream
     *            the output stream
     * @since 1.0.8
     * @since jdk1.4
     */
    private static void writeUseNIO(int bufferLength,InputStream inputStream,OutputStream outputStream){
        Date beginDate = new Date();
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferLength);

        try{
            int loopCount = 0;
            int sumSize = 0;
            while (readableByteChannel.read(byteBuffer) != -1){
                byteBuffer.flip();
                sumSize += writableByteChannel.write(byteBuffer);
                byteBuffer.clear();
                loopCount++;
            }
            if (LOGGER.isDebugEnabled()){
                String formatSize = FileUtil.formatSize(sumSize);
                String time = DateExtensionUtil.getIntervalForView(beginDate, new Date());
                LOGGER.debug(
                                "Write data over,sumSize:[{}],bufferLength:[{}],loopCount:[{}],time:{}",
                                formatSize,
                                bufferLength,
                                loopCount,
                                time);
            }
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(writableByteChannel);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(readableByteChannel);
        }
    }
}