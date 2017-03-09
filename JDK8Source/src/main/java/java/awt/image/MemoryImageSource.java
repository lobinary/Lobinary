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
import java.awt.image.ImageProducer;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

/**
 * This class is an implementation of the ImageProducer interface which
 * uses an array to produce pixel values for an Image.  Here is an example
 * which calculates a 100x100 image representing a fade from black to blue
 * along the X axis and a fade from black to red along the Y axis:
 * <pre>{@code
 *
 *      int w = 100;
 *      int h = 100;
 *      int pix[] = new int[w * h];
 *      int index = 0;
 *      for (int y = 0; y < h; y++) {
 *          int red = (y * 255) / (h - 1);
 *          for (int x = 0; x < w; x++) {
 *              int blue = (x * 255) / (w - 1);
 *              pix[index++] = (255 << 24) | (red << 16) | blue;
 *          }
 *      }
 *      Image img = createImage(new MemoryImageSource(w, h, pix, 0, w));
 *
 * }</pre>
 * The MemoryImageSource is also capable of managing a memory image which
 * varies over time to allow animation or custom rendering.  Here is an
 * example showing how to set up the animation source and signal changes
 * in the data (adapted from the MemoryAnimationSourceDemo by Garth Dickie):
 * <pre>{@code
 *
 *      int pixels[];
 *      MemoryImageSource source;
 *
 *      public void init() {
 *          int width = 50;
 *          int height = 50;
 *          int size = width * height;
 *          pixels = new int[size];
 *
 *          int value = getBackground().getRGB();
 *          for (int i = 0; i < size; i++) {
 *              pixels[i] = value;
 *          }
 *
 *          source = new MemoryImageSource(width, height, pixels, 0, width);
 *          source.setAnimated(true);
 *          image = createImage(source);
 *      }
 *
 *      public void run() {
 *          Thread me = Thread.currentThread( );
 *          me.setPriority(Thread.MIN_PRIORITY);
 *
 *          while (true) {
 *              try {
 *                  Thread.sleep(10);
 *              } catch( InterruptedException e ) {
 *                  return;
 *              }
 *
 *              // Modify the values in the pixels array at (x, y, w, h)
 *
 *              // Send the new data to the interested ImageConsumers
 *              source.newPixels(x, y, w, h);
 *          }
 *      }
 *
 * }</pre>
 *
 * <p>
 *  这个类是ImageProducer接口的实现,它使用数组来产生图像的像素值。
 * 这里是一个例子,它计算一个100x100的图像,表示沿着X轴从黑色到蓝色的淡变,以及沿着Y轴从黑色到红色的淡化：<pre> {@ code。
 * 
 *  int w = 100; int h = 100; int pix [] = new int [w * h]; int index = 0; for(int y = 0; y <h; y ++){int red =(y * 255)/(h-1) for(int x = 0; x <w; x ++){int blue =(x * 255)/(w-1); pix [index ++] =(255 << 24)| (红色<< 16)|蓝色; }} Image img = createImage(new MemoryImageSource(w,h,pix,0,w));。
 * 
 *  } </pre> MemoryImageSource还能够管理随时间变化的内存映像,以允许动画或自定义渲染。
 * 这里是一个例子,显示如何设置动画源和信号变化的数据(改编自Garth Dickie的MemoryAnimationSourceDemo)：<pre> {@ code。
 * 
 *  int pixels []; MemoryImageSource源;
 * 
 *  public void init(){int width = 50; int height = 50; int size = width * height; pixels = new int [size];。
 * 
 *  int value = getBackground()。getRGB(); for(int i = 0; i <size; i ++){pixels [i] = value; }}
 * 
 *  source = new MemoryImageSource(width,height,pixels,0,width); source.setAnimated(true) image = create
 * Image(source); }}。
 * 
 * public void run(){Thread me = Thread.currentThread(); me.setPriority(Thread.MIN_PRIORITY);
 * 
 *  while(true){try {Thread.sleep(10); } catch(InterruptedException e){return; }}
 * 
 *  //修改像素数组中的值(x,y,w,h)
 * 
 *  //将新数据发送到感兴趣的ImageConsumers source.newPixels(x,y,w,h); }}
 * 
 *  } </pre>
 * 
 * 
 * @see ImageProducer
 *
 * @author      Jim Graham
 * @author      Animation capabilities inspired by the
 *              MemoryAnimationSource class written by Garth Dickie
 */
