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
package com.feilong.tools.formatter;

import static com.feilong.tools.formatter.FormatterUtil.formatToSimpleTable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.store.member.Address;
import com.feilong.test.User;
import com.feilong.tools.jsonlib.JsonUtil;

import static com.feilong.core.bean.ConvertUtil.toArray;
import static com.feilong.core.bean.ConvertUtil.toBigDecimal;
import static com.feilong.core.bean.ConvertUtil.toList;
import static com.feilong.core.bean.ConvertUtil.toMap;

/**
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.8.2
 */
public class FormatterUtilTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(FormatterUtilTest.class);

    @Test
    public final void testFormatToSimpleTable(){
        Map<String, String> map = toMap(//
                        Pair.of(
                                        "Loading entityengine.xml from",
                                        "file:/opt/atlassian/jira/atlassian-jira/WEB-INF/classes/entityengine.xml"),
                        Pair.of("Entity model field type name", "postgres72"),
                        Pair.of("Entity model schema name", "public"),
                        Pair.of("Database Version", "PostgreSQL - 9.2.8"),
                        Pair.of("Database Driver", "PostgreSQL Native Driver - PostgreSQL 9.0 JDBC4 (build 801)"),
                        Pair.of("Database Version", "PostgreSQL - 9.2.8"),
                        Pair.of((String) null, "PostgreSQL - 9.2.8"),
                        Pair.of("Database URL", "jdbc:postgresql://127.0.0.1:5432/db_feilong_jira"),
                        Pair.of("Database JDBC config", "postgres72 jdbc:postgresql://127.0.0.1:5432/db_feilong_jira"));

        LOGGER.debug(formatToSimpleTable(map));
    }

    @Test
    public final void testFormatToSimpleTable1(){
        User user = new User();
        user.setAge(15);
        user.setId(88L);
        user.setAttrMap(toMap("love", "sanguo"));
        user.setDate(new Date());
        user.setMoney(toBigDecimal(999));
        user.setName("xinge");
        user.setNickNames(toArray("jinxin", "feilong"));
        LOGGER.debug(formatToSimpleTable(user));
    }

    @Test
    public final void testFormatToSimpleTable2(){
        List<Address> list = toList(
                        new Address("china", "shanghai", "wenshui wanrong.lu 888", "216000"),
                        new Address("china", "beijing", "wenshui wanrong.lu 666", "216001"),
                        new Address("china", "nantong", "wenshui wanrong.lu 222", "216002"),
                        new Address("china", "tianjing", "wenshui wanrong.lu 999", "216600"));

        LOGGER.debug(JsonUtil.format(list));
        LOGGER.debug(formatToSimpleTable(list));
    }
}
