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

package javax.xml.bind.annotation.adapters;

import static java.lang.annotation.ElementType.PACKAGE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * <p>
 * A container for multiple @{@link XmlJavaTypeAdapter} annotations.
 *
 * <p> Multiple annotations of the same type are not allowed on a program
 * element. This annotation therefore serves as a container annotation
 * for multiple &#64;XmlJavaTypeAdapter as follows:
 *
 * <pre>
 * &#64;XmlJavaTypeAdapters ({ @XmlJavaTypeAdapter(...),@XmlJavaTypeAdapter(...) })
 * </pre>
 *
 * <p>The <tt>@XmlJavaTypeAdapters</tt> annnotation is useful for
 * defining {@link XmlJavaTypeAdapter} annotations for different types
 * at the package level.
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p>
 * <p>
 *  用于多个@ {@ link XmlJavaTypeAdapter}注释的容器。
 * 
 *  <p>程序元素上不允许使用同一类型的多个注释。因此,此注释用作多个@XmlJavaTypeAdapter的容器注释,如下所示：
 * 
 * <pre>
 *  @XmlJavaTypeAdapters({@XmlJavaTypeAdapter(...),@ XmlJavaTypeAdapter(...)})
 * </pre>
 * 
 * @author <ul><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li></ul>
 * @see XmlJavaTypeAdapter
 * @since JAXB2.0
 */
@Retention(RUNTIME) @Target({PACKAGE})
public @interface XmlJavaTypeAdapters {
    /**
     * Collection of @{@link XmlJavaTypeAdapter} annotations
     * <p>
     * 
     *  <p> <tt> @XmlJavaTypeAdapters </tt>注释适用于在包级别定义不同类型的{@link XmlJavaTypeAdapter}注释。
     * 
     *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
     */
    XmlJavaTypeAdapter[] value();
}
