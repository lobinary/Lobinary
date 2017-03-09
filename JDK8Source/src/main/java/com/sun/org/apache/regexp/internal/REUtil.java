/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.regexp.internal;

/**
 * This is a class that contains utility helper methods for this package.
 *
 * <p>
 *  这是一个包含此包的实用程序帮助程序方法的类。
 * 
 * 
 * @author <a href="mailto:jonl@muppetlabs.com">Jonathan Locke</a>
 */
public class REUtil
{
    /** complex: */
    private static final String complexPrefix = "complex:";

    /**
     * Creates a regular expression, permitting simple or complex syntax
     * <p>
     *  创建正则表达式,允许简单或复杂的语法
     * 
     * 
     * @param expression The expression, beginning with a prefix if it's complex or
     * having no prefix if it's simple
     * @param matchFlags Matching style flags
     * @return The regular expression object
     * @exception RESyntaxException thrown in case of error
     */
    public static RE createRE(String expression, int matchFlags) throws RESyntaxException
    {
        if (expression.startsWith(complexPrefix))
        {
            return new RE(expression.substring(complexPrefix.length()), matchFlags);
        }
        return new RE(RE.simplePatternToFullRegularExpression(expression), matchFlags);
    }

    /**
     * Creates a regular expression, permitting simple or complex syntax
     * <p>
     *  创建正则表达式,允许简单或复杂的语法
     * 
     * @param expression The expression, beginning with a prefix if it's complex or
     * having no prefix if it's simple
     * @return The regular expression object
     * @exception RESyntaxException thrown in case of error
     */
    public static RE createRE(String expression) throws RESyntaxException
    {
        return createRE(expression, RE.MATCH_NORMAL);
    }
}
