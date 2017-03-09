/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.print.attribute;

/**
 * Thrown to indicate that the requested operation cannot be performed
 * because the set is unmodifiable.
 *
 * <p>
 *  抛出以指示无法执行所请求的操作,因为该集不可修改。
 * 
 * 
 * @author  Phil Race
 * @since   1.4
 */
public class UnmodifiableSetException extends RuntimeException {
    /**
     * Constructs an UnsupportedOperationException with no detail message.
     * <p>
     *  构造不带详细消息的UnsupportedOperationException。
     * 
     */
    public UnmodifiableSetException() {
    }

    /**
     * Constructs an UnmodifiableSetException with the specified
     * detail message.
     *
     * <p>
     *  构造具有指定详细消息的UnmodifiableSetException。
     * 
     * @param message the detail message
     */
    public UnmodifiableSetException(String message) {
        super(message);
    }
}
