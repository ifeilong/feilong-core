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

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.jsonlib.JsonUtil;

/**
 * The Class DatePatternTest.
 *
 * @author feilong
 * @version 1.2.2 2015年7月17日 下午3:31:43
 * @since 1.2.2
 */
public class DatePatternTest extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DatePatternTest.class);

    /**
     * Test date pattern.
     */
    @Test
    public void testDatePattern(){
        Map<String, String> map = new LinkedHashMap<String, String>();

        map.put("forToString", DateUtil.date2String(NOW, DatePattern.forToString));
        map.put("ddMMyyyyHHmmss", DateUtil.date2String(NOW, DatePattern.ddMMyyyyHHmmss));
        map.put("timestamp", DateUtil.date2String(NOW, DatePattern.TIMESTAMP));
        map.put("timestampWithMillisecond", DateUtil.date2String(NOW, DatePattern.TIMESTAMP_WITH_MILLISECOND));

        map.put("commonWithMillisecond", DateUtil.date2String(NOW, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));
        map.put("commonWithoutAndYearSecond", DateUtil.date2String(NOW, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND));
        map.put("commonWithoutSecond", DateUtil.date2String(NOW, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND));
        map.put("commonWithTime", DateUtil.date2String(NOW, DatePattern.COMMON_DATE_AND_TIME));

        map.put("monthAndDay", DateUtil.date2String(NOW, DatePattern.MONTH_AND_DAY));
        map.put("monthAndDayWithWeek", DateUtil.date2String(NOW, DatePattern.MONTH_AND_DAY_WITH_WEEK));
        map.put("onlyDate", DateUtil.date2String(NOW, DatePattern.COMMON_DATE));
        map.put("onlyTime", DateUtil.date2String(NOW, DatePattern.COMMON_TIME));
        map.put("onlyTime_withoutSecond", DateUtil.date2String(NOW, DatePattern.COMMON_TIME_WITHOUT_SECOND));

        map.put("yearAndMonth", DateUtil.date2String(NOW, DatePattern.YEAR_AND_MONTH));

        //yyyy/yyy/yy/y 显示为 2014/2014/14/4
        //（3个y与4个y是一样的，为了便于理解多写成4个y）

        map.put("y", DateUtil.date2String(NOW, "y"));
        map.put("yy", DateUtil.date2String(NOW, DatePattern.yy));
        map.put("yyy", DateUtil.date2String(NOW, "yyy"));
        map.put("yyyy", DateUtil.date2String(NOW, DatePattern.yyyy));

        //MMMM/MMM/MM/M 显示为 一月/一月/01/1
        //（4个M显示全称，3个M显示缩写，不过中文显示是一样的，英文就是January和Jan）
        map.put("M", DateUtil.date2String(NOW, "M"));
        map.put("MM", DateUtil.date2String(NOW, DatePattern.MM));
        map.put("MMM", DateUtil.date2String(NOW, "MMM"));
        map.put("MMMM", DateUtil.date2String(NOW, "MMMM"));

        //dddd/ddd/dd/d 显示为 星期三/周三(有的语言显示为“三”)/01/1
        //（在英文中同M一样，4个d是全称，3个是简称；
        //dddd/ddd表示星期几，dd/d表示几号）
        map.put("d", DateUtil.date2String(NOW, "d"));
        map.put("dd", DateUtil.date2String(NOW, "dd"));
        map.put("ddd", DateUtil.date2String(NOW, "ddd"));
        map.put("dddd", DateUtil.date2String(NOW, "dddd"));

        //HH/H/hh/h 显示为 01/1/01 AM/1 AM
        map.put("HH", DateUtil.date2String(NOW, DatePattern.HH));
        map.put("H", DateUtil.date2String(NOW, "H"));
        map.put("hh", DateUtil.date2String(NOW, "hh"));
        map.put("h", DateUtil.date2String(NOW, "h"));

        //剩下的mm/m/ss/s只是前导零的问题了
        map.put("mm", DateUtil.date2String(NOW, "mm"));
        map.put("m", DateUtil.date2String(NOW, "m"));

        map.put("ss", DateUtil.date2String(NOW, "ss"));
        map.put("s", DateUtil.date2String(NOW, "s"));

        map.put("mmss", DateUtil.date2String(NOW, DatePattern.mmss));

        map.put("yyyyMMdd", DateUtil.date2String(NOW, DatePattern.yyyyMMdd));

        map.put("yyyy年MM月dd日", DateUtil.date2String(NOW, DatePattern.CHINESE_COMMON_DATE));
        map.put("yyyy年MM月dd日 HH:mm:ss", DateUtil.date2String(NOW, DatePattern.CHINESE_COMMON_DATE_AND_TIME));

        LOGGER.debug(JsonUtil.format(map));
    }
}
