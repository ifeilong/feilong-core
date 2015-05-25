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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.core.util.RegexUtil;

/**
 * The Class SelectHelper.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-4-6 下午3:11:10
 * @deprecated
 */
@Deprecated
public class SelectHelper{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(SelectHelper.class);

    /** Don't let anyone instantiate this class. */
    private SelectHelper(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获得年份集合
     * 
     * <pre>
     * 
     * 例如 要获得1949-2020年份集合
     * 
     * 则getYearList(1949,2020,true);
     * 
     * </pre>
     * 
     * .
     *
     * @param beginYear
     *            开始年份
     * @param endYear
     *            结束年份
     * @param desc
     *            是否倒序,年份大的排在上面
     * @return 获得年份集合
     * @since 1.0
     */
    public static List<SelectEntity> getYearList(int beginYear,int endYear,boolean desc){
        List<SelectEntity> yearList = new ArrayList<SelectEntity>();
        SelectEntity selectEntity = null;
        // 倒序
        if (desc){
            for (int i = endYear; i >= beginYear; --i){
                selectEntity = new SelectEntity(i, i);
                yearList.add(selectEntity);
            }
        }
        // 顺序
        else{
            for (int i = beginYear; i <= endYear; ++i){
                selectEntity = new SelectEntity(i, i);
                yearList.add(selectEntity);
            }
        }
        return yearList;
    }

    /**
     * 获得月份列表.
     *
     * @param desc
     *            是否倒序,年份大的排在上面
     * @return 获得月份列表
     * @since 1.0
     */
    public static List<SelectEntity> getMonthList(boolean desc){
        List<SelectEntity> monthList = new ArrayList<SelectEntity>();
        SelectEntity selectEntity = null;
        log.info("the param desc is:{}", desc);
        // 倒序
        if (desc){
            for (int i = 12; i >= 1; --i){
                selectEntity = new SelectEntity(i, i);
                monthList.add(selectEntity);
            }
        }
        // 顺序
        else{
            for (int i = 1; i <= 12; ++i){
                selectEntity = new SelectEntity(i, i);
                monthList.add(selectEntity);
            }
        }
        return monthList;
    }

    /**
     * 将出生日期转换成年龄.
     * 
     * @param birthday
     *            出生日期,格式{@link DatePattern#COMMON_DATE}
     * @return 将生日转换成年龄,如果传入的日期格式不正确,则返回null
     * @since 1.0
     * @deprecated 待重构,不建议使用
     */
    @Deprecated
    public static final Integer convertBirthdayToAge(String birthday){
        String birthdayPattern = "^(?:([0-9]{4}-(?:(?:0?[1,3-9]|1[0-2])-(?:29|30)|" + "((?:0?[13578]|1[02])-31)))|"
                        + "([0-9]{4}-(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1\\d|2[0-8]))|" + "(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|"
                        + "(?:0[48]00|[2468][048]00|[13579][26]00))-0?2-29)))$";
        if (RegexUtil.matches(birthdayPattern, birthday)){
            Date nowDate = new Date();
            return DateUtil.getYear(nowDate) - Integer.parseInt(birthday.substring(0, 4)) + 1;
        }
        return null;
    }
}
