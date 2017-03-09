/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1998, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996 - 1997, All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998, All Rights Reserved
 *
 * The original version of this source code and documentation is
 * copyrighted and owned by Taligent, Inc., a wholly-owned subsidiary
 * of IBM. These materials are provided under terms of a License
 * Agreement between Taligent and Sun. This technology is protected
 * by multiple US and International patents.
 *
 * This notice and attribution to Taligent may not be removed.
 * Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权所有Taligent,Inc. 1996  -  1997,保留所有权利(C)版权所有IBM Corp. 1996  -  1998,保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 
 *  此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.awt.font;
import java.lang.String;

/**
 * The <code>TextHitInfo</code> class represents a character position in a
 * text model, and a <b>bias</b>, or "side," of the character.  Biases are
 * either <EM>leading</EM> (the left edge, for a left-to-right character)
 * or <EM>trailing</EM> (the right edge, for a left-to-right character).
 * Instances of <code>TextHitInfo</code> are used to specify caret and
 * insertion positions within text.
 * <p>
 * For example, consider the text "abc".  TextHitInfo.trailing(1)
 * corresponds to the right side of the 'b' in the text.
 * <p>
 * <code>TextHitInfo</code> is used primarily by {@link TextLayout} and
 * clients of <code>TextLayout</code>.  Clients of <code>TextLayout</code>
 * query <code>TextHitInfo</code> instances for an insertion offset, where
 * new text is inserted into the text model.  The insertion offset is equal
 * to the character position in the <code>TextHitInfo</code> if the bias
 * is leading, and one character after if the bias is trailing.  The
 * insertion offset for TextHitInfo.trailing(1) is 2.
 * <p>
 * Sometimes it is convenient to construct a <code>TextHitInfo</code> with
 * the same insertion offset as an existing one, but on the opposite
 * character.  The <code>getOtherHit</code> method constructs a new
 * <code>TextHitInfo</code> with the same insertion offset as an existing
 * one, with a hit on the character on the other side of the insertion offset.
 * Calling <code>getOtherHit</code> on trailing(1) would return leading(2).
 * In general, <code>getOtherHit</code> for trailing(n) returns
 * leading(n+1) and <code>getOtherHit</code> for leading(n)
 * returns trailing(n-1).
 * <p>
 * <strong>Example</strong>:<p>
 * Converting a graphical point to an insertion point within a text
 * model
 * <blockquote><pre>
 * TextLayout layout = ...;
 * Point2D.Float hitPoint = ...;
 * TextHitInfo hitInfo = layout.hitTestChar(hitPoint.x, hitPoint.y);
 * int insPoint = hitInfo.getInsertionIndex();
 * // insPoint is relative to layout;  may need to adjust for use
 * // in a text model
 * </pre></blockquote>
 *
 * <p>
 *  <code> TextHitInfo </code>类表示文本模型中的字符位置,以及字符的<b>偏置</b>或"side"。
 * 偏差是<EM>超前</EM>(左边缘,对于从左到右字符)或<EM>尾部</EM>(右边缘,对于从左到右字符)。
 *  <code> TextHitInfo </code>的实例用于指定文本中的插入符和插入位置。
 * <p>
 *  例如,考虑文本"abc"。 TextHitInfo.trailing(1)对应于文本中"b"的右侧。
 * <p>
 * <code> TextHitInfo </code>主要由{@link TextLayout}和<code> TextLayout </code>的客户端使用。
 *  <code> TextLayout </code>查询<code> TextHitInfo </code>实例中的插入偏移的客户端,其中新文本插入到文本模型中。
 * 如果偏差为前导,则插入偏移量等于<code> TextHitInfo </code>中的字符位置,如果偏差为拖尾,则为一个字符。 TextHitInfo.trailing(1)的插入偏移量为2。
 * <p>
 *  有时,使用与现有插入偏移相同的插入偏移来构造<code> TextHitInfo </code>是很方便的,但是在相反的字符上。
 *  <code> getOtherHit </code>方法构造一个具有与现有插入偏移相同的插入偏移的新的<code> TextHitInfo </code>,其中对插入偏移的另一侧的字符进行命中。
 * 在尾部(1)调用<code> getOtherHit </code>会返回引导(2)。
 * 通常,对于前导(n)返回前导(n-1),<n>返回前导(n + 1)和<code> getOtherHit </code>的<code> getOtherHit </code>。
 * <p>
 *  <strong>示例</strong>：<p>将图形点转换为文本模型中的插入点<blockquote> <pre> TextLayout layout = ...; Point2D.Float hit
 * Point = ...; TextHitInfo hitInfo = layout.hitTestChar(hitPoint.x,hitPoint.y); int insPoint = hitInfo.
 * getInsertionIndex(); // insPoint是相对于布局的;可能需要调整//在文本模型中使用</pre> </blockquote>。
 * 
 * 
 * @see TextLayout
 */

