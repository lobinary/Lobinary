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
 * The interface represents the Attribute Declaration schema component.
 * <p>
 *  接口表示属性声明模式组件。
 * 
 */
public interface XSAttributeDeclaration extends XSObject {
    /**
     * [type definition]: A simple type definition.
     * <p>
     *  [类型定义]：一个简单的类型定义。
     * 
     */
    public XSSimpleTypeDefinition getTypeDefinition();

    /**
     * [scope]. One of <code>SCOPE_GLOBAL</code>, <code>SCOPE_LOCAL</code>, or
     * <code>SCOPE_ABSENT</code>. If the scope is local, then the
     * <code>enclosingCTDefinition</code> is present.
     * <p>
     *  [范围]。 <code> SCOPE_GLOBAL </code>,<code> SCOPE_LOCAL </code>或<code> SCOPE_ABSENT </code>之一。
     * 如果范围是本地的,那么存在<code> enclosingCTDefinition </code>。
     * 
     */
    public short getScope();

    /**
     * The complex type definition for locally scoped declarations (see
     * <code>scope</code>), otherwise <code>null</code> if no such
     * definition exists.
     * <p>
     *  局部范围声明的复杂类型定义(参见<code> scope </code>),否则<code> null </code>
     * 
     */
    public XSComplexTypeDefinition getEnclosingCTDefinition();

    /**
     * Value constraint: one of <code>VC_NONE, VC_DEFAULT, VC_FIXED</code>.
     * <p>
     *  值约束：<code> VC_NONE,VC_DEFAULT,VC_FIXED </code>之一。
     * 
     */
    public short getConstraintType();

    /**
     * Value constraint: The constraint value with respect to the [type
     * definition], otherwise <code>null</code>.
     * <p>
     *  值约束：相对于[类型定义]的约束值,否则<code> null </code>。
     * 
     */
    public String getConstraintValue();

    /**
     * Value Constraint: Binding specific actual constraint value or
     * <code>null</code> if the value is in error or there is no value
     * constraint.
     * <p>
     *  值约束：如果值错误或没有值约束,则绑定特定实际约束值或<code> null </code>。
     * 
     * 
     * @exception XSException
     *   NOT_SUPPORTED_ERR: Raised if the implementation does not support this
     *   method.
     */
    public Object getActualVC()
                                                        throws XSException;

    /**
     * The actual constraint value built-in datatype, e.g.
     * <code>STRING_DT, SHORT_DT</code>. If the type definition of this
     * value is a list type definition, this method returns
     * <code>LIST_DT</code>. If the type definition of this value is a list
     * type definition whose item type is a union type definition, this
     * method returns <code>LISTOFUNION_DT</code>. To query the actual
     * constraint value of the list or list of union type definitions use
     * <code>itemValueTypes</code>. If the <code>actualValue</code> is
     * <code>null</code>, this method returns <code>UNAVAILABLE_DT</code>.
     * <p>
     * 实际约束值内置数据类型,例如<code> STRING_DT,SHORT_DT </code>。如果此值的类型定义是列表类型定义,则此方法返回<code> LIST_DT </code>。
     * 如果此值的类型定义是项类型为联合类型定义的列表类型定义,则此方法返回<code> LISTOFUNION_DT </code>。
     * 要查询列表或联合类型定义列表的实际约束值,请使用<code> itemValueTypes </code>。
     * 如果<code> actualValue </code>是<code> null </code>,则此方法返回<code> UNAVAILABLE_DT </code>。
     * 
     * 
     * @exception XSException
     *   NOT_SUPPORTED_ERR: Raised if the implementation does not support this
     *   method.
     */
    public short getActualVCType()
                                                        throws XSException;

    /**
     * In the case the actual constraint value represents a list, i.e. the
     * <code>actualValueType</code> is <code>LIST_DT</code>, the returned
     * array consists of one type kind which represents the itemType. If the
     * actual constraint value represents a list type definition whose item
     * type is a union type definition, i.e. <code>LISTOFUNION_DT</code>,
     * for each actual constraint value in the list the array contains the
     * corresponding memberType kind. For examples, see
     * <code>ItemPSVI.itemValueTypes</code>.
     * <p>
     *  在实际约束值表示列表,即<code> actualValueType </code>是<code> LIST_DT </code>的情况下,返回的数组由表示itemType的一种类型类型组成。
     * 如果实际约束值表示其类型为联合类型定义的列表类型定义,即<code> LISTOFUNION_DT </code>,则对于列表中的每个实际约束值,数组包含相应的memberType类型。
     * 有关示例,请参阅<code> ItemPSVI.itemValueTypes </code>。
     * 
     * 
     * @exception XSException
     *   NOT_SUPPORTED_ERR: Raised if the implementation does not support this
     *   method.
     */
    public ShortList getItemValueTypes()
                                                        throws XSException;

    /**
     * An annotation if it exists, otherwise <code>null</code>.
     * If not null then the first [annotation] from the sequence of annotations.
     * <p>
     *  注释(如果存在),否则<code> null </code>。如果不是null,那么第一个[注释]来自注释序列。
     * 
     */
    public XSAnnotation getAnnotation();

    /**
     * A sequence of [annotations] or an empty  <code>XSObjectList</code>.
     * <p>
     *  一个[注释]序列或一个空的<code> XSObjectList </code>。
     */
    public XSObjectList getAnnotations();
}
