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
import java.time.chrono.Chronology;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * A field of date-time, such as month-of-year or hour-of-minute.
 * <p>
 * Date and time is expressed using fields which partition the time-line into something
 * meaningful for humans. Implementations of this interface represent those fields.
 * <p>
 * The most commonly used units are defined in {@link ChronoField}.
 * Further fields are supplied in {@link IsoFields}, {@link WeekFields} and {@link JulianFields}.
 * Fields can also be written by application code by implementing this interface.
 * <p>
 * The field works using double dispatch. Client code calls methods on a date-time like
 * {@code LocalDateTime} which check if the field is a {@code ChronoField}.
 * If it is, then the date-time must handle it.
 * Otherwise, the method call is re-dispatched to the matching method in this interface.
 *
 * @implSpec
 * This interface must be implemented with care to ensure other classes operate correctly.
 * All implementations that can be instantiated must be final, immutable and thread-safe.
 * Implementations should be {@code Serializable} where possible.
 * An enum is as effective implementation choice.
 *
 * <p>
 *  日期时间字段,例如年份或分钟。
 * <p>
 *  日期和时间使用将时间线划分为对人有意义的字段来表示。此接口的实现代表这些字段。
 * <p>
 *  最常用的单位在{@link ChronoField}中定义。更多字段在{@link IsoFields},{@link WeekFields}和{@link JulianFields}中提供。
 * 字段也可以通过实现此接口由应用程序代码编写。
 * <p>
 * 该字段使用双调度。客户端代码在类似{@code LocalDateTime}的日期时间调用方法,该方法检查该字段是否为{@code ChronoField}。如果是,那么日期时间必须处理它。
 * 否则,将重新分派方法调用到此接口中的匹配方法。
 * 
 *  @implSpec必须小心地实现此接口,以确保其他类正常运行。所有可以实例化的实现必须是final,immutable和线程安全的。实施应尽可能{@code Serializable}。
 * 枚举是一种有效的实现选择。
 * 
 * 
 * @since 1.8
 */
public interface TemporalField {

    /**
     * Gets the display name for the field in the requested locale.
     * <p>
     * If there is no display name for the locale then a suitable default must be returned.
     * <p>
     * The default implementation must check the locale is not null
     * and return {@code toString()}.
     *
     * <p>
     *  获取所请求语言环境中字段的显示名称。
     * <p>
     *  如果没有区域设置的显示名称,则必须返回合适的默认值。
     * <p>
     *  默认实现必须检查语言环境不为null并返回{@code toString()}。
     * 
     * 
     * @param locale  the locale to use, not null
     * @return the display name for the locale or a suitable default, not null
     */
    default String getDisplayName(Locale locale) {
        Objects.requireNonNull(locale, "locale");
        return toString();
    }

    /**
     * Gets the unit that the field is measured in.
     * <p>
     * The unit of the field is the period that varies within the range.
     * For example, in the field 'MonthOfYear', the unit is 'Months'.
     * See also {@link #getRangeUnit()}.
     *
     * <p>
     *  获取字段测量的单位。
     * <p>
     *  字段的单位是在该范围内变化的周期。例如,在"MonthOfYear"字段中,单位为"Months"。另请参见{@link #getRangeUnit()}。
     * 
     * 
     * @return the period unit defining the base unit of the field, not null
     */
    TemporalUnit getBaseUnit();

    /**
     * Gets the range that the field is bound by.
     * <p>
     * The range of the field is the period that the field varies within.
     * For example, in the field 'MonthOfYear', the range is 'Years'.
     * See also {@link #getBaseUnit()}.
     * <p>
     * The range is never null. For example, the 'Year' field is shorthand for
     * 'YearOfForever'. It therefore has a unit of 'Years' and a range of 'Forever'.
     *
     * <p>
     *  获取字段所绑定的范围。
     * <p>
     *  字段的范围是字段在其内变化的周期。例如,在"MonthOfYear"字段中,范围为"年"。另请参见{@link #getBaseUnit()}。
     * <p>
     *  范围永远不为null。例如,"Year"字段是"YearOfForever"的缩写。因此,它的单位是"年",范围是"永远"。
     * 
     * 
     * @return the period unit defining the range of the field, not null
     */
    TemporalUnit getRangeUnit();

