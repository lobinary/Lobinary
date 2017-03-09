/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2000-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)2000-2002 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得将"Xerces"和"Apache Software Foundation"名称用于支持或推广从本软件衍生的产品。如需书面许可,请联系apache@apache.org。
 * 
 *  未经Apache软件基金会事先书面许可,从本软件派生的产品可能不会被称为"Apache",也不可能出现在他们的名字中。
 * 
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款,最初是基于软件版权(c)1999,国际商业机器公司,http://www.apache.org。
 * 有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

package com.sun.org.apache.xerces.internal.impl;

import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.util.XMLSymbols;
import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentFilter;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;

/**
 * This class performs namespace binding on the startElement and endElement
 * method calls and passes all other methods through to the registered
 * document handler. This class can be configured to only pass the
 * start and end prefix mappings (start/endPrefixMapping).
 * <p>
 * This component requires the following features and properties from the
 * component manager that uses it:
 * <ul>
 *  <li>http://xml.org/sax/features/namespaces</li>
 *  <li>http://apache.org/xml/properties/internal/symbol-table</li>
 *  <li>http://apache.org/xml/properties/internal/error-reporter</li>
 * </ul>
 *
 * @xerces.internal
 *
 * <p>
 *  此类在startElement和endElement方法调用上执行命名空间绑定,并将所有其他方法传递到注册的文档处理程序。
 * 此类可以配置为只传递开始和结束前缀映射(start / endPrefixMapping)。
 * <p>
 * 此组件需要使用它的组件管理器中的以下功能和属性：
 * <ul>
 *  <li> http://xml.org/sax/features/namespaces </li> <li> http://apache.org/xml/properties/internal/sym
 * bol-table </li> <li> http：/ /apache.org/xml/properties/internal/error-reporter </li>。
 * </ul>
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Andy Clark, IBM
 *
 * @version $Id: XMLNamespaceBinder.java,v 1.4 2010-11-01 04:39:41 joehw Exp $
 */
