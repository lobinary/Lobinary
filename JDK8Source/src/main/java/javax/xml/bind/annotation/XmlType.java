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
 * Maps a class or an enum type to a XML Schema type.
 *
 * <p><b>Usage</b></p>
 * <p> The <tt>@XmlType</tt> annnotation can be used with the following program
 * elements:
 * <ul>
 *   <li> a top level class </li>
 *   <li> an enum type </li>
 * </ul>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <h3> Mapping a Class </h3>
 * <p>
 * A class maps to a XML Schema type. A class is a data container for
 * values represented by properties and fields. A schema type is a
 * data container for values represented by schema components within a
 * schema type's content model (e.g. model groups, attributes etc).
 * <p> To be mapped, a class must either have a public no-arg
 * constructor or a static no-arg factory method. The static factory
 * method can be specified in <tt>factoryMethod()</tt> and
 * <tt>factoryClass()</tt> annotation elements. The static factory
 * method or the no-arg constructor is used during unmarshalling to
 * create an instance of this class. If both are present, the static
 * factory method overrides the no-arg constructor.
 * <p>
 * A class maps to either a XML Schema complex type or a XML Schema simple
 * type. The XML Schema type is derived based on the
 * mapping of JavaBean properties and fields contained within the
 * class. The schema type to which the class is mapped can either be
 * named or anonymous. A class can be mapped to an anonymous schema
 * type by annotating the class with <tt>&#64XmlType(name="")</tt>.
 * <p>
 * Either a global element, local element or a local attribute can be
 * associated with an anonymous type as follows:
 * <ul>
 *   <li><b>global element: </b> A global element of an anonymous
 *      type can be derived by annotating the class with @{@link
 *      XmlRootElement}. See Example 3 below. </li>
 *
 *   <li><b>local element: </b> A JavaBean property that references
 *      a class annotated with @XmlType(name="") and is mapped to the
 *      element associated with the anonymous type. See Example 4
 *      below.</li>
 *
 *   <li><b>attribute: </b> A JavaBean property that references
 *      a class annotated with @XmlType(name="") and is mapped to the
 *      attribute associated with the anonymous type. See Example 5 below. </li>
 * </ul>
 * <b> Mapping to XML Schema Complex Type </b>
 * <ul>
 *   <li>If class is annotated with <tt>@XmlType(name="") </tt>, it
 *   is mapped to an anonymous type otherwise, the class name maps
 *   to a complex type name. The <tt>XmlName()</tt> annotation element
 *   can be used to customize the name.</li>
 *
 *   <li> Properties and fields that are mapped to elements are mapped to a
 *   content model within a complex type. The annotation element
 *   <tt>propOrder()</tt> can be used to customize the content model to be
 *   <tt>xs:all</tt> or <tt>xs:sequence</tt>.  It is used for specifying
 *   the order of XML elements in <tt>xs:sequence</tt>. </li>
 *
 *   <li> Properties and fields can be mapped to attributes within the
 *        complex type.  </li>
 *
 *   <li> The targetnamespace of the XML Schema type can be customized
 *        using the annotation element <tt>namespace()</tt>. </li>
 * </ul>
 *
 * <p>
 * <b> Mapping class to XML Schema simple type </b>
 * <p>
 * A class can be mapped to a XML Schema simple type using the
 * <tt>@XmlValue</tt> annotation. For additional details and examples,
 * see @{@link XmlValue} annotation type.
 * <p>
 * The following table shows the mapping of the class to a XML Schema
 * complex type or simple type. The notational symbols used in the table are:
 * <ul>
 *   <li> ->    : represents a mapping </li>
 *   <li> [x]+  : one or more occurances of x </li>
 *   <li> [ <tt>@XmlValue</tt> property ]: JavaBean property annotated with
 *         <tt>@XmlValue</tt></li>
 *   <li> X     : don't care
 * </ul>
 * <blockquote>
 *   <table border="1" cellpadding="4" cellspacing="3">
 *     <tbody>
 *       <tr>
 *         <td><b>Target</b></td>
 *         <td><b>propOrder</b></td>
 *         <td><b>ClassBody</b></td>
 *         <td><b>ComplexType</b></td>
 *         <td><b>SimpleType</b></td>
 *       </tr>
 *
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>{}</td>
 *         <td>[property]+ -> elements</td>
 *         <td>complexcontent<br>xs:all</td>
 *         <td> </td>
 *       </tr>
 *
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>non empty</td>
 *         <td>[property]+ -> elements</td>
 *         <td>complexcontent<br>xs:sequence</td>
 *         <td> </td>
 *       </tr>
 *
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>X</td>
 *         <td>no property -> element</td>
 *         <td>complexcontent<br>empty sequence</td>
 *         <td> </td>
 *       </tr>
 *
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>X</td>
 *         <td>1 [ <tt>@XmlValue</tt> property] && <br> [property]+
 *             ->attributes</td>
 *         <td>simplecontent</td>
 *         <td> </td>
 *       </tr>
 *
 *       <tr valign="top">
 *         <td>Class</td>
 *         <td>X</td>
 *         <td>1 [ <tt>@XmlValue</tt> property ]&& <br> no properties
 *         -> attribute</td>
 *         <td> </td>
 *         <td>simpletype</td>
 *         <td> </td>
 *       </tr>
 *     </tbody>
 *   </table>
 * </blockquote>
 *
 * <h3> Mapping an enum type </h3>
 *
 * An enum type maps to a XML schema simple type with enumeration
 * facets. The following annotation elements are ignored since they
 * are not meaningful: <tt>propOrder()</tt> , <tt>factoryMethod()</tt> ,
 * <tt>factoryClass()</tt> .
 *
 *  <h3> Usage with other annotations </h3>
 * <p> This annotation can be used with the following annotations:
 * {@link XmlRootElement}, {@link XmlAccessorOrder}, {@link XmlAccessorType},
 * {@link XmlEnum}. However, {@link
 * XmlAccessorOrder} and {@link XmlAccessorType} are ignored when this
 * annotation is used on an enum type.
 *
 * <p> <b> Example 1: </b> Map a class to a complex type with
 *   xs:sequence with a customized ordering of JavaBean properties.
 * </p>
 *
 * <pre>
 *   &#64;XmlType(propOrder={"street", "city" , "state", "zip", "name" })
 *   public class USAddress {
 *     String getName() {..};
 *     void setName(String) {..};
 *
 *     String getStreet() {..};
 *     void setStreet(String) {..};
 *
 *     String getCity() {..};
 *     void setCity(String) {..};
 *
 *     String getState() {..};
 *     void setState(String) {..};
 *
 *     java.math.BigDecimal getZip() {..};
 *     void setZip(java.math.BigDecimal) {..};
 *   }
 *
 *   &lt;!-- XML Schema mapping for USAddress -->
 *   &lt;xs:complexType name="USAddress">
 *     &lt;xs:sequence>
 *       &lt;xs:element name="street" type="xs:string"/>
 *       &lt;xs:element name="city" type="xs:string"/>
 *       &lt;xs:element name="state" type="xs:string"/>
 *       &lt;xs:element name="zip" type="xs:decimal"/>
 *       &lt;xs:element name="name" type="xs:string"/>
 *     &lt;/xs:all>
 *   &lt;/xs:complexType>
 * </pre>
 * <p> <b> Example 2: </b> Map a class to a complex type with
 *     xs:all </p>
 * <pre>
 * &#64;XmlType(propOrder={})
 * public class USAddress { ...}
 *
 * &lt;!-- XML Schema mapping for USAddress -->
 * &lt;xs:complexType name="USAddress">
 *   &lt;xs:all>
 *     &lt;xs:element name="name" type="xs:string"/>
 *     &lt;xs:element name="street" type="xs:string"/>
 *     &lt;xs:element name="city" type="xs:string"/>
 *     &lt;xs:element name="state" type="xs:string"/>
 *     &lt;xs:element name="zip" type="xs:decimal"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 *</pre>
 * <p> <b> Example 3: </b> Map a class to a global element with an
 * anonymous type.
 * </p>
 * <pre>
 *   &#64;XmlRootElement
 *   &#64;XmlType(name="")
 *   public class USAddress { ...}
 *
 *   &lt;!-- XML Schema mapping for USAddress -->
 *   &lt;xs:element name="USAddress">
 *     &lt;xs:complexType>
 *       &lt;xs:sequence>
 *         &lt;xs:element name="name" type="xs:string"/>
 *         &lt;xs:element name="street" type="xs:string"/>
 *         &lt;xs:element name="city" type="xs:string"/>
 *         &lt;xs:element name="state" type="xs:string"/>
 *         &lt;xs:element name="zip" type="xs:decimal"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 *   &lt;/xs:element>
 * </pre>
 *
 * <p> <b> Example 4: </b> Map a property to a local element with
 * anonmyous type.
 * <pre>
 *   //Example: Code fragment
 *   public class Invoice {
 *       USAddress addr;
 *           ...
 *       }
 *
 *   &#64;XmlType(name="")
 *   public class USAddress { ... }
 *   }
 *
 *   &lt;!-- XML Schema mapping for USAddress -->
 *   &lt;xs:complexType name="Invoice">
 *     &lt;xs:sequence>
 *       &lt;xs:element name="addr">
 *         &lt;xs:complexType>
 *           &lt;xs:element name="name", type="xs:string"/>
 *           &lt;xs:element name="city", type="xs:string"/>
 *           &lt;xs:element name="city" type="xs:string"/>
 *           &lt;xs:element name="state" type="xs:string"/>
 *           &lt;xs:element name="zip" type="xs:decimal"/>
 *         &lt;/xs:complexType>
 *       ...
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * </pre>
 *
 * <p> <b> Example 5: </b> Map a property to an attribute with
 * anonymous type.
 *
 * <pre>
 *
 *     //Example: Code fragment
 *     public class Item {
 *         public String name;
 *         &#64;XmlAttribute
 *         public USPrice price;
 *     }
 *
 *     // map class to anonymous simple type.
 *     &#64;XmlType(name="")
 *     public class USPrice {
 *         &#64;XmlValue
 *         public java.math.BigDecimal price;
 *     }
 *
 *     &lt;!-- Example: XML Schema fragment -->
 *     &lt;xs:complexType name="Item">
 *       &lt;xs:sequence>
 *         &lt;xs:element name="name" type="xs:string"/>
 *         &lt;xs:attribute name="price">
 *           &lt;xs:simpleType>
 *             &lt;xs:restriction base="xs:decimal"/>
 *           &lt;/xs:simpleType>
 *         &lt;/xs:attribute>
 *       &lt;/xs:sequence>
 *     &lt;/xs:complexType>
 * </pre>
 *
 *  <p> <b> Example 6: </b> Define a factoryClass and factoryMethod
 *
 * <pre>
 *      &#64;XmlType(name="USAddressType", factoryClass=USAddressFactory.class,
 *      factoryMethod="getUSAddress")
 *      public class USAddress {
 *
 *          private String city;
 *          private String name;
 *          private String state;
 *          private String street;
 *          private int    zip;
 *
 *      public USAddress(String name, String street, String city,
 *          String state, int zip) {
 *          this.name = name;
 *          this.street = street;
 *          this.city = city;
 *          this.state = state;
 *          this.zip = zip;
 *      }
 *  }
 *
 *  public class USAddressFactory {
 *      public static USAddress getUSAddress(){
 *       return new USAddress("Mark Baker", "23 Elm St",
 *          "Dayton", "OH", 90952);
 *  }
 *
 * </pre>
 *
 *  <p> <b> Example 7: </b> Define factoryMethod and use the default factoryClass
 *
 * <pre>
 *      &#64;XmlType(name="USAddressType", factoryMethod="getNewInstance")
 *      public class USAddress {
 *
 *          private String city;
 *          private String name;
 *          private String state;
 *          private String street;
 *          private int    zip;
 *
 *          private USAddress() {}
 *
 *          public static USAddress getNewInstance(){
 *              return new USAddress();
 *          }
 *      }
 * </pre>
 *
 * <p>
 * <p>
 *  将类或枚举类型映射到XML模式类型
 * 
 *  <p> <b>使用</b> </p> <p> <tt> @XmlType </tt>注释可用于以下程序元素：
 * <ul>
 *  <li>顶级类</li> <li>枚举类型</li>
 * </ul>
 * 
 *  <p>有关其他常见信息,请参阅javaxxmlbindpackage javadoc中的"包规范"。</p>
 * 
 *  <h3>映射类</h3>
 * <p>
 * 类映射到XML模式类型类是由属性和字段表示的值的数据容器模式类型是由模式类型的内容模型中的模式组件(例如模型组,属性等)表示的值的数据容器<p >要映射,类必须具有公共无参arg构造函数或静态无arg工
 * 厂方法。
 * 静态工厂方法可以在<tt> factoryMethod()</tt>和<tt> factoryClass / tt>注释元素在解组合期间使用静态工厂方法或no-arg构造函数来创建此类的实例如果两者都存
 * 在,则静态工厂方法将覆盖no-arg构造函数。
 * <p>
 * 类映射到XML模式复杂类型或XML模式简单类型基于JavaBean属性和类中包含的字段的映射来推导XML模式类型类映射到的模式类型可以是命名的或匿名的类可以通过使用<tt>&#64XmlType(nam
 * e ="")注释类来映射到匿名模式类型</tt>。
 * <p>
 *  全局元素,局部元素或局部属性可以与匿名类型相关联,如下所示：
 * <ul>
 *  <li> <b>全局元素</b>：匿名类型的全局元素可以通过使用@ {@ link XmlRootElement}注释类来获得。参见下面的示例3 </li>
 * 
 * <li> <b> local element：</b>一个JavaBean属性,它引用以@XmlType(name ="")注释的类,并映射到与匿名类型相关联的元素。请参阅下面的示例4 </li>
 * 
 *  <li> <b> attribute：</b>引用以@XmlType(name ="")注释的类并映射到与匿名类型相关联的属性的JavaBean属性请参阅下面的示例5 </li>
 * </ul>
 *  <b>映射到XML模式复杂类型</b>
 * <ul>
 *  <li>如果类用<tt> @XmlType(name ="")</tt>注释,则它将映射到匿名类型,否则类名称将映射到复杂类型名称<tt> XmlName / tt>注释元素可用于自定义名称</li>
 * 。
 * 
 * <li>映射到元素的属性和字段映射到复杂类型中的内容模型注释元素<tt> propOrder()</tt>可用于将内容模型自定义为<tt> xs：all </tt>或<tt> xs：sequence </tt>
 * 用于指定<tt> xs：sequence </tt> </li>。
 * 
 *  <li>属性和字段可以映射到复杂类型</li>中的属性
 * 
 *  <li> XML模式类型的targetnamespace可以使用注释元素<tt>命名空间()</tt> </li>
 * </ul>
 * 
 * <p>
 *  <b>将类映射到XML模式简单类型</b>
 * <p>
 *  类可以使用<tt> @XmlValue </tt>注释映射到XML模式简单类型有关其他详细信息和示例,请参阅@ {@ link XmlValue}注释类型
 * <p>
 * 下表显示了类到XML模式复杂类型或简单类型的映射表中使用的符号符号是：
 * <ul>
 *  <li>  - >：表示映射</li> <li> [x] +：一次或多次出现x </li> <li> [<tt> @XmlValue </tt>属性]：JavaBean属性注释与<tt> @XmlVa
 * lue </tt> </li> <li> X：不在乎。
 * </ul>
 * <blockquote>
 * <table border="1" cellpadding="4" cellspacing="3">
 * <tbody>
 * <tr>
 *  <td> <b>目标</b> </td> <td> <b> propOrder </b> </td> <td> <b> ClassBody </b> </td> <td> <b > ComplexTy
 * pe </b> </td> <td> <b> SimpleType </b> </td>。
 * </tr>
 * 
 * <tr valign="top">
 *  <td>类</td> <td> {} </td> <td> [property] +  - >元素</td> <td> complexcontent <br> xs：all </td> <td> </td>
 * 。
 * </tr>
 * 
 * <tr valign="top">
 *  <td>类</td> <td>非空</td> <td> [property] +  - >元素</td> <td> complexcontent <br> xs：sequence </td> <td>
 *  td>。
 * </tr>
 * 
 * <tr valign="top">
 * <td>类</td> <td> X </td> <td>无属性 - >元素</td> <td> complexcontent <br>空序列</td> <td> </td>
 * </tr>
 * 
 * <tr valign="top">
 *  <td> Class </td> <td> X </td> <td> 1 [<tt> @XmlValue </tt>属性] && <br> [property] +  - >属性</td> <td> 
 * simplecontent </td> <td> </td>。
 * </tr>
 * 
 * <tr valign="top">
 *  <td>类</td> <td> X </td> <td> 1 [<tt> @XmlValue </tt>属性] && <br>无属性 - >属性</td> <td> td> <td> simplety
 * pe </td> <td> </td>。
 * </tr>
 * </tbody>
 * </table>
 * </blockquote>
 * 
 *  <h3>映射枚举类型</h3>
 * 
 *  枚举类型映射到具有枚举面的XML模式简单类型以下注释元素被忽略,因为它们无意义：<tt> propOrder()</tt>,<tt> factoryMethod()</tt>,<tt> factory
 * Class()</tt>。
 * 
 * <h3>与其他注释的用法</h3> <p>此注释可用于以下注释：{@link XmlRootElement},{@link XmlAccessorOrder},{@link XmlAccessorType}
 * ,{@link XmlEnum} @link XmlAccessorOrder}和{@link XmlAccessorType}将在枚举类型中使用此注释时被忽略。
 * 
 *  <p> <b>示例1：</b>将类映射到具有xs：sequence的复杂类型,并具有JavaBean属性的自定义排序
 * </p>
 * 
 * <pre>
 *  @XmlType(propOrder = {"street","city","state","zip","name"})public class USAddress {String getName(){}
 * ; void setName(String){};。
 * 
 *  String getStreet(){}; void setStreet(String){};
 * 
 *  String getCity(){}; void setCity(String){};
 * 
 *  String getState(){}; void setState(String){};
 * 
 * javamathBigDecimal getZip(){}; void setZip(javamathBigDecimal){}; }}
 * 
 * &lt;!-- XML Schema mapping for USAddress -->
 * &lt;xs:complexType name="USAddress">
 * &lt;xs:sequence>
 * &lt;xs:element name="street" type="xs:string"/>
 * &lt;xs:element name="city" type="xs:string"/>
 * &lt;xs:element name="state" type="xs:string"/>
 * &lt;xs:element name="zip" type="xs:decimal"/>
 * &lt;xs:element name="name" type="xs:string"/>
 * &lt;/xs:all>
 * &lt;/xs:complexType>
 * </pre>
 *  <p> <b>示例2：</b>将类映射到xs：all </p>的复杂类型
 * <pre>
 *  @XmlType(propOrder = {})public class USAddress {}
 * 
 * &lt;!-- XML Schema mapping for USAddress -->
 * &lt;xs:complexType name="USAddress">
 * &lt;xs:all>
 * &lt;xs:element name="name" type="xs:string"/>
 * &lt;xs:element name="street" type="xs:string"/>
 * &lt;xs:element name="city" type="xs:string"/>
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @see XmlElement
 * @see XmlAttribute
 * @see XmlValue
 * @see XmlSchema
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({TYPE})
public @interface XmlType {
    /**
     * Name of the XML Schema type which the class is mapped.
     * <p>
     * &lt;xs:element name="state" type="xs:string"/>
     * &lt;xs:element name="zip" type="xs:decimal"/>
     * &lt;/xs:sequence>
     * &lt;/xs:complexType>
     * /pre>
     *  <p> <b>示例3：</b>将类映射到具有匿名类型的全局元素
     * </p>
     * <pre>
     *  @XmlRootElement @XmlType(name ="")public class USAddress {}
     * 
     * &lt;!-- XML Schema mapping for USAddress -->
     * &lt;xs:element name="USAddress">
     * &lt;xs:complexType>
     * &lt;xs:sequence>
     * &lt;xs:element name="name" type="xs:string"/>
     * &lt;xs:element name="street" type="xs:string"/>
     * &lt;xs:element name="city" type="xs:string"/>
     * &lt;xs:element name="state" type="xs:string"/>
     * &lt;xs:element name="zip" type="xs:decimal"/>
     * &lt;/xs:sequence>
     * &lt;/xs:complexType>
     * &lt;/xs:element>
     * </pre>
     * 
     *  <p> <b>示例4：</b>将属性映射到具有anonmyous类型的本地元素
     * <pre>
     *  //示例：代码片段public class Invoice {USAddress addr; }}
     * 
     *  @XmlType(name ="")public class USAddress {}}
     * 
     * &lt;!-- XML Schema mapping for USAddress -->
     * &lt;xs:complexType name="Invoice">
     * &lt;xs:sequence>
     * &lt;xs:element name="addr">
     * &lt;xs:complexType>
     * &lt;xs:element name="name", type="xs:string"/>
     * &lt;xs:element name="city", type="xs:string"/>
     * &lt;xs:element name="city" type="xs:string"/>
     * &lt;xs:element name="state" type="xs:string"/>
     * &lt;xs:element name="zip" type="xs:decimal"/>
     * &lt;/xs:complexType>
     *  
     * &lt;/xs:sequence>
     * &lt;/xs:complexType>
     * </pre>
     * 
     *  <p> <b>示例5：</b>将属性映射到具有匿名类型的属性
     * 
     * <pre>
     * 
     *  //示例：代码片段public class Item {public String name; @XmlAttribute public USPrice price; }}
     * 
     * //将类映射到匿名简单类型@XmlType(name ="")public class USPrice {@XmlValue public javamathBigDecimal price; }}
     * 
     * &lt;!-- Example: XML Schema fragment -->
     * &lt;xs:complexType name="Item">
     * &lt;xs:sequence>
     * &lt;xs:element name="name" type="xs:string"/>
     * &lt;xs:attribute name="price">
     * &lt;xs:simpleType>
     * &lt;xs:restriction base="xs:decimal"/>
     * &lt;/xs:simpleType>
     * &lt;/xs:attribute>
     * &lt;/xs:sequence>
     * &lt;/xs:complexType>
     */
    String name() default "##default" ;

