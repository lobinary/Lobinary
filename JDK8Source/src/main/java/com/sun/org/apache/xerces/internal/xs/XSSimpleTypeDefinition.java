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
 * This interface represents the Simple Type Definition schema component. This
 * interface provides several query operations for facet components. Users
 * can either retrieve the defined facets as XML Schema components, using
 * the <code>facets</code> and the <code>multiValueFacets</code> attributes;
 * or users can separately query a facet's properties using methods such as
 * <code>getLexicalFacetValue</code>, <code>isFixedFacet</code>, etc.
 * <p>
 *  此接口表示简单类型定义模式组件。此接口为构面组件提供了几个查询操作。
 * 用户可以使用<code> facets </code>和<code> multiValueFacets </code>属性来检索定义的面作为XML模式组件;或用户可以使用<code> getLexica
 * lFacetValue </code>,<code> isFixedFacet </code>等方法单独查询构面的属性。
 *  此接口表示简单类型定义模式组件。此接口为构面组件提供了几个查询操作。
 * 
 */
public interface XSSimpleTypeDefinition extends XSTypeDefinition {
    // Variety definitions
    /**
     * The variety is absent for the anySimpleType definition.
     * <p>
     *  对于anySimpleType定义,缺少变量。
     * 
     */
    public static final short VARIETY_ABSENT            = 0;
    /**
     * <code>Atomic</code> type.
     * <p>
     *  <code> Atomic </code>类型。
     * 
     */
    public static final short VARIETY_ATOMIC            = 1;
    /**
     * <code>List</code> type.
     * <p>
     *  <code>列表</code>类型。
     * 
     */
    public static final short VARIETY_LIST              = 2;
    /**
     * <code>Union</code> type.
     * <p>
     *  <code>联合</code>类型。
     * 
     */
    public static final short VARIETY_UNION             = 3;

    // Facets
    /**
     * No facets defined.
     * <p>
     *  未定义面。
     * 
     */
    public static final short FACET_NONE                = 0;
    /**
     * 4.3.1 Length
     * <p>
     *  4.3.1长度
     * 
     */
    public static final short FACET_LENGTH              = 1;
    /**
     * 4.3.2 minLength.
     * <p>
     *  4.3.2 minLength。
     * 
     */
    public static final short FACET_MINLENGTH           = 2;
    /**
     * 4.3.3 maxLength.
     * <p>
     *  4.3.3 maxLength。
     * 
     */
    public static final short FACET_MAXLENGTH           = 4;
    /**
     * 4.3.4 pattern.
     * <p>
     *  4.3.4模式。
     * 
     */
    public static final short FACET_PATTERN             = 8;
    /**
     * 4.3.5 whitespace.
     * <p>
     *  4.3.5空白。
     * 
     */
    public static final short FACET_WHITESPACE          = 16;
    /**
     * 4.3.7 maxInclusive.
     * <p>
     *  4.3.7最大包含。
     * 
     */
    public static final short FACET_MAXINCLUSIVE        = 32;
    /**
     * 4.3.9 maxExclusive.
     * <p>
     *  4.3.9 maxExclusive。
     * 
     */
    public static final short FACET_MAXEXCLUSIVE        = 64;
    /**
     * 4.3.9 minExclusive.
     * <p>
     *  4.3.9分钟排除。
     * 
     */
    public static final short FACET_MINEXCLUSIVE        = 128;
    /**
     * 4.3.10 minInclusive.
     * <p>
     *  4.3.10分钟包含。
     * 
     */
    public static final short FACET_MININCLUSIVE        = 256;
    /**
     * 4.3.11 totalDigits .
     * <p>
     *  4.3.11 totalDigits。
     * 
     */
    public static final short FACET_TOTALDIGITS         = 512;
    /**
     * 4.3.12 fractionDigits.
     * <p>
     * 4.3.12 fractionDigits。
     * 
     */
    public static final short FACET_FRACTIONDIGITS      = 1024;
    /**
     * 4.3.5 enumeration.
     * <p>
     *  4.3.5枚举。
     * 
     */
    public static final short FACET_ENUMERATION         = 2048;

    /**
     * A constant defined for the 'ordered' fundamental facet: not ordered.
     * <p>
     *  为有序的基本面定义的常数：没有排序。
     * 
     */
    public static final short ORDERED_FALSE             = 0;
    /**
     * A constant defined for the 'ordered' fundamental facet: partially
     * ordered.
     * <p>
     *  为"有序"基本面定义的常数：部分有序。
     * 
     */
    public static final short ORDERED_PARTIAL           = 1;
    /**
     * A constant defined for the 'ordered' fundamental facet: total ordered.
     * <p>
     *  为"有序"基本面定义的常数：总有序。
     * 
     */
    public static final short ORDERED_TOTAL             = 2;
    /**
     * [variety]: one of {atomic, list, union} or absent.
     * <p>
     *  [品种]：{atomic,list,union}之一或不存在。
     * 
     */
    public short getVariety();

    /**
     * If variety is <code>atomic</code> the primitive type definition (a
     * built-in primitive datatype definition or the simple ur-type
     * definition) is available, otherwise <code>null</code>.
     * <p>
     *  如果品种是<code> atomic </code>,原始类型定义(内置原始数据类型定义或简单ur类型定义)可用,否则<code> null </code>。
     * 
     */
    public XSSimpleTypeDefinition getPrimitiveType();

