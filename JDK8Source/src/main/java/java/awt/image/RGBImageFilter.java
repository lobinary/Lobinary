/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This class provides an easy way to create an ImageFilter which modifies
 * the pixels of an image in the default RGB ColorModel.  It is meant to
 * be used in conjunction with a FilteredImageSource object to produce
 * filtered versions of existing images.  It is an abstract class that
 * provides the calls needed to channel all of the pixel data through a
 * single method which converts pixels one at a time in the default RGB
 * ColorModel regardless of the ColorModel being used by the ImageProducer.
 * The only method which needs to be defined to create a useable image
 * filter is the filterRGB method.  Here is an example of a definition
 * of a filter which swaps the red and blue components of an image:
 * <pre>{@code
 *
 *      class RedBlueSwapFilter extends RGBImageFilter {
 *          public RedBlueSwapFilter() {
 *              // The filter's operation does not depend on the
 *              // pixel's location, so IndexColorModels can be
 *              // filtered directly.
 *              canFilterIndexColorModel = true;
 *          }
 *
 *          public int filterRGB(int x, int y, int rgb) {
 *              return ((rgb & 0xff00ff00)
 *                      | ((rgb & 0xff0000) >> 16)
 *                      | ((rgb & 0xff) << 16));
 *          }
 *      }
 *
 * }</pre>
 *
 * <p>
 *  此类提供了一种创建ImageFilter的简单方法,该方法修改默认RGB ColorModel中图像的像素。它意味着与FilteredImageSource对象结合使用,以生成现有映像的过滤版本。
 * 它是一个抽象类,它提供了通过一种单一方法来传递所有像素数据所需的调用,该方法在默认RGB ColorModel中一次转换一个像素,而不管ImageProducer使用的ColorModel。
 * 唯一需要定义以创建可用的图像过滤器的方法是filterRGB方法。这里是一个过滤器的定义的例子,它交换图像的红色和蓝色分量：<pre> {@ code。
 * 
 *  类RedBlueSwapFilter扩展RGBImageFilter {public RedBlueSwapFilter(){//过滤器的操作不依赖于像素的位置,因此可以直接过滤IndexColorModels。
 *  canFilterIndexColorModel = true; }}。
 * 
 *  public int filterRGB(int x,int y,int rgb){return((rgb&0xff00ff00)|((rgb&0xff0000)>> 16)|((rgb&0xff)<< 16) }}。
 * 
 *  } </pre>
 * 
 * 
 * @see FilteredImageSource
 * @see ImageFilter
 * @see ColorModel#getRGBdefault
 *
 * @author      Jim Graham
 */
public abstract class RGBImageFilter extends ImageFilter {

    /**
     * The <code>ColorModel</code> to be replaced by
     * <code>newmodel</code> when the user calls
     * {@link #substituteColorModel(ColorModel, ColorModel) substituteColorModel}.
     * <p>
     *  当用户调用{@link #substituteColorModel(ColorModel,ColorModel)replaceColorModel}时,<code> ColorModel </code>
     * 将替换为<code> newmodel </code>。
     * 
     */
    protected ColorModel origmodel;

    /**
     * The <code>ColorModel</code> with which to
     * replace <code>origmodel</code> when the user calls
     * <code>substituteColorModel</code>.
     * <p>
     * 当用户调用<code> substituteColorModel </code>时,用<code> ColorModel </code>替换<code> origmodel </code>。
     * 
     */
    protected ColorModel newmodel;

    /**
     * This boolean indicates whether or not it is acceptable to apply
     * the color filtering of the filterRGB method to the color table
     * entries of an IndexColorModel object in lieu of pixel by pixel
     * filtering.  Subclasses should set this variable to true in their
     * constructor if their filterRGB method does not depend on the
     * coordinate of the pixel being filtered.
     * <p>
     *  该布尔表示是否可以将filterRGB方法的颜色滤波应用于IndexColorModel对象的颜色表条目,而不是逐个像素滤波。
     * 如果子类的filterRGB方法不依赖于被过滤的像素的坐标,则子类应在其构造函数中将此变量设置为true。
     * 
     * 
     * @see #substituteColorModel
     * @see #filterRGB
     * @see IndexColorModel
     */
    protected boolean canFilterIndexColorModel;

