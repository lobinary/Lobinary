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
 * $Id: ObjectType.java,v 1.2.4.1 2005/09/12 11:45:54 pvedula Exp $
 * <p>
 *  $ Id：ObjectType.java,v 1.2.4.1 2005/09/12 11:45:54 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler.util;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.BranchHandle;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import com.sun.org.apache.bcel.internal.generic.IFNULL;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;

/**
/* <p>
/* 
 * @author Todd Miller
 * @author Santiago Pericas-Geertsen
 */
public final class ObjectType extends Type {

    private String _javaClassName = "java.lang.Object";
    private Class  _clazz = java.lang.Object.class;

    /**
     * Used to represent a Java Class type such is required to support
     * non-static java functions.
     * <p>
     *  用于表示支持非静态java函数所需的Java类类型。
     * 
     * 
     * @param javaClassName name of the class such as 'com.foo.Processor'
     */
    protected ObjectType(String javaClassName) {
        _javaClassName = javaClassName;

        try {
          _clazz = ObjectFactory.findProviderClass(javaClassName, true);
        }
        catch (ClassNotFoundException e) {
          _clazz = null;
        }
    }

    protected ObjectType(Class clazz) {
        _clazz = clazz;
        _javaClassName = clazz.getName();
    }

    /**
     * Must return the same value for all ObjectType instances. This is
     * needed in CastExpr to ensure the mapping table is used correctly.
     * <p>
     *  必须对所有ObjectType实例返回相同的值。这在CastExpr中需要,以确保映射表正确使用。
     * 
     */
    public int hashCode() {
        return java.lang.Object.class.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ObjectType);
    }

    public String getJavaClassName() {
        return _javaClassName;
    }

    public Class getJavaClass() {
        return _clazz;
    }

    public String toString() {
        return _javaClassName;
    }

    public boolean identicalTo(Type other) {
        return this == other;
    }

    public String toSignature() {
        final StringBuffer result = new StringBuffer("L");
        result.append(_javaClassName.replace('.', '/')).append(';');
        return result.toString();
    }

    public com.sun.org.apache.bcel.internal.generic.Type toJCType() {
        return Util.getJCRefType(toSignature());
    }

    /**
     * Translates a void into an object of internal type <code>type</code>.
     * This translation is needed when calling external functions
     * that return void.
     *
     * <p>
     *  将void转换为内部类型<code> type </code>的对象。调用返回void的外部函数时需要此翻译。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            Type type) {
        if (type == Type.String) {
            translateTo(classGen, methodGen, (StringType) type);
        }
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                                        toString(), type.toString());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Expects an integer on the stack and pushes its string value by calling
     * <code>Integer.toString(int i)</code>.
     *
     * <p>
     *  在栈上看到一个整数,并通过调用<code> Integer.toString(int i)</code>来推送它的字符串值。
     * 
     * 
     * @see     com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type#translateTo
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            StringType type) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        il.append(DUP);
        final BranchHandle ifNull = il.append(new IFNULL(null));
        il.append(new INVOKEVIRTUAL(cpg.addMethodref(_javaClassName,
                                                    "toString",
                                                    "()" + STRING_SIG)));
        final BranchHandle gotobh = il.append(new GOTO(null));
        ifNull.setTarget(il.append(POP));
        il.append(new PUSH(cpg, ""));
        gotobh.setTarget(il.append(NOP));
    }

    /**
     * Translates an object of this type to the external (Java) type denoted
     * by <code>clazz</code>. This method is used to translate parameters
     * when external functions are called.
     * <p>
     *  将此类型的对象转换为由<code> clazz </code>表示的外部(Java)类型。当调用外部函数时,此方法用于翻译参数。
     * 
     */
    public void translateTo(ClassGenerator classGen, MethodGenerator methodGen,
                            Class clazz) {
        if (clazz.isAssignableFrom(_clazz))
            methodGen.getInstructionList().append(NOP);
        else {
            ErrorMsg err = new ErrorMsg(ErrorMsg.DATA_CONVERSION_ERR,
                               toString(), clazz.getClass().toString());
            classGen.getParser().reportError(Constants.FATAL, err);
        }
    }

    /**
     * Translates an external Java type into an Object type
     * <p>
     *  将外部Java类型转换为对象类型
     */
    public void translateFrom(ClassGenerator classGen,
                              MethodGenerator methodGen, Class clazz) {
        methodGen.getInstructionList().append(NOP);
    }

    public Instruction LOAD(int slot) {
        return new ALOAD(slot);
    }

    public Instruction STORE(int slot) {
        return new ASTORE(slot);
    }
}
