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
 * 日期字典(focus on date config).
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Jan 9, 2013 12:01:50 AM
 * @version 1.0.5 2014-5-4 14:23 change to interface
 * @since 1.0.0
 */
//默认作用域 --->当前包
final class DateDictionary{

    /** 昨天. */
    public static final String   YESTERDAY                    = "昨天";

    /** 前天. */
    public static final String   THEDAY_BEFORE_YESTERDAY      = "前天";

    /** 星期. */
    public static final String   WEEK                         = "星期";

    /** 天. */
    public static final String   DAY                          = "天";

    /** 小时. */
    public static final String   HOUR                         = "小时";

    /** 分钟. */
    public static final String   MINUTE                       = "分钟";

    /** 秒. */
    public static final String   SECOND                       = "秒";

    /** 毫秒. */
    public static final String   MILLISECOND                  = "毫秒";

    /**
     * 英文星期.<br>
     * { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }
     */
    public static final String[] WEEK_ENGLISHS                = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"                                       };

    /**
     * 中文星期.<br>
     * { "日", "一", "二", "三", "四", "五", "六" }
     */
    public static final String[] WEEK_CHINESES                = { "日", "一", "二", "三", "四", "五", "六" };

    /**
     * 生肖.<br>
     * ["鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"]
     */
    public static final String[] ZODIACS                      = { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };

    /**
     * 天干.<br>
     * ["甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"]
     */
    public static final String[] HEAVENLY_STEMS               = { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };

    /**
     * 地支.<br>
     * ["子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"]
     */
    public static final String[] EARTHLY_BRANCHES             = { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };

