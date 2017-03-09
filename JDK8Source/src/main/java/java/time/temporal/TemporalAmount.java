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
 * Copyright (c) 2012, 2013 Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2012,2013 Stephen Colebourne和Michael Nascimento Santos
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
import java.time.Period;
import java.util.List;

/**
 * Framework-level interface defining an amount of time, such as
 * "6 hours", "8 days" or "2 years and 3 months".
 * <p>
 * This is the base interface type for amounts of time.
 * An amount is distinct from a date or time-of-day in that it is not tied
 * to any specific point on the time-line.
 * <p>
 * The amount can be thought of as a {@code Map} of {@link TemporalUnit} to
 * {@code long}, exposed via {@link #getUnits()} and {@link #get(TemporalUnit)}.
 * A simple case might have a single unit-value pair, such as "6 hours".
 * A more complex case may have multiple unit-value pairs, such as
 * "7 years, 3 months and 5 days".
 * <p>
 * There are two common implementations.
 * {@link Period} is a date-based implementation, storing years, months and days.
 * {@link Duration} is a time-based implementation, storing seconds and nanoseconds,
 * but providing some access using other duration based units such as minutes,
 * hours and fixed 24-hour days.
 * <p>
 * This interface is a framework-level interface that should not be widely
 * used in application code. Instead, applications should create and pass
 * around instances of concrete types, such as {@code Period} and {@code Duration}.
 *
 * @implSpec
 * This interface places no restrictions on the mutability of implementations,
 * however immutability is strongly recommended.
 *
 * <p>
 *  框架级接口定义时间量,例如"6小时","8天"或"2年3个月"。
 * <p>
 *  这是时间量的基本接口类型。金额不同于日期或时间,因为它不与时间线上的任何特定点相关联。
 * <p>
 *  这笔金额可以被视为{@link TemporalUnit}到{@code long}的{@code Map},通过{@link #getUnits()}和{@link #get(TemporalUnit)}
 * 公开。
 * 一个简单的情况可能有一个单一的单位值对,例如"6小时"。更复杂的情况可以具有多个单位 - 值对,例如"7年,3个月和5天"。
 * <p>
 * 有两个常见的实现。 {@link Period}是一个基于日期的实施,存储年,月和日。
 *  {@link Duration}是一种基于时间的实施,存储秒和纳秒,但使用其他基于时长的单位(例如分钟,小时和固定的24小时天)提供一些访问。
 * <p>
 *  此接口是一个框架级接口,不应在应用程序代码中广泛使用。相反,应用程序应创建并传递具体类型的实例,例如{@code Period}和{@code Duration}。
 * 
 *  @implSpec此接口对实现的可变性没有限制,但强烈建议使用不可变性。
 * 
 * 
 * @since 1.8
 */
public interface TemporalAmount {

    /**
     * Returns the value of the requested unit.
     * The units returned from {@link #getUnits()} uniquely define the
     * value of the {@code TemporalAmount}.  A value must be returned
     * for each unit listed in {@code getUnits}.
     *
     * @implSpec
     * Implementations may declare support for units not listed by {@link #getUnits()}.
     * Typically, the implementation would define additional units
     * as conversions for the convenience of developers.
     *
     * <p>
     *  返回请求的单位的值。从{@link #getUnits()}返回的单位唯一定义{@code TemporalAmount}的值。必须为{@code getUnits}中列出的每个单元返回一个值。
     * 
     *  @implSpec实现可以声明对{@link #getUnits()}未列出的单元的支持。通常,为了方便开发人员,实现将定义额外的单位作为转换。
     * 
     * 
     * @param unit the {@code TemporalUnit} for which to return the value
     * @return the long value of the unit
     * @throws DateTimeException if a value for the unit cannot be obtained
     * @throws UnsupportedTemporalTypeException if the {@code unit} is not supported
     */
    long get(TemporalUnit unit);

    /**
     * Returns the list of units uniquely defining the value of this TemporalAmount.
     * The list of {@code TemporalUnits} is defined by the implementation class.
     * The list is a snapshot of the units at the time {@code getUnits}
     * is called and is not mutable.
     * The units are ordered from longest duration to the shortest duration
     * of the unit.
     *
     * @implSpec
     * The list of units completely and uniquely represents the
     * state of the object without omissions, overlaps or duplication.
     * The units are in order from longest duration to shortest.
     *
     * <p>
     *  返回唯一定义此TemporalAmount的值的单位列表。 {@code TemporalUnits}的列表由实现类定义。该列表是调用{@code getUnits}时的单位的快照,不可变。
     * 单位从单位的最长持续时间到最短持续时间排序。
     * 
     * @implSpec单元列表完全和唯一地表示对象的状态,没有遗漏,重叠或重复。单位按照从最长到最短的顺序。
     * 
     * 
     * @return the List of {@code TemporalUnits}; not null
     */
    List<TemporalUnit> getUnits();

