/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Copyright (c) 2013, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2013,Stephen Colebourne和Michael Nascimento Santos
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

import java.time.DateTimeException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.List;
import java.util.Objects;

/**
 * A date-based amount of time, such as '3 years, 4 months and 5 days' in an
 * arbitrary chronology, intended for advanced globalization use cases.
 * <p>
 * This interface models a date-based amount of time in a calendar system.
 * While most calendar systems use years, months and days, some do not.
 * Therefore, this interface operates solely in terms of a set of supported
 * units that are defined by the {@code Chronology}.
 * The set of supported units is fixed for a given chronology.
 * The amount of a supported unit may be set to zero.
 * <p>
 * The period is modeled as a directed amount of time, meaning that individual
 * parts of the period may be negative.
 *
 * @implSpec
 * This interface must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * Subclasses should be Serializable wherever possible.
 *
 * <p>
 *  以任意年表为基础的基于日期的时间量,例如"3年,4个月和5天",用于高级全球化用例。
 * <p>
 *  此接口模拟日历系统中基于日期的时间量。虽然大多数日历系统使用年,月和日,但有些不使用。因此,该接口仅仅依据由{@code Chronology}定义的一组支持单元操作。
 * 对于给定的年表,支持单位的集合是固定的。支持单元的量可以被设置为零。
 * <p>
 *  周期被建模为定向时间量,意味着周期的各个部分可以是负的。
 * 
 * @implSpec必须小心地实现此接口,以确保其他类正常运行。所有可以实例化的实现必须是final,immutable和线程安全的。子类应尽可能序列化。
 * 
 * 
 * @since 1.8
 */
