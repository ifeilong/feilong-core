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
import java.util.List;

import com.feilong.test.User;
import com.feilong.test.UserAddress;
import com.feilong.test.UserInfo;

/**
 *
 * @author feilong
 * @version 1.2.2 2015年7月8日 上午1:54:27
 * @since 1.2.2
 */
public class BaseJsonTest{

    protected String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";

    /**
     * Gets the json string.
     * 
     * @return the json string
     */
    protected User getUserForJsonTest(){
        User user = new User();

        user.setId(8L);
        user.setName("feilong");

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
}
