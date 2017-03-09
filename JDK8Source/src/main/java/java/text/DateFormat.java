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
 * (C) Copyright Taligent, Inc. 1996 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996  - 保留所有权利(C)版权所有IBM Corp. 1996  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.io.InvalidObjectException;
import java.text.spi.DateFormatProvider;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.spi.LocaleServiceProvider;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleServiceProviderPool;

/**
 * {@code DateFormat} is an abstract class for date/time formatting subclasses which
 * formats and parses dates or time in a language-independent manner.
 * The date/time formatting subclass, such as {@link SimpleDateFormat}, allows for
 * formatting (i.e., date &rarr; text), parsing (text &rarr; date), and
 * normalization.  The date is represented as a <code>Date</code> object or
 * as the milliseconds since January 1, 1970, 00:00:00 GMT.
 *
 * <p>{@code DateFormat} provides many class methods for obtaining default date/time
 * formatters based on the default or a given locale and a number of formatting
 * styles. The formatting styles include {@link #FULL}, {@link #LONG}, {@link #MEDIUM}, and {@link #SHORT}. More
 * detail and examples of using these styles are provided in the method
 * descriptions.
 *
 * <p>{@code DateFormat} helps you to format and parse dates for any locale.
 * Your code can be completely independent of the locale conventions for
 * months, days of the week, or even the calendar format: lunar vs. solar.
 *
 * <p>To format a date for the current Locale, use one of the
 * static factory methods:
 * <blockquote>
 * <pre>{@code
 * myString = DateFormat.getDateInstance().format(myDate);
 * }</pre>
 * </blockquote>
 * <p>If you are formatting multiple dates, it is
 * more efficient to get the format and use it multiple times so that
 * the system doesn't have to fetch the information about the local
 * language and country conventions multiple times.
 * <blockquote>
 * <pre>{@code
 * DateFormat df = DateFormat.getDateInstance();
 * for (int i = 0; i < myDate.length; ++i) {
 *     output.println(df.format(myDate[i]) + "; ");
 * }
 * }</pre>
 * </blockquote>
 * <p>To format a date for a different Locale, specify it in the
 * call to {@link #getDateInstance(int, Locale) getDateInstance()}.
 * <blockquote>
 * <pre>{@code
 * DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.FRANCE);
 * }</pre>
 * </blockquote>
 * <p>You can use a DateFormat to parse also.
 * <blockquote>
 * <pre>{@code
 * myDate = df.parse(myString);
 * }</pre>
 * </blockquote>
 * <p>Use {@code getDateInstance} to get the normal date format for that country.
 * There are other static factory methods available.
 * Use {@code getTimeInstance} to get the time format for that country.
 * Use {@code getDateTimeInstance} to get a date and time format. You can pass in
 * different options to these factory methods to control the length of the
 * result; from {@link #SHORT} to {@link #MEDIUM} to {@link #LONG} to {@link #FULL}. The exact result depends
 * on the locale, but generally:
 * <ul><li>{@link #SHORT} is completely numeric, such as {@code 12.13.52} or {@code 3:30pm}
 * <li>{@link #MEDIUM} is longer, such as {@code Jan 12, 1952}
 * <li>{@link #LONG} is longer, such as {@code January 12, 1952} or {@code 3:30:32pm}
 * <li>{@link #FULL} is pretty completely specified, such as
 * {@code Tuesday, April 12, 1952 AD or 3:30:42pm PST}.
 * </ul>
 *
 * <p>You can also set the time zone on the format if you wish.
 * If you want even more control over the format or parsing,
 * (or want to give your users more control),
 * you can try casting the {@code DateFormat} you get from the factory methods
 * to a {@link SimpleDateFormat}. This will work for the majority
 * of countries; just remember to put it in a {@code try} block in case you
 * encounter an unusual one.
 *
 * <p>You can also use forms of the parse and format methods with
 * {@link ParsePosition} and {@link FieldPosition} to
 * allow you to
 * <ul><li>progressively parse through pieces of a string.
 * <li>align any particular field, or find out where it is for selection
 * on the screen.
 * </ul>
 *
 * <h3><a name="synchronization">Synchronization</a></h3>
 *
 * <p>
 * Date formats are not synchronized.
 * It is recommended to create separate format instances for each thread.
 * If multiple threads access a format concurrently, it must be synchronized
 * externally.
 *
 * <p>
 *  {@code DateFormat}是日期/时间格式化子类的抽象类,它以独立于语言的方式格式化和解析日期或时间。
 * 日期/时间格式化子类,如{@link SimpleDateFormat},允许格式化(即日期和文本),解析(文本&rarr;日期)和规范化。
 * 日期表示为<code> Date </code>对象或自1970年1月1日,00:00:00 GMT以来的毫秒数。
 * 
 *  <p> {@ code DateFormat}提供了许多类方法,用于根据默认或给定的语言环境和多种格式化样式获取默认的日期/时间格式化程序。
 * 格式样式包括{@link #FULL},{@link #LONG},{@link #MEDIUM}和{@link #SHORT}。在方法描述中提供了使用这些样式的更多细节和示例。
 * 
 * <p> {@ code DateFormat}可帮助您为任何语言区域设置格式并解析日期。您的代码可以完全独立于月,星期几,或甚至日历格式的地区惯例：月球与太阳。
 * 
 *  <p>要格式化当前区域设置的日期,请使用以下静态工厂方法之一：
 * <blockquote>
 *  <pre> {@ code myString = DateFormat.getDateInstance()。format(myDate); } </pre>
 * </blockquote>
 *  <p>如果要格式化多个日期,则更有效的方法是获取格式并多次使用,以便系统不必多次获取有关本地语言和国家/地区惯例的信息。
 * <blockquote>
 *  <pre> {@ code DateFormat df = DateFormat.getDateInstance(); for(int i = 0; i <myDate.length; ++ i){output.println(df.format(myDate [i])+";"); }} </pre>
 * 。
 * </blockquote>
 *  <p>要格式化不同区域设置的日期,请在调用{@link #getDateInstance(int,Locale)getDateInstance()}时指定。
 * <blockquote>
 *  <pre> {@ code DateFormat df = DateFormat.getDateInstance(DateFormat.LONG,Locale.FRANCE); } </pre>
 * </blockquote>
 *  <p>您可以使用DateFormat来解析。
 * <blockquote>
 *  <pre> {@ code myDate = df.parse(myString); } </pre>
 * </blockquote>
 * <p>使用{@code getDateInstance}获取该国家/地区的正常日期格式。还有其他静态工厂方法可用。使用{@code getTimeInstance}获取该国家/地区的时间格式。
 * 使用{@code getDateTimeInstance}获取日期和时间格式。
 * 您可以向这些工厂方法传递不同的选项来控制结果的长度;从{@link #SHORT}到{@link #MEDIUM}到{@link #LONG}到{@link #FULL}。
 * 确切的结果取决于区域设置,但通常：<ul> <li> {@ link #SHORT}完全是数字,例如{@code 12.13.52}或{@code 3:30 pm} <li> {@ link #MEDIUM}
 * 更长,例如{@code Jan 12,1952} <li> {@ link #LONG}的时间更长,例如{@code 1952年1月12日}或{@code 3:30:32 pm} <li > {@ link #FULL}
 * 完全指定,例如{@code 1952年4月12日(星期二)或者美国太平洋时间下午3:30:42)。
 * 您可以向这些工厂方法传递不同的选项来控制结果的长度;从{@link #SHORT}到{@link #MEDIUM}到{@link #LONG}到{@link #FULL}。
 * </ul>
 * 
 *  <p>您也可以根据需要在格式上设置时区。
 * 如果您想要更多地控制格式或解析(或者希望给予用户更多的控制权),您可以尝试将从工厂方法获得的{@code DateFormat}转换为{@link SimpleDateFormat}。
 * 这将为大多数国家工作;只要记住把它放在一个{@code try}块,以防你遇到一个不寻常的。
 * 
 *  <p>您还可以使用{@link ParsePosition}和{@link FieldPosition}的解析和格式方法形式,让您可以<ul> <li>通过字符串逐段解析。
 *  <li>对齐任何特定字段,或者找到它在屏幕上的选择位置。
 * </ul>
 * 
 * <h3> <a name="synchronization">同步</a> </h3>
 * 
 * <p>
 *  日期格式不同步。建议为每个线程创建单独的格式实例。如果多个线程并发访问格式,则必须在外部同步。
 * 
 * 
 * @see          Format
 * @see          NumberFormat
 * @see          SimpleDateFormat
 * @see          java.util.Calendar
 * @see          java.util.GregorianCalendar
 * @see          java.util.TimeZone
 * @author       Mark Davis, Chen-Lieh Huang, Alan Liu
 */