public class MemoryImageSource implements ImageProducer {
    int width;
    int height;
    ColorModel model;
    Object pixels;
    int pixeloffset;
    int pixelscan;
    Hashtable properties;
    Vector theConsumers = new Vector();
    boolean animating;
    boolean fullbuffers;

    /**
     * Constructs an ImageProducer object which uses an array of bytes
     * to produce data for an Image object.
     * <p>
     *  构造一个ImageProducer对象,该对象使用字节数组为Image对象生成数据。
     * 
     * 
     * @param w the width of the rectangle of pixels
     * @param h the height of the rectangle of pixels
     * @param cm the specified <code>ColorModel</code>
     * @param pix an array of pixels
     * @param off the offset into the array of where to store the
     *        first pixel
     * @param scan the distance from one row of pixels to the next in
     *        the array
     * @see java.awt.Component#createImage
     */
    public MemoryImageSource(int w, int h, ColorModel cm,
                             byte[] pix, int off, int scan) {
        initialize(w, h, cm, (Object) pix, off, scan, null);
    }

    /**
     * Constructs an ImageProducer object which uses an array of bytes
     * to produce data for an Image object.
     * <p>
     *  构造一个ImageProducer对象,该对象使用字节数组为Image对象生成数据。
     * 
     * 
     * @param w the width of the rectangle of pixels
     * @param h the height of the rectangle of pixels
     * @param cm the specified <code>ColorModel</code>
     * @param pix an array of pixels
     * @param off the offset into the array of where to store the
     *        first pixel
     * @param scan the distance from one row of pixels to the next in
     *        the array
     * @param props a list of properties that the <code>ImageProducer</code>
     *        uses to process an image
     * @see java.awt.Component#createImage
     */
    public MemoryImageSource(int w, int h, ColorModel cm,
                             byte[] pix, int off, int scan,
                             Hashtable<?,?> props)
    {
        initialize(w, h, cm, (Object) pix, off, scan, props);
    }

    /**
     * Constructs an ImageProducer object which uses an array of integers
     * to produce data for an Image object.
     * <p>
     *  构造一个ImageProducer对象,该对象使用整数数组为Image对象生成数据。
     * 
     * 
     * @param w the width of the rectangle of pixels
     * @param h the height of the rectangle of pixels
     * @param cm the specified <code>ColorModel</code>
     * @param pix an array of pixels
     * @param off the offset into the array of where to store the
     *        first pixel
     * @param scan the distance from one row of pixels to the next in
     *        the array
     * @see java.awt.Component#createImage
     */
    public MemoryImageSource(int w, int h, ColorModel cm,
                             int[] pix, int off, int scan) {
        initialize(w, h, cm, (Object) pix, off, scan, null);
    }

    /**
     * Constructs an ImageProducer object which uses an array of integers
     * to produce data for an Image object.
     * <p>
     *  构造一个ImageProducer对象,该对象使用整数数组为Image对象生成数据。
     * 
     * 
     * @param w the width of the rectangle of pixels
     * @param h the height of the rectangle of pixels
     * @param cm the specified <code>ColorModel</code>
     * @param pix an array of pixels
     * @param off the offset into the array of where to store the
     *        first pixel
     * @param scan the distance from one row of pixels to the next in
     *        the array
     * @param props a list of properties that the <code>ImageProducer</code>
     *        uses to process an image
     * @see java.awt.Component#createImage
     */
    public MemoryImageSource(int w, int h, ColorModel cm,
                             int[] pix, int off, int scan,
                             Hashtable<?,?> props)
    {
        initialize(w, h, cm, (Object) pix, off, scan, props);
    }

