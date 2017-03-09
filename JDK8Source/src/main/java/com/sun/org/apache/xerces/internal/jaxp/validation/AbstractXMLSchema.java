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

import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

/**
 * <p>Abstract implementation of Schema for W3C XML Schemas.</p>
 *
 * <p>
 *  <p> W3C XML模式的Schema的抽象实现。</p>
 * 
 * 
 * @author Michael Glavassevich, IBM
 * @version $Id: AbstractXMLSchema.java,v 1.6 2010-11-01 04:40:07 joehw Exp $
 */
abstract class AbstractXMLSchema extends Schema implements
        XSGrammarPoolContainer {

    /**
     * Map containing the initial values of features for
     * validators created using this grammar pool container.
     * <p>
     *  包含使用此语法池容器创建的验证器的要素初始值的映射。
     * 
     */
    private final HashMap fFeatures;

    /**
     * Map containing the initial values of properties for
     * validators created using this grammar pool container.
     * <p>
     *  包含使用此语法池容器创建的验证器的属性的初始值的映射。
     * 
     */
    private final HashMap fProperties;

    public AbstractXMLSchema() {
        fFeatures = new HashMap();
        fProperties = new HashMap();
    }

    /*
     * Schema methods
     * <p>
     *  模式方法
     * 
     */

    /*
    /* <p>
    /* 
     * @see javax.xml.validation.Schema#newValidator()
     */
    public final Validator newValidator() {
        return new ValidatorImpl(this);
    }

    /*
    /* <p>
    /* 
     * @see javax.xml.validation.Schema#newValidatorHandler()
     */
    public final ValidatorHandler newValidatorHandler() {
        return new ValidatorHandlerImpl(this);
    }

    /*
     * XSGrammarPoolContainer methods
     * <p>
     *  XSGrammarPoolContainer方法
     * 
     */

    /**
     * Returns the initial value of a feature for validators created
     * using this grammar pool container or null if the validators
     * should use the default value.
     * <p>
     *  返回使用此语法池容器创建的验证器的要素的初始值,如果验证器应使用默认值,则返回null。
     * 
     */
    public final Boolean getFeature(String featureId) {
        return (Boolean) fFeatures.get(featureId);
    }

    /*
     * Set a feature on the schema
     * <p>
     *  在模式上设置一个功能
     * 
     */
    public final void setFeature(String featureId, boolean state) {
        fFeatures.put(featureId, state ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Returns the initial value of a property for validators created
     * using this grammar pool container or null if the validators
     * should use the default value.
     * <p>
     *  返回使用此语法池容器创建的验证器的属性的初始值,如果验证器应使用默认值,则返回null。
     * 
     */
    public final Object getProperty(String propertyId) {
        return fProperties.get(propertyId);
    }

    /*
     * Set a property on the schema
     * <p>
     *  在模式上设置属性
     */
    public final void setProperty(String propertyId, Object state) {
        fProperties.put(propertyId, state);
    }

} // AbstractXMLSchema
