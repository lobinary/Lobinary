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

import java.awt.color.ColorSpace;
import java.awt.Transparency;

/**
 * The <code>DirectColorModel</code> class is a <code>ColorModel</code>
 * class that works with pixel values that represent RGB
 * color and alpha information as separate samples and that pack all
 * samples for a single pixel into a single int, short, or byte quantity.
 * This class can be used only with ColorSpaces of type ColorSpace.TYPE_RGB.
 * In addition, for each component of the ColorSpace, the minimum
 * normalized component value obtained via the <code>getMinValue()</code>
 * method of ColorSpace must be 0.0, and the maximum value obtained via
 * the <code>getMaxValue()</code> method must be 1.0 (these min/max
 * values are typical for RGB spaces).
 * There must be three color samples in the pixel values and there can
 * be a single alpha sample.  For those methods that use a primitive array
 * pixel representation of type <code>transferType</code>, the array
 * length is always one.  The transfer
 * types supported are DataBuffer.TYPE_BYTE,
 * DataBuffer.TYPE_USHORT, and DataBuffer.TYPE_INT.
 * Color and alpha samples are stored in the single
 * element of the array in bits indicated by bit masks.  Each bit mask
 * must be contiguous and masks must not overlap.  The same masks apply to
 * the single int pixel representation used by other methods.  The
 * correspondence of masks and color/alpha samples is as follows:
 * <ul>
 * <li> Masks are identified by indices running from 0 through 2
 * if no alpha is present, or 3 if an alpha is present.
 * <li> The first three indices refer to color samples;
 * index 0 corresponds to red, index 1 to green, and index 2 to blue.
 * <li> Index 3 corresponds to the alpha sample, if present.
 * </ul>
 * <p>
 * The translation from pixel values to color/alpha components for
 * display or processing purposes is a one-to-one correspondence of
 * samples to components.  A <code>DirectColorModel</code> is
 * typically used with image data which uses masks to define packed
 * samples.  For example, a <code>DirectColorModel</code> can be used in
 * conjunction with a <code>SinglePixelPackedSampleModel</code> to
 * construct a {@link BufferedImage}.  Normally the masks used by the
 * {@link SampleModel} and the <code>ColorModel</code> would be the
 * same.  However, if they are different, the color interpretation
 * of pixel data will be done according to the masks of the
 * <code>ColorModel</code>.
 * <p>
 * A single int pixel representation is valid for all objects of this
 * class, since it is always possible to represent pixel values used with
 * this class in a single int.  Therefore, methods which use this
 * representation will not throw an <code>IllegalArgumentException</code>
 * due to an invalid pixel value.
 * <p>
 * This color model is similar to an X11 TrueColor visual.
 * The default RGB ColorModel specified by the
 * {@link ColorModel#getRGBdefault() getRGBdefault} method is a
 * <code>DirectColorModel</code> with the following parameters:
 * <pre>
 * Number of bits:        32
 * Red mask:              0x00ff0000
 * Green mask:            0x0000ff00
 * Blue mask:             0x000000ff
 * Alpha mask:            0xff000000
 * Color space:           sRGB
 * isAlphaPremultiplied:  False
 * Transparency:          Transparency.TRANSLUCENT
 * transferType:          DataBuffer.TYPE_INT
 * </pre>
 * <p>
 * Many of the methods in this class are final. This is because the
 * underlying native graphics code makes assumptions about the layout
 * and operation of this class and those assumptions are reflected in
 * the implementations of the methods here that are marked final.  You
 * can subclass this class for other reasons, but you cannot override
 * or modify the behavior of those methods.
 *
 * <p>
 *  <code> DirectColorModel </code>类是一个<code> ColorModel </code>类,它使用表示RGB颜色和alpha信息的像素值作为单独的样本工作,并将单个像素
 * 的所有样本打包为单个int,短,或字节数量。
 * 此类只能与ColorSpace.TYPE_RGB类型的ColorSpaces一起使用。
 * 此外,对于ColorSpace的每个组件,通过ColorSpace的<code> getMinValue()</code>方法获得的最小标准化组件值必须为0.0,通过<code> getMaxValue
 *  code>方法必须为1.0(这些最小/最大值是RGB空间的典型值)。
 * 此类只能与ColorSpace.TYPE_RGB类型的ColorSpaces一起使用。在像素值中必须有三个颜色样本,并且可以有单个α样本。
 * 对于使用<code> transferType </code>类型的基本数组像素表示的那些方法,数组长度总是为1。
 * 支持的传输类型是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT和DataBuffer.TYPE_INT。颜色和α样本以由位掩码指示的位存储在阵列的单个元素中。
 * 每个位掩码必须是连续的,并且掩码不能重叠。相同的掩码应用于由其他方法使用的单个int像素表示。掩模和颜色/α样本的对应如下：。
 * <ul>
 * <li>如果不存在alpha,则掩码由从0到2的索引标识;如果存在alpha,则掩码由3标识。 <li>前三个指数是指颜色样本;索引0对应于红色,索引1对应于绿色,索引2对应于蓝色。
 *  <li>索引3对应于alpha样本(如果存在)。
 * </ul>
 * <p>
 *  从像素值到颜色/阿尔法分量的显示或处理目的的转换是样本与分量的一对一对应。 <code> DirectColorModel </code>通常与使用蒙版来定义打包样本的图像数据一起使用。
 * 例如,<code> DirectColorModel </code>可以与<code> SinglePixelPackedSampleModel </code>结合使用以构造{@link BufferedImage}
 * 。
 *  从像素值到颜色/阿尔法分量的显示或处理目的的转换是样本与分量的一对一对应。 <code> DirectColorModel </code>通常与使用蒙版来定义打包样本的图像数据一起使用。
 * 通常,{@link SampleModel}和<code> ColorModel </code>使用的掩码将是相同的。
 * 然而,如果它们不同,则将根据<code> ColorModel </code>的掩码来进行像素数据的颜色解释。
 * <p>
 *  单个int像素表示对于该类的所有对象有效,因为总是可以在单个int中表示与该类一起使用的像素值。
 * 因此,由于无效的像素值,使用此表示的方法不会抛出<code> IllegalArgumentException </code>。
 * <p>
 *  此颜色模型类似于X11 TrueColor视觉效果。
 * 由{@link ColorModel#getRGBdefault()getRGBdefault}方法指定的默认RGB ColorModel是具有以下参数的<code> DirectColorModel 
 * </code>：。
 *  此颜色模型类似于X11 TrueColor视觉效果。
 * <pre>
 * 位数：32红色掩码：0x00ff0000绿色掩码：0x0000ff00蓝色掩码：0x000000ff Alpha掩码：0xff000000颜色空间：sRGB isAlphaPremultiplied：F
 * alse透明度：Transparency.TRANSLUCENT transferType：DataBuffer.TYPE_INT。
 * </pre>
 * <p>
 *  这个类中的许多方法是最终的。这是因为底层本地图形代码对这个类的布局和操作进行了假设,这些假设反映在这里标记为final的方法的实现中。
 * 由于其他原因,您可以对此类进行子类化,但不能覆盖或修改这些方法的行为。
 * 
 * 
 * @see ColorModel
 * @see ColorSpace
 * @see SinglePixelPackedSampleModel
 * @see BufferedImage
 * @see ColorModel#getRGBdefault
 *
 */
public class DirectColorModel extends PackedColorModel {
    private int red_mask;
    private int green_mask;
    private int blue_mask;
    private int alpha_mask;
    private int red_offset;
    private int green_offset;
    private int blue_offset;
    private int alpha_offset;
    private int red_scale;
    private int green_scale;
    private int blue_scale;
    private int alpha_scale;
    private boolean is_LinearRGB;
    private int lRGBprecision;
    private byte[] tosRGB8LUT;
    private byte[] fromsRGB8LUT8;
    private short[] fromsRGB8LUT16;

