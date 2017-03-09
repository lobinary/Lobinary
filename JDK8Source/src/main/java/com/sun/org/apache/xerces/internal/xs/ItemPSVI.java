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
 *  Represents an abstract PSVI item for an element or an attribute
 * information item.
 * <p>
 *  表示元素或属性信息项的抽象PSVI项。
 * 
 */
public interface ItemPSVI {
    /**
     * Validity value indicating that validation has either not been performed
     * or that a strict assessment of validity could not be performed.
     * <p>
     *  指示未进行验证或无法执行严格的有效性评估的有效性值。
     * 
     */
    public static final short VALIDITY_NOTKNOWN         = 0;
    /**
     *  Validity value indicating that validation has been strictly assessed
     * and the item in question is invalid according to the rules of schema
     * validation.
     * <p>
     *  表示根据模式验证规则严格评估验证并且所讨论的项目无效的有效性值。
     * 
     */
    public static final short VALIDITY_INVALID          = 1;
    /**
     *  Validation status indicating that schema validation has been performed
     * and the item in question is valid according to the rules of schema
     * validation.
     * <p>
     *  根据模式验证规则,指示已执行模式验证并且所讨论的项目有效的验证状态。
     * 
     */
    public static final short VALIDITY_VALID            = 2;
    /**
     *  Validation status indicating that schema validation has been performed
     * and the item in question has specifically been skipped.
     * <p>
     *  指示已经执行模式验证并且具体跳过所讨论的项目的验证状态。
     * 
     */
    public static final short VALIDATION_NONE           = 0;
    /**
     * Validation status indicating that schema validation has been performed
     * on the item in question under the rules of lax validation.
     * <p>
     *  验证状态,指示已根据宽松验证规则对所讨论的项目执行了模式验证。
     * 
     */
    public static final short VALIDATION_PARTIAL        = 1;
    /**
     *  Validation status indicating that full schema validation has been
     * performed on the item.
     * <p>
     * 指示已对项执行完全模式验证的验证状态。
     * 
     */
    public static final short VALIDATION_FULL           = 2;
    /**
     *  The nearest ancestor element information item with a
     * <code>[schema information]</code> property (or this element item
     * itself if it has such a property). For more information refer to
     * element validation context and attribute validation context .
     * <p>
     *  具有<code> [schema信息] </code>属性的最近的祖先元素信息项(如果它有这样的属性,则为此元素项本身)。有关更多信息,请参阅元素验证上下文和属性验证上下文。
     * 
     */
    public String getValidationContext();

    /**
     *  <code>[validity]</code>: determines the validity of the schema item
     * with respect to the validation being attempted. The value will be one
     * of the constants: <code>VALIDITY_NOTKNOWN</code>,
     * <code>VALIDITY_INVALID</code> or <code>VALIDITY_VALID</code>.
     * <p>
     *  <code> [validity] </code>：确定相对于尝试的验证的模式项的有效性。
     * 该值将是下列常量之一：<code> VALIDITY_NOTKNOWN </code>,<code> VALIDITY_INVALID </code>或<code> VALIDITY_VALID </code>
     * 。
     *  <code> [validity] </code>：确定相对于尝试的验证的模式项的有效性。
     * 
     */
    public short getValidity();

    /**
     *  <code>[validation attempted]</code>: determines the extent to which
     * the schema item has been validated. The value will be one of the
     * constants: <code>VALIDATION_NONE</code>,
     * <code>VALIDATION_PARTIAL</code> or <code>VALIDATION_FULL</code>.
     * <p>
     *  <code> [validation attempts] </code>：确定模式项目已验证的范围。
     * 该值将是以下常量之一：<code> VALIDATION_NONE </code>,<code> VALIDATION_PARTIAL </code>或<code> VALIDATION_FULL </code>
     * 。
     *  <code> [validation attempts] </code>：确定模式项目已验证的范围。
     * 
     */
    public short getValidationAttempted();

