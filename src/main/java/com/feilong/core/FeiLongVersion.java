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
package com.feilong.core;

/**
 * 暴露Feilong version.
 * 
 * <p>
 * 从jar里面提取 "Implementation-Version" manifest属性.
 * </p>
 * 
 * <h3>注意:</h3>
 * 
 * <blockquote>
 * <p>
 * 一些ClassLoaders不会expose the package metadata,因此这个类可能不能判断所有环境的Feilong version.<br>
 * Consider using a reflection-based check instead: <br>
 * 比如, checking for the presence of a specific Feilong 1.0 method that you intend to call.
 * </p>
 * </blockquote>
 * 
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 * @since 1.0.8
 */
public final class FeiLongVersion{

    /** Don't let anyone instantiate this class. */
    private FeiLongVersion(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 返回当前feilong代码的 version.
     * 
     * @return 如果不能determined,返回 <code>null</code>
     * @see java.lang.Package#getImplementationVersion()
     */
    public static String getVersion(){
        Package pkg = FeiLongVersion.class.getPackage();
        return pkg != null ? pkg.getImplementationVersion() : null;
    }

}