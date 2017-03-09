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

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * <p>
 * Maps a JavaBean property to a XML attribute.
 *
 * <p> <b>Usage</b> </p>
 * <p>
 * The <tt>@XmlAttribute</tt> annotation can be used with the
 * following program elements:
 * <ul>
 *   <li> JavaBean property </li>
 *   <li> field </li>
 * </ul>
 *
 * <p> A static final field is mapped to a XML fixed attribute.
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * The usage is subject to the following constraints:
 * <ul>
 *   <li> If type of the field or the property is a collection
 *        type, then the collection item type must be mapped to schema
 *        simple type.
 * <pre>
 *     // Examples
 *     &#64;XmlAttribute List&lt;Integer> items; //legal
 *     &#64;XmlAttribute List&lt;Bar> foo; // illegal if Bar does not map to a schema simple type
 * </pre>
 *   </li>
 *   <li> If the type of the field or the property is a non
 *         collection type, then the type of the property or field
 *         must map to a simple schema type.
 * <pre>
 *     // Examples
 *     &#64;XmlAttribute int foo; // legal
 *     &#64;XmlAttribute Foo foo; // illegal if Foo does not map to a schema simple type
 * </pre>
 *   </li>
 *   <li> This annotation can be used with the following annotations:
 *            {@link XmlID},
 *            {@link XmlIDREF},
 *            {@link XmlList},
 *            {@link XmlSchemaType},
 *            {@link XmlValue},
 *            {@link XmlAttachmentRef},
 *            {@link XmlMimeType},
 *            {@link XmlInlineBinaryData},
 *            {@link javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter}.</li>
 * </ul>
 * </p>
 *
 * <p> <b>Example 1: </b>Map a JavaBean property to an XML attribute.</p>
 * <pre>
 *     //Example: Code fragment
 *     public class USPrice {
 *         &#64;XmlAttribute
 *         public java.math.BigDecimal getPrice() {...} ;
 *         public void setPrice(java.math.BigDecimal ) {...};
 *     }
 *
 *     &lt;!-- Example: XML Schema fragment -->
 *     &lt;xs:complexType name="USPrice">
 *       &lt;xs:sequence>
 *       &lt;/xs:sequence>
 *       &lt;xs:attribute name="price" type="xs:decimal"/>
 *     &lt;/xs:complexType>
 * </pre>
 *
 * <p> <b>Example 2: </b>Map a JavaBean property to an XML attribute with anonymous type.</p>
 * See Example 7 in @{@link XmlType}.
 *
 * <p> <b>Example 3: </b>Map a JavaBean collection property to an XML attribute.</p>
 * <pre>
 *     // Example: Code fragment
 *     class Foo {
 *         ...
 *         &#64;XmlAttribute List&lt;Integer> items;
 *     }
 *
 *     &lt;!-- Example: XML Schema fragment -->
 *     &lt;xs:complexType name="foo">
 *       ...
 *       &lt;xs:attribute name="items">
 *         &lt;xs:simpleType>
 *           &lt;xs:list itemType="xs:int"/>
 *         &lt;/xs:simpleType>
 *     &lt;/xs:complexType>
 *
 * </pre>
 * <p>
 * <p>
 *  将JavaBean属性映射到XML属性。
 * 
 *  <p> <b>使用</b> </p>
 * <p>
 *  <tt> @XmlAttribute </tt>注释可以与以下程序元素一起使用：
 * <ul>
 *  <li> JavaBean属性</li> <li>字段</li>
 * </ul>
 * 
 *  <p>静态final字段映射到XML固定属性。
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 *  用法受以下约束：
 * <ul>
 *  <li>如果字段或属性的类型是集合类型,则集合项类​​型必须映射到模式简单类型。
 * <pre>
 *  //示例@XmlAttribute List&lt; Integer> items; // legal @XmlAttribute List&lt; Bar> foo; //如果Bar不映射到模式简单
 * 类型,则为非法。
 * </pre>
 * </li>
 *  <li>如果字段或属性的类型是非集合类型,则属性或字段的类型必须映射到简单的模式类型。
 * <pre>
 *  //示例@XmlAttribute int foo; // legal @XmlAttribute Foo foo; //非法如果Foo不映射到模式简单类型
 * </pre>
 * </li>
 *  <li>此注释可用于以下注释：{@link XmlID},{@link XmlIDREF},{@link XmlList},{@link XmlSchemaType},{@link XmlValue}
 * ,{@link XmlAttachmentRef},{ @link XmlMimeType},{@link XmlInlineBinaryData},{@link javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter}
 * 。
 * </li>。
 * </ul>
 * </p>
 * 
 * <p> <b>示例1：</b>将JavaBean属性映射到XML属性。</p>
 * <pre>
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @see XmlType
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlAttribute {
    /**
     * Name of the XML Schema attribute. By default, the XML Schema
     * attribute name is derived from the JavaBean property name.
     *
     * <p>
     *  //示例：代码片段public class USPrice {@XmlAttribute public java.math.BigDecimal getPrice(){...}; public voi
     * d setPrice(java.math.BigDecimal){...}; }}。
     * 
     * &lt;!-- Example: XML Schema fragment -->
     * &lt;xs:complexType name="USPrice">
     * &lt;xs:sequence>
     * &lt;/xs:sequence>
     * &lt;xs:attribute name="price" type="xs:decimal"/>
     * &lt;/xs:complexType>
     * </pre>
     * 
     *  <p> <b>示例2：</b>将JavaBean属性映射到具有匿名类型的XML属性。</p>请参见@ {@ link XmlType}中的示例7。
     * 
     *  <p> <b>示例3：</b>将JavaBean集合属性映射到XML属性。</p>
     * <pre>
     *  //示例：代码片段类Foo {... @XmlAttribute List&lt; Integer> items; }}
     * 
     * &lt;!-- Example: XML Schema fragment -->
     */
    String name() default "##default";

    /**
     * Specifies if the XML Schema attribute is optional or
     * required. If true, then the JavaBean property is mapped to a
     * XML Schema attribute that is required. Otherwise it is mapped
     * to a XML Schema attribute that is optional.
     *
     * <p>
     * &lt;xs:complexType name="foo">
     *  ... ...
     * &lt;xs:attribute name="items">
     * &lt;xs:simpleType>
     * &lt;xs:list itemType="xs:int"/>
     * &lt;/xs:simpleType>
     * &lt;/xs:complexType>
     * 
     * </pre>
     */
     boolean required() default false;

    /**
     * Specifies the XML target namespace of the XML Schema
     * attribute.
     *
     * <p>
     */
    String namespace() default "##default" ;
}
