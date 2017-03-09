/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

// XMLReaderAdapter.java - adapt an SAX2 XMLReader to a SAX1 Parser
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the public domain.
// $Id: XMLReaderAdapter.java,v 1.3 2004/11/03 22:53:09 jsuttor Exp $

package org.xml.sax.helpers;

import java.io.IOException;
import java.util.Locale;

import org.xml.sax.Parser;      // deprecated
import org.xml.sax.Locator;
import org.xml.sax.InputSource;
import org.xml.sax.AttributeList; // deprecated
import org.xml.sax.EntityResolver;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler; // deprecated
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXNotSupportedException;


/**
 * Adapt a SAX2 XMLReader as a SAX1 Parser.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class wraps a SAX2 {@link org.xml.sax.XMLReader XMLReader}
 * and makes it act as a SAX1 {@link org.xml.sax.Parser Parser}.  The XMLReader
 * must support a true value for the
 * http://xml.org/sax/features/namespace-prefixes property or parsing will fail
 * with a {@link org.xml.sax.SAXException SAXException}; if the XMLReader
 * supports a false value for the http://xml.org/sax/features/namespaces
 * property, that will also be used to improve efficiency.</p>
 *
 * <p>
 *  将SAX2 XMLReader适配为SAX1解析器。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>此类包装一个SAX2 {@link org.xml.sax.XMLReader XMLReader},并使其作为SAX1 {@link org.xml.sax.Parser Parser}。
 *  XMLReader必须支持http://xml.org/sax/features/namespace-prefixes属性的真实值,否则解析将失败,并显示{@link org.xml.sax.SAXException SAXException}
 * ;如果XMLReader支持http://xml.org/sax/features/namespaces属性的false值,那么也将用于提高效率。
 *  <p>此类包装一个SAX2 {@link org.xml.sax.XMLReader XMLReader},并使其作为SAX1 {@link org.xml.sax.Parser Parser}。
 * </p>。
 * 
 * 
 * @since SAX 2.0
 * @author David Megginson
 * @see org.xml.sax.Parser
 * @see org.xml.sax.XMLReader
 */
public class XMLReaderAdapter implements Parser, ContentHandler
{


    ////////////////////////////////////////////////////////////////////
    // Constructor.
    ////////////////////////////////////////////////////////////////////


    /**
     * Create a new adapter.
     *
     * <p>Use the "org.xml.sax.driver" property to locate the SAX2
     * driver to embed.</p>
     *
     * <p>
     *  创建新适配器。
     * 
     *  <p>使用"org.xml.sax.driver"属性来定位要嵌入的SAX2驱动程序。</p>
     * 
     * 
     * @exception org.xml.sax.SAXException If the embedded driver
     *            cannot be instantiated or if the
     *            org.xml.sax.driver property is not specified.
     */
    public XMLReaderAdapter ()
      throws SAXException
    {
        setup(XMLReaderFactory.createXMLReader());
    }


    /**
     * Create a new adapter.
     *
     * <p>Create a new adapter, wrapped around a SAX2 XMLReader.
     * The adapter will make the XMLReader act like a SAX1
     * Parser.</p>
     *
     * <p>
     *  创建新适配器。
     * 
     *  <p>创建一个新的适配器,包裹在SAX2 XMLReader周围。适配器将使XMLReader的行为像SAX1解析器。</p>
     * 
     * 
     * @param xmlReader The SAX2 XMLReader to wrap.
     * @exception java.lang.NullPointerException If the argument is null.
     */
    public XMLReaderAdapter (XMLReader xmlReader)
    {
        setup(xmlReader);
    }



