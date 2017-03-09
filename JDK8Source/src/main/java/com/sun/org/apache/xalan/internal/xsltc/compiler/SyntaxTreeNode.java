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
 * $Id: SyntaxTreeNode.java,v 1.6 2006/06/06 22:34:33 spericas Exp $
 * <p>
 *  $ Id：SyntaxTreeNode.java,v 1.6 2006/06/06 22:34:33 spericas Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import com.sun.org.apache.bcel.internal.generic.BasicType;
import com.sun.org.apache.bcel.internal.generic.CHECKCAST;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.DUP_X1;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;
import com.sun.org.apache.bcel.internal.generic.ICONST;
import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.INVOKEVIRTUAL;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ClassGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodGenerator;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;


/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author G. Todd Miller
 * @author Morten Jorensen
 * @author Erwin Bolwidt <ejb@klomp.org>
 * @author John Howard <JohnH@schemasoft.com>
 */
public abstract class SyntaxTreeNode implements Constants {

    // Reference to the AST parser
    private Parser _parser;

    // AST navigation pointers
    protected SyntaxTreeNode _parent;          // Parent node
    private Stylesheet       _stylesheet;      // Stylesheet ancestor node
    private Template         _template;        // Template ancestor node
    private final Vector _contents = new Vector(2); // Child nodes

    // Element description data
    protected QName _qname;                    // The element QName
    private int _line;                         // Source file line number
    protected AttributesImpl _attributes = null;   // Attributes of this element
    private   Hashtable _prefixMapping = null; // Namespace declarations

    // Sentinel - used to denote unrecognised syntaxt tree nodes.
    protected static final SyntaxTreeNode Dummy = new AbsolutePathPattern(null);

    // These two are used for indenting nodes in the AST (debug output)
    protected static final int IndentIncrement = 4;
    private static final char[] _spaces =
        "                                                       ".toCharArray();

    /**
     * Creates a new SyntaxTreeNode with a 'null' QName and no source file
     * line number reference.
     * <p>
     *  创建一个带有'null'QName并且没有源文件行号引用的新的SyntaxTreeNode。
     * 
     */
    public SyntaxTreeNode() {
        _line = 0;
        _qname = null;
    }

    /**
     * Creates a new SyntaxTreeNode with a 'null' QName.
     * <p>
     *  创建一个带有'null'QName的新的SyntaxTreeNode。
     * 
     * 
     * @param line Source file line number reference
     */
    public SyntaxTreeNode(int line) {
        _line = line;
        _qname = null;
    }

    /**
     * Creates a new SyntaxTreeNode with no source file line number reference.
     * <p>
     *  创建一个没有源文件行号引用的新的SyntaxTreeNode。
     * 
     * 
     * @param uri The element's namespace URI
     * @param prefix The element's namespace prefix
     * @param local The element's local name
     */
    public SyntaxTreeNode(String uri, String prefix, String local) {
        _line = 0;
        setQName(uri, prefix, local);
    }

    /**
     * Set the source file line number for this element
     * <p>
     *  设置此元素的源文件行号
     * 
     * 
     * @param line The source file line number.
     */
    protected final void setLineNumber(int line) {
        _line = line;
    }

    /**
     * Get the source file line number for this element. If unavailable, lookup
     * in ancestors.
     *
     * <p>
     *  获取此元素的源文件行号。如果不可用,查找祖先。
     * 
     * 
     * @return The source file line number.
     */
    public final int getLineNumber() {
        if (_line > 0) return _line;
        SyntaxTreeNode parent = getParent();
        return (parent != null) ? parent.getLineNumber() : 0;
    }

    /**
     * Set the QName for the syntax tree node.
     * <p>
     *  设置语法树节点的QName。
     * 
     * 
     * @param qname The QName for the syntax tree node
     */
    protected void setQName(QName qname) {
        _qname = qname;
    }

    /**
     * Set the QName for the SyntaxTreeNode
     * <p>
     *  设置SyntaxTreeNode的QName
     * 
     * 
     * @param uri The element's namespace URI
     * @param prefix The element's namespace prefix
     * @param local The element's local name
     */
    protected void setQName(String uri, String prefix, String localname) {
        _qname = new QName(uri, prefix, localname);
    }

    /**
     * Set the QName for the SyntaxTreeNode
     * <p>
     *  设置SyntaxTreeNode的QName
     * 
     * 
     * @param qname The QName for the syntax tree node
     */
    protected QName getQName() {
        return(_qname);
    }

