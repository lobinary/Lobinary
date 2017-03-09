/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2003, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Image;
import java.awt.image.ImageFilter;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.util.Hashtable;
import java.awt.image.ColorModel;

/**
 * This class is an implementation of the ImageProducer interface which
 * takes an existing image and a filter object and uses them to produce
 * image data for a new filtered version of the original image.
 * Here is an example which filters an image by swapping the red and
 * blue compents:
 * <pre>
 *
 *      Image src = getImage("doc:///demo/images/duke/T1.gif");
 *      ImageFilter colorfilter = new RedBlueSwapFilter();
 *      Image img = createImage(new FilteredImageSource(src.getSource(),
 *                                                      colorfilter));
 *
 * </pre>
 *
 * <p>
 *  此类是ImageProducer接口的实现,它接收现有图像和过滤器对象,并使用它们为原始图像的新过滤版本生成图像数据。下面是一个通过交换红色和蓝色字符来过滤图像的示例：
 * <pre>
 * 
 *  Image src = getImage("doc：///demo/images/duke/T1.gif"); ImageFilter colorfilter = new RedBlueSwapFil
 * ter(); Image img = createImage(new FilteredImageSource(src.getSource(),colorfilter));。
 * 
 * </pre>
 * 
 * 
 * @see ImageProducer
 *
 * @author      Jim Graham
 */
public class FilteredImageSource implements ImageProducer {
    ImageProducer src;
    ImageFilter filter;

    /**
     * Constructs an ImageProducer object from an existing ImageProducer
     * and a filter object.
     * <p>
     *  构造来自现有ImageProducer和过滤器对象的ImageProducer对象。
     * 
     * 
     * @param orig the specified <code>ImageProducer</code>
     * @param imgf the specified <code>ImageFilter</code>
     * @see ImageFilter
     * @see java.awt.Component#createImage
     */
    public FilteredImageSource(ImageProducer orig, ImageFilter imgf) {
        src = orig;
        filter = imgf;
    }

    private Hashtable proxies;

    /**
     * Adds the specified <code>ImageConsumer</code>
     * to the list of consumers interested in data for the filtered image.
     * An instance of the original <code>ImageFilter</code>
     * is created
     * (using the filter's <code>getFilterInstance</code> method)
     * to manipulate the image data
     * for the specified <code>ImageConsumer</code>.
     * The newly created filter instance
     * is then passed to the <code>addConsumer</code> method
     * of the original <code>ImageProducer</code>.
     *
     * <p>
     * This method is public as a side effect
     * of this class implementing
     * the <code>ImageProducer</code> interface.
     * It should not be called from user code,
     * and its behavior if called from user code is unspecified.
     *
     * <p>
     *  将指定的<code> ImageConsumer </code>添加到对过滤图像的数据感兴趣的用户列表中。
     * 创建原始<code> ImageFilter </code>的一个实例(使用过滤器的<code> getFilterInstance </code>方法)来操作指定的<code> ImageConsum
     * er </code>的图像数据。
     *  将指定的<code> ImageConsumer </code>添加到对过滤图像的数据感兴趣的用户列表中。
     * 然后将新创建的过滤器实例传递给原始<code> ImageProducer </code>的<code> addConsumer </code>方法。
     * 
     * <p>
     *  此方法是公共的,作为实现<code> ImageProducer </code>接口的类的副作用。它不应该从用户代码调用,如果从用户代码调用它的行为是未指定的。
     * 
     * 
     * @param ic  the consumer for the filtered image
     * @see ImageConsumer
     */
    public synchronized void addConsumer(ImageConsumer ic) {
        if (proxies == null) {
            proxies = new Hashtable();
        }
        if (!proxies.containsKey(ic)) {
            ImageFilter imgf = filter.getFilterInstance(ic);
            proxies.put(ic, imgf);
            src.addConsumer(imgf);
        }
    }

