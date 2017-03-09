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

import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.FOREVER;

import java.time.DateTimeException;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.format.ResolverStyle;
import java.util.Map;

/**
 * A set of date fields that provide access to Julian Days.
 * <p>
 * The Julian Day is a standard way of expressing date and time commonly used in the scientific community.
 * It is expressed as a decimal number of whole days where days start at midday.
 * This class represents variations on Julian Days that count whole days from midnight.
 * <p>
 * The fields are implemented relative to {@link ChronoField#EPOCH_DAY EPOCH_DAY}.
 * The fields are supported, and can be queried and set if {@code EPOCH_DAY} is available.
 * The fields work with all chronologies.
 *
 * @implSpec
 * This is an immutable and thread-safe class.
 *
 * <p>
 *  一组日期字段,用于提供对Julian Days的访问。
 * <p>
 *  儒略日是表达科学界常用的日期和时间的标准方式。它表示为从中午开始的整天的十进制数。这个类表示从午夜算起整天的朱利安天的变化。
 * <p>
 *  这些字段相对于{@link ChronoField#EPOCH_DAY EPOCH_DAY}实现。这些字段是受支持的,如果{@code EPOCH_DAY}可用,则可以查询和设置。
 * 字段与所有年表一起使用。
 * 
 *  @implSpec这是一个不可变和线程安全的类。
 * 
 * 
 * @since 1.8
 */
public final class JulianFields {

    /**
     * The offset from Julian to EPOCH DAY.
     * <p>
     *  从朱利安到EPOCH DAY的偏移量。
     * 
     */
    private static final long JULIAN_DAY_OFFSET = 2440588L;

