/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2008, Oracle and/or its affiliates. All rights reserved.
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
package java.io;

/**
 * The Character Encoding is not supported.
 *
 * <p>
 *  不支持字符编码。
 * 
 * 
 * @author  Asmus Freytag
 * @since   JDK1.1
 */
public class UnsupportedEncodingException
    extends IOException
{
    private static final long serialVersionUID = -4274276298326136670L;

    /**
     * Constructs an UnsupportedEncodingException without a detail message.
     * <p>
     *  构造UnsupportedEncodingException,不包含详细信息。
     * 
     */
    public UnsupportedEncodingException() {
        super();
    }

    /**
     * Constructs an UnsupportedEncodingException with a detail message.
     * <p>
     *  构造带有详细消息的UnsupportedEncodingException。
     * 
     * @param s Describes the reason for the exception.
     */
    public UnsupportedEncodingException(String s) {
        super(s);
    }
}
