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

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.SystemUtils.LINE_SEPARATOR;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.StringUtil;
import com.feilong.core.net.URLUtil;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.bean.ConvertUtil.convert;
import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toIterator;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toLocale;
import static com.feilong.core.bean.ConvertUtil.toLong;
import static com.feilong.core.bean.ConvertUtil.toStrings;

/**
 * The Class ConvertUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.3.0
 */
public class ConvertUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertUtilTest.class);

    /**
     * Test to iterator.
     */
    @Test
    public void testToIterator(){
        // *************************逗号分隔的数组********************************
        LOGGER.debug(StringUtils.center("逗号分隔的数组", 60, "*"));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator("1,2")));

        // ************************map*********************************
        LOGGER.debug(StringUtils.center("map", 60, "*"));
        Map<String, String> map = new HashMap<String, String>();

        map.put("a", "1");
        map.put("b", "2");

        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(map)));

        // ***************************array******************************
        LOGGER.debug(StringUtils.center("array", 60, "*"));
        Object[] array = { "5", 8 };
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(array)));
        // ***************************collection******************************
        LOGGER.debug(StringUtils.center("collection", 60, "*"));
        Collection<String> collection = new ArrayList<String>();
        collection.add("aaaa");
        collection.add("nnnnn");

        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(collection)));

        // **********************enumeration***********************************
        LOGGER.debug(StringUtils.center("enumeration", 60, "*"));
        Enumeration<Object> enumeration = new StringTokenizer("this is a test");
        LOGGER.debug(JsonUtil.format(ConvertUtil.toIterator(enumeration)));
    }

    /**
     * TestConvertUtilTest.
     */
    @Test
    public void testConvertUtilTest(){
        int[] i2 = { 1, 2 };
        LOGGER.debug(JsonUtil.format(IteratorUtils.getIterator(i2), 0, 0));

        Iterator<Integer> iterator = toIterator(i2);
        LOGGER.debug(JsonUtil.format(iterator, 0, 0));
    }

    @Test
    public void testToBigDecimal1(){
        BigDecimal a = toBigDecimal("1.000000");
        BigDecimal b = BigDecimal.ONE;
        LOGGER.debug(a.compareTo(b) + "");
        LOGGER.debug(a.equals(b) + "");
    }

    /**
     * Test to strings.
     */
    @Test
    public void testToStrings(){
        LOGGER.debug(JsonUtil.format(toStrings("{5,4, 8,2;8 9_5@3`a}"), 0, 0));

        assertArrayEquals(new String[] { "1", "2", "5" }, ConvertUtil.toStrings(new Integer[] { 1, 2, 5 }));
        assertArrayEquals(null, ConvertUtil.toStrings(null));

        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings("null,1,2,3,\"4\",\'aaaa\'")));
    }

    /**
     * To t test.
     */
    @Test
    public void testConvert(){
        String[] tokenizeToStringArray = StringUtil.tokenizeToStringArray("6", "_");

        LinkedList<Serializable> linkedList = new LinkedList<Serializable>();

        for (String string : tokenizeToStringArray){
            Serializable t = ConvertUtil.convert(string, Serializable.class);
            LOGGER.debug(t.getClass().getCanonicalName());
            linkedList.add(t);
        }

        Serializable l = 6L;

        LOGGER.debug("linkedList:{},contains:{},{}", linkedList, l, linkedList.contains(l));
    }

    /**
     * To t test.
     */
    @Test
    public void testConvert1(){
        String[] strings = null;
        Serializable t = ConvertUtil.toArray(strings, Serializable.class);
        assertEquals(null, t);
    }

    /**
     * Test convert3.
     */
    @Test(expected = NullPointerException.class)
    public void testConvert3(){
        String[] strings = toArray("");
        ConvertUtil.toArray(strings, null);
    }

    /**
     * Test convert2.
     */
    @Test
    public void testConvert2(){
        assertEquals(1, convert("1", Integer.class).intValue());
        assertEquals(1, convert("1", Long.class).intValue());
        assertEquals(0, convert("", Integer.class).intValue());
    }

    /**
     * Test to string2.
     */
    @Test
    public void testToString2(){
        assertEquals("2", ConvertUtil.toString(toArray(2, null, 1, null)));

        ArrayConverter arrayConverter = new ArrayConverter(ArrayUtils.EMPTY_INT_ARRAY.getClass(), new IntegerConverter());
        arrayConverter.setOnlyFirstToString(false);
        arrayConverter.setDelimiter(',');
        arrayConverter.setAllowedChars(new char[] { '.', '-' });
        LOGGER.debug(arrayConverter.convert(String.class, toArray(2, null, 1, null)));
    }

    @Test
    public void testToString3(){
        assertEquals("张飞", ConvertUtil.toString(toList("张飞", "关羽", "", "赵云")));
    }

    @Test
    public void testToString5(){
        assertEquals(null, ConvertUtil.toString(null));
        assertEquals("1", ConvertUtil.toString(1));
        assertEquals("1.0", ConvertUtil.toString(toBigDecimal(1.0)));
        assertEquals("8", ConvertUtil.toString(toLong(8L)));
    }

    /**
     * Test convert array.
     */
    @Test
    public void testConvertArray(){
        String[] int1 = { "2", "1" };
        assertArrayEquals(new Long[] { 2L, 1L }, toArray(int1, Long.class));
    }

    /**
     * Test to string.
     */
    @Test
    public void testToString33(){
        ToStringConfig toStringConfig = new ToStringConfig(",");
        Object[] arrays = { "222", "1111" };
        assertEquals("222,1111", ConvertUtil.toString(arrays, toStringConfig));

        Integer[] array1 = { 2, 1 };
        assertEquals("2,1", ConvertUtil.toString(array1, toStringConfig));

        Integer[] array2 = { 2, 1, null };
        toStringConfig = new ToStringConfig(",");
        toStringConfig.setIsJoinNullOrEmpty(false);
        assertEquals("2,1", ConvertUtil.toString(array2, toStringConfig));

        Integer[] array3 = { 2, null, 1, null };
        toStringConfig = new ToStringConfig(",");
        toStringConfig.setIsJoinNullOrEmpty(false);
        assertEquals("2,1", ConvertUtil.toString(array3, toStringConfig));
    }

    /**
     * Test to string2.
     */
    @Test
    public void testToString22(){
        int[] int1 = { 2, 1 };
        assertEquals("2,1", ConvertUtil.toString(toArray(int1), null));
        assertEquals("2", ConvertUtil.toString(toArray(2), new ToStringConfig(",")));
        assertEquals(",,,", ConvertUtil.toString(toArray(",", ","), new ToStringConfig(",", true)));
    }

    @Test
    public void testToString222(){
        assertEquals("2,", ConvertUtil.toString(toArray(new Integer(2), null), new ToStringConfig(",", true)));
    }

    @Test
    public void testToStringWithOneNullElementArray(){
        String[] ss = { null };
        assertEquals(EMPTY, ConvertUtil.toString(ss, new ToStringConfig(",", true)));
    }

    @Test
    public void testToString221(){
        assertEquals(EMPTY, ConvertUtil.toString((String[]) null, new ToStringConfig(",")));
        assertEquals(EMPTY, ConvertUtil.toString(toArray(), new ToStringConfig(",")));
    }

    /**
     * To array.
     */
    @Test
    public void testToArray0(){
        List<String> list = toList("xinge", "feilong");
        assertArrayEquals(new String[] { "xinge", "feilong" }, toArray(list, String.class));
    }

    /**
     * To array2.
     */
    @Test
    public void testToArray(){
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        LOGGER.debug(JsonUtil.format(toArray(user1, user2)));

    }

    /**
     * Test to array1.
     */
    @Test
    public void testToArray1(){
        assertArrayEquals(new String[] { "xinge", "feilong" }, toArray("xinge", "feilong"));
    }

    /**
     * Test to array4.
     */
    @Test
    public void testToArray4(){
        assertArrayEquals(new String[] {}, ConvertUtil.<String> toArray());
        assertArrayEquals(new Integer[] {}, ConvertUtil.<Integer> toArray());
    }

    @Test
    public void testToArrayNull(){
        Object[] array = ConvertUtil.toArray(null);
        assertArrayEquals(null, array);
    }

    @Test
    public void testToArrayNull1(){
        String[] array = ConvertUtil.toArray((String) null);
        assertArrayEquals(new String[] { null }, array);
    }

    /**
     * To list.
     */
    @Test
    public void testToList(){
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, "a", "b");
        Enumeration<String> enumeration = ConvertUtil.toEnumeration(list);
        assertThat(toList(enumeration), contains("a", "b"));

        enumeration = null;
        assertEquals(emptyList(), toList(enumeration));
    }

    /**
     * Test to enumeration.
     */
    @Test
    public void testToEnumeration(){
        assertEquals(Collections.emptyEnumeration(), ConvertUtil.toEnumeration(null));
    }

    /**
     * To list.
     */
    @Test
    public void testToList2(){
        Set<String> set = new HashSet<String>();
        Collections.addAll(set, "a", "a", "b", "b");
        assertThat(toList(set), hasItems("a", "b", "b", "b"));
    }

    /**
     * To list3.
     */
    @Test
    public void testToList3(){
        LOGGER.debug("{}", toList(toList("a", "a", "b", "b")));
    }

    /**
     * To list.
     */
    @Test
    public void testToList4(){
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        LOGGER.debug(JsonUtil.format(toList(user1, user2)));
        LOGGER.debug(JsonUtil.format(toList((User) null)));
    }

    //****************************************************************************************************

    @Test
    public void testCollectionToString(){
        ToStringConfig toStringConfig = new ToStringConfig(",");
        toStringConfig.setIsJoinNullOrEmpty(false);

        assertEquals(EMPTY, ConvertUtil.toString((List<String>) null, toStringConfig));
        assertEquals(EMPTY, ConvertUtil.toString(toList(), toStringConfig));
    }

    /**
     * 集合转成字符串.
     */
    @Test
    public void testCollectionToString1(){
        List<String> list = toList("feilong", "", "xinge");

        ToStringConfig toStringConfig = new ToStringConfig(",");
        toStringConfig.setIsJoinNullOrEmpty(false);

        assertEquals("feilong,xinge", ConvertUtil.toString(list, toStringConfig));
        assertEquals("feilong@@xinge", ConvertUtil.toString(list, new ToStringConfig("@")));
        assertEquals("feilong,,xinge", ConvertUtil.toString(list, null));
    }

    @Test
    public void testCollectionToString11(){
        List<String> list = toList("feilong", "", "xinge", null);
        assertEquals("feilong@@xinge@", ConvertUtil.toString(list, new ToStringConfig("@")));
    }

    /**
     * 集合转成字符串.
     */
    @Test
    public void testCollectionToString2(){
        List<String> list = toList("飞龙", "小金", "四金", "金金金金");

        ToStringConfig toStringConfig = new ToStringConfig(LINE_SEPARATOR);
        assertEquals(
                        "飞龙" + LINE_SEPARATOR + "小金" + LINE_SEPARATOR + "四金" + LINE_SEPARATOR + "金金金金",
                        ConvertUtil.toString(list, toStringConfig));
    }

    //****************************************************************************************************

    /**
     * Test map to enumeration.
     */
    public void testMapToEnumeration(){
        // Enumeration
        final Map<Object, Object> map = new LinkedHashMap<Object, Object>();
        map.put("jinxin", 1);
        map.put(2, 2);
        map.put("甲", 3);
        map.put(4, 4);
        map.put("jinxin1", 1);
        map.put(21, 2);
        map.put("甲1", 3);
        map.put(41, 4);
        Enumeration<Object> enumeration = ConvertUtil.toEnumeration(map.keySet());
        while (enumeration.hasMoreElements()){
            LOGGER.debug("" + enumeration.nextElement());
        }
    }

    /**
     * Test convert utils test22.
     */
    @Test
    public void testConvertUtilsTest22(){
        LOGGER.debug(ConvertUtils.convert(888.000f));
        LOGGER.debug("{}", ConvertUtils.convert(888.000f, BigDecimal.class));
    }

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

    @Test(expected = UnsupportedOperationException.class)
    public void testToLocale1(){
        toLocale(new User());
    }

    @Test
    public void testToStringsURLs(){
        URL[] urls = {
                       URLUtil.toURL("http://www.exiaoshuo.com/jinyiyexing0/"),
                       URLUtil.toURL("http://www.exiaoshuo.com/jinyiyexing1/"),
                       URLUtil.toURL("http://www.exiaoshuo.com/jinyiyexing2/"),
                       null };

        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings(urls)));

        URL[] urls1 = {};
        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings(urls1)));
        LOGGER.debug(JsonUtil.format(ConvertUtil.toStrings(null)));
    }

    @Test(expected = ConversionException.class)
    public void testToURL(){
        String spec = "C:\\Users\\feilong\\feilong\\train\\新员工\\warmReminder\\20160704141057.html";
        URL url = convert(spec, URL.class); //异常

        //MalformedURLException ConversionException
    }

}
