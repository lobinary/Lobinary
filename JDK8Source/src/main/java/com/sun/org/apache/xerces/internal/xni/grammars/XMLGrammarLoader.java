/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
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
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xni.grammars;

import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import com.sun.org.apache.xerces.internal.xni.XNIException;

import java.io.IOException;
import java.util.Locale;

/**
 * The intention of this interface is to provide a generic means
 * by which Grammar objects may be created without parsing instance
 * documents.  Implementations of this interface will know how to load
 * specific types of grammars (e.g., DTD's or schemas); a wrapper
 * will be provided for user applications to interact with these implementations.
 *
 * <p>
 *  这个接口的目的是提供一个通用的手段,通过它可以创建语法对象而不需要解析实例文档。此接口的实现将知道如何加载特定类型的语法(例如,DTD或模式);将提供包装器以使用户应用程序与这些实现交互。
 * 
 * 
 * @author Neil Graham, IBM
 */

public interface XMLGrammarLoader {

    /**
     * Returns a list of feature identifiers that are recognized by
     * this XMLGrammarLoader.  This method may return null if no features
     * are recognized.
     * <p>
     *  返回此XMLGrammarLoader可识别的要素标识符列表。如果没有识别特征,此方法可能返回null。
     * 
     */
    public String[] getRecognizedFeatures();

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
            throws XMLConfigurationException;

    /**
     * Sets the state of a feature.
     *
     * <p>
     *  设置要素的状态。
     * 
     * 
     * @param featureId The feature identifier.
     * @param state     The state of the feature.
     *
     * @throws XMLConfigurationException Thrown when a feature is not
     *                  recognized or cannot be set.
     */
    public void setFeature(String featureId,
                boolean state) throws XMLConfigurationException;

    /**
     * Returns a list of property identifiers that are recognized by
     * this XMLGrammarLoader.  This method may return null if no properties
     * are recognized.
     * <p>
     *  返回此XMLGrammarLoader可识别的属性标识符列表。如果没有识别属性,此方法可能返回null。
     * 
     */
    public String[] getRecognizedProperties();

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
            throws XMLConfigurationException;

    /**
     * Sets the state of a property.
     *
     * <p>
     *  设置属性的状态。
     * 
     * 
     * @param propertyId The property identifier.
     * @param state     The state of the property.
     *
     * @throws XMLConfigurationException Thrown when a property is not
     *                  recognized or cannot be set.
     */
    public void setProperty(String propertyId,
                Object state) throws XMLConfigurationException;

    /**
     * Set the locale to use for messages.
     *
     * <p>
     * 设置要用于消息的区域设置。
     * 
     * 
     * @param locale The locale object to use for localization of messages.
     *
     * @exception XNIException Thrown if the parser does not support the
     *                         specified locale.
     */
    public void setLocale(Locale locale);

    /** Return the Locale the XMLGrammarLoader is using. */
    public Locale getLocale();

    /**
     * Sets the error handler.
     *
     * <p>
     *  设置错误处理程序。
     * 
     * 
     * @param errorHandler The error handler.
     */
    public void setErrorHandler(XMLErrorHandler errorHandler);

    /** Returns the registered error handler.  */
    public XMLErrorHandler getErrorHandler();

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

    /** Returns the registered entity resolver.  */
    public XMLEntityResolver getEntityResolver();

    /**
     * Returns a Grammar object by parsing the contents of the
     * entity pointed to by source.
     *
     * <p>
     *  通过解析源指向的实体的内容来返回语法对象。
     * 
     * @param source        the location of the entity which forms
     *                          the starting point of the grammar to be constructed.
     * @throws IOException      When a problem is encountered reading the entity
     *          XNIException    When a condition arises (such as a FatalError) that requires parsing
     *                              of the entity be terminated.
     */
    public Grammar loadGrammar(XMLInputSource source)
        throws IOException, XNIException;
} // XMLGrammarLoader
