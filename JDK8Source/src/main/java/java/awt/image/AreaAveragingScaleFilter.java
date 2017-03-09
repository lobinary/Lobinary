/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2002, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.image.ImageConsumer;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.awt.Rectangle;

/**
 * An ImageFilter class for scaling images using a simple area averaging
 * algorithm that produces smoother results than the nearest neighbor
 * algorithm.
 * <p>This class extends the basic ImageFilter Class to scale an existing
 * image and provide a source for a new image containing the resampled
 * image.  The pixels in the source image are blended to produce pixels
 * for an image of the specified size.  The blending process is analogous
 * to scaling up the source image to a multiple of the destination size
 * using pixel replication and then scaling it back down to the destination
 * size by simply averaging all the pixels in the supersized image that
 * fall within a given pixel of the destination image.  If the data from
 * the source is not delivered in TopDownLeftRight order then the filter
 * will back off to a simple pixel replication behavior and utilize the
 * requestTopDownLeftRightResend() method to refilter the pixels in a
 * better way at the end.
 * <p>It is meant to be used in conjunction with a FilteredImageSource
 * object to produce scaled versions of existing images.  Due to
 * implementation dependencies, there may be differences in pixel values
 * of an image filtered on different platforms.
 *
 * <p>
 *  ImageFilter类,用于使用简单的面积平均算法来缩放图像,该算法产生比最近邻算法更平滑的结果。
 *  <p>此类扩展了基本的ImageFilter类,以扩展现有图像,并为包含重新采样图像的新图像提供源。源图像中的像素被混合以产生用于指定大小的图像的像素。
 * 混合处理类似于使用像素复制将源图像按比例放大到目标大小的倍数,然后通过简单地对落在目的地的给定像素内的超大化图像中的所有像素进行平均来将其缩小到目的地大小图片。
 * 如果来自源的数据不是按照TopDownLeftRight顺序传送的,则过滤器将退回到简单的像素复制行为,并且在结束时利用requestTopDownLeftRightResend()方法以更好的方式重新
 * 过滤像素。
 * 混合处理类似于使用像素复制将源图像按比例放大到目标大小的倍数,然后通过简单地对落在目的地的给定像素内的超大化图像中的所有像素进行平均来将其缩小到目的地大小图片。
 *  <p>这意味着与FilteredImageSource对象结合使用以生成现有图像的缩放版本。由于实现依赖性,在不同平台上过滤的图像的像素值可能存在差异。
 * 
 * 
 * @see FilteredImageSource
 * @see ReplicateScaleFilter
 * @see ImageFilter
 *
 * @author      Jim Graham
 */
public class AreaAveragingScaleFilter extends ReplicateScaleFilter {
    private static final ColorModel rgbmodel = ColorModel.getRGBdefault();
    private static final int neededHints = (TOPDOWNLEFTRIGHT
                                            | COMPLETESCANLINES);

    private boolean passthrough;
    private float reds[], greens[], blues[], alphas[];
    private int savedy;
    private int savedyrem;

    /**
     * Constructs an AreaAveragingScaleFilter that scales the pixels from
     * its source Image as specified by the width and height parameters.
     * <p>
     *  构造一个AreaAveragingScaleFilter,它根据width和height参数指定的缩放来自其源图像的像素。
     * 
     * 
     * @param width the target width to scale the image
     * @param height the target height to scale the image
     */
    public AreaAveragingScaleFilter(int width, int height) {
        super(width, height);
    }

    /**
     * Detect if the data is being delivered with the necessary hints
     * to allow the averaging algorithm to do its work.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose
     * pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     * 检测数据是否正在传递必要的提示,以允许平均算法完成其工作。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer#setHints
     */
    public void setHints(int hints) {
        passthrough = ((hints & neededHints) != neededHints);
        super.setHints(hints);
    }

    private void makeAccumBuffers() {
        reds = new float[destWidth];
        greens = new float[destWidth];
        blues = new float[destWidth];
        alphas = new float[destWidth];
    }

    private int[] calcRow() {
        float origmult = ((float) srcWidth) * srcHeight;
        if (outpixbuf == null || !(outpixbuf instanceof int[])) {
            outpixbuf = new int[destWidth];
        }
        int[] outpix = (int[]) outpixbuf;
        for (int x = 0; x < destWidth; x++) {
            float mult = origmult;
            int a = Math.round(alphas[x] / mult);
            if (a <= 0) {
                a = 0;
            } else if (a >= 255) {
                a = 255;
            } else {
                // un-premultiply the components (by modifying mult here, we
                // are effectively doing the divide by mult and divide by
                // alpha in the same step)
                mult = alphas[x] / 255;
            }
            int r = Math.round(reds[x] / mult);
            int g = Math.round(greens[x] / mult);
            int b = Math.round(blues[x] / mult);
            if (r < 0) {r = 0;} else if (r > 255) {r = 255;}
            if (g < 0) {g = 0;} else if (g > 255) {g = 255;}
            if (b < 0) {b = 0;} else if (b > 255) {b = 255;}
            outpix[x] = (a << 24 | r << 16 | g << 8 | b);
        }
        return outpix;
    }

