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
package com.feilong.core.net.paramutiltest;

import static com.feilong.core.CharsetType.UTF8;
import static com.feilong.core.net.ParamUtil.addParameter;
import static com.feilong.core.net.URIUtil.encode;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class ParamUtilAddParameterTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class AddParameterTest{

    /** The Constant PATH. */
    private static final String PATH = "http://www.feilong.com:8888/esprit-frontend/search.htm";

    /**
     * Test add parameter no param.
     */
    @Test
    public void testAddParameterNoParam(){
        String uriString = PATH;
        assertEquals(PATH + "?label=2-5-8-12", addParameter(uriString, "label", "2-5-8-12", UTF8));
    }

    /**
     * Test add parameter.
     */
    @Test
    public void testAddParameter(){
        String uriString = PATH + "?keyword=%E6%81%A4&page=";
        assertEquals(PATH + "?keyword=%E6%81%A4&page=&label=2-5-8-12", addParameter(uriString, "label", "2-5-8-12", UTF8));
    }

    /**
     * Test add parameter chinese value.
     */
    @Test
    public void testAddParameterChineseValue(){
        String uriString = PATH + "?keyword=%E6%81%A4&page=";
        String value = "中国";
        assertEquals(PATH + "?keyword=%E6%81%A4&page=&label=" + encode(value, UTF8), addParameter(uriString, "label", value, UTF8));
    }

    /**
     * Test add parameter replace value.
     */
    @Test
    public void testAddParameterReplaceValue(){
        String uriString = PATH + "?keyword=%E6%81%A4&label=hahaha&page=";
        String value = "中国";
        assertEquals(PATH + "?keyword=%E6%81%A4&label=" + encode(value, UTF8) + "&page=", addParameter(uriString, "label", value, UTF8));
    }

    /**
     * Test add parameter replace value two params.
     */
    @Test
    public void testAddParameterReplaceValueTwoParams(){
        String uriString = PATH + "?label=lalala&keyword=%E6%81%A4&label=hahaha&page=";

        String value = "中国";
        String expected = PATH + "?label=" + encode(value, UTF8) + "&keyword=%E6%81%A4&page=";
        assertEquals(expected, addParameter(uriString, "label", value, UTF8));
    }

    /**
     * Test add parameter null charset type.
     */
    @Test
    public void testAddParameterNullCharsetType(){
        String uriString = PATH + "?keyword=%E6%81%A4&page=";
        assertEquals(PATH + "?keyword=%E6%81%A4&page=&label=中国", addParameter(uriString, "label", "中国", null));
    }

    /**
     * Test add parameter null param name.
     */
    @Test
    public void testAddParameterNullParamName(){
        String uriString = PATH + "?keyword=%E6%81%A4&page=";
        assertEquals(PATH + "?keyword=%E6%81%A4&page=&=", addParameter(uriString, null, null, null));
    }

    /**
     * Test add parameter null param name 1.
     */
    @Test
    public void testAddParameterNullParamName1(){
        String uriString = PATH + "?keyword=%E6%81%A4&page=";
        assertEquals(PATH + "?keyword=%E6%81%A4&page=&=2-5-8-12", addParameter(uriString, null, "2-5-8-12", null));
    }

    /**
     * Test add parameter null value.
     */
    @Test
    public void testAddParameterNullValue(){
        String uriString = PATH + "?keyword=%E6%81%A4&page=";
        assertEquals(PATH + "?keyword=%E6%81%A4&page=&label=", addParameter(uriString, "label", null, null));
    }

    //---------------------------------------------------------------

    /**
     * Test add parameter null uri.
     */
    @Test
    public void testAddParameterNullUri(){
        assertEquals(EMPTY, addParameter(null, "label", "2-5-8-12", UTF8));
    }

    /**
     * Test add parameter empty uri.
     */
    @Test
    public void testAddParameterEmptyUri(){
        assertEquals(EMPTY, addParameter("", "label", "2-5-8-12", UTF8));
    }

    /**
     * Test add parameter blank uri.
     */
    @Test
    public void testAddParameterBlankUri(){
        assertEquals(EMPTY, addParameter(" ", "label", "2-5-8-12", UTF8));
    }
}
