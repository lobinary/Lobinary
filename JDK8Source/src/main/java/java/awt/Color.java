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

import java.beans.ConstructorProperties;
import java.awt.image.ColorModel;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.color.ColorSpace;

/**
 * The <code>Color</code> class is used to encapsulate colors in the default
 * sRGB color space or colors in arbitrary color spaces identified by a
 * {@link ColorSpace}.  Every color has an implicit alpha value of 1.0 or
 * an explicit one provided in the constructor.  The alpha value
 * defines the transparency of a color and can be represented by
 * a float value in the range 0.0&nbsp;-&nbsp;1.0 or 0&nbsp;-&nbsp;255.
 * An alpha value of 1.0 or 255 means that the color is completely
 * opaque and an alpha value of 0 or 0.0 means that the color is
 * completely transparent.
 * When constructing a <code>Color</code> with an explicit alpha or
 * getting the color/alpha components of a <code>Color</code>, the color
 * components are never premultiplied by the alpha component.
 * <p>
 * The default color space for the Java 2D(tm) API is sRGB, a proposed
 * standard RGB color space.  For further information on sRGB,
 * see <A href="http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html">
 * http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html
 * </A>.
 * <p>
 * <p>
 *  <code> Color </code>类用于封装默认sRGB颜色空间中的颜色或由{@link ColorSpace}标识的任意颜色空间中的颜色。
 * 每个颜色都有一个1.0的隐式alpha值,或者在构造函数中提供一个显式的值。
 *  Alpha值定义颜色的透明度,可以通过范围为0.0&nbsp;  - &nbsp; 1.0或0&nbsp;  - &nbsp; 255中的浮点值来表示。
 *  alpha值为1.0或255表示颜色完全不透明,alpha值为0或0.0表示颜色完全透明。
 * 当使用显式alpha或获取<code> Color </code>的颜色/ alpha组件构造<code> Color </code>时,颜色分量决不会被alpha分量预乘。
 * <p>
 *  Java 2D(tm)API的默认颜色空间是sRGB,一个建议的标准RGB颜色空间。
 * 有关sRGB的详情,请参阅<A href="http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html"> http://www.w3.org/pub/WWW/
 * Graphics /Color/sRGB.html </A>。
 *  Java 2D(tm)API的默认颜色空间是sRGB,一个建议的标准RGB颜色空间。
 * <p>
 * 
 * @version     10 Feb 1997
 * @author      Sami Shaio
 * @author      Arthur van Hoff
 * @see         ColorSpace
 * @see         AlphaComposite
 */
public class Color implements Paint, java.io.Serializable {

    /**
     * The color white.  In the default sRGB space.
     * <p>
     *  颜色白色。在默认的sRGB空间。
     * 
     */
    public final static Color white     = new Color(255, 255, 255);

    /**
     * The color white.  In the default sRGB space.
     * <p>
     *  颜色白色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color WHITE = white;

    /**
     * The color light gray.  In the default sRGB space.
     * <p>
     *  颜色浅灰色。在默认的sRGB空间。
     * 
     */
    public final static Color lightGray = new Color(192, 192, 192);

    /**
     * The color light gray.  In the default sRGB space.
     * <p>
     *  颜色浅灰色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color LIGHT_GRAY = lightGray;

    /**
     * The color gray.  In the default sRGB space.
     * <p>
     *  颜色灰色。在默认的sRGB空间。
     * 
     */
    public final static Color gray      = new Color(128, 128, 128);

    /**
     * The color gray.  In the default sRGB space.
     * <p>
     *  颜色灰色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color GRAY = gray;

    /**
     * The color dark gray.  In the default sRGB space.
     * <p>
     *  颜色深灰色。在默认的sRGB空间。
     * 
     */
    public final static Color darkGray  = new Color(64, 64, 64);

    /**
     * The color dark gray.  In the default sRGB space.
     * <p>
     * 颜色深灰色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color DARK_GRAY = darkGray;

    /**
     * The color black.  In the default sRGB space.
     * <p>
     *  颜色黑色。在默认的sRGB空间。
     * 
     */
    public final static Color black     = new Color(0, 0, 0);

    /**
     * The color black.  In the default sRGB space.
     * <p>
     *  颜色黑色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color BLACK = black;

    /**
     * The color red.  In the default sRGB space.
     * <p>
     *  颜色红色。在默认的sRGB空间。
     * 
     */
    public final static Color red       = new Color(255, 0, 0);

    /**
     * The color red.  In the default sRGB space.
     * <p>
     *  颜色红色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color RED = red;

    /**
     * The color pink.  In the default sRGB space.
     * <p>
     *  颜色粉红色。在默认的sRGB空间。
     * 
     */
    public final static Color pink      = new Color(255, 175, 175);

    /**
     * The color pink.  In the default sRGB space.
     * <p>
     *  颜色粉红色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color PINK = pink;

    /**
     * The color orange.  In the default sRGB space.
     * <p>
     *  颜色橙色。在默认的sRGB空间。
     * 
     */
    public final static Color orange    = new Color(255, 200, 0);

    /**
     * The color orange.  In the default sRGB space.
     * <p>
     *  颜色橙色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color ORANGE = orange;

    /**
     * The color yellow.  In the default sRGB space.
     * <p>
     *  颜色黄色。在默认的sRGB空间。
     * 
     */
    public final static Color yellow    = new Color(255, 255, 0);

    /**
     * The color yellow.  In the default sRGB space.
     * <p>
     *  颜色黄色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color YELLOW = yellow;

    /**
     * The color green.  In the default sRGB space.
     * <p>
     *  颜色绿色。在默认的sRGB空间。
     * 
     */
    public final static Color green     = new Color(0, 255, 0);

    /**
     * The color green.  In the default sRGB space.
     * <p>
     *  颜色绿色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color GREEN = green;

    /**
     * The color magenta.  In the default sRGB space.
     * <p>
     *  颜色品红色。在默认的sRGB空间。
     * 
     */
    public final static Color magenta   = new Color(255, 0, 255);