    /**
     * Constructs a <code>DirectColorModel</code> from the specified masks
     * that indicate which bits in an <code>int</code> pixel representation
     * contain the red, green and blue color samples.  As pixel values do not
     * contain alpha information, all pixels are treated as opaque, which
     * means that alpha&nbsp;=&nbsp;1.0.  All of the bits
     * in each mask must be contiguous and fit in the specified number
     * of least significant bits of an <code>int</code> pixel representation.
     *  The <code>ColorSpace</code> is the default sRGB space. The
     * transparency value is Transparency.OPAQUE.  The transfer type
     * is the smallest of DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT,
     * or DataBuffer.TYPE_INT that can hold a single pixel.
     * <p>
     *  从指定掩码中构造<code> DirectColorModel </code>,指示<code> int </code>像素表示中的哪些位包含红色,绿色和蓝色样本。
     * 由于像素值不包含Alpha信息,因此所有像素都被视为不透明,这意味着alpha&nbsp; =&nbsp; 1.0。
     * 每个掩码中的所有位必须是连续的并且适合于<code> int </code>像素表示的指定数量的最低有效位。 <code> ColorSpace </code>是默认的sRGB空间。
     * 透明度值为Transparency.OPAQUE。
     * 传输类型是可以容纳单个像素的DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT或DataBuffer.TYPE_INT中最小的。
     * 
     * 
     * @param bits the number of bits in the pixel values; for example,
     *         the sum of the number of bits in the masks.
     * @param rmask specifies a mask indicating which bits in an
     *         integer pixel contain the red component
     * @param gmask specifies a mask indicating which bits in an
     *         integer pixel contain the green component
     * @param bmask specifies a mask indicating which bits in an
     *         integer pixel contain the blue component
     *
     */
    public DirectColorModel(int bits,
                            int rmask, int gmask, int bmask) {
        this(bits, rmask, gmask, bmask, 0);
    }

    /**
     * Constructs a <code>DirectColorModel</code> from the specified masks
     * that indicate which bits in an <code>int</code> pixel representation
     * contain the red, green and blue color samples and the alpha sample,
     * if present.  If <code>amask</code> is 0, pixel values do not contain
     * alpha information and all pixels are treated as opaque, which means
     * that alpha&nbsp;=&nbsp;1.0.  All of the bits in each mask must
     * be contiguous and fit in the specified number of least significant bits
     * of an <code>int</code> pixel representation.  Alpha, if present, is not
     * premultiplied.  The <code>ColorSpace</code> is the default sRGB space.
     * The transparency value is Transparency.OPAQUE if no alpha is
     * present, or Transparency.TRANSLUCENT otherwise.  The transfer type
     * is the smallest of DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT,
     * or DataBuffer.TYPE_INT that can hold a single pixel.
     * <p>
     * 从指定掩码中构造<code> DirectColorModel </code>,指示<code> int </code>像素表示中的哪些位包含红色,绿色和蓝色颜色样本以及alpha样本(如果存在)。
     * 如果<code> amask </code>为0,则像素值不包含Alpha信息,所有像素都被视为不透明,这意味着alpha&nbsp; =&nbsp; 1.0。
     * 每个掩码中的所有位必须是连续的并且适合于<code> int </code>像素表示的指定数量的最低有效位。阿尔法(如果存在)不是预乘的。
     *  <code> ColorSpace </code>是默认的sRGB空间。
     * 如果没有alpha值,透明度值为Transparency.OPAQUE,否则为Transparency.TRANSLUCENT。
     * 传输类型是可以容纳单个像素的DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT或DataBuffer.TYPE_INT中最小的。
     * 
     * 
     * @param bits the number of bits in the pixel values; for example,
     *         the sum of the number of bits in the masks.
     * @param rmask specifies a mask indicating which bits in an
     *         integer pixel contain the red component
     * @param gmask specifies a mask indicating which bits in an
     *         integer pixel contain the green component
     * @param bmask specifies a mask indicating which bits in an
     *         integer pixel contain the blue component
     * @param amask specifies a mask indicating which bits in an
     *         integer pixel contain the alpha component
     */
    public DirectColorModel(int bits, int rmask, int gmask,
                            int bmask, int amask) {
        super (ColorSpace.getInstance(ColorSpace.CS_sRGB),
               bits, rmask, gmask, bmask, amask, false,
               amask == 0 ? Transparency.OPAQUE : Transparency.TRANSLUCENT,
               ColorModel.getDefaultTransferType(bits));
        setFields();
    }

    /**
     * Constructs a <code>DirectColorModel</code> from the specified
     * parameters.  Color components are in the specified
     * <code>ColorSpace</code>, which must be of type ColorSpace.TYPE_RGB
     * and have minimum normalized component values which are all 0.0
     * and maximum values which are all 1.0.
     * The masks specify which bits in an <code>int</code> pixel
     * representation contain the red, green and blue color samples and
     * the alpha sample, if present.  If <code>amask</code> is 0, pixel
     * values do not contain alpha information and all pixels are treated
     * as opaque, which means that alpha&nbsp;=&nbsp;1.0.  All of the
     * bits in each mask must be contiguous and fit in the specified number
     * of least significant bits of an <code>int</code> pixel
     * representation.  If there is alpha, the <code>boolean</code>
     * <code>isAlphaPremultiplied</code> specifies how to interpret
     * color and alpha samples in pixel values.  If the <code>boolean</code>
     * is <code>true</code>, color samples are assumed to have been
     * multiplied by the alpha sample.  The transparency value is
     * Transparency.OPAQUE, if no alpha is present, or
     * Transparency.TRANSLUCENT otherwise.  The transfer type
     * is the type of primitive array used to represent pixel values and
     * must be one of DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT, or
     * DataBuffer.TYPE_INT.
     * <p>
     * 从指定的参数构造一个<code> DirectColorModel </code>。
     * 颜色分量在指定的<code> ColorSpace </code>中,它必须是ColorSpace.TYPE_RGB类型,并且具有最小标准化组件值,均为0.0,最大值均为1.0。
     * 掩码指定<code> int </code>像素表示中的哪些位包含红色,绿色和蓝色样本以及α样本(如果存在)。
     * 如果<code> amask </code>为0,则像素值不包含Alpha信息,所有像素都被视为不透明,这意味着alpha&nbsp; =&nbsp; 1.0。
     * 每个掩码中的所有位必须是连续的并且适合于<code> int </code>像素表示的指定数量的最低有效位。
     * 如果有alpha,<code> boolean </code> <code> isAlphaPremultiplied </code>指定如何解释像素值中的颜色和alpha样本。
     * 如果<code> boolean </code>是<code> true </code>,则颜色样本假定已经乘以alpha样本。
     * 如果没有alpha值,透明度值为Transparency.OPAQUE,否则为Transparency.TRANSLUCENT。
     * 传输类型是用于表示像素值的基本数组类型,必须是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT或DataBuffer.TYPE_INT中的一个。
     * 
     * 
     * @param space the specified <code>ColorSpace</code>
     * @param bits the number of bits in the pixel values; for example,
     *         the sum of the number of bits in the masks.
     * @param rmask specifies a mask indicating which bits in an
     *         integer pixel contain the red component
     * @param gmask specifies a mask indicating which bits in an
     *         integer pixel contain the green component
     * @param bmask specifies a mask indicating which bits in an
     *         integer pixel contain the blue component
     * @param amask specifies a mask indicating which bits in an
     *         integer pixel contain the alpha component
     * @param isAlphaPremultiplied <code>true</code> if color samples are
     *        premultiplied by the alpha sample; <code>false</code> otherwise
     * @param transferType the type of array used to represent pixel values
     * @throws IllegalArgumentException if <code>space</code> is not a
     *         TYPE_RGB space or if the min/max normalized component
     *         values are not 0.0/1.0.
     */
    public DirectColorModel(ColorSpace space, int bits, int rmask,
                            int gmask, int bmask, int amask,
                            boolean isAlphaPremultiplied,
                            int transferType) {
        super (space, bits, rmask, gmask, bmask, amask,
               isAlphaPremultiplied,
               amask == 0 ? Transparency.OPAQUE : Transparency.TRANSLUCENT,
               transferType);
        if (ColorModel.isLinearRGBspace(colorSpace)) {
            is_LinearRGB = true;
            if (maxBits <= 8) {
                lRGBprecision = 8;
                tosRGB8LUT = ColorModel.getLinearRGB8TosRGB8LUT();
                fromsRGB8LUT8 = ColorModel.getsRGB8ToLinearRGB8LUT();
            } else {
                lRGBprecision = 16;
                tosRGB8LUT = ColorModel.getLinearRGB16TosRGB8LUT();
                fromsRGB8LUT16 = ColorModel.getsRGB8ToLinearRGB16LUT();
            }
        } else if (!is_sRGB) {
            for (int i = 0; i < 3; i++) {
                // super constructor checks that space is TYPE_RGB
                // check here that min/max are all 0.0/1.0
                if ((space.getMinValue(i) != 0.0f) ||
                    (space.getMaxValue(i) != 1.0f)) {
                    throw new IllegalArgumentException(
                        "Illegal min/max RGB component value");
                }
            }
        }
        setFields();
    }

