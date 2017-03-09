/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.plaf.metal;

import javax.swing.plaf.*;
import javax.swing.*;
import java.awt.*;

import sun.awt.AppContext;
import sun.security.action.GetPropertyAction;
import sun.swing.SwingUtilities2;

/**
 * A concrete implementation of {@code MetalTheme} providing
 * the original look of the Java Look and Feel, code-named "Steel". Refer
 * to {@link MetalLookAndFeel#setCurrentTheme} for details on changing
 * the default theme.
 * <p>
 * All colors returned by {@code DefaultMetalTheme} are completely
 * opaque.
 *
 * <h3><a name="fontStyle"></a>Font Style</h3>
 *
 * {@code DefaultMetalTheme} uses bold fonts for many controls.  To make all
 * controls (with the exception of the internal frame title bars and
 * client decorated frame title bars) use plain fonts you can do either of
 * the following:
 * <ul>
 * <li>Set the system property <code>swing.boldMetal</code> to
 *     <code>false</code>.  For example,
 *     <code>java&nbsp;-Dswing.boldMetal=false&nbsp;MyApp</code>.
 * <li>Set the defaults property <code>swing.boldMetal</code> to
 *     <code>Boolean.FALSE</code>.  For example:
 *     <code>UIManager.put("swing.boldMetal",&nbsp;Boolean.FALSE);</code>
 * </ul>
 * The defaults property <code>swing.boldMetal</code>, if set,
 * takes precedence over the system property of the same name. After
 * setting this defaults property you need to re-install
 * <code>MetalLookAndFeel</code>, as well as update the UI
 * of any previously created widgets. Otherwise the results are undefined.
 * The following illustrates how to do this:
 * <pre>
 *   // turn off bold fonts
 *   UIManager.put("swing.boldMetal", Boolean.FALSE);
 *
 *   // re-install the Metal Look and Feel
 *   UIManager.setLookAndFeel(new MetalLookAndFeel());
 *
 *   // Update the ComponentUIs for all Components. This
 *   // needs to be invoked for all windows.
 *   SwingUtilities.updateComponentTreeUI(rootComponent);
 * </pre>
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
 *  {@code MetalTheme}的具体实现提供了Java Look and Feel的原始外观,代码为"Steel"。
 * 有关更改默认主题的详细信息,请参阅{@link MetalLookAndFeel#setCurrentTheme}。
 * <p>
 *  {@code DefaultMetalTheme}返回的所有颜色都是完全不透明的。
 * 
 *  <h3> <a name="fontStyle"> </a>字体样式</h3>
 * 
 *  {@code DefaultMetalTheme}对许多控件使用粗体字体。要使所有控件(除了内部框架标题栏和客户端装饰的框架标题栏)使用纯字体,您可以执行以下操作之一：
 * <ul>
 *  <li>将系统属性<code> swing.boldMetal </code>设置为<code> false </code>。
 * 例如,<code> java&nbsp; -Dswing.boldMetal = false&nbsp; MyApp </code>。
 *  <li>将defaults属性<code> swing.boldMetal </code>设置为<code> Boolean.FALSE </code>。
 * 例如：<code> UIManager.put("swing.boldMetal",&amp;; Boolean.FALSE); </code>。
 * </ul>
 *  默认属性<code> swing.boldMetal </code>(如果设置)优先于同名的系统属性。
 * 设置此默认属性后,您需要重新安装<code> MetalLookAndFeel </code>,以及更新任何以前创建的小部件的UI。否则结果是未定义的。以下说明如何执行此操作：。
 * <pre>
 *  //关闭粗体字体UIManager.put("swing.boldMetal",Boolean.FALSE);
 * 
 * //重新安装Metal Look和Feel UIManager.setLookAndFeel(new MetalLookAndFeel());
 * 
 *  //更新所有组件的ComponentUI。这个//需要为所有窗口调用。 SwingUtilities.updateComponentTreeUI(rootComponent);
 * </pre>
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see MetalLookAndFeel
 * @see MetalLookAndFeel#setCurrentTheme
 *
 * @author Steve Wilson
 */
