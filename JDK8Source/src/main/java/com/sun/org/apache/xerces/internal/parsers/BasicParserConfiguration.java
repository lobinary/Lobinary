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
 *      http://www.apache.org/licenses/LICENSE-2.0
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

package com.sun.org.apache.xerces.internal.parsers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.util.FeatureState;
import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;
import com.sun.org.apache.xerces.internal.util.PropertyState;
import com.sun.org.apache.xerces.internal.util.Status;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;
import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;
import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParserConfiguration;

/**
 * A very basic parser configuration. This configuration class can
 * be used as a base class for custom parser configurations. The
 * basic parser configuration creates the symbol table (if not
 * specified at construction time) and manages all of the recognized
 * features and properties.
 * <p>
 * The basic parser configuration does <strong>not</strong> mandate
 * any particular pipeline configuration or the use of specific
 * components except for the symbol table. If even this is too much
 * for a basic parser configuration, the programmer can create a new
 * configuration class that implements the
 * <code>XMLParserConfiguration</code> interface.
 * <p>
 * Subclasses of the basic parser configuration can add their own
 * recognized features and properties by calling the
 * <code>addRecognizedFeature</code> and
 * <code>addRecognizedProperty</code> methods, respectively.
 * <p>
 * The basic parser configuration assumes that the configuration
 * will be made up of various parser components that implement the
 * <code>XMLComponent</code> interface. If subclasses of this
 * configuration create their own components for use in the
 * parser configuration, then each component should be added to
 * the list of components by calling the <code>addComponent</code>
 * method. The basic parser configuration will make sure to call
 * the <code>reset</code> method of each registered component
 * before parsing an instance document.
 * <p>
 * This class recognizes the following features and properties:
 * <ul>
 * <li>Features
 *  <ul>
 *   <li>http://xml.org/sax/features/validation</li>
 *   <li>http://xml.org/sax/features/namespaces</li>
 *   <li>http://xml.org/sax/features/external-general-entities</li>
 *   <li>http://xml.org/sax/features/external-parameter-entities</li>
 *  </ul>
 * <li>Properties
 *  <ul>
 *   <li>http://xml.org/sax/properties/xml-string</li>
 *   <li>http://apache.org/xml/properties/internal/symbol-table</li>
 *   <li>http://apache.org/xml/properties/internal/error-handler</li>
 *   <li>http://apache.org/xml/properties/internal/entity-resolver</li>
 *  </ul>
 * </ul>
 *
 * <p>
 *  一个非常基本的解析器配置。此配置类可以用作自定义解析器配置的基类。基本解析器配置创建符号表(如果在构建时未指定)并管理所有已识别的特征和属性。
 * <p>
 *  基本解析器配置<strong>不</strong>要求任何特定的管道配置或使用除符号表之外的特定组件。
 * 如果即使这对于基本的解析器配置太多了,程序员也可以创建一个实现<code> XMLParserConfiguration </code>接口的新配置类。
 * <p>
 * 基本解析器配置的子类可以通过分别调用<code> addRecognizedFeature </code>和<code> addRecognizedProperty </code>方法来添加自己已识别的
 * 特性和属性。
 * <p>
 *  基本解析器配置假设配置将由实现<code> XMLComponent </code>接口的各种解析器组件组成。
 * 如果此配置的子类创建自己的组件以在解析器配置中使用,则每个组件应通过调用<code> addComponent </code>方法添加到组件列表中。
 * 基本解析器配置将确保在解析实例文档之前调用每个注册组件的<code> reset </code>方法。
 * <p>
 *  此类识别以下功能和属性：
 * <ul>
 *  <li>功能
 * <ul>
 *  <li> http://xml.org/sax/features/validation </li> <li> http://xml.org/sax/features/namespaces </li> 
 * <li> http://xml.org / sax / features / external-general-entities </li> <li> http://xml.org/sax/featur
 * es/external-parameter-entities </li>。
 * </ul>
 *  <li>属性
 * <ul>
 *  <li> http://xml.org/sax/properties/xml-string </li> <li> http://apache.org/xml/properties/internal/s
 * ymbol-table </li> <li> http ：//apache.org/xml/properties/internal/error-handler </li> <li> http://apa
 * che.org/xml/properties/internal/entity-resolver </li>。
 * </ul>
 * </ul>
 * 
 * 
 * @author Arnaud  Le Hors, IBM
 * @author Andy Clark, IBM
 *
 * @version $Id: BasicParserConfiguration.java,v 1.6 2010-11-01 04:40:09 joehw Exp $
 */