    /**
     *  <code>[schema error code]</code>: a list of error codes generated from
     * the validation attempt or an empty <code>StringList</code> if no
     * errors occurred during the validation attempt.
     * <p>
     *  <code> [schema error code] </code>：如果在验证尝试期间没有发生错误,则从验证尝试生成的错误代码列表或空的<code> StringList </code>。
     * 
     */
    public StringList getErrorCodes();

    /**
     * <code>[schema normalized value]</code>: the normalized value of this
     * item after validation.
     * <p>
     *  <code> [schema normalized value] </code>：验证后该项的规范化值。
     * 
     */
    public String getSchemaNormalizedValue();

    /**
     * <code>[schema normalized value]</code>: Binding specific actual value
     * or <code>null</code> if the value is in error.
     * <p>
     *  <code> [schema normalized value] </code>：如果值错误,则绑定特定实际值或<code> null </code>。
     * 
     * 
     * @exception XSException
     *   NOT_SUPPORTED_ERR: Raised if the implementation does not support this
     *   method.
     */
    public Object getActualNormalizedValue()
                                   throws XSException;

    /**
     * The actual value built-in datatype, e.g.
     * <code>STRING_DT, SHORT_DT</code>. If the type definition of this
     * value is a list type definition, this method returns
     * <code>LIST_DT</code>. If the type definition of this value is a list
     * type definition whose item type is a union type definition, this
     * method returns <code>LISTOFUNION_DT</code>. To query the actual value
     * of the list or list of union type definitions use
     * <code>itemValueTypes</code>. If the <code>actualNormalizedValue</code>
     *  is <code>null</code>, this method returns <code>UNAVAILABLE_DT</code>
     * .
     * <p>
     * 实际值内置数据类型,例如。 <code> STRING_DT,SHORT_DT </code>。如果此值的类型定义是列表类型定义,则此方法返回<code> LIST_DT </code>。
     * 如果此值的类型定义是项类型为联合类型定义的列表类型定义,则此方法返回<code> LISTOFUNION_DT </code>。
     * 要查询列表或联合类型定义列表的实际值,请使用<code> itemValueTypes </code>。
     * 如果<code> actualNormalizedValue </code>是<code> null </code>,则此方法返回<code> UNAVAILABLE_DT </code>。
     * 
     * 
     * @exception XSException
     *   NOT_SUPPORTED_ERR: Raised if the implementation does not support this
     *   method.
     */
    public short getActualNormalizedValueType()
                                   throws XSException;

