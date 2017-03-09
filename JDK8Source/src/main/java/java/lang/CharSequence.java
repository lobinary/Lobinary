/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * A <tt>CharSequence</tt> is a readable sequence of <code>char</code> values. This
 * interface provides uniform, read-only access to many different kinds of
 * <code>char</code> sequences.
 * A <code>char</code> value represents a character in the <i>Basic
 * Multilingual Plane (BMP)</i> or a surrogate. Refer to <a
 * href="Character.html#unicode">Unicode Character Representation</a> for details.
 *
 * <p> This interface does not refine the general contracts of the {@link
 * java.lang.Object#equals(java.lang.Object) equals} and {@link
 * java.lang.Object#hashCode() hashCode} methods.  The result of comparing two
 * objects that implement <tt>CharSequence</tt> is therefore, in general,
 * undefined.  Each object may be implemented by a different class, and there
 * is no guarantee that each class will be capable of testing its instances
 * for equality with those of the other.  It is therefore inappropriate to use
 * arbitrary <tt>CharSequence</tt> instances as elements in a set or as keys in
 * a map. </p>
 *
 * <p>
 *  <tt> CharSequence </tt>是<code> char </code>值的可读序列。此接口提供对许多不同种类的<code> char </code>序列的统一,只读访问。
 *  <code> char </code>值表示<b>基本多语言平面(BMP)</i>中的字符或代理。
 * 有关详细信息,请参阅<a href="Character.html#unicode"> Unicode字符表示</a>。
 * 
 *  <p>此接口不会细化{@link java.lang.Object#equals(java.lang.Object)equals}和{@link java.lang.Object#hashCode()hashCode}
 * 方法的一般合同。
 * 因此,比较实现<tt> CharSequence </tt>的两个对象的结果通常是未定义的。每个对象可以由不同的类实现,并且不能保证每个类将能够测试其实例以与其他类的实例相等。
 * 因此,不宜使用任意的<tt> CharSequence </tt>实例作为集合中的元素或作为地图中的键。 </p>。
 * 
 * 
 * @author Mike McCloskey
 * @since 1.4
 * @spec JSR-51
 */

public interface CharSequence {

    /**
     * Returns the length of this character sequence.  The length is the number
     * of 16-bit <code>char</code>s in the sequence.
     *
     * <p>
     *  返回此字符序列的长度。长度是序列中16位<code> char </code>的数量。
     * 
     * 
     * @return  the number of <code>char</code>s in this sequence
     */
    int length();

    /**
     * Returns the <code>char</code> value at the specified index.  An index ranges from zero
     * to <tt>length() - 1</tt>.  The first <code>char</code> value of the sequence is at
     * index zero, the next at index one, and so on, as for array
     * indexing.
     *
     * <p>If the <code>char</code> value specified by the index is a
     * <a href="{@docRoot}/java/lang/Character.html#unicode">surrogate</a>, the surrogate
     * value is returned.
     *
     * <p>
     *  返回指定索引处的<code> char </code>值。索引范围从零到<tt> length() -  1 </tt>。
     * 序列的第一个<code> char </code>值为索引0,下一个为索引1,依此类推,如同数组索引一样。
     * 
     * <p>如果索引指定的<code> char </code>值为<a href="{@docRoot}/java/lang/Character.html#unicode">代理</a>,代理值返回。
     * 
     * 
     * @param   index   the index of the <code>char</code> value to be returned
     *
     * @return  the specified <code>char</code> value
     *
     * @throws  IndexOutOfBoundsException
     *          if the <tt>index</tt> argument is negative or not less than
     *          <tt>length()</tt>
     */
    char charAt(int index);

    /**
     * Returns a <code>CharSequence</code> that is a subsequence of this sequence.
     * The subsequence starts with the <code>char</code> value at the specified index and
     * ends with the <code>char</code> value at index <tt>end - 1</tt>.  The length
     * (in <code>char</code>s) of the
     * returned sequence is <tt>end - start</tt>, so if <tt>start == end</tt>
     * then an empty sequence is returned.
     *
     * <p>
     *  返回一个<code> CharSequence </code>,它是此序列的子序列。
     * 子序列以指定索引处的<code> char </code>值开始,并以索引<tt> end-1 </tt>处的<code> char </code>值结束。
     * 返回序列的长度(在<code> char </code>中)为<tt> end-start </tt>,因此如果<tt> start == end </tt>,则返回空序列。
     * 
     * 
     * @param   start   the start index, inclusive
     * @param   end     the end index, exclusive
     *
     * @return  the specified subsequence
     *
     * @throws  IndexOutOfBoundsException
     *          if <tt>start</tt> or <tt>end</tt> are negative,
     *          if <tt>end</tt> is greater than <tt>length()</tt>,
     *          or if <tt>start</tt> is greater than <tt>end</tt>
     */
    CharSequence subSequence(int start, int end);

    /**
     * Returns a string containing the characters in this sequence in the same
     * order as this sequence.  The length of the string will be the length of
     * this sequence.
     *
     * <p>
     *  以与此序列相同的顺序返回包含此序列中的字符的字符串。字符串的长度将是此序列的长度。
     * 
     * 
     * @return  a string consisting of exactly this sequence of characters
     */
    public String toString();

    /**
     * Returns a stream of {@code int} zero-extending the {@code char} values
     * from this sequence.  Any char which maps to a <a
     * href="{@docRoot}/java/lang/Character.html#unicode">surrogate code
     * point</a> is passed through uninterpreted.
     *
     * <p>If the sequence is mutated while the stream is being read, the
     * result is undefined.
     *
     * <p>
     *  返回从此序列中{@code int}零扩展{@code char}值的流。
     * 映射到<a href="{@docRoot}/java/lang/Character.html#unicode">代理代码点</a>的任何字符都将通过未解析的字符传递。
     * 
     *  <p>如果在读取流时序列发生了突变,则结果未定义。
     * 
     * 
     * @return an IntStream of char values from this sequence
     * @since 1.8
     */
    public default IntStream chars() {
        class CharIterator implements PrimitiveIterator.OfInt {
            int cur = 0;

            public boolean hasNext() {
                return cur < length();
            }

            public int nextInt() {
                if (hasNext()) {
                    return charAt(cur++);
                } else {
                    throw new NoSuchElementException();
                }
            }

            @Override
            public void forEachRemaining(IntConsumer block) {
                for (; cur < length(); cur++) {
                    block.accept(charAt(cur));
                }
            }
        }

        return StreamSupport.intStream(() ->
                Spliterators.spliterator(
                        new CharIterator(),
                        length(),
                        Spliterator.ORDERED),
                Spliterator.SUBSIZED | Spliterator.SIZED | Spliterator.ORDERED,
                false);
    }

    /**
     * Returns a stream of code point values from this sequence.  Any surrogate
     * pairs encountered in the sequence are combined as if by {@linkplain
     * Character#toCodePoint Character.toCodePoint} and the result is passed
     * to the stream. Any other code units, including ordinary BMP characters,
     * unpaired surrogates, and undefined code units, are zero-extended to
     * {@code int} values which are then passed to the stream.
     *
     * <p>If the sequence is mutated while the stream is being read, the result
     * is undefined.
     *
     * <p>
     *  从此序列返回代码点值的流。
     * 在序列中遇到的任何代理对被组合,如同通过{@linkplain Character#toCodePoint Character.toCodePoint},并且结果被传递到流。
     * 任何其他代码单元,包括普通的BMP字符,不成对的代理和未定义的代码单元,被零扩展为{@code int}值,然后传递给流。
     * 
     * @return an IntStream of Unicode code points from this sequence
     * @since 1.8
     */
    public default IntStream codePoints() {
        class CodePointIterator implements PrimitiveIterator.OfInt {
            int cur = 0;

            @Override
            public void forEachRemaining(IntConsumer block) {
                final int length = length();
                int i = cur;
                try {
                    while (i < length) {
                        char c1 = charAt(i++);
                        if (!Character.isHighSurrogate(c1) || i >= length) {
                            block.accept(c1);
                        } else {
                            char c2 = charAt(i);
                            if (Character.isLowSurrogate(c2)) {
                                i++;
                                block.accept(Character.toCodePoint(c1, c2));
                            } else {
                                block.accept(c1);
                            }
                        }
                    }
                } finally {
                    cur = i;
                }
            }

            public boolean hasNext() {
                return cur < length();
            }

            public int nextInt() {
                final int length = length();

                if (cur >= length) {
                    throw new NoSuchElementException();
                }
                char c1 = charAt(cur++);
                if (Character.isHighSurrogate(c1) && cur < length) {
                    char c2 = charAt(cur);
                    if (Character.isLowSurrogate(c2)) {
                        cur++;
                        return Character.toCodePoint(c1, c2);
                    }
                }
                return c1;
            }
        }

        return StreamSupport.intStream(() ->
                Spliterators.spliteratorUnknownSize(
                        new CodePointIterator(),
                        Spliterator.ORDERED),
                Spliterator.ORDERED,
                false);
    }
}