public abstract class DateFormat extends Format {

    /**
     * The {@link Calendar} instance used for calculating the date-time fields
     * and the instant of time. This field is used for both formatting and
     * parsing.
     *
     * <p>Subclasses should initialize this field to a {@link Calendar}
     * appropriate for the {@link Locale} associated with this
     * <code>DateFormat</code>.
     * <p>
     *  用于计算日期时间字段和时间点的{@link Calendar}实例。此字段用于格式化和解析。
     * 
     *  <p>子类别应将此字段初始化为适合与此<code> DateFormat </code>关联的{@link Locale}的{@link Calendar}。
     * 
     * 
     * @serial
     */
    protected Calendar calendar;

    /**
     * The number formatter that <code>DateFormat</code> uses to format numbers
     * in dates and times.  Subclasses should initialize this to a number format
     * appropriate for the locale associated with this <code>DateFormat</code>.
     * <p>
     *  <code> DateFormat </code>用于格式化日期和时间中的数字的数字格式化程序。
     * 子类应该将此初始化为适合与此<code> DateFormat </code>关联的区域设置的数字格式。
     * 
     * 
     * @serial
     */
    protected NumberFormat numberFormat;

    /**
     * Useful constant for ERA field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  ERA场对准的有用常数。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int ERA_FIELD = 0;
    /**
     * Useful constant for YEAR field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  YEAR字段对齐的常用常数。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int YEAR_FIELD = 1;
    /**
     * Useful constant for MONTH field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  MONTH字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int MONTH_FIELD = 2;
    /**
     * Useful constant for DATE field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  DATE字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int DATE_FIELD = 3;
    /**
     * Useful constant for one-based HOUR_OF_DAY field alignment.
     * Used in FieldPosition of date/time formatting.
     * HOUR_OF_DAY1_FIELD is used for the one-based 24-hour clock.
     * For example, 23:59 + 01:00 results in 24:59.
     * <p>
     *  基于一个HOUR_OF_DAY字段对齐的常用常量。用于日期/时间格式的FieldPosition。 HOUR_OF_DAY1_FIELD用于基于1的24小时制时钟。
     * 例如,23:59 + 01:00导致24:59。
     * 
     */
    public final static int HOUR_OF_DAY1_FIELD = 4;
    /**
     * Useful constant for zero-based HOUR_OF_DAY field alignment.
     * Used in FieldPosition of date/time formatting.
     * HOUR_OF_DAY0_FIELD is used for the zero-based 24-hour clock.
     * For example, 23:59 + 01:00 results in 00:59.
     * <p>
     * 对于基于零的HOUR_OF_DAY字段对齐有用的常量。用于日期/时间格式的FieldPosition。 HOUR_OF_DAY0_FIELD用于基于零的24小时制时钟。
     * 例如,23:59 + 01:00导致00:59。
     * 
     */
    public final static int HOUR_OF_DAY0_FIELD = 5;
    /**
     * Useful constant for MINUTE field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  MINUTE字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int MINUTE_FIELD = 6;
    /**
     * Useful constant for SECOND field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  SECOND字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int SECOND_FIELD = 7;
    /**
     * Useful constant for MILLISECOND field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  MILLISECOND字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int MILLISECOND_FIELD = 8;
    /**
     * Useful constant for DAY_OF_WEEK field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  DAY_OF_WEEK字段对齐的常用常数。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int DAY_OF_WEEK_FIELD = 9;
    /**
     * Useful constant for DAY_OF_YEAR field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  DAY_OF_YEAR字段对齐的常用常数。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int DAY_OF_YEAR_FIELD = 10;
    /**
     * Useful constant for DAY_OF_WEEK_IN_MONTH field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  DAY_OF_WEEK_IN_MONTH字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int DAY_OF_WEEK_IN_MONTH_FIELD = 11;
    /**
     * Useful constant for WEEK_OF_YEAR field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  WEEK_OF_YEAR字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int WEEK_OF_YEAR_FIELD = 12;
    /**
     * Useful constant for WEEK_OF_MONTH field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  WEEK_OF_MONTH字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int WEEK_OF_MONTH_FIELD = 13;
    /**
     * Useful constant for AM_PM field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     *  AM_PM字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int AM_PM_FIELD = 14;
    /**
     * Useful constant for one-based HOUR field alignment.
     * Used in FieldPosition of date/time formatting.
     * HOUR1_FIELD is used for the one-based 12-hour clock.
     * For example, 11:30 PM + 1 hour results in 12:30 AM.
     * <p>
     *  对于基于一个的HOUR场对准的有用常数。用于日期/时间格式的FieldPosition。 HOUR1_FIELD用于基于1的12小时时钟。例如,11:30 PM + 1小时导致12:30 AM。
     * 
     */
    public final static int HOUR1_FIELD = 15;
    /**
     * Useful constant for zero-based HOUR field alignment.
     * Used in FieldPosition of date/time formatting.
     * HOUR0_FIELD is used for the zero-based 12-hour clock.
     * For example, 11:30 PM + 1 hour results in 00:30 AM.
     * <p>
     *  对于基于零的HOUR场对准有用的常数。用于日期/时间格式的FieldPosition。 HOUR0_FIELD用于基于零的12小时时钟。例如,11:30 PM + 1小时的结果为00:30 AM。
     * 
     */
    public final static int HOUR0_FIELD = 16;
    /**
     * Useful constant for TIMEZONE field alignment.
     * Used in FieldPosition of date/time formatting.
     * <p>
     * TIMEZONE字段对齐的常用常量。用于日期/时间格式的FieldPosition。
     * 
     */
    public final static int TIMEZONE_FIELD = 17;

