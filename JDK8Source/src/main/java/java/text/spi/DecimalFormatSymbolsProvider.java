/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2012, Oracle and/or its affiliates. All rights reserved.
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

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.spi.LocaleServiceProvider;

/**
 * An abstract class for service providers that
 * provide instances of the
 * {@link java.text.DecimalFormatSymbols DecimalFormatSymbols} class.
 *
 * <p>The requested {@code Locale} may contain an <a
 * href="../../util/Locale.html#def_locale_extension"> extension</a> for
 * specifying the desired numbering system. For example, {@code "ar-u-nu-arab"}
 * (in the BCP 47 language tag form) specifies Arabic with the Arabic-Indic
 * digits and symbols, while {@code "ar-u-nu-latn"} specifies Arabic with the
 * Latin digits and symbols. Refer to the <em>Unicode Locale Data Markup
 * Language (LDML)</em> specification for numbering systems.
 *
 * <p>
 *  用于提供{@link java.text.DecimalFormatSymbols DecimalFormatSymbols}类实例的服务提供程序的抽象类。
 * 
 *  <p>所请求的{@code Locale}可能包含用于指定所需编号系统的<a href="../../util/Locale.html#def_locale_extension">扩展</a>。
 * 例如,{@code"ar-u-nu-arab"}(以BCP 47语言标签形式)指定带有Arabic-Indic数字和符号的阿拉伯语,而{@code"ar-u-nu-latn"}指定带拉丁数字和符号的阿拉
 * 伯语。
 *  <p>所请求的{@code Locale}可能包含用于指定所需编号系统的<a href="../../util/Locale.html#def_locale_extension">扩展</a>。
 * 有关编号系统,请参阅<em> Unicode区域设置数据标记语言(LDML)</em>规范。
 * 
 * @since        1.6
 * @see Locale#forLanguageTag(String)
 * @see Locale#getExtension(char)
 */
public abstract class DecimalFormatSymbolsProvider extends LocaleServiceProvider {

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     * 
     */
    protected DecimalFormatSymbolsProvider() {
    }

    /**
     * Returns a new <code>DecimalFormatSymbols</code> instance for the
     * specified locale.
     *
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     * 
     * @param locale the desired locale
     * @exception NullPointerException if <code>locale</code> is null
     * @exception IllegalArgumentException if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @return a <code>DecimalFormatSymbols</code> instance.
     * @see java.text.DecimalFormatSymbols#getInstance(java.util.Locale)
     */
    public abstract DecimalFormatSymbols getInstance(Locale locale);
}
