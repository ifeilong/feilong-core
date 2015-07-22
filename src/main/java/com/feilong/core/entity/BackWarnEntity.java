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
package com.feilong.core.entity;

import java.io.Serializable;

/**
 * 返回提示.
 * <p>
 * 可以用来被继承,以便实现各种定制化的功能
 * </p>
 * 
 * @author feilong
 * @version 1.0.0 2010-6-24 上午03:14:56
 * @since 1.0.0
 */
public class BackWarnEntity implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3703766119930341844L;

    /** 是否成功. */
    private boolean           isSuccess;

    /**
     * 错误码,某些时候需要 设置 ,而前台判断(可选).
     *
     * @since 1.2.2
     */
    private Integer           errorCode        = null;

    /** 描述. */
    private Serializable      description;

    /**
     * Instantiates a new back warn entity.
     */
    public BackWarnEntity(){
    }

    /**
     * The Constructor.
     *
     * @param isSuccess
     *            the is success
     * @param description
     *            the description
     */
    public BackWarnEntity(boolean isSuccess, Serializable description){
        this.isSuccess = isSuccess;
        this.description = description;
    }

    /**
     * The Constructor.
     *
     * @param isSuccess
     *            the is success
     * @param errorCode
     *            the error code
     * @param description
     *            the description
     * @since 1.2.2
     */
    public BackWarnEntity(boolean isSuccess, Integer errorCode, Serializable description){
        super();
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.description = description;
    }

    /**
     * 获得 是否成功.
     *
     * @return the isSuccess
     */
    public boolean getIsSuccess(){
        return isSuccess;
    }

    /**
     * 设置 是否成功.
     *
     * @param isSuccess
     *            the isSuccess to set
     */
    public void setIsSuccess(boolean isSuccess){
        this.isSuccess = isSuccess;
    }

    /**
     * 获得 描述.
     *
     * @return the description
     */
    public Serializable getDescription(){
        return description;
    }

    /**
     * 设置 描述.
     *
     * @param description
     *            the description to set
     */
    public void setDescription(Serializable description){
        this.description = description;
    }

    /**
     * 获得 错误码,某些时候需要 设置 ,而前台判断(可选).
     *
     * @return the errorCode
     * @since 1.2.2
     */
    public Integer getErrorCode(){
        return errorCode;
    }

    /**
     * 设置 错误码,某些时候需要 设置 ,而前台判断(可选).
     *
     * @param errorCode
     *            the errorCode to set
     * @since 1.2.2
     */
    public void setErrorCode(Integer errorCode){
        this.errorCode = errorCode;
    }
}