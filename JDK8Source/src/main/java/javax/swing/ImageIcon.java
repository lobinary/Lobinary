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
package javax.swing;

import java.awt.*;
import java.awt.image.*;
import java.beans.ConstructorProperties;
import java.beans.Transient;
import java.net.URL;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import java.util.Locale;
import javax.accessibility.*;

import sun.awt.AppContext;
import java.lang.reflect.Field;
import java.security.*;

/**
 * An implementation of the Icon interface that paints Icons
 * from Images. Images that are created from a URL, filename or byte array
 * are preloaded using MediaTracker to monitor the loaded state
 * of the image.
 *
 * <p>
 * For further information and examples of using image icons, see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html">How to Use Icons</a>
 * in <em>The Java Tutorial.</em>
 *
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  实现从图像绘制图标的Icon界面。使用MediaTracker预加载从URL,文件名或字节数组创建的图像,以监视图像的加载状态。
 * 
 * <p>
 *  有关使用图片图标的详细信息和示例,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html">如
 * 何使用图标</a> > Java教程。
 * </em>。
 * 
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Jeff Dinkins
 * @author Lynn Monsanto
 */
public class ImageIcon implements Icon, Serializable, Accessible {
    /* Keep references to the filename and location so that
     * alternate persistence schemes have the option to archive
     * images symbolically rather than including the image data
     * in the archive.
     * <p>
     *  替代持久性方案具有符号存档图像的选项,而不是将图像数据包括在存档中。
     * 
     */
    transient private String filename;
    transient private URL location;

    transient Image image;
    transient int loadStatus = 0;
    ImageObserver imageObserver;
    String description = null;

    /**
     * Do not use this shared component, which is used to track image loading.
     * It is left for backward compatibility only.
     * <p>
     *  不要使用此共享组件,用于跟踪图像加载。它仅用于向后兼容。
     * 
     * 
     * @deprecated since 1.8
     */
    @Deprecated
    protected final static Component component;

    /**
     * Do not use this shared media tracker, which is used to load images.
     * It is left for backward compatibility only.
     * <p>
     *  不要使用此共享媒体跟踪器,用于加载图像。它仅用于向后兼容。
     * 
     * 
     * @deprecated since 1.8
     */
    @Deprecated
    protected final static MediaTracker tracker;

    static {
        component = AccessController.doPrivileged(new PrivilegedAction<Component>() {
            public Component run() {
                try {
                    final Component component = createNoPermsComponent();

                    // 6482575 - clear the appContext field so as not to leak it
                    Field appContextField =

                            Component.class.getDeclaredField("appContext");
                    appContextField.setAccessible(true);
                    appContextField.set(component, null);

                    return component;
                } catch (Throwable e) {
                    // We don't care about component.
                    // So don't prevent class initialisation.
                    e.printStackTrace();
                    return null;
                }
            }
        });
        tracker = new MediaTracker(component);
    }

    private static Component createNoPermsComponent() {
        // 7020198 - set acc field to no permissions and no subject
        // Note, will have appContext set.
        return AccessController.doPrivileged(
                new PrivilegedAction<Component>() {
                    public Component run() {
                        return new Component() {
                        };
                    }
                },
                new AccessControlContext(new ProtectionDomain[]{
                        new ProtectionDomain(null, null)
                })
        );
    }

    /**
     * Id used in loading images from MediaTracker.
     * <p>
     *  用于从MediaTracker加载图像的ID。
     * 
     */
    private static int mediaTrackerID;

    private final static Object TRACKER_KEY = new StringBuilder("TRACKER_KEY");

    int width = -1;
    int height = -1;

    /**
     * Creates an ImageIcon from the specified file. The image will
     * be preloaded by using MediaTracker to monitor the loading state
     * of the image.
     * <p>
     *  从指定的文件创建ImageIcon。通过使用MediaTracker监视图像的加载状态,将预加载图像。
     * 
     * 
     * @param filename the name of the file containing the image
     * @param description a brief textual description of the image
     * @see #ImageIcon(String)
     */
    public ImageIcon(String filename, String description) {
        image = Toolkit.getDefaultToolkit().getImage(filename);
        if (image == null) {
            return;
        }
        this.filename = filename;
        this.description = description;
        loadImage(image);
    }

