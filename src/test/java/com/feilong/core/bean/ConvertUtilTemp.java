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

import java.util.Locale;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class ConvertUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.3.0
 */
public class ConvertUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertUtilTemp.class);

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

        return arrayConverter.convert(defaultArrayType, toBeConvertedValue);
    }

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
     * @since 1.2.2
     */
    private <T> T convert(Class<T> defaultArrayType,Converter individualArrayElementConverter,Object toBeConvertedValue){
        char[] allowedChars = new char[] { ',', '-' };
        char delimiter = ';';
        boolean onlyFirstToString = true;

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
     * To long array.
     *
     * @param <T>
     *            the generic type
     * @param defaultType
     *            the default type
     * @param stringA
     *            the string a
     * @return the t
     * @since 1.2.2
     */
    private <T> T toLongArray(Class<T> defaultType,String stringA){
        LongConverter elementConverter = new LongConverter(new Long(0L));
        elementConverter.setPattern("#,###");
        elementConverter.setLocale(Locale.US);

        return convert(defaultType, elementConverter, stringA);
    }

    /**
     * Test Converting using the IntegerConverter as the component Converter.
     */
    @Test
    public void testComponentIntegerConverter(){
        String stringA = "1,111; 2,222; 3,333; 4,444";

        Class<Long[]> defaultType = Long[].class;
        Long[] result = toLongArray(defaultType, stringA);

        System.out.println(JsonUtil.format(result));//TODO:remove
    }
}
