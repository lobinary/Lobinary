/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

// ParserAdapter.java - adapt a SAX1 Parser to a SAX2 XMLReader.
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the public domain.
// $Id: ParserAdapter.java,v 1.3 2004/11/03 22:53:09 jsuttor Exp $

package org.xml.sax.helpers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import org.xml.sax.Parser;      // deprecated
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.AttributeList; // deprecated
import org.xml.sax.EntityResolver;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler; // deprecated
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.xml.sax.XMLReader;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;


/**
 * Adapt a SAX1 Parser as a SAX2 XMLReader.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class wraps a SAX1 {@link org.xml.sax.Parser Parser}
 * and makes it act as a SAX2 {@link org.xml.sax.XMLReader XMLReader},
 * with feature, property, and Namespace support.  Note
 * that it is not possible to report {@link org.xml.sax.ContentHandler#skippedEntity
 * skippedEntity} events, since SAX1 does not make that information available.</p>
 *
 * <p>This adapter does not test for duplicate Namespace-qualified
 * attribute names.</p>
 *
 * <p>
 *  将SAX1解析器适配为SAX2 XMLReader。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>此类封装了SAX1 {@link org.xml.sax.Parser Parser},并使其作为SAX2 {@link org.xml.sax.XMLReader XMLReader},并支持
 * feature,property和Namespace。
 * 请注意,由于SAX1不会提供此信息,因此无法报告{@link org.xml.sax.ContentHandler#skippedEntity skippedEntity}事件。</p>。
 * 
 *  <p>此适配器不会测试重复的命名空间限定的属性名称。</p>
 * 
 * 
 * @since SAX 2.0
 * @author David Megginson
 * @version 2.0.1 (sax2r2)
 * @see org.xml.sax.helpers.XMLReaderAdapter
 * @see org.xml.sax.XMLReader
 * @see org.xml.sax.Parser
 */
public class ParserAdapter implements XMLReader, DocumentHandler
{
    private static SecuritySupport ss = new SecuritySupport();

    ////////////////////////////////////////////////////////////////////
    // Constructors.
    ////////////////////////////////////////////////////////////////////


    /**
     * Construct a new parser adapter.
     *
     * <p>Use the "org.xml.sax.parser" property to locate the
     * embedded SAX1 driver.</p>
     *
     * <p>
     *  构造一个新的解析器适配器。
     * 
     *  <p>使用"org.xml.sax.parser"属性来定位嵌入式SAX1驱动程序。</p>
     * 
     * 
     * @exception SAXException If the embedded driver
     *            cannot be instantiated or if the
     *            org.xml.sax.parser property is not specified.
     */
    public ParserAdapter ()
      throws SAXException
    {
        super();

        String driver = ss.getSystemProperty("org.xml.sax.parser");

        try {
            setup(ParserFactory.makeParser());
        } catch (ClassNotFoundException e1) {
            throw new
                SAXException("Cannot find SAX1 driver class " +
                             driver, e1);
        } catch (IllegalAccessException e2) {
            throw new
                SAXException("SAX1 driver class " +
                             driver +
                             " found but cannot be loaded", e2);
        } catch (InstantiationException e3) {
            throw new
                SAXException("SAX1 driver class " +
                             driver +
                             " loaded but cannot be instantiated", e3);
        } catch (ClassCastException e4) {
            throw new
                SAXException("SAX1 driver class " +
                             driver +
                             " does not implement org.xml.sax.Parser");
        } catch (NullPointerException e5) {
            throw new
                SAXException("System property org.xml.sax.parser not specified");
        }
    }


    /**
     * Construct a new parser adapter.
     *
     * <p>Note that the embedded parser cannot be changed once the
     * adapter is created; to embed a different parser, allocate
     * a new ParserAdapter.</p>
     *
     * <p>
     *  构造一个新的解析器适配器。
     * 
     *  <p>请注意,创建适配器后,无法更改嵌入式解析器;嵌入一​​个不同的解析器,分配一个新的ParserAdapter。</p>
     * 
     * 
     * @param parser The SAX1 parser to embed.
     * @exception java.lang.NullPointerException If the parser parameter
     *            is null.
     */
    public ParserAdapter (Parser parser)
    {
        super();
        setup(parser);
    }


