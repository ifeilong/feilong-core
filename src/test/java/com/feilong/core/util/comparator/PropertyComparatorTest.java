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
package com.feilong.core.util.comparator;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

/**
 * The Class PropertyComparatorTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.2.1
 */
public class PropertyComparatorTest{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyComparatorTest.class);

    /**
     * Test property comparator2.
     */
    @Test
    public void testPropertyComparator2(){
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