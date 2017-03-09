/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.color.ColorSpace;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.RenderingHints;
import sun.awt.image.ImagingLib;

/**
 * This class performs a pixel-by-pixel rescaling of the data in the
 * source image by multiplying the sample values for each pixel by a scale
 * factor and then adding an offset. The scaled sample values are clipped
 * to the minimum/maximum representable in the destination image.
 * <p>
 * The pseudo code for the rescaling operation is as follows:
 * <pre>
 *for each pixel from Source object {
 *    for each band/component of the pixel {
 *        dstElement = (srcElement*scaleFactor) + offset
 *    }
 *}
 * </pre>
 * <p>
 * For Rasters, rescaling operates on bands.  The number of
 * sets of scaling constants may be one, in which case the same constants
 * are applied to all bands, or it must equal the number of Source
 * Raster bands.
 * <p>
 * For BufferedImages, rescaling operates on color and alpha components.
 * The number of sets of scaling constants may be one, in which case the
 * same constants are applied to all color (but not alpha) components.
 * Otherwise, the  number of sets of scaling constants may
 * equal the number of Source color components, in which case no
 * rescaling of the alpha component (if present) is performed.
 * If neither of these cases apply, the number of sets of scaling constants
 * must equal the number of Source color components plus alpha components,
 * in which case all color and alpha components are rescaled.
 * <p>
 * BufferedImage sources with premultiplied alpha data are treated in the same
 * manner as non-premultiplied images for purposes of rescaling.  That is,
 * the rescaling is done per band on the raw data of the BufferedImage source
 * without regard to whether the data is premultiplied.  If a color conversion
 * is required to the destination ColorModel, the premultiplied state of
 * both source and destination will be taken into account for this step.
 * <p>
 * Images with an IndexColorModel cannot be rescaled.
 * <p>
 * If a RenderingHints object is specified in the constructor, the
 * color rendering hint and the dithering hint may be used when color
 * conversion is required.
 * <p>
 * Note that in-place operation is allowed (i.e. the source and destination can
 * be the same object).
 * <p>
 *  该类通过将每个像素的样本值乘以比例因子,然后添加偏移量,来执行源图像中的数据的逐像素重新缩放。缩放的样本值被剪切到目标图像中可表示的最小/最大值。
 * <p>
 *  用于重新缩放操作的伪代码如下：
 * <pre>
 *  或来自Source对象的每个像素{对于像素的每个带/分量{dstElement =(srcElement * scaleFactor)+ offset}
 * 
 * </pre>
 * <p>
 *  对于栅格,重新缩放在波段上运行。缩放常数的集合的数量可以是一个,在这种情况下,相同的常数应用于所有频带,或者其必须等于源光栅频带的数量。
 * <p>
 *  对于BufferedImages,重新缩放操作的颜色和alpha组件。缩放常数的集合的数量可以是一个,在这种情况下,相同的常数被应用于所有颜色(但不是α)分量。
 * 否则,缩放常数的集合的数量可以等于源颜色分量的数量,在这种情况下不执行alpha分量(如果存在)的重新缩放。
 * 如果这些情况都不适用,则缩放常数集合的数量必须等于源颜色分量加alpha分量的数量,在这种情况下,所有颜色和alpha分量被重新缩放。
 * <p>
 * 具有预乘α数据的BufferedImage源将以与非预乘图像相同的方式处理,以进行重新缩放。也就是说,对于BufferedImage源的原始数据,每个频带进行重新缩放,而不考虑数据是否被预乘。
 * 如果需要对目标ColorModel进行颜色转换,则此步骤将考虑源和目标的预乘状态。
 * <p>
 *  不能重新缩放带有IndexColorModel的图像。
 * <p>
 *  如果在构造函数中指定了RenderingHints对象,则当需要颜色转换时可以使用颜色渲染提示和抖动提示。
 * <p>
 *  注意,允许就地操作(即,源和目的地可以是相同的对象)。
 * 
 * 
 * @see java.awt.RenderingHints#KEY_COLOR_RENDERING
 * @see java.awt.RenderingHints#KEY_DITHERING
 */
