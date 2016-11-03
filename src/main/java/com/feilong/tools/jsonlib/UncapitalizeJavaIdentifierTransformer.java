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
package com.feilong.tools.jsonlib;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.util.JavaIdentifierTransformer;

/**
 * 当json转成bean的时候,json字符串里面的属性名字可能首字母是大写的,转成bean里面属性首字母小写的转换器.
 * 
 * <p>
 * 比如 MemberId,但是我们的java bean里面的属性名字是标准的 驼峰命名法则,比如 memberId
 * </p>
 * 
 * <h3>示例:</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * <b>场景:</b> 从相关接口得到的json数据格式如下(注意:属性首字母是大写的):
 * </p>
 * 
 * <pre class="code">
 * {
 * "MemberNo":"11105000009",
 * "Name":null,
 * "Gender":"",
 * "Phone":"15036334567",
 * "Email":null,
 * "Birthday":""
 * }
 * </pre>
 * 
 * <p>
 * 但是我们的类是标准的java bean,属性符合驼峰命名规则,比如:
 * </p>
 * 
 * <pre class="code">
 * public class CrmMemberInfoCommand{
 * 
 *     //** 会员编号
 *     private String memberNo;
 * 
 *     //** 姓名/昵称 
 *     private String name;
 * 
 *     //** 电话号码 
 *     private String phone;
 * 
 *     //** 性别：男；女 
 *     private String gender;
 * 
 *     //** 生日：Format：yyyy-MM-dd 
 *     private String birthday;
 * 
 *     //** 邮箱 
 *     private String email;
 * 
 *     // setter getter
 * }
 * </pre>
 * 
 * <p>
 * 此时可以使用该类,示例如下:
 * </p>
 * 
 * <pre class="code">
 * 
 * public void testToBean2(){
 *     String json = "{'MemberNo':'11105000009','Name':null,'Gender':'','Phone':'15036334567','Email':null,'Birthday':''}";
 * 
 *     JsonToJavaConfig jsonToJavaConfig = new JsonToJavaConfig();
 *     jsonToJavaConfig.setRootClass(CrmMemberInfoCommand.class);
 *     jsonToJavaConfig.setJavaIdentifierTransformer(UncapitalizeJavaIdentifierTransformer.UNCAPITALIZE);
 * 
 *     CrmMemberInfoCommand crmMemberInfoCommand = JsonUtil.toBean(json, jsonToJavaConfig);
 *     //.....
 * }
 * 
 * </pre>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see <a href="https://github.com/venusdrogon/feilong-core/issues/509">issue 509</a>
 * @since 1.9.4
 */
public class UncapitalizeJavaIdentifierTransformer extends JavaIdentifierTransformer{

    /** 首字母小写 transformer 'MemberNo' {@code =>} 'memberNo'. */
    public static final JavaIdentifierTransformer UNCAPITALIZE = new UncapitalizeJavaIdentifierTransformer();

    /*
     * (non-Javadoc)
     * 
     * @see net.sf.json.util.JavaIdentifierTransformer#transformToJavaIdentifier(java.lang.String)
     */
    @Override
    public String transformToJavaIdentifier(String str){
        return StringUtils.uncapitalize(str);
    }
}
