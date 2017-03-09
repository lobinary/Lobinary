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
package java.time.temporal;

import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.Chronology;

/**
 * Common implementations of {@code TemporalQuery}.
 * <p>
 * This class provides common implementations of {@link TemporalQuery}.
 * These are defined here as they must be constants, and the definition
 * of lambdas does not guarantee that. By assigning them once here,
 * they become 'normal' Java constants.
 * <p>
 * Queries are a key tool for extracting information from temporal objects.
 * They exist to externalize the process of querying, permitting different
 * approaches, as per the strategy design pattern.
 * Examples might be a query that checks if the date is the day before February 29th
 * in a leap year, or calculates the number of days to your next birthday.
 * <p>
 * The {@link TemporalField} interface provides another mechanism for querying
 * temporal objects. That interface is limited to returning a {@code long}.
 * By contrast, queries can return any type.
 * <p>
 * There are two equivalent ways of using a {@code TemporalQuery}.
 * The first is to invoke the method on this interface directly.
 * The second is to use {@link TemporalAccessor#query(TemporalQuery)}:
 * <pre>
 *   // these two lines are equivalent, but the second approach is recommended
 *   temporal = thisQuery.queryFrom(temporal);
 *   temporal = temporal.query(thisQuery);
 * </pre>
 * It is recommended to use the second approach, {@code query(TemporalQuery)},
 * as it is a lot clearer to read in code.
 * <p>
 * The most common implementations are method references, such as
 * {@code LocalDate::from} and {@code ZoneId::from}.
 * Additional common queries are provided to return:
 * <ul>
 * <li> a Chronology,
 * <li> a LocalDate,
 * <li> a LocalTime,
 * <li> a ZoneOffset,
 * <li> a precision,
 * <li> a zone, or
 * <li> a zoneId.
 * </ul>
 *
 * <p>
 *  {@code TemporalQuery}的常见实现。
 * <p>
 *  这个类提供了{@link TemporalQuery}的常见实现。这些在这里被定义,因为它们必须是常量,并且lambdas的定义不保证。通过在这里分配它们,它们变成"正常"Java常量。
 * <p>
 *  查询是从时间对象中提取信息的关键工具。它们存在于外部化查询的过程,允许不同的方法,根据策略设计模式。示例可以是检查日期是否为闰年中2月29日前一天的查询,或计算下一个生日的天数。
 * <p>
 * {@link TemporalField}接口提供了另一种查询时态对象的机制。该界面仅限于返回{@code long}。相比之下,查询可以返回任何类型。
 * <p>
 *  有两种使用{@code TemporalQuery}的等效方式。第一种是直接在此接口上调用方法。
 * 第二个是使用{@link TemporalAccessor#query(TemporalQuery)}：。
 * <pre>
 *  //这两行是等价的,但第二种方法是推荐temporal = thisQuery.queryFrom(temporal); temporal = temporal.query(thisQuery);
 * </pre>
 *  建议使用第二种方法{@code query(TemporalQuery)},因为它在代码中更清晰。
 * <p>
 *  最常见的实现是方法引用,例如{@code LocalDate :: from}和{@code ZoneId :: from}。提供了其他常见查询以返回：
 * <ul>
 *  <li>年表,<li> LocalDate,<li> LocalTime,<li> ZoneOffset,<li>精确度,<li>区域或<li> zoneId。
 * </ul>
 * 
 * 
 * @since 1.8
 */
public final class TemporalQueries {
    // note that it is vital that each method supplies a constant, not a
    // calculated value, as they will be checked for using ==
    // it is also vital that each constant is different (due to the == checking)
    // as such, alterations to this code must be done with care

    /**
     * Private constructor since this is a utility class.
     * <p>
     *  私人构造函数,因为这是一个实用程序类。
     * 
     */
    private TemporalQueries() {
    }

    //-----------------------------------------------------------------------
    // special constants should be used to extract information from a TemporalAccessor
    // that cannot be derived in other ways
    // Javadoc added here, so as to pretend they are more normal than they really are

