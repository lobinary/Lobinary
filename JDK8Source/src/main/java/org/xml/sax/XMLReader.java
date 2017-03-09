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

// XMLReader.java - read an XML document.
// http://www.saxproject.org
// Written by David Megginson
// NO WARRANTY!  This class is in the Public Domain.
// $Id: XMLReader.java,v 1.3 2004/11/03 22:55:32 jsuttor Exp $

package org.xml.sax;

import java.io.IOException;


/**
 * Interface for reading an XML document using callbacks.
 *
 * <blockquote>
 * <em>This module, both source code and documentation, is in the
 * Public Domain, and comes with <strong>NO WARRANTY</strong>.</em>
 * See <a href='http://www.saxproject.org'>http://www.saxproject.org</a>
 * for further information.
 * </blockquote>
 *
 * <p><strong>Note:</strong> despite its name, this interface does
 * <em>not</em> extend the standard Java {@link java.io.Reader Reader}
 * interface, because reading XML is a fundamentally different activity
 * than reading character data.</p>
 *
 * <p>XMLReader is the interface that an XML parser's SAX2 driver must
 * implement.  This interface allows an application to set and
 * query features and properties in the parser, to register
 * event handlers for document processing, and to initiate
 * a document parse.</p>
 *
 * <p>All SAX interfaces are assumed to be synchronous: the
 * {@link #parse parse} methods must not return until parsing
 * is complete, and readers must wait for an event-handler callback
 * to return before reporting the next event.</p>
 *
 * <p>This interface replaces the (now deprecated) SAX 1.0 {@link
 * org.xml.sax.Parser Parser} interface.  The XMLReader interface
 * contains two important enhancements over the old Parser
 * interface (as well as some minor ones):</p>
 *
 * <ol>
 * <li>it adds a standard way to query and set features and
 *  properties; and</li>
 * <li>it adds Namespace support, which is required for many
 *  higher-level XML standards.</li>
 * </ol>
 *
 * <p>There are adapters available to convert a SAX1 Parser to
 * a SAX2 XMLReader and vice-versa.</p>
 *
 * <p>
 *  用于使用回调读取XML文档的接口。
 * 
 * <blockquote>
 *  <em>此模块(源代码和文档)都位于公共域中,并且随附<strong>无保证</strong>。
 * </em>请参阅<a href ='http：//www.saxproject.org '> http://www.saxproject.org </a>了解更多信息。
 * </blockquote>
 * 
 *  <p> <strong>注意</strong>：</strong>尽管其名称,但此界面不会</em>扩展标准Java {@link java.io.Reader Reader}界面,因为阅读XML是根
 * 本不同的活动比阅读字符数据。
 * </p>。
 * 
 *  <p> XMLReader是XML解析器的SAX2驱动程序必须实现的接口。此接口允许应用程序在解析器中设置和查询特征和属性,注册事件处理程序以进行文档处理,并启动文档解析。</p>
 * 
 *  <p>假定所有SAX接口都是同步的：{@link #parse parse}方法必须在解析完成之前不返回,读者必须等待事件处理程序回调才能报告下一个事件。</p >
 * 
 *  <p>此接口替换(现已弃用)SAX 1.0 {@link org.xml.sax.Parser Parser}接口。
 *  XMLReader接口包含旧的Parser接口(以及一些小的接口)的两个重要的增强功能：</p>。
 * 
 * <ol>
 * <li>它添加了一种标准方法来查询和设置要素和属性;和</li> <li>它添加了命名空间支持,这是许多更高级别XML标准所必需的。</li>
 * </ol>
 * 
 *  <p>有适配器可用于将SAX1解析器转换为SAX2 XMLReader,反之亦然。</p>
 * 
 * 
 * @since SAX 2.0
 * @author David Megginson
 * @see org.xml.sax.XMLFilter
 * @see org.xml.sax.helpers.ParserAdapter
 * @see org.xml.sax.helpers.XMLReaderAdapter
 */
