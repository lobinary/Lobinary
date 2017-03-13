/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 * The original version of this source code and documentation
 * is copyrighted and owned by Taligent, Inc., a wholly-owned
 * subsidiary of IBM. These materials are provided under terms
 * of a License Agreement between Taligent and Sun. This technology
 * is protected by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权所有Taligent,Inc 1996,1997  - 保留所有权利(C)版权所有IBM Corp 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本受版权保护,并由Taligent,Inc(IBM的全资子公司)拥有。这些资料根据Taligent与Sun之间的许可协议的条款提供此技术受多项美国和国际专利保护
 * 
 *  本通知和Taligent的归属不得删除Taligent是Taligent,Inc的注册商标
 * 
 */

package java.text;


/**
 * This interface defines a protocol for bidirectional iteration over text.
 * The iterator iterates over a bounded sequence of characters.  Characters
 * are indexed with values beginning with the value returned by getBeginIndex() and
 * continuing through the value returned by getEndIndex()-1.
 * <p>
 * Iterators maintain a current character index, whose valid range is from
 * getBeginIndex() to getEndIndex(); the value getEndIndex() is included to allow
 * handling of zero-length text ranges and for historical reasons.
 * The current index can be retrieved by calling getIndex() and set directly
 * by calling setIndex(), first(), and last().
 * <p>
 * The methods previous() and next() are used for iteration. They return DONE if
 * they would move outside the range from getBeginIndex() to getEndIndex() -1,
 * signaling that the iterator has reached the end of the sequence. DONE is
 * also returned by other methods to indicate that the current index is
 * outside this range.
 *
 * <P>Examples:<P>
 *
 * Traverse the text from start to finish
 * <pre>{@code
 * public void traverseForward(CharacterIterator iter) {
 *     for(char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
 *         processChar(c);
 *     }
 * }
 * }</pre>
 *
 * Traverse the text backwards, from end to start
 * <pre>{@code
 * public void traverseBackward(CharacterIterator iter) {
 *     for(char c = iter.last(); c != CharacterIterator.DONE; c = iter.previous()) {
 *         processChar(c);
 *     }
 * }
 * }</pre>
 *
 * Traverse both forward and backward from a given position in the text.
 * Calls to notBoundary() in this example represents some
 * additional stopping criteria.
 * <pre>{@code
 * public void traverseOut(CharacterIterator iter, int pos) {
 *     for (char c = iter.setIndex(pos);
 *              c != CharacterIterator.DONE && notBoundary(c);
 *              c = iter.next()) {
 *     }
 *     int end = iter.getIndex();
 *     for (char c = iter.setIndex(pos);
 *             c != CharacterIterator.DONE && notBoundary(c);
 *             c = iter.previous()) {
 *     }
 *     int start = iter.getIndex();
 *     processSection(start, end);
 * }
 * }</pre>
 *
 * <p>
 * 该接口定义了一个用于文本上的双向迭代的协议。迭代器遍历有界字符序列字符使用从getBeginIndex()返回的值开始的值索引,并继续getEndIndex()返回的值 -  1
 * <p>
 *  迭代器维护当前字符索引,其有效范围是从getBeginIndex()到getEndIndex();包括值getEndIndex()以允许处理零长度文本范围和历史原因当前索引可以通过调用getIndex
 * ()并通过调用setIndex(),first()和last()直接设置来检索。
 * <p>
 * 方法previous()和next()用于迭代如果它们超出从getBeginIndex()到getEndIndex()-1的范围,则返回DONE,表示迭代器已到达序列的末尾DONE也返回其他方法来指示当
 * 前索引超出此范围。
 * 
 *  <P>示例：<P>
 * 
 *  遍历从头到尾的文本<pre> {@ code public void traverseForward(CharacterIterator iter){for(char c = iterfirst(); c！= CharacterIteratorDONE; c = iternext()){processChar(c); }
 * }} </pre>。
 * 
 *  遍历文本向后,从结束到开始<pre> {@ code public void traverseBackward(CharacterIterator iter){for(char c = iterlast(); c！= CharacterIteratorDONE; c = iterprevious()){processChar(c); }
 * }} </pre>。
 * 
 * 从文本中的给定位置向前和向后遍历在此示例中,调用notBoundary()表示一些附加的停止标准。
 * <pre> {@ code public void traverseOut(CharacterIterator iter,int pos){for(char c = itersetIndex ); c！= CharacterIteratorDONE && notBoundary(c); c = iternext()){}
 *  int end = itergetIndex(); for(char c = itersetIndex(pos); c！= CharacterIteratorDONE && notBoundary(c
 * ); c = iterprevious()){} int start = itergetIndex processSection(start,end); }} </pre>。
 * 从文本中的给定位置向前和向后遍历在此示例中,调用notBoundary()表示一些附加的停止标准。
 * 
 * 
 * @see StringCharacterIterator
 * @see AttributedCharacterIterator
 */