    /**
     * The color magenta.  In the default sRGB space.
     * <p>
     *  颜色品红色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color MAGENTA = magenta;

    /**
     * The color cyan.  In the default sRGB space.
     * <p>
     *  颜色青色。在默认的sRGB空间。
     * 
     */
    public final static Color cyan      = new Color(0, 255, 255);

    /**
     * The color cyan.  In the default sRGB space.
     * <p>
     *  颜色青色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color CYAN = cyan;

    /**
     * The color blue.  In the default sRGB space.
     * <p>
     *  颜色蓝色。在默认的sRGB空间。
     * 
     */
    public final static Color blue      = new Color(0, 0, 255);

    /**
     * The color blue.  In the default sRGB space.
     * <p>
     *  颜色蓝色。在默认的sRGB空间。
     * 
     * 
     * @since 1.4
     */
    public final static Color BLUE = blue;

    /**
     * The color value.
     * <p>
     *  颜色值。
     * 
     * 
     * @serial
     * @see #getRGB
     */
    int value;

    /**
     * The color value in the default sRGB <code>ColorSpace</code> as
     * <code>float</code> components (no alpha).
     * If <code>null</code> after object construction, this must be an
     * sRGB color constructed with 8-bit precision, so compute from the
     * <code>int</code> color value.
     * <p>
     *  默认sRGB <code> ColorSpace </code>中的颜色值为<code> float </code>组件(无alpha)。
     * 如果在对象构造之后<code> null </code>,这必须是用8位精度构造的sRGB颜色,因此从<code> int </code>颜色值计算。
     * 
     * 
     * @serial
     * @see #getRGBColorComponents
     * @see #getRGBComponents
     */
    private float frgbvalue[] = null;

    /**
     * The color value in the native <code>ColorSpace</code> as
     * <code>float</code> components (no alpha).
     * If <code>null</code> after object construction, this must be an
     * sRGB color constructed with 8-bit precision, so compute from the
     * <code>int</code> color value.
     * <p>
     *  原生<code> ColorSpace </code>中的颜色值为<code> float </code>组件(无alpha)。
     * 如果在对象构造之后<code> null </code>,这必须是用8位精度构造的sRGB颜色,因此从<code> int </code>颜色值计算。
     * 
     * 
     * @serial
     * @see #getRGBColorComponents
     * @see #getRGBComponents
     */
    private float fvalue[] = null;

    /**
     * The alpha value as a <code>float</code> component.
     * If <code>frgbvalue</code> is <code>null</code>, this is not valid
     * data, so compute from the <code>int</code> color value.
     * <p>
     * alpha值作为<code> float </code>组件。
     * 如果<code> frgbvalue </code>是<code> null </code>,则这不是有效的数据,因此从<code> int </code>颜色值计算。
     * 
     * 
     * @serial
     * @see #getRGBComponents
     * @see #getComponents
     */
    private float falpha = 0.0f;

    /**
     * The <code>ColorSpace</code>.  If <code>null</code>, then it's
     * default is sRGB.
     * <p>
     *  <code> ColorSpace </code>。如果<code> null </code>,那么它的默认值为sRGB。
     * 
     * 
     * @serial
     * @see #getColor
     * @see #getColorSpace
     * @see #getColorComponents
     */
    private ColorSpace cs = null;

    /*
     * JDK 1.1 serialVersionUID
     * <p>
     *  JDK 1.1 serialVersionUID
     * 
     */
     private static final long serialVersionUID = 118526816881161077L;

    /**
     * Initialize JNI field and method IDs
     * <p>
     *  初始化JNI字段和方法ID
     * 
     */
    private static native void initIDs();

    static {
        /** 4112352 - Calling getDefaultToolkit()
         ** here can cause this class to be accessed before it is fully
         ** initialized. DON'T DO IT!!!
         **
         ** Toolkit.getDefaultToolkit();
         * <p>
         *  这里可以导致这个类在被完全初始化之前被访问。不要这样做！
         * 
         *  Toolkit.getDefaultToolkit();
         * 
         * 
         **/

        /* ensure that the necessary native libraries are loaded */
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
    }

    /**
     * Checks the color integer components supplied for validity.
     * Throws an {@link IllegalArgumentException} if the value is out of
     * range.
     * <p>
     *  检查为有效性提供的颜色整数分量。如果值超出范围,则抛出{@link IllegalArgumentException}。
     * 
     * 
     * @param r the Red component
     * @param g the Green component
     * @param b the Blue component
     **/
    private static void testColorValueRange(int r, int g, int b, int a) {
        boolean rangeError = false;
        String badComponentString = "";

        if ( a < 0 || a > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Alpha";
        }
        if ( r < 0 || r > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Red";
        }
        if ( g < 0 || g > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Green";
        }
        if ( b < 0 || b > 255) {
            rangeError = true;
            badComponentString = badComponentString + " Blue";
        }
        if ( rangeError == true ) {
        throw new IllegalArgumentException("Color parameter outside of expected range:"
                                           + badComponentString);
        }
    }

    /**
     * Checks the color <code>float</code> components supplied for
     * validity.
     * Throws an <code>IllegalArgumentException</code> if the value is out
     * of range.
     * <p>
     *  检查为有效性提供的颜色<code> float </code>。如果值超出范围,则抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param r the Red component
     * @param g the Green component
     * @param b the Blue component
     **/
    private static void testColorValueRange(float r, float g, float b, float a) {
        boolean rangeError = false;
        String badComponentString = "";
        if ( a < 0.0 || a > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Alpha";
        }
        if ( r < 0.0 || r > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Red";
        }
        if ( g < 0.0 || g > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Green";
        }
        if ( b < 0.0 || b > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + " Blue";
        }
        if ( rangeError == true ) {
        throw new IllegalArgumentException("Color parameter outside of expected range:"
                                           + badComponentString);
        }
    }

