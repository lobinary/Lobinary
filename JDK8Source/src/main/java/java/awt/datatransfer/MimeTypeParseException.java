/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.datatransfer;


/**
 *    A class to encapsulate MimeType parsing related exceptions
 *
 * <p>
 *  一个类来封装MimeType解析相关的异常
 * 
 * 
 * @serial exclude
 * @since 1.3
 */
public class MimeTypeParseException extends Exception {

    // use serialVersionUID from JDK 1.2.2 for interoperability
    private static final long serialVersionUID = -5604407764691570741L;

    /**
     * Constructs a MimeTypeParseException with no specified detail message.
     * <p>
     *  构造没有指定详细消息的MimeTypeParseException。
     * 
     */
    public MimeTypeParseException() {
        super();
    }

    /**
     * Constructs a MimeTypeParseException with the specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的MimeTypeParseException。
     * 
     * @param   s   the detail message.
     */
    public MimeTypeParseException(String s) {
        super(s);
    }
} // class MimeTypeParseException
