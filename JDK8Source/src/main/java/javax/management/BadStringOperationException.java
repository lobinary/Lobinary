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
 * Thrown when an invalid string operation is passed
 * to a method for constructing a query.
 *
 * <p>
 *  当无效的字符串操作传递给构造查询的方法时抛出。
 * 
 * 
 * @since 1.5
 */
public class BadStringOperationException extends Exception   {


    /* Serial version */
    private static final long serialVersionUID = 7802201238441662100L;

    /**
    /* <p>
    /* 
     * @serial The description of the operation that originated this exception
     */
    private String op;

    /**
     * Constructs a <CODE>BadStringOperationException</CODE> with the specified detail
     * message.
     *
     * <p>
     *  用指定的详细消息构造一个<CODE> BadStringOperationException </CODE>。
     * 
     * 
     * @param message the detail message.
     */
    public BadStringOperationException(String message) {
        this.op = message;
    }


    /**
     * Returns the string representing the object.
     * <p>
     *  返回表示对象的字符串。
     */
    public String toString()  {
        return "BadStringOperationException: " + op;
    }

 }
