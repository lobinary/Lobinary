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
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Inherited;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * <p> Controls whether fields or Javabean properties are serialized by default. </p>
 *
 * <p> <b> Usage </b> </p>
 *
 * <p> <tt>@XmlAccessorType</tt> annotation can be used with the following program elements:</p>
 *
 * <ul>
 *   <li> package</li>
 *   <li> a top level class </li>
 * </ul>
 *
 * <p> See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>This annotation provides control over the default serialization
 * of properties and fields in a class.
 *
 * <p>The annotation <tt> @XmlAccessorType </tt> on a package applies to
 * all classes in the package. The following inheritance
 * semantics apply:
 *
 * <ul>
 *   <li> If there is a <tt>@XmlAccessorType</tt> on a class, then it
 *        is used. </li>
 *   <li> Otherwise, if a <tt>@XmlAccessorType</tt> exists on one of
 *        its super classes, then it is inherited.
 *   <li> Otherwise, the <tt>@XmlAccessorType </tt> on a package is
 *        inherited.
 * </ul>
 * <p> <b> Defaulting Rules: </b> </p>
 *
 * <p>By default, if <tt>@XmlAccessorType </tt> on a package is absent,
 * then the following package level annotation is assumed.</p>
 * <pre>
 *   &#64;XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
 * </pre>
 * <p> By default, if <tt>@XmlAccessorType</tt> on a class is absent,
 * and none of its super classes is annotated with
 * <tt>@XmlAccessorType</tt>, then the following default on the class
 * is assumed: </p>
 * <pre>
 *   &#64;XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
 * </pre>
 * <p>This annotation can be used with the following annotations:
 *    {@link XmlType}, {@link XmlRootElement}, {@link XmlAccessorOrder},
 *    {@link XmlSchema}, {@link XmlSchemaType}, {@link XmlSchemaTypes},
 *    , {@link XmlJavaTypeAdapter}. It can also be used with the
 *    following annotations at the package level: {@link XmlJavaTypeAdapter}.
 *
 * <p>
 *  <p>控制字段或Javabean属性是否按默认序列化。 </p>
 * 
 *  <p> <b>使用</b> </p>
 * 
 *  <p> <tt> @XmlAccessorType </tt>注释可用于以下程序元素：</p>
 * 
 * <ul>
 *  <li>包装</li> <li>顶级类</li>
 * </ul>
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 *  <p>此注释提供对类中的属性和字段的默认序列化的控制。
 * 
 *  <p>包上的注释<tt> @XmlAccessorType </tt>适用于包中的所有类。以下继承语义适用：
 * 
 * <ul>
 *  <li>如果类上有<tt> @XmlAccessorType </tt>,则会使用它。
 *  </li> <li>否则,如果<tt> @XmlAccessorType </tt>存在于其某个超类中,则它将被继承。
 *  <li>否则,程序包上的<tt> @XmlAccessorType </tt>将继承。
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @see XmlAccessType
 */

@Inherited @Retention(RUNTIME) @Target({PACKAGE, TYPE})
public @interface XmlAccessorType {

    /**
     * Specifies whether fields or properties are serialized.
     *
     * <p>
     * </ul>
     *  <p> <b>默认规则：</b> </p>
     * 
     *  <p>默认情况下,如果包上的<tt> @XmlAccessorType </tt>不存在,则假定以下包级别注释。</p>
     * <pre>
     *  @XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
     * </pre>
     *  <p>默认情况下,如果类上的<tt> @XmlAccessorType </tt>不存在,并且其所有超类都未使用<tt> @XmlAccessorType </tt>注释,那么将假定类上的以下默认值：
     * </p>。
     * <pre>
     * 
     * @see XmlAccessType
     */
    XmlAccessType value() default XmlAccessType.PUBLIC_MEMBER;
}
