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
 * Prevents the mapping of a JavaBean property/type to XML representation.
 * <p>
 * The <tt>@XmlTransient</tt> annotation is useful for resolving name
 * collisions between a JavaBean property name and a field name or
 * preventing the mapping of a field/property. A name collision can
 * occur when the decapitalized JavaBean property name and a field
 * name are the same. If the JavaBean property refers to the field,
 * then the name collision can be resolved by preventing the
 * mapping of either the field or the JavaBean property using the
 * <tt>@XmlTransient</tt> annotation.
 *
 * <p>
 * When placed on a class, it indicates that the class shouldn't be mapped
 * to XML by itself. Properties on such class will be mapped to XML along
 * with its derived classes, as if the class is inlined.
 *
 * <p><b>Usage</b></p>
 * <p> The <tt>@XmlTransient</tt> annotation can be used with the following
 *     program elements:
 * <ul>
 *   <li> a JavaBean property </li>
 *   <li> field </li>
 *   <li> class </li>
 * </ul>
 *
 * <p><tt>@XmlTransient</tt>is mutually exclusive with all other
 * JAXB defined annotations. </p>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p><b>Example:</b> Resolve name collision between JavaBean property and
 *     field name </p>
 *
 * <pre>
 *   // Example: Code fragment
 *   public class USAddress {
 *
 *       // The field name "name" collides with the property name
 *       // obtained by bean decapitalization of getName() below
 *       &#64;XmlTransient public String name;
 *
 *       String getName() {..};
 *       String setName() {..};
 *   }
 *
 *
 *   &lt;!-- Example: XML Schema fragment -->
 *   &lt;xs:complexType name="USAddress">
 *     &lt;xs:sequence>
 *       &lt;xs:element name="name" type="xs:string"/>
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * </pre>
 *
 * <p>
 * <p>
 *  防止将JavaBean属性/类型映射到XML表示。
 * <p>
 *  <tt> @XmlTransient </tt>注释有助于解决JavaBean属性名称和字段名称之间的名称冲突,或防止字段/属性的映射。
 * 当资源化的JavaBean属性名称和字段名称相同时,会发生名称冲突。
 * 如果JavaBean属性引用字段,那么可以通过使用<tt> @XmlTransient </tt>注释阻止字段或JavaBean属性的映射来解析名称冲突。
 * 
 * <p>
 *  当放置在类上时,它表示该类本身不应映射到XML。此类的属性将与其派生类一起映射到XML,就好像类是内联的一样。
 * 
 *  <p> <b>使用</b> </p> <p> <tt> @XmlTransient </tt>注释可用于以下程序元素：
 * <ul>
 *  <li>一个JavaBean属性</li> <li>字段</li> <li>类</li>
 * </ul>
 * 
 *  <p> <tt> @XmlTransient </tt>与所有其他JAXB定义的注释互斥。 </p>
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({FIELD, METHOD, TYPE})
public @interface XmlTransient {}
