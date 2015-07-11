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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.date.DateExtensionUtil;
import com.feilong.core.tools.jsonlib.JsonUtil;
import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;

/**
 * The Class BaseJsonTest.
 *
 * @author feilong
 * @version 1.2.2 2015年7月8日 上午1:54:27
 * @since 1.2.2
 */
public abstract class BaseJsonTest{

    /** The Constant log. */
    private static final Logger   LOGGER                          = LoggerFactory.getLogger(BaseJsonTest.class);

    /** The Constant DEFAULT_USER_FOR_JSON_TEST. */
    protected static final User   DEFAULT_USER_FOR_JSON_TEST      = getUserForJsonTest();

    /** The Constant DEFAULT_USER_FOR_JSON_TEST_JSON. */
    protected static final String DEFAULT_USER_FOR_JSON_TEST_JSON = JsonUtil.format(DEFAULT_USER_FOR_JSON_TEST);

    /**
     * Gets the json string.
     * 
     * @return the json string
     */
    private static User getUserForJsonTest(){
        User user = new User();

        user.setId(8L);
        user.setName("feilong");
        user.setDate(new Date());
        user.setMoney(new BigDecimal("99999999.00"));

        String[] loves = { "桔子", "香蕉" };
        user.setLoves(loves);

        UserInfo userInfo = new UserInfo();

        userInfo.setAge(10);
        user.setUserInfo(userInfo);

        UserAddress userAddress1 = new UserAddress();
        userAddress1.setAddress("上海市闸北区万荣路1188号H座109-118室");

        UserAddress userAddress2 = new UserAddress();
        userAddress2.setAddress("上海市闸北区阳城路280弄25号802室(阳城贵都)");

        UserAddress[] userAddresses = { userAddress1, userAddress2 };
        user.setUserAddresses(userAddresses);

        List<UserAddress> userAddresseList = new ArrayList<UserAddress>();
        userAddresseList.add(userAddress1);
        userAddresseList.add(userAddress2);
        user.setUserAddresseList(userAddresseList);

        return user;
    }

    protected void testPerformance(){
        User user = DEFAULT_USER_FOR_JSON_TEST;

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
        String type = getType();
        Date beginDate = new Date();
        for (int i = 0; i < times; ++i){
            performanceMethod(user);

        }
        Date endDate = new Date();
        LOGGER.info("[{}]{},use time:{}", type, times, DateExtensionUtil.getIntervalForView(beginDate, endDate));
    }

    protected abstract void performanceMethod(User user);

    protected abstract String getType();

}