public final class TextHitInfo {
    private int charIndex;
    private boolean isLeadingEdge;

    /**
     * Constructs a new <code>TextHitInfo</code>.
     * <p>
     *  构造一个新的<code> TextHitInfo </code>。
     * 
     * 
     * @param charIndex the index of the character hit
     * @param isLeadingEdge <code>true</code> if the leading edge of the
     * character was hit
     */
    private TextHitInfo(int charIndex, boolean isLeadingEdge) {
        this.charIndex = charIndex;
        this.isLeadingEdge = isLeadingEdge;
    }

    /**
     * Returns the index of the character hit.
     * <p>
     * 返回字符匹配的索引。
     * 
     * 
     * @return the index of the character hit.
     */
    public int getCharIndex() {
        return charIndex;
    }

    /**
     * Returns <code>true</code> if the leading edge of the character was
     * hit.
     * <p>
     *  如果字符的前边缘被击中,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if the leading edge of the character was
     * hit; <code>false</code> otherwise.
     */
    public boolean isLeadingEdge() {
        return isLeadingEdge;
    }

    /**
     * Returns the insertion index.  This is the character index if
     * the leading edge of the character was hit, and one greater
     * than the character index if the trailing edge was hit.
     * <p>
     *  返回插入索引。这是字符索引,如果字符的前缘被命中,并且一个大于字符索引,如果后缘被命中。
     * 
     * 
     * @return the insertion index.
     */
    public int getInsertionIndex() {
        return isLeadingEdge ? charIndex : charIndex + 1;
    }

    /**
     * Returns the hash code.
     * <p>
     *  返回散列码。
     * 
     * 
     * @return the hash code of this <code>TextHitInfo</code>, which is
     * also the <code>charIndex</code> of this <code>TextHitInfo</code>.
     */
    public int hashCode() {
        return charIndex;
    }

    /**
     * Returns <code>true</code> if the specified <code>Object</code> is a
     * <code>TextHitInfo</code> and equals this <code>TextHitInfo</code>.
     * <p>
     *  如果指定的<code> Object </code>是<code> TextHitInfo </code>且等于此<code> TextHitInfo </code>,则返回<code> true </code>
     * 。
     * 
     * 
     * @param obj the <code>Object</code> to test for equality
     * @return <code>true</code> if the specified <code>Object</code>
     * equals this <code>TextHitInfo</code>; <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        return (obj instanceof TextHitInfo) && equals((TextHitInfo)obj);
    }

    /**
     * Returns <code>true</code> if the specified <code>TextHitInfo</code>
     * has the same <code>charIndex</code> and <code>isLeadingEdge</code>
     * as this <code>TextHitInfo</code>.  This is not the same as having
     * the same insertion offset.
     * <p>
     *  如果指定的<code> TextHitInfo </code>具有与此<code> TextHitInfo </code>相同的<code> charIndex </code>和<code> isLe
     * adingEdge </code>,则返回<code> true </code> 。
     * 这与具有相同的插入偏移不同。
     * 
     * 
     * @param hitInfo a specified <code>TextHitInfo</code>
     * @return <code>true</code> if the specified <code>TextHitInfo</code>
     * has the same <code>charIndex</code> and <code>isLeadingEdge</code>
     * as this <code>TextHitInfo</code>.
     */
    public boolean equals(TextHitInfo hitInfo) {
        return hitInfo != null && charIndex == hitInfo.charIndex &&
            isLeadingEdge == hitInfo.isLeadingEdge;
    }

