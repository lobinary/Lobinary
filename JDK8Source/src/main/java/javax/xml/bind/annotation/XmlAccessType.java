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



/**
 * Used by XmlAccessorType to control serialization of fields or
 * properties.
 *
 * <p>
 *  由XmlAccessorType用于控制字段或属性的序列化。
 * 
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @see XmlAccessorType
 */

public enum XmlAccessType {
    /**
     * Every getter/setter pair in a JAXB-bound class will be automatically
     * bound to XML, unless annotated by {@link XmlTransient}.
     *
     * Fields are bound to XML only when they are explicitly annotated
     * by some of the JAXB annotations.
     * <p>
     *  除非由{@link XmlTransient}注释,否则JAXB绑定类中的每个getter / setter对都将自动绑定到XML。
     * 
     *  仅当它们由某些JAXB注释显式注释时,字段才绑定到XML。
     * 
     */
    PROPERTY,
    /**
     * Every non static, non transient field in a JAXB-bound class will be automatically
     * bound to XML, unless annotated by {@link XmlTransient}.
     *
     * Getter/setter pairs are bound to XML only when they are explicitly annotated
     * by some of the JAXB annotations.
     * <p>
     *  除非由{@link XmlTransient}注释,否则JAXB绑定类中的每个非静态,非瞬态字段都将自动绑定到XML。
     * 
     *  Getter / setter对只有当它们被一些JAXB注释显式注释时才绑定到XML。
     * 
     */
    FIELD,
    /**
     * Every public getter/setter pair and every public field will be
     * automatically bound to XML, unless annotated by {@link XmlTransient}.
     *
     * Fields or getter/setter pairs that are private, protected, or
     * defaulted to package-only access are bound to XML only when they are
     * explicitly annotated by the appropriate JAXB annotations.
     * <p>
     *  除非由{@link XmlTransient}注释,每个public getter / setter对和每个公共字段都将自动绑定到XML。
     * 
     *  私有的,受保护的或默认为纯包访问的字段或getter / setter对仅在由相应的JAXB注释显式注释时才绑定到XML。
     * 
     */
    PUBLIC_MEMBER,
    /**
     * None of the fields or properties is bound to XML unless they
     * are specifically  annotated with some of the JAXB annotations.
     * <p>
     */
    NONE
}
