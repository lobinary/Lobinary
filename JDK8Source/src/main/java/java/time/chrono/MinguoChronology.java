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

import java.io.InvalidObjectException;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The Minguo calendar system.
 * <p>
 * This chronology defines the rules of the Minguo calendar system.
 * This calendar system is primarily used in the Republic of China, often known as Taiwan.
 * Dates are aligned such that {@code 0001-01-01 (Minguo)} is {@code 1912-01-01 (ISO)}.
 * <p>
 * The fields are defined as follows:
 * <ul>
 * <li>era - There are two eras, the current 'Republic' (ERA_ROC) and the previous era (ERA_BEFORE_ROC).
 * <li>year-of-era - The year-of-era for the current era increases uniformly from the epoch at year one.
 *  For the previous era the year increases from one as time goes backwards.
 *  The value for the current era is equal to the ISO proleptic-year minus 1911.
 * <li>proleptic-year - The proleptic year is the same as the year-of-era for the
 *  current era. For the previous era, years have zero, then negative values.
 *  The value is equal to the ISO proleptic-year minus 1911.
 * <li>month-of-year - The Minguo month-of-year exactly matches ISO.
 * <li>day-of-month - The Minguo day-of-month exactly matches ISO.
 * <li>day-of-year - The Minguo day-of-year exactly matches ISO.
 * <li>leap-year - The Minguo leap-year pattern exactly matches ISO, such that the two calendars
 *  are never out of step.
 * </ul>
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  Minguo日历系统。
 * <p>
 *  这个年表定义了Minguo日历系统的规则。这种日历系统主要用于中华民国,通常称为台湾。
 * 日期对齐,使{@code 0001-01-01(Minguo)}为{@code 1912-01-01(ISO)}。
 * <p>
 *  字段定义如下：
 * <ul>
 * <li> era  - 有两个时代,当前的"共和国"(ERA_ROC)和前一个时代(ERA_BEFORE_ROC)。 <li>年代 - 当前时代的年代从第一年的时代开始一致地增加。
 * 对于前一个时代,随着时间的推移,这一年逐渐增加。当前时代的价值等于ISO proleptic-year减去1911年。
 * <li> proleptic-year  -  propptic-year  - 这个年代与当前时代的年代相同。对于前一个时代,年份为零,然后为负值。
 * 该值等于ISO proleptic-year减去1911年。<li> year-of-year  -  Minguo年份与ISO完全匹配。 <li>日期 - 月份日期与ISO完全匹配。
 *  <li> day-of-year  -  Minguo day-of-year完全符合ISO。 <li>闰年 -  Minguo闰年模式与ISO完全匹配,因此这两个日历永远不会失步。
 * </ul>
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class MinguoChronology extends AbstractChronology implements Serializable {

    /**
     * Singleton instance for the Minguo chronology.
     * <p>
     *  单语实例为Minguo年表。
     * 
     */
    public static final MinguoChronology INSTANCE = new MinguoChronology();

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 1039765215346859963L;
    /**
     * The difference in years between ISO and Minguo.
     * <p>
     *  ISO和Minguo之间的年份差异。
     * 
     */
    static final int YEARS_DIFFERENCE = 1911;

    /**
     * Restricted constructor.
     * <p>
     *  受限制的构造函数。
     * 
     */
    private MinguoChronology() {
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the ID of the chronology - 'Minguo'.
     * <p>
     * The ID uniquely identifies the {@code Chronology}.
     * It can be used to lookup the {@code Chronology} using {@link #of(String)}.
     *
     * <p>
     *  获取年表的ID  - "Minguo"。
     * <p>
     *  ID唯一标识{@code Chronology}。它可以用于使用{@link #of(String)}查找{@code Chronology}。
     * 
     * 
     * @return the chronology ID - 'Minguo'
     * @see #getCalendarType()
     */
    @Override
    public String getId() {
        return "Minguo";
    }

    /**
     * Gets the calendar type of the underlying calendar system - 'roc'.
     * <p>
     * The calendar type is an identifier defined by the
     * <em>Unicode Locale Data Markup Language (LDML)</em> specification.
     * It can be used to lookup the {@code Chronology} using {@link #of(String)}.
     * It can also be used as part of a locale, accessible via
     * {@link Locale#getUnicodeLocaleType(String)} with the key 'ca'.
     *
     * <p>
     *  获取基础日历系统的日历类型 - 'roc'。
     * <p>
     * 日历类型是由<em> Unicode区域设置数据标记语言(LDML)</em>规范定义的标识符。它可以用于使用{@link #of(String)}查找{@code Chronology}。
     * 它也可以作为区域设置的一部分,通过{@link Locale#getUnicodeLocaleType(String)}访问,使用键'ca'。
     * 
     * 
     * @return the calendar system type - 'roc'
     * @see #getId()
     */
    @Override
    public String getCalendarType() {
        return "roc";
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains a local date in Minguo calendar system from the
     * era, year-of-era, month-of-year and day-of-month fields.
     *
     * <p>
     *  从时代,年份,年份和月份字段获取Minguo日历系统中的本地日期。
     * 
     * 
     * @param era  the Minguo era, not null
     * @param yearOfEra  the year-of-era
     * @param month  the month-of-year
     * @param dayOfMonth  the day-of-month
     * @return the Minguo local date, not null
     * @throws DateTimeException if unable to create the date
     * @throws ClassCastException if the {@code era} is not a {@code MinguoEra}
     */
    @Override
    public MinguoDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
        return date(prolepticYear(era, yearOfEra), month, dayOfMonth);
    }

    /**
     * Obtains a local date in Minguo calendar system from the
     * proleptic-year, month-of-year and day-of-month fields.
     *
     * <p>
     *  从Manguo日历系统中从原始年,月和月日字段中获取本地日期。
     * 
     * 
     * @param prolepticYear  the proleptic-year
     * @param month  the month-of-year
     * @param dayOfMonth  the day-of-month
     * @return the Minguo local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override
    public MinguoDate date(int prolepticYear, int month, int dayOfMonth) {
        return new MinguoDate(LocalDate.of(prolepticYear + YEARS_DIFFERENCE, month, dayOfMonth));
    }

    /**
     * Obtains a local date in Minguo calendar system from the
     * era, year-of-era and day-of-year fields.
     *
     * <p>
     *  从时代,年代和年年字段获取Minguo日历系统中的本地日期。
     * 
     * 
     * @param era  the Minguo era, not null
     * @param yearOfEra  the year-of-era
     * @param dayOfYear  the day-of-year
     * @return the Minguo local date, not null
     * @throws DateTimeException if unable to create the date
     * @throws ClassCastException if the {@code era} is not a {@code MinguoEra}
     */
    @Override
    public MinguoDate dateYearDay(Era era, int yearOfEra, int dayOfYear) {
        return dateYearDay(prolepticYear(era, yearOfEra), dayOfYear);
    }

    /**
     * Obtains a local date in Minguo calendar system from the
     * proleptic-year and day-of-year fields.
     *
     * <p>
     *  从引渡年和年年字段获取Minguo日历系统中的本地日期。
     * 
     * 
     * @param prolepticYear  the proleptic-year
     * @param dayOfYear  the day-of-year
     * @return the Minguo local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override
    public MinguoDate dateYearDay(int prolepticYear, int dayOfYear) {
        return new MinguoDate(LocalDate.ofYearDay(prolepticYear + YEARS_DIFFERENCE, dayOfYear));
    }

    /**
     * Obtains a local date in the Minguo calendar system from the epoch-day.
     *
     * <p>
     *  从纪元日在Minguo日历系统中获取本地日期。
     * 
     * 
     * @param epochDay  the epoch day
     * @return the Minguo local date, not null
     * @throws DateTimeException if unable to create the date
     */
    @Override  // override with covariant return type
    public MinguoDate dateEpochDay(long epochDay) {
        return new MinguoDate(LocalDate.ofEpochDay(epochDay));
    }

    @Override
    public MinguoDate dateNow() {
        return dateNow(Clock.systemDefaultZone());
    }

    @Override
    public MinguoDate dateNow(ZoneId zone) {
        return dateNow(Clock.system(zone));
    }

    @Override
    public MinguoDate dateNow(Clock clock) {
        return date(LocalDate.now(clock));
    }

    @Override
    public MinguoDate date(TemporalAccessor temporal) {
        if (temporal instanceof MinguoDate) {
            return (MinguoDate) temporal;
        }
        return new MinguoDate(LocalDate.from(temporal));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChronoLocalDateTime<MinguoDate> localDateTime(TemporalAccessor temporal) {
        return (ChronoLocalDateTime<MinguoDate>)super.localDateTime(temporal);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChronoZonedDateTime<MinguoDate> zonedDateTime(TemporalAccessor temporal) {
        return (ChronoZonedDateTime<MinguoDate>)super.zonedDateTime(temporal);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChronoZonedDateTime<MinguoDate> zonedDateTime(Instant instant, ZoneId zone) {
        return (ChronoZonedDateTime<MinguoDate>)super.zonedDateTime(instant, zone);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified year is a leap year.
     * <p>
     * Minguo leap years occur exactly in line with ISO leap years.
     * This method does not validate the year passed in, and only has a
     * well-defined result for years in the supported range.
     *
     * <p>
     *  检查指定的年份是否为闰年。
     * <p>
     *  Minguo闰年的发生完全符合ISO闰年。此方法不会验证传入的年份,并且在支持范围内的年份中只有明确定义的结果。
     * 
     * 
     * @param prolepticYear  the proleptic-year to check, not validated for range
     * @return true if the year is a leap year
     */
    @Override
    public boolean isLeapYear(long prolepticYear) {
        return IsoChronology.INSTANCE.isLeapYear(prolepticYear + YEARS_DIFFERENCE);
    }

    @Override
    public int prolepticYear(Era era, int yearOfEra) {
        if (era instanceof MinguoEra == false) {
            throw new ClassCastException("Era must be MinguoEra");
        }
        return (era == MinguoEra.ROC ? yearOfEra : 1 - yearOfEra);
    }

    @Override
    public MinguoEra eraOf(int eraValue) {
        return MinguoEra.of(eraValue);
    }

    @Override
    public List<Era> eras() {
        return Arrays.<Era>asList(MinguoEra.values());
    }

    //-----------------------------------------------------------------------
    @Override
    public ValueRange range(ChronoField field) {
        switch (field) {
            case PROLEPTIC_MONTH: {
                ValueRange range = PROLEPTIC_MONTH.range();
                return ValueRange.of(range.getMinimum() - YEARS_DIFFERENCE * 12L, range.getMaximum() - YEARS_DIFFERENCE * 12L);
            }
            case YEAR_OF_ERA: {
                ValueRange range = YEAR.range();
                return ValueRange.of(1, range.getMaximum() - YEARS_DIFFERENCE, -range.getMinimum() + 1 + YEARS_DIFFERENCE);
            }
            case YEAR: {
                ValueRange range = YEAR.range();
                return ValueRange.of(range.getMinimum() - YEARS_DIFFERENCE, range.getMaximum() - YEARS_DIFFERENCE);
            }
        }
        return field.range();
    }

    //-----------------------------------------------------------------------
    @Override  // override for return type
    public MinguoDate resolveDate(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        return (MinguoDate) super.resolveDate(fieldValues, resolverStyle);
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the Chronology using a
     * <a href="../../../serialized-form.html#java.time.chrono.Ser">dedicated serialized form</a>.
     * <p>
     *  使用<a href="../../../serialized-form.html#java.time.chrono.Ser">专用序列化表单</a>撰写年表。
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
     *  防御恶意流。
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
