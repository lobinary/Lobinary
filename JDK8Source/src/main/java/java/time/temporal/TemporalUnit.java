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

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;

/**
 * A unit of date-time, such as Days or Hours.
 * <p>
 * Measurement of time is built on units, such as years, months, days, hours, minutes and seconds.
 * Implementations of this interface represent those units.
 * <p>
 * An instance of this interface represents the unit itself, rather than an amount of the unit.
 * See {@link Period} for a class that represents an amount in terms of the common units.
 * <p>
 * The most commonly used units are defined in {@link ChronoUnit}.
 * Further units are supplied in {@link IsoFields}.
 * Units can also be written by application code by implementing this interface.
 * <p>
 * The unit works using double dispatch. Client code calls methods on a date-time like
 * {@code LocalDateTime} which check if the unit is a {@code ChronoUnit}.
 * If it is, then the date-time must handle it.
 * Otherwise, the method call is re-dispatched to the matching method in this interface.
 *
 * @implSpec
 * This interface must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * It is recommended to use an enum where possible.
 *
 * <p>
 *  日期时间单位,例如天或小时。
 * <p>
 *  时间的测量基于单位,例如年,月,日,小时,分钟和秒。此接口的实现代表这些单元。
 * <p>
 *  此接口的实例表示单元本身,而不是单元的量。请参阅{@link Period}了解以公共单位表示金额的类。
 * <p>
 *  最常用的单位在{@link ChronoUnit}中定义。更多单元在{@link IsoFields}中提供。也可以通过实现该接口通过应用程序代码来编写单位。
 * <p>
 * 该单位使用双调度。客户端代码在像{@code LocalDateTime}这样的日期时间调用方法,该方法检查单元是否为{@code ChronoUnit}。如果是,那么日期时间必须处理它。
 * 否则,将重新分派方法调用到此接口中的匹配方法。
 * 
 *  @implSpec必须小心地实现此接口,以确保其他类正常运行。所有可以实例化的实现必须是final,immutable和线程安全的。建议在可能的情况下使用枚举。
 * 
 * 
 * @since 1.8
 */
public interface TemporalUnit {

    /**
     * Gets the duration of this unit, which may be an estimate.
     * <p>
     * All units return a duration measured in standard nanoseconds from this method.
     * The duration will be positive and non-zero.
     * For example, an hour has a duration of {@code 60 * 60 * 1,000,000,000ns}.
     * <p>
     * Some units may return an accurate duration while others return an estimate.
     * For example, days have an estimated duration due to the possibility of
     * daylight saving time changes.
     * To determine if the duration is an estimate, use {@link #isDurationEstimated()}.
     *
     * <p>
     *  获取此单位的持续时间,可以是估计值。
     * <p>
     *  所有单位返回从此方法以标准纳秒测量的持续时间。持续时间将为正且非零。例如,一小时的持续时间为{@code 60 * 60 * 1,000,000,000ns}。
     * <p>
     *  某些单位可能返回准确的持续时间,而其他单位可能返回估计例如,由于夏令时变化的可能性,天具有估计的持续时间。
     * 要确定持续时间是否是估计值,请使用{@link #isDurationEstimated()}。
     * 
     * 
     * @return the duration of this unit, which may be an estimate, not null
     */
    Duration getDuration();

    /**
     * Checks if the duration of the unit is an estimate.
     * <p>
     * All units have a duration, however the duration is not always accurate.
     * For example, days have an estimated duration due to the possibility of
     * daylight saving time changes.
     * This method returns true if the duration is an estimate and false if it is
     * accurate. Note that accurate/estimated ignores leap seconds.
     *
     * <p>
     *  检查单位的持续时间是否为估计。
     * <p>
     *  所有单位都有持续时间,但持续时间并不总是准确。例如,由于夏令时变化的可能性,天具有估计的持续时间。如果持续时间是估计值,此方法返回true,如果准确,则此方法返回false。
     * 请注意,精确/估计忽略闰秒。
     * 
     * 
     * @return true if the duration is estimated, false if accurate
     */
    boolean isDurationEstimated();

    //-----------------------------------------------------------------------
    /**
     * Checks if this unit represents a component of a date.
     * <p>
     * A date is time-based if it can be used to imply meaning from a date.
     * It must have a {@linkplain #getDuration() duration} that is an integral
     * multiple of the length of a standard day.
     * Note that it is valid for both {@code isDateBased()} and {@code isTimeBased()}
     * to return false, such as when representing a unit like 36 hours.
     *
     * <p>
     *  检查此单元是否表示日期的组件。
     * <p>
     * 日期是基于时间的,如果它可以用于暗示从某个日期起的含义。它必须有{@linkplain #getDuration()duration},它是标准日的长度的整数倍。
     * 注意,它对于{@code isDateBased()}和{@code isTimeBased()}都返回false是有效的,例如当表示36小时的单位时。
     * 
     * 
     * @return true if this unit is a component of a date
     */
    boolean isDateBased();

