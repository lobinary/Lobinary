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

package java.awt.image;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import sun.java2d.cmm.CMSManager;
import sun.java2d.cmm.ColorTransform;
import sun.java2d.cmm.PCMM;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * The <code>ColorModel</code> abstract class encapsulates the
 * methods for translating a pixel value to color components
 * (for example, red, green, and blue) and an alpha component.
 * In order to render an image to the screen, a printer, or another
 * image, pixel values must be converted to color and alpha components.
 * As arguments to or return values from methods of this class,
 * pixels are represented as 32-bit ints or as arrays of primitive types.
 * The number, order, and interpretation of color components for a
 * <code>ColorModel</code> is specified by its <code>ColorSpace</code>.
 * A <code>ColorModel</code> used with pixel data that does not include
 * alpha information treats all pixels as opaque, which is an alpha
 * value of 1.0.
 * <p>
 * This <code>ColorModel</code> class supports two representations of
 * pixel values.  A pixel value can be a single 32-bit int or an
 * array of primitive types.  The Java(tm) Platform 1.0 and 1.1 APIs
 * represented pixels as single <code>byte</code> or single
 * <code>int</code> values.  For purposes of the <code>ColorModel</code>
 * class, pixel value arguments were passed as ints.  The Java(tm) 2
 * Platform API introduced additional classes for representing images.
 * With {@link BufferedImage} or {@link RenderedImage}
 * objects, based on {@link Raster} and {@link SampleModel} classes, pixel
 * values might not be conveniently representable as a single int.
 * Consequently, <code>ColorModel</code> now has methods that accept
 * pixel values represented as arrays of primitive types.  The primitive
 * type used by a particular <code>ColorModel</code> object is called its
 * transfer type.
 * <p>
 * <code>ColorModel</code> objects used with images for which pixel values
 * are not conveniently representable as a single int throw an
 * {@link IllegalArgumentException} when methods taking a single int pixel
 * argument are called.  Subclasses of <code>ColorModel</code> must
 * specify the conditions under which this occurs.  This does not
 * occur with {@link DirectColorModel} or {@link IndexColorModel} objects.
 * <p>
 * Currently, the transfer types supported by the Java 2D(tm) API are
 * DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT, DataBuffer.TYPE_INT,
 * DataBuffer.TYPE_SHORT, DataBuffer.TYPE_FLOAT, and DataBuffer.TYPE_DOUBLE.
 * Most rendering operations will perform much faster when using ColorModels
 * and images based on the first three of these types.  In addition, some
 * image filtering operations are not supported for ColorModels and
 * images based on the latter three types.
 * The transfer type for a particular <code>ColorModel</code> object is
 * specified when the object is created, either explicitly or by default.
 * All subclasses of <code>ColorModel</code> must specify what the
 * possible transfer types are and how the number of elements in the
 * primitive arrays representing pixels is determined.
 * <p>
 * For <code>BufferedImages</code>, the transfer type of its
 * <code>Raster</code> and of the <code>Raster</code> object's
 * <code>SampleModel</code> (available from the
 * <code>getTransferType</code> methods of these classes) must match that
 * of the <code>ColorModel</code>.  The number of elements in an array
 * representing a pixel for the <code>Raster</code> and
 * <code>SampleModel</code> (available from the
 * <code>getNumDataElements</code> methods of these classes) must match
 * that of the <code>ColorModel</code>.
 * <p>
 * The algorithm used to convert from pixel values to color and alpha
 * components varies by subclass.  For example, there is not necessarily
 * a one-to-one correspondence between samples obtained from the
 * <code>SampleModel</code> of a <code>BufferedImage</code> object's
 * <code>Raster</code> and color/alpha components.  Even when
 * there is such a correspondence, the number of bits in a sample is not
 * necessarily the same as the number of bits in the corresponding color/alpha
 * component.  Each subclass must specify how the translation from
 * pixel values to color/alpha components is done.
 * <p>
 * Methods in the <code>ColorModel</code> class use two different
 * representations of color and alpha components - a normalized form
 * and an unnormalized form.  In the normalized form, each component is a
 * <code>float</code> value between some minimum and maximum values.  For
 * the alpha component, the minimum is 0.0 and the maximum is 1.0.  For
 * color components the minimum and maximum values for each component can
 * be obtained from the <code>ColorSpace</code> object.  These values
 * will often be 0.0 and 1.0 (e.g. normalized component values for the
 * default sRGB color space range from 0.0 to 1.0), but some color spaces
 * have component values with different upper and lower limits.  These
 * limits can be obtained using the <code>getMinValue</code> and
 * <code>getMaxValue</code> methods of the <code>ColorSpace</code>
 * class.  Normalized color component values are not premultiplied.
 * All <code>ColorModels</code> must support the normalized form.
 * <p>
 * In the unnormalized
 * form, each component is an unsigned integral value between 0 and
 * 2<sup>n</sup> - 1, where n is the number of significant bits for a
 * particular component.  If pixel values for a particular
 * <code>ColorModel</code> represent color samples premultiplied by
 * the alpha sample, unnormalized color component values are
 * also premultiplied.  The unnormalized form is used only with instances
 * of <code>ColorModel</code> whose <code>ColorSpace</code> has minimum
 * component values of 0.0 for all components and maximum values of
 * 1.0 for all components.
 * The unnormalized form for color and alpha components can be a convenient
 * representation for <code>ColorModels</code> whose normalized component
 * values all lie
 * between 0.0 and 1.0.  In such cases the integral value 0 maps to 0.0 and
 * the value 2<sup>n</sup> - 1 maps to 1.0.  In other cases, such as
 * when the normalized component values can be either negative or positive,
 * the unnormalized form is not convenient.  Such <code>ColorModel</code>
 * objects throw an {@link IllegalArgumentException} when methods involving
 * an unnormalized argument are called.  Subclasses of <code>ColorModel</code>
 * must specify the conditions under which this occurs.
 *
 * <p>
 * <code> ColorModel </code>抽象类封装了将像素值转换为颜色分量(例如,红色,绿色和蓝色)和alpha分量的方法。
 * 为了将图像渲染到屏幕,或另一个图像,像素值必须转换为颜色和alpha分量作为该类方法的参数或返回值,像素表示为32位int或基元类型数组颜色分量的数量,顺序和解释对于<code> ColorModel 
 * </code>是由其<code> ColorSpace </code> A <code> ColorModel </code>指定的,用于不包括alpha信息的像素数据,将所有像素视为不透明, alph
 * a值为10。
 * <code> ColorModel </code>抽象类封装了将像素值转换为颜色分量(例如,红色,绿色和蓝色)和alpha分量的方法。
 * <p>
 * 这个<code> ColorModel </code>类支持像素值的两个表示像素值可以是单个32位int或基本类型数组Java(tm)Platform 10和11 API将像素表示为单个<code>字节
 * </code>或单个<code> int </code>值为了<code> ColorModel </code>类的目的,像素值参数以int的形式传递Java(tm)图像使用{@link BufferedImage}
 * 或{@link RenderedImage}对象,基于{@link Raster}和{@link SampleModel}类,像素值可能不能方便地表示为单个int因此,<code> ColorModel
 *  </code >现在有接受表示为基本类型数组的像素值的方法由特定<code> ColorModel </code>对象使用的原始类型称为其传输类型。
 * <p>
 * <code> ColorModel </code>对象与像素值不能方便地表示为单个int的图像一起使用{@link IllegalArgumentException}当采用单个int像素参数的方法被称为
 * <code> ColorModel的子类</code >必须指定发生这种情况的条件这不会发生在{@link DirectColorModel}或{@link IndexColorModel}对象。
 * <p>
 * 目前,Java 2D(tm)API支持的传输类型是DataBufferTYPE_BYTE,DataBufferTYPE_USHORT,DataBufferTYPE_INT,DataBufferTYPE_
 * SHORT,DataBufferTYPE_FLOAT和DataBufferTYPE_DOUBLE。
 * 当使用ColorModel和基于前三种类型的映像时,大多数渲染操作的执行速度要快得多。
 * 此外,某些图像过滤操作不支持ColorModel和基于后三种类型的图像当创建对象时,指定特定<code> ColorModel </code>对象的传输类型,无论是显式还是默认情况下<code>的所有子
 * 类> ColorModel </code>必须指定可能的传输类型以及如何确定表示像素的基本数组中的元素数量。
 * 当使用ColorModel和基于前三种类型的映像时,大多数渲染操作的执行速度要快得多。
 * <p>
 * 对于<code> BufferedImages </code>,其<code> Raster </code>和<code> Raster </code>对象<code> SampleModel </code>
 * 的传输类型(可从<code>这些类的getTransferType </code>方法)必须与<code> ColorModel </code>匹配。
 * 数组中表示<code> Raster </code>和<code> SampleModel </code>代码>(可从这些类的<code> getNumDataElements </code>方法中获得
 * )必须匹配<code> ColorModel </code>。
 * <p>
 * 用于从像素值转换为颜色和α分量的算法根据子类而变化。
 * 例如,从<code> BufferedImage </code>的<code> SampleModel </code>获得的样本之间不一定有一一对应关系, / code>对象的<code>光栅</code>
 * 和颜色/ alpha分量即使有这样的对应关系,样本中的位数也不一定与对应的颜色/ alpha分量中的位数相同。
 * 用于从像素值转换为颜色和α分量的算法根据子类而变化。子类必须指定如何从像素值到颜色/ alpha组件的转换。
 * <p>
 * <code> ColorModel </code>类中的方法使用颜色和alpha组件的两种不同表示形式 - 规范化形式和非规范化形式在规范化形式中,每个组件是一个<code> float </code>
 * 和最大值对于alpha分量,最小值为00,最大值为10.对于颜色分量,可以从<code> ColorSpace </code>对象中获取每个分量的最小值和最大值这些值通常为00和10 (例如,默认sRG
 * B颜色空间范围从00到10的归一化分量值),但是一些颜色空间具有具有不同上限和下限的分量值这些限制可以使用<code> ColorSpace </code>类的<code> getMinValue </code>
 * 和<code> getMaxValue </code>方法获得。
 * 标准化颜色分量值不预乘所有<code> ColorModels <代码>必须支持规范化形式。
 * <p>
 * 在非归一化形式中,每个分量是0和2之间的无符号整数值,其中n是特定分量的有效位的数量。
 * 如果特定<code> ColorModel < / code>表示由α样本预乘的颜色样本,非规格化的颜色分量值也被预乘。
 * 非规格化形式仅用于<code> ColorModel </code>的实例,其<Color> </code> 00,所有分量的最大值为10对于颜色和alpha分量的非标准化形式可以是其规范化分量值都在0
 * 0和10之间的<code> ColorModels </code>的方便表示。
 * 如果特定<code> ColorModel < / code>表示由α样本预乘的颜色样本,非规格化的颜色分量值也被预乘。
 * 在这种情况下, 0映射到00并且值2 <sup> n </sup> -1映射到10.在其他情况下,例如当归一化分量值可以是负数或正数时,非规格化形式不方便这样的<code> ColorModel </code >
 *  objects抛出一个{@link IllegalArgumentException}当涉及非标准化参数的方法被调用<code> ColorModel </code>的子类必须指定发生这种情况的条件。
 * 如果特定<code> ColorModel < / code>表示由α样本预乘的颜色样本,非规格化的颜色分量值也被预乘。
 * 
 * 
 * @see IndexColorModel
 * @see ComponentColorModel
 * @see PackedColorModel
 * @see DirectColorModel
 * @see java.awt.Image
 * @see BufferedImage
 * @see RenderedImage
 * @see java.awt.color.ColorSpace
 * @see SampleModel
 * @see Raster
 * @see DataBuffer
 */
public abstract class ColorModel implements Transparency{
    private long pData;         // Placeholder for data for native functions

    /**
     * The total number of bits in the pixel.
     * <p>
     * 像素中的总位数
     * 
     */
    protected int pixel_bits;
    int nBits[];
    int transparency = Transparency.TRANSLUCENT;
    boolean supportsAlpha = true;
    boolean isAlphaPremultiplied = false;
    int numComponents = -1;
    int numColorComponents = -1;
    ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
    int colorSpaceType = ColorSpace.TYPE_RGB;
    int maxBits;
    boolean is_sRGB = true;

    /**
     * Data type of the array used to represent pixel values.
     * <p>
     *  用于表示像素值的数组的数据类型
     * 
     */
    protected int transferType;

