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
package com.feilong.core.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 设置 连接object成为字符串 的配置.
 * 
 * <h3>默认的规则:</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>连接符使用{@link ToStringConfig#DEFAULT_CONNECTOR}</li>
 * <li>拼接null或者empty元素</li>
 * <li>如果元素是null,使用{@link StringUtils#EMPTY}替代拼接</li>
 * <li>最后一个元素后面不拼接拼接符</li>
 * </ol>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see org.apache.commons.lang3.builder.ToStringStyle
 * @see org.apache.commons.lang3.StringUtils#join(Object[], String)
 * @since 1.4.0
 */
public final class ToStringConfig implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long  serialVersionUID  = 3182446945343865398L;

    /**
     * 默认逗号连接 <code>{@value}</code>.
     * 
     * @since 1.0.6
     */
    public static final String DEFAULT_CONNECTOR = ",";

    //**********************************************************************************************

    /** 连接符,默认={@link #DEFAULT_CONNECTOR}. */
    private String             connector         = DEFAULT_CONNECTOR;

    /**
     * 是否拼接 null或者empty对象.
     * 
     * @since 1.2.1
     */
    private boolean            isJoinNullOrEmpty = true;

    //**********************************************************************************************

    /**
     * <h3>默认的规则:</h3>
     * 
     * <blockquote>.
     * <ol>
     * <li>连接符使用{@link ToStringConfig#DEFAULT_CONNECTOR}</li>
     * <li>拼接null或者empty元素</li>
     * <li>如果元素是null,使用{@link StringUtils#EMPTY}替代拼接</li>
     * <li>最后一个元素后面不拼接拼接符</li>
     * </ol>
     * </blockquote>.
     */
    public ToStringConfig(){
    }

    /**
     * Instantiates a new to string config.
     *
     * @param connector
     *            the connector
     */
    public ToStringConfig(String connector){
        this.connector = connector;
    }

    /**
     * Instantiates a new to string config.
     *
     * @param connector
     *            the connector
     * @param isJoinNullOrEmpty
     *            the is join null或者empty
     * @since 1.4.0
     */
    public ToStringConfig(String connector, boolean isJoinNullOrEmpty){
        this.connector = connector;
        this.isJoinNullOrEmpty = isJoinNullOrEmpty;
    }

    /**
     * 获得 连接符,默认={@link #DEFAULT_CONNECTOR}.
     * 
     * @return the connector
     */
    public String getConnector(){
        return connector;
    }

    /**
     * 设置 连接符,默认={@link #DEFAULT_CONNECTOR}.
     * 
     * @param connector
     *            the connector to set
     */
    public void setConnector(String connector){
        this.connector = connector;
    }

    /**
     * 获得 是否拼接 null或者empty对象.
     *
     * @return the isJoinNullOrEmpty
     * @since 1.2.1
     */
    public boolean getIsJoinNullOrEmpty(){
        return isJoinNullOrEmpty;
    }

    /**
     * 设置 是否拼接 null或者empty对象.
     *
     * @param isJoinNullOrEmpty
     *            the isJoinNullOrEmpty to set
     * @since 1.2.1
     */
    public void setIsJoinNullOrEmpty(boolean isJoinNullOrEmpty){
        this.isJoinNullOrEmpty = isJoinNullOrEmpty;
    }
}