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

package java.awt.image;

import java.awt.color.ICC_Profile;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.lang.annotation.Native;
import sun.awt.image.ImagingLib;

/**
 * This class implements a convolution from the source
 * to the destination.
 * Convolution using a convolution kernel is a spatial operation that
 * computes the output pixel from an input pixel by multiplying the kernel
 * with the surround of the input pixel.
 * This allows the output pixel to be affected by the immediate neighborhood
 * in a way that can be mathematically specified with a kernel.
 *<p>
 * This class operates with BufferedImage data in which color components are
 * premultiplied with the alpha component.  If the Source BufferedImage has
 * an alpha component, and the color components are not premultiplied with
 * the alpha component, then the data are premultiplied before being
 * convolved.  If the Destination has color components which are not
 * premultiplied, then alpha is divided out before storing into the
 * Destination (if alpha is 0, the color components are set to 0).  If the
 * Destination has no alpha component, then the resulting alpha is discarded
 * after first dividing it out of the color components.
 * <p>
 * Rasters are treated as having no alpha channel.  If the above treatment
 * of the alpha channel in BufferedImages is not desired, it may be avoided
 * by getting the Raster of a source BufferedImage and using the filter method
 * of this class which works with Rasters.
 * <p>
 * If a RenderingHints object is specified in the constructor, the
 * color rendering hint and the dithering hint may be used when color
 * conversion is required.
 *<p>
 * Note that the Source and the Destination may not be the same object.
 * <p>
 *  这个类实现从源到目的地的卷积。使用卷积核的卷积是通过将核与输入像素的环绕相乘来从输入像素计算输出像素的空间操作。这允许输出像素以可以用内核以数学方式指定的方式受到直接邻域的影响。
 * p>
 *  该类使用BufferedImage数据操作,其中颜色分量与alpha分量预乘。如果源缓冲图像具有alpha分量,并且颜色分量未与alpha分量预乘,则在卷积之前数据被预乘。
 * 如果目标具有未预乘的颜色分量,则在存储到目的地之前将alpha分割(如果alpha是0,则颜色分量被设置为0)。如果目标没有alpha分量,那么生成的alpha首先从颜色分量中分割出来后被丢弃。
 * <p>
 *  栅格被视为没有alpha通道。
 * 如果不希望在BufferedImages中对Alpha通道进行上述处理,则可以通过获取源BufferedImage的栅格并使用与Rasters一起工作的此类的过滤器方法来避免。
 * <p>
 * 如果在构造函数中指定了RenderingHints对象,则当需要颜色转换时可以使用颜色渲染提示和抖动提示。
 * p>
 *  请注意,源和目标可能不是同一个对象。
 * 
 * 
 * @see Kernel
 * @see java.awt.RenderingHints#KEY_COLOR_RENDERING
 * @see java.awt.RenderingHints#KEY_DITHERING
 */
public class ConvolveOp implements BufferedImageOp, RasterOp {
    Kernel kernel;
    int edgeHint;
    RenderingHints hints;
    /**
     * Edge condition constants.
     * <p>
     *  边缘条件常量。
     * 
     */

    /**
     * Pixels at the edge of the destination image are set to zero.  This
     * is the default.
     * <p>
     *  目标图像边缘的像素设置为零。这是默认值。
     * 
     */

    @Native public static final int EDGE_ZERO_FILL = 0;

    /**
     * Pixels at the edge of the source image are copied to
     * the corresponding pixels in the destination without modification.
     * <p>
     *  源图像的边缘处的像素被复制到目的地中的相应像素,而不进行修改。
     * 
     */
    @Native public static final int EDGE_NO_OP     = 1;

    /**
     * Constructs a ConvolveOp given a Kernel, an edge condition, and a
     * RenderingHints object (which may be null).
     * <p>
     *  构造一个ConvolveOp给定一个内核,边缘条件和一个RenderingHints对象(可能为null)。
     * 
     * 
     * @param kernel the specified <code>Kernel</code>
     * @param edgeCondition the specified edge condition
     * @param hints the specified <code>RenderingHints</code> object
     * @see Kernel
     * @see #EDGE_NO_OP
     * @see #EDGE_ZERO_FILL
     * @see java.awt.RenderingHints
     */
    public ConvolveOp(Kernel kernel, int edgeCondition, RenderingHints hints) {
        this.kernel   = kernel;
        this.edgeHint = edgeCondition;
        this.hints    = hints;
    }