public class RescaleOp implements BufferedImageOp, RasterOp {
    float[] scaleFactors;
    float[] offsets;
    int length = 0;
    RenderingHints hints;

    private int srcNbits;
    private int dstNbits;


    /**
     * Constructs a new RescaleOp with the desired scale factors
     * and offsets.  The length of the scaleFactor and offset arrays
     * must meet the restrictions stated in the class comments above.
     * The RenderingHints argument may be null.
     * <p>
     *  构造具有所需缩放因子和偏移量的新RescaleOp。 scaleFactor和偏移数组的长度必须满足上面类注释中所述的限制。 RenderingHints参数可以为null。
     * 
     * 
     * @param scaleFactors the specified scale factors
     * @param offsets the specified offsets
     * @param hints the specified <code>RenderingHints</code>, or
     *        <code>null</code>
     */
    public RescaleOp (float[] scaleFactors, float[] offsets,
                      RenderingHints hints) {
        length = scaleFactors.length;
        if (length > offsets.length) length = offsets.length;

        this.scaleFactors = new float[length];
        this.offsets      = new float[length];
        for (int i=0; i < length; i++) {
            this.scaleFactors[i] = scaleFactors[i];
            this.offsets[i]      = offsets[i];
        }
        this.hints = hints;
    }

    /**
     * Constructs a new RescaleOp with the desired scale factor
     * and offset.  The scaleFactor and offset will be applied to
     * all bands in a source Raster and to all color (but not alpha)
     * components in a BufferedImage.
     * The RenderingHints argument may be null.
     * <p>
     *  构造具有所需比例因子和偏移量的新RescaleOp。 scaleFactor和offset将应用于源栅格中的所有波段,以及BufferedImage中的所有颜色(但不是alpha)组件。
     *  RenderingHints参数可以为null。
     * 
     * 
     * @param scaleFactor the specified scale factor
     * @param offset the specified offset
     * @param hints the specified <code>RenderingHints</code>, or
     *        <code>null</code>
     */
    public RescaleOp (float scaleFactor, float offset, RenderingHints hints) {
        length = 1;
        this.scaleFactors = new float[1];
        this.offsets      = new float[1];
        this.scaleFactors[0] = scaleFactor;
        this.offsets[0]       = offset;
        this.hints = hints;
    }

    /**
     * Returns the scale factors in the given array. The array is also
     * returned for convenience.  If scaleFactors is null, a new array
     * will be allocated.
     * <p>
     *  返回给定数组中的缩放因子。为了方便,也返回数组。如果scaleFactors为null,将分配一个新数组。
     * 
     * 
     * @param scaleFactors the array to contain the scale factors of
     *        this <code>RescaleOp</code>
     * @return the scale factors of this <code>RescaleOp</code>.
     */
    final public float[] getScaleFactors (float scaleFactors[]) {
        if (scaleFactors == null) {
            return (float[]) this.scaleFactors.clone();
        }
        System.arraycopy (this.scaleFactors, 0, scaleFactors, 0,
                          Math.min(this.scaleFactors.length,
                                   scaleFactors.length));
        return scaleFactors;
    }

    /**
     * Returns the offsets in the given array. The array is also returned
     * for convenience.  If offsets is null, a new array
     * will be allocated.
     * <p>
     * 返回给定数组中的偏移量。为了方便,也返回数组。如果offsets为null,将分配一个新数组。
     * 
     * 
     * @param offsets the array to contain the offsets of
     *        this <code>RescaleOp</code>
     * @return the offsets of this <code>RescaleOp</code>.
     */
    final public float[] getOffsets(float offsets[]) {
        if (offsets == null) {
            return (float[]) this.offsets.clone();
        }

        System.arraycopy (this.offsets, 0, offsets, 0,
                          Math.min(this.offsets.length, offsets.length));
        return offsets;
    }

    /**
     * Returns the number of scaling factors and offsets used in this
     * RescaleOp.
     * <p>
     *  返回此RescaleOp中使用的缩放因子和偏移量的数量。
     * 
     * 
     * @return the number of scaling factors and offsets of this
     *         <code>RescaleOp</code>.
     */
    final public int getNumFactors() {
        return length;
    }