    /**
     * Set the attributes for this SyntaxTreeNode.
     * <p>
     *  设置此SyntaxTreeNode的属性。
     * 
     * 
     * @param attributes Attributes for the element. Must be passed in as an
     *                   implementation of org.xml.sax.Attributes.
     */
    protected void setAttributes(AttributesImpl attributes) {
        _attributes = attributes;
    }

    /**
     * Returns a value for an attribute from the source element.
     * <p>
     *  从源元素返回属性的值。
     * 
     * 
     * @param qname The QName of the attribute to return.
     * @return The value of the attribute of name 'qname'.
     */
    protected String getAttribute(String qname) {
        if (_attributes == null) {
            return EMPTYSTRING;
        }
        final String value = _attributes.getValue(qname);
        return (value == null || value.equals(EMPTYSTRING)) ?
            EMPTYSTRING : value;
    }

    protected String getAttribute(String prefix, String localName) {
        return getAttribute(prefix + ':' + localName);
    }

    protected boolean hasAttribute(String qname) {
        return (_attributes != null && _attributes.getValue(qname) != null);
    }

    protected void addAttribute(String qname, String value) {
        int index = _attributes.getIndex(qname);
        if (index != -1) {
            _attributes.setAttribute(index, "", Util.getLocalName(qname),
                    qname, "CDATA", value);
        }
        else {
            _attributes.addAttribute("", Util.getLocalName(qname), qname,
                    "CDATA", value);
        }
    }

    /**
     * Returns a list of all attributes declared for the element represented by
     * this syntax tree node.
     * <p>
     *  返回由此语法树节点表示的元素声明的所有属性的列表。
     * 
     * 
     * @return Attributes for this syntax tree node
     */
    protected Attributes getAttributes() {
        return(_attributes);
    }

    /**
     * Sets the prefix mapping for the namespaces that were declared in this
     * element. This does not include all prefix mappings in scope, so one
     * may have to check ancestor elements to get all mappings that are in
     * in scope. The prefixes must be passed in as a Hashtable that maps
     * namespace prefixes (String objects) to namespace URIs (also String).
     * <p>
     * 为在此元素中声明的命名空间设置前缀映射。这不包括范围中的所有前缀映射,因此可能必须检查祖先元素以获取范围内的所有映射。
     * 前缀必须作为将命名空间前缀(String对象)映射到命名空间URI(也是String)的Hashtable传递。
     * 
     * 
     * @param mapping The Hashtable containing the mappings.
     */
    protected void setPrefixMapping(Hashtable mapping) {
        _prefixMapping = mapping;
    }

    /**
     * Returns a Hashtable containing the prefix mappings that were declared
     * for this element. This does not include all prefix mappings in scope,
     * so one may have to check ancestor elements to get all mappings that are
     * in in scope.
     * <p>
     *  返回包含为此元素声明的前缀映射的Hashtable。这不包括范围中的所有前缀映射,因此可能必须检查祖先元素以获取范围内的所有映射。
     * 
     * 
     * @return Prefix mappings (for this element only).
     */
    protected Hashtable getPrefixMapping() {
        return _prefixMapping;
    }

    /**
     * Adds a single prefix mapping to this syntax tree node.
     * <p>
     *  向此语法树节点添加单个前缀映射。
     * 
     * 
     * @param prefix Namespace prefix.
     * @param uri Namespace URI.
     */
    protected void addPrefixMapping(String prefix, String uri) {
        if (_prefixMapping == null)
            _prefixMapping = new Hashtable();
        _prefixMapping.put(prefix, uri);
    }

    /**
     * Returns any namespace URI that is in scope for a given prefix. This
     * method checks namespace mappings for this element, and if necessary
     * for ancestor elements as well (ie. if the prefix maps to an URI in this
     * scope then you'll definately get the URI from this method).
     * <p>
     *  返回给定前缀范围内的任何命名空间URI。此方法检查此元素的命名空间映射,如果必要,还检查祖先元素(即如果前缀映射到此范围中的URI,那么您将从此方法中明确获取URI)。
     * 
     * 
     * @param prefix Namespace prefix.
     * @return Namespace URI.
     */
    protected String lookupNamespace(String prefix) {
        // Initialise the output (default is 'null' for undefined)
        String uri = null;

        // First look up the prefix/uri mapping in our own hashtable...
        if (_prefixMapping != null)
            uri = (String)_prefixMapping.get(prefix);
        // ... but if we can't find it there we ask our parent for the mapping
        if ((uri == null) && (_parent != null)) {
            uri = _parent.lookupNamespace(prefix);
            if ((prefix == Constants.EMPTYSTRING) && (uri == null))
                uri = Constants.EMPTYSTRING;
        }
        // ... and then we return whatever URI we've got.
        return(uri);
    }

