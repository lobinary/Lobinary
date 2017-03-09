/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2008, Oracle and/or its affiliates. All rights reserved.
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

/* ********************************************************************
 **********************************************************************
 **********************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997                      ***
 *** As  an unpublished  work pursuant to Title 17 of the United    ***
 *** States Code.  All rights reserved.                             ***
 **********************************************************************
 **********************************************************************
 * <p>
 *  **************************************************** ****************** ****************************
 * **** ************************************ * COPYRIGHT(c)Eastman Kodak Company,1997 *** *根据United *** 
 * *国家法典第17章的未公布的工作。
 * 版权所有。
 *  *** *********************************************** ********************* **************************
 * *** ***************************************。
 * 版权所有。
 * 
 * 
 **********************************************************************/

package java.awt.image.renderable;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DirectColorModel;
import java.awt.image.ImageConsumer;
import java.awt.image.ImageProducer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.util.Enumeration;
import java.util.Vector;

/**
 * An adapter class that implements ImageProducer to allow the
 * asynchronous production of a RenderableImage.  The size of the
 * ImageConsumer is determined by the scale factor of the usr2dev
 * transform in the RenderContext.  If the RenderContext is null, the
 * default rendering of the RenderableImage is used.  This class
 * implements an asynchronous production that produces the image in
 * one thread at one resolution.  This class may be subclassed to
 * implement versions that will render the image using several
 * threads.  These threads could render either the same image at
 * progressively better quality, or different sections of the image at
 * a single resolution.
 * <p>
 *  实现ImageProducer以允许异步生成RenderableImage的适配器类。 ImageConsumer的大小由RenderContext中的usr2dev变换的缩放因子决定。
 * 如果RenderContext为null,则使用RenderableImage的默认呈现。此类实现异步生产,以一种分辨率在一个线程中生成映像。这个类可以被子类化以实现将使用多个线程渲染图像的版本。
 * 这些线程可以以逐渐更好的质量呈现相同的图像,或者以单个分辨率呈现图像的不同部分。
 * 
 */
public class RenderableImageProducer implements ImageProducer, Runnable {

    /** The RenderableImage source for the producer. */
    RenderableImage rdblImage;

    /** The RenderContext to use for producing the image. */
    RenderContext rc;

    /** A Vector of image consumers. */
    Vector ics = new Vector();

    /**
     * Constructs a new RenderableImageProducer from a RenderableImage
     * and a RenderContext.
     *
     * <p>
     *  从RenderableImage和RenderContext构造一个新的RenderableImageProducer。
     * 
     * 
     * @param rdblImage the RenderableImage to be rendered.
     * @param rc the RenderContext to use for producing the pixels.
     */
    public RenderableImageProducer(RenderableImage rdblImage,
                                   RenderContext rc) {
        this.rdblImage = rdblImage;
        this.rc = rc;
    }

    /**
     * Sets a new RenderContext to use for the next startProduction() call.
     *
     * <p>
     *  设置一个新的RenderContext用于下一个startProduction()调用。
     * 
     * 
     * @param rc the new RenderContext.
     */
    public synchronized void setRenderContext(RenderContext rc) {
        this.rc = rc;
    }

   /**
     * Adds an ImageConsumer to the list of consumers interested in
     * data for this image.
     *
     * <p>
     * 将ImageConsumer添加到对此图像的数据感兴趣的消费者列表中。
     * 
     * 
     * @param ic an ImageConsumer to be added to the interest list.
     */
    public synchronized void addConsumer(ImageConsumer ic) {
        if (!ics.contains(ic)) {
            ics.addElement(ic);
        }
    }

    /**
     * Determine if an ImageConsumer is on the list of consumers
     * currently interested in data for this image.
     *
     * <p>
     *  确定ImageConsumer是否在当前对此图像的数据感兴趣的消费者列表中。
     * 
     * 
     * @param ic the ImageConsumer to be checked.
     * @return true if the ImageConsumer is on the list; false otherwise.
     */
    public synchronized boolean isConsumer(ImageConsumer ic) {
        return ics.contains(ic);
    }