    private void initialize(int w, int h, ColorModel cm,
                            Object pix, int off, int scan, Hashtable props) {
        width = w;
        height = h;
        model = cm;
        pixels = pix;
        pixeloffset = off;
        pixelscan = scan;
        if (props == null) {
            props = new Hashtable();
        }
        properties = props;
    }

    /**
     * Constructs an ImageProducer object which uses an array of integers
     * in the default RGB ColorModel to produce data for an Image object.
     * <p>
     *  构造一个ImageProducer对象,它使用默认RGB ColorModel中的整数数组为Image对象生成数据。
     * 
     * 
     * @param w the width of the rectangle of pixels
     * @param h the height of the rectangle of pixels
     * @param pix an array of pixels
     * @param off the offset into the array of where to store the
     *        first pixel
     * @param scan the distance from one row of pixels to the next in
     *        the array
     * @see java.awt.Component#createImage
     * @see ColorModel#getRGBdefault
     */
    public MemoryImageSource(int w, int h, int pix[], int off, int scan) {
        initialize(w, h, ColorModel.getRGBdefault(),
                   (Object) pix, off, scan, null);
    }

    /**
     * Constructs an ImageProducer object which uses an array of integers
     * in the default RGB ColorModel to produce data for an Image object.
     * <p>
     *  构造一个ImageProducer对象,它使用默认RGB ColorModel中的整数数组为Image对象生成数据。
     * 
     * 
     * @param w the width of the rectangle of pixels
     * @param h the height of the rectangle of pixels
     * @param pix an array of pixels
     * @param off the offset into the array of where to store the
     *        first pixel
     * @param scan the distance from one row of pixels to the next in
     *        the array
     * @param props a list of properties that the <code>ImageProducer</code>
     *        uses to process an image
     * @see java.awt.Component#createImage
     * @see ColorModel#getRGBdefault
     */
    public MemoryImageSource(int w, int h, int pix[], int off, int scan,
                             Hashtable<?,?> props)
    {
        initialize(w, h, ColorModel.getRGBdefault(),
                   (Object) pix, off, scan, props);
    }

    /**
     * Adds an ImageConsumer to the list of consumers interested in
     * data for this image.
     * <p>
     *  将ImageConsumer添加到对此图像的数据感兴趣的消费者列表中。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @throws NullPointerException if the specified
     *           <code>ImageConsumer</code> is null
     * @see ImageConsumer
     */
    public synchronized void addConsumer(ImageConsumer ic) {
        if (theConsumers.contains(ic)) {
            return;
        }
        theConsumers.addElement(ic);
        try {
            initConsumer(ic);
            sendPixels(ic, 0, 0, width, height);
            if (isConsumer(ic)) {
                ic.imageComplete(animating
                                 ? ImageConsumer.SINGLEFRAMEDONE
                                 : ImageConsumer.STATICIMAGEDONE);
                if (!animating && isConsumer(ic)) {
                    ic.imageComplete(ImageConsumer.IMAGEERROR);
                    removeConsumer(ic);
                }
            }
        } catch (Exception e) {
            if (isConsumer(ic)) {
                ic.imageComplete(ImageConsumer.IMAGEERROR);
            }
        }
    }

    /**
     * Determines if an ImageConsumer is on the list of consumers currently
     * interested in data for this image.
     * <p>
     *  确定ImageConsumer是否在当前对此图像的数据感兴趣的消费者列表上。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @return <code>true</code> if the <code>ImageConsumer</code>
     * is on the list; <code>false</code> otherwise.
     * @see ImageConsumer
     */
    public synchronized boolean isConsumer(ImageConsumer ic) {
        return theConsumers.contains(ic);
    }

    /**
     * Removes an ImageConsumer from the list of consumers interested in
     * data for this image.
     * <p>
     *  从对此图像的数据感兴趣的消费者列表中删除ImageConsumer。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @see ImageConsumer
     */
    public synchronized void removeConsumer(ImageConsumer ic) {
        theConsumers.removeElement(ic);
    }

