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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The rules defining how the zone offset varies for a single time-zone.
 * <p>
 * The rules model all the historic and future transitions for a time-zone.
 * {@link ZoneOffsetTransition} is used for known transitions, typically historic.
 * {@link ZoneOffsetTransitionRule} is used for future transitions that are based
 * on the result of an algorithm.
 * <p>
 * The rules are loaded via {@link ZoneRulesProvider} using a {@link ZoneId}.
 * The same rules may be shared internally between multiple zone IDs.
 * <p>
 * Serializing an instance of {@code ZoneRules} will store the entire set of rules.
 * It does not store the zone ID as it is not part of the state of this object.
 * <p>
 * A rule implementation may or may not store full information about historic
 * and future transitions, and the information stored is only as accurate as
 * that supplied to the implementation by the rules provider.
 * Applications should treat the data provided as representing the best information
 * available to the implementation of this rule.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  定义区域偏移对于单个时区如何变化的规则。
 * <p>
 *  规则模拟时区的所有历史和未来转换。 {@link ZoneOffsetTransition}用于已知的转换,通常是历史的。
 *  {@link ZoneOffsetTransitionRule}用于基于算法结果的未来转换。
 * <p>
 *  规则通过{@link ZoneRulesProvider}使用{@link ZoneId}加载。相同的规则可以在多个区域ID之间内部共享。
 * <p>
 *  序列化{@code ZoneRules}的实例将存储整个规则集。它不存储区域ID,因为它不是此对象的状态的一部分。
 * <p>
 * 规则实现可以或可以不存储关于历史和未来转换的完整信息,并且所存储的信息仅与由规则提供者提供给实现的信息一样精确。应用程序应将提供的数据视为实施本规则可用的最佳信息。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class ZoneRules implements Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = 3044319355680032515L;
    /**
     * The last year to have its transitions cached.
     * <p>
     *  最后一年缓存其转换。
     * 
     */
    private static final int LAST_CACHED_YEAR = 2100;

    /**
     * The transitions between standard offsets (epoch seconds), sorted.
     * <p>
     *  标准偏移(epoch秒)之间的转换,排序。
     * 
     */
    private final long[] standardTransitions;
    /**
     * The standard offsets.
     * <p>
     *  标准偏移。
     * 
     */
    private final ZoneOffset[] standardOffsets;
    /**
     * The transitions between instants (epoch seconds), sorted.
     * <p>
     *  时刻之间的转换(时代秒),排序。
     * 
     */
    private final long[] savingsInstantTransitions;
    /**
     * The transitions between local date-times, sorted.
     * This is a paired array, where the first entry is the start of the transition
     * and the second entry is the end of the transition.
     * <p>
     *  本地日期时间之间的过渡,排序。这是一个配对数组,其中第一个条目是转换的开始,第二个条目是转换的结束。
     * 
     */
    private final LocalDateTime[] savingsLocalTransitions;
    /**
     * The wall offsets.
     * <p>
     *  墙壁偏移。
     * 
     */
    private final ZoneOffset[] wallOffsets;
    /**
     * The last rule.
     * <p>
     *  最后一条规则。
     * 
     */
    private final ZoneOffsetTransitionRule[] lastRules;
    /**
     * The map of recent transitions.
     * <p>
     *  最近转换的地图。
     * 
     */
    private final transient ConcurrentMap<Integer, ZoneOffsetTransition[]> lastRulesCache =
                new ConcurrentHashMap<Integer, ZoneOffsetTransition[]>();
    /**
     * The zero-length long array.
     * <p>
     *  零长度长阵列。
     * 
     */
    private static final long[] EMPTY_LONG_ARRAY = new long[0];
    /**
     * The zero-length lastrules array.
     * <p>
     *  零长度的lastrules数组。
     * 
     */
    private static final ZoneOffsetTransitionRule[] EMPTY_LASTRULES =
        new ZoneOffsetTransitionRule[0];
    /**
     * The zero-length ldt array.
     * <p>
     *  零长度ldt数组。
     * 
     */
    private static final LocalDateTime[] EMPTY_LDT_ARRAY = new LocalDateTime[0];

    /**
     * Obtains an instance of a ZoneRules.
     *
     * <p>
     *  获取ZoneRules的实例。
     * 
     * 
     * @param baseStandardOffset  the standard offset to use before legal rules were set, not null
     * @param baseWallOffset  the wall offset to use before legal rules were set, not null
     * @param standardOffsetTransitionList  the list of changes to the standard offset, not null
     * @param transitionList  the list of transitions, not null
     * @param lastRules  the recurring last rules, size 16 or less, not null
     * @return the zone rules, not null
     */
    public static ZoneRules of(ZoneOffset baseStandardOffset,
                               ZoneOffset baseWallOffset,
                               List<ZoneOffsetTransition> standardOffsetTransitionList,
                               List<ZoneOffsetTransition> transitionList,
                               List<ZoneOffsetTransitionRule> lastRules) {
        Objects.requireNonNull(baseStandardOffset, "baseStandardOffset");
        Objects.requireNonNull(baseWallOffset, "baseWallOffset");
        Objects.requireNonNull(standardOffsetTransitionList, "standardOffsetTransitionList");
        Objects.requireNonNull(transitionList, "transitionList");
        Objects.requireNonNull(lastRules, "lastRules");
        return new ZoneRules(baseStandardOffset, baseWallOffset,
                             standardOffsetTransitionList, transitionList, lastRules);
    }

    /**
     * Obtains an instance of ZoneRules that has fixed zone rules.
     *
     * <p>
     *  获取具有固定区域规则的ZoneRules的实例。
     * 
     * 
     * @param offset  the offset this fixed zone rules is based on, not null
     * @return the zone rules, not null
     * @see #isFixedOffset()
     */
    public static ZoneRules of(ZoneOffset offset) {
        Objects.requireNonNull(offset, "offset");
        return new ZoneRules(offset);
    }

    /**
     * Creates an instance.
     *
     * <p>
     *  创建实例。
     * 
     * 
     * @param baseStandardOffset  the standard offset to use before legal rules were set, not null
     * @param baseWallOffset  the wall offset to use before legal rules were set, not null
     * @param standardOffsetTransitionList  the list of changes to the standard offset, not null
     * @param transitionList  the list of transitions, not null
     * @param lastRules  the recurring last rules, size 16 or less, not null
     */
    ZoneRules(ZoneOffset baseStandardOffset,
              ZoneOffset baseWallOffset,
              List<ZoneOffsetTransition> standardOffsetTransitionList,
              List<ZoneOffsetTransition> transitionList,
              List<ZoneOffsetTransitionRule> lastRules) {
        super();

        // convert standard transitions

        this.standardTransitions = new long[standardOffsetTransitionList.size()];

        this.standardOffsets = new ZoneOffset[standardOffsetTransitionList.size() + 1];
        this.standardOffsets[0] = baseStandardOffset;
        for (int i = 0; i < standardOffsetTransitionList.size(); i++) {
            this.standardTransitions[i] = standardOffsetTransitionList.get(i).toEpochSecond();
            this.standardOffsets[i + 1] = standardOffsetTransitionList.get(i).getOffsetAfter();
        }

        // convert savings transitions to locals
        List<LocalDateTime> localTransitionList = new ArrayList<>();
        List<ZoneOffset> localTransitionOffsetList = new ArrayList<>();
        localTransitionOffsetList.add(baseWallOffset);
        for (ZoneOffsetTransition trans : transitionList) {
            if (trans.isGap()) {
                localTransitionList.add(trans.getDateTimeBefore());
                localTransitionList.add(trans.getDateTimeAfter());
            } else {
                localTransitionList.add(trans.getDateTimeAfter());
                localTransitionList.add(trans.getDateTimeBefore());
            }
            localTransitionOffsetList.add(trans.getOffsetAfter());
        }
        this.savingsLocalTransitions = localTransitionList.toArray(new LocalDateTime[localTransitionList.size()]);
        this.wallOffsets = localTransitionOffsetList.toArray(new ZoneOffset[localTransitionOffsetList.size()]);

        // convert savings transitions to instants
        this.savingsInstantTransitions = new long[transitionList.size()];
        for (int i = 0; i < transitionList.size(); i++) {
            this.savingsInstantTransitions[i] = transitionList.get(i).toEpochSecond();
        }

        // last rules
        if (lastRules.size() > 16) {
            throw new IllegalArgumentException("Too many transition rules");
        }
        this.lastRules = lastRules.toArray(new ZoneOffsetTransitionRule[lastRules.size()]);
    }

    /**
     * Constructor.
     *
     * <p>
     *  构造函数。
     * 
     * 
     * @param standardTransitions  the standard transitions, not null
     * @param standardOffsets  the standard offsets, not null
     * @param savingsInstantTransitions  the standard transitions, not null
     * @param wallOffsets  the wall offsets, not null
     * @param lastRules  the recurring last rules, size 15 or less, not null
     */
    private ZoneRules(long[] standardTransitions,
                      ZoneOffset[] standardOffsets,
                      long[] savingsInstantTransitions,
                      ZoneOffset[] wallOffsets,
                      ZoneOffsetTransitionRule[] lastRules) {
        super();

        this.standardTransitions = standardTransitions;
        this.standardOffsets = standardOffsets;
        this.savingsInstantTransitions = savingsInstantTransitions;
        this.wallOffsets = wallOffsets;
        this.lastRules = lastRules;

        if (savingsInstantTransitions.length == 0) {
            this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
        } else {
            // convert savings transitions to locals
            List<LocalDateTime> localTransitionList = new ArrayList<>();
            for (int i = 0; i < savingsInstantTransitions.length; i++) {
                ZoneOffset before = wallOffsets[i];
                ZoneOffset after = wallOffsets[i + 1];
                ZoneOffsetTransition trans = new ZoneOffsetTransition(savingsInstantTransitions[i], before, after);
                if (trans.isGap()) {
                    localTransitionList.add(trans.getDateTimeBefore());
                    localTransitionList.add(trans.getDateTimeAfter());
                } else {
                    localTransitionList.add(trans.getDateTimeAfter());
                    localTransitionList.add(trans.getDateTimeBefore());
               }
            }
            this.savingsLocalTransitions = localTransitionList.toArray(new LocalDateTime[localTransitionList.size()]);
        }
    }

    /**
     * Creates an instance of ZoneRules that has fixed zone rules.
     *
     * <p>
     *  创建具有固定区域规则的ZoneRules的实例。
     * 
     * 
     * @param offset  the offset this fixed zone rules is based on, not null
     * @return the zone rules, not null
     * @see #isFixedOffset()
     */
    private ZoneRules(ZoneOffset offset) {
        this.standardOffsets = new ZoneOffset[1];
        this.standardOffsets[0] = offset;
        this.standardTransitions = EMPTY_LONG_ARRAY;
        this.savingsInstantTransitions = EMPTY_LONG_ARRAY;
        this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
        this.wallOffsets = standardOffsets;
        this.lastRules = EMPTY_LASTRULES;
    }

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
     * <pre style="font-size:1.0em">{@code
     *
     *   out.writeByte(1);  // identifies a ZoneRules
     *   out.writeInt(standardTransitions.length);
     *   for (long trans : standardTransitions) {
     *       Ser.writeEpochSec(trans, out);
     *   }
     *   for (ZoneOffset offset : standardOffsets) {
     *       Ser.writeOffset(offset, out);
     *   }
     *   out.writeInt(savingsInstantTransitions.length);
     *   for (long trans : savingsInstantTransitions) {
     *       Ser.writeEpochSec(trans, out);
     *   }
     *   for (ZoneOffset offset : wallOffsets) {
     *       Ser.writeOffset(offset, out);
     *   }
     *   out.writeByte(lastRules.length);
     *   for (ZoneOffsetTransitionRule rule : lastRules) {
     *       rule.writeExternal(out);
     *   }
     * }
     * </pre>
     * <p>
     * Epoch second values used for offsets are encoded in a variable
     * length form to make the common cases put fewer bytes in the stream.
     * <pre style="font-size:1.0em">{@code
     *
     *  static void writeEpochSec(long epochSec, DataOutput out) throws IOException {
     *     if (epochSec >= -4575744000L && epochSec < 10413792000L && epochSec % 900 == 0) {  // quarter hours between 1825 and 2300
     *         int store = (int) ((epochSec + 4575744000L) / 900);
     *         out.writeByte((store >>> 16) & 255);
     *         out.writeByte((store >>> 8) & 255);
     *         out.writeByte(store & 255);
     *      } else {
     *          out.writeByte(255);
     *          out.writeLong(epochSec);
     *      }
     *  }
     * }
     * </pre>
     * <p>
     * ZoneOffset values are encoded in a variable length form so the
     * common cases put fewer bytes in the stream.
     * <pre style="font-size:1.0em">{@code
     *
     *  static void writeOffset(ZoneOffset offset, DataOutput out) throws IOException {
     *     final int offsetSecs = offset.getTotalSeconds();
     *     int offsetByte = offsetSecs % 900 == 0 ? offsetSecs / 900 : 127;  // compress to -72 to +72
     *     out.writeByte(offsetByte);
     *     if (offsetByte == 127) {
     *         out.writeInt(offsetSecs);
     *     }
     * }
     *}
     * </pre>
     * @return the replacing object, not null
     */
    private Object writeReplace() {
        return new Ser(Ser.ZRULES, this);
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
        out.writeInt(standardTransitions.length);
        for (long trans : standardTransitions) {
            Ser.writeEpochSec(trans, out);
        }
        for (ZoneOffset offset : standardOffsets) {
            Ser.writeOffset(offset, out);
        }
        out.writeInt(savingsInstantTransitions.length);
        for (long trans : savingsInstantTransitions) {
            Ser.writeEpochSec(trans, out);
        }
        for (ZoneOffset offset : wallOffsets) {
            Ser.writeOffset(offset, out);
        }
        out.writeByte(lastRules.length);
        for (ZoneOffsetTransitionRule rule : lastRules) {
            rule.writeExternal(out);
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
    static ZoneRules readExternal(DataInput in) throws IOException, ClassNotFoundException {
        int stdSize = in.readInt();
        long[] stdTrans = (stdSize == 0) ? EMPTY_LONG_ARRAY
                                         : new long[stdSize];
        for (int i = 0; i < stdSize; i++) {
            stdTrans[i] = Ser.readEpochSec(in);
        }
        ZoneOffset[] stdOffsets = new ZoneOffset[stdSize + 1];
        for (int i = 0; i < stdOffsets.length; i++) {
            stdOffsets[i] = Ser.readOffset(in);
        }
        int savSize = in.readInt();
        long[] savTrans = (savSize == 0) ? EMPTY_LONG_ARRAY
                                         : new long[savSize];
        for (int i = 0; i < savSize; i++) {
            savTrans[i] = Ser.readEpochSec(in);
        }
        ZoneOffset[] savOffsets = new ZoneOffset[savSize + 1];
        for (int i = 0; i < savOffsets.length; i++) {
            savOffsets[i] = Ser.readOffset(in);
        }
        int ruleSize = in.readByte();
        ZoneOffsetTransitionRule[] rules = (ruleSize == 0) ?
            EMPTY_LASTRULES : new ZoneOffsetTransitionRule[ruleSize];
        for (int i = 0; i < ruleSize; i++) {
            rules[i] = ZoneOffsetTransitionRule.readExternal(in);
        }
        return new ZoneRules(stdTrans, stdOffsets, savTrans, savOffsets, rules);
    }

    /**
     * Checks of the zone rules are fixed, such that the offset never varies.
     *
     * <p>
     *  区域规则的检查是固定的,使得偏移从不改变。
     * 
     * 
     * @return true if the time-zone is fixed and the offset never changes
     */
    public boolean isFixedOffset() {
        return savingsInstantTransitions.length == 0;
    }

    /**
     * Gets the offset applicable at the specified instant in these rules.
     * <p>
     * The mapping from an instant to an offset is simple, there is only
     * one valid offset for each instant.
     * This method returns that offset.
     *
     * <p>
     *  获取这些规则中指定时刻适用的偏移量。
     * <p>
     * 从瞬时到偏移的映射是简单的,每个时刻只有一个有效偏移。此方法返回该偏移量。
     * 
     * 
     * @param instant  the instant to find the offset for, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the offset, not null
     */
    public ZoneOffset getOffset(Instant instant) {
        if (savingsInstantTransitions.length == 0) {
            return standardOffsets[0];
        }
        long epochSec = instant.getEpochSecond();
        // check if using last rules
        if (lastRules.length > 0 &&
                epochSec > savingsInstantTransitions[savingsInstantTransitions.length - 1]) {
            int year = findYear(epochSec, wallOffsets[wallOffsets.length - 1]);
            ZoneOffsetTransition[] transArray = findTransitionArray(year);
            ZoneOffsetTransition trans = null;
            for (int i = 0; i < transArray.length; i++) {
                trans = transArray[i];
                if (epochSec < trans.toEpochSecond()) {
                    return trans.getOffsetBefore();
                }
            }
            return trans.getOffsetAfter();
        }

        // using historic rules
        int index  = Arrays.binarySearch(savingsInstantTransitions, epochSec);
        if (index < 0) {
            // switch negative insert position to start of matched range
            index = -index - 2;
        }
        return wallOffsets[index + 1];
    }

    /**
     * Gets a suitable offset for the specified local date-time in these rules.
     * <p>
     * The mapping from a local date-time to an offset is not straightforward.
     * There are three cases:
     * <ul>
     * <li>Normal, with one valid offset. For the vast majority of the year, the normal
     *  case applies, where there is a single valid offset for the local date-time.</li>
     * <li>Gap, with zero valid offsets. This is when clocks jump forward typically
     *  due to the spring daylight savings change from "winter" to "summer".
     *  In a gap there are local date-time values with no valid offset.</li>
     * <li>Overlap, with two valid offsets. This is when clocks are set back typically
     *  due to the autumn daylight savings change from "summer" to "winter".
     *  In an overlap there are local date-time values with two valid offsets.</li>
     * </ul>
     * Thus, for any given local date-time there can be zero, one or two valid offsets.
     * This method returns the single offset in the Normal case, and in the Gap or Overlap
     * case it returns the offset before the transition.
     * <p>
     * Since, in the case of Gap and Overlap, the offset returned is a "best" value, rather
     * than the "correct" value, it should be treated with care. Applications that care
     * about the correct offset should use a combination of this method,
     * {@link #getValidOffsets(LocalDateTime)} and {@link #getTransition(LocalDateTime)}.
     *
     * <p>
     *  在这些规则中为指定的本地日期时间获取适当的偏移量。
     * <p>
     *  从本地日期时间到偏移的映射不是简单的。有三种情况：
     * <ul>
     *  <li>正常,具有一个有效偏移量。对于绝大多数年份,正常情况适用,其中本地日期时间存在单个有效偏移量。</li> <li> Gap,零有效偏移量。
     * 这是当时钟向前跳跃通常由于春天夏令时从"冬天"改变为"夏天"。在间隙中存在没有有效偏移的本地日期时间值。</li> <li>重叠,具有两个有效偏移量。
     * 这是当时钟被放回,通常由于秋季夏令从"夏季"改变为"冬季"。在重叠中,存在具有两个有效偏移量的本地日期时间值。</li>。
     * </ul>
     *  因此,对于任何给定的本地日期时间,可以存在零个,一个或两个有效偏移量。此方法在Normal情况下返回单个偏移量,在Gap或Overlap情况下,它返回转移前的偏移量。
     * <p>
     *  因为在Gap和重叠的情况下,返回的偏移量是"最佳"值,而不是"正确"值,所以应该小心对待。
     * 关心正确偏移的应用程序应结合使用此方法{@link #getValidOffsets(LocalDateTime)}和{@link #getTransition(LocalDateTime)}。
     * 
     * 
     * @param localDateTime  the local date-time to query, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the best available offset for the local date-time, not null
     */
    public ZoneOffset getOffset(LocalDateTime localDateTime) {
        Object info = getOffsetInfo(localDateTime);
        if (info instanceof ZoneOffsetTransition) {
            return ((ZoneOffsetTransition) info).getOffsetBefore();
        }
        return (ZoneOffset) info;
    }

    /**
     * Gets the offset applicable at the specified local date-time in these rules.
     * <p>
     * The mapping from a local date-time to an offset is not straightforward.
     * There are three cases:
     * <ul>
     * <li>Normal, with one valid offset. For the vast majority of the year, the normal
     *  case applies, where there is a single valid offset for the local date-time.</li>
     * <li>Gap, with zero valid offsets. This is when clocks jump forward typically
     *  due to the spring daylight savings change from "winter" to "summer".
     *  In a gap there are local date-time values with no valid offset.</li>
     * <li>Overlap, with two valid offsets. This is when clocks are set back typically
     *  due to the autumn daylight savings change from "summer" to "winter".
     *  In an overlap there are local date-time values with two valid offsets.</li>
     * </ul>
     * Thus, for any given local date-time there can be zero, one or two valid offsets.
     * This method returns that list of valid offsets, which is a list of size 0, 1 or 2.
     * In the case where there are two offsets, the earlier offset is returned at index 0
     * and the later offset at index 1.
     * <p>
     * There are various ways to handle the conversion from a {@code LocalDateTime}.
     * One technique, using this method, would be:
     * <pre>
     *  List&lt;ZoneOffset&gt; validOffsets = rules.getOffset(localDT);
     *  if (validOffsets.size() == 1) {
     *    // Normal case: only one valid offset
     *    zoneOffset = validOffsets.get(0);
     *  } else {
     *    // Gap or Overlap: determine what to do from transition (which will be non-null)
     *    ZoneOffsetTransition trans = rules.getTransition(localDT);
     *  }
     * </pre>
     * <p>
     * In theory, it is possible for there to be more than two valid offsets.
     * This would happen if clocks to be put back more than once in quick succession.
     * This has never happened in the history of time-zones and thus has no special handling.
     * However, if it were to happen, then the list would return more than 2 entries.
     *
     * <p>
     * 获取这些规则中指定的本地日期时间适用的偏移量。
     * <p>
     *  从本地日期时间到偏移的映射不是简单的。有三种情况：
     * <ul>
     *  <li>正常,具有一个有效偏移量。对于绝大多数年份,正常情况适用,其中本地日期时间存在单个有效偏移量。</li> <li> Gap,零有效偏移量。
     * 这是当时钟向前跳跃通常由于春天夏令时从"冬天"改变为"夏天"。在间隙中存在没有有效偏移的本地日期时间值。</li> <li>重叠,具有两个有效偏移量。
     * 这是当时钟被放回,通常由于秋季夏令从"夏季"改变为"冬季"。在重叠中,存在具有两个有效偏移量的本地日期时间值。</li>。
     * </ul>
     *  因此,对于任何给定的本地日期时间,可以存在零个,一个或两个有效偏移量。此方法返回有效偏移量的列表,其是大小为0,1或2的列表。
     * 在存在两个偏移量的情况下,较早的偏移量在索引0处返回,而较晚的偏移量在索引1处返回。
     * <p>
     *  有多种方法来处理从{@code LocalDateTime}的转换。一种技术,使用这种方法,将是：
     * <pre>
     *  List&lt; ZoneOffset&gt; validOffsets = rules.getOffset(localDT); if(validOffsets.size()== 1){//正常情况：只有一个有效的偏移zoneOffset = validOffsets.get(0); }
     *  else {// Gap or Overlap：确定从transition中做什么(这将是非null)ZoneOffsetTransition trans = rules.getTransition(localDT); }
     * }。
     * </pre>
     * <p>
     * 在理论上,有可能有两个以上的有效偏移。如果时钟快速连续被多次回放,就会发生这种情况。这从来没有发生在时区的历史,因此没有特殊的处理。然而,如果它发生,那么列表将返回多于2个条目。
     * 
     * 
     * @param localDateTime  the local date-time to query for valid offsets, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the list of valid offsets, may be immutable, not null
     */
    public List<ZoneOffset> getValidOffsets(LocalDateTime localDateTime) {
        // should probably be optimized
        Object info = getOffsetInfo(localDateTime);
        if (info instanceof ZoneOffsetTransition) {
            return ((ZoneOffsetTransition) info).getValidOffsets();
        }
        return Collections.singletonList((ZoneOffset) info);
    }

    /**
     * Gets the offset transition applicable at the specified local date-time in these rules.
     * <p>
     * The mapping from a local date-time to an offset is not straightforward.
     * There are three cases:
     * <ul>
     * <li>Normal, with one valid offset. For the vast majority of the year, the normal
     *  case applies, where there is a single valid offset for the local date-time.</li>
     * <li>Gap, with zero valid offsets. This is when clocks jump forward typically
     *  due to the spring daylight savings change from "winter" to "summer".
     *  In a gap there are local date-time values with no valid offset.</li>
     * <li>Overlap, with two valid offsets. This is when clocks are set back typically
     *  due to the autumn daylight savings change from "summer" to "winter".
     *  In an overlap there are local date-time values with two valid offsets.</li>
     * </ul>
     * A transition is used to model the cases of a Gap or Overlap.
     * The Normal case will return null.
     * <p>
     * There are various ways to handle the conversion from a {@code LocalDateTime}.
     * One technique, using this method, would be:
     * <pre>
     *  ZoneOffsetTransition trans = rules.getTransition(localDT);
     *  if (trans == null) {
     *    // Gap or Overlap: determine what to do from transition
     *  } else {
     *    // Normal case: only one valid offset
     *    zoneOffset = rule.getOffset(localDT);
     *  }
     * </pre>
     *
     * <p>
     *  获取在这些规则中指定的本地日期时间适用的偏移转换。
     * <p>
     *  从本地日期时间到偏移的映射不是简单的。有三种情况：
     * <ul>
     *  <li>正常,具有一个有效偏移量。对于绝大多数年份,正常情况适用,其中本地日期时间存在单个有效偏移量。</li> <li> Gap,零有效偏移量。
     * 这是当时钟向前跳跃通常由于春天夏令时从"冬天"改变为"夏天"。在间隙中存在没有有效偏移的本地日期时间值。</li> <li>重叠,具有两个有效偏移量。
     * 这是当时钟被放回,通常由于秋季夏令从"夏季"改变为"冬季"。在重叠中,存在具有两个有效偏移量的本地日期时间值。</li>。
     * </ul>
     *  转换用于模拟间隙或重叠的情况。正常情况将返回null。
     * <p>
     *  有多种方法来处理从{@code LocalDateTime}的转换。一种技术,使用这种方法,将是：
     * <pre>
     * ZoneOffsetTransition trans = rules.getTransition(localDT); if(trans == null){//间隙或重叠：确定从转换做什么} else {//正常情况：只有一个有效的偏移zoneOffset = rule.getOffset(localDT); }
     * }。
     * </pre>
     * 
     * 
     * @param localDateTime  the local date-time to query for offset transition, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the offset transition, null if the local date-time is not in transition
     */
    public ZoneOffsetTransition getTransition(LocalDateTime localDateTime) {
        Object info = getOffsetInfo(localDateTime);
        return (info instanceof ZoneOffsetTransition ? (ZoneOffsetTransition) info : null);
    }

    private Object getOffsetInfo(LocalDateTime dt) {
        if (savingsInstantTransitions.length == 0) {
            return standardOffsets[0];
        }
        // check if using last rules
        if (lastRules.length > 0 &&
                dt.isAfter(savingsLocalTransitions[savingsLocalTransitions.length - 1])) {
            ZoneOffsetTransition[] transArray = findTransitionArray(dt.getYear());
            Object info = null;
            for (ZoneOffsetTransition trans : transArray) {
                info = findOffsetInfo(dt, trans);
                if (info instanceof ZoneOffsetTransition || info.equals(trans.getOffsetBefore())) {
                    return info;
                }
            }
            return info;
        }

        // using historic rules
        int index  = Arrays.binarySearch(savingsLocalTransitions, dt);
        if (index == -1) {
            // before first transition
            return wallOffsets[0];
        }
        if (index < 0) {
            // switch negative insert position to start of matched range
            index = -index - 2;
        } else if (index < savingsLocalTransitions.length - 1 &&
                savingsLocalTransitions[index].equals(savingsLocalTransitions[index + 1])) {
            // handle overlap immediately following gap
            index++;
        }
        if ((index & 1) == 0) {
            // gap or overlap
            LocalDateTime dtBefore = savingsLocalTransitions[index];
            LocalDateTime dtAfter = savingsLocalTransitions[index + 1];
            ZoneOffset offsetBefore = wallOffsets[index / 2];
            ZoneOffset offsetAfter = wallOffsets[index / 2 + 1];
            if (offsetAfter.getTotalSeconds() > offsetBefore.getTotalSeconds()) {
                // gap
                return new ZoneOffsetTransition(dtBefore, offsetBefore, offsetAfter);
            } else {
                // overlap
                return new ZoneOffsetTransition(dtAfter, offsetBefore, offsetAfter);
            }
        } else {
            // normal (neither gap or overlap)
            return wallOffsets[index / 2 + 1];
        }
    }

    /**
     * Finds the offset info for a local date-time and transition.
     *
     * <p>
     *  查找本地日期时间和转换的偏移量信息。
     * 
     * 
     * @param dt  the date-time, not null
     * @param trans  the transition, not null
     * @return the offset info, not null
     */
    private Object findOffsetInfo(LocalDateTime dt, ZoneOffsetTransition trans) {
        LocalDateTime localTransition = trans.getDateTimeBefore();
        if (trans.isGap()) {
            if (dt.isBefore(localTransition)) {
                return trans.getOffsetBefore();
            }
            if (dt.isBefore(trans.getDateTimeAfter())) {
                return trans;
            } else {
                return trans.getOffsetAfter();
            }
        } else {
            if (dt.isBefore(localTransition) == false) {
                return trans.getOffsetAfter();
            }
            if (dt.isBefore(trans.getDateTimeAfter())) {
                return trans.getOffsetBefore();
            } else {
                return trans;
            }
        }
    }

    /**
     * Finds the appropriate transition array for the given year.
     *
     * <p>
     *  找到给定年份的适当转换数组。
     * 
     * 
     * @param year  the year, not null
     * @return the transition array, not null
     */
    private ZoneOffsetTransition[] findTransitionArray(int year) {
        Integer yearObj = year;  // should use Year class, but this saves a class load
        ZoneOffsetTransition[] transArray = lastRulesCache.get(yearObj);
        if (transArray != null) {
            return transArray;
        }
        ZoneOffsetTransitionRule[] ruleArray = lastRules;
        transArray  = new ZoneOffsetTransition[ruleArray.length];
        for (int i = 0; i < ruleArray.length; i++) {
            transArray[i] = ruleArray[i].createTransition(year);
        }
        if (year < LAST_CACHED_YEAR) {
            lastRulesCache.putIfAbsent(yearObj, transArray);
        }
        return transArray;
    }

    /**
     * Gets the standard offset for the specified instant in this zone.
     * <p>
     * This provides access to historic information on how the standard offset
     * has changed over time.
     * The standard offset is the offset before any daylight saving time is applied.
     * This is typically the offset applicable during winter.
     *
     * <p>
     *  获取此区域中指定时刻的标准偏移量。
     * <p>
     *  这提供了对标准偏移随时间变化的历史信息的访问。标准偏移是应用任何夏令时之前的偏移。这通常是冬季适用的偏移量。
     * 
     * 
     * @param instant  the instant to find the offset information for, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the standard offset, not null
     */
    public ZoneOffset getStandardOffset(Instant instant) {
        if (savingsInstantTransitions.length == 0) {
            return standardOffsets[0];
        }
        long epochSec = instant.getEpochSecond();
        int index  = Arrays.binarySearch(standardTransitions, epochSec);
        if (index < 0) {
            // switch negative insert position to start of matched range
            index = -index - 2;
        }
        return standardOffsets[index + 1];
    }

    /**
     * Gets the amount of daylight savings in use for the specified instant in this zone.
     * <p>
     * This provides access to historic information on how the amount of daylight
     * savings has changed over time.
     * This is the difference between the standard offset and the actual offset.
     * Typically the amount is zero during winter and one hour during summer.
     * Time-zones are second-based, so the nanosecond part of the duration will be zero.
     * <p>
     * This default implementation calculates the duration from the
     * {@link #getOffset(java.time.Instant) actual} and
     * {@link #getStandardOffset(java.time.Instant) standard} offsets.
     *
     * <p>
     *  获取此区域中指定时刻正在使用的夏令时数量。
     * <p>
     *  这提供了有关夏令时量如何随时间变化的历史信息。这是标准偏移和实际偏移之间的差异。通常该量在冬季为零,在夏季为一小时。时区是基于秒的,因此持续时间的纳秒部分将为零。
     * <p>
     *  此默认实施方案根据{@link #getOffset(java.time.Instant)actual}和{@link #getStandardOffset(java.time.Instant)standard}
     * 偏移量计算持续时间。
     * 
     * 
     * @param instant  the instant to find the daylight savings for, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the difference between the standard and actual offset, not null
     */
    public Duration getDaylightSavings(Instant instant) {
        if (savingsInstantTransitions.length == 0) {
            return Duration.ZERO;
        }
        ZoneOffset standardOffset = getStandardOffset(instant);
        ZoneOffset actualOffset = getOffset(instant);
        return Duration.ofSeconds(actualOffset.getTotalSeconds() - standardOffset.getTotalSeconds());
    }

    /**
     * Checks if the specified instant is in daylight savings.
     * <p>
     * This checks if the standard offset and the actual offset are the same
     * for the specified instant.
     * If they are not, it is assumed that daylight savings is in operation.
     * <p>
     * This default implementation compares the {@link #getOffset(java.time.Instant) actual}
     * and {@link #getStandardOffset(java.time.Instant) standard} offsets.
     *
     * <p>
     *  检查指定的时间是否为夏令时。
     * <p>
     *  这将检查标准偏移和实际偏移在指定时刻是否相同。如果不是,则假定夏令时运行。
     * <p>
     * 此默认实现将{@link #getOffset(java.time.Instant)actual}和{@link #getStandardOffset(java.time.Instant)standard}
     * 偏移量进行比较。
     * 
     * 
     * @param instant  the instant to find the offset information for, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the standard offset, not null
     */
    public boolean isDaylightSavings(Instant instant) {
        return (getStandardOffset(instant).equals(getOffset(instant)) == false);
    }

    /**
     * Checks if the offset date-time is valid for these rules.
     * <p>
     * To be valid, the local date-time must not be in a gap and the offset
     * must match one of the valid offsets.
     * <p>
     * This default implementation checks if {@link #getValidOffsets(java.time.LocalDateTime)}
     * contains the specified offset.
     *
     * <p>
     *  检查偏移日期时间对这些规则是否有效。
     * <p>
     *  为了有效,本地日期时间不能在间隙中,并且偏移必须与有效偏移之一匹配。
     * <p>
     *  此默认实现检查{@link #getValidOffsets(java.time.LocalDateTime)}是否包含指定的偏移量。
     * 
     * 
     * @param localDateTime  the date-time to check, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @param offset  the offset to check, null returns false
     * @return true if the offset date-time is valid for these rules
     */
    public boolean isValidOffset(LocalDateTime localDateTime, ZoneOffset offset) {
        return getValidOffsets(localDateTime).contains(offset);
    }

    /**
     * Gets the next transition after the specified instant.
     * <p>
     * This returns details of the next transition after the specified instant.
     * For example, if the instant represents a point where "Summer" daylight savings time
     * applies, then the method will return the transition to the next "Winter" time.
     *
     * <p>
     *  获取指定时刻后的下一个转换。
     * <p>
     *  这将返回指定时刻之后的下一个转换的详细信息。例如,如果该时刻表示应用"夏季"夏令时的点,则该方法将返回到下一个"冬季"时间的转换。
     * 
     * 
     * @param instant  the instant to get the next transition after, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the next transition after the specified instant, null if this is after the last transition
     */
    public ZoneOffsetTransition nextTransition(Instant instant) {
        if (savingsInstantTransitions.length == 0) {
            return null;
        }
        long epochSec = instant.getEpochSecond();
        // check if using last rules
        if (epochSec >= savingsInstantTransitions[savingsInstantTransitions.length - 1]) {
            if (lastRules.length == 0) {
                return null;
            }
            // search year the instant is in
            int year = findYear(epochSec, wallOffsets[wallOffsets.length - 1]);
            ZoneOffsetTransition[] transArray = findTransitionArray(year);
            for (ZoneOffsetTransition trans : transArray) {
                if (epochSec < trans.toEpochSecond()) {
                    return trans;
                }
            }
            // use first from following year
            if (year < Year.MAX_VALUE) {
                transArray = findTransitionArray(year + 1);
                return transArray[0];
            }
            return null;
        }

        // using historic rules
        int index  = Arrays.binarySearch(savingsInstantTransitions, epochSec);
        if (index < 0) {
            index = -index - 1;  // switched value is the next transition
        } else {
            index += 1;  // exact match, so need to add one to get the next
        }
        return new ZoneOffsetTransition(savingsInstantTransitions[index], wallOffsets[index], wallOffsets[index + 1]);
    }

    /**
     * Gets the previous transition before the specified instant.
     * <p>
     * This returns details of the previous transition after the specified instant.
     * For example, if the instant represents a point where "summer" daylight saving time
     * applies, then the method will return the transition from the previous "winter" time.
     *
     * <p>
     *  获取指定时刻之前的转换。
     * <p>
     *  这将返回指定时刻之后的上一个转换的详细信息。例如,如果瞬时表示"夏季"夏令时适用的点,则该方法将返回从前一"冬季"时间的转变。
     * 
     * 
     * @param instant  the instant to get the previous transition after, not null, but null
     *  may be ignored if the rules have a single offset for all instants
     * @return the previous transition after the specified instant, null if this is before the first transition
     */
    public ZoneOffsetTransition previousTransition(Instant instant) {
        if (savingsInstantTransitions.length == 0) {
            return null;
        }
        long epochSec = instant.getEpochSecond();
        if (instant.getNano() > 0 && epochSec < Long.MAX_VALUE) {
            epochSec += 1;  // allow rest of method to only use seconds
        }

        // check if using last rules
        long lastHistoric = savingsInstantTransitions[savingsInstantTransitions.length - 1];
        if (lastRules.length > 0 && epochSec > lastHistoric) {
            // search year the instant is in
            ZoneOffset lastHistoricOffset = wallOffsets[wallOffsets.length - 1];
            int year = findYear(epochSec, lastHistoricOffset);
            ZoneOffsetTransition[] transArray = findTransitionArray(year);
            for (int i = transArray.length - 1; i >= 0; i--) {
                if (epochSec > transArray[i].toEpochSecond()) {
                    return transArray[i];
                }
            }
            // use last from preceding year
            int lastHistoricYear = findYear(lastHistoric, lastHistoricOffset);
            if (--year > lastHistoricYear) {
                transArray = findTransitionArray(year);
                return transArray[transArray.length - 1];
            }
            // drop through
        }

        // using historic rules
        int index  = Arrays.binarySearch(savingsInstantTransitions, epochSec);
        if (index < 0) {
            index = -index - 1;
        }
        if (index <= 0) {
            return null;
        }
        return new ZoneOffsetTransition(savingsInstantTransitions[index - 1], wallOffsets[index - 1], wallOffsets[index]);
    }

    private int findYear(long epochSecond, ZoneOffset offset) {
        // inline for performance
        long localSecond = epochSecond + offset.getTotalSeconds();
        long localEpochDay = Math.floorDiv(localSecond, 86400);
        return LocalDate.ofEpochDay(localEpochDay).getYear();
    }

    /**
     * Gets the complete list of fully defined transitions.
     * <p>
     * The complete set of transitions for this rules instance is defined by this method
     * and {@link #getTransitionRules()}. This method returns those transitions that have
     * been fully defined. These are typically historical, but may be in the future.
     * <p>
     * The list will be empty for fixed offset rules and for any time-zone where there has
     * only ever been a single offset. The list will also be empty if the transition rules are unknown.
     *
     * <p>
     *  获取完全定义的转换的完整列表。
     * <p>
     *  此规则实例的完整转换集由此方法和{@link #getTransitionRules()}定义。此方法返回已完全定义的那些转换。这些通常是历史的,但可能在未来。
     * <p>
     * 对于固定偏移规则和任何只有单个偏移的时区,该列表将为空。如果转换规则未知,列表也将为空。
     * 
     * 
     * @return an immutable list of fully defined transitions, not null
     */
    public List<ZoneOffsetTransition> getTransitions() {
        List<ZoneOffsetTransition> list = new ArrayList<>();
        for (int i = 0; i < savingsInstantTransitions.length; i++) {
            list.add(new ZoneOffsetTransition(savingsInstantTransitions[i], wallOffsets[i], wallOffsets[i + 1]));
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * Gets the list of transition rules for years beyond those defined in the transition list.
     * <p>
     * The complete set of transitions for this rules instance is defined by this method
     * and {@link #getTransitions()}. This method returns instances of {@link ZoneOffsetTransitionRule}
     * that define an algorithm for when transitions will occur.
     * <p>
     * For any given {@code ZoneRules}, this list contains the transition rules for years
     * beyond those years that have been fully defined. These rules typically refer to future
     * daylight saving time rule changes.
     * <p>
     * If the zone defines daylight savings into the future, then the list will normally
     * be of size two and hold information about entering and exiting daylight savings.
     * If the zone does not have daylight savings, or information about future changes
     * is uncertain, then the list will be empty.
     * <p>
     * The list will be empty for fixed offset rules and for any time-zone where there is no
     * daylight saving time. The list will also be empty if the transition rules are unknown.
     *
     * <p>
     *  获取超出转换列表中定义的转换规则的列表。
     * <p>
     *  此规则实例的完整转换集由此方法和{@link #getTransitions()}定义。此方法返回{@link ZoneOffsetTransitionRule}的实例,定义将发生转换的算法。
     * <p>
     *  对于任何给定的{@code ZoneRules},此列表包含已经完全定义的年份之后的年份的转换规则。这些规则通常指未来的夏令时规则更改。
     * <p>
     *  如果区域将夏令时定义为未来,则列表通常为大小2,并保存有关进入和退出夏令时的信息。如果区域没有夏令时,或关于未来变化的信息不确定,则列表将为空。
     * <p>
     *  对于固定偏移规则和没有夏令时的任何时区,该列表将为空。如果转换规则未知,列表也将为空。
     * 
     * 
     * @return an immutable list of transition rules, not null
     */
    public List<ZoneOffsetTransitionRule> getTransitionRules() {
        return Collections.unmodifiableList(Arrays.asList(lastRules));
    }

    /**
     * Checks if this set of rules equals another.
     * <p>
     * Two rule sets are equal if they will always result in the same output
     * for any given input instant or local date-time.
     * Rules from two different groups may return false even if they are in fact the same.
     * <p>
     * This definition should result in implementations comparing their entire state.
     *
     * <p>
     *  检查这组规则是否等同于另一个。
     * <p>
     *  如果对于任何给定的输入即时或本地日期时间它们将总是导致相同的输出,则两个规则集是相等的。来自两个不同组的规则可能返回假,即使它们实际上是相同的。
     * <p>
     * 这个定义应该导致实现比较它们的整个状态。
     * 
     * 
     * @param otherRules  the other rules, null returns false
     * @return true if this rules is the same as that specified
     */
    @Override
    public boolean equals(Object otherRules) {
        if (this == otherRules) {
           return true;
        }
        if (otherRules instanceof ZoneRules) {
            ZoneRules other = (ZoneRules) otherRules;
            return Arrays.equals(standardTransitions, other.standardTransitions) &&
                    Arrays.equals(standardOffsets, other.standardOffsets) &&
                    Arrays.equals(savingsInstantTransitions, other.savingsInstantTransitions) &&
                    Arrays.equals(wallOffsets, other.wallOffsets) &&
                    Arrays.equals(lastRules, other.lastRules);
        }
        return false;
    }

    /**
     * Returns a suitable hash code given the definition of {@code #equals}.
     *
     * <p>
     * 
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(standardTransitions) ^
                Arrays.hashCode(standardOffsets) ^
                Arrays.hashCode(savingsInstantTransitions) ^
                Arrays.hashCode(wallOffsets) ^
                Arrays.hashCode(lastRules);
    }

    /**
     * Returns a string describing this object.
     *
     * <p>
     *  给定{@code #equals}的定义,返回一个合适的哈希码。
     * 
     * 
     * @return a string for debugging, not null
     */
    @Override
    public String toString() {
        return "ZoneRules[currentStandardOffset=" + standardOffsets[standardOffsets.length - 1] + "]";
    }

}
