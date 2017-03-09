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

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * <p>
 * Associates a namespace prefix with a XML namespace URI.
 *
 * <p><b>Usage</b></p>
 * <p><tt>@XmlNs</tt> annotation is intended for use from other
 * program annotations.
 *
 * <p>See "Package Specification" in javax.xml.bind.package javadoc for
 * additional common information.</p>
 *
 * <p><b>Example:</b>See <tt>XmlSchema</tt> annotation type for an example.
 * <p>
 * <p>
 *  将命名空间前缀与XML命名空间URI相关联。
 * 
 *  <p> <b>使用</b> </p> <p> <tt> @XmlNs </tt>注释适用于其他程序注释。
 * 
 *  <p>有关其他常见信息,请参阅javax.xml.bind.package javadoc中的"包规范"。</p>
 * 
 *  <p> <b>示例：</b>请参阅<tt> XmlSchema </tt>注释类型示例。
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 */

@Retention(RUNTIME) @Target({})
public @interface XmlNs {
    /**
     * Namespace prefix
     * <p>
     * 
     */
    String prefix();

    /**
     * Namespace URI
     * <p>
     *  命名空间前缀
     * 
     */
    String namespaceURI();
}
