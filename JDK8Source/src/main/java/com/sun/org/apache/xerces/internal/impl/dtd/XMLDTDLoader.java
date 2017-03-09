/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999-2003 The Apache Software Foundation.
 * All rights reserved.
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
 *  版权所有(c)1999-2003 Apache软件基金会。版权所有。
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

package com.sun.org.apache.xerces.internal.impl.dtd;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.impl.XMLDTDScannerImpl;
import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;

import com.sun.org.apache.xerces.internal.util.Status;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.util.DefaultErrorHandler;

import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarLoader;
import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;


/**
 * The DTD loader. The loader knows how to build grammars from XMLInputSources.
 * It extends the DTD processor in order to do this; it's
 * a separate class because DTD processors don't need to know how
 * to talk to the outside world in their role as instance-document
 * helpers.
 * <p>
 * This component requires the following features and properties.  It
 * know ho to set them if no one else does:from the
 * <ul>
 *  <li>http://xml.org/sax/features/namespaces</li>
 *  <li>http://apache.org/xml/properties/internal/symbol-table</li>
 *  <li>http://apache.org/xml/properties/internal/error-reporter</li>
 *  <li>http://apache.org/xml/properties/internal/grammar-pool</li>
 *  <li>http://apache.org/xml/properties/internal/datatype-validator-factory</li>
 * </ul>
 *
 * @xerces.internal
 *
 * <p>
 *  DTD加载器。加载器知道如何从XMLInputSources构建语法。它扩展了DTD处理器来做到这一点;它是一个单独的类,因为DTD处理器不需要知道如何作为实例文档助手与外部世界交谈。
 * <p>
 * 此组件需要以下功能和属性。它知道如果没有其他人,设置他们：从
 * <ul>
 *  <li> http://xml.org/sax/features/namespaces </li> <li> http://apache.org/xml/properties/internal/sym
 * bol-table </li> <li> http：/ /apache.org/xml/properties/internal/error-reporter </li> <li> http://apac
 * he.org/xml/properties/internal/grammar-pool </li> <li> http：// apache .org / xml / properties / inter
 * nal / datatype-validator-factory </li>。
 * </ul>
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Neil Graham, IBM
 * @author Michael Glavassevich, IBM
 *
 * @version $Id: XMLDTDLoader.java,v 1.6 2010-11-01 04:39:42 joehw Exp $
 */
