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

import java.awt.image.BufferedImage;
import java.security.AccessController;
import java.util.Locale;

import sun.font.FontManager;
import sun.font.FontManagerFactory;
import sun.java2d.HeadlessGraphicsEnvironment;
import sun.java2d.SunGraphicsEnvironment;
import sun.security.action.GetPropertyAction;

/**
 *
 * The <code>GraphicsEnvironment</code> class describes the collection
 * of {@link GraphicsDevice} objects and {@link java.awt.Font} objects
 * available to a Java(tm) application on a particular platform.
 * The resources in this <code>GraphicsEnvironment</code> might be local
 * or on a remote machine.  <code>GraphicsDevice</code> objects can be
 * screens, printers or image buffers and are the destination of
 * {@link Graphics2D} drawing methods.  Each <code>GraphicsDevice</code>
 * has a number of {@link GraphicsConfiguration} objects associated with
 * it.  These objects specify the different configurations in which the
 * <code>GraphicsDevice</code> can be used.
 * <p>
 *  <code> GraphicsEnvironment </code>类描述了特定平台上Java(tm)应用程序可用的{@link GraphicsDevice}对象和{@link javaawtFont}
 * 对象的集合此<code> GraphicsEnvironment </code >可以是本地的或在远程机器上<code> GraphicsDevice </code>对象可以是屏幕,打印机或图像缓冲区,
 * 并且是{@link Graphics2D}绘制方法的目标每个<code> GraphicsDevice </code> of {@link GraphicsConfiguration}与其相关联的对象这
 * 些对象指定可以使用<code> GraphicsDevice </code>的不同配置。
 * 
 * 
 * @see GraphicsDevice
 * @see GraphicsConfiguration
 */

public abstract class GraphicsEnvironment {
    private static GraphicsEnvironment localEnv;

    /**
     * The headless state of the Toolkit and GraphicsEnvironment
     * <p>
     * Toolkit和GraphicsEnvironment的无头状态
     * 
     */
    private static Boolean headless;

    /**
     * The headless state assumed by default
     * <p>
     *  默认情况下假定无头状态
     * 
     */
    private static Boolean defaultHeadless;

    /**
     * This is an abstract class and cannot be instantiated directly.
     * Instances must be obtained from a suitable factory or query method.
     * <p>
     *  这是一个抽象类,不能直接实例化实例必须从合适的工厂或查询方法获得
     * 
     */
    protected GraphicsEnvironment() {
    }

    /**
     * Returns the local <code>GraphicsEnvironment</code>.
     * <p>
     *  返回局部<code> GraphicsEnvironment </code>
     * 
     * 
     * @return the local <code>GraphicsEnvironment</code>
     */
    public static synchronized GraphicsEnvironment getLocalGraphicsEnvironment() {
        if (localEnv == null) {
            localEnv = createGE();
        }

        return localEnv;
    }

    /**
     * Creates and returns the GraphicsEnvironment, according to the
     * system property 'java.awt.graphicsenv'.
     *
     * <p>
     *  创建并返回GraphicsEnvironment,根据系统属性"javaawtgraphicsenv"
     * 
     * 
     * @return the graphics environment
     */
    private static GraphicsEnvironment createGE() {
        GraphicsEnvironment ge;
        String nm = AccessController.doPrivileged(new GetPropertyAction("java.awt.graphicsenv", null));
        try {
//          long t0 = System.currentTimeMillis();
            Class<GraphicsEnvironment> geCls;
            try {
                // First we try if the bootclassloader finds the requested
                // class. This way we can avoid to run in a privileged block.
                geCls = (Class<GraphicsEnvironment>)Class.forName(nm);
            } catch (ClassNotFoundException ex) {
                // If the bootclassloader fails, we try again with the
                // application classloader.
                ClassLoader cl = ClassLoader.getSystemClassLoader();
                geCls = (Class<GraphicsEnvironment>)Class.forName(nm, true, cl);
            }
            ge = geCls.newInstance();
//          long t1 = System.currentTimeMillis();
//          System.out.println("GE creation took " + (t1-t0)+ "ms.");
            if (isHeadless()) {
                ge = new HeadlessGraphicsEnvironment(ge);
            }
        } catch (ClassNotFoundException e) {
            throw new Error("Could not find class: "+nm);
        } catch (InstantiationException e) {
            throw new Error("Could not instantiate Graphics Environment: "
                            + nm);
        } catch (IllegalAccessException e) {
            throw new Error ("Could not access Graphics Environment: "
                             + nm);
        }
        return ge;
    }

