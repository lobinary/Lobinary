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
package java.time;

import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

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
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

/**
 * A year-month in the ISO-8601 calendar system, such as {@code 2007-12}.
 * <p>
 * {@code YearMonth} is an immutable date-time object that represents the combination
 * of a year and month. Any field that can be derived from a year and month, such as
 * quarter-of-year, can be obtained.
 * <p>
 * This class does not store or represent a day, time or time-zone.
 * For example, the value "October 2007" can be stored in a {@code YearMonth}.
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
 * {@code YearMonth} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 * 在ISO-8601日历系统中的一个年月,例如{@code 2007-12}
 * <p>
 *  {@code YearMonth}是一个不可变的日期时间对象,表示年和月的组合可以从一年和一月导出的任何字段,如四分之一年
 * <p>
 *  此类不存储或表示日期,时间或时区。例如,值"2007年10月"可以存储在{@code YearMonth}
 * <p>
 * ISO-8601日历系统是当今世界上大多数地区使用的现代民用日历系统。
 * 它相当于普通的公历日历系统,其中适用于闰年的今天规则对于今天大多数应用,ISO- 8601规则完全合适但是,任何使用历史日期并要求它们准确的应用程序都会发现ISO-8601方法不适用。
 * 
 * <p>
 *  这是<a href=\"{@docRoot}/java/lang/doc-files/ValueBasedhtml\">基于价值的</a>类;在{@code YearMonth}的实例上使用身份敏感操
 * 作(包括引用相等({@code ==}),身份哈希码或同步)可能有不可预测的结果,应该避免使用{@code equals}方法应该用于比较。
 * 
 * @implSpec这个类是不可变的和线程安全的
 * 
 * 
 * @since 1.8
 */
