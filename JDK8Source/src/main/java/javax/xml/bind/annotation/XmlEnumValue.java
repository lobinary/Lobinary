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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

/**
 * Maps an enum constant in {@link Enum} type to XML representation.
 *
 * <p> <b>Usage</b> </p>
 *
 * <p> The <tt>@XmlEnumValue</tt> annotation can be used with the
 *     following program elements:
 * <ul>
 *   <li>enum constant</li>
 * </ul>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>This annotation, together with {@link XmlEnum} provides a
 * mapping of enum type to XML representation.
 *
 * <p>An enum type is mapped to a schema simple type with enumeration
 * facets. The schema type is derived from the Java type specified in
 * <tt>@XmlEnum.value()</tt>. Each enum constant <tt>@XmlEnumValue</tt>
 * must have a valid lexical representation for the type
 * <tt>@XmlEnum.value()</tt>
 *
 * <p> In the absence of this annotation, {@link Enum#name()} is used
 * as the XML representation.
 *
 * <p> <b>Example 1: </b>Map enum constant name -> enumeration facet</p>
 * <pre>
 *     //Example: Code fragment
 *     &#64;XmlEnum(String.class)
 *     public enum Card { CLUBS, DIAMONDS, HEARTS, SPADES }
 *
 *     &lt;!-- Example: XML Schema fragment -->
 *     &lt;xs:simpleType name="Card">
 *       &lt;xs:restriction base="xs:string"/>
 *         &lt;xs:enumeration value="CLUBS"/>
 *         &lt;xs:enumeration value="DIAMONDS"/>
 *         &lt;xs:enumeration value="HEARTS"/>
 *         &lt;xs:enumeration value="SPADES"/>
 *     &lt;/xs:simpleType>
 * </pre>
 *
 * <p><b>Example 2: </b>Map enum constant name(value) -> enumeration facet </p>
 * <pre>
 *     //Example: code fragment
 *     &#64;XmlType
 *     &#64;XmlEnum(Integer.class)
 *     public enum Coin {
 *         &#64;XmlEnumValue("1") PENNY(1),
 *         &#64;XmlEnumValue("5") NICKEL(5),
 *         &#64;XmlEnumValue("10") DIME(10),
 *         &#64;XmlEnumValue("25") QUARTER(25) }
 *
 *     &lt;!-- Example: XML Schema fragment -->
 *     &lt;xs:simpleType name="Coin">
 *       &lt;xs:restriction base="xs:int">
 *         &lt;xs:enumeration value="1"/>
 *         &lt;xs:enumeration value="5"/>
 *         &lt;xs:enumeration value="10"/>
 *         &lt;xs:enumeration value="25"/>
 *       &lt;/xs:restriction>
 *     &lt;/xs:simpleType>
 * </pre>
 *
 * <p><b>Example 3: </b>Map enum constant name -> enumeration facet </p>
 *
 * <pre>
 *     //Code fragment
 *     &#64;XmlType
 *     &#64;XmlEnum(Integer.class)
 *     public enum Code {
 *         &#64;XmlEnumValue("1") ONE,
 *         &#64;XmlEnumValue("2") TWO;
 *     }
 *
 *     &lt;!-- Example: XML Schema fragment -->
 *     &lt;xs:simpleType name="Code">
 *       &lt;xs:restriction base="xs:int">
 *         &lt;xs:enumeration value="1"/>
 *         &lt;xs:enumeration value="2"/>
 *       &lt;/xs:restriction>
 *     &lt;/xs:simpleType>
 * </pre>
 *
 * <p>
 *  将{@link Enum}类型的枚举常量映射为XML表示形式。
 * 
 *  <p> <b>使用</b> </p>
 * 
 *  <p> <tt> @XmlEnumValue </tt>注释可与以下程序元素一起使用：
 * <ul>
 *  <li>枚举常数</li>
 * </ul>
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 *  <p>此注释与{@link XmlEnum}一起提供了枚举类型到XML表示的映射。
 * 
 *  <p>枚举类型映射到具有枚举构面的模式简单类型。模式类型派生自<tt> @ XmlEnum.value()</tt>中指定的Java类型。
 * 每个枚举常量<tt> @XmlEnumValue </tt>必须具有<tt> @ XmlEnum.value()</tt>类型的有效词汇表示形式。
 * 
 *  <p>在缺少此注释的情况下,{@link Enum#name()}用作XML表示形式。
 * 
 *  <p> <b>示例1：</b>地图枚举常量名称 - >枚举类别</p>
 * <pre>
 *  //示例：代码片段@XmlEnum(String.class)public enum Card {CLUBS,DIAMONDS,HEARTS,SPADES}
 * 
 * &lt;!-- Example: XML Schema fragment -->
 * &lt;xs:simpleType name="Card">
 * &lt;xs:restriction base="xs:string"/>
 * &lt;xs:enumeration value="CLUBS"/>
 * &lt;xs:enumeration value="DIAMONDS"/>
 * &lt;xs:enumeration value="HEARTS"/>
 * &lt;xs:enumeration value="SPADES"/>
 * &lt;/xs:simpleType>
 * </pre>
 * 
 * @since JAXB 2.0
 */
@Retention(RUNTIME)
@Target({FIELD})
public @interface XmlEnumValue {
    String value();
}
