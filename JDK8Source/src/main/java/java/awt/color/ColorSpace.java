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

/*
 **********************************************************************
 **********************************************************************
 **********************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997                      ***
 *** As  an unpublished  work pursuant to Title 17 of the United    ***
 *** States Code.  All rights reserved.                             ***
 **********************************************************************
 **********************************************************************
 * <p>
 *  **************************************************** ****************** ****************************
 * **** ************************************ ************** ********************************************
 * ******** **** * COPYRIGHT(c)Eastman Kodak Company,1997 *** *根据United *** *国家法典第17章的未发表的作品。
 * 版权所有。
 *  *** *********************************************** ********************* **************************
 * *** ***************************************。
 * 版权所有。
 * 
 * 
 **********************************************************************/

package java.awt.color;

import java.lang.annotation.Native;

import sun.java2d.cmm.PCMM;
import sun.java2d.cmm.CMSManager;


/**
 * This abstract class is used to serve as a color space tag to identify the
 * specific color space of a Color object or, via a ColorModel object,
 * of an Image, a BufferedImage, or a GraphicsDevice.  It contains
 * methods that transform colors in a specific color space to/from sRGB
 * and to/from a well-defined CIEXYZ color space.
 * <p>
 * For purposes of the methods in this class, colors are represented as
 * arrays of color components represented as floats in a normalized range
 * defined by each ColorSpace.  For many ColorSpaces (e.g. sRGB), this
 * range is 0.0 to 1.0.  However, some ColorSpaces have components whose
 * values have a different range.  Methods are provided to inquire per
 * component minimum and maximum normalized values.
 * <p>
 * Several variables are defined for purposes of referring to color
 * space types (e.g. TYPE_RGB, TYPE_XYZ, etc.) and to refer to specific
 * color spaces (e.g. CS_sRGB and CS_CIEXYZ).
 * sRGB is a proposed standard RGB color space.  For more information,
 * see <A href="http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html">
 * http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html
 * </A>.
 * <p>
 * The purpose of the methods to transform to/from the well-defined
 * CIEXYZ color space is to support conversions between any two color
 * spaces at a reasonably high degree of accuracy.  It is expected that
 * particular implementations of subclasses of ColorSpace (e.g.
 * ICC_ColorSpace) will support high performance conversion based on
 * underlying platform color management systems.
 * <p>
 * The CS_CIEXYZ space used by the toCIEXYZ/fromCIEXYZ methods can be
 * described as follows:
<pre>

&nbsp;     CIEXYZ
&nbsp;     viewing illuminance: 200 lux
&nbsp;     viewing white point: CIE D50
&nbsp;     media white point: "that of a perfectly reflecting diffuser" -- D50
&nbsp;     media black point: 0 lux or 0 Reflectance
&nbsp;     flare: 1 percent
&nbsp;     surround: 20percent of the media white point
&nbsp;     media description: reflection print (i.e., RLAB, Hunt viewing media)
&nbsp;     note: For developers creating an ICC profile for this conversion
&nbsp;           space, the following is applicable.  Use a simple Von Kries
&nbsp;           white point adaptation folded into the 3X3 matrix parameters
&nbsp;           and fold the flare and surround effects into the three
&nbsp;           one-dimensional lookup tables (assuming one uses the minimal
&nbsp;           model for monitors).

</pre>
 *
 * <p>
 *  此抽象类用于作为颜色空间标记,以标识Color对象的特定颜色空间,或者通过ColorModel对象创建图像,BufferedImage或GraphicsDevice。
 * 它包含将特定颜色空间中的颜色转换为/从sRGB和/或从明确定义的CIEXYZ颜色空间转换的方法。
 * <p>
 *  为了该类中的方法的目的,颜色被表示为由每个ColorSpace定义的归一化范围中表示为浮点的颜色分量的数组。对于许多ColorSpaces(例如sRGB),此范围为0.0至1.0。
 * 但是,一些ColorSpaces具有值的范围不同的组件。提供了用于查询每个组件最小和最大归一化值的方法。
 * <p>
 * 为了参考颜色空间类型(例如TYPE_RGB,TYPE_XYZ等)和参考特定颜色空间(例如CS_sRGB和CS_CIEXYZ)而定义了几个变量。 sRGB是建议的标准RGB颜色空间。
 * 有关详情,请参阅<A href="http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html"> http://www.w3.org/pub/WWW/Graph
 * ics/Color /sRGB.html </A>。
 * 为了参考颜色空间类型(例如TYPE_RGB,TYPE_XYZ等)和参考特定颜色空间(例如CS_sRGB和CS_CIEXYZ)而定义了几个变量。 sRGB是建议的标准RGB颜色空间。
 * <p>
 *  转换到/从明确定义的CIEXYZ颜色空间的方法的目的是以相当高的精确度支持任意两个颜色空间之间的转换。
 * 期望ColorSpace的子类(例如ICC_ColorSpace)的特定实现将支持基于底层平台颜色管理系统的高性能转换。
 * <p>
 *  toCIEXYZ / fromCIEXYZ方法使用的CS_CIEXYZ空间可以描述如下：
 * <pre>
 * 
 * &nbsp; CIEXYZ&nbsp;观看照度：200 lux&nbsp;查看白点：CIE D50&nbsp;媒体白点："一个完美的反射扩散器" -  D50&nbsp;媒体黑点：0 lux或0反射率f
 * lare：1％&nbsp;环绕：20％的媒体白点媒体描述：反射打印(即RLAB,Hunt查看媒体)注意：对于为此次转换创建ICC配置文件的开发者空间,以下是适用的。
 * 使用简单的Von Kries&nbsp;白点适应折叠成3X3矩阵参数&并将耀斑和环绕效果折叠成三个一维查找表(假设一个使用监视器的最小模型)。
 * 
 * </pre>
 * 
 * 
 * @see ICC_ColorSpace
 */

