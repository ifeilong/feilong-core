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
package com.feilong.core.util.randomutiltest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.feilong.core.util.RandomUtil;
import com.feilong.test.rule.Repeat;
import com.feilong.test.rule.RepeatRule;

import static com.feilong.core.Alphabet.DECIMAL;
import static com.feilong.core.Alphabet.DECIMAL_AND_LETTERS;

/**
 * The Class RandomUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CreateRandomFromStringTest{

    /** The repeat rule. */
    @Rule
    public RepeatRule repeatRule = new RepeatRule();

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
}
