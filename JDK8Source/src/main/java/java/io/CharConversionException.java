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
 * Base class for character conversion exceptions.
 *
 * <p>
 *  字符转换异常的基类。
 * 
 * 
 * @author      Asmus Freytag
 * @since       JDK1.1
 */
public class CharConversionException
    extends java.io.IOException
{
    private static final long serialVersionUID = -8680016352018427031L;

    /**
     * This provides no detailed message.
     * <p>
     *  这没有提供详细的消息。
     * 
     */
    public CharConversionException() {
    }
    /**
     * This provides a detailed message.
     *
     * <p>
     *  这提供了详细的消息。
     * 
     * @param s the detailed message associated with the exception.
     */
    public CharConversionException(String s) {
        super(s);
    }
}
