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
 * $Id: If.java,v 1.2.4.1 2005/09/01 15:39:47 pvedula Exp $
 * <p>
 *  $ Id：If.java,v 1.2.4.1 2005/09/01 15:39:47 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
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
final class If extends Instruction {

    private Expression _test;
    private boolean    _ignore = false;

    /**
     * Display the contents of this element
     * <p>
     *  显示此元素的内容
     * 
     */
    public void display(int indent) {
        indent(indent);
        Util.println("If");
        indent(indent + IndentIncrement);
        System.out.print("test ");
        Util.println(_test.toString());
        displayContents(indent + IndentIncrement);
    }

    /**
     * Parse the "test" expression and contents of this element.
     * <p>
     *  解析"测试"表达式和此元素的内容。
     * 
     */
    public void parseContents(Parser parser) {
        // Parse the "test" expression
        _test = parser.parseExpression(this, "test", null);

        // Make sure required attribute(s) have been set
        if (_test.isDummy()) {
            reportError(this, parser, ErrorMsg.REQUIRED_ATTR_ERR, "test");
            return;
        }

        // Ignore xsl:if when test is false (function-available() and
        // element-available())
        Object result = _test.evaluateAtCompileTime();
        if (result != null && result instanceof Boolean) {
            _ignore = !((Boolean) result).booleanValue();
        }

        parseChildren(parser);
    }

    /**
     * Type-check the "test" expression and contents of this element.
     * The contents will be ignored if we know the test will always fail.
     * <p>
     *  键入 - 检查"测试"表达式和此元素的内容。如果我们知道测试总是失败,内容将被忽略。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        // Type-check the "test" expression
        if (_test.typeCheck(stable) instanceof BooleanType == false) {
            _test = new CastExpr(_test, Type.Boolean);
        }
        // Type check the element contents
        if (!_ignore) {
            typeCheckContents(stable);
        }
        return Type.Void;
    }

    /**
     * Translate the "test" expression and contents of this element.
     * The contents will be ignored if we know the test will always fail.
     * <p>
     *  翻译"测试"表达式和此元素的内容。如果我们知道测试总是失败,内容将被忽略。
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final InstructionList il = methodGen.getInstructionList();
        _test.translateDesynthesized(classGen, methodGen);
        // remember end of condition
        final InstructionHandle truec = il.getEnd();
        if (!_ignore) {
            translateContents(classGen, methodGen);
        }
        _test.backPatchFalseList(il.append(NOP));
        _test.backPatchTrueList(truec.getNext());
    }
}
