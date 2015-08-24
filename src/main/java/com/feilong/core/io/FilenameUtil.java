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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.Validator;

/**
 * The Class FilenameUtil.
 *
 * @author feilong
 * @version 1.4.0 2015年8月4日 上午11:47:18
 * @see org.apache.commons.io.FilenameUtils
 * @since 1.4.0
 */
public final class FilenameUtil{

    /** The Constant LOGGER. */
    private static final Logger     LOGGER       = LoggerFactory.getLogger(FilenameUtil.class);

    /**
     * 文件名称由文件名和扩展名组成,两者由小黑点分隔,扩展名通常是用来表示文件的类 别.
     * <p>
     * Windows 中整个文件名称最长 255 个字符（一个中文字算两个字符）； <br>
     * DOS 中,文件名最长 8 字符,扩展名最长 3 字符,故又称 DOS 8.3 命名规则. <br>
     * 文件名称可仅有前半部,即无扩展名,如文件名称最短可以是 “ 1 ” 、 “ C ” 等. <br>
     * 给文件命名还应注意以下规则：
     * </p>
     * <ul>
     * <li>文件名不能包含下列任何字符之一（共 9 个）： \/:*?"<>|</li>
     * <li>不能单独使用 “ 设备名 ” 作文件名. “ 设备名 ” 包括： con , aux , com0 ~ com9 , lpt0 ~ lpt9 , nul , prn</li>
     * <li>文件名不区分大小写,如 A.txt 和 a.TxT 表示同一文件</li>
     * </ul>
     * 
     * @see <a href="http://support.microsoft.com/kb/177506/zh-cn">错误消息： 文件名是无效的或不能包含任何以下字符</a>
     * @since 1.0.7
     */
    private static final String[][] MICROSOFT_PC = { //
                                                 //            { "\\", "" }, // \
            //  { "/", "" }, // /
            { "\"", "" }, // "
            { ":", "" }, // :
            { "*", "" }, // *
            { "?", "" }, // ?
            { "<", "" }, // <
            { ">", "" }, // >
            { "|", "" }, // |
                                                 };

    /** Don't let anyone instantiate this class. */
    private FilenameUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
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
                LOGGER.warn("formatFileName:[{}] contains oldChar:[{}],will replace newChar:[{}]", formatFileName, oldChar, newChar);
                formatFileName = formatFileName.replace(oldChar, newChar);
            }
        }
        return formatFileName;
    }

    /**
     * 获得带后缀的文件纯名称.
     * 
     * <p>
     * 如:F:/pie2.png,return pie2.png
     * </p>
     * 
     * @param fileName
     *            the file name
     * @return the file name
     * @see java.io.File#getName()
     */
    public static String getFileName(String fileName){
        File file = new File(fileName);
        return file.getName();
    }

    /**
     * 获得文件的不带后缀名的名称.
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
    public static String getFilePreName(String fileName){
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 获得文件后缀名(不带. 的后缀),并返回原样字母.
     * 
     * <p>
     * 如果文件没有后缀名 返回 "" (EMPTY)
     * </p>
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
     * Gets the extension of a filename.
     * <p>
     * This method returns the textual part of the filename after the last dot. There must be no directory separator after the dot.
     * 
     * <pre>
     * foo.txt      --> "txt"
     * a/b/c.jpg    --> "jpg"
     * a/b.txt/c    --> ""
     * a/b/c        --> ""
     * </pre>
     * <p>
     * The output will be the same irrespective of the machine that the code is running on.
     * 
     * @param fileName
     *            文件名称
     * @return 不带. 的后缀
     * @see org.apache.commons.io.FilenameUtils#getExtension(String)
     * @see java.lang.String#substring(int, int)
     * @since 1.4.0
     */
    public static String getExtension(String fileName){
        return StringUtils.defaultString(org.apache.commons.io.FilenameUtils.getExtension(fileName));
    }

    /**
     * 获得文件后缀名,并返回小写字母.
     * 
     * <p>
     * 如果文件没有后缀名 返回 ""
     * </p>
     * 
     * @param fileName
     *            文件名称
     * @return 不带. 的后缀
     * @see org.apache.commons.io.FilenameUtils#getExtension(String)
     */
    public static String getFilePostfixNameLowerCase(String fileName){
        return getExtension(fileName).toLowerCase();
    }

    // [start] 解析文件名称

    /**
     * 将一个文件使用新的文件后缀,其余部分不变.
     * 
     * <p>
     * 如果一个文件没有后缀,将会添加 .+newPostfixName
     * </p>
     * 
     * <pre>
     * {@code
     * Example 1:
     *      String fileName="F:/pie2.png";
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
     */
    public static String getNewFileName(String fileName,String newPostfixName){
        if (Validator.isNullOrEmpty(fileName)){
            throw new NullPointerException("fileName can't be null/empty!");
        }
        if (Validator.isNullOrEmpty(newPostfixName)){
            throw new NullPointerException("newPostfixName can't be null/empty!");
        }

        // 有后缀
        if (hasExtension(fileName)){
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
     * @see org.apache.commons.io.FilenameUtils#indexOfExtension(String)
     * @since 1.4.0
     */
    public static boolean hasExtension(String fileName){
        return -1 != FilenameUtils.indexOfExtension(fileName);
    }

    // [end]

    /**
     * 获得文件的最顶层 父文件夹名称.
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
     *            通过将给定路径名字符串转换为抽象路径名来创建一个新 File 实例.如果给定字符串是空字符串,那么结果是空抽象路径名.
     * @return 如果没有父文件夹,返回自己,比如 E:/ 直接返回 E:/
     * @since 1.0.7
     */
    public static String getFileTopParentName(String pathname){
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
        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("pathname:[{}],fileTopParentName:[{}]", pathname, fileTopParentName);
        }
        return fileTopParentName;
    }

    /**
     * 获得文件的最顶层父文件夹名称.
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
     * @since 1.0.7
     */
    public static String getFileTopParentName(File file){
        if (Validator.isNullOrEmpty(file)){
            throw new NullPointerException("file can't be null/empty!");
        }

        File parent = file.getParentFile();
        if (Validator.isNullOrEmpty(parent)){
            String name = file.getPath();//E:/--->E:\

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug("parent is isNullOrEmpty,return file name:{}", name);
            }
            return name;
        }
        //递归
        String fileTopParentName = getFileTopParentName(parent);

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug("file.getAbsolutePath():[{}],fileTopParentName:[{}]", file.getAbsolutePath(), fileTopParentName);
        }
        return fileTopParentName;
    }
}
