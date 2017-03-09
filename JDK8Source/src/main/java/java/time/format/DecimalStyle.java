/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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
 *
 *
 *
 *
 *
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 *  版权所有(c)2008-2012,Stephen Colebourne和Michael Nascimento Santos
 * 
 *  版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  *源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  *二进制形式的再分发必须在随发行提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  *未经特定事先书面许可,JSR-310的名称及其贡献者的名称不得用于支持或推广衍生自此软件的产品。
 * 
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,版权所有者或贡献者对任何直接,间接,偶发,特殊,惩戒性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据或利润损失,或业务中断),无论是由于任何责任推定,无论是在合同,严格责任,或
 * 侵权(包括疏忽或其他)任何方式使用本软件,即使已被告知此类损害的可能性。
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 
 */
package java.time.format;

import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Localized decimal style used in date and time formatting.
 * <p>
 * A significant part of dealing with dates and times is the localization.
 * This class acts as a central point for accessing the information.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  用于日期和时间格式的本地化十进制样式。
 * <p>
 *  处理日期和时间的一个重要部分是本地化。这个类作为访问信息的中心点。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class DecimalStyle {

    /**
     * The standard set of non-localized decimal style symbols.
     * <p>
     * This uses standard ASCII characters for zero, positive, negative and a dot for the decimal point.
     * <p>
     *  非本地化的十进制样式符号的标准集。
     * <p>
     *  这使用标准ASCII字符为零,正,负和小点的点。
     * 
     */
    public static final DecimalStyle STANDARD = new DecimalStyle('0', '+', '-', '.');
    /**
     * The cache of DecimalStyle instances.
     * <p>
     *  DecimalStyle实例的缓存。
     * 
     */
    private static final ConcurrentMap<Locale, DecimalStyle> CACHE = new ConcurrentHashMap<>(16, 0.75f, 2);

    /**
     * The zero digit.
     * <p>
     *  零数字。
     * 
     */
    private final char zeroDigit;
    /**
     * The positive sign.
     * <p>
     *  积极的迹象。
     * 
     */
    private final char positiveSign;
    /**
     * The negative sign.
     * <p>
     *  负号。
     * 
     */
    private final char negativeSign;
    /**
     * The decimal separator.
     * <p>
     *  小数分隔符。
     * 
     */
    private final char decimalSeparator;

    //-----------------------------------------------------------------------
    /**
     * Lists all the locales that are supported.
     * <p>
     * The locale 'en_US' will always be present.
     *
     * <p>
     *  列出所有支持的语言环境。
     * <p>
     *  语言环境"en_US"将始终存在。
     * 
     * 
     * @return a Set of Locales for which localization is supported
     */
    public static Set<Locale> getAvailableLocales() {
        Locale[] l = DecimalFormatSymbols.getAvailableLocales();
        Set<Locale> locales = new HashSet<>(l.length);
        Collections.addAll(locales, l);
        return locales;
    }

    /**
     * Obtains the DecimalStyle for the default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>
     * This method provides access to locale sensitive decimal style symbols.
     * <p>
     * This is equivalent to calling
     * {@link #of(Locale)
     *     of(Locale.getDefault(Locale.Category.FORMAT))}.
     *
     * <p>
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置的DecimalStyle。
     * <p>
     * 此方法提供对区域设置敏感的十进制样式符号的访问。
     * <p>
     *  这相当于调用(Locale.getDefault(Locale.Category.FORMAT))的{@link #of(Locale))}。
     * 
     * 
     * @see java.util.Locale.Category#FORMAT
     * @return the info, not null
     */
    public static DecimalStyle ofDefaultLocale() {
        return of(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Obtains the DecimalStyle for the specified locale.
     * <p>
     * This method provides access to locale sensitive decimal style symbols.
     *
     * <p>
     *  获取指定区域设置的DecimalStyle。
     * <p>
     *  此方法提供对区域设置敏感的十进制样式符号的访问。
     * 
     * 
     * @param locale  the locale, not null
     * @return the info, not null
     */
    public static DecimalStyle of(Locale locale) {
        Objects.requireNonNull(locale, "locale");
        DecimalStyle info = CACHE.get(locale);
        if (info == null) {
            info = create(locale);
            CACHE.putIfAbsent(locale, info);
            info = CACHE.get(locale);
        }
        return info;
    }

    private static DecimalStyle create(Locale locale) {
        DecimalFormatSymbols oldSymbols = DecimalFormatSymbols.getInstance(locale);
        char zeroDigit = oldSymbols.getZeroDigit();
        char positiveSign = '+';
        char negativeSign = oldSymbols.getMinusSign();
        char decimalSeparator = oldSymbols.getDecimalSeparator();
        if (zeroDigit == '0' && negativeSign == '-' && decimalSeparator == '.') {
            return STANDARD;
        }
        return new DecimalStyle(zeroDigit, positiveSign, negativeSign, decimalSeparator);
    }

    //-----------------------------------------------------------------------
    /**
     * Restricted constructor.
     *
     * <p>
     *  受限制的构造函数。
     * 
     * 
     * @param zeroChar  the character to use for the digit of zero
     * @param positiveSignChar  the character to use for the positive sign
     * @param negativeSignChar  the character to use for the negative sign
     * @param decimalPointChar  the character to use for the decimal point
     */
    private DecimalStyle(char zeroChar, char positiveSignChar, char negativeSignChar, char decimalPointChar) {
        this.zeroDigit = zeroChar;
        this.positiveSign = positiveSignChar;
        this.negativeSign = negativeSignChar;
        this.decimalSeparator = decimalPointChar;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character that represents zero.
     * <p>
     * The character used to represent digits may vary by culture.
     * This method specifies the zero character to use, which implies the characters for one to nine.
     *
     * <p>
     *  获取表示零的字符。
     * <p>
     *  用于表示数字的字符可能因文化而异。此方法指定要使用的零字符,这意味着一到九个字符。
     * 
     * 
     * @return the character for zero
     */
    public char getZeroDigit() {
        return zeroDigit;
    }

    /**
     * Returns a copy of the info with a new character that represents zero.
     * <p>
     * The character used to represent digits may vary by culture.
     * This method specifies the zero character to use, which implies the characters for one to nine.
     *
     * <p>
     *  返回包含表示零的新字符的信息副本。
     * <p>
     *  用于表示数字的字符可能因文化而异。此方法指定要使用的零字符,这意味着一到九个字符。
     * 
     * 
     * @param zeroDigit  the character for zero
     * @return  a copy with a new character that represents zero, not null

     */
    public DecimalStyle withZeroDigit(char zeroDigit) {
        if (zeroDigit == this.zeroDigit) {
            return this;
        }
        return new DecimalStyle(zeroDigit, positiveSign, negativeSign, decimalSeparator);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character that represents the positive sign.
     * <p>
     * The character used to represent a positive number may vary by culture.
     * This method specifies the character to use.
     *
     * <p>
     *  获取表示正号的字符。
     * <p>
     *  用于表示正数的字符可以根据文化而变化。此方法指定要使用的字符。
     * 
     * 
     * @return the character for the positive sign
     */
    public char getPositiveSign() {
        return positiveSign;
    }

    /**
     * Returns a copy of the info with a new character that represents the positive sign.
     * <p>
     * The character used to represent a positive number may vary by culture.
     * This method specifies the character to use.
     *
     * <p>
     *  返回包含表示正号的新字符的信息副本。
     * <p>
     *  用于表示正数的字符可以根据文化而变化。此方法指定要使用的字符。
     * 
     * 
     * @param positiveSign  the character for the positive sign
     * @return  a copy with a new character that represents the positive sign, not null
     */
    public DecimalStyle withPositiveSign(char positiveSign) {
        if (positiveSign == this.positiveSign) {
            return this;
        }
        return new DecimalStyle(zeroDigit, positiveSign, negativeSign, decimalSeparator);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character that represents the negative sign.
     * <p>
     * The character used to represent a negative number may vary by culture.
     * This method specifies the character to use.
     *
     * <p>
     *  获取表示负号的字符。
     * <p>
     *  用于表示负数的字符可以根据文化而变化。此方法指定要使用的字符。
     * 
     * 
     * @return the character for the negative sign
     */
    public char getNegativeSign() {
        return negativeSign;
    }

    /**
     * Returns a copy of the info with a new character that represents the negative sign.
     * <p>
     * The character used to represent a negative number may vary by culture.
     * This method specifies the character to use.
     *
     * <p>
     *  返回具有表示负号的新字符的信息副本。
     * <p>
     *  用于表示负数的字符可以根据文化而变化。此方法指定要使用的字符。
     * 
     * 
     * @param negativeSign  the character for the negative sign
     * @return  a copy with a new character that represents the negative sign, not null
     */
    public DecimalStyle withNegativeSign(char negativeSign) {
        if (negativeSign == this.negativeSign) {
            return this;
        }
        return new DecimalStyle(zeroDigit, positiveSign, negativeSign, decimalSeparator);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the character that represents the decimal point.
     * <p>
     * The character used to represent a decimal point may vary by culture.
     * This method specifies the character to use.
     *
     * <p>
     * 获取表示小数点的字符。
     * <p>
     *  用于表示小数点的字符可能因文化而异。此方法指定要使用的字符。
     * 
     * 
     * @return the character for the decimal point
     */
    public char getDecimalSeparator() {
        return decimalSeparator;
    }

    /**
     * Returns a copy of the info with a new character that represents the decimal point.
     * <p>
     * The character used to represent a decimal point may vary by culture.
     * This method specifies the character to use.
     *
     * <p>
     *  返回一个包含表示小数点的新字符的信息副本。
     * <p>
     *  用于表示小数点的字符可能因文化而异。此方法指定要使用的字符。
     * 
     * 
     * @param decimalSeparator  the character for the decimal point
     * @return  a copy with a new character that represents the decimal point, not null
     */
    public DecimalStyle withDecimalSeparator(char decimalSeparator) {
        if (decimalSeparator == this.decimalSeparator) {
            return this;
        }
        return new DecimalStyle(zeroDigit, positiveSign, negativeSign, decimalSeparator);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks whether the character is a digit, based on the currently set zero character.
     *
     * <p>
     *  根据当前设置的零字符检查字符是否为数字。
     * 
     * 
     * @param ch  the character to check
     * @return the value, 0 to 9, of the character, or -1 if not a digit
     */
    int convertToDigit(char ch) {
        int val = ch - zeroDigit;
        return (val >= 0 && val <= 9) ? val : -1;
    }

    /**
     * Converts the input numeric text to the internationalized form using the zero character.
     *
     * <p>
     *  使用零字符将输入数字文本转换为国际化表单。
     * 
     * 
     * @param numericText  the text, consisting of digits 0 to 9, to convert, not null
     * @return the internationalized text, not null
     */
    String convertNumberToI18N(String numericText) {
        if (zeroDigit == '0') {
            return numericText;
        }
        int diff = zeroDigit - '0';
        char[] array = numericText.toCharArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = (char) (array[i] + diff);
        }
        return new String(array);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this DecimalStyle is equal another DecimalStyle.
     *
     * <p>
     *  检查此DecimalStyle是否等于另一个DecimalStyle。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other date
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DecimalStyle) {
            DecimalStyle other = (DecimalStyle) obj;
            return (zeroDigit == other.zeroDigit && positiveSign == other.positiveSign &&
                    negativeSign == other.negativeSign && decimalSeparator == other.decimalSeparator);
        }
        return false;
    }

    /**
     * A hash code for this DecimalStyle.
     *
     * <p>
     *  此DecimalStyle的哈希代码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return zeroDigit + positiveSign + negativeSign + decimalSeparator;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a string describing this DecimalStyle.
     *
     * <p>
     *  返回描述此DecimalStyle的字符串。
     * 
     * @return a string description, not null
     */
    @Override
    public String toString() {
        return "DecimalStyle[" + zeroDigit + positiveSign + negativeSign + decimalSeparator + "]";
    }

}