    /**
     * Gets the range of valid values for the field.
     * <p>
     * All fields can be expressed as a {@code long} integer.
     * This method returns an object that describes the valid range for that value.
     * This method is generally only applicable to the ISO-8601 calendar system.
     * <p>
     * Note that the result only describes the minimum and maximum valid values
     * and it is important not to read too much into them. For example, there
     * could be values within the range that are invalid for the field.
     *
     * <p>
     *  获取字段的有效值范围。
     * <p>
     * 所有字段都可以表示为{@code long}整数。此方法返回描述该值的有效范围的对象。此方法通常仅适用于ISO-8601日历系统。
     * <p>
     *  注意,结果只描述最小和最大有效值,重要的是不要过多地读入它们。例如,可能存在对于字段无效的范围内的值。
     * 
     * 
     * @return the range of valid values for the field, not null
     */
    ValueRange range();

    //-----------------------------------------------------------------------
    /**
     * Checks if this field represents a component of a date.
     * <p>
     * A field is date-based if it can be derived from
     * {@link ChronoField#EPOCH_DAY EPOCH_DAY}.
     * Note that it is valid for both {@code isDateBased()} and {@code isTimeBased()}
     * to return false, such as when representing a field like minute-of-week.
     *
     * <p>
     *  检查此字段是否表示日期的组件。
     * <p>
     *  如果字段可以从{@link ChronoField#EPOCH_DAY EPOCH_DAY}派生,则字段为基于日期的字段。
     * 注意,它对于{@code isDateBased()}和{@code isTimeBased()}都返回false是有效的,例如当表示像星期几的字段时。
     * 
     * 
     * @return true if this field is a component of a date
     */
    boolean isDateBased();

    /**
     * Checks if this field represents a component of a time.
     * <p>
     * A field is time-based if it can be derived from
     * {@link ChronoField#NANO_OF_DAY NANO_OF_DAY}.
     * Note that it is valid for both {@code isDateBased()} and {@code isTimeBased()}
     * to return false, such as when representing a field like minute-of-week.
     *
     * <p>
     *  检查此字段是否表示时间的组件。
     * <p>
     *  字段基于时间,如果它可以从{@link ChronoField#NANO_OF_DAY NANO_OF_DAY}派生。
     * 注意,它对于{@code isDateBased()}和{@code isTimeBased()}都返回false是有效的,例如当表示像星期几的字段时。
     * 
     * 
     * @return true if this field is a component of a time
     */
    boolean isTimeBased();

    //-----------------------------------------------------------------------
    /**
     * Checks if this field is supported by the temporal object.
     * <p>
     * This determines whether the temporal accessor supports this field.
     * If this returns false, the the temporal cannot be queried for this field.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link TemporalAccessor#isSupported(TemporalField)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisField.isSupportedBy(temporal);
     *   temporal = temporal.isSupported(thisField);
     * </pre>
     * It is recommended to use the second approach, {@code isSupported(TemporalField)},
     * as it is a lot clearer to read in code.
     * <p>
     * Implementations should determine whether they are supported using the fields
     * available in {@link ChronoField}.
     *
     * <p>
     *  检查临时对象是否支持此字段。
     * <p>
     *  这确定时间访问器是否支持此字段。如果此返回false,则不能查询此字段的时间。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link TemporalAccessor#isSupported(TemporalField)}：
     * <pre>
     * //这两行是等价的,但第二种方法是推荐temporal = thisField.isSupportedBy(temporal); temporal = temporal.isSupported(this
     * Field);。
     * </pre>
     *  建议使用第二种方法,{@code isSupported(TemporalField)},因为它在代码中很容易阅读。
     * <p>
     *  实施应使用{@link ChronoField}中提供的字段确定是否支持它们。
     * 
     * 
     * @param temporal  the temporal object to query, not null
     * @return true if the date-time can be queried for this field, false if not
     */
    boolean isSupportedBy(TemporalAccessor temporal);

