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

// XMLFilterImpl.java - base SAX2 filter implementation.
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the Public Domain.
// $Id: XMLFilterImpl.java,v 1.3 2004/11/03 22:53:09 jsuttor Exp $

package org.xml.sax.helpers;

import java.io.IOException;

import org.xml.sax.XMLReader;
import org.xml.sax.XMLFilter;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.DTDHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXNotRecognizedException;


/**
 * Base class for deriving an XML filter.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This class is designed to sit between an {@link org.xml.sax.XMLReader
 * XMLReader} and the client application's event handlers.  By default, it
 * does nothing but pass requests up to the reader and events
 * on to the handlers unmodified, but subclasses can override
 * specific methods to modify the event stream or the configuration
 * requests as they pass through.</p>
 *
 * <p>
 *  用于派生XML过滤器的基类。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>此类设计为位于{@link org.xml.sax.XMLReader XMLReader}和客户端应用程序的事件处理程序之间。
 * 默认情况下,它不做任何事情,只是将读取器和事件直接传递到处理程序,但是子类可以重写特定的方法来修改事件流或配置请求,直到它们通过。</p>。
 * 
 * 
 * @since SAX 2.0
 * @author David Megginson
 * @see org.xml.sax.XMLFilter
 * @see org.xml.sax.XMLReader
 * @see org.xml.sax.EntityResolver
 * @see org.xml.sax.DTDHandler
 * @see org.xml.sax.ContentHandler
 * @see org.xml.sax.ErrorHandler
 */