    /**
     * A strict query for the {@code ZoneId}.
     * <p>
     * This queries a {@code TemporalAccessor} for the zone.
     * The zone is only returned if the date-time conceptually contains a {@code ZoneId}.
     * It will not be returned if the date-time only conceptually has an {@code ZoneOffset}.
     * Thus a {@link java.time.ZonedDateTime} will return the result of {@code getZone()},
     * but an {@link java.time.OffsetDateTime} will return null.
     * <p>
     * In most cases, applications should use {@link #zone()} as this query is too strict.
     * <p>
     * The result from JDK classes implementing {@code TemporalAccessor} is as follows:<br>
     * {@code LocalDate} returns null<br>
     * {@code LocalTime} returns null<br>
     * {@code LocalDateTime} returns null<br>
     * {@code ZonedDateTime} returns the associated zone<br>
     * {@code OffsetTime} returns null<br>
     * {@code OffsetDateTime} returns null<br>
     * {@code ChronoLocalDate} returns null<br>
     * {@code ChronoLocalDateTime} returns null<br>
     * {@code ChronoZonedDateTime} returns the associated zone<br>
     * {@code Era} returns null<br>
     * {@code DayOfWeek} returns null<br>
     * {@code Month} returns null<br>
     * {@code Year} returns null<br>
     * {@code YearMonth} returns null<br>
     * {@code MonthDay} returns null<br>
     * {@code ZoneOffset} returns null<br>
     * {@code Instant} returns null<br>
     *
     * <p>
     *  严格查询{@code ZoneId}。
     * <p>
     *  这将查询区域的{@code TemporalAccessor}。如果日期时间在概念上包含{@code ZoneId},则只返回区域。
     * 如果日期时间在概念上只有{@code ZoneOffset},则不会返回。
     * 因此,{@link java.time.ZonedDateTime}将返回{@code getZone()}的结果,但{@link java.time.OffsetDateTime}将返回null。
     * <p>
     *  在大多数情况下,应用程序应使用{@link #zone()},因为此查询过于严格。
     * <p>
     * 实现{@code TemporalAccessor}的JDK类的结果如下：<br> {@code LocalDate}返回null <br> {@code LocalTime}返回null <br> {@code LocalDateTime}
     * 返回null <br> {@代码ZonedDateTime}返回关联区域<br> {@code OffsetTime}返回null <br> {@code OffsetDateTime}返回null <br>
     *  {@code ChronoLocalDate}返回null <br> {@code ChronoLocalDateTime}返回null <br> {@code ChronoZonedDateTime}
     * 返回关联区域<br> {@code Era}返回null <br> {@code DayOfWeek}返回null <br> {@code Month}返回null <br> {@code Year}返
     * 回null < br> {@code YearMonth}返回null <br> {@code MonthDay}返回null <br> {@code ZoneOffset}返回null <br> {@code Instant}
     * 返回null <br>。
     * 
     * 
     * @return a query that can obtain the zone ID of a temporal, not null
     */
    public static TemporalQuery<ZoneId> zoneId() {
        return TemporalQueries.ZONE_ID;
    }

