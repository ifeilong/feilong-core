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
package com.feilong.core.util.predicate;

import static com.feilong.core.bean.ConvertUtil.toList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.feilong.test.Abstract2ParamsAndResultParameterizedTest;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.14.3
 */
public class ContainsStringPredicateParameterizedTest extends Abstract2ParamsAndResultParameterizedTest<String, String, Boolean>{

    @Parameters(name = "index:{index}: new ContainsStringPredicate({1}).evaluate({0})={2}")
    public static Iterable<Object[]> data(){
        Object[][] objects = new Object[][] { //
                                              {
                                                "<alipay><is_success>F</is_success><error>TRADE_NOT_EXIST</error></alipay>",
                                                "TRADE_NOT_EXIST",
                                                true },
                                              { "<alipay><is_success>F</is_success><error></error></alipay>", "TRADE_NOT_EXIST", false },
                                              { "", "TRADE_NOT_EXIST", false },
                                              { null, "TRADE_NOT_EXIST", false },
                //
        };
        return toList(objects);
    }

    @Test
    public void test(){
        assertEquals(expectedValue, new ContainsStringPredicate(input2).evaluate(input1));
    }

}
