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
 * $Id: TemplatesHandlerImpl.java,v 1.2.4.1 2005/09/06 12:09:03 pvedula Exp $
 * <p>
 *  $ Id：TemplatesHandlerImpl.java,v 1.2.4.1 2005/09/06 12:09:03 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.trax;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.TemplatesHandler;
import com.sun.org.apache.xalan.internal.XalanConstants;
import com.sun.org.apache.xalan.internal.xsltc.compiler.CompilerException;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Parser;
import com.sun.org.apache.xalan.internal.xsltc.compiler.SourceLoader;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Stylesheet;
import com.sun.org.apache.xalan.internal.xsltc.compiler.SyntaxTreeNode;
import com.sun.org.apache.xalan.internal.xsltc.compiler.XSLTC;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import java.util.Vector;

/**
 * Implementation of a JAXP1.1 TemplatesHandler
 * <p>
 *  实现JAXP1.1 TemplatesHandler
 * 
 * 
 * @author Morten Jorgensen
 * @author Santiago Pericas-Geertsen
 */
public class TemplatesHandlerImpl
    implements ContentHandler, TemplatesHandler, SourceLoader
{
    /**
     * System ID for this stylesheet.
     * <p>
     *  此样式表的系统ID。
     * 
     */
    private String _systemId;

    /**
     * Number of spaces to add for output indentation.
     * <p>
     *  要为输出缩进添加的空格数。
     * 
     */
    private int _indentNumber;

    /**
     * This URIResolver is passed to all Transformers.
     * <p>
     *  这个URIResolver被传递给所有的变形金刚。
     * 
     */
    private URIResolver _uriResolver = null;

    /**
     * A reference to the transformer factory that this templates
     * object belongs to.
     * <p>
     *  对此模板对象所属的变压器工厂的引用。
     * 
     */
    private TransformerFactoryImpl _tfactory = null;

    /**
     * A reference to XSLTC's parser object.
     * <p>
     *  对XSLTC的解析器对象的引用。
     * 
     */
    private Parser _parser = null;

    /**
     * The created Templates object.
     * <p>
     *  创建的Templates对象。
     * 
     */
    private TemplatesImpl _templates = null;

    /**
     * Default constructor
     * <p>
     *  默认构造函数
     * 
     */
    protected TemplatesHandlerImpl(int indentNumber,
        TransformerFactoryImpl tfactory)
    {
        _indentNumber = indentNumber;
        _tfactory = tfactory;

        // Instantiate XSLTC and get reference to parser object
        XSLTC xsltc = new XSLTC(tfactory.useServicesMechnism(), tfactory.getFeatureManager());
        if (tfactory.getFeature(XMLConstants.FEATURE_SECURE_PROCESSING))
            xsltc.setSecureProcessing(true);

        xsltc.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET,
                (String)tfactory.getAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET));
        xsltc.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD,
                (String)tfactory.getAttribute(XMLConstants.ACCESS_EXTERNAL_DTD));
        xsltc.setProperty(XalanConstants.SECURITY_MANAGER,
                tfactory.getAttribute(XalanConstants.SECURITY_MANAGER));


        if ("true".equals(tfactory.getAttribute(TransformerFactoryImpl.ENABLE_INLINING)))
            xsltc.setTemplateInlining(true);
        else
            xsltc.setTemplateInlining(false);

        _parser = xsltc.getParser();
    }

    /**
     * Implements javax.xml.transform.sax.TemplatesHandler.getSystemId()
     * Get the base ID (URI or system ID) from where relative URLs will be
     * resolved.
     * <p>
     *  实现javax.xml.transform.sax.TemplatesHandler.getSystemId()获取将从中解析相对URL的基本ID(URI或系统ID)。
     * 
     * 
     * @return The systemID that was set with setSystemId(String id)
     */
    public String getSystemId() {
        return _systemId;
    }

    /**
     * Implements javax.xml.transform.sax.TemplatesHandler.setSystemId()
     * Get the base ID (URI or system ID) from where relative URLs will be
     * resolved.
     * <p>
     *  实现javax.xml.transform.sax.TemplatesHandler.setSystemId()获取将从中解析相对URL的基本ID(URI或系统ID)。
     * 
     * 
     * @param id Base URI for this stylesheet
     */
    public void setSystemId(String id) {
        _systemId = id;
    }

    /**
     * Store URIResolver needed for Transformers.
     * <p>
     *  存储变压器所需的URIResolver。
     * 
     */
    public void setURIResolver(URIResolver resolver) {
        _uriResolver = resolver;
    }

    /**
     * Implements javax.xml.transform.sax.TemplatesHandler.getTemplates()
     * When a TemplatesHandler object is used as a ContentHandler or
     * DocumentHandler for the parsing of transformation instructions, it
     * creates a Templates object, which the caller can get once the SAX
     * events have been completed.
     * <p>
     * 实现javax.xml.transform.sax.TemplatesHandler.getTemplates()当一个TemplatesHandler对象用作ContentHandler或Docume
     * ntHandler来解析转换指令时,它会创建一个Templates对象,一旦SAX事件完成,调用者就可以获得。
     * 
     * 
     * @return The Templates object that was created during the SAX event
     *         process, or null if no Templates object has been created.
     */
    public Templates getTemplates() {
        return _templates;
    }

    /**
     * This method implements XSLTC's SourceLoader interface. It is used to
     * glue a TrAX URIResolver to the XSLTC compiler's Input and Import classes.
     *
     * <p>
     *  这个方法实现了XSLTC的SourceLoader接口。它用于将TrAX URIResolver粘合到XSLTC编译器的输入和导入类。
     * 
     * 
     * @param href The URI of the document to load
     * @param context The URI of the currently loaded document
     * @param xsltc The compiler that resuests the document
     * @return An InputSource with the loaded document
     */
    public InputSource loadSource(String href, String context, XSLTC xsltc) {
        try {
            // A _uriResolver must be set if this method is called
            final Source source = _uriResolver.resolve(href, context);
            if (source != null) {
                return Util.getInputSource(xsltc, source);
            }
        }
        catch (TransformerException e) {
            // Falls through
        }
        return null;
    }

    // -- ContentHandler --------------------------------------------------

    /**
     * Re-initialize parser and forward SAX2 event.
     * <p>
     *  重新初始化解析器并转发SAX2事件。
     * 
     */
    public void startDocument() {
        XSLTC xsltc = _parser.getXSLTC();
        xsltc.init();   // calls _parser.init()
        xsltc.setOutputType(XSLTC.BYTEARRAY_OUTPUT);
        _parser.startDocument();
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void endDocument() throws SAXException {
        _parser.endDocument();

        // create the templates
        try {
            XSLTC xsltc = _parser.getXSLTC();

            // Set the translet class name if not already set
            String transletName;
            if (_systemId != null) {
                transletName = Util.baseName(_systemId);
            }
            else {
                transletName = (String)_tfactory.getAttribute("translet-name");
            }
            xsltc.setClassName(transletName);

            // Get java-legal class name from XSLTC module
            transletName = xsltc.getClassName();

            Stylesheet stylesheet = null;
            SyntaxTreeNode root = _parser.getDocumentRoot();

            // Compile the translet - this is where the work is done!
            if (!_parser.errorsFound() && root != null) {
                // Create a Stylesheet element from the root node
                stylesheet = _parser.makeStylesheet(root);
                stylesheet.setSystemId(_systemId);
                stylesheet.setParentStylesheet(null);

                if (xsltc.getTemplateInlining())
                   stylesheet.setTemplateInlining(true);
                else
                   stylesheet.setTemplateInlining(false);

                // Set a document loader (for xsl:include/import) if defined
                if (_uriResolver != null) {
                    stylesheet.setSourceLoader(this);
                }

                _parser.setCurrentStylesheet(stylesheet);

                // Set it as top-level in the XSLTC object
                xsltc.setStylesheet(stylesheet);

                // Create AST under the Stylesheet element
                _parser.createAST(stylesheet);
            }

            // Generate the bytecodes and output the translet class(es)
            if (!_parser.errorsFound() && stylesheet != null) {
                stylesheet.setMultiDocument(xsltc.isMultiDocument());
                stylesheet.setHasIdCall(xsltc.hasIdCall());

                // Class synchronization is needed for BCEL
                synchronized (xsltc.getClass()) {
                    stylesheet.translate();
                }
            }

            if (!_parser.errorsFound()) {
                // Check that the transformation went well before returning
                final byte[][] bytecodes = xsltc.getBytecodes();
                if (bytecodes != null) {
                    _templates =
                    new TemplatesImpl(xsltc.getBytecodes(), transletName,
                        _parser.getOutputProperties(), _indentNumber, _tfactory);

                    // Set URIResolver on templates object
                    if (_uriResolver != null) {
                        _templates.setURIResolver(_uriResolver);
                    }
                }
            }
            else {
                StringBuffer errorMessage = new StringBuffer();
                Vector errors = _parser.getErrors();
                final int count = errors.size();
                for (int i = 0; i < count; i++) {
                    if (errorMessage.length() > 0)
                        errorMessage.append('\n');
                    errorMessage.append(errors.elementAt(i).toString());
                }
                throw new SAXException(ErrorMsg.JAXP_COMPILE_ERR, new TransformerException(errorMessage.toString()));
            }
        }
        catch (CompilerException e) {
            throw new SAXException(ErrorMsg.JAXP_COMPILE_ERR, e);
        }
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void startPrefixMapping(String prefix, String uri) {
        _parser.startPrefixMapping(prefix, uri);
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void endPrefixMapping(String prefix) {
        _parser.endPrefixMapping(prefix);
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void startElement(String uri, String localname, String qname,
        Attributes attributes) throws SAXException
    {
        _parser.startElement(uri, localname, qname, attributes);
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void endElement(String uri, String localname, String qname) {
        _parser.endElement(uri, localname, qname);
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void characters(char[] ch, int start, int length) {
        _parser.characters(ch, start, length);
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void processingInstruction(String name, String value) {
        _parser.processingInstruction(name, value);
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void ignorableWhitespace(char[] ch, int start, int length) {
        _parser.ignorableWhitespace(ch, start, length);
    }

    /**
     * Just forward SAX2 event to parser object.
     * <p>
     *  只是将SAX2事件转发到解析器对象。
     * 
     */
    public void skippedEntity(String name) {
        _parser.skippedEntity(name);
    }

    /**
     * Set internal system Id and forward SAX2 event to parser object.
     * <p>
     *  设置内部系统ID并将SAX2事件转发到解析器对象。
     */
    public void setDocumentLocator(Locator locator) {
        setSystemId(locator.getSystemId());
        _parser.setDocumentLocator(locator);
    }
}
