/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.VolatileImage;
import java.awt.image.WritableRaster;

import sun.awt.image.SunVolatileImage;

/**
 * The <code>GraphicsConfiguration</code> class describes the
 * characteristics of a graphics destination such as a printer or monitor.
 * There can be many <code>GraphicsConfiguration</code> objects associated
 * with a single graphics device, representing different drawing modes or
 * capabilities.  The corresponding native structure will vary from platform
 * to platform.  For example, on X11 windowing systems,
 * each visual is a different <code>GraphicsConfiguration</code>.
 * On Microsoft Windows, <code>GraphicsConfiguration</code>s represent
 * PixelFormats available in the current resolution and color depth.
 * <p>
 * In a virtual device multi-screen environment in which the desktop
 * area could span multiple physical screen devices, the bounds of the
 * <code>GraphicsConfiguration</code> objects are relative to the
 * virtual coordinate system.  When setting the location of a
 * component, use {@link #getBounds() getBounds} to get the bounds of
 * the desired <code>GraphicsConfiguration</code> and offset the location
 * with the coordinates of the <code>GraphicsConfiguration</code>,
 * as the following code sample illustrates:
 * </p>
 *
 * <pre>
 *      Frame f = new Frame(gc);  // where gc is a GraphicsConfiguration
 *      Rectangle bounds = gc.getBounds();
 *      f.setLocation(10 + bounds.x, 10 + bounds.y); </pre>
 *
 * <p>
 * To determine if your environment is a virtual device
 * environment, call <code>getBounds</code> on all of the
 * <code>GraphicsConfiguration</code> objects in your system.  If
 * any of the origins of the returned bounds is not (0,&nbsp;0),
 * your environment is a virtual device environment.
 *
 * <p>
 * You can also use <code>getBounds</code> to determine the bounds
 * of the virtual device.  To do this, first call <code>getBounds</code> on all
 * of the <code>GraphicsConfiguration</code> objects in your
 * system.  Then calculate the union of all of the bounds returned
 * from the calls to <code>getBounds</code>.  The union is the
 * bounds of the virtual device.  The following code sample
 * calculates the bounds of the virtual device.
 *
 * <pre>{@code
 *      Rectangle virtualBounds = new Rectangle();
 *      GraphicsEnvironment ge = GraphicsEnvironment.
 *              getLocalGraphicsEnvironment();
 *      GraphicsDevice[] gs =
 *              ge.getScreenDevices();
 *      for (int j = 0; j < gs.length; j++) {
 *          GraphicsDevice gd = gs[j];
 *          GraphicsConfiguration[] gc =
 *              gd.getConfigurations();
 *          for (int i=0; i < gc.length; i++) {
 *              virtualBounds =
 *                  virtualBounds.union(gc[i].getBounds());
 *          }
 *      } }</pre>
 *
 * <p>
 *  <code> GraphicsConfiguration </code>类描述了图形目标(如打印机或监视器)的特性。
 * 可以有与单个图形设备相关联的许多<code> GraphicsConfiguration </code>对象,表示不同的绘制模式或能力。相应的本机结构将随平台而变化。
 * 例如,在X11窗口系统上,每个视觉是不同的<code> GraphicsConfiguration </code>。
 * 在Microsoft Windows上,<code> GraphicsConfiguration </code>表示当前分辨率和颜色深度中可用的PixelFormats。
 * <p>
 *  在其中桌面区域可以跨越多个物理屏幕设备的虚拟设备多屏幕环境中,<code> GraphicsConfiguration </code>对象的边界相对于虚拟坐标系。
 * 当设置组件的位置时,使用{@link #getBounds()getBounds}来获取所需<code> GraphicsConfiguration </code>的边界,并使用<code> Graph
 * icsConfiguration </code> ,如下面的代码示例所示：。
 *  在其中桌面区域可以跨越多个物理屏幕设备的虚拟设备多屏幕环境中,<code> GraphicsConfiguration </code>对象的边界相对于虚拟坐标系。
 * </p>
 * 
 * <pre>
 *  帧f =新帧(gc); //其中gc是GraphicsConfiguration Rectangle bounds = gc.getBounds(); f.setLocation(10 + bound
 * s.x,10 + bounds.y); </pre>。
 * 
 * <p>
 * 要确定您的环境是否是虚拟设备环境,请在系统中的所有<code> GraphicsConfiguration </code>对象上调用<code> getBounds </code>。
 * 如果返回的边界的任何起点不是(0,&nbsp; 0),则您的环境是虚拟设备环境。
 * 
 * <p>
 *  您还可以使用<code> getBounds </code>来确定虚拟设备的边界。
 * 为此,首先在系统中的所有<code> GraphicsConfiguration </code>对象上调用<code> getBounds </code>。
 * 然后计算从调用<code> getBounds </code>返回的所有边界的并集。联合是虚拟设备的边界。以下代码示例计算虚拟设备的边界。
 * 
 *  <pre> {@ code Rectangle virtualBounds = new Rectangle(); GraphicsEnvironment ge = GraphicsEnvironment。
 *  getLocalGraphicsEnvironment(); GraphicsDevice [] gs = ge.getScreenDevices(); for(int j = 0; j <gs.length; j ++){GraphicsDevice gd = gs [j]; GraphicsConfiguration [] gc = gd.getConfigurations(); for(int i = 0; i <gc.length; i ++){virtualBounds = virtualBounds.union(gc [i] .getBounds()); }}} </pre>
 * 。
 * 
 * 
 * @see Window
 * @see Frame
 * @see GraphicsEnvironment
 * @see GraphicsDevice
 */
