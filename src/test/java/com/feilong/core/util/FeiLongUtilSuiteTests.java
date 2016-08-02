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

import com.feilong.core.util.regexpattern.FeiLongRegexPatternSuiteTests;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.3
 */
@RunWith(Suite.class)
@SuiteClasses({
                AggregateUtilTest.class,
                CollectionsUtilTest.class,
                MapUtilTest.class,
                RandomUtilTest.class,
                RegexUtilTest.class,
                ResourceBundleUtilTest.class,
                SortUtilTest.class,
                FeiLongRegexPatternSuiteTests.class })
public class FeiLongUtilSuiteTests{

}
