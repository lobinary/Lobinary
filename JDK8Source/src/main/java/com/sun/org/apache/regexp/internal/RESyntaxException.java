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
 */

package com.sun.org.apache.regexp.internal;

/**
 * Exception thrown to indicate a syntax error in a regular expression.
 * This is a non-checked exception because you should only have problems compiling
 * a regular expression during development.
 * If you are making regular expresion programs dynamically then you can catch it
 * if you wish. But should not be forced to.
 *
 * <p>
 * 
 * 
 * @author <a href="mailto:jonl@muppetlabs.com">Jonathan Locke</a>
 * @author <a href="mailto:gholam@xtra.co.nz>Michael McCallum</a>
 */
public class RESyntaxException extends RuntimeException
{
    /**
     * Constructor.
     * <p>
     *  抛出异常以指示正则表达式中的语法错误。这是一个未检查的异常,因为在开发期间只应该有编译正则表达式的问题。如果你正在定期表达程序动态,那么你可以赶上它,如果你想要的。但不应该被迫去。
     * 
     * 
     * @param s Further description of the syntax error
     */
    public RESyntaxException(String s)
    {
        super("Syntax error: " + s);
    }
}
