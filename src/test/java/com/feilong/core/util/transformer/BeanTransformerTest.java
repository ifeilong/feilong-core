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
package com.feilong.core.util.transformer;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.feilong.store.member.User;
import com.feilong.store.member.UserInfo;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.10.7
 */
public class BeanTransformerTest{

    @Test
    public void testTransform1(){
        User user = new User(1L, 18);

        BeanTransformer<User, UserInfo> beanTransformer = new BeanTransformer<>(UserInfo.class, "age");
        UserInfo userInfo = beanTransformer.transform(user);

        assertThat(userInfo, allOf(hasProperty("age", is(18))));
    }

    @Test
    public void testTransform(){
        User user = new User(1L, 18);

        BeanTransformer<User, UserInfo> beanTransformer = new BeanTransformer<>(UserInfo.class);
        UserInfo userInfo = beanTransformer.transform(user);

        assertThat(userInfo, allOf(hasProperty("age", is(18))));
    }

    //---------------------------------------------------------------

    @Test(expected = NullPointerException.class)
    public void testBeanTransformerTestNull(){
        new BeanTransformer(null);
    }

    @Test(expected = NullPointerException.class)
    public void testBeanTransformerTestNull1(){
        new BeanTransformer(null, "name");
    }
}
