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
 * 默认的 {@link PartitionRunnableBuilder}.
 * 
 * <h3>重构:</h3>
 * 
 * <blockquote>
 * <p>
 * 对于以下代码:
 * </p>
 * 
 * <pre class="code">
 * 
 * ThreadUtil.execute(list, 5, new PartitionRunnableBuilder{@code <String>}(){
 * 
 *     public Runnable build(final List{@code <String>} perBatchList,PartitionThreadEntity partitionThreadEntity,Map{@code <String, ?>} paramsMap){
 * 
 *         return new Runnable(){
 * 
 *             public void run(){
 *                 map.putAll(handle(perBatchList, noList));
 *             }
 *         };
 *     }
 * });
 * 
 * </pre>
 * 
 * <b>可以重构成:</b>
 * 
 * <pre class="code">
 * ThreadUtil.execute(list, 5, new DefaultPartitionRunnableBuilder{@code <>}(new Call{@code <String>}(){
 * 
 *     public void call(List{@code <String>} perBatchList,PartitionThreadEntity partitionThreadEntity,Map{@code <String, ?>} paramsMap){
 *         map.putAll(handle(perBatchList, noList));
 *     }
 * }));
 * </pre>
 * 
 * 从 13 行 简写到 6 行
 * 
 * </blockquote>
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @param <T>
 *            the generic type
 * @since 2.0.0
 */
public class DefaultPartitionRunnableBuilder<T> implements PartitionRunnableBuilder<T>{

    /** The partition per handler. */
    private final PartitionPerHandler<T> partitionPerHandler;

    //---------------------------------------------------------------

    /**
     * Instantiates a new simple partition runnable builder.
     *
     * @param partitionPerHandler
     *            the partition per handler
     */
    public DefaultPartitionRunnableBuilder(PartitionPerHandler<T> partitionPerHandler){
        super();
        this.partitionPerHandler = partitionPerHandler;
    }

    //---------------------------------------------------------------

    /**
     * Builds the.
     *
     * @param perBatchList
     *            the per batch list
     * @param partitionThreadEntity
     *            the partition thread entity
     * @param paramsMap
     *            the params map
     * @return the runnable
     */
    @Override
    public Runnable build(final List<T> perBatchList,final PartitionThreadEntity partitionThreadEntity,final Map<String, ?> paramsMap){
        return new Runnable(){

            @Override
            public void run(){
                partitionPerHandler.handle(perBatchList, partitionThreadEntity, paramsMap);
            }
        };
    }
}