public class DefaultMetalTheme extends MetalTheme {
    /**
     * Whether or not fonts should be plain.  This is only used if
     * the defaults property 'swing.boldMetal' == "false".
     * <p>
     *  字体是否应该是平常的。这仅在defaults属性"swing.boldMetal"=="false"时使用。
     * 
     */
    private static final boolean PLAIN_FONTS;

    /**
     * Names of the fonts to use.
     * <p>
     *  要使用的字体的名称。
     * 
     */
    private static final String[] fontNames = {
        Font.DIALOG,Font.DIALOG,Font.DIALOG,Font.DIALOG,Font.DIALOG,Font.DIALOG
    };
    /**
     * Styles for the fonts.  This is ignored if the defaults property
     * <code>swing.boldMetal</code> is false, or PLAIN_FONTS is true.
     * <p>
     *  字体样式。如果defaults属性<code> swing.boldMetal </code>为false或PLAIN_FONTS为true,则会被忽略。
     * 
     */
    private static final int[] fontStyles = {
        Font.BOLD, Font.PLAIN, Font.PLAIN, Font.BOLD, Font.BOLD, Font.PLAIN
    };
    /**
     * Sizes for the fonts.
     * <p>
     *  字体大小。
     * 
     */
    private static final int[] fontSizes = {
        12, 12, 12, 12, 12, 10
    };

    // note the properties listed here can currently be used by people
    // providing runtimes to hint what fonts are good.  For example the bold
    // dialog font looks bad on a Mac, so Apple could use this property to
    // hint at a good font.
    //
    // However, we don't promise to support these forever.  We may move
    // to getting these from the swing.properties file, or elsewhere.
    /**
     * System property names used to look up fonts.
     * <p>
     *  用于查找字体的系统属性名称。
     * 
     */
    private static final String[] defaultNames = {
        "swing.plaf.metal.controlFont",
        "swing.plaf.metal.systemFont",
        "swing.plaf.metal.userFont",
        "swing.plaf.metal.controlFont",
        "swing.plaf.metal.controlFont",
        "swing.plaf.metal.smallFont"
    };

    /**
     * Returns the ideal font name for the font identified by key.
     * <p>
     *  返回由键标识的字体的理想字体名称。
     * 
     */
    static String getDefaultFontName(int key) {
        return fontNames[key];
    }

    /**
     * Returns the ideal font size for the font identified by key.
     * <p>
     *  返回由键标识的字体的理想字体大小。
     * 
     */
    static int getDefaultFontSize(int key) {
        return fontSizes[key];
    }

    /**
     * Returns the ideal font style for the font identified by key.
     * <p>
     *  返回由键标识的字体的理想字体样式。
     * 
     */
    static int getDefaultFontStyle(int key) {
        if (key != WINDOW_TITLE_FONT) {
            Object boldMetal = null;
            if (AppContext.getAppContext().get(
                    SwingUtilities2.LAF_STATE_KEY) != null) {
                // Only access the boldMetal key if a look and feel has
                // been loaded, otherwise we'll trigger loading the look
                // and feel.
                boldMetal = UIManager.get("swing.boldMetal");
            }
            if (boldMetal != null) {
                if (Boolean.FALSE.equals(boldMetal)) {
                    return Font.PLAIN;
                }
            }
            else if (PLAIN_FONTS) {
                return Font.PLAIN;
            }
        }
        return fontStyles[key];
    }

    /**
     * Returns the default used to look up the specified font.
     * <p>
     *  返回用于查找指定字体的默认值。
     * 
     */
    static String getDefaultPropertyName(int key) {
        return defaultNames[key];
    }

    static {
        Object boldProperty = java.security.AccessController.doPrivileged(
            new GetPropertyAction("swing.boldMetal"));
        if (boldProperty == null || !"false".equals(boldProperty)) {
            PLAIN_FONTS = false;
        }
        else {
            PLAIN_FONTS = true;
        }
    }

    private static final ColorUIResource primary1 = new ColorUIResource(
                              102, 102, 153);
    private static final ColorUIResource primary2 = new ColorUIResource(153,
                              153, 204);
    private static final ColorUIResource primary3 = new ColorUIResource(
                              204, 204, 255);
    private static final ColorUIResource secondary1 = new ColorUIResource(
                              102, 102, 102);
    private static final ColorUIResource secondary2 = new ColorUIResource(
                              153, 153, 153);
    private static final ColorUIResource secondary3 = new ColorUIResource(
                              204, 204, 204);

