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
package com.feilong.core.bean.convertutiltest;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.store.member.User;

import static com.feilong.core.bean.ConvertUtil.toLocale;

/**
 * The Class ConvertUtilToLocaleTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToLocaleTest{

    /**
     * Test convert5.
     */
    @Test
    public void testConvert5(){
        assertEquals("zh_CN", ConvertUtil.convert("zh_CN", Locale.class));
    }

    /**
     * Test to locale.
     */
    @Test
    public void testToLocale(){
        assertEquals(null, toLocale(null));
        assertEquals(Locale.CHINA, toLocale("zh_CN"));
        assertEquals(Locale.CHINA, toLocale(Locale.CHINA));
    }

    /**
     * Test to locale 1.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testToLocale1(){
        toLocale(new User());
    }
}
