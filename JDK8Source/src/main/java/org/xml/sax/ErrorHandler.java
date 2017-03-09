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

// SAX error handler.
// http://www.saxproject.org
// No warranty; no copyright -- use this as you will.
// $Id: ErrorHandler.java,v 1.2 2004/11/03 22:44:52 jsuttor Exp $

package org.xml.sax;


/**
 * Basic interface for SAX error handlers.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p>If a SAX application needs to implement customized error
 * handling, it must implement this interface and then register an
 * instance with the XML reader using the
 * {@link org.xml.sax.XMLReader#setErrorHandler setErrorHandler}
 * method.  The parser will then report all errors and warnings
 * through this interface.</p>
 *
 * <p><strong>WARNING:</strong> If an application does <em>not</em>
 * register an ErrorHandler, XML parsing errors will go unreported,
 * except that <em>SAXParseException</em>s will be thrown for fatal errors.
 * In order to detect validity errors, an ErrorHandler that does something
 * with {@link #error error()} calls must be registered.</p>
 *
 * <p>For XML processing errors, a SAX driver must use this interface
 * in preference to throwing an exception: it is up to the application
 * to decide whether to throw an exception for different types of
 * errors and warnings.  Note, however, that there is no requirement that
 * the parser continue to report additional errors after a call to
 * {@link #fatalError fatalError}.  In other words, a SAX driver class
 * may throw an exception after reporting any fatalError.
 * Also parsers may throw appropriate exceptions for non-XML errors.
 * For example, {@link XMLReader#parse XMLReader.parse()} would throw
 * an IOException for errors accessing entities or the document.</p>
 *
 * <p>
 *  SAX错误处理程序的基本接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p>如果SAX应用程序需要实现自定义错误处理,则必须实现此接口,然后使用{@link org.xml.sax.XMLReader#setErrorHandler setErrorHandler}方法
 * 向XML阅读器注册实例。
 * 然后解析器将通过此接口报告所有错误和警告。</p>。
 * 
 *  <p> <strong>警告：</strong>如果应用程序未注册ErrorHandler,则XML解析错误将不会被报告,除非会抛出<em> SAXParseException </em>致命错误。
 * 为了检测有效性错误,必须注册一个执行{@link #error error()}调用的错误处理程序。</p>。
 * 
 * <p>对于XML处理错误,SAX驱动程序必须使用此接口,优先于抛出异常：由应用程序决定是否针对不同类型的错误和警告抛出异常。
 * 但请注意,没有要求解析器在调用{@link #fatalError fatalError}后继续报告其他错误。换句话说,SAX驱动程序类可能在报告任何fatalError之后抛出异常。
 * 解析器也可能会为非XML错误抛出适当的异常。例如,{@link XMLReader#parse XMLReader.parse()}会抛出访问实体或文档的错误的IOException。</p>。
 * 
 * 
 * @since SAX 1.0
 * @author David Megginson
 * @see org.xml.sax.XMLReader#setErrorHandler
 * @see org.xml.sax.SAXParseException
 */
public interface ErrorHandler {


    /**
     * Receive notification of a warning.
     *
     * <p>SAX parsers will use this method to report conditions that
     * are not errors or fatal errors as defined by the XML
     * recommendation.  The default behaviour is to take no
     * action.</p>
     *
     * <p>The SAX parser must continue to provide normal parsing events
     * after invoking this method: it should still be possible for the
     * application to process the document through to the end.</p>
     *
     * <p>Filters may use this method to report other, non-XML warnings
     * as well.</p>
     *
     * <p>
     *  接收警告通知。
     * 
     *  <p> SAX解析器将使用此方法报告不是XML建议定义的错误或致命错误的情况。默认行为是不采取任何操作。</p>
     * 
     *  <p>在调用此方法后,SAX解析器必须继续提供正常的解析事件：应用程序仍然可以处理文档,直到结束。</p>
     * 
     *  <p>过滤器也可以使用此方法报告其他非XML警告。</p>
     * 
     * 
     * @param exception The warning information encapsulated in a
     *                  SAX parse exception.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.SAXParseException
     */
    public abstract void warning (SAXParseException exception)
        throws SAXException;


    /**
     * Receive notification of a recoverable error.
     *
     * <p>This corresponds to the definition of "error" in section 1.2
     * of the W3C XML 1.0 Recommendation.  For example, a validating
     * parser would use this callback to report the violation of a
     * validity constraint.  The default behaviour is to take no
     * action.</p>
     *
     * <p>The SAX parser must continue to provide normal parsing
     * events after invoking this method: it should still be possible
     * for the application to process the document through to the end.
     * If the application cannot do so, then the parser should report
     * a fatal error even if the XML recommendation does not require
     * it to do so.</p>
     *
     * <p>Filters may use this method to report other, non-XML errors
     * as well.</p>
     *
     * <p>
     *  接收可恢复错误的通知。
     * 
     *  <p>这对应于W3C XML 1.0建议书第1.2节中"错误"的定义。例如,验证解析器将使用此回调来报告有效性约束的违反。默认行为是不采取任何操作。</p>
     * 
     * <p>在调用此方法后,SAX解析器必须继续提供正常的解析事件：应用程序仍然可能处理文档直到结束。如果应用程序不能这样做,那么解析器应报告致命错误,即使XML建议不要求它这样做。</p>
     * 
     *  <p>过滤器也可以使用此方法来报告其他非XML错误。</p>
     * 
     * 
     * @param exception The error information encapsulated in a
     *                  SAX parse exception.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.SAXParseException
     */
    public abstract void error (SAXParseException exception)
        throws SAXException;


    /**
     * Receive notification of a non-recoverable error.
     *
     * <p><strong>There is an apparent contradiction between the
     * documentation for this method and the documentation for {@link
     * org.xml.sax.ContentHandler#endDocument}.  Until this ambiguity
     * is resolved in a future major release, clients should make no
     * assumptions about whether endDocument() will or will not be
     * invoked when the parser has reported a fatalError() or thrown
     * an exception.</strong></p>
     *
     * <p>This corresponds to the definition of "fatal error" in
     * section 1.2 of the W3C XML 1.0 Recommendation.  For example, a
     * parser would use this callback to report the violation of a
     * well-formedness constraint.</p>
     *
     * <p>The application must assume that the document is unusable
     * after the parser has invoked this method, and should continue
     * (if at all) only for the sake of collecting additional error
     * messages: in fact, SAX parsers are free to stop reporting any
     * other events once this method has been invoked.</p>
     *
     * <p>
     *  接收不可恢复错误的通知。
     * 
     *  <p> <strong>此方法的文档与{@link org.xml.sax.ContentHandler#endDocument}的文档之间存在明显的矛盾。
     * 在未来的主要版本中解决这种模糊性之前,客户端不应该假设endDocument()在解析器报告了fatalError()或抛出异常时是否将被调用。</strong>。
     * 
     * 
     * @param exception The error information encapsulated in a
     *                  SAX parse exception.
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @see org.xml.sax.SAXParseException
     */
    public abstract void fatalError (SAXParseException exception)
        throws SAXException;

}

// end of ErrorHandler.java
