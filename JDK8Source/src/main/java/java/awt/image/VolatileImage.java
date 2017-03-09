/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Toolkit;
import java.awt.Transparency;

/**
 * VolatileImage is an image which can lose its
 * contents at any time due to circumstances beyond the control of the
 * application (e.g., situations caused by the operating system or by
 * other applications). Because of the potential for hardware acceleration,
 * a VolatileImage object can have significant performance benefits on
 * some platforms.
 * <p>
 * The drawing surface of an image (the memory where the image contents
 * actually reside) can be lost or invalidated, causing the contents of that
 * memory to go away.  The drawing surface thus needs to be restored
 * or recreated and the contents of that surface need to be
 * re-rendered.  VolatileImage provides an interface for
 * allowing the user to detect these problems and fix them
 * when they occur.
 * <p>
 * When a VolatileImage object is created, limited system resources
 * such as video memory (VRAM) may be allocated in order to support
 * the image.
 * When a VolatileImage object is no longer used, it may be
 * garbage-collected and those system resources will be returned,
 * but this process does not happen at guaranteed times.
 * Applications that create many VolatileImage objects (for example,
 * a resizing window may force recreation of its back buffer as the
 * size changes) may run out of optimal system resources for new
 * VolatileImage objects simply because the old objects have not
 * yet been removed from the system.
 * (New VolatileImage objects may still be created, but they
 * may not perform as well as those created in accelerated
 * memory).
 * The flush method may be called at any time to proactively release
 * the resources used by a VolatileImage so that it does not prevent
 * subsequent VolatileImage objects from being accelerated.
 * In this way, applications can have more control over the state
 * of the resources taken up by obsolete VolatileImage objects.
 * <p>
 * This image should not be subclassed directly but should be created
 * by using the {@link java.awt.Component#createVolatileImage(int, int)
 * Component.createVolatileImage} or
 * {@link java.awt.GraphicsConfiguration#createCompatibleVolatileImage(int, int)
 * GraphicsConfiguration.createCompatibleVolatileImage(int, int)} methods.
 * <P>
 * An example of using a VolatileImage object follows:
 * <pre>
 * // image creation
 * VolatileImage vImg = createVolatileImage(w, h);
 *
 *
 * // rendering to the image
 * void renderOffscreen() {
 *      do {
 *          if (vImg.validate(getGraphicsConfiguration()) ==
 *              VolatileImage.IMAGE_INCOMPATIBLE)
 *          {
 *              // old vImg doesn't work with new GraphicsConfig; re-create it
 *              vImg = createVolatileImage(w, h);
 *          }
 *          Graphics2D g = vImg.createGraphics();
 *          //
 *          // miscellaneous rendering commands...
 *          //
 *          g.dispose();
 *      } while (vImg.contentsLost());
 * }
 *
 *
 * // copying from the image (here, gScreen is the Graphics
 * // object for the onscreen window)
 * do {
 *      int returnCode = vImg.validate(getGraphicsConfiguration());
 *      if (returnCode == VolatileImage.IMAGE_RESTORED) {
 *          // Contents need to be restored
 *          renderOffscreen();      // restore contents
 *      } else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
 *          // old vImg doesn't work with new GraphicsConfig; re-create it
 *          vImg = createVolatileImage(w, h);
 *          renderOffscreen();
 *      }
 *      gScreen.drawImage(vImg, 0, 0, this);
 * } while (vImg.contentsLost());
 * </pre>
 * <P>
 * Note that this class subclasses from the {@link Image} class, which
 * includes methods that take an {@link ImageObserver} parameter for
 * asynchronous notifications as information is received from
 * a potential {@link ImageProducer}.  Since this <code>VolatileImage</code>
 * is not loaded from an asynchronous source, the various methods that take
 * an <code>ImageObserver</code> parameter will behave as if the data has
 * already been obtained from the <code>ImageProducer</code>.
 * Specifically, this means that the return values from such methods
 * will never indicate that the information is not yet available and
 * the <code>ImageObserver</code> used in such methods will never
 * need to be recorded for an asynchronous callback notification.
 * <p>
 *  VolatileImage是由于超出应用程序控制的情况(例如,由操作系统或其他应用程序引起的情况)而在任何时候丢失其内容的图像。
 * 由于硬件加速的潜力,VolatileImage对象在某些平台上可以获得显着的性能优势。
 * <p>
 *  图像(图像内容实际存在的存储器)的绘图表面可能丢失或失效,导致该存储器的内容消失。因此,绘图表面需要被恢复或重新创建,并且该表面的内容需要被重新渲染。
 *  VolatileImage提供了一个接口,允许用户检测这些问题,并在发生时进行修复。
 * <p>
 * 当创建VolatileImage对象时,可以分配有限的系统资源,例如视频存储器(VRAM),以便支持图像。
 * 当VolatileImage对象不再使用时,它可能被垃圾回收,并且将返回那些系统资源,但是这个过程不会在保证时间发生。
 * 创建许多VolatileImage对象的应用程序(例如,调整大小窗口可能会随着大小的改变而强制重新调整其后台缓冲区)可能会因为尚未从系统中删除旧对象而导致用于新VolatileImage对象的最佳系统资
 * 源用完。
 * 当VolatileImage对象不再使用时,它可能被垃圾回收,并且将返回那些系统资源,但是这个过程不会在保证时间发生。
 *  (仍然可以创建新的VolatileImage对象,但它们的性能可能不如在加速内存中创建的那么好)。
 * 可以在任何时间调用flush方法以主动释放由VolatileImage使用的资源,使得它不会阻止随后的VolatileImage对象被加速。
 * 这样,应用程序可以更好地控制由过时VolatileImage对象占用的资源的状态。
 * <p>
 *  此图片不应直接子类化,而应使用{@link java.awt.Component#createVolatileImage(int,int)Component.createVolatileImage}或
 * {@link java.awt.GraphicsConfiguration#createCompatibleVolatileImage(int,int)GraphicsConfiguration .createCompatibleVolatileImage(int,int)}
 * 方法。
 * <P>
 *  以下是使用VolatileImage对象的示例：
 * <pre>
 *  // image creation VolatileImage vImg = createVolatileImage(w,h);
 * 
 * //渲染到图像void renderOffscreen(){do {if(vImg.validate(getGraphicsConfiguration())== VolatileImage.IMAGE_INCOMPATIBLE){//旧vImg不能使用新的GraphicsConfig;重新创建它vImg = createVolatileImage(w,h); }
 *  Graphics2D g = vImg.createGraphics(); // //各种渲染命令... // g.dispose(); } while(vImg.contentsLost()); }
 * }。
 * 
 *  //从图像复制(这里,gScreen是屏幕窗口的图形//对象)do {int returnCode = vImg.validate(getGraphicsConfiguration()); if(returnCode == VolatileImage.IMAGE_RESTORED){//内容需要恢复renderOffscreen(); // restore contents}
 *  else if(returnCode == VolatileImage.IMAGE_INCOMPATIBLE){//旧的vImg不支持新的GraphicsConfig;重新创建它vImg = createVolatileImage(w,h); renderOffscreen(); }
 *  gScreen.drawImage(vImg,0,0,this); } while(vImg.contentsLost());。
 * </pre>
 * <P>
 * 请注意,此类从{@link Image}类中提供了子类,其中包含从异步通知获取{@link ImageObserver}参数的方法,因为从潜在的{@link ImageProducer}接收到信息。
 * 由于这个<code> VolatileImage </code>不是从异步源加载的,因此采用<code> ImageObserver </code>参数的各种方法将表现得好像数据已从<code> Ima
 * geProducer < / code>。
 * 请注意,此类从{@link Image}类中提供了子类,其中包含从异步通知获取{@link ImageObserver}参数的方法,因为从潜在的{@link ImageProducer}接收到信息。
 * 具体来说,这意味着来自这些方法的返回值将永远不会指示信息尚不可用,并且在这些方法中使用的<code> ImageObserver </code>将永远不需要记录异步回调通知。
 * 
 * 
 * @since 1.4
 */
