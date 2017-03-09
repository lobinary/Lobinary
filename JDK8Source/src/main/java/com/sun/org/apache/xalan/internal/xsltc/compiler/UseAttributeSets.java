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
 * $Id: UseAttributeSets.java,v 1.5 2005/09/28 13:48:17 pvedula Exp $
 * <p>
 *  $ Id：UseAttributeSets.java,v 1.5 2005/09/28 13:48:17 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.StringTokenizer;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class UseAttributeSets extends Instruction {

    // Only error that can occur:
    private final static String ATTR_SET_NOT_FOUND =
        "";

    // Contains the names of all references attribute sets
    private final Vector _sets = new Vector(2);

    /**
     * Constructur - define initial attribute sets to use
     * <p>
     *  Constructur  - 定义要使用的初始属性集
     * 
     */
    public UseAttributeSets(String setNames, Parser parser) {
        setParser(parser);
        addAttributeSets(setNames);
    }

    /**
     * This method is made public to enable an AttributeSet object to merge
     * itself with another AttributeSet (including any other AttributeSets
     * the two may inherit from).
     * <p>
     *  这个方法是公开的,使一个AttributeSet对象能够与另一个AttributeSet(包括两个可能继承的AttributeSet)合并。
     * 
     */
    public void addAttributeSets(String setNames) {
        if ((setNames != null) && (!setNames.equals(Constants.EMPTYSTRING))) {
            final StringTokenizer tokens = new StringTokenizer(setNames);
            while (tokens.hasMoreTokens()) {
                final QName qname =
                    getParser().getQNameIgnoreDefaultNs(tokens.nextToken());
                _sets.add(qname);
            }
        }
    }

    /**
     * Do nada.
     * <p>
     *  做纳达。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        return Type.Void;
    }

    /**
     * Generate a call to the method compiled for this attribute set
     * <p>
     *  生成对为此属性集编译的方法的调用
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {

        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        final SymbolTable symbolTable = getParser().getSymbolTable();

        // Go through each attribute set and generate a method call
        for (int i=0; i<_sets.size(); i++) {
            // Get the attribute set name
            final QName name = (QName)_sets.elementAt(i);
            // Get the AttributeSet reference from the symbol table
            final AttributeSet attrs = symbolTable.lookupAttributeSet(name);
            // Compile the call to the set's method if the set exists
            if (attrs != null) {
                final String methodName = attrs.getMethodName();
                il.append(classGen.loadTranslet());
                il.append(methodGen.loadDOM());
                il.append(methodGen.loadIterator());
                il.append(methodGen.loadHandler());
                il.append(methodGen.loadCurrentNode());
                final int method = cpg.addMethodref(classGen.getClassName(),
                                                    methodName, ATTR_SET_SIG);
                il.append(new INVOKESPECIAL(method));
            }
            // Generate an error if the attribute set does not exist
            else {
                final Parser parser = getParser();
                final String atrs = name.toString();
                reportError(this, parser, ErrorMsg.ATTRIBSET_UNDEF_ERR, atrs);
            }
        }
    }
}
