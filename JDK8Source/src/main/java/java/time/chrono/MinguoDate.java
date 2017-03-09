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

import static java.time.chrono.MinguoChronology.YEARS_DIFFERENCE;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
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
import java.util.Objects;

/**
 * A date in the Minguo calendar system.
 * <p>
 * This date operates using the {@linkplain MinguoChronology Minguo calendar}.
 * This calendar system is primarily used in the Republic of China, often known as Taiwan.
 * Dates are aligned such that {@code 0001-01-01 (Minguo)} is {@code 1912-01-01 (ISO)}.
 *
 * <p>
 * This is a <a href="{@docRoot}/java/lang/doc-files/ValueBased.html">value-based</a>
 * class; use of identity-sensitive operations (including reference equality
 * ({@code ==}), identity hash code, or synchronization) on instances of
 * {@code MinguoDate} may have unpredictable results and should be avoided.
 * The {@code equals} method should be used for comparisons.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  Minguo日历系统中的日期。
 * <p>
 *  此日期使用{@linkplain Minguo Chronology Minguo calendar}进行操作。这种日历系统主要用于中华民国,通常称为台湾。
 * 日期对齐,使{@code 0001-01-01(Minguo)}为{@code 1912-01-01(ISO)}。
 * 
 * <p>
 *  这是<a href="{@docRoot}/java/lang/doc-files/ValueBased.html">以价值为基础的</a>类;对{@code MinguoDate}实例使用身份敏感操
 * 作(包括引用等同({@code ==}),身份哈希码或同步)可能会产生不可预测的结果,应该避免使用。
 * 应该使用{@code equals}方法进行比较。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class MinguoDate
        extends ChronoLocalDateImpl<MinguoDate>
        implements ChronoLocalDate, Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 1300372329181994526L;

    /**
     * The underlying date.
     * <p>
     * 基础日期。
     * 
     */
    private final transient LocalDate isoDate;

    //-----------------------------------------------------------------------
    /**
     * Obtains the current {@code MinguoDate} from the system clock in the default time-zone.
     * <p>
     * This will query the {@link Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current date.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在默认时区中从系统时钟获取当前{@code MinguoDate}。
     * <p>
     *  这将在默认时区中查询{@link Clock#systemDefaultZone()系统时钟}以获取当前日期。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @return the current date using the system clock and default time-zone, not null
     */
    public static MinguoDate now() {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains the current {@code MinguoDate} from the system clock in the specified time-zone.
     * <p>
     * This will query the {@link Clock#system(ZoneId) system clock} to obtain the current date.
     * Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing
     * because the clock is hard-coded.
     *
     * <p>
     *  在指定的时区从系统时钟获取当前{@code MinguoDate}。
     * <p>
     *  这将查询{@link Clock#system(ZoneId)系统时钟}以获取当前日期。指定时区避免了对默认时区的依赖。
     * <p>
     *  使用此方法将会阻止使用备用时钟进行测试,因为时钟是硬编码的。
     * 
     * 
     * @param zone  the zone ID to use, not null
     * @return the current date using the system clock, not null
     */
    public static MinguoDate now(ZoneId zone) {
        return now(Clock.system(zone));
    }

    /**
     * Obtains the current {@code MinguoDate} from the specified clock.
     * <p>
     * This will query the specified clock to obtain the current date - today.
     * Using this method allows the use of an alternate clock for testing.
     * The alternate clock may be introduced using {@linkplain Clock dependency injection}.
     *
     * <p>
     *  从指定的时钟获取当前{@code MinguoDate}。
     * <p>
     *  这将查询指定的时钟以获取当前日期 - 今天。使用此方法允许使用替代时钟进行测试。可以使用{@linkplain时钟依赖注入}来引入替代时钟。
     * 
     * 
     * @param clock  the clock to use, not null
     * @return the current date, not null
     * @throws DateTimeException if the current date cannot be obtained
     */
    public static MinguoDate now(Clock clock) {
        return new MinguoDate(LocalDate.now(clock));
    }

    /**
     * Obtains a {@code MinguoDate} representing a date in the Minguo calendar
     * system from the proleptic-year, month-of-year and day-of-month fields.
     * <p>
     * This returns a {@code MinguoDate} with the specified fields.
     * The day must be valid for the year and month, otherwise an exception will be thrown.
     *
     * <p>
     *  从原始年,月和月的字段中获取表示Minguo日历系统中日期的{@code MinguoDate}。
     * <p>
     *  这将返回一个带有指定字段的{@code MinguoDate}。日期必须对年和月有效,否则将抛出异常。
     * 
     * 
     * @param prolepticYear  the Minguo proleptic-year
     * @param month  the Minguo month-of-year, from 1 to 12
     * @param dayOfMonth  the Minguo day-of-month, from 1 to 31
     * @return the date in Minguo calendar system, not null
     * @throws DateTimeException if the value of any field is out of range,
     *  or if the day-of-month is invalid for the month-year
     */
    public static MinguoDate of(int prolepticYear, int month, int dayOfMonth) {
        return new MinguoDate(LocalDate.of(prolepticYear + YEARS_DIFFERENCE, month, dayOfMonth));
    }

    /**
     * Obtains a {@code MinguoDate} from a temporal object.
     * <p>
     * This obtains a date in the Minguo calendar system based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code MinguoDate}.
     * <p>
     * The conversion typically uses the {@link ChronoField#EPOCH_DAY EPOCH_DAY}
     * field, which is standardized across calendar systems.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code MinguoDate::from}.
     *
     * <p>
     *  从临时对象获取{@code MinguoDate}。
     * <p>
     * 这基于指定的时间在Minguo日历系统中获得日期。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code MinguoDate}的实例。
     * <p>
     *  转化通常使用{@link ChronoField#EPOCH_DAY EPOCH_DAY}字段,该字段在日历系统中标准化。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code MinguoDate :: from}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the date in Minguo calendar system, not null
     * @throws DateTimeException if unable to convert to a {@code MinguoDate}
     */
    public static MinguoDate from(TemporalAccessor temporal) {
        return MinguoChronology.INSTANCE.date(temporal);
    }

    //-----------------------------------------------------------------------
    /**
     * Creates an instance from an ISO date.
     *
     * <p>
     *  从ISO日期创建实例。
     * 
     * 
     * @param isoDate  the standard local date, validated not null
     */
    MinguoDate(LocalDate isoDate) {
        Objects.requireNonNull(isoDate, "isoDate");
        this.isoDate = isoDate;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the chronology of this date, which is the Minguo calendar system.
     * <p>
     * The {@code Chronology} represents the calendar system in use.
     * The era and other fields in {@link ChronoField} are defined by the chronology.
     *
     * <p>
     *  获取此日期的年表,这是Minguo日历系统。
     * <p>
     *  {@code Chronology}表示正在使用的日历系统。 {@link ChronoField}中的时代和其他字段由年表定义。
     * 
     * 
     * @return the Minguo chronology, not null
     */
    @Override
    public MinguoChronology getChronology() {
        return MinguoChronology.INSTANCE;
    }

    /**
     * Gets the era applicable at this date.
     * <p>
     * The Minguo calendar system has two eras, 'ROC' and 'BEFORE_ROC',
     * defined by {@link MinguoEra}.
     *
     * <p>
     *  获取此日期适用的时代。
     * <p>
     *  Minguo日历系统有两个时间,'ROC'和'BEFORE_ROC',由{@link MinguoEra}定义。
     * 
     * 
     * @return the era applicable at this date, not null
     */
    @Override
    public MinguoEra getEra() {
        return (getProlepticYear() >= 1 ? MinguoEra.ROC : MinguoEra.BEFORE_ROC);
    }

    /**
     * Returns the length of the month represented by this date.
     * <p>
     * This returns the length of the month in days.
     * Month lengths match those of the ISO calendar system.
     *
     * <p>
     *  返回此日期表示的月份的长度。
     * <p>
     *  这将返回月份的长度(以天为单位)。月长度与ISO日历系统的长度相匹配。
     * 
     * 
     * @return the length of the month in days
     */
    @Override
    public int lengthOfMonth() {
        return isoDate.lengthOfMonth();
    }

    //-----------------------------------------------------------------------
    @Override
    public ValueRange range(TemporalField field) {
        if (field instanceof ChronoField) {
            if (isSupported(field)) {
                ChronoField f = (ChronoField) field;
                switch (f) {
                    case DAY_OF_MONTH:
                    case DAY_OF_YEAR:
                    case ALIGNED_WEEK_OF_MONTH:
                        return isoDate.range(field);
                    case YEAR_OF_ERA: {
                        ValueRange range = YEAR.range();
                        long max = (getProlepticYear() <= 0 ? -range.getMinimum() + 1 + YEARS_DIFFERENCE : range.getMaximum() - YEARS_DIFFERENCE);
                        return ValueRange.of(1, max);
                    }
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
                case PROLEPTIC_MONTH:
                    return getProlepticMonth();
                case YEAR_OF_ERA: {
                    int prolepticYear = getProlepticYear();
                    return (prolepticYear >= 1 ? prolepticYear : 1 - prolepticYear);
                }
                case YEAR:
                    return getProlepticYear();
                case ERA:
                    return (getProlepticYear() >= 1 ? 1 : 0);
            }
            return isoDate.getLong(field);
        }
        return field.getFrom(this);
    }

    private long getProlepticMonth() {
        return getProlepticYear() * 12L + isoDate.getMonthValue() - 1;
    }

    private int getProlepticYear() {
        return isoDate.getYear() - YEARS_DIFFERENCE;
    }

    //-----------------------------------------------------------------------
    @Override
    public MinguoDate with(TemporalField field, long newValue) {
        if (field instanceof ChronoField) {
            ChronoField f = (ChronoField) field;
            if (getLong(f) == newValue) {
                return this;
            }
            switch (f) {
                case PROLEPTIC_MONTH:
                    getChronology().range(f).checkValidValue(newValue, f);
                    return plusMonths(newValue - getProlepticMonth());
                case YEAR_OF_ERA:
                case YEAR:
                case ERA: {
                    int nvalue = getChronology().range(f).checkValidIntValue(newValue, f);
                    switch (f) {
                        case YEAR_OF_ERA:
                            return with(isoDate.withYear(getProlepticYear() >= 1 ? nvalue + YEARS_DIFFERENCE : (1 - nvalue)  + YEARS_DIFFERENCE));
                        case YEAR:
                            return with(isoDate.withYear(nvalue + YEARS_DIFFERENCE));
                        case ERA:
                            return with(isoDate.withYear((1 - getProlepticYear()) + YEARS_DIFFERENCE));
                    }
                }
            }
            return with(isoDate.with(field, newValue));
        }
        return super.with(field, newValue);
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
    public  MinguoDate with(TemporalAdjuster adjuster) {
        return super.with(adjuster);
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
    public MinguoDate plus(TemporalAmount amount) {
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
    public MinguoDate minus(TemporalAmount amount) {
        return super.minus(amount);
    }

    //-----------------------------------------------------------------------
    @Override
    MinguoDate plusYears(long years) {
        return with(isoDate.plusYears(years));
    }

    @Override
    MinguoDate plusMonths(long months) {
        return with(isoDate.plusMonths(months));
    }

    @Override
    MinguoDate plusWeeks(long weeksToAdd) {
        return super.plusWeeks(weeksToAdd);
    }

    @Override
    MinguoDate plusDays(long days) {
        return with(isoDate.plusDays(days));
    }

    @Override
    public MinguoDate plus(long amountToAdd, TemporalUnit unit) {
        return super.plus(amountToAdd, unit);
    }

    @Override
    public MinguoDate minus(long amountToAdd, TemporalUnit unit) {
        return super.minus(amountToAdd, unit);
    }

    @Override
    MinguoDate minusYears(long yearsToSubtract) {
        return super.minusYears(yearsToSubtract);
    }

    @Override
    MinguoDate minusMonths(long monthsToSubtract) {
        return super.minusMonths(monthsToSubtract);
    }

    @Override
    MinguoDate minusWeeks(long weeksToSubtract) {
        return super.minusWeeks(weeksToSubtract);
    }

    @Override
    MinguoDate minusDays(long daysToSubtract) {
        return super.minusDays(daysToSubtract);
    }

    private MinguoDate with(LocalDate newDate) {
        return (newDate.equals(isoDate) ? this : new MinguoDate(newDate));
    }

    @Override        // for javadoc and covariant return type
    @SuppressWarnings("unchecked")
    public final ChronoLocalDateTime<MinguoDate> atTime(LocalTime localTime) {
        return (ChronoLocalDateTime<MinguoDate>)super.atTime(localTime);
    }

    @Override
    public ChronoPeriod until(ChronoLocalDate endDate) {
        Period period = isoDate.until(endDate);
        return getChronology().period(period.getYears(), period.getMonths(), period.getDays());
    }

    @Override  // override for performance
    public long toEpochDay() {
        return isoDate.toEpochDay();
    }

    //-------------------------------------------------------------------------
    /**
     * Compares this date to another date, including the chronology.
     * <p>
     * Compares this {@code MinguoDate} with another ensuring that the date is the same.
     * <p>
     * Only objects of type {@code MinguoDate} are compared, other types return false.
     * To compare the dates of two {@code TemporalAccessor} instances, including dates
     * in two different chronologies, use {@link ChronoField#EPOCH_DAY} as a comparator.
     *
     * <p>
     *  将此日期与另一个日期(包括年表)进行比较。
     * <p>
     *  将此{@code MinguoDate}与另一个比较,确保日期是相同的。
     * <p>
     * 仅比较{@code MinguoDate}类型的对象,其他类型返回false。
     * 要比较两个{@code TemporalAccessor}实例的日期,包括两个不同年代的日期,请使用{@link ChronoField#EPOCH_DAY}作为比较。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other date
     */
    @Override  // override for performance
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MinguoDate) {
            MinguoDate otherDate = (MinguoDate) obj;
            return this.isoDate.equals(otherDate.isoDate);
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
        return getChronology().getId().hashCode() ^ isoDate.hashCode();
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
     *  out.writeByte(8);                 // identifies a MinguoDate
     *  out.writeInt(get(YEAR));
     *  out.writeByte(get(MONTH_OF_YEAR));
     *  out.writeByte(get(DAY_OF_MONTH));
     * </pre>
     *
     * @return the instance of {@code Ser}, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.MINGUO_DATE_TYPE, this);
    }

    void writeExternal(DataOutput out) throws IOException {
        // MinguoChronology is implicit in the MINGUO_DATE_TYPE
        out.writeInt(get(YEAR));
        out.writeByte(get(MONTH_OF_YEAR));
        out.writeByte(get(DAY_OF_MONTH));
    }

    static MinguoDate readExternal(DataInput in) throws IOException {
        int year = in.readInt();
        int month = in.readByte();
        int dayOfMonth = in.readByte();
        return MinguoChronology.INSTANCE.date(year, month, dayOfMonth);
    }

}
