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
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time.chrono;

import java.io.InvalidObjectException;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * The ISO calendar system.
 * <p>
 * This chronology defines the rules of the ISO calendar system.
 * This calendar system is based on the ISO-8601 standard, which is the
 * <i>de facto</i> world calendar.
 * <p>
 * The fields are defined as follows:
 * <ul>
 * <li>era - There are two eras, 'Current Era' (CE) and 'Before Current Era' (BCE).
 * <li>year-of-era - The year-of-era is the same as the proleptic-year for the current CE era.
 *  For the BCE era before the ISO epoch the year increases from 1 upwards as time goes backwards.
 * <li>proleptic-year - The proleptic year is the same as the year-of-era for the
 *  current era. For the previous era, years have zero, then negative values.
 * <li>month-of-year - There are 12 months in an ISO year, numbered from 1 to 12.
 * <li>day-of-month - There are between 28 and 31 days in each of the ISO month, numbered from 1 to 31.
 *  Months 4, 6, 9 and 11 have 30 days, Months 1, 3, 5, 7, 8, 10 and 12 have 31 days.
 *  Month 2 has 28 days, or 29 in a leap year.
 * <li>day-of-year - There are 365 days in a standard ISO year and 366 in a leap year.
 *  The days are numbered from 1 to 365 or 1 to 366.
 * <li>leap-year - Leap years occur every 4 years, except where the year is divisble by 100 and not divisble by 400.
 * </ul>
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  ISO日历系统
 * <p>
 * 这个年表定义了ISO日历系统的规则该日历系统基于ISO-8601标准,其是<i>事实</i>世界日历
 * <p>
 *  字段定义如下：
 * <ul>
 * <li>时代 - 有两个时代,"当代时代"(CE)和"在当前时代之前"(BCE)<li>年代 - 年代与晚年相同当前的CE时代对于BCE时代在ISO时代之前的年份,从1年开始,随着时间的倒退,它将从1增
 * 加<li>推测年 - 推测年与当前时代的年代相同。
 * 对于上一个时代,年份为零,然后为负值<li>年份 - 年份中有12个月,从1到12。
 * <li>每月日期 - 每个月有28到31天ISO月份,编号从1到31个月4,6,9和11有30天,第1,3,5,7,8,10和12月有31天。
 * 第2个月有28天,或闰年29<li>年份 - 标准ISO年份为365天,闰年为366天天数从1到365或1到366。<li>闰年 - 闰年每4年出现一次,除非年份除以100,而不是除以400。
 * </ul>
 * 
 * @implSpec这个类是不可变的和线程安全的
 * 
 * 
 * @since 1.8
 */
public final class IsoChronology extends AbstractChronology implements Serializable {

    /**
     * Singleton instance of the ISO chronology.
     * <p>
     *  Singleton实例的ISO年表
     * 
     */
    public static final IsoChronology INSTANCE = new IsoChronology();

    /**
     * Serialization version.
     * <p>
     *  序列化版本
     * 
     */
    private static final long serialVersionUID = -1440403870442975015L;

