/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * This class is used with the CHAR_REPLACEMENT attribute.
 * <p>
 * The <code>GraphicAttribute</code> class represents a graphic embedded
 * in text. Clients subclass this class to implement their own char
 * replacement graphics.  Clients wishing to embed shapes and images in
 * text need not subclass this class.  Instead, clients can use the
 * {@link ShapeGraphicAttribute} and {@link ImageGraphicAttribute}
 * classes.
 * <p>
 * Subclasses must ensure that their objects are immutable once they
 * are constructed.  Mutating a <code>GraphicAttribute</code> that
 * is used in a {@link TextLayout} results in undefined behavior from the
 * <code>TextLayout</code>.
 * <p>
 *  此类与CHAR_REPLACEMENT属性一起使用。
 * <p>
 *  <code> GraphicAttribute </code>类表示嵌入在文本中的图形。客户端子类这个类来实现自己的char替换图形。希望在文本中嵌入形状和图像的客户端不需要子类化这个类。
 * 相反,客户端可以使用{@link ShapeGraphicAttribute}和{@link ImageGraphicAttribute}类。
 * <p>
 *  子类必须确保它们的对象在构造后是不可变的。
 * 改变{@link TextLayout}中使用的<code> GraphicAttribute </code>会导致<code> TextLayout </code>中的未定义行为。
 * 
 */
public abstract class GraphicAttribute {

    private int fAlignment;

    /**
     * Aligns top of graphic to top of line.
     * <p>
     *  将图形顶部与行顶部对齐。
     * 
     */
    public static final int TOP_ALIGNMENT = -1;

    /**
     * Aligns bottom of graphic to bottom of line.
     * <p>
     *  将图形底部与行底对齐。
     * 
     */
    public static final int BOTTOM_ALIGNMENT = -2;

    /**
     * Aligns origin of graphic to roman baseline of line.
     * <p>
     *  将图形的起点与线的罗马基线对齐。
     * 
     */
    public static final int ROMAN_BASELINE = Font.ROMAN_BASELINE;

    /**
     * Aligns origin of graphic to center baseline of line.
     * <p>
     *  将图形的起点与线的中心基线对齐。
     * 
     */
    public static final int CENTER_BASELINE = Font.CENTER_BASELINE;

    /**
     * Aligns origin of graphic to hanging baseline of line.
     * <p>
     * 将图形的起点与线的悬垂基线对齐。
     * 
     */
    public static final int HANGING_BASELINE = Font.HANGING_BASELINE;

    /**
     * Constructs a <code>GraphicAttribute</code>.
     * Subclasses use this to define the alignment of the graphic.
     * <p>
     *  构造一个<code> GraphicAttribute </code>。子类使用它来定义图形的对齐方式。
     * 
     * 
     * @param alignment an int representing one of the
     * <code>GraphicAttribute</code> alignment fields
     * @throws IllegalArgumentException if alignment is not one of the
     * five defined values.
     */
    protected GraphicAttribute(int alignment) {
        if (alignment < BOTTOM_ALIGNMENT || alignment > HANGING_BASELINE) {
          throw new IllegalArgumentException("bad alignment");
        }
        fAlignment = alignment;
    }

    /**
     * Returns the ascent of this <code>GraphicAttribute</code>.  A
     * graphic can be rendered above its ascent.
     * <p>
     *  返回此<code> GraphicAttribute </code>的上升。可以在其上升之上绘制图形。
     * 
     * 
     * @return the ascent of this <code>GraphicAttribute</code>.
     * @see #getBounds()
     */
    public abstract float getAscent();


    /**
     * Returns the descent of this <code>GraphicAttribute</code>.  A
     * graphic can be rendered below its descent.
     * <p>
     *  返回此<code> GraphicAttribute </code>的下降。可以在其下降之下呈现图形。
     * 
     * 
     * @return the descent of this <code>GraphicAttribute</code>.
     * @see #getBounds()
     */
    public abstract float getDescent();

    /**
     * Returns the advance of this <code>GraphicAttribute</code>.  The
     * <code>GraphicAttribute</code> object's advance is the distance
     * from the point at which the graphic is rendered and the point where
     * the next character or graphic is rendered.  A graphic can be
     * rendered beyond its advance
     * <p>
     *  返回这个<code> GraphicAttribute </code>的提前。
     *  <code> GraphicAttribute </code>对象的提前是从渲染图形的点到渲染下一个字符或图形的点的距离。图形可以超越它的前进。
     * 
     * 
     * @return the advance of this <code>GraphicAttribute</code>.
     * @see #getBounds()
     */
    public abstract float getAdvance();

