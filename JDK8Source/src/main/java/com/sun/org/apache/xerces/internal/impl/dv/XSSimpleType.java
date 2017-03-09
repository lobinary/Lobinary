/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.dv;

import com.sun.org.apache.xerces.internal.xs.XSSimpleTypeDefinition;

/**
 * This interface <code>XSSimpleType</code> represents the simple type
 * definition of schema component and defines methods to query the information
 * contained.
 * Any simple type (atomic, list or union) will implement this interface.
 * It inherits from <code>XSTypeDecl</code>.
 *
 * @xerces.internal
 *
 * <p>
 *  此接口<code> XSSimpleType </code>表示模式组件的简单类型定义,并定义查询包含的信息的方法。任何简单类型(原子,列表或联合)将实现此接口。
 * 它继承自<code> XSTypeDecl </code>。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 */
public interface XSSimpleType extends XSSimpleTypeDefinition {

    /**
     * constants defined for the values of 'whitespace' facet.
     * see <a href='http://www.w3.org/TR/xmlschema-2/#dt-whiteSpace'> XML Schema
     * Part 2: Datatypes </a>
     * <p>
     *  为"空白"面的值定义的常量。请参见<a href='http://www.w3.org/TR/xmlschema-2/#dt-whiteSpace'> XML模式第2部分：数据类型</a>
     * 
     */
    /** preserve the white spaces */
    public static final short WS_PRESERVE = 0;
    /** replace the white spaces */
    public static final short WS_REPLACE  = 1;
    /** collapse the white spaces */
    public static final short WS_COLLAPSE = 2;

    /**
     * Constant defined for the primitive built-in simple tpyes.
     * see <a href='http://www.w3.org/TR/xmlschema-2/#built-in-primitive-datatypes'>
     * XML Schema Part 2: Datatypes </a>
     * <p>
     *  常量定义为原始内置简单tpyes。
     * 请参见<a href='http://www.w3.org/TR/xmlschema-2/#built-in-primitive-datatypes'> XML模式第2部分：数据类型</a>。
     * 
     */
    /** "string" type */
    public static final short PRIMITIVE_STRING        = 1;
    /** "boolean" type */
    public static final short PRIMITIVE_BOOLEAN       = 2;
    /** "decimal" type */
    public static final short PRIMITIVE_DECIMAL       = 3;
    /** "float" type */
    public static final short PRIMITIVE_FLOAT         = 4;
    /** "double" type */
    public static final short PRIMITIVE_DOUBLE        = 5;
    /** "duration" type */
    public static final short PRIMITIVE_DURATION      = 6;
    /** "dataTime" type */
    public static final short PRIMITIVE_DATETIME      = 7;
    /** "time" type */
    public static final short PRIMITIVE_TIME          = 8;
    /** "date" type */
    public static final short PRIMITIVE_DATE          = 9;
    /** "gYearMonth" type */
    public static final short PRIMITIVE_GYEARMONTH    = 10;
    /** "gYear" type */
    public static final short PRIMITIVE_GYEAR         = 11;
    /** "gMonthDay" type */
    public static final short PRIMITIVE_GMONTHDAY     = 12;
    /** "gDay" type */
    public static final short PRIMITIVE_GDAY          = 13;
    /** "gMonth" type */
    public static final short PRIMITIVE_GMONTH        = 14;
    /** "hexBinary" type */
    public static final short PRIMITIVE_HEXBINARY     = 15;
    /** "base64Binary" type */
    public static final short PRIMITIVE_BASE64BINARY  = 16;
    /** "anyURI" type */
    public static final short PRIMITIVE_ANYURI        = 17;
    /** "QName" type */
    public static final short PRIMITIVE_QNAME         = 18;
    /** "precisionDecimal" type */
    public static final short PRIMITIVE_PRECISIONDECIMAL = 19;
    /** "NOTATION" type */
    public static final short PRIMITIVE_NOTATION      = 20;

    /**
     * return an ID representing the built-in primitive base type.
     * REVISIT: This method is (currently) for internal use only.
     *          the constants returned from this method are not finalized yet.
     *          the names and values might change in the further.
     *
     * <p>
     * 返回表示内置基本类型的ID。 REVISIT：此方法(当前)仅供内部使用。从此方法返回的常量尚未完成。名称和值可能会进一步改变。
     * 
     * 
     * @return   an ID representing the built-in primitive base type
     */
    public short getPrimitiveKind();

