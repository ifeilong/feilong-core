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
package com.feilong.core.lang.thread;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.ThreadUtil;
import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 默认基于 {@link Thread} 数组的执行实现.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.11.0
 * @since 2.0.0 move from package com.feilong.core.lang
 */
public class DefaultPartitionThreadExecutor extends AbstractPartitionThreadExecutor{

    /** The Constant LOGGER. */
    private static final Logger                 LOGGER   = LoggerFactory.getLogger(DefaultPartitionThreadExecutor.class);

    //---------------------------------------------------------------

    /** Static instance. */
    public static final PartitionThreadExecutor INSTANCE = new DefaultPartitionThreadExecutor();

    //---------------------------------------------------------------

    /**
     * Actual execute.
     *
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @param eachSize
     *            the each size
     * @param paramsMap
     *            the params map
     * @param partitionRunnableBuilder
     *            the partition runnable builder
     */
    @Override
    protected <T> void actualExecute(
                    List<T> list,
                    int eachSize,
                    Map<String, ?> paramsMap,
                    PartitionRunnableBuilder<T> partitionRunnableBuilder){
        //1. 自动构造需要启动的线程数组
        Thread[] threads = buildThreadArray(list, eachSize, paramsMap, partitionRunnableBuilder);

        //2. start 并且 join
        ThreadUtil.startAndJoin(threads);
    }

    //---------------------------------------------------------------

    /**
     * Builds the thread array.
     * 
     * <p>
     * 调用 {@link ListUtils#partition(List, int)} 对list 分成N份,对应的创建N份线程,每个线程的 名字 参见 {@link #buildThreadName(int, PartitionRunnableBuilder)}
     * </p>
     * 
     * <p>
     * 会自动创建 ThreadGroup,线程组名字参见 {@link #buildThreadGroupName(List, PartitionRunnableBuilder)}, <br>
     * 所有新建的线程将归属到该 线程组,你可以在自定义的partitionRunnableBuilder中监控或者管理 该ThreadGroup
     * </p>
     *
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @param eachSize
     *            the per size
     * @param paramsMap
     *            the params map
     * @param partitionRunnableBuilder
     *            the group runnable builder
     * @return the thread[]
     */
    private static <T> Thread[] buildThreadArray(
                    List<T> list,
                    int eachSize,
                    Map<String, ?> paramsMap,
                    PartitionRunnableBuilder<T> partitionRunnableBuilder){

        //使用group进行管理  
        ThreadGroup threadGroup = new ThreadGroup(buildThreadGroupName(list, partitionRunnableBuilder));

        //将 list 分成 N 份
        List<List<T>> groupList = ListUtils.partition(list, eachSize);

        //-------------------------------------------------------------------
        int i = 0;
        Thread[] threads = new Thread[groupList.size()];
        for (List<T> perBatchList : groupList){
            String threadName = buildThreadName(i, partitionRunnableBuilder);

            PartitionThreadEntity partitionThreadEntity = new PartitionThreadEntity(
                            threadName,
                            list.size(),
                            eachSize,
                            i,
                            perBatchList.size());

            Runnable runnable = partitionRunnableBuilder.build(perBatchList, partitionThreadEntity, paramsMap);
            threads[i] = new Thread(threadGroup, runnable, threadName);
            i++;
        }

        //---------------------------------------------------------------

        LOGGER.info("total list size:[{}],build [{}] threads,perSize:[{}]", list.size(), threads.length, eachSize);
        return threads;
    }

    //---------------------------------------------------------------

    /**
     * 构建线程组名称.
     * 
     * <h3>格式:</h3>
     * 
     * <blockquote>
     * "ThreadGroup-partitionRunnableBuilder 实现类名称-list size"
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param list
     *            the list
     * @param partitionRunnableBuilder
     *            the group runnable builder
     * @return the string
     */
    private static <T> String buildThreadGroupName(List<T> list,PartitionRunnableBuilder<T> partitionRunnableBuilder){
        Validate.notNull(partitionRunnableBuilder, "partitionRunnableBuilder can't be null!");
        return Slf4jUtil.format("ThreadGroup-{}-{}", getName(partitionRunnableBuilder), list.size());
    }

    /**
     * 构建线程名称.
     * 
     * <h3>格式:</h3>
     * 
     * <blockquote>
     * "Thread-partitionRunnableBuilder 实现类名称-{@link com.feilong.core.lang.PartitionThreadEntity#getBatchNumber() batchNumber}"
     * </blockquote>
     * 
     * <h3>作用:</h3>
     * 
     * <blockquote>
     * 
     * <ul>
     * <li>一来便于管理, 可以使用相关代码来获得线程;</li>
     * <li>二来常用于日志显示, 比如, 如果是 log4j 的配置文件,如果 ConversionPattern
     * 
     * <pre>
     * {@code 
     * <param name="ConversionPattern" value="%d}{HH:mm:ss} {@code %t %-5p (%F:%L) %m%n" />
     * }
     * </pre>
     * 
     * 其中 %t 表示 线程名称
     * 
     * 正常情况的日志,会显示(示例)
     * 
     * <pre>
     * 13:54:43 <span style=
     * "color:red">Thread-NovelpartitionRunnableBuilder-13</span> INFO (NovelpartitionRunnableBuilder.java:91) 第914章 好手段 3406 [6/20] 14 30%
     * 13:54:43 <span style=
     * "color:red">Thread-NovelpartitionRunnableBuilder-5</span> INFO (NovelpartitionRunnableBuilder.java:91) 第761章 不得其时 3573 [7/20] 6 35%
     * 13:54:43 <span style=
     * "color:red">Thread-NovelpartitionRunnableBuilder-3</span> INFO (NovelpartitionRunnableBuilder.java:91) 第718章 各打各的算盘 3411 [4/20] 4 20%
     * </pre>
     * 
     * 如果代码有异常, 会显示
     * 
     * <pre>
     * 13:54:52 <span style="color:red">Thread-NovelpartitionRunnableBuilder-16</span> ERROR (DefaultChapterBuilder.java:83) Exception:
     * com.feilong.tools.jsoup.JsoupUtilException: urlString:[http://www.37zw.com/0/181/1662249.html],userAgent:[Mozilla/5.0 (X11; Linux
     * x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21]
     * at com.feilong.tools.jsoup.JsoupUtil.getDocument(JsoupUtil.java:87)
     * at com.feilong.tools.jsoup.JsoupUtil.getDocument(JsoupUtil.java:65)
     * at com.feilong.project.novel.build.DefaultChapterBuilder.getContentElement(DefaultChapterBuilder.java:124)
     * at com.feilong.project.novel.build.DefaultChapterBuilder.build(DefaultChapterBuilder.java:68)
     * at com.feilong.project.novel.build.NovelpartitionRunnableBuilder$1.run(NovelpartitionRunnableBuilder.java:86)
     * at java.lang.Thread.run(Thread.java:745)
     * </pre>
     * 
     * </li>
     * </ul>
     * 
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param batchNumber
     *            the batch number
     * @param partitionRunnableBuilder
     *            the group runnable builder
     * @return 如果 <code>partitionRunnableBuilder</code> 是null,抛出 {@link NullPointerException}<br>
     */
    private static <T> String buildThreadName(int batchNumber,PartitionRunnableBuilder<T> partitionRunnableBuilder){
        Validate.notNull(partitionRunnableBuilder, "partitionRunnableBuilder can't be null!");
        return Slf4jUtil.format("Thread-{}-{}", getName(partitionRunnableBuilder), batchNumber);
    }

}