    /**
     * Returns a {@link Rectangle2D} that encloses all of the
     * bits drawn by this <code>GraphicAttribute</code> relative to the
     * rendering position.
     * A graphic may be rendered beyond its origin, ascent, descent,
     * or advance;  but if it is, this method's implementation must
     * indicate where the graphic is rendered.
     * Default bounds is the rectangle (0, -ascent, advance, ascent+descent).
     * <p>
     *  返回一个{@link Rectangle2D},它包含由相对于渲染位置的<code> GraphicAttribute </code>绘制的所有位。
     * 图形可以超越其起源,上升,下降或前进;但如果是这样,这个方法的实现必须指出图形的呈现位置。默认边界是矩形(0,-ascent,advance,上升+下降)。
     * 
     * 
     * @return a <code>Rectangle2D</code> that encloses all of the bits
     * rendered by this <code>GraphicAttribute</code>.
     */
    public Rectangle2D getBounds() {
        float ascent = getAscent();
        return new Rectangle2D.Float(0, -ascent,
                                        getAdvance(), ascent+getDescent());
    }

    /**
     * Return a {@link java.awt.Shape} that represents the region that
     * this <code>GraphicAttribute</code> renders.  This is used when a
     * {@link TextLayout} is requested to return the outline of the text.
     * The (untransformed) shape must not extend outside the rectangular
     * bounds returned by <code>getBounds</code>.
     * The default implementation returns the rectangle returned by
     * {@link #getBounds}, transformed by the provided {@link AffineTransform}
     * if present.
     * <p>
     *  返回一个{@link java.awt.Shape},代表这个<code> GraphicAttribute </code>呈现的区域。
     * 当请求{@link TextLayout}返回文本的大纲时使用。 (未转换的)形状不得延伸到<code> getBounds </code>返回的矩形边界之外。
     * 默认实现返回由{@link #getBounds}返回的矩形,由提供的{@link AffineTransform}(如果存在)转换。
     * 
     * 
     * @param tx an optional {@link AffineTransform} to apply to the
     *   outline of this <code>GraphicAttribute</code>. This can be null.
     * @return a <code>Shape</code> representing this graphic attribute,
     *   suitable for stroking or filling.
     * @since 1.6
     */
    public Shape getOutline(AffineTransform tx) {
        Shape b = getBounds();
        if (tx != null) {
            b = tx.createTransformedShape(b);
        }
        return b;
    }

    /**
     * Renders this <code>GraphicAttribute</code> at the specified
     * location.
     * <p>
     * 在指定的位置呈现此<code> GraphicAttribute </code>。
     * 
     * 
     * @param graphics the {@link Graphics2D} into which to render the
     * graphic
     * @param x the user-space X coordinate where the graphic is rendered
     * @param y the user-space Y coordinate where the graphic is rendered
     */
    public abstract void draw(Graphics2D graphics, float x, float y);

    /**
     * Returns the alignment of this <code>GraphicAttribute</code>.
     * Alignment can be to a particular baseline, or to the absolute top
     * or bottom of a line.
     * <p>
     *  返回此<code> GraphicAttribute </code>的对齐方式。对齐可以是到特定基线,或者到线的绝对顶部或底部。
     * 
     * 
     * @return the alignment of this <code>GraphicAttribute</code>.
     */
    public final int getAlignment() {

        return fAlignment;
    }

    /**
     * Returns the justification information for this
     * <code>GraphicAttribute</code>.  Subclasses
     * can override this method to provide different justification
     * information.
     * <p>
     *  返回此<code> GraphicAttribute </code>的对齐信息。子类可以覆盖此方法以提供不同的对齐信息。
     * 
     * @return a {@link GlyphJustificationInfo} object that contains the
     * justification information for this <code>GraphicAttribute</code>.
     */
    public GlyphJustificationInfo getJustificationInfo() {

        // should we cache this?
        float advance = getAdvance();

        return new GlyphJustificationInfo(
                                     advance,   // weight
                                     false,     // growAbsorb
                                     2,         // growPriority
                                     advance/3, // growLeftLimit
                                     advance/3, // growRightLimit
                                     false,     // shrinkAbsorb
                                     1,         // shrinkPriority
                                     0,         // shrinkLeftLimit
                                     0);        // shrinkRightLimit
    }
}
