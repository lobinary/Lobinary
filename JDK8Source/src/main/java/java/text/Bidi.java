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

/*
 * (C) Copyright IBM Corp. 1999-2003 - All Rights Reserved
 *
 * The original version of this source code and documentation is
 * copyrighted and owned by IBM. These materials are provided
 * under terms of a License Agreement between IBM and Sun.
 * This technology is protected by multiple US and International
 * patents. This notice and attribution to IBM may not be removed.
 * <p>
 *  (C)版权所有IBM Corp. 1999-2003  - 保留所有权利
 * 
 *  此源代码和文档的原始版本受版权和IBM拥有。这些材料是根据IBM和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。本通知和归属于IBM的内容不得删除。
 * 
 */

package java.text;

import sun.text.bidi.BidiBase;

/**
 * This class implements the Unicode Bidirectional Algorithm.
 * <p>
 * A Bidi object provides information on the bidirectional reordering of the text
 * used to create it.  This is required, for example, to properly display Arabic
 * or Hebrew text.  These languages are inherently mixed directional, as they order
 * numbers from left-to-right while ordering most other text from right-to-left.
 * <p>
 * Once created, a Bidi object can be queried to see if the text it represents is
 * all left-to-right or all right-to-left.  Such objects are very lightweight and
 * this text is relatively easy to process.
 * <p>
 * If there are multiple runs of text, information about the runs can be accessed
 * by indexing to get the start, limit, and level of a run.  The level represents
 * both the direction and the 'nesting level' of a directional run.  Odd levels
 * are right-to-left, while even levels are left-to-right.  So for example level
 * 0 represents left-to-right text, while level 1 represents right-to-left text, and
 * level 2 represents left-to-right text embedded in a right-to-left run.
 *
 * <p>
 *  此类实现Unicode双向算法。
 * <p>
 *  Bidi对象提供有关用于创建文本的文本的双向重新排序的信息。这是必需的,例如,正确显示阿拉伯语或希伯来语文本。这些语言固有地混合定向,因为他们从左到右排序数字,而从右到左排序大多数其他文本。
 * <p>
 *  一旦创建,就可以查询一个Bidi对象来查看它所代表的文本是从左到右还是从右到左。这样的对象非常轻量级,并且这个文本相对容易处理。
 * <p>
 * 如果有多个文本运行,可以通过索引访问有关运行的信息,以获取运行的开始,限制和级别。水平表示方向运行的方向和'嵌套水平'。奇数级是从右到左,而偶数级是从左到右。
 * 因此,例如,级别0表示从左到右文本,而级别1表示从右到左文本,而级别2表示从右到左运行嵌入的从左到右文本。
 * 
 * 
 * @since 1.4
 */
public final class Bidi {

    /** Constant indicating base direction is left-to-right. */
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;

    /** Constant indicating base direction is right-to-left. */
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;

    /**
     * Constant indicating that the base direction depends on the first strong
     * directional character in the text according to the Unicode
     * Bidirectional Algorithm.  If no strong directional character is present,
     * the base direction is left-to-right.
     * <p>
     *  常数,指示基本方向取决于根据Unicode双向算法的文本中的第一强方向字符。如果不存在强方向字符,则基本方向是从左到右。
     * 
     */
    public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = -2;

    /**
     * Constant indicating that the base direction depends on the first strong
     * directional character in the text according to the Unicode
     * Bidirectional Algorithm.  If no strong directional character is present,
     * the base direction is right-to-left.
     * <p>
     *  常数,指示基本方向取决于根据Unicode双向算法的文本中的第一强方向字符。如果不存在强方向字符,则基本方向是从右到左。
     * 
     */
    public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = -1;

    private BidiBase bidiBase;

    /**
     * Create Bidi from the given paragraph of text and base direction.
     * <p>
     *  从文本的给定段落和基本方向创建Bidi。
     * 
     * 
     * @param paragraph a paragraph of text
     * @param flags a collection of flags that control the algorithm.  The
     * algorithm understands the flags DIRECTION_LEFT_TO_RIGHT, DIRECTION_RIGHT_TO_LEFT,
     * DIRECTION_DEFAULT_LEFT_TO_RIGHT, and DIRECTION_DEFAULT_RIGHT_TO_LEFT.
     * Other values are reserved.
     */
    public Bidi(String paragraph, int flags) {
        if (paragraph == null) {
            throw new IllegalArgumentException("paragraph is null");
        }

        bidiBase = new BidiBase(paragraph.toCharArray(), 0, null, 0, paragraph.length(), flags);
    }

