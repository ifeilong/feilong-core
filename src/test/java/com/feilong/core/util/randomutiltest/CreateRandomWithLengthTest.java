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

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.feilong.core.util.RandomUtil;
import com.feilong.test.rule.Repeat;
import com.feilong.test.rule.RepeatRule;

/**
 * The Class RandomUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CreateRandomWithLengthTest{

    /** The repeat rule. */
    @Rule
    public RepeatRule repeatRule = new RepeatRule();
    //********************com.feilong.core.util.RandomUtil.createRandomWithLength(int)***************************

    /**
     * Test create random with length 0.
     */
    @Test
    @Repeat(20000)
    @SuppressWarnings("static-method")
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

}
