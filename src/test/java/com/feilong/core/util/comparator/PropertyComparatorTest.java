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
package com.feilong.core.util.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.tools.json.JsonUtil;
import com.feilong.test.User;

/**
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.2.1 2015年6月26日 下午8:28:21
 * @since 1.2.1
 */
public class PropertyComparatorTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyComparatorTest.class);

    @Test
    public final void testPropertyComparator(){
        List<User> list = new ArrayList<User>();
        Long id = null;
        list.add(new User(id));
        list.add(new User(12L));
        list.add(new User(2L));
        list.add(new User(5L));
        list.add(null);
        list.add(new User(1L));
        list.add(new User(id));
        Collections.sort(list, new PropertyComparator<User>("id"));
        LOGGER.debug(JsonUtil.format(list));
    }

    @Test
    public final void testPropertyComparator2(){
        Set<User> list = new TreeSet<User>(new PropertyComparator<User>("id"));
        Long id = null;
        list.add(new User(id));
        list.add(new User(12L));
        list.add(new User(2L));
        list.add(new User(5L));
        list.add(null);
        list.add(new User(2L));
        list.add(new User(id));
        LOGGER.debug(JsonUtil.format(list));
    }
}