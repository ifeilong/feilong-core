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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 将 {@link ConvertUtil#toString(java.util.Collection, ToStringConfig) 集合转成字符串},将{@link ConvertUtil#toString(Object[], ToStringConfig)
 * 数组转成字符串} 的参数配置.
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
    private static final long          serialVersionUID            = 3182446945343865398L;

    //---------------------------------------------------------------

    /**
     * 默认逗号连接 <code>{@value}</code>.
     * 
     * @since 1.0.6
     */
    public static final String         DEFAULT_CONNECTOR           = ",";

    //---------------------------------------------------------------

    /**
     * 默认的转换参数.
     * 
     * <h3>默认的规则:</h3>
     * 
     * <blockquote>.
     * <ol>
     * <li>连接符使用{@link ToStringConfig#DEFAULT_CONNECTOR}</li>
     * <li>拼接null或者empty元素</li>
     * <li>如果元素是null,使用{@link StringUtils#EMPTY}替代拼接</li>
     * <li>最后一个元素后面不拼接拼接符</li>
     * </ol>
     * </blockquote>
     *
     * <h3>示例:</h3>
     * 
     * <p>
     * 下面是调用 {@link ConvertUtil#toString(java.util.Collection, ToStringConfig) }或者 {@link ConvertUtil#toString(Object[], ToStringConfig)}的示例
     * </p>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toString(toList("feilong", "xinge"), DEFAULT_CONFIG) = "feilong,xinge";
     * ConvertUtil.toString(toList("feilong", "", "xinge", null), DEFAULT_CONFIG) = "feilong,,xinge,";
     * </pre>
     * 
     * </blockquote>
     * 
     * @see org.apache.commons.lang3.builder.ToStringStyle#DEFAULT_STYLE
     * 
     * @since 1.10.3
     */
    public static final ToStringConfig DEFAULT_CONFIG              = new ToStringConfig();

    /**
     * 使用 {@link ToStringConfig#DEFAULT_CONNECTOR} 但是<span style="color:red">忽视 null 或者 empty 元素</span>进行拼接的参数.
     * 
     * <h3>默认的规则:</h3>
     * 
     * <blockquote>.
     * <ol>
     * <li>连接符使用{@link ToStringConfig#DEFAULT_CONNECTOR}</li>
     * <li><span style="color:red">不拼接</span>null或者empty元素</li>
     * <li>最后一个元素后面不拼接拼接符</li>
     * </ol>
     * </blockquote>
     *
     * <h3>示例:</h3>
     * 
     * <p>
     * 下面是调用 {@link ConvertUtil#toString(java.util.Collection, ToStringConfig) }或者 {@link ConvertUtil#toString(Object[], ToStringConfig)}的示例
     * </p>
     * 
     * <blockquote>
     * 
     * <pre class="code">
     * ConvertUtil.toString(toList("feilong", "xinge"), IGNORE_NULL_OR_EMPTY_CONFIG) = "feilong,xinge";
     * ConvertUtil.toString(toList("feilong", "", "xinge", null), IGNORE_NULL_OR_EMPTY_CONFIG) = "feilong,xinge";
     * </pre>
     * 
     * </blockquote>
     * 
     * @see org.apache.commons.lang3.builder.ToStringStyle#DEFAULT_STYLE
     * @since 1.10.3
     */
    public static final ToStringConfig IGNORE_NULL_OR_EMPTY_CONFIG = new ToStringConfig(DEFAULT_CONNECTOR, false);

    //---------------------------------------------------------------

    /** 连接符,默认={@link #DEFAULT_CONNECTOR}. */
    private String                     connector                   = DEFAULT_CONNECTOR;

    /**
     * 是否拼接null或者empty的元素,如果是true ,表示拼接,如果是false,那么拼接的时候跳过这个元素.
     * 
     * @since 1.2.1
     */
    private boolean                    isJoinNullOrEmpty           = true;

    //---------------------------------------------------------------

    /**
     * 循环拼接元素的时候,支持给每个元素拼接一个前缀.
     * 
     * @since 1.12.9
     */
    private String                     prefix;

    //---------------------------------------------------------------

    /**
     * 默认的构造函数.
     * 
     * <h3>默认的规则:</h3>
     * 
     * <blockquote>.
     * <ol>
     * <li>连接符使用{@link ToStringConfig#DEFAULT_CONNECTOR}</li>
     * <li>拼接null或者empty元素</li>
     * <li>如果元素是null,使用{@link StringUtils#EMPTY}替代拼接</li>
     * <li>最后一个元素后面不拼接拼接符</li>
     * </ol>
     * </blockquote>
     */
    public ToStringConfig(){
        // default
    }

    /**
     * Instantiates a new to string config.
     *
     * @param connector
     *            连接符
     */
    public ToStringConfig(String connector){
        this.connector = connector;
    }

    /**
     * Instantiates a new to string config.
     *
     * @param connector
     *            连接符
     * @param isJoinNullOrEmpty
     *            是否拼接null或者empty的元素,如果是true ,表示拼接,如果是false,那么拼接的时候跳过这个元素
     * @since 1.4.0
     */
    public ToStringConfig(String connector, boolean isJoinNullOrEmpty){
        this.connector = connector;
        this.isJoinNullOrEmpty = isJoinNullOrEmpty;
    }

    /**
     * Instantiates a new to string config.
     *
     * @param connector
     *            the connector
     * @param isJoinNullOrEmpty
     *            the is join null or empty
     * @param prefix
     *            the prefix
     * @since 1.12.9
     */
    public ToStringConfig(String connector, boolean isJoinNullOrEmpty, String prefix){
        super();
        this.connector = connector;
        this.isJoinNullOrEmpty = isJoinNullOrEmpty;
        this.prefix = prefix;
    }

    //----------------------------------------------------------------------------------------

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
     * 是否拼接null或者empty的元素,如果是true ,表示拼接,如果是false,那么拼接的时候跳过这个元素.
     *
     * @return the isJoinNullOrEmpty
     * @since 1.2.1
     */
    public boolean getIsJoinNullOrEmpty(){
        return isJoinNullOrEmpty;
    }

    /**
     * 是否拼接null或者empty的元素,如果是true ,表示拼接,如果是false,那么拼接的时候跳过这个元素.
     *
     * @param isJoinNullOrEmpty
     *            the isJoinNullOrEmpty to set
     * @since 1.2.1
     */
    public void setIsJoinNullOrEmpty(boolean isJoinNullOrEmpty){
        this.isJoinNullOrEmpty = isJoinNullOrEmpty;
    }

    /**
     * 获得 循环拼接元素的时候,支持给每个元素拼接一个前缀.
     *
     * @return the prefix
     * @since 1.12.9
     */
    public String getPrefix(){
        return prefix;
    }

    /**
     * 设置 循环拼接元素的时候,支持给每个元素拼接一个前缀.
     *
     * @param prefix
     *            the prefix to set
     * @since 1.12.9
     */
    public void setPrefix(String prefix){
        this.prefix = prefix;
    }

    //---------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}