    /**
     * Creates an opaque sRGB color with the specified red, green,
     * and blue values in the range (0 - 255).
     * The actual color used in rendering depends
     * on finding the best match given the color space
     * available for a given output device.
     * Alpha is defaulted to 255.
     *
     * <p>
     *  创建一个不透明的sRGB颜色,指定的红色,绿色和蓝色值在范围(0  -  255)内。渲染中使用的实际颜色取决于给定给定输出设备可用的颜色空间的最佳匹配。 Alpha默认为255。
     * 
     * 
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *        or <code>b</code> are outside of the range
     *        0 to 255, inclusive
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /**
     * Creates an sRGB color with the specified red, green, blue, and alpha
     * values in the range (0 - 255).
     *
     * <p>
     *  创建在范围(0  -  255)中指定的红色,绿色,蓝色和alpha值的sRGB颜色。
     * 
     * 
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>,
     *        <code>b</code> or <code>a</code> are outside of the range
     *        0 to 255, inclusive
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    @ConstructorProperties({"red", "green", "blue", "alpha"})
    public Color(int r, int g, int b, int a) {
        value = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8)  |
                ((b & 0xFF) << 0);
        testColorValueRange(r,g,b,a);
    }

    /**
     * Creates an opaque sRGB color with the specified combined RGB value
     * consisting of the red component in bits 16-23, the green component
     * in bits 8-15, and the blue component in bits 0-7.  The actual color
     * used in rendering depends on finding the best match given the
     * color space available for a particular output device.  Alpha is
     * defaulted to 255.
     *
     * <p>
     *  创建不透明的sRGB颜色,指定的组合RGB值由位16-23中的红色分量,位8-15中的绿色分量和位0-7中的蓝色分量组成。渲染中使用的实际颜色取决于给定特定输出设备可用的颜色空间的最佳匹配。
     *  Alpha默认为255。
     * 
     * 
     * @param rgb the combined RGB components
     * @see java.awt.image.ColorModel#getRGBdefault
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public Color(int rgb) {
        value = 0xff000000 | rgb;
    }

    /**
     * Creates an sRGB color with the specified combined RGBA value consisting
     * of the alpha component in bits 24-31, the red component in bits 16-23,
     * the green component in bits 8-15, and the blue component in bits 0-7.
     * If the <code>hasalpha</code> argument is <code>false</code>, alpha
     * is defaulted to 255.
     *
     * <p>
     * 创建sRGB颜色,指定的组合RGBA值由位24-31中的alpha分量,位16-23中的红色分量,位8-15中的绿色分量和位0-7中的蓝色分量组成。
     * 如果<code> hasalpha </code>参数是<code> false </code>,alpha默认为255。
     * 
     * 
     * @param rgba the combined RGBA components
     * @param hasalpha <code>true</code> if the alpha bits are valid;
     *        <code>false</code> otherwise
     * @see java.awt.image.ColorModel#getRGBdefault
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public Color(int rgba, boolean hasalpha) {
        if (hasalpha) {
            value = rgba;
        } else {
            value = 0xff000000 | rgba;
        }
    }

    /**
     * Creates an opaque sRGB color with the specified red, green, and blue
     * values in the range (0.0 - 1.0).  Alpha is defaulted to 1.0.  The
     * actual color used in rendering depends on finding the best
     * match given the color space available for a particular output
     * device.
     *
     * <p>
     *  创建不透明的sRGB颜色,指定的红色,绿色和蓝色值在范围(0.0  -  1.0)中。 Alpha默认为1.0。渲染中使用的实际颜色取决于给定特定输出设备可用的颜色空间的最佳匹配。
     * 
     * 
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *        or <code>b</code> are outside of the range
     *        0.0 to 1.0, inclusive
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public Color(float r, float g, float b) {
        this( (int) (r*255+0.5), (int) (g*255+0.5), (int) (b*255+0.5));
        testColorValueRange(r,g,b,1.0f);
        frgbvalue = new float[3];
        frgbvalue[0] = r;
        frgbvalue[1] = g;
        frgbvalue[2] = b;
        falpha = 1.0f;
        fvalue = frgbvalue;
    }

    /**
     * Creates an sRGB color with the specified red, green, blue, and
     * alpha values in the range (0.0 - 1.0).  The actual color
     * used in rendering depends on finding the best match given the
     * color space available for a particular output device.
     * <p>
     *  创建一个sRGB颜色,指定的红色,绿色,蓝色和alpha值在范围(0.0  -  1.0)。渲染中使用的实际颜色取决于给定特定输出设备可用的颜色空间的最佳匹配。
     * 
     * 
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *        <code>b</code> or <code>a</code> are outside of the range
     *        0.0 to 1.0, inclusive
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public Color(float r, float g, float b, float a) {
        this((int)(r*255+0.5), (int)(g*255+0.5), (int)(b*255+0.5), (int)(a*255+0.5));
        frgbvalue = new float[3];
        frgbvalue[0] = r;
        frgbvalue[1] = g;
        frgbvalue[2] = b;
        falpha = a;
        fvalue = frgbvalue;
    }

    /**
     * Creates a color in the specified <code>ColorSpace</code>
     * with the color components specified in the <code>float</code>
     * array and the specified alpha.  The number of components is
     * determined by the type of the <code>ColorSpace</code>.  For
     * example, RGB requires 3 components, but CMYK requires 4
     * components.
     * <p>
     *  使用<code> float </code>数组和指定的alpha中指定的颜色组件在指定的<code> ColorSpace </code>中创建颜色。
     * 组件的数量由<code> ColorSpace </code>的类型确定。例如,RGB需要3个分量,但CMYK需要4个分量。
     * 
     * 
     * @param cspace the <code>ColorSpace</code> to be used to
     *                  interpret the components
     * @param components an arbitrary number of color components
     *                      that is compatible with the <code>ColorSpace</code>
     * @param alpha alpha value
     * @throws IllegalArgumentException if any of the values in the
     *         <code>components</code> array or <code>alpha</code> is
     *         outside of the range 0.0 to 1.0
     * @see #getComponents
     * @see #getColorComponents
     */
    public Color(ColorSpace cspace, float components[], float alpha) {
        boolean rangeError = false;
        String badComponentString = "";
        int n = cspace.getNumComponents();
        fvalue = new float[n];
        for (int i = 0; i < n; i++) {
            if (components[i] < 0.0 || components[i] > 1.0) {
                rangeError = true;
                badComponentString = badComponentString + "Component " + i
                                     + " ";
            } else {
                fvalue[i] = components[i];
            }
        }
        if (alpha < 0.0 || alpha > 1.0) {
            rangeError = true;
            badComponentString = badComponentString + "Alpha";
        } else {
            falpha = alpha;
        }
        if (rangeError) {
            throw new IllegalArgumentException(
                "Color parameter outside of expected range: " +
                badComponentString);
        }
        frgbvalue = cspace.toRGB(fvalue);
        cs = cspace;
        value = ((((int)(falpha*255)) & 0xFF) << 24) |
                ((((int)(frgbvalue[0]*255)) & 0xFF) << 16) |
                ((((int)(frgbvalue[1]*255)) & 0xFF) << 8)  |
                ((((int)(frgbvalue[2]*255)) & 0xFF) << 0);
    }