    /**
     * Creates a ByteLookupTable to implement the rescale.
     * The table may have either a SHORT or BYTE input.
     * <p>
     *  创建一个ByteLookupTable来实现rescale。表可以具有SHORT或BYTE输入。
     * 
     * 
     * @param nElems    Number of elements the table is to have.
     *                  This will generally be 256 for byte and
     *                  65536 for short.
     */
    private ByteLookupTable createByteLut(float scale[],
                                          float off[],
                                          int   nBands,
                                          int   nElems) {

        byte[][]        lutData = new byte[scale.length][nElems];

        for (int band=0; band<scale.length; band++) {
            float  bandScale   = scale[band];
            float  bandOff     = off[band];
            byte[] bandLutData = lutData[band];
            for (int i=0; i<nElems; i++) {
                int val = (int)(i*bandScale + bandOff);
                if ((val & 0xffffff00) != 0) {
                    if (val < 0) {
                        val = 0;
                    } else {
                        val = 255;
                    }
                }
                bandLutData[i] = (byte)val;
            }

        }

        return new ByteLookupTable(0, lutData);
    }

    /**
     * Creates a ShortLookupTable to implement the rescale.
     * The table may have either a SHORT or BYTE input.
     * <p>
     *  创建ShortLookupTable以实现重定比例。表可以具有SHORT或BYTE输入。
     * 
     * 
     * @param nElems    Number of elements the table is to have.
     *                  This will generally be 256 for byte and
     *                  65536 for short.
     */
    private ShortLookupTable createShortLut(float scale[],
                                            float off[],
                                            int   nBands,
                                            int   nElems) {

        short[][]        lutData = new short[scale.length][nElems];

        for (int band=0; band<scale.length; band++) {
            float   bandScale   = scale[band];
            float   bandOff     = off[band];
            short[] bandLutData = lutData[band];
            for (int i=0; i<nElems; i++) {
                int val = (int)(i*bandScale + bandOff);
                if ((val & 0xffff0000) != 0) {
                    if (val < 0) {
                        val = 0;
                    } else {
                        val = 65535;
                    }
                }
                bandLutData[i] = (short)val;
            }
        }

        return new ShortLookupTable(0, lutData);
    }


    /**
     * Determines if the rescale can be performed as a lookup.
     * The dst must be a byte or short type.
     * The src must be less than 16 bits.
     * All source band sizes must be the same and all dst band sizes
     * must be the same.
     * <p>
     *  确定重新缩放是否可以作为查找执行。 dst必须是字节或短类型。 src必须小于16位。所有源带大小必须相同,并且所有dst带大小必须相同。
     * 
     */
    private boolean canUseLookup(Raster src, Raster dst) {

        //
        // Check that the src datatype is either a BYTE or SHORT
        //
        int datatype = src.getDataBuffer().getDataType();
        if(datatype != DataBuffer.TYPE_BYTE &&
           datatype != DataBuffer.TYPE_USHORT) {
            return false;
        }

        //
        // Check dst sample sizes. All must be 8 or 16 bits.
        //
        SampleModel dstSM = dst.getSampleModel();
        dstNbits = dstSM.getSampleSize(0);

        if (!(dstNbits == 8 || dstNbits == 16)) {
            return false;
        }
        for (int i=1; i<src.getNumBands(); i++) {
            int bandSize = dstSM.getSampleSize(i);
            if (bandSize != dstNbits) {
                return false;
            }
        }

        //
        // Check src sample sizes. All must be the same size
        //
        SampleModel srcSM = src.getSampleModel();
        srcNbits = srcSM.getSampleSize(0);
        if (srcNbits > 16) {
            return false;
        }
        for (int i=1; i<src.getNumBands(); i++) {
            int bandSize = srcSM.getSampleSize(i);
            if (bandSize != srcNbits) {
                return false;
            }
        }

        return true;
    }

