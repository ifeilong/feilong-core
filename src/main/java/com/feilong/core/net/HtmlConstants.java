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
package com.feilong.core.net;

/**
 * html相关常量.
 * 
 * @author <a href="mailto:venusdrogon@163.com">feilong</a>
 * @version 1.2.1 2015年6月1日 下午2:10:04
 * @since 1.2.1
 * @see "org.springframework.web.util.HtmlUtils"
 * @see "org.springframework.web.util.HtmlCharacterEntityDecoder"
 * @see "weibo4j.http.HTMLEntity"
 */
public final class HtmlConstants{

    /**
     * 空格 (Non-Breaking SPace)<code>{@value}</code>.
     * <p>
     * 用"&nbsp;"来代替空格，一个"&nbsp;"相当于一个空格，多加几个"&nbsp;"就可以把空格拉大。<br>
     * 虽然"&nbsp;"可以当作空格用,但是"&nbsp;"其实和空格是不一样的,<br>
     * nbsp是英文Non-Breaking SPace的缩写，可以直接翻译成“不被折断的空格”。
     * </p>
     * 
     * 比如下面这段html：
     * 
     * <pre>
     * {@code
     * <h2>10 Most Sought-after Skills in Web Development</h2>
     * }
     * </pre>
     * 
     * <pre>
     * 假设{@code<h2>}的宽度有限，只能容下“10 Most Sought-after Skills in Web”， 
     * 由于Web Development之间用的是空格，"Development"就会被移到第二行。 
     * 
     * 因为Web Development是相关的两个词，所以如果可以把它们同时移到第二行，效果可能会更好一点。
     * 为了到达这个目的，我们可以在Web Development之间用"&nbsp;"来代替空格，这个样它们就会被连在一起。
     * </pre>
     * 
     * <pre>
     * {@code
     * <h2>10 Most Sought-after Skills in Web&nbsp;Development</h2>
     * }
     * </pre>
     */
    public static final String NBSP = "&nbsp;";

    /** Don't let anyone instantiate this class. */
    private HtmlConstants(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }
}
