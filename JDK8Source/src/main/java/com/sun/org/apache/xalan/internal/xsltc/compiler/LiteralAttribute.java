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
 * $Id: LiteralAttribute.java,v 1.2.4.1 2005/09/12 10:38:03 pvedula Exp $
 * <p>
 *  $ Id：LiteralAttribute.java,v 1.2.4.1 2005/09/12 10:38:03 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

import com.sun.org.apache.xml.internal.serializer.ElemDesc;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class LiteralAttribute extends Instruction {

    private final String  _name;         // Attribute name (incl. prefix)
    private final AttributeValue _value; // Attribute value

    /**
     * Creates a new literal attribute (but does not insert it into the AST).
     * <p>
     *  创建一个新的文本属性(但不会将其插入到AST中)。
     * 
     * 
     * @param name the attribute name (incl. prefix) as a String.
     * @param value the attribute value.
     * @param parser the XSLT parser (wraps XPath parser).
     */
    public LiteralAttribute(String name, String value, Parser parser,
        SyntaxTreeNode parent)
    {
        _name = name;
        setParent(parent);
        _value = AttributeValue.create(this, value, parser);
    }

    public void display(int indent) {
        indent(indent);
        Util.println("LiteralAttribute name=" + _name + " value=" + _value);
    }

    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        _value.typeCheck(stable);
        typeCheckContents(stable);
        return Type.Void;
    }

    protected boolean contextDependent() {
        return _value.contextDependent();
    }

    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // push handler
        il.append(methodGen.loadHandler());
        // push attribute name - namespace prefix set by parent node
        il.append(new PUSH(cpg, _name));
        // push attribute value
        _value.translate(classGen, methodGen);

        // Generate code that calls SerializationHandler.addUniqueAttribute()
        // if all attributes are unique.
        SyntaxTreeNode parent = getParent();
        if (parent instanceof LiteralElement
            && ((LiteralElement)parent).allAttributesUnique()) {

            int flags = 0;
            boolean isHTMLAttrEmpty = false;
            ElemDesc elemDesc = ((LiteralElement)parent).getElemDesc();

            // Set the HTML flags
            if (elemDesc != null) {
                if (elemDesc.isAttrFlagSet(_name, ElemDesc.ATTREMPTY)) {
                    flags = flags | SerializationHandler.HTML_ATTREMPTY;
                    isHTMLAttrEmpty = true;
                }
                else if (elemDesc.isAttrFlagSet(_name, ElemDesc.ATTRURL)) {
                    flags = flags | SerializationHandler.HTML_ATTRURL;
                }
            }

            if (_value instanceof SimpleAttributeValue) {
                String attrValue = ((SimpleAttributeValue)_value).toString();

                if (!hasBadChars(attrValue) && !isHTMLAttrEmpty) {
                    flags = flags | SerializationHandler.NO_BAD_CHARS;
                }
            }

            il.append(new PUSH(cpg, flags));
            il.append(methodGen.uniqueAttribute());
        }
        else {
            // call attribute
            il.append(methodGen.attribute());
        }
    }

    /**
     * Return true if at least one character in the String is considered to
     * be a "bad" character. A bad character is one whose code is:
     * less than 32 (a space),
     * or greater than 126,
     * or it is one of '<', '>', '&' or '\"'.
     * This helps the serializer to decide whether the String needs to be escaped.
     * <p>
     *  如果字符串中至少有一个字符被认为是"坏"字符,则返回true。坏字符的代码是：小于32(空格)或大于126,或者是'<','>','&'或'\"之一。这有助于序列化程序决定是否需要转义字符串。
     * 
     */
    private boolean hasBadChars(String value) {
        char[] chars = value.toCharArray();
        int size = chars.length;
        for (int i = 0; i < size; i++) {
            char ch = chars[i];
            if (ch < 32 || 126 < ch || ch == '<' || ch == '>' || ch == '&' || ch == '\"')
                return true;
        }
        return false;
    }

    /**
     * Return the name of the attribute
     * <p>
     *  返回属性的名称
     * 
     */
    public String getName() {
        return _name;
    }

    /**
     * Return the value of the attribute
     * <p>
     *  返回属性的值
     */
    public AttributeValue getValue() {
        return _value;
    }

}
