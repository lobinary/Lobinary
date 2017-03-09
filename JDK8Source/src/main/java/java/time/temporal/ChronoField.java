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

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.FOREVER;
import static java.time.temporal.ChronoUnit.HALF_DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MICROS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.LocaleResources;

/**
 * A standard set of fields.
 * <p>
 * This set of fields provide field-based access to manipulate a date, time or date-time.
 * The standard set of fields can be extended by implementing {@link TemporalField}.
 * <p>
 * These fields are intended to be applicable in multiple calendar systems.
 * For example, most non-ISO calendar systems define dates as a year, month and day,
 * just with slightly different rules.
 * The documentation of each field explains how it operates.
 *
 * @implSpec
 * This is a final, immutable and thread-safe enum.
 *
 * <p>
 *  一组标准字段。
 * <p>
 *  该组字段提供基于字段的访问以操纵日期,时间或日期时间。标准字段集可以通过实现{@link TemporalField}来扩展。
 * <p>
 *  这些字段旨在适用于多个日历系统。例如,大多数非ISO日历系统将日期定义为年,月和日,只是略有不同的规则。每个字段的文档说明如何操作。
 * 
 *  @implSpec这是一个最终的,不可变的和线程安全的枚举。
 * 
 * 
 * @since 1.8
 */
public enum ChronoField implements TemporalField {

