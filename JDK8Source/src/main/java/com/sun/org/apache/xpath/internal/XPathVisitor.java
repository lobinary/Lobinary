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
 * $Id: XPathVisitor.java,v 1.1.2.1 2005/08/01 01:30:11 jeffsuttor Exp $
 * <p>
 *  $ Id：XPathVisitor.java,v 1.1.2.1 2005/08/01 01:30:11 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import com.sun.org.apache.xpath.internal.axes.LocPathIterator;
import com.sun.org.apache.xpath.internal.axes.UnionPathIterator;
import com.sun.org.apache.xpath.internal.functions.Function;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.org.apache.xpath.internal.operations.Operation;
import com.sun.org.apache.xpath.internal.operations.UnaryOperation;
import com.sun.org.apache.xpath.internal.operations.Variable;
import com.sun.org.apache.xpath.internal.patterns.NodeTest;
import com.sun.org.apache.xpath.internal.patterns.StepPattern;
import com.sun.org.apache.xpath.internal.patterns.UnionPattern;

/**
 * A derivation from this class can be passed to a class that implements
 * the XPathVisitable interface, to have the appropriate method called
 * for each component of the XPath.  Aside from possible other uses, the
 * main intention is to provide a reasonable means to perform expression
 * rewriting.
 *
 * <p>Each method has the form
 * <code>boolean visitComponentType(ExpressionOwner owner, ComponentType compType)</code>.
 * The ExpressionOwner argument is the owner of the component, and can
 * be used to reset the expression for rewriting.  If a method returns
 * false, the sub hierarchy will not be traversed.</p>
 *
 * <p>This class is meant to be a base class that will be derived by concrete classes,
 * and doesn't much except return true for each method.</p>
 * <p>
 *  可以将此类的派生类传递给实现XPathVisitable接口的类,以便为XPath的每个组件调用适当的方法。除了可能的其他用途,主要目的是提供一种合理的方法来执行表达式重写。
 * 
 *  <p>每个方法都具有<code> boolean visitComponentType(ExpressionOwner owner,ComponentType compType)</code>的形式。
 *  ExpressionOwner参数是组件的所有者,可用于重置表达式以进行重写。如果方法返回false,则不会遍历子层次结构。</p>。
 * 
 * <p>这个类是一个基类,它将通过具体的类派生,并且没有多少,除非每个方法都返回true。</p>
 * 
 */
public class XPathVisitor
{
        /**
         * Visit a LocationPath.
         * <p>
         *  访问LocationPath。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param path The LocationPath object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitLocationPath(ExpressionOwner owner, LocPathIterator path)
        {
                return true;
        }

        /**
         * Visit a UnionPath.
         * <p>
         *  访问UnionPath。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param path The UnionPath object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitUnionPath(ExpressionOwner owner, UnionPathIterator path)
        {
                return true;
        }

        /**
         * Visit a step within a location path.
         * <p>
         *  访问位置路径中的步骤。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param step The Step object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitStep(ExpressionOwner owner, NodeTest step)
        {
                return true;
        }

        /**
         * Visit a predicate within a location path.  Note that there isn't a
         * proper unique component for predicates, and that the expression will
         * be called also for whatever type Expression is.
         *
         * <p>
         *  访问位置路径中的谓词。注意,谓词没有一个适当的唯一组件,并且表达式也将被称为Expression是任何类型。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param pred The predicate object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitPredicate(ExpressionOwner owner, Expression pred)
        {
                return true;
        }

        /**
         * Visit a binary operation.
         * <p>
         *  访问二进制操作。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param op The operation object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitBinaryOperation(ExpressionOwner owner, Operation op)
        {
                return true;
        }

        /**
         * Visit a unary operation.
         * <p>
         *  访问一元操作。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param op The operation object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitUnaryOperation(ExpressionOwner owner, UnaryOperation op)
        {
                return true;
        }

        /**
         * Visit a variable reference.
         * <p>
         *  访问变量引用。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param var The variable reference object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitVariableRef(ExpressionOwner owner, Variable var)
        {
                return true;
        }

        /**
         * Visit a function.
         * <p>
         *  访问函数。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param func The function reference object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitFunction(ExpressionOwner owner, Function func)
        {
                return true;
        }

        /**
         * Visit a match pattern.
         * <p>
         *  访问匹配模式。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param pattern The match pattern object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitMatchPattern(ExpressionOwner owner, StepPattern pattern)
        {
                return true;
        }

        /**
         * Visit a union pattern.
         * <p>
         *  访问联合模式。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param pattern The union pattern object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitUnionPattern(ExpressionOwner owner, UnionPattern pattern)
        {
                return true;
        }

        /**
         * Visit a string literal.
         * <p>
         *  访问字符串字面量。
         * 
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param str The string literal object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitStringLiteral(ExpressionOwner owner, XString str)
        {
                return true;
        }


        /**
         * Visit a number literal.
         * <p>
         *  访问数字文字。
         * 
         * @param owner The owner of the expression, to which the expression can
         *              be reset if rewriting takes place.
         * @param num The number literal object.
         * @return true if the sub expressions should be traversed.
         */
        public boolean visitNumberLiteral(ExpressionOwner owner, XNumber num)
        {
                return true;
        }


}
