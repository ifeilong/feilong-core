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
import java.util.Date;

/**
 * The Class MemberAddress.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014年6月24日 下午1:36:29
 * @since 1.0.7
 */
public class MemberAddress implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 288232184048495608L;

    /** The id. */
    private Long              id;

    /** The address. */
    private String            address;

    /** The member id. */
    private Long              memberId;

    /** The add time. */
    private Date              addTime;

    /**
     * 获得 id.
     *
     * @return the id
     */
    public Long getId(){
        return id;
    }

    /**
     * 设置 id.
     *
     * @param id
     *            the id to set
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * 获得 address.
     *
     * @return the address
     */
    public String getAddress(){
        return address;
    }

    /**
     * 设置 address.
     *
     * @param address
     *            the address to set
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * 获得 member id.
     *
     * @return the memberId
     */
    public Long getMemberId(){
        return memberId;
    }

    /**
     * 设置 member id.
     *
     * @param memberId
     *            the memberId to set
     */
    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    /**
     * 获得 add time.
     *
     * @return the addTime
     */
    public Date getAddTime(){
        return addTime;
    }

    /**
     * 设置 add time.
     *
     * @param addTime
     *            the addTime to set
     */
    public void setAddTime(Date addTime){
        this.addTime = addTime;
    }
}
