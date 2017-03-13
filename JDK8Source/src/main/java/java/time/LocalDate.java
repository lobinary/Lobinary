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

import static java.time.LocalTime.SECONDS_PER_DAY;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Era;
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
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.Objects;

/**
 * A date without a time-zone in the ISO-8601 calendar system,
 * such as {@code 2007-12-03}.
 * <p>
 * {@code LocalDate} is an immutable date-time object that represents a date,
 * often viewed as year-month-day. Other date fields, such as day-of-year,
 * day-of-week and week-of-year, can also be accessed.
 * For example, the value "2nd October 2007" can be stored in a {@code LocalDate}.
 * <p>
 * This class does not store or represent a time or time-zone.
 * Instead, it is a description of the date, as used for birthdays.
 * It cannot represent an instant on the time-line without additional information
 * such as an offset or time-zone.
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
 * {@code LocalDate} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 * 在ISO-8601日历系统中没有时区的日期,例如{@code 2007-12-03}
 * <p>
 *  {@code LocalDate}是一个不可变的日期时间对象,表示一个日期,通常被视为年 - 月 - 日。
 * 其他日期字段,例如年,星期和星期,可以也被访问例如,值"2007年10月2日"可以存储在{@code LocalDate}。
 * <p>
 *  此类不存储或表示时间或时区。相反,它是用于生日的日期的描述。它不能在没有附加信息(如偏移或时区)的时间线上表示一个时刻
 * <p>
 * ISO-8601日历系统是当今世界上大多数地区使用的现代民用日历系统。
 * 它相当于普通的公历日历系统,其中适用于闰年的今天规则对于今天大多数应用,ISO- 8601规则完全合适但是,任何使用历史日期并要求它们准确的应用程序都会发现ISO-8601方法不适用。
 * 
 * <p>
 *  这是<a href=\"{@docRoot}/java/lang/doc-files/ValueBasedhtml\">基于价值的</a>类;在{@code LocalDate}的实例上使用身份敏感操
 * 作(包括引用相等({@code ==}),身份哈希码或同步)可能有不可预测的结果,应该避免使用{@code equals}方法应该用于比较。
 * 
 * @implSpec这个类是不可变的和线程安全的
 * 
 * 
 * @since 1.8
 */
