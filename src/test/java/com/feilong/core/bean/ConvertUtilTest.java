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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.lang3.StringUtils;
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
     * Test to iterator.
     */
    @Test
    public final void testToIterator(){
        // *************************逗号分隔的数组********************************
        LOGGER.info(StringUtils.center("逗号分隔的数组", 60, "*"));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator("1,2")));

        // ************************map*********************************
        LOGGER.info(StringUtils.center("map", 60, "*"));
        Map<String, String> map = new HashMap<String, String>();

        map.put("a", "1");
        map.put("b", "2");

        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(map)));

        // ***************************array******************************
        LOGGER.info(StringUtils.center("array", 60, "*"));
        Object[] array = { "5", 8 };
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(array)));
        // ***************************collection******************************
        LOGGER.info(StringUtils.center("collection", 60, "*"));
        Collection<String> collection = new ArrayList<String>();
        collection.add("aaaa");
        collection.add("nnnnn");

        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(collection)));

        // **********************enumeration***********************************
        LOGGER.info(StringUtils.center("enumeration", 60, "*"));
        Enumeration<Object> enumeration = new StringTokenizer("this is a test");
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(enumeration)));
    }

    /**
     * Convert.
     *
     * @param <T>
     *            the generic type
     * @param toBeConvertedValue
     *            需要被转换的值
     * @param defaultArrayType
     *            默认的数组类型
     * @param individualArrayElementConverter
     *            单个元素的 {@link Converter}
     * @return the t
     * @deprecated will Re-structure
     */
    @Deprecated
    public static <T> T convert(Object toBeConvertedValue,Class<T> defaultArrayType,Converter individualArrayElementConverter){
        char[] allowedChars = new char[] { ',', '-' };
        char delimiter = ',';
        boolean onlyFirstToString = false;

        int defaultSize = 0;

        //**********************************************************
        ArrayConverter arrayConverter = new ArrayConverter(defaultArrayType, individualArrayElementConverter, defaultSize);
        arrayConverter.setAllowedChars(allowedChars);
        arrayConverter.setDelimiter(delimiter);
        arrayConverter.setOnlyFirstToString(onlyFirstToString);

        T result = arrayConverter.convert(defaultArrayType, toBeConvertedValue);
        return result;
    }

    /**
     * Test to big decimal.
     */
    @Test
    public final void testToBigDecimal(){
        assertEquals(null, ConvertUtil.toBigDecimal(null));
        assertEquals(BigDecimal.valueOf(1111), ConvertUtil.toBigDecimal(1111));
        assertEquals(BigDecimal.valueOf(0.1), ConvertUtil.toBigDecimal(0.1));
    }

    /**
     * Test to longs.
     */
    @Test
    public void testToLongs(){
        LOGGER.debug(JsonUtil.format(ConvertUtil.toLongs(new String[] { "1", "2", "3" })));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toLongs(new String[] { "1", null, "2", "3" })));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toLongs("1,2,3")));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toLongs(null)));
    }

    /**
     * Test to strings.
     */
    @Test
    public void testToStrings(){
        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings("{5,4, 8,2;8 9_5@3`a}")));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings(new Integer[] { 1, 2, 5 })));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings("null,1,2,3,\"4\",\'aaaa\'")));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings(null)));
    }

    /**
     * Test to integers.
     */
    @Test
    public void testToIntegers(){
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIntegers(null)));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIntegers(new String[] { "1", "2", "3" })));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIntegers(new String[] { "1", null, "2", "3" })));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIntegers("1,2,3")));
    }

    /**
     * Test to long.
     */
    @Test
    public void testToLong(){
        LOGGER.debug("" + ConvertUtil.toLong("1"));
        LOGGER.debug("" + ConvertUtil.toLong(null));
        LOGGER.debug("" + ConvertUtil.toLong(new String[] { "1", "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toLong(new String[] { "1", null, "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toLong("1,2,3"));
    }

    /**
     * Test to boolean.
     */
    @Test
    public void testToBoolean(){
        LOGGER.debug("" + ConvertUtil.toBoolean("1"));
        LOGGER.debug("" + ConvertUtil.toBoolean(null));
        LOGGER.debug("" + ConvertUtil.toBoolean(new String[] { "1", "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toBoolean(new String[] { "1", null, "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toBoolean("1,2,3"));
    }

    /**
     * Test to integer.
     */
    @Test
    public void testToInteger(){
        LOGGER.debug("" + ConvertUtil.toInteger(new String[] { "1", "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toInteger("1"));
        LOGGER.debug("" + ConvertUtil.toInteger(null));
        LOGGER.debug("" + ConvertUtil.toInteger(new String[] { "1", null, "2", "3" }));
        LOGGER.debug("" + ConvertUtil.toInteger("1,2,3"));
    }

    /**
     * Test to integer.
     */
    @Test
    public final void testToInteger2(){
        assertEquals(null, ConvertUtil.toInteger(null));
        assertEquals(8, ConvertUtil.toInteger(8L).intValue());
        assertEquals(8, ConvertUtil.toInteger("8").intValue());
    }

    /**
     * To t test.
     */
    @Test
    public void toTTest(){
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray("6", "_");

        LinkedList<Serializable> linkedList = new LinkedList<Serializable>();

        for (String string : tokenizeToStringArray){
            Serializable t = ConvertUtil.convert(string, Serializable.class);
            LOGGER.debug(t.getClass().getCanonicalName());
            linkedList.add(t);
        }

        Serializable l = 6L;

        LOGGER.info("linkedList:{},contains:{},{}", linkedList, l, linkedList.contains(l));
    }

    /**
     * Test to string object.
     */
    @Test
    public final void testToString(){
        String[] aaaa = { "aa", "aaa" };
        assertEquals("[aa, aaa]", ConvertUtil.toString(aaaa));
    }

    /**
     * Test to string2.
     */
    @Test
    public final void testToString2(){
        int[] int1 = { 2, 1 };
        LOGGER.debug(ConvertUtil.toString(int1));
    }

    /**
     * Test convert array.
     */
    @Test
    public final void testConvertArray(){
        String[] int1 = { "2", "1" };
        LOGGER.debug(JsonUtil.format(ConvertUtil.convert(int1, Long.class)));
    }
}
