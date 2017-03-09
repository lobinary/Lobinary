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
 * The interface represents the Element Declaration schema component.
 * <p>
 *  接口表示元素声明模式组件。
 * 
 */
public interface XSElementDeclaration extends XSTerm {
    /**
     * [type definition]: either a simple type definition or a complex type
     * definition.
     * <p>
     *  [类型定义]：简单类型定义或复杂类型定义。
     * 
     */
    public XSTypeDefinition getTypeDefinition();

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
     * [Value constraint]: one of <code>VC_NONE, VC_DEFAULT, VC_FIXED</code>.
     * <p>
     *  [值约束]：<code> VC_NONE,VC_DEFAULT,VC_FIXED </code>中的一个。
     * 
     */
    public short getConstraintType();

    /**
     * [Value constraint]: the constraint value with respect to the [type
     * definition], otherwise <code>null</code>.
     * <p>
     *  [值约束]：相对于[类型定义]的约束值,否则<code> null </code>。
     * 
     */
    public String getConstraintValue();

    /**
     * Value Constraint: Binding specific actual constraint value or
     * <code>null</code> if the value is in error or there is no value
     * constraint.
     * <p>
     * 值约束：如果值错误或没有值约束,则绑定特定实际约束值或<code> null </code>。
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
     *  实际约束值内置数据类型,例如<code> STRING_DT,SHORT_DT </code>。如果此值的类型定义是列表类型定义,则此方法返回<code> LIST_DT </code>。
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
     *  If nillable is true, then an element may also be valid if it carries
     * the namespace qualified attribute with local name <code>nil</code>
     * from namespace <code>http://www.w3.org/2001/XMLSchema-instance</code>
     * and value <code>true</code> (xsi:nil) even if it has no text or
     * element content despite a <code>content type</code> which would
     * otherwise require content.
     * <p>
     * 如果nillable为true,那么如果元素携带来自命名空间<code>的本地名为<code> nil </code>的命名空间限定属性,则该元素也可能有效http://www.w3.org/2001/
     * XMLSchema-instance </code>和值<code> true </code>(xsi：nil),即使它没有文本或元素内容,尽管<code>内容类型</code>。
     * 
     */
    public boolean getNillable();

    /**
     * identity-constraint definitions: a set of constraint definitions if it
     * exists, otherwise an empty <code>XSNamedMap</code>.
     * <p>
     *  identity-constraint定义：一组约束定义(如果存在),否则为空<code> XSNamedMap </code>。
     * 
     */
    public XSNamedMap getIdentityConstraints();

    /**
     * [substitution group affiliation]: a top-level element definition if it
     * exists, otherwise <code>null</code>.
     * <p>
     *  [替换组附属]：顶级元素定义(如果存在),否则<code> null </code>。
     * 
     */
    public XSElementDeclaration getSubstitutionGroupAffiliation();

    /**
     * Convenience method that checks if <code>exclusion</code> is a
     * substitution group exclusion for this element declaration.
     * <p>
     *  检查<code>排除</code>是否为此元素声明的替换组排除的便捷方法。
     * 
     * 
     * @param exclusion
     *   <code>DERIVATION_EXTENSION, DERIVATION_RESTRICTION</code> or
     *   <code>DERIVATION_NONE</code>. Represents final set for the element.
     * @return True if <code>exclusion</code> is a part of the substitution
     *   group exclusion subset.
     */
    public boolean isSubstitutionGroupExclusion(short exclusion);

    /**
     *  [substitution group exclusions]: the returned value is a bit
     * combination of the subset of {
     * <code>DERIVATION_EXTENSION, DERIVATION_RESTRICTION</code>} or
     * <code>DERIVATION_NONE</code>.
     * <p>
     *  [替换组排除]：返回值是{<code> DERIVATION_EXTENSION,DERIVATION_RESTRICTION </code>}或<code> DERIVATION_NONE </code>
     * 子集的位组合。
     * 
     */
    public short getSubstitutionGroupExclusions();

    /**
     * Convenience method that checks if <code>disallowed</code> is a
     * disallowed substitution for this element declaration.
     * <p>
     *  检查<code>不允许</code>是否为此元素声明的不允许替换的便利方法。
     * 
     * 
     * @param disallowed {
     *   <code>DERIVATION_SUBSTITUTION, DERIVATION_EXTENSION, DERIVATION_RESTRICTION</code>
     *   } or <code>DERIVATION_NONE</code>. Represents a block set for the
     *   element.
     * @return True if <code>disallowed</code> is a part of the substitution
     *   group exclusion subset.
     */
    public boolean isDisallowedSubstitution(short disallowed);

    /**
     *  [disallowed substitutions]: the returned value is a bit combination of
     * the subset of {
     * <code>DERIVATION_SUBSTITUTION, DERIVATION_EXTENSION, DERIVATION_RESTRICTION</code>
     * } corresponding to substitutions disallowed by this
     * <code>XSElementDeclaration</code> or <code>DERIVATION_NONE</code>.
     * <p>
     *  [不允许的替换]：返回的值是与<code> XSElementDeclaration </code>或<code> DERIVATION_NONE </code>不允许的替换对应的{<code> DERIVATION_SUBSTITUTION,DERIVATION_EXTENSION,DERIVATION_RESTRICTION </code> / code>。
     * 
     */
    public short getDisallowedSubstitutions();

    /**
     * {abstract} A boolean.
     * <p>
     *  {abstract}布尔值。
     * 
     */
    public boolean getAbstract();

    /**
     * An annotation if it exists, otherwise <code>null</code>. If not null
     * then the first [annotation] from the sequence of annotations.
     * <p>
     *  注释(如果存在),否则<code> null </code>。如果不是null,那么第一个[注释]来自注释序列。
     * 
     */
    public XSAnnotation getAnnotation();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     * <p>
     * 一个[注释]序列或一个空的<code> XSObjectList </code>。
     */
    public XSObjectList getAnnotations();
}
