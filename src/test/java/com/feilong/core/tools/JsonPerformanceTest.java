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
package com.feilong.core.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DateExtensionUtil;
import com.feilong.test.User;

/**
 *
 * @author feilong
 * @version 1.2.2 2015年7月8日 上午3:31:38
 * @since 1.2.2
 */
public class JsonPerformanceTest extends BaseJsonTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonPerformanceTest.class);

    @Test
    public void testJsonUtilTest2(){
        User user = getUserForJsonTest();

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(10);
        list.add(100);
        list.add(1000);
        list.add(10000);
        list.add(100000);
        list.add(1000000);

        for (Integer times : list){
            performanceTest(user, times);
        }
    }

    private void performanceTest(User user,int times){
        //String type = "jackson2 2";
        String type = "json-lib";
        Date beginDate = new Date();
        for (int i = 0; i < times; ++i){
            //JsonUtil.toJSON(userForJsonTest, null);
            //com.feilong.core.tools.jackson2.JsonUtil.writeValueAsString(user);
            //com.feilong.core.tools.jackson2.JsonUtil.toJson(user);
        }
        Date endDate = new Date();
        LOGGER.info("[{}]{},use time:{}", type, times, DateExtensionUtil.getIntervalForView(beginDate, endDate));
    }
}