public class XMLNamespaceBinder
    implements XMLComponent, XMLDocumentFilter {

    //
    // Constants
    //

    // feature identifiers

    /** Feature identifier: namespaces. */
    protected static final String NAMESPACES =
        Constants.SAX_FEATURE_PREFIX + Constants.NAMESPACES_FEATURE;

    // property identifiers

    /** Property identifier: symbol table. */
    protected static final String SYMBOL_TABLE =
        Constants.XERCES_PROPERTY_PREFIX + Constants.SYMBOL_TABLE_PROPERTY;

    /** Property identifier: error reporter. */
    protected static final String ERROR_REPORTER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_REPORTER_PROPERTY;

    // recognized features and properties

    /** Recognized features. */
    private static final String[] RECOGNIZED_FEATURES = {
        NAMESPACES,
    };

    /** Feature defaults. */
    private static final Boolean[] FEATURE_DEFAULTS = {
        null,
    };

    /** Recognized properties. */
    private static final String[] RECOGNIZED_PROPERTIES = {
        SYMBOL_TABLE,
        ERROR_REPORTER,
    };

    /** Property defaults. */
    private static final Object[] PROPERTY_DEFAULTS = {
        null,
        null,
    };

    //
    // Data
    //

    // features

    /** Namespaces. */
    protected boolean fNamespaces;

    // properties

    /** Symbol table. */
    protected SymbolTable fSymbolTable;

    /** Error reporter. */
    protected XMLErrorReporter fErrorReporter;

    // handlers

    /** Document handler. */
    protected XMLDocumentHandler fDocumentHandler;

    protected XMLDocumentSource fDocumentSource;

    // settings

    /** Only pass start and end prefix mapping events. */
    protected boolean fOnlyPassPrefixMappingEvents;

    // shared context

    /** Namespace context. */
    private NamespaceContext fNamespaceContext;

    // temp vars

    /** Attribute QName. */
    private QName fAttributeQName = new QName();

    //
    // Constructors
    //

    /** Default constructor. */
    public XMLNamespaceBinder() {
    } // <init>()

    //
    // Public methods
    //

    // settings

    /**
     * Sets whether the namespace binder only passes the prefix mapping
     * events to the registered document handler or passes all document
     * events.
     *
     * <p>
     *  设置命名空间绑定器是否仅将前缀映射事件传递到已注册的文档处理程序,或传递所有文档事件。
     * 
     * 
     * @param onlyPassPrefixMappingEvents True to pass only the prefix
     *                                    mapping events; false to pass
     *                                    all events.
     */
    public void setOnlyPassPrefixMappingEvents(boolean onlyPassPrefixMappingEvents) {
        fOnlyPassPrefixMappingEvents = onlyPassPrefixMappingEvents;
    } // setOnlyPassPrefixMappingEvents(boolean)

    /**
     * Returns true if the namespace binder only passes the prefix mapping
     * events to the registered document handler; false if the namespace
     * binder passes all document events.
     * <p>
     *  如果命名空间绑定器仅将前缀映射事件传递到注册的文档处理程序,则返回true; false如果命名空间绑定器传递所有文档事件。
     * 
     */
    public boolean getOnlyPassPrefixMappingEvents() {
        return fOnlyPassPrefixMappingEvents;
    } // getOnlyPassPrefixMappingEvents():boolean

    //
    // XMLComponent methods
    //

    /**
     * Resets the component. The component can query the component manager
     * about any features and properties that affect the operation of the
     * component.
     *
     * <p>
     *  复位组件。组件可以向组件管理器查询影响组件操作的任何特征和属性。
     * 
     * 
     * @param componentManager The component manager.
     *
     * @throws SAXException Thrown by component on initialization error.
     *                      For example, if a feature or property is
     *                      required for the operation of the component, the
     *                      component manager may throw a
     *                      SAXNotRecognizedException or a
     *                      SAXNotSupportedException.
     */
    public void reset(XMLComponentManager componentManager)
        throws XNIException {

        // features
        fNamespaces = componentManager.getFeature(NAMESPACES, true);

        // Xerces properties
        fSymbolTable = (SymbolTable)componentManager.getProperty(SYMBOL_TABLE);
        fErrorReporter = (XMLErrorReporter)componentManager.getProperty(ERROR_REPORTER);

    } // reset(XMLComponentManager)

    /**
     * Returns a list of feature identifiers that are recognized by
     * this component. This method may return null if no features
     * are recognized by this component.
     * <p>
     *  返回此组件可识别的要素标识符列表。如果此组件未识别任何功能,此方法可能返回null。
     * 
     */
    public String[] getRecognizedFeatures() {
        return (String[])(RECOGNIZED_FEATURES.clone());
    } // getRecognizedFeatures():String[]

    /**
     * Sets the state of a feature. This method is called by the component
     * manager any time after reset when a feature changes state.
     * <p>
     * <strong>Note:</strong> Components should silently ignore features
     * that do not affect the operation of the component.
     *
     * <p>
     *  设置要素的状态。当特性改变状态时,组件管理器在重置后任何时候调用此方法。
     * <p>
     *  <strong>注意：</strong>组件应默认忽略不影响组件操作的功能。
     * 
     * 
     * @param featureId The feature identifier.
     * @param state     The state of the feature.
     *
     * @throws SAXNotRecognizedException The component should not throw
     *                                   this exception.
     * @throws SAXNotSupportedException The component should not throw
     *                                  this exception.
     */
    public void setFeature(String featureId, boolean state)
        throws XMLConfigurationException {
    } // setFeature(String,boolean)

    /**
     * Returns a list of property identifiers that are recognized by
     * this component. This method may return null if no properties
     * are recognized by this component.
     * <p>
     *  返回此组件可识别的属性标识符列表。如果此组件未识别任何属性,此方法可能返回null。
     * 
     */
    public String[] getRecognizedProperties() {
        return (String[])(RECOGNIZED_PROPERTIES.clone());
    } // getRecognizedProperties():String[]

    /**
     * Sets the value of a property during parsing.
     *
     * <p>
     *  在解析期间设置属性的值。
     * 
     * 
     * @param propertyId
     * @param value
     */
    public void setProperty(String propertyId, Object value)
        throws XMLConfigurationException {

        // Xerces properties
        if (propertyId.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
                final int suffixLength = propertyId.length() - Constants.XERCES_PROPERTY_PREFIX.length();

            if (suffixLength == Constants.SYMBOL_TABLE_PROPERTY.length() &&
                propertyId.endsWith(Constants.SYMBOL_TABLE_PROPERTY)) {
                fSymbolTable = (SymbolTable)value;
            }
            else if (suffixLength == Constants.ERROR_REPORTER_PROPERTY.length() &&
                propertyId.endsWith(Constants.ERROR_REPORTER_PROPERTY)) {
                fErrorReporter = (XMLErrorReporter)value;
            }
            return;
        }

    } // setProperty(String,Object)

    /**
     * Returns the default state for a feature, or null if this
     * component does not want to report a default value for this
     * feature.
     *
     * <p>
     *  返回特征的默认状态,如果此组件不希望报告此特征的默认值,则返回null。
     * 
     * 
     * @param featureId The feature identifier.
     *
     * @since Xerces 2.2.0
     */
    public Boolean getFeatureDefault(String featureId) {
        for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
            if (RECOGNIZED_FEATURES[i].equals(featureId)) {
                return FEATURE_DEFAULTS[i];
            }
        }
        return null;
    } // getFeatureDefault(String):Boolean

    /**
     * Returns the default state for a property, or null if this
     * component does not want to report a default value for this
     * property.
     *
     * <p>
     * 返回属性的默认状态,如果此组件不希望报告此属性的默认值,则返回null。
     * 
     * 
     * @param propertyId The property identifier.
     *
     * @since Xerces 2.2.0
     */
    public Object getPropertyDefault(String propertyId) {
        for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
            if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
                return PROPERTY_DEFAULTS[i];
            }
        }
        return null;
    } // getPropertyDefault(String):Object

    //
    // XMLDocumentSource methods
    //

    /** Sets the document handler to receive information about the document. */
    public void setDocumentHandler(XMLDocumentHandler documentHandler) {
        fDocumentHandler = documentHandler;
    } // setDocumentHandler(XMLDocumentHandler)

    /** Returns the document handler */
    public XMLDocumentHandler getDocumentHandler() {
        return fDocumentHandler;
    } // setDocumentHandler(XMLDocumentHandler)


    //
    // XMLDocumentHandler methods
    //

    /** Sets the document source */
    public void setDocumentSource(XMLDocumentSource source){
        fDocumentSource = source;
    } // setDocumentSource

    /** Returns the document source */
    public XMLDocumentSource getDocumentSource (){
        return fDocumentSource;
    } // getDocumentSource


    /**
     * This method notifies the start of a general entity.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  此方法通知一般实体的开始。
     * <p>
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param name     The name of the general entity.
     * @param identifier The resource identifier.
     * @param encoding The auto-detected IANA encoding name of the entity
     *                 stream. This value will be null in those situations
     *                 where the entity encoding is not auto-detected (e.g.
     *                 internal entities or a document entity that is
     *                 parsed from a java.io.Reader).
     * @param augs     Additional information that may include infoset augmentations
     *
     * @exception XNIException Thrown by handler to signal an error.
     */
    public void startGeneralEntity(String name,
                                   XMLResourceIdentifier identifier,
                                   String encoding, Augmentations augs)
        throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.startGeneralEntity(name, identifier, encoding, augs);
        }
    } // startEntity(String,String,String,String,String)

    /**
     * Notifies of the presence of a TextDecl line in an entity. If present,
     * this method will be called immediately following the startEntity call.
     * <p>
     * <strong>Note:</strong> This method will never be called for the
     * document entity; it is only called for external general entities
     * referenced in document content.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  通知实体中存在TextDecl行。如果存在,此方法将在startEntity调用之后立即调用。
     * <p>
     *  <strong>注意：</strong>此方法将永远不会为文档实体调用;它只被要求在文档内容中引用的外部通用实体。
     * <p>
     *  <strong>注意</strong>：对于作为属性值一部分显示的实体引用,不调用此方法。
     * 
     * 
     * @param version  The XML version, or null if not specified.
     * @param encoding The IANA encoding name of the entity.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void textDecl(String version, String encoding, Augmentations augs)
        throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.textDecl(version, encoding, augs);
        }
    } // textDecl(String,String)

    /**
     * The start of the document.
     *
     * <p>
     *  文档的开始。
     * 
     * 
     * @param locator  The system identifier of the entity if the entity
     *                 is external, null otherwise.
     * @param encoding The auto-detected IANA encoding name of the entity
     *                 stream. This value will be null in those situations
     *                 where the entity encoding is not auto-detected (e.g.
     *                 internal entities or a document entity that is
     *                 parsed from a java.io.Reader).
     * @param namespaceContext
     *                 The namespace context in effect at the
     *                 start of this document.
     *                 This object represents the current context.
     *                 Implementors of this class are responsible
     *                 for copying the namespace bindings from the
     *                 the current context (and its parent contexts)
     *                 if that information is important.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
        public void startDocument(XMLLocator locator, String encoding,
                                NamespaceContext namespaceContext, Augmentations augs)
                                      throws XNIException {
                fNamespaceContext = namespaceContext;

                if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
                        fDocumentHandler.startDocument(locator, encoding, namespaceContext, augs);
                }
        } // startDocument(XMLLocator,String)

    /**
     * Notifies of the presence of an XMLDecl line in the document. If
     * present, this method will be called immediately following the
     * startDocument call.
     *
     * <p>
     *  通知文档中存在XMLDecl行。如果存在,此方法将在startDocument调用后立即调用。
     * 
     * 
     * @param version    The XML version.
     * @param encoding   The IANA encoding name of the document, or null if
     *                   not specified.
     * @param standalone The standalone value, or null if not specified.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void xmlDecl(String version, String encoding, String standalone, Augmentations augs)
        throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.xmlDecl(version, encoding, standalone, augs);
        }
    } // xmlDecl(String,String,String)

    /**
     * Notifies of the presence of the DOCTYPE line in the document.
     *
     * <p>
     *  通知文档中DOCTYPE行的存在。
     * 
     * 
     * @param rootElement The name of the root element.
     * @param publicId    The public identifier if an external DTD or null
     *                    if the external DTD is specified using SYSTEM.
     * @param systemId    The system identifier if an external DTD, null
     *                    otherwise.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void doctypeDecl(String rootElement,
                            String publicId, String systemId, Augmentations augs)
        throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.doctypeDecl(rootElement, publicId, systemId, augs);
        }
    } // doctypeDecl(String,String,String)

    /**
     * A comment.
     *
     * <p>
     *  评论。
     * 
     * 
     * @param text The text in the comment.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by application to signal an error.
     */
    public void comment(XMLString text, Augmentations augs) throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.comment(text, augs);
        }
    } // comment(XMLString)

    /**
     * A processing instruction. Processing instructions consist of a
     * target name and, optionally, text data. The data is only meaningful
     * to the application.
     * <p>
     * Typically, a processing instruction's data will contain a series
     * of pseudo-attributes. These pseudo-attributes follow the form of
     * element attributes but are <strong>not</strong> parsed or presented
     * to the application as anything other than text. The application is
     * responsible for parsing the data.
     *
     * <p>
     *  一个处理指令。处理指令由目标名称和可选的文本数据组成。数据只对应用程序有意义。
     * <p>
     *  通常,处理指令的数据将包含一系列伪属性。这些伪属性遵循元素属性的形式,但<strong>不</strong>作为除文本之外的任何东西解析或呈现给应用程序。应用程序负责解析数据。
     * 
     * 
     * @param target The target.
     * @param data   The data or null if none specified.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void processingInstruction(String target, XMLString data, Augmentations augs)
        throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.processingInstruction(target, data, augs);
        }
    } // processingInstruction(String,XMLString)


    /**
     * Binds the namespaces. This method will handle calling the
     * document handler to start the prefix mappings.
     * <p>
     * <strong>Note:</strong> This method makes use of the
     * fAttributeQName variable. Any contents of the variable will
     * be destroyed. Caller should copy the values out of this
     * temporary variable before calling this method.
     *
     * <p>
     * 绑定命名空间。此方法将处理调用文档处理程序以启动前缀映射。
     * <p>
     *  <strong>注意：</strong>此方法使用fAttributeQName变量。变量的任何内容都将被销毁。在调用此方法之前,调用者应该将值从此临时变量中复制。
     * 
     * 
     * @param element    The name of the element.
     * @param attributes The element attributes.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startElement(QName element, XMLAttributes attributes, Augmentations augs)
        throws XNIException {

        if (fNamespaces) {
            handleStartElement(element, attributes, augs, false);
        }
        else if (fDocumentHandler != null) {
            fDocumentHandler.startElement(element, attributes, augs);
        }


    } // startElement(QName,XMLAttributes)

    /**
     * An empty element.
     *
     * <p>
     *  空元素。
     * 
     * 
     * @param element    The name of the element.
     * @param attributes The element attributes.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs)
        throws XNIException {

        if (fNamespaces) {
            handleStartElement(element, attributes, augs, true);
            handleEndElement(element, augs, true);
        }
        else if (fDocumentHandler != null) {
            fDocumentHandler.emptyElement(element, attributes, augs);
        }

    } // emptyElement(QName,XMLAttributes)

    /**
     * Character content.
     *
     * <p>
     *  字符内容。
     * 
     * 
     * @param text The content.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void characters(XMLString text, Augmentations augs) throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.characters(text, augs);
        }
    } // characters(XMLString)

    /**
     * Ignorable whitespace. For this method to be called, the document
     * source must have some way of determining that the text containing
     * only whitespace characters should be considered ignorable. For
     * example, the validator can determine if a length of whitespace
     * characters in the document are ignorable based on the element
     * content model.
     *
     * <p>
     *  可怕的空格。对于要调用的此方法,文档源必须具有某种方式确定仅包含空格字符的文本应该被视为可忽略。例如,验证器可以基于元素内容模型来确定文档中的空白字符的长度是否可忽略。
     * 
     * 
     * @param text The ignorable whitespace.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.ignorableWhitespace(text, augs);
        }
    } // ignorableWhitespace(XMLString)

    /**
     * The end of an element.
     *
     * <p>
     *  元素的结尾。
     * 
     * 
     * @param element The name of the element.
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endElement(QName element, Augmentations augs) throws XNIException {

        if (fNamespaces) {
            handleEndElement(element, augs, false);
        }
        else if (fDocumentHandler != null) {
            fDocumentHandler.endElement(element, augs);
        }

    } // endElement(QName)

    /**
     * The start of a CDATA section.
     * <p>
     *  CDATA节的开始。
     * 
     * 
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startCDATA(Augmentations augs) throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.startCDATA(augs);
        }
    } // startCDATA()

    /**
     * The end of a CDATA section.
     * <p>
     *  CDATA段的结尾。
     * 
     * 
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endCDATA(Augmentations augs) throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.endCDATA(augs);
        }
    } // endCDATA()

    /**
     * The end of the document.
     * <p>
     *  文档的结尾。
     * 
     * 
     * @param augs     Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endDocument(Augmentations augs) throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.endDocument(augs);
        }
    } // endDocument()

    /**
     * This method notifies the end of a general entity.
     * <p>
     * <strong>Note:</strong> This method is not called for entity references
     * appearing as part of attribute values.
     *
     * <p>
     *  此方法通知一般实体的结束。
     * <p>
     * 
     * @param name   The name of the entity.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @exception XNIException
     *                   Thrown by handler to signal an error.
     */
    public void endGeneralEntity(String name, Augmentations augs) throws XNIException {
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            fDocumentHandler.endGeneralEntity(name, augs);
        }
    } // endEntity(String)

    //
    // Protected methods
    //

    /** Handles start element. */
    protected void handleStartElement(QName element, XMLAttributes attributes,
                                      Augmentations augs,
                                      boolean isEmpty) throws XNIException {

        // add new namespace context
        fNamespaceContext.pushContext();

        if (element.prefix == XMLSymbols.PREFIX_XMLNS) {
            fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                       "ElementXMLNSPrefix",
                                       new Object[]{element.rawname},
                                       XMLErrorReporter.SEVERITY_FATAL_ERROR);
        }

        // search for new namespace bindings
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            String localpart = attributes.getLocalName(i);
            String prefix = attributes.getPrefix(i);
            // when it's of form xmlns="..." or xmlns:prefix="...",
            // it's a namespace declaration. but prefix:xmlns="..." isn't.
            if (prefix == XMLSymbols.PREFIX_XMLNS ||
                prefix == XMLSymbols.EMPTY_STRING && localpart == XMLSymbols.PREFIX_XMLNS) {

                // get the internalized value of this attribute
                String uri = fSymbolTable.addSymbol(attributes.getValue(i));

                // 1. "xmlns" can't be bound to any namespace
                if (prefix == XMLSymbols.PREFIX_XMLNS && localpart == XMLSymbols.PREFIX_XMLNS) {
                    fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                               "CantBindXMLNS",
                                               new Object[]{attributes.getQName(i)},
                                               XMLErrorReporter.SEVERITY_FATAL_ERROR);
                }

                // 2. the namespace for "xmlns" can't be bound to any prefix
                if (uri == NamespaceContext.XMLNS_URI) {
                    fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                               "CantBindXMLNS",
                                               new Object[]{attributes.getQName(i)},
                                               XMLErrorReporter.SEVERITY_FATAL_ERROR);
                }

                // 3. "xml" can't be bound to any other namespace than it's own
                if (localpart == XMLSymbols.PREFIX_XML) {
                    if (uri != NamespaceContext.XML_URI) {
                        fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                                   "CantBindXML",
                                                   new Object[]{attributes.getQName(i)},
                                                   XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    }
                }
                // 4. the namespace for "xml" can't be bound to any other prefix
                else {
                    if (uri ==NamespaceContext.XML_URI) {
                        fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                                   "CantBindXML",
                                                   new Object[]{attributes.getQName(i)},
                                                   XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    }
                }

                prefix = localpart != XMLSymbols.PREFIX_XMLNS ? localpart : XMLSymbols.EMPTY_STRING;

                // http://www.w3.org/TR/1999/REC-xml-names-19990114/#dt-prefix
                // We should only report an error if there is a prefix,
                // that is, the local part is not "xmlns". -SG
                // Since this is an error condition in XML 1.0,
                // and should be relatively uncommon in XML 1.1,
                // making this test into a method call to reuse code
                // should be acceptable.  - NG
                if(prefixBoundToNullURI(uri, localpart)) {
                    fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                               "EmptyPrefixedAttName",
                                               new Object[]{attributes.getQName(i)},
                                               XMLErrorReporter.SEVERITY_FATAL_ERROR);
                    continue;
                }

                // declare prefix in context
                fNamespaceContext.declarePrefix(prefix, uri.length() != 0 ? uri : null);

            }
        }

        // bind the element
        String prefix = element.prefix != null
                      ? element.prefix : XMLSymbols.EMPTY_STRING;
        element.uri = fNamespaceContext.getURI(prefix);
        if (element.prefix == null && element.uri != null) {
            element.prefix = XMLSymbols.EMPTY_STRING;
        }
        if (element.prefix != null && element.uri == null) {
            fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                       "ElementPrefixUnbound",
                                       new Object[]{element.prefix, element.rawname},
                                       XMLErrorReporter.SEVERITY_FATAL_ERROR);
        }

        // bind the attributes
        for (int i = 0; i < length; i++) {
            attributes.getName(i, fAttributeQName);
            String aprefix = fAttributeQName.prefix != null
                           ? fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
            String arawname = fAttributeQName.rawname;
            if (arawname == XMLSymbols.PREFIX_XMLNS) {
                fAttributeQName.uri = fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS);
                attributes.setName(i, fAttributeQName);
            }
            else if (aprefix != XMLSymbols.EMPTY_STRING) {
                fAttributeQName.uri = fNamespaceContext.getURI(aprefix);
                if (fAttributeQName.uri == null) {
                    fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                               "AttributePrefixUnbound",
                                               new Object[]{element.rawname,arawname,aprefix},
                                               XMLErrorReporter.SEVERITY_FATAL_ERROR);
                }
                attributes.setName(i, fAttributeQName);
            }
        }

        // verify that duplicate attributes don't exist
        // Example: <foo xmlns:a='NS' xmlns:b='NS' a:attr='v1' b:attr='v2'/>
        int attrCount = attributes.getLength();
        for (int i = 0; i < attrCount - 1; i++) {
            String auri = attributes.getURI(i);
            if (auri == null || auri == NamespaceContext.XMLNS_URI) {
                continue;
            }
            String alocalpart = attributes.getLocalName(i);
            for (int j = i + 1; j < attrCount; j++) {
                String blocalpart = attributes.getLocalName(j);
                String buri = attributes.getURI(j);
                if (alocalpart == blocalpart && auri == buri) {
                    fErrorReporter.reportError(XMLMessageFormatter.XMLNS_DOMAIN,
                                               "AttributeNSNotUnique",
                                               new Object[]{element.rawname,alocalpart, auri},
                                               XMLErrorReporter.SEVERITY_FATAL_ERROR);
                }
            }
        }

        // call handler
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            if (isEmpty) {
                fDocumentHandler.emptyElement(element, attributes, augs);
            }
            else {
                fDocumentHandler.startElement(element, attributes, augs);
            }
        }


    } // handleStartElement(QName,XMLAttributes,boolean)

    /** Handles end element. */
    protected void handleEndElement(QName element, Augmentations augs, boolean isEmpty)
        throws XNIException {

        // bind element
        String eprefix = element.prefix != null ? element.prefix : XMLSymbols.EMPTY_STRING;
        element.uri = fNamespaceContext.getURI(eprefix);
        if (element.uri != null) {
            element.prefix = eprefix;
        }

        // call handlers
        if (fDocumentHandler != null && !fOnlyPassPrefixMappingEvents) {
            if (!isEmpty) {
                fDocumentHandler.endElement(element, augs);
            }
        }

        // pop context
        fNamespaceContext.popContext();

    } // handleEndElement(QName,boolean)

    // returns true iff the given prefix is bound to "" *and*
    // this is disallowed by the version of XML namespaces in use.
    protected boolean prefixBoundToNullURI(String uri, String localpart) {
        return (uri == XMLSymbols.EMPTY_STRING && localpart != XMLSymbols.PREFIX_XMLNS);
    } // prefixBoundToNullURI(String, String):  boolean

} // class XMLNamespaceBinder
