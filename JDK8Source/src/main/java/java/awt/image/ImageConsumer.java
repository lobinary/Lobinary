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

import java.util.Hashtable;


/**
 * The interface for objects expressing interest in image data through
 * the ImageProducer interfaces.  When a consumer is added to an image
 * producer, the producer delivers all of the data about the image
 * using the method calls defined in this interface.
 *
 * <p>
 *  通过ImageProducer界面表达对图像数据感兴趣的对象的界面。当消费者被添加到图像制作者时,制作者使用在该接口中定义的方法调用来递送关于图像的所有数据。
 * 
 * 
 * @see ImageProducer
 *
 * @author      Jim Graham
 */
public interface ImageConsumer {
    /**
     * The dimensions of the source image are reported using the
     * setDimensions method call.
     * <p>
     *  源图像的尺寸使用setDimensions方法调用进行报告。
     * 
     * 
     * @param width the width of the source image
     * @param height the height of the source image
     */
    void setDimensions(int width, int height);

    /**
     * Sets the extensible list of properties associated with this image.
     * <p>
     *  设置与此图像关联的属性的可扩展列表。
     * 
     * 
     * @param props the list of properties to be associated with this
     *        image
     */
    void setProperties(Hashtable<?,?> props);

    /**
     * Sets the ColorModel object used for the majority of
     * the pixels reported using the setPixels method
     * calls.  Note that each set of pixels delivered using setPixels
     * contains its own ColorModel object, so no assumption should
     * be made that this model will be the only one used in delivering
     * pixel values.  A notable case where multiple ColorModel objects
     * may be seen is a filtered image when for each set of pixels
     * that it filters, the filter
     * determines  whether the
     * pixels can be sent on untouched, using the original ColorModel,
     * or whether the pixels should be modified (filtered) and passed
     * on using a ColorModel more convenient for the filtering process.
     * <p>
     *  设置用于使用setPixels方法调用报告的大多数像素的ColorModel对象。
     * 请注意,使用setPixels传送的每组像素包含其自己的ColorModel对象,因此不应假设该模型将是用于传递像素值的唯一一个。
     * 可以看到多个ColorModel对象的一个​​值得注意的情况是过滤的图像,当它过滤的每组像素时,过滤器确定是否可以使用原始ColorModel在未触摸的情况下发送像素,或者是否应该修改像素)和传递使用C
     * olorModel更方便的过滤过程。
     * 请注意,使用setPixels传送的每组像素包含其自己的ColorModel对象,因此不应假设该模型将是用于传递像素值的唯一一个。
     * 
     * 
     * @param model the specified <code>ColorModel</code>
     * @see ColorModel
     */
    void setColorModel(ColorModel model);

    /**
     * Sets the hints that the ImageConsumer uses to process the
     * pixels delivered by the ImageProducer.
     * The ImageProducer can deliver the pixels in any order, but
     * the ImageConsumer may be able to scale or convert the pixels
     * to the destination ColorModel more efficiently or with higher
     * quality if it knows some information about how the pixels will
     * be delivered up front.  The setHints method should be called
     * before any calls to any of the setPixels methods with a bit mask
     * of hints about the manner in which the pixels will be delivered.
     * If the ImageProducer does not follow the guidelines for the
     * indicated hint, the results are undefined.
     * <p>
     * 设置ImageConsumer用于处理由ImageProducer传递的像素的提示。
     *  ImageProducer可以以任何顺序递送像素,但是如果ImageConsumer知道关于像素将如何被传送的一些信息,则ImageConsumer可以能够更有效地将像素缩放或转换到目的地ColorM
     * odel,或者以更高的质量。
     * 设置ImageConsumer用于处理由ImageProducer传递的像素的提示。
     * 在对任何setPixels方法的任何调用之前,应该调用setHints方法,其中具有关于像素将被传递的方式的提示的位掩码。如果ImageProducer不遵循指示的提示的准则,则结果未定义。
     * 
     * 
     * @param hintflags a set of hints that the ImageConsumer uses to
     *        process the pixels
     */
    void setHints(int hintflags);

    /**
     * The pixels will be delivered in a random order.  This tells the
     * ImageConsumer not to use any optimizations that depend on the
     * order of pixel delivery, which should be the default assumption
     * in the absence of any call to the setHints method.
     * <p>
     *  像素将以随机顺序传送。这告诉ImageConsumer不要使用依赖于像素传递顺序的任何优化,这应该是没有任何调用setHints方法的默认假设。
     * 
     * 
     * @see #setHints
     */
    int RANDOMPIXELORDER = 1;

    /**
     * The pixels will be delivered in top-down, left-to-right order.
     * <p>
     *  像素将按照从上到下,从左到右的顺序传送。
     * 
     * 
     * @see #setHints
     */
    int TOPDOWNLEFTRIGHT = 2;

    /**
     * The pixels will be delivered in (multiples of) complete scanlines
     * at a time.
     * <p>
     *  像素将一次以多个(多个)完整扫描线传送。
     * 
     * 
     * @see #setHints
     */
    int COMPLETESCANLINES = 4;

    /**
     * The pixels will be delivered in a single pass.  Each pixel will
     * appear in only one call to any of the setPixels methods.  An
     * example of an image format which does not meet this criterion
     * is a progressive JPEG image which defines pixels in multiple
     * passes, each more refined than the previous.
     * <p>
     *  像素将在单次传递中传递。每个像素将只出现在对任何setPixels方法的一次调用中。不符合该标准的图像格式的示例是逐渐JPEG图像,其定义多个遍次中的像素,每个次序比以前更精细。
     * 
     * 
     * @see #setHints
     */
    int SINGLEPASS = 8;