    /**
     * Returns the mask indicating which bits in an <code>int</code> pixel
     * representation contain the red color component.
     * <p>
     *  返回指示<code> int </code>像素表示中哪些位包含红色分量的掩码。
     * 
     * 
     * @return the mask, which indicates which bits of the <code>int</code>
     *         pixel representation contain the red color sample.
     */
    final public int getRedMask() {
        return maskArray[0];
    }

    /**
     * Returns the mask indicating which bits in an <code>int</code> pixel
     * representation contain the green color component.
     * <p>
     * 返回掩码,指示<code> int </code>像素表示中的哪些位包含绿色颜色分量。
     * 
     * 
     * @return the mask, which indicates which bits of the <code>int</code>
     *         pixel representation contain the green color sample.
     */
    final public int getGreenMask() {
        return maskArray[1];
    }

    /**
     * Returns the mask indicating which bits in an <code>int</code> pixel
     * representation contain the blue color component.
     * <p>
     *  返回掩码,指示<code> int </code>像素表示中的哪些位包含蓝色颜色分量。
     * 
     * 
     * @return the mask, which indicates which bits of the <code>int</code>
     *         pixel representation contain the blue color sample.
     */
    final public int getBlueMask() {
        return maskArray[2];
    }

    /**
     * Returns the mask indicating which bits in an <code>int</code> pixel
     * representation contain the alpha component.
     * <p>
     *  返回掩码,指示<code> int </code>像素表示中的哪些位包含alpha组件。
     * 
     * 
     * @return the mask, which indicates which bits of the <code>int</code>
     *         pixel representation contain the alpha sample.
     */
    final public int getAlphaMask() {
        if (supportsAlpha) {
            return maskArray[3];
        } else {
            return 0;
        }
    }


    /*
     * Given an int pixel in this ColorModel's ColorSpace, converts
     * it to the default sRGB ColorSpace and returns the R, G, and B
     * components as float values between 0.0 and 1.0.
     * <p>
     *  在ColorModel的ColorSpace中给定一个int像素,将其转换为默认的sRGB ColorSpace,并返回R,G和B分量作为0.0和1.0之间的浮点值。
     * 
     */
    private float[] getDefaultRGBComponents(int pixel) {
        int components[] = getComponents(pixel, null, 0);
        float norm[] = getNormalizedComponents(components, 0, null, 0);
        // Note that getNormalizedComponents returns non-premultiplied values
        return colorSpace.toRGB(norm);
    }


    private int getsRGBComponentFromsRGB(int pixel, int idx) {
        int c = ((pixel & maskArray[idx]) >>> maskOffsets[idx]);
        if (isAlphaPremultiplied) {
            int a = ((pixel & maskArray[3]) >>> maskOffsets[3]);
            c = (a == 0) ? 0 :
                         (int) (((c * scaleFactors[idx]) * 255.0f /
                                 (a * scaleFactors[3])) + 0.5f);
        } else if (scaleFactors[idx] != 1.0f) {
            c = (int) ((c * scaleFactors[idx]) + 0.5f);
        }
        return c;
    }


    private int getsRGBComponentFromLinearRGB(int pixel, int idx) {
        int c = ((pixel & maskArray[idx]) >>> maskOffsets[idx]);
        if (isAlphaPremultiplied) {
            float factor = (float) ((1 << lRGBprecision) - 1);
            int a = ((pixel & maskArray[3]) >>> maskOffsets[3]);
            c = (a == 0) ? 0 :
                         (int) (((c * scaleFactors[idx]) * factor /
                                 (a * scaleFactors[3])) + 0.5f);
        } else if (nBits[idx] != lRGBprecision) {
            if (lRGBprecision == 16) {
                c = (int) ((c * scaleFactors[idx] * 257.0f) + 0.5f);
            } else {
                c = (int) ((c * scaleFactors[idx]) + 0.5f);
            }
        }
        // now range of c is 0-255 or 0-65535, depending on lRGBprecision
        return tosRGB8LUT[c] & 0xff;
    }


    /**
     * Returns the red color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is specified
     * as an <code>int</code>.
     * The returned value is a non pre-multiplied value.  Thus, if the
     * alpha is premultiplied, this method divides it out before returning
     * the value.  If the alpha value is 0, for example, the red value
     * is 0.
     * <p>
     *  返回指定像素的红色分量,在默认的RGB <code> ColorSpace </code>,sRGB中从0到255。如果需要,进行颜色转换。像素值指定为<code> int </code>。
     * 返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。如果alpha值为0,例如,红色值为0。
     * 
     * 
     * @param pixel the specified pixel
     * @return the red color component for the specified pixel, from
     *         0 to 255 in the sRGB <code>ColorSpace</code>.
     */
    final public int getRed(int pixel) {
        if (is_sRGB) {
            return getsRGBComponentFromsRGB(pixel, 0);
        } else if (is_LinearRGB) {
            return getsRGBComponentFromLinearRGB(pixel, 0);
        }
        float rgb[] = getDefaultRGBComponents(pixel);
        return (int) (rgb[0] * 255.0f + 0.5f);
    }

    /**
     * Returns the green color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is specified
     * as an <code>int</code>.
     * The returned value is a non pre-multiplied value.  Thus, if the
     * alpha is premultiplied, this method divides it out before returning
     * the value.  If the alpha value is 0, for example, the green value
     * is 0.
     * <p>
     *  返回指定像素的绿色分量,在默认的RGB <code> ColorSpace </code>,sRGB中从0到255。如果需要,进行颜色转换。像素值指定为<code> int </code>。
     * 返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。如果alpha值为0,例如,绿色值为0。
     * 
     * 
     * @param pixel the specified pixel
     * @return the green color component for the specified pixel, from
     *         0 to 255 in the sRGB <code>ColorSpace</code>.
     */
    final public int getGreen(int pixel) {
        if (is_sRGB) {
            return getsRGBComponentFromsRGB(pixel, 1);
        } else if (is_LinearRGB) {
            return getsRGBComponentFromLinearRGB(pixel, 1);
        }
        float rgb[] = getDefaultRGBComponents(pixel);
        return (int) (rgb[1] * 255.0f + 0.5f);
    }

    /**
     * Returns the blue color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is specified
     * as an <code>int</code>.
     * The returned value is a non pre-multiplied value.  Thus, if the
     * alpha is premultiplied, this method divides it out before returning
     * the value.  If the alpha value is 0, for example, the blue value
     * is 0.
     * <p>
     * 返回指定像素的蓝色分量,在默认的RGB <code> ColorSpace </code>,sRGB中从0到255。如果需要,进行颜色转换。像素值指定为<code> int </code>。
     * 返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。如果alpha值为0,例如,蓝色值为0。
     * 
     * 
     * @param pixel the specified pixel
     * @return the blue color component for the specified pixel, from
     *         0 to 255 in the sRGB <code>ColorSpace</code>.
     */
    final public int getBlue(int pixel) {
        if (is_sRGB) {
            return getsRGBComponentFromsRGB(pixel, 2);
        } else if (is_LinearRGB) {
            return getsRGBComponentFromLinearRGB(pixel, 2);
        }
        float rgb[] = getDefaultRGBComponents(pixel);
        return (int) (rgb[2] * 255.0f + 0.5f);
    }

    /**
     * Returns the alpha component for the specified pixel, scaled
     * from 0 to 255.  The pixel value is specified as an <code>int</code>.
     * <p>
     *  返回指定像素的alpha分量,从0到255。像素值指定为<code> int </code>。
     * 
     * 
     * @param pixel the specified pixel
     * @return the value of the alpha component of <code>pixel</code>
     *         from 0 to 255.
     */
    final public int getAlpha(int pixel) {
        if (!supportsAlpha) return 255;
        int a = ((pixel & maskArray[3]) >>> maskOffsets[3]);
        if (scaleFactors[3] != 1.0f) {
            a = (int)(a * scaleFactors[3] + 0.5f);
        }
        return a;
    }

    /**
     * Returns the color/alpha components of the pixel in the default
     * RGB color model format.  A color conversion is done if necessary.
     * The pixel value is specified as an <code>int</code>.
     * The returned value is in a non pre-multiplied format.  Thus, if
     * the alpha is premultiplied, this method divides it out of the
     * color components.  If the alpha value is 0, for example, the color
     * values are each 0.
     * <p>
     *  以默认的RGB颜色模型格式返回像素的颜色/ alpha分量。如果需要,进行颜色转换。像素值指定为<code> int </code>。返回的值采用非预扩展格式。
     * 因此,如果alpha被预乘,该方法将其从颜色分量中分离出来。例如,如果α值为0,则颜色值均为0。
     * 
     * 
     * @param pixel the specified pixel
     * @return the RGB value of the color/alpha components of the specified
     *         pixel.
     * @see ColorModel#getRGBdefault
     */
    final public int getRGB(int pixel) {
        if (is_sRGB || is_LinearRGB) {
            return (getAlpha(pixel) << 24)
                | (getRed(pixel) << 16)
                | (getGreen(pixel) << 8)
                | (getBlue(pixel) << 0);
        }
        float rgb[] = getDefaultRGBComponents(pixel);
        return (getAlpha(pixel) << 24)
            | (((int) (rgb[0] * 255.0f + 0.5f)) << 16)
            | (((int) (rgb[1] * 255.0f + 0.5f)) << 8)
            | (((int) (rgb[2] * 255.0f + 0.5f)) << 0);
    }

