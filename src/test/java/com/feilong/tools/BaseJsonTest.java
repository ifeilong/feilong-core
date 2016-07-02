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

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.date.DateExtensionUtil.getIntervalForView;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class BaseJsonTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.2
 */
public abstract class BaseJsonTest{

    /** The Constant log. */
    private static final Logger   LOGGER                          = LoggerFactory.getLogger(BaseJsonTest.class);

    protected static final User   USER                            = getUserForJsonTest();

    /** The Constant DEFAULT_USER_FOR_JSON_TEST_JSON. */
    protected static final String USER_JSON_STRING = JsonUtil.format(USER, 0, 0);

    /**
     * Gets the json string.
     * 
     * @return the json string
     */
    private static User getUserForJsonTest(){
        User user = new User();

        user.setPassword("123456");
        user.setId(8L);
        user.setName("feilong");
        user.setDate(new Date());
        user.setMoney(toBigDecimal("99999999.00"));

        user.setLoves(toArray("桔子", "香蕉"));
        user.setUserInfo(new UserInfo(10));

        UserAddress userAddress1 = new UserAddress("上海市闸北区万荣路1188号H座109-118室");
        UserAddress userAddress2 = new UserAddress("上海市闸北区阳城路280弄25号802室(阳城贵都)");

        user.setUserAddresses(toArray(userAddress1, userAddress2));
        user.setUserAddresseList(toList(userAddress1, userAddress2));

        return user;
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
        LOGGER.debug("[{}]{},use time:{}", getType(), times, getIntervalForView(beginDate, new Date()));
    }

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
}
