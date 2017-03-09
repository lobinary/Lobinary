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
 * $Id: UnsupportedElement.java,v 1.2.4.1 2005/09/05 09:26:51 pvedula Exp $
 * <p>
 *  $ Id：UnsupportedElement.java,v 1.2.4.1 2005/09/05 09:26:51 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

/**
/* <p>
/* 
 * @author Morten Jorgensen
 */
final class UnsupportedElement extends SyntaxTreeNode {

    private Vector _fallbacks = null;
    private ErrorMsg _message = null;
    private boolean _isExtension = false;

    /**
     * Basic consutrcor - stores element uri/prefix/localname
     * <p>
     *  基本consutrcor  - 存储元素uri / prefix / localname
     * 
     */
    public UnsupportedElement(String uri, String prefix, String local, boolean isExtension) {
        super(uri, prefix, local);
        _isExtension = isExtension;
    }

    /**
     * There are different categories of unsupported elements (believe it
     * or not): there are elements within the XSLT namespace (these would
     * be elements that are not yet implemented), there are extensions of
     * other XSLT processors and there are unrecognised extension elements
     * of this XSLT processor. The error message passed to this method
     * should describe the unsupported element itself and what category
     * the element belongs in.
     * <p>
     *  有不同类别的不支持的元素(相信或不相信)：XSLT命名空间中有元素(这些将是尚未实现的元素),有其他XSLT处理器的扩展,还有这个XSLT处理器的无法识别的扩展元素。
     * 传递给此方法的错误消息应描述不受支持的元素本身和元素所属的类别。
     * 
     */
    public void setErrorMessage(ErrorMsg message) {
        _message = message;
    }

    /**
     * Displays the contents of this element
     * <p>
     *  显示此元素的内容
     * 
     */
    public void display(int indent) {
        indent(indent);
        Util.println("Unsupported element = " + _qname.getNamespace() +
                     ":" + _qname.getLocalPart());
        displayContents(indent + IndentIncrement);
    }


    /**
     * Scan and process all fallback children of the unsupported element.
     * <p>
     *  扫描并处理不受支持元素的所有后备子项。
     * 
     */
    private void processFallbacks(Parser parser) {

        Vector children = getContents();
        if (children != null) {
            final int count = children.size();
            for (int i = 0; i < count; i++) {
                SyntaxTreeNode child = (SyntaxTreeNode)children.elementAt(i);
                if (child instanceof Fallback) {
                    Fallback fallback = (Fallback)child;
                    fallback.activate();
                    fallback.parseContents(parser);
                    if (_fallbacks == null) {
                        _fallbacks = new Vector();
                    }
                    _fallbacks.addElement(child);
                }
            }
        }
    }

    /**
     * Find any fallback in the descendant nodes; then activate & parse it
     * <p>
     *  在后代节点中找到任何后备;然后激活并解析它
     * 
     */
    public void parseContents(Parser parser) {
        processFallbacks(parser);
    }

    /**
     * Run type check on the fallback element (if any).
     * <p>
     *  对后备元素执行类型检查(如果有)。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        if (_fallbacks != null) {
            int count = _fallbacks.size();
            for (int i = 0; i < count; i++) {
                Fallback fallback = (Fallback)_fallbacks.elementAt(i);
                fallback.typeCheck(stable);
            }
        }
        return Type.Void;
    }

    /**
     * Translate the fallback element (if any).
     * <p>
     * 翻译后备元素(如果有)。
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        if (_fallbacks != null) {
            int count = _fallbacks.size();
            for (int i = 0; i < count; i++) {
                Fallback fallback = (Fallback)_fallbacks.elementAt(i);
                fallback.translate(classGen, methodGen);
            }
        }
        // We only go into the else block in forward-compatibility mode, when
        // the unsupported element has no fallback.
        else {
            // If the unsupported element does not have any fallback child, then
            // at runtime, a runtime error should be raised when the unsupported
            // element is instantiated. Otherwise, no error is thrown.
            ConstantPoolGen cpg = classGen.getConstantPool();
            InstructionList il = methodGen.getInstructionList();

            final int unsupportedElem = cpg.addMethodref(BASIS_LIBRARY_CLASS, "unsupported_ElementF",
                                                         "(" + STRING_SIG + "Z)V");
            il.append(new PUSH(cpg, getQName().toString()));
            il.append(new PUSH(cpg, _isExtension));
            il.append(new INVOKESTATIC(unsupportedElem));
        }
    }
}
