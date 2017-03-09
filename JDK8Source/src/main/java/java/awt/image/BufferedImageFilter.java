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

import java.util.Hashtable;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageFilter;

/**
 * The <code>BufferedImageFilter</code> class subclasses an
 * <code>ImageFilter</code> to provide a simple means of
 * using a single-source/single-destination image operator
 * ({@link BufferedImageOp}) to filter a <code>BufferedImage</code>
 * in the Image Producer/Consumer/Observer
 * paradigm. Examples of these image operators are: {@link ConvolveOp},
 * {@link AffineTransformOp} and {@link LookupOp}.
 *
 * <p>
 *  <code> BufferedImageFilter </code>类将<code> ImageFilter </code>作为子类,以提供使用单源/单目的图像运算符({@link BufferedImageOp}
 * )过滤<code> Image Producer / Consumer / Observer范例中的BufferedImage </code>。
 * 这些图像运算符的示例是：{@link ConvolveOp},{@link AffineTransformOp}和{@link LookupOp}。
 * 
 * 
 * @see ImageFilter
 * @see BufferedImage
 * @see BufferedImageOp
 */

public class BufferedImageFilter extends ImageFilter implements Cloneable {
    BufferedImageOp bufferedImageOp;
    ColorModel model;
    int width;
    int height;
    byte[] bytePixels;
    int[] intPixels;

    /**
     * Constructs a <code>BufferedImageFilter</code> with the
     * specified single-source/single-destination operator.
     * <p>
     *  使用指定的单源/单目标运算符构造<code> BufferedImageFilter </code>。
     * 
     * 
     * @param op the specified <code>BufferedImageOp</code> to
     *           use to filter a <code>BufferedImage</code>
     * @throws NullPointerException if op is null
     */
    public BufferedImageFilter (BufferedImageOp op) {
        super();
        if (op == null) {
            throw new NullPointerException("Operation cannot be null");
        }
        bufferedImageOp = op;
    }

    /**
     * Returns the <code>BufferedImageOp</code>.
     * <p>
     *  返回<code> BufferedImageOp </code>。
     * 
     * 
     * @return the operator of this <code>BufferedImageFilter</code>.
     */
    public BufferedImageOp getBufferedImageOp() {
        return bufferedImageOp;
    }

    /**
     * Filters the information provided in the
     * {@link ImageConsumer#setDimensions(int, int) setDimensions } method
     * of the {@link ImageConsumer} interface.
     * <p>
     * Note: This method is intended to be called by the
     * {@link ImageProducer} of the <code>Image</code> whose pixels are
     * being filtered. Developers using this class to retrieve pixels from
     * an image should avoid calling this method directly since that
     * operation could result in problems with retrieving the requested
     * pixels.
     * <p>
     * <p>
     *  过滤{@link ImageConsumer}界面的{@link ImageConsumer#setDimensions(int,int)setDimensions}方法中提供的信息。
     * <p>
     *  注意：此方法旨在由其像素正在被过滤的<code> Image </code>的{@link ImageProducer}调用。
     * 使用此类从图像检索像素的开发人员应避免直接调用此方法,因为该操作可能会导致检索请求的像素时出现问题。
     * <p>
     * 
     * @param width the width to which to set the width of this
     *        <code>BufferedImageFilter</code>
     * @param height the height to which to set the height of this
     *        <code>BufferedImageFilter</code>
     * @see ImageConsumer#setDimensions
     */
    public void setDimensions(int width, int height) {
        if (width <= 0 || height <= 0) {
            imageComplete(STATICIMAGEDONE);
            return;
        }
        this.width  = width;
        this.height = height;
    }

    /**
     * Filters the information provided in the
     * {@link ImageConsumer#setColorModel(ColorModel) setColorModel} method
     * of the <code>ImageConsumer</code> interface.
     * <p>
     * If <code>model</code> is <code>null</code>, this
     * method clears the current <code>ColorModel</code> of this
     * <code>BufferedImageFilter</code>.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code>
     * whose pixels are being filtered.  Developers using this
     * class to retrieve pixels from an image
     * should avoid calling this method directly since that
     * operation could result in problems with retrieving the
     * requested pixels.
     * <p>
     *  过滤<code> ImageConsumer </code>界面的{@link ImageConsumer#setColorModel(ColorModel)setColorModel}方法中提供的信
     * 息。
     * <p>
     *  如果<code> model </code>是<code> null </code>,此方法清除此<code> BufferedImageFilter </code>的当前<code> ColorMo
     * del </code>。
     * <p>
     * 注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像检索像素的开发人员应避免直接调用此方法,因为该操作可能会导致检索请求的像素时出现问题。
     * 
     * 
     * @param model the {@link ColorModel} to which to set the
     *        <code>ColorModel</code> of this <code>BufferedImageFilter</code>
     * @see ImageConsumer#setColorModel
     */
    public void setColorModel(ColorModel model) {
        this.model = model;
    }

