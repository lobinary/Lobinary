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
 * $Id: XSLTC.java,v 1.2.4.1 2005/09/05 09:51:38 pvedula Exp $
 * <p>
 *  $ Id：XSLTC.java,v 1.2.4.1 2005/09/05 09:51:38 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.xalan.internal.XalanConstants;
import com.sun.org.apache.xalan.internal.utils.FeatureManager;
import com.sun.org.apache.xalan.internal.utils.FeatureManager.Feature;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import com.sun.org.apache.xml.internal.dtm.DTM;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import javax.xml.XMLConstants;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author G. Todd Miller
 * @author Morten Jorgensen
 * @author John Howard (johnh@schemasoft.com)
 */
public final class XSLTC {

    // A reference to the main stylesheet parser object.
    private Parser _parser;

    // A reference to an external XMLReader (SAX parser) passed to us
    private XMLReader _reader = null;

    // A reference to an external SourceLoader (for use with include/import)
    private SourceLoader _loader = null;

    // A reference to the stylesheet being compiled.
    private Stylesheet _stylesheet;

    // Counters used by various classes to generate unique names.
    // private int _variableSerial     = 1;
    private int _modeSerial         = 1;
    private int _stylesheetSerial   = 1;
    private int _stepPatternSerial  = 1;
    private int _helperClassSerial  = 0;
    private int _attributeSetSerial = 0;

    private int[] _numberFieldIndexes;

    // Name index tables
    private int       _nextGType;  // Next available element type
    private Vector    _namesIndex; // Index of all registered QNames
    private Hashtable _elements;   // Hashtable of all registered elements
    private Hashtable _attributes; // Hashtable of all registered attributes

    // Namespace index tables
    private int       _nextNSType; // Next available namespace type
    private Vector    _namespaceIndex; // Index of all registered namespaces
    private Hashtable _namespaces; // Hashtable of all registered namespaces
    private Hashtable _namespacePrefixes;// Hashtable of all registered namespace prefixes


    // All literal text in the stylesheet
    private Vector m_characterData;

    // These define the various methods for outputting the translet
    public static final int FILE_OUTPUT        = 0;
    public static final int JAR_OUTPUT         = 1;
    public static final int BYTEARRAY_OUTPUT   = 2;
    public static final int CLASSLOADER_OUTPUT = 3;
    public static final int BYTEARRAY_AND_FILE_OUTPUT = 4;
    public static final int BYTEARRAY_AND_JAR_OUTPUT  = 5;


    // Compiler options (passed from command line or XSLTC client)
    private boolean _debug = false;      // -x
    private String  _jarFileName = null; // -j <jar-file-name>
    private String  _className = null;   // -o <class-name>
    private String  _packageName = null; // -p <package-name>
    private File    _destDir = null;     // -d <directory-name>
    private int     _outputType = FILE_OUTPUT; // by default

    private Vector  _classes;
    private Vector  _bcelClasses;
    private boolean _callsNodeset = false;
    private boolean _multiDocument = false;
    private boolean _hasIdCall = false;

    /**
     * Set to true if template inlining is requested. Template
     * inlining used to be the default, but we have found that
     * Hotspots does a better job with shorter methods, so the
     * default is *not* to inline now.
     * <p>
     *  如果请求模板内联,请设置为true。模板内联以前是默认的,但我们发现热点使用更短的方法更好的工作,所以默认是*不是*现在内联。
     * 
     */
    private boolean _templateInlining = false;

    /**
     * State of the secure processing feature.
     * <p>
     *  安全处理功能的状态。
     * 
     */
    private boolean _isSecureProcessing = false;

    private boolean _useServicesMechanism = true;

    /**
     * protocols allowed for external references set by the stylesheet processing instruction, Import and Include element.
     * <p>
     *  允许由样式表处理指令设置的外部引用的协议,Import和Include元素。
     * 
     */
    private String _accessExternalStylesheet = XalanConstants.EXTERNAL_ACCESS_DEFAULT;
     /**
     * protocols allowed for external DTD references in source file and/or stylesheet.
     * <p>
     *  允许在源文件和/或样式表中的外部DTD引用的协议。
     * 
     */
    private String _accessExternalDTD = XalanConstants.EXTERNAL_ACCESS_DEFAULT;

    private XMLSecurityManager _xmlSecurityManager;

    private final FeatureManager _featureManager;

    /**
    *  Extension function class loader variables
    * <p>
    *  扩展函数类加载器变量
    * 
    */

