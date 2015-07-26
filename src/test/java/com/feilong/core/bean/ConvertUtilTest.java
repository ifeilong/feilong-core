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

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
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
     * Convert.
     *
     * @param <T>
     *            the generic type
     * @param defaultArrayType
     *            默认的数组类型
     * @param individualArrayElementConverter
     *            单个元素的 {@link Converter}
     * @param toBeConvertedValue
     *            需要被转换的值
     * @return the t
     * @deprecated will Re-structure
     */
    @Deprecated
    public static final <T> T convert(Class<T> defaultArrayType,Converter individualArrayElementConverter,Object toBeConvertedValue){
        //char[] allowedChars = new char[] { ',', '-' };
        char delimiter = ',';
        boolean onlyFirstToString = true;

        int defaultSize = 0;

        //**********************************************************
        ArrayConverter arrayConverter = new ArrayConverter(defaultArrayType, individualArrayElementConverter, defaultSize);
        //arrayConverter.setAllowedChars(allowedChars);
        arrayConverter.setDelimiter(delimiter);
        arrayConverter.setOnlyFirstToString(onlyFirstToString);

        T result = arrayConverter.convert(defaultArrayType, toBeConvertedValue);
        return result;
    }

    /**
     * Convert.
     *
     * @param <T>
     *            the generic type
     * @param individualArrayElementConverter
     *            the individual array element converter
     * @param toBeConvertedValue
     *            the to be converted value
     * @return the t
     */
    public static final <T> T convert(Converter individualArrayElementConverter,Object toBeConvertedValue){
        //if null will use default 
        //see org.apache.commons.beanutils.converters.AbstractConverter.convertToDefaultType(Class<T>, Object)
        Class<T> defaultArrayType = null;
        return convert(defaultArrayType, individualArrayElementConverter, toBeConvertedValue);

        //  ConvertUtilsBean convertUtils = beanUtilsBean.getConvertUtils();
        //return ConvertUtils.convert(toBeConvertedValue, targetType);
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
        LOGGER.debug("" + ConvertUtil.toInteger("1"));
        LOGGER.debug("" + ConvertUtil.toInteger(null));
        LOGGER.debug("" + ConvertUtil.toInteger(new String[] { "1", "2", "3" }));
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

            if (LOGGER.isDebugEnabled()){
                LOGGER.debug(t.getClass().getCanonicalName());
            }
            linkedList.add(t);
        }

        Serializable l = 6L;

        LOGGER.info("linkedList:{},contains:{},{}", linkedList, l, linkedList.contains(l));
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
        LOGGER.info("" + ConvertUtil.toFloat(BigDecimal.ONE));
    }

}
