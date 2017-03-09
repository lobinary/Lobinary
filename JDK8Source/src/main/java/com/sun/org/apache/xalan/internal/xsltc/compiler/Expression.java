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
 * $Id: Expression.java,v 1.2.4.1 2005/09/01 14:17:51 pvedula Exp $
 * <p>
 *  $ Id：Expression.java,v 1.2.4.1 2005/09/01 14:17:51 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.BranchHandle;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.GOTO_W;
import com.sun.org.apache.bcel.internal.generic.IFEQ;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeSetType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 * @author Erwin Bolwidt <ejb@klomp.org>
 */
abstract class Expression extends SyntaxTreeNode {
    /**
     * The type of this expression. It is set after calling
     * <code>typeCheck()</code>.
     * <p>
     *  此表达式的类型。它在调用<code> typeCheck()</code>之后设置。
     * 
     */
    protected Type _type;

    /**
     * Instruction handles that comprise the true list.
     * <p>
     *  包含真实列表的指令句柄。
     * 
     */
    protected FlowList _trueList = new FlowList();

    /**
     * Instruction handles that comprise the false list.
     * <p>
     *  包含假列表的指令句柄。
     * 
     */
    protected FlowList _falseList = new FlowList();

    public Type getType() {
        return _type;
    }

    public abstract String toString();

    public boolean hasPositionCall() {
        return false;           // default should be 'false' for StepPattern
    }

    public boolean hasLastCall() {
        return false;
    }

    /**
     * Returns an object representing the compile-time evaluation
     * of an expression. We are only using this for function-available
     * and element-available at this time.
     * <p>
     *  返回表示表达式的编译时评估的对象。我们现在只使用这个功能可用和元素可用。
     * 
     */
    public Object evaluateAtCompileTime() {
        return null;
    }

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
    public final InstructionList compile(ClassGenerator classGen,
                                         MethodGenerator methodGen) {
        final InstructionList result, save = methodGen.getInstructionList();
        methodGen.setInstructionList(result = new InstructionList());
        translate(classGen, methodGen);
        methodGen.setInstructionList(save);
        return result;
    }

    /**
     * Redefined by expressions of type boolean that use flow lists.
     * <p>
     *  由使用流列表的boolean类型的表达式重新定义。
     * 
     */
    public void translateDesynthesized(ClassGenerator classGen,
                                       MethodGenerator methodGen) {
        translate(classGen, methodGen);
        if (_type instanceof BooleanType) {
            desynthesize(classGen, methodGen);
        }
    }

    /**
     * If this expression is of type node-set and it is not a variable
     * reference, then call setStartNode() passing the context node.
     * <p>
     * 如果此表达式是节点集类型,并且它不是变量引用,则调用setStartNode()传递上下文节点。
     * 
     */
    public void startIterator(ClassGenerator classGen,
                                   MethodGenerator methodGen) {
        // Ignore if type is not node-set
        if (_type instanceof NodeSetType == false) {
            return;
        }

        // setStartNode() should not be called if expr is a variable ref
        Expression expr = this;
        if (expr instanceof CastExpr) {
            expr = ((CastExpr) expr).getExpr();
        }
        if (expr instanceof VariableRefBase == false) {
            final InstructionList il = methodGen.getInstructionList();
            il.append(methodGen.loadContextNode());
            il.append(methodGen.setStartNode());
        }
    }

    /**
     * Synthesize a boolean expression, i.e., either push a 0 or 1 onto the
     * operand stack for the next statement to succeed. Returns the handle
     * of the instruction to be backpatched.
     * <p>
     *  合成布尔表达式,即,将0或1推送到操作数堆栈,以便下一语句成功。返回要返回的指令的句柄。
     * 
     */
    public void synthesize(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        _trueList.backPatch(il.append(ICONST_1));
        final BranchHandle truec = il.append(new GOTO_W(null));
        _falseList.backPatch(il.append(ICONST_0));
        truec.setTarget(il.append(NOP));
    }

    public void desynthesize(ClassGenerator classGen,
                             MethodGenerator methodGen) {
        final InstructionList il = methodGen.getInstructionList();
        _falseList.add(il.append(new IFEQ(null)));
    }

    public FlowList getFalseList() {
        return _falseList;
    }

    public FlowList getTrueList() {
        return _trueList;
    }

    public void backPatchFalseList(InstructionHandle ih) {
        _falseList.backPatch(ih);
    }

    public void backPatchTrueList(InstructionHandle ih) {
        _trueList.backPatch(ih);
    }

    /**
     * Search for a primop in the symbol table that matches the method type
     * <code>ctype</code>. Two methods match if they have the same arity.
     * If a primop is overloaded then the "closest match" is returned. The
     * first entry in the vector of primops that has the right arity is
     * considered to be the default one.
     * <p>
     *  在符号表中搜索与方法类型<code> ctype </code>匹配的primop。如果两个方法具有相同的属性,则它们匹配。如果primop重载,则返回"最接近的匹配"。
     * 在具有正确的权限的primops的向量中的第一个条目被认为是默认的。
     */
    public MethodType lookupPrimop(SymbolTable stable, String op,
                                   MethodType ctype) {
        MethodType result = null;
        final Vector primop = stable.lookupPrimop(op);
        if (primop != null) {
            final int n = primop.size();
            int minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                final MethodType ptype = (MethodType) primop.elementAt(i);
                // Skip if different arity
                if (ptype.argsCount() != ctype.argsCount()) {
                    continue;
                }

                // The first method with the right arity is the default
                if (result == null) {
                    result = ptype;             // default method
                }

                // Check if better than last one found
                final int distance = ctype.distanceTo(ptype);
                if (distance < minDistance) {
                    minDistance = distance;
                    result = ptype;
                }
            }
        }
        return result;
    }
}