    /**
     * This is copied from java.awt.Toolkit since we need the library
     * loaded in java.awt.image also:
     *
     * WARNING: This is a temporary workaround for a problem in the
     * way the AWT loads native libraries. A number of classes in the
     * AWT package have a native method, initIDs(), which initializes
     * the JNI field and method ids used in the native portion of
     * their implementation.
     *
     * Since the use and storage of these ids is done by the
     * implementation libraries, the implementation of these method is
     * provided by the particular AWT implementations (for example,
     * "Toolkit"s/Peer), such as Motif, Microsoft Windows, or Tiny. The
     * problem is that this means that the native libraries must be
     * loaded by the java.* classes, which do not necessarily know the
     * names of the libraries to load. A better way of doing this
     * would be to provide a separate library which defines java.awt.*
     * initIDs, and exports the relevant symbols out to the
     * implementation libraries.
     *
     * For now, we know it's done by the implementation, and we assume
     * that the name of the library is "awt".  -br.
     * <p>
     *  这是从javaawtToolkit复制,因为我们需要在javaawtimage中加载的库：
     * 
     *  警告：这是AWT加载本机库的方式中的问题的临时解决方法AWT包中的许多类都有一个本地方法initIDs(),它初始化JNI字段和在它们的本地部分中使用的方法ID实施
     * 
     * 由于这些ID的使用和存储由实现库完成,这些方法的实现由特定的AWT实现(例如,"Toolkit / s / Peer")提供,例如Motif,Microsoft Windows或Tiny。
     * 这意味着本地库必须由java *类加载,这不一定知道要加载的库的名称。更好的方法是提供一个单独的库,定义javaawt * initIDs和exports相关符号到实现库。
     * 
     *  现在,我们知道它是由实现完成的,我们假设库的名称是"awt"-br
     * 
     */
    private static boolean loaded = false;
    static void loadLibraries() {
        if (!loaded) {
            java.security.AccessController.doPrivileged(
                new java.security.PrivilegedAction<Void>() {
                    public Void run() {
                        System.loadLibrary("awt");
                        return null;
                    }
                });
            loaded = true;
        }
    }
    private static native void initIDs();
    static {
        /* ensure that the proper libraries are loaded */
        loadLibraries();
        initIDs();
    }
    private static ColorModel RGBdefault;

    /**
     * Returns a <code>DirectColorModel</code> that describes the default
     * format for integer RGB values used in many of the methods in the
     * AWT image interfaces for the convenience of the programmer.
     * The color space is the default {@link ColorSpace}, sRGB.
     * The format for the RGB values is an integer with 8 bits
     * each of alpha, red, green, and blue color components ordered
     * correspondingly from the most significant byte to the least
     * significant byte, as in:  0xAARRGGBB.  Color components are
     * not premultiplied by the alpha component.  This format does not
     * necessarily represent the native or the most efficient
     * <code>ColorModel</code> for a particular device or for all images.
     * It is merely used as a common color model format.
     * <p>
     * 返回一个<code> DirectColorModel </code>,它描述了AWT图像接口中许多方法中使用的整数RGB值的默认格式,以方便程序员颜色空间是默认的{@link ColorSpace},
     * sRGB RGB值的格式是具有从最高有效字节到最低有效字节相应地排序的α,红,绿和蓝色分量的8位的整数,如下：0xAARRGGBB颜色分量不被α分量预乘格式不一定表示特定设备或所有图像的本机或最有效的<code>
     *  ColorModel </code>它仅用作常见的颜色模型格式。
     * 
     * 
     * @return a <code>DirectColorModel</code>object describing default
     *          RGB values.
     */
    public static ColorModel getRGBdefault() {
        if (RGBdefault == null) {
            RGBdefault = new DirectColorModel(32,
                                              0x00ff0000,       // Red
                                              0x0000ff00,       // Green
                                              0x000000ff,       // Blue
                                              0xff000000        // Alpha
                                              );
        }
        return RGBdefault;
    }

    /**
     * Constructs a <code>ColorModel</code> that translates pixels of the
     * specified number of bits to color/alpha components.  The color
     * space is the default RGB <code>ColorSpace</code>, which is sRGB.
     * Pixel values are assumed to include alpha information.  If color
     * and alpha information are represented in the pixel value as
     * separate spatial bands, the color bands are assumed not to be
     * premultiplied with the alpha value. The transparency type is
     * java.awt.Transparency.TRANSLUCENT.  The transfer type will be the
     * smallest of DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT,
     * or DataBuffer.TYPE_INT that can hold a single pixel
     * (or DataBuffer.TYPE_UNDEFINED if bits is greater
     * than 32).  Since this constructor has no information about the
     * number of bits per color and alpha component, any subclass calling
     * this constructor should override any method that requires this
     * information.
     * <p>
     * 构造一个<code> ColorModel </code>,将指定位数的像素转换为颜色/ alpha分量颜色空间是默认的RGB <code> ColorSpace </code>,即sRGB。
     * 假设像素值包括alpha信息如果颜色和alpha信息在像素值中表示为单独的空间带,则颜色带假定不与alpha值预乘。
     * 透明度类型为javaawtTransparencyTRANSLUCENT传输类型将是DataBufferTYPE_BYTE,DataBufferTYPE_USHORT或DataBufferTYPE_IN
     * T中最小的可以保存单个像素(如果位大于32,则为DataBufferTYPE_UNDEFINED)由于此构造函数没有关于每个颜色和alpha组件的位数的信息,任何调用此构造函数的子类都应覆盖任何需要此信
     * 息的方法。
     * 假设像素值包括alpha信息如果颜色和alpha信息在像素值中表示为单独的空间带,则颜色带假定不与alpha值预乘。
     * 
     * 
     * @param bits the number of bits of a pixel
     * @throws IllegalArgumentException if the number
     *          of bits in <code>bits</code> is less than 1
     */
    public ColorModel(int bits) {
        pixel_bits = bits;
        if (bits < 1) {
            throw new IllegalArgumentException("Number of bits must be > 0");
        }
        numComponents = 4;
        numColorComponents = 3;
        maxBits = bits;
        // REMIND: make sure transferType is set correctly
        transferType = ColorModel.getDefaultTransferType(bits);
    }

    /**
     * Constructs a <code>ColorModel</code> that translates pixel values
     * to color/alpha components.  Color components will be in the
     * specified <code>ColorSpace</code>. <code>pixel_bits</code> is the
     * number of bits in the pixel values.  The bits array
     * specifies the number of significant bits per color and alpha component.
     * Its length should be the number of components in the
     * <code>ColorSpace</code> if there is no alpha information in the
     * pixel values, or one more than this number if there is alpha
     * information.  <code>hasAlpha</code> indicates whether or not alpha
     * information is present.  The <code>boolean</code>
     * <code>isAlphaPremultiplied</code> specifies how to interpret pixel
     * values in which color and alpha information are represented as
     * separate spatial bands.  If the <code>boolean</code>
     * is <code>true</code>, color samples are assumed to have been
     * multiplied by the alpha sample.  The <code>transparency</code>
     * specifies what alpha values can be represented by this color model.
     * The transfer type is the type of primitive array used to represent
     * pixel values.  Note that the bits array contains the number of
     * significant bits per color/alpha component after the translation
     * from pixel values.  For example, for an
     * <code>IndexColorModel</code> with <code>pixel_bits</code> equal to
     * 16, the bits array might have four elements with each element set
     * to 8.
     * <p>
     * 构造将像素值转换为颜色/ alpha组件的<code> ColorModel </code>颜色组件将在指定的<code> ColorSpace </code> <code> pixel_bits </code>
     * 是像素中的位数values bits数组指定每个颜色和alpha分量的有效位数如果像素值中没有alpha信息,或者超过此数字,则其长度应为<code> ColorSpace </code>中的组件数如果
     * 有alpha信息<code> hasAlpha </code>指示是否存在alpha信息<code> boolean </code> <code> isAlphaPremultiplied </code>
     * 指定如何解释像素值,其中颜色和alpha信息被表示为单独的空间频带如果<code> boolean </code>是<code> true </code>,则颜色样本被假定已经乘以alpha样本。
     * <code> transparency </code>指定什么alpha值可以由颜色模型传输类型是用于表示像素值的基本数组类型请注意,bits数组包含从像素值转换后每个颜色/ alpha组件的有效位数。
     * 例如,对于<code> IndexColorModel </code >与<code> pixel_bits </code>等于16,位数组可能有四个元素,每个元素设置为8。
     * 
     * 
     * @param pixel_bits the number of bits in the pixel values
     * @param bits array that specifies the number of significant bits
     *          per color and alpha component
     * @param cspace the specified <code>ColorSpace</code>
     * @param hasAlpha <code>true</code> if alpha information is present;
     *          <code>false</code> otherwise
     * @param isAlphaPremultiplied <code>true</code> if color samples are
     *          assumed to be premultiplied by the alpha samples;
     *          <code>false</code> otherwise
     * @param transparency what alpha values can be represented by this
     *          color model
     * @param transferType the type of the array used to represent pixel
     *          values
     * @throws IllegalArgumentException if the length of
     *          the bit array is less than the number of color or alpha
     *          components in this <code>ColorModel</code>, or if the
     *          transparency is not a valid value.
     * @throws IllegalArgumentException if the sum of the number
     *          of bits in <code>bits</code> is less than 1 or if
     *          any of the elements in <code>bits</code> is less than 0.
     * @see java.awt.Transparency
     */
    protected ColorModel(int pixel_bits, int[] bits, ColorSpace cspace,
                         boolean hasAlpha,
                         boolean isAlphaPremultiplied,
                         int transparency,
                         int transferType) {
        colorSpace                = cspace;
        colorSpaceType            = cspace.getType();
        numColorComponents        = cspace.getNumComponents();
        numComponents             = numColorComponents + (hasAlpha ? 1 : 0);
        supportsAlpha             = hasAlpha;
        if (bits.length < numComponents) {
            throw new IllegalArgumentException("Number of color/alpha "+
                                               "components should be "+
                                               numComponents+
                                               " but length of bits array is "+
                                               bits.length);
        }

        // 4186669
        if (transparency < Transparency.OPAQUE ||
            transparency > Transparency.TRANSLUCENT)
        {
            throw new IllegalArgumentException("Unknown transparency: "+
                                               transparency);
        }

        if (supportsAlpha == false) {
            this.isAlphaPremultiplied = false;
            this.transparency = Transparency.OPAQUE;
        }
        else {
            this.isAlphaPremultiplied = isAlphaPremultiplied;
            this.transparency         = transparency;
        }

        nBits = bits.clone();
        this.pixel_bits = pixel_bits;
        if (pixel_bits <= 0) {
            throw new IllegalArgumentException("Number of pixel bits must "+
                                               "be > 0");
        }
        // Check for bits < 0
        maxBits = 0;
        for (int i=0; i < bits.length; i++) {
            // bug 4304697
            if (bits[i] < 0) {
                throw new
                    IllegalArgumentException("Number of bits must be >= 0");
            }
            if (maxBits < bits[i]) {
                maxBits = bits[i];
            }
        }

        // Make sure that we don't have all 0-bit components
        if (maxBits == 0) {
            throw new IllegalArgumentException("There must be at least "+
                                               "one component with > 0 "+
                                              "pixel bits.");
        }

        // Save this since we always need to check if it is the default CS
        if (cspace != ColorSpace.getInstance(ColorSpace.CS_sRGB)) {
            is_sRGB = false;
        }

        // Save the transfer type
        this.transferType = transferType;
    }

    /**
     * Returns whether or not alpha is supported in this
     * <code>ColorModel</code>.
     * <p>
     * 返回此<code> ColorModel </code>中是否支持alpha
     * 
     * 
     * @return <code>true</code> if alpha is supported in this
     * <code>ColorModel</code>; <code>false</code> otherwise.
     */
    final public boolean hasAlpha() {
        return supportsAlpha;
    }

    /**
     * Returns whether or not the alpha has been premultiplied in the
     * pixel values to be translated by this <code>ColorModel</code>.
     * If the boolean is <code>true</code>, this <code>ColorModel</code>
     * is to be used to interpret pixel values in which color and alpha
     * information are represented as separate spatial bands, and color
     * samples are assumed to have been multiplied by the
     * alpha sample.
     * <p>
     *  返回是否已经在要通过<code> ColorModel </code>翻译的像素值中预乘alpha。
     * 如果布尔值是<code> true </code>,则此<code> ColorModel </code>用于解释像素值,其中颜色和α信息被表示为单独的空间带,并且假设颜色样本已经乘以α样本。
     * 
     * 
     * @return <code>true</code> if the alpha values are premultiplied
     *          in the pixel values to be translated by this
     *          <code>ColorModel</code>; <code>false</code> otherwise.
     */
    final public boolean isAlphaPremultiplied() {
        return isAlphaPremultiplied;
    }

    /**
     * Returns the transfer type of this <code>ColorModel</code>.
     * The transfer type is the type of primitive array used to represent
     * pixel values as arrays.
     * <p>
     *  返回此<code> ColorModel </code>的传输类型传输类型是用于将像素值表示为数组的基本数组类型
     * 
     * 
     * @return the transfer type.
     * @since 1.3
     */
    final public int getTransferType() {
        return transferType;
    }

    /**
     * Returns the number of bits per pixel described by this
     * <code>ColorModel</code>.
     * <p>
     *  返回由此<code> ColorModel </code>描述的每像素的位数
     * 
     * 
     * @return the number of bits per pixel.
     */
    public int getPixelSize() {
        return pixel_bits;
    }

