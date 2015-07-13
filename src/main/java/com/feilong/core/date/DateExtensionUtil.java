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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.feilong.core.util.Validator;

/**
 * 日期扩展工具类.
 * <p>
 * 和 DateUtil 的区别在于,DateUtil是纯 操作Date API的工具类,而DateExtensionUtil类 用于个性化 输出结果,针对业务个性化显示.
 * </p>
 * 
 * <h3>获得两个日期间隔</h3>
 * 
 * <blockquote>
 * <ul>
 * <li>{@link #getIntervalDayList(String, String, String)}</li>
 * <li>{@link #getIntervalForView(long)}</li>
 * <li>{@link #getIntervalForView(Date, Date)}</li>
 * </ul>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.8 2014年7月31日 下午2:34:33
 * @since 1.0.8
 */
public final class DateExtensionUtil{

    /** 昨天. */
    public static final String    YESTERDAY               = "昨天";

    /** 前天. */
    public static final String    THEDAY_BEFORE_YESTERDAY = "前天";

    /** 星期. */
    public static final String    WEEK                    = "星期";

    /** 天. */
    public static final String    DAY                     = "天";

    /** 小时. */
    public static final String    HOUR                    = "小时";

    /** 分钟. */
    public static final String    MINUTE                  = "分钟";

    /** 秒. */
    public static final String    SECOND                  = "秒";

    /** 毫秒. */
    public static final String    MILLISECOND             = "毫秒";

    /**
     * 中文星期.<br>
     * { "日", "一", "二", "三", "四", "五", "六" }
     */
    private static final String[] WEEK_CHINESES           = { "日", "一", "二", "三", "四", "五", "六" };

    /**
     * 英文星期.<br>
     * { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }
     */
    private static final String[] WEEK_ENGLISHS           = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

