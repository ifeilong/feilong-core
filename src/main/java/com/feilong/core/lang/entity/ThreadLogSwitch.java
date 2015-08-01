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
package com.feilong.core.lang.entity;

import java.io.Serializable;

/**
 * Switch 判断.
 *
 * @author feilong
 * @version 1.1.1 2015年4月10日 下午3:36:49
 * @since 1.1.1
 */
public final class ThreadLogSwitch implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 288232184048495608L;

    /** The is show id. */
    private boolean           isShowId;

    /** The is show name. */
    private boolean           isShowName;

    /** The is show active count. */
    private boolean           isShowActiveCount;

    /** The is show is alive. */
    private boolean           isShowIsAlive;

    /** The is show is daemon. */
    private boolean           isShowIsDaemon;

    /** The is show is interrupted. */
    private boolean           isShowIsInterrupted;

    private boolean           isShowMinPriority;

    private boolean           isShowNormPriority;

    private boolean           isShowMaxPriority;

    /** The is show priority. */
    private boolean           isShowPriority;

    /** The is show state. */
    private boolean           isShowState;

    /** The is show stack trace. */
    private boolean           isShowStackTrace;

    /** The is show thread group. */
    private boolean           isShowThreadGroup;

    /** The is show uncaught exception handler. */
    private boolean           isShowUncaughtExceptionHandler;

    /** The is show all stack traces. */
    private boolean           isShowAllStackTraces;

    /** The is show to string. */
    private boolean           isShowToString;

    /** The is show context class loader. */
    private boolean           isShowContextClassLoader;

    /** The is show default uncaught exception handler. */
    private boolean           isShowDefaultUncaughtExceptionHandler;

    /**
     * The Constructor.
     */
    public ThreadLogSwitch(){
        super();
    }

    /**
     * The Constructor.
     *
     * @param isShowId
     *            the is show id
     * @param isShowName
     *            the is show name
     * @param isShowActiveCount
     *            the is show active count
     */
    public ThreadLogSwitch(boolean isShowId, boolean isShowName, boolean isShowActiveCount){
        super();
        this.isShowId = isShowId;
        this.isShowName = isShowName;
        this.isShowActiveCount = isShowActiveCount;
    }

    /**
     * 获得 is show id.
     *
     * @return the isShowId
     */
    public boolean getIsShowId(){
        return isShowId;
    }

    /**
     * 设置 is show id.
     *
     * @param isShowId
     *            the isShowId to set
     */
    public void setIsShowId(boolean isShowId){
        this.isShowId = isShowId;
    }

    /**
     * 获得 is show name.
     *
     * @return the isShowName
     */
    public boolean getIsShowName(){
        return isShowName;
    }

    /**
     * 设置 is show name.
     *
     * @param isShowName
     *            the isShowName to set
     */
    public void setIsShowName(boolean isShowName){
        this.isShowName = isShowName;
    }

    /**
     * 获得 is show active count.
     *
     * @return the isShowActiveCount
     */
    public boolean getIsShowActiveCount(){
        return isShowActiveCount;
    }

    /**
     * 设置 is show active count.
     *
     * @param isShowActiveCount
     *            the isShowActiveCount to set
     */
    public void setIsShowActiveCount(boolean isShowActiveCount){
        this.isShowActiveCount = isShowActiveCount;
    }

    /**
     * 获得 is show is alive.
     *
     * @return the isShowIsAlive
     */
    public boolean getIsShowIsAlive(){
        return isShowIsAlive;
    }

    /**
     * 设置 is show is alive.
     *
     * @param isShowIsAlive
     *            the isShowIsAlive to set
     */
    public void setIsShowIsAlive(boolean isShowIsAlive){
        this.isShowIsAlive = isShowIsAlive;
    }

    /**
     * 获得 is show is daemon.
     *
     * @return the isShowIsDaemon
     */
    public boolean getIsShowIsDaemon(){
        return isShowIsDaemon;
    }

    /**
     * 设置 is show is daemon.
     *
     * @param isShowIsDaemon
     *            the isShowIsDaemon to set
     */
    public void setIsShowIsDaemon(boolean isShowIsDaemon){
        this.isShowIsDaemon = isShowIsDaemon;
    }

    /**
     * 获得 is show is interrupted.
     *
     * @return the isShowIsInterrupted
     */
    public boolean getIsShowIsInterrupted(){
        return isShowIsInterrupted;
    }

    /**
     * 设置 is show is interrupted.
     *
     * @param isShowIsInterrupted
     *            the isShowIsInterrupted to set
     */
    public void setIsShowIsInterrupted(boolean isShowIsInterrupted){
        this.isShowIsInterrupted = isShowIsInterrupted;
    }

    /**
     * 获得 is show mi n_ priority.
     *
     * @return the isShowMIN_PRIORITY
     */
    public boolean isShowMinPriority(){
        return isShowMinPriority;
    }

    /**
     * 设置 is show mi n_ priority.
     *
     * @param isShowMinPriority
     *            the isShowMIN_PRIORITY to set
     */
    public void setIsShowMinPriority(boolean isShowMinPriority){
        this.isShowMinPriority = isShowMinPriority;
    }

    /**
     * 获得 is show nor m_ priority.
     *
     * @return the isShowNORM_PRIORITY
     */
    public boolean isShowNormPriority(){
        return isShowNormPriority;
    }

    /**
     * 设置 is show nor m_ priority.
     *
     * @param isShowNormPriority
     *            the isShowNORM_PRIORITY to set
     */
    public void setIsShowNormPriority(boolean isShowNormPriority){
        this.isShowNormPriority = isShowNormPriority;
    }

    /**
     * 获得 is show ma x_ priority.
     *
     * @return the isShowMAX_PRIORITY
     */
    public boolean isShowMaxPriority(){
        return isShowMaxPriority;
    }

    /**
     * 设置 is show ma x_ priority.
     *
     * @param isShowMaxPriority
     *            the isShowMAX_PRIORITY to set
     */
    public void setIsShowMaxPriority(boolean isShowMaxPriority){
        this.isShowMaxPriority = isShowMaxPriority;
    }

    /**
     * 获得 is show priority.
     *
     * @return the isShowPriority
     */
    public boolean getIsShowPriority(){
        return isShowPriority;
    }

    /**
     * 设置 is show priority.
     *
     * @param isShowPriority
     *            the isShowPriority to set
     */
    public void setIsShowPriority(boolean isShowPriority){
        this.isShowPriority = isShowPriority;
    }

    /**
     * 获得 is show state.
     *
     * @return the isShowState
     */
    public boolean getIsShowState(){
        return isShowState;
    }

    /**
     * 设置 is show state.
     *
     * @param isShowState
     *            the isShowState to set
     */
    public void setIsShowState(boolean isShowState){
        this.isShowState = isShowState;
    }

    /**
     * 获得 is show stack trace.
     *
     * @return the isShowStackTrace
     */
    public boolean getIsShowStackTrace(){
        return isShowStackTrace;
    }

    /**
     * 设置 is show stack trace.
     *
     * @param isShowStackTrace
     *            the isShowStackTrace to set
     */
    public void setIsShowStackTrace(boolean isShowStackTrace){
        this.isShowStackTrace = isShowStackTrace;
    }

    /**
     * 获得 is show thread group.
     *
     * @return the isShowThreadGroup
     */
    public boolean getIsShowThreadGroup(){
        return isShowThreadGroup;
    }

    /**
     * 设置 is show thread group.
     *
     * @param isShowThreadGroup
     *            the isShowThreadGroup to set
     */
    public void setIsShowThreadGroup(boolean isShowThreadGroup){
        this.isShowThreadGroup = isShowThreadGroup;
    }

    /**
     * 获得 is show uncaught exception handler.
     *
     * @return the isShowUncaughtExceptionHandler
     */
    public boolean getIsShowUncaughtExceptionHandler(){
        return isShowUncaughtExceptionHandler;
    }

    /**
     * 设置 is show uncaught exception handler.
     *
     * @param isShowUncaughtExceptionHandler
     *            the isShowUncaughtExceptionHandler to set
     */
    public void setIsShowUncaughtExceptionHandler(boolean isShowUncaughtExceptionHandler){
        this.isShowUncaughtExceptionHandler = isShowUncaughtExceptionHandler;
    }

    /**
     * 获得 is show all stack traces.
     *
     * @return the isShowAllStackTraces
     */
    public boolean getIsShowAllStackTraces(){
        return isShowAllStackTraces;
    }

    /**
     * 设置 is show all stack traces.
     *
     * @param isShowAllStackTraces
     *            the isShowAllStackTraces to set
     */
    public void setIsShowAllStackTraces(boolean isShowAllStackTraces){
        this.isShowAllStackTraces = isShowAllStackTraces;
    }

    /**
     * 获得 is show to string.
     *
     * @return the isShowToString
     */
    public boolean getIsShowToString(){
        return isShowToString;
    }

    /**
     * 设置 is show to string.
     *
     * @param isShowToString
     *            the isShowToString to set
     */
    public void setIsShowToString(boolean isShowToString){
        this.isShowToString = isShowToString;
    }

    /**
     * 获得 is show context class loader.
     *
     * @return the isShowContextClassLoader
     */
    public boolean getIsShowContextClassLoader(){
        return isShowContextClassLoader;
    }

    /**
     * 设置 is show context class loader.
     *
     * @param isShowContextClassLoader
     *            the isShowContextClassLoader to set
     */
    public void setIsShowContextClassLoader(boolean isShowContextClassLoader){
        this.isShowContextClassLoader = isShowContextClassLoader;
    }

    /**
     * 获得 is show default uncaught exception handler.
     *
     * @return the isShowDefaultUncaughtExceptionHandler
     */
    public boolean getIsShowDefaultUncaughtExceptionHandler(){
        return isShowDefaultUncaughtExceptionHandler;
    }

    /**
     * 设置 is show default uncaught exception handler.
     *
     * @param isShowDefaultUncaughtExceptionHandler
     *            the isShowDefaultUncaughtExceptionHandler to set
     */
    public void setIsShowDefaultUncaughtExceptionHandler(boolean isShowDefaultUncaughtExceptionHandler){
        this.isShowDefaultUncaughtExceptionHandler = isShowDefaultUncaughtExceptionHandler;
    }
}