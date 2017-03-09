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

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Objects;

/**
 * A date expressed in terms of a standard year-month-day calendar system.
 * <p>
 * This class is used by applications seeking to handle dates in non-ISO calendar systems.
 * For example, the Japanese, Minguo, Thai Buddhist and others.
 * <p>
 * {@code ChronoLocalDate} is built on the generic concepts of year, month and day.
 * The calendar system, represented by a {@link java.time.chrono.Chronology}, expresses the relationship between
 * the fields and this class allows the resulting date to be manipulated.
 * <p>
 * Note that not all calendar systems are suitable for use with this class.
 * For example, the Mayan calendar uses a system that bears no relation to years, months and days.
 * <p>
 * The API design encourages the use of {@code LocalDate} for the majority of the application.
 * This includes code to read and write from a persistent data store, such as a database,
 * and to send dates and times across a network. The {@code ChronoLocalDate} instance is then used
 * at the user interface level to deal with localized input/output.
 *
 * <P>Example: </p>
 * <pre>
 *        System.out.printf("Example()%n");
 *        // Enumerate the list of available calendars and print today for each
 *        Set&lt;Chronology&gt; chronos = Chronology.getAvailableChronologies();
 *        for (Chronology chrono : chronos) {
 *            ChronoLocalDate date = chrono.dateNow();
 *            System.out.printf("   %20s: %s%n", chrono.getID(), date.toString());
 *        }
 *
 *        // Print the Hijrah date and calendar
 *        ChronoLocalDate date = Chronology.of("Hijrah").dateNow();
 *        int day = date.get(ChronoField.DAY_OF_MONTH);
 *        int dow = date.get(ChronoField.DAY_OF_WEEK);
 *        int month = date.get(ChronoField.MONTH_OF_YEAR);
 *        int year = date.get(ChronoField.YEAR);
 *        System.out.printf("  Today is %s %s %d-%s-%d%n", date.getChronology().getID(),
 *                dow, day, month, year);

 *        // Print today's date and the last day of the year
 *        ChronoLocalDate now1 = Chronology.of("Hijrah").dateNow();
 *        ChronoLocalDate first = now1.with(ChronoField.DAY_OF_MONTH, 1)
 *                .with(ChronoField.MONTH_OF_YEAR, 1);
 *        ChronoLocalDate last = first.plus(1, ChronoUnit.YEARS)
 *                .minus(1, ChronoUnit.DAYS);
 *        System.out.printf("  Today is %s: start: %s; end: %s%n", last.getChronology().getID(),
 *                first, last);
 * </pre>
 *
 * <h3>Adding Calendars</h3>
 * <p> The set of calendars is extensible by defining a subclass of {@link ChronoLocalDate}
 * to represent a date instance and an implementation of {@code Chronology}
 * to be the factory for the ChronoLocalDate subclass.
 * </p>
 * <p> To permit the discovery of the additional calendar types the implementation of
 * {@code Chronology} must be registered as a Service implementing the {@code Chronology} interface
 * in the {@code META-INF/Services} file as per the specification of {@link java.util.ServiceLoader}.
 * The subclass must function according to the {@code Chronology} class description and must provide its
 * {@link java.time.chrono.Chronology#getId() chronlogy ID} and {@link Chronology#getCalendarType() calendar type}. </p>
 *
 * @implSpec
 * This abstract class must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * Subclasses should be Serializable wherever possible.
 *
 * <p>
 *  以标准年 - 月 - 日日历系统表示的日期。
 * <p>
 *  此类由寻求处理非ISO日历系统中的日期的应用程序使用。例如,日本,民族,泰国佛教和其他。
 * <p>
 *  {@code ChronoLocalDate}建立在年,月和日的通用概念上。
 * 日历系统由{@link java.time.chrono.Chronology}表示,表示字段之间的关系,并且此类允许处理生成的日期。
 * <p>
 *  请注意,并非所有日历系统都适合与此类别一起使用。例如,玛雅日历使用与年,月和日无关的系统。
 * <p>
 * API设计鼓励对大多数应用程序使用{@code LocalDate}。这包括从持久性数据存储(如数据库)读取和写入的代码,并通过网络发送日期和时间。
 * 然后在用户界面级别使用{@code ChronoLocalDate}实例来处理本地化的输入/输出。
 * 
 *  <P>示例：</p>
 * <pre>
 *  System.out.printf("Example()％n"); //枚举可用日历的列表,并为每个Set&lt; Chronology&gt; chronos = Chronology.getAva
 * ilableChronologies(); for(Chronology chrono：chronos){ChronoLocalDate date = chrono.dateNow(); System.out.printf("％20s：％s％n",chrono.getID(),date.toString()) }
 * }。
 * 
 *  //打印Hijrah日期和日历ChronoLocalDate date = Chronology.of("Hijrah")。
 * dateNow(); int day = date.get(ChronoField.DAY_OF_MONTH); int dow = date.get(ChronoField.DAY_OF_WEEK);
 *  int month = date.get(ChronoField.MONTH_OF_YEAR); int year = date.get(ChronoField.YEAR); System.out.p
 * rintf("Today is％s％s％d-％s-％d％n",date.getChronology()。
 *  //打印Hijrah日期和日历ChronoLocalDate date = Chronology.of("Hijrah")。getID(),dow,day,month,year);。
 * 
 *  //打印今天的日期和一年中的最后一天ChronoLocalDate now1 = Chronology.of("Hijrah")。
 * dateNow(); ChronoLocalDate first = now1.with(ChronoField.DAY_OF_MONTH,1).with(ChronoField.MONTH_OF_YE
 * AR,1); ChronoLocalDate last = first.plus(1,ChronoUnit.YEARS).minus(1,ChronoUnit.DAYS); System.out.pri
 * ntf("Today is％s：start：％s; end：％s％n",last.getChronology()。
 *  //打印今天的日期和一年中的最后一天ChronoLocalDate now1 = Chronology.of("Hijrah")。getID(),first,last);。
 * </pre>
 * 
 * <h3>添加日历</h3> <p>通过定义{@link ChronoLocalDate}的子类来表示日期实例,并将{@code Chronology}的实现作为ChronoLocalDate子类的工厂,
 * 可以扩展日历集。
 * </p>
 *  <p>为了允许发现其他日历类型,{@code Chronology}的实现必须注册为实现{@code META-INF / Services}文件中的{@code Chronology}界面的服务的{@link java.util.ServiceLoader}
 * 。
 * 子类必须根据{@code Chronology}类描述运行,并且必须提供其{@link java.time.chrono.Chronology#getId()计时ID}和{@link Chronology#getCalendarType()日历类型}
 * 。
 *  </p>。
 * 
 *  @implSpec这个抽象类必须小心地实现,以确保其他类操作正确。所有可以实例化的实现必须是final,immutable和线程安全的。子类应尽可能序列化。
 * 
 * 
 * @param <D> the ChronoLocalDate of this date-time
 * @since 1.8
 */
