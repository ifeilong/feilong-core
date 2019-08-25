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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.feilong.core.util.predicate.beanpredicateutil.ComparatorPredicateComparatorTest;
import com.feilong.core.util.predicate.beanpredicateutil.ComparatorPredicateTest;
import com.feilong.core.util.predicate.beanpredicateutil.ContainsPredicateListTest;
import com.feilong.core.util.predicate.beanpredicateutil.ContainsPredicateTest;
import com.feilong.core.util.predicate.beanpredicateutil.EqualBeanPredicateTest;
import com.feilong.core.util.predicate.beanpredicateutil.EqualIgnoreCasePredicateTest;
import com.feilong.core.util.predicate.beanpredicateutil.EqualMapPredicateTest;
import com.feilong.core.util.predicate.beanpredicateutil.EqualPredicateTest;

/**
 * The Class FeiLongBeanPredicateUtilSuiteTests.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
@RunWith(Suite.class)
@SuiteClasses({ //
                BeanPredicateTest.class,

                EqualPredicateTest.class,
                EqualIgnoreCasePredicateTest.class,
                EqualMapPredicateTest.class,
                EqualBeanPredicateTest.class,

                ContainsStringPredicateParameterizedTest.class,

                ComparatorPredicateComparatorTest.class,
                ComparatorPredicateTest.class,
                ContainsPredicateListTest.class,
                ContainsPredicateTest.class
        //
})
public class FeiLongBeanPredicateUtilSuiteTests{

}
