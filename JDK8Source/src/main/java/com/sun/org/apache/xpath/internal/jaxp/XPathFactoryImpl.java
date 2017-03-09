/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
// $Id: XPathFactoryImpl.java,v 1.2 2005/08/16 22:41:13 jeffsuttor Exp $

package com.sun.org.apache.xpath.internal.jaxp;

import com.sun.org.apache.xalan.internal.XalanConstants;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;
import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xalan.internal.utils.FeatureManager;
import com.sun.org.apache.xalan.internal.utils.FeaturePropertyBase;

import javax.xml.XMLConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;

/**
 * The XPathFactory builds XPaths.
 *
 * <p>
 *  XPathFactory构建XPath。
 * 
 * 
 * @version $Revision: 1.11 $
 * @author  Ramesh Mandava
 */
public  class XPathFactoryImpl extends XPathFactory {

        /**
         * <p>Name of class as a constant to use for debugging.</p>
         * <p>
         *  <p>类名称为用于调试的常量</p>
         * 
         */
        private static final String CLASS_NAME = "XPathFactoryImpl";

        /**
         *<p>XPathFunctionResolver for this XPathFactory and created XPaths.</p>
         * <p>
         *  p> XPathFunctionResolver用于此XPathFactory并创建XPath。</p>
         * 
         */
        private XPathFunctionResolver xPathFunctionResolver = null;

        /**
         * <p>XPathVariableResolver for this XPathFactory and created XPaths</p>
         * <p>
         *  <p>此XPathFactory的XPathVariableResolver和创建的XPath </p>
         * 
         */
        private XPathVariableResolver xPathVariableResolver = null;

        /**
         * <p>State of secure processing feature.</p>
         * <p>
         *  <p>安全处理功能的状态。</p>
         * 
         */
        private boolean _isNotSecureProcessing = true;
        /**
         * <p>State of secure mode.</p>
         * <p>
         *  <p>安全模式的状态。</p>
         * 
         */
        private boolean _isSecureMode = false;
        /**
         * javax.xml.xpath.XPathFactory implementation.
         * <p>
         *  javax.xml.xpath.XPathFactory实现。
         * 
         */

        private boolean _useServicesMechanism = true;

        private final FeatureManager _featureManager;

        public XPathFactoryImpl() {
            this(true);
        }

        public static XPathFactory newXPathFactoryNoServiceLoader() {
            return new XPathFactoryImpl(false);
        }

        public XPathFactoryImpl(boolean useServicesMechanism) {
            _featureManager = new FeatureManager();
            if (System.getSecurityManager() != null) {
                _isSecureMode = true;
                _isNotSecureProcessing = false;
                _featureManager.setValue(FeatureManager.Feature.ORACLE_ENABLE_EXTENSION_FUNCTION,
                        FeaturePropertyBase.State.FSP, XalanConstants.FEATURE_FALSE);
            }
            this._useServicesMechanism = useServicesMechanism;
        }
        /**
         * <p>Is specified object model supported by this
         * <code>XPathFactory</code>?</p>
         *
         * <p>
         *  <p>此<code> XPathFactory </code>?</p>支持指定的对象模型
         * 
         * 
         * @param objectModel Specifies the object model which the returned
         * <code>XPathFactory</code> will understand.
         *
         * @return <code>true</code> if <code>XPathFactory</code> supports
         * <code>objectModel</code>, else <code>false</code>.
         *
         * @throws NullPointerException If <code>objectModel</code> is <code>null</code>.
         * @throws IllegalArgumentException If <code>objectModel.length() == 0</code>.
         */
        public boolean isObjectModelSupported(String objectModel) {

            if (objectModel == null) {
                String fmsg = XSLMessages.createXPATHMessage(
                        XPATHErrorResources.ER_OBJECT_MODEL_NULL,
                        new Object[] { this.getClass().getName() } );

                throw new NullPointerException( fmsg );
            }

            if (objectModel.length() == 0) {
                String fmsg = XSLMessages.createXPATHMessage(
                        XPATHErrorResources.ER_OBJECT_MODEL_EMPTY,
                        new Object[] { this.getClass().getName() } );
                throw new IllegalArgumentException( fmsg );
            }

            // know how to support default object model, W3C DOM
            if (objectModel.equals(XPathFactory.DEFAULT_OBJECT_MODEL_URI)) {
                return true;
            }

            // don't know how to support anything else
            return false;
        }

