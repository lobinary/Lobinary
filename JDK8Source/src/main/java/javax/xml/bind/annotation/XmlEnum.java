/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.xml.bind.annotation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * <p>
 * Maps an enum type {@link Enum} to XML representation.
 *
 * <p>This annotation, together with {@link XmlEnumValue} provides a
 * mapping of enum type to XML representation.
 *
 * <p> <b>Usage</b> </p>
 * <p>
 * The <tt>@XmlEnum</tt> annotation can be used with the
 * following program elements:
 * <ul>
 *   <li>enum type</li>
 * </ul>
 *
 * <p> The usage is subject to the following constraints:
 * <ul>
 *   <li> This annotation can be used the following other annotations:
 *         {@link XmlType},
 *         {@link XmlRootElement} </li>
 * </ul>
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information </p>
 *
 * <p>An enum type is mapped to a schema simple type with enumeration
 * facets. The schema type is derived from the Java type to which
 * <tt>@XmlEnum.value()</tt>. Each enum constant <tt>@XmlEnumValue</tt>
 * must have a valid lexical representation for the type
 * <tt>@XmlEnum.value()</tt> .
 *
 * <p><b>Examples:</b> See examples in {@link XmlEnumValue}
 *
 * <p>
 * <p>
 *  将枚举类型{@link Enum}映射为XML表示形式。
 * 
 *  <p>此注释与{@link XmlEnumValue}一起提供了枚举类型到XML表示形式的映射。
 * 
 *  <p> <b>使用</b> </p>
 * <p>
 *  <tt> @XmlEnum </tt>注释可以与以下程序元素一起使用：
 * <ul>
 *  <li>枚举类型</li>
 * </ul>
 * 
 *  <p>使用受以下限制约束：
 * 
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({TYPE})
public @interface XmlEnum {
    /**
     * Java type that is mapped to a XML simple type.
     *
     * <p>
     * <ul>
     *  <li>此注释可用于以下其他注释：{@link XmlType},{@link XmlRootElement} </li>
     * </ul>
     *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"</p>
     * 
     *  <p>枚举类型映射到具有枚举构面的模式简单类型。模式类型派生自<tt> @ XmlEnum.value()</tt>的Java类型。
     * 每个枚举常量<tt> @XmlEnumValue </tt>必须对类型<tt> @ XmlEnum.value()</tt>具有有效的词法表示。
     */
    Class<?> value() default String.class;
}
