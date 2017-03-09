/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;

import sun.misc.FloatingDecimal;
import java.util.Arrays;

/**
 * A mutable sequence of characters.
 * <p>
 * Implements a modifiable string. At any point in time it contains some
 * particular sequence of characters, but the length and content of the
 * sequence can be changed through certain method calls.
 *
 * <p>Unless otherwise noted, passing a {@code null} argument to a constructor
 * or method in this class will cause a {@link NullPointerException} to be
 * thrown.
 *
 * <p>
 *  可变字符序列。
 * <p>
 *  实现可修改的字符串。在任何时间点,它包含一些特定的字符序列,但是序列的长度和内容可以通过某些方法调用来改变。
 * 
 *  <p>除非另有说明,否则将{@code null}参数传递给此类中的构造函数或方法将导致抛出{@link NullPointerException}。
 * 
 * 
 * @author      Michael McCloskey
 * @author      Martin Buchholz
 * @author      Ulf Zibis
 * @since       1.5
 */
abstract class AbstractStringBuilder implements Appendable, CharSequence {
    /**
     * The value is used for character storage.
     * <p>
     *  该值用于字符存储。
     * 
     */
    char[] value;

    /**
     * The count is the number of characters used.
     * <p>
     *  计数是使用的字符数。
     * 
     */
    int count;

    /**
     * This no-arg constructor is necessary for serialization of subclasses.
     * <p>
     *  这个无参数构造函数对于子类的序列化是必需的。
     * 
     */
    AbstractStringBuilder() {
    }

    /**
     * Creates an AbstractStringBuilder of the specified capacity.
     * <p>
     *  创建指定容量的AbstractStringBuilder。
     * 
     */
    AbstractStringBuilder(int capacity) {
        value = new char[capacity];
    }

    /**
     * Returns the length (character count).
     *
     * <p>
     *  返回长度(字符计数)。
     * 
     * 
     * @return  the length of the sequence of characters currently
     *          represented by this object
     */
    @Override
    public int length() {
        return count;
    }

    /**
     * Returns the current capacity. The capacity is the amount of storage
     * available for newly inserted characters, beyond which an allocation
     * will occur.
     *
     * <p>
     *  返回当前容量。容量是可用于新插入字符的存储量,超过该容量将进行分配。
     * 
     * 
     * @return  the current capacity
     */
    public int capacity() {
        return value.length;
    }