public final class LocalDate
        implements Temporal, TemporalAdjuster, ChronoLocalDate, Serializable {

    /**
     * The minimum supported {@code LocalDate}, '-999999999-01-01'.
     * This could be used by an application as a "far past" date.
     * <p>
     *  最低支持{@code LocalDate},'-999999999-01-01'这可以由应用程序用作"过去"日期
     * 
     */
    public static final LocalDate MIN = LocalDate.of(Year.MIN_VALUE, 1, 1);
    /**
     * The maximum supported {@code LocalDate}, '+999999999-12-31'.
     * This could be used by an application as a "far future" date.
     * <p>
     *  最大支持的{@code LocalDate},'+ 999999999-12-31'这可以被应用程序用作"远期"日期
     * 
     */
    public static final LocalDate MAX = LocalDate.of(Year.MAX_VALUE, 12, 31);

    /**
     * Serialization version.
     * <p>
     *  序列化版本
     * 
     */
    private static final long serialVersionUID = 2942565459149668126L;
    /**
     * The number of days in a 400 year cycle.
     * <p>
     *  在400年周期中的天数
     * 
     */
    private static final int DAYS_PER_CYCLE = 146097;
    /**
     * The number of days from year zero to year 1970.
     * There are five 400 year cycles from year zero to 2000.
     * There are 7 leap years from 1970 to 2000.
     * <p>
     *  从0年到1970年的天数从0年到2000年有5个400年周期从1970年到2000年有7个闰年
     * 
     */
    static final long DAYS_0000_TO_1970 = (DAYS_PER_CYCLE * 5L) - (30L * 365L + 7L);

    /**
     * The year.
     * <p>
     *  那一年
     * 
     */
    private final int year;
    /**
     * The month-of-year.
     * <p>
     *  一年中的月份
     * 
     */
    private final short month;
    /**
     * The day-of-month.
     * <p>
     *  日期
     * 
     */
    private final short day;

    //-----------------------------------------------------------------------
    /**
     * Obtains the current date from the system clock in the default time-zone.
     * <p>
     * This will query the {@link Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current date.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区中从系统时钟获取当前日期
     * <p>
     *  这将在默认时区中查询{@link Clock#systemDefaultZone()系统时钟}以获取当前日期
     * <p>
     * 使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的
     * 
     * 
     * @return the current date using the system clock and default time-zone, not null
     */
    public static LocalDate now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current date from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区中从系统时钟获取当前日期
     * <p>
     *  这将查询{@link Clock#system(ZoneId)系统时钟}以获取当前日期指定时区避免依赖于默认时区
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current date using the system clock, not null
     */
    public static LocalDate now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current date from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current date - today.
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前日期
     * <p>
     *  这将查询指定的时钟以获取当前日期 - 今天使用此方法允许使用备用时钟进行测试备用时钟可以使用{@link时钟依赖注入}
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current date, not null
     */
    public static LocalDate now(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        // inline to avoid creating object and Instant checks
        final Instant now = clock.instant();  // called once
        ZoneOffset offset = clock.getZone().getRules().getOffset(now);
        long epochSec = now.getEpochSecond() + offset.getTotalSeconds();  // overflow caught later
        long epochDay = Math.floorDiv(epochSec, SECONDS_PER_DAY);
        return LocalDate.ofEpochDay(epochDay);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code LocalDate} from a year, month and day.
     * <p>
     * This returns a {@code LocalDate} with the specified year, month and day-of-month.
     * The day must be valid for the year and month, otherwise an exception will be thrown.
     *
     * <p>
     * 从年,月和日获取{@code LocalDate}的实例
     * <p>
     *  这将返回带有指定的年,月和日的{@code LocalDate}日期必须对年和月有效,否则将抛出异常
     * 
     * 
     * @param year  the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, not null
     * @param dayOfMonth  the day-of-month to represent, from 1 to 31
     * @return the local date, not null
     * @throws DateTimeException if the value of any field is out of range,
     *  or if the day-of-month is invalid for the month-year
     */
    public static LocalDate of(int year, Month month, int dayOfMonth) {
        YEAR.checkValidValue(year);
        Objects.requireNonNull(month, "month");
        DAY_OF_MONTH.checkValidValue(dayOfMonth);
        return create(year, month.getValue(), dayOfMonth);
    }

    /**
     * Obtains an instance of {@code LocalDate} from a year, month and day.
     * <p>
     * This returns a {@code LocalDate} with the specified year, month and day-of-month.
     * The day must be valid for the year and month, otherwise an exception will be thrown.
     *
     * <p>
     *  从年,月和日获取{@code LocalDate}的实例
     * <p>
     *  这将返回带有指定的年,月和日的{@code LocalDate}日期必须对年和月有效,否则将抛出异常
     * 
     * 
     * @param year  the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, from 1 (January) to 12 (December)
     * @param dayOfMonth  the day-of-month to represent, from 1 to 31
     * @return the local date, not null
     * @throws DateTimeException if the value of any field is out of range,
     *  or if the day-of-month is invalid for the month-year
     */
    public static LocalDate of(int year, int month, int dayOfMonth) {
        YEAR.checkValidValue(year);
        MONTH_OF_YEAR.checkValidValue(month);
        DAY_OF_MONTH.checkValidValue(dayOfMonth);
        return create(year, month, dayOfMonth);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code LocalDate} from a year and day-of-year.
     * <p>
     * This returns a {@code LocalDate} with the specified year and day-of-year.
     * The day-of-year must be valid for the year, otherwise an exception will be thrown.
     *
     * <p>
     *  从一年和一年中获取{@code LocalDate}的实例
     * <p>
     *  这将返回一个带有指定年份和年份的{@code LocalDate}年份必须对该年有效,否则将抛出异常
     * 
     * 
     * @param year  the year to represent, from MIN_YEAR to MAX_YEAR
     * @param dayOfYear  the day-of-year to represent, from 1 to 366
     * @return the local date, not null
     * @throws DateTimeException if the value of any field is out of range,
     *  or if the day-of-year is invalid for the month-year
     */
    public static LocalDate ofYearDay(int year, int dayOfYear) {
        YEAR.checkValidValue(year);
        DAY_OF_YEAR.checkValidValue(dayOfYear);
        boolean leap = IsoChronology.INSTANCE.isLeapYear(year);
        if (dayOfYear == 366 && leap == false) {
            throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + year + "' is not a leap year");
        }
        Month moy = Month.of((dayOfYear - 1) / 31 + 1);
        int monthEnd = moy.firstDayOfYear(leap) + moy.length(leap) - 1;
        if (dayOfYear > monthEnd) {
            moy = moy.plus(1);
        }
        int dom = dayOfYear - moy.firstDayOfYear(leap) + 1;
        return new LocalDate(year, moy.getValue(), dom);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code LocalDate} from the epoch day count.
     * <p>
     * This returns a {@code LocalDate} with the specified epoch-day.
     * The {@link ChronoField#EPOCH_DAY EPOCH_DAY} is a simple incrementing count
     * of days where day 0 is 1970-01-01. Negative numbers represent earlier days.
     *
     * <p>
     *  从时代天计数获取{@code LocalDate}的实例
     * <p>
     * 这将返回一个带有指定的纪元日的{@code LocalDate} {@link ChronoField#EPOCH_DAY EPOCH_DAY}是一个简单的递增计数,其中第0天为1970-01-01负数
     * 表示早期。
     * 
     * 
     * @param epochDay  the Epoch Day to convert, based on the epoch 1970-01-01
     * @return the local date, not null
     * @throws DateTimeException if the epoch days exceeds the supported date range
     */
    public static LocalDate ofEpochDay(long epochDay) {
        long zeroDay = epochDay + DAYS_0000_TO_1970;
        // find the march-based year
        zeroDay -= 60;  // adjust to 0000-03-01 so leap day is at end of four year cycle
        long adjust = 0;
        if (zeroDay < 0) {
            // adjust negative years to positive for calculation
            long adjustCycles = (zeroDay + 1) / DAYS_PER_CYCLE - 1;
            adjust = adjustCycles * 400;
            zeroDay += -adjustCycles * DAYS_PER_CYCLE;
        }
        long yearEst = (400 * zeroDay + 591) / DAYS_PER_CYCLE;
        long doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);
        if (doyEst < 0) {
            // fix estimate
            yearEst--;
            doyEst = zeroDay - (365 * yearEst + yearEst / 4 - yearEst / 100 + yearEst / 400);
        }
        yearEst += adjust;  // reset any negative year
        int marchDoy0 = (int) doyEst;

        // convert march-based values back to january-based
        int marchMonth0 = (marchDoy0 * 5 + 2) / 153;
        int month = (marchMonth0 + 2) % 12 + 1;
        int dom = marchDoy0 - (marchMonth0 * 306 + 5) / 10 + 1;
        yearEst += marchMonth0 / 10;

        // check year now we are certain it is correct
        int year = YEAR.checkValidIntValue(yearEst);
        return new LocalDate(year, month, dom);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code LocalDate} from a temporal object.
     * <p>
     * This obtains a local date based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code LocalDate}.
     * <p>
     * The conversion uses the {@link TemporalQueries#localDate()} query, which relies
     * on extracting the {@link ChronoField#EPOCH_DAY EPOCH_DAY} field.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code LocalDate::from}.
     *
     * <p>
     *  从临时对象获取{@code LocalDate}的实例
     * <p>
     *  这将根据指定的时间获得一个本地日期A {@code TemporalAccessor}表示一组任意的日期和时间信息,该工厂转换为{@code LocalDate}的实例,
     * <p>
     *  转换使用{@link TemporalQueries#localDate()}查询,该查询依赖于提取{@link ChronoField#EPOCH_DAY EPOCH_DAY}字段
     * <p>
     * 此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用用作查询,{@code LocalDate :: from}
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the local date, not null
     * @throws DateTimeException if unable to convert to a {@code LocalDate}
     */
    public static LocalDate from(TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        LocalDate date = temporal.query(TemporalQueries.localDate());
        if (date == null) {
            throw new DateTimeException("Unable to obtain LocalDate from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName());
        }
        return date;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code LocalDate} from a text string such as {@code 2007-12-03}.
     * <p>
     * The string must represent a valid date and is parsed using
     * {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE}.
     *
     * <p>
     *  从文本字符串(例如{@code 2007-12-03})获取{@code LocalDate}的实例,
     * <p>
     *  该字符串必须表示有效的日期,并使用{@link javatimeformatDateTimeFormatter#ISO_LOCAL_DATE}进行解析
     * 
     * 
     * @param text  the text to parse such as "2007-12-03", not null
     * @return the parsed local date, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static LocalDate parse(CharSequence text) {
        return parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Obtains an instance of {@code LocalDate} from a text string using a specific formatter.
     * <p>
     * The text is parsed using the formatter, returning a date.
     *
     * <p>
     *  使用特定格式化程序从文本字符串获取{@code LocalDate}的实例
     * <p>
     *  使用格式化程序解析文本,返回日期
     * 
     * 
     * @param text  the text to parse, not null
     * @param formatter  the formatter to use, not null
     * @return the parsed local date, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static LocalDate parse(CharSequence text, DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.parse(text, LocalDate::from);
    }

    //-----------------------------------------------------------------------
    /**
     * Creates a local date from the year, month and day fields.
     *
     * <p>
     *  从年,月和日字段创建本地日期
     * 
     * 
     * @param year  the year to represent, validated from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, from 1 to 12, validated
     * @param dayOfMonth  the day-of-month to represent, validated from 1 to 31
     * @return the local date, not null
     * @throws DateTimeException if the day-of-month is invalid for the month-year
     */
    private static LocalDate create(int year, int month, int dayOfMonth) {
        if (dayOfMonth > 28) {
            int dom = 31;
            switch (month) {
                case 2:
                    dom = (IsoChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dom = 30;
                    break;
            }
            if (dayOfMonth > dom) {
                if (dayOfMonth == 29) {
                    throw new DateTimeException("Invalid date 'February 29' as '" + year + "' is not a leap year");
                } else {
                    throw new DateTimeException("Invalid date '" + Month.of(month).name() + " " + dayOfMonth + "'");
                }
            }
        }
        return new LocalDate(year, month, dayOfMonth);
    }

    /**
     * Resolves the date, resolving days past the end of month.
     *
     * <p>
     *  解决日期,解决月底之前的天数
     * 
     * 
     * @param year  the year to represent, validated from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, validated from 1 to 12
     * @param day  the day-of-month to represent, validated from 1 to 31
     * @return the resolved date, not null
     */
    private static LocalDate resolvePreviousValid(int year, int month, int day) {
        switch (month) {
            case 2:
                day = Math.min(day, IsoChronology.INSTANCE.isLeapYear(year) ? 29 : 28);
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = Math.min(day, 30);
                break;
        }
        return new LocalDate(year, month, day);
    }

    /**
     * Constructor, previously validated.
     *
     * <p>
     *  构造函数,先前已验证
     * 
     * 
     * @param year  the year to represent, from MIN_YEAR to MAX_YEAR
     * @param month  the month-of-year to represent, not null
     * @param dayOfMonth  the day-of-month to represent, valid for year-month, from 1 to 31
     */
    private LocalDate(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = (short) month;
        this.day = (short) dayOfMonth;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this date can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range},
     * {@link #get(TemporalField) get} and {@link #with(TemporalField, long)}
     * methods will throw an exception.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The supported fields are:
     * <ul>
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
     * 这将检查是否可以查询指定字段的此日期如果为false,则调用{@link #range(TemporalField)范围},{@link #get(TemporalField)get}和{@link #with(TemporalField,long) }
     * 方法将抛出异常。
     * <p>
     *  如果字段是{@link ChronoField},则在此实现查询支持的字段为：
     * <ul>
     *  <li> {@ code DAY_OF_WEEK} <li> {@ code ALIGNED_DAY_OF_WEEK_IN_MONTH} <li> {@ code ALIGNED_DAY_OF_WEEK_IN_YEAR}
     *  <li> {@ code DAY_OF_MONTH} <li> {@ code DAY_OF_YEAR} <li> {@ code EPOCH_DAY} > {@ code ALIGNED_WEEK_OF_MONTH}
     *  <li> {@ code ALIGNED_WEEK_OF_YEAR} <li> {@ code MONTH_OF_YEAR} <li> {@ code PROLEPTIC_MONTH} <li> {@ code YEAR_OF_ERA}
     *  <li> {@ code YEAR} @code ERA}。
     * </ul>
     *  所有其他{@code ChronoField}实例将返回false
     * <p>
     * 如果字段不是{@code ChronoField},则通过调用{@code TemporalFieldisSupportedBy(TemporalAccessor)}传递{@code this}作为参数
     * 来获得此方法的结果。
     * 字段是否受支持由字段。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this date, false if not
     */
    @Override  // override for Javadoc
    public boolean isSupported(TemporalField field) {
        return ChronoLocalDate.super.isSupported(field);
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
     *  检查是否支持指定的单元
     * <p>
     *  这将检查指定的单元是否可以添加到此日期时间或从此日期时间减去。
     * 如果为false,则调用{@link #plus(long,TemporalUnit)}和{@link #minus(long,TemporalUnit)minus}会抛出异常。
     * <p>
     *  如果单位是{@link ChronoUnit},则在此实现查询支持的单位是：
     * <ul>
     *  <li> {@ code DAYS} <li> {@ code WEEKS} <li> {@ code MONTHS} <li> {@ code YEARS} <li> {@ code DECADES}
     *  <li> > {@ code MILLENNIA} <li> {@ code ERAS}。
     * </ul>
     * 所有其他{@code ChronoUnit}实例将返回false
     * <p>
     *  如果单元不是{@code ChronoUnit},则通过调用{@code TemporalUnitisSupportedBy(Temporal)}传递{@code this}作为参数获得此方法的结果。
     * 单元是否受支持由单元确定。
     * 
     * 
     * @param unit  the unit to check, null returns false
     * @return true if the unit can be added/subtracted, false if not
     */
    @Override  // override for Javadoc
    public boolean isSupported(TemporalUnit unit) {
        return ChronoLocalDate.super.isSupported(unit);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This date is used to enhance the accuracy of the returned range.
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
     *  范围对象表示字段的最小和最大有效值此日期用于提高返回范围的准确性如果不可能返回范围,因为该字段不受支持或由于某种其他原因,异常是抛出
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
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            if (f.isDateBased()) {
                switch (f) {
                    case DAY_OF_MONTH: return ValueRange.of(1, lengthOfMonth());
                    case DAY_OF_YEAR: return ValueRange.of(1, lengthOfYear());
                    case ALIGNED_WEEK_OF_MONTH: return ValueRange.of(1, getMonth() == Month.FEBRUARY && isLeapYear() == false ? 4 : 5);
                    case YEAR_OF_ERA:
                        return (getYear() <= 0 ? ValueRange.of(1, Year.MAX_VALUE + 1) : ValueRange.of(1, Year.MAX_VALUE));
                }
                return field.range();
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.rangeRefinedBy(this);
    }

    /**
     * Gets the value of the specified field from this date as an {@code int}.
     * <p>
     * This queries this date for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this date, except {@code EPOCH_DAY} and {@code PROLEPTIC_MONTH}
     * which are too large to fit in an {@code int} and throw a {@code DateTimeException}.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此日期获取指定字段的值作为{@code int}
     * <p>
     * 这将为该日期查询指定字段的值返回的值将始终在该字段的有效值范围内如果不可能返回该值,因为该字段不受支持或由于某种其他原因,被抛出
     * <p>
     *  如果该字段是{@link ChronoField},则此处实现查询。
     * {@link #isSupported(TemporalField)supported fields}将根据此日期返回有效值,{@code EPOCH_DAY}和{@code PROLEPTIC_MONTH}
     * 除外太大,无法适应{@code int}并抛出{@code DateTimeException}所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}
     * 。
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
    @Override  // override for Javadoc and performance
    public int get(TemporalField field) {
        if (field instanceof ChronoField) {
            return get0(field);
        }
        return ChronoLocalDate.super.get(field);
    }

    /**
     * Gets the value of the specified field from this date as a {@code long}.
     * <p>
     * This queries this date for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is a {@link ChronoField} then the query is implemented here.
     * The {@link #isSupported(TemporalField) supported fields} will return valid
     * values based on this date.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从此日期获取指定字段的值作为{@code long}
     * <p>
     *  这将查询指定字段的值的此日期如果不可能返回值,因为该字段不受支持或由于某种其他原因,抛出异常
     * <p>
     * 如果字段是{@link ChronoField},则在此实现查询。
     * {@link #isSupported(TemporalField)supported fields}将根据此日期返回有效值所有其他{@code ChronoField}实例将抛出一个{@code UnsupportedTemporalTypeException}
     * 。
     * 如果字段是{@link ChronoField},则在此实现查询。
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
            if (field == EPOCH_DAY) {
                return toEpochDay();
            }
            if (field == PROLEPTIC_MONTH) {
                return getProlepticMonth();
            }
            return get0(field);
        }
        return field.getFrom(this);
    }

    private int get0(TemporalField field) {
        switch ((ChronoField) field) {
            case DAY_OF_WEEK: return getDayOfWeek().getValue();
            case ALIGNED_DAY_OF_WEEK_IN_MONTH: return ((day - 1) % 7) + 1;
            case ALIGNED_DAY_OF_WEEK_IN_YEAR: return ((getDayOfYear() - 1) % 7) + 1;
            case DAY_OF_MONTH: return day;
            case DAY_OF_YEAR: return getDayOfYear();
            case EPOCH_DAY: throw new UnsupportedTemporalTypeException("Invalid field 'EpochDay' for get() method, use getLong() instead");
            case ALIGNED_WEEK_OF_MONTH: return ((day - 1) / 7) + 1;
            case ALIGNED_WEEK_OF_YEAR: return ((getDayOfYear() - 1) / 7) + 1;
            case MONTH_OF_YEAR: return month;
            case PROLEPTIC_MONTH: throw new UnsupportedTemporalTypeException("Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            case YEAR_OF_ERA: return (year >= 1 ? year : 1 - year);
            case YEAR: return year;
            case ERA: return (year >= 1 ? 1 : 0);
        }
        throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
    }

    private long getProlepticMonth() {
        return (year * 12L + month - 1);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the chronology of this date, which is the ISO calendar system.
     * <p>
     * The {@code Chronology} represents the calendar system in use.
     * The ISO-8601 calendar system is the modern civil calendar system used today
     * in most of the world. It is equivalent to the proleptic Gregorian calendar
     * system, in which today's rules for leap years are applied for all time.
     *
     * <p>
     *  获取此日期的年表,这是ISO日历系统
     * <p>
     * {@code Chronology}代表正在使用的日历系统ISO-8601日历系统是当今世界上大多数地区使用的现代民用日历系统。它相当于普通的公历日历系统,其中应用了今天的闰年规则所有时间
     * 
     * 
     * @return the ISO chronology, not null
     */
    @Override
    public IsoChronology getChronology() {
        return IsoChronology.INSTANCE;
    }

    /**
     * Gets the era applicable at this date.
     * <p>
     * The official ISO-8601 standard does not define eras, however {@code IsoChronology} does.
     * It defines two eras, 'CE' from year one onwards and 'BCE' from year zero backwards.
     * Since dates before the Julian-Gregorian cutover are not in line with history,
     * the cutover between 'BCE' and 'CE' is also not aligned with the commonly used
     * eras, often referred to using 'BC' and 'AD'.
     * <p>
     * Users of this class should typically ignore this method as it exists primarily
     * to fulfill the {@link ChronoLocalDate} contract where it is necessary to support
     * the Japanese calendar system.
     * <p>
     * The returned era will be a singleton capable of being compared with the constants
     * in {@link IsoChronology} using the {@code ==} operator.
     *
     * <p>
     *  获取此日期适用的时代
     * <p>
     *  官方的ISO-8601标准没有定义时代,然而{@code IsoChronology}却定义了两个时代,从第一年起的"CE"和从零年开始的"BCE"。
     * 因为在朱利安 - 格里高利切换之前的日期不一致历史上,"BCE"和"CE"之间的转换也不与常用的时代对齐,通常使用"BC"和"AD"。
     * <p>
     * 这个类的用户通常应该忽略这个方法,因为它存在主要是为了履行{@link ChronoLocalDate}合同,有必要支持日本日历系统
     * <p>
     *  返回的时代将是一个单例,能够使用{@code ==}运算符与{@link IsoChronology}中的常量进行比较
     * 
     * 
     * @return the {@code IsoChronology} era constant applicable at this date, not null
     */
    @Override // override for Javadoc
    public Era getEra() {
        return ChronoLocalDate.super.getEra();
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
     *  获取年份字段
     * <p>
     *  此方法返回年份的原始{@code int}值
     * <p>
     *  这个方法返回的年份是{@code get(YEAR)}。为了获得年代,使用{@code get(YEAR_OF_ERA)}
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
     *  此方法将{@code int}从1到12的月份返回为应用程序代码通常更清晰,如果调用{@link #getMonth()}使用枚举{@link Month}
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
     * 使用{@code Month}枚举获取年份字段
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

    /**
     * Gets the day-of-month field.
     * <p>
     * This method returns the primitive {@code int} value for the day-of-month.
     *
     * <p>
     *  获取日期字段
     * <p>
     *  此方法返回日期的原始{@code int}值
     * 
     * 
     * @return the day-of-month, from 1 to 31
     */
    public int getDayOfMonth() {
        return day;
    }

    /**
     * Gets the day-of-year field.
     * <p>
     * This method returns the primitive {@code int} value for the day-of-year.
     *
     * <p>
     *  获取年份字段
     * <p>
     *  此方法返回一年中的原始{@code int}值
     * 
     * 
     * @return the day-of-year, from 1 to 365, or 366 in a leap year
     */
    public int getDayOfYear() {
        return getMonth().firstDayOfYear(isLeapYear()) + day - 1;
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
     *  获取星期字段,这是一个枚举{@code DayOfWeek}
     * <p>
     * 此方法返回星期几的枚举{@link DayOfWeek}这避免了对{@code int}值的含义的混淆如果您需要访问原始的{@code int}值,那么枚举提供了{@链接DayOfWeek#getValue()int value}
     * 。
     * <p>
     *  其他信息可以从{@code DayOfWeek}获取,其中包括值的文本名称
     * 
     * 
     * @return the day-of-week, not null
     */
    public DayOfWeek getDayOfWeek() {
        int dow0 = (int)Math.floorMod(toEpochDay() + 3, 7);
        return DayOfWeek.of(dow0 + 1);
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
     *  这种方法适用于整个时间线的闰年的现行规则一般来说,一年是一个闰年,如果它可以除以四而没有余数,然而,年除以100,不是闰年,除了年可整除由400
     * <p>
     * 例如,1904年是一个闰年,可以被4整除1900年不是一个闰年,因为它可以被100整除,但2000年是一个闰年,因为它可以被400整除
     * <p>
     *  计算是推测的 - 将相同的规则应用到远远和远远过去这是历史上不准确的,但是对于ISO-8601标准是正确的
     * 
     * 
     * @return true if the year is leap, false otherwise
     */
    @Override // override for Javadoc and performance
    public boolean isLeapYear() {
        return IsoChronology.INSTANCE.isLeapYear(year);
    }

    /**
     * Returns the length of the month represented by this date.
     * <p>
     * This returns the length of the month in days.
     * For example, a date in January would return 31.
     *
     * <p>
     *  返回此日期表示的月份的长度
     * <p>
     *  这将返回月份的长度(以天为单位)例如,1月的日期将返回31
     * 
     * 
     * @return the length of the month in days
     */
    @Override
    public int lengthOfMonth() {
        switch (month) {
            case 2:
                return (isLeapYear() ? 29 : 28);
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    /**
     * Returns the length of the year represented by this date.
     * <p>
     * This returns the length of the year in days, either 365 or 366.
     *
     * <p>
     *  返回由此日期表示的年份的长度
     * <p>
     *  这将返回年的长度,以天为单位,365或366
     * 
     * 
     * @return 366 if the year is leap, 365 otherwise
     */
    @Override // override for Javadoc and performance
    public int lengthOfYear() {
        return (isLeapYear() ? 366 : 365);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns an adjusted copy of this date.
     * <p>
     * This returns a {@code LocalDate}, based on this one, with the date adjusted.
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
     *  result = localDate.with(JULY).with(lastDayOfMonth());
     * </pre>
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalAdjuster#adjustInto(Temporal)} method on the
     * specified adjuster passing {@code this} as the argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的调整副本
     * <p>
     * 这将返回一个{@code LocalDate},基于这个日期,日期已调整使用指定的adjuster策略对象进行调整读取调整器的文档以了解将要进行的调整
     * <p>
     *  一个简单的调整器可以简单地设置一个字段,例如年份字段更复杂的调整器可能将日期设置为月的最后一天在{@link TemporalAdjuster}中提供了一些常见调整选项,包括查找"月的最后一天"和"下
     * 周三"关键日期时间类也实现{@code TemporalAdjuster}接口,例如{@link Month}和{@link javatimeMonthDay MonthDay}调整器负责处理特殊情况,
     * 例如作为月和闰年的变化长度。
     * <p>
     * 例如,此代码返回7月最后一天的日期：
     * <pre>
     *  import static javatimeMonth *; import static javatimetemporalAdjusters *;
     * 
     *  result = localDatewith(JULY)with(lastDayOfMonth());
     * </pre>
     * <p>
     *  此方法的结果是通过调用指定的调整器传递{@code this}作为参数的{@link TemporalAdjuster#adjustInto(Temporal)}方法获得的
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param adjuster the adjuster to use, not null
     * @return a {@code LocalDate} based on {@code this} with the adjustment made, not null
     * @throws DateTimeException if the adjustment cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public LocalDate with(TemporalAdjuster adjuster) {
        // optimizations
        if (adjuster instanceof LocalDate) {
            return (LocalDate) adjuster;
        }
        return (LocalDate) adjuster.adjustInto(this);
    }

    /**
     * Returns a copy of this date with the specified field set to a new value.
     * <p>
     * This returns a {@code LocalDate}, based on this one, with the value
     * for the specified field changed.
     * This can be used to change any supported field, such as the year, month or day-of-month.
     * If it is not possible to set the value, because the field is not supported or for
     * some other reason, an exception is thrown.
     * <p>
     * In some cases, changing the specified field can cause the resulting date to become invalid,
     * such as changing the month from 31st January to February would make the day-of-month invalid.
     * In cases like this, the field is responsible for resolving the date. Typically it will choose
     * the previous valid date, which would be the last valid day of February in this example.
     * <p>
     * If the field is a {@link ChronoField} then the adjustment is implemented here.
     * The supported fields behave as follows:
     * <ul>
     * <li>{@code DAY_OF_WEEK} -
     *  Returns a {@code LocalDate} with the specified day-of-week.
     *  The date is adjusted up to 6 days forward or backward within the boundary
     *  of a Monday to Sunday week.
     * <li>{@code ALIGNED_DAY_OF_WEEK_IN_MONTH} -
     *  Returns a {@code LocalDate} with the specified aligned-day-of-week.
     *  The date is adjusted to the specified month-based aligned-day-of-week.
     *  Aligned weeks are counted such that the first week of a given month starts
     *  on the first day of that month.
     *  This may cause the date to be moved up to 6 days into the following month.
     * <li>{@code ALIGNED_DAY_OF_WEEK_IN_YEAR} -
     *  Returns a {@code LocalDate} with the specified aligned-day-of-week.
     *  The date is adjusted to the specified year-based aligned-day-of-week.
     *  Aligned weeks are counted such that the first week of a given year starts
     *  on the first day of that year.
     *  This may cause the date to be moved up to 6 days into the following year.
     * <li>{@code DAY_OF_MONTH} -
     *  Returns a {@code LocalDate} with the specified day-of-month.
     *  The month and year will be unchanged. If the day-of-month is invalid for the
     *  year and month, then a {@code DateTimeException} is thrown.
     * <li>{@code DAY_OF_YEAR} -
     *  Returns a {@code LocalDate} with the specified day-of-year.
     *  The year will be unchanged. If the day-of-year is invalid for the
     *  year, then a {@code DateTimeException} is thrown.
     * <li>{@code EPOCH_DAY} -
     *  Returns a {@code LocalDate} with the specified epoch-day.
     *  This completely replaces the date and is equivalent to {@link #ofEpochDay(long)}.
     * <li>{@code ALIGNED_WEEK_OF_MONTH} -
     *  Returns a {@code LocalDate} with the specified aligned-week-of-month.
     *  Aligned weeks are counted such that the first week of a given month starts
     *  on the first day of that month.
     *  This adjustment moves the date in whole week chunks to match the specified week.
     *  The result will have the same day-of-week as this date.
     *  This may cause the date to be moved into the following month.
     * <li>{@code ALIGNED_WEEK_OF_YEAR} -
     *  Returns a {@code LocalDate} with the specified aligned-week-of-year.
     *  Aligned weeks are counted such that the first week of a given year starts
     *  on the first day of that year.
     *  This adjustment moves the date in whole week chunks to match the specified week.
     *  The result will have the same day-of-week as this date.
     *  This may cause the date to be moved into the following year.
     * <li>{@code MONTH_OF_YEAR} -
     *  Returns a {@code LocalDate} with the specified month-of-year.
     *  The year will be unchanged. The day-of-month will also be unchanged,
     *  unless it would be invalid for the new month and year. In that case, the
     *  day-of-month is adjusted to the maximum valid value for the new month and year.
     * <li>{@code PROLEPTIC_MONTH} -
     *  Returns a {@code LocalDate} with the specified proleptic-month.
     *  The day-of-month will be unchanged, unless it would be invalid for the new month
     *  and year. In that case, the day-of-month is adjusted to the maximum valid value
     *  for the new month and year.
     * <li>{@code YEAR_OF_ERA} -
     *  Returns a {@code LocalDate} with the specified year-of-era.
     *  The era and month will be unchanged. The day-of-month will also be unchanged,
     *  unless it would be invalid for the new month and year. In that case, the
     *  day-of-month is adjusted to the maximum valid value for the new month and year.
     * <li>{@code YEAR} -
     *  Returns a {@code LocalDate} with the specified year.
     *  The month will be unchanged. The day-of-month will also be unchanged,
     *  unless it would be invalid for the new month and year. In that case, the
     *  day-of-month is adjusted to the maximum valid value for the new month and year.
     * <li>{@code ERA} -
     *  Returns a {@code LocalDate} with the specified era.
     *  The year-of-era and month will be unchanged. The day-of-month will also be unchanged,
     *  unless it would be invalid for the new month and year. In that case, the
     *  day-of-month is adjusted to the maximum valid value for the new month and year.
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
     *  返回此日期的副本,指定的字段设置为新值
     * <p>
     * 这将返回一个{@code LocalDate},基于这一个,指定字段的值更改这可以用于更改任何支持的字段,例如年,月或日子如果不可能设置值,因为该字段不受支持或由于某种其他原因,抛出异常
     * <p>
     *  在某些情况下,更改指定字段可能导致生成日期无效,例如将月份从1月31日更改为2月会使日期无效在这种情况下,字段负责解决日期通常它将选择上一个有效日期,这将是本示例中二月的最后一个有效日期
     * <p>
     * 如果字段是{@link ChronoField},则在此执行调整。支持的字段的行为如下：
     * <ul>
     * <li> {@ code DAY_OF_WEEK}  - 返回带有指定星期的{@code LocalDate}日期在星期一至星期日的边界内向前或向后调整6天<li> {@ code ALIGNED_DAY_OF_WEEK_IN_MONTH}
     *   - 返回具有指定的对齐日 - 星期的{@code LocalDate}日期被调整为指定的基于月份的对齐日 - 星期对齐的星期被计数,使得给定月份的第一周从该月的第一天开始这可能会导致该日期最多移动到
     * 下一个月的6天<li> {@ code ALIGNED_DAY_OF_WEEK_IN_YEAR}  - 返回指定的星期几的{@code LocalDate}将日期调整为指定的基于年份的对齐日期对齐的周数
     * 计数,使得给定年份的第一周从该年的第一天开始。
     * 这可能会导致该日期向下移动到6天。
     * <li> {@ code DAY_OF_MONTH}  - 返回{@代码LocalDate}与指定的日期不同月份和年份将保持不变如果日期对年份和月份无效,则会抛出{@code DateTimeException}
     *  <li> {@ code DAY_OF_YEAR} - 返回带有指定年份的{@code LocalDate}年份不会改变如果年份日期对年份无效,则会抛出{@code DateTimeException}
     *  <li> {@ code EPOCH_DAY }  - 返回一个带有指定的epoch-day的{@code LocalDate}这完全取代了日期,相当于{@link #ofEpochDay(long)}
     * <li> {@ code ALIGNED_WEEK_OF_MONTH}  - 返回具有指定排列周的月份的{@code LocalDate}计算排列的周数,以便给定月份的第一周从该月的第一天开始此调整将移
     * 动结果将与此日期具有相同的星期几这可能会将日期移动到下一个月<li> {@ code ALIGNED_WEEK_OF_YEAR}  - 返回{@代码LocalDate}与指定的aligned-week-
     * of-year对齐的周数进行计数,使得给定年份的第一周从该年的第一天开始。
     * 这可能会导致该日期向下移动到6天。
     * 此调整将整周块的日期移动到与指定周相匹配结果将与此日期具有相同的星期几这可能会导致将日期移动到下一年<li> {@ code MONTH_OF_YEAR}  - 返回带有指定年份的{@code LocalDate}
     * 年份将保持不变日期也将是除非对新的月份和年份无效,在这种情况下,将月份调整为新的月份和年份的最大有效值<li> {@ code PROLEPTIC_MONTH}  - 返回{@code LocalDate}
     * 与指定的搜索月份的日期将保持不变,除非对新的月份和年份无效。
     * 这可能会导致该日期向下移动到6天。
     * 在这种情况下,将月份调整为新的月份的最大有效值和年</li> {@ code YEAR_OF_ERA}  - 返回具有指定年份的{@code LocalDate}时代和月份将保持不变除非对新的月份和年份
     * 无效,否则日期也将保持不变。
     * 这可能会导致该日期向下移动到6天。
     * 在这种情况下,将月份调整为新的月份和年份的最大有效值<li> {@代码YEAR}  - 返回带有指定年份的{@code LocalDate}月份将保持不变除非月份和年份无效,否则日期也将保持不变。
     * 在这种情况下, - 月份调整为新的月份和年份的最大有效值<li> {@ code ERA}  - 返回指定时代的{@code LocalDate}年份和月份将保持不变天数 - 月份也将保持不变,除非对新
     * 的月份和年份无效。
     * 在这种情况下,将月份调整为新的月份和年份的最大有效值<li> {@代码YEAR}  - 返回带有指定年份的{@code LocalDate}月份将保持不变除非月份和年份无效,否则日期也将保持不变。
     * 在这种情况下,将月份调整为新的月份和年份的最大有效值。
     * </ul>
     * <p>
     * 在所有情况下,如果新值超出字段的有效值范围,那么将抛出{@code DateTimeException}
     * <p>
     *  所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}
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
     * @return a {@code LocalDate} based on {@code this} with the specified field set, not null
     * @throws DateTimeException if the field cannot be set
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public LocalDate with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            f.checkValidValue(newValue);
            switch (f) {
                case DAY_OF_WEEK: return plusDays(newValue - getDayOfWeek().getValue());
                case ALIGNED_DAY_OF_WEEK_IN_MONTH: return plusDays(newValue - getLong(ALIGNED_DAY_OF_WEEK_IN_MONTH));
                case ALIGNED_DAY_OF_WEEK_IN_YEAR: return plusDays(newValue - getLong(ALIGNED_DAY_OF_WEEK_IN_YEAR));
                case DAY_OF_MONTH: return withDayOfMonth((int) newValue);
                case DAY_OF_YEAR: return withDayOfYear((int) newValue);
                case EPOCH_DAY: return LocalDate.ofEpochDay(newValue);
                case ALIGNED_WEEK_OF_MONTH: return plusWeeks(newValue - getLong(ALIGNED_WEEK_OF_MONTH));
                case ALIGNED_WEEK_OF_YEAR: return plusWeeks(newValue - getLong(ALIGNED_WEEK_OF_YEAR));
                case MONTH_OF_YEAR: return withMonth((int) newValue);
                case PROLEPTIC_MONTH: return plusMonths(newValue - getProlepticMonth());
                case YEAR_OF_ERA: return withYear((int) (year >= 1 ? newValue : 1 - newValue));
                case YEAR: return withYear((int) newValue);
                case ERA: return (getLong(ERA) == newValue ? this : withYear(1 - year));
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.adjustInto(this, newValue);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date with the year altered.
     * If the day-of-month is invalid for the year, it will be changed to the last valid day of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期与已更改年份的副本如果日期对于年份无效,将更改为月份的最后有效日期
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param year  the year to set in the result, from MIN_YEAR to MAX_YEAR
     * @return a {@code LocalDate} based on this date with the requested year, not null
     * @throws DateTimeException if the year value is invalid
     */
    public LocalDate withYear(int year) {
        if (this.year == year) {
            return this;
        }
        YEAR.checkValidValue(year);
        return resolvePreviousValid(year, month, day);
    }

    /**
     * Returns a copy of this date with the month-of-year altered.
     * If the day-of-month is invalid for the year, it will be changed to the last valid day of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此日期的副本,并更改​​年份。如果年份的日期无效,将更改为月份的最后一个有效日期
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param month  the month-of-year to set in the result, from 1 (January) to 12 (December)
     * @return a {@code LocalDate} based on this date with the requested month, not null
     * @throws DateTimeException if the month-of-year value is invalid
     */
    public LocalDate withMonth(int month) {
        if (this.month == month) {
            return this;
        }
        MONTH_OF_YEAR.checkValidValue(month);
        return resolvePreviousValid(year, month, day);
    }

    /**
     * Returns a copy of this date with the day-of-month altered.
     * If the resulting date is invalid, an exception is thrown.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的副本,其中包含更改的日期。如果生成的日期无效,则抛出异常
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param dayOfMonth  the day-of-month to set in the result, from 1 to 28-31
     * @return a {@code LocalDate} based on this date with the requested day, not null
     * @throws DateTimeException if the day-of-month value is invalid,
     *  or if the day-of-month is invalid for the month-year
     */
    public LocalDate withDayOfMonth(int dayOfMonth) {
        if (this.day == dayOfMonth) {
            return this;
        }
        return of(year, month, dayOfMonth);
    }

    /**
     * Returns a copy of this date with the day-of-year altered.
     * If the resulting date is invalid, an exception is thrown.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的副本,其中包含年份已更改如果生成的日期无效,则抛出异常
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param dayOfYear  the day-of-year to set in the result, from 1 to 365-366
     * @return a {@code LocalDate} based on this date with the requested day, not null
     * @throws DateTimeException if the day-of-year value is invalid,
     *  or if the day-of-year is invalid for the year
     */
    public LocalDate withDayOfYear(int dayOfYear) {
        if (this.getDayOfYear() == dayOfYear) {
            return this;
        }
        return ofYearDay(year, dayOfYear);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date with the specified amount added.
     * <p>
     * This returns a {@code LocalDate}, based on this one, with the specified amount added.
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
     *  返回此日期的指定添加金额的副本
     * <p>
     * 这会返回一个{@code LocalDate},基于这个值,添加了指定的量。该值通常是{@link Period},但可以是实现{@link TemporalAmount}接口的任何其他类型
     * <p>
     *  计算通过调用{@link TemporalAmount#addTo(Temporal)}来委托给金额对象。
     * 金额实现可以以任何希望的方式实现添加,但它通常回调{@link #plus(long,TemporalUnit )}请参阅金额实施的文档,以确定是否可以成功添加。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param amountToAdd  the amount to add, not null
     * @return a {@code LocalDate} based on this date with the addition made, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public LocalDate plus(TemporalAmount amountToAdd) {
        if (amountToAdd instanceof Period) {
            Period periodToAdd = (Period) amountToAdd;
            return plusMonths(periodToAdd.toTotalMonths()).plusDays(periodToAdd.getDays());
        }
        Objects.requireNonNull(amountToAdd, "amountToAdd");
        return (LocalDate) amountToAdd.addTo(this);
    }

    /**
     * Returns a copy of this date with the specified amount added.
     * <p>
     * This returns a {@code LocalDate}, based on this one, with the amount
     * in terms of the unit added. If it is not possible to add the amount, because the
     * unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * In some cases, adding the amount can cause the resulting date to become invalid.
     * For example, adding one month to 31st January would result in 31st February.
     * In cases like this, the unit is responsible for resolving the date.
     * Typically it will choose the previous valid date, which would be the last valid
     * day of February in this example.
     * <p>
     * If the field is a {@link ChronoUnit} then the addition is implemented here.
     * The supported fields behave as follows:
     * <ul>
     * <li>{@code DAYS} -
     *  Returns a {@code LocalDate} with the specified number of days added.
     *  This is equivalent to {@link #plusDays(long)}.
     * <li>{@code WEEKS} -
     *  Returns a {@code LocalDate} with the specified number of weeks added.
     *  This is equivalent to {@link #plusWeeks(long)} and uses a 7 day week.
     * <li>{@code MONTHS} -
     *  Returns a {@code LocalDate} with the specified number of months added.
     *  This is equivalent to {@link #plusMonths(long)}.
     *  The day-of-month will be unchanged unless it would be invalid for the new
     *  month and year. In that case, the day-of-month is adjusted to the maximum
     *  valid value for the new month and year.
     * <li>{@code YEARS} -
     *  Returns a {@code LocalDate} with the specified number of years added.
     *  This is equivalent to {@link #plusYears(long)}.
     *  The day-of-month will be unchanged unless it would be invalid for the new
     *  month and year. In that case, the day-of-month is adjusted to the maximum
     *  valid value for the new month and year.
     * <li>{@code DECADES} -
     *  Returns a {@code LocalDate} with the specified number of decades added.
     *  This is equivalent to calling {@link #plusYears(long)} with the amount
     *  multiplied by 10.
     *  The day-of-month will be unchanged unless it would be invalid for the new
     *  month and year. In that case, the day-of-month is adjusted to the maximum
     *  valid value for the new month and year.
     * <li>{@code CENTURIES} -
     *  Returns a {@code LocalDate} with the specified number of centuries added.
     *  This is equivalent to calling {@link #plusYears(long)} with the amount
     *  multiplied by 100.
     *  The day-of-month will be unchanged unless it would be invalid for the new
     *  month and year. In that case, the day-of-month is adjusted to the maximum
     *  valid value for the new month and year.
     * <li>{@code MILLENNIA} -
     *  Returns a {@code LocalDate} with the specified number of millennia added.
     *  This is equivalent to calling {@link #plusYears(long)} with the amount
     *  multiplied by 1,000.
     *  The day-of-month will be unchanged unless it would be invalid for the new
     *  month and year. In that case, the day-of-month is adjusted to the maximum
     *  valid value for the new month and year.
     * <li>{@code ERAS} -
     *  Returns a {@code LocalDate} with the specified number of eras added.
     *  Only two eras are supported so the amount must be one, zero or minus one.
     *  If the amount is non-zero then the year is changed such that the year-of-era
     *  is unchanged.
     *  The day-of-month will be unchanged unless it would be invalid for the new
     *  month and year. In that case, the day-of-month is adjusted to the maximum
     *  valid value for the new month and year.
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
     *  返回此日期的指定添加金额的副本
     * <p>
     * 这将返回一个{@code LocalDate},根据这一个,与添加的单位的金额如果不可能添加金额,因为单位不被支持或由于一些其他原因,抛出异常
     * <p>
     *  在某些情况下,添加金额可能导致生成日期无效例如,将一个月添加到1月31日将导致2月31日在这种情况下,单位负责解决日期通常,它将选择上一个有效日期,这将是本示例中2月的最后一个有效日
     * <p>
     *  如果字段是{@link ChronoUnit},则在此处实现添加。支持的字段的行为如下：
     * <ul>
     * <li> {@ code DAYS}  - 返回添加了指定天数的{@code LocalDate}这等效于{@link #plusDays(long)} <li> {@ code WEEKS}  - 返
     * 回{@code LocalDate}添加指定的周数这等同于{@link #plusWeeks(long)}并使用7天的周<li> {@ code MONTHS}  - 返回指定月数的{@code LocalDate}
     * 添加这相当于{@link #plusMonths(long)}除非对新的月份和年份无效,否则日期将保持不变。
     * 在这种情况下,将月份调整为最大有效新的月份和年份的值<@> {@ code YEARS}  - 返回添加了指定年数的{@code LocalDate}这等价于{@link #plusYears(long)}
     * 除非对新的月份和年份无效,否则日期将保持不变。
     * 在这种情况下,将月份调整为新的月份和年份的最大有效值<li> {@ code DECADES }  - 返回一个带有指定增加的十进制数的{@code LocalDate}这相当于调用{@link #plusYears(long)}
     * 乘以10的值除非日期无效,否则日期将保持不变对于新的月份和年份在这种情况下,将月份调整为新的月份和年份的最大有效值<li> {@ code CENTURIES}  - 返回指定数量的{@code LocalDate}
     *  centuries added这相当于调用{@link #plusYears(long)},其数量乘以100除非对新的月份和年份无效,否则日期将保持不变。
     * 在这种情况下,将月份调整为新的月份和年份的最大有效值<li> {@ code MILLENNIA }  - 返回一个带有指定的千年数的{@code LocalDate}这相当于调用{@link #plusYears(long)}
     * 乘以1000除非日期无效,日期将保持不变对于新的月份和年份在这种情况下,将月份调整为新的月份和年份的最大有效值<li> {@ code ERAS}  - 返回指定数量的{@code LocalDate}
     *  eras added仅支持两个时间,所以数量必须为一,零或减一如果金额不为零,那么年份会更改,以使年份不变。
     * 除非对新的月份和年份无效,否则日期将保持不变。在这种情况下,日期 - 月被调整为新的月份和年份的最大有效值。
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
     * @return a {@code LocalDate} based on this date with the specified amount added, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public LocalDate plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            ChronoUnit f = (ChronoUnit) unit;
            switch (f) {
                case DAYS: return plusDays(amountToAdd);
                case WEEKS: return plusWeeks(amountToAdd);
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

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code LocalDate} with the specified period in years added.
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
     *  返回此{@code LocalDate}的副本,其中包含指定的周期(以增加的年数)
     * <p>
     *  此方法通过三个步骤将指定的金额添加到"年份"字段中：
     * <ol>
     *  <li>将输入年份添加到年份字段</li> <li>检查结果日期是否无效</li> <li>如有必要,将日期调整为最后一天。</li >
     * </ol>
     * <p>
     * 例如,2008-02-29(闰年)加一年将导致无效日期2009-02-29(标准年)而不是返回无效结果,该月的最后一个有效日,2009-02-28 ,被选择
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param yearsToAdd  the years to add, may be negative
     * @return a {@code LocalDate} based on this date with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public LocalDate plusYears(long yearsToAdd) {
        if (yearsToAdd == 0) {
            return this;
        }
        int newYear = YEAR.checkValidIntValue(year + yearsToAdd);  // safe overflow
        return resolvePreviousValid(newYear, month, day);
    }

    /**
     * Returns a copy of this {@code LocalDate} with the specified period in months added.
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
     *  返回此{@code LocalDate}的副本,其中包含指定的期间(以月为单位)
     * <p>
     *  此方法通过三个步骤将指定的金额添加到months字段：
     * <ol>
     *  <li>将输入月份添加到年份字段</li> <li>检查结果日期是否无效</li> <li>将日期调整为最后一个有效日,如果必要</li>
     * </ol>
     * <p>
     * 例如,2007-03-31加一个月将导致无效日期2007-04-31而不是返回无效结果,而是选择该月的最后一个有效日(2007-04-30)
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param monthsToAdd  the months to add, may be negative
     * @return a {@code LocalDate} based on this date with the months added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public LocalDate plusMonths(long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }
        long monthCount = year * 12L + (month - 1);
        long calcMonths = monthCount + monthsToAdd;  // safe overflow
        int newYear = YEAR.checkValidIntValue(Math.floorDiv(calcMonths, 12));
        int newMonth = (int)Math.floorMod(calcMonths, 12) + 1;
        return resolvePreviousValid(newYear, newMonth, day);
    }

    /**
     * Returns a copy of this {@code LocalDate} with the specified period in weeks added.
     * <p>
     * This method adds the specified amount in weeks to the days field incrementing
     * the month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2008-12-31 plus one week would result in 2009-01-07.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code LocalDate}的副本,其中包含指定的周期(以星期为单位)
     * <p>
     *  此方法将星期中的指定金额添加到天字段中,根据需要增加月和年字段,以确保结果保持有效结果仅在超过最大/最小年份时无效
     * <p>
     *  例如,2008-12-31加上一周将导致2009-01-07
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param weeksToAdd  the weeks to add, may be negative
     * @return a {@code LocalDate} based on this date with the weeks added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public LocalDate plusWeeks(long weeksToAdd) {
        return plusDays(Math.multiplyExact(weeksToAdd, 7));
    }

    /**
     * Returns a copy of this {@code LocalDate} with the specified number of days added.
     * <p>
     * This method adds the specified amount to the days field incrementing the
     * month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2008-12-31 plus one day would result in 2009-01-01.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回添加了指定天数的此{@code LocalDate}的副本
     * <p>
     * 此方法将指定的金额添加到"日期"字段中,根据需要增加月份和年份字段,以确保结果保持有效结果仅在超过最大/最小年份时无效
     * <p>
     *  例如,2008-12-31加一天将导致2009-01-01
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param daysToAdd  the days to add, may be negative
     * @return a {@code LocalDate} based on this date with the days added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public LocalDate plusDays(long daysToAdd) {
        if (daysToAdd == 0) {
            return this;
        }
        long mjDay = Math.addExact(toEpochDay(), daysToAdd);
        return LocalDate.ofEpochDay(mjDay);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date with the specified amount subtracted.
     * <p>
     * This returns a {@code LocalDate}, based on this one, with the specified amount subtracted.
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
     *  返回此日期的指定减去的金额的副本
     * <p>
     *  这会返回一个{@code LocalDate},基于这个值,减去指定的量。该值通常是{@link Period},但可以是实现{@link TemporalAmount}接口的任何其他类型
     * <p>
     * 通过调用{@link TemporalAmount#subtractFrom(Temporal)}来将计算委托给金额对象。
     * 金额实现可以以任何希望的方式实现减法,但是它通常回调{@link #minus(long,TemporalUnit )}请参阅金额实施的文档,以确定是否可以成功扣除。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param amountToSubtract  the amount to subtract, not null
     * @return a {@code LocalDate} based on this date with the subtraction made, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public LocalDate minus(TemporalAmount amountToSubtract) {
        if (amountToSubtract instanceof Period) {
            Period periodToSubtract = (Period) amountToSubtract;
            return minusMonths(periodToSubtract.toTotalMonths()).minusDays(periodToSubtract.getDays());
        }
        Objects.requireNonNull(amountToSubtract, "amountToSubtract");
        return (LocalDate) amountToSubtract.subtractFrom(this);
    }

    /**
     * Returns a copy of this date with the specified amount subtracted.
     * <p>
     * This returns a {@code LocalDate}, based on this one, with the amount
     * in terms of the unit subtracted. If it is not possible to subtract the amount,
     * because the unit is not supported or for some other reason, an exception is thrown.
     * <p>
     * This method is equivalent to {@link #plus(long, TemporalUnit)} with the amount negated.
     * See that method for a full description of how addition, and thus subtraction, works.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的指定减去的金额的副本
     * <p>
     *  这将返回一个{@code LocalDate},根据这一个,与减去单位的金额如果不可能减去金额,因为单位不被支持或由于其他原因,抛出异常
     * <p>
     * 此方法等效于{@link #plus(long,TemporalUnit)},其值为negated请参阅该方法,了解有关如何加法和减法的完整描述
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param amountToSubtract  the amount of the unit to subtract from the result, may be negative
     * @param unit  the unit of the amount to subtract, not null
     * @return a {@code LocalDate} based on this date with the specified amount subtracted, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public LocalDate minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this {@code LocalDate} with the specified period in years subtracted.
     * <p>
     * This method subtracts the specified amount from the years field in three steps:
     * <ol>
     * <li>Subtract the input years to the year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2008-02-29 (leap year) minus one year would result in the
     * invalid date 2007-02-29 (standard year). Instead of returning an invalid
     * result, the last valid day of the month, 2007-02-28, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code LocalDate}的副本,其中指定的时间段减去年
     * <p>
     *  此方法通过三个步骤从年份字段中减去指定的金额：
     * <ol>
     *  <li>将输入的年份减去年份字段</li> <li>检查结果日期是否无效</li> <li>如有必要,将日期调整为最后一天。</li >
     * </ol>
     * <p>
     * 例如,2008-02-29(闰年)减去一年将导致无效日期2007-02-29(标准年)而不是返回无效结果,该月的最后一个有效日,2007-02-28 ,被选择
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param yearsToSubtract  the years to subtract, may be negative
     * @return a {@code LocalDate} based on this date with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public LocalDate minusYears(long yearsToSubtract) {
        return (yearsToSubtract == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
    }

    /**
     * Returns a copy of this {@code LocalDate} with the specified period in months subtracted.
     * <p>
     * This method subtracts the specified amount from the months field in three steps:
     * <ol>
     * <li>Subtract the input months to the month-of-year field</li>
     * <li>Check if the resulting date would be invalid</li>
     * <li>Adjust the day-of-month to the last valid day if necessary</li>
     * </ol>
     * <p>
     * For example, 2007-03-31 minus one month would result in the invalid date
     * 2007-02-31. Instead of returning an invalid result, the last valid day
     * of the month, 2007-02-28, is selected instead.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code LocalDate}的副本,其中指定的周期减去月
     * <p>
     *  此方法从三个步骤中的月字段中减去指定的金额：
     * <ol>
     *  <li>将输入月份减去年月日字段</li> <li>检查结果日期是否无效</li> <li>将月份调整为最后一个有效日,如果必要</li>
     * </ol>
     * <p>
     * 例如,2007-03-31减去一个月将导致无效日期2007-02-31而不是返回无效结果,而是选择该月的最后一个有效日(2007-02-28)
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param monthsToSubtract  the months to subtract, may be negative
     * @return a {@code LocalDate} based on this date with the months subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public LocalDate minusMonths(long monthsToSubtract) {
        return (monthsToSubtract == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-monthsToSubtract));
    }

    /**
     * Returns a copy of this {@code LocalDate} with the specified period in weeks subtracted.
     * <p>
     * This method subtracts the specified amount in weeks from the days field decrementing
     * the month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2009-01-07 minus one week would result in 2008-12-31.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code LocalDate}的副本,其中指定的周期减去后的周数
     * <p>
     *  此方法从星期减去指定的金额,从字段减少月份和年份字段,以确保结果保持有效结果仅在超过最大/最小年份时无效
     * <p>
     *  例如,2009-01-07减一周将导致2008-12-31
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param weeksToSubtract  the weeks to subtract, may be negative
     * @return a {@code LocalDate} based on this date with the weeks subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public LocalDate minusWeeks(long weeksToSubtract) {
        return (weeksToSubtract == Long.MIN_VALUE ? plusWeeks(Long.MAX_VALUE).plusWeeks(1) : plusWeeks(-weeksToSubtract));
    }

    /**
     * Returns a copy of this {@code LocalDate} with the specified number of days subtracted.
     * <p>
     * This method subtracts the specified amount from the days field decrementing the
     * month and year fields as necessary to ensure the result remains valid.
     * The result is only invalid if the maximum/minimum year is exceeded.
     * <p>
     * For example, 2009-01-01 minus one day would result in 2008-12-31.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此{@code LocalDate}的指定减去天数的副本
     * <p>
     * 此方法从必要的天字段减少月和年字段中减去指定的金额,以确保结果保持有效结果仅在超过最大/最小年份时才无效
     * <p>
     *  例如,2009-01-01减去一天将导致2008-12-31
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param daysToSubtract  the days to subtract, may be negative
     * @return a {@code LocalDate} based on this date with the days subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    public LocalDate minusDays(long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-daysToSubtract));
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this date using the specified query.
     * <p>
     * This queries this date using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此日期
     * <p>
     *  这使用指定的查询策略对象查询此日期{@code TemporalQuery}对象定义用于获取结果的逻辑读取查询的文档以了解此方法的结果将是什么
     * <p>
     * 此方法的结果是通过在指定的查询上调用{@link TemporalQuery#queryFrom(TemporalAccessor)}方法{@code this}作为参数
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
        if (query == TemporalQueries.localDate()) {
            return (R) this;
        }
        return ChronoLocalDate.super.query(query);
    }

    /**
     * Adjusts the specified temporal object to have the same date as this object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the date changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * passing {@link ChronoField#EPOCH_DAY} as the field.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisLocalDate.adjustInto(temporal);
     *   temporal = temporal.with(thisLocalDate);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为与此对象具有相同的日期
     * <p>
     *  这返回一个与输入相同的observable类型的时间对象,日期更改为与此相同
     * <p>
     *  该调整等同于使用{@link Temporal#with(TemporalField,long)}传递{@link ChronoField#EPOCH_DAY}作为字段
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐temporal = thisLocalDateadjustInto(temporal); temporal = temporalwith(thisLocalDa
     * te);。
     * </pre>
     * <p>
     * 此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param temporal  the target object to be adjusted, not null
     * @return the adjusted object, not null
     * @throws DateTimeException if unable to make the adjustment
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override  // override for Javadoc
    public Temporal adjustInto(Temporal temporal) {
        return ChronoLocalDate.super.adjustInto(temporal);
    }

    /**
     * Calculates the amount of time until another date in terms of the specified unit.
     * <p>
     * This calculates the amount of time between two {@code LocalDate}
     * objects in terms of a single {@code TemporalUnit}.
     * The start and end points are {@code this} and the specified date.
     * The result will be negative if the end is before the start.
     * The {@code Temporal} passed to this method is converted to a
     * {@code LocalDate} using {@link #from(TemporalAccessor)}.
     * For example, the amount in days between two dates can be calculated
     * using {@code startDate.until(endDate, DAYS)}.
     * <p>
     * The calculation returns a whole number, representing the number of
     * complete units between the two dates.
     * For example, the amount in months between 2012-06-15 and 2012-08-14
     * will only be one month as it is one day short of two months.
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
     * The units {@code DAYS}, {@code WEEKS}, {@code MONTHS}, {@code YEARS},
     * {@code DECADES}, {@code CENTURIES}, {@code MILLENNIA} and {@code ERAS}
     * are supported. Other {@code ChronoUnit} values will throw an exception.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.between(Temporal, Temporal)}
     * passing {@code this} as the first argument and the converted input temporal
     * as the second argument.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  根据指定的单位计算直到另一个日期的时间量
     * <p>
     *  这将计算两个{@code LocalDate}对象之间在单个{@code TemporalUnit}方面的时间量开始和结束点是{@code this}和指定的日期如果结束在结束之前start使用{@link #from(TemporalAccessor)}
     * 将传递给此方法的{@code Temporal}转换为{@code LocalDate}。
     * 例如,两个日期之间的天数可以使用{@code startDateuntil(endDate , 天)}。
     * <p>
     * 计算返回一个整数,表示两个日期之间的完整单位数例如,2012-06-15和2012-08-14之间的月份数将只有一个月,因为它是两个月的一天
     * <p>
     *  有两种使用这种方法的等效方法第一种是调用这种方法第二种是使用{@link TemporalUnit#between(Temporal,Temporal)}：
     * <pre>
     *  //这两行是等价的amount = startuntil(end,MONTHS); amount = MONTHS between(start,end);
     * </pre>
     *  应该基于哪个使得代码更可读的选择
     * <p>
     * {@code ChronoUnit}单位{@code DAYS},{@code WEEKS},{@code MONTHS},{@code YEARS},{@code DECADES},{@code CENTURIES}
     *  ,{@code MILLENNIA}和{@code ERAS}其他{@code ChronoUnit}值会抛出异常。
     * <p>
     *  如果单元不是{@code ChronoUnit},则该方法的结果通过调用{@code TemporalUnit between(Temporal,Temporal)}传递{@code this}作为第
     * 一个参数和转换的输入时间作为第二个参数论据。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响
     * 
     * 
     * @param endExclusive  the end date, exclusive, which is converted to a {@code LocalDate}, not null
     * @param unit  the unit to measure the amount in, not null
     * @return the amount of time between this date and the end date
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to a {@code LocalDate}
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        LocalDate end = LocalDate.from(endExclusive);
        if (unit instanceof ChronoUnit) {
            switch ((ChronoUnit) unit) {
                case DAYS: return daysUntil(end);
                case WEEKS: return daysUntil(end) / 7;
                case MONTHS: return monthsUntil(end);
                case YEARS: return monthsUntil(end) / 12;
                case DECADES: return monthsUntil(end) / 120;
                case CENTURIES: return monthsUntil(end) / 1200;
                case MILLENNIA: return monthsUntil(end) / 12000;
                case ERAS: return end.getLong(ERA) - getLong(ERA);
            }
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return unit.between(this, end);
    }

    long daysUntil(LocalDate end) {
        return end.toEpochDay() - toEpochDay();  // no overflow
    }

    private long monthsUntil(LocalDate end) {
        long packed1 = getProlepticMonth() * 32L + getDayOfMonth();  // no overflow
        long packed2 = end.getProlepticMonth() * 32L + end.getDayOfMonth();  // no overflow
        return (packed2 - packed1) / 32;
    }

    /**
     * Calculates the period between this date and another date as a {@code Period}.
     * <p>
     * This calculates the period between two dates in terms of years, months and days.
     * The start and end points are {@code this} and the specified date.
     * The result will be negative if the end is before the start.
     * The negative sign will be the same in each of year, month and day.
     * <p>
     * The calculation is performed using the ISO calendar system.
     * If necessary, the input date will be converted to ISO.
     * <p>
     * The start date is included, but the end date is not.
     * The period is calculated by removing complete months, then calculating
     * the remaining number of days, adjusting to ensure that both have the same sign.
     * The number of months is then normalized into years and months based on a 12 month year.
     * A month is considered to be complete if the end day-of-month is greater
     * than or equal to the start day-of-month.
     * For example, from {@code 2010-01-15} to {@code 2011-03-18} is "1 year, 2 months and 3 days".
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method.
     * The second is to use {@link Period#between(LocalDate, LocalDate)}:
     * <pre>
     *   // these two lines are equivalent
     *   period = start.until(end);
     *   period = Period.between(start, end);
     * </pre>
     * The choice should be made based on which makes the code more readable.
     *
     * <p>
     *  计算此日期与另一个日期之间的期间为{@code Period}
     * <p>
     * 这将以年,月和日计算两个日期之间的周期开始和结束点是{@code this}和指定的日期如果结束在开始之前,结果将为负值负号将是相同的每年,每月和每天
     * <p>
     *  使用ISO日历系统执行计算如果需要,输入日期将转换为ISO
     * <p>
     * 包括开始日期,但不包括结束日期通过删除完整月份,然后计算剩余天数计算期间,调整以确保两者具有相同的符号。然后将月数标准化为年和月在12个月年份如果结束日期大于或等于开始日期的月份,则认为一个月份完成。
     * 例如,从{@code 2010-01-15}到{@code 2011-03-18}是"1年,2个月和3天"。
     * <p>
     *  有两种等效的方法使用这种方法第一种是调用这种方法第二种是使用{@link Period#between(LocalDate,LocalDate)}：
     * <pre>
     *  //这两行是等价的period = startuntil(end);周期=(开始,结束)之间的周期;
     * </pre>
     * 应该基于哪个使得代码更可读的选择
     * 
     * 
     * @param endDateExclusive  the end date, exclusive, which may be in any chronology, not null
     * @return the period between this date and the end date, not null
     */
    @Override
    public Period until(ChronoLocalDate endDateExclusive) {
        LocalDate end = LocalDate.from(endDateExclusive);
        long totalMonths = end.getProlepticMonth() - this.getProlepticMonth();  // safe
        int days = end.day - this.day;
        if (totalMonths > 0 && days < 0) {
            totalMonths--;
            LocalDate calcDate = this.plusMonths(totalMonths);
            days = (int) (end.toEpochDay() - calcDate.toEpochDay());  // safe
        } else if (totalMonths < 0 && days > 0) {
            totalMonths++;
            days -= end.lengthOfMonth();
        }
        long years = totalMonths / 12;  // safe
        int months = (int) (totalMonths % 12);  // safe
        return Period.of(Math.toIntExact(years), months, days);
    }

    /**
     * Formats this date using the specified formatter.
     * <p>
     * This date will be passed to the formatter to produce a string.
     *
     * <p>
     *  使用指定的格式化程序格式化此日期
     * <p>
     *  此日期将传递到格式化程序以生成字符串
     * 
     * 
     * @param formatter  the formatter to use, not null
     * @return the formatted date string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    @Override  // override for Javadoc and performance
    public String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Combines this date with a time to create a {@code LocalDateTime}.
     * <p>
     * This returns a {@code LocalDateTime} formed from this date at the specified time.
     * All possible combinations of date and time are valid.
     *
     * <p>
     *  将此日期与时间组合以创建{@code LocalDateTime}
     * <p>
     *  这将返回在指定时间从此日期形成的{@code LocalDateTime}所有可能的日期和时间组合都有效
     * 
     * 
     * @param time  the time to combine with, not null
     * @return the local date-time formed from this date and the specified time, not null
     */
    @Override
    public LocalDateTime atTime(LocalTime time) {
        return LocalDateTime.of(this, time);
    }

    /**
     * Combines this date with a time to create a {@code LocalDateTime}.
     * <p>
     * This returns a {@code LocalDateTime} formed from this date at the
     * specified hour and minute.
     * The seconds and nanosecond fields will be set to zero.
     * The individual time fields must be within their valid range.
     * All possible combinations of date and time are valid.
     *
     * <p>
     *  将此日期与时间组合以创建{@code LocalDateTime}
     * <p>
     *  这将返回在指定的小时和分钟从此日期形成的{@code LocalDateTime}秒和纳秒字段将设置为零个别时间字段必须在其有效范围内所有可能的日期和时间组合都有效
     * 
     * 
     * @param hour  the hour-of-day to use, from 0 to 23
     * @param minute  the minute-of-hour to use, from 0 to 59
     * @return the local date-time formed from this date and the specified time, not null
     * @throws DateTimeException if the value of any field is out of range
     */
    public LocalDateTime atTime(int hour, int minute) {
        return atTime(LocalTime.of(hour, minute));
    }

    /**
     * Combines this date with a time to create a {@code LocalDateTime}.
     * <p>
     * This returns a {@code LocalDateTime} formed from this date at the
     * specified hour, minute and second.
     * The nanosecond field will be set to zero.
     * The individual time fields must be within their valid range.
     * All possible combinations of date and time are valid.
     *
     * <p>
     *  将此日期与时间组合以创建{@code LocalDateTime}
     * <p>
     * 这将返回从此日期以指定的小时,分​​钟和秒形成的{@code LocalDateTime}纳秒字段将设置为零个别时间字段必须在其有效范围内所有可能的日期和时间组合都有效
     * 
     * 
     * @param hour  the hour-of-day to use, from 0 to 23
     * @param minute  the minute-of-hour to use, from 0 to 59
     * @param second  the second-of-minute to represent, from 0 to 59
     * @return the local date-time formed from this date and the specified time, not null
     * @throws DateTimeException if the value of any field is out of range
     */
    public LocalDateTime atTime(int hour, int minute, int second) {
        return atTime(LocalTime.of(hour, minute, second));
    }

    /**
     * Combines this date with a time to create a {@code LocalDateTime}.
     * <p>
     * This returns a {@code LocalDateTime} formed from this date at the
     * specified hour, minute, second and nanosecond.
     * The individual time fields must be within their valid range.
     * All possible combinations of date and time are valid.
     *
     * <p>
     *  将此日期与时间组合以创建{@code LocalDateTime}
     * <p>
     *  这将返回从此日期以指定的小时,分​​钟,秒和纳秒形成的{@code LocalDateTime}各个时间字段必须在其有效范围内所有可能的日期和时间组合都有效
     * 
     * 
     * @param hour  the hour-of-day to use, from 0 to 23
     * @param minute  the minute-of-hour to use, from 0 to 59
     * @param second  the second-of-minute to represent, from 0 to 59
     * @param nanoOfSecond  the nano-of-second to represent, from 0 to 999,999,999
     * @return the local date-time formed from this date and the specified time, not null
     * @throws DateTimeException if the value of any field is out of range
     */
    public LocalDateTime atTime(int hour, int minute, int second, int nanoOfSecond) {
        return atTime(LocalTime.of(hour, minute, second, nanoOfSecond));
    }

    /**
     * Combines this date with an offset time to create an {@code OffsetDateTime}.
     * <p>
     * This returns an {@code OffsetDateTime} formed from this date at the specified time.
     * All possible combinations of date and time are valid.
     *
     * <p>
     *  将此日期与偏移时间组合,以创建{@code OffsetDateTime}
     * <p>
     *  这将返回在指定时间从此日期形成的{@code OffsetDateTime}所有可能的日期和时间组合都有效
     * 
     * 
     * @param time  the time to combine with, not null
     * @return the offset date-time formed from this date and the specified time, not null
     */
    public OffsetDateTime atTime(OffsetTime time) {
        return OffsetDateTime.of(LocalDateTime.of(this, time.toLocalTime()), time.getOffset());
    }

    /**
     * Combines this date with the time of midnight to create a {@code LocalDateTime}
     * at the start of this date.
     * <p>
     * This returns a {@code LocalDateTime} formed from this date at the time of
     * midnight, 00:00, at the start of this date.
     *
     * <p>
     * 将此日期与午夜时间相结合,以便在此日期的开始创建{@code LocalDateTime}
     * <p>
     *  这会返回从此日期开始在午夜00:00开始生成的{@code LocalDateTime}
     * 
     * 
     * @return the local date-time of midnight at the start of this date, not null
     */
    public LocalDateTime atStartOfDay() {
        return LocalDateTime.of(this, LocalTime.MIDNIGHT);
    }

    /**
     * Returns a zoned date-time from this date at the earliest valid time according
     * to the rules in the time-zone.
     * <p>
     * Time-zone rules, such as daylight savings, mean that not every local date-time
     * is valid for the specified zone, thus the local date-time may not be midnight.
     * <p>
     * In most cases, there is only one valid offset for a local date-time.
     * In the case of an overlap, there are two valid offsets, and the earlier one is used,
     * corresponding to the first occurrence of midnight on the date.
     * In the case of a gap, the zoned date-time will represent the instant just after the gap.
     * <p>
     * If the zone ID is a {@link ZoneOffset}, then the result always has a time of midnight.
     * <p>
     * To convert to a specific time in a given time-zone call {@link #atTime(LocalTime)}
     * followed by {@link LocalDateTime#atZone(ZoneId)}.
     *
     * <p>
     *  根据时区中的规则,从最早有效时间的此日期返回分区日期时间
     * <p>
     *  时区规则(例如夏令时)意味着并非每个本地日期时间对于指定区域有效,因此本地日期时间可能不是午夜
     * <p>
     * 在大多数情况下,对于本地日期时间只有一个有效偏移量。在重叠的情况下,存在两个有效偏移量,并且使用较早的偏移量,对应于日期上午夜的第一次出现。间隔,分区日期时间将表示紧接间隙之后的时刻
     * <p>
     *  如果区域ID是{@link ZoneOffset},那么结果总是有一个午夜时间
     * <p>
     *  要转换到给定时区调用{@link #atTime(LocalTime)}中的特定时间,然后转换为{@link LocalDateTime#atZone(ZoneId)}
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the zoned date-time formed from this date and the earliest valid time for the zone, not null
     */
    public ZonedDateTime atStartOfDay(ZoneId zone) {
        Objects.requireNonNull(zone, "zone");
        // need to handle case where there is a gap from 11:30 to 00:30
        // standard ZDT factory would result in 01:00 rather than 00:30
        LocalDateTime ldt = atTime(LocalTime.MIDNIGHT);
        if (zone instanceof ZoneOffset == false) {
            ZoneRules rules = zone.getRules();
            ZoneOffsetTransition trans = rules.getTransition(ldt);
            if (trans != null && trans.isGap()) {
                ldt = trans.getDateTimeAfter();
            }
        }
        return ZonedDateTime.of(ldt, zone);
    }

    //-----------------------------------------------------------------------
    @Override
    public long toEpochDay() {
        long y = year;
        long m = month;
        long total = 0;
        total += 365 * y;
        if (y >= 0) {
            total += (y + 3) / 4 - (y + 99) / 100 + (y + 399) / 400;
        } else {
            total -= y / -4 - y / -100 + y / -400;
        }
        total += ((367 * m - 362) / 12);
        total += day - 1;
        if (m > 2) {
            total--;
            if (isLeapYear() == false) {
                total--;
            }
        }
        return total - DAYS_0000_TO_1970;
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this date to another date.
     * <p>
     * The comparison is primarily based on the date, from earliest to latest.
     * It is "consistent with equals", as defined by {@link Comparable}.
     * <p>
     * If all the dates being compared are instances of {@code LocalDate},
     * then the comparison will be entirely based on the date.
     * If some dates being compared are in different chronologies, then the
     * chronology is also considered, see {@link java.time.chrono.ChronoLocalDate#compareTo}.
     *
     * <p>
     *  将此日期与另一个日期进行比较
     * <p>
     *  比较主要基于日期,从最早到最晚它是"与等号一致",由{@link Comparable}定义,
     * <p>
     * 如果所有被比较的日期都是{@code LocalDate}的实例,那么比较将完全基于日期。
     * 如果一些被比较的日期是不同的时间表,那么也考虑年表,参见{@link javatimechronoChronoLocalDate#compareTo}。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override  // override for Javadoc and performance
    public int compareTo(ChronoLocalDate other) {
        if (other instanceof LocalDate) {
            return compareTo0((LocalDate) other);
        }
        return ChronoLocalDate.super.compareTo(other);
    }

    int compareTo0(LocalDate otherDate) {
        int cmp = (year - otherDate.year);
        if (cmp == 0) {
            cmp = (month - otherDate.month);
            if (cmp == 0) {
                cmp = (day - otherDate.day);
            }
        }
        return cmp;
    }

    /**
     * Checks if this date is after the specified date.
     * <p>
     * This checks to see if this date represents a point on the
     * local time-line after the other date.
     * <pre>
     *   LocalDate a = LocalDate.of(2012, 6, 30);
     *   LocalDate b = LocalDate.of(2012, 7, 1);
     *   a.isAfter(b) == false
     *   a.isAfter(a) == false
     *   b.isAfter(a) == true
     * </pre>
     * <p>
     * This method only considers the position of the two dates on the local time-line.
     * It does not take into account the chronology, or calendar system.
     * This is different from the comparison in {@link #compareTo(ChronoLocalDate)},
     * but is the same approach as {@link ChronoLocalDate#timeLineOrder()}.
     *
     * <p>
     *  检查此日期是否晚于指定日期
     * <p>
     *  这将检查此日期是否表示本地时间线上另一个日期之后的点
     * <pre>
     *  LocalDate a = LocalDateof(2012,6,30); LocalDate b = LocalDateof(2012,7,1); aisAfter(b)== false aisAf
     * ter(a)== false bisAfter(a)== true。
     * </pre>
     * <p>
     * 此方法仅考虑两个日期在本地时间轴上的位置。
     * 它不考虑年表或日历系统这与{@link #compareTo(ChronoLocalDate)}中的比较不同,但是是相同的方法作为{@link ChronoLocalDate#timeLineOrder()}
     * 。
     * 此方法仅考虑两个日期在本地时间轴上的位置。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return true if this date is after the specified date
     */
    @Override  // override for Javadoc and performance
    public boolean isAfter(ChronoLocalDate other) {
        if (other instanceof LocalDate) {
            return compareTo0((LocalDate) other) > 0;
        }
        return ChronoLocalDate.super.isAfter(other);
    }

    /**
     * Checks if this date is before the specified date.
     * <p>
     * This checks to see if this date represents a point on the
     * local time-line before the other date.
     * <pre>
     *   LocalDate a = LocalDate.of(2012, 6, 30);
     *   LocalDate b = LocalDate.of(2012, 7, 1);
     *   a.isBefore(b) == true
     *   a.isBefore(a) == false
     *   b.isBefore(a) == false
     * </pre>
     * <p>
     * This method only considers the position of the two dates on the local time-line.
     * It does not take into account the chronology, or calendar system.
     * This is different from the comparison in {@link #compareTo(ChronoLocalDate)},
     * but is the same approach as {@link ChronoLocalDate#timeLineOrder()}.
     *
     * <p>
     *  检查此日期是否早于指定日期
     * <p>
     *  这将检查此日期是否表示本地时间线上另一个日期之前的点
     * <pre>
     *  LocalDate a = LocalDateof(2012,6,30); LocalDate b = LocalDateof(2012,7,1); aisBefore(b)== true aisBe
     * fore(a)== false bisBefore(a)== false。
     * </pre>
     * <p>
     * 此方法仅考虑两个日期在本地时间轴上的位置。
     * 它不考虑年表或日历系统这与{@link #compareTo(ChronoLocalDate)}中的比较不同,但是是相同的方法作为{@link ChronoLocalDate#timeLineOrder()}
     * 。
     * 此方法仅考虑两个日期在本地时间轴上的位置。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return true if this date is before the specified date
     */
    @Override  // override for Javadoc and performance
    public boolean isBefore(ChronoLocalDate other) {
        if (other instanceof LocalDate) {
            return compareTo0((LocalDate) other) < 0;
        }
        return ChronoLocalDate.super.isBefore(other);
    }

    /**
     * Checks if this date is equal to the specified date.
     * <p>
     * This checks to see if this date represents the same point on the
     * local time-line as the other date.
     * <pre>
     *   LocalDate a = LocalDate.of(2012, 6, 30);
     *   LocalDate b = LocalDate.of(2012, 7, 1);
     *   a.isEqual(b) == false
     *   a.isEqual(a) == true
     *   b.isEqual(a) == false
     * </pre>
     * <p>
     * This method only considers the position of the two dates on the local time-line.
     * It does not take into account the chronology, or calendar system.
     * This is different from the comparison in {@link #compareTo(ChronoLocalDate)}
     * but is the same approach as {@link ChronoLocalDate#timeLineOrder()}.
     *
     * <p>
     *  检查此日期是否等于指定的日期
     * <p>
     *  这将检查此日期是否表示本地时间线上与其他日期相同的点
     * <pre>
     *  LocalDate a = LocalDateof(2012,6,30); LocalDate b = LocalDateof(2012,7,1); aisEqual(b)== false aisEq
     * ual(a)== true bisEqual(a)== false。
     * </pre>
     * <p>
     * 此方法仅考虑两个日期在本地时间轴上的位置。
     * 它不考虑年表或日历系统这与{@link #compareTo(ChronoLocalDate)}中的比较不同,但是是相同的方法, {@link ChronoLocalDate#timeLineOrder()}
     * 。
     * 此方法仅考虑两个日期在本地时间轴上的位置。
     * 
     * 
     * @param other  the other date to compare to, not null
     * @return true if this date is equal to the specified date
     */
    @Override  // override for Javadoc and performance
    public boolean isEqual(ChronoLocalDate other) {
        if (other instanceof LocalDate) {
            return compareTo0((LocalDate) other) == 0;
        }
        return ChronoLocalDate.super.isEqual(other);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this date is equal to another date.
     * <p>
     * Compares this {@code LocalDate} with another ensuring that the date is the same.
     * <p>
     * Only objects of type {@code LocalDate} are compared, other types return false.
     * To compare the dates of two {@code TemporalAccessor} instances, including dates
     * in two different chronologies, use {@link ChronoField#EPOCH_DAY} as a comparator.
     *
     * <p>
     *  检查此日期是否等于另一个日期
     * <p>
     *  将此{@code LocalDate}与另一个比较,确保日期是相同的
     * <p>
     *  仅比较{@code LocalDate}类型的对象,其他类型返回false要比较两个{@code TemporalAccessor}实例的日期,包括两个不同年代的日期,请使用{@link ChronoField#EPOCH_DAY}
     * 作为比较。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other date
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocalDate) {
            return compareTo0((LocalDate) obj) == 0;
        }
        return false;
    }

    /**
     * A hash code for this date.
     *
     * <p>
     *  此日期的哈希码
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        int yearValue = year;
        int monthValue = month;
        int dayValue = day;
        return (yearValue & 0xFFFFF800) ^ ((yearValue << 11) + (monthValue << 6) + (dayValue));
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this date as a {@code String}, such as {@code 2007-12-03}.
     * <p>
     * The output will be in the ISO-8601 format {@code uuuu-MM-dd}.
     *
     * <p>
     *  将此日期作为{@code String}输出,例如{@code 2007-12-03}
     * <p>
     * 输出将采用ISO-8601格式{@code uuuu-MM-dd}
     * 
     * 
     * @return a string representation of this date, not null
     */
    @Override
    public String toString() {
        int yearValue = year;
        int monthValue = month;
        int dayValue = day;
        int absYear = Math.abs(yearValue);
        StringBuilder buf = new StringBuilder(10);
        if (absYear < 1000) {
            if (yearValue < 0) {
                buf.append(yearValue - 10000).deleteCharAt(1);
            } else {
                buf.append(yearValue + 10000).deleteCharAt(0);
            }
        } else {
            if (yearValue > 9999) {
                buf.append('+');
            }
            buf.append(yearValue);
        }
        return buf.append(monthValue < 10 ? "-0" : "-")
            .append(monthValue)
            .append(dayValue < 10 ? "-0" : "-")
            .append(dayValue)
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
     *  out.writeByte(3);  // identifies a LocalDate
     *  out.writeInt(year);
     *  out.writeByte(month);
     *  out.writeByte(day);
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.LOCAL_DATE_TYPE, this);
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
        out.writeByte(day);
    }

    static LocalDate readExternal(DataInput in) throws IOException {
        int year = in.readInt();
        int month = in.readByte();
        int dayOfMonth = in.readByte();
        return LocalDate.of(year, month, dayOfMonth);
    }

}