    /**
     * Returns any namespace prefix that is mapped to a prefix in the current
     * scope. This method checks namespace mappings for this element, and if
     * necessary for ancestor elements as well (ie. if the URI is declared
     * within the current scope then you'll definately get the prefix from
     * this method). Note that this is a very slow method and consequentially
     * it should only be used strictly when needed.
     * <p>
     *  返回映射到当前作用域中的前缀的任何命名空间前缀。此方法检查此元素的命名空间映射,如果必要,还检查祖先元素(即,如果URI在当前范围内声明,那么您将明确地从此方法获取前缀)。
     * 注意,这是一个非常缓慢的方法,因此它应该只在需要时严格使用。
     * 
     * 
     * @param uri Namespace URI.
     * @return Namespace prefix.
     */
    protected String lookupPrefix(String uri) {
        // Initialise the output (default is 'null' for undefined)
        String prefix = null;

        // First look up the prefix/uri mapping in our own hashtable...
        if ((_prefixMapping != null) &&
            (_prefixMapping.contains(uri))) {
            Enumeration prefixes = _prefixMapping.keys();
            while (prefixes.hasMoreElements()) {
                prefix = (String)prefixes.nextElement();
                String mapsTo = (String)_prefixMapping.get(prefix);
                if (mapsTo.equals(uri)) return(prefix);
            }
        }
        // ... but if we can't find it there we ask our parent for the mapping
        else if (_parent != null) {
            prefix = _parent.lookupPrefix(uri);
            if ((uri == Constants.EMPTYSTRING) && (prefix == null))
                prefix = Constants.EMPTYSTRING;
        }
        return(prefix);
    }

    /**
     * Set this node's parser. The parser (the XSLT parser) gives this
     * syntax tree node access to the symbol table and XPath parser.
     * <p>
     *  设置此节点的解析器。解析器(XSLT解析器)给这个语法树节点访问符号表和XPath解析器。
     * 
     * 
     * @param parser The XSLT parser.
     */
    protected void setParser(Parser parser) {
        _parser = parser;
    }

    /**
     * Returns this node's XSLT parser.
     * <p>
     *  返回此节点的XSLT解析器。
     * 
     * 
     * @return The XSLT parser.
     */
    public final Parser getParser() {
        return _parser;
    }

    /**
     * Set this syntax tree node's parent node, if unset. For
     * re-parenting just use <code>node._parent = newparent</code>.
     *
     * <p>
     * 如果未设置,请设置此语法树节点的父节点。对于重新生成,只需使用<code> node._parent = newparent </code>。
     * 
     * 
     * @param parent The parent node.
     */
    protected void setParent(SyntaxTreeNode parent) {
        if (_parent == null) _parent = parent;
    }

    /**
     * Returns this syntax tree node's parent node.
     * <p>
     *  返回此语法树节点的父节点。
     * 
     * 
     * @return The parent syntax tree node.
     */
    protected final SyntaxTreeNode getParent() {
        return _parent;
    }

    /**
     * Returns 'true' if this syntax tree node is the Sentinal node.
     * <p>
     *  如果此语法树节点是Sentinal节点,则返回'true'。
     * 
     * 
     * @return 'true' if this syntax tree node is the Sentinal node.
     */
    protected final boolean isDummy() {
        return this == Dummy;
    }

    /**
     * Get the import precedence of this element. The import precedence equals
     * the import precedence of the stylesheet in which this element occured.
     * <p>
     *  获取此元素的导入优先级。导入优先级等于此元素出现的样式表的导入优先级。
     * 
     * 
     * @return The import precedence of this syntax tree node.
     */
    protected int getImportPrecedence() {
        Stylesheet stylesheet = getStylesheet();
        if (stylesheet == null) return Integer.MIN_VALUE;
        return stylesheet.getImportPrecedence();
    }