/*
 * REMIND:  What to do about capabilities?
 * The
 * capabilities of the device can be determined by enumerating the possible
 * capabilities and checking if the GraphicsConfiguration
 * implements the interface for that capability.
 *
 * <p>
 *  提醒：对能力做什么?可以通过枚举可能的能力并检查GraphicsConfiguration是否实现该能力的接口来确定设备的能力。
 * 
 */


public abstract class GraphicsConfiguration {

    private static BufferCapabilities defaultBufferCaps;
    private static ImageCapabilities defaultImageCaps;

    /**
     * This is an abstract class that cannot be instantiated directly.
     * Instances must be obtained from a suitable factory or query method.
     *
     * <p>
     *  这是一个不能直接实例化的抽象类。实例必须从适当的工厂或查询方法获取。
     * 
     * 
     * @see GraphicsDevice#getConfigurations
     * @see GraphicsDevice#getDefaultConfiguration
     * @see GraphicsDevice#getBestConfiguration
     * @see Graphics2D#getDeviceConfiguration
     */
    protected GraphicsConfiguration() {
    }

    /**
     * Returns the {@link GraphicsDevice} associated with this
     * <code>GraphicsConfiguration</code>.
     * <p>
     * 返回与此<code> GraphicsConfiguration </code>关联的{@link GraphicsDevice}。
     * 
     * 
     * @return a <code>GraphicsDevice</code> object that is
     * associated with this <code>GraphicsConfiguration</code>.
     */
    public abstract GraphicsDevice getDevice();

    /**
     * Returns a {@link BufferedImage} with a data layout and color model
     * compatible with this <code>GraphicsConfiguration</code>.  This
     * method has nothing to do with memory-mapping
     * a device.  The returned <code>BufferedImage</code> has
     * a layout and color model that is closest to this native device
     * configuration and can therefore be optimally blitted to this
     * device.
     * <p>
     *  返回具有与此<code> GraphicsConfiguration </code>兼容的数据布局和颜色模型的{@link BufferedImage}。这种方法与内存映射设备无关。
     * 返回的<code> BufferedImage </code>具有最接近此本机设备配置的布局和颜色模型,因此可以最佳地布局到此设备。
     * 
     * 
     * @param width the width of the returned <code>BufferedImage</code>
     * @param height the height of the returned <code>BufferedImage</code>
     * @return a <code>BufferedImage</code> whose data layout and color
     * model is compatible with this <code>GraphicsConfiguration</code>.
     */
    public BufferedImage createCompatibleImage(int width, int height) {
        ColorModel model = getColorModel();
        WritableRaster raster =
            model.createCompatibleWritableRaster(width, height);
        return new BufferedImage(model, raster,
                                 model.isAlphaPremultiplied(), null);
    }