    /**
     * Constructs a ConvolveOp given a Kernel.  The edge condition
     * will be EDGE_ZERO_FILL.
     * <p>
     *  构造一个ConvolveOp给定一个内核。边缘条件将为EDGE_ZERO_FILL。
     * 
     * 
     * @param kernel the specified <code>Kernel</code>
     * @see Kernel
     * @see #EDGE_ZERO_FILL
     */
    public ConvolveOp(Kernel kernel) {
        this.kernel   = kernel;
        this.edgeHint = EDGE_ZERO_FILL;
    }

    /**
     * Returns the edge condition.
     * <p>
     *  返回边缘条件。
     * 
     * 
     * @return the edge condition of this <code>ConvolveOp</code>.
     * @see #EDGE_NO_OP
     * @see #EDGE_ZERO_FILL
     */
    public int getEdgeCondition() {
        return edgeHint;
    }

    /**
     * Returns the Kernel.
     * <p>
     *  返回内核。
     * 
     * 
     * @return the <code>Kernel</code> of this <code>ConvolveOp</code>.
     */
    public final Kernel getKernel() {
        return (Kernel) kernel.clone();
    }

    /**
     * Performs a convolution on BufferedImages.  Each component of the
     * source image will be convolved (including the alpha component, if
     * present).
     * If the color model in the source image is not the same as that
     * in the destination image, the pixels will be converted
     * in the destination.  If the destination image is null,
     * a BufferedImage will be created with the source ColorModel.
     * The IllegalArgumentException may be thrown if the source is the
     * same as the destination.
     * <p>
     *  在BufferedImages上执行卷积。源图像的每个分量将被卷积(包括alpha分量,如果存在)。如果源图像中的颜色模型与目标图像中的颜色模型不同,则将在目标中转换像素。
     * 如果目标图像为null,将使用源ColorModel创建BufferedImage。如果源与目标相同,则可能抛出IllegalArgumentException。
     * 
     * 
     * @param src the source <code>BufferedImage</code> to filter
     * @param dst the destination <code>BufferedImage</code> for the
     *        filtered <code>src</code>
     * @return the filtered <code>BufferedImage</code>
     * @throws NullPointerException if <code>src</code> is <code>null</code>
     * @throws IllegalArgumentException if <code>src</code> equals
     *         <code>dst</code>
     * @throws ImagingOpException if <code>src</code> cannot be filtered
     */
    public final BufferedImage filter (BufferedImage src, BufferedImage dst) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (src == dst) {
            throw new IllegalArgumentException("src image cannot be the "+
                                               "same as the dst image");
        }

        boolean needToConvert = false;
        ColorModel srcCM = src.getColorModel();
        ColorModel dstCM;
        BufferedImage origDst = dst;

        // Can't convolve an IndexColorModel.  Need to expand it
        if (srcCM instanceof IndexColorModel) {
            IndexColorModel icm = (IndexColorModel) srcCM;
            src = icm.convertToIntDiscrete(src.getRaster(), false);
            srcCM = src.getColorModel();
        }

        if (dst == null) {
            dst = createCompatibleDestImage(src, null);
            dstCM = srcCM;
            origDst = dst;
        }
        else {
            dstCM = dst.getColorModel();
            if (srcCM.getColorSpace().getType() !=
                dstCM.getColorSpace().getType())
            {
                needToConvert = true;
                dst = createCompatibleDestImage(src, null);
                dstCM = dst.getColorModel();
            }
            else if (dstCM instanceof IndexColorModel) {
                dst = createCompatibleDestImage(src, null);
                dstCM = dst.getColorModel();
            }
        }

        if (ImagingLib.filter(this, src, dst) == null) {
            throw new ImagingOpException ("Unable to convolve src image");
        }

        if (needToConvert) {
            ColorConvertOp ccop = new ColorConvertOp(hints);
            ccop.filter(dst, origDst);
        }
        else if (origDst != dst) {
            java.awt.Graphics2D g = origDst.createGraphics();
            try {
                g.drawImage(dst, 0, 0, null);
            } finally {
                g.dispose();
            }
        }

