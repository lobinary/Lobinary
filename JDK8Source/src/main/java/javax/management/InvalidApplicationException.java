/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;


/**
 * Thrown when an attempt is made to apply either of the following: A
 * subquery expression to an MBean or a qualified attribute expression
 * to an MBean of the wrong class.  This exception is used internally
 * by JMX during the evaluation of a query.  User code does not
 * usually see it.
 *
 * <p>
 *  尝试应用以下任一项时抛出：子查询表达式到MBean或限定属性表达式到错误类的MBean。此异常在评估查询期间由JMX在内部使用。用户代码通常不会看到它。
 * 
 * 
 * @since 1.5
 */
public class InvalidApplicationException extends Exception   {


    /* Serial version */
    private static final long serialVersionUID = -3048022274675537269L;

    /**
    /* <p>
    /* 
     * @serial The object representing the class of the MBean
     */
    private Object val;


    /**
     * Constructs an <CODE>InvalidApplicationException</CODE> with the specified <CODE>Object</CODE>.
     *
     * <p>
     *  使用指定的<CODE>对象</CODE>构造<CODE> InvalidApplicationException </CODE>。
     * 
     * @param val the detail message of this exception.
     */
    public InvalidApplicationException(Object val) {
        this.val = val;
    }
}
