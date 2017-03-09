/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.awt.image.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.File;
import sun.util.logging.PlatformLogger;
import sun.awt.image.SunWritableRaster;

/**
 * The splash screen can be displayed at application startup, before the
 * Java Virtual Machine (JVM) starts. The splash screen is displayed as an
 * undecorated window containing an image. You can use GIF, JPEG, or PNG files
 * for the image. Animation is supported for the GIF format, while transparency
 * is supported both for GIF and PNG.  The window is positioned at the center
 * of the screen. The position on multi-monitor systems is not specified. It is
 * platform and implementation dependent.  The splash screen window is closed
 * automatically as soon as the first window is displayed by Swing/AWT (may be
 * also closed manually using the Java API, see below).
 * <P>
 * If your application is packaged in a jar file, you can use the
 * "SplashScreen-Image" option in a manifest file to show a splash screen.
 * Place the image in the jar archive and specify the path in the option.
 * The path should not have a leading slash.
 * <BR>
 * For example, in the <code>manifest.mf</code> file:
 * <PRE>
 * Manifest-Version: 1.0
 * Main-Class: Test
 * SplashScreen-Image: filename.gif
 * </PRE>
 * <P>
 * If the Java implementation provides the command-line interface and you run
 * your application by using the command line or a shortcut, use the Java
 * application launcher option to show a splash screen. The Oracle reference
 * implementation allows you to specify the splash screen image location with
 * the {@code -splash:} option.
 * <BR>
 * For example:
 * <PRE>
 * java -splash:filename.gif Test
 * </PRE>
 * The command line interface has higher precedence over the manifest
 * setting.
 * <p>
 * The splash screen will be displayed as faithfully as possible to present the
 * whole splash screen image given the limitations of the target platform and
 * display.
 * <p>
 * It is implied that the specified image is presented on the screen "as is",
 * i.e. preserving the exact color values as specified in the image file. Under
 * certain circumstances, though, the presented image may differ, e.g. when
 * applying color dithering to present a 32 bits per pixel (bpp) image on a 16
 * or 8 bpp screen. The native platform display configuration may also affect
 * the colors of the displayed image (e.g.  color profiles, etc.)
 * <p>
 * The {@code SplashScreen} class provides the API for controlling the splash
 * screen. This class may be used to close the splash screen, change the splash
 * screen image, get the splash screen native window position/size, and paint
 * in the splash screen. It cannot be used to create the splash screen. You
 * should use the options provided by the Java implementation for that.
 * <p>
 * This class cannot be instantiated. Only a single instance of this class
 * can exist, and it may be obtained by using the {@link #getSplashScreen()}
 * static method. In case the splash screen has not been created at
 * application startup via the command line or manifest file option,
 * the <code>getSplashScreen</code> method returns <code>null</code>.
 *
 * <p>
 *  在Java虚拟机(JVM)启动之前,可以在应用程序启动时显示启动屏幕。启动画面显示为包含图像的未装饰窗口。您可以对图片使用GIF,JPEG或PNG文件。
 *  GIF格式支持动画,而GIF和PNG格式均支持透明度。窗口位于屏幕中心。未指定多监视器系统上的位置。它是平台和实现依赖。
 * 只要Swing / AWT显示第一个窗口(也可以使用Java API手动关闭,请参见下文),启动屏幕窗口将自动关闭。
 * <P>
 *  如果您的应用程序打包在一个jar文件中,您可以使用清单文件中的"SplashScreen-Image"选项来显示启动屏幕。将图像放在jar存档中,并在选项中指定路径。路径不应该有一个斜杠。
 * <BR>
 *  例如,在<code> manifest.mf </code>文件中：
 * <PRE>
 *  Manifest-Version：1.0 Main-Class：Test SplashScreen-Image：filename.gif
 * </PRE>
 * <P>
 *  如果Java实现提供了命令行界面,并且使用命令行或快捷方式运行应用程序,请使用Java应用程序启动器选项显示启动屏幕。
 *  Oracle参考实现允许您使用{@code -splash：}选项指定初始屏幕图像位置。
 * <BR>
 * 例如：
 * <PRE>
 *  java -splash：filename.gif测试
 * </PRE>
 *  命令行界面的优先级高于清单设置。
 * <p>
 *  在给定目标平台和显示器的限制的情况下,飞溅屏幕将尽可能忠实地显示以呈现整个飞溅屏幕图像。
 * <p>
 *  这意味着指定的图像在屏幕上"按原样"呈现,即保留在图像文件中指定的精确颜色值。然而,在某些情况下,所呈现的图像可能不同,例如,当应用彩色抖动以在16或8bpp屏幕上呈现32位每像素(bpp)图像时。
 * 原生平台显示配置还可以影响所显示的图像的颜色(例如,颜色配置文件等)。
 * <p>
 *  {@code SplashScreen}类提供了用于控制启动屏幕的API。此类可用于关闭启动屏幕,更改启动屏幕图像,获取启动屏幕本机窗口位置/大小,并在启动屏幕中绘制。它不能用于创建启动屏幕。
 * 您应该使用Java实现提供的选项。
 * <p>
 *  此类不能实例化。只有此类的一个实例可以存在,并且可以通过使用{@link #getSplashScreen()}静态方法获得。
 * 如果尚未在应用程序启动时通过命令行或清单文件选项创建启动屏幕,则<code> getSplashScreen </code>方法返回<code> null </code>。
 * 
 * 
 * @author Oleg Semenov
 * @since 1.6
 */
