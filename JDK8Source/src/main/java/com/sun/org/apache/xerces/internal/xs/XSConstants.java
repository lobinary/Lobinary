/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2005 The Apache Software Foundation.
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
 *  版权所有2003-2005 Apache软件基金会。
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
 *  This interface defines constants used by this specification.
 * <p>
 *  此接口定义此规范使用的常量。
 * 
 */
public interface XSConstants {
    // XML Schema Components
    /**
     * The object describes an attribute declaration.
     * <p>
     *  对象描述属性声明。
     * 
     */
    public static final short ATTRIBUTE_DECLARATION     = 1;
    /**
     * The object describes an element declaration.
     * <p>
     *  对象描述了一个元素声明。
     * 
     */
    public static final short ELEMENT_DECLARATION       = 2;
    /**
     * The object describes a complex type or simple type definition.
     * <p>
     *  对象描述复杂类型或简单类型定义。
     * 
     */
    public static final short TYPE_DEFINITION           = 3;
    /**
     * The object describes an attribute use definition.
     * <p>
     *  对象描述了属性使用定义。
     * 
     */
    public static final short ATTRIBUTE_USE             = 4;
    /**
     * The object describes an attribute group definition.
     * <p>
     *  对象描述属性组定义。
     * 
     */
    public static final short ATTRIBUTE_GROUP           = 5;
    /**
     * The object describes a model group definition.
     * <p>
     *  对象描述模型组定义。
     * 
     */
    public static final short MODEL_GROUP_DEFINITION    = 6;
    /**
     * A model group.
     * <p>
     *  模型组。
     * 
     */
    public static final short MODEL_GROUP               = 7;
    /**
     * The object describes a particle.
     * <p>
     *  对象描述一个粒子。
     * 
     */
    public static final short PARTICLE                  = 8;
    /**
     * The object describes a wildcard.
     * <p>
     *  对象描述通配符。
     * 
     */
    public static final short WILDCARD                  = 9;
    /**
     * The object describes an identity constraint definition.
     * <p>
     *  对象描述了身份约束定义。
     * 
     */
    public static final short IDENTITY_CONSTRAINT       = 10;
    /**
     * The object describes a notation declaration.
     * <p>
     *  对象描述符号声明。
     * 
     */
    public static final short NOTATION_DECLARATION      = 11;
    /**
     * The object describes an annotation.
     * <p>
     *  对象描述注释。
     * 
     */
    public static final short ANNOTATION                = 12;
    /**
     * The object describes a constraining facet. Note: this object does not
     * describe pattern and enumeration facets.
     * <p>
     *  对象描述约束面。注意：此对象不描述模式和枚举构面。
     * 
     */
    public static final short FACET                     = 13;
    /**
     * The object describes enumeration and pattern facets.
     * <p>
     *  该对象描述枚举和模式构面。
     * 
     */
    public static final short MULTIVALUE_FACET          = 14;

    // Derivation constants
    /**
     * No constraint is available.
     * <p>
     * 没有约束可用。
     * 
     */
    public static final short DERIVATION_NONE           = 0;
    /**
     * <code>XSTypeDefinition</code> final set or
     * <code>XSElementDeclaration</code> disallowed substitution group.
     * <p>
     *  <code> XSTypeDefinition </code> final set或<code> XSElementDeclaration </code>不允许的替换组。
     * 
     */
    public static final short DERIVATION_EXTENSION      = 1;
    /**
     * <code>XSTypeDefinition</code> final set or
     * <code>XSElementDeclaration</code> disallowed substitution group.
     * <p>
     *  <code> XSTypeDefinition </code> final set或<code> XSElementDeclaration </code>不允许的替换组。
     * 
     */
    public static final short DERIVATION_RESTRICTION    = 2;
    /**
     * <code>XSTypeDefinition</code> final set.
     * <p>
     *  <code> XSTypeDefinition </code> final set。
     * 
     */
    public static final short DERIVATION_SUBSTITUTION   = 4;
    /**
     * <code>XSTypeDefinition</code> final set.
     * <p>
     *  <code> XSTypeDefinition </code> final set。
     * 
     */
    public static final short DERIVATION_UNION          = 8;
    /**
     * <code>XSTypeDefinition</code> final set.
     * <p>
     *  <code> XSTypeDefinition </code> final set。
     * 
     */
    public static final short DERIVATION_LIST           = 16;
    /**
     * <code>XSTypeDefinition</code> final set.
     * <p>
     *  <code> XSTypeDefinition </code> final set。
     * 
     */
    public static final short DERIVATION_EXTENSION_RESTRICTION_SUBSTITION =
            XSConstants.DERIVATION_EXTENSION
            | XSConstants.DERIVATION_RESTRICTION
            | XSConstants.DERIVATION_SUBSTITUTION;
    /**
     * <code>XSTypeDefinition</code> final set.
     * <p>
     *  <code> XSTypeDefinition </code> final set。
     * 
     */
    public static final short DERIVATION_ALL            =
            XSConstants.DERIVATION_SUBSTITUTION
            | XSConstants.DERIVATION_EXTENSION
            | XSConstants.DERIVATION_RESTRICTION
            | XSConstants.DERIVATION_LIST
            | XSConstants.DERIVATION_UNION;