    /* Class loader reference that will be used for external extension functions loading */
    private ClassLoader _extensionClassLoader;

    /**
    *  HashMap with the loaded classes
    * <p>
    *  HashMap与加载的类
    * 
    */
    private final Map<String, Class> _externalExtensionFunctions;

    /**
     * XSLTC compiler constructor
     * <p>
     *  XSLTC编译器构造器
     * 
     */
    public XSLTC(boolean useServicesMechanism, FeatureManager featureManager) {
        _parser = new Parser(this, useServicesMechanism);
        _featureManager = featureManager;
        _extensionClassLoader = null;
        _externalExtensionFunctions = new HashMap<>();
    }

    /**
     * Set the state of the secure processing feature.
     * <p>
     *  设置安全处理功能的状态。
     * 
     */
    public void setSecureProcessing(boolean flag) {
        _isSecureProcessing = flag;
    }

    /**
     * Return the state of the secure processing feature.
     * <p>
     *  返回安全处理功能的状态。
     * 
     */
    public boolean isSecureProcessing() {
        return _isSecureProcessing;
    }
    /**
     * Return the state of the services mechanism feature.
     * <p>
     *  返回服务机制功能的状态。
     * 
     */
    public boolean useServicesMechnism() {
        return _useServicesMechanism;
    }

    /**
     * Set the state of the services mechanism feature.
     * <p>
     * 设置服务机制功能的状态。
     * 
     */
    public void setServicesMechnism(boolean flag) {
        _useServicesMechanism = flag;
    }

     /**
     * Return the value of the specified feature
     * <p>
     *  返回指定要素的值
     * 
     * 
     * @param name name of the feature
     * @return true if the feature is enabled, false otherwise
     */
    public boolean getFeature(Feature name) {
        return _featureManager.isFeatureEnabled(name);
    }

    /**
     * Return allowed protocols for accessing external stylesheet.
     * <p>
     *  返回允许访问外部样式表的协议。
     * 
     */
    public Object getProperty(String name) {
        if (name.equals(XMLConstants.ACCESS_EXTERNAL_STYLESHEET)) {
            return _accessExternalStylesheet;
        }
        else if (name.equals(XMLConstants.ACCESS_EXTERNAL_DTD)) {
            return _accessExternalDTD;
        } else if (name.equals(XalanConstants.SECURITY_MANAGER)) {
            return _xmlSecurityManager;
        } else if (name.equals(XalanConstants.JDK_EXTENSION_CLASSLOADER)) {
            return _extensionClassLoader;
        }
        return null;
    }

    /**
     * Set allowed protocols for accessing external stylesheet.
     * <p>
     *  设置允许访问外部样式表的协议。
     * 
     */
    public void setProperty(String name, Object value) {
        if (name.equals(XMLConstants.ACCESS_EXTERNAL_STYLESHEET)) {
            _accessExternalStylesheet = (String)value;
        }
        else if (name.equals(XMLConstants.ACCESS_EXTERNAL_DTD)) {
            _accessExternalDTD = (String)value;
        } else if (name.equals(XalanConstants.SECURITY_MANAGER)) {
            _xmlSecurityManager = (XMLSecurityManager)value;
        } else if (name.equals(XalanConstants.JDK_EXTENSION_CLASSLOADER)) {
            _extensionClassLoader = (ClassLoader) value;
            /* Clear the external extension functions HashMap if extension class
            /* <p>
            /* 
               loader was changed */
            _externalExtensionFunctions.clear();
        }
    }

    /**
     * Only for user by the internal TrAX implementation.
     * <p>
     *  仅供用户通过内部TrAX实现。
     * 
     */
    public Parser getParser() {
        return _parser;
    }

    /**
     * Only for user by the internal TrAX implementation.
     * <p>
     *  仅供用户通过内部TrAX实现。
     * 
     */
    public void setOutputType(int type) {
        _outputType = type;
    }

    /**
     * Only for user by the internal TrAX implementation.
     * <p>
     *  仅供用户通过内部TrAX实现。
     * 
     */
    public Properties getOutputProperties() {
        return _parser.getOutputProperties();
    }

    /**
     * Initializes the compiler to compile a new stylesheet
     * <p>
     *  初始化编译器以编译新的样式表
     * 
     */
    public void init() {
        reset();
        _reader = null;
        _classes = new Vector();
        _bcelClasses = new Vector();
    }

