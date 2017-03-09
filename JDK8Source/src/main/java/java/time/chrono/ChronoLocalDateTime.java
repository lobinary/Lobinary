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
 * Copyright (c) 2007-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2007-2012,Stephen Colebourne和Michael Nascimento Santos
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

import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoUnit.FOREVER;
import static java.time.temporal.ChronoUnit.NANOS;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalUnit;
import java.time.zone.ZoneRules;
import java.util.Comparator;
import java.util.Objects;

/**
 * A date-time without a time-zone in an arbitrary chronology, intended
 * for advanced globalization use cases.
 * <p>
 * <b>Most applications should declare method signatures, fields and variables
 * as {@link LocalDateTime}, not this interface.</b>
 * <p>
 * A {@code ChronoLocalDateTime} is the abstract representation of a local date-time
 * where the {@code Chronology chronology}, or calendar system, is pluggable.
 * The date-time is defined in terms of fields expressed by {@link TemporalField},
 * where most common implementations are defined in {@link ChronoField}.
 * The chronology defines how the calendar system operates and the meaning of
 * the standard fields.
 *
 * <h3>When to use this interface</h3>
 * The design of the API encourages the use of {@code LocalDateTime} rather than this
 * interface, even in the case where the application needs to deal with multiple
 * calendar systems. The rationale for this is explored in detail in {@link ChronoLocalDate}.
 * <p>
 * Ensure that the discussion in {@code ChronoLocalDate} has been read and understood
 * before using this interface.
 *
 * @implSpec
 * This interface must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * Subclasses should be Serializable wherever possible.
 *
 * <p>
 *  在任意年表中没有时区的日期时间,用于高级全球化用例。
 * <p>
 *  <b>大多数应用程序应该将方法签名,字段和变量声明为{@link LocalDateTime},而不是此接口。</b>
 * <p>
 *  {@code ChronoLocalDateTime}是本地日期时间的抽象表示,其中{@code Chronology chronology}或日历系统是可插入的。
 * 日期时间以由{@link TemporalField}表示的字段来定义,其中最常见的实现在{@link ChronoField}中定义。年表定义日历系统的操作方式和标准字段的含义。
 * 
 * <h3>何时使用此接口</h3> API的设计鼓励使用{@code LocalDateTime}而不是此接口,即使在应用程序需要处理多个日历系统的情况下。
 * 这一点的理由在{@link ChronoLocalDate}中详细探讨。
 * <p>
 *  确保在使用此界面之前已阅读并理解{@code ChronoLocalDate}中的讨论。
 * 
 *  @implSpec必须小心地实现此接口,以确保其他类正常运行。所有可以实例化的实现必须是final,immutable和线程安全的。子类应尽可能序列化。
 * 
 * 
 * @param <D> the concrete type for the date of this date-time
 * @since 1.8
 */
