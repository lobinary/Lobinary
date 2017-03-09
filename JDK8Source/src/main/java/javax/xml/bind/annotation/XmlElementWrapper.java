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

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Generates a wrapper element around XML representation.
 *
 * This is primarily intended to be used to produce a wrapper
 * XML element around collections. The annotation therefore supports
 * two forms of serialization shown below.
 *
 * <pre>
 *    //Example: code fragment
 *      int[] names;
 *
 *    // XML Serialization Form 1 (Unwrapped collection)
 *    &lt;names> ... &lt;/names>
 *    &lt;names> ... &lt;/names>
 *
 *    // XML Serialization Form 2 ( Wrapped collection )
 *    &lt;wrapperElement>
 *       &lt;names> value-of-item &lt;/names>
 *       &lt;names> value-of-item &lt;/names>
 *       ....
 *    &lt;/wrapperElement>
 * </pre>
 *
 * <p> The two serialized XML forms allow a null collection to be
 * represented either by absence or presence of an element with a
 * nillable attribute.
 *
 * <p> <b>Usage</b> </p>
 * <p>
 * The <tt>@XmlElementWrapper</tt> annotation can be used with the
 * following program elements:
 * <ul>
 *   <li> JavaBean property </li>
 *   <li> non static, non transient field </li>
 * </ul>
 *
 * <p>The usage is subject to the following constraints:
 * <ul>
 *   <li> The property must be a collection property </li>
 *   <li> This annotation can be used with the following annotations:
 *            {@link XmlElement},
 *            {@link XmlElements},
 *            {@link XmlElementRef},
 *            {@link XmlElementRefs},
 *            {@link XmlJavaTypeAdapter}.</li>
 * </ul>
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>
 *  生成围绕XML表示的包装器元素。
 * 
 *  这主要是用于生成围绕集合的包装XML元素。因此,注释支持两种形式的序列化,如下所示。
 * 
 * <pre>
 *  //示例：code fragment int [] names;
 * 
 *  // XML序列化表单1(展开的集合)
 * &lt;names> ... &lt;/names>
 * &lt;names> ... &lt;/names>
 * 
 *  // XML序列化表单2(包装集合)
 * &lt;wrapperElement>
 * &lt;names> value-of-item &lt;/names>
 * &lt;names> value-of-item &lt;/names>
 *  ....
 * &lt;/wrapperElement>
 * </pre>
 * 
 *  <p>这两个序列化的XML表单允许空集合通过缺少或存在具有nillable属性的元素来表示。
 * 
 *  <p> <b>使用</b> </p>
 * <p>
 *  <tt> @XmlElementWrapper </tt>注释可以与以下程序元素一起使用：
 * <ul>
 *  <li> JavaBean属性</li> <li>非静态,非瞬态字段</li>
 * </ul>
 * 
 *  <p>使用受以下限制约束：
 * <ul>
 *  <li>此属性必须是集合属性</li> <li>此注释可用于以下注释：{@link XmlElement},{@link XmlElements},{@link XmlElementRef},{@link XmlElementRefs}
 *  ,{@link XmlJavaTypeAdapter}。
 * 
 * @author <ul><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li></ul>
 * @see XmlElement
 * @see XmlElements
 * @see XmlElementRef
 * @see XmlElementRefs
 * @since JAXB2.0
 *
 */

@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface XmlElementWrapper {
    /**
     * Name of the XML wrapper element. By default, the XML wrapper
     * element name is derived from the JavaBean property name.
     * <p>
     * </li>。
     * </ul>
     * 
     *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
     * 
     */
    String name() default "##default";

    /**
     * XML target namespace of the XML wrapper element.
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
     *  Otherwise "" (which produces unqualified element in the default
     *  namespace.
     * </ol>
     * <p>
     *  XML包装器元素的名称。默认情况下,XML包装器元素名称派生自JavaBean属性名称。
     * 
     */
    String namespace() default "##default";

    /**
     * If true, the absence of the collection is represented by
     * using <tt>xsi:nil='true'</tt>. Otherwise, it is represented by
     * the absence of the element.
     * <p>
     *  XML包装器元素的XML目标命名空间。
     * <p>
     *  如果值为"## default",则命名空间确定如下：
     * <ol>
     * <li>
     * 如果封装包具有{@link XmlSchema}注释,并且其{@link XmlSchema#elementFormDefault()elementFormDefault}为{@link XmlNsForm#QUALIFIED QUALIFIED}
     * ,则为封闭类的命名空间。
     * 
     * <li>
     *  否则""(它在默认命名空间中产生非限定元素。
     * </ol>
     */
    boolean nillable() default false;

    /**
     * Customize the wrapper element declaration to be required.
     *
     * <p>
     * If required() is true, then the corresponding generated
     * XML schema element declaration will have <tt>minOccurs="1"</tt>,
     * to indicate that the wrapper element is always expected.
     *
     * <p>
     * Note that this only affects the schema generation, and
     * not the unmarshalling or marshalling capability. This is
     * simply a mechanism to let users express their application constraints
     * better.
     *
     * <p>
     *  如果为true,则使用<tt> xsi：nil ='true'</tt>来表示集合的缺失。否则,它由没有元素表示。
     * 
     * 
     * @since JAXB 2.1
     */
    boolean required() default false;
}