    /**
     * Julian Day field.
     * <p>
     * This is an integer-based version of the Julian Day Number.
     * Julian Day is a well-known system that represents the count of whole days since day 0,
     * which is defined to be January 1, 4713 BCE in the Julian calendar, and -4713-11-24 Gregorian.
     * The field  has "JulianDay" as 'name', and 'DAYS' as 'baseUnit'.
     * The field always refers to the local date-time, ignoring the offset or zone.
     * <p>
     * For date-times, 'JULIAN_DAY.getFrom()' assumes the same value from
     * midnight until just before the next midnight.
     * When 'JULIAN_DAY.adjustInto()' is applied to a date-time, the time of day portion remains unaltered.
     * 'JULIAN_DAY.adjustInto()' and 'JULIAN_DAY.getFrom()' only apply to {@code Temporal} objects that
     * can be converted into {@link ChronoField#EPOCH_DAY}.
     * An {@link UnsupportedTemporalTypeException} is thrown for any other type of object.
     * <p>
     * In the resolving phase of parsing, a date can be created from a Julian Day field.
     * In {@linkplain ResolverStyle#STRICT strict mode} and {@linkplain ResolverStyle#SMART smart mode}
     * the Julian Day value is validated against the range of valid values.
     * In {@linkplain ResolverStyle#LENIENT lenient mode} no validation occurs.
     *
     * <h3>Astronomical and Scientific Notes</h3>
     * The standard astronomical definition uses a fraction to indicate the time-of-day,
     * thus 3.25 would represent the time 18:00, since days start at midday.
     * This implementation uses an integer and days starting at midnight.
     * The integer value for the Julian Day Number is the astronomical Julian Day value at midday
     * of the date in question.
     * This amounts to the astronomical Julian Day, rounded to an integer {@code JDN = floor(JD + 0.5)}.
     *
     * <pre>
     *  | ISO date          |  Julian Day Number | Astronomical Julian Day |
     *  | 1970-01-01T00:00  |         2,440,588  |         2,440,587.5     |
     *  | 1970-01-01T06:00  |         2,440,588  |         2,440,587.75    |
     *  | 1970-01-01T12:00  |         2,440,588  |         2,440,588.0     |
     *  | 1970-01-01T18:00  |         2,440,588  |         2,440,588.25    |
     *  | 1970-01-02T00:00  |         2,440,589  |         2,440,588.5     |
     *  | 1970-01-02T06:00  |         2,440,589  |         2,440,588.75    |
     *  | 1970-01-02T12:00  |         2,440,589  |         2,440,589.0     |
     * </pre>
     * <p>
     * Julian Days are sometimes taken to imply Universal Time or UTC, but this
     * implementation always uses the Julian Day number for the local date,
     * regardless of the offset or time-zone.
     * <p>
     *  朱利安日字段。
     * <p>
     * 这是基于整数的朱利安日数字版本。朱利安日是一种众所周知的系统,表示自第0天以来的整天的数量,其被定义为儒略历中的4713BCE 1月1日和-4713-11-24格里高利。
     * 该字段的"JulianDay"为"name","DAYS"为"baseUnit"。该字段总是引用本地日期时间,忽略偏移或区域。
     * <p>
     *  对于日期时间,"JULIAN_DAY.getFrom()"假设从午夜到下一个午夜之前的值相同。当'JULIAN_DAY.adjustInto()'应用于日期时间时,日期时间保持不变。
     *  'JULIAN_DAY.adjustInto()'和'JULIAN_DAY.getFrom()'仅适用于可以转换为{@link ChronoField#EPOCH_DAY}的{@code Temporal}
     * 对象。
     *  对于日期时间,"JULIAN_DAY.getFrom()"假设从午夜到下一个午夜之前的值相同。当'JULIAN_DAY.adjustInto()'应用于日期时间时,日期时间保持不变。
     * 对任何其他类型的对象抛出{@link UnsupportedTemporalTypeException}。
     * <p>
     *  在解析的解析阶段,可以从朱利安日字段创建日期。
     * 在{@linkplain ResolverStyle#STRICT strict mode}和{@linkplain ResolverStyle#SMART smart模式}中,将根据有效值范围验证儒略
     * 日值。
     *  在解析的解析阶段,可以从朱利安日字段创建日期。在{@linkplain ResolverStyle#LENIENT宽松模式}中不进行验证。
     * 
     * <h3>天文学和科学注释</h3>标准天文学定义使用一个分数来表示一天中的时间,因此3.25将表示时间18:00,因为天从中午开始。此实现使用整数和从午夜开始的日期。
     * 朱利安日数的整数值是相关日期中午的天文朱利安日值。这相当于天文Julian日,四舍五入为整数{@code JDN = floor(JD + 0.5)}。
     * 
     * <pre>
     *  | ISO日期|朱利安日数|天文Julian天| | 1970-01-01T00：00 | 2,440,588 | 2,440,587.5 | | 1970-01-01T06：00 | 2,440,5
     * 88 | 2,440,587.75 | | 1970-01-01T12：00 | 2,440,588 | 2,440,588.0 | | 1970-01-01T18：00 | 2,440,588 | 2
     * ,440,588.25 | | 1970-01-02T00：00 | 2,440,589 | 2,440,588.5 | | 1970-01-02T06：00 | 2,440,589 | 2,440,5
     * 88.75 | | 1970-01-02T12：00 | 2,440,589 | 2,440,589.0 |。
     * </pre>
     * <p>
     *  儒略日有时用于表示世界时或UTC,但此实施方式始终使用本地日期的儒略日数字,而不考虑偏移量或时区。
     * 
     */
    public static final TemporalField JULIAN_DAY = Field.JULIAN_DAY;

