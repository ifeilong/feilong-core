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
package com.feilong.core.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.text.NumberFormatUtil;

/**
 * The Class NumberFormatUtilTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.7 2014年5月22日 下午11:31:15
 * @since 1.0.7
 */
public class NumberFormatUtilTest{

    /**
     * Test method for {@link com.feilong.core.text.NumberFormatUtil#format(java.lang.Number, java.lang.String)}.
     */
    @Test
    public final void testFormat(){
        assertEquals("26", NumberFormatUtil.format(25.5, "#####"));
        assertEquals("RP 26", NumberFormatUtil.format(25.5, "RP #####"));
    }
}
