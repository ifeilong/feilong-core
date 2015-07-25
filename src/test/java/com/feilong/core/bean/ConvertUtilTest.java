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
package com.feilong.core.bean;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.jsonlib.JsonUtil;
import com.feilong.core.util.StringUtil;

/**
 * The Class ConvertUtilTest.
 *
 * @author feilong
 * @version 1.3.0 2015年7月25日 上午1:57:56
 * @since 1.3.0
 */
public class ConvertUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertUtilTest.class);

    /**
     * Test to longs.
     */
    @Test
    public void testToLongs(){
        String source = "1,2,3";

        if (LOGGER.isDebugEnabled()){
            LOGGER.debug(JsonUtil.format(ConvertUtil.toLongs(source)));
        }
    }

    /**
     * To t test.
     */
    @Test
    public void toTTest(){
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray("6", "_");

        LinkedList<Serializable> linkedList = new LinkedList<Serializable>();

        for (String string : tokenizeToStringArray){
            Serializable t = ConvertUtil.toT(string, Serializable.class);

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug(t.getClass().getCanonicalName());
            }
            linkedList.add(t);
        }

        Serializable l = 6L;

        LOGGER.info("linkedList:{},contains:{},{}", linkedList, l, linkedList.contains(l));
    }

    /**
     * Test to integer.
     */
    @Test
    public final void testToInteger(){
        assertEquals(null, ConvertUtil.toInteger(null));
        assertEquals(8, ConvertUtil.toInteger(8L).intValue());
        assertEquals(8, ConvertUtil.toInteger("8").intValue());
    }

    /**
     * Test to string object.
     */
    @Test
    public final void testToStringObject(){
        String[] aaaa = { "aa", "aaa" };
        assertEquals("[aa, aaa]", ConvertUtil.toString(aaaa));
    }

    /**
     * Test to t.
     */
    @Test
    public final void testToT(){
        LOGGER.info(ConvertUtil.toT(BigDecimal.ONE, Float.class) + "");
    }

    /**
     * Test to big decimal.
     */
    @Test
    public final void testToBigDecimal(){
        assertEquals(BigDecimal.valueOf(1111), ConvertUtil.toBigDecimal(1111));
        assertEquals(BigDecimal.valueOf(0.1), ConvertUtil.toBigDecimal(0.1));
        assertEquals(null, ConvertUtil.toBigDecimal(null));
    }
}
