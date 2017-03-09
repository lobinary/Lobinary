/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999-2002 The Apache Software Foundation.
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
 *  版权所有(c)1999-2002 Apache软件基金会。版权所有。
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
import com.sun.org.apache.xerces.internal.impl.msg.XMLMessageFormatter;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.util.XMLChar;
import com.sun.org.apache.xerces.internal.util.XMLSymbols;
import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.XMLDTDContentModelHandler;
import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;
import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.grammars.Grammar;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarDescription;
import com.sun.org.apache.xerces.internal.xni.grammars.XMLGrammarPool;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDContentModelFilter;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDContentModelSource;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDFilter;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDTDSource;

/**
 * The DTD processor. The processor implements a DTD
 * filter: receiving DTD events from the DTD scanner; validating
 * the content and structure; building a grammar, if applicable;
 * and notifying the DTDHandler of the information resulting from the
 * process.
 * <p>
 * This component requires the following features and properties from the
 * component manager that uses it:
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
 *  DTD处理器。处理器实现DTD过滤器：从DTD扫描器接收DTD事件;验证内容和结构;如果适用,建立语法;以及向所述DTDHandler通知从所述过程产生的信息。
 * <p>
 *  此组件需要使用它的组件管理器中的以下功能和属性：
 * <ul>
 * <li> http://xml.org/sax/features/namespaces </li> <li> http://apache.org/xml/properties/internal/symb
 * ol-table </li> <li> http：/ /apache.org/xml/properties/internal/error-reporter </li> <li> http://apach
 * e.org/xml/properties/internal/grammar-pool </li> <li> http：// apache .org / xml / properties / intern
 * al / datatype-validator-factory </li>。
 * </ul>
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Neil Graham, IBM
 *
 * @version $Id: XMLDTDProcessor.java,v 1.5 2010-11-01 04:39:42 joehw Exp $
 */