    /**
     * Get the Stylesheet node that represents the <xsl:stylesheet/> element
     * that this node occured under.
     * <p>
     *  获取表示此节点出现的<xsl：stylesheet />元素的样式表节点。
     * 
     * 
     * @return The Stylesheet ancestor node of this node.
     */
    public Stylesheet getStylesheet() {
        if (_stylesheet == null) {
            SyntaxTreeNode parent = this;
            while (parent != null) {
                if (parent instanceof Stylesheet)
                    return((Stylesheet)parent);
                parent = parent.getParent();
            }
            _stylesheet = (Stylesheet)parent;
        }
        return(_stylesheet);
    }

    /**
     * Get the Template node that represents the <xsl:template/> element
     * that this node occured under. Note that this method will return 'null'
     * for nodes that represent top-level elements.
     * <p>
     *  获取表示此节点发生的<xsl：template />元素的模板节点。请注意,此方法将为表示顶级元素的节点返回"null"。
     * 
     * 
     * @return The Template ancestor node of this node or 'null'.
     */
    protected Template getTemplate() {
        if (_template == null) {
            SyntaxTreeNode parent = this;
            while ((parent != null) && (!(parent instanceof Template)))
                parent = parent.getParent();
            _template = (Template)parent;
        }
        return(_template);
    }

    /**
     * Returns a reference to the XSLTC (XSLT compiler) in use.
     * <p>
     *  返回对正在使用的XSLTC(XSLT编译器)的引用。
     * 
     * 
     * @return XSLTC - XSLT compiler.
     */
    protected final XSLTC getXSLTC() {
        return _parser.getXSLTC();
    }

    /**
     * Returns the XSLT parser's symbol table.
     * <p>
     *  返回XSLT解析器的符号表。
     * 
     * 
     * @return Symbol table.
     */
    protected final SymbolTable getSymbolTable() {
        return (_parser == null) ? null : _parser.getSymbolTable();
    }

    /**
     * Parse the contents of this syntax tree nodes (child nodes, XPath
     * expressions, patterns and functions). The default behaviour is to parser
     * the syntax tree node's children (since there are no common expressions,
     * patterns, etc. that can be handled in this base class.
     * <p>
     *  解析此语法树节点(子节点,XPath表达式,模式和函数)的内容。默认行为是解析语法树节点的子节点(因为没有公共表达式,模式等可以在这个基类中处理。
     * 
     * 
     * @param parser reference to the XSLT parser
     */
    public void parseContents(Parser parser) {
        parseChildren(parser);
    }

    /**
     * Parse all children of this syntax tree node. This method is normally
     * called by the parseContents() method.
     * <p>
     *  解析此语法树节点的所有子节点。这个方法通常由parseContents()方法调用。
     * 
     * 
     * @param parser reference to the XSLT parser
     */
    protected final void parseChildren(Parser parser) {

        Vector locals = null;   // only create when needed

        final int count = _contents.size();
        for (int i=0; i<count; i++) {
            SyntaxTreeNode child = (SyntaxTreeNode)_contents.elementAt(i);
            parser.getSymbolTable().setCurrentNode(child);
            child.parseContents(parser);
            // if variable or parameter, add it to scope
            final QName varOrParamName = updateScope(parser, child);
            if (varOrParamName != null) {
                if (locals == null) {
                    locals = new Vector(2);
                }
                locals.addElement(varOrParamName);
            }
        }

        parser.getSymbolTable().setCurrentNode(this);

        // after the last element, remove any locals from scope
        if (locals != null) {
            final int nLocals = locals.size();
            for (int i = 0; i < nLocals; i++) {
                parser.removeVariable((QName)locals.elementAt(i));
            }
        }
    }

    /**
     * Add a node to the current scope and return name of a variable or
     * parameter if the node represents a variable or a parameter.
     * <p>
     *  如果节点表示变量或参数,则将节点添加到当前作用域并返回变量或参数的名称。
     * 
     */
    protected QName updateScope(Parser parser, SyntaxTreeNode node) {
        if (node instanceof Variable) {
            final Variable var = (Variable)node;
            parser.addVariable(var);
            return var.getName();
        }
        else if (node instanceof Param) {
            final Param param = (Param)node;
            parser.addParameter(param);
            return param.getName();
        }
        else {
            return null;
        }
    }

