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
package com.feilong.core.entity;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.entity.Pager;
import com.feilong.core.tools.json.JsonUtil;

/**
 * The Class PagerTest.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 May 3, 2014 2:41:11 PM
 */
public class PagerTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(PagerTest.class);

    /**
     * Test.
     */
    @Test
    public final void test(){
        Pager pager = new Pager(2, 10, 10000);
        pager.setMaxShowPageNo(-2);
        if (log.isInfoEnabled()){
            log.info(JsonUtil.format(pager));
        }
    }
}