public abstract class VolatileImage extends Image implements Transparency
{

    // Return codes for validate() method

    /**
     * Validated image is ready to use as-is.
     * <p>
     *  验证的图像可以直接使用。
     * 
     */
    public static final int IMAGE_OK = 0;

    /**
     * Validated image has been restored and is now ready to use.
     * Note that restoration causes contents of the image to be lost.
     * <p>
     *  已验证的图片已恢复,现在可以使用。注意,恢复导致图像的内容丢失。
     * 
     */
    public static final int IMAGE_RESTORED = 1;

    /**
     * Validated image is incompatible with supplied
     * <code>GraphicsConfiguration</code> object and should be
     * re-created as appropriate.  Usage of the image as-is
     * after receiving this return code from <code>validate</code>
     * is undefined.
     * <p>
     *  验证的图像与提供的<code> GraphicsConfiguration </code>对象不兼容,应该根据需要重新创建。
     * 从<code> validate </code>接收到此返回码之后,图像的使用未定义。
     * 
     */
    public static final int IMAGE_INCOMPATIBLE = 2;

    /**
     * Returns a static snapshot image of this object.  The
     * <code>BufferedImage</code> returned is only current with
     * the <code>VolatileImage</code> at the time of the request
     * and will not be updated with any future changes to the
     * <code>VolatileImage</code>.
     * <p>
     *  返回此对象的静态快照图像。
     * 在请求时返回的<code> BufferedImage </code>只与当前的<code> VolatileImage </code>匹配,并且不会随着对<code> VolatileImage </code>
     * 的任何更改而更新。
     *  返回此对象的静态快照图像。
     * 
     * 
     * @return a {@link BufferedImage} representation of this
     *          <code>VolatileImage</code>
     * @see BufferedImage
     */
    public abstract BufferedImage getSnapshot();

