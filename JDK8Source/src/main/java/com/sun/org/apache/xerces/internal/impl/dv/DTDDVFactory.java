/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.dv;

import java.util.Hashtable;
import com.sun.org.apache.xerces.internal.utils.ObjectFactory;

/**
 * The factory to create and return DTD types. The implementation should
 * store the created datatypes in static data, so that they can be shared by
 * multiple parser instance, and multiple threads.
 *
 * @xerces.internal
 *
 * <p>
 *  工厂创建和返回DTD类型。实现应该将创建的数据类型存储在静态数据中,以便它们可以被多个解析器实例和多个线程共享。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 * @version $Id: DTDDVFactory.java,v 1.6 2010-11-01 04:39:43 joehw Exp $
 */
public abstract class DTDDVFactory {

    private static final String DEFAULT_FACTORY_CLASS = "com.sun.org.apache.xerces.internal.impl.dv.dtd.DTDDVFactoryImpl";

    /**
     * Get an instance of the default DTDDVFactory implementation.
     *
     * <p>
     *  获取默认DTDDVFactory实现的实例。
     * 
     * 
     * @return  an instance of DTDDVFactory implementation
     * @exception DVFactoryException  cannot create an instance of the specified
     *                                class name or the default class name
     */
    public static final DTDDVFactory getInstance() throws DVFactoryException {
        return getInstance(DEFAULT_FACTORY_CLASS);
    }

    /**
     * Get an instance of DTDDVFactory implementation.
     *
     * <p>
     *  获取DTDDVFactory实现的实例。
     * 
     * 
     * @param factoryClass  name of the implementation to load.
     * @return  an instance of DTDDVFactory implementation
     * @exception DVFactoryException  cannot create an instance of the specified
     *                                class name or the default class name
     */
    public static final DTDDVFactory getInstance(String factoryClass) throws DVFactoryException {
        try {
            // if the class name is not specified, use the default one
            return (DTDDVFactory)
                (ObjectFactory.newInstance(factoryClass, true));
        }
        catch (ClassCastException e) {
            throw new DVFactoryException("DTD factory class " + factoryClass + " does not extend from DTDDVFactory.");
        }
    }

    // can't create a new object of this class
    protected DTDDVFactory() {}

    /**
     * return a dtd type of the given name
     *
     * <p>
     *  返回给定名称的dtd类型
     * 
     * 
     * @param name  the name of the datatype
     * @return      the datatype validator of the given name
     */
    public abstract DatatypeValidator getBuiltInDV(String name);

    /**
     * get all built-in DVs, which are stored in a hashtable keyed by the name
     *
     * <p>
     *  得到所有内置的DV,它们存储在以名称键入的散列表中
     * 
     * @return      a hashtable which contains all datatypes
     */
    public abstract Hashtable getBuiltInTypes();

}