public interface XMLReader
{


    ////////////////////////////////////////////////////////////////////
    // Configuration.
    ////////////////////////////////////////////////////////////////////


    /**
     * Look up the value of a feature flag.
     *
     * <p>The feature name is any fully-qualified URI.  It is
     * possible for an XMLReader to recognize a feature name but
     * temporarily be unable to return its value.
     * Some feature values may be available only in specific
     * contexts, such as before, during, or after a parse.
     * Also, some feature values may not be programmatically accessible.
     * (In the case of an adapter for SAX1 {@link Parser}, there is no
     * implementation-independent way to expose whether the underlying
     * parser is performing validation, expanding external entities,
     * and so forth.) </p>
     *
     * <p>All XMLReaders are required to recognize the
     * http://xml.org/sax/features/namespaces and the
     * http://xml.org/sax/features/namespace-prefixes feature names.</p>
     *
     * <p>Typical usage is something like this:</p>
     *
     * <pre>
     * XMLReader r = new MySAXDriver();
     *
     *                         // try to activate validation
     * try {
     *   r.setFeature("http://xml.org/sax/features/validation", true);
     * } catch (SAXException e) {
     *   System.err.println("Cannot activate validation.");
     * }
     *
     *                         // register event handlers
     * r.setContentHandler(new MyContentHandler());
     * r.setErrorHandler(new MyErrorHandler());
     *
     *                         // parse the first document
     * try {
     *   r.parse("http://www.foo.com/mydoc.xml");
     * } catch (IOException e) {
     *   System.err.println("I/O exception reading XML document");
     * } catch (SAXException e) {
     *   System.err.println("XML exception reading document.");
     * }
     * </pre>
     *
     * <p>Implementors are free (and encouraged) to invent their own features,
     * using names built on their own URIs.</p>
     *
     * <p>
     *  查找特征标志的值。
     * 
     *  <p>地图项名称是任何完全限定的URI。 XMLReader可以识别功能名称,但暂时无法返回其值。一些特征值可能仅在特定上下文中可用,例如在解析之前,期间或之后。
     * 此外,某些特征值可能无法以编程方式访问。 (在适用于SAX1 {@link Parser}的适配器的情况下,没有与实现无关的方式来公开底层解析器是否在执行验证,扩展外部实体等等。)</p>。
     * 
     *  <p>所有XMLReader都必须识别http://xml.org/sax/features/namespaces和http://xml.org/sax/features/namespace-pref
     * ixes功能名称。
     * </p>。
     * 
     *  <p>典型用法如下：</p>
     * 
     * <pre>
     *  XMLReader r = new MySAXDriver();
     * 
     *  //尝试激活验证try {r.setFeature("http://xml.org/sax/features/validation",true); } catch(SAXException e){System.err.println("无法激活验证。
     *  }}。
     * 
     *  // register event handlers r.setContentHandler(new MyContentHandler()); r.setErrorHandler(new MyErro
     * rHandler());。
     * 
     * // parse the first document try {r.parse("http://www.foo.com/mydoc.xml"); } catch(IOException e){System.err.println("读取XML文档的I / O异常"); }
     *  catch(SAXException e){System.err.println("XML exception reading document。
     *  }}。
     * </pre>
     * 
     *  <p>实施者可以自由地(并鼓励)使用自己的URI创建自己的特征。</p>
     * 
     * 
     * @param name The feature name, which is a fully-qualified URI.
     * @return The current value of the feature (true or false).
     * @exception org.xml.sax.SAXNotRecognizedException If the feature
     *            value can't be assigned or retrieved.
     * @exception org.xml.sax.SAXNotSupportedException When the
     *            XMLReader recognizes the feature name but
     *            cannot determine its value at this time.
     * @see #setFeature
     */
    public boolean getFeature (String name)
        throws SAXNotRecognizedException, SAXNotSupportedException;