    /**
     * Returns the width of the <code>VolatileImage</code>.
     * <p>
     *  返回<code> VolatileImage </code>的宽度。
     * 
     * 
     * @return the width of this <code>VolatileImage</code>.
     */
    public abstract int getWidth();

    /**
     * Returns the height of the <code>VolatileImage</code>.
     * <p>
     *  返回<code> VolatileImage </code>的高度。
     * 
     * 
     * @return the height of this <code>VolatileImage</code>.
     */
    public abstract int getHeight();

    // Image overrides

    /**
     * This returns an ImageProducer for this VolatileImage.
     * Note that the VolatileImage object is optimized for
     * rendering operations and blitting to the screen or other
     * VolatileImage objects, as opposed to reading back the
     * pixels of the image.  Therefore, operations such as
     * <code>getSource</code> may not perform as fast as
     * operations that do not rely on reading the pixels.
     * Note also that the pixel values read from the image are current
     * with those in the image only at the time that they are
     * retrieved. This method takes a snapshot
     * of the image at the time the request is made and the
     * ImageProducer object returned works with
     * that static snapshot image, not the original VolatileImage.
     * Calling getSource()
     * is equivalent to calling getSnapshot().getSource().
     * <p>
     * 这将返回此VolatileImage的ImageProducer。
     * 请注意,VolatileImage对象被优化用于渲染操作和blitting到屏幕或其他VolatileImage对象,而不是读回图像的像素。
     * 因此,诸如<code> getSource </code>之类的操作的执行速度可能不如不依赖于读取像素的操作那么快。还要注意,从图像中读取的像素值只有在检索图像时才与当前图像中的像素值一致。
     * 此方法在发出请求时获取映像的快照,并且返回的ImageProducer对象使用该静态快照映像而不是原始的VolatileImage。调用getSource()等效于调用getSnapshot()。
     * getSource()。
     * 
     * 
     * @return an {@link ImageProducer} that can be used to produce the
     * pixels for a <code>BufferedImage</code> representation of
     * this Image.
     * @see ImageProducer
     * @see #getSnapshot()
     */
    public ImageProducer getSource() {
        // REMIND: Make sure this functionality is in line with the
        // spec.  In particular, we are returning the Source for a
        // static image (the snapshot), not a changing image (the
        // VolatileImage).  So if the user expects the Source to be
        // up-to-date with the current contents of the VolatileImage,
        // they will be disappointed...
        // REMIND: This assumes that getSnapshot() returns something
        // valid and not the default null object returned by this class
        // (so it assumes that the actual VolatileImage object is
        // subclassed off something that does the right thing
        // (e.g., SunVolatileImage).
        return getSnapshot().getSource();
    }

