/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.print;

/**
 * The <code>PrinterException</code> class and its subclasses are used
 * to indicate that an exceptional condition has occurred in the print
 * system.
 * <p>
 *  <code> PrinterException </code>类及其子类用于指示在打印系统中发生了异常情况。
 * 
 */

public class PrinterException extends Exception {

    /**
     * Constructs a new <code>PrinterException</code> object
     * without a detail message.
     * <p>
     *  构造一个新的<code> PrinterException </code>对象而不包含详细消息。
     * 
     */
    public PrinterException() {

    }

    /**
     * Constructs a new <code>PrinterException</code> object
     * with the specified detail message.
     * <p>
     *  使用指定的详细消息构造新的<code> PrinterException </code>对象。
     * 
     * @param msg the message to generate when a
     * <code>PrinterException</code> is thrown
     */
    public PrinterException(String msg) {
        super(msg);
    }
}