    /**
     * Returns a <code>BufferedImage</code> that supports the specified
     * transparency and has a data layout and color model
     * compatible with this <code>GraphicsConfiguration</code>.  This
     * method has nothing to do with memory-mapping
     * a device. The returned <code>BufferedImage</code> has a layout and
     * color model that can be optimally blitted to a device
     * with this <code>GraphicsConfiguration</code>.
     * <p>
     *  返回一个支持指定透明度并且具有与此<code> GraphicsConfiguration </code>兼容的数据布局和颜色模型的<code> BufferedImage </code>。
     * 这种方法与内存映射设备无关。
     * 返回的<code> BufferedImage </code>有一个布局和颜色模型,可以通过这个<code> GraphicsConfiguration </code>最佳地布局到一个设备。
     * 
     * 
     * @param width the width of the returned <code>BufferedImage</code>
     * @param height the height of the returned <code>BufferedImage</code>
     * @param transparency the specified transparency mode
     * @return a <code>BufferedImage</code> whose data layout and color
     * model is compatible with this <code>GraphicsConfiguration</code>
     * and also supports the specified transparency.
     * @throws IllegalArgumentException if the transparency is not a valid value
     * @see Transparency#OPAQUE
     * @see Transparency#BITMASK
     * @see Transparency#TRANSLUCENT
     */
    public BufferedImage createCompatibleImage(int width, int height,
                                               int transparency)
    {
        if (getColorModel().getTransparency() == transparency) {
            return createCompatibleImage(width, height);
        }

        ColorModel cm = getColorModel(transparency);
        if (cm == null) {
            throw new IllegalArgumentException("Unknown transparency: " +
                                               transparency);
        }
        WritableRaster wr = cm.createCompatibleWritableRaster(width, height);
        return new BufferedImage(cm, wr, cm.isAlphaPremultiplied(), null);
    }


    /**
     * Returns a {@link VolatileImage} with a data layout and color model
     * compatible with this <code>GraphicsConfiguration</code>.
     * The returned <code>VolatileImage</code>
     * may have data that is stored optimally for the underlying graphics
     * device and may therefore benefit from platform-specific rendering
     * acceleration.
     * <p>
     *  返回具有与此<code> GraphicsConfiguration </code>兼容的数据布局和颜色模型的{@link VolatileImage}。
     * 返回的<code> VolatileImage </code>可以具有为底层图形设备最佳存储的数据,因此可以受益于平台特定的呈现加速。
     * 
     * 
     * @param width the width of the returned <code>VolatileImage</code>
     * @param height the height of the returned <code>VolatileImage</code>
     * @return a <code>VolatileImage</code> whose data layout and color
     * model is compatible with this <code>GraphicsConfiguration</code>.
     * @see Component#createVolatileImage(int, int)
     * @since 1.4
     */
    public VolatileImage createCompatibleVolatileImage(int width, int height) {
        VolatileImage vi = null;
        try {
            vi = createCompatibleVolatileImage(width, height,
                                               null, Transparency.OPAQUE);
        } catch (AWTException e) {
            // shouldn't happen: we're passing in null caps
            assert false;
        }
        return vi;
    }

    /**
     * Returns a {@link VolatileImage} with a data layout and color model
     * compatible with this <code>GraphicsConfiguration</code>.
     * The returned <code>VolatileImage</code>
     * may have data that is stored optimally for the underlying graphics
     * device and may therefore benefit from platform-specific rendering
     * acceleration.
     * <p>
     *  返回具有与此<code> GraphicsConfiguration </code>兼容的数据布局和颜色模型的{@link VolatileImage}。
     * 返回的<code> VolatileImage </code>可以具有为底层图形设备最佳存储的数据,因此可以受益于平台特定的呈现加速。
     * 
     * 
     * @param width the width of the returned <code>VolatileImage</code>
     * @param height the height of the returned <code>VolatileImage</code>
     * @param transparency the specified transparency mode
     * @return a <code>VolatileImage</code> whose data layout and color
     * model is compatible with this <code>GraphicsConfiguration</code>.
     * @throws IllegalArgumentException if the transparency is not a valid value
     * @see Transparency#OPAQUE
     * @see Transparency#BITMASK
     * @see Transparency#TRANSLUCENT
     * @see Component#createVolatileImage(int, int)
     * @since 1.5
     */
    public VolatileImage createCompatibleVolatileImage(int width, int height,
                                                       int transparency)
    {
        VolatileImage vi = null;
        try {
            vi = createCompatibleVolatileImage(width, height, null, transparency);
        } catch (AWTException e) {
            // shouldn't happen: we're passing in null caps
            assert false;
        }
        return vi;
    }

