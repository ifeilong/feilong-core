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
import java.util.Map;

/**
 * The Class Member.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014年6月24日 下午1:22:39
 * @since 1.0.7
 */
public class Member implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long   serialVersionUID = 288232184048495608L;

    /** The id. */
    private Long                id;

    /** The code. */
    private String              code;

    /** The login name. */
    private String              loginName;

    /** The member addresses. */
    private MemberAddress[]     memberAddresses;

    /** The love map. */
    private Map<String, String> loveMap;

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
     * Gets the member addresses.
     * 
     * @return the memberAddresses
     */
    public MemberAddress[] getMemberAddresses(){
        return memberAddresses;
    }

    /**
     * Sets the member addresses.
     * 
     * @param memberAddresses
     *            the memberAddresses to set
     */
    public void setMemberAddresses(MemberAddress[] memberAddresses){
        this.memberAddresses = memberAddresses;
    }

    /**
     * Gets the login name.
     * 
     * @return the loginName
     */
    public String getLoginName(){
        return loginName;
    }

    /**
     * Sets the login name.
     * 
     * @param loginName
     *            the loginName to set
     */
    public void setLoginName(String loginName){
        this.loginName = loginName;
    }

    /**
     * Gets the love map.
     * 
     * @return the loveMap
     */
    public Map<String, String> getLoveMap(){
        return loveMap;
    }

    /**
     * 设置 love map.
     * 
     * @param loveMap
     *            the loveMap to set
     */
    public void setLoveMap(Map<String, String> loveMap){
        this.loveMap = loveMap;
    }

}