    // Scope
    /**
     * The scope of a declaration within named model groups or attribute
     * groups is <code>absent</code>. The scope of such a declaration is
     * determined when it is used in the construction of complex type
     * definitions.
     * <p>
     *  命名模型组或属性组中的声明范围为<code> absent </code>。这种声明的范围在用于复杂类型定义的构造时被确定。
     * 
     */
    public static final short SCOPE_ABSENT              = 0;
    /**
     * A scope of <code>global</code> identifies top-level declarations.
     * <p>
     *  <code> global </code>的范围标识顶级声明。
     * 
     */
    public static final short SCOPE_GLOBAL              = 1;
    /**
     * <code>Locally scoped</code> declarations are available for use only
     * within the complex type.
     * <p>
     *  <code>本地作用域</code>声明只能在复杂类型中使用。
     * 
     */
    public static final short SCOPE_LOCAL               = 2;

    // Value Constraint
    /**
     * Indicates that the component does not have any value constraint.
     * <p>
     *  表示组件没有任何值约束。
     * 
     */
    public static final short VC_NONE                   = 0;
    /**
     * Indicates that there is a default value constraint.
     * <p>
     *  表示存在默认值约束。
     * 
     */
    public static final short VC_DEFAULT                = 1;
    /**
     * Indicates that there is a fixed value constraint for this attribute.
     * <p>
     *  表示此属性有固定值约束。
     * 
     */
    public static final short VC_FIXED                  = 2;