    // Proclaim serial compatibility with 1.1 FCS
    private static final long serialVersionUID = 7218322306649953788L;

    /**
     * Overrides Format.
     * Formats a time object into a time string. Examples of time objects
     * are a time value expressed in milliseconds and a Date object.
     * <p>
     *  覆盖格式。将时间对象格式化为时间字符串。时间对象的示例是以毫秒表示的时间值和Date对象。
     * 
     * 
     * @param obj must be a Number or a Date.
     * @param toAppendTo the string buffer for the returning time string.
     * @return the string buffer passed in as toAppendTo, with formatted text appended.
     * @param fieldPosition keeps track of the position of the field
     * within the returned string.
     * On input: an alignment field,
     * if desired. On output: the offsets of the alignment field. For
     * example, given a time text "1996.07.10 AD at 15:08:56 PDT",
     * if the given fieldPosition is DateFormat.YEAR_FIELD, the
     * begin index and end index of fieldPosition will be set to
     * 0 and 4, respectively.
     * Notice that if the same time field appears
     * more than once in a pattern, the fieldPosition will be set for the first
     * occurrence of that time field. For instance, formatting a Date to
     * the time string "1 PM PDT (Pacific Daylight Time)" using the pattern
     * "h a z (zzzz)" and the alignment field DateFormat.TIMEZONE_FIELD,
     * the begin index and end index of fieldPosition will be set to
     * 5 and 8, respectively, for the first occurrence of the timezone
     * pattern character 'z'.
     * @see java.text.Format
     */
    public final StringBuffer format(Object obj, StringBuffer toAppendTo,
                                     FieldPosition fieldPosition)
    {
        if (obj instanceof Date)
            return format( (Date)obj, toAppendTo, fieldPosition );
        else if (obj instanceof Number)
            return format( new Date(((Number)obj).longValue()),
                          toAppendTo, fieldPosition );
        else
            throw new IllegalArgumentException("Cannot format given Object as a Date");
    }

    /**
     * Formats a Date into a date/time string.
     * <p>
     *  将日期格式化为日期/时间字符串。
     * 
     * 
     * @param date a Date to be formatted into a date/time string.
     * @param toAppendTo the string buffer for the returning date/time string.
     * @param fieldPosition keeps track of the position of the field
     * within the returned string.
     * On input: an alignment field,
     * if desired. On output: the offsets of the alignment field. For
     * example, given a time text "1996.07.10 AD at 15:08:56 PDT",
     * if the given fieldPosition is DateFormat.YEAR_FIELD, the
     * begin index and end index of fieldPosition will be set to
     * 0 and 4, respectively.
     * Notice that if the same time field appears
     * more than once in a pattern, the fieldPosition will be set for the first
     * occurrence of that time field. For instance, formatting a Date to
     * the time string "1 PM PDT (Pacific Daylight Time)" using the pattern
     * "h a z (zzzz)" and the alignment field DateFormat.TIMEZONE_FIELD,
     * the begin index and end index of fieldPosition will be set to
     * 5 and 8, respectively, for the first occurrence of the timezone
     * pattern character 'z'.
     * @return the string buffer passed in as toAppendTo, with formatted text appended.
     */
    public abstract StringBuffer format(Date date, StringBuffer toAppendTo,
                                        FieldPosition fieldPosition);

    /**
     * Formats a Date into a date/time string.
     * <p>
     *  将日期格式化为日期/时间字符串。
     * 
     * 
     * @param date the time value to be formatted into a time string.
     * @return the formatted time string.
     */
    public final String format(Date date)
    {
        return format(date, new StringBuffer(),
                      DontCareFieldPosition.INSTANCE).toString();
    }

