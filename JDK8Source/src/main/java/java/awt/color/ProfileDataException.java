/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.color;

/**
 * This exception is thrown when an error occurs in accessing or
 * processing an ICC_Profile object.
 * <p>
 *  当访问或处理ICC_Profile对象时发生错误时抛出此异常。
 * 
 */

public class ProfileDataException extends java.lang.RuntimeException {

    /**
     *  Constructs a ProfileDataException with the specified detail message.
     * <p>
     *  构造具有指定详细消息的ProfileDataException。
     * 
     *  @param s the specified detail message
     */
    public ProfileDataException(String s) {
        super (s);
    }
}
