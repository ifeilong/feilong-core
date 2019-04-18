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
package com.feilong.core.util.collectionsutiltest;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.CollectionsUtil.select;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.feilong.core.util.predicate.NotNullOrEmptyStringPredicate;

/**
 * The Class CollectionsUtilSelectPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SelectNotNullOrEmptyStringPredicateTest{

    @Test
    public void testSelectPredicateTest(){
        String tel = "15002841618";

        //---------------------------------------------------------------
        List<String> list = toList(//
                        null,
                        tel,
                        tel,
                        null,
                        "飞龙",
                        "",
                        "jinxin",
                        tel,
                        tel);

        List<String> select = select(list, NotNullOrEmptyStringPredicate.INSTANCE);
        assertEquals(6, select.size());
        assertThat(
                        select,
                        allOf(//
                                        hasItem(tel),

                                        hasItem("jinxin"),
                                        hasItem("飞龙"),

                                        not(hasItem("")),
                                        not(hasItem((String) null))
                        //
                        ));
    }

}
