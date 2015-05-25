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
package com.feilong.core.date;

import java.io.Serializable;

/**
 * 自定义下拉框 包含 值和显示文本.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2010-8-11 上午11:27:47
 * @deprecated
 * @since 1.0
 */
@Deprecated
public class SelectEntity implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** 值. */
    private int               value;

    /** 显示的文本. */
    private int               label;

    /**
     * Instantiates a new select entity.
     */
    public SelectEntity(){

    }

    /**
     * Instantiates a new select entity.
     * 
     * @param value
     *            值
     * @param label
     *            显示的文本
     */
    public SelectEntity(int value, int label){
        this.value = value;
        this.label = label;
    }

    /**
     * 获得 值.
     * 
     * @return the 值
     */
    public int getValue(){
        return value;
    }

    /**
     * 设置 值.
     * 
     * @param value
     *            the new 值
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     * 获得 显示的文本.
     * 
     * @return the 显示的文本
     */
    public int getLabel(){
        return label;
    }

    /**
     * 设置 显示的文本.
     * 
     * @param label
     *            the new 显示的文本
     */
    public void setLabel(int label){
        this.label = label;
    }
}