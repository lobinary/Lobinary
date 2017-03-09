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

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A date-based amount of time in the ISO-8601 calendar system,
 * such as '2 years, 3 months and 4 days'.
 * <p>
 * This class models a quantity or amount of time in terms of years, months and days.
 * See {@link Duration} for the time-based equivalent to this class.
 * <p>
 * Durations and periods differ in their treatment of daylight savings time
 * when added to {@link ZonedDateTime}. A {@code Duration} will add an exact
 * number of seconds, thus a duration of one day is always exactly 24 hours.
 * By contrast, a {@code Period} will add a conceptual day, trying to maintain
 * the local time.
 * <p>
 * For example, consider adding a period of one day and a duration of one day to
 * 18:00 on the evening before a daylight savings gap. The {@code Period} will add
 * the conceptual day and result in a {@code ZonedDateTime} at 18:00 the following day.
 * By contrast, the {@code Duration} will add exactly 24 hours, resulting in a
 * {@code ZonedDateTime} at 19:00 the following day (assuming a one hour DST gap).
 * <p>
 * The supported units of a period are {@link ChronoUnit#YEARS YEARS},
 * {@link ChronoUnit#MONTHS MONTHS} and {@link ChronoUnit#DAYS DAYS}.
 * All three fields are always present, but may be set to zero.
 * <p>
 * The ISO-8601 calendar system is the modern civil calendar system used today
 * in most of the world. It is equivalent to the proleptic Gregorian calendar
 * system, in which today's rules for leap years are applied for all time.
 * <p>
 * The period is modeled as a directed amount of time, meaning that individual parts of the
 * period may be negative.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code Period} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  ISO-8601日历系统中基于日期的时间量,例如"2年,3个月和4天"。
 * <p>
 *  此类别以年,月和日为单位建模时间的数量或时间量。请参阅{@link Duration},了解基于时间的等同于此类。
 * <p>
 *  持续时间和周期在添加到{@link ZonedDateTime}时的夏令时处理方式不同。 {@code Duration}将会添加确切的秒数,因此一天的持续时间始终为24小时。
 * 相比之下,{@code Period}将添加一个概念性的日子,试图维持当地时间。
 * <p>
 * 例如,考虑将一天的时间段和一天的持续时间添加到夏令时间隔之前的晚上18:00。 {@code Period}会在第二天18:00在{@code ZonedDateTime}中添加概念性的日期和结果。
 * 相比之下,{@code Duration}将正好添加24小时,从而在第二天的19:00(假设一小时的DST差距)产生{@code ZonedDateTime}。
 * <p>
 *  一个期间支持的单位是{@link ChronoUnit#YEARS YEARS},{@link ChronoUnit#MONTHS MONTHS}和{@link ChronoUnit#DAYS DAYS}
 * 。
 * 所有三个字段总是存在,但可以设置为零。
 * <p>
 *  ISO-8601日历系统是当今世界上使用的现代民用日历系统。它相当于普通的公历日历系统,其中今天的闰年规则适用于所有时间。
 * <p>
 *  周期被建模为定向时间量,意味着周期的各个部分可以是负的。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code Period}实例上使用身份敏感操作(包
 * 括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class Period
        implements ChronoPeriod, Serializable {

    /**
     * A constant for a period of zero.
     * <p>
     *  一个为零的常数。
     * 
     */
    public static final Period ZERO = new Period(0, 0, 0);
    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = -3587258372562876L;
    /**
     * The pattern for parsing.
     * <p>
     *  解析模式。
     * 
     */
    private static final Pattern PATTERN =
            Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?", Pattern.CASE_INSENSITIVE);

    /**
     * The set of supported units.
     * <p>
     *  支持的单位集。
     * 
     */
    private static final List<TemporalUnit> SUPPORTED_UNITS =
            Collections.unmodifiableList(Arrays.<TemporalUnit>asList(YEARS, MONTHS, DAYS));

    /**
     * The number of years.
     * <p>
     *  年数。
     * 
     */
    private final int years;
    /**
     * The number of months.
     * <p>
     *  月数。
     * 
     */
    private final int months;
    /**
     * The number of days.
     * <p>
     * 天数。
     * 
     */
    private final int days;

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Period} representing a number of years.
     * <p>
     * The resulting period will have the specified years.
     * The months and days units will be zero.
     *
     * <p>
     *  获取代表多年的{@code Period}。
     * <p>
     *  生成的期间将具有指定的年份。月和日单位将为零。
     * 
     * 
     * @param years  the number of years, positive or negative
     * @return the period of years, not null
     */
    public static Period ofYears(int years) {
        return create(years, 0, 0);
    }

    /**
     * Obtains a {@code Period} representing a number of months.
     * <p>
     * The resulting period will have the specified months.
     * The years and days units will be zero.
     *
     * <p>
     *  获取代表多个月的{@code Period}。
     * <p>
     *  生成的期间将具有指定的月份。年和天单位将为零。
     * 
     * 
     * @param months  the number of months, positive or negative
     * @return the period of months, not null
     */
    public static Period ofMonths(int months) {
        return create(0, months, 0);
    }

    /**
     * Obtains a {@code Period} representing a number of weeks.
     * <p>
     * The resulting period will be day-based, with the amount of days
     * equal to the number of weeks multiplied by 7.
     * The years and months units will be zero.
     *
     * <p>
     *  获取{@code Period}表示几周。
     * <p>
     *  所得期间将以天为基础,天数等于星期数乘以7.年和月单位将为零。
     * 
     * 
     * @param weeks  the number of weeks, positive or negative
     * @return the period, with the input weeks converted to days, not null
     */
    public static Period ofWeeks(int weeks) {
        return create(0, 0, Math.multiplyExact(weeks, 7));
    }

    /**
     * Obtains a {@code Period} representing a number of days.
     * <p>
     * The resulting period will have the specified days.
     * The years and months units will be zero.
     *
     * <p>
     *  获取表示天数的{@code Period}。
     * <p>
     *  生成的期间将具有指定的天数。年和月单位将为零。
     * 
     * 
     * @param days  the number of days, positive or negative
     * @return the period of days, not null
     */
    public static Period ofDays(int days) {
        return create(0, 0, days);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Period} representing a number of years, months and days.
     * <p>
     * This creates an instance based on years, months and days.
     *
     * <p>
     *  获取表示多年,几个月和几天的{@code Period}。
     * <p>
     *  这将创建一个基于年,月和日的实例。
     * 
     * 
     * @param years  the amount of years, may be negative
     * @param months  the amount of months, may be negative
     * @param days  the amount of days, may be negative
     * @return the period of years, months and days, not null
     */
    public static Period of(int years, int months, int days) {
        return create(years, months, days);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Period} from a temporal amount.
     * <p>
     * This obtains a period based on the specified amount.
     * A {@code TemporalAmount} represents an  amount of time, which may be
     * date-based or time-based, which this factory extracts to a {@code Period}.
     * <p>
     * The conversion loops around the set of units from the amount and uses
     * the {@link ChronoUnit#YEARS YEARS}, {@link ChronoUnit#MONTHS MONTHS}
     * and {@link ChronoUnit#DAYS DAYS} units to create a period.
     * If any other units are found then an exception is thrown.
     * <p>
     * If the amount is a {@code ChronoPeriod} then it must use the ISO chronology.
     *
     * <p>
     *  从时间量获取{@code Period}的实例。
     * <p>
     *  这获得基于指定量的周期。 {@code TemporalAmount}表示时间量,可以是基于日期或基于时间,此工厂会将其提取到{@code Period}。
     * <p>
     *  转换会根据金额对该组单位进行循环,并使用{@link ChronoUnit#YEARS YEARS},{@link ChronoUnit#MONTHS MONTHS}和{@link ChronoUnit#DAYS DAYS}
     * 单位创建期间。
     * 如果发现任何其他单位,则抛出异常。
     * <p>
     *  如果金额是{@code ChronoPeriod},那么它必须使用ISO年表。
     * 
     * 
     * @param amount  the temporal amount to convert, not null
     * @return the equivalent period, not null
     * @throws DateTimeException if unable to convert to a {@code Period}
     * @throws ArithmeticException if the amount of years, months or days exceeds an int
     */
    public static Period from(TemporalAmount amount) {
        if (amount instanceof Period) {
            return (Period) amount;
        }
        if (amount instanceof ChronoPeriod) {
            if (IsoChronology.INSTANCE.equals(((ChronoPeriod) amount).getChronology()) == false) {
                throw new DateTimeException("Period requires ISO chronology: " + amount);
            }
        }
        Objects.requireNonNull(amount, "amount");
        int years = 0;
        int months = 0;
        int days = 0;
        for (TemporalUnit unit : amount.getUnits()) {
            long unitAmount = amount.get(unit);
            if (unit == ChronoUnit.YEARS) {
                years = Math.toIntExact(unitAmount);
            } else if (unit == ChronoUnit.MONTHS) {
                months = Math.toIntExact(unitAmount);
            } else if (unit == ChronoUnit.DAYS) {
                days = Math.toIntExact(unitAmount);
            } else {
                throw new DateTimeException("Unit must be Years, Months or Days, but was " + unit);
            }
        }
        return create(years, months, days);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Period} from a text string such as {@code PnYnMnD}.
     * <p>
     * This will parse the string produced by {@code toString()} which is
     * based on the ISO-8601 period formats {@code PnYnMnD} and {@code PnW}.
     * <p>
     * The string starts with an optional sign, denoted by the ASCII negative
     * or positive symbol. If negative, the whole period is negated.
     * The ASCII letter "P" is next in upper or lower case.
     * There are then four sections, each consisting of a number and a suffix.
     * At least one of the four sections must be present.
     * The sections have suffixes in ASCII of "Y", "M", "W" and "D" for
     * years, months, weeks and days, accepted in upper or lower case.
     * The suffixes must occur in order.
     * The number part of each section must consist of ASCII digits.
     * The number may be prefixed by the ASCII negative or positive symbol.
     * The number must parse to an {@code int}.
     * <p>
     * The leading plus/minus sign, and negative values for other units are
     * not part of the ISO-8601 standard. In addition, ISO-8601 does not
     * permit mixing between the {@code PnYnMnD} and {@code PnW} formats.
     * Any week-based input is multiplied by 7 and treated as a number of days.
     * <p>
     * For example, the following are valid inputs:
     * <pre>
     *   "P2Y"             -- Period.ofYears(2)
     *   "P3M"             -- Period.ofMonths(3)
     *   "P4W"             -- Period.ofWeeks(4)
     *   "P5D"             -- Period.ofDays(5)
     *   "P1Y2M3D"         -- Period.of(1, 2, 3)
     *   "P1Y2M3W4D"       -- Period.of(1, 2, 25)
     *   "P-1Y2M"          -- Period.of(-1, 2, 0)
     *   "-P1Y2M"          -- Period.of(-1, -2, 0)
     * </pre>
     *
     * <p>
     *  从文本字符串(例如{@code PnYnMnD})获取{@code Period}。
     * <p>
     * 这将解析由基于ISO-8601周期格式{@code PnYnMnD}和{@code PnW}的{@code toString()}生成的字符串。
     * <p>
     *  字符串以可选符号开始,由ASCII负或正符号表示。如果为负,则整个周期被否定。 ASCII字母"P"大写或小写。然后有四个部分,每个部分由数字和后缀组成。必须存在四个部分中的至少一个。
     * 这些段在ASCII,"Y","M","W"和"D"的后缀以年,月,周和天为单位,以大写或小写接受。后缀必须按顺序出现。每个部分的编号部分必须由ASCII数字组成。数字可以以ASCII负或正符号为前缀。
     * 该数字必须解析为{@code int}。
     * <p>
     *  前导加号/减号和其他单位的负值不是ISO-8601标准的一部分。此外,ISO-8601不允许在{@code PnYnMnD}和{@code PnW}格式之间混合。任何基于周的输入乘以7并视为天数。
     * <p>
     *  例如,以下是有效输入：
     * <pre>
     *  "P2Y" - 周期(2)"P3M" - 周期(3)"P4W" - 周期(4)"P5D" - 周期(5)"P1Y2M3D" - 周期。
     * (1,2,3)"P1Y2M3W4D" - (1,2,25)的周期"P-1Y2M" - (-1,2,0)的周期"-P1Y2M" - 周期。的(-1,-2,0)。
     * </pre>
     * 
     * 
     * @param text  the text to parse, not null
     * @return the parsed period, not null
     * @throws DateTimeParseException if the text cannot be parsed to a period
     */
    public static Period parse(CharSequence text) {
        Objects.requireNonNull(text, "text");
        Matcher matcher = PATTERN.matcher(text);
        if (matcher.matches()) {
            int negate = ("-".equals(matcher.group(1)) ? -1 : 1);
            String yearMatch = matcher.group(2);
            String monthMatch = matcher.group(3);
            String weekMatch = matcher.group(4);
            String dayMatch = matcher.group(5);
            if (yearMatch != null || monthMatch != null || dayMatch != null || weekMatch != null) {
                try {
                    int years = parseNumber(text, yearMatch, negate);
                    int months = parseNumber(text, monthMatch, negate);
                    int weeks = parseNumber(text, weekMatch, negate);
                    int days = parseNumber(text, dayMatch, negate);
                    days = Math.addExact(days, Math.multiplyExact(weeks, 7));
                    return create(years, months, days);
                } catch (NumberFormatException ex) {
                    throw new DateTimeParseException("Text cannot be parsed to a Period", text, 0, ex);
                }
            }
        }
        throw new DateTimeParseException("Text cannot be parsed to a Period", text, 0);
    }

    private static int parseNumber(CharSequence text, String str, int negate) {
        if (str == null) {
            return 0;
        }
        int val = Integer.parseInt(str);
        try {
            return Math.multiplyExact(val, negate);
        } catch (ArithmeticException ex) {
            throw new DateTimeParseException("Text cannot be parsed to a Period", text, 0, ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a {@code Period} consisting of the number of years, months,
     * and days between two dates.
     * <p>
     * The start date is included, but the end date is not.
     * The period is calculated by removing complete months, then calculating
     * the remaining number of days, adjusting to ensure that both have the same sign.
     * The number of months is then split into years and months based on a 12 month year.
     * A month is considered if the end day-of-month is greater than or equal to the start day-of-month.
     * For example, from {@code 2010-01-15} to {@code 2011-03-18} is one year, two months and three days.
     * <p>
     * The result of this method can be a negative period if the end is before the start.
     * The negative sign will be the same in each of year, month and day.
     *
     * <p>
     * 获取{@code期间},包括两个日期之间的年数,月数和天数。
     * <p>
     *  包括开始日期,但不包括结束日期。通过删除完整月份,然后计算剩余天数来计算期间,调整以确保两者具有相同的符号。然后,将月数分为基于12个月年的年和月。如果结束日期大于或等于开始日期,则考虑一个月。
     * 例如,从{@code 2010-01-15}到{@code 2011-03-18}是一年,两个月和三天。
     * <p>
     *  如果结束在开始之前,此方法的结果可以是负期间。负号在年,月和日的每一个中都是相同的。
     * 
     * 
     * @param startDateInclusive  the start date, inclusive, not null
     * @param endDateExclusive  the end date, exclusive, not null
     * @return the period between this date and the end date, not null
     * @see ChronoLocalDate#until(ChronoLocalDate)
     */
    public static Period between(LocalDate startDateInclusive, LocalDate endDateExclusive) {
        return startDateInclusive.until(endDateExclusive);
    }

    //-----------------------------------------------------------------------
    /**
     * Creates an instance.
     *
     * <p>
     *  创建实例。
     * 
     * 
     * @param years  the amount
     * @param months  the amount
     * @param days  the amount
     */
    private static Period create(int years, int months, int days) {
        if ((years | months | days) == 0) {
            return ZERO;
        }
        return new Period(years, months, days);
    }

    /**
     * Constructor.
     *
     * <p>
     *  构造函数。
     * 
     * 
     * @param years  the amount
     * @param months  the amount
     * @param days  the amount
     */
    private Period(int years, int months, int days) {
        this.years = years;
        this.months = months;
        this.days = days;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value of the requested unit.
     * <p>
     * This returns a value for each of the three supported units,
     * {@link ChronoUnit#YEARS YEARS}, {@link ChronoUnit#MONTHS MONTHS} and
     * {@link ChronoUnit#DAYS DAYS}.
     * All other units throw an exception.
     *
     * <p>
     *  获取所请求单位的值。
     * <p>
     *  这会为三个受支持的单元{@link ChronoUnit#YEARS YEARS},{@link ChronoUnit#MONTHS MONTHS}和{@link ChronoUnit#DAYS DAYS}
     * 中的每一个返回一个值。
     * 所有其他单位都会抛出异常。
     * 
     * 
     * @param unit the {@code TemporalUnit} for which to return the value
     * @return the long value of the unit
     * @throws DateTimeException if the unit is not supported
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     */
    @Override
    public long get(TemporalUnit unit) {
        if (unit == ChronoUnit.YEARS) {
            return getYears();
        } else if (unit == ChronoUnit.MONTHS) {
            return getMonths();
        } else if (unit == ChronoUnit.DAYS) {
            return getDays();
        } else {
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
    }

    /**
     * Gets the set of units supported by this period.
     * <p>
     * The supported units are {@link ChronoUnit#YEARS YEARS},
     * {@link ChronoUnit#MONTHS MONTHS} and {@link ChronoUnit#DAYS DAYS}.
     * They are returned in the order years, months, days.
     * <p>
     * This set can be used in conjunction with {@link #get(TemporalUnit)}
     * to access the entire state of the period.
     *
     * <p>
     *  获取此期间支持的单位集。
     * <p>
     *  支持的单位为{@link ChronoUnit#YEARS YEARS},{@link ChronoUnit#MONTHS MONTHS}和{@link ChronoUnit#DAYS DAYS}。
     * 它们按照年,月,日的顺序返回。
     * <p>
     *  此集合可以与{@link #get(TemporalUnit)}结合使用以访问期间的整个状态。
     * 
     * 
     * @return a list containing the years, months and days units, not null
     */
    @Override
    public List<TemporalUnit> getUnits() {
        return SUPPORTED_UNITS;
    }

    /**
     * Gets the chronology of this period, which is the ISO calendar system.
     * <p>
     * The {@code Chronology} represents the calendar system in use.
     * The ISO-8601 calendar system is the modern civil calendar system used today
     * in most of the world. It is equivalent to the proleptic Gregorian calendar
     * system, in which today's rules for leap years are applied for all time.
     *
     * <p>
     *  获取这个时期的年表,这是ISO日历系统。
     * <p>
     * {@code Chronology}表示正在使用的日历系统。 ISO-8601日历系统是当今世界上使用的现代民用日历系统。它相当于普通的公历日历系统,其中今天的闰年规则适用于所有时间。
     * 
     * 
     * @return the ISO chronology, not null
     */
    @Override
    public IsoChronology getChronology() {
        return IsoChronology.INSTANCE;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if all three units of this period are zero.
     * <p>
     * A zero period has the value zero for the years, months and days units.
     *
     * <p>
     *  检查此周期的所有三个单位是否为零。
     * <p>
     *  零周期的年,月和日单位的值为零。
     * 
     * 
     * @return true if this period is zero-length
     */
    public boolean isZero() {
        return (this == ZERO);
    }

    /**
     * Checks if any of the three units of this period are negative.
     * <p>
     * This checks whether the years, months or days units are less than zero.
     *
     * <p>
     *  检查此期间的三个单位是否为负。
     * <p>
     *  这将检查年,月或天单位是否小于零。
     * 
     * 
     * @return true if any unit of this period is negative
     */
    public boolean isNegative() {
        return years < 0 || months < 0 || days < 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the amount of years of this period.
     * <p>
     * This returns the years unit.
     * <p>
     * The months unit is not automatically normalized with the years unit.
     * This means that a period of "15 months" is different to a period
     * of "1 year and 3 months".
     *
     * <p>
     *  获取此期间的年数。
     * <p>
     *  这将返回年份单位。
     * <p>
     *  月份单位不会使用年份单位自动标准化。这意味着"15个月"的期间与"1年3个月"的期间不同。
     * 
     * 
     * @return the amount of years of this period, may be negative
     */
    public int getYears() {
        return years;
    }

    /**
     * Gets the amount of months of this period.
     * <p>
     * This returns the months unit.
     * <p>
     * The months unit is not automatically normalized with the years unit.
     * This means that a period of "15 months" is different to a period
     * of "1 year and 3 months".
     *
     * <p>
     *  获取此期间的月数。
     * <p>
     *  这返回月单位。
     * <p>
     *  月份单位不会使用年份单位自动标准化。这意味着"15个月"的期间与"1年3个月"的期间不同。
     * 
     * 
     * @return the amount of months of this period, may be negative
     */
    public int getMonths() {
        return months;
    }

    /**
     * Gets the amount of days of this period.
     * <p>
     * This returns the days unit.
     *
     * <p>
     *  获取此期间的天数。
     * <p>
     *  这将返回天单位。
     * 
     * 
     * @return the amount of days of this period, may be negative
     */
    public int getDays() {
        return days;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the specified amount of years.
     * <p>
     * This sets the amount of the years unit in a copy of this period.
     * The months and days units are unaffected.
     * <p>
     * The months unit is not automatically normalized with the years unit.
     * This means that a period of "15 months" is different to a period
     * of "1 year and 3 months".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回指定年数的此期间的副本。
     * <p>
     *  这将设置此期间副本中年份单位的数量。月和日单位不受影响。
     * <p>
     *  月份单位不会使用年份单位自动标准化。这意味着"15个月"的期间与"1年3个月"的期间不同。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param years  the years to represent, may be negative
     * @return a {@code Period} based on this period with the requested years, not null
     */
    public Period withYears(int years) {
        if (years == this.years) {
            return this;
        }
        return create(years, months, days);
    }

    /**
     * Returns a copy of this period with the specified amount of months.
     * <p>
     * This sets the amount of the months unit in a copy of this period.
     * The years and days units are unaffected.
     * <p>
     * The months unit is not automatically normalized with the years unit.
     * This means that a period of "15 months" is different to a period
     * of "1 year and 3 months".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回指定月数的此期间的副本。
     * <p>
     *  这将设置此期间副本中月份单位的数量。年和日单位不受影响。
     * <p>
     *  月份单位不会使用年份单位自动标准化。这意味着"15个月"的期间与"1年3个月"的期间不同。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param months  the months to represent, may be negative
     * @return a {@code Period} based on this period with the requested months, not null
     */
    public Period withMonths(int months) {
        if (months == this.months) {
            return this;
        }
        return create(years, months, days);
    }

    /**
     * Returns a copy of this period with the specified amount of days.
     * <p>
     * This sets the amount of the days unit in a copy of this period.
     * The years and months units are unaffected.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回指定天数的此期间的副本。
     * <p>
     *  这将设置此期间副本中的天单位数量。年和月单位不受影响。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param days  the days to represent, may be negative
     * @return a {@code Period} based on this period with the requested days, not null
     */
    public Period withDays(int days) {
        if (days == this.days) {
            return this;
        }
        return create(years, months, days);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the specified period added.
     * <p>
     * This operates separately on the years, months and days.
     * No normalization is performed.
     * <p>
     * For example, "1 year, 6 months and 3 days" plus "2 years, 2 months and 2 days"
     * returns "3 years, 8 months and 5 days".
     * <p>
     * The specified amount is typically an instance of {@code Period}.
     * Other types are interpreted using {@link Period#from(TemporalAmount)}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回添加了指定时间段的此期间的副本。
     * <p>
     *  这是单独操作的年,月和日。不执行归一化。
     * <p>
     *  例如,"1年,6个月和3天"加上"2年,2个月和2天"返回"3年8个月5天"。
     * <p>
     *  指定的金额通常是{@code Period}的实例。其他类型使用{@link Period#from(TemporalAmount)}解释。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the period to add, not null
     * @return a {@code Period} based on this period with the requested period added, not null
     * @throws DateTimeException if the specified amount has a non-ISO chronology or
     *  contains an invalid unit
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period plus(TemporalAmount amountToAdd) {
        Period isoAmount = Period.from(amountToAdd);
        return create(
                Math.addExact(years, isoAmount.years),
                Math.addExact(months, isoAmount.months),
                Math.addExact(days, isoAmount.days));
    }

    /**
     * Returns a copy of this period with the specified years added.
     * <p>
     * This adds the amount to the years unit in a copy of this period.
     * The months and days units are unaffected.
     * For example, "1 year, 6 months and 3 days" plus 2 years returns "3 years, 6 months and 3 days".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回添加了指定年份的此期间的副本。
     * <p>
     *  这会将此金额添加到此期间的副本中的年份单位。月和日单位不受影响。例如,"1年,6个月和3天"加2年返回"3年,6个月和3天"。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param yearsToAdd  the years to add, positive or negative
     * @return a {@code Period} based on this period with the specified years added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period plusYears(long yearsToAdd) {
        if (yearsToAdd == 0) {
            return this;
        }
        return create(Math.toIntExact(Math.addExact(years, yearsToAdd)), months, days);
    }

    /**
     * Returns a copy of this period with the specified months added.
     * <p>
     * This adds the amount to the months unit in a copy of this period.
     * The years and days units are unaffected.
     * For example, "1 year, 6 months and 3 days" plus 2 months returns "1 year, 8 months and 3 days".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回添加了指定月份的此期间的副本。
     * <p>
     *  这会将金额添加到此期间的副本中的月份单位。年和日单位不受影响。例如,"1年,6个月和3天"加2个月返回"1年,8个月和3天"。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param monthsToAdd  the months to add, positive or negative
     * @return a {@code Period} based on this period with the specified months added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period plusMonths(long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }
        return create(years, Math.toIntExact(Math.addExact(months, monthsToAdd)), days);
    }

    /**
     * Returns a copy of this period with the specified days added.
     * <p>
     * This adds the amount to the days unit in a copy of this period.
     * The years and months units are unaffected.
     * For example, "1 year, 6 months and 3 days" plus 2 days returns "1 year, 6 months and 5 days".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回添加了指定天数的此期间的副本。
     * <p>
     *  这会将金额添加到此期间的副本中的days单位。年和月单位不受影响。例如,"1年,6个月和3天"加2天返回"1年,6个月和5天"。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param daysToAdd  the days to add, positive or negative
     * @return a {@code Period} based on this period with the specified days added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period plusDays(long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        return create(years, months, Math.toIntExact(Math.addExact(days, daysToAdd)));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the specified period subtracted.
     * <p>
     * This operates separately on the years, months and days.
     * No normalization is performed.
     * <p>
     * For example, "1 year, 6 months and 3 days" minus "2 years, 2 months and 2 days"
     * returns "-1 years, 4 months and 1 day".
     * <p>
     * The specified amount is typically an instance of {@code Period}.
     * Other types are interpreted using {@link Period#from(TemporalAmount)}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此周期的副本,减去指定的周期。
     * <p>
     *  这是单独操作的年,月和日。不执行归一化。
     * <p>
     *  例如,"1年,6个月和3天"减"2年,2个月和2天"返回"-1年,4个月和1天"。
     * <p>
     *  指定的金额通常是{@code Period}的实例。其他类型使用{@link Period#from(TemporalAmount)}解释。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the period to subtract, not null
     * @return a {@code Period} based on this period with the requested period subtracted, not null
     * @throws DateTimeException if the specified amount has a non-ISO chronology or
     *  contains an invalid unit
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period minus(TemporalAmount amountToSubtract) {
        Period isoAmount = Period.from(amountToSubtract);
        return create(
                Math.subtractExact(years, isoAmount.years),
                Math.subtractExact(months, isoAmount.months),
                Math.subtractExact(days, isoAmount.days));
    }

    /**
     * Returns a copy of this period with the specified years subtracted.
     * <p>
     * This subtracts the amount from the years unit in a copy of this period.
     * The months and days units are unaffected.
     * For example, "1 year, 6 months and 3 days" minus 2 years returns "-1 years, 6 months and 3 days".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回减去指定年份的此期间的副本。
     * <p>
     *  这将减去该期间副本中年份单位的金额。月和日单位不受影响。例如,"1年,6个月和3天"减去2年返回"-1年,6个月和3天"。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param yearsToSubtract  the years to subtract, positive or negative
     * @return a {@code Period} based on this period with the specified years subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period minusYears(long yearsToSubtract) {
        return (yearsToSubtract == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
    }

    /**
     * Returns a copy of this period with the specified months subtracted.
     * <p>
     * This subtracts the amount from the months unit in a copy of this period.
     * The years and days units are unaffected.
     * For example, "1 year, 6 months and 3 days" minus 2 months returns "1 year, 4 months and 3 days".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此期间减去指定月份的副本。
     * <p>
     *  这将从此期间的副本中减去月份单位的金额。年和日单位不受影响。例如,"1年,6个月和3天"减去2个月返回"1年,4个月和3天"。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param monthsToSubtract  the years to subtract, positive or negative
     * @return a {@code Period} based on this period with the specified months subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period minusMonths(long monthsToSubtract) {
        return (monthsToSubtract == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-monthsToSubtract));
    }

    /**
     * Returns a copy of this period with the specified days subtracted.
     * <p>
     * This subtracts the amount from the days unit in a copy of this period.
     * The years and months units are unaffected.
     * For example, "1 year, 6 months and 3 days" minus 2 days returns "1 year, 6 months and 1 day".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回减去指定天数的此期间的副本。
     * <p>
     *  这将从此期间的副本中减去days单位的金额。年和月单位不受影响。例如,"1年,6个月和3天"减去2天返回"1年,6个月和1天"。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param daysToSubtract  the months to subtract, positive or negative
     * @return a {@code Period} based on this period with the specified days subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period minusDays(long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-daysToSubtract));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a new instance with each element in this period multiplied
     * by the specified scalar.
     * <p>
     * This returns a period with each of the years, months and days units
     * individually multiplied.
     * For example, a period of "2 years, -3 months and 4 days" multiplied by
     * 3 will return "6 years, -9 months and 12 days".
     * No normalization is performed.
     *
     * <p>
     *  返回一个新实例,此周期中的每个元素都乘以指定的标量。
     * <p>
     *  这将返回一个期间,每年的年,月和日单位单独乘。例如,"2年,-3个月和4天"的周期乘以3将返回"6年,-9个月和12天"。不执行归一化。
     * 
     * 
     * @param scalar  the scalar to multiply by, not null
     * @return a {@code Period} based on this period with the amounts multiplied by the scalar, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period multipliedBy(int scalar) {
        if (this == ZERO || scalar == 1) {
            return this;
        }
        return create(
                Math.multiplyExact(years, scalar),
                Math.multiplyExact(months, scalar),
                Math.multiplyExact(days, scalar));
    }

    /**
     * Returns a new instance with each amount in this period negated.
     * <p>
     * This returns a period with each of the years, months and days units
     * individually negated.
     * For example, a period of "2 years, -3 months and 4 days" will be
     * negated to "-2 years, 3 months and -4 days".
     * No normalization is performed.
     *
     * <p>
     *  返回一个新实例,其中此期间的每个金额都被取消。
     * <p>
     *  这将返回一个期间,每年的年,月和日单位被单独否定。例如,"2年,-3个月和4天"的期间将被取消为"-2年,3个月和-4天"。不执行归一化。
     * 
     * 
     * @return a {@code Period} based on this period with the amounts negated, not null
     * @throws ArithmeticException if numeric overflow occurs, which only happens if
     *  one of the units has the value {@code Long.MIN_VALUE}
     */
    public Period negated() {
        return multipliedBy(-1);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the years and months normalized.
     * <p>
     * This normalizes the years and months units, leaving the days unit unchanged.
     * The months unit is adjusted to have an absolute value less than 11,
     * with the years unit being adjusted to compensate. For example, a period of
     * "1 Year and 15 months" will be normalized to "2 years and 3 months".
     * <p>
     * The sign of the years and months units will be the same after normalization.
     * For example, a period of "1 year and -25 months" will be normalized to
     * "-1 year and -1 month".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此期间的经过归一化的年份和月份的副本。
     * <p>
     * 这将使年和月单位标准化,使天单位不变。月份单位调整为具有小于11的绝对值,其中年份单位被调整以补偿。例如,"1年15个月"的期间将标准化为"2年3个月"。
     * <p>
     *  年和月单位的符号在归一化后将是相同的。例如,"1年和-25个月"的时间段将被规范化为"-1年和-1个月"。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return a {@code Period} based on this period with excess months normalized to years, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    public Period normalized() {
        long totalMonths = toTotalMonths();
        long splitYears = totalMonths / 12;
        int splitMonths = (int) (totalMonths % 12);  // no overflow
        if (splitYears == years && splitMonths == months) {
            return this;
        }
        return create(Math.toIntExact(splitYears), splitMonths, days);
    }

    /**
     * Gets the total number of months in this period.
     * <p>
     * This returns the total number of months in the period by multiplying the
     * number of years by 12 and adding the number of months.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  获取此期间的总月数。
     * <p>
     *  这将通过将年数乘以12并加上月数来返回期间内的总月数。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return the total number of months in the period, may be negative
     */
    public long toTotalMonths() {
        return years * 12L + months;  // no overflow
    }

    //-------------------------------------------------------------------------
    /**
     * Adds this period to the specified temporal object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with this period added.
     * If the temporal has a chronology, it must be the ISO chronology.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#plus(TemporalAmount)}.
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   dateTime = thisPeriod.addTo(dateTime);
     *   dateTime = dateTime.plus(thisPeriod);
     * </pre>
     * <p>
     * The calculation operates as follows.
     * First, the chronology of the temporal is checked to ensure it is ISO chronology or null.
     * Second, if the months are zero, the years are added if non-zero, otherwise
     * the combination of years and months is added if non-zero.
     * Finally, any days are added.
     * <p>
     * This approach ensures that a partial period can be added to a partial date.
     * For example, a period of years and/or months can be added to a {@code YearMonth},
     * but a period including days cannot.
     * The approach also adds years and months together when necessary, which ensures
     * correct behaviour at the end of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将此期间添加到指定的临时对象。
     * <p>
     *  这返回与添加了此句点的输入相同的observable类型的时间对象。如果时间有一个年表,它必须是ISO年表。
     * <p>
     *  在大多数情况下,使用{@link Temporal#plus(TemporalAmount)}来反转调用模式是更清楚的。
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐dateTime = thisPeriod.addTo(dateTime); dateTime = dateTime.plus(thisPeriod);
     * </pre>
     * <p>
     * 计算操作如下。首先,检查时间的年表,以确保它是ISO年表或null。第二,如果月份为零,则在非零时添加年份,否则,如果非零,则添加年份和月份的组合。最后,添加任何天。
     * <p>
     *  此方法可确保将部分期间添加到部分日期。例如,年份和/或月份的期间可以添加到{@code YearMonth},但包括天的期间不能。该方法还在需要时将年和月加在一起,这确保了月底的正确行为。
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
        validateChrono(temporal);
        if (months == 0) {
            if (years != 0) {
                temporal = temporal.plus(years, YEARS);
            }
        } else {
            long totalMonths = toTotalMonths();
            if (totalMonths != 0) {
                temporal = temporal.plus(totalMonths, MONTHS);
            }
        }
        if (days != 0) {
            temporal = temporal.plus(days, DAYS);
        }
        return temporal;
    }

    /**
     * Subtracts this period from the specified temporal object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with this period subtracted.
     * If the temporal has a chronology, it must be the ISO chronology.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#minus(TemporalAmount)}.
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   dateTime = thisPeriod.subtractFrom(dateTime);
     *   dateTime = dateTime.minus(thisPeriod);
     * </pre>
     * <p>
     * The calculation operates as follows.
     * First, the chronology of the temporal is checked to ensure it is ISO chronology or null.
     * Second, if the months are zero, the years are subtracted if non-zero, otherwise
     * the combination of years and months is subtracted if non-zero.
     * Finally, any days are subtracted.
     * <p>
     * This approach ensures that a partial period can be subtracted from a partial date.
     * For example, a period of years and/or months can be subtracted from a {@code YearMonth},
     * but a period including days cannot.
     * The approach also subtracts years and months together when necessary, which ensures
     * correct behaviour at the end of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  从指定的时间对象中减去此时间段。
     * <p>
     *  这将返回与减去此周期的输入相同的observable类型的时间对象。如果时间有一个年表,它必须是ISO年表。
     * <p>
     *  在大多数情况下,使用{@link Temporal#minus(TemporalAmount)}来反转调用模式是更清楚的。
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐dateTime = thisPeriod.subtractFrom(dateTime); dateTime = dateTime.minus(thisPerio
     * d);。
     * </pre>
     * <p>
     *  计算操作如下。首先,检查时间的年表,以确保它是ISO年表或null。第二,如果月份为零,则如果非零则减去年份,否则如果非零则减去年份和月份的组合。最后,减去任何天数。
     * <p>
     * 这种方法确保可以从部分日期中减去部分期间。例如,可以从{@code YearMonth}中减去年和/或月的期间,但包括天的期间不能。该方法还在必要时一起减去年和月,这确保在月底的正确行为。
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
        validateChrono(temporal);
        if (months == 0) {
            if (years != 0) {
                temporal = temporal.minus(years, YEARS);
            }
        } else {
            long totalMonths = toTotalMonths();
            if (totalMonths != 0) {
                temporal = temporal.minus(totalMonths, MONTHS);
            }
        }
        if (days != 0) {
            temporal = temporal.minus(days, DAYS);
        }
        return temporal;
    }

    /**
     * Validates that the temporal has the correct chronology.
     * <p>
     *  验证时间具有正确的年表。
     * 
     */
    private void validateChrono(TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        Chronology temporalChrono = temporal.query(TemporalQueries.chronology());
        if (temporalChrono != null && IsoChronology.INSTANCE.equals(temporalChrono) == false) {
            throw new DateTimeException("Chronology mismatch, expected: ISO, actual: " + temporalChrono.getId());
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this period is equal to another period.
     * <p>
     * The comparison is based on the type {@code Period} and each of the three amounts.
     * To be equal, the years, months and days units must be individually equal.
     * Note that this means that a period of "15 Months" is not equal to a period
     * of "1 Year and 3 Months".
     *
     * <p>
     *  检查此时间段是否等于另一个时间段。
     * <p>
     *  比较基于类型{@code Period}和三个金额中的每一个。为了相等,年,月和日单位必须单独相等。注意,这意味着"15个月"的期间不等于"1年3个月"的期间。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other period
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Period) {
            Period other = (Period) obj;
            return years == other.years &&
                    months == other.months &&
                    days == other.days;
        }
        return false;
    }

    /**
     * A hash code for this period.
     *
     * <p>
     *  此期间的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return years + Integer.rotateLeft(months, 8) + Integer.rotateLeft(days, 16);
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this period as a {@code String}, such as {@code P6Y3M1D}.
     * <p>
     * The output will be in the ISO-8601 period format.
     * A zero period will be represented as zero days, 'P0D'.
     *
     * <p>
     *  将此期间输出为{@code String},例如{@code P6Y3M1D}。
     * <p>
     *  输出将为ISO-8601周期格式。零周期将被表示为零天,"P0D"。
     * 
     * 
     * @return a string representation of this period, not null
     */
    @Override
    public String toString() {
        if (this == ZERO) {
            return "P0D";
        } else {
            StringBuilder buf = new StringBuilder();
            buf.append('P');
            if (years != 0) {
                buf.append(years).append('Y');
            }
            if (months != 0) {
                buf.append(months).append('M');
            }
            if (days != 0) {
                buf.append(days).append('D');
            }
            return buf.toString();
        }
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
     *  out.writeByte(14);  // identifies a Period
     *  out.writeInt(years);
     *  out.writeInt(months);
     *  out.writeInt(days);
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.PERIOD_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     *  防御恶意流。
     * 
     * @param s the stream to read
     * @throws java.io.InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeInt(years);
        out.writeInt(months);
        out.writeInt(days);
    }

    static Period readExternal(DataInput in) throws IOException {
        int years = in.readInt();
        int months = in.readInt();
        int days = in.readInt();
        return Period.of(years, months, days);
    }

}
