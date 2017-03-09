/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002-2004 The Apache Software Foundation.
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
 *  版权所有2002-2004 Apache软件基金会
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XPathVisitable.java,v 1.1.2.1 2005/08/01 01:30:13 jeffsuttor Exp $
 * <p>
 *  $ Id：XPathVisitable.java,v 1.1.2.1 2005/08/01 01:30:13 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

/**
 * A class that implements this interface will call a XPathVisitor
 * for itself and members within it's heararchy.  If the XPathVisitor's
 * method returns false, the sub-member heararchy will not be
 * traversed.
 * <p>
 *  实现这个接口的类将为它自己和它的语法成员调用一个XPathVisitor。如果XPathVisitor的方法返回false,则不会遍历子成员的语法规则。
 * 
 */
public interface XPathVisitable
{
        /**
         * This will traverse the heararchy, calling the visitor for
         * each member.  If the called visitor method returns
         * false, the subtree should not be called.
         *
         * <p>
         *  这将遍历听证,调用每个成员的访问者。如果被调用的visitor方法返回false,则不应该调用子树。
         * 
         * @param owner The owner of the visitor, where that path may be
         *              rewritten if needed.
         * @param visitor The visitor whose appropriate method will be called.
         */
        public void callVisitors(ExpressionOwner owner, XPathVisitor visitor);
}
