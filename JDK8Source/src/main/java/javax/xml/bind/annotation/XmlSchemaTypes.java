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

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.PACKAGE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * A container for multiple @{@link XmlSchemaType} annotations.
 *
 * <p> Multiple annotations of the same type are not allowed on a program
 * element. This annotation therefore serves as a container annotation
 * for multiple &#64;XmlSchemaType annotations as follows:
 *
 * <pre>
 * &#64;XmlSchemaTypes({ @XmlSchemaType(...), @XmlSchemaType(...) })
 * </pre>
 * <p>The <tt>@XmlSchemaTypes</tt> annnotation can be used to
 * define {@link XmlSchemaType} for different types at the
 * package level.
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>
 * <p>
 *  用于多个@ {@ link XmlSchemaType}注释的容器。
 * 
 *  <p>程序元素上不允许使用同一类型的多个注释。因此,此注释用作多个@XmlSchemaType注释的容器注释,如下所示：
 * 
 * <pre>
 *  @XmlSchemaTypes({@XmlSchemaType(...),@XmlSchemaType(...)})
 * </pre>
 * 
 * @author <ul><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li></ul>
 * @see XmlSchemaType
 * @since JAXB2.0
 */
@Retention(RUNTIME) @Target({PACKAGE})
public @interface XmlSchemaTypes {
    /**
     * Collection of @{@link XmlSchemaType} annotations
     * <p>
     *  <p> <tt> @XmlSchemaTypes </tt>注释可用于在包级别为不同类型定义{@link XmlSchemaType}。
     * 
     *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
     * 
     */
    XmlSchemaType[] value();
}
