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
package com.feilong.test.rule;

import org.junit.runners.model.Statement;

/**
 * The Class RepeatStatement.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class RepeatStatement extends Statement{

    /** The statement. */
    private final Statement statement;

    /** The repeat. */
    private final int       repeat;

    /**
     * Instantiates a new repeat statement.
     *
     * @param statement
     *            the statement
     * @param repeat
     *            the repeat
     */
    public RepeatStatement(Statement statement, int repeat){
        this.statement = statement;
        this.repeat = repeat;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.junit.runners.model.Statement#evaluate()
     */
    @Override
    public void evaluate() throws Throwable{
        for (int i = 0; i < repeat; i++){
            statement.evaluate();
        }
    }

}