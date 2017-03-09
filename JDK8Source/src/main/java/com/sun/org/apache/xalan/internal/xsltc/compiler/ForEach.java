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
 * $Id: ForEach.java,v 1.2.4.1 2005/09/01 15:23:46 pvedula Exp $
 * <p>
 *  $ Id：ForEach.java,v 1.2.4.1 2005/09/01 15:23:46 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Enumeration;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.BranchHandle;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.IFGT;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ResultTreeType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class ForEach extends Instruction {

    private Expression _select;
    private Type       _type;

    public void display(int indent) {
        indent(indent);
        Util.println("ForEach");
        indent(indent + IndentIncrement);
        Util.println("select " + _select.toString());
        displayContents(indent + IndentIncrement);
    }

    public void parseContents(Parser parser) {
        _select = parser.parseExpression(this, "select", null);

        parseChildren(parser);

        // make sure required attribute(s) have been set
        if (_select.isDummy()) {
            reportError(this, parser, ErrorMsg.REQUIRED_ATTR_ERR, "select");
        }
    }

    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        _type = _select.typeCheck(stable);

        if (_type instanceof ReferenceType || _type instanceof NodeType) {
            _select = new CastExpr(_select, Type.NodeSet);
            typeCheckContents(stable);
            return Type.Void;
        }
        if (_type instanceof NodeSetType||_type instanceof ResultTreeType) {
            typeCheckContents(stable);
            return Type.Void;
        }
        throw new TypeCheckError(this);
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // Save current node and current iterator on the stack
        il.append(methodGen.loadCurrentNode());
        il.append(methodGen.loadIterator());

        // Collect sort objects associated with this instruction
        final Vector sortObjects = new Vector();
        Enumeration children = elements();
        while (children.hasMoreElements()) {
            final Object child = children.nextElement();
            if (child instanceof Sort) {
                sortObjects.addElement(child);
            }
        }

        if ((_type != null) && (_type instanceof ResultTreeType)) {
            // Store existing DOM on stack - must be restored when loop is done
            il.append(methodGen.loadDOM());

            // <xsl:sort> cannot be applied to a result tree - issue warning
            if (sortObjects.size() > 0) {
                ErrorMsg msg = new ErrorMsg(ErrorMsg.RESULT_TREE_SORT_ERR,this);
                getParser().reportError(WARNING, msg);
            }

            // Put the result tree on the stack (DOM)
            _select.translate(classGen, methodGen);
            // Get an iterator for the whole DOM - excluding the root node
            _type.translateTo(classGen, methodGen, Type.NodeSet);
            // Store the result tree as the default DOM
            il.append(SWAP);
            il.append(methodGen.storeDOM());
        }
        else {
            // Compile node iterator
            if (sortObjects.size() > 0) {
                Sort.translateSortIterator(classGen, methodGen,
                                           _select, sortObjects);
            }
            else {
                _select.translate(classGen, methodGen);
            }

            if (_type instanceof ReferenceType == false) {
                il.append(methodGen.loadContextNode());
                il.append(methodGen.setStartNode());
            }
        }


        // Overwrite current iterator
        il.append(methodGen.storeIterator());

        // Give local variables (if any) default values before starting loop
        initializeVariables(classGen, methodGen);

        final BranchHandle nextNode = il.append(new GOTO(null));
        final InstructionHandle loop = il.append(NOP);

        translateContents(classGen, methodGen);

        nextNode.setTarget(il.append(methodGen.loadIterator()));
        il.append(methodGen.nextNode());
        il.append(DUP);
        il.append(methodGen.storeCurrentNode());
        il.append(new IFGT(loop));

        // Restore current DOM (if result tree was used instead for this loop)
        if ((_type != null) && (_type instanceof ResultTreeType)) {
            il.append(methodGen.storeDOM());
        }

        // Restore current node and current iterator from the stack
        il.append(methodGen.storeIterator());
        il.append(methodGen.storeCurrentNode());
    }

    /**
     * The code that is generated by nested for-each loops can appear to some
     * JVMs as if it is accessing un-initialized variables. We must add some
     * code that pushes the default variable value on the stack and pops it
     * into the variable slot. This is done by the Variable.initialize()
     * method. The code that we compile for this loop looks like this:
     *
     *           initialize iterator
     *           initialize variables <-- HERE!!!
     *           goto   Iterate
     *  Loop:    :
     *           : (code for <xsl:for-each> contents)
     *           :
     *  Iterate: node = iterator.next();
     *           if (node != END) goto Loop
     * <p>
     *  由嵌套for-each循环生成的代码可能会出现在一些JVM上,就像它正在访问未初始化的变量一样。我们必须添加一些代码,推动堆栈上的默认变量值,并将其弹出到变量槽。
     * 这是通过Variable.initialize()方法。我们为这个循环编译的代码如下所示：。
     * 
     */
    public void initializeVariables(ClassGenerator classGen,
                                   MethodGenerator methodGen) {
        final int n = elementCount();
        for (int i = 0; i < n; i++) {
            final Object child = getContents().elementAt(i);
            if (child instanceof Variable) {
                Variable var = (Variable)child;
                var.initialize(classGen, methodGen);
            }
        }
    }

}