    /**
     * Returns the number of bits for the specified color/alpha component.
     * Color components are indexed in the order specified by the
     * <code>ColorSpace</code>.  Typically, this order reflects the name
     * of the color space type. For example, for TYPE_RGB, index 0
     * corresponds to red, index 1 to green, and index 2
     * to blue.  If this <code>ColorModel</code> supports alpha, the alpha
     * component corresponds to the index following the last color
     * component.
     * <p>
     * 返回指定颜色/ alpha组件的位数颜色组件按照<code> ColorSpace </code>指定的顺序索引。
     * 通常,此顺序反映颜色空间类型的名称例如,对于TYPE_RGB,索引为0对应于红色,索引1到绿色,索引2到蓝色如果此<code> ColorModel </code>支持alpha,alpha组件对应于最
     * 后一个颜色组件之后的索引。
     * 返回指定颜色/ alpha组件的位数颜色组件按照<code> ColorSpace </code>指定的顺序索引。
     * 
     * 
     * @param componentIdx the index of the color/alpha component
     * @return the number of bits for the color/alpha component at the
     *          specified index.
     * @throws ArrayIndexOutOfBoundsException if <code>componentIdx</code>
     *         is greater than the number of components or
     *         less than zero
     * @throws NullPointerException if the number of bits array is
     *         <code>null</code>
     */
    public int getComponentSize(int componentIdx) {
        // REMIND:
        if (nBits == null) {
            throw new NullPointerException("Number of bits array is null.");
        }

        return nBits[componentIdx];
    }

    /**
     * Returns an array of the number of bits per color/alpha component.
     * The array contains the color components in the order specified by the
     * <code>ColorSpace</code>, followed by the alpha component, if
     * present.
     * <p>
     *  返回每个颜色/ alpha组件的位数的数组该数组按照<code> ColorSpace </code>指定的顺序包含颜色组件,后跟alpha组件(如果存在)
     * 
     * 
     * @return an array of the number of bits per color/alpha component
     */
    public int[] getComponentSize() {
        if (nBits != null) {
            return nBits.clone();
        }

        return null;
    }

    /**
     * Returns the transparency.  Returns either OPAQUE, BITMASK,
     * or TRANSLUCENT.
     * <p>
     *  返回透明度返回OPAQUE,BITMASK或TRANSLUCENT
     * 
     * 
     * @return the transparency of this <code>ColorModel</code>.
     * @see Transparency#OPAQUE
     * @see Transparency#BITMASK
     * @see Transparency#TRANSLUCENT
     */
    public int getTransparency() {
        return transparency;
    }

    /**
     * Returns the number of components, including alpha, in this
     * <code>ColorModel</code>.  This is equal to the number of color
     * components, optionally plus one, if there is an alpha component.
     * <p>
     * 返回这个<code> ColorModel </code>中的组件数,包括alpha。这等于颜色组件的数量,如果有一个alpha组件,可以加上一个
     * 
     * 
     * @return the number of components in this <code>ColorModel</code>
     */
    public int getNumComponents() {
        return numComponents;
    }

    /**
     * Returns the number of color components in this
     * <code>ColorModel</code>.
     * This is the number of components returned by
     * {@link ColorSpace#getNumComponents}.
     * <p>
     *  返回这个<code> ColorModel </code>中的颜色分量的数量这是{@link ColorSpace#getNumComponents}返回的分量数量,
     * 
     * 
     * @return the number of color components in this
     * <code>ColorModel</code>.
     * @see ColorSpace#getNumComponents
     */
    public int getNumColorComponents() {
        return numColorComponents;
    }

    /**
     * Returns the red color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB ColorSpace, sRGB.  A color conversion
     * is done if necessary.  The pixel value is specified as an int.
     * An <code>IllegalArgumentException</code> is thrown if pixel
     * values for this <code>ColorModel</code> are not conveniently
     * representable as a single int.  The returned value is not a
     * pre-multiplied value.  For example, if the
     * alpha is premultiplied, this method divides it out before returning
     * the value.  If the alpha value is 0, the red value is 0.
     * <p>
     * 返回指定像素的红色分量,在默认RGB ColorSpace中从0到255缩放。
     * sRGB如果必要,进行颜色转换像素值指定为int如果像素值为0,则抛出<code> IllegalArgumentException </code>这个<code> ColorModel </code>
     * 的值不能方便地表示为单个int返回的值不是预乘的值例如,如果alpha被预乘,该方法在返回值之前将其分割出来。
     * 返回指定像素的红色分量,在默认RGB ColorSpace中从0到255缩放。如果alpha值为0,红色值为0。
     * 
     * 
     * @param pixel a specified pixel
     * @return the value of the red component of the specified pixel.
     */
    public abstract int getRed(int pixel);

    /**
     * Returns the green color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB ColorSpace, sRGB.  A color conversion
     * is done if necessary.  The pixel value is specified as an int.
     * An <code>IllegalArgumentException</code> is thrown if pixel
     * values for this <code>ColorModel</code> are not conveniently
     * representable as a single int.  The returned value is a non
     * pre-multiplied value.  For example, if the alpha is premultiplied,
     * this method divides it out before returning
     * the value.  If the alpha value is 0, the green value is 0.
     * <p>
     * 返回指定像素的绿色分量,在默认RGB ColorSpace中从0到255缩放。
     * sRGB如果必要,进行颜色转换像素值指定为int如果像素值为0,则抛出<code> IllegalArgumentException </code>这个<code> ColorModel </code>
     * 的值不能方便地表示为单个int返回的值是一个非预乘的值例如,如果alpha被预乘,该方法在返回值之前将其分割出来。
     * 返回指定像素的绿色分量,在默认RGB ColorSpace中从0到255缩放。如果alpha值为0,绿色值为0。
     * 
     * 
     * @param pixel the specified pixel
     * @return the value of the green component of the specified pixel.
     */
    public abstract int getGreen(int pixel);

    /**
     * Returns the blue color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB ColorSpace, sRGB.  A color conversion
     * is done if necessary.  The pixel value is specified as an int.
     * An <code>IllegalArgumentException</code> is thrown if pixel values
     * for this <code>ColorModel</code> are not conveniently representable
     * as a single int.  The returned value is a non pre-multiplied
     * value, for example, if the alpha is premultiplied, this method
     * divides it out before returning the value.  If the alpha value is
     * 0, the blue value is 0.
     * <p>
     * 返回指定像素的蓝色颜色分量,在默认RGB ColorSpace中从0到255缩放。
     * sRGB如果必要,进行颜色转换像素值被指定为int如果像素的像素值被指定为<code> IllegalArgumentException </code>此<code> ColorModel </code>
     * 的值不能方便地表示为单个int返回的值是非预乘的值,例如,如果alpha被预乘,则此方法在返回值之前将其除后如果alpha值为0,蓝色值为0。
     * 返回指定像素的蓝色颜色分量,在默认RGB ColorSpace中从0到255缩放。
     * 
     * 
     * @param pixel the specified pixel
     * @return the value of the blue component of the specified pixel.
     */
    public abstract int getBlue(int pixel);

    /**
     * Returns the alpha component for the specified pixel, scaled
     * from 0 to 255.  The pixel value is specified as an int.
     * An <code>IllegalArgumentException</code> is thrown if pixel
     * values for this <code>ColorModel</code> are not conveniently
     * representable as a single int.
     * <p>
     * 返回指定像素的alpha分量,从0到255缩放像素值指定为int如果<code> ColorModel </code>的像素值不是可方便表示的,则抛出<code> IllegalArgumentExce
     * ption </code>作为单个int。
     * 
     * 
     * @param pixel the specified pixel
     * @return the value of alpha component of the specified pixel.
     */
    public abstract int getAlpha(int pixel);

    /**
     * Returns the color/alpha components of the pixel in the default
     * RGB color model format.  A color conversion is done if necessary.
     * The pixel value is specified as an int.
     * An <code>IllegalArgumentException</code> thrown if pixel values
     * for this <code>ColorModel</code> are not conveniently representable
     * as a single int.  The returned value is in a non
     * pre-multiplied format. For example, if the alpha is premultiplied,
     * this method divides it out of the color components.  If the alpha
     * value is 0, the color values are 0.
     * <p>
     *  以默认的RGB颜色模型格式返回像素的颜色/ alpha分量如果需要,进行颜色转换像素值指定为int如果<code> ColorModel的像素值,则抛出<code> IllegalArgumentEx
     * ception </code> </code>不能方便地表示为单个int返回的值是非预扩展格式例如,如果alpha被预乘,该方法将其从颜色分量中分割出来。
     * 如果alpha值为0,颜色值为0。
     * 
     * 
     * @param pixel the specified pixel
     * @return the RGB value of the color/alpha components of the
     *          specified pixel.
     * @see ColorModel#getRGBdefault
     */
    public int getRGB(int pixel) {
        return (getAlpha(pixel) << 24)
            | (getRed(pixel) << 16)
            | (getGreen(pixel) << 8)
            | (getBlue(pixel) << 0);
    }

    /**
     * Returns the red color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is
     * specified by an array of data elements of type transferType passed
     * in as an object reference.  The returned value is a non
     * pre-multiplied value.  For example, if alpha is premultiplied,
     * this method divides it out before returning
     * the value.  If the alpha value is 0, the red value is 0.
     * If <code>inData</code> is not a primitive array of type
     * transferType, a <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * <code>inData</code> is not large enough to hold a pixel value for
     * this <code>ColorModel</code>.
     * If this <code>transferType</code> is not supported, a
     * <code>UnsupportedOperationException</code> will be
     * thrown.  Since
     * <code>ColorModel</code> is an abstract class, any instance
     * must be an instance of a subclass.  Subclasses inherit the
     * implementation of this method and if they don't override it, this
     * method throws an exception if the subclass uses a
     * <code>transferType</code> other than
     * <code>DataBuffer.TYPE_BYTE</code>,
     * <code>DataBuffer.TYPE_USHORT</code>, or
     * <code>DataBuffer.TYPE_INT</code>.
     * <p>
     * 返回指定像素的红色分量,在默认的RGB <code> ColorSpace </code>,sRGB中从0到255缩放如果必要,进行颜色转换像素值由transferType类型的数据元素数组指定作为对象
     * 引用传递返回的值是非预乘的值例如,如果alpha被预乘,此方法在返回值之前将其分割出来如果alpha值为0,红色值为0如果<code> inData </code>不是类型transferType的原始
     * 数组,则抛出<code> ClassCastException </code>如果<code> inData </code>不足够大,则抛出<code> ArrayIndexOutOfBoundsExc
     * eption </code>这个<code> ColorModel </code>的像素值如果不支持此<code> transferType </code>,将抛出<code> UnsupportedO
     * perationException </code>由于<code> ColorModel </code>是抽象类,所以任何实例必须是子类的子类继承此方法的实现,如果它们不覆盖它,如果子类使用除<code>
     *  DataBufferTYPE_BYTE </code>,<code> DataBufferTYPE_USHORT </code>之外的<code> transferType </code>代码>或<code>
     *  DataBufferTYPE_INT </code>。
     * 
     * 
     * @param inData an array of pixel values
     * @return the value of the red component of the specified pixel.
     * @throws ClassCastException if <code>inData</code>
     *  is not a primitive array of type <code>transferType</code>
     * @throws ArrayIndexOutOfBoundsException if
     *  <code>inData</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code>
     * @throws UnsupportedOperationException if this
     *  <code>tranferType</code> is not supported by this
     *  <code>ColorModel</code>
     */
    public int getRed(Object inData) {
        int pixel=0,length=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
               length = bdata.length;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
               length = sdata.length;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
               length = idata.length;
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        if (length == 1) {
            return getRed(pixel);
        }
        else {
            throw new UnsupportedOperationException
                ("This method is not supported by this color model");
        }
    }

    /**
     * Returns the green color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is
     * specified by an array of data elements of type transferType passed
     * in as an object reference.  The returned value will be a non
     * pre-multiplied value.  For example, if the alpha is premultiplied,
     * this method divides it out before returning the value.  If the
     * alpha value is 0, the green value is 0.  If <code>inData</code> is
     * not a primitive array of type transferType, a
     * <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * <code>inData</code> is not large enough to hold a pixel value for
     * this <code>ColorModel</code>.
     * If this <code>transferType</code> is not supported, a
     * <code>UnsupportedOperationException</code> will be
     * thrown.  Since
     * <code>ColorModel</code> is an abstract class, any instance
     * must be an instance of a subclass.  Subclasses inherit the
     * implementation of this method and if they don't override it, this
     * method throws an exception if the subclass uses a
     * <code>transferType</code> other than
     * <code>DataBuffer.TYPE_BYTE</code>,
     * <code>DataBuffer.TYPE_USHORT</code>, or
     * <code>DataBuffer.TYPE_INT</code>.
     * <p>
     * 返回指定像素的绿色分量,在默认的RGB中从0到255缩放<code> ColorSpace </code>,sRGB如果需要,进行颜色转换像素值由transferType类型的数据元素数组指定作为对象引
     * 用传递返回的值将是非预乘的值例如,如果alpha被预乘,此方法在返回值之前将其分割出来如果alpha值为0,则绿色值为0如果<code > inData </code>不是类型transferType的
     * 原始数组,则抛出<code> ClassCastException </code>如果<code> inData </code>不够大,则抛出<code> ArrayIndexOutOfBoundsEx
     * ception </code>以保存此<code> ColorModel </code>的像素值如果不支持此<code> transferType </code>,将抛出<code> Unsupport
     * edOperationException </code>由于<code> ColorModel </code>是抽象类,所以任何实例必须是子类的子类继承此方法的实现,如果它们不覆盖它,如果子类使用除<code>
     *  DataBufferTYPE_BYTE </code>,<code> DataBufferTYPE_USHORT </code>之外的<code> transferType </code>代码>或<code>
     *  DataBufferTYPE_INT </code>。
     * 
     * 
     * @param inData an array of pixel values
     * @return the value of the green component of the specified pixel.
     * @throws ClassCastException if <code>inData</code>
     *  is not a primitive array of type <code>transferType</code>
     * @throws ArrayIndexOutOfBoundsException if
     *  <code>inData</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code>
     * @throws UnsupportedOperationException if this
     *  <code>tranferType</code> is not supported by this
     *  <code>ColorModel</code>
     */
    public int getGreen(Object inData) {
        int pixel=0,length=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
               length = bdata.length;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
               length = sdata.length;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
               length = idata.length;
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        if (length == 1) {
            return getGreen(pixel);
        }
        else {
            throw new UnsupportedOperationException
                ("This method is not supported by this color model");
        }
    }