    /**
     * Creates an ImageIcon from the specified file. The image will
     * be preloaded by using MediaTracker to monitor the loading state
     * of the image. The specified String can be a file name or a
     * file path. When specifying a path, use the Internet-standard
     * forward-slash ("/") as a separator.
     * (The string is converted to an URL, so the forward-slash works
     * on all systems.)
     * For example, specify:
     * <pre>
     *    new ImageIcon("images/myImage.gif") </pre>
     * The description is initialized to the <code>filename</code> string.
     *
     * <p>
     * 从指定的文件创建ImageIcon。通过使用MediaTracker监视图像的加载状态,将预加载图像。指定的String可以是文件名或文件路径。
     * 指定路径时,请使用Internet标准的正斜杠("/")作为分隔符。 (字符串被转换为URL,因此正斜线在所有系统上都有效。)例如,指定：。
     * <pre>
     *  new ImageIcon("images / myImage.gif")</pre>描述初始化为<code> filename </code>字符串。
     * 
     * 
     * @param filename a String specifying a filename or path
     * @see #getDescription
     */
    @ConstructorProperties({"description"})
    public ImageIcon (String filename) {
        this(filename, filename);
    }

    /**
     * Creates an ImageIcon from the specified URL. The image will
     * be preloaded by using MediaTracker to monitor the loaded state
     * of the image.
     * <p>
     *  从指定的URL创建ImageIcon。将使用MediaTracker监视图像的加载状态来预加载图像。
     * 
     * 
     * @param location the URL for the image
     * @param description a brief textual description of the image
     * @see #ImageIcon(String)
     */
    public ImageIcon(URL location, String description) {
        image = Toolkit.getDefaultToolkit().getImage(location);
        if (image == null) {
            return;
        }
        this.location = location;
        this.description = description;
        loadImage(image);
    }

    /**
     * Creates an ImageIcon from the specified URL. The image will
     * be preloaded by using MediaTracker to monitor the loaded state
     * of the image.
     * The icon's description is initialized to be
     * a string representation of the URL.
     * <p>
     *  从指定的URL创建ImageIcon。将使用MediaTracker监视图像的加载状态来预加载图像。图标的描述被初始化为URL的字符串表示形式。
     * 
     * 
     * @param location the URL for the image
     * @see #getDescription
     */
    public ImageIcon (URL location) {
        this(location, location.toExternalForm());
    }

    /**
     * Creates an ImageIcon from the image.
     * <p>
     *  从图像创建ImageIcon。
     * 
     * 
     * @param image the image
     * @param description a brief textual description of the image
     */
    public ImageIcon(Image image, String description) {
        this(image);
        this.description = description;
    }

    /**
     * Creates an ImageIcon from an image object.
     * If the image has a "comment" property that is a string,
     * then the string is used as the description of this icon.
     * <p>
     *  从图像对象创建ImageIcon。如果图像具有为字符串的"comment"属性,则该字符串将用作此图标的描述。
     * 
     * 
     * @param image the image
     * @see #getDescription
     * @see java.awt.Image#getProperty
     */
    public ImageIcon (Image image) {
        this.image = image;
        Object o = image.getProperty("comment", imageObserver);
        if (o instanceof String) {
            description = (String) o;
        }
        loadImage(image);
    }

    /**
     * Creates an ImageIcon from an array of bytes which were
     * read from an image file containing a supported image format,
     * such as GIF, JPEG, or (as of 1.3) PNG.
     * Normally this array is created
     * by reading an image using Class.getResourceAsStream(), but
     * the byte array may also be statically stored in a class.
     *
     * <p>
     *  从包含支持的图像格式(例如GIF,JPEG或(从1.3版本)PNG)的图像文件读取的字节数组创建ImageIcon。
     * 通常,此数组是通过使用Class.getResourceAsStream()读取图像创建的,但字节数组也可以静态存储在类中。
     * 
     * 
     * @param  imageData an array of pixels in an image format supported
     *         by the AWT Toolkit, such as GIF, JPEG, or (as of 1.3) PNG
     * @param  description a brief textual description of the image
     * @see    java.awt.Toolkit#createImage
     */
    public ImageIcon (byte[] imageData, String description) {
        this.image = Toolkit.getDefaultToolkit().createImage(imageData);
        if (image == null) {
            return;
        }
        this.description = description;
        loadImage(image);
    }

