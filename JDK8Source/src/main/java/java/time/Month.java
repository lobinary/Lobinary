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

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoUnit.MONTHS;

import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
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
import java.util.Locale;

/**
 * A month-of-year, such as 'July'.
 * <p>
 * {@code Month} is an enum representing the 12 months of the year -
 * January, February, March, April, May, June, July, August, September, October,
 * November and December.
 * <p>
 * In addition to the textual enum name, each month-of-year has an {@code int} value.
 * The {@code int} value follows normal usage and the ISO-8601 standard,
 * from 1 (January) to 12 (December). It is recommended that applications use the enum
 * rather than the {@code int} value to ensure code clarity.
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code Month}.
 * Use {@code getValue()} instead.</b>
 * <p>
 * This enum represents a common concept that is found in many calendar systems.
 * As such, this enum may be used by any calendar system that has the month-of-year
 * concept defined exactly equivalent to the ISO-8601 calendar system.
 *
 * @implSpec
 * This is an immutable and thread-safe enum.
 *
 * <p>
 *  一个月,例如"七月"。
 * <p>
 *  {@code Month}是表示一年,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月和十二月的12个月的枚举。
 * <p>
 *  除了文本枚举名称外,每个月份都有一个{@code int}值。 {@code int}值遵循正常使用和ISO-8601标准,从1(1月)到12(12月)。
 * 建议应用程序使用枚举而不是{@code int}值来确保代码的清晰度。
 * <p>
 *  <b>不要使用{@code ordinal()}来获取{@code Month}的数字表示形式。请改用{@code getValue()}。</b>
 * <p>
 * 这个枚举表示在许多日历系统中找到的常见概念。因此,该枚举可以由具有严格等同于ISO-8601日历系统的年份概念的任何日历系统使用。
 * 
 *  @implSpec这是一个不可变和线程安全的枚举。
 * 
 * 
 * @since 1.8
 */
public enum Month implements TemporalAccessor, TemporalAdjuster {

