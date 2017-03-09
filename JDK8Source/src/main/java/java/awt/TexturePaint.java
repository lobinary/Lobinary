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

package java.awt;

import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * The <code>TexturePaint</code> class provides a way to fill a
 * {@link Shape} with a texture that is specified as
 * a {@link BufferedImage}. The size of the <code>BufferedImage</code>
 * object should be small because the <code>BufferedImage</code> data
 * is copied by the <code>TexturePaint</code> object.
 * At construction time, the texture is anchored to the upper
 * left corner of a {@link Rectangle2D} that is
 * specified in user space.  Texture is computed for
 * locations in the device space by conceptually replicating the
 * specified <code>Rectangle2D</code> infinitely in all directions
 * in user space and mapping the <code>BufferedImage</code> to each
 * replicated <code>Rectangle2D</code>.
 * <p>
 *  <code> TexturePaint </code>类提供了一种使用指定为{@link BufferedImage}的纹理填充{@link Shape}的方法。
 *  <code> BufferedImage </code>对象的大小应该很小,因为<code> BufferedImage </code>数据是由<code> TexturePaint </code>对
 * 象复制的。
 *  <code> TexturePaint </code>类提供了一种使用指定为{@link BufferedImage}的纹理填充{@link Shape}的方法。
 * 在构造时,纹理锚定在用户空间中指定的{@link Rectangle2D}的左上角。
 * 通过在用户空间中的所有方向上无限地复制指定的<code> Rectangle2D </code>,并将<code> BufferedImage </code>映射到每个复制的<code> Rectang
 * le2D </code> >。
 * 在构造时,纹理锚定在用户空间中指定的{@link Rectangle2D}的左上角。
 * 
 * 
 * @see Paint
 * @see Graphics2D#setPaint
 * @version 1.48, 06/05/07
 */

public class TexturePaint implements Paint {

    BufferedImage bufImg;
    double tx;
    double ty;
    double sx;
    double sy;

    /**
     * Constructs a <code>TexturePaint</code> object.
     * <p>
     *  构造一个<code> TexturePaint </code>对象。
     * 
     * 
     * @param txtr the <code>BufferedImage</code> object with the texture
     * used for painting
     * @param anchor the <code>Rectangle2D</code> in user space used to
     * anchor and replicate the texture
     */
    public TexturePaint(BufferedImage txtr,
                        Rectangle2D anchor) {
        this.bufImg = txtr;
        this.tx = anchor.getX();
        this.ty = anchor.getY();
        this.sx = anchor.getWidth() / bufImg.getWidth();
        this.sy = anchor.getHeight() / bufImg.getHeight();
    }

    /**
     * Returns the <code>BufferedImage</code> texture used to
     * fill the shapes.
     * <p>
     *  返回用于填充形状的<code> BufferedImage </code>纹理。
     * 
     * 
     * @return a <code>BufferedImage</code>.
     */
    public BufferedImage getImage() {
        return bufImg;
    }

    /**
     * Returns a copy of the anchor rectangle which positions and
     * sizes the textured image.
     * <p>
     *  返回锚定矩形的副本,其将纹理图像的位置和大小。
     * 
     * 
     * @return the <code>Rectangle2D</code> used to anchor and
     * size this <code>TexturePaint</code>.
     */
    public Rectangle2D getAnchorRect() {
        return new Rectangle2D.Double(tx, ty,
                                      sx * bufImg.getWidth(),
                                      sy * bufImg.getHeight());
    }

    /**
     * Creates and returns a {@link PaintContext} used to
     * generate a tiled image pattern.
     * See the {@link Paint#createContext specification} of the
     * method in the {@link Paint} interface for information
     * on null parameter handling.
     *
     * <p>
     *  创建并返回用于生成平铺图像模式的{@link PaintContext}。
     * 有关空参数处理的信息,请参阅{@link Paint}界面中的方法的{@link Paint#createContext specification}。
     * 
     * 
     * @param cm the preferred {@link ColorModel} which represents the most convenient
     *           format for the caller to receive the pixel data, or {@code null}
     *           if there is no preference.
     * @param deviceBounds the device space bounding box
     *                     of the graphics primitive being rendered.
     * @param userBounds the user space bounding box
     *                   of the graphics primitive being rendered.
     * @param xform the {@link AffineTransform} from user
     *              space into device space.
     * @param hints the set of hints that the context object can use to
     *              choose between rendering alternatives.
     * @return the {@code PaintContext} for
     *         generating color patterns.
     * @see Paint
     * @see PaintContext
     * @see ColorModel
     * @see Rectangle
     * @see Rectangle2D
     * @see AffineTransform
     * @see RenderingHints
     */
    public PaintContext createContext(ColorModel cm,
                                      Rectangle deviceBounds,
                                      Rectangle2D userBounds,
                                      AffineTransform xform,
                                      RenderingHints hints) {
        if (xform == null) {
            xform = new AffineTransform();
        } else {
            xform = (AffineTransform) xform.clone();
        }
        xform.translate(tx, ty);
        xform.scale(sx, sy);

        return TexturePaintContext.getContext(bufImg, xform, hints,
                                              deviceBounds);
    }

    /**
     * Returns the transparency mode for this <code>TexturePaint</code>.
     * <p>
     * 
     * @return the transparency mode for this <code>TexturePaint</code>
     * as an integer value.
     * @see Transparency
     */
    public int getTransparency() {
        return (bufImg.getColorModel()).getTransparency();
    }

}
