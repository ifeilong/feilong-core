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
package com.feilong.core.bean.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The Class SalesOrderDto.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014年6月24日 上午11:28:42
 * @since 1.0.7
 */
public class SalesOrderDto implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 288232184048495608L;

    /** The id. */
    private Long              id;

    /** The code. */
    private String            code;

    /** The price. */
    private BigDecimal        price;

    /** The create time. */
    private Date              createTime;

    /** The member. */
    private Member            member;

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public Long getId(){
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            the id to set
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public String getCode(){
        return code;
    }

    /**
     * Sets the code.
     * 
     * @param code
     *            the code to set
     */
    public void setCode(String code){
        this.code = code;
    }

    /**
     * Gets the price.
     * 
     * @return the price
     */
    public BigDecimal getPrice(){
        return price;
    }

    /**
     * Sets the price.
     * 
     * @param price
     *            the price to set
     */
    public void setPrice(BigDecimal price){
        this.price = price;
    }

    /**
     * Gets the creates the time.
     * 
     * @return the createTime
     */
    public Date getCreateTime(){
        return createTime;
    }

    /**
     * Sets the creates the time.
     * 
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
     * Gets the member.
     * 
     * @return the member
     */
    public Member getMember(){
        return member;
    }

    /**
     * Sets the member.
     * 
     * @param member
     *            the member to set
     */
    public void setMember(Member member){
        this.member = member;
    }
}
