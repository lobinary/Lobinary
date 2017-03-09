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

/**
 * Framework-level interface defining read-write access to a temporal object,
 * such as a date, time, offset or some combination of these.
 * <p>
 * This is the base interface type for date, time and offset objects that
 * are complete enough to be manipulated using plus and minus.
 * It is implemented by those classes that can provide and manipulate information
 * as {@linkplain TemporalField fields} or {@linkplain TemporalQuery queries}.
 * See {@link TemporalAccessor} for the read-only version of this interface.
 * <p>
 * Most date and time information can be represented as a number.
 * These are modeled using {@code TemporalField} with the number held using
 * a {@code long} to handle large values. Year, month and day-of-month are
 * simple examples of fields, but they also include instant and offsets.
 * See {@link ChronoField} for the standard set of fields.
 * <p>
 * Two pieces of date/time information cannot be represented by numbers,
 * the {@linkplain java.time.chrono.Chronology chronology} and the
 * {@linkplain java.time.ZoneId time-zone}.
 * These can be accessed via {@link #query(TemporalQuery) queries} using
 * the static methods defined on {@link TemporalQuery}.
 * <p>
 * This interface is a framework-level interface that should not be widely
 * used in application code. Instead, applications should create and pass
 * around instances of concrete types, such as {@code LocalDate}.
 * There are many reasons for this, part of which is that implementations
 * of this interface may be in calendar systems other than ISO.
 * See {@link java.time.chrono.ChronoLocalDate} for a fuller discussion of the issues.
 *
 * <h3>When to implement</h3>
 * <p>
 * A class should implement this interface if it meets three criteria:
 * <ul>
 * <li>it provides access to date/time/offset information, as per {@code TemporalAccessor}
 * <li>the set of fields are contiguous from the largest to the smallest
 * <li>the set of fields are complete, such that no other field is needed to define the
 *  valid range of values for the fields that are represented
 * </ul>
 * <p>
 * Four examples make this clear:
 * <ul>
 * <li>{@code LocalDate} implements this interface as it represents a set of fields
 *  that are contiguous from days to forever and require no external information to determine
 *  the validity of each date. It is therefore able to implement plus/minus correctly.
 * <li>{@code LocalTime} implements this interface as it represents a set of fields
 *  that are contiguous from nanos to within days and require no external information to determine
 *  validity. It is able to implement plus/minus correctly, by wrapping around the day.
 * <li>{@code MonthDay}, the combination of month-of-year and day-of-month, does not implement
 *  this interface.  While the combination is contiguous, from days to months within years,
 *  the combination does not have sufficient information to define the valid range of values
 *  for day-of-month.  As such, it is unable to implement plus/minus correctly.
 * <li>The combination day-of-week and day-of-month ("Friday the 13th") should not implement
 *  this interface. It does not represent a contiguous set of fields, as days to weeks overlaps
 *  days to months.
 * </ul>
 *
 * @implSpec
 * This interface places no restrictions on the mutability of implementations,
 * however immutability is strongly recommended.
 * All implementations must be {@link Comparable}.
 *
 * <p>
 *  框架级接口定义对时间对象的读写访问,例如日期,时间,偏移或这些的某种组合。
 * <p>
 *  这是日期,时间和偏移对象的基本接口类型,完全足以使用加号和减号进行操作。
 * 它由可以提供和操作信息的类实现为{@linkplain TemporalField fields}或{@linkplain TemporalQuery queries}。
 * 有关此接口的只读版本,请参阅{@link TemporalAccessor}。
 * <p>
 * 大多数日期和时间信息可以表示为数字。这些是使用{@code TemporalField}建模的,使用{@code long}保存的数字来处理大的值。
 * 年,月和日是字段的简单示例,但它们也包括即时和偏移量。有关标准字段集,请参见{@link ChronoField}。
 * <p>
 *  两个日期/时间信息不能由数字{@linkplain java.time.chrono.Chronology chronology}和{@linkplain java.time.ZoneId time-zone}
 * 表示。
 * 这些可以通过{@link #query(TemporalQuery)查询}使用在{@link TemporalQuery}上定义的静态方法访问。
 * <p>
 *  此接口是一个框架级接口,不应在应用程序代码中广泛使用。相反,应用程序应该创建和传递具体类型的实例,例如{@code LocalDate}。
 * 这有许多原因,其中的一部分是该接口的实现可以在除ISO之外的日历系统中。有关这些问题的更全面讨论,请参阅{@link java.time.chrono.ChronoLocalDate}。
 * 
 *  <h3>何时实施</h3>
 * <p>
 *  如果满足以下三个条件,类应实现此接口：
 * <ul>
 *  <li>它提供对日期/时间/偏移量信息的访问,根据{@code TemporalAccessor} <li>字段集从最大到最小<li>字段集完成,字段来定义所表示字段的有效值范围
 * </ul>
 * <p>
 *  四个例子说明了这一点：
 * <ul>
 * <li> {@ code LocalDate}实现此接口,因为它代表一组从天到连续的字段,并且不需要外部信息来确定每个日期的有效性。因此能够正确地实现正/负。
 *  <li> {@ code LocalTime}实现此接口,因为它表示一组从nanos到几天内连续的字段,并且不需要外部信息来确定有效性。它能够正确地实现加/减,通过环绕一天。
 *  <li> {@ code MonthDay}是年月日和月份的组合,不会实现此界面。虽然组合是连续的,从几天到几年内,但组合没有足够的信息来定义日期的有效值范围。因此,它不能正确地实现正/负。
 *  <li>组合星期和星期几("星期五13日")不应实现此接口。它不表示连续的字段集,因为天到周重叠天到几个月。
 * </ul>
 * 
 *  @implSpec此接口对实现的可变性没有限制,但强烈建议使用不可变性。所有实现必须是{@link Comparable}。
 * 
 * 
 * @since 1.8
 */
