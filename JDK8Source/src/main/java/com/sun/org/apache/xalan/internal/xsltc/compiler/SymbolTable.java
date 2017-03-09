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
 * $Id: SymbolTable.java,v 1.5 2005/09/28 13:48:16 pvedula Exp $
 * <p>
 *  $ Id：SymbolTable.java,v 1.5 2005/09/28 13:48:16 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import java.util.Hashtable;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodType;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class SymbolTable {

    // These hashtables are used for all stylesheets
    private final Hashtable _stylesheets = new Hashtable();
    private final Hashtable _primops     = new Hashtable();

    // These hashtables are used for some stylesheets
    private Hashtable _variables = null;
    private Hashtable _templates = null;
    private Hashtable _attributeSets = null;
    private Hashtable _aliases = null;
    private Hashtable _excludedURI = null;
    private Stack     _excludedURIStack = null;
    private Hashtable _decimalFormats = null;
    private Hashtable _keys = null;

    public DecimalFormatting getDecimalFormatting(QName name) {
        if (_decimalFormats == null) return null;
        return((DecimalFormatting)_decimalFormats.get(name));
    }

    public void addDecimalFormatting(QName name, DecimalFormatting symbols) {
        if (_decimalFormats == null) _decimalFormats = new Hashtable();
        _decimalFormats.put(name, symbols);
    }

    public Key getKey(QName name) {
        if (_keys == null) return null;
        return (Key) _keys.get(name);
    }

    public void addKey(QName name, Key key) {
        if (_keys == null) _keys = new Hashtable();
        _keys.put(name, key);
    }

    public Stylesheet addStylesheet(QName name, Stylesheet node) {
        return (Stylesheet)_stylesheets.put(name, node);
    }

    public Stylesheet lookupStylesheet(QName name) {
        return (Stylesheet)_stylesheets.get(name);
    }

    public Template addTemplate(Template template) {
        final QName name = template.getName();
        if (_templates == null) _templates = new Hashtable();
        return (Template)_templates.put(name, template);
    }

    public Template lookupTemplate(QName name) {
        if (_templates == null) return null;
        return (Template)_templates.get(name);
    }

    public Variable addVariable(Variable variable) {
        if (_variables == null) _variables = new Hashtable();
        final String name = variable.getName().getStringRep();
        return (Variable)_variables.put(name, variable);
    }

    public Param addParam(Param parameter) {
        if (_variables == null) _variables = new Hashtable();
        final String name = parameter.getName().getStringRep();
        return (Param)_variables.put(name, parameter);
    }

    public Variable lookupVariable(QName qname) {
        if (_variables == null) return null;
        final String name = qname.getStringRep();
        final Object obj = _variables.get(name);
        return obj instanceof Variable ? (Variable)obj : null;
    }

    public Param lookupParam(QName qname) {
        if (_variables == null) return null;
        final String name = qname.getStringRep();
        final Object obj = _variables.get(name);
        return obj instanceof Param ? (Param)obj : null;
    }

    public SyntaxTreeNode lookupName(QName qname) {
        if (_variables == null) return null;
        final String name = qname.getStringRep();
        return (SyntaxTreeNode)_variables.get(name);
    }

    public AttributeSet addAttributeSet(AttributeSet atts) {
        if (_attributeSets == null) _attributeSets = new Hashtable();
        return (AttributeSet)_attributeSets.put(atts.getName(), atts);
    }

    public AttributeSet lookupAttributeSet(QName name) {
        if (_attributeSets == null) return null;
        return (AttributeSet)_attributeSets.get(name);
    }

    /**
     * Add a primitive operator or function to the symbol table. To avoid
     * name clashes with user-defined names, the prefix <tt>PrimopPrefix</tt>
     * is prepended.
     * <p>
     *  向符号表中添加一个基本操作符或函数。要避免与用户定义的名称冲突,请添加前缀<tt> PrimopPrefix </tt>。
     * 
     */
    public void addPrimop(String name, MethodType mtype) {
        Vector methods = (Vector)_primops.get(name);
        if (methods == null) {
            _primops.put(name, methods = new Vector());
        }
        methods.addElement(mtype);
    }

    /**
     * Lookup a primitive operator or function in the symbol table by
     * prepending the prefix <tt>PrimopPrefix</tt>.
     * <p>
     *  通过在前缀<tt> PrimopPrefix </tt>之前查找符号表中的原始运算符或函数。
     * 
     */
    public Vector lookupPrimop(String name) {
        return (Vector)_primops.get(name);
    }

    /**
     * This is used for xsl:attribute elements that have a "namespace"
     * attribute that is currently not defined using xmlns:
     * <p>
     *  这用于具有当前未使用xmlns定义的"namespace"属性的xsl：attribute元素：
     * 
     */
    private int _nsCounter = 0;

    public String generateNamespacePrefix() {
        return("ns"+(_nsCounter++));
    }

    /**
     * Use a namespace prefix to lookup a namespace URI
     * <p>
     *  使用命名空间前缀来查找命名空间URI
     * 
     */
    private SyntaxTreeNode _current = null;

    public void setCurrentNode(SyntaxTreeNode node) {
        _current = node;
    }

    public String lookupNamespace(String prefix) {
        if (_current == null) return(Constants.EMPTYSTRING);
        return(_current.lookupNamespace(prefix));
    }

    /**
     * Adds an alias for a namespace prefix
     * <p>
     *  为命名空间前缀添加别名
     * 
     */
    public void addPrefixAlias(String prefix, String alias) {
        if (_aliases == null) _aliases = new Hashtable();
        _aliases.put(prefix,alias);
    }

    /**
     * Retrieves any alias for a given namespace prefix
     * <p>
     *  检索给定命名空间前缀的任何别名
     * 
     */
    public String lookupPrefixAlias(String prefix) {
        if (_aliases == null) return null;
        return (String)_aliases.get(prefix);
    }

    /**
     * Register a namespace URI so that it will not be declared in the output
     * unless it is actually referenced in the output.
     * <p>
     *  注册命名空间URI,以便它不会在输出中声明,除非它在输出中实际引用。
     * 
     */
    public void excludeURI(String uri) {
        // The null-namespace cannot be excluded
        if (uri == null) return;

        // Create new hashtable of exlcuded URIs if none exists
        if (_excludedURI == null) _excludedURI = new Hashtable();

        // Register the namespace URI
        Integer refcnt = (Integer)_excludedURI.get(uri);
        if (refcnt == null)
            refcnt = new Integer(1);
        else
            refcnt = new Integer(refcnt.intValue() + 1);
        _excludedURI.put(uri,refcnt);
    }

    /**
     * Exclude a series of namespaces given by a list of whitespace
     * separated namespace prefixes.
     * <p>
     * 排除由空格分隔的命名空间前缀列表给出的一系列命名空间。
     * 
     */
    public void excludeNamespaces(String prefixes) {
        if (prefixes != null) {
            StringTokenizer tokens = new StringTokenizer(prefixes);
            while (tokens.hasMoreTokens()) {
                final String prefix = tokens.nextToken();
                final String uri;
                if (prefix.equals("#default"))
                    uri = lookupNamespace(Constants.EMPTYSTRING);
                else
                    uri = lookupNamespace(prefix);
                if (uri != null) excludeURI(uri);
            }
        }
    }

    /**
     * Check if a namespace should not be declared in the output (unless used)
     * <p>
     *  检查是否不应在输出中声明命名空间(除非使用)
     * 
     */
    public boolean isExcludedNamespace(String uri) {
        if (uri != null && _excludedURI != null) {
            final Integer refcnt = (Integer)_excludedURI.get(uri);
            return (refcnt != null && refcnt.intValue() > 0);
        }
        return false;
    }

    /**
     * Turn of namespace declaration exclusion
     * <p>
     *  转义命名空间声明排除
     * 
     */
    public void unExcludeNamespaces(String prefixes) {
        if (_excludedURI == null) return;
        if (prefixes != null) {
            StringTokenizer tokens = new StringTokenizer(prefixes);
            while (tokens.hasMoreTokens()) {
                final String prefix = tokens.nextToken();
                final String uri;
                if (prefix.equals("#default"))
                    uri = lookupNamespace(Constants.EMPTYSTRING);
                else
                    uri = lookupNamespace(prefix);
                Integer refcnt = (Integer)_excludedURI.get(uri);
                if (refcnt != null)
                    _excludedURI.put(uri, new Integer(refcnt.intValue() - 1));
            }
        }
    }
    /**
     * Exclusion of namespaces by a stylesheet does not extend to any stylesheet
     * imported or included by the stylesheet.  Upon entering the context of a
     * new stylesheet, a call to this method is needed to clear the current set
     * of excluded namespaces temporarily.  Every call to this method requires
     * a corresponding call to {@link #popExcludedNamespacesContext()}.
     * <p>
     *  通过样式表排除命名空间不会扩展到样式表导入或包含的任何样式表。在输入新样式表的上下文时,需要调用此方法以临时清除当前排除的名称空间集。
     * 每次调用此方法都需要对{@link #popExcludedNamespacesContext()}进行相应的调用。
     * 
     */
    public void pushExcludedNamespacesContext() {
        if (_excludedURIStack == null) {
            _excludedURIStack = new Stack();
        }
        _excludedURIStack.push(_excludedURI);
        _excludedURI = null;
    }

    /**
     * Exclusion of namespaces by a stylesheet does not extend to any stylesheet
     * imported or included by the stylesheet.  Upon exiting the context of a
     * stylesheet, a call to this method is needed to restore the set of
     * excluded namespaces that was in effect prior to entering the context of
     * the current stylesheet.
     * <p>
     */
    public void popExcludedNamespacesContext() {
        _excludedURI = (Hashtable) _excludedURIStack.pop();
        if (_excludedURIStack.isEmpty()) {
            _excludedURIStack = null;
        }
    }

}