public abstract class ColorSpace implements java.io.Serializable {

    static final long serialVersionUID = -409452704308689724L;

    private int type;
    private int numComponents;
    private transient String [] compName = null;

    // Cache of singletons for the predefined color spaces.
    private static ColorSpace sRGBspace;
    private static ColorSpace XYZspace;
    private static ColorSpace PYCCspace;
    private static ColorSpace GRAYspace;
    private static ColorSpace LINEAR_RGBspace;

    /**
     * Any of the family of XYZ color spaces.
     * <p>
     *  任何XYZ颜色空间族。
     * 
     */
    @Native public static final int TYPE_XYZ = 0;

    /**
     * Any of the family of Lab color spaces.
     * <p>
     *  任何Lab色彩空间系列。
     * 
     */
    @Native public static final int TYPE_Lab = 1;

    /**
     * Any of the family of Luv color spaces.
     * <p>
     *  任何Luv颜色空间的家庭。
     * 
     */
    @Native public static final int TYPE_Luv = 2;

    /**
     * Any of the family of YCbCr color spaces.
     * <p>
     *  任何YCbCr颜色空间族。
     * 
     */
    @Native public static final int TYPE_YCbCr = 3;

    /**
     * Any of the family of Yxy color spaces.
     * <p>
     *  任何一个Yxy颜色空间族。
     * 
     */
    @Native public static final int TYPE_Yxy = 4;

    /**
     * Any of the family of RGB color spaces.
     * <p>
     *  任何RGB颜色空间的家庭。
     * 
     */
    @Native public static final int TYPE_RGB = 5;

    /**
     * Any of the family of GRAY color spaces.
     * <p>
     *  任何GRAY颜色空间族。
     * 
     */
    @Native public static final int TYPE_GRAY = 6;

    /**
     * Any of the family of HSV color spaces.
     * <p>
     *  任何HSV颜色空间族。
     * 
     */
    @Native public static final int TYPE_HSV = 7;

    /**
     * Any of the family of HLS color spaces.
     * <p>
     *  任何HLS颜色空间族。
     * 
     */
    @Native public static final int TYPE_HLS = 8;

    /**
     * Any of the family of CMYK color spaces.
     * <p>
     *  任何CMYK颜色空间族。
     * 
     */
    @Native public static final int TYPE_CMYK = 9;