public interface Temporal extends TemporalAccessor {

    /**
     * Checks if the specified unit is supported.
     * <p>
     * This checks if the specified unit can be added to, or subtracted from, this date-time.
     * If false, then calling the {@link #plus(long, TemporalUnit)} and
     * {@link #minus(long, TemporalUnit) minus} methods will throw an exception.
     *
     * @implSpec
     * Implementations must check and handle all units defined in {@link ChronoUnit}.
     * If the unit is supported, then true must be returned, otherwise false must be returned.
     * <p>
     * If the field is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.isSupportedBy(Temporal)}
     * passing {@code this} as the argument.
     * <p>
     * Implementations must ensure that no observable state is altered when this
     * read-only method is invoked.
     *
     * <p>
     *  检查是否支持指定的单元。
     * <p>
     * 这将检查指定的单位是否可以添加到此日期时间或从此日期时间中减去。
     * 如果为false,则调用{@link #plus(long,TemporalUnit)}和{@link #minus(long,TemporalUnit)minus}方法将抛出异常。
     * 
     *  @implSpec实现必须检查和处理{@link ChronoUnit}中定义的所有单元。如果支持单位,则必须返回true,否则必须返回false。
     * <p>
     *  如果字段不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.isSupportedBy(Temporal)}传递{@code this}作为参数来获得此方法的
     * 结果。
     * <p>
     *  实现必须确保在调用此只读方法时不会更改可观察状态。
     * 
     * 
     * @param unit  the unit to check, null returns false
     * @return true if the unit can be added/subtracted, false if not
     */
    boolean isSupported(TemporalUnit unit);