    /**
     * Create Bidi from the given paragraph of text.
     * <p>
     * The RUN_DIRECTION attribute in the text, if present, determines the base
     * direction (left-to-right or right-to-left).  If not present, the base
     * direction is computes using the Unicode Bidirectional Algorithm, defaulting to left-to-right
     * if there are no strong directional characters in the text.  This attribute, if
     * present, must be applied to all the text in the paragraph.
     * <p>
     * The BIDI_EMBEDDING attribute in the text, if present, represents embedding level
     * information.  Negative values from -1 to -62 indicate overrides at the absolute value
     * of the level.  Positive values from 1 to 62 indicate embeddings.  Where values are
     * zero or not defined, the base embedding level as determined by the base direction
     * is assumed.
     * <p>
     * The NUMERIC_SHAPING attribute in the text, if present, converts European digits to
     * other decimal digits before running the bidi algorithm.  This attribute, if present,
     * must be applied to all the text in the paragraph.
     *
     * <p>
     *  从文本的给定段创建Bidi。
     * <p>
     *  文本中的RUN_DIRECTION属性(如果存在)确定基本方向(从左到右或从右到左)。如果不存在,则基本方向使用Unicode双向算法计算,如果文本中没有强方向字符,则默认为从左到右。
     * 此属性(如果存在)必须应用于段落中的所有文本。
     * <p>
     * 文本中的BIDI_EMBEDDING属性(如果存在)表示嵌入级别信息。从-1到-62的负值表示在级别的绝对值上的覆盖。从1到62的正值表示嵌入。
     * 在值为零或未定义的情况下,假设由基本方向确定的基本嵌入级别。
     * <p>
     *  文本中的NUMERIC_SHAPING属性(如果存在)将在运行bidi算法之前将欧洲位数转换为其他十进制数字。此属性(如果存在)必须应用于段落中的所有文本。
     * 
     * 
     * @param paragraph a paragraph of text with optional character and paragraph attribute information
     *
     * @see java.awt.font.TextAttribute#BIDI_EMBEDDING
     * @see java.awt.font.TextAttribute#NUMERIC_SHAPING
     * @see java.awt.font.TextAttribute#RUN_DIRECTION
     */
    public Bidi(AttributedCharacterIterator paragraph) {
        if (paragraph == null) {
            throw new IllegalArgumentException("paragraph is null");
        }

        bidiBase = new BidiBase(0, 0);
        bidiBase.setPara(paragraph);
    }

    /**
     * Create Bidi from the given text, embedding, and direction information.
     * The embeddings array may be null.  If present, the values represent embedding level
     * information.  Negative values from -1 to -61 indicate overrides at the absolute value
     * of the level.  Positive values from 1 to 61 indicate embeddings.  Where values are
     * zero, the base embedding level as determined by the base direction is assumed.
     * <p>
     *  从给定文本,嵌入和方向信息创建Bidi。 embeddings数组可以为null。如果存在,则值表示嵌入级别信息。从-1到-61的负值表示在级别的绝对值上的覆盖。从1到61的正值表示嵌入。
     * 在值为零的情况下,假设由基本方向确定的基本嵌入级别。
     * 
     * 
     * @param text an array containing the paragraph of text to process.
     * @param textStart the index into the text array of the start of the paragraph.
     * @param embeddings an array containing embedding values for each character in the paragraph.
     * This can be null, in which case it is assumed that there is no external embedding information.
     * @param embStart the index into the embedding array of the start of the paragraph.
     * @param paragraphLength the length of the paragraph in the text and embeddings arrays.
     * @param flags a collection of flags that control the algorithm.  The
     * algorithm understands the flags DIRECTION_LEFT_TO_RIGHT, DIRECTION_RIGHT_TO_LEFT,
     * DIRECTION_DEFAULT_LEFT_TO_RIGHT, and DIRECTION_DEFAULT_RIGHT_TO_LEFT.
     * Other values are reserved.
     */
    public Bidi(char[] text, int textStart, byte[] embeddings, int embStart, int paragraphLength, int flags) {
        if (text == null) {
            throw new IllegalArgumentException("text is null");
        }
        if (paragraphLength < 0) {
            throw new IllegalArgumentException("bad length: " + paragraphLength);
        }
        if (textStart < 0 || paragraphLength > text.length - textStart) {
            throw new IllegalArgumentException("bad range: " + textStart +
                                               " length: " + paragraphLength +
                                               " for text of length: " + text.length);
        }
        if (embeddings != null && (embStart < 0 || paragraphLength > embeddings.length - embStart)) {
            throw new IllegalArgumentException("bad range: " + embStart +
                                               " length: " + paragraphLength +
                                               " for embeddings of length: " + text.length);
        }

        bidiBase = new BidiBase(text, textStart, embeddings, embStart, paragraphLength, flags);
    }

