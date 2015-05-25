/*
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
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

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.util.StringBuilderUtil;

/**
 * The Class StringBuilderUtilTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-7-11 下午5:10:00
 */
public class StringBuilderUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(StringBuilderUtilTest.class);

    /**
     * Append.
     */
    @Test
    public void append(){
        log.info(StringBuilderUtil.append("1", 2, 5.2f, 6.03d));
    }
}
