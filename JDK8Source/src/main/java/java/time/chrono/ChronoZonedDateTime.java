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

import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;
import static java.time.temporal.ChronoUnit.FOREVER;
import static java.time.temporal.ChronoUnit.NANOS;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Comparator;
import java.util.Objects;

/**
 * A date-time with a time-zone in an arbitrary chronology,
 * intended for advanced globalization use cases.
 * <p>
 * <b>Most applications should declare method signatures, fields and variables
 * as {@link ZonedDateTime}, not this interface.</b>
 * <p>
 * A {@code ChronoZonedDateTime} is the abstract representation of an offset date-time
 * where the {@code Chronology chronology}, or calendar system, is pluggable.
 * The date-time is defined in terms of fields expressed by {@link TemporalField},
 * where most common implementations are defined in {@link ChronoField}.
 * The chronology defines how the calendar system operates and the meaning of
 * the standard fields.
 *
 * <h3>When to use this interface</h3>
 * The design of the API encourages the use of {@code ZonedDateTime} rather than this
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
 *  带有任意时间表中的时区的日期时间,用于高级全球化用例。
 * <p>
 *  <b>大多数应用程序应该将方法签名,字段和变量声明为{@link ZonedDateTime},而不是此接口。</b>
 * <p>
 *  {@code ChronoZonedDateTime}是偏移日期时间的抽象表示,其中{@code Chronology chronology}或日历系统是可插入的。
 * 日期时间以由{@link TemporalField}表示的字段来定义,其中最常见的实现在{@link ChronoField}中定义。年表定义日历系统的操作方式和标准字段的含义。
 * 
 * <h3>何时使用此接口</h3> API的设计鼓励使用{@code ZonedDateTime}而不是此接口,即使在应用程序需要处理多个日历系统的情况下。
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
public interface ChronoZonedDateTime<D extends ChronoLocalDate>
        extends Temporal, Comparable<ChronoZonedDateTime<?>> {

    /**
     * Gets a comparator that compares {@code ChronoZonedDateTime} in
     * time-line order ignoring the chronology.
     * <p>
     * This comparator differs from the comparison in {@link #compareTo} in that it
     * only compares the underlying instant and not the chronology.
     * This allows dates in different calendar systems to be compared based
     * on the position of the date-time on the instant time-line.
     * The underlying comparison is equivalent to comparing the epoch-second and nano-of-second.
     *
     * <p>
     *  获取一个比较器,按时间线顺序比较{@code ChronoZonedDateTime}忽略时间顺序。
     * <p>
     *  此比较器与{@link #compareTo}中的比较不同,它仅比较基础即时而不是时间顺序。这允许基于在时间线上的日期时间的位置来比较不同日历系统中的日期。潜在的比较等同于比较时代秒和纳秒。
     * 
     * 
     * @return a comparator that compares in time-line order ignoring the chronology
     *
     * @see #isAfter
     * @see #isBefore
     * @see #isEqual
     */
    static Comparator<ChronoZonedDateTime<?>> timeLineOrder() {
        return AbstractChronology.INSTANT_ORDER;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code ChronoZonedDateTime} from a temporal object.
     * <p>
     * This creates a zoned date-time based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code ChronoZonedDateTime}.
     * <p>
     * The conversion extracts and combines the chronology, date, time and zone
     * from the temporal object. The behavior is equivalent to using
     * {@link Chronology#zonedDateTime(TemporalAccessor)} with the extracted chronology.
     * Implementations are permitted to perform optimizations such as accessing
     * those fields that are equivalent to the relevant objects.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code ChronoZonedDateTime::from}.
     *
     * <p>
     *  从临时对象获取{@code ChronoZonedDateTime}的实例。
     * <p>
     *  这将基于指定的时间创建一个分区的日期时间。
     *  {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code ChronoZonedDateTime}的实例。
     * <p>
     * 转换从时间对象提取并组合年表,日期,时间和区域。该行为等同于使用{@link Chronology#zonedDateTime(TemporalAccessor)}和提取的年表。
     * 实现允许执行优化,例如访问等价于相关对象的那些字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code ChronoZonedDateTime :: from}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the date-time, not null
     * @throws DateTimeException if unable to convert to a {@code ChronoZonedDateTime}
     * @see Chronology#zonedDateTime(TemporalAccessor)
     */
    static ChronoZonedDateTime<?> from(TemporalAccessor temporal) {
        if (temporal instanceof ChronoZonedDateTime) {
            return (ChronoZonedDateTime<?>) temporal;
        }
        Objects.requireNonNull(temporal, "temporal");
        Chronology chrono = temporal.query(TemporalQueries.chronology());
        if (chrono == null) {
            throw new DateTimeException("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + temporal.getClass());
        }
        return chrono.zonedDateTime(temporal);
    }

    //-----------------------------------------------------------------------
    @Override
    default ValueRange range(TemporalField field) {
        if (field instanceof ChronoField) {
            if (field == INSTANT_SECONDS || field == OFFSET_SECONDS) {
                return field.range();
            }
            return toLocalDateTime().range(field);
        }
        return field.rangeRefinedBy(this);
    }

    @Override
    default int get(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case INSTANT_SECONDS:
                    throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
                case OFFSET_SECONDS:
                    return getOffset().getTotalSeconds();
            }
            return toLocalDateTime().get(field);
        }
        return Temporal.super.get(field);
    }

    @Override
    default long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case INSTANT_SECONDS: return toEpochSecond();
                case OFFSET_SECONDS: return getOffset().getTotalSeconds();
            }
            return toLocalDateTime().getLong(field);
        }
        return field.getFrom(this);
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
    default D toLocalDate() {
        return toLocalDateTime().toLocalDate();
    }

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
    default LocalTime toLocalTime() {
        return toLocalDateTime().toLocalTime();
    }

    /**
     * Gets the local date-time part of this date-time.
     * <p>
     * This returns a local date with the same year, month and day
     * as this date-time.
     *
     * <p>
     *  获取此日期时间的本地日期时间部分。
     * <p>
     *  这将返回与此日期时间相同的年,月和日的本地日期。
     * 
     * 
     * @return the local date-time part of this date-time, not null
     */
    ChronoLocalDateTime<D> toLocalDateTime();

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
     * Gets the zone offset, such as '+01:00'.
     * <p>
     * This is the offset of the local date-time from UTC/Greenwich.
     *
     * <p>
     *  获取区域偏移量,例如"+01：00"。
     * <p>
     *  这是本地日期时间与UTC /格林威治的偏移量。
     * 
     * 
     * @return the zone offset, not null
     */
    ZoneOffset getOffset();

    /**
     * Gets the zone ID, such as 'Europe/Paris'.
     * <p>
     * This returns the stored time-zone id used to determine the time-zone rules.
     *
     * <p>
     *  获取区域ID,例如"Europe / Paris"。
     * <p>
     *  这返回用于确定时区规则的存储的时区标识。
     * 
     * 
     * @return the zone ID, not null
     */
    ZoneId getZone();

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this date-time changing the zone offset to the
     * earlier of the two valid offsets at a local time-line overlap.
     * <p>
     * This method only has any effect when the local time-line overlaps, such as
     * at an autumn daylight savings cutover. In this scenario, there are two
     * valid offsets for the local date-time. Calling this method will return
     * a zoned date-time with the earlier of the two selected.
     * <p>
     * If this method is called when it is not an overlap, {@code this}
     * is returned.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期时间的副本,将区域偏移更改为本地时间线重叠处的两个有效偏移量中的较早者。
     * <p>
     * 该方法仅在本地时间线重叠时(例如在秋季日光节约割接处)具有任何效果。在这种情况下,本地日期时间有两个有效偏移量。调用此方法将返回一个分区日期时间与所选的两个较早的日期时间。
     * <p>
     *  如果此方法在不重叠时调用,则返回{@code this}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return a {@code ZoneChronoDateTime} based on this date-time with the earlier offset, not null
     * @throws DateTimeException if no rules can be found for the zone
     * @throws DateTimeException if no rules are valid for this date-time
     */
    ChronoZonedDateTime<D> withEarlierOffsetAtOverlap();

    /**
     * Returns a copy of this date-time changing the zone offset to the
     * later of the two valid offsets at a local time-line overlap.
     * <p>
     * This method only has any effect when the local time-line overlaps, such as
     * at an autumn daylight savings cutover. In this scenario, there are two
     * valid offsets for the local date-time. Calling this method will return
     * a zoned date-time with the later of the two selected.
     * <p>
     * If this method is called when it is not an overlap, {@code this}
     * is returned.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此日期时间的副本,将区域偏移更改为本地时间线重叠处的两个有效偏移中的较晚者。
     * <p>
     *  该方法仅在本地时间线重叠时(例如在秋季日光节约割接处)具有任何效果。在这种情况下,本地日期时间有两个有效偏移量。调用此方法将返回一个分区的日期时间,后两个选择的日期时间。
     * <p>
     *  如果此方法在不重叠时调用,则返回{@code this}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @return a {@code ChronoZonedDateTime} based on this date-time with the later offset, not null
     * @throws DateTimeException if no rules can be found for the zone
     * @throws DateTimeException if no rules are valid for this date-time
     */
    ChronoZonedDateTime<D> withLaterOffsetAtOverlap();

    /**
     * Returns a copy of this ZonedDateTime with a different time-zone,
     * retaining the local date-time if possible.
     * <p>
     * This method changes the time-zone and retains the local date-time.
     * The local date-time is only changed if it is invalid for the new zone.
     * <p>
     * To change the zone and adjust the local date-time,
     * use {@link #withZoneSameInstant(ZoneId)}.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  以不同的时区返回此ZonedDateTime的副本,如果可能,保留本地日期时间。
     * <p>
     *  此方法更改时区并保留本地日期时间。仅当本地日期时间对新区域无效时,才更改本地日期时间。
     * <p>
     *  要更改区域并调整本地日期时间,请使用{@link #withZoneSameInstant(ZoneId)}。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param zone  the time-zone to change to, not null
     * @return a {@code ChronoZonedDateTime} based on this date-time with the requested zone, not null
     */
    ChronoZonedDateTime<D> withZoneSameLocal(ZoneId zone);

    /**
     * Returns a copy of this date-time with a different time-zone,
     * retaining the instant.
     * <p>
     * This method changes the time-zone and retains the instant.
     * This normally results in a change to the local date-time.
     * <p>
     * This method is based on retaining the same instant, thus gaps and overlaps
     * in the local time-line have no effect on the result.
     * <p>
     * To change the offset while keeping the local time,
     * use {@link #withZoneSameLocal(ZoneId)}.
     *
     * <p>
     *  返回此日期时间的副本,使用不同的时区,保留即时。
     * <p>
     * 此方法更改时区并保留即时。这通常导致对本地日期时间的更改。
     * <p>
     *  该方法基于保持相同的瞬时,因此在本地时间线中的间隙和重叠对结果没有影响。
     * <p>
     *  要在保持本地时间的情况下更改偏移,请使用{@link #withZoneSameLocal(ZoneId)}。
     * 
     * 
     * @param zone  the time-zone to change to, not null
     * @return a {@code ChronoZonedDateTime} based on this date-time with the requested zone, not null
     * @throws DateTimeException if the result exceeds the supported date range
     */
    ChronoZonedDateTime<D> withZoneSameInstant(ZoneId zone);

    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if the specified field can be queried on this date-time.
     * If false, then calling the {@link #range(TemporalField) range},
     * {@link #get(TemporalField) get} and {@link #with(TemporalField, long)}
     * methods will throw an exception.
     * <p>
     * The set of supported fields is defined by the chronology and normally includes
     * all {@code ChronoField} fields.
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
     *  支持字段集由年表定义,通常包括所有{@code ChronoField}字段。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
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
     * 如果单元不是{@code ChronoUnit},那么通过调用{@code TemporalUnit.isSupportedBy(Temporal)}传递{@code this}作为参数来获得此方法的结
     * 果。
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
    default ChronoZonedDateTime<D> with(TemporalAdjuster adjuster) {
        return ChronoZonedDateTimeImpl.ensureValid(getChronology(), Temporal.super.with(adjuster));
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
    ChronoZonedDateTime<D> with(TemporalField field, long newValue);

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
    default ChronoZonedDateTime<D> plus(TemporalAmount amount) {
        return ChronoZonedDateTimeImpl.ensureValid(getChronology(), Temporal.super.plus(amount));
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
    ChronoZonedDateTime<D> plus(long amountToAdd, TemporalUnit unit);

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
    default ChronoZonedDateTime<D> minus(TemporalAmount amount) {
        return ChronoZonedDateTimeImpl.ensureValid(getChronology(), Temporal.super.minus(amount));
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
    default ChronoZonedDateTime<D> minus(long amountToSubtract, TemporalUnit unit) {
        return ChronoZonedDateTimeImpl.ensureValid(getChronology(), Temporal.super.minus(amountToSubtract, unit));
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
        if (query == TemporalQueries.zone() || query == TemporalQueries.zoneId()) {
            return (R) getZone();
        } else if (query == TemporalQueries.offset()) {
            return (R) getOffset();
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
     * Converts this date-time to an {@code Instant}.
     * <p>
     * This returns an {@code Instant} representing the same point on the
     * time-line as this date-time. The calculation combines the
     * {@linkplain #toLocalDateTime() local date-time} and
     * {@linkplain #getOffset() offset}.
     *
     * <p>
     *  将此日期时间转换为{@code Instant}。
     * <p>
     *  这会返回代表与此日期时间相同的时间线上的相同点的{@code Instant}。
     * 计算结合{@linkplain #toLocalDateTime()local date-time}和{@linkplain #getOffset()offset}。
     * 
     * 
     * @return an {@code Instant} representing the same instant, not null
     */
    default Instant toInstant() {
        return Instant.ofEpochSecond(toEpochSecond(), toLocalTime().getNano());
    }

    /**
     * Converts this date-time to the number of seconds from the epoch
     * of 1970-01-01T00:00:00Z.
     * <p>
     * This uses the {@linkplain #toLocalDateTime() local date-time} and
     * {@linkplain #getOffset() offset} to calculate the epoch-second value,
     * which is the number of elapsed seconds from 1970-01-01T00:00:00Z.
     * Instants on the time-line after the epoch are positive, earlier are negative.
     *
     * <p>
     *  将此日期时间转换为从1970-01-01T00：00：00Z的时期的秒数。
     * <p>
     * 这使用{@linkplain #toLocalDateTime()local date-time}和{@linkplain #getOffset()offset}来计算epoch-second值,即从1
     * 970-01-01T00：00：00Z起经过的秒数。
     * 时代后的时间线上的实例为正,早期为负。
     * 
     * 
     * @return the number of seconds from the epoch of 1970-01-01T00:00:00Z
     */
    default long toEpochSecond() {
        long epochDay = toLocalDate().toEpochDay();
        long secs = epochDay * 86400 + toLocalTime().toSecondOfDay();
        secs -= getOffset().getTotalSeconds();
        return secs;
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this date-time to another date-time, including the chronology.
     * <p>
     * The comparison is based first on the instant, then on the local date-time,
     * then on the zone ID, then on the chronology.
     * It is "consistent with equals", as defined by {@link Comparable}.
     * <p>
     * If all the date-time objects being compared are in the same chronology, then the
     * additional chronology stage is not required.
     * <p>
     * This default implementation performs the comparison defined above.
     *
     * <p>
     *  将此日期时间与另一个日期时间(包括年表)进行比较。
     * <p>
     *  比较首先基于时间,然后基于本地日期时间,然后基于区域ID,然后基于年表。它是"与等号一致",由{@link Comparable}定义。
     * <p>
     *  如果所比较的所有日期时间对象在相同的年表中,则不需要附加的年表。
     * <p>
     *  此默认实现执行上面定义的比较。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    default int compareTo(ChronoZonedDateTime<?> other) {
        int cmp = Long.compare(toEpochSecond(), other.toEpochSecond());
        if (cmp == 0) {
            cmp = toLocalTime().getNano() - other.toLocalTime().getNano();
            if (cmp == 0) {
                cmp = toLocalDateTime().compareTo(other.toLocalDateTime());
                if (cmp == 0) {
                    cmp = getZone().getId().compareTo(other.getZone().getId());
                    if (cmp == 0) {
                        cmp = getChronology().compareTo(other.getChronology());
                    }
                }
            }
        }
        return cmp;
    }

    /**
     * Checks if the instant of this date-time is before that of the specified date-time.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the instant of the date-time. This is equivalent to using
     * {@code dateTime1.toInstant().isBefore(dateTime2.toInstant());}.
     * <p>
     * This default implementation performs the comparison based on the epoch-second
     * and nano-of-second.
     *
     * <p>
     *  检查此日期时间的时间是否在指定日期时间的时间之前。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较日期时间的时间。这相当于使用{@code dateTime1.toInstant()。
     * isBefore(dateTime2.toInstant());}。
     * <p>
     *  此默认实现执行基于时代秒和纳秒的比较。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if this point is before the specified date-time
     */
    default boolean isBefore(ChronoZonedDateTime<?> other) {
        long thisEpochSec = toEpochSecond();
        long otherEpochSec = other.toEpochSecond();
        return thisEpochSec < otherEpochSec ||
            (thisEpochSec == otherEpochSec && toLocalTime().getNano() < other.toLocalTime().getNano());
    }

    /**
     * Checks if the instant of this date-time is after that of the specified date-time.
     * <p>
     * This method differs from the comparison in {@link #compareTo} in that it
     * only compares the instant of the date-time. This is equivalent to using
     * {@code dateTime1.toInstant().isAfter(dateTime2.toInstant());}.
     * <p>
     * This default implementation performs the comparison based on the epoch-second
     * and nano-of-second.
     *
     * <p>
     *  检查此日期时间的时间是否晚于指定的日期时间。
     * <p>
     *  此方法与{@link #compareTo}中的比较不同,它仅比较日期时间的时间。这相当于使用{@code dateTime1.toInstant()。
     * isAfter(dateTime2.toInstant());}。
     * <p>
     * 此默认实现执行基于时代秒和纳秒的比较。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if this is after the specified date-time
     */
    default boolean isAfter(ChronoZonedDateTime<?> other) {
        long thisEpochSec = toEpochSecond();
        long otherEpochSec = other.toEpochSecond();
        return thisEpochSec > otherEpochSec ||
            (thisEpochSec == otherEpochSec && toLocalTime().getNano() > other.toLocalTime().getNano());
    }

    /**
     * Checks if the instant of this date-time is equal to that of the specified date-time.
     * <p>
     * This method differs from the comparison in {@link #compareTo} and {@link #equals}
     * in that it only compares the instant of the date-time. This is equivalent to using
     * {@code dateTime1.toInstant().equals(dateTime2.toInstant());}.
     * <p>
     * This default implementation performs the comparison based on the epoch-second
     * and nano-of-second.
     *
     * <p>
     *  检查此日期时间的时间是否等于指定日期时间的时间。
     * <p>
     *  此方法与{@link #compareTo}和{@link #equals}中的比较不同,它仅比较日期时间的时间。这相当于使用{@code dateTime1.toInstant()。
     * equals(dateTime2.toInstant());}。
     * <p>
     *  此默认实现执行基于时代秒和纳秒的比较。
     * 
     * 
     * @param other  the other date-time to compare to, not null
     * @return true if the instant equals the instant of the specified date-time
     */
    default boolean isEqual(ChronoZonedDateTime<?> other) {
        return toEpochSecond() == other.toEpochSecond() &&
                toLocalTime().getNano() == other.toLocalTime().getNano();
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this date-time is equal to another date-time.
     * <p>
     * The comparison is based on the offset date-time and the zone.
     * To compare for the same instant on the time-line, use {@link #compareTo}.
     * Only objects of type {@code ChronoZonedDateTime} are compared, other types return false.
     *
     * <p>
     *  检查此日期时间是否等于另一个日期时间。
     * <p>
     *  比较基于偏移日期时间和区域。要在时间线上的同一时刻进行比较,请使用{@link #compareTo}。
     * 仅比较{@code ChronoZonedDateTime}类型的对象,其他类型返回false。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other date-time
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
     * The output will include the full zoned date-time.
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