    // REMIND: if we want any decent performance for getScaledInstance(),
    // we should override the Image implementation of it...

    /**
     * This method returns a {@link Graphics2D}, but is here
     * for backwards compatibility.  {@link #createGraphics() createGraphics} is more
     * convenient, since it is declared to return a
     * <code>Graphics2D</code>.
     * <p>
     *  此方法返回一个{@link Graphics2D},但这里是为了向后兼容性。
     *  {@link #createGraphics()createGraphics}更方便,因为它被声明为返回一个<code> Graphics2D </code>。
     * 
     * 
     * @return a <code>Graphics2D</code>, which can be used to draw into
     *          this image.
     */
    public Graphics getGraphics() {
        return createGraphics();
    }

    /**
     * Creates a <code>Graphics2D</code>, which can be used to draw into
     * this <code>VolatileImage</code>.
     * <p>
     *  创建一个<code> Graphics2D </code>,它可以用来绘制这个<code> VolatileImage </code>。
     * 
     * 
     * @return a <code>Graphics2D</code>, used for drawing into this
     *          image.
     */
    public abstract Graphics2D createGraphics();


    // Volatile management methods

    /**
     * Attempts to restore the drawing surface of the image if the surface
     * had been lost since the last <code>validate</code> call.  Also
     * validates this image against the given GraphicsConfiguration
     * parameter to see whether operations from this image to the
     * GraphicsConfiguration are compatible.  An example of an
     * incompatible combination might be a situation where a VolatileImage
     * object was created on one graphics device and then was used
     * to render to a different graphics device.  Since VolatileImage
     * objects tend to be very device-specific, this operation might
     * not work as intended, so the return code from this validate
     * call would note that incompatibility.  A null or incorrect
     * value for gc may cause incorrect values to be returned from
     * <code>validate</code> and may cause later problems with rendering.
     *
     * <p>
     * 如果表面自上次<code>验证</code>调用后丢失,尝试恢复图像的绘图表面。
     * 还根据给定的GraphicsConfiguration参数验证此映像,以查看从此映像到GraphicsConfiguration的操作是否兼容。
     * 不兼容组合的示例可能是这样的情况,其中VolatileImage对象在一个图形设备上创建,然后被用于呈现到不同的图形设备。
     * 由于VolatileImage对象往往是特定于设备的,所以此操作可能无法按预期工作,因此来自此验证调用的返回代码会注意到不兼容。
     *  gc的空值或不正确的值可能会导致从<code> validate </code>返回不正确的值,并可能导致稍后的呈现问题。
     * 
     * 
     * @param   gc   a <code>GraphicsConfiguration</code> object for this
     *          image to be validated against.  A null gc implies that the
     *          validate method should skip the compatibility test.
     * @return  <code>IMAGE_OK</code> if the image did not need validation<BR>
     *          <code>IMAGE_RESTORED</code> if the image needed restoration.
     *          Restoration implies that the contents of the image may have
     *          been affected and the image may need to be re-rendered.<BR>
     *          <code>IMAGE_INCOMPATIBLE</code> if the image is incompatible
     *          with the <code>GraphicsConfiguration</code> object passed
     *          into the <code>validate</code> method.  Incompatibility
     *          implies that the image may need to be recreated with a
     *          new <code>Component</code> or
     *          <code>GraphicsConfiguration</code> in order to get an image
     *          that can be used successfully with this
     *          <code>GraphicsConfiguration</code>.
     *          An incompatible image is not checked for whether restoration
     *          was necessary, so the state of the image is unchanged
     *          after a return value of <code>IMAGE_INCOMPATIBLE</code>
     *          and this return value implies nothing about whether the
     *          image needs to be restored.
     * @see java.awt.GraphicsConfiguration
     * @see java.awt.Component
     * @see #IMAGE_OK
     * @see #IMAGE_RESTORED
     * @see #IMAGE_INCOMPATIBLE
     */
    public abstract int validate(GraphicsConfiguration gc);

