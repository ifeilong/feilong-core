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
package com.feilong.core.util.collectionsutiltest;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

import com.feilong.core.util.CollectionsUtil;

/**
 * The Class NewTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class NewTest{

    /**
     * Test new array list.
     */
    @Test
    public void testNewArrayList(){
        ArrayList<Object> newArrayList = CollectionsUtil.newArrayList();
        assertEquals(emptyList(), newArrayList);
    }

    /**
     * Test new linked list.
     */
    @Test
    public void testNewLinkedList(){
        LinkedList<Object> newLinkedList = CollectionsUtil.newLinkedList();
        assertEquals(emptyList(), newLinkedList);
    }

    //---------------------------------------------------------------

    /**
     * Test new copy on write array list.
     */
    @Test
    public void testNewCopyOnWriteArrayList(){
        CopyOnWriteArrayList<Object> newCopyOnWriteArrayList = CollectionsUtil.newCopyOnWriteArrayList();
        assertEquals(emptyList(), newCopyOnWriteArrayList);
    }

    /**
     * Test new hash set.
     */
    @Test
    public void testNewHashSet(){
        HashSet<Object> newHashSet = CollectionsUtil.newHashSet();
        assertEquals(emptySet(), newHashSet);
    }

    /**
     * Test new linked hash set.
     */
    @Test
    public void testNewLinkedHashSet(){
        LinkedHashSet<Object> newLinkedHashSet = CollectionsUtil.newLinkedHashSet();
        assertEquals(emptySet(), newLinkedHashSet);
    }

}
