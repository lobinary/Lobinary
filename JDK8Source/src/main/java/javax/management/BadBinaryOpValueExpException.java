/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2003, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown when an invalid expression is passed to a method for
 * constructing a query.  This exception is used internally by JMX
 * during the evaluation of a query.  User code does not usually see
 * it.
 *
 * <p>
 *  当无效的表达式传递给构造查询的方法时抛出。此异常在评估查询期间由JMX在内部使用。用户代码通常不会看到它。
 * 
 * 
 * @since 1.5
 */
public class BadBinaryOpValueExpException extends Exception   {


    /* Serial version */
    private static final long serialVersionUID = 5068475589449021227L;

    /**
    /* <p>
    /* 
     * @serial the {@link ValueExp} that originated this exception
     */
    private ValueExp exp;


    /**
     * Constructs a <CODE>BadBinaryOpValueExpException</CODE> with the specified <CODE>ValueExp</CODE>.
     *
     * <p>
     *  使用指定的<CODE> ValueExp </CODE>构造一个<CODE> BadBinaryOpValueExpException </CODE>。
     * 
     * 
     * @param exp the expression whose value was inappropriate.
     */
    public BadBinaryOpValueExpException(ValueExp exp) {
        this.exp = exp;
    }


    /**
     * Returns the <CODE>ValueExp</CODE> that originated the exception.
     *
     * <p>
     *  返回产生异常的<CODE> ValueExp </CODE>。
     * 
     * 
     * @return the problematic {@link ValueExp}.
     */
    public ValueExp getExp()  {
        return exp;
    }

    /**
     * Returns the string representing the object.
     * <p>
     *  返回表示对象的字符串。
     */
    public String toString()  {
        return "BadBinaryOpValueExpException: " + exp;
    }

 }