    /**
     * Create a Bidi object representing the bidi information on a line of text within
     * the paragraph represented by the current Bidi.  This call is not required if the
     * entire paragraph fits on one line.
     *
     * <p>
     *  创建一个Bidi对象,表示由当前Bidi表示的段落中的一行文本上的双向信息。如果整个段落适合一行,则不需要此调用。
     * 
     * 
     * @param lineStart the offset from the start of the paragraph to the start of the line.
     * @param lineLimit the offset from the start of the paragraph to the limit of the line.
     * @return a {@code Bidi} object
     */
    public Bidi createLineBidi(int lineStart, int lineLimit) {
        AttributedString astr = new AttributedString("");
        Bidi newBidi = new Bidi(astr.getIterator());

        return bidiBase.setLine(this, bidiBase, newBidi, newBidi.bidiBase,lineStart, lineLimit);
    }

    /**
     * Return true if the line is not left-to-right or right-to-left.  This means it either has mixed runs of left-to-right
     * and right-to-left text, or the base direction differs from the direction of the only run of text.
     *
     * <p>
     *  如果行不是从左到右或从右到左,则返回true。这意味着它具有从左到右和从右到左文本的混合运行,或者基本方向与唯一文本运行的方向不同。
     * 
     * 
     * @return true if the line is not left-to-right or right-to-left.
     */
    public boolean isMixed() {
        return bidiBase.isMixed();
    }

    /**
     * Return true if the line is all left-to-right text and the base direction is left-to-right.
     *
     * <p>
     *  如果行是所有从左到右的文本,基本方向是从左到右,则返回true。
     * 
     * 
     * @return true if the line is all left-to-right text and the base direction is left-to-right
     */
    public boolean isLeftToRight() {
        return bidiBase.isLeftToRight();
    }

    /**
     * Return true if the line is all right-to-left text, and the base direction is right-to-left.
     * <p>
     * 如果行是所有从右到左的文本,并且基本方向是从右到左,则返回true。
     * 
     * 
     * @return true if the line is all right-to-left text, and the base direction is right-to-left
     */
    public boolean isRightToLeft() {
        return bidiBase.isRightToLeft();
    }

    /**
     * Return the length of text in the line.
     * <p>
     *  返回行中的文本长度。
     * 
     * 
     * @return the length of text in the line
     */
    public int getLength() {
        return bidiBase.getLength();
    }

    /**
     * Return true if the base direction is left-to-right.
     * <p>
     *  如果基本方向是从左到右,则返回true。
     * 
     * 
     * @return true if the base direction is left-to-right
     */
    public boolean baseIsLeftToRight() {
        return bidiBase.baseIsLeftToRight();
    }

    /**
     * Return the base level (0 if left-to-right, 1 if right-to-left).
     * <p>
     *  返回基本级别(如果从左向右为0,则返回0,如果从右向左,则返回1)。
     * 
     * 
     * @return the base level
     */
    public int getBaseLevel() {
        return bidiBase.getParaLevel();
    }

    /**
     * Return the resolved level of the character at offset.  If offset is
     * {@literal <} 0 or &ge; the length of the line, return the base direction
     * level.
     *
     * <p>
     *  返回偏移处的字符的解析级别。如果offset为{@literal <} 0或&ge;线的长度,返回基本方向水平。
     * 
     * 
     * @param offset the index of the character for which to return the level
     * @return the resolved level of the character at offset
     */
    public int getLevelAt(int offset) {
        return bidiBase.getLevelAt(offset);
    }