    /**
     * Tests whether or not a display, keyboard, and mouse can be
     * supported in this environment.  If this method returns true,
     * a HeadlessException is thrown from areas of the Toolkit
     * and GraphicsEnvironment that are dependent on a display,
     * keyboard, or mouse.
     * <p>
     *  测试此环境中是否可以支持显示器,键盘和鼠标如果此方法返回true,则会从Toolkit和GraphicsEnvironment的依赖于显示器,键盘或鼠标的区域抛出HeadlessException
     * 
     * 
     * @return <code>true</code> if this environment cannot support
     * a display, keyboard, and mouse; <code>false</code>
     * otherwise
     * @see java.awt.HeadlessException
     * @since 1.4
     */
    public static boolean isHeadless() {
        return getHeadlessProperty();
    }

    /**
    /* <p>
    /* 
     * @return warning message if headless state is assumed by default;
     * null otherwise
     * @since 1.5
     */
    static String getHeadlessMessage() {
        if (headless == null) {
            getHeadlessProperty(); // initialize the values
        }
        return defaultHeadless != Boolean.TRUE ? null :
            "\nNo X11 DISPLAY variable was set, " +
            "but this program performed an operation which requires it.";
    }

    /**
    /* <p>
    /* 
     * @return the value of the property "java.awt.headless"
     * @since 1.4
     */
    private static boolean getHeadlessProperty() {
        if (headless == null) {
            java.security.AccessController.doPrivileged(
            new java.security.PrivilegedAction<Object>() {
                public Object run() {
                    String nm = System.getProperty("java.awt.headless");

                    if (nm == null) {
                        /* No need to ask for DISPLAY when run in a browser */
                        if (System.getProperty("javaplugin.version") != null) {
                            headless = defaultHeadless = Boolean.FALSE;
                        } else {
                            String osName = System.getProperty("os.name");
                            if (osName.contains("OS X") && "sun.awt.HToolkit".equals(
                                    System.getProperty("awt.toolkit")))
                            {
                                headless = defaultHeadless = Boolean.TRUE;
                            } else {
                                headless = defaultHeadless =
                                    Boolean.valueOf(("Linux".equals(osName) ||
                                                     "SunOS".equals(osName) ||
                                                     "FreeBSD".equals(osName) ||
                                                     "NetBSD".equals(osName) ||
                                                     "OpenBSD".equals(osName)) &&
                                                     (System.getenv("DISPLAY") == null));
                            }
                        }
                    } else if (nm.equals("true")) {
                        headless = Boolean.TRUE;
                    } else {
                        headless = Boolean.FALSE;
                    }
                    return null;
                }
                }
            );
        }
        return headless.booleanValue();
    }

    /**
     * Check for headless state and throw HeadlessException if headless
     * <p>
     *  检查无头状态,如果无头则抛出HeadlessException
     * 
     * 
     * @since 1.4
     */
    static void checkHeadless() throws HeadlessException {
        if (isHeadless()) {
            throw new HeadlessException();
        }
    }

    /**
     * Returns whether or not a display, keyboard, and mouse can be
     * supported in this graphics environment.  If this returns true,
     * <code>HeadlessException</code> will be thrown from areas of the
     * graphics environment that are dependent on a display, keyboard, or
     * mouse.
     * <p>
     * 返回此图形环境中是否可以支持显示器,键盘和鼠标。如果此操作返回true,则将从依赖于显示器,键盘或图形界面的图形环境区域抛出<code> HeadlessException </code>老鼠
     * 
     * 
     * @return <code>true</code> if a display, keyboard, and mouse
     * can be supported in this environment; <code>false</code>
     * otherwise
     * @see java.awt.HeadlessException
     * @see #isHeadless
     * @since 1.4
     */
    public boolean isHeadlessInstance() {
        // By default (local graphics environment), simply check the
        // headless property.
        return getHeadlessProperty();
    }

    /**
     * Returns an array of all of the screen <code>GraphicsDevice</code>
     * objects.
     * <p>
     *  返回所有屏幕<code> GraphicsDevice </code>对象的数组
     * 
     * 
     * @return an array containing all the <code>GraphicsDevice</code>
     * objects that represent screen devices
     * @exception HeadlessException if isHeadless() returns true
     * @see #isHeadless()
     */
    public abstract GraphicsDevice[] getScreenDevices()
        throws HeadlessException;

    /**
     * Returns the default screen <code>GraphicsDevice</code>.
     * <p>
     *  返回默认画面<code> GraphicsDevice </code>
     * 
     * 
     * @return the <code>GraphicsDevice</code> that represents the
     * default screen device
     * @exception HeadlessException if isHeadless() returns true
     * @see #isHeadless()
     */
    public abstract GraphicsDevice getDefaultScreenDevice()
        throws HeadlessException;