    /**
     * A query for the {@code Chronology}.
     * <p>
     * This queries a {@code TemporalAccessor} for the chronology.
     * If the target {@code TemporalAccessor} represents a date, or part of a date,
     * then it should return the chronology that the date is expressed in.
     * As a result of this definition, objects only representing time, such as
     * {@code LocalTime}, will return null.
     * <p>
     * The result from JDK classes implementing {@code TemporalAccessor} is as follows:<br>
     * {@code LocalDate} returns {@code IsoChronology.INSTANCE}<br>
     * {@code LocalTime} returns null (does not represent a date)<br>
     * {@code LocalDateTime} returns {@code IsoChronology.INSTANCE}<br>
     * {@code ZonedDateTime} returns {@code IsoChronology.INSTANCE}<br>
     * {@code OffsetTime} returns null (does not represent a date)<br>
     * {@code OffsetDateTime} returns {@code IsoChronology.INSTANCE}<br>
     * {@code ChronoLocalDate} returns the associated chronology<br>
     * {@code ChronoLocalDateTime} returns the associated chronology<br>
     * {@code ChronoZonedDateTime} returns the associated chronology<br>
     * {@code Era} returns the associated chronology<br>
     * {@code DayOfWeek} returns null (shared across chronologies)<br>
     * {@code Month} returns {@code IsoChronology.INSTANCE}<br>
     * {@code Year} returns {@code IsoChronology.INSTANCE}<br>
     * {@code YearMonth} returns {@code IsoChronology.INSTANCE}<br>
     * {@code MonthDay} returns null {@code IsoChronology.INSTANCE}<br>
     * {@code ZoneOffset} returns null (does not represent a date)<br>
     * {@code Instant} returns null (does not represent a date)<br>
     * <p>
     * The method {@link java.time.chrono.Chronology#from(TemporalAccessor)} can be used as a
     * {@code TemporalQuery} via a method reference, {@code Chronology::from}.
     * That method is equivalent to this query, except that it throws an
     * exception if a chronology cannot be obtained.
     *
     * <p>
     *  查询{@code Chronology}。
     * <p>
     *  这将查询一个{@code TemporalAccessor}的年表。如果目标{@code TemporalAccessor}表示日期或日期的一部分,则它应该返回日期表示的年表。
     * 作为该定义的结果,仅表示时间的对象,例如{@code LocalTime} ,将返回null。
     * <p>
     * 来自实现{@code TemporalAccessor}的JDK类的结果如下：<br> {@code LocalDate}返回{@code IsoChronology.INSTANCE} <br> {@code LocalTime}
     * 返回null(不表示日期)<br > {@code LocalDateTime}返回{@code IsoChronology.INSTANCE} <br> {@code ZonedDateTime}返回
     * {@code IsoChronology.INSTANCE} <br> {@code OffsetTime}返回null(不表示日期)<br> {@code OffsetDateTime}返回{@code IsoChronology.INSTANCE}
     *  <br> {@code ChronoLocalDate}返回关联的时间顺序<br> {@code ChronoLocalDateTime}返回关联的时间顺序<br> {@code ChronoZonedDateTime}
     * 返回关联的年表< br> {@code Era}返回关联的时间顺序<br> {@code DayOfWeek}返回null(跨时间共享)<br> {@code Month}返回{@code IsoChronology.INSTANCE}
     *  <br> {@code Year}返回{@code IsoChronology.INSTANCE} <br> {@code YearMonth}返回{@code IsoStronology.INSTANCE}
     *  <br> {@code MonthDay}返回null {@code IsoStronology.INSTANCE} <br> {@code ZoneOffset} null(不表示日期)<br> {@code Instant}
     * 返回null(不表示日期)<br>。
     * <p>
     *  方法{@link java.time.chrono.Chronology#from(TemporalAccessor)}可以通过方法引用{@code Chronology :: from}用作{@code TemporalQuery}
     * 。
     * 该方法等效于此查询,但如果无法获取年表,它会抛出异常。
     * 
     * 
     * @return a query that can obtain the chronology of a temporal, not null
     */
    public static TemporalQuery<Chronology> chronology() {
        return TemporalQueries.CHRONO;
    }

