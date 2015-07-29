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

import java.io.Serializable;
import java.util.Date;

import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;

/**
 * 文件信息 entity.
 * 
 * @author feilong
 * @version 1.0.0 Dec 7, 2013 8:13:34 PM
 * @since 1.0.0
 */
public class FileInfoEntity implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long   serialVersionUID = 288232184048495608L;

    /** The date pattern. */
    private final static String datePattern      = DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND;

    /** 名称. */
    private String              name;

    /** 类型. */
    private FileType            fileType;

    /** 文件大小， 单位 字节,如果是文件夹 不显示size. */
    private Long                size;

    /** 格式化显示的size. */
    private String              formatSize;

    /**
     * 返回此抽象路径名表示的文件最后一次被修改的时间. <br>
     * 表示文件最后一次被修改的时间的 long 值，用与时间点（1970 年 1 月 1 日，00:00:00 GMT）之间的毫秒数表示；如果该文件不存在，或者发生 I/O 错误，则返回 0L.
     */
    private Long                lastModified;

    /** 格式化显示的时间默认 yy-mm hh:ss. */
    private String              formatLastModified;

    /**
     * Gets the 名称.
     * 
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the 名称.
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the 类型.
     * 
     * @return the fileType
     */
    public FileType getFileType(){
        return fileType;
    }

    /**
     * Sets the 类型.
     * 
     * @param fileType
     *            the fileType to set
     */
    public void setFileType(FileType fileType){
        this.fileType = fileType;
    }

    /**
     * Gets the 文件大小， 单位 字节,如果是文件夹 不显示size.
     * 
     * @return the size
     */
    public Long getSize(){
        return size;
    }

    /**
     * Sets the 文件大小， 单位 字节,如果是文件夹 不显示size.
     * 
     * @param size
     *            the size to set
     */
    public void setSize(Long size){
        this.size = size;
    }

    /**
     * Gets the 返回此抽象路径名表示的文件最后一次被修改的时间. <br>
     * 表示文件最后一次被修改的时间的 long 值，用与时间点（1970 年 1 月 1 日，00:00:00 GMT）之间的毫秒数表示；如果该文件不存在，或者发生 I/O 错误，则返回 0L.
     * 
     * @return the lastModified
     */
    public Long getLastModified(){
        return lastModified;
    }

    /**
     * Sets the 返回此抽象路径名表示的文件最后一次被修改的时间. <br>
     * 表示文件最后一次被修改的时间的 long 值，用与时间点（1970 年 1 月 1 日，00:00:00 GMT）之间的毫秒数表示；如果该文件不存在，或者发生 I/O 错误，则返回 0L.
     * 
     * @param lastModified
     *            the lastModified to set
     */
    public void setLastModified(Long lastModified){
        this.lastModified = lastModified;
    }

    /**
     * Gets the 格式化显示的size.
     * 
     * @return the formatSize
     */
    public String getFormatSize(){
        formatSize = FileUtil.formatSize(size);
        return formatSize;
    }

    /**
     * Gets the 格式化显示的时间默认 yy-mm hh:ss.
     * 
     * @return the formatLastModified
     */
    public String getFormatLastModified(){
        Date date = new Date(lastModified);
        formatLastModified = DateUtil.date2String(date, datePattern);
        return formatLastModified;
    }

}
