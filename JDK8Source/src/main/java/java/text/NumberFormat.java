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

import java.io.InvalidObjectException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.spi.NumberFormatProvider;
import java.util.Currency;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.spi.LocaleServiceProvider;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleServiceProviderPool;

/**
 * <code>NumberFormat</code> is the abstract base class for all number
 * formats. This class provides the interface for formatting and parsing
 * numbers. <code>NumberFormat</code> also provides methods for determining
 * which locales have number formats, and what their names are.
 *
 * <p>
 * <code>NumberFormat</code> helps you to format and parse numbers for any locale.
 * Your code can be completely independent of the locale conventions for
 * decimal points, thousands-separators, or even the particular decimal
 * digits used, or whether the number format is even decimal.
 *
 * <p>
 * To format a number for the current Locale, use one of the factory
 * class methods:
 * <blockquote>
 * <pre>{@code
 * myString = NumberFormat.getInstance().format(myNumber);
 * }</pre>
 * </blockquote>
 * If you are formatting multiple numbers, it is
 * more efficient to get the format and use it multiple times so that
 * the system doesn't have to fetch the information about the local
 * language and country conventions multiple times.
 * <blockquote>
 * <pre>{@code
 * NumberFormat nf = NumberFormat.getInstance();
 * for (int i = 0; i < myNumber.length; ++i) {
 *     output.println(nf.format(myNumber[i]) + "; ");
 * }
 * }</pre>
 * </blockquote>
 * To format a number for a different Locale, specify it in the
 * call to <code>getInstance</code>.
 * <blockquote>
 * <pre>{@code
 * NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
 * }</pre>
 * </blockquote>
 * You can also use a <code>NumberFormat</code> to parse numbers:
 * <blockquote>
 * <pre>{@code
 * myNumber = nf.parse(myString);
 * }</pre>
 * </blockquote>
 * Use <code>getInstance</code> or <code>getNumberInstance</code> to get the
 * normal number format. Use <code>getIntegerInstance</code> to get an
 * integer number format. Use <code>getCurrencyInstance</code> to get the
 * currency number format. And use <code>getPercentInstance</code> to get a
 * format for displaying percentages. With this format, a fraction like
 * 0.53 is displayed as 53%.
 *
 * <p>
 * You can also control the display of numbers with such methods as
 * <code>setMinimumFractionDigits</code>.
 * If you want even more control over the format or parsing,
 * or want to give your users more control,
 * you can try casting the <code>NumberFormat</code> you get from the factory methods
 * to a <code>DecimalFormat</code>. This will work for the vast majority
 * of locales; just remember to put it in a <code>try</code> block in case you
 * encounter an unusual one.
 *
 * <p>
 * NumberFormat and DecimalFormat are designed such that some controls
 * work for formatting and others work for parsing.  The following is
 * the detailed description for each these control methods,
 * <p>
 * setParseIntegerOnly : only affects parsing, e.g.
 * if true,  "3456.78" &rarr; 3456 (and leaves the parse position just after index 6)
 * if false, "3456.78" &rarr; 3456.78 (and leaves the parse position just after index 8)
 * This is independent of formatting.  If you want to not show a decimal point
 * where there might be no digits after the decimal point, use
 * setDecimalSeparatorAlwaysShown.
 * <p>
 * setDecimalSeparatorAlwaysShown : only affects formatting, and only where
 * there might be no digits after the decimal point, such as with a pattern
 * like "#,##0.##", e.g.,
 * if true,  3456.00 &rarr; "3,456."
 * if false, 3456.00 &rarr; "3456"
 * This is independent of parsing.  If you want parsing to stop at the decimal
 * point, use setParseIntegerOnly.
 *
 * <p>
 * You can also use forms of the <code>parse</code> and <code>format</code>
 * methods with <code>ParsePosition</code> and <code>FieldPosition</code> to
 * allow you to:
 * <ul>
 * <li> progressively parse through pieces of a string
 * <li> align the decimal point and other areas
 * </ul>
 * For example, you can align numbers in two ways:
 * <ol>
 * <li> If you are using a monospaced font with spacing for alignment,
 *      you can pass the <code>FieldPosition</code> in your format call, with
 *      <code>field</code> = <code>INTEGER_FIELD</code>. On output,
 *      <code>getEndIndex</code> will be set to the offset between the
 *      last character of the integer and the decimal. Add
 *      (desiredSpaceCount - getEndIndex) spaces at the front of the string.
 *
 * <li> If you are using proportional fonts,
 *      instead of padding with spaces, measure the width
 *      of the string in pixels from the start to <code>getEndIndex</code>.
 *      Then move the pen by
 *      (desiredPixelWidth - widthToAlignmentPoint) before drawing the text.
 *      It also works where there is no decimal, but possibly additional
 *      characters at the end, e.g., with parentheses in negative
 *      numbers: "(12)" for -12.
 * </ol>
 *
 * <h3><a name="synchronization">Synchronization</a></h3>
 *
 * <p>
 * Number formats are generally not synchronized.
 * It is recommended to create separate format instances for each thread.
 * If multiple threads access a format concurrently, it must be synchronized
 * externally.
 *
 * <p>
 *  <code> NumberFormat </code>是所有数字格式的抽象基类。这个类提供了格式化和解析数字的接口。
 *  <code> NumberFormat </code>还提供了用于确定哪些语言区域具有数字格式及其名称的方法。
 * 
 * <p>
 *  <code> NumberFormat </code>可帮助您为任何语言环境格式化和解析数字。
 * 您的代码可以完全独立于小数点,千位分隔符或甚至使用的特定小数位数的区域设置约定,或者数字格式是否为十进制。
 * 
 * <p>
 *  要格式化当前区域设置的数字,请使用工厂类方法之一：
 * <blockquote>
 *  <pre> {@ code myString = NumberFormat.getInstance()。format(myNumber); } </pre>
 * </blockquote>
 * 如果要格式化多个数字,则更有效的方法是获取格式并多次使用,以便系统不必多次获取有关本地语言和国家/地区惯例的信息。
 * <blockquote>
 *  <pre> {@ code NumberFormat nf = NumberFormat.getInstance(); for(int i = 0; i <myNumber.length; ++ i){output.println(nf.format(myNumber [i])+";"); }} </pre>
 * 。
 * </blockquote>
 *  要为不同的区域设置格式化数字,请在调用<code> getInstance </code>中指定该数字。
 * <blockquote>
 *  <pre> {@ code NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH); } </pre>
 * </blockquote>
 *  您还可以使用<code> NumberFormat </code>来解析数字：
 * <blockquote>
 *  <pre> {@ code myNumber = nf.parse(myString); } </pre>
 * </blockquote>
 *  使用<code> getInstance </code>或<code> getNumberInstance </code>可获得正常的数字格式。
 * 使用<code> getIntegerInstance </code>获取整数格式。使用<code> getCurrencyInstance </code>获取货币号格式。
 * 并使用<code> getPercentInstance </code>获取显示百分比的格式。使用此格式,像0.53的分数显示为53％。
 * 
 * <p>
 * 您还可以使用<code> setMinimumFractionDigits </code>等方法来控制数字的显示。
 * 如果你想要更多的控制格式或解析,或者想给你的用户更多的控制,你可以尝试转换<code> NumberFormat </code>你从工厂方法到一个<code> DecimalFormat </code>
 *  。
 * 您还可以使用<code> setMinimumFractionDigits </code>等方法来控制数字的显示。
 * 这将适用于绝大多数地区;只是记住把它放在一个<code> try </code>块,以防你遇到一个不寻常的。
 * 
 * <p>
 *  NumberFormat和DecimalFormat设计使得一些控件用于格式化,其他控件用于解析。下面是每个这些控制方法的详细描述,
 * <p>
 *  setParseIntegerOnly：只影响解析,例如如果为true,"3456.78"&rarr; 3456(并且离开索引6之后的解析位置)如果为假,"3456.78"&rarr; 3456.78
 * (并且离开索引8之后的解析位置)这与格式无关。
 * 如果希望不显示小数点,其中小数点后面可能没有数字,请使用setDecimalSeparatorAlwaysShown。
 * <p>
 *  setDecimalSeparatorAlwaysShown：只影响格式化,并且只有在小数点后面没有数字,例如使用类似"#,## 0。
 * ##"的模式,例如,如果为true,3456.00&rarr; "3,456"。如果为false,3456.00&rarr; "3456"这独立于解析。
 * 如果要解析以小数点停止,请使用setParseIntegerOnly。
 * 
 * <p>
 * 您还可以使用<code> parse </code>和<code> FieldPosition </code>的<code> parse </code>和<code> format </code>方法的
 * 形式允许您：。
 * <ul>
 *  <li>逐步解析字符串<li>对齐小数点和其他区域
 * </ul>
 *  例如,您可以通过两种方式对齐数字：
 * <ol>
 *  <li>如果您使用间距对齐的等宽字体,则可以通过格式调用中的<code> FieldPosition </code>,使用<code>字段</code> = <code> INTEGER_FIELD 
 * </code> 。
 * 在输出上,<code> getEndIndex </code>将被设置为整数的最后一个字符和小数之间的偏移量。
 * 在字符串的前面添加(desiredSpaceCount  -  getEndIndex)空格。
 * 
 *  <li>如果您使用比例字体,而不是用空格填充,请从头开始到<code> getEndIndex </code>,以像素为单位测量字符串的宽度。
 * 然后在绘制文本之前将笔移动(desiredPixelWidth  -  widthToAlignmentPoint)。
 * 它也适用于没有小数,但在末尾可能有额外的字符,例如,带负号的括号："(12)"为-12。
 * </ol>
 * 
 *  <h3> <a name="synchronization">同步</a> </h3>
 * 
 * <p>
 *  数字格式通常不同步。建议为每个线程创建单独的格式实例。如果多个线程并发访问格式,则必须在外部同步。
 * 
 * 
 * @see          DecimalFormat
 * @see          ChoiceFormat
 * @author       Mark Davis
 * @author       Helena Shih
 */