    /**
     * Returns the blue color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is
     * specified by an array of data elements of type transferType passed
     * in as an object reference.  The returned value is a non
     * pre-multiplied value.  For example, if the alpha is premultiplied,
     * this method divides it out before returning the value.  If the
     * alpha value is 0, the blue value will be 0.  If
     * <code>inData</code> is not a primitive array of type transferType,
     * a <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>inData</code> is not large enough to hold a pixel
     * value for this <code>ColorModel</code>.
     * If this <code>transferType</code> is not supported, a
     * <code>UnsupportedOperationException</code> will be
     * thrown.  Since
     * <code>ColorModel</code> is an abstract class, any instance
     * must be an instance of a subclass.  Subclasses inherit the
     * implementation of this method and if they don't override it, this
     * method throws an exception if the subclass uses a
     * <code>transferType</code> other than
     * <code>DataBuffer.TYPE_BYTE</code>,
     * <code>DataBuffer.TYPE_USHORT</code>, or
     * <code>DataBuffer.TYPE_INT</code>.
     * <p>
     * 返回指定像素的蓝色颜色分量,在默认RGB中从0到255缩放colorCode ColorStyle </code>,sRGB如果需要,进行颜色转换像素值由transferType类型的数据元素数组指定作
     * 为对象引用传递返回的值是非预乘的值例如,如果alpha被预乘,此方法在返回值之前将其分割出来如果alpha值为0,蓝色值将为0如果<code > inData </code>不是类型transferTy
     * pe的原始数组,则抛出<code> ClassCastException </code>如果<code> inData </code>不够大,则抛出<code> ArrayIndexOutOfBound
     * sException </code>以保存此<code> ColorModel </code>的像素值如果不支持此<code> transferType </code>,将抛出<code> Unsupp
     * ortedOperationException </code>由于<code> ColorModel </code>是抽象类,所以任何实例必须是子类的子类继承此方法的实现,如果它们不覆盖它,如果子类使用
     * 除<code> DataBufferTYPE_BYTE </code>,<code> DataBufferTYPE_USHORT </code>之外的<code> transferType </code>
     * 代码>或<code> DataBufferTYPE_INT </code>。
     * 
     * 
     * @param inData an array of pixel values
     * @return the value of the blue component of the specified pixel.
     * @throws ClassCastException if <code>inData</code>
     *  is not a primitive array of type <code>transferType</code>
     * @throws ArrayIndexOutOfBoundsException if
     *  <code>inData</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code>
     * @throws UnsupportedOperationException if this
     *  <code>tranferType</code> is not supported by this
     *  <code>ColorModel</code>
     */
    public int getBlue(Object inData) {
        int pixel=0,length=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
               length = bdata.length;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
               length = sdata.length;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
               length = idata.length;
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        if (length == 1) {
            return getBlue(pixel);
        }
        else {
            throw new UnsupportedOperationException
                ("This method is not supported by this color model");
        }
    }

    /**
     * Returns the alpha component for the specified pixel, scaled
     * from 0 to 255.  The pixel value is specified by an array of data
     * elements of type transferType passed in as an object reference.
     * If inData is not a primitive array of type transferType, a
     * <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * <code>inData</code> is not large enough to hold a pixel value for
     * this <code>ColorModel</code>.
     * If this <code>transferType</code> is not supported, a
     * <code>UnsupportedOperationException</code> will be
     * thrown.  Since
     * <code>ColorModel</code> is an abstract class, any instance
     * must be an instance of a subclass.  Subclasses inherit the
     * implementation of this method and if they don't override it, this
     * method throws an exception if the subclass uses a
     * <code>transferType</code> other than
     * <code>DataBuffer.TYPE_BYTE</code>,
     * <code>DataBuffer.TYPE_USHORT</code>, or
     * <code>DataBuffer.TYPE_INT</code>.
     * <p>
     * 返回指定像素的alpha分量,从0到255缩放像素值由作为对象引用传递的transferType类型的数据元素数组指定。
     * 如果inData不是transferType类型的原始数组,则<code> ClassCastException </code>被抛出如果<code> inData </code>不足够大以容纳此<code>
     *  ColorModel </code>的像素值,则抛出<code> ArrayIndexOutOfBoundsException </code> transferType </code>不被支持,将抛出
     * <code> UnsupportedOperationException </code>由于<code> ColorModel </code>是一个抽象类,所以任何实例必须是一个子类的实例子类继承此方法
     * 的实现,如果它们不覆盖它,如果子类使用<code> transferType </code>而不是<code> DataBufferTYPE_BYTE </code>,<code> DataBuffer
     * TYPE_USHORT < / code>或<code> DataBufferTYPE_INT </code>。
     * 返回指定像素的alpha分量,从0到255缩放像素值由作为对象引用传递的transferType类型的数据元素数组指定。
     * 
     * 
     * @param inData the specified pixel
     * @return the alpha component of the specified pixel, scaled from
     * 0 to 255.
     * @throws ClassCastException if <code>inData</code>
     *  is not a primitive array of type <code>transferType</code>
     * @throws ArrayIndexOutOfBoundsException if
     *  <code>inData</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code>
     * @throws UnsupportedOperationException if this
     *  <code>tranferType</code> is not supported by this
     *  <code>ColorModel</code>
     */
    public int getAlpha(Object inData) {
        int pixel=0,length=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
               length = bdata.length;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
               length = sdata.length;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
               length = idata.length;
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        if (length == 1) {
            return getAlpha(pixel);
        }
        else {
            throw new UnsupportedOperationException
                ("This method is not supported by this color model");
        }
    }

    /**
     * Returns the color/alpha components for the specified pixel in the
     * default RGB color model format.  A color conversion is done if
     * necessary.  The pixel value is specified by an array of data
     * elements of type transferType passed in as an object reference.
     * If inData is not a primitive array of type transferType, a
     * <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>inData</code> is not large enough to hold a pixel
     * value for this <code>ColorModel</code>.
     * The returned value will be in a non pre-multiplied format, i.e. if
     * the alpha is premultiplied, this method will divide it out of the
     * color components (if the alpha value is 0, the color values will be 0).
     * <p>
     * 返回默认RGB颜色模型格式中指定像素的颜色/ alpha分量如果需要,将进行颜色转换像素值由作为对象引用传递的transferType类型的数据元素数组指定如果inData不是基本类型arrayType
     * 类型transferType,抛出<code> ClassCastException </code>如果<code> inData </code>不足够大以容纳此<code>的像素值,则抛出<code>
     *  ArrayIndexOutOfBoundsException </code> ColorModel </code>返回值将采用非预乘法格式,即如果alpha被预乘,则此方法将它从颜色分量中分割出来(如
     * 果alpha值为0,颜色值为0)。
     * 
     * 
     * @param inData the specified pixel
     * @return the color and alpha components of the specified pixel.
     * @see ColorModel#getRGBdefault
     */
    public int getRGB(Object inData) {
        return (getAlpha(inData) << 24)
            | (getRed(inData) << 16)
            | (getGreen(inData) << 8)
            | (getBlue(inData) << 0);
    }

    /**
     * Returns a data element array representation of a pixel in this
     * <code>ColorModel</code>, given an integer pixel representation in
     * the default RGB color model.
     * This array can then be passed to the
     * {@link WritableRaster#setDataElements} method of
     * a {@link WritableRaster} object.  If the pixel variable is
     * <code>null</code>, a new array will be allocated.  If
     * <code>pixel</code> is not
     * <code>null</code>, it must be a primitive array of type
     * <code>transferType</code>; otherwise, a
     * <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * <code>pixel</code> is
     * not large enough to hold a pixel value for this
     * <code>ColorModel</code>. The pixel array is returned.
     * If this <code>transferType</code> is not supported, a
     * <code>UnsupportedOperationException</code> will be
     * thrown.  Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     * 返回这个<code> ColorModel </code>中的像素的数据元素数组表示,给定默认RGB颜色模型中的整数像素表示。
     * 然后可以将该数组传递给{@link WritableRaster#setDataElements} @link WritableRaster} object如果像素变量是<code> null </code>
     * ,则会分配一个新数组如果<code> pixel </code>不是<code> null </code> <code> transferType </code>类型的数组否则,抛出<code> Cla
     * ssCastException </code>如果<code> pixel </code>不足以容纳此<code> ColorModel </code>的像素值,则抛出<code> ArrayIndex
     * OutOfBoundsException </code> code>返回像素数组如果不支持<code> transferType </code>,将抛出<code> UnsupportedOperati
     * onException </code>由于<code> ColorModel </code>是一个抽象类,所以任何实例都是子类的实例覆盖此方法,因为此抽象类中的实现引发了一个<code> Unsuppo
     * rtedOperationException </code>。
     * 返回这个<code> ColorModel </code>中的像素的数据元素数组表示,给定默认RGB颜色模型中的整数像素表示。
     * 
     * 
     * @param rgb the integer pixel representation in the default RGB
     * color model
     * @param pixel the specified pixel
     * @return an array representation of the specified pixel in this
     *  <code>ColorModel</code>.
     * @throws ClassCastException if <code>pixel</code>
     *  is not a primitive array of type <code>transferType</code>
     * @throws ArrayIndexOutOfBoundsException if
     *  <code>pixel</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code>
     * @throws UnsupportedOperationException if this
     *  method is not supported by this <code>ColorModel</code>
     * @see WritableRaster#setDataElements
     * @see SampleModel#setDataElements
     */
    public Object getDataElements(int rgb, Object pixel) {
        throw new UnsupportedOperationException
            ("This method is not supported by this color model.");
    }

    /**
     * Returns an array of unnormalized color/alpha components given a pixel
     * in this <code>ColorModel</code>.  The pixel value is specified as
     * an <code>int</code>.  An <code>IllegalArgumentException</code>
     * will be thrown if pixel values for this <code>ColorModel</code> are
     * not conveniently representable as a single <code>int</code> or if
     * color component values for this <code>ColorModel</code> are not
     * conveniently representable in the unnormalized form.
     * For example, this method can be used to retrieve the
     * components for a specific pixel value in a
     * <code>DirectColorModel</code>.  If the components array is
     * <code>null</code>, a new array will be allocated.  The
     * components array will be returned.  Color/alpha components are
     * stored in the components array starting at <code>offset</code>
     * (even if the array is allocated by this method).  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if  the
     * components array is not <code>null</code> and is not large
     * enough to hold all the color and alpha components (starting at offset).
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     * 给定这个<code> ColorModel </code>中一个像素的非标准化颜色/ alpha分量数组返回一个像素值指定为<code> int </code>如果一个<code> IllegalArg
     * umentException </code>这个<code> ColorModel </code>的像素值不能方便地表示为单个<code> int </code>,或者如果该<code> ColorMo
     * del </code>的颜色分量值不能以非规范化形式例如,此方法可用于检索<code> DirectColorModel </code>中特定像素值的组件。
     * 如果components数组为<code> null </code>,则将分配新数组components数组将返回颜色/ alpha组件存储在从<code> offset </code>开始的组件数组中
     * (即使数组是由此方法分配的)如果组件数组不是<code>,则抛出<code> ArrayIndexOutOfBoundsException </code> > null </code>并且不够大,不足以
     * 容纳所有的颜色和alpha组件(从offset开始)由于<code> ColorModel </code>是一个抽象类,任何实例都是一个子类的实例子类必须覆盖方法,因为在这个抽象类中的实现引发了一个<code>
     *  UnsupportedOperationException </code>。
     * 
     * 
     * @param pixel the specified pixel
     * @param components the array to receive the color and alpha
     * components of the specified pixel
     * @param offset the offset into the <code>components</code> array at
     * which to start storing the color and alpha components
     * @return an array containing the color and alpha components of the
     * specified pixel starting at the specified offset.
     * @throws UnsupportedOperationException if this
     *          method is not supported by this <code>ColorModel</code>
     */
    public int[] getComponents(int pixel, int[] components, int offset) {
        throw new UnsupportedOperationException
            ("This method is not supported by this color model.");
    }