    /**
     * Get the range of valid values for this field using the temporal object to
     * refine the result.
     * <p>
     * This uses the temporal object to find the range of valid values for the field.
     * This is similar to {@link #range()}, however this method refines the result
     * using the temporal. For example, if the field is {@code DAY_OF_MONTH} the
     * {@code range} method is not accurate as there are four possible month lengths,
     * 28, 29, 30 and 31 days. Using this method with a date allows the range to be
     * accurate, returning just one of those four options.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link TemporalAccessor#range(TemporalField)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisField.rangeRefinedBy(temporal);
     *   temporal = temporal.range(thisField);
     * </pre>
     * It is recommended to use the second approach, {@code range(TemporalField)},
     * as it is a lot clearer to read in code.
     * <p>
     * Implementations should perform any queries or calculations using the fields
     * available in {@link ChronoField}.
     * If the field is not supported an {@code UnsupportedTemporalTypeException} must be thrown.
     *
     * <p>
     *  使用时间对象获取此字段的有效值的范围以细化结果。
     * <p>
     *  这将使用临时对象来查找字段的有效值范围。这类似于{@link #range()},但是这种方法使用时间来精炼结果。
     * 例如,如果字段为{@code DAY_OF_MONTH},则{@code range}方法不准确,因为有四个可能的月份长度,28,29,30和31天。
     * 对日期使用此方法允许范围准确,只返回这四个选项之一。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link TemporalAccessor#range(TemporalField)}：
     * <pre>
     *  //这两行是等价的,但第二种方法是推荐temporal = thisField.rangeRefinedBy(temporal); temporal = temporal.range(thisFiel
     * d);。
     * </pre>
     *  建议使用第二种方法{@code range(TemporalField)},因为它在代码中更清晰。
     * <p>
     * 实施应使用{@link ChronoField}中提供的字段执行任何查询或计算。如果不支持该字段,则必须抛出{@code UnsupportedTemporalTypeException}。
     * 
     * 
     * @param temporal  the temporal object used to refine the result, not null
     * @return the range of valid values for this field, not null
     * @throws DateTimeException if the range for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported by the temporal
     */
    ValueRange rangeRefinedBy(TemporalAccessor temporal);

    /**
     * Gets the value of this field from the specified temporal object.
     * <p>
     * This queries the temporal object for the value of this field.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link TemporalAccessor#getLong(TemporalField)}
     * (or {@link TemporalAccessor#get(TemporalField)}):
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisField.getFrom(temporal);
     *   temporal = temporal.getLong(thisField);
     * </pre>
     * It is recommended to use the second approach, {@code getLong(TemporalField)},
     * as it is a lot clearer to read in code.
     * <p>
     * Implementations should perform any queries or calculations using the fields
     * available in {@link ChronoField}.
     * If the field is not supported an {@code UnsupportedTemporalTypeException} must be thrown.
     *
     * <p>
     *  从指定的时间对象获取此字段的值。
     * <p>
     *  这将查询临时对象的此字段的值。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。
     * 第二个是使用{@link TemporalAccessor#getLong(TemporalField)}(或{@link TemporalAccessor#get(TemporalField)})：。
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。
     * <pre>
     *  //这两行是等效的,但第二种方法是推荐temporal = thisField.getFrom(temporal); temporal = temporal.getLong(thisField);
     * </pre>
     *  建议使用第二种方法{@code getLong(TemporalField)},因为它在代码中更清晰。
     * <p>
     *  实施应使用{@link ChronoField}中提供的字段执行任何查询或计算。如果不支持该字段,则必须抛出{@code UnsupportedTemporalTypeException}。
     * 
     * 
     * @param temporal  the temporal object to query, not null
     * @return the value of this field, not null
     * @throws DateTimeException if a value for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported by the temporal
     * @throws ArithmeticException if numeric overflow occurs
     */
    long getFrom(TemporalAccessor temporal);

