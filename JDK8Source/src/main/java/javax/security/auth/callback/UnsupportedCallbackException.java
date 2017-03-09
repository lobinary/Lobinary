/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.auth.callback;

/**
 * Signals that a {@code CallbackHandler} does not
 * recognize a particular {@code Callback}.
 *
 * <p>
 *  表示{@code CallbackHandler}无法识别特定的{@code Callback}。
 * 
 */
public class UnsupportedCallbackException extends Exception {

    private static final long serialVersionUID = -6873556327655666839L;

    /**
    /* <p>
    /* 
     * @serial
     */
    private Callback callback;

    /**
     * Constructs a {@code UnsupportedCallbackException}
     * with no detail message.
     *
     * <p>
     *
     * <p>
     *  构造一个没有详细消息的{@code UnsupportedCallbackException}。
     * 
     * <p>
     * 
     * 
     * @param callback the unrecognized {@code Callback}.
     */
    public UnsupportedCallbackException(Callback callback) {
        super();
        this.callback = callback;
    }

    /**
     * Constructs a UnsupportedCallbackException with the specified detail
     * message.  A detail message is a String that describes this particular
     * exception.
     *
     * <p>
     *
     * <p>
     *  使用指定的详细消息构造UnsupportedCallbackException。详细消息是描述此特殊异常的字符串。
     * 
     * <p>
     * 
     * 
     * @param callback the unrecognized {@code Callback}. <p>
     *
     * @param msg the detail message.
     */
    public UnsupportedCallbackException(Callback callback, String msg) {
        super(msg);
        this.callback = callback;
    }

    /**
     * Get the unrecognized {@code Callback}.
     *
     * <p>
     *
     * <p>
     *  获取无法识别的{@code Callback}。
     * 
     * 
     * @return the unrecognized {@code Callback}.
     */
    public Callback getCallback() {
        return callback;
    }
}
