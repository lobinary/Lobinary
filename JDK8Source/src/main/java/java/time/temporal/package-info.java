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

/**
 * <p>
 * Access to date and time using fields and units, and date time adjusters.
 * </p>
 * <p>
 * This package expands on the base package to provide additional functionality for
 * more powerful use cases. Support is included for:
 * </p>
 * <ul>
 * <li>Units of date-time, such as years, months, days and hours</li>
 * <li>Fields of date-time, such as month-of-year, day-of-week or hour-of-day</li>
 * <li>Date-time adjustment functions</li>
 * <li>Different definitions of weeks</li>
 * </ul>
 *
 * <h3>Fields and Units</h3>
 * <p>
 * Dates and times are expressed in terms of fields and units.
 * A unit is used to measure an amount of time, such as years, days or minutes.
 * All units implement {@link java.time.temporal.TemporalUnit}.
 * The set of well known units is defined in {@link java.time.temporal.ChronoUnit}, such as {@code DAYS}.
 * The unit interface is designed to allow application defined units.
 * </p>
 * <p>
 * A field is used to express part of a larger date-time, such as year, month-of-year or second-of-minute.
 * All fields implement {@link java.time.temporal.TemporalField}.
 * The set of well known fields are defined in {@link java.time.temporal.ChronoField}, such as {@code HOUR_OF_DAY}.
 * Additional fields are defined by {@link java.time.temporal.JulianFields}, {@link java.time.temporal.WeekFields}
 * and {@link java.time.temporal.IsoFields}.
 * The field interface is designed to allow application defined fields.
 * </p>
 * <p>
 * This package provides tools that allow the units and fields of date and time to be accessed
 * in a general way most suited for frameworks.
 * {@link java.time.temporal.Temporal} provides the abstraction for date time types that support fields.
 * Its methods support getting the value of a field, creating a new date time with the value of
 * a field modified, and querying for additional information, typically used to extract the offset or time-zone.
 * </p>
 * <p>
 * One use of fields in application code is to retrieve fields for which there is no convenience method.
 * For example, getting the day-of-month is common enough that there is a method on {@code LocalDate}
 * called {@code getDayOfMonth()}. However for more unusual fields it is necessary to use the field.
 * For example, {@code date.get(ChronoField.ALIGNED_WEEK_OF_MONTH)}.
 * The fields also provide access to the range of valid values.
 * </p>
 *
 * <h3>Adjustment and Query</h3>
 * <p>
 * A key part of the date-time problem space is adjusting a date to a new, related value,
 * such as the "last day of the month", or "next Wednesday".
 * These are modeled as functions that adjust a base date-time.
 * The functions implement {@link java.time.temporal.TemporalAdjuster} and operate on {@code Temporal}.
 * A set of common functions are provided in {@link java.time.temporal.TemporalAdjusters}.
 * For example, to find the first occurrence of a day-of-week after a given date, use
 * {@link java.time.temporal.TemporalAdjusters#next(DayOfWeek)}, such as
 * {@code date.with(next(MONDAY))}.
 * Applications can also define adjusters by implementing {@link java.time.temporal.TemporalAdjuster}.
 * </p>
 * <p>
 * The {@link java.time.temporal.TemporalAmount} interface models amounts of relative time.
 * </p>
 * <p>
 * In addition to adjusting a date-time, an interface is provided to enable querying via
 * {@link java.time.temporal.TemporalQuery}.
 * The most common implementations of the query interface are method references.
 * The {@code from(TemporalAccessor)} methods on major classes can all be used, such as
 * {@code LocalDate::from} or {@code Month::from}.
 * Further implementations are provided in {@link java.time.temporal.TemporalQueries} as static methods.
 * Applications can also define queries by implementing {@link java.time.temporal.TemporalQuery}.
 * </p>
 *
 * <h3>Weeks</h3>
 * <p>
 * Different locales have different definitions of the week.
 * For example, in Europe the week typically starts on a Monday, while in the US it starts on a Sunday.
 * The {@link java.time.temporal.WeekFields} class models this distinction.
 * </p>
 * <p>
 * The ISO calendar system defines an additional week-based division of years.
 * This defines a year based on whole Monday to Monday weeks.
 * This is modeled in {@link java.time.temporal.IsoFields}.
 * </p>
 *
 * <h3>Package specification</h3>
 * <p>
 * Unless otherwise noted, passing a null argument to a constructor or method in any class or interface
 * in this package will cause a {@link java.lang.NullPointerException NullPointerException} to be thrown.
 * <p>
 * <p>
 *  使用字段和单位以及日期时间调整器访问日期和时间。
 * </p>
 * <p>
 *  此包在基础包上扩展,以提供更强大的用例的附加功能。支持包括：
 * </p>
 * <ul>
 *  <li>日期时间单位,例如年,月,日和小时</li> <li>日期时间字段,例如年月日,星期几或小时</li> <li>日期时间调整功能</li> <li>周的不同定义</li>
 * </ul>
 * 
 *  <h3>字段和单位</h3>
 * <p>
 * 日期和时间以字段和单位表示。单位用于测量时间量,如年,天或分钟。所有单位都实现{@link java.time.temporal.TemporalUnit}。
 *  {@link java.time.temporal.ChronoUnit}中定义了一组众所周知的单位,例如{@code DAYS}。单元接口被设计为允许应用定义的单元。
 * </p>
 * <p>
 *  字段用于表示较大日期时间的一部分,例如年,月或秒。所有字段都实现{@link java.time.temporal.TemporalField}。
 * 知名字段集在{@link java.time.temporal.ChronoField}中定义,例如{@code HOUR_OF_DAY}。
 * 其他字段由{@link java.time.temporal.JulianFields},{@link java.time.temporal.WeekFields}和{@link java.time.temporal.IsoFields}
 * 定义。
 * 知名字段集在{@link java.time.temporal.ChronoField}中定义,例如{@code HOUR_OF_DAY}。字段接口被设计为允许应用程序定义的字段。
 * </p>
 * <p>
 *  此软件包提供的工具允许以最适合框架的通用方式访问日期和时间的单位和字段。 {@link java.time.temporal.Temporal}提供了支持字段的日期时间类型的抽象。
 * 其方法支持获取字段的值,创建具有修改的字段的值的新的日期时间,并查询附加信息,通常用于提取偏移或时区。
 * </p>
 * <p>
 * 应用程序代码中字段的一种使用是检索没有方便方法的字段。例如,获取日期是常见的,在{@code LocalDate}上有一个名为{@code getDayOfMonth()}的方法。
 * 然而,对于更不寻常的字段,有必要使用字段。例如,{@code date.get(ChronoField.ALIGNED_WEEK_OF_MONTH)}。这些字段还提供对有效值范围的访问。
 * </p>
 * 
 *  <h3>调整和查询</h3>
 * 
 * The Javadoc "@param" definition is used to summarise the null-behavior.
 * The "@throws {@link java.lang.NullPointerException}" is not explicitly documented in each method.
 * </p>
 * <p>
 * All calculations should check for numeric overflow and throw either an {@link java.lang.ArithmeticException}
 * or a {@link java.time.DateTimeException}.
 * </p>
 * @since JDK1.8
 */
package java.time.temporal;