public abstract class NumberFormat extends Format  {

    /**
     * Field constant used to construct a FieldPosition object. Signifies that
     * the position of the integer part of a formatted number should be returned.
     * <p>
     *  用于构造FieldPosition对象的字段常量。表示应返回格式化数字的整数部分的位置。
     * 
     * 
     * @see java.text.FieldPosition
     */
    public static final int INTEGER_FIELD = 0;

    /**
     * Field constant used to construct a FieldPosition object. Signifies that
     * the position of the fraction part of a formatted number should be returned.
     * <p>
     * 用于构造FieldPosition对象的字段常量。表示应返回格式化数字的小数部分的位置。
     * 
     * 
     * @see java.text.FieldPosition
     */
    public static final int FRACTION_FIELD = 1;

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected NumberFormat() {
    }

    /**
     * Formats a number and appends the resulting text to the given string
     * buffer.
     * The number can be of any subclass of {@link java.lang.Number}.
     * <p>
     * This implementation extracts the number's value using
     * {@link java.lang.Number#longValue()} for all integral type values that
     * can be converted to <code>long</code> without loss of information,
     * including <code>BigInteger</code> values with a
     * {@link java.math.BigInteger#bitLength() bit length} of less than 64,
     * and {@link java.lang.Number#doubleValue()} for all other types. It
     * then calls
     * {@link #format(long,java.lang.StringBuffer,java.text.FieldPosition)}
     * or {@link #format(double,java.lang.StringBuffer,java.text.FieldPosition)}.
     * This may result in loss of magnitude information and precision for
     * <code>BigInteger</code> and <code>BigDecimal</code> values.
     * <p>
     *  格式化一个数字,并将结果文本附加到给定的字符串缓冲区。该数字可以是{@link java.lang.Number}的任何子类。
     * <p>
     *  此实现使用{@link java.lang.Number#longValue()}为所有可转换为<code> long </code>的整数类型值提取数字的值,而不会丢失信息,包括<code> Big
     * Integer < code>值小于64的{@link java.math.BigInteger#bitLength()位长度},以及所有其他类型的{@link java.lang.Number#doubleValue()}
     * 。
     * 然后调用{@link #format(long,java.lang.StringBuffer,java.text.FieldPosition)}或{@link #format(double,java.lang.StringBuffer,java.text.FieldPosition)}
     * 。
     * 这可能导致<code> BigInteger </code>和<code> BigDecimal </code>值的大小信息和精度的损失。
     * 
     * 
     * @param number     the number to format
     * @param toAppendTo the <code>StringBuffer</code> to which the formatted
     *                   text is to be appended
     * @param pos        On input: an alignment field, if desired.
     *                   On output: the offsets of the alignment field.
     * @return           the value passed in as <code>toAppendTo</code>
     * @exception        IllegalArgumentException if <code>number</code> is
     *                   null or not an instance of <code>Number</code>.
     * @exception        NullPointerException if <code>toAppendTo</code> or
     *                   <code>pos</code> is null
     * @exception        ArithmeticException if rounding is needed with rounding
     *                   mode being set to RoundingMode.UNNECESSARY
     * @see              java.text.FieldPosition
     */
    @Override
    public StringBuffer format(Object number,
                               StringBuffer toAppendTo,
                               FieldPosition pos) {
        if (number instanceof Long || number instanceof Integer ||
            number instanceof Short || number instanceof Byte ||
            number instanceof AtomicInteger || number instanceof AtomicLong ||
            (number instanceof BigInteger &&
             ((BigInteger)number).bitLength() < 64)) {
            return format(((Number)number).longValue(), toAppendTo, pos);
        } else if (number instanceof Number) {
            return format(((Number)number).doubleValue(), toAppendTo, pos);
        } else {
            throw new IllegalArgumentException("Cannot format given Object as a Number");
        }
    }

    /**
     * Parses text from a string to produce a <code>Number</code>.
     * <p>
     * The method attempts to parse text starting at the index given by
     * <code>pos</code>.
     * If parsing succeeds, then the index of <code>pos</code> is updated
     * to the index after the last character used (parsing does not necessarily
     * use all characters up to the end of the string), and the parsed
     * number is returned. The updated <code>pos</code> can be used to
     * indicate the starting point for the next call to this method.
     * If an error occurs, then the index of <code>pos</code> is not
     * changed, the error index of <code>pos</code> is set to the index of
     * the character where the error occurred, and null is returned.
     * <p>
     * See the {@link #parse(String, ParsePosition)} method for more information
     * on number parsing.
     *
     * <p>
     *  从字符串解析文本以生成<code> Number </code>。
     * <p>
     * 该方法尝试解析从<code> pos </code>给出的索引开始的文本。
     * 如果解析成功,则将<code> pos </code>的索引更新为使用最后一个字符后的索引(解析不一定使用直到字符串结尾的所有字符),并返回解析的数字。
     * 更新的<code> pos </code>可用于指示下一次调用此方法的起点。
     * 如果发生错误,则<code> pos </code>的索引不改变,<code> pos </code>的错误索引设置为发生错误的字符的索引,返回null 。
     * <p>
     *  有关数字解析的更多信息,请参阅{@link #parse(String,ParsePosition)}方法。
     * 
     * 
     * @param source A <code>String</code>, part of which should be parsed.
     * @param pos A <code>ParsePosition</code> object with index and error
     *            index information as described above.
     * @return A <code>Number</code> parsed from the string. In case of
     *         error, returns null.
     * @exception NullPointerException if <code>pos</code> is null.
     */
    @Override
    public final Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

