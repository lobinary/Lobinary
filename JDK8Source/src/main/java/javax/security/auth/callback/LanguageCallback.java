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

import java.util.Locale;

/**
 * <p> Underlying security services instantiate and pass a
 * {@code LanguageCallback} to the {@code handle}
 * method of a {@code CallbackHandler} to retrieve the {@code Locale}
 * used for localizing text.
 *
 * <p>
 *  <p>基础安全服务会实例化一个{@code LanguageCallback}并传递给{@code CallbackHandler}的{@code handle}方法,以检索用于本地化文本的{@code Locale}
 * 。
 * 
 * 
 * @see javax.security.auth.callback.CallbackHandler
 */
public class LanguageCallback implements Callback, java.io.Serializable {

    private static final long serialVersionUID = 2019050433478903213L;

    /**
    /* <p>
    /* 
     * @serial
     * @since 1.4
     */
    private Locale locale;

    /**
     * Construct a {@code LanguageCallback}.
     * <p>
     *  构造一个{@code LanguageCallback}。
     * 
     */
    public LanguageCallback() { }

    /**
     * Set the retrieved {@code Locale}.
     *
     * <p>
     *
     * <p>
     *  设置检索的{@code Locale}。
     * 
     * <p>
     * 
     * 
     * @param locale the retrieved {@code Locale}.
     *
     * @see #getLocale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Get the retrieved {@code Locale}.
     *
     * <p>
     *
     * <p>
     *  获取检索到的{@code Locale}。
     * 
     * 
     * @return the retrieved {@code Locale}, or null
     *          if no {@code Locale} could be retrieved.
     *
     * @see #setLocale
     */
    public Locale getLocale() {
        return locale;
    }
}
