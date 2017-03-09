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

import static java.time.LocalTime.NANOS_PER_SECOND;
import static java.time.LocalTime.SECONDS_PER_DAY;
import static java.time.LocalTime.SECONDS_PER_HOUR;
import static java.time.LocalTime.SECONDS_PER_MINUTE;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A time-based amount of time, such as '34.5 seconds'.
 * <p>
 * This class models a quantity or amount of time in terms of seconds and nanoseconds.
 * It can be accessed using other duration-based units, such as minutes and hours.
 * In addition, the {@link ChronoUnit#DAYS DAYS} unit can be used and is treated as
 * exactly equal to 24 hours, thus ignoring daylight savings effects.
 * See {@link Period} for the date-based equivalent to this class.
 * <p>
 * A physical duration could be of infinite length.
 * For practicality, the duration is stored with constraints similar to {@link Instant}.
 * The duration uses nanosecond resolution with a maximum value of the seconds that can
 * be held in a {@code long}. This is greater than the current estimated age of the universe.
 * <p>
 * The range of a duration requires the storage of a number larger than a {@code long}.
 * To achieve this, the class stores a {@code long} representing seconds and an {@code int}
 * representing nanosecond-of-second, which will always be between 0 and 999,999,999.
 * The model is of a directed duration, meaning that the duration may be negative.
 * <p>
 * The duration is measured in "seconds", but these are not necessarily identical to
 * the scientific "SI second" definition based on atomic clocks.
 * This difference only impacts durations measured near a leap-second and should not affect
 * most applications.
 * See {@link Instant} for a discussion as to the meaning of the second and time-scales.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code Duration} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  基于时间的时间量,例如"34.5秒"。
 * <p>
 *  该类以秒和纳秒为单位建模时间的数量或时间量。它可以使用其他基于持续时间的单位进行访问,例如分钟和小时。
 * 此外,{@link ChronoUnit#DAYS DAYS}单位可以使用,并且被视为完全等于24小时,因此忽略了夏令时效果。请参阅{@link Period}以等效于此类别的日期。
 * <p>
 *  物理持续时间可以是无限长度。为了实用性,持续时间与类似于{@link Instant}的约束一起存储。持续时间使用纳秒分辨率,在{@code long}中可以保持的最大值为秒。
 * 这大于当前估计的宇宙年龄。
 * <p>
 * 持续时间的范围需要存储大于{@code long}的数字。为了实现这一点,类存储代表秒的{@code long}和代表秒的纳秒的{@code int},它将始终在0和999,999,999之间。
 * 该模型是有向持续时间,意味着持续时间可以是否定的。
 * <p>
 *  持续时间以"秒"测量,但这些不一定与基于原子钟的科学"SI秒"定义相同。该差异仅影响在闰秒附近测量的持续时间,并且不应影响大多数应用。
 * 有关第二个和时间范围的含义的讨论,请参阅{@link Instant}。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code Duration}的实例上使用身份敏感操
 * 作(包括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class Duration
        implements TemporalAmount, Comparable<Duration>, Serializable {

    /**
     * Constant for a duration of zero.
     * <p>
     *  持续时间为零的常数。
     * 
     */
    public static final Duration ZERO = new Duration(0, 0);
    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 3078945930695997490L;
    /**
     * Constant for nanos per second.
     * <p>
     *  常数为每秒纳秒。
     * 
     */
    private static final BigInteger BI_NANOS_PER_SECOND = BigInteger.valueOf(NANOS_PER_SECOND);
    /**
     * The pattern for parsing.
     * <p>
     *  解析模式。
     * 
     */
    private static final Pattern PATTERN =
            Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)D)?" +
                    "(T(?:([-+]?[0-9]+)H)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)(?:[.,]([0-9]{0,9}))?S)?)?",
                    Pattern.CASE_INSENSITIVE);

    /**
     * The number of seconds in the duration.
     * <p>
     *  持续时间中的秒数。
     * 
     */
    private final long seconds;
    /**
     * The number of nanoseconds in the duration, expressed as a fraction of the
     * number of seconds. This is always positive, and never exceeds 999,999,999.
     * <p>
     *  持续时间中的纳秒数,表示为秒数的分数。这总是积极的,从不超过999,999,999。
     * 
     */
    private final int nanos;

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Duration} representing a number of standard 24 hour days.
     * <p>
     * The seconds are calculated based on the standard definition of a day,
     * where each day is 86400 seconds which implies a 24 hour day.
     * The nanosecond in second field is set to zero.
     *
     * <p>
     *  获取表示标准24小时天数的{@code Duration}。
     * <p>
     * 秒是基于一天的标准定义计算的,其中每天是86400秒,这意味着24小时的天。第二场中的纳秒设置为零。
     * 
     * 
     * @param days  the number of days, positive or negative
     * @return a {@code Duration}, not null
     * @throws ArithmeticException if the input days exceeds the capacity of {@code Duration}
     */
    public static Duration ofDays(long days) {
        return create(Math.multiplyExact(days, SECONDS_PER_DAY), 0);
    }

    /**
     * Obtains a {@code Duration} representing a number of standard hours.
     * <p>
     * The seconds are calculated based on the standard definition of an hour,
     * where each hour is 3600 seconds.
     * The nanosecond in second field is set to zero.
     *
     * <p>
     *  获取表示标准小时数的{@code Duration}。
     * <p>
     *  秒基于一小时的标准定义计算,其中每小时为3600秒。第二场中的纳秒设置为零。
     * 
     * 
     * @param hours  the number of hours, positive or negative
     * @return a {@code Duration}, not null
     * @throws ArithmeticException if the input hours exceeds the capacity of {@code Duration}
     */
    public static Duration ofHours(long hours) {
        return create(Math.multiplyExact(hours, SECONDS_PER_HOUR), 0);
    }

    /**
     * Obtains a {@code Duration} representing a number of standard minutes.
     * <p>
     * The seconds are calculated based on the standard definition of a minute,
     * where each minute is 60 seconds.
     * The nanosecond in second field is set to zero.
     *
     * <p>
     *  获取表示标准分钟数的{@code Duration}。
     * <p>
     *  秒基于一分钟的标准定义计算,其中每分钟为60秒。第二场中的纳秒设置为零。
     * 
     * 
     * @param minutes  the number of minutes, positive or negative
     * @return a {@code Duration}, not null
     * @throws ArithmeticException if the input minutes exceeds the capacity of {@code Duration}
     */
    public static Duration ofMinutes(long minutes) {
        return create(Math.multiplyExact(minutes, SECONDS_PER_MINUTE), 0);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Duration} representing a number of seconds.
     * <p>
     * The nanosecond in second field is set to zero.
     *
     * <p>
     *  获取表示秒数的{@code Duration}。
     * <p>
     *  第二场中的纳秒设置为零。
     * 
     * 
     * @param seconds  the number of seconds, positive or negative
     * @return a {@code Duration}, not null
     */
    public static Duration ofSeconds(long seconds) {
        return create(seconds, 0);
    }

    /**
     * Obtains a {@code Duration} representing a number of seconds and an
     * adjustment in nanoseconds.
     * <p>
     * This method allows an arbitrary number of nanoseconds to be passed in.
     * The factory will alter the values of the second and nanosecond in order
     * to ensure that the stored nanosecond is in the range 0 to 999,999,999.
     * For example, the following will result in the exactly the same duration:
     * <pre>
     *  Duration.ofSeconds(3, 1);
     *  Duration.ofSeconds(4, -999_999_999);
     *  Duration.ofSeconds(2, 1000_000_001);
     * </pre>
     *
     * <p>
     *  获取表示秒数的{@code Duration}和以纳秒为单位的调整。
     * <p>
     *  此方法允许传入任意数量的纳秒。工厂将改变第二和纳秒的值,以确保存储的纳秒在0至999,999,999的范围内。例如,以下将导致完全相同的持续时间：
     * <pre>
     *  持续时间(3,1); Duration.ofSeconds(4,-999_999_999); Duration.ofSeconds(2,1000_000_001);
     * </pre>
     * 
     * 
     * @param seconds  the number of seconds, positive or negative
     * @param nanoAdjustment  the nanosecond adjustment to the number of seconds, positive or negative
     * @return a {@code Duration}, not null
     * @throws ArithmeticException if the adjustment causes the seconds to exceed the capacity of {@code Duration}
     */
    public static Duration ofSeconds(long seconds, long nanoAdjustment) {
        long secs = Math.addExact(seconds, Math.floorDiv(nanoAdjustment, NANOS_PER_SECOND));
        int nos = (int) Math.floorMod(nanoAdjustment, NANOS_PER_SECOND);
        return create(secs, nos);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Duration} representing a number of milliseconds.
     * <p>
     * The seconds and nanoseconds are extracted from the specified milliseconds.
     *
     * <p>
     *  获取表示毫秒数的{@code Duration}。
     * <p>
     *  秒和纳秒是从指定的毫秒提取的。
     * 
     * 
     * @param millis  the number of milliseconds, positive or negative
     * @return a {@code Duration}, not null
     */
    public static Duration ofMillis(long millis) {
        long secs = millis / 1000;
        int mos = (int) (millis % 1000);
        if (mos < 0) {
            mos += 1000;
            secs--;
        }
        return create(secs, mos * 1000_000);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Duration} representing a number of nanoseconds.
     * <p>
     * The seconds and nanoseconds are extracted from the specified nanoseconds.
     *
     * <p>
     *  获取表示纳秒数的{@code Duration}。
     * <p>
     * 秒和纳秒是从指定的纳秒提取的。
     * 
     * 
     * @param nanos  the number of nanoseconds, positive or negative
     * @return a {@code Duration}, not null
     */
    public static Duration ofNanos(long nanos) {
        long secs = nanos / NANOS_PER_SECOND;
        int nos = (int) (nanos % NANOS_PER_SECOND);
        if (nos < 0) {
            nos += NANOS_PER_SECOND;
            secs--;
        }
        return create(secs, nos);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Duration} representing an amount in the specified unit.
     * <p>
     * The parameters represent the two parts of a phrase like '6 Hours'. For example:
     * <pre>
     *  Duration.of(3, SECONDS);
     *  Duration.of(465, HOURS);
     * </pre>
     * Only a subset of units are accepted by this method.
     * The unit must either have an {@linkplain TemporalUnit#isDurationEstimated() exact duration} or
     * be {@link ChronoUnit#DAYS} which is treated as 24 hours. Other units throw an exception.
     *
     * <p>
     *  获取{@code Duration}表示指定单位中的金额。
     * <p>
     *  参数表示短语的两个部分,如"6小时"。例如：
     * <pre>
     *  Duration.of(3,SECONDS);持续时间(465,小时);
     * </pre>
     *  此方法仅接受单元的子集。
     * 单位必须具有{@linkplain TemporalUnit#isDurationEstimated()精确持续时间}或{@link ChronoUnit#DAYS},该时间被视为24小时。
     * 其他单位抛出异常。
     * 
     * 
     * @param amount  the amount of the duration, measured in terms of the unit, positive or negative
     * @param unit  the unit that the duration is measured in, must have an exact duration, not null
     * @return a {@code Duration}, not null
     * @throws DateTimeException if the period unit has an estimated duration
     * @throws ArithmeticException if a numeric overflow occurs
     */
    public static Duration of(long amount, TemporalUnit unit) {
        return ZERO.plus(amount, unit);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Duration} from a temporal amount.
     * <p>
     * This obtains a duration based on the specified amount.
     * A {@code TemporalAmount} represents an  amount of time, which may be
     * date-based or time-based, which this factory extracts to a duration.
     * <p>
     * The conversion loops around the set of units from the amount and uses
     * the {@linkplain TemporalUnit#getDuration() duration} of the unit to
     * calculate the total {@code Duration}.
     * Only a subset of units are accepted by this method. The unit must either
     * have an {@linkplain TemporalUnit#isDurationEstimated() exact duration}
     * or be {@link ChronoUnit#DAYS} which is treated as 24 hours.
     * If any other units are found then an exception is thrown.
     *
     * <p>
     *  从时间量中获取{@code Duration}的实例。
     * <p>
     *  这获得基于指定量的持续时间。 {@code TemporalAmount}表示时间量,其可以是基于日期的或基于时间的,该工厂提取到持续时间。
     * <p>
     *  转换循环单元的集合,并使用单元的{@linkplain TemporalUnit#getDuration()duration}计算总{@code Duration}。此方法仅接受单元的子集。
     * 单位必须具有{@linkplain TemporalUnit#isDurationEstimated()精确持续时间}或{@link ChronoUnit#DAYS},该时间被视为24小时。
     * 如果发现任何其他单位,则抛出异常。
     * 
     * 
     * @param amount  the temporal amount to convert, not null
     * @return the equivalent duration, not null
     * @throws DateTimeException if unable to convert to a {@code Duration}
     * @throws ArithmeticException if numeric overflow occurs
     */
    public static Duration from(TemporalAmount amount) {
        Objects.requireNonNull(amount, "amount");
        Duration duration = ZERO;
        for (TemporalUnit unit : amount.getUnits()) {
            duration = duration.plus(amount.get(unit), unit);
        }
        return duration;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Duration} from a text string such as {@code PnDTnHnMn.nS}.
     * <p>
     * This will parse a textual representation of a duration, including the
     * string produced by {@code toString()}. The formats accepted are based
     * on the ISO-8601 duration format {@code PnDTnHnMn.nS} with days
     * considered to be exactly 24 hours.
     * <p>
     * The string starts with an optional sign, denoted by the ASCII negative
     * or positive symbol. If negative, the whole period is negated.
     * The ASCII letter "P" is next in upper or lower case.
     * There are then four sections, each consisting of a number and a suffix.
     * The sections have suffixes in ASCII of "D", "H", "M" and "S" for
     * days, hours, minutes and seconds, accepted in upper or lower case.
     * The suffixes must occur in order. The ASCII letter "T" must occur before
     * the first occurrence, if any, of an hour, minute or second section.
     * At least one of the four sections must be present, and if "T" is present
     * there must be at least one section after the "T".
     * The number part of each section must consist of one or more ASCII digits.
     * The number may be prefixed by the ASCII negative or positive symbol.
     * The number of days, hours and minutes must parse to an {@code long}.
     * The number of seconds must parse to an {@code long} with optional fraction.
     * The decimal point may be either a dot or a comma.
     * The fractional part may have from zero to 9 digits.
     * <p>
     * The leading plus/minus sign, and negative values for other units are
     * not part of the ISO-8601 standard.
     * <p>
     * Examples:
     * <pre>
     *    "PT20.345S" -- parses as "20.345 seconds"
     *    "PT15M"     -- parses as "15 minutes" (where a minute is 60 seconds)
     *    "PT10H"     -- parses as "10 hours" (where an hour is 3600 seconds)
     *    "P2D"       -- parses as "2 days" (where a day is 24 hours or 86400 seconds)
     *    "P2DT3H4M"  -- parses as "2 days, 3 hours and 4 minutes"
     *    "P-6H3M"    -- parses as "-6 hours and +3 minutes"
     *    "-P6H3M"    -- parses as "-6 hours and -3 minutes"
     *    "-P-6H+3M"  -- parses as "+6 hours and -3 minutes"
     * </pre>
     *
     * <p>
     *  从文本字符串(例如{@code PnDTnHnMn.nS})获取{@code Duration}。
     * <p>
     * 这将解析持续时间的文本表示,包括{@code toString()}生成的字符串。接受的格式基于ISO-8601持续时间格式{@code PnDTnHnMn.nS},其天数被认为是24小时。
     * <p>
     *  字符串以可选符号开始,由ASCII负或正符号表示。如果为负,则整个周期被否定。 ASCII字母"P"大写或小写。然后有四个部分,每个部分由数字和后缀组成。
     * 这些段具有ASCII,"D","H","M"和"S"的后缀,表示天,小时,分钟和秒,以大写或小写接受。后缀必须按顺序出现。
     *  ASCII字母"T"必须出现在小时,分钟或秒部分的第一次出现之前(如果有的话)。必须存在四个部分中的至少一个,并且如果存在"T",则在"T"之后必须存在至少一个部分。
     * 每个部分的编号部分必须由一个或多个ASCII数字组成。数字可以以ASCII负或正符号为前缀。天数,小时数和分钟数必须解析为{@code long}。
     * 秒数必须解析为带有可选分数的{@code long}。小数点可以是点或逗号。小数部分可以具有从零到9个数字。
     * <p>
     *  前导加号/减号和其他单位的负值不是ISO-8601标准的一部分。
     * <p>
     *  例子：
     * <pre>
     * "PT20.345S" - 解析为"20.345秒""PT15M" - 解析为"15分钟"(分钟为60秒)"PT10H" - 解析为"10小时" )"P2D" - 解析为"2天"(其中一天为24小时或8
     * 6400秒)"P2DT3H4M" - 解析为"2天,3小时和4分钟""P-6H3M" - 解析为" -6小时和+3分钟"-P6H3M"解析为"-6小时和-3分钟""-P-6H + 3M"解析为"+ 6小
     * 时和-3分钟"。
     * </pre>
     * 
     * 
     * @param text  the text to parse, not null
     * @return the parsed duration, not null
     * @throws DateTimeParseException if the text cannot be parsed to a duration
     */
    public static Duration parse(CharSequence text) {
        Objects.requireNonNull(text, "text");
        Matcher matcher = PATTERN.matcher(text);
        if (matcher.matches()) {
            // check for letter T but no time sections
            if ("T".equals(matcher.group(3)) == false) {
                boolean negate = "-".equals(matcher.group(1));
                String dayMatch = matcher.group(2);
                String hourMatch = matcher.group(4);
                String minuteMatch = matcher.group(5);
                String secondMatch = matcher.group(6);
                String fractionMatch = matcher.group(7);
                if (dayMatch != null || hourMatch != null || minuteMatch != null || secondMatch != null) {
                    long daysAsSecs = parseNumber(text, dayMatch, SECONDS_PER_DAY, "days");
                    long hoursAsSecs = parseNumber(text, hourMatch, SECONDS_PER_HOUR, "hours");
                    long minsAsSecs = parseNumber(text, minuteMatch, SECONDS_PER_MINUTE, "minutes");
                    long seconds = parseNumber(text, secondMatch, 1, "seconds");
                    int nanos = parseFraction(text,  fractionMatch, seconds < 0 ? -1 : 1);
                    try {
                        return create(negate, daysAsSecs, hoursAsSecs, minsAsSecs, seconds, nanos);
                    } catch (ArithmeticException ex) {
                        throw (DateTimeParseException) new DateTimeParseException("Text cannot be parsed to a Duration: overflow", text, 0).initCause(ex);
                    }
                }
            }
        }
        throw new DateTimeParseException("Text cannot be parsed to a Duration", text, 0);
    }

    private static long parseNumber(CharSequence text, String parsed, int multiplier, String errorText) {
        // regex limits to [-+]?[0-9]+
        if (parsed == null) {
            return 0;
        }
        try {
            long val = Long.parseLong(parsed);
            return Math.multiplyExact(val, multiplier);
        } catch (NumberFormatException | ArithmeticException ex) {
            throw (DateTimeParseException) new DateTimeParseException("Text cannot be parsed to a Duration: " + errorText, text, 0).initCause(ex);
        }
    }

    private static int parseFraction(CharSequence text, String parsed, int negate) {
        // regex limits to [0-9]{0,9}
        if (parsed == null || parsed.length() == 0) {
            return 0;
        }
        try {
            parsed = (parsed + "000000000").substring(0, 9);
            return Integer.parseInt(parsed) * negate;
        } catch (NumberFormatException | ArithmeticException ex) {
            throw (DateTimeParseException) new DateTimeParseException("Text cannot be parsed to a Duration: fraction", text, 0).initCause(ex);
        }
    }

    private static Duration create(boolean negate, long daysAsSecs, long hoursAsSecs, long minsAsSecs, long secs, int nanos) {
        long seconds = Math.addExact(daysAsSecs, Math.addExact(hoursAsSecs, Math.addExact(minsAsSecs, secs)));
        if (negate) {
            return ofSeconds(seconds, nanos).negated();
        }
        return ofSeconds(seconds, nanos);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Duration} representing the duration between two temporal objects.
     * <p>
     * This calculates the duration between two temporal objects. If the objects
     * are of different types, then the duration is calculated based on the type
     * of the first object. For example, if the first argument is a {@code LocalTime}
     * then the second argument is converted to a {@code LocalTime}.
     * <p>
     * The specified temporal objects must support the {@link ChronoUnit#SECONDS SECONDS} unit.
     * For full accuracy, either the {@link ChronoUnit#NANOS NANOS} unit or the
     * {@link ChronoField#NANO_OF_SECOND NANO_OF_SECOND} field should be supported.
     * <p>
     * The result of this method can be a negative period if the end is before the start.
     * To guarantee to obtain a positive duration call {@link #abs()} on the result.
     *
     * <p>
     *  获取表示两个时间对象之间的持续时间的{@code Duration}。
     * <p>
     *  这计算两个时间对象之间的持续时间。如果对象具有不同类型,则基于第一对象的类型来计算持续时间。
     * 例如,如果第一个参数是{@code LocalTime},那么第二个参数被转换为{@code LocalTime}。
     * <p>
     *  指定的时态对象必须支持{@link ChronoUnit#SECONDS SECONDS}单位。
     * 为了完全准确,应支持{@link ChronoUnit#NANOS NANOS}单位或{@link ChronoField#NANO_OF_SECOND NANO_OF_SECOND}字段。
     * <p>
     *  如果结束在开始之前,此方法的结果可以是负期间。为了保证获得正持续时间,请对结果调用{@link #abs()}。
     * 
     * 
     * @param startInclusive  the start instant, inclusive, not null
     * @param endExclusive  the end instant, exclusive, not null
     * @return a {@code Duration}, not null
     * @throws DateTimeException if the seconds between the temporals cannot be obtained
     * @throws ArithmeticException if the calculation exceeds the capacity of {@code Duration}
     */
    public static Duration between(Temporal startInclusive, Temporal endExclusive) {
        try {
            return ofNanos(startInclusive.until(endExclusive, NANOS));
        } catch (DateTimeException | ArithmeticException ex) {
            long secs = startInclusive.until(endExclusive, SECONDS);
            long nanos;
            try {
                nanos = endExclusive.getLong(NANO_OF_SECOND) - startInclusive.getLong(NANO_OF_SECOND);
                if (secs > 0 && nanos < 0) {
                    secs++;
                } else if (secs < 0 && nanos > 0) {
                    secs--;
                }
            } catch (DateTimeException ex2) {
                nanos = 0;
            }
            return ofSeconds(secs, nanos);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Duration} using seconds and nanoseconds.
     *
     * <p>
     *  使用秒和纳秒获取{@code Duration}的实例。
     * 
     * 
     * @param seconds  the length of the duration in seconds, positive or negative
     * @param nanoAdjustment  the nanosecond adjustment within the second, from 0 to 999,999,999
     */
    private static Duration create(long seconds, int nanoAdjustment) {
        if ((seconds | nanoAdjustment) == 0) {
            return ZERO;
        }
        return new Duration(seconds, nanoAdjustment);
    }

    /**
     * Constructs an instance of {@code Duration} using seconds and nanoseconds.
     *
     * <p>
     *  使用秒和纳秒构造{@code Duration}的实例。
     * 
     * 
     * @param seconds  the length of the duration in seconds, positive or negative
     * @param nanos  the nanoseconds within the second, from 0 to 999,999,999
     */
    private Duration(long seconds, int nanos) {
        super();
        this.seconds = seconds;
        this.nanos = nanos;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value of the requested unit.
     * <p>
     * This returns a value for each of the two supported units,
     * {@link ChronoUnit#SECONDS SECONDS} and {@link ChronoUnit#NANOS NANOS}.
     * All other units throw an exception.
     *
     * <p>
     *  获取所请求单位的值。
     * <p>
     * 这会为两个受支持的单元{@link ChronoUnit#SECONDS SECONDS}和{@link ChronoUnit#NANOS NANOS}返回一个值。所有其他单位都会抛出异常。
     * 
     * 
     * @param unit the {@code TemporalUnit} for which to return the value
     * @return the long value of the unit
     * @throws DateTimeException if the unit is not supported
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     */
    @Override
    public long get(TemporalUnit unit) {
        if (unit == SECONDS) {
            return seconds;
        } else if (unit == NANOS) {
            return nanos;
        } else {
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
    }

    /**
     * Gets the set of units supported by this duration.
     * <p>
     * The supported units are {@link ChronoUnit#SECONDS SECONDS},
     * and {@link ChronoUnit#NANOS NANOS}.
     * They are returned in the order seconds, nanos.
     * <p>
     * This set can be used in conjunction with {@link #get(TemporalUnit)}
     * to access the entire state of the period.
     *
     * <p>
     *  获取此持续时间支持的单位集。
     * <p>
     *  支持的单位是{@link ChronoUnit#SECONDS SECONDS}和{@link ChronoUnit#NANOS NANOS}。它们以秒为单位返回,nanos。
     * <p>
     *  此集合可以与{@link #get(TemporalUnit)}结合使用以访问期间的整个状态。
     * 
     * 
     * @return a list containing the seconds and nanos units, not null
     */
    @Override
    public List<TemporalUnit> getUnits() {
        return DurationUnits.UNITS;
    }

    /**
     * Private class to delay initialization of this list until needed.
     * The circular dependency between Duration and ChronoUnit prevents
     * the simple initialization in Duration.
     * <p>
     *  私有类延迟此列表的初始化,直到需要。 Duration和ChronoUnit之间的循环依赖防止在Duration中进行简单初始化。
     * 
     */
    private static class DurationUnits {
        static final List<TemporalUnit> UNITS =
                Collections.unmodifiableList(Arrays.<TemporalUnit>asList(SECONDS, NANOS));
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this duration is zero length.
     * <p>
     * A {@code Duration} represents a directed distance between two points on
     * the time-line and can therefore be positive, zero or negative.
     * This method checks whether the length is zero.
     *
     * <p>
     *  检查此持续时间是否为零长度。
     * <p>
     *  {@code Duration}表示时间线上两点之间的有向距离,因此可以是正,零或负。此方法检查长度是否为零。
     * 
     * 
     * @return true if this duration has a total length equal to zero
     */
    public boolean isZero() {
        return (seconds | nanos) == 0;
    }

    /**
     * Checks if this duration is negative, excluding zero.
     * <p>
     * A {@code Duration} represents a directed distance between two points on
     * the time-line and can therefore be positive, zero or negative.
     * This method checks whether the length is less than zero.
     *
     * <p>
     *  检查此持续时间是否为负,不包括零。
     * <p>
     *  {@code Duration}表示时间线上两点之间的有向距离,因此可以是正,零或负。此方法检查长度是否小于零。
     * 
     * 
     * @return true if this duration has a total length less than zero
     */
    public boolean isNegative() {
        return seconds < 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the number of seconds in this duration.
     * <p>
     * The length of the duration is stored using two fields - seconds and nanoseconds.
     * The nanoseconds part is a value from 0 to 999,999,999 that is an adjustment to
     * the length in seconds.
     * The total duration is defined by calling this method and {@link #getNano()}.
     * <p>
     * A {@code Duration} represents a directed distance between two points on the time-line.
     * A negative duration is expressed by the negative sign of the seconds part.
     * A duration of -1 nanosecond is stored as -1 seconds plus 999,999,999 nanoseconds.
     *
     * <p>
     *  获取此持续时间中的秒数。
     * <p>
     *  持续时间的长度使用两个字段(秒和纳秒)存储。纳秒部分是从0到999,999,999的值,它是以秒为单位的长度调整。总持续时间通过调用此方法和{@link #getNano()}来定义。
     * <p>
     * {@code Duration}表示时间线上两点之间的有向距离。负的持续时间由秒部分的负号表示。 -1纳秒的持续时间被存储为-1秒加上999,999,999纳秒。
     * 
     * 
     * @return the whole seconds part of the length of the duration, positive or negative
     */
    public long getSeconds() {
        return seconds;
    }

    /**
     * Gets the number of nanoseconds within the second in this duration.
     * <p>
     * The length of the duration is stored using two fields - seconds and nanoseconds.
     * The nanoseconds part is a value from 0 to 999,999,999 that is an adjustment to
     * the length in seconds.
     * The total duration is defined by calling this method and {@link #getSeconds()}.
     * <p>
     * A {@code Duration} represents a directed distance between two points on the time-line.
     * A negative duration is expressed by the negative sign of the seconds part.
     * A duration of -1 nanosecond is stored as -1 seconds plus 999,999,999 nanoseconds.
     *
     * <p>
     *  获取此持续时间内的秒数内的纳秒数。
     * <p>
     *  持续时间的长度使用两个字段(秒和纳秒)存储。纳秒部分是从0到999,999,999的值,它是以秒为单位的长度调整。总持续时间通过调用此方法和{@link #getSeconds()}来定义。
     * <p>
     *  {@code Duration}表示时间线上两点之间的有向距离。负的持续时间由秒部分的负号表示。 -1纳秒的持续时间被存储为-1秒加上999,999,999纳秒。
     * 
     * 
     * @return the nanoseconds within the second part of the length of the duration, from 0 to 999,999,999
     */
    public int getNano() {
        return nanos;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this duration with the specified amount of seconds.
     * <p>
     * This returns a duration with the specified seconds, retaining the
     * nano-of-second part of this duration.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的秒数返回此持续时间的副本。
     * <p>
     *  这将返回具有指定秒数的持续时间,保留此持续时间的毫微秒部分。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param seconds  the seconds to represent, may be negative
     * @return a {@code Duration} based on this period with the requested seconds, not null
     */
    public Duration withSeconds(long seconds) {
        return create(seconds, nanos);
    }

    /**
     * Returns a copy of this duration with the specified nano-of-second.
     * <p>
     * This returns a duration with the specified nano-of-second, retaining the
     * seconds part of this duration.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回具有指定纳秒的此持续时间的副本。
     * <p>
     *  这将返回指定纳秒的持续时间,保留此持续时间的秒部分。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanoOfSecond  the nano-of-second to represent, from 0 to 999,999,999
     * @return a {@code Duration} based on this period with the requested nano-of-second, not null
     * @throws DateTimeException if the nano-of-second is invalid
     */
    public Duration withNanos(int nanoOfSecond) {
        NANO_OF_SECOND.checkValidIntValue(nanoOfSecond);
        return create(seconds, nanoOfSecond);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this duration with the specified duration added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此持续时间的副本,并添加指定的持续时间。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param duration  the duration to add, positive or negative, not null
     * @return a {@code Duration} based on this duration with the specified duration added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration plus(Duration duration) {
        return plus(duration.getSeconds(), duration.getNano());
     }

    /**
     * Returns a copy of this duration with the specified duration added.
     * <p>
     * The duration amount is measured in terms of the specified unit.
     * Only a subset of units are accepted by this method.
     * The unit must either have an {@linkplain TemporalUnit#isDurationEstimated() exact duration} or
     * be {@link ChronoUnit#DAYS} which is treated as 24 hours. Other units throw an exception.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此持续时间的副本,并添加指定的持续时间。
     * <p>
     * 持续时间量以指定的单位来衡量。此方法仅接受单元的子集。
     * 单位必须具有{@linkplain TemporalUnit#isDurationEstimated()精确持续时间}或{@link ChronoUnit#DAYS},该时间被视为24小时。
     * 其他单位抛出异常。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the amount of the period, measured in terms of the unit, positive or negative
     * @param unit  the unit that the period is measured in, must have an exact duration, not null
     * @return a {@code Duration} based on this duration with the specified duration added, not null
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration plus(long amountToAdd, TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit");
        if (unit == DAYS) {
            return plus(Math.multiplyExact(amountToAdd, SECONDS_PER_DAY), 0);
        }
        if (unit.isDurationEstimated()) {
            throw new UnsupportedTemporalTypeException("Unit must not have an estimated duration");
        }
        if (amountToAdd == 0) {
            return this;
        }
        if (unit instanceof ChronoUnit) {
            switch ((ChronoUnit) unit) {
                case NANOS: return plusNanos(amountToAdd);
                case MICROS: return plusSeconds((amountToAdd / (1000_000L * 1000)) * 1000).plusNanos((amountToAdd % (1000_000L * 1000)) * 1000);
                case MILLIS: return plusMillis(amountToAdd);
                case SECONDS: return plusSeconds(amountToAdd);
            }
            return plusSeconds(Math.multiplyExact(unit.getDuration().seconds, amountToAdd));
        }
        Duration duration = unit.getDuration().multipliedBy(amountToAdd);
        return plusSeconds(duration.getSeconds()).plusNanos(duration.getNano());
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this duration with the specified duration in standard 24 hour days added.
     * <p>
     * The number of days is multiplied by 86400 to obtain the number of seconds to add.
     * This is based on the standard definition of a day as 24 hours.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以标准24小时内的指定时间长度返回此持续时间的副本。
     * <p>
     *  天数乘以86400以获得要添加的秒数。这是基于一天的标准定义为24小时。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param daysToAdd  the days to add, positive or negative
     * @return a {@code Duration} based on this duration with the specified days added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration plusDays(long daysToAdd) {
        return plus(Math.multiplyExact(daysToAdd, SECONDS_PER_DAY), 0);
    }

    /**
     * Returns a copy of this duration with the specified duration in hours added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的持续时间(以小时为单位)返回此持续时间的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hoursToAdd  the hours to add, positive or negative
     * @return a {@code Duration} based on this duration with the specified hours added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration plusHours(long hoursToAdd) {
        return plus(Math.multiplyExact(hoursToAdd, SECONDS_PER_HOUR), 0);
    }

    /**
     * Returns a copy of this duration with the specified duration in minutes added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此持续时间的副本,指定的持续时间(以分钟为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minutesToAdd  the minutes to add, positive or negative
     * @return a {@code Duration} based on this duration with the specified minutes added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration plusMinutes(long minutesToAdd) {
        return plus(Math.multiplyExact(minutesToAdd, SECONDS_PER_MINUTE), 0);
    }

    /**
     * Returns a copy of this duration with the specified duration in seconds added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此持续时间的副本,指定的持续时间(以秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param secondsToAdd  the seconds to add, positive or negative
     * @return a {@code Duration} based on this duration with the specified seconds added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration plusSeconds(long secondsToAdd) {
        return plus(secondsToAdd, 0);
    }

    /**
     * Returns a copy of this duration with the specified duration in milliseconds added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此指定持续时间的副本(以毫秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param millisToAdd  the milliseconds to add, positive or negative
     * @return a {@code Duration} based on this duration with the specified milliseconds added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration plusMillis(long millisToAdd) {
        return plus(millisToAdd / 1000, (millisToAdd % 1000) * 1000_000);
    }

    /**
     * Returns a copy of this duration with the specified duration in nanoseconds added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此时间段的副本,指定的持续时间(以纳秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanosToAdd  the nanoseconds to add, positive or negative
     * @return a {@code Duration} based on this duration with the specified nanoseconds added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration plusNanos(long nanosToAdd) {
        return plus(0, nanosToAdd);
    }

    /**
     * Returns a copy of this duration with the specified duration added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此持续时间的副本,并添加指定的持续时间。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param secondsToAdd  the seconds to add, positive or negative
     * @param nanosToAdd  the nanos to add, positive or negative
     * @return a {@code Duration} based on this duration with the specified seconds added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    private Duration plus(long secondsToAdd, long nanosToAdd) {
        if ((secondsToAdd | nanosToAdd) == 0) {
            return this;
        }
        long epochSec = Math.addExact(seconds, secondsToAdd);
        epochSec = Math.addExact(epochSec, nanosToAdd / NANOS_PER_SECOND);
        nanosToAdd = nanosToAdd % NANOS_PER_SECOND;
        long nanoAdjustment = nanos + nanosToAdd;  // safe int+NANOS_PER_SECOND
        return ofSeconds(epochSec, nanoAdjustment);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this duration with the specified duration subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此指定持续时间减去的持续时间的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param duration  the duration to subtract, positive or negative, not null
     * @return a {@code Duration} based on this duration with the specified duration subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration minus(Duration duration) {
        long secsToSubtract = duration.getSeconds();
        int nanosToSubtract = duration.getNano();
        if (secsToSubtract == Long.MIN_VALUE) {
            return plus(Long.MAX_VALUE, -nanosToSubtract).plus(1, 0);
        }
        return plus(-secsToSubtract, -nanosToSubtract);
     }

    /**
     * Returns a copy of this duration with the specified duration subtracted.
     * <p>
     * The duration amount is measured in terms of the specified unit.
     * Only a subset of units are accepted by this method.
     * The unit must either have an {@linkplain TemporalUnit#isDurationEstimated() exact duration} or
     * be {@link ChronoUnit#DAYS} which is treated as 24 hours. Other units throw an exception.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此指定持续时间减去的持续时间的副本。
     * <p>
     *  持续时间量以指定的单位来衡量。此方法仅接受单元的子集。
     * 单位必须具有{@linkplain TemporalUnit#isDurationEstimated()精确持续时间}或{@link ChronoUnit#DAYS},该时间被视为24小时。
     * 其他单位抛出异常。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the amount of the period, measured in terms of the unit, positive or negative
     * @param unit  the unit that the period is measured in, must have an exact duration, not null
     * @return a {@code Duration} based on this duration with the specified duration subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this duration with the specified duration in standard 24 hour days subtracted.
     * <p>
     * The number of days is multiplied by 86400 to obtain the number of seconds to subtract.
     * This is based on the standard definition of a day as 24 hours.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以标准24小时天减去指定的持续时间,返回此持续时间的副本。
     * <p>
     *  天数乘以86400以获得减去的秒数。这是基于一天的标准定义为24小时。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param daysToSubtract  the days to subtract, positive or negative
     * @return a {@code Duration} based on this duration with the specified days subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration minusDays(long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-daysToSubtract));
    }

    /**
     * Returns a copy of this duration with the specified duration in hours subtracted.
     * <p>
     * The number of hours is multiplied by 3600 to obtain the number of seconds to subtract.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此持续时间的副本,指定的持续时间(减去小时数)。
     * <p>
     *  小时数乘以3600以获得减去的秒数。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param hoursToSubtract  the hours to subtract, positive or negative
     * @return a {@code Duration} based on this duration with the specified hours subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration minusHours(long hoursToSubtract) {
        return (hoursToSubtract == Long.MIN_VALUE ? plusHours(Long.MAX_VALUE).plusHours(1) : plusHours(-hoursToSubtract));
    }

    /**
     * Returns a copy of this duration with the specified duration in minutes subtracted.
     * <p>
     * The number of hours is multiplied by 60 to obtain the number of seconds to subtract.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此持续时间的副本,指定的持续时间(减去分钟)。
     * <p>
     *  小时数乘以60以获得减去的秒数。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param minutesToSubtract  the minutes to subtract, positive or negative
     * @return a {@code Duration} based on this duration with the specified minutes subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration minusMinutes(long minutesToSubtract) {
        return (minutesToSubtract == Long.MIN_VALUE ? plusMinutes(Long.MAX_VALUE).plusMinutes(1) : plusMinutes(-minutesToSubtract));
    }

    /**
     * Returns a copy of this duration with the specified duration in seconds subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此持续时间的副本,指定的持续时间(以秒为单位)。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param secondsToSubtract  the seconds to subtract, positive or negative
     * @return a {@code Duration} based on this duration with the specified seconds subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration minusSeconds(long secondsToSubtract) {
        return (secondsToSubtract == Long.MIN_VALUE ? plusSeconds(Long.MAX_VALUE).plusSeconds(1) : plusSeconds(-secondsToSubtract));
    }

    /**
     * Returns a copy of this duration with the specified duration in milliseconds subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以指定的持续时间(以毫秒减去)返回此持续时间的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param millisToSubtract  the milliseconds to subtract, positive or negative
     * @return a {@code Duration} based on this duration with the specified milliseconds subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration minusMillis(long millisToSubtract) {
        return (millisToSubtract == Long.MIN_VALUE ? plusMillis(Long.MAX_VALUE).plusMillis(1) : plusMillis(-millisToSubtract));
    }

    /**
     * Returns a copy of this duration with the specified duration in nanoseconds subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此指定时间长度的副本,以纳秒减去。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param nanosToSubtract  the nanoseconds to subtract, positive or negative
     * @return a {@code Duration} based on this duration with the specified nanoseconds subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration minusNanos(long nanosToSubtract) {
        return (nanosToSubtract == Long.MIN_VALUE ? plusNanos(Long.MAX_VALUE).plusNanos(1) : plusNanos(-nanosToSubtract));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this duration multiplied by the scalar.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此持续时间的倍数乘以标量的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param multiplicand  the value to multiply the duration by, positive or negative
     * @return a {@code Duration} based on this duration multiplied by the specified scalar, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration multipliedBy(long multiplicand) {
        if (multiplicand == 0) {
            return ZERO;
        }
        if (multiplicand == 1) {
            return this;
        }
        return create(toSeconds().multiply(BigDecimal.valueOf(multiplicand)));
     }

    /**
     * Returns a copy of this duration divided by the specified value.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此指定时间除以指定值的副本。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param divisor  the value to divide the duration by, positive or negative, not zero
     * @return a {@code Duration} based on this duration divided by the specified divisor, not null
     * @throws ArithmeticException if the divisor is zero or if numeric overflow occurs
     */
    public Duration dividedBy(long divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        if (divisor == 1) {
            return this;
        }
        return create(toSeconds().divide(BigDecimal.valueOf(divisor), RoundingMode.DOWN));
     }

    /**
     * Converts this duration to the total length in seconds and
     * fractional nanoseconds expressed as a {@code BigDecimal}.
     *
     * <p>
     *  将此持续时间转换为以{@code BigDecimal}表示的以秒和分数纳秒为单位的总长度。
     * 
     * 
     * @return the total length of the duration in seconds, with a scale of 9, not null
     */
    private BigDecimal toSeconds() {
        return BigDecimal.valueOf(seconds).add(BigDecimal.valueOf(nanos, 9));
    }

    /**
     * Creates an instance of {@code Duration} from a number of seconds.
     *
     * <p>
     *  从几秒钟创建{@code Duration}的实例。
     * 
     * 
     * @param seconds  the number of seconds, up to scale 9, positive or negative
     * @return a {@code Duration}, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    private static Duration create(BigDecimal seconds) {
        BigInteger nanos = seconds.movePointRight(9).toBigIntegerExact();
        BigInteger[] divRem = nanos.divideAndRemainder(BI_NANOS_PER_SECOND);
        if (divRem[0].bitLength() > 63) {
            throw new ArithmeticException("Exceeds capacity of Duration: " + nanos);
        }
        return ofSeconds(divRem[0].longValue(), divRem[1].intValue());
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this duration with the length negated.
     * <p>
     * This method swaps the sign of the total length of this duration.
     * For example, {@code PT1.3S} will be returned as {@code PT-1.3S}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此长度的副本,长度为negated。
     * <p>
     *  此方法交换此持续时间的总长度的符号。例如,{@code PT1.3S}将返回为{@code PT-1.3S}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return a {@code Duration} based on this duration with the amount negated, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration negated() {
        return multipliedBy(-1);
    }

    /**
     * Returns a copy of this duration with a positive length.
     * <p>
     * This method returns a positive duration by effectively removing the sign from any negative total length.
     * For example, {@code PT-1.3S} will be returned as {@code PT1.3S}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此长度为正值的持续时间的副本。
     * <p>
     *  此方法通过有效地从任何负总长度中移除符号来返回正持续时间。例如,{@code PT-1.3S}将返回为{@code PT1.3S}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return a {@code Duration} based on this duration with an absolute length, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Duration abs() {
        return isNegative() ? negated() : this;
    }

    //-------------------------------------------------------------------------
    /**
     * Adds this duration to the specified temporal object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with this duration added.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#plus(TemporalAmount)}.
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   dateTime = thisDuration.addTo(dateTime);
     *   dateTime = dateTime.plus(thisDuration);
     * </pre>
     * <p>
     * The calculation will add the seconds, then nanos.
     * Only non-zero amounts will be added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将此持续时间添加到指定的时间对象。
     * <p>
     * 这返回与添加了此持续时间的输入相同的observable类型的时间对象。
     * <p>
     *  在大多数情况下,使用{@link Temporal#plus(TemporalAmount)}来反转调用模式是更清楚的。
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐的dateTime = thisDuration.addTo(dateTime); dateTime = dateTime.plus(thisDuration);
     * 。
     * </pre>
     * <p>
     *  计算将添加秒,然后是nanos。将只添加非零数量。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param temporal  the temporal object to adjust, not null
     * @return an object of the same type with the adjustment made, not null
     * @throws DateTimeException if unable to add
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Temporal addTo(Temporal temporal) {
        if (seconds != 0) {
            temporal = temporal.plus(seconds, SECONDS);
        }
        if (nanos != 0) {
            temporal = temporal.plus(nanos, NANOS);
        }
        return temporal;
    }

    /**
     * Subtracts this duration from the specified temporal object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with this duration subtracted.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#minus(TemporalAmount)}.
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   dateTime = thisDuration.subtractFrom(dateTime);
     *   dateTime = dateTime.minus(thisDuration);
     * </pre>
     * <p>
     * The calculation will subtract the seconds, then nanos.
     * Only non-zero amounts will be added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  从指定的时间对象中减去此持续时间。
     * <p>
     *  这返回与具有该持续时间减去的输入相同的observable类型的时间对象。
     * <p>
     *  在大多数情况下,使用{@link Temporal#minus(TemporalAmount)}来反转调用模式是更清楚的。
     * <pre>
     *  //这两行是等价的,但第二种方法是推荐的dateTime = thisDuration.subtractFrom(dateTime); dateTime = dateTime.minus(thisDu
     * ration);。
     * </pre>
     * <p>
     *  计算将减去秒,然后减去nanos。将只添加非零数量。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param temporal  the temporal object to adjust, not null
     * @return an object of the same type with the adjustment made, not null
     * @throws DateTimeException if unable to subtract
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Temporal subtractFrom(Temporal temporal) {
        if (seconds != 0) {
            temporal = temporal.minus(seconds, SECONDS);
        }
        if (nanos != 0) {
            temporal = temporal.minus(nanos, NANOS);
        }
        return temporal;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the number of days in this duration.
     * <p>
     * This returns the total number of days in the duration by dividing the
     * number of seconds by 86400.
     * This is based on the standard definition of a day as 24 hours.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  获取此持续时间中的天数。
     * <p>
     *  这会返回持续时间中的总天数,即秒数除以86400.这是基于一天的标准定义为24小时。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return the number of days in the duration, may be negative
     */
    public long toDays() {
        return seconds / SECONDS_PER_DAY;
    }

    /**
     * Gets the number of hours in this duration.
     * <p>
     * This returns the total number of hours in the duration by dividing the
     * number of seconds by 3600.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  获取此持续时间中的小时数。
     * <p>
     * 这会返回持续时间中的总小时数,将秒数除以3600。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return the number of hours in the duration, may be negative
     */
    public long toHours() {
        return seconds / SECONDS_PER_HOUR;
    }

    /**
     * Gets the number of minutes in this duration.
     * <p>
     * This returns the total number of minutes in the duration by dividing the
     * number of seconds by 60.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  获取此持续时间中的分钟数。
     * <p>
     *  这将返回持续时间中的总分钟数,将秒数除以60。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return the number of minutes in the duration, may be negative
     */
    public long toMinutes() {
        return seconds / SECONDS_PER_MINUTE;
    }

    /**
     * Converts this duration to the total length in milliseconds.
     * <p>
     * If this duration is too large to fit in a {@code long} milliseconds, then an
     * exception is thrown.
     * <p>
     * If this duration has greater than millisecond precision, then the conversion
     * will drop any excess precision information as though the amount in nanoseconds
     * was subject to integer division by one million.
     *
     * <p>
     *  将此持续时间转换为总长度(以毫秒为单位)。
     * <p>
     *  如果此持续时间太长,无法适应{@code long}毫秒,则会抛出异常。
     * <p>
     *  如果此持续时间大于毫秒精度,则转换将丢弃任何超额精度信息,如同以纳秒为单位的量受到整数除以一百万。
     * 
     * 
     * @return the total length of the duration in milliseconds
     * @throws ArithmeticException if numeric overflow occurs
     */
    public long toMillis() {
        long millis = Math.multiplyExact(seconds, 1000);
        millis = Math.addExact(millis, nanos / 1000_000);
        return millis;
    }

    /**
     * Converts this duration to the total length in nanoseconds expressed as a {@code long}.
     * <p>
     * If this duration is too large to fit in a {@code long} nanoseconds, then an
     * exception is thrown.
     *
     * <p>
     *  将此持续时间转换为表示为{@code long}的以纳秒为单位的总长度。
     * <p>
     *  如果此持续时间太长,无法适应{@code long}纳秒,则会抛出异常。
     * 
     * 
     * @return the total length of the duration in nanoseconds
     * @throws ArithmeticException if numeric overflow occurs
     */
    public long toNanos() {
        long totalNanos = Math.multiplyExact(seconds, NANOS_PER_SECOND);
        totalNanos = Math.addExact(totalNanos, nanos);
        return totalNanos;
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this duration to the specified {@code Duration}.
     * <p>
     * The comparison is based on the total length of the durations.
     * It is "consistent with equals", as defined by {@link Comparable}.
     *
     * <p>
     *  将此持续时间与指定的{@code Duration}进行比较。
     * <p>
     *  比较基于持续时间的总长度。它是"与等号一致",由{@link Comparable}定义。
     * 
     * 
     * @param otherDuration  the other duration to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    public int compareTo(Duration otherDuration) {
        int cmp = Long.compare(seconds, otherDuration.seconds);
        if (cmp != 0) {
            return cmp;
        }
        return nanos - otherDuration.nanos;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this duration is equal to the specified {@code Duration}.
     * <p>
     * The comparison is based on the total length of the durations.
     *
     * <p>
     *  检查此持续时间是否等于指定的{@code Duration}。
     * <p>
     *  比较基于持续时间的总长度。
     * 
     * 
     * @param otherDuration  the other duration, null returns false
     * @return true if the other duration is equal to this one
     */
    @Override
    public boolean equals(Object otherDuration) {
        if (this == otherDuration) {
            return true;
        }
        if (otherDuration instanceof Duration) {
            Duration other = (Duration) otherDuration;
            return this.seconds == other.seconds &&
                   this.nanos == other.nanos;
        }
        return false;
    }

    /**
     * A hash code for this duration.
     *
     * <p>
     *  此持续时间的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return ((int) (seconds ^ (seconds >>> 32))) + (51 * nanos);
    }

    //-----------------------------------------------------------------------
    /**
     * A string representation of this duration using ISO-8601 seconds
     * based representation, such as {@code PT8H6M12.345S}.
     * <p>
     * The format of the returned string will be {@code PTnHnMnS}, where n is
     * the relevant hours, minutes or seconds part of the duration.
     * Any fractional seconds are placed after a decimal point i the seconds section.
     * If a section has a zero value, it is omitted.
     * The hours, minutes and seconds will all have the same sign.
     * <p>
     * Examples:
     * <pre>
     *    "20.345 seconds"                 -- "PT20.345S
     *    "15 minutes" (15 * 60 seconds)   -- "PT15M"
     *    "10 hours" (10 * 3600 seconds)   -- "PT10H"
     *    "2 days" (2 * 86400 seconds)     -- "PT48H"
     * </pre>
     * Note that multiples of 24 hours are not output as days to avoid confusion
     * with {@code Period}.
     *
     * <p>
     *  此持续时间的字符串表示形式,使用基于ISO-8601秒的表示形式,例如{@code PT8H6M12.345S}。
     * <p>
     * 返回的字符串的格式为{@code PTnHnMnS},其中n是持续时间的相关小时,分钟或秒部分。任何小数秒都放在小数点后的秒部分。如果节具有零值,则省略。小时,分钟和秒钟都将具有相同的符号。
     * <p>
     *  例子：
     * <pre>
     *  "PT30M""10小时"(10 * 3600秒) - "PT10H""2天"(2 * 86400秒)"20.345秒" - "PT20.345S"15分钟" - "PT48H"
     * </pre>
     * 
     * @return an ISO-8601 representation of this duration, not null
     */
    @Override
    public String toString() {
        if (this == ZERO) {
            return "PT0S";
        }
        long hours = seconds / SECONDS_PER_HOUR;
        int minutes = (int) ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        int secs = (int) (seconds % SECONDS_PER_MINUTE);
        StringBuilder buf = new StringBuilder(24);
        buf.append("PT");
        if (hours != 0) {
            buf.append(hours).append('H');
        }
        if (minutes != 0) {
            buf.append(minutes).append('M');
        }
        if (secs == 0 && nanos == 0 && buf.length() > 2) {
            return buf.toString();
        }
        if (secs < 0 && nanos > 0) {
            if (secs == -1) {
                buf.append("-0");
            } else {
                buf.append(secs + 1);
            }
        } else {
            buf.append(secs);
        }
        if (nanos > 0) {
            int pos = buf.length();
            if (secs < 0) {
                buf.append(2 * NANOS_PER_SECOND - nanos);
            } else {
                buf.append(nanos + NANOS_PER_SECOND);
            }
            while (buf.charAt(buf.length() - 1) == '0') {
                buf.setLength(buf.length() - 1);
            }
            buf.setCharAt(pos, '.');
        }
        buf.append('S');
        return buf.toString();
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the object using a
     * <a href="../../serialized-form.html#java.time.Ser">dedicated serialized form</a>.
     * <p>
     *  请注意,24小时的倍数不会以天为单位输出,以免与{@code Period}混淆。
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(1);  // identifies a Duration
     *  out.writeLong(seconds);
     *  out.writeInt(nanos);
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.DURATION_TYPE, this);
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
        out.writeLong(seconds);
        out.writeInt(nanos);
    }

    static Duration readExternal(DataInput in) throws IOException {
        long seconds = in.readLong();
        int nanos = in.readInt();
        return Duration.ofSeconds(seconds, nanos);
    }

}