    /**
     * Returns a <code>Graphics2D</code> object for rendering into the
     * specified {@link BufferedImage}.
     * <p>
     *  返回一个<code> Graphics2D </code>对象,用于渲染到指定的{@link BufferedImage}
     * 
     * 
     * @param img the specified <code>BufferedImage</code>
     * @return a <code>Graphics2D</code> to be used for rendering into
     * the specified <code>BufferedImage</code>
     * @throws NullPointerException if <code>img</code> is null
     */
    public abstract Graphics2D createGraphics(BufferedImage img);

    /**
     * Returns an array containing a one-point size instance of all fonts
     * available in this <code>GraphicsEnvironment</code>.  Typical usage
     * would be to allow a user to select a particular font.  Then, the
     * application can size the font and set various font attributes by
     * calling the <code>deriveFont</code> method on the chosen instance.
     * <p>
     * This method provides for the application the most precise control
     * over which <code>Font</code> instance is used to render text.
     * If a font in this <code>GraphicsEnvironment</code> has multiple
     * programmable variations, only one
     * instance of that <code>Font</code> is returned in the array, and
     * other variations must be derived by the application.
     * <p>
     * If a font in this environment has multiple programmable variations,
     * such as Multiple-Master fonts, only one instance of that font is
     * returned in the <code>Font</code> array.  The other variations
     * must be derived by the application.
     *
     * <p>
     * 返回包含此<code> GraphicsEnvironment </code>中可用的所有字体的一点大小实例的数组。
     * 典型用法是允许用户选择特定字体然后,应用程序可以调整字体大小并设置各种字体属性通过调用所选实例上的<code> derivFont </code>方法。
     * <p>
     *  此方法为应用程序提供了对使用<code> Font </code>实例渲染文本的最精确控制。
     * 如果此<code> GraphicsEnvironment </code>中的字体有多个可编程变量,代码>字体</code>在数组中返回,并且其他变体必须由应用程序派生。
     * <p>
     * 如果此环境中的字体有多个可编程变量,例如多主字体,则在<code> Font </code>数组中只返回该字体的一个实例。其他变体必须由应用程序派生
     * 
     * 
     * @return an array of <code>Font</code> objects
     * @see #getAvailableFontFamilyNames
     * @see java.awt.Font
     * @see java.awt.Font#deriveFont
     * @see java.awt.Font#getFontName
     * @since 1.2
     */
    public abstract Font[] getAllFonts();

    /**
     * Returns an array containing the names of all font families in this
     * <code>GraphicsEnvironment</code> localized for the default locale,
     * as returned by <code>Locale.getDefault()</code>.
     * <p>
     * Typical usage would be for presentation to a user for selection of
     * a particular family name. An application can then specify this name
     * when creating a font, in conjunction with a style, such as bold or
     * italic, giving the font system flexibility in choosing its own best
     * match among multiple fonts in the same font family.
     *
     * <p>
     *  返回一个数组,该数组包含由<code> LocalegetDefault()返回的<code> GraphicsEnvironment </code>本地化默认语言环境中所有字体族的名称</code>
     * 。
     * <p>
     *  典型用法是向用户呈现用于选择特定姓氏。然后,应用可以在创建字体时结合诸如粗体或斜体的样式指定该名称,从而给予字体系统在选择其自己的最佳方面的灵活性匹配同一个字体系列中的多种字体
     * 
     * 
     * @return an array of <code>String</code> containing font family names
     * localized for the default locale, or a suitable alternative
     * name if no name exists for this locale.
     * @see #getAllFonts
     * @see java.awt.Font
     * @see java.awt.Font#getFamily
     * @since 1.2
     */
    public abstract String[] getAvailableFontFamilyNames();

