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

    // [start] 获得时间 /时间数组,可以用于sql查询
    /**
     * 获得重置清零的今天和明天,当天0:00:00及下一天0:00:00.
     * 
     * <p>
     * 一般用于统计当天数据,between ... and ...
     * </p>
     * 
     * <pre>
     * 比如今天是 2012-10-16 22:18:34
     * 
     * return {2012-10-16 00:00:00.000,2012-10-17 00:00:00.000}
     * </pre>
     * 
     * @return Date数组 第一个为today 第二个为tomorrow
     */
    public static Date[] getResetTodayAndTomorrow(){
        Calendar calendar = CalendarUtil.resetCalendarByDay(new Date());
        Date today = calendar.getTime();
        // ***************************
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        return new Date[] { today, tomorrow };
    }

    /**
     * 获得重置清零的昨天和今天 [yestoday,today].
     * 
     * <p>
     * 第一个为昨天00:00 <br>
     * 第二个为今天00:00 <br>
     * 一般用于sql/hql统计昨天数据,between ... and ...
     * </p>
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
     */
    public static Date[] getResetYesterdayAndToday(){
        Calendar calendar = CalendarUtil.resetCalendarByDay(new Date());
        Date today = calendar.getTime();
        return new Date[] { DateUtil.getYesterday(today), today };
    }

    // [end]
    /**
     * 获得两个日期时间的日期间隔时间集合(包含最小和最大值),用于统计日报表.
     * 
     * <p>
     * 每天的日期会被重置清零 <code>00:00:00.000</code>
     * </p>
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
    public static List<Date> getIntervalDayList(String fromDateString,String toDateString,String datePattern){
        List<Date> dateList = new ArrayList<Date>();
        //***************************************************************/
        Date beginDate = DateUtil.string2Date(fromDateString, datePattern);
        Date endDate = DateUtil.string2Date(toDateString, datePattern);
        // ******重置时间********
        Date beginDateReset = CalendarUtil.resetDateByDay(beginDate);
        Date endDateReset = CalendarUtil.resetDateByDay(endDate);
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
        return dateList;
    }

    /**
     * 获得一年中所有的周几集合 例如:getWeekDateStringList(6, "yyyy-MM-dd");.
     * 
     * @param week
     *            周几 星期天开始为1 依次2 3 4 5 6 7,建议使用 常量 {@link Calendar#SUNDAY}, {@link Calendar#MONDAY}, {@link Calendar#TUESDAY},
     *            {@link Calendar#WEDNESDAY}, {@link Calendar#THURSDAY}, {@link Calendar#FRIDAY}, {@link Calendar#SATURDAY}
     * @param datePattern
     *            获得集合里面时间字符串模式
     * @return 获得一年中所有的周几集合
     * @see org.apache.commons.lang3.time.DateUtils#iterator(Date, int)
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static List<String> getWeekDateStringList(int week,String datePattern){
        Date now = new Date();
        Date firstWeekOfSpecifyDateYear = DateUtil.getFirstWeekOfSpecifyDateYear(now, week);
        //当年最后一天
        Calendar calendarEnd = CalendarUtil.resetYearEnd(DateUtil.toCalendar(now));

        List<String> list = new ArrayList<String>();
        for (Calendar calendar = DateUtil.toCalendar(firstWeekOfSpecifyDateYear); calendar.before(calendarEnd); calendar.add(
                        Calendar.DAY_OF_YEAR,
                        7)){
            list.add(CalendarUtil.toString(calendar, datePattern));
        }
        return list;
    }

    // [start]转换成特色时间 toHumanizationDateString

    /**
     * 人性化显示date时间,依据是和现在的时间间隔.
     * 
     * <p>
     * 转换规则,将传入的inDate和 new Date()当前时间比较;当两者的时间差,(一般inDate小于当前时间 ,暂时不支持大于当前时间)
     * </p>
     * 
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
     */
    public static String toPrettyDateString(Date inDate){
        Date nowDate = new Date();

        // 传过来的日期的年份
        int inYear = DateUtil.getYear(inDate);
        //**************************************************************************************/
        int currentYear = DateUtil.getYear(nowDate);// 当前时间的年
        boolean isSameYear = currentYear == inYear;//是否是同一年
        long spaceTime = DateUtil.getIntervalTime(inDate, nowDate);// 任意日期和现在相差的毫秒数
        int spaceDay = DateUtil.getIntervalDay(spaceTime);// 相差天数
        //**************************************************************************************/
        switch (spaceDay) {
            case 0: // 间隔0天
                return doWithZeroDayInterval(inDate, nowDate, spaceTime);
            case 1: // 间隔一天
                return doWithOneDayInterval(inDate, nowDate);
            case 2: // 间隔2天
                return doWithTwoDaysInterval(inDate, nowDate, isSameYear);
            default://spaceDay > 2     // 间隔大于2天
                if (isSameYear){
                    return DateUtil.date2String(inDate, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND);
                }
                return DateUtil.date2String(inDate, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND);
        }
    }

    /**
     * Do with one day interval.
     *
     * @param inDate
     *            the in date
     * @param nowDate
     *            the now date
     * @return the string
     * @since 1.4.0
     */
    private static String doWithOneDayInterval(Date inDate,Date nowDate){
        if (DateUtil.isEquals(DateUtil.addDay(inDate, 1), nowDate, DatePattern.COMMON_DATE)){
            return YESTERDAY + " " + DateUtil.date2String(inDate, DatePattern.COMMON_TIME_WITHOUT_SECOND);
        }
        return THEDAY_BEFORE_YESTERDAY + " " + DateUtil.date2String(inDate, DatePattern.COMMON_TIME_WITHOUT_SECOND);
    }

    /**
     * Do with two days interval.
     *
     * @param inDate
     *            the in date
     * @param nowDate
     *            the now date
     * @param isSameYear
     *            the is same year
     * @return the string
     * @since 1.4.0
     */
    private static String doWithTwoDaysInterval(Date inDate,Date nowDate,boolean isSameYear){
        if (DateUtil.isEquals(DateUtil.addDay(inDate, 2), nowDate, DatePattern.COMMON_DATE)){
            return THEDAY_BEFORE_YESTERDAY + " " + DateUtil.date2String(inDate, DatePattern.COMMON_TIME_WITHOUT_SECOND);
        }
        if (isSameYear){
            return DateUtil.date2String(inDate, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND);
        }
        return DateUtil.date2String(inDate, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND);
    }

    /**
     * Do with zero day interval.
     *
     * @param inDate
     *            the in date
     * @param nowDate
     *            the now date
     * @param spaceTime
     *            the space time
     * @return the string
     * @since 1.4.0
     */
    private static String doWithZeroDayInterval(Date inDate,Date nowDate,long spaceTime){
        int spaceHour = DateUtil.getIntervalHour(spaceTime); // 相差小时数
        if (spaceHour == 0){// 小时间隔
            int spaceMinute = DateUtil.getIntervalMinute(spaceTime);
            if (spaceMinute == 0){
                int spaceSecond = DateUtil.getIntervalSecond(spaceTime);
                return spaceSecond + SECOND + "前";
            }
            return spaceMinute + MINUTE + "前";
        }
        // 传过来的日期的日
        int inDay = DateUtil.getDayOfMonth(inDate);
        // 当前时间的日
        int currentDayOfMonth = DateUtil.getDayOfMonth(nowDate);
        if (inDay == currentDayOfMonth){
            return spaceHour + HOUR + "前";
        }
        return YESTERDAY + " " + DateUtil.date2String(inDate, DatePattern.COMMON_TIME_WITHOUT_SECOND);
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
    public static List<String> toStringList(List<Date> dateList,String datePattern){
        if (Validator.isNullOrEmpty(dateList)){
            return Collections.emptyList();
        }

        List<String> stringList = new ArrayList<String>();
        for (Date date : dateList){
            stringList.add(DateUtil.date2String(date, datePattern));
        }
        return stringList;
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
    public static String getIntervalForView(long spaceTime){
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
        StringBuilder sb = new StringBuilder();
        if (0 != spaceDay){
            sb.append(spaceDay + DAY);
        }
        if (0 != spaceHour){
            sb.append(spaceHour + HOUR);
        }
        if (0 != spaceMinute){
            sb.append(spaceMinute + MINUTE);
        }
        if (0 != spaceSecond){
            sb.append(spaceSecond + SECOND);
        }
        if (0 != spaceMillisecond){
            sb.append(spaceMillisecond + MILLISECOND);
        }
        return sb.toString();
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
    public static String getIntervalForView(Date date1,Date date2){
        long spaceTime = DateUtil.getIntervalTime(date1, date2);
        return getIntervalForView(spaceTime);
    }
}