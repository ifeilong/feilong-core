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
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.util.CollectionsUtil.newArrayList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.feilong.store.member.User;

/**
 * The Class ConvertUtilToListVarargsTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class ToListVarargsTest{

    /**
     * Test to list.
     */
    @Test
    public void testToList(){
        User user1 = new User(1L);
        User user2 = new User(2L);

        assertThat(toList(user1, user2), contains(user1, user2));
    }

    /**
     * Test to list empty array.
     */
    @Test
    public void testToListEmptyArray(){
        assertEquals(emptyList(), toList(toArray()));
    }

    //---------------------------------------------------------------

    /**
     * Test to list null array.
     */
    @Test
    public void testToListNullArray(){
        assertEquals(emptyList(), toList((User[]) null));
    }

    /**
     * Test to list null element array.
     */
    @Test
    public void testToListNullElementArray(){
        List<User> list = newArrayList();
        list.add(null);

        assertEquals(list, toList((User) null));
    }
}