    /**
     * Rescales the source BufferedImage.
     * If the color model in the source image is not the same as that
     * in the destination image, the pixels will be converted
     * in the destination.  If the destination image is null,
     * a BufferedImage will be created with the source ColorModel.
     * An IllegalArgumentException may be thrown if the number of
     * scaling factors/offsets in this object does not meet the
     * restrictions stated in the class comments above, or if the
     * source image has an IndexColorModel.
     * <p>
     *  调整源BufferedImage的大小。如果源图像中的颜色模型与目标图像中的颜色模型不同,则将在目标中转换像素。如果目标图像为null,将使用源ColorModel创建BufferedImage。
     * 如果此对象中缩放因子/偏移的数量不满足上述类注释中所述的限制,或者源图像具有IndexColorModel,则可能抛出IllegalArgumentException。
     * 
     * 
     * @param src the <code>BufferedImage</code> to be filtered
     * @param dst the destination for the filtering operation
     *            or <code>null</code>
     * @return the filtered <code>BufferedImage</code>.
     * @throws IllegalArgumentException if the <code>ColorModel</code>
     *         of <code>src</code> is an <code>IndexColorModel</code>,
     *         or if the number of scaling factors and offsets in this
     *         <code>RescaleOp</code> do not meet the requirements
     *         stated in the class comments.
     */
    public final BufferedImage filter (BufferedImage src, BufferedImage dst) {
        ColorModel srcCM = src.getColorModel();
        ColorModel dstCM;
        int numBands = srcCM.getNumColorComponents();


        if (srcCM instanceof IndexColorModel) {
            throw new
                IllegalArgumentException("Rescaling cannot be "+
                                         "performed on an indexed image");
        }
        if (length != 1 && length != numBands &&
            length != srcCM.getNumComponents())
        {
            throw new IllegalArgumentException("Number of scaling constants "+
                                               "does not equal the number of"+
                                               " of color or color/alpha "+
                                               " components");
        }

        boolean needToConvert = false;

        // Include alpha
        if (length > numBands && srcCM.hasAlpha()) {
            length = numBands+1;
        }

        int width = src.getWidth();
        int height = src.getHeight();

        if (dst == null) {
            dst = createCompatibleDestImage(src, null);
            dstCM = srcCM;
        }
        else {
            if (width != dst.getWidth()) {
                throw new
                    IllegalArgumentException("Src width ("+width+
                                             ") not equal to dst width ("+
                                             dst.getWidth()+")");
            }
            if (height != dst.getHeight()) {
                throw new
                    IllegalArgumentException("Src height ("+height+
                                             ") not equal to dst height ("+
                                             dst.getHeight()+")");
            }

            dstCM = dst.getColorModel();
            if(srcCM.getColorSpace().getType() !=
               dstCM.getColorSpace().getType()) {
                needToConvert = true;
                dst = createCompatibleDestImage(src, null);
            }

        }

        BufferedImage origDst = dst;

        //
        // Try to use a native BI rescale operation first
        //
        if (ImagingLib.filter(this, src, dst) == null) {
            //
            // Native BI rescale failed - convert to rasters
            //
            WritableRaster srcRaster = src.getRaster();
            WritableRaster dstRaster = dst.getRaster();

            if (srcCM.hasAlpha()) {
                if (numBands-1 == length || length == 1) {
                    int minx = srcRaster.getMinX();
                    int miny = srcRaster.getMinY();
                    int[] bands = new int[numBands-1];
                    for (int i=0; i < numBands-1; i++) {
                        bands[i] = i;
                    }
                    srcRaster =
                        srcRaster.createWritableChild(minx, miny,
                                                      srcRaster.getWidth(),
                                                      srcRaster.getHeight(),
                                                      minx, miny,
                                                      bands);
                }
            }
            if (dstCM.hasAlpha()) {
                int dstNumBands = dstRaster.getNumBands();
                if (dstNumBands-1 == length || length == 1) {
                    int minx = dstRaster.getMinX();
                    int miny = dstRaster.getMinY();
                    int[] bands = new int[numBands-1];
                    for (int i=0; i < numBands-1; i++) {
                        bands[i] = i;
                    }
                    dstRaster =
                        dstRaster.createWritableChild(minx, miny,
                                                      dstRaster.getWidth(),
                                                      dstRaster.getHeight(),
                                                      minx, miny,
                                                      bands);
                }
            }

            //
            // Call the raster filter method
            //
            filter(srcRaster, dstRaster);

        }

        if (needToConvert) {
            // ColorModels are not the same
            ColorConvertOp ccop = new ColorConvertOp(hints);
            ccop.filter(dst, origDst);
        }

        return origDst;
    }

