/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni.parser;

import java.io.IOException;
import java.util.Locale;

import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;
import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;
import com.sun.org.apache.xerces.internal.xni.XNIException;

/**
 * Represents a parser configuration. The parser configuration maintains
 * a table of recognized features and properties, assembles components
 * for the parsing pipeline, and is responsible for initiating parsing
 * of an XML document.
 * <p>
 * By separating the configuration of a parser from the specific parser
 * instance, applications can create new configurations and re-use the
 * existing parser components and external API generators (e.g. the
 * DOMParser and SAXParser).
 * <p>
 * The internals of any specific parser configuration instance are hidden.
 * Therefore, each configuration may implement the parsing mechanism any
 * way necessary. However, the parser configuration should follow these
 * guidelines:
 * <ul>
 *  <li>
 *   Call the <code>reset</code> method on each component before parsing.
 *   This is only required if the configuration is re-using existing
 *   components that conform to the <code>XMLComponent</code> interface.
 *   If the configuration uses all custom parts, then it is free to
 *   implement everything as it sees fit as long as it follows the
 *   other guidelines.
 *  </li>
 *  <li>
 *   Call the <code>setFeature</code> and <code>setProperty</code> method
 *   on each component during parsing to propagate features and properties
 *   that have changed. This is only required if the configuration is
 *   re-using existing components that conform to the <code>XMLComponent</code>
 *   interface. If the configuration uses all custom parts, then it is free
 *   to implement everything as it sees fit as long as it follows the other
 *   guidelines.
 *  </li>
 *  <li>
 *   Pass the same unique String references for all symbols that are
 *   propagated to the registered handlers. Symbols include, but may not
 *   be limited to, the names of elements and attributes (including their
 *   uri, prefix, and localpart). This is suggested but not an absolute
 *   must. However, the standard parser components may require access to
 *   the same symbol table for creation of unique symbol references to be
 *   propagated in the XNI pipeline.
 *  </li>
 * </ul>
 *
 * <p>
 *  表示解析器配置。解析器配置维护识别的特征和属性的表,为解析流水线组装组件,并且负责启动XML文档的解析。
 * <p>
 *  通过将解析器的配置与特定解析器实例分离,应用程序可以创建新的配置并重新使用现有的解析器组件和外部API生成器(例如DOMParser和SAXParser)。
 * <p>
 *  任何特定解析器配置实例的内部被隐藏。因此,每个配置可以以任何必要的方式实现解析机制。但是,解析器配置应遵循以下准则：
 * <ul>
 * <li>
 * 在解析之前,对每个组件调用<code> reset </code>方法。只有在配置重新使用符合<code> XMLComponent </code>接口的现有组件时,才需要这样做。
 * 如果配置使用所有自定义部件,那么只要遵循其他指南,就可以自由地实现所有适合的内容。
 * </li>
 * <li>
 *  在解析期间调用每个组件上的<code> setFeature </code>和<code> setProperty </code>方法来传播已更改的功能和属性。
 * 只有在配置重新使用符合<code> XMLComponent </code>接口的现有组件时,才需要这样做。如果配置使用所有自定义部件,那么只要遵循其他指南,就可以自由地实现所有适合的内容。
 * </li>
 * <li>
 *  为传播到注册的处理程序的所有符号传递相同的唯一字符串引用。符号包括但不限于元素和属性的名称(包括其uri,prefix和localpart)。这是建议,但不是绝对必须。
 * 然而,标准解析器组件可能需要访问相同的符号表以创建要在XNI管线中传播的唯一符号引用。
 * </li>
 * </ul>
 * 
 * 
 * @author Arnaud  Le Hors, IBM
 * @author Andy Clark, IBM
 *
 */
