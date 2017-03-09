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

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * The <code>ImageGraphicAttribute</code> class is an implementation of
 * {@link GraphicAttribute} which draws images in
 * a {@link TextLayout}.
 * <p>
 *  <code> ImageGraphicAttribute </code>类是{@link GraphicAttribute}的一个实现,它在{@link TextLayout}中绘制图像。
 * 
 * 
 * @see GraphicAttribute
 */

public final class ImageGraphicAttribute extends GraphicAttribute {

    private Image fImage;
    private float fImageWidth, fImageHeight;
    private float fOriginX, fOriginY;

    /**
     * Constucts an <code>ImageGraphicAttribute</code> from the specified
     * {@link Image}.  The origin is at (0,&nbsp;0).
     * <p>
     *  从指定的{@link Image}中产生一个<code> ImageGraphicAttribute </code>。起点为(0,&nbsp; 0)。
     * 
     * 
     * @param image the <code>Image</code> rendered by this
     * <code>ImageGraphicAttribute</code>.
     * This object keeps a reference to <code>image</code>.
     * @param alignment one of the alignments from this
     * <code>ImageGraphicAttribute</code>
     */
    public ImageGraphicAttribute(Image image, int alignment) {

        this(image, alignment, 0, 0);
    }

    /**
     * Constructs an <code>ImageGraphicAttribute</code> from the specified
     * <code>Image</code>. The point
     * (<code>originX</code>,&nbsp;<code>originY</code>) in the
     * <code>Image</code> appears at the origin of the
     * <code>ImageGraphicAttribute</code> within the text.
     * <p>
     *  从指定的<code> Image </code>构造一个<code> ImageGraphicAttribute </code>。
     *  <code> Image </code>中的<code> originX </code>,&nbsp; <code> originY </code>)出现在文本中<code> ImageGraphic
     * Attribute </code> 。
     *  从指定的<code> Image </code>构造一个<code> ImageGraphicAttribute </code>。
     * 
     * 
     * @param image the <code>Image</code> rendered by this
     * <code>ImageGraphicAttribute</code>.
     * This object keeps a reference to <code>image</code>.
     * @param alignment one of the alignments from this
     * <code>ImageGraphicAttribute</code>
     * @param originX the X coordinate of the point within
     * the <code>Image</code> that appears at the origin of the
     * <code>ImageGraphicAttribute</code> in the text line.
     * @param originY the Y coordinate of the point within
     * the <code>Image</code> that appears at the origin of the
     * <code>ImageGraphicAttribute</code> in the text line.
     */
    public ImageGraphicAttribute(Image image,
                                 int alignment,
                                 float originX,
                                 float originY) {

        super(alignment);

        // Can't clone image
        // fImage = (Image) image.clone();
        fImage = image;

        fImageWidth = image.getWidth(null);
        fImageHeight = image.getHeight(null);

        // ensure origin is in Image?
        fOriginX = originX;
        fOriginY = originY;
    }

    /**
     * Returns the ascent of this <code>ImageGraphicAttribute</code>.  The
     * ascent of an <code>ImageGraphicAttribute</code> is the distance
     * from the top of the image to the origin.
     * <p>
     *  返回此<code> ImageGraphicAttribute </code>的上升。 <code> ImageGraphicAttribute </code>的上升是从图像的顶部到原点的距离。
     * 
     * 
     * @return the ascent of this <code>ImageGraphicAttribute</code>.
     */
    public float getAscent() {

        return Math.max(0, fOriginY);
    }

    /**
     * Returns the descent of this <code>ImageGraphicAttribute</code>.
     * The descent of an <code>ImageGraphicAttribute</code> is the
     * distance from the origin to the bottom of the image.
     * <p>
     * 返回此<code> ImageGraphicAttribute </code>的下降。 <code> ImageGraphicAttribute </code>的下降是从原点到图像底部的距离。
     * 
     * 
     * @return the descent of this <code>ImageGraphicAttribute</code>.
     */
    public float getDescent() {

        return Math.max(0, fImageHeight-fOriginY);
    }

    /**
     * Returns the advance of this <code>ImageGraphicAttribute</code>.
     * The advance of an <code>ImageGraphicAttribute</code> is the
     * distance from the origin to the right edge of the image.
     * <p>
     *  返回此<code> ImageGraphicAttribute </code>的提前。 <code> ImageGraphicAttribute </code>的前进是从原点到图像右边缘的距离。
     * 
     * 
     * @return the advance of this <code>ImageGraphicAttribute</code>.
     */
    public float getAdvance() {

        return Math.max(0, fImageWidth-fOriginX);
    }

    /**
     * Returns a {@link Rectangle2D} that encloses all of the
     * bits rendered by this <code>ImageGraphicAttribute</code>, relative
     * to the rendering position.  A graphic can be rendered beyond its
     * origin, ascent, descent, or advance;  but if it is, this
     * method's implementation must indicate where the graphic is rendered.
     * <p>
     *  返回一个{@link Rectangle2D},它包含由此<code> ImageGraphicAttribute </code>呈现的所有位,相对于呈现位置。
     * 图形可以超越其起源,上升,下降或前进;但如果是这样,这个方法的实现必须指出图形的呈现位置。
     * 
     * 
     * @return a <code>Rectangle2D</code> that encloses all of the bits
     * rendered by this <code>ImageGraphicAttribute</code>.
     */
    public Rectangle2D getBounds() {

        return new Rectangle2D.Float(
                        -fOriginX, -fOriginY, fImageWidth, fImageHeight);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void draw(Graphics2D graphics, float x, float y) {

        graphics.drawImage(fImage, (int) (x-fOriginX), (int) (y-fOriginY), null);
    }

    /**
     * Returns a hashcode for this <code>ImageGraphicAttribute</code>.
     * <p>
     *  返回此<code> ImageGraphicAttribute </code>的哈希码。
     * 
     * 
     * @return  a hash code value for this object.
     */
    public int hashCode() {

        return fImage.hashCode();
    }

    /**
     * Compares this <code>ImageGraphicAttribute</code> to the specified
     * {@link Object}.
     * <p>
     *  将此<code> ImageGraphicAttribute </code>比较到指定的{@link对象}。
     * 
     * 
     * @param rhs the <code>Object</code> to compare for equality
     * @return <code>true</code> if this
     * <code>ImageGraphicAttribute</code> equals <code>rhs</code>;
     * <code>false</code> otherwise.
     */
    public boolean equals(Object rhs) {

        try {
            return equals((ImageGraphicAttribute) rhs);
        }
        catch(ClassCastException e) {
            return false;
        }
    }

    /**
     * Compares this <code>ImageGraphicAttribute</code> to the specified
     * <code>ImageGraphicAttribute</code>.
     * <p>
     *  将此<code> ImageGraphicAttribute </code>与指定的<code> ImageGraphicAttribute </code>进行比较。
     * 
     * @param rhs the <code>ImageGraphicAttribute</code> to compare for
     * equality
     * @return <code>true</code> if this
     * <code>ImageGraphicAttribute</code> equals <code>rhs</code>;
     * <code>false</code> otherwise.
     */
    public boolean equals(ImageGraphicAttribute rhs) {

        if (rhs == null) {
            return false;
        }

        if (this == rhs) {
            return true;
        }

        if (fOriginX != rhs.fOriginX || fOriginY != rhs.fOriginY) {
            return false;
        }

        if (getAlignment() != rhs.getAlignment()) {
            return false;
        }

        if (!fImage.equals(rhs.fImage)) {
            return false;
        }

        return true;
    }
}
