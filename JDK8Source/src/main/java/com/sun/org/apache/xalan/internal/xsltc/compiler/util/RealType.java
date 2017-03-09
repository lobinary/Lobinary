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
 * $Id: RealType.java,v 1.2.4.1 2005/09/05 11:28:45 pvedula Exp $
 * <p>
 *  $ Id：RealType.java,v 1.2.4.1 2005/09/05 11:28:45 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler.util;

import com.sun.org.apache.bcel.internal.generic.BranchHandle;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.DLOAD;
import com.sun.org.apache.bcel.internal.generic.DSTORE;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.IFEQ;
import com.sun.org.apache.bcel.internal.generic.IFNE;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.LocalVariableGen;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;
import com.sun.org.apache.xalan.internal.xsltc.compiler.FlowList;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public final class RealType extends NumberType {
    protected RealType() {}

    public String toString() {
        return "real";
    }

    public boolean identicalTo(Type other) {
        return this == other;
    }

    public String toSignature() {
        return "D";
    }

    public com.sun.org.apache.bcel.internal.generic.Type toJCType() {
        return com.sun.org.apache.bcel.internal.generic.Type.DOUBLE;
    }

    /**
    /* <p>
    /* 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#distanceTo
     */
    public int distanceTo(Type type) {
        if (type == this) {
            return 0;
        }
        else if (type == Type.Int) {
            return 1;
        }
        else {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Translates a real into an object of internal type <code>type</code>. The
     * translation to int is undefined since reals are never converted to ints.
     *
     * <p>
     *  将真实内容转换为内部类型<code> type </code>的对象。转换为int是未定义的,因为reals永远不会转换为int。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            Type type) {
        if (type == Type.String) {
            translateTo(classGen, methodGen, (StringType) type);
        }
        else if (type == Type.Boolean) {
            translateTo(classGen, methodGen, (BooleanType) type);
        }
        else if (type == Type.Reference) {
            translateTo(classGen, methodGen, (ReferenceType) type);
        }
        else if (type == Type.Int) {
            translateTo(classGen, methodGen, (IntType) type);
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), type.toString());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Expects a real on the stack and pushes its string value by calling
     * <code>Double.toString(double d)</code>.
     *
     * <p>
     *  通过调用<code> Double.toString(double d)</code>,可以看到栈上的一个真实的字符串值。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            StringType type) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        il.append(new INVOKESTATIC(cpg.addMethodref(BASIS_LIBRARY_CLASS,
                                                    "realToString",
                                                    "(D)" + STRING_SIG)));
    }

    /**
     * Expects a real on the stack and pushes a 0 if that number is 0.0 and
     * a 1 otherwise.
     *
     * <p>
     *  期望堆栈上的一个真实的,如果该数字为0.0,则推送0,否则为1。
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
     * Expects a real on the stack and pushes a truncated integer value
     *
     * <p>
     *  期望一个真正的堆栈,并推送一个截断的整数值
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            IntType type) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        il.append(new INVOKESTATIC(cpg.addMethodref(BASIS_LIBRARY_CLASS,
                                                    "realToInt","(D)I")));
    }

    /**
     * Translates a real into a non-synthesized boolean. It does not push a
     * 0 or a 1 but instead returns branchhandle list to be appended to the
     * false list. A NaN must be converted to "false".
     *
     * <p>
     *  将真实值转换为非合成布尔值。它不推动0或1,而是返回分支句柄列表附加到false列表。 NaN必须转换为"假"。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateToDesynthesized
     */
    public FlowList translateToDesynthesized(ClassGenerator classGen,
                                             MethodGenerator methodGen,
                                             BooleanType type) {
        LocalVariableGen local;
        final FlowList flowlist = new FlowList();
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // Store real into a local variable
        il.append(DUP2);
        local = methodGen.addLocalVariable("real_to_boolean_tmp",
                                           com.sun.org.apache.bcel.internal.generic.Type.DOUBLE,
                                           null, null);
        local.setStart(il.append(new DSTORE(local.getIndex())));

        // Compare it to 0.0
        il.append(DCONST_0);
        il.append(DCMPG);
        flowlist.add(il.append(new IFEQ(null)));

        //!!! call isNaN
        // Compare it to itself to see if NaN
        il.append(new DLOAD(local.getIndex()));
        local.setEnd(il.append(new DLOAD(local.getIndex())));
        il.append(DCMPG);
        flowlist.add(il.append(new IFNE(null)));        // NaN != NaN
        return flowlist;
    }

    /**
     * Expects a double on the stack and pushes a boxed double. Boxed
     * double are represented by an instance of <code>java.lang.Double</code>.
     *
     * <p>
     * 期望双栈的堆栈,并推动一个盒装双。 Boxed double由<code> java.lang.Double </code>的实例表示。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            ReferenceType type) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        il.append(new NEW(cpg.addClass(DOUBLE_CLASS)));
        il.append(DUP_X2);
        il.append(DUP_X2);
        il.append(POP);
        il.append(new INVOKESPECIAL(cpg.addMethodref(DOUBLE_CLASS,
                                                     "<init>", "(D)V")));
    }

    /**
     * Translates a real into the Java type denoted by <code>clazz</code>.
     * Expects a real on the stack and pushes a number of the appropriate
     * type after coercion.
     * <p>
     *  将实数转换为由<code> clazz </code>表示的Java类型。期望一个真正的堆栈,并推出一些强制后的适当类型。
     * 
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            final Class clazz) {
        final InstructionList il = methodGen.getInstructionList();
        if (clazz == Character.TYPE) {
            il.append(D2I);
            il.append(I2C);
        }
        else if (clazz == Byte.TYPE) {
            il.append(D2I);
            il.append(I2B);
        }
        else if (clazz == Short.TYPE) {
            il.append(D2I);
            il.append(I2S);
        }
        else if (clazz == Integer.TYPE) {
            il.append(D2I);
        }
        else if (clazz == Long.TYPE) {
            il.append(D2L);
        }
        else if (clazz == Float.TYPE) {
            il.append(D2F);
        }
        else if (clazz == Double.TYPE) {
            il.append(NOP);
        }
        // Is Double <: clazz? I.e. clazz in { Double, Number, Object }
        else if (clazz.isAssignableFrom(java.lang.Double.class)) {
            translateTo(classGen, methodGen, Type.Reference);
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), clazz.getName());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Translates an external (primitive) Java type into a real. Expects a java
     * object on the stack and pushes a real (i.e., a double).
     * <p>
     *  将外部(原始)Java类型转换为实数。在栈上预期一个java对象,并推送一个真实的(即,一个双)。
     * 
     */
    public void translateFrom(ClassGenerator classGen, MethodGenerator methodGen,
                              Class clazz) {
        InstructionList il = methodGen.getInstructionList();

        if (clazz == Character.TYPE || clazz == Byte.TYPE ||
            clazz == Short.TYPE || clazz == Integer.TYPE) {
            il.append(I2D);
        }
        else if (clazz == Long.TYPE) {
            il.append(L2D);
        }
        else if (clazz == Float.TYPE) {
            il.append(F2D);
        }
        else if (clazz == Double.TYPE) {
            il.append(NOP);
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
     *  将此类型的对象转换为其框化表示。
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
     */
    public void translateUnBox(ClassGenerator classGen,
                               MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        il.append(new CHECKCAST(cpg.addClass(DOUBLE_CLASS)));
        il.append(new INVOKEVIRTUAL(cpg.addMethodref(DOUBLE_CLASS,
                                                     DOUBLE_VALUE,
                                                     DOUBLE_VALUE_SIG)));
    }

    public Instruction ADD() {
        return InstructionConstants.DADD;
    }

    public Instruction SUB() {
        return InstructionConstants.DSUB;
    }

    public Instruction MUL() {
        return InstructionConstants.DMUL;
    }

    public Instruction DIV() {
        return InstructionConstants.DDIV;
    }

    public Instruction REM() {
        return InstructionConstants.DREM;
    }

    public Instruction NEG() {
        return InstructionConstants.DNEG;
    }

    public Instruction LOAD(int slot) {
        return new DLOAD(slot);
    }

    public Instruction STORE(int slot) {
        return new DSTORE(slot);
    }

    public Instruction POP() {
        return POP2;
    }

    public Instruction CMP(boolean less) {
        return less ? InstructionConstants.DCMPG : InstructionConstants.DCMPL;
    }

    public Instruction DUP() {
        return DUP2;
    }
}
