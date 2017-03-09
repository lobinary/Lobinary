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
 * $Id: LiteralElement.java,v 1.2.4.1 2005/09/13 12:38:33 pvedula Exp $
 * <p>
 *  $ Id：LiteralElement.java,v 1.2.4.1 2005/09/13 12:38:33 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

import com.sun.org.apache.xml.internal.serializer.ElemDesc;
import com.sun.org.apache.xml.internal.serializer.ToHTMLStream;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class LiteralElement extends Instruction {

    private String _name;
    private LiteralElement _literalElemParent = null;
    private Vector _attributeElements = null;
    private Hashtable _accessedPrefixes = null;

    // True if all attributes of this LRE are unique, i.e. they all have
    // different names. This flag is set to false if some attribute
    // names are not known at compile time.
    private boolean _allAttributesUnique = false;

    private final static String XMLNS_STRING = "xmlns";

    /**
     * Returns the QName for this literal element
     * <p>
     *  返回此文字元素的QName
     * 
     */
    public QName getName() {
        return _qname;
    }

    /**
     * Displays the contents of this literal element
     * <p>
     *  显示此文本元素的内容
     * 
     */
    public void display(int indent) {
        indent(indent);
        Util.println("LiteralElement name = " + _name);
        displayContents(indent + IndentIncrement);
    }

    /**
     * Returns the namespace URI for which a prefix is pointing to
     * <p>
     *  返回前缀所指向的命名空间URI
     * 
     */
    private String accessedNamespace(String prefix) {
        if (_literalElemParent != null) {
            String result = _literalElemParent.accessedNamespace(prefix);
            if (result != null) {
                return result;
            }
        }
        return _accessedPrefixes != null ?
            (String) _accessedPrefixes.get(prefix) : null;
    }

    /**
     * Method used to keep track of what namespaces that are references by
     * this literal element and its attributes. The output must contain a
     * definition for each namespace, so we stuff them in a hashtable.
     * <p>
     *  用于跟踪由此文字元素及其属性引用的命名空间的方法。输出必须包含每个命名空间的定义,因此我们将它们放在散列表中。
     * 
     */
    public void registerNamespace(String prefix, String uri,
                                  SymbolTable stable, boolean declared) {

        // Check if the parent has a declaration for this namespace
        if (_literalElemParent != null) {
            final String parentUri = _literalElemParent.accessedNamespace(prefix);
            if (parentUri != null && parentUri.equals(uri)) {
                return;
            }
        }

        // Check if we have any declared namesaces
        if (_accessedPrefixes == null) {
            _accessedPrefixes = new Hashtable();
        }
        else {
            if (!declared) {
                // Check if this node has a declaration for this namespace
                final String old = (String)_accessedPrefixes.get(prefix);
                if (old != null) {
                    if (old.equals(uri))
                        return;
                    else
                        prefix = stable.generateNamespacePrefix();
                }
            }
        }

        if (!prefix.equals("xml")) {
            _accessedPrefixes.put(prefix,uri);
        }
    }

    /**
     * Translates the prefix of a QName according to the rules set in
     * the attributes of xsl:stylesheet. Also registers a QName to assure
     * that the output element contains the necessary namespace declarations.
     * <p>
     *  根据xsl：stylesheet属性中设置的规则翻译QName的前缀。还注册一个QName,以确保输出元素包含必要的命名空间声明。
     * 
     */
    private String translateQName(QName qname, SymbolTable stable) {
        // Break up the QName and get prefix:localname strings
        String localname = qname.getLocalPart();
        String prefix = qname.getPrefix();

        // Treat default namespace as "" and not null
        if (prefix == null)
            prefix = Constants.EMPTYSTRING;
        else if (prefix.equals(XMLNS_STRING))
            return(XMLNS_STRING);

        // Check if we must translate the prefix
        final String alternative = stable.lookupPrefixAlias(prefix);
        if (alternative != null) {
            stable.excludeNamespaces(prefix);
            prefix = alternative;
        }

        // Get the namespace this prefix refers to
        String uri = lookupNamespace(prefix);
        if (uri == null) return(localname);

        // Register the namespace as accessed
        registerNamespace(prefix, uri, stable, false);

        // Construct the new name for the element (may be unchanged)
        if (prefix != Constants.EMPTYSTRING)
            return(prefix+":"+localname);
        else
            return(localname);
    }

    /**
     * Add an attribute to this element
     * <p>
     *  向此元素添加属性
     * 
     */
    public void addAttribute(SyntaxTreeNode attribute) {
        if (_attributeElements == null) {
            _attributeElements = new Vector(2);
        }
        _attributeElements.add(attribute);
    }

    /**
     * Set the first attribute of this element
     * <p>
     *  设置此元素的第一个属性
     * 
     */
    public void setFirstAttribute(SyntaxTreeNode attribute) {
        if (_attributeElements == null) {
            _attributeElements = new Vector(2);
        }
        _attributeElements.insertElementAt(attribute,0);
    }

    /**
     * Type-check the contents of this element. The element itself does not
     * need any type checking as it leaves nothign on the JVM's stack.
     * <p>
     * 键入 - 检查此元素的内容。元素本身不需要任何类型检查,因为它离开不在JVM的堆栈。
     * 
     */
    public Type typeCheck(SymbolTable stable) throws TypeCheckError {
        // Type-check all attributes
        if (_attributeElements != null) {
            final int count = _attributeElements.size();
            for (int i = 0; i < count; i++) {
                SyntaxTreeNode node =
                    (SyntaxTreeNode)_attributeElements.elementAt(i);
                node.typeCheck(stable);
            }
        }
        typeCheckContents(stable);
        return Type.Void;
    }

    /**
     * This method starts at a given node, traverses all namespace mappings,
     * and assembles a list of all prefixes that (for the given node) maps
     * to _ANY_ namespace URI. Used by literal result elements to determine
     * <p>
     *  此方法从给定节点开始,遍历所有命名空间映射,并汇编(对于给定节点)映射到_ANY_命名空间URI的所有前缀的列表。由字面结果元素用于确定
     * 
     */
    public Enumeration getNamespaceScope(SyntaxTreeNode node) {
        Hashtable all = new Hashtable();

        while (node != null) {
            Hashtable mapping = node.getPrefixMapping();
            if (mapping != null) {
                Enumeration prefixes = mapping.keys();
                while (prefixes.hasMoreElements()) {
                    String prefix = (String)prefixes.nextElement();
                    if (!all.containsKey(prefix)) {
                        all.put(prefix, mapping.get(prefix));
                    }
                }
            }
            node = node.getParent();
        }
        return(all.keys());
    }

    /**
     * Determines the final QName for the element and its attributes.
     * Registers all namespaces that are used by the element/attributes
     * <p>
     *  确定元素及其属性的最终QName。注册元素/属性使用的所有命名空间
     * 
     */
    public void parseContents(Parser parser) {
        final SymbolTable stable = parser.getSymbolTable();
        stable.setCurrentNode(this);

        // Check if in a literal element context
        SyntaxTreeNode parent = getParent();
        if (parent != null && parent instanceof LiteralElement) {
            _literalElemParent = (LiteralElement) parent;
        }

        _name = translateQName(_qname, stable);

        // Process all attributes and register all namespaces they use
        final int count = _attributes.getLength();
        for (int i = 0; i < count; i++) {
            final QName qname = parser.getQName(_attributes.getQName(i));
            final String uri = qname.getNamespace();
            final String val = _attributes.getValue(i);

            // Handle xsl:use-attribute-sets. Attribute sets are placed first
            // in the vector or attributes to make sure that later local
            // attributes can override an attributes in the set.
            if (qname.equals(parser.getUseAttributeSets())) {
                if (!Util.isValidQNames(val)) {
                    ErrorMsg err = new ErrorMsg(ErrorMsg.INVALID_QNAME_ERR, val, this);
                    parser.reportError(Constants.ERROR, err);
               }
                setFirstAttribute(new UseAttributeSets(val, parser));
            }
            // Handle xsl:extension-element-prefixes
            else if (qname.equals(parser.getExtensionElementPrefixes())) {
                stable.excludeNamespaces(val);
            }
            // Handle xsl:exclude-result-prefixes
            else if (qname.equals(parser.getExcludeResultPrefixes())) {
                stable.excludeNamespaces(val);
            }
            else {
                // Ignore special attributes (e.g. xmlns:prefix and xmlns)
                final String prefix = qname.getPrefix();
                if (prefix != null && prefix.equals(XMLNS_PREFIX) ||
                    prefix == null && qname.getLocalPart().equals("xmlns") ||
                    uri != null && uri.equals(XSLT_URI))
                {
                    continue;
                }

                // Handle all other literal attributes
                final String name = translateQName(qname, stable);
                LiteralAttribute attr = new LiteralAttribute(name, val, parser, this);
                addAttribute(attr);
                attr.setParent(this);
                attr.parseContents(parser);
            }
        }

        // Register all namespaces that are in scope, except for those that
        // are listed in the xsl:stylesheet element's *-prefixes attributes
        final Enumeration include = getNamespaceScope(this);
        while (include.hasMoreElements()) {
            final String prefix = (String)include.nextElement();
            if (!prefix.equals("xml")) {
                final String uri = lookupNamespace(prefix);
                if (uri != null && !stable.isExcludedNamespace(uri)) {
                    registerNamespace(prefix, uri, stable, true);
                }
            }
        }

        parseChildren(parser);

        // Process all attributes and register all namespaces they use
        for (int i = 0; i < count; i++) {
            final QName qname = parser.getQName(_attributes.getQName(i));
            final String val = _attributes.getValue(i);

            // Handle xsl:extension-element-prefixes
            if (qname.equals(parser.getExtensionElementPrefixes())) {
                stable.unExcludeNamespaces(val);
            }
            // Handle xsl:exclude-result-prefixes
            else if (qname.equals(parser.getExcludeResultPrefixes())) {
                stable.unExcludeNamespaces(val);
            }
        }
    }

    protected boolean contextDependent() {
        return dependentContents();
    }

    /**
     * Compiles code that emits the literal element to the output handler,
     * first the start tag, then namespace declaration, then attributes,
     * then the element contents, and then the element end tag. Since the
     * value of an attribute may depend on a variable, variables must be
     * compiled first.
     * <p>
     *  将发出文字元素的代码编译到输出处理程序,首先是开始标记,然后是命名空间声明,然后是属性,然后是元素内容,然后是元素结束标记。由于属性的值可能取决于变量,因此必须首先编译变量。
     * 
     */
    public void translate(ClassGenerator classGen, MethodGenerator methodGen) {

        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();

        // Check whether all attributes are unique.
        _allAttributesUnique = checkAttributesUnique();

        // Compile code to emit element start tag
        il.append(methodGen.loadHandler());

        il.append(new PUSH(cpg, _name));
        il.append(DUP2);                // duplicate these 2 args for endElement
        il.append(methodGen.startElement());

        // The value of an attribute may depend on a (sibling) variable
        int j=0;
        while (j < elementCount())  {
            final SyntaxTreeNode item = (SyntaxTreeNode) elementAt(j);
            if (item instanceof Variable) {
                item.translate(classGen, methodGen);
            }
            j++;
        }

        // Compile code to emit namespace attributes
        if (_accessedPrefixes != null) {
            boolean declaresDefaultNS = false;
            Enumeration e = _accessedPrefixes.keys();

            while (e.hasMoreElements()) {
                final String prefix = (String)e.nextElement();
                final String uri = (String)_accessedPrefixes.get(prefix);

                if (uri != Constants.EMPTYSTRING ||
                        prefix != Constants.EMPTYSTRING)
                {
                    if (prefix == Constants.EMPTYSTRING) {
                        declaresDefaultNS = true;
                    }
                    il.append(methodGen.loadHandler());
                    il.append(new PUSH(cpg,prefix));
                    il.append(new PUSH(cpg,uri));
                    il.append(methodGen.namespace());
                }
            }

            /*
             * If our XslElement parent redeclares the default NS, and this
             * element doesn't, it must be redeclared one more time.
             * <p>
             *  如果我们的XslElement父重定义默认NS,并且这个元素没有,它必须重新声明一次。
             * 
             */
            if (!declaresDefaultNS && (_parent instanceof XslElement)
                    && ((XslElement) _parent).declaresDefaultNS())
            {
                il.append(methodGen.loadHandler());
                il.append(new PUSH(cpg, Constants.EMPTYSTRING));
                il.append(new PUSH(cpg, Constants.EMPTYSTRING));
                il.append(methodGen.namespace());
            }
        }

        // Output all attributes
        if (_attributeElements != null) {
            final int count = _attributeElements.size();
            for (int i = 0; i < count; i++) {
                SyntaxTreeNode node =
                    (SyntaxTreeNode)_attributeElements.elementAt(i);
                if (!(node instanceof XslAttribute)) {
                    node.translate(classGen, methodGen);
                }
            }
        }

        // Compile code to emit attributes and child elements
        translateContents(classGen, methodGen);

        // Compile code to emit element end tag
        il.append(methodGen.endElement());
    }

    /**
     * Return true if the output method is html.
     * <p>
     *  如果输出方法是html,则返回true。
     * 
     */
    private boolean isHTMLOutput() {
        return getStylesheet().getOutputMethod() == Stylesheet.HTML_OUTPUT;
    }

    /**
     * Return the ElemDesc object for an HTML element.
     * Return null if the output method is not HTML or this is not a
     * valid HTML element.
     * <p>
     *  返回一个HTML元素的ElemDesc对象。如果输出方法不是HTML,或者这不是有效的HTML元素,则返回null。
     * 
     */
    public ElemDesc getElemDesc() {
        if (isHTMLOutput()) {
            return ToHTMLStream.getElemDesc(_name);
        }
        else
            return null;
    }

    /**
     * Return true if all attributes of this LRE have unique names.
     * <p>
     *  如果此LRE的所有属性都具有唯一的名称,则返回true。
     * 
     */
    public boolean allAttributesUnique() {
        return _allAttributesUnique;
    }

    /**
     * Check whether all attributes are unique.
     * <p>
     *  检查所有属性是否唯一。
     * 
     */
    private boolean checkAttributesUnique() {
         boolean hasHiddenXslAttribute = canProduceAttributeNodes(this, true);
         if (hasHiddenXslAttribute)
             return false;

         if (_attributeElements != null) {
             int numAttrs = _attributeElements.size();
             Hashtable attrsTable = null;
             for (int i = 0; i < numAttrs; i++) {
                 SyntaxTreeNode node = (SyntaxTreeNode)_attributeElements.elementAt(i);

                 if (node instanceof UseAttributeSets) {
                     return false;
                 }
                 else if (node instanceof XslAttribute) {
                     if (attrsTable == null) {
                        attrsTable = new Hashtable();
                         for (int k = 0; k < i; k++) {
                             SyntaxTreeNode n = (SyntaxTreeNode)_attributeElements.elementAt(k);
                             if (n instanceof LiteralAttribute) {
                                 LiteralAttribute literalAttr = (LiteralAttribute)n;
                                 attrsTable.put(literalAttr.getName(), literalAttr);
                             }
                         }
                     }

                     XslAttribute xslAttr = (XslAttribute)node;
                     AttributeValue attrName = xslAttr.getName();
                     if (attrName instanceof AttributeValueTemplate) {
                         return false;
                     }
                     else if (attrName instanceof SimpleAttributeValue) {
                         SimpleAttributeValue simpleAttr = (SimpleAttributeValue)attrName;
                         String name = simpleAttr.toString();
                         if (name != null && attrsTable.get(name) != null)
                             return false;
                         else if (name != null) {
                             attrsTable.put(name, xslAttr);
                         }
                     }
                 }
             }
         }
         return true;
    }

    /**
     * Return true if the instructions under the given SyntaxTreeNode can produce attribute nodes
     * to an element. Only return false when we are sure that no attribute node is produced.
     * Return true if we are not sure. If the flag ignoreXslAttribute is true, the direct
     * <xsl:attribute> children of the current node are not included in the check.
     * <p>
     *  如果给定的SyntaxTreeNode下的指令可以为元素生成属性节点,则返回true。只有当我们确定没有产生属性节点时才返回false。如果我们不确定,返回true。
     * 如果标记ignoreXslAttribute为true,则当前节点的直接<xsl：attribute>子项不包括在检查中。
     */
    private boolean canProduceAttributeNodes(SyntaxTreeNode node, boolean ignoreXslAttribute) {
        Vector contents = node.getContents();
        int size = contents.size();
        for (int i = 0; i < size; i++) {
            SyntaxTreeNode child = (SyntaxTreeNode)contents.elementAt(i);
            if (child instanceof Text) {
                Text text = (Text)child;
                if (text.isIgnore())
                    continue;
                else
                    return false;
            }
            // Cannot add an attribute to an element after children have been added to it.
            // We can safely return false when the instruction can produce an output node.
            else if (child instanceof LiteralElement
                || child instanceof ValueOf
                || child instanceof XslElement
                || child instanceof Comment
                || child instanceof Number
                || child instanceof ProcessingInstruction)
                return false;
            else if (child instanceof XslAttribute) {
                if (ignoreXslAttribute)
                    continue;
                else
                    return true;
            }
            // In general, there is no way to check whether <xsl:call-template> or
            // <xsl:apply-templates> can produce attribute nodes. <xsl:copy> and
            // <xsl:copy-of> can also copy attribute nodes to an element. Return
            // true in those cases to be safe.
            else if (child instanceof CallTemplate
                || child instanceof ApplyTemplates
                || child instanceof Copy
                || child instanceof CopyOf)
                return true;
            else if ((child instanceof If
                       || child instanceof ForEach)
                     && canProduceAttributeNodes(child, false)) {
                return true;
            }
            else if (child instanceof Choose) {
                Vector chooseContents = child.getContents();
                int num = chooseContents.size();
                for (int k = 0; k < num; k++) {
                    SyntaxTreeNode chooseChild = (SyntaxTreeNode)chooseContents.elementAt(k);
                    if (chooseChild instanceof When || chooseChild instanceof Otherwise) {
                        if (canProduceAttributeNodes(chooseChild, false))
                            return true;
                    }
                }
            }
        }
        return false;
    }

}