    /**
     * The nano-of-second.
     * <p>
     * This counts the nanosecond within the second, from 0 to 999,999,999.
     * This field has the same meaning for all calendar systems.
     * <p>
     * This field is used to represent the nano-of-second handling any fraction of the second.
     * Implementations of {@code TemporalAccessor} should provide a value for this field if
     * they can return a value for {@link #SECOND_OF_MINUTE}, {@link #SECOND_OF_DAY} or
     * {@link #INSTANT_SECONDS} filling unknown precision with zero.
     * <p>
     * When this field is used for setting a value, it should set as much precision as the
     * object stores, using integer division to remove excess precision.
     * For example, if the {@code TemporalAccessor} stores time to millisecond precision,
     * then the nano-of-second must be divided by 1,000,000 before replacing the milli-of-second.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The field is resolved in combination with {@code MILLI_OF_SECOND} and {@code MICRO_OF_SECOND}.
     * <p>
     *  纳秒级。
     * <p>
     *  这计数在秒内的纳秒,从0到999,999,999。此字段对所有日历系统具有相同的含义。
     * <p>
     * 该字段用于表示第二处理的任何部分的纳秒级。
     * 如果{@code #SECOND_OF_MINUTE},{@link #SECOND_OF_DAY}或{@link #INSTANT_SECONDS}以零填充未知精确度,{@code TemporalAccessor}
     * 的实施应为此字段提供一个值。
     * 该字段用于表示第二处理的任何部分的纳秒级。
     * <p>
     *  当此字段用于设置值时,它应设置与对象存储一样多的精度,使用整数除法去除超额精度。
     * 例如,如果{@code TemporalAccessor}存储时间到毫秒精度,则在替换毫秒之前,纳秒必须除以1,000,000。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 该字段与{@code MILLI_OF_SECOND}和{@code MICRO_OF_SECOND}结合使用来解析。
     * 
     */
    NANO_OF_SECOND("NanoOfSecond", NANOS, SECONDS, ValueRange.of(0, 999_999_999)),
    /**
     * The nano-of-day.
     * <p>
     * This counts the nanosecond within the day, from 0 to (24 * 60 * 60 * 1,000,000,000) - 1.
     * This field has the same meaning for all calendar systems.
     * <p>
     * This field is used to represent the nano-of-day handling any fraction of the second.
     * Implementations of {@code TemporalAccessor} should provide a value for this field if
     * they can return a value for {@link #SECOND_OF_DAY} filling unknown precision with zero.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The value is split to form {@code NANO_OF_SECOND}, {@code SECOND_OF_MINUTE},
     * {@code MINUTE_OF_HOUR} and {@code HOUR_OF_DAY} fields.
     * <p>
     *  纳米天。
     * <p>
     *  这计数在一天内的纳秒,从0到(24 * 60 * 60 * 1,000,000,000) -  1.该字段对所有日历系统具有相同的含义。
     * <p>
     *  此字段用于表示纳秒处理第二个的任何部分。如果{@code TemporalAccessor}可以返回{@link #SECOND_OF_DAY}填充未知精度为零的值,则此实现应为此字段提供一个值。
     * <p>
     * 当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 该值将拆分为{@code NANO_OF_SECOND},{@code SECOND_OF_MINUTE},{@code MINUTE_OF_HOUR}和{@code HOUR_OF_DAY}字段。
     * 
     */
    NANO_OF_DAY("NanoOfDay", NANOS, DAYS, ValueRange.of(0, 86400L * 1000_000_000L - 1)),
    /**
     * The micro-of-second.
     * <p>
     * This counts the microsecond within the second, from 0 to 999,999.
     * This field has the same meaning for all calendar systems.
     * <p>
     * This field is used to represent the micro-of-second handling any fraction of the second.
     * Implementations of {@code TemporalAccessor} should provide a value for this field if
     * they can return a value for {@link #SECOND_OF_MINUTE}, {@link #SECOND_OF_DAY} or
     * {@link #INSTANT_SECONDS} filling unknown precision with zero.
     * <p>
     * When this field is used for setting a value, it should behave in the same way as
     * setting {@link #NANO_OF_SECOND} with the value multiplied by 1,000.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The field is resolved in combination with {@code MILLI_OF_SECOND} to produce
     * {@code NANO_OF_SECOND}.
     * <p>
     *  微秒。
     * <p>
     *  这计数在秒内的微秒,从0到999,999。此字段对所有日历系统具有相同的含义。
     * <p>
     *  该字段用于表示秒的微秒处理的任何分数。
     * 如果{@code #SECOND_OF_MINUTE},{@link #SECOND_OF_DAY}或{@link #INSTANT_SECONDS}以零填充未知精确度,{@code TemporalAccessor}
     * 的实施应为此字段提供一个值。
     *  该字段用于表示秒的微秒处理的任何分数。
     * <p>
     *  当此字段用于设置值时,其行为应与设置{@link #NANO_OF_SECOND}的值相同,其值乘以1,000。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 该字段与{@code MILLI_OF_SECOND}结合使用以生成{@code NANO_OF_SECOND}。
     * 
     */
    MICRO_OF_SECOND("MicroOfSecond", MICROS, SECONDS, ValueRange.of(0, 999_999)),
    /**
     * The micro-of-day.
     * <p>
     * This counts the microsecond within the day, from 0 to (24 * 60 * 60 * 1,000,000) - 1.
     * This field has the same meaning for all calendar systems.
     * <p>
     * This field is used to represent the micro-of-day handling any fraction of the second.
     * Implementations of {@code TemporalAccessor} should provide a value for this field if
     * they can return a value for {@link #SECOND_OF_DAY} filling unknown precision with zero.
     * <p>
     * When this field is used for setting a value, it should behave in the same way as
     * setting {@link #NANO_OF_DAY} with the value multiplied by 1,000.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The value is split to form {@code MICRO_OF_SECOND}, {@code SECOND_OF_MINUTE},
     * {@code MINUTE_OF_HOUR} and {@code HOUR_OF_DAY} fields.
     * <p>
     *  微天。
     * <p>
     *  这个值计算一天内的微秒,从0到(24 * 60 * 60 * 1,000,000) -  1.该字段对所有日历系统都有相同的含义。
     * <p>
     * 此字段用于表示微秒处理第二个任意小数。如果{@code TemporalAccessor}可以返回{@link #SECOND_OF_DAY}填充未知精度为零的值,则此实现应为此字段提供一个值。
     * <p>
     *  当此字段用于设置值时,其行为应与设置{@link #NANO_OF_DAY}的值相同,其值乘以1,000。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 该值会拆分为{@code MICRO_OF_SECOND},{@code SECOND_OF_MINUTE},{@code MINUTE_OF_HOUR}和{@code HOUR_OF_DAY}字段。
     * 
     */
    MICRO_OF_DAY("MicroOfDay", MICROS, DAYS, ValueRange.of(0, 86400L * 1000_000L - 1)),
    /**
     * The milli-of-second.
     * <p>
     * This counts the millisecond within the second, from 0 to 999.
     * This field has the same meaning for all calendar systems.
     * <p>
     * This field is used to represent the milli-of-second handling any fraction of the second.
     * Implementations of {@code TemporalAccessor} should provide a value for this field if
     * they can return a value for {@link #SECOND_OF_MINUTE}, {@link #SECOND_OF_DAY} or
     * {@link #INSTANT_SECONDS} filling unknown precision with zero.
     * <p>
     * When this field is used for setting a value, it should behave in the same way as
     * setting {@link #NANO_OF_SECOND} with the value multiplied by 1,000,000.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The field is resolved in combination with {@code MICRO_OF_SECOND} to produce
     * {@code NANO_OF_SECOND}.
     * <p>
     *  毫秒。
     * <p>
     *  这计数在秒内的毫秒,从0到999.该字段对于所有日历系统具有相同的含义。
     * <p>
     *  该字段用于表示秒的毫秒处理的任何分数。
     * 如果{@code #SECOND_OF_MINUTE},{@link #SECOND_OF_DAY}或{@link #INSTANT_SECONDS}以零填充未知精确度,{@code TemporalAccessor}
     * 的实施应为此字段提供一个值。
     *  该字段用于表示秒的毫秒处理的任何分数。
     * <p>
     *  当此字段用于设置值时,其行为应与设置{@link #NANO_OF_SECOND}的值相同,其值乘以1,000,000。
     * <p>
     * 当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 该字段与{@code MICRO_OF_SECOND}结合使用以生成{@code NANO_OF_SECOND}。
     * 
     */
    MILLI_OF_SECOND("MilliOfSecond", MILLIS, SECONDS, ValueRange.of(0, 999)),
    /**
     * The milli-of-day.
     * <p>
     * This counts the millisecond within the day, from 0 to (24 * 60 * 60 * 1,000) - 1.
     * This field has the same meaning for all calendar systems.
     * <p>
     * This field is used to represent the milli-of-day handling any fraction of the second.
     * Implementations of {@code TemporalAccessor} should provide a value for this field if
     * they can return a value for {@link #SECOND_OF_DAY} filling unknown precision with zero.
     * <p>
     * When this field is used for setting a value, it should behave in the same way as
     * setting {@link #NANO_OF_DAY} with the value multiplied by 1,000,000.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The value is split to form {@code MILLI_OF_SECOND}, {@code SECOND_OF_MINUTE},
     * {@code MINUTE_OF_HOUR} and {@code HOUR_OF_DAY} fields.
     * <p>
     *  毫米。
     * <p>
     *  这计算在一天内的毫秒,从0到(24 * 60 * 60 * 1,000) -  1.该字段对于所有日历系统具有相同的含义。
     * <p>
     *  此字段用于表示毫秒处理第二个的任何分数。如果{@code TemporalAccessor}可以返回{@link #SECOND_OF_DAY}填充未知精度为零的值,则此实现应为此字段提供一个值。
     * <p>
     *  当此字段用于设置值时,其行为应与设置{@link #NANO_OF_DAY}的值相同,其值乘以1,000,000。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 该值会拆分为{@code MILLI_OF_SECOND},{@code SECOND_OF_MINUTE},{@code MINUTE_OF_HOUR}和{@code HOUR_OF_DAY}字段。
     * 
     */
    MILLI_OF_DAY("MilliOfDay", MILLIS, DAYS, ValueRange.of(0, 86400L * 1000L - 1)),
    /**
     * The second-of-minute.
     * <p>
     * This counts the second within the minute, from 0 to 59.
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * <p>
     *  第二分钟。
     * <p>
     *  这将计算分钟内的秒数,从0到59.此字段对所有日历系统具有相同的含义。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 
     */
    SECOND_OF_MINUTE("SecondOfMinute", SECONDS, MINUTES, ValueRange.of(0, 59), "second"),
    /**
     * The second-of-day.
     * <p>
     * This counts the second within the day, from 0 to (24 * 60 * 60) - 1.
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The value is split to form {@code SECOND_OF_MINUTE}, {@code MINUTE_OF_HOUR}
     * and {@code HOUR_OF_DAY} fields.
     * <p>
     *  第二天。
     * <p>
     * 这将计算一天内的秒数,从0到(24 * 60 * 60) -  1.此字段对所有日历系统具有相同的含义。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 该值将拆分为{@code SECOND_OF_MINUTE},{@code MINUTE_OF_HOUR}和{@code HOUR_OF_DAY}字段。
     * 
     */
    SECOND_OF_DAY("SecondOfDay", SECONDS, DAYS, ValueRange.of(0, 86400L - 1)),
    /**
     * The minute-of-hour.
     * <p>
     * This counts the minute within the hour, from 0 to 59.
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * <p>
     *  一分钟的小时。
     * <p>
     *  这将计算小时内的分钟,从0到59.此字段对所有日历系统具有相同的含义。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 
     */
    MINUTE_OF_HOUR("MinuteOfHour", MINUTES, HOURS, ValueRange.of(0, 59), "minute"),
    /**
     * The minute-of-day.
     * <p>
     * This counts the minute within the day, from 0 to (24 * 60) - 1.
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The value is split to form {@code MINUTE_OF_HOUR} and {@code HOUR_OF_DAY} fields.
     * <p>
     *  分钟。
     * <p>
     *  这将计算一天内的分钟,从0到(24 * 60) -  1.此字段对所有日历系统具有相同的含义。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。该值将拆分为{@code MINUTE_OF_HOUR}和{@code HOUR_OF_DAY}字段。
     * 
     */
    MINUTE_OF_DAY("MinuteOfDay", MINUTES, DAYS, ValueRange.of(0, (24 * 60) - 1)),
    /**
     * The hour-of-am-pm.
     * <p>
     * This counts the hour within the AM/PM, from 0 to 11.
     * This is the hour that would be observed on a standard 12-hour digital clock.
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated from 0 to 11 in strict and smart mode.
     * In lenient mode the value is not validated. It is combined with
     * {@code AMPM_OF_DAY} to form {@code HOUR_OF_DAY} by multiplying
     * the {AMPM_OF_DAY} value by 12.
     * <p>
     *  下午的下午。
     * <p>
     *  这将计算AM / PM中的小时,从0到11.这是在标准12小时数字时钟上观察的小时。此字段对所有日历系统具有相同的含义。
     * <p>
     * 解析此字段时,其行为等效于以下内容：在严格和智能模式下,该值从0到11进行验证。在宽松模式下,值不验证。
     * 它与{@code AMPM_OF_DAY}结合,通过将{AMPM_OF_DAY}值乘以12来形成{@code HOUR_OF_DAY}。
     * 
     */
    HOUR_OF_AMPM("HourOfAmPm", HOURS, HALF_DAYS, ValueRange.of(0, 11)),
    /**
     * The clock-hour-of-am-pm.
     * <p>
     * This counts the hour within the AM/PM, from 1 to 12.
     * This is the hour that would be observed on a standard 12-hour analog wall clock.
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated from 1 to 12 in strict mode and from
     * 0 to 12 in smart mode. In lenient mode the value is not validated.
     * The field is converted to an {@code HOUR_OF_AMPM} with the same value,
     * unless the value is 12, in which case it is converted to 0.
     * <p>
     *  下午 - 下午的时钟。
     * <p>
     *  这将计算AM / PM内的小时,从1到12.这是在标准的12小时模拟挂钟上观察到的小时。此字段对所有日历系统具有相同的含义。
     * <p>
     *  解析此字段时,其行为等效于以下内容：在严格模式下从1到12,在智能模式下从0到12验证该值。在宽松模式下,值不验证。
     * 该字段将转换为具有相同值的{@code HOUR_OF_AMPM},除非值为12,在这种情况下,它将转换为0。
     * 
     */
    CLOCK_HOUR_OF_AMPM("ClockHourOfAmPm", HOURS, HALF_DAYS, ValueRange.of(1, 12)),
    /**
     * The hour-of-day.
     * <p>
     * This counts the hour within the day, from 0 to 23.
     * This is the hour that would be observed on a standard 24-hour digital clock.
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated in strict and smart mode but not in lenient mode.
     * The field is combined with {@code MINUTE_OF_HOUR}, {@code SECOND_OF_MINUTE} and
     * {@code NANO_OF_SECOND} to produce a {@code LocalTime}.
     * In lenient mode, any excess days are added to the parsed date, or
     * made available via {@link java.time.format.DateTimeFormatter#parsedExcessDays()}.
     * <p>
     *  一天中的时间。
     * <p>
     *  它计算一天内的小时,从0到23.这是在标准的24小时数字时钟上观察的小时。此字段对所有日历系统具有相同的含义。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：该值在严格和智能模式下验证,但不在宽松模式下验证。
     * 该字段与{@code MINUTE_OF_HOUR},{@code SECOND_OF_MINUTE}和{@code NANO_OF_SECOND}结合,以产生{@code LocalTime}。
     * 在宽松模式下,任何多余的天都会添加到解析日期,或通过{@link java.time.format.DateTimeFormatter#parsedExcessDays()}获取。
     * 
     */
    HOUR_OF_DAY("HourOfDay", HOURS, DAYS, ValueRange.of(0, 23), "hour"),
    /**
     * The clock-hour-of-day.
     * <p>
     * This counts the hour within the AM/PM, from 1 to 24.
     * This is the hour that would be observed on a 24-hour analog wall clock.
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated from 1 to 24 in strict mode and from
     * 0 to 24 in smart mode. In lenient mode the value is not validated.
     * The field is converted to an {@code HOUR_OF_DAY} with the same value,
     * unless the value is 24, in which case it is converted to 0.
     * <p>
     *  时钟的时钟。
     * <p>
     * 这将计算AM / PM内的小时,从1到24.这是在24小时模拟挂钟上观察到的小时数。此字段对所有日历系统具有相同的含义。
     * <p>
     *  当解析此字段时,其行为等效于以下内容：在严格模式下,值从1到24,在智能模式下,值从0到24进行验证。在宽松模式下,值不验证。
     * 该字段将转换为具有相同值的{@code HOUR_OF_DAY},除非值为24,在这种情况下,它将转换为0。
     * 
     */
    CLOCK_HOUR_OF_DAY("ClockHourOfDay", HOURS, DAYS, ValueRange.of(1, 24)),
    /**
     * The am-pm-of-day.
     * <p>
     * This counts the AM/PM within the day, from 0 (AM) to 1 (PM).
     * This field has the same meaning for all calendar systems.
     * <p>
     * When parsing this field it behaves equivalent to the following:
     * The value is validated from 0 to 1 in strict and smart mode.
     * In lenient mode the value is not validated. It is combined with
     * {@code HOUR_OF_AMPM} to form {@code HOUR_OF_DAY} by multiplying
     * the {AMPM_OF_DAY} value by 12.
     * <p>
     *  下午的下午。
     * <p>
     *  这将计算一天内的AM / PM,从0(AM)到1(PM)。此字段对所有日历系统具有相同的含义。
     * <p>
     *  解析此字段时,其行为等效于以下内容：在严格和智能模式下,该值从0到1进行验证。在宽松模式下,值不验证。
     * 它与{@code HOUR_OF_AMPM}结合,通过将{AMPM_OF_DAY}值乘以12来形成{@code HOUR_OF_DAY}。
     * 
     */
    AMPM_OF_DAY("AmPmOfDay", HALF_DAYS, DAYS, ValueRange.of(0, 1), "dayperiod"),
    /**
     * The day-of-week, such as Tuesday.
     * <p>
     * This represents the standard concept of the day of the week.
     * In the default ISO calendar system, this has values from Monday (1) to Sunday (7).
     * The {@link DayOfWeek} class can be used to interpret the result.
     * <p>
     * Most non-ISO calendar systems also define a seven day week that aligns with ISO.
     * Those calendar systems must also use the same numbering system, from Monday (1) to
     * Sunday (7), which allows {@code DayOfWeek} to be used.
     * <p>
     * Calendar systems that do not have a standard seven day week should implement this field
     * if they have a similar concept of named or numbered days within a period similar
     * to a week. It is recommended that the numbering starts from 1.
     * <p>
     *  星期几,如星期二。
     * <p>
     *  这代表了星期几的标准概念。在默认ISO日历系统中,此值具有从星期一(1)到星期日(7)的值。 {@link DayOfWeek}类可用于解释结果。
     * <p>
     *  大多数非ISO日历系统还定义了一个与ISO一致的七天周。那些日历系统还必须使用相同的编号系统,从星期一(1)到星期日(7),这允许使用{@code DayOfWeek}。
     * <p>
     * 没有标准七天的日历系统应该实施此字段,如果他们在类似于一周的时间段内具有类似的命名或编号的天的概念。建议编号从1开始。
     * 
     */
    DAY_OF_WEEK("DayOfWeek", DAYS, WEEKS, ValueRange.of(1, 7), "weekday"),
    /**
     * The aligned day-of-week within a month.
     * <p>
     * This represents concept of the count of days within the period of a week
     * where the weeks are aligned to the start of the month.
     * This field is typically used with {@link #ALIGNED_WEEK_OF_MONTH}.
     * <p>
     * For example, in a calendar systems with a seven day week, the first aligned-week-of-month
     * starts on day-of-month 1, the second aligned-week starts on day-of-month 8, and so on.
     * Within each of these aligned-weeks, the days are numbered from 1 to 7 and returned
     * as the value of this field.
     * As such, day-of-month 1 to 7 will have aligned-day-of-week values from 1 to 7.
     * And day-of-month 8 to 14 will repeat this with aligned-day-of-week values from 1 to 7.
     * <p>
     * Calendar systems that do not have a seven day week should typically implement this
     * field in the same way, but using the alternate week length.
     * <p>
     *  一个月内对齐的星期。
     * <p>
     *  这表示在一周的周期内的天数的概念,其中星期与月份的开始对齐。此字段通常与{@link #ALIGNED_WEEK_OF_MONTH}一起使用。
     * <p>
     *  例如,在具有七天周的日历系统中,第一对齐周的月开始于月的第1天,第二对齐周开始于月的天8,等等。在每个排列周期内,日期从1到7编号,并作为此字段的值返回。因此,日期1到7将具有从1到7的星期几值。
     * 并且日期8到14将重复这一点与星期几的值从1到7。
     * <p>
     *  没有每周七天的日历系统应该通常以相同的方式实现此字段,但使用另一周的长度。
     * 
     */
    ALIGNED_DAY_OF_WEEK_IN_MONTH("AlignedDayOfWeekInMonth", DAYS, WEEKS, ValueRange.of(1, 7)),
    /**
     * The aligned day-of-week within a year.
     * <p>
     * This represents concept of the count of days within the period of a week
     * where the weeks are aligned to the start of the year.
     * This field is typically used with {@link #ALIGNED_WEEK_OF_YEAR}.
     * <p>
     * For example, in a calendar systems with a seven day week, the first aligned-week-of-year
     * starts on day-of-year 1, the second aligned-week starts on day-of-year 8, and so on.
     * Within each of these aligned-weeks, the days are numbered from 1 to 7 and returned
     * as the value of this field.
     * As such, day-of-year 1 to 7 will have aligned-day-of-week values from 1 to 7.
     * And day-of-year 8 to 14 will repeat this with aligned-day-of-week values from 1 to 7.
     * <p>
     * Calendar systems that do not have a seven day week should typically implement this
     * field in the same way, but using the alternate week length.
     * <p>
     *  一年内对齐的星期。
     * <p>
     *  这表示在一周的周期内的天数的概念,其中星期与年初开始一致。此字段通常与{@link #ALIGNED_WEEK_OF_YEAR}一起使用。
     * <p>
     * 例如,在具有七天周的日历系统中,第一对齐周的年开始于年的第1天,第二对齐周开始于年的第8天,等等。在每个排列周期内,日期从1到7编号,并作为此字段的值返回。因此,年1到7的日将具有从1到7的星期几值。
     * 并且年8到14的日将重复这一点与星期几的值从1到7。
     * <p>
     *  没有每周七天的日历系统应该通常以相同的方式实现此字段,但使用另一周的长度。
     * 
     */
    ALIGNED_DAY_OF_WEEK_IN_YEAR("AlignedDayOfWeekInYear", DAYS, WEEKS, ValueRange.of(1, 7)),
    /**
     * The day-of-month.
     * <p>
     * This represents the concept of the day within the month.
     * In the default ISO calendar system, this has values from 1 to 31 in most months.
     * April, June, September, November have days from 1 to 30, while February has days
     * from 1 to 28, or 29 in a leap year.
     * <p>
     * Non-ISO calendar systems should implement this field using the most recognized
     * day-of-month values for users of the calendar system.
     * Normally, this is a count of days from 1 to the length of the month.
     * <p>
     *  日期。
     * <p>
     *  这代表了一个月内的一天的概念。在默认ISO日历系统中,在大多数月份中,其值为1到31。 4月,6月,9月,11月有1至30天,而2月有1至28天,或闰年29天。
     * <p>
     *  非ISO日历系统应使用日历系统用户最常识别的日期值来实现此字段。通常,这是从1到月的长度的天数。
     * 
     */
    DAY_OF_MONTH("DayOfMonth", DAYS, MONTHS, ValueRange.of(1, 28, 31), "day"),
    /**
     * The day-of-year.
     * <p>
     * This represents the concept of the day within the year.
     * In the default ISO calendar system, this has values from 1 to 365 in standard
     * years and 1 to 366 in leap years.
     * <p>
     * Non-ISO calendar systems should implement this field using the most recognized
     * day-of-year values for users of the calendar system.
     * Normally, this is a count of days from 1 to the length of the year.
     * <p>
     * Note that a non-ISO calendar system may have year numbering system that changes
     * at a different point to the natural reset in the month numbering. An example
     * of this is the Japanese calendar system where a change of era, which resets
     * the year number to 1, can happen on any date. The era and year reset also cause
     * the day-of-year to be reset to 1, but not the month-of-year or day-of-month.
     * <p>
     *  一年中的日子。
     * <p>
     *  这代表了一年中一天的概念。在默认ISO日历系统中,此值的标准年份为1到365,闰年为1到366。
     * <p>
     *  非ISO日历系统应使用日历系统用户最常识别的年的值来实现此字段。通常,这是从1到一年的长度的天数。
     * <p>
     * 注意,非ISO日历系统可以具有在与月编号中的自然重置不同的点处改变的年编号系统。一个例子是日本日历系统,其中将年份数目重置为1的时代的改变可以在任何日期发生。
     * 时间和年份重置也会将年份重置为1,但不会将年份或月份重新设置为1。
     * 
     */
    DAY_OF_YEAR("DayOfYear", DAYS, YEARS, ValueRange.of(1, 365, 366)),
    /**
     * The epoch-day, based on the Java epoch of 1970-01-01 (ISO).
     * <p>
     * This field is the sequential count of days where 1970-01-01 (ISO) is zero.
     * Note that this uses the <i>local</i> time-line, ignoring offset and time-zone.
     * <p>
     * This field is strictly defined to have the same meaning in all calendar systems.
     * This is necessary to ensure interoperation between calendars.
     * <p>
     *  时代日,基于1970-01-01(ISO)的Java时代。
     * <p>
     *  此字段是1970-01-01(ISO)为零的连续天数。请注意,这将使用<i> local </i>时间线,忽略偏移量和时区。
     * <p>
     *  此字段被严格定义为在所有日历系统中具有相同的含义。这是必要的,以确保日历之间的互操作。
     * 
     */
    EPOCH_DAY("EpochDay", DAYS, FOREVER, ValueRange.of((long) (Year.MIN_VALUE * 365.25), (long) (Year.MAX_VALUE * 365.25))),
    /**
     * The aligned week within a month.
     * <p>
     * This represents concept of the count of weeks within the period of a month
     * where the weeks are aligned to the start of the month.
     * This field is typically used with {@link #ALIGNED_DAY_OF_WEEK_IN_MONTH}.
     * <p>
     * For example, in a calendar systems with a seven day week, the first aligned-week-of-month
     * starts on day-of-month 1, the second aligned-week starts on day-of-month 8, and so on.
     * Thus, day-of-month values 1 to 7 are in aligned-week 1, while day-of-month values
     * 8 to 14 are in aligned-week 2, and so on.
     * <p>
     * Calendar systems that do not have a seven day week should typically implement this
     * field in the same way, but using the alternate week length.
     * <p>
     *  一个月内对齐的星期。
     * <p>
     *  这表示在一个月的周期内周的计数的概念,其中周与月的开始对齐。此字段通常与{@link #ALIGNED_DAY_OF_WEEK_IN_MONTH}一起使用。
     * <p>
     *  例如,在具有七天周的日历系统中,第一对齐周的月开始于月的第1天,第二对齐周开始于月的天8,等等。因此,日期值1到7在排列的星期1,而星期几值8到14在排列的星期2,依此类推。
     * <p>
     *  没有每周七天的日历系统应该通常以相同的方式实现此字段,但使用另一周的长度。
     * 
     */
    ALIGNED_WEEK_OF_MONTH("AlignedWeekOfMonth", WEEKS, MONTHS, ValueRange.of(1, 4, 5)),
    /**
     * The aligned week within a year.
     * <p>
     * This represents concept of the count of weeks within the period of a year
     * where the weeks are aligned to the start of the year.
     * This field is typically used with {@link #ALIGNED_DAY_OF_WEEK_IN_YEAR}.
     * <p>
     * For example, in a calendar systems with a seven day week, the first aligned-week-of-year
     * starts on day-of-year 1, the second aligned-week starts on day-of-year 8, and so on.
     * Thus, day-of-year values 1 to 7 are in aligned-week 1, while day-of-year values
     * 8 to 14 are in aligned-week 2, and so on.
     * <p>
     * Calendar systems that do not have a seven day week should typically implement this
     * field in the same way, but using the alternate week length.
     * <p>
     *  一年内对齐的星期。
     * <p>
     * 这表示在一年的周期内的周数计数的概念,其中星期与年初相一致。此字段通常与{@link #ALIGNED_DAY_OF_WEEK_IN_YEAR}一起使用。
     * <p>
     *  例如,在具有七天周的日历系统中,第一对齐周的年开始于年的第1天,第二对齐周开始于年的第8天,等等。因此,年份值1至7在排列的周1中,而年份值8至14在排列的周2中,以此类推。
     * <p>
     *  没有每周七天的日历系统应该通常以相同的方式实现此字段,但使用另一周的长度。
     * 
     */
    ALIGNED_WEEK_OF_YEAR("AlignedWeekOfYear", WEEKS, YEARS, ValueRange.of(1, 53)),
    /**
     * The month-of-year, such as March.
     * <p>
     * This represents the concept of the month within the year.
     * In the default ISO calendar system, this has values from January (1) to December (12).
     * <p>
     * Non-ISO calendar systems should implement this field using the most recognized
     * month-of-year values for users of the calendar system.
     * Normally, this is a count of months starting from 1.
     * <p>
     *  一个月,例如三月。
     * <p>
     *  这代表了一年中的月份的概念。在默认ISO日历系统中,此值具有从1月(1)到12月(12)的值。
     * <p>
     *  非ISO日历系统应使用日历系统用户最常识别的年份值来实现此字段。通常,这是从1开始的月数。
     * 
     */
    MONTH_OF_YEAR("MonthOfYear", MONTHS, YEARS, ValueRange.of(1, 12), "month"),
    /**
     * The proleptic-month based, counting months sequentially from year 0.
     * <p>
     * This field is the sequential count of months where the first month
     * in proleptic-year zero has the value zero.
     * Later months have increasingly larger values.
     * Earlier months have increasingly small values.
     * There are no gaps or breaks in the sequence of months.
     * Note that this uses the <i>local</i> time-line, ignoring offset and time-zone.
     * <p>
     * In the default ISO calendar system, June 2012 would have the value
     * {@code (2012 * 12 + 6 - 1)}. This field is primarily for internal use.
     * <p>
     * Non-ISO calendar systems must implement this field as per the definition above.
     * It is just a simple zero-based count of elapsed months from the start of proleptic-year 0.
     * All calendar systems with a full proleptic-year definition will have a year zero.
     * If the calendar system has a minimum year that excludes year zero, then one must
     * be extrapolated in order for this method to be defined.
     * <p>
     *  从第0年起依次计数几个月。
     * <p>
     *  该字段是月份的顺序计数,其中,保守年份零的第一个月的值为零。后来几个月有越来越大的价值。早期的月份有越来越小的价值。在月份的顺序中没有间隙或中断。
     * 请注意,这将使用<i> local </i>时间线,忽略偏移量和时区。
     * <p>
     * 在默认ISO日历系统中,2012年6月的值为{@code(2012 * 12 + 6  -  1)}。此字段主要用于内部使用。
     * <p>
     *  非ISO日历系统必须按照上述定义实现此字段。它只是一个简单的从零开始计算从启动年0开始的月数。所有具有完全衍生年定义的日历系统将具有零年。
     * 如果日历系统具有排除了零年的最小年,则必须外推一个年,以便定义该方法。
     * 
     */
    PROLEPTIC_MONTH("ProlepticMonth", MONTHS, FOREVER, ValueRange.of(Year.MIN_VALUE * 12L, Year.MAX_VALUE * 12L + 11)),
    /**
     * The year within the era.
     * <p>
     * This represents the concept of the year within the era.
     * This field is typically used with {@link #ERA}.
     * <p>
     * The standard mental model for a date is based on three concepts - year, month and day.
     * These map onto the {@code YEAR}, {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH} fields.
     * Note that there is no reference to eras.
     * The full model for a date requires four concepts - era, year, month and day. These map onto
     * the {@code ERA}, {@code YEAR_OF_ERA}, {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH} fields.
     * Whether this field or {@code YEAR} is used depends on which mental model is being used.
     * See {@link ChronoLocalDate} for more discussion on this topic.
     * <p>
     * In the default ISO calendar system, there are two eras defined, 'BCE' and 'CE'.
     * The era 'CE' is the one currently in use and year-of-era runs from 1 to the maximum value.
     * The era 'BCE' is the previous era, and the year-of-era runs backwards.
     * <p>
     * For example, subtracting a year each time yield the following:<br>
     * - year-proleptic 2  = 'CE' year-of-era 2<br>
     * - year-proleptic 1  = 'CE' year-of-era 1<br>
     * - year-proleptic 0  = 'BCE' year-of-era 1<br>
     * - year-proleptic -1 = 'BCE' year-of-era 2<br>
     * <p>
     * Note that the ISO-8601 standard does not actually define eras.
     * Note also that the ISO eras do not align with the well-known AD/BC eras due to the
     * change between the Julian and Gregorian calendar systems.
     * <p>
     * Non-ISO calendar systems should implement this field using the most recognized
     * year-of-era value for users of the calendar system.
     * Since most calendar systems have only two eras, the year-of-era numbering approach
     * will typically be the same as that used by the ISO calendar system.
     * The year-of-era value should typically always be positive, however this is not required.
     * <p>
     *  年代内的时代。
     * <p>
     *  这代表了时代内一年的概念。此字段通常与{@link #ERA}一起使用。
     * <p>
     *  日期的标准心理模型基于三个概念：年,月和日。这些映射到{@code YEAR},{@code MONTH_OF_YEAR}和{@code DAY_OF_MONTH}字段。注意,没有参考时代。
     * 日期的完整模型需要四个概念 - 时代,年,月和日。
     * 这些映射到{@code ERA},{@code YEAR_OF_ERA},{@code MONTH_OF_YEAR}和{@code DAY_OF_MONTH}字段。
     * 是否使用此字段或{@code YEAR}取决于使用的心理模型。有关此主题的更多讨论,请参阅{@link ChronoLocalDate}。
     * <p>
     *  在默认ISO日历系统中,定义了两个时间,"B​​CE"和"CE"。时代"CE"是当前使用的,年份从1到最大值。时代"BCE"是前一个时代,年代倒退。
     * <p>
     * 例如,每次减去一年得到以下结果：<br>  -  year-proleptic 2 ='CE'year-of-era 2 <br>  -  year-proleptic 1 ='CE'year-of-e
     * ra 1 <br >  - 年犯0 ='BCE'年龄1 <br>  - 年犯-1 ='BCE'年龄2 <br>。
     * <p>
     *  注意,ISO-8601标准实际上不定义时间。还要注意,由于Julian和Gregorian日历系统之间的变化,ISO时代与公知的AD / BC时代不一致。
     * <p>
     *  非ISO日历系统应使用日历系统用户最常识别的年龄值来实现此字段。由于大多数日历系统只有两个时代,年代码编号方法通常与ISO日历系统使用的相同。年代价值通常应该是正的,但这不是必需的。
     * 
     */
    YEAR_OF_ERA("YearOfEra", YEARS, FOREVER, ValueRange.of(1, Year.MAX_VALUE, Year.MAX_VALUE + 1)),
    /**
     * The proleptic year, such as 2012.
     * <p>
     * This represents the concept of the year, counting sequentially and using negative numbers.
     * The proleptic year is not interpreted in terms of the era.
     * See {@link #YEAR_OF_ERA} for an example showing the mapping from proleptic year to year-of-era.
     * <p>
     * The standard mental model for a date is based on three concepts - year, month and day.
     * These map onto the {@code YEAR}, {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH} fields.
     * Note that there is no reference to eras.
     * The full model for a date requires four concepts - era, year, month and day. These map onto
     * the {@code ERA}, {@code YEAR_OF_ERA}, {@code MONTH_OF_YEAR} and {@code DAY_OF_MONTH} fields.
     * Whether this field or {@code YEAR_OF_ERA} is used depends on which mental model is being used.
     * See {@link ChronoLocalDate} for more discussion on this topic.
     * <p>
     * Non-ISO calendar systems should implement this field as follows.
     * If the calendar system has only two eras, before and after a fixed date, then the
     * proleptic-year value must be the same as the year-of-era value for the later era,
     * and increasingly negative for the earlier era.
     * If the calendar system has more than two eras, then the proleptic-year value may be
     * defined with any appropriate value, although defining it to be the same as ISO may be
     * the best option.
     * <p>
     *  发生的年份,如2012年。
     * <p>
     *  这代表年的概念,顺序计数并使用负数。逃避的年代不是根据时代来解释的。请参阅{@link #YEAR_OF_ERA}一个示例,显示从初步年份到年份的映射。
     * <p>
     * 日期的标准心理模型基于三个概念：年,月和日。这些映射到{@code YEAR},{@code MONTH_OF_YEAR}和{@code DAY_OF_MONTH}字段。注意,没有参考时代。
     * 日期的完整模型需要四个概念 - 时代,年,月和日。
     * 这些映射到{@code ERA},{@code YEAR_OF_ERA},{@code MONTH_OF_YEAR}和{@code DAY_OF_MONTH}字段。
     * 是否使用此字段或{@code YEAR_OF_ERA}取决于使用的心理模型。有关此主题的更多讨论,请参阅{@link ChronoLocalDate}。
     * <p>
     *  非ISO日历系统应该实现此字段如下。如果日历系统只有两个时间,在固定日期之前和之后,那么推测年值必须与后一时代的年代值相同,并且对于较早时代越来越负面。
     * 如果日历系统具有多于两个时间,则可以用任何适当的值来定义提示年值,尽管将其定义为相同,但ISO可以是最佳选项。
     * 
     */
    YEAR("Year", YEARS, FOREVER, ValueRange.of(Year.MIN_VALUE, Year.MAX_VALUE), "year"),
    /**
     * The era.
     * <p>
     * This represents the concept of the era, which is the largest division of the time-line.
     * This field is typically used with {@link #YEAR_OF_ERA}.
     * <p>
     * In the default ISO calendar system, there are two eras defined, 'BCE' and 'CE'.
     * The era 'CE' is the one currently in use and year-of-era runs from 1 to the maximum value.
     * The era 'BCE' is the previous era, and the year-of-era runs backwards.
     * See {@link #YEAR_OF_ERA} for a full example.
     * <p>
     * Non-ISO calendar systems should implement this field to define eras.
     * The value of the era that was active on 1970-01-01 (ISO) must be assigned the value 1.
     * Earlier eras must have sequentially smaller values.
     * Later eras must have sequentially larger values,
     * <p>
     *  时代。
     * <p>
     *  这代表了时代的概念,这是时间线的最大分割。此字段通常与{@link #YEAR_OF_ERA}一起使用。
     * <p>
     *  在默认ISO日历系统中,定义了两个时间,"B​​CE"和"CE"。时代"CE"是当前使用的,年份从1到最大值。时代"BCE"是前一个时代,年代倒退。
     * 有关完整示例,请参见{@link #YEAR_OF_ERA}。
     * <p>
     * 非ISO日历系统应实现此字段以定义时间。在1970-01-01(ISO)上活动的时代的值必须赋值1.早期时代必须具有顺序较小的值。后来的时间必须有顺序更大的值,
     * 
     */
    ERA("Era", ERAS, FOREVER, ValueRange.of(0, 1), "era"),
    /**
     * The instant epoch-seconds.
     * <p>
     * This represents the concept of the sequential count of seconds where
     * 1970-01-01T00:00Z (ISO) is zero.
     * This field may be used with {@link #NANO_OF_SECOND} to represent the fraction of the second.
     * <p>
     * An {@link Instant} represents an instantaneous point on the time-line.
     * On their own, an instant has insufficient information to allow a local date-time to be obtained.
     * Only when paired with an offset or time-zone can the local date or time be calculated.
     * <p>
     * This field is strictly defined to have the same meaning in all calendar systems.
     * This is necessary to ensure interoperation between calendars.
     * <p>
     *  瞬时时代秒。
     * <p>
     *  这表示秒的顺序计数的概念,其中1970-01-01T00：00Z(ISO)为零。此字段可与{@link #NANO_OF_SECOND}一起使用,以表示第二个的小数部分。
     * <p>
     *  {@link Instant}表示时间线上的瞬时点。就其自身而言,瞬时具有不足以允许获得本地日期时间的信息。只有当与偏移量或时区配对时,才能计算本地日期或时间。
     * <p>
     *  此字段被严格定义为在所有日历系统中具有相同的含义。这是必要的,以确保日历之间的互操作。
     * 
     */
    INSTANT_SECONDS("InstantSeconds", SECONDS, FOREVER, ValueRange.of(Long.MIN_VALUE, Long.MAX_VALUE)),
    /**
     * The offset from UTC/Greenwich.
     * <p>
     * This represents the concept of the offset in seconds of local time from UTC/Greenwich.
     * <p>
     * A {@link ZoneOffset} represents the period of time that local time differs from UTC/Greenwich.
     * This is usually a fixed number of hours and minutes.
     * It is equivalent to the {@link ZoneOffset#getTotalSeconds() total amount} of the offset in seconds.
     * For example, during the winter Paris has an offset of {@code +01:00}, which is 3600 seconds.
     * <p>
     * This field is strictly defined to have the same meaning in all calendar systems.
     * This is necessary to ensure interoperation between calendars.
     * <p>
     *  偏离UTC /格林威治。
     * <p>
     *  这表示本地时间与UTC / Greenwich之间的偏移量(以秒为单位)的概念。
     * <p>
     *  {@link ZoneOffset}表示本地时间与UTC / Greenwich不同的时间段。这通常是固定的小时数和分钟数。
     * 它等价于{@link ZoneOffset#getTotalSeconds()total amount}的偏移量(以秒为单位)。
     * 例如,在冬天期间,巴黎的偏移量为{@code +01：00},即3600秒。
     * <p>
     *  此字段被严格定义为在所有日历系统中具有相同的含义。这是必要的,以确保日历之间的互操作。
     * 
     */
    OFFSET_SECONDS("OffsetSeconds", SECONDS, FOREVER, ValueRange.of(-18 * 3600, 18 * 3600));