    /**
     * Returns the red component in the range 0-255 in the default sRGB
     * space.
     * <p>
     *  返回默认sRGB空间中0-255范围内的红色分量。
     * 
     * 
     * @return the red component.
     * @see #getRGB
     */
    public int getRed() {
        return (getRGB() >> 16) & 0xFF;
    }

    /**
     * Returns the green component in the range 0-255 in the default sRGB
     * space.
     * <p>
     *  返回默认sRGB空间中0-255范围内的绿色分量。
     * 
     * 
     * @return the green component.
     * @see #getRGB
     */
    public int getGreen() {
        return (getRGB() >> 8) & 0xFF;
    }

    /**
     * Returns the blue component in the range 0-255 in the default sRGB
     * space.
     * <p>
     *  返回默认sRGB空间中0-255范围内的蓝色分量。
     * 
     * 
     * @return the blue component.
     * @see #getRGB
     */
    public int getBlue() {
        return (getRGB() >> 0) & 0xFF;
    }

    /**
     * Returns the alpha component in the range 0-255.
     * <p>
     *  返回0-255范围内的alpha分量。
     * 
     * 
     * @return the alpha component.
     * @see #getRGB
     */
    public int getAlpha() {
        return (getRGB() >> 24) & 0xff;
    }

    /**
     * Returns the RGB value representing the color in the default sRGB
     * {@link ColorModel}.
     * (Bits 24-31 are alpha, 16-23 are red, 8-15 are green, 0-7 are
     * blue).
     * <p>
     * 返回表示默认sRGB {@link ColorModel}中颜色的RGB值。 (位24-31是α,16-23是红色,8-15是绿色,0-7是蓝色)。
     * 
     * 
     * @return the RGB value of the color in the default sRGB
     *         <code>ColorModel</code>.
     * @see java.awt.image.ColorModel#getRGBdefault
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @since JDK1.0
     */
    public int getRGB() {
        return value;
    }

    private static final double FACTOR = 0.7;

    /**
     * Creates a new <code>Color</code> that is a brighter version of this
     * <code>Color</code>.
     * <p>
     * This method applies an arbitrary scale factor to each of the three RGB
     * components of this <code>Color</code> to create a brighter version
     * of this <code>Color</code>.
     * The {@code alpha} value is preserved.
     * Although <code>brighter</code> and
     * <code>darker</code> are inverse operations, the results of a
     * series of invocations of these two methods might be inconsistent
     * because of rounding errors.
     * <p>
     *  创建一个新的<code> Color </code>,它是这个<code> Color </code>的一个更亮的版本。
     * <p>
     *  此方法对此<code> Color </code>的三个RGB分量中的每一个应用任意比例因子,以创建此<code> Color </code>的更亮的版本。系统会保留{@code alpha}值。
     * 虽然<code>更亮</code>和<code> darker </code>是反向操作,但是由于舍入错误,这两种方法的一系列调用的结果可能不一致。
     * 
     * 
     * @return     a new <code>Color</code> object that is
     *                 a brighter version of this <code>Color</code>
     *                 with the same {@code alpha} value.
     * @see        java.awt.Color#darker
     * @since      JDK1.0
     */
    public Color brighter() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        int alpha = getAlpha();

        /* From 2D group:
         * 1. black.brighter() should return grey
         * 2. applying brighter to blue will always return blue, brighter
         * 3. non pure color (non zero rgb) will eventually return white
         * <p>
         *  black.brighter()应该返回灰色2.应用更亮到蓝色将总是返回蓝色,更亮3.非纯颜色(非零rgb)将最终返回白色
         * 
         */
        int i = (int)(1.0/(1.0-FACTOR));
        if ( r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;

        return new Color(Math.min((int)(r/FACTOR), 255),
                         Math.min((int)(g/FACTOR), 255),
                         Math.min((int)(b/FACTOR), 255),
                         alpha);
    }

    /**
     * Creates a new <code>Color</code> that is a darker version of this
     * <code>Color</code>.
     * <p>
     * This method applies an arbitrary scale factor to each of the three RGB
     * components of this <code>Color</code> to create a darker version of
     * this <code>Color</code>.
     * The {@code alpha} value is preserved.
     * Although <code>brighter</code> and
     * <code>darker</code> are inverse operations, the results of a series
     * of invocations of these two methods might be inconsistent because
     * of rounding errors.
     * <p>
     *  创建一个新的<code> Color </code>,它是这个<code> Color </code>的深色版本。
     * <p>
     *  此方法对此<code> Color </code>的三个RGB分量中的每一个应用任意比例因子,以创建此<code> Color </code>的较暗版本。系统会保留{@code alpha}值。
     * 虽然<code>更亮</code>和<code> darker </code>是反向操作,但是由于舍入错误,这两种方法的一系列调用的结果可能不一致。
     * 
     * 
     * @return  a new <code>Color</code> object that is
     *                    a darker version of this <code>Color</code>
     *                    with the same {@code alpha} value.
     * @see        java.awt.Color#brighter
     * @since      JDK1.0
     */
    public Color darker() {
        return new Color(Math.max((int)(getRed()  *FACTOR), 0),
                         Math.max((int)(getGreen()*FACTOR), 0),
                         Math.max((int)(getBlue() *FACTOR), 0),
                         getAlpha());
    }

