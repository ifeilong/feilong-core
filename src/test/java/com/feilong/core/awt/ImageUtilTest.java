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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.awt.ImageUtil;

/**
 * The Class ImageUtilTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.1.2 2015年5月9日 上午12:03:27
 * @since 1.1.2
 */
public class ImageUtilTest{

    /**
     * Test method for {@link com.feilong.core.awt.ImageUtil#isCMYKType(java.lang.String)}.
     */
    @Test
    public final void testIsCMYKType(){
        //assertEquals(false, ImageUtil.isCMYKType("E:/DataCommon/test/1.png"));
        assertEquals(false, ImageUtil.isCMYKType("E:/DataCommon/test/1.jpg"));
    }

    /**
     * Test is cmyk type2.
     */
    @Test
    public final void testIsCMYKType2(){
        assertEquals(true, ImageUtil.isCMYKType("E:/DataCommon/test/cmyk.jpg"));
    }
}
