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
package com.feilong.core.awt;

import java.util.Date;

import org.junit.Test;

import com.feilong.core.awt.ScreenShotUtil;
import com.feilong.core.date.DatePattern;
import com.feilong.core.date.DateUtil;
import com.feilong.core.io.ImageType;

/**
 * The Class ScreenShotUtilTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-5-30 下午01:36:20
 * @since 1.0
 */
public class ScreenShotUtilTest{

    /**
     * Test screenshot.
     */
    @Test
    public final void testScreenshot(){
        // 根据文件前缀变量和文件格式变量，自动生成文件名
        String name = "e:/" + DateUtil.date2String(new Date(), DatePattern.TIMESTAMP) + "." + "png"; //"png"
        ScreenShotUtil.screenshot(name, ImageType.PNG);
    }
}
