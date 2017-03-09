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
 * $Id: Parser.java,v 1.2.4.1 2005/09/13 12:14:32 pvedula Exp $
 * <p>
 *  $ Id：Parser.java,v 1.2.4.1 2005/09/13 12:14:32 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.java_cup.internal.runtime.Symbol;
import com.sun.org.apache.xalan.internal.XalanConstants;
import com.sun.org.apache.xalan.internal.utils.FactoryImpl;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.MethodType;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;
import com.sun.org.apache.xml.internal.serializer.utils.SystemIDResolver;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author G. Todd Miller
 * @author Morten Jorgensen
 * @author Erwin Bolwidt <ejb@klomp.org>
 */
public class Parser implements Constants, ContentHandler {

    private static final String XSL = "xsl";            // standard prefix
    private static final String TRANSLET = "translet"; // extension prefix

    private Locator _locator = null;

    private XSLTC _xsltc;             // Reference to the compiler object.
    private XPathParser _xpathParser; // Reference to the XPath parser.
    private Vector _errors;           // Contains all compilation errors
    private Vector _warnings;         // Contains all compilation errors

    private Hashtable   _instructionClasses; // Maps instructions to classes
    private Hashtable   _instructionAttrs;;  // reqd and opt attrs
    private Hashtable   _qNames;
    private Hashtable   _namespaces;
    private QName       _useAttributeSets;
    private QName       _excludeResultPrefixes;
    private QName       _extensionElementPrefixes;
    private Hashtable   _variableScope;
    private Stylesheet  _currentStylesheet;
    private SymbolTable _symbolTable; // Maps QNames to syntax-tree nodes
    private Output      _output;
    private Template    _template;    // Reference to the template being parsed.

    private boolean     _rootNamespaceDef; // Used for validity check

    private SyntaxTreeNode _root;

    private String _target;

    private int _currentImportPrecedence;

    private boolean _useServicesMechanism = true;

    public Parser(XSLTC xsltc, boolean useServicesMechanism) {
        _xsltc = xsltc;
        _useServicesMechanism = useServicesMechanism;
    }

    public void init() {
        _qNames              = new Hashtable(512);
        _namespaces          = new Hashtable();
        _instructionClasses  = new Hashtable();
        _instructionAttrs    = new Hashtable();
        _variableScope       = new Hashtable();
        _template            = null;
        _errors              = new Vector();
        _warnings            = new Vector();
        _symbolTable         = new SymbolTable();
        _xpathParser         = new XPathParser(this);
        _currentStylesheet   = null;
        _output              = null;
        _root                = null;
        _rootNamespaceDef    = false;
        _currentImportPrecedence = 1;

        initStdClasses();
        initInstructionAttrs();
        initExtClasses();
        initSymbolTable();

        _useAttributeSets =
            getQName(XSLT_URI, XSL, "use-attribute-sets");
        _excludeResultPrefixes =
            getQName(XSLT_URI, XSL, "exclude-result-prefixes");
        _extensionElementPrefixes =
            getQName(XSLT_URI, XSL, "extension-element-prefixes");
    }

    public void setOutput(Output output) {
        if (_output != null) {
            if (_output.getImportPrecedence() <= output.getImportPrecedence()) {
                String cdata = _output.getCdata();
                output.mergeOutput(_output);
                _output.disable();
                _output = output;
            }
            else {
                output.disable();
            }
        }
        else {
            _output = output;
        }
    }

    public Output getOutput() {
        return _output;
    }

    public Properties getOutputProperties() {
        return getTopLevelStylesheet().getOutputProperties();
    }

    public void addVariable(Variable var) {
        addVariableOrParam(var);
    }

    public void addParameter(Param param) {
        addVariableOrParam(param);
    }

    private void addVariableOrParam(VariableBase var) {
        Object existing = _variableScope.get(var.getName());
        if (existing != null) {
            if (existing instanceof Stack) {
                Stack stack = (Stack)existing;
                stack.push(var);
            }
            else if (existing instanceof VariableBase) {
                Stack stack = new Stack();
                stack.push(existing);
                stack.push(var);
                _variableScope.put(var.getName(), stack);
            }
        }
        else {
            _variableScope.put(var.getName(), var);
        }
    }

    public void removeVariable(QName name) {
        Object existing = _variableScope.get(name);
        if (existing instanceof Stack) {
            Stack stack = (Stack)existing;
            if (!stack.isEmpty()) stack.pop();
            if (!stack.isEmpty()) return;
        }
        _variableScope.remove(name);
    }

    public VariableBase lookupVariable(QName name) {
        Object existing = _variableScope.get(name);
        if (existing instanceof VariableBase) {
            return((VariableBase)existing);
        }
        else if (existing instanceof Stack) {
            Stack stack = (Stack)existing;
            return((VariableBase)stack.peek());
        }
        return(null);
    }

    public void setXSLTC(XSLTC xsltc) {
        _xsltc = xsltc;
    }

    public XSLTC getXSLTC() {
        return _xsltc;
    }

    public int getCurrentImportPrecedence() {
        return _currentImportPrecedence;
    }

    public int getNextImportPrecedence() {
        return ++_currentImportPrecedence;
    }

    public void setCurrentStylesheet(Stylesheet stylesheet) {
        _currentStylesheet = stylesheet;
    }

    public Stylesheet getCurrentStylesheet() {
        return _currentStylesheet;
    }

    public Stylesheet getTopLevelStylesheet() {
        return _xsltc.getStylesheet();
    }

    public QName getQNameSafe(final String stringRep) {
        // parse and retrieve namespace
        final int colon = stringRep.lastIndexOf(':');
        if (colon != -1) {
            final String prefix = stringRep.substring(0, colon);
            final String localname = stringRep.substring(colon + 1);
            String namespace = null;

            // Get the namespace uri from the symbol table
            if (prefix.equals(XMLNS_PREFIX) == false) {
                namespace = _symbolTable.lookupNamespace(prefix);
                if (namespace == null) namespace = EMPTYSTRING;
            }
            return getQName(namespace, prefix, localname);
        }
        else {
            final String uri = stringRep.equals(XMLNS_PREFIX) ? null
                : _symbolTable.lookupNamespace(EMPTYSTRING);
            return getQName(uri, null, stringRep);
        }
    }

    public QName getQName(final String stringRep) {
        return getQName(stringRep, true, false);
    }

    public QName getQNameIgnoreDefaultNs(final String stringRep) {
        return getQName(stringRep, true, true);
    }

    public QName getQName(final String stringRep, boolean reportError) {
        return getQName(stringRep, reportError, false);
    }

