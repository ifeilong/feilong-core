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
package com.feilong.core.date;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试 星座.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-1-5 上午11:49:13
 */
@RunWith(Parameterized.class)
public class ConstellationUtilTest extends TestCase{

    /** The Constant LOGGER. */
    private static final Logger     LOGGER = LoggerFactory.getLogger(ConstellationUtilTest.class);

    /** The f expected. */
    private final ConstellationType fExpected;

    /** The f input. */
    private final String            birthday;

    /**
     * The Constructor.
     *
     * @param expected
     *            the expected
     * @param birthday
     *            the birthday
     */
    public ConstellationUtilTest(ConstellationType expected, String birthday){
        this.birthday = birthday;
        this.fExpected = expected;
    }

    /**
     * Data.
     *
     * @return the collection
     */
    @Parameters
    public static Collection<?> data(){
        return Arrays.asList(new Object[][] {
                { ConstellationType.CAPRICORN, "1986-12-22" },
                { ConstellationType.CAPRICORN, "1986-12-25" },
                { ConstellationType.AQUARIUS, "1986-01-20" },
                { ConstellationType.AQUARIUS, "1986-01-21" },
                { ConstellationType.AQUARIUS, "1986-02-18" },
                { ConstellationType.PISCES, "1986-02-19" },
                { ConstellationType.PISCES, "1986-02-20" },
                { ConstellationType.PISCES, "1986-02-26" },
                { ConstellationType.PISCES, "1986-03-20" },
                { ConstellationType.ARIES, "1986-03-21" },
                { ConstellationType.ARIES, "1986-03-25" },
                { ConstellationType.TAURUS, "1986-04-20" },
                { ConstellationType.TAURUS, "1986-04-21" },
                { ConstellationType.TAURUS, "1986-05-10" },
                { ConstellationType.GEMINI, "1986-05-21" },
                { ConstellationType.GEMINI, "1986-05-22" },
                { ConstellationType.GEMINI, "1986-05-25" },
                { ConstellationType.GEMINI, "1986-06-21" },
                { ConstellationType.CANCER, "1986-06-22" },
                { ConstellationType.CANCER, "1986-06-28" },
                { ConstellationType.CANCER, "1986-07-22" },
                { ConstellationType.LEO, "1986-07-23" },
                { ConstellationType.LEO, "1986-07-30" },
                { ConstellationType.VIRGO, "1986-08-23" },
                { ConstellationType.VIRGO, "1986-08-24" },
                { ConstellationType.VIRGO, "1986-09-21" },
                { ConstellationType.LIBRA, "1986-09-23" },
                { ConstellationType.LIBRA, "1986-09-24" },
                { ConstellationType.LIBRA, "1986-10-21" },
                { ConstellationType.LIBRA, "1986-10-23" },
                { ConstellationType.SCORPIO, "1986-10-24" },
                { ConstellationType.SCORPIO, "1986-11-21" },
                { ConstellationType.SCORPIO, "1986-11-22" },
                { ConstellationType.SAGITTARIUS, "1986-11-23" },
                { ConstellationType.SAGITTARIUS, "1986-11-28" },
                { ConstellationType.SAGITTARIUS, "1986-12-21" } });
    }

    /**
     * Test get constellation type.
     */
    @Test
    public void testGetConstellationType(){
        String testBirthday = "1984-07-25";
        ConstellationType constellationType = ConstellationUtil.getConstellationType(testBirthday);
        LOGGER.info(constellationType.getChineseName());
    }

    /**
     * Test.
     */
    @Test
    public void test(){
        assertEquals(fExpected, ConstellationUtil.getConstellationType(birthday));
    }

