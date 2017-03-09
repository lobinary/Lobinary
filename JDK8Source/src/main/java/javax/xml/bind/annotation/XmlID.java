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

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * <p>
 * Maps a JavaBean property to XML ID.
 *
 * <p>
 * To preserve referential integrity of an object graph across XML
 * serialization followed by a XML deserialization, requires an object
 * reference to be marshalled by reference or containment
 * appropriately. Annotations <tt>&#64;XmlID</tt> and <tt>&#64;XmlIDREF</tt>
 * together allow a customized mapping of a JavaBean property's
 * type by containment or reference.
 *
 * <p><b>Usage</b> </p>
 * The <tt>&#64;XmlID</tt> annotation can be used with the following
 * program elements:
 * <ul>
 *   <li> a JavaBean property </li>
 *   <li> non static, non transient field </li>
 * </ul>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * The usage is subject to the following constraints:
 * <ul>
 *   <li> At most one field or property in a class can be annotated
 *        with <tt>&#64;XmlID</tt>.  </li>
 *   <li> The JavaBean property's type must be <tt>java.lang.String</tt>.</li>
 *   <li> The only other mapping annotations that can be used
 *        with <tt>&#64;XmlID</tt>
 *        are:<tt>&#64;XmlElement</tt> and <tt>&#64;XmlAttribute</tt>.</li>
 * </ul>
 *
 * <p><b>Example</b>: Map a JavaBean property's type to <tt>xs:ID</tt></p>
 * <pre>
 *    // Example: code fragment
 *    public class Customer {
 *        &#64;XmlAttribute
 *        &#64;XmlID
 *        public String getCustomerID();
 *        public void setCustomerID(String id);
 *        .... other properties not shown
 *    }
 *
 *    &lt;!-- Example: XML Schema fragment -->
 *    &lt;xs:complexType name="Customer">
 *      &lt;xs:complexContent>
 *        &lt;xs:sequence>
 *          ....
 *        &lt;/xs:sequence>
 *        &lt;xs:attribute name="customerID" type="xs:ID"/>
 *      &lt;/xs:complexContent>
 *    &lt;/xs:complexType>
 * </pre>
 *
 * <p>
 * <p>
 *  将JavaBean属性映射到XML ID。
 * 
 * <p>
 *  为了保持XML序列化之后的XML反序列化的对象图的引用完整性,需要通过引用或包含适当地对对象引用进行编组。
 * 注释<tt> @XmlID </tt>和<tt> @XmlIDREF </tt>一起允许通过包含或引用对JavaBean属性的类型进行自定义映射。
 * 
 *  <p> <b>使用</b> </p> <tt> @XmlID </tt>注释可用于以下程序元素：
 * <ul>
 *  <li>一个JavaBean属性</li> <li>非静态,非瞬态字段</li>
 * </ul>
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 *  用法受以下约束：
 * <ul>
 *  <li>类别中的一个字段或属性最多可以使用<tt> @XmlID </tt>注释。
 *  </li> <li>唯一可用于<tt> @XmlID </t>的其他映射注释</li> </tt>是：<tt> @XmlElement </tt>和<tt> @XmlAttribute </tt>。
 * </li>。
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @see XmlIDREF
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlID { }
