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

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.feilong.core.bean.ConvertUtil;
import com.feilong.core.net.ParamUtil;

import static com.feilong.core.bean.ConvertUtil.toMap;

/**
 * The Class ParamUtilJoinValuesOrderByIncludeKeysTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class JoinValuesOrderByIncludeKeysTest{

    /**
     * Test join values one key.
     */
    @Test
    public void testJoinValuesOneKey(){
        Map<String, String> map = toMap("service", "create_salesorder", "paymentType", "unionpay_mobile");
        assertEquals("create_salesorder", ParamUtil.joinValuesOrderByIncludeKeys(map, "service"));
    }

    /**
     * Test join values two keys.
     */
    @Test
    public void testJoinValuesTwoKeys(){
        Map<String, String> map = toMap("service", "create_salesorder", "paymentType", "unionpay_mobile");
        assertEquals("create_salesorder" + "unionpay_mobile", ParamUtil.joinValuesOrderByIncludeKeys(map, "service", "paymentType"));
        assertEquals("unionpay_mobile" + "create_salesorder", ParamUtil.joinValuesOrderByIncludeKeys(map, "paymentType", "service"));
    }

    /**
     * Test join values no exist key.
     */
    @Test
    public void testJoinValuesNoExistKey(){
        Map<String, String> map = toMap("service", "create_salesorder", "paymentType", "unionpay_mobile");
        assertEquals(EMPTY, ParamUtil.joinValuesOrderByIncludeKeys(map, "a", "b"));
    }

    /**
     * Test join values order by include keys null map.
     */
    @Test(expected = NullPointerException.class)
    public void testJoinValuesOrderByIncludeKeysNullMap(){
        ParamUtil.joinValuesOrderByIncludeKeys(null, "service");
    }

    /**
     * Test join values order by include keys null keys.
     */
    @Test
    public void testJoinValuesOrderByIncludeKeysNullKeys(){
        Map<String, String> map = toMap("service", "create_salesorder", "paymentType", "unionpay_mobile");
        assertEquals(EMPTY, ParamUtil.joinValuesOrderByIncludeKeys(map, null));
    }

    /**
     * Test join values order by include keys empty keys.
     */
    @Test
    public void testJoinValuesOrderByIncludeKeysEmptyKeys(){
        Map<String, String> map = toMap("service", "create_salesorder", "paymentType", "unionpay_mobile");
        assertEquals(EMPTY, ParamUtil.joinValuesOrderByIncludeKeys(map));
    }

    /**
     * Test join values order by include keys empty keys 1.
     */
    @Test
    public void testJoinValuesOrderByIncludeKeysEmptyKeys1(){
        Map<String, String> map = toMap("service", "create_salesorder", "paymentType", "unionpay_mobile");
        assertEquals(EMPTY, ParamUtil.joinValuesOrderByIncludeKeys(map, ConvertUtil.<String> toArray()));
    }

    /**
     * Test join values order by include keys null element keys.
     */
    @Test
    public void testJoinValuesOrderByIncludeKeysNullElementKeys(){
        Map<String, String> map = toMap("service", "create_salesorder", "paymentType", "unionpay_mobile");
        assertEquals(EMPTY, ParamUtil.joinValuesOrderByIncludeKeys(map, ConvertUtil.<String> toArray((String) null)));
    }
}
