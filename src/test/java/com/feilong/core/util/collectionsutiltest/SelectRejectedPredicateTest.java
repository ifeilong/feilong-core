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

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ComparatorUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.functors.ComparatorPredicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.apache.commons.collections4.functors.EqualPredicate;
import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;

import static com.feilong.core.bean.ConvertUtil.toList;

/**
 * The Class CollectionsUtilSelectRejectedPredicateTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class SelectRejectedPredicateTest{

    /**
     * Test select rejected predicate.
     */
    @Test
    public void testSelectRejectedPredicate(){
        Predicate<Integer> predicate = new ComparatorPredicate<Integer>(10, ComparatorUtils.<Integer> naturalComparator(), Criterion.LESS);

        List<Integer> result = CollectionsUtil.selectRejected(toList(1, 5, 10, 30, 55, 88, 1, 12, 3), predicate);
        assertThat(result, contains(1, 5, 10, 1, 3));
    }

    /**
     * Test select rejected predicate equal predicate.
     */
    @Test
    public void testSelectRejectedPredicateEqualPredicate(){
        List<Long> list = toList(1L, 1L, 2L, 3L);
        assertThat(CollectionsUtil.selectRejected(list, new EqualPredicate<Long>(1L)), contains(2L, 3L));
    }

    /**
     * Test select rejected predicate null collection.
     */
    @Test
    public void testSelectRejectedPredicateNullCollection(){
        assertEquals(emptyList(), CollectionsUtil.selectRejected(null, new EqualPredicate<Long>(1L)));
    }

    /**
     * Test select rejected predicate empty collection.
     */
    @Test
    public void testSelectRejectedPredicateEmptyCollection(){
        assertEquals(emptyList(), CollectionsUtil.selectRejected(new ArrayList<Long>(), new EqualPredicate<Long>(1L)));
    }
}
