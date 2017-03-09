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
package java.time.temporal;

import java.time.Duration;

/**
 * A standard set of date periods units.
 * <p>
 * This set of units provide unit-based access to manipulate a date, time or date-time.
 * The standard set of units can be extended by implementing {@link TemporalUnit}.
 * <p>
 * These units are intended to be applicable in multiple calendar systems.
 * For example, most non-ISO calendar systems define units of years, months and days,
 * just with slightly different rules.
 * The documentation of each unit explains how it operates.
 *
 * @implSpec
 * This is a final, immutable and thread-safe enum.
 *
 * <p>
 *  一组标准日期期间单位。
 * <p>
 *  该组单元提供基于单元的访问以操纵日期,时间或日期时间。可以通过实现{@link TemporalUnit}来扩展标准的单元集合。
 * <p>
 *  这些单元旨在适用于多个日历系统。例如,大多数非ISO日历系统定义年,月和日的单位,只是略有不同的规则。每个单元的文档说明如何操作。
 * 
 *  @implSpec这是一个最终的,不可变的和线程安全的枚举。
 * 
 * 
 * @since 1.8
 */
public enum ChronoUnit implements TemporalUnit {

    /**
     * Unit that represents the concept of a nanosecond, the smallest supported unit of time.
     * For the ISO calendar system, it is equal to the 1,000,000,000th part of the second unit.
     * <p>
     *  表示纳秒的概念的单位,是支持的最小时间单位。对于ISO日历系统,它等于第二个单位的1,000,000,000份。
     * 
     */
    NANOS("Nanos", Duration.ofNanos(1)),
    /**
     * Unit that represents the concept of a microsecond.
     * For the ISO calendar system, it is equal to the 1,000,000th part of the second unit.
     * <p>
     * 表示微秒的概念的单位。对于ISO日历系统,它等于第二个单位的第1,000,000个部分。
     * 
     */
    MICROS("Micros", Duration.ofNanos(1000)),
    /**
     * Unit that represents the concept of a millisecond.
     * For the ISO calendar system, it is equal to the 1000th part of the second unit.
     * <p>
     *  表示毫秒概念的单位。对于ISO日历系统,它等于第二个单元的第1000个部分。
     * 
     */
    MILLIS("Millis", Duration.ofNanos(1000_000)),
    /**
     * Unit that represents the concept of a second.
     * For the ISO calendar system, it is equal to the second in the SI system
     * of units, except around a leap-second.
     * <p>
     *  代表第二个概念的单位。对于ISO日历系统,它等于SI系统中的第二个单位,除了大约闰秒。
     * 
     */
    SECONDS("Seconds", Duration.ofSeconds(1)),
    /**
     * Unit that represents the concept of a minute.
     * For the ISO calendar system, it is equal to 60 seconds.
     * <p>
     *  表示一分钟的概念的单位。对于ISO日历系统,它等于60秒。
     * 
     */
    MINUTES("Minutes", Duration.ofSeconds(60)),
    /**
     * Unit that represents the concept of an hour.
     * For the ISO calendar system, it is equal to 60 minutes.
     * <p>
     *  表示一小时的概念的单位。对于ISO日历系统,它等于60分钟。
     * 
     */
    HOURS("Hours", Duration.ofSeconds(3600)),
    /**
     * Unit that represents the concept of half a day, as used in AM/PM.
     * For the ISO calendar system, it is equal to 12 hours.
     * <p>
     *  表示AM / PM中使用的半天的概念的单位。对于ISO日历系统,它等于12小时。
     * 
     */
    HALF_DAYS("HalfDays", Duration.ofSeconds(43200)),
    /**
     * Unit that represents the concept of a day.
     * For the ISO calendar system, it is the standard day from midnight to midnight.
     * The estimated duration of a day is {@code 24 Hours}.
     * <p>
     * When used with other calendar systems it must correspond to the day defined by
     * the rising and setting of the Sun on Earth. It is not required that days begin
     * at midnight - when converting between calendar systems, the date should be
     * equivalent at midday.
     * <p>
     *  表示一天的概念的单位。对于ISO日历系统,它是从午夜到午夜的标准日。一天的估计持续时间为{@code 24小时}。
     * <p>
     *  当与其他日历系统一起使用时,它必须对应于由地球上的太阳的上升和设置定义的日子。不要求日期从午夜开始 - 在日历系统之间转换时,日期应等于中午。
     * 
     */
    DAYS("Days", Duration.ofSeconds(86400)),
    /**
     * Unit that represents the concept of a week.
     * For the ISO calendar system, it is equal to 7 days.
     * <p>
     * When used with other calendar systems it must correspond to an integral number of days.
     * <p>
     *  表示一周概念的单位。对于ISO日历系统,它等于7天。
     * <p>
     *  当与其他日历系统一起使用时,它必须对应于整数天数。
     * 
     */
    WEEKS("Weeks", Duration.ofSeconds(7 * 86400L)),
    /**
     * Unit that represents the concept of a month.
     * For the ISO calendar system, the length of the month varies by month-of-year.
     * The estimated duration of a month is one twelfth of {@code 365.2425 Days}.
     * <p>
     * When used with other calendar systems it must correspond to an integral number of days.
     * <p>
     * 表示一个月的概念的单位。对于ISO日历系统,月的长度因年份而异。一个月的估计持续时间是{@code 365.2425 Days}的十二分之一。
     * <p>
     *  当与其他日历系统一起使用时,它必须对应于整数天数。
     * 
     */
    MONTHS("Months", Duration.ofSeconds(31556952L / 12)),
    /**
     * Unit that represents the concept of a year.
     * For the ISO calendar system, it is equal to 12 months.
     * The estimated duration of a year is {@code 365.2425 Days}.
     * <p>
     * When used with other calendar systems it must correspond to an integral number of days
     * or months roughly equal to a year defined by the passage of the Earth around the Sun.
     * <p>
     *  表示一年概念的单位。对于ISO日历系统,它等于12个月。一年的估计持续时间为{@code 365.2425 Days}。
     * <p>
     *  当与其他日历系统一起使用时,它必须对应于整数天或月,大致等于地球绕太阳所定义的一年。
     * 
     */
    YEARS("Years", Duration.ofSeconds(31556952L)),
    /**
     * Unit that represents the concept of a decade.
     * For the ISO calendar system, it is equal to 10 years.
     * <p>
     * When used with other calendar systems it must correspond to an integral number of days
     * and is normally an integral number of years.
     * <p>
     *  代表十年概念的单位。对于ISO日历系统,它等于10年。
     * <p>
     *  当与其他日历系统一起使用时,它必须对应于整数天,通常为整数年。
     * 
     */
    DECADES("Decades", Duration.ofSeconds(31556952L * 10L)),
    /**
     * Unit that represents the concept of a century.
     * For the ISO calendar system, it is equal to 100 years.
     * <p>
     * When used with other calendar systems it must correspond to an integral number of days
     * and is normally an integral number of years.
     * <p>
     *  代表一个世纪的概念的单位。对于ISO日历系统,它等于100年。
     * <p>
     *  当与其他日历系统一起使用时,它必须对应于整数天,通常为整数年。
     * 
     */
    CENTURIES("Centuries", Duration.ofSeconds(31556952L * 100L)),
    /**
     * Unit that represents the concept of a millennium.
     * For the ISO calendar system, it is equal to 1000 years.
     * <p>
     * When used with other calendar systems it must correspond to an integral number of days
     * and is normally an integral number of years.
     * <p>
     *  代表千年概念的单位。对于ISO日历系统,它等于1000年。
     * <p>
     *  当与其他日历系统一起使用时,它必须对应于整数天,通常为整数年。
     * 
     */
    MILLENNIA("Millennia", Duration.ofSeconds(31556952L * 1000L)),
    /**
     * Unit that represents the concept of an era.
     * The ISO calendar system doesn't have eras thus it is impossible to add
     * an era to a date or date-time.
     * The estimated duration of the era is artificially defined as {@code 1,000,000,000 Years}.
     * <p>
     * When used with other calendar systems there are no restrictions on the unit.
     * <p>
     * 代表一个时代的概念的单位。 ISO日历系统没有时代,因此不可能向日期或日期时间添加时代。该时代的估计持续时间被人为地定义为{@code 1,000,000,000年}。
     * <p>
     *  当与其他日历系统一起使用时,对本机没有限制。
     * 
     */
    ERAS("Eras", Duration.ofSeconds(31556952L * 1000_000_000L)),
    /**
     * Artificial unit that represents the concept of forever.
     * This is primarily used with {@link TemporalField} to represent unbounded fields
     * such as the year or era.
     * The estimated duration of the era is artificially defined as the largest duration
     * supported by {@code Duration}.
     * <p>
     *  代表永远概念的人工单位。这主要用于{@link TemporalField}表示无界字段,例如年份或年代。该时代的估计持续时间被人为地定义为由{@code Duration}支持的最大持续时间。
     * 
     */
    FOREVER("Forever", Duration.ofSeconds(Long.MAX_VALUE, 999_999_999));

