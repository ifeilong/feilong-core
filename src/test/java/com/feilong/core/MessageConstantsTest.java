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
package com.feilong.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.MessageConstants;

/**
 * The Class MessageConstantsTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-6-1 上午11:50:45
 * @since 1.0
 */
public class MessageConstantsTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(MessageConstantsTest.class);

    /**
     * Test.
     */
    @Test
    public void test(){
        log.debug(MessageConstants.DATE_MINUTE);
    }
}