    private FontDelegate fontDelegate;

    /**
     * Returns the name of this theme. This returns {@code "Steel"}.
     *
     * <p>
     *  返回此主题的名称。这将返回{@code"Steel"}。
     * 
     * 
     * @return the name of this theme.
     */
    public String getName() { return "Steel"; }

    /**
     * Creates and returns an instance of {@code DefaultMetalTheme}.
     * <p>
     *  创建并返回{@code DefaultMetalTheme}的实例。
     * 
     */
    public DefaultMetalTheme() {
        install();
    }

    /**
     * Returns the primary 1 color. This returns a color with rgb values
     * of 102, 102, and 153, respectively.
     *
     * <p>
     *  返回主1颜色。这将返回一个颜色,分别为rgb值102,102和153。
     * 
     * 
     * @return the primary 1 color
     */
    protected ColorUIResource getPrimary1() { return primary1; }

    /**
     * Returns the primary 2 color. This returns a color with rgb values
     * of 153, 153, 204, respectively.
     *
     * <p>
     * 返回主2颜色。这将返回一个颜色,rgb值分别为153,153,204。
     * 
     * 
     * @return the primary 2 color
     */
    protected ColorUIResource getPrimary2() { return primary2; }

    /**
     * Returns the primary 3 color. This returns a color with rgb values
     * 204, 204, 255, respectively.
     *
     * <p>
     *  返回主3颜色。这返回分别具有rgb值204,204,255的颜色。
     * 
     * 
     * @return the primary 3 color
     */
    protected ColorUIResource getPrimary3() { return primary3; }

    /**
     * Returns the secondary 1 color. This returns a color with rgb values
     * 102, 102, and 102, respectively.
     *
     * <p>
     *  返回辅助1颜色。这返回分别具有rgb值102,102和102的颜色。
     * 
     * 
     * @return the secondary 1 color
     */
    protected ColorUIResource getSecondary1() { return secondary1; }

    /**
     * Returns the secondary 2 color. This returns a color with rgb values
     * 153, 153, and 153, respectively.
     *
     * <p>
     *  返回辅助2颜色。这将返回一个颜色,分别为rgb值153,153和153。
     * 
     * 
     * @return the secondary 2 color
     */
    protected ColorUIResource getSecondary2() { return secondary2; }

    /**
     * Returns the secondary 3 color. This returns a color with rgb values
     * 204, 204, and 204, respectively.
     *
     * <p>
     *  返回辅助3颜色。这返回分别具有rgb值204,204和204的颜色。
     * 
     * 
     * @return the secondary 3 color
     */
    protected ColorUIResource getSecondary3() { return secondary3; }


    /**
     * Returns the control text font. This returns Dialog, 12pt. If
     * plain fonts have been enabled as described in <a href="#fontStyle">
     * font style</a>, the font style is plain. Otherwise the font style is
     * bold.
     *
     * <p>
     *  返回控件文本字体。这返回Dialog,12pt。如果已按<a href="#fontStyle">字体样式</a>中所述启用纯字体,则字体样式是纯的。否则字体样式为粗体。
     * 
     * 
     * @return the control text font
     */
    public FontUIResource getControlTextFont() {
        return getFont(CONTROL_TEXT_FONT);
    }

    /**
     * Returns the system text font. This returns Dialog, 12pt, plain.
     *
     * <p>
     *  返回系统文本字体。这返回Dialog,12pt,plain。
     * 
     * 
     * @return the system text font
     */
    public FontUIResource getSystemTextFont() {
        return getFont(SYSTEM_TEXT_FONT);
    }

    /**
     * Returns the user text font. This returns Dialog, 12pt, plain.
     *
     * <p>
     *  返回用户文本字体。这返回Dialog,12pt,plain。
     * 
     * 
     * @return the user text font
     */
    public FontUIResource getUserTextFont() {
        return getFont(USER_TEXT_FONT);
    }

    /**
     * Returns the menu text font. This returns Dialog, 12pt. If
     * plain fonts have been enabled as described in <a href="#fontStyle">
     * font style</a>, the font style is plain. Otherwise the font style is
     * bold.
     *
     * <p>
     *  返回菜单文本字体。这返回Dialog,12pt。如果已按<a href="#fontStyle">字体样式</a>中所述启用纯字体,则字体样式是纯的。否则字体样式为粗体。
     * 
     * 
     * @return the menu text font
     */
    public FontUIResource getMenuTextFont() {
        return getFont(MENU_TEXT_FONT);
    }