public interface ChronoLocalDateTime<D extends ChronoLocalDate>
        extends Temporal, TemporalAdjuster, Comparable<ChronoLocalDateTime<?>> {

    /**
     * Gets a comparator that compares {@code ChronoLocalDateTime} in
     * time-line order ignoring the chronology.
     * <p>
     * This comparator differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying date-time and not the chronology.
     * This allows dates in different calendar systems to be compared based
     * on the position of the date-time on the local time-line.
     * The underlying comparison is equivalent to comparing the epoch-day and nano-of-day.
     *
     * <p>
     *  获取一个比较器,比较{@code ChronoLocalDateTime}在时间线顺序忽略年表。
     * <p>
     *  此比较器与{@link #compareTo}中的比较不同,它仅比较基础日期时间而不是时间顺序。这允许基于本地时间线上的日期时间的位置来比较不同日历系统中的日期。潜在的比较等同于比较时代和纳米。
     * 
     * 
     * @return a comparator that compares in time-line order ignoring the chronology
     *
     * @see #isAfter
     * @see #isBefore
     * @see #isEqual
     */
    static Comparator<ChronoLocalDateTime<?>> timeLineOrder() {
        return AbstractChronology.DATE_TIME_ORDER;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ChronoLocalDateTime} from a temporal object.
     * <p>
     * This obtains a local date-time based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ChronoLocalDateTime}.
     * <p>
     * The conversion extracts and combines the chronology and the date-time
     * from the temporal object. The behavior is equivalent to using
     * {@link Chronology#localDateTime(TemporalAccessor)} with the extracted chronology.
     * Implementations are permitted to perform optimizations such as accessing
     * those fields that are equivalent to the relevant objects.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code ChronoLocalDateTime::from}.
     *
     * <p>
     *  从临时对象获取{@code ChronoLocalDateTime}的实例。
     * <p>
     *  这基于指定的时间获得本地日期时间。 {@code TemporalAccessor}表示一组任意的日期和时间信息,该工厂转换为{@code ChronoLocalDateTime}的实例。
     * <p>
     * 转换提取并组合来自时间对象的年表和日期时间。该行为等同于使用{@link Chronology#localDateTime(TemporalAccessor)}和提取的年表。
     * 实现允许执行优化,例如访问等价于相关对象的那些字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code ChronoLocalDateTime :: from}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the date-time, not null
     * @throws DateTimeException if unable to convert to a {@code ChronoLocalDateTime}
     * @see Chronology#localDateTime(TemporalAccessor)
     */
    static ChronoLocalDateTime<?> from(TemporalAccessor temporal) {
        if (temporal instanceof ChronoLocalDateTime) {
            return (ChronoLocalDateTime<?>) temporal;
        }
        Objects.requireNonNull(temporal, "temporal");
        Chronology chrono = temporal.query(TemporalQueries.chronology());
        if (chrono == null) {
            throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + temporal.getClass());
        }
        return chrono.localDateTime(temporal);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the chronology of this date-time.
     * <p>
     * The {@code Chronology} represents the calendar system in use.
     * The era and other fields in {@link ChronoField} are defined by the chronology.
     *
     * <p>
     *  获取此日期时间的年表。
     * <p>
     *  {@code Chronology}表示正在使用的日历系统。 {@link ChronoField}中的时代和其他字段由年表定义。
     * 
     * 
     * @return the chronology, not null
     */
    default Chronology getChronology() {
        return toLocalDate().getChronology();
    }

    /**
     * Gets the local date part of this date-time.
     * <p>
     * This returns a local date with the same year, month and day
     * as this date-time.
     *
     * <p>
     *  获取此日期时间的本地日期部分。
     * <p>
     *  这将返回与此日期时间相同的年,月和日的本地日期。
     * 
     * 
     * @return the date part of this date-time, not null
     */
    D toLocalDate() ;

    /**
     * Gets the local time part of this date-time.
     * <p>
     * This returns a local time with the same hour, minute, second and
     * nanosecond as this date-time.
     *
     * <p>
     *  获取此日期时间的本地时间部分。
     * <p>
     *  这将返回与此日期时间相同的小时,分​​钟,秒和纳秒的本地时间。
     * 
     * 
     * @return the time part of this date-time, not null
     */
    LocalTime toLocalTime();

    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if the specified field can be queried on this date-time.
     * If false, then calling the {@link #range(TemporalField) range},
     * {@link #get(TemporalField) get} and {@link #with(TemporalField, long)}
     * methods will throw an exception.
     * <p>
     * The set of supported fields is defined by the chronology and normally includes
     * all {@code ChronoField} date and time fields.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.isSupportedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the field is supported is determined by the field.
     *
     * <p>
     *  检查是否支持指定的字段。
     * <p>
     *  这将检查是否可以在此日期时间查询指定的字段。
     * 如果为false,则调用{@link #range(TemporalField)范围},{@link #get(TemporalField)get}和{@link #with(TemporalField,long)}
     * 方法将抛出异常。
     *  这将检查是否可以在此日期时间查询指定的字段。
     * <p>
     *  支持字段集由年表定义,通常包括所有{@code ChronoField}日期和时间字段。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作为
     * 参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field can be queried, false if not
     */
    @Override
    boolean isSupported(TemporalField field);

    /**
     * Checks if the specified unit is supported.
     * <p>
     * This checks if the specified unit can be added to or subtracted from this date-time.
     * If false, then calling the {@link #plus(long, TemporalUnit)} and
     * {@link #minus(long, TemporalUnit) minus} methods will throw an exception.
     * <p>
     * The set of supported units is defined by the chronology and normally includes
     * all {@code ChronoUnit} units except {@code FOREVER}.
     * <p>
     * If the unit is not a {@code ChronoUnit}, then the result of this method
     * is obtained by invoking {@code TemporalUnit.isSupportedBy(Temporal)}
     * passing {@code this} as the argument.
     * Whether the unit is supported is determined by the unit.
     *
     * <p>
     *  检查是否支持指定的单元。
     * <p>
     *  这将检查指定的单位是否可以添加到此日期时间或从此日期时间中减去。
     * 如果为false,则调用{@link #plus(long,TemporalUnit)}和{@link #minus(long,TemporalUnit)minus}方法将抛出异常。
     * <p>
     *  受支持单位的集合由年表定义,通常包括除{@code FOREVER}之外的所有{@code ChronoUnit}单位。
     * <p>
     *  如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.isSupportedBy(Temporal)}传递{@code this}作为参数来获得此方法的
     * 结果。
     * 单元是否受支持由单元确定。
     * 
     * 
     * @param unit  the unit to check, null returns false
     * @return true if the unit can be added/subtracted, false if not
     */
    @Override
    default boolean isSupported(TemporalUnit unit) {
        if (unit instanceof ChronoUnit) {
            return unit != FOREVER;
        }
        return unit != null && unit.isSupportedBy(this);
    }

    //-----------------------------------------------------------------------
    // override for covariant return type
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
    default ChronoLocalDateTime<D> with(TemporalAdjuster adjuster) {
        return ChronoLocalDateTimeImpl.ensureValid(getChronology(), Temporal.super.with(adjuster));
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
    ChronoLocalDateTime<D> with(TemporalField field, long newValue);

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
    default ChronoLocalDateTime<D> plus(TemporalAmount amount) {
        return ChronoLocalDateTimeImpl.ensureValid(getChronology(), Temporal.super.plus(amount));
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
    ChronoLocalDateTime<D> plus(long amountToAdd, TemporalUnit unit);

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
    default ChronoLocalDateTime<D> minus(TemporalAmount amount) {
        return ChronoLocalDateTimeImpl.ensureValid(getChronology(), Temporal.super.minus(amount));
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
    default ChronoLocalDateTime<D> minus(long amountToSubtract, TemporalUnit unit) {
        return ChronoLocalDateTimeImpl.ensureValid(getChronology(), Temporal.super.minus(amountToSubtract, unit));
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this date-time using the specified query.
     * <p>
     * This queries this date-time using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link java.time.temporal.TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此日期时间。
     * <p>
     *  这将使用指定的查询策略对象查询此日期时间。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
     * <p>
     *  此方法的结果是通过调用{@link java.time.temporal.TemporalQuery#queryFrom(TemporalAccessor)}方法对指定的查询传递{@code this}
     * 作为参数来获得的。
     * 
     * 
     * @param <R> the type of the result
     * @param query  the query to invoke, not null
     * @return the query result, null may be returned (defined by the query)
     * @throws DateTimeException if unable to query (defined by the query)
     * @throws ArithmeticException if numeric overflow occurs (defined by the query)
     */
    @SuppressWarnings("unchecked")
    @Override
    default <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.zoneId() || query == TemporalQueries.zone() || query == TemporalQueries.offset()) {
            return null;
        } else if (query == TemporalQueries.localTime()) {
            return (R) toLocalTime();
        } else if (query == TemporalQueries.chronology()) {
            return (R) getChronology();
        } else if (query == TemporalQueries.precision()) {
            return (R) NANOS;
        }
        // inline TemporalAccessor.super.query(query) as an optimization
        // non-JDK classes are not permitted to make this optimization
        return query.queryFrom(this);
    }

    /**
     * Adjusts the specified temporal object to have the same date and time as this object.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the date and time changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * twice, passing {@link ChronoField#EPOCH_DAY} and
     * {@link ChronoField#NANO_OF_DAY} as the fields.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisLocalDateTime.adjustInto(temporal);
     *   temporal = temporal.with(thisLocalDateTime);
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     * 将指定的时间对象调整为具有与此对象相同的日期和时间。
     * <p>
     *  这返回一个与输入相同的observable类型的时间对象,日期和时间更改为与此相同。
     * <p>
     *  该调整等同于使用{@link Temporal#with(TemporalField,long)}两次,传递{@link ChronoField#EPOCH_DAY}和{@link ChronoField#NANO_OF_DAY}
     * 作为字段。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐temporal = thisLocalDateTime.adjustInto(temporal); temporal = temporal.with(thisL
     * ocalDateTime);。
     * </pre>
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param temporal  the target object to be adjusted, not null
     * @return the adjusted object, not null
     * @throws DateTimeException if unable to make the adjustment
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    default Temporal adjustInto(Temporal temporal) {
        return temporal
                .with(EPOCH_DAY, toLocalDate().toEpochDay())
                .with(NANO_OF_DAY, toLocalTime().toNanoOfDay());
    }

    /**
     * Formats this date-time using the specified formatter.
     * <p>
     * This date-time will be passed to the formatter to produce a string.
     * <p>
     * The default implementation must behave as follows:
     * <pre>
     *  return formatter.format(this);
     * </pre>
     *
     * <p>
     *  使用指定的格式化程序格式化此日期时间。
     * <p>
     *  此日期时间将传递到格式化程序以生成字符串。
     * <p>
     *  默认实现必须如下所示：
     * <pre>
     *  return formatter.format(this);
     * </pre>
     * 
     * 
     * @param formatter  the formatter to use, not null
     * @return the formatted date-time string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    default String format(DateTimeFormatter formatter) {
        Objects.requireNonNull(formatter, "formatter");
        return formatter.format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Combines this time with a time-zone to create a {@code ChronoZonedDateTime}.
     * <p>
     * This returns a {@code ChronoZonedDateTime} formed from this date-time at the
     * specified time-zone. The result will match this date-time as closely as possible.
     * Time-zone rules, such as daylight savings, mean that not every local date-time
     * is valid for the specified zone, thus the local date-time may be adjusted.
     * <p>
     * The local date-time is resolved to a single instant on the time-line.
     * This is achieved by finding a valid offset from UTC/Greenwich for the local
     * date-time as defined by the {@link ZoneRules rules} of the zone ID.
     *<p>
     * In most cases, there is only one valid offset for a local date-time.
     * In the case of an overlap, where clocks are set back, there are two valid offsets.
     * This method uses the earlier offset typically corresponding to "summer".
     * <p>
     * In the case of a gap, where clocks jump forward, there is no valid offset.
     * Instead, the local date-time is adjusted to be later by the length of the gap.
     * For a typical one hour daylight savings change, the local date-time will be
     * moved one hour later into the offset typically corresponding to "summer".
     * <p>
     * To obtain the later offset during an overlap, call
     * {@link ChronoZonedDateTime#withLaterOffsetAtOverlap()} on the result of this method.
     *
     * <p>
     *  将此时间与时区相结合,创建{@code ChronoZonedDateTime}。
     * <p>
     *  这将返回从指定时区的此日期时间形成的{@code ChronoZonedDateTime}。结果将与该日期时间尽可能匹配。
     * 时区规则(例如夏令时)意味着并非每个本地日期时间对于指定区域有效,因此可以调整本地日期时间。
     * <p>
     * 本地日期时间在时间​​线上被解析为单个时刻。这是通过从区域ID的{@link ZoneRules规则}定义的本地日期 - 时间找到与UTC /格林威治的有效偏移来实现的。
     * p>
     *  在大多数情况下,本地日期时间只有一个有效偏移量。在重叠的情况下,其中时钟被回退,存在两个有效偏移。该方法使用通常对应于"夏天"的较早偏移。
     * <p>
     *  在间隙的情况下,其中时钟向前跳跃,没有有效的偏移。相反,本地日期时间稍后被调整为间隙的长度。对于典型的一小时夏令时变化,本地日期时间将在一小时后移动到通常对应于"夏季"的偏移量。
     * <p>
     *  要在重叠期间获取更晚的偏移量,请对此方法的结果调用{@link ChronoZonedDateTime#withLaterOffsetAtOverlap()}。
     * 
     * 
     * @param zone  the time-zone to use, not null
     * @return the zoned date-time formed from this date-time, not null
     */
    ChronoZonedDateTime<D> atZone(ZoneId zone);

    //-----------------------------------------------------------------------
    /**
     * Converts this date-time to an {@code Instant}.
     * <p>
     * This combines this local date-time and the specified offset to form
     * an {@code Instant}.
     * <p>
     * This default implementation calculates from the epoch-day of the date and the
     * second-of-day of the time.
     *
     * <p>
     *  将此日期时间转换为{@code Instant}。
     * <p>
     *  这会将此本地日期时间与指定的偏移量相结合,形成{@code Instant}。
     * <p>
     *  此默认实施方案从日期的时代日期和时间的第二天计算。
     * 
     * 
     * @param offset  the offset to use for the conversion, not null
     * @return an {@code Instant} representing the same instant, not null
     */
    default Instant toInstant(ZoneOffset offset) {
        return Instant.ofEpochSecond(toEpochSecond(offset), toLocalTime().getNano());
    }

    /**
     * Converts this date-time to the number of seconds from the epoch
     * of 1970-01-01T00:00:00Z.
     * <p>
     * This combines this local date-time and the specified offset to calculate the
     * epoch-second value, which is the number of elapsed seconds from 1970-01-01T00:00:00Z.
     * Instants on the time-line after the epoch are positive, earlier are negative.
     * <p>
     * This default implementation calculates from the epoch-day of the date and the
     * second-of-day of the time.
     *
     * <p>
     *  将此日期时间转换为从1970-01-01T00：00：00Z的时期的秒数。
     * <p>
     *  这将本地日期时间和指定的偏移量组合以计算时期秒值,其是从1970-01-01T00：00：00Z起经过的秒数。时代后的时间线上的实例为正,早期为负。
     * <p>
     * 此默认实施方案从日期的时代日期和时间的第二天计算。
     * 
     * 
     * @param offset  the offset to use for the conversion, not null
     * @return the number of seconds from the epoch of 1970-01-01T00:00:00Z
     */
    default long toEpochSecond(ZoneOffset offset) {
        Objects.requireNonNull(offset, "offset");
        long epochDay = toLocalDate().toEpochDay();
        long secs = epochDay * 86400 + toLocalTime().toSecondOfDay();
        secs -= offset.getTotalSeconds();
        return secs;
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this date-time to another date-time, including the chronology.
     * <p>
     * The comparison is based first on the underlying time-line date-time, then
     * on the chronology.
     * It is "consistent with equals", as defined by {@link Comparable}.
     * <p>
     * For example, the following is the comparator order:
     * <ol>
     * <li>{@code 2012-12-03T12:00 (ISO)}</li>
     * <li>{@code 2012-12-04T12:00 (ISO)}</li>
     * <li>{@code 2555-12-04T12:00 (ThaiBuddhist)}</li>
     * <li>{@code 2012-12-05T12:00 (ISO)}</li>
     * </ol>
     * Values #2 and #3 represent the same date-time on the time-line.
     * When two values represent the same date-time, the chronology ID is compared to distinguish them.
     * This step is needed to make the ordering "consistent with equals".
     * <p>
     * If all the date-time objects being compared are in the same chronology, then the
     * additional chronology stage is not required and only the local date-time is used.
     * <p>
     * This default implementation performs the comparison defined above.
     *
     * <p>
     *  将此日期时间与另一个日期时间(包括年表)进行比较。
     * <p>
     *  比较首先基于潜在的时间线日期时间,然后根据年表。它是"与等号一致",由{@link Comparable}定义。
     * <p>
     *  例如,以下是比较器顺序：
     * <ol>
     *  <li> {@ code 2012-12-03T12：00(ISO)} </li> <li> {@ code 2012-12-04T12：00(ISO)} </li> <li> {@ code 2555- 12-04T12：00(ThaiBuddhist)}
     *  </li> <li> {@ code 2012-12-05T12：00(ISO)} </li>。
     * </ol>
     *  值#2和#3表示时间线上的相同日期时间。当两个值表示相同的日期时间时,比较年代ID以区分它们。需要此步骤使排序"与等号一致"。
     * <p>
     *  如果所比较的所有日期时间对象都在相同的年表中,则不需要附加的年表,而仅使用本地日期时间。
     * <p>
     *  此默认实现执行上面定义的比较。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    default int compareTo(ChronoLocalDateTime<?> other) {
        int cmp = toLocalDate().compareTo(other.toLocalDate());
        if (cmp == 0) {
            cmp = toLocalTime().compareTo(other.toLocalTime());
            if (cmp == 0) {
                cmp = getChronology().compareTo(other.getChronology());
            }
        }
        return cmp;
    }

    /**
     * Checks if this date-time is after the specified date-time ignoring the chronology.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying date-time and not the chronology.
     * This allows dates in different calendar systems to be compared based
     * on the time-line position.
     * <p>
     * This default implementation performs the comparison based on the epoch-day
     * and nano-of-day.
     *
     * <p>
     *  检查此日期时间是否晚于指定的日期时间,而忽略年表。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较基础日期时间而不是时间顺序。这允许基于时间线位置来比较不同日历系统中的日期。
     * <p>
     *  此默认实现基于时代和纳秒进行比较。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if this is after the specified date-time
     */
    default boolean isAfter(ChronoLocalDateTime<?> other) {
        long thisEpDay = this.toLocalDate().toEpochDay();
        long otherEpDay = other.toLocalDate().toEpochDay();
        return thisEpDay > otherEpDay ||
            (thisEpDay == otherEpDay && this.toLocalTime().toNanoOfDay() > other.toLocalTime().toNanoOfDay());
    }

    /**
     * Checks if this date-time is before the specified date-time ignoring the chronology.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying date-time and not the chronology.
     * This allows dates in different calendar systems to be compared based
     * on the time-line position.
     * <p>
     * This default implementation performs the comparison based on the epoch-day
     * and nano-of-day.
     *
     * <p>
     * 检查此日期时间是否在指定的日期时间之前,而忽略年表。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较基础日期时间而不是时间顺序。这允许基于时间线位置来比较不同日历系统中的日期。
     * <p>
     *  此默认实现基于时代和纳秒进行比较。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if this is before the specified date-time
     */
    default boolean isBefore(ChronoLocalDateTime<?> other) {
        long thisEpDay = this.toLocalDate().toEpochDay();
        long otherEpDay = other.toLocalDate().toEpochDay();
        return thisEpDay < otherEpDay ||
            (thisEpDay == otherEpDay && this.toLocalTime().toNanoOfDay() < other.toLocalTime().toNanoOfDay());
    }

    /**
     * Checks if this date-time is equal to the specified date-time ignoring the chronology.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying date and time and not the chronology.
     * This allows date-times in different calendar systems to be compared based
     * on the time-line position.
     * <p>
     * This default implementation performs the comparison based on the epoch-day
     * and nano-of-day.
     *
     * <p>
     *  检查此日期时间是否等于指定的日期时间,忽略年表。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较基础日期和时间,而不是时间顺序。这允许基于时间线位置来比较不同日历系统中的日期时间。
     * <p>
     *  此默认实现基于时代和纳秒进行比较。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if the underlying date-time is equal to the specified date-time on the timeline
     */
    default boolean isEqual(ChronoLocalDateTime<?> other) {
        // Do the time check first, it is cheaper than computing EPOCH day.
        return this.toLocalTime().toNanoOfDay() == other.toLocalTime().toNanoOfDay() &&
               this.toLocalDate().toEpochDay() == other.toLocalDate().toEpochDay();
    }

    /**
     * Checks if this date-time is equal to another date-time, including the chronology.
     * <p>
     * Compares this date-time with another ensuring that the date-time and chronology are the same.
     *
     * <p>
     *  检查此日期时间是否等于另一个日期时间,包括年表。
     * <p>
     *  将此日期时间与另一个日期时间进行比较,确保日期时间和年表相同。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other date
     */
    @Override
    boolean equals(Object obj);

    /**
     * A hash code for this date-time.
     *
     * <p>
     *  此日期时间的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    int hashCode();

    //-----------------------------------------------------------------------
    /**
     * Outputs this date-time as a {@code String}.
     * <p>
     * The output will include the full local date-time.
     *
     * <p>
     *  将此日期时间输出为{@code String}。
     * <p>
     * 
     * @return a string representation of this date-time, not null
     */
    @Override
    String toString();

}
