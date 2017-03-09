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
 * $Id: Param.java,v 1.2.4.1 2005/09/02 11:03:42 pvedula Exp $
 * <p>
 *  $ Id：Param.java,v 1.2.4.1 2005/09/02 11:03:42 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.classfile.Field;
import com.sun.org.apache.bcel.internal.generic.BranchHandle;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.IFNONNULL;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.bcel.internal.generic.PUTFIELD;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ObjectType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 * @author Erwin Bolwidt <ejb@klomp.org>
 * @author John Howard <JohnH@schemasoft.com>
 */
final class Param extends VariableBase {

    /**
     * True if this Param is declared in a simple named template.
     * This is used to optimize codegen for parameter passing
     * in named templates.
     * <p>
     *  如果此Param在简单命名模板中声明,则为True。这用于优化codegen以便在命名模板中传递参数。
     * 
     */
    private boolean _isInSimpleNamedTemplate = false;

    /**
     * Display variable as single string
     * <p>
     *  将变量显示为单个字符串
     * 
     */
    public String toString() {
        return "param(" + _name + ")";
    }

    /**
     * Set the instruction for loading the value of this variable onto the
     * JVM stack and returns the old instruction.
     * <p>
     *  设置将该变量的值加载到JVM堆栈的指令,并返回旧的指令。
     * 
     */
    public Instruction setLoadInstruction(Instruction instruction) {
        Instruction tmp = _loadInstruction;
        _loadInstruction = instruction;
        return tmp;
    }

    /**
     * Set the instruction for storing a value from the stack into this
     * variable and returns the old instruction.
     * <p>
     *  将用于将值从堆栈存储到该变量的指令设置为返回旧指令。
     * 
     */
    public Instruction setStoreInstruction(Instruction instruction) {
        Instruction tmp = _storeInstruction;
        _storeInstruction = instruction;
        return tmp;
    }

    /**
     * Display variable in a full AST dump
     * <p>
     *  在完全AST转储中显示变量
     * 
     */
    public void display(int indent) {
        indent(indent);
        System.out.println("param " + _name);
        if (_select != null) {
            indent(indent + IndentIncrement);
            System.out.println("select " + _select.toString());
        }
        displayContents(indent + IndentIncrement);
    }

    /**
     * Parse the contents of the <xsl:param> element. This method must read
     * the 'name' (required) and 'select' (optional) attributes.
     * <p>
     *  解析<xsl：param>元素的内容。此方法必须读取"name"(必需)和"select"(可选)属性。
     * 
     */
    public void parseContents(Parser parser) {

        // Parse 'name' and 'select' attributes plus parameter contents
        super.parseContents(parser);

        // Add a ref to this param to its enclosing construct
        final SyntaxTreeNode parent = getParent();
        if (parent instanceof Stylesheet) {
            // Mark this as a global parameter
            _isLocal = false;
            // Check if a global variable with this name already exists...
            Param param = parser.getSymbolTable().lookupParam(_name);
            // ...and if it does we need to check import precedence
            if (param != null) {
                final int us = this.getImportPrecedence();
                final int them = param.getImportPrecedence();
                // It is an error if the two have the same import precedence
                if (us == them) {
                    final String name = _name.toString();
                    reportError(this, parser, ErrorMsg.VARIABLE_REDEF_ERR,name);
                }
                // Ignore this if previous definition has higher precedence
                else if (them > us) {
                    _ignore = true;
                    copyReferences(param);
                    return;
                }
                else {
                    param.copyReferences(this);
                    param.disable();
                }
            }
            // Add this variable if we have higher precedence
            ((Stylesheet)parent).addParam(this);
            parser.getSymbolTable().addParam(this);
        }
        else if (parent instanceof Template) {
            Template template = (Template) parent;
            _isLocal = true;
            template.addParameter(this);
            if (template.isSimpleNamedTemplate()) {
                _isInSimpleNamedTemplate = true;
            }
        }
    }

    /**
     * Type-checks the parameter. The parameter type is determined by the
     * 'select' expression (if present) or is a result tree if the parameter
     * element has a body and no 'select' expression.
     * <p>
     * 类型检查参数。参数类型由"select"表达式(如果存在)确定,或者如果参数元素具有主体且没有"select"表达式,则为结果树。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        if (_select != null) {
            _type = _select.typeCheck(stable);
            if (_type instanceof ReferenceType == false && !(_type instanceof ObjectType)) {
                _select = new CastExpr(_select, Type.Reference);
            }
        }
        else if (hasContents()) {
            typeCheckContents(stable);
        }
        _type = Type.Reference;

        // This element has no type (the parameter does, but the parameter
        // element itself does not).
        return Type.Void;
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        if (_ignore) return;
        _ignore = true;

        /*
         * To fix bug 24518 related to setting parameters of the form
         * {namespaceuri}localName which will get mapped to an instance
         * variable in the class.
         * <p>
         *  修复与设置{namespaceuri} localName形式的参数相关的错误24518,它将被映射到类中的一个实例变量。
         * 
         */
        final String name = BasisLibrary.mapQNameToJavaName(_name.toString());
        final String signature = _type.toSignature();
        final String className = _type.getClassName();

        if (isLocal()) {
            /*
              * If simple named template then generate a conditional init of the
              * param using its default value:
              *       if (param == null) param = <default-value>
              * <p>
              *  如果简单命名模板然后使用其默认值生成param的条件init：if(param == null)param = <default-value>
              */
            if (_isInSimpleNamedTemplate) {
                il.append(loadInstruction());
                BranchHandle ifBlock = il.append(new IFNONNULL(null));
                translateValue(classGen, methodGen);
                il.append(storeInstruction());
                ifBlock.setTarget(il.append(NOP));
                return;
            }

            il.append(classGen.loadTranslet());
            il.append(new PUSH(cpg, name));
            translateValue(classGen, methodGen);
            il.append(new PUSH(cpg, true));

            // Call addParameter() from this class
            il.append(new INVOKEVIRTUAL(cpg.addMethodref(TRANSLET_CLASS,
                                                         ADD_PARAMETER,
                                                         ADD_PARAMETER_SIG)));
            if (className != EMPTYSTRING) {
                il.append(new CHECKCAST(cpg.addClass(className)));
            }

            _type.translateUnBox(classGen, methodGen);

            if (_refs.isEmpty()) { // nobody uses the value
                il.append(_type.POP());
                _local = null;
            }
            else {              // normal case
                _local = methodGen.addLocalVariable2(name,
                                                     _type.toJCType(),
                                                     il.getEnd());
                // Cache the result of addParameter() in a local variable
                il.append(_type.STORE(_local.getIndex()));
            }
        }
        else {
            if (classGen.containsField(name) == null) {
                classGen.addField(new Field(ACC_PUBLIC, cpg.addUtf8(name),
                                            cpg.addUtf8(signature),
                                            null, cpg.getConstantPool()));
                il.append(classGen.loadTranslet());
                il.append(DUP);
                il.append(new PUSH(cpg, name));
                translateValue(classGen, methodGen);
                il.append(new PUSH(cpg, true));

                // Call addParameter() from this class
                il.append(new INVOKEVIRTUAL(cpg.addMethodref(TRANSLET_CLASS,
                                                     ADD_PARAMETER,
                                                     ADD_PARAMETER_SIG)));

                _type.translateUnBox(classGen, methodGen);

                // Cache the result of addParameter() in a field
                if (className != EMPTYSTRING) {
                    il.append(new CHECKCAST(cpg.addClass(className)));
                }
                il.append(new PUTFIELD(cpg.addFieldref(classGen.getClassName(),
                                                       name, signature)));
            }
        }
    }
}
