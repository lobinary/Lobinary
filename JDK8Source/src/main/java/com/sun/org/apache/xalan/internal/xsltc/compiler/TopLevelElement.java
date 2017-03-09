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
 * $Id: TopLevelElement.java,v 1.5 2005/09/28 13:48:17 pvedula Exp $
 * <p>
 *  $ Id：TopLevelElement.java,v 1.5 2005/09/28 13:48:17 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

class TopLevelElement extends SyntaxTreeNode {

    /*
     * List of dependencies with other variables, parameters or
     * keys defined at the top level.
     * <p>
     *  与其他变量,在顶层定义的参数或键的依赖性列表。
     * 
     */
    protected Vector _dependencies = null;

    /**
     * Type check all the children of this node.
     * <p>
     *  类型检查此节点的所有子节点。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        return typeCheckContents(stable);
    }

    /**
     * Translate this node into JVM bytecodes.
     * <p>
     *  将此节点转换为JVM字节码。
     * 
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        ErrorMsg msg = new ErrorMsg(ErrorMsg.NOT_IMPLEMENTED_ERR,
                                    getClass(), this);
        getParser().reportError(FATAL, msg);
    }

    /**
     * Translate this node into a fresh instruction list.
     * The original instruction list is saved and restored.
     * <p>
     *  将此节点转换为新的指令列表。保存并恢复原始指令列表。
     * 
     */
    public InstructionList compile(ClassGenerator classGen,
                                   MethodGenerator methodGen) {
        final InstructionList result, save = methodGen.getInstructionList();
        methodGen.setInstructionList(result = new InstructionList());
        translate(classGen, methodGen);
        methodGen.setInstructionList(save);
        return result;
    }

    public void display(int indent) {
        indent(indent);
        Util.println("TopLevelElement");
        displayContents(indent + IndentIncrement);
    }

    /**
     * Add a dependency with other top-level elements like
     * variables, parameters or keys.
     * <p>
     *  与其他顶级元素(如变量,参数或键)添加依赖关系。
     * 
     */
    public void addDependency(TopLevelElement other) {
        if (_dependencies == null) {
            _dependencies = new Vector();
        }
        if (!_dependencies.contains(other)) {
            _dependencies.addElement(other);
        }
    }

    /**
     * Get the list of dependencies with other top-level elements
     * like variables, parameteres or keys.
     * <p>
     *  获取与其他顶级元素(如变量,参数或键)的依赖关系列表。
     */
    public Vector getDependencies() {
        return _dependencies;
    }

}