    /**
     * Returns the window title font. This returns Dialog, 12pt, bold.
     *
     * <p>
     *  返回窗口标题字体。这返回Dialog,12pt,bold。
     * 
     * 
     * @return the window title font
     */
    public FontUIResource getWindowTitleFont() {
        return getFont(WINDOW_TITLE_FONT);
    }

    /**
     * Returns the sub-text font. This returns Dialog, 10pt, plain.
     *
     * <p>
     *  返回子文本字体。这返回Dialog,10pt,plain。
     * 
     * 
     * @return the sub-text font
     */
    public FontUIResource getSubTextFont() {
        return getFont(SUB_TEXT_FONT);
    }

    private FontUIResource getFont(int key) {
        return fontDelegate.getFont(key);
    }

    void install() {
        if (MetalLookAndFeel.isWindows() &&
                             MetalLookAndFeel.useSystemFonts()) {
            fontDelegate = new WindowsFontDelegate();
        }
        else {
            fontDelegate = new FontDelegate();
        }
    }

    /**
     * Returns true if this is a theme provided by the core platform.
     * <p>
     *  如果这是核心平台提供的主题,则返回true。
     * 
     */
    boolean isSystemTheme() {
        return (getClass() == DefaultMetalTheme.class);
    }

    /**
     * FontDelegates add an extra level of indirection to obtaining fonts.
     * <p>
     *  FontDelegates添加额外级别的间接获取字体。
     * 
     */
    private static class FontDelegate {
        private static int[] defaultMapping = {
            CONTROL_TEXT_FONT, SYSTEM_TEXT_FONT,
            USER_TEXT_FONT, CONTROL_TEXT_FONT,
            CONTROL_TEXT_FONT, SUB_TEXT_FONT
        };
        FontUIResource fonts[];

        // menu and window are mapped to controlFont
        public FontDelegate() {
            fonts = new FontUIResource[6];
        }

        public FontUIResource getFont(int type) {
            int mappedType = defaultMapping[type];
            if (fonts[type] == null) {
                Font f = getPrivilegedFont(mappedType);

                if (f == null) {
                    f = new Font(getDefaultFontName(type),
                             getDefaultFontStyle(type),
                             getDefaultFontSize(type));
                }
                fonts[type] = new FontUIResource(f);
            }
            return fonts[type];
        }

        /**
         * This is the same as invoking
         * <code>Font.getFont(key)</code>, with the exception
         * that it is wrapped inside a <code>doPrivileged</code> call.
         * <p>
         *  这与调用<code> Font.getFont(key)</code>相同,只是它被包装在<code> doPrivileged </code>调用中。
         * 
         */
        protected Font getPrivilegedFont(final int key) {
            return java.security.AccessController.doPrivileged(
                new java.security.PrivilegedAction<Font>() {
                    public Font run() {
                        return Font.getFont(getDefaultPropertyName(key));
                    }
                }
                );
        }
    }

    /**
     * The WindowsFontDelegate uses DesktopProperties to obtain fonts.
     * <p>
     * WindowsFontDelegate使用DesktopProperties来获取字体。
     */
    private static class WindowsFontDelegate extends FontDelegate {
        private MetalFontDesktopProperty[] props;
        private boolean[] checkedPriviledged;

        public WindowsFontDelegate() {
            props = new MetalFontDesktopProperty[6];
            checkedPriviledged = new boolean[6];
        }

        public FontUIResource getFont(int type) {
            if (fonts[type] != null) {
                return fonts[type];
            }
            if (!checkedPriviledged[type]) {
                Font f = getPrivilegedFont(type);

                checkedPriviledged[type] = true;
                if (f != null) {
                    fonts[type] = new FontUIResource(f);
                    return fonts[type];
                }
            }
            if (props[type] == null) {
                props[type] = new MetalFontDesktopProperty(type);
            }
            // While passing null may seem bad, we don't actually use
            // the table and looking it up is rather expensive.
            return (FontUIResource)props[type].createValue(null);
        }
    }
}