    /**
     * Returns an array of unnormalized color/alpha components given a pixel
     * in this <code>ColorModel</code>.  The pixel value is specified by
     * an array of data elements of type transferType passed in as an
     * object reference.  If <code>pixel</code> is not a primitive array
     * of type transferType, a <code>ClassCastException</code> is thrown.
     * An <code>IllegalArgumentException</code> will be thrown if color
     * component values for this <code>ColorModel</code> are not
     * conveniently representable in the unnormalized form.
     * An <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>pixel</code> is not large enough to hold a pixel
     * value for this <code>ColorModel</code>.
     * This method can be used to retrieve the components for a specific
     * pixel value in any <code>ColorModel</code>.  If the components
     * array is <code>null</code>, a new array will be allocated.  The
     * components array will be returned.  Color/alpha components are
     * stored in the <code>components</code> array starting at
     * <code>offset</code> (even if the array is allocated by this
     * method).  An <code>ArrayIndexOutOfBoundsException</code>
     * is thrown if  the components array is not <code>null</code> and is
     * not large enough to hold all the color and alpha components
     * (starting at <code>offset</code>).
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     * 给定此<code> ColorModel </code>中的像素的非标准化颜色/ alpha分量数组返回一个数组。
     * 像素值由作为对象引用传递的transferType类型的数据元素数组指定If <code> pixel </code >不是类型transferType的原始数组,则抛出<code> ClassCast
     * Exception </code>的原始数组如果<code> ColorModel </code>的颜色组件值不方便,则会抛出<code> IllegalArgumentException </code>
     * 可以以非规范化形式表示如果<code> pixel </code>不足够大以容纳此<code> ColorModel </code>的像素值,则抛出<code> ArrayIndexOutOfBound
     * sException </code>此方法可用于检索任何<code> ColorModel </code>中特定像素值的组件如果组件数组是<code> null </code>,将分配一个新数组将返回组
     * 件数组Color / alpha组件存储在<code>组件</code> / code>(即使数组是由这个方法分配的)如果组件数组不是<code> null </code>,并且不足够大以容纳所有的颜色
     * ,则抛出<code> ArrayIndexOutOfBoundsException </code> alpha组件(起始于<code> offset </code>)由于<code> ColorMode
     * l </code>是一个抽象类,任何实例都是子类的实例。
     * 给定此<code> ColorModel </code>中的像素的非标准化颜色/ alpha分量数组返回一个数组。
     * 子类必须重写此方法,因为此抽象类中的实现抛出<code> UnsupportedOperationException </code>。
     * 
     * 
     * @param pixel the specified pixel
     * @param components an array that receives the color and alpha
     * components of the specified pixel
     * @param offset the index into the <code>components</code> array at
     * which to begin storing the color and alpha components of the
     * specified pixel
     * @return an array containing the color and alpha components of the
     * specified pixel starting at the specified offset.
     * @throws UnsupportedOperationException if this
     *          method is not supported by this <code>ColorModel</code>
     */
    public int[] getComponents(Object pixel, int[] components, int offset) {
        throw new UnsupportedOperationException
            ("This method is not supported by this color model.");
    }

    /**
     * Returns an array of all of the color/alpha components in unnormalized
     * form, given a normalized component array.  Unnormalized components
     * are unsigned integral values between 0 and 2<sup>n</sup> - 1, where
     * n is the number of bits for a particular component.  Normalized
     * components are float values between a per component minimum and
     * maximum specified by the <code>ColorSpace</code> object for this
     * <code>ColorModel</code>.  An <code>IllegalArgumentException</code>
     * will be thrown if color component values for this
     * <code>ColorModel</code> are not conveniently representable in the
     * unnormalized form.  If the
     * <code>components</code> array is <code>null</code>, a new array
     * will be allocated.  The <code>components</code> array will
     * be returned.  Color/alpha components are stored in the
     * <code>components</code> array starting at <code>offset</code> (even
     * if the array is allocated by this method). An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if the
     * <code>components</code> array is not <code>null</code> and is not
     * large enough to hold all the color and alpha
     * components (starting at <code>offset</code>).  An
     * <code>IllegalArgumentException</code> is thrown if the
     * <code>normComponents</code> array is not large enough to hold
     * all the color and alpha components starting at
     * <code>normOffset</code>.
     * <p>
     * 给定归一化的组件数组,返回非归一化形式的所有颜色/ alpha组件的数组未归一化的组件是0和2之间的无符号整数值<sup> n </sup>  -  1,其中n是特定组件规范化组件是由<code> Co
     * lorModel </code>的<code> ColorSpace </code>对象指定的每个组件最小值和最大值之间的浮点值。
     * 如果颜色为true,将抛出<code> IllegalArgumentException </code>这个<code> ColorModel </code>的组件值在非规范化形式中不能方便地表示如果<code>
     *  components </code>数组是<code> null </code>,将分配一个新数组<code > components </code>数组颜色/ alpha组件存储在<code>组件</code>
     * 数组中,从<code> offset </code>开始(即使数组是由这个方法分配的),如果抛出一个<code> ArrayIndexOutOfBoundsException </code> <code>
     * 组件</code>数组不是<code> null </code>,并且不够大以容纳所有的颜色和alpha组件(从<code> offset </code>开始)<code> IllegalArgumen
     * tException如果<code> normComponents </code>数组不够大,不能容纳所有的颜色和alpha分量开始<code> normOffset </code>。
     * 
     * 
     * @param normComponents an array containing normalized components
     * @param normOffset the offset into the <code>normComponents</code>
     * array at which to start retrieving normalized components
     * @param components an array that receives the components from
     * <code>normComponents</code>
     * @param offset the index into <code>components</code> at which to
     * begin storing normalized components from
     * <code>normComponents</code>
     * @return an array containing unnormalized color and alpha
     * components.
     * @throws IllegalArgumentException If the component values for this
     * <CODE>ColorModel</CODE> are not conveniently representable in the
     * unnormalized form.
     * @throws IllegalArgumentException if the length of
     *          <code>normComponents</code> minus <code>normOffset</code>
     *          is less than <code>numComponents</code>
     * @throws UnsupportedOperationException if the
     *          constructor of this <code>ColorModel</code> called the
     *          <code>super(bits)</code> constructor, but did not
     *          override this method.  See the constructor,
     *          {@link #ColorModel(int)}.
     */
    public int[] getUnnormalizedComponents(float[] normComponents,
                                           int normOffset,
                                           int[] components, int offset) {
        // Make sure that someone isn't using a custom color model
        // that called the super(bits) constructor.
        if (colorSpace == null) {
            throw new UnsupportedOperationException("This method is not supported "+
                                        "by this color model.");
        }

        if (nBits == null) {
            throw new UnsupportedOperationException ("This method is not supported.  "+
                                         "Unable to determine #bits per "+
                                         "component.");
        }
        if ((normComponents.length - normOffset) < numComponents) {
            throw new
                IllegalArgumentException(
                        "Incorrect number of components.  Expecting "+
                        numComponents);
        }

        if (components == null) {
            components = new int[offset+numComponents];
        }

        if (supportsAlpha && isAlphaPremultiplied) {
            float normAlpha = normComponents[normOffset+numColorComponents];
            for (int i=0; i < numColorComponents; i++) {
                components[offset+i] = (int) (normComponents[normOffset+i]
                                              * ((1<<nBits[i]) - 1)
                                              * normAlpha + 0.5f);
            }
            components[offset+numColorComponents] = (int)
                (normAlpha * ((1<<nBits[numColorComponents]) - 1) + 0.5f);
        }
        else {
            for (int i=0; i < numComponents; i++) {
                components[offset+i] = (int) (normComponents[normOffset+i]
                                              * ((1<<nBits[i]) - 1) + 0.5f);
            }
        }

        return components;
    }

    /**
     * Returns an array of all of the color/alpha components in normalized
     * form, given an unnormalized component array.  Unnormalized components
     * are unsigned integral values between 0 and 2<sup>n</sup> - 1, where
     * n is the number of bits for a particular component.  Normalized
     * components are float values between a per component minimum and
     * maximum specified by the <code>ColorSpace</code> object for this
     * <code>ColorModel</code>.  An <code>IllegalArgumentException</code>
     * will be thrown if color component values for this
     * <code>ColorModel</code> are not conveniently representable in the
     * unnormalized form.  If the
     * <code>normComponents</code> array is <code>null</code>, a new array
     * will be allocated.  The <code>normComponents</code> array
     * will be returned.  Color/alpha components are stored in the
     * <code>normComponents</code> array starting at
     * <code>normOffset</code> (even if the array is allocated by this
     * method).  An <code>ArrayIndexOutOfBoundsException</code> is thrown
     * if the <code>normComponents</code> array is not <code>null</code>
     * and is not large enough to hold all the color and alpha components
     * (starting at <code>normOffset</code>).  An
     * <code>IllegalArgumentException</code> is thrown if the
     * <code>components</code> array is not large enough to hold all the
     * color and alpha components starting at <code>offset</code>.
     * <p>
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  The default implementation
     * of this method in this abstract class assumes that component values
     * for this class are conveniently representable in the unnormalized
     * form.  Therefore, subclasses which may
     * have instances which do not support the unnormalized form must
     * override this method.
     * <p>
     * 给定一个非规格化的组件数组,以规范化形式返回所有颜色/ alpha组件的数组未规范化的组件是0和2之间的无符号整数值<sup> n </sup>  -  1,其中n是特定组件规范化组件是由<code> 
     * ColorModel </code>的<code> ColorSpace </code>对象指定的每个组件最小值和最大值之间的浮点值。
     * 如果颜色为true,将抛出<code> IllegalArgumentException </code>这个<code> ColorModel </code>的组件值在非规范化形式中不能方便地表示如果<code>
     *  normComponents </code>数组是<code> null </code>,将分配一个新数组<code > normComponents </code>数组颜色/ alpha分量存储在<code>
     *  normComponent </code>数组中,从<code> normOffset </code>开始(即使数组是由这个方法分配的),如果一个<code> ArrayIndexOutOfBound
     * sException </code> <code> normComponents </code>数组不是<code> null </code>,并且不够大以容纳所有的颜色和alpha组件(从<code>
     *  normOffset </code>开始)<code> IllegalArgumentException如果<code> components </code>数组不够大,不能容纳所有的颜色和alpha
     * 组件开始<code> offset </code>时抛出</code>。
     * <p>
     * 由于<code> ColorModel </code>是一个抽象类,任何实例都是一个子类的实例。此抽象类中的此方法的默认实现假定该类的组件值可以非标准化形式方便地表示。
     * 因此,可能有不支持非规范化形式的实例必须覆盖此方法。
     * 
     * 
     * @param components an array containing unnormalized components
     * @param offset the offset into the <code>components</code> array at
     * which to start retrieving unnormalized components
     * @param normComponents an array that receives the normalized components
     * @param normOffset the index into <code>normComponents</code> at
     * which to begin storing normalized components
     * @return an array containing normalized color and alpha
     * components.
     * @throws IllegalArgumentException If the component values for this
     * <CODE>ColorModel</CODE> are not conveniently representable in the
     * unnormalized form.
     * @throws UnsupportedOperationException if the
     *          constructor of this <code>ColorModel</code> called the
     *          <code>super(bits)</code> constructor, but did not
     *          override this method.  See the constructor,
     *          {@link #ColorModel(int)}.
     * @throws UnsupportedOperationException if this method is unable
     *          to determine the number of bits per component
     */
    public float[] getNormalizedComponents(int[] components, int offset,
                                           float[] normComponents,
                                           int normOffset) {
        // Make sure that someone isn't using a custom color model
        // that called the super(bits) constructor.
        if (colorSpace == null) {
            throw new UnsupportedOperationException("This method is not supported by "+
                                        "this color model.");
        }
        if (nBits == null) {
            throw new UnsupportedOperationException ("This method is not supported.  "+
                                         "Unable to determine #bits per "+
                                         "component.");
        }

        if ((components.length - offset) < numComponents) {
            throw new
                IllegalArgumentException(
                        "Incorrect number of components.  Expecting "+
                        numComponents);
        }

        if (normComponents == null) {
            normComponents = new float[numComponents+normOffset];
        }

        if (supportsAlpha && isAlphaPremultiplied) {
            // Normalized coordinates are non premultiplied
            float normAlpha = (float)components[offset+numColorComponents];
            normAlpha /= (float) ((1<<nBits[numColorComponents]) - 1);
            if (normAlpha != 0.0f) {
                for (int i=0; i < numColorComponents; i++) {
                    normComponents[normOffset+i] =
                        ((float) components[offset+i]) /
                        (normAlpha * ((float) ((1<<nBits[i]) - 1)));
                }
            } else {
                for (int i=0; i < numColorComponents; i++) {
                    normComponents[normOffset+i] = 0.0f;
                }
            }
            normComponents[normOffset+numColorComponents] = normAlpha;
        }
        else {
            for (int i=0; i < numComponents; i++) {
                normComponents[normOffset+i] = ((float) components[offset+i]) /
                                               ((float) ((1<<nBits[i]) - 1));
            }
        }

        return normComponents;
    }