    /**
     * Returns a copy of the specified temporal object with the value of this field set.
     * <p>
     * This returns a new temporal object based on the specified one with the value for
     * this field changed. For example, on a {@code LocalDate}, this could be used to
     * set the year, month or day-of-month.
     * The returned object has the same observable type as the specified object.
     * <p>
     * In some cases, changing a field is not fully defined. For example, if the target object is
     * a date representing the 31st January, then changing the month to February would be unclear.
     * In cases like this, the implementation is responsible for resolving the result.
     * Typically it will choose the previous valid date, which would be the last valid
     * day of February in this example.
     * <p>
     * There are two equivalent ways of using this method.
     * The first is to invoke this method directly.
     * The second is to use {@link Temporal#with(TemporalField, long)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisField.adjustInto(temporal);
     *   temporal = temporal.with(thisField);
     * </pre>
     * It is recommended to use the second approach, {@code with(TemporalField)},
     * as it is a lot clearer to read in code.
     * <p>
     * Implementations should perform any queries or calculations using the fields
     * available in {@link ChronoField}.
     * If the field is not supported an {@code UnsupportedTemporalTypeException} must be thrown.
     * <p>
     * Implementations must not alter the specified temporal object.
     * Instead, an adjusted copy of the original must be returned.
     * This provides equivalent, safe behavior for immutable and mutable implementations.
     *
     * <p>
     *  返回具有此字段集的值的指定临时对象的副本。
     * <p>
     *  这将返回一个新的时间对象基于指定的一个与此字段的值更改。例如,在{@code LocalDate}上,这可以用于设置年,月或日。返回的对象具有与指定对象相同的observable类型。
     * <p>
     * 在某些情况下,更改字段未完全定义。例如,如果目标对象是表示1月31日的日期,则将该月更改为2月将是不清楚的。在这种情况下,实现负责解决结果。
     * 通常,它将选择上一个有效日期,这将是本示例中二月的最后一个有效日期。
     * <p>
     *  有两种等效的方法使用这种方法。第一个是直接调用这个方法。第二个是使用{@link Temporal#with(TemporalField,long)}：
     * <pre>
     *  //这两行是等价的,但第二种方法是建议temporal = thisField.adjustInto(temporal); temporal = temporal.with(thisField);
     * </pre>
     *  建议使用第二种方法,{@code with(TemporalField)},因为它在代码中更清晰。
     * <p>
     *  实施应使用{@link ChronoField}中提供的字段执行任何查询或计算。如果不支持该字段,则必须抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  实现不得更改指定的时间对象。相反,必须返回原件的调整副本。这为不可变和可变实现提供了等效的,安全的行为。
     * 
     * 
     * @param <R>  the type of the Temporal object
     * @param temporal the temporal object to adjust, not null
     * @param newValue the new value of the field
     * @return the adjusted temporal object, not null
     * @throws DateTimeException if the field cannot be set
     * @throws UnsupportedTemporalTypeException if the field is not supported by the temporal
     * @throws ArithmeticException if numeric overflow occurs
     */
    <R extends Temporal> R adjustInto(R temporal, long newValue);

