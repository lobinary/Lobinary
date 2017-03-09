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

package com.sun.org.apache.xerces.internal.impl.dv;

import com.sun.org.apache.xerces.internal.util.SymbolHash;
import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.utils.ObjectFactory;

/**
 * Defines a factory API that enables applications to <p>
 * 1. to get the instance of specified SchemaDVFactory implementation <p>
 * 2. to create/return built-in schema simple types <p>
 * 3. to create user defined simple types. <p>
 *
 * Implementations of this abstract class can be used to get built-in simple
 * types and create user-defined simle types. <p>
 *
 * The implementation should store the built-in datatypes in static data, so
 * that they can be shared by multiple parser instance, and multiple threads.
 *
 * @xerces.internal
 *
 * <p>
 *  定义工厂API,使应用程序能够<p> 1.获取指定的SchemaDVFactory实现的实例<p> 2.创建/返回内置模式简单类型<p> 3.创建用户定义的简单类型。 <p>
 * 
 *  这个抽象类的实现可以用于获取内置的简单类型和创建用户定义的类型。 <p>
 * 
 *  实现应该将内置数据类型存储在静态数据中,以便它们可以被多个解析器实例和多个线程共享。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 * @version $Id: SchemaDVFactory.java,v 1.6 2010-11-01 04:39:43 joehw Exp $
 */
public abstract class SchemaDVFactory {

    private static final String DEFAULT_FACTORY_CLASS = "com.sun.org.apache.xerces.internal.impl.dv.xs.SchemaDVFactoryImpl";

    /**
     * Get a default instance of SchemaDVFactory implementation.
     *
     * <p>
     *  获取SchemaDVFactory实现的默认实例。
     * 
     * 
     * @return  an instance of SchemaDVFactory implementation
     * @exception DVFactoryException  cannot create an instance of the specified
     *                                class name or the default class name
     */
    public static synchronized final SchemaDVFactory getInstance() throws DVFactoryException {
        return getInstance(DEFAULT_FACTORY_CLASS);
    } //getInstance():  SchemaDVFactory


    /**
     * Get an instance of SchemaDVFactory implementation.
     *
     * <p>
     *  获取SchemaDVFactory实现的实例。
     * 
     * 
     * @param factoryClass   name of the schema factory implementation to instantiate.
     * @return  an instance of SchemaDVFactory implementation
     * @exception DVFactoryException  cannot create an instance of the specified
     *                                class name or the default class name
     */
    public static synchronized final SchemaDVFactory getInstance(String factoryClass) throws DVFactoryException {

        try {
            // if the class name is not specified, use the default one
            return (SchemaDVFactory)(ObjectFactory.newInstance(factoryClass, true));
        } catch (ClassCastException e4) {
            throw new DVFactoryException("Schema factory class " + factoryClass + " does not extend from SchemaDVFactory.");
        }

    }

    // can't create a new object of this class
    protected SchemaDVFactory(){}

    /**
     * Get a built-in simple type of the given name
     * REVISIT: its still not decided within the Schema WG how to define the
     *          ur-types and if all simple types should be derived from a
     *          complex type, so as of now we ignore the fact that anySimpleType
     *          is derived from anyType, and pass 'null' as the base of
     *          anySimpleType. It needs to be changed as per the decision taken.
     *
     * <p>
     * 获取给定名称的内置简单类型REVISIT：它仍然没有在Schema WG中决定如何定义ur-types和如果所有简单类型都应该从复杂类型派生,因此我们现在忽略了这样的事实： anySimpleType派
     * 生自anyType,并传递'null'作为anySimpleType的基础。
     * 它需要根据所做的决定进行更改。
     * 
     * 
     * @param name  the name of the datatype
     * @return      the datatype validator of the given name
     */
    public abstract XSSimpleType getBuiltInType(String name);

    /**
     * get all built-in simple types, which are stored in a SymbolHash keyed by
     * the name
     *
     * <p>
     *  获取所有内置的简单类型,这些类型存储在由名称键入的SymbolHash中
     * 
     * 
     * @return      a SymbolHash which contains all built-in simple types
     */
    public abstract SymbolHash getBuiltInTypes();

    /**
     * Create a new simple type which is derived by restriction from another
     * simple type.
     *
     * <p>
     *  创建一个新的简单类型,通过限制从另一个简单类型派生。
     * 
     * 
     * @param name              name of the new type, could be null
     * @param targetNamespace   target namespace of the new type, could be null
     * @param finalSet          value of "final"
     * @param base              base type of the new type
     * @param annotations       set of annotations
     * @return                  the newly created simple type
     */
    public abstract XSSimpleType createTypeRestriction(String name, String targetNamespace,
                                                       short finalSet, XSSimpleType base,
                                                       XSObjectList annotations);

    /**
     * Create a new simple type which is derived by list from another simple
     * type.
     *
     * <p>
     *  创建一个新的简单类型,由另一个简单类型的列表派生。
     * 
     * 
     * @param name              name of the new type, could be null
     * @param targetNamespace   target namespace of the new type, could be null
     * @param finalSet          value of "final"
     * @param itemType          item type of the list type
     * @param annotations       set of annotations
     * @return                  the newly created simple type
     */
    public abstract XSSimpleType createTypeList(String name, String targetNamespace,
                                                short finalSet, XSSimpleType itemType,
                                                XSObjectList annotations);

    /**
     * Create a new simple type which is derived by union from a list of other
     * simple types.
     *
     * <p>
     *  创建一个新的简单类型,通过union从其他简单类型的列表中派生。
     * 
     * @param name              name of the new type, could be null
     * @param targetNamespace   target namespace of the new type, could be null
     * @param finalSet          value of "final"
     * @param memberTypes       member types of the union type
     * @param annotations       set of annotations
     * @return                  the newly created simple type
     */
    public abstract XSSimpleType createTypeUnion(String name, String targetNamespace,
                                                 short finalSet, XSSimpleType[] memberTypes,
                                                 XSObjectList annotations);

}