    /**
     * Adds to the specified temporal object.
     * <p>
     * Adds the amount to the specified temporal object using the logic
     * encapsulated in the implementing class.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link Temporal#plus(TemporalAmount)}:
     * <pre>
     *   // These two lines are equivalent, but the second approach is recommended
     *   dateTime = amount.addTo(dateTime);
     *   dateTime = dateTime.plus(adder);
     * </pre>
     * It is recommended to use the second approach, {@code plus(TemporalAmount)},
     * as it is a lot clearer to read in code.
     *
     * @implSpec
     * The implementation must take the input object and add to it.
     * The implementation defines the logic of the addition and is responsible for
     * documenting that logic. It may use any method on {@code Temporal} to
     * query the temporal object and perform the addition.
     * The returned object must have the same observable type as the input object
     * <p>
     * The input object must not be altered.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable temporal objects.
     * <p>
     * The input temporal object may be in a calendar system other than ISO.
     * Implementations may choose to document compatibility with other calendar systems,
     * or reject non-ISO temporal objects by {@link TemporalQueries#chronology() querying the chronology}.
     * <p>
     * This method may be called from multiple threads in parallel.
     * It must be thread-safe when invoked.
     *
     * <p>
     *  添加到指定的时间对象。
     * <p>
     *  使用实现类中封装的逻辑将数量添加到指定的临时对象。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link Temporal#plus(TemporalAmount)}：
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐的dateTime = amount.addTo(dateTime); dateTime = dateTime.plus(adder);
     * </pre>
     *  建议使用第二种方法{@code plus(TemporalAmount)},因为它在代码中更清晰。
     * 
     *  @implSpec实现必须接受输入对象并添加它。实现定义了添加的逻辑,并负责记录该逻辑。它可以使用{@code Temporal}上的任何方法来查询临时对象并执行添加。
     * 返回的对象必须具有与输入对象相同的observable类型。
     * <p>
     *  输入对象不能更改。相反,必须返回原件的调整副本。这为不可变和可变时间对象提供了等价的,安全的行为。
     * <p>
     * 输入时间对象可以在除ISO之外的日历系统中。实现可以选择记录与其他日历系统的兼容性,或通过{@link TemporalQueries#chronology()查询时间顺序来拒绝非ISO时间对象。
     * <p>
     *  此方法可以从多个线程并行调用。它在调用时必须是线程安全的。
     * 
     * 
     * @param temporal  the temporal object to add the amount to, not null
     * @return an object of the same observable type with the addition made, not null
     * @throws DateTimeException if unable to add
     * @throws ArithmeticException if numeric overflow occurs
     */
    Temporal addTo(Temporal temporal);

    /**
     * Subtracts this object from the specified temporal object.
     * <p>
     * Subtracts the amount from the specified temporal object using the logic
     * encapsulated in the implementing class.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link Temporal#minus(TemporalAmount)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   dateTime = amount.subtractFrom(dateTime);
     *   dateTime = dateTime.minus(amount);
     * </pre>
     * It is recommended to use the second approach, {@code minus(TemporalAmount)},
     * as it is a lot clearer to read in code.
     *
     * @implSpec
     * The implementation must take the input object and subtract from it.
     * The implementation defines the logic of the subtraction and is responsible for
     * documenting that logic. It may use any method on {@code Temporal} to
     * query the temporal object and perform the subtraction.
     * The returned object must have the same observable type as the input object
     * <p>
     * The input object must not be altered.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable temporal objects.
     * <p>
     * The input temporal object may be in a calendar system other than ISO.
     * Implementations may choose to document compatibility with other calendar systems,
     * or reject non-ISO temporal objects by {@link TemporalQueries#chronology() querying the chronology}.
     * <p>
     * This method may be called from multiple threads in parallel.
     * It must be thread-safe when invoked.
     *
     * <p>
     *  从指定的时间对象中减去此对象。
     * <p>
     *  使用实现类中封装的逻辑从指定的临时对象中减去金额。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link Temporal#minus(TemporalAmount)}：
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐的dateTime = amount.subtractFrom(dateTime); dateTime = dateTime.minus(amount);
     * </pre>
     *  建议使用第二种方法{@code minus(TemporalAmount)},因为它在代码中更清晰。
     * 
     * 
     * @param temporal  the temporal object to subtract the amount from, not null
     * @return an object of the same observable type with the subtraction made, not null
     * @throws DateTimeException if unable to subtract
     * @throws ArithmeticException if numeric overflow occurs
     */
    Temporal subtractFrom(Temporal temporal);
}
