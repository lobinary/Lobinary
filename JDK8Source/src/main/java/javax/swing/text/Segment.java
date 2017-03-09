/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.text.CharacterIterator;

/**
 * A segment of a character array representing a fragment
 * of text.  It should be treated as immutable even though
 * the array is directly accessible.  This gives fast access
 * to fragments of text without the overhead of copying
 * around characters.  This is effectively an unprotected
 * String.
 * <p>
 * The Segment implements the java.text.CharacterIterator
 * interface to support use with the i18n support without
 * copying text into a string.
 *
 * <p>
 *  代表文本片段的字符数组的段。它应该被视为不可变的,即使数组是可直接访问的。这样可以快速访问文本片段,而无需复制字符。这实际上是一个不受保护的字符串。
 * <p>
 *  Segment实现java.text.CharacterIterator接口,以支持在不将文本复制到字符串的情况下与i18n支持一起使用。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class Segment implements Cloneable, CharacterIterator, CharSequence {

    /**
     * This is the array containing the text of
     * interest.  This array should never be modified;
     * it is available only for efficiency.
     * <p>
     *  这是包含感兴趣的文本的数组。此数组不应被修改;它只能用于提高效率。
     * 
     */
    public char[] array;

    /**
     * This is the offset into the array that
     * the desired text begins.
     * <p>
     *  这是数组中所需文本开始的偏移量。
     * 
     */
    public int offset;

    /**
     * This is the number of array elements that
     * make up the text of interest.
     * <p>
     *  这是组成感兴趣的文本的数组元素的数量。
     * 
     */
    public int count;

    private boolean partialReturn;

    /**
     * Creates a new segment.
     * <p>
     *  创建新细分。
     * 
     */
    public Segment() {
        this(null, 0, 0);
    }

    /**
     * Creates a new segment referring to an existing array.
     *
     * <p>
     *  创建指向现有数组的新段。
     * 
     * 
     * @param array the array to refer to
     * @param offset the offset into the array
     * @param count the number of characters
     */
    public Segment(char[] array, int offset, int count) {
        this.array = array;
        this.offset = offset;
        this.count = count;
        partialReturn = false;
    }

    /**
     * Flag to indicate that partial returns are valid.  If the flag is true,
     * an implementation of the interface method Document.getText(position,length,Segment)
     * should return as much text as possible without making a copy.  The default
     * state of the flag is false which will cause Document.getText(position,length,Segment)
     * to provide the same return behavior it always had, which may or may not
     * make a copy of the text depending upon the request.
     *
     * <p>
     *  表示部分退货有效的标记。如果标志为真,接口方法Document.getText(position,length,Segment)的实现应该返回尽可能多的文本而不进行复制。
     * 标志的默认状态为false,这将导致Document.getText(position,length,Segment)提供它总是具有的相同的返回行为,它可能或可能不会根据请求生成文本的副本。
     * 
     * 
     * @param p whether or not partial returns are valid.
     * @since 1.4
     */
    public void setPartialReturn(boolean p) {
        partialReturn = p;
    }

    /**
     * Flag to indicate that partial returns are valid.
     *
     * <p>
     *  表示部分退货有效的标记。
     * 
     * 
     * @return whether or not partial returns are valid.
     * @since 1.4
     */
    public boolean isPartialReturn() {
        return partialReturn;
    }

    /**
     * Converts a segment into a String.
     *
     * <p>
     *  将段转换为字符串。
     * 
     * 
     * @return the string
     */
    public String toString() {
        if (array != null) {
            return new String(array, offset, count);
        }
        return "";
    }

    // --- CharacterIterator methods -------------------------------------

    /**
     * Sets the position to getBeginIndex() and returns the character at that
     * position.
     * <p>
     * 将位置设置为getBeginIndex()并返回该位置的字符。
     * 
     * 
     * @return the first character in the text, or DONE if the text is empty
     * @see #getBeginIndex
     * @since 1.3
     */
    public char first() {
        pos = offset;
        if (count != 0) {
            return array[pos];
        }
        return DONE;
    }

    /**
     * Sets the position to getEndIndex()-1 (getEndIndex() if the text is empty)
     * and returns the character at that position.
     * <p>
     *  将位置设置为getEndIndex() -  1(getEndIndex(),如果文本为空),并返回该位置处的字符。
     * 
     * 
     * @return the last character in the text, or DONE if the text is empty
     * @see #getEndIndex
     * @since 1.3
     */
    public char last() {
        pos = offset + count;
        if (count != 0) {
            pos -= 1;
            return array[pos];
        }
        return DONE;
    }

    /**
     * Gets the character at the current position (as returned by getIndex()).
     * <p>
     *  获取当前位置的字符(由getIndex()返回)。
     * 
     * 
     * @return the character at the current position or DONE if the current
     * position is off the end of the text.
     * @see #getIndex
     * @since 1.3
     */
    public char current() {
        if (count != 0 && pos < offset + count) {
            return array[pos];
        }
        return DONE;
    }

    /**
     * Increments the iterator's index by one and returns the character
     * at the new index.  If the resulting index is greater or equal
     * to getEndIndex(), the current index is reset to getEndIndex() and
     * a value of DONE is returned.
     * <p>
     *  将迭代器的索引增加1,并在新索引处返回字符。如果结果索引大于或等于getEndIndex(),则当前索引将重置为getEndIndex(),并返回DONE的值。
     * 
     * 
     * @return the character at the new position or DONE if the new
     * position is off the end of the text range.
     * @since 1.3
     */
    public char next() {
        pos += 1;
        int end = offset + count;
        if (pos >= end) {
            pos = end;
            return DONE;
        }
        return current();
    }

    /**
     * Decrements the iterator's index by one and returns the character
     * at the new index. If the current index is getBeginIndex(), the index
     * remains at getBeginIndex() and a value of DONE is returned.
     * <p>
     *  将迭代器的索引递减1,并在新索引处返回字符。如果当前索引是getBeginIndex(),则索引保留在getBeginIndex(),并返回DONE的值。
     * 
     * 
     * @return the character at the new position or DONE if the current
     * position is equal to getBeginIndex().
     * @since 1.3
     */
    public char previous() {
        if (pos == offset) {
            return DONE;
        }
        pos -= 1;
        return current();
    }

    /**
     * Sets the position to the specified position in the text and returns that
     * character.
     * <p>
     *  将位置设置为文本中指定的位置,并返回该字符。
     * 
     * 
     * @param position the position within the text.  Valid values range from
     * getBeginIndex() to getEndIndex().  An IllegalArgumentException is thrown
     * if an invalid value is supplied.
     * @return the character at the specified position or DONE if the specified position is equal to getEndIndex()
     * @since 1.3
     */
    public char setIndex(int position) {
        int end = offset + count;
        if ((position < offset) || (position > end)) {
            throw new IllegalArgumentException("bad position: " + position);
        }
        pos = position;
        if ((pos != end) && (count != 0)) {
            return array[pos];
        }
        return DONE;
    }

    /**
     * Returns the start index of the text.
     * <p>
     *  返回文本的开始索引。
     * 
     * 
     * @return the index at which the text begins.
     * @since 1.3
     */
    public int getBeginIndex() {
        return offset;
    }

    /**
     * Returns the end index of the text.  This index is the index of the first
     * character following the end of the text.
     * <p>
     *  返回文本的结束索引。此索引是文本结束后的第一个字符的索引。
     * 
     * 
     * @return the index after the last character in the text
     * @since 1.3
     */
    public int getEndIndex() {
        return offset + count;
    }

    /**
     * Returns the current index.
     * <p>
     *  返回当前索引。
     * 
     * 
     * @return the current index.
     * @since 1.3
     */
    public int getIndex() {
        return pos;
    }

    // --- CharSequence methods -------------------------------------

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public char charAt(int index) {
        if (index < 0
            || index >= count) {
            throw new StringIndexOutOfBoundsException(index);
        }
        return array[offset + index];
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public int length() {
        return count;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @since 1.6
     */
    public CharSequence subSequence(int start, int end) {
        if (start < 0) {
            throw new StringIndexOutOfBoundsException(start);
        }
        if (end > count) {
            throw new StringIndexOutOfBoundsException(end);
        }
        if (start > end) {
            throw new StringIndexOutOfBoundsException(end - start);
        }
        Segment segment = new Segment();
        segment.array = this.array;
        segment.offset = this.offset + start;
        segment.count = end - start;
        return segment;
    }

    /**
     * Creates a shallow copy.
     *
     * <p>
     *  创建浅拷贝。
     * 
     * @return the copy
     */
    public Object clone() {
        Object o;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException cnse) {
            o = null;
        }
        return o;
    }

    private int pos;


}
