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
 *  版权所有
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  *源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明
 * 
 *  *二进制形式的再分发必须在随分发版提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明
 * 
 * *未经特定事先书面许可,JSR-310的名称及其贡献者的名称不得用于支持或宣传衍生自此软件的产品
 * 
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或暗示的担保,包括但不限于适销性和针对特定用途的适用性的默示担保,在任何情况下均不得免版权所有者或贡献者对任何直接,间接,偶发,特殊,惩罚性或后果性损害
 * (包括但不限于替代商品或服务的采购,使用,数据或利润损失;或业务中断)有责任的理论,无论是在合同,严格责任或侵权(包括疏忽或其他方式)以任何方式使用本软件,即使已被告知此类损害的可能性。
 * 
 */
package java.time.format;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParsePosition;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeTextProvider.LocaleStore;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.ValueRange;
import java.time.temporal.WeekFields;
import java.time.zone.ZoneRulesProvider;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleResources;
import sun.util.locale.provider.TimeZoneNameUtility;

/**
 * Builder to create date-time formatters.
 * <p>
 * This allows a {@code DateTimeFormatter} to be created.
 * All date-time formatters are created ultimately using this builder.
 * <p>
 * The basic elements of date-time can all be added:
 * <ul>
 * <li>Value - a numeric value</li>
 * <li>Fraction - a fractional value including the decimal place. Always use this when
 * outputting fractions to ensure that the fraction is parsed correctly</li>
 * <li>Text - the textual equivalent for the value</li>
 * <li>OffsetId/Offset - the {@linkplain ZoneOffset zone offset}</li>
 * <li>ZoneId - the {@linkplain ZoneId time-zone} id</li>
 * <li>ZoneText - the name of the time-zone</li>
 * <li>ChronologyId - the {@linkplain Chronology chronology} id</li>
 * <li>ChronologyText - the name of the chronology</li>
 * <li>Literal - a text literal</li>
 * <li>Nested and Optional - formats can be nested or made optional</li>
 * <li>Other - the printer and parser interfaces can be used to add user supplied formatting</li>
 * </ul>
 * In addition, any of the elements may be decorated by padding, either with spaces or any other character.
 * <p>
 * Finally, a shorthand pattern, mostly compatible with {@code java.text.SimpleDateFormat SimpleDateFormat}
 * can be used, see {@link #appendPattern(String)}.
 * In practice, this simply parses the pattern and calls other methods on the builder.
 *
 * @implSpec
 * This class is a mutable builder intended for use from a single thread.
 *
 * <p>
 *  Builder创建日期时间格式化程序
 * <p>
 * 这允许创建{@code DateTimeFormatter}最终使用此构建器创建所有日期时间格式化程序
 * <p>
 *  日期时间的基本元素都可以添加：
 * <ul>
 * <li>值 - 数字值</li> <li>分数 - 包括小数位数的小数值在输出分数时始终使用此分数以确保分数正确解析</li> <li>文字 - </li> <li>区域标识 -  {@linkplain ZoneId时区}
 * 标识</li> <li>区域标记 -  </li>时区名称</li> <li>时间顺序名称</li> <li>文字文字</li> <li>其他 - 打印机和解析器界面可用于添加用户提供的格式</li>。
 * </ul>
 * 另外,任何元素可以通过填充,空白或任何其他字符来装饰
 * <p>
 *  最后,可以使用大致与{@code javatextSimpleDateFormat SimpleDateFormat}兼容的简写模式,参见{@link #appendPattern(String)}在
 * 实践中,这只是解析模式并调用构建器上的其他方法。
 * 
 *  @implSpec此类是一个可变的构建器,用于从单个线程使用
 * 
 * 
 * @since 1.8
 */
public final class DateTimeFormatterBuilder {

    /**
     * Query for a time-zone that is region-only.
     * <p>
     *  查询仅区域的时区
     * 
     */
    private static final TemporalQuery<ZoneId> QUERY_REGION_ONLY = (temporal) -> {
        ZoneId zone = temporal.query(TemporalQueries.zoneId());
        return (zone != null && zone instanceof ZoneOffset == false ? zone : null);
    };

    /**
     * The currently active builder, used by the outermost builder.
     * <p>
     *  当前活动的构建器,由最外面的构建器使用
     * 
     */
    private DateTimeFormatterBuilder active = this;
    /**
     * The parent builder, null for the outermost builder.
     * <p>
     *  父构建器,对于最外层构建器为null
     * 
     */
    private final DateTimeFormatterBuilder parent;
    /**
     * The list of printers that will be used.
     * <p>
     *  将使用的打印机列表
     * 
     */
    private final List<DateTimePrinterParser> printerParsers = new ArrayList<>();
    /**
     * Whether this builder produces an optional formatter.
     * <p>
     *  此构建器是否生成可选的格式化程序
     * 
     */
    private final boolean optional;
    /**
     * The width to pad the next field to.
     * <p>
     *  填充下一个字段的宽度
     * 
     */
    private int padNextWidth;
    /**
     * The character to pad the next field with.
     * <p>
     *  用于填充下一个字段的字符
     * 
     */
    private char padNextChar;
    /**
     * The index of the last variable width value parser.
     * <p>
     * 最后一个变量宽度值解析器的索引
     * 
     */
    private int valueParserIndex = -1;

    /**
     * Gets the formatting pattern for date and time styles for a locale and chronology.
     * The locale and chronology are used to lookup the locale specific format
     * for the requested dateStyle and/or timeStyle.
     *
     * <p>
     *  获取语​​言环境和年表的日期和时间样式的格式化模式语言环境和年表用于查找所请求的dateStyle和/或timeStyle的语言环境特定格式
     * 
     * 
     * @param dateStyle  the FormatStyle for the date
     * @param timeStyle  the FormatStyle for the time
     * @param chrono  the Chronology, non-null
     * @param locale  the locale, non-null
     * @return the locale and Chronology specific formatting pattern
     * @throws IllegalArgumentException if both dateStyle and timeStyle are null
     */
    public static String getLocalizedDateTimePattern(FormatStyle dateStyle, FormatStyle timeStyle,
            Chronology chrono, Locale locale) {
        Objects.requireNonNull(locale, "locale");
        Objects.requireNonNull(chrono, "chrono");
        if (dateStyle == null && timeStyle == null) {
            throw new IllegalArgumentException("Either dateStyle or timeStyle must be non-null");
        }
        LocaleResources lr = LocaleProviderAdapter.getResourceBundleBased().getLocaleResources(locale);
        String pattern = lr.getJavaTimeDateTimePattern(
                convertStyle(timeStyle), convertStyle(dateStyle), chrono.getCalendarType());
        return pattern;
    }

    /**
     * Converts the given FormatStyle to the java.text.DateFormat style.
     *
     * <p>
     *  将给定的FormatStyle转换为javatextDateFormat样式
     * 
     * 
     * @param style  the FormatStyle style
     * @return the int style, or -1 if style is null, indicating un-required
     */
    private static int convertStyle(FormatStyle style) {
        if (style == null) {
            return -1;
        }
        return style.ordinal();  // indices happen to align
    }

    /**
     * Constructs a new instance of the builder.
     * <p>
     *  构造构建器的新实例
     * 
     */
    public DateTimeFormatterBuilder() {
        super();
        parent = null;
        optional = false;
    }

    /**
     * Constructs a new instance of the builder.
     *
     * <p>
     *  构造构建器的新实例
     * 
     * 
     * @param parent  the parent builder, not null
     * @param optional  whether the formatter is optional, not null
     */
    private DateTimeFormatterBuilder(DateTimeFormatterBuilder parent, boolean optional) {
        super();
        this.parent = parent;
        this.optional = optional;
    }

    //-----------------------------------------------------------------------
    /**
     * Changes the parse style to be case sensitive for the remainder of the formatter.
     * <p>
     * Parsing can be case sensitive or insensitive - by default it is case sensitive.
     * This method allows the case sensitivity setting of parsing to be changed.
     * <p>
     * Calling this method changes the state of the builder such that all
     * subsequent builder method calls will parse text in case sensitive mode.
     * See {@link #parseCaseInsensitive} for the opposite setting.
     * The parse case sensitive/insensitive methods may be called at any point
     * in the builder, thus the parser can swap between case parsing modes
     * multiple times during the parse.
     * <p>
     * Since the default is case sensitive, this method should only be used after
     * a previous call to {@code #parseCaseInsensitive}.
     *
     * <p>
     *  将格式化程序的其余部分的分析样式更改为区分大小写
     * <p>
     *  解析可以区分大小写或不区分大小写 - 默认情况下区分大小写此方法允许更改解析的区分大小写设置
     * <p>
     * 调用此方法将更改构建器的状态,以便所有后续构建器方法调用都将以区分大小写的模式解析文本。对于相反的设置,请参见{@link #parseCaseInsensitive}。
     * 可以在构建器中的任何点调用区分大小写/不区分大小写的方法,因此解析器可以在解析期间多次在大小写解析模式之间交换。
     * <p>
     *  由于默认是区分大小写的,所以这个方法应该只在先前调用{@code #parseCaseInsensitive}
     * 
     * 
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder parseCaseSensitive() {
        appendInternal(SettingsParser.SENSITIVE);
        return this;
    }

    /**
     * Changes the parse style to be case insensitive for the remainder of the formatter.
     * <p>
     * Parsing can be case sensitive or insensitive - by default it is case sensitive.
     * This method allows the case sensitivity setting of parsing to be changed.
     * <p>
     * Calling this method changes the state of the builder such that all
     * subsequent builder method calls will parse text in case insensitive mode.
     * See {@link #parseCaseSensitive()} for the opposite setting.
     * The parse case sensitive/insensitive methods may be called at any point
     * in the builder, thus the parser can swap between case parsing modes
     * multiple times during the parse.
     *
     * <p>
     *  将解析样式更改为格式化程序的其余部分不区分大小写
     * <p>
     *  解析可以区分大小写或不区分大小写 - 默认情况下区分大小写此方法允许更改解析的区分大小写设置
     * <p>
     * 调用此方法将更改构建器的状态,以便所有后续构建器方法调用将以不区分大小写的方式解析文本。
     * 对于相反的设置,请参见{@link #parseCaseSensitive()}解析区分大小写/不区分大小写的方法可以在构建器,因此解析器可以在解析期间多次在大小写解析模式之间交换。
     * 
     * 
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder parseCaseInsensitive() {
        appendInternal(SettingsParser.INSENSITIVE);
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Changes the parse style to be strict for the remainder of the formatter.
     * <p>
     * Parsing can be strict or lenient - by default its strict.
     * This controls the degree of flexibility in matching the text and sign styles.
     * <p>
     * When used, this method changes the parsing to be strict from this point onwards.
     * As strict is the default, this is normally only needed after calling {@link #parseLenient()}.
     * The change will remain in force until the end of the formatter that is eventually
     * constructed or until {@code parseLenient} is called.
     *
     * <p>
     *  将格式化程序的其余部分的解析样式更改为严格
     * <p>
     *  解析可以是严格的或宽松的 - 默认情况下它的strict它控制匹配文本和标志样式的灵活程度
     * <p>
     * 当使用时,此方法从这一点开始将解析更改为严格。由于strict是默认值,因此通常只有在调用{@link #parseLenient()}后才需要。
     * 更改将保持有效,直到格式化程序结束最终构造或直到{@code parseLenient}被调用。
     * 
     * 
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder parseStrict() {
        appendInternal(SettingsParser.STRICT);
        return this;
    }

    /**
     * Changes the parse style to be lenient for the remainder of the formatter.
     * Note that case sensitivity is set separately to this method.
     * <p>
     * Parsing can be strict or lenient - by default its strict.
     * This controls the degree of flexibility in matching the text and sign styles.
     * Applications calling this method should typically also call {@link #parseCaseInsensitive()}.
     * <p>
     * When used, this method changes the parsing to be lenient from this point onwards.
     * The change will remain in force until the end of the formatter that is eventually
     * constructed or until {@code parseStrict} is called.
     *
     * <p>
     *  将格式化程序的其余部分的解析样式更改为宽松请注意,区分大小写与此方法分开设置
     * <p>
     *  解析可以是严格的或宽松的 - 默认情况下它的strict这控制匹配文本和符号样式的灵活程度调用此方法的应用程序通常也应调用{@link #parseCaseInsensitive()}
     * <p>
     * 使用时,此方法从这一点开始将分析更改为放宽更改将保持有效,直到最终构造的格式化程序结束或调用{@code parseStrict}
     * 
     * 
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder parseLenient() {
        appendInternal(SettingsParser.LENIENT);
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends a default value for a field to the formatter for use in parsing.
     * <p>
     * This appends an instruction to the builder to inject a default value
     * into the parsed result. This is especially useful in conjunction with
     * optional parts of the formatter.
     * <p>
     * For example, consider a formatter that parses the year, followed by
     * an optional month, with a further optional day-of-month. Using such a
     * formatter would require the calling code to check whether a full date,
     * year-month or just a year had been parsed. This method can be used to
     * default the month and day-of-month to a sensible value, such as the
     * first of the month, allowing the calling code to always get a date.
     * <p>
     * During formatting, this method has no effect.
     * <p>
     * During parsing, the current state of the parse is inspected.
     * If the specified field has no associated value, because it has not been
     * parsed successfully at that point, then the specified value is injected
     * into the parse result. Injection is immediate, thus the field-value pair
     * will be visible to any subsequent elements in the formatter.
     * As such, this method is normally called at the end of the builder.
     *
     * <p>
     *  将字段的默认值附加到格式化程序以用于解析
     * <p>
     *  这将一个指令附加到构建器以将默认值注入到解析的结果中这与格式器的可选部分一起使用是特别有用的
     * <p>
     * 例如,考虑一个格式化程序,它解析年份,然后是可选月份,以及可选的月份日期使用这样的格式化程序将需要调用代码来检查完整日期,年月或只是一年已被解析此方法可用于将月份和月份默认为一个敏感的值,例如月份的第一
     * 天,允许调用代码总是获取日期。
     * <p>
     *  在格式化期间,此方法无效
     * <p>
     * 在解析期间,将检查解析的当前状态如果指定的字段没有关联值,因为它尚未在该点成功解析,则将指定的值注入解析结果Injection中,因此字段值对将对格式器中的任何后续元素可见。
     * 因此,此方法通常在构建器结尾处调用。
     * 
     * 
     * @param field  the field to default the value of, not null
     * @param value  the value to default the field to
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder parseDefaulting(TemporalField field, long value) {
        Objects.requireNonNull(field, "field");
        appendInternal(new DefaultValueParser(field, value));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends the value of a date-time field to the formatter using a normal
     * output style.
     * <p>
     * The value of the field will be output during a format.
     * If the value cannot be obtained then an exception will be thrown.
     * <p>
     * The value will be printed as per the normal format of an integer value.
     * Only negative numbers will be signed. No padding will be added.
     * <p>
     * The parser for a variable width value such as this normally behaves greedily,
     * requiring one digit, but accepting as many digits as possible.
     * This behavior can be affected by 'adjacent value parsing'.
     * See {@link #appendValue(java.time.temporal.TemporalField, int)} for full details.
     *
     * <p>
     *  使用正常的输出样式将日期时间字段的值附加到格式化程序
     * <p>
     *  字段的值将在格式期间输出如果无法获取该值,则会抛出异常
     * <p>
     *  该值将按照整数值的正常格式打印。只有负数将被签名。将添加填充
     * <p>
     * 这种可变宽度值的解析器通常表现为贪婪,需要一个数字,但接受尽可能多的数字这种行为可能受到"相邻值解析"的影响。
     * 参见{@link #appendValue(javatimetemporalTemporalField,int)}完整细节。
     * 
     * 
     * @param field  the field to append, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendValue(TemporalField field) {
        Objects.requireNonNull(field, "field");
        appendValue(new NumberPrinterParser(field, 1, 19, SignStyle.NORMAL));
        return this;
    }

    /**
     * Appends the value of a date-time field to the formatter using a fixed
     * width, zero-padded approach.
     * <p>
     * The value of the field will be output during a format.
     * If the value cannot be obtained then an exception will be thrown.
     * <p>
     * The value will be zero-padded on the left. If the size of the value
     * means that it cannot be printed within the width then an exception is thrown.
     * If the value of the field is negative then an exception is thrown during formatting.
     * <p>
     * This method supports a special technique of parsing known as 'adjacent value parsing'.
     * This technique solves the problem where a value, variable or fixed width, is followed by one or more
     * fixed length values. The standard parser is greedy, and thus it would normally
     * steal the digits that are needed by the fixed width value parsers that follow the
     * variable width one.
     * <p>
     * No action is required to initiate 'adjacent value parsing'.
     * When a call to {@code appendValue} is made, the builder
     * enters adjacent value parsing setup mode. If the immediately subsequent method
     * call or calls on the same builder are for a fixed width value, then the parser will reserve
     * space so that the fixed width values can be parsed.
     * <p>
     * For example, consider {@code builder.appendValue(YEAR).appendValue(MONTH_OF_YEAR, 2);}
     * The year is a variable width parse of between 1 and 19 digits.
     * The month is a fixed width parse of 2 digits.
     * Because these were appended to the same builder immediately after one another,
     * the year parser will reserve two digits for the month to parse.
     * Thus, the text '201106' will correctly parse to a year of 2011 and a month of 6.
     * Without adjacent value parsing, the year would greedily parse all six digits and leave
     * nothing for the month.
     * <p>
     * Adjacent value parsing applies to each set of fixed width not-negative values in the parser
     * that immediately follow any kind of value, variable or fixed width.
     * Calling any other append method will end the setup of adjacent value parsing.
     * Thus, in the unlikely event that you need to avoid adjacent value parsing behavior,
     * simply add the {@code appendValue} to another {@code DateTimeFormatterBuilder}
     * and add that to this builder.
     * <p>
     * If adjacent parsing is active, then parsing must match exactly the specified
     * number of digits in both strict and lenient modes.
     * In addition, no positive or negative sign is permitted.
     *
     * <p>
     *  使用固定宽度,零填充方法将日期时间字段的值附加到格式化程序
     * <p>
     *  字段的值将在格式期间输出如果无法获取该值,则会抛出异常
     * <p>
     *  该值将在左边填零如果值的大小意味着它不能在宽度内打印,则抛出异常如果字段的值为负,则在格式化期间抛出异常
     * <p>
     * 这种方法支持一种称为"相邻值解析"的特殊解析技术。这种技术解决了一个值,变量或固定宽度后面跟有一个或多个固定长度值的问题。
     * 标准解析器是贪婪的,因此它通常会被窃取固定宽度值解析器所需的数字,跟随变量width。
     * <p>
     *  启动"相邻值解析"不需要任何操作当对{@code appendValue}进行调用时,构建器进入相邻值解析设置模式如果同一构建器上紧接着的后续方法调用或调用是固定宽度值,那么解析器将保留空间,以便可以
     * 解析固定宽度值。
     * <p>
     * 例如,考虑{@code builderappendValue(YEAR)appendValue(MONTH_OF_YEAR,2);}年份是1到19位数字之间的可变宽度解析。
     * 月份是2位数的固定宽度解析因为这些被附加到同一个构建器紧接着,年分析器将保留两个数字用于要解析的月份。
     * 因此,文本'201106'将正确解析为2011年的一个月和6个月没有相邻值解析,年将贪婪地解析所有六个数字并为本月不留任何东西。
     * <p>
     * 相邻值解析适用于解析器中紧跟任何类型的值,变量或固定宽度的每组固定宽度非负值调用任何其他append方法将结束相邻值解析的设置因此,在不太可能发生的情况下需要避免相邻值解析行为,只需将{@code appendValue}
     * 添加到另一个{@code DateTimeFormatterBuilder},并将其添加到此构建器。
     * <p>
     *  如果相邻解析处于活动状态,则解析必须在严格和宽松模式下精确匹配指定的位数。此外,不允许使用正号或负号
     * 
     * 
     * @param field  the field to append, not null
     * @param width  the width of the printed field, from 1 to 19
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if the width is invalid
     */
    public DateTimeFormatterBuilder appendValue(TemporalField field, int width) {
        Objects.requireNonNull(field, "field");
        if (width < 1 || width > 19) {
            throw new IllegalArgumentException("The width must be from 1 to 19 inclusive but was " + width);
        }
        NumberPrinterParser pp = new NumberPrinterParser(field, width, width, SignStyle.NOT_NEGATIVE);
        appendValue(pp);
        return this;
    }