    /**
     * Adds an ImageConsumer to the list of consumers interested in
     * data for this image and immediately starts delivery of the
     * image data through the ImageConsumer interface.
     * <p>
     *  将ImageConsumer添加到对此图像的数据感兴趣的消费者列表中,并立即通过ImageConsumer界面开始传递图像数据。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * image data through the ImageConsumer interface.
     * @see ImageConsumer
     */
    public void startProduction(ImageConsumer ic) {
        addConsumer(ic);
    }

    /**
     * Requests that a given ImageConsumer have the image data delivered
     * one more time in top-down, left-right order.
     * <p>
     * 请求给定的ImageConsumer具有以自上而下,左右顺序再次传送图像数据。
     * 
     * 
     * @param ic the specified <code>ImageConsumer</code>
     * @see ImageConsumer
     */
    public void requestTopDownLeftRightResend(ImageConsumer ic) {
        // Ignored.  The data is either single frame and already in TDLR
        // format or it is multi-frame and TDLR resends aren't critical.
    }

    /**
     * Changes this memory image into a multi-frame animation or a
     * single-frame static image depending on the animated parameter.
     * <p>This method should be called immediately after the
     * MemoryImageSource is constructed and before an image is
     * created with it to ensure that all ImageConsumers will
     * receive the correct multi-frame data.  If an ImageConsumer
     * is added to this ImageProducer before this flag is set then
     * that ImageConsumer will see only a snapshot of the pixel
     * data that was available when it connected.
     * <p>
     *  根据动画参数将此内存图像更改为多帧动画或单帧静态图像。
     *  <p>此方法应在构建MemoryImageSource之后并在使用它创建图像之前立即调用,以确保所有ImageConsumers都将接收到正确的多帧数据。
     * 如果在设置此标志之前将ImageConsumer添加到此ImageProducer,则ImageConsumer将仅查看连接时可用的像素数据的快照。
     * 
     * 
     * @param animated <code>true</code> if the image is a
     *       multi-frame animation
     */
    public synchronized void setAnimated(boolean animated) {
        this.animating = animated;
        if (!animating) {
            Enumeration enum_ = theConsumers.elements();
            while (enum_.hasMoreElements()) {
                ImageConsumer ic = (ImageConsumer) enum_.nextElement();
                ic.imageComplete(ImageConsumer.STATICIMAGEDONE);
                if (isConsumer(ic)) {
                    ic.imageComplete(ImageConsumer.IMAGEERROR);
                }
            }
            theConsumers.removeAllElements();
        }
    }

    /**
     * Specifies whether this animated memory image should always be
     * updated by sending the complete buffer of pixels whenever
     * there is a change.
     * This flag is ignored if the animation flag is not turned on
     * through the setAnimated() method.
     * <p>This method should be called immediately after the
     * MemoryImageSource is constructed and before an image is
     * created with it to ensure that all ImageConsumers will
     * receive the correct pixel delivery hints.
     * <p>
     *  指定是否应始终通过在发生更改时发送像素的完整缓冲区来更新此动画内存映像。如果动画标志未通过setAnimated()方法打开,则将忽略此标志。
     *  <p>此方法应在构建MemoryImageSource之后并在使用它创建图像之前立即调用,以确保所有ImageConsumers都将接收到正确的像素传递提示。
     * 
     * 
     * @param fullbuffers <code>true</code> if the complete pixel
     *             buffer should always
     * be sent
     * @see #setAnimated
     */
    public synchronized void setFullBufferUpdates(boolean fullbuffers) {
        if (this.fullbuffers == fullbuffers) {
            return;
        }
        this.fullbuffers = fullbuffers;
        if (animating) {
            Enumeration enum_ = theConsumers.elements();
            while (enum_.hasMoreElements()) {
                ImageConsumer ic = (ImageConsumer) enum_.nextElement();
                ic.setHints(fullbuffers
                            ? (ImageConsumer.TOPDOWNLEFTRIGHT |
                               ImageConsumer.COMPLETESCANLINES)
                            : ImageConsumer.RANDOMPIXELORDER);
            }
        }
    }