    /**
     * Modified Julian Day field.
     * <p>
     * This is an integer-based version of the Modified Julian Day Number.
     * Modified Julian Day (MJD) is a well-known system that counts days continuously.
     * It is defined relative to astronomical Julian Day as  {@code MJD = JD - 2400000.5}.
     * Each Modified Julian Day runs from midnight to midnight.
     * The field always refers to the local date-time, ignoring the offset or zone.
     * <p>
     * For date-times, 'MODIFIED_JULIAN_DAY.getFrom()' assumes the same value from
     * midnight until just before the next midnight.
     * When 'MODIFIED_JULIAN_DAY.adjustInto()' is applied to a date-time, the time of day portion remains unaltered.
     * 'MODIFIED_JULIAN_DAY.adjustInto()' and 'MODIFIED_JULIAN_DAY.getFrom()' only apply to {@code Temporal} objects
     * that can be converted into {@link ChronoField#EPOCH_DAY}.
     * An {@link UnsupportedTemporalTypeException} is thrown for any other type of object.
     * <p>
     * This implementation is an integer version of MJD with the decimal part rounded to floor.
     * <p>
     * In the resolving phase of parsing, a date can be created from a Modified Julian Day field.
     * In {@linkplain ResolverStyle#STRICT strict mode} and {@linkplain ResolverStyle#SMART smart mode}
     * the Modified Julian Day value is validated against the range of valid values.
     * In {@linkplain ResolverStyle#LENIENT lenient mode} no validation occurs.
     *
     * <h3>Astronomical and Scientific Notes</h3>
     * <pre>
     *  | ISO date          | Modified Julian Day |      Decimal MJD |
     *  | 1970-01-01T00:00  |             40,587  |       40,587.0   |
     *  | 1970-01-01T06:00  |             40,587  |       40,587.25  |
     *  | 1970-01-01T12:00  |             40,587  |       40,587.5   |
     *  | 1970-01-01T18:00  |             40,587  |       40,587.75  |
     *  | 1970-01-02T00:00  |             40,588  |       40,588.0   |
     *  | 1970-01-02T06:00  |             40,588  |       40,588.25  |
     *  | 1970-01-02T12:00  |             40,588  |       40,588.5   |
     * </pre>
     *
     * Modified Julian Days are sometimes taken to imply Universal Time or UTC, but this
     * implementation always uses the Modified Julian Day for the local date,
     * regardless of the offset or time-zone.
     * <p>
     *  修改的儒略日字段。
     * <p>
     * 这是修正的朱利安日数的基于整数的版本。修正儒略日(MJD)是一种众所周知的连续计数天数的系统。它相对于天文儒略日被定义为{@code MJD = JD-2400000.5}。
     * 每个修改的朱利安日运行从午夜到午夜。该字段总是引用本地日期时间,忽略偏移或区域。
     * <p>
     *  对于日期时间,"MODIFIED_JULIAN_DAY.getFrom()"假设从午夜到下一个午夜之前的值相同。
     * 当将"MODIFIED_JULIAN_DAY.adjustInto()"应用于日期时间时,日期时间保持不变。
     *  "MODIFIED_JULIAN_DAY.adjustInto()"和"MODIFIED_JULIAN_DAY.getFrom()"仅适用于可以转换为{@link ChronoField#EPOCH_DAY}
     * 的{@code Temporal}对象。
     * 当将"MODIFIED_JULIAN_DAY.adjustInto()"应用于日期时间时,日期时间保持不变。
     * 对任何其他类型的对象抛出{@link UnsupportedTemporalTypeException}。
     * <p>
     *  此实现是MJD的整数版本,小数部分舍入为floor。
     * <p>
     *  在解析的解析阶段,可以从修改的儒略日期字段创建日期。
     * 在{@linkplain ResolverStyle#STRICT strict mode}和{@linkplain ResolverStyle#SMART smart模式}中,修改的儒略日值将根据有效
     * 值的范围进行验证。
     *  在解析的解析阶段,可以从修改的儒略日期字段创建日期。在{@linkplain ResolverStyle#LENIENT宽松模式}中不进行验证。
     * 
     *  <h3>天文学和科学注释</h3>
     * <pre>
     * | ISO日期|修改儒略日|十进制MJD | | 1970-01-01T00：00 | 40,587 | 40,587.0 | | 1970-01-01T06：00 | 40,587 | 40,587.
     * 25 | | 1970-01-01T12：00 | 40,587 | 40,587.5 | | 1970-01-01T18：00 | 40,587 | 40,587.75 | | 1970-01-02T
     * 00：00 | 40,588 | 40,588.0 | | 1970-01-02T06：00 | 40,588 | 40,588.25 | | 1970-01-02T12：00 | 40,588 | 4
     * 0,588.5 |。
     * </pre>
     */
    public static final TemporalField MODIFIED_JULIAN_DAY = Field.MODIFIED_JULIAN_DAY;

    /**
     * Rata Die field.
     * <p>
     * Rata Die counts whole days continuously starting day 1 at midnight at the beginning of 0001-01-01 (ISO).
     * The field always refers to the local date-time, ignoring the offset or zone.
     * <p>
     * For date-times, 'RATA_DIE.getFrom()' assumes the same value from
     * midnight until just before the next midnight.
     * When 'RATA_DIE.adjustInto()' is applied to a date-time, the time of day portion remains unaltered.
     * 'RATA_DIE.adjustInto()' and 'RATA_DIE.getFrom()' only apply to {@code Temporal} objects
     * that can be converted into {@link ChronoField#EPOCH_DAY}.
     * An {@link UnsupportedTemporalTypeException} is thrown for any other type of object.
     * <p>
     * In the resolving phase of parsing, a date can be created from a Rata Die field.
     * In {@linkplain ResolverStyle#STRICT strict mode} and {@linkplain ResolverStyle#SMART smart mode}
     * the Rata Die value is validated against the range of valid values.
     * In {@linkplain ResolverStyle#LENIENT lenient mode} no validation occurs.
     * <p>
     * 
     *  修改的儒略日有时被视为表示世界时间或UTC,但此实施方式总是对本地日期使用修改的儒略日,而不考虑偏移量或时区。
     * 
     */
    public static final TemporalField RATA_DIE = Field.RATA_DIE;

