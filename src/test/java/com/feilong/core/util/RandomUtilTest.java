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
package com.feilong.core.util;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.Alphabet;

/**
 * The Class RandomUtilTest.
 *
 * @author feilong
 * @since 1.0
 */
public class RandomUtilTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomUtilTest.class);

    /**
     * Test create random.
     */
    @Test
    public void testCreateRandom(){
        LOGGER.debug(RandomUtil.createRandom(800) + "");
    }

    /**
     * 创建 random with length.
     */
    @Test
    public void createRandomWithLength(){
        for (int i = 0, j = 100; i < j; ++i){
            LOGGER.debug(RandomUtil.createRandomWithLength(2) + "");
        }
    }

    /**
     * 创建 random with length1.
     */
    @Test
    public void createRandomWithLength1(){
        LOGGER.debug(RandomUtil.createRandomWithLength(18) + "");
    }

    /**
     * Test create random with length.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateRandomWithLength(){
        RandomUtil.createRandomWithLength(-1);
    }

    /**
     * Creates the random with length2.
     */
    @Test
    public void createRandomWithLength2(){
        for (int i = 0, j = 100; i < j; ++i){
            Random rand = new Random();
            LOGGER.debug("" + rand.nextDouble());
        }
    }

    /**
     * Testget random from string.
     */
    @Test
    public void testgetRandomFromString(){
        LOGGER.debug(RandomUtil.createRandomFromString(Alphabet.DECIMAL_AND_LETTERS, 5));
        LOGGER.debug(RandomUtil.createRandomFromString(Alphabet.DECIMAL, 200));
    }

    /**
     * Testget random from string1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testgetRandomFromString1(){
        RandomUtil.createRandomFromString(Alphabet.DECIMAL, 0);
    }

    /**
     * Testget random from string2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testgetRandomFromString2(){
        RandomUtil.createRandomFromString("", 5);
    }

    /**
     * Creates the random from string.
     */
    @Test
    public void createRandomFromString(){
        LOGGER.debug(RandomUtil.createRandomFromString(Alphabet.DECIMAL, 8, 20));
    }

    /**
     * Creates the random.
     */
    @Test
    public void createRandom(){
        LOGGER.debug("" + RandomUtil.createRandom(10, 20));
        LOGGER.debug("" + RandomUtil.createRandom(0, 800));

        assertEquals(800L, RandomUtil.createRandom(800, 800));
    }
}