    /**
     * Rescales the pixel data in the source Raster.
     * If the destination Raster is null, a new Raster will be created.
     * The source and destination must have the same number of bands.
     * Otherwise, an IllegalArgumentException is thrown.
     * Note that the number of scaling factors/offsets in this object must
     * meet the restrictions stated in the class comments above.
     * Otherwise, an IllegalArgumentException is thrown.
     * <p>
     * 调整源栅格中的像素数据。如果目标栅格为空,将创建一个新的栅格。源和目标必须具有相同数量的波段。否则,抛出IllegalArgumentException。
     * 请注意,此对象中缩放因子/偏移的数量必须满足上面类注释中所述的限制。否则,抛出IllegalArgumentException。
     * 
     * 
     * @param src the <code>Raster</code> to be filtered
     * @param dst the destination for the filtering operation
     *            or <code>null</code>
     * @return the filtered <code>WritableRaster</code>.
     * @throws IllegalArgumentException if <code>src</code> and
     *         <code>dst</code> do not have the same number of bands,
     *         or if the number of scaling factors and offsets in this
     *         <code>RescaleOp</code> do not meet the requirements
     *         stated in the class comments.
     */
    public final WritableRaster filter (Raster src, WritableRaster dst)  {
        int numBands = src.getNumBands();
        int width  = src.getWidth();
        int height = src.getHeight();
        int[] srcPix = null;
        int step = 0;
        int tidx = 0;

        // Create a new destination Raster, if needed
        if (dst == null) {
            dst = createCompatibleDestRaster(src);
        }
        else if (height != dst.getHeight() || width != dst.getWidth()) {
            throw new
               IllegalArgumentException("Width or height of Rasters do not "+
                                        "match");
        }
        else if (numBands != dst.getNumBands()) {
            // Make sure that the number of bands are equal
            throw new IllegalArgumentException("Number of bands in src "
                            + numBands
                            + " does not equal number of bands in dest "
                            + dst.getNumBands());
        }
        // Make sure that the arrays match
        // Make sure that the low/high/constant arrays match
        if (length != 1 && length != src.getNumBands()) {
            throw new IllegalArgumentException("Number of scaling constants "+
                                               "does not equal the number of"+
                                               " of bands in the src raster");
        }


        //
        // Try for a native raster rescale first
        //
        if (ImagingLib.filter(this, src, dst) != null) {
            return dst;
        }

        //
        // Native raster rescale failed.
        // Try to see if a lookup operation can be used
        //
        if (canUseLookup(src, dst)) {
            int srcNgray = (1 << srcNbits);
            int dstNgray = (1 << dstNbits);

            if (dstNgray == 256) {
                ByteLookupTable lut = createByteLut(scaleFactors, offsets,
                                                    numBands, srcNgray);
                LookupOp op = new LookupOp(lut, hints);
                op.filter(src, dst);
            } else {
                ShortLookupTable lut = createShortLut(scaleFactors, offsets,
                                                      numBands, srcNgray);
                LookupOp op = new LookupOp(lut, hints);
                op.filter(src, dst);
            }
        } else {
            //
            // Fall back to the slow code
            //
            if (length > 1) {
                step = 1;
            }

            int sminX = src.getMinX();
            int sY = src.getMinY();
            int dminX = dst.getMinX();
            int dY = dst.getMinY();
            int sX;
            int dX;

            //
            //  Determine bits per band to determine maxval for clamps.
            //  The min is assumed to be zero.
            //  REMIND: This must change if we ever support signed data types.
            //
            int nbits;
            int dstMax[] = new int[numBands];
            int dstMask[] = new int[numBands];
            SampleModel dstSM = dst.getSampleModel();
            for (int z=0; z<numBands; z++) {
                nbits = dstSM.getSampleSize(z);
                dstMax[z] = (1 << nbits) - 1;
                dstMask[z] = ~(dstMax[z]);
            }

            int val;
            for (int y=0; y < height; y++, sY++, dY++) {
                dX = dminX;
                sX = sminX;
                for (int x = 0; x < width; x++, sX++, dX++) {
                    // Get data for all bands at this x,y position
                    srcPix = src.getPixel(sX, sY, srcPix);
                    tidx = 0;
                    for (int z=0; z<numBands; z++, tidx += step) {
                        val = (int)(srcPix[z]*scaleFactors[tidx]
                                          + offsets[tidx]);
                        // Clamp
                        if ((val & dstMask[z]) != 0) {
                            if (val < 0) {
                                val = 0;
                            } else {
                                val = dstMax[z];
                            }
                        }
                        srcPix[z] = val;

                    }

                    // Put it back for all bands
                    dst.setPixel(dX, dY, srcPix);
                }
            }
        }
        return dst;
    }

