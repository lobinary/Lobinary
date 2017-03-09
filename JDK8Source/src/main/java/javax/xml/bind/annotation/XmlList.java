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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * Used to map a property to a list simple type.
 *
 * <p><b>Usage</b> </p>
 * <p>
 * The <tt>@XmlList</tt> annotation can be used with the
 * following program elements:
 * <ul>
 *   <li> JavaBean property </li>
 *   <li> field </li>
 * </ul>
 *
 * <p>
 * When a collection property is annotated just with @XmlElement,
 * each item in the collection will be wrapped by an element.
 * For example,
 *
 * <pre>
 * &#64;XmlRootElement
 * class Foo {
 *     &#64;XmlElement
 *     List&lt;String> data;
 * }
 * </pre>
 *
 * would produce XML like this:
 *
 * <pre>
 * &lt;foo>
 *   &lt;data>abc</data>
 *   &lt;data>def</data>
 * &lt;/foo>
 * </pre>
 *
 * &#64;XmlList annotation, on the other hand, allows multiple values to be
 * represented as whitespace-separated tokens in a single element. For example,
 *
 * <pre>
 * &#64;XmlRootElement
 * class Foo {
 *     &#64;XmlElement
 *     &#64;XmlList
 *     List&lt;String> data;
 * }
 * </pre>
 *
 * the above code will produce XML like this:
 *
 * <pre>
 * &lt;foo>
 *   &lt;data>abc def</data>
 * &lt;/foo>
 * </pre>
 *
 * <p>This annotation can be used with the following annotations:
 *        {@link XmlElement},
 *        {@link XmlAttribute},
 *        {@link XmlValue},
 *        {@link XmlIDREF}.
 *  <ul>
 *    <li> The use of <tt>@XmlList</tt> with {@link XmlValue} while
 *         allowed, is redundant since  {@link XmlList} maps a
 *         collection type to a simple schema type that derives by
 *         list just as {@link XmlValue} would. </li>
 *
 *    <li> The use of <tt>@XmlList</tt> with {@link XmlAttribute} while
 *         allowed, is redundant since  {@link XmlList} maps a
 *         collection type to a simple schema type that derives by
 *         list just as {@link XmlAttribute} would. </li>
 *  </ul>
 *
 * <p>
 *  用于将属性映射到列表简单类型。
 * 
 *  <p> <b>使用</b> </p>
 * <p>
 *  <tt> @XmlList </tt>注释可以与以下程序元素一起使用：
 * <ul>
 *  <li> JavaBean属性</li> <li>字段</li>
 * </ul>
 * 
 * <p>
 *  当一个集合属性只用@XmlElement注释时,集合中的每个项目都将被一个元素包装。例如,
 * 
 * <pre>
 *  @XmlRootElement class Foo {@XmlElement List&lt; String&gt; data; }}
 * </pre>
 * 
 *  将生成XML像这样：
 * 
 * <pre>
 * &lt;foo>
 *  &lt; data&gt; abc </data>&lt; data> def </data>
 * &lt;/foo>
 * </pre>
 * 
 *  另一方面,@XmlList注释允许在单个元素中将多个值表示为空格分隔的令牌。例如,
 * 
 * @author <ul><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li></ul>
 * @since JAXB2.0
 */
@Retention(RUNTIME) @Target({FIELD,METHOD,PARAMETER})
public @interface XmlList {
}
