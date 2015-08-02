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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DateExtensionUtil;
import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.core.lang.StringUtil;
import com.feilong.core.util.Validator;

/**
 * The Class IOWriteUtilTest.
 * 
 * @author feilong
 * @version 1.0 Dec 23, 2013 10:29:11 PM
 */
public class IOWriteUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IOWriteUtilTest.class);

    /**
     * Unescape html2.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    public void unescapeHtml2() throws Exception{
        String a = "第572章 三十年后（大结局） *局";
        String result = (String) MethodUtils.invokeExactStaticMethod(IOWriteUtil.class, "getFormatFilePath", a);
        LOGGER.info(result);
    }

    /**
     * Write1.
     */
    @Test
    public void write1(){

        Date beginDate = new Date();

        InputStream inputStream = FileUtil.getFileInputStream("K:\\Movie 电影\\1993 超级战警 马可·布拉姆贝拉 史泰龙.rmvb");
        OutputStream outputStream = FileUtil.getFileOutputStream("D:\\1993 超级战警 马可·布拉姆贝拉 史泰龙.rmvb");
        IOWriteUtil.write(inputStream, outputStream);

        Date endDate = new Date();
        LOGGER.info("time:{}", DateExtensionUtil.getIntervalForView(beginDate, endDate));
    }

    /**
     * Write.
     */
    @Test
    public void write(){
        String url = "F:\\test.txt";
        String directoryName = SpecialFolder.getDesktop();
        IOWriteUtil.write(url, directoryName);
    }

    /**
     * Test write nio.
     */
    @Test
    public void testWriteNio(){
        String content = getContent();

        Date beginDate = new Date();
        for (int i = 0; i < 10; ++i){
            testWriteNio(content);
        }
        LOGGER.info("use time:{}", DateExtensionUtil.getIntervalForView(beginDate, new Date()));

    }

    /**
     * Test write io.
     */
    @Test
    public void testWriteIo(){
        String content = getContent();
        Date beginDate = new Date();
        for (int i = 0; i < 10; ++i){
            testWriteIO(content);
        }
        LOGGER.info("time:{}", DateExtensionUtil.getIntervalForView(beginDate, new Date()));
    }

    /**
     * @param content
     */
    private void testWriteIO(String content){
        Date beginDate = new Date();
        String type = "io";
        write(getPath(type), content, null, FileWriteMode.COVER);
        LOGGER.info("[{}] time:{}", type, DateExtensionUtil.getIntervalForView(beginDate, new Date()));
    }

    /**
     * @param content
     */
    private void testWriteNio(String content){
        Date beginDate = new Date();
        String type = "nio";
        IOWriteUtil.write(getPath(type), content);
        LOGGER.info("[{}] time:{}", type, DateExtensionUtil.getIntervalForView(beginDate, new Date()));
    }

    /**
     * 获得 path.
     *
     * @param type
     *            the type
     * @return the path
     */
    private String getPath(String type){
        String templateString = "/home/webuser/nike_int/expressdelivery/${yearMonth}/${expressDeliveryType}/vipQuery_${fileName}_${type}.log";
        Date date = new Date();
        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("yearMonth", DateUtil.date2String(date, DatePattern.YEAR_AND_MONTH));
        valuesMap.put("expressDeliveryType", "sf");
        valuesMap.put("fileName", DateUtil.date2String(date, DatePattern.TIMESTAMP));
        valuesMap.put("type", type);
        return StringUtil.replace(templateString, valuesMap);
    }

    /**
     * 获得 content.
     *
     * @return the content
     */
    private String getContent(){
        StringBuilder sb = new StringBuilder();
        sb.append("****************************************************" + SystemUtils.LINE_SEPARATOR);
        sb.append("2011-05-13 22:24:37调用,系统顺丰在途订单597件" + SystemUtils.LINE_SEPARATOR);
        sb.append("耗时:429020" + SystemUtils.LINE_SEPARATOR);
        sb.append("****************************************************" + SystemUtils.LINE_SEPARATOR);
        sb.append("派送成功订单495条" + SystemUtils.LINE_SEPARATOR);
        for (int i = 0; i < 100000; i++){
            sb.append("订单号:20850010运单号:102085592089\t寄件时间:2011-05-09 19:00:00\t签收人:张寄件时间:2011-05-10 14:49:00\t回单类型:1\n");
        }
        return sb.toString();
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
     *            编码,如果isNullOrEmpty,则默认使用 {@link CharsetType#UTF8}编码 {@link CharsetType}
     * @param fileWriteMode
     *            写模式
     * @see FileWriteMode
     * @see CharsetType
     * @see java.io.FileOutputStream#FileOutputStream(File, boolean)
     */
    public static void write(String filePath,String content,String encode,FileWriteMode fileWriteMode){
        String useEncode = Validator.isNullOrEmpty(encode) ? CharsetType.UTF8 : encode;
        FileUtil.createDirectoryByFilePath(filePath);
        OutputStream outputStream = null;
        try{
            outputStream = FileUtil.getFileOutputStream(filePath, fileWriteMode);
            Writer outputStreamWriter = new OutputStreamWriter(outputStream, useEncode);

            Writer writer = new PrintWriter(outputStreamWriter);
            writer.write(content);
            writer.close();

            File file = new File(filePath);
            LOGGER.info(
                            "fileWriteMode:[{}],useEncode:[{}],contentLength:[{}],fileSize:[{}],absolutePath:[{}]",
                            fileWriteMode,
                            useEncode,
                            content.length(),
                            FileUtil.getFileFormatSize(file),
                            file.getAbsolutePath());
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 使用IO API来写数据.
     *
     * @param bufferLength
     *            the buffer length
     * @param inputStream
     *            the input stream
     * @param outputStream
     *            the output stream
     * @since 1.0.8
     * @deprecated use {@link IOWriteUtil#writeUseNIO(int, InputStream, OutputStream)}
     */
    @Deprecated
    public static void writeUseIO(int bufferLength,InputStream inputStream,OutputStream outputStream){
        int i = 0;
        int sumSize = 0;
        int j = 0;

        byte[] bytes = new byte[bufferLength];
        try{
            //从输入流中读取一定数量的字节，并将其存储在缓冲区数组bytes 中。以整数形式返回实际读取的字节数。在输入数据可用、检测到文件末尾或者抛出异常前，此方法一直阻塞。 
            //如果 bytes 的长度为 0，则不读取任何字节并返回 0；否则，尝试读取至少一个字节。
            //如果因为流位于文件末尾而没有可用的字节，则返回值 -1；否则，至少读取一个字节并将其存储在 bytes 中。 
            //将读取的第一个字节存储在元素 bytes[0] 中，下一个存储在 b[1] 中，依次类推。
            //读取的字节数最多等于 b 的长度。设 k 为实际读取的字节数；这些字节将存储在 b[0] 到 b[k-1] 的元素中，不影响 b[k] 到 b[b.length-1] 的元素。 
            //类 InputStream 的 read(b) 方法的效果等同于：read(b, 0, b.length) 
            while ((j = inputStream.read(bytes)) != -1){

                //迅雷下载会报下面的异常，但是不影响下载效果
                //ClientAbortException:  java.net.SocketException: Software caused connection abort: socket write error
                outputStream.write(bytes, 0, j);
                i++;
                sumSize = sumSize + j;

                //  LOGGER.debug(
                //                  "write data,index:[{}],bufferLength:[{}],currentLoopLength:[{}],sumSize:[{}]",
                //                  i,
                //                  bufferLength,
                //                  j,
                //                  FileUtil.formatSize(sumSize));
            }
            LOGGER.debug("write data over,sumSize:[{}],bufferLength:[{}],loopCount:[{}]", FileUtil.formatSize(sumSize), bufferLength, i);
            //刷新此输出流并强制写出所有缓冲的输出字节。flush 的常规协定是：如果此输出流的实现已经缓冲了以前写入的任何字节，则调用此方法指示应将这些字节立即写入它们预期的目标。 
            outputStream.flush();
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            // 用完关闭流 是个好习惯,^_^
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }
}