    /**
     * Computes the hash code for this <code>Color</code>.
     * <p>
     *  计算此<code> Color </code>的哈希码。
     * 
     * 
     * @return     a hash code value for this object.
     * @since      JDK1.0
     */
    public int hashCode() {
        return value;
    }

    /**
     * Determines whether another object is equal to this
     * <code>Color</code>.
     * <p>
     * The result is <code>true</code> if and only if the argument is not
     * <code>null</code> and is a <code>Color</code> object that has the same
     * red, green, blue, and alpha values as this object.
     * <p>
     *  确定另一个对象是否等于此<code> Color </code>。
     * <p>
     * 如果且仅当参数不是<code> null </code>且是具有相同红色,绿色,蓝色和蓝色的<code> Color </code>对象,结果是<code> true </code> alpha值作为此
     * 对象。
     * 
     * 
     * @param       obj   the object to test for equality with this
     *                          <code>Color</code>
     * @return      <code>true</code> if the objects are the same;
     *                             <code>false</code> otherwise.
     * @since   JDK1.0
     */
    public boolean equals(Object obj) {
        return obj instanceof Color && ((Color)obj).getRGB() == this.getRGB();
    }

    /**
     * Returns a string representation of this <code>Color</code>. This
     * method is intended to be used only for debugging purposes.  The
     * content and format of the returned string might vary between
     * implementations. The returned string might be empty but cannot
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> Color </code>的字符串表示形式。此方法仅用于调试目的。返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但不能是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>Color</code>.
     */
    public String toString() {
        return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
    }

    /**
     * Converts a <code>String</code> to an integer and returns the
     * specified opaque <code>Color</code>. This method handles string
     * formats that are used to represent octal and hexadecimal numbers.
     * <p>
     *  将<code> String </code>转换为整数,并返回指定的不透明<code> Color </code>。此方法处理用于表示八进制和十六进制数字的字符串格式。
     * 
     * 
     * @param      nm a <code>String</code> that represents
     *                            an opaque color as a 24-bit integer
     * @return     the new <code>Color</code> object.
     * @see        java.lang.Integer#decode
     * @exception  NumberFormatException  if the specified string cannot
     *                      be interpreted as a decimal,
     *                      octal, or hexadecimal integer.
     * @since      JDK1.1
     */
    public static Color decode(String nm) throws NumberFormatException {
        Integer intval = Integer.decode(nm);
        int i = intval.intValue();
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

    /**
     * Finds a color in the system properties.
     * <p>
     * The argument is treated as the name of a system property to
     * be obtained. The string value of this property is then interpreted
     * as an integer which is then converted to a <code>Color</code>
     * object.
     * <p>
     * If the specified property is not found or could not be parsed as
     * an integer then <code>null</code> is returned.
     * <p>
     *  在系统属性中查找颜色。
     * <p>
     *  参数被视为要获取的系统属性的名称。然后,此属性的字符串值将解释为整数,然后将其转换为<code> Color </code>对象。
     * <p>
     *  如果未找到指定的属性或无法解析为整数,则返回<code> null </code>。
     * 
     * 
     * @param    nm the name of the color property
     * @return   the <code>Color</code> converted from the system
     *          property.
     * @see      java.lang.System#getProperty(java.lang.String)
     * @see      java.lang.Integer#getInteger(java.lang.String)
     * @see      java.awt.Color#Color(int)
     * @since    JDK1.0
     */
    public static Color getColor(String nm) {
        return getColor(nm, null);
    }

    /**
     * Finds a color in the system properties.
     * <p>
     * The first argument is treated as the name of a system property to
     * be obtained. The string value of this property is then interpreted
     * as an integer which is then converted to a <code>Color</code>
     * object.
     * <p>
     * If the specified property is not found or cannot be parsed as
     * an integer then the <code>Color</code> specified by the second
     * argument is returned instead.
     * <p>
     *  在系统属性中查找颜色。
     * <p>
     *  第一个参数被视为要获取的系统属性的名称。然后,此属性的字符串值将解释为整数,然后将其转换为<code> Color </code>对象。
     * <p>
     *  如果未找到指定的属性或不能将其解析为整数,那么将返回由第二个参数指定的<code> Color </code>。
     * 
     * 
     * @param    nm the name of the color property
     * @param    v    the default <code>Color</code>
     * @return   the <code>Color</code> converted from the system
     *          property, or the specified <code>Color</code>.
     * @see      java.lang.System#getProperty(java.lang.String)
     * @see      java.lang.Integer#getInteger(java.lang.String)
     * @see      java.awt.Color#Color(int)
     * @since    JDK1.0
     */
    public static Color getColor(String nm, Color v) {
        Integer intval = Integer.getInteger(nm);
        if (intval == null) {
            return v;
        }
        int i = intval.intValue();
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF);
    }

    /**
     * Finds a color in the system properties.
     * <p>
     * The first argument is treated as the name of a system property to
     * be obtained. The string value of this property is then interpreted
     * as an integer which is then converted to a <code>Color</code>
     * object.
     * <p>
     * If the specified property is not found or could not be parsed as
     * an integer then the integer value <code>v</code> is used instead,
     * and is converted to a <code>Color</code> object.
     * <p>
     *  在系统属性中查找颜色。
     * <p>
     * 第一个参数被视为要获取的系统属性的名称。然后,此属性的字符串值将解释为整数,然后将其转换为<code> Color </code>对象。
     * <p>
     *  如果未找到指定的属性或无法解析为整数,则使用整数值<code> v </code>,并将其转换为<code> Color </code>对象。
     * 
     * 
     * @param    nm  the name of the color property
     * @param    v   the default color value, as an integer
     * @return   the <code>Color</code> converted from the system
     *          property or the <code>Color</code> converted from
     *          the specified integer.
     * @see      java.lang.System#getProperty(java.lang.String)
     * @see      java.lang.Integer#getInteger(java.lang.String)
     * @see      java.awt.Color#Color(int)
     * @since    JDK1.0
     */
    public static Color getColor(String nm, int v) {
        Integer intval = Integer.getInteger(nm);
        int i = (intval != null) ? intval.intValue() : v;
        return new Color((i >> 16) & 0xFF, (i >> 8) & 0xFF, (i >> 0) & 0xFF);
    }