    /**
     * Appends the value of a date-time field to the formatter providing full
     * control over formatting.
     * <p>
     * The value of the field will be output during a format.
     * If the value cannot be obtained then an exception will be thrown.
     * <p>
     * This method provides full control of the numeric formatting, including
     * zero-padding and the positive/negative sign.
     * <p>
     * The parser for a variable width value such as this normally behaves greedily,
     * accepting as many digits as possible.
     * This behavior can be affected by 'adjacent value parsing'.
     * See {@link #appendValue(java.time.temporal.TemporalField, int)} for full details.
     * <p>
     * In strict parsing mode, the minimum number of parsed digits is {@code minWidth}
     * and the maximum is {@code maxWidth}.
     * In lenient parsing mode, the minimum number of parsed digits is one
     * and the maximum is 19 (except as limited by adjacent value parsing).
     * <p>
     * If this method is invoked with equal minimum and maximum widths and a sign style of
     * {@code NOT_NEGATIVE} then it delegates to {@code appendValue(TemporalField,int)}.
     * In this scenario, the formatting and parsing behavior described there occur.
     *
     * <p>
     *  将日期时间字段的值附加到格式化程序,提供对格式化的完全控制
     * <p>
     * 字段的值将在格式期间输出如果无法获取该值,则会抛出异常
     * <p>
     *  此方法提供对数字格式的完全控制,包括零填充和正/负号
     * <p>
     *  对于这样的可变宽度值的解析器通常表现为贪婪,接受尽可能多的数字这种行为可以受到"邻接值解析"的影响参见{@link #appendValue(javatimetemporalTemporalField,int)}
     * 完整的详细信息。
     * <p>
     *  在严格解析模式下,解析数字的最小数目为{@code minWidth},最大值为{@code maxWidth}在宽松解析模式下,解析数字的最小数目为1,最大为19(除非由相邻值解析)
     * <p>
     * 如果调用此方法具有相等的最小和最大宽度以及{@code NOT_NEGATIVE}的符号样式,那么它将委派给{@code appendValue(TemporalField,int)}在这种情况下,出现
     * 的格式化和解析行为。
     * 
     * 
     * @param field  the field to append, not null
     * @param minWidth  the minimum field width of the printed field, from 1 to 19
     * @param maxWidth  the maximum field width of the printed field, from 1 to 19
     * @param signStyle  the positive/negative output style, not null
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if the widths are invalid
     */
    public DateTimeFormatterBuilder appendValue(
            TemporalField field, int minWidth, int maxWidth, SignStyle signStyle) {
        if (minWidth == maxWidth && signStyle == SignStyle.NOT_NEGATIVE) {
            return appendValue(field, maxWidth);
        }
        Objects.requireNonNull(field, "field");
        Objects.requireNonNull(signStyle, "signStyle");
        if (minWidth < 1 || minWidth > 19) {
            throw new IllegalArgumentException("The minimum width must be from 1 to 19 inclusive but was " + minWidth);
        }
        if (maxWidth < 1 || maxWidth > 19) {
            throw new IllegalArgumentException("The maximum width must be from 1 to 19 inclusive but was " + maxWidth);
        }
        if (maxWidth < minWidth) {
            throw new IllegalArgumentException("The maximum width must exceed or equal the minimum width but " +
                    maxWidth + " < " + minWidth);
        }
        NumberPrinterParser pp = new NumberPrinterParser(field, minWidth, maxWidth, signStyle);
        appendValue(pp);
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends the reduced value of a date-time field to the formatter.
     * <p>
     * Since fields such as year vary by chronology, it is recommended to use the
     * {@link #appendValueReduced(TemporalField, int, int, ChronoLocalDate)} date}
     * variant of this method in most cases. This variant is suitable for
     * simple fields or working with only the ISO chronology.
     * <p>
     * For formatting, the {@code width} and {@code maxWidth} are used to
     * determine the number of characters to format.
     * If they are equal then the format is fixed width.
     * If the value of the field is within the range of the {@code baseValue} using
     * {@code width} characters then the reduced value is formatted otherwise the value is
     * truncated to fit {@code maxWidth}.
     * The rightmost characters are output to match the width, left padding with zero.
     * <p>
     * For strict parsing, the number of characters allowed by {@code width} to {@code maxWidth} are parsed.
     * For lenient parsing, the number of characters must be at least 1 and less than 10.
     * If the number of digits parsed is equal to {@code width} and the value is positive,
     * the value of the field is computed to be the first number greater than
     * or equal to the {@code baseValue} with the same least significant characters,
     * otherwise the value parsed is the field value.
     * This allows a reduced value to be entered for values in range of the baseValue
     * and width and absolute values can be entered for values outside the range.
     * <p>
     * For example, a base value of {@code 1980} and a width of {@code 2} will have
     * valid values from {@code 1980} to {@code 2079}.
     * During parsing, the text {@code "12"} will result in the value {@code 2012} as that
     * is the value within the range where the last two characters are "12".
     * By contrast, parsing the text {@code "1915"} will result in the value {@code 1915}.
     *
     * <p>
     *  将日期时间字段的减小的值附加到格式化程序
     * <p>
     *  由于诸如年份的字段根据年表而不同,因此建议在大多数情况下使用此方法的{@link #appendValueReduced(TemporalField,int,int,ChronoLocalDate)}
     *  date}变体此变体适用于简单字段或仅使用ISO年表。
     * <p>
     * 对于格式化,{@code width}和{@code maxWidth}用于确定要格式化的字符数如果它们相等,则格式为fixed width如果字段的值在{@code baseValue}使用{@code width}
     * 字符,然后减小的值被格式化,否则该值被截断以适合{@code maxWidth}最右边的字符被输出以匹配宽度,左边填充为零。
     * <p>
     * 对于严格解析,将解析{@code width}到{@code maxWidth}允许的字符数对于宽松解析,字符数必须至少为1且小于10如果解析的位数等于{ @code width},并且值为正,则该字段
     * 的值计算为大于或等于{@code baseValue}的具有相同最低有效字符的第一个数字,否则值为parsed是字段值这允许可以为baseValue范围内的值输入减小的值,并且可以为该范围之外的值输入宽
     * 度和绝对值。
     * <p>
     * 例如,基本值{@code 1980}和宽度{@code 2}将具有从{@code 1980}到{@code 2079}的有效值。
     * 在解析期间,文本{@code"12"}将导致值{@code 2012},因为它是在最后两个字符为"12"的范围内的值。相比之下,解析文本{@code"1915"}将导致值{@code 1915}。
     * 
     * 
     * @param field  the field to append, not null
     * @param width  the field width of the printed and parsed field, from 1 to 10
     * @param maxWidth  the maximum field width of the printed field, from 1 to 10
     * @param baseValue  the base value of the range of valid values
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if the width or base value is invalid
     */
    public DateTimeFormatterBuilder appendValueReduced(TemporalField field,
            int width, int maxWidth, int baseValue) {
        Objects.requireNonNull(field, "field");
        ReducedPrinterParser pp = new ReducedPrinterParser(field, width, maxWidth, baseValue, null);
        appendValue(pp);
        return this;
    }

    /**
     * Appends the reduced value of a date-time field to the formatter.
     * <p>
     * This is typically used for formatting and parsing a two digit year.
     * <p>
     * The base date is used to calculate the full value during parsing.
     * For example, if the base date is 1950-01-01 then parsed values for
     * a two digit year parse will be in the range 1950-01-01 to 2049-12-31.
     * Only the year would be extracted from the date, thus a base date of
     * 1950-08-25 would also parse to the range 1950-01-01 to 2049-12-31.
     * This behavior is necessary to support fields such as week-based-year
     * or other calendar systems where the parsed value does not align with
     * standard ISO years.
     * <p>
     * The exact behavior is as follows. Parse the full set of fields and
     * determine the effective chronology using the last chronology if
     * it appears more than once. Then convert the base date to the
     * effective chronology. Then extract the specified field from the
     * chronology-specific base date and use it to determine the
     * {@code baseValue} used below.
     * <p>
     * For formatting, the {@code width} and {@code maxWidth} are used to
     * determine the number of characters to format.
     * If they are equal then the format is fixed width.
     * If the value of the field is within the range of the {@code baseValue} using
     * {@code width} characters then the reduced value is formatted otherwise the value is
     * truncated to fit {@code maxWidth}.
     * The rightmost characters are output to match the width, left padding with zero.
     * <p>
     * For strict parsing, the number of characters allowed by {@code width} to {@code maxWidth} are parsed.
     * For lenient parsing, the number of characters must be at least 1 and less than 10.
     * If the number of digits parsed is equal to {@code width} and the value is positive,
     * the value of the field is computed to be the first number greater than
     * or equal to the {@code baseValue} with the same least significant characters,
     * otherwise the value parsed is the field value.
     * This allows a reduced value to be entered for values in range of the baseValue
     * and width and absolute values can be entered for values outside the range.
     * <p>
     * For example, a base value of {@code 1980} and a width of {@code 2} will have
     * valid values from {@code 1980} to {@code 2079}.
     * During parsing, the text {@code "12"} will result in the value {@code 2012} as that
     * is the value within the range where the last two characters are "12".
     * By contrast, parsing the text {@code "1915"} will result in the value {@code 1915}.
     *
     * <p>
     *  将日期时间字段的减小的值附加到格式化程序
     * <p>
     *  这通常用于格式化和解析两位数年份
     * <p>
     * 基本日期用于在解析期间计算完整值例如,如果基准日期为1950-01-01,则两位数年份解析的解析值将在1950-01-01到2049-12-31之间仅从该日期提取年份,因此基准日期1950-08-25也
     * 将解析为1950-01-01至2049-12-31这个行为是必要的,以支持字段,如基于周的,年或其他日历系统,其中解析值与标准ISO年不一致。
     * <p>
     * 确切的行为如下解析全部字段,并且如果出现多次,使用最后一个年表确定有效年表。然后将基本日期转换为有效年表。然后从年表特定基准日期提取指定字段,并使用它确定下面使用的{@code baseValue}
     * <p>
     *  对于格式化,{@code width}和{@code maxWidth}用于确定要格式化的字符数如果它们相等,则格式为fixed width如果字段的值在{@code baseValue}使用{@code width}
     * 字符,然后减小的值被格式化,否则该值被截断以适合{@code maxWidth}最右边的字符被输出以匹配宽度,左边填充为零。
     * <p>
     * 对于严格解析,将解析{@code width}到{@code maxWidth}允许的字符数对于宽松解析,字符数必须至少为1且小于10如果解析的位数等于{ @code width},并且值为正,则该字段
     * 的值计算为大于或等于{@code baseValue}的具有相同最低有效字符的第一个数字,否则值为parsed是字段值这允许可以为baseValue范围内的值输入减小的值,并且可以为该范围之外的值输入宽
     * 度和绝对值。
     * <p>
     * 例如,基本值{@code 1980}和宽度{@code 2}将具有从{@code 1980}到{@code 2079}的有效值。
     * 在解析期间,文本{@code"12"}将导致值{@code 2012},因为它是在最后两个字符为"12"的范围内的值。相比之下,解析文本{@code"1915"}将导致值{@code 1915}。
     * 
     * 
     * @param field  the field to append, not null
     * @param width  the field width of the printed and parsed field, from 1 to 10
     * @param maxWidth  the maximum field width of the printed field, from 1 to 10
     * @param baseDate  the base date used to calculate the base value for the range
     *  of valid values in the parsed chronology, not null
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if the width or base value is invalid
     */
    public DateTimeFormatterBuilder appendValueReduced(
            TemporalField field, int width, int maxWidth, ChronoLocalDate baseDate) {
        Objects.requireNonNull(field, "field");
        Objects.requireNonNull(baseDate, "baseDate");
        ReducedPrinterParser pp = new ReducedPrinterParser(field, width, maxWidth, 0, baseDate);
        appendValue(pp);
        return this;
    }

    /**
     * Appends a fixed or variable width printer-parser handling adjacent value mode.
     * If a PrinterParser is not active then the new PrinterParser becomes
     * the active PrinterParser.
     * Otherwise, the active PrinterParser is modified depending on the new PrinterParser.
     * If the new PrinterParser is fixed width and has sign style {@code NOT_NEGATIVE}
     * then its width is added to the active PP and
     * the new PrinterParser is forced to be fixed width.
     * If the new PrinterParser is variable width, the active PrinterParser is changed
     * to be fixed width and the new PrinterParser becomes the active PP.
     *
     * <p>
     * 附加固定或可变宽度打印机解析器处理相邻值模式如果PrinterParser未处于活动状态,则新的PrinterParser将变为活动的PrinterParser否则,将根据新的PrinterParser
     * 修改活动的PrinterParser如果新的PrinterParser是固定宽度并且具有符号样式{@code NOT_NEGATIVE},则其宽度被添加到活动PP,并且新的PrinterParser被强
     * 制为固定宽度。
     * 如果新的PrinterParser是可变宽度,则活动的PrinterParser被改变为固定宽度,并且新的PrinterParser变为活动PP。
     * 
     * 
     * @param pp  the printer-parser, not null
     * @return this, for chaining, not null
     */
    private DateTimeFormatterBuilder appendValue(NumberPrinterParser pp) {
        if (active.valueParserIndex >= 0) {
            final int activeValueParser = active.valueParserIndex;

            // adjacent parsing mode, update setting in previous parsers
            NumberPrinterParser basePP = (NumberPrinterParser) active.printerParsers.get(activeValueParser);
            if (pp.minWidth == pp.maxWidth && pp.signStyle == SignStyle.NOT_NEGATIVE) {
                // Append the width to the subsequentWidth of the active parser
                basePP = basePP.withSubsequentWidth(pp.maxWidth);
                // Append the new parser as a fixed width
                appendInternal(pp.withFixedWidth());
                // Retain the previous active parser
                active.valueParserIndex = activeValueParser;
            } else {
                // Modify the active parser to be fixed width
                basePP = basePP.withFixedWidth();
                // The new parser becomes the mew active parser
                active.valueParserIndex = appendInternal(pp);
            }
            // Replace the modified parser with the updated one
            active.printerParsers.set(activeValueParser, basePP);
        } else {
            // The new Parser becomes the active parser
            active.valueParserIndex = appendInternal(pp);
        }
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends the fractional value of a date-time field to the formatter.
     * <p>
     * The fractional value of the field will be output including the
     * preceding decimal point. The preceding value is not output.
     * For example, the second-of-minute value of 15 would be output as {@code .25}.
     * <p>
     * The width of the printed fraction can be controlled. Setting the
     * minimum width to zero will cause no output to be generated.
     * The printed fraction will have the minimum width necessary between
     * the minimum and maximum widths - trailing zeroes are omitted.
     * No rounding occurs due to the maximum width - digits are simply dropped.
     * <p>
     * When parsing in strict mode, the number of parsed digits must be between
     * the minimum and maximum width. When parsing in lenient mode, the minimum
     * width is considered to be zero and the maximum is nine.
     * <p>
     * If the value cannot be obtained then an exception will be thrown.
     * If the value is negative an exception will be thrown.
     * If the field does not have a fixed set of valid values then an
     * exception will be thrown.
     * If the field value in the date-time to be printed is invalid it
     * cannot be printed and an exception will be thrown.
     *
     * <p>
     *  将日期时间字段的小数值附加到格式化程序
     * <p>
     * 将输出包含前面小数点的字段的小数值不输出前面的值例如,第二分钟值15将输出为{@code 25}
     * <p>
     *  可以控制打印分数的宽度将最小宽度设置为零将导致不产生输出打印分数将具有最小宽度和最大宽度之间所需的最小宽度 - 省略尾随零不会由于最大宽度 - 数字被简单地丢弃
     * <p>
     *  在严格模式下解析时,解析数字的数量必须在最小和最大宽度之间。在宽松模式下解析时,最小宽度被认为是零,最大为9
     * <p>
     * 如果无法获取值,那么将抛出异常如果值为负,将抛出异常如果字段没有固定的有效值,那么将抛出异常如果日期时间中的字段值为被打印是无效的,它不能被打印,并且将抛出异常
     * 
     * 
     * @param field  the field to append, not null
     * @param minWidth  the minimum width of the field excluding the decimal point, from 0 to 9
     * @param maxWidth  the maximum width of the field excluding the decimal point, from 1 to 9
     * @param decimalPoint  whether to output the localized decimal point symbol
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if the field has a variable set of valid values or
     *  either width is invalid
     */
    public DateTimeFormatterBuilder appendFraction(
            TemporalField field, int minWidth, int maxWidth, boolean decimalPoint) {
        appendInternal(new FractionPrinterParser(field, minWidth, maxWidth, decimalPoint));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends the text of a date-time field to the formatter using the full
     * text style.
     * <p>
     * The text of the field will be output during a format.
     * The value must be within the valid range of the field.
     * If the value cannot be obtained then an exception will be thrown.
     * If the field has no textual representation, then the numeric value will be used.
     * <p>
     * The value will be printed as per the normal format of an integer value.
     * Only negative numbers will be signed. No padding will be added.
     *
     * <p>
     *  使用全文样式将日期时间字段的文本附加到格式化程序
     * <p>
     *  字段的文本将在格式期间输出该值必须在字段的有效范围内如果无法获取该值,则将抛出异常如果字段没有文本表示,则将使用数值
     * <p>
     * 该值将按照整数值的正常格式打印。只有负数将被签名。将添加填充
     * 
     * 
     * @param field  the field to append, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendText(TemporalField field) {
        return appendText(field, TextStyle.FULL);
    }

    /**
     * Appends the text of a date-time field to the formatter.
     * <p>
     * The text of the field will be output during a format.
     * The value must be within the valid range of the field.
     * If the value cannot be obtained then an exception will be thrown.
     * If the field has no textual representation, then the numeric value will be used.
     * <p>
     * The value will be printed as per the normal format of an integer value.
     * Only negative numbers will be signed. No padding will be added.
     *
     * <p>
     *  将日期时间字段的文本附加到格式化程序
     * <p>
     *  字段的文本将在格式期间输出该值必须在字段的有效范围内如果无法获取该值,则将抛出异常如果字段没有文本表示,则将使用数值
     * <p>
     *  该值将按照整数值的正常格式打印。只有负数将被签名。将添加填充
     * 
     * 
     * @param field  the field to append, not null
     * @param textStyle  the text style to use, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendText(TemporalField field, TextStyle textStyle) {
        Objects.requireNonNull(field, "field");
        Objects.requireNonNull(textStyle, "textStyle");
        appendInternal(new TextPrinterParser(field, textStyle, DateTimeTextProvider.getInstance()));
        return this;
    }

    /**
     * Appends the text of a date-time field to the formatter using the specified
     * map to supply the text.
     * <p>
     * The standard text outputting methods use the localized text in the JDK.
     * This method allows that text to be specified directly.
     * The supplied map is not validated by the builder to ensure that formatting or
     * parsing is possible, thus an invalid map may throw an error during later use.
     * <p>
     * Supplying the map of text provides considerable flexibility in formatting and parsing.
     * For example, a legacy application might require or supply the months of the
     * year as "JNY", "FBY", "MCH" etc. These do not match the standard set of text
     * for localized month names. Using this method, a map can be created which
     * defines the connection between each value and the text:
     * <pre>
     * Map&lt;Long, String&gt; map = new HashMap&lt;&gt;();
     * map.put(1, "JNY");
     * map.put(2, "FBY");
     * map.put(3, "MCH");
     * ...
     * builder.appendText(MONTH_OF_YEAR, map);
     * </pre>
     * <p>
     * Other uses might be to output the value with a suffix, such as "1st", "2nd", "3rd",
     * or as Roman numerals "I", "II", "III", "IV".
     * <p>
     * During formatting, the value is obtained and checked that it is in the valid range.
     * If text is not available for the value then it is output as a number.
     * During parsing, the parser will match against the map of text and numeric values.
     *
     * <p>
     *  使用指定的地图将日期时间字段的文本附加到格式化程序以提供文本
     * <p>
     * 标准文本输出方法使用JDK中的本地化文本此方法允许直接指定文本提供的映射不会被构建器验证,以确保格式化或解析是可能的,因此无效映射可能在以后使用期间抛出错误
     * <p>
     *  提供文本地图在格式化和解析方面提供了相当大的灵活性例如,遗留应用程序可能需要或提供"JNY","FBY","MCH"等一年中的月份。
     * 这些不匹配标准的文本集本地化月份名称使用此方法,可以创建一个定义每个值和文本之间的连接的地图：。
     * <pre>
     * 地图&lt; Long,String&gt; map = new HashMap&lt;&gt;(); mapput(1,"JNY"); mapput(2,"FBY"); mapput(3,"MCH")
     * ; builderappendText(MONTH_OF_YEAR,map);。
     * </pre>
     * <p>
     *  其他用途可能是输出具有后缀的值,例如"1st","2nd","3rd"或作为罗马数字"I","II","III","IV"
     * <p>
     *  在格式化期间,获取该值并检查它是否在有效范围内如果文本不可用于该值,那么它将作为数字输出在解析期间,解析器将匹配文本和数值的映射
     * 
     * 
     * @param field  the field to append, not null
     * @param textLookup  the map from the value to the text
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendText(TemporalField field, Map<Long, String> textLookup) {
        Objects.requireNonNull(field, "field");
        Objects.requireNonNull(textLookup, "textLookup");
        Map<Long, String> copy = new LinkedHashMap<>(textLookup);
        Map<TextStyle, Map<Long, String>> map = Collections.singletonMap(TextStyle.FULL, copy);
        final LocaleStore store = new LocaleStore(map);
        DateTimeTextProvider provider = new DateTimeTextProvider() {
            @Override
            public String getText(TemporalField field, long value, TextStyle style, Locale locale) {
                return store.getText(value, style);
            }
            @Override
            public Iterator<Entry<String, Long>> getTextIterator(TemporalField field, TextStyle style, Locale locale) {
                return store.getTextIterator(style);
            }
        };
        appendInternal(new TextPrinterParser(field, TextStyle.FULL, provider));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends an instant using ISO-8601 to the formatter, formatting fractional
     * digits in groups of three.
     * <p>
     * Instants have a fixed output format.
     * They are converted to a date-time with a zone-offset of UTC and formatted
     * using the standard ISO-8601 format.
     * With this method, formatting nano-of-second outputs zero, three, six
     * or nine digits digits as necessary.
     * The localized decimal style is not used.
     * <p>
     * The instant is obtained using {@link ChronoField#INSTANT_SECONDS INSTANT_SECONDS}
     * and optionally (@code NANO_OF_SECOND). The value of {@code INSTANT_SECONDS}
     * may be outside the maximum range of {@code LocalDateTime}.
     * <p>
     * The {@linkplain ResolverStyle resolver style} has no effect on instant parsing.
     * The end-of-day time of '24:00' is handled as midnight at the start of the following day.
     * The leap-second time of '23:59:59' is handled to some degree, see
     * {@link DateTimeFormatter#parsedLeapSecond()} for full details.
     * <p>
     * An alternative to this method is to format/parse the instant as a single
     * epoch-seconds value. That is achieved using {@code appendValue(INSTANT_SECONDS)}.
     *
     * <p>
     *  使用ISO-8601附加立即到格式化程序,格式化小数三位数
     * <p>
     * 实例具有固定的输出格式将它们转换为带UTC区域偏移的日期时间,并使用标准ISO-8601格式进行格式化使用此方法,格式化纳秒输出零,三,六或九位数字根据需要不使用本地化的十进制样式
     * <p>
     *  该时刻是使用{@link ChronoField#INSTANT_SECONDS INSTANT_SECONDS}和可选的(@code NANO_OF_SECOND)获得的。
     * {@code INSTANT_SECONDS}的值可能超出{@code LocalDateTime}的最大范围,。
     * <p>
     * {@linkplain ResolverStyle解析器样式}对即时解析没有影响'24：00'的日终时间被处理为第二天开始时的午夜闰秒时间'23：59：59 '在一定程度上处理,请参阅{@link DateTimeFormatter#parsedLeapSecond()}
     * 了解完整详细信息。
     * <p>
     *  这种方法的一个替代方法是将时间格式化/解析为单个纪元秒值。使用{@code appendValue(INSTANT_SECONDS)}
     * 
     * 
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendInstant() {
        appendInternal(new InstantPrinterParser(-2));
        return this;
    }

    /**
     * Appends an instant using ISO-8601 to the formatter with control over
     * the number of fractional digits.
     * <p>
     * Instants have a fixed output format, although this method provides some
     * control over the fractional digits. They are converted to a date-time
     * with a zone-offset of UTC and printed using the standard ISO-8601 format.
     * The localized decimal style is not used.
     * <p>
     * The {@code fractionalDigits} parameter allows the output of the fractional
     * second to be controlled. Specifying zero will cause no fractional digits
     * to be output. From 1 to 9 will output an increasing number of digits, using
     * zero right-padding if necessary. The special value -1 is used to output as
     * many digits as necessary to avoid any trailing zeroes.
     * <p>
     * When parsing in strict mode, the number of parsed digits must match the
     * fractional digits. When parsing in lenient mode, any number of fractional
     * digits from zero to nine are accepted.
     * <p>
     * The instant is obtained using {@link ChronoField#INSTANT_SECONDS INSTANT_SECONDS}
     * and optionally (@code NANO_OF_SECOND). The value of {@code INSTANT_SECONDS}
     * may be outside the maximum range of {@code LocalDateTime}.
     * <p>
     * The {@linkplain ResolverStyle resolver style} has no effect on instant parsing.
     * The end-of-day time of '24:00' is handled as midnight at the start of the following day.
     * The leap-second time of '23:59:59' is handled to some degree, see
     * {@link DateTimeFormatter#parsedLeapSecond()} for full details.
     * <p>
     * An alternative to this method is to format/parse the instant as a single
     * epoch-seconds value. That is achieved using {@code appendValue(INSTANT_SECONDS)}.
     *
     * <p>
     *  使用ISO-8601附加一个即时到格式化程序,控制小数位数
     * <p>
     * 实例具有固定的输出格式,尽管此方法提供对小数位数的一些控制。它们被转换为具有UTC的区域偏移的日期时间,并使用标准ISO-8601格式打印。不使用本地化的小数格式
     * <p>
     *  {@code fractionalDigits}参数允许控制小数秒的输出指定零将导致不输出小数位从1到9将输出递增的数字,如果必要使用零右填充特殊值 - 1用于输出所需的数字,以避免任何尾随零
     * <p>
     *  在严格模式下解析时,解析数字的数量必须与小数数字匹配。在宽松模式下解析时,接受从0到9的任意数量的小数位数
     * <p>
     * 该时刻是使用{@link ChronoField#INSTANT_SECONDS INSTANT_SECONDS}和可选的(@code NANO_OF_SECOND)获得的。
     * {@code INSTANT_SECONDS}的值可能超出{@code LocalDateTime}的最大范围,。
     * <p>
     *  {@linkplain ResolverStyle解析器样式}对即时解析没有影响'24：00'的日终时间被处理为第二天开始时的午夜闰秒时间'23：59：59 '在一定程度上处理,请参阅{@link DateTimeFormatter#parsedLeapSecond()}
     * 了解完整详细信息。
     * <p>
     *  这种方法的一个替代方法是将时间格式化/解析为单个纪元秒值。使用{@code appendValue(INSTANT_SECONDS)}
     * 
     * 
     * @param fractionalDigits  the number of fractional second digits to format with,
     *  from 0 to 9, or -1 to use as many digits as necessary
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendInstant(int fractionalDigits) {
        if (fractionalDigits < -1 || fractionalDigits > 9) {
            throw new IllegalArgumentException("The fractional digits must be from -1 to 9 inclusive but was " + fractionalDigits);
        }
        appendInternal(new InstantPrinterParser(fractionalDigits));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends the zone offset, such as '+01:00', to the formatter.
     * <p>
     * This appends an instruction to format/parse the offset ID to the builder.
     * This is equivalent to calling {@code appendOffset("HH:MM:ss", "Z")}.
     *
     * <p>
     *  将区域偏移量(例如"+01：00")附加到格式化程序
     * <p>
     * 这附加了一个指令来将偏移ID格式化/解析到构建器这等效于调用{@code appendOffset("HH：MM：ss","Z")}
     * 
     * 
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendOffsetId() {
        appendInternal(OffsetIdPrinterParser.INSTANCE_ID_Z);
        return this;
    }

    /**
     * Appends the zone offset, such as '+01:00', to the formatter.
     * <p>
     * This appends an instruction to format/parse the offset ID to the builder.
     * <p>
     * During formatting, the offset is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#offset()}.
     * It will be printed using the format defined below.
     * If the offset cannot be obtained then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * During parsing, the offset is parsed using the format defined below.
     * If the offset cannot be parsed then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * The format of the offset is controlled by a pattern which must be one
     * of the following:
     * <ul>
     * <li>{@code +HH} - hour only, ignoring minute and second
     * <li>{@code +HHmm} - hour, with minute if non-zero, ignoring second, no colon
     * <li>{@code +HH:mm} - hour, with minute if non-zero, ignoring second, with colon
     * <li>{@code +HHMM} - hour and minute, ignoring second, no colon
     * <li>{@code +HH:MM} - hour and minute, ignoring second, with colon
     * <li>{@code +HHMMss} - hour and minute, with second if non-zero, no colon
     * <li>{@code +HH:MM:ss} - hour and minute, with second if non-zero, with colon
     * <li>{@code +HHMMSS} - hour, minute and second, no colon
     * <li>{@code +HH:MM:SS} - hour, minute and second, with colon
     * </ul>
     * The "no offset" text controls what text is printed when the total amount of
     * the offset fields to be output is zero.
     * Example values would be 'Z', '+00:00', 'UTC' or 'GMT'.
     * Three formats are accepted for parsing UTC - the "no offset" text, and the
     * plus and minus versions of zero defined by the pattern.
     *
     * <p>
     *  将区域偏移量(例如"+01：00")附加到格式化程序
     * <p>
     *  这将附加一条指令来将偏移ID格式化/解析到构建器
     * <p>
     *  在格式化期间,使用等同于使用{@link TemporalQueries#offset()}查询时间的机制来获得偏移量。将使用下面定义的格式打印偏移量。
     * 如果不能获得偏移量,则抛出异常,除非格式化程序是可选的。
     * <p>
     *  在解析期间,使用以下定义的格式解析偏移量如果无法解析偏移量,则抛出异常,除非格式化程序的部分是可选的
     * <p>
     * 偏移的格式由一个模式控制,该模式必须是以下之一：
     * <ul>
     *  <li> {@ code + HH}  - 小时,忽略分钟和秒<li> {@ code + HHmm}  - 小时,如果非零, mm}  - 小时,如果非零则为分钟,忽略第二个,使用冒号<@> {@ code + HHMM}
     *   - 小时和分钟,忽略第二个,不含冒号<@> {@ code + HH：MM}分钟,忽略秒,使用冒号<@> {@ code + HHMMss}  - 小时和分钟,第二个(非零),不含冒号<@> {@ code + HH：MM：ss}
     *   - 小时和分钟如果非零,带有冒号<@> {@ code + HHMMSS}  - 小时,分钟和秒,无冒号<@> {@ code + HH：MM：SS}  - 小时,分钟和秒,。
     * </ul>
     * 当要输出的偏移字段的总量为零时,"无偏移"文本控制打印什么文本示例值将是'Z','+ 00：00','UTC'或'GMT'三种格式被接受用于解析UTC  - "无偏移"文本,以及由模式定义的零的正负版本
     * 。
     * 
     * 
     * @param pattern  the pattern to use, not null
     * @param noOffsetText  the text to use when the offset is zero, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendOffset(String pattern, String noOffsetText) {
        appendInternal(new OffsetIdPrinterParser(pattern, noOffsetText));
        return this;
    }

    /**
     * Appends the localized zone offset, such as 'GMT+01:00', to the formatter.
     * <p>
     * This appends a localized zone offset to the builder, the format of the
     * localized offset is controlled by the specified {@link FormatStyle style}
     * to this method:
     * <ul>
     * <li>{@link TextStyle#FULL full} - formats with localized offset text, such
     * as 'GMT, 2-digit hour and minute field, optional second field if non-zero,
     * and colon.
     * <li>{@link TextStyle#SHORT short} - formats with localized offset text,
     * such as 'GMT, hour without leading zero, optional 2-digit minute and
     * second if non-zero, and colon.
     * </ul>
     * <p>
     * During formatting, the offset is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#offset()}.
     * If the offset cannot be obtained then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * During parsing, the offset is parsed using the format defined above.
     * If the offset cannot be parsed then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * <p>
     *  将本地化区域偏移(例如"GMT + 01：00")附加到格式化程序
     * <p>
     *  这会将一个本地化区域偏移附加到构建器,本地化偏移的格式由此方法的指定{@link FormatStyle style}控制：
     * <ul>
     * <li> {@ link TextStyle#FULL full}  - 采用本地化偏移文字的格式,例如'GMT,2位小时和分钟字段,非零时可选的第二个字段和冒号<li> {@ link TextStyle#SHORT short}
     *   - 带有本地偏移文本的格式,例如'GMT,无前导零的小时,可选的2位数分钟,如果非零则为秒,以及冒号。
     * </ul>
     * <p>
     *  在格式化期间,使用等同于使用{@link TemporalQueries#offset()}查询时间的机制来获得偏移。如果不能获得偏移,则抛出异常,除非格式化器的部分是可选的
     * <p>
     *  在解析期间,使用上面定义的格式解析偏移量如果不能解析偏移,则抛出异常,除非格式化器的部分是可选的
     * <p>
     * 
     * @param style  the format style to use, not null
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if style is neither {@link TextStyle#FULL
     * full} nor {@link TextStyle#SHORT short}
     */
    public DateTimeFormatterBuilder appendLocalizedOffset(TextStyle style) {
        Objects.requireNonNull(style, "style");
        if (style != TextStyle.FULL && style != TextStyle.SHORT) {
            throw new IllegalArgumentException("Style must be either full or short");
        }
        appendInternal(new LocalizedOffsetIdPrinterParser(style));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends the time-zone ID, such as 'Europe/Paris' or '+02:00', to the formatter.
     * <p>
     * This appends an instruction to format/parse the zone ID to the builder.
     * The zone ID is obtained in a strict manner suitable for {@code ZonedDateTime}.
     * By contrast, {@code OffsetDateTime} does not have a zone ID suitable
     * for use with this method, see {@link #appendZoneOrOffsetId()}.
     * <p>
     * During formatting, the zone is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#zoneId()}.
     * It will be printed using the result of {@link ZoneId#getId()}.
     * If the zone cannot be obtained then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * During parsing, the text must match a known zone or offset.
     * There are two types of zone ID, offset-based, such as '+01:30' and
     * region-based, such as 'Europe/London'. These are parsed differently.
     * If the parse starts with '+', '-', 'UT', 'UTC' or 'GMT', then the parser
     * expects an offset-based zone and will not match region-based zones.
     * The offset ID, such as '+02:30', may be at the start of the parse,
     * or prefixed by  'UT', 'UTC' or 'GMT'. The offset ID parsing is
     * equivalent to using {@link #appendOffset(String, String)} using the
     * arguments 'HH:MM:ss' and the no offset string '0'.
     * If the parse starts with 'UT', 'UTC' or 'GMT', and the parser cannot
     * match a following offset ID, then {@link ZoneOffset#UTC} is selected.
     * In all other cases, the list of known region-based zones is used to
     * find the longest available match. If no match is found, and the parse
     * starts with 'Z', then {@code ZoneOffset.UTC} is selected.
     * The parser uses the {@linkplain #parseCaseInsensitive() case sensitive} setting.
     * <p>
     * For example, the following will parse:
     * <pre>
     *   "Europe/London"           -- ZoneId.of("Europe/London")
     *   "Z"                       -- ZoneOffset.UTC
     *   "UT"                      -- ZoneId.of("UT")
     *   "UTC"                     -- ZoneId.of("UTC")
     *   "GMT"                     -- ZoneId.of("GMT")
     *   "+01:30"                  -- ZoneOffset.of("+01:30")
     *   "UT+01:30"                -- ZoneOffset.of("+01:30")
     *   "UTC+01:30"               -- ZoneOffset.of("+01:30")
     *   "GMT+01:30"               -- ZoneOffset.of("+01:30")
     * </pre>
     *
     * <p>
     * 将时区ID(例如"Europe / Paris"或"+02：00")附加到格式化程序
     * <p>
     *  这将追加用于将区域ID格式化/解析到构建器的指令。区ID是以适合于{@code ZonedDateTime}的严格方式获得的。
     * 相反,{@code OffsetDateTime}没有适合于该方法使用的区ID ,请参阅{@link #appendZoneOrOffsetId()}。
     * <p>
     *  在格式化期间,使用等同于使用{@link TemporalQueries#zoneId()}查询时间的机制获得区域。将使用{@link ZoneId#getId()}的结果打印该区域。
     * 如果不能获得区域,则抛出异常,除非格式化程序的部分是可选的。
     * <p>
     * 在解析期间,文本必须匹配已知区域或偏移量有两种类型的区域ID,基于偏移量(例如"+01：30")和基于区域(例如"Europe / London")。
     * 解析器以'+',' - ','UT','UTC'或'GMT'开始,则解析器期望基于偏移的区域,并且不匹配基于区域的区域偏移ID, 30',可以在解析的开始,或前缀'UT','UTC'或'GMT'偏移ID
     * 解析等效于使用{@link #appendOffset(String,String)}使用参数'HH ：MM：ss'和无偏移字符串'0'如果解析以"UT","UTC"或"GMT"开始,并且解析器不能匹配
     * 后面的偏移ID,则选择{@link ZoneOffset#UTC}在所有其他情况下,已知基于区域的区域的列表用于查找最长可用匹配如果未找到匹配,并且解析以"Z"开头,则选择{@code ZoneOffsetUTC}
     * 解析器使用{@ linkplain #parseCaseInsensitive()区分大小写}设置。
     * 在解析期间,文本必须匹配已知区域或偏移量有两种类型的区域ID,基于偏移量(例如"+01：30")和基于区域(例如"Europe / London")。
     * <p>
     * 例如,下面将解析：
     * <pre>
     *  "Europe / London" -  ZoneIdof("Europe / London")"Z" -  ZoneOffsetUTC"UT" -  ZoneIdof("UT")"UTC" -  Z
     * oneIdof("UTC")"GMT" -  ZoneIdof "+ 01：30" -  ZoneOffsetof("+ 01:30")"UTC + 01：30" -  ZoneOffsetof "+0
     * 1：30")"GMT + 01：30" -  ZoneOffsetof("+ 01:30")。
     * </pre>
     * 
     * 
     * @return this, for chaining, not null
     * @see #appendZoneRegionId()
     */
    public DateTimeFormatterBuilder appendZoneId() {
        appendInternal(new ZoneIdPrinterParser(TemporalQueries.zoneId(), "ZoneId()"));
        return this;
    }

    /**
     * Appends the time-zone region ID, such as 'Europe/Paris', to the formatter,
     * rejecting the zone ID if it is a {@code ZoneOffset}.
     * <p>
     * This appends an instruction to format/parse the zone ID to the builder
     * only if it is a region-based ID.
     * <p>
     * During formatting, the zone is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#zoneId()}.
     * If the zone is a {@code ZoneOffset} or it cannot be obtained then
     * an exception is thrown unless the section of the formatter is optional.
     * If the zone is not an offset, then the zone will be printed using
     * the zone ID from {@link ZoneId#getId()}.
     * <p>
     * During parsing, the text must match a known zone or offset.
     * There are two types of zone ID, offset-based, such as '+01:30' and
     * region-based, such as 'Europe/London'. These are parsed differently.
     * If the parse starts with '+', '-', 'UT', 'UTC' or 'GMT', then the parser
     * expects an offset-based zone and will not match region-based zones.
     * The offset ID, such as '+02:30', may be at the start of the parse,
     * or prefixed by  'UT', 'UTC' or 'GMT'. The offset ID parsing is
     * equivalent to using {@link #appendOffset(String, String)} using the
     * arguments 'HH:MM:ss' and the no offset string '0'.
     * If the parse starts with 'UT', 'UTC' or 'GMT', and the parser cannot
     * match a following offset ID, then {@link ZoneOffset#UTC} is selected.
     * In all other cases, the list of known region-based zones is used to
     * find the longest available match. If no match is found, and the parse
     * starts with 'Z', then {@code ZoneOffset.UTC} is selected.
     * The parser uses the {@linkplain #parseCaseInsensitive() case sensitive} setting.
     * <p>
     * For example, the following will parse:
     * <pre>
     *   "Europe/London"           -- ZoneId.of("Europe/London")
     *   "Z"                       -- ZoneOffset.UTC
     *   "UT"                      -- ZoneId.of("UT")
     *   "UTC"                     -- ZoneId.of("UTC")
     *   "GMT"                     -- ZoneId.of("GMT")
     *   "+01:30"                  -- ZoneOffset.of("+01:30")
     *   "UT+01:30"                -- ZoneOffset.of("+01:30")
     *   "UTC+01:30"               -- ZoneOffset.of("+01:30")
     *   "GMT+01:30"               -- ZoneOffset.of("+01:30")
     * </pre>
     * <p>
     * Note that this method is is identical to {@code appendZoneId()} except
     * in the mechanism used to obtain the zone.
     * Note also that parsing accepts offsets, whereas formatting will never
     * produce one.
     *
     * <p>
     *  将时区区域ID(如"Europe / Paris")附加到格式化程序,如果它是{@code ZoneOffset},则拒绝区域ID
     * <p>
     *  如果它是基于区域的ID,则此附加指令来将区域ID格式化/解析到构建器
     * <p>
     * 在格式化期间,使用等同于使用{@link TemporalQueries#zoneId()}查询时间的机制获得区域。
     * 如果区域是{@code ZoneOffset}或者无法获得,则抛出异常,除非格式化程序是可选的如果区域不是偏移量,则将使用{@link ZoneId#getId()}中的区域ID打印区域。
     * <p>
     * 在解析期间,文本必须匹配已知区域或偏移量有两种类型的区域ID,基于偏移量(例如"+01：30")和基于区域(例如"Europe / London")。
     * 解析器以'+',' - ','UT','UTC'或'GMT'开始,则解析器期望基于偏移的区域,并且不匹配基于区域的区域偏移ID, 30',可以在解析的开始,或前缀'UT','UTC'或'GMT'偏移ID
     * 解析等效于使用{@link #appendOffset(String,String)}使用参数'HH ：MM：ss'和无偏移字符串'0'如果解析以"UT","UTC"或"GMT"开始,并且解析器不能匹配
     * 后面的偏移ID,则选择{@link ZoneOffset#UTC}在所有其他情况下,已知基于区域的区域的列表用于查找最长可用匹配如果未找到匹配,并且解析以"Z"开头,则选择{@code ZoneOffsetUTC}
     * 解析器使用{@ linkplain #parseCaseInsensitive()区分大小写}设置。
     * 在解析期间,文本必须匹配已知区域或偏移量有两种类型的区域ID,基于偏移量(例如"+01：30")和基于区域(例如"Europe / London")。
     * <p>
     * 例如,下面将解析：
     * <pre>
     *  "Europe / London" -  ZoneIdof("Europe / London")"Z" -  ZoneOffsetUTC"UT" -  ZoneIdof("UT")"UTC" -  Z
     * oneIdof("UTC")"GMT" -  ZoneIdof "+ 01：30" -  ZoneOffsetof("+ 01:30")"UTC + 01：30" -  ZoneOffsetof "+0
     * 1：30")"GMT + 01：30" -  ZoneOffsetof("+ 01:30")。
     * </pre>
     * <p>
     *  注意这个方法和{@code appendZoneId()}是一样的,除了用于获取区域的机制注意,解析接受偏移,而格式化永远不会产生一个
     * 
     * 
     * @return this, for chaining, not null
     * @see #appendZoneId()
     */
    public DateTimeFormatterBuilder appendZoneRegionId() {
        appendInternal(new ZoneIdPrinterParser(QUERY_REGION_ONLY, "ZoneRegionId()"));
        return this;
    }

    /**
     * Appends the time-zone ID, such as 'Europe/Paris' or '+02:00', to
     * the formatter, using the best available zone ID.
     * <p>
     * This appends an instruction to format/parse the best available
     * zone or offset ID to the builder.
     * The zone ID is obtained in a lenient manner that first attempts to
     * find a true zone ID, such as that on {@code ZonedDateTime}, and
     * then attempts to find an offset, such as that on {@code OffsetDateTime}.
     * <p>
     * During formatting, the zone is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#zone()}.
     * It will be printed using the result of {@link ZoneId#getId()}.
     * If the zone cannot be obtained then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * During parsing, the text must match a known zone or offset.
     * There are two types of zone ID, offset-based, such as '+01:30' and
     * region-based, such as 'Europe/London'. These are parsed differently.
     * If the parse starts with '+', '-', 'UT', 'UTC' or 'GMT', then the parser
     * expects an offset-based zone and will not match region-based zones.
     * The offset ID, such as '+02:30', may be at the start of the parse,
     * or prefixed by  'UT', 'UTC' or 'GMT'. The offset ID parsing is
     * equivalent to using {@link #appendOffset(String, String)} using the
     * arguments 'HH:MM:ss' and the no offset string '0'.
     * If the parse starts with 'UT', 'UTC' or 'GMT', and the parser cannot
     * match a following offset ID, then {@link ZoneOffset#UTC} is selected.
     * In all other cases, the list of known region-based zones is used to
     * find the longest available match. If no match is found, and the parse
     * starts with 'Z', then {@code ZoneOffset.UTC} is selected.
     * The parser uses the {@linkplain #parseCaseInsensitive() case sensitive} setting.
     * <p>
     * For example, the following will parse:
     * <pre>
     *   "Europe/London"           -- ZoneId.of("Europe/London")
     *   "Z"                       -- ZoneOffset.UTC
     *   "UT"                      -- ZoneId.of("UT")
     *   "UTC"                     -- ZoneId.of("UTC")
     *   "GMT"                     -- ZoneId.of("GMT")
     *   "+01:30"                  -- ZoneOffset.of("+01:30")
     *   "UT+01:30"                -- ZoneOffset.of("UT+01:30")
     *   "UTC+01:30"               -- ZoneOffset.of("UTC+01:30")
     *   "GMT+01:30"               -- ZoneOffset.of("GMT+01:30")
     * </pre>
     * <p>
     * Note that this method is is identical to {@code appendZoneId()} except
     * in the mechanism used to obtain the zone.
     *
     * <p>
     * 使用最佳可用区域ID将时区ID(例如"Europe / Paris"或"+02：00")附加到格式化程序
     * <p>
     *  这将附加指令来将最佳可用区域或偏移ID格式化/解析到构建器。
     * 区域ID以宽松的方式获得,首先尝试找到真正的区域ID,例如{@code ZonedDateTime}上的ID,然后尝试找到偏移量,例如{@code OffsetDateTime}。
     * <p>
     *  在格式化期间,使用等同于使用{@link TemporalQueries#zone()}查询时间的机制获得区域。将使用{@link ZoneId#getId()}的结果打印该区域。
     * 如果不能获得区域,则抛出异常,除非格式化程序的部分是可选的。
     * <p>
     * 在解析期间,文本必须匹配已知区域或偏移量有两种类型的区域ID,基于偏移量(例如"+01：30")和基于区域(例如"Europe / London")。
     * 解析器以'+',' - ','UT','UTC'或'GMT'开始,则解析器期望基于偏移的区域,并且不匹配基于区域的区域偏移ID, 30',可以在解析的开始,或前缀'UT','UTC'或'GMT'偏移ID
     * 解析等效于使用{@link #appendOffset(String,String)}使用参数'HH ：MM：ss'和无偏移字符串'0'如果解析以"UT","UTC"或"GMT"开始,并且解析器不能匹配
     * 后面的偏移ID,则选择{@link ZoneOffset#UTC}在所有其他情况下,已知基于区域的区域的列表用于查找最长可用匹配如果未找到匹配,并且解析以"Z"开头,则选择{@code ZoneOffsetUTC}
     * 解析器使用{@ linkplain #parseCaseInsensitive()区分大小写}设置。
     * 在解析期间,文本必须匹配已知区域或偏移量有两种类型的区域ID,基于偏移量(例如"+01：30")和基于区域(例如"Europe / London")。
     * <p>
     * 例如,下面将解析：
     * <pre>
     *  "Europe / London" -  ZoneIdof("Europe / London")"Z" -  ZoneOffsetUTC"UT" -  ZoneIdof("UT")"UTC" -  Z
     * oneIdof("UTC")"GMT" -  ZoneIdof "ZoneOffsetof("UT + 01：30")"UTC + 01：30" -  ZoneOffsetof("+ 01：30")" 
     * ("UTC + 01：30")"GMT + 01：30" -  ZoneOffsetof("GMT + 01：30")。
     * </pre>
     * <p>
     *  注意这个方法与{@code appendZoneId()}是相同的,除了用于获得区域的机制
     * 
     * 
     * @return this, for chaining, not null
     * @see #appendZoneId()
     */
    public DateTimeFormatterBuilder appendZoneOrOffsetId() {
        appendInternal(new ZoneIdPrinterParser(TemporalQueries.zone(), "ZoneOrOffsetId()"));
        return this;
    }

    /**
     * Appends the time-zone name, such as 'British Summer Time', to the formatter.
     * <p>
     * This appends an instruction to format/parse the textual name of the zone to
     * the builder.
     * <p>
     * During formatting, the zone is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#zoneId()}.
     * If the zone is a {@code ZoneOffset} it will be printed using the
     * result of {@link ZoneOffset#getId()}.
     * If the zone is not an offset, the textual name will be looked up
     * for the locale set in the {@link DateTimeFormatter}.
     * If the temporal object being printed represents an instant, then the text
     * will be the summer or winter time text as appropriate.
     * If the lookup for text does not find any suitable reuslt, then the
     * {@link ZoneId#getId() ID} will be printed instead.
     * If the zone cannot be obtained then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * During parsing, either the textual zone name, the zone ID or the offset
     * is accepted. Many textual zone names are not unique, such as CST can be
     * for both "Central Standard Time" and "China Standard Time". In this
     * situation, the zone id will be determined by the region information from
     * formatter's  {@link DateTimeFormatter#getLocale() locale} and the standard
     * zone id for that area, for example, America/New_York for the America Eastern
     * zone. The {@link #appendZoneText(TextStyle, Set)} may be used
     * to specify a set of preferred {@link ZoneId} in this situation.
     *
     * <p>
     *  将时区名称(例如"英国夏令时")附加到格式化程序
     * <p>
     *  这将向编译器附加用于格式化/解析区域的文本名称的指令
     * <p>
     * 在格式化期间,使用等同于使用{@link TemporalQueries#zoneId()}查询时间的机制获得区域。
     * 如果区域是{@code ZoneOffset},则将使用{@link ZoneOffset#getId )}如果区域不是偏移量,则将查找文本名称以查找在{@link DateTimeFormatter}
     * 中设置的语言环境。
     * 在格式化期间,使用等同于使用{@link TemporalQueries#zoneId()}查询时间的机制获得区域。
     * 如果正在打印的时态对象表示一个时刻,则文本将是夏季或冬季时间文本适当如果对文本的查找没有找到任何合适的reuslt,则将打印{@link ZoneId#getId()ID}如果无法获取区域,则会抛出异常
     * ,除非格式化程序的部分是可选的。
     * 在格式化期间,使用等同于使用{@link TemporalQueries#zoneId()}查询时间的机制获得区域。
     * <p>
     * 在解析期间,接受文本区名称,区域ID或偏移量许多文本区名称不是唯一的,例如CST可以用于"中央标准时间"和"中国标准时间"在这种情况下,区域ID将由格式化器的{@link DateTimeFormatter#getLocale()locale}
     * 和该区域的标准区域ID的区域信息确定,例如美国东部区域的America / New_York {@link #appendZoneText(TextStyle,Set) }可以用于在这种情况下指定一组优
     * 选的{@link ZoneId}。
     * 
     * 
     * @param textStyle  the text style to use, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendZoneText(TextStyle textStyle) {
        appendInternal(new ZoneTextPrinterParser(textStyle, null));
        return this;
    }

    /**
     * Appends the time-zone name, such as 'British Summer Time', to the formatter.
     * <p>
     * This appends an instruction to format/parse the textual name of the zone to
     * the builder.
     * <p>
     * During formatting, the zone is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#zoneId()}.
     * If the zone is a {@code ZoneOffset} it will be printed using the
     * result of {@link ZoneOffset#getId()}.
     * If the zone is not an offset, the textual name will be looked up
     * for the locale set in the {@link DateTimeFormatter}.
     * If the temporal object being printed represents an instant, then the text
     * will be the summer or winter time text as appropriate.
     * If the lookup for text does not find any suitable reuslt, then the
     * {@link ZoneId#getId() ID} will be printed instead.
     * If the zone cannot be obtained then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * During parsing, either the textual zone name, the zone ID or the offset
     * is accepted. Many textual zone names are not unique, such as CST can be
     * for both "Central Standard Time" and "China Standard Time". In this
     * situation, the zone id will be determined by the region information from
     * formatter's  {@link DateTimeFormatter#getLocale() locale} and the standard
     * zone id for that area, for example, America/New_York for the America Eastern
     * zone. This method also allows a set of preferred {@link ZoneId} to be
     * specified for parsing. The matched preferred zone id will be used if the
     * textural zone name being parsed is not unique.
     *
     * If the zone cannot be parsed then an exception is thrown unless the
     * section of the formatter is optional.
     *
     * <p>
     *  将时区名称(例如"英国夏令时")附加到格式化程序
     * <p>
     *  这将向编译器附加用于格式化/解析区域的文本名称的指令
     * <p>
     * 在格式化期间,使用等同于使用{@link TemporalQueries#zoneId()}查询时间的机制获得区域。
     * 如果区域是{@code ZoneOffset},则将使用{@link ZoneOffset#getId )}如果区域不是偏移量,则将查找文本名称以查找在{@link DateTimeFormatter}
     * 中设置的语言环境。
     * 在格式化期间,使用等同于使用{@link TemporalQueries#zoneId()}查询时间的机制获得区域。
     * 如果正在打印的时态对象表示一个时刻,则文本将是夏季或冬季时间文本适当如果对文本的查找没有找到任何合适的reuslt,则将打印{@link ZoneId#getId()ID}如果无法获取区域,则会抛出异常
     * ,除非格式化程序的部分是可选的。
     * 在格式化期间,使用等同于使用{@link TemporalQueries#zoneId()}查询时间的机制获得区域。
     * <p>
     * 在解析期间,接受文本区名称,区域ID或偏移量许多文本区名称不是唯一的,例如CST可以用于"中央标准时间"和"中国标准时间"在这种情况下,区域ID将由格式化器的{@link DateTimeFormatter#getLocale()locale}
     * 的区域信息和该区域的标准区域ID确定,例如America / New_York用于美国东部区域此方法还允许一组首选{@link ZoneId}如果要解析的纹理区域名称不唯一,将使用匹配的首选区域ID。
     * 
     *  如果无法解析区域,则抛出异常,除非格式化程序的部分是可选的
     * 
     * 
     * @param textStyle  the text style to use, not null
     * @param preferredZones  the set of preferred zone ids, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendZoneText(TextStyle textStyle,
                                                   Set<ZoneId> preferredZones) {
        Objects.requireNonNull(preferredZones, "preferredZones");
        appendInternal(new ZoneTextPrinterParser(textStyle, preferredZones));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends the chronology ID, such as 'ISO' or 'ThaiBuddhist', to the formatter.
     * <p>
     * This appends an instruction to format/parse the chronology ID to the builder.
     * <p>
     * During formatting, the chronology is obtained using a mechanism equivalent
     * to querying the temporal with {@link TemporalQueries#chronology()}.
     * It will be printed using the result of {@link Chronology#getId()}.
     * If the chronology cannot be obtained then an exception is thrown unless the
     * section of the formatter is optional.
     * <p>
     * During parsing, the chronology is parsed and must match one of the chronologies
     * in {@link Chronology#getAvailableChronologies()}.
     * If the chronology cannot be parsed then an exception is thrown unless the
     * section of the formatter is optional.
     * The parser uses the {@linkplain #parseCaseInsensitive() case sensitive} setting.
     *
     * <p>
     * 将年表ID(如"ISO"或"ThaiBuddhist")附加到格式化程序
     * <p>
     *  这将附加一条指令来将编年表ID格式化/解析到构建器
     * <p>
     *  在格式化期间,使用等同于使用{@link TemporalQueries#chronology()}查询时间的机制来获得年表。将使用{@link Chronology#getId()}的结果打印。
     * 如果不能获得年表,抛出异常,除非格式化程序的部分是可选的。
     * <p>
     * 在解析期间,解析时间顺序并且必须匹配{@link Chronology#getAvailableChronologies()}中的一个时间表。
     * 如果无法解析年表,则抛出异常,除非格式化程序的部分是可选的。解析器使用{@linkplain #parseCaseInsensitive()区分大小写}设置。
     * 
     * 
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendChronologyId() {
        appendInternal(new ChronoPrinterParser(null));
        return this;
    }

    /**
     * Appends the chronology name to the formatter.
     * <p>
     * The calendar system name will be output during a format.
     * If the chronology cannot be obtained then an exception will be thrown.
     * The calendar system name is obtained from the Chronology.
     *
     * <p>
     *  将年表名称附加到格式化程序
     * <p>
     *  日历系统名称将在格式期间输出如果无法获取年表,则将抛出异常日历系统名称从年表中获取
     * 
     * 
     * @param textStyle  the text style to use, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendChronologyText(TextStyle textStyle) {
        Objects.requireNonNull(textStyle, "textStyle");
        appendInternal(new ChronoPrinterParser(textStyle));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends a localized date-time pattern to the formatter.
     * <p>
     * This appends a localized section to the builder, suitable for outputting
     * a date, time or date-time combination. The format of the localized
     * section is lazily looked up based on four items:
     * <ul>
     * <li>the {@code dateStyle} specified to this method
     * <li>the {@code timeStyle} specified to this method
     * <li>the {@code Locale} of the {@code DateTimeFormatter}
     * <li>the {@code Chronology}, selecting the best available
     * </ul>
     * During formatting, the chronology is obtained from the temporal object
     * being formatted, which may have been overridden by
     * {@link DateTimeFormatter#withChronology(Chronology)}.
     * <p>
     * During parsing, if a chronology has already been parsed, then it is used.
     * Otherwise the default from {@code DateTimeFormatter.withChronology(Chronology)}
     * is used, with {@code IsoChronology} as the fallback.
     * <p>
     * Note that this method provides similar functionality to methods on
     * {@code DateFormat} such as {@link java.text.DateFormat#getDateTimeInstance(int, int)}.
     *
     * <p>
     *  将本地化日期时间模式附加到格式化程序
     * <p>
     * 这会将一个本地化部分附加到构建器,适合输出日期,时间或日期时间组合本地化部分的格式基于四个项目进行延迟查找：
     * <ul>
     *  <li>指定给此方法的{@code dateStyle} <li>指定给此方法的{@code timeStyle} <li> {@code DateTimeFormatter}的{@code Locale}
     *  <li> {@code Chronology },选择最佳可用。
     * </ul>
     *  在格式化期间,时间顺序是从正在格式化的时间对象获得的,其可能已被{@link DateTimeFormatter#with Chronology(Chronology)}覆盖
     * <p>
     *  在解析期间,如果已经解析了年表,则使用它。
     * 否则,使用{@code DateTimeFormatterwithChronology(Chronology)}的默认值,使用{@code IsoChronology}作为后备。
     * <p>
     * 请注意,此方法提供与{@code DateFormat}上的方法类似的功能,例如{@link javatextDateFormat#getDateTimeInstance(int,int)}
     * 
     * 
     * @param dateStyle  the date style to use, null means no date required
     * @param timeStyle  the time style to use, null means no time required
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if both the date and time styles are null
     */
    public DateTimeFormatterBuilder appendLocalized(FormatStyle dateStyle, FormatStyle timeStyle) {
        if (dateStyle == null && timeStyle == null) {
            throw new IllegalArgumentException("Either the date or time style must be non-null");
        }
        appendInternal(new LocalizedPrinterParser(dateStyle, timeStyle));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends a character literal to the formatter.
     * <p>
     * This character will be output during a format.
     *
     * <p>
     *  将字符文字附加到格式化程序
     * <p>
     *  此字符将在格式期间输出
     * 
     * 
     * @param literal  the literal to append, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendLiteral(char literal) {
        appendInternal(new CharLiteralPrinterParser(literal));
        return this;
    }

    /**
     * Appends a string literal to the formatter.
     * <p>
     * This string will be output during a format.
     * <p>
     * If the literal is empty, nothing is added to the formatter.
     *
     * <p>
     *  将字符串文字附加到格式化程序
     * <p>
     *  此字符串将在格式期间输出
     * <p>
     *  如果文字为空,则不会将任何内容添加到格式化程序
     * 
     * 
     * @param literal  the literal to append, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendLiteral(String literal) {
        Objects.requireNonNull(literal, "literal");
        if (literal.length() > 0) {
            if (literal.length() == 1) {
                appendInternal(new CharLiteralPrinterParser(literal.charAt(0)));
            } else {
                appendInternal(new StringLiteralPrinterParser(literal));
            }
        }
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends all the elements of a formatter to the builder.
     * <p>
     * This method has the same effect as appending each of the constituent
     * parts of the formatter directly to this builder.
     *
     * <p>
     *  将格式化程序的所有元素附加到构建器
     * <p>
     *  该方法具有将格式化器的每个组成部分直接附加到该构建器的相同效果
     * 
     * 
     * @param formatter  the formatter to add, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder append(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        appendInternal(formatter.toPrinterParser(false));
        return this;
    }

    /**
     * Appends a formatter to the builder which will optionally format/parse.
     * <p>
     * This method has the same effect as appending each of the constituent
     * parts directly to this builder surrounded by an {@link #optionalStart()} and
     * {@link #optionalEnd()}.
     * <p>
     * The formatter will format if data is available for all the fields contained within it.
     * The formatter will parse if the string matches, otherwise no error is returned.
     *
     * <p>
     *  将格式化程序附加到构建器,可以选择格式化/解析
     * <p>
     * 此方法具有将每个组成部分直接附加到此构建器的相同效果,其由{@link #optionalStart()}和{@link #optionalEnd()}
     * <p>
     *  如果数据对其中包含的所有字段可用,格式化程序将格式化格式化程序将解析如果字符串匹配,否则不返回错误
     * 
     * 
     * @param formatter  the formatter to add, not null
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder appendOptional(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        appendInternal(formatter.toPrinterParser(true));
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends the elements defined by the specified pattern to the builder.
     * <p>
     * All letters 'A' to 'Z' and 'a' to 'z' are reserved as pattern letters.
     * The characters '#', '{' and '}' are reserved for future use.
     * The characters '[' and ']' indicate optional patterns.
     * The following pattern letters are defined:
     * <pre>
     *  Symbol  Meaning                     Presentation      Examples
     *  ------  -------                     ------------      -------
     *   G       era                         text              AD; Anno Domini; A
     *   u       year                        year              2004; 04
     *   y       year-of-era                 year              2004; 04
     *   D       day-of-year                 number            189
     *   M/L     month-of-year               number/text       7; 07; Jul; July; J
     *   d       day-of-month                number            10
     *
     *   Q/q     quarter-of-year             number/text       3; 03; Q3; 3rd quarter
     *   Y       week-based-year             year              1996; 96
     *   w       week-of-week-based-year     number            27
     *   W       week-of-month               number            4
     *   E       day-of-week                 text              Tue; Tuesday; T
     *   e/c     localized day-of-week       number/text       2; 02; Tue; Tuesday; T
     *   F       week-of-month               number            3
     *
     *   a       am-pm-of-day                text              PM
     *   h       clock-hour-of-am-pm (1-12)  number            12
     *   K       hour-of-am-pm (0-11)        number            0
     *   k       clock-hour-of-am-pm (1-24)  number            0
     *
     *   H       hour-of-day (0-23)          number            0
     *   m       minute-of-hour              number            30
     *   s       second-of-minute            number            55
     *   S       fraction-of-second          fraction          978
     *   A       milli-of-day                number            1234
     *   n       nano-of-second              number            987654321
     *   N       nano-of-day                 number            1234000000
     *
     *   V       time-zone ID                zone-id           America/Los_Angeles; Z; -08:30
     *   z       time-zone name              zone-name         Pacific Standard Time; PST
     *   O       localized zone-offset       offset-O          GMT+8; GMT+08:00; UTC-08:00;
     *   X       zone-offset 'Z' for zero    offset-X          Z; -08; -0830; -08:30; -083015; -08:30:15;
     *   x       zone-offset                 offset-x          +0000; -08; -0830; -08:30; -083015; -08:30:15;
     *   Z       zone-offset                 offset-Z          +0000; -0800; -08:00;
     *
     *   p       pad next                    pad modifier      1
     *
     *   '       escape for text             delimiter
     *   ''      single quote                literal           '
     *   [       optional section start
     *   ]       optional section end
     *   #       reserved for future use
     *   {       reserved for future use
     *   }       reserved for future use
     * </pre>
     * <p>
     * The count of pattern letters determine the format.
     * See <a href="DateTimeFormatter.html#patterns">DateTimeFormatter</a> for a user-focused description of the patterns.
     * The following tables define how the pattern letters map to the builder.
     * <p>
     * <b>Date fields</b>: Pattern letters to output a date.
     * <pre>
     *  Pattern  Count  Equivalent builder methods
     *  -------  -----  --------------------------
     *    G       1      appendText(ChronoField.ERA, TextStyle.SHORT)
     *    GG      2      appendText(ChronoField.ERA, TextStyle.SHORT)
     *    GGG     3      appendText(ChronoField.ERA, TextStyle.SHORT)
     *    GGGG    4      appendText(ChronoField.ERA, TextStyle.FULL)
     *    GGGGG   5      appendText(ChronoField.ERA, TextStyle.NARROW)
     *
     *    u       1      appendValue(ChronoField.YEAR, 1, 19, SignStyle.NORMAL);
     *    uu      2      appendValueReduced(ChronoField.YEAR, 2, 2000);
     *    uuu     3      appendValue(ChronoField.YEAR, 3, 19, SignStyle.NORMAL);
     *    u..u    4..n   appendValue(ChronoField.YEAR, n, 19, SignStyle.EXCEEDS_PAD);
     *    y       1      appendValue(ChronoField.YEAR_OF_ERA, 1, 19, SignStyle.NORMAL);
     *    yy      2      appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2000);
     *    yyy     3      appendValue(ChronoField.YEAR_OF_ERA, 3, 19, SignStyle.NORMAL);
     *    y..y    4..n   appendValue(ChronoField.YEAR_OF_ERA, n, 19, SignStyle.EXCEEDS_PAD);
     *    Y       1      append special localized WeekFields element for numeric week-based-year
     *    YY      2      append special localized WeekFields element for reduced numeric week-based-year 2 digits;
     *    YYY     3      append special localized WeekFields element for numeric week-based-year (3, 19, SignStyle.NORMAL);
     *    Y..Y    4..n   append special localized WeekFields element for numeric week-based-year (n, 19, SignStyle.EXCEEDS_PAD);
     *
     *    Q       1      appendValue(IsoFields.QUARTER_OF_YEAR);
     *    QQ      2      appendValue(IsoFields.QUARTER_OF_YEAR, 2);
     *    QQQ     3      appendText(IsoFields.QUARTER_OF_YEAR, TextStyle.SHORT)
     *    QQQQ    4      appendText(IsoFields.QUARTER_OF_YEAR, TextStyle.FULL)
     *    QQQQQ   5      appendText(IsoFields.QUARTER_OF_YEAR, TextStyle.NARROW)
     *    q       1      appendValue(IsoFields.QUARTER_OF_YEAR);
     *    qq      2      appendValue(IsoFields.QUARTER_OF_YEAR, 2);
     *    qqq     3      appendText(IsoFields.QUARTER_OF_YEAR, TextStyle.SHORT_STANDALONE)
     *    qqqq    4      appendText(IsoFields.QUARTER_OF_YEAR, TextStyle.FULL_STANDALONE)
     *    qqqqq   5      appendText(IsoFields.QUARTER_OF_YEAR, TextStyle.NARROW_STANDALONE)
     *
     *    M       1      appendValue(ChronoField.MONTH_OF_YEAR);
     *    MM      2      appendValue(ChronoField.MONTH_OF_YEAR, 2);
     *    MMM     3      appendText(ChronoField.MONTH_OF_YEAR, TextStyle.SHORT)
     *    MMMM    4      appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL)
     *    MMMMM   5      appendText(ChronoField.MONTH_OF_YEAR, TextStyle.NARROW)
     *    L       1      appendValue(ChronoField.MONTH_OF_YEAR);
     *    LL      2      appendValue(ChronoField.MONTH_OF_YEAR, 2);
     *    LLL     3      appendText(ChronoField.MONTH_OF_YEAR, TextStyle.SHORT_STANDALONE)
     *    LLLL    4      appendText(ChronoField.MONTH_OF_YEAR, TextStyle.FULL_STANDALONE)
     *    LLLLL   5      appendText(ChronoField.MONTH_OF_YEAR, TextStyle.NARROW_STANDALONE)
     *
     *    w       1      append special localized WeekFields element for numeric week-of-year
     *    ww      1      append special localized WeekFields element for numeric week-of-year, zero-padded
     *    W       1      append special localized WeekFields element for numeric week-of-month
     *    d       1      appendValue(ChronoField.DAY_OF_MONTH)
     *    dd      2      appendValue(ChronoField.DAY_OF_MONTH, 2)
     *    D       1      appendValue(ChronoField.DAY_OF_YEAR)
     *    DD      2      appendValue(ChronoField.DAY_OF_YEAR, 2)
     *    DDD     3      appendValue(ChronoField.DAY_OF_YEAR, 3)
     *    F       1      appendValue(ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH)
     *    E       1      appendText(ChronoField.DAY_OF_WEEK, TextStyle.SHORT)
     *    EE      2      appendText(ChronoField.DAY_OF_WEEK, TextStyle.SHORT)
     *    EEE     3      appendText(ChronoField.DAY_OF_WEEK, TextStyle.SHORT)
     *    EEEE    4      appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL)
     *    EEEEE   5      appendText(ChronoField.DAY_OF_WEEK, TextStyle.NARROW)
     *    e       1      append special localized WeekFields element for numeric day-of-week
     *    ee      2      append special localized WeekFields element for numeric day-of-week, zero-padded
     *    eee     3      appendText(ChronoField.DAY_OF_WEEK, TextStyle.SHORT)
     *    eeee    4      appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL)
     *    eeeee   5      appendText(ChronoField.DAY_OF_WEEK, TextStyle.NARROW)
     *    c       1      append special localized WeekFields element for numeric day-of-week
     *    ccc     3      appendText(ChronoField.DAY_OF_WEEK, TextStyle.SHORT_STANDALONE)
     *    cccc    4      appendText(ChronoField.DAY_OF_WEEK, TextStyle.FULL_STANDALONE)
     *    ccccc   5      appendText(ChronoField.DAY_OF_WEEK, TextStyle.NARROW_STANDALONE)
     * </pre>
     * <p>
     * <b>Time fields</b>: Pattern letters to output a time.
     * <pre>
     *  Pattern  Count  Equivalent builder methods
     *  -------  -----  --------------------------
     *    a       1      appendText(ChronoField.AMPM_OF_DAY, TextStyle.SHORT)
     *    h       1      appendValue(ChronoField.CLOCK_HOUR_OF_AMPM)
     *    hh      2      appendValue(ChronoField.CLOCK_HOUR_OF_AMPM, 2)
     *    H       1      appendValue(ChronoField.HOUR_OF_DAY)
     *    HH      2      appendValue(ChronoField.HOUR_OF_DAY, 2)
     *    k       1      appendValue(ChronoField.CLOCK_HOUR_OF_DAY)
     *    kk      2      appendValue(ChronoField.CLOCK_HOUR_OF_DAY, 2)
     *    K       1      appendValue(ChronoField.HOUR_OF_AMPM)
     *    KK      2      appendValue(ChronoField.HOUR_OF_AMPM, 2)
     *    m       1      appendValue(ChronoField.MINUTE_OF_HOUR)
     *    mm      2      appendValue(ChronoField.MINUTE_OF_HOUR, 2)
     *    s       1      appendValue(ChronoField.SECOND_OF_MINUTE)
     *    ss      2      appendValue(ChronoField.SECOND_OF_MINUTE, 2)
     *
     *    S..S    1..n   appendFraction(ChronoField.NANO_OF_SECOND, n, n, false)
     *    A       1      appendValue(ChronoField.MILLI_OF_DAY)
     *    A..A    2..n   appendValue(ChronoField.MILLI_OF_DAY, n)
     *    n       1      appendValue(ChronoField.NANO_OF_SECOND)
     *    n..n    2..n   appendValue(ChronoField.NANO_OF_SECOND, n)
     *    N       1      appendValue(ChronoField.NANO_OF_DAY)
     *    N..N    2..n   appendValue(ChronoField.NANO_OF_DAY, n)
     * </pre>
     * <p>
     * <b>Zone ID</b>: Pattern letters to output {@code ZoneId}.
     * <pre>
     *  Pattern  Count  Equivalent builder methods
     *  -------  -----  --------------------------
     *    VV      2      appendZoneId()
     *    z       1      appendZoneText(TextStyle.SHORT)
     *    zz      2      appendZoneText(TextStyle.SHORT)
     *    zzz     3      appendZoneText(TextStyle.SHORT)
     *    zzzz    4      appendZoneText(TextStyle.FULL)
     * </pre>
     * <p>
     * <b>Zone offset</b>: Pattern letters to output {@code ZoneOffset}.
     * <pre>
     *  Pattern  Count  Equivalent builder methods
     *  -------  -----  --------------------------
     *    O       1      appendLocalizedOffsetPrefixed(TextStyle.SHORT);
     *    OOOO    4      appendLocalizedOffsetPrefixed(TextStyle.FULL);
     *    X       1      appendOffset("+HHmm","Z")
     *    XX      2      appendOffset("+HHMM","Z")
     *    XXX     3      appendOffset("+HH:MM","Z")
     *    XXXX    4      appendOffset("+HHMMss","Z")
     *    XXXXX   5      appendOffset("+HH:MM:ss","Z")
     *    x       1      appendOffset("+HHmm","+00")
     *    xx      2      appendOffset("+HHMM","+0000")
     *    xxx     3      appendOffset("+HH:MM","+00:00")
     *    xxxx    4      appendOffset("+HHMMss","+0000")
     *    xxxxx   5      appendOffset("+HH:MM:ss","+00:00")
     *    Z       1      appendOffset("+HHMM","+0000")
     *    ZZ      2      appendOffset("+HHMM","+0000")
     *    ZZZ     3      appendOffset("+HHMM","+0000")
     *    ZZZZ    4      appendLocalizedOffset(TextStyle.FULL);
     *    ZZZZZ   5      appendOffset("+HH:MM:ss","Z")
     * </pre>
     * <p>
     * <b>Modifiers</b>: Pattern letters that modify the rest of the pattern:
     * <pre>
     *  Pattern  Count  Equivalent builder methods
     *  -------  -----  --------------------------
     *    [       1      optionalStart()
     *    ]       1      optionalEnd()
     *    p..p    1..n   padNext(n)
     * </pre>
     * <p>
     * Any sequence of letters not specified above, unrecognized letter or
     * reserved character will throw an exception.
     * Future versions may add to the set of patterns.
     * It is recommended to use single quotes around all characters that you want
     * to output directly to ensure that future changes do not break your application.
     * <p>
     * Note that the pattern string is similar, but not identical, to
     * {@link java.text.SimpleDateFormat SimpleDateFormat}.
     * The pattern string is also similar, but not identical, to that defined by the
     * Unicode Common Locale Data Repository (CLDR/LDML).
     * Pattern letters 'X' and 'u' are aligned with Unicode CLDR/LDML.
     * By contrast, {@code SimpleDateFormat} uses 'u' for the numeric day of week.
     * Pattern letters 'y' and 'Y' parse years of two digits and more than 4 digits differently.
     * Pattern letters 'n', 'A', 'N', and 'p' are added.
     * Number types will reject large numbers.
     *
     * <p>
     *  将由指定模式定义的元素附加到构建器
     * <p>
     *  所有字母'A'到'Z'和'a'到'z'被保留为模式字母'#','{'和'}'被保留以备将来使用字符'['可选模式定义了以下模式字母：
     * <pre>
     * 符号含义表示示例------ ------- ------------ ------- G时代文本AD;公元; 2004年; 2004年04年; 04 D年份数189 M / L年份数字/文本7; 07
     * ; Jul;七月; J d每月第10天。
     * 
     * Q / q四分之一数字/文本3; 03; Q3;第三季度Y每周年度1996年; 96 w基于星期的年份数27 W星期几数字4 E星期几文本星期;星期二; T e / c本地化星期数/文本2; 02;星期
     * 二星期二; T F月份第3周。
     * 
     *  am-pm-of-day文本PM h clock-hour-of-am-pm(1-12)number 12 K hour-of-am-pm(0-11)number 0 k clock-of-am -p
     * m(1-24)数字0。
     * 
     * H小时(0-23)数0分钟小时数30秒秒数55 S第二分数部分978 A毫日数1234 n纳米 - 第二个数字987654321 N纳天数1234000000
     * 
     * V时区ID zone-id America / Los_Angeles; Z; -08：30 z时区名称区域名太平洋标准时间; PST O本地化区域偏移偏移-O GMT + 8; GMT + 08：00
     * ; UTC-08：00; X区偏移"Z"用于零偏移-X Z; -08; -0830; -08：30; -083015; -08：30：15; x zone-offset offset-x +0000; 
     * -08; -0830; -08：30; -083015; -08：30：15; Z zone-offset offset-Z +0000; -0800; -08：00;。
     * 
     *  p垫下一垫修改器1
     * 
     * '文本分隔符的转义''单引号文字'[可选部分开始]可选部分结束#保留供将来使用{保留供未来使用}保留供将来使用
     * </pre>
     * <p>
     *  模式字母计数确定格式有关模式的以用户为中心的说明,请参见<a href=\"DateTimeFormatterhtml#patterns\"> DateTimeFormatter </a>以下表格定义
     * 模式字母如何映射到构建器。
     * <p>
     *  <b>日期字段</b>：用于输出日期的花样字母
     * <pre>
     * 模式计数等效构建器方法------- ----- -------------------------- G 1 appendText(ChronoFieldERA,TextStyleSHORT )GG 
     * 2 appendText(ChronoFieldERA,TextStyleSHORT)GGG 3 appendText(ChronoFieldERA,TextStyleSHORT)GGGG 4 appe
     * ndText(ChronoFieldERA,TextStyleFULL)GGGGG 5 appendText(ChronoFieldERA,TextStyleNARROW)。
     * 
     * u 1 appendValue(ChronoFieldYEAR,1,19,SignStyleNORMAL); uu 2 appendValueReduced(ChronoFieldYEAR,2,2000
     * ); uuu 3 appendValue(ChronoFieldYEAR,3,19,SignStyleNORMAL); uu 4n appendValue(ChronoFieldYEAR,n,19,Si
     * gnStyleEXCEEDS_PAD); y 1 appendValue(ChronoFieldYEAR_OF_ERA,1,19,SignStyleNORMAL); yy 2 appendValueRe
     * duced(ChronoFieldYEAR_OF_ERA,2,2000); yyy 3 appendValue(ChronoFieldYEAR_OF_ERA,3,19,SignStyleNORMAL);
     *  yy 4n appendValue(ChronoFieldYEAR_OF_ERA,n,19,SignStyleEXCEEDS_PAD); Y 1为基于数字周的年份YY附加特殊的本地化WeekField
     * s元素附加特殊的本地化WeekFields元素用于减少的基于数字周的年份2位数字; YYY 3为基于数字周的年(3,19,SignStyleNORMAL)附加特殊的本地化WeekFields元素; YY
     *  4n为数字基于周的年(n,19,SignStyleEXCEEDS_PAD)附加特殊的本地化WeekFields元素;。
     * 
     * Q 1 appendValue(IsoFieldsQUARTER_OF_YEAR); QQ 2 appendValue(IsoFieldsQUARTER_OF_YEAR,2); QQQ 3 append
     * Text(IsoFieldsQUARTER_OF_YEAR,TextStyleSHORT)QQQQ 4 appendText(IsoFieldsQUARTER_OF_YEAR,TextStyleFULL
     * )QQQQQ 5 appendText(IsoFieldsQUARTER_OF_YEAR,TextStyleNARROW)q 1 appendValue(IsoFieldsQUARTER_OF_YEAR
     * ); qq 2 appendValue(IsoFieldsQUARTER_OF_YEAR,2); qqq 3 appendText(IsoFieldsQUARTER_OF_YEAR,TextStyleS
     * HORT_STANDALONE)qqqq 4 appendText(IsoFieldsQUARTER_OF_YEAR,TextStyleFULL_STANDALONE)qqqqq 5 appendTex
     * t(IsoFieldsQUARTER_OF_YEAR,TextStyleNARROW_STANDALONE)。
     * 
     * M 1 appendValue(ChronoFieldMONTH_OF_YEAR); MM 2 appendValue(ChronoFieldMONTH_OF_YEAR,2); MMM 3 append
     * Text(ChronoFieldMONTH_OF_YEAR,TextStyleSHORT)MMMM 4 appendText(ChronoFieldMONTH_OF_YEAR,TextStyleFULL
     * )MMMMM 5 appendText(ChronoFieldMONTH_OF_YEAR,TextStyleNARROW)L 1 appendValue(ChronoFieldMONTH_OF_YEAR
     * ); LL 2 appendValue(ChronoFieldMONTH_OF_YEAR,2); LLL 3 appendText(ChronoFieldMONTH_OF_YEAR,TextStyleS
     * HORT_STANDALONE)LLLL 4 appendText(ChronoFieldMONTH_OF_YEAR,TextStyleFULL_STANDALONE)LLLLL 5 appendTex
     * t(ChronoFieldMONTH_OF_YEAR,TextStyleNARROW_STANDALONE)。
     * 
     * w 1为数字周年ww附加特别的本地化WeekFields元素1为数字周年,零填充W 1附加特别的本地化WeekFields元素附加特定的本地化WeekFields元素用于数字周的月d 1 appendV
     * alue ChronoFieldDAY_OF_MONTH)DD 2 appendValue(ChronoFieldDAY_OF_MONTH,2)D 1 appendValue(ChronoFieldDA
     * Y_OF_YEAR)DD 2 appendValue(ChronoFieldDAY_OF_YEAR,2)DDD 3 appendValue(ChronoFieldDAY_OF_YEAR,3)的F 1 a
     * ppendValue(ChronoFieldALIGNED_DAY_OF_WEEK_IN_MONTH)电子1 AppendText通过(ChronoFieldDAY_OF_WEEK,TextStyleS
     * HORT)EE 2 AppendText通过( ChronoFieldDAY_OF_WEEK,TextStyleSHORT)EEE 3 appendText(ChronoFieldDAY_OF_WEEK
     * ,TextStyleSHORT)EEEE 4 appendText(ChronoFieldDAY_OF_WEEK,TextStyleFULL)EEEEE 5 appendText(ChronoField
     * DAY_OF_WEEK,TextStyleNARROW)e 1为数字day-of-week ee追加特别的本地化WeekFields元素2为数字day-of-week, EEE 3 AppendText
     * 通过(ChronoFieldDAY_OF_WEEK,TextStyleSHORT)EEEE 4 AppendText通过(ChronoFieldDAY_OF_WEEK,TextStyleFULL)EEE
     * EE 5 AppendText通过(ChronoFieldDAY_OF_WEEK,TextStyleNARROW)对于C 1追加特殊的本地化WeekFields元数值一天的一周CCC 3 AppendT
     * ext通过(ChronoFieldDAY_OF_WEEK,TextStyleSHORT_STANDALONE)CCCC 4 AppendText通过( ChronoFieldDAY_OF_WEEK,Te
     * xtStyleFULL_STANDALONE)ccccc 5 appendText(ChronoFieldDAY_OF_WEEK,TextStyleNARROW_STANDALONE)。
     * </pre>
     * <p>
     * <b>时间字段</b>：输出时间的花样字母
     * <pre>
     * 模式计数等效构建器方法------- ----- -------------------------- 1 appendText(ChronoFieldAMPM_OF_DAY,TextStyleSHOR
     * T )H 1 appendValue(ChronoFieldCLOCK_HOUR_OF_AMPM)HH 2 appendValue(ChronoFieldCLOCK_HOUR_OF_AMPM,2)H 1
     *  appendValue(ChronoFieldHOUR_OF_DAY)HH 2 appendValue(ChronoFieldHOUR_OF_DAY,2)K 1 appendValue(ChronoF
     * ieldCLOCK_HOUR_OF_DAY)KK 2 appendValue(ChronoFieldCLOCK_HOUR_OF_DAY,2)K 1 appendValue(ChronoFieldHOUR
     * _OF_AMPM)KK 2 appendValue(ChronoFieldHOUR_OF_AMPM,2)m 1 appendValue(ChronoFieldMINUTE_OF_HOUR)mm 2 ap
     * pendValue(ChronoFieldMINUTE_OF_HOUR,2)s 1 appendValue(ChronoFieldSECOND_OF_MINUTE)ss 2 appendValue(Ch
     * ronoFieldSECOND_OF_MINUTE,2)。
     * 
     * SS 1N appendFraction(ChronoFieldNANO_OF_SECOND,N,N,假)1 appendValue(ChronoFieldMILLI_OF_DAY)AA 2N appe
     * ndValue(ChronoFieldMILLI_OF_DAY,N)N 1 appendValue(ChronoFieldNANO_OF_SECOND)NN 2N appendValue(ChronoF
     * ieldNANO_OF_SECOND,N)N 1 appendValue(ChronoFieldNANO_OF_DAY)NN 2N appendValue( ChronoFieldNANO_OF_DAY
     * ,n)。
     * </pre>
     * <p>
     *  <b>区域ID </b>：输出样式字母{@code ZoneId}
     * <pre>
     *  模式计数等效构建器方法------- ----- -------------------------- VV 2 appendZoneId()z 1 appendZoneText(TextStyleS
     * HORT)zz 2 appendZoneText(TextStyleSHORT)zzz 3 appendZoneText(TextStyleSHORT)zzzz 4 appendZoneText(Tex
     * tStyleFULL)。
     * </pre>
     * <p>
     * <b>区域偏移</b>：输出图案字母{@code ZoneOffset}
     * <pre>
     * 模式计数等效构建器方法------- ----- -------------------------- O 1 appendLocalizedOffsetPrefixed(TextStyleSHORT)
     * ; OOOO 4 appendLocalizedOffsetPrefixed(TextStyle充分); X 1 appendOffset("+ HHmm","Z")XX 2 appendOffset(
     * "+ HHMM","Z")XXX 3 appendOffset("+ HH：MM","Z")XXXX 4 appendOffset("+ HHMMss" ","X       1      append
     * Offset(\"+HHmm\",\"Z\") XX      2      appendOffset(\"+HHMM\",\"Z\") XXX     3      appendOffset(\"+H
     * H:MM\",\"Z\") XXXX    4      appendOffset(\"+HHMMss\"Z")XXXXX 5 appendOffset("+ HH：MM：ss","Z")x 1 app
     * endOffset("+ HHmm","+ 00")xx 2 appendOffset("+ HHMM","+ 0000")xxx 3 appendOffset("+ HH：MM","+ 00:00")
     * xxxx 4 appendOffset("+ HHMMss","+ 0000")xxxxx 5 appendOffset("+ HH：MM：ss","+ 00:00" )Z 1 appendOffset
     * ("+ HHMM","+ 0000")ZZ 2 appendOffset("+ HHMM","+ 0000")ZZZ 3 appendOffset("+ HHMM","+ 0000")ZZZZ 4 ap
     * pendLocalizedOffset(TextStyleFULL); ZZZZZ 5 appendOffset("+ HH：MM：ss","Z")。
     * </pre>
     * <p>
     * <b>修饰符</b>：修改模式其余部分的模式字母：
     * <pre>
     *  模式计数等效构建器方法------- ----- -------------------------- [1 optionalStart()] 1 optionalEnd()pp 1n padNext
     * (n)。
     * </pre>
     * <p>
     *  上面未指定的任何字母序列,未识别的字母或保留字符将抛出异常未来版本可能会添加到模式集合中。建议在要直接输出的所有字符周围使用单引号,以确保未来的更改不会中断你的申请
     * <p>
     * 请注意,模式字符串与{@link javatextSimpleDateFormat SimpleDateFormat}类似,但不完全相同。
     * 模式字符串与Unicode通用区域设置数据存储库(CLDR / LDML)模式字母'X'定义的类似,但不完全相同,和'u'与Unicode CLDR / LDML对齐。
     * 相比之下,{@code SimpleDateFormat}对星期的数字使用'u'模式字母'y'和'Y'解析两位数字和多于4个数字的年份模式字母'n','A','N'和'p'被添加。
     * 数字类型将拒绝大数字。
     * 
     * 
     * @param pattern  the pattern to add, not null
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if the pattern is invalid
     */
    public DateTimeFormatterBuilder appendPattern(String pattern) {
        Objects.requireNonNull(pattern, "pattern");
        parsePattern(pattern);
        return this;
    }

    private void parsePattern(String pattern) {
        for (int pos = 0; pos < pattern.length(); pos++) {
            char cur = pattern.charAt(pos);
            if ((cur >= 'A' && cur <= 'Z') || (cur >= 'a' && cur <= 'z')) {
                int start = pos++;
                for ( ; pos < pattern.length() && pattern.charAt(pos) == cur; pos++);  // short loop
                int count = pos - start;
                // padding
                if (cur == 'p') {
                    int pad = 0;
                    if (pos < pattern.length()) {
                        cur = pattern.charAt(pos);
                        if ((cur >= 'A' && cur <= 'Z') || (cur >= 'a' && cur <= 'z')) {
                            pad = count;
                            start = pos++;
                            for ( ; pos < pattern.length() && pattern.charAt(pos) == cur; pos++);  // short loop
                            count = pos - start;
                        }
                    }
                    if (pad == 0) {
                        throw new IllegalArgumentException(
                                "Pad letter 'p' must be followed by valid pad pattern: " + pattern);
                    }
                    padNext(pad); // pad and continue parsing
                }
                // main rules
                TemporalField field = FIELD_MAP.get(cur);
                if (field != null) {
                    parseField(cur, count, field);
                } else if (cur == 'z') {
                    if (count > 4) {
                        throw new IllegalArgumentException("Too many pattern letters: " + cur);
                    } else if (count == 4) {
                        appendZoneText(TextStyle.FULL);
                    } else {
                        appendZoneText(TextStyle.SHORT);
                    }
                } else if (cur == 'V') {
                    if (count != 2) {
                        throw new IllegalArgumentException("Pattern letter count must be 2: " + cur);
                    }
                    appendZoneId();
                } else if (cur == 'Z') {
                    if (count < 4) {
                        appendOffset("+HHMM", "+0000");
                    } else if (count == 4) {
                        appendLocalizedOffset(TextStyle.FULL);
                    } else if (count == 5) {
                        appendOffset("+HH:MM:ss","Z");
                    } else {
                        throw new IllegalArgumentException("Too many pattern letters: " + cur);
                    }
                } else if (cur == 'O') {
                    if (count == 1) {
                        appendLocalizedOffset(TextStyle.SHORT);
                    } else if (count == 4) {
                        appendLocalizedOffset(TextStyle.FULL);
                    } else {
                        throw new IllegalArgumentException("Pattern letter count must be 1 or 4: " + cur);
                    }
                } else if (cur == 'X') {
                    if (count > 5) {
                        throw new IllegalArgumentException("Too many pattern letters: " + cur);
                    }
                    appendOffset(OffsetIdPrinterParser.PATTERNS[count + (count == 1 ? 0 : 1)], "Z");
                } else if (cur == 'x') {
                    if (count > 5) {
                        throw new IllegalArgumentException("Too many pattern letters: " + cur);
                    }
                    String zero = (count == 1 ? "+00" : (count % 2 == 0 ? "+0000" : "+00:00"));
                    appendOffset(OffsetIdPrinterParser.PATTERNS[count + (count == 1 ? 0 : 1)], zero);
                } else if (cur == 'W') {
                    // Fields defined by Locale
                    if (count > 1) {
                        throw new IllegalArgumentException("Too many pattern letters: " + cur);
                    }
                    appendInternal(new WeekBasedFieldPrinterParser(cur, count));
                } else if (cur == 'w') {
                    // Fields defined by Locale
                    if (count > 2) {
                        throw new IllegalArgumentException("Too many pattern letters: " + cur);
                    }
                    appendInternal(new WeekBasedFieldPrinterParser(cur, count));
                } else if (cur == 'Y') {
                    // Fields defined by Locale
                    appendInternal(new WeekBasedFieldPrinterParser(cur, count));
                } else {
                    throw new IllegalArgumentException("Unknown pattern letter: " + cur);
                }
                pos--;

            } else if (cur == '\'') {
                // parse literals
                int start = pos++;
                for ( ; pos < pattern.length(); pos++) {
                    if (pattern.charAt(pos) == '\'') {
                        if (pos + 1 < pattern.length() && pattern.charAt(pos + 1) == '\'') {
                            pos++;
                        } else {
                            break;  // end of literal
                        }
                    }
                }
                if (pos >= pattern.length()) {
                    throw new IllegalArgumentException("Pattern ends with an incomplete string literal: " + pattern);
                }
                String str = pattern.substring(start + 1, pos);
                if (str.length() == 0) {
                    appendLiteral('\'');
                } else {
                    appendLiteral(str.replace("''", "'"));
                }

            } else if (cur == '[') {
                optionalStart();

            } else if (cur == ']') {
                if (active.parent == null) {
                    throw new IllegalArgumentException("Pattern invalid as it contains ] without previous [");
                }
                optionalEnd();

            } else if (cur == '{' || cur == '}' || cur == '#') {
                throw new IllegalArgumentException("Pattern includes reserved character: '" + cur + "'");
            } else {
                appendLiteral(cur);
            }
        }
    }

    @SuppressWarnings("fallthrough")
    private void parseField(char cur, int count, TemporalField field) {
        boolean standalone = false;
        switch (cur) {
            case 'u':
            case 'y':
                if (count == 2) {
                    appendValueReduced(field, 2, 2, ReducedPrinterParser.BASE_DATE);
                } else if (count < 4) {
                    appendValue(field, count, 19, SignStyle.NORMAL);
                } else {
                    appendValue(field, count, 19, SignStyle.EXCEEDS_PAD);
                }
                break;
            case 'c':
                if (count == 2) {
                    throw new IllegalArgumentException("Invalid pattern \"cc\"");
                }
                /*fallthrough*/
            case 'L':
            case 'q':
                standalone = true;
                /*fallthrough*/
            case 'M':
            case 'Q':
            case 'E':
            case 'e':
                switch (count) {
                    case 1:
                    case 2:
                        if (cur == 'c' || cur == 'e') {
                            appendInternal(new WeekBasedFieldPrinterParser(cur, count));
                        } else if (cur == 'E') {
                            appendText(field, TextStyle.SHORT);
                        } else {
                            if (count == 1) {
                                appendValue(field);
                            } else {
                                appendValue(field, 2);
                            }
                        }
                        break;
                    case 3:
                        appendText(field, standalone ? TextStyle.SHORT_STANDALONE : TextStyle.SHORT);
                        break;
                    case 4:
                        appendText(field, standalone ? TextStyle.FULL_STANDALONE : TextStyle.FULL);
                        break;
                    case 5:
                        appendText(field, standalone ? TextStyle.NARROW_STANDALONE : TextStyle.NARROW);
                        break;
                    default:
                        throw new IllegalArgumentException("Too many pattern letters: " + cur);
                }
                break;
            case 'a':
                if (count == 1) {
                    appendText(field, TextStyle.SHORT);
                } else {
                    throw new IllegalArgumentException("Too many pattern letters: " + cur);
                }
                break;
            case 'G':
                switch (count) {
                    case 1:
                    case 2:
                    case 3:
                        appendText(field, TextStyle.SHORT);
                        break;
                    case 4:
                        appendText(field, TextStyle.FULL);
                        break;
                    case 5:
                        appendText(field, TextStyle.NARROW);
                        break;
                    default:
                        throw new IllegalArgumentException("Too many pattern letters: " + cur);
                }
                break;
            case 'S':
                appendFraction(NANO_OF_SECOND, count, count, false);
                break;
            case 'F':
                if (count == 1) {
                    appendValue(field);
                } else {
                    throw new IllegalArgumentException("Too many pattern letters: " + cur);
                }
                break;
            case 'd':
            case 'h':
            case 'H':
            case 'k':
            case 'K':
            case 'm':
            case 's':
                if (count == 1) {
                    appendValue(field);
                } else if (count == 2) {
                    appendValue(field, count);
                } else {
                    throw new IllegalArgumentException("Too many pattern letters: " + cur);
                }
                break;
            case 'D':
                if (count == 1) {
                    appendValue(field);
                } else if (count <= 3) {
                    appendValue(field, count);
                } else {
                    throw new IllegalArgumentException("Too many pattern letters: " + cur);
                }
                break;
            default:
                if (count == 1) {
                    appendValue(field);
                } else {
                    appendValue(field, count);
                }
                break;
        }
    }

    /** Map of letters to fields. */
    private static final Map<Character, TemporalField> FIELD_MAP = new HashMap<>();
    static {
        // SDF = SimpleDateFormat
        FIELD_MAP.put('G', ChronoField.ERA);                       // SDF, LDML (different to both for 1/2 chars)
        FIELD_MAP.put('y', ChronoField.YEAR_OF_ERA);               // SDF, LDML
        FIELD_MAP.put('u', ChronoField.YEAR);                      // LDML (different in SDF)
        FIELD_MAP.put('Q', IsoFields.QUARTER_OF_YEAR);             // LDML (removed quarter from 310)
        FIELD_MAP.put('q', IsoFields.QUARTER_OF_YEAR);             // LDML (stand-alone)
        FIELD_MAP.put('M', ChronoField.MONTH_OF_YEAR);             // SDF, LDML
        FIELD_MAP.put('L', ChronoField.MONTH_OF_YEAR);             // SDF, LDML (stand-alone)
        FIELD_MAP.put('D', ChronoField.DAY_OF_YEAR);               // SDF, LDML
        FIELD_MAP.put('d', ChronoField.DAY_OF_MONTH);              // SDF, LDML
        FIELD_MAP.put('F', ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH);  // SDF, LDML
        FIELD_MAP.put('E', ChronoField.DAY_OF_WEEK);               // SDF, LDML (different to both for 1/2 chars)
        FIELD_MAP.put('c', ChronoField.DAY_OF_WEEK);               // LDML (stand-alone)
        FIELD_MAP.put('e', ChronoField.DAY_OF_WEEK);               // LDML (needs localized week number)
        FIELD_MAP.put('a', ChronoField.AMPM_OF_DAY);               // SDF, LDML
        FIELD_MAP.put('H', ChronoField.HOUR_OF_DAY);               // SDF, LDML
        FIELD_MAP.put('k', ChronoField.CLOCK_HOUR_OF_DAY);         // SDF, LDML
        FIELD_MAP.put('K', ChronoField.HOUR_OF_AMPM);              // SDF, LDML
        FIELD_MAP.put('h', ChronoField.CLOCK_HOUR_OF_AMPM);        // SDF, LDML
        FIELD_MAP.put('m', ChronoField.MINUTE_OF_HOUR);            // SDF, LDML
        FIELD_MAP.put('s', ChronoField.SECOND_OF_MINUTE);          // SDF, LDML
        FIELD_MAP.put('S', ChronoField.NANO_OF_SECOND);            // LDML (SDF uses milli-of-second number)
        FIELD_MAP.put('A', ChronoField.MILLI_OF_DAY);              // LDML
        FIELD_MAP.put('n', ChronoField.NANO_OF_SECOND);            // 310 (proposed for LDML)
        FIELD_MAP.put('N', ChronoField.NANO_OF_DAY);               // 310 (proposed for LDML)
        // 310 - z - time-zone names, matches LDML and SimpleDateFormat 1 to 4
        // 310 - Z - matches SimpleDateFormat and LDML
        // 310 - V - time-zone id, matches LDML
        // 310 - p - prefix for padding
        // 310 - X - matches LDML, almost matches SDF for 1, exact match 2&3, extended 4&5
        // 310 - x - matches LDML
        // 310 - w, W, and Y are localized forms matching LDML
        // LDML - U - cycle year name, not supported by 310 yet
        // LDML - l - deprecated
        // LDML - j - not relevant
        // LDML - g - modified-julian-day
        // LDML - v,V - extended time-zone names
    }

    //-----------------------------------------------------------------------
    /**
     * Causes the next added printer/parser to pad to a fixed width using a space.
     * <p>
     * This padding will pad to a fixed width using spaces.
     * <p>
     * During formatting, the decorated element will be output and then padded
     * to the specified width. An exception will be thrown during formatting if
     * the pad width is exceeded.
     * <p>
     * During parsing, the padding and decorated element are parsed.
     * If parsing is lenient, then the pad width is treated as a maximum.
     * If parsing is case insensitive, then the pad character is matched ignoring case.
     * The padding is parsed greedily. Thus, if the decorated element starts with
     * the pad character, it will not be parsed.
     *
     * <p>
     *  使下一个添加的打印机/解析器使用空格填充固定宽度
     * <p>
     *  此填充将使用空格填充到固定宽度
     * <p>
     * 在格式化期间,装饰元素将被输出,然后被填充到指定的宽度如果超过焊盘宽度,格式化期间将抛出异常
     * <p>
     *  在解析期间,解析填充和修饰元素如果解析宽松,则填充宽度被视为最大值如果解析是不区分大小写,则填充字符匹配忽略大小写填充解析贪婪因此,如果装饰元素开始用pad字符,它不会被解析
     * 
     * 
     * @param padWidth  the pad width, 1 or greater
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if pad width is too small
     */
    public DateTimeFormatterBuilder padNext(int padWidth) {
        return padNext(padWidth, ' ');
    }

    /**
     * Causes the next added printer/parser to pad to a fixed width.
     * <p>
     * This padding is intended for padding other than zero-padding.
     * Zero-padding should be achieved using the appendValue methods.
     * <p>
     * During formatting, the decorated element will be output and then padded
     * to the specified width. An exception will be thrown during formatting if
     * the pad width is exceeded.
     * <p>
     * During parsing, the padding and decorated element are parsed.
     * If parsing is lenient, then the pad width is treated as a maximum.
     * If parsing is case insensitive, then the pad character is matched ignoring case.
     * The padding is parsed greedily. Thus, if the decorated element starts with
     * the pad character, it will not be parsed.
     *
     * <p>
     *  使下一个添加的打印机/解析器以固定宽度进行打印
     * <p>
     *  这个填充用于填充而不是填零填零填充应该使用appendValue方法实现
     * <p>
     * 在格式化期间,装饰元素将被输出,然后被填充到指定的宽度如果超过焊盘宽度,格式化期间将抛出异常
     * <p>
     *  在解析期间,解析填充和修饰元素如果解析宽松,则填充宽度被视为最大值如果解析是不区分大小写,则填充字符匹配忽略大小写填充解析贪婪因此,如果装饰元素开始用pad字符,它不会被解析
     * 
     * 
     * @param padWidth  the pad width, 1 or greater
     * @param padChar  the pad character
     * @return this, for chaining, not null
     * @throws IllegalArgumentException if pad width is too small
     */
    public DateTimeFormatterBuilder padNext(int padWidth, char padChar) {
        if (padWidth < 1) {
            throw new IllegalArgumentException("The pad width must be at least one but was " + padWidth);
        }
        active.padNextWidth = padWidth;
        active.padNextChar = padChar;
        active.valueParserIndex = -1;
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Mark the start of an optional section.
     * <p>
     * The output of formatting can include optional sections, which may be nested.
     * An optional section is started by calling this method and ended by calling
     * {@link #optionalEnd()} or by ending the build process.
     * <p>
     * All elements in the optional section are treated as optional.
     * During formatting, the section is only output if data is available in the
     * {@code TemporalAccessor} for all the elements in the section.
     * During parsing, the whole section may be missing from the parsed string.
     * <p>
     * For example, consider a builder setup as
     * {@code builder.appendValue(HOUR_OF_DAY,2).optionalStart().appendValue(MINUTE_OF_HOUR,2)}.
     * The optional section ends automatically at the end of the builder.
     * During formatting, the minute will only be output if its value can be obtained from the date-time.
     * During parsing, the input will be successfully parsed whether the minute is present or not.
     *
     * <p>
     *  标记可选部分的开始
     * <p>
     *  格式化的输出可以包括可选的节,可以嵌套可选节通过调用此方法启动,并通过调用{@link #optionalEnd()}或结束构建过程结束
     * <p>
     * 可选部分中的所有元素都被视为可选在格式化期间,只有在{@code TemporalAccessor}中的所有元素的数据可用时,才会输出该部分。在解析期间,解析字符串中可能缺少整个部分
     * <p>
     *  例如,将构建器设置视为{@code builderappendValue(HOUR_OF_DAY,2)optionalStart()appendValue(MINUTE_OF_HOUR,2)}可选部分
     * 在构建器结束时自动结束在格式化期间,仅当其值可以从日期时间获得在解析期间,输入将成功解析是否存在分钟。
     * 
     * 
     * @return this, for chaining, not null
     */
    public DateTimeFormatterBuilder optionalStart() {
        active.valueParserIndex = -1;
        active = new DateTimeFormatterBuilder(active, true);
        return this;
    }

    /**
     * Ends an optional section.
     * <p>
     * The output of formatting can include optional sections, which may be nested.
     * An optional section is started by calling {@link #optionalStart()} and ended
     * using this method (or at the end of the builder).
     * <p>
     * Calling this method without having previously called {@code optionalStart}
     * will throw an exception.
     * Calling this method immediately after calling {@code optionalStart} has no effect
     * on the formatter other than ending the (empty) optional section.
     * <p>
     * All elements in the optional section are treated as optional.
     * During formatting, the section is only output if data is available in the
     * {@code TemporalAccessor} for all the elements in the section.
     * During parsing, the whole section may be missing from the parsed string.
     * <p>
     * For example, consider a builder setup as
     * {@code builder.appendValue(HOUR_OF_DAY,2).optionalStart().appendValue(MINUTE_OF_HOUR,2).optionalEnd()}.
     * During formatting, the minute will only be output if its value can be obtained from the date-time.
     * During parsing, the input will be successfully parsed whether the minute is present or not.
     *
     * <p>
     *  结束可选部分
     * <p>
     * 格式化的输出可以包括可以嵌套的可选部分可选部分通过调用{@link #optionalStart()}启动,并使用此方法结束(或在构建器的结尾)
     * <p>
     *  在没有事先调用{@code optionalStart}的情况下调用此方法将抛出异常在调用{@code optionalStart}之后立即调用此方法对格式化程序没有影响,而是结束(空)可选部分
     * <p>
     *  可选部分中的所有元素都被视为可选在格式化期间,只有在{@code TemporalAccessor}中的所有元素的数据可用时,才会输出该部分。在解析期间,解析字符串中可能缺少整个部分
     * <p>
     * 例如,将构建器设置视为{@code builderappendValue(HOUR_OF_DAY,2)optionalStart()appendValue(MINUTE_OF_HOUR,2)optionalEnd()}
     * 在格式化期间,只有当其值可以从日期时间在解析期间,无论分钟是否存在,输入都将成功解析。
     * 
     * 
     * @return this, for chaining, not null
     * @throws IllegalStateException if there was no previous call to {@code optionalStart}
     */
    public DateTimeFormatterBuilder optionalEnd() {
        if (active.parent == null) {
            throw new IllegalStateException("Cannot call optionalEnd() as there was no previous call to optionalStart()");
        }
        if (active.printerParsers.size() > 0) {
            CompositePrinterParser cpp = new CompositePrinterParser(active.printerParsers, active.optional);
            active = active.parent;
            appendInternal(cpp);
        } else {
            active = active.parent;
        }
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Appends a printer and/or parser to the internal list handling padding.
     *
     * <p>
     *  将打印机和/或解析器附加到内部列表处理填充
     * 
     * 
     * @param pp  the printer-parser to add, not null
     * @return the index into the active parsers list
     */
    private int appendInternal(DateTimePrinterParser pp) {
        Objects.requireNonNull(pp, "pp");
        if (active.padNextWidth > 0) {
            if (pp != null) {
                pp = new PadPrinterParserDecorator(pp, active.padNextWidth, active.padNextChar);
            }
            active.padNextWidth = 0;
            active.padNextChar = 0;
        }
        active.printerParsers.add(pp);
        active.valueParserIndex = -1;
        return active.printerParsers.size() - 1;
    }

    //-----------------------------------------------------------------------
    /**
     * Completes this builder by creating the {@code DateTimeFormatter}
     * using the default locale.
     * <p>
     * This will create a formatter with the {@linkplain Locale#getDefault(Locale.Category) default FORMAT locale}.
     * Numbers will be printed and parsed using the standard DecimalStyle.
     * The resolver style will be {@link ResolverStyle#SMART SMART}.
     * <p>
     * Calling this method will end any open optional sections by repeatedly
     * calling {@link #optionalEnd()} before creating the formatter.
     * <p>
     * This builder can still be used after creating the formatter if desired,
     * although the state may have been changed by calls to {@code optionalEnd}.
     *
     * <p>
     *  通过使用默认语言环境创建{@code DateTimeFormatter}来完成此构建器
     * <p>
     *  这将创建一个带有{@linkplain Locale#getDefault(LocaleCategory)默认FORMAT语言环境}的格式化器。
     * 将使用标准DecimalStyle打印和解析数字解析器样式将是{@link ResolverStyle#SMART SMART}。
     * <p>
     * 调用此方法将在创建格式化程序之前重复调用{@link #optionalEnd()},从而结束任何打开的可选部分
     * <p>
     *  如果需要,此构建器仍然可以在创建格式化程序后使用,但状态可能已通过调用{@code optionalEnd}
     * 
     * 
     * @return the created formatter, not null
     */
    public DateTimeFormatter toFormatter() {
        return toFormatter(Locale.getDefault(Locale.Category.FORMAT));
    }

    /**
     * Completes this builder by creating the {@code DateTimeFormatter}
     * using the specified locale.
     * <p>
     * This will create a formatter with the specified locale.
     * Numbers will be printed and parsed using the standard DecimalStyle.
     * The resolver style will be {@link ResolverStyle#SMART SMART}.
     * <p>
     * Calling this method will end any open optional sections by repeatedly
     * calling {@link #optionalEnd()} before creating the formatter.
     * <p>
     * This builder can still be used after creating the formatter if desired,
     * although the state may have been changed by calls to {@code optionalEnd}.
     *
     * <p>
     *  通过使用指定的区域设置创建{@code DateTimeFormatter}来完成此构建器
     * <p>
     *  这将创建一个具有指定区域设置的格式化程序将使用标准DecimalStyle打印和解析数字解析器样式将是{@link ResolverStyle#SMART SMART}
     * <p>
     *  调用此方法将在创建格式化程序之前重复调用{@link #optionalEnd()},从而结束任何打开的可选部分
     * <p>
     * 如果需要,此构建器仍然可以在创建格式化程序后使用,但状态可能已通过调用{@code optionalEnd}
     * 
     * 
     * @param locale  the locale to use for formatting, not null
     * @return the created formatter, not null
     */
    public DateTimeFormatter toFormatter(Locale locale) {
        return toFormatter(locale, ResolverStyle.SMART, null);
    }

    /**
     * Completes this builder by creating the formatter.
     * This uses the default locale.
     *
     * <p>
     *  通过创建格式化程序来完成此构建器这将使用缺省区域设置
     * 
     * 
     * @param resolverStyle  the resolver style to use, not null
     * @return the created formatter, not null
     */
    DateTimeFormatter toFormatter(ResolverStyle resolverStyle, Chronology chrono) {
        return toFormatter(Locale.getDefault(Locale.Category.FORMAT), resolverStyle, chrono);
    }

    /**
     * Completes this builder by creating the formatter.
     *
     * <p>
     *  通过创建格式化程序完成此构建器
     * 
     * 
     * @param locale  the locale to use for formatting, not null
     * @param chrono  the chronology to use, may be null
     * @return the created formatter, not null
     */
    private DateTimeFormatter toFormatter(Locale locale, ResolverStyle resolverStyle, Chronology chrono) {
        Objects.requireNonNull(locale, "locale");
        while (active.parent != null) {
            optionalEnd();
        }
        CompositePrinterParser pp = new CompositePrinterParser(printerParsers, false);
        return new DateTimeFormatter(pp, locale, DecimalStyle.STANDARD,
                resolverStyle, null, chrono, null);
    }

    //-----------------------------------------------------------------------
    /**
     * Strategy for formatting/parsing date-time information.
     * <p>
     * The printer may format any part, or the whole, of the input date-time object.
     * Typically, a complete format is constructed from a number of smaller
     * units, each outputting a single field.
     * <p>
     * The parser may parse any piece of text from the input, storing the result
     * in the context. Typically, each individual parser will just parse one
     * field, such as the day-of-month, storing the value in the context.
     * Once the parse is complete, the caller will then resolve the parsed values
     * to create the desired object, such as a {@code LocalDate}.
     * <p>
     * The parse position will be updated during the parse. Parsing will start at
     * the specified index and the return value specifies the new parse position
     * for the next parser. If an error occurs, the returned index will be negative
     * and will have the error position encoded using the complement operator.
     *
     * @implSpec
     * This interface must be implemented with care to ensure other classes operate correctly.
     * All implementations that can be instantiated must be final, immutable and thread-safe.
     * <p>
     * The context is not a thread-safe object and a new instance will be created
     * for each format that occurs. The context must not be stored in an instance
     * variable or shared with any other threads.
     * <p>
     *  格式/解析日期时间信息的策略
     * <p>
     *  打印机可以格式化输入日期时间对象的任何部分或整体。通常,完整格式由多个较小单元构成,每个单元输出单个字段
     * <p>
     * 解析器可以解析来自输入的任何文本块,将结果存储在上下文中。通常,每个单独的解析器将仅解析一个字段,例如一个月,将该值存储在上下文中。
     * 一旦解析完成,调用者将解析解析的值以创建所需的对象,例如{@code LocalDate}。
     * <p>
     *  解析位置将在解析期间更新,解析将从指定索引开始,返回值指定下一个解析器的新解析位置。如果发生错误,返回的索引将为负,并使用补码运算符
     * 
     * @implSpec此接口必须小心地实现,以确保其他类正常运行所有可实例化的实现必须是final,immutable和线程安全
     * <p>
     *  上下文不是线程安全的对象,并且将为每个发生的格式创建一个新的实例。上下文不能存储在实例变量中,也不能与任何其他线程共享
     * 
     */
    interface DateTimePrinterParser {

        /**
         * Prints the date-time object to the buffer.
         * <p>
         * The context holds information to use during the format.
         * It also contains the date-time information to be printed.
         * <p>
         * The buffer must not be mutated beyond the content controlled by the implementation.
         *
         * <p>
         *  将日期时间对象打印到缓冲区
         * <p>
         *  上下文保存要在格式期间使用的信息。它还包含要打印的日期时间信息
         * <p>
         *  缓冲区不能超出实现控制的内容
         * 
         * 
         * @param context  the context to format using, not null
         * @param buf  the buffer to append to, not null
         * @return false if unable to query the value from the date-time, true otherwise
         * @throws DateTimeException if the date-time cannot be printed successfully
         */
        boolean format(DateTimePrintContext context, StringBuilder buf);

        /**
         * Parses text into date-time information.
         * <p>
         * The context holds information to use during the parse.
         * It is also used to store the parsed date-time information.
         *
         * <p>
         *  将文本解析为日期时间信息
         * <p>
         *  上下文保存要在解析期间使用的信息。它还用于存储解析的日期时间信息
         * 
         * 
         * @param context  the context to use and parse into, not null
         * @param text  the input text to parse, not null
         * @param position  the position to start parsing at, from 0 to the text length
         * @return the new parse position, where negative means an error with the
         *  error position encoded using the complement ~ operator
         * @throws NullPointerException if the context or text is null
         * @throws IndexOutOfBoundsException if the position is invalid
         */
        int parse(DateTimeParseContext context, CharSequence text, int position);
    }

    //-----------------------------------------------------------------------
    /**
     * Composite printer and parser.
     * <p>
     * 复合打印机和解析器
     * 
     */
    static final class CompositePrinterParser implements DateTimePrinterParser {
        private final DateTimePrinterParser[] printerParsers;
        private final boolean optional;

        CompositePrinterParser(List<DateTimePrinterParser> printerParsers, boolean optional) {
            this(printerParsers.toArray(new DateTimePrinterParser[printerParsers.size()]), optional);
        }

        CompositePrinterParser(DateTimePrinterParser[] printerParsers, boolean optional) {
            this.printerParsers = printerParsers;
            this.optional = optional;
        }

        /**
         * Returns a copy of this printer-parser with the optional flag changed.
         *
         * <p>
         *  返回此打印机解析器的副本,其中可选标志已更改
         * 
         * 
         * @param optional  the optional flag to set in the copy
         * @return the new printer-parser, not null
         */
        public CompositePrinterParser withOptional(boolean optional) {
            if (optional == this.optional) {
                return this;
            }
            return new CompositePrinterParser(printerParsers, optional);
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            int length = buf.length();
            if (optional) {
                context.startOptional();
            }
            try {
                for (DateTimePrinterParser pp : printerParsers) {
                    if (pp.format(context, buf) == false) {
                        buf.setLength(length);  // reset buffer
                        return true;
                    }
                }
            } finally {
                if (optional) {
                    context.endOptional();
                }
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            if (optional) {
                context.startOptional();
                int pos = position;
                for (DateTimePrinterParser pp : printerParsers) {
                    pos = pp.parse(context, text, pos);
                    if (pos < 0) {
                        context.endOptional(false);
                        return position;  // return original position
                    }
                }
                context.endOptional(true);
                return pos;
            } else {
                for (DateTimePrinterParser pp : printerParsers) {
                    position = pp.parse(context, text, position);
                    if (position < 0) {
                        break;
                    }
                }
                return position;
            }
        }

        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder();
            if (printerParsers != null) {
                buf.append(optional ? "[" : "(");
                for (DateTimePrinterParser pp : printerParsers) {
                    buf.append(pp);
                }
                buf.append(optional ? "]" : ")");
            }
            return buf.toString();
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Pads the output to a fixed width.
     * <p>
     *  将输出填充到固定宽度
     * 
     */
    static final class PadPrinterParserDecorator implements DateTimePrinterParser {
        private final DateTimePrinterParser printerParser;
        private final int padWidth;
        private final char padChar;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param printerParser  the printer, not null
         * @param padWidth  the width to pad to, 1 or greater
         * @param padChar  the pad character
         */
        PadPrinterParserDecorator(DateTimePrinterParser printerParser, int padWidth, char padChar) {
            // input checked by DateTimeFormatterBuilder
            this.printerParser = printerParser;
            this.padWidth = padWidth;
            this.padChar = padChar;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            int preLen = buf.length();
            if (printerParser.format(context, buf) == false) {
                return false;
            }
            int len = buf.length() - preLen;
            if (len > padWidth) {
                throw new DateTimeException(
                    "Cannot print as output of " + len + " characters exceeds pad width of " + padWidth);
            }
            for (int i = 0; i < padWidth - len; i++) {
                buf.insert(preLen, padChar);
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            // cache context before changed by decorated parser
            final boolean strict = context.isStrict();
            // parse
            if (position > text.length()) {
                throw new IndexOutOfBoundsException();
            }
            if (position == text.length()) {
                return ~position;  // no more characters in the string
            }
            int endPos = position + padWidth;
            if (endPos > text.length()) {
                if (strict) {
                    return ~position;  // not enough characters in the string to meet the parse width
                }
                endPos = text.length();
            }
            int pos = position;
            while (pos < endPos && context.charEquals(text.charAt(pos), padChar)) {
                pos++;
            }
            text = text.subSequence(0, endPos);
            int resultPos = printerParser.parse(context, text, pos);
            if (resultPos != endPos && strict) {
                return ~(position + pos);  // parse of decorated field didn't parse to the end
            }
            return resultPos;
        }

        @Override
        public String toString() {
            return "Pad(" + printerParser + "," + padWidth + (padChar == ' ' ? ")" : ",'" + padChar + "')");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Enumeration to apply simple parse settings.
     * <p>
     *  枚举应用简单的解析设置
     * 
     */
    static enum SettingsParser implements DateTimePrinterParser {
        SENSITIVE,
        INSENSITIVE,
        STRICT,
        LENIENT;

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            return true;  // nothing to do here
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            // using ordinals to avoid javac synthetic inner class
            switch (ordinal()) {
                case 0: context.setCaseSensitive(true); break;
                case 1: context.setCaseSensitive(false); break;
                case 2: context.setStrict(true); break;
                case 3: context.setStrict(false); break;
            }
            return position;
        }

        @Override
        public String toString() {
            // using ordinals to avoid javac synthetic inner class
            switch (ordinal()) {
                case 0: return "ParseCaseSensitive(true)";
                case 1: return "ParseCaseSensitive(false)";
                case 2: return "ParseStrict(true)";
                case 3: return "ParseStrict(false)";
            }
            throw new IllegalStateException("Unreachable");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Defaults a value into the parse if not currently present.
     * <p>
     *  将一个值默认为解析(如果当前不存在)
     * 
     */
    static class DefaultValueParser implements DateTimePrinterParser {
        private final TemporalField field;
        private final long value;

        DefaultValueParser(TemporalField field, long value) {
            this.field = field;
            this.value = value;
        }

        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            return true;
        }

        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            if (context.getParsed(field) == null) {
                context.setParsedField(field, value, position, position);
            }
            return position;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses a character literal.
     * <p>
     *  打印或解析字符文字
     * 
     */
    static final class CharLiteralPrinterParser implements DateTimePrinterParser {
        private final char literal;

        CharLiteralPrinterParser(char literal) {
            this.literal = literal;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            buf.append(literal);
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            int length = text.length();
            if (position == length) {
                return ~position;
            }
            char ch = text.charAt(position);
            if (ch != literal) {
                if (context.isCaseSensitive() ||
                        (Character.toUpperCase(ch) != Character.toUpperCase(literal) &&
                         Character.toLowerCase(ch) != Character.toLowerCase(literal))) {
                    return ~position;
                }
            }
            return position + 1;
        }

        @Override
        public String toString() {
            if (literal == '\'') {
                return "''";
            }
            return "'" + literal + "'";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses a string literal.
     * <p>
     *  打印或解析字符串文字
     * 
     */
    static final class StringLiteralPrinterParser implements DateTimePrinterParser {
        private final String literal;

        StringLiteralPrinterParser(String literal) {
            this.literal = literal;  // validated by caller
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            buf.append(literal);
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            int length = text.length();
            if (position > length || position < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (context.subSequenceEquals(text, position, literal, 0, literal.length()) == false) {
                return ~position;
            }
            return position + literal.length();
        }

        @Override
        public String toString() {
            String converted = literal.replace("'", "''");
            return "'" + converted + "'";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints and parses a numeric date-time field with optional padding.
     * <p>
     *  打印并解析具有可选填充的数字日期时间字段
     * 
     */
    static class NumberPrinterParser implements DateTimePrinterParser {

        /**
         * Array of 10 to the power of n.
         * <p>
         *  数组10的幂的n
         * 
         */
        static final long[] EXCEED_POINTS = new long[] {
            0L,
            10L,
            100L,
            1000L,
            10000L,
            100000L,
            1000000L,
            10000000L,
            100000000L,
            1000000000L,
            10000000000L,
        };

        final TemporalField field;
        final int minWidth;
        final int maxWidth;
        private final SignStyle signStyle;
        final int subsequentWidth;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param field  the field to format, not null
         * @param minWidth  the minimum field width, from 1 to 19
         * @param maxWidth  the maximum field width, from minWidth to 19
         * @param signStyle  the positive/negative sign style, not null
         */
        NumberPrinterParser(TemporalField field, int minWidth, int maxWidth, SignStyle signStyle) {
            // validated by caller
            this.field = field;
            this.minWidth = minWidth;
            this.maxWidth = maxWidth;
            this.signStyle = signStyle;
            this.subsequentWidth = 0;
        }

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param field  the field to format, not null
         * @param minWidth  the minimum field width, from 1 to 19
         * @param maxWidth  the maximum field width, from minWidth to 19
         * @param signStyle  the positive/negative sign style, not null
         * @param subsequentWidth  the width of subsequent non-negative numbers, 0 or greater,
         *  -1 if fixed width due to active adjacent parsing
         */
        protected NumberPrinterParser(TemporalField field, int minWidth, int maxWidth, SignStyle signStyle, int subsequentWidth) {
            // validated by caller
            this.field = field;
            this.minWidth = minWidth;
            this.maxWidth = maxWidth;
            this.signStyle = signStyle;
            this.subsequentWidth = subsequentWidth;
        }

        /**
         * Returns a new instance with fixed width flag set.
         *
         * <p>
         *  返回设置了固定宽度标志的新实例
         * 
         * 
         * @return a new updated printer-parser, not null
         */
        NumberPrinterParser withFixedWidth() {
            if (subsequentWidth == -1) {
                return this;
            }
            return new NumberPrinterParser(field, minWidth, maxWidth, signStyle, -1);
        }

        /**
         * Returns a new instance with an updated subsequent width.
         *
         * <p>
         *  返回具有更新的后续宽度的新实例
         * 
         * 
         * @param subsequentWidth  the width of subsequent non-negative numbers, 0 or greater
         * @return a new updated printer-parser, not null
         */
        NumberPrinterParser withSubsequentWidth(int subsequentWidth) {
            return new NumberPrinterParser(field, minWidth, maxWidth, signStyle, this.subsequentWidth + subsequentWidth);
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            Long valueLong = context.getValue(field);
            if (valueLong == null) {
                return false;
            }
            long value = getValue(context, valueLong);
            DecimalStyle decimalStyle = context.getDecimalStyle();
            String str = (value == Long.MIN_VALUE ? "9223372036854775808" : Long.toString(Math.abs(value)));
            if (str.length() > maxWidth) {
                throw new DateTimeException("Field " + field +
                    " cannot be printed as the value " + value +
                    " exceeds the maximum print width of " + maxWidth);
            }
            str = decimalStyle.convertNumberToI18N(str);

            if (value >= 0) {
                switch (signStyle) {
                    case EXCEEDS_PAD:
                        if (minWidth < 19 && value >= EXCEED_POINTS[minWidth]) {
                            buf.append(decimalStyle.getPositiveSign());
                        }
                        break;
                    case ALWAYS:
                        buf.append(decimalStyle.getPositiveSign());
                        break;
                }
            } else {
                switch (signStyle) {
                    case NORMAL:
                    case EXCEEDS_PAD:
                    case ALWAYS:
                        buf.append(decimalStyle.getNegativeSign());
                        break;
                    case NOT_NEGATIVE:
                        throw new DateTimeException("Field " + field +
                            " cannot be printed as the value " + value +
                            " cannot be negative according to the SignStyle");
                }
            }
            for (int i = 0; i < minWidth - str.length(); i++) {
                buf.append(decimalStyle.getZeroDigit());
            }
            buf.append(str);
            return true;
        }

        /**
         * Gets the value to output.
         *
         * <p>
         *  获取要输出的值
         * 
         * 
         * @param context  the context
         * @param value  the value of the field, not null
         * @return the value
         */
        long getValue(DateTimePrintContext context, long value) {
            return value;
        }

        /**
         * For NumberPrinterParser, the width is fixed depending on the
         * minWidth, maxWidth, signStyle and whether subsequent fields are fixed.
         * <p>
         *  对于NumberPrinterParser,宽度是固定的,具体取决于minWidth,maxWidth,signStyle以及后续字段是否固定
         * 
         * 
         * @param context the context
         * @return true if the field is fixed width
         * @see DateTimeFormatterBuilder#appendValue(java.time.temporal.TemporalField, int)
         */
        boolean isFixedWidth(DateTimeParseContext context) {
            return subsequentWidth == -1 ||
                (subsequentWidth > 0 && minWidth == maxWidth && signStyle == SignStyle.NOT_NEGATIVE);
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            int length = text.length();
            if (position == length) {
                return ~position;
            }
            char sign = text.charAt(position);  // IOOBE if invalid position
            boolean negative = false;
            boolean positive = false;
            if (sign == context.getDecimalStyle().getPositiveSign()) {
                if (signStyle.parse(true, context.isStrict(), minWidth == maxWidth) == false) {
                    return ~position;
                }
                positive = true;
                position++;
            } else if (sign == context.getDecimalStyle().getNegativeSign()) {
                if (signStyle.parse(false, context.isStrict(), minWidth == maxWidth) == false) {
                    return ~position;
                }
                negative = true;
                position++;
            } else {
                if (signStyle == SignStyle.ALWAYS && context.isStrict()) {
                    return ~position;
                }
            }
            int effMinWidth = (context.isStrict() || isFixedWidth(context) ? minWidth : 1);
            int minEndPos = position + effMinWidth;
            if (minEndPos > length) {
                return ~position;
            }
            int effMaxWidth = (context.isStrict() || isFixedWidth(context) ? maxWidth : 9) + Math.max(subsequentWidth, 0);
            long total = 0;
            BigInteger totalBig = null;
            int pos = position;
            for (int pass = 0; pass < 2; pass++) {
                int maxEndPos = Math.min(pos + effMaxWidth, length);
                while (pos < maxEndPos) {
                    char ch = text.charAt(pos++);
                    int digit = context.getDecimalStyle().convertToDigit(ch);
                    if (digit < 0) {
                        pos--;
                        if (pos < minEndPos) {
                            return ~position;  // need at least min width digits
                        }
                        break;
                    }
                    if ((pos - position) > 18) {
                        if (totalBig == null) {
                            totalBig = BigInteger.valueOf(total);
                        }
                        totalBig = totalBig.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit));
                    } else {
                        total = total * 10 + digit;
                    }
                }
                if (subsequentWidth > 0 && pass == 0) {
                    // re-parse now we know the correct width
                    int parseLen = pos - position;
                    effMaxWidth = Math.max(effMinWidth, parseLen - subsequentWidth);
                    pos = position;
                    total = 0;
                    totalBig = null;
                } else {
                    break;
                }
            }
            if (negative) {
                if (totalBig != null) {
                    if (totalBig.equals(BigInteger.ZERO) && context.isStrict()) {
                        return ~(position - 1);  // minus zero not allowed
                    }
                    totalBig = totalBig.negate();
                } else {
                    if (total == 0 && context.isStrict()) {
                        return ~(position - 1);  // minus zero not allowed
                    }
                    total = -total;
                }
            } else if (signStyle == SignStyle.EXCEEDS_PAD && context.isStrict()) {
                int parseLen = pos - position;
                if (positive) {
                    if (parseLen <= minWidth) {
                        return ~(position - 1);  // '+' only parsed if minWidth exceeded
                    }
                } else {
                    if (parseLen > minWidth) {
                        return ~position;  // '+' must be parsed if minWidth exceeded
                    }
                }
            }
            if (totalBig != null) {
                if (totalBig.bitLength() > 63) {
                    // overflow, parse 1 less digit
                    totalBig = totalBig.divide(BigInteger.TEN);
                    pos--;
                }
                return setValue(context, totalBig.longValue(), position, pos);
            }
            return setValue(context, total, position, pos);
        }

        /**
         * Stores the value.
         *
         * <p>
         *  存储值
         * 
         * 
         * @param context  the context to store into, not null
         * @param value  the value
         * @param errorPos  the position of the field being parsed
         * @param successPos  the position after the field being parsed
         * @return the new position
         */
        int setValue(DateTimeParseContext context, long value, int errorPos, int successPos) {
            return context.setParsedField(field, value, errorPos, successPos);
        }

        @Override
        public String toString() {
            if (minWidth == 1 && maxWidth == 19 && signStyle == SignStyle.NORMAL) {
                return "Value(" + field + ")";
            }
            if (minWidth == maxWidth && signStyle == SignStyle.NOT_NEGATIVE) {
                return "Value(" + field + "," + minWidth + ")";
            }
            return "Value(" + field + "," + minWidth + "," + maxWidth + "," + signStyle + ")";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints and parses a reduced numeric date-time field.
     * <p>
     *  打印和解析缩小的数字日期时间字段
     * 
     */
    static final class ReducedPrinterParser extends NumberPrinterParser {
        /**
         * The base date for reduced value parsing.
         * <p>
         * 减值解析的基准日期
         * 
         */
        static final LocalDate BASE_DATE = LocalDate.of(2000, 1, 1);

        private final int baseValue;
        private final ChronoLocalDate baseDate;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param field  the field to format, validated not null
         * @param minWidth  the minimum field width, from 1 to 10
         * @param maxWidth  the maximum field width, from 1 to 10
         * @param baseValue  the base value
         * @param baseDate  the base date
         */
        ReducedPrinterParser(TemporalField field, int minWidth, int maxWidth,
                int baseValue, ChronoLocalDate baseDate) {
            this(field, minWidth, maxWidth, baseValue, baseDate, 0);
            if (minWidth < 1 || minWidth > 10) {
                throw new IllegalArgumentException("The minWidth must be from 1 to 10 inclusive but was " + minWidth);
            }
            if (maxWidth < 1 || maxWidth > 10) {
                throw new IllegalArgumentException("The maxWidth must be from 1 to 10 inclusive but was " + minWidth);
            }
            if (maxWidth < minWidth) {
                throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " +
                        maxWidth + " < " + minWidth);
            }
            if (baseDate == null) {
                if (field.range().isValidValue(baseValue) == false) {
                    throw new IllegalArgumentException("The base value must be within the range of the field");
                }
                if ((((long) baseValue) + EXCEED_POINTS[maxWidth]) > Integer.MAX_VALUE) {
                    throw new DateTimeException("Unable to add printer-parser as the range exceeds the capacity of an int");
                }
            }
        }

        /**
         * Constructor.
         * The arguments have already been checked.
         *
         * <p>
         *  构造函数参数已经被检查
         * 
         * 
         * @param field  the field to format, validated not null
         * @param minWidth  the minimum field width, from 1 to 10
         * @param maxWidth  the maximum field width, from 1 to 10
         * @param baseValue  the base value
         * @param baseDate  the base date
         * @param subsequentWidth the subsequentWidth for this instance
         */
        private ReducedPrinterParser(TemporalField field, int minWidth, int maxWidth,
                int baseValue, ChronoLocalDate baseDate, int subsequentWidth) {
            super(field, minWidth, maxWidth, SignStyle.NOT_NEGATIVE, subsequentWidth);
            this.baseValue = baseValue;
            this.baseDate = baseDate;
        }

        @Override
        long getValue(DateTimePrintContext context, long value) {
            long absValue = Math.abs(value);
            int baseValue = this.baseValue;
            if (baseDate != null) {
                Chronology chrono = Chronology.from(context.getTemporal());
                baseValue = chrono.date(baseDate).get(field);
            }
            if (value >= baseValue && value < baseValue + EXCEED_POINTS[minWidth]) {
                // Use the reduced value if it fits in minWidth
                return absValue % EXCEED_POINTS[minWidth];
            }
            // Otherwise truncate to fit in maxWidth
            return absValue % EXCEED_POINTS[maxWidth];
        }

        @Override
        int setValue(DateTimeParseContext context, long value, int errorPos, int successPos) {
            int baseValue = this.baseValue;
            if (baseDate != null) {
                Chronology chrono = context.getEffectiveChronology();
                baseValue = chrono.date(baseDate).get(field);

                // In case the Chronology is changed later, add a callback when/if it changes
                final long initialValue = value;
                context.addChronoChangedListener(
                        (_unused) ->  {
                            /* Repeat the set of the field using the current Chronology
                             * The success/error position is ignored because the value is
                             * intentionally being overwritten.
                             * <p>
                             *  成功/错误位置被忽略,因为该值被有意地覆盖
                             * 
                             */
                            setValue(context, initialValue, errorPos, successPos);
                        });
            }
            int parseLen = successPos - errorPos;
            if (parseLen == minWidth && value >= 0) {
                long range = EXCEED_POINTS[minWidth];
                long lastPart = baseValue % range;
                long basePart = baseValue - lastPart;
                if (baseValue > 0) {
                    value = basePart + value;
                } else {
                    value = basePart - value;
                }
                if (value < baseValue) {
                    value += range;
                }
            }
            return context.setParsedField(field, value, errorPos, successPos);
        }

        /**
         * Returns a new instance with fixed width flag set.
         *
         * <p>
         *  返回设置了固定宽度标志的新实例
         * 
         * 
         * @return a new updated printer-parser, not null
         */
        @Override
        ReducedPrinterParser withFixedWidth() {
            if (subsequentWidth == -1) {
                return this;
            }
            return new ReducedPrinterParser(field, minWidth, maxWidth, baseValue, baseDate, -1);
        }

        /**
         * Returns a new instance with an updated subsequent width.
         *
         * <p>
         *  返回具有更新的后续宽度的新实例
         * 
         * 
         * @param subsequentWidth  the width of subsequent non-negative numbers, 0 or greater
         * @return a new updated printer-parser, not null
         */
        @Override
        ReducedPrinterParser withSubsequentWidth(int subsequentWidth) {
            return new ReducedPrinterParser(field, minWidth, maxWidth, baseValue, baseDate,
                    this.subsequentWidth + subsequentWidth);
        }

        /**
         * For a ReducedPrinterParser, fixed width is false if the mode is strict,
         * otherwise it is set as for NumberPrinterParser.
         * <p>
         *  对于ReducedPrinterParser,如果模式是strict,固定宽度为false,否则设置为NumberPrinterParser
         * 
         * 
         * @param context the context
         * @return if the field is fixed width
         * @see DateTimeFormatterBuilder#appendValueReduced(java.time.temporal.TemporalField, int, int, int)
         */
        @Override
        boolean isFixedWidth(DateTimeParseContext context) {
           if (context.isStrict() == false) {
               return false;
           }
           return super.isFixedWidth(context);
        }

        @Override
        public String toString() {
            return "ReducedValue(" + field + "," + minWidth + "," + maxWidth + "," + (baseDate != null ? baseDate : baseValue) + ")";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints and parses a numeric date-time field with optional padding.
     * <p>
     *  打印并解析具有可选填充的数字日期时间字段
     * 
     */
    static final class FractionPrinterParser implements DateTimePrinterParser {
        private final TemporalField field;
        private final int minWidth;
        private final int maxWidth;
        private final boolean decimalPoint;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param field  the field to output, not null
         * @param minWidth  the minimum width to output, from 0 to 9
         * @param maxWidth  the maximum width to output, from 0 to 9
         * @param decimalPoint  whether to output the localized decimal point symbol
         */
        FractionPrinterParser(TemporalField field, int minWidth, int maxWidth, boolean decimalPoint) {
            Objects.requireNonNull(field, "field");
            if (field.range().isFixed() == false) {
                throw new IllegalArgumentException("Field must have a fixed set of values: " + field);
            }
            if (minWidth < 0 || minWidth > 9) {
                throw new IllegalArgumentException("Minimum width must be from 0 to 9 inclusive but was " + minWidth);
            }
            if (maxWidth < 1 || maxWidth > 9) {
                throw new IllegalArgumentException("Maximum width must be from 1 to 9 inclusive but was " + maxWidth);
            }
            if (maxWidth < minWidth) {
                throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " +
                        maxWidth + " < " + minWidth);
            }
            this.field = field;
            this.minWidth = minWidth;
            this.maxWidth = maxWidth;
            this.decimalPoint = decimalPoint;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            Long value = context.getValue(field);
            if (value == null) {
                return false;
            }
            DecimalStyle decimalStyle = context.getDecimalStyle();
            BigDecimal fraction = convertToFraction(value);
            if (fraction.scale() == 0) {  // scale is zero if value is zero
                if (minWidth > 0) {
                    if (decimalPoint) {
                        buf.append(decimalStyle.getDecimalSeparator());
                    }
                    for (int i = 0; i < minWidth; i++) {
                        buf.append(decimalStyle.getZeroDigit());
                    }
                }
            } else {
                int outputScale = Math.min(Math.max(fraction.scale(), minWidth), maxWidth);
                fraction = fraction.setScale(outputScale, RoundingMode.FLOOR);
                String str = fraction.toPlainString().substring(2);
                str = decimalStyle.convertNumberToI18N(str);
                if (decimalPoint) {
                    buf.append(decimalStyle.getDecimalSeparator());
                }
                buf.append(str);
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            int effectiveMin = (context.isStrict() ? minWidth : 0);
            int effectiveMax = (context.isStrict() ? maxWidth : 9);
            int length = text.length();
            if (position == length) {
                // valid if whole field is optional, invalid if minimum width
                return (effectiveMin > 0 ? ~position : position);
            }
            if (decimalPoint) {
                if (text.charAt(position) != context.getDecimalStyle().getDecimalSeparator()) {
                    // valid if whole field is optional, invalid if minimum width
                    return (effectiveMin > 0 ? ~position : position);
                }
                position++;
            }
            int minEndPos = position + effectiveMin;
            if (minEndPos > length) {
                return ~position;  // need at least min width digits
            }
            int maxEndPos = Math.min(position + effectiveMax, length);
            int total = 0;  // can use int because we are only parsing up to 9 digits
            int pos = position;
            while (pos < maxEndPos) {
                char ch = text.charAt(pos++);
                int digit = context.getDecimalStyle().convertToDigit(ch);
                if (digit < 0) {
                    if (pos < minEndPos) {
                        return ~position;  // need at least min width digits
                    }
                    pos--;
                    break;
                }
                total = total * 10 + digit;
            }
            BigDecimal fraction = new BigDecimal(total).movePointLeft(pos - position);
            long value = convertFromFraction(fraction);
            return context.setParsedField(field, value, position, pos);
        }

        /**
         * Converts a value for this field to a fraction between 0 and 1.
         * <p>
         * The fractional value is between 0 (inclusive) and 1 (exclusive).
         * It can only be returned if the {@link java.time.temporal.TemporalField#range() value range} is fixed.
         * The fraction is obtained by calculation from the field range using 9 decimal
         * places and a rounding mode of {@link RoundingMode#FLOOR FLOOR}.
         * The calculation is inaccurate if the values do not run continuously from smallest to largest.
         * <p>
         * For example, the second-of-minute value of 15 would be returned as 0.25,
         * assuming the standard definition of 60 seconds in a minute.
         *
         * <p>
         *  将此字段的值转换为0到1之间的分数
         * <p>
         * 分数值在0(包含)和1(不包含)之间。只有当{@link javatimetemporalTemporalField#range()value range}固定时,才能返回分数值。
         * 通过使用9个小数位的字段范围计算获得分数, {@link RoundingMode#FLOOR FLOOR}的舍入模式如果值不从最小到最大连续运行,计算是不准确的。
         * <p>
         *  例如,假设在一分钟内标准定义为60秒,第二分钟值15将返回为025
         * 
         * 
         * @param value  the value to convert, must be valid for this rule
         * @return the value as a fraction within the range, from 0 to 1, not null
         * @throws DateTimeException if the value cannot be converted to a fraction
         */
        private BigDecimal convertToFraction(long value) {
            ValueRange range = field.range();
            range.checkValidValue(value, field);
            BigDecimal minBD = BigDecimal.valueOf(range.getMinimum());
            BigDecimal rangeBD = BigDecimal.valueOf(range.getMaximum()).subtract(minBD).add(BigDecimal.ONE);
            BigDecimal valueBD = BigDecimal.valueOf(value).subtract(minBD);
            BigDecimal fraction = valueBD.divide(rangeBD, 9, RoundingMode.FLOOR);
            // stripTrailingZeros bug
            return fraction.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : fraction.stripTrailingZeros();
        }

        /**
         * Converts a fraction from 0 to 1 for this field to a value.
         * <p>
         * The fractional value must be between 0 (inclusive) and 1 (exclusive).
         * It can only be returned if the {@link java.time.temporal.TemporalField#range() value range} is fixed.
         * The value is obtained by calculation from the field range and a rounding
         * mode of {@link RoundingMode#FLOOR FLOOR}.
         * The calculation is inaccurate if the values do not run continuously from smallest to largest.
         * <p>
         * For example, the fractional second-of-minute of 0.25 would be converted to 15,
         * assuming the standard definition of 60 seconds in a minute.
         *
         * <p>
         *  将此字段的分数从0转换为1为一个值
         * <p>
         * 分数值必须在0(包含)和1(不包含)之间。只有当{@link javatimetemporalTemporalField#range()value range}固定时,才能返回该值。
         * 通过从字段范围计算获得该值, {@link RoundingMode#FLOOR FLOOR}如果值不从最小到最大连续运行,计算是不准确的。
         * <p>
         *  例如,025的分数秒数将被转换为15,假设在一分钟内标准定义为60秒
         * 
         * 
         * @param fraction  the fraction to convert, not null
         * @return the value of the field, valid for this rule
         * @throws DateTimeException if the value cannot be converted
         */
        private long convertFromFraction(BigDecimal fraction) {
            ValueRange range = field.range();
            BigDecimal minBD = BigDecimal.valueOf(range.getMinimum());
            BigDecimal rangeBD = BigDecimal.valueOf(range.getMaximum()).subtract(minBD).add(BigDecimal.ONE);
            BigDecimal valueBD = fraction.multiply(rangeBD).setScale(0, RoundingMode.FLOOR).add(minBD);
            return valueBD.longValueExact();
        }

        @Override
        public String toString() {
            String decimal = (decimalPoint ? ",DecimalPoint" : "");
            return "Fraction(" + field + "," + minWidth + "," + maxWidth + decimal + ")";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses field text.
     * <p>
     *  打印或解析字段文本
     * 
     */
    static final class TextPrinterParser implements DateTimePrinterParser {
        private final TemporalField field;
        private final TextStyle textStyle;
        private final DateTimeTextProvider provider;
        /**
         * The cached number printer parser.
         * Immutable and volatile, so no synchronization needed.
         * <p>
         *  缓存编号打印机解析器不可变和易失性,因此不需要同步
         * 
         */
        private volatile NumberPrinterParser numberPrinterParser;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param field  the field to output, not null
         * @param textStyle  the text style, not null
         * @param provider  the text provider, not null
         */
        TextPrinterParser(TemporalField field, TextStyle textStyle, DateTimeTextProvider provider) {
            // validated by caller
            this.field = field;
            this.textStyle = textStyle;
            this.provider = provider;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            Long value = context.getValue(field);
            if (value == null) {
                return false;
            }
            String text;
            Chronology chrono = context.getTemporal().query(TemporalQueries.chronology());
            if (chrono == null || chrono == IsoChronology.INSTANCE) {
                text = provider.getText(field, value, textStyle, context.getLocale());
            } else {
                text = provider.getText(chrono, field, value, textStyle, context.getLocale());
            }
            if (text == null) {
                return numberPrinterParser().format(context, buf);
            }
            buf.append(text);
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence parseText, int position) {
            int length = parseText.length();
            if (position < 0 || position > length) {
                throw new IndexOutOfBoundsException();
            }
            TextStyle style = (context.isStrict() ? textStyle : null);
            Chronology chrono = context.getEffectiveChronology();
            Iterator<Entry<String, Long>> it;
            if (chrono == null || chrono == IsoChronology.INSTANCE) {
                it = provider.getTextIterator(field, style, context.getLocale());
            } else {
                it = provider.getTextIterator(chrono, field, style, context.getLocale());
            }
            if (it != null) {
                while (it.hasNext()) {
                    Entry<String, Long> entry = it.next();
                    String itText = entry.getKey();
                    if (context.subSequenceEquals(itText, 0, parseText, position, itText.length())) {
                        return context.setParsedField(field, entry.getValue(), position, position + itText.length());
                    }
                }
                if (context.isStrict()) {
                    return ~position;
                }
            }
            return numberPrinterParser().parse(context, parseText, position);
        }

        /**
         * Create and cache a number printer parser.
         * <p>
         *  创建并缓存数字打印机解析器
         * 
         * 
         * @return the number printer parser for this field, not null
         */
        private NumberPrinterParser numberPrinterParser() {
            if (numberPrinterParser == null) {
                numberPrinterParser = new NumberPrinterParser(field, 1, 19, SignStyle.NORMAL);
            }
            return numberPrinterParser;
        }

        @Override
        public String toString() {
            if (textStyle == TextStyle.FULL) {
                return "Text(" + field + ")";
            }
            return "Text(" + field + "," + textStyle + ")";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses an ISO-8601 instant.
     * <p>
     *  打印或解析ISO-8601即时
     * 
     */
    static final class InstantPrinterParser implements DateTimePrinterParser {
        // days in a 400 year cycle = 146097
        // days in a 10,000 year cycle = 146097 * 25
        // seconds per day = 86400
        private static final long SECONDS_PER_10000_YEARS = 146097L * 25L * 86400L;
        private static final long SECONDS_0000_TO_1970 = ((146097L * 5L) - (30L * 365L + 7L)) * 86400L;
        private final int fractionalDigits;

        InstantPrinterParser(int fractionalDigits) {
            this.fractionalDigits = fractionalDigits;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            // use INSTANT_SECONDS, thus this code is not bound by Instant.MAX
            Long inSecs = context.getValue(INSTANT_SECONDS);
            Long inNanos = null;
            if (context.getTemporal().isSupported(NANO_OF_SECOND)) {
                inNanos = context.getTemporal().getLong(NANO_OF_SECOND);
            }
            if (inSecs == null) {
                return false;
            }
            long inSec = inSecs;
            int inNano = NANO_OF_SECOND.checkValidIntValue(inNanos != null ? inNanos : 0);
            // format mostly using LocalDateTime.toString
            if (inSec >= -SECONDS_0000_TO_1970) {
                // current era
                long zeroSecs = inSec - SECONDS_PER_10000_YEARS + SECONDS_0000_TO_1970;
                long hi = Math.floorDiv(zeroSecs, SECONDS_PER_10000_YEARS) + 1;
                long lo = Math.floorMod(zeroSecs, SECONDS_PER_10000_YEARS);
                LocalDateTime ldt = LocalDateTime.ofEpochSecond(lo - SECONDS_0000_TO_1970, 0, ZoneOffset.UTC);
                if (hi > 0) {
                    buf.append('+').append(hi);
                }
                buf.append(ldt);
                if (ldt.getSecond() == 0) {
                    buf.append(":00");
                }
            } else {
                // before current era
                long zeroSecs = inSec + SECONDS_0000_TO_1970;
                long hi = zeroSecs / SECONDS_PER_10000_YEARS;
                long lo = zeroSecs % SECONDS_PER_10000_YEARS;
                LocalDateTime ldt = LocalDateTime.ofEpochSecond(lo - SECONDS_0000_TO_1970, 0, ZoneOffset.UTC);
                int pos = buf.length();
                buf.append(ldt);
                if (ldt.getSecond() == 0) {
                    buf.append(":00");
                }
                if (hi < 0) {
                    if (ldt.getYear() == -10_000) {
                        buf.replace(pos, pos + 2, Long.toString(hi - 1));
                    } else if (lo == 0) {
                        buf.insert(pos, hi);
                    } else {
                        buf.insert(pos + 1, Math.abs(hi));
                    }
                }
            }
            // add fraction
            if ((fractionalDigits < 0 && inNano > 0) || fractionalDigits > 0) {
                buf.append('.');
                int div = 100_000_000;
                for (int i = 0; ((fractionalDigits == -1 && inNano > 0) ||
                                    (fractionalDigits == -2 && (inNano > 0 || (i % 3) != 0)) ||
                                    i < fractionalDigits); i++) {
                    int digit = inNano / div;
                    buf.append((char) (digit + '0'));
                    inNano = inNano - (digit * div);
                    div = div / 10;
                }
            }
            buf.append('Z');
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            // new context to avoid overwriting fields like year/month/day
            int minDigits = (fractionalDigits < 0 ? 0 : fractionalDigits);
            int maxDigits = (fractionalDigits < 0 ? 9 : fractionalDigits);
            CompositePrinterParser parser = new DateTimeFormatterBuilder()
                    .append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral('T')
                    .appendValue(HOUR_OF_DAY, 2).appendLiteral(':')
                    .appendValue(MINUTE_OF_HOUR, 2).appendLiteral(':')
                    .appendValue(SECOND_OF_MINUTE, 2)
                    .appendFraction(NANO_OF_SECOND, minDigits, maxDigits, true)
                    .appendLiteral('Z')
                    .toFormatter().toPrinterParser(false);
            DateTimeParseContext newContext = context.copy();
            int pos = parser.parse(newContext, text, position);
            if (pos < 0) {
                return pos;
            }
            // parser restricts most fields to 2 digits, so definitely int
            // correctly parsed nano is also guaranteed to be valid
            long yearParsed = newContext.getParsed(YEAR);
            int month = newContext.getParsed(MONTH_OF_YEAR).intValue();
            int day = newContext.getParsed(DAY_OF_MONTH).intValue();
            int hour = newContext.getParsed(HOUR_OF_DAY).intValue();
            int min = newContext.getParsed(MINUTE_OF_HOUR).intValue();
            Long secVal = newContext.getParsed(SECOND_OF_MINUTE);
            Long nanoVal = newContext.getParsed(NANO_OF_SECOND);
            int sec = (secVal != null ? secVal.intValue() : 0);
            int nano = (nanoVal != null ? nanoVal.intValue() : 0);
            int days = 0;
            if (hour == 24 && min == 0 && sec == 0 && nano == 0) {
                hour = 0;
                days = 1;
            } else if (hour == 23 && min == 59 && sec == 60) {
                context.setParsedLeapSecond();
                sec = 59;
            }
            int year = (int) yearParsed % 10_000;
            long instantSecs;
            try {
                LocalDateTime ldt = LocalDateTime.of(year, month, day, hour, min, sec, 0).plusDays(days);
                instantSecs = ldt.toEpochSecond(ZoneOffset.UTC);
                instantSecs += Math.multiplyExact(yearParsed / 10_000L, SECONDS_PER_10000_YEARS);
            } catch (RuntimeException ex) {
                return ~position;
            }
            int successPos = pos;
            successPos = context.setParsedField(INSTANT_SECONDS, instantSecs, position, successPos);
            return context.setParsedField(NANO_OF_SECOND, nano, position, successPos);
        }

        @Override
        public String toString() {
            return "Instant()";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses an offset ID.
     * <p>
     *  打印或解析偏移ID
     * 
     */
    static final class OffsetIdPrinterParser implements DateTimePrinterParser {
        static final String[] PATTERNS = new String[] {
            "+HH", "+HHmm", "+HH:mm", "+HHMM", "+HH:MM", "+HHMMss", "+HH:MM:ss", "+HHMMSS", "+HH:MM:SS",
        };  // order used in pattern builder
        static final OffsetIdPrinterParser INSTANCE_ID_Z = new OffsetIdPrinterParser("+HH:MM:ss", "Z");
        static final OffsetIdPrinterParser INSTANCE_ID_ZERO = new OffsetIdPrinterParser("+HH:MM:ss", "0");

        private final String noOffsetText;
        private final int type;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param pattern  the pattern
         * @param noOffsetText  the text to use for UTC, not null
         */
        OffsetIdPrinterParser(String pattern, String noOffsetText) {
            Objects.requireNonNull(pattern, "pattern");
            Objects.requireNonNull(noOffsetText, "noOffsetText");
            this.type = checkPattern(pattern);
            this.noOffsetText = noOffsetText;
        }

        private int checkPattern(String pattern) {
            for (int i = 0; i < PATTERNS.length; i++) {
                if (PATTERNS[i].equals(pattern)) {
                    return i;
                }
            }
            throw new IllegalArgumentException("Invalid zone offset pattern: " + pattern);
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            Long offsetSecs = context.getValue(OFFSET_SECONDS);
            if (offsetSecs == null) {
                return false;
            }
            int totalSecs = Math.toIntExact(offsetSecs);
            if (totalSecs == 0) {
                buf.append(noOffsetText);
            } else {
                int absHours = Math.abs((totalSecs / 3600) % 100);  // anything larger than 99 silently dropped
                int absMinutes = Math.abs((totalSecs / 60) % 60);
                int absSeconds = Math.abs(totalSecs % 60);
                int bufPos = buf.length();
                int output = absHours;
                buf.append(totalSecs < 0 ? "-" : "+")
                    .append((char) (absHours / 10 + '0')).append((char) (absHours % 10 + '0'));
                if (type >= 3 || (type >= 1 && absMinutes > 0)) {
                    buf.append((type % 2) == 0 ? ":" : "")
                        .append((char) (absMinutes / 10 + '0')).append((char) (absMinutes % 10 + '0'));
                    output += absMinutes;
                    if (type >= 7 || (type >= 5 && absSeconds > 0)) {
                        buf.append((type % 2) == 0 ? ":" : "")
                            .append((char) (absSeconds / 10 + '0')).append((char) (absSeconds % 10 + '0'));
                        output += absSeconds;
                    }
                }
                if (output == 0) {
                    buf.setLength(bufPos);
                    buf.append(noOffsetText);
                }
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            int length = text.length();
            int noOffsetLen = noOffsetText.length();
            if (noOffsetLen == 0) {
                if (position == length) {
                    return context.setParsedField(OFFSET_SECONDS, 0, position, position);
                }
            } else {
                if (position == length) {
                    return ~position;
                }
                if (context.subSequenceEquals(text, position, noOffsetText, 0, noOffsetLen)) {
                    return context.setParsedField(OFFSET_SECONDS, 0, position, position + noOffsetLen);
                }
            }

            // parse normal plus/minus offset
            char sign = text.charAt(position);  // IOOBE if invalid position
            if (sign == '+' || sign == '-') {
                // starts
                int negative = (sign == '-' ? -1 : 1);
                int[] array = new int[4];
                array[0] = position + 1;
                if ((parseNumber(array, 1, text, true) ||
                        parseNumber(array, 2, text, type >=3) ||
                        parseNumber(array, 3, text, false)) == false) {
                    // success
                    long offsetSecs = negative * (array[1] * 3600L + array[2] * 60L + array[3]);
                    return context.setParsedField(OFFSET_SECONDS, offsetSecs, position, array[0]);
                }
            }
            // handle special case of empty no offset text
            if (noOffsetLen == 0) {
                return context.setParsedField(OFFSET_SECONDS, 0, position, position + noOffsetLen);
            }
            return ~position;
        }

        /**
         * Parse a two digit zero-prefixed number.
         *
         * <p>
         * 解析两位数的零前缀数字
         * 
         * 
         * @param array  the array of parsed data, 0=pos,1=hours,2=mins,3=secs, not null
         * @param arrayIndex  the index to parse the value into
         * @param parseText  the offset ID, not null
         * @param required  whether this number is required
         * @return true if an error occurred
         */
        private boolean parseNumber(int[] array, int arrayIndex, CharSequence parseText, boolean required) {
            if ((type + 3) / 2 < arrayIndex) {
                return false;  // ignore seconds/minutes
            }
            int pos = array[0];
            if ((type % 2) == 0 && arrayIndex > 1) {
                if (pos + 1 > parseText.length() || parseText.charAt(pos) != ':') {
                    return required;
                }
                pos++;
            }
            if (pos + 2 > parseText.length()) {
                return required;
            }
            char ch1 = parseText.charAt(pos++);
            char ch2 = parseText.charAt(pos++);
            if (ch1 < '0' || ch1 > '9' || ch2 < '0' || ch2 > '9') {
                return required;
            }
            int value = (ch1 - 48) * 10 + (ch2 - 48);
            if (value < 0 || value > 59) {
                return required;
            }
            array[arrayIndex] = value;
            array[0] = pos;
            return false;
        }

        @Override
        public String toString() {
            String converted = noOffsetText.replace("'", "''");
            return "Offset(" + PATTERNS[type] + ",'" + converted + "')";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses an offset ID.
     * <p>
     *  打印或解析偏移ID
     * 
     */
    static final class LocalizedOffsetIdPrinterParser implements DateTimePrinterParser {
        private final TextStyle style;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param style  the style, not null
         */
        LocalizedOffsetIdPrinterParser(TextStyle style) {
            this.style = style;
        }

        private static StringBuilder appendHMS(StringBuilder buf, int t) {
            return buf.append((char)(t / 10 + '0'))
                      .append((char)(t % 10 + '0'));
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            Long offsetSecs = context.getValue(OFFSET_SECONDS);
            if (offsetSecs == null) {
                return false;
            }
            String gmtText = "GMT";  // TODO: get localized version of 'GMT'
            if (gmtText != null) {
                buf.append(gmtText);
            }
            int totalSecs = Math.toIntExact(offsetSecs);
            if (totalSecs != 0) {
                int absHours = Math.abs((totalSecs / 3600) % 100);  // anything larger than 99 silently dropped
                int absMinutes = Math.abs((totalSecs / 60) % 60);
                int absSeconds = Math.abs(totalSecs % 60);
                buf.append(totalSecs < 0 ? "-" : "+");
                if (style == TextStyle.FULL) {
                    appendHMS(buf, absHours);
                    buf.append(':');
                    appendHMS(buf, absMinutes);
                    if (absSeconds != 0) {
                       buf.append(':');
                       appendHMS(buf, absSeconds);
                    }
                } else {
                    if (absHours >= 10) {
                        buf.append((char)(absHours / 10 + '0'));
                    }
                    buf.append((char)(absHours % 10 + '0'));
                    if (absMinutes != 0 || absSeconds != 0) {
                        buf.append(':');
                        appendHMS(buf, absMinutes);
                        if (absSeconds != 0) {
                            buf.append(':');
                            appendHMS(buf, absSeconds);
                        }
                    }
                }
            }
            return true;
        }

        int getDigit(CharSequence text, int position) {
            char c = text.charAt(position);
            if (c < '0' || c > '9') {
                return -1;
            }
            return c - '0';
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            int pos = position;
            int end = pos + text.length();
            String gmtText = "GMT";  // TODO: get localized version of 'GMT'
            if (gmtText != null) {
                if (!context.subSequenceEquals(text, pos, gmtText, 0, gmtText.length())) {
                    return ~position;
                }
                pos += gmtText.length();
            }
            // parse normal plus/minus offset
            int negative = 0;
            if (pos == end) {
                return context.setParsedField(OFFSET_SECONDS, 0, position, pos);
            }
            char sign = text.charAt(pos);  // IOOBE if invalid position
            if (sign == '+') {
                negative = 1;
            } else if (sign == '-') {
                negative = -1;
            } else {
                return context.setParsedField(OFFSET_SECONDS, 0, position, pos);
            }
            pos++;
            int h = 0;
            int m = 0;
            int s = 0;
            if (style == TextStyle.FULL) {
                int h1 = getDigit(text, pos++);
                int h2 = getDigit(text, pos++);
                if (h1 < 0 || h2 < 0 || text.charAt(pos++) != ':') {
                    return ~position;
                }
                h = h1 * 10 + h2;
                int m1 = getDigit(text, pos++);
                int m2 = getDigit(text, pos++);
                if (m1 < 0 || m2 < 0) {
                    return ~position;
                }
                m = m1 * 10 + m2;
                if (pos + 2 < end && text.charAt(pos) == ':') {
                    int s1 = getDigit(text, pos + 1);
                    int s2 = getDigit(text, pos + 2);
                    if (s1 >= 0 && s2 >= 0) {
                        s = s1 * 10 + s2;
                        pos += 3;
                    }
                }
            } else {
                h = getDigit(text, pos++);
                if (h < 0) {
                    return ~position;
                }
                if (pos < end) {
                    int h2 = getDigit(text, pos);
                    if (h2 >=0) {
                        h = h * 10 + h2;
                        pos++;
                    }
                    if (pos + 2 < end && text.charAt(pos) == ':') {
                        if (pos + 2 < end && text.charAt(pos) == ':') {
                            int m1 = getDigit(text, pos + 1);
                            int m2 = getDigit(text, pos + 2);
                            if (m1 >= 0 && m2 >= 0) {
                                m = m1 * 10 + m2;
                                pos += 3;
                                if (pos + 2 < end && text.charAt(pos) == ':') {
                                    int s1 = getDigit(text, pos + 1);
                                    int s2 = getDigit(text, pos + 2);
                                    if (s1 >= 0 && s2 >= 0) {
                                        s = s1 * 10 + s2;
                                        pos += 3;
                                   }
                                }
                            }
                        }
                    }
                }
            }
            long offsetSecs = negative * (h * 3600L + m * 60L + s);
            return context.setParsedField(OFFSET_SECONDS, offsetSecs, position, pos);
        }

        @Override
        public String toString() {
            return "LocalizedOffset(" + style + ")";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses a zone ID.
     * <p>
     *  打印或解析区域ID
     * 
     */
    static final class ZoneTextPrinterParser extends ZoneIdPrinterParser {

        /** The text style to output. */
        private final TextStyle textStyle;

        /** The preferred zoneid map */
        private Set<String> preferredZones;

        ZoneTextPrinterParser(TextStyle textStyle, Set<ZoneId> preferredZones) {
            super(TemporalQueries.zone(), "ZoneText(" + textStyle + ")");
            this.textStyle = Objects.requireNonNull(textStyle, "textStyle");
            if (preferredZones != null && preferredZones.size() != 0) {
                this.preferredZones = new HashSet<>();
                for (ZoneId id : preferredZones) {
                    this.preferredZones.add(id.getId());
                }
            }
        }

        private static final int STD = 0;
        private static final int DST = 1;
        private static final int GENERIC = 2;
        private static final Map<String, SoftReference<Map<Locale, String[]>>> cache =
            new ConcurrentHashMap<>();

        private String getDisplayName(String id, int type, Locale locale) {
            if (textStyle == TextStyle.NARROW) {
                return null;
            }
            String[] names;
            SoftReference<Map<Locale, String[]>> ref = cache.get(id);
            Map<Locale, String[]> perLocale = null;
            if (ref == null || (perLocale = ref.get()) == null ||
                (names = perLocale.get(locale)) == null) {
                names = TimeZoneNameUtility.retrieveDisplayNames(id, locale);
                if (names == null) {
                    return null;
                }
                names = Arrays.copyOfRange(names, 0, 7);
                names[5] =
                    TimeZoneNameUtility.retrieveGenericDisplayName(id, TimeZone.LONG, locale);
                if (names[5] == null) {
                    names[5] = names[0]; // use the id
                }
                names[6] =
                    TimeZoneNameUtility.retrieveGenericDisplayName(id, TimeZone.SHORT, locale);
                if (names[6] == null) {
                    names[6] = names[0];
                }
                if (perLocale == null) {
                    perLocale = new ConcurrentHashMap<>();
                }
                perLocale.put(locale, names);
                cache.put(id, new SoftReference<>(perLocale));
            }
            switch (type) {
            case STD:
                return names[textStyle.zoneNameStyleIndex() + 1];
            case DST:
                return names[textStyle.zoneNameStyleIndex() + 3];
            }
            return names[textStyle.zoneNameStyleIndex() + 5];
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            ZoneId zone = context.getValue(TemporalQueries.zoneId());
            if (zone == null) {
                return false;
            }
            String zname = zone.getId();
            if (!(zone instanceof ZoneOffset)) {
                TemporalAccessor dt = context.getTemporal();
                String name = getDisplayName(zname,
                                             dt.isSupported(ChronoField.INSTANT_SECONDS)
                                             ? (zone.getRules().isDaylightSavings(Instant.from(dt)) ? DST : STD)
                                             : GENERIC,
                                             context.getLocale());
                if (name != null) {
                    zname = name;
                }
            }
            buf.append(zname);
            return true;
        }

        // cache per instance for now
        private final Map<Locale, Entry<Integer, SoftReference<PrefixTree>>>
            cachedTree = new HashMap<>();
        private final Map<Locale, Entry<Integer, SoftReference<PrefixTree>>>
            cachedTreeCI = new HashMap<>();

        @Override
        protected PrefixTree getTree(DateTimeParseContext context) {
            if (textStyle == TextStyle.NARROW) {
                return super.getTree(context);
            }
            Locale locale = context.getLocale();
            boolean isCaseSensitive = context.isCaseSensitive();
            Set<String> regionIds = ZoneRulesProvider.getAvailableZoneIds();
            int regionIdsSize = regionIds.size();

            Map<Locale, Entry<Integer, SoftReference<PrefixTree>>> cached =
                isCaseSensitive ? cachedTree : cachedTreeCI;

            Entry<Integer, SoftReference<PrefixTree>> entry = null;
            PrefixTree tree = null;
            String[][] zoneStrings = null;
            if ((entry = cached.get(locale)) == null ||
                (entry.getKey() != regionIdsSize ||
                (tree = entry.getValue().get()) == null)) {
                tree = PrefixTree.newTree(context);
                zoneStrings = TimeZoneNameUtility.getZoneStrings(locale);
                for (String[] names : zoneStrings) {
                    String zid = names[0];
                    if (!regionIds.contains(zid)) {
                        continue;
                    }
                    tree.add(zid, zid);    // don't convert zid -> metazone
                    zid = ZoneName.toZid(zid, locale);
                    int i = textStyle == TextStyle.FULL ? 1 : 2;
                    for (; i < names.length; i += 2) {
                        tree.add(names[i], zid);
                    }
                }
                // if we have a set of preferred zones, need a copy and
                // add the preferred zones again to overwrite
                if (preferredZones != null) {
                    for (String[] names : zoneStrings) {
                        String zid = names[0];
                        if (!preferredZones.contains(zid) || !regionIds.contains(zid)) {
                            continue;
                        }
                        int i = textStyle == TextStyle.FULL ? 1 : 2;
                        for (; i < names.length; i += 2) {
                            tree.add(names[i], zid);
                       }
                    }
                }
                cached.put(locale, new SimpleImmutableEntry<>(regionIdsSize, new SoftReference<>(tree)));
            }
            return tree;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses a zone ID.
     * <p>
     *  打印或解析区域ID
     * 
     */
    static class ZoneIdPrinterParser implements DateTimePrinterParser {
        private final TemporalQuery<ZoneId> query;
        private final String description;

        ZoneIdPrinterParser(TemporalQuery<ZoneId> query, String description) {
            this.query = query;
            this.description = description;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            ZoneId zone = context.getValue(query);
            if (zone == null) {
                return false;
            }
            buf.append(zone.getId());
            return true;
        }

        /**
         * The cached tree to speed up parsing.
         * <p>
         *  缓存的树加速解析
         * 
         */
        private static volatile Entry<Integer, PrefixTree> cachedPrefixTree;
        private static volatile Entry<Integer, PrefixTree> cachedPrefixTreeCI;

        protected PrefixTree getTree(DateTimeParseContext context) {
            // prepare parse tree
            Set<String> regionIds = ZoneRulesProvider.getAvailableZoneIds();
            final int regionIdsSize = regionIds.size();
            Entry<Integer, PrefixTree> cached = context.isCaseSensitive()
                                                ? cachedPrefixTree : cachedPrefixTreeCI;
            if (cached == null || cached.getKey() != regionIdsSize) {
                synchronized (this) {
                    cached = context.isCaseSensitive() ? cachedPrefixTree : cachedPrefixTreeCI;
                    if (cached == null || cached.getKey() != regionIdsSize) {
                        cached = new SimpleImmutableEntry<>(regionIdsSize, PrefixTree.newTree(regionIds, context));
                        if (context.isCaseSensitive()) {
                            cachedPrefixTree = cached;
                        } else {
                            cachedPrefixTreeCI = cached;
                        }
                    }
                }
            }
            return cached.getValue();
        }

        /**
         * This implementation looks for the longest matching string.
         * For example, parsing Etc/GMT-2 will return Etc/GMC-2 rather than just
         * Etc/GMC although both are valid.
         * <p>
         *  这个实现查找最长匹配的字符串例如,解析Etc / GMT-2将返回Etc / GMC-2,而不是仅仅Etc / GMC,尽管两者都有效
         * 
         */
        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            int length = text.length();
            if (position > length) {
                throw new IndexOutOfBoundsException();
            }
            if (position == length) {
                return ~position;
            }

            // handle fixed time-zone IDs
            char nextChar = text.charAt(position);
            if (nextChar == '+' || nextChar == '-') {
                return parseOffsetBased(context, text, position, position, OffsetIdPrinterParser.INSTANCE_ID_Z);
            } else if (length >= position + 2) {
                char nextNextChar = text.charAt(position + 1);
                if (context.charEquals(nextChar, 'U') && context.charEquals(nextNextChar, 'T')) {
                    if (length >= position + 3 && context.charEquals(text.charAt(position + 2), 'C')) {
                        return parseOffsetBased(context, text, position, position + 3, OffsetIdPrinterParser.INSTANCE_ID_ZERO);
                    }
                    return parseOffsetBased(context, text, position, position + 2, OffsetIdPrinterParser.INSTANCE_ID_ZERO);
                } else if (context.charEquals(nextChar, 'G') && length >= position + 3 &&
                        context.charEquals(nextNextChar, 'M') && context.charEquals(text.charAt(position + 2), 'T')) {
                    return parseOffsetBased(context, text, position, position + 3, OffsetIdPrinterParser.INSTANCE_ID_ZERO);
                }
            }

            // parse
            PrefixTree tree = getTree(context);
            ParsePosition ppos = new ParsePosition(position);
            String parsedZoneId = tree.match(text, ppos);
            if (parsedZoneId == null) {
                if (context.charEquals(nextChar, 'Z')) {
                    context.setParsed(ZoneOffset.UTC);
                    return position + 1;
                }
                return ~position;
            }
            context.setParsed(ZoneId.of(parsedZoneId));
            return ppos.getIndex();
        }

        /**
         * Parse an offset following a prefix and set the ZoneId if it is valid.
         * To matching the parsing of ZoneId.of the values are not normalized
         * to ZoneOffsets.
         *
         * <p>
         *  解析前缀之后的偏移量,并设置ZoneId,如果它是有效的匹配分析的ZoneIdof值不归一化为ZoneOffsets
         * 
         * 
         * @param context the parse context
         * @param text the input text
         * @param prefixPos start of the prefix
         * @param position start of text after the prefix
         * @param parser parser for the value after the prefix
         * @return the position after the parse
         */
        private int parseOffsetBased(DateTimeParseContext context, CharSequence text, int prefixPos, int position, OffsetIdPrinterParser parser) {
            String prefix = text.toString().substring(prefixPos, position).toUpperCase();
            if (position >= text.length()) {
                context.setParsed(ZoneId.of(prefix));
                return position;
            }

            // '0' or 'Z' after prefix is not part of a valid ZoneId; use bare prefix
            if (text.charAt(position) == '0' ||
                context.charEquals(text.charAt(position), 'Z')) {
                context.setParsed(ZoneId.of(prefix));
                return position;
            }

            DateTimeParseContext newContext = context.copy();
            int endPos = parser.parse(newContext, text, position);
            try {
                if (endPos < 0) {
                    if (parser == OffsetIdPrinterParser.INSTANCE_ID_Z) {
                        return ~prefixPos;
                    }
                    context.setParsed(ZoneId.of(prefix));
                    return position;
                }
                int offset = (int) newContext.getParsed(OFFSET_SECONDS).longValue();
                ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
                context.setParsed(ZoneId.ofOffset(prefix, zoneOffset));
                return endPos;
            } catch (DateTimeException dte) {
                return ~prefixPos;
            }
        }

        @Override
        public String toString() {
            return description;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * A String based prefix tree for parsing time-zone names.
     * <p>
     *  用于解析时区名称的基于字符串的前缀树
     * 
     */
    static class PrefixTree {
        protected String key;
        protected String value;
        protected char c0;    // performance optimization to avoid the
                              // boundary check cost of key.charat(0)
        protected PrefixTree child;
        protected PrefixTree sibling;

        private PrefixTree(String k, String v, PrefixTree child) {
            this.key = k;
            this.value = v;
            this.child = child;
            if (k.length() == 0){
                c0 = 0xffff;
            } else {
                c0 = key.charAt(0);
            }
        }

        /**
         * Creates a new prefix parsing tree based on parse context.
         *
         * <p>
         *  基于解析上下文创建新的前缀解析树
         * 
         * 
         * @param context  the parse context
         * @return the tree, not null
         */
        public static PrefixTree newTree(DateTimeParseContext context) {
            //if (!context.isStrict()) {
            //    return new LENIENT("", null, null);
            //}
            if (context.isCaseSensitive()) {
                return new PrefixTree("", null, null);
            }
            return new CI("", null, null);
        }

        /**
         * Creates a new prefix parsing tree.
         *
         * <p>
         *  创建一个新的前缀解析树
         * 
         * 
         * @param keys  a set of strings to build the prefix parsing tree, not null
         * @param context  the parse context
         * @return the tree, not null
         */
        public static  PrefixTree newTree(Set<String> keys, DateTimeParseContext context) {
            PrefixTree tree = newTree(context);
            for (String k : keys) {
                tree.add0(k, k);
            }
            return tree;
        }

        /**
         * Clone a copy of this tree
         * <p>
         *  克隆此树的副本
         * 
         */
        public PrefixTree copyTree() {
            PrefixTree copy = new PrefixTree(key, value, null);
            if (child != null) {
                copy.child = child.copyTree();
            }
            if (sibling != null) {
                copy.sibling = sibling.copyTree();
            }
            return copy;
        }


        /**
         * Adds a pair of {key, value} into the prefix tree.
         *
         * <p>
         *  在前缀树中添加一对{key,value}
         * 
         * 
         * @param k  the key, not null
         * @param v  the value, not null
         * @return  true if the pair is added successfully
         */
        public boolean add(String k, String v) {
            return add0(k, v);
        }

        private boolean add0(String k, String v) {
            k = toKey(k);
            int prefixLen = prefixLength(k);
            if (prefixLen == key.length()) {
                if (prefixLen < k.length()) {  // down the tree
                    String subKey = k.substring(prefixLen);
                    PrefixTree c = child;
                    while (c != null) {
                        if (isEqual(c.c0, subKey.charAt(0))) {
                            return c.add0(subKey, v);
                        }
                        c = c.sibling;
                    }
                    // add the node as the child of the current node
                    c = newNode(subKey, v, null);
                    c.sibling = child;
                    child = c;
                    return true;
                }
                // have an existing <key, value> already, overwrite it
                // if (value != null) {
                //    return false;
                //}
                value = v;
                return true;
            }
            // split the existing node
            PrefixTree n1 = newNode(key.substring(prefixLen), value, child);
            key = k.substring(0, prefixLen);
            child = n1;
            if (prefixLen < k.length()) {
                PrefixTree n2 = newNode(k.substring(prefixLen), v, null);
                child.sibling = n2;
                value = null;
            } else {
                value = v;
            }
            return true;
        }

        /**
         * Match text with the prefix tree.
         *
         * <p>
         *  使文本与前缀树匹配
         * 
         * 
         * @param text  the input text to parse, not null
         * @param off  the offset position to start parsing at
         * @param end  the end position to stop parsing
         * @return the resulting string, or null if no match found.
         */
        public String match(CharSequence text, int off, int end) {
            if (!prefixOf(text, off, end)){
                return null;
            }
            if (child != null && (off += key.length()) != end) {
                PrefixTree c = child;
                do {
                    if (isEqual(c.c0, text.charAt(off))) {
                        String found = c.match(text, off, end);
                        if (found != null) {
                            return found;
                        }
                        return value;
                    }
                    c = c.sibling;
                } while (c != null);
            }
            return value;
        }

        /**
         * Match text with the prefix tree.
         *
         * <p>
         *  使文本与前缀树匹配
         * 
         * 
         * @param text  the input text to parse, not null
         * @param pos  the position to start parsing at, from 0 to the text
         *  length. Upon return, position will be updated to the new parse
         *  position, or unchanged, if no match found.
         * @return the resulting string, or null if no match found.
         */
        public String match(CharSequence text, ParsePosition pos) {
            int off = pos.getIndex();
            int end = text.length();
            if (!prefixOf(text, off, end)){
                return null;
            }
            off += key.length();
            if (child != null && off != end) {
                PrefixTree c = child;
                do {
                    if (isEqual(c.c0, text.charAt(off))) {
                        pos.setIndex(off);
                        String found = c.match(text, pos);
                        if (found != null) {
                            return found;
                        }
                        break;
                    }
                    c = c.sibling;
                } while (c != null);
            }
            pos.setIndex(off);
            return value;
        }

        protected String toKey(String k) {
            return k;
        }

        protected PrefixTree newNode(String k, String v, PrefixTree child) {
            return new PrefixTree(k, v, child);
        }

        protected boolean isEqual(char c1, char c2) {
            return c1 == c2;
        }

        protected boolean prefixOf(CharSequence text, int off, int end) {
            if (text instanceof String) {
                return ((String)text).startsWith(key, off);
            }
            int len = key.length();
            if (len > end - off) {
                return false;
            }
            int off0 = 0;
            while (len-- > 0) {
                if (!isEqual(key.charAt(off0++), text.charAt(off++))) {
                    return false;
                }
            }
            return true;
        }

        private int prefixLength(String k) {
            int off = 0;
            while (off < k.length() && off < key.length()) {
                if (!isEqual(k.charAt(off), key.charAt(off))) {
                    return off;
                }
                off++;
            }
            return off;
        }

        /**
         * Case Insensitive prefix tree.
         * <p>
         * 不区分大小写的前缀树
         * 
         */
        private static class CI extends PrefixTree {

            private CI(String k, String v, PrefixTree child) {
                super(k, v, child);
            }

            @Override
            protected CI newNode(String k, String v, PrefixTree child) {
                return new CI(k, v, child);
            }

            @Override
            protected boolean isEqual(char c1, char c2) {
                return DateTimeParseContext.charEqualsIgnoreCase(c1, c2);
            }

            @Override
            protected boolean prefixOf(CharSequence text, int off, int end) {
                int len = key.length();
                if (len > end - off) {
                    return false;
                }
                int off0 = 0;
                while (len-- > 0) {
                    if (!isEqual(key.charAt(off0++), text.charAt(off++))) {
                        return false;
                    }
                }
                return true;
            }
        }

        /**
         * Lenient prefix tree. Case insensitive and ignores characters
         * like space, underscore and slash.
         * <p>
         *  Lenient前缀树不区分大小写,忽略空格,下划线和斜杠等字符
         * 
         */
        private static class LENIENT extends CI {

            private LENIENT(String k, String v, PrefixTree child) {
                super(k, v, child);
            }

            @Override
            protected CI newNode(String k, String v, PrefixTree child) {
                return new LENIENT(k, v, child);
            }

            private boolean isLenientChar(char c) {
                return c == ' ' || c == '_' || c == '/';
            }

            protected String toKey(String k) {
                for (int i = 0; i < k.length(); i++) {
                    if (isLenientChar(k.charAt(i))) {
                        StringBuilder sb = new StringBuilder(k.length());
                        sb.append(k, 0, i);
                        i++;
                        while (i < k.length()) {
                            if (!isLenientChar(k.charAt(i))) {
                                sb.append(k.charAt(i));
                            }
                            i++;
                        }
                        return sb.toString();
                    }
                }
                return k;
            }

            @Override
            public String match(CharSequence text, ParsePosition pos) {
                int off = pos.getIndex();
                int end = text.length();
                int len = key.length();
                int koff = 0;
                while (koff < len && off < end) {
                    if (isLenientChar(text.charAt(off))) {
                        off++;
                        continue;
                    }
                    if (!isEqual(key.charAt(koff++), text.charAt(off++))) {
                        return null;
                    }
                }
                if (koff != len) {
                    return null;
                }
                if (child != null && off != end) {
                    int off0 = off;
                    while (off0 < end && isLenientChar(text.charAt(off0))) {
                        off0++;
                    }
                    if (off0 < end) {
                        PrefixTree c = child;
                        do {
                            if (isEqual(c.c0, text.charAt(off0))) {
                                pos.setIndex(off0);
                                String found = c.match(text, pos);
                                if (found != null) {
                                    return found;
                                }
                                break;
                            }
                            c = c.sibling;
                        } while (c != null);
                    }
                }
                pos.setIndex(off);
                return value;
            }
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses a chronology.
     * <p>
     *  打印或解析年表
     * 
     */
    static final class ChronoPrinterParser implements DateTimePrinterParser {
        /** The text style to output, null means the ID. */
        private final TextStyle textStyle;

        ChronoPrinterParser(TextStyle textStyle) {
            // validated by caller
            this.textStyle = textStyle;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            Chronology chrono = context.getValue(TemporalQueries.chronology());
            if (chrono == null) {
                return false;
            }
            if (textStyle == null) {
                buf.append(chrono.getId());
            } else {
                buf.append(getChronologyName(chrono, context.getLocale()));
            }
            return true;
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            // simple looping parser to find the chronology
            if (position < 0 || position > text.length()) {
                throw new IndexOutOfBoundsException();
            }
            Set<Chronology> chronos = Chronology.getAvailableChronologies();
            Chronology bestMatch = null;
            int matchLen = -1;
            for (Chronology chrono : chronos) {
                String name;
                if (textStyle == null) {
                    name = chrono.getId();
                } else {
                    name = getChronologyName(chrono, context.getLocale());
                }
                int nameLen = name.length();
                if (nameLen > matchLen && context.subSequenceEquals(text, position, name, 0, nameLen)) {
                    bestMatch = chrono;
                    matchLen = nameLen;
                }
            }
            if (bestMatch == null) {
                return ~position;
            }
            context.setParsed(bestMatch);
            return position + matchLen;
        }

        /**
         * Returns the chronology name of the given chrono in the given locale
         * if available, or the chronology Id otherwise. The regular ResourceBundle
         * search path is used for looking up the chronology name.
         *
         * <p>
         *  返回给定语言环境(如果可用)中给定计时器的年表名称,否则返回年表ID常规ResourceBundle搜索路径用于查找年表名称
         * 
         * 
         * @param chrono  the chronology, not null
         * @param locale  the locale, not null
         * @return the chronology name of chrono in locale, or the id if no name is available
         * @throws NullPointerException if chrono or locale is null
         */
        private String getChronologyName(Chronology chrono, Locale locale) {
            String key = "calendarname." + chrono.getCalendarType();
            String name = DateTimeTextProvider.getLocalizedResource(key, locale);
            return name != null ? name : chrono.getId();
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses a localized pattern.
     * <p>
     *  打印或解析本地化模式
     * 
     */
    static final class LocalizedPrinterParser implements DateTimePrinterParser {
        /** Cache of formatters. */
        private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>(16, 0.75f, 2);

        private final FormatStyle dateStyle;
        private final FormatStyle timeStyle;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param dateStyle  the date style to use, may be null
         * @param timeStyle  the time style to use, may be null
         */
        LocalizedPrinterParser(FormatStyle dateStyle, FormatStyle timeStyle) {
            // validated by caller
            this.dateStyle = dateStyle;
            this.timeStyle = timeStyle;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            Chronology chrono = Chronology.from(context.getTemporal());
            return formatter(context.getLocale(), chrono).toPrinterParser(false).format(context, buf);
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            Chronology chrono = context.getEffectiveChronology();
            return formatter(context.getLocale(), chrono).toPrinterParser(false).parse(context, text, position);
        }

        /**
         * Gets the formatter to use.
         * <p>
         * The formatter will be the most appropriate to use for the date and time style in the locale.
         * For example, some locales will use the month name while others will use the number.
         *
         * <p>
         *  获取要使用的格式化程序
         * <p>
         *  格式化程序将最适合用于语言环境中的日期和时间样式例如,某些语言环境将使用月份名称,而其他语言环境将使用该数字
         * 
         * 
         * @param locale  the locale to use, not null
         * @param chrono  the chronology to use, not null
         * @return the formatter, not null
         * @throws IllegalArgumentException if the formatter cannot be found
         */
        private DateTimeFormatter formatter(Locale locale, Chronology chrono) {
            String key = chrono.getId() + '|' + locale.toString() + '|' + dateStyle + timeStyle;
            DateTimeFormatter formatter = FORMATTER_CACHE.get(key);
            if (formatter == null) {
                String pattern = getLocalizedDateTimePattern(dateStyle, timeStyle, chrono, locale);
                formatter = new DateTimeFormatterBuilder().appendPattern(pattern).toFormatter(locale);
                DateTimeFormatter old = FORMATTER_CACHE.putIfAbsent(key, formatter);
                if (old != null) {
                    formatter = old;
                }
            }
            return formatter;
        }

        @Override
        public String toString() {
            return "Localized(" + (dateStyle != null ? dateStyle : "") + "," +
                (timeStyle != null ? timeStyle : "") + ")";
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Prints or parses a localized pattern from a localized field.
     * The specific formatter and parameters is not selected until the
     * the field is to be printed or parsed.
     * The locale is needed to select the proper WeekFields from which
     * the field for day-of-week, week-of-month, or week-of-year is selected.
     * <p>
     * 从本地化字段打印或解析本地化模式在打印或解析字段之前,不会选择特定格式化程序和参数。需要使用语言环境来选择正确的WeekField,从中选择星期,星期,月,或星期
     * 
     */
    static final class WeekBasedFieldPrinterParser implements DateTimePrinterParser {
        private char chr;
        private int count;

        /**
         * Constructor.
         *
         * <p>
         *  构造函数
         * 
         * 
         * @param chr the pattern format letter that added this PrinterParser.
         * @param count the repeat count of the format letter
         */
        WeekBasedFieldPrinterParser(char chr, int count) {
            this.chr = chr;
            this.count = count;
        }

        @Override
        public boolean format(DateTimePrintContext context, StringBuilder buf) {
            return printerParser(context.getLocale()).format(context, buf);
        }

        @Override
        public int parse(DateTimeParseContext context, CharSequence text, int position) {
            return printerParser(context.getLocale()).parse(context, text, position);
        }

        /**
         * Gets the printerParser to use based on the field and the locale.
         *
         * <p>
         *  获取要根据字段和区域设置使用的printerParser
         * 
         * 
         * @param locale  the locale to use, not null
         * @return the formatter, not null
         * @throws IllegalArgumentException if the formatter cannot be found
         */
        private DateTimePrinterParser printerParser(Locale locale) {
            WeekFields weekDef = WeekFields.of(locale);
            TemporalField field = null;
            switch (chr) {
                case 'Y':
                    field = weekDef.weekBasedYear();
                    if (count == 2) {
                        return new ReducedPrinterParser(field, 2, 2, 0, ReducedPrinterParser.BASE_DATE, 0);
                    } else {
                        return new NumberPrinterParser(field, count, 19,
                                (count < 4) ? SignStyle.NORMAL : SignStyle.EXCEEDS_PAD, -1);
                    }
                case 'e':
                case 'c':
                    field = weekDef.dayOfWeek();
                    break;
                case 'w':
                    field = weekDef.weekOfWeekBasedYear();
                    break;
                case 'W':
                    field = weekDef.weekOfMonth();
                    break;
                default:
                    throw new IllegalStateException("unreachable");
            }
            return new NumberPrinterParser(field, (count == 2 ? 2 : 1), 2, SignStyle.NOT_NEGATIVE);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(30);
            sb.append("Localized(");
            if (chr == 'Y') {
                if (count == 1) {
                    sb.append("WeekBasedYear");
                } else if (count == 2) {
                    sb.append("ReducedValue(WeekBasedYear,2,2,2000-01-01)");
                } else {
                    sb.append("WeekBasedYear,").append(count).append(",")
                            .append(19).append(",")
                            .append((count < 4) ? SignStyle.NORMAL : SignStyle.EXCEEDS_PAD);
                }
            } else {
                switch (chr) {
                    case 'c':
                    case 'e':
                        sb.append("DayOfWeek");
                        break;
                    case 'w':
                        sb.append("WeekOfWeekBasedYear");
                        break;
                    case 'W':
                        sb.append("WeekOfMonth");
                        break;
                    default:
                        break;
                }
                sb.append(",");
                sb.append(count);
            }
            sb.append(")");
            return sb.toString();
        }
    }

    //-------------------------------------------------------------------------
    /**
     * Length comparator.
     * <p>
     *  长度比较器
     */
    static final Comparator<String> LENGTH_SORT = new Comparator<String>() {
        @Override
        public int compare(String str1, String str2) {
            return str1.length() == str2.length() ? str1.compareTo(str2) : str1.length() - str2.length();
        }
    };
}