public final class SplashScreen {

    SplashScreen(long ptr) { // non-public constructor
        splashPtr = ptr;
    }

    /**
     * Returns the {@code SplashScreen} object used for
     * Java startup splash screen control on systems that support display.
     *
     * <p>
     * 返回用于支持显示的系统上的Java启动初始屏幕控件的{@code SplashScreen}对象。
     * 
     * 
     * @throws UnsupportedOperationException if the splash screen feature is not
     *         supported by the current toolkit
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *         returns true
     * @return the {@link SplashScreen} instance, or <code>null</code> if there is
     *         none or it has already been closed
     */
    public static  SplashScreen getSplashScreen() {
        synchronized (SplashScreen.class) {
            if (GraphicsEnvironment.isHeadless()) {
                throw new HeadlessException();
            }
            // SplashScreen class is now a singleton
            if (!wasClosed && theInstance == null) {
                java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedAction<Void>() {
                        public Void run() {
                            System.loadLibrary("splashscreen");
                            return null;
                        }
                    });
                long ptr = _getInstance();
                if (ptr != 0 && _isVisible(ptr)) {
                    theInstance = new SplashScreen(ptr);
                }
            }
            return theInstance;
        }
    }

    /**
     * Changes the splash screen image. The new image is loaded from the
     * specified URL; GIF, JPEG and PNG image formats are supported.
     * The method returns after the image has finished loading and the window
     * has been updated.
     * The splash screen window is resized according to the size of
     * the image and is centered on the screen.
     *
     * <p>
     *  更改启动屏幕图像。新图像从指定的网址加载;支持GIF,JPEG和PNG图像格式。该方法在图像加载完成并且窗口已更新后返回。启动屏幕窗口根据图像的大小调整大小,并以屏幕为中心。
     * 
     * 
     * @param imageURL the non-<code>null</code> URL for the new
     *        splash screen image
     * @throws NullPointerException if {@code imageURL} is <code>null</code>
     * @throws IOException if there was an error while loading the image
     * @throws IllegalStateException if the splash screen has already been
     *         closed
     */
    public void setImageURL(URL imageURL) throws NullPointerException, IOException, IllegalStateException {
        checkVisible();
        URLConnection connection = imageURL.openConnection();
        connection.connect();
        int length = connection.getContentLength();
        java.io.InputStream stream = connection.getInputStream();
        byte[] buf = new byte[length];
        int off = 0;
        while(true) {
            // check for available data
            int available = stream.available();
            if (available <= 0) {
                // no data available... well, let's try reading one byte
                // we'll see what happens then
                available = 1;
            }
            // check for enough room in buffer, realloc if needed
            // the buffer always grows in size 2x minimum
            if (off + available > length) {
                length = off*2;
                if (off + available > length) {
                    length = available+off;
                }
                byte[] oldBuf = buf;
                buf = new byte[length];
                System.arraycopy(oldBuf, 0, buf, 0, off);
            }
            // now read the data
            int result = stream.read(buf, off, available);
            if (result < 0) {
                break;
            }
            off += result;
        }
        synchronized(SplashScreen.class) {
            checkVisible();
            if (!_setImageData(splashPtr, buf)) {
                throw new IOException("Bad image format or i/o error when loading image");
            }
            this.imageURL = imageURL;
        }
    }

    private void checkVisible() {
        if (!isVisible()) {
            throw new IllegalStateException("no splash screen available");
        }
    }
    /**
     * Returns the current splash screen image.
     *
     * <p>
     *  返回当前启动屏幕图像。
     * 
     * 
     * @return URL for the current splash screen image file
     * @throws IllegalStateException if the splash screen has already been closed
     */
    public URL getImageURL() throws IllegalStateException {
        synchronized (SplashScreen.class) {
            checkVisible();
            if (imageURL == null) {
                try {
                    String fileName = _getImageFileName(splashPtr);
                    String jarName = _getImageJarName(splashPtr);
                    if (fileName != null) {
                        if (jarName != null) {
                            imageURL = new URL("jar:"+(new File(jarName).toURL().toString())+"!/"+fileName);
                        } else {
                            imageURL = new File(fileName).toURL();
                        }
                    }
                }
                catch(java.net.MalformedURLException e) {
                    if (log.isLoggable(PlatformLogger.Level.FINE)) {
                        log.fine("MalformedURLException caught in the getImageURL() method", e);
                    }
                }
            }
            return imageURL;
        }
    }

    /**
     * Returns the bounds of the splash screen window as a {@link Rectangle}.
     * This may be useful if, for example, you want to replace the splash
     * screen with your window at the same location.
     * <p>
     * You cannot control the size or position of the splash screen.
     * The splash screen size is adjusted automatically when the image changes.
     * <p>
     * The image may contain transparent areas, and thus the reported bounds may
     * be larger than the visible splash screen image on the screen.
     *
     * <p>
     *  以{@link Rectangle}形式返回启动屏幕窗口的边界。这可能是有用的,例如,如果你想用同一位置的窗口替换启动屏幕。
     * <p>
     *  您无法控制启动画面的大小或位置。当图像更改时,会自动调整闪屏尺寸。
     * <p>
     *  图像可以包含透明区域,因此报告的边界可以大于屏幕上的可见闪光屏幕图像。
     * 
     * 
     * @return a {@code Rectangle} containing the splash screen bounds
     * @throws IllegalStateException if the splash screen has already been closed
     */
    public Rectangle getBounds() throws IllegalStateException {
        synchronized (SplashScreen.class) {
            checkVisible();
            float scale = _getScaleFactor(splashPtr);
            Rectangle bounds = _getBounds(splashPtr);
            assert scale > 0;
            if (scale > 0 && scale != 1) {
                bounds.setSize((int) (bounds.getWidth() / scale),
                        (int) (bounds.getWidth() / scale));
            }
            return bounds;
        }
    }

    /**
     * Returns the size of the splash screen window as a {@link Dimension}.
     * This may be useful if, for example,
     * you want to draw on the splash screen overlay surface.
     * <p>
     * You cannot control the size or position of the splash screen.
     * The splash screen size is adjusted automatically when the image changes.
     * <p>
     * The image may contain transparent areas, and thus the reported size may
     * be larger than the visible splash screen image on the screen.
     *
     * <p>
     *  以{@link Dimension}形式返回启动屏幕窗口的大小。这可能是有用的,例如,如果你想画在闪屏重叠表面。
     * <p>
     *  您无法控制启动画面的大小或位置。当图像更改时,会自动调整闪屏尺寸。
     * <p>
     *  图像可以包含透明区域,因此报告的大小可以大于屏幕上的可见闪光屏幕图像。
     * 
     * 
     * @return a {@link Dimension} object indicating the splash screen size
     * @throws IllegalStateException if the splash screen has already been closed
     */
    public Dimension getSize() throws IllegalStateException {
        return getBounds().getSize();
    }

    /**
     * Creates a graphics context (as a {@link Graphics2D} object) for the splash
     * screen overlay image, which allows you to draw over the splash screen.
     * Note that you do not draw on the main image but on the image that is
     * displayed over the main image using alpha blending. Also note that drawing
     * on the overlay image does not necessarily update the contents of splash
     * screen window. You should call {@code update()} on the
     * <code>SplashScreen</code> when you want the splash screen to be
     * updated immediately.
     * <p>
     * The pixel (0, 0) in the coordinate space of the graphics context
     * corresponds to the origin of the splash screen native window bounds (see
     * {@link #getBounds()}).
     *
     * <p>
     * 为启动屏幕覆盖图像创建图形上下文(作为{@link Graphics2D}对象),允许您在启动屏幕上绘制。请注意,您不在主图像上绘制,而是在使用Alpha混合在主图像上显示的图像上绘制。
     * 还要注意,在叠加图像上绘图不一定更新启动屏幕窗口的内容。当您希望立即更新启动屏幕时,应在<code> SplashScreen </code>上调用{@code update()}。
     * <p>
     *  图形上下文的坐标空间中的像素(0,0)对应于初始屏幕本机窗口边界的原点(参见{@link #getBounds()})。
     * 
     * 
     * @return graphics context for the splash screen overlay surface
     * @throws IllegalStateException if the splash screen has already been closed
     */
    public Graphics2D createGraphics() throws IllegalStateException {
        synchronized (SplashScreen.class) {
            checkVisible();
            if (image==null) {
                // get unscaled splash image size
                Dimension dim = _getBounds(splashPtr).getSize();
                image = new BufferedImage(dim.width, dim.height,
                        BufferedImage.TYPE_INT_ARGB);
            }
            float scale = _getScaleFactor(splashPtr);
            Graphics2D g = image.createGraphics();
            assert (scale > 0);
            if (scale <= 0) {
                scale = 1;
            }
            g.scale(scale, scale);
            return g;
        }
    }

    /**
     * Updates the splash window with current contents of the overlay image.
     *
     * <p>
     *  使用叠加图像的当前内容更新启动窗口。
     * 
     * 
     * @throws IllegalStateException if the overlay image does not exist;
     *         for example, if {@code createGraphics} has never been called,
     *         or if the splash screen has already been closed
     */
    public void update() throws IllegalStateException {
        BufferedImage image;
        synchronized (SplashScreen.class) {
            checkVisible();
            image = this.image;
        }
        if (image == null) {
            throw new IllegalStateException("no overlay image available");
        }
        DataBuffer buf = image.getRaster().getDataBuffer();
        if (!(buf instanceof DataBufferInt)) {
            throw new AssertionError("Overlay image DataBuffer is of invalid type == "+buf.getClass().getName());
        }
        int numBanks = buf.getNumBanks();
        if (numBanks!=1) {
            throw new AssertionError("Invalid number of banks =="+numBanks+" in overlay image DataBuffer");
        }
        if (!(image.getSampleModel() instanceof SinglePixelPackedSampleModel)) {
            throw new AssertionError("Overlay image has invalid sample model == "+image.getSampleModel().getClass().getName());
        }
        SinglePixelPackedSampleModel sm = (SinglePixelPackedSampleModel)image.getSampleModel();
        int scanlineStride = sm.getScanlineStride();
        Rectangle rect = image.getRaster().getBounds();
        // Note that we steal the data array here, but just for reading
        // so we do not need to mark the DataBuffer dirty...
        int[] data = SunWritableRaster.stealData((DataBufferInt)buf, 0);
        synchronized(SplashScreen.class) {
            checkVisible();
            _update(splashPtr, data, rect.x, rect.y, rect.width, rect.height, scanlineStride);
        }
    }

    /**
     * Hides the splash screen, closes the window, and releases all associated
     * resources.
     *
     * <p>
     *  隐藏启动屏幕,关闭窗口,并释放所有相关资源。
     * 
     * 
     * @throws IllegalStateException if the splash screen has already been closed
     */
    public void close() throws IllegalStateException {
        synchronized (SplashScreen.class) {
            checkVisible();
            _close(splashPtr);
            image = null;
            SplashScreen.markClosed();
        }
    }

    static void markClosed() {
        synchronized (SplashScreen.class) {
            wasClosed = true;
            theInstance = null;
        }
    }


    /**
     * Determines whether the splash screen is visible. The splash screen may
     * be hidden using {@link #close()}, it is also hidden automatically when
     * the first AWT/Swing window is made visible.
     * <p>
     * Note that the native platform may delay presenting the splash screen
     * native window on the screen. The return value of {@code true} for this
     * method only guarantees that the conditions to hide the splash screen
     * window have not occurred yet.
     *
     * <p>
     *  确定启动屏幕是否可见。启动屏幕可以使用{@link #close()}隐藏,当第一个AWT / Swing窗口可见时,它也会自动隐藏。
     * <p>
     *  注意,本地平台可以延迟在屏幕上呈现闪屏屏幕本机窗口。此方法的{@code true}的返回值仅保证隐藏初始屏幕窗口的条件尚未发生。
     * 
     * 
     * @return true if the splash screen is visible (has not been closed yet),
     *         false otherwise
     */
    public boolean isVisible() {
        synchronized (SplashScreen.class) {
            return !wasClosed && _isVisible(splashPtr);
        }
    }

    private BufferedImage image; // overlay image

    private final long splashPtr; // pointer to native Splash structure
    private static boolean wasClosed = false;

    private URL imageURL;

    /**
     * The instance reference for the singleton.
     * (<code>null</code> if no instance exists yet.)
     *
     * <p>
     * 
     * @see #getSplashScreen
     * @see #close
     */
    private static SplashScreen theInstance = null;

    private static final PlatformLogger log = PlatformLogger.getLogger("java.awt.SplashScreen");

    private native static void _update(long splashPtr, int[] data, int x, int y, int width, int height, int scanlineStride);
    private native static boolean _isVisible(long splashPtr);
    private native static Rectangle _getBounds(long splashPtr);
    private native static long _getInstance();
    private native static void _close(long splashPtr);
    private native static String _getImageFileName(long splashPtr);
    private native static String _getImageJarName(long SplashPtr);
    private native static boolean _setImageData(long SplashPtr, byte[] data);
    private native static float _getScaleFactor(long SplashPtr);

}