    /**
     * Sends a whole new buffer of pixels to any ImageConsumers that
     * are currently interested in the data for this image and notify
     * them that an animation frame is complete.
     * This method only has effect if the animation flag has been
     * turned on through the setAnimated() method.
     * <p>
     *  向当前对此图像的数据感兴趣的任何ImageConsum发送一个全新的像素缓冲区,并通知他们动画帧已完成。此方法仅在动画标志通过setAnimated()方法打开时有效。
     * 
     * 
     * @see #newPixels(int, int, int, int, boolean)
     * @see ImageConsumer
     * @see #setAnimated
     */
    public void newPixels() {
        newPixels(0, 0, width, height, true);
    }

    /**
     * Sends a rectangular region of the buffer of pixels to any
     * ImageConsumers that are currently interested in the data for
     * this image and notify them that an animation frame is complete.
     * This method only has effect if the animation flag has been
     * turned on through the setAnimated() method.
     * If the full buffer update flag was turned on with the
     * setFullBufferUpdates() method then the rectangle parameters
     * will be ignored and the entire buffer will always be sent.
     * <p>
     * 将像素缓冲区的矩形区域发送到当前对此图像的数据感兴趣的任何ImageConsumers,并通知它们动画帧已完成。此方法仅在动画标志通过setAnimated()方法打开时有效。
     * 如果使用setFullBufferUpdates()方法打开完全缓冲区更新标志,那么将忽略矩形参数,并且将始终发送整个缓冲区。
     * 
     * 
     * @param x the x coordinate of the upper left corner of the rectangle
     * of pixels to be sent
     * @param y the y coordinate of the upper left corner of the rectangle
     * of pixels to be sent
     * @param w the width of the rectangle of pixels to be sent
     * @param h the height of the rectangle of pixels to be sent
     * @see #newPixels(int, int, int, int, boolean)
     * @see ImageConsumer
     * @see #setAnimated
     * @see #setFullBufferUpdates
     */
    public synchronized void newPixels(int x, int y, int w, int h) {
        newPixels(x, y, w, h, true);
    }

    /**
     * Sends a rectangular region of the buffer of pixels to any
     * ImageConsumers that are currently interested in the data for
     * this image.
     * If the framenotify parameter is true then the consumers are
     * also notified that an animation frame is complete.
     * This method only has effect if the animation flag has been
     * turned on through the setAnimated() method.
     * If the full buffer update flag was turned on with the
     * setFullBufferUpdates() method then the rectangle parameters
     * will be ignored and the entire buffer will always be sent.
     * <p>
     *  将像素缓冲区的矩形区域发送到当前对此图像的数据感兴趣的任何ImageConsumers。如果framenotify参数为true,则还通知消费者动画帧完成。
     * 此方法仅在动画标志通过setAnimated()方法打开时有效。如果使用setFullBufferUpdates()方法打开完全缓冲区更新标志,那么将忽略矩形参数,并且将始终发送整个缓冲区。
     * 
     * 
     * @param x the x coordinate of the upper left corner of the rectangle
     * of pixels to be sent
     * @param y the y coordinate of the upper left corner of the rectangle
     * of pixels to be sent
     * @param w the width of the rectangle of pixels to be sent
     * @param h the height of the rectangle of pixels to be sent
     * @param framenotify <code>true</code> if the consumers should be sent a
     * {@link ImageConsumer#SINGLEFRAMEDONE SINGLEFRAMEDONE} notification
     * @see ImageConsumer
     * @see #setAnimated
     * @see #setFullBufferUpdates
     */
    public synchronized void newPixels(int x, int y, int w, int h,
                                       boolean framenotify) {
        if (animating) {
            if (fullbuffers) {
                x = y = 0;
                w = width;
                h = height;
            } else {
                if (x < 0) {
                    w += x;
                    x = 0;
                }
                if (x + w > width) {
                    w = width - x;
                }
                if (y < 0) {
                    h += y;
                    y = 0;
                }
                if (y + h > height) {
                    h = height - y;
                }
            }
            if ((w <= 0 || h <= 0) && !framenotify) {
                return;
            }
            Enumeration enum_ = theConsumers.elements();
            while (enum_.hasMoreElements()) {
                ImageConsumer ic = (ImageConsumer) enum_.nextElement();
                if (w > 0 && h > 0) {
                    sendPixels(ic, x, y, w, h);
                }
                if (framenotify && isConsumer(ic)) {
                    ic.imageComplete(ImageConsumer.SINGLEFRAMEDONE);
                }
            }
        }
    }