    /**
     * Type check the children of this node. The type check phase may add
     * coercions (CastExpr) to the AST.
     * <p>
     *  类型检查此节点的子节点。类型检查阶段可以向AST添加强制(CastExpr)。
     * 
     * 
     * @param stable The compiler/parser's symbol table
     */
    public abstract Type typeCheck(SymbolTable stable) throws TypeCheckError;

    /**
     * Call typeCheck() on all child syntax tree nodes.
     * <p>
     *  在所有子语法树节点上调用typeCheck()。
     * 
     * 
     * @param stable The compiler/parser's symbol table
     */
    protected Type typeCheckContents(SymbolTable stable) throws TypeCheckError {
        final int n = elementCount();
        for (int i = 0; i < n; i++) {
            SyntaxTreeNode item = (SyntaxTreeNode)_contents.elementAt(i);
            item.typeCheck(stable);
        }
        return Type.Void;
    }

    /**
     * Translate this abstract syntax tree node into JVM bytecodes.
     * <p>
     *  将此抽象语法树节点转换为JVM字节码。
     * 
     * 
     * @param classGen BCEL Java class generator
     * @param methodGen BCEL Java method generator
     */
    public abstract void translate(ClassGenerator classGen,
                                   MethodGenerator methodGen);

    /**
     * Call translate() on all child syntax tree nodes.
     * <p>
     * 在所有子语法树节点上调用translate()。
     * 
     * 
     * @param classGen BCEL Java class generator
     * @param methodGen BCEL Java method generator
     */
    protected void translateContents(ClassGenerator classGen,
                                     MethodGenerator methodGen) {
        // Call translate() on all child nodes
        final int n = elementCount();

        for (int i = 0; i < n; i++) {
            methodGen.markChunkStart();
            final SyntaxTreeNode item = (SyntaxTreeNode)_contents.elementAt(i);
            item.translate(classGen, methodGen);
            methodGen.markChunkEnd();
        }

        // After translation, unmap any registers for any variables/parameters
        // that were declared in this scope. Performing this unmapping in the
        // same AST scope as the declaration deals with the problems of
        // references falling out-of-scope inside the for-each element.
        // (the cause of which being 'lazy' register allocation for references)
        for (int i = 0; i < n; i++) {
            if( _contents.elementAt(i) instanceof VariableBase) {
                final VariableBase var = (VariableBase)_contents.elementAt(i);
                var.unmapRegister(methodGen);
            }
        }
    }

    /**
     * Return true if the node represents a simple RTF.
     *
     * A node is a simple RTF if all children only produce Text value.
     *
     * <p>
     *  如果节点表示简单的RTF,则返回true。
     * 
     *  如果所有的子节点都只生成Text值,节点是一个简单的RTF。
     * 
     * 
     * @param node A node
     * @return true if the node content can be considered as a simple RTF.
     */
    private boolean isSimpleRTF(SyntaxTreeNode node) {

        Vector contents = node.getContents();
        for (int i = 0; i < contents.size(); i++) {
            SyntaxTreeNode item = (SyntaxTreeNode)contents.elementAt(i);
            if (!isTextElement(item, false))
                return false;
        }

        return true;
    }

     /**
     * Return true if the node represents an adaptive RTF.
     *
     * A node is an adaptive RTF if each children is a Text element
     * or it is <xsl:call-template> or <xsl:apply-templates>.
     *
     * <p>
     *  如果节点表示自适应RTF,则返回true。
     * 
     *  如果每个子代是一个Text元素,或者它是<xsl：call-template>或<xsl：apply-templates>,则节点是自适应RTF。
     * 
     * 
     * @param node A node
     * @return true if the node content can be considered as an adaptive RTF.
     */
    private boolean isAdaptiveRTF(SyntaxTreeNode node) {

        Vector contents = node.getContents();
        for (int i = 0; i < contents.size(); i++) {
            SyntaxTreeNode item = (SyntaxTreeNode)contents.elementAt(i);
            if (!isTextElement(item, true))
                return false;
        }

        return true;
    }