public class XMLDTDProcessor
        implements XMLComponent, XMLDTDFilter, XMLDTDContentModelFilter {

    //
    // Constants
    //

    /** Top level scope (-1). */
    private static final int TOP_LEVEL_SCOPE = -1;

    // feature identifiers

    /** Feature identifier: validation. */
    protected static final String VALIDATION =
        Constants.SAX_FEATURE_PREFIX + Constants.VALIDATION_FEATURE;

    /** Feature identifier: notify character references. */
    protected static final String NOTIFY_CHAR_REFS =
        Constants.XERCES_FEATURE_PREFIX + Constants.NOTIFY_CHAR_REFS_FEATURE;

    /** Feature identifier: warn on duplicate attdef */
    protected static final String WARN_ON_DUPLICATE_ATTDEF =
        Constants.XERCES_FEATURE_PREFIX +Constants.WARN_ON_DUPLICATE_ATTDEF_FEATURE;

    /** Feature identifier: warn on undeclared element referenced in content model. */
    protected static final String WARN_ON_UNDECLARED_ELEMDEF =
        Constants.XERCES_FEATURE_PREFIX + Constants.WARN_ON_UNDECLARED_ELEMDEF_FEATURE;

        protected static final String PARSER_SETTINGS =
        Constants.XERCES_FEATURE_PREFIX + Constants.PARSER_SETTINGS;

    // property identifiers

    /** Property identifier: symbol table. */
    protected static final String SYMBOL_TABLE =
        Constants.XERCES_PROPERTY_PREFIX + Constants.SYMBOL_TABLE_PROPERTY;

    /** Property identifier: error reporter. */
    protected static final String ERROR_REPORTER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_REPORTER_PROPERTY;

    /** Property identifier: grammar pool. */
    protected static final String GRAMMAR_POOL =
        Constants.XERCES_PROPERTY_PREFIX + Constants.XMLGRAMMAR_POOL_PROPERTY;

    /** Property identifier: validator . */
    protected static final String DTD_VALIDATOR =
        Constants.XERCES_PROPERTY_PREFIX + Constants.DTD_VALIDATOR_PROPERTY;

    // recognized features and properties

    /** Recognized features. */
    private static final String[] RECOGNIZED_FEATURES = {
        VALIDATION,
        WARN_ON_DUPLICATE_ATTDEF,
        WARN_ON_UNDECLARED_ELEMDEF,
        NOTIFY_CHAR_REFS,
    };

    /** Feature defaults. */
    private static final Boolean[] FEATURE_DEFAULTS = {
        null,
        Boolean.FALSE,
        Boolean.FALSE,
        null,
    };

    /** Recognized properties. */
    private static final String[] RECOGNIZED_PROPERTIES = {
        SYMBOL_TABLE,
        ERROR_REPORTER,
        GRAMMAR_POOL,
        DTD_VALIDATOR,
    };

    /** Property defaults. */
    private static final Object[] PROPERTY_DEFAULTS = {
        null,
        null,
        null,
        null,
    };

    // debugging

    //
    // Data
    //

    // features

    /** Validation. */
    protected boolean fValidation;

    /** Validation against only DTD */
    protected boolean fDTDValidation;

    /** warn on duplicate attribute definition, this feature works only when validation is true */
    protected boolean fWarnDuplicateAttdef;

    /** warn on undeclared element referenced in content model, this feature only works when valiation is true */
    protected boolean fWarnOnUndeclaredElemdef;

    // properties

    /** Symbol table. */
    protected SymbolTable fSymbolTable;

    /** Error reporter. */
    protected XMLErrorReporter fErrorReporter;

    /** Grammar bucket. */
    protected DTDGrammarBucket fGrammarBucket;

    // the validator to which we look for our grammar bucket (the
    // validator needs to hold the bucket so that it can initialize
    // the grammar with details like whether it's for a standalone document...
    protected XMLDTDValidator fValidator;

    // the grammar pool we'll try to add the grammar to:
    protected XMLGrammarPool fGrammarPool;

    // what's our Locale?
    protected Locale fLocale;

    // handlers

    /** DTD handler. */
    protected XMLDTDHandler fDTDHandler;

    /** DTD source. */
    protected XMLDTDSource fDTDSource;

    /** DTD content model handler. */
    protected XMLDTDContentModelHandler fDTDContentModelHandler;

    /** DTD content model source. */
    protected XMLDTDContentModelSource fDTDContentModelSource;

    // grammars

    /** DTD Grammar. */
    protected DTDGrammar fDTDGrammar;

    // state

    /** Perform validation. */
    private boolean fPerformValidation;

    /** True if in an ignore conditional section of the DTD. */
    protected boolean fInDTDIgnore;

    // information regarding the current element

    // validation states

    /** Mixed. */
    private boolean fMixed;

    // temporary variables

    /** Temporary entity declaration. */
    private final XMLEntityDecl fEntityDecl = new XMLEntityDecl();

    /** Notation declaration hash. */
    private final HashMap fNDataDeclNotations = new HashMap();

    /** DTD element declaration name. */
    private String fDTDElementDeclName = null;

    /** Mixed element type "hash". */
    private final ArrayList fMixedElementTypes = new ArrayList();

    /** Element declarations in DTD. */
    private final ArrayList fDTDElementDecls = new ArrayList();

    // to check for duplicate ID or ANNOTATION attribute declare in
    // ATTLIST, and misc VCs

    /** ID attribute names. */
    private HashMap fTableOfIDAttributeNames;

    /** NOTATION attribute names. */
    private HashMap fTableOfNOTATIONAttributeNames;

    /** NOTATION enumeration values. */
    private HashMap fNotationEnumVals;

    //
    // Constructors
    //

    /** Default constructor. */
    public XMLDTDProcessor() {

        // initialize data

    } // <init>()

    //
    // XMLComponent methods
    //

    /*
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
     * @throws SAXException Thrown by component on finitialization error.
     *                      For example, if a feature or property is
     *                      required for the operation of the component, the
     *                      component manager may throw a
     *                      SAXNotRecognizedException or a
     *                      SAXNotSupportedException.
     */
    public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {

        boolean parser_settings = componentManager.getFeature(PARSER_SETTINGS, true);

        if (!parser_settings) {
            // parser settings have not been changed
            reset();
            return;
        }

        // sax features
        fValidation = componentManager.getFeature(VALIDATION, false);

        fDTDValidation =
                !(componentManager
                    .getFeature(
                        Constants.XERCES_FEATURE_PREFIX + Constants.SCHEMA_VALIDATION_FEATURE, false));

        // Xerces features

        fWarnDuplicateAttdef = componentManager.getFeature(WARN_ON_DUPLICATE_ATTDEF, false);
        fWarnOnUndeclaredElemdef = componentManager.getFeature(WARN_ON_UNDECLARED_ELEMDEF, false);

        // get needed components
        fErrorReporter =
            (XMLErrorReporter) componentManager.getProperty(
                Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_REPORTER_PROPERTY);
        fSymbolTable =
            (SymbolTable) componentManager.getProperty(
                Constants.XERCES_PROPERTY_PREFIX + Constants.SYMBOL_TABLE_PROPERTY);

        fGrammarPool = (XMLGrammarPool) componentManager.getProperty(GRAMMAR_POOL, null);

        try {
            fValidator = (XMLDTDValidator) componentManager.getProperty(DTD_VALIDATOR, null);
        } catch (ClassCastException e) {
            fValidator = null;
        }
        // we get our grammarBucket from the validator...
        if (fValidator != null) {
            fGrammarBucket = fValidator.getGrammarBucket();
        } else {
            fGrammarBucket = null;
        }
        reset();

    } // reset(XMLComponentManager)

    protected void reset() {
        // clear grammars
        fDTDGrammar = null;
        // initialize state
        fInDTDIgnore = false;

        fNDataDeclNotations.clear();

        // datatype validators
        if (fValidation) {

            if (fNotationEnumVals == null) {
                fNotationEnumVals = new HashMap();
            }
            fNotationEnumVals.clear();

            fTableOfIDAttributeNames = new HashMap();
            fTableOfNOTATIONAttributeNames = new HashMap();
        }

    }
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
    // XMLDTDSource methods
    //

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

    /**
     * Returns the DTD handler.
     *
     * <p>
     *  返回DTD处理程序。
     * 
     * 
     * @return The DTD handler.
     */
    public XMLDTDHandler getDTDHandler() {
        return fDTDHandler;
    } // getDTDHandler():  XMLDTDHandler

    //
    // XMLDTDContentModelSource methods
    //

    /**
     * Sets the DTD content model handler.
     *
     * <p>
     *  设置DTD内容模型处理程序。
     * 
     * 
     * @param dtdContentModelHandler The DTD content model handler.
     */
    public void setDTDContentModelHandler(XMLDTDContentModelHandler dtdContentModelHandler) {
        fDTDContentModelHandler = dtdContentModelHandler;
    } // setDTDContentModelHandler(XMLDTDContentModelHandler)

    /**
     * Gets the DTD content model handler.
     *
     * <p>
     *  获取DTD内容模型处理程序。
     * 
     * 
     * @return dtdContentModelHandler The DTD content model handler.
     */
    public XMLDTDContentModelHandler getDTDContentModelHandler() {
        return fDTDContentModelHandler;
    } // getDTDContentModelHandler():  XMLDTDContentModelHandler

    //
    // XMLDTDContentModelHandler and XMLDTDHandler methods
    //

    /**
     * The start of the DTD external subset.
     *
     * <p>
     *  DTD外部子集的开始。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startExternalSubset(XMLResourceIdentifier identifier,
                                    Augmentations augs) throws XNIException {
        if(fDTDGrammar != null)
            fDTDGrammar.startExternalSubset(identifier, augs);
        if(fDTDHandler != null){
            fDTDHandler.startExternalSubset(identifier, augs);
        }
    }

    /**
     * The end of the DTD external subset.
     *
     * <p>
     *  DTD外部子集的结束。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endExternalSubset(Augmentations augs) throws XNIException {
        if(fDTDGrammar != null)
            fDTDGrammar.endExternalSubset(augs);
        if(fDTDHandler != null){
            fDTDHandler.endExternalSubset(augs);
        }
    }

    /**
     * Check standalone entity reference.
     * Made static to make common between the validator and loader.
     *
     * <p>
     *  检查独立实体引用。使得静态,使验证器和加载器之间的共同。
     * 
     * 
     * @param name
     *@param grammar    grammar to which entity belongs
     * @param tempEntityDecl    empty entity declaration to put results in
     * @param errorReporter     error reporter to send errors to
     *
     * @throws XNIException Thrown by application to signal an error.
     */
    protected static void checkStandaloneEntityRef(String name, DTDGrammar grammar,
                    XMLEntityDecl tempEntityDecl, XMLErrorReporter errorReporter) throws XNIException {
        // check VC: Standalone Document Declartion, entities references appear in the document.
        int entIndex = grammar.getEntityDeclIndex(name);
        if (entIndex > -1) {
            grammar.getEntityDecl(entIndex, tempEntityDecl);
            if (tempEntityDecl.inExternal) {
                errorReporter.reportError( XMLMessageFormatter.XML_DOMAIN,
                                            "MSG_REFERENCE_TO_EXTERNALLY_DECLARED_ENTITY_WHEN_STANDALONE",
                                            new Object[]{name}, XMLErrorReporter.SEVERITY_ERROR);
            }
        }
    }

    /**
     * A comment.
     *
     * <p>
     *  评论。
     * 
     * 
     * @param text The text in the comment.
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by application to signal an error.
     */
    public void comment(XMLString text, Augmentations augs) throws XNIException {

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.comment(text, augs);
        if (fDTDHandler != null) {
            fDTDHandler.comment(text, augs);
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
     * @param augs   Additional information that may include infoset augmentations
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void processingInstruction(String target, XMLString data, Augmentations augs)
    throws XNIException {

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.processingInstruction(target, data, augs);
        if (fDTDHandler != null) {
            fDTDHandler.processingInstruction(target, data, augs);
        }
    } // processingInstruction(String,XMLString)

    //
    // XMLDTDHandler methods
    //

    /**
     * The start of the DTD.
     *
     * <p>
     *  DTD的开始。
     * 
     * 
     * @param locator  The document locator, or null if the document
     *                 location cannot be reported during the parsing of
     *                 the document DTD. However, it is <em>strongly</em>
     *                 recommended that a locator be supplied that can
     *                 at least report the base system identifier of the
     *                 DTD.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startDTD(XMLLocator locator, Augmentations augs) throws XNIException {


        // initialize state
        fNDataDeclNotations.clear();
        fDTDElementDecls.clear();

        // the grammar bucket's DTDGrammar will now be the
        // one we want, whether we're constructing it or not.
        // if we're not constructing it, then we should not have a reference
        // to it!
       if( !fGrammarBucket.getActiveGrammar().isImmutable()) {
            fDTDGrammar = fGrammarBucket.getActiveGrammar();
        }

        // call handlers
        if(fDTDGrammar != null )
            fDTDGrammar.startDTD(locator, augs);
        if (fDTDHandler != null) {
            fDTDHandler.startDTD(locator, augs);
        }

    } // startDTD(XMLLocator)

    /**
     * Characters within an IGNORE conditional section.
     *
     * <p>
     *  IGNORE条件部分中的字符。
     * 
     * 
     * @param text The ignored text.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void ignoredCharacters(XMLString text, Augmentations augs) throws XNIException {

        // ignored characters in DTD
        if(fDTDGrammar != null )
            fDTDGrammar.ignoredCharacters(text, augs);
        if (fDTDHandler != null) {
            fDTDHandler.ignoredCharacters(text, augs);
        }
    }

    /**
     * Notifies of the presence of a TextDecl line in an entity. If present,
     * this method will be called immediately following the startParameterEntity call.
     * <p>
     * <strong>Note:</strong> This method is only called for external
     * parameter entities referenced in the DTD.
     *
     * <p>
     *  通知实体中存在TextDecl行。如果存在,此方法将在startParameterEntity调用后立即调用。
     * <p>
     *  <strong>注意</strong>：仅在DTD中引用的外部参数实体才调用此方法。
     * 
     * 
     * @param version  The XML version, or null if not specified.
     * @param encoding The IANA encoding name of the entity.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void textDecl(String version, String encoding, Augmentations augs) throws XNIException {

        // call handlers
        if(fDTDGrammar != null )
            fDTDGrammar.textDecl(version, encoding, augs);
        if (fDTDHandler != null) {
            fDTDHandler.textDecl(version, encoding, augs);
        }
    }

    /**
     * This method notifies of the start of a parameter entity. The parameter
     * entity name start with a '%' character.
     *
     * <p>
     *  该方法通知参数实体的开始。参数实体名称以"％"字符开头。
     * 
     * 
     * @param name     The name of the parameter entity.
     * @param identifier The resource identifier.
     * @param encoding The auto-detected IANA encoding name of the entity
     *                 stream. This value will be null in those situations
     *                 where the entity encoding is not auto-detected (e.g.
     *                 internal parameter entities).
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startParameterEntity(String name,
                                     XMLResourceIdentifier identifier,
                                     String encoding,
                                     Augmentations augs) throws XNIException {

        if (fPerformValidation && fDTDGrammar != null &&
                fGrammarBucket.getStandalone()) {
            checkStandaloneEntityRef(name, fDTDGrammar, fEntityDecl, fErrorReporter);
        }
        // call handlers
        if(fDTDGrammar != null )
            fDTDGrammar.startParameterEntity(name, identifier, encoding, augs);
        if (fDTDHandler != null) {
            fDTDHandler.startParameterEntity(name, identifier, encoding, augs);
        }
    }

    /**
     * This method notifies the end of a parameter entity. Parameter entity
     * names begin with a '%' character.
     *
     * <p>
     *  此方法通知参数实体的结束。参数实体名称以"％"字符开头。
     * 
     * 
     * @param name The name of the parameter entity.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endParameterEntity(String name, Augmentations augs) throws XNIException {

        // call handlers
        if(fDTDGrammar != null )
            fDTDGrammar.endParameterEntity(name, augs);
        if (fDTDHandler != null) {
            fDTDHandler.endParameterEntity(name, augs);
        }
    }

    /**
     * An element declaration.
     *
     * <p>
     *  元素声明。
     * 
     * 
     * @param name         The name of the element.
     * @param contentModel The element content model.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void elementDecl(String name, String contentModel, Augmentations augs)
    throws XNIException {

        //check VC: Unique Element Declaration
        if (fValidation) {
            if (fDTDElementDecls.contains(name)) {
                fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                           "MSG_ELEMENT_ALREADY_DECLARED",
                                           new Object[]{ name},
                                           XMLErrorReporter.SEVERITY_ERROR);
            }
            else {
                fDTDElementDecls.add(name);
            }
        }

        // call handlers
        if(fDTDGrammar != null )
            fDTDGrammar.elementDecl(name, contentModel, augs);
        if (fDTDHandler != null) {
            fDTDHandler.elementDecl(name, contentModel, augs);
        }

    } // elementDecl(String,String)

    /**
     * The start of an attribute list.
     *
     * <p>
     *  属性列表的开始。
     * 
     * 
     * @param elementName The name of the element that this attribute
     *                    list is associated with.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startAttlist(String elementName, Augmentations augs)
        throws XNIException {

        // call handlers
        if(fDTDGrammar != null )
            fDTDGrammar.startAttlist(elementName, augs);
        if (fDTDHandler != null) {
            fDTDHandler.startAttlist(elementName, augs);
        }

    } // startAttlist(String)

    /**
     * An attribute declaration.
     *
     * <p>
     * 属性声明。
     * 
     * 
     * @param elementName   The name of the element that this attribute
     *                      is associated with.
     * @param attributeName The name of the attribute.
     * @param type          The attribute type. This value will be one of
     *                      the following: "CDATA", "ENTITY", "ENTITIES",
     *                      "ENUMERATION", "ID", "IDREF", "IDREFS",
     *                      "NMTOKEN", "NMTOKENS", or "NOTATION".
     * @param enumeration   If the type has the value "ENUMERATION" or
     *                      "NOTATION", this array holds the allowed attribute
     *                      values; otherwise, this array is null.
     * @param defaultType   The attribute default type. This value will be
     *                      one of the following: "#FIXED", "#IMPLIED",
     *                      "#REQUIRED", or null.
     * @param defaultValue  The attribute default value, or null if no
     *                      default value is specified.
     * @param nonNormalizedDefaultValue  The attribute default value with no normalization
     *                      performed, or null if no default value is specified.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void attributeDecl(String elementName, String attributeName,
                              String type, String[] enumeration,
                              String defaultType, XMLString defaultValue,
                              XMLString nonNormalizedDefaultValue, Augmentations augs) throws XNIException {

        if (type != XMLSymbols.fCDATASymbol && defaultValue != null) {
            normalizeDefaultAttrValue(defaultValue);
        }

        if (fValidation) {

                boolean duplicateAttributeDef = false ;

                //Get Grammar index to grammar array
                DTDGrammar grammar = (fDTDGrammar != null? fDTDGrammar:fGrammarBucket.getActiveGrammar());
                int elementIndex       = grammar.getElementDeclIndex( elementName);
                if (grammar.getAttributeDeclIndex(elementIndex, attributeName) != -1) {
                    //more than one attribute definition is provided for the same attribute of a given element type.
                    duplicateAttributeDef = true ;

                    //this feature works only when validation is true.
                    if(fWarnDuplicateAttdef){
                        fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                                 "MSG_DUPLICATE_ATTRIBUTE_DEFINITION",
                                                 new Object[]{ elementName, attributeName },
                                                 XMLErrorReporter.SEVERITY_WARNING );
                    }
                }


            //
            // a) VC: One ID per Element Type, If duplicate ID attribute
            // b) VC: ID attribute Default. if there is a declareared attribute
            //        default for ID it should be of type #IMPLIED or #REQUIRED
            if (type == XMLSymbols.fIDSymbol) {
                if (defaultValue != null && defaultValue.length != 0) {
                    if (defaultType == null ||
                        !(defaultType == XMLSymbols.fIMPLIEDSymbol ||
                          defaultType == XMLSymbols.fREQUIREDSymbol)) {
                        fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                                   "IDDefaultTypeInvalid",
                                                   new Object[]{ attributeName},
                                                   XMLErrorReporter.SEVERITY_ERROR);
                    }
                }

                if (!fTableOfIDAttributeNames.containsKey(elementName)) {
                    fTableOfIDAttributeNames.put(elementName, attributeName);
                }
                else {
                        //we should not report an error, when there is duplicate attribute definition for given element type
                        //according to XML 1.0 spec, When more than one definition is provided for the same attribute of a given
                        //element type, the first declaration is binding and later declaration are *ignored*. So processor should
                        //ignore the second declarations, however an application would be warned of the duplicate attribute defintion
                        // if http://apache.org/xml/features/validation/warn-on-duplicate-attdef feature is set to true,
                        // one typical case where this could be a  problem, when any XML file
                        // provide the ID type information through internal subset so that it is available to the parser which read
                        //only internal subset. Now that attribute declaration(ID Type) can again be part of external parsed entity
                        //referenced. At that time if parser doesn't make this distinction it will throw an error for VC One ID per
                        //Element Type, which (second defintion) actually should be ignored. Application behavior may differ on the
                        //basis of error or warning thrown. - nb.

                        if(!duplicateAttributeDef){
                                String previousIDAttributeName = (String)fTableOfIDAttributeNames.get( elementName );//rule a)
                                fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                               "MSG_MORE_THAN_ONE_ID_ATTRIBUTE",
                                               new Object[]{ elementName, previousIDAttributeName, attributeName},
                                               XMLErrorReporter.SEVERITY_ERROR);
                        }
                }
            }

            //
            //  VC: One Notation Per Element Type, should check if there is a
            //      duplicate NOTATION attribute

            if (type == XMLSymbols.fNOTATIONSymbol) {
                // VC: Notation Attributes: all notation names in the
                //     (attribute) declaration must be declared.
                for (int i=0; i<enumeration.length; i++) {
                    fNotationEnumVals.put(enumeration[i], attributeName);
                }

                if (fTableOfNOTATIONAttributeNames.containsKey( elementName ) == false) {
                    fTableOfNOTATIONAttributeNames.put( elementName, attributeName);
                }
                else {
                        //we should not report an error, when there is duplicate attribute definition for given element type
                        //according to XML 1.0 spec, When more than one definition is provided for the same attribute of a given
                        //element type, the first declaration is binding and later declaration are *ignored*. So processor should
                        //ignore the second declarations, however an application would be warned of the duplicate attribute definition
                        // if http://apache.org/xml/features/validation/warn-on-duplicate-attdef feature is set to true, Application behavior may differ on the basis of error or
                        //warning thrown. - nb.

                        if(!duplicateAttributeDef){

                                String previousNOTATIONAttributeName = (String) fTableOfNOTATIONAttributeNames.get( elementName );
                                fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                               "MSG_MORE_THAN_ONE_NOTATION_ATTRIBUTE",
                                               new Object[]{ elementName, previousNOTATIONAttributeName, attributeName},
                                               XMLErrorReporter.SEVERITY_ERROR);
                         }
                }
            }

            // VC: No Duplicate Tokens
            // XML 1.0 SE Errata - E2
            if (type == XMLSymbols.fENUMERATIONSymbol || type == XMLSymbols.fNOTATIONSymbol) {
                outer:
                    for (int i = 0; i < enumeration.length; ++i) {
                        for (int j = i + 1; j < enumeration.length; ++j) {
                            if (enumeration[i].equals(enumeration[j])) {
                                // Only report the first uniqueness violation. There could be others,
                                // but additional overhead would be incurred tracking unique tokens
                                // that have already been encountered. -- mrglavas
                                fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                               type == XMLSymbols.fENUMERATIONSymbol
                                                   ? "MSG_DISTINCT_TOKENS_IN_ENUMERATION"
                                                   : "MSG_DISTINCT_NOTATION_IN_ENUMERATION",
                                               new Object[]{ elementName, enumeration[i], attributeName },
                                               XMLErrorReporter.SEVERITY_ERROR);
                                break outer;
                            }
                        }
                    }
            }

            // VC: Attribute Default Legal
            boolean ok = true;
            if (defaultValue != null &&
                (defaultType == null ||
                 (defaultType != null && defaultType == XMLSymbols.fFIXEDSymbol))) {

                String value = defaultValue.toString();
                if (type == XMLSymbols.fNMTOKENSSymbol ||
                    type == XMLSymbols.fENTITIESSymbol ||
                    type == XMLSymbols.fIDREFSSymbol) {

                    StringTokenizer tokenizer = new StringTokenizer(value," ");
                    if (tokenizer.hasMoreTokens()) {
                        while (true) {
                            String nmtoken = tokenizer.nextToken();
                            if (type == XMLSymbols.fNMTOKENSSymbol) {
                                if (!isValidNmtoken(nmtoken)) {
                                    ok = false;
                                    break;
                                }
                            }
                            else if (type == XMLSymbols.fENTITIESSymbol ||
                                     type == XMLSymbols.fIDREFSSymbol) {
                                if (!isValidName(nmtoken)) {
                                    ok = false;
                                    break;
                                }
                            }
                            if (!tokenizer.hasMoreTokens()) {
                                break;
                            }
                        }
                    }

                }
                else {
                    if (type == XMLSymbols.fENTITYSymbol ||
                        type == XMLSymbols.fIDSymbol ||
                        type == XMLSymbols.fIDREFSymbol ||
                        type == XMLSymbols.fNOTATIONSymbol) {

                        if (!isValidName(value)) {
                            ok = false;
                        }

                    }
                    else if (type == XMLSymbols.fNMTOKENSymbol ||
                             type == XMLSymbols.fENUMERATIONSymbol) {

                        if (!isValidNmtoken(value)) {
                            ok = false;
                        }
                    }

                    if (type == XMLSymbols.fNOTATIONSymbol ||
                        type == XMLSymbols.fENUMERATIONSymbol) {
                        ok = false;
                        for (int i=0; i<enumeration.length; i++) {
                            if (defaultValue.equals(enumeration[i])) {
                                ok = true;
                            }
                        }
                    }

                }
                if (!ok) {
                    fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                               "MSG_ATT_DEFAULT_INVALID",
                                               new Object[]{attributeName, value},
                                               XMLErrorReporter.SEVERITY_ERROR);
                }
            }
        }

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.attributeDecl(elementName, attributeName,
                                  type, enumeration,
                                  defaultType, defaultValue, nonNormalizedDefaultValue, augs);
        if (fDTDHandler != null) {
            fDTDHandler.attributeDecl(elementName, attributeName,
                                      type, enumeration,
                                      defaultType, defaultValue, nonNormalizedDefaultValue, augs);
        }

    } // attributeDecl(String,String,String,String[],String,XMLString, XMLString, Augmentations)

    /**
     * The end of an attribute list.
     *
     * <p>
     *  属性列表的结尾。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endAttlist(Augmentations augs) throws XNIException {

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.endAttlist(augs);
        if (fDTDHandler != null) {
            fDTDHandler.endAttlist(augs);
        }

    } // endAttlist()

    /**
     * An internal entity declaration.
     *
     * <p>
     *  内部实体声明。
     * 
     * 
     * @param name The name of the entity. Parameter entity names start with
     *             '%', whereas the name of a general entity is just the
     *             entity name.
     * @param text The value of the entity.
     * @param nonNormalizedText The non-normalized value of the entity. This
     *             value contains the same sequence of characters that was in
     *             the internal entity declaration, without any entity
     *             references expanded.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void internalEntityDecl(String name, XMLString text,
                                   XMLString nonNormalizedText,
                                   Augmentations augs) throws XNIException {

        DTDGrammar grammar = (fDTDGrammar != null? fDTDGrammar: fGrammarBucket.getActiveGrammar());
        int index = grammar.getEntityDeclIndex(name) ;

        //If the same entity is declared more than once, the first declaration
        //encountered is binding, SAX requires only effective(first) declaration
        //to be reported to the application

        //REVISIT: Does it make sense to pass duplicate Entity information across
        //the pipeline -- nb?

        //its a new entity and hasn't been declared.
        if(index == -1){
            //store internal entity declaration in grammar
            if(fDTDGrammar != null)
                fDTDGrammar.internalEntityDecl(name, text, nonNormalizedText, augs);
            // call handlers
            if (fDTDHandler != null) {
                fDTDHandler.internalEntityDecl(name, text, nonNormalizedText, augs);
            }
        }

    } // internalEntityDecl(String,XMLString,XMLString)


    /**
     * An external entity declaration.
     *
     * <p>
     *  外部实体声明。
     * 
     * 
     * @param name     The name of the entity. Parameter entity names start
     *                 with '%', whereas the name of a general entity is just
     *                 the entity name.
     * @param identifier    An object containing all location information
     *                      pertinent to this external entity.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void externalEntityDecl(String name, XMLResourceIdentifier identifier,
                                   Augmentations augs) throws XNIException {

        DTDGrammar grammar = (fDTDGrammar != null? fDTDGrammar:  fGrammarBucket.getActiveGrammar());
        int index = grammar.getEntityDeclIndex(name) ;

        //If the same entity is declared more than once, the first declaration
        //encountered is binding, SAX requires only effective(first) declaration
        //to be reported to the application

        //REVISIT: Does it make sense to pass duplicate entity information across
        //the pipeline -- nb?

        //its a new entity and hasn't been declared.
        if(index == -1){
            //store external entity declaration in grammar
            if(fDTDGrammar != null)
                fDTDGrammar.externalEntityDecl(name, identifier, augs);
            // call handlers
            if (fDTDHandler != null) {
                fDTDHandler.externalEntityDecl(name, identifier, augs);
            }
        }

    } // externalEntityDecl(String,XMLResourceIdentifier, Augmentations)

    /**
     * An unparsed entity declaration.
     *
     * <p>
     *  未解析的实体声明。
     * 
     * 
     * @param name     The name of the entity.
     * @param identifier    An object containing all location information
     *                      pertinent to this entity.
     * @param notation The name of the notation.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void unparsedEntityDecl(String name, XMLResourceIdentifier identifier,
                                   String notation,
                                   Augmentations augs) throws XNIException {

        // VC: Notation declared,  in the production of NDataDecl
        if (fValidation) {
            fNDataDeclNotations.put(name, notation);
        }

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.unparsedEntityDecl(name, identifier, notation, augs);
        if (fDTDHandler != null) {
            fDTDHandler.unparsedEntityDecl(name, identifier, notation, augs);
        }

    } // unparsedEntityDecl(String,XMLResourceIdentifier,String,Augmentations)

    /**
     * A notation declaration
     *
     * <p>
     *  符号声明
     * 
     * 
     * @param name     The name of the notation.
     * @param identifier    An object containing all location information
     *                      pertinent to this notation.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void notationDecl(String name, XMLResourceIdentifier identifier,
                             Augmentations augs) throws XNIException {

        // VC: Unique Notation Name
        if (fValidation) {
            DTDGrammar grammar = (fDTDGrammar != null ? fDTDGrammar : fGrammarBucket.getActiveGrammar());
            if (grammar.getNotationDeclIndex(name) != -1) {
                fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                           "UniqueNotationName",
                                           new Object[]{name},
                                           XMLErrorReporter.SEVERITY_ERROR);
            }
        }

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.notationDecl(name, identifier, augs);
        if (fDTDHandler != null) {
            fDTDHandler.notationDecl(name, identifier, augs);
        }

    } // notationDecl(String,XMLResourceIdentifier, Augmentations)

    /**
     * The start of a conditional section.
     *
     * <p>
     *  条件段的开始。
     * 
     * 
     * @param type The type of the conditional section. This value will
     *             either be CONDITIONAL_INCLUDE or CONDITIONAL_IGNORE.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #CONDITIONAL_INCLUDE
     * @see #CONDITIONAL_IGNORE
     */
    public void startConditional(short type, Augmentations augs) throws XNIException {

        // set state
        fInDTDIgnore = type == XMLDTDHandler.CONDITIONAL_IGNORE;

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.startConditional(type, augs);
        if (fDTDHandler != null) {
            fDTDHandler.startConditional(type, augs);
        }

    } // startConditional(short)

    /**
     * The end of a conditional section.
     *
     * <p>
     *  条件段的结束。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endConditional(Augmentations augs) throws XNIException {

        // set state
        fInDTDIgnore = false;

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.endConditional(augs);
        if (fDTDHandler != null) {
            fDTDHandler.endConditional(augs);
        }

    } // endConditional()

    /**
     * The end of the DTD.
     *
     * <p>
     *  DTD的结束。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endDTD(Augmentations augs) throws XNIException {


        // save grammar
        if(fDTDGrammar != null) {
            fDTDGrammar.endDTD(augs);
            if(fGrammarPool != null)
                fGrammarPool.cacheGrammars(XMLGrammarDescription.XML_DTD, new Grammar[] {fDTDGrammar});
        }
        if (fValidation) {
            DTDGrammar grammar = (fDTDGrammar != null? fDTDGrammar: fGrammarBucket.getActiveGrammar());

            // VC : Notation Declared. for external entity declaration [Production 76].
            Iterator entities = fNDataDeclNotations.entrySet().iterator();
            while (entities.hasNext()) {
                Map.Entry entry = (Map.Entry) entities.next();
                String notation = (String) entry.getValue();
                if (grammar.getNotationDeclIndex(notation) == -1) {
                    String entity = (String) entry.getKey();
                    fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                               "MSG_NOTATION_NOT_DECLARED_FOR_UNPARSED_ENTITYDECL",
                                               new Object[]{entity, notation},
                                               XMLErrorReporter.SEVERITY_ERROR);
                }
            }

            // VC: Notation Attributes:
            //     all notation names in the (attribute) declaration must be declared.
            Iterator notationVals = fNotationEnumVals.entrySet().iterator();
            while (notationVals.hasNext()) {
                Map.Entry entry = (Map.Entry) notationVals.next();
                String notation = (String) entry.getKey();
                if (grammar.getNotationDeclIndex(notation) == -1) {
                    String attributeName = (String) entry.getValue();
                    fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                               "MSG_NOTATION_NOT_DECLARED_FOR_NOTATIONTYPE_ATTRIBUTE",
                                               new Object[]{attributeName, notation},
                                               XMLErrorReporter.SEVERITY_ERROR);
                }
            }

            // VC: No Notation on Empty Element
            // An attribute of type NOTATION must not be declared on an element declared EMPTY.
            Iterator elementsWithNotations = fTableOfNOTATIONAttributeNames.entrySet().iterator();
            while (elementsWithNotations.hasNext()) {
                Map.Entry entry = (Map.Entry) elementsWithNotations.next();
                String elementName = (String) entry.getKey();
                int elementIndex = grammar.getElementDeclIndex(elementName);
                if (grammar.getContentSpecType(elementIndex) == XMLElementDecl.TYPE_EMPTY) {
                    String attributeName = (String) entry.getValue();
                    fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                               "NoNotationOnEmptyElement",
                                               new Object[]{elementName, attributeName},
                                               XMLErrorReporter.SEVERITY_ERROR);
                }
            }

            // should be safe to release these references
            fTableOfIDAttributeNames = null;
            fTableOfNOTATIONAttributeNames = null;

            // check whether each element referenced in a content model is declared
            if (fWarnOnUndeclaredElemdef) {
                checkDeclaredElements(grammar);
            }
        }

        // call handlers
        if (fDTDHandler != null) {
            fDTDHandler.endDTD(augs);
        }

    } // endDTD()

    // sets the XMLDTDSource of this handler
    public void setDTDSource(XMLDTDSource source ) {
        fDTDSource = source;
    } // setDTDSource(XMLDTDSource)

    // returns the XMLDTDSource of this handler
    public XMLDTDSource getDTDSource() {
        return fDTDSource;
    } // getDTDSource():  XMLDTDSource

    //
    // XMLDTDContentModelHandler methods
    //

    // sets the XMLContentModelDTDSource of this handler
    public void setDTDContentModelSource(XMLDTDContentModelSource source ) {
        fDTDContentModelSource = source;
    } // setDTDContentModelSource(XMLDTDContentModelSource)

    // returns the XMLDTDSource of this handler
    public XMLDTDContentModelSource getDTDContentModelSource() {
        return fDTDContentModelSource;
    } // getDTDContentModelSource():  XMLDTDContentModelSource


    /**
     * The start of a content model. Depending on the type of the content
     * model, specific methods may be called between the call to the
     * startContentModel method and the call to the endContentModel method.
     *
     * <p>
     *  内容模型的开始。根据内容模型的类型,可以在调用startContentModel方法和调用endContentModel方法之间调用特定方法。
     * 
     * 
     * @param elementName The name of the element.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void startContentModel(String elementName, Augmentations augs)
        throws XNIException {

        if (fValidation) {
            fDTDElementDeclName = elementName;
            fMixedElementTypes.clear();
        }

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.startContentModel(elementName, augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.startContentModel(elementName, augs);
        }

    } // startContentModel(String)

    /**
     * A content model of ANY.
     *
     * <p>
     *  ANY的内容模型。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #empty
     * @see #startGroup
     */
    public void any(Augmentations augs) throws XNIException {
        if(fDTDGrammar != null)
            fDTDGrammar.any(augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.any(augs);
        }
    } // any()

    /**
     * A content model of EMPTY.
     *
     * <p>
     *  EMPTY的内容模型。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #any
     * @see #startGroup
     */
    public void empty(Augmentations augs) throws XNIException {
        if(fDTDGrammar != null)
            fDTDGrammar.empty(augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.empty(augs);
        }
    } // empty()

    /**
     * A start of either a mixed or children content model. A mixed
     * content model will immediately be followed by a call to the
     * <code>pcdata()</code> method. A children content model will
     * contain additional groups and/or elements.
     *
     * <p>
     *  混合或儿童内容模型的开始。混合内容模型将立即调用<code> pcdata()</code>方法。子内容模型将包含其他组和/或元素。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #any
     * @see #empty
     */
    public void startGroup(Augmentations augs) throws XNIException {

        fMixed = false;
        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.startGroup(augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.startGroup(augs);
        }

    } // startGroup()

    /**
     * The appearance of "#PCDATA" within a group signifying a
     * mixed content model. This method will be the first called
     * following the content model's <code>startGroup()</code>.
     *
     * <p>
     *  表示混合内容模型的组中的"#PCDATA"的外观。这个方法将首先被调用遵循内容模型的<code> startGroup()</code>。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #startGroup
     */
    public void pcdata(Augmentations augs) {
        fMixed = true;
        if(fDTDGrammar != null)
            fDTDGrammar.pcdata(augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.pcdata(augs);
        }
    } // pcdata()

    /**
     * A referenced element in a mixed or children content model.
     *
     * <p>
     *  混合或子内容模型中引用的元素。
     * 
     * 
     * @param elementName The name of the referenced element.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void element(String elementName, Augmentations augs) throws XNIException {

        // check VC: No duplicate Types, in a single mixed-content declaration
        if (fMixed && fValidation) {
            if (fMixedElementTypes.contains(elementName)) {
                fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                                           "DuplicateTypeInMixedContent",
                                           new Object[]{fDTDElementDeclName, elementName},
                                           XMLErrorReporter.SEVERITY_ERROR);
            }
            else {
                fMixedElementTypes.add(elementName);
            }
        }

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.element(elementName, augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.element(elementName, augs);
        }

    } // childrenElement(String)

    /**
     * The separator between choices or sequences of a mixed or children
     * content model.
     *
     * <p>
     *  混合或子内容模型的选择或序列之间的分隔符。
     * 
     * 
     * @param separator The type of children separator.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #SEPARATOR_CHOICE
     * @see #SEPARATOR_SEQUENCE
     */
    public void separator(short separator, Augmentations augs)
        throws XNIException {

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.separator(separator, augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.separator(separator, augs);
        }

    } // separator(short)

    /**
     * The occurrence count for a child in a children content model or
     * for the mixed content model group.
     *
     * <p>
     *  子内容模型中的子级或混合内容模型组的子级的发生计数。
     * 
     * 
     * @param occurrence The occurrence count for the last element
     *                   or group.
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     *
     * @see #OCCURS_ZERO_OR_ONE
     * @see #OCCURS_ZERO_OR_MORE
     * @see #OCCURS_ONE_OR_MORE
     */
    public void occurrence(short occurrence, Augmentations augs)
        throws XNIException {

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.occurrence(occurrence, augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.occurrence(occurrence, augs);
        }

    } // occurrence(short)

    /**
     * The end of a group for mixed or children content models.
     *
     * <p>
     *  一个组的结尾的混合或儿童内容模型。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endGroup(Augmentations augs) throws XNIException {

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.endGroup(augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.endGroup(augs);
        }

    } // endGroup()

    /**
     * The end of a content model.
     *
     * <p>
     *  内容模型的结束。
     * 
     * 
     * @param augs Additional information that may include infoset
     *                      augmentations.
     *
     * @throws XNIException Thrown by handler to signal an error.
     */
    public void endContentModel(Augmentations augs) throws XNIException {

        // call handlers
        if(fDTDGrammar != null)
            fDTDGrammar.endContentModel(augs);
        if (fDTDContentModelHandler != null) {
            fDTDContentModelHandler.endContentModel(augs);
        }

    } // endContentModel()

    //
    // Private methods
    //

    /**
     * Normalize the attribute value of a non CDATA default attribute
     * collapsing sequences of space characters (x20)
     *
     * <p>
     *  规范化非CDATA默认属性的属性值,折叠空格字符序列(x20)
     * 
     * 
     * @param value The value to normalize
     * @return Whether the value was changed or not.
     */
    private boolean normalizeDefaultAttrValue(XMLString value) {

        boolean skipSpace = true; // skip leading spaces
        int current = value.offset;
        int end = value.offset + value.length;
        for (int i = value.offset; i < end; i++) {
            if (value.ch[i] == ' ') {
                if (!skipSpace) {
                    // take the first whitespace as a space and skip the others
                    value.ch[current++] = ' ';
                    skipSpace = true;
                }
                else {
                    // just skip it.
                }
            }
            else {
                // simply shift non space chars if needed
                if (current != i) {
                    value.ch[current] = value.ch[i];
                }
                current++;
                skipSpace = false;
            }
        }
        if (current != end) {
            if (skipSpace) {
                // if we finished on a space trim it
                current--;
            }
            // set the new value length
            value.length = current - value.offset;
            return true;
        }
        return false;
    }

    protected boolean isValidNmtoken(String nmtoken) {
        return XMLChar.isValidNmtoken(nmtoken);
    } // isValidNmtoken(String):  boolean

    protected boolean isValidName(String name) {
        return XMLChar.isValidName(name);
    } // isValidName(String):  boolean

    /**
     * Checks that all elements referenced in content models have
     * been declared. This method calls out to the error handler
     * to indicate warnings.
     * <p>
     * 检查内容模型中引用的所有元素是否已声明。此方法调用错误处理程序以指示警告。
     * 
     */
    private void checkDeclaredElements(DTDGrammar grammar) {
        int elementIndex = grammar.getFirstElementDeclIndex();
        XMLContentSpec contentSpec = new XMLContentSpec();
        while (elementIndex >= 0) {
            int type = grammar.getContentSpecType(elementIndex);
            if (type == XMLElementDecl.TYPE_CHILDREN || type == XMLElementDecl.TYPE_MIXED) {
                checkDeclaredElements(grammar,
                        elementIndex,
                        grammar.getContentSpecIndex(elementIndex),
                        contentSpec);
            }
            elementIndex = grammar.getNextElementDeclIndex(elementIndex);
        }
    }

    /**
     * Does a recursive (if necessary) check on the specified element's
     * content spec to make sure that all children refer to declared
     * elements.
     * <p>
     *  对指定元素的内容规范进行递归检查(如果必要),以确保所有子元素都引用声明的元素。
     */
    private void checkDeclaredElements(DTDGrammar grammar, int elementIndex,
            int contentSpecIndex, XMLContentSpec contentSpec) {
        grammar.getContentSpec(contentSpecIndex, contentSpec);
        if (contentSpec.type == XMLContentSpec.CONTENTSPECNODE_LEAF) {
            String value = (String) contentSpec.value;
            if (value != null && grammar.getElementDeclIndex(value) == -1) {
                fErrorReporter.reportError(XMLMessageFormatter.XML_DOMAIN,
                        "UndeclaredElementInContentSpec",
                        new Object[]{grammar.getElementDeclName(elementIndex).rawname, value},
                        XMLErrorReporter.SEVERITY_WARNING);
            }
        }
        // It's not a leaf, so we have to recurse its left and maybe right
        // nodes. Save both values before we recurse and trash the node.
        else if ((contentSpec.type == XMLContentSpec.CONTENTSPECNODE_CHOICE)
                || (contentSpec.type == XMLContentSpec.CONTENTSPECNODE_SEQ)) {
            final int leftNode = ((int[])contentSpec.value)[0];
            final int rightNode = ((int[])contentSpec.otherValue)[0];
            //  Recurse on both children.
            checkDeclaredElements(grammar, elementIndex, leftNode, contentSpec);
            checkDeclaredElements(grammar, elementIndex, rightNode, contentSpec);
        }
        else if (contentSpec.type == XMLContentSpec.CONTENTSPECNODE_ZERO_OR_MORE
                || contentSpec.type == XMLContentSpec.CONTENTSPECNODE_ZERO_OR_ONE
                || contentSpec.type == XMLContentSpec.CONTENTSPECNODE_ONE_OR_MORE) {
            final int leftNode = ((int[])contentSpec.value)[0];
            checkDeclaredElements(grammar, elementIndex, leftNode, contentSpec);
        }
    }

} // class XMLDTDProcessor
