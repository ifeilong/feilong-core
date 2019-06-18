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
package com.feilong.core.bean.beanutiltest;

import static com.feilong.core.date.DateUtil.now;

import org.junit.Test;

import com.feilong.core.bean.BeanUtil;
import com.feilong.core.bean.beanutiltest.entity.AccessExceptionProperty;
import com.feilong.store.member.User;

/**
 * The Class BeanUtilCopyPropertiesTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class CopyPropertiesExceptionLogTest{

    @Test
    @SuppressWarnings("static-method")
    public void testCopyPropertiesNoDateLocaleConverter(){
        User user = new User();
        user.setDate(now());

        User user2 = new User();
        BeanUtil.copyProperties(user2, user, "date");
    }

    @Test
    //@Test(expected = BeanOperationException.class)
    @SuppressWarnings("static-method")
    public void testCopyProperties(){
        AccessExceptionProperty user = new AccessExceptionProperty();

        AccessExceptionProperty user2 = new AccessExceptionProperty();
        BeanUtil.copyProperties(user2, user);
    }
}