    /**
     * Returns a {@link VolatileImage} with a data layout and color model
     * compatible with this <code>GraphicsConfiguration</code>, using
     * the specified image capabilities.
     * If the <code>caps</code> parameter is null, it is effectively ignored
     * and this method will create a VolatileImage without regard to
     * <code>ImageCapabilities</code> constraints.
     *
     * The returned <code>VolatileImage</code> has
     * a layout and color model that is closest to this native device
     * configuration and can therefore be optimally blitted to this
     * device.
     * <p>
     * 使用指定的图像功能,返回与此<code> GraphicsConfiguration </code>兼容的数据布局和颜色模型的{@link VolatileImage}。
     * 如果<code> caps </code>参数为null,那么它将被有效地忽略,并且此方法将创建一个VolatileImage而不考虑<code> ImageCapabilities </code>约束
     * 。
     * 使用指定的图像功能,返回与此<code> GraphicsConfiguration </code>兼容的数据布局和颜色模型的{@link VolatileImage}。
     * 
     *  返回的<code> VolatileImage </code>具有最接近本地设备配置的布局和颜色模型,因此可以最佳地适用于此设备。
     * 
     * 
     * @return a <code>VolatileImage</code> whose data layout and color
     * model is compatible with this <code>GraphicsConfiguration</code>.
     * @param width the width of the returned <code>VolatileImage</code>
     * @param height the height of the returned <code>VolatileImage</code>
     * @param caps the image capabilities
     * @exception AWTException if the supplied image capabilities could not
     * be met by this graphics configuration
     * @since 1.4
     */
    public VolatileImage createCompatibleVolatileImage(int width, int height,
        ImageCapabilities caps) throws AWTException
    {
        return createCompatibleVolatileImage(width, height, caps,
                                             Transparency.OPAQUE);
    }

    /**
     * Returns a {@link VolatileImage} with a data layout and color model
     * compatible with this <code>GraphicsConfiguration</code>, using
     * the specified image capabilities and transparency value.
     * If the <code>caps</code> parameter is null, it is effectively ignored
     * and this method will create a VolatileImage without regard to
     * <code>ImageCapabilities</code> constraints.
     *
     * The returned <code>VolatileImage</code> has
     * a layout and color model that is closest to this native device
     * configuration and can therefore be optimally blitted to this
     * device.
     * <p>
     *  使用指定的图像能力和透明度值,返回与此<code> GraphicsConfiguration </code>兼容的数据布局和颜色模型的{@link VolatileImage}。
     * 如果<code> caps </code>参数为null,那么它将被有效地忽略,并且此方法将创建一个VolatileImage而不考虑<code> ImageCapabilities </code>约束
     * 。
     *  使用指定的图像能力和透明度值,返回与此<code> GraphicsConfiguration </code>兼容的数据布局和颜色模型的{@link VolatileImage}。
     * 
     *  返回的<code> VolatileImage </code>具有最接近本地设备配置的布局和颜色模型,因此可以最佳地适用于此设备。
     * 
     * 
     * @param width the width of the returned <code>VolatileImage</code>
     * @param height the height of the returned <code>VolatileImage</code>
     * @param caps the image capabilities
     * @param transparency the specified transparency mode
     * @return a <code>VolatileImage</code> whose data layout and color
     * model is compatible with this <code>GraphicsConfiguration</code>.
     * @see Transparency#OPAQUE
     * @see Transparency#BITMASK
     * @see Transparency#TRANSLUCENT
     * @throws IllegalArgumentException if the transparency is not a valid value
     * @exception AWTException if the supplied image capabilities could not
     * be met by this graphics configuration
     * @see Component#createVolatileImage(int, int)
     * @since 1.5
     */
    public VolatileImage createCompatibleVolatileImage(int width, int height,
        ImageCapabilities caps, int transparency) throws AWTException
    {
        VolatileImage vi =
            new SunVolatileImage(this, width, height, transparency, caps);
        if (caps != null && caps.isAccelerated() &&
            !vi.getCapabilities().isAccelerated())
        {
            throw new AWTException("Supplied image capabilities could not " +
                                   "be met by this graphics configuration.");
        }
        return vi;
    }

