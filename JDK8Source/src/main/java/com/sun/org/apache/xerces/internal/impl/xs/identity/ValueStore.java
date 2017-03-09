/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004,2005 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004,2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs.identity;

import com.sun.org.apache.xerces.internal.xs.ShortList;


/**
 * Interface for storing values associated to an identity constraint.
 * Each value stored corresponds to a field declared for the identity
 * constraint. One instance of an object implementing this interface
 * is created for each identity constraint per element declaration in
 * the instance document to store the information for this identity
 * constraint.
 * <p>
 * <strong>Note:</strong> The component performing identity constraint
 * collection and validation is responsible for providing an
 * implementation of this interface. The component is also responsible
 * for performing the necessary checks required by each type of identity
 * constraint.
 *
 * @xerces.internal
 *
 * <p>
 *  用于存储与身份约束相关联的值的接口。每个存储的值对应于为身份约束声明的字段。针对实例文档中每个元素声明的每个标识约束创建实现此接口的对象的一个​​实例,以存储此身份约束的信息。
 * <p>
 *  <strong>注意</strong>：执行身份约束收集和验证的组件负责提供此接口的实现。该组件还负责执行每种类型的身份约束所需的必要检查。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Andy Clark, IBM
 *
 */
public interface ValueStore {

    //
    // ValueStore methods
    //

    /**
     * Adds the specified value to the value store.
     *
     * <p>
     * 
     * @param field The field associated to the value. This reference
     *              is used to ensure that each field only adds a value
     *              once within a selection scope.
     * @param actualValue The value to add.
     */
    public void addValue(Field field, Object actualValue, short valueType, ShortList itemValueType);

    /**
     * Since the valueStore will have access to an error reporter, this
     * allows it to be called appropriately.
     * <p>
     *  将指定的值添加到值存储。
     * 
     * 
     * @param key  the key of the localized error message
     * @param args  the list of arguments for substitution.
     */
    public void reportError(String key, Object[] args);


} // interface ValueStore
