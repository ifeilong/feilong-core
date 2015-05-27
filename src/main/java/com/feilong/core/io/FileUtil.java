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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.ArrayUtil;
import com.feilong.core.util.Validator;

/**
 * {@link File}文件操作.<br>
 * 
 * File的几个属性:
 * <ul>
 * <li>{@link File#getAbsolutePath()} 得到的是全路径</li>
 * <li>{@link File#getPath()} 得到的是构造file的时候的路径</li>
 * <li>{@link File#getCanonicalPath()} 可以看到CanonicalPath不但是全路径，而且把..或者.这样的符号解析出来.</li>
 * </ul>
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-5-23 下午5:00:54
 * @version 1.0.7 2014-5-23 20:27 add {@link #getFileFormatSize(File)}
 * @see java.io.File
 * @since 1.0.0
 */
public final class FileUtil{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

    /** Don't let anyone instantiate this class. */
    private FileUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 将文件转成ByteArray.
     *
     * @param file
     *            file
     * @return byteArrayOutputStream.toByteArray();
     * @throws UncheckedIOException
     *             the unchecked io exception
     */
    public static final byte[] convertFileToByteArray(File file) throws UncheckedIOException{
        InputStream inputStream = FileUtil.getFileInputStream(file);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] bytes = new byte[IOConstants.DEFAULT_BUFFER_LENGTH];
        int j;