    private QName getQName(final String stringRep, boolean reportError,
        boolean ignoreDefaultNs)
    {
        // parse and retrieve namespace
        final int colon = stringRep.lastIndexOf(':');
        if (colon != -1) {
            final String prefix = stringRep.substring(0, colon);
            final String localname = stringRep.substring(colon + 1);
            String namespace = null;

            // Get the namespace uri from the symbol table
            if (prefix.equals(XMLNS_PREFIX) == false) {
                namespace = _symbolTable.lookupNamespace(prefix);
                if (namespace == null && reportError) {
                    final int line = getLineNumber();
                    ErrorMsg err = new ErrorMsg(ErrorMsg.NAMESPACE_UNDEF_ERR,
                                                line, prefix);
                    reportError(ERROR, err);
                }
            }
            return getQName(namespace, prefix, localname);
        }
        else {
            if (stringRep.equals(XMLNS_PREFIX)) {
                ignoreDefaultNs = true;
            }
            final String defURI = ignoreDefaultNs ? null
                                  : _symbolTable.lookupNamespace(EMPTYSTRING);
            return getQName(defURI, null, stringRep);
        }
    }

    public QName getQName(String namespace, String prefix, String localname) {
        if (namespace == null || namespace.equals(EMPTYSTRING)) {
            QName name = (QName)_qNames.get(localname);
            if (name == null) {
                name = new QName(null, prefix, localname);
                _qNames.put(localname, name);
            }
            return name;
        }
        else {
            Dictionary space = (Dictionary)_namespaces.get(namespace);
            String lexicalQName =
                       (prefix == null || prefix.length() == 0)
                            ? localname
                            : (prefix + ':' + localname);

            if (space == null) {
                final QName name = new QName(namespace, prefix, localname);
                _namespaces.put(namespace, space = new Hashtable());
                space.put(lexicalQName, name);
                return name;
            }
            else {
                QName name = (QName)space.get(lexicalQName);
                if (name == null) {
                    name = new QName(namespace, prefix, localname);
                    space.put(lexicalQName, name);
                }
                return name;
            }
        }
    }

    public QName getQName(String scope, String name) {
        return getQName(scope + name);
    }

    public QName getQName(QName scope, QName name) {
        return getQName(scope.toString() + name.toString());
    }

    public QName getUseAttributeSets() {
        return _useAttributeSets;
    }

    public QName getExtensionElementPrefixes() {
        return _extensionElementPrefixes;
    }

    public QName getExcludeResultPrefixes() {
        return _excludeResultPrefixes;
    }

