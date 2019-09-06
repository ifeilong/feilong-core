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

/**
 * 分区线程执行器.
 * 
 * <h3>背景:</h3>
 * 
 * <blockquote>
 * 
 * <p>
 * 经常有如此的需求, 下载客户8000张图片加工处理, 如果串行执行耗时很长,此时需要使用多线程,<br>
 * 但是如果从头开始写会开发蛮久时间,此时把核心功能进行封装 ,你只需要传入 8000和url list, 告知每个线程处理多少张图片, 以及每个线程的代码处理方式即可,实现类会自动分区创建线程并执行
 * </p>
 * 
 * <p>
 * 契合 feilong 的思想, Reduce development, Release ideas (减少开发,释放思想)
 * </p>
 * </blockquote>
 * 
 * <h3>快速调用以及实现类:</h3>
 * 
 * <blockquote>
 * <p>
 * 目前已知快速调用有 {@link com.feilong.core.lang.ThreadUtil#execute(List, int, Map, PartitionRunnableBuilder) }, 或者调用
 * {@link com.feilong.core.lang.thread.DefaultPartitionThreadExecutor}, 或者调用
 * {@link "com.feilong.spring.scheduling.concurrent.AsyncTaskExecutorPartitionThreadExecutor"}
 * </p>
 * 
 * </blockquote>
 * 
 * <h3>如果使用spring asyncTaskExecutor:</h3>
 * <blockquote>
 * 
 * <pre>
{@code
 <!-- 配置线程池 -->
    <bean id="taskExecutor" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
         <!-- 线程池维护线程的最少数量 -->
        <property name="corePoolSize" value="10" />
         <!-- 线程池维护线程所允许的空闲时间 -->
        <property name="keepAliveSeconds" value="30000" />
         <!-- 线程池维护线程的最大数量 -->
        <property name="maxPoolSize" value="1000" />
         <!-- 线程池所使用的缓冲队列 -->
        <property name="queueCapacity" value="200" />
    </bean>
    
    <!-- partitionThreadExecutor -->

    <bean id="partitionThreadExecutor" class="com.feilong.spring.scheduling.concurrent.AsyncTaskExecutorPartitionThreadExecutor">
        <property name="asyncTaskExecutor" ref="taskExecutor"></property>
    </bean>
    
    }
 * </pre>
 * 
 * 然后 java 代码调用
 * 
 * <pre>
 * 
 * <code>@Autowired</code>
 * private PartitionThreadExecutor partitionThreadExecutor;
 * 
 * <code>@Test</code>
 * public void test(){
 *     final List{@code <Integer>} list = toList(1, 2, 3, 4, 5);
 * 
 *     partitionThreadExecutor.execute(list, 2, new PartitionRunnableBuilder{@code <Integer>}(){
 * 
 *         public Runnable build(List{@code <Integer>} perBatchList,final PartitionThreadEntity partitionThreadEntity,Map{@code <String, ?>} paramsMap){
 *             return new Runnable(){
 * 
 *                 public void run(){
 *                     for (Integer integer : list){
 *                         LOGGER.debug("{}-{}", partitionThreadEntity.getBatchNumber(), integer);
 *                     }
 *                 }
 *             };
 *         }
 *     });
 * }
 * </pre>
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.11.0
 * @since 2.0.0 move from package com.feilong.core.lang
 */
public interface PartitionThreadExecutor{

    /**
     * 给定一个待解析的 <code>list</code>,设定每个线程执行多少条 <code>eachSize</code> ,使用自定义的
     * <code>partitionRunnableBuilder</code>,自动<span style="color:green">构造多条线程</span>并运行.
     * 
     * <p>
     * 如果 <code>list</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>list</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     * 如果 {@code eachSize <=0} ,抛出 {@link IllegalArgumentException}<br>
     * 如果 <code>partitionRunnableBuilder</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     * 
     * @param <T>
     *            the generic type
     * @param list
     *            执行解析的list
     * 
     *            <p>
     *            比如 100000个 User,不能为null或者empty
     *            </p>
     * @param eachSize
     *            每个线程执行多少个对象
     * 
     *            <p>
     *            比如 一个线程解析 1000个 User, 那么程序内部 会自动创建 100000/1000个 线程去解析;<br>
     *            必须{@code >}0
     *            </p>
     * @param partitionRunnableBuilder
     *            每个线程做的事情,不能为null
     * @see com.feilong.core.lang.ThreadUtil#execute(List, int, Map, PartitionRunnableBuilder)
     * @since 2.0.0 change name from <b>excute</b> to <b>execute</b>
     */
    <T> void execute(List<T> list,int eachSize,PartitionRunnableBuilder<T> partitionRunnableBuilder);

    //---------------------------------------------------------------

    /**
     * 给定一个待解析的 <code>list</code>,设定每个线程执行多少条 <code>eachSize</code>,传入一些额外的参数 <code>paramsMap</code>,使用自定义的
     * <code>partitionRunnableBuilder</code>,自动<span style="color:green">构造多条线程</span>并运行.
     * 
     * <p>
     * 如果 <code>list</code> 是null,抛出 {@link NullPointerException}<br>
     * 如果 <code>list</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     * 如果 {@code eachSize <=0} ,抛出 {@link IllegalArgumentException}<br>
     * 如果 <code>partitionRunnableBuilder</code> 是null,抛出 {@link NullPointerException}<br>
     * </p>
     * 
     * @param <T>
     *            the generic type
     * @param list
     *            执行解析的list
     * 
     *            <p>
     *            比如 100000个 User,不能为null或者empty
     *            </p>
     * @param eachSize
     *            每个线程执行多少个对象
     * 
     *            <p>
     *            比如 一个线程解析 1000个 User, 那么程序内部 会自动创建 100000/1000个 线程去解析;<br>
     *            必须{@code >}0
     *            </p>
     * @param paramsMap
     *            自定义的相关参数
     * 
     *            <p>
     *            该参数目的是你可以在自定义的 <code>partitionRunnableBuilder</code>中使用;<br>
     *            如果你传入的<code>partitionRunnableBuilder</code>中不需要额外的自定义参数,那么此处可以传入null
     *            </p>
     * @param partitionRunnableBuilder
     *            每个线程做的事情,不能为null
     * @see com.feilong.core.lang.ThreadUtil#execute(List, int, Map, PartitionRunnableBuilder)
     * @since 2.0.0 change name from <b>excute</b> to <b>execute</b>
     */
    <T> void execute(List<T> list,int eachSize,Map<String, ?> paramsMap,PartitionRunnableBuilder<T> partitionRunnableBuilder);

}
