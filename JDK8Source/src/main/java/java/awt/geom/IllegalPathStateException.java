/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.geom;

/**
 * The <code>IllegalPathStateException</code> represents an
 * exception that is thrown if an operation is performed on a path
 * that is in an illegal state with respect to the particular
 * operation being performed, such as appending a path segment
 * to a {@link GeneralPath} without an initial moveto.
 *
 * <p>
 *  <code> IllegalPathStateException </code>表示如果在相对于正在执行的特定操作处于非法状态的路径上执行操作时抛出的异常,例如将路径段附加到{@link GeneralPath }
 * 没有初始moveto。
 * 
 */

public class IllegalPathStateException extends RuntimeException {
    /**
     * Constructs an <code>IllegalPathStateException</code> with no
     * detail message.
     *
     * <p>
     *  构造一个没有详细消息的<code> IllegalPathStateException </code>。
     * 
     * 
     * @since   1.2
     */
    public IllegalPathStateException() {
    }

    /**
     * Constructs an <code>IllegalPathStateException</code> with the
     * specified detail message.
     * <p>
     *  使用指定的详细消息构造一个<code> IllegalPathStateException </code>。
     * 
     * @param   s   the detail message
     * @since   1.2
     */
    public IllegalPathStateException(String s) {
        super (s);
    }
}
