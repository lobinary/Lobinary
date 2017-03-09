/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

/**
 * Signals that an unrecoverable error has occurred.
 *
 * <p>
 *  表示发生了不可恢复的错误。
 * 
 * 
 * @author  Dave Bristor
 * @since   1.6
 */
public class ZipError extends InternalError {
    private static final long serialVersionUID = 853973422266861979L;

    /**
     * Constructs a ZipError with the given detail message.
     * <p>
     *  构造具有给定详细消息的ZipError。
     * 
     * @param s the {@code String} containing a detail message
     */
    public ZipError(String s) {
        super(s);
    }
}
