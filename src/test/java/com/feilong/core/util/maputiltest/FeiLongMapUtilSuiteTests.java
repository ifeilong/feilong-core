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
package com.feilong.core.util.maputiltest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ //
                MapUtilToSingleValueMapTest.class,
                MapUtilToArrayValueMapTest.class,

                MapUtilGetSubMapTest.class,
                MapUtilGetSubMapExcludeKeysTest.class,

                MapUtilExtractSubMapTest.class,
                MapUtilExtractSubMapIncludeKeysTest.class,

                MapUtilNewHashMapTest.class,
                MapUtilNewLinkedHashMapTest.class,

                MapUtilInvertMapTest.class,

                MapUtilPutMultiValueTest.class,
                MapUtilPutSumValueTest.class,

                MapUtilRemoveKeysTest.class,
        //
})
public class FeiLongMapUtilSuiteTests{

}
