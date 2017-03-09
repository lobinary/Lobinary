/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.javadoc;

/**
 * This interface provides error, warning and notice printing.
 *
 * <p>
 *  此接口提供错误,警告和通知打印。
 * 
 * 
 * @since 1.2
 * @author Robert Field
 */
public interface DocErrorReporter {

    /**
     * Print error message and increment error count.
     *
     * <p>
     *  打印错误消息和增加错误计数。
     * 
     * 
     * @param msg message to print
     */
    void printError(String msg);

    /**
     * Print an error message and increment error count.
     *
     * <p>
     *  打印错误消息并增加错误计数。
     * 
     * 
     * @param pos the position item where the error occurs
     * @param msg message to print
     * @since 1.4
     */
    void printError(SourcePosition pos, String msg);

    /**
     * Print warning message and increment warning count.
     *
     * <p>
     *  打印警告消息和增加警告计数。
     * 
     * 
     * @param msg message to print
     */
    void printWarning(String msg);

    /**
     * Print warning message and increment warning count.
     *
     * <p>
     *  打印警告消息和增加警告计数。
     * 
     * 
     * @param pos the position item where the warning occurs
     * @param msg message to print
     * @since 1.4
     */
    void printWarning(SourcePosition pos, String msg);

    /**
     * Print a message.
     *
     * <p>
     *  打印消息。
     * 
     * 
     * @param msg message to print
     */
    void printNotice(String msg);

    /**
     * Print a message.
     *
     * <p>
     *  打印消息。
     * 
     * @param pos the position item where the message occurs
     * @param msg message to print
     * @since 1.4
     */
    void printNotice(SourcePosition pos, String msg);
}
