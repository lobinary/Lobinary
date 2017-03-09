/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.spi.DecimalFormatSymbolsProvider;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleServiceProviderPool;
import sun.util.locale.provider.ResourceBundleBasedAdapter;

/**
 * This class represents the set of symbols (such as the decimal separator,
 * the grouping separator, and so on) needed by <code>DecimalFormat</code>
 * to format numbers. <code>DecimalFormat</code> creates for itself an instance of
 * <code>DecimalFormatSymbols</code> from its locale data.  If you need to change any
 * of these symbols, you can get the <code>DecimalFormatSymbols</code> object from
 * your <code>DecimalFormat</code> and modify it.
 *
 * <p>
 *  此类表示由<code> DecimalFormat </code>格式化数字所需的符号集(如小数分隔符,分组分隔符等)。
 *  <code> DecimalFormat </code>为其本身创建一个<code> DecimalFormatSymbols </code>实例。
 * 如果需要更改这些符号,可以从<code> DecimalFormat </code>中获取<code> DecimalFormatSymbols </code>对象并进行修改。
 * 
 * 
 * @see          java.util.Locale
 * @see          DecimalFormat
 * @author       Mark Davis
 * @author       Alan Liu
 */

public class DecimalFormatSymbols implements Cloneable, Serializable {

    /**
     * Create a DecimalFormatSymbols object for the default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * This constructor can only construct instances for the locales
     * supported by the Java runtime environment, not for those
     * supported by installed
     * {@link java.text.spi.DecimalFormatSymbolsProvider DecimalFormatSymbolsProvider}
     * implementations. For full locale coverage, use the
     * {@link #getInstance(Locale) getInstance} method.
     * <p>This is equivalent to calling
     * {@link #DecimalFormatSymbols(Locale)
     *     DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     * 为默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置创建一个DecimalFormatSymbols对象。
     * 此构造函数只能为Java运行时环境支持的语言环境构造实例,而不能为已安装的{@link java.text.spi.DecimalFormatSymbolsProvider DecimalFormatSymbolsProvider}
     * 实现支持的语言环境构建实例。
     * 为默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置创建一个DecimalFormatSymbols对象。
     * 对于完整的区域覆盖,请使用{@link #getInstance(Locale)getInstance}方法。
     *  <p>这相当于调用{@link #DecimalFormatSymbols(Locale)DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     * 对于完整的区域覆盖,请使用{@link #getInstance(Locale)getInstance}方法。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     */
    public DecimalFormatSymbols() {
        initialize( Locale.getDefault(Locale.Category.FORMAT) );
    }

    /**
     * Create a DecimalFormatSymbols object for the given locale.
     * This constructor can only construct instances for the locales
     * supported by the Java runtime environment, not for those
     * supported by installed
     * {@link java.text.spi.DecimalFormatSymbolsProvider DecimalFormatSymbolsProvider}
     * implementations. For full locale coverage, use the
     * {@link #getInstance(Locale) getInstance} method.
     * If the specified locale contains the {@link java.util.Locale#UNICODE_LOCALE_EXTENSION}
     * for the numbering system, the instance is initialized with the specified numbering
     * system if the JRE implementation supports it. For example,
     * <pre>
     * NumberFormat.getNumberInstance(Locale.forLanguageTag("th-TH-u-nu-thai"))
     * </pre>
     * This may return a {@code NumberFormat} instance with the Thai numbering system,
     * instead of the Latin numbering system.
     *
     * <p>
     *  为给定的区域设置创建一个DecimalFormatSymbols对象。
     * 此构造函数只能为Java运行时环境支持的语言环境构造实例,而不能为已安装的{@link java.text.spi.DecimalFormatSymbolsProvider DecimalFormatSymbolsProvider}
     * 实现支持的语言环境构建实例。
     *  为给定的区域设置创建一个DecimalFormatSymbols对象。对于完整的区域覆盖,请使用{@link #getInstance(Locale)getInstance}方法。
     * 如果指定的区域设置包含编号系统的{@link java.util.Locale#UNICODE_LOCALE_EXTENSION},如果JRE实现支持,则使用指定的编号系统初始化实例。例如,。
     * <pre>
     *  NumberFormat.getNumberInstance(Locale.forLanguageTag("th-TH-u-nu-thai"))
     * </pre>
     *  这可能返回一个带有泰语编号系统的{@code NumberFormat}实例,而不是拉丁语编号系统。
     * 
     * 
     * @param locale the desired locale
     * @exception NullPointerException if <code>locale</code> is null
     */
    public DecimalFormatSymbols( Locale locale ) {
        initialize( locale );
    }

