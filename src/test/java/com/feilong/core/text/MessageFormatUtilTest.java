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
package com.feilong.core.text;

import static com.feilong.core.date.DateUtil.toDate;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class MessageFormatUtilTest.
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class MessageFormatUtilTest{

    /**
     * Test format.
     */
    @Test
    public void testFormat(){
        assertEquals("name=张三jinaxin", MessageFormatUtil.format("name=张三{0}a{1}", "jin", "xin"));
        assertEquals("name=张三5axin", MessageFormatUtil.format("name=张三{0,number}a{1}", 5, "xin"));
        assertEquals("name=张三2000-01-01axin", MessageFormatUtil.format("name=张三{0,date,yyyy-MM-dd}a{1}", toDate("2000", "yyyy"), "xin"));
    }

    /**
     * Test format 1.
     */
    @Test(expected = NullPointerException.class)
    public void testFormat1(){
        MessageFormatUtil.format(null, "jin", "xin");
    }

}
