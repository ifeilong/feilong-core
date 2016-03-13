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
 * Class that exposes the Spring version. Fetches the "Implementation-Version" manifest attribute from the jar file.
 * <p>
 * Note that some ClassLoaders do not expose the package metadata, hence this class might not be able to determine the Spring version in all
 * environments. Consider using a reflection-based check instead: For example, checking for the presence of a specific Spring 2.0 method
 * that you intend to call.
 * 
 * @author feilong
 * @version 1.0 Aug 12, 2013 1:21:20 AM
 */
public class FeiLongVersion{

    /** Don't let anyone instantiate this class. */
    private FeiLongVersion(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * Return the full version feilong of the present feilong codebase, or <code>null</code> if it cannot be determined.
     * 
     * @return the version
     * @see java.lang.Package#getImplementationVersion()
     */
    public static String getVersion(){
        Package pkg = FeiLongVersion.class.getPackage();
        return pkg != null ? pkg.getImplementationVersion() : null;
    }

}