    /**
     * Returns the closest built-in type category this type represents or
     * derived from. For example, if this simple type is a built-in derived
     * type integer the <code>INTEGER_DV</code> is returned.
     * <p>
     *  返回此类型表示或派生自的最近的内置类型类别。例如,如果此简单类型是内置的派生类型整数,则返回<code> INTEGER_DV </code>。
     * 
     */
    public short getBuiltInKind();

    /**
     * If variety is <code>list</code> the item type definition (an atomic or
     * union simple type definition) is available, otherwise
     * <code>null</code>.
     * <p>
     *  如果品种是<code> list </code>,则可以使用项类型定义(原子或联合简单类型定义),否则<code> null </code>。
     * 
     */
    public XSSimpleTypeDefinition getItemType();

    /**
     * If variety is <code>union</code> the list of member type definitions (a
     * non-empty sequence of simple type definitions) is available,
     * otherwise an empty <code>XSObjectList</code>.
     * <p>
     *  如果品种是<code> union </code>,则成员类型定义的列表(非空序列的简单类型定义)是可用的,否则为空的<code> XSObjectList </code>。
     * 
     */
    public XSObjectList getMemberTypes();

    /**
     * [facets]: all facets defined on this type. The value is a bit
     * combination of FACET_XXX constants of all defined facets.
     * <p>
     *  [facets]：在此类型上定义的所有构面。该值是所有定义面的FACET_XXX常量的位组合。
     * 
     */
    public short getDefinedFacets();

    /**
     * Convenience method. [Facets]: check whether a facet is defined on this
     * type.
     * <p>
     *  方便的方法。 [Facets]：检查是否在此类型上定义了构面。
     * 
     * 
     * @param facetName  The name of the facet.
     * @return  True if the facet is defined, false otherwise.
     */
    public boolean isDefinedFacet(short facetName);

    /**
     * [facets]: all defined facets for this type which are fixed.
     * <p>
     *  [facets]：这个类型的所有定义的facets是固定的。
     * 
     */
    public short getFixedFacets();

    /**
     * Convenience method. [Facets]: check whether a facet is defined and
     * fixed on this type.
     * <p>
     *  方便的方法。 [Facets]：检查在此类型上是否定义和修订构面。
     * 
     * 
     * @param facetName  The name of the facet.
     * @return  True if the facet is fixed, false otherwise.
     */
    public boolean isFixedFacet(short facetName);

    /**
     * Convenience method. Returns a value of a single constraining facet for
     * this simple type definition. This method must not be used to retrieve
     * values for <code>enumeration</code> and <code>pattern</code> facets.
     * <p>
     * 方便的方法。返回此简单类型定义的单个约束构面的值。此方法不能用于检索<code>枚举</code>和<code>模式</code>构面的值。
     * 
     * 
     * @param facetName The name of the facet, i.e.
     *   <code>FACET_LENGTH, FACET_TOTALDIGITS</code>.
     *   To retrieve the value for a pattern or
     *   an enumeration, see <code>enumeration</code> and
     *   <code>pattern</code>.
     * @return A value of the facet specified in <code>facetName</code> for
     *   this simple type definition or <code>null</code>.
     */
    public String getLexicalFacetValue(short facetName);

    /**
     * A list of enumeration values if it exists, otherwise an empty
     * <code>StringList</code>.
     * <p>
     *  枚举值列表(如果存在),否则为空<> StringList </code>。
     * 
     */
    public StringList getLexicalEnumeration();

    /**
     * A list of pattern values if it exists, otherwise an empty
     * <code>StringList</code>.
     * <p>
     *  模式值的列表(如果存在),否则为空的<code> StringList </code>。
     * 
     */
    public StringList getLexicalPattern();

    /**
     *  Fundamental Facet: ordered.
     * <p>
     *  基本面：有序。
     * 
     */
    public short getOrdered();

    /**
     * Fundamental Facet: cardinality.
     * <p>
     *  基本面：基数。
     * 
     */
    public boolean getFinite();

    /**
     * Fundamental Facet: bounded.
     * <p>
     *  基本方面：有界。
     * 
     */
    public boolean getBounded();

    /**
     * Fundamental Facet: numeric.
     * <p>
     *  基本方面：数字。
     * 
     */
    public boolean getNumeric();

    /**
     *  A list of constraining facets if it exists, otherwise an empty
     * <code>XSObjectList</code>. Note: This method must not be used to
     * retrieve values for <code>enumeration</code> and <code>pattern</code>
     * facets.
     * <p>
     *  约束面的列表(如果存在),否则为空的<code> XSObjectList </code>。注意：此方法不能用于检索<code>枚举</code>和<code>模式</code>构面的值。
     * 
     */
    public XSObjectList getFacets();

    /**
     *  A list of enumeration and pattern constraining facets if it exists,
     * otherwise an empty <code>XSObjectList</code>.
     * <p>
     *  枚举和模式约束facet的列表(如果存在),否则为空的<code> XSObjectList </code>。
     * 
     */
    public XSObjectList getMultiValueFacets();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     * <p>
     *  一个[注释]序列或一个空的<code> XSObjectList </code>。
     */
    public XSObjectList getAnnotations();

}
