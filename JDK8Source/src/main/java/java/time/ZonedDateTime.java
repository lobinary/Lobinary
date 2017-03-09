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

import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.chrono.ChronoZonedDateTime;
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
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.List;
import java.util.Objects;

/**
 * A date-time with a time-zone in the ISO-8601 calendar system,
 * such as {@code 2007-12-03T10:15:30+01:00 Europe/Paris}.
 * <p>
 * {@code ZonedDateTime} is an immutable representation of a date-time with a time-zone.
 * This class stores all date and time fields, to a precision of nanoseconds,
 * and a time-zone, with a zone offset used to handle ambiguous local date-times.
 * For example, the value
 * "2nd October 2007 at 13:45.30.123456789 +02:00 in the Europe/Paris time-zone"
 * can be stored in a {@code ZonedDateTime}.
 * <p>
 * This class handles conversion from the local time-line of {@code LocalDateTime}
 * to the instant time-line of {@code Instant}.
 * The difference between the two time-lines is the offset from UTC/Greenwich,
 * represented by a {@code ZoneOffset}.
 * <p>
 * Converting between the two time-lines involves calculating the offset using the
 * {@link ZoneRules rules} accessed from the {@code ZoneId}.
 * Obtaining the offset for an instant is simple, as there is exactly one valid
 * offset for each instant. By contrast, obtaining the offset for a local date-time
 * is not straightforward. There are three cases:
 * <ul>
 * <li>Normal, with one valid offset. For the vast majority of the year, the normal
 *  case applies, where there is a single valid offset for the local date-time.</li>
 * <li>Gap, with zero valid offsets. This is when clocks jump forward typically
 *  due to the spring daylight savings change from "winter" to "summer".
 *  In a gap there are local date-time values with no valid offset.</li>
 * <li>Overlap, with two valid offsets. This is when clocks are set back typically
 *  due to the autumn daylight savings change from "summer" to "winter".
 *  In an overlap there are local date-time values with two valid offsets.</li>
 * </ul>
 * <p>
 * Any method that converts directly or implicitly from a local date-time to an
 * instant by obtaining the offset has the potential to be complicated.
 * <p>
 * For Gaps, the general strategy is that if the local date-time falls in the
 * middle of a Gap, then the resulting zoned date-time will have a local date-time
 * shifted forwards by the length of the Gap, resulting in a date-time in the later
 * offset, typically "summer" time.
 * <p>
 * For Overlaps, the general strategy is that if the local date-time falls in the
 * middle of an Overlap, then the previous offset will be retained. If there is no
 * previous offset, or the previous offset is invalid, then the earlier offset is
 * used, typically "summer" time.. Two additional methods,
 * {@link #withEarlierOffsetAtOverlap()} and {@link #withLaterOffsetAtOverlap()},
 * help manage the case of an overlap.
 * <p>
 * In terms of design, this class should be viewed primarily as the combination
 * of a {@code LocalDateTime} and a {@code ZoneId}. The {@code ZoneOffset} is
 * a vital, but secondary, piece of information, used to ensure that the class
 * represents an instant, especially during a daylight savings overlap.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code ZonedDateTime} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * A {@code ZonedDateTime} holds state equivalent to three separate objects,
 * a {@code LocalDateTime}, a {@code ZoneId} and the resolved {@code ZoneOffset}.
 * The offset and local date-time are used to define an instant when necessary.
 * The zone ID is used to obtain the rules for how and when the offset changes.
 * The offset cannot be freely set, as the zone controls which offsets are valid.
 * <p>
 * This class is immutable and thread-safe.
 *
 * <p>
 *  在ISO-8601日历系统中具有时区的日期时间,例如{@code 2007-12-03T10：15：30 + 01：00 Europe / Paris}。
 * <p>
 *  {@code ZonedDateTime}是具有时区的日期时间的不变的表示。此类存储所有日期和时间字段(精度为纳秒)和时区,带有用于处理模糊的本地日期时间的区域偏移。
 * 例如,值"2007年10月2日在13：45.30.123456789 +02：00在欧洲/巴黎时区"可以存储在{@code ZonedDateTime}中。
 * <p>
 *  此类处理从{@code LocalDateTime}的本地时间线到{@code Instant}的即时时间线的转换。
 * 两个时间线之间的差异是UTC / Greenwich的偏移量,由{@code ZoneOffset}表示。
 * <p>
 * 两条时间线之间的转换涉及使用从{@code ZoneId}访问的{@link ZoneRules规则}计算偏移量。为瞬时获得偏移是简单的,因为对于每个时刻恰好有一个有效偏移。
 * 相反,获得本地日期时间的偏移不是直接的。有三种情况：。
 * <ul>
 *  <li>正常,具有一个有效偏移量。对于绝大多数年份,正常情况适用,其中本地日期时间存在单个有效偏移量。</li> <li> Gap,零有效偏移量。
 * 这是当时钟向前跳跃通常由于春天夏令时从"冬天"改变为"夏天"。在间隙中存在没有有效偏移的本地日期时间值。</li> <li>重叠,具有两个有效偏移量。
 * 这是当时钟被放回,通常由于秋季夏令从"夏季"改变为"冬季"。在重叠中,存在具有两个有效偏移量的本地日期时间值。</li>。
 * </ul>
 * <p>
 *  通过获得偏移直接或隐含地从本地日期时间转换到瞬时的任何方法都有可能变得复杂。
 * <p>
 *  对于间隙,一般策略是,如果本地日期时间落在间隙的中间,则生成的分区日期时间将具有向前移动间隙长度的本地日期时间,导致日期 - 时间在后面的偏移,通常是"夏天"时间。
 * <p>
 * 对于重叠,一般策略是如果本地日期时间落在重叠的中间,则将保留先前的偏移。如果没有先前的偏移,或者先前的偏移无效,则使用较早的偏移,通常是"夏天"时间。
 * 另外两种方法{@link #withEarlierOffsetAtOverlap()}和{@link #withLaterOffsetAtOverlap帮助管理重叠的情况。
 * <p>
 *  在设计方面,这个类应该主要被视为{@code LocalDateTime}和{@code ZoneId}的组合。
 *  {@code ZoneOffset}是一个重要的,但次要的信息,用于确保类代表一个瞬间,特别是在夏令时重叠时。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code ZonedDateTime}的实例上使用
 * 身份敏感操作(包括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec {@code ZonedDateTime}拥有相当于三个单独对象的状态,一个{@code LocalDateTime},一个{@code ZoneId}和解析的{@code ZoneOffset}
 * 。
 * 偏移和本地日期时间用于在必要时定义时刻。区域ID用于获取偏移变化的方式和时间的规则。偏移不能自由设置,因为区域控制哪些偏移有效。
 * <p>
 * 这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class ZonedDateTime
        implements Temporal, ChronoZonedDateTime<LocalDate>, Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = -6260982410461394882L;

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
    /**
     * The time-zone.
     * <p>
     *  时区。
     * 
     */
    private final ZoneId zone;

    //-----------------------------------------------------------------------
    /**
     * Obtains the current date-time from the system clock in the default time-zone.
     * <p>
     * This will query the {@link Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current date-time.
     * The zone and offset will be set based on the time-zone in the clock.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区中从系统时钟获取当前日期时间。
     * <p>
     *  这将在默认时区中查询{@link Clock#systemDefaultZone()系统时钟}以获取当前日期时间。区域和偏移将根据时钟中的时区设置。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @return the current date-time using the system clock, not null
     */
    public static ZonedDateTime now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current date-time from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date-time.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * The offset will be calculated from the specified time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区中从系统时钟获取当前日期时间。
     * <p>
     *  这将查询{@link Clock#system(ZoneId)系统时钟}以获取当前日期时间。指定时区避免了对默认时区的依赖。将根据指定的时区计算偏移量。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current date-time using the system clock, not null
     */
    public static ZonedDateTime now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current date-time from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current date-time.
     * The zone and offset will be set based on the time-zone in the clock.
     * <p>
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前日期时间。
     * <p>
     *  这将查询指定的时钟以获取当前日期时间。区域和偏移将根据时钟中的时区设置。
     * <p>
     *  使用此方法允许使用替代时钟进行测试。可以使用{@link时钟依赖注入}来引入替代时钟。
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current date-time, not null
     */
    public static ZonedDateTime now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        final Instant now = clock.instant();  // called once
        return ofInstant(now, clock.getZone());
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZonedDateTime} from a local date and time.
     * <p>
     * This creates a zoned date-time matching the input local date and time as closely as possible.
     * Time-zone rules, such as daylight savings, mean that not every local date-time
     * is valid for the specified zone, thus the local date-time may be adjusted.
     * <p>
     * The local date time and first combined to form a local date-time.
     * The local date-time is then resolved to a single instant on the time-line.
     * This is achieved by finding a valid offset from UTC/Greenwich for the local
     * date-time as defined by the {@link ZoneRules rules} of the zone ID.
     *<p>
     * In most cases, there is only one valid offset for a local date-time.
     * In the case of an overlap, when clocks are set back, there are two valid offsets.
     * This method uses the earlier offset typically corresponding to "summer".
     * <p>
     * In the case of a gap, when clocks jump forward, there is no valid offset.
     * Instead, the local date-time is adjusted to be later by the length of the gap.
     * For a typical one hour daylight savings change, the local date-time will be
     * moved one hour later into the offset typically corresponding to "summer".
     *
     * <p>
     *  从本地日期和时间获取{@code ZonedDateTime}的实例。
     * <p>
     * 这将创建与输入本地日期和时间尽可能匹配的分区日期时间。时区规则(例如夏令时)意味着并非每个本地日期时间对于指定区域有效,因此可以调整本地日期时间。
     * <p>
     *  本地日期时间并首先组合形成本地日期时间。然后将本地日期时间解析到时​​间线上的单个时刻。
     * 这是通过从区域ID的{@link ZoneRules规则}定义的本地日期 - 时间找到与UTC /格林威治的有效偏移来实现的。
     * p>
     *  在大多数情况下,本地日期时间只有一个有效偏移量。在重叠的情况下,当时钟被回退时,存在两个有效偏移。该方法使用通常对应于"夏天"的较早偏移。
     * <p>
     *  在间隙的情况下,当时钟向前跳跃时,没有有效的偏移。相反,本地日期时间稍后被调整为间隙的长度。对于典型的一小时夏令时变化,本地日期时间将在一小时后移动到通常对应于"夏季"的偏移量。
     * 
     * 
     * @param date  the local date, not null
     * @param time  the local time, not null
     * @param zone  the time-zone, not null
     * @return the offset date-time, not null
     */
    public static ZonedDateTime of(LocalDate date, LocalTime time, ZoneId zone) {
        return of(LocalDateTime.of(date, time), zone);
    }

    /**
     * Obtains an instance of {@code ZonedDateTime} from a local date-time.
     * <p>
     * This creates a zoned date-time matching the input local date-time as closely as possible.
     * Time-zone rules, such as daylight savings, mean that not every local date-time
     * is valid for the specified zone, thus the local date-time may be adjusted.
     * <p>
     * The local date-time is resolved to a single instant on the time-line.
     * This is achieved by finding a valid offset from UTC/Greenwich for the local
     * date-time as defined by the {@link ZoneRules rules} of the zone ID.
     *<p>
     * In most cases, there is only one valid offset for a local date-time.
     * In the case of an overlap, when clocks are set back, there are two valid offsets.
     * This method uses the earlier offset typically corresponding to "summer".
     * <p>
     * In the case of a gap, when clocks jump forward, there is no valid offset.
     * Instead, the local date-time is adjusted to be later by the length of the gap.
     * For a typical one hour daylight savings change, the local date-time will be
     * moved one hour later into the offset typically corresponding to "summer".
     *
     * <p>
     *  从本地日期时间获取{@code ZonedDateTime}的实例。
     * <p>
     *  这将创建与输入本地日期时间尽可能匹配的分区日期时间。时区规则(例如夏令时)意味着并非每个本地日期时间对于指定区域有效,因此可以调整本地日期时间。
     * <p>
     * 本地日期时间在时间​​线上被解析为单个时刻。这是通过从区域ID的{@link ZoneRules规则}定义的本地日期 - 时间找到与UTC /格林威治的有效偏移来实现的。
     * p>
     *  在大多数情况下,本地日期时间只有一个有效偏移量。在重叠的情况下,当时钟被回退时,存在两个有效偏移。该方法使用通常对应于"夏天"的较早偏移。
     * <p>
     *  在间隙的情况下,当时钟向前跳跃时,没有有效的偏移。相反,本地日期时间稍后被调整为间隙的长度。对于典型的一小时夏令时变化,本地日期时间将在一小时后移动到通常对应于"夏季"的偏移量。
     * 
     * 
     * @param localDateTime  the local date-time, not null
     * @param zone  the time-zone, not null
     * @return the zoned date-time, not null
     */
    public static ZonedDateTime of(LocalDateTime localDateTime, ZoneId zone) {
        return ofLocal(localDateTime, zone, null);
    }

    /**
     * Obtains an instance of {@code ZonedDateTime} from a year, month, day,
     * hour, minute, second, nanosecond and time-zone.
     * <p>
     * This creates a zoned date-time matching the local date-time of the seven
     * specified fields as closely as possible.
     * Time-zone rules, such as daylight savings, mean that not every local date-time
     * is valid for the specified zone, thus the local date-time may be adjusted.
     * <p>
     * The local date-time is resolved to a single instant on the time-line.
     * This is achieved by finding a valid offset from UTC/Greenwich for the local
     * date-time as defined by the {@link ZoneRules rules} of the zone ID.
     *<p>
     * In most cases, there is only one valid offset for a local date-time.
     * In the case of an overlap, when clocks are set back, there are two valid offsets.
     * This method uses the earlier offset typically corresponding to "summer".
     * <p>
     * In the case of a gap, when clocks jump forward, there is no valid offset.
     * Instead, the local date-time is adjusted to be later by the length of the gap.
     * For a typical one hour daylight savings change, the local date-time will be
     * moved one hour later into the offset typically corresponding to "summer".
     * <p>
     * This method exists primarily for writing test cases.
     * Non test-code will typically use other methods to create an offset time.
     * {@code LocalDateTime} has five additional convenience variants of the
     * equivalent factory method taking fewer arguments.
     * They are not provided here to reduce the footprint of the API.
     *
     * <p>
     *  从年,月,日,小时,分钟,秒,纳秒和时区获取{@code ZonedDateTime}的实例。
     * <p>
     *  这将创建与7个指定字段的本地日期时间尽可能匹配的分区日期时间。时区规则(例如夏令时)意味着并非每个本地日期时间对于指定区域有效,因此可以调整本地日期时间。
     * <p>
     *  本地日期时间在时间​​线上被解析为单个时刻。这是通过从区域ID的{@link ZoneRules规则}定义的本地日期 - 时间找到与UTC /格林威治的有效偏移来实现的。
     * p>
     * 在大多数情况下,本地日期时间只有一个有效偏移量。在重叠的情况下,当时钟被回退时,存在两个有效偏移。该方法使用通常对应于"夏天"的较早偏移。
     * <p>
     *  在间隙的情况下,当时钟向前跳跃时,没有有效的偏移。相反,本地日期时间稍后被调整为间隙的长度。对于典型的一小时夏令时变化,本地日期时间将在一小时后移动到通常对应于"夏季"的偏移量。
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
     * @param zone  the time-zone, not null
     * @return the offset date-time, not null
     * @throws DateTimeException if the value of any field is out of range, or
     *  if the day-of-month is invalid for the month-year
     */
    public static ZonedDateTime of(
            int year, int month, int dayOfMonth,
            int hour, int minute, int second, int nanoOfSecond, ZoneId zone) {
        LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second, nanoOfSecond);
        return ofLocal(dt, zone, null);
    }

    /**
     * Obtains an instance of {@code ZonedDateTime} from a local date-time
     * using the preferred offset if possible.
     * <p>
     * The local date-time is resolved to a single instant on the time-line.
     * This is achieved by finding a valid offset from UTC/Greenwich for the local
     * date-time as defined by the {@link ZoneRules rules} of the zone ID.
     *<p>
     * In most cases, there is only one valid offset for a local date-time.
     * In the case of an overlap, where clocks are set back, there are two valid offsets.
     * If the preferred offset is one of the valid offsets then it is used.
     * Otherwise the earlier valid offset is used, typically corresponding to "summer".
     * <p>
     * In the case of a gap, where clocks jump forward, there is no valid offset.
     * Instead, the local date-time is adjusted to be later by the length of the gap.
     * For a typical one hour daylight savings change, the local date-time will be
     * moved one hour later into the offset typically corresponding to "summer".
     *
     * <p>
     *  从本地日期时间使用首选偏移量(如果可能)获取{@code ZonedDateTime}的实例。
     * <p>
     *  本地日期时间在时间​​线上被解析为单个时刻。这是通过从区域ID的{@link ZoneRules规则}定义的本地日期 - 时间找到与UTC /格林威治的有效偏移来实现的。
     * p>
     *  在大多数情况下,本地日期时间只有一个有效偏移量。在重叠的情况下,其中时钟被回退,存在两个有效偏移。如果优选偏移是有效偏移之一,则使用它。否则,使用较早的有效偏移,通常对应于"夏季"。
     * <p>
     * 在间隙的情况下,其中时钟向前跳跃,没有有效的偏移。相反,本地日期时间稍后被调整为间隙的长度。对于典型的一小时夏令时变化,本地日期时间将在一小时后移动到通常对应于"夏季"的偏移量。
     * 
     * 
     * @param localDateTime  the local date-time, not null
     * @param zone  the time-zone, not null
     * @param preferredOffset  the zone offset, null if no preference
     * @return the zoned date-time, not null
     */
    public static ZonedDateTime ofLocal(LocalDateTime localDateTime, ZoneId zone, ZoneOffset preferredOffset) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zone, "zone");
        if (zone instanceof ZoneOffset) {
            return new ZonedDateTime(localDateTime, (ZoneOffset) zone, zone);
        }
        ZoneRules rules = zone.getRules();
        List<ZoneOffset> validOffsets = rules.getValidOffsets(localDateTime);
        ZoneOffset offset;
        if (validOffsets.size() == 1) {
            offset = validOffsets.get(0);
        } else if (validOffsets.size() == 0) {
            ZoneOffsetTransition trans = rules.getTransition(localDateTime);
            localDateTime = localDateTime.plusSeconds(trans.getDuration().getSeconds());
            offset = trans.getOffsetAfter();
        } else {
            if (preferredOffset != null && validOffsets.contains(preferredOffset)) {
                offset = preferredOffset;
            } else {
                offset = Objects.requireNonNull(validOffsets.get(0), "offset");  // protect against bad ZoneRules
            }
        }
        return new ZonedDateTime(localDateTime, offset, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZonedDateTime} from an {@code Instant}.
     * <p>
     * This creates a zoned date-time with the same instant as that specified.
     * Calling {@link #toInstant()} will return an instant equal to the one used here.
     * <p>
     * Converting an instant to a zoned date-time is simple as there is only one valid
     * offset for each instant.
     *
     * <p>
     *  从{@code Instant}中获取{@code ZonedDateTime}的实例。
     * <p>
     *  这将创建一个与指定的时间相同的分区日期时间。调用{@link #toInstant()}将返回一个等于此处使用的时刻。
     * <p>
     *  将即时转换为分区日期时间很简单,因为每个时刻只有一个有效偏移量。
     * 
     * 
     * @param instant  the instant to create the date-time from, not null
     * @param zone  the time-zone, not null
     * @return the zoned date-time, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    public static ZonedDateTime ofInstant(Instant instant, ZoneId zone) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zone, "zone");
        return create(instant.getEpochSecond(), instant.getNano(), zone);
    }

    /**
     * Obtains an instance of {@code ZonedDateTime} from the instant formed by combining
     * the local date-time and offset.
     * <p>
     * This creates a zoned date-time by {@link LocalDateTime#toInstant(ZoneOffset) combining}
     * the {@code LocalDateTime} and {@code ZoneOffset}.
     * This combination uniquely specifies an instant without ambiguity.
     * <p>
     * Converting an instant to a zoned date-time is simple as there is only one valid
     * offset for each instant. If the valid offset is different to the offset specified,
     * the the date-time and offset of the zoned date-time will differ from those specified.
     * <p>
     * If the {@code ZoneId} to be used is a {@code ZoneOffset}, this method is equivalent
     * to {@link #of(LocalDateTime, ZoneId)}.
     *
     * <p>
     *  从通过组合本地日期时间和偏移形成的时刻获取{@code ZonedDateTime}的实例。
     * <p>
     *  这将通过{@link LocalDateTime#toInstant(ZoneOffset)combining} {@code LocalDateTime}和{@code ZoneOffset}创建一
     * 个分区的日期时间。
     * 该组合唯一地指定不含歧义的时刻。
     * <p>
     *  将即时转换为分区日期时间很简单,因为每个时刻只有一个有效偏移量。如果有效偏移量与指定的偏移量不同,则分区日期时间的日期时间和偏移量将与指定的日期时间和偏移量不同。
     * <p>
     *  如果要使用的{@code ZoneId}是{@code ZoneOffset},则此方法等效于{@link #of(LocalDateTime,ZoneId)}。
     * 
     * 
     * @param localDateTime  the local date-time, not null
     * @param offset  the zone offset, not null
     * @param zone  the time-zone, not null
     * @return the zoned date-time, not null
     */
    public static ZonedDateTime ofInstant(LocalDateTime localDateTime, ZoneOffset offset, ZoneId zone) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(offset, "offset");
        Objects.requireNonNull(zone, "zone");
        if (zone.getRules().isValidOffset(localDateTime, offset)) {
            return new ZonedDateTime(localDateTime, offset, zone);
        }
        return create(localDateTime.toEpochSecond(offset), localDateTime.getNano(), zone);
    }

    /**
     * Obtains an instance of {@code ZonedDateTime} using seconds from the
     * epoch of 1970-01-01T00:00:00Z.
     *
     * <p>
     *  从1970-01-01T00：00：00Z的时代使用秒来获取{@code ZonedDateTime}的实例。
     * 
     * 
     * @param epochSecond  the number of seconds from the epoch of 1970-01-01T00:00:00Z
     * @param nanoOfSecond  the nanosecond within the second, from 0 to 999,999,999
     * @param zone  the time-zone, not null
     * @return the zoned date-time, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    private static ZonedDateTime create(long epochSecond, int nanoOfSecond, ZoneId zone) {
        ZoneRules rules = zone.getRules();
        Instant instant = Instant.ofEpochSecond(epochSecond, nanoOfSecond);  // TODO: rules should be queryable by epochSeconds
        ZoneOffset offset = rules.getOffset(instant);
        LocalDateTime ldt = LocalDateTime.ofEpochSecond(epochSecond, nanoOfSecond, offset);
        return new ZonedDateTime(ldt, offset, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZonedDateTime} strictly validating the
     * combination of local date-time, offset and zone ID.
     * <p>
     * This creates a zoned date-time ensuring that the offset is valid for the
     * local date-time according to the rules of the specified zone.
     * If the offset is invalid, an exception is thrown.
     *
     * <p>
     * 获取{@code ZonedDateTime}的实例,严格验证本地日期时间,偏移量和区域ID的组合。
     * <p>
     *  这将创建一个分区日期时间,以确保根据指定区域的规则,偏移量对于本地日期时间有效。如果偏移量无效,则抛出异常。
     * 
     * 
     * @param localDateTime  the local date-time, not null
     * @param offset  the zone offset, not null
     * @param zone  the time-zone, not null
     * @return the zoned date-time, not null
     */
    public static ZonedDateTime ofStrict(LocalDateTime localDateTime, ZoneOffset offset, ZoneId zone) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(offset, "offset");
        Objects.requireNonNull(zone, "zone");
        ZoneRules rules = zone.getRules();
        if (rules.isValidOffset(localDateTime, offset) == false) {
            ZoneOffsetTransition trans = rules.getTransition(localDateTime);
            if (trans != null && trans.isGap()) {
                // error message says daylight savings for simplicity
                // even though there are other kinds of gaps
                throw new DateTimeException("LocalDateTime '" + localDateTime +
                        "' does not exist in zone '" + zone +
                        "' due to a gap in the local time-line, typically caused by daylight savings");
            }
            throw new DateTimeException("ZoneOffset '" + offset + "' is not valid for LocalDateTime '" +
                    localDateTime + "' in zone '" + zone + "'");
        }
        return new ZonedDateTime(localDateTime, offset, zone);
    }

    /**
     * Obtains an instance of {@code ZonedDateTime} leniently, for advanced use cases,
     * allowing any combination of local date-time, offset and zone ID.
     * <p>
     * This creates a zoned date-time with no checks other than no nulls.
     * This means that the resulting zoned date-time may have an offset that is in conflict
     * with the zone ID.
     * <p>
     * This method is intended for advanced use cases.
     * For example, consider the case where a zoned date-time with valid fields is created
     * and then stored in a database or serialization-based store. At some later point,
     * the object is then re-loaded. However, between those points in time, the government
     * that defined the time-zone has changed the rules, such that the originally stored
     * local date-time now does not occur. This method can be used to create the object
     * in an "invalid" state, despite the change in rules.
     *
     * <p>
     *  对于高级用例,宽松地获取{@code ZonedDateTime}的实例,允许本地日期时间,偏移量和区域ID的任意组合。
     * <p>
     *  这将创建一个分区日期时间,没有任何检查,除非没有null。这意味着所产生的分区日期时间可能具有与区域ID冲突的偏移。
     * <p>
     *  此方法适用于高级用例。例如,考虑创建具有有效字段的分区日期时间,然后将其存储在数据库或基于序列化的存储中的情况。在稍后的某点,然后重新加载对象。
     * 然而,在这些时间点之间,定义时区的政府已经改变了规则,使得原来存储的本地日期时间现在不发生。此方法可用于创建处于"无效"状态的对象,尽管规则发生更改。
     * 
     * 
     * @param localDateTime  the local date-time, not null
     * @param offset  the zone offset, not null
     * @param zone  the time-zone, not null
     * @return the zoned date-time, not null
     */
    private static ZonedDateTime ofLenient(LocalDateTime localDateTime, ZoneOffset offset, ZoneId zone) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(offset, "offset");
        Objects.requireNonNull(zone, "zone");
        if (zone instanceof ZoneOffset && offset.equals(zone) == false) {
            throw new IllegalArgumentException("ZoneId must match ZoneOffset");
        }
        return new ZonedDateTime(localDateTime, offset, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZonedDateTime} from a temporal object.
     * <p>
     * This obtains a zoned date-time based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ZonedDateTime}.
     * <p>
     * The conversion will first obtain a {@code ZoneId} from the temporal object,
     * falling back to a {@code ZoneOffset} if necessary. It will then try to obtain
     * an {@code Instant}, falling back to a {@code LocalDateTime} if necessary.
     * The result will be either the combination of {@code ZoneId} or {@code ZoneOffset}
     * with {@code Instant} or {@code LocalDateTime}.
     * Implementations are permitted to perform optimizations such as accessing
     * those fields that are equivalent to the relevant objects.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code ZonedDateTime::from}.
     *
     * <p>
     *  从临时对象获取{@code ZonedDateTime}的实例。
     * <p>
     *  这基于指定的时间获得分区日期时间。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂会将其转换为{@code ZonedDateTime}的实例。
     * <p>
     * 转换将首先从临时对象获取{@code ZoneId},如果必要,则返回到{@code ZoneOffset}。
     * 然后,系统会尝试获取{@code Instant},如有必要,会返回到{@code LocalDateTime}。
     * 结果将是{@code ZoneId}或{@code ZoneOffset}与{@code Instant}或{@code LocalDateTime}的组合。
     * 实现允许执行优化,例如访问等价于相关对象的那些字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许通过方法引用{@code ZonedDateTime :: from}在查询中使用它。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the zoned date-time, not null
     * @throws DateTimeException if unable to convert to an {@code ZonedDateTime}
     */
    public static ZonedDateTime from(TemporalAccessor temporal) {
        if (temporal instanceof ZonedDateTime) {
            return (ZonedDateTime) temporal;
        }
        try {
            ZoneId zone = ZoneId.from(temporal);
            if (temporal.isSupported(INSTANT_SECONDS)) {
                long epochSecond = temporal.getLong(INSTANT_SECONDS);
                int nanoOfSecond = temporal.get(NANO_OF_SECOND);
                return create(epochSecond, nanoOfSecond, zone);
            } else {
                LocalDate date = LocalDate.from(temporal);
                LocalTime time = LocalTime.from(temporal);
                return of(date, time, zone);
            }
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain ZonedDateTime from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZonedDateTime} from a text string such as
     * {@code 2007-12-03T10:15:30+01:00[Europe/Paris]}.
     * <p>
     * The string must represent a valid date-time and is parsed using
     * {@link java.time.format.DateTimeFormatter#ISO_ZONED_DATE_TIME}.
     *
     * <p>
     *  从文本字符串(例如{@code 2007-12-03T10：15：30 + 01：00 [Europe / Paris]})获取{@code ZonedDateTime}的实例。
     * <p>
     *  该字符串必须表示有效的日期时间,并使用{@link java.time.format.DateTimeFormatter#ISO_ZONED_DATE_TIME}进行解析。
     * 
     * 
     * @param text  the text to parse such as "2007-12-03T10:15:30+01:00[Europe/Paris]", not null
     * @return the parsed zoned date-time, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static ZonedDateTime parse(CharSequence text) {
        return parse(text, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    /**
     * Obtains an instance of {@code ZonedDateTime} from a text string using a specific formatter.
     * <p>
     * The text is parsed using the formatter, returning a date-time.
     *
     * <p>
     *  使用特定格式化程序从文本字符串获取{@code ZonedDateTime}的实例。
     * <p>
     *  使用格式化程序解析文本,返回日期时间。
     * 
     * 
     * @param text  the text to parse, not null
     * @param formatter  the formatter to use, not null
     * @return the parsed zoned date-time, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static ZonedDateTime parse(CharSequence text, DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, ZonedDateTime::from);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * <p>
     *  构造函数。
     * 
     * 
     * @param dateTime  the date-time, validated as not null
     * @param offset  the zone offset, validated as not null
     * @param zone  the time-zone, validated as not null
     */
    private ZonedDateTime(LocalDateTime dateTime, ZoneOffset offset, ZoneId zone) {
        this.dateTime = dateTime;
        this.offset = offset;
        this.zone = zone;
    }

    /**
     * Resolves the new local date-time using this zone ID, retaining the offset if possible.
     *
     * <p>
     *  使用此区域ID解析新的本地日期时间,如果可能,保留偏移。
     * 
     * 
     * @param newDateTime  the new local date-time, not null
     * @return the zoned date-time, not null
     */
    private ZonedDateTime resolveLocal(LocalDateTime newDateTime) {
        return ofLocal(newDateTime, zone, offset);
    }

    /**
     * Resolves the new local date-time using the offset to identify the instant.
     *
     * <p>
     *  使用偏移来解析新的本地日期时间以标识时刻。
     * 
     * 
     * @param newDateTime  the new local date-time, not null
     * @return the zoned date-time, not null
     */
    private ZonedDateTime resolveInstant(LocalDateTime newDateTime) {
        return ofInstant(newDateTime, offset, zone);
    }

    /**
     * Resolves the offset into this zoned date-time for the with methods.
     * <p>
     * This typically ignores the offset, unless it can be used to switch offset in a DST overlap.
     *
     * <p>
     *  解决方法的分区日期时间的偏移量。
     * <p>
     *  这通常忽略偏移,除非它可以用于在DST重叠中切换偏移。
     * 
     * 
     * @param offset  the offset, not null
     * @return the zoned date-time, not null
     */
    private ZonedDateTime resolveOffset(ZoneOffset offset) {
        if (offset.equals(this.offset) == false && zone.getRules().isValidOffset(dateTime, offset)) {
            return new ZonedDateTime(dateTime, offset, zone);
        }
        return this;
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
     * 这将检查是否可以查询指定字段的此日期时间。
     * 如果为false,则调用{@link #range(TemporalField)范围},{@link #get(TemporalField)get}和{@link #with(TemporalField,long)}
     * 方法将抛出异常。
     * 这将检查是否可以查询指定字段的此日期时间。
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
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
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
     * 检查是否支持指定的单元。
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
        return ChronoZonedDateTime.super.isSupported(unit);
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
     *  范围对象表示字段的最小和最大有效值。此日期时间用于提高返回范围的精度。如果不可能返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     * 如果字段是{@link ChronoField},则在此执行查询。 {@link #isSupported(TemporalField)supported fields}会传回适当的范围执行个体。
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
     *  如果字段是{@link ChronoField},则在此执行查询。
     * 根据此日期时间,{@link #isSupported(TemporalField)支持的字段}将返回有效的值,{@code NANO_OF_DAY},{@code MICRO_OF_DAY},{@code EPOCH_DAY}
     * ,{@code PROLEPTIC_MONTH}和{@代码INSTANT_SECONDS},它们太大,无法适应{@code int}并抛出{@code DateTimeException}。
     *  如果字段是{@link ChronoField},则在此执行查询。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得此
     * 方法的结果。
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
    @Override  // override for Javadoc and performance
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
        return ChronoZonedDateTime.super.get(field);
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
    @Override
    public ZoneOffset getOffset() {
        return offset;
    }

    /**
     * Returns a copy of this date-time changing the zone offset to the
     * earlier of the two valid offsets at a local time-line overlap.
     * <p>
     * This method only has any effect when the local time-line overlaps, such as
     * at an autumn daylight savings cutover. In this scenario, there are two
     * valid offsets for the local date-time. Calling this method will return
     * a zoned date-time with the earlier of the two selected.
     * <p>
     * If this method is called when it is not an overlap, {@code this}
     * is returned.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期时间的副本,将区域偏移更改为本地时间线重叠处的两个有效偏移量中的较早者。
     * <p>
     * 该方法仅在本地时间线重叠时(例如在秋季日光节约割接处)具有任何效果。在这种情况下,本地日期时间有两个有效偏移量。调用此方法将返回一个分区日期时间与所选的两个较早的日期时间。
     * <p>
     *  如果此方法在不重叠时调用,则返回{@code this}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return a {@code ZonedDateTime} based on this date-time with the earlier offset, not null
     */
    @Override
    public ZonedDateTime withEarlierOffsetAtOverlap() {
        ZoneOffsetTransition trans = getZone().getRules().getTransition(dateTime);
        if (trans != null && trans.isOverlap()) {
            ZoneOffset earlierOffset = trans.getOffsetBefore();
            if (earlierOffset.equals(offset) == false) {
                return new ZonedDateTime(dateTime, earlierOffset, zone);
            }
        }
        return this;
    }

    /**
     * Returns a copy of this date-time changing the zone offset to the
     * later of the two valid offsets at a local time-line overlap.
     * <p>
     * This method only has any effect when the local time-line overlaps, such as
     * at an autumn daylight savings cutover. In this scenario, there are two
     * valid offsets for the local date-time. Calling this method will return
     * a zoned date-time with the later of the two selected.
     * <p>
     * If this method is called when it is not an overlap, {@code this}
     * is returned.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期时间的副本,将区域偏移更改为本地时间线重叠处的两个有效偏移中的较晚者。
     * <p>
     *  该方法仅在本地时间线重叠时(例如在秋季日光节约割接处)具有任何效果。在这种情况下,本地日期时间有两个有效偏移量。调用此方法将返回一个分区的日期时间,后两个选择的日期时间。
     * <p>
     *  如果此方法在不重叠时调用,则返回{@code this}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return a {@code ZonedDateTime} based on this date-time with the later offset, not null
     */
    @Override
    public ZonedDateTime withLaterOffsetAtOverlap() {
        ZoneOffsetTransition trans = getZone().getRules().getTransition(toLocalDateTime());
        if (trans != null) {
            ZoneOffset laterOffset = trans.getOffsetAfter();
            if (laterOffset.equals(offset) == false) {
                return new ZonedDateTime(dateTime, laterOffset, zone);
            }
        }
        return this;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the time-zone, such as 'Europe/Paris'.
     * <p>
     * This returns the zone ID. This identifies the time-zone {@link ZoneRules rules}
     * that determine when and how the offset from UTC/Greenwich changes.
     * <p>
     * The zone ID may be same as the {@linkplain #getOffset() offset}.
     * If this is true, then any future calculations, such as addition or subtraction,
     * have no complex edge cases due to time-zone rules.
     * See also {@link #withFixedOffsetZone()}.
     *
     * <p>
     *  获取时区,如"欧洲/巴黎"。
     * <p>
     *  这将返回区域ID。这标识了时区{@link ZoneRules规则},确定UTC / Greenwich偏移的时间和方式如何更改。
     * <p>
     *  区域ID可以与{@linkplain #getOffset()offset}相同。如果这是真的,则任何未来的计算,例如加法或减法,由于时区规则没有复杂边缘情况。
     * 另请参见{@link #withFixedOffsetZone()}。
     * 
     * 
     * @return the time-zone, not null
     */
    @Override
    public ZoneId getZone() {
        return zone;
    }

    /**
     * Returns a copy of this date-time with a different time-zone,
     * retaining the local date-time if possible.
     * <p>
     * This method changes the time-zone and retains the local date-time.
     * The local date-time is only changed if it is invalid for the new zone,
     * determined using the same approach as
     * {@link #ofLocal(LocalDateTime, ZoneId, ZoneOffset)}.
     * <p>
     * To change the zone and adjust the local date-time,
     * use {@link #withZoneSameInstant(ZoneId)}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  使用不同的时区返回此日期时间的副本,如果可能,保留本地日期时间。
     * <p>
     * 此方法更改时区并保留本地日期时间。仅当本地日期时间对于新区域无效时才更改,使用与{@link #ofLocal(LocalDateTime,ZoneId,ZoneOffset)}相同的方法确定。
     * <p>
     *  要更改区域并调整本地日期时间,请使用{@link #withZoneSameInstant(ZoneId)}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param zone  the time-zone to change to, not null
     * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
     */
    @Override
    public ZonedDateTime withZoneSameLocal(ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return this.zone.equals(zone) ? this : ofLocal(dateTime, zone, offset);
    }

    /**
     * Returns a copy of this date-time with a different time-zone,
     * retaining the instant.
     * <p>
     * This method changes the time-zone and retains the instant.
     * This normally results in a change to the local date-time.
     * <p>
     * This method is based on retaining the same instant, thus gaps and overlaps
     * in the local time-line have no effect on the result.
     * <p>
     * To change the offset while keeping the local time,
     * use {@link #withZoneSameLocal(ZoneId)}.
     *
     * <p>
     *  返回此日期时间的副本,使用不同的时区,保留即时。
     * <p>
     *  此方法更改时区并保留即时。这通常导致对本地日期时间的更改。
     * <p>
     *  该方法基于保持相同的瞬时,因此在本地时间线中的间隙和重叠对结果没有影响。
     * <p>
     *  要在保持本地时间的情况下更改偏移,请使用{@link #withZoneSameLocal(ZoneId)}。
     * 
     * 
     * @param zone  the time-zone to change to, not null
     * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    @Override
    public ZonedDateTime withZoneSameInstant(ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        return this.zone.equals(zone) ? this :
            create(dateTime.toEpochSecond(offset), dateTime.getNano(), zone);
    }

    /**
     * Returns a copy of this date-time with the zone ID set to the offset.
     * <p>
     * This returns a zoned date-time where the zone ID is the same as {@link #getOffset()}.
     * The local date-time, offset and instant of the result will be the same as in this date-time.
     * <p>
     * Setting the date-time to a fixed single offset means that any future
     * calculations, such as addition or subtraction, have no complex edge cases
     * due to time-zone rules.
     * This might also be useful when sending a zoned date-time across a network,
     * as most protocols, such as ISO-8601, only handle offsets,
     * and not region-based zone IDs.
     * <p>
     * This is equivalent to {@code ZonedDateTime.of(zdt.toLocalDateTime(), zdt.getOffset())}.
     *
     * <p>
     *  返回此日期时间的副本,其中区域ID设置为偏移量。
     * <p>
     *  这将返回一个分区日期时间,其中区域ID与{@link #getOffset()}相同。结果的本地日期时间,偏移和时间将与此日期时间中的相同。
     * <p>
     *  将日期时间设置为固定的单个偏移意味着任何未来的计算,例如加法或减法,由于时区规则没有复杂的边缘情况。
     * 这在通过网络发送分区日期时间时也很有用,因为大多数协议(如ISO-8601)仅处理偏移量,而不处理基于区域的区域ID。
     * <p>
     *  这相当于{@code ZonedDateTime.of(zdt.toLocalDateTime(),zdt.getOffset())}。
     * 
     * 
     * @return a {@code ZonedDateTime} with the zone ID set to the offset, not null
     */
    public ZonedDateTime withFixedOffsetZone() {
        return this.zone.equals(offset) ? this : new ZonedDateTime(dateTime, offset, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the {@code LocalDateTime} part of this date-time.
     * <p>
     * This returns a {@code LocalDateTime} with the same year, month, day and time
     * as this date-time.
     *
     * <p>
     * 获取此日期时间的{@code LocalDateTime}部分。
     * <p>
     *  这会返回与此日期时间相同的年份,月份,日期和时间的{@code LocalDateTime}。
     * 
     * 
     * @return the local date-time part of this date-time, not null
     */
    @Override  // override for return type
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
    @Override  // override for return type
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
     *  此方法返回当月的枚举{@link Month}。这避免了对{@code int}值意味着什么的混淆。
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
     * This method returns the enum {@link DayOfWeek} for the day-of-week.
     * This avoids confusion as to what {@code int} values mean.
     * If you need access to the primitive {@code int} value then the enum
     * provides the {@link DayOfWeek#getValue() int value}.
     * <p>
     * Additional information can be obtained from the {@code DayOfWeek}.
     * This includes textual names of the values.
     *
     * <p>
     *  获取"day-of-week"字段,这是一个枚举{@code DayOfWeek}。
     * <p>
     * 此方法返回星期几的枚举{@link DayOfWeek}。这避免了对{@code int}值意味着什么的混淆。
     * 如果你需要访问原始的{@code int}值,那么枚举提供{@link DayOfWeek#getValue()int value}。
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
    @Override  // override for Javadoc and performance
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
     * This returns a {@code ZonedDateTime}, based on this one, with the date-time adjusted.
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
     *  result = zonedDateTime.with(JULY).with(lastDayOfMonth());
     * </pre>
     * <p>
     * The classes {@link LocalDate} and {@link LocalTime} implement {@code TemporalAdjuster},
     * thus this method can be used to change the date, time or offset:
     * <pre>
     *  result = zonedDateTime.with(date);
     *  result = zonedDateTime.with(time);
     * </pre>
     * <p>
     * {@link ZoneOffset} also implements {@code TemporalAdjuster} however using it
     * as an argument typically has no effect. The offset of a {@code ZonedDateTime} is
     * controlled primarily by the time-zone. As such, changing the offset does not generally
     * make sense, because there is only one valid offset for the local date-time and zone.
     * If the zoned date-time is in a daylight savings overlap, then the offset is used
     * to switch between the two valid offsets. In all other cases, the offset is ignored.
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
     *  这会返回一个{@code ZonedDateTime},根据这一个,日期时间调整。使用指定的调整器策略对象进行调整。阅读调整器的文档以了解将要进行的调整。
     * <p>
     *  简单的调整器可以简单地设置一个字段,例如年份字段。更复杂的调整器可以将日期设置为该月的最后一天。 {@link TemporalAdjuster}中提供了一些常用调整选项。
     * 这些包括找到"月份的最后一天"和"下周三"。
     * 关键日期时间类还实现{@code TemporalAdjuster}接口,例如{@link Month}和{@link java.time.MonthDay MonthDay}。
     * 调整器负责处理特殊情况,例如月份和闰年的变化长度。
     * <p>
     * 例如,此代码返回7月最后一天的日期：
     * <pre>
     *  import static java.time.Month。*; import static java.time.temporal.Adjusters。*;
     * 
     *  result = zonedDateTime.with(JULY).with(lastDayOfMonth());
     * </pre>
     * <p>
     *  类{@link LocalDate}和{@link LocalTime}实现{@code TemporalAdjuster},因此该方法可用于更改日期,时间或偏移量：
     * <pre>
     *  result = zonedDateTime.with(date); result = zonedDateTime.with(time);
     * </pre>
     * <p>
     *  {@link ZoneOffset}也实现了{@code TemporalAdjuster},但是使用它作为参数通常没有效果。 {@code ZonedDateTime}的偏移量主要由时区控制。
     * 因此,改变偏移通常不是有意义的,因为对于本地日期时间和区域仅存在一个有效偏移。如果分区日期时间是在夏令时重叠,则偏移用于在两个有效偏移之间切换。在所有其他情况下,将忽略偏移。
     * <p>
     *  此方法的结果是通过调用指定调整器的{@link TemporalAdjuster#adjustInto(Temporal)}方法传递{@code this}作为参数来获得的。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param adjuster the adjuster to use, not null
     * @return a {@code ZonedDateTime} based on {@code this} with the adjustment made, not null
     * @throws DateTimeException if the adjustment cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public ZonedDateTime with(TemporalAdjuster adjuster) {
        // optimizations
        if (adjuster instanceof LocalDate) {
            return resolveLocal(LocalDateTime.of((LocalDate) adjuster, dateTime.toLocalTime()));
        } else if (adjuster instanceof LocalTime) {
            return resolveLocal(LocalDateTime.of(dateTime.toLocalDate(), (LocalTime) adjuster));
        } else if (adjuster instanceof LocalDateTime) {
            return resolveLocal((LocalDateTime) adjuster);
        } else if (adjuster instanceof OffsetDateTime) {
            OffsetDateTime odt = (OffsetDateTime) adjuster;
            return ofLocal(odt.toLocalDateTime(), zone, odt.getOffset());
        } else if (adjuster instanceof Instant) {
            Instant instant = (Instant) adjuster;
            return create(instant.getEpochSecond(), instant.getNano(), zone);
        } else if (adjuster instanceof ZoneOffset) {
            return resolveOffset((ZoneOffset) adjuster);
        }
        return (ZonedDateTime) adjuster.adjustInto(this);
    }

    /**
     * Returns a copy of this date-time with the specified field set to a new value.
     * <p>
     * This returns a {@code ZonedDateTime}, based on this one, with the value
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
     * The zone and nano-of-second are unchanged.
     * The result will have an offset derived from the new instant and original zone.
     * If the new instant value is outside the valid range then a {@code DateTimeException} will be thrown.
     * <p>
     * The {@code OFFSET_SECONDS} field will typically be ignored.
     * The offset of a {@code ZonedDateTime} is controlled primarily by the time-zone.
     * As such, changing the offset does not generally make sense, because there is only
     * one valid offset for the local date-time and zone.
     * If the zoned date-time is in a daylight savings overlap, then the offset is used
     * to switch between the two valid offsets. In all other cases, the offset is ignored.
     * If the new offset value is outside the valid range then a {@code DateTimeException} will be thrown.
     * <p>
     * The other {@link #isSupported(TemporalField) supported fields} will behave as per
     * the matching method on {@link LocalDateTime#with(TemporalField, long) LocalDateTime}.
     * The zone is not part of the calculation and will be unchanged.
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
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
     * 这将返回一个{@code ZonedDateTime},基于此,指定字段的值已更改。这可以用于更改任何支持的字段,例如年,月或日。如果无法设置该值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  在某些情况下,更改指定字段可能导致生成日期时间变得无效,例如将月份从1月31日更改为2月会使日期无效。在这种情况下,字段负责解析日期。
     * 通常,它将选择上一个有效日期,这将是本示例中二月的最后一个有效日期。
     * <p>
     *  如果字段是{@link ChronoField},那么在此处执行调整。
     * <p>
     *  {@code INSTANT_SECONDS}字段将返回具有指定时刻的日期时间。区域和纳秒是不变的。结果将具有从新的时间和原始区域导出的偏移。
     * 如果新的即时值超出有效范围,那么将抛出{@code DateTimeException}。
     * <p>
     * 通常会忽略{@code OFFSET_SECONDS}字段。 {@code ZonedDateTime}的偏移量主要由时区控制。
     * 因此,改变偏移通常不是有意义的,因为对于本地日期时间和区域仅存在一个有效偏移。如果分区日期时间是在夏令时重叠,则偏移用于在两个有效偏移之间切换。在所有其他情况下,将忽略偏移。
     * 如果新的偏移值在有效范围之外,那么将抛出{@code DateTimeException}。
     * <p>
     *  其他{@link #isSupported(TemporalField)支持的字段}将按照{@link LocalDateTime#with(TemporalField,long)LocalDateTime}
     * 上的匹配方法进行操作。
     * 区域不是计算的一部分,将不会更改。当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。
     * 如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
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
     * @return a {@code ZonedDateTime} based on {@code this} with the specified field set, not null
     * @throws DateTimeException if the field cannot be set
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public ZonedDateTime with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            switch (f) {
                case INSTANT_SECONDS:
                    return create(newValue, getNano(), zone);
                case OFFSET_SECONDS:
                    ZoneOffset offset = ZoneOffset.ofTotalSeconds(f.checkValidIntValue(newValue));
                    return resolveOffset(offset);
            }
            return resolveLocal(dateTime.with(field, newValue));
        }
        return field.adjustInto(this, newValue);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code ZonedDateTime} with the year value altered.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#withYear(int) changing the year} of the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此{@code ZonedDateTime}的副本,其中年值已更改。
     * <p>
     *  此操作基于本地日期时间的本地时间线{@link LocalDateTime#withYear(int)更改年份}。然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param year  the year to set in the result, from MIN_YEAR to MAX_YEAR
     * @return a {@code ZonedDateTime} based on this date-time with the requested year, not null
     * @throws DateTimeException if the year value is invalid
     */
    public ZonedDateTime withYear(int year) {
        return resolveLocal(dateTime.withYear(year));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the month-of-year value altered.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#withMonth(int) changing the month} of the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中年月值已更改。
     * <p>
     *  此操作基于本地日期时间的本地时间线{@link LocalDateTime#withMonth(int)更改月份}。然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param month  the month-of-year to set in the result, from 1 (January) to 12 (December)
     * @return a {@code ZonedDateTime} based on this date-time with the requested month, not null
     * @throws DateTimeException if the month-of-year value is invalid
     */
    public ZonedDateTime withMonth(int month) {
        return resolveLocal(dateTime.withMonth(month));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the day-of-month value altered.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#withDayOfMonth(int) changing the day-of-month} of the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中的日期值已更改。
     * <p>
     * 此操作基于本地时间线,{@link LocalDateTime#withDayOfMonth(int)更改本地日期时间的日期}。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param dayOfMonth  the day-of-month to set in the result, from 1 to 28-31
     * @return a {@code ZonedDateTime} based on this date-time with the requested day, not null
     * @throws DateTimeException if the day-of-month value is invalid,
     *  or if the day-of-month is invalid for the month-year
     */
    public ZonedDateTime withDayOfMonth(int dayOfMonth) {
        return resolveLocal(dateTime.withDayOfMonth(dayOfMonth));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the day-of-year altered.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#withDayOfYear(int) changing the day-of-year} of the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,并更改​​年份。
     * <p>
     *  此操作基于本地时间线,{@link LocalDateTime#withDayOfYear(int)更改本地日期时间的年份}。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param dayOfYear  the day-of-year to set in the result, from 1 to 365-366
     * @return a {@code ZonedDateTime} based on this date with the requested day, not null
     * @throws DateTimeException if the day-of-year value is invalid,
     *  or if the day-of-year is invalid for the year
     */
    public ZonedDateTime withDayOfYear(int dayOfYear) {
        return resolveLocal(dateTime.withDayOfYear(dayOfYear));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code ZonedDateTime} with the hour-of-day value altered.
     * <p>
     * This operates on the local time-line,
     * {@linkplain LocalDateTime#withHour(int) changing the time} of the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中的日期值已更改。
     * <p>
     *  它对本地时间线,本地日期时间的{@linkplain LocalDateTime#withHour(int)更改时间}进行操作。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     * 当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hour  the hour-of-day to set in the result, from 0 to 23
     * @return a {@code ZonedDateTime} based on this date-time with the requested hour, not null
     * @throws DateTimeException if the hour value is invalid
     */
    public ZonedDateTime withHour(int hour) {
        return resolveLocal(dateTime.withHour(hour));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the minute-of-hour value altered.
     * <p>
     * This operates on the local time-line,
     * {@linkplain LocalDateTime#withMinute(int) changing the time} of the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中小时值的值已更改。
     * <p>
     *  它对本地时间线,本地日期时间的{@linkplain LocalDateTime#withMinute(int)更改时间}进行操作。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minute  the minute-of-hour to set in the result, from 0 to 59
     * @return a {@code ZonedDateTime} based on this date-time with the requested minute, not null
     * @throws DateTimeException if the minute value is invalid
     */
    public ZonedDateTime withMinute(int minute) {
        return resolveLocal(dateTime.withMinute(minute));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the second-of-minute value altered.
     * <p>
     * This operates on the local time-line,
     * {@linkplain LocalDateTime#withSecond(int) changing the time} of the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中二分钟值已更改。
     * <p>
     *  它对本地时间线,本地日期时间的{@linkplain LocalDateTime#withSecond(int)更改时间}进行操作。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     * 当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param second  the second-of-minute to set in the result, from 0 to 59
     * @return a {@code ZonedDateTime} based on this date-time with the requested second, not null
     * @throws DateTimeException if the second value is invalid
     */
    public ZonedDateTime withSecond(int second) {
        return resolveLocal(dateTime.withSecond(second));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the nano-of-second value altered.
     * <p>
     * This operates on the local time-line,
     * {@linkplain LocalDateTime#withNano(int) changing the time} of the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的纳秒值已更改的副本。
     * <p>
     *  这在本地时间线上操作,本地日期时间的{@linkplain LocalDateTime#withNano(int)更改时间}。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanoOfSecond  the nano-of-second to set in the result, from 0 to 999,999,999
     * @return a {@code ZonedDateTime} based on this date-time with the requested nanosecond, not null
     * @throws DateTimeException if the nano value is invalid
     */
    public ZonedDateTime withNano(int nanoOfSecond) {
        return resolveLocal(dateTime.withNano(nanoOfSecond));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code ZonedDateTime} with the time truncated.
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
     * This operates on the local time-line,
     * {@link LocalDateTime#truncatedTo(java.time.temporal.TemporalUnit) truncating}
     * the underlying local date-time. This is then converted back to a
     * {@code ZonedDateTime}, using the zone ID to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本并截断时间。
     * <p>
     *  截断返回原始日期时间的副本,其中字段小于指定的单位设置为零。例如,使用{@link ChronoUnit#MINUTES分钟}单位进行截断将会将秒数和毫微秒字段设置为零。
     * <p>
     * 单位必须有一个{@linkplain TemporalUnit#getDuration()duration},它分成标准日的长度,没有余数。
     * 这包括{@link ChronoUnit}和{@link ChronoUnit#DAYS DAYS}上提供的所有时间单位。其他单位抛出异常。
     * <p>
     *  此操作在本地时间线{@link LocalDateTime#truncatedTo(java.time.temporal.TemporalUnit)截断}底层本地日期时间。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param unit  the unit to truncate to, not null
     * @return a {@code ZonedDateTime} based on this date-time with the time truncated, not null
     * @throws DateTimeException if unable to truncate
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     */
    public ZonedDateTime truncatedTo(TemporalUnit unit) {
        return resolveLocal(dateTime.truncatedTo(unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date-time with the specified amount added.
     * <p>
     * This returns a {@code ZonedDateTime}, based on this one, with the specified amount added.
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
     *  这将返回一个{@code ZonedDateTime},基于这一个,与指定的添加量。
     * 金额通常为{@link Period}或{@link Duration},但可以是实现{@link TemporalAmount}界面的任何其他类型。
     * <p>
     *  通过调用{@link TemporalAmount#addTo(Temporal)}将计算委托给金额对象。
     * 金额实现可以以任何希望的方式实现添加,但是它通常回调{@link #plus(long,TemporalUnit)}。请参阅金额实施的文档以确定是否可以成功添加。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount to add, not null
     * @return a {@code ZonedDateTime} based on this date-time with the addition made, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public ZonedDateTime plus(TemporalAmount amountToAdd) {
        if (amountToAdd instanceof Period) {
            Period periodToAdd = (Period) amountToAdd;
            return resolveLocal(dateTime.plus(periodToAdd));
        }
        Objects.requireNonNull(amountToAdd, "amountToAdd");
        return (ZonedDateTime) amountToAdd.addTo(this);
    }

    /**
     * Returns a copy of this date-time with the specified amount added.
     * <p>
     * This returns a {@code ZonedDateTime}, based on this one, with the amount
     * in terms of the unit added. If it is not possible to add the amount, because the
     * unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoUnit} then the addition is implemented here.
     * The zone is not part of the calculation and will be unchanged in the result.
     * The calculation for date and time units differ.
     * <p>
     * Date units operate on the local time-line.
     * The period is first added to the local date-time, then converted back
     * to a zoned date-time using the zone ID.
     * The conversion uses {@link #ofLocal(LocalDateTime, ZoneId, ZoneOffset)}
     * with the offset before the addition.
     * <p>
     * Time units operate on the instant time-line.
     * The period is first added to the local date-time, then converted back to
     * a zoned date-time using the zone ID.
     * The conversion uses {@link #ofInstant(LocalDateTime, ZoneOffset, ZoneId)}
     * with the offset before the addition.
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
     *  这将返回一个{@code ZonedDateTime},基于这一个,与添加单位的金额。如果无法添加该金额,因为该单元不受支持或出于其他原因,将抛出异常。
     * <p>
     *  如果该字段是{@link ChronoUnit},则在此处实现添加。区域不是计算的一部分,并且在结果中将保持不变。日期和时间单位的计算不同。
     * <p>
     *  日期单位在本地时间线上操作。该时间段首先添加到本地日期时间,然后使用区域ID转换回分区日期时间。
     * 转换使用{@link #ofLocal(LocalDateTime,ZoneId,ZoneOffset)}与添加之前的偏移量。
     * <p>
     *  时间单位在即时时间线上操作。该时间段首先添加到本地日期时间,然后使用区域ID转换回分区日期时间。
     * 转换使用{@link #ofInstant(LocalDateTime,ZoneOffset,ZoneId)}与添加之前的偏移量。
     * <p>
     *  如果字段不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.addTo(Temporal,long)}传递{@code this}作为参数来获得此方法的结果。
     * 在这种情况下,单元确定是否以及如何执行添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount of the unit to add to the result, may be negative
     * @param unit  the unit of the amount to add, not null
     * @return a {@code ZonedDateTime} based on this date-time with the specified amount added, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public ZonedDateTime plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            if (unit.isDateBased()) {
                return resolveLocal(dateTime.plus(amountToAdd, unit));
            } else {
                return resolveInstant(dateTime.plus(amountToAdd, unit));
            }
        }
        return unit.addTo(this, amountToAdd);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in years added.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#plusYears(long) adding years} to the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中包含指定的周期(以年为单位)。
     * <p>
     * 此操作基于本地时间线{@link LocalDateTime#plusYears(long)adding years}到本地日期时间。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param years  the years to add, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime plusYears(long years) {
        return resolveLocal(dateTime.plusYears(years));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in months added.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#plusMonths(long) adding months} to the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中包含指定的周期(以月为单位)。
     * <p>
     *  此操作基于本地时间线{@link LocalDateTime#plusMonths(long)添加月份}到本地日期时间。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param months  the months to add, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the months added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime plusMonths(long months) {
        return resolveLocal(dateTime.plusMonths(months));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in weeks added.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#plusWeeks(long) adding weeks} to the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中包含指定的周期(以周为单位)。
     * <p>
     *  此操作基于本地时间线{@link LocalDateTime#plusWeeks(long)adding weeks}到本地日期时间。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     * 当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param weeks  the weeks to add, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the weeks added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime plusWeeks(long weeks) {
        return resolveLocal(dateTime.plusWeeks(weeks));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in days added.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#plusDays(long) adding days} to the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的周期(以天为单位)返回此{@code ZonedDateTime}的副本。
     * <p>
     *  此操作基于本地时间线{@link LocalDateTime#plusDays(long)adding days}到本地日期时间。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param days  the days to add, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the days added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime plusDays(long days) {
        return resolveLocal(dateTime.plusDays(days));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in hours added.
     * <p>
     * This operates on the instant time-line, such that adding one hour will
     * always be a duration of one hour later.
     * This may cause the local date-time to change by an amount other than one hour.
     * Note that this is a different approach to that used by days, months and years,
     * thus adding one day is not the same as adding 24 hours.
     * <p>
     * For example, consider a time-zone where the spring DST cutover means that the
     * local times 01:00 to 01:59 occur twice changing from offset +02:00 to +01:00.
     * <ul>
     * <li>Adding one hour to 00:30+02:00 will result in 01:30+02:00
     * <li>Adding one hour to 01:30+02:00 will result in 01:30+01:00
     * <li>Adding one hour to 01:30+01:00 will result in 02:30+01:00
     * <li>Adding three hours to 00:30+02:00 will result in 02:30+01:00
     * </ul>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的周期(以小时为单位)返回此{@code ZonedDateTime}的副本。
     * <p>
     *  这在瞬时时间线上操作,使得添加一小时将总是一小时后的持续时间。这可能会导致本地日期时间更改非1小时的数量。注意,这是一种与天,月和年使用的方法不同的方法,因此添加一天不等于添加24小时。
     * <p>
     * 例如,考虑一个时区,其中弹簧DST转换意味着本地时间01:00至01:59发生两次从偏移+02：00到+01：00的改变。
     * <ul>
     *  <li>将时间加到00：30 + 02：00将导致01：30 + 02：00 <li>将时间加到01：30 + 02：00将导致01：30 + 01：00 <li >向01：30 + 01：00增加1
     * 小时将导致02：30 + 01：00 <li>将三小时添加到00：30 + 02：00将导致02：30 + 01：00。
     * </ul>
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hours  the hours to add, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the hours added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime plusHours(long hours) {
        return resolveInstant(dateTime.plusHours(hours));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in minutes added.
     * <p>
     * This operates on the instant time-line, such that adding one minute will
     * always be a duration of one minute later.
     * This may cause the local date-time to change by an amount other than one minute.
     * Note that this is a different approach to that used by days, months and years.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中包含指定的时间(以分钟为单位)。
     * <p>
     *  这在瞬时时间线上操作,使得添加一分钟将总是一分钟后的持续时间。这可能会导致本地日期时间更改非1分钟的量。注意,这是一种不同的方法,使用的天,月和年。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minutes  the minutes to add, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the minutes added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime plusMinutes(long minutes) {
        return resolveInstant(dateTime.plusMinutes(minutes));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in seconds added.
     * <p>
     * This operates on the instant time-line, such that adding one second will
     * always be a duration of one second later.
     * This may cause the local date-time to change by an amount other than one second.
     * Note that this is a different approach to that used by days, months and years.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的周期(以秒为单位)返回此{@code ZonedDateTime}的副本。
     * <p>
     *  这在瞬时时间线上操作,使得添加一秒将总是持续一秒。这可能会导致本地日期时间更改非1秒钟的数量。注意,这是一种不同的方法,使用的天,月和年。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param seconds  the seconds to add, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the seconds added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime plusSeconds(long seconds) {
        return resolveInstant(dateTime.plusSeconds(seconds));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in nanoseconds added.
     * <p>
     * This operates on the instant time-line, such that adding one nano will
     * always be a duration of one nano later.
     * This may cause the local date-time to change by an amount other than one nano.
     * Note that this is a different approach to that used by days, months and years.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中包含指定的周期(以纳秒为单位)。
     * <p>
     * 这在瞬时时间线上操作,使得添加一个纳米将总是1纳米的持续时间。这可能导致本地日期时间改变一个纳米以外的量。注意,这是一种不同的方法,使用的天,月和年。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanos  the nanos to add, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the nanoseconds added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime plusNanos(long nanos) {
        return resolveInstant(dateTime.plusNanos(nanos));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date-time with the specified amount subtracted.
     * <p>
     * This returns a {@code ZonedDateTime}, based on this one, with the specified amount subtracted.
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
     *  这将返回一个{@code ZonedDateTime},基于此,减去指定的金额。
     * 金额通常为{@link Period}或{@link Duration},但可以是实现{@link TemporalAmount}界面的任何其他类型。
     * <p>
     *  通过调用{@link TemporalAmount#subtractFrom(Temporal)}将计算委托给金额对象。
     * 金额实现可以以任何方式自由实现减法,但它通常回调{@link #minus(long,TemporalUnit)}。请参阅金额实施的文档,以确定是否可以成功扣除。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount to subtract, not null
     * @return a {@code ZonedDateTime} based on this date-time with the subtraction made, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public ZonedDateTime minus(TemporalAmount amountToSubtract) {
        if (amountToSubtract instanceof Period) {
            Period periodToSubtract = (Period) amountToSubtract;
            return resolveLocal(dateTime.minus(periodToSubtract));
        }
        Objects.requireNonNull(amountToSubtract, "amountToSubtract");
        return (ZonedDateTime) amountToSubtract.subtractFrom(this);
    }

    /**
     * Returns a copy of this date-time with the specified amount subtracted.
     * <p>
     * This returns a {@code ZonedDateTime}, based on this one, with the amount
     * in terms of the unit subtracted. If it is not possible to subtract the amount,
     * because the unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * The calculation for date and time units differ.
     * <p>
     * Date units operate on the local time-line.
     * The period is first subtracted from the local date-time, then converted back
     * to a zoned date-time using the zone ID.
     * The conversion uses {@link #ofLocal(LocalDateTime, ZoneId, ZoneOffset)}
     * with the offset before the subtraction.
     * <p>
     * Time units operate on the instant time-line.
     * The period is first subtracted from the local date-time, then converted back to
     * a zoned date-time using the zone ID.
     * The conversion uses {@link #ofInstant(LocalDateTime, ZoneOffset, ZoneId)}
     * with the offset before the subtraction.
     * <p>
     * This method is equivalent to {@link #plus(long, TemporalUnit)} with the amount negated.
     * See that method for a full description of how addition, and thus subtraction, works.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期时间的副本,并减去指定的金额。
     * <p>
     *  这会返回一个{@code ZonedDateTime},根据这一个,与减去单位的金额。如果不可能减去金额,因为该单元不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  日期和时间单位的计算不同。
     * <p>
     * 日期单位在本地时间线上操作。首先从本地日期时间中减去期间,然后使用区域ID将其转换回分区日期时间。
     * 转换使用{@link #ofLocal(LocalDateTime,ZoneId,ZoneOffset)}与减法之前的偏移量。
     * <p>
     *  时间单位在即时时间线上操作。首先从本地日期时间中减去期间,然后使用区域ID将其转换回分区日期时间。
     * 转换使用{@link #ofInstant(LocalDateTime,ZoneOffset,ZoneId)}与减法之前的偏移量。
     * <p>
     *  此方法等效于{@link #plus(long,TemporalUnit)},其值为negated。请参阅该方法,了解如何添加和减少的工作原理的完整描述。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount of the unit to subtract from the result, may be negative
     * @param unit  the unit of the amount to subtract, not null
     * @return a {@code ZonedDateTime} based on this date-time with the specified amount subtracted, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public ZonedDateTime minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in years subtracted.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#minusYears(long) subtracting years} to the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中包含指定的减去年份的周期。
     * <p>
     *  此操作在本地时间线{@link LocalDateTime#minusYears(long)减年数}到本地日期时间。然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param years  the years to subtract, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime minusYears(long years) {
        return (years == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-years));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in months subtracted.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#minusMonths(long) subtracting months} to the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此{@code ZonedDateTime}的副本,其中指定的周期减去月。
     * <p>
     *  此操作在本地时间线{@link LocalDateTime#减去月份(长)减去月份}到本地日期时间。然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param months  the months to subtract, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the months subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime minusMonths(long months) {
        return (months == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-months));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in weeks subtracted.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#minusWeeks(long) subtracting weeks} to the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中指定的周期减去星期。
     * <p>
     *  此操作在本地时间线{@link LocalDateTime#minusWeeks(long)减星期}到本地日期时间。然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param weeks  the weeks to subtract, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the weeks subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime minusWeeks(long weeks) {
        return (weeks == Long.MIN_VALUE ? plusWeeks(Long.MAX_VALUE).plusWeeks(1) : plusWeeks(-weeks));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in days subtracted.
     * <p>
     * This operates on the local time-line,
     * {@link LocalDateTime#minusDays(long) subtracting days} to the local date-time.
     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
     * to obtain the offset.
     * <p>
     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
     * then the offset will be retained if possible, otherwise the earlier offset will be used.
     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中指定的周期减去天数。
     * <p>
     * 这会根据本地时间线{@link LocalDateTime#minusDays(long)减days}运行到本地日期时间。
     * 然后将其转换回{@code ZonedDateTime},使用区域ID获取偏移量。
     * <p>
     *  当转换回{@code ZonedDateTime}时,如果本地日期时间在重叠,则偏移将被保留,如果可能的话,否则将使用更早的偏移。如果在间隙中,本地日期时间将被向前调整间隙的长度。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param days  the days to subtract, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the days subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime minusDays(long days) {
        return (days == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-days));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in hours subtracted.
     * <p>
     * This operates on the instant time-line, such that subtracting one hour will
     * always be a duration of one hour earlier.
     * This may cause the local date-time to change by an amount other than one hour.
     * Note that this is a different approach to that used by days, months and years,
     * thus subtracting one day is not the same as adding 24 hours.
     * <p>
     * For example, consider a time-zone where the spring DST cutover means that the
     * local times 01:00 to 01:59 occur twice changing from offset +02:00 to +01:00.
     * <ul>
     * <li>Subtracting one hour from 02:30+01:00 will result in 01:30+02:00
     * <li>Subtracting one hour from 01:30+01:00 will result in 01:30+02:00
     * <li>Subtracting one hour from 01:30+02:00 will result in 00:30+01:00
     * <li>Subtracting three hours from 02:30+01:00 will result in 00:30+02:00
     * </ul>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中包含指定的减去小时数的周期。
     * <p>
     *  这在瞬时时间线上操作,使得减去一小时将总是早一小时的持续时间。这可能会导致本地日期时间更改非1小时的数量。注意,这是一个不同的方法,使用的天,月和年,因此减去一天不等于加入24小时。
     * <p>
     *  例如,考虑一个时区,其中弹簧DST转换意味着本地时间01:00至01:59发生两次从偏移+02：00到+01：00的改变。
     * <ul>
     *  <li>从02：30 + 01：00减去1小时将导致01：30 + 02：00 <li>从01：30 + 01：00减去1小时将导致01：30 + 02：00 <li >从01：30 + 02：00减
     * 去1小时将导致00：30 + 01：00 <li>从02：30 + 01：00减去3小时将得到00：30 + 02：00。
     * </ul>
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hours  the hours to subtract, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the hours subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime minusHours(long hours) {
        return (hours == Long.MIN_VALUE ? plusHours(Long.MAX_VALUE).plusHours(1) : plusHours(-hours));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in minutes subtracted.
     * <p>
     * This operates on the instant time-line, such that subtracting one minute will
     * always be a duration of one minute earlier.
     * This may cause the local date-time to change by an amount other than one minute.
     * Note that this is a different approach to that used by days, months and years.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此{@code ZonedDateTime}的副本,其中包含指定的时间(以分钟为单位)。
     * <p>
     *  这在瞬时时间线上操作,使得减去一分钟将总是早一分钟的持续时间。这可能会导致本地日期时间更改非1分钟的量。注意,这是一种不同的方法,使用的天,月和年。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minutes  the minutes to subtract, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the minutes subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime minusMinutes(long minutes) {
        return (minutes == Long.MIN_VALUE ? plusMinutes(Long.MAX_VALUE).plusMinutes(1) : plusMinutes(-minutes));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in seconds subtracted.
     * <p>
     * This operates on the instant time-line, such that subtracting one second will
     * always be a duration of one second earlier.
     * This may cause the local date-time to change by an amount other than one second.
     * Note that this is a different approach to that used by days, months and years.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,其中指定的周期(以秒为单位)减去。
     * <p>
     *  这在瞬时时间线上操作,使得减去一秒将总是先于一秒的持续时间。这可能会导致本地日期时间更改非1秒钟的数量。注意,这是一种不同的方法,使用的天,月和年。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param seconds  the seconds to subtract, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the seconds subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime minusSeconds(long seconds) {
        return (seconds == Long.MIN_VALUE ? plusSeconds(Long.MAX_VALUE).plusSeconds(1) : plusSeconds(-seconds));
    }

    /**
     * Returns a copy of this {@code ZonedDateTime} with the specified period in nanoseconds subtracted.
     * <p>
     * This operates on the instant time-line, such that subtracting one nano will
     * always be a duration of one nano earlier.
     * This may cause the local date-time to change by an amount other than one nano.
     * Note that this is a different approach to that used by days, months and years.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code ZonedDateTime}的副本,以指定的周期减去纳秒。
     * <p>
     *  这在瞬时时间线上操作,使得减去一个纳米将总是先前一纳米的持续时间。这可能导致本地日期时间改变一个纳米以外的量。注意,这是一种不同的方法,使用的天,月和年。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanos  the nanos to subtract, may be negative
     * @return a {@code ZonedDateTime} based on this date-time with the nanoseconds subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public ZonedDateTime minusNanos(long nanos) {
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
     * {@link java.time.temporal.TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此日期时间。
     * <p>
     * 这将使用指定的查询策略对象查询此日期时间。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
     * <p>
     *  此方法的结果是通过调用{@link java.time.temporal.TemporalQuery#queryFrom(TemporalAccessor)}方法对指定的查询传递{@code this}
     * 作为参数来获得的。
     * 
     * 
     * @param <R> the type of the result
     * @param query  the query to invoke, not null
     * @return the query result, null may be returned (defined by the query)
     * @throws DateTimeException if unable to query (defined by the query)
     * @throws ArithmeticException if numeric overflow occurs (defined by the query)
     */
    @SuppressWarnings("unchecked")
    @Override  // override for Javadoc
    public <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.localDate()) {
            return (R) toLocalDate();
        }
        return ChronoZonedDateTime.super.query(query);
    }

    /**
     * Calculates the amount of time until another date-time in terms of the specified unit.
     * <p>
     * This calculates the amount of time between two {@code ZonedDateTime}
     * objects in terms of a single {@code TemporalUnit}.
     * The start and end points are {@code this} and the specified date-time.
     * The result will be negative if the end is before the start.
     * For example, the period in days between two date-times can be calculated
     * using {@code startDateTime.until(endDateTime, DAYS)}.
     * <p>
     * The {@code Temporal} passed to this method is converted to a
     * {@code ZonedDateTime} using {@link #from(TemporalAccessor)}.
     * If the time-zone differs between the two zoned date-times, the specified
     * end date-time is normalized to have the same zone as this date-time.
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
     * The calculation for date and time units differ.
     * <p>
     * Date units operate on the local time-line, using the local date-time.
     * For example, the period from noon on day 1 to noon the following day
     * in days will always be counted as exactly one day, irrespective of whether
     * there was a daylight savings change or not.
     * <p>
     * Time units operate on the instant time-line.
     * The calculation effectively converts both zoned date-times to instants
     * and then calculates the period between the instants.
     * For example, the period from noon on day 1 to noon the following day
     * in hours may be 23, 24 or 25 hours (or some other amount) depending on
     * whether there was a daylight savings change or not.
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
     *  这将根据单个{@code TemporalUnit}计算两个{@code ZonedDateTime}对象之间的时间量。起点和终点是{@code this}和指定的日期时间。
     * 如果结束在开始之前,结果将为负。例如,两个日期时间之间的天数可以使用{@code startDateTime.until(endDateTime,DAYS)}计算。
     * <p>
     *  传递给此方法的{@code Temporal}将使用{@link #from(TemporalAccessor)}转换为{@code ZonedDateTime}。
     * 如果两个分区日期时间之间的时区不同,则将指定的结束日期时间归一化为与此日期时间相同的区域。
     * <p>
     *  计算返回一个整数,表示两个日期时间之间的完整单位数。例如,2012-06-15T00：00Z和2012-08-14T23：59Z之间的月份只有一个月,因为它是两个月的一分钟。
     * <p>
     * 有两种等效的方法使用这种方法。第一个是调用这个方法。第二个是使用{@link TemporalUnit#between(Temporal,Temporal)}：
     * <pre>
     *  //这两行是等价的amount = start.until(end,MONTHS); amount = MONTHS.between(start,end);
     * </pre>
     *  应该基于哪个使得代码更可读的选择。
     * <p>
     *  该计算在{@link ChronoUnit}的此方法中实现。
     *  {@code NANOS},{@code MICROS},{@code MILLIS},{@code SECONDS},{@code MINUTES},{@code HOURS}和{@code HALF_DAYS}
     * ,{@code DAYS}支持{@code WEEKS},{@code MONTHS},{@code YEARS},{@code DECADES},{@code CENTURIES},{@code MILLENNIA}
     * 和{@code ERAS}。
     *  该计算在{@link ChronoUnit}的此方法中实现。其他{@code ChronoUnit}值会抛出异常。
     * <p>
     *  日期和时间单位的计算不同。
     * <p>
     *  日期单位使用本地日期时间在本地时间线上运行。例如,无论是否有夏令时更改,从第1天中午到第几天中午到第二天的时间段都将被计为完全一天。
     * <p>
     *  时间单位在即时时间线上操作。计算有效地将两个分区日期时间转换为时刻,然后计算时刻之间的周期。
     * 例如,取决于是否存在夏令时改变,从第1天中午到第二天中午(以小时计)的时段可以是23小时,24小时或25小时(或一些其他量)。
     * <p>
     * 如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.between(Temporal,Temporal)}传递{@code this}作为第一个参数和转
     * 换的输入时间为第二个参数。
     * <p>
     * 
     * @param endExclusive  the end date, exclusive, which is converted to a {@code ZonedDateTime}, not null
     * @param unit  the unit to measure the amount in, not null
     * @return the amount of time between this date-time and the end date-time
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to a {@code ZonedDateTime}
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        ZonedDateTime end = ZonedDateTime.from(endExclusive);
        if (unit instanceof ChronoUnit) {
            end = end.withZoneSameInstant(zone);
            if (unit.isDateBased()) {
                return dateTime.until(end.dateTime, unit);
            } else {
                return toOffsetDateTime().until(end.toOffsetDateTime(), unit);
            }
        }
        return unit.between(this, end);
    }

    /**
     * Formats this date-time using the specified formatter.
     * <p>
     * This date-time will be passed to the formatter to produce a string.
     *
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param formatter  the formatter to use, not null
     * @return the formatted date-time string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    @Override  // override for Javadoc and performance
    public String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Converts this date-time to an {@code OffsetDateTime}.
     * <p>
     * This creates an offset date-time using the local date-time and offset.
     * The zone ID is ignored.
     *
     * <p>
     *  使用指定的格式化程序格式化此日期时间。
     * <p>
     *  此日期时间将传递到格式化程序以生成字符串。
     * 
     * 
     * @return an offset date-time representing the same local date-time and offset, not null
     */
    public OffsetDateTime toOffsetDateTime() {
        return OffsetDateTime.of(dateTime, offset);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this date-time is equal to another date-time.
     * <p>
     * The comparison is based on the offset date-time and the zone.
     * Only objects of type {@code ZonedDateTime} are compared, other types return false.
     *
     * <p>
     *  将此日期时间转换为{@code OffsetDateTime}。
     * <p>
     *  这将使用本地日期时间和偏移量创建偏移日期时间。将忽略区域ID。
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
        if (obj instanceof ZonedDateTime) {
            ZonedDateTime other = (ZonedDateTime) obj;
            return dateTime.equals(other.dateTime) &&
                offset.equals(other.offset) &&
                zone.equals(other.zone);
        }
        return false;
    }

    /**
     * A hash code for this date-time.
     *
     * <p>
     *  检查此日期时间是否等于另一个日期时间。
     * <p>
     *  比较基于偏移日期时间和区域。仅比较{@code ZonedDateTime}类型的对象,其他类型返回false。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return dateTime.hashCode() ^ offset.hashCode() ^ Integer.rotateLeft(zone.hashCode(), 3);
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this date-time as a {@code String}, such as
     * {@code 2007-12-03T10:15:30+01:00[Europe/Paris]}.
     * <p>
     * The format consists of the {@code LocalDateTime} followed by the {@code ZoneOffset}.
     * If the {@code ZoneId} is not the same as the offset, then the ID is output.
     * The output is compatible with ISO-8601 if the offset and ID are the same.
     *
     * <p>
     *  此日期时间的哈希码。
     * 
     * 
     * @return a string representation of this date-time, not null
     */
    @Override  // override for Javadoc
    public String toString() {
        String str = dateTime.toString() + offset.toString();
        if (offset != zone) {
            str += '[' + zone.toString() + ']';
        }
        return str;
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     *  将此日期时间作为{@code String}输出,例如{@code 2007-12-03T10：15：30 + 01：00 [Europe / Paris]}。
     * <p>
     *  格式包括{@code LocalDateTime}和{@code ZoneOffset}。如果{@code ZoneId}与偏移量不同,则输出ID。如果偏移和ID相同,则输出与ISO-8601兼容。
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(6);  // identifies a ZonedDateTime
     *  // the <a href="../../serialized-form.html#java.time.LocalDateTime">dateTime</a> excluding the one byte header
     *  // the <a href="../../serialized-form.html#java.time.ZoneOffset">offset</a> excluding the one byte header
     *  // the <a href="../../serialized-form.html#java.time.ZoneId">zone ID</a> excluding the one byte header
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.ZONE_DATE_TIME_TYPE, this);
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

    void writeExternal(DataOutput out) throws IOException {
        dateTime.writeExternal(out);
        offset.writeExternal(out);
        zone.write(out);
    }

    static ZonedDateTime readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        LocalDateTime dateTime = LocalDateTime.readExternal(in);
        ZoneOffset offset = ZoneOffset.readExternal(in);
        ZoneId zone = (ZoneId) Ser.read(in);
        return ZonedDateTime.ofLenient(dateTime, offset, zone);
    }

}