   /**
     * Specialization of format.
     *
     * <p>
     *  格式的专业化。
     * 
     * 
     * @param number the double number to format
     * @return the formatted String
     * @exception        ArithmeticException if rounding is needed with rounding
     *                   mode being set to RoundingMode.UNNECESSARY
     * @see java.text.Format#format
     */
    public final String format(double number) {
        // Use fast-path for double result if that works
        String result = fastFormat(number);
        if (result != null)
            return result;

        return format(number, new StringBuffer(),
                      DontCareFieldPosition.INSTANCE).toString();
    }

    /*
     * fastFormat() is supposed to be implemented in concrete subclasses only.
     * Default implem always returns null.
     * <p>
     *  fastFormat()应该只在具体子类中实现。默认implem总是返回null。
     * 
     */
    String fastFormat(double number) { return null; }

   /**
     * Specialization of format.
     *
     * <p>
     *  格式的专业化。
     * 
     * 
     * @param number the long number to format
     * @return the formatted String
     * @exception        ArithmeticException if rounding is needed with rounding
     *                   mode being set to RoundingMode.UNNECESSARY
     * @see java.text.Format#format
     */
    public final String format(long number) {
        return format(number, new StringBuffer(),
                      DontCareFieldPosition.INSTANCE).toString();
    }

   /**
     * Specialization of format.
     *
     * <p>
     *  格式的专业化。
     * 
     * 
     * @param number     the double number to format
     * @param toAppendTo the StringBuffer to which the formatted text is to be
     *                   appended
     * @param pos        the field position
     * @return the formatted StringBuffer
     * @exception        ArithmeticException if rounding is needed with rounding
     *                   mode being set to RoundingMode.UNNECESSARY
     * @see java.text.Format#format
     */
    public abstract StringBuffer format(double number,
                                        StringBuffer toAppendTo,
                                        FieldPosition pos);

   /**
     * Specialization of format.
     *
     * <p>
     *  格式的专业化。
     * 
     * 
     * @param number     the long number to format
     * @param toAppendTo the StringBuffer to which the formatted text is to be
     *                   appended
     * @param pos        the field position
     * @return the formatted StringBuffer
     * @exception        ArithmeticException if rounding is needed with rounding
     *                   mode being set to RoundingMode.UNNECESSARY
     * @see java.text.Format#format
     */
    public abstract StringBuffer format(long number,
                                        StringBuffer toAppendTo,
                                        FieldPosition pos);

   /**
     * Returns a Long if possible (e.g., within the range [Long.MIN_VALUE,
     * Long.MAX_VALUE] and with no decimals), otherwise a Double.
     * If IntegerOnly is set, will stop at a decimal
     * point (or equivalent; e.g., for rational numbers "1 2/3", will stop
     * after the 1).
     * Does not throw an exception; if no object can be parsed, index is
     * unchanged!
     *
     * <p>
     *  如果可能,返回Long(例如,在[Long.MIN_VALUE,Long.MAX_VALUE]范围内且不带小数),否则返回Double。
     * 如果IntegerOnly被设置,将停止在小数点(或等效;例如,对于有理数"1 2/3",将停止在1之后)。不抛出异常;如果没有对象可以解析,索引不变！。
     * 
     * 
     * @param source the String to parse
     * @param parsePosition the parse position
     * @return the parsed value
     * @see java.text.NumberFormat#isParseIntegerOnly
     * @see java.text.Format#parseObject
     */
    public abstract Number parse(String source, ParsePosition parsePosition);