    /**
     * Returns the {@link ColorModel} associated with this
     * <code>GraphicsConfiguration</code>.
     * <p>
     *  返回与此<code> GraphicsConfiguration </code>关联的{@link ColorModel}。
     * 
     * 
     * @return a <code>ColorModel</code> object that is associated with
     * this <code>GraphicsConfiguration</code>.
     */
    public abstract ColorModel getColorModel();

    /**
     * Returns the <code>ColorModel</code> associated with this
     * <code>GraphicsConfiguration</code> that supports the specified
     * transparency.
     * <p>
     *  返回与此<code> GraphicsConfiguration </code>关联的<code> ColorModel </code>,它支持指定的透明度。
     * 
     * 
     * @param transparency the specified transparency mode
     * @return a <code>ColorModel</code> object that is associated with
     * this <code>GraphicsConfiguration</code> and supports the
     * specified transparency or null if the transparency is not a valid
     * value.
     * @see Transparency#OPAQUE
     * @see Transparency#BITMASK
     * @see Transparency#TRANSLUCENT
     */
    public abstract ColorModel getColorModel(int transparency);

    /**
     * Returns the default {@link AffineTransform} for this
     * <code>GraphicsConfiguration</code>. This
     * <code>AffineTransform</code> is typically the Identity transform
     * for most normal screens.  The default <code>AffineTransform</code>
     * maps coordinates onto the device such that 72 user space
     * coordinate units measure approximately 1 inch in device
     * space.  The normalizing transform can be used to make
     * this mapping more exact.  Coordinates in the coordinate space
     * defined by the default <code>AffineTransform</code> for screen and
     * printer devices have the origin in the upper left-hand corner of
     * the target region of the device, with X coordinates
     * increasing to the right and Y coordinates increasing downwards.
     * For image buffers not associated with a device, such as those not
     * created by <code>createCompatibleImage</code>,
     * this <code>AffineTransform</code> is the Identity transform.
     * <p>
     * 为此<code> GraphicsConfiguration </code>返回默认的{@link AffineTransform}。
     * 这个<code> AffineTransform </code>通常是大多数正常屏幕的身份转换。
     * 默认的<code> AffineTransform </code>将坐标映射到设备上,使得72个用户空间坐标单位在设备空间中测量大约1英寸。归一化变换可以用于使该映射更精确。
     * 由屏幕和打印机设备的默认<code> AffineTransform </code>定义的坐标空间中的坐标具有在设备的目标区域的左上角的原点,其中X坐标向右增加,Y坐标向下增加。
     * 对于不与设备相关联的图像缓冲器(例如不是由<code> createCompatibleImage </code>创建的缓冲器),此<代码> AffineTransform </code>是身份转换。
     * 
     * 
     * @return the default <code>AffineTransform</code> for this
     * <code>GraphicsConfiguration</code>.
     */
    public abstract AffineTransform getDefaultTransform();