    /**
     * Changes to a new byte array to hold the pixels for this image.
     * If the animation flag has been turned on through the setAnimated()
     * method, then the new pixels will be immediately delivered to any
     * ImageConsumers that are currently interested in the data for
     * this image.
     * <p>
     *  更改为保存此图像的像素的新字节数组。如果动画标志已通过setAnimated()方法打开,则新的像素将立即传递到当前对此图像的数据感兴趣的任何ImageConsumers。
     * 
     * 
     * @param newpix the new pixel array
     * @param newmodel the specified <code>ColorModel</code>
     * @param offset the offset into the array
     * @param scansize the distance from one row of pixels to the next in
     * the array
     * @see #newPixels(int, int, int, int, boolean)
     * @see #setAnimated
     */
    public synchronized void newPixels(byte[] newpix, ColorModel newmodel,
                                       int offset, int scansize) {
        this.pixels = newpix;
        this.model = newmodel;
        this.pixeloffset = offset;
        this.pixelscan = scansize;
        newPixels();
    }

    /**
     * Changes to a new int array to hold the pixels for this image.
     * If the animation flag has been turned on through the setAnimated()
     * method, then the new pixels will be immediately delivered to any
     * ImageConsumers that are currently interested in the data for
     * this image.
     * <p>
     * 更改为保存此图像的像素的新int数组。如果动画标志已通过setAnimated()方法打开,则新的像素将立即传递到当前对此图像的数据感兴趣的任何ImageConsumers。
     * 
     * @param newpix the new pixel array
     * @param newmodel the specified <code>ColorModel</code>
     * @param offset the offset into the array
     * @param scansize the distance from one row of pixels to the next in
     * the array
     * @see #newPixels(int, int, int, int, boolean)
     * @see #setAnimated
     */
    public synchronized void newPixels(int[] newpix, ColorModel newmodel,
                                       int offset, int scansize) {
        this.pixels = newpix;
        this.model = newmodel;
        this.pixeloffset = offset;
        this.pixelscan = scansize;
        newPixels();
    }

    private void initConsumer(ImageConsumer ic) {
        if (isConsumer(ic)) {
            ic.setDimensions(width, height);
        }
        if (isConsumer(ic)) {
            ic.setProperties(properties);
        }
        if (isConsumer(ic)) {
            ic.setColorModel(model);
        }
        if (isConsumer(ic)) {
            ic.setHints(animating
                        ? (fullbuffers
                           ? (ImageConsumer.TOPDOWNLEFTRIGHT |
                              ImageConsumer.COMPLETESCANLINES)
                           : ImageConsumer.RANDOMPIXELORDER)
                        : (ImageConsumer.TOPDOWNLEFTRIGHT |
                           ImageConsumer.COMPLETESCANLINES |
                           ImageConsumer.SINGLEPASS |
                           ImageConsumer.SINGLEFRAME));
        }
    }

    private void sendPixels(ImageConsumer ic, int x, int y, int w, int h) {
        int off = pixeloffset + pixelscan * y + x;
        if (isConsumer(ic)) {
            if (pixels instanceof byte[]) {
                ic.setPixels(x, y, w, h, model,
                             ((byte[]) pixels), off, pixelscan);
            } else {
                ic.setPixels(x, y, w, h, model,
                             ((int[]) pixels), off, pixelscan);
            }
        }
    }
}