    /**
     * Parses text from the beginning of the given string to produce a number.
     * The method may not use the entire text of the given string.
     * <p>
     * See the {@link #parse(String, ParsePosition)} method for more information
     * on number parsing.
     *
     * <p>
     *  从给定字符串的开头解析文本以产生一个数字。该方法可能不使用给定字符串的整个文本。
     * <p>
     *  有关数字解析的更多信息,请参阅{@link #parse(String,ParsePosition)}方法。
     * 
     * 
     * @param source A <code>String</code> whose beginning should be parsed.
     * @return A <code>Number</code> parsed from the string.
     * @exception ParseException if the beginning of the specified string
     *            cannot be parsed.
     */
    public Number parse(String source) throws ParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Number result = parse(source, parsePosition);
        if (parsePosition.index == 0) {
            throw new ParseException("Unparseable number: \"" + source + "\"",
                                     parsePosition.errorIndex);
        }
        return result;
    }

    /**
     * Returns true if this format will parse numbers as integers only.
     * For example in the English locale, with ParseIntegerOnly true, the
     * string "1234." would be parsed as the integer value 1234 and parsing
     * would stop at the "." character.  Of course, the exact format accepted
     * by the parse operation is locale dependant and determined by sub-classes
     * of NumberFormat.
     *
     * <p>
     * 如果此格式仅将数字解析为整数,则返回true。例如在英语语言环境中,使用ParseIntegerOnly true,字符串"1234."将被解析为整数值1234,并且解析将停止在"。"。字符。
     * 当然,解析操作接受的确切格式是依赖于区域设置,并由NumberFormat的子类确定。
     * 
     * 
     * @return {@code true} if numbers should be parsed as integers only;
     *         {@code false} otherwise
     */
    public boolean isParseIntegerOnly() {
        return parseIntegerOnly;
    }

    /**
     * Sets whether or not numbers should be parsed as integers only.
     *
     * <p>
     *  设置是否应将数字解析为整数。
     * 
     * 
     * @param value {@code true} if numbers should be parsed as integers only;
     *              {@code false} otherwise
     * @see #isParseIntegerOnly
     */
    public void setParseIntegerOnly(boolean value) {
        parseIntegerOnly = value;
    }

    //============== Locale Stuff =====================

    /**
     * Returns a general-purpose number format for the current default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * This is the same as calling
     * {@link #getNumberInstance() getNumberInstance()}.
     *
     * <p>
     *  返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的通用数字格式。
     * 这和调用{@link #getNumberInstance()getNumberInstance()}相同。
     * 
     * 
     * @return the {@code NumberFormat} instance for general-purpose number
     * formatting
     */
    public final static NumberFormat getInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), NUMBERSTYLE);
    }

    /**
     * Returns a general-purpose number format for the specified locale.
     * This is the same as calling
     * {@link #getNumberInstance(java.util.Locale) getNumberInstance(inLocale)}.
     *
     * <p>
     *  返回指定语言环境的通用数字格式。这和调用{@link #getNumberInstance(java.util.Locale)getNumberInstance(inLocale)}相同。
     * 
     * 
     * @param inLocale the desired locale
     * @return the {@code NumberFormat} instance for general-purpose number
     * formatting
     */
    public static NumberFormat getInstance(Locale inLocale) {
        return getInstance(inLocale, NUMBERSTYLE);
    }

    /**
     * Returns a general-purpose number format for the current default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getNumberInstance(Locale)
     *     getNumberInstance(Locale.getDefault(Locale.Category.FORMAT))}.
     *
     * <p>
     *  返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的通用数字格式。
     *  <p>这相当于调用{@link #getNumberInstance(Locale)getNumberInstance(Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的通用数字格式。
     * 
     * 
     * @return the {@code NumberFormat} instance for general-purpose number
     * formatting
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     */
    public final static NumberFormat getNumberInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), NUMBERSTYLE);
    }

    /**
     * Returns a general-purpose number format for the specified locale.
     *
     * <p>
     *  返回指定语言环境的通用数字格式。
     * 
     * 
     * @param inLocale the desired locale
     * @return the {@code NumberFormat} instance for general-purpose number
     * formatting
     */
    public static NumberFormat getNumberInstance(Locale inLocale) {
        return getInstance(inLocale, NUMBERSTYLE);
    }

    /**
     * Returns an integer number format for the current default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale. The
     * returned number format is configured to round floating point numbers
     * to the nearest integer using half-even rounding (see {@link
     * java.math.RoundingMode#HALF_EVEN RoundingMode.HALF_EVEN}) for formatting,
     * and to parse only the integer part of an input string (see {@link
     * #isParseIntegerOnly isParseIntegerOnly}).
     * <p>This is equivalent to calling
     * {@link #getIntegerInstance(Locale)
     *     getIntegerInstance(Locale.getDefault(Locale.Category.FORMAT))}.
     *
     * <p>
     * 返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的整数数字格式。
     * 返回的数字格式被配置为使用半整数舍入(参见{@link java.math.RoundingMode#HALF_EVEN RoundingMode.HALF_EVEN})将浮点数舍入到最接近的整数,用于
     * 格式化,并且仅解析输入的整数部分字符串(参见{@link #isParseIntegerOnly isParseIntegerOnly})。
     * 返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的整数数字格式。
     *  <p>这相当于调用{@link #getIntegerInstance(Locale)getIntegerInstance(Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     * 返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的整数数字格式。
     * 
     * 
     * @see #getRoundingMode()
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @return a number format for integer values
     * @since 1.4
     */
    public final static NumberFormat getIntegerInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), INTEGERSTYLE);
    }

    /**
     * Returns an integer number format for the specified locale. The
     * returned number format is configured to round floating point numbers
     * to the nearest integer using half-even rounding (see {@link
     * java.math.RoundingMode#HALF_EVEN RoundingMode.HALF_EVEN}) for formatting,
     * and to parse only the integer part of an input string (see {@link
     * #isParseIntegerOnly isParseIntegerOnly}).
     *
     * <p>
     *  返回指定语言环境的整数格式。
     * 返回的数字格式被配置为使用半整数舍入(参见{@link java.math.RoundingMode#HALF_EVEN RoundingMode.HALF_EVEN})将浮点数舍入到最接近的整数,用于
     * 格式化,并且仅解析输入的整数部分字符串(参见{@link #isParseIntegerOnly isParseIntegerOnly})。
     *  返回指定语言环境的整数格式。
     * 
     * 
     * @param inLocale the desired locale
     * @see #getRoundingMode()
     * @return a number format for integer values
     * @since 1.4
     */
    public static NumberFormat getIntegerInstance(Locale inLocale) {
        return getInstance(inLocale, INTEGERSTYLE);
    }

    /**
     * Returns a currency format for the current default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getCurrencyInstance(Locale)
     *     getCurrencyInstance(Locale.getDefault(Locale.Category.FORMAT))}.
     *
     * <p>
     *  返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的货币格式。
     *  <p>这相当于调用{@link #getCurrencyInstance(Locale)getCurrencyInstance(Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的货币格式。
     * 
     * 
     * @return the {@code NumberFormat} instance for currency formatting
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     */
    public final static NumberFormat getCurrencyInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), CURRENCYSTYLE);
    }

    /**
     * Returns a currency format for the specified locale.
     *
     * <p>
     *  返回指定语言环境的货币格式。
     * 
     * 
     * @param inLocale the desired locale
     * @return the {@code NumberFormat} instance for currency formatting
     */
    public static NumberFormat getCurrencyInstance(Locale inLocale) {
        return getInstance(inLocale, CURRENCYSTYLE);
    }

    /**
     * Returns a percentage format for the current default
     * {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getPercentInstance(Locale)
     *     getPercentInstance(Locale.getDefault(Locale.Category.FORMAT))}.
     *
     * <p>
     *  返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的百分比格式。
     *  <p>这相当于调用{@link #getPercentInstance(Locale)getPercentInstance(Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  返回当前默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的百分比格式。
     * 
     * 
     * @return the {@code NumberFormat} instance for percentage formatting
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     */
    public final static NumberFormat getPercentInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), PERCENTSTYLE);
    }

    /**
     * Returns a percentage format for the specified locale.
     *
     * <p>
     * 返回指定语言环境的百分比格式。
     * 
     * 
     * @param inLocale the desired locale
     * @return the {@code NumberFormat} instance for percentage formatting
     */
    public static NumberFormat getPercentInstance(Locale inLocale) {
        return getInstance(inLocale, PERCENTSTYLE);
    }

    /**
     * Returns a scientific format for the current default locale.
     * <p>
     *  返回当前默认语言环境的科学格式。
     * 
     */
    /*public*/ final static NumberFormat getScientificInstance() {
        return getInstance(Locale.getDefault(Locale.Category.FORMAT), SCIENTIFICSTYLE);
    }

    /**
     * Returns a scientific format for the specified locale.
     *
     * <p>
     *  return getInstance(Locale.getDefault(Locale.Category.FORMAT),SCIENTIFICSTYLE); }}
     * 
     *  / **返回指定区域设置的科学格式。
     * 
     * 
     * @param inLocale the desired locale
     */
    /*public*/ static NumberFormat getScientificInstance(Locale inLocale) {
        return getInstance(inLocale, SCIENTIFICSTYLE);
    }

    /**
     * Returns an array of all locales for which the
     * <code>get*Instance</code> methods of this class can return
     * localized instances.
     * The returned array represents the union of locales supported by the Java
     * runtime and by installed
     * {@link java.text.spi.NumberFormatProvider NumberFormatProvider} implementations.
     * It must contain at least a <code>Locale</code> instance equal to
     * {@link java.util.Locale#US Locale.US}.
     *
     * <p>
     *  return getInstance(inLocale,SCIENTIFICSTYLE); }}
     * 
     *  / **返回此类的<code> get * Instance </code>方法可返回本地化实例的所有语言环境的数组。
     * 返回的数组表示Java运行时支持的语言环境的并集,以及安装的{@link java.text.spi.NumberFormatProvider NumberFormatProvider}实现。
     * 它必须至少包含等于{@link java.util.Locale#US Locale.US}的<code> Locale </code>实例。
     * 
     * 
     * @return An array of locales for which localized
     *         <code>NumberFormat</code> instances are available.
     */
    public static Locale[] getAvailableLocales() {
        LocaleServiceProviderPool pool =
            LocaleServiceProviderPool.getPool(NumberFormatProvider.class);
        return pool.getAvailableLocales();
    }

    /**
     * Overrides hashCode.
     * <p>
     *  覆盖hashCode。
     * 
     */
    @Override
    public int hashCode() {
        return maximumIntegerDigits * 37 + maxFractionDigits;
        // just enough fields for a reasonable distribution
    }

    /**
     * Overrides equals.
     * <p>
     *  覆盖equals。
     * 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        NumberFormat other = (NumberFormat) obj;
        return (maximumIntegerDigits == other.maximumIntegerDigits
            && minimumIntegerDigits == other.minimumIntegerDigits
            && maximumFractionDigits == other.maximumFractionDigits
            && minimumFractionDigits == other.minimumFractionDigits
            && groupingUsed == other.groupingUsed
            && parseIntegerOnly == other.parseIntegerOnly);
    }

    /**
     * Overrides Cloneable.
     * <p>
     *  覆盖可克隆。
     * 
     */
    @Override
    public Object clone() {
        NumberFormat other = (NumberFormat) super.clone();
        return other;
    }

    /**
     * Returns true if grouping is used in this format. For example, in the
     * English locale, with grouping on, the number 1234567 might be formatted
     * as "1,234,567". The grouping separator as well as the size of each group
     * is locale dependant and is determined by sub-classes of NumberFormat.
     *
     * <p>
     *  如果以此格式使用分组,则返回true。例如,在英语区域设置中,如果打开分组,则数字1234567可能格式为"1,234,567"。
     * 分组分隔符以及每个组的大小取决于语言环境,并由NumberFormat的子类确定。
     * 
     * 
     * @return {@code true} if grouping is used;
     *         {@code false} otherwise
     * @see #setGroupingUsed
     */
    public boolean isGroupingUsed() {
        return groupingUsed;
    }

    /**
     * Set whether or not grouping will be used in this format.
     *
     * <p>
     *  设置是否以此格式使用分组。
     * 
     * 
     * @param newValue {@code true} if grouping is used;
     *                 {@code false} otherwise
     * @see #isGroupingUsed
     */
    public void setGroupingUsed(boolean newValue) {
        groupingUsed = newValue;
    }

    /**
     * Returns the maximum number of digits allowed in the integer portion of a
     * number.
     *
     * <p>
     *  返回数字的整数部分中允许的最大位数。
     * 
     * 
     * @return the maximum number of digits
     * @see #setMaximumIntegerDigits
     */
    public int getMaximumIntegerDigits() {
        return maximumIntegerDigits;
    }

    /**
     * Sets the maximum number of digits allowed in the integer portion of a
     * number. maximumIntegerDigits must be &ge; minimumIntegerDigits.  If the
     * new value for maximumIntegerDigits is less than the current value
     * of minimumIntegerDigits, then minimumIntegerDigits will also be set to
     * the new value.
     *
     * <p>
     * 设置数字的整数部分中允许的最大位数。 maximumIntegerDigits必须为&ge; minimumIntegerDigits。
     * 如果maximumIntegerDigits的新值小于minimumIntegerDigits的当前值,那么minimumIntegerDigits也将设置为新值。
     * 
     * 
     * @param newValue the maximum number of integer digits to be shown; if
     * less than zero, then zero is used. The concrete subclass may enforce an
     * upper limit to this value appropriate to the numeric type being formatted.
     * @see #getMaximumIntegerDigits
     */
    public void setMaximumIntegerDigits(int newValue) {
        maximumIntegerDigits = Math.max(0,newValue);
        if (minimumIntegerDigits > maximumIntegerDigits) {
            minimumIntegerDigits = maximumIntegerDigits;
        }
    }

    /**
     * Returns the minimum number of digits allowed in the integer portion of a
     * number.
     *
     * <p>
     *  返回数字的整数部分中允许的最小位数。
     * 
     * 
     * @return the minimum number of digits
     * @see #setMinimumIntegerDigits
     */
    public int getMinimumIntegerDigits() {
        return minimumIntegerDigits;
    }

    /**
     * Sets the minimum number of digits allowed in the integer portion of a
     * number. minimumIntegerDigits must be &le; maximumIntegerDigits.  If the
     * new value for minimumIntegerDigits exceeds the current value
     * of maximumIntegerDigits, then maximumIntegerDigits will also be set to
     * the new value
     *
     * <p>
     *  设置数字的整数部分中允许的最小位数。 minimumIntegerDigits必须为&le; maximumIntegerDigits。
     * 如果minimumIntegerDigits的新值超过maximumIntegerDigits的当前值,则maximumIntegerDigits也将设置为新值。
     * 
     * 
     * @param newValue the minimum number of integer digits to be shown; if
     * less than zero, then zero is used. The concrete subclass may enforce an
     * upper limit to this value appropriate to the numeric type being formatted.
     * @see #getMinimumIntegerDigits
     */
    public void setMinimumIntegerDigits(int newValue) {
        minimumIntegerDigits = Math.max(0,newValue);
        if (minimumIntegerDigits > maximumIntegerDigits) {
            maximumIntegerDigits = minimumIntegerDigits;
        }
    }

    /**
     * Returns the maximum number of digits allowed in the fraction portion of a
     * number.
     *
     * <p>
     *  返回数字小数部分中允许的最大位数。
     * 
     * 
     * @return the maximum number of digits.
     * @see #setMaximumFractionDigits
     */
    public int getMaximumFractionDigits() {
        return maximumFractionDigits;
    }

    /**
     * Sets the maximum number of digits allowed in the fraction portion of a
     * number. maximumFractionDigits must be &ge; minimumFractionDigits.  If the
     * new value for maximumFractionDigits is less than the current value
     * of minimumFractionDigits, then minimumFractionDigits will also be set to
     * the new value.
     *
     * <p>
     *  设置数字小数部分中允许的最大位数。 maximumFractionDigits必须是&ge; minimumFractionDigits。
     * 如果maximumFractionDigits的新值小于minimumFractionDigits的当前值,那么minimumFractionDigits也将设置为新值。
     * 
     * 
     * @param newValue the maximum number of fraction digits to be shown; if
     * less than zero, then zero is used. The concrete subclass may enforce an
     * upper limit to this value appropriate to the numeric type being formatted.
     * @see #getMaximumFractionDigits
     */
    public void setMaximumFractionDigits(int newValue) {
        maximumFractionDigits = Math.max(0,newValue);
        if (maximumFractionDigits < minimumFractionDigits) {
            minimumFractionDigits = maximumFractionDigits;
        }
    }

    /**
     * Returns the minimum number of digits allowed in the fraction portion of a
     * number.
     *
     * <p>
     *  返回数字小数部分中允许的最小位数。
     * 
     * 
     * @return the minimum number of digits
     * @see #setMinimumFractionDigits
     */
    public int getMinimumFractionDigits() {
        return minimumFractionDigits;
    }

    /**
     * Sets the minimum number of digits allowed in the fraction portion of a
     * number. minimumFractionDigits must be &le; maximumFractionDigits.  If the
     * new value for minimumFractionDigits exceeds the current value
     * of maximumFractionDigits, then maximumIntegerDigits will also be set to
     * the new value
     *
     * <p>
     *  设置数字小数部分中允许的最小位数。 minimumFractionDigits必须为&le; maximumFractionDigits。
     * 如果minimumFractionDigits的新值超过maximumFractionDigits的当前值,则maximumIntegerDigits也将设置为新值。
     * 
     * 
     * @param newValue the minimum number of fraction digits to be shown; if
     * less than zero, then zero is used. The concrete subclass may enforce an
     * upper limit to this value appropriate to the numeric type being formatted.
     * @see #getMinimumFractionDigits
     */
    public void setMinimumFractionDigits(int newValue) {
        minimumFractionDigits = Math.max(0,newValue);
        if (maximumFractionDigits < minimumFractionDigits) {
            maximumFractionDigits = minimumFractionDigits;
        }
    }

    /**
     * Gets the currency used by this number format when formatting
     * currency values. The initial value is derived in a locale dependent
     * way. The returned value may be null if no valid
     * currency could be determined and no currency has been set using
     * {@link #setCurrency(java.util.Currency) setCurrency}.
     * <p>
     * The default implementation throws
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     * 在格式化货币值时获取此数字格式使用的货币。初始值是以区域设置相关方式导出的。
     * 如果未能确定有效的货币并且没有使用{@link #setCurrency(java.util.Currency)setCurrency}设置货币,则返回的值可以为null。
     * <p>
     *  默认实现throws <code> UnsupportedOperationException </code>。
     * 
     * 
     * @return the currency used by this number format, or <code>null</code>
     * @exception UnsupportedOperationException if the number format class
     * doesn't implement currency formatting
     * @since 1.4
     */
    public Currency getCurrency() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the currency used by this number format when formatting
     * currency values. This does not update the minimum or maximum
     * number of fraction digits used by the number format.
     * <p>
     * The default implementation throws
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  设置格式化货币值时此数字格式使用的货币。这不会更新数字格式使用的小数位数的最小或最大数。
     * <p>
     *  默认实现throws <code> UnsupportedOperationException </code>。
     * 
     * 
     * @param currency the new currency to be used by this number format
     * @exception UnsupportedOperationException if the number format class
     * doesn't implement currency formatting
     * @exception NullPointerException if <code>currency</code> is null
     * @since 1.4
     */
    public void setCurrency(Currency currency) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the {@link java.math.RoundingMode} used in this NumberFormat.
     * The default implementation of this method in NumberFormat
     * always throws {@link java.lang.UnsupportedOperationException}.
     * Subclasses which handle different rounding modes should override
     * this method.
     *
     * <p>
     *  获取此NumberFormat中使用的{@link java.math.RoundingMode}。
     * 该方法在NumberFormat中的默认实现总是抛出{@link java.lang.UnsupportedOperationException}。处理不同舍入模式的子类应覆盖此方法。
     * 
     * 
     * @exception UnsupportedOperationException The default implementation
     *     always throws this exception
     * @return The <code>RoundingMode</code> used for this NumberFormat.
     * @see #setRoundingMode(RoundingMode)
     * @since 1.6
     */
    public RoundingMode getRoundingMode() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the {@link java.math.RoundingMode} used in this NumberFormat.
     * The default implementation of this method in NumberFormat always
     * throws {@link java.lang.UnsupportedOperationException}.
     * Subclasses which handle different rounding modes should override
     * this method.
     *
     * <p>
     *  设置在此NumberFormat中使用的{@link java.math.RoundingMode}。
     * 该方法在NumberFormat中的默认实现总是抛出{@link java.lang.UnsupportedOperationException}。处理不同舍入模式的子类应覆盖此方法。
     * 
     * 
     * @exception UnsupportedOperationException The default implementation
     *     always throws this exception
     * @exception NullPointerException if <code>roundingMode</code> is null
     * @param roundingMode The <code>RoundingMode</code> to be used
     * @see #getRoundingMode()
     * @since 1.6
     */
    public void setRoundingMode(RoundingMode roundingMode) {
        throw new UnsupportedOperationException();
    }

    // =======================privates===============================

    private static NumberFormat getInstance(Locale desiredLocale,
                                           int choice) {
        LocaleProviderAdapter adapter;
        adapter = LocaleProviderAdapter.getAdapter(NumberFormatProvider.class,
                                                   desiredLocale);
        NumberFormat numberFormat = getInstance(adapter, desiredLocale, choice);
        if (numberFormat == null) {
            numberFormat = getInstance(LocaleProviderAdapter.forJRE(),
                                       desiredLocale, choice);
        }
        return numberFormat;
    }

    private static NumberFormat getInstance(LocaleProviderAdapter adapter,
                                            Locale locale, int choice) {
        NumberFormatProvider provider = adapter.getNumberFormatProvider();
        NumberFormat numberFormat = null;
        switch (choice) {
        case NUMBERSTYLE:
            numberFormat = provider.getNumberInstance(locale);
            break;
        case PERCENTSTYLE:
            numberFormat = provider.getPercentInstance(locale);
            break;
        case CURRENCYSTYLE:
            numberFormat = provider.getCurrencyInstance(locale);
            break;
        case INTEGERSTYLE:
            numberFormat = provider.getIntegerInstance(locale);
            break;
        }
        return numberFormat;
    }

    /**
     * First, read in the default serializable data.
     *
     * Then, if <code>serialVersionOnStream</code> is less than 1, indicating that
     * the stream was written by JDK 1.1,
     * set the <code>int</code> fields such as <code>maximumIntegerDigits</code>
     * to be equal to the <code>byte</code> fields such as <code>maxIntegerDigits</code>,
     * since the <code>int</code> fields were not present in JDK 1.1.
     * Finally, set serialVersionOnStream back to the maximum allowed value so that
     * default serialization will work properly if this object is streamed out again.
     *
     * <p>If <code>minimumIntegerDigits</code> is greater than
     * <code>maximumIntegerDigits</code> or <code>minimumFractionDigits</code>
     * is greater than <code>maximumFractionDigits</code>, then the stream data
     * is invalid and this method throws an <code>InvalidObjectException</code>.
     * In addition, if any of these values is negative, then this method throws
     * an <code>InvalidObjectException</code>.
     *
     * <p>
     *  首先,读取默认的可序列化数据。
     * 
     * 然后,如果<code> serialVersionOnStream </code>小于1,表示流由JDK 1.1写入,则将<code> int </code>字段(如<code> maximumInte
     * gerDigits </code>)设置为等于到<code>字节</code>字段(例如<code> maxIntegerDigits </code>),因为<code> int </code>字段在J
     * DK 1.1中不存在。
     * 最后,将serialVersionOnStream设置为最大允许值,以便如果此对象再次流出,则默认序列化将正常工作。
     * 
     *  <p>如果<code> minimumIntegerDigits </code>大于<code> maximumIntegerDigits </code>或<code> minimumFraction
     * Digits </code>大于<code> maximumFractionDigits </code>,则流数据无效并且此方法会抛出一个<code> InvalidObjectException </code>
     * 。
     * 此外,如果这些值中的任何一个是负的,那么这个方法会抛出一个<code> InvalidObjectException </code>。
     * 
     * 
     * @since 1.2
     */
    private void readObject(ObjectInputStream stream)
         throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        if (serialVersionOnStream < 1) {
            // Didn't have additional int fields, reassign to use them.
            maximumIntegerDigits = maxIntegerDigits;
            minimumIntegerDigits = minIntegerDigits;
            maximumFractionDigits = maxFractionDigits;
            minimumFractionDigits = minFractionDigits;
        }
        if (minimumIntegerDigits > maximumIntegerDigits ||
            minimumFractionDigits > maximumFractionDigits ||
            minimumIntegerDigits < 0 || minimumFractionDigits < 0) {
            throw new InvalidObjectException("Digit count range invalid");
        }
        serialVersionOnStream = currentSerialVersion;
    }

    /**
     * Write out the default serializable data, after first setting
     * the <code>byte</code> fields such as <code>maxIntegerDigits</code> to be
     * equal to the <code>int</code> fields such as <code>maximumIntegerDigits</code>
     * (or to <code>Byte.MAX_VALUE</code>, whichever is smaller), for compatibility
     * with the JDK 1.1 version of the stream format.
     *
     * <p>
     *  在首先将<code> byte </code>字段(例如<code> maxIntegerDigits </code>)设置为等于<code> int </code>字段(如<code> maximu
     * mIntegerDigits)后,写出默认的可序列化数据</code>(或到<code> Byte.MAX_VALUE </code>,以较小者为准),以与流格式的JDK 1.1版本兼容。
     * 
     * 
     * @since 1.2
     */
    private void writeObject(ObjectOutputStream stream)
         throws IOException
    {
        maxIntegerDigits = (maximumIntegerDigits > Byte.MAX_VALUE) ?
                           Byte.MAX_VALUE : (byte)maximumIntegerDigits;
        minIntegerDigits = (minimumIntegerDigits > Byte.MAX_VALUE) ?
                           Byte.MAX_VALUE : (byte)minimumIntegerDigits;
        maxFractionDigits = (maximumFractionDigits > Byte.MAX_VALUE) ?
                            Byte.MAX_VALUE : (byte)maximumFractionDigits;
        minFractionDigits = (minimumFractionDigits > Byte.MAX_VALUE) ?
                            Byte.MAX_VALUE : (byte)minimumFractionDigits;
        stream.defaultWriteObject();
    }

    // Constants used by factory methods to specify a style of format.
    private static final int NUMBERSTYLE = 0;
    private static final int CURRENCYSTYLE = 1;
    private static final int PERCENTSTYLE = 2;
    private static final int SCIENTIFICSTYLE = 3;
    private static final int INTEGERSTYLE = 4;

    /**
     * True if the grouping (i.e. thousands) separator is used when
     * formatting and parsing numbers.
     *
     * <p>
     *  如果在格式化和解析数字时使用分组(即千位)分隔符,则为true。
     * 
     * 
     * @serial
     * @see #isGroupingUsed
     */
    private boolean groupingUsed = true;

    /**
     * The maximum number of digits allowed in the integer portion of a
     * number.  <code>maxIntegerDigits</code> must be greater than or equal to
     * <code>minIntegerDigits</code>.
     * <p>
     * <strong>Note:</strong> This field exists only for serialization
     * compatibility with JDK 1.1.  In Java platform 2 v1.2 and higher, the new
     * <code>int</code> field <code>maximumIntegerDigits</code> is used instead.
     * When writing to a stream, <code>maxIntegerDigits</code> is set to
     * <code>maximumIntegerDigits</code> or <code>Byte.MAX_VALUE</code>,
     * whichever is smaller.  When reading from a stream, this field is used
     * only if <code>serialVersionOnStream</code> is less than 1.
     *
     * <p>
     *  在数字的整数部分中允许的最大位数。 <code> maxIntegerDigits </code>必须大于或等于<code> minIntegerDigits </code>。
     * <p>
     * <strong>注意：</strong>此字段仅与JDK 1.1的序列化兼容性存在。
     * 在Java平台2 v1.2及更高版本中,将使用新的<code> int </code>字段<code> maximumIntegerDigits </code>。
     * 写入流时,<code> maxIntegerDigits </code>设置为<code> maximumIntegerDigits </code>或<code> Byte.MAX_VALUE </code>
     * ,以较小者为准。
     * 在Java平台2 v1.2及更高版本中,将使用新的<code> int </code>字段<code> maximumIntegerDigits </code>。
     * 从流读取时,仅当<code> serialVersionOnStream </code>小于1时,才使用此字段。
     * 
     * 
     * @serial
     * @see #getMaximumIntegerDigits
     */
    private byte    maxIntegerDigits = 40;

    /**
     * The minimum number of digits allowed in the integer portion of a
     * number.  <code>minimumIntegerDigits</code> must be less than or equal to
     * <code>maximumIntegerDigits</code>.
     * <p>
     * <strong>Note:</strong> This field exists only for serialization
     * compatibility with JDK 1.1.  In Java platform 2 v1.2 and higher, the new
     * <code>int</code> field <code>minimumIntegerDigits</code> is used instead.
     * When writing to a stream, <code>minIntegerDigits</code> is set to
     * <code>minimumIntegerDigits</code> or <code>Byte.MAX_VALUE</code>,
     * whichever is smaller.  When reading from a stream, this field is used
     * only if <code>serialVersionOnStream</code> is less than 1.
     *
     * <p>
     *  数字的整数部分中允许的最小位数。 <code> minimumIntegerDigits </code>必须小于或等于<code> maximumIntegerDigits </code>。
     * <p>
     *  <strong>注意：</strong>此字段仅与JDK 1.1的序列化兼容性存在。
     * 在Java平台2 v1.2及更高版本中,使用新的<code> int </code>字段<code> minimumIntegerDigits </code>。
     * 写入流时,<code> minIntegerDigits </code>设置为<code> minimumIntegerDigits </code>或<code> Byte.MAX_VALUE </code>
     * ,以较小者为准。
     * 在Java平台2 v1.2及更高版本中,使用新的<code> int </code>字段<code> minimumIntegerDigits </code>。
     * 从流读取时,仅当<code> serialVersionOnStream </code>小于1时,才使用此字段。
     * 
     * 
     * @serial
     * @see #getMinimumIntegerDigits
     */
    private byte    minIntegerDigits = 1;

    /**
     * The maximum number of digits allowed in the fractional portion of a
     * number.  <code>maximumFractionDigits</code> must be greater than or equal to
     * <code>minimumFractionDigits</code>.
     * <p>
     * <strong>Note:</strong> This field exists only for serialization
     * compatibility with JDK 1.1.  In Java platform 2 v1.2 and higher, the new
     * <code>int</code> field <code>maximumFractionDigits</code> is used instead.
     * When writing to a stream, <code>maxFractionDigits</code> is set to
     * <code>maximumFractionDigits</code> or <code>Byte.MAX_VALUE</code>,
     * whichever is smaller.  When reading from a stream, this field is used
     * only if <code>serialVersionOnStream</code> is less than 1.
     *
     * <p>
     *  数字小数部分中允许的最大位数。 <code> maximumFractionDigits </code>必须大于或等于<code> minimumFractionDigits </code>。
     * <p>
     * <strong>注意：</strong>此字段仅与JDK 1.1的序列化兼容性存在。
     * 在Java平台2 v1.2及更高版本中,使用新的<code> int </code>字段<code> maximumFractionDigits </code>。
     * 写入流时,<code> maxFractionDigits </code>设置为<code> maximumFractionDigits </code>或<code> Byte.MAX_VALUE </code>
     * ,以较小者为准。
     * 在Java平台2 v1.2及更高版本中,使用新的<code> int </code>字段<code> maximumFractionDigits </code>。
     * 从流读取时,仅当<code> serialVersionOnStream </code>小于1时,才使用此字段。
     * 
     * 
     * @serial
     * @see #getMaximumFractionDigits
     */
    private byte    maxFractionDigits = 3;    // invariant, >= minFractionDigits

    /**
     * The minimum number of digits allowed in the fractional portion of a
     * number.  <code>minimumFractionDigits</code> must be less than or equal to
     * <code>maximumFractionDigits</code>.
     * <p>
     * <strong>Note:</strong> This field exists only for serialization
     * compatibility with JDK 1.1.  In Java platform 2 v1.2 and higher, the new
     * <code>int</code> field <code>minimumFractionDigits</code> is used instead.
     * When writing to a stream, <code>minFractionDigits</code> is set to
     * <code>minimumFractionDigits</code> or <code>Byte.MAX_VALUE</code>,
     * whichever is smaller.  When reading from a stream, this field is used
     * only if <code>serialVersionOnStream</code> is less than 1.
     *
     * <p>
     *  数字小数部分中允许的最小位数。 <code> minimumFractionDigits </code>必须小于或等于<code> maximumFractionDigits </code>。
     * <p>
     *  <strong>注意：</strong>此字段仅与JDK 1.1的序列化兼容性存在。
     * 在Java平台2 v1.2及更高版本中,使用新的<code> int </code>字段<code> minimumFractionDigits </code>。
     * 写入流时,<code> minFractionDigits </code>设置为<code> minimumFractionDigits </code>或<code> Byte.MAX_VALUE </code>
     * ,以较小者为准。
     * 在Java平台2 v1.2及更高版本中,使用新的<code> int </code>字段<code> minimumFractionDigits </code>。
     * 从流读取时,仅当<code> serialVersionOnStream </code>小于1时,才使用此字段。
     * 
     * 
     * @serial
     * @see #getMinimumFractionDigits
     */
    private byte    minFractionDigits = 0;

    /**
     * True if this format will parse numbers as integers only.
     *
     * <p>
     *  如果此格式仅将数字解析为整数,则为True。
     * 
     * 
     * @serial
     * @see #isParseIntegerOnly
     */
    private boolean parseIntegerOnly = false;

    // new fields for 1.2.  byte is too small for integer digits.

    /**
     * The maximum number of digits allowed in the integer portion of a
     * number.  <code>maximumIntegerDigits</code> must be greater than or equal to
     * <code>minimumIntegerDigits</code>.
     *
     * <p>
     *  在数字的整数部分中允许的最大位数。 <code> maximumIntegerDigits </code>必须大于或等于<code> minimumIntegerDigits </code>。
     * 
     * 
     * @serial
     * @since 1.2
     * @see #getMaximumIntegerDigits
     */
    private int    maximumIntegerDigits = 40;

    /**
     * The minimum number of digits allowed in the integer portion of a
     * number.  <code>minimumIntegerDigits</code> must be less than or equal to
     * <code>maximumIntegerDigits</code>.
     *
     * <p>
     * 数字的整数部分中允许的最小位数。 <code> minimumIntegerDigits </code>必须小于或等于<code> maximumIntegerDigits </code>。
     * 
     * 
     * @serial
     * @since 1.2
     * @see #getMinimumIntegerDigits
     */
    private int    minimumIntegerDigits = 1;

    /**
     * The maximum number of digits allowed in the fractional portion of a
     * number.  <code>maximumFractionDigits</code> must be greater than or equal to
     * <code>minimumFractionDigits</code>.
     *
     * <p>
     *  数字小数部分中允许的最大位数。 <code> maximumFractionDigits </code>必须大于或等于<code> minimumFractionDigits </code>。
     * 
     * 
     * @serial
     * @since 1.2
     * @see #getMaximumFractionDigits
     */
    private int    maximumFractionDigits = 3;    // invariant, >= minFractionDigits

    /**
     * The minimum number of digits allowed in the fractional portion of a
     * number.  <code>minimumFractionDigits</code> must be less than or equal to
     * <code>maximumFractionDigits</code>.
     *
     * <p>
     *  数字小数部分中允许的最小位数。 <code> minimumFractionDigits </code>必须小于或等于<code> maximumFractionDigits </code>。
     * 
     * 
     * @serial
     * @since 1.2
     * @see #getMinimumFractionDigits
     */
    private int    minimumFractionDigits = 0;

    static final int currentSerialVersion = 1;

    /**
     * Describes the version of <code>NumberFormat</code> present on the stream.
     * Possible values are:
     * <ul>
     * <li><b>0</b> (or uninitialized): the JDK 1.1 version of the stream format.
     *     In this version, the <code>int</code> fields such as
     *     <code>maximumIntegerDigits</code> were not present, and the <code>byte</code>
     *     fields such as <code>maxIntegerDigits</code> are used instead.
     *
     * <li><b>1</b>: the 1.2 version of the stream format.  The values of the
     *     <code>byte</code> fields such as <code>maxIntegerDigits</code> are ignored,
     *     and the <code>int</code> fields such as <code>maximumIntegerDigits</code>
     *     are used instead.
     * </ul>
     * When streaming out a <code>NumberFormat</code>, the most recent format
     * (corresponding to the highest allowable <code>serialVersionOnStream</code>)
     * is always written.
     *
     * <p>
     *  描述流上存在的<code> NumberFormat </code>的版本。可能的值为：
     * <ul>
     *  <li> <b> 0 </b>(或未初始化)：流格式的JDK 1.1版本。
     * 在此版本中,不存在<code> int </code>字段,如<code> maximumIntegerDigits </code>,<code> byte </code>字段如<code> maxIn
     * tegerDigits </code>使用。
     *  <li> <b> 0 </b>(或未初始化)：流格式的JDK 1.1版本。
     * 
     *  <li> <b> 1 </b>：1.2版本的流格式。
     * 将忽略<code> byte </code>字段的值(如<code> maxIntegerDigits </code>),而改用<code> int </code>字段,如<code> maximumI
     * ntegerDigits </code> 。
     *  <li> <b> 1 </b>：1.2版本的流格式。
     * </ul>
     *  当流出一个<code> NumberFormat </code>时,总是写入最新的格式(对应于最高允许的<code> serialVersionOnStream </code>)。
     * 
     * 
     * @serial
     * @since 1.2
     */
    private int serialVersionOnStream = currentSerialVersion;

    // Removed "implements Cloneable" clause.  Needs to update serialization
    // ID for backward compatibility.
    static final long serialVersionUID = -2308460125733713944L;


    //
    // class for AttributedCharacterIterator attributes
    //
    /**
     * Defines constants that are used as attribute keys in the
     * <code>AttributedCharacterIterator</code> returned
     * from <code>NumberFormat.formatToCharacterIterator</code> and as
     * field identifiers in <code>FieldPosition</code>.
     *
     * <p>
     * 定义在<code> NumberFormat.formatToCharacterIterator </code>返回的<code> AttributedCharacterIterator </code>
     * 中作为属性键使用的常量,以及<code> FieldPosition </code>中的字段标识。
     * 
     * 
     * @since 1.4
     */
    public static class Field extends Format.Field {

        // Proclaim serial compatibility with 1.4 FCS
        private static final long serialVersionUID = 7494728892700160890L;

        // table of all instances in this class, used by readResolve
        private static final Map<String, Field> instanceMap = new HashMap<>(11);

        /**
         * Creates a Field instance with the specified
         * name.
         *
         * <p>
         *  创建具有指定名称的字段实例。
         * 
         * 
         * @param name Name of the attribute
         */
        protected Field(String name) {
            super(name);
            if (this.getClass() == NumberFormat.Field.class) {
                instanceMap.put(name, this);
            }
        }

        /**
         * Resolves instances being deserialized to the predefined constants.
         *
         * <p>
         *  解析反序列化为预定义常量的实例。
         * 
         * 
         * @throws InvalidObjectException if the constant could not be resolved.
         * @return resolved NumberFormat.Field constant
         */
        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != NumberFormat.Field.class) {
                throw new InvalidObjectException("subclass didn't correctly implement readResolve");
            }

            Object instance = instanceMap.get(getName());
            if (instance != null) {
                return instance;
            } else {
                throw new InvalidObjectException("unknown attribute name");
            }
        }

        /**
         * Constant identifying the integer field.
         * <p>
         *  用于识别整数字段的常量。
         * 
         */
        public static final Field INTEGER = new Field("integer");

        /**
         * Constant identifying the fraction field.
         * <p>
         *  常数识别分数字段。
         * 
         */
        public static final Field FRACTION = new Field("fraction");

        /**
         * Constant identifying the exponent field.
         * <p>
         *  常数标识指数字段。
         * 
         */
        public static final Field EXPONENT = new Field("exponent");

        /**
         * Constant identifying the decimal separator field.
         * <p>
         *  常量标识小数分隔符字段。
         * 
         */
        public static final Field DECIMAL_SEPARATOR =
                            new Field("decimal separator");

        /**
         * Constant identifying the sign field.
         * <p>
         *  常量标识符号字段。
         * 
         */
        public static final Field SIGN = new Field("sign");

        /**
         * Constant identifying the grouping separator field.
         * <p>
         *  常量标识分组分隔符字段。
         * 
         */
        public static final Field GROUPING_SEPARATOR =
                            new Field("grouping separator");

        /**
         * Constant identifying the exponent symbol field.
         * <p>
         *  常量标识指数符号字段。
         * 
         */
        public static final Field EXPONENT_SYMBOL = new
                            Field("exponent symbol");

        /**
         * Constant identifying the percent field.
         * <p>
         *  常量标识百分比字段。
         * 
         */
        public static final Field PERCENT = new Field("percent");

        /**
         * Constant identifying the permille field.
         * <p>
         *  常数识别permille字段。
         * 
         */
        public static final Field PERMILLE = new Field("per mille");

        /**
         * Constant identifying the currency field.
         * <p>
         *  常量标识货币字段。
         * 
         */
        public static final Field CURRENCY = new Field("currency");

        /**
         * Constant identifying the exponent sign field.
         * <p>
         *  常数标识指数符号字段。
         */
        public static final Field EXPONENT_SIGN = new Field("exponent sign");
    }
}
