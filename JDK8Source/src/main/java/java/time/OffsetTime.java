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

import static java.time.LocalTime.NANOS_PER_HOUR;
import static java.time.LocalTime.NANOS_PER_MINUTE;
import static java.time.LocalTime.NANOS_PER_SECOND;
import static java.time.LocalTime.SECONDS_PER_DAY;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;
import static java.time.temporal.ChronoUnit.NANOS;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import java.time.zone.ZoneRules;
import java.util.Objects;

/**
 * A time with an offset from UTC/Greenwich in the ISO-8601 calendar system,
 * such as {@code 10:15:30+01:00}.
 * <p>
 * {@code OffsetTime} is an immutable date-time object that represents a time, often
 * viewed as hour-minute-second-offset.
 * This class stores all time fields, to a precision of nanoseconds,
 * as well as a zone offset.
 * For example, the value "13:45.30.123456789+02:00" can be stored
 * in an {@code OffsetTime}.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code OffsetTime} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  在ISO-8601日历系统中偏离UTC / Greenwich的时间,例如{@code 10：15：30 + 01：00}。
 * <p>
 *  {@code OffsetTime}是一个不可变的日期时间对象,表示时间,通常被视为小时分钟 - 秒偏移。此类存储所有时间字段,精度为纳秒,以及区域偏移。
 * 例如,值"13：45.30.123456789 + 02：00"可以存储在{@code OffsetTime}中。
 * 
 * <p>
 * 这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code OffsetTime}的实例上使用身份敏感
 * 操作(包括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class OffsetTime
        implements Temporal, TemporalAdjuster, Comparable<OffsetTime>, Serializable {

    /**
     * The minimum supported {@code OffsetTime}, '00:00:00+18:00'.
     * This is the time of midnight at the start of the day in the maximum offset
     * (larger offsets are earlier on the time-line).
     * This combines {@link LocalTime#MIN} and {@link ZoneOffset#MAX}.
     * This could be used by an application as a "far past" date.
     * <p>
     *  最低支持{@code OffsetTime},'00：00：00 + 18：00'。这是最大偏移量在一天开始时的午夜时间(较大的偏移量在时间线上较早)。
     * 结合{@link LocalTime#MIN}和{@link ZoneOffset#MAX}。这可以被应用程序用作"过去"日期。
     * 
     */
    public static final OffsetTime MIN = LocalTime.MIN.atOffset(ZoneOffset.MAX);
    /**
     * The maximum supported {@code OffsetTime}, '23:59:59.999999999-18:00'.
     * This is the time just before midnight at the end of the day in the minimum offset
     * (larger negative offsets are later on the time-line).
     * This combines {@link LocalTime#MAX} and {@link ZoneOffset#MIN}.
     * This could be used by an application as a "far future" date.
     * <p>
     *  最大支持的{@code OffsetTime},'23：59：59.999999999-18：00'。这是恰好在午夜之前在最小偏移量的结尾的时间(较大的负偏移在时间线上较晚)。
     * 这结合了{@link LocalTime#MAX}和{@link ZoneOffset#MIN}。这可以被应用程序用作"远期"日期。
     * 
     */
    public static final OffsetTime MAX = LocalTime.MAX.atOffset(ZoneOffset.MIN);

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 7264499704384272492L;

    /**
     * The local date-time.
     * <p>
     *  本地日期时间。
     * 
     */
    private final LocalTime time;
    /**
     * The offset from UTC/Greenwich.
     * <p>
     *  偏离UTC /格林威治。
     * 
     */
    private final ZoneOffset offset;

    //-----------------------------------------------------------------------
    /**
     * Obtains the current time from the system clock in the default time-zone.
     * <p>
     * This will query the {@link java.time.Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current time.
     * The offset will be calculated from the time-zone in the clock.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区中从系统时钟获取当前时间。
     * <p>
     *  这将在默认时区中查询{@link java.time.Clock#systemDefaultZone()系统时钟}以获取当前时间。偏移将根据时钟中的时区计算。
     * <p>
     * 使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @return the current time using the system clock, not null
     */
    public static OffsetTime now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current time from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(java.time.ZoneId) system clock} to obtain the current time.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * The offset will be calculated from the specified time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区中从系统时钟获取当前时间。
     * <p>
     *  这将查询{@link Clock#system(java.time.ZoneId)系统时钟}以获取当前时间。指定时区避免了对默认时区的依赖。将根据指定的时区计算偏移量。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current time using the system clock, not null
     */
    public static OffsetTime now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current time from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current time.
     * The offset will be calculated from the time-zone in the clock.
     * <p>
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前时间。
     * <p>
     *  这将查询指定的时钟以获取当前时间。偏移将根据时钟中的时区计算。
     * <p>
     *  使用此方法允许使用替代时钟进行测试。可以使用{@link时钟依赖注入}来引入替代时钟。
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current time, not null
     */
    public static OffsetTime now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        final Instant now = clock.instant();  // called once
        return ofInstant(now, clock.getZone().getRules().getOffset(now));
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code OffsetTime} from a local time and an offset.
     *
     * <p>
     *  从本地时间和偏移获取{@code OffsetTime}的实例。
     * 
     * 
     * @param time  the local time, not null
     * @param offset  the zone offset, not null
     * @return the offset time, not null
     */
    public static OffsetTime of(LocalTime time, ZoneOffset offset) {
        return new OffsetTime(time, offset);
    }

    /**
     * Obtains an instance of {@code OffsetTime} from an hour, minute, second and nanosecond.
     * <p>
     * This creates an offset time with the four specified fields.
     * <p>
     * This method exists primarily for writing test cases.
     * Non test-code will typically use other methods to create an offset time.
     * {@code LocalTime} has two additional convenience variants of the
     * equivalent factory method taking fewer arguments.
     * They are not provided here to reduce the footprint of the API.
     *
     * <p>
     *  从小时,分钟,秒和纳秒获取{@code OffsetTime}的实例。
     * <p>
     *  这将使用四个指定字段创建一个偏移时间。
     * <p>
     *  这种方法主要用于写测试用例。非测试代码通常将使用其他方法来创建偏移时间。 {@code LocalTime}有两个额外的方便变体的等效工厂方法,使用较少的参数。这里不提供它们来减少API的占用空间。
     * 
     * 
     * @param hour  the hour-of-day to represent, from 0 to 23
     * @param minute  the minute-of-hour to represent, from 0 to 59
     * @param second  the second-of-minute to represent, from 0 to 59
     * @param nanoOfSecond  the nano-of-second to represent, from 0 to 999,999,999
     * @param offset  the zone offset, not null
     * @return the offset time, not null
     * @throws DateTimeException if the value of any field is out of range
     */
    public static OffsetTime of(int hour, int minute, int second, int nanoOfSecond, ZoneOffset offset) {
        return new OffsetTime(LocalTime.of(hour, minute, second, nanoOfSecond), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code OffsetTime} from an {@code Instant} and zone ID.
     * <p>
     * This creates an offset time with the same instant as that specified.
     * Finding the offset from UTC/Greenwich is simple as there is only one valid
     * offset for each instant.
     * <p>
     * The date component of the instant is dropped during the conversion.
     * This means that the conversion can never fail due to the instant being
     * out of the valid range of dates.
     *
     * <p>
     *  从{@code Instant}和区域ID获取{@code OffsetTime}的实例。
     * <p>
     * 这将创建一个与指定的时刻相同的偏移时间。从UTC / Greenwich找到偏移是很简单的,因为每个时刻只有一个有效的偏移。
     * <p>
     *  在转换期间删除即时的日期分量。这意味着转换永远不会失败,因为即时在有效的日期范围之外。
     * 
     * 
     * @param instant  the instant to create the time from, not null
     * @param zone  the time-zone, which may be an offset, not null
     * @return the offset time, not null
     */
    public static OffsetTime ofInstant(Instant instant, ZoneId zone) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zone, "zone");
        ZoneRules rules = zone.getRules();
        ZoneOffset offset = rules.getOffset(instant);
        long localSecond = instant.getEpochSecond() + offset.getTotalSeconds();  // overflow caught later
        int secsOfDay = (int) Math.floorMod(localSecond, SECONDS_PER_DAY);
        LocalTime time = LocalTime.ofNanoOfDay(secsOfDay * NANOS_PER_SECOND + instant.getNano());
        return new OffsetTime(time, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code OffsetTime} from a temporal object.
     * <p>
     * This obtains an offset time based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code OffsetTime}.
     * <p>
     * The conversion extracts and combines the {@code ZoneOffset} and the
     * {@code LocalTime} from the temporal object.
     * Implementations are permitted to perform optimizations such as accessing
     * those fields that are equivalent to the relevant objects.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code OffsetTime::from}.
     *
     * <p>
     *  从临时对象获取{@code OffsetTime}的实例。
     * <p>
     *  这基于指定的时间获得偏移时间。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code OffsetTime}的实例。
     * <p>
     *  转换从时间对象提取和组合{@code ZoneOffset}和{@code LocalTime}。实现允许执行优化,例如访问等价于相关对象的那些字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code OffsetTime :: from}在查询中使用。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the offset time, not null
     * @throws DateTimeException if unable to convert to an {@code OffsetTime}
     */
    public static OffsetTime from(TemporalAccessor temporal) {
        if (temporal instanceof OffsetTime) {
            return (OffsetTime) temporal;
        }
        try {
            LocalTime time = LocalTime.from(temporal);
            ZoneOffset offset = ZoneOffset.from(temporal);
            return new OffsetTime(time, offset);
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain OffsetTime from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code OffsetTime} from a text string such as {@code 10:15:30+01:00}.
     * <p>
     * The string must represent a valid time and is parsed using
     * {@link java.time.format.DateTimeFormatter#ISO_OFFSET_TIME}.
     *
     * <p>
     *  从文本字符串(例如{@code 10：15：30 + 01：00})获取{@code OffsetTime}的实例。
     * <p>
     *  该字符串必须表示有效时间,并使用{@link java.time.format.DateTimeFormatter#ISO_OFFSET_TIME}进行解析。
     * 
     * 
     * @param text  the text to parse such as "10:15:30+01:00", not null
     * @return the parsed local time, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static OffsetTime parse(CharSequence text) {
        return parse(text, DateTimeFormatter.ISO_OFFSET_TIME);
    }

    /**
     * Obtains an instance of {@code OffsetTime} from a text string using a specific formatter.
     * <p>
     * The text is parsed using the formatter, returning a time.
     *
     * <p>
     *  使用特定格式化程序从文本字符串获取{@code OffsetTime}的实例。
     * <p>
     *  使用格式化程序解析文本,返回时间。
     * 
     * 
     * @param text  the text to parse, not null
     * @param formatter  the formatter to use, not null
     * @return the parsed offset time, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static OffsetTime parse(CharSequence text, DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, OffsetTime::from);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * <p>
     *  构造函数。
     * 
     * 
     * @param time  the local time, not null
     * @param offset  the zone offset, not null
     */
    private OffsetTime(LocalTime time, ZoneOffset offset) {
        this.time = Objects.requireNonNull(time, "time");
        this.offset = Objects.requireNonNull(offset, "offset");
    }

    /**
     * Returns a new time based on this one, returning {@code this} where possible.
     *
     * <p>
     *  返回基于此时间的新时间,尽可能返回{@code this}。
     * 
     * 
     * @param time  the time to create with, not null
     * @param offset  the zone offset to create with, not null
     */
    private OffsetTime with(LocalTime time, ZoneOffset offset) {
        if (this.time == time && this.offset.equals(offset)) {
            return this;
        }
        return new OffsetTime(time, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this time can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range},
     * {@link #get(TemporalField) get} and {@link #with(TemporalField, long)}
     * methods will throw an exception.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The supported fields are:
     * <ul>
     * <li>{@code NANO_OF_SECOND}
     * <li>{@code NANO_OF_DAY}
     * <li>{@code MICRO_OF_SECOND}
     * <li>{@code MICRO_OF_DAY}
     * <li>{@code MILLI_OF_SECOND}
     * <li>{@code MILLI_OF_DAY}
     * <li>{@code SECOND_OF_MINUTE}
     * <li>{@code SECOND_OF_DAY}
     * <li>{@code MINUTE_OF_HOUR}
     * <li>{@code MINUTE_OF_DAY}
     * <li>{@code HOUR_OF_AMPM}
     * <li>{@code CLOCK_HOUR_OF_AMPM}
     * <li>{@code HOUR_OF_DAY}
     * <li>{@code CLOCK_HOUR_OF_DAY}
     * <li>{@code AMPM_OF_DAY}
     * <li>{@code OFFSET_SECONDS}
     * </ul>
     * All other {@code ChronoField} instances will return false.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.isSupportedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the field is supported is determined by the field.
     *
     * <p>
     * 检查是否支持指定的字段。
     * <p>
     *  这将检查是否可以查询指定字段的此时间。
     * 如果为false,则调用{@link #range(TemporalField)范围},{@link #get(TemporalField)get}和{@link #with(TemporalField,long)}
     * 方法将抛出异常。
     *  这将检查是否可以查询指定字段的此时间。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。支持的字段包括：
     * <ul>
     *  <li> {@ code NANO_OF_DAY} <li> {@ code MANI_OF_DAY} <li> {@ code MICRO_OF_DAY} <li> {@ code MICRO_OF_DAY}
     *  > {@ code SECOND_OF_MINUTE} <li> {@ code SECOND_OF_DAY} <li> {@ code MINUTE_OF_HOUR} <li> {@ code MINUTE_OF_DAY}
     *  <li> {@ code HOUR_OF_AMPM} <li> {@ code CLOCK_HOUR_OF_AMPM} @code HOUR_OF_DAY} <li> {@ code CLOCK_HOUR_OF_DAY}
     *  <li> {@ code AMPM_OF_DAY} <li> {@ code OFFSET_SECONDS}。
     * </ul>
     *  所有其他{@code ChronoField}实例将返回false。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this time, false if not
     */
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field.isTimeBased() || field == OFFSET_SECONDS;
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
     * <li>{@code NANOS}
     * <li>{@code MICROS}
     * <li>{@code MILLIS}
     * <li>{@code SECONDS}
     * <li>{@code MINUTES}
     * <li>{@code HOURS}
     * <li>{@code HALF_DAYS}
     * </ul>
     * All other {@code ChronoUnit} instances will return false.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.isSupportedBy(Temporal)}
     * passing {@code this} as the argument.
     * Whether the unit is supported is determined by the unit.
     *
     * <p>
     *  检查是否支持指定的单元。
     * <p>
     *  这将检查指定的单位是否可以添加到此日期时间或从此日期时间中减去。
     * 如果为false,则调用{@link #plus(long,TemporalUnit)}和{@link #minus(long,TemporalUnit)minus}方法将抛出异常。
     * <p>
     *  如果单位是{@link ChronoUnit},则在此执行查询。支持的单位有：
     * <ul>
     * <li> {@ code MINUTES} <li> {@ code MICROS} <li> {@ code MILLIS} <li> {@ code SECONDS} <li> {@ code MINUTES}
     *  > {@ code HALF_DAYS}。
     * </ul>
     *  所有其他{@code ChronoUnit}实例将返回false。
     * <p>
     *  如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.isSupportedBy(Temporal)}传递{@code this}作为参数来获得此方法的
     * 结果。
     * 单元是否受支持由单元确定。
     * 
     * 
     * @param unit  the unit to check, null returns false
     * @return true if the unit can be added/subtracted, false if not
     */
    @Override  // override for Javadoc
    public boolean isSupported(TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            return unit.isTimeBased();
        }
        return unit != null && unit.isSupportedBy(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This time is used to enhance the accuracy of the returned range.
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
     *  范围对象表示字段的最小和最大有效值。此时间用于提高返回范围的精度。如果不可能返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)supported fields}会传回适当的范围执行个体。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.rangeRefinedBy(TemporalAccessor)}传递{@code this}
     * 作为参数来获得此方法的结果。
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
        if (field instanceof ChronoField) {
            if (field == OFFSET_SECONDS) {
                return field.range();
            }
            return time.range(field);
        }
        return field.rangeRefinedBy(this);
    }

    /**
     * Gets the value of the specified field from this time as an {@code int}.
     * <p>
     * This queries this time for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this time, except {@code NANO_OF_DAY} and {@code MICRO_OF_DAY}
     * which are too large to fit in an {@code int} and throw a {@code DateTimeException}.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此时获取指定字段的值为{@code int}。
     * <p>
     * 这次查询指定字段的值。返回的值将始终在字段的有效值范围内。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。
     * 除非{@code NANO_OF_DAY}和{@code MICRO_OF_DAY}太大,无法容纳在{@code int}中,{@link #isSupported(TemporalField)支持的字段}
     * 将返回基于此时间的有效值, @code DateTimeException}。
     *  如果字段是{@link ChronoField},则在此执行查询。
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
        return Temporal.super.get(field);
    }

    /**
     * Gets the value of the specified field from this time as a {@code long}.
     * <p>
     * This queries this time for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this time.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此时获取指定字段的值为{@code long}。
     * <p>
     *  这次查询指定字段的值。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)supported fields}将根据此时间返回有效值。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得此
     * 方法的结果。
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
            if (field == OFFSET_SECONDS) {
                return offset.getTotalSeconds();
            }
            return time.getLong(field);
        }
        return field.getFrom(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the zone offset, such as '+01:00'.
     * <p>
     * This is the offset of the local time from UTC/Greenwich.
     *
     * <p>
     *  获取区域偏移量,例如"+01：00"。
     * <p>
     *  这是当地时间偏离UTC /格林威治。
     * 
     * 
     * @return the zone offset, not null
     */
    public ZoneOffset getOffset() {
        return offset;
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the specified offset ensuring
     * that the result has the same local time.
     * <p>
     * This method returns an object with the same {@code LocalTime} and the specified {@code ZoneOffset}.
     * No calculation is needed or performed.
     * For example, if this time represents {@code 10:30+02:00} and the offset specified is
     * {@code +03:00}, then this method will return {@code 10:30+03:00}.
     * <p>
     * To take into account the difference between the offsets, and adjust the time fields,
     * use {@link #withOffsetSameInstant}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回具有指定偏移量的{@code OffsetTime}的副本,以确保结果具有相同的本地时间。
     * <p>
     *  此方法返回具有相同{@code LocalTime}和指定的{@code ZoneOffset}的对象。不需要或不执行计算。
     * 例如,如果这个时间代表{@code 10：30 + 02：00}并且指定的偏移量是{@code +03：00},那么这个方法将返回{@code 10：30 + 03：00}。
     * <p>
     *  要考虑偏移之间的差异,并调整时间字段,请使用{@link #withOffsetSameInstant}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param offset  the zone offset to change to, not null
     * @return an {@code OffsetTime} based on this time with the requested offset, not null
     */
    public OffsetTime withOffsetSameLocal(ZoneOffset offset) {
        return offset != null && offset.equals(this.offset) ? this : new OffsetTime(time, offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the specified offset ensuring
     * that the result is at the same instant on an implied day.
     * <p>
     * This method returns an object with the specified {@code ZoneOffset} and a {@code LocalTime}
     * adjusted by the difference between the two offsets.
     * This will result in the old and new objects representing the same instant an an implied day.
     * This is useful for finding the local time in a different offset.
     * For example, if this time represents {@code 10:30+02:00} and the offset specified is
     * {@code +03:00}, then this method will return {@code 11:30+03:00}.
     * <p>
     * To change the offset without adjusting the local time use {@link #withOffsetSameLocal}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回具有指定偏移量的{@code OffsetTime}的副本,以确保结果在隐含日的同一时刻。
     * <p>
     * 此方法返回具有指定的{@code ZoneOffset}和{@code LocalTime}的对象,由两个偏移之间的差调整。这将导致表示相同时刻的旧对象和新对象是隐含的日子。
     * 这对于在不同偏移中查找本地时间很有用。
     * 例如,如果这个时间代表{@code 10：30 + 02：00}并且指定的偏移量是{@code +03：00},那么这个方法将返回{@code 11：30 + 03：00}。
     * <p>
     *  要更改偏移而不调整本地时间,请使用{@link #withOffsetSameLocal}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param offset  the zone offset to change to, not null
     * @return an {@code OffsetTime} based on this time with the requested offset, not null
     */
    public OffsetTime withOffsetSameInstant(ZoneOffset offset) {
        if (offset.equals(this.offset)) {
            return this;
        }
        int difference = offset.getTotalSeconds() - this.offset.getTotalSeconds();
        LocalTime adjusted = time.plusSeconds(difference);
        return new OffsetTime(adjusted, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the {@code LocalTime} part of this date-time.
     * <p>
     * This returns a {@code LocalTime} with the same hour, minute, second and
     * nanosecond as this date-time.
     *
     * <p>
     *  获取此日期时间的{@code LocalTime}部分。
     * <p>
     *  这会返回与此日期时间相同的小时,分​​钟,秒和纳秒的{@code LocalTime}。
     * 
     * 
     * @return the time part of this date-time, not null
     */
    public LocalTime toLocalTime() {
        return time;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the hour-of-day field.
     *
     * <p>
     *  获取日期字段。
     * 
     * 
     * @return the hour-of-day, from 0 to 23
     */
    public int getHour() {
        return time.getHour();
    }

    /**
     * Gets the minute-of-hour field.
     *
     * <p>
     *  获取小时字段。
     * 
     * 
     * @return the minute-of-hour, from 0 to 59
     */
    public int getMinute() {
        return time.getMinute();
    }

    /**
     * Gets the second-of-minute field.
     *
     * <p>
     *  获取秒分秒字段。
     * 
     * 
     * @return the second-of-minute, from 0 to 59
     */
    public int getSecond() {
        return time.getSecond();
    }

    /**
     * Gets the nano-of-second field.
     *
     * <p>
     *  获取纳秒二次场。
     * 
     * 
     * @return the nano-of-second, from 0 to 999,999,999
     */
    public int getNano() {
        return time.getNano();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns an adjusted copy of this time.
     * <p>
     * This returns an {@code OffsetTime}, based on this one, with the time adjusted.
     * The adjustment takes place using the specified adjuster strategy object.
     * Read the documentation of the adjuster to understand what adjustment will be made.
     * <p>
     * A simple adjuster might simply set the one of the fields, such as the hour field.
     * A more complex adjuster might set the time to the last hour of the day.
     * <p>
     * The classes {@link LocalTime} and {@link ZoneOffset} implement {@code TemporalAdjuster},
     * thus this method can be used to change the time or offset:
     * <pre>
     *  result = offsetTime.with(time);
     *  result = offsetTime.with(offset);
     * </pre>
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalAdjuster#adjustInto(Temporal)} method on the
     * specified adjuster passing {@code this} as the argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间的调整副本。
     * <p>
     *  这将返回一个{@code OffsetTime},基于这一个,调整时间。使用指定的调整器策略对象进行调整。阅读调整器的文档以了解将要进行的调整。
     * <p>
     *  简单的调整器可以简单地设置一个字段,例如小时字段。更复杂的调整器可以将时间设置为一天的最后一个小时。
     * <p>
     *  类{@link LocalTime}和{@link ZoneOffset}实现{@code TemporalAdjuster},因此这个方法可以用来改变时间或偏移：
     * <pre>
     * result = offsetTime.with(time); result = offsetTime.with(offset);
     * </pre>
     * <p>
     *  此方法的结果是通过调用指定调整器的{@link TemporalAdjuster#adjustInto(Temporal)}方法传递{@code this}作为参数来获得的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param adjuster the adjuster to use, not null
     * @return an {@code OffsetTime} based on {@code this} with the adjustment made, not null
     * @throws DateTimeException if the adjustment cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetTime with(TemporalAdjuster adjuster) {
        // optimizations
        if (adjuster instanceof LocalTime) {
            return with((LocalTime) adjuster, offset);
        } else if (adjuster instanceof ZoneOffset) {
            return with(time, (ZoneOffset) adjuster);
        } else if (adjuster instanceof OffsetTime) {
            return (OffsetTime) adjuster;
        }
        return (OffsetTime) adjuster.adjustInto(this);
    }

    /**
     * Returns a copy of this time with the specified field set to a new value.
     * <p>
     * This returns an {@code OffsetTime}, based on this one, with the value
     * for the specified field changed.
     * This can be used to change any supported field, such as the hour, minute or second.
     * If it is not possible to set the value, because the field is not supported or for
     * some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the adjustment is implemented here.
     * <p>
     * The {@code OFFSET_SECONDS} field will return a time with the specified offset.
     * The local time is unaltered. If the new offset value is outside the valid range
     * then a {@code DateTimeException} will be thrown.
     * <p>
     * The other {@link #isSupported(TemporalField) supported fields} will behave as per
     * the matching method on {@link LocalTime#with(TemporalField, long)} LocalTime}.
     * In this case, the offset is not part of the calculation and will be unchanged.
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
     *  返回此时的指定字段设置为新值的副本。
     * <p>
     *  这将返回一个{@code OffsetTime},基于此,指定字段的值已更改。这可以用于更改任何支持的字段,例如小时,分钟或秒。如果无法设置该值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},那么在此处执行调整。
     * <p>
     *  {@code OFFSET_SECONDS}字段将返回具有指定偏移量的时间。当地时间不变。如果新的偏移值在有效范围之外,那么将抛出{@code DateTimeException}。
     * <p>
     *  其他{@link #isSupported(TemporalField)支持的字段}将按照{@link LocalTime#with(TemporalField,long)} LocalTime}中的
     * 匹配方法进行操作。
     * 在这种情况下,偏移量不是计算的一部分,并且不会改变。
     * <p>
     *  所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.adjustInto(Temporal,long)}传递{@code this}作为参数来获得此
     * 方法的结果。
     * 在这种情况下,字段确定是否以及如何调整时刻。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param field  the field to set in the result, not null
     * @param newValue  the new value of the field in the result
     * @return an {@code OffsetTime} based on {@code this} with the specified field set, not null
     * @throws DateTimeException if the field cannot be set
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetTime with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            if (field == OFFSET_SECONDS) {
                ChronoField f = (ChronoField) field;
                return with(time, ZoneOffset.ofTotalSeconds(f.checkValidIntValue(newValue)));
            }
            return with(time.with(field, newValue), offset);
        }
        return field.adjustInto(this, newValue);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetTime} with the hour-of-day value altered.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本,其中日期值的值已更改。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hour  the hour-of-day to set in the result, from 0 to 23
     * @return an {@code OffsetTime} based on this time with the requested hour, not null
     * @throws DateTimeException if the hour value is invalid
     */
    public OffsetTime withHour(int hour) {
        return with(time.withHour(hour), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the minute-of-hour value altered.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本,其中小时值的值已更改。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minute  the minute-of-hour to set in the result, from 0 to 59
     * @return an {@code OffsetTime} based on this time with the requested minute, not null
     * @throws DateTimeException if the minute value is invalid
     */
    public OffsetTime withMinute(int minute) {
        return with(time.withMinute(minute), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the second-of-minute value altered.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本,其中二分钟值已更改。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param second  the second-of-minute to set in the result, from 0 to 59
     * @return an {@code OffsetTime} based on this time with the requested second, not null
     * @throws DateTimeException if the second value is invalid
     */
    public OffsetTime withSecond(int second) {
        return with(time.withSecond(second), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the nano-of-second value altered.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的纳秒值已更改的副本。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanoOfSecond  the nano-of-second to set in the result, from 0 to 999,999,999
     * @return an {@code OffsetTime} based on this time with the requested nanosecond, not null
     * @throws DateTimeException if the nanos value is invalid
     */
    public OffsetTime withNano(int nanoOfSecond) {
        return with(time.withNano(nanoOfSecond), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetTime} with the time truncated.
     * <p>
     * Truncation returns a copy of the original time with fields
     * smaller than the specified unit set to zero.
     * For example, truncating with the {@link ChronoUnit#MINUTES minutes} unit
     * will set the second-of-minute and nano-of-second field to zero.
     * <p>
     * The unit must have a {@linkplain TemporalUnit#getDuration() duration}
     * that divides into the length of a standard day without remainder.
     * This includes all supplied time units on {@link ChronoUnit} and
     * {@link ChronoUnit#DAYS DAYS}. Other units throw an exception.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本并截断时间。
     * <p>
     * 截断返回原始时间的副本,其中字段小于指定的单位设置为零。例如,使用{@link ChronoUnit#MINUTES分钟}单位进行截断将会将秒数和毫微秒字段设置为零。
     * <p>
     *  单位必须有一个{@linkplain TemporalUnit#getDuration()duration},它分成标准日的长度,没有余数。
     * 这包括{@link ChronoUnit}和{@link ChronoUnit#DAYS DAYS}上提供的所有时间单位。其他单位抛出异常。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param unit  the unit to truncate to, not null
     * @return an {@code OffsetTime} based on this time with the time truncated, not null
     * @throws DateTimeException if unable to truncate
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     */
    public OffsetTime truncatedTo(TemporalUnit unit) {
        return with(time.truncatedTo(unit), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this time with the specified amount added.
     * <p>
     * This returns an {@code OffsetTime}, based on this one, with the specified amount added.
     * The amount is typically {@link Duration} but may be any other type implementing
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
     *  返回此时间的指定添加金额的副本。
     * <p>
     *  这将返回一个{@code OffsetTime},基于这一个,与指定的添加量。金额通常为{@link Duration},但可以是实现{@link TemporalAmount}界面的任何其他类型。
     * <p>
     *  通过调用{@link TemporalAmount#addTo(Temporal)}将计算委托给金额对象。
     * 金额实现可以以任何希望的方式实现添加,但是它通常回调{@link #plus(long,TemporalUnit)}。请参阅金额实施的文档以确定是否可以成功添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount to add, not null
     * @return an {@code OffsetTime} based on this time with the addition made, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetTime plus(TemporalAmount amountToAdd) {
        return (OffsetTime) amountToAdd.addTo(this);
    }

    /**
     * Returns a copy of this time with the specified amount added.
     * <p>
     * This returns an {@code OffsetTime}, based on this one, with the amount
     * in terms of the unit added. If it is not possible to add the amount, because the
     * unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoUnit} then the addition is implemented by
     * {@link LocalTime#plus(long, TemporalUnit)}.
     * The offset is not part of the calculation and will be unchanged in the result.
     * <p>
     * If the field is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.addTo(Temporal, long)}
     * passing {@code this} as the argument. In this case, the unit determines
     * whether and how to perform the addition.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间的指定添加金额的副本。
     * <p>
     * 这将返回一个{@code OffsetTime},根据这一个,与添加的单位的金额。如果无法添加该金额,因为该单元不受支持或出于其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoUnit},则通过{@link LocalTime#plus(long,TemporalUnit)}实现添加。偏移量不是计算的一部分,在结果中不会改变。
     * <p>
     *  如果字段不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.addTo(Temporal,long)}传递{@code this}作为参数来获得此方法的结果。
     * 在这种情况下,单元确定是否以及如何执行添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount of the unit to add to the result, may be negative
     * @param unit  the unit of the amount to add, not null
     * @return an {@code OffsetTime} based on this time with the specified amount added, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetTime plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            return with(time.plus(amountToAdd, unit), offset);
        }
        return unit.addTo(this, amountToAdd);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetTime} with the specified period in hours added.
     * <p>
     * This adds the specified number of hours to this time, returning a new time.
     * The calculation wraps around midnight.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的周期(以小时为单位)返回此{@code OffsetTime}的副本。
     * <p>
     *  这将为此时间添加指定的小时数,返回新的时间。计算在午夜左右。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hours  the hours to add, may be negative
     * @return an {@code OffsetTime} based on this time with the hours added, not null
     */
    public OffsetTime plusHours(long hours) {
        return with(time.plusHours(hours), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the specified period in minutes added.
     * <p>
     * This adds the specified number of minutes to this time, returning a new time.
     * The calculation wraps around midnight.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的周期(以分钟为单位)返回此{@code OffsetTime}的副本。
     * <p>
     *  这将为此时间添加指定的分钟数,返回一个新的时间。计算在午夜左右。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minutes  the minutes to add, may be negative
     * @return an {@code OffsetTime} based on this time with the minutes added, not null
     */
    public OffsetTime plusMinutes(long minutes) {
        return with(time.plusMinutes(minutes), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the specified period in seconds added.
     * <p>
     * This adds the specified number of seconds to this time, returning a new time.
     * The calculation wraps around midnight.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本,其中包含指定的周期(以秒为单位)。
     * <p>
     *  这将为此时间添加指定的秒数,返回新的时间。计算在午夜左右。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param seconds  the seconds to add, may be negative
     * @return an {@code OffsetTime} based on this time with the seconds added, not null
     */
    public OffsetTime plusSeconds(long seconds) {
        return with(time.plusSeconds(seconds), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the specified period in nanoseconds added.
     * <p>
     * This adds the specified number of nanoseconds to this time, returning a new time.
     * The calculation wraps around midnight.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本,其中指定的周期(以纳秒为单位)添加。
     * <p>
     *  这将为此时间添加指定的纳秒数,返回新的时间。计算在午夜左右。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanos  the nanos to add, may be negative
     * @return an {@code OffsetTime} based on this time with the nanoseconds added, not null
     */
    public OffsetTime plusNanos(long nanos) {
        return with(time.plusNanos(nanos), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this time with the specified amount subtracted.
     * <p>
     * This returns an {@code OffsetTime}, based on this one, with the specified amount subtracted.
     * The amount is typically {@link Duration} but may be any other type implementing
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
     *  返回此时间减去指定金额的副本。
     * <p>
     *  这将返回一个{@code OffsetTime},基于此,减去指定的减去。金额通常为{@link Duration},但可以是实现{@link TemporalAmount}界面的任何其他类型。
     * <p>
     *  通过调用{@link TemporalAmount#subtractFrom(Temporal)}将计算委托给金额对象。
     * 金额实现可以以任何方式自由实现减法,但它通常回调{@link #minus(long,TemporalUnit)}。请参阅金额实施的文档,以确定是否可以成功扣除。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount to subtract, not null
     * @return an {@code OffsetTime} based on this time with the subtraction made, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetTime minus(TemporalAmount amountToSubtract) {
        return (OffsetTime) amountToSubtract.subtractFrom(this);
    }

    /**
     * Returns a copy of this time with the specified amount subtracted.
     * <p>
     * This returns an {@code OffsetTime}, based on this one, with the amount
     * in terms of the unit subtracted. If it is not possible to subtract the amount,
     * because the unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * This method is equivalent to {@link #plus(long, TemporalUnit)} with the amount negated.
     * See that method for a full description of how addition, and thus subtraction, works.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间减去指定金额的副本。
     * <p>
     *  这将返回一个{@code OffsetTime},根据这一个,与减去单位的金额。如果不可能减去金额,因为该单元不受支持或由于某种其他原因,将抛出异常。
     * <p>
     * 此方法等效于{@link #plus(long,TemporalUnit)},其值为negated。请参阅该方法,了解如何添加和减少的工作原理的完整描述。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount of the unit to subtract from the result, may be negative
     * @param unit  the unit of the amount to subtract, not null
     * @return an {@code OffsetTime} based on this time with the specified amount subtracted, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetTime minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetTime} with the specified period in hours subtracted.
     * <p>
     * This subtracts the specified number of hours from this time, returning a new time.
     * The calculation wraps around midnight.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以减去的小时为单位返回此{@code OffsetTime}的副本。
     * <p>
     *  这将从此时间减去指定的小时数,返回一个新时间。计算在午夜左右。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hours  the hours to subtract, may be negative
     * @return an {@code OffsetTime} based on this time with the hours subtracted, not null
     */
    public OffsetTime minusHours(long hours) {
        return with(time.minusHours(hours), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the specified period in minutes subtracted.
     * <p>
     * This subtracts the specified number of minutes from this time, returning a new time.
     * The calculation wraps around midnight.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本,其中指定的周期(以分钟为单位)减去。
     * <p>
     *  这将从此时间减去指定的分钟数,返回一个新的时间。计算在午夜左右。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minutes  the minutes to subtract, may be negative
     * @return an {@code OffsetTime} based on this time with the minutes subtracted, not null
     */
    public OffsetTime minusMinutes(long minutes) {
        return with(time.minusMinutes(minutes), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the specified period in seconds subtracted.
     * <p>
     * This subtracts the specified number of seconds from this time, returning a new time.
     * The calculation wraps around midnight.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本,其中指定的周期(以秒为单位)减去。
     * <p>
     *  这将从此时间减去指定的秒数,返回一个新的时间。计算在午夜左右。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param seconds  the seconds to subtract, may be negative
     * @return an {@code OffsetTime} based on this time with the seconds subtracted, not null
     */
    public OffsetTime minusSeconds(long seconds) {
        return with(time.minusSeconds(seconds), offset);
    }

    /**
     * Returns a copy of this {@code OffsetTime} with the specified period in nanoseconds subtracted.
     * <p>
     * This subtracts the specified number of nanoseconds from this time, returning a new time.
     * The calculation wraps around midnight.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetTime}的副本,其中指定的周期减去纳秒。
     * <p>
     *  这从该时间减去指定的纳秒数,返回一个新的时间。计算在午夜左右。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanos  the nanos to subtract, may be negative
     * @return an {@code OffsetTime} based on this time with the nanoseconds subtracted, not null
     */
    public OffsetTime minusNanos(long nanos) {
        return with(time.minusNanos(nanos), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this time using the specified query.
     * <p>
     * This queries this time using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  此时使用指定的查询查询。
     * <p>
     * 此时使用指定的查询策略对象进行查询。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
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
        if (query == TemporalQueries.offset() || query == TemporalQueries.zone()) {
            return (R) offset;
        } else if (query == TemporalQueries.zoneId() | query == TemporalQueries.chronology() || query == TemporalQueries.localDate()) {
            return null;
        } else if (query == TemporalQueries.localTime()) {
            return (R) time;
        } else if (query == TemporalQueries.precision()) {
            return (R) NANOS;
        }
        // inline TemporalAccessor.super.query(query) as an optimization
        // non-JDK classes are not permitted to make this optimization
        return query.queryFrom(this);
    }

    /**
     * Adjusts the specified temporal object to have the same offset and time
     * as this object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the offset and time changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * twice, passing {@link ChronoField#NANO_OF_DAY} and
     * {@link ChronoField#OFFSET_SECONDS} as the fields.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisOffsetTime.adjustInto(temporal);
     *   temporal = temporal.with(thisOffsetTime);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为具有与此对象相同的偏移和时间。
     * <p>
     *  这返回与具有偏移的输入相同的可观察类型的时间对象,并且时间被改变为与此相同。
     * <p>
     *  该调整等同于使用{@link Temporal#with(TemporalField,long)}两次,将{@link ChronoField#NANO_OF_DAY}和{@link ChronoField#OFFSET_SECONDS}
     * 作为字段。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐temporal = thisOffsetTime.adjustInto(temporal); temporal = temporal.with(thisOffs
     * etTime);。
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
        return temporal
                .with(NANO_OF_DAY, time.toNanoOfDay())
                .with(OFFSET_SECONDS, offset.getTotalSeconds());
    }

    /**
     * Calculates the amount of time until another time in terms of the specified unit.
     * <p>
     * This calculates the amount of time between two {@code OffsetTime}
     * objects in terms of a single {@code TemporalUnit}.
     * The start and end points are {@code this} and the specified time.
     * The result will be negative if the end is before the start.
     * For example, the period in hours between two times can be calculated
     * using {@code startTime.until(endTime, HOURS)}.
     * <p>
     * The {@code Temporal} passed to this method is converted to a
     * {@code OffsetTime} using {@link #from(TemporalAccessor)}.
     * If the offset differs between the two times, then the specified
     * end time is normalized to have the same offset as this time.
     * <p>
     * The calculation returns a whole number, representing the number of
     * complete units between the two times.
     * For example, the period in hours between 11:30Z and 13:29Z will only
     * be one hour as it is one minute short of two hours.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method.
     * The second is to use {@link TemporalUnit#between(Temporal, Temporal)}:
     * <pre>
     *   // these two lines are equivalent
     *   amount = start.until(end, MINUTES);
     *   amount = MINUTES.between(start, end);
     * </pre>
     * The choice should be made based on which makes the code more readable.
     * <p>
     * The calculation is implemented in this method for {@link ChronoUnit}.
     * The units {@code NANOS}, {@code MICROS}, {@code MILLIS}, {@code SECONDS},
     * {@code MINUTES}, {@code HOURS} and {@code HALF_DAYS} are supported.
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
     *  以指定单位计算直到另一时间的时间量。
     * <p>
     * 这将根据单个{@code TemporalUnit}计算两个{@code OffsetTime}对象之间的时间量。起点和终点是{@code this}和指定的时间。如果结束在开始之前,结果将为负。
     * 例如,可以使用{@code startTime.until(endTime,HOURS)}计算两个时间之间的小时数。
     * <p>
     *  传递给此方法的{@code Temporal}将使用{@link #from(TemporalAccessor)}转换为{@code OffsetTime}。
     * 如果偏移在两个时间之间不同,则指定的结束时间被归一化为具有与该时间相同的偏移。
     * <p>
     *  计算返回一个整数,表示两个时间之间的完整单位数。例如,11：30Z和13：29Z之间的小时数将仅为一小时,因为它比两小时短一分钟。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是调用这个方法。第二个是使用{@link TemporalUnit#between(Temporal,Temporal)}：
     * <pre>
     *  //这两行是等价的amount = start.until(end,MINUTES); amount = MINUTES.between(start,end);
     * </pre>
     *  应该基于哪个使得代码更可读的选择。
     * <p>
     *  该计算在{@link ChronoUnit}的此方法中实现。
     * 支持{@code NANOS},{@code MICROS},{@code MILLIS},{@code SECONDS},{@code MINUTES},{@code HOURS}和{@code HALF_DAYS}
     * 的单位。
     *  该计算在{@link ChronoUnit}的此方法中实现。其他{@code ChronoUnit}值会抛出异常。
     * <p>
     * 如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.between(Temporal,Temporal)}传递{@code this}作为第一个参数和转
     * 换的输入时间为第二个参数。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param endExclusive  the end date, exclusive, which is converted to an {@code OffsetTime}, not null
     * @param unit  the unit to measure the amount in, not null
     * @return the amount of time between this time and the end time
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to an {@code OffsetTime}
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        OffsetTime end = OffsetTime.from(endExclusive);
        if (unit instanceof ChronoUnit) {
            long nanosUntil = end.toEpochNano() - toEpochNano();  // no overflow
            switch ((ChronoUnit) unit) {
                case NANOS: return nanosUntil;
                case MICROS: return nanosUntil / 1000;
                case MILLIS: return nanosUntil / 1000_000;
                case SECONDS: return nanosUntil / NANOS_PER_SECOND;
                case MINUTES: return nanosUntil / NANOS_PER_MINUTE;
                case HOURS: return nanosUntil / NANOS_PER_HOUR;
                case HALF_DAYS: return nanosUntil / (12 * NANOS_PER_HOUR);
            }
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return unit.between(this, end);
    }

    /**
     * Formats this time using the specified formatter.
     * <p>
     * This time will be passed to the formatter to produce a string.
     *
     * <p>
     *  此时使用指定的格式化程序格式化。
     * <p>
     *  这个时间将被传递给格式化程序以产生一个字符串。
     * 
     * 
     * @param formatter  the formatter to use, not null
     * @return the formatted time string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    public String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Combines this time with a date to create an {@code OffsetDateTime}.
     * <p>
     * This returns an {@code OffsetDateTime} formed from this time and the specified date.
     * All possible combinations of date and time are valid.
     *
     * <p>
     *  将此时间与日期结合以创建{@code OffsetDateTime}。
     * <p>
     *  这将返回从此时间和指定日期形成的{@code OffsetDateTime}。所有可能的日期和时间组合都有效。
     * 
     * 
     * @param date  the date to combine with, not null
     * @return the offset date-time formed from this time and the specified date, not null
     */
    public OffsetDateTime atDate(LocalDate date) {
        return OffsetDateTime.of(date, time, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Converts this time to epoch nanos based on 1970-01-01Z.
     *
     * <p>
     *  基于1970-01-01Z将此时间转换为epoch nanos。
     * 
     * 
     * @return the epoch nanos value
     */
    private long toEpochNano() {
        long nod = time.toNanoOfDay();
        long offsetNanos = offset.getTotalSeconds() * NANOS_PER_SECOND;
        return nod - offsetNanos;
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this {@code OffsetTime} to another time.
     * <p>
     * The comparison is based first on the UTC equivalent instant, then on the local time.
     * It is "consistent with equals", as defined by {@link Comparable}.
     * <p>
     * For example, the following is the comparator order:
     * <ol>
     * <li>{@code 10:30+01:00}</li>
     * <li>{@code 11:00+01:00}</li>
     * <li>{@code 12:00+02:00}</li>
     * <li>{@code 11:30+01:00}</li>
     * <li>{@code 12:00+01:00}</li>
     * <li>{@code 12:30+01:00}</li>
     * </ol>
     * Values #2 and #3 represent the same instant on the time-line.
     * When two values represent the same instant, the local time is compared
     * to distinguish them. This step is needed to make the ordering
     * consistent with {@code equals()}.
     * <p>
     * To compare the underlying local time of two {@code TemporalAccessor} instances,
     * use {@link ChronoField#NANO_OF_DAY} as a comparator.
     *
     * <p>
     *  将此{@code OffsetTime}与另一个时间进行比较。
     * <p>
     *  比较首先基于UTC等效时间,然后基于本地时间。它是"与等号一致",由{@link Comparable}定义。
     * <p>
     *  例如,以下是比较器顺序：
     * <ol>
     *  <li> {@ code 10：30 + 01：00} </li> <li> {@ code 11：00 + 01：00} </li> <li> {@ code 12：00 + 02：00} </li>
     *  <li> {@ code 11：30 + 01：00} </li> <li> {@ code 12：00 + 01：00} </li> 01:00} </li>。
     * </ol>
     *  值#2和#3表示时间线上的相同时刻。当两个值表示相同的时刻时,比较本地时间以区分它们。需要此步骤使排序与{@code equals()}一致。
     * <p>
     *  要比较两个{@code TemporalAccessor}实例的底层本地时间,请使用{@link ChronoField#NANO_OF_DAY}作为比较。
     * 
     * 
     * @param other  the other time to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     * @throws NullPointerException if {@code other} is null
     */
    @Override
    public int compareTo(OffsetTime other) {
        if (offset.equals(other.offset)) {
            return time.compareTo(other.time);
        }
        int compare = Long.compare(toEpochNano(), other.toEpochNano());
        if (compare == 0) {
            compare = time.compareTo(other.time);
        }
        return compare;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the instant of this {@code OffsetTime} is after that of the
     * specified time applying both times to a common date.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the instant of the time. This is equivalent to converting both
     * times to an instant using the same date and comparing the instants.
     *
     * <p>
     * 检查此{@code OffsetTime}的时间是否超过将两次应用到共同日期的指定时间。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它只比较时间的时间。这相当于使用相同的日期和比较时刻将两个时间转换为瞬时。
     * 
     * 
     * @param other  the other time to compare to, not null
     * @return true if this is after the instant of the specified time
     */
    public boolean isAfter(OffsetTime other) {
        return toEpochNano() > other.toEpochNano();
    }

    /**
     * Checks if the instant of this {@code OffsetTime} is before that of the
     * specified time applying both times to a common date.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the instant of the time. This is equivalent to converting both
     * times to an instant using the same date and comparing the instants.
     *
     * <p>
     *  检查此{@code OffsetTime}的时间是否早于将两次应用到共同日期的指定时间。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它只比较时间的时间。这等效于使用相同的日期将两个时间转换为瞬时并比较时刻。
     * 
     * 
     * @param other  the other time to compare to, not null
     * @return true if this is before the instant of the specified time
     */
    public boolean isBefore(OffsetTime other) {
        return toEpochNano() < other.toEpochNano();
    }

    /**
     * Checks if the instant of this {@code OffsetTime} is equal to that of the
     * specified time applying both times to a common date.
     * <p>
     * This method differs from the comparison in {@link #compareTo} and {@link #equals}
     * in that it only compares the instant of the time. This is equivalent to converting both
     * times to an instant using the same date and comparing the instants.
     *
     * <p>
     *  检查此{@code OffsetTime}的时间是否等于将两个时间应用于共同日期的指定时间的时间。
     * <p>
     *  此方法与{@link #compareTo}和{@link #equals}中的比较不同,它仅比较时间的时间。这相当于使用相同的日期和比较时刻将两个时间转换为瞬时。
     * 
     * 
     * @param other  the other time to compare to, not null
     * @return true if this is equal to the instant of the specified time
     */
    public boolean isEqual(OffsetTime other) {
        return toEpochNano() == other.toEpochNano();
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this time is equal to another time.
     * <p>
     * The comparison is based on the local-time and the offset.
     * To compare for the same instant on the time-line, use {@link #isEqual(OffsetTime)}.
     * <p>
     * Only objects of type {@code OffsetTime} are compared, other types return false.
     * To compare the underlying local time of two {@code TemporalAccessor} instances,
     * use {@link ChronoField#NANO_OF_DAY} as a comparator.
     *
     * <p>
     *  检查此时间是否等于另一时间。
     * <p>
     *  比较基于局部时间和偏移。要在时间线上的同一时刻进行比较,请使用{@link #isEqual(OffsetTime)}。
     * <p>
     *  仅比较{@code OffsetTime}类型的对象,其他类型返回false。
     * 要比较两个{@code TemporalAccessor}实例的底层本地时间,请使用{@link ChronoField#NANO_OF_DAY}作为比较。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other time
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OffsetTime) {
            OffsetTime other = (OffsetTime) obj;
            return time.equals(other.time) && offset.equals(other.offset);
        }
        return false;
    }

    /**
     * A hash code for this time.
     *
     * <p>
     *  此时的哈希代码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return time.hashCode() ^ offset.hashCode();
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this time as a {@code String}, such as {@code 10:15:30+01:00}.
     * <p>
     * The output will be one of the following ISO-8601 formats:
     * <ul>
     * <li>{@code HH:mmXXXXX}</li>
     * <li>{@code HH:mm:ssXXXXX}</li>
     * <li>{@code HH:mm:ss.SSSXXXXX}</li>
     * <li>{@code HH:mm:ss.SSSSSSXXXXX}</li>
     * <li>{@code HH:mm:ss.SSSSSSSSSXXXXX}</li>
     * </ul>
     * The format used will be the shortest that outputs the full value of
     * the time where the omitted parts are implied to be zero.
     *
     * <p>
     * 此时输出为{@code String},例如{@code 10：15：30 + 01：00}。
     * <p>
     *  输出将是以下ISO-8601格式之一：
     * <ul>
     *  <li> {@ code HH：mmXXXXX} </li> <li> {@ code HH：mm：ssXXXXX} </li> <li> {@ code HH：mm：ss.SSSXXXXX} > {@ code HH：mm：ss.SSSSSSXXXXX}
     *  </li> <li> {@ code HH：mm：ss.SSSSSSSSSXXXXX} </li>。
     * </ul>
     *  所使用的格式将是输出完全值的时间的最短,其中省略的部分被暗示为零。
     * 
     * @return a string representation of this time, not null
     */
    @Override
    public String toString() {
        return time.toString() + offset.toString();
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(9);  // identifies an OffsetTime
     *  // the <a href="../../serialized-form.html#java.time.LocalTime">time</a> excluding the one byte header
     *  // the <a href="../../serialized-form.html#java.time.ZoneOffset">offset</a> excluding the one byte header
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.OFFSET_TIME_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     *  使用<a href="../../serialized-form.html#java.time.Ser">专用序列化表单</a>写入对象。
     * 
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(ObjectOutput out) throws IOException {
        time.writeExternal(out);
        offset.writeExternal(out);
    }

    static OffsetTime readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        LocalTime time = LocalTime.readExternal(in);
        ZoneOffset offset = ZoneOffset.readExternal(in);
        return OffsetTime.of(time, offset);
    }

}