public interface XMLParserConfiguration
    extends XMLComponentManager {

    //
    // XMLParserConfiguration methods
    //

    // parsing

    /**
     * Parse an XML document.
     * <p>
     * The parser can use this method to instruct this configuration
     * to begin parsing an XML document from any valid input source
     * (a character stream, a byte stream, or a URI).
     * <p>
     * Parsers may not invoke this method while a parse is in progress.
     * Once a parse is complete, the parser may then parse another XML
     * document.
     * <p>
     * This method is synchronous: it will not return until parsing
     * has ended.  If a client application wants to terminate
     * parsing early, it should throw an exception.
     * <p>
     * When this method returns, all characters streams and byte streams
     * opened by the parser are closed.
     *
     * <p>
     *  解析XML文档。
     * <p>
     *  解析器可以使用此方法指示此配置开始从任何有效的输入源(字符流,字节流或URI)解析XML文档。
     * <p>
     * 解析正在进行时,解析器不能调用此方法。一旦解析完成,解析器就可以解析另一个XML文档。
     * <p>
     *  此方法是同步的：它将不会返回,直到解析结束。如果客户端应用程序想要尽早终止解析,它应该抛出异常。
     * <p>
     *  当此方法返回时,解析器打开的所有字符流和字节流都将关闭。
     * 
     * 
     * @param inputSource The input source for the top-level of the
     *                    XML document.
     *
     * @exception XNIException Any XNI exception, possibly wrapping
     *                         another exception.
     * @exception IOException  An IO exception from the parser, possibly
     *                         from a byte stream or character stream
     *                         supplied by the parser.
     */
    public void parse(XMLInputSource inputSource)
        throws XNIException, IOException;

    // generic configuration

    /**
     * Allows a parser to add parser specific features to be recognized
     * and managed by the parser configuration.
     *
     * <p>
     *  允许解析器添加解析器特定的功能,以便由解析器配置识别和管理。
     * 
     * 
     * @param featureIds An array of the additional feature identifiers
     *                   to be recognized.
     */
    public void addRecognizedFeatures(String[] featureIds);

    /**
     * Sets the state of a feature. This method is called by the parser
     * and gets propagated to components in this parser configuration.
     *
     * <p>
     *  设置要素的状态。此方法由解析器调用,并在此解析器配置中传播到组件。
     * 
     * 
     * @param featureId The feature identifier.
     * @param state     The state of the feature.
     *
     * @throws XMLConfigurationException Thrown if there is a configuration
     *                                   error.
     */
    public void setFeature(String featureId, boolean state)
        throws XMLConfigurationException;

    /**
     * Returns the state of a feature.
     *
     * <p>
     *  返回要素的状态。
     * 
     * 
     * @param featureId The feature identifier.
     *
     * @throws XMLConfigurationException Thrown if there is a configuration
     *                                   error.
     */
    public boolean getFeature(String featureId)
        throws XMLConfigurationException;

    /**
     * Allows a parser to add parser specific properties to be recognized
     * and managed by the parser configuration.
     *
     * <p>
     *  允许解析器添加解析器特定属性,以便由解析器配置识别和管理。
     * 
     * 
     * @param propertyIds An array of the additional property identifiers
     *                    to be recognized.
     */
    public void addRecognizedProperties(String[] propertyIds);

    /**
     * Sets the value of a property. This method is called by the parser
     * and gets propagated to components in this parser configuration.
     *
     * <p>
     *  设置属性的值。此方法由解析器调用,并在此解析器配置中传播到组件。
     * 
     * 
     * @param propertyId The property identifier.
     * @param value      The value of the property.
     *
     * @throws XMLConfigurationException Thrown if there is a configuration
     *                                   error.
     */
    public void setProperty(String propertyId, Object value)
        throws XMLConfigurationException;

    /**
     * Returns the value of a property.
     *
     * <p>
     *  返回属性的值。
     * 
     * 
     * @param propertyId The property identifier.
     *
     * @throws XMLConfigurationException Thrown if there is a configuration
     *                                   error.
     */
    public Object getProperty(String propertyId)
        throws XMLConfigurationException;

    // handlers

    /**
     * Sets the error handler.
     *
     * <p>
     *  设置错误处理程序。
     * 
     * 
     * @param errorHandler The error resolver.
     */
    public void setErrorHandler(XMLErrorHandler errorHandler);

    /** Returns the registered error handler. */
    public XMLErrorHandler getErrorHandler();

    /**
     * Sets the document handler to receive information about the document.
     *
     * <p>
     *  设置文档处理程序以接收有关文档的信息。
     * 
     * 
     * @param documentHandler The document handler.
     */
    public void setDocumentHandler(XMLDocumentHandler documentHandler);

    /** Returns the registered document handler. */
    public XMLDocumentHandler getDocumentHandler();

    /**
     * Sets the DTD handler.
     *
     * <p>
     *  设置DTD处理程序。
     * 
     * 
     * @param dtdHandler The DTD handler.
     */
    public void setDTDHandler(XMLDTDHandler dtdHandler);

    /** Returns the registered DTD handler. */
    public XMLDTDHandler getDTDHandler();

    /**
     * Sets the DTD content model handler.
     *
     * <p>
     *  设置DTD内容模型处理程序。
     * 
     * 
     * @param dtdContentModelHandler The DTD content model handler.
     */
    public void setDTDContentModelHandler(XMLDTDContentModelHandler dtdContentModelHandler);

    /** Returns the registered DTD content model handler. */
    public XMLDTDContentModelHandler getDTDContentModelHandler();

    // other settings

    /**
     * Sets the entity resolver.
     *
     * <p>
     *  设置实体解析器。
     * 
     * 
     * @param entityResolver The new entity resolver.
     */
    public void setEntityResolver(XMLEntityResolver entityResolver);

    /** Returns the registered entity resolver. */
    public XMLEntityResolver getEntityResolver();

    /**
     * Set the locale to use for messages.
     *
     * <p>
     *  设置要用于消息的区域设置。
     * 
     * @param locale The locale object to use for localization of messages.
     *
     * @exception XNIException Thrown if the parser does not support the
     *                         specified locale.
     */
    public void setLocale(Locale locale) throws XNIException;

    /** Returns the locale. */
    public Locale getLocale();

} // interface XMLParserConfiguration