    /**
     *
     * Returns a <code>AffineTransform</code> that can be concatenated
     * with the default <code>AffineTransform</code>
     * of a <code>GraphicsConfiguration</code> so that 72 units in user
     * space equals 1 inch in device space.
     * <p>
     * For a particular {@link Graphics2D}, g, one
     * can reset the transformation to create
     * such a mapping by using the following pseudocode:
     * <pre>
     *      GraphicsConfiguration gc = g.getDeviceConfiguration();
     *
     *      g.setTransform(gc.getDefaultTransform());
     *      g.transform(gc.getNormalizingTransform());
     * </pre>
     * Note that sometimes this <code>AffineTransform</code> is identity,
     * such as for printers or metafile output, and that this
     * <code>AffineTransform</code> is only as accurate as the information
     * supplied by the underlying system.  For image buffers not
     * associated with a device, such as those not created by
     * <code>createCompatibleImage</code>, this
     * <code>AffineTransform</code> is the Identity transform
     * since there is no valid distance measurement.
     * <p>
     *  返回可以与<code> GraphicsConfiguration </code>的默认<code> AffineTransform </code>级联的<code> AffineTransform 
     * </code>,以便用户空间中的72个单位等于1英寸的设备空间。
     * <p>
     *  对于特定的{@link Graphics2D},g,可以使用以下伪代码重置转换以创建此类映射：
     * <pre>
     *  GraphicsConfiguration gc = g.getDeviceConfiguration();
     * 
     *  g.setTransform(gc.getDefaultTransform()); g.transform(gc.getNormalizingTransform());
     * </pre>
     * 注意,有时这个<code> AffineTransform </code>是标识,例如打印机或元文件输出,并且这个<code> AffineTransform </code>只与底层系统提供的信息一样准
     * 确。
     * 对于不与设备相关联的图像缓冲器(例如不是由<code> createCompatibleImage </code>创建的缓冲器),此<代码> AffineTransform </code>是身份转换,因
     * 为没有有效的距离测量。
     * 
     * 
     * @return an <code>AffineTransform</code> to concatenate to the
     * default <code>AffineTransform</code> so that 72 units in user
     * space is mapped to 1 inch in device space.
     */
    public abstract AffineTransform getNormalizingTransform();

    /**
     * Returns the bounds of the <code>GraphicsConfiguration</code>
     * in the device coordinates. In a multi-screen environment
     * with a virtual device, the bounds can have negative X
     * or Y origins.
     * <p>
     * 
     * @return the bounds of the area covered by this
     * <code>GraphicsConfiguration</code>.
     * @since 1.3
     */
    public abstract Rectangle getBounds();

    private static class DefaultBufferCapabilities extends BufferCapabilities {
        public DefaultBufferCapabilities(ImageCapabilities imageCaps) {
            super(imageCaps, imageCaps, null);
        }
    }

    /**
     * Returns the buffering capabilities of this
     * <code>GraphicsConfiguration</code>.
     * <p>
     *  返回设备坐标中<code> GraphicsConfiguration </code>的边界。在具有虚拟设备的多屏幕环境中,边界可以具有负X或Y起点。
     * 
     * 
     * @return the buffering capabilities of this graphics
     * configuration object
     * @since 1.4
     */
    public BufferCapabilities getBufferCapabilities() {
        if (defaultBufferCaps == null) {
            defaultBufferCaps = new DefaultBufferCapabilities(
                getImageCapabilities());
        }
        return defaultBufferCaps;
    }

    /**
     * Returns the image capabilities of this
     * <code>GraphicsConfiguration</code>.
     * <p>
     *  返回此<code> GraphicsConfiguration </code>的缓冲能力。
     * 
     * 
     * @return the image capabilities of this graphics
     * configuration object
     * @since 1.4
     */
    public ImageCapabilities getImageCapabilities() {
        if (defaultImageCaps == null) {
            defaultImageCaps = new ImageCapabilities(false);
        }
        return defaultImageCaps;
    }

    /**
     * Returns whether this {@code GraphicsConfiguration} supports
     * the {@link GraphicsDevice.WindowTranslucency#PERPIXEL_TRANSLUCENT
     * PERPIXEL_TRANSLUCENT} kind of translucency.
     *
     * <p>
     *  返回此<code> GraphicsConfiguration </code>的图像功能。
     * 
     * 
     * @return whether the given GraphicsConfiguration supports
     *         the translucency effects.
     *
     * @see Window#setBackground(Color)
     *
     * @since 1.7
     */
    public boolean isTranslucencyCapable() {
        // Overridden in subclasses
        return false;
    }
}