    private void convertToRGB() {
        int size = width * height;
        int newpixels[] = new int[size];
        if (bytePixels != null) {
            for (int i = 0; i < size; i++) {
                newpixels[i] = this.model.getRGB(bytePixels[i] & 0xff);
            }
        } else if (intPixels != null) {
            for (int i = 0; i < size; i++) {
                newpixels[i] = this.model.getRGB(intPixels[i]);
            }
        }
        bytePixels = null;
        intPixels = newpixels;
        this.model = ColorModel.getRGBdefault();
    }

    /**
     * Filters the information provided in the <code>setPixels</code>
     * method of the <code>ImageConsumer</code> interface which takes
     * an array of bytes.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose pixels
     * are being filtered.  Developers using
     * this class to retrieve pixels from an image should avoid calling
     * this method directly since that operation could result in problems
     * with retrieving the requested pixels.
     * <p>
     *  过滤在<code> ImageConsumer </code>接口的<code> setPixels </code>方法中提供的信息,该方法接收字节数组。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像检索像素的开发人员应避免直接调用此方法,因为该操作可能会导致检索请求的像素时出现问题。
     * 
     * 
     * @throws IllegalArgumentException if width or height are less than
     * zero.
     * @see ImageConsumer#setPixels(int, int, int, int, ColorModel, byte[],
                                    int, int)
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, byte pixels[], int off,
                          int scansize) {
        // Fix 4184230
        if (w < 0 || h < 0) {
            throw new IllegalArgumentException("Width ("+w+
                                                ") and height ("+h+
                                                ") must be > 0");
        }
        // Nothing to do
        if (w == 0 || h == 0) {
            return;
        }
        if (y < 0) {
            int diff = -y;
            if (diff >= h) {
                return;
            }
            off += scansize * diff;
            y += diff;
            h -= diff;
        }
        if (y + h > height) {
            h = height - y;
            if (h <= 0) {
                return;
            }
        }
        if (x < 0) {
            int diff = -x;
            if (diff >= w) {
                return;
            }
            off += diff;
            x += diff;
            w -= diff;
        }
        if (x + w > width) {
            w = width - x;
            if (w <= 0) {
                return;
            }
        }
        int dstPtr = y*width + x;
        if (intPixels == null) {
            if (bytePixels == null) {
                bytePixels = new byte[width*height];
                this.model = model;
            } else if (this.model != model) {
                convertToRGB();
            }
            if (bytePixels != null) {
                for (int sh = h; sh > 0; sh--) {
                    System.arraycopy(pixels, off, bytePixels, dstPtr, w);
                    off += scansize;
                    dstPtr += width;
                }
            }
        }
        if (intPixels != null) {
            int dstRem = width - w;
            int srcRem = scansize - w;
            for (int sh = h; sh > 0; sh--) {
                for (int sw = w; sw > 0; sw--) {
                    intPixels[dstPtr++] = model.getRGB(pixels[off++]&0xff);
                }
                off    += srcRem;
                dstPtr += dstRem;
            }
        }
    }
    /**
     * Filters the information provided in the <code>setPixels</code>
     * method of the <code>ImageConsumer</code> interface which takes
     * an array of integers.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose
     * pixels are being filtered.  Developers using this class to
     * retrieve pixels from an image should avoid calling this method
     * directly since that operation could result in problems
     * with retrieving the requested pixels.
     * <p>
     *  过滤在<code> ImageConsumer </code>接口的<code> setPixels </code>方法中提供的信息,该接口采用整数数组。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像检索像素的开发人员应避免直接调用此方法,因为该操作可能会导致检索请求的像素时出现问题。
     * 
     * 
     * @throws IllegalArgumentException if width or height are less than
     * zero.
     * @see ImageConsumer#setPixels(int, int, int, int, ColorModel, int[],
                                    int, int)
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, int pixels[], int off,
                          int scansize) {
        // Fix 4184230
        if (w < 0 || h < 0) {
            throw new IllegalArgumentException("Width ("+w+
                                                ") and height ("+h+
                                                ") must be > 0");
        }
        // Nothing to do
        if (w == 0 || h == 0) {
            return;
        }
        if (y < 0) {
            int diff = -y;
            if (diff >= h) {
                return;
            }
            off += scansize * diff;
            y += diff;
            h -= diff;
        }
        if (y + h > height) {
            h = height - y;
            if (h <= 0) {
                return;
            }
        }
        if (x < 0) {
            int diff = -x;
            if (diff >= w) {
                return;
            }
            off += diff;
            x += diff;
            w -= diff;
        }
        if (x + w > width) {
            w = width - x;
            if (w <= 0) {
                return;
            }
        }

        if (intPixels == null) {
            if (bytePixels == null) {
                intPixels = new int[width * height];
                this.model = model;
            } else {
                convertToRGB();
            }
        }
        int dstPtr = y*width + x;
        if (this.model == model) {
            for (int sh = h; sh > 0; sh--) {
                System.arraycopy(pixels, off, intPixels, dstPtr, w);
                off += scansize;
                dstPtr += width;
            }
        } else {
            if (this.model != ColorModel.getRGBdefault()) {
                convertToRGB();
            }
            int dstRem = width - w;
            int srcRem = scansize - w;
            for (int sh = h; sh > 0; sh--) {
                for (int sw = w; sw > 0; sw--) {
                    intPixels[dstPtr++] = model.getRGB(pixels[off++]);
                }
                off += srcRem;
                dstPtr += dstRem;
            }
        }
    }

    /**
     * Filters the information provided in the <code>imageComplete</code>
     * method of the <code>ImageConsumer</code> interface.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose pixels
     * are being filtered.  Developers using
     * this class to retrieve pixels from an image should avoid calling
     * this method directly since that operation could result in problems
     * with retrieving the requested pixels.
     * <p>
     *  过滤在<code> ImageConsumer </code>界面的<code> imageComplete </code>方法中提供的信息。
     * <p>
     * 注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 
     * @param status the status of image loading
     * @throws ImagingOpException if there was a problem calling the filter
     * method of the <code>BufferedImageOp</code> associated with this
     * instance.
     * @see ImageConsumer#imageComplete
     */
    public void imageComplete(int status) {
        WritableRaster wr;
        switch(status) {
        case IMAGEERROR:
        case IMAGEABORTED:
            // reinitialize the params
            model  = null;
            width  = -1;
            height = -1;
            intPixels  = null;
            bytePixels = null;
            break;

        case SINGLEFRAMEDONE:
        case STATICIMAGEDONE:
            if (width <= 0 || height <= 0) break;
            if (model instanceof DirectColorModel) {
                if (intPixels == null) break;
                wr = createDCMraster();
            }
            else if (model instanceof IndexColorModel) {
                int[] bandOffsets = {0};
                if (bytePixels == null) break;
                DataBufferByte db = new DataBufferByte(bytePixels,
                                                       width*height);
                wr = Raster.createInterleavedRaster(db, width, height, width,
                                                    1, bandOffsets, null);
            }
            else {
                convertToRGB();
                if (intPixels == null) break;
                wr = createDCMraster();
            }
            BufferedImage bi = new BufferedImage(model, wr,
                                                 model.isAlphaPremultiplied(),
                                                 null);
            bi = bufferedImageOp.filter(bi, null);
            WritableRaster r = bi.getRaster();
            ColorModel cm = bi.getColorModel();
            int w = r.getWidth();
            int h = r.getHeight();
            consumer.setDimensions(w, h);
            consumer.setColorModel(cm);
            if (cm instanceof DirectColorModel) {
                DataBufferInt db = (DataBufferInt) r.getDataBuffer();
                consumer.setPixels(0, 0, w, h,
                                   cm, db.getData(), 0, w);
            }
            else if (cm instanceof IndexColorModel) {
                DataBufferByte db = (DataBufferByte) r.getDataBuffer();
                consumer.setPixels(0, 0, w, h,
                                   cm, db.getData(), 0, w);
            }
            else {
                throw new InternalError("Unknown color model "+cm);
            }
            break;
        }
        consumer.imageComplete(status);
    }

    private final WritableRaster createDCMraster() {
        WritableRaster wr;
        DirectColorModel dcm = (DirectColorModel) model;
        boolean hasAlpha = model.hasAlpha();
        int[] bandMasks = new int[3+(hasAlpha ? 1 : 0)];
        bandMasks[0] = dcm.getRedMask();
        bandMasks[1] = dcm.getGreenMask();
        bandMasks[2] = dcm.getBlueMask();
        if (hasAlpha) {
            bandMasks[3] = dcm.getAlphaMask();
        }
        DataBufferInt db = new DataBufferInt(intPixels, width*height);
        wr = Raster.createPackedRaster(db, width, height, width,
                                       bandMasks, null);
        return wr;
    }

}
