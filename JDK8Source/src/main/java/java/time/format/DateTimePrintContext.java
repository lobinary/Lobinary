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
 * Copyright (c) 2011-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2011-2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time.format;

import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;
import java.time.temporal.ValueRange;
import java.util.Locale;
import java.util.Objects;

/**
 * Context object used during date and time printing.
 * <p>
 * This class provides a single wrapper to items used in the format.
 *
 * @implSpec
 * This class is a mutable context intended for use from a single thread.
 * Usage of the class is thread-safe within standard printing as the framework creates
 * a new instance of the class for each format and printing is single-threaded.
 *
 * <p>
 *  日期和时间打印期间使用的上下文对象。
 * <p>
 *  这个类为格式中使用的项提供了一个包装器。
 * 
 *  @implSpec这个类是一个可变的上下文,用于单线程。在标准打印中,类的使用是线程安全的,因为框架为每种格式创建类的新实例,并且打印是单线程的。
 * 
 * 
 * @since 1.8
 */
final class DateTimePrintContext {

    /**
     * The temporal being output.
     * <p>
     *  时间输出。
     * 
     */
    private TemporalAccessor temporal;
    /**
     * The formatter, not null.
     * <p>
     *  格式化程序,不是null。
     * 
     */
    private DateTimeFormatter formatter;
    /**
     * Whether the current formatter is optional.
     * <p>
     *  当前格式化程序是否是可选的。
     * 
     */
    private int optional;

    /**
     * Creates a new instance of the context.
     *
     * <p>
     *  创建上下文的新实例。
     * 
     * 
     * @param temporal  the temporal object being output, not null
     * @param formatter  the formatter controlling the format, not null
     */
    DateTimePrintContext(TemporalAccessor temporal, DateTimeFormatter formatter) {
        super();
        this.temporal = adjust(temporal, formatter);
        this.formatter = formatter;
    }

