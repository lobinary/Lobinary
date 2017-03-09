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
 * Copyright (c) 2007-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2007-2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

/**
 * A month-day in the ISO-8601 calendar system, such as {@code --12-03}.
 * <p>
 * {@code MonthDay} is an immutable date-time object that represents the combination
 * of a year and month. Any field that can be derived from a month and day, such as
 * quarter-of-year, can be obtained.
 * <p>
 * This class does not store or represent a year, time or time-zone.
 * For example, the value "December 3rd" can be stored in a {@code MonthDay}.
 * <p>
 * Since a {@code MonthDay} does not possess a year, the leap day of
 * February 29th is considered valid.
 * <p>
 * This class implements {@link TemporalAccessor} rather than {@link Temporal}.
 * This is because it is not possible to define whether February 29th is valid or not
 * without external information, preventing the implementation of plus/minus.
 * Related to this, {@code MonthDay} only provides access to query and set the fields
 * {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH}.
 * <p>
 * The ISO-8601 calendar system is the modern civil calendar system used today
 * in most of the world. It is equivalent to the proleptic Gregorian calendar
 * system, in which today's rules for leap years are applied for all time.
 * For most applications written today, the ISO-8601 rules are entirely suitable.
 * However, any application that makes use of historical dates, and requires them
 * to be accurate will find the ISO-8601 approach unsuitable.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code MonthDay} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  ISO-8601日历系统中的月 - 日,例如{@code  -  12-03}。
 * <p>
 *  {@code MonthDay}是一个不可变的日期时间对象,表示年和月的组合。可以获得可以从一个月和一天(例如一年中的四分之一)导出的任何字段。
 * <p>
 *  此类不存储或表示年,时间或时区。例如,值"12月3日"可以存储在{@code MonthDay}中。
 * <p>
 *  由于{@code MonthDay}不具有年份,因此2月29日的闰年视为有效。
 * <p>
 * 此类实现{@link TemporalAccessor},而不是{@link Temporal}。这是因为如果没有外部信息,不可能定义2月29日是否有效,从而阻止执行加/减。
 * 与此相关的是,{@code MonthDay}只提供查询和设置字段{@code MONTH_OF_YEAR}和{@code DAY_OF_MONTH}的权限。
 * <p>
 *  ISO-8601日历系统是当今世界上使用的现代民用日历系统。它相当于普通的公历日历系统,其中今天的闰年规则适用于所有时间。对于今天编写的大多数应用程序,ISO-8601规则完全合适。
 * 然而,使用历史日期并要求它们准确的任何应用程序将发现ISO-8601方法不适用。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;对{@code MonthDay}实例使用身份敏感操作(
 * 包括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class MonthDay
        implements TemporalAccessor, TemporalAdjuster, Comparable<MonthDay>, Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = -939150713474957432L;
    /**
     * Parser.
     * <p>
     *  解析器。
     * 
     */
    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder()
        .appendLiteral("--")
        .appendValue(MONTH_OF_YEAR, 2)
        .appendLiteral('-')
        .appendValue(DAY_OF_MONTH, 2)
        .toFormatter();

    /**
     * The month-of-year, not null.
     * <p>
     *  年月,不为null。
     * 
     */
    private final int month;
    /**
     * The day-of-month.
     * <p>
     *  日期。
     * 
     */
    private final int day;

    //-----------------------------------------------------------------------
    /**
     * Obtains the current month-day from the system clock in the default time-zone.
     * <p>
     * This will query the {@link java.time.Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current month-day.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区中从系统时钟获取当前月 - 日。
     * <p>
     * 这将在默认时区中查询{@link java.time.Clock#systemDefaultZone()系统时钟}以获取当前月 - 日。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @return the current month-day using the system clock and default time-zone, not null
     */
    public static MonthDay now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current month-day from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(java.time.ZoneId) system clock} to obtain the current month-day.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区从系统时钟获取当前月 - 日。
     * <p>
     *  这将查询{@link Clock#system(java.time.ZoneId)系统时钟}以获取当前月 - 日。指定时区避免了对默认时区的依赖。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current month-day using the system clock, not null
     */
    public static MonthDay now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current month-day from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current month-day.
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前月 - 日。
     * <p>
     *  这将查询指定的时钟以获取当前月 - 日。使用此方法允许使用替代时钟进行测试。可以使用{@link时钟依赖注入}来引入替代时钟。
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current month-day, not null
     */
    public static MonthDay now(Clock clock) {
        final LocalDate now = LocalDate.now(clock);  // called once
        return MonthDay.of(now.getMonth(), now.getDayOfMonth());
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code MonthDay}.
     * <p>
     * The day-of-month must be valid for the month within a leap year.
     * Hence, for February, day 29 is valid.
     * <p>
     * For example, passing in April and day 31 will throw an exception, as
     * there can never be April 31st in any year. By contrast, passing in
     * February 29th is permitted, as that month-day can sometimes be valid.
     *
     * <p>
     *  获取{@code MonthDay}的实例。
     * <p>
     *  月的日期必须对闰年内的月份有效。因此,对于2月,第29天是有效的。
     * <p>
     *  例如,在4月和31日通过将抛出异常,因为在任何一年中永远不会有4月31日。相比之下,允许在2月29日过境,因为该月份有时是有效的。
     * 
     * 
     * @param month  the month-of-year to represent, not null
     * @param dayOfMonth  the day-of-month to represent, from 1 to 31
     * @return the month-day, not null
     * @throws DateTimeException if the value of any field is out of range,
     *  or if the day-of-month is invalid for the month
     */
    public static MonthDay of(Month month, int dayOfMonth) {
        Objects.requireNonNull(month, "month");
        DAY_OF_MONTH.checkValidValue(dayOfMonth);
        if (dayOfMonth > month.maxLength()) {
            throw new DateTimeException("Illegal value for DayOfMonth field, value " + dayOfMonth +
                    " is not valid for month " + month.name());
        }
        return new MonthDay(month.getValue(), dayOfMonth);
    }

    /**
     * Obtains an instance of {@code MonthDay}.
     * <p>
     * The day-of-month must be valid for the month within a leap year.
     * Hence, for month 2 (February), day 29 is valid.
     * <p>
     * For example, passing in month 4 (April) and day 31 will throw an exception, as
     * there can never be April 31st in any year. By contrast, passing in
     * February 29th is permitted, as that month-day can sometimes be valid.
     *
     * <p>
     *  获取{@code MonthDay}的实例。
     * <p>
     *  月的日期必须对闰年内的月份有效。因此,对于第2个月(2月),第29天有效。
     * <p>
     * 例如,在第4个月(4月)和第31天通过会抛出异常,因为任何一年都不会有4月31日。相比之下,允许在2月29日过境,因为该月份有时是有效的。
     * 
     * 
     * @param month  the month-of-year to represent, from 1 (January) to 12 (December)
     * @param dayOfMonth  the day-of-month to represent, from 1 to 31
     * @return the month-day, not null
     * @throws DateTimeException if the value of any field is out of range,
     *  or if the day-of-month is invalid for the month
     */
    public static MonthDay of(int month, int dayOfMonth) {
        return of(Month.of(month), dayOfMonth);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code MonthDay} from a temporal object.
     * <p>
     * This obtains a month-day based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code MonthDay}.
     * <p>
     * The conversion extracts the {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} and
     * {@link ChronoField#DAY_OF_MONTH DAY_OF_MONTH} fields.
     * The extraction is only permitted if the temporal object has an ISO
     * chronology, or can be converted to a {@code LocalDate}.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code MonthDay::from}.
     *
     * <p>
     *  从临时对象获取{@code MonthDay}的实例。
     * <p>
     *  这基于指定的时间获得月 - 日。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂会将其转换为{@code MonthDay}的实例。
     * <p>
     *  转换会撷取{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR}和{@link ChronoField#DAY_OF_MONTH DAY_OF_MONTH}个栏
     * 位。
     * 仅当时间对象具有ISO年表时,才允许提取,或者可以将其转换为{@code LocalDate}。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许通过方法引用{@code MonthDay :: from}在查询中使用它。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the month-day, not null
     * @throws DateTimeException if unable to convert to a {@code MonthDay}
     */
    public static MonthDay from(TemporalAccessor temporal) {
        if (temporal instanceof MonthDay) {
            return (MonthDay) temporal;
        }
        try {
            if (IsoChronology.INSTANCE.equals(Chronology.from(temporal)) == false) {
                temporal = LocalDate.from(temporal);
            }
            return of(temporal.get(MONTH_OF_YEAR), temporal.get(DAY_OF_MONTH));
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain MonthDay from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code MonthDay} from a text string such as {@code --12-03}.
     * <p>
     * The string must represent a valid month-day.
     * The format is {@code --MM-dd}.
     *
     * <p>
     *  从文本字符串(例如{@code  -  12-03})获取{@code MonthDay}的实例。
     * <p>
     *  字符串必须表示有效的月 - 日。格式为{@code  -  MM-dd}。
     * 
     * 
     * @param text  the text to parse such as "--12-03", not null
     * @return the parsed month-day, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static MonthDay parse(CharSequence text) {
        return parse(text, PARSER);
    }

    /**
     * Obtains an instance of {@code MonthDay} from a text string using a specific formatter.
     * <p>
     * The text is parsed using the formatter, returning a month-day.
     *
     * <p>
     *  使用特定格式化程序从文本字符串中获取{@code MonthDay}的实例。
     * <p>
     *  使用格式化程序解析文本,返回一个月 - 日。
     * 
     * 
     * @param text  the text to parse, not null
     * @param formatter  the formatter to use, not null
     * @return the parsed month-day, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static MonthDay parse(CharSequence text, DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, MonthDay::from);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor, previously validated.
     *
     * <p>
     *  构造函数,先前已验证。
     * 
     * 
     * @param month  the month-of-year to represent, validated from 1 to 12
     * @param dayOfMonth  the day-of-month to represent, validated from 1 to 29-31
     */
    private MonthDay(int month, int dayOfMonth) {
        this.month = month;
        this.day = dayOfMonth;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this month-day can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range} and
     * {@link #get(TemporalField) get} methods will throw an exception.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The supported fields are:
     * <ul>
     * <li>{@code MONTH_OF_YEAR}
     * <li>{@code YEAR}
     * </ul>
     * All other {@code ChronoField} instances will return false.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.isSupportedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the field is supported is determined by the field.
     *
     * <p>
     *  检查是否支持指定的字段。
     * <p>
     * 这将检查是否可以查询指定字段的这个月 - 日。
     * 如果为false,则调用{@link #range(TemporalField)range}和{@link #get(TemporalField)get}方法将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。支持的字段包括：
     * <ul>
     *  <li> {@ code MONTH_OF_YEAR} <li> {@ code YEAR}
     * </ul>
     *  所有其他{@code ChronoField}实例将返回false。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this month-day, false if not
     */
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field == MONTH_OF_YEAR || field == DAY_OF_MONTH;
        }
        return field != null && field.isSupportedBy(this);
    }

    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This month-day is used to enhance the accuracy of the returned range.
     * If it is not possible to return the range, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return
     * appropriate range instances.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.rangeRefinedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the range can be obtained is determined by the field.
     *
     * <p>
     *  获取指定字段的有效值范围。
     * <p>
     *  范围对象表示字段的最小和最大有效值。这个月 - 日用于提高返回范围的准确性。如果不可能返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)supported fields}会传回适当的范围执行个体。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.rangeRefinedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
     * 是否可以获得范围由字段确定。
     * 
     * 
     * @param field  the field to query the range for, not null
     * @return the range of valid values for the field, not null
     * @throws DateTimeException if the range for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     */
    @Override
    public ValueRange range(TemporalField field) {
        if (field == MONTH_OF_YEAR) {
            return field.range();
        } else if (field == DAY_OF_MONTH) {
            return ValueRange.of(1, getMonth().minLength(), getMonth().maxLength());
        }
        return TemporalAccessor.super.range(field);
    }

    /**
     * Gets the value of the specified field from this month-day as an {@code int}.
     * <p>
     * This queries this month-day for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this month-day.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此月 - 日获取指定字段的值为{@code int}。
     * <p>
     *  这将查询指定字段的值的这个月 - 日。返回的值将始终在字段的有效值范围内。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)支持的字段}将根据此月 - 日返回有效值。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * 是否可以获取该值以及该值表示什么,由字段确定。
     * 
     * 
     * @param field  the field to get, not null
     * @return the value for the field
     * @throws DateTimeException if a value for the field cannot be obtained or
     *         the value is outside the range of valid values for the field
     * @throws UnsupportedTemporalTypeException if the field is not supported or
     *         the range of values exceeds an {@code int}
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override  // override for Javadoc
    public int get(TemporalField field) {
        return range(field).checkValidIntValue(getLong(field), field);
    }

    /**
     * Gets the value of the specified field from this month-day as a {@code long}.
     * <p>
     * This queries this month-day for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this month-day.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此月 - 日获取指定字段的值为{@code long}。
     * <p>
     *  这将查询指定字段的值的这个月 - 日。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     * 如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)支持的字段}将根据此月 - 日返回有效值。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * 是否可以获取该值以及该值表示什么,由字段确定。
     * 
     * 
     * @param field  the field to get, not null
     * @return the value for the field
     * @throws DateTimeException if a value for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                // alignedDOW and alignedWOM not supported because they cannot be set in with()
                case DAY_OF_MONTH: return day;
                case MONTH_OF_YEAR: return month;
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.getFrom(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the month-of-year field from 1 to 12.
     * <p>
     * This method returns the month as an {@code int} from 1 to 12.
     * Application code is frequently clearer if the enum {@link Month}
     * is used by calling {@link #getMonth()}.
     *
     * <p>
     *  获取年份字段从1到12。
     * <p>
     *  此方法将{@code int}的月份从1返回到12.如果调用{@link #getMonth()}使用枚举{@link Month},应用程序代码通常更清晰。
     * 
     * 
     * @return the month-of-year, from 1 to 12
     * @see #getMonth()
     */
    public int getMonthValue() {
        return month;
    }

    /**
     * Gets the month-of-year field using the {@code Month} enum.
     * <p>
     * This method returns the enum {@link Month} for the month.
     * This avoids confusion as to what {@code int} values mean.
     * If you need access to the primitive {@code int} value then the enum
     * provides the {@link Month#getValue() int value}.
     *
     * <p>
     *  使用{@code Month}枚举获取年份字段。
     * <p>
     *  此方法返回当月的枚举{@link Month}。这避免了对{@code int}值意味着什么的混淆。
     * 如果你需要访问原始的{@code int}值,那么枚举提供{@link Month#getValue()int value}。
     * 
     * 
     * @return the month-of-year, not null
     * @see #getMonthValue()
     */
    public Month getMonth() {
        return Month.of(month);
    }

    /**
     * Gets the day-of-month field.
     * <p>
     * This method returns the primitive {@code int} value for the day-of-month.
     *
     * <p>
     *  获取日期字段。
     * <p>
     *  此方法返回日期的原始{@code int}值。
     * 
     * 
     * @return the day-of-month, from 1 to 31
     */
    public int getDayOfMonth() {
        return day;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the year is valid for this month-day.
     * <p>
     * This method checks whether this month and day and the input year form
     * a valid date. This can only return false for February 29th.
     *
     * <p>
     *  检查年份是否对此月 - 日有效。
     * <p>
     *  此方法检查此月份和输入年份是否形成有效日期。这只能在2月29日返回假。
     * 
     * 
     * @param year  the year to validate, an out of range value returns false
     * @return true if the year is valid for this month-day
     * @see Year#isValidMonthDay(MonthDay)
     */
    public boolean isValidYear(int year) {
        return (day == 29 && month == 2 && Year.isLeap(year) == false) == false;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code MonthDay} with the month-of-year altered.
     * <p>
     * This returns a month-day with the specified month.
     * If the day-of-month is invalid for the specified month, the day will
     * be adjusted to the last valid day-of-month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code MonthDay}的副本,其中的年份已更改。
     * <p>
     * 这将返回具有指定月份的月 - 日。如果指定月份的月份无效,那么日期将被调整为最后一个有效的月份。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param month  the month-of-year to set in the returned month-day, from 1 (January) to 12 (December)
     * @return a {@code MonthDay} based on this month-day with the requested month, not null
     * @throws DateTimeException if the month-of-year value is invalid
     */
    public MonthDay withMonth(int month) {
        return with(Month.of(month));
    }

    /**
     * Returns a copy of this {@code MonthDay} with the month-of-year altered.
     * <p>
     * This returns a month-day with the specified month.
     * If the day-of-month is invalid for the specified month, the day will
     * be adjusted to the last valid day-of-month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code MonthDay}的副本,其中的年份已更改。
     * <p>
     *  这将返回具有指定月份的月 - 日。如果指定月份的月份无效,那么日期将被调整为最后一个有效的月份。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param month  the month-of-year to set in the returned month-day, not null
     * @return a {@code MonthDay} based on this month-day with the requested month, not null
     */
    public MonthDay with(Month month) {
        Objects.requireNonNull(month, "month");
        if (month.getValue() == this.month) {
            return this;
        }
        int day = Math.min(this.day, month.maxLength());
        return new MonthDay(month.getValue(), day);
    }

    /**
     * Returns a copy of this {@code MonthDay} with the day-of-month altered.
     * <p>
     * This returns a month-day with the specified day-of-month.
     * If the day-of-month is invalid for the month, an exception is thrown.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code MonthDay}的副本,其中的月份已更改。
     * <p>
     *  这将返回具有指定的日期的月 - 日。如果该月的日期无效,则抛出异常。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param dayOfMonth  the day-of-month to set in the return month-day, from 1 to 31
     * @return a {@code MonthDay} based on this month-day with the requested day, not null
     * @throws DateTimeException if the day-of-month value is invalid,
     *  or if the day-of-month is invalid for the month
     */
    public MonthDay withDayOfMonth(int dayOfMonth) {
        if (dayOfMonth == this.day) {
            return this;
        }
        return of(month, dayOfMonth);
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this month-day using the specified query.
     * <p>
     * This queries this month-day using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此月 - 日。
     * <p>
     *  这将使用指定的查询策略对象在此月 - 日查询。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
     * <p>
     *  此方法的结果是通过对指定的查询调用{@link TemporalQuery#queryFrom(TemporalAccessor)}方法传递{@code this}作为参数来获得的。
     * 
     * 
     * @param <R> the type of the result
     * @param query  the query to invoke, not null
     * @return the query result, null may be returned (defined by the query)
     * @throws DateTimeException if unable to query (defined by the query)
     * @throws ArithmeticException if numeric overflow occurs (defined by the query)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.chronology()) {
            return (R) IsoChronology.INSTANCE;
        }
        return TemporalAccessor.super.query(query);
    }

    /**
     * Adjusts the specified temporal object to have this month-day.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the month and day-of-month changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * twice, passing {@link ChronoField#MONTH_OF_YEAR} and
     * {@link ChronoField#DAY_OF_MONTH} as the fields.
     * If the specified temporal object does not use the ISO calendar system then
     * a {@code DateTimeException} is thrown.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisMonthDay.adjustInto(temporal);
     *   temporal = temporal.with(thisMonthDay);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为具有此月 - 日。
     * <p>
     *  这返回一个与输入相同的observable类型的临时对象,月和月的日期与此相同。
     * <p>
     * 此调整等同于使用{@link Temporal#with(TemporalField,long)}两次,传递{@link ChronoField#MONTH_OF_YEAR}和{@link ChronoField#DAY_OF_MONTH}
     * 作为字段。
     * 如果指定的临时对象不使用ISO日历系统,那么将抛出{@code DateTimeException}。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐temporal = thisMonthDay.adjustInto(temporal); temporal = temporal.with(thisMonthD
     * ay);。
     * </pre>
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param temporal  the target object to be adjusted, not null
     * @return the adjusted object, not null
     * @throws DateTimeException if unable to make the adjustment
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        if (Chronology.from(temporal).equals(IsoChronology.INSTANCE) == false) {
            throw new DateTimeException("Adjustment only supported on ISO date-time");
        }
        temporal = temporal.with(MONTH_OF_YEAR, month);
        return temporal.with(DAY_OF_MONTH, Math.min(temporal.range(DAY_OF_MONTH).getMaximum(), day));
    }

    /**
     * Formats this month-day using the specified formatter.
     * <p>
     * This month-day will be passed to the formatter to produce a string.
     *
     * <p>
     *  使用指定的格式化程序在这个月 - 日格式化。
     * <p>
     *  这个月 - 日将被传递给格式器以产生一个字符串。
     * 
     * 
     * @param formatter  the formatter to use, not null
     * @return the formatted month-day string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    public String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Combines this month-day with a year to create a {@code LocalDate}.
     * <p>
     * This returns a {@code LocalDate} formed from this month-day and the specified year.
     * <p>
     * A month-day of February 29th will be adjusted to February 28th in the resulting
     * date if the year is not a leap year.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将这个月 - 一年与一年创建一个{@code LocalDate}。
     * <p>
     *  这将返回从此月 - 日和指定年份形成的{@code LocalDate}。
     * <p>
     *  如果该年不是闰年,则2月29日的月 - 日将在所得日期调整为2月28日。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param year  the year to use, from MIN_YEAR to MAX_YEAR
     * @return the local date formed from this month-day and the specified year, not null
     * @throws DateTimeException if the year is outside the valid range of years
     */
    public LocalDate atYear(int year) {
        return LocalDate.of(year, month, isValidYear(year) ? day : 28);
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this month-day to another month-day.
     * <p>
     * The comparison is based first on value of the month, then on the value of the day.
     * It is "consistent with equals", as defined by {@link Comparable}.
     *
     * <p>
     *  将此月 - 日与另一个月 - 日比较。
     * <p>
     *  比较首先基于月的值,然后基于一天的值。它是"与等号一致",由{@link Comparable}定义。
     * 
     * 
     * @param other  the other month-day to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    public int compareTo(MonthDay other) {
        int cmp = (month - other.month);
        if (cmp == 0) {
            cmp = (day - other.day);
        }
        return cmp;
    }

    /**
     * Is this month-day after the specified month-day.
     *
     * <p>
     *  是指定月 - 日后的这个月 - 日。
     * 
     * 
     * @param other  the other month-day to compare to, not null
     * @return true if this is after the specified month-day
     */
    public boolean isAfter(MonthDay other) {
        return compareTo(other) > 0;
    }

    /**
     * Is this month-day before the specified month-day.
     *
     * <p>
     *  这是指定月份日之前的这个月 - 日。
     * 
     * 
     * @param other  the other month-day to compare to, not null
     * @return true if this point is before the specified month-day
     */
    public boolean isBefore(MonthDay other) {
        return compareTo(other) < 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this month-day is equal to another month-day.
     * <p>
     * The comparison is based on the time-line position of the month-day within a year.
     *
     * <p>
     *  检查此月份是否等于另一个月份。
     * <p>
     * 比较基于一年中的月 - 日的时间线位置。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other month-day
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MonthDay) {
            MonthDay other = (MonthDay) obj;
            return month == other.month && day == other.day;
        }
        return false;
    }

    /**
     * A hash code for this month-day.
     *
     * <p>
     *  这个月 - 日的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return (month << 6) + day;
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this month-day as a {@code String}, such as {@code --12-03}.
     * <p>
     * The output will be in the format {@code --MM-dd}:
     *
     * <p>
     *  将此月 - 日输出为{@code String},例如{@code --12-03}。
     * <p>
     *  输出将采用格式{@code  -  MM-dd}：
     * 
     * 
     * @return a string representation of this month-day, not null
     */
    @Override
    public String toString() {
        return new StringBuilder(10).append("--")
            .append(month < 10 ? "0" : "").append(month)
            .append(day < 10 ? "-0" : "-").append(day)
            .toString();
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     *  使用<a href="../../serialized-form.html#java.time.Ser">专用序列化表单</a>写入对象。
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(13);  // identifies a MonthDay
     *  out.writeByte(month);
     *  out.writeByte(day);
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.MONTH_DAY_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     *  防御恶意流。
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeByte(month);
        out.writeByte(day);
    }

    static MonthDay readExternal(DataInput in) throws IOException {
        byte month = in.readByte();
        byte day = in.readByte();
        return MonthDay.of(month, day);
    }

}