public class XMLFilterImpl
    implements XMLFilter, EntityResolver, DTDHandler, ContentHandler, ErrorHandler
{


    ////////////////////////////////////////////////////////////////////
    // Constructors.
    ////////////////////////////////////////////////////////////////////


    /**
     * Construct an empty XML filter, with no parent.
     *
     * <p>This filter will have no parent: you must assign a parent
     * before you start a parse or do any configuration with
     * setFeature or setProperty, unless you use this as a pure event
     * consumer rather than as an {@link XMLReader}.</p>
     *
     * <p>
     *  构造一个空的XML过滤器,不带父级。
     * 
     *  <p>此过滤器不会有父级：您必须在开始解析之前分配父级,或者使用setFeature或setProperty进行任何配置,除非您将其用作纯事件消费者而不是{@link XMLReader}。
     * < p>。
     * 
     * 
     * @see org.xml.sax.XMLReader#setFeature
     * @see org.xml.sax.XMLReader#setProperty
     * @see #setParent
     */
    public XMLFilterImpl ()
    {
        super();
    }


    /**
     * Construct an XML filter with the specified parent.
     *
     * <p>
     *  使用指定的父构造XML过滤器。
     * 
     * 
     * @see #setParent
     * @see #getParent
     */
    public XMLFilterImpl (XMLReader parent)
    {
        super();
        setParent(parent);
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.XMLFilter.
    ////////////////////////////////////////////////////////////////////


    /**
     * Set the parent reader.
     *
     * <p>This is the {@link org.xml.sax.XMLReader XMLReader} from which
     * this filter will obtain its events and to which it will pass its
     * configuration requests.  The parent may itself be another filter.</p>
     *
     * <p>If there is no parent reader set, any attempt to parse
     * or to set or get a feature or property will fail.</p>
     *
     * <p>
     *  设置父读取器。
     * 
     *  <p>这是{@link org.xml.sax.XMLReader XMLReader},此过滤器将从中获取其事件,并将向其传递其配置请求。父母本身可以是另一个过滤器。</p>
     * 
     *  <p>如果没有父级阅读器设置,任何解析或设置或获取功能或属性的尝试都会失败。</p>
     * 
     * 
     * @param parent The parent XML reader.
     * @see #getParent
     */
    public void setParent (XMLReader parent)
    {
        this.parent = parent;
    }


    /**
     * Get the parent reader.
     *
     * <p>
     *  获取父读者。
     * 
     * 
     * @return The parent XML reader, or null if none is set.
     * @see #setParent
     */
    public XMLReader getParent ()
    {
        return parent;
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.XMLReader.
    ////////////////////////////////////////////////////////////////////


    /**
     * Set the value of a feature.
     *
     * <p>This will always fail if the parent is null.</p>
     *
     * <p>
     *  设置要素的值。
     * 
     * <p>如果父项为null,这将总是失败。</p>
     * 
     * 
     * @param name The feature name.
     * @param value The requested feature value.
     * @exception org.xml.sax.SAXNotRecognizedException If the feature
     *            value can't be assigned or retrieved from the parent.
     * @exception org.xml.sax.SAXNotSupportedException When the
     *            parent recognizes the feature name but
     *            cannot set the requested value.
     */
    public void setFeature (String name, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        if (parent != null) {
            parent.setFeature(name, value);
        } else {
            throw new SAXNotRecognizedException("Feature: " + name);
        }
    }


    /**
     * Look up the value of a feature.
     *
     * <p>This will always fail if the parent is null.</p>
     *
     * <p>
     *  查找要素的值。
     * 
     *  <p>如果父项为null,这将总是失败。</p>
     * 
     * 
     * @param name The feature name.
     * @return The current value of the feature.
     * @exception org.xml.sax.SAXNotRecognizedException If the feature
     *            value can't be assigned or retrieved from the parent.
     * @exception org.xml.sax.SAXNotSupportedException When the
     *            parent recognizes the feature name but
     *            cannot determine its value at this time.
     */
    public boolean getFeature (String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        if (parent != null) {
            return parent.getFeature(name);
        } else {
            throw new SAXNotRecognizedException("Feature: " + name);
        }
    }


    /**
     * Set the value of a property.
     *
     * <p>This will always fail if the parent is null.</p>
     *
     * <p>
     *  设置属性的值。
     * 
     *  <p>如果父项为null,这将总是失败。</p>
     * 
     * 
     * @param name The property name.
     * @param value The requested property value.
     * @exception org.xml.sax.SAXNotRecognizedException If the property
     *            value can't be assigned or retrieved from the parent.
     * @exception org.xml.sax.SAXNotSupportedException When the
     *            parent recognizes the property name but
     *            cannot set the requested value.
     */
    public void setProperty (String name, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        if (parent != null) {
            parent.setProperty(name, value);
        } else {
            throw new SAXNotRecognizedException("Property: " + name);
        }
    }


    /**
     * Look up the value of a property.
     *
     * <p>
     *  查找属性的值。
     * 
     * 
     * @param name The property name.
     * @return The current value of the property.
     * @exception org.xml.sax.SAXNotRecognizedException If the property
     *            value can't be assigned or retrieved from the parent.
     * @exception org.xml.sax.SAXNotSupportedException When the
     *            parent recognizes the property name but
     *            cannot determine its value at this time.
     */
    public Object getProperty (String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
    {
        if (parent != null) {
            return parent.getProperty(name);
        } else {
            throw new SAXNotRecognizedException("Property: " + name);
        }
    }


    /**
     * Set the entity resolver.
     *
     * <p>
     *  设置实体解析器。
     * 
     * 
     * @param resolver The new entity resolver.
     */
    public void setEntityResolver (EntityResolver resolver)
    {
        entityResolver = resolver;
    }


    /**
     * Get the current entity resolver.
     *
     * <p>
     *  获取当前实体解析器。
     * 
     * 
     * @return The current entity resolver, or null if none was set.
     */
    public EntityResolver getEntityResolver ()
    {
        return entityResolver;
    }


    /**
     * Set the DTD event handler.
     *
     * <p>
     *  设置DTD事件处理程序。
     * 
     * 
     * @param handler the new DTD handler
     */
    public void setDTDHandler (DTDHandler handler)
    {
        dtdHandler = handler;
    }


    /**
     * Get the current DTD event handler.
     *
     * <p>
     *  获取当前的DTD事件处理程序。
     * 
     * 
     * @return The current DTD handler, or null if none was set.
     */
    public DTDHandler getDTDHandler ()
    {
        return dtdHandler;
    }


    /**
     * Set the content event handler.
     *
     * <p>
     *  设置内容事件处理程序。
     * 
     * 
     * @param handler the new content handler
     */
    public void setContentHandler (ContentHandler handler)
    {
        contentHandler = handler;
    }


    /**
     * Get the content event handler.
     *
     * <p>
     *  获取内容事件处理程序。
     * 
     * 
     * @return The current content handler, or null if none was set.
     */
    public ContentHandler getContentHandler ()
    {
        return contentHandler;
    }


    /**
     * Set the error event handler.
     *
     * <p>
     *  设置错误事件处理程序。
     * 
     * 
     * @param handler the new error handler
     */
    public void setErrorHandler (ErrorHandler handler)
    {
        errorHandler = handler;
    }


    /**
     * Get the current error event handler.
     *
     * <p>
     *  获取当前错误事件处理程序。
     * 
     * 
     * @return The current error handler, or null if none was set.
     */
    public ErrorHandler getErrorHandler ()
    {
        return errorHandler;
    }


    /**
     * Parse a document.
     *
     * <p>
     *  解析文档。
     * 
     * 
     * @param input The input source for the document entity.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     */
    public void parse (InputSource input)
        throws SAXException, IOException
    {
        setupParse();
        parent.parse(input);
    }


    /**
     * Parse a document.
     *
     * <p>
     *  解析文档。
     * 
     * 
     * @param systemId The system identifier as a fully-qualified URI.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     */
    public void parse (String systemId)
        throws SAXException, IOException
    {
        parse(new InputSource(systemId));
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.EntityResolver.
    ////////////////////////////////////////////////////////////////////


    /**
     * Filter an external entity resolution.
     *
     * <p>
     *  过滤外部实体分辨率。
     * 
     * 
     * @param publicId The entity's public identifier, or null.
     * @param systemId The entity's system identifier.
     * @return A new InputSource or null for the default.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     * @exception java.io.IOException The client may throw an
     *            I/O-related exception while obtaining the
     *            new InputSource.
     */
    public InputSource resolveEntity (String publicId, String systemId)
        throws SAXException, IOException
    {
        if (entityResolver != null) {
            return entityResolver.resolveEntity(publicId, systemId);
        } else {
            return null;
        }
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.DTDHandler.
    ////////////////////////////////////////////////////////////////////


    /**
     * Filter a notation declaration event.
     *
     * <p>
     *  过滤符号声明事件。
     * 
     * 
     * @param name The notation name.
     * @param publicId The notation's public identifier, or null.
     * @param systemId The notation's system identifier, or null.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void notationDecl (String name, String publicId, String systemId)
        throws SAXException
    {
        if (dtdHandler != null) {
            dtdHandler.notationDecl(name, publicId, systemId);
        }
    }


    /**
     * Filter an unparsed entity declaration event.
     *
     * <p>
     *  过滤未解析的实体声明事件。
     * 
     * 
     * @param name The entity name.
     * @param publicId The entity's public identifier, or null.
     * @param systemId The entity's system identifier, or null.
     * @param notationName The name of the associated notation.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void unparsedEntityDecl (String name, String publicId,
                                    String systemId, String notationName)
        throws SAXException
    {
        if (dtdHandler != null) {
            dtdHandler.unparsedEntityDecl(name, publicId, systemId,
                                          notationName);
        }
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.ContentHandler.
    ////////////////////////////////////////////////////////////////////


    /**
     * Filter a new document locator event.
     *
     * <p>
     *  过滤新的文档定位器事件。
     * 
     * 
     * @param locator The document locator.
     */
    public void setDocumentLocator (Locator locator)
    {
        this.locator = locator;
        if (contentHandler != null) {
            contentHandler.setDocumentLocator(locator);
        }
    }


    /**
     * Filter a start document event.
     *
     * <p>
     *  过滤开始文档事件。
     * 
     * 
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void startDocument ()
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.startDocument();
        }
    }


    /**
     * Filter an end document event.
     *
     * <p>
     *  过滤结束文档事件。
     * 
     * 
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void endDocument ()
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.endDocument();
        }
    }


    /**
     * Filter a start Namespace prefix mapping event.
     *
     * <p>
     *  过滤开始命名空间前缀映射事件。
     * 
     * 
     * @param prefix The Namespace prefix.
     * @param uri The Namespace URI.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void startPrefixMapping (String prefix, String uri)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.startPrefixMapping(prefix, uri);
        }
    }


    /**
     * Filter an end Namespace prefix mapping event.
     *
     * <p>
     *  过滤结束命名空间前缀映射事件。
     * 
     * 
     * @param prefix The Namespace prefix.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void endPrefixMapping (String prefix)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.endPrefixMapping(prefix);
        }
    }


    /**
     * Filter a start element event.
     *
     * <p>
     *  过滤开始元素事件。
     * 
     * 
     * @param uri The element's Namespace URI, or the empty string.
     * @param localName The element's local name, or the empty string.
     * @param qName The element's qualified (prefixed) name, or the empty
     *        string.
     * @param atts The element's attributes.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void startElement (String uri, String localName, String qName,
                              Attributes atts)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.startElement(uri, localName, qName, atts);
        }
    }


    /**
     * Filter an end element event.
     *
     * <p>
     *  过滤结束元素事件。
     * 
     * 
     * @param uri The element's Namespace URI, or the empty string.
     * @param localName The element's local name, or the empty string.
     * @param qName The element's qualified (prefixed) name, or the empty
     *        string.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void endElement (String uri, String localName, String qName)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.endElement(uri, localName, qName);
        }
    }


    /**
     * Filter a character data event.
     *
     * <p>
     *  过滤字符数据事件。
     * 
     * 
     * @param ch An array of characters.
     * @param start The starting position in the array.
     * @param length The number of characters to use from the array.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void characters (char ch[], int start, int length)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.characters(ch, start, length);
        }
    }


    /**
     * Filter an ignorable whitespace event.
     *
     * <p>
     *  过滤可忽略的空格事件。
     * 
     * 
     * @param ch An array of characters.
     * @param start The starting position in the array.
     * @param length The number of characters to use from the array.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void ignorableWhitespace (char ch[], int start, int length)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.ignorableWhitespace(ch, start, length);
        }
    }


    /**
     * Filter a processing instruction event.
     *
     * <p>
     *  过滤处理指令事件。
     * 
     * 
     * @param target The processing instruction target.
     * @param data The text following the target.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void processingInstruction (String target, String data)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.processingInstruction(target, data);
        }
    }


    /**
     * Filter a skipped entity event.
     *
     * <p>
     *  过滤跳过的实体事件。
     * 
     * 
     * @param name The name of the skipped entity.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void skippedEntity (String name)
        throws SAXException
    {
        if (contentHandler != null) {
            contentHandler.skippedEntity(name);
        }
    }



    ////////////////////////////////////////////////////////////////////
    // Implementation of org.xml.sax.ErrorHandler.
    ////////////////////////////////////////////////////////////////////


    /**
     * Filter a warning event.
     *
     * <p>
     *  过滤警告事件。
     * 
     * 
     * @param e The warning as an exception.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void warning (SAXParseException e)
        throws SAXException
    {
        if (errorHandler != null) {
            errorHandler.warning(e);
        }
    }


    /**
     * Filter an error event.
     *
     * <p>
     *  过滤错误事件。
     * 
     * 
     * @param e The error as an exception.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void error (SAXParseException e)
        throws SAXException
    {
        if (errorHandler != null) {
            errorHandler.error(e);
        }
    }


    /**
     * Filter a fatal error event.
     *
     * <p>
     *  过滤致命错误事件。
     * 
     * 
     * @param e The error as an exception.
     * @exception org.xml.sax.SAXException The client may throw
     *            an exception during processing.
     */
    public void fatalError (SAXParseException e)
        throws SAXException
    {
        if (errorHandler != null) {
            errorHandler.fatalError(e);
        }
    }



    ////////////////////////////////////////////////////////////////////
    // Internal methods.
    ////////////////////////////////////////////////////////////////////


    /**
     * Set up before a parse.
     *
     * <p>Before every parse, check whether the parent is
     * non-null, and re-register the filter for all of the
     * events.</p>
     * <p>
     *  设置之前解析。
     * 
     */
    private void setupParse ()
    {
        if (parent == null) {
            throw new NullPointerException("No parent for filter");
        }
        parent.setEntityResolver(this);
        parent.setDTDHandler(this);
        parent.setContentHandler(this);
        parent.setErrorHandler(this);
    }



    ////////////////////////////////////////////////////////////////////
    // Internal state.
    ////////////////////////////////////////////////////////////////////

    private XMLReader parent = null;
    private Locator locator = null;
    private EntityResolver entityResolver = null;
    private DTDHandler dtdHandler = null;
    private ContentHandler contentHandler = null;
    private ErrorHandler errorHandler = null;

}

// end of XMLFilterImpl.java