    private static TemporalAccessor adjust(final TemporalAccessor temporal, DateTimeFormatter formatter) {
        // normal case first (early return is an optimization)
        Chronology overrideChrono = formatter.getChronology();
        ZoneId overrideZone = formatter.getZone();
        if (overrideChrono == null && overrideZone == null) {
            return temporal;
        }

        // ensure minimal change (early return is an optimization)
        Chronology temporalChrono = temporal.query(TemporalQueries.chronology());
        ZoneId temporalZone = temporal.query(TemporalQueries.zoneId());
        if (Objects.equals(overrideChrono, temporalChrono)) {
            overrideChrono = null;
        }
        if (Objects.equals(overrideZone, temporalZone)) {
            overrideZone = null;
        }
        if (overrideChrono == null && overrideZone == null) {
            return temporal;
        }

        // make adjustment
        final Chronology effectiveChrono = (overrideChrono != null ? overrideChrono : temporalChrono);
        if (overrideZone != null) {
            // if have zone and instant, calculation is simple, defaulting chrono if necessary
            if (temporal.isSupported(INSTANT_SECONDS)) {
                Chronology chrono = (effectiveChrono != null ? effectiveChrono : IsoChronology.INSTANCE);
                return chrono.zonedDateTime(Instant.from(temporal), overrideZone);
            }
            // block changing zone on OffsetTime, and similar problem cases
            if (overrideZone.normalized() instanceof ZoneOffset && temporal.isSupported(OFFSET_SECONDS) &&
                    temporal.get(OFFSET_SECONDS) != overrideZone.getRules().getOffset(Instant.EPOCH).getTotalSeconds()) {
                throw new DateTimeException("Unable to apply override zone '" + overrideZone +
                        "' because the temporal object being formatted has a different offset but" +
                        " does not represent an instant: " + temporal);
            }
        }
        final ZoneId effectiveZone = (overrideZone != null ? overrideZone : temporalZone);
        final ChronoLocalDate effectiveDate;
        if (overrideChrono != null) {
            if (temporal.isSupported(EPOCH_DAY)) {
                effectiveDate = effectiveChrono.date(temporal);
            } else {
                // check for date fields other than epoch-day, ignoring case of converting null to ISO
                if (!(overrideChrono == IsoChronology.INSTANCE && temporalChrono == null)) {
                    for (ChronoField f : ChronoField.values()) {
                        if (f.isDateBased() && temporal.isSupported(f)) {
                            throw new DateTimeException("Unable to apply override chronology '" + overrideChrono +
                                    "' because the temporal object being formatted contains date fields but" +
                                    " does not represent a whole date: " + temporal);
                        }
                    }
                }
                effectiveDate = null;
            }
        } else {
            effectiveDate = null;
        }

        // combine available data
        // this is a non-standard temporal that is almost a pure delegate
        // this better handles map-like underlying temporal instances
        return new TemporalAccessor() {
            @Override
            public boolean isSupported(TemporalField field) {
                if (effectiveDate != null && field.isDateBased()) {
                    return effectiveDate.isSupported(field);
                }
                return temporal.isSupported(field);
            }
            @Override
            public ValueRange range(TemporalField field) {
                if (effectiveDate != null && field.isDateBased()) {
                    return effectiveDate.range(field);
                }
                return temporal.range(field);
            }
            @Override
            public long getLong(TemporalField field) {
                if (effectiveDate != null && field.isDateBased()) {
                    return effectiveDate.getLong(field);
                }
                return temporal.getLong(field);
            }
            @SuppressWarnings("unchecked")
            @Override
            public <R> R query(TemporalQuery<R> query) {
                if (query == TemporalQueries.chronology()) {
                    return (R) effectiveChrono;
                }
                if (query == TemporalQueries.zoneId()) {
                    return (R) effectiveZone;
                }
                if (query == TemporalQueries.precision()) {
                    return temporal.query(query);
                }
                return query.queryFrom(this);
            }
        };
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the temporal object being output.
     *
     * <p>
     *  获取正在输出的时间对象。
     * 
     * 
     * @return the temporal object, not null
     */
    TemporalAccessor getTemporal() {
        return temporal;
    }

    /**
     * Gets the locale.
     * <p>
     * This locale is used to control localization in the format output except
     * where localization is controlled by the DecimalStyle.
     *
     * <p>
     *  获取区域设置。
     * <p>
     *  此区域设置用于控制格式输出中的本地化,除非本地化由DecimalStyle控制。
     * 
     * 
     * @return the locale, not null
     */
    Locale getLocale() {
        return formatter.getLocale();
    }

    /**
     * Gets the DecimalStyle.
     * <p>
     * The DecimalStyle controls the localization of numeric output.
     *
     * <p>
     *  获取DecimalStyle。
     * <p>
     * DecimalStyle控制数字输出的本地化。
     * 
     * 
     * @return the DecimalStyle, not null
     */
    DecimalStyle getDecimalStyle() {
        return formatter.getDecimalStyle();
    }

    //-----------------------------------------------------------------------
    /**
     * Starts the printing of an optional segment of the input.
     * <p>
     *  开始打印输入的可选段。
     * 
     */
    void startOptional() {
        this.optional++;
    }

    /**
     * Ends the printing of an optional segment of the input.
     * <p>
     *  结束输入的可选段的打印。
     * 
     */
    void endOptional() {
        this.optional--;
    }

    /**
     * Gets a value using a query.
     *
     * <p>
     *  使用查询获取值。
     * 
     * 
     * @param query  the query to use, not null
     * @return the result, null if not found and optional is true
     * @throws DateTimeException if the type is not available and the section is not optional
     */
    <R> R getValue(TemporalQuery<R> query) {
        R result = temporal.query(query);
        if (result == null && optional == 0) {
            throw new DateTimeException("Unable to extract value: " + temporal.getClass());
        }
        return result;
    }

    /**
     * Gets the value of the specified field.
     * <p>
     * This will return the value for the specified field.
     *
     * <p>
     *  获取指定字段的值。
     * <p>
     *  这将返回指定字段的值。
     * 
     * 
     * @param field  the field to find, not null
     * @return the value, null if not found and optional is true
     * @throws DateTimeException if the field is not available and the section is not optional
     */
    Long getValue(TemporalField field) {
        try {
            return temporal.getLong(field);
        } catch (DateTimeException ex) {
            if (optional > 0) {
                return null;
            }
            throw ex;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a string version of the context for debugging.
     *
     * <p>
     * 
     * @return a string representation of the context, not null
     */
    @Override
    public String toString() {
        return temporal.toString();
    }

}
