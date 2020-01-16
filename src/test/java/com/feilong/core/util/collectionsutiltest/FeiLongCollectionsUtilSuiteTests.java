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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The Class FeiLongCollectionsUtilSuiteTests.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
@RunWith(Suite.class)
@SuiteClasses({

                NewTest.class,

                AddAllIgnoreNullTest.class,
                AddIgnoreNullOrEmptyTest.class,

                IndexOfTest.class,

                CollectIterableTest.class,
                CollectIterableBeanTypeTest.class,
                CollectIteratorTest.class,

                FindTest.class,
                FindWithMapTest.class,
                FindWithPredicateTest.class,

                GetPropertyValueListTest.class,
                GetPropertyValueListBeanTest.class,
                GetPropertyValueListPropertyNameTest.class,

                GetPropertyValueSetTest.class,
                GetPropertyValueMapTest.class,

                GroupWithPropertyNameTest.class,
                GroupWithPropertyNameAndPredicateTest.class,
                GroupWithTransformerTest.class,
                GroupWithTransformerAndPredicateTest.class,
                GroupOneTest.class,

                SelectPredicateTest.class,
                SelectArrayTest.class,
                SelectCollectionTest.class,

                SelectRejectedArrayTest.class,
                SelectRejectedCollectionTest.class,
                SelectRejectedPredicateTest.class,

                RemoveDuplicateTest.class,
                RemoveDuplicateOnePropertyNameTest.class,
                RemoveDuplicatePropertyNamesTest.class,
                RemoveElementTest.class,
                RemoveAllNullTest.class,
                RemoveAllCollectionTest.class,

                ForEachTest.class,

                SelectRegexStringPredicateTest.class,
                SelectNotNullOrEmptyStringPredicateTest.class,
        //
})
public class FeiLongCollectionsUtilSuiteTests{

}