        try{
            while ((j = bufferedInputStream.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes, 0, j);
            }
            byteArrayOutputStream.flush();

            return byteArrayOutputStream.toByteArray();
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }finally{
            try{
                // 为避免内存泄漏，Stream的Close是必须的.即使中途发生了异常，也必须Close，
                // 因此请在finally Block中描述close()
                byteArrayOutputStream.close();
                bufferedInputStream.close();
            }catch (IOException e){
                throw new UncheckedIOException(e);
            }
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
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see java.io.FileOutputStream#FileOutputStream(String)
     * @see #getFileOutputStream(String, boolean)
     */
    public static final FileOutputStream getFileOutputStream(String filePath) throws UncheckedIOException{
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
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see #getFileOutputStream(String, boolean)
     * @since 1.2.0
     */
    public static final FileOutputStream getFileOutputStream(String filePath,FileWriteMode fileWriteMode) throws UncheckedIOException{
        boolean append = (fileWriteMode == FileWriteMode.APPEND);
        return getFileOutputStream(filePath, append);
    }

    /**
     * 获得 {@link java.io.FileOutputStream} 文件输出流 （或其他文件写入对象）打开文件进行写入 .<br>
     * {@link java.io.FileOutputStream} 用于写入诸如图像数据之类的原始字节的流.<br>
     * 如果要写入字符流，请考虑使用 {@link java.io.FileWriter}.
     *
     * @param filePath
     *            the file path
     * @param append
     *            if true, then bytes will be written to the end of the file rather than the beginning
     * @return the file output stream
     * @throws UncheckedIOException
     *             the unchecked io exception
     * @see java.io.FileOutputStream#FileOutputStream(String, boolean)
     * @since 1.2.0
     */
    //默认 Access Modifiers 权限修饰符
    static final FileOutputStream getFileOutputStream(String filePath,boolean append) throws UncheckedIOException{
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(filePath, append);
            return fileOutputStream;
        }catch (FileNotFoundException e){
            throw new UncheckedIOException(e);
        }
    }

    //********************************************************************************************

    /**
     * FileInputStream 从文件系统中的某个文件中获得输入字节.哪些文件可用取决于主机环境.<br>
     * FileInputStream 用于读取诸如图像数据之类的原始字节流.要读取字符流，请考虑使用 FileReader..
     *
     * @param fileName
     *            该文件通过文件系统中的路径名 fileName 指定.
     * @return FileInputStream
     * @throws UncheckedIOException
     *             the unchecked io exception
     */
    public static final FileInputStream getFileInputStream(String fileName) throws UncheckedIOException{
        File file = new File(fileName);
        return getFileInputStream(file);
    }

    /**
     * FileInputStream 从文件系统中的某个文件中获得输入字节.哪些文件可用取决于主机环境.<br>
     * FileInputStream 用于读取诸如图像数据之类的原始字节流.要读取字符流，请考虑使用 FileReader..
     *
     * @param file
     *            为了进行读取而打开的文件.
     * @return FileInputStream
     * @throws UncheckedIOException
     *             the unchecked io exception
     */
    public static final FileInputStream getFileInputStream(File file) throws UncheckedIOException{
        try{
            // 如果指定文件不存在，或者它是一个目录，而不是一个常规文件，抑或因为其他某些原因而无法打开进行读取，则抛出 FileNotFoundException.
            FileInputStream fileInputStream = new FileInputStream(file);
            return fileInputStream;
        }catch (FileNotFoundException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 判断一个目录 是否是空目录(里面没有文件).
     *
     * @param directory
     *            指定一个存在的文件夹
     * @return <ul>
     *         <li>如果directory isNullOrEmpty,throw IllegalArgumentException</li>
     *         <li>如果directory don't exists,throw IllegalArgumentException</li>
     *         <li>如果directory is not Directory,throw IllegalArgumentException</li>
     *         <li>return file.list() ==0</li>
     *         </ul>
     * @throws NullPointerException
     *             the null pointer exception
     * @throws IllegalArgumentException
     *             the illegal argument exception
     */
    public static boolean isEmptyDirectory(String directory) throws NullPointerException,IllegalArgumentException{
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
        String[] fileList = file.list();

        int fileListLength = fileList.length;

        if (log.isDebugEnabled()){
            log.debug("file :[{}] list length:[{}]", directory, fileListLength);
            for (String fileName : fileList){
                log.debug(fileName);
            }
        }
        boolean flag = (fileListLength == 0);
        return flag;
    }

    // [start] 文件夹操作(createDirectory/deleteFileOrDirectory/deleteFileOrDirectory)
    /**
     * 创建文件夹by文件路径,支持级联创建.<br>
     * 
     * 注意:
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
     * @throws NullPointerException
     *             the null pointer exception
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @see #getParent(String)
     * @see #createDirectory(String)
     * @since 1.2.0
     */
    public static void createDirectoryByFilePath(String filePath) throws NullPointerException,IllegalArgumentException{
        if (Validator.isNullOrEmpty(filePath)){
            throw new NullPointerException("filePath can't be null/empty!");
        }
        String directory = getParent(filePath);
        createDirectory(directory);
    }

    /**
     * 创建文件夹,支持级联创建.<br>
     * 注意:
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
     * @throws NullPointerException
     *             the null pointer exception
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @see #createDirectoryByFilePath(String)
     */
    public static void createDirectory(String directory) throws NullPointerException,IllegalArgumentException{
        if (Validator.isNullOrEmpty(directory)){
            throw new NullPointerException("filePath can't be null/empty!");
        }
        File directoryFile = new File(directory);

        boolean isExists = directoryFile.exists();

        //***********do with 存在******************
        if (isExists){//存在
            log.debug("exists directoryFile:[{}],don't need mkdirs,nothing to do~", directoryFile);
            return;
        }

        //***********do with 不存在******************
        String absolutePath = directoryFile.getAbsolutePath();

        // mkdir 如果 parent 目录不存在 会返回false 不会报错
        boolean flag = directoryFile.mkdirs();
        // 级联创建 父级文件夹
        if (!flag){
            String msg = "File [" + absolutePath + "] could not be created";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }else{
            //创建成功 记录下日志
            log.debug("success mkdirs:[{}]~~", absolutePath);
        }
    }

    /**
     * 删除某个文件或者文件夹.
     *
     * @param fileName
     *            文件或者文件夹名称
     * @throws IllegalArgumentException
     *             if file not exists
     */
    public static void deleteFileOrDirectory(String fileName) throws IllegalArgumentException{
        File file = new File(fileName);
        if (file.exists()){
            deleteFileOrDirectory(file);
        }
        throw new IllegalArgumentException("file:[" + fileName + "] not exists,please check it!");
    }

    /**
     * 删除文件或者文件夹,如果是文件夹 ,递归深层次 删除.
     * 
     * @param file
     *            文件或者文件夹名称
     */
    public static void deleteFileOrDirectory(File file){
        if (file.exists() && file.isDirectory()){
            File[] files = file.listFiles();
            if (null != files && files.length > 0){
                for (File currentFile : files){
                    if (!currentFile.isDirectory()){
                        currentFile.delete();
                    }else{
                        deleteFileOrDirectory(currentFile);
                    }
                }
            }
            file.delete();
        }else{
            file.delete();
        }
    }

    // [end]

    // ************************************************************

    // [start] 解析文件名称

    /**
     * 将一个文件使用新的文件后缀,其余部分不变
     * <p>
     * 如果一个文件没有后缀,将会添加 .+newPostfixName
     * </p>
     * 
     * <pre>
     * {@code
     * Example 1:
     * 		String fileName="F:/pie2.png";
     *       FileUtil.getNewFileName(fileName, "gif")
     *       
     *       return F:/pie2.gif
     * }
     * </pre>
     * 
     * @param fileName
     *            文件名称,比如 F:/pie2.png
     * @param newPostfixName
     *            不带.号, 比如 gif
     * @return 新文件名称
     * @throws NullPointerException
     *             isNullOrEmpty(fileName) or isNullOrEmpty(newPostfixName)
     */
    public static final String getNewFileName(String fileName,String newPostfixName) throws NullPointerException{

        if (Validator.isNullOrEmpty(fileName)){
            throw new NullPointerException("fileName can't be null/empty!");
        }
        if (Validator.isNullOrEmpty(newPostfixName)){
            throw new NullPointerException("newPostfixName can't be null/empty!");
        }

        // 有后缀
        if (hasPostfixName(fileName)){
            return fileName.substring(0, fileName.lastIndexOf(".") + 1) + newPostfixName;
        }
        // 没有后缀直接拼接
        return fileName + "." + newPostfixName;
    }

    /**
     * 判断是否有后缀.
     * 
     * @param fileName
     *            the file name
     * @return true, if successful
     */
    public static final boolean hasPostfixName(String fileName){
        String fileNameString = getFileName(fileName);
        int lastIndexOf = fileNameString.lastIndexOf(".");
        if (-1 == lastIndexOf){
            return false;
        }
        return true;
    }

    /**
     * 获得文件后缀名(不带. 的后缀),并返回原样字母<br>
     * 如果文件没有后缀名 返回 "" (EMPTY).
     * 
     * <pre>
     * {@code
     * Example 1: 
     * F:/pie2.png, return png
     * 
     * Example 2: 
     * F:/pie2, return ""
     * }
     * </pre>
     * 
     * @param fileName
     *            文件名称
     * @return 不带. 的后缀
     * @see org.apache.commons.io.FilenameUtils#getExtension(String)
     * @see java.lang.String#substring(int, int)
     */
    public static final String getFilePostfixName(String fileName){
        if (hasPostfixName(fileName)){
            String filePostfixName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            return filePostfixName;
        }
        return "";
    }

    /**
     * 获得文件后缀名,并返回小写字母<br>
     * 如果文件没有后缀名 返回 "".
     * 
     * @param fileName
     *            文件名称
     * @return 不带. 的后缀
     * @see org.apache.commons.io.FilenameUtils#getExtension(String)
     */
    public static final String getFilePostfixNameLowerCase(String fileName){
        String postfixName = getFilePostfixName(fileName);
        return postfixName.toLowerCase();
    }

    /**
     * 获得文件的不带后缀名的名称. <br>
     * 
     * <pre>
     * {@code
     * Example 1: 
     * F:/pie2.png, return F:/pie2
     * 
     * Example 2: 
     * pie2.png, return pie2
     * }
     * </pre>
     * 
     * @param fileName
     *            文件名称
     * @return 获得文件的不带后缀名的名称
     * @see java.lang.String#substring(int, int)
     */
    public static final String getFilePreName(String fileName){
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 获得带后缀的文件纯名称<br>
     * 如:F:/pie2.png,return pie2.png
     * 
     * @param fileName
     *            the file name
     * @return the file name
     * @see java.io.File#getName()
     */
    public static final String getFileName(String fileName){
        File file = new File(fileName);
        return file.getName();
    }

    // [end]
    /**
     * 获得文件的最顶层 父文件夹名称
     * 
     * <pre>
     * {@code
     *   Example 1:
     *      "mp2-product\\mp2-product-impl\\src\\main\\java\\com\\baozun\\mp2\\rpc\\impl\\item\\repo\\package-info.java"
     *      
     *      返回 mp2-product
     * }
     * </pre>
     * 
     * @param pathname
     *            通过将给定路径名字符串转换为抽象路径名来创建一个新 File 实例.如果给定字符串是空字符串，那么结果是空抽象路径名.
     * @return 如果没有父文件夹,返回自己,比如 E:/ 直接返回 E:/
     * @throws NullPointerException
     *             isNullOrEmpty(fileName)
     * 
     * @since 1.0.7
     */
    public static final String getFileTopParentName(String pathname) throws NullPointerException{
        if (Validator.isNullOrEmpty(pathname)){
            throw new NullPointerException("pathname can't be null/empty!");
        }

        File file = new File(pathname);

        String parent = file.getParent();

        if (Validator.isNullOrEmpty(parent)){
            return pathname;
        }

        //递归
        String fileTopParentName = getFileTopParentName(file);

        if (log.isDebugEnabled()){
            log.debug("pathname:[{}],fileTopParentName:[{}]", pathname, fileTopParentName);
        }
        return fileTopParentName;
    }

    /**
     * 返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回 null.
     *
     * @param path
     *            the path
     * @return the parent
     * @throws NullPointerException
     *             the null pointer exception
     * @see java.io.File#getParent()
     */
    public static String getParent(String path) throws NullPointerException{
        if (Validator.isNullOrEmpty(path)){
            throw new NullPointerException("pathname can't be null/empty!");
        }
        File file = new File(path);
        String parent = file.getParent();
        return parent;
    }

    /**
     * 获得文件的最顶层 父文件夹名称
     * 
     * <pre>
     * {@code
     *   Example 1:
     *      "mp2-product\\mp2-product-impl\\src\\main\\java\\com\\baozun\\mp2\\rpc\\impl\\item\\repo\\package-info.java"
     *      
     *      返回 mp2-product
     * }
     * </pre>
     * 
     * @param file
     *            the file
     * @return 如果没有父文件夹,返回自己,比如 E:/ 直接返回 E:/
     * @throws NullPointerException
     *             isNullOrEmpty(fileName)
     * @since 1.0.7
     */
    public static final String getFileTopParentName(File file) throws NullPointerException{
        if (Validator.isNullOrEmpty(file)){
            throw new NullPointerException("file can't be null/empty!");
        }

        File parent = file.getParentFile();

        if (Validator.isNullOrEmpty(parent)){
            String name = file.getPath();//E:/--->E:\

            if (log.isDebugEnabled()){
                log.debug("parent is isNullOrEmpty,return file name:{}", name);
            }
            return name;
        }
        //递归
        String fileTopParentName = getFileTopParentName(parent);

        if (log.isDebugEnabled()){
            log.debug("file.getAbsolutePath():[{}],fileTopParentName:[{}]", file.getAbsolutePath(), fileTopParentName);
        }
        return fileTopParentName;
    }

    /**
     * 判断文件是否存在.
     * 
     * @param filePath
     *            the file path
     * @return 如果文件存在,返回true
     */
    public static final boolean isExistFile(String filePath){
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
    public static final boolean isNotExistFile(String filePath){
        return !isExistFile(filePath);
    }

    // ************************************************************************
    //
    // 各种判断
    //
    // ************************************************************************
    /**
     * 上传的文件是否是常用图片格式.
     * 
     * @param fileName
     *            文件名称,可以是全路径 ,也可以是 部分路径,会解析取到后缀名
     * @return 上传的文件是否是常用图片格式
     */
    public static final boolean isCommonImage(String fileName){
        return isInAppointTypes(fileName, IOConstants.COMMON_IMAGES);
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
    public static final boolean isInAppointTypes(String fileName,String[] appointTypes){
        return ArrayUtil.isContain(appointTypes, getFilePostfixName(fileName));
    }

    // ************************************************************
    /**
     * 文件大小格式化.
     * <p>
     * 目前支持单位有 GB MB KB以及最小单位 Bytes
     * </p>
     * <p>
     * Common-io 2.4{@link org.apache.commons.io.FileUtils#byteCountToDisplaySize(long)}有缺点，显示的是整数GB 不带小数（比如1.99G 显示为1G），apache 论坛上争议比较大
     * </p>
     * 
     * @param fileSize
     *            文件大小 单位byte
     * @return 文件大小byte 转换
     * @see #getFileSize(File)
     * @see IOConstants#GB
     * @see IOConstants#MB
     * @see IOConstants#KB
     * 
     * @see org.apache.commons.io.FileUtils#byteCountToDisplaySize(long)
     */
    public static final String formatSize(long fileSize){
        String danwei = "Bytes";
        // 除完之后的余数
        String yushu = "";
        // 除数
        long chushu = 1;
        if (fileSize >= IOConstants.GB){
            danwei = "GB";
            chushu = IOConstants.GB;
        }else if (fileSize >= IOConstants.MB){
            danwei = "MB";
            chushu = IOConstants.MB;
        }else if (fileSize >= IOConstants.KB){
            danwei = "KB";
            chushu = IOConstants.KB;
        }
        if (chushu == 1){
            return fileSize + danwei;
        }
        yushu = 100 * (fileSize % chushu) / chushu + "";
        if ("0".equals(yushu)){
            return fileSize / chushu + danwei;
        }
        return fileSize / chushu + "." + yushu + danwei;
    }

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
     * @throws NullPointerException
     *             isNullOrEmpty(file)
     * @see #getFileSize(File)
     * @see IOConstants#GB
     * @see IOConstants#MB
     * @see IOConstants#KB
     * @see org.apache.commons.io.FileUtils#byteCountToDisplaySize(long)
     * @since 1.0.7
     */
    public static final String getFileFormatSize(File file) throws NullPointerException{
        if (Validator.isNullOrEmpty(file)){
            throw new NullPointerException("file can't be null/empty!");
        }
        long fileSize = getFileSize(file);
        return formatSize(fileSize);
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
     * 不同的操作系统 对系统文件名称有要求,此方法的作用就是处理这些文件名称.
     * 
     * @param fileName
     *            文件名称
     * @return 可用的文件名称
     * @see #MICROSOFT_PC
     * @since 1.0.7
     */
    public static String getFormatFileName(final String fileName){

        String formatFileName = fileName;

        for (int i = 0, j = MICROSOFT_PC.length; i < j; ++i){
            String[] arrayElement = MICROSOFT_PC[i];

            String oldChar = arrayElement[0];
            String newChar = arrayElement[1];
            if (formatFileName.contains(oldChar)){
                log.warn("formatFileName:[{}] contains oldChar:[{}],will replace newChar:[{}]", formatFileName, oldChar, newChar);
                formatFileName = formatFileName.replace(oldChar, newChar);
            }
        }
        return formatFileName;
    }

    /**
     * 文件名称由文件名和扩展名组成，两者由小黑点分隔，扩展名通常是用来表示文件的类 别.
     * <p>
     * Windows 中整个文件名称最长 255 个字符（一个中文字算两个字符）； <br>
     * DOS 中，文件名最长 8 字符，扩展名最长 3 字符，故又称 DOS 8.3 命名规则. <br>
     * 文件名称可仅有前半部,即无扩展名，如文件名称最短可以是 “ 1 ” 、 “ C ” 等. <br>
     * 给文件命名还应注意以下规则：
     * </p>
     * <ul>
     * <li>文件名不能包含下列任何字符之一（共 9 个）： \/:*?"<>|</li>
     * <li>不能单独使用 “ 设备名 ” 作文件名. “ 设备名 ” 包括： con ， aux ， com0 ~ com9 ， lpt0 ~ lpt9 ， nul ， prn</li>
     * <li>文件名不区分大小写，如 A.txt 和 a.TxT 表示同一文件</li>
     * </ul>
     * 
     * @see <a href="http://support.microsoft.com/kb/177506/zh-cn">错误消息： 文件名是无效的或不能包含任何以下字符</a>
     * @since 1.0.7
     */
    private static final String[][] MICROSOFT_PC = { //
                                                 //			{ "\\", "" }, // \
            //	{ "/", "" }, // /
            { "\"", "" }, // "
            { ":", "" }, // :
            { "*", "" }, // *
            { "?", "" }, // ?
            { "<", "" }, // <
            { ">", "" }, // >
            { "|", "" }, // |
                                                 };
}