    private void setExternalExtensionFunctions(String name, Class clazz) {
        if (_isSecureProcessing && clazz != null && !_externalExtensionFunctions.containsKey(name)) {
            _externalExtensionFunctions.put(name, clazz);
        }
    }

    /*
     * Function loads an external extension functions.
     * The filtering of function types (external,internal) takes place in FunctionCall class
     *
     * <p>
     *  功能加载外部扩展功能。函数类型(外部,内部)的过滤发生在FunctionCall类中
     * 
     */
    Class loadExternalFunction(String name) throws ClassNotFoundException {
        Class loaded = null;
        //Check if the function is not loaded already
        if (_externalExtensionFunctions.containsKey(name)) {
            loaded = _externalExtensionFunctions.get(name);
        } else if (_extensionClassLoader != null) {
            loaded = Class.forName(name, true, _extensionClassLoader);
            setExternalExtensionFunctions(name, loaded);
        }
        if (loaded == null) {
            throw new ClassNotFoundException(name);
        }
        //Return loaded class
        return (Class) loaded;
    }

    /*
     * Returns unmodifiable view of HashMap with loaded external extension
     * functions - will be needed for the TransformerImpl
     * <p>
     *  返回带有加载的外部扩展函数的HashMap的不可修改视图 - 将需要TransformerImpl
     * 
    */
    public Map<String, Class> getExternalExtensionFunctions() {
        return Collections.unmodifiableMap(_externalExtensionFunctions);
    }

    /**
     * Initializes the compiler to produce a new translet
     * <p>
     *  初始化编译器以产生新的translet
     * 
     */
    private void reset() {
        _nextGType      = DTM.NTYPES;
        _elements       = new Hashtable();
        _attributes     = new Hashtable();
        _namespaces     = new Hashtable();
        _namespaces.put("",new Integer(_nextNSType));
        _namesIndex     = new Vector(128);
        _namespaceIndex = new Vector(32);
        _namespacePrefixes = new Hashtable();
        _stylesheet     = null;
        _parser.init();
        //_variableSerial     = 1;
        _modeSerial         = 1;
        _stylesheetSerial   = 1;
        _stepPatternSerial  = 1;
        _helperClassSerial  = 0;
        _attributeSetSerial = 0;
        _multiDocument      = false;
        _hasIdCall          = false;
        _numberFieldIndexes = new int[] {
            -1,         // LEVEL_SINGLE
            -1,         // LEVEL_MULTIPLE
            -1          // LEVEL_ANY
        };
        _externalExtensionFunctions.clear();
    }

    /**
     * Defines an external SourceLoader to provide the compiler with documents
     * referenced in xsl:include/import
     * <p>
     *  定义外部SourceLoader以向编译器提供在xsl：include / import中引用的文档
     * 
     * 
     * @param loader The SourceLoader to use for include/import
     */
    public void setSourceLoader(SourceLoader loader) {
        _loader = loader;
    }

    /**
     * Set a flag indicating if templates are to be inlined or not. The
     * default is to do inlining, but this causes problems when the
     * stylesheets have a large number of templates (e.g. branch targets
     * exceeding 64K or a length of a method exceeding 64K).
     * <p>
     *  设置一个标志,指示模板是否要内联。默认是做内联,但是当样式表有大量模板时(例如,分支目标超过64K或方法长度超过64K)会导致问题。
     * 
     */
    public void setTemplateInlining(boolean templateInlining) {
        _templateInlining = templateInlining;
    }
     /**
     * Return the state of the template inlining feature.
     * <p>
     *  返回模板内联函数的状态。
     * 
     */
    public boolean getTemplateInlining() {
        return _templateInlining;
    }

    /**
     * Set the parameters to use to locate the correct <?xml-stylesheet ...?>
     * processing instruction in the case where the input document to the
     * compiler (and parser) is an XML document.
     * <p>
     *  设置要使用的参数,以便在编译器(和解析器)的输入文档是XML文档的情况下找到正确的<?xml-stylesheet ...?>处理指令。
     * 
     * 
     * @param media The media attribute to be matched. May be null, in which
     * case the prefered templates will be used (i.e. alternate = no).
     * @param title The value of the title attribute to match. May be null.
     * @param charset The value of the charset attribute to match. May be null.
     */
    public void setPIParameters(String media, String title, String charset) {
        _parser.setPIParameters(media, title, charset);
    }