    /**
     * Returns a pixel value represented as an <code>int</code> in this
     * <code>ColorModel</code>, given an array of unnormalized color/alpha
     * components.  This method will throw an
     * <code>IllegalArgumentException</code> if component values for this
     * <code>ColorModel</code> are not conveniently representable as a
     * single <code>int</code> or if color component values for this
     * <code>ColorModel</code> are not conveniently representable in the
     * unnormalized form.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if  the
     * <code>components</code> array is not large enough to hold all the
     * color and alpha components (starting at <code>offset</code>).
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     * 给定一个非规范化的颜色/ alpha分量数组,返回在这个<code> ColorModel </code>中表示为<code> int </code>的像素值此方法将抛出一个<code> Illegal
     * ArgumentException </code> if component这个<code> ColorModel </code>的值不能方便地表示为单个<code> int </code>,或者如果这
     * 个<code> ColorModel </code>的颜色成分值不是非规范化形式<code> ArrayIndexOutOfBoundsException </code>被抛出,如果<code>组件</code>
     * 数组不够大,足以容纳所有的颜色和alpha组件(从<code> offset </code>开始)因为<code> ColorModel </code>是一个抽象类,任何实例都是一个子类的实例子类必须重
     * 写这个方法,因为在这个抽象类中的实现会抛出一个<code> UnsupportedOperationException </code>。
     * 
     * 
     * @param components an array of unnormalized color and alpha
     * components
     * @param offset the index into <code>components</code> at which to
     * begin retrieving the color and alpha components
     * @return an <code>int</code> pixel value in this
     * <code>ColorModel</code> corresponding to the specified components.
     * @throws IllegalArgumentException if
     *  pixel values for this <code>ColorModel</code> are not
     *  conveniently representable as a single <code>int</code>
     * @throws IllegalArgumentException if
     *  component values for this <code>ColorModel</code> are not
     *  conveniently representable in the unnormalized form
     * @throws ArrayIndexOutOfBoundsException if
     *  the <code>components</code> array is not large enough to
     *  hold all of the color and alpha components starting at
     *  <code>offset</code>
     * @throws UnsupportedOperationException if this
     *  method is not supported by this <code>ColorModel</code>
     */
    public int getDataElement(int[] components, int offset) {
        throw new UnsupportedOperationException("This method is not supported "+
                                    "by this color model.");
    }

    /**
     * Returns a data element array representation of a pixel in this
     * <code>ColorModel</code>, given an array of unnormalized color/alpha
     * components.  This array can then be passed to the
     * <code>setDataElements</code> method of a <code>WritableRaster</code>
     * object.  This method will throw an <code>IllegalArgumentException</code>
     * if color component values for this <code>ColorModel</code> are not
     * conveniently representable in the unnormalized form.
     * An <code>ArrayIndexOutOfBoundsException</code> is thrown
     * if the <code>components</code> array is not large enough to hold
     * all the color and alpha components (starting at
     * <code>offset</code>).  If the <code>obj</code> variable is
     * <code>null</code>, a new array will be allocated.  If
     * <code>obj</code> is not <code>null</code>, it must be a primitive
     * array of type transferType; otherwise, a
     * <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * <code>obj</code> is not large enough to hold a pixel value for this
     * <code>ColorModel</code>.
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     * 给定一个非规格化的颜色/ alpha分量的数组,返回这个<code> ColorModel </code>中像素的数据元素数组表示。
     * 然后可以将该数组传递给<code> setDataElements </code> > WritableRaster </code> object如果这个<code> ColorModel </code>
     * 的颜色组件值在非规范化形式中不方便表示,那么此方法将抛出一个<code> IllegalArgumentException </code> <code> ArrayIndexOutOfBoundsExc
     * eption </code >如果<code>组件</code>数组不足以容纳所有的颜色和alpha组件(从<code> offset </code>开始),则抛出此异常。
     * 给定一个非规格化的颜色/ alpha分量的数组,返回这个<code> ColorModel </code>中像素的数据元素数组表示。
     * 如果<code> obj </code> <code> null </code>,将分配一个新数组如果<code> obj </code>不是<code> null </code>,那么它必须是tran
     * sferType类型的原始数组;否则,抛出<code> ClassCastException </code>如果<code> obj </code>不足以容纳此<code> ColorModel </code>
     * 的像素值,则抛出<code> ArrayIndexOutOfBoundsException </code>代码>因为<code> ColorModel </code>是一个抽象类,任何实例都是一个子类的
     * 实例Subclasses必须重写这个方法,因为在这个抽象类中的实现抛出一个<code> UnsupportedOperationException </code>。
     * 给定一个非规格化的颜色/ alpha分量的数组,返回这个<code> ColorModel </code>中像素的数据元素数组表示。
     * 
     * 
     * @param components an array of unnormalized color and alpha
     * components
     * @param offset the index into <code>components</code> at which to
     * begin retrieving color and alpha components
     * @param obj the <code>Object</code> representing an array of color
     * and alpha components
     * @return an <code>Object</code> representing an array of color and
     * alpha components.
     * @throws ClassCastException if <code>obj</code>
     *  is not a primitive array of type <code>transferType</code>
     * @throws ArrayIndexOutOfBoundsException if
     *  <code>obj</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code> or the <code>components</code>
     *  array is not large enough to hold all of the color and alpha
     *  components starting at <code>offset</code>
     * @throws IllegalArgumentException if
     *  component values for this <code>ColorModel</code> are not
     *  conveniently representable in the unnormalized form
     * @throws UnsupportedOperationException if this
     *  method is not supported by this <code>ColorModel</code>
     * @see WritableRaster#setDataElements
     * @see SampleModel#setDataElements
     */
    public Object getDataElements(int[] components, int offset, Object obj) {
        throw new UnsupportedOperationException("This method has not been implemented "+
                                    "for this color model.");
    }

    /**
     * Returns a pixel value represented as an <code>int</code> in this
     * <code>ColorModel</code>, given an array of normalized color/alpha
     * components.  This method will throw an
     * <code>IllegalArgumentException</code> if pixel values for this
     * <code>ColorModel</code> are not conveniently representable as a
     * single <code>int</code>.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if  the
     * <code>normComponents</code> array is not large enough to hold all the
     * color and alpha components (starting at <code>normOffset</code>).
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  The default implementation
     * of this method in this abstract class first converts from the
     * normalized form to the unnormalized form and then calls
     * <code>getDataElement(int[], int)</code>.  Subclasses which may
     * have instances which do not support the unnormalized form must
     * override this method.
     * <p>
     * 给定一个标准化的颜色/ alpha分量数组,返回一个表示为<code> int </code>的像素值。
     * <code> IllegalArgumentException </code>这个<code> ColorModel </code>的值不能方便地表示为单个<code> int </code>如果<code>
     *  normComponents </code>数组不会被抛出,则会抛出<code> ArrayIndexOutOfBoundsException </code>足够大以容纳所有的颜色和alpha组件(从
     * <code> normOffset </code>开始)由于<code> ColorModel </code>是一个抽象类,任何实例都是子类的实例此方法在此抽象类中的默认实现首先从规范化形式转换为非规范
     * 化形式,然后调用<code> getDataElement(int [],int)</code>子类可能具有不支持非规范化形式必须覆盖此方法。
     * 给定一个标准化的颜色/ alpha分量数组,返回一个表示为<code> int </code>的像素值。
     * 
     * 
     * @param normComponents an array of normalized color and alpha
     * components
     * @param normOffset the index into <code>normComponents</code> at which to
     * begin retrieving the color and alpha components
     * @return an <code>int</code> pixel value in this
     * <code>ColorModel</code> corresponding to the specified components.
     * @throws IllegalArgumentException if
     *  pixel values for this <code>ColorModel</code> are not
     *  conveniently representable as a single <code>int</code>
     * @throws ArrayIndexOutOfBoundsException if
     *  the <code>normComponents</code> array is not large enough to
     *  hold all of the color and alpha components starting at
     *  <code>normOffset</code>
     * @since 1.4
     */
    public int getDataElement(float[] normComponents, int normOffset) {
        int components[] = getUnnormalizedComponents(normComponents,
                                                     normOffset, null, 0);
        return getDataElement(components, 0);
    }

    /**
     * Returns a data element array representation of a pixel in this
     * <code>ColorModel</code>, given an array of normalized color/alpha
     * components.  This array can then be passed to the
     * <code>setDataElements</code> method of a <code>WritableRaster</code>
     * object.  An <code>ArrayIndexOutOfBoundsException</code> is thrown
     * if the <code>normComponents</code> array is not large enough to hold
     * all the color and alpha components (starting at
     * <code>normOffset</code>).  If the <code>obj</code> variable is
     * <code>null</code>, a new array will be allocated.  If
     * <code>obj</code> is not <code>null</code>, it must be a primitive
     * array of type transferType; otherwise, a
     * <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * <code>obj</code> is not large enough to hold a pixel value for this
     * <code>ColorModel</code>.
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  The default implementation
     * of this method in this abstract class first converts from the
     * normalized form to the unnormalized form and then calls
     * <code>getDataElement(int[], int, Object)</code>.  Subclasses which may
     * have instances which do not support the unnormalized form must
     * override this method.
     * <p>
     * 给定一个标准化的颜色/ alpha分量的数组,返回这个<code> ColorModel </code>中像素的数据元素数组表示。
     * 然后可以将该数组传递给<code> setDataElements </code> > WritableRaster </code>对象如果<code> normComponents </code>数组
     * 不足以容纳所有的颜色和alpha组件(从<code> normOffset </code>开始),则会抛出<code> ArrayIndexOutOfBoundsException </code>如果<code>
     *  obj </code>变量<code> null </code>,则将分配一个新的数组如果<code> obj </code>它必须是transferType类型的原始数组;否则,抛出<code> C
     * lassCastException </code>如果<code> obj </code>不足以容纳此<color> </code>的<code> ColorModel </code>,则会抛出<code>
     *  ArrayIndexOutOfBoundsException </code>因为<code> ColorModel </code>抽象类,任何实例都是子类的实例。
     * 给定一个标准化的颜色/ alpha分量的数组,返回这个<code> ColorModel </code>中像素的数据元素数组表示。
     * 此抽象类中此方法的默认实现首先从规范化形式转换为非规范化形式,然后调用<code> getDataElement(int [],int,Object)</code >可能具有不支持非规范化形式的实例的子
     * 类必须覆盖此方法。
     * 给定一个标准化的颜色/ alpha分量的数组,返回这个<code> ColorModel </code>中像素的数据元素数组表示。
     * 
     * 
     * @param normComponents an array of normalized color and alpha
     * components
     * @param normOffset the index into <code>normComponents</code> at which to
     * begin retrieving color and alpha components
     * @param obj a primitive data array to hold the returned pixel
     * @return an <code>Object</code> which is a primitive data array
     * representation of a pixel
     * @throws ClassCastException if <code>obj</code>
     *  is not a primitive array of type <code>transferType</code>
     * @throws ArrayIndexOutOfBoundsException if
     *  <code>obj</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code> or the <code>normComponents</code>
     *  array is not large enough to hold all of the color and alpha
     *  components starting at <code>normOffset</code>
     * @see WritableRaster#setDataElements
     * @see SampleModel#setDataElements
     * @since 1.4
     */
    public Object getDataElements(float[] normComponents, int normOffset,
                                  Object obj) {
        int components[] = getUnnormalizedComponents(normComponents,
                                                     normOffset, null, 0);
        return getDataElements(components, 0, obj);
    }

    /**
     * Returns an array of all of the color/alpha components in normalized
     * form, given a pixel in this <code>ColorModel</code>.  The pixel
     * value is specified by an array of data elements of type transferType
     * passed in as an object reference.  If pixel is not a primitive array
     * of type transferType, a <code>ClassCastException</code> is thrown.
     * An <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * <code>pixel</code> is not large enough to hold a pixel value for this
     * <code>ColorModel</code>.
     * Normalized components are float values between a per component minimum
     * and maximum specified by the <code>ColorSpace</code> object for this
     * <code>ColorModel</code>.  If the
     * <code>normComponents</code> array is <code>null</code>, a new array
     * will be allocated.  The <code>normComponents</code> array
     * will be returned.  Color/alpha components are stored in the
     * <code>normComponents</code> array starting at
     * <code>normOffset</code> (even if the array is allocated by this
     * method).  An <code>ArrayIndexOutOfBoundsException</code> is thrown
     * if the <code>normComponents</code> array is not <code>null</code>
     * and is not large enough to hold all the color and alpha components
     * (starting at <code>normOffset</code>).
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  The default implementation
     * of this method in this abstract class first retrieves color and alpha
     * components in the unnormalized form using
     * <code>getComponents(Object, int[], int)</code> and then calls
     * <code>getNormalizedComponents(int[], int, float[], int)</code>.
     * Subclasses which may
     * have instances which do not support the unnormalized form must
     * override this method.
     * <p>
     * 给定这个<code> ColorModel </code>中的像素,以标准化形式返回所有颜色/ alpha组件的数组。
     * 像素值由作为对象引用传递的transferType类型的数据元素数组指定If pixel不是类型transferType的基本数组,则抛出<code> ClassCastException </code>
     * 如果<code> pixel </code>不足以容纳一个像素值,则抛出<code> ArrayIndexOutOfBoundsException </code>这个<code> ColorModel 
     * </code>标准化的组件是这个<code> ColorModel </code>的<code> ColorSpace </code>对象指定的每个组件最小和最大值之间的浮点值如果<code> norm
     * Components < / code> array是<code> null </code>,将分配一个新的数组将返回<code> normComponents </code>数组Color / alp
     * ha组件存储在<code> normComponent </code>数组中,从<code> normOffset </code>开始(即使数组是由此方法分配的) )如果<code> normCompo
     * nents </code>数组不是<code> null </code>,并且不足以容纳所有的颜色和alpha分量(从<code> null </code>开始),则抛出<code> ArrayInde
     * xOutOfBoundsException </code> code> normOffset </code>)由于<code> ColorModel </code>是一个抽象类,任何实例都是一个子类的实
     * 例该方法在此抽象类中的默认实现首先使用<code> getComponents(Object,int [],int)</code>检索非规范化形式的颜色和alpha组件,然后调用<code> getNo
     * rmalizedComponents(int [ int,float [],int)</code>可能有不支持非规范化形式的实例的子类必须重写这个方法。
     * 给定这个<code> ColorModel </code>中的像素,以标准化形式返回所有颜色/ alpha组件的数组。
     * 
     * 
     * @param pixel the specified pixel
     * @param normComponents an array to receive the normalized components
     * @param normOffset the offset into the <code>normComponents</code>
     * array at which to start storing normalized components
     * @return an array containing normalized color and alpha
     * components.
     * @throws ClassCastException if <code>pixel</code> is not a primitive
     *          array of type transferType
     * @throws ArrayIndexOutOfBoundsException if
     *          <code>normComponents</code> is not large enough to hold all
     *          color and alpha components starting at <code>normOffset</code>
     * @throws ArrayIndexOutOfBoundsException if
     *          <code>pixel</code> is not large enough to hold a pixel
     *          value for this <code>ColorModel</code>.
     * @throws UnsupportedOperationException if the
     *          constructor of this <code>ColorModel</code> called the
     *          <code>super(bits)</code> constructor, but did not
     *          override this method.  See the constructor,
     *          {@link #ColorModel(int)}.
     * @throws UnsupportedOperationException if this method is unable
     *          to determine the number of bits per component
     * @since 1.4
     */
    public float[] getNormalizedComponents(Object pixel,
                                           float[] normComponents,
                                           int normOffset) {
        int components[] = getComponents(pixel, null, 0);
        return getNormalizedComponents(components, 0,
                                       normComponents, normOffset);
    }

