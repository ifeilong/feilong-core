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
package com.feilong.core.tools.jsonlib.processor;

import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;

/**
 * 时间转换 日期值处理器实现.
 * 
 * @author feilong
 * @version 1.0.5 Jan 26, 2013 3:49:55 PM
 * @since 1.0.5
 */
public class DateJsonValueProcessor implements JsonValueProcessor{

    /** The date pattern. */
    private String datePattern = DatePattern.COMMON_DATE_AND_TIME;

    /**
     * Instantiates a new json date value processor.
     */
    public DateJsonValueProcessor(){
    }

    /**
     * The Constructor.
     *
     * @param datePattern
     *            the date pattern
     */
    public DateJsonValueProcessor(String datePattern){
        this.datePattern = datePattern;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processArrayValue(Object value,JsonConfig jsonConfig){
        return process(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processObjectValue(String key,Object value,JsonConfig jsonConfig){
        return process(value);
    }

    /**
     * Process.
     *
     * @param value
     *            the value
     * @return the object
     */
    private Object process(Object value){
        if (null == value){
            return null;
        }
        if (value instanceof Date){
            return DateUtil.date2String((Date) value, datePattern);
        }
        return value.toString();
    }

    /**
     * 获得 the date pattern.
     *
     * @return the date pattern
     */
    public String getDatePattern(){
        return datePattern;
    }

    /**
     * 设置 the date pattern.
     *
     * @param datePattern
     *            the new date pattern
     */
    public void setDatePattern(String datePattern){
        this.datePattern = datePattern;
    }
}