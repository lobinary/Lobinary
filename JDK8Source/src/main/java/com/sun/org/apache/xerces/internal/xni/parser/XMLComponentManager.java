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

import com.sun.org.apache.xerces.internal.util.FeatureState;
import com.sun.org.apache.xerces.internal.util.PropertyState;

/**
 * The component manager manages a parser configuration and the components
 * that make up that configuration. The manager notifies each component
 * before parsing to allow the components to initialize their state; and
 * also any time that a parser feature or property changes.
 * <p>
 * The methods of the component manager allow components to query features
 * and properties that affect the operation of the component.
 *
 * <p>
 *  组件管理器管理解析器配置和组成该配置的组件。管理器在解析之前通知每个组件以允许组件初始化其状态;以及解析器功能或属性更改的任何时间。
 * <p>
 *  组件管理器的方法允许组件查询影响组件操作的特征和属性。
 * 
 * 
 * @see XMLComponent
 *
 * @author Andy Clark, IBM
 *
 * @version $Id: XMLComponentManager.java,v 1.6 2010-11-01 04:40:22 joehw Exp $
 */
public interface XMLComponentManager {

    //
    // XMLComponentManager methods
    //

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
     * Returns the state of a feature.
     * Does not throw exceptions.
     *
     * <p>
     *  返回要素的状态。不抛出异常。
     * 
     * 
     * @param featureId The feature identifier.
     * @param defaultValue Default value if future is not available.
     */
    public boolean getFeature(String featureId, boolean defaultValue);

    /**
     * Returns the value of a property.
     *
     * <p>
     *  返回属性的值。
     * 
     * 
     * @param propertyId The property identifier.
     *
    * @throws XMLConfigurationException Thrown on configuration error.
     */
    public Object getProperty(String propertyId)
        throws XMLConfigurationException;

    /**
     * Returns the value of a property.
     * Does not throw exceptions.
     *
     * <p>
     *  返回属性的值。不抛出异常。
     * 
     * @param propertyId The property identifier.
     * @param defaultObject Return value if property is not available.
     *
     */
    public Object getProperty(String propertyId, Object defaultObject);

    public FeatureState getFeatureState(String featureId);

    public PropertyState getPropertyState(String propertyId);

} // interface XMLComponentManager