    /**
     * Any of the family of CMY color spaces.
     * <p>
     *  任何CMY颜色空间族。
     * 
     */
    @Native public static final int TYPE_CMY = 11;

    /**
     * Generic 2 component color spaces.
     * <p>
     *  通用2组件颜色空间。
     * 
     */
    @Native public static final int TYPE_2CLR = 12;

    /**
     * Generic 3 component color spaces.
     * <p>
     *  通用3组件颜色空间。
     * 
     */
    @Native public static final int TYPE_3CLR = 13;

    /**
     * Generic 4 component color spaces.
     * <p>
     *  通用4组件颜色空间。
     * 
     */
    @Native public static final int TYPE_4CLR = 14;

    /**
     * Generic 5 component color spaces.
     * <p>
     *  通用5组件颜色空间。
     * 
     */
    @Native public static final int TYPE_5CLR = 15;

    /**
     * Generic 6 component color spaces.
     * <p>
     *  通用6组件颜色空间。
     * 
     */
    @Native public static final int TYPE_6CLR = 16;

    /**
     * Generic 7 component color spaces.
     * <p>
     *  通用7组件颜色空间。
     * 
     */
    @Native public static final int TYPE_7CLR = 17;

    /**
     * Generic 8 component color spaces.
     * <p>
     * 通用8组件颜色空间。
     * 
     */
    @Native public static final int TYPE_8CLR = 18;

    /**
     * Generic 9 component color spaces.
     * <p>
     *  通用9组件颜色空间。
     * 
     */
    @Native public static final int TYPE_9CLR = 19;

    /**
     * Generic 10 component color spaces.
     * <p>
     *  通用10组件颜色空间。
     * 
     */
    @Native public static final int TYPE_ACLR = 20;

    /**
     * Generic 11 component color spaces.
     * <p>
     *  通用11组件颜色空间。
     * 
     */
    @Native public static final int TYPE_BCLR = 21;

    /**
     * Generic 12 component color spaces.
     * <p>
     *  通用12组件颜色空间。
     * 
     */
    @Native public static final int TYPE_CCLR = 22;

    /**
     * Generic 13 component color spaces.
     * <p>
     *  通用13组件颜色空间。
     * 
     */
    @Native public static final int TYPE_DCLR = 23;

    /**
     * Generic 14 component color spaces.
     * <p>
     *  通用14组件颜色空间。
     * 
     */
    @Native public static final int TYPE_ECLR = 24;

    /**
     * Generic 15 component color spaces.
     * <p>
     *  通用15组件颜色空间。
     * 
     */
    @Native public static final int TYPE_FCLR = 25;

    /**
     * The sRGB color space defined at
     * <A href="http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html">
     * http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html
     * </A>.
     * <p>
     *  sRGB颜色空间定义在
     * <A href="http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html">
     *  http://www.w3.org/pub/WWW/Graphics/Color/sRGB.html </A>。
     * 
     */
    @Native public static final int CS_sRGB = 1000;

    /**
     * A built-in linear RGB color space.  This space is based on the
     * same RGB primaries as CS_sRGB, but has a linear tone reproduction curve.
     * <p>
     *  内置线性RGB颜色空间。该空间基于与CS_sRGB相同的RGB基色,但是具有线性色调再现曲线。
     * 
     */
    @Native public static final int CS_LINEAR_RGB = 1004;

    /**
     * The CIEXYZ conversion color space defined above.
     * <p>
     *  上面定义的CIEXYZ转换颜色空间。
     * 
     */
    @Native public static final int CS_CIEXYZ = 1001;

    /**
     * The Photo YCC conversion color space.
     * <p>
     *  照片YCC转换颜色空间。
     * 
     */
    @Native public static final int CS_PYCC = 1002;

    /**
     * The built-in linear gray scale color space.
     * <p>
     *  内置线性灰度色空间。
     * 
     */
    @Native public static final int CS_GRAY = 1003;


    /**
     * Constructs a ColorSpace object given a color space type
     * and the number of components.
     * <p>
     *  构造一个给定颜色空间类型和组件数量的ColorSpace对象。
     * 
     * 
     * @param type one of the <CODE>ColorSpace</CODE> type constants
     * @param numcomponents the number of components in the color space
     */
    protected ColorSpace (int type, int numcomponents) {
        this.type = type;
        this.numComponents = numcomponents;
    }


