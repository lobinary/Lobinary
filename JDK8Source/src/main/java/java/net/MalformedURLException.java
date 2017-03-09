/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.IOException;

/**
 * Thrown to indicate that a malformed URL has occurred. Either no
 * legal protocol could be found in a specification string or the
 * string could not be parsed.
 *
 * <p>
 *  抛出以指示发生了格式错误的网址。在规范字符串中找不到法定协议,或者无法解析字符串。
 * 
 * 
 * @author  Arthur van Hoff
 * @since   JDK1.0
 */
public class MalformedURLException extends IOException {
    private static final long serialVersionUID = -182787522200415866L;

    /**
     * Constructs a {@code MalformedURLException} with no detail message.
     * <p>
     *  构造一个没有详细消息的{@code MalformedURLException}。
     * 
     */
    public MalformedURLException() {
    }

    /**
     * Constructs a {@code MalformedURLException} with the
     * specified detail message.
     *
     * <p>
     *  使用指定的详细消息构造{@code MalformedURLException}。
     * 
     * @param   msg   the detail message.
     */
    public MalformedURLException(String msg) {
        super(msg);
    }
}
