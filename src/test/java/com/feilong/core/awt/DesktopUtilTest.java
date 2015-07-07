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
package com.feilong.core.awt;

import org.junit.Test;

import com.feilong.core.awt.DesktopUtil;

/**
 * The Class DesktopUtilTest.
 *
 * @author feilong
 * @version 1.0 2011-5-5 下午05:15:42
 * @since 1.0
 */
public class DesktopUtilTest{

    /** The test file. */
    private final String testFile = "E:\\DataCommon\\test\\test.txt";

    /**
     * Test browse1.
     */
    @Test
    public final void testBrowse1(){
        int id = 14;
        String s = "";
        DesktopUtil.browse("http://101.95.128.146/payment/paymentChannel?s=" + s + "&id=" + id);
    }

    /**
     * Test print.
     */
    @Test
    public final void testPrint(){
        DesktopUtil.print(testFile);
    }

    /**
     * Test edit.
     */
    @Test
    public final void testEdit(){
        DesktopUtil.edit(testFile);
    }

    /**
     * Test open.
     */
    @Test
    public final void testOpen(){
        DesktopUtil.open(testFile);
    }

    /**
     * Test mail.
     */
    @Test
    public final void testMail(){
        DesktopUtil.mail("mailto:venusdrogon@163.com");
    }

    /**
     * {@link com.feilong.core.awt.DesktopUtil#browse(java.lang.String)} 的测试方法。
     */
    @Test
    public final void testBrowse(){
        String[] strings = {
                "2RMD217-4",
                "2RMD249-1",
                "2RMD679-4",
                "2RMD679-4",
                "2RMD697-3",
                "2RMD697-3",
                "ARHE027-2",
                "ARHE041-1",
                "CRDF013-3" };
        for (String string : strings){
            DesktopUtil.browse("http://list.tmall.com/search_product.htm?q=" + string + "&type=p&cat=all&userBucket=5&userBucketCell=25");
        }
    }
}
