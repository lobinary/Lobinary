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

package com.sun.org.apache.xerces.internal.xni.parser;

/**
 * The component interface defines methods that must be implemented
 * by components in a parser configuration. The component methods allow
 * the component manager to initialize the component state and notify
 * the component when feature and property values change.
 *
 * <p>
 *  组件接口定义必须由解析器配置中的组件实现的方法。组件方法允许组件管理器初始化组件状态,并在特征和属性值改变时通知组件。
 * 
 * 
 * @see XMLComponentManager
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLComponent {

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
     * @throws XNIException Thrown by component on initialization error.
     */
    public void reset(XMLComponentManager componentManager)
        throws XMLConfigurationException;

    /**
     * Returns a list of feature identifiers that are recognized by
     * this component. This method may return null if no features
     * are recognized by this component.
     * <p>
     *  返回此组件可识别的要素标识符列表。如果此组件未识别任何功能,此方法可能返回null。
     * 
     */
    public String[] getRecognizedFeatures();

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
     * <strong>注意：</strong>组件应默认忽略不影响组件操作的功能。
     * 
     * 
     * @param featureId The feature identifier.
     * @param state     The state of the feature.
     *
     * @throws XMLConfigurationException Thrown for configuration error.
     *                                   In general, components should
     *                                   only throw this exception if
     *                                   it is <strong>really</strong>
     *                                   a critical error.
     */
    public void setFeature(String featureId, boolean state)
        throws XMLConfigurationException;

    /**
     * Returns a list of property identifiers that are recognized by
     * this component. This method may return null if no properties
     * are recognized by this component.
     * <p>
     *  返回此组件可识别的属性标识符列表。如果此组件未识别任何属性,此方法可能返回null。
     * 
     */
    public String[] getRecognizedProperties();

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
     * @throws XMLConfigurationException Thrown for configuration error.
     *                                   In general, components should
     *                                   only throw this exception if
     *                                   it is <strong>really</strong>
     *                                   a critical error.
     */
    public void setProperty(String propertyId, Object value)
       throws XMLConfigurationException;

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
    public Boolean getFeatureDefault(String featureId);

    /**
     * Returns the default state for a property, or null if this
     * component does not want to report a default value for this
     * property.
     *
     * <p>
     *  返回属性的默认状态,如果此组件不希望报告此属性的默认值,则返回null。
     * 
     * @param propertyId The property identifier.
     *
     * @since Xerces 2.2.0
     */
    public Object getPropertyDefault(String propertyId);

} // interface XMLComponent