    /**
     * Returns an array containing the names of all font families in this
     * <code>GraphicsEnvironment</code> localized for the specified locale.
     * <p>
     * Typical usage would be for presentation to a user for selection of
     * a particular family name. An application can then specify this name
     * when creating a font, in conjunction with a style, such as bold or
     * italic, giving the font system flexibility in choosing its own best
     * match among multiple fonts in the same font family.
     *
     * <p>
     * 返回一个数组,该数组包含指定语言环境的本地化<code> GraphicsEnvironment </code>中的所有字体系列的名称
     * <p>
     *  典型用法是向用户呈现用于选择特定姓氏。然后,应用可以在创建字体时结合诸如粗体或斜体的样式指定该名称,从而给予字体系统在选择其自己的最佳方面的灵活性匹配同一个字体系列中的多种字体
     * 
     * 
     * @param l a {@link Locale} object that represents a
     * particular geographical, political, or cultural region.
     * Specifying <code>null</code> is equivalent to
     * specifying <code>Locale.getDefault()</code>.
     * @return an array of <code>String</code> containing font family names
     * localized for the specified <code>Locale</code>, or a
     * suitable alternative name if no name exists for the specified locale.
     * @see #getAllFonts
     * @see java.awt.Font
     * @see java.awt.Font#getFamily
     * @since 1.2
     */
    public abstract String[] getAvailableFontFamilyNames(Locale l);

    /**
     * Registers a <i>created</i> <code>Font</code>in this
     * <code>GraphicsEnvironment</code>.
     * A created font is one that was returned from calling
     * {@link Font#createFont}, or derived from a created font by
     * calling {@link Font#deriveFont}.
     * After calling this method for such a font, it is available to
     * be used in constructing new <code>Font</code>s by name or family name,
     * and is enumerated by {@link #getAvailableFontFamilyNames} and
     * {@link #getAllFonts} within the execution context of this
     * application or applet. This means applets cannot register fonts in
     * a way that they are visible to other applets.
     * <p>
     * Reasons that this method might not register the font and therefore
     * return <code>false</code> are:
     * <ul>
     * <li>The font is not a <i>created</i> <code>Font</code>.
     * <li>The font conflicts with a non-created <code>Font</code> already
     * in this <code>GraphicsEnvironment</code>. For example if the name
     * is that of a system font, or a logical font as described in the
     * documentation of the {@link Font} class. It is implementation dependent
     * whether a font may also conflict if it has the same family name
     * as a system font.
     * <p>Notice that an application can supersede the registration
     * of an earlier created font with a new one.
     * </ul>
     * <p>
     * 在此<code> GraphicsEnvironment </code>中注册<i>创建的</i> <code> Font </code>创建的字体是从调用{@link Font#createFont}
     * 返回的字体,通过调用{@link Font#derivFont}创建字体调用此方法后,可用于通过名称或家族名构造新的<code> Font </code>,并由{@链接#getAvailableFontFamilyNames}
     * 和{@link #getAllFonts}在这个应用程序或applet的执行上下文这意味着applet不能注册字体,他们是可见的其他applet。
     * <p>
     *  此方法可能不注册字体,因此返回<code> false </code>的原因是：
     * <ul>
     * <li>字体不是<i>已创建</i> <code>字体</code> <li>字体与此<code> GraphicsEnvironment中已创建的<code> Font </code> </code>
     * 例如,如果名称是系统字体的名称,或者是{@link Font}类的文档中描述的逻辑字体,这取决于字体是否也可能冲突,如果它有相同的姓氏作为系统字体<p>请注意,应用程序可以取代使用新的字体注册早期创建的
     * 字体。
     * </ul>
     * 
     * @return true if the <code>font</code> is successfully
     * registered in this <code>GraphicsEnvironment</code>.
     * @throws NullPointerException if <code>font</code> is null
     * @since 1.6
     */
    public boolean registerFont(Font font) {
        if (font == null) {
            throw new NullPointerException("font cannot be null.");
        }
        FontManager fm = FontManagerFactory.getInstance();
        return fm.registerFont(font);
    }

    /**
     * Indicates a preference for locale-specific fonts in the mapping of
     * logical fonts to physical fonts. Calling this method indicates that font
     * rendering should primarily use fonts specific to the primary writing
     * system (the one indicated by the default encoding and the initial
     * default locale). For example, if the primary writing system is
     * Japanese, then characters should be rendered using a Japanese font
     * if possible, and other fonts should only be used for characters for
     * which the Japanese font doesn't have glyphs.
     * <p>
     * The actual change in font rendering behavior resulting from a call
     * to this method is implementation dependent; it may have no effect at
     * all, or the requested behavior may already match the default behavior.
     * The behavior may differ between font rendering in lightweight
     * and peered components.  Since calling this method requests a
     * different font, clients should expect different metrics, and may need
     * to recalculate window sizes and layout. Therefore this method should
     * be called before user interface initialisation.
     * <p>
     * 表示在将逻辑字体映射到物理字体时对区域设置特定字体的首选项调用此方法表示字体渲染应主要使用特定于主要写入系统(由默认编码和初始默认语言环境指示的字体)的字体。
     * 例如,如果主要写系统是日语,则字符应该使用日语字体呈现,如果可能的话,并且其他字体应该只用于日语字体没有字形的字符。
     * <p>
     * 调用此方法导致的字体呈现行为的实际更改是实现相关的;它可能没有任何效果,或者所请求的行为可能已经匹配默认行为。
     * 轻量级和对等组件中的字体渲染的行为可能不同由于调用此方法请求不同的字体,客户端应该期望不同的度量,并且可能需要重新计算窗口大小和布局因此,该方法应在用户界面初始化之前调用。
     * 
     * 
     * @since 1.5
     */
    public void preferLocaleFonts() {
        FontManager fm = FontManagerFactory.getInstance();
        fm.preferLocaleFonts();
    }

