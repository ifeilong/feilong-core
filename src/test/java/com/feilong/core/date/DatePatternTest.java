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
 *
 * @author feilong
 * @version 1.2.2 2015年7月17日 下午3:31:43
 * @since 1.2.2
 */
public class DatePatternTest extends BaseDateUtilTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(DatePatternTest.class);

    /**
     * Test date pattern.
     */
    @Test
    public void testDatePattern(){
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("commonWithMillisecond:", DateUtil.date2String(NOW, DatePattern.COMMON_DATE_AND_TIME_WITH_MILLISECOND));
        map.put("commonWithoutAndYearSecond:", DateUtil.date2String(NOW, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_YEAR_AND_SECOND));
        map.put("commonWithoutSecond:", DateUtil.date2String(NOW, DatePattern.COMMON_DATE_AND_TIME_WITHOUT_SECOND));
        map.put("commonWithTime:", DateUtil.date2String(NOW, DatePattern.COMMON_DATE_AND_TIME));
        map.put("forToString:", DateUtil.date2String(NOW, DatePattern.forToString));
        map.put("HH:", DateUtil.date2String(NOW, DatePattern.HH));
        map.put("MM:", DateUtil.date2String(NOW, DatePattern.MM));
        map.put("mmss:", DateUtil.date2String(NOW, DatePattern.mmss));
        map.put("monthAndDay:", DateUtil.date2String(NOW, DatePattern.MONTH_AND_DAY));
        map.put("monthAndDayWithWeek:", DateUtil.date2String(NOW, DatePattern.MONTH_AND_DAY_WITH_WEEK));
        map.put("onlyDate:", DateUtil.date2String(NOW, DatePattern.COMMON_DATE));
        map.put("onlyTime:", DateUtil.date2String(NOW, DatePattern.COMMON_TIME));
        map.put("onlyTime_withoutSecond:", DateUtil.date2String(NOW, DatePattern.COMMON_TIME_WITHOUT_SECOND));

        map.put("timestamp:", DateUtil.date2String(NOW, DatePattern.TIMESTAMP));
        map.put("timestampWithMillisecond:", DateUtil.date2String(NOW, DatePattern.TIMESTAMP_WITH_MILLISECOND));
        map.put("yearAndMonth:", DateUtil.date2String(NOW, DatePattern.YEAR_AND_MONTH));
        map.put("yy:", DateUtil.date2String(NOW, DatePattern.yy));
        map.put("yyyyMMdd:", DateUtil.date2String(NOW, DatePattern.yyyyMMdd));

        map.put("yyyy年MM月dd日:", DateUtil.date2String(NOW, DatePattern.CHINESE_COMMON_DATE));
        map.put("yyyy年MM月dd日 HH:mm:ss:", DateUtil.date2String(NOW, DatePattern.CHINESE_COMMON_DATE_AND_TIME));

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(map));
        }
    }

}