public abstract class BasicParserConfiguration
    extends ParserConfigurationSettings
    implements XMLParserConfiguration {

    //
    // Constants
    //

    // feature identifiers

    /** Feature identifier: validation. */
    protected static final String VALIDATION =
        Constants.SAX_FEATURE_PREFIX + Constants.VALIDATION_FEATURE;

    /** Feature identifier: namespaces. */
    protected static final String NAMESPACES =
        Constants.SAX_FEATURE_PREFIX + Constants.NAMESPACES_FEATURE;

    /** Feature identifier: external general entities. */
    protected static final String EXTERNAL_GENERAL_ENTITIES =
        Constants.SAX_FEATURE_PREFIX + Constants.EXTERNAL_GENERAL_ENTITIES_FEATURE;

    /** Feature identifier: external parameter entities. */
    protected static final String EXTERNAL_PARAMETER_ENTITIES =
        Constants.SAX_FEATURE_PREFIX + Constants.EXTERNAL_PARAMETER_ENTITIES_FEATURE;

    // property identifiers

    /** Property identifier: xml string. */
    protected static final String XML_STRING =
        Constants.SAX_PROPERTY_PREFIX + Constants.XML_STRING_PROPERTY;

    /** Property identifier: symbol table. */
    protected static final String SYMBOL_TABLE =
        Constants.XERCES_PROPERTY_PREFIX + Constants.SYMBOL_TABLE_PROPERTY;

    /** Property identifier: error handler. */
    protected static final String ERROR_HANDLER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_HANDLER_PROPERTY;

    /** Property identifier: entity resolver. */
    protected static final String ENTITY_RESOLVER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_RESOLVER_PROPERTY;

    //
    // Data
    //

    // components (non-configurable)

    /** Symbol table. */
    protected SymbolTable fSymbolTable;


    // data

    /** Locale. */
    protected Locale fLocale;

    /** Components. */
    protected ArrayList fComponents;

    // handlers

    /** The document handler. */
    protected XMLDocumentHandler fDocumentHandler;

    /** The DTD handler. */
    protected XMLDTDHandler fDTDHandler;

    /** The DTD content model handler. */
    protected XMLDTDContentModelHandler fDTDContentModelHandler;

    /** Last component in the document pipeline */
    protected XMLDocumentSource fLastComponent;

    //
    // Constructors
    //

    /** Default Constructor. */
    protected BasicParserConfiguration() {
        this(null, null);
    } // <init>()

    /**
     * Constructs a parser configuration using the specified symbol table.
     *
     * <p>
     *  使用指定的符号表构造解析器配置。
     * 
     * 
     * @param symbolTable The symbol table to use.
     */
    protected BasicParserConfiguration(SymbolTable symbolTable) {
        this(symbolTable, null);
    } // <init>(SymbolTable)

    /**
     * Constructs a parser configuration using the specified symbol table
     * and parent settings.
     *
     * <p>
     *  使用指定的符号表和父设置构造解析器配置。
     * 
     * 
     * @param symbolTable    The symbol table to use.
     * @param parentSettings The parent settings.
     */
    protected BasicParserConfiguration(SymbolTable symbolTable,
                                       XMLComponentManager parentSettings) {
        super(parentSettings);

        // create a vector to hold all the components in use
        fComponents = new ArrayList();

        // create table for features and properties
        fFeatures = new HashMap();
        fProperties = new HashMap();

        // add default recognized features
        final String[] recognizedFeatures = {
                PARSER_SETTINGS,
            VALIDATION,
            NAMESPACES,
            EXTERNAL_GENERAL_ENTITIES,
            EXTERNAL_PARAMETER_ENTITIES,
        };
        addRecognizedFeatures(recognizedFeatures);
        fFeatures.put(PARSER_SETTINGS, Boolean.TRUE);
        // set state for default features
                fFeatures.put(VALIDATION, Boolean.FALSE);
                fFeatures.put(NAMESPACES, Boolean.TRUE);
                fFeatures.put(EXTERNAL_GENERAL_ENTITIES, Boolean.TRUE);
                fFeatures.put(EXTERNAL_PARAMETER_ENTITIES, Boolean.TRUE);

        // add default recognized properties
        final String[] recognizedProperties = {
            XML_STRING,
            SYMBOL_TABLE,
            ERROR_HANDLER,
            ENTITY_RESOLVER,
        };
        addRecognizedProperties(recognizedProperties);

        if (symbolTable == null) {
            symbolTable = new SymbolTable();
        }
        fSymbolTable = symbolTable;
        fProperties.put(SYMBOL_TABLE, fSymbolTable);

    } // <init>(SymbolTable)

    /**
     * Adds a component to the parser configuration. This method will
     * also add all of the component's recognized features and properties
     * to the list of default recognized features and properties.
     *
     * <p>
     * 向解析器配置中添加一个组件。此方法还会将所有组件的已识别要素和属性添加到默认的已识别要素和属性列表中。
     * 
     * 
     * @param component The component to add.
     */
    protected void addComponent(XMLComponent component) {

        // don't add a component more than once
        if (fComponents.contains(component)) {
            return;
        }
        fComponents.add(component);

        // register component's recognized features
        String[] recognizedFeatures = component.getRecognizedFeatures();
        addRecognizedFeatures(recognizedFeatures);

        // register component's recognized properties
        String[] recognizedProperties = component.getRecognizedProperties();
        addRecognizedProperties(recognizedProperties);

        // set default values
        if (recognizedFeatures != null) {
            for (int i = 0; i < recognizedFeatures.length; i++) {
                String featureId = recognizedFeatures[i];
                Boolean state = component.getFeatureDefault(featureId);
                if (state != null) {
                    super.setFeature(featureId, state.booleanValue());
                }
            }
        }
        if (recognizedProperties != null) {
            for (int i = 0; i < recognizedProperties.length; i++) {
                String propertyId = recognizedProperties[i];
                Object value = component.getPropertyDefault(propertyId);
                if (value != null) {
                    super.setProperty(propertyId, value);
                }
            }
        }

    } // addComponent(XMLComponent)

    //
    // XMLParserConfiguration methods
    //

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
     *
     * <p>
     *  解析XML文档。
     * <p>
     *  解析器可以使用此方法指示此配置开始从任何有效的输入源(字符流,字节流或URI)解析XML文档。
     * <p>
     *  解析正在进行时,解析器不能调用此方法。一旦解析完成,解析器就可以解析另一个XML文档。
     * <p>
     *  此方法是同步的：它将不会返回,直到解析结束。如果客户端应用程序想要尽早终止解析,它应该抛出异常。
     * 
     * 
     * @param inputSource The input source for the top-level of the
     *               XML document.
     *
     * @exception XNIException Any XNI exception, possibly wrapping
     *                         another exception.
     * @exception IOException  An IO exception from the parser, possibly
     *                         from a byte stream or character stream
     *                         supplied by the parser.
     */
    public abstract void parse(XMLInputSource inputSource)
        throws XNIException, IOException;

    /**
     * Sets the document handler on the last component in the pipeline
     * to receive information about the document.
     *
     * <p>
     *  在管道中的最后一个组件上设置文档处理程序,以接收有关文档的信息。
     * 
     * 
     * @param documentHandler   The document handler.
     */
    public void setDocumentHandler(XMLDocumentHandler documentHandler) {
        fDocumentHandler = documentHandler;
        if (fLastComponent != null) {
            fLastComponent.setDocumentHandler(fDocumentHandler);
            if (fDocumentHandler !=null){
                fDocumentHandler.setDocumentSource(fLastComponent);
            }
        }
    } // setDocumentHandler(XMLDocumentHandler)

    /** Returns the registered document handler. */
    public XMLDocumentHandler getDocumentHandler() {
        return fDocumentHandler;
    } // getDocumentHandler():XMLDocumentHandler

    /**
     * Sets the DTD handler.
     *
     * <p>
     *  设置DTD处理程序。
     * 
     * 
     * @param dtdHandler The DTD handler.
     */
    public void setDTDHandler(XMLDTDHandler dtdHandler) {
        fDTDHandler = dtdHandler;
    } // setDTDHandler(XMLDTDHandler)

    /** Returns the registered DTD handler. */
    public XMLDTDHandler getDTDHandler() {
        return fDTDHandler;
    } // getDTDHandler():XMLDTDHandler

    /**
     * Sets the DTD content model handler.
     *
     * <p>
     *  设置DTD内容模型处理程序。
     * 
     * 
     * @param handler The DTD content model handler.
     */
    public void setDTDContentModelHandler(XMLDTDContentModelHandler handler) {
        fDTDContentModelHandler = handler;
    } // setDTDContentModelHandler(XMLDTDContentModelHandler)

    /** Returns the registered DTD content model handler. */
    public XMLDTDContentModelHandler getDTDContentModelHandler() {
        return fDTDContentModelHandler;
    } // getDTDContentModelHandler():XMLDTDContentModelHandler

    /**
     * Sets the resolver used to resolve external entities. The EntityResolver
     * interface supports resolution of public and system identifiers.
     *
     * <p>
     *  设置用于解析外部实体的解析器。 EntityResolver接口支持公共和系统标识符的解析。
     * 
     * 
     * @param resolver The new entity resolver. Passing a null value will
     *                 uninstall the currently installed resolver.
     */
    public void setEntityResolver(XMLEntityResolver resolver) {
        // REVISIT: Should this be a property?
        fProperties.put(ENTITY_RESOLVER, resolver);
    } // setEntityResolver(XMLEntityResolver)

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
    public XMLEntityResolver getEntityResolver() {
        // REVISIT: Should this be a property?
        return (XMLEntityResolver)fProperties.get(ENTITY_RESOLVER);
    } // getEntityResolver():XMLEntityResolver

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
     * <p>应用程序可能在解析中间注册一个新的或不同的处理程序,并且SAX解析器必须立即开始使用新的处理程序。</p>
     * 
     * 
     * @param errorHandler The error handler.
     * @exception java.lang.NullPointerException If the handler
     *            argument is null.
     * @see #getErrorHandler
     */
    public void setErrorHandler(XMLErrorHandler errorHandler) {
        // REVISIT: Should this be a property?
        fProperties.put(ERROR_HANDLER, errorHandler);
    } // setErrorHandler(XMLErrorHandler)

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
    public XMLErrorHandler getErrorHandler() {
        // REVISIT: Should this be a property?
        return (XMLErrorHandler)fProperties.get(ERROR_HANDLER);
    } // getErrorHandler():XMLErrorHandler

    /**
     * Set the state of a feature.
     *
     * Set the state of any feature in a SAX2 parser.  The parser
     * might not recognize the feature, and if it does recognize
     * it, it might not be able to fulfill the request.
     *
     * <p>
     *  设置要素的状态。
     * 
     *  设置SAX2解析器中任何功能的状态。解析器可能无法识别该功能,如果它识别它,它可能无法满足该请求。
     * 
     * 
     * @param featureId The unique identifier (URI) of the feature.
     * @param state The requested state of the feature (true or false).
     *
     * @exception com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException If the
     *            requested feature is not known.
     */
    public void setFeature(String featureId, boolean state)
        throws XMLConfigurationException {

        // forward to every component
        int count = fComponents.size();
        for (int i = 0; i < count; i++) {
            XMLComponent c = (XMLComponent) fComponents.get(i);
            c.setFeature(featureId, state);
        }
        // save state if noone "objects"
        super.setFeature(featureId, state);

    } // setFeature(String,boolean)

    /**
     * setProperty
     *
     * <p>
     *  setProperty
     * 
     * 
     * @param propertyId
     * @param value
     */
    public void setProperty(String propertyId, Object value)
        throws XMLConfigurationException {

        // forward to every component
        int count = fComponents.size();
        for (int i = 0; i < count; i++) {
            XMLComponent c = (XMLComponent) fComponents.get(i);
            c.setProperty(propertyId, value);
        }

        // store value if noone "objects"
        super.setProperty(propertyId, value);

    } // setProperty(String,Object)

    /**
     * Set the locale to use for messages.
     *
     * <p>
     *  设置要用于消息的区域设置。
     * 
     * 
     * @param locale The locale object to use for localization of messages.
     *
     * @exception XNIException Thrown if the parser does not support the
     *                         specified locale.
     */
    public void setLocale(Locale locale) throws XNIException {
        fLocale = locale;
    } // setLocale(Locale)

    /** Returns the locale. */
    public Locale getLocale() {
        return fLocale;
    } // getLocale():Locale

    //
    // Protected methods
    //

    /**
     * reset all components before parsing and namespace context
     * <p>
     *  在解析和命名空间上下文之前重置所有组件
     * 
     */
    protected void reset() throws XNIException {

        // reset every component
        int count = fComponents.size();
        for (int i = 0; i < count; i++) {
            XMLComponent c = (XMLComponent) fComponents.get(i);
            c.reset(this);
        }

    } // reset()

    /**
     * Check a property. If the property is known and supported, this method
     * simply returns. Otherwise, the appropriate exception is thrown.
     *
     * <p>
     *  检查属性。如果属性是已知的和支持的,这个方法简单地返回。否则,抛出适当的异常。
     * 
     * 
     * @param propertyId The unique identifier (URI) of the property
     *                   being set.
     * @exception com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException If the
     *            requested feature is not known or supported.
     */
    protected PropertyState checkProperty(String propertyId)
        throws XMLConfigurationException {

        // special cases
        if (propertyId.startsWith(Constants.SAX_PROPERTY_PREFIX)) {
            final int suffixLength = propertyId.length() - Constants.SAX_PROPERTY_PREFIX.length();

            //
            // http://xml.org/sax/properties/xml-string
            // Value type: String
            // Access: read-only
            //   Get the literal string of characters associated with the
            //   current event.  If the parser recognises and supports this
            //   property but is not currently parsing text, it should return
            //   null (this is a good way to check for availability before the
            //   parse begins).
            //
            if (suffixLength == Constants.XML_STRING_PROPERTY.length() &&
                propertyId.endsWith(Constants.XML_STRING_PROPERTY)) {
                // REVISIT - we should probably ask xml-dev for a precise
                // definition of what this is actually supposed to return, and
                // in exactly which circumstances.
                return PropertyState.NOT_SUPPORTED;
            }
        }

        // check property
        return super.checkProperty(propertyId);

    } // checkProperty(String)


    /**
     * Check a feature. If feature is know and supported, this method simply
     * returns. Otherwise, the appropriate exception is thrown.
     *
     * <p>
     *  检查功能。如果特性是知道和支持的,这个方法简单地返回。否则,抛出适当的异常。
     * 
     * @param featureId The unique identifier (URI) of the feature.
     *
     * @throws XMLConfigurationException Thrown for configuration error.
     *                                   In general, components should
     *                                   only throw this exception if
     *                                   it is <strong>really</strong>
     *                                   a critical error.
     */
    protected FeatureState checkFeature(String featureId)
        throws XMLConfigurationException {

        //
        // Xerces Features
        //
        if (featureId.startsWith(Constants.XERCES_FEATURE_PREFIX)) {
            final int suffixLength = featureId.length() - Constants.XERCES_FEATURE_PREFIX.length();

            //
            // special performance feature: no one by component manager is allowed to set it
            //
            if (suffixLength == Constants.PARSER_SETTINGS.length() &&
                featureId.endsWith(Constants.PARSER_SETTINGS)) {
                return FeatureState.NOT_SUPPORTED;
            }
        }

        return super.checkFeature(featureId);
     } // checkFeature(String)


} // class BasicParserConfiguration
