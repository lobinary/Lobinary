/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
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
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.jaxp.validation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;

import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
import com.sun.org.apache.xerces.internal.impl.validation.ValidationManager;
import com.sun.org.apache.xerces.internal.impl.xs.XMLSchemaValidator;
import com.sun.org.apache.xerces.internal.impl.xs.XSMessageFormatter;
import com.sun.org.apache.xerces.internal.util.DOMEntityResolverWrapper;
import com.sun.org.apache.xerces.internal.util.ErrorHandlerWrapper;
import com.sun.org.apache.xerces.internal.util.FeatureState;
import com.sun.org.apache.xerces.internal.util.NamespaceSupport;
import com.sun.org.apache.xerces.internal.util.ParserConfigurationSettings;
import com.sun.org.apache.xerces.internal.util.PropertyState;
import com.sun.org.apache.xerces.internal.util.Status;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.utils.XMLSecurityPropertyManager;
import com.sun.org.apache.xerces.internal.utils.XMLSecurityManager;
import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;

/**
 * <p>An implementation of XMLComponentManager for a schema validator.</p>
 *
 * <p>
 *  <p>用于模式验证程序的XMLComponentManager的实现</p>
 * 
 * 
 * @author Michael Glavassevich, IBM
 * @version $Id: XMLSchemaValidatorComponentManager.java,v 1.9 2010-11-01 04:40:08 joehw Exp $
 */
