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
package java.time.temporal;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.DateTimeException;

/**
 * The range of valid values for a date-time field.
 * <p>
 * All {@link TemporalField} instances have a valid range of values.
 * For example, the ISO day-of-month runs from 1 to somewhere between 28 and 31.
 * This class captures that valid range.
 * <p>
 * It is important to be aware of the limitations of this class.
 * Only the minimum and maximum values are provided.
 * It is possible for there to be invalid values within the outer range.
 * For example, a weird field may have valid values of 1, 2, 4, 6, 7, thus
 * have a range of '1 - 7', despite that fact that values 3 and 5 are invalid.
 * <p>
 * Instances of this class are not tied to a specific field.
 *
 * @implSpec
 * This class is immutable and thread-safe.
 *
 * <p>
 *  日期时间字段的有效值范围。
 * <p>
 *  所有{@link TemporalField}实例都具有有效的值范围。例如,ISO日期从1到28到31之间的某个值。此类捕获该有效范围。
 * <p>
 *  重要的是要知道这个类的限制。仅提供最小值和最大值。外部范围内可能存在无效值。例如,奇怪的字段可以具有1,2,4,6,7的有效值,因此具有"1-7"的范围,尽管值3和5无效的事实。
 * <p>
 *  此类的实例不绑定到特定字段。
 * 
 *  @implSpec这个类是不可变的和线程安全的。
 * 
 * 
 * @since 1.8
 */
