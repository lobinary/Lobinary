/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2010, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.java.swing.plaf.gtk;

import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.plaf.FontUIResource;
import java.util.StringTokenizer;

import sun.font.FontConfigManager;
import sun.font.FontUtilities;

/**
/* <p>
/* 
 * @author Shannon Hickey
 * @author Leif Samuelsson
 */
class PangoFonts {

    public static final String CHARS_DIGITS = "0123456789";

    /**
     * Calculate a default scale factor for fonts in this L&F to match
     * the reported resolution of the screen.
     * Java 2D specified a default user-space scale of 72dpi.
     * This is unlikely to correspond to that of the real screen.
     * The Xserver reports a value which may be used to adjust for this.
     * and Java 2D exposes it via a normalizing transform.
     * However many Xservers report a hard-coded 90dpi whilst others report a
     * calculated value based on possibly incorrect data.
     * That is something that must be solved at the X11 level
     * Note that in an X11 multi-screen environment, the default screen
     * is the one used by the JRE so it is safe to use it here.
     * <p>
     *  计算此L&F中字体的默认比例因子,以匹配报告的屏幕分辨率。 Java 2D指定了72dpi的默认用户空间比例。这不太可能对应于真实屏幕的。 Xserver报告可用于调整此值的值。
     * 并且Java 2D通过归一化变换来曝光它。然而,许多Xserver报告硬编码的90dpi,而其他报告基于可能不正确的数据的计算值。
     * 这是必须在X11级别解决的事情请注意,在X11多屏幕环境中,默认屏幕是JRE使用的屏幕,因此在这里使用它是安全的。
     * 
     */
    private static double fontScale;

    static {
        fontScale = 1.0d;
        GraphicsEnvironment ge =
           GraphicsEnvironment.getLocalGraphicsEnvironment();

        if (!ge.isHeadless()) {
            GraphicsConfiguration gc =
                ge.getDefaultScreenDevice().getDefaultConfiguration();
            AffineTransform at = gc.getNormalizingTransform();
            fontScale = at.getScaleY();
        }
    }


    /**
     * Parses a String containing a pango font description and returns
     * a Font object.
     *
     * <p>
     *  解析一个包含pango字体描述的字符串,并返回一个Font对象。
     * 
     * 
     * @param pangoName a String describing a pango font
     *                  e.g. "Sans Italic 10"
     * @return a Font object as a FontUIResource
     *         or null if no suitable font could be created.
     */
    static Font lookupFont(String pangoName) {
        String family = "";
        int style = Font.PLAIN;
        int size = 10;

        StringTokenizer tok = new StringTokenizer(pangoName);

        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            if (word.equalsIgnoreCase("italic")) {
                style |= Font.ITALIC;
            } else if (word.equalsIgnoreCase("bold")) {
                style |= Font.BOLD;
            } else if (CHARS_DIGITS.indexOf(word.charAt(0)) != -1) {
                try {
                    size = Integer.parseInt(word);
                } catch (NumberFormatException ex) {
                }
            } else {
                if (family.length() > 0) {
                    family += " ";
                }

                family += word;
            }
        }