    private final String name;
    private final TemporalUnit baseUnit;
    private final TemporalUnit rangeUnit;
    private final ValueRange range;
    private final String displayNameKey;

    private ChronoField(String name, TemporalUnit baseUnit, TemporalUnit rangeUnit, ValueRange range) {
        this.name = name;
        this.baseUnit = baseUnit;
        this.rangeUnit = rangeUnit;
        this.range = range;
        this.displayNameKey = null;
    }

    private ChronoField(String name, TemporalUnit baseUnit, TemporalUnit rangeUnit,
            ValueRange range, String displayNameKey) {
        this.name = name;
        this.baseUnit = baseUnit;
        this.rangeUnit = rangeUnit;
        this.range = range;
        this.displayNameKey = displayNameKey;
    }

    @Override
    public String getDisplayName(Locale locale) {
        Objects.requireNonNull(locale, "locale");
        if (displayNameKey == null) {
            return name;
        }

        LocaleResources lr = LocaleProviderAdapter.getResourceBundleBased()
                                    .getLocaleResources(locale);
        ResourceBundle rb = lr.getJavaTimeFormatData();
        String key = "field." + displayNameKey;
        return rb.containsKey(key) ? rb.getString(key) : name;
    }

    @Override
    public TemporalUnit getBaseUnit() {
        return baseUnit;
    }

