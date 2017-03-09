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

// SAX parser interface.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: Parser.java,v 1.2 2004/11/03 22:55:32 jsuttor Exp $

package org.xml.sax;

import java.io.IOException;
import java.util.Locale;


/**
 * Basic interface for SAX (Simple API for XML) parsers.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>This was the main event supplier interface for SAX1; it has
 * been replaced in SAX2 by {@link org.xml.sax.XMLReader XMLReader},
 * which includes Namespace support and sophisticated configurability
 * and extensibility.</p>
 *
 * <p>All SAX1 parsers must implement this basic interface: it allows
 * applications to register handlers for different types of events
 * and to initiate a parse from a URI, or a character stream.</p>
 *
 * <p>All SAX1 parsers must also implement a zero-argument constructor
 * (though other constructors are also allowed).</p>
 *
 * <p>SAX1 parsers are reusable but not re-entrant: the application
 * may reuse a parser object (possibly with a different input source)
 * once the first parse has completed successfully, but it may not
 * invoke the parse() methods recursively within a parse.</p>
 *
 * <p>
 *  SAX(Simple API for XML)解析器的基本接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>这是SAX1的主要活动供应商界面;它在SAX2中被{@link org.xml.sax.XMLReader XMLReader}替换,它包括命名空间支持和复杂的可配置性和可扩展性。</p>
 * 
 *  <p>所有SAX1解析器都必须实现此基本接口：它允许应用程序注册不同类型事件的处理程序,并从URI或字符流启动解析。</p>
 * 
 *  <p>所有SAX1解析器还必须实现一个零参数构造函数(尽管也允许其他构造函数)。</p>
 * 
 *  <p> SAX1解析器可重用,但不可重入：应用程序可以在第一次解析成功完成后重用解析器对象(可能使用不同的输入源),但它可能无法在解析中递归调用parse()方法。</p>
 * 
 * 
 * @deprecated This interface has been replaced by the SAX2
 *             {@link org.xml.sax.XMLReader XMLReader}
 *             interface, which includes Namespace support.
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.EntityResolver
 * @see org.xml.sax.DTDHandler
 * @see org.xml.sax.DocumentHandler
 * @see org.xml.sax.ErrorHandler
 * @see org.xml.sax.HandlerBase
 * @see org.xml.sax.InputSource
 */
public interface Parser
{

    /**
     * Allow an application to request a locale for errors and warnings.
     *
     * <p>SAX parsers are not required to provide localisation for errors
     * and warnings; if they cannot support the requested locale,
     * however, they must throw a SAX exception.  Applications may
     * not request a locale change in the middle of a parse.</p>
     *
     * <p>
     *  允许应用程序请求错误和警告的区域设置。
     * 
     * <p> SAX解析器不需要提供错误和警告的本地化;如果他们不能支持请求的语言环境,但是,他们必须抛出一个SAX异常。应用程序可能不会在解析过程中请求更改区域设置。</p>
     * 
     * 
     * @param locale A Java Locale object.
     * @exception org.xml.sax.SAXException Throws an exception
     *            (using the previous or default locale) if the
     *            requested locale is not supported.
     * @see org.xml.sax.SAXException
     * @see org.xml.sax.SAXParseException
     */
    public abstract void setLocale (Locale locale)
        throws SAXException;


    /**
     * Allow an application to register a custom entity resolver.
     *
     * <p>If the application does not register an entity resolver, the
     * SAX parser will resolve system identifiers and open connections
     * to entities itself (this is the default behaviour implemented in
     * HandlerBase).</p>
     *
     * <p>Applications may register a new or different entity resolver
     * in the middle of a parse, and the SAX parser must begin using
     * the new resolver immediately.</p>
     *
     * <p>
     *  允许应用程序注册自定义实体解析器。
     * 
     *  <p>如果应用程序未注册实体解析器,则SAX解析器将解析系统标识符并打开与实体本身的连接(这是在HandlerBase中实现的默认行为)。</p>
     * 
     *  <p>应用程序可以在解析的过程中注册一个新的或不同的实体解析器,并且SAX解析器必须立即开始使用新的解析器。</p>
     * 
     * 
     * @param resolver The object for resolving entities.
     * @see EntityResolver
     * @see HandlerBase
     */
    public abstract void setEntityResolver (EntityResolver resolver);


