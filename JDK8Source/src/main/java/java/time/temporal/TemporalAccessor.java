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
import java.util.Objects;

/**
 * Framework-level interface defining read-only access to a temporal object,
 * such as a date, time, offset or some combination of these.
 * <p>
 * This is the base interface type for date, time and offset objects.
 * It is implemented by those classes that can provide information
 * as {@linkplain TemporalField fields} or {@linkplain TemporalQuery queries}.
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
 * These can be accessed via {@linkplain #query(TemporalQuery) queries} using
 * the static methods defined on {@link TemporalQuery}.
 * <p>
 * A sub-interface, {@link Temporal}, extends this definition to one that also
 * supports adjustment and manipulation on more complete temporal objects.
 * <p>
 * This interface is a framework-level interface that should not be widely
 * used in application code. Instead, applications should create and pass
 * around instances of concrete types, such as {@code LocalDate}.
 * There are many reasons for this, part of which is that implementations
 * of this interface may be in calendar systems other than ISO.
 * See {@link java.time.chrono.ChronoLocalDate} for a fuller discussion of the issues.
 *
 * @implSpec
 * This interface places no restrictions on the mutability of implementations,
 * however immutability is strongly recommended.
 *
 * <p>
 *  框架级接口定义对时间对象的只读访问,例如日期,时间,偏移或这些的某种组合。
 * <p>
 *  这是日期,时间和偏移对象的基本接口类型。
 * 它由可以提供信息的类实现为{@linkplain TemporalField fields}或{@linkplain TemporalQuery queries}。
 * <p>
 *  大多数日期和时间信息可以表示为数字。这些是使用{@code TemporalField}建模的,使用{@code long}保存的数字来处理大的值。
 * 年,月和日是字段的简单示例,但它们也包括即时和偏移量。有关标准字段集,请参见{@link ChronoField}。
 * <p>
 * 两个日期/时间信息不能由数字{@linkplain java.time.chrono.Chronology chronology}和{@linkplain java.time.ZoneId time-zone}
 * 表示。
 * 这些可以通过{@linkplain #query(TemporalQuery)查询}使用在{@link TemporalQuery}上定义的静态方法访问。
 * <p>
 *  子接口{@link Temporal}将此定义扩展到也支持对更完整的时间对象的调整和操作的定义。
 * <p>
 *  此接口是一个框架级接口,不应在应用程序代码中广泛使用。相反,应用程序应该创建和传递具体类型的实例,例如{@code LocalDate}。
 * 这有许多原因,其中的一部分是该接口的实现可以在除ISO之外的日历系统中。有关这些问题的更全面讨论,请参阅{@link java.time.chrono.ChronoLocalDate}。
 * 
 *  @implSpec此接口对实现的可变性没有限制,但强烈建议使用不可变性。
 * 
 * 
 * @since 1.8
 */