    /**
     * Return the number of level runs.
     * <p>
     *  返回级别运行的数量。
     * 
     * 
     * @return the number of level runs
     */
    public int getRunCount() {
        return bidiBase.countRuns();
    }

    /**
     * Return the level of the nth logical run in this line.
     * <p>
     *  返回此行中第n个逻辑运行的级别。
     * 
     * 
     * @param run the index of the run, between 0 and <code>getRunCount()</code>
     * @return the level of the run
     */
    public int getRunLevel(int run) {
        return bidiBase.getRunLevel(run);
    }

    /**
     * Return the index of the character at the start of the nth logical run in this line, as
     * an offset from the start of the line.
     * <p>
     *  返回此行中第n个逻辑运行开始处的字符的索引,作为从行开始的偏移量。
     * 
     * 
     * @param run the index of the run, between 0 and <code>getRunCount()</code>
     * @return the start of the run
     */
    public int getRunStart(int run) {
        return bidiBase.getRunStart(run);
    }

    /**
     * Return the index of the character past the end of the nth logical run in this line, as
     * an offset from the start of the line.  For example, this will return the length
     * of the line for the last run on the line.
     * <p>
     *  将字符的索引超过此行中第n个逻辑运行的结尾,作为从行开始的偏移量。例如,这将返回该行上最后一次运行的行的长度。
     * 
     * 
     * @param run the index of the run, between 0 and <code>getRunCount()</code>
     * @return limit the limit of the run
     */
    public int getRunLimit(int run) {
        return bidiBase.getRunLimit(run);
    }

    /**
     * Return true if the specified text requires bidi analysis.  If this returns false,
     * the text will display left-to-right.  Clients can then avoid constructing a Bidi object.
     * Text in the Arabic Presentation Forms area of Unicode is presumed to already be shaped
     * and ordered for display, and so will not cause this function to return true.
     *
     * <p>
     *  如果指定的文本需要双向分析,则返回true。如果这返回false,文本将显示从左到右。客户端可以避免构造一个Bidi对象。
     *  Unicode的Arabic Presentation Forms区域中的文本被假定为已经被显示的形状和顺序,因此不会导致此函数返回true。
     * 
     * 
     * @param text the text containing the characters to test
     * @param start the start of the range of characters to test
     * @param limit the limit of the range of characters to test
     * @return true if the range of characters requires bidi analysis
     */
    public static boolean requiresBidi(char[] text, int start, int limit) {
        return BidiBase.requiresBidi(text, start, limit);
    }

    /**
     * Reorder the objects in the array into visual order based on their levels.
     * This is a utility function to use when you have a collection of objects
     * representing runs of text in logical order, each run containing text
     * at a single level.  The elements at <code>index</code> from
     * <code>objectStart</code> up to <code>objectStart + count</code>
     * in the objects array will be reordered into visual order assuming
     * each run of text has the level indicated by the corresponding element
     * in the levels array (at <code>index - objectStart + levelStart</code>).
     *
     * <p>
     * 将数组中的对象重新排序为基于其级别的视觉顺序。这是一个实用函数,用于当您拥有表示逻辑顺序的文本运行的对象集合时,每个运行都包含单个级别的文本。
     * 对象数组中<code> index </code>从<code> objectStart </code>到<code> objectStart + count </code>的元素将被重新排序为视觉顺序
     * ,假设每次运行的文本都具有级别由level数组中的相应元素(在<code> index  -  objectStart + levelStart </code>)指示)。
     * 将数组中的对象重新排序为基于其级别的视觉顺序。这是一个实用函数,用于当您拥有表示逻辑顺序的文本运行的对象集合时,每个运行都包含单个级别的文本。
     * 
     * @param levels an array representing the bidi level of each object
     * @param levelStart the start position in the levels array
     * @param objects the array of objects to be reordered into visual order
     * @param objectStart the start position in the objects array
     * @param count the number of objects to reorder
     */
    public static void reorderVisually(byte[] levels, int levelStart, Object[] objects, int objectStart, int count) {
        BidiBase.reorderVisually(levels, levelStart, objects, objectStart, count);
    }

    /**
     * Display the bidi internal state, used in debugging.
     * <p>
     * 
     */
    public String toString() {
        return bidiBase.toString();
    }

}