public final class YearMonth
        implements Temporal, TemporalAdjuster, Comparable<YearMonth>, Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本
     * 
     */
    private static final long serialVersionUID = 4183400860270640070L;
    /**
     * Parser.
     * <p>
     *  解析器
     * 
     */
    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder()
        .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
        .appendLiteral('-')
        .appendValue(MONTH_OF_YEAR, 2)
        .toFormatter();

    /**
     * The year.
     * <p>
     *  那一年
     * 
     */
    private final int year;
    /**
     * The month-of-year, not null.
     * <p>
     *  年月,不为null
     * 
     */
    private final int month;

    //-----------------------------------------------------------------------
    /**
     * Obtains the current year-month from the system clock in the default time-zone.
     * <p>
     * This will query the {@link java.time.Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current year-month.
     * The zone and offset will be set based on the time-zone in the clock.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区中从系统时钟获取当前年 - 月
     * <p>
     *  这将查询默认时区中的{@link javatimeClock#systemDefaultZone()系统时钟}以获取当前年 - 月。区域和偏移将根据时钟中的时区设置
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的
     * 
     * 
     * @return the current year-month using the system clock and default time-zone, not null
     */
    public static YearMonth now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current year-month from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(java.time.ZoneId) system clock} to obtain the current year-month.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区中从系统时钟获取当前年 - 月
     * <p>
     *  这将查询{@link Clock#system(javatimeZoneId)系统时钟}以获取当前年 - 月指定时区避免依赖于默认时区
     * <p>
     * 使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current year-month using the system clock, not null
     */
    public static YearMonth now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current year-month from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current year-month.
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前年 - 月
     * <p>
     *  这将查询指定的时钟以获取当前的年 - 月使用此方法允许使用备用时钟进行测试可以使用{@link时钟依赖注入}
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current year-month, not null
     */
    public static YearMonth now(Clock clock) {
        final LocalDate now = LocalDate.now(clock);  // called once
        return YearMonth.of(now.getYear(), now.getMonth());
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code YearMonth} from a year and month.
     *
     * <p>
     *  从一年和一月获取{@code YearMonth}的实例
     * 
     * 
     * @param year  the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, not null
     * @return the year-month, not null
     * @throws DateTimeException if the year value is invalid
     */
    public static YearMonth of(int year, Month month) {
        Objects.requireNonNull(month, "month");
        return of(year, month.getValue());
    }

    /**
     * Obtains an instance of {@code YearMonth} from a year and month.
     *
     * <p>
     *  从一年和一月获取{@code YearMonth}的实例
     * 
     * 
     * @param year  the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, from 1 (January) to 12 (December)
     * @return the year-month, not null
     * @throws DateTimeException if either field value is invalid
     */
    public static YearMonth of(int year, int month) {
        YEAR.checkValidValue(year);
        MONTH_OF_YEAR.checkValidValue(month);
        return new YearMonth(year, month);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code YearMonth} from a temporal object.
     * <p>
     * This obtains a year-month based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code YearMonth}.
     * <p>
     * The conversion extracts the {@link ChronoField#YEAR YEAR} and
     * {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} fields.
     * The extraction is only permitted if the temporal object has an ISO
     * chronology, or can be converted to a {@code LocalDate}.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code YearMonth::from}.
     *
     * <p>
     *  从临时对象获取{@code YearMonth}的实例
     * <p>
     *  这将根据指定的时间获取一个年月份{@code TemporalAccessor}表示一组任意的日期和时间信息,该工厂转换为{@code YearMonth}
     * <p>
     * 转换提取{@link ChronoField#YEAR YEAR}和{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR}字段仅当时间对象具有ISO年表时,才允许
     * 提取,或者可以将其转换为{@code LocalDate}。
     * <p>
     *  此方法匹配函数接口{@link TemporalQuery}的签名,允许通过方法引用在查询中使用{@code YearMonth :: from}
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the year-month, not null
     * @throws DateTimeException if unable to convert to a {@code YearMonth}
     */
    public static YearMonth from(TemporalAccessor temporal) {
        if (temporal instanceof YearMonth) {
            return (YearMonth) temporal;
        }
        Objects.requireNonNull(temporal, "temporal");
        try {
            if (IsoChronology.INSTANCE.equals(Chronology.from(temporal)) == false) {
                temporal = LocalDate.from(temporal);
            }
            return of(temporal.get(YEAR), temporal.get(MONTH_OF_YEAR));
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain YearMonth from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code YearMonth} from a text string such as {@code 2007-12}.
     * <p>
     * The string must represent a valid year-month.
     * The format must be {@code uuuu-MM}.
     * Years outside the range 0000 to 9999 must be prefixed by the plus or minus symbol.
     *
     * <p>
     *  从文本字符串(例如{@code 2007-12})获取{@code YearMonth}的实例,
     * <p>
     *  字符串必须表示有效的年 - 月格式必须为{@code uuuu-MM} 0000到9999之间的年份必须以加号或减号为前缀
     * 
     * 
     * @param text  the text to parse such as "2007-12", not null
     * @return the parsed year-month, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static YearMonth parse(CharSequence text) {
        return parse(text, PARSER);
    }

    /**
     * Obtains an instance of {@code YearMonth} from a text string using a specific formatter.
     * <p>
     * The text is parsed using the formatter, returning a year-month.
     *
     * <p>
     *  使用特定格式化程序从文本字符串获取{@code YearMonth}的实例
     * <p>
     * 使用格式化程序解析文本,返回年 - 月
     * 
     * 
     * @param text  the text to parse, not null
     * @param formatter  the formatter to use, not null
     * @return the parsed year-month, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static YearMonth parse(CharSequence text, DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, YearMonth::from);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * <p>
     *  构造函数
     * 
     * 
     * @param year  the year to represent, validated from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, validated from 1 (January) to 12 (December)
     */
    private YearMonth(int year, int month) {
        this.year = year;
        this.month = month;
    }

    /**
     * Returns a copy of this year-month with the new year and month, checking
     * to see if a new object is in fact required.
     *
     * <p>
     *  返回带有新的年份和月份的本年月份的副本,检查是否确实需要新对象
     * 
     * 
     * @param newYear  the year to represent, validated from MIN_YEAR to MAX_YEAR
     * @param newMonth  the month-of-year to represent, validated not null
     * @return the year-month, not null
     */
    private YearMonth with(int newYear, int newMonth) {
        if (year == newYear && month == newMonth) {
            return this;
        }
        return new YearMonth(newYear, newMonth);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this year-month can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range},
     * {@link #get(TemporalField) get} and {@link #with(TemporalField, long)}
     * methods will throw an exception.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The supported fields are:
     * <ul>
     * <li>{@code MONTH_OF_YEAR}
     * <li>{@code PROLEPTIC_MONTH}
     * <li>{@code YEAR_OF_ERA}
     * <li>{@code YEAR}
     * <li>{@code ERA}
     * </ul>
     * All other {@code ChronoField} instances will return false.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.isSupportedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the field is supported is determined by the field.
     *
     * <p>
     *  检查是否支持指定的字段
     * <p>
     *  这将检查是否可以查询指定字段的年份月份如果为false,则调用{@link #range(TemporalField)范围},{@link #get(TemporalField)get}和{@link #with(TemporalField, long)}
     * 方法会抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此实现查询支持的字段为：
     * <ul>
     *  <li> {@ code MONTH_OF_YEAR} <li> {@ code PROLEPTIC_MONTH} <li> {@ code YEAR_OF_ERA} <li> {@ code YEAR}
     *  <li> {@ code ERA}。
     * </ul>
     *  所有其他{@code ChronoField}实例将返回false
     * <p>
     * 如果字段不是{@code ChronoField},则通过调用{@code TemporalFieldisSupportedBy(TemporalAccessor)}传递{@code this}作为参数
     * 来获得此方法的结果。
     * 字段是否受支持由字段。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this year-month, false if not
     */
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field == YEAR || field == MONTH_OF_YEAR ||
                    field == PROLEPTIC_MONTH || field == YEAR_OF_ERA || field == ERA;
        }
        return field != null && field.isSupportedBy(this);
    }

    /**
     * Checks if the specified unit is supported.
     * <p>
     * This checks if the specified unit can be added to, or subtracted from, this date-time.
     * If false, then calling the {@link #plus(long, TemporalUnit)} and
     * {@link #minus(long, TemporalUnit) minus} methods will throw an exception.
     * <p>
     * If the unit is a {@link ChronoUnit} then the query is implemented here.
     * The supported units are:
     * <ul>
     * <li>{@code MONTHS}
     * <li>{@code YEARS}
     * <li>{@code DECADES}
     * <li>{@code CENTURIES}
     * <li>{@code MILLENNIA}
     * <li>{@code ERAS}
     * </ul>
     * All other {@code ChronoUnit} instances will return false.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.isSupportedBy(Temporal)}
     * passing {@code this} as the argument.
     * Whether the unit is supported is determined by the unit.
     *
     * <p>
     *  检查是否支持指定的单元
     * <p>
     *  这将检查指定的单元是否可以添加到此日期时间或从此日期时间减去。
     * 如果为false,则调用{@link #plus(long,TemporalUnit)}和{@link #minus(long,TemporalUnit)minus}会抛出异常。
     * <p>
     *  如果单位是{@link ChronoUnit},则在此实现查询支持的单位是：
     * <ul>
     *  <li> {@ code MONTHS} <li> {@ code YEARS} <li> {@ code DECADES} <li> {@ code CENTURIES} <li> {@ code MILLENNIA}
     *  <li> {@ code ERAS}。
     * </ul>
     *  所有其他{@code ChronoUnit}实例将返回false
     * <p>
     * 如果单元不是{@code ChronoUnit},则通过调用{@code TemporalUnitisSupportedBy(Temporal)}传递{@code this}作为参数获得此方法的结果。
     * 单元是否受支持由单元确定。
     * 
     * 
     * @param unit  the unit to check, null returns false
     * @return true if the unit can be added/subtracted, false if not
     */
    @Override
    public boolean isSupported(TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            return unit == MONTHS || unit == YEARS || unit == DECADES || unit == CENTURIES || unit == MILLENNIA || unit == ERAS;
        }
        return unit != null && unit.isSupportedBy(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This year-month is used to enhance the accuracy of the returned range.
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
     *  获取指定字段的有效值范围
     * <p>
     *  范围对象表示字段的最小和最大有效值该年 - 月用于提高返回范围的准确性如果不可能返回范围,因为不支持该字段或由于某种其他原因,异常被抛出
     * <p>
     * 如果字段是{@link ChronoField},则在此实现查询。
     * {@link #isSupported(TemporalField)supported fields}将返回适当的范围实例所有其他{@code ChronoField}实例将抛出一个{@code UnsupportedTemporalTypeException}
     * 。
     * 如果字段是{@link ChronoField},则在此实现查询。
     * <p>
     *  如果字段不是{@code ChronoField},则通过调用{@code TemporalFieldrangeRefinedBy(TemporalAccessor)}传递{@code this}作为
     * 参数来获得此方法的结果。
     * 是否可以获得范围由字段。
     * 
     * 
     * @param field  the field to query the range for, not null
     * @return the range of valid values for the field, not null
     * @throws DateTimeException if the range for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     */
    @Override
    public ValueRange range(TemporalField field) {
        if (field == YEAR_OF_ERA) {
            return (getYear() <= 0 ? ValueRange.of(1, Year.MAX_VALUE + 1) : ValueRange.of(1, Year.MAX_VALUE));
        }
        return Temporal.super.range(field);
    }

    /**
     * Gets the value of the specified field from this year-month as an {@code int}.
     * <p>
     * This queries this year-month for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this year-month, except {@code PROLEPTIC_MONTH} which is too
     * large to fit in an {@code int} and throw a {@code DateTimeException}.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从这个年月中获取指定字段的值作为{@code int}
     * <p>
     * 这将查询指定字段的值的年份月份返回的值将始终在字段的有效值范围内如果不可能返回该值,因为该字段不受支持或由于某种其他原因,抛出异常
     * <p>
     *  如果该字段是{@link ChronoField},则此处实现查询。
     * {@link #isSupported(TemporalField)supported fields}将根据此年份返回有效值,但{@code PROLEPTIC_MONTH}过大,适合{@code int}
     * 并抛出{@code DateTimeException}所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     *  如果该字段是{@link ChronoField},则此处实现查询。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalFieldgetFrom(TemporalAccessor)}传递{@code this}作为参数获得此方法
     * 的结果。
     * 是否可以获取该值,以及值表示,由字段确定。
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
     * Gets the value of the specified field from this year-month as a {@code long}.
     * <p>
     * This queries this year-month for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this year-month.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  将此年份中指定字段的值作为{@code long}
     * <p>
     *  这将查询指定字段的值的年 - 月如果不可能返回值,因为该字段不受支持或由于某种其他原因,抛出异常
     * <p>
     * 如果字段是{@link ChronoField},那么在此实现查询。
     * {@link #isSupported(TemporalField)supported fields}将根据本年月份返回有效值所有其他{@code ChronoField}实例将抛出一个{ @code UnsupportedTemporalTypeException}
     * 。
     * 如果字段是{@link ChronoField},那么在此实现查询。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalFieldgetFrom(TemporalAccessor)}传递{@code this}作为参数获得此方
     * 法的结果。
     * 是否可以获取该值,以及值表示,由字段确定。
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
                case MONTH_OF_YEAR: return month;
                case PROLEPTIC_MONTH: return getProlepticMonth();
                case YEAR_OF_ERA: return (year < 1 ? 1 - year : year);
                case YEAR: return year;
                case ERA: return (year < 1 ? 0 : 1);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.getFrom(this);
    }

    private long getProlepticMonth() {
        return (year * 12L + month - 1);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the year field.
     * <p>
     * This method returns the primitive {@code int} value for the year.
     * <p>
     * The year returned by this method is proleptic as per {@code get(YEAR)}.
     *
     * <p>
     *  获取年份字段
     * <p>
     *  此方法返回年份的原始{@code int}值
     * <p>
     *  这个方法返回的年份是{@code get(YEAR)}
     * 
     * 
     * @return the year, from MIN_YEAR to MAX_YEAR
     */
    public int getYear() {
        return year;
    }

    /**
     * Gets the month-of-year field from 1 to 12.
     * <p>
     * This method returns the month as an {@code int} from 1 to 12.
     * Application code is frequently clearer if the enum {@link Month}
     * is used by calling {@link #getMonth()}.
     *
     * <p>
     *  获取年份字段从1到12
     * <p>
     * 此方法将{@code int}从1到12的月份返回为应用程序代码通常更清晰,如果调用{@link #getMonth()}使用枚举{@link Month}
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
     *  使用{@code Month}枚举获取年份字段
     * <p>
     *  此方法返回该月的枚举{@link Month}这避免了对{@code int}值的含义的混淆如果您需要访问原始{@code int}值,则枚举提供{@link Month#getValue ()int value}
     * 。
     * 
     * 
     * @return the month-of-year, not null
     * @see #getMonthValue()
     */
    public Month getMonth() {
        return Month.of(month);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the year is a leap year, according to the ISO proleptic
     * calendar system rules.
     * <p>
     * This method applies the current rules for leap years across the whole time-line.
     * In general, a year is a leap year if it is divisible by four without
     * remainder. However, years divisible by 100, are not leap years, with
     * the exception of years divisible by 400 which are.
     * <p>
     * For example, 1904 is a leap year it is divisible by 4.
     * 1900 was not a leap year as it is divisible by 100, however 2000 was a
     * leap year as it is divisible by 400.
     * <p>
     * The calculation is proleptic - applying the same rules into the far future and far past.
     * This is historically inaccurate, but is correct for the ISO-8601 standard.
     *
     * <p>
     *  根据ISO proleptic日历系统规则检查年份是否为闰年
     * <p>
     * 这种方法适用于整个时间线的闰年的现行规则一般来说,一年是一个闰年,如果它可以除以四而没有余数,然而,年除以100,不是闰年,除了年可整除由400
     * <p>
     *  例如,1904年是一个闰年,可以被4整除1900年不是一个闰年,因为它可以被100整除,但2000年是一个闰年,因为它可以被400整除
     * <p>
     *  计算是推测的 - 将相同的规则应用到远远和远远过去这是历史上不准确的,但是对于ISO-8601标准是正确的
     * 
     * 
     * @return true if the year is leap, false otherwise
     */
    public boolean isLeapYear() {
        return IsoChronology.INSTANCE.isLeapYear(year);
    }

    /**
     * Checks if the day-of-month is valid for this year-month.
     * <p>
     * This method checks whether this year and month and the input day form
     * a valid date.
     *
     * <p>
     *  检查月份是否对此年份有效
     * <p>
     *  此方法检查此年和月和输入日是否形成有效日期
     * 
     * 
     * @param dayOfMonth  the day-of-month to validate, from 1 to 31, invalid value returns false
     * @return true if the day is valid for this year-month
     */
    public boolean isValidDay(int dayOfMonth) {
        return dayOfMonth >= 1 && dayOfMonth <= lengthOfMonth();
    }

    /**
     * Returns the length of the month, taking account of the year.
     * <p>
     * This returns the length of the month in days.
     * For example, a date in January would return 31.
     *
     * <p>
     * 返回月份的长度,考虑年份
     * <p>
     *  这将返回月份的长度(以天为单位)例如,1月的日期将返回31
     * 
     * 
     * @return the length of the month in days, from 28 to 31
     */
    public int lengthOfMonth() {
        return getMonth().length(isLeapYear());
    }

    /**
     * Returns the length of the year.
     * <p>
     * This returns the length of the year in days, either 365 or 366.
     *
     * <p>
     *  返回年的长度
     * <p>
     *  这将返回年的长度,以天为单位,365或366
     * 
     * 
     * @return 366 if the year is leap, 365 otherwise
     */
    public int lengthOfYear() {
        return (isLeapYear() ? 366 : 365);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns an adjusted copy of this year-month.
     * <p>
     * This returns a {@code YearMonth}, based on this one, with the year-month adjusted.
     * The adjustment takes place using the specified adjuster strategy object.
     * Read the documentation of the adjuster to understand what adjustment will be made.
     * <p>
     * A simple adjuster might simply set the one of the fields, such as the year field.
     * A more complex adjuster might set the year-month to the next month that
     * Halley's comet will pass the Earth.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalAdjuster#adjustInto(Temporal)} method on the
     * specified adjuster passing {@code this} as the argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此年份的调整副本
     * <p>
     *  这将返回一个{@code YearMonth},基于这一年,调整年月调整使用指定的调整器策略对象进行调整阅读调整器的文档以了解将进行什么调整
     * <p>
     *  简单的调整器可以简单地设置一个字段,例如年字段更复杂的调节器可以将年 - 月设置为下一月Halley的彗星将通过地球
     * <p>
     * 此方法的结果是通过调用指定的调整器传递{@code this}作为参数的{@link TemporalAdjuster#adjustInto(Temporal)}方法获得的
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param adjuster the adjuster to use, not null
     * @return a {@code YearMonth} based on {@code this} with the adjustment made, not null
     * @throws DateTimeException if the adjustment cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public YearMonth with(TemporalAdjuster adjuster) {
        return (YearMonth) adjuster.adjustInto(this);
    }

    /**
     * Returns a copy of this year-month with the specified field set to a new value.
     * <p>
     * This returns a {@code YearMonth}, based on this one, with the value
     * for the specified field changed.
     * This can be used to change any supported field, such as the year or month.
     * If it is not possible to set the value, because the field is not supported or for
     * some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the adjustment is implemented here.
     * The supported fields behave as follows:
     * <ul>
     * <li>{@code MONTH_OF_YEAR} -
     *  Returns a {@code YearMonth} with the specified month-of-year.
     *  The year will be unchanged.
     * <li>{@code PROLEPTIC_MONTH} -
     *  Returns a {@code YearMonth} with the specified proleptic-month.
     *  This completely replaces the year and month of this object.
     * <li>{@code YEAR_OF_ERA} -
     *  Returns a {@code YearMonth} with the specified year-of-era
     *  The month and era will be unchanged.
     * <li>{@code YEAR} -
     *  Returns a {@code YearMonth} with the specified year.
     *  The month will be unchanged.
     * <li>{@code ERA} -
     *  Returns a {@code YearMonth} with the specified era.
     *  The month and year-of-era will be unchanged.
     * </ul>
     * <p>
     * In all cases, if the new value is outside the valid range of values for the field
     * then a {@code DateTimeException} will be thrown.
     * <p>
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.adjustInto(Temporal, long)}
     * passing {@code this} as the argument. In this case, the field determines
     * whether and how to adjust the instant.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此年份的副本,指定的字段设置为新值
     * <p>
     *  这会返回一个{@code YearMonth},基于这个值,指定字段的值已更改。这可以用于更改任何支持的字段,例如年或月如果不可能设置值,因为字段不受支持或由于某种其他原因,抛出异常
     * <p>
     *  如果字段是{@link ChronoField},则在此执行调整。支持的字段的行为如下：
     * <ul>
     * <li> {@ code MONTH_OF_YEAR}  - 返回带有指定年份的{@code YearMonth}年份不会改变<li> {@ code PROLEPTIC_MONTH}  - 返回一个{@code YearMonth}
     * 月</li> {@ code YEAR_OF_ERA}  - 返回具有指定年份的{@code YearMonth}月份和时代将保持不变<li> {@ code YEAR} - 返回具有指定年份的{@code YearMonth}
     * 该月份将保持不变<li> {@ code ERA}  - 返回指定时期的{@code YearMonth}月份和年份将保持不变。
     * </ul>
     * <p>
     *  在所有情况下,如果新值超出字段的有效值范围,那么将抛出{@code DateTimeException}
     * <p>
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalFieldadjustInto(Temporal,long)}传递{@code this}作为参数来获得此
     * 方法的结果。
     * 在这种情况下,字段确定是否如何调整瞬间。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param field  the field to set in the result, not null
     * @param newValue  the new value of the field in the result
     * @return a {@code YearMonth} based on {@code this} with the specified field set, not null
     * @throws DateTimeException if the field cannot be set
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public YearMonth with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            f.checkValidValue(newValue);
            switch (f) {
                case MONTH_OF_YEAR: return withMonth((int) newValue);
                case PROLEPTIC_MONTH: return plusMonths(newValue - getProlepticMonth());
                case YEAR_OF_ERA: return withYear((int) (year < 1 ? 1 - newValue : newValue));
                case YEAR: return withYear((int) newValue);
                case ERA: return (getLong(ERA) == newValue ? this : withYear(1 - year));
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.adjustInto(this, newValue);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code YearMonth} with the year altered.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code YearMonth}的副本,并更改​​年份
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param year  the year to set in the returned year-month, from MIN_YEAR to MAX_YEAR
     * @return a {@code YearMonth} based on this year-month with the requested year, not null
     * @throws DateTimeException if the year value is invalid
     */
    public YearMonth withYear(int year) {
        YEAR.checkValidValue(year);
        return with(year, month);
    }

    /**
     * Returns a copy of this {@code YearMonth} with the month-of-year altered.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code YearMonth}的副本,其中的月份已更改
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param month  the month-of-year to set in the returned year-month, from 1 (January) to 12 (December)
     * @return a {@code YearMonth} based on this year-month with the requested month, not null
     * @throws DateTimeException if the month-of-year value is invalid
     */
    public YearMonth withMonth(int month) {
        MONTH_OF_YEAR.checkValidValue(month);
        return with(year, month);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this year-month with the specified amount added.
     * <p>
     * This returns a {@code YearMonth}, based on this one, with the specified amount added.
     * The amount is typically {@link Period} but may be any other type implementing
     * the {@link TemporalAmount} interface.
     * <p>
     * The calculation is delegated to the amount object by calling
     * {@link TemporalAmount#addTo(Temporal)}. The amount implementation is free
     * to implement the addition in any way it wishes, however it typically
     * calls back to {@link #plus(long, TemporalUnit)}. Consult the documentation
     * of the amount implementation to determine if it can be successfully added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回具有指定添加量的本年月份的副本
     * <p>
     * 这会返回一个{@code YearMonth},基于这个,添加了指定的金额。金额通常为{@link Period},但可以是实现{@link TemporalAmount}接口的任何其他类型
     * <p>
     *  计算通过调用{@link TemporalAmount#addTo(Temporal)}来委托给金额对象。
     * 金额实现可以以任何希望的方式实现添加,但它通常回调{@link #plus(long,TemporalUnit )}请参阅金额实施的文档,以确定是否可以成功添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param amountToAdd  the amount to add, not null
     * @return a {@code YearMonth} based on this year-month with the addition made, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public YearMonth plus(TemporalAmount amountToAdd) {
        return (YearMonth) amountToAdd.addTo(this);
    }

    /**
     * Returns a copy of this year-month with the specified amount added.
     * <p>
     * This returns a {@code YearMonth}, based on this one, with the amount
     * in terms of the unit added. If it is not possible to add the amount, because the
     * unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoUnit} then the addition is implemented here.
     * The supported fields behave as follows:
     * <ul>
     * <li>{@code MONTHS} -
     *  Returns a {@code YearMonth} with the specified number of months added.
     *  This is equivalent to {@link #plusMonths(long)}.
     * <li>{@code YEARS} -
     *  Returns a {@code YearMonth} with the specified number of years added.
     *  This is equivalent to {@link #plusYears(long)}.
     * <li>{@code DECADES} -
     *  Returns a {@code YearMonth} with the specified number of decades added.
     *  This is equivalent to calling {@link #plusYears(long)} with the amount
     *  multiplied by 10.
     * <li>{@code CENTURIES} -
     *  Returns a {@code YearMonth} with the specified number of centuries added.
     *  This is equivalent to calling {@link #plusYears(long)} with the amount
     *  multiplied by 100.
     * <li>{@code MILLENNIA} -
     *  Returns a {@code YearMonth} with the specified number of millennia added.
     *  This is equivalent to calling {@link #plusYears(long)} with the amount
     *  multiplied by 1,000.
     * <li>{@code ERAS} -
     *  Returns a {@code YearMonth} with the specified number of eras added.
     *  Only two eras are supported so the amount must be one, zero or minus one.
     *  If the amount is non-zero then the year is changed such that the year-of-era
     *  is unchanged.
     * </ul>
     * <p>
     * All other {@code ChronoUnit} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.addTo(Temporal, long)}
     * passing {@code this} as the argument. In this case, the unit determines
     * whether and how to perform the addition.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回具有指定添加量的本年月份的副本
     * <p>
     * 这将返回一个{@code YearMonth},根据这一个,与添加的单位的金额如果不可能添加金额,因为该单位不被支持或由于一些其他原因,抛出异常
     * <p>
     *  如果字段是{@link ChronoUnit},则在此处实现添加。支持的字段的行为如下：
     * <ul>
     * <li> {@ code MONTHS}  - 返回添加了指定月数的{@code YearMonth}这等效于{@link #plusMonths(long)} <li> {@ code YEARS} 
     *  - 返回{@code YearMonth}添加指定的年数这等同于{@link #plusYears(long)} <li> {@ code DECADES}  - 返回添加了指定的十进制数的{@code YearMonth}
     * 这相当于调用{@link #plusYears(long)},数量乘以10 <li> {@ code CENTURIES}  - 返回具有指定的添加世纪数的{@code YearMonth}这等效于调用
     * {@link #plusYears )},数量乘以100 <li> {@ code MILLENNIA}  - 返回一个{@code YearMonth},加上指定的千年数这相当于调用{@link #plusYears(long)}
     * 乘以1000的乘数<li> {@ code ERAS}  - 返回一个带有指定数目的时间的{@code YearMonth}只支持两个时间金额必须为一,零或减一如果金额不为零,则年份会更改,以使年份不变
     * 。
     * </ul>
     * <p>
     * 所有其他{@code ChronoUnit}实例将抛出{@code UnsupportedTemporalTypeException}
     * <p>
     *  如果字段不是{@code ChronoUnit},那么通过调用{@code TemporalUnitaddTo(Temporal,long)}传递{@code this}作为参数来获得此方法的结果。
     * 在这种情况下,单元确定是否如何执行添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param amountToAdd  the amount of the unit to add to the result, may be negative
     * @param unit  the unit of the amount to add, not null
     * @return a {@code YearMonth} based on this year-month with the specified amount added, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public YearMonth plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            switch ((ChronoUnit) unit) {
                case MONTHS: return plusMonths(amountToAdd);
                case YEARS: return plusYears(amountToAdd);
                case DECADES: return plusYears(Math.multiplyExact(amountToAdd, 10));
                case CENTURIES: return plusYears(Math.multiplyExact(amountToAdd, 100));
                case MILLENNIA: return plusYears(Math.multiplyExact(amountToAdd, 1000));
                case ERAS: return with(ERA, Math.addExact(getLong(ERA), amountToAdd));
            }
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return unit.addTo(this, amountToAdd);
    }

    /**
     * Returns a copy of this year-month with the specified period in years added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此年份的副本,指定的期间为添加年份
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param yearsToAdd  the years to add, may be negative
     * @return a {@code YearMonth} based on this year-month with the years added, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    public YearMonth plusYears(long yearsToAdd) {
        if (yearsToAdd == 0) {
            return this;
        }
        int newYear = YEAR.checkValidIntValue(year + yearsToAdd);  // safe overflow
        return with(newYear, month);
    }

    /**
     * Returns a copy of this year-month with the specified period in months added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此年份的副本,指定的期间以添加的月份为单位
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param monthsToAdd  the months to add, may be negative
     * @return a {@code YearMonth} based on this year-month with the months added, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    public YearMonth plusMonths(long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }
        long monthCount = year * 12L + (month - 1);
        long calcMonths = monthCount + monthsToAdd;  // safe overflow
        int newYear = YEAR.checkValidIntValue(Math.floorDiv(calcMonths, 12));
        int newMonth = (int)Math.floorMod(calcMonths, 12) + 1;
        return with(newYear, newMonth);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this year-month with the specified amount subtracted.
     * <p>
     * This returns a {@code YearMonth}, based on this one, with the specified amount subtracted.
     * The amount is typically {@link Period} but may be any other type implementing
     * the {@link TemporalAmount} interface.
     * <p>
     * The calculation is delegated to the amount object by calling
     * {@link TemporalAmount#subtractFrom(Temporal)}. The amount implementation is free
     * to implement the subtraction in any way it wishes, however it typically
     * calls back to {@link #minus(long, TemporalUnit)}. Consult the documentation
     * of the amount implementation to determine if it can be successfully subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此年份的指定金额减去的副本
     * <p>
     * 这会返回一个{@code YearMonth},基于这个值,减去指定的金额。金额通常为{@link Period},但可以是实现{@link TemporalAmount}接口的任何其他类型
     * <p>
     *  通过调用{@link TemporalAmount#subtractFrom(Temporal)}来将计算委托给金额对象。
     * 金额实现可以以任何希望的方式实现减法,但是它通常回调{@link #minus(long,TemporalUnit )}请参阅金额实施的文档,以确定是否可以成功扣除。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param amountToSubtract  the amount to subtract, not null
     * @return a {@code YearMonth} based on this year-month with the subtraction made, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public YearMonth minus(TemporalAmount amountToSubtract) {
        return (YearMonth) amountToSubtract.subtractFrom(this);
    }

    /**
     * Returns a copy of this year-month with the specified amount subtracted.
     * <p>
     * This returns a {@code YearMonth}, based on this one, with the amount
     * in terms of the unit subtracted. If it is not possible to subtract the amount,
     * because the unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * This method is equivalent to {@link #plus(long, TemporalUnit)} with the amount negated.
     * See that method for a full description of how addition, and thus subtraction, works.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此年份的指定金额减去的副本
     * <p>
     * 这将返回一个{@code YearMonth},根据这一个,与减去单位的金额如果不可能减去金额,因为单位不被支持或由于其他原因,抛出异常
     * <p>
     *  此方法等效于{@link #plus(long,TemporalUnit)},其值为negated请参阅该方法,了解有关如何加法和减法的完整描述
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param amountToSubtract  the amount of the unit to subtract from the result, may be negative
     * @param unit  the unit of the amount to subtract, not null
     * @return a {@code YearMonth} based on this year-month with the specified amount subtracted, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public YearMonth minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    /**
     * Returns a copy of this year-month with the specified period in years subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此年份的副本,指定的期间(扣除年份)
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param yearsToSubtract  the years to subtract, may be negative
     * @return a {@code YearMonth} based on this year-month with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    public YearMonth minusYears(long yearsToSubtract) {
        return (yearsToSubtract == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
    }

    /**
     * Returns a copy of this year-month with the specified period in months subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此年份的副本,指定的期间减去月份
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param monthsToSubtract  the months to subtract, may be negative
     * @return a {@code YearMonth} based on this year-month with the months subtracted, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    public YearMonth minusMonths(long monthsToSubtract) {
        return (monthsToSubtract == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-monthsToSubtract));
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this year-month using the specified query.
     * <p>
     * This queries this year-month using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     * 本年度使用指定的查询查询
     * <p>
     *  这使用指定的查询策略对象查询今年一月{@code TemporalQuery}对象定义用于获取结果的逻辑读取查询的文档以了解此方法的结果将是什么
     * <p>
     *  此方法的结果是通过在指定的查询上调用{@link TemporalQuery#queryFrom(TemporalAccessor)}方法{@code this}作为参数
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
        } else if (query == TemporalQueries.precision()) {
            return (R) MONTHS;
        }
        return Temporal.super.query(query);
    }

    /**
     * Adjusts the specified temporal object to have this year-month.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the year and month changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * passing {@link ChronoField#PROLEPTIC_MONTH} as the field.
     * If the specified temporal object does not use the ISO calendar system then
     * a {@code DateTimeException} is thrown.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisYearMonth.adjustInto(temporal);
     *   temporal = temporal.with(thisYearMonth);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为具有今年
     * <p>
     *  这返回与具有年和月的输入相同的observable类型的时间对象更改为与此相同
     * <p>
     * 该调整等同于使用{@link Temporal#with(TemporalField,long)}传递{@link ChronoField#PROLEPTIC_MONTH}作为字段如果指定的临时对象不使
     * 用ISO日历系统,则会抛出{@code DateTimeException}。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等价的,但第二种方法是推荐temporal = thisYearMonthadjustInto(temporal); temporal = temporalwith(thisYearMon
     * th);。
     * </pre>
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
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
        return temporal.with(PROLEPTIC_MONTH, getProlepticMonth());
    }

    /**
     * Calculates the amount of time until another year-month in terms of the specified unit.
     * <p>
     * This calculates the amount of time between two {@code YearMonth}
     * objects in terms of a single {@code TemporalUnit}.
     * The start and end points are {@code this} and the specified year-month.
     * The result will be negative if the end is before the start.
     * The {@code Temporal} passed to this method is converted to a
     * {@code YearMonth} using {@link #from(TemporalAccessor)}.
     * For example, the period in years between two year-months can be calculated
     * using {@code startYearMonth.until(endYearMonth, YEARS)}.
     * <p>
     * The calculation returns a whole number, representing the number of
     * complete units between the two year-months.
     * For example, the period in decades between 2012-06 and 2032-05
     * will only be one decade as it is one month short of two decades.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method.
     * The second is to use {@link TemporalUnit#between(Temporal, Temporal)}:
     * <pre>
     *   // these two lines are equivalent
     *   amount = start.until(end, MONTHS);
     *   amount = MONTHS.between(start, end);
     * </pre>
     * The choice should be made based on which makes the code more readable.
     * <p>
     * The calculation is implemented in this method for {@link ChronoUnit}.
     * The units {@code MONTHS}, {@code YEARS}, {@code DECADES},
     * {@code CENTURIES}, {@code MILLENNIA} and {@code ERAS} are supported.
     * Other {@code ChronoUnit} values will throw an exception.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.between(Temporal, Temporal)}
     * passing {@code this} as the first argument and the converted input temporal
     * as the second argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定单位计算直到另一个年 - 月的时间量
     * <p>
     * 这将以单个{@code TemporalUnit}的形式计算两个{@code YearMonth}对象之间的时间量开始和结束点为{@code this}和指定的年 - 月如果结束为开始之前传递给此方法的
     * {@code Temporal}将使用{@link #from(TemporalAccessor)}转换为{@code YearMonth}。
     * 例如,两个月份之间的年份可以使用{@ code startYearMonthuntil(endYearMonth,YEARS)}。
     * <p>
     *  计算返回一个整数,表示两个年份之间的完整单位数例如,2012-06和2032-05之间的几十年的时期将只有十年,因为它比二十年短一个月
     * <p>
     * 有两种使用这种方法的等效方法第一种是调用这种方法第二种是使用{@link TemporalUnit#between(Temporal,Temporal)}：
     * <pre>
     *  //这两行是等价的amount = startuntil(end,MONTHS); amount = MONTHS between(start,end);
     * </pre>
     *  应该基于哪个使得代码更可读的选择
     * <p>
     *  {@code MONTHS},{@code YEARS},{@code DECADES},{@code CENTURIES},{@code MILLENNIA}和{@code ERAS}这些单位都使用
     * {@link ChronoUnit}是受支持的其他{@code ChronoUnit}值将抛出异常。
     * <p>
     * 如果单元不是{@code ChronoUnit},则该方法的结果通过调用{@code TemporalUnit between(Temporal,Temporal)}传递{@code this}作为第一
     * 个参数和转换的输入时间作为第二个参数论据。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param endExclusive  the end date, exclusive, which is converted to a {@code YearMonth}, not null
     * @param unit  the unit to measure the amount in, not null
     * @return the amount of time between this year-month and the end year-month
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to a {@code YearMonth}
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        YearMonth end = YearMonth.from(endExclusive);
        if (unit instanceof ChronoUnit) {
            long monthsUntil = end.getProlepticMonth() - getProlepticMonth();  // no overflow
            switch ((ChronoUnit) unit) {
                case MONTHS: return monthsUntil;
                case YEARS: return monthsUntil / 12;
                case DECADES: return monthsUntil / 120;
                case CENTURIES: return monthsUntil / 1200;
                case MILLENNIA: return monthsUntil / 12000;
                case ERAS: return end.getLong(ERA) - getLong(ERA);
            }
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return unit.between(this, end);
    }

    /**
     * Formats this year-month using the specified formatter.
     * <p>
     * This year-month will be passed to the formatter to produce a string.
     *
     * <p>
     *  使用指定的格式化程序在今年月份格式化
     * <p>
     *  这个年月将被传递给格式器以产生一个字符串
     * 
     * 
     * @param formatter  the formatter to use, not null
     * @return the formatted year-month string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    public String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Combines this year-month with a day-of-month to create a {@code LocalDate}.
     * <p>
     * This returns a {@code LocalDate} formed from this year-month and the specified day-of-month.
     * <p>
     * The day-of-month value must be valid for the year-month.
     * <p>
     * This method can be used as part of a chain to produce a date:
     * <pre>
     *  LocalDate date = year.atMonth(month).atDay(day);
     * </pre>
     *
     * <p>
     *  将今年一月与一个月的日期组合,创建一个{@code LocalDate}
     * <p>
     *  这会返回由此年份和指定的日期组成的{@code LocalDate}
     * <p>
     *  日期值必须对年月有效
     * <p>
     *  此方法可用作链的一部分以生成日期：
     * <pre>
     *  LocalDate date = yearatMonth(month)atDay(day);
     * </pre>
     * 
     * 
     * @param dayOfMonth  the day-of-month to use, from 1 to 31
     * @return the date formed from this year-month and the specified day, not null
     * @throws DateTimeException if the day is invalid for the year-month
     * @see #isValidDay(int)
     */
    public LocalDate atDay(int dayOfMonth) {
        return LocalDate.of(year, month, dayOfMonth);
    }

    /**
     * Returns a {@code LocalDate} at the end of the month.
     * <p>
     * This returns a {@code LocalDate} based on this year-month.
     * The day-of-month is set to the last valid day of the month, taking
     * into account leap years.
     * <p>
     * This method can be used as part of a chain to produce a date:
     * <pre>
     *  LocalDate date = year.atMonth(month).atEndOfMonth();
     * </pre>
     *
     * <p>
     * 在月底返回{@code LocalDate}
     * <p>
     *  这会根据今年的月份返回一个{@code LocalDate}。将月份设置为月份的最后一个有效日期,同时考虑闰年
     * <p>
     *  此方法可用作链的一部分以生成日期：
     * <pre>
     *  LocalDate date = yearatMonth(month)atEndOfMonth();
     * </pre>
     * 
     * 
     * @return the last valid date of this year-month, not null
     */
    public LocalDate atEndOfMonth() {
        return LocalDate.of(year, month, lengthOfMonth());
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this year-month to another year-month.
     * <p>
     * The comparison is based first on the value of the year, then on the value of the month.
     * It is "consistent with equals", as defined by {@link Comparable}.
     *
     * <p>
     *  将今年与另一个年月比较
     * <p>
     *  比较首先基于年的值,然后基于月的值它是"与等号一致",由{@link Comparable}定义,
     * 
     * 
     * @param other  the other year-month to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    public int compareTo(YearMonth other) {
        int cmp = (year - other.year);
        if (cmp == 0) {
            cmp = (month - other.month);
        }
        return cmp;
    }

    /**
     * Is this year-month after the specified year-month.
     *
     * <p>
     *  是指定年 - 月后的年 - 月
     * 
     * 
     * @param other  the other year-month to compare to, not null
     * @return true if this is after the specified year-month
     */
    public boolean isAfter(YearMonth other) {
        return compareTo(other) > 0;
    }

    /**
     * Is this year-month before the specified year-month.
     *
     * <p>
     *  是指定年 - 月之前的年月
     * 
     * 
     * @param other  the other year-month to compare to, not null
     * @return true if this point is before the specified year-month
     */
    public boolean isBefore(YearMonth other) {
        return compareTo(other) < 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this year-month is equal to another year-month.
     * <p>
     * The comparison is based on the time-line position of the year-months.
     *
     * <p>
     *  检查此年份是否等于另一个年份
     * <p>
     *  比较基于年 - 月的时间线位置
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other year-month
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof YearMonth) {
            YearMonth other = (YearMonth) obj;
            return year == other.year && month == other.month;
        }
        return false;
    }

    /**
     * A hash code for this year-month.
     *
     * <p>
     *  本年度的哈希码
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return year ^ (month << 27);
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this year-month as a {@code String}, such as {@code 2007-12}.
     * <p>
     * The output will be in the format {@code uuuu-MM}:
     *
     * <p>
     * 将今年一月作为{@code String}输出,例如{@code 2007-12}
     * <p>
     *  输出将采用格式{@code uuuu-MM}：
     * 
     * 
     * @return a string representation of this year-month, not null
     */
    @Override
    public String toString() {
        int absYear = Math.abs(year);
        StringBuilder buf = new StringBuilder(9);
        if (absYear < 1000) {
            if (year < 0) {
                buf.append(year - 10000).deleteCharAt(1);
            } else {
                buf.append(year + 10000).deleteCharAt(0);
            }
        } else {
            buf.append(year);
        }
        return buf.append(month < 10 ? "-0" : "-")
            .append(month)
            .toString();
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     *  使用<a href=\"//serialized-formhtml#javatimeSer\">专用序列化表单</a>写入对象
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(12);  // identifies a YearMonth
     *  out.writeInt(year);
     *  out.writeByte(month);
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.YEAR_MONTH_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     *  防御恶意流
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeInt(year);
        out.writeByte(month);
    }

    static YearMonth readExternal(DataInput in) throws IOException {
        int year = in.readInt();
        byte month = in.readByte();
        return YearMonth.of(year, month);
    }

}