    /**
     * Specifies the order for XML Schema elements when class is
     * mapped to a XML Schema complex type.
     *
     * <p> Refer to the table for how the propOrder affects the
     * mapping of class </p>
     *
     * <p> The propOrder is a list of names of JavaBean properties in
     *     the class. Each name in the list is the name of a Java
     *     identifier of the JavaBean property. The order in which
     *     JavaBean properties are listed is the order of XML Schema
     *     elements to which the JavaBean properties are mapped. </p>
     * <p> All of the JavaBean properties being mapped to XML Schema elements
     *     must be listed.
     * <p> A JavaBean property or field listed in propOrder must not
     *     be transient or annotated with <tt>@XmlTransient</tt>.
     * <p> The default ordering of JavaBean properties is determined
     *     by @{@link XmlAccessorOrder}.
     * <p>
     * </pre>
     * 
     *  <p> <b>示例6：</b>定义factoryClass和factoryMethod
     * 
     * <pre>
     *  @XmlType(name ="USAddressType",factoryClass = USAddressFactoryclass,factoryMethod ="getUSAddress")pu
     * blic class USAddress {。
     * 
     *  private String city; private String name; private String state; private String street; private int z
     * ip;。
     * 
     *  public USAddress(String name,String street,String city,String state,int zip){thisname = name; thisstreet = street; thiscity = city; thisstate = state; thiszip = zip; }
     * }。
     * 
     *  public class USAddressFactory {public static USAddress getUSAddress(){return new USAddress("Mark Ba​​ker","23 Elm St","Dayton","OH",90952); }
     * }。
     * 
     * </pre>
     * 
     * <p> <b>示例7：</b>定义factoryMethod并使用默认factoryClass
     * 
     * <pre>
     *  @XmlType(name ="USAddressType",factoryMethod ="getNewInstance")public class USAddress {
     * 
     *  private String city; private String name; private String state; private String street; private int z
     * ip;。
     * 
     *  private USAddress(){}
     * 
     *  public static USAddress getNewInstance(){return new USAddress(); }}
     * </pre>
     * 
     */
    String[] propOrder() default {""};

