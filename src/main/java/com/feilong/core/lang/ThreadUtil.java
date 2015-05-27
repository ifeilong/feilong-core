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
package com.feilong.core.lang;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.core.lang.entity.ThreadLogSwitch;
import com.feilong.core.log.Slf4jUtil;
import com.feilong.core.tools.json.JsonUtil;
import com.feilong.core.util.Validator;

/**
 * 线程 {@link java.lang.Thread}解析.
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0.0 Dec 17, 2011 2:00:35 AM
 * @see java.lang.Thread
 * @see java.lang.Thread#currentThread()
 * @since 1.0.0
 */
public final class ThreadUtil{

    /** The Constant log. */
    private static final Logger          log           = LoggerFactory.getLogger(ThreadUtil.class);

    /** 简单开关,只显示id name 和活跃线程数量 . */
    private static final ThreadLogSwitch SIMPLE_SWITCH = new ThreadLogSwitch(true, true, true);

    /** Don't let anyone instantiate this class. */
    private ThreadUtil(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 获得线程总数.
     * 
     * @return the top thread group active count
     */
    public static int getTopThreadGroupActiveCount(){

        // 线程组表示一个线程的集合.此外，线程组也可以包含其他线程组.线程组构成一棵树，在树中，除了初始线程组外，每个线程组都有一个父线程组.
        // 允许线程访问有关自己的线程组的信息，但是不允许它访问有关其线程组的父线程组或其他任何线程组的信息.
        Thread currentThread = Thread.currentThread();

        log.debug(JsonUtil.format(getCurrentThreadMapForLog()));

        ThreadGroup currentThreadGroup = currentThread.getThreadGroup();
        ThreadGroup topThreadGroup = getTopThreadGroup(currentThreadGroup);

        log.debug(JsonUtil.format(getThreadGroupInfoMapForLog(topThreadGroup)));
        // 返回此线程组中活动线程的估计数.
        int topThreadGroupActiveCount = topThreadGroup.activeCount();

        return topThreadGroupActiveCount;
    }

    /**
     * 获得最顶层的 ThreadGroup.
     * 
     * @param threadGroup
     *            the thread group
     * @return the top thread group
     */
    private static ThreadGroup getTopThreadGroup(ThreadGroup threadGroup){
        log.debug(JsonUtil.format(getThreadGroupInfoMapForLog(threadGroup)));

        ThreadGroup parentThreadGroup = threadGroup.getParent();
        if (parentThreadGroup == null){
            return threadGroup;
        }else{
            // 递归写法
            return getTopThreadGroup(parentThreadGroup);
        }
    }

    /**
     * 获得 ThreadGroup 对象log.
     * 
     * @param threadGroup
     *            the thread group
     * @return the thread group object log
     */
    public static Map<String, Object> getThreadGroupInfoMapForLog(ThreadGroup threadGroup){

        if (null != threadGroup){
            Map<String, Object> map = new LinkedHashMap<String, Object>();

            // 返回此线程组中活动线程的估计数.
            map.put("threadGroup.activeCount()", threadGroup.activeCount());

            // 返回此线程组中活动线程组的估计数.
            map.put("threadGroup.activeGroupCount()", threadGroup.activeGroupCount());

            // 返回此线程组的最高优先级.
            map.put("threadGroup.getMaxPriority()", threadGroup.getMaxPriority());
            map.put("threadGroup.getName()", threadGroup.getName());

            // 测试此线程组是否为一个后台程序线程组.
            map.put("threadGroup.isDaemon()", threadGroup.isDaemon());
            map.put("threadGroup.toString()", threadGroup.toString());

            return map;
        }
        return null;
    }

    /**
     * 获得 thread 对象log.
     * 
     * @param thread
     *            线程 是程序中的执行线程.Java 虚拟机允许应用程序并发地运行多个执行线程.<br>
     *            每个线程都有一个优先级，高优先级线程的执行优先于低优先级线程.<br>
     *            每个线程都可以或不可以标记为一个守护程序.<br>
     *            当某个线程中运行的代码创建一个新 Thread 对象时，该新线程的初始优先级被设定为创建线程的优先级，并且当且仅当创建线程是守护线程时，新线程才是守护程序.
     * @return the thread object log
     */
    public static Map<String, Object> getThreadInfoMapForLog(Thread thread){
        return getThreadInfoMapForLog(thread, SIMPLE_SWITCH);
    }

    /**
     * 获得 thread 对象log.
     *
     * @param thread
     *            线程 是程序中的执行线程.Java 虚拟机允许应用程序并发地运行多个执行线程.<br>
     *            每个线程都有一个优先级，高优先级线程的执行优先于低优先级线程.<br>
     *            每个线程都可以或不可以标记为一个守护程序.<br>
     *            当某个线程中运行的代码创建一个新 Thread 对象时，该新线程的初始优先级被设定为创建线程的优先级，并且当且仅当创建线程是守护线程时，新线程才是守护程序.
     * @param threadLogSwitch
     *            the thread log config
     * @return the thread object log
     * @since 1.1.1
     */
    public static Map<String, Object> getThreadInfoMapForLog(Thread thread,ThreadLogSwitch threadLogSwitch){

        if (null != thread){

            if (Validator.isNullOrEmpty(threadLogSwitch)){
                threadLogSwitch = SIMPLE_SWITCH;
            }

            Map<String, Object> map = new LinkedHashMap<String, Object>();

            if (threadLogSwitch.getIsShowId()){
                //返回该线程的标识符
                map.put("thread.getId()", thread.getId());
            }

            if (threadLogSwitch.getIsShowName()){
                //返回该线程的名称
                map.put("thread.getName()", thread.getName());
            }

            if (threadLogSwitch.getIsShowActiveCount()){
                // 返回当前线程的线程组中活动线程的数目
                map.put("Thread.activeCount()", Thread.activeCount());
            }

            if (threadLogSwitch.getIsShowIsAlive()){
                //测试线程是否处于活动状态
                map.put("thread.isAlive()", thread.isAlive());
            }

            if (threadLogSwitch.getIsShowIsDaemon()){
                //测试该线程是否为守护线程
                map.put("thread.isDaemon()", thread.isDaemon());
            }

            if (threadLogSwitch.getIsShowIsInterrupted()){
                //测试线程是否已经中断
                map.put("thread.isInterrupted()", thread.isInterrupted());
            }

            if (threadLogSwitch.getIsShowMIN_PRIORITY()){
                map.put("Thread.MIN_PRIORITY", Thread.MIN_PRIORITY);
            }

            if (threadLogSwitch.getIsShowNORM_PRIORITY()){
                map.put("Thread.NORM_PRIORITY", Thread.NORM_PRIORITY);
            }

            if (threadLogSwitch.getIsShowMAX_PRIORITY()){
                map.put("Thread.MAX_PRIORITY", Thread.MAX_PRIORITY);
            }

            if (threadLogSwitch.getIsShowPriority()){

                //返回线程的优先级
                map.put("thread.getPriority()", thread.getPriority());
            }

            if (threadLogSwitch.getIsShowState()){
                //返回该线程的状态
                State state = thread.getState();
                map.put("thread.getState()", state);
            }

            if (threadLogSwitch.getIsShowStackTrace()){
                StackTraceElement[] stackTraceElement = thread.getStackTrace();
                map.put("thread.getStackTrace()", stackTraceElement);
            }

            if (threadLogSwitch.getIsShowThreadGroup()){
                //返回该线程所属的线程组
                map.put("thread.getThreadGroup()", thread.getThreadGroup());
            }

            if (threadLogSwitch.getIsShowAllStackTraces()){
                map.put("Thread.getAllStackTraces()", Thread.getAllStackTraces());
            }

            if (threadLogSwitch.getIsShowToString()){
                map.put("thread.toString()", thread.toString());
            }

            if (threadLogSwitch.getIsShowContextClassLoader()){
                map.put("thread.getContextClassLoader()", thread.getContextClassLoader());
            }

            if (threadLogSwitch.getIsShowUncaughtExceptionHandler()){
                //返回该线程由于未捕获到异常而突然终止时调用的处理程序
                map.put("thread.getUncaughtExceptionHandler()", thread.getUncaughtExceptionHandler());

            }
            if (threadLogSwitch.getIsShowDefaultUncaughtExceptionHandler()){
                map.put("Thread.getDefaultUncaughtExceptionHandler()", Thread.getDefaultUncaughtExceptionHandler());
            }

            return map;
        }
        return null;
    }

    /**
     * 获得 thread 对象log.
     *
     * @return the thread object log
     * @since 1.1.1
     */
    public static Map<String, Object> getCurrentThreadMapForLog(){
        return getCurrentThreadMapForLog(SIMPLE_SWITCH);
    }

    /**
     * 获得 thread 对象log.
     *
     * @param threadLogConfig
     *            the thread log config
     * @return the thread object log
     * @since 1.1.1
     */
    public static Map<String, Object> getCurrentThreadMapForLog(ThreadLogSwitch threadLogConfig){
        // 线程组表示一个线程的集合.此外，线程组也可以包含其他线程组.
        //线程组构成一棵树，在树中，除了初始线程组外，每个线程组都有一个父线程组.
        // 允许线程访问有关自己的线程组的信息，但是不允许它访问有关其线程组的父线程组或其他任何线程组的信息.
        Thread currentThread = Thread.currentThread();
        return getThreadInfoMapForLog(currentThread, threadLogConfig);
    }

    /**
     * 传入currentThread 解析其当前方法的名称.
     * 
     * @param currentThread
     *            Thread thread = Thread.currentThread();
     * @return the current method name
     * @deprecated 不建议使用
     */
    @Deprecated
    public static String getCurrentMethodName(Thread currentThread){
        return getMethodName(currentThread, 3);
    }

    /**
     * 解析其调用者方法的名称.
     * 
     * @param currentThread
     *            Thread thread = Thread.currentThread();
     * @return the caller method name
     * @deprecated 不建议使用
     */
    @Deprecated
    public static String getCallerMethodName(Thread currentThread){
        return getMethodName(currentThread, 1);
    }

    /**
     * 传入currentThread 解析调用方法的名称.
     * 
     * @param currentThread
     *            currentThread
     * @param index
     *            index 索引
     * @return the method name
     * @deprecated 不建议使用
     */
    @Deprecated
    private static String getMethodName(Thread currentThread,int index){
        StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

        if (log.isDebugEnabled()){

            List<String> list = new ArrayList<String>();

            for (StackTraceElement stackTraceElement : stackTraceElements){

                String messagePattern = "({}:{}) [{}()]";//"(%F:%L) [%M()]"
                //stackTraceElement.getClassName()); //com.feilong.core.lang.ThreadUtil
                //stackTraceElement.isNativeMethod());
                String fileName = stackTraceElement.getFileName();
                list.add(Slf4jUtil.formatMessage(
                                messagePattern,
                                fileName,
                                stackTraceElement.getLineNumber(),
                                stackTraceElement.getMethodName()));
            }

            if (log.isDebugEnabled()){
                log.debug(JsonUtil.format(list));
            }

        }
        StackTraceElement stackTraceElement = stackTraceElements[index];
        String methodName = stackTraceElement.getMethodName();
        return methodName;
    }
}
