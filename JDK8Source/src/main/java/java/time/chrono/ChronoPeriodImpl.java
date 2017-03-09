/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Copyright (c) 2013, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2013,Stephen Colebourne和Michael Nascimento Santos
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
package java.time.chrono;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A period expressed in terms of a standard year-month-day calendar system.
 * <p>
 * This class is used by applications seeking to handle dates in non-ISO calendar systems.
 * For example, the Japanese, Minguo, Thai Buddhist and others.
 *
 * @implSpec
 * This class is immutable nad thread-safe.
 *
 * <p>
 *  以标准年 - 月 - 日日历系统表示的期间。
 * <p>
 *  此类由寻求处理非ISO日历系统中的日期的应用程序使用。例如,日本,民族,泰国佛教和其他。
 * 
 *  @implSpec这个类是不可变的nad线程安全的。
 * 
 * 
 * @since 1.8
 */
final class ChronoPeriodImpl
        implements ChronoPeriod, Serializable {
    // this class is only used by JDK chronology implementations and makes assumptions based on that fact

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 57387258289L;

    /**
     * The set of supported units.
     * <p>
     *  支持的单位集。
     * 
     */
    private static final List<TemporalUnit> SUPPORTED_UNITS =
            Collections.unmodifiableList(Arrays.<TemporalUnit>asList(YEARS, MONTHS, DAYS));

    /**
     * The chronology.
     * <p>
     *  年表。
     * 
     */
    private final Chronology chrono;
    /**
     * The number of years.
     * <p>
     *  年数。
     * 
     */
    final int years;
    /**
     * The number of months.
     * <p>
     *  月数。
     * 
     */
    final int months;
    /**
     * The number of days.
     * <p>
     *  天数。
     * 
     */
    final int days;

    /**
     * Creates an instance.
     * <p>
     *  创建实例。
     * 
     */
    ChronoPeriodImpl(Chronology chrono, int years, int months, int days) {
        Objects.requireNonNull(chrono, "chrono");
        this.chrono = chrono;
        this.years = years;
        this.months = months;
        this.days = days;
    }

    //-----------------------------------------------------------------------
    @Override
    public long get(TemporalUnit unit) {
        if (unit == ChronoUnit.YEARS) {
            return years;
        } else if (unit == ChronoUnit.MONTHS) {
            return months;
        } else if (unit == ChronoUnit.DAYS) {
            return days;
        } else {
            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return ChronoPeriodImpl.SUPPORTED_UNITS;
    }

    @Override
    public Chronology getChronology() {
        return chrono;
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean isZero() {
        return years == 0 && months == 0 && days == 0;
    }

    @Override
    public boolean isNegative() {
        return years < 0 || months < 0 || days < 0;
    }

    //-----------------------------------------------------------------------
    @Override
    public ChronoPeriod plus(TemporalAmount amountToAdd) {
        ChronoPeriodImpl amount = validateAmount(amountToAdd);
        return new ChronoPeriodImpl(
                chrono,
                Math.addExact(years, amount.years),
                Math.addExact(months, amount.months),
                Math.addExact(days, amount.days));
    }

    @Override
    public ChronoPeriod minus(TemporalAmount amountToSubtract) {
        ChronoPeriodImpl amount = validateAmount(amountToSubtract);
        return new ChronoPeriodImpl(
                chrono,
                Math.subtractExact(years, amount.years),
                Math.subtractExact(months, amount.months),
                Math.subtractExact(days, amount.days));
    }

    /**
     * Obtains an instance of {@code ChronoPeriodImpl} from a temporal amount.
     *
     * <p>
     *  从时间量获取{@code ChronoPeriodImpl}的实例。
     * 
     * 
     * @param amount  the temporal amount to convert, not null
     * @return the period, not null
     */
    private ChronoPeriodImpl validateAmount(TemporalAmount amount) {
        Objects.requireNonNull(amount, "amount");
        if (amount instanceof ChronoPeriodImpl == false) {
            throw new DateTimeException("Unable to obtain ChronoPeriod from TemporalAmount: " + amount.getClass());
        }
        ChronoPeriodImpl period = (ChronoPeriodImpl) amount;
        if (chrono.equals(period.getChronology()) == false) {
            throw new ClassCastException("Chronology mismatch, expected: " + chrono.getId() + ", actual: " + period.getChronology().getId());
        }
        return period;
    }

    //-----------------------------------------------------------------------
    @Override
    public ChronoPeriod multipliedBy(int scalar) {
        if (this.isZero() || scalar == 1) {
            return this;
        }
        return new ChronoPeriodImpl(
                chrono,
                Math.multiplyExact(years, scalar),
                Math.multiplyExact(months, scalar),
                Math.multiplyExact(days, scalar));
    }

    //-----------------------------------------------------------------------
    @Override
    public ChronoPeriod normalized() {
        long monthRange = monthRange();
        if (monthRange > 0) {
            long totalMonths = years * monthRange + months;
            long splitYears = totalMonths / monthRange;
            int splitMonths = (int) (totalMonths % monthRange);  // no overflow
            if (splitYears == years && splitMonths == months) {
                return this;
            }
            return new ChronoPeriodImpl(chrono, Math.toIntExact(splitYears), splitMonths, days);

        }
        return this;
    }

    /**
     * Calculates the range of months.
     *
     * <p>
     *  计算月份的范围。
     * 
     * 
     * @return the month range, -1 if not fixed range
     */
    private long monthRange() {
        ValueRange startRange = chrono.range(MONTH_OF_YEAR);
        if (startRange.isFixed() && startRange.isIntValue()) {
            return startRange.getMaximum() - startRange.getMinimum() + 1;
        }
        return -1;
    }

    //-------------------------------------------------------------------------
    @Override
    public Temporal addTo(Temporal temporal) {
        validateChrono(temporal);
        if (months == 0) {
            if (years != 0) {
                temporal = temporal.plus(years, YEARS);
            }
        } else {
            long monthRange = monthRange();
            if (monthRange > 0) {
                temporal = temporal.plus(years * monthRange + months, MONTHS);
            } else {
                if (years != 0) {
                    temporal = temporal.plus(years, YEARS);
                }
                temporal = temporal.plus(months, MONTHS);
            }
        }
        if (days != 0) {
            temporal = temporal.plus(days, DAYS);
        }
        return temporal;
    }



    @Override
    public Temporal subtractFrom(Temporal temporal) {
        validateChrono(temporal);
        if (months == 0) {
            if (years != 0) {
                temporal = temporal.minus(years, YEARS);
            }
        } else {
            long monthRange = monthRange();
            if (monthRange > 0) {
                temporal = temporal.minus(years * monthRange + months, MONTHS);
            } else {
                if (years != 0) {
                    temporal = temporal.minus(years, YEARS);
                }
                temporal = temporal.minus(months, MONTHS);
            }
        }
        if (days != 0) {
            temporal = temporal.minus(days, DAYS);
        }
        return temporal;
    }

    /**
     * Validates that the temporal has the correct chronology.
     * <p>
     *  验证时间具有正确的年表。
     * 
     */
    private void validateChrono(TemporalAccessor temporal) {
        Objects.requireNonNull(temporal, "temporal");
        Chronology temporalChrono = temporal.query(TemporalQueries.chronology());
        if (temporalChrono != null && chrono.equals(temporalChrono) == false) {
            throw new DateTimeException("Chronology mismatch, expected: " + chrono.getId() + ", actual: " + temporalChrono.getId());
        }
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChronoPeriodImpl) {
            ChronoPeriodImpl other = (ChronoPeriodImpl) obj;
            return years == other.years && months == other.months &&
                    days == other.days && chrono.equals(other.chrono);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (years + Integer.rotateLeft(months, 8) + Integer.rotateLeft(days, 16)) ^ chrono.hashCode();
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        if (isZero()) {
            return getChronology().toString() + " P0D";
        } else {
            StringBuilder buf = new StringBuilder();
            buf.append(getChronology().toString()).append(' ').append('P');
            if (years != 0) {
                buf.append(years).append('Y');
            }
            if (months != 0) {
                buf.append(months).append('M');
            }
            if (days != 0) {
                buf.append(days).append('D');
            }
            return buf.toString();
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Writes the Chronology using a
     * <a href="../../../serialized-form.html#java.time.chrono.Ser">dedicated serialized form</a>.
     * <pre>
     *  out.writeByte(12);  // identifies this as a ChronoPeriodImpl
     *  out.writeUTF(getId());  // the chronology
     *  out.writeInt(years);
     *  out.writeInt(months);
     *  out.writeInt(days);
     * </pre>
     *
     * <p>
     *  使用<a href="../../../serialized-form.html#java.time.chrono.Ser">专用序列化表单</a>撰写年表。
     * <pre>
     * out.writeByte(12); //将此标识为ChronoPeriodImpl out.writeUTF(getId()); //年表out.writeInt(年); out.writeInt(m
     * onths); out.writeInt(days);。
     * </pre>
     * 
     * @return the instance of {@code Ser}, not null
     */
    protected Object writeReplace() {
        return new Ser(Ser.CHRONO_PERIOD_TYPE, this);
    }

    /**
     * Defend against malicious streams.
     *
     * <p>
     * 
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws ObjectStreamException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeUTF(chrono.getId());
        out.writeInt(years);
        out.writeInt(months);
        out.writeInt(days);
    }

    static ChronoPeriodImpl readExternal(DataInput in) throws IOException {
        Chronology chrono = Chronology.of(in.readUTF());
        int years = in.readInt();
        int months = in.readInt();
        int days = in.readInt();
        return new ChronoPeriodImpl(chrono, years, months, days);
    }

}