    /**
     * Restricted constructor.
     * <p>
     *  Rata Die场。
     * <p>
     *  Rata Die从0001-01-01(ISO)开始的第1天午夜开始连续计数整天。该字段总是引用本地日期时间,忽略偏移或区域。
     * <p>
     *  对于日期时间,'RATA_DIE.getFrom()'假设从午夜到下一个午夜之前的值相同。当'RATA_DIE.adjustInto()'应用于日期时间时,时间部分保持不变。
     *  'RATA_DIE.adjustInto()'和'RATA_DIE.getFrom()'仅适用于可以转换为{@link ChronoField#EPOCH_DAY}的{@code Temporal}对
     * 象。
     *  对于日期时间,'RATA_DIE.getFrom()'假设从午夜到下一个午夜之前的值相同。当'RATA_DIE.adjustInto()'应用于日期时间时,时间部分保持不变。
     * 对任何其他类型的对象抛出{@link UnsupportedTemporalTypeException}。
     * <p>
     * 在解析的解析阶段中,可以从Rata Die字段创建日期。
     */
    private JulianFields() {
        throw new AssertionError("Not instantiable");
    }

    /**
     * Implementation of JulianFields.  Each instance is a singleton.
     * <p>
     * 在{@linkplain ResolverStyle#STRICT strict mode}和{@linkplain ResolverStyle#SMART smart mode}中,Rata Die值
     * 根据有效值的范围进行验证。
     * 在解析的解析阶段中,可以从Rata Die字段创建日期。在{@linkplain ResolverStyle#LENIENT宽松模式}中不进行验证。
     * 
     */
    private static enum Field implements TemporalField {
        JULIAN_DAY("JulianDay", DAYS, FOREVER, JULIAN_DAY_OFFSET),
        MODIFIED_JULIAN_DAY("ModifiedJulianDay", DAYS, FOREVER, 40587L),
        RATA_DIE("RataDie", DAYS, FOREVER, 719163L);

        private static final long serialVersionUID = -7501623920830201812L;

        private final transient String name;
        private final transient TemporalUnit baseUnit;
        private final transient TemporalUnit rangeUnit;
        private final transient ValueRange range;
        private final transient long offset;

        private Field(String name, TemporalUnit baseUnit, TemporalUnit rangeUnit, long offset) {
            this.name = name;
            this.baseUnit = baseUnit;
            this.rangeUnit = rangeUnit;
            this.range = ValueRange.of(-365243219162L + offset, 365241780471L + offset);
            this.offset = offset;
        }

        //-----------------------------------------------------------------------
        @Override
        public TemporalUnit getBaseUnit() {
            return baseUnit;
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return rangeUnit;
        }

        @Override
        public boolean isDateBased() {
            return true;
        }

        @Override
        public boolean isTimeBased() {
            return false;
        }

        @Override
        public ValueRange range() {
            return range;
        }

        //-----------------------------------------------------------------------
        @Override
        public boolean isSupportedBy(TemporalAccessor temporal) {
            return temporal.isSupported(EPOCH_DAY);
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            if (isSupportedBy(temporal) == false) {
                throw new DateTimeException("Unsupported field: " + this);
            }
            return range();
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            return temporal.getLong(EPOCH_DAY) + offset;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            if (range().isValidValue(newValue) == false) {
                throw new DateTimeException("Invalid value: " + name + " " + newValue);
            }
            return (R) temporal.with(EPOCH_DAY, Math.subtractExact(newValue, offset));
        }

        //-----------------------------------------------------------------------
        @Override
        public ChronoLocalDate resolve(
                Map<TemporalField, Long> fieldValues, TemporalAccessor partialTemporal, ResolverStyle resolverStyle) {
            long value = fieldValues.remove(this);
            Chronology chrono = Chronology.from(partialTemporal);
            if (resolverStyle == ResolverStyle.LENIENT) {
                return chrono.dateEpochDay(Math.subtractExact(value, offset));
            }
            range().checkValidValue(value, this);
            return chrono.dateEpochDay(value - offset);
        }

        //-----------------------------------------------------------------------
        @Override
        public String toString() {
            return name;
        }
    }
}
