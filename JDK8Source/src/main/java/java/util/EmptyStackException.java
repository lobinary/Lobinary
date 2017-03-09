/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1994, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * Thrown by methods in the <code>Stack</code> class to indicate
 * that the stack is empty.
 *
 * <p>
 *  通过<code> Stack </code>类中的方法抛出,以指示堆栈为空。
 * 
 * 
 * @author  Jonathan Payne
 * @see     java.util.Stack
 * @since   JDK1.0
 */
public
class EmptyStackException extends RuntimeException {
    private static final long serialVersionUID = 5084686378493302095L;

    /**
     * Constructs a new <code>EmptyStackException</code> with <tt>null</tt>
     * as its error message string.
     * <p>
     *  使用<tt> null </tt>作为其错误消息字符串构造新的<code> EmptyStackException </code>。
     */
    public EmptyStackException() {
    }
}
