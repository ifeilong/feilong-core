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
package com.feilong.core.bean.convertutiltest;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toSet;
import static java.util.Collections.emptySet;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import com.feilong.store.member.User;

/**
 * The Class ConvertUtilToSetVarargsTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToSetVarargsTest{

    /**
     * Test to set.
     */
    @Test
    public void testToSet(){
        User user1 = new User(1L);
        User user2 = new User(2L);

        assertThat(toSet(user1, user2), contains(user1, user2));
    }

    /**
     * Test to set empty array.
     */
    @Test
    public void testToSetEmptyArray(){
        assertEquals(emptySet(), toSet(toArray()));
    }

    //---------------------------------------------------------------

    /**
     * Test to set null array.
     */
    @Test
    public void testToSetNullArray(){
        assertEquals(emptySet(), toSet((User[]) null));
    }

    /**
     * Test to set null element array.
     */
    @Test
    public void testToSetNullElementArray(){
        Set<User> set = new LinkedHashSet<>();
        set.add(null);

        assertEquals(set, toSet((User) null));
    }
}
