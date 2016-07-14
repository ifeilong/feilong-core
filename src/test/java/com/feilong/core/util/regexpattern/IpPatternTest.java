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
package com.feilong.core.util.regexpattern;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.core.AbstractBooleanParameterizedTest;
import com.feilong.core.RegexPattern;
import com.feilong.core.util.RegexUtil;

/**
 * 
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public class IpPatternTest extends AbstractBooleanParameterizedTest<String, Boolean>{

    /**
     * Data.
     *
     * @return the collection
     */
    @Parameters(name = "index:{index}: matches({0})={1}")
    public static Iterable<Object[]> data(){
        String[] valids = { "127.0.0.1" };

        String[] invalids = { "venusdrogon@163.com", "127.0.0.*", "327.0.0.1" };

        return toList(valids, invalids);
    }

    @Test
    public void matches(){
        assertEquals(expectedValue, RegexUtil.matches(RegexPattern.IP, input));
    }

}