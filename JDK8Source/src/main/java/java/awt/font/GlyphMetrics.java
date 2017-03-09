/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.geom.Rectangle2D;

/**
 * The <code>GlyphMetrics</code> class represents information for a
 * single glyph.   A glyph is the visual representation of one or more
 * characters.  Many different glyphs can be used to represent a single
 * character or combination of characters.  <code>GlyphMetrics</code>
 * instances are produced by {@link java.awt.Font Font} and are applicable
 * to a specific glyph in a particular <code>Font</code>.
 * <p>
 * Glyphs are either STANDARD, LIGATURE, COMBINING, or COMPONENT.
 * <ul>
 * <li>STANDARD glyphs are commonly used to represent single characters.
 * <li>LIGATURE glyphs are used to represent sequences of characters.
 * <li>COMPONENT glyphs in a {@link GlyphVector} do not correspond to a
 * particular character in a text model. Instead, COMPONENT glyphs are
 * added for typographical reasons, such as Arabic justification.
 * <li>COMBINING glyphs embellish STANDARD or LIGATURE glyphs, such
 * as accent marks.  Carets do not appear before COMBINING glyphs.
 * </ul>
 * <p>
 * Other metrics available through <code>GlyphMetrics</code> are the
 * components of the advance, the visual bounds, and the left and right
 * side bearings.
 * <p>
 * Glyphs for a rotated font, or obtained from a <code>GlyphVector</code>
 * which has applied a rotation to the glyph, can have advances that
 * contain both X and Y components.  Usually the advance only has one
 * component.
 * <p>
 * The advance of a glyph is the distance from the glyph's origin to the
 * origin of the next glyph along the baseline, which is either vertical
 * or horizontal.  Note that, in a <code>GlyphVector</code>,
 * the distance from a glyph to its following glyph might not be the
 * glyph's advance, because of kerning or other positioning adjustments.
 * <p>
 * The bounds is the smallest rectangle that completely contains the
 * outline of the glyph.  The bounds rectangle is relative to the
 * glyph's origin.  The left-side bearing is the distance from the glyph
 * origin to the left of its bounds rectangle. If the left-side bearing is
 * negative, part of the glyph is drawn to the left of its origin.  The
 * right-side bearing is the distance from the right side of the bounds
 * rectangle to the next glyph origin (the origin plus the advance).  If
 * negative, part of the glyph is drawn to the right of the next glyph's
 * origin.  Note that the bounds does not necessarily enclose all the pixels
 * affected when rendering the glyph, because of rasterization and pixel
 * adjustment effects.
 * <p>
 * Although instances of <code>GlyphMetrics</code> can be directly
 * constructed, they are almost always obtained from a
 * <code>GlyphVector</code>.  Once constructed, <code>GlyphMetrics</code>
 * objects are immutable.
 * <p>
 * <strong>Example</strong>:<p>
 * Querying a <code>Font</code> for glyph information
 * <blockquote><pre>
 * Font font = ...;
 * int glyphIndex = ...;
 * GlyphMetrics metrics = GlyphVector.getGlyphMetrics(glyphIndex);
 * int isStandard = metrics.isStandard();
 * float glyphAdvance = metrics.getAdvance();
 * </pre></blockquote>
 * <p>
 *  <code> GlyphMetrics </code>类表示单个字形的信息。字形是一个或多个字符的视觉表示。许多不同的字形可以用于表示单个字符或字符的组合。
 *  <code> GlyphMetrics </code>实例由{@link java.awt.Font Font}生成,适用于特定<code> Font </code>中的特定字形。
 * <p>
 *  字形是标准,LIGATURE,组合或组件。
 * <ul>
 * <li>标准字形通常用于表示单个字符。 <li> LIGATURE字形用于表示字符序列。 <li> {@link GlyphVector}中的COMPONENT字形与文字模型中的特定字元不符。
 * 相反,COMPONENT字形是出于排版原因添加的,例如阿拉伯语对齐。 <li>组合字形加上标准或LIGATURE字形,例如重音符号。插入符号不会出现在组合字形之前。
 * </ul>
 * <p>
 *  通过<code> GlyphMetrics </code>可用的其他度量是提前量,视觉边界以及左侧和右侧方位的分量。
 * <p>
 *  旋转字体的字形或从已对字形应用旋转的<code> GlyphVector </code>获得的字形可以具有包含X和Y分量的进度。通常提前只有一个组件。
 * <p>
 *  字形的前进是沿着基线的字形的原点到下一个字形的原点的距离,其是垂直或水平的。
 * 注意,在<code> GlyphVector </code>中,由于字距或其他定位调整,从字形到其后续字形的距离可能不是字形的前进。
 * <p>
 * 边界是完全包含字形轮廓的最小矩形。边界矩形相对于字形的原点。左侧方位是从字形原点到其边界矩形左边的距离。如果左侧方位为负,则字形的一部分被绘制到其原点的左侧。
 * 右侧方位是从边界矩形的右侧到下一个字形原点(原点加上超前)的距离。如果为负,则字形的一部分被绘制到下一个字形的原点的右边。
 * 请注意,由于光栅化和像素调整效果,边界在渲染字形时不一定会包含所有受影响的像素。
 * <p>
 *  虽然<code> GlyphMetrics </code>的实例可以直接构造,但它们几乎总是从<code> GlyphVector </code>获得。
 * 一旦构造,<code> GlyphMetrics </code>对象是不可变的。
 * <p>
 *  <strong>示例</strong>：<p>查询字体</code> <code>字体信息<blockquote> <pre> Font font = ...; int glyphIndex = ..
 * .; GlyphMetrics metrics = GlyphVector.getGlyphMetrics(glyphIndex); int isStandard = metrics.isStandar
 * d(); float glyphAdvance = metrics.getAdvance(); </pre> </blockquote>。
 * 
 * 
 * @see java.awt.Font
 * @see GlyphVector
 */

