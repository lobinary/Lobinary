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
package java.time;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoUnit.DAYS;

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * A day-of-week, such as 'Tuesday'.
 * <p>
 * {@code DayOfWeek} is an enum representing the 7 days of the week -
 * Monday, Tuesday, Wednesday, Thursday, Friday, Saturday and Sunday.
 * <p>
 * In addition to the textual enum name, each day-of-week has an {@code int} value.
 * The {@code int} value follows the ISO-8601 standard, from 1 (Monday) to 7 (Sunday).
 * It is recommended that applications use the enum rather than the {@code int} value
 * to ensure code clarity.
 * <p>
 * This enum provides access to the localized textual form of the day-of-week.
 * Some locales also assign different numeric values to the days, declaring
 * Sunday to have the value 1, however this class provides no support for this.
 * See {@link WeekFields} for localized week-numbering.
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code DayOfWeek}.
 * Use {@code getValue()} instead.</b>
 * <p>
 * This enum represents a common concept that is found in many calendar systems.
 * As such, this enum may be used by any calendar system that has the day-of-week
 * concept defined exactly equivalent to the ISO calendar system.
 *
 * @implSpec
 * This is an immutable and thread-safe enum.
 *
 * <p>
 *  星期几,例如"星期二"。
 * <p>
 *  {@code DayOfWeek}是表示一周中的7天的枚举 - 星期一,星期二,星期三,星期四,星期五,星期六和星期日。
 * <p>
 *  除了文本枚举名称外,每个星期几都有一个{@code int}值。 {@code int}值遵循ISO-8601标准,从1(星期一)到7(星期日)。
 * 建议应用程序使用枚举而不是{@code int}值来确保代码的清晰度。
 * <p>
 *  此枚举允许访问星期几的本地化文本形式。一些区域设置也为这些天分配不同的数字值,声明Sunday的值为1,但是此类不提供此支持。有关本地化的星期编号,请参阅{@link WeekFields}。
 * <p>
 * <b>不要使用{@code ordinal()}获取{@code DayOfWeek}的数字表示形式。请改用{@code getValue()}。</b>
 * <p>
 *  这个枚举表示在许多日历系统中找到的常见概念。因此,该枚举可以由具有与ISO日历系统完全等效的星期几概念的任何日历系统使用。
 * 
 *  @implSpec这是一个不可变和线程安全的枚举。
 * 
 * 
 * @since 1.8
 */
public enum DayOfWeek implements TemporalAccessor, TemporalAdjuster {