    /**
     * Remove an ImageConsumer from the list of consumers interested in
     * data for this image.
     *
     * <p>
     *  从对此图像的数据感兴趣的消费者列表中删除ImageConsumer。
     * 
     * 
     * @param ic the ImageConsumer to be removed.
     */
    public synchronized void removeConsumer(ImageConsumer ic) {
        ics.removeElement(ic);
    }

    /**
     * Adds an ImageConsumer to the list of consumers interested in
     * data for this image, and immediately starts delivery of the
     * image data through the ImageConsumer interface.
     *
     * <p>
     *  将ImageConsumer添加到对此图像的数据感兴趣的消费者列表中,并立即通过ImageConsumer界面开始传递图像数据。
     * 
     * 
     * @param ic the ImageConsumer to be added to the list of consumers.
     */
    public synchronized void startProduction(ImageConsumer ic) {
        addConsumer(ic);
        // Need to build a runnable object for the Thread.
        Thread thread = new Thread(this, "RenderableImageProducer Thread");
        thread.start();
    }

    /**
     * Requests that a given ImageConsumer have the image data delivered
     * one more time in top-down, left-right order.
     *
     * <p>
     *  请求给定的ImageConsumer具有以自上而下,左右顺序再次传送图像数据。
     * 
     * 
     * @param ic the ImageConsumer requesting the resend.
     */
    public void requestTopDownLeftRightResend(ImageConsumer ic) {
        // So far, all pixels are already sent in TDLR order
    }

    /**
     * The runnable method for this class. This will produce an image using
     * the current RenderableImage and RenderContext and send it to all the
     * ImageConsumer currently registered with this class.
     * <p>
     *  这个类的runnable方法。这将产生一个使用当前RenderableImage和RenderContext的图像,并将其发送到当前注册此类的所有ImageConsumer。
     */
    public void run() {
        // First get the rendered image
        RenderedImage rdrdImage;
        if (rc != null) {
            rdrdImage = rdblImage.createRendering(rc);
        } else {
            rdrdImage = rdblImage.createDefaultRendering();
        }

        // And its ColorModel
        ColorModel colorModel = rdrdImage.getColorModel();
        Raster raster = rdrdImage.getData();
        SampleModel sampleModel = raster.getSampleModel();
        DataBuffer dataBuffer = raster.getDataBuffer();

        if (colorModel == null) {
            colorModel = ColorModel.getRGBdefault();
        }
        int minX = raster.getMinX();
        int minY = raster.getMinY();
        int width = raster.getWidth();
        int height = raster.getHeight();

        Enumeration icList;
        ImageConsumer ic;
        // Set up the ImageConsumers
        icList = ics.elements();
        while (icList.hasMoreElements()) {
            ic = (ImageConsumer)icList.nextElement();
            ic.setDimensions(width,height);
            ic.setHints(ImageConsumer.TOPDOWNLEFTRIGHT |
                        ImageConsumer.COMPLETESCANLINES |
                        ImageConsumer.SINGLEPASS |
                        ImageConsumer.SINGLEFRAME);
        }

        // Get RGB pixels from the raster scanline by scanline and
        // send to consumers.
        int pix[] = new int[width];
        int i,j;
        int numBands = sampleModel.getNumBands();
        int tmpPixel[] = new int[numBands];
        for (j = 0; j < height; j++) {
            for(i = 0; i < width; i++) {
                sampleModel.getPixel(i, j, tmpPixel, dataBuffer);
                pix[i] = colorModel.getDataElement(tmpPixel, 0);
            }
            // Now send the scanline to the Consumers
            icList = ics.elements();
            while (icList.hasMoreElements()) {
                ic = (ImageConsumer)icList.nextElement();
                ic.setPixels(0, j, width, 1, colorModel, pix, 0, width);
            }
        }

        // Now tell the consumers we're done.
        icList = ics.elements();
        while (icList.hasMoreElements()) {
            ic = (ImageConsumer)icList.nextElement();
            ic.imageComplete(ImageConsumer.STATICIMAGEDONE);
        }
    }
}
