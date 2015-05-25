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
package com.feilong.core.util;

import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.ConfigConstants;
import com.feilong.core.util.RandomUtil;

/**
 * The Class RandomUtilTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-7-25 下午04:24:50
 * @since 1.0
 */
public class RandomUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(RandomUtilTest.class);

    /**
     * {@link com.feilong.core.util.RandomUtil#createRandom(Number)} 的测试方法。
     */
    @Test
    public final void testCreateRandom(){
        log.info(RandomUtil.createRandom(8) + "");
    }

    /**
     * {@link com.feilong.core.util.RandomUtil#createRandomWithLength(int)} 的测试方法。
     */
    @Test
    public final void createRandomWithLength(){
        for (int i = 0, j = 100; i < j; ++i){
            log.info(RandomUtil.createRandomWithLength(2) + "");
        }
    }

    /**
     * Creates the random with length2.
     */
    @Test
    public final void createRandomWithLength2(){
        for (int i = 0, j = 100; i < j; ++i){
            Random rand = new Random();
            log.info("" + rand.nextDouble());
        }
    }

    /**
     * Testget random from string.
     */
    @Test
    public final void testgetRandomFromString(){
        log.info(RandomUtil.createRandomFromString(ConfigConstants.NUMBERSANDALLLETTERS, 5));
        log.info(RandomUtil.createRandomFromString(ConfigConstants.NUMBERS, 200));
    }

    /**
     * Testget random from string1.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testgetRandomFromString1(){
        log.info(RandomUtil.createRandomFromString(ConfigConstants.NUMBERS, 0));
    }

    /**
     * Testget random from string2.
     */
    @Test(expected = NullPointerException.class)
    public final void testgetRandomFromString2(){
        log.info(RandomUtil.createRandomFromString("", 5));
    }

    /**
     * Creates the random from string.
     */
    @Test
    public final void createRandomFromString(){
        log.info(RandomUtil.createRandomFromString(ConfigConstants.NUMBERS, 8, 20));
    }

    /**
     * Creates the random.
     */
    @Test
    public final void createRandom(){
        log.info(RandomUtil.createRandom(10, 12) + "");
    }
}
