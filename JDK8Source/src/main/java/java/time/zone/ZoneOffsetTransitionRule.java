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
 * Copyright (c) 2009-2012, Stephen Colebourne & Michael Nascimento Santos
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
 *  版权所有(c)2009-2012,Stephen Colebourne和Michael Nascimento Santos
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
package java.time.zone;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.chrono.IsoChronology;
import java.util.Objects;

/**
 * A rule expressing how to create a transition.
 * <p>
 * This class allows rules for identifying future transitions to be expressed.
 * A rule might be written in many forms:
 * <ul>
 * <li>the 16th March
 * <li>the Sunday on or after the 16th March
 * <li>the Sunday on or before the 16th March
 * <li>the last Sunday in February
 * </ul>
 * These different rule types can be expressed and queried.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  表示如何创建转换的规则。
 * <p>
 *  此类允许表示用于识别未来转换的规则。规则可以以许多形式写：
 * <ul>
 *  <li> 3月16日<li> 3月16日或之后的星期天<li> 3月16日或之前的星期天<li> 2月最后一个星期日
 * </ul>
 *  这些不同的规则类型可以表示和查询。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class ZoneOffsetTransitionRule implements Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 6889046316657758795L;

    /**
     * The month of the month-day of the first day of the cutover week.
     * The actual date will be adjusted by the dowChange field.
     * <p>
     *  割接周的第一天的月 - 日的月份。实际的日期将通过dowChange字段进行调整。
     * 
     */
    private final Month month;
    /**
     * The day-of-month of the month-day of the cutover week.
     * If positive, it is the start of the week where the cutover can occur.
     * If negative, it represents the end of the week where cutover can occur.
     * The value is the number of days from the end of the month, such that
     * {@code -1} is the last day of the month, {@code -2} is the second
     * to last day, and so on.
     * <p>
     * 割接周的月 - 日的月。如果为正,则是切换可能发生的星期的开始。如果为负,则表示可能发生割接的星期末。
     * 该值是从月底算起的天数,因此{@code -1}是该月的最后一天,{@code -2}是第二天到最后一天,依此类推。
     * 
     */
    private final byte dom;
    /**
     * The cutover day-of-week, null to retain the day-of-month.
     * <p>
     *  切换日期,星期,null保留月。
     * 
     */
    private final DayOfWeek dow;
    /**
     * The cutover time in the 'before' offset.
     * <p>
     *  割接时间在"before"偏移。
     * 
     */
    private final LocalTime time;
    /**
     * Whether the cutover time is midnight at the end of day.
     * <p>
     *  切换时间是否在一天结束时为午夜。
     * 
     */
    private final boolean timeEndOfDay;
    /**
     * The definition of how the local time should be interpreted.
     * <p>
     *  定义如何解释本地时间。
     * 
     */
    private final TimeDefinition timeDefinition;
    /**
     * The standard offset at the cutover.
     * <p>
     *  割接处的标准偏移。
     * 
     */
    private final ZoneOffset standardOffset;
    /**
     * The offset before the cutover.
     * <p>
     *  割接前的偏移量。
     * 
     */
    private final ZoneOffset offsetBefore;
    /**
     * The offset after the cutover.
     * <p>
     *  割接后的偏移量。
     * 
     */
    private final ZoneOffset offsetAfter;

    /**
     * Obtains an instance defining the yearly rule to create transitions between two offsets.
     * <p>
     * Applications should normally obtain an instance from {@link ZoneRules}.
     * This factory is only intended for use when creating {@link ZoneRules}.
     *
     * <p>
     *  获取定义年度规则以在两个偏移之间创建过渡的实例。
     * <p>
     *  应用程序通常应从{@link ZoneRules}获取一个实例。此工厂仅用于创建{@link ZoneRules}时使用。
     * 
     * 
     * @param month  the month of the month-day of the first day of the cutover week, not null
     * @param dayOfMonthIndicator  the day of the month-day of the cutover week, positive if the week is that
     *  day or later, negative if the week is that day or earlier, counting from the last day of the month,
     *  from -28 to 31 excluding 0
     * @param dayOfWeek  the required day-of-week, null if the month-day should not be changed
     * @param time  the cutover time in the 'before' offset, not null
     * @param timeEndOfDay  whether the time is midnight at the end of day
     * @param timeDefnition  how to interpret the cutover
     * @param standardOffset  the standard offset in force at the cutover, not null
     * @param offsetBefore  the offset before the cutover, not null
     * @param offsetAfter  the offset after the cutover, not null
     * @return the rule, not null
     * @throws IllegalArgumentException if the day of month indicator is invalid
     * @throws IllegalArgumentException if the end of day flag is true when the time is not midnight
     */
    public static ZoneOffsetTransitionRule of(
            Month month,
            int dayOfMonthIndicator,
            DayOfWeek dayOfWeek,
            LocalTime time,
            boolean timeEndOfDay,
            TimeDefinition timeDefnition,
            ZoneOffset standardOffset,
            ZoneOffset offsetBefore,
            ZoneOffset offsetAfter) {
        Objects.requireNonNull(month, "month");
        Objects.requireNonNull(time, "time");
        Objects.requireNonNull(timeDefnition, "timeDefnition");
        Objects.requireNonNull(standardOffset, "standardOffset");
        Objects.requireNonNull(offsetBefore, "offsetBefore");
        Objects.requireNonNull(offsetAfter, "offsetAfter");
        if (dayOfMonthIndicator < -28 || dayOfMonthIndicator > 31 || dayOfMonthIndicator == 0) {
            throw new IllegalArgumentException("Day of month indicator must be between -28 and 31 inclusive excluding zero");
        }
        if (timeEndOfDay && time.equals(LocalTime.MIDNIGHT) == false) {
            throw new IllegalArgumentException("Time must be midnight when end of day flag is true");
        }
        return new ZoneOffsetTransitionRule(month, dayOfMonthIndicator, dayOfWeek, time, timeEndOfDay, timeDefnition, standardOffset, offsetBefore, offsetAfter);
    }

    /**
     * Creates an instance defining the yearly rule to create transitions between two offsets.
     *
     * <p>
     *  创建定义年度规则以在两个偏移之间创建过渡的实例。
     * 
     * 
     * @param month  the month of the month-day of the first day of the cutover week, not null
     * @param dayOfMonthIndicator  the day of the month-day of the cutover week, positive if the week is that
     *  day or later, negative if the week is that day or earlier, counting from the last day of the month,
     *  from -28 to 31 excluding 0
     * @param dayOfWeek  the required day-of-week, null if the month-day should not be changed
     * @param time  the cutover time in the 'before' offset, not null
     * @param timeEndOfDay  whether the time is midnight at the end of day
     * @param timeDefnition  how to interpret the cutover
     * @param standardOffset  the standard offset in force at the cutover, not null
     * @param offsetBefore  the offset before the cutover, not null
     * @param offsetAfter  the offset after the cutover, not null
     * @throws IllegalArgumentException if the day of month indicator is invalid
     * @throws IllegalArgumentException if the end of day flag is true when the time is not midnight
     */
    ZoneOffsetTransitionRule(
            Month month,
            int dayOfMonthIndicator,
            DayOfWeek dayOfWeek,
            LocalTime time,
            boolean timeEndOfDay,
            TimeDefinition timeDefnition,
            ZoneOffset standardOffset,
            ZoneOffset offsetBefore,
            ZoneOffset offsetAfter) {
        this.month = month;
        this.dom = (byte) dayOfMonthIndicator;
        this.dow = dayOfWeek;
        this.time = time;
        this.timeEndOfDay = timeEndOfDay;
        this.timeDefinition = timeDefnition;
        this.standardOffset = standardOffset;
        this.offsetBefore = offsetBefore;
        this.offsetAfter = offsetAfter;
    }

    //-----------------------------------------------------------------------
    /**
     * Defend against malicious streams.
     *
     * <p>
     *  防御恶意流。
     * 
     * 
     * @param s the stream to read
     * @throws InvalidObjectException always
     */
    private void readObject(ObjectInputStream s) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /**
     * Writes the object using a
     * <a href="../../../serialized-form.html#java.time.zone.Ser">dedicated serialized form</a>.
     * <p>
     *  使用<a href="../../../serialized-form.html#java.time.zone.Ser">专用序列化表单</a>写入对象。
     * 
     * 
     * @serialData
     * Refer to the serialized form of
     * <a href="../../../serialized-form.html#java.time.zone.ZoneRules">ZoneRules.writeReplace</a>
     * for the encoding of epoch seconds and offsets.
     * <pre style="font-size:1.0em">{@code
     *
     *      out.writeByte(3);                // identifies a ZoneOffsetTransition
     *      final int timeSecs = (timeEndOfDay ? 86400 : time.toSecondOfDay());
     *      final int stdOffset = standardOffset.getTotalSeconds();
     *      final int beforeDiff = offsetBefore.getTotalSeconds() - stdOffset;
     *      final int afterDiff = offsetAfter.getTotalSeconds() - stdOffset;
     *      final int timeByte = (timeSecs % 3600 == 0 ? (timeEndOfDay ? 24 : time.getHour()) : 31);
     *      final int stdOffsetByte = (stdOffset % 900 == 0 ? stdOffset / 900 + 128 : 255);
     *      final int beforeByte = (beforeDiff == 0 || beforeDiff == 1800 || beforeDiff == 3600 ? beforeDiff / 1800 : 3);
     *      final int afterByte = (afterDiff == 0 || afterDiff == 1800 || afterDiff == 3600 ? afterDiff / 1800 : 3);
     *      final int dowByte = (dow == null ? 0 : dow.getValue());
     *      int b = (month.getValue() << 28) +          // 4 bits
     *              ((dom + 32) << 22) +                // 6 bits
     *              (dowByte << 19) +                   // 3 bits
     *              (timeByte << 14) +                  // 5 bits
     *              (timeDefinition.ordinal() << 12) +  // 2 bits
     *              (stdOffsetByte << 4) +              // 8 bits
     *              (beforeByte << 2) +                 // 2 bits
     *              afterByte;                          // 2 bits
     *      out.writeInt(b);
     *      if (timeByte == 31) {
     *          out.writeInt(timeSecs);
     *      }
     *      if (stdOffsetByte == 255) {
     *          out.writeInt(stdOffset);
     *      }
     *      if (beforeByte == 3) {
     *          out.writeInt(offsetBefore.getTotalSeconds());
     *      }
     *      if (afterByte == 3) {
     *          out.writeInt(offsetAfter.getTotalSeconds());
     *      }
     * }
     * </pre>
     *
     * @return the replacing object, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.ZOTRULE, this);
    }

    /**
     * Writes the state to the stream.
     *
     * <p>
     *  将状态写入流。
     * 
     * 
     * @param out  the output stream, not null
     * @throws IOException if an error occurs
     */
    void writeExternal(DataOutput out) throws IOException {
        final int timeSecs = (timeEndOfDay ? 86400 : time.toSecondOfDay());
        final int stdOffset = standardOffset.getTotalSeconds();
        final int beforeDiff = offsetBefore.getTotalSeconds() - stdOffset;
        final int afterDiff = offsetAfter.getTotalSeconds() - stdOffset;
        final int timeByte = (timeSecs % 3600 == 0 ? (timeEndOfDay ? 24 : time.getHour()) : 31);
        final int stdOffsetByte = (stdOffset % 900 == 0 ? stdOffset / 900 + 128 : 255);
        final int beforeByte = (beforeDiff == 0 || beforeDiff == 1800 || beforeDiff == 3600 ? beforeDiff / 1800 : 3);
        final int afterByte = (afterDiff == 0 || afterDiff == 1800 || afterDiff == 3600 ? afterDiff / 1800 : 3);
        final int dowByte = (dow == null ? 0 : dow.getValue());
        int b = (month.getValue() << 28) +          // 4 bits
                ((dom + 32) << 22) +                // 6 bits
                (dowByte << 19) +                   // 3 bits
                (timeByte << 14) +                  // 5 bits
                (timeDefinition.ordinal() << 12) +  // 2 bits
                (stdOffsetByte << 4) +              // 8 bits
                (beforeByte << 2) +                 // 2 bits
                afterByte;                          // 2 bits
        out.writeInt(b);
        if (timeByte == 31) {
            out.writeInt(timeSecs);
        }
        if (stdOffsetByte == 255) {
            out.writeInt(stdOffset);
        }
        if (beforeByte == 3) {
            out.writeInt(offsetBefore.getTotalSeconds());
        }
        if (afterByte == 3) {
            out.writeInt(offsetAfter.getTotalSeconds());
        }
    }

    /**
     * Reads the state from the stream.
     *
     * <p>
     *  从流中读取状态。
     * 
     * 
     * @param in  the input stream, not null
     * @return the created object, not null
     * @throws IOException if an error occurs
     */
    static ZoneOffsetTransitionRule readExternal(DataInput in) throws IOException {
        int data = in.readInt();
        Month month = Month.of(data >>> 28);
        int dom = ((data & (63 << 22)) >>> 22) - 32;
        int dowByte = (data & (7 << 19)) >>> 19;
        DayOfWeek dow = dowByte == 0 ? null : DayOfWeek.of(dowByte);
        int timeByte = (data & (31 << 14)) >>> 14;
        TimeDefinition defn = TimeDefinition.values()[(data & (3 << 12)) >>> 12];
        int stdByte = (data & (255 << 4)) >>> 4;
        int beforeByte = (data & (3 << 2)) >>> 2;
        int afterByte = (data & 3);
        LocalTime time = (timeByte == 31 ? LocalTime.ofSecondOfDay(in.readInt()) : LocalTime.of(timeByte % 24, 0));
        ZoneOffset std = (stdByte == 255 ? ZoneOffset.ofTotalSeconds(in.readInt()) : ZoneOffset.ofTotalSeconds((stdByte - 128) * 900));
        ZoneOffset before = (beforeByte == 3 ? ZoneOffset.ofTotalSeconds(in.readInt()) : ZoneOffset.ofTotalSeconds(std.getTotalSeconds() + beforeByte * 1800));
        ZoneOffset after = (afterByte == 3 ? ZoneOffset.ofTotalSeconds(in.readInt()) : ZoneOffset.ofTotalSeconds(std.getTotalSeconds() + afterByte * 1800));
        return ZoneOffsetTransitionRule.of(month, dom, dow, time, timeByte == 24, defn, std, before, after);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the month of the transition.
     * <p>
     * If the rule defines an exact date then the month is the month of that date.
     * <p>
     * If the rule defines a week where the transition might occur, then the month
     * if the month of either the earliest or latest possible date of the cutover.
     *
     * <p>
     *  获取转换的月份。
     * <p>
     *  如果规则定义确切的日期,则月份是该日期的月份。
     * <p>
     *  如果规则定义了可能发生转换的星期,则指定割接的最早或最近可能日期的月份。
     * 
     * 
     * @return the month of the transition, not null
     */
    public Month getMonth() {
        return month;
    }

    /**
     * Gets the indicator of the day-of-month of the transition.
     * <p>
     * If the rule defines an exact date then the day is the month of that date.
     * <p>
     * If the rule defines a week where the transition might occur, then the day
     * defines either the start of the end of the transition week.
     * <p>
     * If the value is positive, then it represents a normal day-of-month, and is the
     * earliest possible date that the transition can be.
     * The date may refer to 29th February which should be treated as 1st March in non-leap years.
     * <p>
     * If the value is negative, then it represents the number of days back from the
     * end of the month where {@code -1} is the last day of the month.
     * In this case, the day identified is the latest possible date that the transition can be.
     *
     * <p>
     * 获取转换的月份的指标。
     * <p>
     *  如果规则定义确切的日期,则日期是该日期的月份。
     * <p>
     *  如果规则定义了可能发生转换的一周,那么该天定义转换周结束的开始。
     * <p>
     *  如果值为正,则它表示正常的日期,并且是转换可能的最早日期。日期可以参考2月29日,应该被视为非闰年的3月1日。
     * <p>
     *  如果值为负,则表示从月底开始的天数,其中{@code -1}是月份的最后一天。在这种情况下,识别的日期是转换可能的最晚可能的日期。
     * 
     * 
     * @return the day-of-month indicator, from -28 to 31 excluding 0
     */
    public int getDayOfMonthIndicator() {
        return dom;
    }

    /**
     * Gets the day-of-week of the transition.
     * <p>
     * If the rule defines an exact date then this returns null.
     * <p>
     * If the rule defines a week where the cutover might occur, then this method
     * returns the day-of-week that the month-day will be adjusted to.
     * If the day is positive then the adjustment is later.
     * If the day is negative then the adjustment is earlier.
     *
     * <p>
     *  获取过渡的星期几。
     * <p>
     *  如果规则定义确切的日期,则返回null。
     * <p>
     *  如果规则定义可能发生割接的星期,则此方法返回月份日将被调整为的星期几。如果天是正的,那么调整是稍后的。如果日期为负,则调整较早。
     * 
     * 
     * @return the day-of-week that the transition occurs, null if the rule defines an exact date
     */
    public DayOfWeek getDayOfWeek() {
        return dow;
    }

    /**
     * Gets the local time of day of the transition which must be checked with
     * {@link #isMidnightEndOfDay()}.
     * <p>
     * The time is converted into an instant using the time definition.
     *
     * <p>
     *  获取转换的当天时间,必须使用{@link #isMidnightEndOfDay()}进行检查。
     * <p>
     *  使用时间定义将时间转换为即时。
     * 
     * 
     * @return the local time of day of the transition, not null
     */
    public LocalTime getLocalTime() {
        return time;
    }

    /**
     * Is the transition local time midnight at the end of day.
     * <p>
     * The transition may be represented as occurring at 24:00.
     *
     * <p>
     *  是转换本地时间午夜在一天结束。
     * <p>
     *  该转换可以表示为在24:00发生。
     * 
     * 
     * @return whether a local time of midnight is at the start or end of the day
     */
    public boolean isMidnightEndOfDay() {
        return timeEndOfDay;
    }

    /**
     * Gets the time definition, specifying how to convert the time to an instant.
     * <p>
     * The local time can be converted to an instant using the standard offset,
     * the wall offset or UTC.
     *
     * <p>
     *  获取时间定义,指定如何将时间转换为即时。
     * <p>
     * 本地时间可以使用标准偏移,壁偏移或UTC转换为瞬时。
     * 
     * 
     * @return the time definition, not null
     */
    public TimeDefinition getTimeDefinition() {
        return timeDefinition;
    }

    /**
     * Gets the standard offset in force at the transition.
     *
     * <p>
     *  获取过渡时的标准偏置。
     * 
     * 
     * @return the standard offset, not null
     */
    public ZoneOffset getStandardOffset() {
        return standardOffset;
    }

    /**
     * Gets the offset before the transition.
     *
     * <p>
     *  获取转换前的偏移量。
     * 
     * 
     * @return the offset before, not null
     */
    public ZoneOffset getOffsetBefore() {
        return offsetBefore;
    }

    /**
     * Gets the offset after the transition.
     *
     * <p>
     *  获取转换后的偏移量。
     * 
     * 
     * @return the offset after, not null
     */
    public ZoneOffset getOffsetAfter() {
        return offsetAfter;
    }

    //-----------------------------------------------------------------------
    /**
     * Creates a transition instance for the specified year.
     * <p>
     * Calculations are performed using the ISO-8601 chronology.
     *
     * <p>
     *  创建指定年份的转换实例。
     * <p>
     *  使用ISO-8601年表进行计算。
     * 
     * 
     * @param year  the year to create a transition for, not null
     * @return the transition instance, not null
     */
    public ZoneOffsetTransition createTransition(int year) {
        LocalDate date;
        if (dom < 0) {
            date = LocalDate.of(year, month, month.length(IsoChronology.INSTANCE.isLeapYear(year)) + 1 + dom);
            if (dow != null) {
                date = date.with(previousOrSame(dow));
            }
        } else {
            date = LocalDate.of(year, month, dom);
            if (dow != null) {
                date = date.with(nextOrSame(dow));
            }
        }
        if (timeEndOfDay) {
            date = date.plusDays(1);
        }
        LocalDateTime localDT = LocalDateTime.of(date, time);
        LocalDateTime transition = timeDefinition.createDateTime(localDT, standardOffset, offsetBefore);
        return new ZoneOffsetTransition(transition, offsetBefore, offsetAfter);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this object equals another.
     * <p>
     * The entire state of the object is compared.
     *
     * <p>
     *  检查此对象是否等于另一个。
     * <p>
     *  比较对象的整个状态。
     * 
     * 
     * @param otherRule  the other object to compare to, null returns false
     * @return true if equal
     */
    @Override
    public boolean equals(Object otherRule) {
        if (otherRule == this) {
            return true;
        }
        if (otherRule instanceof ZoneOffsetTransitionRule) {
            ZoneOffsetTransitionRule other = (ZoneOffsetTransitionRule) otherRule;
            return month == other.month && dom == other.dom && dow == other.dow &&
                timeDefinition == other.timeDefinition &&
                time.equals(other.time) &&
                timeEndOfDay == other.timeEndOfDay &&
                standardOffset.equals(other.standardOffset) &&
                offsetBefore.equals(other.offsetBefore) &&
                offsetAfter.equals(other.offsetAfter);
        }
        return false;
    }

    /**
     * Returns a suitable hash code.
     *
     * <p>
     *  返回合适的哈希码。
     * 
     * 
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = ((time.toSecondOfDay() + (timeEndOfDay ? 1 : 0)) << 15) +
                (month.ordinal() << 11) + ((dom + 32) << 5) +
                ((dow == null ? 7 : dow.ordinal()) << 2) + (timeDefinition.ordinal());
        return hash ^ standardOffset.hashCode() ^
                offsetBefore.hashCode() ^ offsetAfter.hashCode();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a string describing this object.
     *
     * <p>
     *  返回描述此对象的字符串。
     * 
     * 
     * @return a string for debugging, not null
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("TransitionRule[")
            .append(offsetBefore.compareTo(offsetAfter) > 0 ? "Gap " : "Overlap ")
            .append(offsetBefore).append(" to ").append(offsetAfter).append(", ");
        if (dow != null) {
            if (dom == -1) {
                buf.append(dow.name()).append(" on or before last day of ").append(month.name());
            } else if (dom < 0) {
                buf.append(dow.name()).append(" on or before last day minus ").append(-dom - 1).append(" of ").append(month.name());
            } else {
                buf.append(dow.name()).append(" on or after ").append(month.name()).append(' ').append(dom);
            }
        } else {
            buf.append(month.name()).append(' ').append(dom);
        }
        buf.append(" at ").append(timeEndOfDay ? "24:00" : time.toString())
            .append(" ").append(timeDefinition)
            .append(", standard offset ").append(standardOffset)
            .append(']');
        return buf.toString();
    }

    //-----------------------------------------------------------------------
    /**
     * A definition of the way a local time can be converted to the actual
     * transition date-time.
     * <p>
     * Time zone rules are expressed in one of three ways:
     * <ul>
     * <li>Relative to UTC</li>
     * <li>Relative to the standard offset in force</li>
     * <li>Relative to the wall offset (what you would see on a clock on the wall)</li>
     * </ul>
     * <p>
     *  本地时间可以转换为实际转换日期时间的方式的定义。
     * <p>
     *  时区规则以三种方式之一表示：
     * <ul>
     *  <li>相对于UTC </li> <li>相对于标准偏差</li> <li>相对于墙面偏移(您将在墙上看到的时钟)</li>
     * </ul>
     */
    public static enum TimeDefinition {
        /** The local date-time is expressed in terms of the UTC offset. */
        UTC,
        /** The local date-time is expressed in terms of the wall offset. */
        WALL,
        /** The local date-time is expressed in terms of the standard offset. */
        STANDARD;

        /**
         * Converts the specified local date-time to the local date-time actually
         * seen on a wall clock.
         * <p>
         * This method converts using the type of this enum.
         * The output is defined relative to the 'before' offset of the transition.
         * <p>
         * The UTC type uses the UTC offset.
         * The STANDARD type uses the standard offset.
         * The WALL type returns the input date-time.
         * The result is intended for use with the wall-offset.
         *
         * <p>
         *  将指定的本地日期时间转换为实际在挂钟上显示的本地日期时间。
         * <p>
         *  此方法使用此枚举的类型进行转换。相对于转换的"before"偏移量定义输出。
         * 
         * @param dateTime  the local date-time, not null
         * @param standardOffset  the standard offset, not null
         * @param wallOffset  the wall offset, not null
         * @return the date-time relative to the wall/before offset, not null
         */
        public LocalDateTime createDateTime(LocalDateTime dateTime, ZoneOffset standardOffset, ZoneOffset wallOffset) {
            switch (this) {
                case UTC: {
                    int difference = wallOffset.getTotalSeconds() - ZoneOffset.UTC.getTotalSeconds();
                    return dateTime.plusSeconds(difference);
                }
                case STANDARD: {
                    int difference = wallOffset.getTotalSeconds() - standardOffset.getTotalSeconds();
                    return dateTime.plusSeconds(difference);
                }
                default:  // WALL
                    return dateTime;
            }
        }
    }

}
