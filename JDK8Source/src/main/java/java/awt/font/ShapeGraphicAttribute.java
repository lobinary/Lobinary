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

import java.awt.Shape;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * The <code>ShapeGraphicAttribute</code> class is an implementation of
 * {@link GraphicAttribute} that draws shapes in a {@link TextLayout}.
 * <p>
 *  <code> ShapeGraphicAttribute </code>类是{@link GraphicAttribute}的一个实现,它在{@link TextLayout}中绘制形状。
 * 
 * 
 * @see GraphicAttribute
 */
public final class ShapeGraphicAttribute extends GraphicAttribute {

    private Shape fShape;
    private boolean fStroke;

    /**
     * A key indicating the shape should be stroked with a 1-pixel wide stroke.
     * <p>
     *  指示形状的键应该用1像素宽行程来描画。
     * 
     */
    public static final boolean STROKE = true;

    /**
     * A key indicating the shape should be filled.
     * <p>
     *  应该填充指示形状的键。
     * 
     */
    public static final boolean FILL = false;

    // cache shape bounds, since GeneralPath doesn't
    private Rectangle2D fShapeBounds;

    /**
     * Constructs a <code>ShapeGraphicAttribute</code> for the specified
     * {@link Shape}.
     * <p>
     *  为指定的{@link Shape}构造一个<code> ShapeGraphicAttribute </code>。
     * 
     * 
     * @param shape the <code>Shape</code> to render.  The
     * <code>Shape</code> is rendered with its origin at the origin of
     * this <code>ShapeGraphicAttribute</code> in the
     * host <code>TextLayout</code>.  This object maintains a reference to
     * <code>shape</code>.
     * @param alignment one of the alignments from this
     * <code>ShapeGraphicAttribute</code>.
     * @param stroke <code>true</code> if the <code>Shape</code> should be
     * stroked; <code>false</code> if the <code>Shape</code> should be
     * filled.
     */
    public ShapeGraphicAttribute(Shape shape,
                                 int alignment,
                                 boolean stroke) {

        super(alignment);

        fShape = shape;
        fStroke = stroke;
        fShapeBounds = fShape.getBounds2D();
    }

    /**
     * Returns the ascent of this <code>ShapeGraphicAttribute</code>.  The
     * ascent of a <code>ShapeGraphicAttribute</code> is the positive
     * distance from the origin of its <code>Shape</code> to the top of
     * bounds of its <code>Shape</code>.
     * <p>
     *  返回此<code> ShapeGraphicAttribute </code>的上升。
     *  <code> ShapeGraphicAttribute </code>的上升是从其<code> Shape </code>的起点到其<code> Shape </code>的边界的顶部的正距离。
     * 
     * 
     * @return the ascent of this <code>ShapeGraphicAttribute</code>.
     */
    public float getAscent() {

        return (float) Math.max(0, -fShapeBounds.getMinY());
    }

    /**
     * Returns the descent of this <code>ShapeGraphicAttribute</code>.
     * The descent of a <code>ShapeGraphicAttribute</code> is the distance
     * from the origin of its <code>Shape</code> to the bottom of the
     * bounds of its <code>Shape</code>.
     * <p>
     *  返回此<code> ShapeGraphicAttribute </code>的下降。
     *  <code> ShapeGraphicAttribute </code>的下降是从其<code> Shape </code>的原点到其<code> Shape </code>的边界的底部的距离。
     * 
     * 
     * @return the descent of this <code>ShapeGraphicAttribute</code>.
     */
    public float getDescent() {

        return (float) Math.max(0, fShapeBounds.getMaxY());
    }