        /*
         * Java 2D font point sizes are in a user-space scale of 72dpi.
         * GTK allows a user to configure a "dpi" property used to scale
         * the fonts used to match a user's preference.
         * To match the font size of GTK apps we need to obtain this DPI and
         * adjust as follows:
         * Some versions of GTK use XSETTINGS if available to dynamically
         * monitor user-initiated changes in the DPI to be used by GTK
         * apps. This value is also made available as the Xft.dpi X resource.
         * This is presumably a function of the font preferences API and/or
         * the manner in which it requests the toolkit to update the default
         * for the desktop. This dual approach is probably necessary since
         * other versions of GTK - or perhaps some apps - determine the size
         * to use only at start-up from that X resource.
         * If that resource is not set then GTK scales for the DPI resolution
         * reported by the Xserver using the formula
         * DisplayHeight(dpy, screen) / DisplayHeightMM(dpy, screen) * 25.4
         * (25.4mm == 1 inch).
         * JDK tracks the Xft.dpi XSETTINGS property directly so it can
         * dynamically change font size by tracking just that value.
         * If that resource is not available use the same fall back formula
         * as GTK (see calculation for fontScale).
         *
         * GTK's default setting for Xft.dpi is 96 dpi (and it seems -1
         * apparently also can mean that "default"). However this default
         * isn't used if there's no property set. The real default in the
         * absence of a resource is the Xserver reported dpi.
         * Finally this DPI is used to calculate the nearest Java 2D font
         * 72 dpi font size.
         * There are cases in which JDK behaviour may not exactly mimic
         * GTK native app behaviour :
         * 1) When a GTK app is not able to dynamically track the changes
         * (does not use XSETTINGS), JDK will resize but other apps will
         * not. This is OK as JDK is exhibiting preferred behaviour and
         * this is probably how all later GTK apps will behave
         * 2) When a GTK app does not use XSETTINGS and for some reason
         * the XRDB property is not present. JDK will pick up XSETTINGS
         * and the GTK app will use the Xserver default. Since its
         * impossible for JDK to know that some other GTK app is not
         * using XSETTINGS its impossible to account for this and in any
         * case for it to be a problem the values would have to be different.
         * It also seems unlikely to arise except when a user explicitly
         * deletes the X resource database entry.
         * There also some other issues to be aware of for the future:
         * GTK specifies the Xft.dpi value as server-wide which when used
         * on systems with 2 distinct X screens with different physical DPI
         * the font sizes will inevitably appear different. It would have
         * been a more user-friendly design to further adjust that one
         * setting depending on the screen resolution to achieve perceived
         * equivalent sizes. If such a change were ever to be made in GTK
         * we would need to update for that.
         * <p>
         * Java 2D字体点大小在72dpi的用户空间比例。 GTK允许用户配置"dpi"属性,用于缩放用于匹配用户首选项的字体。
         * 为了匹配GTK应用程序的字体大小,我们需要获得这个DPI并进行如下调整：一些版本的GTK使用XSETTINGS(如果可用)动态监视用户发起的DPI更改以供GTK应用程序使用。
         * 此值还可用作Xft.dpi X资源。这可能是字体偏好API的函数和/或它请求工具箱更新桌面的默认值的方式。
         * 这种双重方法可能是必要的,因为GTK的其他版本或者一些应用程序决定了只有在启动时使用X资源才能使用的大小。
         * 如果未设置该资源,则GTK使用公式DisplayHeight(dpy,screen)/ DisplayHeightMM(dpy,screen)* 25.4(25.4mm == 1英寸)缩放Xserver
         * 报告的DPI分辨率。
         * 这种双重方法可能是必要的,因为GTK的其他版本或者一些应用程序决定了只有在启动时使用X资源才能使用的大小。
         *  JDK直接跟踪Xft.dpi XSETTINGS属性,因此它可以通过跟踪该值来动态地更改字体大小。如果该资源不可用,则使用与GTK相同的后退公式(请参阅fontScale的计算)。
         * 
         * GTK的Xft.dpi的默认设置是96 dpi(似乎-1显然也可以意味着"默认")。但是,如果没有设置属性,则不使用此默认值。缺少资源时的真实默认值是Xserver报告的dpi。
         * 最后,该DPI用于计算最近的Java 2D字体72 dpi字体大小。
         * 有些情况下,JDK行为可能不会完全模仿GTK本机应用程序行为：1)当GTK应用程序无法动态跟踪更改(不使用XSETTINGS)时,JDK将调整大小,但其他应用程序不会。
         * 这是确定的,因为JDK展示了首选行为,这可能是所有后来的GTK应用程序的行为2)当一个GTK应用程序不使用XSETTINGS,由于某种原因XRDB属性不存在。
         *  JDK将选择XSETTINGS,GTK应用程序将使用Xserver默认值。
         */
        double dsize = size;
        int dpi = 96;
        Object value =
            Toolkit.getDefaultToolkit().getDesktopProperty("gnome.Xft/DPI");
        if (value instanceof Integer) {
            dpi = ((Integer)value).intValue() / 1024;
            if (dpi == -1) {
              dpi = 96;
            }
            if (dpi < 50) { /* 50 dpi is the minimum value gnome allows */
                dpi = 50;
            }
            /* The Java rasteriser assumes pts are in a user space of
             * 72 dpi, so we need to adjust for that.
             * <p>
             * 因为JDK不可能知道一些其他GTK应用程序不使用XSETTINGS它不可能解释这一点,在任何情况下,它是一个问题的值将不得不不同。除了当用户显式删除X资源数据库条目时,它似乎也不太可能出现。
             * 还有一些其他问题需要注意的未来：GTK指定Xft.dpi值作为服务器范围,当用于具有2个不同的X屏幕的不同物理DPI的系统时,字体大小将不可避免地显示不同。
             * 这将是一个更加用户友好的设计,以进一步调整一个设置取决于屏幕分辨率,以实现感知等效尺寸。如果这样的变化曾经在GTK中进行,我们将需要更新。
             * 
             */
            dsize = ((double)(dpi * size)/ 72.0);
        } else {
            /* If there's no property, GTK scales for the resolution
             * reported by the Xserver using the formula listed above.
             * fontScale already accounts for the 72 dpi Java 2D space.
             * <p>
             * 72 dpi,因此我们需要调整。
             * 
             */
            dsize = size * fontScale;
        }

        /* Round size to nearest integer pt size */
        size = (int)(dsize + 0.5);
        if (size < 1) {
            size = 1;
        }

        String fcFamilyLC = family.toLowerCase();
        if (FontUtilities.mapFcName(fcFamilyLC) != null) {
            /* family is a Fc/Pango logical font which we need to expand. */
            Font font =  FontUtilities.getFontConfigFUIR(fcFamilyLC, style, size);
            font = font.deriveFont(style, (float)dsize);
            return new FontUIResource(font);
        } else {
            /* It's a physical font which we will create with a fallback */
            Font font = new Font(family, style, size);
            /* a roundabout way to set the font size in floating points */
            font = font.deriveFont(style, (float)dsize);
            FontUIResource fuir = new FontUIResource(font);
            return FontUtilities.getCompositeFontUIResource(fuir);
        }
    }

    /**
     * Parses a String containing a pango font description and returns
     * the (unscaled) font size as an integer.
     *
     * <p>
     *  由Xserver使用上面列出的公式报告。 fontScale已经占用了72 dpi的Java 2D空间。
     * 
     * 
     * @param pangoName a String describing a pango font
     * @return the size of the font described by pangoName (e.g. if
     *         pangoName is "Sans Italic 10", then this method returns 10)
     */
    static int getFontSize(String pangoName) {
        int size = 10;

        StringTokenizer tok = new StringTokenizer(pangoName);
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            if (CHARS_DIGITS.indexOf(word.charAt(0)) != -1) {
                try {
                    size = Integer.parseInt(word);
                } catch (NumberFormatException ex) {
                }
            }
        }

        return size;
    }
}