    /**
     * In the case the actual value represents a list, i.e. the
     * <code>actualNormalizedValueType</code> is <code>LIST_DT</code>, the
     * returned array consists of one type kind which represents the itemType
     * . For example:
     * <pre> &lt;simpleType name="listtype"&gt; &lt;list
     * itemType="positiveInteger"/&gt; &lt;/simpleType&gt; &lt;element
     * name="list" type="listtype"/&gt; ... &lt;list&gt;1 2 3&lt;/list&gt; </pre>
     *
     * The <code>schemaNormalizedValue</code> value is "1 2 3", the
     * <code>actualNormalizedValueType</code> value is <code>LIST_DT</code>,
     * and the <code>itemValueTypes</code> is an array of size 1 with the
     * value <code>POSITIVEINTEGER_DT</code>.
     * <br> If the actual value represents a list type definition whose item
     * type is a union type definition, i.e. <code>LISTOFUNION_DT</code>,
     * for each actual value in the list the array contains the
     * corresponding memberType kind. For example:
     * <pre> &lt;simpleType
     * name='union_type' memberTypes="integer string"/&gt; &lt;simpleType
     * name='listOfUnion'&gt; &lt;list itemType='union_type'/&gt;
     * &lt;/simpleType&gt; &lt;element name="list" type="listOfUnion"/&gt;
     * ... &lt;list&gt;1 2 foo&lt;/list&gt; </pre>
     *  The
     * <code>schemaNormalizedValue</code> value is "1 2 foo", the
     * <code>actualNormalizedValueType</code> is <code>LISTOFUNION_DT</code>
     * , and the <code>itemValueTypes</code> is an array of size 3 with the
     * following values: <code>INTEGER_DT, INTEGER_DT, STRING_DT</code>.
     * <p>
     *  在实际值表示列表,即<code> actualNormalizedValueType </code>是<code> LIST_DT </code>的情况下,返回的数组由表示itemType的一种类型类
     * 型组成。
     * 例如：<pre>&lt; simpleType name ="listtype"&gt; &lt; list itemType ="positiveInteger"/&gt; &lt; / simple
     * Type&gt; &lt; element name ="list"type ="listtype"/&gt; ...&lt; list&gt; 1 2 3&lt; / list&gt; </pre>。
     * 
     * <code> schemaNormalizedValue </code>值为"1 2 3",<code> actualNormalizedValueType </code>值为<code> LIST_D
     * T </code>,<code> itemValueTypes </code>大小为1,值为<code> POSITIVEINTEGER_DT </code>。
     *  <br>如果实际值代表其类型为联合类型定义的列表类型定义,即<code> LISTOFUNION_DT </code>,则对于列表中的每个实际值,该数组包含相应的memberType类型。
     * 例如：<pre>&lt; simpleType name ='union_type'memberTypes ="integer string"/&gt; &lt; simpleType name ='l
     * istOfUnion'&gt; &lt; list itemType ='union_type'/&gt; &lt; / simpleType&gt; &lt; element name ="list"
     * type ="listOfUnion"/&gt; ...&lt; list&gt; 1 2 foo&lt; / list&gt; <code> schemaNormalizedValue </code>
     * 值为"1 2 foo",<code> actualNormalizedValueType </code>为<code> LISTOFUNION_DT </code>,<code> itemValueTy
     * pes </code>是大小为3的数组,具有以下值：<code> INTEGER_DT,INTEGER_DT,STRING_DT </code>。
     *  <br>如果实际值代表其类型为联合类型定义的列表类型定义,即<code> LISTOFUNION_DT </code>,则对于列表中的每个实际值,该数组包含相应的memberType类型。
     * 
     * @exception XSException
     *   NOT_SUPPORTED_ERR: Raised if the implementation does not support this
     *   method.
     */
    public ShortList getItemValueTypes()
                                   throws XSException;

    /**
     *  <code>[type definition]</code>: an item isomorphic to the type
     * definition used to validate the schema item.
     * <p>
     * 
     */
    public XSTypeDefinition getTypeDefinition();

    /**
     * <code>[member type definition]</code>: if and only if that type
     * definition is a simple type definition with {variety} union, or a
     * complex type definition whose {content type} is a simple type
     * definition with {variety} union, then an item isomorphic to that
     * member of the union's {member type definitions} which actually
     * validated the schema item's normalized value.
     * <p>
     *  <code> [type definition] </code>：与用于验证模式项的类型定义同构的项。
     * 
     */
    public XSSimpleTypeDefinition getMemberTypeDefinition();

    /**
     * <code>[schema default]</code>: the canonical lexical representation of
     * the declaration's {value constraint} value. For more information
     * refer to element schema default and attribute schema default.
     * <p>
     *  <code> [成员类型定义] </code>：当且仅当该类型定义是一个具有{variety}联合的简单类型定义或一个复杂类型定义,其{content type} union,那么项目同构于该联合体的
     * {member type definitions}的成员,该成员实际验证了该模式项目的归一化值。
     * 
     */
    public String getSchemaDefault();

    /**
     * <code>[schema specified]</code>: if true, the value was specified in
     * the schema. If false, the value comes from the infoset. For more
     * information refer to element specified and attribute specified.
     * <p>
     * <code> [schema default] </code>：声明的{value constraint}值的规范词法表示。有关更多信息,请参阅元素模式缺省值和属性模式缺省值。
     * 
     */
    public boolean getIsSchemaSpecified();

}