    /**
     * Returns an array of all locales for which the
     * <code>getInstance</code> methods of this class can return
     * localized instances.
     * The returned array represents the union of locales supported by the Java
     * runtime and by installed
     * {@link java.text.spi.DecimalFormatSymbolsProvider DecimalFormatSymbolsProvider}
     * implementations.  It must contain at least a <code>Locale</code>
     * instance equal to {@link java.util.Locale#US Locale.US}.
     *
     * <p>
     * 返回所有语言环境的数组,其中此类的<code> getInstance </code>方法可以返回本地化实例。
     * 返回的数组表示Java运行时和安装的{@link java.text.spi.DecimalFormatSymbolsProvider DecimalFormatSymbolsProvider}实现支持
     * 的区域的联合。
     * 返回所有语言环境的数组,其中此类的<code> getInstance </code>方法可以返回本地化实例。
     * 它必须至少包含等于{@link java.util.Locale#US Locale.US}的<code> Locale </code>实例。
     * 
     * 
     * @return an array of locales for which localized
     *         <code>DecimalFormatSymbols</code> instances are available.
     * @since 1.6
     */
    public static Locale[] getAvailableLocales() {
        LocaleServiceProviderPool pool =
            LocaleServiceProviderPool.getPool(DecimalFormatSymbolsProvider.class);
        return pool.getAvailableLocales();
    }