    /**
     * Return true if the node only produces Text content.
     *
     * A node is a Text element if it is Text, xsl:value-of, xsl:number,
     * or a combination of these nested in a control instruction (xsl:if or
     * xsl:choose).
     *
     * If the doExtendedCheck flag is true, xsl:call-template and xsl:apply-templates
     * are also considered as Text elements.
     *
     * <p>
     *  如果节点仅生成文本内容,则返回true。
     * 
     *  如果节点是Text,xsl：value-of,xsl：number或嵌套在控制指令(xsl：if或xsl：choose)中的组合,则该节点是Text元素。
     * 
     *  如果doExtendedCheck标志为true,则xsl：call-template和xsl：apply-templates也被视为文本元素。
     * 
     * 
     * @param node A node
     * @param doExtendedCheck If this flag is true, <xsl:call-template> and
     * <xsl:apply-templates> are also considered as Text elements.
     *
     * @return true if the node of Text type
     */
    private boolean isTextElement(SyntaxTreeNode node, boolean doExtendedCheck) {
        if (node instanceof ValueOf || node instanceof Number
            || node instanceof Text)
        {
            return true;
        }
        else if (node instanceof If) {
            return doExtendedCheck ? isAdaptiveRTF(node) : isSimpleRTF(node);
        }
        else if (node instanceof Choose) {
            Vector contents = node.getContents();
            for (int i = 0; i < contents.size(); i++) {
                SyntaxTreeNode item = (SyntaxTreeNode)contents.elementAt(i);
                if (item instanceof Text ||
                     ((item instanceof When || item instanceof Otherwise)
                     && ((doExtendedCheck && isAdaptiveRTF(item))
                         || (!doExtendedCheck && isSimpleRTF(item)))))
                    continue;
                else
                    return false;
            }
            return true;
        }
        else if (doExtendedCheck &&
                  (node instanceof CallTemplate
                   || node instanceof ApplyTemplates))
            return true;
        else
            return false;
    }

    /**
     * Utility method used by parameters and variables to store result trees
     * <p>
     *  用于存储结果树的参数和变量使用的实用程序方法
     * 
     * 
     * @param classGen BCEL Java class generator
     * @param methodGen BCEL Java method generator
     */
    protected void compileResultTree(ClassGenerator classGen,
                                     MethodGenerator methodGen)
    {
        final ConstantPoolGen cpg = classGen.getConstantPool();
        final InstructionList il = methodGen.getInstructionList();
        final Stylesheet stylesheet = classGen.getStylesheet();

        boolean isSimple = isSimpleRTF(this);
        boolean isAdaptive = false;
        if (!isSimple) {
            isAdaptive = isAdaptiveRTF(this);
        }

        int rtfType = isSimple ? DOM.SIMPLE_RTF
                               : (isAdaptive ? DOM.ADAPTIVE_RTF : DOM.TREE_RTF);

        // Save the current handler base on the stack
        il.append(methodGen.loadHandler());

        final String DOM_CLASS = classGen.getDOMClass();

        // Create new instance of DOM class (with RTF_INITIAL_SIZE nodes)
        //int index = cpg.addMethodref(DOM_IMPL, "<init>", "(I)V");
        //il.append(new NEW(cpg.addClass(DOM_IMPL)));

        il.append(methodGen.loadDOM());
        int index = cpg.addInterfaceMethodref(DOM_INTF,
                                 "getResultTreeFrag",
                                 "(IIZ)" + DOM_INTF_SIG);
        il.append(new PUSH(cpg, RTF_INITIAL_SIZE));
        il.append(new PUSH(cpg, rtfType));
        il.append(new PUSH(cpg, stylesheet.callsNodeset()));
        il.append(new INVOKEINTERFACE(index,4));

        il.append(DUP);

        // Overwrite old handler with DOM handler
        index = cpg.addInterfaceMethodref(DOM_INTF,
                                 "getOutputDomBuilder",
                                 "()" + TRANSLET_OUTPUT_SIG);

        il.append(new INVOKEINTERFACE(index,1));
        il.append(DUP);
        il.append(methodGen.storeHandler());

        // Call startDocument on the new handler
        il.append(methodGen.startDocument());

        // Instantiate result tree fragment
        translateContents(classGen, methodGen);

        // Call endDocument on the new handler
        il.append(methodGen.loadHandler());
        il.append(methodGen.endDocument());

        // Check if we need to wrap the DOMImpl object in a DOMAdapter object.
        // DOMAdapter is not needed if the RTF is a simple RTF and the nodeset()
        // function is not used.
        if (stylesheet.callsNodeset()
            && !DOM_CLASS.equals(DOM_IMPL_CLASS)) {
            // new com.sun.org.apache.xalan.internal.xsltc.dom.DOMAdapter(DOMImpl,String[]);
            index = cpg.addMethodref(DOM_ADAPTER_CLASS,
                                     "<init>",
                                     "("+DOM_INTF_SIG+
                                     "["+STRING_SIG+
                                     "["+STRING_SIG+
                                     "[I"+
                                     "["+STRING_SIG+")V");
            il.append(new NEW(cpg.addClass(DOM_ADAPTER_CLASS)));
            il.append(new DUP_X1());
            il.append(SWAP);

            /*
             * Give the DOM adapter an empty type mapping if the nodeset
             * extension function is never called.
             * <p>
             *  如果从未调用节点集扩展函数,则为DOM适配器指定空类型映射。
             * 
             */
            if (!stylesheet.callsNodeset()) {
                il.append(new ICONST(0));
                il.append(new ANEWARRAY(cpg.addClass(STRING)));
                il.append(DUP);
                il.append(DUP);
                il.append(new ICONST(0));
                il.append(new NEWARRAY(BasicType.INT));
                il.append(SWAP);
                il.append(new INVOKESPECIAL(index));
            }
            else {
                // Push name arrays on the stack
                il.append(ALOAD_0);
                il.append(new GETFIELD(cpg.addFieldref(TRANSLET_CLASS,
                                           NAMES_INDEX,
                                           NAMES_INDEX_SIG)));
                il.append(ALOAD_0);
                il.append(new GETFIELD(cpg.addFieldref(TRANSLET_CLASS,
                                           URIS_INDEX,
                                           URIS_INDEX_SIG)));
                il.append(ALOAD_0);
                il.append(new GETFIELD(cpg.addFieldref(TRANSLET_CLASS,
                                           TYPES_INDEX,
                                           TYPES_INDEX_SIG)));
                il.append(ALOAD_0);
                il.append(new GETFIELD(cpg.addFieldref(TRANSLET_CLASS,
                                           NAMESPACE_INDEX,
                                           NAMESPACE_INDEX_SIG)));

                // Initialized DOM adapter
                il.append(new INVOKESPECIAL(index));

                // Add DOM adapter to MultiDOM class by calling addDOMAdapter()
                il.append(DUP);
                il.append(methodGen.loadDOM());
                il.append(new CHECKCAST(cpg.addClass(classGen.getDOMClass())));
                il.append(SWAP);
                index = cpg.addMethodref(MULTI_DOM_CLASS,
                                         "addDOMAdapter",
                                         "(" + DOM_ADAPTER_SIG + ")I");
                il.append(new INVOKEVIRTUAL(index));
                il.append(POP);         // ignore mask returned by addDOMAdapter
            }
        }

        // Restore old handler base from stack
        il.append(SWAP);
        il.append(methodGen.storeHandler());
    }

