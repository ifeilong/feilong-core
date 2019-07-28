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
package com.feilong.core.util.comparator;

import static java.util.Collections.emptyList;

import org.apache.commons.collections4.comparators.FixedOrderComparator.UnknownObjectBehavior;
import org.junit.Test;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.14.3
 */
public class ComparatorUtilTest{

    @Test(expected = NullPointerException.class)
    public void test(){
        ComparatorUtil.buildFixedOrderComparator(null);
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testComparatorUtilTestNull(){
        ComparatorUtil.buildFixedOrderComparator(null, UnknownObjectBehavior.AFTER);
    }

    @Test(expected = NullPointerException.class)
    public void testComparatorUtilTestNullUnknownObjectBehavior(){
        ComparatorUtil.buildFixedOrderComparator(emptyList(), null);
    }

}