        return origDst;
    }

    /**
     * Performs a convolution on Rasters.  Each band of the source Raster
     * will be convolved.
     * The source and destination must have the same number of bands.
     * If the destination Raster is null, a new Raster will be created.
     * The IllegalArgumentException may be thrown if the source is
     * the same as the destination.
     * <p>
     *  对栅格执行卷积。源光栅的每个带将被卷积。源和目标必须具有相同数量的波段。如果目标栅格为空,将创建一个新的栅格。如果源与目标相同,则可能抛出IllegalArgumentException。
     * 
     * 
     * @param src the source <code>Raster</code> to filter
     * @param dst the destination <code>WritableRaster</code> for the
     *        filtered <code>src</code>
     * @return the filtered <code>WritableRaster</code>
     * @throws NullPointerException if <code>src</code> is <code>null</code>
     * @throws ImagingOpException if <code>src</code> and <code>dst</code>
     *         do not have the same number of bands
     * @throws ImagingOpException if <code>src</code> cannot be filtered
     * @throws IllegalArgumentException if <code>src</code> equals
     *         <code>dst</code>
     */
    public final WritableRaster filter (Raster src, WritableRaster dst) {
        if (dst == null) {
            dst = createCompatibleDestRaster(src);
        }
        else if (src == dst) {
            throw new IllegalArgumentException("src image cannot be the "+
                                               "same as the dst image");
        }
        else if (src.getNumBands() != dst.getNumBands()) {
            throw new ImagingOpException("Different number of bands in src "+
                                         " and dst Rasters");
        }

        if (ImagingLib.filter(this, src, dst) == null) {
            throw new ImagingOpException ("Unable to convolve src image");
        }

        return dst;
    }

    /**
     * Creates a zeroed destination image with the correct size and number
     * of bands.  If destCM is null, an appropriate ColorModel will be used.
     * <p>
     * 使用正确的大小和带数创建一个调零的目标图像。如果destCM为null,将使用适当的ColorModel。
     * 
     * 
     * @param src       Source image for the filter operation.
     * @param destCM    ColorModel of the destination.  Can be null.
     * @return a destination <code>BufferedImage</code> with the correct
     *         size and number of bands.
     */
    public BufferedImage createCompatibleDestImage(BufferedImage src,
                                                   ColorModel destCM) {
        BufferedImage image;

        int w = src.getWidth();
        int h = src.getHeight();

        WritableRaster wr = null;

        if (destCM == null) {
            destCM = src.getColorModel();
            // Not much support for ICM
            if (destCM instanceof IndexColorModel) {
                destCM = ColorModel.getRGBdefault();
            } else {
                /* Create destination image as similar to the source
                 *  as it possible...
                 * <p>
                 *  因为它可能...
                 * 
                 */
                wr = src.getData().createCompatibleWritableRaster(w, h);
            }
        }

        if (wr == null) {
            /* This is the case when destination color model
             * was explicitly specified (and it may be not compatible
             * with source raster structure) or source is indexed image.
             * We should use destination color model to create compatible
             * destination raster here.
             * <p>
             *  (它可能与源栅格结构不兼容)或源是索引图像。我们应该使用目的地颜色模型在这里创建兼容的目标栅格。
             * 
             */
            wr = destCM.createCompatibleWritableRaster(w, h);
        }

        image = new BufferedImage (destCM, wr,
                                   destCM.isAlphaPremultiplied(), null);

        return image;
    }

    /**
     * Creates a zeroed destination Raster with the correct size and number
     * of bands, given this source.
     * <p>
     *  在给定此源的情况下,创建具有正确大小和带数的零位置目标光栅。
     * 
     */
    public WritableRaster createCompatibleDestRaster(Raster src) {
        return src.createCompatibleWritableRaster();
    }

    /**
     * Returns the bounding box of the filtered destination image.  Since
     * this is not a geometric operation, the bounding box does not
     * change.
     * <p>
     *  返回过滤的目标图像的边框。由于这不是几何操作,边界框不会改变。
     * 
     */
    public final Rectangle2D getBounds2D(BufferedImage src) {
        return getBounds2D(src.getRaster());
    }

    /**
     * Returns the bounding box of the filtered destination Raster.  Since
     * this is not a geometric operation, the bounding box does not
     * change.
     * <p>
     *  返回过滤的目标栅格的边界框。由于这不是几何操作,边界框不会改变。
     * 
     */
    public final Rectangle2D getBounds2D(Raster src) {
        return src.getBounds();
    }

    /**
     * Returns the location of the destination point given a
     * point in the source.  If dstPt is non-null, it will
     * be used to hold the return value.  Since this is not a geometric
     * operation, the srcPt will equal the dstPt.
     * <p>
     *  返回给定源中的点的目标点的位置。如果dstPt为非空,它将用于保存返回值。因为这不是几何操作,所以srcPt将等于dstPt。
     * 
     */
    public final Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
        if (dstPt == null) {
            dstPt = new Point2D.Float();
        }
        dstPt.setLocation(srcPt.getX(), srcPt.getY());

        return dstPt;
    }

    /**
     * Returns the rendering hints for this op.
     * <p>
     *  返回此操作的渲染提示。
     */
    public final RenderingHints getRenderingHints() {
        return hints;
    }
}