    /**
     * Returns the advance of this <code>ShapeGraphicAttribute</code>.
     * The advance of a <code>ShapeGraphicAttribute</code> is the distance
     * from the origin of its <code>Shape</code> to the right side of the
     * bounds of its <code>Shape</code>.
     * <p>
     * 返回此<code> ShapeGraphicAttribute </code>的提前。
     *  <code> ShapeGraphicAttribute </code>的提前是从其<code> Shape </code>的原点到其<code> Shape </code>的边界的右侧的距离。
     * 
     * 
     * @return the advance of this <code>ShapeGraphicAttribute</code>.
     */
    public float getAdvance() {

        return (float) Math.max(0, fShapeBounds.getMaxX());
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void draw(Graphics2D graphics, float x, float y) {

        // translating graphics to draw Shape !!!
        graphics.translate((int)x, (int)y);

        try {
            if (fStroke == STROKE) {
                // REMIND: set stroke to correct size
                graphics.draw(fShape);
            }
            else {
                graphics.fill(fShape);
            }
        }
        finally {
            graphics.translate(-(int)x, -(int)y);
        }
    }

    /**
     * Returns a {@link Rectangle2D} that encloses all of the
     * bits drawn by this <code>ShapeGraphicAttribute</code> relative to
     * the rendering position.  A graphic can be rendered beyond its
     * origin, ascent, descent, or advance;  but if it does, this method's
     * implementation should indicate where the graphic is rendered.
     * <p>
     *  返回一个{@link Rectangle2D},它包含了由相对于渲染位置的<code> ShapeGraphicAttribute </code>绘制的所有位。
     * 图形可以超越其原点,上升,下降或前进;但如果是这样,这个方法的实现应该指示图形的呈现。
     * 
     * 
     * @return a <code>Rectangle2D</code> that encloses all of the bits
     * rendered by this <code>ShapeGraphicAttribute</code>.
     */
    public Rectangle2D getBounds() {

        Rectangle2D.Float bounds = new Rectangle2D.Float();
        bounds.setRect(fShapeBounds);

        if (fStroke == STROKE) {
            ++bounds.width;
            ++bounds.height;
        }

        return bounds;
    }

    /**
     * Return a {@link java.awt.Shape} that represents the region that
     * this <code>ShapeGraphicAttribute</code> renders.  This is used when a
     * {@link TextLayout} is requested to return the outline of the text.
     * The (untransformed) shape must not extend outside the rectangular
     * bounds returned by <code>getBounds</code>.
     * <p>
     *  返回一个{@link java.awt.Shape},它代表这个<code> ShapeGraphicAttribute </code>呈现的区域。
     * 当请求{@link TextLayout}返回文本的大纲时使用。 (未转换的)形状不得延伸到<code> getBounds </code>返回的矩形边界之外。
     * 
     * 
     * @param tx an optional {@link AffineTransform} to apply to the
     *   this <code>ShapeGraphicAttribute</code>. This can be null.
     * @return the <code>Shape</code> representing this graphic attribute,
     *   suitable for stroking or filling.
     * @since 1.6
     */
    public Shape getOutline(AffineTransform tx) {
        return tx == null ? fShape : tx.createTransformedShape(fShape);
    }

    /**
     * Returns a hashcode for this <code>ShapeGraphicAttribute</code>.
     * <p>
     *  返回此<code> ShapeGraphicAttribute </code>的哈希码。
     * 
     * 
     * @return  a hash code value for this
     * <code>ShapeGraphicAttribute</code>.
     */
    public int hashCode() {

        return fShape.hashCode();
    }

    /**
     * Compares this <code>ShapeGraphicAttribute</code> to the specified
     * <code>Object</code>.
     * <p>
     *  将此<code> ShapeGraphicAttribute </code>与指定的<code> Object </code>进行比较。
     * 
     * 
     * @param rhs the <code>Object</code> to compare for equality
     * @return <code>true</code> if this
     * <code>ShapeGraphicAttribute</code> equals <code>rhs</code>;
     * <code>false</code> otherwise.
     */
    public boolean equals(Object rhs) {

        try {
            return equals((ShapeGraphicAttribute) rhs);
        }
        catch(ClassCastException e) {
            return false;
        }
    }

    /**
     * Compares this <code>ShapeGraphicAttribute</code> to the specified
     * <code>ShapeGraphicAttribute</code>.
     * <p>
     *  将此<code> ShapeGraphicAttribute </code>与指定的<code> ShapeGraphicAttribute </code>进行比较。
     * 
     * @param rhs the <code>ShapeGraphicAttribute</code> to compare for
     * equality
     * @return <code>true</code> if this
     * <code>ShapeGraphicAttribute</code> equals <code>rhs</code>;
     * <code>false</code> otherwise.
     */
    public boolean equals(ShapeGraphicAttribute rhs) {

        if (rhs == null) {
            return false;
        }

        if (this == rhs) {
            return true;
        }

        if (fStroke != rhs.fStroke) {
            return false;
        }

        if (getAlignment() != rhs.getAlignment()) {
            return false;
        }

        if (!fShape.equals(rhs.fShape)) {
            return false;
        }

        return true;
    }
}