    /**
     * Checks if this unit represents a component of a time.
     * <p>
     * A unit is time-based if it can be used to imply meaning from a time.
     * It must have a {@linkplain #getDuration() duration} that divides into
     * the length of a standard day without remainder.
     * Note that it is valid for both {@code isDateBased()} and {@code isTimeBased()}
     * to return false, such as when representing a unit like 36 hours.
     *
     * <p>
     *  检查此单元是否表示时间的组成部分。
     * <p>
     *  一个单位是基于时间的,如果它可以用来暗示一个时间的意义。它必须有一个{@linkplain #getDuration()duration},它将标准日的长度除以余数。
     * 注意,它对于{@code isDateBased()}和{@code isTimeBased()}都返回false是有效的,例如当表示36小时的单位时。
     * 
     * 
     * @return true if this unit is a component of a time
     */
    boolean isTimeBased();

    //-----------------------------------------------------------------------
    /**
     * Checks if this unit is supported by the specified temporal object.
     * <p>
     * This checks that the implementing date-time can add/subtract this unit.
     * This can be used to avoid throwing an exception.
     * <p>
     * This default implementation derives the value using
     * {@link Temporal#plus(long, TemporalUnit)}.
     *
     * <p>
     *  检查指定的临时对象是否支持此单元。
     * <p>
     *  这将检查实施日期时间是否可以添加/减去此单位。这可以用来避免抛出异常。
     * <p>
     *  此默认实现使用{@link Temporal#plus(long,TemporalUnit)}导出值。
     * 
     * 
     * @param temporal  the temporal object to check, not null
     * @return true if the unit is supported
     */
    default boolean isSupportedBy(Temporal temporal) {
        if (temporal instanceof LocalTime) {
            return isTimeBased();
        }
        if (temporal instanceof ChronoLocalDate) {
            return isDateBased();
        }
        if (temporal instanceof ChronoLocalDateTime || temporal instanceof ChronoZonedDateTime) {
            return true;
        }
        try {
            temporal.plus(1, this);
            return true;
        } catch (UnsupportedTemporalTypeException ex) {
            return false;
        } catch (RuntimeException ex) {
            try {
                temporal.plus(-1, this);
                return true;
            } catch (RuntimeException ex2) {
                return false;
            }
        }
    }

    /**
     * Returns a copy of the specified temporal object with the specified period added.
     * <p>
     * The period added is a multiple of this unit. For example, this method
     * could be used to add "3 days" to a date by calling this method on the
     * instance representing "days", passing the date and the period "3".
     * The period to be added may be negative, which is equivalent to subtraction.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link Temporal#plus(long, TemporalUnit)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisUnit.addTo(temporal);
     *   temporal = temporal.plus(thisUnit);
     * </pre>
     * It is recommended to use the second approach, {@code plus(TemporalUnit)},
     * as it is a lot clearer to read in code.
     * <p>
     * Implementations should perform any queries or calculations using the units
     * available in {@link ChronoUnit} or the fields available in {@link ChronoField}.
     * If the unit is not supported an {@code UnsupportedTemporalTypeException} must be thrown.
     * <p>
     * Implementations must not alter the specified temporal object.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable implementations.
     *
     * <p>
     *  返回添加了指定时间段的指定时态对象的副本。
     * <p>
     *  添加的周期是该单位的倍数。例如,此方法可用于通过在表示"天"的实例上调用此方法,通过日期和周期"3",将"3天"添加到日期。要添加的周期可以是负的,这等效于减法。
     * <p>
     * 有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link Temporal#plus(long,TemporalUnit)}：
     * <pre>
     *  //这两行是等价的,但第二种方法是建议temporal = thisUnit.addTo(temporal); temporal = temporal.plus(thisUnit);
     * </pre>
     *  建议使用第二种方法{@code plus(TemporalUnit)},因为它在代码中更清晰。
     * <p>
     *  实施应使用{@link ChronoUnit}中可用的单位或{@link ChronoField}中的字段执行任何查询或计算。
     * 如果不支持该单元,则必须抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  实现不得更改指定的时间对象。相反,必须返回原件的调整副本。这为不可变和可变实现提供了等效的,安全的行为。
     * 
     * 
     * @param <R>  the type of the Temporal object
     * @param temporal  the temporal object to adjust, not null
     * @param amount  the amount of this unit to add, positive or negative
     * @return the adjusted temporal object, not null
     * @throws DateTimeException if the period cannot be added
     * @throws UnsupportedTemporalTypeException if the unit is not supported by the temporal
     */
    <R extends Temporal> R addTo(R temporal, long amount);