    /**
     * Allow an application to register a DTD event handler.
     *
     * <p>If the application does not register a DTD handler, all DTD
     * events reported by the SAX parser will be silently
     * ignored (this is the default behaviour implemented by
     * HandlerBase).</p>
     *
     * <p>Applications may register a new or different
     * handler in the middle of a parse, and the SAX parser must
     * begin using the new handler immediately.</p>
     *
     * <p>
     *  允许应用程序注册DTD事件处理程序。
     * 
     *  <p>如果应用程序未注册DTD处理程序,则SAX解析器报告的所有DTD事件将被静默忽略(这是HandlerBase实现的默认行为)。</p>
     * 
     *  <p>应用程序可能在解析中间注册一个新的或不同的处理程序,并且SAX解析器必须立即开始使用新的处理程序。</p>
     * 
     * 
     * @param handler The DTD handler.
     * @see DTDHandler
     * @see HandlerBase
     */
    public abstract void setDTDHandler (DTDHandler handler);


    /**
     * Allow an application to register a document event handler.
     *
     * <p>If the application does not register a document handler, all
     * document events reported by the SAX parser will be silently
     * ignored (this is the default behaviour implemented by
     * HandlerBase).</p>
     *
     * <p>Applications may register a new or different handler in the
     * middle of a parse, and the SAX parser must begin using the new
     * handler immediately.</p>
     *
     * <p>
     *  允许应用程序注册文档事件处理程序。
     * 
     *  <p>如果应用程序没有注册文档处理程序,则SAX解析器报告的所有文档事件将被默认忽略(这是HandlerBase实现的默认行为)。</p>
     * 
     *  <p>应用程序可能在解析中间注册一个新的或不同的处理程序,并且SAX解析器必须立即开始使用新的处理程序。</p>
     * 
     * 
     * @param handler The document handler.
     * @see DocumentHandler
     * @see HandlerBase
     */
    public abstract void setDocumentHandler (DocumentHandler handler);


    /**
     * Allow an application to register an error event handler.
     *
     * <p>If the application does not register an error event handler,
     * all error events reported by the SAX parser will be silently
     * ignored, except for fatalError, which will throw a SAXException
     * (this is the default behaviour implemented by HandlerBase).</p>
     *
     * <p>Applications may register a new or different handler in the
     * middle of a parse, and the SAX parser must begin using the new
     * handler immediately.</p>
     *
     * <p>
     * 允许应用程序注册错误事件处理程序。
     * 
     *  <p>如果应用程序没有注册错误事件处理程序,SAX解析器报告的所有错误事件将被忽略,除了fatalError,它将抛出一个SAXException(这是由HandlerBase实现的默认行为)。
     * </p >。
     * 
     *  <p>应用程序可能在解析中间注册一个新的或不同的处理程序,并且SAX解析器必须立即开始使用新的处理程序。</p>
     * 
     * 
     * @param handler The error handler.
     * @see ErrorHandler
     * @see SAXException
     * @see HandlerBase
     */
    public abstract void setErrorHandler (ErrorHandler handler);


    /**
     * Parse an XML document.
     *
     * <p>The application can use this method to instruct the SAX parser
     * to begin parsing an XML document from any valid input
     * source (a character stream, a byte stream, or a URI).</p>
     *
     * <p>Applications may not invoke this method while a parse is in
     * progress (they should create a new Parser instead for each
     * additional XML document).  Once a parse is complete, an
     * application may reuse the same Parser object, possibly with a
     * different input source.</p>
     *
     * <p>
     *  解析XML文档。
     * 
     *  <p>应用程序可以使用此方法指示SAX解析器开始从任何有效的输入源(字符流,字节流或URI)解析XML文档。</p>
     * 
     *  <p>在解析正在进行时,应用程序不能调用此方法(它们应为每个其他XML文档创建一个新的解析器)。一旦解析完成,应用程序可以重用相同的解析器对象,可能使用不同的输入源。</p>
     * 
     * 
     * @param source The input source for the top-level of the
     *        XML document.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @see org.xml.sax.InputSource
     * @see #parse(java.lang.String)
     * @see #setEntityResolver
     * @see #setDTDHandler
     * @see #setDocumentHandler
     * @see #setErrorHandler
     */
    public abstract void parse (InputSource source)
        throws SAXException, IOException;


    /**
     * Parse an XML document from a system identifier (URI).
     *
     * <p>This method is a shortcut for the common case of reading a
     * document from a system identifier.  It is the exact
     * equivalent of the following:</p>
     *
     * <pre>
     * parse(new InputSource(systemId));
     * </pre>
     *
     * <p>If the system identifier is a URL, it must be fully resolved
     * by the application before it is passed to the parser.</p>
     *
     * <p>
     *  从系统标识符(URI)解析XML文档。
     * 
     *  <p>此方法是从系统标识符读取文档的常见情况的快捷方式。它与以下内容完全相同：</p>
     * 
     * <pre>
     * 
     * @param systemId The system identifier (URI).
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @see #parse(org.xml.sax.InputSource)
     */
    public abstract void parse (String systemId)
        throws SAXException, IOException;

}

// end of Parser.java
