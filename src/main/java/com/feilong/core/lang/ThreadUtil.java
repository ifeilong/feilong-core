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
package com.feilong.core.lang;

import static com.feilong.core.date.DateExtensionUtil.formatDuration;
import static com.feilong.core.lang.ObjectUtil.defaultIfNullOrEmpty;
import static org.apache.commons.lang3.ClassUtils.getSimpleName;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.tools.slf4j.Slf4jUtil;

/**
 * 线程相关工具类.
 * 
 * <h3>关于 {@link ThreadGroup}</h3>
 * 
 * <blockquote>
 * <p>
 * 线程组表示一个线程的集合。
 * </p>
 * 
 * <p>
 * 此外，线程组也可以包含其他线程组。线程组构成一棵树，<br>
 * 在树中，除了初始线程组外，每个线程组都有一个父线程组。<br>
 * 允许线程访问有关自己的线程组的信息，但是不允许它访问有关其线程组的父线程组或其他任何线程组的信息。
 * </p>
 * 
 * <p>
 * 所有线程都隶属于一个线程组。那可以是一个默认线程组，亦可是一个创建线程时明确指定的组。
 * </p>
 * 
 * <p>
 * 在创建之初，线程被限制到一个组里，而且不能改变到一个不同的组。每个应用都至少有一个线程从属于系统线程组。<br>
 * 若创建多个线程而不指定一个组，它们就会自动归属于系统线程组。<br>
 * 线程组也必须从属于其他线程组。<br>
 * 必须在构建器里指定新线程组从属于哪个线程组。<br>
 * 
 * 若在创建一个线程组的时候没有指定它的归属，则同样会自动成为系统线程组的一名属下。
 * </p>
 * 
 * <p>
 * 因此，一个应用程序中的所有线程组最终都会将系统线程组作为自己的“父”
 * 之所以要提出“线程组”的概念，一般认为，是由于“安全”或者“保密”方面的理由。<br>
 * 根据Arnold和Gosling的说法：“线程组中的线程可以修改组内的其他线程，包括那些位于分层结构最深处的。一个线程不能修改位于自己所在组或者下属组之外的任何线程”
 * </p>
 * </blockquote>
 * 
 * <h3>{@link Callable}和{@link Runnable}的区别如下:</h3>
 * <blockquote>
 * <ol>
 * <li>{@link Callable} since jdk1.5,而{@link Runnable} since jdk1.0.</li>
 * <li>{@link Callable}定义的方法是{@link Callable#call()},而{@link Runnable}定义的方法是{@link Runnable#run()}.</li>
 * <li>{@link Callable}的{@link Callable#call()}方法有返回值,而{@link Runnable}的{@link Runnable#run()}方法没有返回值</li>
 * <li>{@link Callable}的{@link Callable#call()}方法可抛出异常,而{@link Runnable}的{@link Runnable#run()}方法不能抛出异常</li>
 * </ol>
 * </blockquote>
 * 
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @see "org.springframework.core.task.SimpleAsyncTaskExecutor"
 * @see "org.springframework.core.task.SyncTaskExecutor"
 * @see "org.springframework.core.task.TaskExecutor"
 * @since 1.10.3
 */