public final class GlyphMetrics {
    /**
     * Indicates whether the metrics are for a horizontal or vertical baseline.
     * <p>
     *  指示度量标准是针对水平还是垂直基准。
     * 
     */
    private boolean horizontal;

    /**
     * The x-component of the advance.
     * <p>
     *  提前的x分量。
     * 
     */
    private float advanceX;

    /**
     * The y-component of the advance.
     * <p>
     *  推进的y分量。
     * 
     */
    private float advanceY;

    /**
     * The bounds of the associated glyph.
     * <p>
     *  相关字形的边界。
     * 
     */
    private Rectangle2D.Float bounds;

    /**
     * Additional information about the glyph encoded as a byte.
     * <p>
     *  有关以字节编码的字形的附加信息。
     * 
     */
    private byte glyphType;

    /**
     * Indicates a glyph that represents a single standard
     * character.
     * <p>
     * 表示表示单个标准字符的字形。
     * 
     */
    public static final byte STANDARD = 0;

    /**
     * Indicates a glyph that represents multiple characters
     * as a ligature, for example 'fi' or 'ffi'.  It is followed by
     * filler glyphs for the remaining characters. Filler and combining
     * glyphs can be intermixed to control positioning of accent marks
     * on the logically preceding ligature.
     * <p>
     *  表示将多个字符表示为连字的字形,例如'fi'或'ffi'。它后面是其余字符的填充字形。填充和组合字形可以混合以控制在逻辑上在前的连字上的重音标记的定位。
     * 
     */
    public static final byte LIGATURE = 1;

    /**
     * Indicates a glyph that represents a combining character,
     * such as an umlaut.  There is no caret position between this glyph
     * and the preceding glyph.
     * <p>
     *  指示表示组合字符的字形,例如变音符。在此字形和前一个字形之间没有插入符号位置。
     * 
     */
    public static final byte COMBINING = 2;

    /**
     * Indicates a glyph with no corresponding character in the
     * backing store.  The glyph is associated with the character
     * represented by the logically preceding non-component glyph.  This
     * is used for kashida justification or other visual modifications to
     * existing glyphs.  There is no caret position between this glyph
     * and the preceding glyph.
     * <p>
     *  表示后备存储中没有对应字符的字形。字形与由逻辑上在前的非组件字形表示的字符相关联。这用于kashida调整或对现有字形的其他视觉修改。在此字形和前一个字形之间没有插入位置。
     * 
     */
    public static final byte COMPONENT = 3;

    /**
     * Indicates a glyph with no visual representation. It can
     * be added to the other code values to indicate an invisible glyph.
     * <p>
     *  表示没有视觉表示的字形。它可以添加到其他代码值以指示不可见字形。
     * 
     */
    public static final byte WHITESPACE = 4;

    /**
     * Constructs a <code>GlyphMetrics</code> object.
     * <p>
     *  构造一个<code> GlyphMetrics </code>对象。
     * 
     * 
     * @param advance the advance width of the glyph
     * @param bounds the black box bounds of the glyph
     * @param glyphType the type of the glyph
     */
    public GlyphMetrics(float advance, Rectangle2D bounds, byte glyphType) {
        this.horizontal = true;
        this.advanceX = advance;
        this.advanceY = 0;
        this.bounds = new Rectangle2D.Float();
        this.bounds.setRect(bounds);
        this.glyphType = glyphType;
    }

