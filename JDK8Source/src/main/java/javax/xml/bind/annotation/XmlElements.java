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
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * A container for multiple @{@link XmlElement} annotations.
 *
 * Multiple annotations of the same type are not allowed on a program
 * element. This annotation therefore serves as a container annotation
 * for multiple &#64;XmlElements as follows:
 *
 * <pre>
 * &#64;XmlElements({ @XmlElement(...),@XmlElement(...) })
 * </pre>
 *
 * <p>The <tt>@XmlElements</tt> annnotation can be used with the
 * following program elements: </p>
 * <ul>
 *   <li> a JavaBean property </li>
 *   <li> non static, non transient field </li>
 * </ul>
 *
 * This annotation is intended for annotation a JavaBean collection
 * property (e.g. List).
 *
 * <p><b>Usage</b></p>
 *
 * <p>The usage is subject to the following constraints:
 * <ul>
 *   <li> This annotation can be used with the following
 *        annotations: @{@link XmlIDREF}, @{@link XmlElementWrapper}. </li>
 *   <li> If @XmlIDREF is also specified on the JavaBean property,
 *        then each &#64;XmlElement.type() must contain a JavaBean
 *        property annotated with <tt>&#64;XmlID</tt>.</li>
 * </ul>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <hr>
 *
 * <p><b>Example 1:</b> Map to a list of elements</p>
 * <pre>
 *
 *    // Mapped code fragment
 *    public class Foo {
 *        &#64;XmlElements(
 *            &#64;XmlElement(name="A", type=Integer.class),
 *            &#64;XmlElement(name="B", type=Float.class)
 *         }
 *         public List items;
 *    }
 *
 *    &lt;!-- XML Representation for a List of {1,2.5}
 *            XML output is not wrapped using another element -->
 *    ...
 *    &lt;A> 1 &lt;/A>
 *    &lt;B> 2.5 &lt;/B>
 *    ...
 *
 *    &lt;!-- XML Schema fragment -->
 *    &lt;xs:complexType name="Foo">
 *      &lt;xs:sequence>
 *        &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *          &lt;xs:element name="A" type="xs:int"/>
 *          &lt;xs:element name="B" type="xs:float"/>
 *        &lt;xs:choice>
 *      &lt;/xs:sequence>
 *    &lt;/xs:complexType>
 *
 * </pre>
 *
 * <p><b>Example 2:</b> Map to a list of elements wrapped with another element
 * </p>
 * <pre>
 *
 *    // Mapped code fragment
 *    public class Foo {
 *        &#64;XmlElementWrapper(name="bar")
 *        &#64;XmlElements(
 *            &#64;XmlElement(name="A", type=Integer.class),
 *            &#64;XmlElement(name="B", type=Float.class)
 *        }
 *        public List items;
 *    }
 *
 *    &lt;!-- XML Schema fragment -->
 *    &lt;xs:complexType name="Foo">
 *      &lt;xs:sequence>
 *        &lt;xs:element name="bar">
 *          &lt;xs:complexType>
 *            &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *              &lt;xs:element name="A" type="xs:int"/>
 *              &lt;xs:element name="B" type="xs:float"/>
 *            &lt;/xs:choice>
 *          &lt;/xs:complexType>
 *        &lt;/xs:element>
 *      &lt;/xs:sequence>
 *    &lt;/xs:complexType>
 * </pre>
 *
 * <p><b>Example 3:</b> Change element name based on type using an adapter.
 * </p>
 * <pre>
 *    class Foo {
 *       &#64;XmlJavaTypeAdapter(QtoPAdapter.class)
 *       &#64;XmlElements({
 *           &#64;XmlElement(name="A",type=PX.class),
 *           &#64;XmlElement(name="B",type=PY.class)
 *       })
 *       Q bar;
 *    }
 *
 *    &#64;XmlType abstract class P {...}
 *    &#64;XmlType(name="PX") class PX extends P {...}
 *    &#64;XmlType(name="PY") class PY extends P {...}
 *
 *    &lt;!-- XML Schema fragment -->
 *    &lt;xs:complexType name="Foo">
 *      &lt;xs:sequence>
 *        &lt;xs:element name="bar">
 *          &lt;xs:complexType>
 *            &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 *              &lt;xs:element name="A" type="PX"/>
 *              &lt;xs:element name="B" type="PY"/>
 *            &lt;/xs:choice>
 *          &lt;/xs:complexType>
 *        &lt;/xs:element>
 *      &lt;/xs:sequence>
 *    &lt;/xs:complexType>
 * </pre>
 *
 * <p>
 * <p>
 *  用于多个@ {@ link XmlElement}注释的容器。
 * 
 *  程序元素上不允许使用相同类型的多个注释。因此,此注释用作多个@XmlElements的容器注释,如下所示：
 * 
 * <pre>
 *  @XmlElements({@XmlElement(...),@ XmlElement(...)})
 * </pre>
 * 
 *  <p> <tt> @XmlElements </tt>注释可用于以下程序元素：</p>
 * <ul>
 *  <li>一个JavaBean属性</li> <li>非静态,非瞬态字段</li>
 * </ul>
 * 
 *  此注释用于注释JavaBean集合属性(例如List)。
 * 
 *  <p> <b>使用</b> </p>
 * 
 *  <p>使用受以下限制约束：
 * <ul>
 *  <li>此注释可用于以下注释：@ {@ link XmlIDREF},@ {@ link XmlElementWrapper}。
 *  </li> <li>如果还在JavaBean属性上指定了@XmlIDREF,则每个@ XmlElement.type()必须包含一个用<tt> @XmlID </tt>注释的JavaBean属性。
 * </li>。
 * </ul>
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 * <hr>
 * 
 *  <p> <b>示例1：</b>映射到元素列表</p>
 * <pre>
 * 
 *  //映射代码片段public class Foo {@XmlElements(@XmlElement(name ="A",type = Integer.class),@XmlElement(name ="B",type = Float.class)。
 * 
 *  &lt;！ - 一个{1,2.5}列表的XML表示形式
 * XML output is not wrapped using another element -->
 *  ... ...
 * &lt;A> 1 &lt;/A>
 * &lt;B> 2.5 &lt;/B>
 *  ... ...
 * 
 * &lt;!-- XML Schema fragment -->
 * &lt;xs:complexType name="Foo">
 * &lt;xs:sequence>
 * &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
 * &lt;xs:element name="A" type="xs:int"/>
 * &lt;xs:element name="B" type="xs:float"/>
 * &lt;xs:choice>
 * &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * 
 * 
 * @author <ul><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li></ul>
 * @see XmlElement
 * @see XmlElementRef
 * @see XmlElementRefs
 * @see XmlJavaTypeAdapter
 * @since JAXB2.0
 */