    /**
     * Internal setup method.
     *
     * <p>
     *  内部设置方法。
     * 
     * 
     * @param parser The embedded parser.
     * @exception java.lang.NullPointerException If the parser parameter
     *            is null.
     */
    private void setup (Parser parser)
    {
        if (parser == null) {
            throw new
                NullPointerException("Parser argument must not be null");
        }
        this.parser = parser;
        atts = new AttributesImpl();
        nsSupport = new NamespaceSupport();
        attAdapter = new AttributeListAdapter();
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.XMLReader.
    ////////////////////////////////////////////////////////////////////


    //
    // Internal constants for the sake of convenience.
    //
    private final static String FEATURES = "http://xml.org/sax/features/";
    private final static String NAMESPACES = FEATURES + "namespaces";
    private final static String NAMESPACE_PREFIXES = FEATURES + "namespace-prefixes";
    private final static String XMLNS_URIs = FEATURES + "xmlns-uris";


    /**
     * Set a feature flag for the parser.
     *
     * <p>The only features recognized are namespaces and
     * namespace-prefixes.</p>
     *
     * <p>
     *  为解析器设置一个功能标志。
     * 
     *  <p>唯一可识别的功能是命名空间和命名空间前缀。</p>
     * 
     * 
     * @param name The feature name, as a complete URI.
     * @param value The requested feature value.
     * @exception SAXNotRecognizedException If the feature
     *            can't be assigned or retrieved.
     * @exception SAXNotSupportedException If the feature
     *            can't be assigned that value.
     * @see org.xml.sax.XMLReader#setFeature
     */
    public void setFeature (String name, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        if (name.equals(NAMESPACES)) {
            checkNotParsing("feature", name);
            namespaces = value;
            if (!namespaces && !prefixes) {
                prefixes = true;
            }
        } else if (name.equals(NAMESPACE_PREFIXES)) {
            checkNotParsing("feature", name);
            prefixes = value;
            if (!prefixes && !namespaces) {
                namespaces = true;
            }
        } else if (name.equals(XMLNS_URIs)) {
            checkNotParsing("feature", name);
            uris = value;
        } else {
            throw new SAXNotRecognizedException("Feature: " + name);
        }
    }


    /**
     * Check a parser feature flag.
     *
     * <p>The only features recognized are namespaces and
     * namespace-prefixes.</p>
     *
     * <p>
     *  检查解析器功能标志。
     * 
     *  <p>唯一可识别的功能是命名空间和命名空间前缀。</p>
     * 
     * 
     * @param name The feature name, as a complete URI.
     * @return The current feature value.
     * @exception SAXNotRecognizedException If the feature
     *            value can't be assigned or retrieved.
     * @exception SAXNotSupportedException If the
     *            feature is not currently readable.
     * @see org.xml.sax.XMLReader#setFeature
     */
    public boolean getFeature (String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        if (name.equals(NAMESPACES)) {
            return namespaces;
        } else if (name.equals(NAMESPACE_PREFIXES)) {
            return prefixes;
        } else if (name.equals(XMLNS_URIs)) {
            return uris;
        } else {
            throw new SAXNotRecognizedException("Feature: " + name);
        }
    }


    /**
     * Set a parser property.
     *
     * <p>No properties are currently recognized.</p>
     *
     * <p>
     *  设置解析器属性。
     * 
     *  <p>目前未识别任何属性。</p>
     * 
     * 
     * @param name The property name.
     * @param value The property value.
     * @exception SAXNotRecognizedException If the property
     *            value can't be assigned or retrieved.
     * @exception SAXNotSupportedException If the property
     *            can't be assigned that value.
     * @see org.xml.sax.XMLReader#setProperty
     */
    public void setProperty (String name, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new SAXNotRecognizedException("Property: " + name);
    }


    /**
     * Get a parser property.
     *
     * <p>No properties are currently recognized.</p>
     *
     * <p>
     *  获取解析器属性。
     * 
     * <p>目前未识别任何属性。</p>
     * 
     * 
     * @param name The property name.
     * @return The property value.
     * @exception SAXNotRecognizedException If the property
     *            value can't be assigned or retrieved.
     * @exception SAXNotSupportedException If the property
     *            value is not currently readable.
     * @see org.xml.sax.XMLReader#getProperty
     */
    public Object getProperty (String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        throw new SAXNotRecognizedException("Property: " + name);
    }


    /**
     * Set the entity resolver.
     *
     * <p>
     *  设置实体解析器。
     * 
     * 
     * @param resolver The new entity resolver.
     * @see org.xml.sax.XMLReader#setEntityResolver
     */
    public void setEntityResolver (EntityResolver resolver)
    {
        entityResolver = resolver;
    }


    /**
     * Return the current entity resolver.
     *
     * <p>
     *  返回当前实体解析器。
     * 
     * 
     * @return The current entity resolver, or null if none was supplied.
     * @see org.xml.sax.XMLReader#getEntityResolver
     */
    public EntityResolver getEntityResolver ()
    {
        return entityResolver;
    }


    /**
     * Set the DTD handler.
     *
     * <p>
     *  设置DTD处理程序。
     * 
     * 
     * @param handler the new DTD handler
     * @see org.xml.sax.XMLReader#setEntityResolver
     */
    public void setDTDHandler (DTDHandler handler)
    {
        dtdHandler = handler;
    }


    /**
     * Return the current DTD handler.
     *
     * <p>
     *  返回当前DTD处理程序。
     * 
     * 
     * @return the current DTD handler, or null if none was supplied
     * @see org.xml.sax.XMLReader#getEntityResolver
     */
    public DTDHandler getDTDHandler ()
    {
        return dtdHandler;
    }


    /**
     * Set the content handler.
     *
     * <p>
     *  设置内容处理程序。
     * 
     * 
     * @param handler the new content handler
     * @see org.xml.sax.XMLReader#setEntityResolver
     */
    public void setContentHandler (ContentHandler handler)
    {
        contentHandler = handler;
    }


    /**
     * Return the current content handler.
     *
     * <p>
     *  返回当前内容处理程序。
     * 
     * 
     * @return The current content handler, or null if none was supplied.
     * @see org.xml.sax.XMLReader#getEntityResolver
     */
    public ContentHandler getContentHandler ()
    {
        return contentHandler;
    }


    /**
     * Set the error handler.
     *
     * <p>
     *  设置错误处理程序。
     * 
     * 
     * @param handler The new error handler.
     * @see org.xml.sax.XMLReader#setEntityResolver
     */
    public void setErrorHandler (ErrorHandler handler)
    {
        errorHandler = handler;
    }


    /**
     * Return the current error handler.
     *
     * <p>
     *  返回当前错误处理程序。
     * 
     * 
     * @return The current error handler, or null if none was supplied.
     * @see org.xml.sax.XMLReader#getEntityResolver
     */
    public ErrorHandler getErrorHandler ()
    {
        return errorHandler;
    }


    /**
     * Parse an XML document.
     *
     * <p>
     *  解析XML文档。
     * 
     * 
     * @param systemId The absolute URL of the document.
     * @exception java.io.IOException If there is a problem reading
     *            the raw content of the document.
     * @exception SAXException If there is a problem
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
     * Parse an XML document.
     *
     * <p>
     *  解析XML文档。
     * 
     * 
     * @param input An input source for the document.
     * @exception java.io.IOException If there is a problem reading
     *            the raw content of the document.
     * @exception SAXException If there is a problem
     *            processing the document.
     * @see #parse(java.lang.String)
     * @see org.xml.sax.Parser#parse(org.xml.sax.InputSource)
     */
    public void parse (InputSource input)
        throws IOException, SAXException
    {
        if (parsing) {
            throw new SAXException("Parser is already in use");
        }
        setupParser();
        parsing = true;
        try {
            parser.parse(input);
        } finally {
            parsing = false;
        }
        parsing = false;
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.DocumentHandler.
    ////////////////////////////////////////////////////////////////////


    /**
     * Adapter implementation method; do not call.
     * Adapt a SAX1 document locator event.
     *
     * <p>
     *  适配器实现方法;不要打电话。修改SAX1文档定位器事件。
     * 
     * 
     * @param locator A document locator.
     * @see org.xml.sax.ContentHandler#setDocumentLocator
     */
    public void setDocumentLocator (Locator locator)
    {
        this.locator = locator;
        if (contentHandler != null) {
            contentHandler.setDocumentLocator(locator);
        }
    }


    /**
     * Adapter implementation method; do not call.
     * Adapt a SAX1 start document event.
     *
     * <p>
     *  适配器实现方法;不要打电话。修改SAX1开始文档事件。
     * 
     * 
     * @exception SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.DocumentHandler#startDocument
     */
    public void startDocument ()
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.startDocument();
        }
    }


