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
 * This interface represents the Attribute Use schema component.
 * <p>
 *  此接口表示属性使用模式组件。
 * 
 */
public interface XSAttributeUse extends XSObject {
    /**
     * [required]: determines whether this use of an attribute declaration
     * requires an appropriate attribute information item to be present, or
     * merely allows it.
     * <p>
     *  [必需]：确定属性声明的这种使用是否需要存在适当的属性信息项,或者仅仅允许它存在。
     * 
     */
    public boolean getRequired();

    /**
     * [attribute declaration]: provides the attribute declaration itself,
     * which will in turn determine the simple type definition used.
     * <p>
     *  [属性声明]：提供属性声明本身,这将反过来确定使用的简单类型定义。
     * 
     */
    public XSAttributeDeclaration getAttrDeclaration();

    /**
     * Value Constraint: one of default, fixed.
     * <p>
     *  值约束：默认值之一,固定。
     * 
     */
    public short getConstraintType();

    /**
     * Value Constraint: The constraint value, otherwise <code>null</code>.
     * <p>
     *  值约束：约束值,否则<code> null </code>。
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
     * <code>itemValueTypes</code>. If the <code>actualNormalizedValue</code>
     *  is <code>null</code>, this method returns <code>UNAVAILABLE_DT</code>
     * .
     * <p>
     * 实际约束值内置数据类型,例如<code> STRING_DT,SHORT_DT </code>。如果此值的类型定义是列表类型定义,则此方法返回<code> LIST_DT </code>。
     * 如果此值的类型定义是项类型为联合类型定义的列表类型定义,则此方法返回<code> LISTOFUNION_DT </code>。
     * 要查询列表或联合类型定义列表的实际约束值,请使用<code> itemValueTypes </code>。
     * 如果<code> actualNormalizedValue </code>是<code> null </code>,则此方法返回<code> UNAVAILABLE_DT </code>。
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
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     * <p>
     */
    public XSObjectList getAnnotations();
}
