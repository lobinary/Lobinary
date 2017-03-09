/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.image;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.awt.RenderingHints;

/**
 * This interface describes single-input/single-output
 * operations performed on <CODE>BufferedImage</CODE> objects.
 * It is implemented by <CODE>AffineTransformOp</CODE>,
 * <CODE>ConvolveOp</CODE>, <CODE>ColorConvertOp</CODE>, <CODE>RescaleOp</CODE>,
 * and <CODE>LookupOp</CODE>.  These objects can be passed into
 * a <CODE>BufferedImageFilter</CODE> to operate on a
 * <CODE>BufferedImage</CODE> in the
 * ImageProducer-ImageFilter-ImageConsumer paradigm.
 * <p>
 * Classes that implement this
 * interface must specify whether or not they allow in-place filtering--
 * filter operations where the source object is equal to the destination
 * object.
 * <p>
 * This interface cannot be used to describe more sophisticated operations
 * such as those that take multiple sources. Note that this restriction also
 * means that the values of the destination pixels prior to the operation are
 * not used as input to the filter operation.

 * <p>
 *  此接口描述在<CODE> BufferedImage </CODE>对象上执行的单输入/单输出操作。
 * 它由<CODE> AffineTransformOp </CODE>,<CODE> ConvolveOp </CODE>,<CODE> ColorConvertOp </CODE>,<CODE> Res
 * caleOp </CODE>和<CODE> LookupOp </CODE>实现。
 *  此接口描述在<CODE> BufferedImage </CODE>对象上执行的单输入/单输出操作。
 * 这些对象可以传递到<CODE> BufferedImageFilter </CODE>中,以在ImageProducer-ImageFilter-ImageConsumer范例中的<CODE> Buff
 * eredImage </CODE>上操作。
 *  此接口描述在<CODE> BufferedImage </CODE>对象上执行的单输入/单输出操作。
 * <p>
 *  实现此接口的类必须指定它们是否允许原位过滤,即源对象等于目标对象的过滤操作。
 * <p>
 *  此接口不能用于描述更复杂的操作,例如那些需要多个源的操作。注意,该限制还意味着操作之前的目的地像素的值不用作滤波操作的输入。
 * 
 * 
 * @see BufferedImage
 * @see BufferedImageFilter
 * @see AffineTransformOp
 * @see BandCombineOp
 * @see ColorConvertOp
 * @see ConvolveOp
 * @see LookupOp
 * @see RescaleOp
 */
public interface BufferedImageOp {
    /**
     * Performs a single-input/single-output operation on a
     * <CODE>BufferedImage</CODE>.
     * If the color models for the two images do not match, a color
     * conversion into the destination color model is performed.
     * If the destination image is null,
     * a <CODE>BufferedImage</CODE> with an appropriate <CODE>ColorModel</CODE>
     * is created.
     * <p>
     * An <CODE>IllegalArgumentException</CODE> may be thrown if the source
     * and/or destination image is incompatible with the types of images       $
     * allowed by the class implementing this filter.
     *
     * <p>
     *  在<CODE> BufferedImage </CODE>上执行单输入/单输出操作。如果两个图像的颜色模型不匹配,则执行到目的地颜色模型的颜色转换。
     * 如果目标图像为null,则创建具有适当的<CODE> ColorModel </CODE>的<CODE> BufferedImage </CODE>。
     * <p>
     * 如果源和/或目标映像与实现此过滤器的类所允许的图像类型不兼容,则可能会抛出<CODE> IllegalArgumentException </CODE>。
     * 
     * 
     * @param src The <CODE>BufferedImage</CODE> to be filtered
     * @param dest The <CODE>BufferedImage</CODE> in which to store the results$
     *
     * @return The filtered <CODE>BufferedImage</CODE>.
     *
     * @throws IllegalArgumentException If the source and/or destination
     * image is not compatible with the types of images allowed by the class
     * implementing this filter.
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest);

    /**
     * Returns the bounding box of the filtered destination image.
     * An <CODE>IllegalArgumentException</CODE> may be thrown if the source
     * image is incompatible with the types of images allowed
     * by the class implementing this filter.
     *
     * <p>
     *  返回过滤的目标图像的边框。如果源图像与实现此过滤器的类所允许的图像类型不兼容,则可能会抛出<CODE> IllegalArgumentException </CODE>。
     * 
     * 
     * @param src The <CODE>BufferedImage</CODE> to be filtered
     *
     * @return The <CODE>Rectangle2D</CODE> representing the destination
     * image's bounding box.
     */
    public Rectangle2D getBounds2D (BufferedImage src);

    /**
     * Creates a zeroed destination image with the correct size and number of
     * bands.
     * An <CODE>IllegalArgumentException</CODE> may be thrown if the source
     * image is incompatible with the types of images allowed
     * by the class implementing this filter.
     *
     * <p>
     *  使用正确的大小和带数创建一个调零的目标图像。如果源图像与实现此过滤器的类所允许的图像类型不兼容,则可能会抛出<CODE> IllegalArgumentException </CODE>。
     * 
     * 
     * @param src The <CODE>BufferedImage</CODE> to be filtered
     * @param destCM <CODE>ColorModel</CODE> of the destination.  If null,
     * the <CODE>ColorModel</CODE> of the source is used.
     *
     * @return The zeroed destination image.
     */
    public BufferedImage createCompatibleDestImage (BufferedImage src,
                                                    ColorModel destCM);

    /**
     * Returns the location of the corresponding destination point given a
     * point in the source image.  If <CODE>dstPt</CODE> is specified, it
     * is used to hold the return value.
     * <p>
     *  返回给定源图像中的点的相应目标点的位置。如果指定<CODE> dstPt </CODE>,它将用于保存返回值。
     * 
     * 
     * @param srcPt the <code>Point2D</code> that represents the point in
     * the source image
     * @param dstPt The <CODE>Point2D</CODE> in which to store the result
     *
     * @return The <CODE>Point2D</CODE> in the destination image that
     * corresponds to the specified point in the source image.
     */
    public Point2D getPoint2D (Point2D srcPt, Point2D dstPt);

    /**
     * Returns the rendering hints for this operation.
     *
     * <p>
     *  返回此操作的渲染提示。
     * 
     * @return The <CODE>RenderingHints</CODE> object for this
     * <CODE>BufferedImageOp</CODE>.  Returns
     * null if no hints have been set.
     */
    public RenderingHints getRenderingHints();
}
