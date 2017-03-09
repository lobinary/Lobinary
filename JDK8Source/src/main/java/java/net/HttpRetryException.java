/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Thrown to indicate that a HTTP request needs to be retried
 * but cannot be retried automatically, due to streaming mode
 * being enabled.
 *
 * <p>
 *  抛出以表明HTTP请求需要重试,但不能自动重试,因为流模式已启用。
 * 
 * 
 * @author  Michael McMahon
 * @since   1.5
 */
public
class HttpRetryException extends IOException {
    private static final long serialVersionUID = -9186022286469111381L;

    private int responseCode;
    private String location;

    /**
     * Constructs a new {@code HttpRetryException} from the
     * specified response code and exception detail message
     *
     * <p>
     *  从指定的响应代码和异常详细消息构造新的{@code HttpRetryException}
     * 
     * 
     * @param   detail   the detail message.
     * @param   code   the HTTP response code from server.
     */
    public HttpRetryException(String detail, int code) {
        super(detail);
        responseCode = code;
    }

    /**
     * Constructs a new {@code HttpRetryException} with detail message
     * responseCode and the contents of the Location response header field.
     *
     * <p>
     *  构造一个带有详细消息responseCode的新{@code HttpRetryException}以及Location响应头字段的内容。
     * 
     * 
     * @param   detail   the detail message.
     * @param   code   the HTTP response code from server.
     * @param   location   the URL to be redirected to
     */
    public HttpRetryException(String detail, int code, String location) {
        super (detail);
        responseCode = code;
        this.location = location;
    }

    /**
     * Returns the http response code
     *
     * <p>
     *  返回http响应代码
     * 
     * 
     * @return  The http response code.
     */
    public int responseCode() {
        return responseCode;
    }

    /**
     * Returns a string explaining why the http request could
     * not be retried.
     *
     * <p>
     *  返回一个字符串,解释为什么不能重试http请求。
     * 
     * 
     * @return  The reason string
     */
    public String getReason() {
        return super.getMessage();
    }

    /**
     * Returns the value of the Location header field if the
     * error resulted from redirection.
     *
     * <p>
     *  如果重定向导致错误,则返回Location头字段的值。
     * 
     * @return The location string
     */
    public String getLocation() {
        return location;
    }
}
