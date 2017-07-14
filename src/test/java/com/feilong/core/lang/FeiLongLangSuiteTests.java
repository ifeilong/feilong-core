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
package com.feilong.core.lang;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.feilong.core.lang.annotation.FeiLongAnnotationToStringBuilderSuiteTests;
import com.feilong.core.lang.arrayutiltest.FeiLongArrayUtilSuiteTests;
import com.feilong.core.lang.classloaderutiltest.FeiLongClassLoaderUtilSuiteTests;
import com.feilong.core.lang.classutiltest.FeiLongClassUtilSuiteTests;
import com.feilong.core.lang.enumutiltest.FeiLongEnumUtilSuiteTests;
import com.feilong.core.lang.numberutiltest.FeiLongNumberUtilSuiteTests;
import com.feilong.core.lang.objectutiltest.FeiLongObjectUtilSuiteTests;
import com.feilong.core.lang.reflect.FeiLongReflectSuiteTests;
import com.feilong.core.lang.stringutiltest.FeiLongStringUtilSuiteTests;
import com.feilong.core.lang.systemutiltest.FeiLongSystemUtilSuiteTests;
import com.feilong.core.lang.threadutiltest.FeiLongThreadUtilSuiteTests;

/**
 * The Class FeiLongLangSuiteTests.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.3
 */
@RunWith(Suite.class)
@SuiteClasses({
                FeiLongArrayUtilSuiteTests.class,

                FeiLongClassLoaderUtilSuiteTests.class,

                FeiLongEnumUtilSuiteTests.class,

                FeiLongStringUtilSuiteTests.class,
                FeiLongReflectSuiteTests.class,
                FeiLongNumberUtilSuiteTests.class,
                FeiLongObjectUtilSuiteTests.class,
                FeiLongClassUtilSuiteTests.class,

                FeiLongThreadUtilSuiteTests.class,

                FeiLongSystemUtilSuiteTests.class,
                FeiLongAnnotationToStringBuilderSuiteTests.class,
        //
})
public class FeiLongLangSuiteTests{

}