        /**
         * <p>Returns a new <code>XPath</code> object using the underlying
         * object model determined when the factory was instantiated.</p>
         *
         * <p>
         *  <p>使用在实例化工厂时确定的基础对象模型返回新的<code> XPath </code>对象。</p>
         * 
         * 
         * @return New <code>XPath</code>
         */
        public javax.xml.xpath.XPath newXPath() {
            return new com.sun.org.apache.xpath.internal.jaxp.XPathImpl(
                    xPathVariableResolver, xPathFunctionResolver,
                    !_isNotSecureProcessing, _useServicesMechanism,
                    _featureManager );
        }

        /**
         * <p>Set a feature for this <code>XPathFactory</code> and
         * <code>XPath</code>s created by this factory.</p>
         *
         * <p>
         * Feature names are fully qualified {@link java.net.URI}s.
         * Implementations may define their own features.
         * An {@link XPathFactoryConfigurationException} is thrown if this
         * <code>XPathFactory</code> or the <code>XPath</code>s
         *  it creates cannot support the feature.
         * It is possible for an <code>XPathFactory</code> to expose a feature
         * value but be unable to change its state.
         * </p>
         *
         * <p>See {@link javax.xml.xpath.XPathFactory} for full documentation
         * of specific features.</p>
         *
         * <p>
         *  <p>为此工厂创建的<code> XPathFactory </code>和<code> XPath </code>设置一个功能。</p>
         * 
         * <p>
         * 功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
         * 如果此<code> XPathFactory </code>或其创建的<code> XPath </code>无法支持该功能,则会抛出{@link XPathFactoryConfigurationException}
         * 。
         * 功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。一个<code> XPathFactory </code>可以暴露一个特征值,但是无法改变它的状态。
         * </p>
         * 
         *  <p>有关特定功能的完整文档,请参阅{@link javax.xml.xpath.XPathFactory}。</p>
         * 
         * 
         * @param name Feature name.
         * @param value Is feature state <code>true</code> or <code>false</code>.
         *
         * @throws XPathFactoryConfigurationException if this
         * <code>XPathFactory</code> or the <code>XPath</code>s
         *   it creates cannot support this feature.
         * @throws NullPointerException if <code>name</code> is
         * <code>null</code>.
         */
        public void setFeature(String name, boolean value)
                throws XPathFactoryConfigurationException {

            // feature name cannot be null
            if (name == null) {
                String fmsg = XSLMessages.createXPATHMessage(
                        XPATHErrorResources.ER_FEATURE_NAME_NULL,
                        new Object[] { CLASS_NAME, new Boolean( value) } );
                throw new NullPointerException( fmsg );
             }

            // secure processing?
            if (name.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
                if ((_isSecureMode) && (!value)) {
                    String fmsg = XSLMessages.createXPATHMessage(
                            XPATHErrorResources.ER_SECUREPROCESSING_FEATURE,
                            new Object[] { name, CLASS_NAME, new Boolean(value) } );
                    throw new XPathFactoryConfigurationException( fmsg );
                }

                _isNotSecureProcessing = !value;
                if (value && _featureManager != null) {
                    _featureManager.setValue(FeatureManager.Feature.ORACLE_ENABLE_EXTENSION_FUNCTION,
                            FeaturePropertyBase.State.FSP, XalanConstants.FEATURE_FALSE);
                }

                // all done processing feature
                return;
            }
            if (name.equals(XalanConstants.ORACLE_FEATURE_SERVICE_MECHANISM)) {
                //in secure mode, let _useServicesMechanism be determined by the constructor
                if (!_isSecureMode)
                    _useServicesMechanism = value;
                return;
            }

            if (_featureManager != null &&
                    _featureManager.setValue(name, FeaturePropertyBase.State.APIPROPERTY, value)) {
                return;
            }

            // unknown feature
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_FEATURE_UNKNOWN,
                    new Object[] { name, CLASS_NAME, new Boolean(value) } );
            throw new XPathFactoryConfigurationException( fmsg );
        }

        /**
         * <p>Get the state of the named feature.</p>
         *
         * <p>
         * Feature names are fully qualified {@link java.net.URI}s.
         * Implementations may define their own features.
         * An {@link XPathFactoryConfigurationException} is thrown if this
         * <code>XPathFactory</code> or the <code>XPath</code>s
         * it creates cannot support the feature.
         * It is possible for an <code>XPathFactory</code> to expose a feature
         * value but be unable to change its state.
         * </p>
         *
         * <p>
         *  <p>获取命名要素的状态。</p>
         * 
         * <p>
         *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
         * 如果此<code> XPathFactory </code>或其创建的<code> XPath </code>无法支持该功能,则会抛出{@link XPathFactoryConfigurationException}
         * 。
         *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。一个<code> XPathFactory </code>可以暴露一个特征值,但是无法改变它的状态。
         * </p>
         * 
         * 
         * @param name Feature name.
         *
         * @return State of the named feature.
         *
         * @throws XPathFactoryConfigurationException if this
         * <code>XPathFactory</code> or the <code>XPath</code>s
         *   it creates cannot support this feature.
         * @throws NullPointerException if <code>name</code> is
         * <code>null</code>.
         */
        public boolean getFeature(String name)
                throws XPathFactoryConfigurationException {

            // feature name cannot be null
            if (name == null) {
                String fmsg = XSLMessages.createXPATHMessage(
                        XPATHErrorResources.ER_GETTING_NULL_FEATURE,
                        new Object[] { CLASS_NAME } );
                throw new NullPointerException( fmsg );
            }

            // secure processing?
            if (name.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
                return !_isNotSecureProcessing;
            }
            if (name.equals(XalanConstants.ORACLE_FEATURE_SERVICE_MECHANISM)) {
                return _useServicesMechanism;
            }

            /** Check to see if the property is managed by the security manager **/
            String propertyValue = (_featureManager != null) ?
                    _featureManager.getValueAsString(name) : null;
            if (propertyValue != null) {
                return _featureManager.isFeatureEnabled(name);
            }

            // unknown feature
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_GETTING_UNKNOWN_FEATURE,
                    new Object[] { name, CLASS_NAME } );

            throw new XPathFactoryConfigurationException( fmsg );
        }

        /**
         * <p>Establish a default function resolver.</p>
         *
         * <p>Any <code>XPath</code> objects constructed from this factory will use
         * the specified resolver by default.</p>
         *
         * <p>A <code>NullPointerException</code> is thrown if
         * <code>resolver</code> is <code>null</code>.</p>
         *
         * <p>
         *  <p>建立默认函数解析器。</p>
         * 
         *  <p>默认情况下,从此工厂构造的任何<code> XPath </code>对象将使用指定的解析器。</p>
         * 
         *  <p>如果<code> resolver </code>为<code> null </code>,则抛出<code> NullPointerException </code>。</p>
         * 
         * 
         * @param resolver XPath function resolver.
         *
         * @throws NullPointerException If <code>resolver</code> is
         * <code>null</code>.
         */
        public void setXPathFunctionResolver(XPathFunctionResolver resolver) {

            // resolver cannot be null
            if (resolver == null) {
                String fmsg = XSLMessages.createXPATHMessage(
                        XPATHErrorResources.ER_NULL_XPATH_FUNCTION_RESOLVER,
                        new Object[] {  CLASS_NAME } );
                throw new NullPointerException( fmsg );
            }

            xPathFunctionResolver = resolver;
        }

        /**
         * <p>Establish a default variable resolver.</p>
         *
         * <p>Any <code>XPath</code> objects constructed from this factory will use
         * the specified resolver by default.</p>
         *
         * <p>A <code>NullPointerException</code> is thrown if <code>resolver</code> is <code>null</code>.</p>
         *
         * <p>
         *  <p>建立一个默认变量解析器。</p>
         * 
         *  <p>默认情况下,从此工厂构造的任何<code> XPath </code>对象将使用指定的解析器。</p>
         * 
         * @param resolver Variable resolver.
         *
         *  @throws NullPointerException If <code>resolver</code> is
         * <code>null</code>.
         */
        public void setXPathVariableResolver(XPathVariableResolver resolver) {

                // resolver cannot be null
                if (resolver == null) {
                    String fmsg = XSLMessages.createXPATHMessage(
                            XPATHErrorResources.ER_NULL_XPATH_VARIABLE_RESOLVER,
                            new Object[] {  CLASS_NAME } );
                    throw new NullPointerException( fmsg );
                }

                xPathVariableResolver = resolver;
        }
}