    /**
     * If the ColorModel is an IndexColorModel and the subclass has
     * set the canFilterIndexColorModel flag to true, we substitute
     * a filtered version of the color model here and wherever
     * that original ColorModel object appears in the setPixels methods.
     * If the ColorModel is not an IndexColorModel or is null, this method
     * overrides the default ColorModel used by the ImageProducer and
     * specifies the default RGB ColorModel instead.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose pixels
     * are being filtered. Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  如果ColorModel是一个IndexColorModel,并且子类将canFilterIndexColorModel标志设置为true,那么在这里,以及原始ColorModel对象出现在setPi
     * xels方法中的哪个位置,都会替换颜色模型的过滤版本。
     * 如果ColorModel不是IndexColorModel或为null,此方法将覆盖ImageProducer使用的默认ColorModel,并指定默认的RGB ColorModel。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer
     * @see ColorModel#getRGBdefault
     */
    public void setColorModel(ColorModel model) {
        if (canFilterIndexColorModel && (model instanceof IndexColorModel)) {
            ColorModel newcm = filterIndexColorModel((IndexColorModel)model);
            substituteColorModel(model, newcm);
            consumer.setColorModel(newcm);
        } else {
            consumer.setColorModel(ColorModel.getRGBdefault());
        }
    }

    /**
     * Registers two ColorModel objects for substitution.  If the oldcm
     * is encountered during any of the setPixels methods, the newcm
     * is substituted and the pixels passed through
     * untouched (but with the new ColorModel object).
     * <p>
     *  注册两个ColorModel对象以进行替换。如果在任何setPixels方法期间遇到oldcm,则newcm将被替换,像​​素通过未触摸的(但使用新的ColorModel对象)。
     * 
     * 
     * @param oldcm the ColorModel object to be replaced on the fly
     * @param newcm the ColorModel object to replace oldcm on the fly
     */
    public void substituteColorModel(ColorModel oldcm, ColorModel newcm) {
        origmodel = oldcm;
        newmodel = newcm;
    }

    /**
     * Filters an IndexColorModel object by running each entry in its
     * color tables through the filterRGB function that RGBImageFilter
     * subclasses must provide.  Uses coordinates of -1 to indicate that
     * a color table entry is being filtered rather than an actual
     * pixel value.
     * <p>
     * 通过RGBImageFilter子类必须提供的filterRGB函数运行其颜色表中的每个条目来过滤IndexColorModel对象。使用坐标为-1表示正在过滤颜色表条目,而不是实际的像素值。
     * 
     * 
     * @param icm the IndexColorModel object to be filtered
     * @exception NullPointerException if <code>icm</code> is null
     * @return a new IndexColorModel representing the filtered colors
     */
    public IndexColorModel filterIndexColorModel(IndexColorModel icm) {
        int mapsize = icm.getMapSize();
        byte r[] = new byte[mapsize];
        byte g[] = new byte[mapsize];
        byte b[] = new byte[mapsize];
        byte a[] = new byte[mapsize];
        icm.getReds(r);
        icm.getGreens(g);
        icm.getBlues(b);
        icm.getAlphas(a);
        int trans = icm.getTransparentPixel();
        boolean needalpha = false;
        for (int i = 0; i < mapsize; i++) {
            int rgb = filterRGB(-1, -1, icm.getRGB(i));
            a[i] = (byte) (rgb >> 24);
            if (a[i] != ((byte)0xff) && i != trans) {
                needalpha = true;
            }
            r[i] = (byte) (rgb >> 16);
            g[i] = (byte) (rgb >> 8);
            b[i] = (byte) (rgb >> 0);
        }
        if (needalpha) {
            return new IndexColorModel(icm.getPixelSize(), mapsize,
                                       r, g, b, a);
        } else {
            return new IndexColorModel(icm.getPixelSize(), mapsize,
                                       r, g, b, trans);
        }
    }

