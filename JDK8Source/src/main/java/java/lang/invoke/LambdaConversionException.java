/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

/**
 * LambdaConversionException
 * <p>
 *  LambdaConversionException
 * 
 */
public class LambdaConversionException extends Exception {
    private static final long serialVersionUID = 292L + 8L;

    /**
     * Constructs a {@code LambdaConversionException}.
     * <p>
     *  构造一个{@code LambdaConversionException}。
     * 
     */
    public LambdaConversionException() {
    }

    /**
     * Constructs a {@code LambdaConversionException} with a message.
     * <p>
     *  构造一个带有消息的{@code LambdaConversionException}。
     * 
     * 
     * @param message the detail message
     */
    public LambdaConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code LambdaConversionException} with a message and cause.
     * <p>
     *  构造一个{@code LambdaConversionException}消息和原因。
     * 
     * 
     * @param message the detail message
     * @param cause the cause
     */
    public LambdaConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a {@code LambdaConversionException} with a cause.
     * <p>
     *  构造具有原因的{@code LambdaConversionException}。
     * 
     * 
     * @param cause the cause
     */
    public LambdaConversionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a {@code LambdaConversionException} with a message,
     * cause, and other settings.
     * <p>
     *  构造具有消息,原因和其他设置的{@code LambdaConversionException}。
     * 
     * @param message the detail message
     * @param cause the cause
     * @param enableSuppression whether or not suppressed exceptions are enabled
     * @param writableStackTrace whether or not the stack trace is writable
     */
    public LambdaConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
