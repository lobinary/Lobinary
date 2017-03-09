/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: Pattern.java,v 1.2.4.1 2005/09/12 11:00:31 pvedula Exp $
 * <p>
 *  $ Id：Pattern.java,v 1.2.4.1 2005/09/12 11:00:31 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public abstract class Pattern extends Expression {
    /**
     * Returns the type of a pattern, which is always a <code>NodeType</code>.
     * A <code>NodeType</code> has a number of subtypes defined by
     * <code>NodeType._type</code> corresponding to each type of node.
     * <p>
     *  返回模式的类型,它总是一个<code> NodeType </code>。
     *  <code> NodeType </code>具有由每个类型的节点对应的<code> NodeType._type </code>定义的多个子类型。
     * 
     */
    public abstract Type typeCheck(SymbolTable stable) throws TypeCheckError;

    /**
     * Translate this node into JVM bytecodes. Patterns are translated as
     * boolean expressions with true/false lists. Before calling
     * <code>translate</code> on a pattern, make sure that the node being
     * matched is on top of the stack. After calling <code>translate</code>,
     * make sure to backpatch both true and false lists. True lists are the
     * default, in the sense that they always <em>"fall through"</em>. If this
     * is not the intended semantics (e.g., see
     * {@link com.sun.org.apache.xalan.internal.xsltc.compiler.AlternativePattern#translate})
     * then a GOTO must be appended to the instruction list after calling
     * <code>translate</code>.
     * <p>
     * 将此节点转换为JVM字节码。模式被转换为带有真/假列表的布尔表达式。在模式上调用<code> translate </code>之前,请确保匹配的节点在堆栈顶部。
     * 调用<code> translate </code>之后,请确保同时备份true和false列表。 True列表是默认列表,因为它们总是<em>"通过"</em>。
     * 如果这不是预期的语义(例如,参见{@link com.sun.org.apache.xalan.internal.xsltc.compiler.AlternativePattern#translate}
     * ),那么在调用<code>之后必须将GOTO附加到指令列表翻译</code>。
     * 调用<code> translate </code>之后,请确保同时备份true和false列表。 True列表是默认列表,因为它们总是<em>"通过"</em>。
     */
    public abstract void translate(ClassGenerator classGen,
                                   MethodGenerator methodGen);

    /**
     * Returns the priority of this pattern (section 5.5 in the XSLT spec).
     * <p>
     * 
     */
    public abstract double getPriority();
}
