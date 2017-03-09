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

import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;
import static java.time.temporal.ChronoUnit.FOREVER;
import static java.time.temporal.ChronoUnit.NANOS;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.chrono.IsoChronology;
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
import java.util.Comparator;
import java.util.Objects;

/**
 * A date-time with an offset from UTC/Greenwich in the ISO-8601 calendar system,
 * such as {@code 2007-12-03T10:15:30+01:00}.
 * <p>
 * {@code OffsetDateTime} is an immutable representation of a date-time with an offset.
 * This class stores all date and time fields, to a precision of nanoseconds,
 * as well as the offset from UTC/Greenwich. For example, the value
 * "2nd October 2007 at 13:45.30.123456789 +02:00" can be stored in an {@code OffsetDateTime}.
 * <p>
 * {@code OffsetDateTime}, {@link java.time.ZonedDateTime} and {@link java.time.Instant} all store an instant
 * on the time-line to nanosecond precision.
 * {@code Instant} is the simplest, simply representing the instant.
 * {@code OffsetDateTime} adds to the instant the offset from UTC/Greenwich, which allows
 * the local date-time to be obtained.
 * {@code ZonedDateTime} adds full time-zone rules.
 * <p>
 * It is intended that {@code ZonedDateTime} or {@code Instant} is used to model data
 * in simpler applications. This class may be used when modeling date-time concepts in
 * more detail, or when communicating to a database or in a network protocol.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code OffsetDateTime} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  在ISO-8601日历系统中偏离UTC /格林威治的日期时间,例如{@code 2007-12-03T10：15：30 + 01：00}。
 * <p>
 *  {@code OffsetDateTime}是具有偏移量的日期时间的不可变表示。此类存储所有日期和时间字段,精度为纳秒,以及与UTC / Greenwich的偏移量。
 * 例如,值"2007年10月2日在13：45.30.123456789 +02：00"可以存储在{@code OffsetDateTime}中。
 * <p>
 * {@code OffsetDateTime},{@link java.time.ZonedDateTime}和{@link java.time.Instant}都将时间线上的瞬间存储到纳秒精度。
 *  {@code Instant}是最简单的,只是表示即时。 {@code OffsetDateTime}添加到UTC / Greenwich的偏移,允许获取本地日期时间。
 *  {@code ZonedDateTime}添加完整的时区规则。
 * <p>
 *  目的是{@code ZonedDateTime}或{@code Instant}用于在更简单的应用程序中建模数据。当更详细地建模日期时间概念时,或者当与数据库或网络协议通信时,可以使用该类。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;对{@code OffsetDateTime}实例使用身
 * 份敏感操作(包括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class OffsetDateTime
        implements Temporal, TemporalAdjuster, Comparable<OffsetDateTime>, Serializable {

    /**
     * The minimum supported {@code OffsetDateTime}, '-999999999-01-01T00:00:00+18:00'.
     * This is the local date-time of midnight at the start of the minimum date
     * in the maximum offset (larger offsets are earlier on the time-line).
     * This combines {@link LocalDateTime#MIN} and {@link ZoneOffset#MAX}.
     * This could be used by an application as a "far past" date-time.
     * <p>
     *  最低支持{@code OffsetDateTime},'-999999999-01-01T00：00：00 + 18：00'。
     * 这是最大偏移量中最小日期开始时午夜的本地日期 - 时间(较大的偏移量在时间线上较早)。结合{@link LocalDateTime#MIN}和{@link ZoneOffset#MAX}。
     * 这可以由应用程序用作"远过去"日期时间。
     * 
     */
    public static final OffsetDateTime MIN = LocalDateTime.MIN.atOffset(ZoneOffset.MAX);
    /**
     * The maximum supported {@code OffsetDateTime}, '+999999999-12-31T23:59:59.999999999-18:00'.
     * This is the local date-time just before midnight at the end of the maximum date
     * in the minimum offset (larger negative offsets are later on the time-line).
     * This combines {@link LocalDateTime#MAX} and {@link ZoneOffset#MIN}.
     * This could be used by an application as a "far future" date-time.
     * <p>
     * 最多支持的{@code OffsetDateTime},'+ 999999999-12-31T23：59：59.999999999-18：00'。
     * 这是在最小偏移量的最大日期之前的午夜之前的本地日期时间(较大的负偏移在时间线上较晚)。结合{@link LocalDateTime#MAX}和{@link ZoneOffset#MIN}。
     * 这可以被应用程序用作"远期"日期时间。
     * 
     */
    public static final OffsetDateTime MAX = LocalDateTime.MAX.atOffset(ZoneOffset.MIN);

    /**
     * Gets a comparator that compares two {@code OffsetDateTime} instances
     * based solely on the instant.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying instant.
     *
     * <p>
     *  获取一个比较器,它仅根据即时比较两个{@code OffsetDateTime}实例。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较基础时刻。
     * 
     * 
     * @return a comparator that compares in time-line order
     *
     * @see #isAfter
     * @see #isBefore
     * @see #isEqual
     */
    public static Comparator<OffsetDateTime> timeLineOrder() {
        return OffsetDateTime::compareInstant;
    }

    /**
     * Compares this {@code OffsetDateTime} to another date-time.
     * The comparison is based on the instant.
     *
     * <p>
     *  将此{@code OffsetDateTime}与另一个日期时间进行比较。比较基于时刻。
     * 
     * 
     * @param datetime1  the first date-time to compare, not null
     * @param datetime2  the other date-time to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    private static int compareInstant(OffsetDateTime datetime1, OffsetDateTime datetime2) {
        if (datetime1.getOffset().equals(datetime2.getOffset())) {
            return datetime1.toLocalDateTime().compareTo(datetime2.toLocalDateTime());
        }
        int cmp = Long.compare(datetime1.toEpochSecond(), datetime2.toEpochSecond());
        if (cmp == 0) {
            cmp = datetime1.toLocalTime().getNano() - datetime2.toLocalTime().getNano();
        }
        return cmp;
    }

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 2287754244819255394L;

    /**
     * The local date-time.
     * <p>
     *  本地日期时间。
     * 
     */
    private final LocalDateTime dateTime;
    /**
     * The offset from UTC/Greenwich.
     * <p>
     *  偏离UTC /格林威治。
     * 
     */
    private final ZoneOffset offset;

    //-----------------------------------------------------------------------
    /**
     * Obtains the current date-time from the system clock in the default time-zone.
     * <p>
     * This will query the {@link java.time.Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current date-time.
     * The offset will be calculated from the time-zone in the clock.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区中从系统时钟获取当前日期时间。
     * <p>
     *  这将在默认时区中查询{@link java.time.Clock#systemDefaultZone()系统时钟}以获取当前日期时间。偏移将根据时钟中的时区计算。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @return the current date-time using the system clock, not null
     */
    public static OffsetDateTime now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current date-time from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(java.time.ZoneId) system clock} to obtain the current date-time.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * The offset will be calculated from the specified time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区中从系统时钟获取当前日期时间。
     * <p>
     *  这将查询{@link Clock#system(java.time.ZoneId)系统时钟}以获取当前日期时间。指定时区避免了对默认时区的依赖。将根据指定的时区计算偏移量。
     * <p>
     * 使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current date-time using the system clock, not null
     */
    public static OffsetDateTime now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current date-time from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current date-time.
     * The offset will be calculated from the time-zone in the clock.
     * <p>
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前日期时间。
     * <p>
     *  这将查询指定的时钟以获取当前日期时间。偏移将根据时钟中的时区计算。
     * <p>
     *  使用此方法允许使用替代时钟进行测试。可以使用{@link时钟依赖注入}来引入替代时钟。
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current date-time, not null
     */
    public static OffsetDateTime now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        final Instant now = clock.instant();  // called once
        return ofInstant(now, clock.getZone().getRules().getOffset(now));
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code OffsetDateTime} from a date, time and offset.
     * <p>
     * This creates an offset date-time with the specified local date, time and offset.
     *
     * <p>
     *  从日期,时间和偏移获取{@code OffsetDateTime}的实例。
     * <p>
     *  这将创建具有指定的本地日期,时间和偏移量的偏移日期时间。
     * 
     * 
     * @param date  the local date, not null
     * @param time  the local time, not null
     * @param offset  the zone offset, not null
     * @return the offset date-time, not null
     */
    public static OffsetDateTime of(LocalDate date, LocalTime time, ZoneOffset offset) {
        LocalDateTime dt = LocalDateTime.of(date, time);
        return new OffsetDateTime(dt, offset);
    }

    /**
     * Obtains an instance of {@code OffsetDateTime} from a date-time and offset.
     * <p>
     * This creates an offset date-time with the specified local date-time and offset.
     *
     * <p>
     *  从日期时间和偏移获取{@code OffsetDateTime}的实例。
     * <p>
     *  这将创建具有指定的本地日期时间和偏移量的偏移日期时间。
     * 
     * 
     * @param dateTime  the local date-time, not null
     * @param offset  the zone offset, not null
     * @return the offset date-time, not null
     */
    public static OffsetDateTime of(LocalDateTime dateTime, ZoneOffset offset) {
        return new OffsetDateTime(dateTime, offset);
    }

    /**
     * Obtains an instance of {@code OffsetDateTime} from a year, month, day,
     * hour, minute, second, nanosecond and offset.
     * <p>
     * This creates an offset date-time with the seven specified fields.
     * <p>
     * This method exists primarily for writing test cases.
     * Non test-code will typically use other methods to create an offset time.
     * {@code LocalDateTime} has five additional convenience variants of the
     * equivalent factory method taking fewer arguments.
     * They are not provided here to reduce the footprint of the API.
     *
     * <p>
     *  从年,月,日,小时,分钟,秒,纳秒和偏移获取{@code OffsetDateTime}的实例。
     * <p>
     *  这将使用七个指定字段创建偏移日期时间。
     * <p>
     *  这种方法主要用于写测试用例。非测试代码通常将使用其他方法来创建偏移时间。 {@code LocalDateTime}有五个额外的方便变体的等效工厂方法需要较少的参数。
     * 这里不提供它们来减少API的占用空间。
     * 
     * 
     * @param year  the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, from 1 (January) to 12 (December)
     * @param dayOfMonth  the day-of-month to represent, from 1 to 31
     * @param hour  the hour-of-day to represent, from 0 to 23
     * @param minute  the minute-of-hour to represent, from 0 to 59
     * @param second  the second-of-minute to represent, from 0 to 59
     * @param nanoOfSecond  the nano-of-second to represent, from 0 to 999,999,999
     * @param offset  the zone offset, not null
     * @return the offset date-time, not null
     * @throws DateTimeException if the value of any field is out of range, or
     *  if the day-of-month is invalid for the month-year
     */
    public static OffsetDateTime of(
            int year, int month, int dayOfMonth,
            int hour, int minute, int second, int nanoOfSecond, ZoneOffset offset) {
        LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
        return new OffsetDateTime(dt, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code OffsetDateTime} from an {@code Instant} and zone ID.
     * <p>
     * This creates an offset date-time with the same instant as that specified.
     * Finding the offset from UTC/Greenwich is simple as there is only one valid
     * offset for each instant.
     *
     * <p>
     *  从{@code Instant}和区域ID获取{@code OffsetDateTime}的实例。
     * <p>
     * 这将创建与指定的时间相同的偏移日期时间。从UTC / Greenwich找到偏移是很简单的,因为每个时刻只有一个有效的偏移。
     * 
     * 
     * @param instant  the instant to create the date-time from, not null
     * @param zone  the time-zone, which may be an offset, not null
     * @return the offset date-time, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    public static OffsetDateTime ofInstant(Instant instant, ZoneId zone) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zone, "zone");
        ZoneRules rules = zone.getRules();
        ZoneOffset offset = rules.getOffset(instant);
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), offset);
        return new OffsetDateTime(ldt, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code OffsetDateTime} from a temporal object.
     * <p>
     * This obtains an offset date-time based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code OffsetDateTime}.
     * <p>
     * The conversion will first obtain a {@code ZoneOffset} from the temporal object.
     * It will then try to obtain a {@code LocalDateTime}, falling back to an {@code Instant} if necessary.
     * The result will be the combination of {@code ZoneOffset} with either
     * with {@code LocalDateTime} or {@code Instant}.
     * Implementations are permitted to perform optimizations such as accessing
     * those fields that are equivalent to the relevant objects.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code OffsetDateTime::from}.
     *
     * <p>
     *  从时间对象获取{@code OffsetDateTime}的实例。
     * <p>
     *  这基于指定的时间获得偏移日期时间。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code OffsetDateTime}的实例。
     * <p>
     *  转换将首先从时间对象获得{@code ZoneOffset}。然后,它将尝试获取{@code LocalDateTime},如有必要,会返回到{@code Instant}。
     * 结果将是{@code ZoneOffset}与{@code LocalDateTime}或{@code Instant}的组合。实现允许执行优化,例如访问等价于相关对象的那些字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许通过方法引用{@code OffsetDateTime :: from}在查询中使用它。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the offset date-time, not null
     * @throws DateTimeException if unable to convert to an {@code OffsetDateTime}
     */
    public static OffsetDateTime from(TemporalAccessor temporal) {
        if (temporal instanceof OffsetDateTime) {
            return (OffsetDateTime) temporal;
        }
        try {
            ZoneOffset offset = ZoneOffset.from(temporal);
            LocalDate date = temporal.query(TemporalQueries.localDate());
            LocalTime time = temporal.query(TemporalQueries.localTime());
            if (date != null && time != null) {
                return OffsetDateTime.of(date, time, offset);
            } else {
                Instant instant = Instant.from(temporal);
                return OffsetDateTime.ofInstant(instant, offset);
            }
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain OffsetDateTime from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code OffsetDateTime} from a text string
     * such as {@code 2007-12-03T10:15:30+01:00}.
     * <p>
     * The string must represent a valid date-time and is parsed using
     * {@link java.time.format.DateTimeFormatter#ISO_OFFSET_DATE_TIME}.
     *
     * <p>
     *  从文本字符串(例如{@code 2007-12-03T10：15：30 + 01：00})获取{@code OffsetDateTime}的实例。
     * <p>
     *  该字符串必须表示有效的日期时间,并使用{@link java.time.format.DateTimeFormatter#ISO_OFFSET_DATE_TIME}进行解析。
     * 
     * 
     * @param text  the text to parse such as "2007-12-03T10:15:30+01:00", not null
     * @return the parsed offset date-time, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static OffsetDateTime parse(CharSequence text) {
        return parse(text, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * Obtains an instance of {@code OffsetDateTime} from a text string using a specific formatter.
     * <p>
     * The text is parsed using the formatter, returning a date-time.
     *
     * <p>
     *  使用特定格式化程序从文本字符串获取{@code OffsetDateTime}的实例。
     * <p>
     *  使用格式化程序解析文本,返回日期时间。
     * 
     * 
     * @param text  the text to parse, not null
     * @param formatter  the formatter to use, not null
     * @return the parsed offset date-time, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static OffsetDateTime parse(CharSequence text, DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, OffsetDateTime::from);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * <p>
     *  构造函数。
     * 
     * 
     * @param dateTime  the local date-time, not null
     * @param offset  the zone offset, not null
     */
    private OffsetDateTime(LocalDateTime dateTime, ZoneOffset offset) {
        this.dateTime = Objects.requireNonNull(dateTime, "dateTime");
        this.offset = Objects.requireNonNull(offset, "offset");
    }

    /**
     * Returns a new date-time based on this one, returning {@code this} where possible.
     *
     * <p>
     * 基于此返回一个新的日期时间,在可能的情况下返回{@code this}。
     * 
     * 
     * @param dateTime  the date-time to create with, not null
     * @param offset  the zone offset to create with, not null
     */
    private OffsetDateTime with(LocalDateTime dateTime, ZoneOffset offset) {
        if (this.dateTime == dateTime && this.offset.equals(offset)) {
            return this;
        }
        return new OffsetDateTime(dateTime, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this date-time can be queried for the specified field.
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
     * <li>{@code DAY_OF_WEEK}
     * <li>{@code ALIGNED_DAY_OF_WEEK_IN_MONTH}
     * <li>{@code ALIGNED_DAY_OF_WEEK_IN_YEAR}
     * <li>{@code DAY_OF_MONTH}
     * <li>{@code DAY_OF_YEAR}
     * <li>{@code EPOCH_DAY}
     * <li>{@code ALIGNED_WEEK_OF_MONTH}
     * <li>{@code ALIGNED_WEEK_OF_YEAR}
     * <li>{@code MONTH_OF_YEAR}
     * <li>{@code PROLEPTIC_MONTH}
     * <li>{@code YEAR_OF_ERA}
     * <li>{@code YEAR}
     * <li>{@code ERA}
     * <li>{@code INSTANT_SECONDS}
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
     *  检查是否支持指定的字段。
     * <p>
     *  这将检查是否可以查询指定字段的此日期时间。
     * 如果为false,则调用{@link #range(TemporalField)范围},{@link #get(TemporalField)get}和{@link #with(TemporalField,long)}
     * 方法将抛出异常。
     *  这将检查是否可以查询指定字段的此日期时间。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。支持的字段包括：
     * <ul>
     *  <li> {@ code NANO_OF_DAY} <li> {@ code MANI_OF_DAY} <li> {@ code MULI_OF_DAY} <li> {@ code MICRO_OF_DAY}
     *  > {@ code SECOND_OF_MINUTE} <li> {@ code SECOND_OF_DAY} <li> {@ code MINUTE_OF_HOUR} <li> {@ code MINUTE_OF_DAY}
     *  <li> {@ code HOUR_OF_AMPM} <li> {@ code CLOCK_HOUR_OF_AMPM} @code HOUR_OF_DAY} <li> {@ code CLOCK_HOUR_OF_DAY}
     *  <li> {@ code AMPM_OF_DAY} <li> {@ code DAY_OF_WEEK} <li> {@ code ALIGNED_DAY_OF_WEEK_IN_MONTH} <li> 
     * {@ code ALIGNED_DAY_OF_WEEK_IN_YEAR} <li> {@ code DAY_OF_MONTH} <li> {@ code DAY_OF_YEAR} <li> {@ code HAY_OF_YEAR}
     *  <li> {@ code PHOCH_DAY} <li> {@ code ALIGNED_WEEK_OF_MONTH} <li> {@ code ALIGNED_WEEK_OF_YEAR} <li> 
     * {@ code YEAR_OF_ERA} <li> {@ code YEAR} <li> {@ code ERA} <li> {@ code INSTANT_SECONDS} <li> {@ code OFFSET_SECONDS}
     * 。
     * </ul>
     *  所有其他{@code ChronoField}实例将返回false。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作为
     * 参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this date-time, false if not
     */
    @Override
    public boolean isSupported(TemporalField field) {
        return field instanceof ChronoField || (field != null && field.isSupportedBy(this));
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
     * <li>{@code DAYS}
     * <li>{@code WEEKS}
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
     *  检查是否支持指定的单元。
     * <p>
     *  这将检查指定的单位是否可以添加到此日期时间或从此日期时间中减去。
     * 如果为false,则调用{@link #plus(long,TemporalUnit)}和{@link #minus(long,TemporalUnit)minus}方法将抛出异常。
     * <p>
     *  如果单位是{@link ChronoUnit},则在此执行查询。支持的单位有：
     * <ul>
     *  <li> {@ code MINUTES} <li> {@ code MICROS} <li> {@ code MILLIS} <li> {@ code SECONDS} <li> {@ code MINUTES}
     *  > {@ code HALF_DAYS} <li> {@ code DAYS} <li> {@ code WEEKS} <li> {@ code MONTHS} <li> {@ code YEARS}
     *  <li> {@ code DECADES} <li> { @code CENTURIES} <li> {@ code MILLENNIA} <li> {@ code ERAS}。
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
            return unit != FOREVER;
        }
        return unit != null && unit.isSupportedBy(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This date-time is used to enhance the accuracy of the returned range.
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
     * 范围对象表示字段的最小和最大有效值。此日期时间用于提高返回范围的精度。如果不可能返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
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
            if (field == INSTANT_SECONDS || field == OFFSET_SECONDS) {
                return field.range();
            }
            return dateTime.range(field);
        }
        return field.rangeRefinedBy(this);
    }

    /**
     * Gets the value of the specified field from this date-time as an {@code int}.
     * <p>
     * This queries this date-time for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this date-time, except {@code NANO_OF_DAY}, {@code MICRO_OF_DAY},
     * {@code EPOCH_DAY}, {@code PROLEPTIC_MONTH} and {@code INSTANT_SECONDS} which are too
     * large to fit in an {@code int} and throw a {@code DateTimeException}.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此日期时间获取指定字段的值为{@code int}。
     * <p>
     *  这将查询指定字段的值的此日期时间。返回的值将始终在字段的有效值范围内。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     * 如果字段是{@link ChronoField},则在此执行查询。
     * 根据此日期时间,{@link #isSupported(TemporalField)支持的字段}将返回有效的值,{@code NANO_OF_DAY},{@code MICRO_OF_DAY},{@code EPOCH_DAY}
     * ,{@code PROLEPTIC_MONTH}和{@代码INSTANT_SECONDS},它们太大,无法适应{@code int}并抛出{@code DateTimeException}。
     * 如果字段是{@link ChronoField},则在此执行查询。
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
    @Override
    public int get(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case INSTANT_SECONDS:
                    throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
                case OFFSET_SECONDS:
                    return getOffset().getTotalSeconds();
            }
            return dateTime.get(field);
        }
        return Temporal.super.get(field);
    }

    /**
     * Gets the value of the specified field from this date-time as a {@code long}.
     * <p>
     * This queries this date-time for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this date-time.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此日期时间获取指定字段的值为{@code long}。
     * <p>
     *  这将查询指定字段的值的此日期时间。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)supported fields}将根据此日期时间返回有效值。
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
            switch ((ChronoField) field) {
                case INSTANT_SECONDS: return toEpochSecond();
                case OFFSET_SECONDS: return getOffset().getTotalSeconds();
            }
            return dateTime.getLong(field);
        }
        return field.getFrom(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the zone offset, such as '+01:00'.
     * <p>
     * This is the offset of the local date-time from UTC/Greenwich.
     *
     * <p>
     *  获取区域偏移量,例如"+01：00"。
     * <p>
     *  这是本地日期时间与UTC /格林威治的偏移量。
     * 
     * 
     * @return the zone offset, not null
     */
    public ZoneOffset getOffset() {
        return offset;
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified offset ensuring
     * that the result has the same local date-time.
     * <p>
     * This method returns an object with the same {@code LocalDateTime} and the specified {@code ZoneOffset}.
     * No calculation is needed or performed.
     * For example, if this time represents {@code 2007-12-03T10:30+02:00} and the offset specified is
     * {@code +03:00}, then this method will return {@code 2007-12-03T10:30+03:00}.
     * <p>
     * To take into account the difference between the offsets, and adjust the time fields,
     * use {@link #withOffsetSameInstant}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  使用指定的偏移量返回此{@code OffsetDateTime}的副本,以确保结果具有相同的本地日期时间。
     * <p>
     *  此方法返回具有相同{@code LocalDateTime}和指定的{@code ZoneOffset}的对象。不需要或不执行计算。
     * 例如,如果这个时间代表{@code 2007-12-03T10：30 + 02：00}并且指定的偏移量是{@code +03：00},那么此方法将返回{@code 2007-12-03T10： 30 + 03：00}
     * 。
     *  此方法返回具有相同{@code LocalDateTime}和指定的{@code ZoneOffset}的对象。不需要或不执行计算。
     * <p>
     *  要考虑偏移之间的差异,并调整时间字段,请使用{@link #withOffsetSameInstant}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param offset  the zone offset to change to, not null
     * @return an {@code OffsetDateTime} based on this date-time with the requested offset, not null
     */
    public OffsetDateTime withOffsetSameLocal(ZoneOffset offset) {
        return with(dateTime, offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified offset ensuring
     * that the result is at the same instant.
     * <p>
     * This method returns an object with the specified {@code ZoneOffset} and a {@code LocalDateTime}
     * adjusted by the difference between the two offsets.
     * This will result in the old and new objects representing the same instant.
     * This is useful for finding the local time in a different offset.
     * For example, if this time represents {@code 2007-12-03T10:30+02:00} and the offset specified is
     * {@code +03:00}, then this method will return {@code 2007-12-03T11:30+03:00}.
     * <p>
     * To change the offset without adjusting the local time use {@link #withOffsetSameLocal}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回带有指定偏移量的{@code OffsetDateTime}的副本,确保结果在同一时刻。
     * <p>
     * 此方法返回具有指定的{@code ZoneOffset}和{@code LocalDateTime}的对象,由两个偏移之间的差调整。这将导致旧对象和新对象表示相同的时刻。
     * 这对于在不同偏移中查找本地时间很有用。
     * 例如,如果这个时间代表{@code 2007-12-03T10：30 + 02：00}并且指定的偏移量是{@code +03：00},那么此方法将返回{@code 2007-12-03T11： 30 + 03：00}
     * 。
     * 这对于在不同偏移中查找本地时间很有用。
     * <p>
     *  要更改偏移而不调整本地时间,请使用{@link #withOffsetSameLocal}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param offset  the zone offset to change to, not null
     * @return an {@code OffsetDateTime} based on this date-time with the requested offset, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime withOffsetSameInstant(ZoneOffset offset) {
        if (offset.equals(this.offset)) {
            return this;
        }
        int difference = offset.getTotalSeconds() - this.offset.getTotalSeconds();
        LocalDateTime adjusted = dateTime.plusSeconds(difference);
        return new OffsetDateTime(adjusted, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the {@code LocalDateTime} part of this offset date-time.
     * <p>
     * This returns a {@code LocalDateTime} with the same year, month, day and time
     * as this date-time.
     *
     * <p>
     *  获取此偏移日期时间的{@code LocalDateTime}部分。
     * <p>
     *  这会返回与此日期时间相同的年份,月份,日期和时间的{@code LocalDateTime}。
     * 
     * 
     * @return the local date-time part of this date-time, not null
     */
    public LocalDateTime toLocalDateTime() {
        return dateTime;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the {@code LocalDate} part of this date-time.
     * <p>
     * This returns a {@code LocalDate} with the same year, month and day
     * as this date-time.
     *
     * <p>
     *  获取此日期时间的{@code LocalDate}部分。
     * <p>
     *  这会返回与此日期时间相同的年份,月份和日期的{@code LocalDate}。
     * 
     * 
     * @return the date part of this date-time, not null
     */
    public LocalDate toLocalDate() {
        return dateTime.toLocalDate();
    }

    /**
     * Gets the year field.
     * <p>
     * This method returns the primitive {@code int} value for the year.
     * <p>
     * The year returned by this method is proleptic as per {@code get(YEAR)}.
     * To obtain the year-of-era, use {@code get(YEAR_OF_ERA)}.
     *
     * <p>
     *  获取年份字段。
     * <p>
     *  此方法返回年份的原始{@code int}值。
     * <p>
     *  该方法返回的年份是{@code get(YEAR)}。要获取年代,请使用{@code get(YEAR_OF_ERA)}。
     * 
     * 
     * @return the year, from MIN_YEAR to MAX_YEAR
     */
    public int getYear() {
        return dateTime.getYear();
    }

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
        return dateTime.getMonthValue();
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
     * 此方法返回当月的枚举{@link Month}。这避免了对{@code int}值意味着什么的混淆。
     * 如果你需要访问原始的{@code int}值,那么枚举提供{@link Month#getValue()int value}。
     * 
     * 
     * @return the month-of-year, not null
     * @see #getMonthValue()
     */
    public Month getMonth() {
        return dateTime.getMonth();
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
        return dateTime.getDayOfMonth();
    }

    /**
     * Gets the day-of-year field.
     * <p>
     * This method returns the primitive {@code int} value for the day-of-year.
     *
     * <p>
     *  获取年份字段。
     * <p>
     *  此方法返回一年中的原始{@code int}值。
     * 
     * 
     * @return the day-of-year, from 1 to 365, or 366 in a leap year
     */
    public int getDayOfYear() {
        return dateTime.getDayOfYear();
    }

    /**
     * Gets the day-of-week field, which is an enum {@code DayOfWeek}.
     * <p>
     * This method returns the enum {@link java.time.DayOfWeek} for the day-of-week.
     * This avoids confusion as to what {@code int} values mean.
     * If you need access to the primitive {@code int} value then the enum
     * provides the {@link java.time.DayOfWeek#getValue() int value}.
     * <p>
     * Additional information can be obtained from the {@code DayOfWeek}.
     * This includes textual names of the values.
     *
     * <p>
     *  获取"day-of-week"字段,这是一个枚举{@code DayOfWeek}。
     * <p>
     *  此方法返回星期几的枚举{@link java.time.DayOfWeek}。这避免了对{@code int}值意味着什么的混淆。
     * 如果你需要访问原始的{@code int}值,那么枚举提供{@link java.time.DayOfWeek#getValue()int value}。
     * <p>
     *  其他信息可以从{@code DayOfWeek}获取。这包括值的文本名称。
     * 
     * 
     * @return the day-of-week, not null
     */
    public DayOfWeek getDayOfWeek() {
        return dateTime.getDayOfWeek();
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
        return dateTime.toLocalTime();
    }

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
        return dateTime.getHour();
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
        return dateTime.getMinute();
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
        return dateTime.getSecond();
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
        return dateTime.getNano();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns an adjusted copy of this date-time.
     * <p>
     * This returns an {@code OffsetDateTime}, based on this one, with the date-time adjusted.
     * The adjustment takes place using the specified adjuster strategy object.
     * Read the documentation of the adjuster to understand what adjustment will be made.
     * <p>
     * A simple adjuster might simply set the one of the fields, such as the year field.
     * A more complex adjuster might set the date to the last day of the month.
     * A selection of common adjustments is provided in {@link TemporalAdjuster}.
     * These include finding the "last day of the month" and "next Wednesday".
     * Key date-time classes also implement the {@code TemporalAdjuster} interface,
     * such as {@link Month} and {@link java.time.MonthDay MonthDay}.
     * The adjuster is responsible for handling special cases, such as the varying
     * lengths of month and leap years.
     * <p>
     * For example this code returns a date on the last day of July:
     * <pre>
     *  import static java.time.Month.*;
     *  import static java.time.temporal.Adjusters.*;
     *
     *  result = offsetDateTime.with(JULY).with(lastDayOfMonth());
     * </pre>
     * <p>
     * The classes {@link LocalDate}, {@link LocalTime} and {@link ZoneOffset} implement
     * {@code TemporalAdjuster}, thus this method can be used to change the date, time or offset:
     * <pre>
     *  result = offsetDateTime.with(date);
     *  result = offsetDateTime.with(time);
     *  result = offsetDateTime.with(offset);
     * </pre>
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalAdjuster#adjustInto(Temporal)} method on the
     * specified adjuster passing {@code this} as the argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期时间的调整副本。
     * <p>
     *  这会返回一个{@code OffsetDateTime},基于这一个,调整日期时间。使用指定的调整器策略对象进行调整。阅读调整器的文档以了解将要进行的调整。
     * <p>
     * 简单的调整器可以简单地设置一个字段,例如年份字段。更复杂的调整器可以将日期设置为该月的最后一天。 {@link TemporalAdjuster}中提供了一些常用调整选项。
     * 这些包括找到"月份的最后一天"和"下周三"。
     * 关键日期时间类还实现{@code TemporalAdjuster}接口,例如{@link Month}和{@link java.time.MonthDay MonthDay}。
     * 调整器负责处理特殊情况,例如月份和闰年的变化长度。
     * <p>
     *  例如,此代码返回7月最后一天的日期：
     * <pre>
     *  import static java.time.Month。*; import static java.time.temporal.Adjusters。*;
     * 
     *  result = offsetDateTime.with(JULY).with(lastDayOfMonth());
     * </pre>
     * <p>
     *  类{@link LocalDate},{@link LocalTime}和{@link ZoneOffset}实现{@code TemporalAdjuster},因此这个方法可以用来改变日期,时间或
     * 偏移量：。
     * <pre>
     *  result = offsetDateTime.with(date); result = offsetDateTime.with(time); result = offsetDateTime.with
     * (offset);。
     * </pre>
     * <p>
     *  此方法的结果是通过调用指定调整器的{@link TemporalAdjuster#adjustInto(Temporal)}方法传递{@code this}作为参数来获得的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param adjuster the adjuster to use, not null
     * @return an {@code OffsetDateTime} based on {@code this} with the adjustment made, not null
     * @throws DateTimeException if the adjustment cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetDateTime with(TemporalAdjuster adjuster) {
        // optimizations
        if (adjuster instanceof LocalDate || adjuster instanceof LocalTime || adjuster instanceof LocalDateTime) {
            return with(dateTime.with(adjuster), offset);
        } else if (adjuster instanceof Instant) {
            return ofInstant((Instant) adjuster, offset);
        } else if (adjuster instanceof ZoneOffset) {
            return with(dateTime, (ZoneOffset) adjuster);
        } else if (adjuster instanceof OffsetDateTime) {
            return (OffsetDateTime) adjuster;
        }
        return (OffsetDateTime) adjuster.adjustInto(this);
    }

    /**
     * Returns a copy of this date-time with the specified field set to a new value.
     * <p>
     * TThis returns an {@code OffsetDateTime}, based on this one, with the value
     * for the specified field changed.
     * This can be used to change any supported field, such as the year, month or day-of-month.
     * If it is not possible to set the value, because the field is not supported or for
     * some other reason, an exception is thrown.
     * <p>
     * In some cases, changing the specified field can cause the resulting date-time to become invalid,
     * such as changing the month from 31st January to February would make the day-of-month invalid.
     * In cases like this, the field is responsible for resolving the date. Typically it will choose
     * the previous valid date, which would be the last valid day of February in this example.
     * <p>
     * If the field is a {@link ChronoField} then the adjustment is implemented here.
     * <p>
     * The {@code INSTANT_SECONDS} field will return a date-time with the specified instant.
     * The offset and nano-of-second are unchanged.
     * If the new instant value is outside the valid range then a {@code DateTimeException} will be thrown.
     * <p>
     * The {@code OFFSET_SECONDS} field will return a date-time with the specified offset.
     * The local date-time is unaltered. If the new offset value is outside the valid range
     * then a {@code DateTimeException} will be thrown.
     * <p>
     * The other {@link #isSupported(TemporalField) supported fields} will behave as per
     * the matching method on {@link LocalDateTime#with(TemporalField, long) LocalDateTime}.
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
     *  返回此日期时间的副本,指定的字段设置为新值。
     * <p>
     * TThis返回一个{@code OffsetDateTime},基于这一个,指定字段的值已更改。这可以用于更改任何支持的字段,例如年,月或日。
     * 如果无法设置该值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  在某些情况下,更改指定字段可能导致生成日期时间变得无效,例如将月份从1月31日更改为2月会使日期无效。在这种情况下,字段负责解析日期。
     * 通常,它将选择上一个有效日期,这将是本示例中二月的最后一个有效日期。
     * <p>
     *  如果字段是{@link ChronoField},那么在此处执行调整。
     * <p>
     *  {@code INSTANT_SECONDS}字段将返回具有指定时刻的日期时间。偏移和纳秒是不变的。如果新的即时值超出有效范围,那么将抛出{@code DateTimeException}。
     * <p>
     *  {@code OFFSET_SECONDS}字段将返回具有指定偏移量的日期时间。本地日期时间未更改。如果新的偏移值在有效范围之外,那么将抛出{@code DateTimeException}。
     * <p>
     *  其他{@link #isSupported(TemporalField)支持的字段}将按照{@link LocalDateTime#with(TemporalField,long)LocalDateTime}
     * 上的匹配方法进行操作。
     * 在这种情况下,偏移量不是计算的一部分,并且不会改变。
     * <p>
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.adjustInto(Temporal,long)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * 在这种情况下,字段确定是否以及如何调整时刻。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param field  the field to set in the result, not null
     * @param newValue  the new value of the field in the result
     * @return an {@code OffsetDateTime} based on {@code this} with the specified field set, not null
     * @throws DateTimeException if the field cannot be set
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetDateTime with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            switch (f) {
                case INSTANT_SECONDS: return ofInstant(Instant.ofEpochSecond(newValue, getNano()), offset);
                case OFFSET_SECONDS: {
                    return with(dateTime, ZoneOffset.ofTotalSeconds(f.checkValidIntValue(newValue)));
                }
            }
            return with(dateTime.with(field, newValue), offset);
        }
        return field.adjustInto(this, newValue);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetDateTime} with the year altered.
     * The offset does not affect the calculation and will be the same in the result.
     * If the day-of-month is invalid for the year, it will be changed to the last valid day of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,并更改​​年份。偏移不影响计算,并且在结果中将是相同的。如果该年的日期无效,它将更改为该月的最后一个有效日期。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param year  the year to set in the result, from MIN_YEAR to MAX_YEAR
     * @return an {@code OffsetDateTime} based on this date-time with the requested year, not null
     * @throws DateTimeException if the year value is invalid
     */
    public OffsetDateTime withYear(int year) {
        return with(dateTime.withYear(year), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the month-of-year altered.
     * The offset does not affect the calculation and will be the same in the result.
     * If the day-of-month is invalid for the year, it will be changed to the last valid day of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中的年份已更改。偏移不影响计算,并且在结果中将是相同的。如果该年的日期无效,它将更改为该月的最后一个有效日期。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param month  the month-of-year to set in the result, from 1 (January) to 12 (December)
     * @return an {@code OffsetDateTime} based on this date-time with the requested month, not null
     * @throws DateTimeException if the month-of-year value is invalid
     */
    public OffsetDateTime withMonth(int month) {
        return with(dateTime.withMonth(month), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the day-of-month altered.
     * If the resulting {@code OffsetDateTime} is invalid, an exception is thrown.
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中的日期已更改。如果生成的{@code OffsetDateTime}无效,则抛出异常。偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param dayOfMonth  the day-of-month to set in the result, from 1 to 28-31
     * @return an {@code OffsetDateTime} based on this date-time with the requested day, not null
     * @throws DateTimeException if the day-of-month value is invalid,
     *  or if the day-of-month is invalid for the month-year
     */
    public OffsetDateTime withDayOfMonth(int dayOfMonth) {
        return with(dateTime.withDayOfMonth(dayOfMonth), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the day-of-year altered.
     * If the resulting {@code OffsetDateTime} is invalid, an exception is thrown.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此{@code OffsetDateTime}的副本,并更改​​年份。如果生成的{@code OffsetDateTime}无效,则抛出异常。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param dayOfYear  the day-of-year to set in the result, from 1 to 365-366
     * @return an {@code OffsetDateTime} based on this date with the requested day, not null
     * @throws DateTimeException if the day-of-year value is invalid,
     *  or if the day-of-year is invalid for the year
     */
    public OffsetDateTime withDayOfYear(int dayOfYear) {
        return with(dateTime.withDayOfYear(dayOfYear), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetDateTime} with the hour-of-day value altered.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中日期值的值已更改。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hour  the hour-of-day to set in the result, from 0 to 23
     * @return an {@code OffsetDateTime} based on this date-time with the requested hour, not null
     * @throws DateTimeException if the hour value is invalid
     */
    public OffsetDateTime withHour(int hour) {
        return with(dateTime.withHour(hour), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the minute-of-hour value altered.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中小时值的值已更改。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minute  the minute-of-hour to set in the result, from 0 to 59
     * @return an {@code OffsetDateTime} based on this date-time with the requested minute, not null
     * @throws DateTimeException if the minute value is invalid
     */
    public OffsetDateTime withMinute(int minute) {
        return with(dateTime.withMinute(minute), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the second-of-minute value altered.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中二分钟值已更改。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param second  the second-of-minute to set in the result, from 0 to 59
     * @return an {@code OffsetDateTime} based on this date-time with the requested second, not null
     * @throws DateTimeException if the second value is invalid
     */
    public OffsetDateTime withSecond(int second) {
        return with(dateTime.withSecond(second), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the nano-of-second value altered.
     * <p>
     * The offset does not affect the calculation and will be the same in the result.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的纳秒值已更改的副本。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanoOfSecond  the nano-of-second to set in the result, from 0 to 999,999,999
     * @return an {@code OffsetDateTime} based on this date-time with the requested nanosecond, not null
     * @throws DateTimeException if the nanos value is invalid
     */
    public OffsetDateTime withNano(int nanoOfSecond) {
        return with(dateTime.withNano(nanoOfSecond), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetDateTime} with the time truncated.
     * <p>
     * Truncation returns a copy of the original date-time with fields
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
     *  返回此{@code OffsetDateTime}的副本,并截断时间。
     * <p>
     *  截断返回原始日期时间的副本,其中字段小于指定的单位设置为零。例如,使用{@link ChronoUnit#MINUTES分钟}单位进行截断将会将秒数和毫微秒字段设置为零。
     * <p>
     * 单位必须有一个{@linkplain TemporalUnit#getDuration()duration},它分成标准日的长度,没有余数。
     * 这包括{@link ChronoUnit}和{@link ChronoUnit#DAYS DAYS}上提供的所有时间单位。其他单位抛出异常。
     * <p>
     *  偏移不影响计算,并且在结果中将是相同的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param unit  the unit to truncate to, not null
     * @return an {@code OffsetDateTime} based on this date-time with the time truncated, not null
     * @throws DateTimeException if unable to truncate
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     */
    public OffsetDateTime truncatedTo(TemporalUnit unit) {
        return with(dateTime.truncatedTo(unit), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date-time with the specified amount added.
     * <p>
     * This returns an {@code OffsetDateTime}, based on this one, with the specified amount added.
     * The amount is typically {@link Period} or {@link Duration} but may be
     * any other type implementing the {@link TemporalAmount} interface.
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
     *  返回此日期时间的副本,并添加指定的金额。
     * <p>
     *  这将返回一个{@code OffsetDateTime},基于此,使用指定的添加量。
     * 金额通常为{@link Period}或{@link Duration},但可以是实现{@link TemporalAmount}界面的任何其他类型。
     * <p>
     *  通过调用{@link TemporalAmount#addTo(Temporal)}将计算委托给金额对象。
     * 金额实现可以以任何希望的方式实现添加,但是它通常回调{@link #plus(long,TemporalUnit)}。请参阅金额实施的文档以确定是否可以成功添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount to add, not null
     * @return an {@code OffsetDateTime} based on this date-time with the addition made, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetDateTime plus(TemporalAmount amountToAdd) {
        return (OffsetDateTime) amountToAdd.addTo(this);
    }

    /**
     * Returns a copy of this date-time with the specified amount added.
     * <p>
     * This returns an {@code OffsetDateTime}, based on this one, with the amount
     * in terms of the unit added. If it is not possible to add the amount, because the
     * unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoUnit} then the addition is implemented by
     * {@link LocalDateTime#plus(long, TemporalUnit)}.
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
     *  返回此日期时间的副本,并添加指定的金额。
     * <p>
     *  这将返回一个{@code OffsetDateTime},根据这一个,与添加的单位的金额。如果无法添加该金额,因为该单元不受支持或出于其他原因,将抛出异常。
     * <p>
     * 如果字段是{@link ChronoUnit},则通过{@link LocalDateTime#plus(long,TemporalUnit)}实现添加。偏移量不是计算的一部分,在结果中不会改变。
     * <p>
     *  如果字段不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.addTo(Temporal,long)}传递{@code this}作为参数来获得此方法的结果。
     * 在这种情况下,单元确定是否以及如何执行添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount of the unit to add to the result, may be negative
     * @param unit  the unit of the amount to add, not null
     * @return an {@code OffsetDateTime} based on this date-time with the specified amount added, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetDateTime plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            return with(dateTime.plus(amountToAdd, unit), offset);
        }
        return unit.addTo(this, amountToAdd);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in years added.
     * <p>
     * This method adds the specified amount to the years field in three steps:
     * <ol>
     * <li>Add the input years to the year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2008-02-29 (leap year) plus one year would result in the
     * invalid date 2009-02-29 (standard year). Instead of returning an invalid
     * result, the last valid day of the month, 2009-02-28, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中包含指定的周期(以年为单位)。
     * <p>
     *  此方法通过三个步骤将指定的金额添加到"年份"字段中：
     * <ol>
     *  <li>将输入年份添加到年份字段</li> <li>检查结果日期是否无效</li> <li>如有必要,将日期调整为最后一天。</li >
     * </ol>
     * <p>
     *  例如,2008-02-29(闰年)加上一年将导致无效日期2009-02-29(标准年)。而不是返回无效的结果,而是选择该月的最后一个有效日期(2009-02-28)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param years  the years to add, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime plusYears(long years) {
        return with(dateTime.plusYears(years), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in months added.
     * <p>
     * This method adds the specified amount to the months field in three steps:
     * <ol>
     * <li>Add the input months to the month-of-year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2007-03-31 plus one month would result in the invalid date
     * 2007-04-31. Instead of returning an invalid result, the last valid day
     * of the month, 2007-04-30, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中包含指定的周期(以月为单位)。
     * <p>
     *  此方法通过三个步骤将指定的金额添加到months字段：
     * <ol>
     *  <li>将输入月份添加到年份字段</li> <li>检查结果日期是否无效</li> <li>将日期调整为最后一个有效日,如果必要</li>
     * </ol>
     * <p>
     * 例如,2007-03-31加一个月将导致无效日期2007-04-31。而不是返回无效结果,而是选择该月的最后一个有效日(2007-04-30)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param months  the months to add, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the months added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime plusMonths(long months) {
        return with(dateTime.plusMonths(months), offset);
    }

    /**
     * Returns a copy of this OffsetDateTime with the specified period in weeks added.
     * <p>
     * This method adds the specified amount in weeks to the days field incrementing
     * the month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2008-12-31 plus one week would result in the 2009-01-07.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此OffsetDateTime的副本,其中包含指定的周期(以星期为单位)。
     * <p>
     *  此方法将指定的金额(以周为单位)添加到"日期"字段,根据需要增加月份和年份字段,以确保结果保持有效。如果超过最大/最小年份,结果将无效。
     * <p>
     *  例如,2008-12-31加一周将导致2009-01-07。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param weeks  the weeks to add, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the weeks added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime plusWeeks(long weeks) {
        return with(dateTime.plusWeeks(weeks), offset);
    }

    /**
     * Returns a copy of this OffsetDateTime with the specified period in days added.
     * <p>
     * This method adds the specified amount to the days field incrementing the
     * month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2008-12-31 plus one day would result in the 2009-01-01.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的周期(以天为单位)返回此OffsetDateTime的副本。
     * <p>
     *  此方法将指定的金额添加到"日期"字段,根据需要增加月份和年份字段,以确保结果保持有效。如果超过最大/最小年份,结果将无效。
     * <p>
     *  例如,2008-12-31加一天将导致2009-01-01。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param days  the days to add, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the days added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime plusDays(long days) {
        return with(dateTime.plusDays(days), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in hours added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的周期(以小时为单位)返回此{@code OffsetDateTime}的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hours  the hours to add, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the hours added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime plusHours(long hours) {
        return with(dateTime.plusHours(hours), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in minutes added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的周期(以分钟为单位)返回此{@code OffsetDateTime}的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minutes  the minutes to add, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the minutes added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime plusMinutes(long minutes) {
        return with(dateTime.plusMinutes(minutes), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in seconds added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此{@code OffsetDateTime}的副本,其中指定的周期(以秒为单位)添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param seconds  the seconds to add, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the seconds added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime plusSeconds(long seconds) {
        return with(dateTime.plusSeconds(seconds), offset);
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in nanoseconds added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中包含指定的周期(以纳秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanos  the nanos to add, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the nanoseconds added, not null
     * @throws DateTimeException if the unit cannot be added to this type
     */
    public OffsetDateTime plusNanos(long nanos) {
        return with(dateTime.plusNanos(nanos), offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date-time with the specified amount subtracted.
     * <p>
     * This returns an {@code OffsetDateTime}, based on this one, with the specified amount subtracted.
     * The amount is typically {@link Period} or {@link Duration} but may be
     * any other type implementing the {@link TemporalAmount} interface.
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
     *  返回此日期时间的副本,并减去指定的金额。
     * <p>
     *  这将返回一个{@code OffsetDateTime},基于此,减去指定的金额。
     * 金额通常为{@link Period}或{@link Duration},但可以是实现{@link TemporalAmount}界面的任何其他类型。
     * <p>
     *  通过调用{@link TemporalAmount#subtractFrom(Temporal)}将计算委托给金额对象。
     * 金额实现可以以任何方式自由实现减法,但它通常回调{@link #minus(long,TemporalUnit)}。请参阅金额实施的文档,以确定是否可以成功扣除。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount to subtract, not null
     * @return an {@code OffsetDateTime} based on this date-time with the subtraction made, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetDateTime minus(TemporalAmount amountToSubtract) {
        return (OffsetDateTime) amountToSubtract.subtractFrom(this);
    }

    /**
     * Returns a copy of this date-time with the specified amount subtracted.
     * <p>
     * This returns an {@code OffsetDateTime}, based on this one, with the amount
     * in terms of the unit subtracted. If it is not possible to subtract the amount,
     * because the unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * This method is equivalent to {@link #plus(long, TemporalUnit)} with the amount negated.
     * See that method for a full description of how addition, and thus subtraction, works.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期时间的副本,并减去指定的金额。
     * <p>
     *  这将返回一个{@code OffsetDateTime},根据这一个,与减去单位的金额。如果不可能减去金额,因为该单元不受支持或由于某种其他原因,将抛出异常。
     * <p>
     * 此方法等效于{@link #plus(long,TemporalUnit)},其值为negated。请参阅该方法,了解如何添加和减少的工作原理的完整描述。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount of the unit to subtract from the result, may be negative
     * @param unit  the unit of the amount to subtract, not null
     * @return an {@code OffsetDateTime} based on this date-time with the specified amount subtracted, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public OffsetDateTime minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in years subtracted.
     * <p>
     * This method subtracts the specified amount from the years field in three steps:
     * <ol>
     * <li>Subtract the input years to the year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2008-02-29 (leap year) minus one year would result in the
     * invalid date 2009-02-29 (standard year). Instead of returning an invalid
     * result, the last valid day of the month, 2009-02-28, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中减去指定的周期。
     * <p>
     *  此方法通过三个步骤从年份字段中减去指定的金额：
     * <ol>
     *  <li>将输入的年份减去年份字段</li> <li>检查结果日期是否无效</li> <li>如有必要,将日期调整为最后一天。</li >
     * </ol>
     * <p>
     *  例如,2008-02-29(闰年)减去一年将导致无效日期2009-02-29(标准年)。而不是返回无效的结果,而是选择该月的最后一个有效日期(2009-02-28)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param years  the years to subtract, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime minusYears(long years) {
        return (years == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-years));
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in months subtracted.
     * <p>
     * This method subtracts the specified amount from the months field in three steps:
     * <ol>
     * <li>Subtract the input months to the month-of-year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2007-03-31 minus one month would result in the invalid date
     * 2007-04-31. Instead of returning an invalid result, the last valid day
     * of the month, 2007-04-30, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中包含指定的减去月数的期间。
     * <p>
     *  此方法从三个步骤中的月字段中减去指定的金额：
     * <ol>
     *  <li>将输入月份减去年月日字段</li> <li>检查结果日期是否无效</li> <li>将月份调整为最后一个有效日,如果必要</li>
     * </ol>
     * <p>
     *  例如,2007-03-31减去一个月将导致无效日期2007-04-31。而不是返回无效结果,而是选择该月的最后一个有效日(2007-04-30)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param months  the months to subtract, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the months subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime minusMonths(long months) {
        return (months == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-months));
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in weeks subtracted.
     * <p>
     * This method subtracts the specified amount in weeks from the days field decrementing
     * the month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2008-12-31 minus one week would result in the 2009-01-07.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此{@code OffsetDateTime}的副本,其中指定的周期减去星期。
     * <p>
     *  此方法从星期减去指定的金额,从字段减少月份和年份字段,以确保结果保持有效。如果超过最大/最小年份,结果将无效。
     * <p>
     *  例如,2008-12-31减去一周将导致2009-01-07。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param weeks  the weeks to subtract, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the weeks subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime minusWeeks(long weeks) {
        return (weeks == Long.MIN_VALUE ? plusWeeks(Long.MAX_VALUE).plusWeeks(1) : plusWeeks(-weeks));
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in days subtracted.
     * <p>
     * This method subtracts the specified amount from the days field incrementing the
     * month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2008-12-31 minus one day would result in the 2009-01-01.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中指定的周期减去天数。
     * <p>
     *  此方法从"天"字段中减去指定的金额,根据需要增加月份和年份字段,以确保结果保持有效。如果超过最大/最小年份,结果将无效。
     * <p>
     *  例如,2008-12-31减去一天将导致2009-01-01。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param days  the days to subtract, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the days subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime minusDays(long days) {
        return (days == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-days));
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in hours subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以减去的小时为单位返回此{@code OffsetDateTime}的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hours  the hours to subtract, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the hours subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime minusHours(long hours) {
        return (hours == Long.MIN_VALUE ? plusHours(Long.MAX_VALUE).plusHours(1) : plusHours(-hours));
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in minutes subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中指定的周期(以分钟为单位)减去。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minutes  the minutes to subtract, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the minutes subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime minusMinutes(long minutes) {
        return (minutes == Long.MIN_VALUE ? plusMinutes(Long.MAX_VALUE).plusMinutes(1) : plusMinutes(-minutes));
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in seconds subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,指定的周期(以秒为单位)减去。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param seconds  the seconds to subtract, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the seconds subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime minusSeconds(long seconds) {
        return (seconds == Long.MIN_VALUE ? plusSeconds(Long.MAX_VALUE).plusSeconds(1) : plusSeconds(-seconds));
    }

    /**
     * Returns a copy of this {@code OffsetDateTime} with the specified period in nanoseconds subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code OffsetDateTime}的副本,其中指定的周期减去纳秒。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanos  the nanos to subtract, may be negative
     * @return an {@code OffsetDateTime} based on this date-time with the nanoseconds subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public OffsetDateTime minusNanos(long nanos) {
        return (nanos == Long.MIN_VALUE ? plusNanos(Long.MAX_VALUE).plusNanos(1) : plusNanos(-nanos));
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this date-time using the specified query.
     * <p>
     * This queries this date-time using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此日期时间。
     * <p>
     *  这将使用指定的查询策略对象查询此日期时间。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
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
            return (R) getOffset();
        } else if (query == TemporalQueries.zoneId()) {
            return null;
        } else if (query == TemporalQueries.localDate()) {
            return (R) toLocalDate();
        } else if (query == TemporalQueries.localTime()) {
            return (R) toLocalTime();
        } else if (query == TemporalQueries.chronology()) {
            return (R) IsoChronology.INSTANCE;
        } else if (query == TemporalQueries.precision()) {
            return (R) NANOS;
        }
        // inline TemporalAccessor.super.query(query) as an optimization
        // non-JDK classes are not permitted to make this optimization
        return query.queryFrom(this);
    }

    /**
     * Adjusts the specified temporal object to have the same offset, date
     * and time as this object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the offset, date and time changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * three times, passing {@link ChronoField#EPOCH_DAY},
     * {@link ChronoField#NANO_OF_DAY} and {@link ChronoField#OFFSET_SECONDS} as the fields.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisOffsetDateTime.adjustInto(temporal);
     *   temporal = temporal.with(thisOffsetDateTime);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为具有与此对象相同的偏移量,日期和时间。
     * <p>
     *  这返回与具有偏移量,日期和时间更改为与此相同的输入具有相同observable类型的时间对象。
     * <p>
     *  该调整等同于使用{@link Temporal#with(TemporalField,long)}三次,将{@link ChronoField#EPOCH_DAY},{@link ChronoField#NANO_OF_DAY}
     * 和{@link ChronoField#OFFSET_SECONDS}作为字段。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等价的,但第二种方法是推荐temporal = thisOffsetDateTime.adjustInto(temporal); temporal = temporal.with(this
     * OffsetDateTime);。
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
        // OffsetDateTime is treated as three separate fields, not an instant
        // this produces the most consistent set of results overall
        // the offset is set after the date and time, as it is typically a small
        // tweak to the result, with ZonedDateTime frequently ignoring the offset
        return temporal
                .with(EPOCH_DAY, toLocalDate().toEpochDay())
                .with(NANO_OF_DAY, toLocalTime().toNanoOfDay())
                .with(OFFSET_SECONDS, getOffset().getTotalSeconds());
    }

    /**
     * Calculates the amount of time until another date-time in terms of the specified unit.
     * <p>
     * This calculates the amount of time between two {@code OffsetDateTime}
     * objects in terms of a single {@code TemporalUnit}.
     * The start and end points are {@code this} and the specified date-time.
     * The result will be negative if the end is before the start.
     * For example, the period in days between two date-times can be calculated
     * using {@code startDateTime.until(endDateTime, DAYS)}.
     * <p>
     * The {@code Temporal} passed to this method is converted to a
     * {@code OffsetDateTime} using {@link #from(TemporalAccessor)}.
     * If the offset differs between the two date-times, the specified
     * end date-time is normalized to have the same offset as this date-time.
     * <p>
     * The calculation returns a whole number, representing the number of
     * complete units between the two date-times.
     * For example, the period in months between 2012-06-15T00:00Z and 2012-08-14T23:59Z
     * will only be one month as it is one minute short of two months.
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
     * The units {@code NANOS}, {@code MICROS}, {@code MILLIS}, {@code SECONDS},
     * {@code MINUTES}, {@code HOURS} and {@code HALF_DAYS}, {@code DAYS},
     * {@code WEEKS}, {@code MONTHS}, {@code YEARS}, {@code DECADES},
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
     *  以指定的单位计算另一个日期时间之前的时间量。
     * <p>
     * 这将根据单个{@code TemporalUnit}计算两个{@code OffsetDateTime}对象之间的时间量。起点和终点是{@code this}和指定的日期时间。
     * 如果结束在开始之前,结果将为负。例如,两个日期时间之间的天数可以使用{@code startDateTime.until(endDateTime,DAYS)}计算。
     * <p>
     *  传递给此方法的{@code Temporal}将使用{@link #from(TemporalAccessor)}转换为{@code OffsetDateTime}。
     * 如果偏移在两个日期时间之间不同,则将指定的结束日期时间标准化为具有与此日期时间相同的偏移量。
     * <p>
     *  计算返回一个整数,表示两个日期时间之间的完整单位数。例如,2012-06-15T00：00Z和2012-08-14T23：59Z之间的月份只有一个月,因为它是两个月的一分钟。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是调用这个方法。第二个是使用{@link TemporalUnit#between(Temporal,Temporal)}：
     * <pre>
     *  //这两行是等价的amount = start.until(end,MONTHS); amount = MONTHS.between(start,end);
     * </pre>
     *  应该基于哪个使得代码更可读的选择。
     * <p>
     * 该计算在{@link ChronoUnit}的此方法中实现。
     *  {@code NANOS},{@code MICROS},{@code MILLIS},{@code SECONDS},{@code MINUTES},{@code HOURS}和{@code HALF_DAYS}
     * ,{@code DAYS}支持{@code WEEKS},{@code MONTHS},{@code YEARS},{@code DECADES},{@code CENTURIES},{@code MILLENNIA}
     * 和{@code ERAS}。
     * 该计算在{@link ChronoUnit}的此方法中实现。其他{@code ChronoUnit}值会抛出异常。
     * <p>
     *  如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.between(Temporal,Temporal)}传递{@code this}作为第一个参数和
     * 转换的输入时间为第二个参数。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param endExclusive  the end date, exclusive, which is converted to an {@code OffsetDateTime}, not null
     * @param unit  the unit to measure the amount in, not null
     * @return the amount of time between this date-time and the end date-time
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to an {@code OffsetDateTime}
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        OffsetDateTime end = OffsetDateTime.from(endExclusive);
        if (unit instanceof ChronoUnit) {
            end = end.withOffsetSameInstant(offset);
            return dateTime.until(end.dateTime, unit);
        }
        return unit.between(this, end);
    }

    /**
     * Formats this date-time using the specified formatter.
     * <p>
     * This date-time will be passed to the formatter to produce a string.
     *
     * <p>
     *  使用指定的格式化程序格式化此日期时间。
     * <p>
     *  此日期时间将传递到格式化程序以生成字符串。
     * 
     * 
     * @param formatter  the formatter to use, not null
     * @return the formatted date-time string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    public String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Combines this date-time with a time-zone to create a {@code ZonedDateTime}
     * ensuring that the result has the same instant.
     * <p>
     * This returns a {@code ZonedDateTime} formed from this date-time and the specified time-zone.
     * This conversion will ignore the visible local date-time and use the underlying instant instead.
     * This avoids any problems with local time-line gaps or overlaps.
     * The result might have different values for fields such as hour, minute an even day.
     * <p>
     * To attempt to retain the values of the fields, use {@link #atZoneSimilarLocal(ZoneId)}.
     * To use the offset as the zone ID, use {@link #toZonedDateTime()}.
     *
     * <p>
     *  将此日期时间与时区相结合,以创建{@code ZonedDateTime},确保结果具有相同的时间。
     * <p>
     *  这将返回从此日期时间和指定时区形成的{@code ZonedDateTime}。此转换将忽略可见的本地日期时间,并使用基础即时。这避免了本地时间线间隙或重叠的任何问题。
     * 结果可能具有不同的字段值,例如小时,分钟和偶数日。
     * <p>
     *  要尝试保留字段的值,请使用{@link #atZoneSimilarLocal(ZoneId)}。要使用偏移作为区域ID,请使用{@link #toZonedDateTime()}。
     * 
     * 
     * @param zone  the time-zone to use, not null
     * @return the zoned date-time formed from this date-time, not null
     */
    public ZonedDateTime atZoneSameInstant(ZoneId zone) {
        return ZonedDateTime.ofInstant(dateTime, offset, zone);
    }

    /**
     * Combines this date-time with a time-zone to create a {@code ZonedDateTime}
     * trying to keep the same local date and time.
     * <p>
     * This returns a {@code ZonedDateTime} formed from this date-time and the specified time-zone.
     * Where possible, the result will have the same local date-time as this object.
     * <p>
     * Time-zone rules, such as daylight savings, mean that not every time on the
     * local time-line exists. If the local date-time is in a gap or overlap according to
     * the rules then a resolver is used to determine the resultant local time and offset.
     * This method uses {@link ZonedDateTime#ofLocal(LocalDateTime, ZoneId, ZoneOffset)}
     * to retain the offset from this instance if possible.
     * <p>
     * Finer control over gaps and overlaps is available in two ways.
     * If you simply want to use the later offset at overlaps then call
     * {@link ZonedDateTime#withLaterOffsetAtOverlap()} immediately after this method.
     * <p>
     * To create a zoned date-time at the same instant irrespective of the local time-line,
     * use {@link #atZoneSameInstant(ZoneId)}.
     * To use the offset as the zone ID, use {@link #toZonedDateTime()}.
     *
     * <p>
     * 将此日期时间与时区相结合,以创建{@code ZonedDateTime},以保持相同的本地日期和时间。
     * <p>
     *  这将返回从此日期时间和指定时区形成的{@code ZonedDateTime}。在可能的情况下,结果将具有与此对象相同的本地日期时间。
     * <p>
     *  时区规则(例如夏令时)意味着不是每次都在本地时间线上存在。如果本地日期时间在根据规则的间隙或重叠中,则使用解析器来确定所得到的本地时间和偏移。
     * 此方法使用{@link ZonedDateTime#ofLocal(LocalDateTime,ZoneId,ZoneOffset)}尽可能保留此实例的偏移量。
     * <p>
     *  对间隙和重叠的更精细控制有两种方式。如果你只想在重叠使用稍后的偏移,然后立即调用{@link ZonedDateTime#withLaterOffsetAtOverlap()}。
     * <p>
     *  要在不考虑本地时间线的情况下在同一时刻创建分区日期时间,请使用{@link #atZoneSameInstant(ZoneId)}。
     * 要使用偏移作为区域ID,请使用{@link #toZonedDateTime()}。
     * 
     * 
     * @param zone  the time-zone to use, not null
     * @return the zoned date-time formed from this date and the earliest valid time for the zone, not null
     */
    public ZonedDateTime atZoneSimilarLocal(ZoneId zone) {
        return ZonedDateTime.ofLocal(dateTime, zone, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Converts this date-time to an {@code OffsetTime}.
     * <p>
     * This returns an offset time with the same local time and offset.
     *
     * <p>
     *  将此日期时间转换为{@code OffsetTime}。
     * <p>
     *  这将返回具有相同本地时间和偏移的偏移时间。
     * 
     * 
     * @return an OffsetTime representing the time and offset, not null
     */
    public OffsetTime toOffsetTime() {
        return OffsetTime.of(dateTime.toLocalTime(), offset);
    }

    /**
     * Converts this date-time to a {@code ZonedDateTime} using the offset as the zone ID.
     * <p>
     * This creates the simplest possible {@code ZonedDateTime} using the offset
     * as the zone ID.
     * <p>
     * To control the time-zone used, see {@link #atZoneSameInstant(ZoneId)} and
     * {@link #atZoneSimilarLocal(ZoneId)}.
     *
     * <p>
     *  使用偏移作为区域ID将此日期时间转换为{@code ZonedDateTime}。
     * <p>
     *  这创建了使用偏移作为区域ID的最简单的{@code ZonedDateTime}。
     * <p>
     *  要控制所使用的时区,请参阅{@link #atZoneSameInstant(ZoneId)}和{@link #atZoneSimilarLocal(ZoneId)}。
     * 
     * 
     * @return a zoned date-time representing the same local date-time and offset, not null
     */
    public ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.of(dateTime, offset);
    }

    /**
     * Converts this date-time to an {@code Instant}.
     * <p>
     * This returns an {@code Instant} representing the same point on the
     * time-line as this date-time.
     *
     * <p>
     * 将此日期时间转换为{@code Instant}。
     * <p>
     *  这会返回代表与此日期时间相同的时间线上的相同点的{@code Instant}。
     * 
     * 
     * @return an {@code Instant} representing the same instant, not null
     */
    public Instant toInstant() {
        return dateTime.toInstant(offset);
    }

    /**
     * Converts this date-time to the number of seconds from the epoch of 1970-01-01T00:00:00Z.
     * <p>
     * This allows this date-time to be converted to a value of the
     * {@link ChronoField#INSTANT_SECONDS epoch-seconds} field. This is primarily
     * intended for low-level conversions rather than general application usage.
     *
     * <p>
     *  将此日期时间转换为从1970-01-01T00：00：00Z的时期的秒数。
     * <p>
     *  这允许将此日期时间转换为{@link ChronoField#INSTANT_SECONDS epoch-seconds}字段的值。这主要用于低级别转换,而不是一般的应用程序使用。
     * 
     * 
     * @return the number of seconds from the epoch of 1970-01-01T00:00:00Z
     */
    public long toEpochSecond() {
        return dateTime.toEpochSecond(offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this {@code OffsetDateTime} to another date-time.
     * <p>
     * The comparison is based on the instant then on the local date-time.
     * It is "consistent with equals", as defined by {@link Comparable}.
     * <p>
     * For example, the following is the comparator order:
     * <ol>
     * <li>{@code 2008-12-03T10:30+01:00}</li>
     * <li>{@code 2008-12-03T11:00+01:00}</li>
     * <li>{@code 2008-12-03T12:00+02:00}</li>
     * <li>{@code 2008-12-03T11:30+01:00}</li>
     * <li>{@code 2008-12-03T12:00+01:00}</li>
     * <li>{@code 2008-12-03T12:30+01:00}</li>
     * </ol>
     * Values #2 and #3 represent the same instant on the time-line.
     * When two values represent the same instant, the local date-time is compared
     * to distinguish them. This step is needed to make the ordering
     * consistent with {@code equals()}.
     *
     * <p>
     *  将此{@code OffsetDateTime}与另一个日期时间进行比较。
     * <p>
     *  比较基于当时对本地日期时间的时刻。它是"与等号一致",由{@link Comparable}定义。
     * <p>
     *  例如,以下是比较器顺序：
     * <ol>
     *  <li> {@ code 2008-12-03T10：30 + 01：00} </li> <li> {@ code 2008-12-03T11：00 + 01：00} </li> 2008-12-03
     * T12：00 + 02：00} </li> <li> {@ code 2008-12-03T11：30 + 01：00} </li> <li> {@ code 2008-12-03T12： 00 + 01：00}
     *  </li> <li> {@ code 2008-12-03T12：30 + 01：00} </li>。
     * </ol>
     *  值#2和#3表示时间线上的相同时刻。当两个值表示相同的时刻时,比较本地日期时间以区分它们。需要此步骤使排序与{@code equals()}一致。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    public int compareTo(OffsetDateTime other) {
        int cmp = compareInstant(this, other);
        if (cmp == 0) {
            cmp = toLocalDateTime().compareTo(other.toLocalDateTime());
        }
        return cmp;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the instant of this date-time is after that of the specified date-time.
     * <p>
     * This method differs from the comparison in {@link #compareTo} and {@link #equals} in that it
     * only compares the instant of the date-time. This is equivalent to using
     * {@code dateTime1.toInstant().isAfter(dateTime2.toInstant());}.
     *
     * <p>
     *  检查此日期时间的时间是否晚于指定的日期时间。
     * <p>
     *  此方法与{@link #compareTo}和{@link #equals}中的比较不同,它仅比较日期时间的时间。这相当于使用{@code dateTime1.toInstant()。
     * isAfter(dateTime2.toInstant());}。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if this is after the instant of the specified date-time
     */
    public boolean isAfter(OffsetDateTime other) {
        long thisEpochSec = toEpochSecond();
        long otherEpochSec = other.toEpochSecond();
        return thisEpochSec > otherEpochSec ||
            (thisEpochSec == otherEpochSec && toLocalTime().getNano() > other.toLocalTime().getNano());
    }

    /**
     * Checks if the instant of this date-time is before that of the specified date-time.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the instant of the date-time. This is equivalent to using
     * {@code dateTime1.toInstant().isBefore(dateTime2.toInstant());}.
     *
     * <p>
     * 检查此日期时间的时间是否在指定日期时间的时间之前。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较日期时间的时间。这相当于使用{@code dateTime1.toInstant()。
     * isBefore(dateTime2.toInstant());}。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if this is before the instant of the specified date-time
     */
    public boolean isBefore(OffsetDateTime other) {
        long thisEpochSec = toEpochSecond();
        long otherEpochSec = other.toEpochSecond();
        return thisEpochSec < otherEpochSec ||
            (thisEpochSec == otherEpochSec && toLocalTime().getNano() < other.toLocalTime().getNano());
    }

    /**
     * Checks if the instant of this date-time is equal to that of the specified date-time.
     * <p>
     * This method differs from the comparison in {@link #compareTo} and {@link #equals}
     * in that it only compares the instant of the date-time. This is equivalent to using
     * {@code dateTime1.toInstant().equals(dateTime2.toInstant());}.
     *
     * <p>
     *  检查此日期时间的时间是否等于指定日期时间的时间。
     * <p>
     *  此方法与{@link #compareTo}和{@link #equals}中的比较不同,它仅比较日期时间的时间。这相当于使用{@code dateTime1.toInstant()。
     * equals(dateTime2.toInstant());}。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if the instant equals the instant of the specified date-time
     */
    public boolean isEqual(OffsetDateTime other) {
        return toEpochSecond() == other.toEpochSecond() &&
                toLocalTime().getNano() == other.toLocalTime().getNano();
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this date-time is equal to another date-time.
     * <p>
     * The comparison is based on the local date-time and the offset.
     * To compare for the same instant on the time-line, use {@link #isEqual}.
     * Only objects of type {@code OffsetDateTime} are compared, other types return false.
     *
     * <p>
     *  检查此日期时间是否等于另一个日期时间。
     * <p>
     *  比较基于本地日期时间和偏移量。要在时间线上的同一时刻进行比较,请使用{@link #isEqual}。只有{@code OffsetDateTime}类型的对象被比较,其他类型返回false。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other date-time
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OffsetDateTime) {
            OffsetDateTime other = (OffsetDateTime) obj;
            return dateTime.equals(other.dateTime) && offset.equals(other.offset);
        }
        return false;
    }

    /**
     * A hash code for this date-time.
     *
     * <p>
     *  此日期时间的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return dateTime.hashCode() ^ offset.hashCode();
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this date-time as a {@code String}, such as {@code 2007-12-03T10:15:30+01:00}.
     * <p>
     * The output will be one of the following ISO-8601 formats:
     * <ul>
     * <li>{@code uuuu-MM-dd'T'HH:mmXXXXX}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ssXXXXX}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSXXXXX}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSSXXXXX}</li>
     * <li>{@code uuuu-MM-dd'T'HH:mm:ss.SSSSSSSSSXXXXX}</li>
     * </ul>
     * The format used will be the shortest that outputs the full value of
     * the time where the omitted parts are implied to be zero.
     *
     * <p>
     *  将此日期时间输出为{@code String},例如{@code 2007-12-03T10：15：30 + 01：00}。
     * <p>
     *  输出将是以下ISO-8601格式之一：
     * <ul>
     *  <li> {@ code uuuu-MM-dd'T'HH：mmXXXXX} </li> <li> {@ code uuuu-MM-dd'T'HH：mm：ssXXXXX} </li> <li> @cod
     * e uuuu-MM-dd'T'HH：mm：ss.SSSXXXXX} </li> <li> {@ code uuuu-MM-dd'T'HH：mm：ss.SSSSSSXXXXX} </li> <li > {@ code uuuu-MM-dd'T'HH：mm：ss.SSSSSSSSSXXXXX}
     *  </li>。
     * </ul>
     * 
     * @return a string representation of this date-time, not null
     */
    @Override
    public String toString() {
        return dateTime.toString() + offset.toString();
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     *  使用的格式将是输出完全值的时间的最短,其中省略的部分被暗示为零。
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(10);  // identifies an OffsetDateTime
     *  // the <a href="../../serialized-form.html#java.time.LocalDateTime">datetime</a> excluding the one byte header
     *  // the <a href="../../serialized-form.html#java.time.ZoneOffset">offset</a> excluding the one byte header
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.OFFSET_DATE_TIME_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     * 使用<a href="../../serialized-form.html#java.time.Ser">专用序列化表单</a>写入对象。
     * 
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(ObjectOutput out) throws IOException {
        dateTime.writeExternal(out);
        offset.writeExternal(out);
    }

    static OffsetDateTime readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        LocalDateTime dateTime = LocalDateTime.readExternal(in);
        ZoneOffset offset = ZoneOffset.readExternal(in);
        return OffsetDateTime.of(dateTime, offset);
    }

}
