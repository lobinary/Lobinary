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

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Maps a JavaBean property to a XML element derived from property name.
 *
 * <p> <b>Usage</b> </p>
 * <p>
 * <tt>@XmlElement</tt> annotation can be used with the following program
 * elements:
 * <ul>
 *   <li> a JavaBean property </li>
 *   <li> non static, non transient field </li>
 *   <li> within {@link XmlElements}
 * <p>
 *
 * </ul>
 *
 * The usage is subject to the following constraints:
 * <ul>
 *   <li> This annotation can be used with following annotations:
 *            {@link XmlID},
 *            {@link XmlIDREF},
 *            {@link XmlList},
 *            {@link XmlSchemaType},
 *            {@link XmlValue},
 *            {@link XmlAttachmentRef},
 *            {@link XmlMimeType},
 *            {@link XmlInlineBinaryData},
 *            {@link XmlElementWrapper},
 *            {@link XmlJavaTypeAdapter}</li>
 *   <li> if the type of JavaBean property is a collection type of
 *        array, an indexed property, or a parameterized list, and
 *        this annotation is used with {@link XmlElements} then,
 *        <tt>@XmlElement.type()</tt> must be DEFAULT.class since the
 *        collection item type is already known. </li>
 * </ul>
 *
 * <p>
 * A JavaBean property, when annotated with @XmlElement annotation
 * is mapped to a local element in the XML Schema complex type to
 * which the containing class is mapped.
 *
 * <p>
 * <b>Example 1: </b> Map a public non static non final field to local
 * element
 * <pre>
 *     //Example: Code fragment
 *     public class USPrice {
 *         &#64;XmlElement(name="itemprice")
 *         public java.math.BigDecimal price;
 *     }
 *
 *     &lt;!-- Example: Local XML Schema element -->
 *     &lt;xs:complexType name="USPrice"/>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="itemprice" type="xs:decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/xs:complexType>
 *   </pre>
 * <p>
 *
 * <b> Example 2: </b> Map a field to a nillable element.
 *   <pre>
 *
 *     //Example: Code fragment
 *     public class USPrice {
 *         &#64;XmlElement(nillable=true)
 *         public java.math.BigDecimal price;
 *     }
 *
 *     &lt;!-- Example: Local XML Schema element -->
 *     &lt;xs:complexType name="USPrice">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="price" type="xs:decimal" nillable="true" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/xs:complexType>
 *   </pre>
 * <p>
 * <b> Example 3: </b> Map a field to a nillable, required element.
 *   <pre>
 *
 *     //Example: Code fragment
 *     public class USPrice {
 *         &#64;XmlElement(nillable=true, required=true)
 *         public java.math.BigDecimal price;
 *     }
 *
 *     &lt;!-- Example: Local XML Schema element -->
 *     &lt;xs:complexType name="USPrice">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="price" type="xs:decimal" nillable="true" minOccurs="1"/>
 *       &lt;/sequence>
 *     &lt;/xs:complexType>
 *   </pre>
 * <p>
 *
 * <p> <b>Example 4: </b>Map a JavaBean property to an XML element
 * with anonymous type.</p>
 * <p>
 * See Example 6 in @{@link XmlType}.
 *
 * <p>
 * <p>
 *  将JavaBean属性映射到从属性名派生的XML元素。
 * 
 *  <p> <b>使用</b> </p>
 * <p>
 *  <tt> @XmlElement </tt>注释可以与以下程序元素一起使用：
 * <ul>
 *  <li> {@link XmlElements}中的<b> </li> <li>非静态,非瞬态字段</li>
 * <p>
 * 
 * </ul>
 * 
 *  用法受以下约束：
 * <ul>
 *  <li>此注释可与以下注释一起使用：{@link XmlID},{@link XmlIDREF},{@link XmlList},{@link XmlSchemaType},{@link XmlValue}
 * ,{@link XmlAttachmentRef},{@链接XmlMimeType},{@link XmlInlineBinaryData},{@link XmlElementWrapper},{@link XmlJavaTypeAdapter}
 *  </li> <li>如果JavaBean属性的类型是数组,索引属性或参数化列表的集合类型,并且此注释与{@link XmlElements}一起使用,则<tt> @ XmlElement.type()
 * </tt>必须是DEFAULT.class,因为集合项类型是已知的。
 *  </li>。
 * </ul>
 * 
 * <p>
 *  当使用@XmlElement注释注释时,JavaBean属性映射到包含类映射到的XML模式复杂类型中的局部元素。
 * 
 * <p>
 *  <b>示例1：</b>将公共非静态非final字段映射到本地元素
 * <pre>
 *  //示例：代码片段public class USPrice {@XmlElement(name ="itemprice")public java.math.BigDecimal price; }}
 * 
 * &lt;!-- Example: Local XML Schema element -->
 * &lt;xs:complexType name="USPrice"/>
 * &lt;xs:sequence>
 * &lt;xs:element name="itemprice" type="xs:decimal" minOccurs="0"/>
 * &lt;/sequence>
 * &lt;/xs:complexType>
 * </pre>
 * <p>
 * 
 *  <b>示例2：</b>将字段映射到nillable元素。
 * <pre>
 * 
 * //示例：代码片段public class USPrice {@XmlElement(nillable = true)public java.math.BigDecimal price; }}
 * 
 * &lt;!-- Example: Local XML Schema element -->
 * &lt;xs:complexType name="USPrice">
 * &lt;xs:sequence>
 * &lt;xs:element name="price" type="xs:decimal" nillable="true" minOccurs="0"/>
 * &lt;/sequence>
 * &lt;/xs:complexType>
 * </pre>
 * <p>
 *  <b>示例3：</b>将字段映射到可填充的必需元素。
 * <pre>
 * 
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({FIELD, METHOD, PARAMETER})
public @interface XmlElement {
    /**
     * Name of the XML Schema element.
     * <p> If the value is "##default", then element name is derived from the
     * JavaBean property name.
     * <p>
     *  //示例：代码片段public class USPrice {@XmlElement(nillable = true,required = true)public java.math.BigDecimal price; }
     * }。
     * 
     * &lt;!-- Example: Local XML Schema element -->
     * &lt;xs:complexType name="USPrice">
     * &lt;xs:sequence>
     * &lt;xs:element name="price" type="xs:decimal" nillable="true" minOccurs="1"/>
     * &lt;/sequence>
     * &lt;/xs:complexType>
     * </pre>
     * <p>
     * 
     *  <p> <b>示例4：</b>将JavaBean属性映射到具有匿名类型的XML元素。</p>
     * <p>
     *  请参见@ {@ link XmlType}中的示例6。
     * 
     * <p>
     */
    String name() default "##default";