    /**
     * Returns a ColorSpace representing one of the specific
     * predefined color spaces.
     * <p>
     *  返回表示特定预定义颜色空间之一的ColorSpace。
     * 
     * 
     * @param colorspace a specific color space identified by one of
     *        the predefined class constants (e.g. CS_sRGB, CS_LINEAR_RGB,
     *        CS_CIEXYZ, CS_GRAY, or CS_PYCC)
     * @return the requested <CODE>ColorSpace</CODE> object
     */
    // NOTE: This method may be called by privileged threads.
    //       DO NOT INVOKE CLIENT CODE ON THIS THREAD!
    public static ColorSpace getInstance (int colorspace)
    {
    ColorSpace    theColorSpace;

        switch (colorspace) {
        case CS_sRGB:
            synchronized(ColorSpace.class) {
                if (sRGBspace == null) {
                    ICC_Profile theProfile = ICC_Profile.getInstance (CS_sRGB);
                    sRGBspace = new ICC_ColorSpace (theProfile);
                }

                theColorSpace = sRGBspace;
            }
            break;

        case CS_CIEXYZ:
            synchronized(ColorSpace.class) {
                if (XYZspace == null) {
                    ICC_Profile theProfile =
                        ICC_Profile.getInstance (CS_CIEXYZ);
                    XYZspace = new ICC_ColorSpace (theProfile);
                }

                theColorSpace = XYZspace;
            }
            break;

        case CS_PYCC:
            synchronized(ColorSpace.class) {
                if (PYCCspace == null) {
                    ICC_Profile theProfile = ICC_Profile.getInstance (CS_PYCC);
                    PYCCspace = new ICC_ColorSpace (theProfile);
                }

                theColorSpace = PYCCspace;
            }
            break;


        case CS_GRAY:
            synchronized(ColorSpace.class) {
                if (GRAYspace == null) {
                    ICC_Profile theProfile = ICC_Profile.getInstance (CS_GRAY);
                    GRAYspace = new ICC_ColorSpace (theProfile);
                    /* to allow access from java.awt.ColorModel */
                    CMSManager.GRAYspace = GRAYspace;
                }

                theColorSpace = GRAYspace;
            }
            break;


        case CS_LINEAR_RGB:
            synchronized(ColorSpace.class) {
                if (LINEAR_RGBspace == null) {
                    ICC_Profile theProfile =
                        ICC_Profile.getInstance(CS_LINEAR_RGB);
                    LINEAR_RGBspace = new ICC_ColorSpace (theProfile);
                    /* to allow access from java.awt.ColorModel */
                    CMSManager.LINEAR_RGBspace = LINEAR_RGBspace;
                }

                theColorSpace = LINEAR_RGBspace;
            }
            break;


        default:
            throw new IllegalArgumentException ("Unknown color space");
        }

        return theColorSpace;
    }


    /**
     * Returns true if the ColorSpace is CS_sRGB.
     * <p>
     *  如果ColorSpace是CS_sRGB,则返回true。
     * 
     * 
     * @return <CODE>true</CODE> if this is a <CODE>CS_sRGB</CODE> color
     *         space, <code>false</code> if it is not
     */
    public boolean isCS_sRGB () {
        /* REMIND - make sure we know sRGBspace exists already */
        return (this == sRGBspace);
    }