    /**
     * A query for the smallest supported unit.
     * <p>
     * This queries a {@code TemporalAccessor} for the time precision.
     * If the target {@code TemporalAccessor} represents a consistent or complete date-time,
     * date or time then this must return the smallest precision actually supported.
     * Note that fields such as {@code NANO_OF_DAY} and {@code NANO_OF_SECOND}
     * are defined to always return ignoring the precision, thus this is the only
     * way to find the actual smallest supported unit.
     * For example, were {@code GregorianCalendar} to implement {@code TemporalAccessor}
     * it would return a precision of {@code MILLIS}.
     * <p>
     * The result from JDK classes implementing {@code TemporalAccessor} is as follows:<br>
     * {@code LocalDate} returns {@code DAYS}<br>
     * {@code LocalTime} returns {@code NANOS}<br>
     * {@code LocalDateTime} returns {@code NANOS}<br>
     * {@code ZonedDateTime} returns {@code NANOS}<br>
     * {@code OffsetTime} returns {@code NANOS}<br>
     * {@code OffsetDateTime} returns {@code NANOS}<br>
     * {@code ChronoLocalDate} returns {@code DAYS}<br>
     * {@code ChronoLocalDateTime} returns {@code NANOS}<br>
     * {@code ChronoZonedDateTime} returns {@code NANOS}<br>
     * {@code Era} returns {@code ERAS}<br>
     * {@code DayOfWeek} returns {@code DAYS}<br>
     * {@code Month} returns {@code MONTHS}<br>
     * {@code Year} returns {@code YEARS}<br>
     * {@code YearMonth} returns {@code MONTHS}<br>
     * {@code MonthDay} returns null (does not represent a complete date or time)<br>
     * {@code ZoneOffset} returns null (does not represent a date or time)<br>
     * {@code Instant} returns {@code NANOS}<br>
     *
     * <p>
     *  查询最小支持的单位。
     * <p>
     * 这查询一个{@code TemporalAccessor}的时间精度。如果目标{@code TemporalAccessor}表示一致或完整的日期 - 时间,日期或时间,则必须返回实际支持的最小精度。
     * 请注意,诸如{@code NANO_OF_DAY}和{@code NANO_OF_SECOND}之类的字段被定义为总是返回忽略精度,因此这是找到实际最小支持单位的唯一方法。
     * 例如,{@code GregorianCalendar}实现{@code TemporalAccessor}会返回{@code MILLIS}的精度。
     * <p>
     *  实现{@code TemporalAccessor}的JDK类的结果如下：<br> {@code LocalDate}返回{@code DAYS} <br> {@code LocalTime}返回{@code NANOS}
     *  <br> {@code LocalDateTime }返回{@code NANOS} <br> {@code ZonedDateTime}返回{@code NANOS} <br> {@code OffsetTime}
     * 返回{@code NANOS} <br> {@code OffsetDateTime}返回{@code NANOS} < br> {@code ChronoLocalDate}返回{@code DAYS}
     *  <br> {@code ChronoLocalDateTime}返回{@code NANOS} <br> {@code ChronoZonedDateTime}返回{@code NANOS} <br>
     *  {@code Era} {@code ERAS} <br> {@code DayOfWeek}返回{@code DAYS} <br> {@code Month}返回{@code MONTHS} <br>
     *  {@code Year}返回{@code YEARS} <br> {@code YearMonth}返回{@code MONTHS} <br> {@code MonthDay}返回null(不表示完整
     * 的日期或时间)<br> {@code ZoneOffset}返回null(不表示日期或时间) <br> {@code Instant}返回{@code NANOS} <br>。
     * 
     * 
     * @return a query that can obtain the precision of a temporal, not null
     */
    public static TemporalQuery<TemporalUnit> precision() {
        return TemporalQueries.PRECISION;
    }

    //-----------------------------------------------------------------------
    // non-special constants are standard queries that derive information from other information
    /**
     * A lenient query for the {@code ZoneId}, falling back to the {@code ZoneOffset}.
     * <p>
     * This queries a {@code TemporalAccessor} for the zone.
     * It first tries to obtain the zone, using {@link #zoneId()}.
     * If that is not found it tries to obtain the {@link #offset()}.
     * Thus a {@link java.time.ZonedDateTime} will return the result of {@code getZone()},
     * while an {@link java.time.OffsetDateTime} will return the result of {@code getOffset()}.
     * <p>
     * In most cases, applications should use this query rather than {@code #zoneId()}.
     * <p>
     * The method {@link ZoneId#from(TemporalAccessor)} can be used as a
     * {@code TemporalQuery} via a method reference, {@code ZoneId::from}.
     * That method is equivalent to this query, except that it throws an
     * exception if a zone cannot be obtained.
     *
     * <p>
     * 对{@code ZoneId}的宽松查询,回退到{@code ZoneOffset}。
     * <p>
     *  这将查询区域的{@code TemporalAccessor}。它首先尝试使用{@link #zoneId()}获取区域。如果没有找到它尝试获得{@link #offset()}。
     * 因此,{@link java.time.ZonedDateTime}将返回{@code getZone()}的结果,而{@link java.time.OffsetDateTime}将返回{@code getOffset()}
     * 的结果。
     *  这将查询区域的{@code TemporalAccessor}。它首先尝试使用{@link #zoneId()}获取区域。如果没有找到它尝试获得{@link #offset()}。
     * <p>
     *  在大多数情况下,应用程序应使用此查询,而不是{@code #zoneId()}。
     * <p>
     *  方法{@link ZoneId#from(TemporalAccessor)}可以通过方法引用{@code ZoneId :: from}用作{@code TemporalQuery}。
     * 该方法等效于此查询,除了如果无法获取区域,它会抛出异常。
     * 
     * 
     * @return a query that can obtain the zone ID or offset of a temporal, not null
     */
    public static TemporalQuery<ZoneId> zone() {
        return TemporalQueries.ZONE;
    }