    /**
     * validate a given string against this simple type.
     *
     * <p>
     *  根据这个简单类型验证给定的字符串。
     * 
     * 
     * @param content       the string value that needs to be validated
     * @param context       the validation context
     * @param validatedInfo used to store validation result
     *
     * @return              the actual value (QName, Boolean) of the string value
     */
    public Object validate(String content, ValidationContext context, ValidatedInfo validatedInfo)
        throws InvalidDatatypeValueException;

    /**
     * validate a given string value, represented by content.toString().
     * note that if content is a StringBuffer, for performance reasons,
     * it's possible that the content of the string buffer is modified.
     *
     * <p>
     *  验证给定的字符串值,由content.toString()表示。请注意,如果内容是StringBuffer,出于性能原因,可能会修改字符串缓冲区的内容。
     * 
     * 
     * @param content       the string value that needs to be validated
     * @param context       the validation context
     * @param validatedInfo used to store validation result
     *
     * @return              the actual value (QName, Boolean) of the string value
     */
    public Object validate(Object content, ValidationContext context, ValidatedInfo validatedInfo)
        throws InvalidDatatypeValueException;

    /**
     * Validate an actual value against this simple type.
     *
     * <p>
     *  针对此简单类型验证实际值。
     * 
     * 
     * @param context       the validation context
     * @param validatedInfo used to provide the actual value and member types
     * @exception InvalidDatatypeValueException  exception for invalid values.
     */
    public void validate(ValidationContext context, ValidatedInfo validatedInfo)
        throws InvalidDatatypeValueException;

    /**
     * If this type is created from restriction, then some facets can be applied
     * to the simple type. <code>XSFacets</code> is used to pass the value of
     * different facets.
     *
     * <p>
     *  如果从限制创建此类型,则某些构面可应用于简单类型。 <code> XSFacets </code>用于传递不同facet的值。
     * 
     * 
     * @param facets        the value of all the facets
     * @param presentFacet  bit combination value of the costraining facet
     *                      constants which are present.
     * @param fixedFacet    bit combination value of the costraining facet
     *                      constants which are fixed.
     * @param context       the validation context
     * @exception InvalidDatatypeFacetException  exception for invalid facet values.
     */
    public void applyFacets(XSFacets facets, short presentFacet, short fixedFacet, ValidationContext context)
        throws InvalidDatatypeFacetException;

    /**
     * Check whether two actual values are equal.
     *
     * <p>
     *  检查两个实际值是否相等。
     * 
     * 
     * @param value1  the first value
     * @param value2  the second value
     * @return        true if the two value are equal
     */
    public boolean isEqual(Object value1, Object value2);

    /**
     * Check the order of the two actual values. (May not be supported by all
     * simple types.
     * REVISIT: Andy believes that a compare() method is necessary.
     *          I don't see the necessity for schema (the only place where we
     *          need to compare two values is to check min/maxIn/Exclusive
     *          facets, but we only need a private method for this case.)
     *          But Andy thinks XPATH potentially needs this compare() method.
     *
     * <p>
     *  检查两个实际值的顺序。 (可能不是所有简单类型都支持REVISIT：Andy认为一个compare()方法是必要的。
     * 我没有看到模式的必要性(我们需要比较两个值的唯一的地方是检查min / maxIn /但是我们只需要一个私有的方法。)但Andy认为XPATH可能需要这个compare()方法。
     * 
     * 
     * @param value1  the first value
     * @prarm value2  the second value
     * @return        > 0 if value1 > value2
     *                = 0 if value1 == value2
     *                < = if value1 < value2
     */
    //public short compare(Object value1, Object value2);

    /**
     * Check whether this type is or is derived from ID.
     * REVISIT: this method makes ID special, which is not a good design.
     *          but since ID is not a primitive, there doesn't seem to be a
     *          clean way of doing it except to define special method like this.
     *
     * <p>
     *  检查此类型是否是或是从ID派生。 REVISIT：这种方法使ID特殊,这不是一个好的设计。但由于ID不是一个原始的,似乎没有一个干净的做法,除了定义这样的特殊方法。
     * 
     * 
     * @return  whether this simple type is or is derived from ID.
     */
    public boolean isIDType();

    /**
     * Return the whitespace corresponding to this datatype.
     *
     * <p>
     *  返回与此数据类型对应的空格。
     * 
     * @return valid values are WS_PRESERVE, WS_REPLACE, WS_COLLAPSE.
     * @exception DatatypeException
     *                   union datatypes don't have whitespace facet associated with them
     */
    public short getWhitespace() throws DatatypeException;
}