    /**
     * Returns the bounding box of the rescaled destination image.  Since
     * this is not a geometric operation, the bounding box does not
     * change.
     * <p>
     *  返回重新缩放的目标图像的边框。由于这不是几何操作,边界框不会改变。
     * 
     */
    public final Rectangle2D getBounds2D (BufferedImage src) {
         return getBounds2D(src.getRaster());
    }

    /**
     * Returns the bounding box of the rescaled destination Raster.  Since
     * this is not a geometric operation, the bounding box does not
     * change.
     * <p>
     *  返回重新缩放的目标栅格的边界框。由于这不是几何操作,边界框不会改变。
     * 
     * 
     * @param src the rescaled destination <code>Raster</code>
     * @return the bounds of the specified <code>Raster</code>.
     */
    public final Rectangle2D getBounds2D (Raster src) {
        return src.getBounds();
    }

    /**
     * Creates a zeroed destination image with the correct size and number of
     * bands.
     * <p>
     *  使用正确的大小和带数创建一个调零的目标图像。
     * 
     * 
     * @param src       Source image for the filter operation.
     * @param destCM    ColorModel of the destination.  If null, the
     *                  ColorModel of the source will be used.
     * @return the zeroed-destination image.
     */
    public BufferedImage createCompatibleDestImage (BufferedImage src,
                                                    ColorModel destCM) {
        BufferedImage image;
        if (destCM == null) {
            ColorModel cm = src.getColorModel();
            image = new BufferedImage(cm,
                                      src.getRaster().createCompatibleWritableRaster(),
                                      cm.isAlphaPremultiplied(),
                                      null);
        }
        else {
            int w = src.getWidth();
            int h = src.getHeight();
            image = new BufferedImage (destCM,
                                   destCM.createCompatibleWritableRaster(w, h),
                                   destCM.isAlphaPremultiplied(), null);
        }

        return image;
    }

    /**
     * Creates a zeroed-destination <code>Raster</code> with the correct
     * size and number of bands, given this source.
     * <p>
     *  给定这个源,创建一个带有正确大小和带数的零目的<code> Raster </code>。
     * 
     * 
     * @param src       the source <code>Raster</code>
     * @return the zeroed-destination <code>Raster</code>.
     */
    public WritableRaster createCompatibleDestRaster (Raster src) {
        return src.createCompatibleWritableRaster(src.getWidth(), src.getHeight());
    }

    /**
     * Returns the location of the destination point given a
     * point in the source.  If dstPt is non-null, it will
     * be used to hold the return value.  Since this is not a geometric
     * operation, the srcPt will equal the dstPt.
     * <p>
     *  返回给定源中的点的目标点的位置。如果dstPt为非空,它将用于保存返回值。因为这不是几何操作,所以srcPt将等于dstPt。
     * 
     * 
     * @param srcPt a point in the source image
     * @param dstPt the destination point or <code>null</code>
     * @return the location of the destination point.
     */
    public final Point2D getPoint2D (Point2D srcPt, Point2D dstPt) {
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
     * 
     * @return the rendering hints of this <code>RescaleOp</code>.
     */
    public final RenderingHints getRenderingHints() {
        return hints;
    }
}