    /**
     * Set the value of a feature flag.
     *
     * <p>The feature name is any fully-qualified URI.  It is
     * possible for an XMLReader to expose a feature value but
     * to be unable to change the current value.
     * Some feature values may be immutable or mutable only
     * in specific contexts, such as before, during, or after
     * a parse.</p>
     *
     * <p>All XMLReaders are required to support setting
     * http://xml.org/sax/features/namespaces to true and
     * http://xml.org/sax/features/namespace-prefixes to false.</p>
     *
     * <p>
     *  设置功能标志的值。
     * 
     *  <p>地图项名称是任何完全限定的URI。 XMLReader可以公开特征值,但无法更改当前值。某些特征值可能只在特定上下文中是不可变的或可变的,例如在解析之前,期间或之后。</p>
     * 
     *  <p>所有XMLReader都必须支持将http://xml.org/sax/features/namespaces设置为true,将http://xml.org/sax/features/names
     * pace-prefixes设置为false。
     * </p>。
     * 
     * 
     * @param name The feature name, which is a fully-qualified URI.
     * @param value The requested value of the feature (true or false).
     * @exception org.xml.sax.SAXNotRecognizedException If the feature
     *            value can't be assigned or retrieved.
     * @exception org.xml.sax.SAXNotSupportedException When the
     *            XMLReader recognizes the feature name but
     *            cannot set the requested value.
     * @see #getFeature
     */
    public void setFeature (String name, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException;


    /**
     * Look up the value of a property.
     *
     * <p>The property name is any fully-qualified URI.  It is
     * possible for an XMLReader to recognize a property name but
     * temporarily be unable to return its value.
     * Some property values may be available only in specific
     * contexts, such as before, during, or after a parse.</p>
     *
     * <p>XMLReaders are not required to recognize any specific
     * property names, though an initial core set is documented for
     * SAX2.</p>
     *
     * <p>Implementors are free (and encouraged) to invent their own properties,
     * using names built on their own URIs.</p>
     *
     * <p>
     *  查找属性的值。
     * 
     *  <p>属性名称是任何完全限定的URI。 XMLReader可以识别属性名称,但暂时无法返回其值。某些属性值只能在特定上下文中使用,例如在解析之前,期间或之后。</p>
     * 
     *  <p> XMLReaders不需要识别任何特定的属性名称,虽然记录了SAX2的初始核心集。</p>
     * 
     *  <p>实施者可以自由(鼓励)使用自己的URI创建自己的属性。</p>
     * 
     * 
     * @param name The property name, which is a fully-qualified URI.
     * @return The current value of the property.
     * @exception org.xml.sax.SAXNotRecognizedException If the property
     *            value can't be assigned or retrieved.
     * @exception org.xml.sax.SAXNotSupportedException When the
     *            XMLReader recognizes the property name but
     *            cannot determine its value at this time.
     * @see #setProperty
     */
    public Object getProperty (String name)
        throws SAXNotRecognizedException, SAXNotSupportedException;


    /**
     * Set the value of a property.
     *
     * <p>The property name is any fully-qualified URI.  It is
     * possible for an XMLReader to recognize a property name but
     * to be unable to change the current value.
     * Some property values may be immutable or mutable only
     * in specific contexts, such as before, during, or after
     * a parse.</p>
     *
     * <p>XMLReaders are not required to recognize setting
     * any specific property names, though a core set is defined by
     * SAX2.</p>
     *
     * <p>This method is also the standard mechanism for setting
     * extended handlers.</p>
     *
     * <p>
     *  设置属性的值。
     * 
     * <p>属性名称是任何完全限定的URI。 XMLReader可以识别属性名称,但无法更改当前值。一些属性值可能只在特定上下文中是不可变的或可变的,例如在解析之前,期间或之后。</p>
     * 
     *  <p> XMLReaders不需要识别设置任何特定的属性名称,虽然核心集由SAX2定义。</p>
     * 
     *  <p>此方法也是设置扩展处理程序的标准机制。</p>
     * 
     * 
     * @param name The property name, which is a fully-qualified URI.
     * @param value The requested value for the property.
     * @exception org.xml.sax.SAXNotRecognizedException If the property
     *            value can't be assigned or retrieved.
     * @exception org.xml.sax.SAXNotSupportedException When the
     *            XMLReader recognizes the property name but
     *            cannot set the requested value.
     */
    public void setProperty (String name, Object value)
        throws SAXNotRecognizedException, SAXNotSupportedException;



    ////////////////////////////////////////////////////////////////////
    // Event handlers.
    ////////////////////////////////////////////////////////////////////


    /**
     * Allow an application to register an entity resolver.
     *
     * <p>If the application does not register an entity resolver,
     * the XMLReader will perform its own default resolution.</p>
     *
     * <p>Applications may register a new or different resolver in the
     * middle of a parse, and the SAX parser must begin using the new
     * resolver immediately.</p>
     *
     * <p>
     *  允许应用程序注册实体解析器。
     * 
     *  <p>如果应用程序未注册实体解析器,则XMLReader将执行自己的默认解析器。</p>
     * 
     *  <p>应用程序可能在解析的过程中注册一个新的或不同的解析器,并且SAX解析器必须立即开始使用新的解析器。</p>
     * 
     * 
     * @param resolver The entity resolver.
     * @see #getEntityResolver
     */
    public void setEntityResolver (EntityResolver resolver);


    /**
     * Return the current entity resolver.
     *
     * <p>
     *  返回当前实体解析器。
     * 
     * 
     * @return The current entity resolver, or null if none
     *         has been registered.
     * @see #setEntityResolver
     */
    public EntityResolver getEntityResolver ();


    /**
     * Allow an application to register a DTD event handler.
     *
     * <p>If the application does not register a DTD handler, all DTD
     * events reported by the SAX parser will be silently ignored.</p>
     *
     * <p>Applications may register a new or different handler in the
     * middle of a parse, and the SAX parser must begin using the new
     * handler immediately.</p>
     *
     * <p>
     *  允许应用程序注册DTD事件处理程序。
     * 
     *  <p>如果应用程序未注册DTD处理程序,则SAX解析器报告的所有DTD事件将被忽略。</p>
     * 
     *  <p>应用程序可能在解析中间注册一个新的或不同的处理程序,并且SAX解析器必须立即开始使用新的处理程序。</p>
     * 
     * 
     * @param handler The DTD handler.
     * @see #getDTDHandler
     */
    public void setDTDHandler (DTDHandler handler);


    /**
     * Return the current DTD handler.
     *
     * <p>
     *  返回当前DTD处理程序。
     * 
     * 
     * @return The current DTD handler, or null if none
     *         has been registered.
     * @see #setDTDHandler
     */
    public DTDHandler getDTDHandler ();


    /**
     * Allow an application to register a content event handler.
     *
     * <p>If the application does not register a content handler, all
     * content events reported by the SAX parser will be silently
     * ignored.</p>
     *
     * <p>Applications may register a new or different handler in the
     * middle of a parse, and the SAX parser must begin using the new
     * handler immediately.</p>
     *
     * <p>
     *  允许应用程序注册内容事件处理程序。
     * 
     *  <p>如果应用程序未注册内容处理程序,则会默认忽略SAX解析器报告的所有内容事件。</p>
     * 
     * <p>应用程序可能在解析中间注册一个新的或不同的处理程序,并且SAX解析器必须立即开始使用新的处理程序。</p>
     * 
     * 
     * @param handler The content handler.
     * @see #getContentHandler
     */
    public void setContentHandler (ContentHandler handler);


    /**
     * Return the current content handler.
     *
     * <p>
     *  返回当前内容处理程序。
     * 
     * 
     * @return The current content handler, or null if none
     *         has been registered.
     * @see #setContentHandler
     */
    public ContentHandler getContentHandler ();


    /**
     * Allow an application to register an error event handler.
     *
     * <p>If the application does not register an error handler, all
     * error events reported by the SAX parser will be silently
     * ignored; however, normal processing may not continue.  It is
     * highly recommended that all SAX applications implement an
     * error handler to avoid unexpected bugs.</p>
     *
     * <p>Applications may register a new or different handler in the
     * middle of a parse, and the SAX parser must begin using the new
     * handler immediately.</p>
     *
     * <p>
     *  允许应用程序注册错误事件处理程序。
     * 
     *  <p>如果应用程序未注册错误处理程序,则SAX解析器报告的所有错误事件将被忽略;然而,正常处理可能不会继续。强烈建议所有SAX应用程序实现错误处理程序,以避免意外的错误。</p>
     * 
     *  <p>应用程序可能在解析中间注册一个新的或不同的处理程序,并且SAX解析器必须立即开始使用新的处理程序。</p>
     * 
     * 
     * @param handler The error handler.
     * @see #getErrorHandler
     */
    public void setErrorHandler (ErrorHandler handler);


    /**
     * Return the current error handler.
     *
     * <p>
     *  返回当前错误处理程序。
     * 
     * 
     * @return The current error handler, or null if none
     *         has been registered.
     * @see #setErrorHandler
     */
    public ErrorHandler getErrorHandler ();



    ////////////////////////////////////////////////////////////////////
    // Parsing.
    ////////////////////////////////////////////////////////////////////

    /**
     * Parse an XML document.
     *
     * <p>The application can use this method to instruct the XML
     * reader to begin parsing an XML document from any valid input
     * source (a character stream, a byte stream, or a URI).</p>
     *
     * <p>Applications may not invoke this method while a parse is in
     * progress (they should create a new XMLReader instead for each
     * nested XML document).  Once a parse is complete, an
     * application may reuse the same XMLReader object, possibly with a
     * different input source.
     * Configuration of the XMLReader object (such as handler bindings and
     * values established for feature flags and properties) is unchanged
     * by completion of a parse, unless the definition of that aspect of
     * the configuration explicitly specifies other behavior.
     * (For example, feature flags or properties exposing
     * characteristics of the document being parsed.)
     * </p>
     *
     * <p>During the parse, the XMLReader will provide information
     * about the XML document through the registered event
     * handlers.</p>
     *
     * <p>This method is synchronous: it will not return until parsing
     * has ended.  If a client application wants to terminate
     * parsing early, it should throw an exception.</p>
     *
     * <p>
     *  解析XML文档。
     * 
     *  <p>应用程序可以使用此方法指示XML阅读器开始从任何有效的输入源(字符流,字节流或URI)解析XML文档。</p>
     * 
     * <p>在解析正在进行时,应用程序不能调用此方法(它们应为每个嵌套的XML文档创建一个新的XMLReader)。一旦解析完成,应用程序可以重用相同的XMLReader对象,可能使用不同的输入源。
     * 除非配置的该方面的定义明确指定其他行为,否则XMLReader对象的配置(例如处理程序绑定和为特征标志和属性建立的值)不会完成分析。 (例如,功能标志或属性暴露正在解析的文档的特征。)。
     * </p>
     * 
     *  <p>在解析期间,XMLReader将通过注册的事件处理程序提供有关XML文档的信息。</p>
     * 
     *  <p>此方法是同步的：它将不会返回,直到解析结束。如果客户端应用程序想要尽早终止解析,则应抛出异常。</p>
     * 
     * 
     * @param input The input source for the top-level of the
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
     * @see #setContentHandler
     * @see #setErrorHandler
     */
    public void parse (InputSource input)
        throws IOException, SAXException;


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
     * 
     * @param systemId The system identifier (URI).
     * @exception org.xml.sax.SAXException Any SAX exception, possibly
     *            wrapping another exception.
     * @exception java.io.IOException An IO exception from the parser,
     *            possibly from a byte stream or character stream
     *            supplied by the application.
     * @see #parse(org.xml.sax.InputSource)
     */
    public void parse (String systemId)
        throws IOException, SAXException;

}