    /**
     * Returns the red color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is specified
     * by an array of data elements of type <code>transferType</code> passed
     * in as an object reference.
     * The returned value is a non pre-multiplied value.  Thus, if the
     * alpha is premultiplied, this method divides it out before returning
     * the value.  If the alpha value is 0, for example, the red value
     * is 0.
     * If <code>inData</code> is not a primitive array of type
     * <code>transferType</code>, a <code>ClassCastException</code> is
     * thrown.  An <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>inData</code> is not large enough to hold a
     * pixel value for this <code>ColorModel</code>.  Since
     * <code>DirectColorModel</code> can be subclassed, subclasses inherit
     * the implementation of this method and if they don't override it
     * then they throw an exception if they use an unsupported
     * <code>transferType</code>.
     * An <code>UnsupportedOperationException</code> is thrown if this
     * <code>transferType</code> is not supported by this
     * <code>ColorModel</code>.
     * <p>
     * 返回指定像素的红色分量,在默认的RGB <code> ColorSpace </code>,sRGB中从0到255。如果需要,进行颜色转换。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 例如,如果α值为0,则红色值为0.如果<code> inData </code>不是<code> transferType </code>类型的原始数组,则<code> ClassCastExcepti
     * on </code>被抛出。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 如果<code> inData </code>不足以容纳此<code> ColorModel </code>的像素值,则会抛出<code> ArrayIndexOutOfBoundsException 
     * </code>。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 由于<code> DirectColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么如果它们使用不支持的<code> transferType </code>,它
     * 们会抛出异常。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 如果<code> TransferType </code>不受此<code> ColorModel </code>支持,则会抛出<code> UnsupportedOperationException 
     * </code>。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 
     * 
     * @param inData the array containing the pixel value
     * @return the value of the red component of the specified pixel.
     * @throws ArrayIndexOutOfBoundsException if <code>inData</code> is not
     *         large enough to hold a pixel value for this color model
     * @throws ClassCastException if <code>inData</code> is not a
     *         primitive array of type <code>transferType</code>
     * @throws UnsupportedOperationException if this <code>transferType</code>
     *         is not supported by this color model
     */
    public int getRed(Object inData) {
        int pixel=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        return getRed(pixel);
    }


    /**
     * Returns the green color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is specified
     * by an array of data elements of type <code>transferType</code> passed
     * in as an object reference.
     * The returned value is a non pre-multiplied value.  Thus, if the
     * alpha is premultiplied, this method divides it out before returning
     * the value.  If the alpha value is 0, for example, the green value
     * is 0.  If <code>inData</code> is not a primitive array of type
     * <code>transferType</code>, a <code>ClassCastException</code> is thrown.
     *  An <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>inData</code> is not large enough to hold a pixel
     * value for this <code>ColorModel</code>.  Since
     * <code>DirectColorModel</code> can be subclassed, subclasses inherit
     * the implementation of this method and if they don't override it
     * then they throw an exception if they use an unsupported
     * <code>transferType</code>.
     * An <code>UnsupportedOperationException</code> is
     * thrown if this <code>transferType</code> is not supported by this
     * <code>ColorModel</code>.
     * <p>
     * 返回指定像素的绿色分量,在默认的RGB <code> ColorSpace </code>,sRGB中从0到255。如果需要,进行颜色转换。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 例如,如果α值为0,则绿色值为0.如果<code> inData </code>不是类型<code> transferType </code>的原始数组,则<code> ClassCastExcepti
     * on </code>被抛出。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 如果<code> inData </code>不足以容纳此<code> ColorModel </code>的像素值,则会抛出<code> ArrayIndexOutOfBoundsException 
     * </code>。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 由于<code> DirectColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么如果它们使用不支持的<code> transferType </code>,它
     * 们会抛出异常。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 如果<code> TransferType </code>不受此<code> ColorModel </code>支持,则会抛出<code> UnsupportedOperationException 
     * </code>。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 
     * 
     * @param inData the array containing the pixel value
     * @return the value of the green component of the specified pixel.
     * @throws ArrayIndexOutOfBoundsException if <code>inData</code> is not
     *         large enough to hold a pixel value for this color model
     * @throws ClassCastException if <code>inData</code> is not a
     *         primitive array of type <code>transferType</code>
     * @throws UnsupportedOperationException if this <code>transferType</code>
     *         is not supported by this color model
     */
    public int getGreen(Object inData) {
        int pixel=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        return getGreen(pixel);
    }


    /**
     * Returns the blue color component for the specified pixel, scaled
     * from 0 to 255 in the default RGB <code>ColorSpace</code>, sRGB.  A
     * color conversion is done if necessary.  The pixel value is specified
     * by an array of data elements of type <code>transferType</code> passed
     * in as an object reference.
     * The returned value is a non pre-multiplied value.  Thus, if the
     * alpha is premultiplied, this method divides it out before returning
     * the value.  If the alpha value is 0, for example, the blue value
     * is 0.  If <code>inData</code> is not a primitive array of type
     * <code>transferType</code>, a <code>ClassCastException</code> is thrown.
     *  An <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>inData</code> is not large enough to hold a pixel
     * value for this <code>ColorModel</code>.  Since
     * <code>DirectColorModel</code> can be subclassed, subclasses inherit
     * the implementation of this method and if they don't override it
     * then they throw an exception if they use an unsupported
     * <code>transferType</code>.
     * An <code>UnsupportedOperationException</code> is
     * thrown if this <code>transferType</code> is not supported by this
     * <code>ColorModel</code>.
     * <p>
     * 返回指定像素的蓝色分量,在默认的RGB <code> ColorSpace </code>,sRGB中从0到255。如果需要,进行颜色转换。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 例如,如果<code> inData </code>不是类型<code> transferType </code>的原始数组,则<code> ClassCastException </code>被抛出。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 如果<code> inData </code>不足以容纳此<code> ColorModel </code>的像素值,则会抛出<code> ArrayIndexOutOfBoundsException 
     * </code>。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 由于<code> DirectColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么如果它们使用不支持的<code> transferType </code>,它
     * 们会抛出异常。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 如果<code> TransferType </code>不受此<code> ColorModel </code>支持,则会抛出<code> UnsupportedOperationException 
     * </code>。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。返回的值是非预乘的值。因此,如果alpha被预乘,该方法在返回值之前将其分割。
     * 
     * 
     * @param inData the array containing the pixel value
     * @return the value of the blue component of the specified pixel.
     * @throws ArrayIndexOutOfBoundsException if <code>inData</code> is not
     *         large enough to hold a pixel value for this color model
     * @throws ClassCastException if <code>inData</code> is not a
     *         primitive array of type <code>transferType</code>
     * @throws UnsupportedOperationException if this <code>transferType</code>
     *         is not supported by this color model
     */
    public int getBlue(Object inData) {
        int pixel=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        return getBlue(pixel);
    }