public interface TemporalAccessor {

    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if the date-time can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range} and {@link #get(TemporalField) get}
     * methods will throw an exception.
     *
     * @implSpec
     * Implementations must check and handle all fields defined in {@link ChronoField}.
     * If the field is supported, then true must be returned, otherwise false must be returned.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.isSupportedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * <p>
     * Implementations must ensure that no observable state is altered when this
     * read-only method is invoked.
     *
     * <p>
     *  检查是否支持指定的字段。
     * <p>
     *  这将检查是否可以查询指定字段的日期时间。
     * 如果为false,则调用{@link #range(TemporalField)range}和{@link #get(TemporalField)get}方法将抛出异常。
     * 
     *  @implSpec实现必须检查和处理{@link ChronoField}中定义的所有字段。如果支持字段,则必须返回true,否则必须返回false。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作为
     * 参数来获得此方法的结果。
     * <p>
     *  实现必须确保在调用此只读方法时不会更改可观察状态。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if this date-time can be queried for the field, false if not
     */
    boolean isSupported(TemporalField field);

    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * All fields can be expressed as a {@code long} integer.
     * This method returns an object that describes the valid range for that value.
     * The value of this temporal object is used to enhance the accuracy of the returned range.
     * If the date-time cannot return the range, because the field is unsupported or for
     * some other reason, an exception will be thrown.
     * <p>
     * Note that the result only describes the minimum and maximum valid values
     * and it is important not to read too much into them. For example, there
     * could be values within the range that are invalid for the field.
     *
     * @implSpec
     * Implementations must check and handle all fields defined in {@link ChronoField}.
     * If the field is supported, then the range of the field must be returned.
     * If unsupported, then an {@code UnsupportedTemporalTypeException} must be thrown.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.rangeRefinedBy(TemporalAccessorl)}
     * passing {@code this} as the argument.
     * <p>
     * Implementations must ensure that no observable state is altered when this
     * read-only method is invoked.
     * <p>
     * The default implementation must behave equivalent to this code:
     * <pre>
     *  if (field instanceof ChronoField) {
     *    if (isSupported(field)) {
     *      return field.range();
     *    }
     *    throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
     *  }
     *  return field.rangeRefinedBy(this);
     * </pre>
     *
     * <p>
     *  获取指定字段的有效值范围。
     * <p>
     *  所有字段都可以表示为{@code long}整数。此方法返回描述该值的有效范围的对象。此时间对象的值用于提高返回范围的精度。
     * 如果日期时间无法返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  注意,结果只描述最小和最大有效值,重要的是不要过多地读入它们。例如,可能存在对于字段无效的范围内的值。
     * 
     *  @implSpec实现必须检查和处理{@link ChronoField}中定义的所有字段。如果支持该字段,则必须返回字段的范围。
     * 如果不受支持,则必须抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.rangeRefinedBy(TemporalAccessorl)}传递{@code this}
     * 作为参数来获得此方法的结果。
     * <p>
     *  实现必须确保在调用此只读方法时不会更改可观察状态。
     * <p>
     * 默认实现必须与此代码等效：
     * <pre>
     *  if(isSupported(field)){return field.range(); } throw new UnsupportedTemporalTypeException("Unsupport
     * ed field："+ field); } return field.rangeRefinedBy(this);。
     * </pre>
     * 
     * 
     * @param field  the field to query the range for, not null
     * @return the range of valid values for the field, not null
     * @throws DateTimeException if the range for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     */
    default ValueRange range(TemporalField field) {
        if (field instanceof ChronoField) {
            if (isSupported(field)) {
                return field.range();
            }
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        Objects.requireNonNull(field, "field");
        return field.rangeRefinedBy(this);
    }

    /**
     * Gets the value of the specified field as an {@code int}.
     * <p>
     * This queries the date-time for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If the date-time cannot return the value, because the field is unsupported or for
     * some other reason, an exception will be thrown.
     *
     * @implSpec
     * Implementations must check and handle all fields defined in {@link ChronoField}.
     * If the field is supported and has an {@code int} range, then the value of
     * the field must be returned.
     * If unsupported, then an {@code UnsupportedTemporalTypeException} must be thrown.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument.
     * <p>
     * Implementations must ensure that no observable state is altered when this
     * read-only method is invoked.
     * <p>
     * The default implementation must behave equivalent to this code:
     * <pre>
     *  if (range(field).isIntValue()) {
     *    return range(field).checkValidIntValue(getLong(field), field);
     *  }
     *  throw new UnsupportedTemporalTypeException("Invalid field " + field + " + for get() method, use getLong() instead");
     * </pre>
     *
     * <p>
     *  将指定字段的值作为{@code int}。
     * <p>
     *  这将查询指定字段的值的日期时间。返回的值将始终在字段的有效值范围内。如果日期时间无法返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * 
     *  @implSpec实现必须检查和处理{@link ChronoField}中定义的所有字段。如果字段受支持且具有{@code int}范围,则必须返回字段的值。
     * 如果不受支持,则必须抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * <p>
     *  实现必须确保在调用此只读方法时不会更改可观察状态。
     * <p>
     *  默认实现必须与此代码等效：
     * <pre>
     *  if(range(field).isIntValue()){return range(field).checkValidIntValue(getLong(field),field); } throw 
     * new UnsupportedTemporalTypeException("Invalid field"+ field +"+ for get()method,use getLong()instead"
     * );。
     * </pre>
     * 
     * 
     * @param field  the field to get, not null
     * @return the value for the field, within the valid range of values
     * @throws DateTimeException if a value for the field cannot be obtained or
     *         the value is outside the range of valid values for the field
     * @throws UnsupportedTemporalTypeException if the field is not supported or
     *         the range of values exceeds an {@code int}
     * @throws ArithmeticException if numeric overflow occurs
     */
    default int get(TemporalField field) {
        ValueRange range = range(field);
        if (range.isIntValue() == false) {
            throw new UnsupportedTemporalTypeException("Invalid field " + field + " for get() method, use getLong() instead");
        }
        long value = getLong(field);
        if (range.isValidValue(value) == false) {
            throw new DateTimeException("Invalid value for " + field + " (valid values " + range + "): " + value);
        }
        return (int) value;
    }

    /**
     * Gets the value of the specified field as a {@code long}.
     * <p>
     * This queries the date-time for the value for the specified field.
     * The returned value may be outside the valid range of values for the field.
     * If the date-time cannot return the value, because the field is unsupported or for
     * some other reason, an exception will be thrown.
     *
     * @implSpec
     * Implementations must check and handle all fields defined in {@link ChronoField}.
     * If the field is supported, then the value of the field must be returned.
     * If unsupported, then an {@code UnsupportedTemporalTypeException} must be thrown.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument.
     * <p>
     * Implementations must ensure that no observable state is altered when this
     * read-only method is invoked.
     *
     * <p>
     * 将指定字段的值作为{@code long}。
     * <p>
     *  这将查询指定字段的值的日期时间。返回的值可能在字段的有效值范围之外。如果日期时间无法返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * 
     *  @implSpec实现必须检查和处理{@link ChronoField}中定义的所有字段。如果支持该字段,则必须返回字段的值。
     * 如果不受支持,则必须抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * <p>
     *  实现必须确保在调用此只读方法时不会更改可观察状态。
     * 
     * 
     * @param field  the field to get, not null
     * @return the value for the field
     * @throws DateTimeException if a value for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    long getLong(TemporalField field);

    /**
     * Queries this date-time.
     * <p>
     * This queries this date-time using the specified query strategy object.
     * <p>
     * Queries are a key tool for extracting information from date-times.
     * They exists to externalize the process of querying, permitting different
     * approaches, as per the strategy design pattern.
     * Examples might be a query that checks if the date is the day before February 29th
     * in a leap year, or calculates the number of days to your next birthday.
     * <p>
     * The most common query implementations are method references, such as
     * {@code LocalDate::from} and {@code ZoneId::from}.
     * Additional implementations are provided as static methods on {@link TemporalQuery}.
     *
     * @implSpec
     * The default implementation must behave equivalent to this code:
     * <pre>
     *  if (query == TemporalQueries.zoneId() ||
     *        query == TemporalQueries.chronology() || query == TemporalQueries.precision()) {
     *    return null;
     *  }
     *  return query.queryFrom(this);
     * </pre>
     * Future versions are permitted to add further queries to the if statement.
     * <p>
     * All classes implementing this interface and overriding this method must call
     * {@code TemporalAccessor.super.query(query)}. JDK classes may avoid calling
     * super if they provide behavior equivalent to the default behaviour, however
     * non-JDK classes may not utilize this optimization and must call {@code super}.
     * <p>
     * If the implementation can supply a value for one of the queries listed in the
     * if statement of the default implementation, then it must do so.
     * For example, an application-defined {@code HourMin} class storing the hour
     * and minute must override this method as follows:
     * <pre>
     *  if (query == TemporalQueries.precision()) {
     *    return MINUTES;
     *  }
     *  return TemporalAccessor.super.query(query);
     * </pre>
     * <p>
     * Implementations must ensure that no observable state is altered when this
     * read-only method is invoked.
     *
     * <p>
     *  查询此日期时间。
     * <p>
     *  这将使用指定的查询策略对象查询此日期时间。
     * <p>
     *  查询是从日期时间提取信息的关键工具。它们存在于外部化查询的过程,允许不同的方法,根据策略设计模式。示例可以是检查日期是否为闰年中2月29日前一天的查询,或计算下一个生日的天数。
     * <p>
     * 最常见的查询实现是方法引用,例如{@code LocalDate :: from}和{@code ZoneId :: from}。
     * 在{@link TemporalQuery}上提供了其他实现作为静态方法。
     * 
     *  @implSpec默认实现必须与此代码等效：
     * <pre>
     *  if(query == TemporalQueries.zoneId()|| query == TemporalQueries.chronology()|| query == TemporalQuer
     * ies.precision()){return null; } return query.queryFrom(this);。
     * </pre>
     * 
     * @param <R> the type of the result
     * @param query  the query to invoke, not null
     * @return the query result, null may be returned (defined by the query)
     * @throws DateTimeException if unable to query
     * @throws ArithmeticException if numeric overflow occurs
     */
    default <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.zoneId()
                || query == TemporalQueries.chronology()
                || query == TemporalQueries.precision()) {
            return null;
        }
        return query.queryFrom(this);
    }

}