    /**
     * Determines whether an ImageConsumer is on the list of consumers
     * currently interested in data for this image.
     *
     * <p>
     * This method is public as a side effect
     * of this class implementing
     * the <code>ImageProducer</code> interface.
     * It should not be called from user code,
     * and its behavior if called from user code is unspecified.
     *
     * <p>
     *  确定ImageConsumer是否在当前对此图像的数据感兴趣的消费者列表上。
     * 
     * <p>
     * 此方法是公共的,作为实现<code> ImageProducer </code>接口的类的副作用。它不应该从用户代码调用,如果从用户代码调用它的行为是未指定的。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @return true if the ImageConsumer is on the list; false otherwise
     * @see ImageConsumer
     */
    public synchronized boolean isConsumer(ImageConsumer ic) {
        return (proxies != null && proxies.containsKey(ic));
    }

    /**
     * Removes an ImageConsumer from the list of consumers interested in
     * data for this image.
     *
     * <p>
     * This method is public as a side effect
     * of this class implementing
     * the <code>ImageProducer</code> interface.
     * It should not be called from user code,
     * and its behavior if called from user code is unspecified.
     *
     * <p>
     *  从对此图像的数据感兴趣的消费者列表中删除ImageConsumer。
     * 
     * <p>
     *  此方法是公共的,作为实现<code> ImageProducer </code>接口的类的副作用。它不应该从用户代码调用,如果从用户代码调用它的行为是未指定的。
     * 
     * 
     * @see ImageConsumer
     */
    public synchronized void removeConsumer(ImageConsumer ic) {
        if (proxies != null) {
            ImageFilter imgf = (ImageFilter) proxies.get(ic);
            if (imgf != null) {
                src.removeConsumer(imgf);
                proxies.remove(ic);
                if (proxies.isEmpty()) {
                    proxies = null;
                }
            }
        }
    }

    /**
     * Starts production of the filtered image.
     * If the specified <code>ImageConsumer</code>
     * isn't already a consumer of the filtered image,
     * an instance of the original <code>ImageFilter</code>
     * is created
     * (using the filter's <code>getFilterInstance</code> method)
     * to manipulate the image data
     * for the <code>ImageConsumer</code>.
     * The filter instance for the <code>ImageConsumer</code>
     * is then passed to the <code>startProduction</code> method
     * of the original <code>ImageProducer</code>.
     *
     * <p>
     * This method is public as a side effect
     * of this class implementing
     * the <code>ImageProducer</code> interface.
     * It should not be called from user code,
     * and its behavior if called from user code is unspecified.
     *
     * <p>
     *  开始生成过滤的图像。
     * 如果指定的<code> ImageConsumer </code>不是已过滤的图像的使用者,则创建原始<code> ImageFilter </code>的一个实例(使用过滤器的<code> getFi
     * lterInstance </code>方法)来处理<code> ImageConsumer </code>的图像数据。
     *  开始生成过滤的图像。
     * 然后将<code> ImageConsumer </code>的过滤器实例传递给原始<code> ImageProducer </code>的<code> startProduction </code>
     * 方法。
     *  开始生成过滤的图像。
     * 
     * <p>
     * 
     * @param ic  the consumer for the filtered image
     * @see ImageConsumer
     */
    public void startProduction(ImageConsumer ic) {
        if (proxies == null) {
            proxies = new Hashtable();
        }
        ImageFilter imgf = (ImageFilter) proxies.get(ic);
        if (imgf == null) {
            imgf = filter.getFilterInstance(ic);
            proxies.put(ic, imgf);
        }
        src.startProduction(imgf);
    }

    /**
     * Requests that a given ImageConsumer have the image data delivered
     * one more time in top-down, left-right order.  The request is
     * handed to the ImageFilter for further processing, since the
     * ability to preserve the pixel ordering depends on the filter.
     *
     * <p>
     * This method is public as a side effect
     * of this class implementing
     * the <code>ImageProducer</code> interface.
     * It should not be called from user code,
     * and its behavior if called from user code is unspecified.
     *
     * <p>
     *  此方法是公共的,作为实现<code> ImageProducer </code>接口的类的副作用。它不应该从用户代码调用,如果从用户代码调用它的行为是未指定的。
     * 
     * 
     * @see ImageConsumer
     */
    public void requestTopDownLeftRightResend(ImageConsumer ic) {
        if (proxies != null) {
            ImageFilter imgf = (ImageFilter) proxies.get(ic);
            if (imgf != null) {
                imgf.resendTopDownLeftRight(src);
            }
        }
    }
}