    /**
     * Indicates a preference for proportional over non-proportional (e.g.
     * dual-spaced CJK fonts) fonts in the mapping of logical fonts to
     * physical fonts. If the default mapping contains fonts for which
     * proportional and non-proportional variants exist, then calling
     * this method indicates the mapping should use a proportional variant.
     * <p>
     * The actual change in font rendering behavior resulting from a call to
     * this method is implementation dependent; it may have no effect at all.
     * The behavior may differ between font rendering in lightweight and
     * peered components. Since calling this method requests a
     * different font, clients should expect different metrics, and may need
     * to recalculate window sizes and layout. Therefore this method should
     * be called before user interface initialisation.
     * <p>
     * 表示在逻辑字体映射到物理字体中的比例超比非比例(例如双间隔CJK字体)字体的首选项如果默认映射包含存在比例和非比例变体的字体,则调用此方法指示映射应使用比例变量
     * <p>
     *  调用此方法导致的字体呈现行为的实际更改是实现相关的;它可能没有任何效果在轻量级和对等组件中的字体渲染之间的行为可能不同由于调用此方法请求不同的字体,客户端应该期望不同的度量,并可能需要重新计算窗口大小
     * 和布局因此,此方法应该之前调用用户界面初始化。
     * 
     * 
     * @since 1.5
     */
    public void preferProportionalFonts() {
        FontManager fm = FontManagerFactory.getInstance();
        fm.preferProportionalFonts();
    }

    /**
     * Returns the Point where Windows should be centered.
     * It is recommended that centered Windows be checked to ensure they fit
     * within the available display area using getMaximumWindowBounds().
     * <p>
     * 返回Windows应该居中的点建议使用getMaximumWindowBounds()来检查居中的Windows,以确保它们适合可用的显示区域。
     * 
     * 
     * @return the point where Windows should be centered
     *
     * @exception HeadlessException if isHeadless() returns true
     * @see #getMaximumWindowBounds
     * @since 1.4
     */
    public Point getCenterPoint() throws HeadlessException {
    // Default implementation: return the center of the usable bounds of the
    // default screen device.
        Rectangle usableBounds =
         SunGraphicsEnvironment.getUsableBounds(getDefaultScreenDevice());
        return new Point((usableBounds.width / 2) + usableBounds.x,
                         (usableBounds.height / 2) + usableBounds.y);
    }

    /**
     * Returns the maximum bounds for centered Windows.
     * These bounds account for objects in the native windowing system such as
     * task bars and menu bars.  The returned bounds will reside on a single
     * display with one exception: on multi-screen systems where Windows should
     * be centered across all displays, this method returns the bounds of the
     * entire display area.
     * <p>
     * To get the usable bounds of a single display, use
     * <code>GraphicsConfiguration.getBounds()</code> and
     * <code>Toolkit.getScreenInsets()</code>.
     * <p>
     *  返回中心窗口的最大边界这些边界考虑本地窗口系统中的对象(例如任务栏和菜单栏)返回的边界将驻留在单个显示器上,但有一个例外：在多屏幕系统上,Windows应在所有显示器,此方法返回整个显示区域的边界
     * <p>
     *  要获得单个显示的可用边界,使用<code> GraphicsConfigurationgetBounds()</code>和<code> ToolkitgetScreenInsets()</code>
     * 
     * @return  the maximum bounds for centered Windows
     *
     * @exception HeadlessException if isHeadless() returns true
     * @see #getCenterPoint
     * @see GraphicsConfiguration#getBounds
     * @see Toolkit#getScreenInsets
     * @since 1.4
     */
    public Rectangle getMaximumWindowBounds() throws HeadlessException {
    // Default implementation: return the usable bounds of the default screen
    // device.  This is correct for Microsoft Windows and non-Xinerama X11.
        return SunGraphicsEnvironment.getUsableBounds(getDefaultScreenDevice());
    }
}
