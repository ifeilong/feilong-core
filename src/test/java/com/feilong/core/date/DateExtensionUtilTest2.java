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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DateExtensionUtilTest.
 *
 * @author feilong
 * @version 1.0.8 2014年7月31日 下午2:48:22
 * @since 1.0.8
 */
public class DateExtensionUtilTest2 extends BaseDateUtilTest{

    /** The Constant LOGGER. */
    private static final Logger   LOGGER        = LoggerFactory.getLogger(DateExtensionUtilTest2.class);

    /** 星期. */
    private static final String   WEEK          = "星期";

    /**
     * 中文星期.<br>
     * { "日", "一", "二", "三", "四", "五", "六" }
     */
    private static final String[] WEEK_CHINESES = { "日", "一", "二", "三", "四", "五", "六" };

    /**
     * 英文星期.<br>
     * { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }
     */
    private static final String[] WEEK_ENGLISHS = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

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
     * <h3>示例:</h3>
     * <blockquote>
     * 
     * <pre class="code">
     * DateExtensionUtil.getChineseWeek(0)  return 星期日
     * </pre>
     * 
     * </blockquote>
     * 
     * @param week
     *            星期几,从0开始 ,依次1 2 --6
     * @return 如 星期一
     */
    public static String getChineseWeek(int week){
        return WEEK + WEEK_CHINESES[week];
    }

    /**
     * Test get chinese week.
     */
    @Test
    public void testGetChineseWeek(){
        LOGGER.debug(getChineseWeek(0));
    }

}