    /**
     * Returns an adjusted object of the same type as this object with the adjustment made.
     * <p>
     * This adjusts this date-time according to the rules of the specified adjuster.
     * A simple adjuster might simply set the one of the fields, such as the year field.
     * A more complex adjuster might set the date to the last day of the month.
     * A selection of common adjustments is provided in {@link TemporalAdjuster}.
     * These include finding the "last day of the month" and "next Wednesday".
     * The adjuster is responsible for handling special cases, such as the varying
     * lengths of month and leap years.
     * <p>
     * Some example code indicating how and why this method is used:
     * <pre>
     *  date = date.with(Month.JULY);        // most key classes implement TemporalAdjuster
     *  date = date.with(lastDayOfMonth());  // static import from Adjusters
     *  date = date.with(next(WEDNESDAY));   // static import from Adjusters and DayOfWeek
     * </pre>
     *
     * @implSpec
     * <p>
     * Implementations must not alter either this object or the specified temporal object.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable implementations.
     * <p>
     * The default implementation must behave equivalent to this code:
     * <pre>
     *  return adjuster.adjustInto(this);
     * </pre>
     *
     * <p>
     *  返回与此对象具有相同类型的已调整对象,并进行调整。
     * <p>
     *  这将根据指定调整器的规则调整此日期时间。简单的调整器可以简单地设置一个字段,例如年份字段。更复杂的调整器可以将日期设置为该月的最后一天。
     *  {@link TemporalAdjuster}中提供了一些常用调整选项。这些包括找到"月份的最后一天"和"下周三"。调整器负责处理特殊情况,例如月份和闰年的变化长度。
     * <p>
     *  一些示例代码指示此方法的使用方式和原因：
     * <pre>
     * date = date.with(Month.JULY); //大多数键类实现TemporalAdjuster date = date.with(lastDayOfMonth()); // static
     *  import from Adjusters date = date.with(next(WEDNESDAY)); //从Adjusters和DayOfWeek静态导入。
     * </pre>
     * 
     *  @implSpec
     * <p>
     *  实现不能更改此对象或指定的临时对象。相反,必须返回原件的调整副本。这为不可变和可变实现提供了等效的,安全的行为。
     * <p>
     *  默认实现必须与此代码等效：
     * <pre>
     *  return adjuster.adjustInto(this);
     * </pre>
     * 
     * 
     * @param adjuster  the adjuster to use, not null
     * @return an object of the same type with the specified adjustment made, not null
     * @throws DateTimeException if unable to make the adjustment
     * @throws ArithmeticException if numeric overflow occurs
     */
    default Temporal with(TemporalAdjuster adjuster) {
        return adjuster.adjustInto(this);
    }