    /**
     * Test calculate constellation.
     */
    @Test
    @Ignore
    public void testCalculateConstellation(){
        assertEquals(ConstellationType.CAPRICORN, ConstellationUtil.getConstellationType("1986-12-22"));
        assertEquals(ConstellationType.CAPRICORN, ConstellationUtil.getConstellationType("1986-12-25"));
        assertEquals(ConstellationType.AQUARIUS, ConstellationUtil.getConstellationType("1986-01-20"));
        assertEquals(ConstellationType.AQUARIUS, ConstellationUtil.getConstellationType("1986-01-21"));
        assertEquals(ConstellationType.AQUARIUS, ConstellationUtil.getConstellationType("1986-02-18"));
        assertEquals(ConstellationType.PISCES, ConstellationUtil.getConstellationType("1986-02-19"));
        assertEquals(ConstellationType.PISCES, ConstellationUtil.getConstellationType("1986-02-20"));
        assertEquals(ConstellationType.PISCES, ConstellationUtil.getConstellationType("1986-02-26"));
        assertEquals(ConstellationType.PISCES, ConstellationUtil.getConstellationType("1986-03-20"));
        assertEquals(ConstellationType.ARIES, ConstellationUtil.getConstellationType("1986-03-21"));
        assertEquals(ConstellationType.ARIES, ConstellationUtil.getConstellationType("1986-03-25"));
        assertEquals(ConstellationType.TAURUS, ConstellationUtil.getConstellationType("1986-04-20"));
        assertEquals(ConstellationType.TAURUS, ConstellationUtil.getConstellationType("1986-04-21"));
        assertEquals(ConstellationType.TAURUS, ConstellationUtil.getConstellationType("1986-05-10"));
        assertEquals(ConstellationType.GEMINI, ConstellationUtil.getConstellationType("1986-05-21"));
        assertEquals(ConstellationType.GEMINI, ConstellationUtil.getConstellationType("1986-05-22"));
        assertEquals(ConstellationType.GEMINI, ConstellationUtil.getConstellationType("1986-05-25"));
        assertEquals(ConstellationType.GEMINI, ConstellationUtil.getConstellationType("1986-06-21"));
        assertEquals(ConstellationType.CANCER, ConstellationUtil.getConstellationType("1986-06-22"));
        assertEquals(ConstellationType.CANCER, ConstellationUtil.getConstellationType("1986-06-28"));
        assertEquals(ConstellationType.CANCER, ConstellationUtil.getConstellationType("1986-07-22"));
        assertEquals(ConstellationType.LEO, ConstellationUtil.getConstellationType("1986-07-23"));
        assertEquals(ConstellationType.LEO, ConstellationUtil.getConstellationType("1986-07-30"));
        assertEquals(ConstellationType.VIRGO, ConstellationUtil.getConstellationType("1986-08-23"));
        assertEquals(ConstellationType.VIRGO, ConstellationUtil.getConstellationType("1986-08-24"));
        assertEquals(ConstellationType.VIRGO, ConstellationUtil.getConstellationType("1986-09-21"));
        assertEquals(ConstellationType.LIBRA, ConstellationUtil.getConstellationType("1986-09-23"));
        assertEquals(ConstellationType.LIBRA, ConstellationUtil.getConstellationType("1986-09-24"));
        assertEquals(ConstellationType.LIBRA, ConstellationUtil.getConstellationType("1986-10-21"));
        assertEquals(ConstellationType.LIBRA, ConstellationUtil.getConstellationType("1986-10-23"));
        assertEquals(ConstellationType.SCORPIO, ConstellationUtil.getConstellationType("1986-10-24"));
        assertEquals(ConstellationType.SCORPIO, ConstellationUtil.getConstellationType("1986-11-21"));
        assertEquals(ConstellationType.SCORPIO, ConstellationUtil.getConstellationType("1986-11-22"));
        assertEquals(ConstellationType.SAGITTARIUS, ConstellationUtil.getConstellationType("1986-11-23"));
        assertEquals(ConstellationType.SAGITTARIUS, ConstellationUtil.getConstellationType("1986-11-28"));
        assertEquals(ConstellationType.SAGITTARIUS, ConstellationUtil.getConstellationType("1986-12-21"));
    }
}