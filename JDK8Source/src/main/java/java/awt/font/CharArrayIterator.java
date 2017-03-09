/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.font;

import java.text.CharacterIterator;

class CharArrayIterator implements CharacterIterator {

    private char[] chars;
    private int pos;
    private int begin;

    CharArrayIterator(char[] chars) {

        reset(chars, 0);
    }

    CharArrayIterator(char[] chars, int begin) {

        reset(chars, begin);
    }

    /**
     * Sets the position to getBeginIndex() and returns the character at that
     * position.
     * <p>
     *  将位置设置为getBeginIndex()并返回该位置的字符。
     * 
     * 
     * @return the first character in the text, or DONE if the text is empty
     * @see getBeginIndex
     */
    public char first() {

        pos = 0;
        return current();
    }

    /**
     * Sets the position to getEndIndex()-1 (getEndIndex() if the text is empty)
     * and returns the character at that position.
     * <p>
     *  将位置设置为getEndIndex() -  1(getEndIndex(),如果文本为空),并返回该位置处的字符。
     * 
     * 
     * @return the last character in the text, or DONE if the text is empty
     * @see getEndIndex
     */
    public char last() {

        if (chars.length > 0) {
            pos = chars.length-1;
        }
        else {
            pos = 0;
        }
        return current();
    }

    /**
     * Gets the character at the current position (as returned by getIndex()).
     * <p>
     *  获取当前位置的字符(由getIndex()返回)。
     * 
     * 
     * @return the character at the current position or DONE if the current
     * position is off the end of the text.
     * @see getIndex
     */
    public char current() {

        if (pos >= 0 && pos < chars.length) {
            return chars[pos];
        }
        else {
            return DONE;
        }
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
     */
    public char next() {

        if (pos < chars.length-1) {
            pos++;
            return chars[pos];
        }
        else {
            pos = chars.length;
            return DONE;
        }
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
     */
    public char previous() {

        if (pos > 0) {
            pos--;
            return chars[pos];
        }
        else {
            pos = 0;
            return DONE;
        }
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
     */
    public char setIndex(int position) {

        position -= begin;
        if (position < 0 || position > chars.length) {
            throw new IllegalArgumentException("Invalid index");
        }
        pos = position;
        return current();
    }

    /**
     * Returns the start index of the text.
     * <p>
     *  返回文本的开始索引。
     * 
     * 
     * @return the index at which the text begins.
     */
    public int getBeginIndex() {
        return begin;
    }

    /**
     * Returns the end index of the text.  This index is the index of the first
     * character following the end of the text.
     * <p>
     *  返回文本的结束索引。此索引是文本结束后的第一个字符的索引。
     * 
     * 
     * @return the index after the last character in the text
     */
    public int getEndIndex() {
        return begin+chars.length;
    }

    /**
     * Returns the current index.
     * <p>
     *  返回当前索引。
     * 
     * 
     * @return the current index.
     */
    public int getIndex() {
        return begin+pos;
    }

    /**
     * Create a copy of this iterator
     * <p>
     *  创建此迭代器的副本
     * 
     * @return A copy of this
     */
    public Object clone() {
        CharArrayIterator c = new CharArrayIterator(chars, begin);
        c.pos = this.pos;
        return c;
    }

    void reset(char[] chars) {
        reset(chars, 0);
    }

    void reset(char[] chars, int begin) {

        this.chars = chars;
        this.begin = begin;
        pos = 0;
    }
}