    /**
     * Transforms a color value assumed to be in this ColorSpace
     * into a value in the default CS_sRGB color space.
     * <p>
     * This method transforms color values using algorithms designed
     * to produce the best perceptual match between input and output
     * colors.  In order to do colorimetric conversion of color values,
     * you should use the <code>toCIEXYZ</code>
     * method of this color space to first convert from the input
     * color space to the CS_CIEXYZ color space, and then use the
     * <code>fromCIEXYZ</code> method of the CS_sRGB color space to
     * convert from CS_CIEXYZ to the output color space.
     * See {@link #toCIEXYZ(float[]) toCIEXYZ} and
     * {@link #fromCIEXYZ(float[]) fromCIEXYZ} for further information.
     * <p>
     * <p>
     *  将假定为此ColorSpace中的颜色值转换为默认CS_sRGB颜色空间中的值。
     * <p>
     * 此方法使用设计为在输入和输出颜色之间产生最佳感知匹配的算法来变换颜色值。
     * 为了对颜色值进行比色转换,应该使用此颜色空间的<code> toCIEXYZ </code>方法,先将输入颜色空间转换为CS_CIEXYZ颜色空间,然后使用<code> fromCIEXYZ < / code>
     * 方法将CS_sRGB颜色空间从CS_CIEXYZ转换为输出颜色空间。
     * 此方法使用设计为在输入和输出颜色之间产生最佳感知匹配的算法来变换颜色值。
     * 更多信息,请参见{@link #toCIEXYZ(float [])toCIEXYZ}和{@link #fromCIEXYZ(float [])fromCIEXYZ}。
     * <p>
     * 
     * @param colorvalue a float array with length of at least the number
     *        of components in this ColorSpace
     * @return a float array of length 3
     * @throws ArrayIndexOutOfBoundsException if array length is not
     *         at least the number of components in this ColorSpace
     */
    public abstract float[] toRGB(float[] colorvalue);


    /**
     * Transforms a color value assumed to be in the default CS_sRGB
     * color space into this ColorSpace.
     * <p>
     * This method transforms color values using algorithms designed
     * to produce the best perceptual match between input and output
     * colors.  In order to do colorimetric conversion of color values,
     * you should use the <code>toCIEXYZ</code>
     * method of the CS_sRGB color space to first convert from the input
     * color space to the CS_CIEXYZ color space, and then use the
     * <code>fromCIEXYZ</code> method of this color space to
     * convert from CS_CIEXYZ to the output color space.
     * See {@link #toCIEXYZ(float[]) toCIEXYZ} and
     * {@link #fromCIEXYZ(float[]) fromCIEXYZ} for further information.
     * <p>
     * <p>
     *  将假定为在默认CS_sRGB颜色空间中的颜色值转换为此ColorSpace。
     * <p>
     *  此方法使用设计为在输入和输出颜色之间产生最佳感知匹配的算法来变换颜色值。
     * 为了对颜色值进行比色转换,应该使用CS_sRGB颜色空间的<code> toCIEXYZ </code>方法,先将输入颜色空间转换为CS_CIEXYZ颜色空间,然后使用<code> fromCIEXYZ
     *  </code>此颜色空间的方法从CS_CIEXYZ转换为输出颜色空间。
     *  此方法使用设计为在输入和输出颜色之间产生最佳感知匹配的算法来变换颜色值。
     * 更多信息,请参见{@link #toCIEXYZ(float [])toCIEXYZ}和{@link #fromCIEXYZ(float [])fromCIEXYZ}。
     * <p>
     * 
     * @param rgbvalue a float array with length of at least 3
     * @return a float array with length equal to the number of
     *         components in this ColorSpace
     * @throws ArrayIndexOutOfBoundsException if array length is not
     *         at least 3
     */
    public abstract float[] fromRGB(float[] rgbvalue);


    /**
     * Transforms a color value assumed to be in this ColorSpace
     * into the CS_CIEXYZ conversion color space.
     * <p>
     * This method transforms color values using relative colorimetry,
     * as defined by the International Color Consortium standard.  This
     * means that the XYZ values returned by this method are represented
     * relative to the D50 white point of the CS_CIEXYZ color space.
     * This representation is useful in a two-step color conversion
     * process in which colors are transformed from an input color
     * space to CS_CIEXYZ and then to an output color space.  This
     * representation is not the same as the XYZ values that would
     * be measured from the given color value by a colorimeter.
     * A further transformation is necessary to compute the XYZ values
     * that would be measured using current CIE recommended practices.
     * See the {@link ICC_ColorSpace#toCIEXYZ(float[]) toCIEXYZ} method of
     * <code>ICC_ColorSpace</code> for further information.
     * <p>
     * <p>
     *  将假定为此ColorSpace中的颜色值转换为CS_CIEXYZ转换颜色空间。
     * <p>
     * 该方法使用国际色彩联盟标准定义的相对比色法来变换颜色值。这意味着由此方法返回的XYZ值相对于CS_CIEXYZ颜色空间的D50白点表示。
     * 该表示在两步颜色转换过程中是有用的,其中颜色从输入颜色空间变换到CS_CIEXYZ,然后变换到输出颜色空间。该表示与将通过比色计从给定颜色值测量的XYZ值不同。
     * 需要进一步的变换来计算将使用当前CIE推荐实践来测量的XYZ值。
     * 有关更多信息,请参阅<code> ICC_ColorSpace </code>的{@link ICC_ColorSpace#toCIEXYZ(float [])toCIEXYZ}方法。
     * <p>
     * 
     * @param colorvalue a float array with length of at least the number
     *        of components in this ColorSpace
     * @return a float array of length 3
     * @throws ArrayIndexOutOfBoundsException if array length is not
     *         at least the number of components in this ColorSpace.
     */
    public abstract float[] toCIEXYZ(float[] colorvalue);


