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

import java.text.NumberFormat;
import java.util.Locale;
import java.util.spi.LocaleServiceProvider;

/**
 * An abstract class for service providers that
 * provide concrete implementations of the
 * {@link java.text.NumberFormat NumberFormat} class.
 *
 * <p>
 *  服务提供者的抽象类,提供{@link java.text.NumberFormat NumberFormat}类的具体实现。
 * 
 * 
 * @since        1.6
 */
public abstract class NumberFormatProvider extends LocaleServiceProvider {

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected NumberFormatProvider() {
    }

    /**
     * Returns a new <code>NumberFormat</code> instance which formats
     * monetary values for the specified locale.
     *
     * <p>
     *  返回一个新的<code> NumberFormat </code>实例,用于为指定的语言环境设置货币值的格式。
     * 
     * 
     * @param locale the desired locale.
     * @exception NullPointerException if <code>locale</code> is null
     * @exception IllegalArgumentException if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @return a currency formatter
     * @see java.text.NumberFormat#getCurrencyInstance(java.util.Locale)
     */
    public abstract NumberFormat getCurrencyInstance(Locale locale);

    /**
     * Returns a new <code>NumberFormat</code> instance which formats
     * integer values for the specified locale.
     * The returned number format is configured to
     * round floating point numbers to the nearest integer using
     * half-even rounding (see {@link java.math.RoundingMode#HALF_EVEN HALF_EVEN})
     * for formatting, and to parse only the integer part of
     * an input string (see {@link
     * java.text.NumberFormat#isParseIntegerOnly isParseIntegerOnly}).
     *
     * <p>
     *  返回一个新的<code> NumberFormat </code>实例,用于格式化指定语言环境的整数值。
     * 返回的数字格式配置为使用半整数舍入(参见{@link java.math.RoundingMode#HALF_EVEN HALF_EVEN})格式化浮点数到最接近的整数,并且仅解析输入字符串的整数部分请
     * 参阅{@link java.text.NumberFormat#isParseIntegerOnly isParseIntegerOnly})。
     *  返回一个新的<code> NumberFormat </code>实例,用于格式化指定语言环境的整数值。
     * 
     * 
     * @param locale the desired locale
     * @exception NullPointerException if <code>locale</code> is null
     * @exception IllegalArgumentException if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @return a number format for integer values
     * @see java.text.NumberFormat#getIntegerInstance(java.util.Locale)
     */
    public abstract NumberFormat getIntegerInstance(Locale locale);

    /**
     * Returns a new general-purpose <code>NumberFormat</code> instance for
     * the specified locale.
     *
     * <p>
     *  返回指定语言环境的新通用<code> NumberFormat </code>实例。
     * 
     * 
     * @param locale the desired locale
     * @exception NullPointerException if <code>locale</code> is null
     * @exception IllegalArgumentException if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @return a general-purpose number formatter
     * @see java.text.NumberFormat#getNumberInstance(java.util.Locale)
     */
    public abstract NumberFormat getNumberInstance(Locale locale);

    /**
     * Returns a new <code>NumberFormat</code> instance which formats
     * percentage values for the specified locale.
     *
     * <p>
     *  返回一个新的<code> NumberFormat </code>实例,用于格式化指定语言环境的百分比值。
     * 
     * @param locale the desired locale
     * @exception NullPointerException if <code>locale</code> is null
     * @exception IllegalArgumentException if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @return a percent formatter
     * @see java.text.NumberFormat#getPercentInstance(java.util.Locale)
     */
    public abstract NumberFormat getPercentInstance(Locale locale);
}
