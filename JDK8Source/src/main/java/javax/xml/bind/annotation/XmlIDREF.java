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
 * Maps a JavaBean property to XML IDREF.
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
 * The <tt>&#64;XmlIDREF</tt> annotation can be used with the following
 * program elements:
 * <ul>
 *   <li> a JavaBean property </li>
 *   <li> non static, non transient field </li>
 * </ul>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p> The usage is subject to the following constraints:
 * <ul>
 *
 *   <li> If the type of the field or property is a collection type,
 *        then the collection item type must contain a property or
 *        field annotated with <tt>&#64;XmlID</tt>.  </li>
 *   <li> If the field or property is single valued, then the type of
 *        the property or field must contain a property or field
 *        annotated with <tt>&#64;XmlID</tt>.
 *        <p>Note: If the collection item type or the type of the
 *        property (for non collection type) is java.lang.Object, then
 *        the instance must contain a property/field annotated with
 *        <tt>&#64;XmlID</tt> attribute.
 *        </li>
 *   <li> This annotation can be used with the following annotations:
 *        {@link XmlElement}, {@link XmlAttribute}, {@link XmlList},
 *        and {@link XmlElements}.</li>
 *
 * </ul>
 * <p><b>Example:</b> Map a JavaBean property to <tt>xs:IDREF</tt>
 *   (i.e. by reference rather than by containment)</p>
 * <pre>
 *
 *   //EXAMPLE: Code fragment
 *   public class Shipping {
 *       &#64;XmlIDREF public Customer getCustomer();
 *       public void setCustomer(Customer customer);
 *       ....
 *    }
 *
 *   &lt;!-- Example: XML Schema fragment -->
 *   &lt;xs:complexType name="Shipping">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="customer" type="xs:IDREF"/>
 *         ....
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 * </pre>
 *
 *
 * <p><b>Example 2: </b> The following is a complete example of
 * containment versus reference.
 *
 * <pre>
 *    // By default, Customer maps to complex type <tt>xs:Customer</tt>
 *    public class Customer {
 *
 *        // map JavaBean property type to <tt>xs:ID</tt>
 *        &#64;XmlID public String getCustomerID();
 *        public void setCustomerID(String id);
 *
 *        // .... other properties not shown
 *    }
 *
 *
 *   // By default, Invoice maps to a complex type <tt>xs:Invoice</tt>
 *   public class Invoice {
 *
 *       // map by reference
 *       &#64;XmlIDREF public Customer getCustomer();
 *       public void setCustomer(Customer customer);
 *
 *      // .... other properties not shown here
 *   }
 *
 *   // By default, Shipping maps to complex type <tt>xs:Shipping</tt>
 *   public class Shipping {
 *
 *       // map by reference
 *       &#64;XmlIDREF public Customer getCustomer();
 *       public void setCustomer(Customer customer);
 *   }
 *
 *   // at least one class must reference Customer by containment;
 *   // Customer instances won't be marshalled.
 *   &#64;XmlElement(name="CustomerData")
 *   public class CustomerData {
 *       // map reference to Customer by containment by default.
 *       public Customer getCustomer();
 *
 *       // maps reference to Shipping by containment by default.
 *       public Shipping getShipping();
 *
 *       // maps reference to Invoice by containment by default.
 *       public Invoice getInvoice();
 *   }
 *
 *   &lt;!-- XML Schema mapping for above code frament -->
 *
 *   &lt;xs:complexType name="Invoice">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="customer" type="xs:IDREF"/>
 *         ....
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 *   &lt;xs:complexType name="Shipping">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="customer" type="xs:IDREF"/>
 *         ....
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 *   &lt;xs:complexType name="Customer">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         ....
 *       &lt;/xs:sequence>
 *       &lt;xs:attribute name="CustomerID" type="xs:ID"/>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 *   &lt;xs:complexType name="CustomerData">
 *     &lt;xs:complexContent>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="customer" type="xs:Customer"/>
 *         &lt;xs:element name="shipping" type="xs:Shipping"/>
 *         &lt;xs:element name="invoice"  type="xs:Invoice"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexContent>
 *   &lt;/xs:complexType>
 *
 *   &lt;xs:element name"customerData" type="xs:CustomerData"/>
 *
 *   &lt;!-- Instance document conforming to the above XML Schema -->
 *    &lt;customerData>
 *       &lt;customer customerID="Alice">
 *           ....
 *       &lt;/customer>
 *
 *       &lt;shipping customer="Alice">
 *           ....
 *       &lt;/shipping>
 *
 *       &lt;invoice customer="Alice">
 *           ....
 *       &lt;/invoice>
 *   &lt;/customerData>
 *
 * </pre>
 *
 * <p><b>Example 3: </b> Mapping List to repeating element of type IDREF
 * <pre>
 *     // Code fragment
 *     public class Shipping {
 *         &#64;XmlIDREF
 *         &#64;XmlElement(name="Alice")
 *             public List customers;
 *     }
 *
 *     &lt;!-- XML schema fragment -->
 *     &lt;xs:complexType name="Shipping">
 *       &lt;xs:sequence>
 *         &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *           &lt;xs:element name="Alice" type="xs:IDREF"/>
 *         &lt;/xs:choice>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 * </pre>
 *
 * <p><b>Example 4: </b> Mapping a List to a list of elements of type IDREF.
 * <pre>
 *     //Code fragment
 *     public class Shipping {
 *         &#64;XmlIDREF
 *         &#64;XmlElements(
 *             &#64;XmlElement(name="Alice", type="Customer.class")
 *              &#64;XmlElement(name="John", type="InternationalCustomer.class")
 *         public List customers;
 *     }
 *
 *     &lt;!-- XML Schema fragment -->
 *     &lt;xs:complexType name="Shipping">
 *       &lt;xs:sequence>
 *         &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *           &lt;xs:element name="Alice" type="xs:IDREF"/>
 *           &lt;xs:element name="John" type="xs:IDREF"/>
 *         &lt;/xs:choice>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 * </pre>
 * <p>
 * <p>
 *  将JavaBean属性映射到XML IDREF。
 * 
 * <p>
 *  为了保持XML序列化之后的XML反序列化的对象图的引用完整性,需要通过引用或包含适当地对对象引用进行编组。
 * 注释<tt> @XmlID </tt>和<tt> @XmlIDREF </tt>一起允许通过包含或引用对JavaBean属性的类型进行自定义映射。
 * 
 *  <p> <b>使用</b> </p> <tt> @XmlIDREF </tt>注释可用于以下程序元素：
 * <ul>
 *  <li>一个JavaBean属性</li> <li>非静态,非瞬态字段</li>
 * </ul>
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 *  <p>使用受以下限制约束：
 * <ul>
 * 
 *  <li>如果字段或属性的类型是集合类型,则集合项类​​型必须包含使用<tt> @XmlID </tt>注释的属性或字段。
 *  </li> <li>如果字段或属性是单值,那么属性或字段的类型必须包含用<tt> @XmlID </tt>注释的属性或字段。
 *  <p>注意：如果集合项类型或属性的类型(对于非集合类型)为java.lang.Object,则实例必须包含用<tt> @XmlID </tt>属性注释的属性/字段。
 * </li>
 * <li>此注释可用于以下注释：{@link XmlElement},{@link XmlAttribute},{@link XmlList}和{@link XmlElements}。</li>
 * 
 * </ul>
 *  <p> <b>示例：</b>将JavaBean属性映射到<tt> xs：IDREF </tt>(即通过引用而不是通过包含)</p>
 * <pre>
 * 
 *  //示例：代码片段public class Shipping {@XmlIDREF public Customer getCustomer(); public void setCustomer(Customer customer); ....}
 * 。
 * 
 * &lt;!-- Example: XML Schema fragment -->
 * &lt;xs:complexType name="Shipping">
 * &lt;xs:complexContent>
 * &lt;xs:sequence>
 * &lt;xs:element name="customer" type="xs:IDREF"/>
 *  ....
 * &lt;/xs:sequence>
 * &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * 
 * </pre>
 * 
 *  <p> <b>示例2：</b>以下是包含与参考的完整示例。
 * 
 * <pre>
 *  //默认情况下,客户映射到复杂类型<tt> xs：Customer </tt> public class Customer {
 * 
 *  //将JavaBean属性类型映射到<tt> xs：ID </tt> @XmlID public String getCustomerID(); public void setCustomerID(S
 * tring id);。
 * 
 *  // ....其他属性未显示}
 * 
 *  //默认情况下,发票会映射到复杂类型<tt> xs：发票</tt> public class Invoice {
 * 
 *  // map by reference @XmlIDREF public Customer getCustomer(); public void setCustomer(Customer custom
 * er);。
 * 
 *  // ....其他属性未显示}
 * 
 *  //默认情况下,Shipping映射到复杂类型<tt> xs：Shipping </tt> public class Shipping {
 * 
 *  // map by reference @XmlIDREF public Customer getCustomer(); public void setCustomer(Customer custom
 * er); }}。
 * 
 *  //至少有一个类必须通过containment引用Customer; //客户实例不会被编组。
 *  @XmlElement(name ="CustomerData")public class CustomerData {//默认情况下通过包含映射对Customer的引用。
 *  public Customer getCustomer();。
 * 
 * //默认情况下通过containment映射参考Shipping。 public Shipping getShipping();
 * 
 *  //默认情况下通过包含将发票映射到发票。 public Invoice getInvoice(); }}
 * 
 * &lt;!-- XML Schema mapping for above code frament -->
 * 
 * &lt;xs:complexType name="Invoice">
 * &lt;xs:complexContent>
 * &lt;xs:sequence>
 * &lt;xs:element name="customer" type="xs:IDREF"/>
 *  ....
 * &lt;/xs:sequence>
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @see XmlID
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlIDREF {}