    /**
     * Customize the element declaration to be nillable.
     * <p>If nillable() is true, then the JavaBean property is
     * mapped to a XML Schema nillable element declaration.
     * <p>
     *  XML模式元素的名称。 <p>如果值为"## default",则元素名称从JavaBean属性名称派生。
     * 
     */
    boolean nillable() default false;

    /**
     * Customize the element declaration to be required.
     * <p>If required() is true, then Javabean property is mapped to
     * an XML schema element declaration with minOccurs="1".
     * maxOccurs is "1" for a single valued property and "unbounded"
     * for a multivalued property.
     * <p>If required() is false, then the Javabean property is mapped
     * to XML Schema element declaration with minOccurs="0".
     * maxOccurs is "1" for a single valued property and "unbounded"
     * for a multivalued property.
     * <p>
     *  自定义元素声明为nillable。 <p>如果nillable()为true,那么JavaBean属性将映射到XML模式元素声明。
     * 
     */

    boolean required() default false;

    /**
     * XML target namespace of the XML Schema element.
     * <p>
     * If the value is "##default", then the namespace is determined
     * as follows:
     * <ol>
     *  <li>
     *  If the enclosing package has {@link XmlSchema} annotation,
     *  and its {@link XmlSchema#elementFormDefault() elementFormDefault}
     *  is {@link XmlNsForm#QUALIFIED QUALIFIED}, then the namespace of
     *  the enclosing class.
     *
     *  <li>
     *  Otherwise &#39;&#39; (which produces unqualified element in the default
     *  namespace.
     * </ol>
     * <p>
     *  自定义元素声明为必需。 <p>如果required()为true,则将Javabean属性映射到具有minOccurs ="1"的XML模式元素声明。
     * 对于单值属性,maxOccurs为"1",对于多值属性,maxOccurs为"无界"。
     *  <p>如果required()为false,则将Javabean属性映射到具有minOccurs ="0"的XML模式元素声明。
     * 对于单值属性,maxOccurs为"1",对于多值属性,maxOccurs为"无界"。
     * 
     */
    String namespace() default "##default";

    /**
     * Default value of this element.
     *
     * <p>
     * The <pre>'\u0000'</pre> value specified as a default of this annotation element
     * is used as a poor-man's substitute for null to allow implementations
     * to recognize the 'no default value' state.
     * <p>
     *  XML模式元素的XML目标命名空间。
     * <p>
     *  如果值为"## default",则命名空间确定如下：
     * <ol>
     * <li>
     * 如果封装包具有{@link XmlSchema}注释,并且其{@link XmlSchema#elementFormDefault()elementFormDefault}为{@link XmlNsForm#QUALIFIED QUALIFIED}
     * ,则为封闭类的命名空间。
     * 
     * <li>
     *  否则''(它在默认命名空间中产生非限定元素。
     * </ol>
     */
    String defaultValue() default "\u0000";

    /**
     * The Java class being referenced.
     * <p>
     *  此元素的默认值。
     * 
     * <p>
     *  指定为此注记元素的默认值的<pre>'\ u0000'</pre>值用作穷人的null替代,以允许实现识别"无默认值"状态。
     * 
     */
    Class type() default DEFAULT.class;

    /**
     * Used in {@link XmlElement#type()} to
     * signal that the type be inferred from the signature
     * of the property.
     * <p>
     *  被引用的Java类。
     * 
     */
    static final class DEFAULT {}
}
