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
package com.feilong.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.feilong.core.bean.ConvertUtilTest;
import com.feilong.core.bean.PropertyUtilTest;
import com.feilong.core.date.CalendarUtilTest;
import com.feilong.core.date.DateExtensionUtilTest;
import com.feilong.core.date.DateExtensionUtilTest2;
import com.feilong.core.date.DateUtilIsLeapYearParameterizedTest;
import com.feilong.core.date.DateUtilTest;
import com.feilong.core.lang.ArrayUtilTest;
import com.feilong.core.lang.ClassLoaderUtilTest;
import com.feilong.core.lang.ClassUtilTest;
import com.feilong.core.lang.EnumUtilTest;
import com.feilong.core.lang.NumberUtilTest;
import com.feilong.core.lang.ObjectUtilTest;
import com.feilong.core.lang.StringReplaceParameterizedTest;
import com.feilong.core.lang.StringUtilTest;
import com.feilong.core.lang.reflect.FieldUtilTest;
import com.feilong.core.net.ParamUtilTest;
import com.feilong.core.text.MessageFormatUtilTest;
import com.feilong.core.text.NumberFormatUtilTest;
import com.feilong.core.util.CollectionsUtilTest;
import com.feilong.core.util.MapUtilTest;
import com.feilong.core.util.RandomUtilTest;
import com.feilong.core.util.RegexUtilTest;
import com.feilong.core.util.ResourceBundleUtilTest;
import com.feilong.core.util.StatisticsUtilTest;
import com.feilong.core.util.regexpattern.TelephoneMustAreaCodePatternTest;
import com.feilong.core.util.regexpattern.TelephonePatternTest;
import com.feilong.tools.slf4j.Slf4jUtilTest;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.7.1
 */
@RunWith(Suite.class)
@SuiteClasses({
                AlphabetTest.class,
                ArrayUtilTest.class,
                //BeanUtilTest.class,
                CalendarUtilTest.class,
                CharsetTypeTest.class,
                ClassUtilTest.class,
                ClassLoaderUtilTest.class,
                CollectionsUtilTest.class,
                ConvertUtilTest.class,
                DateUtilTest.class,
                DateExtensionUtilTest.class,
                DateExtensionUtilTest2.class,
                DateUtilIsLeapYearParameterizedTest.class,
                EnumUtilTest.class,
                FieldUtilTest.class,
                //HttpMethodTypeTest.class,
                ObjectUtilTest.class,
                ParamUtilTest.class,
                PropertyUtilTest.class,
                RandomUtilTest.class,
                RegexUtilTest.class,
                ResourceBundleUtilTest.class,
                NumberUtilTest.class,
                NumberFormatUtilTest.class,
                MapUtilTest.class,
                MessageFormatUtilTest.class,
                Slf4jUtilTest.class,
                StatisticsUtilTest.class,
                StringUtilTest.class,
                StringReplaceParameterizedTest.class,
                TimeIntervalTest.class,
                ValidatorTest.class,
                TelephonePatternTest.class,
                TelephoneMustAreaCodePatternTest.class })
public class FeiLongSuiteTests{

}