    /**
     * Returns an object of the same type as this object with the specified field altered.
     * <p>
     * This returns a new object based on this one with the value for the specified field changed.
     * For example, on a {@code LocalDate}, this could be used to set the year, month or day-of-month.
     * The returned object will have the same observable type as this object.
     * <p>
     * In some cases, changing a field is not fully defined. For example, if the target object is
     * a date representing the 31st January, then changing the month to February would be unclear.
     * In cases like this, the field is responsible for resolving the result. Typically it will choose
     * the previous valid date, which would be the last valid day of February in this example.
     *
     * @implSpec
     * Implementations must check and handle all fields defined in {@link ChronoField}.
     * If the field is supported, then the adjustment must be performed.
     * If unsupported, then an {@code UnsupportedTemporalTypeException} must be thrown.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.adjustInto(Temporal, long)}
     * passing {@code this} as the first argument.
     * <p>
     * Implementations must not alter this object.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable implementations.
     *
     * <p>
     *  返回与此对象相同类型的对象,其中指定的字段已更改。
     * <p>
     *  这将返回一个基于此对象的新对象,其中指定字段的值已更改。例如,在{@code LocalDate}上,这可以用于设置年,月或日。返回的对象将具有与此对象相同的observable类型。
     * <p>
     *  在某些情况下,更改字段未完全定义。例如,如果目标对象是表示1月31日的日期,则将该月更改为2月将是不清楚的。在这种情况下,字段负责解析结果。
     * 通常,它将选择上一个有效日期,这将是本示例中二月的最后一个有效日期。
     * 
     * @implSpec实现必须检查和处理{@link ChronoField}中定义的所有字段。如果支持该字段,则必须执行调整。
     * 如果不受支持,则必须抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.adjustInto(Temporal,long)}传递{@code this}作为第一个参数
     * 来获得此方法的结果。
     * <p>
     *  实现不能更改此对象。相反,必须返回原件的调整副本。这为不可变和可变实现提供了等效的,安全的行为。
     * 
     * 
     * @param field  the field to set in the result, not null
     * @param newValue  the new value of the field in the result
     * @return an object of the same type with the specified field set, not null
     * @throws DateTimeException if the field cannot be set
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    Temporal with(TemporalField field, long newValue);

    //-----------------------------------------------------------------------
    /**
     * Returns an object of the same type as this object with an amount added.
     * <p>
     * This adjusts this temporal, adding according to the rules of the specified amount.
     * The amount is typically a {@link java.time.Period} but may be any other type implementing
     * the {@link TemporalAmount} interface, such as {@link java.time.Duration}.
     * <p>
     * Some example code indicating how and why this method is used:
     * <pre>
     *  date = date.plus(period);                // add a Period instance
     *  date = date.plus(duration);              // add a Duration instance
     *  date = date.plus(workingDays(6));        // example user-written workingDays method
     * </pre>
     * <p>
     * Note that calling {@code plus} followed by {@code minus} is not guaranteed to
     * return the same date-time.
     *
     * @implSpec
     * <p>
     * Implementations must not alter either this object or the specified temporal object.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable implementations.
     * <p>
     * The default implementation must behave equivalent to this code:
     * <pre>
     *  return amount.addTo(this);
     * </pre>
     *
     * <p>
     *  返回与此对象相同类型的对象,并添加一个数量。
     * <p>
     *  这将调整此时间,根据指定金额的规则添加。
     * 金额通常为{@link java.time.Period},但可以是实现{@link TemporalAmount}接口的任何其他类型,例如{@link java.time.Duration}。
     * <p>
     *  一些示例代码指示此方法的使用方式和原因：
     * <pre>
     *  date = date.plus(period); // add a Period instance date = date.plus(duration); // add a Duration ins
     * tance date = date.plus(workingDays(6)); //示例用户编写的workingDays方法。
     * </pre>
     * <p>
     *  请注意,调用{@code plus}后跟{@code minus}不保证返回相同的日期时间。
     * 
     *  @implSpec
     * <p>
     * 实现不能更改此对象或指定的临时对象。相反,必须返回原件的调整副本。这为不可变和可变实现提供了等效的,安全的行为。
     * <p>
     *  默认实现必须与此代码等效：
     * <pre>
     *  return amount.addTo(this);
     * </pre>
     * 
     * 
     * @param amount  the amount to add, not null
     * @return an object of the same type with the specified adjustment made, not null
     * @throws DateTimeException if the addition cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    default Temporal plus(TemporalAmount amount) {
        return amount.addTo(this);
    }

    /**
     * Returns an object of the same type as this object with the specified period added.
     * <p>
     * This method returns a new object based on this one with the specified period added.
     * For example, on a {@code LocalDate}, this could be used to add a number of years, months or days.
     * The returned object will have the same observable type as this object.
     * <p>
     * In some cases, changing a field is not fully defined. For example, if the target object is
     * a date representing the 31st January, then adding one month would be unclear.
     * In cases like this, the field is responsible for resolving the result. Typically it will choose
     * the previous valid date, which would be the last valid day of February in this example.
     *
     * @implSpec
     * Implementations must check and handle all units defined in {@link ChronoUnit}.
     * If the unit is supported, then the addition must be performed.
     * If unsupported, then an {@code UnsupportedTemporalTypeException} must be thrown.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.addTo(Temporal, long)}
     * passing {@code this} as the first argument.
     * <p>
     * Implementations must not alter this object.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable implementations.
     *
     * <p>
     *  返回与此对象具有相同类型的对象,并添加指定的周期。
     * <p>
     *  此方法将基于添加了指定时间段的新对象返回一个新对象。例如,在{@code LocalDate}上,这可以用于添加几年,几个月或几天。返回的对象将具有与此对象相同的observable类型。
     * <p>
     *  在某些情况下,更改字段未完全定义。例如,如果目标对象是表示1月31日的日期,则添加一个月将是不清楚的。在这种情况下,字段负责解析结果。
     * 通常,它将选择上一个有效日期,这将是本示例中二月的最后一个有效日期。
     * 
     *  @implSpec实现必须检查和处理{@link ChronoUnit}中定义的所有单元。如果支持该单元,则必须执行添加。
     * 如果不受支持,则必须抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.addTo(Temporal,long)}传递{@code this}作为第一个参数来获得此方法的
     * 结果。
     * <p>
     * 实现不能更改此对象。相反,必须返回原件的调整副本。这为不可变和可变实现提供了等效的,安全的行为。
     * 
     * 
     * @param amountToAdd  the amount of the specified unit to add, may be negative
     * @param unit  the unit of the period to add, not null
     * @return an object of the same type with the specified period added, not null
     * @throws DateTimeException if the unit cannot be added
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    Temporal plus(long amountToAdd, TemporalUnit unit);

    //-----------------------------------------------------------------------
    /**
     * Returns an object of the same type as this object with an amount subtracted.
     * <p>
     * This adjusts this temporal, subtracting according to the rules of the specified amount.
     * The amount is typically a {@link java.time.Period} but may be any other type implementing
     * the {@link TemporalAmount} interface, such as {@link java.time.Duration}.
     * <p>
     * Some example code indicating how and why this method is used:
     * <pre>
     *  date = date.minus(period);               // subtract a Period instance
     *  date = date.minus(duration);             // subtract a Duration instance
     *  date = date.minus(workingDays(6));       // example user-written workingDays method
     * </pre>
     * <p>
     * Note that calling {@code plus} followed by {@code minus} is not guaranteed to
     * return the same date-time.
     *
     * @implSpec
     * <p>
     * Implementations must not alter either this object or the specified temporal object.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable implementations.
     * <p>
     * The default implementation must behave equivalent to this code:
     * <pre>
     *  return amount.subtractFrom(this);
     * </pre>
     *
     * <p>
     *  返回与此对象相同类型的对象,并减去一个值。
     * <p>
     *  这将调整此时间,根据指定金额的规则减去。
     * 金额通常为{@link java.time.Period},但可以是实现{@link TemporalAmount}接口的任何其他类型,例如{@link java.time.Duration}。
     * <p>
     *  一些示例代码指示此方法的使用方式和原因：
     * <pre>
     *  date = date.minus(period); // subtract a Period instance date = date.minus(duration); // subtract a 
     * Duration instance date = date.minus(workingDays(6)); //示例用户编写的workingDays方法。
     * </pre>
     * <p>
     *  请注意,调用{@code plus}后跟{@code minus}不保证返回相同的日期时间。
     * 
     *  @implSpec
     * <p>
     *  实现不能更改此对象或指定的临时对象。相反,必须返回原件的调整副本。这为不可变和可变实现提供了等效的,安全的行为。
     * <p>
     *  默认实现必须与此代码等效：
     * <pre>
     *  return amount.subtractFrom(this);
     * </pre>
     * 
     * 
     * @param amount  the amount to subtract, not null
     * @return an object of the same type with the specified adjustment made, not null
     * @throws DateTimeException if the subtraction cannot be made
     * @throws ArithmeticException if numeric overflow occurs
     */
    default Temporal minus(TemporalAmount amount) {
        return amount.subtractFrom(this);
    }

