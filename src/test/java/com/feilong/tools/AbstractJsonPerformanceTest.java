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
package com.feilong.tools;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.User;

import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateExtensionUtil.getIntervalForView;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.0
 */
public abstract class AbstractJsonPerformanceTest extends AbstractJsonTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJsonPerformanceTest.class);

    /**
     * Performance method.
     *
     * @param user
     *            the user
     */
    protected abstract void performanceMethod(User user);

    /**
     * 获得 type.
     *
     * @return the type
     */
    protected abstract String getType();

    /**
     * Performance test.
     *
     * @param user
     *            the user
     * @param times
     *            the times
     */
    private void performanceTest(User user,int times){
        Date beginDate = new Date();
        for (int i = 0; i < times; ++i){
            performanceMethod(user);
        }
        LOGGER.debug("[{}]{},use time:{}", getType(), times, getIntervalForView(beginDate));
    }

    /**
     * Test performance.
     */
    protected void testPerformance(){
        List<Integer> list = toList(1, 10, 100, 1000, 10000, 100000, 1000000);
        for (Integer times : list){
            performanceTest(USER, times);
        }
    }
}