    /**
     * Restricted constructor.
     * <p>
     *  受限制的构造函数
     * 
     */
    private IsoChronology() {
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the ID of the chronology - 'ISO'.
     * <p>
     * The ID uniquely identifies the {@code Chronology}.
     * It can be used to lookup the {@code Chronology} using {@link #of(String)}.
     *
     * <p>
     *  获取年表的ID  - "ISO"
     * <p>
     *  ID唯一标识{@code Chronology}它可用于使用{@link #of(String)}查找{@code Chronology}
     * 
     * 
     * @return the chronology ID - 'ISO'
     * @see #getCalendarType()
     */
    @Override
    public String getId() {
        return "ISO";
    }

    /**
     * Gets the calendar type of the underlying calendar system - 'iso8601'.
     * <p>
     * The calendar type is an identifier defined by the
     * <em>Unicode Locale Data Markup Language (LDML)</em> specification.
     * It can be used to lookup the {@code Chronology} using {@link #of(String)}.
     * It can also be used as part of a locale, accessible via
     * {@link Locale#getUnicodeLocaleType(String)} with the key 'ca'.
     *
     * <p>
     *  获取基础日历系统的日历类型 - "iso8601"
     * <p>
     *  日历类型是由<em> Unicode区域设置数据标记语言(LDML)</em>规范定义的标识符。
     * 它可以用于使用{@link #of(String)}查找{@code Chronology}作为区域设置的一部分,可通过{@link Locale#getUnicodeLocaleType(String)}
     * 访问,其中键'ca'。
     *  日历类型是由<em> Unicode区域设置数据标记语言(LDML)</em>规范定义的标识符。
     * 
     * 
     * @return the calendar system type - 'iso8601'
     * @see #getId()
     */
    @Override
    public String getCalendarType() {
        return "iso8601";
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an ISO local date from the era, year-of-era, month-of-year
     * and day-of-month fields.
     *
     * <p>
     *  从时代,年份,年份和月份字段获取ISO本地日期
     * 
     * 
     * @param era  the ISO era, not null
     * @param yearOfEra  the ISO year-of-era
     * @param month  the ISO month-of-year
     * @param dayOfMonth  the ISO day-of-month
     * @return the ISO local date, not null
     * @throws DateTimeException if unable to create the date
     * @throws ClassCastException if the type of {@code era} is not {@code IsoEra}
     */
    @Override  // override with covariant return type
    public LocalDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
        return date(prolepticYear(era, yearOfEra), month, dayOfMonth);
    }

    /**
     * Obtains an ISO local date from the proleptic-year, month-of-year
     * and day-of-month fields.
     * <p>
     * This is equivalent to {@link LocalDate#of(int, int, int)}.
     *
     * <p>
     * 从原始年,月和月中获取ISO本地日期字段
     * <p>
     *  这相当于{@link LocalDate#of(int,int,int)}
     * 
     * 
     * @param prolepticYear  the ISO proleptic-year
     * @param month  the ISO month-of-year
     * @param dayOfMonth  the ISO day-of-month
     * @return the ISO local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public LocalDate date(int prolepticYear, int month, int dayOfMonth) {
        return LocalDate.of(prolepticYear, month, dayOfMonth);
    }

    /**
     * Obtains an ISO local date from the era, year-of-era and day-of-year fields.
     *
     * <p>
     *  从时代,年份和年份字段获取ISO本地日期
     * 
     * 
     * @param era  the ISO era, not null
     * @param yearOfEra  the ISO year-of-era
     * @param dayOfYear  the ISO day-of-year
     * @return the ISO local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public LocalDate dateYearDay(Era era, int yearOfEra, int dayOfYear) {
        return dateYearDay(prolepticYear(era, yearOfEra), dayOfYear);
    }

    /**
     * Obtains an ISO local date from the proleptic-year and day-of-year fields.
     * <p>
     * This is equivalent to {@link LocalDate#ofYearDay(int, int)}.
     *
     * <p>
     *  从引导年和字段获取ISO本地日期
     * <p>
     *  这相当于{@link LocalDate#ofYearDay(int,int)}
     * 
     * 
     * @param prolepticYear  the ISO proleptic-year
     * @param dayOfYear  the ISO day-of-year
     * @return the ISO local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public LocalDate dateYearDay(int prolepticYear, int dayOfYear) {
        return LocalDate.ofYearDay(prolepticYear, dayOfYear);
    }

    /**
     * Obtains an ISO local date from the epoch-day.
     * <p>
     * This is equivalent to {@link LocalDate#ofEpochDay(long)}.
     *
     * <p>
     *  从时代获取ISO本地日期
     * <p>
     *  这相当于{@link LocalDate#ofEpochDay(long)}
     * 
     * 
     * @param epochDay  the epoch day
     * @return the ISO local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public LocalDate dateEpochDay(long epochDay) {
        return LocalDate.ofEpochDay(epochDay);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an ISO local date from another date-time object.
     * <p>
     * This is equivalent to {@link LocalDate#from(TemporalAccessor)}.
     *
     * <p>
     *  从另一个日期时间对象获取ISO本地日期
     * <p>
     *  这相当于{@link LocalDate#from(TemporalAccessor)}
     * 
     * 
     * @param temporal  the date-time object to convert, not null
     * @return the ISO local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public LocalDate date(TemporalAccessor temporal) {
        return LocalDate.from(temporal);
    }

    /**
     * Obtains an ISO local date-time from another date-time object.
     * <p>
     * This is equivalent to {@link LocalDateTime#from(TemporalAccessor)}.
     *
     * <p>
     *  从另一个日期时间对象获取ISO本地日期时间
     * <p>
     *  这相当于{@link LocalDateTime#from(TemporalAccessor)}
     * 
     * 
     * @param temporal  the date-time object to convert, not null
     * @return the ISO local date-time, not null
     * @throws DateTimeException if unable to create the date-time
     */
    @Override  // override with covariant return type
    public LocalDateTime localDateTime(TemporalAccessor temporal) {
        return LocalDateTime.from(temporal);
    }

    /**
     * Obtains an ISO zoned date-time from another date-time object.
     * <p>
     * This is equivalent to {@link ZonedDateTime#from(TemporalAccessor)}.
     *
     * <p>
     *  从另一个日期时间对象获取ISO分区的日期时间
     * <p>
     * 这相当于{@link ZonedDateTime#from(TemporalAccessor)}
     * 
     * 
     * @param temporal  the date-time object to convert, not null
     * @return the ISO zoned date-time, not null
     * @throws DateTimeException if unable to create the date-time
     */
    @Override  // override with covariant return type
    public ZonedDateTime zonedDateTime(TemporalAccessor temporal) {
        return ZonedDateTime.from(temporal);
    }

    /**
     * Obtains an ISO zoned date-time in this chronology from an {@code Instant}.
     * <p>
     * This is equivalent to {@link ZonedDateTime#ofInstant(Instant, ZoneId)}.
     *
     * <p>
     *  在此年表中从{@code Instant}获取ISO分区的日期时间
     * <p>
     *  这相当于{@link ZonedDateTime#ofInstant(Instant,ZoneId)}
     * 
     * 
     * @param instant  the instant to create the date-time from, not null
     * @param zone  the time-zone, not null
     * @return the zoned date-time, not null
     * @throws DateTimeException if the result exceeds the supported range
     */
    @Override
    public ZonedDateTime zonedDateTime(Instant instant, ZoneId zone) {
        return ZonedDateTime.ofInstant(instant, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains the current ISO local date from the system clock in the default time-zone.
     * <p>
     * This will query the {@link Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current date.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区中从系统时钟获取当前ISO本地日期
     * <p>
     *  这将在默认时区中查询{@link Clock#systemDefaultZone()系统时钟}以获取当前日期
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的
     * 
     * 
     * @return the current ISO local date using the system clock and default time-zone, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public LocalDate dateNow() {
        return dateNow(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current ISO local date from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区从系统时钟获取当前ISO本地日期
     * <p>
     *  这将查询{@link Clock#system(ZoneId)系统时钟}以获取当前日期指定时区避免依赖于默认时区
     * <p>
     * 使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的
     * 
     * 
     * @return the current ISO local date using the system clock, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public LocalDate dateNow(ZoneId zone) {
        return dateNow(Clock.system(zone));
    }

    /**
     * Obtains the current ISO local date from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current date - today.
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@link Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前ISO本地日期
     * <p>
     *  这将查询指定的时钟以获取当前日期 - 今天使用此方法允许使用备用时钟进行测试备用时钟可以使用{@link时钟依赖注入}
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current ISO local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public LocalDate dateNow(Clock clock) {
        Objects.requireNonNull(clock, "clock");
        return date(LocalDate.now(clock));
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
     * @param prolepticYear  the ISO proleptic year to check
     * @return true if the year is leap, false otherwise
     */
    @Override
    public boolean isLeapYear(long prolepticYear) {
        return ((prolepticYear & 3) == 0) && ((prolepticYear % 100) != 0 || (prolepticYear % 400) == 0);
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        if (era instanceof IsoEra == false) {
            throw new ClassCastException("Era must be IsoEra");
        }
        return (era == IsoEra.CE ? yearOfEra : 1 - yearOfEra);
    }

    @Override
    public IsoEra eraOf(int eraValue) {
        return IsoEra.of(eraValue);
    }

    @Override
    public List<Era> eras() {
        return Arrays.<Era>asList(IsoEra.values());
    }

    //-----------------------------------------------------------------------
    /**
     * Resolves parsed {@code ChronoField} values into a date during parsing.
     * <p>
     * Most {@code TemporalField} implementations are resolved using the
     * resolve method on the field. By contrast, the {@code ChronoField} class
     * defines fields that only have meaning relative to the chronology.
     * As such, {@code ChronoField} date fields are resolved here in the
     * context of a specific chronology.
     * <p>
     * {@code ChronoField} instances on the ISO calendar system are resolved
     * as follows.
     * <ul>
     * <li>{@code EPOCH_DAY} - If present, this is converted to a {@code LocalDate}
     *  and all other date fields are then cross-checked against the date.
     * <li>{@code PROLEPTIC_MONTH} - If present, then it is split into the
     *  {@code YEAR} and {@code MONTH_OF_YEAR}. If the mode is strict or smart
     *  then the field is validated.
     * <li>{@code YEAR_OF_ERA} and {@code ERA} - If both are present, then they
     *  are combined to form a {@code YEAR}. In lenient mode, the {@code YEAR_OF_ERA}
     *  range is not validated, in smart and strict mode it is. The {@code ERA} is
     *  validated for range in all three modes. If only the {@code YEAR_OF_ERA} is
     *  present, and the mode is smart or lenient, then the current era (CE/AD)
     *  is assumed. In strict mode, no era is assumed and the {@code YEAR_OF_ERA} is
     *  left untouched. If only the {@code ERA} is present, then it is left untouched.
     * <li>{@code YEAR}, {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH} -
     *  If all three are present, then they are combined to form a {@code LocalDate}.
     *  In all three modes, the {@code YEAR} is validated. If the mode is smart or strict,
     *  then the month and day are validated, with the day validated from 1 to 31.
     *  If the mode is lenient, then the date is combined in a manner equivalent to
     *  creating a date on the first of January in the requested year, then adding
     *  the difference in months, then the difference in days.
     *  If the mode is smart, and the day-of-month is greater than the maximum for
     *  the year-month, then the day-of-month is adjusted to the last day-of-month.
     *  If the mode is strict, then the three fields must form a valid date.
     * <li>{@code YEAR} and {@code DAY_OF_YEAR} -
     *  If both are present, then they are combined to form a {@code LocalDate}.
     *  In all three modes, the {@code YEAR} is validated.
     *  If the mode is lenient, then the date is combined in a manner equivalent to
     *  creating a date on the first of January in the requested year, then adding
     *  the difference in days.
     *  If the mode is smart or strict, then the two fields must form a valid date.
     * <li>{@code YEAR}, {@code MONTH_OF_YEAR}, {@code ALIGNED_WEEK_OF_MONTH} and
     *  {@code ALIGNED_DAY_OF_WEEK_IN_MONTH} -
     *  If all four are present, then they are combined to form a {@code LocalDate}.
     *  In all three modes, the {@code YEAR} is validated.
     *  If the mode is lenient, then the date is combined in a manner equivalent to
     *  creating a date on the first of January in the requested year, then adding
     *  the difference in months, then the difference in weeks, then in days.
     *  If the mode is smart or strict, then the all four fields are validated to
     *  their outer ranges. The date is then combined in a manner equivalent to
     *  creating a date on the first day of the requested year and month, then adding
     *  the amount in weeks and days to reach their values. If the mode is strict,
     *  the date is additionally validated to check that the day and week adjustment
     *  did not change the month.
     * <li>{@code YEAR}, {@code MONTH_OF_YEAR}, {@code ALIGNED_WEEK_OF_MONTH} and
     *  {@code DAY_OF_WEEK} - If all four are present, then they are combined to
     *  form a {@code LocalDate}. The approach is the same as described above for
     *  years, months and weeks in {@code ALIGNED_DAY_OF_WEEK_IN_MONTH}.
     *  The day-of-week is adjusted as the next or same matching day-of-week once
     *  the years, months and weeks have been handled.
     * <li>{@code YEAR}, {@code ALIGNED_WEEK_OF_YEAR} and {@code ALIGNED_DAY_OF_WEEK_IN_YEAR} -
     *  If all three are present, then they are combined to form a {@code LocalDate}.
     *  In all three modes, the {@code YEAR} is validated.
     *  If the mode is lenient, then the date is combined in a manner equivalent to
     *  creating a date on the first of January in the requested year, then adding
     *  the difference in weeks, then in days.
     *  If the mode is smart or strict, then the all three fields are validated to
     *  their outer ranges. The date is then combined in a manner equivalent to
     *  creating a date on the first day of the requested year, then adding
     *  the amount in weeks and days to reach their values. If the mode is strict,
     *  the date is additionally validated to check that the day and week adjustment
     *  did not change the year.
     * <li>{@code YEAR}, {@code ALIGNED_WEEK_OF_YEAR} and {@code DAY_OF_WEEK} -
     *  If all three are present, then they are combined to form a {@code LocalDate}.
     *  The approach is the same as described above for years and weeks in
     *  {@code ALIGNED_DAY_OF_WEEK_IN_YEAR}. The day-of-week is adjusted as the
     *  next or same matching day-of-week once the years and weeks have been handled.
     * </ul>
     *
     * <p>
     *  在解析期间将解析的{@code ChronoField}值解析为日期
     * <p>
     *  大多数{@code TemporalField}实现使用字段上的resolve方法解析相比之下,{@code ChronoField}类定义了仅对于年表有意义的字段。
     * 因此,{@code ChronoField}日期字段在此解析特定年表的上下文。
     * <p>
     *  ISO日历系统上的{@code ChronoField}实例解析如下
     * <ul>
     * <li> {@ code EPOCH_DAY}  - 如果存在,系统会将其转换为{@code LocalDate},然后根据日期交叉检查所有其他日期字段。
     * <li> {@ code PROLEPTIC_MONTH}  - 如果存在,分割为{@code YEAR}和{@code MONTH_OF_YEAR}如果模式是严格的或智能的,则该字段将验证<li> {@ code YEAR_OF_ERA}
     * 和{@code ERA}  - 如果两者都存在,以形成{@code YEAR}在宽松模式下,{@code YEAR_OF_ERA}范围未经验证,在智能和严格模式下,{@code ERA}在所有三种模式下
     * 都被验证范围如果只有{@code YEAR_OF_ERA}存在,并且模式是智能或宽松,则假定当前时代(CE / AD)在严格模式中,不假定时代,并且{@code YEAR_OF_ERA}保持不变如果只有
     * {@code ERA}存在,那么它将保持不变。
     * <li> {@ code EPOCH_DAY}  - 如果存在,系统会将其转换为{@code LocalDate},然后根据日期交叉检查所有其他日期字段。
     * <li> {@ code YEAR},{@code MONTH_OF_YEAR}和{@code DAY_OF_MONTH}  - 如果三个都存在, {@code LocalDate}在所有三种模式下,{@code YEAR}
     * 被验证如果模式是智能或严格的,则验证月份和日期,日期验证从1到31如果模式是宽松的,日期以等同于在所请求的年份中在1月1日创建日期的方式组合,然后加上月份之间的差额,然后是天数差异如果模式是智能的,并且
     * 日期大于日期最大月份,然后将月份调整为最后一个日期如果模式是严格的,则三个字段必须形成有效日期<li> {@ code YEAR}和{@code DAY_OF_YEAR}  - 如果两者都存在,则会合并
     * 形成{@code LocalDate}。
     * <li> {@ code EPOCH_DAY}  - 如果存在,系统会将其转换为{@code LocalDate},然后根据日期交叉检查所有其他日期字段。
     * 在所有三种模式下,{@code YEAR} ,则日期以等同于在请求年份中在1月1日创建日期的方式组合,然后添加天数差异如果模式是智能或严格,则这两个字段必须形成有效日期<li> {@code YEAR}
     * ,{@code MONTH_OF_YEAR},{@code ALIGNED_WEEK_OF_MONTH}和{@code ALIGNED_DAY_OF_WEEK_IN_MONTH}  - 如果全部四个都存
     * 在,那么它们将组合成一个{@code LocalDate}代码YEAR}已验证如果模式是宽松的,那么日期以等同于在所请求的年份中在1月1日创建日期的方式组合,然后添加以月为单位的差,然后以星期为单位,然
     * 后以天为单位如果模式是智能的或严格,则所有四个字段都将验证为其外部范围。
     * <li> {@ code EPOCH_DAY}  - 如果存在,系统会将其转换为{@code LocalDate},然后根据日期交叉检查所有其他日期字段。
     * 然后,日期以相当于在请求的年份和月份的第一天创建日期的方式进行组合,然后添加以周和天为单位的数量,以达到其值如果模式为strict,则系统会另外验证日期,以检查日期和周的调整是否未更改<li> {@ code YEAR}
     * ,{@code MONTH_OF_YEAR},{@code ALIGNED_WEEK_OF_MONTH}和{@code DAY_OF_WEEK} - 如果所有四个都存在,则它们被组合以形成{@code LocalDate}
     * 
     * @param fieldValues  the map of fields to values, which can be updated, not null
     * @param resolverStyle  the requested type of resolve, not null
     * @return the resolved date, null if insufficient information to create a date
     * @throws DateTimeException if the date cannot be resolved, typically
     *  because of a conflict in the input data
     */
    @Override  // override for performance
    public LocalDate resolveDate(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        return (LocalDate) super.resolveDate(fieldValues, resolverStyle);
    }

    @Override  // override for better proleptic algorithm
    void resolveProlepticMonth(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        Long pMonth = fieldValues.remove(PROLEPTIC_MONTH);
        if (pMonth != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                PROLEPTIC_MONTH.checkValidValue(pMonth);
            }
            addFieldValue(fieldValues, MONTH_OF_YEAR, Math.floorMod(pMonth, 12) + 1);
            addFieldValue(fieldValues, YEAR, Math.floorDiv(pMonth, 12));
        }
    }

    @Override  // override for enhanced behaviour
    LocalDate resolveYearOfEra(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        Long yoeLong = fieldValues.remove(YEAR_OF_ERA);
        if (yoeLong != null) {
            if (resolverStyle != ResolverStyle.LENIENT) {
                YEAR_OF_ERA.checkValidValue(yoeLong);
            }
            Long era = fieldValues.remove(ERA);
            if (era == null) {
                Long year = fieldValues.get(YEAR);
                if (resolverStyle == ResolverStyle.STRICT) {
                    // do not invent era if strict, but do cross-check with year
                    if (year != null) {
                        addFieldValue(fieldValues, YEAR, (year > 0 ? yoeLong: Math.subtractExact(1, yoeLong)));
                    } else {
                        // reinstate the field removed earlier, no cross-check issues
                        fieldValues.put(YEAR_OF_ERA, yoeLong);
                    }
                } else {
                    // invent era
                    addFieldValue(fieldValues, YEAR, (year == null || year > 0 ? yoeLong: Math.subtractExact(1, yoeLong)));
                }
            } else if (era.longValue() == 1L) {
                addFieldValue(fieldValues, YEAR, yoeLong);
            } else if (era.longValue() == 0L) {
                addFieldValue(fieldValues, YEAR, Math.subtractExact(1, yoeLong));
            } else {
                throw new DateTimeException("Invalid value for era: " + era);
            }
        } else if (fieldValues.containsKey(ERA)) {
            ERA.checkValidValue(fieldValues.get(ERA));  // always validated
        }
        return null;
    }

    @Override  // override for performance
    LocalDate resolveYMD(Map <TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        int y = YEAR.checkValidIntValue(fieldValues.remove(YEAR));
        if (resolverStyle == ResolverStyle.LENIENT) {
            long months = Math.subtractExact(fieldValues.remove(MONTH_OF_YEAR), 1);
            long days = Math.subtractExact(fieldValues.remove(DAY_OF_MONTH), 1);
            return LocalDate.of(y, 1, 1).plusMonths(months).plusDays(days);
        }
        int moy = MONTH_OF_YEAR.checkValidIntValue(fieldValues.remove(MONTH_OF_YEAR));
        int dom = DAY_OF_MONTH.checkValidIntValue(fieldValues.remove(DAY_OF_MONTH));
        if (resolverStyle == ResolverStyle.SMART) {  // previous valid
            if (moy == 4 || moy == 6 || moy == 9 || moy == 11) {
                dom = Math.min(dom, 30);
            } else if (moy == 2) {
                dom = Math.min(dom, Month.FEBRUARY.length(Year.isLeap(y)));

            }
        }
        return LocalDate.of(y, moy, dom);
    }

    //-----------------------------------------------------------------------
    @Override
    public ValueRange range(ChronoField field) {
        return field.range();
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a period for this chronology based on years, months and days.
     * <p>
     * This returns a period tied to the ISO chronology using the specified
     * years, months and days. See {@link Period} for further details.
     *
     * <p>
     * 该方法与上述{@code ALIGNED_DAY_OF_WEEK_IN_MONTH}中的年,月和周相同。
     * <li> {@ code EPOCH_DAY}  - 如果存在,系统会将其转换为{@code LocalDate},然后根据日期交叉检查所有其他日期字段。
     * 一旦处理年,月和周,则将星期调整为下一个或相同的匹配星期日期。
     *  li> {@ code YEAR},{@code ALIGNED_WEEK_OF_YEAR}和{@code ALIGNED_DAY_OF_WEEK_IN_YEAR}  - 如果这三个都存在,那么它们会
     * 组合成一个{@code LocalDate}。
     * 一旦处理年,月和周,则将星期调整为下一个或相同的匹配星期日期。
     * 在所有三种模式下,{@code YEAR}验证如果模式是宽松的,那么日期以相当于在所请求的年份中在1月1日创建日期的方式组合,然后以周为单位加上差异,然后以天为单位如果模式是智能或严格的,则所有三个字段
     * 都被验证到它们的外部范围然后以等同于在所请求年份的第一天创建日期的方式组合日期,然后添加以周和天为单位的量以达到其值。
     * 一旦处理年,月和周,则将星期调整为下一个或相同的匹配星期日期。
     * 如果模式是严格的,则日期还被验证以检查日期和周调整没有改变年份<li> {@ code YEAR},{@code ALIGNED_WEEK_OF_YEAR}和{@code DAY_OF_WEEK}  -
     *  如果这三个都存在,那么它们被组合成一个{@code LocalDate}与上述{@code ALIGNED_DAY_OF_WEEK_IN_YEAR}中的年份和星期相同。
     * 一旦处理年,月和周,则将星期调整为下一个或相同的匹配星期日期。一旦处理了几年和几周,则将星期几调整为下一个或相同的匹配星期。
     * </ul>
     * 
     * @param years  the number of years, may be negative
     * @param months  the number of years, may be negative
     * @param days  the number of years, may be negative
     * @return the period in terms of this chronology, not null
     * @return the ISO period, not null
     */
    @Override  // override with covariant return type
    public Period period(int years, int months, int days) {
        return Period.of(years, months, days);
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the Chronology using a
     * <a href="../../../serialized-form.html#java.time.chrono.Ser">dedicated serialized form</a>.
     * <p>
     * 
     * 
     * @serialData
     * <pre>
     *  out.writeByte(1);     // identifies a Chronology
     *  out.writeUTF(getId());
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    @Override
    Object writeReplace() {
        return super.writeReplace();
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     * 基于年,月和日获取这个年表的期间
     * <p>
     *  这将返回一个与ISO年表相关的期间,使用指定的年份,月份和天数。有关详细信息,请参阅{@link Period}
     * 
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
