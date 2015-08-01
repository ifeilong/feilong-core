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

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang3.SystemUtils;

/**
 * 特殊文件夹.
 * 
 * @author feilong
 * @version 1.0.1 2011-3-22 下午11:45:57
 * @since 1.0.1
 * @see org.apache.commons.lang3.SystemUtils
 */
public final class SpecialFolder{

    /** Don't let anyone instantiate this class. */
    private SpecialFolder(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获得操作系统临时文件夹.
     * <ul>
     * <li>win7:C:\Users\VENUSD~1\AppData\Local\Temp\</li>
     * <li>win7:C:\Users\feilong\AppData\Local\Temp\</li>
     * </ul>
     * 
     * @return 操作系统临时文件夹
     * 
     * @see org.apache.commons.lang3.SystemUtils#JAVA_IO_TMPDIR
     */
    public static String getTemp(){
        return SystemUtils.JAVA_IO_TMPDIR;
    }

    /**
     * 获得桌面<br>
     * example:win7:C:\Users\venusdrogon\Desktop.
     * 
     * <br>
     * 或者 通过环境变量 USERPROFILE<br>
     * return C:\Users\venusdrogon 再拼接 Desktop 获得
     * 
     * @return 桌面地址
     * @see FileSystemView#getHomeDirectory()
     */
    public static String getDesktop(){
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File file = fileSystemView.getHomeDirectory();
        // 或者 通过环境变量 USERPROFILE======>C:\Users\venusdrogon 再拼接 Desktop 获得
        return file.getPath();
    }

    /**
     * 获得我的文档(返回文件选择器的用户默认起始目录),该文件可以通过360等工具更改.
     * <ul>
     * <li>win7:D:\noMove\documents</li>
     * </ul>
     * 
     * @return 我的文档地址
     * @see FileSystemView#getDefaultDirectory()
     */
    public static String getMyDocuments(){
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        File file = fileSystemView.getDefaultDirectory();
        return file.getPath();
    }
}
