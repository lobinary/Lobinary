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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A transition between two offsets caused by a discontinuity in the local time-line.
 * <p>
 * A transition between two offsets is normally the result of a daylight savings cutover.
 * The discontinuity is normally a gap in spring and an overlap in autumn.
 * {@code ZoneOffsetTransition} models the transition between the two offsets.
 * <p>
 * Gaps occur where there are local date-times that simply do not not exist.
 * An example would be when the offset changes from {@code +03:00} to {@code +04:00}.
 * This might be described as 'the clocks will move forward one hour tonight at 1am'.
 * <p>
 * Overlaps occur where there are local date-times that exist twice.
 * An example would be when the offset changes from {@code +04:00} to {@code +03:00}.
 * This might be described as 'the clocks will move back one hour tonight at 2am'.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  由本地时间线中的不连续性导致的两个偏移之间的转变。
 * <p>
 *  两个偏移之间的转换通常是夏令时割接的结果。不连续通常是春季的间隙和秋季的重叠。 {@code ZoneOffsetTransition}建模两个偏移之间的过渡。
 * <p>
 *  间隙发生在存在本地日期时间的地方,根本不存在。例如,当偏移从{@code +03：00}更改为{@code +04：00}时。这可能被描述为"时钟将在今晚1时前进1小时。
 * <p>
 * 重叠发生在存在两次的本地日期时间的地方。例如,当偏移从{@code +04：00}更改为{@code +03：00}时。这可能被描述为"时钟将在今天晚上2点移动一小时"。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class ZoneOffsetTransition
        implements Comparable<ZoneOffsetTransition>, Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = -6946044323557704546L;
    /**
     * The local transition date-time at the transition.
     * <p>
     *  转换时的本地转换日期时间。
     * 
     */
    private final LocalDateTime transition;
    /**
     * The offset before transition.
     * <p>
     *  转换前的偏移量。
     * 
     */
    private final ZoneOffset offsetBefore;
    /**
     * The offset after transition.
     * <p>
     *  转换后的偏移量。
     * 
     */
    private final ZoneOffset offsetAfter;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance defining a transition between two offsets.
     * <p>
     * Applications should normally obtain an instance from {@link ZoneRules}.
     * This factory is only intended for use when creating {@link ZoneRules}.
     *
     * <p>
     *  获取定义两个偏移之间的转换的实例。
     * <p>
     *  应用程序通常应从{@link ZoneRules}获取一个实例。此工厂仅用于创建{@link ZoneRules}时使用。
     * 
     * 
     * @param transition  the transition date-time at the transition, which never
     *  actually occurs, expressed local to the before offset, not null
     * @param offsetBefore  the offset before the transition, not null
     * @param offsetAfter  the offset at and after the transition, not null
     * @return the transition, not null
     * @throws IllegalArgumentException if {@code offsetBefore} and {@code offsetAfter}
     *         are equal, or {@code transition.getNano()} returns non-zero value
     */
    public static ZoneOffsetTransition of(LocalDateTime transition, ZoneOffset offsetBefore, ZoneOffset offsetAfter) {
        Objects.requireNonNull(transition, "transition");
        Objects.requireNonNull(offsetBefore, "offsetBefore");
        Objects.requireNonNull(offsetAfter, "offsetAfter");
        if (offsetBefore.equals(offsetAfter)) {
            throw new IllegalArgumentException("Offsets must not be equal");
        }
        if (transition.getNano() != 0) {
            throw new IllegalArgumentException("Nano-of-second must be zero");
        }
        return new ZoneOffsetTransition(transition, offsetBefore, offsetAfter);
    }

    /**
     * Creates an instance defining a transition between two offsets.
     *
     * <p>
     *  创建定义两个偏移之间的转换的实例。
     * 
     * 
     * @param transition  the transition date-time with the offset before the transition, not null
     * @param offsetBefore  the offset before the transition, not null
     * @param offsetAfter  the offset at and after the transition, not null
     */
    ZoneOffsetTransition(LocalDateTime transition, ZoneOffset offsetBefore, ZoneOffset offsetAfter) {
        this.transition = transition;
        this.offsetBefore = offsetBefore;
        this.offsetAfter = offsetAfter;
    }

    /**
     * Creates an instance from epoch-second and offsets.
     *
     * <p>
     *  从时代秒和偏移量创建实例。
     * 
     * 
     * @param epochSecond  the transition epoch-second
     * @param offsetBefore  the offset before the transition, not null
     * @param offsetAfter  the offset at and after the transition, not null
     */
    ZoneOffsetTransition(long epochSecond, ZoneOffset offsetBefore, ZoneOffset offsetAfter) {
        this.transition = LocalDateTime.ofEpochSecond(epochSecond, 0, offsetBefore);
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
     *   out.writeByte(2);                // identifies a ZoneOffsetTransition
     *   out.writeEpochSec(toEpochSecond);
     *   out.writeOffset(offsetBefore);
     *   out.writeOffset(offsetAfter);
     * }
     * </pre>
     * @return the replacing object, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.ZOT, this);
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
        Ser.writeEpochSec(toEpochSecond(), out);
        Ser.writeOffset(offsetBefore, out);
        Ser.writeOffset(offsetAfter, out);
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
    static ZoneOffsetTransition readExternal(DataInput in) throws IOException {
        long epochSecond = Ser.readEpochSec(in);
        ZoneOffset before = Ser.readOffset(in);
        ZoneOffset after = Ser.readOffset(in);
        if (before.equals(after)) {
            throw new IllegalArgumentException("Offsets must not be equal");
        }
        return new ZoneOffsetTransition(epochSecond, before, after);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the transition instant.
     * <p>
     * This is the instant of the discontinuity, which is defined as the first
     * instant that the 'after' offset applies.
     * <p>
     * The methods {@link #getInstant()}, {@link #getDateTimeBefore()} and {@link #getDateTimeAfter()}
     * all represent the same instant.
     *
     * <p>
     *  获取转换时刻。
     * <p>
     *  这是不连续的瞬时,其被定义为"后"偏移应用的第一时刻。
     * <p>
     *  方法{@link #getInstant()},{@link #getDateTimeBefore()}和{@link #getDateTimeAfter()}都代表同一个时刻。
     * 
     * 
     * @return the transition instant, not null
     */
    public Instant getInstant() {
        return transition.toInstant(offsetBefore);
    }

    /**
     * Gets the transition instant as an epoch second.
     *
     * <p>
     *  获得过渡瞬间作为一个时代的第二。
     * 
     * 
     * @return the transition epoch second
     */
    public long toEpochSecond() {
        return transition.toEpochSecond(offsetBefore);
    }

    //-------------------------------------------------------------------------
    /**
     * Gets the local transition date-time, as would be expressed with the 'before' offset.
     * <p>
     * This is the date-time where the discontinuity begins expressed with the 'before' offset.
     * At this instant, the 'after' offset is actually used, therefore the combination of this
     * date-time and the 'before' offset will never occur.
     * <p>
     * The combination of the 'before' date-time and offset represents the same instant
     * as the 'after' date-time and offset.
     *
     * <p>
     *  获取本地过渡日期时间,将使用"before"偏移量表示。
     * <p>
     * 这是不连续开始用"之前"偏移表示的日期时间。在这个时刻,实际使用"后"偏移,因此这个日期时间和"之前"偏移的组合永远不会发生。
     * <p>
     *  "before"日期时间和偏移量的组合表示与"after"日期时间和偏移量相同的时刻。
     * 
     * 
     * @return the transition date-time expressed with the before offset, not null
     */
    public LocalDateTime getDateTimeBefore() {
        return transition;
    }

    /**
     * Gets the local transition date-time, as would be expressed with the 'after' offset.
     * <p>
     * This is the first date-time after the discontinuity, when the new offset applies.
     * <p>
     * The combination of the 'before' date-time and offset represents the same instant
     * as the 'after' date-time and offset.
     *
     * <p>
     *  获取本地过渡日期时间,将用"after"偏移量表示。
     * <p>
     *  这是不连续之后的第一个日期 - 时间,当应用新的偏移时。
     * <p>
     *  "before"日期时间和偏移量的组合表示与"after"日期时间和偏移量相同的时刻。
     * 
     * 
     * @return the transition date-time expressed with the after offset, not null
     */
    public LocalDateTime getDateTimeAfter() {
        return transition.plusSeconds(getDurationSeconds());
    }

    /**
     * Gets the offset before the transition.
     * <p>
     * This is the offset in use before the instant of the transition.
     *
     * <p>
     *  获取转换前的偏移量。
     * <p>
     *  这是在转换时刻之前使用的偏移量。
     * 
     * 
     * @return the offset before the transition, not null
     */
    public ZoneOffset getOffsetBefore() {
        return offsetBefore;
    }

    /**
     * Gets the offset after the transition.
     * <p>
     * This is the offset in use on and after the instant of the transition.
     *
     * <p>
     *  获取转换后的偏移量。
     * <p>
     *  这是在转换时刻上及之后使用的偏移量。
     * 
     * 
     * @return the offset after the transition, not null
     */
    public ZoneOffset getOffsetAfter() {
        return offsetAfter;
    }

    /**
     * Gets the duration of the transition.
     * <p>
     * In most cases, the transition duration is one hour, however this is not always the case.
     * The duration will be positive for a gap and negative for an overlap.
     * Time-zones are second-based, so the nanosecond part of the duration will be zero.
     *
     * <p>
     *  获取转换的持续时间。
     * <p>
     *  在大多数情况下,过渡持续时间是一小时,然而这并不总是这样。持续时间对于间隙是正的,对于重叠是负的。时区是基于秒的,因此持续时间的纳秒部分将为零。
     * 
     * 
     * @return the duration of the transition, positive for gaps, negative for overlaps
     */
    public Duration getDuration() {
        return Duration.ofSeconds(getDurationSeconds());
    }

    /**
     * Gets the duration of the transition in seconds.
     *
     * <p>
     *  获取转换的持续时间(以秒为单位)。
     * 
     * 
     * @return the duration in seconds
     */
    private int getDurationSeconds() {
        return getOffsetAfter().getTotalSeconds() - getOffsetBefore().getTotalSeconds();
    }

    /**
     * Does this transition represent a gap in the local time-line.
     * <p>
     * Gaps occur where there are local date-times that simply do not not exist.
     * An example would be when the offset changes from {@code +01:00} to {@code +02:00}.
     * This might be described as 'the clocks will move forward one hour tonight at 1am'.
     *
     * <p>
     *  该转换是否表示本地时间线中的间隙。
     * <p>
     *  间隙发生在存在本地日期时间的地方,根本不存在。例如,当偏移从{@code +01：00}更改为{@code +02：00}时。这可能被描述为"时钟将在今晚1时前进1小时。
     * 
     * 
     * @return true if this transition is a gap, false if it is an overlap
     */
    public boolean isGap() {
        return getOffsetAfter().getTotalSeconds() > getOffsetBefore().getTotalSeconds();
    }

    /**
     * Does this transition represent an overlap in the local time-line.
     * <p>
     * Overlaps occur where there are local date-times that exist twice.
     * An example would be when the offset changes from {@code +02:00} to {@code +01:00}.
     * This might be described as 'the clocks will move back one hour tonight at 2am'.
     *
     * <p>
     * 此转换是否表示本地时间线中的重叠。
     * <p>
     *  重叠发生在存在两次的本地日期时间的地方。例如,当偏移从{@code +02：00}更改为{@code +01：00}时。这可能被描述为"时钟将在今天晚上2点移动一小时"。
     * 
     * 
     * @return true if this transition is an overlap, false if it is a gap
     */
    public boolean isOverlap() {
        return getOffsetAfter().getTotalSeconds() < getOffsetBefore().getTotalSeconds();
    }

    /**
     * Checks if the specified offset is valid during this transition.
     * <p>
     * This checks to see if the given offset will be valid at some point in the transition.
     * A gap will always return false.
     * An overlap will return true if the offset is either the before or after offset.
     *
     * <p>
     *  在此转换期间检查指定的偏移量是否有效。
     * <p>
     *  这将检查给定的偏移在转换中的某个点是否有效。间隙将总是返回false。如果偏移是偏移之前或之后,重叠将返回true。
     * 
     * 
     * @param offset  the offset to check, null returns false
     * @return true if the offset is valid during the transition
     */
    public boolean isValidOffset(ZoneOffset offset) {
        return isGap() ? false : (getOffsetBefore().equals(offset) || getOffsetAfter().equals(offset));
    }

    /**
     * Gets the valid offsets during this transition.
     * <p>
     * A gap will return an empty list, while an overlap will return both offsets.
     *
     * <p>
     *  在此转换期间获取有效偏移量。
     * <p>
     *  间隙将返回一个空列表,而重叠将返回两个偏移。
     * 
     * 
     * @return the list of valid offsets
     */
    List<ZoneOffset> getValidOffsets() {
        if (isGap()) {
            return Collections.emptyList();
        }
        return Arrays.asList(getOffsetBefore(), getOffsetAfter());
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this transition to another based on the transition instant.
     * <p>
     * This compares the instants of each transition.
     * The offsets are ignored, making this order inconsistent with equals.
     *
     * <p>
     *  基于过渡时刻将此转换与另一个进行比较。
     * <p>
     *  这比较了每个转换的时刻。将忽略偏移,使此顺序与等号不一致。
     * 
     * 
     * @param transition  the transition to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     */
    @Override
    public int compareTo(ZoneOffsetTransition transition) {
        return this.getInstant().compareTo(transition.getInstant());
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
     * @param other  the other object to compare to, null returns false
     * @return true if equal
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof ZoneOffsetTransition) {
            ZoneOffsetTransition d = (ZoneOffsetTransition) other;
            return transition.equals(d.transition) &&
                offsetBefore.equals(d.offsetBefore) && offsetAfter.equals(d.offsetAfter);
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
        return transition.hashCode() ^ offsetBefore.hashCode() ^ Integer.rotateLeft(offsetAfter.hashCode(), 16);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a string describing this object.
     *
     * <p>
     *  返回描述此对象的字符串。
     * 
     * @return a string for debugging, not null
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Transition[")
            .append(isGap() ? "Gap" : "Overlap")
            .append(" at ")
            .append(transition)
            .append(offsetBefore)
            .append(" to ")
            .append(offsetAfter)
            .append(']');
        return buf.toString();
    }

}
