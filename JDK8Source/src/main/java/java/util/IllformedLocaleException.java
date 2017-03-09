/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

/*
 *******************************************************************************
 * Copyright (C) 2009-2010, International Business Machines Corporation and    *
 * others. All Rights Reserved.                                                *
 *******************************************************************************
 * <p>
 *  **************************************************** ***************************版权所有(C)2009-2010,国际商
 * 业机器公司和*其他。
 * 版权所有。 * ************************************************* ****************************。
 * 
 */

package java.util;

/**
 * Thrown by methods in {@link Locale} and {@link Locale.Builder} to
 * indicate that an argument is not a well-formed BCP 47 tag.
 *
 * <p>
 *  由{@link Locale}和{@link Locale.Builder}中的方法抛出,表明参数不是格式正确的BCP 47标记。
 * 
 * 
 * @see Locale
 * @since 1.7
 */
public class IllformedLocaleException extends RuntimeException {

    private static final long serialVersionUID = -5245986824925681401L;

    private int _errIdx = -1;

    /**
     * Constructs a new <code>IllformedLocaleException</code> with no
     * detail message and -1 as the error index.
     * <p>
     *  构造一个没有详细消息的新<code> IllformedLocaleException </code>,并将-1作为错误索引。
     * 
     */
    public IllformedLocaleException() {
        super();
    }

    /**
     * Constructs a new <code>IllformedLocaleException</code> with the
     * given message and -1 as the error index.
     *
     * <p>
     *  使用给定消息构造新的<code> IllformedLocaleException </code>,并将-1作为错误索引。
     * 
     * 
     * @param message the message
     */
    public IllformedLocaleException(String message) {
        super(message);
    }

    /**
     * Constructs a new <code>IllformedLocaleException</code> with the
     * given message and the error index.  The error index is the approximate
     * offset from the start of the ill-formed value to the point where the
     * parse first detected an error.  A negative error index value indicates
     * either the error index is not applicable or unknown.
     *
     * <p>
     *  使用给定的消息和错误索引构造新的<code> IllformedLocaleException </code>。误差索引是从不良形式值的开始到解析器首次检测到错误的点的近似偏移。
     * 负误差索引值指示错误索引不适用或未知。
     * 
     * 
     * @param message the message
     * @param errorIndex the index
     */
    public IllformedLocaleException(String message, int errorIndex) {
        super(message + ((errorIndex < 0) ? "" : " [at index " + errorIndex + "]"));
        _errIdx = errorIndex;
    }

    /**
     * Returns the index where the error was found. A negative value indicates
     * either the error index is not applicable or unknown.
     *
     * <p>
     * 
     * @return the error index
     */
    public int getErrorIndex() {
        return _errIdx;
    }
}