    /**
     * A query for {@code ZoneOffset} returning null if not found.
     * <p>
     * This returns a {@code TemporalQuery} that can be used to query a temporal
     * object for the offset. The query will return null if the temporal
     * object cannot supply an offset.
     * <p>
     * The query implementation examines the {@link ChronoField#OFFSET_SECONDS OFFSET_SECONDS}
     * field and uses it to create a {@code ZoneOffset}.
     * <p>
     * The method {@link java.time.ZoneOffset#from(TemporalAccessor)} can be used as a
     * {@code TemporalQuery} via a method reference, {@code ZoneOffset::from}.
     * This query and {@code ZoneOffset::from} will return the same result if the
     * temporal object contains an offset. If the temporal object does not contain
     * an offset, then the method reference will throw an exception, whereas this
     * query will return null.
     *
     * <p>
     *  查询{@code ZoneOffset}如果找不到则返回null。
     * <p>
     *  这返回一个{@code TemporalQuery},可用于查询临时对象的偏移量。如果临时对象不能提供偏移量,则查询将返回null。
     * <p>
     *  查询实现检查{@link ChronoField#OFFSET_SECONDS OFFSET_SECONDS}字段,并使用它来创建{@code ZoneOffset}。
     * <p>
     * 方法{@link java.time.ZoneOffset#from(TemporalAccessor)}可以通过方法引用{@code ZoneOffset :: from}用作{@code TemporalQuery}
     * 。
     * 如果临时对象包含偏移量,此查询和{@code ZoneOffset :: from}将返回相同的结果。如果时间对象不包含偏移量,那么方法引用将抛出异常,而此查询将返回null。
     * 
     * 
     * @return a query that can obtain the offset of a temporal, not null
     */
    public static TemporalQuery<ZoneOffset> offset() {
        return TemporalQueries.OFFSET;
    }

    /**
     * A query for {@code LocalDate} returning null if not found.
     * <p>
     * This returns a {@code TemporalQuery} that can be used to query a temporal
     * object for the local date. The query will return null if the temporal
     * object cannot supply a local date.
     * <p>
     * The query implementation examines the {@link ChronoField#EPOCH_DAY EPOCH_DAY}
     * field and uses it to create a {@code LocalDate}.
     * <p>
     * The method {@link ZoneOffset#from(TemporalAccessor)} can be used as a
     * {@code TemporalQuery} via a method reference, {@code LocalDate::from}.
     * This query and {@code LocalDate::from} will return the same result if the
     * temporal object contains a date. If the temporal object does not contain
     * a date, then the method reference will throw an exception, whereas this
     * query will return null.
     *
     * <p>
     *  查询{@code LocalDate}如果找不到则返回null。
     * <p>
     *  这将返回一个{@code TemporalQuery},可用于查询本地日期的临时对象。如果时态对象不能提供本地日期,则查询将返回null。
     * <p>
     *  查询实施会检查{@link ChronoField#EPOCH_DAY EPOCH_DAY}字段,并使用它来创建{@code LocalDate}。
     * <p>
     *  方法{@link ZoneOffset#from(TemporalAccessor)}可以通过方法引用{@code LocalDate :: from}用作{@code TemporalQuery}。
     * 如果临时对象包含日期,则此查询和{@code LocalDate :: from}将返回相同的结果。如果临时对象不包含日期,那么方法引用将抛出异常,而此查询将返回null。
     * 
     * 
     * @return a query that can obtain the date of a temporal, not null
     */
    public static TemporalQuery<LocalDate> localDate() {
        return TemporalQueries.LOCAL_DATE;
    }

