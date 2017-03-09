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
 * $Id: ContainsCall.java,v 1.2.4.1 2005/09/01 12:12:06 pvedula Exp $
 * <p>
 *  $ Id：ContainsCall.java,v 1.2.4.1 2005/09/01 12:12:06 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.IFLT;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
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
final class ContainsCall extends FunctionCall {

    private Expression _base = null;
    private Expression _token = null;

    /**
     * Create a contains() call - two arguments, both strings
     * <p>
     *  创建一个contains()调用 - 两个参数,两个字符串
     * 
     */
    public ContainsCall(QName fname, Vector arguments) {
        super(fname, arguments);
    }

    /**
     * This XPath function returns true/false values
     * <p>
     *  此XPath函数返回true / false值
     * 
     */
    public boolean isBoolean() {
        return true;
    }

    /**
     * Type check the two parameters for this function
     * <p>
     *  类型检查此函数的两个参数
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {

        // Check that the function was passed exactly two arguments
        if (argumentCount() != 2) {
            throw new TypeCheckError(ErrorMsg.ILLEGAL_ARG_ERR, getName(), this);
        }

        // The first argument must be a String, or cast to a String
        _base = argument(0);
        Type baseType = _base.typeCheck(stable);
        if (baseType != Type.String)
            _base = new CastExpr(_base, Type.String);

        // The second argument must also be a String, or cast to a String
        _token = argument(1);
        Type tokenType = _token.typeCheck(stable);
        if (tokenType != Type.String)
            _token = new CastExpr(_token, Type.String);

        return _type = Type.Boolean;
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
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        _base.translate(classGen, methodGen);
        _token.translate(classGen, methodGen);
        il.append(new INVOKEVIRTUAL(cpg.addMethodref(STRING_CLASS,
                                                     "indexOf",
                                                     "("+STRING_SIG+")I")));
        _falseList.add(il.append(new IFLT(null)));
    }
}
