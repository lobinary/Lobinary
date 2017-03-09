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
 * This interface represents an XML Schema.
 * <p>
 *  此接口表示XML模式。
 * 
 */
public interface XSModel {
    /**
     * Convenience method. Returns a list of all namespaces that belong to
     * this schema. The value <code>null</code> is not a valid namespace
     * name, but if there are components that do not have a target namespace
     * , <code>null</code> is included in this list.
     * <p>
     *  方便的方法。返回属于此模式的所有命名空间的列表。
     * 值<code> null </code>不是有效的命名空间名称,但如果有没有目标命名空间的组件,则<list> null </code>将包含在此列表中。
     * 
     */
    public StringList getNamespaces();

    /**
     * A set of namespace schema information information items (of type
     * <code>XSNamespaceItem</code>), one for each namespace name which
     * appears as the target namespace of any schema component in the schema
     * used for that assessment, and one for absent if any schema component
     * in the schema had no target namespace. For more information see
     * schema information.
     * <p>
     *  一组命名空间模式信息信息项(类型为<code> XSNamespaceItem </code>),每个命名空间名称一个,显示为用于评估的模式中任何模式组件的目标命名空间,一个用于缺席模式中的模式组件没
     * 有目标命名空间。
     * 有关详细信息,请参阅模式信息。
     * 
     */
    public XSNamespaceItemList getNamespaceItems();

    /**
     * Returns a list of top-level components, i.e. element declarations,
     * attribute declarations, etc.
     * <p>
     *  返回顶级组件的列表,即元素声明,属性声明等。
     * 
     * 
     * @param objectType The type of the declaration, i.e.
     *   <code>ELEMENT_DECLARATION</code>. Note that
     *   <code>XSTypeDefinition.SIMPLE_TYPE</code> and
     *   <code>XSTypeDefinition.COMPLEX_TYPE</code> can also be used as the
     *   <code>objectType</code> to retrieve only complex types or simple
     *   types, instead of all types.
     * @return  A list of top-level definitions of the specified type in
     *   <code>objectType</code> or an empty <code>XSNamedMap</code> if no
     *   such definitions exist.
     */
    public XSNamedMap getComponents(short objectType);

    /**
     * Convenience method. Returns a list of top-level component declarations
     * that are defined within the specified namespace, i.e. element
     * declarations, attribute declarations, etc.
     * <p>
     * 方便的方法。返回在指定命名空间中定义的顶级组件声明的列表,即元素声明,属性声明等。
     * 
     * 
     * @param objectType The type of the declaration, i.e.
     *   <code>ELEMENT_DECLARATION</code>.
     * @param namespace The namespace to which the declaration belongs or
     *   <code>null</code> (for components with no target namespace).
     * @return  A list of top-level definitions of the specified type in
     *   <code>objectType</code> and defined in the specified
     *   <code>namespace</code> or an empty <code>XSNamedMap</code>.
     */
    public XSNamedMap getComponentsByNamespace(short objectType,
                                               String namespace);

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
     * @param namespace The namespace of the declaration, otherwise
     *   <code>null</code>.
     * @return A top-level element declaration or <code>null</code> if such a
     *   declaration does not exist.
     */
    public XSElementDeclaration getElementDeclaration(String name,
                                                      String namespace);

    /**
     * Convenience method. Returns a top-level attribute declaration.
     * <p>
     *  方便的方法。返回顶级属性声明。
     * 
     * 
     * @param name The name of the declaration.
     * @param namespace The namespace of the declaration, otherwise
     *   <code>null</code>.
     * @return A top-level attribute declaration or <code>null</code> if such
     *   a declaration does not exist.
     */
    public XSAttributeDeclaration getAttributeDeclaration(String name,
                                                          String namespace);

    /**
     * Convenience method. Returns a top-level simple or complex type
     * definition.
     * <p>
     *  方便的方法。返回顶级简单或复杂类型定义。
     * 
     * 
     * @param name The name of the definition.
     * @param namespace The namespace of the declaration, otherwise
     *   <code>null</code>.
     * @return An <code>XSTypeDefinition</code> or <code>null</code> if such
     *   a definition does not exist.
     */
    public XSTypeDefinition getTypeDefinition(String name,
                                              String namespace);

    /**
     * Convenience method. Returns a top-level attribute group definition.
     * <p>
     *  方便的方法。返回顶级属性组定义。
     * 
     * 
     * @param name The name of the definition.
     * @param namespace The namespace of the definition, otherwise
     *   <code>null</code>.
     * @return A top-level attribute group definition or <code>null</code> if
     *   such a definition does not exist.
     */
    public XSAttributeGroupDefinition getAttributeGroup(String name,
                                                        String namespace);

    /**
     * Convenience method. Returns a top-level model group definition.
     * <p>
     *  方便的方法。返回顶级模型组定义。
     * 
     * 
     * @param name The name of the definition.
     * @param namespace The namespace of the definition, otherwise
     *   <code>null</code>.
     * @return A top-level model group definition or <code>null</code> if
     *   such a definition does not exist.
     */
    public XSModelGroupDefinition getModelGroupDefinition(String name,
                                                          String namespace);

    /**
     * Convenience method. Returns a top-level notation declaration.
     * <p>
     *  方便的方法。返回顶级符号声明。
     * 
     * 
     * @param name The name of the declaration.
     * @param namespace The namespace of the declaration, otherwise
     *   <code>null</code>.
     * @return A top-level notation declaration or <code>null</code> if such
     *   a declaration does not exist.
     */
    public XSNotationDeclaration getNotationDeclaration(String name,
                                                        String namespace);

    /**
     * Convenience method. Returns a list containing the members of the
     * substitution group for the given <code>XSElementDeclaration</code>
     * or an empty <code>XSObjectList</code> if the substitution group
     * contains no members.
     * <p>
     *  方便的方法。如果替换组不包含成员,则返回包含给定<code> XSElementDeclaration </code>或空的<code> XSObjectList </code>的替换组成员的列表。
     * 
     * @param head The substitution group head.
     * @return A list containing the members of the substitution group
     *  for the given <code>XSElementDeclaration</code> or an empty
     *  <code>XSObjectList</code> if the substitution group contains
     *  no members.
     */
    public XSObjectList getSubstitutionGroup(XSElementDeclaration head);

}
