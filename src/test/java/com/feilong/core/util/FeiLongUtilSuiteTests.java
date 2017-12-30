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
package com.feilong.core.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.feilong.core.util.aggregateutiltest.FeiLongAggregateUtilSuiteTests;
import com.feilong.core.util.closure.FeiLongClosureSuiteTests;
import com.feilong.core.util.collectionsutiltest.FeiLongCollectionsUtilSuiteTests;
import com.feilong.core.util.comparator.FeiLongComparatorSuiteTests;
import com.feilong.core.util.enumerationutiltest.EnumerationUtilParameterizedTest;
import com.feilong.core.util.equator.IgnoreCaseEquatorTest;
import com.feilong.core.util.maputiltest.FeiLongMapUtilSuiteTests;
import com.feilong.core.util.predicate.FeiLongBeanPredicateUtilSuiteTests;
import com.feilong.core.util.randomutiltest.FeiLongRandomUtilSuiteTests;
import com.feilong.core.util.regexutiltest.FeiLongRegexUtilSuiteTests;
import com.feilong.core.util.resourcebundleutiltest.FeiLongResourceBundleUtilSuiteTests;
import com.feilong.core.util.sortutiltest.FeiLongSortUtilSuiteTests;
import com.feilong.core.util.transformer.FeiLongTransformerSuiteTests;

/**
 * The Class FeiLongUtilSuiteTests.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
@RunWith(Suite.class)
@SuiteClasses({ //
                EnumerationUtilParameterizedTest.class,
                IgnoreCaseEquatorTest.class,

                FeiLongBeanPredicateUtilSuiteTests.class,

                FeiLongRandomUtilSuiteTests.class,
                FeiLongResourceBundleUtilSuiteTests.class,
                FeiLongAggregateUtilSuiteTests.class,
                FeiLongRegexUtilSuiteTests.class,
                FeiLongMapUtilSuiteTests.class,
                FeiLongCollectionsUtilSuiteTests.class,
                FeiLongSortUtilSuiteTests.class,

                FeiLongComparatorSuiteTests.class,

                FeiLongTransformerSuiteTests.class,
                FeiLongClosureSuiteTests.class,
        //
})
public class FeiLongUtilSuiteTests{

}