    /**
     * The singleton instance for the month of January with 31 days.
     * This has the numeric value of {@code 1}.
     * <p>
     *  1月份的单例实例与31天。它具有{@code 1}的数值。
     * 
     */
    JANUARY,
    /**
     * The singleton instance for the month of February with 28 days, or 29 in a leap year.
     * This has the numeric value of {@code 2}.
     * <p>
     *  2月份的单例实例为28天,或29个闰年。它具有{@code 2}的数值。
     * 
     */
    FEBRUARY,
    /**
     * The singleton instance for the month of March with 31 days.
     * This has the numeric value of {@code 3}.
     * <p>
     *  3月份的单例实例为31天。它具有{@code 3}的数值。
     * 
     */
    MARCH,
    /**
     * The singleton instance for the month of April with 30 days.
     * This has the numeric value of {@code 4}.
     * <p>
     *  4月份的单例实例为30天。它的数值为{@code 4}。
     * 
     */
    APRIL,
    /**
     * The singleton instance for the month of May with 31 days.
     * This has the numeric value of {@code 5}.
     * <p>
     *  5月份的单例实例有31天。它具有{@code 5}的数值。
     * 
     */
    MAY,
    /**
     * The singleton instance for the month of June with 30 days.
     * This has the numeric value of {@code 6}.
     * <p>
     *  6月份的单例实例为30天。它具有{@code 6}的数值。
     * 
     */
    JUNE,
    /**
     * The singleton instance for the month of July with 31 days.
     * This has the numeric value of {@code 7}.
     * <p>
     *  7月的单例实例为31天。它的数值为{@code 7}。
     * 
     */
    JULY,
    /**
     * The singleton instance for the month of August with 31 days.
     * This has the numeric value of {@code 8}.
     * <p>
     *  8月份的单例实例为31天。它的数值为{@code 8}。
     * 
     */
    AUGUST,
    /**
     * The singleton instance for the month of September with 30 days.
     * This has the numeric value of {@code 9}.
     * <p>
     *  9月的单例实例为30天。它具有{@code 9}的数值。
     * 
     */
    SEPTEMBER,
    /**
     * The singleton instance for the month of October with 31 days.
     * This has the numeric value of {@code 10}.
     * <p>
     *  10月份的单例实例为31天。它的数值为{@code 10}。
     * 
     */
    OCTOBER,
    /**
     * The singleton instance for the month of November with 30 days.
     * This has the numeric value of {@code 11}.
     * <p>
     *  11月份的单例实例为30天。它具有{@code 11}的数值。
     * 
     */
    NOVEMBER,
    /**
     * The singleton instance for the month of December with 31 days.
     * This has the numeric value of {@code 12}.
     * <p>
     * 12月的单例实例为31天。它具有{@code 12}的数值。
     * 
     */
    DECEMBER;
    /**
     * Private cache of all the constants.
     * <p>
     *  所有常量的私有缓存。
     * 
     */
    private static final Month[] ENUMS = Month.values();

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Month} from an {@code int} value.
     * <p>
     * {@code Month} is an enum representing the 12 months of the year.
     * This factory allows the enum to be obtained from the {@code int} value.
     * The {@code int} value follows the ISO-8601 standard, from 1 (January) to 12 (December).
     *
     * <p>
     *  从{@code int}值获取{@code Month}的实例。
     * <p>
     *  {@code Month}是表示一年中12个月的枚举。此工厂允许从{@code int}值获取枚举。 {@code int}值遵循ISO-8601标准,从1(1月)到12(12月)。
     * 
     * 
     * @param month  the month-of-year to represent, from 1 (January) to 12 (December)
     * @return the month-of-year, not null
     * @throws DateTimeException if the month-of-year is invalid
     */
    public static Month of(int month) {
        if (month < 1 || month > 12) {
            throw new DateTimeException("Invalid value for MonthOfYear: " + month);
        }
        return ENUMS[month - 1];
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code Month} from a temporal object.
     * <p>
     * This obtains a month based on the specified temporal.
     * A {@code TemporalAccessor} represents an arbitrary set of date and time information,
     * which this factory converts to an instance of {@code Month}.
     * <p>
     * The conversion extracts the {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} field.
     * The extraction is only permitted if the temporal object has an ISO
     * chronology, or can be converted to a {@code LocalDate}.
     * <p>
     * This method matches the signature of the functional interface {@link TemporalQuery}
     * allowing it to be used in queries via method reference, {@code Month::from}.
     *
     * <p>
     *  从临时对象获取{@code Month}的实例。
     * <p>
     *  这基于指定的时间获得一个月。 {@code TemporalAccessor}表示一组任意的日期和时间信息,此工厂会将其转换为{@code Month}的实例。
     * <p>
     *  转换会提取{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR}字段。
     * 仅当时间对象具有ISO年表时,才允许提取,或者可以将其转换为{@code LocalDate}。
     * <p>
     *  此方法匹配功能接口{@link TemporalQuery}的签名,允许通过方法引用{@code Month :: from}在查询中使用它。
     * 
     * 
     * @param temporal  the temporal object to convert, not null
     * @return the month-of-year, not null
     * @throws DateTimeException if unable to convert to a {@code Month}
     */
    public static Month from(TemporalAccessor temporal) {
        if (temporal instanceof Month) {
            return (Month) temporal;
        }
        try {
            if (IsoChronology.INSTANCE.equals(Chronology.from(temporal)) == false) {
                temporal = LocalDate.from(temporal);
            }
            return of(temporal.get(MONTH_OF_YEAR));
        } catch (DateTimeException ex) {
            throw new DateTimeException("Unable to obtain Month from TemporalAccessor: " +
                    temporal + " of type " + temporal.getClass().getName(), ex);
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the month-of-year {@code int} value.
     * <p>
     * The values are numbered following the ISO-8601 standard,
     * from 1 (January) to 12 (December).
     *
     * <p>
     *  获取年度{@code int}值。
     * <p>
     *  这些值按照ISO-8601标准编号,从1(1月)到12(12月)。
     * 
     * 
     * @return the month-of-year, from 1 (January) to 12 (December)
     */
    public int getValue() {
        return ordinal() + 1;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the textual representation, such as 'Jan' or 'December'.
     * <p>
     * This returns the textual name used to identify the month-of-year,
     * suitable for presentation to the user.
     * The parameters control the style of the returned text and the locale.
     * <p>
     * If no textual mapping is found then the {@link #getValue() numeric value} is returned.
     *
     * <p>
     *  获取文本表示形式,例如"Jan"或"December"。
     * <p>
     *  这返回用于标识年份的文本名称,适合向用户呈现。参数控制返回的文本和语言环境的样式。
     * <p>
     * 如果没有找到文本映射,则返回{@link #getValue()数字值}。
     * 
     * 
     * @param style  the length of the text required, not null
     * @param locale  the locale to use, not null
     * @return the text value of the month-of-year, not null
     */
    public String getDisplayName(TextStyle style, Locale locale) {
        return new DateTimeFormatterBuilder().appendText(MONTH_OF_YEAR, style).toFormatter(locale).format(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if the specified field is supported.
     * <p>
     * This checks if this month-of-year can be queried for the specified field.
     * If false, then calling the {@link #range(TemporalField) range} and
     * {@link #get(TemporalField) get} methods will throw an exception.
     * <p>
     * If the field is {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} then
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
     *  这将检查是否可以查询指定字段的年份。
     * 如果为false,则调用{@link #range(TemporalField)range}和{@link #get(TemporalField)get}方法将抛出异常。
     * <p>
     *  如果字段为{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR},则此方法返回true。所有其他{@code ChronoField}实例将返回false。
     * <p>
     *  如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.isSupportedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
     * 字段是否受支持由字段确定。
     * 
     * 
     * @param field  the field to check, null returns false
     * @return true if the field is supported on this month-of-year, false if not
     */
    @Override
    public boolean isSupported(TemporalField field) {
        if (field instanceof ChronoField) {
            return field == MONTH_OF_YEAR;
        }
        return field != null && field.isSupportedBy(this);
    }

    /**
     * Gets the range of valid values for the specified field.
     * <p>
     * The range object expresses the minimum and maximum valid values for a field.
     * This month is used to enhance the accuracy of the returned range.
     * If it is not possible to return the range, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} then the
     * range of the month-of-year, from 1 to 12, will be returned.
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
     *  范围对象表示字段的最小和最大有效值。本月用于提高返回范围的精度。如果不可能返回范围,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果字段为{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR},则将返回年份的范围(从1到12)。
     * 所有其他{@code ChronoField}实例将抛出{@code UnsupportedTemporalTypeException}。
     * <p>
     * 如果字段不是{@code ChronoField},那么通过调用{@code TemporalField.rangeRefinedBy(TemporalAccessor)}传递{@code this}作
     * 为参数来获得此方法的结果。
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
        if (field == MONTH_OF_YEAR) {
            return field.range();
        }
        return TemporalAccessor.super.range(field);
    }

    /**
     * Gets the value of the specified field from this month-of-year as an {@code int}.
     * <p>
     * This queries this month for the value for the specified field.
     * The returned value will always be within the valid range of values for the field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} then the
     * value of the month-of-year, from 1 to 12, will be returned.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从这个月中获取指定字段的值作为{@code int}。
     * <p>
     *  这将在本月查询指定字段的值。返回的值将始终在字段的有效值范围内。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     *  如果该字段为{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR},则将返回年份的值(从1到12)。
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
        if (field == MONTH_OF_YEAR) {
            return getValue();
        }
        return TemporalAccessor.super.get(field);
    }

    /**
     * Gets the value of the specified field from this month-of-year as a {@code long}.
     * <p>
     * This queries this month for the value for the specified field.
     * If it is not possible to return the value, because the field is not supported
     * or for some other reason, an exception is thrown.
     * <p>
     * If the field is {@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR} then the
     * value of the month-of-year, from 1 to 12, will be returned.
     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
     * <p>
     * If the field is not a {@code ChronoField}, then the result of this method
     * is obtained by invoking {@code TemporalField.getFrom(TemporalAccessor)}
     * passing {@code this} as the argument. Whether the value can be obtained,
     * and what the value represents, is determined by the field.
     *
     * <p>
     *  从这个月份获取指定字段的值为{@code long}。
     * <p>
     *  这将在本月查询指定字段的值。如果不可能返回值,因为该字段不受支持或由于某种其他原因,将抛出异常。
     * <p>
     * 如果该字段为{@link ChronoField#MONTH_OF_YEAR MONTH_OF_YEAR},则将返回年份的值(从1到12)。
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
        if (field == MONTH_OF_YEAR) {
            return getValue();
        } else if (field instanceof ChronoField) {
            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
        }
        return field.getFrom(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the month-of-year that is the specified number of quarters after this one.
     * <p>
     * The calculation rolls around the end of the year from December to January.
     * The specified period may be negative.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回在此之后的指定季度数的年份。
     * <p>
     *  计算大约在年底从12月到1月。指定的期间可以为负。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param months  the months to add, positive or negative
     * @return the resulting month, not null
     */
    public Month plus(long months) {
        int amount = (int) (months % 12);
        return ENUMS[(ordinal() + (amount + 12)) % 12];
    }

    /**
     * Returns the month-of-year that is the specified number of months before this one.
     * <p>
     * The calculation rolls around the start of the year from January to December.
     * The specified period may be negative.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  返回此月之前指定月数的月份。
     * <p>
     *  计算在1月至12月的年初开始。指定的期间可以为负。
     * <p>
     *  此实例是不可变的,不受此方法调用的影响。
     * 
     * 
     * @param months  the months to subtract, positive or negative
     * @return the resulting month, not null
     */
    public Month minus(long months) {
        return plus(-(months % 12));
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the length of this month in days.
     * <p>
     * This takes a flag to determine whether to return the length for a leap year or not.
     * <p>
     * February has 28 days in a standard year and 29 days in a leap year.
     * April, June, September and November have 30 days.
     * All other months have 31 days.
     *
     * <p>
     *  获取本月的长度(以天为单位)。
     * <p>
     *  这需要一个标志来确定是否返回闰年的长度。
     * <p>
     *  2月在标准年份有28天,在闰年有29天。 4月,6月,9月和11月有30天。所有其他月份有31天。
     * 
     * 
     * @param leapYear  true if the length is required for a leap year
     * @return the length of this month in days, from 28 to 31
     */
    public int length(boolean leapYear) {
        switch (this) {
            case FEBRUARY:
                return (leapYear ? 29 : 28);
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                return 30;
            default:
                return 31;
        }
    }

    /**
     * Gets the minimum length of this month in days.
     * <p>
     * February has a minimum length of 28 days.
     * April, June, September and November have 30 days.
     * All other months have 31 days.
     *
     * <p>
     *  获取本月的最小长度(以天为单位)。
     * <p>
     *  2月最短期限为28天。 4月,6月,9月和11月有30天。所有其他月份有31天。
     * 
     * 
     * @return the minimum length of this month in days, from 28 to 31
     */
    public int minLength() {
        switch (this) {
            case FEBRUARY:
                return 28;
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                return 30;
            default:
                return 31;
        }
    }

    /**
     * Gets the maximum length of this month in days.
     * <p>
     * February has a maximum length of 29 days.
     * April, June, September and November have 30 days.
     * All other months have 31 days.
     *
     * <p>
     * 获取本月的最大长度(以天为单位)。
     * <p>
     *  2月的最大长度为29天。 4月,6月,9月和11月有30天。所有其他月份有31天。
     * 
     * 
     * @return the maximum length of this month in days, from 29 to 31
     */
    public int maxLength() {
        switch (this) {
            case FEBRUARY:
                return 29;
            case APRIL:
            case JUNE:
            case SEPTEMBER:
            case NOVEMBER:
                return 30;
            default:
                return 31;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the day-of-year corresponding to the first day of this month.
     * <p>
     * This returns the day-of-year that this month begins on, using the leap
     * year flag to determine the length of February.
     *
     * <p>
     *  获取与本月第一天对应的年份。
     * <p>
     *  这将返回本月开始的年,使用闰年标志确定2月的长度。
     * 
     * 
     * @param leapYear  true if the length is required for a leap year
     * @return the day of year corresponding to the first day of this month, from 1 to 336
     */
    public int firstDayOfYear(boolean leapYear) {
        int leap = leapYear ? 1 : 0;
        switch (this) {
            case JANUARY:
                return 1;
            case FEBRUARY:
                return 32;
            case MARCH:
                return 60 + leap;
            case APRIL:
                return 91 + leap;
            case MAY:
                return 121 + leap;
            case JUNE:
                return 152 + leap;
            case JULY:
                return 182 + leap;
            case AUGUST:
                return 213 + leap;
            case SEPTEMBER:
                return 244 + leap;
            case OCTOBER:
                return 274 + leap;
            case NOVEMBER:
                return 305 + leap;
            case DECEMBER:
            default:
                return 335 + leap;
        }
    }

    /**
     * Gets the month corresponding to the first month of this quarter.
     * <p>
     * The year can be divided into four quarters.
     * This method returns the first month of the quarter for the base month.
     * January, February and March return January.
     * April, May and June return April.
     * July, August and September return July.
     * October, November and December return October.
     *
     * <p>
     *  获取本季度第一个月对应的月份。
     * <p>
     *  一年可以分为四个季度。此方法返回基准月份的季度的第一个月。 1月,2月和3月返回1月。 4月,5月和6月返回4月。 7月,8月和9月返回7月。 10月,11月和12月返回10月。
     * 
     * 
     * @return the first month of the quarter corresponding to this month, not null
     */
    public Month firstMonthOfQuarter() {
        return ENUMS[(ordinal() / 3) * 3];
    }

    //-----------------------------------------------------------------------
    /**
     * Queries this month-of-year using the specified query.
     * <p>
     * This queries this month-of-year using the specified query strategy object.
     * The {@code TemporalQuery} object defines the logic to be used to
     * obtain the result. Read the documentation of the query to understand
     * what the result of this method will be.
     * <p>
     * The result of this method is obtained by invoking the
     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
     * specified query passing {@code this} as the argument.
     *
     * <p>
     *  使用指定的查询查询此年份。
     * <p>
     *  这使用指定的查询策略对象查询这个年份。 {@code TemporalQuery}对象定义用于获取结果的逻辑。阅读查询的文档以了解此方法的结果。
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
        if (query == TemporalQueries.chronology()) {
            return (R) IsoChronology.INSTANCE;
        } else if (query == TemporalQueries.precision()) {
            return (R) MONTHS;
        }
        return TemporalAccessor.super.query(query);
    }

    /**
     * Adjusts the specified temporal object to have this month-of-year.
     * <p>
     * This returns a temporal object of the same observable type as the input
     * with the month-of-year changed to be the same as this.
     * <p>
     * The adjustment is equivalent to using {@link Temporal#with(TemporalField, long)}
     * passing {@link ChronoField#MONTH_OF_YEAR} as the field.
     * If the specified temporal object does not use the ISO calendar system then
     * a {@code DateTimeException} is thrown.
     * <p>
     * In most cases, it is clearer to reverse the calling pattern by using
     * {@link Temporal#with(TemporalAdjuster)}:
     * <pre>
     *   // these two lines are equivalent, but the second approach is recommended
     *   temporal = thisMonth.adjustInto(temporal);
     *   temporal = temporal.with(thisMonth);
     * </pre>
     * <p>
     * For example, given a date in May, the following are output:
     * <pre>
     *   dateInMay.with(JANUARY);    // four months earlier
     *   dateInMay.with(APRIL);      // one months earlier
     *   dateInMay.with(MAY);        // same date
     *   dateInMay.with(JUNE);       // one month later
     *   dateInMay.with(DECEMBER);   // seven months later
     * </pre>
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * <p>
     *  将指定的时间对象调整为具有这个年份。
     * <p>
     *  这返回一个与年月的输入相同的observable类型的时间对象更改为与此相同。
     * <p>
     * 该调整等同于使用{@link Temporal#with(TemporalField,long)}传递{@link ChronoField#MONTH_OF_YEAR}作为字段。
     * 如果指定的临时对象不使用ISO日历系统,那么将抛出{@code DateTimeException}。
     * <p>
     *  在大多数情况下,通过使用{@link Temporal#with(TemporalAdjuster)}来反转呼叫模式是更清楚的：
     * <pre>
     *  //这两行是等价的,但第二种方法是建议temporal = thisMonth.adjustInto(temporal); temporal = temporal.with(thisMonth);
     * 
     * @param temporal  the target object to be adjusted, not null
     * @return the adjusted object, not null
     * @throws DateTimeException if unable to make the adjustment
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public Temporal adjustInto(Temporal temporal) {
        if (Chronology.from(temporal).equals(IsoChronology.INSTANCE) == false) {
            throw new DateTimeException("Adjustment only supported on ISO date-time");
        }
        return temporal.with(MONTH_OF_YEAR, getValue());
    }

}