@Retention(RUNTIME) @Target({FIELD,METHOD})
public @interface XmlElements {
    /**
     * Collection of @{@link XmlElement} annotations
     * <p>
     * </pre>
     * 
     * <p> <b>示例2：</b>映射到包含另一个元素的元素列表
     * </p>
     * <pre>
     * 
     *  //映射代码片段public class Foo {@XmlElementWrapper(name ="bar")@XmlElements(@XmlElement(name ="A",type = Integer.class),@XmlElement(name ="B",type = Float。
     *  class)} public List items;}。
     * 
     * &lt;!-- XML Schema fragment -->
     * &lt;xs:complexType name="Foo">
     * &lt;xs:sequence>
     * &lt;xs:element name="bar">
     * &lt;xs:complexType>
     * &lt;xs:choice minOccurs="0" maxOccurs="unbounded">
     * &lt;xs:element name="A" type="xs:int"/>
     * &lt;xs:element name="B" type="xs:float"/>
     * &lt;/xs:choice>
     * &lt;/xs:complexType>
     * &lt;/xs:element>
     * &lt;/xs:sequence>
     * &lt;/xs:complexType>
     * </pre>
     * 
     *  <p> <b>示例3：</b>使用适配器根据类型更改元素名称。
     */
    XmlElement[] value();
}