public interface ChronoPeriod
        extends TemporalAmount {

    /**
     * Obtains a {@code ChronoPeriod} consisting of amount of time between two dates.
     * <p>
     * The start date is included, but the end date is not.
     * The period is calculated using {@link ChronoLocalDate#until(ChronoLocalDate)}.
     * As such, the calculation is chronology specific.
     * <p>
     * The chronology of the first date is used.
     * The chronology of the second date is ignored, with the date being converted
     * to the target chronology system before the calculation starts.
     * <p>
     * The result of this method can be a negative period if the end is before the start.
     * In most cases, the positive/negative sign will be the same in each of the supported fields.
     *
     * <p>
     *  获取{@code ChronoPeriod},由两个日期之间的时间量组成。
     * <p>
     *  包括开始日期,但不包括结束日期。期间使用{@link ChronoLocalDate#until(ChronoLocalDate)}计算。因此,计算是年表特定的。
     * <p>
     *  使用第一个日期的年表。第二个日期的年表将被忽略,日期在计算开始前转换为目标年表系统。
     * <p>
     *  如果结束在开始之前,此方法的结果可以是负期间。在大多数情况下,每个支持字段中的正/负号都是相同的。
     * 
     * 
     * @param startDateInclusive  the start date, inclusive, specifying the chronology of the calculation, not null
     * @param endDateExclusive  the end date, exclusive, in any chronology, not null
     * @return the period between this date and the end date, not null
     * @see ChronoLocalDate#until(ChronoLocalDate)
     */
    public static ChronoPeriod between(ChronoLocalDate startDateInclusive, ChronoLocalDate endDateExclusive) {
        Objects.requireNonNull(startDateInclusive, "startDateInclusive");
        Objects.requireNonNull(endDateExclusive, "endDateExclusive");
        return startDateInclusive.until(endDateExclusive);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the value of the requested unit.
     * <p>
     * The supported units are chronology specific.
     * They will typically be {@link ChronoUnit#YEARS YEARS},
     * {@link ChronoUnit#MONTHS MONTHS} and {@link ChronoUnit#DAYS DAYS}.
     * Requesting an unsupported unit will throw an exception.
     *
     * <p>
     *  获取所请求单位的值。
     * <p>
     *  支持的单位是年表特有的。
     * 通常为{@link ChronoUnit#YEARS YEARS},{@link ChronoUnit#MONTHS MONTHS}和{@link ChronoUnit#DAYS DAYS}。
     * 请求不支持的单元将抛出异常。
     * 
     * 
     * @param unit the {@code TemporalUnit} for which to return the value
     * @return the long value of the unit
     * @throws DateTimeException if the unit is not supported
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     */
    @Override
    long get(TemporalUnit unit);

    /**
     * Gets the set of units supported by this period.
     * <p>
     * The supported units are chronology specific.
     * They will typically be {@link ChronoUnit#YEARS YEARS},
     * {@link ChronoUnit#MONTHS MONTHS} and {@link ChronoUnit#DAYS DAYS}.
     * They are returned in order from largest to smallest.
     * <p>
     * This set can be used in conjunction with {@link #get(TemporalUnit)}
     * to access the entire state of the period.
     *
     * <p>
     *  获取此期间支持的单位集。
     * <p>
     *  支持的单位是年表特有的。
     * 通常为{@link ChronoUnit#YEARS YEARS},{@link ChronoUnit#MONTHS MONTHS}和{@link ChronoUnit#DAYS DAYS}。
     * 它们按从最大到最小的顺序返回。
     * <p>
     * 此集合可以与{@link #get(TemporalUnit)}结合使用以访问期间的整个状态。
     * 
     * 
     * @return a list containing the supported units, not null
     */
    @Override
    List<TemporalUnit> getUnits();

    /**
     * Gets the chronology that defines the meaning of the supported units.
     * <p>
     * The period is defined by the chronology.
     * It controls the supported units and restricts addition/subtraction
     * to {@code ChronoLocalDate} instances of the same chronology.
     *
     * <p>
     *  获取定义支持单位含义的年表。
     * <p>
     *  周期由年表确定。它控制支持的单位,并限制对{@code ChronoLocalDate}实例的相同年表的加/减。
     * 
     * 
     * @return the chronology defining the period, not null
     */
    Chronology getChronology();

    //-----------------------------------------------------------------------
    /**
     * Checks if all the supported units of this period are zero.
     *
     * <p>
     *  检查此期间的所有支持单位是否为零。
     * 
     * 
     * @return true if this period is zero-length
     */
    default boolean isZero() {
        for (TemporalUnit unit : getUnits()) {
            if (get(unit) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if any of the supported units of this period are negative.
     *
     * <p>
     *  检查此期间的任何受支持单位是否为负。
     * 
     * 
     * @return true if any unit of this period is negative
     */
    default boolean isNegative() {
        for (TemporalUnit unit : getUnits()) {
            if (get(unit) < 0) {
                return true;
            }
        }
        return false;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the specified period added.
     * <p>
     * If the specified amount is a {@code ChronoPeriod} then it must have
     * the same chronology as this period. Implementations may choose to
     * accept or reject other {@code TemporalAmount} implementations.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回添加了指定时间段的此期间的副本。
     * <p>
     *  如果指定的金额是{@code ChronoPeriod},那么它必须与此期间具有相同的年表。实现可以选择接受或拒绝其他{@code TemporalAmount}实现。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToAdd  the period to add, not null
     * @return a {@code ChronoPeriod} based on this period with the requested period added, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    ChronoPeriod plus(TemporalAmount amountToAdd);

    /**
     * Returns a copy of this period with the specified period subtracted.
     * <p>
     * If the specified amount is a {@code ChronoPeriod} then it must have
     * the same chronology as this period. Implementations may choose to
     * accept or reject other {@code TemporalAmount} implementations.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此周期的副本,减去指定的周期。
     * <p>
     *  如果指定的金额是{@code ChronoPeriod},那么它必须与此期间具有相同的年表。实现可以选择接受或拒绝其他{@code TemporalAmount}实现。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param amountToSubtract  the period to subtract, not null
     * @return a {@code ChronoPeriod} based on this period with the requested period subtracted, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    ChronoPeriod minus(TemporalAmount amountToSubtract);

    //-----------------------------------------------------------------------
    /**
     * Returns a new instance with each amount in this period in this period
     * multiplied by the specified scalar.
     * <p>
     * This returns a period with each supported unit individually multiplied.
     * For example, a period of "2 years, -3 months and 4 days" multiplied by
     * 3 will return "6 years, -9 months and 12 days".
     * No normalization is performed.
     *
     * <p>
     *  返回一个新实例,此周期中的每个值都乘以指定的标量。
     * <p>
     *  这将返回一个周期,每个支持的单位单独乘。例如,"2年,-3个月和4天"的周期乘以3将返回"6年,-9个月和12天"。不执行归一化。
     * 
     * 
     * @param scalar  the scalar to multiply by, not null
     * @return a {@code ChronoPeriod} based on this period with the amounts multiplied
     *  by the scalar, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    ChronoPeriod multipliedBy(int scalar);

    /**
     * Returns a new instance with each amount in this period negated.
     * <p>
     * This returns a period with each supported unit individually negated.
     * For example, a period of "2 years, -3 months and 4 days" will be
     * negated to "-2 years, 3 months and -4 days".
     * No normalization is performed.
     *
     * <p>
     * 返回一个新实例,其中此期间的每个金额都被取消。
     * <p>
     *  这将返回一个周期,每个支持的单位单独取消。例如,"2年,-3个月和4天"的期间将被取消为"-2年,3个月和-4天"。不执行归一化。
     * 
     * 
     * @return a {@code ChronoPeriod} based on this period with the amounts negated, not null
     * @throws ArithmeticException if numeric overflow occurs, which only happens if
     *  one of the units has the value {@code Long.MIN_VALUE}
     */
    default ChronoPeriod negated() {
        return multipliedBy(-1);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the amounts of each unit normalized.
     * <p>
     * The process of normalization is specific to each calendar system.
     * For example, in the ISO calendar system, the years and months are
     * normalized but the days are not, such that "15 months" would be
     * normalized to "1 year and 3 months".
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此期间的副本,每个单位的金额已归一化。
     * <p>
     *  归一化的过程是特定于每个日历系统的。例如,在ISO日历系统中,年和月被归一化,但天不是,使得"15个月"被归一化为"1年和3个月"。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return a {@code ChronoPeriod} based on this period with the amounts of each
     *  unit normalized, not null
     * @throws ArithmeticException if numeric overflow occurs
     */
    ChronoPeriod normalized();

    //-------------------------------------------------------------------------
    /**
     * Adds this period to the specified temporal object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with this period added.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#plus(TemporalAmount)}.
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   dateTime = thisPeriod.addTo(dateTime);
     *   dateTime = dateTime.plus(thisPeriod);
     * </pre>
     * <p>
     * The specified temporal must have the same chronology as this period.
     * This returns a temporal with the non-zero supported units added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将此期间添加到指定的临时对象。
     * <p>
     *  这返回与添加了此句点的输入相同的observable类型的时间对象。
     * <p>
     *  在大多数情况下,使用{@link Temporal#plus(TemporalAmount)}来反转调用模式是更清楚的。
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐dateTime = thisPeriod.addTo(dateTime); dateTime = dateTime.plus(thisPeriod);
     * </pre>
     * <p>
     *  指定的时间必须与此期间具有相同的年表。这返回一个时间的非零支持单位添加。
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
    Temporal addTo(Temporal temporal);

    /**
     * Subtracts this period from the specified temporal object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with this period subtracted.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#minus(TemporalAmount)}.
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   dateTime = thisPeriod.subtractFrom(dateTime);
     *   dateTime = dateTime.minus(thisPeriod);
     * </pre>
     * <p>
     * The specified temporal must have the same chronology as this period.
     * This returns a temporal with the non-zero supported units subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  从指定的时间对象中减去此时间段。
     * <p>
     *  这将返回与减去此周期的输入相同的observable类型的时间对象。
     * <p>
     * 在大多数情况下,使用{@link Temporal#minus(TemporalAmount)}来反转调用模式是更清楚的。
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐dateTime = thisPeriod.subtractFrom(dateTime); dateTime = dateTime.minus(thisPerio
     * d);。
     * </pre>
     * <p>
     *  指定的时间必须与此期间具有相同的年表。这返回一个时间的非零支持单位减去。
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
    Temporal subtractFrom(Temporal temporal);

    //-----------------------------------------------------------------------
    /**
     * Checks if this period is equal to another period, including the chronology.
     * <p>
     * Compares this period with another ensuring that the type, each amount and
     * the chronology are the same.
     * Note that this means that a period of "15 Months" is not equal to a period
     * of "1 Year and 3 Months".
     *
     * <p>
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other period
     */
    @Override
    boolean equals(Object obj);

    /**
     * A hash code for this period.
     *
     * <p>
     *  检查此时间段是否等于另一个时间段,包括年表。
     * <p>
     *  将此期间与另一个期间进行比较,确保类型,每个金额和年表都相同。注意,这意味着"15个月"的期间不等于"1年3个月"的期间。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    int hashCode();

    //-----------------------------------------------------------------------
    /**
     * Outputs this period as a {@code String}.
     * <p>
     * The output will include the period amounts and chronology.
     *
     * <p>
     *  此期间的哈希码。
     * 
     * 
     * @return a string representation of this period, not null
     */
    @Override
    String toString();

}