    /**
     * Resolves this field to provide a simpler alternative or a date.
     * <p>
     * This method is invoked during the resolve phase of parsing.
     * It is designed to allow application defined fields to be simplified into
     * more standard fields, such as those on {@code ChronoField}, or into a date.
     * <p>
     * Applications should not normally invoke this method directly.
     *
     * @implSpec
     * If an implementation represents a field that can be simplified, or
     * combined with others, then this method must be implemented.
     * <p>
     * The specified map contains the current state of the parse.
     * The map is mutable and must be mutated to resolve the field and
     * any related fields. This method will only be invoked during parsing
     * if the map contains this field, and implementations should therefore
     * assume this field is present.
     * <p>
     * Resolving a field will consist of looking at the value of this field,
     * and potentially other fields, and either updating the map with a
     * simpler value, such as a {@code ChronoField}, or returning a
     * complete {@code ChronoLocalDate}. If a resolve is successful,
     * the code must remove all the fields that were resolved from the map,
     * including this field.
     * <p>
     * For example, the {@code IsoFields} class contains the quarter-of-year
     * and day-of-quarter fields. The implementation of this method in that class
     * resolves the two fields plus the {@link ChronoField#YEAR YEAR} into a
     * complete {@code LocalDate}. The resolve method will remove all three
     * fields from the map before returning the {@code LocalDate}.
     * <p>
     * A partially complete temporal is used to allow the chronology and zone
     * to be queried. In general, only the chronology will be needed.
     * Querying items other than the zone or chronology is undefined and
     * must not be relied on.
     * The behavior of other methods such as {@code get}, {@code getLong},
     * {@code range} and {@code isSupported} is unpredictable and the results undefined.
     * <p>
     * If resolution should be possible, but the data is invalid, the resolver
     * style should be used to determine an appropriate level of leniency, which
     * may require throwing a {@code DateTimeException} or {@code ArithmeticException}.
     * If no resolution is possible, the resolve method must return null.
     * <p>
     * When resolving time fields, the map will be altered and null returned.
     * When resolving date fields, the date is normally returned from the method,
     * with the map altered to remove the resolved fields. However, it would also
     * be acceptable for the date fields to be resolved into other {@code ChronoField}
     * instances that can produce a date, such as {@code EPOCH_DAY}.
     * <p>
     * Not all {@code TemporalAccessor} implementations are accepted as return values.
     * Implementations that call this method must accept {@code ChronoLocalDate},
     * {@code ChronoLocalDateTime}, {@code ChronoZonedDateTime} and {@code LocalTime}.
     * <p>
     * The default implementation must return null.
     *
     * <p>
     *  解决此字段以提供更简单的替代方案或日期。
     * <p>
     *  此方法在解析的解析阶段被调用。它设计为允许将应用程序定义的字段简化为更标准的字段,例如{@code ChronoField}上的字段或日期。
     * <p>
     * 应用程序通常不应直接调用此方法。
     * 
     *  @implSpec如果实现表示可以简化或与其他结合的字段,则必须实现此方法。
     * <p>
     *  指定的映射包含解析的当前状态。地图是可变的,必须进行突变才能解析字段和任何相关字段。只有在映射包含此字段时,此方法才会在解析期间被调用,因此实现应假定此字段存在。
     * <p>
     *  解析字段将包括查看此字段的值以及可能的其他字段,并使用更简单的值(例如{@code ChronoField})更新映射,或返回完整的{@code ChronoLocalDate}。
     * 如果解析成功,代码必须删除从地图解析的所有字段,包括此字段。
     * <p>
     *  例如,{@code IsoFields}类包含四分之一年和季度字段。
     * 该类中的此方法的实现将两个字段加上{@link ChronoField#YEAR YEAR}解析为完整的{@code LocalDate}。
     *  resolve方法将在返回{@code LocalDate}之前从地图中删除所有三个字段。
     * <p>
     * 部分完整的时间用于允许查询年表和区域。一般来说,只需要年表。查询除区域或年表之外的项目是未定义的,不能依赖。
     * 其他方法(例如{@code get},{@code getLong},{@code range}和{@code isSupported})的行为是不可预测的,结果未定义。
     * <p>
     * 
     * @param fieldValues  the map of fields to values, which can be updated, not null
     * @param partialTemporal  the partially complete temporal to query for zone and
     *  chronology; querying for other things is undefined and not recommended, not null
     * @param resolverStyle  the requested type of resolve, not null
     * @return the resolved temporal object; null if resolving only
     *  changed the map, or no resolve occurred
     * @throws ArithmeticException if numeric overflow occurs
     * @throws DateTimeException if resolving results in an error. This must not be thrown
     *  by querying a field on the temporal without first checking if it is supported
     */
    default TemporalAccessor resolve(
            Map<TemporalField, Long> fieldValues,
            TemporalAccessor partialTemporal,
            ResolverStyle resolverStyle) {
        return null;
    }

    /**
     * Gets a descriptive name for the field.
     * <p>
     * The should be of the format 'BaseOfRange', such as 'MonthOfYear',
     * unless the field has a range of {@code FOREVER}, when only
     * the base unit is mentioned, such as 'Year' or 'Era'.
     *
     * <p>
     *  如果解析应该是可能的,但数据是无效的,解析器样式应该用于确定适当的宽松级别,这可能需要抛出一个{@code DateTimeException}或{@code ArithmeticException}
     * 。
     * 如果没有可能的解析,resolve方法必须返回null。
     * <p>
     *  在解析时间字段时,地图将被更改并返回null。在解析日期字段时,日期通常从方法返回,映射已更改以删除已解析的字段。
     * 不过,也可以将日期字段解析为可生成日期的其他{@code ChronoField}实例,例如{@code EPOCH_DAY}。
     * <p>
     *  并非所有{@code TemporalAccessor}实现都被接受为返回值。
     * 实现调用此方法必须接受{@code ChronoLocalDate},{@code ChronoLocalDateTime},{@code ChronoZonedDateTime}和{@code LocalTime}
     * 。
     * 
     * @return the name of the field, not null
     */
    @Override
    String toString();


}