    /**
     * 中文数字.<br>
     * ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"]
     */
    public static final String[] CHINSES_NUMBERS              = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };

    /**
     * 1901-2050每年阴历和农历相差量.
     * 
     * <pre>
     * Array iSolarLunarTable stored the offset days
     * in New Year of solar calendar and lunar calendar from 1901 to 2050;
     * </pre>
     */
    //TODO
    //Public arrays, even ones declared static final can have their contents edited by malicious programs. 
    //The final keyword on an array declaration means that the array object itself may only be assigned once, but its contents are still mutable. Therefore making arrays public is a security risk.
    //Instead, arrays should be private and accessed through methods.
    public static final char[]   SOLAR_AND_LUNAR_OFFSET_TABLE = { //
                                                              49, //
            38,
            28,
            46,
            34,
            24,
            43,
            32,
            21,
            40, // 1910
            29,
            48,
            36,
            25,
            44,
            34,
            22,
            41,
            31,
            50, // 1920
            38,
            27,
            46,
            35,
            23,
            43,
            32,
            22,
            40,
            29, // 1930
            47,
            36,
            25,
            44,
            34,
            23,
            41,
            30,
            49,
            38, // 1940
            26,
            45,
            35,
            24,
            43,
            32,
            21,
            40,
            28,
            47, // 1950
            36,
            26,
            44,
            33,
            23,
            42,
            30,
            48,
            38,
            27, // 1960
            45,
            35,
            24,
            43,
            32,
            20,
            39,
            29,
            47,
            36, // 1970
            26,
            45,
            33,
            22,
            41,
            30,
            48,
            37,
            27,
            46, // 1980
            35,
            24,
            43,
            32,
            50,
            39,
            28,
            47,
            36,
            26, // 1990
            45,
            34,
            22,
            40,
            30,
            49,
            37,
            27,
            46,
            35, // 2000
            23,
            42,
            31,
            21,
            39,
            28,
            48,
            37,
            25,
            44, // 2010
            33,
            23,
            41,
            31,
            50,
            39,
            28,
            47,
            35,
            24, // 2020
            42,
            30,
            21,
            40,
            28,
            47,
            36,
            25,
            43,
            33, // 2030
            22,
            41,
            30,
            49,
            37,
            26,
            44,
            33,
            23,
            42, // 2040
            31,
            21,
            40,
            29,
            47,
            36,
            25,
            44,
            32,
            22, // 2050
                                                              };

    /**
     * 农历闰月表 1901-2050.
     * 
     * <pre>
     *   Array iLunarLeapMonthTable preserves the lunar calendar leap month from 1901 to 2050,
     * 	  if it is 0 express not to have , every byte was stored for two years
     * </pre>
     */
    public static final char[]   LUNAR_LEAP_MONTH_TABLE       = { 0x00, 0x50, 0x04, 0x00, 0x20, // 1910
            0x60,
            0x05,
            0x00,
            0x20,
            0x70, // 1920
            0x05,
            0x00,
            0x40,
            0x02,
            0x06, // 1930
            0x00,
            0x50,
            0x03,
            0x07,
            0x00, // 1940
            0x60,
            0x04,
            0x00,
            0x20,
            0x70, // 1950
            0x05,
            0x00,
            0x30,
            0x80,
            0x06, // 1960
            0x00,
            0x40,
            0x03,
            0x07,
            0x00, // 1970
            0x50,
            0x04,
            0x08,
            0x00,
            0x60, // 1980
            0x04,
            0x0a,
            0x00,
            0x60,
            0x05, // 1990
            0x00,
            0x30,
            0x80,
            0x05,
            0x00, // 2000
            0x40,
            0x02,
            0x07,
            0x00,
            0x50, // 2010
            0x04,
            0x09,
            0x00,
            0x60,
            0x04, // 2020
            0x00,
            0x20,
            0x60,
            0x05,
            0x00, // 2030
            0x30,
            0xb0,
            0x06,
            0x00,
            0x50, // 2040
            0x02,
            0x07,
            0x00,
            0x50,
            0x03                                             // 2050
                                                              };

    /**
     * 农历日期表 1901-2100农历表.
     * 
     * <pre>
     * Array lIntLunarDay is stored in the monthly day information in every year from 1901 to 2100 of the lunar calendar,
     * 农历只能每个月29天或者30天,1年中有12或者13个月
     * The lunar calendar can only be 29 or 30 days every month, express with 12(or 13) pieces of binary bit in one year,
     * it is 30 days for 1 form in the corresponding location , otherwise it is 29 days
     * </pre>
     */
    public static final int[]    LUNAR_MONTH_DAYS_TABLE       = {
            0x4ae0,
            0xa570,
            0x5268,
            0xd260,
            0xd950,
            0x6aa8,
            0x56a0,
            0x9ad0,
            0x4ae8,
            0x4ae0, // 1910
            0xa4d8,
            0xa4d0,
            0xd250,
            0xd548,
            0xb550,
            0x56a0,
            0x96d0,
            0x95b0,
            0x49b8,
            0x49b0, // 1920
            0xa4b0,
            0xb258,
            0x6a50,
            0x6d40,
            0xada8,
            0x2b60,
            0x9570,
            0x4978,
            0x4970,
            0x64b0, // 1930
            0xd4a0,
            0xea50,
            0x6d48,
            0x5ad0,
            0x2b60,
            0x9370,
            0x92e0,
            0xc968,
            0xc950,
            0xd4a0, // 1940
            0xda50,
            0xb550,
            0x56a0,
            0xaad8,
            0x25d0,
            0x92d0,
            0xc958,
            0xa950,
            0xb4a8,
            0x6ca0, // 1950
            0xb550,
            0x55a8,
            0x4da0,
            0xa5b0,
            0x52b8,
            0x52b0,
            0xa950,
            0xe950,
            0x6aa0,
            0xad50, // 1960
            0xab50,
            0x4b60,
            0xa570,
            0xa570,
            0x5260,
            0xe930,
            0xd950,
            0x5aa8,
            0x56a0,
            0x96d0, // 1970
            0x4ae8,
            0x4ad0,
            0xa4d0,
            0xd268,
            0xd250,
            0xd528,
            0xb540,
            0xb6a0,
            0x96d0,
            0x95b0, // 1980
            0x49b0,
            0xa4b8,
            0xa4b0,
            0xb258,
            0x6a50,
            0x6d40,
            0xada0,
            0xab60,
            0x9370,
            0x4978, // 1990
            0x4970,
            0x64b0,
            0x6a50,
            0xea50,
            0x6b28,
            0x5ac0,
            0xab60,
            0x9368,
            0x92e0,
            0xc960, // 2000
            0xd4a8,
            0xd4a0,
            0xda50,
            0x5aa8,
            0x56a0,
            0xaad8,
            0x25d0,
            0x92d0,
            0xc958,
            0xa950, // 2010
            0xb4a0,
            0xb550,
            0xb550,
            0x55a8,
            0x4ba0,
            0xa5b0,
            0x52b8,
            0x52b0,
            0xa930,
            0x74a8, // 2020
            0x6aa0,
            0xad50,
            0x4da8,
            0x4b60,
            0x9570,
            0xa4e0,
            0xd260,
            0xe930,
            0xd530,
            0x5aa0, // 2030
            0x6b50,
            0x96d0,
            0x4ae8,
            0x4ad0,
            0xa4d0,
            0xd258,
            0xd250,
            0xd520,
            0xdaa0,
            0xb5a0, // 2040
            0x56d0,
            0x4ad8,
            0x49b0,
            0xa4b8,
            0xa4b0,
            0xaa50,
            0xb528,
            0x6d20,
            0xada0,
            0x55b0                                           // 2050
                                                              };

    /** Don't let anyone instantiate this class. */
    private DateDictionary(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
