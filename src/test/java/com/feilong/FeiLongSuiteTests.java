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
package com.feilong;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.feilong.core.FeiLongCoreTests;
import com.feilong.core.bean.FeiLongBeanSuiteTests;
import com.feilong.core.date.FeiLongDateSuiteTests;
import com.feilong.core.lang.FeiLongLangSuiteTests;
import com.feilong.core.net.FeiLongNetSuiteTests;
import com.feilong.core.text.FeiLongTextSuiteTests;
import com.feilong.core.util.FeiLongUtilSuiteTests;
import com.feilong.tools.slf4j.Slf4jUtilTest;

/**
 * The Class FeiLongSuiteTests.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.7.1
 */
@RunWith(Suite.class)
@SuiteClasses({
                FeiLongCoreTests.class,

                Slf4jUtilTest.class,
                //FeiLongJsonUtilSuiteTests.class,

                FeiLongBeanSuiteTests.class,
                FeiLongDateSuiteTests.class,
                FeiLongLangSuiteTests.class,
                FeiLongTextSuiteTests.class,
                FeiLongNetSuiteTests.class,
                FeiLongUtilSuiteTests.class
        //
})
public class FeiLongSuiteTests{

}