    /**
     * Returns the alpha component for the specified pixel, scaled
     * from 0 to 255.  The pixel value is specified by an array of data
     * elements of type <code>transferType</code> passed in as an object
     * reference.
     * If <code>inData</code> is not a primitive array of type
     * <code>transferType</code>, a <code>ClassCastException</code> is
     * thrown.  An <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>inData</code> is not large enough to hold a pixel
     * value for this <code>ColorModel</code>.  Since
     * <code>DirectColorModel</code> can be subclassed, subclasses inherit
     * the implementation of this method and if they don't override it
     * then they throw an exception if they use an unsupported
     * <code>transferType</code>.
     * If this <code>transferType</code> is not supported, an
     * <code>UnsupportedOperationException</code> is thrown.
     * <p>
     * 返回指定像素的alpha分量,从0到255缩放。像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。
     * 如果<code> inData </code>不是类型<code> transferType </code>的原始数组,则会抛出<code> ClassCastException </code>。
     * 如果<code> inData </code>不足以容纳此<code> ColorModel </code>的像素值,则会抛出<code> ArrayIndexOutOfBoundsException 
     * </code>。
     * 如果<code> inData </code>不是类型<code> transferType </code>的原始数组,则会抛出<code> ClassCastException </code>。
     * 由于<code> DirectColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么如果它们使用不支持的<code> transferType </code>,它
     * 们会抛出异常。
     * 如果<code> inData </code>不是类型<code> transferType </code>的原始数组,则会抛出<code> ClassCastException </code>。
     * 如果不支持此<code> transferType </code>,则会抛出<code> UnsupportedOperationException </code>。
     * 
     * 
     * @param inData the specified pixel
     * @return the alpha component of the specified pixel, scaled from
     *         0 to 255.
     * @exception ClassCastException if <code>inData</code>
     *  is not a primitive array of type <code>transferType</code>
     * @exception ArrayIndexOutOfBoundsException if
     *  <code>inData</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code>
     * @exception UnsupportedOperationException if this
     *  <code>tranferType</code> is not supported by this
     *  <code>ColorModel</code>
     */
    public int getAlpha(Object inData) {
        int pixel=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        return getAlpha(pixel);
    }