    /**
     * Creates an ImageIcon from an array of bytes which were
     * read from an image file containing a supported image format,
     * such as GIF, JPEG, or (as of 1.3) PNG.
     * Normally this array is created
     * by reading an image using Class.getResourceAsStream(), but
     * the byte array may also be statically stored in a class.
     * If the resulting image has a "comment" property that is a string,
     * then the string is used as the description of this icon.
     *
     * <p>
     * 从包含支持的图像格式(例如GIF,JPEG或(从1.3版本)PNG)的图像文件读取的字节数组创建ImageIcon。
     * 通常,此数组是通过使用Class.getResourceAsStream()读取图像创建的,但字节数组也可以静态存储在类中。
     * 如果生成的图像具有为字符串的"comment"属性,则该字符串将用作此图标的描述。
     * 
     * 
     * @param  imageData an array of pixels in an image format supported by
     *             the AWT Toolkit, such as GIF, JPEG, or (as of 1.3) PNG
     * @see    java.awt.Toolkit#createImage
     * @see #getDescription
     * @see java.awt.Image#getProperty
     */
    public ImageIcon (byte[] imageData) {
        this.image = Toolkit.getDefaultToolkit().createImage(imageData);
        if (image == null) {
            return;
        }
        Object o = image.getProperty("comment", imageObserver);
        if (o instanceof String) {
            description = (String) o;
        }
        loadImage(image);
    }

    /**
     * Creates an uninitialized image icon.
     * <p>
     *  创建未初始化的图像图标。
     * 
     */
    public ImageIcon() {
    }

    /**
     * Loads the image, returning only when the image is loaded.
     * <p>
     *  加载图像,仅在加载图像时返回。
     * 
     * 
     * @param image the image
     */
    protected void loadImage(Image image) {
        MediaTracker mTracker = getTracker();
        synchronized(mTracker) {
            int id = getNextID();

            mTracker.addImage(image, id);
            try {
                mTracker.waitForID(id, 0);
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED while loading Image");
            }
            loadStatus = mTracker.statusID(id, false);
            mTracker.removeImage(image, id);

            width = image.getWidth(imageObserver);
            height = image.getHeight(imageObserver);
        }
    }

    /**
     * Returns an ID to use with the MediaTracker in loading an image.
     * <p>
     *  返回在加载图像时与MediaTracker一起使用的ID。
     * 
     */
    private int getNextID() {
        synchronized(getTracker()) {
            return ++mediaTrackerID;
        }
    }

    /**
     * Returns the MediaTracker for the current AppContext, creating a new
     * MediaTracker if necessary.
     * <p>
     *  返回当前AppContext的MediaTracker,如有必要,创建一个新的MediaTracker。
     * 
     */
    private MediaTracker getTracker() {
        Object trackerObj;
        AppContext ac = AppContext.getAppContext();
        // Opt: Only synchronize if trackerObj comes back null?
        // If null, synchronize, re-check for null, and put new tracker
        synchronized(ac) {
            trackerObj = ac.get(TRACKER_KEY);
            if (trackerObj == null) {
                Component comp = new Component() {};
                trackerObj = new MediaTracker(comp);
                ac.put(TRACKER_KEY, trackerObj);
            }
        }
        return (MediaTracker) trackerObj;
    }

    /**
     * Returns the status of the image loading operation.
     * <p>
     *  返回图像加载操作的状态。
     * 
     * 
     * @return the loading status as defined by java.awt.MediaTracker
     * @see java.awt.MediaTracker#ABORTED
     * @see java.awt.MediaTracker#ERRORED
     * @see java.awt.MediaTracker#COMPLETE
     */
    public int getImageLoadStatus() {
        return loadStatus;
    }

    /**
     * Returns this icon's <code>Image</code>.
     * <p>
     *  返回此图标的<code>图片</code>。
     * 
     * 
     * @return the <code>Image</code> object for this <code>ImageIcon</code>
     */
    @Transient
    public Image getImage() {
        return image;
    }

    /**
     * Sets the image displayed by this icon.
     * <p>
     *  设置此图标显示的图像。
     * 
     * 
     * @param image the image
     */
    public void setImage(Image image) {
        this.image = image;
        loadImage(image);
    }

