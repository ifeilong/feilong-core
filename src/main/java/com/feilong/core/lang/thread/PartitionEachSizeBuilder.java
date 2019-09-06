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

/**
 * 用来计算 each size 大小的.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 2.0.0
 */
public interface PartitionEachSizeBuilder{

    /**
     * Builds the.
     *
     * @param totalSize
     *            the total size
     * @return 如果 <code>totalSize<=0</code> 是empty,抛出 {@link IllegalArgumentException}<br>
     */
    int build(int totalSize);
}
