/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
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

/**
 * This class implements a filter for the set of interface methods that
 * are used to deliver data from an ImageProducer to an ImageConsumer.
 * It is meant to be used in conjunction with a FilteredImageSource
 * object to produce filtered versions of existing images.  It is a
 * base class that provides the calls needed to implement a "Null filter"
 * which has no effect on the data being passed through.  Filters should
 * subclass this class and override the methods which deal with the
 * data that needs to be filtered and modify it as necessary.
 *
 * <p>
 *  这个类实现了一组用于将数据从ImageProducer传递到ImageConsumer的接口方法的过滤器。它意味着与FilteredImageSource对象结合使用,以生成现有映像的过滤版本。
 * 它是一个基类,它提供了实现一个"空过滤器"所需的调用,它对正在传递的数据没有影响。过滤器应该子类化此类,并覆盖处理需要过滤的数据的方法,并根据需要修改它。
 * 
 * 
 * @see FilteredImageSource
 * @see ImageConsumer
 *
 * @author      Jim Graham
 */
public class ImageFilter implements ImageConsumer, Cloneable {
    /**
     * The consumer of the particular image data stream for which this
     * instance of the ImageFilter is filtering data.  It is not
     * initialized during the constructor, but rather during the
     * getFilterInstance() method call when the FilteredImageSource
     * is creating a unique instance of this object for a particular
     * image data stream.
     * <p>
     *  ImageFilter的此实例正在对其进行数据过滤的特定图像数据流的消费者。
     * 它不是在构造函数期间初始化的,而是在getFilterInstance()方法调用期间,当FilteredImageSource为特定图像数据流创建此对象的唯一实例时。
     * 
     * 
     * @see #getFilterInstance
     * @see ImageConsumer
     */
    protected ImageConsumer consumer;

    /**
     * Returns a unique instance of an ImageFilter object which will
     * actually perform the filtering for the specified ImageConsumer.
     * The default implementation just clones this object.
     * <p>
     * Note: This method is intended to be called by the ImageProducer
     * of the Image whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  返回一个ImageFilter对象的唯一实例,该对象实际上将对指定的ImageConsumer执行过滤。默认实现只是克隆这个对象。
     * <p>
     *  注意：此方法旨在由其像素正在被过滤的图像的ImageProducer调用。使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @return an <code>ImageFilter</code> used to perform the
     *         filtering for the specified <code>ImageConsumer</code>.
     */
    public ImageFilter getFilterInstance(ImageConsumer ic) {
        ImageFilter instance = (ImageFilter) clone();
        instance.consumer = ic;
        return instance;
    }

    /**
     * Filters the information provided in the setDimensions method
     * of the ImageConsumer interface.
     * <p>
     * Note: This method is intended to be called by the ImageProducer
     * of the Image whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     * 过滤在ImageConsumer接口的setDimensions方法中提供的信息。
     * <p>
     *  注意：此方法旨在由其像素正在被过滤的图像的ImageProducer调用。使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer#setDimensions
     */
    public void setDimensions(int width, int height) {
        consumer.setDimensions(width, height);
    }

    /**
     * Passes the properties from the source object along after adding a
     * property indicating the stream of filters it has been run through.
     * <p>
     * Note: This method is intended to be called by the ImageProducer
     * of the Image whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     *
     * <p>
     *  在添加指示其已经运行的过滤器流的属性之后,从源对象传递属性。
     * <p>
     *  注意：此方法旨在由其像素正在被过滤的图像的ImageProducer调用。使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @param props the properties from the source object
     * @exception NullPointerException if <code>props</code> is null
     */
    public void setProperties(Hashtable<?,?> props) {
        Hashtable<Object,Object> p = (Hashtable<Object,Object>)props.clone();
        Object o = p.get("filters");
        if (o == null) {
            p.put("filters", toString());
        } else if (o instanceof String) {
            p.put("filters", ((String) o)+toString());
        }
        consumer.setProperties(p);
    }

    /**
     * Filter the information provided in the setColorModel method
     * of the ImageConsumer interface.
     * <p>
     * Note: This method is intended to be called by the ImageProducer
     * of the Image whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  过滤ImageConsumer接口的setColorModel方法中提供的信息。
     * <p>
     *  注意：此方法旨在由其像素正在被过滤的图像的ImageProducer调用。使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer#setColorModel
     */
    public void setColorModel(ColorModel model) {
        consumer.setColorModel(model);
    }

    /**
     * Filters the information provided in the setHints method
     * of the ImageConsumer interface.
     * <p>
     * Note: This method is intended to be called by the ImageProducer
     * of the Image whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  过滤在ImageConsumer接口的setHints方法中提供的信息。
     * <p>
     * 注意：此方法旨在由其像素正在被过滤的图像的ImageProducer调用。使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer#setHints
     */
    public void setHints(int hints) {
        consumer.setHints(hints);
    }