    /**
     * Returns the color/alpha components for the specified pixel in the
     * default RGB color model format.  A color conversion is done if
     * necessary.  The pixel value is specified by an array of data
     * elements of type <code>transferType</code> passed in as an object
     * reference.  If <code>inData</code> is not a primitive array of type
     * <code>transferType</code>, a <code>ClassCastException</code> is
     * thrown.  An <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>inData</code> is not large enough to hold a pixel
     * value for this <code>ColorModel</code>.
     * The returned value is in a non pre-multiplied format.  Thus, if
     * the alpha is premultiplied, this method divides it out of the
     * color components.  If the alpha value is 0, for example, the color
     * values is 0.  Since <code>DirectColorModel</code> can be
     * subclassed, subclasses inherit the implementation of this method
     * and if they don't override it then
     * they throw an exception if they use an unsupported
     * <code>transferType</code>.
     *
     * <p>
     * 返回默认RGB颜色模型格式中指定像素的颜色/ alpha分量。如果需要,进行颜色转换。像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。
     * 如果<code> inData </code>不是类型<code> transferType </code>的原始数组,则会抛出<code> ClassCastException </code>。
     * 如果<code> inData </code>不足以容纳此<code> ColorModel </code>的像素值,则会抛出<code> ArrayIndexOutOfBoundsException 
     * </code>。
     * 如果<code> inData </code>不是类型<code> transferType </code>的原始数组,则会抛出<code> ClassCastException </code>。
     * 返回的值采用非预扩展格式。因此,如果alpha被预乘,该方法将其从颜色分量中分离出来。
     * 例如,如果alpha值为0,颜色值为0.由于<code> DirectColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么它们抛出异常if他们使用不支持的<code>
     *  transferType </code>。
     * 返回的值采用非预扩展格式。因此,如果alpha被预乘,该方法将其从颜色分量中分离出来。
     * 
     * 
     * @param inData the specified pixel
     * @return the color and alpha components of the specified pixel.
     * @exception UnsupportedOperationException if this
     *            <code>transferType</code> is not supported by this
     *            <code>ColorModel</code>
     * @see ColorModel#getRGBdefault
     */
    public int getRGB(Object inData) {
        int pixel=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])inData;
               pixel = bdata[0] & 0xff;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])inData;
               pixel = sdata[0] & 0xffff;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])inData;
               pixel = idata[0];
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        return getRGB(pixel);
    }

    /**
     * Returns a data element array representation of a pixel in this
     * <code>ColorModel</code>, given an integer pixel representation in the
     * default RGB color model.
     * This array can then be passed to the <code>setDataElements</code>
     * method of a <code>WritableRaster</code> object.  If the pixel variable
     * is <code>null</code>, a new array is allocated.  If <code>pixel</code>
     * is not <code>null</code>, it must be a primitive array of type
     * <code>transferType</code>; otherwise, a
     * <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>pixel</code> is not large enough to hold a pixel
     * value for this <code>ColorModel</code>.  The pixel array is returned.
     * Since <code>DirectColorModel</code> can be subclassed, subclasses
     * inherit the implementation of this method and if they don't
     * override it then they throw an exception if they use an unsupported
     * <code>transferType</code>.
     *
     * <p>
     * 返回此<code> ColorModel </code>中的像素的数据元素数组表示,给定默认RGB颜色模型中的整数像素表示。
     * 然后可以将该数组传递给<code> WritableRaster </code>对象的<code> setDataElements </code>方法。
     * 如果像素变量是<code> null </code>,则会分配一个新的数组。
     * 如果<code> pixel </code>不是<code> null </code>,它必须是<code> transferType </code>类型的原始数组;否则,抛出<code> ClassC
     * astException </code>。
     * 如果像素变量是<code> null </code>,则会分配一个新的数组。
     * 如果<code> pixel </code>不足以容纳此<code> ColorModel </code>的像素值,则会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 如果像素变量是<code> null </code>,则会分配一个新的数组。返回像素阵列。
     * 由于<code> DirectColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么如果它们使用不支持的<code> transferType </code>,它
     * 们会抛出异常。
     * 如果像素变量是<code> null </code>,则会分配一个新的数组。返回像素阵列。
     * 
     * 
     * @param rgb the integer pixel representation in the default RGB
     *            color model
     * @param pixel the specified pixel
     * @return an array representation of the specified pixel in this
     *         <code>ColorModel</code>
     * @exception ClassCastException if <code>pixel</code>
     *  is not a primitive array of type <code>transferType</code>
     * @exception ArrayIndexOutOfBoundsException if
     *  <code>pixel</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code>
     * @exception UnsupportedOperationException if this
     *  <code>transferType</code> is not supported by this
     *  <code>ColorModel</code>
     * @see WritableRaster#setDataElements
     * @see SampleModel#setDataElements
     */
    public Object getDataElements(int rgb, Object pixel) {
        //REMIND: maybe more efficient not to use int array for
        //DataBuffer.TYPE_USHORT and DataBuffer.TYPE_INT
        int intpixel[] = null;
        if (transferType == DataBuffer.TYPE_INT &&
            pixel != null) {
            intpixel = (int[])pixel;
            intpixel[0] = 0;
        } else {
            intpixel = new int[1];
        }

        ColorModel defaultCM = ColorModel.getRGBdefault();
        if (this == defaultCM || equals(defaultCM)) {
            intpixel[0] = rgb;
            return intpixel;
        }

        int red, grn, blu, alp;
        red = (rgb>>16) & 0xff;
        grn = (rgb>>8) & 0xff;
        blu = rgb & 0xff;
        if (is_sRGB || is_LinearRGB) {
            int precision;
            float factor;
            if (is_LinearRGB) {
                if (lRGBprecision == 8) {
                    red = fromsRGB8LUT8[red] & 0xff;
                    grn = fromsRGB8LUT8[grn] & 0xff;
                    blu = fromsRGB8LUT8[blu] & 0xff;
                    precision = 8;
                    factor = 1.0f / 255.0f;
                } else {
                    red = fromsRGB8LUT16[red] & 0xffff;
                    grn = fromsRGB8LUT16[grn] & 0xffff;
                    blu = fromsRGB8LUT16[blu] & 0xffff;
                    precision = 16;
                    factor = 1.0f / 65535.0f;
                }
            } else {
                precision = 8;
                factor = 1.0f / 255.0f;
            }
            if (supportsAlpha) {
                alp = (rgb>>24) & 0xff;
                if (isAlphaPremultiplied) {
                    factor *= (alp * (1.0f / 255.0f));
                    precision = -1;  // force component calculations below
                }
                if (nBits[3] != 8) {
                    alp = (int)
                        ((alp * (1.0f / 255.0f) * ((1<<nBits[3]) - 1)) + 0.5f);
                    if (alp > ((1<<nBits[3]) - 1)) {
                        // fix 4412670 - see comment below
                        alp = (1<<nBits[3]) - 1;
                    }
                }
                intpixel[0] = alp << maskOffsets[3];
            }
            if (nBits[0] != precision) {
                red = (int) ((red * factor * ((1<<nBits[0]) - 1)) + 0.5f);
            }
            if (nBits[1] != precision) {
                grn = (int) ((grn * factor * ((1<<nBits[1]) - 1)) + 0.5f);
            }
            if (nBits[2] != precision) {
                blu = (int) ((blu * factor * ((1<<nBits[2]) - 1)) + 0.5f);
            }
        } else {
            // Need to convert the color
            float[] norm = new float[3];
            float factor = 1.0f / 255.0f;
            norm[0] = red * factor;
            norm[1] = grn * factor;
            norm[2] = blu * factor;
            norm = colorSpace.fromRGB(norm);
            if (supportsAlpha) {
                alp = (rgb>>24) & 0xff;
                if (isAlphaPremultiplied) {
                    factor *= alp;
                    for (int i = 0; i < 3; i++) {
                        norm[i] *= factor;
                    }
                }
                if (nBits[3] != 8) {
                    alp = (int)
                        ((alp * (1.0f / 255.0f) * ((1<<nBits[3]) - 1)) + 0.5f);
                    if (alp > ((1<<nBits[3]) - 1)) {
                        // fix 4412670 - see comment below
                        alp = (1<<nBits[3]) - 1;
                    }
                }
                intpixel[0] = alp << maskOffsets[3];
            }
            red = (int) ((norm[0] * ((1<<nBits[0]) - 1)) + 0.5f);
            grn = (int) ((norm[1] * ((1<<nBits[1]) - 1)) + 0.5f);
            blu = (int) ((norm[2] * ((1<<nBits[2]) - 1)) + 0.5f);
        }

        if (maxBits > 23) {
            // fix 4412670 - for components of 24 or more bits
            // some calculations done above with float precision
            // may lose enough precision that the integer result
            // overflows nBits, so we need to clamp.
            if (red > ((1<<nBits[0]) - 1)) {
                red = (1<<nBits[0]) - 1;
            }
            if (grn > ((1<<nBits[1]) - 1)) {
                grn = (1<<nBits[1]) - 1;
            }
            if (blu > ((1<<nBits[2]) - 1)) {
                blu = (1<<nBits[2]) - 1;
            }
        }

        intpixel[0] |= (red << maskOffsets[0]) |
                       (grn << maskOffsets[1]) |
                       (blu << maskOffsets[2]);

        switch (transferType) {
            case DataBuffer.TYPE_BYTE: {
               byte bdata[];
               if (pixel == null) {
                   bdata = new byte[1];
               } else {
                   bdata = (byte[])pixel;
               }
               bdata[0] = (byte)(0xff&intpixel[0]);
               return bdata;
            }
            case DataBuffer.TYPE_USHORT:{
               short sdata[];
               if (pixel == null) {
                   sdata = new short[1];
               } else {
                   sdata = (short[])pixel;
               }
               sdata[0] = (short)(intpixel[0]&0xffff);
               return sdata;
            }
            case DataBuffer.TYPE_INT:
               return intpixel;
        }
        throw new UnsupportedOperationException("This method has not been "+
                 "implemented for transferType " + transferType);

    }

    /**
     * Returns an array of unnormalized color/alpha components given a pixel
     * in this <code>ColorModel</code>.  The pixel value is specified as an
     * <code>int</code>.  If the <code>components</code> array is
     * <code>null</code>, a new array is allocated.  The
     * <code>components</code> array is returned.  Color/alpha components are
     * stored in the <code>components</code> array starting at
     * <code>offset</code>, even if the array is allocated by this method.
     * An <code>ArrayIndexOutOfBoundsException</code> is thrown if the
     * <code>components</code> array is not <code>null</code> and is not large
     * enough to hold all the color and alpha components, starting at
     * <code>offset</code>.
     * <p>
     * 给定这个<code> ColorModel </code>中的一个像素,返回一个非规格化的颜色/ alpha分量的数组。像素值指定为<code> int </code>。
     * 如果<code> components </code>数组是<code> null </code>,则会分配一个新的数组。返回<code>组件</code>数组。
     * 颜色/ alpha分量存储在<code>组件</code>数组中,从<code> offset </code>开始,即使该数组是由此方法分配的。
     * 如果<code> components </code>数组不是<code> null </code>,并且不足以容纳所有的颜色和alpha组件,则会引发<code> ArrayIndexOutOfBou
     * ndsException </code> > offset </code>。
     * 颜色/ alpha分量存储在<code>组件</code>数组中,从<code> offset </code>开始,即使该数组是由此方法分配的。
     * 
     * 
     * @param pixel the specified pixel
     * @param components the array to receive the color and alpha
     * components of the specified pixel
     * @param offset the offset into the <code>components</code> array at
     * which to start storing the color and alpha components
     * @return an array containing the color and alpha components of the
     * specified pixel starting at the specified offset.
     */
    final public int[] getComponents(int pixel, int[] components, int offset) {
        if (components == null) {
            components = new int[offset+numComponents];
        }

        for (int i=0; i < numComponents; i++) {
            components[offset+i] = (pixel & maskArray[i]) >>> maskOffsets[i];
        }

        return components;
    }

    /**
     * Returns an array of unnormalized color/alpha components given a pixel
     * in this <code>ColorModel</code>.  The pixel value is specified by an
     * array of data elements of type <code>transferType</code> passed in as
     * an object reference.  If <code>pixel</code> is not a primitive array
     * of type <code>transferType</code>, a <code>ClassCastException</code>
     * is thrown.  An <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if <code>pixel</code> is not large enough to hold a
     * pixel value for this <code>ColorModel</code>.  If the
     * <code>components</code> array is <code>null</code>, a new
     * array is allocated.  The <code>components</code> array is returned.
     * Color/alpha components are stored in the <code>components</code> array
     * starting at <code>offset</code>, even if the array is allocated by
     * this method.  An <code>ArrayIndexOutOfBoundsException</code>
     * is thrown if the <code>components</code> array is not
     * <code>null</code> and is not large enough to hold all the color and
     * alpha components, starting at <code>offset</code>.
     * Since <code>DirectColorModel</code> can be subclassed, subclasses
     * inherit the implementation of this method and if they don't
     * override it then they throw an exception if they use an unsupported
     * <code>transferType</code>.
     * <p>
     * 给定这个<code> ColorModel </code>中的像素,返回一个非规格化的颜色/ alpha分量的数组。
     * 像素值由作为对象引用传递的<code> transferType </code>类型的数据元素数组指定。
     * 如果<code> pixel </code>不是类型<code> transferType </code>的原始数组,则会抛出<code> ClassCastException </code>。
     * 如果<code> pixel </code>不足以容纳此<code> ColorModel </code>的像素值,则会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 如果<code> pixel </code>不是类型<code> transferType </code>的原始数组,则会抛出<code> ClassCastException </code>。
     * 如果<code> components </code>数组是<code> null </code>,则会分配一个新数组。返回<code>组件</code>数组。
     * 颜色/ alpha分量存储在<code>组件</code>数组中,从<code> offset </code>开始,即使该数组是由此方法分配的。
     * 如果<code> components </code>数组不是<code> null </code>,并且不足以容纳所有的颜色和alpha组件,则会引发<code> ArrayIndexOutOfBou
     * ndsException </code> > offset </code>。
     * 颜色/ alpha分量存储在<code>组件</code>数组中,从<code> offset </code>开始,即使该数组是由此方法分配的。
     * 由于<code> DirectColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么如果它们使用不支持的<code> transferType </code>,它
     * 们会抛出异常。
     * 颜色/ alpha分量存储在<code>组件</code>数组中,从<code> offset </code>开始,即使该数组是由此方法分配的。
     * 
     * 
     * @param pixel the specified pixel
     * @param components the array to receive the color and alpha
     *        components of the specified pixel
     * @param offset the offset into the <code>components</code> array at
     *        which to start storing the color and alpha components
     * @return an array containing the color and alpha components of the
     * specified pixel starting at the specified offset.
     * @exception ClassCastException if <code>pixel</code>
     *  is not a primitive array of type <code>transferType</code>
     * @exception ArrayIndexOutOfBoundsException if
     *  <code>pixel</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code>, or if <code>components</code>
     *  is not <code>null</code> and is not large enough to hold all the
     *  color and alpha components, starting at <code>offset</code>
     * @exception UnsupportedOperationException if this
     *            <code>transferType</code> is not supported by this
     *            color model
     */
    final public int[] getComponents(Object pixel, int[] components,
                                     int offset) {
        int intpixel=0;
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               byte bdata[] = (byte[])pixel;
               intpixel = bdata[0] & 0xff;
            break;
            case DataBuffer.TYPE_USHORT:
               short sdata[] = (short[])pixel;
               intpixel = sdata[0] & 0xffff;
            break;
            case DataBuffer.TYPE_INT:
               int idata[] = (int[])pixel;
               intpixel = idata[0];
            break;
            default:
               throw new UnsupportedOperationException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
        return getComponents(intpixel, components, offset);
    }

    /**
     * Creates a <code>WritableRaster</code> with the specified width and
     * height that has a data layout (<code>SampleModel</code>) compatible
     * with this <code>ColorModel</code>.
     * <p>
     *  创建具有指定宽度和高度的<code> WritableRaster </code>,其具有与此<code> ColorModel </code>兼容的数据布局(<code> SampleModel </code>
     * )。
     * 
     * 
     * @param w the width to apply to the new <code>WritableRaster</code>
     * @param h the height to apply to the new <code>WritableRaster</code>
     * @return a <code>WritableRaster</code> object with the specified
     * width and height.
     * @throws IllegalArgumentException if <code>w</code> or <code>h</code>
     *         is less than or equal to zero
     * @see WritableRaster
     * @see SampleModel
     */
    final public WritableRaster createCompatibleWritableRaster (int w,
                                                                int h) {
        if ((w <= 0) || (h <= 0)) {
            throw new IllegalArgumentException("Width (" + w + ") and height (" + h +
                                               ") cannot be <= 0");
        }
        int[] bandmasks;
        if (supportsAlpha) {
            bandmasks = new int[4];
            bandmasks[3] = alpha_mask;
        }
        else {
            bandmasks = new int[3];
        }
        bandmasks[0] = red_mask;
        bandmasks[1] = green_mask;
        bandmasks[2] = blue_mask;

        if (pixel_bits > 16) {
            return Raster.createPackedRaster(DataBuffer.TYPE_INT,
                                             w,h,bandmasks,null);
        }
        else if (pixel_bits > 8) {
            return Raster.createPackedRaster(DataBuffer.TYPE_USHORT,
                                             w,h,bandmasks,null);
        }
        else {
            return Raster.createPackedRaster(DataBuffer.TYPE_BYTE,
                                             w,h,bandmasks,null);
        }
    }

    /**
     * Returns a pixel value represented as an <code>int</code> in this
     * <code>ColorModel</code>, given an array of unnormalized color/alpha
     * components.   An <code>ArrayIndexOutOfBoundsException</code> is
     * thrown if the <code>components</code> array is
     * not large enough to hold all the color and alpha components, starting
     * at <code>offset</code>.
     * <p>
     * 给定一个非规格化的颜色/ alpha分量数组,返回这个<code> ColorModel </code>中表示为<code> int </code>的像素值。
     * 如果<code> components </code>数组不够大,不能容纳所有的颜色和alpha组件,则从<code> offset </code>开始,会抛出<code> ArrayIndexOutO
     * fBoundsException </code>。
     * 给定一个非规格化的颜色/ alpha分量数组,返回这个<code> ColorModel </code>中表示为<code> int </code>的像素值。
     * 
     * 
     * @param components an array of unnormalized color and alpha
     * components
     * @param offset the index into <code>components</code> at which to
     * begin retrieving the color and alpha components
     * @return an <code>int</code> pixel value in this
     * <code>ColorModel</code> corresponding to the specified components.
     * @exception ArrayIndexOutOfBoundsException if
     *  the <code>components</code> array is not large enough to
     *  hold all of the color and alpha components starting at
     *  <code>offset</code>
     */
    public int getDataElement(int[] components, int offset) {
        int pixel = 0;
        for (int i=0; i < numComponents; i++) {
            pixel |= ((components[offset+i]<<maskOffsets[i])&maskArray[i]);
        }
        return pixel;
    }

    /**
     * Returns a data element array representation of a pixel in this
     * <code>ColorModel</code>, given an array of unnormalized color/alpha
     * components.
     * This array can then be passed to the <code>setDataElements</code>
     * method of a <code>WritableRaster</code> object.
     * An <code>ArrayIndexOutOfBoundsException</code> is thrown if the
     * <code>components</code> array
     * is not large enough to hold all the color and alpha components,
     * starting at offset.  If the <code>obj</code> variable is
     * <code>null</code>, a new array is allocated.  If <code>obj</code> is
     * not <code>null</code>, it must be a primitive array
     * of type <code>transferType</code>; otherwise, a
     * <code>ClassCastException</code> is thrown.
     * An <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * <code>obj</code> is not large enough to hold a pixel value for this
     * <code>ColorModel</code>.
     * Since <code>DirectColorModel</code> can be subclassed, subclasses
     * inherit the implementation of this method and if they don't
     * override it then they throw an exception if they use an unsupported
     * <code>transferType</code>.
     * <p>
     *  给定一个非规格化的颜色/ alpha分量数组,返回这个<code> ColorModel </code>中像素的数据元素数组表示。
     * 然后可以将该数组传递给<code> WritableRaster </code>对象的<code> setDataElements </code>方法。
     * 如果<code> components </code>数组不足以容纳所有的颜色和alpha组件(从offset开始),则抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 然后可以将该数组传递给<code> WritableRaster </code>对象的<code> setDataElements </code>方法。
     * 如果<code> obj </code>变量是<code> null </code>,则会分配一个新数组。
     * 如果<code> obj </code>不是<code> null </code>,它必须是<code> transferType </code>类型的原始数组;否则,抛出<code> ClassCas
     * tException </code>。
     * 如果<code> obj </code>变量是<code> null </code>,则会分配一个新数组。
     * 如果<code> obj </code>不足以容纳此<code> ColorModel </code>的像素值,则会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 如果<code> obj </code>变量是<code> null </code>,则会分配一个新数组。
     * 由于<code> DirectColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么如果它们使用不支持的<code> transferType </code>,它
     * 们会抛出异常。
     * 如果<code> obj </code>变量是<code> null </code>,则会分配一个新数组。
     * 
     * @param components an array of unnormalized color and alpha
     * components
     * @param offset the index into <code>components</code> at which to
     * begin retrieving color and alpha components
     * @param obj the <code>Object</code> representing an array of color
     * and alpha components
     * @return an <code>Object</code> representing an array of color and
     * alpha components.
     * @exception ClassCastException if <code>obj</code>
     *  is not a primitive array of type <code>transferType</code>
     * @exception ArrayIndexOutOfBoundsException if
     *  <code>obj</code> is not large enough to hold a pixel value
     *  for this <code>ColorModel</code> or the <code>components</code>
     *  array is not large enough to hold all of the color and alpha
     *  components starting at <code>offset</code>
     * @exception UnsupportedOperationException if this
     *            <code>transferType</code> is not supported by this
     *            color model
     * @see WritableRaster#setDataElements
     * @see SampleModel#setDataElements
     */
    public Object getDataElements(int[] components, int offset, Object obj) {
        int pixel = 0;
        for (int i=0; i < numComponents; i++) {
            pixel |= ((components[offset+i]<<maskOffsets[i])&maskArray[i]);
        }
        switch (transferType) {
            case DataBuffer.TYPE_BYTE:
               if (obj instanceof byte[]) {
                   byte bdata[] = (byte[])obj;
                   bdata[0] = (byte)(pixel&0xff);
                   return bdata;
               } else {
                   byte bdata[] = {(byte)(pixel&0xff)};
                   return bdata;
               }
            case DataBuffer.TYPE_USHORT:
               if (obj instanceof short[]) {
                   short sdata[] = (short[])obj;
                   sdata[0] = (short)(pixel&0xffff);
                   return sdata;
               } else {
                   short sdata[] = {(short)(pixel&0xffff)};
                   return sdata;
               }
            case DataBuffer.TYPE_INT:
               if (obj instanceof int[]) {
                   int idata[] = (int[])obj;
                   idata[0] = pixel;
                   return idata;
               } else {
                   int idata[] = {pixel};
                   return idata;
               }
            default:
               throw new ClassCastException("This method has not been "+
                   "implemented for transferType " + transferType);
        }
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
     * <code>UnsupportedOperationException</code> if this transferType is
     * not supported by this <code>ColorModel</code>.  Since
     * <code>ColorModel</code> can be subclassed, subclasses inherit the
     * implementation of this method and if they don't override it then
     * they throw an exception if they use an unsupported transferType.
     *
     * <p>
     * 
     * 
     * @param raster the <code>WritableRaster</code> data
     * @param isAlphaPremultiplied <code>true</code> if the alpha is
     * premultiplied; <code>false</code> otherwise
     * @return a <code>ColorModel</code> object that represents the
     * coerced data.
     * @exception UnsupportedOperationException if this
     *            <code>transferType</code> is not supported by this
     *            color model
     */
    final public ColorModel coerceData (WritableRaster raster,
                                        boolean isAlphaPremultiplied)
    {
        if (!supportsAlpha ||
            this.isAlphaPremultiplied() == isAlphaPremultiplied) {
            return this;
        }

        int w = raster.getWidth();
        int h = raster.getHeight();
        int aIdx = numColorComponents;
        float normAlpha;
        float alphaScale = 1.0f / ((float) ((1 << nBits[aIdx]) - 1));

        int rminX = raster.getMinX();
        int rY = raster.getMinY();
        int rX;
        int pixel[] = null;
        int zpixel[] = null;

        if (isAlphaPremultiplied) {
            // Must mean that we are currently not premultiplied so
            // multiply by alpha
            switch (transferType) {
                case DataBuffer.TYPE_BYTE: {
                    for (int y = 0; y < h; y++, rY++) {
                        rX = rminX;
                        for (int x = 0; x < w; x++, rX++) {
                            pixel = raster.getPixel(rX, rY, pixel);
                            normAlpha = pixel[aIdx] * alphaScale;
                            if (normAlpha != 0.f) {
                                for (int c=0; c < aIdx; c++) {
                                    pixel[c] = (int) (pixel[c] * normAlpha +
                                                      0.5f);
                                }
                                raster.setPixel(rX, rY, pixel);
                            } else {
                                if (zpixel == null) {
                                    zpixel = new int[numComponents];
                                    java.util.Arrays.fill(zpixel, 0);
                                }
                                raster.setPixel(rX, rY, zpixel);
                            }
                        }
                    }
                }
                break;
                case DataBuffer.TYPE_USHORT: {
                    for (int y = 0; y < h; y++, rY++) {
                        rX = rminX;
                        for (int x = 0; x < w; x++, rX++) {
                            pixel = raster.getPixel(rX, rY, pixel);
                            normAlpha = pixel[aIdx] * alphaScale;
                            if (normAlpha != 0.f) {
                                for (int c=0; c < aIdx; c++) {
                                    pixel[c] = (int) (pixel[c] * normAlpha +
                                                      0.5f);
                                }
                                raster.setPixel(rX, rY, pixel);
                            } else {
                                if (zpixel == null) {
                                    zpixel = new int[numComponents];
                                    java.util.Arrays.fill(zpixel, 0);
                                }
                                raster.setPixel(rX, rY, zpixel);
                            }
                        }
                    }
                }
                break;
                case DataBuffer.TYPE_INT: {
                    for (int y = 0; y < h; y++, rY++) {
                        rX = rminX;
                        for (int x = 0; x < w; x++, rX++) {
                            pixel = raster.getPixel(rX, rY, pixel);
                            normAlpha = pixel[aIdx] * alphaScale;
                            if (normAlpha != 0.f) {
                                for (int c=0; c < aIdx; c++) {
                                    pixel[c] = (int) (pixel[c] * normAlpha +
                                                      0.5f);
                                }
                                raster.setPixel(rX, rY, pixel);
                            } else {
                                if (zpixel == null) {
                                    zpixel = new int[numComponents];
                                    java.util.Arrays.fill(zpixel, 0);
                                }
                                raster.setPixel(rX, rY, zpixel);
                            }
                        }
                    }
                }
                break;
                default:
                    throw new UnsupportedOperationException("This method has not been "+
                         "implemented for transferType " + transferType);
            }
        }
        else {
            // We are premultiplied and want to divide it out
            switch (transferType) {
                case DataBuffer.TYPE_BYTE: {
                    for (int y = 0; y < h; y++, rY++) {
                        rX = rminX;
                        for (int x = 0; x < w; x++, rX++) {
                            pixel = raster.getPixel(rX, rY, pixel);
                            normAlpha = pixel[aIdx] * alphaScale;
                            if (normAlpha != 0.0f) {
                                float invAlpha = 1.0f / normAlpha;
                                for (int c=0; c < aIdx; c++) {
                                    pixel[c] = (int) (pixel[c] * invAlpha +
                                                      0.5f);
                                }
                                raster.setPixel(rX, rY, pixel);
                            }
                        }
                    }
                }
                break;
                case DataBuffer.TYPE_USHORT: {
                    for (int y = 0; y < h; y++, rY++) {
                        rX = rminX;
                        for (int x = 0; x < w; x++, rX++) {
                            pixel = raster.getPixel(rX, rY, pixel);
                            normAlpha = pixel[aIdx] * alphaScale;
                            if (normAlpha != 0) {
                                float invAlpha = 1.0f / normAlpha;
                                for (int c=0; c < aIdx; c++) {
                                    pixel[c] = (int) (pixel[c] * invAlpha +
                                                      0.5f);
                                }
                                raster.setPixel(rX, rY, pixel);
                            }
                        }
                    }
                }
                break;
                case DataBuffer.TYPE_INT: {
                    for (int y = 0; y < h; y++, rY++) {
                        rX = rminX;
                        for (int x = 0; x < w; x++, rX++) {
                            pixel = raster.getPixel(rX, rY, pixel);
                            normAlpha = pixel[aIdx] * alphaScale;
                            if (normAlpha != 0) {
                                float invAlpha = 1.0f / normAlpha;
                                for (int c=0; c < aIdx; c++) {
                                    pixel[c] = (int) (pixel[c] * invAlpha +
                                                      0.5f);
                                }
                                raster.setPixel(rX, rY, pixel);
                            }
                        }
                    }
                }
                break;
                default:
                    throw new UnsupportedOperationException("This method has not been "+
                         "implemented for transferType " + transferType);
            }
        }

        // Return a new color model
        return new DirectColorModel(colorSpace, pixel_bits, maskArray[0],
                                    maskArray[1], maskArray[2], maskArray[3],
                                    isAlphaPremultiplied,
                                    transferType);

    }

    /**
      * Returns <code>true</code> if <code>raster</code> is compatible
      * with this <code>ColorModel</code> and <code>false</code> if it is
      * not.
      * <p>
      * 假设该数据当前由<code> ColorModel </code>正确描述,强制栅格数据与<code> isAlphaPremultiplied </code>变量中指定的状态匹配。
      * 它可以将颜色栅格数据乘以或除以阿尔法,或者如果数据处于正确状态,则什么也不做。
      * 如果数据需要被强制,这个方法也将返回这个<code> ColorModel </code>的实例,其中<code> isAlphaPremultiplied </code>标志被适当设置。
      * 如果此<code> ColorModel </code>不支持此transferType,此方法将抛出<code> UnsupportedOperationException </code>。
      * 由于<code> ColorModel </code>可以被子类化,子类继承此方法的实现,如果它们不覆盖它,那么如果它们使用不支持的transferType,它们会抛出异常。
      * 
      * 
      * @param raster the {@link Raster} object to test for compatibility
      * @return <code>true</code> if <code>raster</code> is compatible
      * with this <code>ColorModel</code>; <code>false</code> otherwise.
      */
    public boolean isCompatibleRaster(Raster raster) {
        SampleModel sm = raster.getSampleModel();
        SinglePixelPackedSampleModel spsm;
        if (sm instanceof SinglePixelPackedSampleModel) {
            spsm = (SinglePixelPackedSampleModel) sm;
        }
        else {
            return false;
        }
        if (spsm.getNumBands() != getNumComponents()) {
            return false;
        }

        int[] bitMasks = spsm.getBitMasks();
        for (int i=0; i<numComponents; i++) {
            if (bitMasks[i] != maskArray[i]) {
                return false;
            }
        }

        return (raster.getTransferType() == transferType);
    }

    private void setFields() {
        // Set the private fields
        // REMIND: Get rid of these from the native code
        red_mask     = maskArray[0];
        red_offset   = maskOffsets[0];
        green_mask   = maskArray[1];
        green_offset = maskOffsets[1];
        blue_mask    = maskArray[2];
        blue_offset  = maskOffsets[2];
        if (nBits[0] < 8) {
            red_scale = (1 << nBits[0]) - 1;
        }
        if (nBits[1] < 8) {
            green_scale = (1 << nBits[1]) - 1;
        }
        if (nBits[2] < 8) {
            blue_scale = (1 << nBits[2]) - 1;
        }
        if (supportsAlpha) {
            alpha_mask   = maskArray[3];
            alpha_offset = maskOffsets[3];
            if (nBits[3] < 8) {
                alpha_scale = (1 << nBits[3]) - 1;
            }
        }
    }

    /**
     * Returns a <code>String</code> that represents this
     * <code>DirectColorModel</code>.
     * <p>
     * 
     * @return a <code>String</code> representing this
     * <code>DirectColorModel</code>.
     */
    public String toString() {
        return new String("DirectColorModel: rmask="
                          +Integer.toHexString(red_mask)+" gmask="
                          +Integer.toHexString(green_mask)+" bmask="
                          +Integer.toHexString(blue_mask)+" amask="
                          +Integer.toHexString(alpha_mask));
    }
}
