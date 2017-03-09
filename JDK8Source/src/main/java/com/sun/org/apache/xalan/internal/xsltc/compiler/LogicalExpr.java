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
 * $Id: LogicalExpr.java,v 1.2.4.1 2005/09/01 16:03:31 pvedula Exp $
 * <p>
 *  $ Id：LogicalExpr.java,v 1.2.4.1 2005/09/01 16:03:31 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class LogicalExpr extends Expression {

    public static final int OR  = 0;
    public static final int AND = 1;

    private final int  _op;     // operator
    private Expression _left;   // first operand
    private Expression _right;  // second operand

    private static final String[] Ops = { "or", "and" };

    /**
     * Creates a new logical expression - either OR or AND. Note that the
     * left- and right-hand side expressions can also be logical expressions,
     * thus creating logical trees representing structures such as
     * (a and (b or c) and d), etc...
     * <p>
     *  创建一个新的逻辑表达式 -  OR或AND。注意,左手和右手表达式也可以是逻辑表达式,从而创建表示诸如(a和(b或c)和d)等等的结构的逻辑树。
     * 
     */
    public LogicalExpr(int op, Expression left, Expression right) {
        _op = op;
        (_left = left).setParent(this);
        (_right = right).setParent(this);
    }

    /**
     * Returns true if this expressions contains a call to position(). This is
     * needed for context changes in node steps containing multiple predicates.
     * <p>
     *  如果此表达式包含对position()的调用,则返回true。这对于包含多个谓词的节点步骤中的上下文更改是必需的。
     * 
     */
    public boolean hasPositionCall() {
        return (_left.hasPositionCall() || _right.hasPositionCall());
    }

    /**
     * Returns true if this expressions contains a call to last()
     * <p>
     *  如果此表达式包含对last()的调用,则返回true
     * 
     */
    public boolean hasLastCall() {
            return (_left.hasLastCall() || _right.hasLastCall());
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
        final Object leftb = _left.evaluateAtCompileTime();
        final Object rightb = _right.evaluateAtCompileTime();

        // Return null if we can't evaluate at compile time
        if (leftb == null || rightb == null) {
            return null;
        }

        if (_op == AND) {
            return (leftb == Boolean.TRUE && rightb == Boolean.TRUE) ?
                Boolean.TRUE : Boolean.FALSE;
        }
        else {
            return (leftb == Boolean.TRUE || rightb == Boolean.TRUE) ?
                Boolean.TRUE : Boolean.FALSE;
        }
    }

    /**
     * Returns this logical expression's operator - OR or AND represented
     * by 0 and 1 respectively.
     * <p>
     *  返回此逻辑表达式的运算符 - 分别由0和1表示的OR或AND。
     * 
     */
    public int getOp() {
        return(_op);
    }

    /**
     * Override the SyntaxTreeNode.setParser() method to make sure that the
     * parser is set for sub-expressions
     * <p>
     * 覆盖SyntaxTreeNode.setParser()方法以确保为子表达式设置解析器
     * 
     */
    public void setParser(Parser parser) {
        super.setParser(parser);
        _left.setParser(parser);
        _right.setParser(parser);
    }

    /**
     * Returns a string describing this expression
     * <p>
     *  返回描述此表达式的字符串
     * 
     */
    public String toString() {
        return Ops[_op] + '(' + _left + ", " + _right + ')';
    }

    /**
     * Type-check this expression, and possibly child expressions.
     * <p>
     *  键入检查此表达式,以及可能的子表达式。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        // Get the left and right operand types
        Type tleft = _left.typeCheck(stable);
        Type tright = _right.typeCheck(stable);

        // Check if the operator supports the two operand types
        MethodType wantType = new MethodType(Type.Void, tleft, tright);
        MethodType haveType = lookupPrimop(stable, Ops[_op], wantType);

        // Yes, the operation is supported
        if (haveType != null) {
            // Check if left-hand side operand must be type casted
            Type arg1 = (Type)haveType.argsType().elementAt(0);
            if (!arg1.identicalTo(tleft))
                _left = new CastExpr(_left, arg1);
            // Check if right-hand side operand must be type casted
            Type arg2 = (Type) haveType.argsType().elementAt(1);
            if (!arg2.identicalTo(tright))
                _right = new CastExpr(_right, arg1);
            // Return the result type for the operator we will use
            return _type = haveType.resultType();
        }
        throw new TypeCheckError(this);
    }

    /**
     * Compile the expression - leave boolean expression on stack
     * <p>
     *  编译表达式 - 在堆栈上留下布尔表达式
     * 
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        translateDesynthesized(classGen, methodGen);
        synthesize(classGen, methodGen);
    }

    /**
     * Compile expression and update true/false-lists
     * <p>
     *  编译表达式并更新true / false列表
     */
    public void translateDesynthesized(ClassGenerator classGen,
                                       MethodGenerator methodGen) {

        final InstructionList il = methodGen.getInstructionList();
        final SyntaxTreeNode parent = getParent();

        // Compile AND-expression
        if (_op == AND) {

            // Translate left hand side - must be true
            _left.translateDesynthesized(classGen, methodGen);

            // Need this for chaining any OR-expression children
            InstructionHandle middle = il.append(NOP);

            // Translate left right side - must be true
            _right.translateDesynthesized(classGen, methodGen);

            // Need this for chaining any OR-expression children
            InstructionHandle after = il.append(NOP);

            // Append child expression false-lists to our false-list
            _falseList.append(_right._falseList.append(_left._falseList));

            // Special case for OR-expression as a left child of AND.
            // The true-list of OR must point to second clause of AND.
            if ((_left instanceof LogicalExpr) &&
                (((LogicalExpr)_left).getOp() == OR)) {
                _left.backPatchTrueList(middle);
            }
            else if (_left instanceof NotCall) {
                _left.backPatchTrueList(middle);
            }
            else {
                _trueList.append(_left._trueList);
            }

            // Special case for OR-expression as a right child of AND
            // The true-list of OR must point to true-list of AND.
            if ((_right instanceof LogicalExpr) &&
                (((LogicalExpr)_right).getOp() == OR)) {
                _right.backPatchTrueList(after);
            }
            else if (_right instanceof NotCall) {
                _right.backPatchTrueList(after);
            }
            else {
                _trueList.append(_right._trueList);
            }
        }
        // Compile OR-expression
        else {
            // Translate left-hand side expression and produce true/false list
            _left.translateDesynthesized(classGen, methodGen);

            // This GOTO is used to skip over the code for the last test
            // in the case where the the first test succeeds
            InstructionHandle ih = il.append(new GOTO(null));

            // Translate right-hand side expression and produce true/false list
            _right.translateDesynthesized(classGen, methodGen);

            _left._trueList.backPatch(ih);
            _left._falseList.backPatch(ih.getNext());

            _falseList.append(_right._falseList);
            _trueList.add(ih).append(_right._trueList);
        }
    }
}
