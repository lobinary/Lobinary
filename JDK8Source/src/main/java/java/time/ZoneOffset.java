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

import static java.time.LocalTime.MINUTES_PER_HOUR;
import static java.time.LocalTime.SECONDS_PER_HOUR;
import static java.time.LocalTime.SECONDS_PER_MINUTE;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.time.zone.ZoneRules;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A time-zone offset from Greenwich/UTC, such as {@code +02:00}.
 * <p>
 * A time-zone offset is the period of time that a time-zone differs from Greenwich/UTC.
 * This is usually a fixed number of hours and minutes.
 * <p>
 * Different parts of the world have different time-zone offsets.
 * The rules for how offsets vary by place and time of year are captured in the
 * {@link ZoneId} class.
 * <p>
 * For example, Paris is one hour ahead of Greenwich/UTC in winter and two hours
 * ahead in summer. The {@code ZoneId} instance for Paris will reference two
 * {@code ZoneOffset} instances - a {@code +01:00} instance for winter,
 * and a {@code +02:00} instance for summer.
 * <p>
 * In 2008, time-zone offsets around the world extended from -12:00 to +14:00.
 * To prevent any problems with that range being extended, yet still provide
 * validation, the range of offsets is restricted to -18:00 to 18:00 inclusive.
 * <p>
 * This class is designed for use with the ISO calendar system.
 * The fields of hours, minutes and seconds make assumptions that are valid for the
 * standard ISO definitions of those fields. This class may be used with other
 * calendar systems providing the definition of the time fields matches those
 * of the ISO calendar system.
 * <p>
 * Instances of {@code ZoneOffset} must be compared using {@link #equals}.
 * Implementations may choose to cache certain common offsets, however
 * applications must not rely on such caching.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code ZoneOffset} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  相对于格林威治/ UTC的时区偏移量,例如{@code +02：00}。
 * <p>
 *  时区偏移是时区与格林威治/ UTC不同的时间段。这通常是固定的小时数和分钟数。
 * <p>
 *  世界的不同部分具有不同的时区偏移。在{@link ZoneId}类中捕获偏移量因地点和时间而变化的规则。
 * <p>
 *  例如,巴黎在冬天比格林威治/ UTC提前一小时,在夏天提前两个小时。
 * 巴黎的{@code ZoneId}实例将引用两个{@code ZoneOffset}实例 - 冬天的{@code +01：00}实例和夏天的{@code +02：00}实例。
 * <p>
 * 2008年,世界各地的时区偏移从-12：00延伸到+14：00。为了防止扩展该范围的任何问题,但仍提供验证,偏移范围限制为-18：00至18:00(含)。
 * <p>
 *  此类设计用于ISO日历系统。小时,分钟和秒的字段做出对这些字段的标准ISO定义有效的假设。该类可以与其他日历系统一起使用,提供与ISO日历系统的时间字段的时间字段的定义。
 * <p>
 *  必须使用{@link #equals}比较{@code ZoneOffset}的实例。实现可以选择缓存某些公共偏移,然而应用不能依赖这样的缓存。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code ZoneOffset}实例上使用身份敏感
 * 操作(包括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class ZoneOffset
        extends ZoneId
        implements TemporalAccessor, TemporalAdjuster, Comparable<ZoneOffset>, Serializable {

    /** Cache of time-zone offset by offset in seconds. */
    private static final ConcurrentMap<Integer, ZoneOffset> SECONDS_CACHE = new ConcurrentHashMap<>(16, 0.75f, 4);
    /** Cache of time-zone offset by ID. */
    private static final ConcurrentMap<String, ZoneOffset> ID_CACHE = new ConcurrentHashMap<>(16, 0.75f, 4);

    /**
     * The abs maximum seconds.
     * <p>
     *  abs最大秒数。
     * 
     */
    private static final int MAX_SECONDS = 18 * SECONDS_PER_HOUR;
    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 2357656521762053153L;

    /**
     * The time-zone offset for UTC, with an ID of 'Z'.
     * <p>
     *  UTC的时区偏移量,ID为"Z"。
     * 
     */
    public static final ZoneOffset UTC = ZoneOffset.ofTotalSeconds(0);
    /**
     * Constant for the maximum supported offset.
     * <p>
     *  最大支持偏移的常量。
     * 
     */
    public static final ZoneOffset MIN = ZoneOffset.ofTotalSeconds(-MAX_SECONDS);
    /**
     * Constant for the maximum supported offset.
     * <p>
     *  最大支持偏移的常量。
     * 
     */
    public static final ZoneOffset MAX = ZoneOffset.ofTotalSeconds(MAX_SECONDS);

    /**
     * The total offset in seconds.
     * <p>
     *  总偏移量(以秒为单位)。
     * 
     */
    private final int totalSeconds;
    /**
     * The string form of the time-zone offset.
     * <p>
     *  时区偏移的字符串形式。
     * 
     */
    private final transient String id;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZoneOffset} using the ID.
     * <p>
     * This method parses the string ID of a {@code ZoneOffset} to
     * return an instance. The parsing accepts all the formats generated by
     * {@link #getId()}, plus some additional formats:
     * <ul>
     * <li>{@code Z} - for UTC
     * <li>{@code +h}
     * <li>{@code +hh}
     * <li>{@code +hh:mm}
     * <li>{@code -hh:mm}
     * <li>{@code +hhmm}
     * <li>{@code -hhmm}
     * <li>{@code +hh:mm:ss}
     * <li>{@code -hh:mm:ss}
     * <li>{@code +hhmmss}
     * <li>{@code -hhmmss}
     * </ul>
     * Note that &plusmn; means either the plus or minus symbol.
     * <p>
     * The ID of the returned offset will be normalized to one of the formats
     * described by {@link #getId()}.
     * <p>
     * The maximum supported range is from +18:00 to -18:00 inclusive.
     *
     * <p>
     *  使用ID获取{@code ZoneOffset}的实例。
     * <p>
     * 此方法解析{@code ZoneOffset}的字符串ID以返回实例。解析接受{@link #getId()}生成的所有格式,以及一些其他格式：
     * <ul>
     *  <li> {@ code Z}  - 适用于UTC <li> {@ code + h} <li> {@ code + hh} <li> {@ code + hh：mm} } <li> {@ code -hh：mm：ss}
     *  <li> {@ code -hh：mm：ss} <li> {@ code-hhmm} code + hhmmss} <li> {@ code -hhmmss}。
     * </ul>
     *  请注意&plusmn;表示加号或减号。
     * <p>
     *  返回的偏移量的ID将被标准化为{@link #getId()}描述的格式之一。
     * <p>
     *  最大支持范围为+18：00至-18：00(含)。
     * 
     * 
     * @param offsetId  the offset ID, not null
     * @return the zone-offset, not null
     * @throws DateTimeException if the offset ID is invalid
     */
    @SuppressWarnings("fallthrough")
    public static ZoneOffset of(String offsetId) {
        Objects.requireNonNull(offsetId, "offsetId");
        // "Z" is always in the cache
        ZoneOffset offset = ID_CACHE.get(offsetId);
        if (offset != null) {
            return offset;
        }

        // parse - +h, +hh, +hhmm, +hh:mm, +hhmmss, +hh:mm:ss
        final int hours, minutes, seconds;
        switch (offsetId.length()) {
            case 2:
                offsetId = offsetId.charAt(0) + "0" + offsetId.charAt(1);  // fallthru
            case 3:
                hours = parseNumber(offsetId, 1, false);
                minutes = 0;
                seconds = 0;
                break;
            case 5:
                hours = parseNumber(offsetId, 1, false);
                minutes = parseNumber(offsetId, 3, false);
                seconds = 0;
                break;
            case 6:
                hours = parseNumber(offsetId, 1, false);
                minutes = parseNumber(offsetId, 4, true);
                seconds = 0;
                break;
            case 7:
                hours = parseNumber(offsetId, 1, false);
                minutes = parseNumber(offsetId, 3, false);
                seconds = parseNumber(offsetId, 5, false);
                break;
            case 9:
                hours = parseNumber(offsetId, 1, false);
                minutes = parseNumber(offsetId, 4, true);
                seconds = parseNumber(offsetId, 7, true);
                break;
            default:
                throw new DateTimeException("Invalid ID for ZoneOffset, invalid format: " + offsetId);
        }
        char first = offsetId.charAt(0);
        if (first != '+' && first != '-') {
            throw new DateTimeException("Invalid ID for ZoneOffset, plus/minus not found when expected: " + offsetId);
        }
        if (first == '-') {
            return ofHoursMinutesSeconds(-hours, -minutes, -seconds);
        } else {
            return ofHoursMinutesSeconds(hours, minutes, seconds);
        }
    }

    /**
     * Parse a two digit zero-prefixed number.
     *
     * <p>
     *  解析两位数的零前缀数字。
     * 
     * 
     * @param offsetId  the offset ID, not null
     * @param pos  the position to parse, valid
     * @param precededByColon  should this number be prefixed by a precededByColon
     * @return the parsed number, from 0 to 99
     */
    private static int parseNumber(CharSequence offsetId, int pos, boolean precededByColon) {
        if (precededByColon && offsetId.charAt(pos - 1) != ':') {
            throw new DateTimeException("Invalid ID for ZoneOffset, colon not found when expected: " + offsetId);
        }
        char ch1 = offsetId.charAt(pos);
        char ch2 = offsetId.charAt(pos + 1);
        if (ch1 < '0' || ch1 > '9' || ch2 < '0' || ch2 > '9') {
            throw new DateTimeException("Invalid ID for ZoneOffset, non numeric characters found: " + offsetId);
        }
        return (ch1 - 48) * 10 + (ch2 - 48);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZoneOffset} using an offset in hours.
     *
     * <p>
     *  使用小时内的偏移获取{@code ZoneOffset}的实例。
     * 
     * 
     * @param hours  the time-zone offset in hours, from -18 to +18
     * @return the zone-offset, not null
     * @throws DateTimeException if the offset is not in the required range
     */
    public static ZoneOffset ofHours(int hours) {
        return ofHoursMinutesSeconds(hours, 0, 0);
    }

    /**
     * Obtains an instance of {@code ZoneOffset} using an offset in
     * hours and minutes.
     * <p>
     * The sign of the hours and minutes components must match.
     * Thus, if the hours is negative, the minutes must be negative or zero.
     * If the hours is zero, the minutes may be positive, negative or zero.
     *
     * <p>
     *  使用小时和分钟的偏移量获取{@code ZoneOffset}的实例。
     * <p>
     *  小时和分钟组件的符号必须匹配。因此,如果小时为负,分钟必须为负或零。如果小时为零,分钟可以为正,负或零。
     * 
     * 
     * @param hours  the time-zone offset in hours, from -18 to +18
     * @param minutes  the time-zone offset in minutes, from 0 to &plusmn;59, sign matches hours
     * @return the zone-offset, not null
     * @throws DateTimeException if the offset is not in the required range
     */
    public static ZoneOffset ofHoursMinutes(int hours, int minutes) {
        return ofHoursMinutesSeconds(hours, minutes, 0);
    }

    /**
     * Obtains an instance of {@code ZoneOffset} using an offset in
     * hours, minutes and seconds.
     * <p>
     * The sign of the hours, minutes and seconds components must match.
     * Thus, if the hours is negative, the minutes and seconds must be negative or zero.
     *
     * <p>
     *  使用以小时,分钟和秒为单位的偏移量获取{@code ZoneOffset}的实例。
     * <p>
     *  小时,分钟和秒组件的符号必须匹配。因此,如果小时为负,则分钟和秒必须为负或零。
     * 
     * 
     * @param hours  the time-zone offset in hours, from -18 to +18
     * @param minutes  the time-zone offset in minutes, from 0 to &plusmn;59, sign matches hours and seconds
     * @param seconds  the time-zone offset in seconds, from 0 to &plusmn;59, sign matches hours and minutes
     * @return the zone-offset, not null
     * @throws DateTimeException if the offset is not in the required range
     */
    public static ZoneOffset ofHoursMinutesSeconds(int hours, int minutes, int seconds) {
        validate(hours, minutes, seconds);
        int totalSeconds = totalSeconds(hours, minutes, seconds);
        return ofTotalSeconds(totalSeconds);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZoneOffset} from a temporal object.
     * <p>
     * This obtains an offset based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ZoneOffset}.
     * <p>
     * A {@code TemporalAccessor} represents some form of date and time information.
     * This factory converts the arbitrary temporal object to an instance of {@code ZoneOffset}.
     * <p>
     * The conversion uses the {@link TemporalQueries#offset()} query, which relies
     * on extracting the {@link ChronoField#OFFSET_SECONDS OFFSET_SECONDS} field.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code ZoneOffset::from}.
     *
     * <p>
     *  从临时对象获取{@code ZoneOffset}的实例。
     * <p>
     * 这基于指定的时间获得偏移。 {@code TemporalAccessor}表示一组任意的日期和时间信息,该工厂转换为{@code ZoneOffset}的实例。
     * <p>
     *  {@code TemporalAccessor}表示某种形式的日期和时间信息。此工厂将任意临时对象转换为{@code ZoneOffset}的实例。
     * <p>
     *  转换使用{@link TemporalQueries#offset()}查询,该查询依赖于提取{@link ChronoField#OFFSET_SECONDS OFFSET_SECONDS}字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许通过方法引用{@code ZoneOffset :: from}在查询中使用它。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the zone-offset, not null
     * @throws DateTimeException if unable to convert to an {@code ZoneOffset}
     */
    public static ZoneOffset from(TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        ZoneOffset offset = temporal.query(TemporalQueries.offset());
        if (offset == null) {
            throw new DateTimeException("Unable to obtain ZoneOffset from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName());
        }
        return offset;
    }

    //-----------------------------------------------------------------------
    /**
     * Validates the offset fields.
     *
     * <p>
     *  验证偏移字段。
     * 
     * 
     * @param hours  the time-zone offset in hours, from -18 to +18
     * @param minutes  the time-zone offset in minutes, from 0 to &plusmn;59
     * @param seconds  the time-zone offset in seconds, from 0 to &plusmn;59
     * @throws DateTimeException if the offset is not in the required range
     */
    private static void validate(int hours, int minutes, int seconds) {
        if (hours < -18 || hours > 18) {
            throw new DateTimeException("Zone offset hours not in valid range: value " + hours +
                    " is not in the range -18 to 18");
        }
        if (hours > 0) {
            if (minutes < 0 || seconds < 0) {
                throw new DateTimeException("Zone offset minutes and seconds must be positive because hours is positive");
            }
        } else if (hours < 0) {
            if (minutes > 0 || seconds > 0) {
                throw new DateTimeException("Zone offset minutes and seconds must be negative because hours is negative");
            }
        } else if ((minutes > 0 && seconds < 0) || (minutes < 0 && seconds > 0)) {
            throw new DateTimeException("Zone offset minutes and seconds must have the same sign");
        }
        if (Math.abs(minutes) > 59) {
            throw new DateTimeException("Zone offset minutes not in valid range: abs(value) " +
                    Math.abs(minutes) + " is not in the range 0 to 59");
        }
        if (Math.abs(seconds) > 59) {
            throw new DateTimeException("Zone offset seconds not in valid range: abs(value) " +
                    Math.abs(seconds) + " is not in the range 0 to 59");
        }
        if (Math.abs(hours) == 18 && (Math.abs(minutes) > 0 || Math.abs(seconds) > 0)) {
            throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
        }
    }

    /**
     * Calculates the total offset in seconds.
     *
     * <p>
     *  计算总偏移量(以秒为单位)。
     * 
     * 
     * @param hours  the time-zone offset in hours, from -18 to +18
     * @param minutes  the time-zone offset in minutes, from 0 to &plusmn;59, sign matches hours and seconds
     * @param seconds  the time-zone offset in seconds, from 0 to &plusmn;59, sign matches hours and minutes
     * @return the total in seconds
     */
    private static int totalSeconds(int hours, int minutes, int seconds) {
        return hours * SECONDS_PER_HOUR + minutes * SECONDS_PER_MINUTE + seconds;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ZoneOffset} specifying the total offset in seconds
     * <p>
     * The offset must be in the range {@code -18:00} to {@code +18:00}, which corresponds to -64800 to +64800.
     *
     * <p>
     *  获取{@code ZoneOffset}的实例,以秒为单位指定总偏移量
     * <p>
     *  偏移必须在{@code -18：00}到{@code +18：00}的范围内,对应于-64800到+64800。
     * 
     * 
     * @param totalSeconds  the total time-zone offset in seconds, from -64800 to +64800
     * @return the ZoneOffset, not null
     * @throws DateTimeException if the offset is not in the required range
     */
    public static ZoneOffset ofTotalSeconds(int totalSeconds) {
        if (Math.abs(totalSeconds) > MAX_SECONDS) {
            throw new DateTimeException("Zone offset not in valid range: -18:00 to +18:00");
        }
        if (totalSeconds % (15 * SECONDS_PER_MINUTE) == 0) {
            Integer totalSecs = totalSeconds;
            ZoneOffset result = SECONDS_CACHE.get(totalSecs);
            if (result == null) {
                result = new ZoneOffset(totalSeconds);
                SECONDS_CACHE.putIfAbsent(totalSecs, result);
                result = SECONDS_CACHE.get(totalSecs);
                ID_CACHE.putIfAbsent(result.getId(), result);
            }
            return result;
        } else {
            return new ZoneOffset(totalSeconds);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * <p>
     *  构造函数。
     * 
     * 
     * @param totalSeconds  the total time-zone offset in seconds, from -64800 to +64800
     */
    private ZoneOffset(int totalSeconds) {
        super();
        this.totalSeconds = totalSeconds;
        id = buildId(totalSeconds);
    }

    private static String buildId(int totalSeconds) {
        if (totalSeconds == 0) {
            return "Z";
        } else {
            int absTotalSeconds = Math.abs(totalSeconds);
            StringBuilder buf = new StringBuilder();
            int absHours = absTotalSeconds / SECONDS_PER_HOUR;
            int absMinutes = (absTotalSeconds / SECONDS_PER_MINUTE) % MINUTES_PER_HOUR;
            buf.append(totalSeconds < 0 ? "-" : "+")
                .append(absHours < 10 ? "0" : "").append(absHours)
                .append(absMinutes < 10 ? ":0" : ":").append(absMinutes);
            int absSeconds = absTotalSeconds % SECONDS_PER_MINUTE;
            if (absSeconds != 0) {
                buf.append(absSeconds < 10 ? ":0" : ":").append(absSeconds);
            }
            return buf.toString();
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the total zone offset in seconds.
     * <p>
     * This is the primary way to access the offset amount.
     * It returns the total of the hours, minutes and seconds fields as a
     * single offset that can be added to a time.
     *
     * <p>
     *  获取总区域偏移量(以秒为单位)。
     * <p>
     *  这是访问偏移量的主要方式。它将小时,分钟和秒字段的总和返回为可以添加到时间的单个偏移量。
     * 
     * 
     * @return the total zone offset amount in seconds
     */
    public int getTotalSeconds() {
        return totalSeconds;
    }

    /**
     * Gets the normalized zone offset ID.
     * <p>
     * The ID is minor variation to the standard ISO-8601 formatted string
     * for the offset. There are three formats:
     * <ul>
     * <li>{@code Z} - for UTC (ISO-8601)
     * <li>{@code +hh:mm} or {@code -hh:mm} - if the seconds are zero (ISO-8601)
     * <li>{@code +hh:mm:ss} or {@code -hh:mm:ss} - if the seconds are non-zero (not ISO-8601)
     * </ul>
     *
     * <p>
     *  获取归一化区域偏移ID。
     * <p>
     *  ID是对偏移量的标准ISO-8601格式化字符串的微小变化。有三种格式：
     * <ul>
     * <li> {@ code Z}  - 对于UTC(ISO-8601)<li> {@ code + hh：mm}或{@code -hh：mm} {@code + hh：mm：ss}或{@code -hh：mm：ss}
     *   - 如果秒数不为零(不是ISO-8601)。
     * </ul>
     * 
     * 
     * @return the zone offset ID, not null
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Gets the associated time-zone rules.
     * <p>
     * The rules will always return this offset when queried.
     * The implementation class is immutable, thread-safe and serializable.
     *
     * <p>
     *  获取关联的时区规则。
     * <p>
     *  当查询时,规则将总是返回此偏移量。实现类是不可变的,线程安全的和可序列化的。
     * 
     * 
     * @return the rules, not null
     */
    @Override
    public ZoneRules getRules() {
        return ZoneRules.of(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this offset can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range} and
     * {@link #get(TemporalField) get} methods will throw an exception.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@code OFFSET_SECONDS} field returns true.
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
     *  这将检查是否可以查询指定字段的偏移量。
     * 如果为false,则调用{@link #range(TemporalField)range}和{@link #get(TemporalField)get}方法将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@code OFFSET_SECONDS}字段返回true。所有其他{@code ChronoField}实例将返回false。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this offset, false if not
     */
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field == OFFSET_SECONDS;
        }
        return field != null && field.isSupportedBy(this);
    }

    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This offset is used to enhance the accuracy of the returned range.
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
     *  范围对象表示字段的最小和最大有效值。此偏移用于提高返回范围的精度。如果不可能返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
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
    @Override  // override for Javadoc
    public ValueRange range(TemporalField field) {
        return TemporalAccessor.super.range(field);
    }

    /**
     * Gets the value of the specified field from this offset as an {@code int}.
     * <p>
     * This queries this offset for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@code OFFSET_SECONDS} field returns the value of the offset.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此偏移获取指定字段的值为{@code int}。
     * <p>
     *  这将为指定字段的值查询此偏移量。返回的值将始终在字段的有效值范围内。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@code OFFSET_SECONDS}字段返回偏移量的值。
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
    @Override  // override for Javadoc and performance
    public int get(TemporalField field) {
        if (field == OFFSET_SECONDS) {
            return totalSeconds;
        } else if (field instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return range(field).checkValidIntValue(getLong(field), field);
    }

    /**
     * Gets the value of the specified field from this offset as a {@code long}.
     * <p>
     * This queries this offset for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@code OFFSET_SECONDS} field returns the value of the offset.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此偏移获取指定字段的值为{@code long}。
     * <p>
     * 这将为指定字段的值查询此偏移量。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此执行查询。 {@code OFFSET_SECONDS}字段返回偏移量的值。
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
        if (field == OFFSET_SECONDS) {
            return totalSeconds;
        } else if (field instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.getFrom(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this offset using the specified query.
     * <p>
     * This queries this offset using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此偏移量。
     * <p>
     *  这将使用指定的查询策略对象查询此偏移量。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
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
            return (R) this;
        }
        return TemporalAccessor.super.query(query);
    }

    /**
     * Adjusts the specified temporal object to have the same offset as this object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the offset changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * passing {@link ChronoField#OFFSET_SECONDS} as the field.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisOffset.adjustInto(temporal);
     *   temporal = temporal.with(thisOffset);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为具有与此对象相同的偏移量。
     * <p>
     *  这返回一个与输入相同的observable类型的时间对象,其偏移量更改为与此相同。
     * <p>
     * 该调整等同于使用{@link Temporal#with(TemporalField,long)}传递{@link ChronoField#OFFSET_SECONDS}作为字段。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等价的,但第二种方法是推荐temporal = thisOffset.adjustInto(temporal); temporal = temporal.with(thisOffset);
     * 。
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
        return temporal.with(OFFSET_SECONDS, totalSeconds);
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this offset to another offset in descending order.
     * <p>
     * The offsets are compared in the order that they occur for the same time
     * of day around the world. Thus, an offset of {@code +10:00} comes before an
     * offset of {@code +09:00} and so on down to {@code -18:00}.
     * <p>
     * The comparison is "consistent with equals", as defined by {@link Comparable}.
     *
     * <p>
     *  将此偏移量与降序的另一个偏移量进行比较。
     * <p>
     *  按照它们在世界上相同的时间发生的顺序来比较偏移。因此,{@code +10：00}的偏移量在{@code +09：00}的偏移量之前,直到{@code -18：00}。
     * <p>
     *  比较是"与等号一致",由{@ link Comparable}定义。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return the comparator value, negative if less, postive if greater
     * @throws NullPointerException if {@code other} is null
     */
    @Override
    public int compareTo(ZoneOffset other) {
        return other.totalSeconds - totalSeconds;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this offset is equal to another offset.
     * <p>
     * The comparison is based on the amount of the offset in seconds.
     * This is equivalent to a comparison by ID.
     *
     * <p>
     *  检查这个偏移是否等于另一个偏移。
     * <p>
     *  比较基于偏移量(以秒为单位)。这相当于ID的比较。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other offset
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
           return true;
        }
        if (obj instanceof ZoneOffset) {
            return totalSeconds == ((ZoneOffset) obj).totalSeconds;
        }
        return false;
    }

    /**
     * A hash code for this offset.
     *
     * <p>
     *  此偏移量的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return totalSeconds;
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this offset as a {@code String}, using the normalized ID.
     *
     * <p>
     *  使用规范化的ID将此偏移量输出为{@code String}。
     * 
     * 
     * @return a string representation of this offset, not null
     */
    @Override
    public String toString() {
        return id;
    }

    // -----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     *  使用<a href="../../serialized-form.html#java.time.Ser">专用序列化表单</a>写入对象。
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(8);                  // identifies a ZoneOffset
     *  int offsetByte = totalSeconds % 900 == 0 ? totalSeconds / 900 : 127;
     *  out.writeByte(offsetByte);
     *  if (offsetByte == 127) {
     *      out.writeInt(totalSeconds);
     *  }
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.ZONE_OFFSET_TYPE, this);
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

    @Override
    void write(DataOutput out) throws IOException {
        out.writeByte(Ser.ZONE_OFFSET_TYPE);
        writeExternal(out);
    }

    void writeExternal(DataOutput out) throws IOException {
        final int offsetSecs = totalSeconds;
        int offsetByte = offsetSecs % 900 == 0 ? offsetSecs / 900 : 127;  // compress to -72 to +72
        out.writeByte(offsetByte);
        if (offsetByte == 127) {
            out.writeInt(offsetSecs);
        }
    }

    static ZoneOffset readExternal(DataInput in) throws IOException {
        int offsetByte = in.readByte();
        return (offsetByte == 127 ? ZoneOffset.ofTotalSeconds(in.readInt()) : ZoneOffset.ofTotalSeconds(offsetByte * 900));
    }

}