    /**
     * Converts the components of a color, as specified by the HSB
     * model, to an equivalent set of values for the default RGB model.
     * <p>
     * The <code>saturation</code> and <code>brightness</code> components
     * should be floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>hue</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * <p>
     * The integer that is returned by <code>HSBtoRGB</code> encodes the
     * value of a color in bits 0-23 of an integer value that is the same
     * format used by the method {@link #getRGB() getRGB}.
     * This integer can be supplied as an argument to the
     * <code>Color</code> constructor that takes a single integer argument.
     * <p>
     *  将颜色的组件(由HSB模型指定)转换为默认RGB模型的一组等效值。
     * <p>
     *  <code>饱和</code>和<code>亮度</code>组件应为零和一之间的浮点值(0.0-1.0范围内的数字)。 <code> hue </code>组件可以是任何浮点数。
     * 从其中减去该数字的底部以创建0和1之间的分数。然后将该分数乘以360以在HSB颜色模型中产生色调角。
     * <p>
     *  由<code> HSBtoRGB </code>返回的整数对与方法{@link #getRGB()getRGB}使用的格式相同的整数值的位0-23中的颜色值进行编码。
     * 此整数可作为参数提供给采用单个整数参数的<code> Color </code>构造函数。
     * 
     * 
     * @param     hue   the hue component of the color
     * @param     saturation   the saturation of the color
     * @param     brightness   the brightness of the color
     * @return    the RGB value of the color with the indicated hue,
     *                            saturation, and brightness.
     * @see       java.awt.Color#getRGB()
     * @see       java.awt.Color#Color(int)
     * @see       java.awt.image.ColorModel#getRGBdefault()
     * @since     JDK1.0
     */
    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
            case 0:
                r = (int) (brightness * 255.0f + 0.5f);
                g = (int) (t * 255.0f + 0.5f);
                b = (int) (p * 255.0f + 0.5f);
                break;
            case 1:
                r = (int) (q * 255.0f + 0.5f);
                g = (int) (brightness * 255.0f + 0.5f);
                b = (int) (p * 255.0f + 0.5f);
                break;
            case 2:
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (brightness * 255.0f + 0.5f);
                b = (int) (t * 255.0f + 0.5f);
                break;
            case 3:
                r = (int) (p * 255.0f + 0.5f);
                g = (int) (q * 255.0f + 0.5f);
                b = (int) (brightness * 255.0f + 0.5f);
                break;
            case 4:
                r = (int) (t * 255.0f + 0.5f);
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (brightness * 255.0f + 0.5f);
                break;
            case 5:
                r = (int) (brightness * 255.0f + 0.5f);
                g = (int) (p * 255.0f + 0.5f);
                b = (int) (q * 255.0f + 0.5f);
                break;
            }
        }
        return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
    }

    /**
     * Converts the components of a color, as specified by the default RGB
     * model, to an equivalent set of values for hue, saturation, and
     * brightness that are the three components of the HSB model.
     * <p>
     * If the <code>hsbvals</code> argument is <code>null</code>, then a
     * new array is allocated to return the result. Otherwise, the method
     * returns the array <code>hsbvals</code>, with the values put into
     * that array.
     * <p>
     *  将颜色的组件(由默认RGB模型指定)转换为HSK模型的三个组件的色调,饱和度和亮度的等效值集合。
     * <p>
     * 如果<code> hsbvals </code>参数是<code> null </code>,那么将分配一个新数组以返回结果。
     * 否则,该方法返回数组<code> hsbvals </code>,将值放入该数组。
     * 
     * 
     * @param     r   the red component of the color
     * @param     g   the green component of the color
     * @param     b   the blue component of the color
     * @param     hsbvals  the array used to return the
     *                     three HSB values, or <code>null</code>
     * @return    an array of three elements containing the hue, saturation,
     *                     and brightness (in that order), of the color with
     *                     the indicated red, green, and blue components.
     * @see       java.awt.Color#getRGB()
     * @see       java.awt.Color#Color(int)
     * @see       java.awt.image.ColorModel#getRGBdefault()
     * @since     JDK1.0
     */
    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }
        int cmax = (r > g) ? r : g;
        if (b > cmax) cmax = b;
        int cmin = (r < g) ? r : g;
        if (b < cmin) cmin = b;

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0)
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        else
            saturation = 0;
        if (saturation == 0)
            hue = 0;
        else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0f + redc - bluec;
            else
                hue = 4.0f + greenc - redc;
            hue = hue / 6.0f;
            if (hue < 0)
                hue = hue + 1.0f;
        }
        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }

    /**
     * Creates a <code>Color</code> object based on the specified values
     * for the HSB color model.
     * <p>
     * The <code>s</code> and <code>b</code> components should be
     * floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>h</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * <p>
     *  基于HSB颜色模型的指定值创建<code> Color </code>对象。
     * <p>
     *  <code> s </code>和<code> b </code>组件应为零到一(0.0-1.0范围内的数字)之间的浮点值。 <code> h </code>组件可以是任何浮点数。
     * 从其中减去该数字的底部以创建0和1之间的分数。然后将该分数乘以360以在HSB颜色模型中产生色调角。
     * 
     * 
     * @param  h   the hue component
     * @param  s   the saturation of the color
     * @param  b   the brightness of the color
     * @return  a <code>Color</code> object with the specified hue,
     *                                 saturation, and brightness.
     * @since   JDK1.0
     */
    public static Color getHSBColor(float h, float s, float b) {
        return new Color(HSBtoRGB(h, s, b));
    }

    /**
     * Returns a <code>float</code> array containing the color and alpha
     * components of the <code>Color</code>, as represented in the default
     * sRGB color space.
     * If <code>compArray</code> is <code>null</code>, an array of length
     * 4 is created for the return value.  Otherwise,
     * <code>compArray</code> must have length 4 or greater,
     * and it is filled in with the components and returned.
     * <p>
     *  返回一个包含<code> Color </code>的颜色和alpha组件的<code> float </code>数组,如默认sRGB颜色空间中所示。
     * 如果<code> compArray </code>是<code> null </code>,则为返回值创建长度为4的数组。
     * 否则,<code> compArray </code>的长度必须为4或更大,并且用组件填充并返回。
     * 
     * 
     * @param compArray an array that this method fills with
     *                  color and alpha components and returns
     * @return the RGBA components in a <code>float</code> array.
     */
    public float[] getRGBComponents(float[] compArray) {
        float[] f;
        if (compArray == null) {
            f = new float[4];
        } else {
            f = compArray;
        }
        if (frgbvalue == null) {
            f[0] = ((float)getRed())/255f;
            f[1] = ((float)getGreen())/255f;
            f[2] = ((float)getBlue())/255f;
            f[3] = ((float)getAlpha())/255f;
        } else {
            f[0] = frgbvalue[0];
            f[1] = frgbvalue[1];
            f[2] = frgbvalue[2];
            f[3] = falpha;
        }
        return f;
    }

    /**
     * Returns a <code>float</code> array containing only the color
     * components of the <code>Color</code>, in the default sRGB color
     * space.  If <code>compArray</code> is <code>null</code>, an array of
     * length 3 is created for the return value.  Otherwise,
     * <code>compArray</code> must have length 3 or greater, and it is
     * filled in with the components and returned.
     * <p>
     *  返回在默认sRGB颜色空间中仅包含<code> Color </code>的颜色分量的<code> float </code>数组。
     * 如果<code> compArray </code>是<code> null </code>,则为返回值创建长度为3的数组。
     * 否则,<code> compArray </code>必须具有长度3或更大,并且用组件填充并返回。
     * 
     * 
     * @param compArray an array that this method fills with color
     *          components and returns
     * @return the RGB components in a <code>float</code> array.
     */
    public float[] getRGBColorComponents(float[] compArray) {
        float[] f;
        if (compArray == null) {
            f = new float[3];
        } else {
            f = compArray;
        }
        if (frgbvalue == null) {
            f[0] = ((float)getRed())/255f;
            f[1] = ((float)getGreen())/255f;
            f[2] = ((float)getBlue())/255f;
        } else {
            f[0] = frgbvalue[0];
            f[1] = frgbvalue[1];
            f[2] = frgbvalue[2];
        }
        return f;
    }

    /**
     * Returns a <code>float</code> array containing the color and alpha
     * components of the <code>Color</code>, in the
     * <code>ColorSpace</code> of the <code>Color</code>.
     * If <code>compArray</code> is <code>null</code>, an array with
     * length equal to the number of components in the associated
     * <code>ColorSpace</code> plus one is created for
     * the return value.  Otherwise, <code>compArray</code> must have at
     * least this length and it is filled in with the components and
     * returned.
     * <p>
     * 在<code> Color </code>的<code> ColorSpace </code>中返回<code> float </code>数组,其中包含<code> Color </code>的颜色和
     * alpha组件。
     * 如果<code> compArray </code>是<code> null </code>,则为返回值创建一个长度等于相关联的<code> ColorSpace </code>加一的组件数的数组。
     * 否则,<code> compArray </code>必须至少有这个长度,它被填充组件并返回。
     * 
     * 
     * @param compArray an array that this method fills with the color and
     *          alpha components of this <code>Color</code> in its
     *          <code>ColorSpace</code> and returns
     * @return the color and alpha components in a <code>float</code>
     *          array.
     */
    public float[] getComponents(float[] compArray) {
        if (fvalue == null)
            return getRGBComponents(compArray);
        float[] f;
        int n = fvalue.length;
        if (compArray == null) {
            f = new float[n + 1];
        } else {
            f = compArray;
        }
        for (int i = 0; i < n; i++) {
            f[i] = fvalue[i];
        }
        f[n] = falpha;
        return f;
    }

    /**
     * Returns a <code>float</code> array containing only the color
     * components of the <code>Color</code>, in the
     * <code>ColorSpace</code> of the <code>Color</code>.
     * If <code>compArray</code> is <code>null</code>, an array with
     * length equal to the number of components in the associated
     * <code>ColorSpace</code> is created for
     * the return value.  Otherwise, <code>compArray</code> must have at
     * least this length and it is filled in with the components and
     * returned.
     * <p>
     *  在<code> Color </code>的<code> ColorSpace </code>中返回仅包含<code> Color </code>的颜色分量的<code> float </code>数
     * 组。
     * 如果<code> compArray </code>是<code> null </code>,则为返回值创建一个长度等于相关联的<code> ColorSpace </code>中组件数的数组。
     * 否则,<code> compArray </code>必须至少有这个长度,它被填充组件并返回。
     * 
     * 
     * @param compArray an array that this method fills with the color
     *          components of this <code>Color</code> in its
     *          <code>ColorSpace</code> and returns
     * @return the color components in a <code>float</code> array.
     */
    public float[] getColorComponents(float[] compArray) {
        if (fvalue == null)
            return getRGBColorComponents(compArray);
        float[] f;
        int n = fvalue.length;
        if (compArray == null) {
            f = new float[n];
        } else {
            f = compArray;
        }
        for (int i = 0; i < n; i++) {
            f[i] = fvalue[i];
        }
        return f;
    }

    /**
     * Returns a <code>float</code> array containing the color and alpha
     * components of the <code>Color</code>, in the
     * <code>ColorSpace</code> specified by the <code>cspace</code>
     * parameter.  If <code>compArray</code> is <code>null</code>, an
     * array with length equal to the number of components in
     * <code>cspace</code> plus one is created for the return value.
     * Otherwise, <code>compArray</code> must have at least this
     * length, and it is filled in with the components and returned.
     * <p>
     *  在<code> cspace </code>参数指定的<code> ColorSpace </code>中返回包含<code> Color </code>的颜色和alpha组件的<code> floa
     * t </code>数组。
     * 如果<code> compArray </code>是<code> null </code>,则为返回值创建一个长度等于<code> cspace </code>加一的组件数量的数组。
     * 否则,<code> compArray </code>必须至少有这个长度,并且它被组件填充并返回。
     * 
     * 
     * @param cspace a specified <code>ColorSpace</code>
     * @param compArray an array that this method fills with the
     *          color and alpha components of this <code>Color</code> in
     *          the specified <code>ColorSpace</code> and returns
     * @return the color and alpha components in a <code>float</code>
     *          array.
     */
    public float[] getComponents(ColorSpace cspace, float[] compArray) {
        if (cs == null) {
            cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        }
        float f[];
        if (fvalue == null) {
            f = new float[3];
            f[0] = ((float)getRed())/255f;
            f[1] = ((float)getGreen())/255f;
            f[2] = ((float)getBlue())/255f;
        } else {
            f = fvalue;
        }
        float tmp[] = cs.toCIEXYZ(f);
        float tmpout[] = cspace.fromCIEXYZ(tmp);
        if (compArray == null) {
            compArray = new float[tmpout.length + 1];
        }
        for (int i = 0 ; i < tmpout.length ; i++) {
            compArray[i] = tmpout[i];
        }
        if (fvalue == null) {
            compArray[tmpout.length] = ((float)getAlpha())/255f;
        } else {
            compArray[tmpout.length] = falpha;
        }
        return compArray;
    }

    /**
     * Returns a <code>float</code> array containing only the color
     * components of the <code>Color</code> in the
     * <code>ColorSpace</code> specified by the <code>cspace</code>
     * parameter. If <code>compArray</code> is <code>null</code>, an array
     * with length equal to the number of components in
     * <code>cspace</code> is created for the return value.  Otherwise,
     * <code>compArray</code> must have at least this length, and it is
     * filled in with the components and returned.
     * <p>
     * 返回一个只包含由<code> cspace </code>参数指定的<code> ColorSpace </code>中<color> </code>颜色组件的<code> float </code>数
     * 组。
     * 如果<code> compArray </code>是<code> null </code>,则为返回值创建一个长度等于<code> cspace </code>中组件数的数组。
     * 否则,<code> compArray </code>必须至少具有此长度,并且用组件填充并返回。
     * 
     * 
     * @param cspace a specified <code>ColorSpace</code>
     * @param compArray an array that this method fills with the color
     *          components of this <code>Color</code> in the specified
     *          <code>ColorSpace</code>
     * @return the color components in a <code>float</code> array.
     */
    public float[] getColorComponents(ColorSpace cspace, float[] compArray) {
        if (cs == null) {
            cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        }
        float f[];
        if (fvalue == null) {
            f = new float[3];
            f[0] = ((float)getRed())/255f;
            f[1] = ((float)getGreen())/255f;
            f[2] = ((float)getBlue())/255f;
        } else {
            f = fvalue;
        }
        float tmp[] = cs.toCIEXYZ(f);
        float tmpout[] = cspace.fromCIEXYZ(tmp);
        if (compArray == null) {
            return tmpout;
        }
        for (int i = 0 ; i < tmpout.length ; i++) {
            compArray[i] = tmpout[i];
        }
        return compArray;
    }

    /**
     * Returns the <code>ColorSpace</code> of this <code>Color</code>.
     * <p>
     *  返回此<code> Color </code>的<code> ColorSpace </code>。
     * 
     * 
     * @return this <code>Color</code> object's <code>ColorSpace</code>.
     */
    public ColorSpace getColorSpace() {
        if (cs == null) {
            cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        }
        return cs;
    }

    /**
     * Creates and returns a {@link PaintContext} used to
     * generate a solid color field pattern.
     * See the {@link Paint#createContext specification} of the
     * method in the {@link Paint} interface for information
     * on null parameter handling.
     *
     * <p>
     *  创建并返回用于生成纯色字段模式的{@link PaintContext}。
     * 有关空参数处理的信息,请参阅{@link Paint}界面中的方法的{@link Paint#createContext specification}。
     * 
     * 
     * @param cm the preferred {@link ColorModel} which represents the most convenient
     *           format for the caller to receive the pixel data, or {@code null}
     *           if there is no preference.
     * @param r the device space bounding box
     *                     of the graphics primitive being rendered.
     * @param r2d the user space bounding box
     *                   of the graphics primitive being rendered.
     * @param xform the {@link AffineTransform} from user
     *              space into device space.
     * @param hints the set of hints that the context object can use to
     *              choose between rendering alternatives.
     * @return the {@code PaintContext} for
     *         generating color patterns.
     * @see Paint
     * @see PaintContext
     * @see ColorModel
     * @see Rectangle
     * @see Rectangle2D
     * @see AffineTransform
     * @see RenderingHints
     */
    public synchronized PaintContext createContext(ColorModel cm, Rectangle r,
                                                   Rectangle2D r2d,
                                                   AffineTransform xform,
                                                   RenderingHints hints) {
        return new ColorPaintContext(getRGB(), cm);
    }

    /**
     * Returns the transparency mode for this <code>Color</code>.  This is
     * required to implement the <code>Paint</code> interface.
     * <p>
     * 
     * @return this <code>Color</code> object's transparency mode.
     * @see Paint
     * @see Transparency
     * @see #createContext
     */
    public int getTransparency() {
        int alpha = getAlpha();
        if (alpha == 0xff) {
            return Transparency.OPAQUE;
        }
        else if (alpha == 0) {
            return Transparency.BITMASK;
        }
        else {
            return Transparency.TRANSLUCENT;
        }
    }

}