    private final String name;
    private final Duration duration;

    private ChronoUnit(String name, Duration estimatedDuration) {
        this.name = name;
        this.duration = estimatedDuration;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the estimated duration of this unit in the ISO calendar system.
     * <p>
     * All of the units in this class have an estimated duration.
     * Days vary due to daylight saving time, while months have different lengths.
     *
     * <p>
     *  获取ISO日历系统中此单元的估计持续时间。
     * <p>
     *  此类别中的所有单位都有估计的持续时间。天数因夏令时而异,而月份有不同的长度。
     * 
     * 
     * @return the estimated duration of this unit, not null
     */
    @Override
    public Duration getDuration() {
        return duration;
    }

    /**
     * Checks if the duration of the unit is an estimate.
     * <p>
     * All time units in this class are considered to be accurate, while all date
     * units in this class are considered to be estimated.
     * <p>
     * This definition ignores leap seconds, but considers that Days vary due to
     * daylight saving time and months have different lengths.
     *
     * <p>
     *  检查单位的持续时间是否为估计。
     * <p>
     *  该类中的所有时间单位都被认为是准确的,而该类中的所有日期单位都被认为是估计的。
     * <p>
     *  此定义忽略闰秒,但认为天数因夏令时而异,月份有不同的长度。
     * 
     * 
     * @return true if the duration is estimated, false if accurate
     */
    @Override
    public boolean isDurationEstimated() {
        return this.compareTo(DAYS) >= 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this unit is a date unit.
     * <p>
     * All units from days to eras inclusive are date-based.
     * Time-based units and {@code FOREVER} return false.
     *
     * <p>
     *  检查本机是否为日期单位。
     * <p>
     *  所有单位从天到日期包括日期。基于时间的单位和{@code FOREVER}返回false。
     * 
     * 
     * @return true if a date unit, false if a time unit
     */
    @Override
    public boolean isDateBased() {
        return this.compareTo(DAYS) >= 0 && this != FOREVER;
    }

    /**
     * Checks if this unit is a time unit.
     * <p>
     * All units from nanos to half-days inclusive are time-based.
     * Date-based units and {@code FOREVER} return false.
     *
     * <p>
     *  检查本机是否是时间单位。
     * <p>
     * 
     * @return true if a time unit, false if a date unit
     */
    @Override
    public boolean isTimeBased() {
        return this.compareTo(DAYS) < 0;
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean isSupportedBy(Temporal temporal) {
        return temporal.isSupported(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Temporal> R addTo(R temporal, long amount) {
        return (R) temporal.plus(amount, this);
    }

    //-----------------------------------------------------------------------
    @Override
    public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
        return temporal1Inclusive.until(temporal2Exclusive, this);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        return name;
    }

}