final class XMLSchemaValidatorComponentManager extends ParserConfigurationSettings implements
        XMLComponentManager {

    // feature identifiers

    /** Feature identifier: schema validation. */
    private static final String SCHEMA_VALIDATION =
        Constants.XERCES_FEATURE_PREFIX + Constants.SCHEMA_VALIDATION_FEATURE;

    /** Feature identifier: validation. */
    private static final String VALIDATION =
        Constants.SAX_FEATURE_PREFIX + Constants.VALIDATION_FEATURE;

    /** Feature identifier: send element default value via characters() */
    private static final String SCHEMA_ELEMENT_DEFAULT =
        Constants.XERCES_FEATURE_PREFIX + Constants.SCHEMA_ELEMENT_DEFAULT;

    /** Feature identifier: use grammar pool only. */
    private static final String USE_GRAMMAR_POOL_ONLY =
        Constants.XERCES_FEATURE_PREFIX + Constants.USE_GRAMMAR_POOL_ONLY_FEATURE;

    // property identifiers

    /** Property identifier: entity manager. */
    private static final String ENTITY_MANAGER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_MANAGER_PROPERTY;

    /** Property identifier: entity resolver. */
    private static final String ENTITY_RESOLVER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_RESOLVER_PROPERTY;

    /** Property identifier: error handler. */
    private static final String ERROR_HANDLER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_HANDLER_PROPERTY;

    /** Property identifier: error reporter. */
    private static final String ERROR_REPORTER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_REPORTER_PROPERTY;

    /** Property identifier: namespace context. */
    private static final String NAMESPACE_CONTEXT =
        Constants.XERCES_PROPERTY_PREFIX + Constants.NAMESPACE_CONTEXT_PROPERTY;

    /** Property identifier: XML Schema validator. */
    private static final String SCHEMA_VALIDATOR =
        Constants.XERCES_PROPERTY_PREFIX + Constants.SCHEMA_VALIDATOR_PROPERTY;

    /** Property identifier: security manager. */
    private static final String SECURITY_MANAGER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.SECURITY_MANAGER_PROPERTY;

    /** Property identifier: security property manager. */
    private static final String XML_SECURITY_PROPERTY_MANAGER =
            Constants.XML_SECURITY_PROPERTY_MANAGER;

    /** Property identifier: symbol table. */
    private static final String SYMBOL_TABLE =
        Constants.XERCES_PROPERTY_PREFIX + Constants.SYMBOL_TABLE_PROPERTY;

    /** Property identifier: validation manager. */
    private static final String VALIDATION_MANAGER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.VALIDATION_MANAGER_PROPERTY;

    /** Property identifier: grammar pool. */
    private static final String XMLGRAMMAR_POOL =
        Constants.XERCES_PROPERTY_PREFIX + Constants.XMLGRAMMAR_POOL_PROPERTY;

    /** Property identifier: locale. */
    private static final String LOCALE =
        Constants.XERCES_PROPERTY_PREFIX + Constants.LOCALE_PROPERTY;

    //
    // Data
    //
    /**
     * <p>State of secure mode.</p>
     * <p>
     *  <p>安全模式的状态。</p>
     * 
     */
    private boolean _isSecureMode = false;

    /**
     * fConfigUpdated is set to true if there has been any change to the configuration settings,
     * i.e a feature or a property was changed.
     * <p>
     *  如果配置设置有任何更改(即某个功能或属性已更改),则fConfigUpdated设置为true。
     * 
     */
    private boolean fConfigUpdated = true;

    /**
     * Tracks whether the validator should use components from
     * the grammar pool to the exclusion of all others.
     * <p>
     *  跟踪验证器是否应使用语法池中的组件,排除所有其他组件。
     * 
     */
    private boolean fUseGrammarPoolOnly;

    /** Lookup map for components required for validation. **/
    private final HashMap fComponents = new HashMap();

    //
    // Components
    //

    /** Entity manager. */
    private XMLEntityManager fEntityManager;

    /** Error reporter. */
    private XMLErrorReporter fErrorReporter;

    /** Namespace context. */
    private NamespaceContext fNamespaceContext;

    /** XML Schema validator. */
    private XMLSchemaValidator fSchemaValidator;

    /** Validation manager. */
    private ValidationManager fValidationManager;

    //
    // Configuration
    //

    /** Stores initial feature values for validator reset. */
    private final HashMap fInitFeatures = new HashMap();

    /** Stores initial property values for validator reset. */
    private final HashMap fInitProperties = new HashMap();

    /** Stores the initial security manager. */
    private XMLSecurityManager fInitSecurityManager;

    /** Stores the initial security property manager. */
    private final XMLSecurityPropertyManager fSecurityPropertyMgr;

    //
    // User Objects
    //

    /** Application's ErrorHandler. **/
    private ErrorHandler fErrorHandler = null;

    /** Application's LSResourceResolver. */
    private LSResourceResolver fResourceResolver = null;

    /** Locale chosen by the application. */
    private Locale fLocale = null;

    /** Constructs a component manager suitable for Xerces' schema validator. */
    public XMLSchemaValidatorComponentManager(XSGrammarPoolContainer grammarContainer) {
        // setup components
        fEntityManager = new XMLEntityManager();
        fComponents.put(ENTITY_MANAGER, fEntityManager);

        fErrorReporter = new XMLErrorReporter();
        fComponents.put(ERROR_REPORTER, fErrorReporter);

        fNamespaceContext = new NamespaceSupport();
        fComponents.put(NAMESPACE_CONTEXT, fNamespaceContext);

        fSchemaValidator = new XMLSchemaValidator();
        fComponents.put(SCHEMA_VALIDATOR, fSchemaValidator);

        fValidationManager = new ValidationManager();
        fComponents.put(VALIDATION_MANAGER, fValidationManager);

        // setup other properties
        fComponents.put(ENTITY_RESOLVER, null);
        fComponents.put(ERROR_HANDLER, null);

        fComponents.put(SYMBOL_TABLE, new SymbolTable());

        // setup grammar pool
        fComponents.put(XMLGRAMMAR_POOL, grammarContainer.getGrammarPool());
        fUseGrammarPoolOnly = grammarContainer.isFullyComposed();

        // add schema message formatter to error reporter
        fErrorReporter.putMessageFormatter(XSMessageFormatter.SCHEMA_DOMAIN, new XSMessageFormatter());

        // add all recognized features and properties and apply their defaults
        addRecognizedParamsAndSetDefaults(fEntityManager, grammarContainer);
        addRecognizedParamsAndSetDefaults(fErrorReporter, grammarContainer);
        addRecognizedParamsAndSetDefaults(fSchemaValidator, grammarContainer);

        boolean secureProcessing = grammarContainer.getFeature(XMLConstants.FEATURE_SECURE_PROCESSING);
        if (System.getSecurityManager() != null) {
            _isSecureMode = true;
            secureProcessing = true;
        }

        fInitSecurityManager = (XMLSecurityManager)
                grammarContainer.getProperty(SECURITY_MANAGER);
        if (fInitSecurityManager != null ) {
            fInitSecurityManager.setSecureProcessing(secureProcessing);
        } else {
            fInitSecurityManager = new XMLSecurityManager(secureProcessing);
        }

        setProperty(SECURITY_MANAGER, fInitSecurityManager);

        //pass on properties set on SchemaFactory
        fSecurityPropertyMgr = (XMLSecurityPropertyManager)
                grammarContainer.getProperty(Constants.XML_SECURITY_PROPERTY_MANAGER);
        setProperty(XML_SECURITY_PROPERTY_MANAGER, fSecurityPropertyMgr);
    }

    /**
     * Returns the state of a feature.
     *
     * <p>
     *  返回要素的状态。
     * 
     * 
     * @param featureId The feature identifier.
     * @return true if the feature is supported
     *
     * @throws XMLConfigurationException Thrown for configuration error.
     *                                   In general, components should
     *                                   only throw this exception if
     *                                   it is <strong>really</strong>
     *                                   a critical error.
     */
    public FeatureState getFeatureState(String featureId)
            throws XMLConfigurationException {
        if (PARSER_SETTINGS.equals(featureId)) {
            return FeatureState.is(fConfigUpdated);
        }
        else if (VALIDATION.equals(featureId) || SCHEMA_VALIDATION.equals(featureId)) {
            return FeatureState.is(true);
        }
        else if (USE_GRAMMAR_POOL_ONLY.equals(featureId)) {
            return FeatureState.is(fUseGrammarPoolOnly);
        }
        else if (XMLConstants.FEATURE_SECURE_PROCESSING.equals(featureId)) {
            return FeatureState.is(fInitSecurityManager.isSecureProcessing());
        }
        else if (SCHEMA_ELEMENT_DEFAULT.equals(featureId)) {
            return FeatureState.is(true); //pre-condition: VALIDATION and SCHEMA_VALIDATION are always true
        }
        return super.getFeatureState(featureId);
    }

    /**
     * Set the state of a feature.
     *
     * <p>
     *  设置要素的状态。
     * 
     * 
     * @param featureId The unique identifier (URI) of the feature.
     * @param state The requested state of the feature (true or false).
     *
     * @exception XMLConfigurationException If the requested feature is not known.
     */
    public void setFeature(String featureId, boolean value) throws XMLConfigurationException {
        if (PARSER_SETTINGS.equals(featureId)) {
            throw new XMLConfigurationException(Status.NOT_SUPPORTED, featureId);
        }
        else if (value == false && (VALIDATION.equals(featureId) || SCHEMA_VALIDATION.equals(featureId))) {
            throw new XMLConfigurationException(Status.NOT_SUPPORTED, featureId);
        }
        else if (USE_GRAMMAR_POOL_ONLY.equals(featureId) && value != fUseGrammarPoolOnly) {
            throw new XMLConfigurationException(Status.NOT_SUPPORTED, featureId);
        }
        if (XMLConstants.FEATURE_SECURE_PROCESSING.equals(featureId)) {
            if (_isSecureMode && !value) {
                throw new XMLConfigurationException(Status.NOT_ALLOWED, XMLConstants.FEATURE_SECURE_PROCESSING);
            }

            fInitSecurityManager.setSecureProcessing(value);
            setProperty(SECURITY_MANAGER, fInitSecurityManager);

            if (value && Constants.IS_JDK8_OR_ABOVE) {
                fSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_DTD,
                        XMLSecurityPropertyManager.State.FSP, Constants.EXTERNAL_ACCESS_DEFAULT_FSP);
                fSecurityPropertyMgr.setValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_SCHEMA,
                        XMLSecurityPropertyManager.State.FSP, Constants.EXTERNAL_ACCESS_DEFAULT_FSP);
                setProperty(XML_SECURITY_PROPERTY_MANAGER, fSecurityPropertyMgr);
            }

            return;
        }
        fConfigUpdated = true;
        fEntityManager.setFeature(featureId, value);
        fErrorReporter.setFeature(featureId, value);
        fSchemaValidator.setFeature(featureId, value);
        if (!fInitFeatures.containsKey(featureId)) {
            boolean current = super.getFeature(featureId);
            fInitFeatures.put(featureId, current ? Boolean.TRUE : Boolean.FALSE);
        }
        super.setFeature(featureId, value);
    }

    /**
     * Returns the value of a property.
     *
     * <p>
     *  返回属性的值。
     * 
     * 
     * @param propertyId The property identifier.
     * @return the value of the property
     *
     * @throws XMLConfigurationException Thrown for configuration error.
     *                                   In general, components should
     *                                   only throw this exception if
     *                                   it is <strong>really</strong>
     *                                   a critical error.
     */
    public PropertyState getPropertyState(String propertyId)
            throws XMLConfigurationException {
        if (LOCALE.equals(propertyId)) {
            return PropertyState.is(getLocale());
        }
        final Object component = fComponents.get(propertyId);
        if (component != null) {
            return PropertyState.is(component);
        }
        else if (fComponents.containsKey(propertyId)) {
            return PropertyState.is(null);
        }
        return super.getPropertyState(propertyId);
    }

    /**
     * Sets the state of a property.
     *
     * <p>
     *  设置属性的状态。
     * 
     * 
     * @param propertyId The unique identifier (URI) of the property.
     * @param value The requested state of the property.
     *
     * @exception XMLConfigurationException If the requested property is not known.
     */
    public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
        if ( ENTITY_MANAGER.equals(propertyId) || ERROR_REPORTER.equals(propertyId) ||
             NAMESPACE_CONTEXT.equals(propertyId) || SCHEMA_VALIDATOR.equals(propertyId) ||
             SYMBOL_TABLE.equals(propertyId) || VALIDATION_MANAGER.equals(propertyId) ||
             XMLGRAMMAR_POOL.equals(propertyId)) {
            throw new XMLConfigurationException(Status.NOT_SUPPORTED, propertyId);
        }
        fConfigUpdated = true;
        fEntityManager.setProperty(propertyId, value);
        fErrorReporter.setProperty(propertyId, value);
        fSchemaValidator.setProperty(propertyId, value);
        if (ENTITY_RESOLVER.equals(propertyId) || ERROR_HANDLER.equals(propertyId) ||
                SECURITY_MANAGER.equals(propertyId)) {
            fComponents.put(propertyId, value);
            return;
        }
        else if (LOCALE.equals(propertyId)) {
            setLocale((Locale) value);
            fComponents.put(propertyId, value);
            return;
        }
        //check if the property is managed by security manager
        if (fInitSecurityManager == null ||
                !fInitSecurityManager.setLimit(propertyId, XMLSecurityManager.State.APIPROPERTY, value)) {
            //check if the property is managed by security property manager
            if (fSecurityPropertyMgr == null ||
                    !fSecurityPropertyMgr.setValue(propertyId, XMLSecurityPropertyManager.State.APIPROPERTY, value)) {
                //fall back to the existing property manager
                if (!fInitProperties.containsKey(propertyId)) {
                    fInitProperties.put(propertyId, super.getProperty(propertyId));
                }
                super.setProperty(propertyId, value);
            }
        }
    }

    /**
     * Adds all of the component's recognized features and properties
     * to the list of default recognized features and properties, and
     * sets default values on the configuration for features and
     * properties which were previously absent from the configuration.
     *
     * <p>
     *  将所有组件的已识别要素和属性添加到默认已识别要素和属性的列表中,并在配置上设置先前在配置中不存在的要素和属性的默认值。
     * 
     * @param component The component whose recognized features
     * and properties will be added to the configuration
     */
    public void addRecognizedParamsAndSetDefaults(XMLComponent component, XSGrammarPoolContainer grammarContainer) {

        // register component's recognized features
        final String[] recognizedFeatures = component.getRecognizedFeatures();
        addRecognizedFeatures(recognizedFeatures);

        // register component's recognized properties
        final String[] recognizedProperties = component.getRecognizedProperties();
        addRecognizedProperties(recognizedProperties);

        // set default values
        setFeatureDefaults(component, recognizedFeatures, grammarContainer);
        setPropertyDefaults(component, recognizedProperties);
    }

    /** Calls reset on each of the components owned by this component manager. **/
    public void reset() throws XNIException {
        fNamespaceContext.reset();
        fValidationManager.reset();
        fEntityManager.reset(this);
        fErrorReporter.reset(this);
        fSchemaValidator.reset(this);
        // Mark configuration as fixed.
        fConfigUpdated = false;
    }

    void setErrorHandler(ErrorHandler errorHandler) {
        fErrorHandler = errorHandler;
        setProperty(ERROR_HANDLER, (errorHandler != null) ? new ErrorHandlerWrapper(errorHandler) :
                new ErrorHandlerWrapper(DraconianErrorHandler.getInstance()));
    }

    ErrorHandler getErrorHandler() {
        return fErrorHandler;
    }

    void setResourceResolver(LSResourceResolver resourceResolver) {
        fResourceResolver = resourceResolver;
        setProperty(ENTITY_RESOLVER, new DOMEntityResolverWrapper(resourceResolver));
    }

    LSResourceResolver getResourceResolver() {
        return fResourceResolver;
    }

    void setLocale(Locale locale) {
        fLocale = locale;
        fErrorReporter.setLocale(locale);
    }

    Locale getLocale() {
        return fLocale;
    }

    /** Cleans out configuration, restoring it to its initial state. */
    void restoreInitialState() {
        fConfigUpdated = true;

        // Remove error resolver and error handler
        fComponents.put(ENTITY_RESOLVER, null);
        fComponents.put(ERROR_HANDLER, null);

        // Set the Locale back to null.
        setLocale(null);
        fComponents.put(LOCALE, null);

        // Restore initial security manager
        fComponents.put(SECURITY_MANAGER, fInitSecurityManager);

        // Set the Locale back to null.
        setLocale(null);
        fComponents.put(LOCALE, null);

        // Reset feature and property values to their initial values
        if (!fInitFeatures.isEmpty()) {
            Iterator iter = fInitFeatures.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String name = (String) entry.getKey();
                boolean value = ((Boolean) entry.getValue()).booleanValue();
                super.setFeature(name, value);
            }
            fInitFeatures.clear();
        }
        if (!fInitProperties.isEmpty()) {
            Iterator iter = fInitProperties.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String name = (String) entry.getKey();
                Object value = entry.getValue();
                super.setProperty(name, value);
            }
            fInitProperties.clear();
        }
    }

    /** Sets feature defaults for the given component on this configuration. */
    private void setFeatureDefaults(final XMLComponent component,
            final String [] recognizedFeatures, XSGrammarPoolContainer grammarContainer) {
        if (recognizedFeatures != null) {
            for (int i = 0; i < recognizedFeatures.length; ++i) {
                String featureId = recognizedFeatures[i];
                Boolean state = grammarContainer.getFeature(featureId);
                if (state == null) {
                    state = component.getFeatureDefault(featureId);
                }
                if (state != null) {
                    // Do not overwrite values already set on the configuration.
                    if (!fFeatures.containsKey(featureId)) {
                        fFeatures.put(featureId, state);
                        // For newly added components who recognize this feature
                        // but did not offer a default value, we need to make
                        // sure these components will get an opportunity to read
                        // the value before parsing begins.
                        fConfigUpdated = true;
                    }
                }
            }
        }
    }

    /** Sets property defaults for the given component on this configuration. */
    private void setPropertyDefaults(final XMLComponent component, final String [] recognizedProperties) {
        if (recognizedProperties != null) {
            for (int i = 0; i < recognizedProperties.length; ++i) {
                String propertyId = recognizedProperties[i];
                Object value = component.getPropertyDefault(propertyId);
                if (value != null) {
                    // Do not overwrite values already set on the configuration.
                    if (!fProperties.containsKey(propertyId)) {
                        fProperties.put(propertyId, value);
                        // For newly added components who recognize this property
                        // but did not offer a default value, we need to make
                        // sure these components will get an opportunity to read
                        // the value before parsing begins.
                        fConfigUpdated = true;
                    }
                }
            }
        }
    }

} // XMLSchemaValidatorComponentManager
