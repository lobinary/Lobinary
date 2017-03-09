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
 * The interface represents the namespace schema information information item.
 * Each namespace schema information information item corresponds to an XML
 * Schema with a unique namespace name.
 * <p>
 *  该接口表示命名空间模式信息信息项。每个命名空间模式信息信息项对应于具有唯一命名空间名称的XML模式。
 * 
 */
public interface XSNamespaceItem {
    /**
     * [schema namespace]: A namespace name or <code>null</code> if absent.
     * <p>
     *  [schema namespace]：如果不存在,则为命名空间名称或<code> null </code>。
     * 
     */
    public String getSchemaNamespace();

    /**
     * [schema components]: a list of top-level components, i.e. element
     * declarations, attribute declarations, etc.
     * <p>
     *  [模式组件]：顶级组件的列表,即元素声明,属性声明等。
     * 
     * 
     * @param objectType The type of the declaration, i.e.
     *   <code>ELEMENT_DECLARATION</code>. Note that
     *   <code>XSTypeDefinition.SIMPLE_TYPE</code> and
     *   <code>XSTypeDefinition.COMPLEX_TYPE</code> can also be used as the
     *   <code>objectType</code> to retrieve only complex types or simple
     *   types, instead of all types.
     * @return  A list of top-level definition of the specified type in
     *   <code>objectType</code> or an empty <code>XSNamedMap</code> if no
     *   such definitions exist.
     */
    public XSNamedMap getComponents(short objectType);

    /**
     *  [annotations]: a set of annotations if it exists, otherwise an empty
     * <code>XSObjectList</code>.
     * <p>
     *  [annotations]：一组注释(如果存在),否则为一个空的<code> XSObjectList </code>。
     * 
     */
    public XSObjectList getAnnotations();

    /**
     * Convenience method. Returns a top-level element declaration.
     * <p>
     *  方便的方法。返回顶级元素声明。
     * 
     * 
     * @param name The name of the declaration.
     * @return A top-level element declaration or <code>null</code> if such a
     *   declaration does not exist.
     */
    public XSElementDeclaration getElementDeclaration(String name);

    /**
     * Convenience method. Returns a top-level attribute declaration.
     * <p>
     *  方便的方法。返回顶级属性声明。
     * 
     * 
     * @param name The name of the declaration.
     * @return A top-level attribute declaration or <code>null</code> if such
     *   a declaration does not exist.
     */
    public XSAttributeDeclaration getAttributeDeclaration(String name);

    /**
     * Convenience method. Returns a top-level simple or complex type
     * definition.
     * <p>
     *  方便的方法。返回顶级简单或复杂类型定义。
     * 
     * 
     * @param name The name of the definition.
     * @return An <code>XSTypeDefinition</code> or <code>null</code> if such
     *   a definition does not exist.
     */
    public XSTypeDefinition getTypeDefinition(String name);

    /**
     * Convenience method. Returns a top-level attribute group definition.
     * <p>
     *  方便的方法。返回顶级属性组定义。
     * 
     * 
     * @param name The name of the definition.
     * @return A top-level attribute group definition or <code>null</code> if
     *   such a definition does not exist.
     */
    public XSAttributeGroupDefinition getAttributeGroup(String name);

    /**
     * Convenience method. Returns a top-level model group definition.
     * <p>
     * 方便的方法。返回顶级模型组定义。
     * 
     * 
     * @param name The name of the definition.
     * @return A top-level model group definition definition or
     *   <code>null</code> if such a definition does not exist.
     */
    public XSModelGroupDefinition getModelGroupDefinition(String name);

    /**
     * Convenience method. Returns a top-level notation declaration.
     * <p>
     *  方便的方法。返回顶级符号声明。
     * 
     * 
     * @param name The name of the declaration.
     * @return A top-level notation declaration or <code>null</code> if such
     *   a declaration does not exist.
     */
    public XSNotationDeclaration getNotationDeclaration(String name);

    /**
     * [document location] - a list of location URIs for the documents that
     * contributed to the <code>XSModel</code>.
     * <p>
     *  [文档位置]  - 贡献给<code> XSModel </code>的文档的位置URI列表。
     */
    public StringList getDocumentLocations();

}
