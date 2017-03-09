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
 * $Id: WithParam.java,v 1.2.4.1 2005/09/12 11:38:01 pvedula Exp $
 * <p>
 *  $ Id：WithParam.java,v 1.2.4.1 2005/09/12 11:38:01 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ReferenceType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.org.apache.xml.internal.utils.XML11Char;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 * @author John Howard <JohnH@schemasoft.com>
 */
final class WithParam extends Instruction {

    /**
     * Parameter's name.
     * <p>
     *  参数名称。
     * 
     */
    private QName _name;

    /**
     * The escaped qname of the with-param.
     * <p>
     *  with-param的转义的qname。
     * 
     */
    protected String _escapedName;

    /**
     * Parameter's default value.
     * <p>
     *  参数的默认值。
     * 
     */
    private Expression _select;

    /**
     * %OPT% This is set to true when the WithParam is used in a CallTemplate
     * for a simple named template. If this is true, the parameters are
     * passed to the named template through method arguments rather than
     * using the expensive Translet.addParameter() call.
     * <p>
     *  ％OPT％当在CallTemplate中使用WithParam作为简单命名模板时,此属性设置为true。
     * 如果这是真的,参数通过方法参数传递到命名的模板,而不是使用昂贵的Translet.addParameter()调用。
     * 
     */
    private boolean _doParameterOptimization = false;

    /**
     * Displays the contents of this element
     * <p>
     *  显示此元素的内容
     * 
     */
    public void display(int indent) {
        indent(indent);
        Util.println("with-param " + _name);
        if (_select != null) {
            indent(indent + IndentIncrement);
            Util.println("select " + _select.toString());
        }
        displayContents(indent + IndentIncrement);
    }

    /**
     * Returns the escaped qname of the parameter
     * <p>
     *  返回参数的转义的qname
     * 
     */
    public String getEscapedName() {
        return _escapedName;
    }

    /**
     * Return the name of this WithParam.
     * <p>
     *  返回此WithParam的名称。
     * 
     */
    public QName getName() {
        return _name;
    }

    /**
     * Set the name of the variable or paremeter. Escape all special chars.
     * <p>
     *  设置变量或参数的名称。逃脱所有特殊字符。
     * 
     */
    public void setName(QName name) {
        _name = name;
        _escapedName = Util.escape(name.getStringRep());
    }

    /**
     * Set the do parameter optimization flag
     * <p>
     *  设置do参数优化标志
     * 
     */
    public void setDoParameterOptimization(boolean flag) {
        _doParameterOptimization = flag;
    }

    /**
     * The contents of a <xsl:with-param> elements are either in the element's
     * 'select' attribute (this has precedence) or in the element body.
     * <p>
     *  <xsl：with-param>元素的内容位于元素的"select"属性中(这具有优先级)或在元素体中。
     * 
     */
    public void parseContents(Parser parser) {
        final String name = getAttribute("name");
        if (name.length() > 0) {
            if (!XML11Char.isXML11ValidQName(name)) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.INVALID_QNAME_ERR, name,
                                            this);
                parser.reportError(Constants.ERROR, err);
            }
            setName(parser.getQNameIgnoreDefaultNs(name));
        }
        else {
            reportError(this, parser, ErrorMsg.REQUIRED_ATTR_ERR, "name");
        }

        final String select = getAttribute("select");
        if (select.length() > 0) {
            _select = parser.parseExpression(this, "select", null);
        }

        parseChildren(parser);
    }

    /**
     * Type-check either the select attribute or the element body, depending
     * on which is in use.
     * <p>
     * 类型检查select属性或元素体,具体取决于使用中。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        if (_select != null) {
            final Type tselect = _select.typeCheck(stable);
            if (tselect instanceof ReferenceType == false) {
                _select = new CastExpr(_select, Type.Reference);
            }
        }
        else {
            typeCheckContents(stable);
        }
        return Type.Void;
    }

    /**
     * Compile the value of the parameter, which is either in an expression in
     * a 'select' attribute, or in the with-param element's body
     * <p>
     *  编译参数的值,该值在"select"属性中的表达式中,或者在with-param元素的正文中
     * 
     */
    public void translateValue(ClassGenerator classGen,
                               MethodGenerator methodGen) {
        // Compile expression is 'select' attribute if present
        if (_select != null) {
            _select.translate(classGen, methodGen);
            _select.startIterator(classGen, methodGen);
        }
        // If not, compile result tree from parameter body if present.
        else if (hasContents()) {
            compileResultTree(classGen, methodGen);
        }
        // If neither are present then store empty string in parameter slot
        else {
            final ConstantPoolGen cpg = classGen.getConstantPool();
            final InstructionList il = methodGen.getInstructionList();
            il.append(new PUSH(cpg, Constants.EMPTYSTRING));
        }
    }

    /**
     * This code generates a sequence of bytecodes that call the
     * addParameter() method in AbstractTranslet. The method call will add
     * (or update) the parameter frame with the new parameter value.
     * <p>
     *  此代码生成调用AbstractTranslet中的addParameter()方法的一系列字节码。方法调用将使用新的参数值添加(或更新)参数框架。
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // Translate the value and put it on the stack
        if (_doParameterOptimization) {
            translateValue(classGen, methodGen);
            return;
        }

        // Make name acceptable for use as field name in class
        String name = Util.escape(getEscapedName());

        // Load reference to the translet (method is in AbstractTranslet)
        il.append(classGen.loadTranslet());

        // Load the name of the parameter
        il.append(new PUSH(cpg, name)); // TODO: namespace ?
        // Generete the value of the parameter (use value in 'select' by def.)
        translateValue(classGen, methodGen);
        // Mark this parameter value is not being the default value
        il.append(new PUSH(cpg, false));
        // Pass the parameter to the template
        il.append(new INVOKEVIRTUAL(cpg.addMethodref(TRANSLET_CLASS,
                                                     ADD_PARAMETER,
                                                     ADD_PARAMETER_SIG)));
        il.append(POP); // cleanup stack
    }
}