    /**
     * Compiles an XSL stylesheet pointed to by a URL
     * <p>
     *  编译URL指向的XSL样式表
     * 
     * 
     * @param url An URL containing the input XSL stylesheet
     */
    public boolean compile(URL url) {
        try {
            // Open input stream from URL and wrap inside InputSource
            final InputStream stream = url.openStream();
            final InputSource input = new InputSource(stream);
            input.setSystemId(url.toString());
            return compile(input, _className);
        }
        catch (IOException e) {
            _parser.reportError(Constants.FATAL, new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
            return false;
        }
    }

    /**
     * Compiles an XSL stylesheet pointed to by a URL
     * <p>
     *  编译URL指向的XSL样式表
     * 
     * 
     * @param url An URL containing the input XSL stylesheet
     * @param name The name to assign to the translet class
     */
    public boolean compile(URL url, String name) {
        try {
            // Open input stream from URL and wrap inside InputSource
            final InputStream stream = url.openStream();
            final InputSource input = new InputSource(stream);
            input.setSystemId(url.toString());
            return compile(input, name);
        }
        catch (IOException e) {
            _parser.reportError(Constants.FATAL, new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
            return false;
        }
    }

    /**
     * Compiles an XSL stylesheet passed in through an InputStream
     * <p>
     *  编译通过InputStream传入的XSL样式表
     * 
     * 
     * @param stream An InputStream that will pass in the stylesheet contents
     * @param name The name of the translet class to generate
     * @return 'true' if the compilation was successful
     */
    public boolean compile(InputStream stream, String name) {
        final InputSource input = new InputSource(stream);
        input.setSystemId(name); // We have nothing else!!!
        return compile(input, name);
    }

    /**
     * Compiles an XSL stylesheet passed in through an InputStream
     * <p>
     * 编译通过InputStream传入的XSL样式表
     * 
     * 
     * @param input An InputSource that will pass in the stylesheet contents
     * @param name The name of the translet class to generate - can be null
     * @return 'true' if the compilation was successful
     */
    public boolean compile(InputSource input, String name) {
        try {
            // Reset globals in case we're called by compile(Vector v);
            reset();

            // The systemId may not be set, so we'll have to check the URL
            String systemId = null;
            if (input != null) {
                systemId = input.getSystemId();
            }

            // Set the translet class name if not already set
            if (_className == null) {
                if (name != null) {
                    setClassName(name);
                }
                else if (systemId != null && !systemId.equals("")) {
                    setClassName(Util.baseName(systemId));
                }

                // Ensure we have a non-empty class name at this point
                if (_className == null || _className.length() == 0) {
                    setClassName("GregorSamsa"); // default translet name
                }
            }

            // Get the root node of the abstract syntax tree
            SyntaxTreeNode element = null;
            if (_reader == null) {
                element = _parser.parse(input);
            }
            else {
                element = _parser.parse(_reader, input);
            }

            // Compile the translet - this is where the work is done!
            if ((!_parser.errorsFound()) && (element != null)) {
                // Create a Stylesheet element from the root node
                _stylesheet = _parser.makeStylesheet(element);
                _stylesheet.setSourceLoader(_loader);
                _stylesheet.setSystemId(systemId);
                _stylesheet.setParentStylesheet(null);
                _stylesheet.setTemplateInlining(_templateInlining);
                _parser.setCurrentStylesheet(_stylesheet);

                // Create AST under the Stylesheet element (parse & type-check)
                _parser.createAST(_stylesheet);
            }
            // Generate the bytecodes and output the translet class(es)
            if ((!_parser.errorsFound()) && (_stylesheet != null)) {
                _stylesheet.setCallsNodeset(_callsNodeset);
                _stylesheet.setMultiDocument(_multiDocument);
                _stylesheet.setHasIdCall(_hasIdCall);

                // Class synchronization is needed for BCEL
                synchronized (getClass()) {
                    _stylesheet.translate();
                }
            }
        }
        catch (Exception e) {
            /*if (_debug)*/ e.printStackTrace();
            _parser.reportError(Constants.FATAL, new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
        }
        catch (Error e) {
            if (_debug) e.printStackTrace();
            _parser.reportError(Constants.FATAL, new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR, e));
        }
        finally {
            _reader = null; // reset this here to be sure it is not re-used
        }
        return !_parser.errorsFound();
    }

    /**
     * Compiles a set of stylesheets pointed to by a Vector of URLs
     * <p>
     *  _parser.reportError(Constants.FATAL,new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR,e)); } catch(Error e){if(_debug)e.printStackTrace(); _parser.reportError(Constants.FATAL,new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR,e)); }
     *  finally {_reader = null; // reset this here to确保它不被重用} return！_parser.errorsFound(); }}。
     * 
     *  / **编译由URL的向量指向的一组样式表
     * 
     * 
     * @param stylesheets A Vector containing URLs pointing to the stylesheets
     * @return 'true' if the compilation was successful
     */
    public boolean compile(Vector stylesheets) {
        // Get the number of stylesheets (ie. URLs) in the vector
        final int count = stylesheets.size();

        // Return straight away if the vector is empty
        if (count == 0) return true;

        // Special handling needed if the URL count is one, becuase the
        // _className global must not be reset if it was set explicitly
        if (count == 1) {
            final Object url = stylesheets.firstElement();
            if (url instanceof URL)
                return compile((URL)url);
            else
                return false;
        }
        else {
            // Traverse all elements in the vector and compile
            final Enumeration urls = stylesheets.elements();
            while (urls.hasMoreElements()) {
                _className = null; // reset, so that new name will be computed
                final Object url = urls.nextElement();
                if (url instanceof URL) {
                    if (!compile((URL)url)) return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns an array of bytecode arrays generated by a compilation.
     * <p>
     *  返回由编译生成的字节码数组的数组。
     * 
     * 
     * @return JVM bytecodes that represent translet class definition
     */
    public byte[][] getBytecodes() {
        final int count = _classes.size();
        final byte[][] result = new byte[count][1];
        for (int i = 0; i < count; i++)
            result[i] = (byte[])_classes.elementAt(i);
        return result;
    }

    /**
     * Compiles a stylesheet pointed to by a URL. The result is put in a
     * set of byte arrays. One byte array for each generated class.
     * <p>
     *  编译URL指向的样式表。结果放在一组字节数组中。每个生成的类一个字节数组。
     * 
     * 
     * @param name The name of the translet class to generate
     * @param input An InputSource that will pass in the stylesheet contents
     * @param outputType The output type
     * @return JVM bytecodes that represent translet class definition
     */
    public byte[][] compile(String name, InputSource input, int outputType) {
        _outputType = outputType;
        if (compile(input, name))
            return getBytecodes();
        else
            return null;
    }

    /**
     * Compiles a stylesheet pointed to by a URL. The result is put in a
     * set of byte arrays. One byte array for each generated class.
     * <p>
     *  编译URL指向的样式表。结果放在一组字节数组中。每个生成的类一个字节数组。
     * 
     * 
     * @param name The name of the translet class to generate
     * @param input An InputSource that will pass in the stylesheet contents
     * @return JVM bytecodes that represent translet class definition
     */
    public byte[][] compile(String name, InputSource input) {
        return compile(name, input, BYTEARRAY_OUTPUT);
    }

    /**
     * Set the XMLReader to use for parsing the next input stylesheet
     * <p>
     *  设置XMLReader用于解析下一个输入样式表
     * 
     * 
     * @param reader XMLReader (SAX2 parser) to use
     */
    public void setXMLReader(XMLReader reader) {
        _reader = reader;
    }

    /**
     * Get the XMLReader to use for parsing the next input stylesheet
     * <p>
     *  获取用于解析下一个输入样式表的XMLReader
     * 
     */
    public XMLReader getXMLReader() {
        return _reader ;
    }

    /**
     * Get a Vector containing all compile error messages
     * <p>
     *  获取包含所有编译错误消息的向量
     * 
     * 
     * @return A Vector containing all compile error messages
     */
    public Vector getErrors() {
        return _parser.getErrors();
    }

    /**
     * Get a Vector containing all compile warning messages
     * <p>
     *  获取包含所有编译警告消息的向量
     * 
     * 
     * @return A Vector containing all compile error messages
     */
    public Vector getWarnings() {
        return _parser.getWarnings();
    }

    /**
     * Print all compile error messages to standard output
     * <p>
     *  将所有编译错误消息打印到标准输出
     * 
     */
    public void printErrors() {
        _parser.printErrors();
    }

    /**
     * Print all compile warning messages to standard output
     * <p>
     *  将所有编译警告消息打印到标准输出
     * 
     */
    public void printWarnings() {
        _parser.printWarnings();
    }

    /**
     * This method is called by the XPathParser when it encounters a call
     * to the document() function. Affects the DOM used by the translet.
     * <p>
     *  XPathParser在遇到对document()函数的调用时调用此方法。影响translet使用的DOM。
     * 
     */
    protected void setMultiDocument(boolean flag) {
        _multiDocument = flag;
    }

    public boolean isMultiDocument() {
        return _multiDocument;
    }

    /**
     * This method is called by the XPathParser when it encounters a call
     * to the nodeset() extension function. Implies multi document.
     * <p>
     *  当XPathParser遇到对nodeset()扩展函数的调用时,将调用此方法。隐含多文档。
     * 
     */
    protected void setCallsNodeset(boolean flag) {
        if (flag) setMultiDocument(flag);
        _callsNodeset = flag;
    }

    public boolean callsNodeset() {
        return _callsNodeset;
    }

    protected void setHasIdCall(boolean flag) {
        _hasIdCall = flag;
    }

    public boolean hasIdCall() {
        return _hasIdCall;
    }

    /**
     * Set the class name for the generated translet. This class name is
     * overridden if multiple stylesheets are compiled in one go using the
     * compile(Vector urls) method.
     * <p>
     * 设置生成的translet的类名。如果使用compile(Vector urls)方法一次编译多个样式表,将覆盖此类名。
     * 
     * 
     * @param className The name to assign to the translet class
     */
    public void setClassName(String className) {
        final String base  = Util.baseName(className);
        final String noext = Util.noExtName(base);
        String name  = Util.toJavaName(noext);

        if (_packageName == null)
            _className = name;
        else
            _className = _packageName + '.' + name;
    }

    /**
     * Get the class name for the generated translet.
     * <p>
     *  获取生成的translet的类名。
     * 
     */
    public String getClassName() {
        return _className;
    }

    /**
     * Convert for Java class name of local system file name.
     * (Replace '.' with '/' on UNIX and replace '.' by '\' on Windows/DOS.)
     * <p>
     *  转换本地系统文件名的Java类名。 (在UNIX上将'。'替换为'/',并在Windows / DOS上用'\'替换'。'。)
     * 
     */
    private String classFileName(final String className) {
        return className.replace('.', File.separatorChar) + ".class";
    }

    /**
     * Generate an output File object to send the translet to
     * <p>
     *  生成一个输出File对象以将translet发送到
     * 
     */
    private File getOutputFile(String className) {
        if (_destDir != null)
            return new File(_destDir, classFileName(className));
        else
            return new File(classFileName(className));
    }

    /**
     * Set the destination directory for the translet.
     * The current working directory will be used by default.
     * <p>
     *  设置translet的目标目录。默认情况下将使用当前工作目录。
     * 
     */
    public boolean setDestDirectory(String dstDirName) {
        final File dir = new File(dstDirName);
        if (SecuritySupport.getFileExists(dir) || dir.mkdirs()) {
            _destDir = dir;
            return true;
        }
        else {
            _destDir = null;
            return false;
        }
    }

    /**
     * Set an optional package name for the translet and auxiliary classes
     * <p>
     *  为translet和辅助类设置可选的包名称
     * 
     */
    public void setPackageName(String packageName) {
        _packageName = packageName;
        if (_className != null) setClassName(_className);
    }

    /**
     * Set the name of an optional JAR-file to dump the translet and
     * auxiliary classes to
     * <p>
     *  设置可选的JAR文件的名称以将translet和辅助类转储到
     * 
     */
    public void setJarFileName(String jarFileName) {
        final String JAR_EXT = ".jar";
        if (jarFileName.endsWith(JAR_EXT))
            _jarFileName = jarFileName;
        else
            _jarFileName = jarFileName + JAR_EXT;
        _outputType = JAR_OUTPUT;
    }

    public String getJarFileName() {
        return _jarFileName;
    }

    /**
     * Set the top-level stylesheet
     * <p>
     *  设置顶级样式表
     * 
     */
    public void setStylesheet(Stylesheet stylesheet) {
        if (_stylesheet == null) _stylesheet = stylesheet;
    }

    /**
     * Returns the top-level stylesheet
     * <p>
     *  返回顶级样式表
     * 
     */
    public Stylesheet getStylesheet() {
        return _stylesheet;
    }

    /**
     * Registers an attribute and gives it a type so that it can be mapped to
     * DOM attribute types at run-time.
     * <p>
     *  注册一个属性并给它一个类型,以便它可以在运行时映射到DOM属性类型。
     * 
     */
    public int registerAttribute(QName name) {
        Integer code = (Integer)_attributes.get(name.toString());
        if (code == null) {
            code = new Integer(_nextGType++);
            _attributes.put(name.toString(), code);
            final String uri = name.getNamespace();
            final String local = "@"+name.getLocalPart();
            if ((uri != null) && (!uri.equals("")))
                _namesIndex.addElement(uri+":"+local);
            else
                _namesIndex.addElement(local);
            if (name.getLocalPart().equals("*")) {
                registerNamespace(name.getNamespace());
            }
        }
        return code.intValue();
    }

    /**
     * Registers an element and gives it a type so that it can be mapped to
     * DOM element types at run-time.
     * <p>
     *  注册一个元素并给它一个类型,以便它可以在运行时映射到DOM元素类型。
     * 
     */
    public int registerElement(QName name) {
        // Register element (full QName)
        Integer code = (Integer)_elements.get(name.toString());
        if (code == null) {
            _elements.put(name.toString(), code = new Integer(_nextGType++));
            _namesIndex.addElement(name.toString());
        }
        if (name.getLocalPart().equals("*")) {
            registerNamespace(name.getNamespace());
        }
        return code.intValue();
    }

     /**
      * Registers a namespace prefix and gives it a type so that it can be mapped to
      * DOM namespace types at run-time.
      * <p>
      *  注册一个命名空间前缀并给它一个类型,以便它可以在运行时映射到DOM命名空间类型。
      * 
      */

    public int registerNamespacePrefix(QName name) {

    Integer code = (Integer)_namespacePrefixes.get(name.toString());
    if (code == null) {
        code = new Integer(_nextGType++);
        _namespacePrefixes.put(name.toString(), code);
        final String uri = name.getNamespace();
        if ((uri != null) && (!uri.equals(""))){
            // namespace::ext2:ped2 will be made empty in TypedNamespaceIterator
            _namesIndex.addElement("?");
        } else{
           _namesIndex.addElement("?"+name.getLocalPart());
        }
    }
    return code.intValue();
    }

    /**
     * Registers a namespace and gives it a type so that it can be mapped to
     * DOM namespace types at run-time.
     * <p>
     *  注册一个命名空间并给它一个类型,以便它可以在运行时映射到DOM命名空间类型。
     * 
     */
    public int registerNamespace(String namespaceURI) {
        Integer code = (Integer)_namespaces.get(namespaceURI);
        if (code == null) {
            code = new Integer(_nextNSType++);
            _namespaces.put(namespaceURI,code);
            _namespaceIndex.addElement(namespaceURI);
        }
        return code.intValue();
    }

    public int nextModeSerial() {
        return _modeSerial++;
    }

    public int nextStylesheetSerial() {
        return _stylesheetSerial++;
    }

    public int nextStepPatternSerial() {
        return _stepPatternSerial++;
    }

    public int[] getNumberFieldIndexes() {
        return _numberFieldIndexes;
    }

    public int nextHelperClassSerial() {
        return _helperClassSerial++;
    }

    public int nextAttributeSetSerial() {
        return _attributeSetSerial++;
    }

    public Vector getNamesIndex() {
        return _namesIndex;
    }

    public Vector getNamespaceIndex() {
        return _namespaceIndex;
    }

    /**
     * Returns a unique name for every helper class needed to
     * execute a translet.
     * <p>
     *  返回执行translet所需的每个帮助类的唯一名称。
     * 
     */
    public String getHelperClassName() {
        return getClassName() + '$' + _helperClassSerial++;
    }

    public void dumpClass(JavaClass clazz) {

        if (_outputType == FILE_OUTPUT ||
            _outputType == BYTEARRAY_AND_FILE_OUTPUT)
        {
            File outFile = getOutputFile(clazz.getClassName());
            String parentDir = outFile.getParent();
            if (parentDir != null) {
                File parentFile = new File(parentDir);
                if (!SecuritySupport.getFileExists(parentFile))
                    parentFile.mkdirs();
            }
        }

        try {
            switch (_outputType) {
            case FILE_OUTPUT:
                clazz.dump(
                    new BufferedOutputStream(
                        new FileOutputStream(
                            getOutputFile(clazz.getClassName()))));
                break;
            case JAR_OUTPUT:
                _bcelClasses.addElement(clazz);
                break;
            case BYTEARRAY_OUTPUT:
            case BYTEARRAY_AND_FILE_OUTPUT:
            case BYTEARRAY_AND_JAR_OUTPUT:
            case CLASSLOADER_OUTPUT:
                ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
                clazz.dump(out);
                _classes.addElement(out.toByteArray());

                if (_outputType == BYTEARRAY_AND_FILE_OUTPUT)
                  clazz.dump(new BufferedOutputStream(
                        new FileOutputStream(getOutputFile(clazz.getClassName()))));
                else if (_outputType == BYTEARRAY_AND_JAR_OUTPUT)
                  _bcelClasses.addElement(clazz);

                break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * File separators are converted to forward slashes for ZIP files.
     * <p>
     *  文件分隔符转换为ZIP文件的正斜杠。
     * 
     */
    private String entryName(File f) throws IOException {
        return f.getName().replace(File.separatorChar, '/');
    }

    /**
     * Generate output JAR-file and packages
     * <p>
     *  生成输出JAR文件和包
     * 
     */
    public void outputToJar() throws IOException {
        // create the manifest
        final Manifest manifest = new Manifest();
        final java.util.jar.Attributes atrs = manifest.getMainAttributes();
        atrs.put(java.util.jar.Attributes.Name.MANIFEST_VERSION,"1.2");

        final Map map = manifest.getEntries();
        // create manifest
        Enumeration classes = _bcelClasses.elements();
        final String now = (new Date()).toString();
        final java.util.jar.Attributes.Name dateAttr =
            new java.util.jar.Attributes.Name("Date");
        while (classes.hasMoreElements()) {
            final JavaClass clazz = (JavaClass)classes.nextElement();
            final String className = clazz.getClassName().replace('.','/');
            final java.util.jar.Attributes attr = new java.util.jar.Attributes();
            attr.put(dateAttr, now);
            map.put(className+".class", attr);
        }

        final File jarFile = new File(_destDir, _jarFileName);
        final JarOutputStream jos =
            new JarOutputStream(new FileOutputStream(jarFile), manifest);
        classes = _bcelClasses.elements();
        while (classes.hasMoreElements()) {
            final JavaClass clazz = (JavaClass)classes.nextElement();
            final String className = clazz.getClassName().replace('.','/');
            jos.putNextEntry(new JarEntry(className+".class"));
            final ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            clazz.dump(out); // dump() closes it's output stream
            out.writeTo(jos);
        }
        jos.close();
    }

    /**
     * Turn debugging messages on/off
     * <p>
     *  打开/关闭调试消息
     * 
     */
    public void setDebug(boolean debug) {
        _debug = debug;
    }

    /**
     * Get current debugging message setting
     * <p>
     *  获取当前调试消息设置
     * 
     */
    public boolean debug() {
        return _debug;
    }


    /**
     * Retrieve a string representation of the character data to be stored
     * in the translet as a <code>char[]</code>.  There may be more than
     * one such array required.
     * <p>
     * 检索要作为<code> char [] </code>存储在translet中的字符数据的字符串表示。可能有多个这样的阵列需要。
     * 
     * 
     * @param index The index of the <code>char[]</code>.  Zero-based.
     * @return String The character data to be stored in the corresponding
     *               <code>char[]</code>.
     */
    public String getCharacterData(int index) {
        return ((StringBuffer) m_characterData.elementAt(index)).toString();
    }

    /**
     * Get the number of char[] arrays, thus far, that will be created to
     * store literal text in the stylesheet.
     * <p>
     *  获取char []数组的数目,到目前为止,将被创建来存储文本文本在样式表中。
     * 
     */
    public int getCharacterDataCount() {
        return (m_characterData != null) ? m_characterData.size() : 0;
    }

    /**
     * Add literal text to char arrays that will be used to store character
     * data in the stylesheet.
     * <p>
     *  将字符文本添加到将用于在样式表中存储字符数据的字符数组。
     * 
     * @param newData String data to be added to char arrays.
     *                Pre-condition:  <code>newData.length() &le; 21845</code>
     * @return int offset at which character data will be stored
     */
    public int addCharacterData(String newData) {
        StringBuffer currData;
        if (m_characterData == null) {
            m_characterData = new Vector();
            currData = new StringBuffer();
            m_characterData.addElement(currData);
        } else {
            currData = (StringBuffer) m_characterData
                                           .elementAt(m_characterData.size()-1);
        }

        // Character data could take up to three-times as much space when
        // written to the class file as UTF-8.  The maximum size for a
        // constant is 65535/3.  If we exceed that,
        // (We really should use some "bin packing".)
        if (newData.length() + currData.length() > 21845) {
            currData = new StringBuffer();
            m_characterData.addElement(currData);
        }

        int newDataOffset = currData.length();
        currData.append(newData);

        return newDataOffset;
    }
}