    /**
     * Returns <code>true</code> if rendering data was lost since last
     * <code>validate</code> call.  This method should be called by the
     * application at the end of any series of rendering operations to
     * or from the image to see whether
     * the image needs to be validated and the rendering redone.
     * <p>
     *  如果渲染数据自上次<code> validate </code>调用后丢失,则返回<code> true </code>。
     * 应用程序应在图像的任何系列渲染操作结束时调用此方法,以查看图像是否需要验证和渲染重做。
     * 
     * 
     * @return <code>true</code> if the drawing surface needs to be restored;
     * <code>false</code> otherwise.
     */
    public abstract boolean contentsLost();

    /**
     * Returns an ImageCapabilities object which can be
     * inquired as to the specific capabilities of this
     * VolatileImage.  This would allow programmers to find
     * out more runtime information on the specific VolatileImage
     * object that they have created.  For example, the user
     * might create a VolatileImage but the system may have
     * no video memory left for creating an image of that
     * size, so although the object is a VolatileImage, it is
     * not as accelerated as other VolatileImage objects on
     * this platform might be.  The user might want that
     * information to find other solutions to their problem.
     * <p>
     * 返回一个ImageCapabilities对象,可以查询该VolatileImage的特定功能。这将允许程序员在他们创建的特定VolatileImage对象上找到更多的运行时信息。
     * 例如,用户可以创建VolatileImage,但是系统可能没有用于创建该大小的映像的视频存储器,因此尽管该对象是VolatileImage,但是其并不比该平台上的其他VolatileImage对象加速。
     * 返回一个ImageCapabilities对象,可以查询该VolatileImage的特定功能。这将允许程序员在他们创建的特定VolatileImage对象上找到更多的运行时信息。
     * 用户可能希望该信息找到他们的问题的其他解决方案。
     * 
     * 
     * @return an <code>ImageCapabilities</code> object that contains
     *         the capabilities of this <code>VolatileImage</code>.
     * @since 1.4
     */
    public abstract ImageCapabilities getCapabilities();

    /**
     * The transparency value with which this image was created.
     * <p>
     *  创建此图像的透明度值。
     * 
     * 
     * @see java.awt.GraphicsConfiguration#createCompatibleVolatileImage(int,
     *      int,int)
     * @see java.awt.GraphicsConfiguration#createCompatibleVolatileImage(int,
     *      int,ImageCapabilities,int)
     * @see Transparency
     * @since 1.5
     */
    protected int transparency = TRANSLUCENT;

    /**
     * Returns the transparency.  Returns either OPAQUE, BITMASK,
     * or TRANSLUCENT.
     * <p>
     *  返回透明度。返回OPAQUE,BITMASK或TRANSLUCENT。
     * 
     * @return the transparency of this <code>VolatileImage</code>.
     * @see Transparency#OPAQUE
     * @see Transparency#BITMASK
     * @see Transparency#TRANSLUCENT
     * @since 1.5
     */
    public int getTransparency() {
        return transparency;
    }
}
