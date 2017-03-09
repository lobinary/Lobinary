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
 * The main API for dates, times, instants, and durations.
 * </p>
 * <p>
 * The classes defined here represent the principal date-time concepts,
 * including instants, durations, dates, times, time-zones and periods.
 * They are based on the ISO calendar system, which is the <i>de facto</i> world
 * calendar following the proleptic Gregorian rules.
 * All the classes are immutable and thread-safe.
 * </p>
 * <p>
 * Each date time instance is composed of fields that are conveniently
 * made available by the APIs.  For lower level access to the fields refer
 * to the {@code java.time.temporal} package.
 * Each class includes support for printing and parsing all manner of dates and times.
 * Refer to the {@code java.time.format} package for customization options.
 * </p>
 * <p>
 * The {@code java.time.chrono} package contains the calendar neutral API
 * {@link java.time.chrono.ChronoLocalDate ChronoLocalDate},
 * {@link java.time.chrono.ChronoLocalDateTime ChronoLocalDateTime},
 * {@link java.time.chrono.ChronoZonedDateTime ChronoZonedDateTime} and
 * {@link java.time.chrono.Era Era}.
 * This is intended for use by applications that need to use localized calendars.
 * It is recommended that applications use the ISO-8601 date and time classes from
 * this package across system boundaries, such as to the database or across the network.
 * The calendar neutral API should be reserved for interactions with users.
 * </p>
 *
 * <h3>Dates and Times</h3>
 * <p>
 * {@link java.time.Instant} is essentially a numeric timestamp.
 * The current Instant can be retrieved from a {@link java.time.Clock}.
 * This is useful for logging and persistence of a point in time
 * and has in the past been associated with storing the result
 * from {@link java.lang.System#currentTimeMillis()}.
 * </p>
 * <p>
 * {@link java.time.LocalDate} stores a date without a time.
 * This stores a date like '2010-12-03' and could be used to store a birthday.
 * </p>
 * <p>
 * {@link java.time.LocalTime} stores a time without a date.
 * This stores a time like '11:30' and could be used to store an opening or closing time.
 * </p>
 * <p>
 * {@link java.time.LocalDateTime} stores a date and time.
 * This stores a date-time like '2010-12-03T11:30'.
 * </p>
 * <p>
 * {@link java.time.ZonedDateTime} stores a date and time with a time-zone.
 * This is useful if you want to perform accurate calculations of
 * dates and times taking into account the {@link java.time.ZoneId}, such as 'Europe/Paris'.
 * Where possible, it is recommended to use a simpler class without a time-zone.
 * The widespread use of time-zones tends to add considerable complexity to an application.
 * </p>
 *
 * <h3>Duration and Period</h3>
 * <p>
 * Beyond dates and times, the API also allows the storage of period and durations of time.
 * A {@link java.time.Duration} is a simple measure of time along the time-line in nanoseconds.
 * A {@link java.time.Period} expresses an amount of time in units meaningful to humans, such as years or hours.
 * </p>
 *
 * <h3>Additional value types</h3>
 * <p>
 * {@link java.time.Month} stores a month on its own.
 * This stores a single month-of-year in isolation, such as 'DECEMBER'.
 * </p>
 * <p>
 * {@link java.time.DayOfWeek} stores a day-of-week on its own.
 * This stores a single day-of-week in isolation, such as 'TUESDAY'.
 * </p>
 * <p>
 * {@link java.time.Year} stores a year on its own.
 * This stores a single year in isolation, such as '2010'.
 * </p>
 * <p>
 * {@link java.time.YearMonth} stores a year and month without a day or time.
 * This stores a year and month, such as '2010-12' and could be used for a credit card expiry.
 * </p>
 * <p>
 * {@link java.time.MonthDay} stores a month and day without a year or time.
 * This stores a month and day-of-month, such as '--12-03' and
 * could be used to store an annual event like a birthday without storing the year.
 * </p>
 * <p>
 * {@link java.time.OffsetTime} stores a time and offset from UTC without a date.
 * This stores a date like '11:30+01:00'.
 * The {@link java.time.ZoneOffset ZoneOffset} is of the form '+01:00'.
 * </p>
 * <p>
 * {@link java.time.OffsetDateTime} stores a date and time and offset from UTC.
 * This stores a date-time like '2010-12-03T11:30+01:00'.
 * This is sometimes found in XML messages and other forms of persistence,
 * but contains less information than a full time-zone.
 * </p>
 *
 * <h3>Package specification</h3>
 * <p>
 * Unless otherwise noted, passing a null argument to a constructor or method in any class or interface
 * in this package will cause a {@link java.lang.NullPointerException NullPointerException} to be thrown.
 * <p>
 * <p>
 *  日期,时间,时刻和持续时间的主要API。
 * </p>
 * <p>
 *  这里定义的类代表主要的日期 - 时间概念,包括时刻,持续时间,日期,时间,时区和时期。它们基于ISO日历系统,其是遵循推测的格雷戈里亚规则的<i>事实</i>世界日历。
 * 所有的类都是不可变的和线程安全的。
 * </p>
 * <p>
 *  每个日期时间实例由方便地由API可用的字段组成。对于对字段的低级访问,请参阅{@code java.time.temporal}包。每个类包括支持打印和解析所有方式的日期和时间。
 * 有关自定义选项,请参阅{@code java.time.format}包。
 * </p>
 * <p>
 * {@code java.time.chrono}套件包含日历中性API {@link java.time.chrono.ChronoLocalDate ChronoLocalDate},{@link java.time.chrono.ChronoLocalDateTime ChronoLocalDateTime}
 * ,{@link java.time.chrono .ChronoZonedDateTime ChronoZonedDateTime}和{@link java.time.chrono.Era Era}。
 * 这适用于需要使用本地化日历的应用程序。建议应用程序跨系统边界使用此包中的ISO-8601日期和时间类,例如数据库或网络。日历中性API应保留用于与用户的交互。
 * </p>
 * 
 *  <h3>日期和时间</h3>
 * <p>
 *  {@link java.time.Instant}本质上是一个数字时间戳。当前的Instant可以从{@link java.time.Clock}中检索。
 * 这对于记录和持久化一个时间点是有用的,并且在过去已经与存储来自{@link java.lang.System#currentTimeMillis()}的结果相关联。
 * </p>
 * <p>
 *  {@link java.time.LocalDate}存储没有时间的日期。它存储像"2010-12-03"这样的日期,并可用于存储生日。
 * </p>
 * <p>
 *  {@link java.time.LocalTime}存储没有日期的时间。它存储类似'11：30'的时间,并可用于存储开启或关闭时间。
 * </p>
 * <p>
 *  {@link java.time.LocalDateTime}存储日期和时间。它存储一个日期时间,如'2010-12-03T11：30'。
 * </p>
 * <p>
 * {@link java.time.ZonedDateTime}存储带有时区的日期和时间。
 * 如果您想要考虑{@link java.time.ZoneId}(如"Europe / Paris")执行准确的日期和时间计算,则此选项非常有用。在可能的情况下,建议使用没有时区的更简单的类。
 * 时区的广泛使用趋向于对应用增加相当大的复杂性。
 * </p>
 * 
 * 
 * The Javadoc "@param" definition is used to summarise the null-behavior.
 * The "@throws {@link java.lang.NullPointerException}" is not explicitly documented in each method.
 * </p>
 * <p>
 * All calculations should check for numeric overflow and throw either an {@link java.lang.ArithmeticException}
 * or a {@link java.time.DateTimeException}.
 * </p>
 *
 * <h3>Design notes (non normative)</h3>
 * <p>
 * The API has been designed to reject null early and to be clear about this behavior.
 * A key exception is any method that takes an object and returns a boolean, for the purpose
 * of checking or validating, will generally return false for null.
 * </p>
 * <p>
 * The API is designed to be type-safe where reasonable in the main high-level API.
 * Thus, there are separate classes for the distinct concepts of date, time and date-time,
 * plus variants for offset and time-zone.
 * This can seem like a lot of classes, but most applications can begin with just five date/time types.
 * <ul>
 * <li>{@link java.time.Instant} - a timestamp</li>
 * <li>{@link java.time.LocalDate} - a date without a time, or any reference to an offset or time-zone</li>
 * <li>{@link java.time.LocalTime} - a time without a date, or any reference to an offset or time-zone</li>
 * <li>{@link java.time.LocalDateTime} - combines date and time, but still without any offset or time-zone</li>
 * <li>{@link java.time.ZonedDateTime} - a "full" date-time with time-zone and resolved offset from UTC/Greenwich</li>
 * </ul>
 * <p>
 * {@code Instant} is the closest equivalent class to {@code java.util.Date}.
 * {@code ZonedDateTime} is the closest equivalent class to {@code java.util.GregorianCalendar}.
 * </p>
 * <p>
 * Where possible, applications should use {@code LocalDate}, {@code LocalTime} and {@code LocalDateTime}
 * to better model the domain. For example, a birthday should be stored in a code {@code LocalDate}.
 * Bear in mind that any use of a {@linkplain java.time.ZoneId time-zone}, such as 'Europe/Paris', adds
 * considerable complexity to a calculation.
 * Many applications can be written only using {@code LocalDate}, {@code LocalTime} and {@code Instant},
 * with the time-zone added at the user interface (UI) layer.
 * </p>
 * <p>
 * The offset-based date-time types {@code OffsetTime} and {@code OffsetDateTime},
 * are intended primarily for use with network protocols and database access.
 * For example, most databases cannot automatically store a time-zone like 'Europe/Paris', but
 * they can store an offset like '+02:00'.
 * </p>
 * <p>
 * Classes are also provided for the most important sub-parts of a date, including {@code Month},
 * {@code DayOfWeek}, {@code Year}, {@code YearMonth} and {@code MonthDay}.
 * These can be used to model more complex date-time concepts.
 * For example, {@code YearMonth} is useful for representing a credit card expiry.
 * </p>
 * <p>
 * Note that while there are a large number of classes representing different aspects of dates,
 * there are relatively few dealing with different aspects of time.
 * Following type-safety to its logical conclusion would have resulted in classes for
 * hour-minute, hour-minute-second and hour-minute-second-nanosecond.
 * While logically pure, this was not a practical option as it would have almost tripled the
 * number of classes due to the combinations of date and time.
 * Thus, {@code LocalTime} is used for all precisions of time, with zeroes used to imply lower precision.
 * </p>
 * <p>
 * Following full type-safety to its ultimate conclusion might also argue for a separate class
 * for each field in date-time, such as a class for HourOfDay and another for DayOfMonth.
 * This approach was tried, but was excessively complicated in the Java language, lacking usability.
 * A similar problem occurs with periods.
 * There is a case for a separate class for each period unit, such as a type for Years and a type for Minutes.
 * However, this yields a lot of classes and a problem of type conversion.
 * Thus, the set of date-time types provided is a compromise between purity and practicality.
 * </p>
 * <p>
 * The API has a relatively large surface area in terms of number of methods.
 * This is made manageable through the use of consistent method prefixes.
 * <ul>
 * <li>{@code of} - static factory method</li>
 * <li>{@code parse} - static factory method focussed on parsing</li>
 * <li>{@code get} - gets the value of something</li>
 * <li>{@code is} - checks if something is true</li>
 * <li>{@code with} - the immutable equivalent of a setter</li>
 * <li>{@code plus} - adds an amount to an object</li>
 * <li>{@code minus} - subtracts an amount from an object</li>
 * <li>{@code to} - converts this object to another type</li>
 * <li>{@code at} - combines this object with another, such as {@code date.atTime(time)}</li>
 * </ul>
 * <p>
 * Multiple calendar systems is an awkward addition to the design challenges.
 * The first principal is that most users want the standard ISO calendar system.
 * As such, the main classes are ISO-only. The second principal is that most of those that want a
 * non-ISO calendar system want it for user interaction, thus it is a UI localization issue.
 * As such, date and time objects should be held as ISO objects in the data model and persistent
 * storage, only being converted to and from a local calendar for display.
 * The calendar system would be stored separately in the user preferences.
 * </p>
 * <p>
 * There are, however, some limited use cases where users believe they need to store and use
 * dates in arbitrary calendar systems throughout the application.
 * This is supported by {@link java.time.chrono.ChronoLocalDate}, however it is vital to read
 * all the associated warnings in the Javadoc of that interface before using it.
 * In summary, applications that require general interoperation between multiple calendar systems
 * typically need to be written in a very different way to those only using the ISO calendar,
 * thus most applications should just use ISO and avoid {@code ChronoLocalDate}.
 * </p>
 * <p>
 * The API is also designed for user extensibility, as there are many ways of calculating time.
 * The {@linkplain java.time.temporal.TemporalField field} and {@linkplain java.time.temporal.TemporalUnit unit}
 * API, accessed via {@link java.time.temporal.TemporalAccessor TemporalAccessor} and
 * {@link java.time.temporal.Temporal Temporal} provide considerable flexibility to applications.
 * In addition, the {@link java.time.temporal.TemporalQuery TemporalQuery} and
 * {@link java.time.temporal.TemporalAdjuster TemporalAdjuster} interfaces provide day-to-day
 * power, allowing code to read close to business requirements:
 * </p>
 * <pre>
 *   LocalDate customerBirthday = customer.loadBirthdayFromDatabase();
 *   LocalDate today = LocalDate.now();
 *   if (customerBirthday.equals(today)) {
 *     LocalDate specialOfferExpiryDate = today.plusWeeks(2).with(next(FRIDAY));
 *     customer.sendBirthdaySpecialOffer(specialOfferExpiryDate);
 *   }
 *
 * </pre>
 *
 * @since JDK1.8
 */
package java.time;
