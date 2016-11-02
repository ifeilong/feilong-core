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
package com.feilong.tools.jsonlib;

/**
 * The Class CrmMemberInfoCommand.
 *
 */
public class CrmMemberInfoCommand{

    /** 会员编号. */
    private String memberNo;

    /** 姓名/昵称. */
    private String name;

    /** 电话号码. */
    private String phone;

    /** 性别：男；女. */
    private String gender;

    /** 生日：Format：yyyy-MM-dd. */
    private String birthday;

    /** 邮箱. */
    private String email;

    /**
     * 获得 会员编号.
     *
     * @return the memberNo
     */
    public String getMemberNo(){
        return memberNo;
    }

    /**
     * 设置 会员编号.
     *
     * @param memberNo
     *            the memberNo to set
     */
    public void setMemberNo(String memberNo){
        this.memberNo = memberNo;
    }

    /**
     * 获得 姓名/昵称.
     *
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * 设置 姓名/昵称.
     *
     * @param name
     *            the name to set
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * 获得 电话号码.
     *
     * @return the phone
     */
    public String getPhone(){
        return phone;
    }

    /**
     * 设置 电话号码.
     *
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone){
        this.phone = phone;
    }

    /**
     * 获得 性别：男；女.
     *
     * @return the gender
     */
    public String getGender(){
        return gender;
    }

    /**
     * 设置 性别：男；女.
     *
     * @param gender
     *            the gender to set
     */
    public void setGender(String gender){
        this.gender = gender;
    }

    /**
     * 获得 生日：Format：yyyy-MM-dd.
     *
     * @return the birthday
     */
    public String getBirthday(){
        return birthday;
    }

    /**
     * 设置 生日：Format：yyyy-MM-dd.
     *
     * @param birthday
     *            the birthday to set
     */
    public void setBirthday(String birthday){
        this.birthday = birthday;
    }

    /**
     * 获得 邮箱.
     *
     * @return the email
     */
    public String getEmail(){
        return email;
    }

    /**
     * 设置 邮箱.
     *
     * @param email
     *            the email to set
     */
    public void setEmail(String email){
        this.email = email;
    }

}