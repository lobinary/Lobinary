/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2003, Oracle and/or its affiliates. All rights reserved.
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

/**
* The <code>LineMetrics</code> class allows access to the
* metrics needed to layout characters along a line
* and to layout of a set of lines.  A <code>LineMetrics</code>
* object encapsulates the measurement information associated
* with a run of text.
* <p>
* Fonts can have different metrics for different ranges of
* characters.  The <code>getLineMetrics</code> methods of
* {@link java.awt.Font Font} take some text as an argument
* and return a <code>LineMetrics</code> object describing the
* metrics of the initial number of characters in that text, as
* returned by {@link #getNumChars}.
* <p>
*  <code> LineMetrics </code>类允许访问沿一行布局字符和一组行的布局所需的度量。 <code> LineMetrics </code>对象封装与一段文本相关联的测量信息。
* <p>
*  字体可以针对不同的字符范围具有不同的度量。
*  {@link java.awt.Font Font}的<code> getLineMetrics </code>方法将一些文本作为参数,并返回一个<code> LineMetrics </code>对
* 象,描述初始字符数的度量文本,由{@link #getNumChars}返回。
*  字体可以针对不同的字符范围具有不同的度量。
* 
*/


public abstract class LineMetrics {


    /**
     * Returns the number of characters (<code>char</code> values) in the text whose
     * metrics are encapsulated by this <code>LineMetrics</code>
     * object.
     * <p>
     *  返回其度量值由此<code> LineMetrics </code>对象封装的文本中的字符数(<code> char </code>值)。
     * 
     * 
     * @return the number of characters (<code>char</code> values) in the text with which
     *         this <code>LineMetrics</code> was created.
     */
    public abstract int getNumChars();

    /**
     * Returns the ascent of the text.  The ascent
     * is the distance from the baseline
     * to the ascender line.  The ascent usually represents the
     * the height of the capital letters of the text.  Some characters
     * can extend above the ascender line.
     * <p>
     *  返回文本的上升。上升是从基线到上升线的距离。上升通常表示文本的大写字母的高度。一些字符可以在上升线上方延伸。
     * 
     * 
     * @return the ascent of the text.
     */
    public abstract float getAscent();

    /**
     * Returns the descent of the text.  The descent
     * is the distance from the baseline
     * to the descender line.  The descent usually represents
     * the distance to the bottom of lower case letters like
     * 'p'.  Some characters can extend below the descender
     * line.
     * <p>
     *  返回文本的下降。下降是从基线到下降线的距离。下降通常表示到小写字母底部的距离,如"p"。一些字符可以在下降线下方延伸。
     * 
     * 
     * @return the descent of the text.
     */
    public abstract float getDescent();

    /**
     * Returns the leading of the text. The
     * leading is the recommended
     * distance from the bottom of the descender line to the
     * top of the next line.
     * <p>
     *  返回文本的开头。前导是从下降线的底部到下一线的顶部的推荐距离。
     * 
     * 
     * @return the leading of the text.
     */
    public abstract float getLeading();

    /**
     * Returns the height of the text.  The
     * height is equal to the sum of the ascent, the
     * descent and the leading.
     * <p>
     * 返回文本的高度。高度等于上升,下降和前导的总和。
     * 
     * 
     * @return the height of the text.
     */
    public abstract float getHeight();

    /**
     * Returns the baseline index of the text.
     * The index is one of
     * {@link java.awt.Font#ROMAN_BASELINE ROMAN_BASELINE},
     * {@link java.awt.Font#CENTER_BASELINE CENTER_BASELINE},
     * {@link java.awt.Font#HANGING_BASELINE HANGING_BASELINE}.
     * <p>
     *  返回文本的基线索引。
     * 索引是{@link java.awt.Font#ROMAN_BASELINE ROMAN_BASELINE},{@link java.awt.Font#CENTER_BASELINE CENTER_BASELINE}
     * ,{@link java.awt.Font#HANGING_BASELINE HANGING_BASELINE}之一。
     *  返回文本的基线索引。
     * 
     * 
     * @return the baseline of the text.
     */
    public abstract int getBaselineIndex();

    /**
     * Returns the baseline offsets of the text,
     * relative to the baseline of the text.  The
     * offsets are indexed by baseline index.  For
     * example, if the baseline index is
     * <code>CENTER_BASELINE</code> then
     * <code>offsets[HANGING_BASELINE]</code> is usually
     * negative, <code>offsets[CENTER_BASELINE]</code>
     * is zero, and <code>offsets[ROMAN_BASELINE]</code>
     * is usually positive.
     * <p>
     *  返回文本的基线偏移量,相对于文本的基线。偏移由基线指数索引。
     * 例如,如果基准索引为<code> CENTER_BASELINE </code>,则<code> offsets [HANGING_BASELINE] </code>通常为负数,<code> offse
     * ts [CENTER_BASELINE] </code>偏移量[ROMAN_BASELINE] </code>通常为正数。
     *  返回文本的基线偏移量,相对于文本的基线。偏移由基线指数索引。
     * 
     * 
     * @return the baseline offsets of the text.
     */
    public abstract float[] getBaselineOffsets();

    /**
     * Returns the position of the strike-through line
     * relative to the baseline.
     * <p>
     *  返回穿透线相对于基线的位置。
     * 
     * 
     * @return the position of the strike-through line.
     */
    public abstract float getStrikethroughOffset();

    /**
     * Returns the thickness of the strike-through line.
     * <p>
     *  返回穿透线的粗细。
     * 
     * 
     * @return the thickness of the strike-through line.
     */
    public abstract float getStrikethroughThickness();

    /**
     * Returns the position of the underline relative to
     * the baseline.
     * <p>
     *  返回下划线相对于基线的位置。
     * 
     * 
     * @return the position of the underline.
     */
    public abstract float getUnderlineOffset();

    /**
     * Returns the thickness of the underline.
     * <p>
     *  返回下划线的粗细。
     * 
     * @return the thickness of the underline.
     */
    public abstract float getUnderlineThickness();
}