    /**
     * Filters a buffer of pixels in the default RGB ColorModel by passing
     * them one by one through the filterRGB method.
     * <p>
     *  通过filterRGB方法逐个传递默认RGB ColorModel中的像素的缓冲区。
     * 
     * 
     * @param x the X coordinate of the upper-left corner of the region
     *          of pixels
     * @param y the Y coordinate of the upper-left corner of the region
     *          of pixels
     * @param w the width of the region of pixels
     * @param h the height of the region of pixels
     * @param pixels the array of pixels
     * @param off the offset into the <code>pixels</code> array
     * @param scansize the distance from one row of pixels to the next
     *        in the array
     * @see ColorModel#getRGBdefault
     * @see #filterRGB
     */
    public void filterRGBPixels(int x, int y, int w, int h,
                                int pixels[], int off, int scansize) {
        int index = off;
        for (int cy = 0; cy < h; cy++) {
            for (int cx = 0; cx < w; cx++) {
                pixels[index] = filterRGB(x + cx, y + cy, pixels[index]);
                index++;
            }
            index += scansize - w;
        }
        consumer.setPixels(x, y, w, h, ColorModel.getRGBdefault(),
                           pixels, off, scansize);
    }

    /**
     * If the ColorModel object is the same one that has already
     * been converted, then simply passes the pixels through with the
     * converted ColorModel. Otherwise converts the buffer of byte
     * pixels to the default RGB ColorModel and passes the converted
     * buffer to the filterRGBPixels method to be converted one by one.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose pixels
     * are being filtered. Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  如果ColorModel对象与已经转换的对象相同,则只使用转换后的ColorModel传递像素。
     * 否则将字节像素的缓冲区转换为默认的RGB ColorModel,并将转换后的缓冲区传递给filterRGBPixels方法以逐个转换。
     * <p>
     *  注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ColorModel#getRGBdefault
     * @see #filterRGBPixels
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, byte pixels[], int off,
                          int scansize) {
        if (model == origmodel) {
            consumer.setPixels(x, y, w, h, newmodel, pixels, off, scansize);
        } else {
            int filteredpixels[] = new int[w];
            int index = off;
            for (int cy = 0; cy < h; cy++) {
                for (int cx = 0; cx < w; cx++) {
                    filteredpixels[cx] = model.getRGB((pixels[index] & 0xff));
                    index++;
                }
                index += scansize - w;
                filterRGBPixels(x, y + cy, w, 1, filteredpixels, 0, w);
            }
        }
    }

    /**
     * If the ColorModel object is the same one that has already
     * been converted, then simply passes the pixels through with the
     * converted ColorModel, otherwise converts the buffer of integer
     * pixels to the default RGB ColorModel and passes the converted
     * buffer to the filterRGBPixels method to be converted one by one.
     * Converts a buffer of integer pixels to the default RGB ColorModel
     * and passes the converted buffer to the filterRGBPixels method.
     * <p>
     * Note: This method is intended to be called by the
     * <code>ImageProducer</code> of the <code>Image</code> whose pixels
     * are being filtered. Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  如果ColorModel对象与已经转换的相同,那么只需通过转换的ColorModel传递像素,否则将整数像素的缓冲区转换为默认的RGB ColorModel,并将转换后的缓冲区传递给filterRGB
     * Pixels方法进行转换一个。
     * 将整数像素的缓冲区转换为默认的RGB ColorModel,并将转换后的缓冲区传递给filterRGBPixels方法。
     * <p>
     * 注意：此方法旨在由其像素被过滤的<code> Image </code>的<code> ImageProducer </code>调用。
     * 
     * @see ColorModel#getRGBdefault
     * @see #filterRGBPixels
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, int pixels[], int off,
                          int scansize) {
        if (model == origmodel) {
            consumer.setPixels(x, y, w, h, newmodel, pixels, off, scansize);
        } else {
            int filteredpixels[] = new int[w];
            int index = off;
            for (int cy = 0; cy < h; cy++) {
                for (int cx = 0; cx < w; cx++) {
                    filteredpixels[cx] = model.getRGB(pixels[index]);
                    index++;
                }
                index += scansize - w;
                filterRGBPixels(x, y + cy, w, 1, filteredpixels, 0, w);
            }
        }
    }

    /**
     * Subclasses must specify a method to convert a single input pixel
     * in the default RGB ColorModel to a single output pixel.
     * <p>
     * 使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @param x the X coordinate of the pixel
     * @param y the Y coordinate of the pixel
     * @param rgb the integer pixel representation in the default RGB
     *            color model
     * @return a filtered pixel in the default RGB color model.
     * @see ColorModel#getRGBdefault
     * @see #filterRGBPixels
     */
    public abstract int filterRGB(int x, int y, int rgb);
}
