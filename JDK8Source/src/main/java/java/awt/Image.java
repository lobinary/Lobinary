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
package java.awt;

import java.awt.image.ImageProducer;
import java.awt.image.ImageObserver;
import java.awt.image.ImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.ReplicateScaleFilter;

import sun.awt.image.SurfaceManager;


/**
 * The abstract class <code>Image</code> is the superclass of all
 * classes that represent graphical images. The image must be
 * obtained in a platform-specific manner.
 *
 * <p>
 *  抽象类<code> Image </code>是表示图形图像的所有类的超类。必须以平台特定的方式获取图像。
 * 
 * 
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @since       JDK1.0
 */
public abstract class Image {

    /**
     * convenience object; we can use this single static object for
     * all images that do not create their own image caps; it holds the
     * default (unaccelerated) properties.
     * <p>
     *  便利对象;我们可以对所有不创建自己的图像帽的图像使用这个单一的静态对象;它保存默认(未加速)属性。
     * 
     */
    private static ImageCapabilities defaultImageCaps =
        new ImageCapabilities(false);

    /**
     * Priority for accelerating this image.  Subclasses are free to
     * set different default priorities and applications are free to
     * set the priority for specific images via the
     * <code>setAccelerationPriority(float)</code> method.
     * <p>
     *  加速此图像的优先级。子类可以自由设置不同的默认优先级,应用程序可以通过<code> setAccelerationPriority(float)</code>方法自由设置特定映像的优先级。
     * 
     * 
     * @since 1.5
     */
    protected float accelerationPriority = .5f;

    /**
     * Determines the width of the image. If the width is not yet known,
     * this method returns <code>-1</code> and the specified
     * <code>ImageObserver</code> object is notified later.
     * <p>
     *  确定图像的宽度。如果宽度尚未知道,此方法返回<code> -1 </code>,稍后会通知指定的<code> ImageObserver </code>对象。
     * 
     * 
     * @param     observer   an object waiting for the image to be loaded.
     * @return    the width of this image, or <code>-1</code>
     *                   if the width is not yet known.
     * @see       java.awt.Image#getHeight
     * @see       java.awt.image.ImageObserver
     */
    public abstract int getWidth(ImageObserver observer);

    /**
     * Determines the height of the image. If the height is not yet known,
     * this method returns <code>-1</code> and the specified
     * <code>ImageObserver</code> object is notified later.
     * <p>
     *  确定图像的高度。如果高度尚未知道,此方法返回<code> -1 </code>,稍后会通知指定的<code> ImageObserver </code>对象。
     * 
     * 
     * @param     observer   an object waiting for the image to be loaded.
     * @return    the height of this image, or <code>-1</code>
     *                   if the height is not yet known.
     * @see       java.awt.Image#getWidth
     * @see       java.awt.image.ImageObserver
     */
    public abstract int getHeight(ImageObserver observer);

    /**
     * Gets the object that produces the pixels for the image.
     * This method is called by the image filtering classes and by
     * methods that perform image conversion and scaling.
     * <p>
     *  获取生成图像像素的对象。此方法由图像过滤类和执行图像转换和缩放的方法调用。
     * 
     * 
     * @return     the image producer that produces the pixels
     *                                  for this image.
     * @see        java.awt.image.ImageProducer
     */
    public abstract ImageProducer getSource();

    /**
     * Creates a graphics context for drawing to an off-screen image.
     * This method can only be called for off-screen images.
     * <p>
     *  创建用于绘制到屏幕外图像的图形上下文。此方法只能用于离屏图像。
     * 
     * 
     * @return  a graphics context to draw to the off-screen image.
     * @exception UnsupportedOperationException if called for a
     *            non-off-screen image.
     * @see     java.awt.Graphics
     * @see     java.awt.Component#createImage(int, int)
     */
    public abstract Graphics getGraphics();

