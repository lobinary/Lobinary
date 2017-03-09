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
 * Used by XmlAccessorOrder to control the ordering of properties and
 * fields in a JAXB bound class.
 *
 * <p>
 *  由XmlAccessorOrder用于控制JAXB绑定类中的属性和字段的顺序。
 * 
 * 
 * @author Sekhar Vajjhala, Sun Microsystems, Inc.
 * @since JAXB2.0
 * @see XmlAccessorOrder
 */

public enum XmlAccessOrder {
    /**
     * The ordering of fields and properties in a class is undefined.
     * <p>
     *  类中的字段和属性的顺序未定义。
     * 
     */
    UNDEFINED,
    /**
     * The ordering of fields and properties in a class is in
     * alphabetical order as determined by the
     * method java.lang.String.compareTo(String anotherString).
     * <p>
     *  类中字段和属性的排序按字母顺序,由方法java.lang.String.compareTo(String anotherString)确定。
     */
    ALPHABETICAL
}