    /**
     * Parses text from the beginning of the given string to produce a date.
     * The method may not use the entire text of the given string.
     * <p>
     * See the {@link #parse(String, ParsePosition)} method for more information
     * on date parsing.
     *
     * <p>
     *  从给定字符串的开头解析文本以生成日期。该方法可能不使用给定字符串的整个文本。
     * <p>
     *  有关日期解析的更多信息,请参阅{@link #parse(String,ParsePosition)}方法。
     * 
     * 
     * @param source A <code>String</code> whose beginning should be parsed.
     * @return A <code>Date</code> parsed from the string.
     * @exception ParseException if the beginning of the specified string
     *            cannot be parsed.
     */
    public Date parse(String source) throws ParseException
    {
        ParsePosition pos = new ParsePosition(0);
        Date result = parse(source, pos);
        if (pos.index == 0)
            throw new ParseException("Unparseable date: \"" + source + "\"" ,
                pos.errorIndex);
        return result;
    }

    /**
     * Parse a date/time string according to the given parse position.  For
     * example, a time text {@code "07/10/96 4:5 PM, PDT"} will be parsed into a {@code Date}
     * that is equivalent to {@code Date(837039900000L)}.
     *
     * <p> By default, parsing is lenient: If the input is not in the form used
     * by this object's format method but can still be parsed as a date, then
     * the parse succeeds.  Clients may insist on strict adherence to the
     * format by calling {@link #setLenient(boolean) setLenient(false)}.
     *
     * <p>This parsing operation uses the {@link #calendar} to produce
     * a {@code Date}. As a result, the {@code calendar}'s date-time
     * fields and the {@code TimeZone} value may have been
     * overwritten, depending on subclass implementations. Any {@code
     * TimeZone} value that has previously been set by a call to
     * {@link #setTimeZone(java.util.TimeZone) setTimeZone} may need
     * to be restored for further operations.
     *
     * <p>
     *  根据给定的解析位置分析日期/时间字符串。
     * 例如,时间文本{@code"07/10/96 4：5 PM,PDT"}将被解析为{@code Date},相当于{@code Date(837039900000L)}。
     * 
     *  <p>默认情况下,解析是lenient：如果输入不是此对象的格式方法使用的形式,但仍然可以解析为日期,则解析成功。
     * 客户端可以通过调用{@link #setLenient(boolean)setLenient(false)}来坚持严格遵守格式。
     * 
     *  <p>此解析操作使用{@link #calendar}生成{@code Date}。
     * 因此,根据子类实现,{@code calendar}的日期时间字段和{@code TimeZone}值可能已被覆盖。
     * 之前通过调用{@link #setTimeZone(java.util.TimeZone)setTimeZone}设置的任何{@code TimeZone}值可能需要恢复以进行进一步操作。
     * 
     * 
     * @param source  The date/time string to be parsed
     *
     * @param pos   On input, the position at which to start parsing; on
     *              output, the position at which parsing terminated, or the
     *              start position if the parse failed.
     *
     * @return      A {@code Date}, or {@code null} if the input could not be parsed
     */
    public abstract Date parse(String source, ParsePosition pos);

    /**
     * Parses text from a string to produce a <code>Date</code>.
     * <p>
     * The method attempts to parse text starting at the index given by
     * <code>pos</code>.
     * If parsing succeeds, then the index of <code>pos</code> is updated
     * to the index after the last character used (parsing does not necessarily
     * use all characters up to the end of the string), and the parsed
     * date is returned. The updated <code>pos</code> can be used to
     * indicate the starting point for the next call to this method.
     * If an error occurs, then the index of <code>pos</code> is not
     * changed, the error index of <code>pos</code> is set to the index of
     * the character where the error occurred, and null is returned.
     * <p>
     * See the {@link #parse(String, ParsePosition)} method for more information
     * on date parsing.
     *
     * <p>
     *  从字符串解析文本以生成<code> Date </code>。
     * <p>
     * 该方法尝试解析从<code> pos </code>给出的索引开始的文本。
     * 如果解析成功,则将<code> pos </code>的索引更新为使用最后一个字符后的索引(解析不一定使用直到字符串结尾的所有字符),并返回解析的日期。
     * 更新的<code> pos </code>可用于指示下一次调用此方法的起点。
     * 如果发生错误,则<code> pos </code>的索引不改变,<code> pos </code>的错误索引设置为发生错误的字符的索引,返回null 。
     * <p>
     *  有关日期解析的更多信息,请参阅{@link #parse(String,ParsePosition)}方法。
     * 
     * 
     * @param source A <code>String</code>, part of which should be parsed.
     * @param pos A <code>ParsePosition</code> object with index and error
     *            index information as described above.
     * @return A <code>Date</code> parsed from the string. In case of
     *         error, returns null.
     * @exception NullPointerException if <code>pos</code> is null.
     */
    public Object parseObject(String source, ParsePosition pos) {
        return parse(source, pos);
    }

    /**
     * Constant for full style pattern.
     * <p>
     *  全风格模式的常量。
     * 
     */
    public static final int FULL = 0;
    /**
     * Constant for long style pattern.
     * <p>
     *  长风格图案的常数。
     * 
     */
    public static final int LONG = 1;
    /**
     * Constant for medium style pattern.
     * <p>
     *  中等风格图案的常数。
     * 
     */
    public static final int MEDIUM = 2;
    /**
     * Constant for short style pattern.
     * <p>
     *  短样式图案的常数。
     * 
     */
    public static final int SHORT = 3;
    /**
     * Constant for default style pattern.  Its value is MEDIUM.
     * <p>
     *  默认样式模式的常量。其值为MEDIUM。
     * 
     */
    public static final int DEFAULT = MEDIUM;