    // Built-in types: primitive and derived
    /**
     * anySimpleType
     * <p>
     *  anySimpleType
     * 
     */
    public static final short ANYSIMPLETYPE_DT          = 1;
    /**
     * string
     * <p>
     *  串
     * 
     */
    public static final short STRING_DT                 = 2;
    /**
     * boolean
     * <p>
     *  布尔
     * 
     */
    public static final short BOOLEAN_DT                = 3;
    /**
     * decimal
     * <p>
     *  十进制
     * 
     */
    public static final short DECIMAL_DT                = 4;
    /**
     * float
     * <p>
     *  浮动
     * 
     */
    public static final short FLOAT_DT                  = 5;
    /**
     * double
     * <p>
     *  双
     * 
     */
    public static final short DOUBLE_DT                 = 6;
    /**
     * duration
     * <p>
     *  持续时间
     * 
     */
    public static final short DURATION_DT               = 7;
    /**
     * dateTime
     * <p>
     *  约会时间
     * 
     */
    public static final short DATETIME_DT               = 8;
    /**
     * time
     * <p>
     *  时间
     * 
     */
    public static final short TIME_DT                   = 9;
    /**
     * date
     * <p>
     *  日期
     * 
     */
    public static final short DATE_DT                   = 10;
    /**
     * gYearMonth
     * <p>
     *  gYearMonth
     * 
     */
    public static final short GYEARMONTH_DT             = 11;
    /**
     * gYear
     * <p>
     *  g年
     * 
     */
    public static final short GYEAR_DT                  = 12;
    /**
     * gMonthDay
     * <p>
     *  gMonthDay
     * 
     */
    public static final short GMONTHDAY_DT              = 13;
    /**
     * gDay
     * <p>
     *  gDay
     * 
     */
    public static final short GDAY_DT                   = 14;
    /**
     * gMonth
     * <p>
     *  gMonth
     * 
     */
    public static final short GMONTH_DT                 = 15;
    /**
     * hexBinary
     * <p>
     *  hexBinary
     * 
     */
    public static final short HEXBINARY_DT              = 16;
    /**
     * base64Binary
     * <p>
     *  base64Binary
     * 
     */
    public static final short BASE64BINARY_DT           = 17;
    /**
     * anyURI
     * <p>
     *  anyURI
     * 
     */
    public static final short ANYURI_DT                 = 18;
    /**
     * QName
     * <p>
     *  QName
     * 
     */
    public static final short QNAME_DT                  = 19;
    /**
     * NOTATION
     * <p>
     *  符号
     * 
     */
    public static final short NOTATION_DT               = 20;
    /**
     * normalizedString
     * <p>
     *  normalizedString
     * 
     */
    public static final short NORMALIZEDSTRING_DT       = 21;
    /**
     * token
     * <p>
     *  令牌
     * 
     */
    public static final short TOKEN_DT                  = 22;
    /**
     * language
     * <p>
     *  语言
     * 
     */
    public static final short LANGUAGE_DT               = 23;
    /**
     * NMTOKEN
     * <p>
     *  NMTOKEN
     * 
     */
    public static final short NMTOKEN_DT                = 24;
    /**
     * Name
     * <p>
     *  名称
     * 
     */
    public static final short NAME_DT                   = 25;
    /**
     * NCName
     * <p>
     *  NCName
     * 
     */
    public static final short NCNAME_DT                 = 26;
    /**
     * ID
     * <p>
     *  ID
     * 
     */
    public static final short ID_DT                     = 27;
    /**
     * IDREF
     * <p>
     *  IDREF
     * 
     */
    public static final short IDREF_DT                  = 28;
    /**
     * ENTITY
     * <p>
     *  实体
     * 
     */
    public static final short ENTITY_DT                 = 29;
    /**
     * integer
     * <p>
     *  整数
     * 
     */
    public static final short INTEGER_DT                = 30;
    /**
     * nonPositiveInteger
     * <p>
     *  nonPositiveInteger
     * 
     */
    public static final short NONPOSITIVEINTEGER_DT     = 31;
    /**
     * negativeInteger
     * <p>
     *  negativeInteger
     * 
     */
    public static final short NEGATIVEINTEGER_DT        = 32;
    /**
     * long
     * <p>
     *  长
     * 
     */
    public static final short LONG_DT                   = 33;
    /**
     * int
     * <p>
     *  int
     * 
     */
    public static final short INT_DT                    = 34;
    /**
     * short
     * <p>
     *  短
     * 
     */
    public static final short SHORT_DT                  = 35;
    /**
     * byte
     * <p>
     *  字节
     * 
     */
    public static final short BYTE_DT                   = 36;
    /**
     * nonNegativeInteger
     * <p>
     *  nonNegativeInteger
     * 
     */
    public static final short NONNEGATIVEINTEGER_DT     = 37;
    /**
     * unsignedLong
     * <p>
     *  unsignedLong
     * 
     */
    public static final short UNSIGNEDLONG_DT           = 38;
    /**
     * unsignedInt
     * <p>
     *  unsignedInt
     * 
     */
    public static final short UNSIGNEDINT_DT            = 39;
    /**
     * unsignedShort
     * <p>
     *  unsignedShort
     * 
     */
    public static final short UNSIGNEDSHORT_DT          = 40;
    /**
     * unsignedByte
     * <p>
     *  unsignedByte
     * 
     */
    public static final short UNSIGNEDBYTE_DT           = 41;
    /**
     * positiveInteger
     * <p>
     *  positiveInteger
     * 
     */
    public static final short POSITIVEINTEGER_DT        = 42;
    /**
     * The type represents a list type definition whose item type (itemType)
     * is a union type definition
     * <p>
     * 类型表示其项类型(itemType)是联合类型定义的列表类型定义
     * 
     */
    public static final short LISTOFUNION_DT            = 43;
    /**
     * The type represents a list type definition.
     * <p>
     *  类型表示列表类型定义。
     * 
     */
    public static final short LIST_DT                   = 44;
    /**
     * The built-in type category is not available.
     * <p>
     *  内置类型类别不可用。
     */
    public static final short UNAVAILABLE_DT            = 45;

}