    /**
     * Gets a property of this image by name.
     * <p>
     * Individual property names are defined by the various image
     * formats. If a property is not defined for a particular image, this
     * method returns the <code>UndefinedProperty</code> object.
     * <p>
     * If the properties for this image are not yet known, this method
     * returns <code>null</code>, and the <code>ImageObserver</code>
     * object is notified later.
     * <p>
     * The property name <code>"comment"</code> should be used to store
     * an optional comment which can be presented to the application as a
     * description of the image, its source, or its author.
     * <p>
     *  按名称获取此图片的属性。
     * <p>
     * 单个属性名称由各种图像格式定义。如果没有为特定映像定义属性,则此方法返回<code> UndefinedProperty </code>对象。
     * <p>
     *  如果此图像的属性尚未知晓,则此方法返回<code> null </code>,稍后将通知<code> ImageObserver </code>对象。
     * <p>
     *  属性名<code>"comment"</code>应用于存储可选注释,可以作为图像,其来源或其作者的描述呈现给应用程序。
     * 
     * 
     * @param       name   a property name.
     * @param       observer   an object waiting for this image to be loaded.
     * @return      the value of the named property.
     * @throws      NullPointerException if the property name is null.
     * @see         java.awt.image.ImageObserver
     * @see         java.awt.Image#UndefinedProperty
     */
    public abstract Object getProperty(String name, ImageObserver observer);

    /**
     * The <code>UndefinedProperty</code> object should be returned whenever a
     * property which was not defined for a particular image is fetched.
     * <p>
     *  获取未针对特定图像定义的属性时,应返回<code> UndefinedProperty </code>对象。
     * 
     */
    public static final Object UndefinedProperty = new Object();