    /**
     * Ensures that the capacity is at least equal to the specified minimum.
     * If the current capacity is less than the argument, then a new internal
     * array is allocated with greater capacity. The new capacity is the
     * larger of:
     * <ul>
     * <li>The {@code minimumCapacity} argument.
     * <li>Twice the old capacity, plus {@code 2}.
     * </ul>
     * If the {@code minimumCapacity} argument is nonpositive, this
     * method takes no action and simply returns.
     * Note that subsequent operations on this object can reduce the
     * actual capacity below that requested here.
     *
     * <p>
     *  确保容量至少等于指定的最小值。如果当前容量小于参数,则为新的内部数组分配更大的容量。新容量是以下的较大者：
     * <ul>
     *  <li> {@code minimumCapacity}参数。 <li>两倍的旧容量,加上{@code 2}。
     * </ul>
     *  如果{@code minimumCapacity}参数为非正数,则此方法不会执行任何操作,只会返回。请注意,此对象的后续操作可将实际容量降低到低于此处要求的容量。
     * 
     * 
     * @param   minimumCapacity   the minimum desired capacity.
     */
    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > 0)
            ensureCapacityInternal(minimumCapacity);
    }

    /**
     * This method has the same contract as ensureCapacity, but is
     * never synchronized.
     * <p>
     * 此方法具有与ensureCapacity相同的合同,但永远不会同步。
     * 
     */
    private void ensureCapacityInternal(int minimumCapacity) {
        // overflow-conscious code
        if (minimumCapacity - value.length > 0)
            expandCapacity(minimumCapacity);
    }

    /**
     * This implements the expansion semantics of ensureCapacity with no
     * size check or synchronization.
     * <p>
     *  这实现了ensureCapacity的扩展语义,没有大小检查或同步。
     * 
     */
    void expandCapacity(int minimumCapacity) {
        int newCapacity = value.length * 2 + 2;
        if (newCapacity - minimumCapacity < 0)
            newCapacity = minimumCapacity;
        if (newCapacity < 0) {
            if (minimumCapacity < 0) // overflow
                throw new OutOfMemoryError();
            newCapacity = Integer.MAX_VALUE;
        }
        value = Arrays.copyOf(value, newCapacity);
    }

    /**
     * Attempts to reduce storage used for the character sequence.
     * If the buffer is larger than necessary to hold its current sequence of
     * characters, then it may be resized to become more space efficient.
     * Calling this method may, but is not required to, affect the value
     * returned by a subsequent call to the {@link #capacity()} method.
     * <p>
     *  尝试减少用于字符序列的存储。如果缓冲器大于保持其当前字符序列所需的大小,则可以将其调整大小以变得更有空间效率。
     * 调用此方法可能会影响由{@link #capacity()}方法的后续调用返回的值,但不是必需的。
     * 
     */
    public void trimToSize() {
        if (count < value.length) {
            value = Arrays.copyOf(value, count);
        }
    }

    /**
     * Sets the length of the character sequence.
     * The sequence is changed to a new character sequence
     * whose length is specified by the argument. For every nonnegative
     * index <i>k</i> less than {@code newLength}, the character at
     * index <i>k</i> in the new character sequence is the same as the
     * character at index <i>k</i> in the old sequence if <i>k</i> is less
     * than the length of the old character sequence; otherwise, it is the
     * null character {@code '\u005Cu0000'}.
     *
     * In other words, if the {@code newLength} argument is less than
     * the current length, the length is changed to the specified length.
     * <p>
     * If the {@code newLength} argument is greater than or equal
     * to the current length, sufficient null characters
     * ({@code '\u005Cu0000'}) are appended so that
     * length becomes the {@code newLength} argument.
     * <p>
     * The {@code newLength} argument must be greater than or equal
     * to {@code 0}.
     *
     * <p>
     *  设置字符序列的长度。序列将更改为新的字符序列,其长度由参数指定。
     * 对于小于{@code newLength}的每个非负指数k k,新字符序列中的索引k k处的字符与索引k处的字符相同, / i>在旧序列中,如果<i> k </i>小于旧字符序列的长度;否则,它是空字符
     * {@code'\ u005Cu0000'}。
     *  设置字符序列的长度。序列将更改为新的字符序列,其长度由参数指定。
     * 
     *  换句话说,如果{@code newLength}参数小于当前长度,则长度将更改为指定的长度。
     * <p>
     *  如果{@code newLength}参数大于或等于当前长度,则附加足够的空字符({@code'\ u005Cu0000'}),以使长度成为{@code newLength}参数。
     * <p>
     *  {@code newLength}参数必须大于或等于{@code 0}。
     * 
     * 
     * @param      newLength   the new length
     * @throws     IndexOutOfBoundsException  if the
     *               {@code newLength} argument is negative.
     */
    public void setLength(int newLength) {
        if (newLength < 0)
            throw new StringIndexOutOfBoundsException(newLength);
        ensureCapacityInternal(newLength);

        if (count < newLength) {
            Arrays.fill(value, count, newLength, '\0');
        }

        count = newLength;
    }

    /**
     * Returns the {@code char} value in this sequence at the specified index.
     * The first {@code char} value is at index {@code 0}, the next at index
     * {@code 1}, and so on, as in array indexing.
     * <p>
     * The index argument must be greater than or equal to
     * {@code 0}, and less than the length of this sequence.
     *
     * <p>If the {@code char} value specified by the index is a
     * <a href="Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     *
     * <p>
     * 返回此序列中指定索引处的{@code char}值。第一个{@code char}值位于索引{@code 0},下一个位于索引{@code 1},依此类推,如数组索引。
     * <p>
     *  索引参数必须大于或等于{@code 0},且小于此序列的长度。
     * 
     *  <p>如果索引指定的{​​@code char}值为<a href="Character.html#unicode">代理</a>,则会返回代理值。
     * 
     * 
     * @param      index   the index of the desired {@code char} value.
     * @return     the {@code char} value at the specified index.
     * @throws     IndexOutOfBoundsException  if {@code index} is
     *             negative or greater than or equal to {@code length()}.
     */
    @Override
    public char charAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        return value[index];
    }

    /**
     * Returns the character (Unicode code point) at the specified
     * index. The index refers to {@code char} values
     * (Unicode code units) and ranges from {@code 0} to
     * {@link #length()}{@code  - 1}.
     *
     * <p> If the {@code char} value specified at the given index
     * is in the high-surrogate range, the following index is less
     * than the length of this sequence, and the
     * {@code char} value at the following index is in the
     * low-surrogate range, then the supplementary code point
     * corresponding to this surrogate pair is returned. Otherwise,
     * the {@code char} value at the given index is returned.
     *
     * <p>
     *  返回指定索引处的字符(Unicode代码点)。
     * 索引是指{@code char}值(Unicode代码单位),范围从{@code 0}到{@link #length()} {@ code  -  1}。
     * 
     *  <p>如果在给定索引处指定的{@code char}值位于高代理范围内,则以下索引小于此序列的长度,并且下面索引处的{@code char}值为低代理范围,则返回对应于该代理对的补充代码点。
     * 否则,将返回给定索引处的{@code char}值。
     * 
     * 
     * @param      index the index to the {@code char} values
     * @return     the code point value of the character at the
     *             {@code index}
     * @exception  IndexOutOfBoundsException  if the {@code index}
     *             argument is negative or not less than the length of this
     *             sequence.
     */
    public int codePointAt(int index) {
        if ((index < 0) || (index >= count)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return Character.codePointAtImpl(value, index, count);
    }

    /**
     * Returns the character (Unicode code point) before the specified
     * index. The index refers to {@code char} values
     * (Unicode code units) and ranges from {@code 1} to {@link
     * #length()}.
     *
     * <p> If the {@code char} value at {@code (index - 1)}
     * is in the low-surrogate range, {@code (index - 2)} is not
     * negative, and the {@code char} value at {@code (index -
     * 2)} is in the high-surrogate range, then the
     * supplementary code point value of the surrogate pair is
     * returned. If the {@code char} value at {@code index -
     * 1} is an unpaired low-surrogate or a high-surrogate, the
     * surrogate value is returned.
     *
     * <p>
     *  返回指定索引之前的字符(Unicode代码点)。索引引用{@code char}值(Unicode代码单位),范围从{@code 1}到{@link #length()}。
     * 
     * <p>如果{@code(index  -  1)}的{@code char}值位于低代理范围,则{@code(index  -  2)}不为负数,而且{@code char}在{@code(index-2)}
     * 处于高代理范围,则返回代理对的补充代码点值。
     * 如果{@code index  -  1}的{@code char}值是不成对的低代理值或高代理值,则返回代理值。
     * 
     * 
     * @param     index the index following the code point that should be returned
     * @return    the Unicode code point value before the given index.
     * @exception IndexOutOfBoundsException if the {@code index}
     *            argument is less than 1 or greater than the length
     *            of this sequence.
     */
    public int codePointBefore(int index) {
        int i = index - 1;
        if ((i < 0) || (i >= count)) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return Character.codePointBeforeImpl(value, index, 0);
    }

    /**
     * Returns the number of Unicode code points in the specified text
     * range of this sequence. The text range begins at the specified
     * {@code beginIndex} and extends to the {@code char} at
     * index {@code endIndex - 1}. Thus the length (in
     * {@code char}s) of the text range is
     * {@code endIndex-beginIndex}. Unpaired surrogates within
     * this sequence count as one code point each.
     *
     * <p>
     *  返回此序列的指定文本范围内的Unicode代码点的数量。
     * 文本范围从指定的{@code beginIndex}开始,并延伸到索引{@code endIndex  -  1}处的{@code char}。
     * 因此,文本范围的长度(在{@code char} s中)为{@code endIndex-beginIndex}。该序列中的未配对代理计为每个代码点。
     * 
     * 
     * @param beginIndex the index to the first {@code char} of
     * the text range.
     * @param endIndex the index after the last {@code char} of
     * the text range.
     * @return the number of Unicode code points in the specified text
     * range
     * @exception IndexOutOfBoundsException if the
     * {@code beginIndex} is negative, or {@code endIndex}
     * is larger than the length of this sequence, or
     * {@code beginIndex} is larger than {@code endIndex}.
     */
    public int codePointCount(int beginIndex, int endIndex) {
        if (beginIndex < 0 || endIndex > count || beginIndex > endIndex) {
            throw new IndexOutOfBoundsException();
        }
        return Character.codePointCountImpl(value, beginIndex, endIndex-beginIndex);
    }

    /**
     * Returns the index within this sequence that is offset from the
     * given {@code index} by {@code codePointOffset} code
     * points. Unpaired surrogates within the text range given by
     * {@code index} and {@code codePointOffset} count as
     * one code point each.
     *
     * <p>
     *  返回此序列中与给定{@code index}偏移{@code codePointOffset}代码点的索引。
     * 由{@code index}和{@code codePointOffset}给出的文本范围内的未配对代理计为每个代码点。
     * 
     * 
     * @param index the index to be offset
     * @param codePointOffset the offset in code points
     * @return the index within this sequence
     * @exception IndexOutOfBoundsException if {@code index}
     *   is negative or larger then the length of this sequence,
     *   or if {@code codePointOffset} is positive and the subsequence
     *   starting with {@code index} has fewer than
     *   {@code codePointOffset} code points,
     *   or if {@code codePointOffset} is negative and the subsequence
     *   before {@code index} has fewer than the absolute value of
     *   {@code codePointOffset} code points.
     */
    public int offsetByCodePoints(int index, int codePointOffset) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException();
        }
        return Character.offsetByCodePointsImpl(value, 0, count,
                                                index, codePointOffset);
    }

    /**
     * Characters are copied from this sequence into the
     * destination character array {@code dst}. The first character to
     * be copied is at index {@code srcBegin}; the last character to
     * be copied is at index {@code srcEnd-1}. The total number of
     * characters to be copied is {@code srcEnd-srcBegin}. The
     * characters are copied into the subarray of {@code dst} starting
     * at index {@code dstBegin} and ending at index:
     * <pre>{@code
     * dstbegin + (srcEnd-srcBegin) - 1
     * }</pre>
     *
     * <p>
     *  字符从此序列复制到目标字符数组{@code dst}。要复制的第一个字符是在索引{@code srcBegin};要复制的最后一个字符位于索引{@code srcEnd-1}。
     * 要复制的字符总数为{@code srcEnd-sr​​cBegin}。
     * 这些字符被复制到从索引{@code dstBegin}开始并以索引结尾的{@code dst}的子阵列中：<pre> {@ code dstbegin +(srcEnd-sr​​cBegin) -  1}
     *  </pre>。
     * 要复制的字符总数为{@code srcEnd-sr​​cBegin}。
     * 
     * 
     * @param      srcBegin   start copying at this offset.
     * @param      srcEnd     stop copying at this offset.
     * @param      dst        the array to copy the data into.
     * @param      dstBegin   offset into {@code dst}.
     * @throws     IndexOutOfBoundsException  if any of the following is true:
     *             <ul>
     *             <li>{@code srcBegin} is negative
     *             <li>{@code dstBegin} is negative
     *             <li>the {@code srcBegin} argument is greater than
     *             the {@code srcEnd} argument.
     *             <li>{@code srcEnd} is greater than
     *             {@code this.length()}.
     *             <li>{@code dstBegin+srcEnd-srcBegin} is greater than
     *             {@code dst.length}
     *             </ul>
     */
    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
    {
        if (srcBegin < 0)
            throw new StringIndexOutOfBoundsException(srcBegin);
        if ((srcEnd < 0) || (srcEnd > count))
            throw new StringIndexOutOfBoundsException(srcEnd);
        if (srcBegin > srcEnd)
            throw new StringIndexOutOfBoundsException("srcBegin > srcEnd");
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }

    /**
     * The character at the specified index is set to {@code ch}. This
     * sequence is altered to represent a new character sequence that is
     * identical to the old character sequence, except that it contains the
     * character {@code ch} at position {@code index}.
     * <p>
     * The index argument must be greater than or equal to
     * {@code 0}, and less than the length of this sequence.
     *
     * <p>
     * 指定索引处的字符设置为{@code ch}。该序列被改变以表示与旧字符序列相同的新字符序列,除了它在位置{@code index}处包含字符{@code ch}。
     * <p>
     *  索引参数必须大于或等于{@code 0},且小于此序列的长度。
     * 
     * 
     * @param      index   the index of the character to modify.
     * @param      ch      the new character.
     * @throws     IndexOutOfBoundsException  if {@code index} is
     *             negative or greater than or equal to {@code length()}.
     */
    public void setCharAt(int index, char ch) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        value[index] = ch;
    }

    /**
     * Appends the string representation of the {@code Object} argument.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(Object)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     *  追加{@code Object}参数的字符串表示形式。
     * <p>
     *  整体效果与使用{@link String#valueOf(Object)}方法将参数转换为字符串一样,然后该字符串的字符为{@link #append(String)added)到此字符序列。
     * 
     * 
     * @param   obj   an {@code Object}.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }

    /**
     * Appends the specified string to this character sequence.
     * <p>
     * The characters of the {@code String} argument are appended, in
     * order, increasing the length of this sequence by the length of the
     * argument. If {@code str} is {@code null}, then the four
     * characters {@code "null"} are appended.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at
     * index <i>k</i> in the new character sequence is equal to the character
     * at index <i>k</i> in the old character sequence, if <i>k</i> is less
     * than <i>n</i>; otherwise, it is equal to the character at index
     * <i>k-n</i> in the argument {@code str}.
     *
     * <p>
     *  将指定的字符串附加到此字符序列。
     * <p>
     *  {@code String}参数的字符按顺序附加,将此序列的长度增加参数的长度。如果{@code str}是{@code null},则会附加四个字符{@code"null"}。
     * <p>
     *  让<i> n </i>是在执行{@code append}方法之前的这个字符序列的长度。
     * 然后,新字符序列中索引<k>的字符等于旧字符序列中索引k的字符,如果<k> k <比<i> </i>否则,它等于参数{@code str}中索引<k>的字符<i> k-n </i>。
     * 
     * 
     * @param   str   a string.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(String str) {
        if (str == null)
            return appendNull();
        int len = str.length();
        ensureCapacityInternal(count + len);
        str.getChars(0, len, value, count);
        count += len;
        return this;
    }

    // Documentation in subclasses because of synchro difference
    public AbstractStringBuilder append(StringBuffer sb) {
        if (sb == null)
            return appendNull();
        int len = sb.length();
        ensureCapacityInternal(count + len);
        sb.getChars(0, len, value, count);
        count += len;
        return this;
    }

    /**
    /* <p>
    /* 
     * @since 1.8
     */
    AbstractStringBuilder append(AbstractStringBuilder asb) {
        if (asb == null)
            return appendNull();
        int len = asb.length();
        ensureCapacityInternal(count + len);
        asb.getChars(0, len, value, count);
        count += len;
        return this;
    }

    // Documentation in subclasses because of synchro difference
    @Override
    public AbstractStringBuilder append(CharSequence s) {
        if (s == null)
            return appendNull();
        if (s instanceof String)
            return this.append((String)s);
        if (s instanceof AbstractStringBuilder)
            return this.append((AbstractStringBuilder)s);

        return this.append(s, 0, s.length());
    }

    private AbstractStringBuilder appendNull() {
        int c = count;
        ensureCapacityInternal(c + 4);
        final char[] value = this.value;
        value[c++] = 'n';
        value[c++] = 'u';
        value[c++] = 'l';
        value[c++] = 'l';
        count = c;
        return this;
    }

    /**
     * Appends a subsequence of the specified {@code CharSequence} to this
     * sequence.
     * <p>
     * Characters of the argument {@code s}, starting at
     * index {@code start}, are appended, in order, to the contents of
     * this sequence up to the (exclusive) index {@code end}. The length
     * of this sequence is increased by the value of {@code end - start}.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at
     * index <i>k</i> in this character sequence becomes equal to the
     * character at index <i>k</i> in this sequence, if <i>k</i> is less than
     * <i>n</i>; otherwise, it is equal to the character at index
     * <i>k+start-n</i> in the argument {@code s}.
     * <p>
     * If {@code s} is {@code null}, then this method appends
     * characters as if the s parameter was a sequence containing the four
     * characters {@code "null"}.
     *
     * <p>
     *  将指定的{@code CharSequence}的子序列附加到此序列。
     * <p>
     * 从索引{@code start}开始的参数{@code s}的字符按顺序附加到该序列的内容直到(排他的)索引{@code end}。该序列的长度增加{@code end-start}的值。
     * <p>
     *  让<i> n </i>是在执行{@code append}方法之前的这个字符序列的长度。
     * 然后,在该序列中,在该字符序列中的索引<k>的字符变为等于索引k的字符,如果<i> k <i>小于<i > n </i>;否则,它等于参数{@code s}中索引<i> k + start-n </i>
     * 处的字符。
     *  让<i> n </i>是在执行{@code append}方法之前的这个字符序列的长度。
     * <p>
     *  如果{@code s}是{@code null},那么此方法会追加字符,就像s参数是包含四个字符{@code"null"}的序列。
     * 
     * 
     * @param   s the sequence to append.
     * @param   start   the starting index of the subsequence to be appended.
     * @param   end     the end index of the subsequence to be appended.
     * @return  a reference to this object.
     * @throws     IndexOutOfBoundsException if
     *             {@code start} is negative, or
     *             {@code start} is greater than {@code end} or
     *             {@code end} is greater than {@code s.length()}
     */
    @Override
    public AbstractStringBuilder append(CharSequence s, int start, int end) {
        if (s == null)
            s = "null";
        if ((start < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                "start " + start + ", end " + end + ", s.length() "
                + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        for (int i = start, j = count; i < end; i++, j++)
            value[j] = s.charAt(i);
        count += len;
        return this;
    }

    /**
     * Appends the string representation of the {@code char} array
     * argument to this sequence.
     * <p>
     * The characters of the array argument are appended, in order, to
     * the contents of this sequence. The length of this sequence
     * increases by the length of the argument.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(char[])},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     *  将{@code char}数组参数的字符串表示追加到此序列。
     * <p>
     *  数组参数的字符按顺序附加到此序列的内容。此序列的长度增加参数的长度。
     * <p>
     *  整体效果就好像参数被{@link String#valueOf(char [])}方法转换为字符串,然后该字符串的字符是{@link #append(String)attached)到这个字符序列。
     * 
     * 
     * @param   str   the characters to be appended.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(char[] str) {
        int len = str.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(str, 0, value, count, len);
        count += len;
        return this;
    }

    /**
     * Appends the string representation of a subarray of the
     * {@code char} array argument to this sequence.
     * <p>
     * Characters of the {@code char} array {@code str}, starting at
     * index {@code offset}, are appended, in order, to the contents
     * of this sequence. The length of this sequence increases
     * by the value of {@code len}.
     * <p>
     * The overall effect is exactly as if the arguments were converted
     * to a string by the method {@link String#valueOf(char[],int,int)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     *  将{@code char}数组参数的子数组的字符串表示附加到此序列。
     * <p>
     * 从索引{@code offset}开始的{@code char}数组{@code str}的字符按顺序附加到此序列的内容。该序列的长度增加{@code len}的值。
     * <p>
     *  整体效果就好像参数通过方法{@link String#valueOf(char [],int,int)}转换为字符串,然后该字符串的字符为{@link #append(String)附加}到此字符序列
     * 。
     * 
     * 
     * @param   str      the characters to be appended.
     * @param   offset   the index of the first {@code char} to append.
     * @param   len      the number of {@code char}s to append.
     * @return  a reference to this object.
     * @throws IndexOutOfBoundsException
     *         if {@code offset < 0} or {@code len < 0}
     *         or {@code offset+len > str.length}
     */
    public AbstractStringBuilder append(char str[], int offset, int len) {
        if (len > 0)                // let arraycopy report AIOOBE for len < 0
            ensureCapacityInternal(count + len);
        System.arraycopy(str, offset, value, count, len);
        count += len;
        return this;
    }

    /**
     * Appends the string representation of the {@code boolean}
     * argument to the sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(boolean)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     *  将{@code boolean}参数的字符串表示追加到序列中。
     * <p>
     *  整体效果正如通过方法{@link String#valueOf(boolean)}将参数转换为字符串,然后该字符串的字符为{@link #append(String)attached)到此字符序列。
     * 
     * 
     * @param   b   a {@code boolean}.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(boolean b) {
        if (b) {
            ensureCapacityInternal(count + 4);
            value[count++] = 't';
            value[count++] = 'r';
            value[count++] = 'u';
            value[count++] = 'e';
        } else {
            ensureCapacityInternal(count + 5);
            value[count++] = 'f';
            value[count++] = 'a';
            value[count++] = 'l';
            value[count++] = 's';
            value[count++] = 'e';
        }
        return this;
    }

    /**
     * Appends the string representation of the {@code char}
     * argument to this sequence.
     * <p>
     * The argument is appended to the contents of this sequence.
     * The length of this sequence increases by {@code 1}.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(char)},
     * and the character in that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     *  将{@code char}参数的字符串表示附加到此序列。
     * <p>
     *  参数附加到此序列的内容。此序列的长度增加{@code 1}。
     * <p>
     *  整体效果就好像参数通过方法{@link String#valueOf(char)}转换为字符串,然后该字符串中的字符为{@link #append(String)attached)到此字符序列。
     * 
     * 
     * @param   c   a {@code char}.
     * @return  a reference to this object.
     */
    @Override
    public AbstractStringBuilder append(char c) {
        ensureCapacityInternal(count + 1);
        value[count++] = c;
        return this;
    }

    /**
     * Appends the string representation of the {@code int}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(int)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     *  将{@code int}参数的字符串表示附加到此序列。
     * <p>
     *  整体效果与使用{@link String#valueOf(int)}方法将参数转换为字符串一样,然后该字符串的字符为{@link #append(String)added)到此字符序列。
     * 
     * 
     * @param   i   an {@code int}.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(int i) {
        if (i == Integer.MIN_VALUE) {
            append("-2147483648");
            return this;
        }
        int appendedLength = (i < 0) ? Integer.stringSize(-i) + 1
                                     : Integer.stringSize(i);
        int spaceNeeded = count + appendedLength;
        ensureCapacityInternal(spaceNeeded);
        Integer.getChars(i, spaceNeeded, value);
        count = spaceNeeded;
        return this;
    }

    /**
     * Appends the string representation of the {@code long}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(long)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     * 将{@code long}参数的字符串表示形式附加到此序列。
     * <p>
     *  整体效果正如通过方法{@link String#valueOf(long)}将参数转换为字符串,然后该字符串的字符为{@link #append(String)added}到此字符序列。
     * 
     * 
     * @param   l   a {@code long}.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(long l) {
        if (l == Long.MIN_VALUE) {
            append("-9223372036854775808");
            return this;
        }
        int appendedLength = (l < 0) ? Long.stringSize(-l) + 1
                                     : Long.stringSize(l);
        int spaceNeeded = count + appendedLength;
        ensureCapacityInternal(spaceNeeded);
        Long.getChars(l, spaceNeeded, value);
        count = spaceNeeded;
        return this;
    }

    /**
     * Appends the string representation of the {@code float}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(float)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     *  将{@code float}参数的字符串表示附加到此序列。
     * <p>
     *  整体效果就好像参数通过方法{@link String#valueOf(float)}转换为字符串,然后该字符串的字符是{@link #append(String)added)到此字符序列。
     * 
     * 
     * @param   f   a {@code float}.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(float f) {
        FloatingDecimal.appendTo(f,this);
        return this;
    }

    /**
     * Appends the string representation of the {@code double}
     * argument to this sequence.
     * <p>
     * The overall effect is exactly as if the argument were converted
     * to a string by the method {@link String#valueOf(double)},
     * and the characters of that string were then
     * {@link #append(String) appended} to this character sequence.
     *
     * <p>
     *  将{@code double}参数的字符串表示追加到此序列。
     * <p>
     *  整体效果与使用{@link String#valueOf(double)}方法将参数转换为字符串一样,然后该字符串的字符为{@link #append(String)added}到此字符序列。
     * 
     * 
     * @param   d   a {@code double}.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(double d) {
        FloatingDecimal.appendTo(d,this);
        return this;
    }

    /**
     * Removes the characters in a substring of this sequence.
     * The substring begins at the specified {@code start} and extends to
     * the character at index {@code end - 1} or to the end of the
     * sequence if no such character exists. If
     * {@code start} is equal to {@code end}, no changes are made.
     *
     * <p>
     *  删除此序列的子字符串中的字符。子字符串从指定的{@code start}开始,如果没有这样的字符,则扩展到索引{@code end  -  1}处的字符或序列的末尾。
     * 如果{@code start}等于{@code end},则不会进行任何更改。
     * 
     * 
     * @param      start  The beginning index, inclusive.
     * @param      end    The ending index, exclusive.
     * @return     This object.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             is negative, greater than {@code length()}, or
     *             greater than {@code end}.
     */
    public AbstractStringBuilder delete(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            end = count;
        if (start > end)
            throw new StringIndexOutOfBoundsException();
        int len = end - start;
        if (len > 0) {
            System.arraycopy(value, start+len, value, start, count-end);
            count -= len;
        }
        return this;
    }

    /**
     * Appends the string representation of the {@code codePoint}
     * argument to this sequence.
     *
     * <p> The argument is appended to the contents of this sequence.
     * The length of this sequence increases by
     * {@link Character#charCount(int) Character.charCount(codePoint)}.
     *
     * <p> The overall effect is exactly as if the argument were
     * converted to a {@code char} array by the method
     * {@link Character#toChars(int)} and the character in that array
     * were then {@link #append(char[]) appended} to this character
     * sequence.
     *
     * <p>
     *  将{@code codePoint}参数的字符串表示追加到此序列。
     * 
     *  <p>参数附加到此序列的内容。此序列的长度增加{@link Character#charCount(int)Character.charCount(codePoint)}。
     * 
     * <p>整体效果与使用{@link Character#toChars(int)}方法将参数转换为{@code char}数组一样,该数组中的字符为{@link #append(char [])attached}
     * 到此字符序列。
     * 
     * 
     * @param   codePoint   a Unicode code point
     * @return  a reference to this object.
     * @exception IllegalArgumentException if the specified
     * {@code codePoint} isn't a valid Unicode code point
     */
    public AbstractStringBuilder appendCodePoint(int codePoint) {
        final int count = this.count;

        if (Character.isBmpCodePoint(codePoint)) {
            ensureCapacityInternal(count + 1);
            value[count] = (char) codePoint;
            this.count = count + 1;
        } else if (Character.isValidCodePoint(codePoint)) {
            ensureCapacityInternal(count + 2);
            Character.toSurrogates(codePoint, value, count);
            this.count = count + 2;
        } else {
            throw new IllegalArgumentException();
        }
        return this;
    }

    /**
     * Removes the {@code char} at the specified position in this
     * sequence. This sequence is shortened by one {@code char}.
     *
     * <p>Note: If the character at the given index is a supplementary
     * character, this method does not remove the entire character. If
     * correct handling of supplementary characters is required,
     * determine the number of {@code char}s to remove by calling
     * {@code Character.charCount(thisSequence.codePointAt(index))},
     * where {@code thisSequence} is this sequence.
     *
     * <p>
     *  删除此序列中指定位置处的{@code char}。此序列缩短一个{@code char}。
     * 
     *  <p>注意：如果给定索引处的字符是补充字符,则此方法不会删除整个字符。
     * 如果需要正确处理补充字符,请通过调用{@code Character.charCount(thisSequence.codePointAt(index))}来确定要删除的{@code char}的数量,
     * 其中{@code thisSequence}是此序列。
     *  <p>注意：如果给定索引处的字符是补充字符,则此方法不会删除整个字符。
     * 
     * 
     * @param       index  Index of {@code char} to remove
     * @return      This object.
     * @throws      StringIndexOutOfBoundsException  if the {@code index}
     *              is negative or greater than or equal to
     *              {@code length()}.
     */
    public AbstractStringBuilder deleteCharAt(int index) {
        if ((index < 0) || (index >= count))
            throw new StringIndexOutOfBoundsException(index);
        System.arraycopy(value, index+1, value, index, count-index-1);
        count--;
        return this;
    }

    /**
     * Replaces the characters in a substring of this sequence
     * with characters in the specified {@code String}. The substring
     * begins at the specified {@code start} and extends to the character
     * at index {@code end - 1} or to the end of the
     * sequence if no such character exists. First the
     * characters in the substring are removed and then the specified
     * {@code String} is inserted at {@code start}. (This
     * sequence will be lengthened to accommodate the
     * specified String if necessary.)
     *
     * <p>
     *  将此序列的子字符串中的字符替换为指定的{@code String}中的字符。
     * 子字符串从指定的{@code start}开始,如果没有这样的字符,则扩展到索引{@code end  -  1}处的字符或序列的末尾。
     * 首先删除子字符串中的字符,然后在{@code start}插入指定的{@code String}。 (如果需要,将延长此序列以容纳指定的字符串。)。
     * 
     * 
     * @param      start    The beginning index, inclusive.
     * @param      end      The ending index, exclusive.
     * @param      str   String that will replace previous contents.
     * @return     This object.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             is negative, greater than {@code length()}, or
     *             greater than {@code end}.
     */
    public AbstractStringBuilder replace(int start, int end, String str) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (start > count)
            throw new StringIndexOutOfBoundsException("start > length()");
        if (start > end)
            throw new StringIndexOutOfBoundsException("start > end");

        if (end > count)
            end = count;
        int len = str.length();
        int newCount = count + len - (end - start);
        ensureCapacityInternal(newCount);

        System.arraycopy(value, end, value, start + len, count - end);
        str.getChars(value, start);
        count = newCount;
        return this;
    }

    /**
     * Returns a new {@code String} that contains a subsequence of
     * characters currently contained in this character sequence. The
     * substring begins at the specified index and extends to the end of
     * this sequence.
     *
     * <p>
     *  返回一个新的{@code String},其中包含此字符序列中当前包含的字符的子序列。子字符串从指定的索引开始,并延伸到此序列的结尾。
     * 
     * 
     * @param      start    The beginning index, inclusive.
     * @return     The new string.
     * @throws     StringIndexOutOfBoundsException  if {@code start} is
     *             less than zero, or greater than the length of this object.
     */
    public String substring(int start) {
        return substring(start, count);
    }

    /**
     * Returns a new character sequence that is a subsequence of this sequence.
     *
     * <p> An invocation of this method of the form
     *
     * <pre>{@code
     * sb.subSequence(begin,&nbsp;end)}</pre>
     *
     * behaves in exactly the same way as the invocation
     *
     * <pre>{@code
     * sb.substring(begin,&nbsp;end)}</pre>
     *
     * This method is provided so that this class can
     * implement the {@link CharSequence} interface.
     *
     * <p>
     *  返回作为此序列的子序列的新字符序列。
     * 
     * <p>表单的此方法的调用
     * 
     *  <pre> {@ code sb.subSequence(begin,&nbsp; end)} </pre>
     * 
     *  其行为与调用的方式完全相同
     * 
     *  <pre> {@ code sb.substring(begin,&nbsp; end)} </pre>
     * 
     *  提供此方法,以便此类可以实现{@link CharSequence}接口。
     * 
     * 
     * @param      start   the start index, inclusive.
     * @param      end     the end index, exclusive.
     * @return     the specified subsequence.
     *
     * @throws  IndexOutOfBoundsException
     *          if {@code start} or {@code end} are negative,
     *          if {@code end} is greater than {@code length()},
     *          or if {@code start} is greater than {@code end}
     * @spec JSR-51
     */
    @Override
    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }

    /**
     * Returns a new {@code String} that contains a subsequence of
     * characters currently contained in this sequence. The
     * substring begins at the specified {@code start} and
     * extends to the character at index {@code end - 1}.
     *
     * <p>
     *  返回一个新的{@code String},其中包含此序列中当前包含的字符的子序列。子字符串从指定的{@code start}开始,并延伸到索引{@code end  -  1}处的字符。
     * 
     * 
     * @param      start    The beginning index, inclusive.
     * @param      end      The ending index, exclusive.
     * @return     The new string.
     * @throws     StringIndexOutOfBoundsException  if {@code start}
     *             or {@code end} are negative or greater than
     *             {@code length()}, or {@code start} is
     *             greater than {@code end}.
     */
    public String substring(int start, int end) {
        if (start < 0)
            throw new StringIndexOutOfBoundsException(start);
        if (end > count)
            throw new StringIndexOutOfBoundsException(end);
        if (start > end)
            throw new StringIndexOutOfBoundsException(end - start);
        return new String(value, start, end - start);
    }

    /**
     * Inserts the string representation of a subarray of the {@code str}
     * array argument into this sequence. The subarray begins at the
     * specified {@code offset} and extends {@code len} {@code char}s.
     * The characters of the subarray are inserted into this sequence at
     * the position indicated by {@code index}. The length of this
     * sequence increases by {@code len} {@code char}s.
     *
     * <p>
     *  将{@code str}数组参数的子数组的字符串表示插入到此序列中。子阵列从指定的{@code offset}开始,并扩展{@code len} {@code char}。
     * 子序列的字符插入到此序列中由{@code index}指示的位置。此序列的长度增加{@code len} {@code char}。
     * 
     * 
     * @param      index    position at which to insert subarray.
     * @param      str       A {@code char} array.
     * @param      offset   the index of the first {@code char} in subarray to
     *             be inserted.
     * @param      len      the number of {@code char}s in the subarray to
     *             be inserted.
     * @return     This object
     * @throws     StringIndexOutOfBoundsException  if {@code index}
     *             is negative or greater than {@code length()}, or
     *             {@code offset} or {@code len} are negative, or
     *             {@code (offset+len)} is greater than
     *             {@code str.length}.
     */
    public AbstractStringBuilder insert(int index, char[] str, int offset,
                                        int len)
    {
        if ((index < 0) || (index > length()))
            throw new StringIndexOutOfBoundsException(index);
        if ((offset < 0) || (len < 0) || (offset > str.length - len))
            throw new StringIndexOutOfBoundsException(
                "offset " + offset + ", len " + len + ", str.length "
                + str.length);
        ensureCapacityInternal(count + len);
        System.arraycopy(value, index, value, index + len, count - index);
        System.arraycopy(str, offset, value, index, len);
        count += len;
        return this;
    }

    /**
     * Inserts the string representation of the {@code Object}
     * argument into this character sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(Object)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     *  将{@code Object}参数的字符串表示插入到此字符序列中。
     * <p>
     *  总体效果就好像第二个参数通过方法{@link String#valueOf(Object)}转换为字符串,然后该字符串的字符为{@link #insert(int,String)inserted}这个
     * 字符序列在指定的偏移量。
     * <p>
     *  {@code offset}参数必须大于或等于{@code 0},且小于或等于此序列的{@linkplain #length()length}。
     * 
     * 
     * @param      offset   the offset.
     * @param      obj      an {@code Object}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, Object obj) {
        return insert(offset, String.valueOf(obj));
    }

    /**
     * Inserts the string into this character sequence.
     * <p>
     * The characters of the {@code String} argument are inserted, in
     * order, into this sequence at the indicated offset, moving up any
     * characters originally above that position and increasing the length
     * of this sequence by the length of the argument. If
     * {@code str} is {@code null}, then the four characters
     * {@code "null"} are inserted into this sequence.
     * <p>
     * The character at index <i>k</i> in the new character sequence is
     * equal to:
     * <ul>
     * <li>the character at index <i>k</i> in the old character sequence, if
     * <i>k</i> is less than {@code offset}
     * <li>the character at index <i>k</i>{@code -offset} in the
     * argument {@code str}, if <i>k</i> is not less than
     * {@code offset} but is less than {@code offset+str.length()}
     * <li>the character at index <i>k</i>{@code -str.length()} in the
     * old character sequence, if <i>k</i> is not less than
     * {@code offset+str.length()}
     * </ul><p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     *  将字符串插入到此字符序列中。
     * <p>
     * {@code String}参数的字符按顺序插入到此序列中的指定偏移处,向上移动原先位于该位置上方的任何字符,并将该序列的长度增加参数的长度。
     * 如果{@code str}是{@code null},那么将在此序列中插入四个字符{@code"null"}。
     * <p>
     *  新字符序列中索引<k>的字符等于：
     * <ul>
     *  <li>旧字符序列中索引<i> k处的字符,如果<i> k </i>小于{@code offset} <li>索引<k> / i>在参数{@code str}中的{@ code -offset},如果
     * <i> k </i>不小于{@code offset},但小于{@code offset + str.length } <li>旧字符序列中索引<i> k </i> {@ code -str.length()}
     * 处的字符,如果<i> k < str.length()} </ul> <p> {@code offset}参数必须大于或等于{@code 0},且小于或等于{@linkplain #length()length}
     * 这个序列。
     * 
     * 
     * @param      offset   the offset.
     * @param      str      a string.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, String str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        if (str == null)
            str = "null";
        int len = str.length();
        ensureCapacityInternal(count + len);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        str.getChars(value, offset);
        count += len;
        return this;
    }

    /**
     * Inserts the string representation of the {@code char} array
     * argument into this sequence.
     * <p>
     * The characters of the array argument are inserted into the
     * contents of this sequence at the position indicated by
     * {@code offset}. The length of this sequence increases by
     * the length of the argument.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(char[])},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     *  将{@code char}数组参数的字符串表示插入到此序列中。
     * <p>
     *  数组参数的字符插入到此序列的内容中由{@code offset}指示的位置。此序列的长度增加参数的长度。
     * <p>
     * 整体效果就好像第二个参数通过方法{@link String#valueOf(char [])}转换为字符串,然后该字符串的字符是{@link #insert(int,String)inserted }转
     * 换为指定偏移量的字符序列。
     * <p>
     *  {@code offset}参数必须大于或等于{@code 0},且小于或等于此序列的{@linkplain #length()length}。
     * 
     * 
     * @param      offset   the offset.
     * @param      str      a character array.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, char[] str) {
        if ((offset < 0) || (offset > length()))
            throw new StringIndexOutOfBoundsException(offset);
        int len = str.length;
        ensureCapacityInternal(count + len);
        System.arraycopy(value, offset, value, offset + len, count - offset);
        System.arraycopy(str, 0, value, offset, len);
        count += len;
        return this;
    }

    /**
     * Inserts the specified {@code CharSequence} into this sequence.
     * <p>
     * The characters of the {@code CharSequence} argument are inserted,
     * in order, into this sequence at the indicated offset, moving up
     * any characters originally above that position and increasing the length
     * of this sequence by the length of the argument s.
     * <p>
     * The result of this method is exactly the same as if it were an
     * invocation of this object's
     * {@link #insert(int,CharSequence,int,int) insert}(dstOffset, s, 0, s.length())
     * method.
     *
     * <p>If {@code s} is {@code null}, then the four characters
     * {@code "null"} are inserted into this sequence.
     *
     * <p>
     *  在此序列中插入指定的{@code CharSequence}。
     * <p>
     *  {@code CharSequence}参数的字符按顺序插入到此序列中的指定偏移处,向上移动任何最初位于该位置上方的字符,并将该序列的长度增加参数s的长度。
     * <p>
     *  此方法的结果与调用此对象的{@link #insert(int,CharSequence,int,int)insert}(dstOffset,s,0,s.length())方法完全相同。
     * 
     *  <p>如果{@code s}是{@code null},则将在此序列中插入四个字符{@code"null"}。
     * 
     * 
     * @param      dstOffset   the offset.
     * @param      s the sequence to be inserted
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int dstOffset, CharSequence s) {
        if (s == null)
            s = "null";
        if (s instanceof String)
            return this.insert(dstOffset, (String)s);
        return this.insert(dstOffset, s, 0, s.length());
    }

    /**
     * Inserts a subsequence of the specified {@code CharSequence} into
     * this sequence.
     * <p>
     * The subsequence of the argument {@code s} specified by
     * {@code start} and {@code end} are inserted,
     * in order, into this sequence at the specified destination offset, moving
     * up any characters originally above that position. The length of this
     * sequence is increased by {@code end - start}.
     * <p>
     * The character at index <i>k</i> in this sequence becomes equal to:
     * <ul>
     * <li>the character at index <i>k</i> in this sequence, if
     * <i>k</i> is less than {@code dstOffset}
     * <li>the character at index <i>k</i>{@code +start-dstOffset} in
     * the argument {@code s}, if <i>k</i> is greater than or equal to
     * {@code dstOffset} but is less than {@code dstOffset+end-start}
     * <li>the character at index <i>k</i>{@code -(end-start)} in this
     * sequence, if <i>k</i> is greater than or equal to
     * {@code dstOffset+end-start}
     * </ul><p>
     * The {@code dstOffset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     * <p>The start argument must be nonnegative, and not greater than
     * {@code end}.
     * <p>The end argument must be greater than or equal to
     * {@code start}, and less than or equal to the length of s.
     *
     * <p>If {@code s} is {@code null}, then this method inserts
     * characters as if the s parameter was a sequence containing the four
     * characters {@code "null"}.
     *
     * <p>
     *  在此序列中插入指定{@code CharSequence}的子序列。
     * <p>
     *  由{@code start}和{@code end}指定的参数{@code s}的子序列按顺序插入到此序列中指定的目标偏移处,向上移动最初在该位置上方的任何字符。
     * 该序列的长度增加{@code end-start}。
     * <p>
     *  在该序列中的索引k k处的字符变为等于：
     * <ul>
     * <li>在此序列中的索引<i> k处的字符,如果<i> k </i>小于{@code dstOffset} <li>索引<k>在参数{@code s}中的{@ code + start-dstOffset}
     * ,如果<i> k </i>大于或等于{@code dstOffset},但小于{@code dstOffset + end-start }如果<i> k </i>大于或等于{@code dstOffset +}
     * ,则在该序列中的索引<k> {@ code-(end-start) end-start} </ul> <p> {@code dstOffset}参数必须大于或等于{@code 0},且小于或等于此序列的
     * {@linkplain #length()length} 。
     *  <p> start参数必须为非负数,且不大于{@code end}。 <p>结束参数必须大于或等于{@code start},且小于或等于s的长度。
     * 
     *  <p>如果{@code s}是{@code null},则此方法将插入字符,就像s参数是包含四个字符{@code"null"}的序列。
     * 
     * 
     * @param      dstOffset   the offset in this sequence.
     * @param      s       the sequence to be inserted.
     * @param      start   the starting index of the subsequence to be inserted.
     * @param      end     the end index of the subsequence to be inserted.
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if {@code dstOffset}
     *             is negative or greater than {@code this.length()}, or
     *              {@code start} or {@code end} are negative, or
     *              {@code start} is greater than {@code end} or
     *              {@code end} is greater than {@code s.length()}
     */
     public AbstractStringBuilder insert(int dstOffset, CharSequence s,
                                         int start, int end) {
        if (s == null)
            s = "null";
        if ((dstOffset < 0) || (dstOffset > this.length()))
            throw new IndexOutOfBoundsException("dstOffset "+dstOffset);
        if ((start < 0) || (end < 0) || (start > end) || (end > s.length()))
            throw new IndexOutOfBoundsException(
                "start " + start + ", end " + end + ", s.length() "
                + s.length());
        int len = end - start;
        ensureCapacityInternal(count + len);
        System.arraycopy(value, dstOffset, value, dstOffset + len,
                         count - dstOffset);
        for (int i=start; i<end; i++)
            value[dstOffset++] = s.charAt(i);
        count += len;
        return this;
    }

    /**
     * Inserts the string representation of the {@code boolean}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(boolean)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     *  将{@code boolean}参数的字符串表示插入到此序列中。
     * <p>
     *  总体效果就好像第二个参数通过方法{@link String#valueOf(boolean)}转换为字符串,然后该字符串的字符为{@link #insert(int,String)inserted}这
     * 个字符序列在指定的偏移量。
     * <p>
     *  {@code offset}参数必须大于或等于{@code 0},且小于或等于此序列的{@linkplain #length()length}。
     * 
     * 
     * @param      offset   the offset.
     * @param      b        a {@code boolean}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, boolean b) {
        return insert(offset, String.valueOf(b));
    }

    /**
     * Inserts the string representation of the {@code char}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(char)},
     * and the character in that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     * 将{@code char}参数的字符串表示插入到此序列中。
     * <p>
     *  总体效果就好像第二个参数通过方法{@link String#valueOf(char)}转换为字符串,然后该字符串中的字符为{@link #insert(int,String)inserted}这个字
     * 符序列在指定的偏移量。
     * <p>
     *  {@code offset}参数必须大于或等于{@code 0},且小于或等于此序列的{@linkplain #length()length}。
     * 
     * 
     * @param      offset   the offset.
     * @param      c        a {@code char}.
     * @return     a reference to this object.
     * @throws     IndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, char c) {
        ensureCapacityInternal(count + 1);
        System.arraycopy(value, offset, value, offset + 1, count - offset);
        value[offset] = c;
        count += 1;
        return this;
    }

    /**
     * Inserts the string representation of the second {@code int}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(int)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     *  将第二个{@code int}参数的字符串表示插入到此序列中。
     * <p>
     *  整体效果就好像第二个参数通过方法{@link String#valueOf(int)}转换为字符串,然后该字符串的字符是{@link #insert(int,String)inserted}这个字符序
     * 列在指定的偏移量。
     * <p>
     *  {@code offset}参数必须大于或等于{@code 0},且小于或等于此序列的{@linkplain #length()length}。
     * 
     * 
     * @param      offset   the offset.
     * @param      i        an {@code int}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, int i) {
        return insert(offset, String.valueOf(i));
    }

    /**
     * Inserts the string representation of the {@code long}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(long)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     *  将{@code long}参数的字符串表示插入到此序列中。
     * <p>
     *  整体效果就好像第二个参数通过方法{@link String#valueOf(long)}转换为字符串,然后该字符串的字符是{@link #insert(int,String)inserted}这个字符
     * 序列在指定的偏移量。
     * <p>
     *  {@code offset}参数必须大于或等于{@code 0},且小于或等于此序列的{@linkplain #length()length}。
     * 
     * 
     * @param      offset   the offset.
     * @param      l        a {@code long}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, long l) {
        return insert(offset, String.valueOf(l));
    }

    /**
     * Inserts the string representation of the {@code float}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(float)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     * 将{@code float}参数的字符串表示插入到此序列中。
     * <p>
     *  整体效果就好像第二个参数通过方法{@link String#valueOf(float)}转换为字符串,然后该字符串的字符是{@link #insert(int,String)inserted}这个字
     * 符序列在指定的偏移量。
     * <p>
     *  {@code offset}参数必须大于或等于{@code 0},且小于或等于此序列的{@linkplain #length()length}。
     * 
     * 
     * @param      offset   the offset.
     * @param      f        a {@code float}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, float f) {
        return insert(offset, String.valueOf(f));
    }

    /**
     * Inserts the string representation of the {@code double}
     * argument into this sequence.
     * <p>
     * The overall effect is exactly as if the second argument were
     * converted to a string by the method {@link String#valueOf(double)},
     * and the characters of that string were then
     * {@link #insert(int,String) inserted} into this character
     * sequence at the indicated offset.
     * <p>
     * The {@code offset} argument must be greater than or equal to
     * {@code 0}, and less than or equal to the {@linkplain #length() length}
     * of this sequence.
     *
     * <p>
     *  将{@code double}参数的字符串表示插入到此序列中。
     * <p>
     *  总体效果就好像第二个参数通过方法{@link String#valueOf(double)}转换为字符串,然后该字符串的字符是{@link #insert(int,String)inserted}这个
     * 字符序列在指定的偏移量。
     * <p>
     *  {@code offset}参数必须大于或等于{@code 0},且小于或等于此序列的{@linkplain #length()length}。
     * 
     * 
     * @param      offset   the offset.
     * @param      d        a {@code double}.
     * @return     a reference to this object.
     * @throws     StringIndexOutOfBoundsException  if the offset is invalid.
     */
    public AbstractStringBuilder insert(int offset, double d) {
        return insert(offset, String.valueOf(d));
    }

    /**
     * Returns the index within this string of the first occurrence of the
     * specified substring. The integer returned is the smallest value
     * <i>k</i> such that:
     * <pre>{@code
     * this.toString().startsWith(str, <i>k</i>)
     * }</pre>
     * is {@code true}.
     *
     * <p>
     *  返回此字符串中指定子字符串第一次出现的索引。返回的整数是最小值<i> k </i>,使得：<pre> {@ code this.toString()。
     * startsWith(str,<k>)} </@code true}。
     * 
     * 
     * @param   str   any string.
     * @return  if the string argument occurs as a substring within this
     *          object, then the index of the first character of the first
     *          such substring is returned; if it does not occur as a
     *          substring, {@code -1} is returned.
     */
    public int indexOf(String str) {
        return indexOf(str, 0);
    }

    /**
     * Returns the index within this string of the first occurrence of the
     * specified substring, starting at the specified index.  The integer
     * returned is the smallest value {@code k} for which:
     * <pre>{@code
     *     k >= Math.min(fromIndex, this.length()) &&
     *                   this.toString().startsWith(str, k)
     * }</pre>
     * If no such value of <i>k</i> exists, then -1 is returned.
     *
     * <p>
     * 返回指定子字符串第一次出现的此字符串中的索引,从指定的索引开始。
     * 返回的整数是最小的值{@code k},其中：<pre> {@ code k> = Math.min(fromIndex,this.length())&& this.toString()。
     * startsWith(str,k)} </pre>如果不存在这样的<i> k值,则返回-1。
     * 
     * 
     * @param   str         the substring for which to search.
     * @param   fromIndex   the index from which to start the search.
     * @return  the index within this string of the first occurrence of the
     *          specified substring, starting at the specified index.
     */
    public int indexOf(String str, int fromIndex) {
        return String.indexOf(value, 0, count, str, fromIndex);
    }

    /**
     * Returns the index within this string of the rightmost occurrence
     * of the specified substring.  The rightmost empty string "" is
     * considered to occur at the index value {@code this.length()}.
     * The returned index is the largest value <i>k</i> such that
     * <pre>{@code
     * this.toString().startsWith(str, k)
     * }</pre>
     * is true.
     *
     * <p>
     *  返回此字符串中指定子字符串最右侧出现的索引。最右边的空字符串""被认为发生在索引值{@code this.length()}。
     * 返回的索引是最大值<k>,使得<pre> {@ code this.toString()。startsWith(str,k)} </pre>是真的。
     * 
     * 
     * @param   str   the substring to search for.
     * @return  if the string argument occurs one or more times as a substring
     *          within this object, then the index of the first character of
     *          the last such substring is returned. If it does not occur as
     *          a substring, {@code -1} is returned.
     */
    public int lastIndexOf(String str) {
        return lastIndexOf(str, count);
    }

    /**
     * Returns the index within this string of the last occurrence of the
     * specified substring. The integer returned is the largest value <i>k</i>
     * such that:
     * <pre>{@code
     *     k <= Math.min(fromIndex, this.length()) &&
     *                   this.toString().startsWith(str, k)
     * }</pre>
     * If no such value of <i>k</i> exists, then -1 is returned.
     *
     * <p>
     *  返回此字符串中指定子字符串的最后一次出现的索引。
     * 返回的整数是最大值<i> k </i>,使得：<pre> {@ code k <= Math.min(fromIndex,this.length())&& this.toString()。
     * startsWith(str, k)} </pre>如果不存在这样的<k>的值,则返回-1。
     * 
     * 
     * @param   str         the substring to search for.
     * @param   fromIndex   the index to start the search from.
     * @return  the index within this sequence of the last occurrence of the
     *          specified substring.
     */
    public int lastIndexOf(String str, int fromIndex) {
        return String.lastIndexOf(value, 0, count, str, fromIndex);
    }

    /**
     * Causes this character sequence to be replaced by the reverse of
     * the sequence. If there are any surrogate pairs included in the
     * sequence, these are treated as single characters for the
     * reverse operation. Thus, the order of the high-low surrogates
     * is never reversed.
     *
     * Let <i>n</i> be the character length of this character sequence
     * (not the length in {@code char} values) just prior to
     * execution of the {@code reverse} method. Then the
     * character at index <i>k</i> in the new character sequence is
     * equal to the character at index <i>n-k-1</i> in the old
     * character sequence.
     *
     * <p>Note that the reverse operation may result in producing
     * surrogate pairs that were unpaired low-surrogates and
     * high-surrogates before the operation. For example, reversing
     * "\u005CuDC00\u005CuD800" produces "\u005CuD800\u005CuDC00" which is
     * a valid surrogate pair.
     *
     * <p>
     *  导致此字符序列被序列的反向替换。如果序列中包含任何替代对,则这些对象被视为单个字符,用于反向操作。因此,高低代理的顺序从不逆转。
     * 
     * 在执行{@code reverse}方法之前,让<i> n </i>是此字符序列的字符长度(而不是{@code char}值中的长度)。
     * 然后,新字符序列中索引<k>处的字符等于旧字符序列中索引n-k-1处的字符。
     * 
     *  <p>请注意,反向操作可能导致在操作之前生成未配对的低代理和高代理的替代对。
     * 例如,颠倒"\ u005CuDC00 \ u005CuD800"会生成"\ u005CuD800 \ u005CuDC00",这是一个有效的代理对。
     * 
     * 
     * @return  a reference to this object.
     */
    public AbstractStringBuilder reverse() {
        boolean hasSurrogates = false;
        int n = count - 1;
        for (int j = (n-1) >> 1; j >= 0; j--) {
            int k = n - j;
            char cj = value[j];
            char ck = value[k];
            value[j] = ck;
            value[k] = cj;
            if (Character.isSurrogate(cj) ||
                Character.isSurrogate(ck)) {
                hasSurrogates = true;
            }
        }
        if (hasSurrogates) {
            reverseAllValidSurrogatePairs();
        }
        return this;
    }

    /** Outlined helper method for reverse() */
    private void reverseAllValidSurrogatePairs() {
        for (int i = 0; i < count - 1; i++) {
            char c2 = value[i];
            if (Character.isLowSurrogate(c2)) {
                char c1 = value[i + 1];
                if (Character.isHighSurrogate(c1)) {
                    value[i++] = c1;
                    value[i] = c2;
                }
            }
        }
    }

    /**
     * Returns a string representing the data in this sequence.
     * A new {@code String} object is allocated and initialized to
     * contain the character sequence currently represented by this
     * object. This {@code String} is then returned. Subsequent
     * changes to this sequence do not affect the contents of the
     * {@code String}.
     *
     * <p>
     * 
     * @return  a string representation of this sequence of characters.
     */
    @Override
    public abstract String toString();

    /**
     * Needed by {@code String} for the contentEquals method.
     * <p>
     *  返回表示此序列中的数据的字符串。一个新的{@code String}对象被分配并初始化为包含此对象当前表示的字符序列。然后返回此{@code String}。
     * 对此序列的后续更改不会影响{@code String}的内容。
     * 
     */
    final char[] getValue() {
        return value;
    }

}
