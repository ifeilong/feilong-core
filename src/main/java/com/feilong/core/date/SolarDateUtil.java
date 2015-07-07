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
package com.feilong.core.date;

import java.util.Date;

/**
 * 阳历(公历)日期.
 * 
 * @author feilong
 * @version 1.0 2010-2-8 下午04:59:48
 * @see LunarDateUtil
 * @since 1.0.0
 */
public final class SolarDateUtil{

    /** Don't let anyone instantiate this class. */
    private SolarDateUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 阳历日期转成农历.<br>
     * 如 SolarDateUtil.toLundar(2013, 1, 8) 返回20121126
     * 
     * @param date
     *            the date
     * @return the string
     */
    public static String toLundar(Date date){
        int year = DateUtil.getYear(date);
        int month = DateUtil.getMonth(date);
        int day = DateUtil.getDayOfMonth(date);
        return toLundar(year, month, day);
    }

    /**
     * 阳历日期转成农历.<br>
     * 如 SolarDateUtil.toLundar(2013, 1, 8) 返回20121126
     * 
     * @param year
     *            阳历年
     * @param month
     *            阳历月
     * @param day
     *            阳历日
     * @return 农历时间
     */
    public static String toLundar(int year,int month,int day){
        int iLDay;
        int iLMonth;
        int iLYear;
        int iOffsetDays = CalendarUtil.getDayOfYear(year, month, day);
        int iLeapMonth = LunarDateUtil.getLeapMonth(year);
        if (iOffsetDays < DateDictionary.SOLAR_AND_LUNAR_OFFSET_TABLE[year - 1901]){
            iLYear = year - 1;
            iOffsetDays = DateDictionary.SOLAR_AND_LUNAR_OFFSET_TABLE[year - 1901] - iOffsetDays;
            iLDay = iOffsetDays;
            for (iLMonth = 12; iOffsetDays > LunarDateUtil.getLunarMonthMaxDays(iLYear, iLMonth); --iLMonth){
                iLDay = iOffsetDays;
                iOffsetDays -= LunarDateUtil.getLunarMonthMaxDays(iLYear, iLMonth);
            }
            if (0 == iLDay){
                iLDay = 1;
            }else{
                iLDay = LunarDateUtil.getLunarMonthMaxDays(iLYear, iLMonth) - iOffsetDays + 1;
            }
        }else{
            iLYear = year;
            iOffsetDays -= DateDictionary.SOLAR_AND_LUNAR_OFFSET_TABLE[year - 1901];
            iLDay = iOffsetDays + 1;
            for (iLMonth = 1; iOffsetDays >= 0; ++iLMonth){
                iLDay = iOffsetDays + 1;
                iOffsetDays -= LunarDateUtil.getLunarMonthMaxDays(iLYear, iLMonth);
                if ((iLeapMonth == iLMonth) && (iOffsetDays > 0)){
                    iLDay = iOffsetDays;
                    iOffsetDays -= LunarDateUtil.getLunarMonthMaxDays(iLYear, iLMonth + 12);
                    if (iOffsetDays <= 0){
                        iLMonth += 12 + 1;
                        break;
                    }
                }
            }
            iLMonth--;
        }
        return "" + iLYear + //
                        (iLMonth > 9 ? "" + iLMonth : "0" + iLMonth) + //
                        (iLDay > 9 ? "" + iLDay : "0" + iLDay);
    }
}