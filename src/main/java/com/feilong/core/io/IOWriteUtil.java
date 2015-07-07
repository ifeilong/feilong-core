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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.Validator;

/**
 * 提供写文件操作
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
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Dec 23, 2013 10:23:23 PM
 * @see "org.springframework.util.StreamUtils"
 * @see "org.springframework.util.FileCopyUtils"
 * @see org.apache.commons.io.IOUtils
 * @since 1.0.0
 */
public final class IOWriteUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IOWriteUtil.class);

    /**
     * 将inputStream 写到 某个文件夹(文件夹路径 最后不带"/"),名字为fileName.
     * 
     * <h3>最终的fileAllName 格式是</h3>
     * 
     * <blockquote>
     * <p>
     * {@code directoryName + "/" + fileName}
     * </p>
     * </blockquote>
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
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see com.feilong.core.io.IOWriteUtil#write(InputStream, OutputStream)
     */
    public static void write(InputStream inputStream,String directoryName,String fileName) throws UncheckedIOException{
        String filePath = directoryName + "/" + fileName;

        FileUtil.createDirectory(directoryName);

        OutputStream outputStream = FileUtil.getFileOutputStream(filePath);
        write(inputStream, outputStream);
    }

    /**
     * 写资源,速度最快的方法,速度比较请看 电脑资料 {@code  <<压缩解压性能探究>>} .<br>
     * <b>(注意，本方法最终会关闭 <code>inputStream</code>以及 <code>outputStream</code>).</b>
     *
     * @param inputStream
     *            inputStream
     * @param outputStream
     *            outputStream
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see java.io.OutputStream#write(byte[], int, int)
     * @see #write(int, InputStream, OutputStream)
     * @see org.apache.commons.io.IOUtils#copyLarge(InputStream, OutputStream)
     */
    public static void write(InputStream inputStream,OutputStream outputStream) throws UncheckedIOException{
        //Just write in blocks instead of copying it entirely into Java's memory first.
        //The below basic example writes it in blocks of 10KB.
        //This way you end up with a consistent memory usage of only 10KB instead of the complete content length. 
        //Also the enduser will start getting parts of the content much sooner.
        write(IOConstants.DEFAULT_BUFFER_LENGTH, inputStream, outputStream);
    }

    /**
     * 写资源,速度最快的方法,速度比较请看 电脑资料 {@code  <<压缩解压性能探究>>} .<br>
     * <b>(注意，本方法最终会关闭 <code>inputStream</code>以及 <code>outputStream</code>).</b>
     *
     * @param bufferLength
     *            每次循环buffer大小
     * @param inputStream
     *            inputStream
     * @param outputStream
     *            outputStream
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see java.io.OutputStream#write(byte[], int, int)
     * @see org.apache.commons.io.IOUtils#copyLarge(InputStream, OutputStream)
     * @see <a href="http://stackoverflow.com/questions/10142409/write-an-inputstream-to-an-httpservletresponse">As creme de la creme with
     *      regard to performance, you could use NIO Channels and ByteBuffer. Create the following utility/helper method in some custom
     *      utility class,</a>
     * @see #writeUseNIO(int, InputStream, OutputStream)
     */
    public static void write(int bufferLength,InputStream inputStream,OutputStream outputStream) throws UncheckedIOException{
        writeUseNIO(bufferLength, inputStream, outputStream);
    }

    /**
     * 使用NIO API 来写数据 (效率高).
     *
     * @param bufferLength
     *            the buffer length
     * @param inputStream
     *            the input stream
     * @param outputStream
     *            the output stream
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @since 1.0.8
     * @since jdk1.4
     */
    private static void writeUseNIO(int bufferLength,InputStream inputStream,OutputStream outputStream) throws UncheckedIOException{
        int i = 0;
        int sumSize = 0;
        int j = 0;

        ///方案2 
        //As creme de la creme with regard to performance, you could use NIO Channels and ByteBuffer. 

        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);

        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferLength);

        try{
            while (readableByteChannel.read(byteBuffer) != -1){
                byteBuffer.flip();
                j = writableByteChannel.write(byteBuffer);
                sumSize += j;
                byteBuffer.clear();
                i++;
            }

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("Write data over,sumSize:[{}],bufferLength:[{}],loopCount:[{}]", FileUtil.formatSize(sumSize), bufferLength, i);
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

    // *******************************************************************************************

    /**
     * 将字符串写到文件中
     * 
     * <ul>
     * <li>如果文件不存在,自动创建;包括其父文件夹(级联创建文件夹)</li>
     * <li>如果文件存在,则覆盖旧文件 ,默认 以覆盖的模式 {@link FileWriteMode#COVER}内容.</li>
     * <li>如果不设置encode,则默认使用 {@link CharsetType#GBK}编码</li>
     * </ul>
     *
     * @param filePath
     *            文件路径
     * @param content
     *            字符串内容
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @throws IllegalArgumentException
     *             <ul>
     *             <li>如果filePath文件存在,且isDirectory</li>
     *             <li>如果filePath文件存在,且是!canWrite</li>
     *             </ul>
     * @see FileWriteMode
     * @see CharsetType
     */
    public static void write(String filePath,String content) throws UncheckedIOException,IllegalArgumentException{
        String encode = null;
        write(filePath, content, encode);
    }

    /**
     * 将字符串/文字写到文件中.
     * 
     * <ul>
     * <li>如果文件不存在,自动创建;包括其父文件夹(级联创建文件夹)</li>
     * <li>如果文件存在,则覆盖旧文件 ,默认 以覆盖的模式 {@link FileWriteMode#COVER}内容.</li>
     * </ul>
     *
     * @param filePath
     *            文件路径
     * @param content
     *            字符串内容
     * @param encode
     *            编码,如果isNullOrEmpty,则默认使用 {@link CharsetType#GBK}编码 {@link CharsetType}
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @throws IllegalArgumentException
     *             <ul>
     *             <li>如果filePath文件存在,且isDirectory</li>
     *             <li>如果filePath文件存在,且是!canWrite</li>
     *             </ul>
     * @see FileWriteMode
     * @see CharsetType
     * @see #write(String, String, String, FileWriteMode)
     */
    public static void write(String filePath,String content,String encode) throws UncheckedIOException,IllegalArgumentException{
        FileWriteMode default_fileWriteMode = FileWriteMode.COVER;
        write(filePath, content, encode, default_fileWriteMode);
    }

    /**
     * 将字符串写到文件中.
     * 
     * <ul>
     * <li>如果文件不存在,自动创建,包括其父文件夹 (支持级联创建 文件夹)</li>
     * <li>如果文件存在则覆盖旧文件,可以通过 指定 {@link FileWriteMode#APPEND}来表示追加内容而非覆盖</li>
     * </ul>
     *
     * @param filePath
     *            文件路径
     * @param content
     *            字符串内容
     * @param encode
     *            编码,如果isNullOrEmpty,则默认使用 {@link CharsetType#GBK}编码 {@link CharsetType}
     * @param fileWriteMode
     *            写模式
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @throws IllegalArgumentException
     *             <ul>
     *             <li>如果filePath文件存在,且isDirectory</li>
     *             <li>如果filePath文件存在,且是!canWrite</li>
     *             </ul>
     * @see FileWriteMode
     * @see CharsetType
     * @see java.io.FileOutputStream#FileOutputStream(File, boolean)
     */
    public static void write(String filePath,String content,String encode,FileWriteMode fileWriteMode) throws UncheckedIOException,
                    IllegalArgumentException{

        //TODO 如果不传 将来可能会改成读取 系统默认语言
        if (Validator.isNullOrEmpty(encode)){
            encode = CharsetType.GBK;
        }

        // **************************************************************************8
        File file = new File(filePath);

        FileUtil.createDirectoryByFilePath(filePath);

        OutputStream outputStream = null;
        try{
            outputStream = FileUtil.getFileOutputStream(filePath, fileWriteMode);
            //TODO 看看能否调用 write(inputStream, outputStream);

            Writer outputStreamWriter = new OutputStreamWriter(outputStream, encode);

            Writer writer = new PrintWriter(outputStreamWriter);
            writer.write(content);
            writer.close();

            if (LOGGER.isInfoEnabled()){
                LOGGER.info(
                                "fileWriteMode:[{}],encode:[{}],contentLength:[{}],fileSize:[{}],absolutePath:[{}]",
                                fileWriteMode,
                                encode,
                                content.length(),
                                FileUtil.getFileFormatSize(file),
                                file.getAbsolutePath());
            }
            outputStream.close();
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            IOUtils.closeQuietly(outputStream);
        }
    }

    /** Don't let anyone instantiate this class. */
    private IOWriteUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}