    /**
     * Gets the <code>DecimalFormatSymbols</code> instance for the default
     * locale.  This method provides access to <code>DecimalFormatSymbols</code>
     * instances for locales supported by the Java runtime itself as well
     * as for those supported by installed
     * {@link java.text.spi.DecimalFormatSymbolsProvider
     * DecimalFormatSymbolsProvider} implementations.
     * <p>This is equivalent to calling
     * {@link #getInstance(Locale)
     *     getInstance(Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     *  获取默认语言环境的<code> DecimalFormatSymbols </code>实例。
     * 此方法提供对Java运行时本身支持的语言环境以及已安装的{@link java.text.spi.DecimalFormatSymbolsProvider DecimalFormatSymbolsProvider}
     * 实现支持的语言环境的<code> DecimalFormatSymbols </code>实例的访问。
     *  获取默认语言环境的<code> DecimalFormatSymbols </code>实例。
     *  <p>这相当于调用{@link #getInstance(Locale)getInstance(Locale.getDefault(Locale.Category.FORMAT))}。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @return a <code>DecimalFormatSymbols</code> instance.
     * @since 1.6
     */
    public static final DecimalFormatSymbols getInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets the <code>DecimalFormatSymbols</code> instance for the specified
     * locale.  This method provides access to <code>DecimalFormatSymbols</code>
     * instances for locales supported by the Java runtime itself as well
     * as for those supported by installed
     * {@link java.text.spi.DecimalFormatSymbolsProvider
     * DecimalFormatSymbolsProvider} implementations.
     * If the specified locale contains the {@link java.util.Locale#UNICODE_LOCALE_EXTENSION}
     * for the numbering system, the instance is initialized with the specified numbering
     * system if the JRE implementation supports it. For example,
     * <pre>
     * NumberFormat.getNumberInstance(Locale.forLanguageTag("th-TH-u-nu-thai"))
     * </pre>
     * This may return a {@code NumberFormat} instance with the Thai numbering system,
     * instead of the Latin numbering system.
     *
     * <p>
     *  获取指定语言环境的<code> DecimalFormatSymbols </code>实例。
     * 此方法提供对Java运行时本身支持的语言环境以及已安装的{@link java.text.spi.DecimalFormatSymbolsProvider DecimalFormatSymbolsProvider}
     * 实现支持的语言环境的<code> DecimalFormatSymbols </code>实例的访问。
     *  获取指定语言环境的<code> DecimalFormatSymbols </code>实例。
     * 如果指定的区域设置包含编号系统的{@link java.util.Locale#UNICODE_LOCALE_EXTENSION},如果JRE实现支持,则使用指定的编号系统初始化实例。例如,。
     * <pre>
     * NumberFormat.getNumberInstance(Locale.forLanguageTag("th-TH-u-nu-thai"))
     * </pre>
     *  这可能返回一个带有泰语编号系统的{@code NumberFormat}实例,而不是拉丁语编号系统。
     * 
     * 
     * @param locale the desired locale.
     * @return a <code>DecimalFormatSymbols</code> instance.
     * @exception NullPointerException if <code>locale</code> is null
     * @since 1.6
     */
    public static final DecimalFormatSymbols getInstance(Locale locale) {
        LocaleProviderAdapter adapter;
        adapter = LocaleProviderAdapter.getAdapter(DecimalFormatSymbolsProvider.class, locale);
        DecimalFormatSymbolsProvider provider = adapter.getDecimalFormatSymbolsProvider();
        DecimalFormatSymbols dfsyms = provider.getInstance(locale);
        if (dfsyms == null) {
            provider = LocaleProviderAdapter.forJRE().getDecimalFormatSymbolsProvider();
            dfsyms = provider.getInstance(locale);
        }
        return dfsyms;
    }

    /**
     * Gets the character used for zero. Different for Arabic, etc.
     *
     * <p>
     *  获取用于零的字符。不同于阿拉伯语等
     * 
     * 
     * @return the character used for zero
     */
    public char getZeroDigit() {
        return zeroDigit;
    }

    /**
     * Sets the character used for zero. Different for Arabic, etc.
     *
     * <p>
     *  设置用于零的字符。不同于阿拉伯语等
     * 
     * 
     * @param zeroDigit the character used for zero
     */
    public void setZeroDigit(char zeroDigit) {
        this.zeroDigit = zeroDigit;
    }

    /**
     * Gets the character used for thousands separator. Different for French, etc.
     *
     * <p>
     *  获取用于千位分隔符的字符。不同于法语等
     * 
     * 
     * @return the grouping separator
     */
    public char getGroupingSeparator() {
        return groupingSeparator;
    }

    /**
     * Sets the character used for thousands separator. Different for French, etc.
     *
     * <p>
     *  设置用于千位分隔符的字符。不同于法语等
     * 
     * 
     * @param groupingSeparator the grouping separator
     */
    public void setGroupingSeparator(char groupingSeparator) {
        this.groupingSeparator = groupingSeparator;
    }

    /**
     * Gets the character used for decimal sign. Different for French, etc.
     *
     * <p>
     *  获取用于十进制符号的字符。不同于法语等
     * 
     * 
     * @return the character used for decimal sign
     */
    public char getDecimalSeparator() {
        return decimalSeparator;
    }

    /**
     * Sets the character used for decimal sign. Different for French, etc.
     *
     * <p>
     *  设置用于十进制符号的字符。不同于法语等
     * 
     * 
     * @param decimalSeparator the character used for decimal sign
     */
    public void setDecimalSeparator(char decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    /**
     * Gets the character used for per mille sign. Different for Arabic, etc.
     *
     * <p>
     *  获取用于每英里符号的字符。不同于阿拉伯语等
     * 
     * 
     * @return the character used for per mille sign
     */
    public char getPerMill() {
        return perMill;
    }

    /**
     * Sets the character used for per mille sign. Different for Arabic, etc.
     *
     * <p>
     *  设置每英里符号使用的字符。不同于阿拉伯语等
     * 
     * 
     * @param perMill the character used for per mille sign
     */
    public void setPerMill(char perMill) {
        this.perMill = perMill;
    }

    /**
     * Gets the character used for percent sign. Different for Arabic, etc.
     *
     * <p>
     *  获取用于百分号的字符。不同于阿拉伯语等
     * 
     * 
     * @return the character used for percent sign
     */
    public char getPercent() {
        return percent;
    }

    /**
     * Sets the character used for percent sign. Different for Arabic, etc.
     *
     * <p>
     *  设置用于百分号的字符。不同于阿拉伯语等
     * 
     * 
     * @param percent the character used for percent sign
     */
    public void setPercent(char percent) {
        this.percent = percent;
    }

    /**
     * Gets the character used for a digit in a pattern.
     *
     * <p>
     *  获取模式中用于数字的字符。
     * 
     * 
     * @return the character used for a digit in a pattern
     */
    public char getDigit() {
        return digit;
    }

    /**
     * Sets the character used for a digit in a pattern.
     *
     * <p>
     *  设置模式中用于数字的字符。
     * 
     * 
     * @param digit the character used for a digit in a pattern
     */
    public void setDigit(char digit) {
        this.digit = digit;
    }

    /**
     * Gets the character used to separate positive and negative subpatterns
     * in a pattern.
     *
     * <p>
     *  获取用于在模式中分隔正和负子模式的字符。
     * 
     * 
     * @return the pattern separator
     */
    public char getPatternSeparator() {
        return patternSeparator;
    }

    /**
     * Sets the character used to separate positive and negative subpatterns
     * in a pattern.
     *
     * <p>
     *  设置用于分隔模式中正和负子模式的字符。
     * 
     * 
     * @param patternSeparator the pattern separator
     */
    public void setPatternSeparator(char patternSeparator) {
        this.patternSeparator = patternSeparator;
    }

    /**
     * Gets the string used to represent infinity. Almost always left
     * unchanged.
     *
     * <p>
     *  获取用于表示无穷大的字符串。几乎总是保持不变。
     * 
     * 
     * @return the string representing infinity
     */
    public String getInfinity() {
        return infinity;
    }

    /**
     * Sets the string used to represent infinity. Almost always left
     * unchanged.
     *
     * <p>
     *  设置用于表示无穷大的字符串。几乎总是保持不变。
     * 
     * 
     * @param infinity the string representing infinity
     */
    public void setInfinity(String infinity) {
        this.infinity = infinity;
    }

    /**
     * Gets the string used to represent "not a number". Almost always left
     * unchanged.
     *
     * <p>
     *  获取用于表示"不是数字"的字符串。几乎总是保持不变。
     * 
     * 
     * @return the string representing "not a number"
     */
    public String getNaN() {
        return NaN;
    }

    /**
     * Sets the string used to represent "not a number". Almost always left
     * unchanged.
     *
     * <p>
     *  设置用于表示"不是数字"的字符串。几乎总是保持不变。
     * 
     * 
     * @param NaN the string representing "not a number"
     */
    public void setNaN(String NaN) {
        this.NaN = NaN;
    }

    /**
     * Gets the character used to represent minus sign. If no explicit
     * negative format is specified, one is formed by prefixing
     * minusSign to the positive format.
     *
     * <p>
     * 获取用于表示减号的字符。如果没有指定显式负格式,则通过将minusSign前缀加到正格式来形成一个。
     * 
     * 
     * @return the character representing minus sign
     */
    public char getMinusSign() {
        return minusSign;
    }

    /**
     * Sets the character used to represent minus sign. If no explicit
     * negative format is specified, one is formed by prefixing
     * minusSign to the positive format.
     *
     * <p>
     *  设置用于表示减号的字符。如果没有指定显式负格式,则通过将minusSign前缀加到正格式来形成一个。
     * 
     * 
     * @param minusSign the character representing minus sign
     */
    public void setMinusSign(char minusSign) {
        this.minusSign = minusSign;
    }

    /**
     * Returns the currency symbol for the currency of these
     * DecimalFormatSymbols in their locale.
     *
     * <p>
     *  返回这些DecimalFormatSymbol在其语言环境中的货币的货币符号。
     * 
     * 
     * @return the currency symbol
     * @since 1.2
     */
    public String getCurrencySymbol()
    {
        return currencySymbol;
    }

    /**
     * Sets the currency symbol for the currency of these
     * DecimalFormatSymbols in their locale.
     *
     * <p>
     *  在其语言环境中设置这些DecimalFormatSymbol的货币的货币符号。
     * 
     * 
     * @param currency the currency symbol
     * @since 1.2
     */
    public void setCurrencySymbol(String currency)
    {
        currencySymbol = currency;
    }

    /**
     * Returns the ISO 4217 currency code of the currency of these
     * DecimalFormatSymbols.
     *
     * <p>
     *  返回这些DecimalFormatSymbol的货币的ISO 4217货币代码。
     * 
     * 
     * @return the currency code
     * @since 1.2
     */
    public String getInternationalCurrencySymbol()
    {
        return intlCurrencySymbol;
    }

    /**
     * Sets the ISO 4217 currency code of the currency of these
     * DecimalFormatSymbols.
     * If the currency code is valid (as defined by
     * {@link java.util.Currency#getInstance(java.lang.String) Currency.getInstance}),
     * this also sets the currency attribute to the corresponding Currency
     * instance and the currency symbol attribute to the currency's symbol
     * in the DecimalFormatSymbols' locale. If the currency code is not valid,
     * then the currency attribute is set to null and the currency symbol
     * attribute is not modified.
     *
     * <p>
     *  设置这些DecimalFormatSymbol的货币的ISO 4217货币代码。
     * 如果货币代码有效(由{@link java.util.Currency#getInstance(java.lang.String)Currency.getInstance}定义),这还会将currenc
     * y属性设置为相应的Currency实例,并将货币符号属性设置为在DecimalFormatSymbols的语言环境中的货币符号。
     *  设置这些DecimalFormatSymbol的货币的ISO 4217货币代码。如果货币代码无效,则货币属性设置为null,并且不修改货币符号属性。
     * 
     * 
     * @param currencyCode the currency code
     * @see #setCurrency
     * @see #setCurrencySymbol
     * @since 1.2
     */
    public void setInternationalCurrencySymbol(String currencyCode)
    {
        intlCurrencySymbol = currencyCode;
        currency = null;
        if (currencyCode != null) {
            try {
                currency = Currency.getInstance(currencyCode);
                currencySymbol = currency.getSymbol();
            } catch (IllegalArgumentException e) {
            }
        }
    }

    /**
     * Gets the currency of these DecimalFormatSymbols. May be null if the
     * currency symbol attribute was previously set to a value that's not
     * a valid ISO 4217 currency code.
     *
     * <p>
     *  获取这些DecimalFormatSymbol的货币。如果货币符号属性先前设置为不是有效的ISO 4217货币代码的值,则可以为null。
     * 
     * 
     * @return the currency used, or null
     * @since 1.4
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Sets the currency of these DecimalFormatSymbols.
     * This also sets the currency symbol attribute to the currency's symbol
     * in the DecimalFormatSymbols' locale, and the international currency
     * symbol attribute to the currency's ISO 4217 currency code.
     *
     * <p>
     *  设置这些DecimalFormatSymbol的货币。这也将货币符号属性设置为DecimalFormatSymbols的区域设置中的货币符号,国际货币符号属性设置为货币的ISO 4217货币代码。
     * 
     * 
     * @param currency the new currency to be used
     * @exception NullPointerException if <code>currency</code> is null
     * @since 1.4
     * @see #setCurrencySymbol
     * @see #setInternationalCurrencySymbol
     */
    public void setCurrency(Currency currency) {
        if (currency == null) {
            throw new NullPointerException();
        }
        this.currency = currency;
        intlCurrencySymbol = currency.getCurrencyCode();
        currencySymbol = currency.getSymbol(locale);
    }


    /**
     * Returns the monetary decimal separator.
     *
     * <p>
     * 返回货币小数分隔符。
     * 
     * 
     * @return the monetary decimal separator
     * @since 1.2
     */
    public char getMonetaryDecimalSeparator()
    {
        return monetarySeparator;
    }

    /**
     * Sets the monetary decimal separator.
     *
     * <p>
     *  设置货币小数分隔符。
     * 
     * 
     * @param sep the monetary decimal separator
     * @since 1.2
     */
    public void setMonetaryDecimalSeparator(char sep)
    {
        monetarySeparator = sep;
    }

    //------------------------------------------------------------
    // BEGIN   Package Private methods ... to be made public later
    //------------------------------------------------------------

    /**
     * Returns the character used to separate the mantissa from the exponent.
     * <p>
     *  返回用于将尾数与指数分隔的字符。
     * 
     */
    char getExponentialSymbol()
    {
        return exponential;
    }
  /**
   * Returns the string used to separate the mantissa from the exponent.
   * Examples: "x10^" for 1.23x10^4, "E" for 1.23E4.
   *
   * <p>
   *  返回用于将尾数与指数分隔开的字符串。示例："x10 ^"表示1.23x10 ^ 4,"E"表示1.23E4。
   * 
   * 
   * @return the exponent separator string
   * @see #setExponentSeparator(java.lang.String)
   * @since 1.6
   */
    public String getExponentSeparator()
    {
        return exponentialSeparator;
    }

    /**
     * Sets the character used to separate the mantissa from the exponent.
     * <p>
     *  设置用于将尾数与指数分开的字符。
     * 
     */
    void setExponentialSymbol(char exp)
    {
        exponential = exp;
    }

  /**
   * Sets the string used to separate the mantissa from the exponent.
   * Examples: "x10^" for 1.23x10^4, "E" for 1.23E4.
   *
   * <p>
   *  设置用于将尾数与指数分隔开的字符串。示例："x10 ^"表示1.23x10 ^ 4,"E"表示1.23E4。
   * 
   * 
   * @param exp the exponent separator string
   * @exception NullPointerException if <code>exp</code> is null
   * @see #getExponentSeparator()
   * @since 1.6
   */
    public void setExponentSeparator(String exp)
    {
        if (exp == null) {
            throw new NullPointerException();
        }
        exponentialSeparator = exp;
     }


    //------------------------------------------------------------
    // END     Package Private methods ... to be made public later
    //------------------------------------------------------------

    /**
     * Standard override.
     * <p>
     *  标准覆盖。
     * 
     */
    @Override
    public Object clone() {
        try {
            return (DecimalFormatSymbols)super.clone();
            // other fields are bit-copied
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Override equals.
     * <p>
     *  覆盖等于。
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        DecimalFormatSymbols other = (DecimalFormatSymbols) obj;
        return (zeroDigit == other.zeroDigit &&
        groupingSeparator == other.groupingSeparator &&
        decimalSeparator == other.decimalSeparator &&
        percent == other.percent &&
        perMill == other.perMill &&
        digit == other.digit &&
        minusSign == other.minusSign &&
        patternSeparator == other.patternSeparator &&
        infinity.equals(other.infinity) &&
        NaN.equals(other.NaN) &&
        currencySymbol.equals(other.currencySymbol) &&
        intlCurrencySymbol.equals(other.intlCurrencySymbol) &&
        currency == other.currency &&
        monetarySeparator == other.monetarySeparator &&
        exponentialSeparator.equals(other.exponentialSeparator) &&
        locale.equals(other.locale));
    }

    /**
     * Override hashCode.
     * <p>
     *  覆盖hashCode。
     * 
     */
    @Override
    public int hashCode() {
            int result = zeroDigit;
            result = result * 37 + groupingSeparator;
            result = result * 37 + decimalSeparator;
            return result;
    }

    /**
     * Initializes the symbols from the FormatData resource bundle.
     * <p>
     *  初始化FormatData资源束中的符号。
     * 
     */
    private void initialize( Locale locale ) {
        this.locale = locale;

        // get resource bundle data
        LocaleProviderAdapter adapter = LocaleProviderAdapter.getAdapter(DecimalFormatSymbolsProvider.class, locale);
        // Avoid potential recursions
        if (!(adapter instanceof ResourceBundleBasedAdapter)) {
            adapter = LocaleProviderAdapter.getResourceBundleBased();
        }
        Object[] data = adapter.getLocaleResources(locale).getDecimalFormatSymbolsData();
        String[] numberElements = (String[]) data[0];

        decimalSeparator = numberElements[0].charAt(0);
        groupingSeparator = numberElements[1].charAt(0);
        patternSeparator = numberElements[2].charAt(0);
        percent = numberElements[3].charAt(0);
        zeroDigit = numberElements[4].charAt(0); //different for Arabic,etc.
        digit = numberElements[5].charAt(0);
        minusSign = numberElements[6].charAt(0);
        exponential = numberElements[7].charAt(0);
        exponentialSeparator = numberElements[7]; //string representation new since 1.6
        perMill = numberElements[8].charAt(0);
        infinity  = numberElements[9];
        NaN = numberElements[10];

        // Try to obtain the currency used in the locale's country.
        // Check for empty country string separately because it's a valid
        // country ID for Locale (and used for the C locale), but not a valid
        // ISO 3166 country code, and exceptions are expensive.
        if (locale.getCountry().length() > 0) {
            try {
                currency = Currency.getInstance(locale);
            } catch (IllegalArgumentException e) {
                // use default values below for compatibility
            }
        }
        if (currency != null) {
            intlCurrencySymbol = currency.getCurrencyCode();
            if (data[1] != null && data[1] == intlCurrencySymbol) {
                currencySymbol = (String) data[2];
            } else {
                currencySymbol = currency.getSymbol(locale);
                data[1] = intlCurrencySymbol;
                data[2] = currencySymbol;
            }
        } else {
            // default values
            intlCurrencySymbol = "XXX";
            try {
                currency = Currency.getInstance(intlCurrencySymbol);
            } catch (IllegalArgumentException e) {
            }
            currencySymbol = "\u00A4";
        }
        // Currently the monetary decimal separator is the same as the
        // standard decimal separator for all locales that we support.
        // If that changes, add a new entry to NumberElements.
        monetarySeparator = decimalSeparator;
    }

    /**
     * Reads the default serializable fields, provides default values for objects
     * in older serial versions, and initializes non-serializable fields.
     * If <code>serialVersionOnStream</code>
     * is less than 1, initializes <code>monetarySeparator</code> to be
     * the same as <code>decimalSeparator</code> and <code>exponential</code>
     * to be 'E'.
     * If <code>serialVersionOnStream</code> is less than 2,
     * initializes <code>locale</code>to the root locale, and initializes
     * If <code>serialVersionOnStream</code> is less than 3, it initializes
     * <code>exponentialSeparator</code> using <code>exponential</code>.
     * Sets <code>serialVersionOnStream</code> back to the maximum allowed value so that
     * default serialization will work properly if this object is streamed out again.
     * Initializes the currency from the intlCurrencySymbol field.
     *
     * <p>
     *  读取默认的可序列化字段,为旧版本的串行版本中的对象提供默认值,并初始化不可序列化的字段。
     * 如果<code> serialVersionOnStream </code>小于1,则将<code> monetarySeparator </code>初始化为与<code> decimalSepara
     * tor </code>和<code> exponential </code> 。
     *  读取默认的可序列化字段,为旧版本的串行版本中的对象提供默认值,并初始化不可序列化的字段。
     * 如果<code> serialVersionOnStream </code>小于2,则将<code> locale </code>初始化为根区域设置,并初始化如果<code> serialVersion
     * OnStream </code>小于3,则初始化<code> exponentialSeparator </code>使用<code>指数</code>。
     *  读取默认的可序列化字段,为旧版本的串行版本中的对象提供默认值,并初始化不可序列化的字段。
     * 将<code> serialVersionOnStream </code>设置为允许的最大值,以便如果此对象再次流出,则默认序列化将正常工作。从intlCurrencySymbol字段初始化货币。
     * 
     * 
     * @since JDK 1.1.6
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        if (serialVersionOnStream < 1) {
            // Didn't have monetarySeparator or exponential field;
            // use defaults.
            monetarySeparator = decimalSeparator;
            exponential       = 'E';
        }
        if (serialVersionOnStream < 2) {
            // didn't have locale; use root locale
            locale = Locale.ROOT;
        }
        if (serialVersionOnStream < 3) {
            // didn't have exponentialSeparator. Create one using exponential
            exponentialSeparator = Character.toString(exponential);
        }
        serialVersionOnStream = currentSerialVersion;

        if (intlCurrencySymbol != null) {
            try {
                 currency = Currency.getInstance(intlCurrencySymbol);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    /**
     * Character used for zero.
     *
     * <p>
     *  用于零的字符。
     * 
     * 
     * @serial
     * @see #getZeroDigit
     */
    private  char    zeroDigit;

    /**
     * Character used for thousands separator.
     *
     * <p>
     *  字符用于千位分隔符。
     * 
     * 
     * @serial
     * @see #getGroupingSeparator
     */
    private  char    groupingSeparator;

    /**
     * Character used for decimal sign.
     *
     * <p>
     *  用于十进制符号的字符。
     * 
     * 
     * @serial
     * @see #getDecimalSeparator
     */
    private  char    decimalSeparator;

    /**
     * Character used for per mille sign.
     *
     * <p>
     * 用于每英里符号的字符。
     * 
     * 
     * @serial
     * @see #getPerMill
     */
    private  char    perMill;

    /**
     * Character used for percent sign.
     * <p>
     *  用于百分号的字符。
     * 
     * 
     * @serial
     * @see #getPercent
     */
    private  char    percent;

    /**
     * Character used for a digit in a pattern.
     *
     * <p>
     *  用于模式中数字的字符。
     * 
     * 
     * @serial
     * @see #getDigit
     */
    private  char    digit;

    /**
     * Character used to separate positive and negative subpatterns
     * in a pattern.
     *
     * <p>
     *  用于分隔图案中的正和负子图案的字符。
     * 
     * 
     * @serial
     * @see #getPatternSeparator
     */
    private  char    patternSeparator;

    /**
     * String used to represent infinity.
     * <p>
     *  用于表示无穷大的字符串。
     * 
     * 
     * @serial
     * @see #getInfinity
     */
    private  String  infinity;

    /**
     * String used to represent "not a number".
     * <p>
     *  用于表示"不是数字"的字符串。
     * 
     * 
     * @serial
     * @see #getNaN
     */
    private  String  NaN;

    /**
     * Character used to represent minus sign.
     * <p>
     *  用于表示减号的字符。
     * 
     * 
     * @serial
     * @see #getMinusSign
     */
    private  char    minusSign;

    /**
     * String denoting the local currency, e.g. "$".
     * <p>
     *  表示本地货币的字符串,例如"$"。
     * 
     * 
     * @serial
     * @see #getCurrencySymbol
     */
    private  String  currencySymbol;

    /**
     * ISO 4217 currency code denoting the local currency, e.g. "USD".
     * <p>
     *  ISO 4217货币代码表示当地货币,例如"美元"。
     * 
     * 
     * @serial
     * @see #getInternationalCurrencySymbol
     */
    private  String  intlCurrencySymbol;

    /**
     * The decimal separator used when formatting currency values.
     * <p>
     *  格式化货币值时使用的小数分隔符。
     * 
     * 
     * @serial
     * @since JDK 1.1.6
     * @see #getMonetaryDecimalSeparator
     */
    private  char    monetarySeparator; // Field new in JDK 1.1.6

    /**
     * The character used to distinguish the exponent in a number formatted
     * in exponential notation, e.g. 'E' for a number such as "1.23E45".
     * <p>
     * Note that the public API provides no way to set this field,
     * even though it is supported by the implementation and the stream format.
     * The intent is that this will be added to the API in the future.
     *
     * <p>
     *  用于区分以指数符号格式化的数字中的指数的字符,例如"E"表示数字,例如"1.23E45"。
     * <p>
     *  请注意,公共API不提供设置此字段的方法,即使它由实现和流格式支持。其目的是,这将在未来添加到API。
     * 
     * 
     * @serial
     * @since JDK 1.1.6
     */
    private  char    exponential;       // Field new in JDK 1.1.6

  /**
   * The string used to separate the mantissa from the exponent.
   * Examples: "x10^" for 1.23x10^4, "E" for 1.23E4.
   * <p>
   * If both <code>exponential</code> and <code>exponentialSeparator</code>
   * exist, this <code>exponentialSeparator</code> has the precedence.
   *
   * <p>
   *  用于将尾数与指数分隔开的字符串。示例："x10 ^"表示1.23x10 ^ 4,"E"表示1.23E4。
   * <p>
   *  如果<code>指数</code>和<code> exponentialSeparator </code>都存在,则<code> exponentialSeparator </code>
   * 
   * 
   * @serial
   * @since 1.6
   */
    private  String    exponentialSeparator;       // Field new in JDK 1.6

    /**
     * The locale of these currency format symbols.
     *
     * <p>
     *  这些货币格式符号的区域设置。
     * 
     * 
     * @serial
     * @since 1.4
     */
    private Locale locale;

    // currency; only the ISO code is serialized.
    private transient Currency currency;

    // Proclaim JDK 1.1 FCS compatibility
    static final long serialVersionUID = 5772796243397350300L;

    // The internal serial version which says which version was written
    // - 0 (default) for version up to JDK 1.1.5
    // - 1 for version from JDK 1.1.6, which includes two new fields:
    //     monetarySeparator and exponential.
    // - 2 for version from J2SE 1.4, which includes locale field.
    // - 3 for version from J2SE 1.6, which includes exponentialSeparator field.
    private static final int currentSerialVersion = 3;

    /**
     * Describes the version of <code>DecimalFormatSymbols</code> present on the stream.
     * Possible values are:
     * <ul>
     * <li><b>0</b> (or uninitialized): versions prior to JDK 1.1.6.
     *
     * <li><b>1</b>: Versions written by JDK 1.1.6 or later, which include
     *      two new fields: <code>monetarySeparator</code> and <code>exponential</code>.
     * <li><b>2</b>: Versions written by J2SE 1.4 or later, which include a
     *      new <code>locale</code> field.
     * <li><b>3</b>: Versions written by J2SE 1.6 or later, which include a
     *      new <code>exponentialSeparator</code> field.
     * </ul>
     * When streaming out a <code>DecimalFormatSymbols</code>, the most recent format
     * (corresponding to the highest allowable <code>serialVersionOnStream</code>)
     * is always written.
     *
     * <p>
     *  描述流上存在的<code> DecimalFormatSymbols </code>的版本。可能的值为：
     * <ul>
     *  <li> <b> 0 </b>(或未初始化)：JDK 1.1.6之前的版本。
     * 
     * <li> <b> 1 </b>：由JDK 1.1.6或更高版本编写的版本,其中包含两个新字段：<code> monetarySeparator </code>和<code> exponential </code>
     * 。
     * 
     * @serial
     * @since JDK 1.1.6
     */
    private int serialVersionOnStream = currentSerialVersion;
}
