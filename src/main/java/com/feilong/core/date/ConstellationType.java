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

/**
 * 星座枚举,占星学,黄道12星座是宇宙方位的代名词，代表了12种基本性格原型，
 * <p>
 * 一个人出生时，各星体落入黄道上的位置，正是说明著一个人的先天性格及天赋. <br>
 * 黄道12星座象征心理层面，反映出一个人行为的表现的方式. <br>
 * <br>
 * 于是将黄道分成12个星座，称为黄道12星座.<br>
 * <ul>
 * <li>摩羯座 12月22日-1月19日</li>
 * <li>水瓶座 1月20日-2月18日</li>
 * <li>双鱼座 2月19日-3月20日</li>
 * <li>白羊座 3月21日-4月19日</li>
 * <li>金牛座 4月20日-5月20日</li>
 * <li>双子座 5月21日-6月21日</li>
 * <li>巨蟹座 6月22日-7月22日</li>
 * <li>狮子座 7月23日-8月22日</li>
 * <li>处女座 8月23日-9月22日</li>
 * <li>天秤座 9月23日-10月23日</li>
 * <li>天蝎座 10月24日-11月22日</li>
 * <li>射手座 11月23日-12月21日</li>
 * </ul>.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-1-5 上午11:16:41
 * @since 1.0.0
 */
public enum ConstellationType{

    /** 摩羯座 12月22日-1月19日. */
    CAPRICORN(1,"摩羯座","12月22日","1月19日"),

    /** 水瓶座 1月20日-2月18日. */
    AQUARIUS(2,"水瓶座","1月20日","2月18日"),

    /** 双鱼座 2月19日-3月20日. */
    PISCES(3,"双鱼座"," 2月19日","3月20日"),

    /** 白羊座 3月21日-4月19日. */
    ARIES(4,"白羊座","3月21日","4月19日"),

    /** 金牛座 4月20日-5月20日. */
    TAURUS(5,"金牛座","4月20日","5月20日"),

    /** 双子座 5月21日-6月21日. */
    GEMINI(6,"双子座"," 5月21日","6月21日"),

    /** 巨蟹座 6月22日-7月22日. */
    CANCER(7,"巨蟹座","6月22日","7月22日"),

    /** 狮子座 7月23日-8月22日. */
    LEO(8,"狮子座","7月23日","8月22日"),

    /** 处女座 8月23日-9月22日. */
    VIRGO(9,"处女座"," 8月23日","9月22日"),

    /** 天秤座 9月23日-10月23日. */
    LIBRA(10,"天秤座","9月23日","10月23日"),

    /** 天蝎座 10月24日-11月22日. */
    SCORPIO(11,"天蝎座","10月24日","11月22日"),

    /** 射手座 11月23日-12月21日. */
    SAGITTARIUS(12,"射手座","11月23日","12月21日");

    // *****************************************************************************************
    /** The code. */
    private int    code;

    /** The chinese name. */
    private String chineseName;

    /** The begin date string. */
    private String beginDateString;

    /** The end date string. */
    private String endDateString;

    // *****************************************************************************************
    /**
     * Instantiates a new constellation type.
     * 
     * @param code
     *            the code
     * @param chineseName
     *            the chinese name
     * @param beginDateString
     *            the begin date string
     * @param endDateString
     *            the end date string
     */
    private ConstellationType(int code, String chineseName, String beginDateString, String endDateString){
        this.code = code;
        this.chineseName = chineseName;
        this.beginDateString = beginDateString;
        this.endDateString = endDateString;
    }

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public int getCode(){
        return this.code;
    }

    /**
     * Gets the chinese name.
     * 
     * @return the chinese name
     */
    public String getChineseName(){
        return this.chineseName;
    }

    /**
     * Gets the begin date string.
     * 
     * @return the begin date string
     */
    public String getBeginDateString(){
        return beginDateString;
    }

    /**
     * Gets the end date string.
     * 
     * @return the end date string
     */
    public String getEndDateString(){
        return endDateString;
    }
}