    /**
     * Name of the target namespace of the XML Schema type. By
     * default, this is the target namespace to which the package
     * containing the class is mapped.
     * <p>
     *  该类所映射的XML模式类型的名称
     * 
     */
    String namespace() default "##default" ;

    /**
     * Class containing a no-arg factory method for creating an
     * instance of this class. The default is this class.
     *
     * <p>If <tt>factoryClass</tt> is DEFAULT.class and
     * <tt>factoryMethod</tt> is "", then there is no static factory
     * method.
     *
     * <p>If <tt>factoryClass</tt> is DEFAULT.class and
     * <tt>factoryMethod</tt> is not "", then
     * <tt>factoryMethod</tt> is the name of a static factory method
     * in this class.
     *
     * <p>If <tt>factoryClass</tt> is not DEFAULT.class, then
     * <tt>factoryMethod</tt> must not be "" and must be the name of
     * a static factory method specified in <tt>factoryClass</tt>.
     * <p>
     *  当类映射到XML模式复杂类型时,指定XML模式元素的顺序
     * 
     *  <p>请参阅表格,了解propOrder如何影响类</p>的映射
     * 
     * <p> propOrder是类中JavaBean属性的名称列表中的每个名称都是JavaBean属性的Java标识符的名称。
     * 列出JavaBean属性的顺序是XML模式元素的顺序, JavaBean属性映射</p> <p>必须列出映射到XML模式元素的所有JavaBean属性<p>在propOrder中列出的JavaBean属
     * 性或字段不能为短暂的或注释为<tt> @XmlTransient < tt> <p> JavaBean属性的默认顺序由@ {@ link XmlAccessorOrder}。
     * <p> propOrder是类中JavaBean属性的名称列表中的每个名称都是JavaBean属性的Java标识符的名称。
     * 
     */
    Class factoryClass() default DEFAULT.class;

    /**
     * Used in {@link XmlType#factoryClass()} to
     * signal that either factory mehod is not used or
     * that it's in the class with this {@link XmlType} itself.
     * <p>
     *  XML模式类型的目标命名空间的名称默认情况下,这是包含该类的包映射到的目标命名空间
     * 
     */
    static final class DEFAULT {}

    /**
     * Name of a no-arg factory method in the class specified in
     * <tt>factoryClass</tt> factoryClass().
     *
     * <p>
     * 包含用于创建此类的实例的无参数工厂方法的类默认是此类
     * 
     *  <p>如果<tt> factoryClass </tt>是DEFAULTclass,<tt> factoryMethod </tt>是"",则没有静态工厂方法
     * 
     *  <p>如果<tt> factoryClass </tt>是DEFAULTclass且<tt> factoryMethod </tt>不是"",则<tt> factoryMethod </tt>是此类中
     * 的静态工厂方法的名称。
     * 
     *  <p>如果<tt> factoryClass </tt>不是DEFAULTclass,则<tt> factoryMethod </tt>不能为"",并且必须是<tt> factoryClass </tt>
     */
    String factoryMethod() default "";
}
