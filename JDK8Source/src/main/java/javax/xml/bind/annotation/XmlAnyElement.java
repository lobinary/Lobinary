/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Maps a JavaBean property to XML infoset representation and/or JAXB element.
 *
 * <p>
 * This annotation serves as a "catch-all" property while unmarshalling
 * xml content into a instance of a JAXB annotated class. It typically
 * annotates a multi-valued JavaBean property, but it can occur on
 * single value JavaBean property. During unmarshalling, each xml element
 * that does not match a static &#64;XmlElement or &#64;XmlElementRef
 * annotation for the other JavaBean properties on the class, is added to this
 * "catch-all" property.
 *
 * <p>
 * <h2>Usages:</h2>
 * <pre>
 * &#64;XmlAnyElement
 * public {@link Element}[] others;
 *
 * // Collection of {@link Element} or JAXB elements.
 * &#64;XmlAnyElement(lax="true")
 * public {@link Object}[] others;
 *
 * &#64;XmlAnyElement
 * private List&lt;{@link Element}> nodes;
 *
 * &#64;XmlAnyElement
 * private {@link Element} node;
 * </pre>
 *
 * <h2>Restriction usage constraints</h2>
 * <p>
 * This annotation is mutually exclusive with
 * {@link XmlElement}, {@link XmlAttribute}, {@link XmlValue},
 * {@link XmlElements}, {@link XmlID}, and {@link XmlIDREF}.
 *
 * <p>
 * There can be only one {@link XmlAnyElement} annotated JavaBean property
 * in a class and its super classes.
 *
 * <h2>Relationship to other annotations</h2>
 * <p>
 * This annotation can be used with {@link XmlJavaTypeAdapter}, so that users
 * can map their own data structure to DOM, which in turn can be composed
 * into XML.
 *
 * <p>
 * This annotation can be used with {@link XmlMixed} like this:
 * <pre>
 * // List of java.lang.String or DOM nodes.
 * &#64;XmlAnyElement &#64;XmlMixed
 * List&lt;Object> others;
 * </pre>
 *
 *
 * <h2>Schema To Java example</h2>
 *
 * The following schema would produce the following Java class:
 * <pre>
 * &lt;xs:complexType name="foo">
 *   &lt;xs:sequence>
 *     &lt;xs:element name="a" type="xs:int" />
 *     &lt;xs:element name="b" type="xs:int" />
 *     &lt;xs:any namespace="##other" processContents="lax" minOccurs="0" maxOccurs="unbounded" />
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 *
 * <pre>
 * class Foo {
 *   int a;
 *   int b;
 *   &#64;{@link XmlAnyElement}
 *   List&lt;Element> any;
 * }
 * </pre>
 *
 * It can unmarshal instances like
 *
 * <pre>
 * &lt;foo xmlns:e="extra">
 *   &lt;a>1</a>
 *   &lt;e:other />  // this will be bound to DOM, because unmarshalling is orderless
 *   &lt;b>3</b>
 *   &lt;e:other />
 *   &lt;c>5</c>     // this will be bound to DOM, because the annotation doesn't remember namespaces.
 * &lt;/foo>
 * </pre>
 *
 *
 *
 * The following schema would produce the following Java class:
 * <pre>
 * &lt;xs:complexType name="bar">
 *   &lt;xs:complexContent>
 *   &lt;xs:extension base="foo">
 *     &lt;xs:sequence>
 *       &lt;xs:element name="c" type="xs:int" />
 *       &lt;xs:any namespace="##other" processContents="lax" minOccurs="0" maxOccurs="unbounded" />
 *     &lt;/xs:sequence>
 *   &lt;/xs:extension>
 * &lt;/xs:complexType>
 * </pre>
 *
 * <pre>
 * class Bar extends Foo {
 *   int c;
 *   // Foo.getAny() also represents wildcard content for type definition bar.
 * }
 * </pre>
 *
 *
 * It can unmarshal instances like
 *
 * <pre>
 * &lt;bar xmlns:e="extra">
 *   &lt;a>1</a>
 *   &lt;e:other />  // this will be bound to DOM, because unmarshalling is orderless
 *   &lt;b>3</b>
 *   &lt;e:other />
 *   &lt;c>5</c>     // this now goes to Bar.c
 *   &lt;e:other />  // this will go to Foo.any
 * &lt;/bar>
 * </pre>
 *
 *
 *
 *
 * <h2>Using {@link XmlAnyElement} with {@link XmlElementRef}</h2>
 * <p>
 * The {@link XmlAnyElement} annotation can be used with {@link XmlElementRef}s to
 * designate additional elements that can participate in the content tree.
 *
 * <p>
 * The following schema would produce the following Java class:
 * <pre>
 * &lt;xs:complexType name="foo">
 *   &lt;xs:choice maxOccurs="unbounded" minOccurs="0">
 *     &lt;xs:element name="a" type="xs:int" />
 *     &lt;xs:element name="b" type="xs:int" />
 *     &lt;xs:any namespace="##other" processContents="lax" />
 *   &lt;/xs:choice>
 * &lt;/xs:complexType>
 * </pre>
 *
 * <pre>
 * class Foo {
 *   &#64;{@link XmlAnyElement}(lax="true")
 *   &#64;{@link XmlElementRefs}({
 *     &#64;{@link XmlElementRef}(name="a", type="JAXBElement.class")
 *     &#64;{@link XmlElementRef}(name="b", type="JAXBElement.class")
 *   })
 *   {@link List}&lt;{@link Object}> others;
 * }
 *
 * &#64;XmlRegistry
 * class ObjectFactory {
 *   ...
 *   &#64;XmlElementDecl(name = "a", namespace = "", scope = Foo.class)
 *   {@link JAXBElement}&lt;Integer> createFooA( Integer i ) { ... }
 *
 *   &#64;XmlElementDecl(name = "b", namespace = "", scope = Foo.class)
 *   {@link JAXBElement}&lt;Integer> createFooB( Integer i ) { ... }
 * </pre>
 *
 * It can unmarshal instances like
 *
 * <pre>
 * &lt;foo xmlns:e="extra">
 *   &lt;a>1</a>     // this will unmarshal to a {@link JAXBElement} instance whose value is 1.
 *   &lt;e:other />  // this will unmarshal to a DOM {@link Element}.
 *   &lt;b>3</b>     // this will unmarshal to a {@link JAXBElement} instance whose value is 1.
 * &lt;/foo>
 * </pre>
 *
 *
 *
 *
 * <h2>W3C XML Schema "lax" wildcard emulation</h2>
 * The lax element of the annotation enables the emulation of the "lax" wildcard semantics.
 * For example, when the Java source code is annotated like this:
 * <pre>
 * &#64;{@link XmlRootElement}
 * class Foo {
 *   &#64;XmlAnyElement(lax=true)
 *   public {@link Object}[] others;
 * }
 * </pre>
 * then the following document will unmarshal like this:
 * <pre>
 * &lt;foo>
 *   &lt;unknown />
 *   &lt;foo />
 * &lt;/foo>
 *
 * Foo foo = unmarshal();
 * // 1 for 'unknown', another for 'foo'
 * assert foo.others.length==2;
 * // 'unknown' unmarshals to a DOM element
 * assert foo.others[0] instanceof Element;
 * // because of lax=true, the 'foo' element eagerly
 * // unmarshals to a Foo object.
 * assert foo.others[1] instanceof Foo;
 * </pre>
 *
 * <p>
 *  将JavaBean属性映射到XML信息集表示和/或JAXB元素
 * 
 * <p>
 *  此注释用作"全部"属性,同时将xml内容解组成JAXB注释类的实例它通常注释多值JavaBean属性,但它可以发生在单值JavaBean属性在解组合期间,每个xml元素不匹配对类的其他JavaBean
 * 属性的静态@XmlElement或@XmlElementRef注释,被添加到此"catch-all"属性。
 * 
 * <p>
 *  <h2>用法：</h2>
 * <pre>
 *  @XmlAnyElement public {@link Element} [] others;
 * 
 * // {@link Element}或JAXB元素的集合@XmlAnyElement(lax ="true")public {@link Object} [] others;
 * 
 *  @XmlAnyElement private List&lt; {@ link Element}>节点;
 * 
 *  @XmlAnyElement private {@link Element} node;
 * </pre>
 * 
 *  <h2>限制使用限制</h2>
 * <p>
 *  此注释与{@link XmlElement},{@link XmlAttribute},{@link XmlValue},{@link XmlElements},{@link XmlID}和{@link XmlIDREF}
 * 互斥。
 * 
 * <p>
 *  在类中只能有一个{@link XmlAnyElement}注释的JavaBean属性及其超类
 * 
 *  <h2>与其他注释的关系</h2>
 * <p>
 *  此注释可以与{@link XmlJavaTypeAdapter}一起使用,以便用户可以将自己的数据结构映射到DOM,而DOM又可以组成XML
 * 
 * <p>
 *  此注释可以与{@link XmlMixed}一起使用,如下所示：
 * <pre>
 * // javalangString或DOM节点的列表@XmlAnyElement @XmlMixed List&lt; Object> others;
 * </pre>
 * 
 *  <h2>模式到Java示例</h2>
 * 
 *  以下模式将生成以下Java类：
 * <pre>
 * &lt;xs:complexType name="foo">
 * &lt;xs:sequence>
 * &lt;xs:element name="a" type="xs:int" />
 * &lt;xs:element name="b" type="xs:int" />
 * &lt;xs:any namespace="##other" processContents="lax" minOccurs="0" maxOccurs="unbounded" />
 * &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 * 
 * <pre>
 *  class Foo {int a; int b; @ {@ link XmlAnyElement} List&lt; Element> any; }}
 * </pre>
 * 
 *  它可以解组实例
 * 
 * <pre>
 * &lt;foo xmlns:e="extra">
 *  &lt; a> 1 </a>&lt; e：other /> //这将绑定到DOM,因为取消编组是无序的&lt; b> 3 </b>
 * &lt;e:other />
 *  &lt; c> 5 </c> //这将绑定到DOM,因为注释不记住命名空间
 * &lt;/foo>
 * </pre>
 * 
 *  以下模式将生成以下Java类：
 * <pre>
 * &lt;xs:complexType name="bar">
 * &lt;xs:complexContent>
 * &lt;xs:extension base="foo">
 * &lt;xs:sequence>
 * &lt;xs:element name="c" type="xs:int" />
 * &lt;xs:any namespace="##other" processContents="lax" minOccurs="0" maxOccurs="unbounded" />
 * &lt;/xs:sequence>
 * &lt;/xs:extension>
 * &lt;/xs:complexType>
 * </pre>
 * 
 * <pre>
 *  class Bar extends Foo {int c; // FoogetAny()也代表类型定义bar的通配符内容}
 * </pre>
 * 
 *  它可以解组实例
 * 
 * <pre>
 * 
 * @author Kohsuke Kawaguchi
 * @since JAXB2.0
 */
@Retention(RUNTIME)
@Target({FIELD,METHOD})
public @interface XmlAnyElement {

    /**
     * Controls the unmarshaller behavior when it sees elements
     * known to the current {@link JAXBContext}.
     *
     * <h3>When false</h3>
     * <p>
     * If false, all the elements that match the property will be unmarshalled
     * to DOM, and the property will only contain DOM elements.
     *
     * <h3>When true</h3>
     * <p>
     * If true, when an element matches a property marked with {@link XmlAnyElement}
     * is known to {@link JAXBContext} (for example, there's a class with
     * {@link XmlRootElement} that has the same tag name, or there's
     * {@link XmlElementDecl} that has the same tag name),
     * the unmarshaller will eagerly unmarshal this element to the JAXB object,
     * instead of unmarshalling it to DOM. Additionally, if the element is
     * unknown but it has a known xsi:type, the unmarshaller eagerly unmarshals
     * the element to a {@link JAXBElement}, with the unknown element name and
     * the JAXBElement value is set to an instance of the JAXB mapping of the
     * known xsi:type.
     *
     * <p>
     * As a result, after the unmarshalling, the property can become heterogeneous;
     * it can have both DOM nodes and some JAXB objects at the same time.
     *
     * <p>
     * This can be used to emulate the "lax" wildcard semantics of the W3C XML Schema.
     * <p>
     * &lt;bar xmlns:e="extra">
     * &lt; a> 1 </a>&lt; e：other /> //这将绑定到DOM,因为取消编组是无序的&lt; b> 3 </b>
     * &lt;e:other />
     *  &lt; c> 5 </c> //现在转到Barc&lt; e：other /> //这将转到Fooany
     * &lt;/bar>
     * </pre>
     * 
     *  <h2>使用{@link XmlAnyElement}与{@link XmlElementRef} </h2>
     * <p>
     *  {@link XmlAnyElement}注释可以与{@link XmlElementRef}一起使用,以指定可以参与内容树的其他元素
     * 
     * <p>
     *  以下模式将生成以下Java类：
     * <pre>
     * &lt;xs:complexType name="foo">
     * &lt;xs:choice maxOccurs="unbounded" minOccurs="0">
     * &lt;xs:element name="a" type="xs:int" />
     * &lt;xs:element name="b" type="xs:int" />
     * &lt;xs:any namespace="##other" processContents="lax" />
     * &lt;/xs:choice>
     * &lt;/xs:complexType>
     * </pre>
     * 
     * <pre>
     *  class Foo {@ {@ link XmlAnyElement}(lax ="true")@ {@ link XmlElementRefs}({@ link XmlElementRef}(nam
     * e ="a",type ="JAXBElementclass")@ {@ link XmlElementRef} name ="b",type ="JAXBElementclass")}){@link List}
     * &lt; {@ link Object}>其他; }}。
     * 
     * @XmlRegistry class ObjectFactory {@XmlElementDecl(name ="a",namespace ="",scope = Fooclass){@link JAXBElement}
     * &lt; Integer> createFooA(Integer i){}。
     * 
     *  @XmlElementDecl(name ="b",namespace ="",scope = Fooclass){@link JAXBElement}&lt; Integer> createFooB
     * (Integer i){}。
     * </pre>
     * 
     *  它可以解组实例
     * 
     * <pre>
     * &lt;foo xmlns:e="extra">
     *  &lt; a> 1 </a> //这将解封到值为1的{@link JAXBElement}实例&lt; e：other /> //这将解封到DOM {@link Element}&lt; b> 3 </b>
     *  //这将解封到值为1的{@link JAXBElement}实例。
     */
    boolean lax() default false;

    /**
     * Specifies the {@link DomHandler} which is responsible for actually
     * converting XML from/to a DOM-like data structure.
     * <p>
     * &lt;/foo>
     * </pre>
     * 
     *  <h2> W3C XML模式"lax"通配符模拟</h2>注释的lax元素启用"lax"通配符语义的仿真例如,当Java源代码注释如下：
     * <pre>
     * @ {@ link XmlRootElement} class Foo {@XmlAnyElement(lax = true)public {@link Object} [] others; }}
     * </pre>
     *  那么下面的文档将会这样解组：
     * <pre>
     * &lt;foo>
     * &lt;unknown />
     * &lt;foo />
     * &lt;/foo>
     * 
     *  Foo foo = unmarshal(); // 1为"未知",另一个为"foo"assert foootherslength == 2; //'unknown'unmarshals to a DO
     * M element assert fooothers [0] instanceof Element; //因为lax = true,'foo'元素热切地// unmarshals到一个Foo对象asse
     * rt fooothers [1] instanceof Foo;。
     * </pre>
     * 
     */
    Class<? extends DomHandler> value() default W3CDomHandler.class;
}