    /**
     * Transforms a color value assumed to be in the CS_CIEXYZ conversion
     * color space into this ColorSpace.
     * <p>
     * This method transforms color values using relative colorimetry,
     * as defined by the International Color Consortium standard.  This
     * means that the XYZ argument values taken by this method are represented
     * relative to the D50 white point of the CS_CIEXYZ color space.
     * This representation is useful in a two-step color conversion
     * process in which colors are transformed from an input color
     * space to CS_CIEXYZ and then to an output color space.  The color
     * values returned by this method are not those that would produce
     * the XYZ value passed to the method when measured by a colorimeter.
     * If you have XYZ values corresponding to measurements made using
     * current CIE recommended practices, they must be converted to D50
     * relative values before being passed to this method.
     * See the {@link ICC_ColorSpace#fromCIEXYZ(float[]) fromCIEXYZ} method of
     * <code>ICC_ColorSpace</code> for further information.
     * <p>
     * <p>
     *  将假定为CS_CIEXYZ转换颜色空间中的颜色值转换为此ColorSpace。
     * <p>
     * 该方法使用国际色彩联盟标准定义的相对比色法来变换颜色值。这意味着由该方法获取的XYZ参数值是相对于CS_CIEXYZ颜色空间的D50白点表示的。
     * 该表示在两步颜色转换过程中是有用的,其中颜色从输入颜色空间变换到CS_CIEXYZ,然后变换到输出颜色空间。由该方法返回的颜色值不是那些当通过色度计测量时将产生传递给该方法的XYZ值的颜色值。
     * 如果您具有与使用当前CIE推荐做法进行的测量相对应的XYZ值,则在传递给此方法之前,必须将它们转换为D50相对值。
     * 有关详细信息,请参阅<code> ICC_ColorSpace </code>的{@link ICC_ColorSpace#fromCIEXYZ(float [])fromCIEXYZ}方法。
     * <p>
     * 
     * @param colorvalue a float array with length of at least 3
     * @return a float array with length equal to the number of
     *         components in this ColorSpace
     * @throws ArrayIndexOutOfBoundsException if array length is not
     *         at least 3
     */
    public abstract float[] fromCIEXYZ(float[] colorvalue);

    /**
     * Returns the color space type of this ColorSpace (for example
     * TYPE_RGB, TYPE_XYZ, ...).  The type defines the
     * number of components of the color space and the interpretation,
     * e.g. TYPE_RGB identifies a color space with three components - red,
     * green, and blue.  It does not define the particular color
     * characteristics of the space, e.g. the chromaticities of the
     * primaries.
     *
     * <p>
     *  返回此ColorSpace的颜色空间类型(例如TYPE_RGB,TYPE_XYZ,...)。类型定义颜色空间的分量的数量和解释,例如。 TYPE_RGB用三个分量(红色,绿色和蓝色)来标识颜色空间。
     * 它不定义空间的特定颜色特性,例如。原色的色度。
     * 
     * 
     * @return the type constant that represents the type of this
     *         <CODE>ColorSpace</CODE>
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the number of components of this ColorSpace.
     * <p>
     *  返回此ColorSpace的组件数。
     * 
     * 
     * @return The number of components in this <CODE>ColorSpace</CODE>.
     */
    public int getNumComponents() {
        return numComponents;
    }

