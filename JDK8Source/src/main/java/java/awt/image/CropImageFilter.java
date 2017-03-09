/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2004, Oracle and/or its affiliates. All rights reserved.
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
 * An ImageFilter class for cropping images.
 * This class extends the basic ImageFilter Class to extract a given
 * rectangular region of an existing Image and provide a source for a
 * new image containing just the extracted region.  It is meant to
 * be used in conjunction with a FilteredImageSource object to produce
 * cropped versions of existing images.
 *
 * <p>
 *  用于裁剪图像的ImageFilter类。此类扩展了基本的ImageFilter类,以提取现有图像的给定矩形区域,并为仅包含提取区域的新图像提供源。
 * 它意味着与FilteredImageSource对象结合使用,以生成现有图像的裁剪版本。
 * 
 * 
 * @see FilteredImageSource
 * @see ImageFilter
 *
 * @author      Jim Graham
 */
public class CropImageFilter extends ImageFilter {
    int cropX;
    int cropY;
    int cropW;
    int cropH;

    /**
     * Constructs a CropImageFilter that extracts the absolute rectangular
     * region of pixels from its source Image as specified by the x, y,
     * w, and h parameters.
     * <p>
     *  构造一个CropImageFilter,从x,y,w和h参数指定的源图像中提取像素的绝对矩形区域。
     * 
     * 
     * @param x the x location of the top of the rectangle to be extracted
     * @param y the y location of the top of the rectangle to be extracted
     * @param w the width of the rectangle to be extracted
     * @param h the height of the rectangle to be extracted
     */
    public CropImageFilter(int x, int y, int w, int h) {
        cropX = x;
        cropY = y;
        cropW = w;
        cropH = h;
    }

    /**
     * Passes along  the properties from the source object after adding a
     * property indicating the cropped region.
     * This method invokes <code>super.setProperties</code>,
     * which might result in additional properties being added.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose pixels
     * are being filtered. Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  在添加指示裁剪区域的属性之后,从源对象沿着属性传递。此方法调用<code> super.setProperties </code>,这可能会导致添加其他属性。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     */
    public void setProperties(Hashtable<?,?> props) {
        Hashtable<Object,Object> p = (Hashtable<Object,Object>)props.clone();
        p.put("croprect", new Rectangle(cropX, cropY, cropW, cropH));
        super.setProperties(p);
    }

    /**
     * Override the source image's dimensions and pass the dimensions
     * of the rectangular cropped region to the ImageConsumer.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose
     * pixels are being filtered. Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  覆盖源图像的尺寸,并将矩形裁剪区域的尺寸传递给ImageConsumer。
     * <p>
     * 注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer
     */
    public void setDimensions(int w, int h) {
        consumer.setDimensions(cropW, cropH);
    }

    /**
     * Determine whether the delivered byte pixels intersect the region to
     * be extracted and passes through only that subset of pixels that
     * appear in the output region.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose
     * pixels are being filtered. Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  确定传递的字节像素是否与要提取的区域相交,并仅通过出现在输出区域中的像素子集。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, byte pixels[], int off,
                          int scansize) {
        int x1 = x;
        if (x1 < cropX) {
            x1 = cropX;
        }
    int x2 = addWithoutOverflow(x, w);
        if (x2 > cropX + cropW) {
            x2 = cropX + cropW;
        }
        int y1 = y;
        if (y1 < cropY) {
            y1 = cropY;
        }

    int y2 = addWithoutOverflow(y, h);
        if (y2 > cropY + cropH) {
            y2 = cropY + cropH;
        }
        if (x1 >= x2 || y1 >= y2) {
            return;
        }
        consumer.setPixels(x1 - cropX, y1 - cropY, (x2 - x1), (y2 - y1),
                           model, pixels,
                           off + (y1 - y) * scansize + (x1 - x), scansize);
    }

    /**
     * Determine if the delivered int pixels intersect the region to
     * be extracted and pass through only that subset of pixels that
     * appear in the output region.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose
     * pixels are being filtered. Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  确定递送的int像素是否与要提取的区域相交,并仅通过出现在输出区域中的像素子集。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, int pixels[], int off,
                          int scansize) {
        int x1 = x;
        if (x1 < cropX) {
            x1 = cropX;
        }
    int x2 = addWithoutOverflow(x, w);
        if (x2 > cropX + cropW) {
            x2 = cropX + cropW;
        }
        int y1 = y;
        if (y1 < cropY) {
            y1 = cropY;
        }

    int y2 = addWithoutOverflow(y, h);
        if (y2 > cropY + cropH) {
            y2 = cropY + cropH;
        }
        if (x1 >= x2 || y1 >= y2) {
            return;
        }
        consumer.setPixels(x1 - cropX, y1 - cropY, (x2 - x1), (y2 - y1),
                           model, pixels,
                           off + (y1 - y) * scansize + (x1 - x), scansize);
    }

    //check for potential overflow (see bug 4801285)
    private int addWithoutOverflow(int x, int w) {
        int x2 = x + w;
        if ( x > 0 && w > 0 && x2 < 0 ) {
            x2 = Integer.MAX_VALUE;
        } else if( x < 0 && w < 0 && x2 > 0 ) {
            x2 = Integer.MIN_VALUE;
        }
        return x2;
    }
}
