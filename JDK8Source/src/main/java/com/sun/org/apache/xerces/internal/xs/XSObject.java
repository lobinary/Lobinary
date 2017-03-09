/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003,2004 The Apache Software Foundation.
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
 *  版权所有2003,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xs;

/**
 * The <code>XSObject</code> is a base object for the XML Schema component
 * model.
 * <p>
 *  <code> XSObject </code>是XML模式组件模型的基础对象。
 * 
 */
public interface XSObject {
    /**
     *  The <code>type</code> of this object, i.e.
     * <code>ELEMENT_DECLARATION</code>.
     * <p>
     *  此对象的<code> type </code>,即<code> ELEMENT_DECLARATION </code>。
     * 
     */
    public short getType();

    /**
     * The name of type <code>NCName</code>, as defined in XML Namespaces, of
     * this declaration specified in the <code>{name}</code> property of the
     * component or <code>null</code> if the definition of this component
     * does not have a <code>{name}</code> property. For anonymous types,
     * the processor must construct and expose an anonymous type name that
     * is distinct from the name of every named type and the name of every
     * other anonymous type.
     * <p>
     *  在组件的<code> {name} </code>属性中指定的此声明的XML名称空间中定义的类型<code> NCName </code>的名称,或<code> null </code>这个组件的定义
     * 没有<code> {name} </code>属性。
     * 对于匿名类型,处理器必须构造和公开与每个命名类型的名称和每个其他匿名类型的名称不同的匿名类型名称。
     * 
     */
    public String getName();

    /**
     *  The [target namespace] of this object, or <code>null</code> if it is
     * unspecified.
     * <p>
     *  此对象的[目标命名空间],如果未指定,则为<code> null </code>。
     * 
     */
    public String getNamespace();

    /**
     * A namespace schema information item corresponding to the target
     * namespace of the component, if it is globally declared; or
     * <code>null</code> otherwise.
     * <p>
     * 如果是全局声明的,则组件的目标命名空间对应的命名空间模式信息项;或<code> null </code>。
     */
    public XSNamespaceItem getNamespaceItem();

}