    /**
     * Internal setup.
     *
     * <p>
     *  内部设置。
     * 
     * 
     * @param xmlReader The embedded XMLReader.
     */
    private void setup (XMLReader xmlReader)
    {
        if (xmlReader == null) {
            throw new NullPointerException("XMLReader must not be null");
        }
        this.xmlReader = xmlReader;
        qAtts = new AttributesAdapter();
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.Parser.
    ////////////////////////////////////////////////////////////////////


    /**
     * Set the locale for error reporting.
     *
     * <p>This is not supported in SAX2, and will always throw
     * an exception.</p>
     *
     * <p>
     *  设置错误报告的区域设置。
     * 
     *  <p>这在SAX2中不受支持,并且将始终引发异常。</p>
     * 
     * 
     * @param locale the locale for error reporting.
     * @see org.xml.sax.Parser#setLocale
     * @exception org.xml.sax.SAXException Thrown unless overridden.
     */
    public void setLocale (Locale locale)
        throws SAXException
    {
        throw new SAXNotSupportedException("setLocale not supported");
    }


    /**
     * Register the entity resolver.
     *
     * <p>
     *  注册实体解析器。
     * 
     * 
     * @param resolver The new resolver.
     * @see org.xml.sax.Parser#setEntityResolver
     */
    public void setEntityResolver (EntityResolver resolver)
    {
        xmlReader.setEntityResolver(resolver);
    }


    /**
     * Register the DTD event handler.
     *
     * <p>
     *  注册DTD事件处理程序。
     * 
     * 
     * @param handler The new DTD event handler.
     * @see org.xml.sax.Parser#setDTDHandler
     */
    public void setDTDHandler (DTDHandler handler)
    {
        xmlReader.setDTDHandler(handler);
    }


    /**
     * Register the SAX1 document event handler.
     *
     * <p>Note that the SAX1 document handler has no Namespace
     * support.</p>
     *
     * <p>
     *  注册SAX1文档事件处理程序。
     * 
     *  <p>请注意,SAX1文档处理程序没有命名空间支持。</p>
     * 
     * 
     * @param handler The new SAX1 document event handler.
     * @see org.xml.sax.Parser#setDocumentHandler
     */
    public void setDocumentHandler (DocumentHandler handler)
    {
        documentHandler = handler;
    }


    /**
     * Register the error event handler.
     *
     * <p>
     *  注册错误事件处理程序。
     * 
     * 
     * @param handler The new error event handler.
     * @see org.xml.sax.Parser#setErrorHandler
     */
    public void setErrorHandler (ErrorHandler handler)
    {
        xmlReader.setErrorHandler(handler);
    }


    /**
     * Parse the document.
     *
     * <p>This method will throw an exception if the embedded
     * XMLReader does not support the
     * http://xml.org/sax/features/namespace-prefixes property.</p>
     *
     * <p>
     *  解析文档。
     * 
     * <p>如果嵌入的XMLReader不支持http://xml.org/sax/features/namespace-prefixes属性,此方法将抛出异常。</p>
     * 
     * 
     * @param systemId The absolute URL of the document.
     * @exception java.io.IOException If there is a problem reading
     *            the raw content of the document.
     * @exception org.xml.sax.SAXException If there is a problem
     *            processing the document.
     * @see #parse(org.xml.sax.InputSource)
     * @see org.xml.sax.Parser#parse(java.lang.String)
     */
    public void parse (String systemId)
        throws IOException, SAXException
    {
        parse(new InputSource(systemId));
    }


    /**
     * Parse the document.
     *
     * <p>This method will throw an exception if the embedded
     * XMLReader does not support the
     * http://xml.org/sax/features/namespace-prefixes property.</p>
     *
     * <p>
     *  解析文档。
     * 
     *  <p>如果嵌入的XMLReader不支持http://xml.org/sax/features/namespace-prefixes属性,此方法将抛出异常。</p>
     * 
     * 
     * @param input An input source for the document.
     * @exception java.io.IOException If there is a problem reading
     *            the raw content of the document.
     * @exception org.xml.sax.SAXException If there is a problem
     *            processing the document.
     * @see #parse(java.lang.String)
     * @see org.xml.sax.Parser#parse(org.xml.sax.InputSource)
     */
    public void parse (InputSource input)
        throws IOException, SAXException
    {
        setupXMLReader();
        xmlReader.parse(input);
    }


    /**
     * Set up the XML reader.
     * <p>
     *  设置XML读取器。
     * 
     */
    private void setupXMLReader ()
        throws SAXException
    {
        xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
        try {
            xmlReader.setFeature("http://xml.org/sax/features/namespaces",
                                 false);
        } catch (SAXException e) {
            // NO OP: it's just extra information, and we can ignore it
        }
        xmlReader.setContentHandler(this);
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.ContentHandler.
    ////////////////////////////////////////////////////////////////////


    /**
     * Set a document locator.
     *
     * <p>
     *  设置文档定位器。
     * 
     * 
     * @param locator The document locator.
     * @see org.xml.sax.ContentHandler#setDocumentLocator
     */
    public void setDocumentLocator (Locator locator)
    {
        if (documentHandler != null)
            documentHandler.setDocumentLocator(locator);
    }


    /**
     * Start document event.
     *
     * <p>
     *  启动文档事件。
     * 
     * 
     * @exception org.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.ContentHandler#startDocument
     */
    public void startDocument ()
        throws SAXException
    {
        if (documentHandler != null)
            documentHandler.startDocument();
    }


    /**
     * End document event.
     *
     * <p>
     *  结束文档事件。
     * 
     * 
     * @exception org.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.ContentHandler#endDocument
     */
    public void endDocument ()
        throws SAXException
    {
        if (documentHandler != null)
            documentHandler.endDocument();
    }


    /**
     * Adapt a SAX2 start prefix mapping event.
     *
     * <p>
     *  修改SAX2开始前缀映射事件。
     * 
     * 
     * @param prefix The prefix being mapped.
     * @param uri The Namespace URI being mapped to.
     * @see org.xml.sax.ContentHandler#startPrefixMapping
     */
    public void startPrefixMapping (String prefix, String uri)
    {
    }


    /**
     * Adapt a SAX2 end prefix mapping event.
     *
     * <p>
     *  修改SAX2结束前缀映射事件。
     * 
     * 
     * @param prefix The prefix being mapped.
     * @see org.xml.sax.ContentHandler#endPrefixMapping
     */
    public void endPrefixMapping (String prefix)
    {
    }


    /**
     * Adapt a SAX2 start element event.
     *
     * <p>
     *  修改SAX2 start元素事件。
     * 
     * 
     * @param uri The Namespace URI.
     * @param localName The Namespace local name.
     * @param qName The qualified (prefixed) name.
     * @param atts The SAX2 attributes.
     * @exception org.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.ContentHandler#endDocument
     */
    public void startElement (String uri, String localName,
                              String qName, Attributes atts)
        throws SAXException
    {
        if (documentHandler != null) {
            qAtts.setAttributes(atts);
            documentHandler.startElement(qName, qAtts);
        }
    }


    /**
     * Adapt a SAX2 end element event.
     *
     * <p>
     *  修改SAX2结束元素事件。
     * 
     * 
     * @param uri The Namespace URI.
     * @param localName The Namespace local name.
     * @param qName The qualified (prefixed) name.
     * @exception org.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.ContentHandler#endElement
     */
    public void endElement (String uri, String localName,
                            String qName)
        throws SAXException
    {
        if (documentHandler != null)
            documentHandler.endElement(qName);
    }


    /**
     * Adapt a SAX2 characters event.
     *
     * <p>
     *  修改SAX2字符事件。
     * 
     * 
     * @param ch An array of characters.
     * @param start The starting position in the array.
     * @param length The number of characters to use.
     * @exception org.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.ContentHandler#characters
     */
    public void characters (char ch[], int start, int length)
        throws SAXException
    {
        if (documentHandler != null)
            documentHandler.characters(ch, start, length);
    }


    /**
     * Adapt a SAX2 ignorable whitespace event.
     *
     * <p>
     *  修改SAX2可忽略的空格事件。
     * 
     * 
     * @param ch An array of characters.
     * @param start The starting position in the array.
     * @param length The number of characters to use.
     * @exception org.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.ContentHandler#ignorableWhitespace
     */
    public void ignorableWhitespace (char ch[], int start, int length)
        throws SAXException
    {
        if (documentHandler != null)
            documentHandler.ignorableWhitespace(ch, start, length);
    }


    /**
     * Adapt a SAX2 processing instruction event.
     *
     * <p>
     *  修改SAX2处理指令事件。
     * 
     * 
     * @param target The processing instruction target.
     * @param data The remainder of the processing instruction
     * @exception org.xml.sax.SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.ContentHandler#processingInstruction
     */
    public void processingInstruction (String target, String data)
        throws SAXException
    {
        if (documentHandler != null)
            documentHandler.processingInstruction(target, data);
    }


    /**
     * Adapt a SAX2 skipped entity event.
     *
     * <p>
     *  修改SAX2跳过的实体事件。
     * 
     * 
     * @param name The name of the skipped entity.
     * @see org.xml.sax.ContentHandler#skippedEntity
     * @exception org.xml.sax.SAXException Throwable by subclasses.
     */
    public void skippedEntity (String name)
        throws SAXException
    {
    }



    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    XMLReader xmlReader;
    DocumentHandler documentHandler;
    AttributesAdapter qAtts;



    ////////////////////////////////////////////////////////////////////
    // Internal class.
    ////////////////////////////////////////////////////////////////////


    /**
     * Internal class to wrap a SAX2 Attributes object for SAX1.
     * <p>
     *  用于封装SAX1的SAX2属性对象的内部类。
     * 
     */
    final class AttributesAdapter implements AttributeList
    {
        AttributesAdapter ()
        {
        }


        /**
         * Set the embedded Attributes object.
         *
         * <p>
         *  设置嵌入的Attributes对象。
         * 
         * 
         * @param The embedded SAX2 Attributes.
         */
        void setAttributes (Attributes attributes)
        {
            this.attributes = attributes;
        }


        /**
         * Return the number of attributes.
         *
         * <p>
         *  返回属性的数量。
         * 
         * 
         * @return The length of the attribute list.
         * @see org.xml.sax.AttributeList#getLength
         */
        public int getLength ()
        {
            return attributes.getLength();
        }


        /**
         * Return the qualified (prefixed) name of an attribute by position.
         *
         * <p>
         *  按位置返回属性的限定(前缀)名称。
         * 
         * 
         * @return The qualified name.
         * @see org.xml.sax.AttributeList#getName
         */
        public String getName (int i)
        {
            return attributes.getQName(i);
        }


        /**
         * Return the type of an attribute by position.
         *
         * <p>
         *  按位置返回属性的类型。
         * 
         * 
         * @return The type.
         * @see org.xml.sax.AttributeList#getType(int)
         */
        public String getType (int i)
        {
            return attributes.getType(i);
        }


        /**
         * Return the value of an attribute by position.
         *
         * <p>
         *  按位置返回属性的值。
         * 
         * 
         * @return The value.
         * @see org.xml.sax.AttributeList#getValue(int)
         */
        public String getValue (int i)
        {
            return attributes.getValue(i);
        }


        /**
         * Return the type of an attribute by qualified (prefixed) name.
         *
         * <p>
         *  通过限定(前缀)名称返回属性的类型。
         * 
         * 
         * @return The type.
         * @see org.xml.sax.AttributeList#getType(java.lang.String)
         */
        public String getType (String qName)
        {
            return attributes.getType(qName);
        }


        /**
         * Return the value of an attribute by qualified (prefixed) name.
         *
         * <p>
         *  通过限定(前缀)名称返回属性的值。
         * 
         * @return The value.
         * @see org.xml.sax.AttributeList#getValue(java.lang.String)
         */
        public String getValue (String qName)
        {
            return attributes.getValue(qName);
        }

        private Attributes attributes;
    }

}

// end of XMLReaderAdapter.java