    /**
     * Create an instance of the <code>Stylesheet</code> class,
     * and then parse, typecheck and compile the instance.
     * Must be called after <code>parse()</code>.
     * <p>
     *  创建<代码>的实例样式表</code>类,然后解析,类型检测,并编译实例。必须在<code> parse()</code>之后调用。
     * 
     */
    public Stylesheet makeStylesheet(SyntaxTreeNode element)
        throws CompilerException {
        try {
            Stylesheet stylesheet;

            if (element instanceof Stylesheet) {
                stylesheet = (Stylesheet)element;
            }
            else {
                stylesheet = new Stylesheet();
                stylesheet.setSimplified();
                stylesheet.addElement(element);
                stylesheet.setAttributes((AttributesImpl) element.getAttributes());

                // Map the default NS if not already defined
                if (element.lookupNamespace(EMPTYSTRING) == null) {
                    element.addPrefixMapping(EMPTYSTRING, EMPTYSTRING);
                }
            }
            stylesheet.setParser(this);
            return stylesheet;
        }
        catch (ClassCastException e) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.NOT_STYLESHEET_ERR, element);
            throw new CompilerException(err.toString());
        }
    }

    /**
     * Instanciates a SAX2 parser and generate the AST from the input.
     * <p>
     *  创建一个SAX2解析器并从输入生成AST。
     * 
     */
    public void createAST(Stylesheet stylesheet) {
        try {
            if (stylesheet != null) {
                stylesheet.parseContents(this);
                final int precedence = stylesheet.getImportPrecedence();
                final Enumeration elements = stylesheet.elements();
                while (elements.hasMoreElements()) {
                    Object child = elements.nextElement();
                    if (child instanceof Text) {
                        final int l = getLineNumber();
                        ErrorMsg err =
                            new ErrorMsg(ErrorMsg.ILLEGAL_TEXT_NODE_ERR,l,null);
                        reportError(ERROR, err);
                    }
                }
                if (!errorsFound()) {
                    stylesheet.typeCheck(_symbolTable);
                }
            }
        }
        catch (TypeCheckError e) {
            reportError(ERROR, new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
        }
    }

    /**
     * Parses a stylesheet and builds the internal abstract syntax tree
     * <p>
     *  解析样式表并构建内部抽象语法树
     * 
     * 
     * @param reader A SAX2 SAXReader (parser)
     * @param input A SAX2 InputSource can be passed to a SAX reader
     * @return The root of the abstract syntax tree
     */
    public SyntaxTreeNode parse(XMLReader reader, InputSource input) {
        try {
            // Parse the input document and build the abstract syntax tree
            reader.setContentHandler(this);
            reader.parse(input);
            // Find the start of the stylesheet within the tree
            return (SyntaxTreeNode)getStylesheet(_root);
        }
        catch (IOException e) {
            if (_xsltc.debug()) e.printStackTrace();
            reportError(ERROR,new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
        }
        catch (SAXException e) {
            Throwable ex = e.getException();
            if (_xsltc.debug()) {
                e.printStackTrace();
                if (ex != null) ex.printStackTrace();
            }
            reportError(ERROR, new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
        }
        catch (CompilerException e) {
            if (_xsltc.debug()) e.printStackTrace();
            reportError(ERROR, new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
        }
        catch (Exception e) {
            if (_xsltc.debug()) e.printStackTrace();
            reportError(ERROR, new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
        }
        return null;
    }

    /**
     * Parses a stylesheet and builds the internal abstract syntax tree
     * <p>
     *  解析样式表并构建内部抽象语法树
     * 
     * 
     * @param input A SAX2 InputSource can be passed to a SAX reader
     * @return The root of the abstract syntax tree
     */
    public SyntaxTreeNode parse(InputSource input) {
        try {
            // Create a SAX parser and get the XMLReader object it uses
            final SAXParserFactory factory = FactoryImpl.getSAXFactory(_useServicesMechanism);

            if (_xsltc.isSecureProcessing()) {
                try {
                    factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                }
                catch (SAXException e) {}
            }

            try {
                factory.setFeature(Constants.NAMESPACE_FEATURE,true);
            }
            catch (Exception e) {
                factory.setNamespaceAware(true);
            }
            final SAXParser parser = factory.newSAXParser();
            try {
                parser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD,
                        _xsltc.getProperty(XMLConstants.ACCESS_EXTERNAL_DTD));
            } catch (SAXNotRecognizedException e) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.WARNING_MSG,
                        parser.getClass().getName() + ": " + e.getMessage());
                reportError(WARNING, err);
            }

            final XMLReader reader = parser.getXMLReader();
            try {
                XMLSecurityManager securityManager =
                        (XMLSecurityManager)_xsltc.getProperty(XalanConstants.SECURITY_MANAGER);
                for (XMLSecurityManager.Limit limit : XMLSecurityManager.Limit.values()) {
                    reader.setProperty(limit.apiProperty(), securityManager.getLimitValueAsString(limit));
                }
                if (securityManager.printEntityCountInfo()) {
                    parser.setProperty(XalanConstants.JDK_ENTITY_COUNT_INFO, XalanConstants.JDK_YES);
                }
            } catch (SAXException se) {
                System.err.println("Warning:  " + reader.getClass().getName() + ": "
                            + se.getMessage());
            }

            return(parse(reader, input));
        }
        catch (ParserConfigurationException e) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.SAX_PARSER_CONFIG_ERR);
            reportError(ERROR, err);
        }
        catch (SAXParseException e){
            reportError(ERROR, new ErrorMsg(e.getMessage(),e.getLineNumber()));
        }
        catch (SAXException e) {
            reportError(ERROR, new ErrorMsg(e.getMessage()));
        }
        return null;
    }

    public SyntaxTreeNode getDocumentRoot() {
        return _root;
    }

    private String _PImedia = null;
    private String _PItitle = null;
    private String _PIcharset = null;

    /**
     * Set the parameters to use to locate the correct <?xml-stylesheet ...?>
     * processing instruction in the case where the input document is an
     * XML document with one or more references to a stylesheet.
     * <p>
     *  设置参数来使用以定位正确<?xml样式表...?>在输入文件是与一个或多个引用到一个样式表将XML文档的情况下的处理的指令。
     * 
     * 
     * @param media The media attribute to be matched. May be null, in which
     * case the prefered templates will be used (i.e. alternate = no).
     * @param title The value of the title attribute to match. May be null.
     * @param charset The value of the charset attribute to match. May be null.
     */
    protected void setPIParameters(String media, String title, String charset) {
        _PImedia = media;
        _PItitle = title;
        _PIcharset = charset;
    }

    /**
     * Extracts the DOM for the stylesheet. In the case of an embedded
     * stylesheet, it extracts the DOM subtree corresponding to the
     * embedded stylesheet that has an 'id' attribute whose value is the
     * same as the value declared in the <?xml-stylesheet...?> processing
     * instruction (P.I.). In the xml-stylesheet P.I. the value is labeled
     * as the 'href' data of the P.I. The extracted DOM representing the
     * stylesheet is returned as an Element object.
     * <p>
     * 提取样式表的DOM。在嵌入样式表的情况下,它提取对应于具有'id'属性的嵌入样式表的DOM子树,该属性的值与在<?xml-stylesheet ...?>处理指令)。
     * 在xml样式表该值被标记为P.I.的"href"数据。提取的表示样式表的DOM作为Element对象返回。
     * 
     */
    private SyntaxTreeNode getStylesheet(SyntaxTreeNode root)
        throws CompilerException {

        // Assume that this is a pure XSL stylesheet if there is not
        // <?xml-stylesheet ....?> processing instruction
        if (_target == null) {
            if (!_rootNamespaceDef) {
                ErrorMsg msg = new ErrorMsg(ErrorMsg.MISSING_XSLT_URI_ERR);
                throw new CompilerException(msg.toString());
            }
            return(root);
        }

        // Find the xsl:stylesheet or xsl:transform with this reference
        if (_target.charAt(0) == '#') {
            SyntaxTreeNode element = findStylesheet(root, _target.substring(1));
            if (element == null) {
                ErrorMsg msg = new ErrorMsg(ErrorMsg.MISSING_XSLT_TARGET_ERR,
                                            _target, root);
                throw new CompilerException(msg.toString());
            }
            return(element);
        }
        else {
            try {
                String path = _target;
                if (path.indexOf(":")==-1) {
                    path = "file:" + path;
                }
                path = SystemIDResolver.getAbsoluteURI(path);
                String accessError = SecuritySupport.checkAccess(path,
                        (String)_xsltc.getProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET),
                        XalanConstants.ACCESS_EXTERNAL_ALL);
                if (accessError != null) {
                    ErrorMsg msg = new ErrorMsg(ErrorMsg.ACCESSING_XSLT_TARGET_ERR,
                            SecuritySupport.sanitizePath(_target), accessError,
                            root);
                    throw new CompilerException(msg.toString());
                }
            } catch (IOException ex) {
                throw new CompilerException(ex);
            }

            return(loadExternalStylesheet(_target));
        }
    }

    /**
     * Find a Stylesheet element with a specific ID attribute value.
     * This method is used to find a Stylesheet node that is referred
     * in a <?xml-stylesheet ... ?> processing instruction.
     * <p>
     *  查找具有特定ID属性值的样式表元素。此方法用于查找在<?xml-stylesheet ...?>处理指令中引用的样式表节点。
     * 
     */
    private SyntaxTreeNode findStylesheet(SyntaxTreeNode root, String href) {

        if (root == null) return null;

        if (root instanceof Stylesheet) {
            String id = root.getAttribute("id");
            if (id.equals(href)) return root;
        }
        Vector children = root.getContents();
        if (children != null) {
            final int count = children.size();
            for (int i = 0; i < count; i++) {
                SyntaxTreeNode child = (SyntaxTreeNode)children.elementAt(i);
                SyntaxTreeNode node = findStylesheet(child, href);
                if (node != null) return node;
            }
        }
        return null;
    }

    /**
     * For embedded stylesheets: Load an external file with stylesheet
     * <p>
     *  对于嵌入式样式表：使用样式表加载外部文件
     * 
     */
    private SyntaxTreeNode loadExternalStylesheet(String location)
        throws CompilerException {

        InputSource source;

        // Check if the location is URL or a local file
        if ((new File(location)).exists())
            source = new InputSource("file:"+location);
        else
            source = new InputSource(location);

        SyntaxTreeNode external = (SyntaxTreeNode)parse(source);
        return(external);
    }

    private void initAttrTable(String elementName, String[] attrs) {
        _instructionAttrs.put(getQName(XSLT_URI, XSL, elementName),
                                attrs);
    }

    private void initInstructionAttrs() {
        initAttrTable("template",
            new String[] {"match", "name", "priority", "mode"});
        initAttrTable("stylesheet",
            new String[] {"id", "version", "extension-element-prefixes",
                "exclude-result-prefixes"});
        initAttrTable("transform",
            new String[] {"id", "version", "extension-element-prefixes",
                "exclude-result-prefixes"});
        initAttrTable("text", new String[] {"disable-output-escaping"});
        initAttrTable("if", new String[] {"test"});
        initAttrTable("choose", new String[] {});
        initAttrTable("when", new String[] {"test"});
        initAttrTable("otherwise", new String[] {});
        initAttrTable("for-each", new String[] {"select"});
        initAttrTable("message", new String[] {"terminate"});
        initAttrTable("number",
            new String[] {"level", "count", "from", "value", "format", "lang",
                "letter-value", "grouping-separator", "grouping-size"});
                initAttrTable("comment", new String[] {});
        initAttrTable("copy", new String[] {"use-attribute-sets"});
        initAttrTable("copy-of", new String[] {"select"});
        initAttrTable("param", new String[] {"name", "select"});
        initAttrTable("with-param", new String[] {"name", "select"});
        initAttrTable("variable", new String[] {"name", "select"});
        initAttrTable("output",
            new String[] {"method", "version", "encoding",
                "omit-xml-declaration", "standalone", "doctype-public",
                "doctype-system", "cdata-section-elements", "indent",
                "media-type"});
        initAttrTable("sort",
           new String[] {"select", "order", "case-order", "lang", "data-type"});
        initAttrTable("key", new String[] {"name", "match", "use"});
        initAttrTable("fallback", new String[] {});
        initAttrTable("attribute", new String[] {"name", "namespace"});
        initAttrTable("attribute-set",
            new String[] {"name", "use-attribute-sets"});
        initAttrTable("value-of",
            new String[] {"select", "disable-output-escaping"});
        initAttrTable("element",
            new String[] {"name", "namespace", "use-attribute-sets"});
        initAttrTable("call-template", new String[] {"name"});
        initAttrTable("apply-templates", new String[] {"select", "mode"});
        initAttrTable("apply-imports", new String[] {});
        initAttrTable("decimal-format",
            new String[] {"name", "decimal-separator", "grouping-separator",
                "infinity", "minus-sign", "NaN", "percent", "per-mille",
                "zero-digit", "digit", "pattern-separator"});
        initAttrTable("import", new String[] {"href"});
        initAttrTable("include", new String[] {"href"});
        initAttrTable("strip-space", new String[] {"elements"});
        initAttrTable("preserve-space", new String[] {"elements"});
        initAttrTable("processing-instruction", new String[] {"name"});
        initAttrTable("namespace-alias",
           new String[] {"stylesheet-prefix", "result-prefix"});
    }



    /**
     * Initialize the _instructionClasses Hashtable, which maps XSL element
     * names to Java classes in this package.
     * <p>
     *  初始化_instructionClasses Hashtable,它将XSL元素名称映射到此包中的Java类。
     * 
     */
    private void initStdClasses() {
        initStdClass("template", "Template");
        initStdClass("stylesheet", "Stylesheet");
        initStdClass("transform", "Stylesheet");
        initStdClass("text", "Text");
        initStdClass("if", "If");
        initStdClass("choose", "Choose");
        initStdClass("when", "When");
        initStdClass("otherwise", "Otherwise");
        initStdClass("for-each", "ForEach");
        initStdClass("message", "Message");
        initStdClass("number", "Number");
        initStdClass("comment", "Comment");
        initStdClass("copy", "Copy");
        initStdClass("copy-of", "CopyOf");
        initStdClass("param", "Param");
        initStdClass("with-param", "WithParam");
        initStdClass("variable", "Variable");
        initStdClass("output", "Output");
        initStdClass("sort", "Sort");
        initStdClass("key", "Key");
        initStdClass("fallback", "Fallback");
        initStdClass("attribute", "XslAttribute");
        initStdClass("attribute-set", "AttributeSet");
        initStdClass("value-of", "ValueOf");
        initStdClass("element", "XslElement");
        initStdClass("call-template", "CallTemplate");
        initStdClass("apply-templates", "ApplyTemplates");
        initStdClass("apply-imports", "ApplyImports");
        initStdClass("decimal-format", "DecimalFormatting");
        initStdClass("import", "Import");
        initStdClass("include", "Include");
        initStdClass("strip-space", "Whitespace");
        initStdClass("preserve-space", "Whitespace");
        initStdClass("processing-instruction", "ProcessingInstruction");
        initStdClass("namespace-alias", "NamespaceAlias");
    }

    private void initStdClass(String elementName, String className) {
        _instructionClasses.put(getQName(XSLT_URI, XSL, elementName),
                                COMPILER_PACKAGE + '.' + className);
    }

    public boolean elementSupported(String namespace, String localName) {
        return(_instructionClasses.get(getQName(namespace, XSL, localName)) != null);
    }

    public boolean functionSupported(String fname) {
        return(_symbolTable.lookupPrimop(fname) != null);
    }

    private void initExtClasses() {
        initExtClass("output", "TransletOutput");
        initExtClass(REDIRECT_URI, "write", "TransletOutput");
    }

    private void initExtClass(String elementName, String className) {
        _instructionClasses.put(getQName(TRANSLET_URI, TRANSLET, elementName),
                                COMPILER_PACKAGE + '.' + className);
    }

    private void initExtClass(String namespace, String elementName, String className) {
        _instructionClasses.put(getQName(namespace, TRANSLET, elementName),
                                COMPILER_PACKAGE + '.' + className);
    }

    /**
     * Add primops and base functions to the symbol table.
     * <p>
     *  将primops和基函数添加到符号表。
     * 
     */
    private void initSymbolTable() {
        MethodType I_V  = new MethodType(Type.Int, Type.Void);
        MethodType I_R  = new MethodType(Type.Int, Type.Real);
        MethodType I_S  = new MethodType(Type.Int, Type.String);
        MethodType I_D  = new MethodType(Type.Int, Type.NodeSet);
        MethodType R_I  = new MethodType(Type.Real, Type.Int);
        MethodType R_V  = new MethodType(Type.Real, Type.Void);
        MethodType R_R  = new MethodType(Type.Real, Type.Real);
        MethodType R_D  = new MethodType(Type.Real, Type.NodeSet);
        MethodType R_O  = new MethodType(Type.Real, Type.Reference);
        MethodType I_I  = new MethodType(Type.Int, Type.Int);
        MethodType D_O  = new MethodType(Type.NodeSet, Type.Reference);
        MethodType D_V  = new MethodType(Type.NodeSet, Type.Void);
        MethodType D_S  = new MethodType(Type.NodeSet, Type.String);
        MethodType D_D  = new MethodType(Type.NodeSet, Type.NodeSet);
        MethodType A_V  = new MethodType(Type.Node, Type.Void);
        MethodType S_V  = new MethodType(Type.String, Type.Void);
        MethodType S_S  = new MethodType(Type.String, Type.String);
        MethodType S_A  = new MethodType(Type.String, Type.Node);
        MethodType S_D  = new MethodType(Type.String, Type.NodeSet);
        MethodType S_O  = new MethodType(Type.String, Type.Reference);
        MethodType B_O  = new MethodType(Type.Boolean, Type.Reference);
        MethodType B_V  = new MethodType(Type.Boolean, Type.Void);
        MethodType B_B  = new MethodType(Type.Boolean, Type.Boolean);
        MethodType B_S  = new MethodType(Type.Boolean, Type.String);
        MethodType D_X  = new MethodType(Type.NodeSet, Type.Object);
        MethodType R_RR = new MethodType(Type.Real, Type.Real, Type.Real);
        MethodType I_II = new MethodType(Type.Int, Type.Int, Type.Int);
        MethodType B_RR = new MethodType(Type.Boolean, Type.Real, Type.Real);
        MethodType B_II = new MethodType(Type.Boolean, Type.Int, Type.Int);
        MethodType S_SS = new MethodType(Type.String, Type.String, Type.String);
        MethodType S_DS = new MethodType(Type.String, Type.Real, Type.String);
        MethodType S_SR = new MethodType(Type.String, Type.String, Type.Real);
        MethodType O_SO = new MethodType(Type.Reference, Type.String, Type.Reference);

        MethodType D_SS =
            new MethodType(Type.NodeSet, Type.String, Type.String);
        MethodType D_SD =
            new MethodType(Type.NodeSet, Type.String, Type.NodeSet);
        MethodType B_BB =
            new MethodType(Type.Boolean, Type.Boolean, Type.Boolean);
        MethodType B_SS =
            new MethodType(Type.Boolean, Type.String, Type.String);
        MethodType S_SD =
            new MethodType(Type.String, Type.String, Type.NodeSet);
        MethodType S_DSS =
            new MethodType(Type.String, Type.Real, Type.String, Type.String);
        MethodType S_SRR =
            new MethodType(Type.String, Type.String, Type.Real, Type.Real);
        MethodType S_SSS =
            new MethodType(Type.String, Type.String, Type.String, Type.String);

        /*
         * Standard functions: implemented but not in this table concat().
         * When adding a new function make sure to uncomment
         * the corresponding line in <tt>FunctionAvailableCall</tt>.
         * <p>
         *  标准函数：实现但不在此表concat()。添加新函数时,请务必取消注释<tt> FunctionAvailableCall </tt>中的相应行。
         * 
         */

        // The following functions are inlined

        _symbolTable.addPrimop("current", A_V);
        _symbolTable.addPrimop("last", I_V);
        _symbolTable.addPrimop("position", I_V);
        _symbolTable.addPrimop("true", B_V);
        _symbolTable.addPrimop("false", B_V);
        _symbolTable.addPrimop("not", B_B);
        _symbolTable.addPrimop("name", S_V);
        _symbolTable.addPrimop("name", S_A);
        _symbolTable.addPrimop("generate-id", S_V);
        _symbolTable.addPrimop("generate-id", S_A);
        _symbolTable.addPrimop("ceiling", R_R);
        _symbolTable.addPrimop("floor", R_R);
        _symbolTable.addPrimop("round", R_R);
        _symbolTable.addPrimop("contains", B_SS);
        _symbolTable.addPrimop("number", R_O);
        _symbolTable.addPrimop("number", R_V);
        _symbolTable.addPrimop("boolean", B_O);
        _symbolTable.addPrimop("string", S_O);
        _symbolTable.addPrimop("string", S_V);
        _symbolTable.addPrimop("translate", S_SSS);
        _symbolTable.addPrimop("string-length", I_V);
        _symbolTable.addPrimop("string-length", I_S);
        _symbolTable.addPrimop("starts-with", B_SS);
        _symbolTable.addPrimop("format-number", S_DS);
        _symbolTable.addPrimop("format-number", S_DSS);
        _symbolTable.addPrimop("unparsed-entity-uri", S_S);
        _symbolTable.addPrimop("key", D_SS);
        _symbolTable.addPrimop("key", D_SD);
        _symbolTable.addPrimop("id", D_S);
        _symbolTable.addPrimop("id", D_D);
        _symbolTable.addPrimop("namespace-uri", S_V);
        _symbolTable.addPrimop("function-available", B_S);
        _symbolTable.addPrimop("element-available", B_S);
        _symbolTable.addPrimop("document", D_S);
        _symbolTable.addPrimop("document", D_V);

        // The following functions are implemented in the basis library
        _symbolTable.addPrimop("count", I_D);
        _symbolTable.addPrimop("sum", R_D);
        _symbolTable.addPrimop("local-name", S_V);
        _symbolTable.addPrimop("local-name", S_D);
        _symbolTable.addPrimop("namespace-uri", S_V);
        _symbolTable.addPrimop("namespace-uri", S_D);
        _symbolTable.addPrimop("substring", S_SR);
        _symbolTable.addPrimop("substring", S_SRR);
        _symbolTable.addPrimop("substring-after", S_SS);
        _symbolTable.addPrimop("substring-before", S_SS);
        _symbolTable.addPrimop("normalize-space", S_V);
        _symbolTable.addPrimop("normalize-space", S_S);
        _symbolTable.addPrimop("system-property", S_S);

        // Extensions
        _symbolTable.addPrimop("nodeset", D_O);
        _symbolTable.addPrimop("objectType", S_O);
        _symbolTable.addPrimop("cast", O_SO);

        // Operators +, -, *, /, % defined on real types.
        _symbolTable.addPrimop("+", R_RR);
        _symbolTable.addPrimop("-", R_RR);
        _symbolTable.addPrimop("*", R_RR);
        _symbolTable.addPrimop("/", R_RR);
        _symbolTable.addPrimop("%", R_RR);

        // Operators +, -, * defined on integer types.
        // Operators / and % are not  defined on integers (may cause exception)
        _symbolTable.addPrimop("+", I_II);
        _symbolTable.addPrimop("-", I_II);
        _symbolTable.addPrimop("*", I_II);

         // Operators <, <= >, >= defined on real types.
        _symbolTable.addPrimop("<",  B_RR);
        _symbolTable.addPrimop("<=", B_RR);
        _symbolTable.addPrimop(">",  B_RR);
        _symbolTable.addPrimop(">=", B_RR);

        // Operators <, <= >, >= defined on int types.
        _symbolTable.addPrimop("<",  B_II);
        _symbolTable.addPrimop("<=", B_II);
        _symbolTable.addPrimop(">",  B_II);
        _symbolTable.addPrimop(">=", B_II);

        // Operators <, <= >, >= defined on boolean types.
        _symbolTable.addPrimop("<",  B_BB);
        _symbolTable.addPrimop("<=", B_BB);
        _symbolTable.addPrimop(">",  B_BB);
        _symbolTable.addPrimop(">=", B_BB);

        // Operators 'and' and 'or'.
        _symbolTable.addPrimop("or", B_BB);
        _symbolTable.addPrimop("and", B_BB);

        // Unary minus.
        _symbolTable.addPrimop("u-", R_R);
        _symbolTable.addPrimop("u-", I_I);
    }

    public SymbolTable getSymbolTable() {
        return _symbolTable;
    }

    public Template getTemplate() {
        return _template;
    }

    public void setTemplate(Template template) {
        _template = template;
    }

    private int _templateIndex = 0;

    public int getTemplateIndex() {
        return(_templateIndex++);
    }

    /**
     * Creates a new node in the abstract syntax tree. This node can be
     *  o) a supported XSLT 1.0 element
     *  o) an unsupported XSLT element (post 1.0)
     *  o) a supported XSLT extension
     *  o) an unsupported XSLT extension
     *  o) a literal result element (not an XSLT element and not an extension)
     * Unsupported elements do not directly generate an error. We have to wait
     * until we have received all child elements of an unsupported element to
     * see if any <xsl:fallback> elements exist.
     * <p>
     *  在抽象语法树中创建一个新节点。
     * 此节点可以是o)支持的XSLT 1.0元素o)不支持的XSLT元素(post 1.0)o)支持的XSLT扩展o)不支持的XSLT扩展o)文字结果元素(不是XSLT元素而不是扩展)不支持元素不会直接生成错
     * 误。
     *  在抽象语法树中创建一个新节点。我们必须等待,直到我们收到一个不支持的元素的所有子元素,以查看是否存在任何<xsl：fallback>元素。
     * 
     */

    private boolean versionIsOne = true;

    public SyntaxTreeNode makeInstance(String uri, String prefix,
        String local, Attributes attributes)
    {
        SyntaxTreeNode node = null;
        QName  qname = getQName(uri, prefix, local);
        String className = (String)_instructionClasses.get(qname);

        if (className != null) {
            try {
                final Class clazz = ObjectFactory.findProviderClass(className, true);
                node = (SyntaxTreeNode)clazz.newInstance();
                node.setQName(qname);
                node.setParser(this);
                if (_locator != null) {
                    node.setLineNumber(getLineNumber());
                }
                if (node instanceof Stylesheet) {
                    _xsltc.setStylesheet((Stylesheet)node);
                }
                checkForSuperfluousAttributes(node, attributes);
            }
            catch (ClassNotFoundException e) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.CLASS_NOT_FOUND_ERR, node);
                reportError(ERROR, err);
            }
            catch (Exception e) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.INTERNAL_ERR,
                                            e.getMessage(), node);
                reportError(FATAL, err);
            }
        }
        else {
            if (uri != null) {
                // Check if the element belongs in our namespace
                if (uri.equals(XSLT_URI)) {
                    node = new UnsupportedElement(uri, prefix, local, false);
                    UnsupportedElement element = (UnsupportedElement)node;
                    ErrorMsg msg = new ErrorMsg(ErrorMsg.UNSUPPORTED_XSL_ERR,
                                                getLineNumber(),local);
                    element.setErrorMessage(msg);
                    if (versionIsOne) {
                        reportError(UNSUPPORTED,msg);
                    }
                }
                // Check if this is an XSLTC extension element
                else if (uri.equals(TRANSLET_URI)) {
                    node = new UnsupportedElement(uri, prefix, local, true);
                    UnsupportedElement element = (UnsupportedElement)node;
                    ErrorMsg msg = new ErrorMsg(ErrorMsg.UNSUPPORTED_EXT_ERR,
                                                getLineNumber(),local);
                    element.setErrorMessage(msg);
                }
                // Check if this is an extension of some other XSLT processor
                else {
                    Stylesheet sheet = _xsltc.getStylesheet();
                    if ((sheet != null) && (sheet.isExtension(uri))) {
                        if (sheet != (SyntaxTreeNode)_parentStack.peek()) {
                            node = new UnsupportedElement(uri, prefix, local, true);
                            UnsupportedElement elem = (UnsupportedElement)node;
                            ErrorMsg msg =
                                new ErrorMsg(ErrorMsg.UNSUPPORTED_EXT_ERR,
                                             getLineNumber(),
                                             prefix+":"+local);
                            elem.setErrorMessage(msg);
                        }
                    }
                }
            }
            if (node == null) {
                node = new LiteralElement();
                node.setLineNumber(getLineNumber());
            }
        }
        if ((node != null) && (node instanceof LiteralElement)) {
            ((LiteralElement)node).setQName(qname);
        }
        return(node);
    }

    /**
     * checks the list of attributes against a list of allowed attributes
     * for a particular element node.
     * <p>
     * 根据特定元素节点的允许属性列表检查属性列表。
     * 
     */
    private void checkForSuperfluousAttributes(SyntaxTreeNode node,
        Attributes attrs)
    {
        QName qname = node.getQName();
        boolean isStylesheet = (node instanceof Stylesheet);
        String[] legal = (String[]) _instructionAttrs.get(qname);
        if (versionIsOne && legal != null) {
            int j;
            final int n = attrs.getLength();

            for (int i = 0; i < n; i++) {
                final String attrQName = attrs.getQName(i);

                if (isStylesheet && attrQName.equals("version")) {
                    versionIsOne = attrs.getValue(i).equals("1.0");
                }

                // Ignore if special or if it has a prefix
                if (attrQName.startsWith("xml") ||
                    attrQName.indexOf(':') > 0) continue;

                for (j = 0; j < legal.length; j++) {
                    if (attrQName.equalsIgnoreCase(legal[j])) {
                        break;
                    }
                }
                if (j == legal.length) {
                    final ErrorMsg err =
                        new ErrorMsg(ErrorMsg.ILLEGAL_ATTRIBUTE_ERR,
                                attrQName, node);
                    // Workaround for the TCK failure ErrorListener.errorTests.error001..
                    err.setWarningError(true);
                    reportError(WARNING, err);
                }
            }
        }
    }


    /**
     * Parse an XPath expression:
     * <p>
     *  解析XPath表达式：
     * 
     * 
     *  @param parent - XSL element where the expression occured
     *  @param exp    - textual representation of the expression
     */
    public Expression parseExpression(SyntaxTreeNode parent, String exp) {
        return (Expression)parseTopLevel(parent, "<EXPRESSION>"+exp, null);
    }

    /**
     * Parse an XPath expression:
     * <p>
     *  解析XPath表达式：
     * 
     * 
     *  @param parent - XSL element where the expression occured
     *  @param attr   - name of this element's attribute to get expression from
     *  @param def    - default expression (if the attribute was not found)
     */
    public Expression parseExpression(SyntaxTreeNode parent,
                                      String attr, String def) {
        // Get the textual representation of the expression (if any)
        String exp = parent.getAttribute(attr);
        // Use the default expression if none was found
        if ((exp.length() == 0) && (def != null)) exp = def;
        // Invoke the XPath parser
        return (Expression)parseTopLevel(parent, "<EXPRESSION>"+exp, exp);
    }

    /**
     * Parse an XPath pattern:
     * <p>
     *  解析XPath模式：
     * 
     * 
     *  @param parent  - XSL element where the pattern occured
     *  @param pattern - textual representation of the pattern
     */
    public Pattern parsePattern(SyntaxTreeNode parent, String pattern) {
        return (Pattern)parseTopLevel(parent, "<PATTERN>"+pattern, pattern);
    }

    /**
     * Parse an XPath pattern:
     * <p>
     *  解析XPath模式：
     * 
     * 
     *  @param parent - XSL element where the pattern occured
     *  @param attr   - name of this element's attribute to get pattern from
     *  @param def    - default pattern (if the attribute was not found)
     */
    public Pattern parsePattern(SyntaxTreeNode parent,
                                String attr, String def) {
        // Get the textual representation of the pattern (if any)
        String pattern = parent.getAttribute(attr);
        // Use the default pattern if none was found
        if ((pattern.length() == 0) && (def != null)) pattern = def;
        // Invoke the XPath parser
        return (Pattern)parseTopLevel(parent, "<PATTERN>"+pattern, pattern);
    }

    /**
     * Parse an XPath expression or pattern using the generated XPathParser
     * The method will return a Dummy node if the XPath parser fails.
     * <p>
     *  使用生成的XPathParser解析XPath表达式或模式如果XPath解析器失败,该方法将返回Dummy节点。
     * 
     */
    private SyntaxTreeNode parseTopLevel(SyntaxTreeNode parent, String text,
                                         String expression) {
        int line = getLineNumber();

        try {
            _xpathParser.setScanner(new XPathLexer(new StringReader(text)));
            Symbol result = _xpathParser.parse(expression, line);
            if (result != null) {
                final SyntaxTreeNode node = (SyntaxTreeNode)result.value;
                if (node != null) {
                    node.setParser(this);
                    node.setParent(parent);
                    node.setLineNumber(line);
// System.out.println("e = " + text + " " + node);
                    return node;
                }
            }
            reportError(ERROR, new ErrorMsg(ErrorMsg.XPATH_PARSER_ERR,
                                            expression, parent));
        }
        catch (Exception e) {
            if (_xsltc.debug()) e.printStackTrace();
            reportError(ERROR, new ErrorMsg(ErrorMsg.XPATH_PARSER_ERR,
                                            expression, parent));
        }

        // Return a dummy pattern (which is an expression)
        SyntaxTreeNode.Dummy.setParser(this);
        return SyntaxTreeNode.Dummy;
    }

    /************************ ERROR HANDLING SECTION ************************/

    /**
     * Returns true if there were any errors during compilation
     * <p>
     *  如果在编译期间有任何错误,则返回true
     * 
     */
    public boolean errorsFound() {
        return _errors.size() > 0;
    }

    /**
     * Prints all compile-time errors
     * <p>
     *  打印所有编译时错误
     * 
     */
    public void printErrors() {
        final int size = _errors.size();
        if (size > 0) {
            System.err.println(new ErrorMsg(ErrorMsg.COMPILER_ERROR_KEY));
            for (int i = 0; i < size; i++) {
                System.err.println("  " + _errors.elementAt(i));
            }
        }
    }

    /**
     * Prints all compile-time warnings
     * <p>
     *  打印所有编译时警告
     * 
     */
    public void printWarnings() {
        final int size = _warnings.size();
        if (size > 0) {
            System.err.println(new ErrorMsg(ErrorMsg.COMPILER_WARNING_KEY));
            for (int i = 0; i < size; i++) {
                System.err.println("  " + _warnings.elementAt(i));
            }
        }
    }

    /**
     * Common error/warning message handler
     * <p>
     *  常见错误/警告消息处理程序
     * 
     */
    public void reportError(final int category, final ErrorMsg error) {
        switch (category) {
        case Constants.INTERNAL:
            // Unexpected internal errors, such as null-ptr exceptions, etc.
            // Immediately terminates compilation, no translet produced
            _errors.addElement(error);
            break;
        case Constants.UNSUPPORTED:
            // XSLT elements that are not implemented and unsupported ext.
            // Immediately terminates compilation, no translet produced
            _errors.addElement(error);
            break;
        case Constants.FATAL:
            // Fatal error in the stylesheet input (parsing or content)
            // Immediately terminates compilation, no translet produced
            _errors.addElement(error);
            break;
        case Constants.ERROR:
            // Other error in the stylesheet input (parsing or content)
            // Does not terminate compilation, no translet produced
            _errors.addElement(error);
            break;
        case Constants.WARNING:
            // Other error in the stylesheet input (content errors only)
            // Does not terminate compilation, a translet is produced
            _warnings.addElement(error);
            break;
        }
    }

    public Vector getErrors() {
        return _errors;
    }

    public Vector getWarnings() {
        return _warnings;
    }

    /************************ SAX2 ContentHandler INTERFACE *****************/

    private Stack _parentStack = null;
    private Hashtable _prefixMapping = null;

    /**
     * SAX2: Receive notification of the beginning of a document.
     * <p>
     *  SAX2：接收文档开头的通知。
     * 
     */
    public void startDocument() {
        _root = null;
        _target = null;
        _prefixMapping = null;
        _parentStack = new Stack();
    }

    /**
     * SAX2: Receive notification of the end of a document.
     * <p>
     *  SAX2：接收文档结束的通知。
     * 
     */
    public void endDocument() { }


    /**
     * SAX2: Begin the scope of a prefix-URI Namespace mapping.
     *       This has to be passed on to the symbol table!
     * <p>
     *  SAX2：开始前缀-URI命名空间映射的范围。这必须传递到符号表！
     * 
     */
    public void startPrefixMapping(String prefix, String uri) {
        if (_prefixMapping == null) {
            _prefixMapping = new Hashtable();
        }
        _prefixMapping.put(prefix, uri);
    }

    /**
     * SAX2: End the scope of a prefix-URI Namespace mapping.
     *       This has to be passed on to the symbol table!
     * <p>
     *  SAX2：结束前缀URI范围的命名空间映射。这必须传递到符号表！
     * 
     */
    public void endPrefixMapping(String prefix) { }

    /**
     * SAX2: Receive notification of the beginning of an element.
     *       The parser may re-use the attribute list that we're passed so
     *       we clone the attributes in our own Attributes implementation
     * <p>
     *  SAX2：接收元素开头的通知。解析器可以重用我们传递的属性列表,因此我们在我们自己的Attributes实现中克隆属性
     * 
     */
    public void startElement(String uri, String localname,
                             String qname, Attributes attributes)
        throws SAXException {
        final int col = qname.lastIndexOf(':');
        final String prefix = (col == -1) ? null : qname.substring(0, col);

        SyntaxTreeNode element = makeInstance(uri, prefix,
                                        localname, attributes);
        if (element == null) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.ELEMENT_PARSE_ERR,
                                        prefix+':'+localname);
            throw new SAXException(err.toString());
        }

        // If this is the root element of the XML document we need to make sure
        // that it contains a definition of the XSL namespace URI
        if (_root == null) {
            if ((_prefixMapping == null) ||
                (_prefixMapping.containsValue(Constants.XSLT_URI) == false))
                _rootNamespaceDef = false;
            else
                _rootNamespaceDef = true;
            _root = element;
        }
        else {
            SyntaxTreeNode parent = (SyntaxTreeNode)_parentStack.peek();
            parent.addElement(element);
            element.setParent(parent);
        }
        element.setAttributes(new AttributesImpl(attributes));
        element.setPrefixMapping(_prefixMapping);

        if (element instanceof Stylesheet) {
            // Extension elements and excluded elements have to be
            // handled at this point in order to correctly generate
            // Fallback elements from <xsl:fallback>s.
            getSymbolTable().setCurrentNode(element);
            ((Stylesheet)element).declareExtensionPrefixes(this);
        }

        _prefixMapping = null;
        _parentStack.push(element);
    }

    /**
     * SAX2: Receive notification of the end of an element.
     * <p>
     *  SAX2：接收元素结束的通知。
     * 
     */
    public void endElement(String uri, String localname, String qname) {
        _parentStack.pop();
    }

    /**
     * SAX2: Receive notification of character data.
     * <p>
     *  SAX2：接收字符数据的通知。
     * 
     */
    public void characters(char[] ch, int start, int length) {
        String string = new String(ch, start, length);
        SyntaxTreeNode parent = (SyntaxTreeNode)_parentStack.peek();

        if (string.length() == 0) return;

        // If this text occurs within an <xsl:text> element we append it
        // as-is to the existing text element
        if (parent instanceof Text) {
            ((Text)parent).setText(string);
            return;
        }

        // Ignore text nodes that occur directly under <xsl:stylesheet>
        if (parent instanceof Stylesheet) return;

        SyntaxTreeNode bro = parent.lastChild();
        if ((bro != null) && (bro instanceof Text)) {
            Text text = (Text)bro;
            if (!text.isTextElement()) {
                if ((length > 1) || ( ((int)ch[0]) < 0x100)) {
                    text.setText(string);
                    return;
                }
            }
        }

        // Add it as a regular text node otherwise
        parent.addElement(new Text(string));
    }

    private String getTokenValue(String token) {
        final int start = token.indexOf('"');
        final int stop = token.lastIndexOf('"');
        return token.substring(start+1, stop);
    }

    /**
     * SAX2: Receive notification of a processing instruction.
     *       These require special handling for stylesheet PIs.
     * <p>
     *  SAX2：接收处理指令的通知。这些需要对样式表PI进行特殊处理。
     * 
     */
    public void processingInstruction(String name, String value) {
        // We only handle the <?xml-stylesheet ...?> PI
        if ((_target == null) && (name.equals("xml-stylesheet"))) {

            String href = null;    // URI of stylesheet found
            String media = null;   // Media of stylesheet found
            String title = null;   // Title of stylesheet found
            String charset = null; // Charset of stylesheet found

            // Get the attributes from the processing instruction
            StringTokenizer tokens = new StringTokenizer(value);
            while (tokens.hasMoreElements()) {
                String token = (String)tokens.nextElement();
                if (token.startsWith("href"))
                    href = getTokenValue(token);
                else if (token.startsWith("media"))
                    media = getTokenValue(token);
                else if (token.startsWith("title"))
                    title = getTokenValue(token);
                else if (token.startsWith("charset"))
                    charset = getTokenValue(token);
            }

            // Set the target to this PI's href if the parameters are
            // null or match the corresponding attributes of this PI.
            if ( ((_PImedia == null) || (_PImedia.equals(media))) &&
                 ((_PItitle == null) || (_PImedia.equals(title))) &&
                 ((_PIcharset == null) || (_PImedia.equals(charset))) ) {
                _target = href;
            }
        }
    }

    /**
     * IGNORED - all ignorable whitespace is ignored
     * <p>
     *  IGNORED  - 忽略所有可忽略的空格
     * 
     */
    public void ignorableWhitespace(char[] ch, int start, int length) { }

    /**
     * IGNORED - we do not have to do anything with skipped entities
     * <p>
     *  忽略 - 我们不必对跳过的实体执行任何操作
     * 
     */
    public void skippedEntity(String name) { }

    /**
     * Store the document locator to later retrieve line numbers of all
     * elements from the stylesheet
     * <p>
     *  存储文档定位器以稍后从样式表中检索所有元素的行号
     * 
     */
    public void setDocumentLocator(Locator locator) {
        _locator = locator;
    }

    /**
     * Get the line number, or zero
     * if there is no _locator.
     * <p>
     *  获取行号,如果没有_locator,则为零。
     */
    private int getLineNumber() {
        int line = 0;
        if (_locator != null)
                line = _locator.getLineNumber();
        return line;
    }

}
