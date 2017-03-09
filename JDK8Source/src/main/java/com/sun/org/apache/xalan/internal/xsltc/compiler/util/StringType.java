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
 * $Id: StringType.java,v 1.2.4.1 2005/09/05 11:35:57 pvedula Exp $
 * <p>
 *  $ Id：StringType.java,v 1.2.4.1 2005/09/05 11:35:57 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler.util;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.BranchHandle;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.IFEQ;
import com.sun.org.apache.bcel.internal.generic.IFNONNULL;
import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;
import com.sun.org.apache.xalan.internal.xsltc.compiler.FlowList;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public class StringType extends Type {
    protected StringType() {}

    public String toString() {
        return "string";
    }

    public boolean identicalTo(Type other) {
        return this == other;
    }

    public String toSignature() {
        return "Ljava/lang/String;";
    }

    public boolean isSimple() {
        return true;
    }

    public com.sun.org.apache.bcel.internal.generic.Type toJCType() {
        return com.sun.org.apache.bcel.internal.generic.Type.STRING;
    }

    /**
     * Translates a string into an object of internal type <code>type</code>.
     * The translation to int is undefined since strings are always converted
     * to reals in arithmetic expressions.
     *
     * <p>
     *  将字符串转换为内部类型<code> type </code>的对象。转换为int是未定义的,因为字符串总是转换为算术表达式中的reals。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            Type type) {
        if (type == Type.Boolean) {
            translateTo(classGen, methodGen, (BooleanType) type);
        }
        else if (type == Type.Real) {
            translateTo(classGen, methodGen, (RealType) type);
        }
        else if (type == Type.Reference) {
            translateTo(classGen, methodGen, (ReferenceType) type);
        }
        else if (type == Type.ObjectString) {
            // NOP -> same representation
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), type.toString());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Translates a string into a synthesized boolean.
     *
     * <p>
     *  将字符串转换为合成布尔值。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            BooleanType type) {
        final InstructionList il = methodGen.getInstructionList();
        FlowList falsel = translateToDesynthesized(classGen, methodGen, type);
        il.append(ICONST_1);
        final BranchHandle truec = il.append(new GOTO(null));
        falsel.backPatch(il.append(ICONST_0));
        truec.setTarget(il.append(NOP));
    }

    /**
     * Translates a string into a real by calling stringToReal() from the
     * basis library.
     *
     * <p>
     *  通过从基础库中调用stringToReal()将字符串转换为实数。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            RealType type) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        il.append(new INVOKESTATIC(cpg.addMethodref(BASIS_LIBRARY_CLASS,
                                                    STRING_TO_REAL,
                                                    STRING_TO_REAL_SIG)));
    }

    /**
     * Translates a string into a non-synthesized boolean. It does not push a
     * 0 or a 1 but instead returns branchhandle list to be appended to the
     * false list.
     *
     * <p>
     *  将字符串转换为非合成布尔值。它不推动0或1,而是返回分支句柄列表附加到false列表。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateToDesynthesized
     */
    public FlowList translateToDesynthesized(ClassGenerator classGen,
                                             MethodGenerator methodGen,
                                             BooleanType type) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        il.append(new INVOKEVIRTUAL(cpg.addMethodref(STRING_CLASS,
                                                     "length", "()I")));
        return new FlowList(il.append(new IFEQ(null)));
    }

    /**
     * Expects a string on the stack and pushes a boxed string.
     * Strings are already boxed so the translation is just a NOP.
     *
     * <p>
     *  在堆栈上看到一个字符串,并推送一个加框字符串。字符串已经装箱,所以翻译只是一个NOP。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            ReferenceType type) {
        methodGen.getInstructionList().append(NOP);
    }

    /**
     * Translates a internal string into an external (Java) string.
     *
     * <p>
     *  将内部字符串转换为外部(Java)字符串。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateFrom
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            Class clazz)
    {
        // Is String <: clazz? I.e. clazz in { String, Object }
        if (clazz.isAssignableFrom(java.lang.String.class)) {
            methodGen.getInstructionList().append(NOP);
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), clazz.getName());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Translates an external (primitive) Java type into a string.
     *
     * <p>
     *  将外部(原始)Java类型转换为字符串。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateFrom
     */
    public void translateFrom(ClassGenerator classGen,
        MethodGenerator methodGen, Class clazz)
    {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        if (clazz.getName().equals("java.lang.String")) {
            // same internal representation, convert null to ""
            il.append(DUP);
            final BranchHandle ifNonNull = il.append(new IFNONNULL(null));
            il.append(POP);
            il.append(new PUSH(cpg, ""));
            ifNonNull.setTarget(il.append(NOP));
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), clazz.getName());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Translates an object of this type to its boxed representation.
     * <p>
     * 将此类型的对象转换为其框化表示。
     * 
     */
    public void translateBox(ClassGenerator classGen,
                             MethodGenerator methodGen) {
        translateTo(classGen, methodGen, Type.Reference);
    }

    /**
     * Translates an object of this type to its unboxed representation.
     * <p>
     *  将此类型的对象转换为其未装箱的表示。
     * 
     */
    public void translateUnBox(ClassGenerator classGen,
                               MethodGenerator methodGen) {
        methodGen.getInstructionList().append(NOP);
    }

    /**
     * Returns the class name of an internal type's external representation.
     * <p>
     *  返回内部类型的外部表示的类名。
     */
    public String getClassName() {
        return(STRING_CLASS);
    }


    public Instruction LOAD(int slot) {
        return new ALOAD(slot);
    }

    public Instruction STORE(int slot) {
        return new ASTORE(slot);
    }
}
