/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
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

// DefaultHandler2.java - extended DefaultHandler
// http://www.saxproject.org
// Public Domain: no warranty.
// $Id: DefaultHandler2.java,v 1.2 2004/11/03 22:49:08 jsuttor Exp $

package org.xml.sax.ext;

import java.io.IOException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * This class extends the SAX2 base handler class to support the
 * SAX2 {@link LexicalHandler}, {@link DeclHandler}, and
 * {@link EntityResolver2} extensions.  Except for overriding the
 * original SAX1 {@link DefaultHandler#resolveEntity resolveEntity()}
 * method the added handler methods just return.  Subclassers may
 * override everything on a method-by-method basis.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * </blockquote>
 *
 * <p> <em>Note:</em> this class might yet learn that the
 * <em>ContentHandler.setDocumentLocator()</em> call might be passed a
 * {@link Locator2} object, and that the
 * <em>ContentHandler.startElement()</em> call might be passed a
 * {@link Attributes2} object.
 *
 * <p>
 *  此类扩展了SAX2基本处理程序类,以支持SAX2 {@link LexicalHandler},{@link DeclHandler}和{@link EntityResolver2}扩展。
 * 除了覆盖原来的SAX1 {@link DefaultHandler#resolveEntity resolveEntity()}方法,添加的处理程序方法只是返回。子类可以在逐个方法的基础上覆盖一切。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)位于公共域中,并且随附<strong>无保修</strong>。</em>
 * </blockquote>
 * 
 *  <p> <em>注意：<em>此类可能还会了解到,ContentHandler.setDocumentLocator()</em>调用可能传递了一个{@link Locator2}对象,而且<em> 
 * ContentHandler .startElement()</em>调用可能传递一个{@link Attributes2}对象。
 * 
 * 
 * @since SAX 2.0 (extensions 1.1 alpha)
 * @author David Brownell
 */
public class DefaultHandler2 extends DefaultHandler
    implements LexicalHandler, DeclHandler, EntityResolver2
{
    /** Constructs a handler which ignores all parsing events. */
    public DefaultHandler2 () { }


    // SAX2 ext-1.0 LexicalHandler

    public void startCDATA ()
    throws SAXException
        {}

    public void endCDATA ()
    throws SAXException
        {}

    public void startDTD (String name, String publicId, String systemId)
    throws SAXException
        {}

    public void endDTD ()
    throws SAXException
        {}

    public void startEntity (String name)
    throws SAXException
        {}

    public void endEntity (String name)
    throws SAXException
        {}

    public void comment (char ch [], int start, int length)
    throws SAXException
        { }


    // SAX2 ext-1.0 DeclHandler

    public void attributeDecl (String eName, String aName,
            String type, String mode, String value)
    throws SAXException
        {}

    public void elementDecl (String name, String model)
    throws SAXException
        {}

    public void externalEntityDecl (String name,
        String publicId, String systemId)
    throws SAXException
        {}

    public void internalEntityDecl (String name, String value)
    throws SAXException
        {}

    // SAX2 ext-1.1 EntityResolver2

    /**
     * Tells the parser that if no external subset has been declared
     * in the document text, none should be used.
     * <p>
     *  告诉解析器,如果没有在文档文本中声明外部子集,则不应使用任何外部子集。
     * 
     */
    public InputSource getExternalSubset (String name, String baseURI)
    throws SAXException, IOException
        { return null; }

    /**
     * Tells the parser to resolve the systemId against the baseURI
     * and read the entity text from that resulting absolute URI.
     * Note that because the older
     * {@link DefaultHandler#resolveEntity DefaultHandler.resolveEntity()},
     * method is overridden to call this one, this method may sometimes
     * be invoked with null <em>name</em> and <em>baseURI</em>, and
     * with the <em>systemId</em> already absolutized.
     * <p>
     *  告诉解析器根据baseURI解析systemId,并从生成的绝对URI中读取实体文本。
     * 请注意,因为较早的{@link DefaultHandler#resolveEntity DefaultHandler.resolveEntity()}方法被覆盖而无法调用此方法,所以有时可以使用nul
     * l <em> name </em>和<em> baseURI </em >,以及已经绝对的<em> systemId </em>。
     *  告诉解析器根据baseURI解析systemId,并从生成的绝对URI中读取实体文本。
     * 
     */
    public InputSource resolveEntity (String name, String publicId,
            String baseURI, String systemId)
    throws SAXException, IOException
        { return null; }

    // SAX1 EntityResolver

    /**
     * Invokes
     * {@link EntityResolver2#resolveEntity EntityResolver2.resolveEntity()}
     * with null entity name and base URI.
     * You only need to override that method to use this class.
     * <p>
     */
    public InputSource resolveEntity (String publicId, String systemId)
    throws SAXException, IOException
        { return resolveEntity (null, publicId, null, systemId); }
}
