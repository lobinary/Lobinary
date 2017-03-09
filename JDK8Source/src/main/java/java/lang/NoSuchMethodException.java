/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

/**
 * Thrown when a particular method cannot be found.
 *
 * <p>
 *  当无法找到特定方法时抛出。
 * 
 * 
 * @author     unascribed
 * @since      JDK1.0
 */
public
class NoSuchMethodException extends ReflectiveOperationException {
    private static final long serialVersionUID = 5034388446362600923L;

    /**
     * Constructs a <code>NoSuchMethodException</code> without a detail message.
     * <p>
     *  构造一个没有详细消息的<code> NoSuchMethodException </code>。
     * 
     */
    public NoSuchMethodException() {
        super();
    }

    /**
     * Constructs a <code>NoSuchMethodException</code> with a detail message.
     *
     * <p>
     *  构造具有详细消息的<code> NoSuchMethodException </code>。
     * 
     * @param      s   the detail message.
     */
    public NoSuchMethodException(String s) {
        super(s);
    }
}