public final class ThreadUtil{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUtil.class);

    /** Don't let anyone instantiate this class. */
    private ThreadUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    //---------------------------------------------------------------

    /**
     * 创建指定数量 <b>threadCount</b> 的线程,并执行.
     * 
     * <p>
     * 
     * 如果 <code>runnable</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 {@code threadCount <=0},抛出 {@link IllegalArgumentException}<br>
     * 
     * </p>
     *
     * @param runnable
     *            the runnable
     * @param threadCount
     *            the thread count
     * @since 1.10.4
     */
    public static void execute(Runnable runnable,int threadCount){
        Validate.notNull(runnable, "runnable can't be null!");
        Validate.isTrue(threadCount > 0, "threadCount must > 0");

        //---------------------------------------------------------------

        Date beginDate = new Date();

        Thread[] threads = buildThreadArray(runnable, threadCount);
        ThreadUtil.startAndJoin(threads);

        //---------------------------------------------------------------

        if (LOGGER.isInfoEnabled()){
            LOGGER.info("runnable:[{}],threadCount:[{}],total use time:{}", runnable.toString(), threadCount, formatDuration(beginDate));
        }
    }

    /**
     * Builds the thread array.
     *
     * @param runnable
     *            the runnable
     * @param threadCount
     *            the thread count
     * @return the thread[]
     */
    private static Thread[] buildThreadArray(Runnable runnable,int threadCount){
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; ++i){
            threads[i] = new Thread(runnable);
        }
        return threads;
    }

    //---------------------------------------------------------------

    /**
     * 给定一个待解析的 <code>list</code>,设定每个线程执行多少条 <code>eachSize</code>,传入一些额外的参数 <code>paramsMap</code>,使用自定义的
     * <code>partitionRunnableBuilder</code>,自动<span style="color:green">构造多条线程</span>并运行.
     * 
     * <h3>适用场景:</h3>
     * <blockquote>
     * 
     * <p>
     * 比如同步库存,一次从MQ或者其他接口中得到了5000条数据,如果使用单线程做5000次循环,势必会比较慢,并且影响性能; 如果调用这个方法,传入eachSize=100, 那么自动会开启5000/100=50 个线程来跑功能,大大提高同步库存的速度
     * </p>
     * <p>
     * 其他的适用场景还有诸如同步商品主档数据,同步订单等等这类每个独立对象之间没有相关联关系的数据,能提高执行速度和效率
     * </p>
     * 
     * </blockquote>
     * 
     * <h3>重构:</h3>
     * 
     * <blockquote>
     * <p>
     * 对于以下代码:模拟10个对象/数字,循环执行任务(可能是操作数据库等)
     * </p>
     * 
     * <pre class="code">
     * 
     * public void testExecuteTest() throws InterruptedException{
     *     Date beginDate = new Date();
     * 
     *     List{@code <Integer>} list = toList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
     *     for (Integer integer : list){
     *         <span style="color:green">//------</span>
     * 
     *         <span style="color:green">//模拟 do something</span>
     * 
     *         <span style="color:green">//---------</span>
     *         Thread.sleep(1 * MILLISECOND_PER_SECONDS);
     *     }
     *     LOGGER.info("use time: [{}]", formatDuration(beginDate));
     * }
     * 
     * </pre>
     * 
     * <p>
     * 统计总耗时时间 需要 use time:<span style="color:red">10秒28毫秒</span>
     * </p>
     * 
     * <b>此时你可以调用此方法,改成多线程执行:</b>
     * 
     * <pre class="code">
     * 
     * public void testExecuteTestUsePartitionRunnableBuilder() throws InterruptedException{
     *     Date beginDate = new Date();
     *     List{@code <Integer>} list = toList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
     * 
     *     <span style="color:green">//每个线程执行2条数据, 没有自定义 paramsMap</span>
     * 
     *     <span style="color:green">//将会自动创建 list.size()/2 =5个 线程执行</span>
     * 
     *     <span style="color:green">//每个线程执行的,将会是 PartitionRunnableBuilder build 返回的 Runnable</span>
     *     ThreadUtil.execute(list, 1, null, new PartitionRunnableBuilder{@code <Integer>}(){
     * 
     *         &#64;Override
     *         public Runnable build(final List{@code <Integer>} perBatchList,PartitionThreadEntity partitionThreadEntity,Map{@code <String, ?>} paramsMap){
     * 
     *             return new Runnable(){
     * 
     *                 &#64;Override
     *                 public void run(){
     *                     for (Integer integer : perBatchList){
     *                         <span style="color:green">//------</span>
     * 
     *                         <span style="color:green">//模拟 do something</span>
     * 
     *                         <span style="color:green">//---------</span>
     *                         try{
     *                             Thread.sleep(1 * MILLISECOND_PER_SECONDS);
     *                         }catch (InterruptedException e){
     *                             LOGGER.error("", e);
     *                         }
     *                     }
     *                 }
     *             };
     *         }
     * 
     *     });
     * 
     *     LOGGER.info("use time: [{}]", formatDuration(beginDate));
     * }
     * </pre>
     * 
     * <p>
     * 统计总耗时时间 需要 use time:<span style="color:red">2秒36毫秒</span>
     * </p>
     * 
     * <p>
     * 对于上述的case,如果将 eachSize 参数由2 改成1, 统计总耗时时间 需要 use time:<span style="color:red">1秒36毫秒</span>
     * </p>
     * 
     * <p>
     * 可见 调用该方法,使用多线程能节省执行时间,提高效率; 但是也需要酌情考虑eachSize大小,合理的开启线程数量
     * </p>
     * </blockquote>
     * 
     * <h3>说明:</h3>
     * <blockquote>
     * 线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性;<br>
     * 需要注意合理的评估<code>list</code> 的大小和<code>eachSize</code> 比率;<br>
     * 不建议<code>list</code> size很大,比如 20W,而<code>eachSize</code>值很小,比如2 ,那么会开启20W/2=10W个线程;此时建议考虑 线程池的实现方案
     * </blockquote>
     * 
     * <h3>对于参数 paramsMap 的使用:</h3>
     * <blockquote>
     * 
     * <p>
     * 比如你需要拿到最终每条数据执行的结果,以便后续进行处理(比如对失败的操作再次执行或者发送汇报邮件等)
     * </p>
     * 
     * <pre class="code">
     * 
     * public void testExecuteTestUsePartitionRunnableBuilderParamsMap() throws InterruptedException{
     *     Date beginDate = new Date();
     *     List{@code <Integer>} list = toList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
     * 
     *     final Map{@code <Integer, Boolean>} indexAndResultMap = Collections.synchronizedSortedMap(new TreeMap{@code <Integer, Boolean>}());
     * 
     *     ThreadUtil.execute(list, 2, null, new PartitionRunnableBuilder{@code <Integer>}(){
     * 
     *         &#64;Override
     *         public Runnable build(
     *                         final List{@code <Integer>} perBatchList,
     *                         final PartitionThreadEntity partitionThreadEntity,
     *                         Map{@code <String, ?>} paramsMap){
     * 
     *             return new Runnable(){
     * 
     *                 &#64;Override
     *                 public void run(){
     * 
     *                     int i = 0;
     *                     for (Integer integer : perBatchList){
     *                         <span style="color:green">//------</span>
     * 
     *                         <span style="color:green">//模拟 do something</span>
     * 
     *                         <span style="color:green">//---------</span>
     *                         try{
     *                             Thread.sleep(1 * MILLISECOND_PER_SECONDS);
     *                         }catch (InterruptedException e){
     *                             LOGGER.error("", e);
     *                         }
     * 
     *                         int indexInTotalList = getIndex(partitionThreadEntity, i);
     * 
     *                         <span style="color:green">//模拟 当值是 5 或者8 的时候 操作结果是false</span>
     *                         boolean result = (integer == 5 || integer == 8) ? false : true;
     * 
     *                         indexAndResultMap.put(indexInTotalList, result);
     * 
     *                         i++;
     *                     }
     *                 }
     * 
     *                 private Integer getIndex(PartitionThreadEntity partitionThreadEntity,int i){
     *                     int batchNumber = partitionThreadEntity.getBatchNumber();
     *                     return batchNumber * partitionThreadEntity.getEachSize() + i;
     *                 }
     *             };
     *         }
     * 
     *     });
     * 
     *     LOGGER.debug(JsonUtil.format(indexAndResultMap));
     * 
     *     LOGGER.info("use time: [{}]", formatDuration(beginDate));
     * }
     * 
     * </pre>
     * 
     * <p>
     * 输出结果:
     * </p>
     * 
     * <pre>
    29:21 DEBUG (ThreadUtilExample.java:161) [testExecuteTestUsePartitionRunnableBuilderParamsMap()]     {
        "0": true,
        "1": true,
        "2": true,
        "3": true,
        "4": true,
        "5": false,
        "6": true,
        "7": true,
        "8": false,
        "9": true
    }
    29:21 INFO  (ThreadUtilExample.java:164) [testExecuteTestUsePartitionRunnableBuilderParamsMap()] use time:2秒181毫秒
     * 
     * </pre>
     * 
     * </blockquote>
     * 
     * <h3>异常:</h3>
     * <blockquote>
     * <p>
     * 如果 <code>list</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>list</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     * 如果 <code>partitionRunnableBuilder</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 {@code eachSize<=0} 是null,抛出 {@link IllegalArgumentException}<br>
     * </p>
     * </blockquote>
     *
     * @param <T>
     *            the generic type
     * @param list
     *            执行解析的list, 比如 100000个 User,不能为null或者empty
     * @param eachSize
     *            每个线程执行多少个对象,比如 一个线程解析 1000个 User, 那么程序内部 会自动创建 100000/1000个 线程去解析;<br>
     *            必须{@code >}0
     * @param paramsMap
     *            自定义的相关参数,该参数目的是你可以在自定义的 <code>partitionRunnableBuilder</code>中使用;<br>
     *            如果你传入的<code>partitionRunnableBuilder</code>中不需要额外的自定义参数,那么此处可以传入null
     * @param partitionRunnableBuilder
     *            每个线程做的事情,不能为null
     */
    public static <T> void execute(List<T> list,int eachSize,Map<String, ?> paramsMap,PartitionRunnableBuilder<T> partitionRunnableBuilder){
        Validate.notEmpty(list, "list can't be null/empty!");
        Validate.notNull(partitionRunnableBuilder, "partitionRunnableBuilder can't be null!");

        Validate.isTrue(eachSize > 0, "eachSize must > 0");

        //-----------------------------------------------------------------------------------------------
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("begin [{}],list size:[{}],eachSize:[{}]", getName(partitionRunnableBuilder), list.size(), eachSize);
        }

        Date beginDate = new Date();

        //---------------------------------------------------------------

        //1. 自动构造需要启动的线程数组
        Thread[] threads = buildThreadArray(list, eachSize, paramsMap, partitionRunnableBuilder);

        //2. start 并且 join
        startAndJoin(threads);

        //---------------------------------------------------------------

        if (LOGGER.isInfoEnabled()){
            LOGGER.info("end [{}],use time:[{}]", getName(partitionRunnableBuilder), formatDuration(beginDate));
        }
    }

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

    /**
     * 循环 <code>threads</code> 调用 {@link java.lang.Thread#start()} 再循环 <code>threads</code> 调用 {@link java.lang.Thread#join()}.
     *
     * <p>
     * threads are run concurrently and this method waits for them to finish.
     * </p>
     *
     * <p>
     * 如果 <code>threads</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>threads</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     * </p>
     * 
     * @param threads
     *            the threads
     * @see java.lang.ApplicationShutdownHooks#runHooks()
     */
    public static void startAndJoin(Thread[] threads){
        Validate.notEmpty(threads, "threads can't be null/empty!");

        for (Thread thread : threads){
            thread.start();// 使该线程开始执行；Java 虚拟机调用该线程的 run 方法。
            LOGGER.debug("thread [{}] start", thread.getName());
        }

        //---------------------------------------------------------------

        try{
            for (Thread thread : threads){
                LOGGER.debug("begin thread [{}] join", thread.getName());
                thread.join(); //在一个线程中调用 otherThread.join(),将等待 otherThread 执行完后才继续本线程　　　
                LOGGER.debug("end thread [{}] join", thread.getName());
            }
        }catch (InterruptedException e){
            LOGGER.error("", e);
            // clean up state...
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Gets the name.
     *
     * @param <T>
     *            the generic type
     * @param partitionRunnableBuilder
     *            the partition runnable builder
     * @return the name
     */
    private static <T> String getName(PartitionRunnableBuilder<T> partitionRunnableBuilder){
        return defaultIfNullOrEmpty(getSimpleName(partitionRunnableBuilder.getClass()), partitionRunnableBuilder.getClass().getName());
    }
}