    /**
     * A query for {@code LocalTime} returning null if not found.
     * <p>
     * This returns a {@code TemporalQuery} that can be used to query a temporal
     * object for the local time. The query will return null if the temporal
     * object cannot supply a local time.
     * <p>
     * The query implementation examines the {@link ChronoField#NANO_OF_DAY NANO_OF_DAY}
     * field and uses it to create a {@code LocalTime}.
     * <p>
     * The method {@link ZoneOffset#from(TemporalAccessor)} can be used as a
     * {@code TemporalQuery} via a method reference, {@code LocalTime::from}.
     * This query and {@code LocalTime::from} will return the same result if the
     * temporal object contains a time. If the temporal object does not contain
     * a time, then the method reference will throw an exception, whereas this
     * query will return null.
     *
     * <p>
     *  查询{@code LocalTime}如果找不到则返回null。
     * <p>
     *  这将返回一个{@code TemporalQuery},可用于查询本地时间的临时对象。如果时态对象不能提供本地时间,则查询将返回null。
     * <p>
     * 查询实施会检查{@link ChronoField#NANO_OF_DAY NANO_OF_DAY}字段,并使用它创建{@code LocalTime}。
     * <p>
     *  方法{@link ZoneOffset#from(TemporalAccessor)}可以通过方法引用{@code LocalTime :: from}用作{@code TemporalQuery}。
     * 如果时间对象包含时间,则此查询和{@code LocalTime :: from}将返回相同的结果。如果时间对象不包含时间,那么方法引用将抛出异常,而此查询将返回null。
     * 
     * 
     * @return a query that can obtain the time of a temporal, not null
     */
    public static TemporalQuery<LocalTime> localTime() {
        return TemporalQueries.LOCAL_TIME;
    }

    //-----------------------------------------------------------------------
    /**
     * A strict query for the {@code ZoneId}.
     * <p>
     *  严格查询{@code ZoneId}。
     * 
     */
    static final TemporalQuery<ZoneId> ZONE_ID = (temporal) ->
        temporal.query(TemporalQueries.ZONE_ID);

    /**
     * A query for the {@code Chronology}.
     * <p>
     *  查询{@code Chronology}。
     * 
     */
    static final TemporalQuery<Chronology> CHRONO = (temporal) ->
        temporal.query(TemporalQueries.CHRONO);

    /**
     * A query for the smallest supported unit.
     * <p>
     *  查询最小支持的单位。
     * 
     */
    static final TemporalQuery<TemporalUnit> PRECISION = (temporal) ->
        temporal.query(TemporalQueries.PRECISION);

    //-----------------------------------------------------------------------
    /**
     * A query for {@code ZoneOffset} returning null if not found.
     * <p>
     *  查询{@code ZoneOffset}如果找不到则返回null。
     * 
     */
    static final TemporalQuery<ZoneOffset> OFFSET = (temporal) -> {
        if (temporal.isSupported(OFFSET_SECONDS)) {
            return ZoneOffset.ofTotalSeconds(temporal.get(OFFSET_SECONDS));
        }
        return null;
    };

    /**
     * A lenient query for the {@code ZoneId}, falling back to the {@code ZoneOffset}.
     * <p>
     *  对{@code ZoneId}的宽松查询,回退到{@code ZoneOffset}。
     * 
     */
    static final TemporalQuery<ZoneId> ZONE = (temporal) -> {
        ZoneId zone = temporal.query(ZONE_ID);
        return (zone != null ? zone : temporal.query(OFFSET));
    };

    /**
     * A query for {@code LocalDate} returning null if not found.
     * <p>
     *  查询{@code LocalDate}如果找不到则返回null。
     * 
     */
    static final TemporalQuery<LocalDate> LOCAL_DATE = (temporal) -> {
        if (temporal.isSupported(EPOCH_DAY)) {
            return LocalDate.ofEpochDay(temporal.getLong(EPOCH_DAY));
        }
        return null;
    };

    /**
     * A query for {@code LocalTime} returning null if not found.
     * <p>
     *  查询{@code LocalTime}如果找不到则返回null。
     */
    static final TemporalQuery<LocalTime> LOCAL_TIME = (temporal) -> {
        if (temporal.isSupported(NANO_OF_DAY)) {
            return LocalTime.ofNanoOfDay(temporal.getLong(NANO_OF_DAY));
        }
        return null;
    };

}
