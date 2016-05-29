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
package com.feilong.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * The Class AlphabetTest.
 *
 * @author feilong
 * @since 1.5.3
 */
public class AlphabetTest{

    /**
     * Test.
     */
    @Test
    public void test(){
        assertEquals("23456789abcdefghijkmnpqrstuvwxyz", Alphabet.DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE);
    }

    /**
     * Test1.
     */
    @Test
    public void test1(){
        assertSame(Alphabet.DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE, Alphabet.DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE);
    }

    /**
     * Test2.
     */
    @Test
    public void test2(){
        assertNotSame(
                        new StringBuffer() //
                                        .append("23456789") //
                                        .append("abcdefghijk") //
                                        .append("mn") //
                                        .append("pqrstuvwxyz") //
                                        .toString(),
                        new StringBuffer() //
                                        .append("23456789") //
                                        .append("abcdefghijk") //
                                        .append("mn") //
                                        .append("pqrstuvwxyz") //
                                        .toString());
    }
}
