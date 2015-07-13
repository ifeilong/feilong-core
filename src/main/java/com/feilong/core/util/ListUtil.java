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

import java.util.List;

/**
 * {@link List}工具类.
 * 
 * @author feilong
 * @version 1.0.0 2010-3-2 下午03:20:12
 * @since 1.0.0
 */
public final class ListUtil{

    /** Don't let anyone instantiate this class. */
    private ListUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    // ***********************************************************************************
    /**
     * list集合转换成字符串,仅将[]中括号符号 换成()小括号,其余不动<br>
     * 如:
     * 
     * <pre>
     * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
     * testList.add(&quot;xinge&quot;);
     * testList.add(&quot;feilong&quot;);
     * 
     * toStringReplaceBrackets(testList)-----{@code >}(xinge, feilong)
     * </pre>
     * 
     * @param list
     *            list集合
     * @return list集合转换成字符串,仅将[]中括号符号 换成()小括号,其余不动
     * @deprecated 感觉没有使用场景，将来可能会删除或重构
     */
    @Deprecated
    public static String toStringReplaceBrackets(List<String> list){
        return list.toString().replace('[', '(').replace(']', ')');
    }

    /**
     * list集合转换成字符串,移除[]中括号符号,并去除空格 <br>
     * 如:
     * 
     * <pre>
     * {@code
     * 
     * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
     * testList.add(&quot;xinge&quot;);
     * testList.add(&quot;feilong&quot;);
     * 
     * toStringRemoveBrackets(testList)----->xinge,feilong
     * }
     * </pre>
     * 
     * @param list
     *            list集合
     * @return list集合转换成字符串,移除[]中括号符号,并去除空格
     * @deprecated 感觉没有使用场景，将来可能会删除或重构
     */
    @Deprecated
    public static String toStringRemoveBrackets(List<String> list){
        String s = list.toString();
        return s.substring(1, s.length() - 1).replaceAll(" ", "");
    }

    /**
     * list集合转换成字符串 如:
     * 
     * <pre>
     * {@code
     * 
     * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
     * testList.add(&quot;xinge&quot;);
     * testList.add(&quot;feilong&quot;);
     * 
     * toString(testList,true)----->'xinge','feilong'
     * toString(testList,false)----->xinge,feilong
     * }
     * </pre>
     * 
     * @param list
     *            list集合
     * @param isAddQuotation
     *            是否增加单引号
     * @return list集合转换成字符串
     * @deprecated 感觉没有使用场景，将来可能会删除或重构
     */
    @Deprecated
    public static String toString(List<String> list,boolean isAddQuotation){
        String returnValue = toStringRemoveBrackets(list);
        if (isAddQuotation){
            returnValue = "'" + returnValue.replaceAll(",", "','") + "'";
        }
        return returnValue;
    }
}