    /**
     * Returns a <code>String</code> representing the hit for debugging
     * use only.
     * <p>
     *  返回代表调试使用的匹配的<code> String </code>。
     * 
     * 
     * @return a <code>String</code> representing this
     * <code>TextHitInfo</code>.
     */
    public String toString() {
        return "TextHitInfo[" + charIndex + (isLeadingEdge ? "L" : "T")+"]";
    }

    /**
     * Creates a <code>TextHitInfo</code> on the leading edge of the
     * character at the specified <code>charIndex</code>.
     * <p>
     *  在指定的<code> charIndex </code>处的字符前沿创建一个<code> TextHitInfo </code>。
     * 
     * 
     * @param charIndex the index of the character hit
     * @return a <code>TextHitInfo</code> on the leading edge of the
     * character at the specified <code>charIndex</code>.
     */
    public static TextHitInfo leading(int charIndex) {
        return new TextHitInfo(charIndex, true);
    }

    /**
     * Creates a hit on the trailing edge of the character at
     * the specified <code>charIndex</code>.
     * <p>
     *  在指定的<code> charIndex </code>处,在字符的尾部创建命中。
     * 
     * 
     * @param charIndex the index of the character hit
     * @return a <code>TextHitInfo</code> on the trailing edge of the
     * character at the specified <code>charIndex</code>.
     */
    public static TextHitInfo trailing(int charIndex) {
        return new TextHitInfo(charIndex, false);
    }

    /**
     * Creates a <code>TextHitInfo</code> at the specified offset,
     * associated with the character before the offset.
     * <p>
     *  在指定偏移量处创建与该偏移前的字符相关联的<code> TextHitInfo </code>。
     * 
     * 
     * @param offset an offset associated with the character before
     * the offset
     * @return a <code>TextHitInfo</code> at the specified offset.
     */
    public static TextHitInfo beforeOffset(int offset) {
        return new TextHitInfo(offset-1, false);
    }

    /**
     * Creates a <code>TextHitInfo</code> at the specified offset,
     * associated with the character after the offset.
     * <p>
     *  在指定偏移量处创建一个<code> TextHitInfo </code>,与偏移后的字符相关联。
     * 
     * 
     * @param offset an offset associated with the character after
     * the offset
     * @return a <code>TextHitInfo</code> at the specified offset.
     */
    public static TextHitInfo afterOffset(int offset) {
        return new TextHitInfo(offset, true);
    }

    /**
     * Creates a <code>TextHitInfo</code> on the other side of the
     * insertion point.  This <code>TextHitInfo</code> remains unchanged.
     * <p>
     *  在插入点的另一侧创建<code> TextHitInfo </code>。此<code> TextHitInfo </code>保持不变。
     * 
     * 
     * @return a <code>TextHitInfo</code> on the other side of the
     * insertion point.
     */
    public TextHitInfo getOtherHit() {
        if (isLeadingEdge) {
            return trailing(charIndex - 1);
        } else {
            return leading(charIndex + 1);
        }
    }

    /**
     * Creates a <code>TextHitInfo</code> whose character index is offset
     * by <code>delta</code> from the <code>charIndex</code> of this
     * <code>TextHitInfo</code>. This <code>TextHitInfo</code> remains
     * unchanged.
     * <p>
     * 创建<code> TextHitInfo </code>,其字符索引从<code> TextHitInfo </code>的<code> charIndex </code>中偏移<code> delta
     *  </code>。
     * 
     * @param delta the value to offset this <code>charIndex</code>
     * @return a <code>TextHitInfo</code> whose <code>charIndex</code> is
     * offset by <code>delta</code> from the <code>charIndex</code> of
     * this <code>TextHitInfo</code>.
     */
    public TextHitInfo getOffsetHit(int delta) {
        return new TextHitInfo(charIndex + delta, isLeadingEdge);
    }
}
