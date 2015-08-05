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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.util.Validator;

/**
 * {@link File}文件操作.
 * 
 * <h3>File的几个属性:</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link File#getAbsolutePath()} 得到的是全路径</li>
 * <li>{@link File#getPath()} 得到的是构造file的时候的路径</li>
 * <li>{@link File#getCanonicalPath()} 可以看到CanonicalPath不但是全路径，而且把..或者.这样的符号解析出来.</li>
 * </ul>
 * </blockquote>
 * 
 * 
 * <h3>关于大小</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link FileUtils#ONE_KB} 1024</li>
 * <li>{@link FileUtils#ONE_MB} 1024 * 1024 1048576</li>
 * <li>{@link FileUtils#ONE_GB} 1024 * 1024 * 1024 1073741824
 * <p style="color:red">
 * <b>注意,{@link Integer#MAX_VALUE}=2147483647 是2G大小</b>
 * </p>
 * </ul>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.0 2012-5-23 下午5:00:54
 * @version 1.0.7 2014-5-23 20:27 add {@link #getFileFormatSize(File)}
 * @see java.io.File
 * @see org.apache.commons.io.FilenameUtils
 * @see org.apache.commons.io.FileUtils
 * @since 1.0.0
 */
public final class FileUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER                = LoggerFactory.getLogger(FileUtil.class);

    /** 默认缓冲大小 10k <code>{@value}</code>. */
    public static final int     DEFAULT_BUFFER_LENGTH = (int) (10 * FileUtils.ONE_KB);

    /** Don't let anyone instantiate this class. */
    private FileUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将文件转成 {@code byte[] bytes}.
     *
     * @param fileName
     *            the file name
     * @return {@link java.io.ByteArrayOutputStream#toByteArray()}
     * @see #toByteArray(File)
     * @since 1.2.1
     */
    public static byte[] toByteArray(String fileName){
        File file = new File(fileName);
        return toByteArray(file);
    }

    /**
     * 将文件转成 {@code byte[] bytes}.
     *
     * @param file
     *            file
     * @return {@link java.io.ByteArrayOutputStream#toByteArray()}
     * @see #getFileInputStream(File)
     * @see java.io.ByteArrayOutputStream#toByteArray()
     * @see org.apache.commons.io.FileUtils#readFileToByteArray(File)
     * @see org.apache.commons.io.IOUtils#toByteArray(InputStream, int)
     */
    public static byte[] toByteArray(File file){
        InputStream inputStream = getFileInputStream(file);
        try{
            return IOUtils.toByteArray(inputStream);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            // 为避免内存泄漏，Stream的Close是必须的.即使中途发生了异常，也必须Close，
            IOUtils.closeQuietly(inputStream);
        }
    }

    //*******************************************************************************

    /**
     * 获得 {@link java.io.FileOutputStream} 文件输出流 （或其他文件写入对象）打开文件进行写入 .<br>
     * {@link java.io.FileOutputStream} 用于写入诸如图像数据之类的原始字节的流.<br>
     * 如果要写入字符流，请考虑使用 {@link java.io.FileWriter}.
     *
     * @param filePath
     *            文件路径
     * @return FileOutputStream
     * @see java.io.FileOutputStream#FileOutputStream(String)
     * @see #getFileOutputStream(String, boolean)
     */
    public static FileOutputStream getFileOutputStream(String filePath){
        //默认 append 是 false
        return getFileOutputStream(filePath, false);
    }

    /**
     * 获得 {@link java.io.FileOutputStream} 文件输出流 （或其他文件写入对象）打开文件进行写入 .<br>
     * {@link java.io.FileOutputStream} 用于写入诸如图像数据之类的原始字节的流.<br>
     * 如果要写入字符流，请考虑使用 {@link java.io.FileWriter}.
     *
     * @param filePath
     *            the file path
     * @param fileWriteMode
     *            the file write mode
     * @return the file output stream
     * @see #getFileOutputStream(String, boolean)
     * @since 1.2.0
     */
    public static FileOutputStream getFileOutputStream(String filePath,FileWriteMode fileWriteMode){
        boolean append = fileWriteMode == FileWriteMode.APPEND;
        return getFileOutputStream(filePath, append);
    }

    /**
     * 获得 {@link java.io.FileOutputStream} 文件输出流 （或其他文件写入对象）打开文件进行写入 .
     * 
     * <p>
     * {@link java.io.FileOutputStream} 用于写入诸如图像数据之类的原始字节的流.<br>
     * 如果要写入字符流，请考虑使用 {@link java.io.FileWriter}.
     * </p>
     * 
     * @param filePath
     *            the file path
     * @param append
     *            if {@code true}, then bytes will be added to the end of the file rather than overwriting
     * @return the file output stream
     * @see java.io.FileOutputStream#FileOutputStream(String, boolean)
     * @see org.apache.commons.io.FileUtils#openOutputStream(File, boolean)
     * @since 1.2.0
     */
    //默认 Access Modifiers 权限修饰符
    static FileOutputStream getFileOutputStream(String filePath,boolean append){
        File file = new File(filePath);
        return getFileOutputStream(file, append);
    }

    /**
     * 获得 {@link java.io.FileOutputStream} 文件输出流 （或其他文件写入对象）打开文件进行写入 .
     * 
     * <p>
     * {@link java.io.FileOutputStream} 用于写入诸如图像数据之类的原始字节的流.<br>
     * 如果要写入字符流，请考虑使用 {@link java.io.FileWriter}.
     * </p>
     *
     * @param file
     *            the file
     * @param append
     *            if {@code true}, then bytes will be added to the end of the file rather than overwriting
     * @return the file output stream
     * @since 1.4.0
     */
    //默认 Access Modifiers 权限修饰符
    static FileOutputStream getFileOutputStream(File file,boolean append){
        try{
            return org.apache.commons.io.FileUtils.openOutputStream(file, append);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    //********************************************************************************************

    /**
     * 从文件系统中的某个文件中获得输入字节.哪些文件可用取决于主机环境.<br>
     * {@link java.io.FileInputStream} 用于读取诸如图像数据之类的原始字节流.<br>
     * 要读取字符流，请考虑使用 {@link java.io.FileReader}
     *
     * @param fileName
     *            该文件通过文件系统中的路径名 fileName 指定.
     * @return FileInputStream
     * @see #getFileInputStream(File)
     */
    public static FileInputStream getFileInputStream(String fileName){
        File file = new File(fileName);
        return getFileInputStream(file);
    }

    /**
     * 从文件系统中的某个文件中获得输入字节.哪些文件可用取决于主机环境.
     * 
     * <p>
     * {@link java.io.FileInputStream} 用于读取诸如图像数据之类的原始字节流.<br>
     * 要读取字符流，请考虑使用 {@link java.io.FileReader}
     * </p>
     * 
     * <p>
     * 如果指定文件不存在;或者它是一个目录，而不是一个常规文件;抑或因为其他某些原因而无法打开进行读取，则抛出 FileNotFoundException
     * </p>
     *
     * @param file
     *            为了进行读取而打开的文件.
     * @return FileInputStream
     * @see org.apache.commons.io.FileUtils#openInputStream(File)
     */
    public static FileInputStream getFileInputStream(File file){
        try{
            return org.apache.commons.io.FileUtils.openInputStream(file);
        }catch (IOException e){
            LOGGER.error("", e);
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 判断一个目录是否是空目录 <span style="color:red">(判断的原则:里面没有文件)</span>.
     *
     * @param directory
     *            指定一个存在的文件夹
     * @return <ul>
     *         <li>如果directory isNullOrEmpty,throw IllegalArgumentException</li>
     *         <li>如果directory don't exists,throw IllegalArgumentException</li>
     *         <li>如果directory is not Directory,throw IllegalArgumentException</li>
     *         <li>return file.list() ==0</li>
     *         </ul>
     * @see org.apache.commons.io.FileUtils#sizeOf(File)
     * @see org.apache.commons.io.FileUtils#sizeOfDirectory(File)
     */
    public static boolean isEmptyDirectory(String directory){
        if (Validator.isNullOrEmpty(directory)){
            throw new NullPointerException("directory param " + directory + " can't be null/empty!");
        }
        File file = new File(directory);
        if (!file.exists()){
            throw new IllegalArgumentException("directory file " + directory + " don't exists!");
        }
        if (!file.isDirectory()){
            throw new IllegalArgumentException("directory file " + directory + " is not Directory!");
        }

        // Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname.
        // 如果此抽象路径名不表示一个目录，那么此方法将返回 null

        // ubuntu 已经 测试ok
        File[] listFiles = file.listFiles();

        int fileListLength = listFiles.length;

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("file :[{}] list length:[{}]", directory, fileListLength);
            for (File tempFile : listFiles){
                LOGGER.debug("[{}] [{}]", tempFile.getName(), tempFile.isDirectory() ? FileType.DIRECTORY : FileType.FILE);
            }
        }
        return 0 == fileListLength;
    }

    // [start] 文件夹操作(createDirectory/deleteFileOrDirectory/deleteFileOrDirectory)
    /**
     * 创建文件夹by文件路径,支持级联创建.
     * 
     * <h3>注意:</h3>
     * 
     * <ol>
     * <li>此处<span style="color:red">参数是文件路径</span>,如果需要传递文件夹路径自动创建文件夹,那么请调用 {@link #createDirectory(String)}</li>
     * <li>对于不存在的文件夹/文件夹: "E:\\test\\1\\2011-07-07" 这么一个路径, 没有办法自动区别到底你是要创建文件还是文件夹</li>
     * <li>{@link File#isDirectory()} 这个方法,必须文件存在 才能判断</li>
     * </ol>
     * 
     * <h3>代码流程:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>{@code if isNullOrEmpty(filePath)---->NullPointerException}</li>
     * <li>{@link #getParent(String) getParent}(filePath)</li>
     * <li>{@link #createDirectory(String) createDirectory}(directory)</li>
     * </ol>
     * </blockquote>
     *
     * @param filePath
     *            <span style="color:red">文件路径</span>
     * @see #getParent(String)
     * @see #createDirectory(String)
     * @since 1.2.0
     */
    public static void createDirectoryByFilePath(String filePath){
        if (Validator.isNullOrEmpty(filePath)){
            throw new NullPointerException("filePath can't be null/empty!");
        }
        String directory = getParent(filePath);
        createDirectory(directory);
    }

    /**
     * 创建文件夹,支持<span style="color:green">级联创建</span>.
     * 
     * <h3>注意:</h3>
     * 
     * <ol>
     * <li>此处<span style="color:red">参数是文件夹</span>,如果需要传递文件路径自动创建父文件夹,那么请调用 {@link #createDirectoryByFilePath(String)}</li>
     * <li>对于不存在的文件夹/文件夹: "E:\\test\\1\\2011-07-07" 这么一个路径, 没有办法自动区别到底你是要创建文件还是文件夹</li>
     * <li>{@link File#isDirectory()} 这个方法,必须文件存在 才能判断</li>
     * </ol>
     * 
     * <h3>代码流程:</h3> <blockquote>
     * <ol>
     * <li>{@code if Validator.isNullOrEmpty(directory)---->NullPointerException}</li>
     * <li>{@code if directory exists---->log debug and return}</li>
     * <li>{@link java.io.File#mkdirs()}</li>
     * <li>{@code if mkdirs's result is false ---> return IllegalArgumentException}</li>
     * <li>{@code if mkdirs's result is true ---> log debug}</li>
     * </ol>
     * </blockquote>
     *
     * @param directory
     *            <span style="color:red">文件夹路径</span>
     * @see #createDirectoryByFilePath(String)
     */
    public static void createDirectory(String directory){
        if (Validator.isNullOrEmpty(directory)){
            throw new NullPointerException("filePath can't be null/empty!");
        }
        File directoryFile = new File(directory);

        boolean isExists = directoryFile.exists();

        //***********do with 存在******************
        if (isExists){//存在
            LOGGER.debug("exists directoryFile:[{}],don't need mkdirs,nothing to do~", directoryFile);
            return;
        }

        //***********do with 不存在******************
        String absolutePath = directoryFile.getAbsolutePath();

        // mkdir 如果 parent 目录不存在 会返回false 不会报错
        boolean flag = directoryFile.mkdirs();
        // 级联创建 父级文件夹
        if (!flag){
            String msg = "File [" + absolutePath + "] could not be created";
            LOGGER.error(msg);
            throw new IllegalArgumentException(msg);
        }else{
            //创建成功 记录下日志
            LOGGER.debug("success mkdirs:[{}]~~", absolutePath);
        }
    }

    /**
     * 删除文件或者文件夹,如果是文件夹 ,递归深层次 删除.
     * 
     * <p>
     * Deletes a file, never throwing an exception. If file is a directory, delete it and all sub-directories.
     * </p>
     * 
     * <h3>difference between {@link File#delete()} and current method:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
     * </ul>
     * </blockquote>
     *
     * @param fileName
     *            文件或者文件夹名称
     * @return {@code true} if the file or directory was deleted, otherwise {@code false}
     * @see com.feilong.core.io.FileUtil#deleteFileOrDirectory(File)
     */
    public static boolean deleteFileOrDirectory(String fileName){
        File file = new File(fileName);
        return deleteFileOrDirectory(file);
    }

    /**
     * 删除文件或者文件夹,如果是文件夹 ,递归深层次 删除.
     * 
     * <p>
     * Deletes a file, never throwing an exception. If file is a directory, delete it and all sub-directories.
     * </p>
     * 
     * <h3>difference between {@link File#delete()} and current method:</h3>
     * 
     * <blockquote>
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>No exceptions are thrown when a file or directory cannot be deleted.</li>
     * </ul>
     * </blockquote>
     *
     * @param file
     *            file or directory to delete, can be {@code null}
     * @return {@code true} if the file or directory was deleted, otherwise {@code false}
     * 
     * @see org.apache.commons.io.FileUtils#deleteQuietly(File)
     */
    public static boolean deleteFileOrDirectory(File file){
        return FileUtils.deleteQuietly(file);
    }

    // [end]

    // ************************************************************

    /**
     * 返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回 null.
     *
     * @param path
     *            the path
     * @return the parent
     * @see java.io.File#getParent()
     */
    public static String getParent(String path){
        if (Validator.isNullOrEmpty(path)){
            throw new NullPointerException("pathname can't be null/empty!");
        }
        File file = new File(path);
        return file.getParent();
    }

    /**
     * 判断文件是否存在.
     * 
     * @param filePath
     *            the file path
     * @return 如果文件存在,返回true
     */
    public static boolean isExistFile(String filePath){
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 判断文件不存在.
     * 
     * @param filePath
     *            the file path
     * @return 如果文件不存在,返回true
     * @since 1.0.3
     */
    public static boolean isNotExistFile(String filePath){
        return !isExistFile(filePath);
    }

    // ************************************************************

    /**
     * 获得文件格式化大小.
     * 
     * <p>
     * 比如文件3834字节,格式化大小 3.74KB<br>
     * 比如文件36830335 字节, 格式化大小:35.12MB<br>
     * 比如文件2613122669 字节, 格式化大小 : 2.43GB<br>
     * </p>
     * 
     * <p>
     * 目前支持单位有 GB MB KB以及最小单位 Bytes
     * </p>
     *
     * @param file
     *            the file
     * @return the file format size
     * @see #getFileSize(File)
     * @see com.feilong.core.io.FileUtil#formatSize(long)
     * @see org.apache.commons.io.FileUtils#byteCountToDisplaySize(long)
     * @since 1.0.7
     */
    public static String getFileFormatSize(File file){
        if (Validator.isNullOrEmpty(file)){
            throw new NullPointerException("file can't be null/empty!");
        }
        long fileSize = getFileSize(file);
        return formatSize(fileSize);
    }

    /**
     * 文件大小格式化.
     * 
     * <p>
     * 目前支持单位有 GB MB KB以及最小单位 Bytes
     * </p>
     * 
     * <p>
     * Common-io 2.4{@link org.apache.commons.io.FileUtils#byteCountToDisplaySize(long)}有缺点，显示的是整数GB 不带小数（比如1.99G 显示为1G），apache 论坛上争议比较大
     * </p>
     * 
     * @param fileSize
     *            文件大小 单位byte
     * @return 文件大小byte 转换
     * @see #getFileSize(File)
     * @see org.apache.commons.io.FileUtils#ONE_GB
     * @see org.apache.commons.io.FileUtils#ONE_MB
     * @see org.apache.commons.io.FileUtils#ONE_KB
     * 
     * @see org.apache.commons.io.FileUtils#byteCountToDisplaySize(long)
     */
    public static String formatSize(long fileSize){
        String danwei = "";
        long chushu = 1;// 除数
        if (fileSize >= FileUtils.ONE_GB){
            danwei = "GB";
            chushu = FileUtils.ONE_GB;
        }else if (fileSize >= FileUtils.ONE_MB){
            danwei = "MB";
            chushu = FileUtils.ONE_MB;
        }else if (fileSize >= FileUtils.ONE_KB){
            danwei = "KB";
            chushu = FileUtils.ONE_KB;
        }else{
            return fileSize + "Bytes";
        }
        String yushu = 100 * (fileSize % chushu) / chushu + ""; // 除完之后的余数
        if ("0".equals(yushu)){
            return fileSize / chushu + danwei;
        }
        return fileSize / chushu + "." + yushu + danwei;
    }

    /**
     * 取得文件大小(单位字节).
     * 
     * @param file
     *            文件
     * @return 此抽象路径名表示的文件的长度，以字节为单位；<br>
     *         如果文件不存在，则返回 0L.<br>
     *         对于表示特定于系统的实体（比如设备或管道）的路径名，某些操作系统可能返回 0L.
     * @see File#length()
     */
    public static long getFileSize(File file){
        return file.length();
    }

    /**
     * To ur ls.
     *
     * @param filePathList
     *            the paths
     * @return the UR l[]
     * @see #toURLs(String...)
     * @since 1.4.0
     */
    public static URL[] toURLs(List<String> filePathList){
        if (Validator.isNullOrEmpty(filePathList)){
            throw new NullPointerException("paths can't be null/empty!");
        }
        String[] filePaths = ConvertUtil.toArray(filePathList, String.class);
        return toURLs(filePaths);
    }

    /**
     * To ur ls.
     *
     * @param filePaths
     *            the file paths
     * @return the UR l[]
     * @see com.feilong.core.bean.ConvertUtil#convert(String[], Class)
     * @see org.apache.commons.io.FileUtils#toURLs(File[])
     * @since 1.4.0
     */
    public static URL[] toURLs(String...filePaths){
        if (Validator.isNullOrEmpty(filePaths)){
            throw new NullPointerException("paths can't be null/empty!");
        }
        File[] files = ConvertUtil.convert(filePaths, File.class);
        try{
            return org.apache.commons.io.FileUtils.toURLs(files);
        }catch (IOException e){
            LOGGER.error("", e);
            throw new UncheckedIOException(e);
        }
    }
}