    /**
     * Returns an object of the same type as this object with the specified period subtracted.
     * <p>
     * This method returns a new object based on this one with the specified period subtracted.
     * For example, on a {@code LocalDate}, this could be used to subtract a number of years, months or days.
     * The returned object will have the same observable type as this object.
     * <p>
     * In some cases, changing a field is not fully defined. For example, if the target object is
     * a date representing the 31st March, then subtracting one month would be unclear.
     * In cases like this, the field is responsible for resolving the result. Typically it will choose
     * the previous valid date, which would be the last valid day of February in this example.
     *
     * @implSpec
     * Implementations must behave in a manor equivalent to the default method behavior.
     * <p>
     * Implementations must not alter this object.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable implementations.
     * <p>
     * The default implementation must behave equivalent to this code:
     * <pre>
     *  return (amountToSubtract == Long.MIN_VALUE ?
     *      plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
     * </pre>
     *
     * <p>
     *  返回与此对象相同类型的对象,并减去指定的周期。
     * <p>
     * 此方法根据减去指定周期的此对象返回一个新对象。例如,在{@code LocalDate}上,这可以用于减去几年,几个月或几天。返回的对象将具有与此对象相同的observable类型。
     * <p>
     *  在某些情况下,更改字段未完全定义。例如,如果目标对象是表示3月31日的日期,则减去一个月将是不清楚的。在这种情况下,字段负责解析结果。
     * 通常,它将选择上一个有效日期,这将是本示例中二月的最后一个有效日期。
     * 
     *  @implSpec实现必须以等同于默认方法行为的庄园行为。
     * <p>
     *  实现不能更改此对象。相反,必须返回原件的调整副本。这为不可变和可变实现提供了等效的,安全的行为。
     * <p>
     *  默认实现必须与此代码等效：
     * <pre>
     *  return(amountToSubtract == Long.MIN_VALUE?plus(Long.MAX_VALUE,unit).plus(1,unit)：plus(-amountToSubtr
     * act,unit));。
     * </pre>
     * 
     * 
     * @param amountToSubtract  the amount of the specified unit to subtract, may be negative
     * @param unit  the unit of the period to subtract, not null
     * @return an object of the same type with the specified period subtracted, not null
     * @throws DateTimeException if the unit cannot be subtracted
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    default Temporal minus(long amountToSubtract, TemporalUnit unit) {
        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Calculates the amount of time until another temporal in terms of the specified unit.
     * <p>
     * This calculates the amount of time between two temporal objects
     * in terms of a single {@code TemporalUnit}.
     * The start and end points are {@code this} and the specified temporal.
     * The end point is converted to be of the same type as the start point if different.
     * The result will be negative if the end is before the start.
     * For example, the period in hours between two temporal objects can be
     * calculated using {@code startTime.until(endTime, HOURS)}.
     * <p>
     * The calculation returns a whole number, representing the number of
     * complete units between the two temporals.
     * For example, the period in hours between the times 11:30 and 13:29
     * will only be one hour as it is one minute short of two hours.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link TemporalUnit#between(Temporal, Temporal)}:
     * <pre>
     *   // these two lines are equivalent
     *   temporal = start.until(end, unit);
     *   temporal = unit.between(start, end);
     * </pre>
     * The choice should be made based on which makes the code more readable.
     * <p>
     * For example, this method allows the number of days between two dates to
     * be calculated:
     * <pre>
     *  long daysBetween = start.until(end, DAYS);
     *  // or alternatively
     *  long daysBetween = DAYS.between(start, end);
     * </pre>
     *
     * @implSpec
     * Implementations must begin by checking to ensure that the input temporal
     * object is of the same observable type as the implementation.
     * They must then perform the calculation for all instances of {@link ChronoUnit}.
     * An {@code UnsupportedTemporalTypeException} must be thrown for {@code ChronoUnit}
     * instances that are unsupported.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.between(Temporal, Temporal)}
     * passing {@code this} as the first argument and the converted input temporal as
     * the second argument.
     * <p>
     * In summary, implementations must behave in a manner equivalent to this pseudo-code:
     * <pre>
     *  // convert the end temporal to the same type as this class
     *  if (unit instanceof ChronoUnit) {
     *    // if unit is supported, then calculate and return result
     *    // else throw UnsupportedTemporalTypeException for unsupported units
     *  }
     *  return unit.between(this, convertedEndTemporal);
     * </pre>
     * <p>
     * Note that the unit's {@code between} method must only be invoked if the
     * two temporal objects have exactly the same type evaluated by {@code getClass()}.
     * <p>
     * Implementations must ensure that no observable state is altered when this
     * read-only method is invoked.
     *
     * <p>
     *  计算直到指定单位的另一个时间的时间量。
     * <p>
     * 这将根据单个{@code TemporalUnit}计算两个时间对象之间的时间量。起始点和结束点是{@code this}和指定的时间。如果不同,则将终点转换为与起点相同的类型。
     * 如果结束在开始之前,结果将为负。例如,可以使用{@code startTime.until(endTime,HOURS)}来计算两个时间对象之间的以小时为单位的周期。
     * <p>
     *  计算返回一个整数,表示两个时间之间的完整单位数。例如,时间11:30和13:29之间的时间段将仅为一个小时,因为它是两个小时的一分钟。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link TemporalUnit#between(Temporal,Temporal)}：
     * <pre>
     *  //这两行是等价的temporal = start.until(end,unit); temporal = unit.between(start,end);
     * </pre>
     *  应该基于哪个使得代码更可读的选择。
     * <p>
     *  例如,此方法允许计算两个日期之间的天数：
     * <pre>
     *  long daysBetween = start.until(end,DAYS); //或者long daysBetween = DAYS.between(start,end);
     * </pre>
     * 
     * @param endExclusive  the end temporal, exclusive, converted to be of the
     *  same type as this object, not null
     * @param unit  the unit to measure the amount in, not null
     * @return the amount of time between this temporal object and the specified one
     *  in terms of the unit; positive if the specified object is later than this one,
     *  negative if it is earlier than this one
     * @throws DateTimeException if the amount cannot be calculated, or the end
     *  temporal cannot be converted to the same type as this temporal
     * @throws UnsupportedTemporalTypeException if the unit is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    long until(Temporal endExclusive, TemporalUnit unit);

}