    /**
     * Constructs a <code>GlyphMetrics</code> object.
     * <p>
     *  构造一个<code> GlyphMetrics </code>对象。
     * 
     * 
     * @param horizontal if true, metrics are for a horizontal baseline,
     *   otherwise they are for a vertical baseline
     * @param advanceX the X-component of the glyph's advance
     * @param advanceY the Y-component of the glyph's advance
     * @param bounds the visual bounds of the glyph
     * @param glyphType the type of the glyph
     * @since 1.4
     */
    public GlyphMetrics(boolean horizontal, float advanceX, float advanceY,
                        Rectangle2D bounds, byte glyphType) {

        this.horizontal = horizontal;
        this.advanceX = advanceX;
        this.advanceY = advanceY;
        this.bounds = new Rectangle2D.Float();
        this.bounds.setRect(bounds);
        this.glyphType = glyphType;
    }

    /**
     * Returns the advance of the glyph along the baseline (either
     * horizontal or vertical).
     * <p>
     *  返回字形沿着基线的前进(水平或垂直)。
     * 
     * 
     * @return the advance of the glyph
     */
    public float getAdvance() {
        return horizontal ? advanceX : advanceY;
    }

    /**
     * Returns the x-component of the advance of the glyph.
     * <p>
     *  返回字形前进的x分量。
     * 
     * 
     * @return the x-component of the advance of the glyph
     * @since 1.4
     */
    public float getAdvanceX() {
        return advanceX;
    }

    /**
     * Returns the y-component of the advance of the glyph.
     * <p>
     *  返回字形前进的y分量。
     * 
     * 
     * @return the y-component of the advance of the glyph
     * @since 1.4
     */
    public float getAdvanceY() {
        return advanceY;
    }

    /**
     * Returns the bounds of the glyph. This is the bounding box of the glyph outline.
     * Because of rasterization and pixel alignment effects, it does not necessarily
     * enclose the pixels that are affected when rendering the glyph.
     * <p>
     *  返回字形的边界。这是字形轮廓的边界框。由于光栅化和像素对齐效果,它不一定包围在渲染字形时受影响的像素。
     * 
     * 
     * @return a {@link Rectangle2D} that is the bounds of the glyph.
     */
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /**
     * Returns the left (top) side bearing of the glyph.
     * <p>
     * This is the distance from 0,&nbsp;0 to the left (top) of the glyph
     * bounds.  If the bounds of the glyph is to the left of (above) the
     * origin, the LSB is negative.
     * <p>
     * 返回字形的左(上)边方位。
     * <p>
     *  这是从0到0到字形边界的左(顶部)的距离。如果字形的边界在原点的上方(上面),则LSB是负的。
     * 
     * 
     * @return the left side bearing of the glyph.
     */
    public float getLSB() {
        return horizontal ? bounds.x : bounds.y;
    }

    /**
     * Returns the right (bottom) side bearing of the glyph.
     * <p>
     * This is the distance from the right (bottom) of the glyph bounds to
     * the advance. If the bounds of the glyph is to the right of (below)
     * the advance, the RSB is negative.
     * <p>
     *  返回字形的右(下)边方位。
     * <p>
     *  这是从字形边界的右(底部)到提前的距离。如果字形的边界在提前的右侧(下方),则RSB为负。
     * 
     * 
     * @return the right side bearing of the glyph.
     */
    public float getRSB() {
        return horizontal ?
            advanceX - bounds.x - bounds.width :
            advanceY - bounds.y - bounds.height;
    }

    /**
     * Returns the raw glyph type code.
     * <p>
     *  返回原始字形类型代码。
     * 
     * 
     * @return the raw glyph type code.
     */
    public int getType() {
        return glyphType;
    }

    /**
     * Returns <code>true</code> if this is a standard glyph.
     * <p>
     *  如果这是标准字形,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if this is a standard glyph;
     *          <code>false</code> otherwise.
     */
    public boolean isStandard() {
        return (glyphType & 0x3) == STANDARD;
    }

    /**
     * Returns <code>true</code> if this is a ligature glyph.
     * <p>
     *  如果这是连字字形,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if this is a ligature glyph;
     *          <code>false</code> otherwise.
     */
    public boolean isLigature() {
        return (glyphType & 0x3) == LIGATURE;
    }

    /**
     * Returns <code>true</code> if this is a combining glyph.
     * <p>
     *  如果这是组合字形,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if this is a combining glyph;
     *          <code>false</code> otherwise.
     */
    public boolean isCombining() {
        return (glyphType & 0x3) == COMBINING;
    }

    /**
     * Returns <code>true</code> if this is a component glyph.
     * <p>
     *  如果这是组件字形,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if this is a component glyph;
     *          <code>false</code> otherwise.
     */
    public boolean isComponent() {
        return (glyphType & 0x3) == COMPONENT;
    }

    /**
     * Returns <code>true</code> if this is a whitespace glyph.
     * <p>
     *  如果这是一个空白字形,返回<code> true </code>。
     * 
     * @return <code>true</code> if this is a whitespace glyph;
     *          <code>false</code> otherwise.
     */
    public boolean isWhitespace() {
        return (glyphType & 0x4) == WHITESPACE;
    }
}
