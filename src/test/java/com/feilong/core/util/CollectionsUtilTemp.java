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
package com.feilong.core.util;

import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.tools.jsonlib.JsonUtil;

public class CollectionsUtilTemp{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionsUtilTemp.class);

    /**
     * TestCollectionsUtilTest.
     */
    @Test
    public void testCollectionsUtilTest(){
        Set<String> set = new LinkedHashSet<String>();

        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");
        set.add("5");
        set.add("1");

        LOGGER.debug(JsonUtil.format(set));
    }

    /**
     * TestCollectionsUtilTest.
     */
    @Test
    public void testCollectionsUtilTest2(){
        Stack<Object> stack = new Stack<Object>();

        stack.add("1");
        stack.add("2");
        stack.add("3");
        stack.add("4");

        LOGGER.debug("" + stack.firstElement());
        LOGGER.debug("" + stack.peek());
        LOGGER.debug("" + stack.pop());
        LOGGER.debug(JsonUtil.format(stack));
    }

    /**
     * TestCollectionsUtilTest.
     */
    @Test
    public void testCollectionsUtilTest33(){
        Queue<Object> queue = new PriorityQueue<Object>();

        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);

        LOGGER.debug(JsonUtil.format(queue));
        LOGGER.debug("" + queue.peek());
    }
}
