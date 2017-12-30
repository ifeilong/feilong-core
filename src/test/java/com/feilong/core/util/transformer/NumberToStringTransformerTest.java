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
package com.feilong.core.util.transformer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.feilong.core.NumberPattern;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.7
 */
public class NumberToStringTransformerTest{

    @Test
    public void testTransform1(){
        assertEquals("1235", new NumberToStringTransformer(NumberPattern.NO_SCALE).transform(1234.5d));
    }

    @Test
    public void testTransform(){
        assertEquals(null, new NumberToStringTransformer(NumberPattern.NO_SCALE).transform(null));
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testNumberToStringTransformerTestNull(){
        new NumberToStringTransformer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberToStringTransformerTestEmpty(){
        new NumberToStringTransformer("");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberToStringTransformerTestBlank(){
        new NumberToStringTransformer(" ");

    }
}