public interface CharacterIterator extends Cloneable
{

    /**
     * Constant that is returned when the iterator has reached either the end
     * or the beginning of the text. The value is '\\uFFFF', the "not a
     * character" value which should not occur in any valid Unicode string.
     * <p>
     *  当迭代器到达文本的结尾或开头时返回的常量值是'\\\\ uFFFF',不应该出现在任何有效的Unicode字符串中的"不是字符"值
     * 
     */
    public static final char DONE = '\uFFFF';

    /**
     * Sets the position to getBeginIndex() and returns the character at that
     * position.
     * <p>
     * 将位置设置为getBeginIndex()并返回该位置处的字符
     * 
     * 
     * @return the first character in the text, or DONE if the text is empty
     * @see #getBeginIndex()
     */
    public char first();

    /**
     * Sets the position to getEndIndex()-1 (getEndIndex() if the text is empty)
     * and returns the character at that position.
     * <p>
     *  将位置设置为getEndIndex() -  1(getEndIndex(),如果文本为空),并返回该位置处的字符
     * 
     * 
     * @return the last character in the text, or DONE if the text is empty
     * @see #getEndIndex()
     */
    public char last();

    /**
     * Gets the character at the current position (as returned by getIndex()).
     * <p>
     *  获取当前位置的字符(由getIndex()返回)
     * 
     * 
     * @return the character at the current position or DONE if the current
     * position is off the end of the text.
     * @see #getIndex()
     */
    public char current();

    /**
     * Increments the iterator's index by one and returns the character
     * at the new index.  If the resulting index is greater or equal
     * to getEndIndex(), the current index is reset to getEndIndex() and
     * a value of DONE is returned.
     * <p>
     *  将迭代器的索引增加1,并在新索引处返回字符如果结果索引大于或等于getEndIndex(),则当前索引将重置为getEndIndex(),并返回DONE的值
     * 
     * 
     * @return the character at the new position or DONE if the new
     * position is off the end of the text range.
     */
    public char next();

    /**
     * Decrements the iterator's index by one and returns the character
     * at the new index. If the current index is getBeginIndex(), the index
     * remains at getBeginIndex() and a value of DONE is returned.
     * <p>
     *  将迭代器的索引递减1,并在新索引处返回字符如果当前索引为getBeginIndex(),则索引保留在getBeginIndex(),并返回DONE的值
     * 
     * 
     * @return the character at the new position or DONE if the current
     * position is equal to getBeginIndex().
     */
    public char previous();

    /**
     * Sets the position to the specified position in the text and returns that
     * character.
     * <p>
     *  将位置设置为文本中指定的位置,并返回该字符
     * 
     * 
     * @param position the position within the text.  Valid values range from
     * getBeginIndex() to getEndIndex().  An IllegalArgumentException is thrown
     * if an invalid value is supplied.
     * @return the character at the specified position or DONE if the specified position is equal to getEndIndex()
     */
    public char setIndex(int position);

    /**
     * Returns the start index of the text.
     * <p>
     * 返回文本的开始索引
     * 
     * 
     * @return the index at which the text begins.
     */
    public int getBeginIndex();

    /**
     * Returns the end index of the text.  This index is the index of the first
     * character following the end of the text.
     * <p>
     *  返回文本的结束索引此索引是文本结束后的第一个字符的索引
     * 
     * 
     * @return the index after the last character in the text
     */
    public int getEndIndex();

    /**
     * Returns the current index.
     * <p>
     *  返回当前索引
     * 
     * 
     * @return the current index.
     */
    public int getIndex();

    /**
     * Create a copy of this iterator
     * <p>
     *  创建此迭代器的副本
     * 
     * @return A copy of this
     */
    public Object clone();

}