    /**
     * Gets the description of the image.  This is meant to be a brief
     * textual description of the object.  For example, it might be
     * presented to a blind user to give an indication of the purpose
     * of the image.
     * The description may be null.
     *
     * <p>
     *  获取图像的描述。这意味着对象的简短文本描述。例如,它可以被呈现给盲人用户以给出图像的目的的指示。描述可以为null。
     * 
     * 
     * @return a brief textual description of the image
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the image.  This is meant to be a brief
     * textual description of the object.  For example, it might be
     * presented to a blind user to give an indication of the purpose
     * of the image.
     * <p>
     *  设置图像的描述。这意味着对象的简短文本描述。例如,它可以被呈现给盲人用户以给出图像的目的的指示。
     * 
     * 
     * @param description a brief textual description of the image
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Paints the icon.
     * The top-left corner of the icon is drawn at
     * the point (<code>x</code>, <code>y</code>)
     * in the coordinate space of the graphics context <code>g</code>.
     * If this icon has no image observer,
     * this method uses the <code>c</code> component
     * as the observer.
     *
     * <p>
     * 绘制图标。图标的左上角绘制在图形上下文<code> g </code>的坐标空间中的点(<code> x </code>,<code> y </code>)。
     * 如果此图标没有图像观察者,则此方法使用<code> c </code>组件作为观察者。
     * 
     * 
     * @param c the component to be used as the observer
     *          if this icon has no image observer
     * @param g the graphics context
     * @param x the X coordinate of the icon's top-left corner
     * @param y the Y coordinate of the icon's top-left corner
     */
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        if(imageObserver == null) {
           g.drawImage(image, x, y, c);
        } else {
           g.drawImage(image, x, y, imageObserver);
        }
    }

    /**
     * Gets the width of the icon.
     *
     * <p>
     *  获取图标的宽度。
     * 
     * 
     * @return the width in pixels of this icon
     */
    public int getIconWidth() {
        return width;
    }

    /**
     * Gets the height of the icon.
     *
     * <p>
     *  获取图标的高度。
     * 
     * 
     * @return the height in pixels of this icon
     */
    public int getIconHeight() {
        return height;
    }

    /**
     * Sets the image observer for the image.  Set this
     * property if the ImageIcon contains an animated GIF, so
     * the observer is notified to update its display.
     * For example:
     * <pre>
     *     icon = new ImageIcon(...)
     *     button.setIcon(icon);
     *     icon.setImageObserver(button);
     * </pre>
     *
     * <p>
     *  设置图像的图像观察者。如果ImageIcon包含动画GIF,请设置此属性,以便通知观察者更新其显示。例如：
     * <pre>
     *  icon = new ImageIcon(...)button.setIcon(icon); icon.setImageObserver(button);
     * </pre>
     * 
     * 
     * @param observer the image observer
     */
    public void setImageObserver(ImageObserver observer) {
        imageObserver = observer;
    }

    /**
     * Returns the image observer for the image.
     *
     * <p>
     *  返回图像的图像观察器。
     * 
     * 
     * @return the image observer, which may be null
     */
    @Transient
    public ImageObserver getImageObserver() {
        return imageObserver;
    }

    /**
     * Returns a string representation of this image.
     *
     * <p>
     *  返回此图像的字符串表示形式。
     * 
     * 
     * @return a string representing this image
     */
    public String toString() {
        if (description != null) {
            return description;
        }
        return super.toString();
    }

    private void readObject(ObjectInputStream s)
        throws ClassNotFoundException, IOException
    {
        s.defaultReadObject();

        int w = s.readInt();
        int h = s.readInt();
        int[] pixels = (int[])(s.readObject());

        if (pixels != null) {
            Toolkit tk = Toolkit.getDefaultToolkit();
            ColorModel cm = ColorModel.getRGBdefault();
            image = tk.createImage(new MemoryImageSource(w, h, cm, pixels, 0, w));
            loadImage(image);
        }
    }


    private void writeObject(ObjectOutputStream s)
        throws IOException
    {
        s.defaultWriteObject();

        int w = getIconWidth();
        int h = getIconHeight();
        int[] pixels = image != null? new int[w * h] : null;

        if (image != null) {
            try {
                PixelGrabber pg = new PixelGrabber(image, 0, 0, w, h, pixels, 0, w);
                pg.grabPixels();
                if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                    throw new IOException("failed to load image contents");
                }
            }
            catch (InterruptedException e) {
                throw new IOException("image load interrupted");
            }
        }

        s.writeInt(w);
        s.writeInt(h);
        s.writeObject(pixels);
    }

    /**
     * --- Accessibility Support ---
     * <p>
     *  ---辅助功能
     * 
     */

    private AccessibleImageIcon accessibleContext = null;

    /**
     * Gets the AccessibleContext associated with this ImageIcon.
     * For image icons, the AccessibleContext takes the form of an
     * AccessibleImageIcon.
     * A new AccessibleImageIcon instance is created if necessary.
     *
     * <p>
     *  获取与此ImageIcon相关联的AccessibleContext。对于图像图标,AccessibleContext采用AccessibleImageIcon的形式。
     * 如果需要,将创建一个新的AccessibleImageIcon实例。
     * 
     * 
     * @return an AccessibleImageIcon that serves as the
     *         AccessibleContext of this ImageIcon
     * @beaninfo
     *       expert: true
     *  description: The AccessibleContext associated with this ImageIcon.
     * @since 1.3
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleImageIcon();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>ImageIcon</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to image icon user-interface
     * elements.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     *  此类实现了对<code> ImageIcon </code>类的辅助功能支持。它提供了适用于图像用户界面元素的Java辅助功能API的实现。
     * <p>
     *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     * 
     * @since 1.3
     */
    protected class AccessibleImageIcon extends AccessibleContext
        implements AccessibleIcon, Serializable {

        /*
         * AccessibleContest implementation -----------------
         * <p>
         * AccessibleContest实现-----------------
         * 
         */

        /**
         * Gets the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * 
         * @return an instance of AccessibleRole describing the role of the
         * object
         * @see AccessibleRole
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.ICON;
        }

        /**
         * Gets the state of this object.
         *
         * <p>
         *  获取此对象的状态。
         * 
         * 
         * @return an instance of AccessibleStateSet containing the current
         * state set of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            return null;
        }

        /**
         * Gets the Accessible parent of this object.  If the parent of this
         * object implements Accessible, this method should simply return
         * getParent().
         *
         * <p>
         *  获取此对象的可访问父级。如果这个对象的父对象实现Accessible,这个方法应该简单地返回getParent()。
         * 
         * 
         * @return the Accessible parent of this object -- can be null if this
         * object does not have an Accessible parent
         */
        public Accessible getAccessibleParent() {
            return null;
        }

        /**
         * Gets the index of this object in its accessible parent.
         *
         * <p>
         *  获取此对象在其可访问父级中的索引。
         * 
         * 
         * @return the index of this object in its parent; -1 if this
         * object does not have an accessible parent.
         * @see #getAccessibleParent
         */
        public int getAccessibleIndexInParent() {
            return -1;
        }

        /**
         * Returns the number of accessible children in the object.  If all
         * of the children of this object implement Accessible, than this
         * method should return the number of children of this object.
         *
         * <p>
         *  返回对象中可访问的子项数。如果这个对象的所有子对象实现Accessible,那么这个方法应该返回这个对象的子对象数。
         * 
         * 
         * @return the number of accessible children in the object.
         */
        public int getAccessibleChildrenCount() {
            return 0;
        }

        /**
         * Returns the nth Accessible child of the object.
         *
         * <p>
         *  返回对象的第n个Accessible子项。
         * 
         * 
         * @param i zero-based index of child
         * @return the nth Accessible child of the object
         */
        public Accessible getAccessibleChild(int i) {
            return null;
        }

        /**
         * Returns the locale of this object.
         *
         * <p>
         *  返回此对象的语言环境。
         * 
         * 
         * @return the locale of this object
         */
        public Locale getLocale() throws IllegalComponentStateException {
            return null;
        }

        /*
         * AccessibleIcon implementation -----------------
         * <p>
         *  AccessibleIcon实现-----------------
         * 
         */

        /**
         * Gets the description of the icon.  This is meant to be a brief
         * textual description of the object.  For example, it might be
         * presented to a blind user to give an indication of the purpose
         * of the icon.
         *
         * <p>
         *  获取图标的描述。这意味着对象的简短文本描述。例如,它可以被呈现给盲人用户以给出图标的目的的指示。
         * 
         * 
         * @return the description of the icon
         */
        public String getAccessibleIconDescription() {
            return ImageIcon.this.getDescription();
        }

        /**
         * Sets the description of the icon.  This is meant to be a brief
         * textual description of the object.  For example, it might be
         * presented to a blind user to give an indication of the purpose
         * of the icon.
         *
         * <p>
         *  设置图标的说明。这意味着对象的简短文本描述。例如,它可以被呈现给盲人用户以给出图标的目的的指示。
         * 
         * 
         * @param description the description of the icon
         */
        public void setAccessibleIconDescription(String description) {
            ImageIcon.this.setDescription(description);
        }

        /**
         * Gets the height of the icon.
         *
         * <p>
         *  获取图标的高度。
         * 
         * 
         * @return the height of the icon
         */
        public int getAccessibleIconHeight() {
            return ImageIcon.this.height;
        }

        /**
         * Gets the width of the icon.
         *
         * <p>
         *  获取图标的宽度。
         * 
         * @return the width of the icon
         */
        public int getAccessibleIconWidth() {
            return ImageIcon.this.width;
        }

        private void readObject(ObjectInputStream s)
            throws ClassNotFoundException, IOException
        {
            s.defaultReadObject();
        }

        private void writeObject(ObjectOutputStream s)
            throws IOException
        {
            s.defaultWriteObject();
        }
    }  // AccessibleImageIcon
}