    /**
     * Returns the name of the component given the component index.
     *
     * <p>
     *  返回给定组件索引的组件的名称。
     * 
     * 
     * @param idx the component index
     * @return the name of the component at the specified index
     * @throws IllegalArgumentException if <code>idx</code> is
     *         less than 0 or greater than numComponents - 1
     */
    public String getName (int idx) {
        /* REMIND - handle common cases here */
        if ((idx < 0) || (idx > numComponents - 1)) {
            throw new IllegalArgumentException(
                "Component index out of range: " + idx);
        }

        if (compName == null) {
            switch (type) {
                case ColorSpace.TYPE_XYZ:
                    compName = new String[] {"X", "Y", "Z"};
                    break;
                case ColorSpace.TYPE_Lab:
                    compName = new String[] {"L", "a", "b"};
                    break;
                case ColorSpace.TYPE_Luv:
                    compName = new String[] {"L", "u", "v"};
                    break;
                case ColorSpace.TYPE_YCbCr:
                    compName = new String[] {"Y", "Cb", "Cr"};
                    break;
                case ColorSpace.TYPE_Yxy:
                    compName = new String[] {"Y", "x", "y"};
                    break;
                case ColorSpace.TYPE_RGB:
                    compName = new String[] {"Red", "Green", "Blue"};
                    break;
                case ColorSpace.TYPE_GRAY:
                    compName = new String[] {"Gray"};
                    break;
                case ColorSpace.TYPE_HSV:
                    compName = new String[] {"Hue", "Saturation", "Value"};
                    break;
                case ColorSpace.TYPE_HLS:
                    compName = new String[] {"Hue", "Lightness",
                                             "Saturation"};
                    break;
                case ColorSpace.TYPE_CMYK:
                    compName = new String[] {"Cyan", "Magenta", "Yellow",
                                             "Black"};
                    break;
                case ColorSpace.TYPE_CMY:
                    compName = new String[] {"Cyan", "Magenta", "Yellow"};
                    break;
                default:
                    String [] tmp = new String[numComponents];
                    for (int i = 0; i < tmp.length; i++) {
                        tmp[i] = "Unnamed color component(" + i + ")";
                    }
                    compName = tmp;
            }
        }
        return compName[idx];
    }

    /**
     * Returns the minimum normalized color component value for the
     * specified component.  The default implementation in this abstract
     * class returns 0.0 for all components.  Subclasses should override
     * this method if necessary.
     *
     * <p>
     * 返回指定组件的最小标准化颜色分量值。此抽象类中的默认实现对所有组件返回0.0。如果需要,子类应该覆盖此方法。
     * 
     * 
     * @param component the component index
     * @return the minimum normalized component value
     * @throws IllegalArgumentException if component is less than 0 or
     *         greater than numComponents - 1
     * @since 1.4
     */
    public float getMinValue(int component) {
        if ((component < 0) || (component > numComponents - 1)) {
            throw new IllegalArgumentException(
                "Component index out of range: " + component);
        }
        return 0.0f;
    }

    /**
     * Returns the maximum normalized color component value for the
     * specified component.  The default implementation in this abstract
     * class returns 1.0 for all components.  Subclasses should override
     * this method if necessary.
     *
     * <p>
     *  返回指定组件的最大标准化颜色分量值。此抽象类中的默认实现为所有组件返回1.0。如果需要,子类应该覆盖此方法。
     * 
     * 
     * @param component the component index
     * @return the maximum normalized component value
     * @throws IllegalArgumentException if component is less than 0 or
     *         greater than numComponents - 1
     * @since 1.4
     */
    public float getMaxValue(int component) {
        if ((component < 0) || (component > numComponents - 1)) {
            throw new IllegalArgumentException(
                "Component index out of range: " + component);
        }
        return 1.0f;
    }

    /* Returns true if cspace is the XYZspace.
    /* <p>
     */
    static boolean isCS_CIEXYZ(ColorSpace cspace) {
        return (cspace == XYZspace);
    }
}
