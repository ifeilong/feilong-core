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
package com.feilong.core.bean;

import static org.junit.Assert.assertEquals;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.bean.IntrospectorUtil;
import com.feilong.core.bean.command.SalesOrder;
import com.feilong.core.tools.json.JsonUtil;

/**
 * The Class IntrospectorUtilTest.
 *
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.0.9 2015年3月24日 下午1:41:43
 * @since 1.0.9
 */
public class IntrospectorUtilTest{

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(IntrospectorUtilTest.class);

    /**
     * Test get introspector info map for log.
     *
     * @throws IntrospectionException
     *             the introspection exception
     */
    @Test
    public final void testGetIntrospectorInfoMapForLog() throws IntrospectionException{
        Map<String, Object> map = IntrospectorUtil.getIntrospectorInfoMapForLog(SalesOrder.class);
        log.debug(JsonUtil.format(map));
    }

    /**
     * Test flush caches.
     */
    @Test
    public final void testFlushCaches(){
        Introspector.flushCaches();
    }

    /**
     * Test get bean info search path.
     */
    @Test
    public final void testGetBeanInfoSearchPath(){

        //为了查找 BeanInfo 类而被搜索的包名称数组。此数组的默认值与实现有关，例如 Sun 实现最初设置为 {"sun.beans.infos"}。
        String[] beanInfoSearchPath = Introspector.getBeanInfoSearchPath();

        if (log.isDebugEnabled()){
            log.debug(JsonUtil.format(beanInfoSearchPath));
        }

    }

    /**
     * Test decapitalize.
     */
    @Test
    public final void testDecapitalize(){
        assertEquals("jinxin", IntrospectorUtil.decapitalize("Jinxin"));
        assertEquals("URL", IntrospectorUtil.decapitalize("URL"));
        assertEquals("jinXin", IntrospectorUtil.decapitalize("jinXin"));
    }

}
