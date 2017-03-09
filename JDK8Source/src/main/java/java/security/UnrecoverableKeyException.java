/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2005, Oracle and/or its affiliates. All rights reserved.
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

package java.security;

/**
 * This exception is thrown if a key in the keystore cannot be recovered.
 *
 *
 * <p>
 *  如果密钥库中的密钥无法恢复,则抛出此异常。
 * 
 * 
 * @since 1.2
 */

public class UnrecoverableKeyException extends UnrecoverableEntryException {

    private static final long serialVersionUID = 7275063078190151277L;

    /**
     * Constructs an UnrecoverableKeyException with no detail message.
     * <p>
     *  构造不带有详细消息的UnrecoverableKeyException。
     * 
     */
    public UnrecoverableKeyException() {
        super();
    }

    /**
     * Constructs an UnrecoverableKeyException with the specified detail
     * message, which provides more information about why this exception
     * has been thrown.
     *
     * <p>
     *  构造具有指定详细消息的UnrecoverableKeyException,该消息提供有关为什么抛出此异常的更多信息。
     * 
     * @param msg the detail message.
     */
   public UnrecoverableKeyException(String msg) {
       super(msg);
    }
}