    /**
     * Adapter implementation method; do not call.
     * Adapt a SAX1 end document event.
     *
     * <p>
     *  适配器实现方法;不要打电话。调整SAX1结束文档事件。
     * 
     * 
     * @exception SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.DocumentHandler#endDocument
     */
    public void endDocument ()
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.endDocument();
        }
    }


    /**
     * Adapter implementation method; do not call.
     * Adapt a SAX1 startElement event.
     *
     * <p>If necessary, perform Namespace processing.</p>
     *
     * <p>
     *  适配器实现方法;不要打电话。修改SAX1 startElement事件。
     * 
     *  <p>如有必要,请执行命名空间处理。</p>
     * 
     * 
     * @param qName The qualified (prefixed) name.
     * @param qAtts The XML attribute list (with qnames).
     * @exception SAXException The client may raise a
     *            processing exception.
     */
    public void startElement (String qName, AttributeList qAtts)
        throws SAXException
    {
                                // These are exceptions from the
                                // first pass; they should be
                                // ignored if there's a second pass,
                                // but reported otherwise.
        Vector exceptions = null;

                                // If we're not doing Namespace
                                // processing, dispatch this quickly.
        if (!namespaces) {
            if (contentHandler != null) {
                attAdapter.setAttributeList(qAtts);
                contentHandler.startElement("", "", qName.intern(),
                                            attAdapter);
            }
            return;
        }


                                // OK, we're doing Namespace processing.
        nsSupport.pushContext();
        int length = qAtts.getLength();

                                // First pass:  handle NS decls
        for (int i = 0; i < length; i++) {
            String attQName = qAtts.getName(i);

            if (!attQName.startsWith("xmlns"))
                continue;
                                // Could be a declaration...
            String prefix;
            int n = attQName.indexOf(':');

                                // xmlns=...
            if (n == -1 && attQName.length () == 5) {
                prefix = "";
            } else if (n != 5) {
                // XML namespaces spec doesn't discuss "xmlnsf:oo"
                // (and similarly named) attributes ... at most, warn
                continue;
            } else              // xmlns:foo=...
                prefix = attQName.substring(n+1);

            String value = qAtts.getValue(i);
            if (!nsSupport.declarePrefix(prefix, value)) {
                reportError("Illegal Namespace prefix: " + prefix);
                continue;
            }
            if (contentHandler != null)
                contentHandler.startPrefixMapping(prefix, value);
        }

                                // Second pass: copy all relevant
                                // attributes into the SAX2 AttributeList
                                // using updated prefix bindings
        atts.clear();
        for (int i = 0; i < length; i++) {
            String attQName = qAtts.getName(i);
            String type = qAtts.getType(i);
            String value = qAtts.getValue(i);

                                // Declaration?
            if (attQName.startsWith("xmlns")) {
                String prefix;
                int n = attQName.indexOf(':');

                if (n == -1 && attQName.length () == 5) {
                    prefix = "";
                } else if (n != 5) {
                    // XML namespaces spec doesn't discuss "xmlnsf:oo"
                    // (and similarly named) attributes ... ignore
                    prefix = null;
                } else {
                    prefix = attQName.substring(6);
                }
                                // Yes, decl:  report or prune
                if (prefix != null) {
                    if (prefixes) {
                        if (uris)
                            // note funky case:  localname can be null
                            // when declaring the default prefix, and
                            // yet the uri isn't null.
                            atts.addAttribute (nsSupport.XMLNS, prefix,
                                    attQName.intern(), type, value);
                        else
                            atts.addAttribute ("", "",
                                    attQName.intern(), type, value);
                    }
                    continue;
                }
            }

                                // Not a declaration -- report
            try {
                String attName[] = processName(attQName, true, true);
                atts.addAttribute(attName[0], attName[1], attName[2],
                                  type, value);
            } catch (SAXException e) {
                if (exceptions == null)
                    exceptions = new Vector();
                exceptions.addElement(e);
                atts.addAttribute("", attQName, attQName, type, value);
            }
        }

        // now handle the deferred exception reports
        if (exceptions != null && errorHandler != null) {
            for (int i = 0; i < exceptions.size(); i++)
                errorHandler.error((SAXParseException)
                                (exceptions.elementAt(i)));
        }

                                // OK, finally report the event.
        if (contentHandler != null) {
            String name[] = processName(qName, false, false);
            contentHandler.startElement(name[0], name[1], name[2], atts);
        }
    }


    /**
     * Adapter implementation method; do not call.
     * Adapt a SAX1 end element event.
     *
     * <p>
     *  适配器实现方法;不要打电话。修改SAX1结束元素事件。
     * 
     * 
     * @param qName The qualified (prefixed) name.
     * @exception SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.DocumentHandler#endElement
     */
    public void endElement (String qName)
        throws SAXException
    {
                                // If we're not doing Namespace
                                // processing, dispatch this quickly.
        if (!namespaces) {
            if (contentHandler != null) {
                contentHandler.endElement("", "", qName.intern());
            }
            return;
        }

                                // Split the name.
        String names[] = processName(qName, false, false);
        if (contentHandler != null) {
            contentHandler.endElement(names[0], names[1], names[2]);
            Enumeration prefixes = nsSupport.getDeclaredPrefixes();
            while (prefixes.hasMoreElements()) {
                String prefix = (String)prefixes.nextElement();
                contentHandler.endPrefixMapping(prefix);
            }
        }
        nsSupport.popContext();
    }


    /**
     * Adapter implementation method; do not call.
     * Adapt a SAX1 characters event.
     *
     * <p>
     *  适配器实现方法;不要打电话。修改SAX1字符事件。
     * 
     * 
     * @param ch An array of characters.
     * @param start The starting position in the array.
     * @param length The number of characters to use.
     * @exception SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.DocumentHandler#characters
     */
    public void characters (char ch[], int start, int length)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.characters(ch, start, length);
        }
    }


    /**
     * Adapter implementation method; do not call.
     * Adapt a SAX1 ignorable whitespace event.
     *
     * <p>
     *  适配器实现方法;不要打电话。修改SAX1可忽略的空格事件。
     * 
     * 
     * @param ch An array of characters.
     * @param start The starting position in the array.
     * @param length The number of characters to use.
     * @exception SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.DocumentHandler#ignorableWhitespace
     */
    public void ignorableWhitespace (char ch[], int start, int length)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.ignorableWhitespace(ch, start, length);
        }
    }


    /**
     * Adapter implementation method; do not call.
     * Adapt a SAX1 processing instruction event.
     *
     * <p>
     *  适配器实现方法;不要打电话。修改SAX1处理指令事件。
     * 
     * 
     * @param target The processing instruction target.
     * @param data The remainder of the processing instruction
     * @exception SAXException The client may raise a
     *            processing exception.
     * @see org.xml.sax.DocumentHandler#processingInstruction
     */
    public void processingInstruction (String target, String data)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.processingInstruction(target, data);
        }
    }



    ////////////////////////////////////////////////////////////////////
    // Internal utility methods.
    ////////////////////////////////////////////////////////////////////


    /**
     * Initialize the parser before each run.
     * <p>
     *  在每次运行之前初始化解析器。
     * 
     */
    private void setupParser ()
    {
        // catch an illegal "nonsense" state.
        if (!prefixes && !namespaces)
            throw new IllegalStateException ();

        nsSupport.reset();
        if (uris)
            nsSupport.setNamespaceDeclUris (true);

        if (entityResolver != null) {
            parser.setEntityResolver(entityResolver);
        }
        if (dtdHandler != null) {
            parser.setDTDHandler(dtdHandler);
        }
        if (errorHandler != null) {
            parser.setErrorHandler(errorHandler);
        }
        parser.setDocumentHandler(this);
        locator = null;
    }


    /**
     * Process a qualified (prefixed) name.
     *
     * <p>If the name has an undeclared prefix, use only the qname
     * and make an ErrorHandler.error callback in case the app is
     * interested.</p>
     *
     * <p>
     *  处理限定(前缀)名称。
     * 
     *  <p>如果名称具有未声明的前缀,请仅使用qname,并在应用程序感兴趣的情况下进行ErrorHandler.error回调。</p>
     * 
     * 
     * @param qName The qualified (prefixed) name.
     * @param isAttribute true if this is an attribute name.
     * @return The name split into three parts.
     * @exception SAXException The client may throw
     *            an exception if there is an error callback.
     */
    private String [] processName (String qName, boolean isAttribute,
                                   boolean useException)
        throws SAXException
    {
        String parts[] = nsSupport.processName(qName, nameParts,
                                               isAttribute);
        if (parts == null) {
            if (useException)
                throw makeException("Undeclared prefix: " + qName);
            reportError("Undeclared prefix: " + qName);
            parts = new String[3];
            parts[0] = parts[1] = "";
            parts[2] = qName.intern();
        }
        return parts;
    }


    /**
     * Report a non-fatal error.
     *
     * <p>
     *  报告非致命错误。
     * 
     * 
     * @param message The error message.
     * @exception SAXException The client may throw
     *            an exception.
     */
    void reportError (String message)
        throws SAXException
    {
        if (errorHandler != null)
            errorHandler.error(makeException(message));
    }


    /**
     * Construct an exception for the current context.
     *
     * <p>
     *  为当前上下文构造异常。
     * 
     * 
     * @param message The error message.
     */
    private SAXParseException makeException (String message)
    {
        if (locator != null) {
            return new SAXParseException(message, locator);
        } else {
            return new SAXParseException(message, null, null, -1, -1);
        }
    }


    /**
     * Throw an exception if we are parsing.
     *
     * <p>Use this method to detect illegal feature or
     * property changes.</p>
     *
     * <p>
     *  如果我们正在解析,则抛出异常。
     * 
     *  <p>使用此方法检测非法的地图项或属性变更。</p>
     * 
     * 
     * @param type The type of thing (feature or property).
     * @param name The feature or property name.
     * @exception SAXNotSupportedException If a
     *            document is currently being parsed.
     */
    private void checkNotParsing (String type, String name)
        throws SAXNotSupportedException
    {
        if (parsing) {
            throw new SAXNotSupportedException("Cannot change " +
                                               type + ' ' +
                                               name + " while parsing");

        }
    }



    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    private NamespaceSupport nsSupport;
    private AttributeListAdapter attAdapter;

    private boolean parsing = false;
    private String nameParts[] = new String[3];

    private Parser parser = null;

    private AttributesImpl atts = null;

                                // Features
    private boolean namespaces = true;
    private boolean prefixes = false;
    private boolean uris = false;

                                // Properties

                                // Handlers
    Locator locator;

    EntityResolver entityResolver = null;
    DTDHandler dtdHandler = null;
    ContentHandler contentHandler = null;
    ErrorHandler errorHandler = null;



    ////////////////////////////////////////////////////////////////////
    // Inner class to wrap an AttributeList when not doing NS proc.
    ////////////////////////////////////////////////////////////////////


    /**
     * Adapt a SAX1 AttributeList as a SAX2 Attributes object.
     *
     * <p>This class is in the Public Domain, and comes with NO
     * WARRANTY of any kind.</p>
     *
     * <p>This wrapper class is used only when Namespace support
     * is disabled -- it provides pretty much a direct mapping
     * from SAX1 to SAX2, except that names and types are
     * interned whenever requested.</p>
     * <p>
     *  将SAX1属性列作为SAX2属性对象。
     * 
     * <p>此课程位于公共领域,不附带任何形式的保证。</p>
     * 
     *  <p>此封装类仅在禁用命名空间支持时使用 - 它提供了从SAX1到SAX2的几乎直接映射,除了名称和类型在请求时被内置。</p>
     * 
     */
    final class AttributeListAdapter implements Attributes
    {

        /**
         * Construct a new adapter.
         * <p>
         *  构造新适配器。
         * 
         */
        AttributeListAdapter ()
        {
        }


        /**
         * Set the embedded AttributeList.
         *
         * <p>This method must be invoked before any of the others
         * can be used.</p>
         *
         * <p>
         *  设置嵌入的AttributeList。
         * 
         *  <p>必须先调用此方法,才能使用其他任何方法。</p>
         * 
         * 
         * @param The SAX1 attribute list (with qnames).
         */
        void setAttributeList (AttributeList qAtts)
        {
            this.qAtts = qAtts;
        }


        /**
         * Return the length of the attribute list.
         *
         * <p>
         *  返回属性列表的长度。
         * 
         * 
         * @return The number of attributes in the list.
         * @see org.xml.sax.Attributes#getLength
         */
        public int getLength ()
        {
            return qAtts.getLength();
        }


        /**
         * Return the Namespace URI of the specified attribute.
         *
         * <p>
         *  返回指定属性的命名空间URI。
         * 
         * 
         * @param The attribute's index.
         * @return Always the empty string.
         * @see org.xml.sax.Attributes#getURI
         */
        public String getURI (int i)
        {
            return "";
        }


        /**
         * Return the local name of the specified attribute.
         *
         * <p>
         *  返回指定属性的本地名称。
         * 
         * 
         * @param The attribute's index.
         * @return Always the empty string.
         * @see org.xml.sax.Attributes#getLocalName
         */
        public String getLocalName (int i)
        {
            return "";
        }


        /**
         * Return the qualified (prefixed) name of the specified attribute.
         *
         * <p>
         *  返回指定属性的限定(前缀)名称。
         * 
         * 
         * @param The attribute's index.
         * @return The attribute's qualified name, internalized.
         */
        public String getQName (int i)
        {
            return qAtts.getName(i).intern();
        }


        /**
         * Return the type of the specified attribute.
         *
         * <p>
         *  返回指定属性的类型。
         * 
         * 
         * @param The attribute's index.
         * @return The attribute's type as an internalized string.
         */
        public String getType (int i)
        {
            return qAtts.getType(i).intern();
        }


        /**
         * Return the value of the specified attribute.
         *
         * <p>
         *  返回指定属性的值。
         * 
         * 
         * @param The attribute's index.
         * @return The attribute's value.
         */
        public String getValue (int i)
        {
            return qAtts.getValue(i);
        }


        /**
         * Look up an attribute index by Namespace name.
         *
         * <p>
         *  按名称空间名称查找属性索引。
         * 
         * 
         * @param uri The Namespace URI or the empty string.
         * @param localName The local name.
         * @return The attributes index, or -1 if none was found.
         * @see org.xml.sax.Attributes#getIndex(java.lang.String,java.lang.String)
         */
        public int getIndex (String uri, String localName)
        {
            return -1;
        }


        /**
         * Look up an attribute index by qualified (prefixed) name.
         *
         * <p>
         *  通过限定(前缀)名称查找属性索引。
         * 
         * 
         * @param qName The qualified name.
         * @return The attributes index, or -1 if none was found.
         * @see org.xml.sax.Attributes#getIndex(java.lang.String)
         */
        public int getIndex (String qName)
        {
            int max = atts.getLength();
            for (int i = 0; i < max; i++) {
                if (qAtts.getName(i).equals(qName)) {
                    return i;
                }
            }
            return -1;
        }


        /**
         * Look up the type of an attribute by Namespace name.
         *
         * <p>
         *  按名称空间名称查找属性的类型。
         * 
         * 
         * @param uri The Namespace URI
         * @param localName The local name.
         * @return The attribute's type as an internalized string.
         */
        public String getType (String uri, String localName)
        {
            return null;
        }


        /**
         * Look up the type of an attribute by qualified (prefixed) name.
         *
         * <p>
         *  通过限定(前缀)名称查找属性的类型。
         * 
         * 
         * @param qName The qualified name.
         * @return The attribute's type as an internalized string.
         */
        public String getType (String qName)
        {
            return qAtts.getType(qName).intern();
        }


        /**
         * Look up the value of an attribute by Namespace name.
         *
         * <p>
         *  按名称空间名称查找属性的值。
         * 
         * 
         * @param uri The Namespace URI
         * @param localName The local name.
         * @return The attribute's value.
         */
        public String getValue (String uri, String localName)
        {
            return null;
        }


        /**
         * Look up the value of an attribute by qualified (prefixed) name.
         *
         * <p>
         *  通过限定(前缀)名称查找属性的值。
         * 
         * @param qName The qualified name.
         * @return The attribute's value.
         */
        public String getValue (String qName)
        {
            return qAtts.getValue(qName);
        }

        private AttributeList qAtts;
    }
}

// end of ParserAdapter.java
