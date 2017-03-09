/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

package java.text.spi;

import java.text.DateFormat;
import java.util.Locale;
import java.util.spi.LocaleServiceProvider;

/**
 * An abstract class for service providers that
 * provide concrete implementations of the
 * {@link java.text.DateFormat DateFormat} class.
 *
 * <p>
 *  服务提供者的抽象类,提供{@link java.text.DateFormat DateFormat}类的具体实现。
 * 
 * 
 * @since        1.6
 */
public abstract class DateFormatProvider extends LocaleServiceProvider {

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected DateFormatProvider() {
    }

    /**
     * Returns a new <code>DateFormat</code> instance which formats time
     * with the given formatting style for the specified locale.
     * <p>
     *  返回一个新的<code> DateFormat </code>实例,该实例使用指定语言环境的给定格式化风格来格式化时间。
     * 
     * 
     * @param style the given formatting style.  Either one of
     *     {@link java.text.DateFormat#SHORT DateFormat.SHORT},
     *     {@link java.text.DateFormat#MEDIUM DateFormat.MEDIUM},
     *     {@link java.text.DateFormat#LONG DateFormat.LONG}, or
     *     {@link java.text.DateFormat#FULL DateFormat.FULL}.
     * @param locale the desired locale.
     * @exception IllegalArgumentException if <code>style</code> is invalid,
     *     or if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @exception NullPointerException if <code>locale</code> is null
     * @return a time formatter.
     * @see java.text.DateFormat#getTimeInstance(int, java.util.Locale)
     */
    public abstract DateFormat getTimeInstance(int style, Locale locale);

    /**
     * Returns a new <code>DateFormat</code> instance which formats date
     * with the given formatting style for the specified locale.
     * <p>
     *  返回一个新的<code> DateFormat </code>实例,该实例使用指定语言环境的给定格式化样式来格式化日期。
     * 
     * 
     * @param style the given formatting style.  Either one of
     *     {@link java.text.DateFormat#SHORT DateFormat.SHORT},
     *     {@link java.text.DateFormat#MEDIUM DateFormat.MEDIUM},
     *     {@link java.text.DateFormat#LONG DateFormat.LONG}, or
     *     {@link java.text.DateFormat#FULL DateFormat.FULL}.
     * @param locale the desired locale.
     * @exception IllegalArgumentException if <code>style</code> is invalid,
     *     or if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @exception NullPointerException if <code>locale</code> is null
     * @return a date formatter.
     * @see java.text.DateFormat#getDateInstance(int, java.util.Locale)
     */
    public abstract DateFormat getDateInstance(int style, Locale locale);

    /**
     * Returns a new <code>DateFormat</code> instance which formats date and time
     * with the given formatting style for the specified locale.
     * <p>
     *  返回一个新的<code> DateFormat </code>实例,该实例使用指定语言环境的给定格式化样式来格式化日期和时间。
     * 
     * @param dateStyle the given date formatting style.  Either one of
     *     {@link java.text.DateFormat#SHORT DateFormat.SHORT},
     *     {@link java.text.DateFormat#MEDIUM DateFormat.MEDIUM},
     *     {@link java.text.DateFormat#LONG DateFormat.LONG}, or
     *     {@link java.text.DateFormat#FULL DateFormat.FULL}.
     * @param timeStyle the given time formatting style.  Either one of
     *     {@link java.text.DateFormat#SHORT DateFormat.SHORT},
     *     {@link java.text.DateFormat#MEDIUM DateFormat.MEDIUM},
     *     {@link java.text.DateFormat#LONG DateFormat.LONG}, or
     *     {@link java.text.DateFormat#FULL DateFormat.FULL}.
     * @param locale the desired locale.
     * @exception IllegalArgumentException if <code>dateStyle</code> or
     *     <code>timeStyle</code> is invalid,
     *     or if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @exception NullPointerException if <code>locale</code> is null
     * @return a date/time formatter.
     * @see java.text.DateFormat#getDateTimeInstance(int, int, java.util.Locale)
     */
    public abstract DateFormat
        getDateTimeInstance(int dateStyle, int timeStyle, Locale locale);
}