    /**
     * The image contain a single static image.  The pixels will be defined
     * in calls to the setPixels methods and then the imageComplete method
     * will be called with the STATICIMAGEDONE flag after which no more
     * image data will be delivered.  An example of an image type which
     * would not meet these criteria would be the output of a video feed,
     * or the representation of a 3D rendering being manipulated
     * by the user.  The end of each frame in those types of images will
     * be indicated by calling imageComplete with the SINGLEFRAMEDONE flag.
     * <p>
     * 图像包含单个静态图像。像素将在调用setPixels方法中定义,然后将使用STATICIMAGEDONE标志调用imageComplete方法,之后将不再传送更多的图像数据。
     * 不符合这些标准的图像类型的示例将是视频馈送的输出,或由用户操纵的3D渲染的表示。在这些类型的图像中的每个帧的结束将通过调用具有SINGLEFRAMEDONE标志的imageComplete来指示。
     * 
     * 
     * @see #setHints
     * @see #imageComplete
     */
    int SINGLEFRAME = 16;

    /**
     * Delivers the pixels of the image with one or more calls
     * to this method.  Each call specifies the location and
     * size of the rectangle of source pixels that are contained in
     * the array of pixels.  The specified ColorModel object should
     * be used to convert the pixels into their corresponding color
     * and alpha components.  Pixel (m,n) is stored in the pixels array
     * at index (n * scansize + m + off).  The pixels delivered using
     * this method are all stored as bytes.
     * <p>
     *  通过对此方法的一个或多个调用提供图像的像素。每个调用指定包含在像素阵列中的源像素的矩形的位置和大小。指定的ColorModel对象应该用于将像素转换为其对应的颜色和alpha分量。
     * 像素(m,n)以索引(n * scansize + m + off)存储在像素阵列中。使用此方法传送的像素都存储为字节。
     * 
     * 
     * @param x the X coordinate of the upper-left corner of the
     *        area of pixels to be set
     * @param y the Y coordinate of the upper-left corner of the
     *        area of pixels to be set
     * @param w the width of the area of pixels
     * @param h the height of the area of pixels
     * @param model the specified <code>ColorModel</code>
     * @param pixels the array of pixels
     * @param off the offset into the <code>pixels</code> array
     * @param scansize the distance from one row of pixels to the next in
     * the <code>pixels</code> array
     * @see ColorModel
     */
    void setPixels(int x, int y, int w, int h,
                   ColorModel model, byte pixels[], int off, int scansize);

    /**
     * The pixels of the image are delivered using one or more calls
     * to the setPixels method.  Each call specifies the location and
     * size of the rectangle of source pixels that are contained in
     * the array of pixels.  The specified ColorModel object should
     * be used to convert the pixels into their corresponding color
     * and alpha components.  Pixel (m,n) is stored in the pixels array
     * at index (n * scansize + m + off).  The pixels delivered using
     * this method are all stored as ints.
     * this method are all stored as ints.
     * <p>
     *  使用对setPixels方法的一个或多个调用来传递图像的像素。每个调用指定包含在像素阵列中的源像素的矩形的位置和大小。指定的ColorModel对象应该用于将像素转换为其对应的颜色和alpha分量。
     * 像素(m,n)以索引(n * scansize + m + off)存储在像素阵列中。使用此方法传送的像素都存储为int。这个方法都存储为int。
     * 
     * 
     * @param x the X coordinate of the upper-left corner of the
     *        area of pixels to be set
     * @param y the Y coordinate of the upper-left corner of the
     *        area of pixels to be set
     * @param w the width of the area of pixels
     * @param h the height of the area of pixels
     * @param model the specified <code>ColorModel</code>
     * @param pixels the array of pixels
     * @param off the offset into the <code>pixels</code> array
     * @param scansize the distance from one row of pixels to the next in
     * the <code>pixels</code> array
     * @see ColorModel
     */
    void setPixels(int x, int y, int w, int h,
                   ColorModel model, int pixels[], int off, int scansize);

    /**
     * The imageComplete method is called when the ImageProducer is
     * finished delivering all of the pixels that the source image
     * contains, or when a single frame of a multi-frame animation has
     * been completed, or when an error in loading or producing the
     * image has occurred.  The ImageConsumer should remove itself from the
     * list of consumers registered with the ImageProducer at this time,
     * unless it is interested in successive frames.
     * <p>
     * 当ImageProducer完成传递源图像包含的所有像素时,或者当多帧动画的单个帧已经完成时,或者当加载或产生图像的错误发生时,调用imageComplete方法。
     *  ImageConsumer应该在此时从ImageProducer注册的消费者列表中删除自己,除非它对连续的帧感兴趣。
     * 
     * 
     * @param status the status of image loading
     * @see ImageProducer#removeConsumer
     */
    void imageComplete(int status);

    /**
     * An error was encountered while producing the image.
     * <p>
     *  生成映像时遇到错误。
     * 
     * 
     * @see #imageComplete
     */
    int IMAGEERROR = 1;

    /**
     * One frame of the image is complete but there are more frames
     * to be delivered.
     * <p>
     *  图像的一帧是完整的,但是有更多的帧要传送。
     * 
     * 
     * @see #imageComplete
     */
    int SINGLEFRAMEDONE = 2;

    /**
     * The image is complete and there are no more pixels or frames
     * to be delivered.
     * <p>
     *  图像是完整的,没有更多的像素或帧要交付。
     * 
     * 
     * @see #imageComplete
     */
    int STATICIMAGEDONE = 3;

    /**
     * The image creation process was deliberately aborted.
     * <p>
     *  图像创建过程被故意中止。
     * 
     * @see #imageComplete
     */
    int IMAGEABORTED = 4;
}