    /**
     * Tests if the specified <code>Object</code> is an instance of
     * <code>ColorModel</code> and if it equals this
     * <code>ColorModel</code>.
     * <p>
     * 测试指定的<code> Object </code>是否是<code> ColorModel </code>的实例,如果它等于此<code> ColorModel </code>
     * 
     * 
     * @param obj the <code>Object</code> to test for equality
     * @return <code>true</code> if the specified <code>Object</code>
     * is an instance of <code>ColorModel</code> and equals this
     * <code>ColorModel</code>; <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof ColorModel)) {
            return false;
        }
        ColorModel cm = (ColorModel) obj;

        if (this == cm) {
            return true;
        }
        if (supportsAlpha != cm.hasAlpha() ||
            isAlphaPremultiplied != cm.isAlphaPremultiplied() ||
            pixel_bits != cm.getPixelSize() ||
            transparency != cm.getTransparency() ||
            numComponents != cm.getNumComponents())
        {
            return false;
        }

        int[] nb = cm.getComponentSize();

        if ((nBits != null) && (nb != null)) {
            for (int i = 0; i < numComponents; i++) {
                if (nBits[i] != nb[i]) {
                    return false;
                }
            }
        } else {
            return ((nBits == null) && (nb == null));
        }

        return true;
    }

    /**
     * Returns the hash code for this ColorModel.
     *
     * <p>
     *  返回此ColorModel的哈希码
     * 
     * 
     * @return    a hash code for this ColorModel.
     */
    public int hashCode() {

        int result = 0;

        result = (supportsAlpha ? 2 : 3) +
                 (isAlphaPremultiplied ? 4 : 5) +
                 pixel_bits * 6 +
                 transparency * 7 +
                 numComponents * 8;

        if (nBits != null) {
            for (int i = 0; i < numComponents; i++) {
                result = result + nBits[i] * (i + 9);
            }
        }

        return result;
    }

    /**
     * Returns the <code>ColorSpace</code> associated with this
     * <code>ColorModel</code>.
     * <p>
     *  返回与此<code> ColorModel </code>关联的<code> ColorSpace </code>
     * 
     * 
     * @return the <code>ColorSpace</code> of this
     * <code>ColorModel</code>.
     */
    final public ColorSpace getColorSpace() {
        return colorSpace;
    }

    /**
     * Forces the raster data to match the state specified in the
     * <code>isAlphaPremultiplied</code> variable, assuming the data is
     * currently correctly described by this <code>ColorModel</code>.  It
     * may multiply or divide the color raster data by alpha, or do
     * nothing if the data is in the correct state.  If the data needs to
     * be coerced, this method will also return an instance of this
     * <code>ColorModel</code> with the <code>isAlphaPremultiplied</code>
     * flag set appropriately.  This method will throw a
     * <code>UnsupportedOperationException</code> if it is not supported
     * by this <code>ColorModel</code>.
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     * 假设数据当前由该<code> ColorModel </code>正确描述,强制栅格数据与<code> isAlphaPremultiplied </code>变量中指定的状态匹配。
     * 它可以将颜色栅格数据乘以alpha,或者如果数据处于正确状态则不执行任何操作如果数据需要被强制,则此方法也将返回此<code> ColorModel </code>的实例,其中<code> isAlph
     * aPremultiplied </code>方法将抛出一个<code> UnsupportedOperationException </code>,如果它不支持这个<code> ColorModel </code>
     * 由于<code> ColorModel </code>是一个抽象类,任何实例是一个子类的实例子类必须重写这个方法,因为在这个抽象类中的实现会抛出一个<code> UnsupportedOperation
     * Exception </code>。
     * 假设数据当前由该<code> ColorModel </code>正确描述,强制栅格数据与<code> isAlphaPremultiplied </code>变量中指定的状态匹配。
     * 
     * 
     * @param raster the <code>WritableRaster</code> data
     * @param isAlphaPremultiplied <code>true</code> if the alpha is
     * premultiplied; <code>false</code> otherwise
     * @return a <code>ColorModel</code> object that represents the
     * coerced data.
     */
    public ColorModel coerceData (WritableRaster raster,
                                  boolean isAlphaPremultiplied) {
        throw new UnsupportedOperationException
            ("This method is not supported by this color model");
    }

    /**
      * Returns <code>true</code> if <code>raster</code> is compatible
      * with this <code>ColorModel</code> and <code>false</code> if it is
      * not.
      * Since <code>ColorModel</code> is an abstract class,
      * any instance is an instance of a subclass.  Subclasses must
      * override this method since the implementation in this abstract
      * class throws an <code>UnsupportedOperationException</code>.
      * <p>
      * 如果<code> raster </code>与<code> ColorModel </code>和<code> false </code>兼容,则返回<code> true </code>代码>是一个
      * 抽象类,任何实例是一个子类的实例Subclasses必须重写这个方法,因为在这个抽象类中的实现会抛出一个<code> UnsupportedOperationException </code>。
      * 
      * 
      * @param raster the {@link Raster} object to test for compatibility
      * @return <code>true</code> if <code>raster</code> is compatible
      * with this <code>ColorModel</code>.
      * @throws UnsupportedOperationException if this
      *         method has not been implemented for this
      *         <code>ColorModel</code>
      */
    public boolean isCompatibleRaster(Raster raster) {
        throw new UnsupportedOperationException(
            "This method has not been implemented for this ColorModel.");
    }

    /**
     * Creates a <code>WritableRaster</code> with the specified width and
     * height that has a data layout (<code>SampleModel</code>) compatible
     * with this <code>ColorModel</code>.
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     *  创建一个具有指定宽度和高度的<code> WritableRaster </code>,它具有与此<code> ColorModel </code>兼容的数据布局(<code> SampleModel
     *  </code>)由于<code> ColorModel </code >是一个抽象类,任何实例是一个子类的实例Subclasses必须重写这个方法,因为在这个抽象类中的实现引发一个<code> Uns
     * upportedOperationException </code>。
     * 
     * 
     * @param w the width to apply to the new <code>WritableRaster</code>
     * @param h the height to apply to the new <code>WritableRaster</code>
     * @return a <code>WritableRaster</code> object with the specified
     * width and height.
     * @throws UnsupportedOperationException if this
     *          method is not supported by this <code>ColorModel</code>
     * @see WritableRaster
     * @see SampleModel
     */
    public WritableRaster createCompatibleWritableRaster(int w, int h) {
        throw new UnsupportedOperationException
            ("This method is not supported by this color model");
    }

    /**
     * Creates a <code>SampleModel</code> with the specified width and
     * height that has a data layout compatible with this
     * <code>ColorModel</code>.
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     * 创建一个具有指定宽度和高度的<code> SampleModel </code>,其数据布局与此<code> ColorModel </code>兼容由于<code> ColorModel </code>
     * 是一个抽象类,所以任何实例都是子类的实例子类必须重写此方法,因为此抽象类中的实现会抛出<code> UnsupportedOperationException </code>。
     * 
     * 
     * @param w the width to apply to the new <code>SampleModel</code>
     * @param h the height to apply to the new <code>SampleModel</code>
     * @return a <code>SampleModel</code> object with the specified
     * width and height.
     * @throws UnsupportedOperationException if this
     *          method is not supported by this <code>ColorModel</code>
     * @see SampleModel
     */
    public SampleModel createCompatibleSampleModel(int w, int h) {
        throw new UnsupportedOperationException
            ("This method is not supported by this color model");
    }

    /** Checks if the <code>SampleModel</code> is compatible with this
     * <code>ColorModel</code>.
     * Since <code>ColorModel</code> is an abstract class,
     * any instance is an instance of a subclass.  Subclasses must
     * override this method since the implementation in this abstract
     * class throws an <code>UnsupportedOperationException</code>.
     * <p>
     *  <code> ColorModel </code>由于<code> ColorModel </code>是一个抽象类,任何实例都是一个子类的实例Subclasses必须重写这个方法,因为在这个抽象类中
     * 的实现引发一个<code> UnsupportedOperationException <代码>。
     * 
     * 
     * @param sm the specified <code>SampleModel</code>
     * @return <code>true</code> if the specified <code>SampleModel</code>
     * is compatible with this <code>ColorModel</code>; <code>false</code>
     * otherwise.
     * @throws UnsupportedOperationException if this
     *          method is not supported by this <code>ColorModel</code>
     * @see SampleModel
     */
    public boolean isCompatibleSampleModel(SampleModel sm) {
        throw new UnsupportedOperationException
            ("This method is not supported by this color model");
    }

    /**
     * Disposes of system resources associated with this
     * <code>ColorModel</code> once this <code>ColorModel</code> is no
     * longer referenced.
     * <p>
     *  一旦这个<code> ColorModel </code>不再被引用,与<code> ColorModel </code>相关联的系统资源的处置
     * 
     */
    public void finalize() {
    }


    /**
     * Returns a <code>Raster</code> representing the alpha channel of an
     * image, extracted from the input <code>Raster</code>, provided that
     * pixel values of this <code>ColorModel</code> represent color and
     * alpha information as separate spatial bands (e.g.
     * {@link ComponentColorModel} and <code>DirectColorModel</code>).
     * This method assumes that <code>Raster</code> objects associated
     * with such a <code>ColorModel</code> store the alpha band, if
     * present, as the last band of image data.  Returns <code>null</code>
     * if there is no separate spatial alpha channel associated with this
     * <code>ColorModel</code>.  If this is an
     * <code>IndexColorModel</code> which has alpha in the lookup table,
     * this method will return <code>null</code> since
     * there is no spatially discrete alpha channel.
     * This method will create a new <code>Raster</code> (but will share
     * the data array).
     * Since <code>ColorModel</code> is an abstract class, any instance
     * is an instance of a subclass.  Subclasses must override this
     * method to get any behavior other than returning <code>null</code>
     * because the implementation in this abstract class returns
     * <code>null</code>.
     * <p>
     * 返回表示从输入<code> Raster </code>中提取的图像的Alpha通道的<code> Raster </code>,前提是此<code> ColorModel </code>的像素值表示颜
     * 色和Alpha信息作为分离的空间带(例如{@link ComponentColorModel}和<code> DirectColorModel </code>)。
     * 该方法假定与这样的<code> ColorModel </code>相关联的<code> Raster </code>对象存储α带,如果存在,则作为图像数据的最后一个带返回<code> null </code>
     * 如果没有与该<code> ColorModel </code>相关联的单独的空间阿尔法通道如果这是<code> IndexColorModel </code >其在查找表中具有α,该方法将返回<code>
     *  null </code>,因为没有空间离散的α通道这个方法将创建一个新的<code> Raster </code>(但将共享数据数组)由于<code> ColorModel </code>是一个抽象类
     * ,任何实例都是子类的实例子类必须覆盖此方法获取除了返回<code> null </code>之外的任何行为,因为此抽象类中的实现返回<code> null </code>。
     * 
     * 
     * @param raster the specified <code>Raster</code>
     * @return a <code>Raster</code> representing the alpha channel of
     * an image, obtained from the specified <code>Raster</code>.
     */
    public WritableRaster getAlphaRaster(WritableRaster raster) {
        return null;
    }

    /**
     * Returns the <code>String</code> representation of the contents of
     * this <code>ColorModel</code>object.
     * <p>
     * 返回此<code> ColorModel </code>对象的内容的<code> String </code>表示形式
     * 
     * 
     * @return a <code>String</code> representing the contents of this
     * <code>ColorModel</code> object.
     */
    public String toString() {
       return new String("ColorModel: #pixelBits = "+pixel_bits
                         + " numComponents = "+numComponents
                         + " color space = "+colorSpace
                         + " transparency = "+transparency
                         + " has alpha = "+supportsAlpha
                         + " isAlphaPre = "+isAlphaPremultiplied
                         );
    }

    static int getDefaultTransferType(int pixel_bits) {
        if (pixel_bits <= 8) {
            return DataBuffer.TYPE_BYTE;
        } else if (pixel_bits <= 16) {
            return DataBuffer.TYPE_USHORT;
        } else if (pixel_bits <= 32) {
            return DataBuffer.TYPE_INT;
        } else {
            return DataBuffer.TYPE_UNDEFINED;
        }
    }