    /**
     * Gets the time formatter with the default formatting style
     * for the default {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getTimeInstance(int, Locale) getTimeInstance(DEFAULT,
     *     Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的默认格式化时间格式化程序。
     *  <p>这相当于调用{@link #getTimeInstance(int,Locale)getTimeInstance(DEFAULT,Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的默认格式化时间格式化程序。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @return a time formatter.
     */
    public final static DateFormat getTimeInstance()
    {
        return get(DEFAULT, 0, 1, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets the time formatter with the given formatting style
     * for the default {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getTimeInstance(int, Locale) getTimeInstance(style,
     *     Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置的格式化时间格式。
     *  <p>这相当于调用{@link #getTimeInstance(int,Locale)getTimeInstance(style,Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置的格式化时间格式。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @param style the given formatting style. For example,
     * SHORT for "h:mm a" in the US locale.
     * @return a time formatter.
     */
    public final static DateFormat getTimeInstance(int style)
    {
        return get(style, 0, 1, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets the time formatter with the given formatting style
     * for the given locale.
     * <p>
     * 获取给定语言环境的给定格式化样式的时间格式化程序。
     * 
     * 
     * @param style the given formatting style. For example,
     * SHORT for "h:mm a" in the US locale.
     * @param aLocale the given locale.
     * @return a time formatter.
     */
    public final static DateFormat getTimeInstance(int style,
                                                 Locale aLocale)
    {
        return get(style, 0, 1, aLocale);
    }

    /**
     * Gets the date formatter with the default formatting style
     * for the default {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getDateInstance(int, Locale) getDateInstance(DEFAULT,
     *     Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     *  获取默认格式为{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的日期格式化程序。
     *  <p>这相当于调用{@link #getDateInstance(int,Locale)getDateInstance(DEFAULT,Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  获取默认格式为{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的日期格式化程序。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @return a date formatter.
     */
    public final static DateFormat getDateInstance()
    {
        return get(0, DEFAULT, 2, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets the date formatter with the given formatting style
     * for the default {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getDateInstance(int, Locale) getDateInstance(style,
     *     Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置的格式化日期格式。
     *  <p>这相当于调用{@link #getDateInstance(int,Locale)getDateInstance(style,Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}区域设置的格式化日期格式。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @param style the given formatting style. For example,
     * SHORT for "M/d/yy" in the US locale.
     * @return a date formatter.
     */
    public final static DateFormat getDateInstance(int style)
    {
        return get(0, style, 2, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets the date formatter with the given formatting style
     * for the given locale.
     * <p>
     *  获取给定语言环境的给定格式化样式的日期格式化程序。
     * 
     * 
     * @param style the given formatting style. For example,
     * SHORT for "M/d/yy" in the US locale.
     * @param aLocale the given locale.
     * @return a date formatter.
     */
    public final static DateFormat getDateInstance(int style,
                                                 Locale aLocale)
    {
        return get(0, style, 2, aLocale);
    }

    /**
     * Gets the date/time formatter with the default formatting style
     * for the default {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getDateTimeInstance(int, int, Locale) getDateTimeInstance(DEFAULT,
     *     DEFAULT, Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     *  获取默认格式为{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的日期/时间格式器。
     *  <p>这相当于调用{@link #getDateTimeInstance(int,int,Locale)getDateTimeInstance(DEFAULT,DEFAULT,Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  获取默认格式为{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的日期/时间格式器。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @return a date/time formatter.
     */
    public final static DateFormat getDateTimeInstance()
    {
        return get(DEFAULT, DEFAULT, 3, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets the date/time formatter with the given date and time
     * formatting styles for the default {@link java.util.Locale.Category#FORMAT FORMAT} locale.
     * <p>This is equivalent to calling
     * {@link #getDateTimeInstance(int, int, Locale) getDateTimeInstance(dateStyle,
     *     timeStyle, Locale.getDefault(Locale.Category.FORMAT))}.
     * <p>
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的指定日期和时间格式化样式的日期/时间格式器。
     *  <p>这相当于调用{@link #getDateTimeInstance(int,int,Locale)getDateTimeInstance(dateStyle,timeStyle,Locale.getDefault(Locale.Category.FORMAT))}
     * 。
     *  获取默认{@link java.util.Locale.Category#FORMAT FORMAT}语言环境的指定日期和时间格式化样式的日期/时间格式器。
     * 
     * 
     * @see java.util.Locale#getDefault(java.util.Locale.Category)
     * @see java.util.Locale.Category#FORMAT
     * @param dateStyle the given date formatting style. For example,
     * SHORT for "M/d/yy" in the US locale.
     * @param timeStyle the given time formatting style. For example,
     * SHORT for "h:mm a" in the US locale.
     * @return a date/time formatter.
     */
    public final static DateFormat getDateTimeInstance(int dateStyle,
                                                       int timeStyle)
    {
        return get(timeStyle, dateStyle, 3, Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Gets the date/time formatter with the given formatting styles
     * for the given locale.
     * <p>
     *  获取给定语言环境的给定格式化样式的日期/时间格式器。
     * 
     * 
     * @param dateStyle the given date formatting style.
     * @param timeStyle the given time formatting style.
     * @param aLocale the given locale.
     * @return a date/time formatter.
     */
    public final static DateFormat
        getDateTimeInstance(int dateStyle, int timeStyle, Locale aLocale)
    {
        return get(timeStyle, dateStyle, 3, aLocale);
    }

    /**
     * Get a default date/time formatter that uses the SHORT style for both the
     * date and the time.
     *
     * <p>
     *  获取使用SHORT样式的日期和时间的默认日期/时间格式化程序。
     * 
     * 
     * @return a date/time formatter
     */
    public final static DateFormat getInstance() {
        return getDateTimeInstance(SHORT, SHORT);
    }

    /**
     * Returns an array of all locales for which the
     * <code>get*Instance</code> methods of this class can return
     * localized instances.
     * The returned array represents the union of locales supported by the Java
     * runtime and by installed
     * {@link java.text.spi.DateFormatProvider DateFormatProvider} implementations.
     * It must contain at least a <code>Locale</code> instance equal to
     * {@link java.util.Locale#US Locale.US}.
     *
     * <p>
     * 返回所有语言环境的数组,其中此类的<code> get * Instance </code>方法可以返回本地化实例。
     * 返回的数组表示Java运行时和安装的{@link java.text.spi.DateFormatProvider DateFormatProvider}实现支持的语言环境的联合。
     * 它必须至少包含等于{@link java.util.Locale#US Locale.US}的<code> Locale </code>实例。
     * 
     * 
     * @return An array of locales for which localized
     *         <code>DateFormat</code> instances are available.
     */
    public static Locale[] getAvailableLocales()
    {
        LocaleServiceProviderPool pool =
            LocaleServiceProviderPool.getPool(DateFormatProvider.class);
        return pool.getAvailableLocales();
    }

    /**
     * Set the calendar to be used by this date format.  Initially, the default
     * calendar for the specified or default locale is used.
     *
     * <p>Any {@link java.util.TimeZone TimeZone} and {@linkplain
     * #isLenient() leniency} values that have previously been set are
     * overwritten by {@code newCalendar}'s values.
     *
     * <p>
     *  设置此日期格式要使用的日历。最初,将使用指定或默认语言环境的默认日历。
     * 
     *  <p>之前设置的任何{@link java.util.TimeZone TimeZone}和{@linkplain #isLenient()leniency}值都会被{@code newCalendar}
     * 的值覆盖。
     * 
     * 
     * @param newCalendar the new {@code Calendar} to be used by the date format
     */
    public void setCalendar(Calendar newCalendar)
    {
        this.calendar = newCalendar;
    }

    /**
     * Gets the calendar associated with this date/time formatter.
     *
     * <p>
     *  获取与此日期/时间格式化程序相关联的日历。
     * 
     * 
     * @return the calendar associated with this date/time formatter.
     */
    public Calendar getCalendar()
    {
        return calendar;
    }

    /**
     * Allows you to set the number formatter.
     * <p>
     *  允许您设置数字格式化程序。
     * 
     * 
     * @param newNumberFormat the given new NumberFormat.
     */
    public void setNumberFormat(NumberFormat newNumberFormat)
    {
        this.numberFormat = newNumberFormat;
    }

    /**
     * Gets the number formatter which this date/time formatter uses to
     * format and parse a time.
     * <p>
     *  获取此日期/时间格式化程序用于格式化和解析时间的数字格式化程序。
     * 
     * 
     * @return the number formatter which this date/time formatter uses.
     */
    public NumberFormat getNumberFormat()
    {
        return numberFormat;
    }

    /**
     * Sets the time zone for the calendar of this {@code DateFormat} object.
     * This method is equivalent to the following call.
     * <blockquote><pre>{@code
     * getCalendar().setTimeZone(zone)
     * }</pre></blockquote>
     *
     * <p>The {@code TimeZone} set by this method is overwritten by a
     * {@link #setCalendar(java.util.Calendar) setCalendar} call.
     *
     * <p>The {@code TimeZone} set by this method may be overwritten as
     * a result of a call to the parse method.
     *
     * <p>
     *  设置此{@code DateFormat}对象的日历的时区。此方法等效于以下调用。 <blockquote> <pre> {@ code getCalendar()。
     * setTimeZone(zone)} </pre> </blockquote>。
     * 
     *  <p>由此方法设置的{@code TimeZone}将被{@link #setCalendar(java.util.Calendar)setCalendar}调用覆盖。
     * 
     *  <p>由于调用parse方法,此方法设置的{@code TimeZone}可能会被覆盖。
     * 
     * 
     * @param zone the given new time zone.
     */
    public void setTimeZone(TimeZone zone)
    {
        calendar.setTimeZone(zone);
    }

    /**
     * Gets the time zone.
     * This method is equivalent to the following call.
     * <blockquote><pre>{@code
     * getCalendar().getTimeZone()
     * }</pre></blockquote>
     *
     * <p>
     *  获取时区。此方法等效于以下调用。 <blockquote> <pre> {@ code getCalendar()。getTimeZone()} </pre> </blockquote>
     * 
     * 
     * @return the time zone associated with the calendar of DateFormat.
     */
    public TimeZone getTimeZone()
    {
        return calendar.getTimeZone();
    }

    /**
     * Specify whether or not date/time parsing is to be lenient.  With
     * lenient parsing, the parser may use heuristics to interpret inputs that
     * do not precisely match this object's format.  With strict parsing,
     * inputs must match this object's format.
     *
     * <p>This method is equivalent to the following call.
     * <blockquote><pre>{@code
     * getCalendar().setLenient(lenient)
     * }</pre></blockquote>
     *
     * <p>This leniency value is overwritten by a call to {@link
     * #setCalendar(java.util.Calendar) setCalendar()}.
     *
     * <p>
     * 指定日期/时间解析是否宽松。使用宽松解析,解析器可以使用启发法来解释不精确匹配该对象格式的输入。通过严格解析,输入必须匹配此对象的格式。
     * 
     *  <p>此方法相当于以下调用。 <blockquote> <pre> {@ code getCalendar()。setLenient(lenient)} </pre> </blockquote>
     * 
     *  <p>此宽松价值被{@link #setCalendar(java.util.Calendar)setCalendar()}的调用覆盖。
     * 
     * 
     * @param lenient when {@code true}, parsing is lenient
     * @see java.util.Calendar#setLenient(boolean)
     */
    public void setLenient(boolean lenient)
    {
        calendar.setLenient(lenient);
    }

    /**
     * Tell whether date/time parsing is to be lenient.
     * This method is equivalent to the following call.
     * <blockquote><pre>{@code
     * getCalendar().isLenient()
     * }</pre></blockquote>
     *
     * <p>
     *  告诉日期/时间解析是否宽松。此方法等效于以下调用。 <blockquote> <pre> {@ code getCalendar()。
     * isLenient()} </pre> </blockquote>。
     * 
     * 
     * @return {@code true} if the {@link #calendar} is lenient;
     *         {@code false} otherwise.
     * @see java.util.Calendar#isLenient()
     */
    public boolean isLenient()
    {
        return calendar.isLenient();
    }

    /**
     * Overrides hashCode
     * <p>
     *  覆盖hashCode
     * 
     */
    public int hashCode() {
        return numberFormat.hashCode();
        // just enough fields for a reasonable distribution
    }

    /**
     * Overrides equals
     * <p>
     *  覆盖equals
     * 
     */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DateFormat other = (DateFormat) obj;
        return (// calendar.equivalentTo(other.calendar) // THIS API DOESN'T EXIST YET!
                calendar.getFirstDayOfWeek() == other.calendar.getFirstDayOfWeek() &&
                calendar.getMinimalDaysInFirstWeek() == other.calendar.getMinimalDaysInFirstWeek() &&
                calendar.isLenient() == other.calendar.isLenient() &&
                calendar.getTimeZone().equals(other.calendar.getTimeZone()) &&
                numberFormat.equals(other.numberFormat));
    }

    /**
     * Overrides Cloneable
     * <p>
     *  覆盖可克隆
     * 
     */
    public Object clone()
    {
        DateFormat other = (DateFormat) super.clone();
        other.calendar = (Calendar) calendar.clone();
        other.numberFormat = (NumberFormat) numberFormat.clone();
        return other;
    }

    /**
     * Creates a DateFormat with the given time and/or date style in the given
     * locale.
     * <p>
     *  在给定的语言环境中创建具有给定时间和/或日期样式的DateFormat。
     * 
     * 
     * @param timeStyle a value from 0 to 3 indicating the time format,
     * ignored if flags is 2
     * @param dateStyle a value from 0 to 3 indicating the time format,
     * ignored if flags is 1
     * @param flags either 1 for a time format, 2 for a date format,
     * or 3 for a date/time format
     * @param loc the locale for the format
     */
    private static DateFormat get(int timeStyle, int dateStyle,
                                  int flags, Locale loc) {
        if ((flags & 1) != 0) {
            if (timeStyle < 0 || timeStyle > 3) {
                throw new IllegalArgumentException("Illegal time style " + timeStyle);
            }
        } else {
            timeStyle = -1;
        }
        if ((flags & 2) != 0) {
            if (dateStyle < 0 || dateStyle > 3) {
                throw new IllegalArgumentException("Illegal date style " + dateStyle);
            }
        } else {
            dateStyle = -1;
        }

        LocaleProviderAdapter adapter = LocaleProviderAdapter.getAdapter(DateFormatProvider.class, loc);
        DateFormat dateFormat = get(adapter, timeStyle, dateStyle, loc);
        if (dateFormat == null) {
            dateFormat = get(LocaleProviderAdapter.forJRE(), timeStyle, dateStyle, loc);
        }
        return dateFormat;
    }

    private static DateFormat get(LocaleProviderAdapter adapter, int timeStyle, int dateStyle, Locale loc) {
        DateFormatProvider provider = adapter.getDateFormatProvider();
        DateFormat dateFormat;
        if (timeStyle == -1) {
            dateFormat = provider.getDateInstance(dateStyle, loc);
        } else {
            if (dateStyle == -1) {
                dateFormat = provider.getTimeInstance(timeStyle, loc);
            } else {
                dateFormat = provider.getDateTimeInstance(dateStyle, timeStyle, loc);
            }
        }
        return dateFormat;
    }

    /**
     * Create a new date format.
     * <p>
     *  创建新的日期格式。
     * 
     */
    protected DateFormat() {}

    /**
     * Defines constants that are used as attribute keys in the
     * <code>AttributedCharacterIterator</code> returned
     * from <code>DateFormat.formatToCharacterIterator</code> and as
     * field identifiers in <code>FieldPosition</code>.
     * <p>
     * The class also provides two methods to map
     * between its constants and the corresponding Calendar constants.
     *
     * <p>
     *  定义在<code> DateFormat.formatToCharacterIterator </code>中返回的<code> AttributedCharacterIterator </code>
     * 中作为属性键使用的常量,以及<code> FieldPosition </code>中的字段标识。
     * <p>
     *  该类还提供了两种方法来映射其常量和相应的日历常量。
     * 
     * 
     * @since 1.4
     * @see java.util.Calendar
     */
    public static class Field extends Format.Field {

        // Proclaim serial compatibility with 1.4 FCS
        private static final long serialVersionUID = 7441350119349544720L;

        // table of all instances in this class, used by readResolve
        private static final Map<String, Field> instanceMap = new HashMap<>(18);
        // Maps from Calendar constant (such as Calendar.ERA) to Field
        // constant (such as Field.ERA).
        private static final Field[] calendarToFieldMapping =
                                             new Field[Calendar.FIELD_COUNT];

        /** Calendar field. */
        private int calendarField;

        /**
         * Returns the <code>Field</code> constant that corresponds to
         * the <code>Calendar</code> constant <code>calendarField</code>.
         * If there is no direct mapping between the <code>Calendar</code>
         * constant and a <code>Field</code>, null is returned.
         *
         * <p>
         *  返回与<code> Calendar </code>常量<code> calendarField </code>对应的<code>字段</code>常量。
         * 如果<code> Calendar </code>常量和<code>字段</code>之间没有直接映射,则返回null。
         * 
         * 
         * @throws IllegalArgumentException if <code>calendarField</code> is
         *         not the value of a <code>Calendar</code> field constant.
         * @param calendarField Calendar field constant
         * @return Field instance representing calendarField.
         * @see java.util.Calendar
         */
        public static Field ofCalendarField(int calendarField) {
            if (calendarField < 0 || calendarField >=
                        calendarToFieldMapping.length) {
                throw new IllegalArgumentException("Unknown Calendar constant "
                                                   + calendarField);
            }
            return calendarToFieldMapping[calendarField];
        }

        /**
         * Creates a <code>Field</code>.
         *
         * <p>
         *  创建<code>字段</code>。
         * 
         * 
         * @param name the name of the <code>Field</code>
         * @param calendarField the <code>Calendar</code> constant this
         *        <code>Field</code> corresponds to; any value, even one
         *        outside the range of legal <code>Calendar</code> values may
         *        be used, but <code>-1</code> should be used for values
         *        that don't correspond to legal <code>Calendar</code> values
         */
        protected Field(String name, int calendarField) {
            super(name);
            this.calendarField = calendarField;
            if (this.getClass() == DateFormat.Field.class) {
                instanceMap.put(name, this);
                if (calendarField >= 0) {
                    // assert(calendarField < Calendar.FIELD_COUNT);
                    calendarToFieldMapping[calendarField] = this;
                }
            }
        }

        /**
         * Returns the <code>Calendar</code> field associated with this
         * attribute. For example, if this represents the hours field of
         * a <code>Calendar</code>, this would return
         * <code>Calendar.HOUR</code>. If there is no corresponding
         * <code>Calendar</code> constant, this will return -1.
         *
         * <p>
         * 返回与此属性关联的<code>日历</code>字段。例如,如果这代表<code> Calendar </code>的hours字段,则会返回<code> Calendar.HOUR </code>。
         * 如果没有相应的<code> Calendar </code>常量,则返回-1。
         * 
         * 
         * @return Calendar constant for this field
         * @see java.util.Calendar
         */
        public int getCalendarField() {
            return calendarField;
        }

        /**
         * Resolves instances being deserialized to the predefined constants.
         *
         * <p>
         *  解析反序列化为预定义常量的实例。
         * 
         * 
         * @throws InvalidObjectException if the constant could not be
         *         resolved.
         * @return resolved DateFormat.Field constant
         */
        @Override
        protected Object readResolve() throws InvalidObjectException {
            if (this.getClass() != DateFormat.Field.class) {
                throw new InvalidObjectException("subclass didn't correctly implement readResolve");
            }

            Object instance = instanceMap.get(getName());
            if (instance != null) {
                return instance;
            } else {
                throw new InvalidObjectException("unknown attribute name");
            }
        }

        //
        // The constants
        //

        /**
         * Constant identifying the era field.
         * <p>
         *  常数识别时代字段。
         * 
         */
        public final static Field ERA = new Field("era", Calendar.ERA);

        /**
         * Constant identifying the year field.
         * <p>
         *  常数标识年份字段。
         * 
         */
        public final static Field YEAR = new Field("year", Calendar.YEAR);

        /**
         * Constant identifying the month field.
         * <p>
         *  常数识别月份字段。
         * 
         */
        public final static Field MONTH = new Field("month", Calendar.MONTH);

        /**
         * Constant identifying the day of month field.
         * <p>
         *  常数识别月份日期字段。
         * 
         */
        public final static Field DAY_OF_MONTH = new
                            Field("day of month", Calendar.DAY_OF_MONTH);

        /**
         * Constant identifying the hour of day field, where the legal values
         * are 1 to 24.
         * <p>
         *  常数识别一天中的小时字段,其中合法值为1到24。
         * 
         */
        public final static Field HOUR_OF_DAY1 = new Field("hour of day 1",-1);

        /**
         * Constant identifying the hour of day field, where the legal values
         * are 0 to 23.
         * <p>
         *  常数识别一天中的小时字段,其中合法值为0到23。
         * 
         */
        public final static Field HOUR_OF_DAY0 = new
               Field("hour of day", Calendar.HOUR_OF_DAY);

        /**
         * Constant identifying the minute field.
         * <p>
         *  恒定识别分钟字段。
         * 
         */
        public final static Field MINUTE =new Field("minute", Calendar.MINUTE);

        /**
         * Constant identifying the second field.
         * <p>
         *  常量标识第二个字段。
         * 
         */
        public final static Field SECOND =new Field("second", Calendar.SECOND);

        /**
         * Constant identifying the millisecond field.
         * <p>
         *  常量识别毫秒字段。
         * 
         */
        public final static Field MILLISECOND = new
                Field("millisecond", Calendar.MILLISECOND);

        /**
         * Constant identifying the day of week field.
         * <p>
         *  常数识别星期几字段。
         * 
         */
        public final static Field DAY_OF_WEEK = new
                Field("day of week", Calendar.DAY_OF_WEEK);

        /**
         * Constant identifying the day of year field.
         * <p>
         *  常数识别年份字段。
         * 
         */
        public final static Field DAY_OF_YEAR = new
                Field("day of year", Calendar.DAY_OF_YEAR);

        /**
         * Constant identifying the day of week field.
         * <p>
         *  常数识别星期几字段。
         * 
         */
        public final static Field DAY_OF_WEEK_IN_MONTH =
                     new Field("day of week in month",
                                            Calendar.DAY_OF_WEEK_IN_MONTH);

        /**
         * Constant identifying the week of year field.
         * <p>
         *  常数识别年份字段的周。
         * 
         */
        public final static Field WEEK_OF_YEAR = new
              Field("week of year", Calendar.WEEK_OF_YEAR);

        /**
         * Constant identifying the week of month field.
         * <p>
         *  常数标识月份字段字段。
         * 
         */
        public final static Field WEEK_OF_MONTH = new
            Field("week of month", Calendar.WEEK_OF_MONTH);

        /**
         * Constant identifying the time of day indicator
         * (e.g. "a.m." or "p.m.") field.
         * <p>
         *  常数识别时间指示器(例如"a.m."或"p.m.")字段。
         * 
         */
        public final static Field AM_PM = new
                            Field("am pm", Calendar.AM_PM);

        /**
         * Constant identifying the hour field, where the legal values are
         * 1 to 12.
         * <p>
         *  常数标识小时字段,其中合法值为1到12。
         * 
         */
        public final static Field HOUR1 = new Field("hour 1", -1);

        /**
         * Constant identifying the hour field, where the legal values are
         * 0 to 11.
         * <p>
         *  常数标识小时字段,其中合法值为0到11。
         * 
         */
        public final static Field HOUR0 = new
                            Field("hour", Calendar.HOUR);

        /**
         * Constant identifying the time zone field.
         * <p>
         *  常数识别时区字段。
         */
        public final static Field TIME_ZONE = new Field("time zone", -1);
    }
}