    /**
     * Creates a scaled version of this image.
     * A new <code>Image</code> object is returned which will render
     * the image at the specified <code>width</code> and
     * <code>height</code> by default.  The new <code>Image</code> object
     * may be loaded asynchronously even if the original source image
     * has already been loaded completely.
     *
     * <p>
     *
     * If either <code>width</code>
     * or <code>height</code> is a negative number then a value is
     * substituted to maintain the aspect ratio of the original image
     * dimensions. If both <code>width</code> and <code>height</code>
     * are negative, then the original image dimensions are used.
     *
     * <p>
     *  创建此图像的缩放版本。
     * 默认情况下会返回一个新的<code> Image </code>对象,该对象将以指定的<code> width </code>和<code> height </code>新的<code> Image </code>
     * 对象可以异步加载,即使原始源映像已经完全加载。
     *  创建此图像的缩放版本。
     * 
     * <p>
     * 
     *  如果<code> width </code>或<code> height </code>为负数,则会替换值以保持原始图片尺寸的高宽比。
     * 如果<code> width </code>和<code> height </code>都为负,则使用原始图片尺寸。
     * 
     * 
     * @param width the width to which to scale the image.
     * @param height the height to which to scale the image.
     * @param hints flags to indicate the type of algorithm to use
     * for image resampling.
     * @return     a scaled version of the image.
     * @exception IllegalArgumentException if <code>width</code>
     *             or <code>height</code> is zero.
     * @see        java.awt.Image#SCALE_DEFAULT
     * @see        java.awt.Image#SCALE_FAST
     * @see        java.awt.Image#SCALE_SMOOTH
     * @see        java.awt.Image#SCALE_REPLICATE
     * @see        java.awt.Image#SCALE_AREA_AVERAGING
     * @since      JDK1.1
     */
    public Image getScaledInstance(int width, int height, int hints) {
        ImageFilter filter;
        if ((hints & (SCALE_SMOOTH | SCALE_AREA_AVERAGING)) != 0) {
            filter = new AreaAveragingScaleFilter(width, height);
        } else {
            filter = new ReplicateScaleFilter(width, height);
        }
        ImageProducer prod;
        prod = new FilteredImageSource(getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(prod);
    }

    /**
     * Use the default image-scaling algorithm.
     * <p>
     *  使用默认的图像缩放算法。
     * 
     * 
     * @since JDK1.1
     */
    public static final int SCALE_DEFAULT = 1;

    /**
     * Choose an image-scaling algorithm that gives higher priority
     * to scaling speed than smoothness of the scaled image.
     * <p>
     *  选择一种图像缩放算法,该缩放算法比缩放图像的平滑度优先缩放缩放速度。
     * 
     * 
     * @since JDK1.1
     */
    public static final int SCALE_FAST = 2;

    /**
     * Choose an image-scaling algorithm that gives higher priority
     * to image smoothness than scaling speed.
     * <p>
     * 选择一个图像缩放算法,它比图像平滑度优先于缩放速度。
     * 
     * 
     * @since JDK1.1
     */
    public static final int SCALE_SMOOTH = 4;

    /**
     * Use the image scaling algorithm embodied in the
     * <code>ReplicateScaleFilter</code> class.
     * The <code>Image</code> object is free to substitute a different filter
     * that performs the same algorithm yet integrates more efficiently
     * into the imaging infrastructure supplied by the toolkit.
     * <p>
     *  使用包含在<code> ReplicateScaleFilter </code>类中的图像缩放算法。
     *  <code> Image </code>对象可以免费替换执行相同算法的另一个过滤器,但可以更有效地集成到工具包提供的映像基础架构中。
     * 
     * 
     * @see        java.awt.image.ReplicateScaleFilter
     * @since      JDK1.1
     */
    public static final int SCALE_REPLICATE = 8;

    /**
     * Use the Area Averaging image scaling algorithm.  The
     * image object is free to substitute a different filter that
     * performs the same algorithm yet integrates more efficiently
     * into the image infrastructure supplied by the toolkit.
     * <p>
     *  使用区域平均图像缩放算法。图像对象可以自由地替换执行相同算法,但更有效地集成到工具包提供的图像基础设施的不同过滤器。
     * 
     * 
     * @see java.awt.image.AreaAveragingScaleFilter
     * @since JDK1.1
     */
    public static final int SCALE_AREA_AVERAGING = 16;

    /**
     * Flushes all reconstructable resources being used by this Image object.
     * This includes any pixel data that is being cached for rendering to
     * the screen as well as any system resources that are being used
     * to store data or pixels for the image if they can be recreated.
     * The image is reset to a state similar to when it was first created
     * so that if it is again rendered, the image data will have to be
     * recreated or fetched again from its source.
     * <p>
     * Examples of how this method affects specific types of Image object:
     * <ul>
     * <li>
     * BufferedImage objects leave the primary Raster which stores their
     * pixels untouched, but flush any information cached about those
     * pixels such as copies uploaded to the display hardware for
     * accelerated blits.
     * <li>
     * Image objects created by the Component methods which take a
     * width and height leave their primary buffer of pixels untouched,
     * but have all cached information released much like is done for
     * BufferedImage objects.
     * <li>
     * VolatileImage objects release all of their pixel resources
     * including their primary copy which is typically stored on
     * the display hardware where resources are scarce.
     * These objects can later be restored using their
     * {@link java.awt.image.VolatileImage#validate validate}
     * method.
     * <li>
     * Image objects created by the Toolkit and Component classes which are
     * loaded from files, URLs or produced by an {@link ImageProducer}
     * are unloaded and all local resources are released.
     * These objects can later be reloaded from their original source
     * as needed when they are rendered, just as when they were first
     * created.
     * </ul>
     * <p>
     *  刷新此Image对象正在使用的所有可重建资源。这包括正在缓存以呈现到屏幕的任何像素数据以及用于存储图像的数据或像素的任何系统资源(如果它们可以重新创建)。
     * 图像被重置为类似于其首次被创建时的状态,使得如果再次被渲染,则将必须从其源重新创建或获取图像数据。
     * <p>
     *  此方法如何影响特定类型的Image对象的示例：
     * <ul>
     * <li>
     *  BufferedImage对象离开主光栅,存储它们的像素不被触摸,但是刷新任何关于这些像素的信息,例如上传到显示硬件的拷贝以加速blit。
     * <li>
     * 由Component方法创建的图像对象,它们的宽度和高度使其主缓冲区的像素保持不变,但对BufferedImage对象完全释放所有缓存信息。
     * <li>
     *  VolatileImage对象释放所有的像素资源,包括它们的主要副本,通常存储在资源稀缺的显示硬件上。
     * 这些对象可以稍后使用其{@link java.awt.image.VolatileImage#validate validate}方法进行恢复。
     * <li>
     *  由Toolkit创建的映像对象和从{@link ImageProducer}生成的文件,URL加载的组件类将被卸载,并释放所有本地资源。
     * 这些对象稍后可以在渲染时根据需要从原始源重新加载,就像第一次创建时一样。
     */
    public void flush() {
        if (surfaceManager != null) {
            surfaceManager.flush();
        }
    }

    /**
     * Returns an ImageCapabilities object which can be
     * inquired as to the capabilities of this
     * Image on the specified GraphicsConfiguration.
     * This allows programmers to find
     * out more runtime information on the specific Image
     * object that they have created.  For example, the user
     * might create a BufferedImage but the system may have
     * no video memory left for creating an image of that
     * size on the given GraphicsConfiguration, so although the object
     * may be acceleratable in general, it
     * does not have that capability on this GraphicsConfiguration.
     * <p>
     * </ul>
     * 
     * @param gc a <code>GraphicsConfiguration</code> object.  A value of null
     * for this parameter will result in getting the image capabilities
     * for the default <code>GraphicsConfiguration</code>.
     * @return an <code>ImageCapabilities</code> object that contains
     * the capabilities of this <code>Image</code> on the specified
     * GraphicsConfiguration.
     * @see java.awt.image.VolatileImage#getCapabilities()
     * VolatileImage.getCapabilities()
     * @since 1.5
     */
    public ImageCapabilities getCapabilities(GraphicsConfiguration gc) {
        if (surfaceManager != null) {
            return surfaceManager.getCapabilities(gc);
        }
        // Note: this is just a default object that gets returned in the
        // absence of any more specific information from a surfaceManager.
        // Subclasses of Image should either override this method or
        // make sure that they always have a non-null SurfaceManager
        // to return an ImageCapabilities object that is appropriate
        // for their given subclass type.
        return defaultImageCaps;
    }

    /**
     * Sets a hint for this image about how important acceleration is.
     * This priority hint is used to compare to the priorities of other
     * Image objects when determining how to use scarce acceleration
     * resources such as video memory.  When and if it is possible to
     * accelerate this Image, if there are not enough resources available
     * to provide that acceleration but enough can be freed up by
     * de-accelerating some other image of lower priority, then that other
     * Image may be de-accelerated in deference to this one.  Images
     * that have the same priority take up resources on a first-come,
     * first-served basis.
     * <p>
     *  返回一个ImageCapabilities对象,可以在指定的GraphicsConfiguration上查询此Image的能力。这允许程序员在他们创建的特定Image对象上找到更多的运行时信息。
     * 例如,用户可以创建BufferedImage,但是系统可能没有视频存储器用于在给定GraphicsConfiguration上创建该大小的图像,因此尽管该对象通常可以是可加速的,但是它在该Graphic
     * sConfiguration上不具有该能力。
     *  返回一个ImageCapabilities对象,可以在指定的GraphicsConfiguration上查询此Image的能力。这允许程序员在他们创建的特定Image对象上找到更多的运行时信息。
     * 
     * 
     * @param priority a value between 0 and 1, inclusive, where higher
     * values indicate more importance for acceleration.  A value of 0
     * means that this Image should never be accelerated.  Other values
     * are used simply to determine acceleration priority relative to other
     * Images.
     * @throws IllegalArgumentException if <code>priority</code> is less
     * than zero or greater than 1.
     * @since 1.5
     */
    public void setAccelerationPriority(float priority) {
        if (priority < 0 || priority > 1) {
            throw new IllegalArgumentException("Priority must be a value " +
                                               "between 0 and 1, inclusive");
        }
        accelerationPriority = priority;
        if (surfaceManager != null) {
            surfaceManager.setAccelerationPriority(accelerationPriority);
        }
    }

    /**
     * Returns the current value of the acceleration priority hint.
     * <p>
     * 为此图像设置关于加速度有多重要的提示。当确定如何使用诸如视频存储器的稀缺加速资源时,此优先级提示用于与其他Image对象的优先级进行比较。
     * 当可以加速该图像时,如果没有足够的可用资源来提供该加速度,但是足够的资源可以通过对具有较低优先级的一些其他图像进行加速而被释放,则该另一图像可以被减速到这一个。
     * 具有相同优先级的映像以先来先服务的方式占用资源。
     * 
     * 
     * @see #setAccelerationPriority(float priority) setAccelerationPriority
     * @return value between 0 and 1, inclusive, which represents the current
     * priority value
     * @since 1.5
     */
    public float getAccelerationPriority() {
        return accelerationPriority;
    }

    SurfaceManager surfaceManager;

    static {
        SurfaceManager.setImageAccessor(new SurfaceManager.ImageAccessor() {
            public SurfaceManager getSurfaceManager(Image img) {
                return img.surfaceManager;
            }
            public void setSurfaceManager(Image img, SurfaceManager mgr) {
                img.surfaceManager = mgr;
            }
        });
    }
}