    @Override
    public TemporalUnit getRangeUnit() {
        return rangeUnit;
    }

    /**
     * Gets the range of valid values for the field.
     * <p>
     * All fields can be expressed as a {@code long} integer.
     * This method returns an object that describes the valid range for that value.
     * <p>
     * This method returns the range of the field in the ISO-8601 calendar system.
     * This range may be incorrect for other calendar systems.
     * Use {@link Chronology#range(ChronoField)} to access the correct range
     * for a different calendar system.
     * <p>
     * Note that the result only describes the minimum and maximum valid values
     * and it is important not to read too much into them. For example, there
     * could be values within the range that are invalid for the field.
     *
     * <p>
     * 获取字段的有效值范围。
     * <p>
     *  所有字段都可以表示为{@code long}整数。此方法返回描述该值的有效范围的对象。
     * <p>
     *  此方法返回ISO-8601日历系统中字段的范围。对于其他日历系统,此范围可能不正确。使用{@link Chronology#range(ChronoField)}可以访问不同日历系统的正确范围。
     * <p>
     *  注意,结果只描述最小和最大有效值,重要的是不要过多地读入它们。例如,可能存在对于字段无效的范围内的值。
     * 
     * 
     * @return the range of valid values for the field, not null
     */
    @Override
    public ValueRange range() {
        return range;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this field represents a component of a date.
     * <p>
     * Fields from day-of-week to era are date-based.
     *
     * <p>
     *  检查此字段是否表示日期的组件。
     * <p>
     *  从周日到时代的字段是基于日期的。
     * 
     * 
     * @return true if it is a component of a date
     */
    @Override
    public boolean isDateBased() {
        return ordinal() >= DAY_OF_WEEK.ordinal() && ordinal() <= ERA.ordinal();
    }

    /**
     * Checks if this field represents a component of a time.
     * <p>
     * Fields from nano-of-second to am-pm-of-day are time-based.
     *
     * <p>
     *  检查此字段是否表示时间的组件。
     * <p>
     *  从纳秒到下午的字段是基于时间的。
     * 
     * 
     * @return true if it is a component of a time
     */
    @Override
    public boolean isTimeBased() {
        return ordinal() < DAY_OF_WEEK.ordinal();
    }

    //-----------------------------------------------------------------------
    /**
     * Checks that the specified value is valid for this field.
     * <p>
     * This validates that the value is within the outer range of valid values
     * returned by {@link #range()}.
     * <p>
     * This method checks against the range of the field in the ISO-8601 calendar system.
     * This range may be incorrect for other calendar systems.
     * Use {@link Chronology#range(ChronoField)} to access the correct range
     * for a different calendar system.
     *
     * <p>
     *  检查指定的值对此字段有效。
     * <p>
     *  这将验证该值是否在{@link #range()}返回的有效值的外部范围内。
     * <p>
     *  此方法检查ISO-8601日历系统中字段的范围。对于其他日历系统,此范围可能不正确。使用{@link Chronology#range(ChronoField)}可以访问不同日历系统的正确范围。
     * 
     * 
     * @param value  the value to check
     * @return the value that was passed in
     */
    public long checkValidValue(long value) {
        return range().checkValidValue(value, this);
    }

    /**
     * Checks that the specified value is valid and fits in an {@code int}.
     * <p>
     * This validates that the value is within the outer range of valid values
     * returned by {@link #range()}.
     * It also checks that all valid values are within the bounds of an {@code int}.
     * <p>
     * This method checks against the range of the field in the ISO-8601 calendar system.
     * This range may be incorrect for other calendar systems.
     * Use {@link Chronology#range(ChronoField)} to access the correct range
     * for a different calendar system.
     *
     * <p>
     *  检查指定的值是否有效,并且符合{@code int}。
     * <p>
     *  这将验证该值是否在{@link #range()}返回的有效值的外部范围内。它还检查所有有效值是否在{@code int}的界限内。
     * 
     * @param value  the value to check
     * @return the value that was passed in
     */
    public int checkValidIntValue(long value) {
        return range().checkValidIntValue(value, this);
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean isSupportedBy(TemporalAccessor temporal) {
        return temporal.isSupported(this);
    }

    @Override
    public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
        return temporal.range(this);
    }

    @Override
    public long getFrom(TemporalAccessor temporal) {
        return temporal.getLong(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Temporal> R adjustInto(R temporal, long newValue) {
        return (R) temporal.with(this, newValue);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        return name;
    }

}