    //-----------------------------------------------------------------------
    /**
     * Calculates the amount of time between two temporal objects.
     * <p>
     * This calculates the amount in terms of this unit. The start and end
     * points are supplied as temporal objects and must be of compatible types.
     * The implementation will convert the second type to be an instance of the
     * first type before the calculating the amount.
     * The result will be negative if the end is before the start.
     * For example, the amount in hours between two temporal objects can be
     * calculated using {@code HOURS.between(startTime, endTime)}.
     * <p>
     * The calculation returns a whole number, representing the number of
     * complete units between the two temporals.
     * For example, the amount in hours between the times 11:30 and 13:29
     * will only be one hour as it is one minute short of two hours.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link Temporal#until(Temporal, TemporalUnit)}:
     * <pre>
     *   // these two lines are equivalent
     *   between = thisUnit.between(start, end);
     *   between = start.until(end, thisUnit);
     * </pre>
     * The choice should be made based on which makes the code more readable.
     * <p>
     * For example, this method allows the number of days between two dates to
     * be calculated:
     * <pre>
     *  long daysBetween = DAYS.between(start, end);
     *  // or alternatively
     *  long daysBetween = start.until(end, DAYS);
     * </pre>
     * <p>
     * Implementations should perform any queries or calculations using the units
     * available in {@link ChronoUnit} or the fields available in {@link ChronoField}.
     * If the unit is not supported an {@code UnsupportedTemporalTypeException} must be thrown.
     * Implementations must not alter the specified temporal objects.
     *
     * @implSpec
     * Implementations must begin by checking to if the two temporals have the
     * same type using {@code getClass()}. If they do not, then the result must be
     * obtained by calling {@code temporal1Inclusive.until(temporal2Exclusive, this)}.
     *
     * <p>
     *  计算两个时间对象之间的时间量。
     * <p>
     *  这以单位计算金额。开始点和结束点作为时间对象提供,并且必须是兼容的类型。实现将在计算金额之前将第二类型转换为第一类型的实例。如果结束在开始之前,结果将为负。
     * 例如,可以使用{@code HOURS.between(startTime,endTime)}来计算两个时间对象之间的小时数量。
     * <p>
     * 计算返回一个整数,表示两个时间之间的完整单位数。例如,时间11:30和13:29之间的小时数将仅为一小时,因为它是两小时的一分钟。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link Temporal#until(Temporal,TemporalUnit)}：
     * <pre>
     *  //这两行是等价的= thisUnit.between(start,end); between = start.until(end,thisUnit);
     * </pre>
     *  应该基于哪个使得代码更可读的选择。
     * <p>
     *  例如,此方法允许计算两个日期之间的天数：
     * <pre>
     * 
     * @param temporal1Inclusive  the base temporal object, not null
     * @param temporal2Exclusive  the other temporal object, exclusive, not null
     * @return the amount of time between temporal1Inclusive and temporal2Exclusive
     *  in terms of this unit; positive if temporal2Exclusive is later than
     *  temporal1Inclusive, negative if earlier
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to the same type as the start temporal
     * @throws UnsupportedTemporalTypeException if the unit is not supported by the temporal
     * @throws ArithmeticException if numeric overflow occurs
     */
    long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive);

    //-----------------------------------------------------------------------
    /**
     * Gets a descriptive name for the unit.
     * <p>
     * This should be in the plural and upper-first camel case, such as 'Days' or 'Minutes'.
     *
     * <p>
     *  long daysBetween = DAYS.between(start,end); //或者long daysBetween = start.until(end,DAYS);
     * </pre>
     * <p>
     *  实施应使用{@link ChronoUnit}中可用的单位或{@link ChronoField}中的字段执行任何查询或计算。
     * 如果不支持该单元,则必须抛出{@code UnsupportedTemporalTypeException}。实现不得更改指定的时间对象。
     * 
     *  @implSpec实现必须通过使用{@code getClass()}检查两个临时值是否具有相同的类型来开始。
     * 
     * @return the name of this unit, not null
     */
    @Override
    String toString();

}
