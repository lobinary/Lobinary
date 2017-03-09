/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2011, Oracle and/or its affiliates. All rights reserved.
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

package java.util.spi;

import java.util.Locale;

/**
 * An abstract class for service providers that
 * provide localized names for the
 * {@link java.util.Locale Locale} class.
 *
 * <p>
 *  为{@link java.util.Locale Locale}类提供本地化名称的服务提供程序的抽象类。
 * 
 * 
 * @since        1.6
 */
public abstract class LocaleNameProvider extends LocaleServiceProvider {

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected LocaleNameProvider() {
    }

    /**
     * Returns a localized name for the given <a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">
     * IETF BCP47</a> language code and the given locale that is appropriate for
     * display to the user.
     * For example, if <code>languageCode</code> is "fr" and <code>locale</code>
     * is en_US, getDisplayLanguage() will return "French"; if <code>languageCode</code>
     * is "en" and <code>locale</code> is fr_FR, getDisplayLanguage() will return "anglais".
     * If the name returned cannot be localized according to <code>locale</code>,
     * (say, the provider does not have a Japanese name for Croatian),
     * this method returns null.
     * <p>
     *  返回给定的<a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt"> IETF BCP47 </a>语言代码和适合显示的给定语言环境的本地化名称给用户
     * 。
     * 例如,如果<code> languageCode </code>是"fr"且<code> locale </code>是en_US,getDisplayLanguage()将返回"French";如果<code>
     *  languageCode </code>是"en"且<code> locale </code>是fr_FR,getDisplayLanguage()将返回"anglais"。
     * 如果返回的名称无法根据<code> locale </code>进行本地化(例如,提供程序没有克罗地亚语的日语名称),则此方法返回null。
     * 
     * 
     * @param languageCode the language code string in the form of two to eight
     *     lower-case letters between 'a' (U+0061) and 'z' (U+007A)
     * @param locale the desired locale
     * @return the name of the given language code for the specified locale, or null if it's not
     *     available.
     * @exception NullPointerException if <code>languageCode</code> or <code>locale</code> is null
     * @exception IllegalArgumentException if <code>languageCode</code> is not in the form of
     *     two or three lower-case letters, or <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @see java.util.Locale#getDisplayLanguage(java.util.Locale)
     */
    public abstract String getDisplayLanguage(String languageCode, Locale locale);

    /**
     * Returns a localized name for the given <a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">
     * IETF BCP47</a> script code and the given locale that is appropriate for
     * display to the user.
     * For example, if <code>scriptCode</code> is "Latn" and <code>locale</code>
     * is en_US, getDisplayScript() will return "Latin"; if <code>scriptCode</code>
     * is "Cyrl" and <code>locale</code> is fr_FR, getDisplayScript() will return "cyrillique".
     * If the name returned cannot be localized according to <code>locale</code>,
     * (say, the provider does not have a Japanese name for Cyrillic),
     * this method returns null. The default implementation returns null.
     * <p>
     * 返回给定的<a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt"> IETF BCP47 </a>脚本代码和适合显示的给定语言环境的本地化名称给用户。
     * 例如,如果<code> scriptCode </code>是"Latn",<code> locale </code>是en_US,getDisplayScript()将返回"Latin";如果<code>
     *  scriptCode </code>是"Cyrl",<code> locale </code>是fr_FR,getDisplayScript()将返回"cyrillique"。
     * 如果返回的名称无法根据<code> locale </code>进行本地化(例如,提供程序没有Cyrillic的日语名称),则此方法返回null。默认实现返回null。
     * 
     * 
     * @param scriptCode the four letter script code string in the form of title-case
     *     letters (the first letter is upper-case character between 'A' (U+0041) and
     *     'Z' (U+005A) followed by three lower-case character between 'a' (U+0061)
     *     and 'z' (U+007A)).
     * @param locale the desired locale
     * @return the name of the given script code for the specified locale, or null if it's not
     *     available.
     * @exception NullPointerException if <code>scriptCode</code> or <code>locale</code> is null
     * @exception IllegalArgumentException if <code>scriptCode</code> is not in the form of
     *     four title case letters, or <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @see java.util.Locale#getDisplayScript(java.util.Locale)
     * @since 1.7
     */
    public String getDisplayScript(String scriptCode, Locale locale) {
        return null;
    }

    /**
     * Returns a localized name for the given <a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt">
     * IETF BCP47</a> region code (either ISO 3166 country code or UN M.49 area
     * codes) and the given locale that is appropriate for display to the user.
     * For example, if <code>countryCode</code> is "FR" and <code>locale</code>
     * is en_US, getDisplayCountry() will return "France"; if <code>countryCode</code>
     * is "US" and <code>locale</code> is fr_FR, getDisplayCountry() will return "Etats-Unis".
     * If the name returned cannot be localized according to <code>locale</code>,
     * (say, the provider does not have a Japanese name for Croatia),
     * this method returns null.
     * <p>
     *  返回给定的<a href="http://www.rfc-editor.org/rfc/bcp/bcp47.txt"> IETF BCP47 </a>地区代码的本地化名称(ISO 3166国家代码或U
     * N M .49区域代码)和适合显示给用户的给定区域设置。
     * 例如,如果<code> countryCode </code>是"FR",<code> locale </code>是en_US,getDisplayCountry()将返回"France";如果<code>
     *  countryCode </code>为"US"且<code> locale </code>为fr_FR,getDisplayCountry()将返回"Etats-Unis"。
     * 如果返回的名称无法根据<code> locale </code>进行本地化(例如,提供程序没有克罗地亚的日语名称),则此方法返回null。
     * 
     * @param countryCode the country(region) code string in the form of two
     *     upper-case letters between 'A' (U+0041) and 'Z' (U+005A) or the UN M.49 area code
     *     in the form of three digit letters between '0' (U+0030) and '9' (U+0039).
     * @param locale the desired locale
     * @return the name of the given country code for the specified locale, or null if it's not
     *     available.
     * @exception NullPointerException if <code>countryCode</code> or <code>locale</code> is null
     * @exception IllegalArgumentException if <code>countryCode</code> is not in the form of
     *     two upper-case letters or three digit letters, or <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @see java.util.Locale#getDisplayCountry(java.util.Locale)
     */
    public abstract String getDisplayCountry(String countryCode, Locale locale);

    /**
     * Returns a localized name for the given variant code and the given locale that
     * is appropriate for display to the user.
     * If the name returned cannot be localized according to <code>locale</code>,
     * this method returns null.
     * <p>
     * 
     * 
     * @param variant the variant string
     * @param locale the desired locale
     * @return the name of the given variant string for the specified locale, or null if it's not
     *     available.
     * @exception NullPointerException if <code>variant</code> or <code>locale</code> is null
     * @exception IllegalArgumentException if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @see java.util.Locale#getDisplayVariant(java.util.Locale)
     */
    public abstract String getDisplayVariant(String variant, Locale locale);
}
