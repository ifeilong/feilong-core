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

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.feilong.test.rule.Repeat;
import com.feilong.test.rule.RepeatRule;

import static com.feilong.core.Alphabet.DECIMAL;
import static com.feilong.core.Alphabet.DECIMAL_AND_LETTERS;

/**
 * The Class RandomUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class RandomUtilTest{

    /** The repeat rule. */
    @Rule
    public RepeatRule repeatRule = new RepeatRule();

    //****************com.feilong.core.util.RandomUtil.createRandom(Number)***************************************

    /**
     * Test create random.
     */
    @Test
    @Repeat(20000)
    public void testCreateRandom(){
        assertThat(RandomUtil.createRandom(800), allOf(greaterThanOrEqualTo(0L), lessThan(800L)));
    }

    /**
     * Test create random 1.
     */
    @Test(expected = NullPointerException.class)
    public void testCreateRandom1(){
        RandomUtil.createRandom(null);
    }

    /**
     * Test create random null pointer exception 1.
     */
    //**************com.feilong.core.util.RandomUtil.createRandom(Number, Number)***********************************
    @Test(expected = NullPointerException.class)
    public void testCreateRandomNullPointerException1(){
        RandomUtil.createRandom(null, 5);
    }

    /**
     * Test create random null pointer exception 2.
     */
    @Test(expected = NullPointerException.class)
    public void testCreateRandomNullPointerException2(){
        RandomUtil.createRandom(5, null);
    }

    /**
     * Test create random null pointer exception 22.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateRandomNullPointerException22(){
        RandomUtil.createRandom(5, 2);
    }

    /**
     * Creates the random.
     */
    @Test
    @Repeat(20000)
    public void testCreateRandom2(){
        assertThat(RandomUtil.createRandom(10, 20), allOf(greaterThanOrEqualTo(10L), lessThan(20L)));
        assertThat(RandomUtil.createRandom(0, 800), allOf(greaterThanOrEqualTo(0L), lessThan(800L)));
    }

    /**
     * Test create random 3.
     */
    @Test
    public void testCreateRandom3(){
        assertEquals(800L, RandomUtil.createRandom(800, 800));
    }

    //********************com.feilong.core.util.RandomUtil.createRandomWithLength(int)***************************

    /**
     * Test create random with length 0.
     */
    @Test
    @Repeat(20000)
    public void testCreateRandomWithLength0(){
        assertThat(RandomUtil.createRandomWithLength(1), allOf(greaterThan(0L), lessThan(10L)));
    }

    /**
     * Test create random with length 1.
     */
    @Test
    @Repeat(20000)
    public void testCreateRandomWithLength1(){
        assertThat(RandomUtil.createRandomWithLength(2), allOf(greaterThanOrEqualTo(10L), lessThan(100L)));
    }

    /**
     * Test create random with length 2.
     */
    @Test
    @Repeat(20000)
    public void testCreateRandomWithLength2(){
        assertThat(RandomUtil.createRandomWithLength(18), allOf(greaterThanOrEqualTo(100000000000000000L), lessThan(1000000000000000000L)));
    }

    /**
     * Test create random with length.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateRandomWithLength(){
        RandomUtil.createRandomWithLength(-1);
    }

    /**
     * Test create random with length zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateRandomWithLengthZero(){
        RandomUtil.createRandomWithLength(0);
    }

    //********************com.feilong.core.util.RandomUtil.createRandomFromString(String, int)****************

    /**
     * Test get random from string null.
     */
    @Test(expected = NullPointerException.class)
    public void testGetRandomFromStringNull(){
        RandomUtil.createRandomFromString(null, 4);
    }

    /**
     * Test get random from string empty.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromStringEmpty(){
        RandomUtil.createRandomFromString("", 5);
    }

    /**
     * Test get random from string blank.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromStringBlank(){
        RandomUtil.createRandomFromString(" ", 5);
    }

    /**
     * Test get random from string 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromString1(){
        RandomUtil.createRandomFromString(DECIMAL, 0);
    }

    /**
     * Test get random from string 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromString2(){
        RandomUtil.createRandomFromString(DECIMAL, -1);
    }

    /**
     * Testget random from string.
     */
    @Test
    @Repeat(20000)
    public void testGetRandomFromString(){
        assertThat(RandomUtil.createRandomFromString(DECIMAL_AND_LETTERS, 5).length(), equalTo(5));
    }

    /**
     * Test get random from string 3.
     */
    @Test
    @Repeat(20000)
    public void testGetRandomFromString3(){
        assertThat(RandomUtil.createRandomFromString(DECIMAL, 200).length(), equalTo(200));
    }

    //**************com.feilong.core.util.RandomUtil.createRandomFromString(String, int, int)***********

    /**
     * Test get random from string null 3.
     */
    @Test(expected = NullPointerException.class)
    public void testGetRandomFromStringNull3(){
        RandomUtil.createRandomFromString(null, 4, 6);
    }

    /**
     * Test get random from string empty 3.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromStringEmpty3(){
        RandomUtil.createRandomFromString("", 5, 8);
    }

    /**
     * Test get random from string blank 3.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromStringBlank3(){
        RandomUtil.createRandomFromString(" ", 5, 8);
    }

    /**
     * Test get random from string 13.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromString13(){
        RandomUtil.createRandomFromString(DECIMAL, 0, 1);
    }

    /**
     * Test get random from string 33.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromString33(){
        RandomUtil.createRandomFromString(DECIMAL, -1, 8);
    }

    /**
     * Test get random from string 333.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomFromString333(){
        RandomUtil.createRandomFromString(DECIMAL, 8, 6);
    }

    /**
     * Test create random from string.
     */
    @Test
    @Repeat(20000)
    public void testCreateRandomFromString(){
        assertThat(RandomUtil.createRandomFromString(DECIMAL, 8, 20).length(), allOf(greaterThanOrEqualTo(8), lessThan(20)));
    }

}