    static byte[] l8Tos8 = null;   // 8-bit linear to 8-bit non-linear sRGB LUT
    static byte[] s8Tol8 = null;   // 8-bit non-linear sRGB to 8-bit linear LUT
    static byte[] l16Tos8 = null;  // 16-bit linear to 8-bit non-linear sRGB LUT
    static short[] s8Tol16 = null; // 8-bit non-linear sRGB to 16-bit linear LUT

                                // Maps to hold LUTs for grayscale conversions
    static Map<ICC_ColorSpace, byte[]> g8Tos8Map = null;     // 8-bit gray values to 8-bit sRGB values
    static Map<ICC_ColorSpace, byte[]> lg16Toog8Map = null;  // 16-bit linear to 8-bit "other" gray
    static Map<ICC_ColorSpace, byte[]> g16Tos8Map = null;    // 16-bit gray values to 8-bit sRGB values
    static Map<ICC_ColorSpace, short[]> lg16Toog16Map = null; // 16-bit linear to 16-bit "other" gray

    static boolean isLinearRGBspace(ColorSpace cs) {
        // Note: CMM.LINEAR_RGBspace will be null if the linear
        // RGB space has not been created yet.
        return (cs == CMSManager.LINEAR_RGBspace);
    }

    static boolean isLinearGRAYspace(ColorSpace cs) {
        // Note: CMM.GRAYspace will be null if the linear
        // gray space has not been created yet.
        return (cs == CMSManager.GRAYspace);
    }

    static byte[] getLinearRGB8TosRGB8LUT() {
        if (l8Tos8 == null) {
            l8Tos8 = new byte[256];
            float input, output;
            // algorithm for linear RGB to nonlinear sRGB conversion
            // is from the IEC 61966-2-1 International Standard,
            // Colour Management - Default RGB colour space - sRGB,
            // First Edition, 1999-10,
            // avaiable for order at http://www.iec.ch
            for (int i = 0; i <= 255; i++) {
                input = ((float) i) / 255.0f;
                if (input <= 0.0031308f) {
                    output = input * 12.92f;
                } else {
                    output = 1.055f * ((float) Math.pow(input, (1.0 / 2.4)))
                             - 0.055f;
                }
                l8Tos8[i] = (byte) Math.round(output * 255.0f);
            }
        }
        return l8Tos8;
    }

    static byte[] getsRGB8ToLinearRGB8LUT() {
        if (s8Tol8 == null) {
            s8Tol8 = new byte[256];
            float input, output;
            // algorithm from IEC 61966-2-1 International Standard
            for (int i = 0; i <= 255; i++) {
                input = ((float) i) / 255.0f;
                if (input <= 0.04045f) {
                    output = input / 12.92f;
                } else {
                    output = (float) Math.pow((input + 0.055f) / 1.055f, 2.4);
                }
                s8Tol8[i] = (byte) Math.round(output * 255.0f);
            }
        }
        return s8Tol8;
    }

    static byte[] getLinearRGB16TosRGB8LUT() {
        if (l16Tos8 == null) {
            l16Tos8 = new byte[65536];
            float input, output;
            // algorithm from IEC 61966-2-1 International Standard
            for (int i = 0; i <= 65535; i++) {
                input = ((float) i) / 65535.0f;
                if (input <= 0.0031308f) {
                    output = input * 12.92f;
                } else {
                    output = 1.055f * ((float) Math.pow(input, (1.0 / 2.4)))
                             - 0.055f;
                }
                l16Tos8[i] = (byte) Math.round(output * 255.0f);
            }
        }
        return l16Tos8;
    }

    static short[] getsRGB8ToLinearRGB16LUT() {
        if (s8Tol16 == null) {
            s8Tol16 = new short[256];
            float input, output;
            // algorithm from IEC 61966-2-1 International Standard
            for (int i = 0; i <= 255; i++) {
                input = ((float) i) / 255.0f;
                if (input <= 0.04045f) {
                    output = input / 12.92f;
                } else {
                    output = (float) Math.pow((input + 0.055f) / 1.055f, 2.4);
                }
                s8Tol16[i] = (short) Math.round(output * 65535.0f);
            }
        }
        return s8Tol16;
    }

    /*
     * Return a byte LUT that converts 8-bit gray values in the grayCS
     * ColorSpace to the appropriate 8-bit sRGB value.  I.e., if lut
     * is the byte array returned by this method and sval = lut[gval],
     * then the sRGB triple (sval,sval,sval) is the best match to gval.
     * Cache references to any computed LUT in a Map.
     * <p>
     *  返回一个字节LUT,将grayCS ColorSpace中的8位灰度值转换为适当的8位sRGB值Ie,如果lut是此方法返回的字节数组,sval = lut [gval],则sRGB三元组(sval,
     *  sval,sval)是gval Cache对Map中任何计算的LUT的引用的最佳匹配。
     * 
     */
    static byte[] getGray8TosRGB8LUT(ICC_ColorSpace grayCS) {
        if (isLinearGRAYspace(grayCS)) {
            return getLinearRGB8TosRGB8LUT();
        }
        if (g8Tos8Map != null) {
            byte[] g8Tos8LUT = g8Tos8Map.get(grayCS);
            if (g8Tos8LUT != null) {
                return g8Tos8LUT;
            }
        }
        byte[] g8Tos8LUT = new byte[256];
        for (int i = 0; i <= 255; i++) {
            g8Tos8LUT[i] = (byte) i;
        }
        ColorTransform[] transformList = new ColorTransform[2];
        PCMM mdl = CMSManager.getModule();
        ICC_ColorSpace srgbCS =
            (ICC_ColorSpace) ColorSpace.getInstance(ColorSpace.CS_sRGB);
        transformList[0] = mdl.createTransform(
            grayCS.getProfile(), ColorTransform.Any, ColorTransform.In);
        transformList[1] = mdl.createTransform(
            srgbCS.getProfile(), ColorTransform.Any, ColorTransform.Out);
        ColorTransform t = mdl.createTransform(transformList);
        byte[] tmp = t.colorConvert(g8Tos8LUT, null);
        for (int i = 0, j= 2; i <= 255; i++, j += 3) {
            // All three components of tmp should be equal, since
            // the input color space to colorConvert is a gray scale
            // space.  However, there are slight anomalies in the results.
            // Copy tmp starting at index 2, since colorConvert seems
            // to be slightly more accurate for the third component!
            g8Tos8LUT[i] = tmp[j];
        }
        if (g8Tos8Map == null) {
            g8Tos8Map = Collections.synchronizedMap(new WeakHashMap<ICC_ColorSpace, byte[]>(2));
        }
        g8Tos8Map.put(grayCS, g8Tos8LUT);
        return g8Tos8LUT;
    }

    /*
     * Return a byte LUT that converts 16-bit gray values in the CS_GRAY
     * linear gray ColorSpace to the appropriate 8-bit value in the
     * grayCS ColorSpace.  Cache references to any computed LUT in a Map.
     * <p>
     *  返回一个字节LUT,将CS_GRAY线性灰色ColorSpace中的16位灰度值转换为grayCS ColorSpace缓冲区中适当的8位值。对Map中任何计算的LUT的引用
     * 
     */
    static byte[] getLinearGray16ToOtherGray8LUT(ICC_ColorSpace grayCS) {
        if (lg16Toog8Map != null) {
            byte[] lg16Toog8LUT = lg16Toog8Map.get(grayCS);
            if (lg16Toog8LUT != null) {
                return lg16Toog8LUT;
            }
        }
        short[] tmp = new short[65536];
        for (int i = 0; i <= 65535; i++) {
            tmp[i] = (short) i;
        }
        ColorTransform[] transformList = new ColorTransform[2];
        PCMM mdl = CMSManager.getModule();
        ICC_ColorSpace lgCS =
            (ICC_ColorSpace) ColorSpace.getInstance(ColorSpace.CS_GRAY);
        transformList[0] = mdl.createTransform (
            lgCS.getProfile(), ColorTransform.Any, ColorTransform.In);
        transformList[1] = mdl.createTransform (
            grayCS.getProfile(), ColorTransform.Any, ColorTransform.Out);
        ColorTransform t = mdl.createTransform(transformList);
        tmp = t.colorConvert(tmp, null);
        byte[] lg16Toog8LUT = new byte[65536];
        for (int i = 0; i <= 65535; i++) {
            // scale unsigned short (0 - 65535) to unsigned byte (0 - 255)
            lg16Toog8LUT[i] =
                (byte) (((float) (tmp[i] & 0xffff)) * (1.0f /257.0f) + 0.5f);
        }
        if (lg16Toog8Map == null) {
            lg16Toog8Map = Collections.synchronizedMap(new WeakHashMap<ICC_ColorSpace, byte[]>(2));
        }
        lg16Toog8Map.put(grayCS, lg16Toog8LUT);
        return lg16Toog8LUT;
    }

    /*
     * Return a byte LUT that converts 16-bit gray values in the grayCS
     * ColorSpace to the appropriate 8-bit sRGB value.  I.e., if lut
     * is the byte array returned by this method and sval = lut[gval],
     * then the sRGB triple (sval,sval,sval) is the best match to gval.
     * Cache references to any computed LUT in a Map.
     * <p>
     * 返回一个字节LUT,将grayCS ColorSpace中的16位灰度值转换为适当的8位sRGB值Ie,如果lut是此方法返回的字节数组,sval = lut [gval],则sRGB三元组(sval,
     *  sval,sval)是gval Cache对Map中任何计算的LUT的引用的最佳匹配。
     * 
     */
    static byte[] getGray16TosRGB8LUT(ICC_ColorSpace grayCS) {
        if (isLinearGRAYspace(grayCS)) {
            return getLinearRGB16TosRGB8LUT();
        }
        if (g16Tos8Map != null) {
            byte[] g16Tos8LUT = g16Tos8Map.get(grayCS);
            if (g16Tos8LUT != null) {
                return g16Tos8LUT;
            }
        }
        short[] tmp = new short[65536];
        for (int i = 0; i <= 65535; i++) {
            tmp[i] = (short) i;
        }
        ColorTransform[] transformList = new ColorTransform[2];
        PCMM mdl = CMSManager.getModule();
        ICC_ColorSpace srgbCS =
            (ICC_ColorSpace) ColorSpace.getInstance(ColorSpace.CS_sRGB);
        transformList[0] = mdl.createTransform (
            grayCS.getProfile(), ColorTransform.Any, ColorTransform.In);
        transformList[1] = mdl.createTransform (
            srgbCS.getProfile(), ColorTransform.Any, ColorTransform.Out);
        ColorTransform t = mdl.createTransform(transformList);
        tmp = t.colorConvert(tmp, null);
        byte[] g16Tos8LUT = new byte[65536];
        for (int i = 0, j= 2; i <= 65535; i++, j += 3) {
            // All three components of tmp should be equal, since
            // the input color space to colorConvert is a gray scale
            // space.  However, there are slight anomalies in the results.
            // Copy tmp starting at index 2, since colorConvert seems
            // to be slightly more accurate for the third component!

            // scale unsigned short (0 - 65535) to unsigned byte (0 - 255)
            g16Tos8LUT[i] =
                (byte) (((float) (tmp[j] & 0xffff)) * (1.0f /257.0f) + 0.5f);
        }
        if (g16Tos8Map == null) {
            g16Tos8Map = Collections.synchronizedMap(new WeakHashMap<ICC_ColorSpace, byte[]>(2));
        }
        g16Tos8Map.put(grayCS, g16Tos8LUT);
        return g16Tos8LUT;
    }

    /*
     * Return a short LUT that converts 16-bit gray values in the CS_GRAY
     * linear gray ColorSpace to the appropriate 16-bit value in the
     * grayCS ColorSpace.  Cache references to any computed LUT in a Map.
     * <p>
     */
    static short[] getLinearGray16ToOtherGray16LUT(ICC_ColorSpace grayCS) {
        if (lg16Toog16Map != null) {
            short[] lg16Toog16LUT = lg16Toog16Map.get(grayCS);
            if (lg16Toog16LUT != null) {
                return lg16Toog16LUT;
            }
        }
        short[] tmp = new short[65536];
        for (int i = 0; i <= 65535; i++) {
            tmp[i] = (short) i;
        }
        ColorTransform[] transformList = new ColorTransform[2];
        PCMM mdl = CMSManager.getModule();
        ICC_ColorSpace lgCS =
            (ICC_ColorSpace) ColorSpace.getInstance(ColorSpace.CS_GRAY);
        transformList[0] = mdl.createTransform (
            lgCS.getProfile(), ColorTransform.Any, ColorTransform.In);
        transformList[1] = mdl.createTransform(
            grayCS.getProfile(), ColorTransform.Any, ColorTransform.Out);
        ColorTransform t = mdl.createTransform(
            transformList);
        short[] lg16Toog16LUT = t.colorConvert(tmp, null);
        if (lg16Toog16Map == null) {
            lg16Toog16Map = Collections.synchronizedMap(new WeakHashMap<ICC_ColorSpace, short[]>(2));
        }
        lg16Toog16Map.put(grayCS, lg16Toog16LUT);
        return lg16Toog16LUT;
    }

}