    /** Don't let anyone instantiate this class. */
    private DateExtensionUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获得英文星期.
     * 
     * @param week
     *            星期 日从0开始 1 2 --6
     * @return 如 Sunday { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" } 中一个
     */
    public static String getEnglishWeek(int week){
        return WEEK_ENGLISHS[week];
    }

    /**
     * 获得中文星期.
     * 
     * @param week
     *            星期 日从0开始 1 2 --6
     * @return 如 星期一
     */
    public static String getChineseWeek(int week){
        return WEEK + WEEK_CHINESES[week];
    }

    // [start]extent 获得时间 /时间数组,可以用于sql查询
    /**
     * 获得当天0:00:00及下一天0:00:00,一般用于统计当天数据,between ... and ...
     * 
     * <pre>
     * 比如今天是 2012-10-16 22:18:34
     * 
     * return {2012-10-16 00:00:00.000,2012-10-17 00:00:00.000}
     * </pre>
     * 
     * @return Date数组 第一个为today 第二个为tomorrow
     * @since 1.0
     * @deprecated 方法名在未来版本可能会更新
     */
    @Deprecated
    public static final Date[] getExtentToday(){
        Calendar calendar = CalendarUtil.getResetCalendarByDay(new Date());
        Date today = calendar.getTime();
        // ***************************
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        Date[] dates = { today, tomorrow };
        return dates;
    }

    /**
     * 获得昨天的区间 [yestoday,today]<br>
     * 第一个为昨天00:00 <br>
     * 第二个为今天00:00 <br>
     * 一般用于sql/hql统计昨天数据,between ... and ...
     * 
     * <pre>
     * 比如现在 :2012-10-16 22:46:42
     * 
     * return  {2012-10-15 00:00:00.000,2012-10-16 00:00:00.000}
     * </pre>
     * 
     * @return Date数组 <br>
     *         第一个为昨天00:00 <br>
     *         第二个为今天00:00
     * @since 1.0
     * @deprecated 方法名会更新
     */
    @Deprecated
    public static final Date[] getExtentYesterday(){
        Calendar calendar = CalendarUtil.getResetCalendarByDay(new Date());
        Date today = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        Date[] dates = { yesterday, today };
        return dates;
    }

    // [end]
    /**
     * 获得两个日期时间的日期间隔时间集合(包含最小和最大值),用于统计日报表<br>
     * 每天的日期会被重置清零 <code>00:00:00.000</code>
     * 
     * <pre>
     * getIntervalDayList("2011-03-5 23:31:25.456","2011-03-10 01:30:24.895", DatePattern.commonWithTime)
     * 
     * return
     * 2011-03-05 00:00:00
     * 2011-03-06 00:00:00
     * 2011-03-07 00:00:00
     * 2011-03-08 00:00:00
     * 2011-03-09 00:00:00
     * 2011-03-10 00:00:00
     * 
     * </pre>
     * 
     * @param fromDateString
     *            开始时间
     * @param toDateString
     *            结束时间
     * @param datePattern
     *            时间模式 {@link DatePattern}
     * @return the interval day list
     * @see DateUtil#getIntervalDay(Date, Date)
     */
    public static final List<Date> getIntervalDayList(String fromDateString,String toDateString,String datePattern){
        List<Date> dateList = new ArrayList<Date>();
        //***************************************************************/
        Date beginDate = DateUtil.string2Date(fromDateString, datePattern);
        Date endDate = DateUtil.string2Date(toDateString, datePattern);
        // ******重置时间********
        Date beginDateReset = CalendarUtil.getResetDateByDay(beginDate);
        Date endDateReset = CalendarUtil.getResetDateByDay(endDate);
        //***************************************************************/
        // 相隔的天数
        int intervalDay = DateUtil.getIntervalDay(beginDateReset, endDateReset);
        //***************************************************************/
        Date minDate = beginDateReset;
        if (beginDateReset.equals(endDateReset)){
            minDate = beginDateReset;
        }else if (beginDateReset.before(endDateReset)){
            minDate = beginDateReset;
        }else{
            minDate = endDateReset;
        }
        //***************************************************************/
        dateList.add(minDate);
        //***************************************************************/
        if (intervalDay > 0){
            for (int i = 0; i < intervalDay; ++i){
                dateList.add(DateUtil.addDay(minDate, i + 1));
            }
        }
        //***************************************************************/
        return dateList;
    }

    /**
     * 获得一年中所有的周几集合 例如:getWeekDateStringList(6, "yyyy-MM-dd");.
     * 
     * @param week
     *            周几 星期天开始为0 依次1 2 3 4 5 6
     * @param datePattern
     *            获得集合里面时间字符串模式
     * @return 获得一年中所有的周几集合
     */
    public static List<String> getWeekDateStringList(int week,String datePattern){
        List<String> list = new ArrayList<String>();
        //当前日期
        Calendar calendarToday = Calendar.getInstance();
        //当前年份
        int yearCurrent = calendarToday.get(Calendar.YEAR);
        //下一个年份
        int yearNext = 1 + yearCurrent;
        //开始的calendar
        Calendar calendarBegin = Calendar.getInstance();
        //结束的calendar
        Calendar calendarEnd = Calendar.getInstance();
        calendarBegin.set(yearCurrent, 0, 1);// 2010-1-1
        calendarEnd.set(yearNext, 0, 1);// 2011-1-1
        // ****************************************************************************************
        // 今天在这个星期中的第几天 星期天为1 星期六为7
        int todayDayOfWeek = calendarToday.get(Calendar.DAY_OF_WEEK);

        // 今天前一个周六
        calendarToday.add(Calendar.DAY_OF_MONTH, -todayDayOfWeek - 7 + (1 + week));// + week
        Calendar calendarCloneToday = (Calendar) calendarToday.clone();

        // 向前
        for (; calendarToday.before(calendarEnd) && calendarToday.after(calendarBegin); calendarToday.add(Calendar.DAY_OF_YEAR, -7)){
            list.add(CalendarUtil.toString(calendarToday, datePattern));
        }

        // 向后
        for (int i = 0; calendarCloneToday.before(calendarEnd) && calendarCloneToday.after(calendarBegin); ++i, calendarCloneToday.add(
                        Calendar.DAY_OF_YEAR,
                        7)){
            //第一个值和上面循环重复了 去掉
            if (i != 0){
                list.add(CalendarUtil.toString(calendarCloneToday, datePattern));
            }
        }
        Collections.sort(list);
        return list;
    }

    // [start]转换成特色时间 toHumanizationDateString
    /**
     * 人性化显示date时间,依据是和现在的时间间隔
     * <p>
     * 转换规则,将传入的inDate和 new Date()当前时间比较<br>
     * 当两者的时间差,(一般inDate小于当前时间 ,暂时不支持大于当前时间)
     * <ul>
     * <li>如果时间差为0天,<br>
     * 如果小时间隔等于0,如果分钟间隔为0,则显示间隔秒 + "秒钟前"<br>
     * 如果小时间隔等于0,如果分钟间隔不为0,则显示间隔分钟 + "分钟前"<br>
     * </li>
     * <li>如果时间差为0天,<br>
     * 如果小时间隔不等于0,如果inDate的day 和current的day 相等,则显示space_hour + "小时前"<br>
     * 如果小时间隔不等于0,如果inDate的day 和current的day不相等,则显示"昨天 " + date2String(inDate, "HH:mm")<br>
     * </li>
     * <li>如果时间差为1天,且inDate的day+1和currentDate的day 相等,则显示"昨天 HH:mm"</li>
     * <li>如果时间差为1天,且inDate的day+1和currentDate的day 不相等,则显示"前天 HH:mm"</li>
     * <li>如果时间差为2天,且inDate的day+2和currentDate的day 相等,则显示"前天 HH:mm"</li>
     * <li>如果时间差为2天,且inDate的day+2和currentDate的day 不相等,<br>
     * 1).如果inDate的year和currentDate的year相等,则显示"MM-dd HH:mm"<br>
     * 2).如果inDate的year和currentDate的year不相等,则显示"yyyy-MM-dd HH:mm"</li>
     * <li>如果时间差大于2天<br>
     * 1).如果inDate的year和currentDate的year相等,则显示"MM-dd HH:mm"<br>
     * 2).如果inDate的year和currentDate的year不相等,则显示"yyyy-MM-dd HH:mm"</li>
     * </ul>
     * 
     * @param inDate
     *            任意日期<br>
     *            warn:{@code 一般inDate<=当前时间} ,暂时不支持大于当前时间
     * @return 人性化显示date时间
     * @see DateUtil#date2String(Date, String)
     * @see DateUtil#getYear(Date)
     * @see DateUtil#getDayOfMonth(Date)
     * @see DateUtil#getYear(Date)
     * @see DateUtil#getIntervalTime(Date, Date)
     * @see DateUtil#getIntervalDay(long)
     * @see DateUtil#getIntervalHour(long)
     * @see DateUtil#getIntervalMinute(long)
     * @see DateUtil#getIntervalSecond(long)
     * @deprecated 未来名称可能会更改
     */
    @Deprecated
    public static final String toHumanizationDateString(Date inDate){
        Date nowDate = new Date();

        //**************************************************************************************/
        String returnValue = null;
        // 传过来的日期的年份
        int inYear = DateUtil.getYear(inDate);
        // 传过来的日期的月份
        // int inMonth = getMonth(inDate);
        // 传过来的日期的日
        int inDay = DateUtil.getDayOfMonth(inDate);

        //**************************************************************************************/
        // 当前时间的年
        int year = DateUtil.getYear(nowDate);
        // 当前时间的余额
        // int month = getMonth();
        // 当前时间的日
        int day = DateUtil.getDayOfMonth(nowDate);

        //**************************************************************************************/
        // 任意日期和现在相差的毫秒数
        long spaceTime = DateUtil.getIntervalTime(inDate, nowDate);
        // 相差天数
        int spaceDay = DateUtil.getIntervalDay(spaceTime);
        // 相差小时数
        int spaceHour = DateUtil.getIntervalHour(spaceTime);
        // 相差分数
        int spaceMinute = DateUtil.getIntervalMinute(spaceTime);
        // 相差秒数
        int spaceSecond = DateUtil.getIntervalSecond(spaceTime);
        //**************************************************************************************/
        // 间隔一天
        if (spaceDay == 1){
            if (DateUtil.isEquals(DateUtil.addDay(inDate, 1), nowDate, DatePattern.COMMON_DATE)){
                returnValue = YESTERDAY + " ";
            }else{
                returnValue = THEDAY_BEFORE_YESTERDAY + " ";
            }
            returnValue += DateUtil.date2String(inDate, DatePattern.COMMON_TIME_WITHOUT_SECOND);
        }
        // 间隔2天
        else if (spaceDay == 2){
            if (DateUtil.isEquals(DateUtil.addDay(inDate, 2), nowDate, DatePattern.COMMON_DATE)){
                returnValue = THEDAY_BEFORE_YESTERDAY + " " + DateUtil.date2String(inDate, DatePattern.COMMON_TIME_WITHOUT_SECOND);
            }else{
                // 今年
                if (year == inYear){
                    returnValue = DateUtil.date2String(inDate, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND);
                }else{
                    returnValue = DateUtil.date2String(inDate, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND);
                }
            }
        }
        // 间隔大于2天
        else if (spaceDay > 2){
            // 今年
            if (year == inYear){
                returnValue = DateUtil.date2String(inDate, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND);
            }else{
                returnValue = DateUtil.date2String(inDate, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND);
            }
        }
        // 间隔0天
        else if (spaceDay == 0){
            // 小时间隔
            if (spaceHour != 0){
                if (inDay == day){
                    returnValue = spaceHour + HOUR + "前";
                }else{
                    returnValue = YESTERDAY + " " + DateUtil.date2String(inDate, DatePattern.COMMON_TIME_WITHOUT_SECOND);
                }
            }else{
                // 分钟间隔
                if (spaceMinute == 0){
                    returnValue = spaceSecond + SECOND + "前";
                }else{
                    returnValue = spaceMinute + MINUTE + "前";
                }
            }
        }
        return returnValue;
    }

    // [end]

    /**
     * 将日期集合装成特定pattern的字符串集合.
     * 
     * @param dateList
     *            日期集合
     * @param datePattern
     *            模式 {@link DatePattern}
     * 
     * @return 如果 Validator.isNotNullOrEmpty(dateList) return null;<br>
     *         否则循环date转成string,返回{@code List<String>}
     */
    public static final List<String> toStringList(List<Date> dateList,String datePattern){
        if (Validator.isNotNullOrEmpty(dateList)){
            List<String> stringList = new ArrayList<String>();
            for (Date date : dateList){
                stringList.add(DateUtil.date2String(date, datePattern));
            }
            return stringList;
        }
        return null;
    }

    /**
     * 将时间(单位毫秒),并且转换成直观的表示方式.
     * 
     * <pre>
     * getIntervalForView(13516)
     * return 13秒516毫秒
     * 
     * getIntervalForView(0)
     * return 0
     * 
     * 自动增加 天,小时,分钟,秒,毫秒中文文字
     * </pre>
     * 
     * @param spaceTime
     *            单位毫秒
     * @return 将时间(单位毫秒),并且转换成直观的表示方式<br>
     *         如果 space_time 是0 直接返回0
     * @see DateUtil#getIntervalDay(long)
     * @see DateUtil#getIntervalHour(long)
     * @see DateUtil#getIntervalMinute(long)
     * @see DateUtil#getIntervalSecond(long)
     */
    public static final String getIntervalForView(long spaceTime){
        if (0 == spaceTime){
            return "0";
        }
        // **************************************************************************************
        // 间隔天数
        long spaceDay = DateUtil.getIntervalDay(spaceTime);
        // 间隔小时 减去间隔天数后,
        long spaceHour = DateUtil.getIntervalHour(spaceTime) - spaceDay * 24;
        // 间隔分钟 减去间隔天数及间隔小时后,
        long spaceMinute = DateUtil.getIntervalMinute(spaceTime) - (spaceDay * 24 + spaceHour) * 60;
        // 间隔秒 减去间隔天数及间隔小时,间隔分钟后,
        long spaceSecond = DateUtil.getIntervalSecond(spaceTime) - ((spaceDay * 24 + spaceHour) * 60 + spaceMinute) * 60;
        // 间隔秒 减去间隔天数及间隔小时,间隔分钟后,
        long spaceMillisecond = spaceTime - (((spaceDay * 24 + spaceHour) * 60 + spaceMinute) * 60 + spaceSecond) * 1000;
        // **************************************************************************************
        StringBuilder stringBuilder = new StringBuilder();
        if (0 != spaceDay){
            stringBuilder.append(spaceDay + DAY);
        }
        if (0 != spaceHour){
            stringBuilder.append(spaceHour + HOUR);
        }
        if (0 != spaceMinute){
            stringBuilder.append(spaceMinute + MINUTE);
        }
        if (0 != spaceSecond){
            stringBuilder.append(spaceSecond + SECOND);
        }
        if (0 != spaceMillisecond){
            stringBuilder.append(spaceMillisecond + MILLISECOND);
        }
        return stringBuilder.toString();
    }

    /**
     * 获得两日期之间的间隔,并且转换成直观的表示方式.
     * 
     * <pre>
     * getIntervalForView(2011-05-19 8:30:40,2011-05-19 11:30:24) 
     * return 转换成2小时59分44秒
     * 
     * getIntervalForView(2011-05-19 11:31:25.456,2011-05-19 11:30:24.895)
     * return 1分钟1秒 
     * 
     * 自动增加 天,小时,分钟,秒,毫秒中文文字
     * </pre>
     * 
     * @param date1
     *            时间1
     * @param date2
     *            时间2
     * @return 获得两日期之间的间隔,并且转换成直观的表示方式
     * @see #getIntervalForView(long)
     * @see DateUtil#getIntervalTime(Date, Date)
     */
    public static final String getIntervalForView(Date date1,Date date2){
        long spaceTime = DateUtil.getIntervalTime(date1, date2);
        return getIntervalForView(spaceTime);
    }
}