    /**
     * The singleton instance for the day-of-week of Monday.
     * This has the numeric value of {@code 1}.
     * <p>
     *  星期一的星期几的单例实例。它具有{@code 1}的数值。
     * 
     */
    MONDAY,
    /**
     * The singleton instance for the day-of-week of Tuesday.
     * This has the numeric value of {@code 2}.
     * <p>
     *  星期二的星期几的单例实例。它具有{@code 2}的数值。
     * 
     */
    TUESDAY,
    /**
     * The singleton instance for the day-of-week of Wednesday.
     * This has the numeric value of {@code 3}.
     * <p>
     *  星期三的星期几的单例实例。它具有{@code 3}的数值。
     * 
     */
    WEDNESDAY,
    /**
     * The singleton instance for the day-of-week of Thursday.
     * This has the numeric value of {@code 4}.
     * <p>
     *  星期四的星期几的单例实例。它的数值为{@code 4}。
     * 
     */
    THURSDAY,
    /**
     * The singleton instance for the day-of-week of Friday.
     * This has the numeric value of {@code 5}.
     * <p>
     *  星期五的星期几的单例实例。它具有{@code 5}的数值。
     * 
     */
    FRIDAY,
    /**
     * The singleton instance for the day-of-week of Saturday.
     * This has the numeric value of {@code 6}.
     * <p>
     *  星期六的星期几的单例实例。它具有{@code 6}的数值。
     * 
     */
    SATURDAY,
    /**
     * The singleton instance for the day-of-week of Sunday.
     * This has the numeric value of {@code 7}.
     * <p>
     *  星期日的星期几的单例实例。它的数值为{@code 7}。
     * 
     */
    SUNDAY;
    /**
     * Private cache of all the constants.
     * <p>
     *  所有常量的私有缓存。
     * 
     */
    private static final DayOfWeek[] ENUMS = DayOfWeek.values();

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code DayOfWeek} from an {@code int} value.
     * <p>
     * {@code DayOfWeek} is an enum representing the 7 days of the week.
     * This factory allows the enum to be obtained from the {@code int} value.
     * The {@code int} value follows the ISO-8601 standard, from 1 (Monday) to 7 (Sunday).
     *
     * <p>
     *  从{@code int}值获取{@code DayOfWeek}的实例。
     * <p>
     *  {@code DayOfWeek}是表示一周7天的枚举。此工厂允许从{@code int}值获取枚举。 {@code int}值遵循ISO-8601标准,从1(星期一)到7(星期日)。
     * 
     * 
     * @param dayOfWeek  the day-of-week to represent, from 1 (Monday) to 7 (Sunday)
     * @return the day-of-week singleton, not null
     * @throws DateTimeException if the day-of-week is invalid
     */
    public static DayOfWeek of(int dayOfWeek) {
        if (dayOfWeek < 1 || dayOfWeek > 7) {
            throw new DateTimeException("Invalid value for DayOfWeek: " + dayOfWeek);
        }
        return ENUMS[dayOfWeek - 1];
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code DayOfWeek} from a temporal object.
     * <p>
     * This obtains a day-of-week based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code DayOfWeek}.
     * <p>
     * The conversion extracts the {@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK} field.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used as a query via method reference, {@code DayOfWeek::from}.
     *
     * <p>
     *  从临时对象获取{@code DayOfWeek}的实例。
     * <p>
     * 这基于指定的时间获得星期几。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂将其转换为{@code DayOfWeek}的实例。
     * <p>
     *  转换会提取{@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK}字段。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许它通过方法引用{@code DayOfWeek :: from}用作查询。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the day-of-week, not null
     * @throws DateTimeException if unable to convert to a {@code DayOfWeek}
     */
    public static DayOfWeek from(TemporalAccessor temporal) {
        if (temporal instanceof DayOfWeek) {
            return (DayOfWeek) temporal;
        }
        try {
            return of(temporal.get(DAY_OF_WEEK));
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain DayOfWeek from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the day-of-week {@code int} value.
     * <p>
     * The values are numbered following the ISO-8601 standard, from 1 (Monday) to 7 (Sunday).
     * See {@link WeekFields#dayOfWeek} for localized week-numbering.
     *
     * <p>
     *  获取星期几{@code int}值。
     * <p>
     *  这些值按照ISO-8601标准编号,从1(星期一)到7(星期日)。有关本地化的星期编号,请参阅{@link WeekFields#dayOfWeek}。
     * 
     * 
     * @return the day-of-week, from 1 (Monday) to 7 (Sunday)
     */
    public int getValue() {
        return ordinal() + 1;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the textual representation, such as 'Mon' or 'Friday'.
     * <p>
     * This returns the textual name used to identify the day-of-week,
     * suitable for presentation to the user.
     * The parameters control the style of the returned text and the locale.
     * <p>
     * If no textual mapping is found then the {@link #getValue() numeric value} is returned.
     *
     * <p>
     *  获取文本表示形式,例如"星期一"或"星期五"。
     * <p>
     *  这返回用于标识星期几的文本名称,适合于向用户呈现。参数控制返回的文本和语言环境的样式。
     * <p>
     *  如果没有找到文本映射,则返回{@link #getValue()数字值}。
     * 
     * 
     * @param style  the length of the text required, not null
     * @param locale  the locale to use, not null
     * @return the text value of the day-of-week, not null
     */
    public String getDisplayName(TextStyle style, Locale locale) {
        return new DateTimeFormatterBuilder().appendText(DAY_OF_WEEK, style).toFormatter(locale).format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this day-of-week can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range} and
     * {@link #get(TemporalField) get} methods will throw an exception.
     * <p>
     * If the field is {@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK} then
     * this method returns true.
     * All other {@code ChronoField} instances will return false.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.isSupportedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the field is supported is determined by the field.
     *
     * <p>
     *  检查是否支持指定的字段。
     * <p>
     *  这将检查是否可以查询指定字段的星期几。
     * 如果为false,则调用{@link #range(TemporalField)range}和{@link #get(TemporalField)get}方法将抛出异常。
     * <p>
     *  如果字段为{@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK},则此方法返回true。所有其他{@code ChronoField}实例将返回false。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作为
     * 参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this day-of-week, false if not
     */
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field == DAY_OF_WEEK;
        }
        return field != null && field.isSupportedBy(this);
    }

    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This day-of-week is used to enhance the accuracy of the returned range.
     * If it is not possible to return the range, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is {@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK} then the
     * range of the day-of-week, from 1 to 7, will be returned.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.rangeRefinedBy(TemporalAccessor)}
     * passing {@code this} as the argument.
     * Whether the range can be obtained is determined by the field.
     *
     * <p>
     *  获取指定字段的有效值范围。
     * <p>
     *  范围对象表示字段的最小和最大有效值。这个星期用于提高返回范围的准确性。如果不可能返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果该字段为{@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK},则将返回星期几的范围(从1到7)。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.rangeRefinedBy(TemporalAccessor)}传递{@code this}
     * 作为参数来获得此方法的结果。
     * 是否可以获得范围由字段确定。
     * 
     * 
     * @param field  the field to query the range for, not null
     * @return the range of valid values for the field, not null
     * @throws DateTimeException if the range for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     */
    @Override
    public ValueRange range(TemporalField field) {
        if (field == DAY_OF_WEEK) {
            return field.range();
        }
        return TemporalAccessor.super.range(field);
    }

    /**
     * Gets the value of the specified field from this day-of-week as an {@code int}.
     * <p>
     * This queries this day-of-week for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is {@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK} then the
     * value of the day-of-week, from 1 to 7, will be returned.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从这个星期中获取指定字段的值作为{@code int}。
     * <p>
     *  这将查询指定字段的值的星期几。返回的值将始终在字段的有效值范围内。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     * 如果该字段为{@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK},则将返回星期几的值(从1到7)。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * 是否可以获取该值以及该值表示什么,由字段确定。
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
    @Override
    public int get(TemporalField field) {
        if (field == DAY_OF_WEEK) {
            return getValue();
        }
        return TemporalAccessor.super.get(field);
    }

    /**
     * Gets the value of the specified field from this day-of-week as a {@code long}.
     * <p>
     * This queries this day-of-week for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is {@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK} then the
     * value of the day-of-week, from 1 to 7, will be returned.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从这个星期中获取指定字段的值为{@code long}。
     * <p>
     *  这将查询指定字段的值的星期几。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果该字段为{@link ChronoField#DAY_OF_WEEK DAY_OF_WEEK},则将返回星期几的值(从1到7)。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.getFrom(TemporalAccessor)}传递{@code this}作为参数来获得
     * 此方法的结果。
     * 是否可以获取该值以及该值表示什么,由字段确定。
     * 
     * 
     * @param field  the field to get, not null
     * @return the value for the field
     * @throws DateTimeException if a value for the field cannot be obtained
     * @throws UnsupportedTemporalTypeException if the field is not supported
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public long getLong(TemporalField field) {
        if (field == DAY_OF_WEEK) {
            return getValue();
        } else if (field instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.getFrom(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the day-of-week that is the specified number of days after this one.
     * <p>
     * The calculation rolls around the end of the week from Sunday to Monday.
     * The specified period may be negative.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回在此日之后指定天数的星期几。
     * <p>
     *  计算从周日到周一结束。指定的期间可以为负。
     * <p>
     * 此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param days  the days to add, positive or negative
     * @return the resulting day-of-week, not null
     */
    public DayOfWeek plus(long days) {
        int amount = (int) (days % 7);
        return ENUMS[(ordinal() + (amount + 7)) % 7];
    }

    /**
     * Returns the day-of-week that is the specified number of days before this one.
     * <p>
     * The calculation rolls around the start of the year from Monday to Sunday.
     * The specified period may be negative.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回在此日期之前指定天数的星期几。
     * <p>
     *  计算在周一到周日的年初开始。指定的期间可以为负。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param days  the days to subtract, positive or negative
     * @return the resulting day-of-week, not null
     */
    public DayOfWeek minus(long days) {
        return plus(-(days % 7));
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this day-of-week using the specified query.
     * <p>
     * This queries this day-of-week using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此星期几。
     * <p>
     *  这将使用指定的查询策略对象查询此星期几。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
     * <p>
     *  此方法的结果是通过对指定的查询调用{@link TemporalQuery#queryFrom(TemporalAccessor)}方法传递{@code this}作为参数来获得的。
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
    public <R> R query(TemporalQuery<R> query) {
        if (query == TemporalQueries.precision()) {
            return (R) DAYS;
        }
        return TemporalAccessor.super.query(query);
    }

    /**
     * Adjusts the specified temporal object to have this day-of-week.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the day-of-week changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * passing {@link ChronoField#DAY_OF_WEEK} as the field.
     * Note that this adjusts forwards or backwards within a Monday to Sunday week.
     * See {@link WeekFields#dayOfWeek} for localized week start days.
     * See {@code TemporalAdjuster} for other adjusters with more control,
     * such as {@code next(MONDAY)}.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisDayOfWeek.adjustInto(temporal);
     *   temporal = temporal.with(thisDayOfWeek);
     * </pre>
     * <p>
     * For example, given a date that is a Wednesday, the following are output:
     * <pre>
     *   dateOnWed.with(MONDAY);     // two days earlier
     *   dateOnWed.with(TUESDAY);    // one day earlier
     *   dateOnWed.with(WEDNESDAY);  // same date
     *   dateOnWed.with(THURSDAY);   // one day later
     *   dateOnWed.with(FRIDAY);     // two days later
     *   dateOnWed.with(SATURDAY);   // three days later
     *   dateOnWed.with(SUNDAY);     // four days later
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为具有此星期几。
     * <p>
     *  这返回一个与输入相同的observable类型的时间对象,其中星期更改为与此相同。
     * <p>
     *  该调整等同于使用{@link Temporal#with(TemporalField,long)}传递{@link ChronoField#DAY_OF_WEEK}作为字段。
     * 请注意,这是在星期一至星期日向前或向后调整。有关本地化周开始日期,请参阅{@link WeekFields#dayOfWeek}。
     * 有关更多控制项的其他调整项(例如{@code next(MONDAY)}),请参阅{@code TemporalAdjuster}。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     * //这两行是等价的,但第二种方法是推荐temporal = thisDayOfWeek.adjustInto(temporal); temporal = temporal.with(thisDayOfW
     * eek);。
     * 
     * @param temporal  the target object to be adjusted, not null
     * @return the adjusted object, not null
     * @throws DateTimeException if unable to make the adjustment
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(DAY_OF_WEEK, getValue());
    }

}
