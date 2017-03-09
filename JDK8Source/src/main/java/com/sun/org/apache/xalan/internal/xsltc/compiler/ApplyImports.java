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
 * $Id: ApplyImports.java,v 1.2.4.1 2005/09/13 12:22:02 pvedula Exp $
 * <p>
 *  $ Id：ApplyImports.java,v 1.2.4.1 2005/09/13 12:22:02 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Enumeration;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

final class ApplyImports extends Instruction {

    private QName      _modeName;
    private int        _precedence;

    public void display(int indent) {
        indent(indent);
        Util.println("ApplyTemplates");
        indent(indent + IndentIncrement);
        if (_modeName != null) {
            indent(indent + IndentIncrement);
            Util.println("mode " + _modeName);
        }
    }

    /**
     * Returns true if this <xsl:apply-imports/> element has parameters
     * <p>
     *  如果此<xsl：apply-imports />元素具有参数,则返回true
     * 
     */
    public boolean hasWithParams() {
        return hasContents();
    }

    /**
     * Determine the lowest import precedence for any stylesheet imported
     * or included by the stylesheet in which this <xsl:apply-imports/>
     * element occured. The templates that are imported by the stylesheet in
     * which this element occured will all have higher import precedence than
     * the integer returned by this method.
     * <p>
     *  确定导入或包含在样式表中的<xsl：apply-imported />元素的最低导入优先级。由此元素出现的样式表导入的模板将具有比此方法返回的整数更高的导入优先级。
     * 
     */
    private int getMinPrecedence(int max) {
        // Move to root of include tree
        Stylesheet includeRoot = getStylesheet();
        while (includeRoot._includedFrom != null) {
            includeRoot = includeRoot._includedFrom;
        }

        return includeRoot.getMinimumDescendantPrecedence();
    }

    /**
     * Parse the attributes and contents of an <xsl:apply-imports/> element.
     * <p>
     *  解析<xsl：apply-imports />元素的属性和内容。
     * 
     */
    public void parseContents(Parser parser) {
        // Indicate to the top-level stylesheet that all templates must be
        // compiled into separate methods.
        Stylesheet stylesheet = getStylesheet();
        stylesheet.setTemplateInlining(false);

        // Get the mode we are currently in (might not be any)
        Template template = getTemplate();
        _modeName = template.getModeName();
        _precedence = template.getImportPrecedence();

        // Get the method name for <xsl:apply-imports/> in this mode
        stylesheet = parser.getTopLevelStylesheet();

        parseChildren(parser);  // with-params
    }

    /**
     * Type-check the attributes/contents of an <xsl:apply-imports/> element.
     * <p>
     *  键入 - 检查<xsl：apply-imports />元素的属性/内容。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        typeCheckContents(stable);              // with-params
        return Type.Void;
    }

    /**
     * Translate call-template. A parameter frame is pushed only if
     * some template in the stylesheet uses parameters.
     * <p>
     *  翻译调用模板。仅当样式表中的某些模板使用参数时,才推送参数框架。
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final Stylesheet stylesheet = classGen.getStylesheet();
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        final int current = methodGen.getLocalIndex("current");

        // Push the arguments that are passed to applyTemplates()
        il.append(classGen.loadTranslet());
        il.append(methodGen.loadDOM());
    il.append(methodGen.loadIterator());
        il.append(methodGen.loadHandler());
    il.append(methodGen.loadCurrentNode());

        // Push a new parameter frame in case imported template might expect
        // parameters.  The apply-imports has nothing that it can pass.
        if (stylesheet.hasLocalParams()) {
            il.append(classGen.loadTranslet());
            final int pushFrame = cpg.addMethodref(TRANSLET_CLASS,
                                                   PUSH_PARAM_FRAME,
                                                   PUSH_PARAM_FRAME_SIG);
            il.append(new INVOKEVIRTUAL(pushFrame));
        }

        // Get the [min,max> precedence of all templates imported under the
        // current stylesheet
        final int maxPrecedence = _precedence;
        final int minPrecedence = getMinPrecedence(maxPrecedence);
        final Mode mode = stylesheet.getMode(_modeName);

        // Get name of appropriate apply-templates function for this
        // xsl:apply-imports instruction
        String functionName = mode.functionName(minPrecedence, maxPrecedence);

        // Construct the translet class-name and the signature of the method
        final String className = classGen.getStylesheet().getClassName();
        final String signature = classGen.getApplyTemplatesSigForImport();
        final int applyTemplates = cpg.addMethodref(className,
                                                    functionName,
                                                    signature);
        il.append(new INVOKEVIRTUAL(applyTemplates));

        // Pop any parameter frame that was pushed above.
        if (stylesheet.hasLocalParams()) {
            il.append(classGen.loadTranslet());
            final int pushFrame = cpg.addMethodref(TRANSLET_CLASS,
                                                   POP_PARAM_FRAME,
                                                   POP_PARAM_FRAME_SIG);
            il.append(new INVOKEVIRTUAL(pushFrame));
        }
    }

}