public class XMLDTDLoader
        extends XMLDTDProcessor
        implements XMLGrammarLoader {

    //
    // Constants
    //

    // feature identifiers

    /** Feature identifier: standard uri conformant feature. */
    protected static final String STANDARD_URI_CONFORMANT_FEATURE =
        Constants.XERCES_FEATURE_PREFIX + Constants.STANDARD_URI_CONFORMANT_FEATURE;

    /** Feature identifier: balance syntax trees. */
    protected static final String BALANCE_SYNTAX_TREES =
        Constants.XERCES_FEATURE_PREFIX + Constants.BALANCE_SYNTAX_TREES;

    // recognized features:
    private static final String[] LOADER_RECOGNIZED_FEATURES = {
        VALIDATION,
        WARN_ON_DUPLICATE_ATTDEF,
        WARN_ON_UNDECLARED_ELEMDEF,
        NOTIFY_CHAR_REFS,
        STANDARD_URI_CONFORMANT_FEATURE,
        BALANCE_SYNTAX_TREES
    };

    // property identifiers

    /** Property identifier: error handler. */
    protected static final String ERROR_HANDLER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_HANDLER_PROPERTY;

    /** Property identifier: entity resolver. */
    public static final String ENTITY_RESOLVER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_RESOLVER_PROPERTY;

    /** Property identifier: locale. */
    public static final String LOCALE =
        Constants.XERCES_PROPERTY_PREFIX + Constants.LOCALE_PROPERTY;

    /** Recognized properties. */
    private static final String[] LOADER_RECOGNIZED_PROPERTIES = {
        SYMBOL_TABLE,
        ERROR_REPORTER,
        ERROR_HANDLER,
        ENTITY_RESOLVER,
        GRAMMAR_POOL,
        DTD_VALIDATOR,
        LOCALE
    };

    // enforcing strict uri?
    private boolean fStrictURI = false;

    /** Controls whether the DTD grammar produces balanced syntax trees. */
    private boolean fBalanceSyntaxTrees = false;

    /** Entity resolver . */
    protected XMLEntityResolver fEntityResolver;

    // the scanner we use to actually read the DTD
    protected XMLDTDScannerImpl fDTDScanner;

    // the entity manager the scanner needs.
    protected XMLEntityManager fEntityManager;

    // what's our Locale?
    protected Locale fLocale;

    //
    // Constructors
    //

    /** Deny default construction; we need a SymtolTable! */
    public XMLDTDLoader() {
        this(new SymbolTable());
    } // <init>()

    public XMLDTDLoader(SymbolTable symbolTable) {
        this(symbolTable, null);
    } // init(SymbolTable)

    public XMLDTDLoader(SymbolTable symbolTable,
                XMLGrammarPool grammarPool) {
        this(symbolTable, grammarPool, null, new XMLEntityManager());
    } // init(SymbolTable, XMLGrammarPool)

    XMLDTDLoader(SymbolTable symbolTable,
                XMLGrammarPool grammarPool, XMLErrorReporter errorReporter,
                XMLEntityResolver entityResolver) {
        fSymbolTable = symbolTable;
        fGrammarPool = grammarPool;
        if(errorReporter == null) {
            errorReporter = new XMLErrorReporter();
            errorReporter.setProperty(ERROR_HANDLER, new DefaultErrorHandler());
        }
        fErrorReporter = errorReporter;
        // Add XML message formatter if there isn't one.
        if (fErrorReporter.getMessageFormatter(XMLMessageFormatter.XML_DOMAIN) == null) {
            XMLMessageFormatter xmft = new XMLMessageFormatter();
            fErrorReporter.putMessageFormatter(XMLMessageFormatter.XML_DOMAIN, xmft);
            fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xmft);
        }
        fEntityResolver = entityResolver;
        if(fEntityResolver instanceof XMLEntityManager) {
            fEntityManager = (XMLEntityManager)fEntityResolver;
        } else {
            fEntityManager = new XMLEntityManager();
        }
        fEntityManager.setProperty(Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_REPORTER_PROPERTY, errorReporter);
        fDTDScanner = createDTDScanner(fSymbolTable, fErrorReporter, fEntityManager);
        fDTDScanner.setDTDHandler(this);
        fDTDScanner.setDTDContentModelHandler(this);
        reset();
    } // init(SymbolTable, XMLGrammarPool, XMLErrorReporter, XMLEntityResolver)

    // XMLGrammarLoader methods

    /**
     * Returns a list of feature identifiers that are recognized by
     * this component. This method may return null if no features
     * are recognized by this component.
     * <p>
     *  返回此组件可识别的要素标识符列表。如果此组件未识别任何功能,此方法可能返回null。
     * 
     */
    public String[] getRecognizedFeatures() {
        return (String[])(LOADER_RECOGNIZED_FEATURES.clone());
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
        if (featureId.equals(VALIDATION)) {
            fValidation = state;
        }
        else if (featureId.equals(WARN_ON_DUPLICATE_ATTDEF)) {
            fWarnDuplicateAttdef = state;
        }
        else if (featureId.equals(WARN_ON_UNDECLARED_ELEMDEF)) {
            fWarnOnUndeclaredElemdef = state;
        }
        else if (featureId.equals(NOTIFY_CHAR_REFS)) {
            fDTDScanner.setFeature(featureId, state);
        }
        else if (featureId.equals(STANDARD_URI_CONFORMANT_FEATURE)) {
            fStrictURI = state;
        }
        else if (featureId.equals(BALANCE_SYNTAX_TREES)) {
            fBalanceSyntaxTrees = state;
        }
        else {
            throw new XMLConfigurationException(Status.NOT_RECOGNIZED, featureId);
        }
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
        return (String[])(LOADER_RECOGNIZED_PROPERTIES.clone());
    } // getRecognizedProperties():String[]

    /**
     * Returns the state of a property.
     *
     * <p>
     *  返回属性的状态。
     * 
     * 
     * @param propertyId The property identifier.
     *
     * @throws XMLConfigurationException Thrown on configuration error.
     */
    public Object getProperty(String propertyId)
            throws XMLConfigurationException {
        if (propertyId.equals(SYMBOL_TABLE)) {
            return fSymbolTable;
        }
        else if (propertyId.equals(ERROR_REPORTER)) {
            return fErrorReporter;
        }
        else if (propertyId.equals(ERROR_HANDLER)) {
            return fErrorReporter.getErrorHandler();
        }
        else if (propertyId.equals(ENTITY_RESOLVER)) {
            return fEntityResolver;
        }
        else if (propertyId.equals(LOCALE)) {
            return getLocale();
        }
        else if (propertyId.equals(GRAMMAR_POOL)) {
            return fGrammarPool;
        }
        else if (propertyId.equals(DTD_VALIDATOR)) {
            return fValidator;
        }
        throw new XMLConfigurationException(Status.NOT_RECOGNIZED, propertyId);
    } // getProperty(String):  Object

    /**
     * Sets the value of a property. This method is called by the component
     * manager any time after reset when a property changes value.
     * <p>
     * <strong>Note:</strong> Components should silently ignore properties
     * that do not affect the operation of the component.
     *
     * <p>
     *  设置属性的值。当属性更改值时,组件管理器在重置后任何时候调用此方法。
     * <p>
     *  <strong>注意：</strong>组件应静默忽略不影响组件操作的属性。
     * 
     * 
     * @param propertyId The property identifier.
     * @param value      The value of the property.
     *
     * @throws SAXNotRecognizedException The component should not throw
     *                                   this exception.
     * @throws SAXNotSupportedException The component should not throw
     *                                  this exception.
     */
    public void setProperty(String propertyId, Object value)
            throws XMLConfigurationException {
        if (propertyId.equals(SYMBOL_TABLE)) {
            fSymbolTable = (SymbolTable)value;
            fDTDScanner.setProperty(propertyId, value);
            fEntityManager.setProperty(propertyId, value);
        }
        else if(propertyId.equals(ERROR_REPORTER)) {
            fErrorReporter = (XMLErrorReporter)value;
            // Add XML message formatter if there isn't one.
            if (fErrorReporter.getMessageFormatter(XMLMessageFormatter.XML_DOMAIN) == null) {
                XMLMessageFormatter xmft = new XMLMessageFormatter();
                fErrorReporter.putMessageFormatter(XMLMessageFormatter.XML_DOMAIN, xmft);
                fErrorReporter.putMessageFormatter(XMLMessageFormatter.XMLNS_DOMAIN, xmft);
            }
            fDTDScanner.setProperty(propertyId, value);
            fEntityManager.setProperty(propertyId, value);
        }
        else if (propertyId.equals(ERROR_HANDLER)) {
            fErrorReporter.setProperty(propertyId, value);
        }
        else if (propertyId.equals(ENTITY_RESOLVER)) {
            fEntityResolver = (XMLEntityResolver)value;
            fEntityManager.setProperty(propertyId, value);
        }
        else if (propertyId.equals(LOCALE)) {
            setLocale((Locale) value);
        }
        else if(propertyId.equals(GRAMMAR_POOL)) {
            fGrammarPool = (XMLGrammarPool)value;
        }
        else {
            throw new XMLConfigurationException(Status.NOT_RECOGNIZED, propertyId);
        }
    } // setProperty(String,Object)

    /**
     * Returns the state of a feature.
     *
     * <p>
     *  返回要素的状态。
     * 
     * 
     * @param featureId The feature identifier.
     *
     * @throws XMLConfigurationException Thrown on configuration error.
     */
    public boolean getFeature(String featureId)
            throws XMLConfigurationException {
        if (featureId.equals(VALIDATION)) {
            return fValidation;
        }
        else if (featureId.equals(WARN_ON_DUPLICATE_ATTDEF)) {
            return fWarnDuplicateAttdef;
        }
        else if (featureId.equals(WARN_ON_UNDECLARED_ELEMDEF)) {
            return fWarnOnUndeclaredElemdef;
        }
        else if (featureId.equals(NOTIFY_CHAR_REFS)) {
            return fDTDScanner.getFeature(featureId);
        }
        else if (featureId.equals(STANDARD_URI_CONFORMANT_FEATURE)) {
            return fStrictURI;
        }
        else if (featureId.equals(BALANCE_SYNTAX_TREES)) {
            return fBalanceSyntaxTrees;
        }
        throw new XMLConfigurationException(Status.NOT_RECOGNIZED, featureId);
    } //getFeature(String):  boolean

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
    public void setLocale(Locale locale) {
        fLocale = locale;
        fErrorReporter.setLocale(locale);
    } // setLocale(Locale)

    /** Return the Locale the XMLGrammarLoader is using. */
    public Locale getLocale() {
        return fLocale;
    } // getLocale():  Locale


    /**
     * Sets the error handler.
     *
     * <p>
     *  设置错误处理程序。
     * 
     * 
     * @param errorHandler The error handler.
     */
    public void setErrorHandler(XMLErrorHandler errorHandler) {
        fErrorReporter.setProperty(ERROR_HANDLER, errorHandler);
    } // setErrorHandler(XMLErrorHandler)

    /** Returns the registered error handler.  */
    public XMLErrorHandler getErrorHandler() {
        return fErrorReporter.getErrorHandler();
    } // getErrorHandler():  XMLErrorHandler

    /**
     * Sets the entity resolver.
     *
     * <p>
     *  设置实体解析器。
     * 
     * 
     * @param entityResolver The new entity resolver.
     */
    public void setEntityResolver(XMLEntityResolver entityResolver) {
        fEntityResolver = entityResolver;
        fEntityManager.setProperty(ENTITY_RESOLVER, entityResolver);
    } // setEntityResolver(XMLEntityResolver)

    /** Returns the registered entity resolver.  */
    public XMLEntityResolver getEntityResolver() {
        return fEntityResolver;
    } // getEntityResolver():  XMLEntityResolver

    /**
     * Returns a Grammar object by parsing the contents of the
     * entity pointed to by source.
     *
     * <p>
     * 通过解析源指向的实体的内容来返回语法对象。
     * 
     * 
     * @param source        the location of the entity which forms
     *                          the starting point of the grammar to be constructed.
     * @throws IOException      When a problem is encountered reading the entity
     *          XNIException    When a condition arises (such as a FatalError) that requires parsing
     *                              of the entity be terminated.
     */
    public Grammar loadGrammar(XMLInputSource source)
            throws IOException, XNIException {
        reset();
        // First chance checking strict URI
        String eid = XMLEntityManager.expandSystemId(source.getSystemId(), source.getBaseSystemId(), fStrictURI);
        XMLDTDDescription desc = new XMLDTDDescription(source.getPublicId(), source.getSystemId(), source.getBaseSystemId(), eid, null);
        if (!fBalanceSyntaxTrees) {
            fDTDGrammar = new DTDGrammar(fSymbolTable, desc);
        }
        else {
            fDTDGrammar = new BalancedDTDGrammar(fSymbolTable, desc);
        }
        fGrammarBucket = new DTDGrammarBucket();
        fGrammarBucket.setStandalone(false);
        fGrammarBucket.setActiveGrammar(fDTDGrammar);
        // no reason to use grammar bucket's "put" method--we
        // know which grammar it is, and we don't know the root name anyway...

        // actually start the parsing!
        try {
            fDTDScanner.setInputSource(source);
            fDTDScanner.scanDTDExternalSubset(true);
        } catch (EOFException e) {
            // expected behaviour...
        }
        finally {
            // Close all streams opened by the parser.
            fEntityManager.closeReaders();
        }
        if(fDTDGrammar != null && fGrammarPool != null) {
            fGrammarPool.cacheGrammars(XMLDTDDescription.XML_DTD, new Grammar[] {fDTDGrammar});
        }
        return fDTDGrammar;
    } // loadGrammar(XMLInputSource):  Grammar

    /**
     * Parse a DTD internal and/or external subset and insert the content
     * into the existing DTD grammar owned by the given DTDValidator.
     * <p>
     *  解析DTD内部和/或外部子集,并将内容插入给定DTDValidator拥有的现有DTD语法。
     */
    public void loadGrammarWithContext(XMLDTDValidator validator, String rootName,
            String publicId, String systemId, String baseSystemId, String internalSubset)
        throws IOException, XNIException {
        final DTDGrammarBucket grammarBucket = validator.getGrammarBucket();
        final DTDGrammar activeGrammar = grammarBucket.getActiveGrammar();
        if (activeGrammar != null && !activeGrammar.isImmutable()) {
            fGrammarBucket = grammarBucket;
            fEntityManager.setScannerVersion(getScannerVersion());
            reset();
            try {
                // process internal subset
                if (internalSubset != null) {
                    // To get the DTD scanner to end at the right place we have to fool
                    // it into thinking that it reached the end of the internal subset
                    // in a real document.
                    StringBuffer buffer = new StringBuffer(internalSubset.length() + 2);
                    buffer.append(internalSubset).append("]>");
                    XMLInputSource is = new XMLInputSource(null, baseSystemId,
                            null, new StringReader(buffer.toString()), null);
                    fEntityManager.startDocumentEntity(is);
                    fDTDScanner.scanDTDInternalSubset(true, false, systemId != null);
                }
                // process external subset
                if (systemId != null) {
                    XMLDTDDescription desc = new XMLDTDDescription(publicId, systemId, baseSystemId, null, rootName);
                    XMLInputSource source = fEntityManager.resolveEntity(desc);
                    fDTDScanner.setInputSource(source);
                    fDTDScanner.scanDTDExternalSubset(true);
                }
            }
            catch (EOFException e) {
                // expected behaviour...
            }
            finally {
                // Close all streams opened by the parser.
                fEntityManager.closeReaders();
            }
        }
    } // loadGrammarWithContext(XMLDTDValidator, String, String, String, String, String)

    // reset all the components that we rely upon
    protected void reset() {
        super.reset();
        fDTDScanner.reset();
        fEntityManager.reset();
        fErrorReporter.setDocumentLocator(fEntityManager.getEntityScanner());
    }

    protected XMLDTDScannerImpl createDTDScanner(SymbolTable symbolTable,
            XMLErrorReporter errorReporter, XMLEntityManager entityManager) {
        return new XMLDTDScannerImpl(symbolTable, errorReporter, entityManager);
    } // createDTDScanner(SymbolTable, XMLErrorReporter, XMLEntityManager) : XMLDTDScannerImpl

    protected short getScannerVersion() {
        return Constants.XML_VERSION_1_0;
    } // getScannerVersion() : short

} // class XMLDTDLoader
