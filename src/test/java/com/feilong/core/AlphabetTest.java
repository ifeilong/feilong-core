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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class AlphabetTest.
 *
 * @author feilong
 * @version 1.5.3 2016年3月31日 上午12:42:43
 * @since 1.5.3
 */
public class AlphabetTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AlphabetTest.class);

    /**
     * Test.
     */
    @Test
    public final void test(){
        assertEquals("23456789abcdefghijkmnpqrstuvwxyz", Alphabet.DECIMAL_AND_LOWERCASE_LETTERS_DISTINGUISHABLE);
    }
}