    /**
     * Returns true if this expression/instruction depends on the context. By
     * default, every expression/instruction depends on the context unless it
     * overrides this method. Currently used to determine if result trees are
     * compiled using procedures or little DOMs (result tree fragments).
     * <p>
     *  如果此表达式/指令取决于上下文,则返回true。默认情况下,每个表达式/指令取决于上下文,除非它覆盖此方法。目前用于确定结果树是使用过程还是使用小DOM(结果树片段)进行编译。
     * 
     * 
     * @return 'true' if this node depends on the context.
     */
    protected boolean contextDependent() {
        return true;
    }

    /**
     * Return true if any of the expressions/instructions in the contents of
     * this node is context dependent.
     * <p>
     *  如果此节点的内容中的任何表达式/指令是上下文相关的,则返回true。
     * 
     * 
     * @return 'true' if the contents of this node is context dependent.
     */
    protected boolean dependentContents() {
        final int n = elementCount();
        for (int i = 0; i < n; i++) {
            final SyntaxTreeNode item = (SyntaxTreeNode)_contents.elementAt(i);
            if (item.contextDependent()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a child node to this syntax tree node.
     * <p>
     *  将一个子节点添加到此语法树节点。
     * 
     * 
     * @param element is the new child node.
     */
    protected final void addElement(SyntaxTreeNode element) {
        _contents.addElement(element);
        element.setParent(this);
    }

    /**
     * Inserts the first child node of this syntax tree node. The existing
     * children are shifted back one position.
     * <p>
     *  插入此语法树节点的第一个子节点。现有的孩子被移回一个位置。
     * 
     * 
     * @param element is the new child node.
     */
    protected final void setFirstElement(SyntaxTreeNode element) {
        _contents.insertElementAt(element,0);
        element.setParent(this);
    }

    /**
     * Removed a child node of this syntax tree node.
     * <p>
     *  删除了此语法树节点的子节点。
     * 
     * 
     * @param element is the child node to remove.
     */
    protected final void removeElement(SyntaxTreeNode element) {
        _contents.remove(element);
        element.setParent(null);
    }

    /**
     * Returns a Vector containing all the child nodes of this node.
     * <p>
     *  返回包含此节点的所有子节点的向量。
     * 
     * 
     * @return A Vector containing all the child nodes of this node.
     */
    protected final Vector getContents() {
        return _contents;
    }

    /**
     * Tells you if this node has any child nodes.
     * <p>
     * 告诉你这个节点是否有任何子节点。
     * 
     * 
     * @return 'true' if this node has any children.
     */
    protected final boolean hasContents() {
        return elementCount() > 0;
    }

    /**
     * Returns the number of children this node has.
     * <p>
     *  返回此节点具有的子节点数。
     * 
     * 
     * @return Number of child nodes.
     */
    protected final int elementCount() {
        return _contents.size();
    }

    /**
     * Returns an Enumeration of all child nodes of this node.
     * <p>
     *  返回此节点的所有子节点的枚举。
     * 
     * 
     * @return An Enumeration of all child nodes of this node.
     */
    protected final Enumeration elements() {
        return _contents.elements();
    }

    /**
     * Returns a child node at a given position.
     * <p>
     *  返回给定位置的子节点。
     * 
     * 
     * @param pos The child node's position.
     * @return The child node.
     */
    protected final Object elementAt(int pos) {
        return _contents.elementAt(pos);
    }

    /**
     * Returns this element's last child
     * <p>
     *  返回此元素的最后一个子元素
     * 
     * 
     * @return The child node.
     */
    protected final SyntaxTreeNode lastChild() {
        if (_contents.size() == 0) return null;
        return (SyntaxTreeNode)_contents.lastElement();
    }

    /**
     * Displays the contents of this syntax tree node (to stdout).
     * This method is intended for debugging _only_, and should be overridden
     * by all syntax tree node implementations.
     * <p>
     *  显示此语法树节点(到stdout)的内容。此方法用于调试_only_,并且应该被所有语法树节点实现覆盖。
     * 
     * 
     * @param indent Indentation level for syntax tree levels.
     */
    public void display(int indent) {
        displayContents(indent);
    }

    /**
     * Displays the contents of this syntax tree node (to stdout).
     * This method is intended for debugging _only_ !!!
     * <p>
     *  显示此语法树节点(到stdout)的内容。这个方法是用来调试_only_！
     * 
     * 
     * @param indent Indentation level for syntax tree levels.
     */
    protected void displayContents(int indent) {
        final int n = elementCount();
        for (int i = 0; i < n; i++) {
            SyntaxTreeNode item = (SyntaxTreeNode)_contents.elementAt(i);
            item.display(indent);
        }
    }

    /**
     * Set the indentation level for debug output.
     * <p>
     *  设置调试输出的缩进级别。
     * 
     * 
     * @param indent Indentation level for syntax tree levels.
     */
    protected final void indent(int indent) {
        System.out.print(new String(_spaces, 0, indent));
    }

    /**
     * Report an error to the parser.
     * <p>
     *  向解析器报告错误。
     * 
     * 
     * @param element The element in which the error occured (normally 'this'
     * but it could also be an expression/pattern/etc.)
     * @param parser The XSLT parser to report the error to.
     * @param error The error code (from util/ErrorMsg).
     * @param message Any additional error message.
     */
    protected void reportError(SyntaxTreeNode element, Parser parser,
                               String errorCode, String message) {
        final ErrorMsg error = new ErrorMsg(errorCode, message, element);
        parser.reportError(Constants.ERROR, error);
    }

    /**
     * Report a recoverable error to the parser.
     * <p>
     *  向解析器报告可恢复的错误。
     * 
     * @param element The element in which the error occured (normally 'this'
     * but it could also be an expression/pattern/etc.)
     * @param parser The XSLT parser to report the error to.
     * @param error The error code (from util/ErrorMsg).
     * @param message Any additional error message.
     */
    protected  void reportWarning(SyntaxTreeNode element, Parser parser,
                                  String errorCode, String message) {
        final ErrorMsg error = new ErrorMsg(errorCode, message, element);
        parser.reportError(Constants.WARNING, error);
    }

}
