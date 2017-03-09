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
package java.time.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

/**
 * A date in the Hijrah calendar system.
 * <p>
 * This date operates using one of several variants of the
 * {@linkplain HijrahChronology Hijrah calendar}.
 * <p>
 * The Hijrah calendar has a different total of days in a year than
 * Gregorian calendar, and the length of each month is based on the period
 * of a complete revolution of the moon around the earth
 * (as between successive new moons).
 * Refer to the {@link HijrahChronology} for details of supported variants.
 * <p>
 * Each HijrahDate is created bound to a particular HijrahChronology,
 * The same chronology is propagated to each HijrahDate computed from the date.
 * To use a different Hijrah variant, its HijrahChronology can be used
 * to create new HijrahDate instances.
 * Alternatively, the {@link #withVariant} method can be used to convert
 * to a new HijrahChronology.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code HijrahDate} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  Hijrah日历系统中的日期。
 * <p>
 *  此日期使用{@linkplain Hijrah Chronology Hijrah calendar}的几个变体之一运行。
 * <p>
 *  Hijrah日历在一年中具有不同于Gregorian日历的总天数,并且每月的长度基于月球在地球周围的完整旋转的周期(在连续的新卫星之间)。
 * 有关支持的变量的详细信息,请参阅{@link Hijrah Chronology}。
 * <p>
 * 每个HijrahDate被创建为绑定到特定的HijrahChronology。相同的年表被传播到从日期计算的每个HijrahDate。
 * 要使用不同的Hijrah变体,其HijrahChronology可用于创建新的HijrahDate实例。
 * 或者,可以使用{@link #withVariant}方法转换为新的HijrahChronology。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;在{@code HijrahDate}的实例上使用身份敏
 * 感操作(包括引用相等({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class HijrahDate
        extends ChronoLocalDateImpl<HijrahDate>
        implements ChronoLocalDate, Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = -5207853542612002020L;
    /**
     * The Chronology of this HijrahDate.
     * <p>
     *  这个HijrahDate的年表。
     * 
     */
    private final transient HijrahChronology chrono;
    /**
     * The proleptic year.
     * <p>
     *  晚年。
     * 
     */
    private final transient int prolepticYear;
    /**
     * The month-of-year.
     * <p>
     *  一年中的月份。
     * 
     */
    private final transient int monthOfYear;
    /**
     * The day-of-month.
     * <p>
     *  日期。
     * 
     */
    private final transient int dayOfMonth;

    //-------------------------------------------------------------------------
    /**
     * Obtains an instance of {@code HijrahDate} from the Hijrah proleptic year,
     * month-of-year and day-of-month.
     *
     * <p>
     *  从Hijrah proleptic年,月和月中获取{@code HijrahDate}的实例。
     * 
     * 
     * @param prolepticYear  the proleptic year to represent in the Hijrah calendar
     * @param monthOfYear  the month-of-year to represent, from 1 to 12
     * @param dayOfMonth  the day-of-month to represent, from 1 to 30
     * @return the Hijrah date, never null
     * @throws DateTimeException if the value of any field is out of range
     */
    static HijrahDate of(HijrahChronology chrono, int prolepticYear, int monthOfYear, int dayOfMonth) {
        return new HijrahDate(chrono, prolepticYear, monthOfYear, dayOfMonth);
    }

    /**
     * Returns a HijrahDate for the chronology and epochDay.
     * <p>
     *  返回年表和epochDay的HijrahDate。
     * 
     * 
     * @param chrono The Hijrah chronology
     * @param epochDay the epoch day
     * @return a HijrahDate for the epoch day; non-null
     */
    static HijrahDate ofEpochDay(HijrahChronology chrono, long epochDay) {
        return new HijrahDate(chrono, epochDay);
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains the current {@code HijrahDate} of the Islamic Umm Al-Qura calendar
     * in the default time-zone.
     * <p>
     * This will query the {@link Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current date.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区获取伊斯兰世界杯日历的当前{@code HijrahDate}。
     * <p>
     *  这将在默认时区中查询{@link Clock#systemDefaultZone()系统时钟}以获取当前日期。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @return the current date using the system clock and default time-zone, not null
     */
    public static HijrahDate now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current {@code HijrahDate} of the Islamic Umm Al-Qura calendar
     * in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区获取伊斯兰教义日历的当前{@code HijrahDate}。
     * <p>
     * 这将查询{@link Clock#system(ZoneId)系统时钟}以获取当前日期。指定时区避免了对默认时区的依赖。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current date using the system clock, not null
     */
    public static HijrahDate now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current {@code HijrahDate} of the Islamic Umm Al-Qura calendar
     * from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current date - today.
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@linkplain Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取伊斯兰Umm Al-Qura日历的当前{@code HijrahDate}。
     * <p>
     *  这将查询指定的时钟以获取当前日期 - 今天。使用此方法允许使用替代时钟进行测试。可以使用{@linkplain时钟依赖注入}来引入替代时钟。
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current date, not null
     * @throws DateTimeException if the current date cannot be obtained
     */
    public static HijrahDate now(Clock clock) {
        return HijrahDate.ofEpochDay(HijrahChronology.INSTANCE, LocalDate.now(clock).toEpochDay());
    }

    /**
     * Obtains a {@code HijrahDate} of the Islamic Umm Al-Qura calendar
     * from the proleptic-year, month-of-year and day-of-month fields.
     * <p>
     * This returns a {@code HijrahDate} with the specified fields.
     * The day must be valid for the year and month, otherwise an exception will be thrown.
     *
     * <p>
     *  从推测年,月和月的字段中获取伊斯兰铀铝库拉日历的{@code HijrahDate}。
     * <p>
     *  这将返回一个带有指定字段的{@code HijrahDate}。日期必须对年和月有效,否则将抛出异常。
     * 
     * 
     * @param prolepticYear  the Hijrah proleptic-year
     * @param month  the Hijrah month-of-year, from 1 to 12
     * @param dayOfMonth  the Hijrah day-of-month, from 1 to 30
     * @return the date in Hijrah calendar system, not null
     * @throws DateTimeException if the value of any field is out of range,
     *  or if the day-of-month is invalid for the month-year
     */
    public static HijrahDate of(int prolepticYear, int month, int dayOfMonth) {
        return HijrahChronology.INSTANCE.date(prolepticYear, month, dayOfMonth);
    }

    /**
     * Obtains a {@code HijrahDate} of the Islamic Umm Al-Qura calendar from a temporal object.
     * <p>
     * This obtains a date in the Hijrah calendar system based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code HijrahDate}.
     * <p>
     * The conversion typically uses the {@link ChronoField#EPOCH_DAY EPOCH_DAY}
     * field, which is standardized across calendar systems.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code HijrahDate::from}.
     *
     * <p>
     *  从一个时空对象中获取伊斯兰Umm Al-Qura日历的{@code HijrahDate}。
     * <p>
     *  这基于指定的时间在Hijrah日历系统中获得日期。 {@code TemporalAccessor}表示一组任意的日期和时间信息,该工厂将其转换为{@code HijrahDate}的实例。
     * <p>
     *  转化通常使用{@link ChronoField#EPOCH_DAY EPOCH_DAY}字段,该字段在日历系统中标准化。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code HijrahDate :: from}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the date in Hijrah calendar system, not null
     * @throws DateTimeException if unable to convert to a {@code HijrahDate}
     */
    public static HijrahDate from(TemporalAccessor temporal) {
        return HijrahChronology.INSTANCE.date(temporal);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructs an {@code HijrahDate} with the proleptic-year, month-of-year and
     * day-of-month fields.
     *
     * <p>
     * 用冒号年,月和月的字段构造{@code HijrahDate}。
     * 
     * 
     * @param chrono The chronology to create the date with
     * @param prolepticYear the proleptic year
     * @param monthOfYear the month of year
     * @param dayOfMonth the day of month
     */
    private HijrahDate(HijrahChronology chrono, int prolepticYear, int monthOfYear, int dayOfMonth) {
        // Computing the Gregorian day checks the valid ranges
        chrono.getEpochDay(prolepticYear, monthOfYear, dayOfMonth);

        this.chrono = chrono;
        this.prolepticYear = prolepticYear;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Constructs an instance with the Epoch Day.
     *
     * <p>
     *  构造一个具有"纪元日"的实例。
     * 
     * 
     * @param epochDay  the epochDay
     */
    private HijrahDate(HijrahChronology chrono, long epochDay) {
        int[] dateInfo = chrono.getHijrahDateInfo((int)epochDay);

        this.chrono = chrono;
        this.prolepticYear = dateInfo[0];
        this.monthOfYear = dateInfo[1];
        this.dayOfMonth = dateInfo[2];
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the chronology of this date, which is the Hijrah calendar system.
     * <p>
     * The {@code Chronology} represents the calendar system in use.
     * The era and other fields in {@link ChronoField} are defined by the chronology.
     *
     * <p>
     *  获取此日期的年表,这是Hijrah日历系统。
     * <p>
     *  {@code Chronology}表示正在使用的日历系统。 {@link ChronoField}中的时代和其他字段由年表定义。
     * 
     * 
     * @return the Hijrah chronology, not null
     */
    @Override
    public HijrahChronology getChronology() {
        return chrono;
    }

    /**
     * Gets the era applicable at this date.
     * <p>
     * The Hijrah calendar system has one era, 'AH',
     * defined by {@link HijrahEra}.
     *
     * <p>
     *  获取此日期适用的时代。
     * <p>
     *  Hijrah日历系统有一个时代,'AH',由{@link HijrahEra}定义。
     * 
     * 
     * @return the era applicable at this date, not null
     */
    @Override
    public HijrahEra getEra() {
        return HijrahEra.AH;
    }

    /**
     * Returns the length of the month represented by this date.
     * <p>
     * This returns the length of the month in days.
     * Month lengths in the Hijrah calendar system vary between 29 and 30 days.
     *
     * <p>
     *  返回此日期表示的月份的长度。
     * <p>
     *  这将返回月份的长度(以天为单位)。 Hijrah日历系统的月份长度在29和30天之间变化。
     * 
     * 
     * @return the length of the month in days
     */
    @Override
    public int lengthOfMonth() {
        return chrono.getMonthLength(prolepticYear, monthOfYear);
    }

    /**
     * Returns the length of the year represented by this date.
     * <p>
     * This returns the length of the year in days.
     * A Hijrah calendar system year is typically shorter than
     * that of the ISO calendar system.
     *
     * <p>
     *  返回由此日期表示的年份的长度。
     * <p>
     *  这将返回年的长度(以天为单位)。 Hijrah日历系统年通常短于ISO日历系统的年。
     * 
     * 
     * @return the length of the year in days
     */
    @Override
    public int lengthOfYear() {
        return chrono.getYearLength(prolepticYear);
    }

    //-----------------------------------------------------------------------
    @Override
    public ValueRange range(TemporalField field) {
        if (field instanceof ChronoField) {
            if (isSupported(field)) {
                ChronoField f = (ChronoField) field;
                switch (f) {
                    case DAY_OF_MONTH: return ValueRange.of(1, lengthOfMonth());
                    case DAY_OF_YEAR: return ValueRange.of(1, lengthOfYear());
                    case ALIGNED_WEEK_OF_MONTH: return ValueRange.of(1, 5);  // TODO
                    // TODO does the limited range of valid years cause years to
                    // start/end part way through? that would affect range
                }
                return getChronology().range(f);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.rangeRefinedBy(this);
    }

    @Override
    public long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case DAY_OF_WEEK: return getDayOfWeek();
                case ALIGNED_DAY_OF_WEEK_IN_MONTH: return ((getDayOfWeek() - 1) % 7) + 1;
                case ALIGNED_DAY_OF_WEEK_IN_YEAR: return ((getDayOfYear() - 1) % 7) + 1;
                case DAY_OF_MONTH: return this.dayOfMonth;
                case DAY_OF_YEAR: return this.getDayOfYear();
                case EPOCH_DAY: return toEpochDay();
                case ALIGNED_WEEK_OF_MONTH: return ((dayOfMonth - 1) / 7) + 1;
                case ALIGNED_WEEK_OF_YEAR: return ((getDayOfYear() - 1) / 7) + 1;
                case MONTH_OF_YEAR: return monthOfYear;
                case PROLEPTIC_MONTH: return getProlepticMonth();
                case YEAR_OF_ERA: return prolepticYear;
                case YEAR: return prolepticYear;
                case ERA: return getEraValue();
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.getFrom(this);
    }

    private long getProlepticMonth() {
        return prolepticYear * 12L + monthOfYear - 1;
    }

    @Override
    public HijrahDate with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            // not using checkValidIntValue so EPOCH_DAY and PROLEPTIC_MONTH work
            chrono.range(f).checkValidValue(newValue, f);    // TODO: validate value
            int nvalue = (int) newValue;
            switch (f) {
                case DAY_OF_WEEK: return plusDays(newValue - getDayOfWeek());
                case ALIGNED_DAY_OF_WEEK_IN_MONTH: return plusDays(newValue - getLong(ALIGNED_DAY_OF_WEEK_IN_MONTH));
                case ALIGNED_DAY_OF_WEEK_IN_YEAR: return plusDays(newValue - getLong(ALIGNED_DAY_OF_WEEK_IN_YEAR));
                case DAY_OF_MONTH: return resolvePreviousValid(prolepticYear, monthOfYear, nvalue);
                case DAY_OF_YEAR: return plusDays(Math.min(nvalue, lengthOfYear()) - getDayOfYear());
                case EPOCH_DAY: return new HijrahDate(chrono, newValue);
                case ALIGNED_WEEK_OF_MONTH: return plusDays((newValue - getLong(ALIGNED_WEEK_OF_MONTH)) * 7);
                case ALIGNED_WEEK_OF_YEAR: return plusDays((newValue - getLong(ALIGNED_WEEK_OF_YEAR)) * 7);
                case MONTH_OF_YEAR: return resolvePreviousValid(prolepticYear, nvalue, dayOfMonth);
                case PROLEPTIC_MONTH: return plusMonths(newValue - getProlepticMonth());
                case YEAR_OF_ERA: return resolvePreviousValid(prolepticYear >= 1 ? nvalue : 1 - nvalue, monthOfYear, dayOfMonth);
                case YEAR: return resolvePreviousValid(nvalue, monthOfYear, dayOfMonth);
                case ERA: return resolvePreviousValid(1 - prolepticYear, monthOfYear, dayOfMonth);
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return super.with(field, newValue);
    }

    private HijrahDate resolvePreviousValid(int prolepticYear, int month, int day) {
        int monthDays = chrono.getMonthLength(prolepticYear, month);
        if (day > monthDays) {
            day = monthDays;
        }
        return HijrahDate.of(chrono, prolepticYear, month, day);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException if unable to make the adjustment.
     *     For example, if the adjuster requires an ISO chronology
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    public  HijrahDate with(TemporalAdjuster adjuster) {
        return super.with(adjuster);
    }

    /**
     * Returns a {@code HijrahDate} with the Chronology requested.
     * <p>
     * The year, month, and day are checked against the new requested
     * HijrahChronology.  If the chronology has a shorter month length
     * for the month, the day is reduced to be the last day of the month.
     *
     * <p>
     *  根据请求的年表返回{@code HijrahDate}。
     * <p>
     *  根据新请求的Hijrah Chronology检查年,月和日。如果年表的月份的月份较短,那么日期将缩短为月份的最后一天。
     * 
     * 
     * @param chronology the new HijrahChonology, non-null
     * @return a HijrahDate with the requested HijrahChronology, non-null
     */
    public HijrahDate withVariant(HijrahChronology chronology) {
        if (chrono == chronology) {
            return this;
        }
        // Like resolvePreviousValid the day is constrained to stay in the same month
        int monthDays = chronology.getDayOfYear(prolepticYear, monthOfYear);
        return HijrahDate.of(chronology, prolepticYear, monthOfYear,(dayOfMonth > monthDays) ? monthDays : dayOfMonth );
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    public HijrahDate plus(TemporalAmount amount) {
        return super.plus(amount);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws DateTimeException {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Override
    public HijrahDate minus(TemporalAmount amount) {
        return super.minus(amount);
    }

    @Override
    public long toEpochDay() {
        return chrono.getEpochDay(prolepticYear, monthOfYear, dayOfMonth);
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
     * @return the day-of-year
     */
    private int getDayOfYear() {
        return chrono.getDayOfYear(prolepticYear, monthOfYear) + dayOfMonth;
    }

    /**
     * Gets the day-of-week value.
     *
     * <p>
     *  获取星期值。
     * 
     * 
     * @return the day-of-week; computed from the epochday
     */
    private int getDayOfWeek() {
        int dow0 = (int)Math.floorMod(toEpochDay() + 3, 7);
        return dow0 + 1;
    }

    /**
     * Gets the Era of this date.
     *
     * <p>
     *  获取此日期的时代。
     * 
     * 
     * @return the Era of this date; computed from epochDay
     */
    private int getEraValue() {
        return (prolepticYear > 1 ? 1 : 0);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the year is a leap year, according to the Hijrah calendar system rules.
     *
     * <p>
     *  根据Hijrah日历系统规则检查年份是否为闰年。
     * 
     * 
     * @return true if this date is in a leap year
     */
    @Override
    public boolean isLeapYear() {
        return chrono.isLeapYear(prolepticYear);
    }

    //-----------------------------------------------------------------------
    @Override
    HijrahDate plusYears(long years) {
        if (years == 0) {
            return this;
        }
        int newYear = Math.addExact(this.prolepticYear, (int)years);
        return resolvePreviousValid(newYear, monthOfYear, dayOfMonth);
    }

    @Override
    HijrahDate plusMonths(long monthsToAdd) {
        if (monthsToAdd == 0) {
            return this;
        }
        long monthCount = prolepticYear * 12L + (monthOfYear - 1);
        long calcMonths = monthCount + monthsToAdd;  // safe overflow
        int newYear = chrono.checkValidYear(Math.floorDiv(calcMonths, 12L));
        int newMonth = (int)Math.floorMod(calcMonths, 12L) + 1;
        return resolvePreviousValid(newYear, newMonth, dayOfMonth);
    }

    @Override
    HijrahDate plusWeeks(long weeksToAdd) {
        return super.plusWeeks(weeksToAdd);
    }

    @Override
    HijrahDate plusDays(long days) {
        return new HijrahDate(chrono, toEpochDay() + days);
    }

    @Override
    public HijrahDate plus(long amountToAdd, TemporalUnit unit) {
        return super.plus(amountToAdd, unit);
    }

    @Override
    public HijrahDate minus(long amountToSubtract, TemporalUnit unit) {
        return super.minus(amountToSubtract, unit);
    }

    @Override
    HijrahDate minusYears(long yearsToSubtract) {
        return super.minusYears(yearsToSubtract);
    }

    @Override
    HijrahDate minusMonths(long monthsToSubtract) {
        return super.minusMonths(monthsToSubtract);
    }

    @Override
    HijrahDate minusWeeks(long weeksToSubtract) {
        return super.minusWeeks(weeksToSubtract);
    }

    @Override
    HijrahDate minusDays(long daysToSubtract) {
        return super.minusDays(daysToSubtract);
    }

    @Override        // for javadoc and covariant return type
    @SuppressWarnings("unchecked")
    public final ChronoLocalDateTime<HijrahDate> atTime(LocalTime localTime) {
        return (ChronoLocalDateTime<HijrahDate>)super.atTime(localTime);
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate endDate) {
        // TODO: untested
        HijrahDate end = getChronology().date(endDate);
        long totalMonths = (end.prolepticYear - this.prolepticYear) * 12 + (end.monthOfYear - this.monthOfYear);  // safe
        int days = end.dayOfMonth - this.dayOfMonth;
        if (totalMonths > 0 && days < 0) {
            totalMonths--;
            HijrahDate calcDate = this.plusMonths(totalMonths);
            days = (int) (end.toEpochDay() - calcDate.toEpochDay());  // safe
        } else if (totalMonths < 0 && days > 0) {
            totalMonths++;
            days -= end.lengthOfMonth();
        }
        long years = totalMonths / 12;  // safe
        int months = (int) (totalMonths % 12);  // safe
        return getChronology().period(Math.toIntExact(years), months, days);
    }

    //-------------------------------------------------------------------------
    /**
     * Compares this date to another date, including the chronology.
     * <p>
     * Compares this {@code HijrahDate} with another ensuring that the date is the same.
     * <p>
     * Only objects of type {@code HijrahDate} are compared, other types return false.
     * To compare the dates of two {@code TemporalAccessor} instances, including dates
     * in two different chronologies, use {@link ChronoField#EPOCH_DAY} as a comparator.
     *
     * <p>
     *  将此日期与另一个日期(包括年表)进行比较。
     * <p>
     * 将此{@code HijrahDate}与另一个比较,确保日期是相同的。
     * <p>
     *  仅比较{@code HijrahDate}类型的对象,其他类型返回false。
     * 要比较两个{@code TemporalAccessor}实例的日期,包括两个不同年代的日期,请使用{@link ChronoField#EPOCH_DAY}作为比较。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other date and the Chronologies are equal
     */
    @Override  // override for performance
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof HijrahDate) {
            HijrahDate otherDate = (HijrahDate) obj;
            return prolepticYear == otherDate.prolepticYear
                && this.monthOfYear == otherDate.monthOfYear
                && this.dayOfMonth == otherDate.dayOfMonth
                && getChronology().equals(otherDate.getChronology());
        }
        return false;
    }

    /**
     * A hash code for this date.
     *
     * <p>
     *  此日期的哈希码。
     * 
     * 
     * @return a suitable hash code based only on the Chronology and the date
     */
    @Override  // override for performance
    public int hashCode() {
        int yearValue = prolepticYear;
        int monthValue = monthOfYear;
        int dayValue = dayOfMonth;
        return getChronology().getId().hashCode() ^ (yearValue & 0xFFFFF800)
                ^ ((yearValue << 11) + (monthValue << 6) + (dayValue));
    }

    //-----------------------------------------------------------------------
    /**
     * Defend against malicious streams.
     *
     * <p>
     *  防御恶意流。
     * 
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /**
     * Writes the object using a
     * <a href="../../../serialized-form.html#java.time.chrono.Ser">dedicated serialized form</a>.
     * <p>
     *  使用<a href="../../../serialized-form.html#java.time.chrono.Ser">专用序列化表单</a>写入对象。
     * 
     * @serialData
     * <pre>
     *  out.writeByte(6);                 // identifies a HijrahDate
     *  out.writeObject(chrono);          // the HijrahChronology variant
     *  out.writeInt(get(YEAR));
     *  out.writeByte(get(MONTH_OF_YEAR));
     *  out.writeByte(get(DAY_OF_MONTH));
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.HIJRAH_DATE_TYPE, this);
    }

    void writeExternal(ObjectOutput out) throws IOException {
        // HijrahChronology is implicit in the Hijrah_DATE_TYPE
        out.writeObject(getChronology());
        out.writeInt(get(YEAR));
        out.writeByte(get(MONTH_OF_YEAR));
        out.writeByte(get(DAY_OF_MONTH));
    }

    static HijrahDate readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        HijrahChronology chrono = (HijrahChronology) in.readObject();
        int year = in.readInt();
        int month = in.readByte();
        int dayOfMonth = in.readByte();
        return chrono.date(year, month, dayOfMonth);
    }

}