    private void accumPixels(int x, int y, int w, int h,
                             ColorModel model, Object pixels, int off,
                             int scansize) {
        if (reds == null) {
            makeAccumBuffers();
        }
        int sy = y;
        int syrem = destHeight;
        int dy, dyrem;
        if (sy == 0) {
            dy = 0;
            dyrem = 0;
        } else {
            dy = savedy;
            dyrem = savedyrem;
        }
        while (sy < y + h) {
            int amty;
            if (dyrem == 0) {
                for (int i = 0; i < destWidth; i++) {
                    alphas[i] = reds[i] = greens[i] = blues[i] = 0f;
                }
                dyrem = srcHeight;
            }
            if (syrem < dyrem) {
                amty = syrem;
            } else {
                amty = dyrem;
            }
            int sx = 0;
            int dx = 0;
            int sxrem = 0;
            int dxrem = srcWidth;
            float a = 0f, r = 0f, g = 0f, b = 0f;
            while (sx < w) {
                if (sxrem == 0) {
                    sxrem = destWidth;
                    int rgb;
                    if (pixels instanceof byte[]) {
                        rgb = ((byte[]) pixels)[off + sx] & 0xff;
                    } else {
                        rgb = ((int[]) pixels)[off + sx];
                    }
                    // getRGB() always returns non-premultiplied components
                    rgb = model.getRGB(rgb);
                    a = rgb >>> 24;
                    r = (rgb >> 16) & 0xff;
                    g = (rgb >>  8) & 0xff;
                    b = rgb & 0xff;
                    // premultiply the components if necessary
                    if (a != 255.0f) {
                        float ascale = a / 255.0f;
                        r *= ascale;
                        g *= ascale;
                        b *= ascale;
                    }
                }
                int amtx;
                if (sxrem < dxrem) {
                    amtx = sxrem;
                } else {
                    amtx = dxrem;
                }
                float mult = ((float) amtx) * amty;
                alphas[dx] += mult * a;
                reds[dx] += mult * r;
                greens[dx] += mult * g;
                blues[dx] += mult * b;
                if ((sxrem -= amtx) == 0) {
                    sx++;
                }
                if ((dxrem -= amtx) == 0) {
                    dx++;
                    dxrem = srcWidth;
                }
            }
            if ((dyrem -= amty) == 0) {
                int outpix[] = calcRow();
                do {
                    consumer.setPixels(0, dy, destWidth, 1,
                                       rgbmodel, outpix, 0, destWidth);
                    dy++;
                } while ((syrem -= amty) >= amty && amty == srcHeight);
            } else {
                syrem -= amty;
            }
            if (syrem == 0) {
                syrem = destHeight;
                sy++;
                off += scansize;
            }
        }
        savedyrem = dyrem;
        savedy = dy;
    }

    /**
     * Combine the components for the delivered byte pixels into the
     * accumulation arrays and send on any averaged data for rows of
     * pixels that are complete.  If the correct hints were not
     * specified in the setHints call then relay the work to our
     * superclass which is capable of scaling pixels regardless of
     * the delivery hints.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code>
     * whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  将传送的字节像素的分量组合到累积数组中,并对完成的像素行的任何平均数据发送。如果没有在setHints调用中指定正确的提示,那么将工作中继到我们的超类,它能够缩放像素,而不考虑传递提示。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ReplicateScaleFilter
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, byte pixels[], int off,
                          int scansize) {
        if (passthrough) {
            super.setPixels(x, y, w, h, model, pixels, off, scansize);
        } else {
            accumPixels(x, y, w, h, model, pixels, off, scansize);
        }
    }

    /**
     * Combine the components for the delivered int pixels into the
     * accumulation arrays and send on any averaged data for rows of
     * pixels that are complete.  If the correct hints were not
     * specified in the setHints call then relay the work to our
     * superclass which is capable of scaling pixels regardless of
     * the delivery hints.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code>
     * whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  将用于传送的int像素的分量组合到累积数组中,并对完成的像素行的任何平均数据发送。如果没有在setHints调用中指定正确的提示,那么将工作中继到我们的超类,它能够缩放像素,而不考虑传递提示。
     * <p>
     * 注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 
     * @see ReplicateScaleFilter
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, int pixels[], int off,
                          int scansize) {
        if (passthrough) {
            super.setPixels(x, y, w, h, model, pixels, off, scansize);
        } else {
            accumPixels(x, y, w, h, model, pixels, off, scansize);
        }
    }
}