abstract class ChronoLocalDateImpl<D extends ChronoLocalDate>
        implements ChronoLocalDate, Temporal, TemporalAdjuster, Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 6282433883239719096L;

    /**
     * Casts the {@code Temporal} to {@code ChronoLocalDate} ensuring it bas the specified chronology.
     *
     * <p>
     *  将{@code Temporal}转换为{@code ChronoLocalDate},确保它基于指定的年表。
     * 
     * 
     * @param chrono  the chronology to check for, not null
     * @param temporal  a date-time to cast, not null
     * @return the date-time checked and cast to {@code ChronoLocalDate}, not null
     * @throws ClassCastException if the date-time cannot be cast to ChronoLocalDate
     *  or the chronology is not equal this Chronology
     */
    static <D extends ChronoLocalDate> D ensureValid(Chronology chrono, Temporal temporal) {
        @SuppressWarnings("unchecked")
        D other = (D) temporal;
        if (chrono.equals(other.getChronology()) == false) {
            throw new ClassCastException("Chronology mismatch, expected: " + chrono.getId() + ", actual: " + other.getChronology().getId());
        }
        return other;
    }

    //-----------------------------------------------------------------------
    /**
     * Creates an instance.
     * <p>
     *  创建实例。
     * 
     */
    ChronoLocalDateImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public D with(TemporalAdjuster adjuster) {
        return (D) ChronoLocalDate.super.with(adjuster);
    }

    @Override
    @SuppressWarnings("unchecked")
    public D with(TemporalField field, long value) {
        return (D) ChronoLocalDate.super.with(field, value);
    }

    //-----------------------------------------------------------------------
    @Override
    @SuppressWarnings("unchecked")
    public D plus(TemporalAmount amount) {
        return (D) ChronoLocalDate.super.plus(amount);
    }

    //-----------------------------------------------------------------------
    @Override
    @SuppressWarnings("unchecked")
    public D plus(long amountToAdd, TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            ChronoUnit f = (ChronoUnit) unit;
            switch (f) {
                case DAYS: return plusDays(amountToAdd);
                case WEEKS: return plusDays(Math.multiplyExact(amountToAdd, 7));
                case MONTHS: return plusMonths(amountToAdd);
                case YEARS: return plusYears(amountToAdd);
                case DECADES: return plusYears(Math.multiplyExact(amountToAdd, 10));
                case CENTURIES: return plusYears(Math.multiplyExact(amountToAdd, 100));
                case MILLENNIA: return plusYears(Math.multiplyExact(amountToAdd, 1000));
                case ERAS: return with(ERA, Math.addExact(getLong(ERA), amountToAdd));
            }
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
        return (D) ChronoLocalDate.super.plus(amountToAdd, unit);
    }

    @Override
    @SuppressWarnings("unchecked")
    public D minus(TemporalAmount amount) {
        return (D) ChronoLocalDate.super.minus(amount);
    }

    @Override
    @SuppressWarnings("unchecked")
    public D minus(long amountToSubtract, TemporalUnit unit) {
        return (D) ChronoLocalDate.super.minus(amountToSubtract, unit);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date with the specified period in years added.
     * <p>
     * This adds the specified period in years to the date.
     * In some cases, adding years can cause the resulting date to become invalid.
     * If this occurs, then other fields, typically the day-of-month, will be adjusted to ensure
     * that the result is valid. Typically this will select the last valid day of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的副本,其中包含指定的周期(以增加的年数)。
     * <p>
     * 这会将以年为单位的指定期间添加到日期。在某些情况下,添加年份可能会导致生成的日期无效。如果发生这种情况,则将调整其他字段(通常为月份日期)以确保结果有效。通常,这将选择月份的最后一个有效日期。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param yearsToAdd  the years to add, may be negative
     * @return a date based on this one with the years added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    abstract D plusYears(long yearsToAdd);

    /**
     * Returns a copy of this date with the specified period in months added.
     * <p>
     * This adds the specified period in months to the date.
     * In some cases, adding months can cause the resulting date to become invalid.
     * If this occurs, then other fields, typically the day-of-month, will be adjusted to ensure
     * that the result is valid. Typically this will select the last valid day of the month.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的副本,其中包含指定的期间(以月为单位)。
     * <p>
     *  这会将指定的期间以月为单位添加到日期。在某些情况下,添加月份可能会导致生成的日期无效。如果发生这种情况,则将调整其他字段(通常为月份日期)以确保结果有效。通常,这将选择月份的最后一个有效日期。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param monthsToAdd  the months to add, may be negative
     * @return a date based on this one with the months added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    abstract D plusMonths(long monthsToAdd);

    /**
     * Returns a copy of this date with the specified period in weeks added.
     * <p>
     * This adds the specified period in weeks to the date.
     * In some cases, adding weeks can cause the resulting date to become invalid.
     * If this occurs, then other fields will be adjusted to ensure that the result is valid.
     * <p>
     * The default implementation uses {@link #plusDays(long)} using a 7 day week.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的副本,指定的周期(以添加的星期为单位)。
     * <p>
     *  这会将指定的周期(以周为单位)添加到日期。在某些情况下,添加周可能导致生成的日期无效。如果发生这种情况,则将调整其他字段以确保结果有效。
     * <p>
     *  默认实现使用{@link #plusDays(long)}每周7天。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param weeksToAdd  the weeks to add, may be negative
     * @return a date based on this one with the weeks added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    D plusWeeks(long weeksToAdd) {
        return plusDays(Math.multiplyExact(weeksToAdd, 7));
    }

    /**
     * Returns a copy of this date with the specified number of days added.
     * <p>
     * This adds the specified period in days to the date.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的添加指定天数的副本。
     * <p>
     *  这会将指定的期间(以天为单位)添加到日期。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param daysToAdd  the days to add, may be negative
     * @return a date based on this one with the days added, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    abstract D plusDays(long daysToAdd);

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date with the specified period in years subtracted.
     * <p>
     * This subtracts the specified period in years to the date.
     * In some cases, subtracting years can cause the resulting date to become invalid.
     * If this occurs, then other fields, typically the day-of-month, will be adjusted to ensure
     * that the result is valid. Typically this will select the last valid day of the month.
     * <p>
     * The default implementation uses {@link #plusYears(long)}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此日期的副本,指定的期间(减去年份)。
     * <p>
     *  这将指定的年份减去该日期。在某些情况下,减去年份可能会导致生成的日期无效。如果发生这种情况,则将调整其他字段(通常为月份日期)以确保结果有效。通常,这将选择月份的最后一个有效日期。
     * <p>
     *  默认实现使用{@link #plusYears(long)}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param yearsToSubtract  the years to subtract, may be negative
     * @return a date based on this one with the years subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    @SuppressWarnings("unchecked")
    D minusYears(long yearsToSubtract) {
        return (yearsToSubtract == Long.MIN_VALUE ? ((ChronoLocalDateImpl<D>)plusYears(Long.MAX_VALUE)).plusYears(1) : plusYears(-yearsToSubtract));
    }

    /**
     * Returns a copy of this date with the specified period in months subtracted.
     * <p>
     * This subtracts the specified period in months to the date.
     * In some cases, subtracting months can cause the resulting date to become invalid.
     * If this occurs, then other fields, typically the day-of-month, will be adjusted to ensure
     * that the result is valid. Typically this will select the last valid day of the month.
     * <p>
     * The default implementation uses {@link #plusMonths(long)}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的副本,以指定的周期减去月份。
     * <p>
     *  这将从指定的月份减去该日期。在某些情况下,减去月份可能会导致生成的日期无效。如果发生这种情况,则将调整其他字段(通常为月份日期)以确保结果有效。通常,这将选择月份的最后一个有效日期。
     * <p>
     *  默认实现使用{@link #plusMonths(long)}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param monthsToSubtract  the months to subtract, may be negative
     * @return a date based on this one with the months subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    @SuppressWarnings("unchecked")
    D minusMonths(long monthsToSubtract) {
        return (monthsToSubtract == Long.MIN_VALUE ? ((ChronoLocalDateImpl<D>)plusMonths(Long.MAX_VALUE)).plusMonths(1) : plusMonths(-monthsToSubtract));
    }

    /**
     * Returns a copy of this date with the specified period in weeks subtracted.
     * <p>
     * This subtracts the specified period in weeks to the date.
     * In some cases, subtracting weeks can cause the resulting date to become invalid.
     * If this occurs, then other fields will be adjusted to ensure that the result is valid.
     * <p>
     * The default implementation uses {@link #plusWeeks(long)}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期的副本,指定的周期(以星期扣除)。
     * <p>
     *  这将减去指定的周期(以周为单位)。在某些情况下,减去星期可能导致生成的日期无效。如果发生这种情况,则将调整其他字段以确保结果有效。
     * <p>
     *  默认实现使用{@link #plusWeeks(long)}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param weeksToSubtract  the weeks to subtract, may be negative
     * @return a date based on this one with the weeks subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    @SuppressWarnings("unchecked")
    D minusWeeks(long weeksToSubtract) {
        return (weeksToSubtract == Long.MIN_VALUE ? ((ChronoLocalDateImpl<D>)plusWeeks(Long.MAX_VALUE)).plusWeeks(1) : plusWeeks(-weeksToSubtract));
    }

    /**
     * Returns a copy of this date with the specified number of days subtracted.
     * <p>
     * This subtracts the specified period in days to the date.
     * <p>
     * The default implementation uses {@link #plusDays(long)}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 返回此日期的指定减去天数的副本。
     * <p>
     *  这将从指定日期减去指定的期间。
     * <p>
     * 
     * @param daysToSubtract  the days to subtract, may be negative
     * @return a date based on this one with the days subtracted, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    @SuppressWarnings("unchecked")
    D minusDays(long daysToSubtract) {
        return (daysToSubtract == Long.MIN_VALUE ? ((ChronoLocalDateImpl<D>)plusDays(Long.MAX_VALUE)).plusDays(1) : plusDays(-daysToSubtract));
    }

    //-----------------------------------------------------------------------
    @Override
    public long until(Temporal endExclusive, TemporalUnit unit) {
        Objects.requireNonNull(endExclusive, "endExclusive");
        ChronoLocalDate end = getChronology().date(endExclusive);
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
        Objects.requireNonNull(unit, "unit");
        return unit.between(this, end);
    }

    private long daysUntil(ChronoLocalDate end) {
        return end.toEpochDay() - toEpochDay();  // no overflow
    }

    private long monthsUntil(ChronoLocalDate end) {
        ValueRange range = getChronology().range(MONTH_OF_YEAR);
        if (range.getMaximum() != 12) {
            throw new IllegalStateException("ChronoLocalDateImpl only supports Chronologies with 12 months per year");
        }
        long packed1 = getLong(PROLEPTIC_MONTH) * 32L + get(DAY_OF_MONTH);  // no overflow
        long packed2 = end.getLong(PROLEPTIC_MONTH) * 32L + end.get(DAY_OF_MONTH);  // no overflow
        return (packed2 - packed1) / 32;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChronoLocalDate) {
            return compareTo((ChronoLocalDate) obj) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        long epDay = toEpochDay();
        return getChronology().hashCode() ^ ((int) (epDay ^ (epDay >>> 32)));
    }

    @Override
    public String toString() {
        // getLong() reduces chances of exceptions in toString()
        long yoe = getLong(YEAR_OF_ERA);
        long moy = getLong(MONTH_OF_YEAR);
        long dom = getLong(DAY_OF_MONTH);
        StringBuilder buf = new StringBuilder(30);
        buf.append(getChronology().toString())
                .append(" ")
                .append(getEra())
                .append(" ")
                .append(yoe)
                .append(moy < 10 ? "-0" : "-").append(moy)
                .append(dom < 10 ? "-0" : "-").append(dom);
        return buf.toString();
    }

}