    /**
     * Filters the information provided in the setPixels method of the
     * ImageConsumer interface which takes an array of bytes.
     * <p>
     * Note: This method is intended to be called by the ImageProducer
     * of the Image whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  过滤在ImageConsumer接口的setPixels方法中提供的信息,该方法需要一个字节数组。
     * <p>
     *  注意：此方法旨在由其像素正在被过滤的图像的ImageProducer调用。使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer#setPixels
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, byte pixels[], int off,
                          int scansize) {
        consumer.setPixels(x, y, w, h, model, pixels, off, scansize);
    }

    /**
     * Filters the information provided in the setPixels method of the
     * ImageConsumer interface which takes an array of integers.
     * <p>
     * Note: This method is intended to be called by the ImageProducer
     * of the Image whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  过滤在ImageConsumer接口的setPixels方法中提供的信息,该方法需要一个整数数组。
     * <p>
     *  注意：此方法旨在由其像素正在被过滤的图像的ImageProducer调用。使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer#setPixels
     */
    public void setPixels(int x, int y, int w, int h,
                          ColorModel model, int pixels[], int off,
                          int scansize) {
        consumer.setPixels(x, y, w, h, model, pixels, off, scansize);
    }

    /**
     * Filters the information provided in the imageComplete method of
     * the ImageConsumer interface.
     * <p>
     * Note: This method is intended to be called by the ImageProducer
     * of the Image whose pixels are being filtered.  Developers using
     * this class to filter pixels from an image should avoid calling
     * this method directly since that operation could interfere
     * with the filtering operation.
     * <p>
     *  过滤ImageConsumer接口的imageComplete方法中提供的信息。
     * <p>
     *  注意：此方法旨在由其像素正在被过滤的图像的ImageProducer调用。使用此类从图像中过滤像素的开发人员应避免直接调用此方法,因为该操作可能会干扰过滤操作。
     * 
     * 
     * @see ImageConsumer#imageComplete
     */
    public void imageComplete(int status) {
        consumer.imageComplete(status);
    }

    /**
     * Responds to a request for a TopDownLeftRight (TDLR) ordered resend
     * of the pixel data from an <code>ImageConsumer</code>.
     * When an <code>ImageConsumer</code> being fed
     * by an instance of this <code>ImageFilter</code>
     * requests a resend of the data in TDLR order,
     * the <code>FilteredImageSource</code>
     * invokes this method of the <code>ImageFilter</code>.
     *
     * <p>
     *
     * An <code>ImageFilter</code> subclass might override this method or not,
     * depending on if and how it can send data in TDLR order.
     * Three possibilities exist:
     *
     * <ul>
     * <li>
     * Do not override this method.
     * This makes the subclass use the default implementation,
     * which is to
     * forward the request
     * to the indicated <code>ImageProducer</code>
     * using this filter as the requesting <code>ImageConsumer</code>.
     * This behavior
     * is appropriate if the filter can determine
     * that it will forward the pixels
     * in TDLR order if its upstream producer object
     * sends them in TDLR order.
     *
     * <li>
     * Override the method to simply send the data.
     * This is appropriate if the filter can handle the request itself &#151;
     * for example,
     * if the generated pixels have been saved in some sort of buffer.
     *
     * <li>
     * Override the method to do nothing.
     * This is appropriate
     * if the filter cannot produce filtered data in TDLR order.
     * </ul>
     *
     * <p>
     * 响应对TopDownLeftRight(TDLR)的请求,从<code> ImageConsumer </code>中有序重新发送像素数据。
     * 当由此<code> ImageFilter </code>的实例提供的<code> ImageConsumer </code>请求以TDLR顺序重新发送数据时,<code> FilteredImageS
     * ource </code>调用<代码> ImageFilter </code>。
     * 响应对TopDownLeftRight(TDLR)的请求,从<code> ImageConsumer </code>中有序重新发送像素数据。
     * 
     * <p>
     * 
     *  一个<code> ImageFilter </code>子类可能重写这个方法或不重叠,这取决于它是否以及如何以TDLR顺序发送数据。存在三种可能性：
     * 
     * <ul>
     * <li>
     *  不要覆盖此方法。这使得子类使用默认实现,即使用此过滤器作为请求<code> ImageConsumer </code>将请求转发到指示的<code> ImageProducer </code>。
     * 
     * @see ImageProducer#requestTopDownLeftRightResend
     * @param ip the ImageProducer that is feeding this instance of
     * the filter - also the ImageProducer that the request should be
     * forwarded to if necessary
     * @exception NullPointerException if <code>ip</code> is null
     */
    public void resendTopDownLeftRight(ImageProducer ip) {
        ip.requestTopDownLeftRightResend(this);
    }

    /**
     * Clones this object.
     * <p>
     * 如果过滤器可以确定它将以TDLR顺序转发像素,如果其上游生成器对象以TDLR顺序发送它们,则此行为是适当的。
     * 
     * <li>
     *  覆盖该方法以简单地发送数据。这是适当的,如果过滤器可以处理请求本身,例如,如果生成的像素已保存在某种缓冲区。
     * 
     * <li>
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }
}