public final class ValueRange implements Serializable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = -7317881728594519368L;

    /**
     * The smallest minimum value.
     * <p>
     *  最小最小值。
     * 
     */
    private final long minSmallest;
    /**
     * The largest minimum value.
     * <p>
     * 最大最小值。
     * 
     */
    private final long minLargest;
    /**
     * The smallest maximum value.
     * <p>
     *  最小最大值。
     * 
     */
    private final long maxSmallest;
    /**
     * The largest maximum value.
     * <p>
     *  最大最大值。
     * 
     */
    private final long maxLargest;

    /**
     * Obtains a fixed value range.
     * <p>
     * This factory obtains a range where the minimum and maximum values are fixed.
     * For example, the ISO month-of-year always runs from 1 to 12.
     *
     * <p>
     *  获取固定值范围。
     * <p>
     *  该工厂获得固定最小值和最大值的范围。例如,ISO年份始终从1到12。
     * 
     * 
     * @param min  the minimum value
     * @param max  the maximum value
     * @return the ValueRange for min, max, not null
     * @throws IllegalArgumentException if the minimum is greater than the maximum
     */
    public static ValueRange of(long min, long max) {
        if (min > max) {
            throw new IllegalArgumentException("Minimum value must be less than maximum value");
        }
        return new ValueRange(min, min, max, max);
    }

    /**
     * Obtains a variable value range.
     * <p>
     * This factory obtains a range where the minimum value is fixed and the maximum value may vary.
     * For example, the ISO day-of-month always starts at 1, but ends between 28 and 31.
     *
     * <p>
     *  获取变量值范围。
     * <p>
     *  该工厂获得其中最小值固定且最大值可变化的范围。例如,ISO日期总是从1开始,但在28和31之间结束。
     * 
     * 
     * @param min  the minimum value
     * @param maxSmallest  the smallest maximum value
     * @param maxLargest  the largest maximum value
     * @return the ValueRange for min, smallest max, largest max, not null
     * @throws IllegalArgumentException if
     *     the minimum is greater than the smallest maximum,
     *  or the smallest maximum is greater than the largest maximum
     */
    public static ValueRange of(long min, long maxSmallest, long maxLargest) {
        return of(min, min, maxSmallest, maxLargest);
    }

    /**
     * Obtains a fully variable value range.
     * <p>
     * This factory obtains a range where both the minimum and maximum value may vary.
     *
     * <p>
     *  获取完全可变的值范围。
     * <p>
     *  该工厂获得一个范围,其中最小值和最大值都可能变化。
     * 
     * 
     * @param minSmallest  the smallest minimum value
     * @param minLargest  the largest minimum value
     * @param maxSmallest  the smallest maximum value
     * @param maxLargest  the largest maximum value
     * @return the ValueRange for smallest min, largest min, smallest max, largest max, not null
     * @throws IllegalArgumentException if
     *     the smallest minimum is greater than the smallest maximum,
     *  or the smallest maximum is greater than the largest maximum
     *  or the largest minimum is greater than the largest maximum
     */
    public static ValueRange of(long minSmallest, long minLargest, long maxSmallest, long maxLargest) {
        if (minSmallest > minLargest) {
            throw new IllegalArgumentException("Smallest minimum value must be less than largest minimum value");
        }
        if (maxSmallest > maxLargest) {
            throw new IllegalArgumentException("Smallest maximum value must be less than largest maximum value");
        }
        if (minLargest > maxLargest) {
            throw new IllegalArgumentException("Minimum value must be less than maximum value");
        }
        return new ValueRange(minSmallest, minLargest, maxSmallest, maxLargest);
    }

    /**
     * Restrictive constructor.
     *
     * <p>
     *  限制构造函数。
     * 
     * 
     * @param minSmallest  the smallest minimum value
     * @param minLargest  the largest minimum value
     * @param maxSmallest  the smallest minimum value
     * @param maxLargest  the largest minimum value
     */
    private ValueRange(long minSmallest, long minLargest, long maxSmallest, long maxLargest) {
        this.minSmallest = minSmallest;
        this.minLargest = minLargest;
        this.maxSmallest = maxSmallest;
        this.maxLargest = maxLargest;
    }

    //-----------------------------------------------------------------------
    /**
     * Is the value range fixed and fully known.
     * <p>
     * For example, the ISO day-of-month runs from 1 to between 28 and 31.
     * Since there is uncertainty about the maximum value, the range is not fixed.
     * However, for the month of January, the range is always 1 to 31, thus it is fixed.
     *
     * <p>
     *  是值范围固定和完全知道。
     * <p>
     *  例如,ISO日期从1到28到31之间。由于最大值存在不确定性,因此范围不固定。但是,对于1月份,范围始终为1到31,因此它是固定的。
     * 
     * 
     * @return true if the set of values is fixed
     */
    public boolean isFixed() {
        return minSmallest == minLargest && maxSmallest == maxLargest;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the minimum value that the field can take.
     * <p>
     * For example, the ISO day-of-month always starts at 1.
     * The minimum is therefore 1.
     *
     * <p>
     *  获取字段可以采用的最小值。
     * <p>
     *  例如,ISO日期总是从1开始。因此,最小值为1。
     * 
     * 
     * @return the minimum value for this field
     */
    public long getMinimum() {
        return minSmallest;
    }

    /**
     * Gets the largest possible minimum value that the field can take.
     * <p>
     * For example, the ISO day-of-month always starts at 1.
     * The largest minimum is therefore 1.
     *
     * <p>
     *  获取字段可能采用的最大可能最小值。
     * <p>
     *  例如,ISO日期总是从1开始。因此最大的最小值为1。
     * 
     * 
     * @return the largest possible minimum value for this field
     */
    public long getLargestMinimum() {
        return minLargest;
    }

    /**
     * Gets the smallest possible maximum value that the field can take.
     * <p>
     * For example, the ISO day-of-month runs to between 28 and 31 days.
     * The smallest maximum is therefore 28.
     *
     * <p>
     *  获取字段可以采用的最小可能的最大值。
     * <p>
     *  例如,ISO日期的运行时间为28到31天。因此最小的最大值为28。
     * 
     * 
     * @return the smallest possible maximum value for this field
     */
    public long getSmallestMaximum() {
        return maxSmallest;
    }

    /**
     * Gets the maximum value that the field can take.
     * <p>
     * For example, the ISO day-of-month runs to between 28 and 31 days.
     * The maximum is therefore 31.
     *
     * <p>
     *  获取字段可以采用的最大值。
     * <p>
     *  例如,ISO日期的运行时间为28到31天。因此最大值为31。
     * 
     * 
     * @return the maximum value for this field
     */
    public long getMaximum() {
        return maxLargest;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if all values in the range fit in an {@code int}.
     * <p>
     * This checks that all valid values are within the bounds of an {@code int}.
     * <p>
     * For example, the ISO month-of-year has values from 1 to 12, which fits in an {@code int}.
     * By comparison, ISO nano-of-day runs from 1 to 86,400,000,000,000 which does not fit in an {@code int}.
     * <p>
     * This implementation uses {@link #getMinimum()} and {@link #getMaximum()}.
     *
     * <p>
     * 检查该范围中的所有值是否适合{@code int}。
     * <p>
     *  这将检查所有有效值是否在{@code int}的边界内。
     * <p>
     *  例如,ISO年的值为1到12,适合{@code int}。相比之下,ISO nano-of-day从1到86,400,000,000,000不适合{@code int}。
     * <p>
     *  此实现使用{@link #getMinimum()}和{@link #getMaximum()}。
     * 
     * 
     * @return true if a valid value always fits in an {@code int}
     */
    public boolean isIntValue() {
        return getMinimum() >= Integer.MIN_VALUE && getMaximum() <= Integer.MAX_VALUE;
    }

    /**
     * Checks if the value is within the valid range.
     * <p>
     * This checks that the value is within the stored range of values.
     *
     * <p>
     *  检查值是否在有效范围内。
     * <p>
     *  这将检查该值是否在存储的值范围内。
     * 
     * 
     * @param value  the value to check
     * @return true if the value is valid
     */
    public boolean isValidValue(long value) {
        return (value >= getMinimum() && value <= getMaximum());
    }

    /**
     * Checks if the value is within the valid range and that all values
     * in the range fit in an {@code int}.
     * <p>
     * This method combines {@link #isIntValue()} and {@link #isValidValue(long)}.
     *
     * <p>
     *  检查值是否在有效范围内,并且该范围内的所有值都适合{@code int}。
     * <p>
     *  此方法组合了{@link #isIntValue()}和{@link #isValidValue(long)}。
     * 
     * 
     * @param value  the value to check
     * @return true if the value is valid and fits in an {@code int}
     */
    public boolean isValidIntValue(long value) {
        return isIntValue() && isValidValue(value);
    }

    /**
     * Checks that the specified value is valid.
     * <p>
     * This validates that the value is within the valid range of values.
     * The field is only used to improve the error message.
     *
     * <p>
     *  检查指定的值是否有效。
     * <p>
     *  这将验证该值是否在有效的值范围内。该字段仅用于改进错误消息。
     * 
     * 
     * @param value  the value to check
     * @param field  the field being checked, may be null
     * @return the value that was passed in
     * @see #isValidValue(long)
     */
    public long checkValidValue(long value, TemporalField field) {
        if (isValidValue(value) == false) {
            throw new DateTimeException(genInvalidFieldMessage(field, value));
        }
        return value;
    }

    /**
     * Checks that the specified value is valid and fits in an {@code int}.
     * <p>
     * This validates that the value is within the valid range of values and that
     * all valid values are within the bounds of an {@code int}.
     * The field is only used to improve the error message.
     *
     * <p>
     *  检查指定的值是否有效,并且符合{@code int}。
     * <p>
     *  这将验证该值是否在有效的值范围内,并且所有有效值都在{@code int}的范围内。该字段仅用于改进错误消息。
     * 
     * 
     * @param value  the value to check
     * @param field  the field being checked, may be null
     * @return the value that was passed in
     * @see #isValidIntValue(long)
     */
    public int checkValidIntValue(long value, TemporalField field) {
        if (isValidIntValue(value) == false) {
            throw new DateTimeException(genInvalidFieldMessage(field, value));
        }
        return (int) value;
    }

    private String genInvalidFieldMessage(TemporalField field, long value) {
        if (field != null) {
            return "Invalid value for " + field + " (valid values " + this + "): " + value;
        } else {
            return "Invalid value (valid values " + this + "): " + value;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Restore the state of an ValueRange from the stream.
     * Check that the values are valid.
     *
     * <p>
     *  从流恢复ValueRange的状态。检查值是否有效。
     * 
     * 
     * @param s the stream to read
     * @throws InvalidObjectException if
     *     the smallest minimum is greater than the smallest maximum,
     *  or the smallest maximum is greater than the largest maximum
     *  or the largest minimum is greater than the largest maximum
     * @throws ClassNotFoundException if a class cannot be resolved
     */
    private void readObject(ObjectInputStream s)
         throws IOException, ClassNotFoundException, InvalidObjectException
    {
        s.defaultReadObject();
        if (minSmallest > minLargest) {
            throw new InvalidObjectException("Smallest minimum value must be less than largest minimum value");
        }
        if (maxSmallest > maxLargest) {
            throw new InvalidObjectException("Smallest maximum value must be less than largest maximum value");
        }
        if (minLargest > maxLargest) {
            throw new InvalidObjectException("Minimum value must be less than maximum value");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this range is equal to another range.
     * <p>
     * The comparison is based on the four values, minimum, largest minimum,
     * smallest maximum and maximum.
     * Only objects of type {@code ValueRange} are compared, other types return false.
     *
     * <p>
     *  检查此范围是否等于另一个范围。
     * <p>
     *  比较基于四个值,最小值,最大最小值,最小最大值和最大值。仅比较{@code ValueRange}类型的对象,其他类型返回false。
     * 
     * 
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other range
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ValueRange) {
            ValueRange other = (ValueRange) obj;
           return minSmallest == other.minSmallest && minLargest == other.minLargest &&
                   maxSmallest == other.maxSmallest && maxLargest == other.maxLargest;
        }
        return false;
    }

    /**
     * A hash code for this range.
     *
     * <p>
     *  此范围的哈希码。
     * 
     * 
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        long hash = minSmallest + minLargest << 16 + minLargest >> 48 + maxSmallest << 32 +
            maxSmallest >> 32 + maxLargest << 48 + maxLargest >> 16;
        return (int) (hash ^ (hash >>> 32));
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this range as a {@code String}.
     * <p>
     * The format will be '{min}/{largestMin} - {smallestMax}/{max}',
     * where the largestMin or smallestMax sections may be omitted, together
     * with associated slash, if they are the same as the min or max.
     *
     * <p>
     * 将此范围输出为{@code String}。
     * <p>
     *  格式将是"{min} / {largestMin}  -  {smallestMax} / {max}',其中如果min或max最大,则可以省略maximumMin或smallestMax段以及关联的
     * 
     * @return a string representation of this range, not null
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(minSmallest);
        if (minSmallest != minLargest) {
            buf.append('/').append(minLargest);
        }
        buf.append(" - ").append(maxSmallest);
        if (maxSmallest != maxLargest) {
            buf.append('/').append(maxLargest);
        }
        return buf.toString();
    }

}
