/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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
 * This exception is thrown if an entry in the keystore cannot be recovered.
 *
 *
 * <p>
 *  如果无法恢复密钥库中的条目,则抛出此异常。
 * 
 * 
 * @since 1.5
 */

public class UnrecoverableEntryException extends GeneralSecurityException {

    private static final long serialVersionUID = -4527142945246286535L;

    /**
     * Constructs an UnrecoverableEntryException with no detail message.
     * <p>
     *  构造不包含详细消息的UnrecoverableEntryException。
     * 
     */
    public UnrecoverableEntryException() {
        super();
    }

    /**
     * Constructs an UnrecoverableEntryException with the specified detail
     * message, which provides more information about why this exception
     * has been thrown.
     *
     * <p>
     *  构造具有指定详细消息的UnrecoverableEntryException,该消息提供有关为什么抛出此异常的更多信息。
     * 
     * @param msg the detail message.
     */
   public UnrecoverableEntryException(String msg) {
       super(msg);
    }
}
