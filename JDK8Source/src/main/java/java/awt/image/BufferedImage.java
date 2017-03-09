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

package java.awt.image;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.ImageCapabilities;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import java.util.Vector;

import sun.awt.image.BytePackedRaster;
import sun.awt.image.ShortComponentRaster;
import sun.awt.image.ByteComponentRaster;
import sun.awt.image.IntegerComponentRaster;
import sun.awt.image.OffScreenImageSource;

/**
 *
 * The <code>BufferedImage</code> subclass describes an {@link
 * java.awt.Image Image} with an accessible buffer of image data.
 * A <code>BufferedImage</code> is comprised of a {@link ColorModel} and a
 * {@link Raster} of image data.
 * The number and types of bands in the {@link SampleModel} of the
 * <code>Raster</code> must match the number and types required by the
 * <code>ColorModel</code> to represent its color and alpha components.
 * All <code>BufferedImage</code> objects have an upper left corner
 * coordinate of (0,&nbsp;0).  Any <code>Raster</code> used to construct a
 * <code>BufferedImage</code> must therefore have minX=0 and minY=0.
 *
 * <p>
 * This class relies on the data fetching and setting methods
 * of <code>Raster</code>,
 * and on the color characterization methods of <code>ColorModel</code>.
 *
 * <p>
 *  <code> BufferedImage </code>子类描述了一个具有图像数据的可访问缓冲区的{@link java.awt.Image Image}。
 *  <code> BufferedImage </code>由图像数据的{@link ColorModel}和{@link Raster}组成。
 *  <code> Raster </code>的{@link SampleModel}中的波段数量和类型必须与<code> ColorModel </code>所需的数量和类型相匹配,以表示其颜色和alp
 * ha分量。
 *  <code> BufferedImage </code>由图像数据的{@link ColorModel}和{@link Raster}组成。
 * 所有<code> BufferedImage </code>对象的左上角坐标为(0,&nbsp; 0)。
 * 用于构造<code> BufferedImage </code>的任何<code> Raster </code>必须具有minX = 0和minY = 0。
 * 
 * <p>
 *  该类依赖于<code> Raster </code>的数据提取和设置方法,以及<code> ColorModel </code>的颜色表征方法。
 * 
 * 
 * @see ColorModel
 * @see Raster
 * @see WritableRaster
 */

public class BufferedImage extends java.awt.Image
                           implements WritableRenderedImage, Transparency
{
    int        imageType = TYPE_CUSTOM;
    ColorModel colorModel;
    WritableRaster raster;
    OffScreenImageSource osis;
    Hashtable properties;

    boolean    isAlphaPremultiplied;// If true, alpha has been premultiplied in
    // color channels

    /**
     * Image Type Constants
     * <p>
     *  图像类型常量
     * 
     */

    /**
     * Image type is not recognized so it must be a customized
     * image.  This type is only used as a return value for the getType()
     * method.
     * <p>
     *  无法识别图片类型,因此它必须是自定义图片。此类型仅用作getType()方法的返回值。
     * 
     */
    public static final int TYPE_CUSTOM = 0;

    /**
     * Represents an image with 8-bit RGB color components packed into
     * integer pixels.  The image has a {@link DirectColorModel} without
     * alpha.
     * When data with non-opaque alpha is stored
     * in an image of this type,
     * the color data must be adjusted to a non-premultiplied form
     * and the alpha discarded,
     * as described in the
     * {@link java.awt.AlphaComposite} documentation.
     * <p>
     *  表示具有打包为整数像素的8位RGB颜色分量的图像。图片有一个没有alpha的{@link DirectColorModel}。
     * 当具有非透明alpha的数据存储在此类型的图像中时,必须将颜色数据调整为非预乘形式,并丢弃alpha,如{@link java.awt.AlphaComposite}文档中所述。
     * 
     */
    public static final int TYPE_INT_RGB = 1;

    /**
     * Represents an image with 8-bit RGBA color components packed into
     * integer pixels.  The image has a <code>DirectColorModel</code>
     * with alpha. The color data in this image is considered not to be
     * premultiplied with alpha.  When this type is used as the
     * <code>imageType</code> argument to a <code>BufferedImage</code>
     * constructor, the created image is consistent with images
     * created in the JDK1.1 and earlier releases.
     * <p>
     * 表示具有打包为整数像素的8位RGBA颜色分量的图像。该图像具有带有alpha的<code> DirectColorModel </code>。该图像中的颜色数据被认为不是用α预乘的。
     * 当此类型用作<code> BufferedImage </code>构造函数的<code> imageType </code>参数时,创建的图像与JDK1.1和早期版本中创建的图像一致。
     * 
     */
    public static final int TYPE_INT_ARGB = 2;

    /**
     * Represents an image with 8-bit RGBA color components packed into
     * integer pixels.  The image has a <code>DirectColorModel</code>
     * with alpha.  The color data in this image is considered to be
     * premultiplied with alpha.
     * <p>
     *  表示具有打包为整数像素的8位RGBA颜色分量的图像。该图像具有带有alpha的<code> DirectColorModel </code>。该图像中的颜色数据被认为是用α预乘的。
     * 
     */
    public static final int TYPE_INT_ARGB_PRE = 3;

    /**
     * Represents an image with 8-bit RGB color components, corresponding
     * to a Windows- or Solaris- style BGR color model, with the colors
     * Blue, Green, and Red packed into integer pixels.  There is no alpha.
     * The image has a {@link DirectColorModel}.
     * When data with non-opaque alpha is stored
     * in an image of this type,
     * the color data must be adjusted to a non-premultiplied form
     * and the alpha discarded,
     * as described in the
     * {@link java.awt.AlphaComposite} documentation.
     * <p>
     *  表示具有8位RGB颜色分量的图像,对应于Windows或Solaris风格的BGR颜色模型,颜色为蓝色,绿色和红色,打包为整数像素。没有阿尔法。
     * 该图片有一个{@link DirectColorModel}。
     * 当具有非透明alpha的数据存储在此类型的图像中时,必须将颜色数据调整为非预乘形式,并丢弃alpha,如{@link java.awt.AlphaComposite}文档中所述。
     * 
     */
    public static final int TYPE_INT_BGR = 4;

    /**
     * Represents an image with 8-bit RGB color components, corresponding
     * to a Windows-style BGR color model) with the colors Blue, Green,
     * and Red stored in 3 bytes.  There is no alpha.  The image has a
     * <code>ComponentColorModel</code>.
     * When data with non-opaque alpha is stored
     * in an image of this type,
     * the color data must be adjusted to a non-premultiplied form
     * and the alpha discarded,
     * as described in the
     * {@link java.awt.AlphaComposite} documentation.
     * <p>
     * 表示具有8位RGB颜色分量的图像,对应于Windows样式的BGR颜色模型),具有以3字节存储的颜色Blue,Green和Red。没有阿尔法。
     * 该图像具有<code> ComponentColorModel </code>。
     * 当具有非透明alpha的数据存储在此类型的图像中时,必须将颜色数据调整为非预乘形式,并丢弃alpha,如{@link java.awt.AlphaComposite}文档中所述。
     * 
     */
    public static final int TYPE_3BYTE_BGR = 5;

    /**
     * Represents an image with 8-bit RGBA color components with the colors
     * Blue, Green, and Red stored in 3 bytes and 1 byte of alpha.  The
     * image has a <code>ComponentColorModel</code> with alpha.  The
     * color data in this image is considered not to be premultiplied with
     * alpha.  The byte data is interleaved in a single
     * byte array in the order A, B, G, R
     * from lower to higher byte addresses within each pixel.
     * <p>
     *  表示具有8位RGBA颜色分量的图像,其颜色为蓝色,绿色和红色,存储在3字节和1字节的alpha中。该图像具有带有alpha的<code> ComponentColorModel </code>。
     * 该图像中的颜色数据被认为不是用α预乘的。字节数据以每个像素内从低到高字节地址的顺序A,B,G,R在单字节阵列中交织。
     * 
     */
    public static final int TYPE_4BYTE_ABGR = 6;

    /**
     * Represents an image with 8-bit RGBA color components with the colors
     * Blue, Green, and Red stored in 3 bytes and 1 byte of alpha.  The
     * image has a <code>ComponentColorModel</code> with alpha. The color
     * data in this image is considered to be premultiplied with alpha.
     * The byte data is interleaved in a single byte array in the order
     * A, B, G, R from lower to higher byte addresses within each pixel.
     * <p>
     *  表示具有8位RGBA颜色分量的图像,其颜色为蓝色,绿色和红色,存储在3字节和1字节的alpha中。该图像具有带有alpha的<code> ComponentColorModel </code>。
     * 该图像中的颜色数据被认为是用α预乘的。字节数据以每个像素内从低到高字节地址的顺序A,B,G,R在单字节阵列中交织。
     * 
     */
    public static final int TYPE_4BYTE_ABGR_PRE = 7;

    /**
     * Represents an image with 5-6-5 RGB color components (5-bits red,
     * 6-bits green, 5-bits blue) with no alpha.  This image has
     * a <code>DirectColorModel</code>.
     * When data with non-opaque alpha is stored
     * in an image of this type,
     * the color data must be adjusted to a non-premultiplied form
     * and the alpha discarded,
     * as described in the
     * {@link java.awt.AlphaComposite} documentation.
     * <p>
     * 表示具有5-6-5个RGB颜色分量(5位红色,6位绿色,5位蓝色)且不带alpha的图像。此图片具有<code> DirectColorModel </code>。
     * 当具有非透明alpha的数据存储在此类型的图像中时,必须将颜色数据调整为非预乘形式,并丢弃alpha,如{@link java.awt.AlphaComposite}文档中所述。
     * 
     */
    public static final int TYPE_USHORT_565_RGB = 8;

    /**
     * Represents an image with 5-5-5 RGB color components (5-bits red,
     * 5-bits green, 5-bits blue) with no alpha.  This image has
     * a <code>DirectColorModel</code>.
     * When data with non-opaque alpha is stored
     * in an image of this type,
     * the color data must be adjusted to a non-premultiplied form
     * and the alpha discarded,
     * as described in the
     * {@link java.awt.AlphaComposite} documentation.
     * <p>
     *  表示具有5-5-5 RGB颜色分量(5位红色,5位绿色,5位蓝色)且不带alpha的图像。此图片具有<code> DirectColorModel </code>。
     * 当具有非透明alpha的数据存储在此类型的图像中时,必须将颜色数据调整为非预乘形式,并丢弃alpha,如{@link java.awt.AlphaComposite}文档中所述。
     * 
     */
    public static final int TYPE_USHORT_555_RGB = 9;

    /**
     * Represents a unsigned byte grayscale image, non-indexed.  This
     * image has a <code>ComponentColorModel</code> with a CS_GRAY
     * {@link ColorSpace}.
     * When data with non-opaque alpha is stored
     * in an image of this type,
     * the color data must be adjusted to a non-premultiplied form
     * and the alpha discarded,
     * as described in the
     * {@link java.awt.AlphaComposite} documentation.
     * <p>
     *  表示无索引字节灰度图像,非索引。此图片具有带CS_GRAY {@link ColorSpace}的<code> ComponentColorModel </code>。
     * 当具有非透明alpha的数据存储在此类型的图像中时,必须将颜色数据调整为非预乘形式,并丢弃alpha,如{@link java.awt.AlphaComposite}文档中所述。
     * 
     */
    public static final int TYPE_BYTE_GRAY = 10;

    /**
     * Represents an unsigned short grayscale image, non-indexed).  This
     * image has a <code>ComponentColorModel</code> with a CS_GRAY
     * <code>ColorSpace</code>.
     * When data with non-opaque alpha is stored
     * in an image of this type,
     * the color data must be adjusted to a non-premultiplied form
     * and the alpha discarded,
     * as described in the
     * {@link java.awt.AlphaComposite} documentation.
     * <p>
     *  表示无符号的短灰度图像,非索引)。此图像具有带有CS_GRAY <code> ColorSpace </code>的<code> ComponentColorModel </code>。
     * 当具有非透明alpha的数据存储在此类型的图像中时,必须将颜色数据调整为非预乘形式,并丢弃alpha,如{@link java.awt.AlphaComposite}文档中所述。
     * 
     */
    public static final int TYPE_USHORT_GRAY = 11;

    /**
     * Represents an opaque byte-packed 1, 2, or 4 bit image.  The
     * image has an {@link IndexColorModel} without alpha.  When this
     * type is used as the <code>imageType</code> argument to the
     * <code>BufferedImage</code> constructor that takes an
     * <code>imageType</code> argument but no <code>ColorModel</code>
     * argument, a 1-bit image is created with an
     * <code>IndexColorModel</code> with two colors in the default
     * sRGB <code>ColorSpace</code>: {0,&nbsp;0,&nbsp;0} and
     * {255,&nbsp;255,&nbsp;255}.
     *
     * <p> Images with 2 or 4 bits per pixel may be constructed via
     * the <code>BufferedImage</code> constructor that takes a
     * <code>ColorModel</code> argument by supplying a
     * <code>ColorModel</code> with an appropriate map size.
     *
     * <p> Images with 8 bits per pixel should use the image types
     * <code>TYPE_BYTE_INDEXED</code> or <code>TYPE_BYTE_GRAY</code>
     * depending on their <code>ColorModel</code>.

     * <p> When color data is stored in an image of this type,
     * the closest color in the colormap is determined
     * by the <code>IndexColorModel</code> and the resulting index is stored.
     * Approximation and loss of alpha or color components
     * can result, depending on the colors in the
     * <code>IndexColorModel</code> colormap.
     * <p>
     * 表示不透明的字节包装的1,2或4位图像。该图像有一个{@link IndexColorModel}无alpha。
     * 当此类型用作<code> BufferedImage </code>构造函数的<code> imageType </code>参数时,它采用<code> imageType </code>参数,但不使用
     * <code> ColorModel </code> ,在默认sRGB <code> ColorSpace </code>：{0,&nbsp; 0,&nbsp; 0}和{255,&nbsp;}中创建具有两
     * 种颜色的<code> IndexColorModel </code> ; 255,&nbsp; 255}。
     * 表示不透明的字节包装的1,2或4位图像。该图像有一个{@link IndexColorModel}无alpha。
     * 
     *  <p>每像素2或4位的图像可以通过<code> BufferedImage </code>构造函数构造,该构造函数通过提供具有<code> ColorModel </code>适当的地图大小。
     * 
     *  <p>每像素8位图片的图片应根据<code> ColorModel </code>使用图片类型<code> TYPE_BYTE_INDEXED </code>或<code> TYPE_BYTE_GRA
     * Y </code>。
     * 
     *  <p>当颜色数据存储在此类型的图像中时,颜色映射中最接近的颜色由<code> IndexColorModel </code>确定,并存储生成的索引。
     * 根据<code> IndexColorModel </code>颜色映射中的颜色,可能会导致alpha或颜色分量的近似和丢失。
     * 
     */
    public static final int TYPE_BYTE_BINARY = 12;

    /**
     * Represents an indexed byte image.  When this type is used as the
     * <code>imageType</code> argument to the <code>BufferedImage</code>
     * constructor that takes an <code>imageType</code> argument
     * but no <code>ColorModel</code> argument, an
     * <code>IndexColorModel</code> is created with
     * a 256-color 6/6/6 color cube palette with the rest of the colors
     * from 216-255 populated by grayscale values in the
     * default sRGB ColorSpace.
     *
     * <p> When color data is stored in an image of this type,
     * the closest color in the colormap is determined
     * by the <code>IndexColorModel</code> and the resulting index is stored.
     * Approximation and loss of alpha or color components
     * can result, depending on the colors in the
     * <code>IndexColorModel</code> colormap.
     * <p>
     * 表示索引字节图像。
     * 当此类型用作<code> BufferedImage </code>构造函数的<code> imageType </code>参数时,它采用<code> imageType </code>参数,但不使用
     * <code> ColorModel </code> ,使用256色的6/6/6彩色立方体调色板创建<code> IndexColorModel </code>,其中从默认sRGB ColorSpace中
     * 的灰度值填充216-255的其余颜色。
     * 表示索引字节图像。
     * 
     *  <p>当颜色数据存储在此类型的图像中时,颜色映射中最接近的颜色由<code> IndexColorModel </code>确定,并存储生成的索引。
     * 根据<code> IndexColorModel </code>颜色映射中的颜色,可能会导致alpha或颜色分量的近似和丢失。
     * 
     */
    public static final int TYPE_BYTE_INDEXED = 13;

    private static final int DCM_RED_MASK   = 0x00ff0000;
    private static final int DCM_GREEN_MASK = 0x0000ff00;
    private static final int DCM_BLUE_MASK  = 0x000000ff;
    private static final int DCM_ALPHA_MASK = 0xff000000;
    private static final int DCM_565_RED_MASK = 0xf800;
    private static final int DCM_565_GRN_MASK = 0x07E0;
    private static final int DCM_565_BLU_MASK = 0x001F;
    private static final int DCM_555_RED_MASK = 0x7C00;
    private static final int DCM_555_GRN_MASK = 0x03E0;
    private static final int DCM_555_BLU_MASK = 0x001F;
    private static final int DCM_BGR_RED_MASK = 0x0000ff;
    private static final int DCM_BGR_GRN_MASK = 0x00ff00;
    private static final int DCM_BGR_BLU_MASK = 0xff0000;


    static private native void initIDs();
    static {
        ColorModel.loadLibraries();
        initIDs();
    }

    /**
     * Constructs a <code>BufferedImage</code> of one of the predefined
     * image types.  The <code>ColorSpace</code> for the image is the
     * default sRGB space.
     * <p>
     *  构造一个预定义图像类型的<code> BufferedImage </code>。图像的<code> ColorSpace </code>是默认的sRGB空间。
     * 
     * 
     * @param width     width of the created image
     * @param height    height of the created image
     * @param imageType type of the created image
     * @see ColorSpace
     * @see #TYPE_INT_RGB
     * @see #TYPE_INT_ARGB
     * @see #TYPE_INT_ARGB_PRE
     * @see #TYPE_INT_BGR
     * @see #TYPE_3BYTE_BGR
     * @see #TYPE_4BYTE_ABGR
     * @see #TYPE_4BYTE_ABGR_PRE
     * @see #TYPE_BYTE_GRAY
     * @see #TYPE_USHORT_GRAY
     * @see #TYPE_BYTE_BINARY
     * @see #TYPE_BYTE_INDEXED
     * @see #TYPE_USHORT_565_RGB
     * @see #TYPE_USHORT_555_RGB
     */
    public BufferedImage(int width,
                         int height,
                         int imageType) {
        switch (imageType) {
        case TYPE_INT_RGB:
            {
                colorModel = new DirectColorModel(24,
                                                  0x00ff0000,   // Red
                                                  0x0000ff00,   // Green
                                                  0x000000ff,   // Blue
                                                  0x0           // Alpha
                                                  );
                  raster = colorModel.createCompatibleWritableRaster(width,
                                                                      height);
            }
        break;

        case TYPE_INT_ARGB:
            {
                colorModel = ColorModel.getRGBdefault();

                raster = colorModel.createCompatibleWritableRaster(width,
                                                                   height);
            }
        break;

        case TYPE_INT_ARGB_PRE:
            {
                colorModel = new
                    DirectColorModel(
                                     ColorSpace.getInstance(ColorSpace.CS_sRGB),
                                     32,
                                     0x00ff0000,// Red
                                     0x0000ff00,// Green
                                     0x000000ff,// Blue
                                     0xff000000,// Alpha
                                     true,       // Alpha Premultiplied
                                     DataBuffer.TYPE_INT
                                     );

                  raster = colorModel.createCompatibleWritableRaster(width,
                                                                      height);
            }
        break;

        case TYPE_INT_BGR:
            {
                colorModel = new DirectColorModel(24,
                                                  0x000000ff,   // Red
                                                  0x0000ff00,   // Green
                                                  0x00ff0000    // Blue
                                                  );
                  raster = colorModel.createCompatibleWritableRaster(width,
                                                                      height);
            }
        break;

        case TYPE_3BYTE_BGR:
            {
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                int[] nBits = {8, 8, 8};
                int[] bOffs = {2, 1, 0};
                colorModel = new ComponentColorModel(cs, nBits, false, false,
                                                     Transparency.OPAQUE,
                                                     DataBuffer.TYPE_BYTE);
                raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                                                        width, height,
                                                        width*3, 3,
                                                        bOffs, null);
            }
        break;

        case TYPE_4BYTE_ABGR:
            {
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                int[] nBits = {8, 8, 8, 8};
                int[] bOffs = {3, 2, 1, 0};
                colorModel = new ComponentColorModel(cs, nBits, true, false,
                                                     Transparency.TRANSLUCENT,
                                                     DataBuffer.TYPE_BYTE);
                raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                                                        width, height,
                                                        width*4, 4,
                                                        bOffs, null);
            }
        break;

        case TYPE_4BYTE_ABGR_PRE:
            {
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                int[] nBits = {8, 8, 8, 8};
                int[] bOffs = {3, 2, 1, 0};
                colorModel = new ComponentColorModel(cs, nBits, true, true,
                                                     Transparency.TRANSLUCENT,
                                                     DataBuffer.TYPE_BYTE);
                raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                                                        width, height,
                                                        width*4, 4,
                                                        bOffs, null);
            }
        break;

        case TYPE_BYTE_GRAY:
            {
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                int[] nBits = {8};
                colorModel = new ComponentColorModel(cs, nBits, false, true,
                                                     Transparency.OPAQUE,
                                                     DataBuffer.TYPE_BYTE);
                raster = colorModel.createCompatibleWritableRaster(width,
                                                                   height);
            }
        break;

        case TYPE_USHORT_GRAY:
            {
                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                int[] nBits = {16};
                colorModel = new ComponentColorModel(cs, nBits, false, true,
                                                     Transparency.OPAQUE,
                                                     DataBuffer.TYPE_USHORT);
                raster = colorModel.createCompatibleWritableRaster(width,
                                                                   height);
            }
        break;

        case TYPE_BYTE_BINARY:
            {
                byte[] arr = {(byte)0, (byte)0xff};

                colorModel = new IndexColorModel(1, 2, arr, arr, arr);
                raster = Raster.createPackedRaster(DataBuffer.TYPE_BYTE,
                                                   width, height, 1, 1, null);
            }
        break;

        case TYPE_BYTE_INDEXED:
            {
                // Create a 6x6x6 color cube
                int[] cmap = new int[256];
                int i=0;
                for (int r=0; r < 256; r += 51) {
                    for (int g=0; g < 256; g += 51) {
                        for (int b=0; b < 256; b += 51) {
                            cmap[i++] = (r<<16)|(g<<8)|b;
                        }
                    }
                }
                // And populate the rest of the cmap with gray values
                int grayIncr = 256/(256-i);

                // The gray ramp will be between 18 and 252
                int gray = grayIncr*3;
                for (; i < 256; i++) {
                    cmap[i] = (gray<<16)|(gray<<8)|gray;
                    gray += grayIncr;
                }

                colorModel = new IndexColorModel(8, 256, cmap, 0, false, -1,
                                                 DataBuffer.TYPE_BYTE);
                raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                                                      width, height, 1, null);
            }
        break;

        case TYPE_USHORT_565_RGB:
            {
                colorModel = new DirectColorModel(16,
                                                  DCM_565_RED_MASK,
                                                  DCM_565_GRN_MASK,
                                                  DCM_565_BLU_MASK
                                                  );
                raster = colorModel.createCompatibleWritableRaster(width,
                                                                   height);
            }
            break;

        case TYPE_USHORT_555_RGB:
            {
                colorModel = new DirectColorModel(15,
                                                  DCM_555_RED_MASK,
                                                  DCM_555_GRN_MASK,
                                                  DCM_555_BLU_MASK
                                                  );
                raster = colorModel.createCompatibleWritableRaster(width,
                                                                   height);
            }
            break;

        default:
            throw new IllegalArgumentException ("Unknown image type " +
                                                imageType);
        }

        this.imageType = imageType;
    }

    /**
     * Constructs a <code>BufferedImage</code> of one of the predefined
     * image types:
     * TYPE_BYTE_BINARY or TYPE_BYTE_INDEXED.
     *
     * <p> If the image type is TYPE_BYTE_BINARY, the number of
     * entries in the color model is used to determine whether the
     * image should have 1, 2, or 4 bits per pixel.  If the color model
     * has 1 or 2 entries, the image will have 1 bit per pixel.  If it
     * has 3 or 4 entries, the image with have 2 bits per pixel.  If
     * it has between 5 and 16 entries, the image will have 4 bits per
     * pixel.  Otherwise, an IllegalArgumentException will be thrown.
     *
     * <p>
     *  构造预定义图像类型之一的<code> BufferedImage </code>：TYPE_BYTE_BINARY或TYPE_BYTE_INDEXED。
     * 
     *  <p>如果图像类型为TYPE_BYTE_BINARY,则颜色模型中的条目数用于确定图像是否应具有每像素1,2或4位。如果颜色模型具有1或2个条目,则图像将具有每像素1位。
     * 如果它具有3或4个条目,则图像具有每像素2位。如果它具有5到16个条目,则图像将具有每像素4位。否则,将抛出IllegalArgumentException。
     * 
     * 
     * @param width     width of the created image
     * @param height    height of the created image
     * @param imageType type of the created image
     * @param cm        <code>IndexColorModel</code> of the created image
     * @throws IllegalArgumentException   if the imageType is not
     * TYPE_BYTE_BINARY or TYPE_BYTE_INDEXED or if the imageType is
     * TYPE_BYTE_BINARY and the color map has more than 16 entries.
     * @see #TYPE_BYTE_BINARY
     * @see #TYPE_BYTE_INDEXED
     */
    public BufferedImage (int width,
                          int height,
                          int imageType,
                          IndexColorModel cm) {
        if (cm.hasAlpha() && cm.isAlphaPremultiplied()) {
            throw new IllegalArgumentException("This image types do not have "+
                                               "premultiplied alpha.");
        }

        switch(imageType) {
        case TYPE_BYTE_BINARY:
            int bits; // Will be set below
            int mapSize = cm.getMapSize();
            if (mapSize <= 2) {
                bits = 1;
            } else if (mapSize <= 4) {
                bits = 2;
            } else if (mapSize <= 16) {
                bits = 4;
            } else {
                throw new IllegalArgumentException
                    ("Color map for TYPE_BYTE_BINARY " +
                     "must have no more than 16 entries");
            }
            raster = Raster.createPackedRaster(DataBuffer.TYPE_BYTE,
                                                width, height, 1, bits, null);
            break;

        case TYPE_BYTE_INDEXED:
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,
                                                    width, height, 1, null);
            break;
        default:
            throw new IllegalArgumentException("Invalid image type (" +
                                               imageType+").  Image type must"+
                                               " be either TYPE_BYTE_BINARY or "+
                                               " TYPE_BYTE_INDEXED");
        }

        if (!cm.isCompatibleRaster(raster)) {
            throw new IllegalArgumentException("Incompatible image type and IndexColorModel");
        }

        colorModel = cm;
        this.imageType = imageType;
    }

    /**
     * Constructs a new <code>BufferedImage</code> with a specified
     * <code>ColorModel</code> and <code>Raster</code>.  If the number and
     * types of bands in the <code>SampleModel</code> of the
     * <code>Raster</code> do not match the number and types required by
     * the <code>ColorModel</code> to represent its color and alpha
     * components, a {@link RasterFormatException} is thrown.  This
     * method can multiply or divide the color <code>Raster</code> data by
     * alpha to match the <code>alphaPremultiplied</code> state
     * in the <code>ColorModel</code>.  Properties for this
     * <code>BufferedImage</code> can be established by passing
     * in a {@link Hashtable} of <code>String</code>/<code>Object</code>
     * pairs.
     * <p>
     * 使用指定的<code> ColorModel </code>和<code> Raster </code>构造新的<code> BufferedImage </code>。
     * 如果<code> Raster </code>的<code> SampleModel </code>中的波段数量和类型与<code> ColorModel </code>表示其颜色所需的数量和类型不匹配
     * , alpha组件,则会抛出{@link RasterFormatException}。
     * 使用指定的<code> ColorModel </code>和<code> Raster </code>构造新的<code> BufferedImage </code>。
     * 此方法可以通过alpha将颜色<code> Raster </code>乘以或除以匹配<code> ColorModel </code>中的<code> alphaPremultiplied </code>
     * 状态。
     * 使用指定的<code> ColorModel </code>和<code> Raster </code>构造新的<code> BufferedImage </code>。
     * 可通过传递<code> String </code> / <code> Object </code>对的{@link Hashtable}来建立此<code> BufferedImage </code>
     * 的属性。
     * 使用指定的<code> ColorModel </code>和<code> Raster </code>构造新的<code> BufferedImage </code>。
     * 
     * 
     * @param cm <code>ColorModel</code> for the new image
     * @param raster     <code>Raster</code> for the image data
     * @param isRasterPremultiplied   if <code>true</code>, the data in
     *                  the raster has been premultiplied with alpha.
     * @param properties <code>Hashtable</code> of
     *                  <code>String</code>/<code>Object</code> pairs.
     * @exception RasterFormatException if the number and
     * types of bands in the <code>SampleModel</code> of the
     * <code>Raster</code> do not match the number and types required by
     * the <code>ColorModel</code> to represent its color and alpha
     * components.
     * @exception IllegalArgumentException if
     *          <code>raster</code> is incompatible with <code>cm</code>
     * @see ColorModel
     * @see Raster
     * @see WritableRaster
     */


/*
 *
 *  FOR NOW THE CODE WHICH DEFINES THE RASTER TYPE IS DUPLICATED BY DVF
 *  SEE THE METHOD DEFINERASTERTYPE @ RASTEROUTPUTMANAGER
 *
 * <p>
 *  现在,定义RASTER类型的代码被DVF复制的代码参见METHODS DEFINERASTERTYPE @ RASTEROUTPUTMANAGER
 * 
 */
    public BufferedImage (ColorModel cm,
                          WritableRaster raster,
                          boolean isRasterPremultiplied,
                          Hashtable<?,?> properties) {

        if (!cm.isCompatibleRaster(raster)) {
            throw new
                IllegalArgumentException("Raster "+raster+
                                         " is incompatible with ColorModel "+
                                         cm);
        }

        if ((raster.minX != 0) || (raster.minY != 0)) {
            throw new
                IllegalArgumentException("Raster "+raster+
                                         " has minX or minY not equal to zero: "
                                         + raster.minX + " " + raster.minY);
        }

        colorModel = cm;
        this.raster  = raster;
        this.properties = properties;
        int numBands = raster.getNumBands();
        boolean isAlphaPre = cm.isAlphaPremultiplied();
        final boolean isStandard = isStandard(cm, raster);
        ColorSpace cs;

        // Force the raster data alpha state to match the premultiplied
        // state in the color model
        coerceData(isRasterPremultiplied);

        SampleModel sm = raster.getSampleModel();
        cs = cm.getColorSpace();
        int csType = cs.getType();
        if (csType != ColorSpace.TYPE_RGB) {
            if (csType == ColorSpace.TYPE_GRAY &&
                isStandard &&
                cm instanceof ComponentColorModel) {
                // Check if this might be a child raster (fix for bug 4240596)
                if (sm instanceof ComponentSampleModel &&
                    ((ComponentSampleModel)sm).getPixelStride() != numBands) {
                    imageType = TYPE_CUSTOM;
                } else if (raster instanceof ByteComponentRaster &&
                       raster.getNumBands() == 1 &&
                       cm.getComponentSize(0) == 8 &&
                       ((ByteComponentRaster)raster).getPixelStride() == 1) {
                    imageType = TYPE_BYTE_GRAY;
                } else if (raster instanceof ShortComponentRaster &&
                       raster.getNumBands() == 1 &&
                       cm.getComponentSize(0) == 16 &&
                       ((ShortComponentRaster)raster).getPixelStride() == 1) {
                    imageType = TYPE_USHORT_GRAY;
                }
            } else {
                imageType = TYPE_CUSTOM;
            }
            return;
        }

        if ((raster instanceof IntegerComponentRaster) &&
            (numBands == 3 || numBands == 4)) {
            IntegerComponentRaster iraster =
                (IntegerComponentRaster) raster;
            // Check if the raster params and the color model
            // are correct
            int pixSize = cm.getPixelSize();
            if (iraster.getPixelStride() == 1 &&
                isStandard &&
                cm instanceof DirectColorModel  &&
                (pixSize == 32 || pixSize == 24))
            {
                // Now check on the DirectColorModel params
                DirectColorModel dcm = (DirectColorModel) cm;
                int rmask = dcm.getRedMask();
                int gmask = dcm.getGreenMask();
                int bmask = dcm.getBlueMask();
                if (rmask == DCM_RED_MASK && gmask == DCM_GREEN_MASK &&
                    bmask == DCM_BLUE_MASK)
                {
                    if (dcm.getAlphaMask() == DCM_ALPHA_MASK) {
                        imageType = (isAlphaPre
                                     ? TYPE_INT_ARGB_PRE
                                     : TYPE_INT_ARGB);
                    }
                    else {
                        // No Alpha
                        if (!dcm.hasAlpha()) {
                            imageType = TYPE_INT_RGB;
                        }
                    }
                }   // if (dcm.getRedMask() == DCM_RED_MASK &&
                else if (rmask == DCM_BGR_RED_MASK && gmask == DCM_BGR_GRN_MASK
                         && bmask == DCM_BGR_BLU_MASK) {
                    if (!dcm.hasAlpha()) {
                        imageType = TYPE_INT_BGR;
                    }
                }  // if (rmask == DCM_BGR_RED_MASK &&
            }   // if (iraster.getPixelStride() == 1
        }   // ((raster instanceof IntegerComponentRaster) &&
        else if ((cm instanceof IndexColorModel) && (numBands == 1) &&
                 isStandard &&
                 (!cm.hasAlpha() || !isAlphaPre))
        {
            IndexColorModel icm = (IndexColorModel) cm;
            int pixSize = icm.getPixelSize();

            if (raster instanceof BytePackedRaster) {
                imageType = TYPE_BYTE_BINARY;
            }   // if (raster instanceof BytePackedRaster)
            else if (raster instanceof ByteComponentRaster) {
                ByteComponentRaster braster = (ByteComponentRaster) raster;
                if (braster.getPixelStride() == 1 && pixSize <= 8) {
                    imageType = TYPE_BYTE_INDEXED;
                }
            }
        }   // else if (cm instanceof IndexColorModel) && (numBands == 1))
        else if ((raster instanceof ShortComponentRaster)
                 && (cm instanceof DirectColorModel)
                 && isStandard
                 && (numBands == 3)
                 && !cm.hasAlpha())
        {
            DirectColorModel dcm = (DirectColorModel) cm;
            if (dcm.getRedMask() == DCM_565_RED_MASK) {
                if (dcm.getGreenMask() == DCM_565_GRN_MASK &&
                    dcm.getBlueMask()  == DCM_565_BLU_MASK) {
                    imageType = TYPE_USHORT_565_RGB;
                }
            }
            else if (dcm.getRedMask() == DCM_555_RED_MASK) {
                if (dcm.getGreenMask() == DCM_555_GRN_MASK &&
                    dcm.getBlueMask() == DCM_555_BLU_MASK) {
                    imageType = TYPE_USHORT_555_RGB;
                }
            }
        }   // else if ((cm instanceof IndexColorModel) && (numBands == 1))
        else if ((raster instanceof ByteComponentRaster)
                 && (cm instanceof ComponentColorModel)
                 && isStandard
                 && (raster.getSampleModel() instanceof PixelInterleavedSampleModel)
                 && (numBands == 3 || numBands == 4))
        {
            ComponentColorModel ccm = (ComponentColorModel) cm;
            PixelInterleavedSampleModel csm =
                (PixelInterleavedSampleModel)raster.getSampleModel();
            ByteComponentRaster braster = (ByteComponentRaster) raster;
            int[] offs = csm.getBandOffsets();
            if (ccm.getNumComponents() != numBands) {
                throw new RasterFormatException("Number of components in "+
                                                "ColorModel ("+
                                                ccm.getNumComponents()+
                                                ") does not match # in "+
                                                " Raster ("+numBands+")");
            }
            int[] nBits = ccm.getComponentSize();
            boolean is8bit = true;
            for (int i=0; i < numBands; i++) {
                if (nBits[i] != 8) {
                    is8bit = false;
                    break;
                }
            }
            if (is8bit &&
                braster.getPixelStride() == numBands &&
                offs[0] == numBands-1 &&
                offs[1] == numBands-2 &&
                offs[2] == numBands-3)
            {
                if (numBands == 3 && !ccm.hasAlpha()) {
                    imageType = TYPE_3BYTE_BGR;
                }
                else if (offs[3] == 0 && ccm.hasAlpha()) {
                    imageType = (isAlphaPre
                                 ? TYPE_4BYTE_ABGR_PRE
                                 : TYPE_4BYTE_ABGR);
                }
            }
        }   // else if ((raster instanceof ByteComponentRaster) &&
    }

    private static boolean isStandard(ColorModel cm, WritableRaster wr) {
        final Class<? extends ColorModel> cmClass = cm.getClass();
        final Class<? extends WritableRaster> wrClass = wr.getClass();
        final Class<? extends SampleModel> smClass = wr.getSampleModel().getClass();

        final PrivilegedAction<Boolean> checkClassLoadersAction =
                new PrivilegedAction<Boolean>()
        {

            @Override
            public Boolean run() {
                final ClassLoader std = System.class.getClassLoader();

                return (cmClass.getClassLoader() == std) &&
                        (smClass.getClassLoader() == std) &&
                        (wrClass.getClassLoader() == std);
            }
        };
        return AccessController.doPrivileged(checkClassLoadersAction);
    }

    /**
     * Returns the image type.  If it is not one of the known types,
     * TYPE_CUSTOM is returned.
     * <p>
     *  返回图像类型。如果它不是已知类型之一,则返回TYPE_CUSTOM。
     * 
     * 
     * @return the image type of this <code>BufferedImage</code>.
     * @see #TYPE_INT_RGB
     * @see #TYPE_INT_ARGB
     * @see #TYPE_INT_ARGB_PRE
     * @see #TYPE_INT_BGR
     * @see #TYPE_3BYTE_BGR
     * @see #TYPE_4BYTE_ABGR
     * @see #TYPE_4BYTE_ABGR_PRE
     * @see #TYPE_BYTE_GRAY
     * @see #TYPE_BYTE_BINARY
     * @see #TYPE_BYTE_INDEXED
     * @see #TYPE_USHORT_GRAY
     * @see #TYPE_USHORT_565_RGB
     * @see #TYPE_USHORT_555_RGB
     * @see #TYPE_CUSTOM
     */
    public int getType() {
        return imageType;
    }

    /**
     * Returns the <code>ColorModel</code>.
     * <p>
     *  返回<code> ColorModel </code>。
     * 
     * 
     * @return the <code>ColorModel</code> of this
     *  <code>BufferedImage</code>.
     */
    public ColorModel getColorModel() {
        return colorModel;
    }

    /**
     * Returns the {@link WritableRaster}.
     * <p>
     *  返回{@link WritableRaster}。
     * 
     * 
     * @return the <code>WriteableRaster</code> of this
     *  <code>BufferedImage</code>.
     */
    public WritableRaster getRaster() {
        return raster;
    }


    /**
     * Returns a <code>WritableRaster</code> representing the alpha
     * channel for <code>BufferedImage</code> objects
     * with <code>ColorModel</code> objects that support a separate
     * spatial alpha channel, such as <code>ComponentColorModel</code> and
     * <code>DirectColorModel</code>.  Returns <code>null</code> if there
     * is no alpha channel associated with the <code>ColorModel</code> in
     * this image.  This method assumes that for all
     * <code>ColorModel</code> objects other than
     * <code>IndexColorModel</code>, if the <code>ColorModel</code>
     * supports alpha, there is a separate alpha channel
     * which is stored as the last band of image data.
     * If the image uses an <code>IndexColorModel</code> that
     * has alpha in the lookup table, this method returns
     * <code>null</code> since there is no spatially discrete alpha
     * channel.  This method creates a new
     * <code>WritableRaster</code>, but shares the data array.
     * <p>
     * 返回<code> WritableRaster </code>代表<code> BufferedImage </code>对象的Alpha通道,该对象具有支持单独的空间Alpha通道的<code> Co
     * lorModel </code>对象,例如<code> ComponentColorModel < code>和<code> DirectColorModel </code>。
     * 如果此图片中没有与<code> ColorModel </code>关联的Alpha通道,则返回<code> null </code>。
     * 这个方法假设对于除<code> IndexColorModel </code>之外的所有<code> ColorModel </code>对象,如果<code> ColorModel </code>支持
     * alpha,则有一个单独的alpha通道存储为图像数据的最后一个频带。
     * 如果此图片中没有与<code> ColorModel </code>关联的Alpha通道,则返回<code> null </code>。
     * 如果图像使用在查找表中具有alpha的<code> IndexColorModel </code>,则该方法返回<code> null </code>,因为没有空间离散的alpha通道。
     * 此方法创建一个新的<code> WritableRaster </code>,但共享数据数组。
     * 
     * 
     * @return a <code>WritableRaster</code> or <code>null</code> if this
     *          <code>BufferedImage</code> has no alpha channel associated
     *          with its <code>ColorModel</code>.
     */
    public WritableRaster getAlphaRaster() {
        return colorModel.getAlphaRaster(raster);
    }

    /**
     * Returns an integer pixel in the default RGB color model
     * (TYPE_INT_ARGB) and default sRGB colorspace.  Color
     * conversion takes place if this default model does not match
     * the image <code>ColorModel</code>.  There are only 8-bits of
     * precision for each color component in the returned data when using
     * this method.
     *
     * <p>
     *
     * An <code>ArrayOutOfBoundsException</code> may be thrown
     * if the coordinates are not in bounds.
     * However, explicit bounds checking is not guaranteed.
     *
     * <p>
     *  返回默认RGB颜色模型(TYPE_INT_ARGB)和默认sRGB颜色空间中的整数像素。如果此默认模型与图像<code> ColorModel </code>不匹配,则会进行颜色转换。
     * 使用此方法时,返回数据中的每个颜色分量只有8位的精度。
     * 
     * <p>
     * 
     *  如果坐标不在边界中,可能会抛出<code> ArrayOutOfBoundsException </code>。但是,不能保证显式边界检查。
     * 
     * 
     * @param x the X coordinate of the pixel from which to get
     *          the pixel in the default RGB color model and sRGB
     *          color space
     * @param y the Y coordinate of the pixel from which to get
     *          the pixel in the default RGB color model and sRGB
     *          color space
     * @return an integer pixel in the default RGB color model and
     *          default sRGB colorspace.
     * @see #setRGB(int, int, int)
     * @see #setRGB(int, int, int, int, int[], int, int)
     */
    public int getRGB(int x, int y) {
        return colorModel.getRGB(raster.getDataElements(x, y, null));
    }

    /**
     * Returns an array of integer pixels in the default RGB color model
     * (TYPE_INT_ARGB) and default sRGB color space,
     * from a portion of the image data.  Color conversion takes
     * place if the default model does not match the image
     * <code>ColorModel</code>.  There are only 8-bits of precision for
     * each color component in the returned data when
     * using this method.  With a specified coordinate (x,&nbsp;y) in the
     * image, the ARGB pixel can be accessed in this way:
     *
     * <pre>
     *    pixel   = rgbArray[offset + (y-startY)*scansize + (x-startX)]; </pre>
     *
     * <p>
     *
     * An <code>ArrayOutOfBoundsException</code> may be thrown
     * if the region is not in bounds.
     * However, explicit bounds checking is not guaranteed.
     *
     * <p>
     * 从图像数据的一部分返回默认RGB颜色模型(TYPE_INT_ARGB)和默认sRGB颜色空间中的整数像素数组。
     * 如果默认模型与图像<code> ColorModel </code>不匹配,则会进行颜色转换。使用此方法时,返回数据中的每个颜色分量只有8位的精度。
     * 利用图像中的指定坐标(x,y),可以以这种方式访问​​ARGB像素：。
     * 
     * <pre>
     *  pixel = rgbArray [offset +(y-startY)* scansize +(x-startX)]; </pre>
     * 
     * <p>
     * 
     *  如果区域不在边界中,可能会抛出<code> ArrayOutOfBoundsException </code>。但是,不能保证显式边界检查。
     * 
     * 
     * @param startX      the starting X coordinate
     * @param startY      the starting Y coordinate
     * @param w           width of region
     * @param h           height of region
     * @param rgbArray    if not <code>null</code>, the rgb pixels are
     *          written here
     * @param offset      offset into the <code>rgbArray</code>
     * @param scansize    scanline stride for the <code>rgbArray</code>
     * @return            array of RGB pixels.
     * @see #setRGB(int, int, int)
     * @see #setRGB(int, int, int, int, int[], int, int)
     */
    public int[] getRGB(int startX, int startY, int w, int h,
                        int[] rgbArray, int offset, int scansize) {
        int yoff  = offset;
        int off;
        Object data;
        int nbands = raster.getNumBands();
        int dataType = raster.getDataBuffer().getDataType();
        switch (dataType) {
        case DataBuffer.TYPE_BYTE:
            data = new byte[nbands];
            break;
        case DataBuffer.TYPE_USHORT:
            data = new short[nbands];
            break;
        case DataBuffer.TYPE_INT:
            data = new int[nbands];
            break;
        case DataBuffer.TYPE_FLOAT:
            data = new float[nbands];
            break;
        case DataBuffer.TYPE_DOUBLE:
            data = new double[nbands];
            break;
        default:
            throw new IllegalArgumentException("Unknown data buffer type: "+
                                               dataType);
        }

        if (rgbArray == null) {
            rgbArray = new int[offset+h*scansize];
        }

        for (int y = startY; y < startY+h; y++, yoff+=scansize) {
            off = yoff;
            for (int x = startX; x < startX+w; x++) {
                rgbArray[off++] = colorModel.getRGB(raster.getDataElements(x,
                                                                        y,
                                                                        data));
            }
        }

        return rgbArray;
    }


    /**
     * Sets a pixel in this <code>BufferedImage</code> to the specified
     * RGB value. The pixel is assumed to be in the default RGB color
     * model, TYPE_INT_ARGB, and default sRGB color space.  For images
     * with an <code>IndexColorModel</code>, the index with the nearest
     * color is chosen.
     *
     * <p>
     *
     * An <code>ArrayOutOfBoundsException</code> may be thrown
     * if the coordinates are not in bounds.
     * However, explicit bounds checking is not guaranteed.
     *
     * <p>
     *  将此<code> BufferedImage </code>中的像素设置为指定的RGB值。假设像素处于默认RGB颜色模型TYPE_INT_ARGB和默认sRGB颜色空间中。
     * 对于带有<code> IndexColorModel </code>的图像,选择具有最接近颜色的索引。
     * 
     * <p>
     * 
     *  如果坐标不在边界中,可能会抛出<code> ArrayOutOfBoundsException </code>。但是,不能保证显式边界检查。
     * 
     * 
     * @param x the X coordinate of the pixel to set
     * @param y the Y coordinate of the pixel to set
     * @param rgb the RGB value
     * @see #getRGB(int, int)
     * @see #getRGB(int, int, int, int, int[], int, int)
     */
    public synchronized void setRGB(int x, int y, int rgb) {
        raster.setDataElements(x, y, colorModel.getDataElements(rgb, null));
    }

    /**
     * Sets an array of integer pixels in the default RGB color model
     * (TYPE_INT_ARGB) and default sRGB color space,
     * into a portion of the image data.  Color conversion takes place
     * if the default model does not match the image
     * <code>ColorModel</code>.  There are only 8-bits of precision for
     * each color component in the returned data when
     * using this method.  With a specified coordinate (x,&nbsp;y) in the
     * this image, the ARGB pixel can be accessed in this way:
     * <pre>
     *    pixel   = rgbArray[offset + (y-startY)*scansize + (x-startX)];
     * </pre>
     * WARNING: No dithering takes place.
     *
     * <p>
     *
     * An <code>ArrayOutOfBoundsException</code> may be thrown
     * if the region is not in bounds.
     * However, explicit bounds checking is not guaranteed.
     *
     * <p>
     * 将默认RGB颜色模型(TYPE_INT_ARGB)和默认sRGB颜色空间中的整数像素数组设置为图像数据的一部分。
     * 如果默认模型与图像<code> ColorModel </code>不匹配,则会进行颜色转换。使用此方法时,返回数据中的每个颜色分量只有8位的精度。
     * 利用该图像中的指定坐标(x,y),可以以这种方式访问​​ARGB像素：。
     * <pre>
     *  pixel = rgbArray [offset +(y-startY)* scansize +(x-startX)];
     * </pre>
     *  警告：不发生抖动。
     * 
     * <p>
     * 
     *  如果区域不在边界中,可能会抛出<code> ArrayOutOfBoundsException </code>。但是,不能保证显式边界检查。
     * 
     * 
     * @param startX      the starting X coordinate
     * @param startY      the starting Y coordinate
     * @param w           width of the region
     * @param h           height of the region
     * @param rgbArray    the rgb pixels
     * @param offset      offset into the <code>rgbArray</code>
     * @param scansize    scanline stride for the <code>rgbArray</code>
     * @see #getRGB(int, int)
     * @see #getRGB(int, int, int, int, int[], int, int)
     */
    public void setRGB(int startX, int startY, int w, int h,
                        int[] rgbArray, int offset, int scansize) {
        int yoff  = offset;
        int off;
        Object pixel = null;

        for (int y = startY; y < startY+h; y++, yoff+=scansize) {
            off = yoff;
            for (int x = startX; x < startX+w; x++) {
                pixel = colorModel.getDataElements(rgbArray[off++], pixel);
                raster.setDataElements(x, y, pixel);
            }
        }
    }


    /**
     * Returns the width of the <code>BufferedImage</code>.
     * <p>
     *  返回<code> BufferedImage </code>的宽度。
     * 
     * 
     * @return the width of this <code>BufferedImage</code>
     */
    public int getWidth() {
        return raster.getWidth();
    }

    /**
     * Returns the height of the <code>BufferedImage</code>.
     * <p>
     *  返回<code> BufferedImage </code>的高度。
     * 
     * 
     * @return the height of this <code>BufferedImage</code>
     */
    public int getHeight() {
        return raster.getHeight();
    }

    /**
     * Returns the width of the <code>BufferedImage</code>.
     * <p>
     *  返回<code> BufferedImage </code>的宽度。
     * 
     * 
     * @param observer ignored
     * @return the width of this <code>BufferedImage</code>
     */
    public int getWidth(ImageObserver observer) {
        return raster.getWidth();
    }

    /**
     * Returns the height of the <code>BufferedImage</code>.
     * <p>
     *  返回<code> BufferedImage </code>的高度。
     * 
     * 
     * @param observer ignored
     * @return the height of this <code>BufferedImage</code>
     */
    public int getHeight(ImageObserver observer) {
        return raster.getHeight();
    }

    /**
     * Returns the object that produces the pixels for the image.
     * <p>
     *  返回生成图像像素的对象。
     * 
     * 
     * @return the {@link ImageProducer} that is used to produce the
     * pixels for this image.
     * @see ImageProducer
     */
    public ImageProducer getSource() {
        if (osis == null) {
            if (properties == null) {
                properties = new Hashtable();
            }
            osis = new OffScreenImageSource(this, properties);
        }
        return osis;
    }


    /**
     * Returns a property of the image by name.  Individual property names
     * are defined by the various image formats.  If a property is not
     * defined for a particular image, this method returns the
     * <code>UndefinedProperty</code> field.  If the properties
     * for this image are not yet known, then this method returns
     * <code>null</code> and the <code>ImageObserver</code> object is
     * notified later.  The property name "comment" should be used to
     * store an optional comment that can be presented to the user as a
     * description of the image, its source, or its author.
     * <p>
     * 按名称返回图像的属性。单个属性名称由各种图像格式定义。如果没有为特定映像定义属性,则此方法返回<code> UndefinedProperty </code>字段。
     * 如果此图像的属性尚未知晓,则此方法返回<code> null </code>,稍后会通知<code> ImageObserver </code>对象。
     * 属性名称"注释"应用于存储可选注释,可以作为图像,其源或其作者的描述呈现给用户。
     * 
     * 
     * @param name the property name
     * @param observer the <code>ImageObserver</code> that receives
     *  notification regarding image information
     * @return an {@link Object} that is the property referred to by the
     *          specified <code>name</code> or <code>null</code> if the
     *          properties of this image are not yet known.
     * @throws NullPointerException if the property name is null.
     * @see ImageObserver
     * @see java.awt.Image#UndefinedProperty
     */
    public Object getProperty(String name, ImageObserver observer) {
        return getProperty(name);
    }

    /**
     * Returns a property of the image by name.
     * <p>
     *  按名称返回图像的属性。
     * 
     * 
     * @param name the property name
     * @return an <code>Object</code> that is the property referred to by
     *          the specified <code>name</code>.
     * @throws NullPointerException if the property name is null.
     */
    public Object getProperty(String name) {
        if (name == null) {
            throw new NullPointerException("null property name is not allowed");
        }
        if (properties == null) {
            return java.awt.Image.UndefinedProperty;
        }
        Object o = properties.get(name);
        if (o == null) {
            o = java.awt.Image.UndefinedProperty;
        }
        return o;
    }

    /**
     * This method returns a {@link Graphics2D}, but is here
     * for backwards compatibility.  {@link #createGraphics() createGraphics} is more
     * convenient, since it is declared to return a
     * <code>Graphics2D</code>.
     * <p>
     *  此方法返回一个{@link Graphics2D},但这里是为了向后兼容性。
     *  {@link #createGraphics()createGraphics}更方便,因为它被声明为返回一个<code> Graphics2D </code>。
     * 
     * 
     * @return a <code>Graphics2D</code>, which can be used to draw into
     *          this image.
     */
    public java.awt.Graphics getGraphics() {
        return createGraphics();
    }

    /**
     * Creates a <code>Graphics2D</code>, which can be used to draw into
     * this <code>BufferedImage</code>.
     * <p>
     *  创建一个<code> Graphics2D </code>,可以用来绘制这个<code> BufferedImage </code>。
     * 
     * 
     * @return a <code>Graphics2D</code>, used for drawing into this
     *          image.
     */
    public Graphics2D createGraphics() {
        GraphicsEnvironment env =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        return env.createGraphics(this);
    }

    /**
     * Returns a subimage defined by a specified rectangular region.
     * The returned <code>BufferedImage</code> shares the same
     * data array as the original image.
     * <p>
     *  返回由指定矩形区域定义的子图像。返回的<code> BufferedImage </code>与原始图像共享相同的数据数组。
     * 
     * 
     * @param x the X coordinate of the upper-left corner of the
     *          specified rectangular region
     * @param y the Y coordinate of the upper-left corner of the
     *          specified rectangular region
     * @param w the width of the specified rectangular region
     * @param h the height of the specified rectangular region
     * @return a <code>BufferedImage</code> that is the subimage of this
     *          <code>BufferedImage</code>.
     * @exception RasterFormatException if the specified
     * area is not contained within this <code>BufferedImage</code>.
     */
    public BufferedImage getSubimage (int x, int y, int w, int h) {
        return new BufferedImage (colorModel,
                                  raster.createWritableChild(x, y, w, h,
                                                             0, 0, null),
                                  colorModel.isAlphaPremultiplied(),
                                  properties);
    }

    /**
     * Returns whether or not the alpha has been premultiplied.  It
     * returns <code>false</code> if there is no alpha.
     * <p>
     *  返回alpha是否已预乘。如果没有alpha,它返回<code> false </code>。
     * 
     * 
     * @return <code>true</code> if the alpha has been premultiplied;
     *          <code>false</code> otherwise.
     */
    public boolean isAlphaPremultiplied() {
        return colorModel.isAlphaPremultiplied();
    }

    /**
     * Forces the data to match the state specified in the
     * <code>isAlphaPremultiplied</code> variable.  It may multiply or
     * divide the color raster data by alpha, or do nothing if the data is
     * in the correct state.
     * <p>
     *  强制数据匹配<code> isAlphaPremultiplied </code>变量中指定的状态。它可以将颜色栅格数据乘以或除以阿尔法,或者如果数据处于正确状态,则什么也不做。
     * 
     * 
     * @param isAlphaPremultiplied <code>true</code> if the alpha has been
     *          premultiplied; <code>false</code> otherwise.
     */
    public void coerceData (boolean isAlphaPremultiplied) {
        if (colorModel.hasAlpha() &&
            colorModel.isAlphaPremultiplied() != isAlphaPremultiplied) {
            // Make the color model do the conversion
            colorModel = colorModel.coerceData (raster, isAlphaPremultiplied);
        }
    }

    /**
     * Returns a <code>String</code> representation of this
     * <code>BufferedImage</code> object and its values.
     * <p>
     *  返回此<> BufferedImage </code>对象及其值的<code> String </code>表示形式。
     * 
     * 
     * @return a <code>String</code> representing this
     *          <code>BufferedImage</code>.
     */
    public String toString() {
        return "BufferedImage@"+Integer.toHexString(hashCode())
            +": type = "+imageType
            +" "+colorModel+" "+raster;
    }

    /**
     * Returns a {@link Vector} of {@link RenderedImage} objects that are
     * the immediate sources, not the sources of these immediate sources,
     * of image data for this <code>BufferedImage</code>.  This
     * method returns <code>null</code> if the <code>BufferedImage</code>
     * has no information about its immediate sources.  It returns an
     * empty <code>Vector</code> if the <code>BufferedImage</code> has no
     * immediate sources.
     * <p>
     * 返回此@ <code> BufferedImage </code>的图片数据的直接来源(而不是这些直接来源的来源)的{@link RenderedImage}对象的{@link Vector}。
     * 如果<code> BufferedImage </code>没有关于其直接源的信息,此方法返回<code> null </code>。
     * 如果<code> BufferedImage </code>没有直接来源,它会返回一个空的<code> Vector </code>。
     * 
     * 
     * @return a <code>Vector</code> containing immediate sources of
     *          this <code>BufferedImage</code> object's image date, or
     *          <code>null</code> if this <code>BufferedImage</code> has
     *          no information about its immediate sources, or an empty
     *          <code>Vector</code> if this <code>BufferedImage</code>
     *          has no immediate sources.
     */
    public Vector<RenderedImage> getSources() {
        return null;
    }

    /**
     * Returns an array of names recognized by
     * {@link #getProperty(String) getProperty(String)}
     * or <code>null</code>, if no property names are recognized.
     * <p>
     *  如果未识别属性名称,则返回由{@link #getProperty(String)getProperty(String)}或<code> null </code>识别的名称数组。
     * 
     * 
     * @return a <code>String</code> array containing all of the property
     *          names that <code>getProperty(String)</code> recognizes;
     *          or <code>null</code> if no property names are recognized.
     */
    public String[] getPropertyNames() {
         return null;
    }

    /**
     * Returns the minimum x coordinate of this
     * <code>BufferedImage</code>.  This is always zero.
     * <p>
     *  返回此<code> BufferedImage </code>的最小x坐标。这总是零。
     * 
     * 
     * @return the minimum x coordinate of this
     *          <code>BufferedImage</code>.
     */
    public int getMinX() {
        return raster.getMinX();
    }

    /**
     * Returns the minimum y coordinate of this
     * <code>BufferedImage</code>.  This is always zero.
     * <p>
     *  返回此<code> BufferedImage </code>的最小y坐标。这总是零。
     * 
     * 
     * @return the minimum y coordinate of this
     *          <code>BufferedImage</code>.
     */
    public int getMinY() {
        return raster.getMinY();
    }

    /**
     * Returns the <code>SampleModel</code> associated with this
     * <code>BufferedImage</code>.
     * <p>
     *  返回与此<code> BufferedImage </code>关联的<code> SampleModel </code>。
     * 
     * 
     * @return the <code>SampleModel</code> of this
     *          <code>BufferedImage</code>.
     */
    public SampleModel getSampleModel() {
        return raster.getSampleModel();
    }

    /**
     * Returns the number of tiles in the x direction.
     * This is always one.
     * <p>
     *  返回x方向上的图块数量。这总是一个。
     * 
     * 
     * @return the number of tiles in the x direction.
     */
    public int getNumXTiles() {
        return 1;
    }

    /**
     * Returns the number of tiles in the y direction.
     * This is always one.
     * <p>
     *  返回y方向上的tile数量。这总是一个。
     * 
     * 
     * @return the number of tiles in the y direction.
     */
    public int getNumYTiles() {
        return 1;
    }

    /**
     * Returns the minimum tile index in the x direction.
     * This is always zero.
     * <p>
     *  返回x方向上的最小图块索引。这总是零。
     * 
     * 
     * @return the minimum tile index in the x direction.
     */
    public int getMinTileX() {
        return 0;
    }

    /**
     * Returns the minimum tile index in the y direction.
     * This is always zero.
     * <p>
     *  返回y方向上的最小图块索引。这总是零。
     * 
     * 
     * @return the minimum tile index in the y direction.
     */
    public int getMinTileY() {
        return 0;
    }

    /**
     * Returns the tile width in pixels.
     * <p>
     *  返回图块宽度(以像素为单位)。
     * 
     * 
     * @return the tile width in pixels.
     */
    public int getTileWidth() {
       return raster.getWidth();
    }

    /**
     * Returns the tile height in pixels.
     * <p>
     *  返回图块高度(以像素为单位)。
     * 
     * 
     * @return the tile height in pixels.
     */
    public int getTileHeight() {
       return raster.getHeight();
    }

    /**
     * Returns the x offset of the tile grid relative to the origin,
     * For example, the x coordinate of the location of tile
     * (0,&nbsp;0).  This is always zero.
     * <p>
     *  返回拼贴网格相对于原点的x偏移量,例如,拼贴位置的x坐标(0,&nbsp; 0)。这总是零。
     * 
     * 
     * @return the x offset of the tile grid.
     */
    public int getTileGridXOffset() {
        return raster.getSampleModelTranslateX();
    }

    /**
     * Returns the y offset of the tile grid relative to the origin,
     * For example, the y coordinate of the location of tile
     * (0,&nbsp;0).  This is always zero.
     * <p>
     *  返回tile网格相对于原点的y偏移量,例如,tile(0,&nbsp; 0)位置的y坐标。这总是零。
     * 
     * 
     * @return the y offset of the tile grid.
     */
    public int getTileGridYOffset() {
        return raster.getSampleModelTranslateY();
    }

    /**
     * Returns tile (<code>tileX</code>,&nbsp;<code>tileY</code>).  Note
     * that <code>tileX</code> and <code>tileY</code> are indices
     * into the tile array, not pixel locations.  The <code>Raster</code>
     * that is returned is live, which means that it is updated if the
     * image is changed.
     * <p>
     * 返回tile(<code> tileX </code>,&nbsp; <code> tileY </code>)。
     * 注意,<code> tileX </code>和<code> tileY </code>是拼贴数组的索引,而不是像素位置。
     * 返回的<code> Raster </code>是实时的,这意味着如果更改图像,它会更新。
     * 
     * 
     * @param tileX the x index of the requested tile in the tile array
     * @param tileY the y index of the requested tile in the tile array
     * @return a <code>Raster</code> that is the tile defined by the
     *          arguments <code>tileX</code> and <code>tileY</code>.
     * @exception ArrayIndexOutOfBoundsException if both
     *          <code>tileX</code> and <code>tileY</code> are not
     *          equal to 0
     */
    public Raster getTile(int tileX, int tileY) {
        if (tileX == 0 && tileY == 0) {
            return raster;
        }
        throw new ArrayIndexOutOfBoundsException("BufferedImages only have"+
             " one tile with index 0,0");
    }

    /**
     * Returns the image as one large tile.  The <code>Raster</code>
     * returned is a copy of the image data is not updated if the
     * image is changed.
     * <p>
     *  将图像返回为一个大图块。返回的<code> Raster </code>是图像数据的副本,如果图像更改,则不会更新。
     * 
     * 
     * @return a <code>Raster</code> that is a copy of the image data.
     * @see #setData(Raster)
     */
    public Raster getData() {

        // REMIND : this allocates a whole new tile if raster is a
        // subtile.  (It only copies in the requested area)
        // We should do something smarter.
        int width = raster.getWidth();
        int height = raster.getHeight();
        int startX = raster.getMinX();
        int startY = raster.getMinY();
        WritableRaster wr =
           Raster.createWritableRaster(raster.getSampleModel(),
                         new Point(raster.getSampleModelTranslateX(),
                                   raster.getSampleModelTranslateY()));

        Object tdata = null;

        for (int i = startY; i < startY+height; i++)  {
            tdata = raster.getDataElements(startX,i,width,1,tdata);
            wr.setDataElements(startX,i,width,1, tdata);
        }
        return wr;
    }

    /**
     * Computes and returns an arbitrary region of the
     * <code>BufferedImage</code>.  The <code>Raster</code> returned is a
     * copy of the image data and is not updated if the image is
     * changed.
     * <p>
     *  计算并返回<code> BufferedImage </code>的任意区域。返回的<code> Raster </code>是图像数据的副本,如果更改图像,则不会更新。
     * 
     * 
     * @param rect the region of the <code>BufferedImage</code> to be
     * returned.
     * @return a <code>Raster</code> that is a copy of the image data of
     *          the specified region of the <code>BufferedImage</code>
     * @see #setData(Raster)
     */
    public Raster getData(Rectangle rect) {
        SampleModel sm = raster.getSampleModel();
        SampleModel nsm = sm.createCompatibleSampleModel(rect.width,
                                                         rect.height);
        WritableRaster wr = Raster.createWritableRaster(nsm,
                                                  rect.getLocation());
        int width = rect.width;
        int height = rect.height;
        int startX = rect.x;
        int startY = rect.y;

        Object tdata = null;

        for (int i = startY; i < startY+height; i++)  {
            tdata = raster.getDataElements(startX,i,width,1,tdata);
            wr.setDataElements(startX,i,width,1, tdata);
        }
        return wr;
    }

    /**
     * Computes an arbitrary rectangular region of the
     * <code>BufferedImage</code> and copies it into a specified
     * <code>WritableRaster</code>.  The region to be computed is
     * determined from the bounds of the specified
     * <code>WritableRaster</code>.  The specified
     * <code>WritableRaster</code> must have a
     * <code>SampleModel</code> that is compatible with this image.  If
     * <code>outRaster</code> is <code>null</code>,
     * an appropriate <code>WritableRaster</code> is created.
     * <p>
     *  计算<code> BufferedImage </code>的任意矩形区域,并将其复制到指定的<code> WritableRaster </code>中。
     * 要计算的区域从指定的<code> WritableRaster </code>的边界确定。
     * 指定的<code> WritableRaster </code>必须具有与此图片兼容的<code> SampleModel </code>。
     * 如果<code> outRaster </code>是<code> null </code>,则创建适当的<code> WritableRaster </code>。
     * 
     * 
     * @param outRaster a <code>WritableRaster</code> to hold the returned
     *          part of the image, or <code>null</code>
     * @return a reference to the supplied or created
     *          <code>WritableRaster</code>.
     */
    public WritableRaster copyData(WritableRaster outRaster) {
        if (outRaster == null) {
            return (WritableRaster) getData();
        }
        int width = outRaster.getWidth();
        int height = outRaster.getHeight();
        int startX = outRaster.getMinX();
        int startY = outRaster.getMinY();

        Object tdata = null;

        for (int i = startY; i < startY+height; i++)  {
            tdata = raster.getDataElements(startX,i,width,1,tdata);
            outRaster.setDataElements(startX,i,width,1, tdata);
        }

        return outRaster;
    }

  /**
     * Sets a rectangular region of the image to the contents of the
     * specified <code>Raster</code> <code>r</code>, which is
     * assumed to be in the same coordinate space as the
     * <code>BufferedImage</code>. The operation is clipped to the bounds
     * of the <code>BufferedImage</code>.
     * <p>
     *  将图像的矩形区域设置为指定的<code> Raster </code> <code> r </code>的内容,假设它与<code> BufferedImage </code> 。
     * 操作被剪切到<code> BufferedImage </code>的边界。
     * 
     * 
     * @param r the specified <code>Raster</code>
     * @see #getData
     * @see #getData(Rectangle)
    */
    public void setData(Raster r) {
        int width = r.getWidth();
        int height = r.getHeight();
        int startX = r.getMinX();
        int startY = r.getMinY();

        int[] tdata = null;

        // Clip to the current Raster
        Rectangle rclip = new Rectangle(startX, startY, width, height);
        Rectangle bclip = new Rectangle(0, 0, raster.width, raster.height);
        Rectangle intersect = rclip.intersection(bclip);
        if (intersect.isEmpty()) {
            return;
        }
        width = intersect.width;
        height = intersect.height;
        startX = intersect.x;
        startY = intersect.y;

        // remind use get/setDataElements for speed if Rasters are
        // compatible
        for (int i = startY; i < startY+height; i++)  {
            tdata = r.getPixels(startX,i,width,1,tdata);
            raster.setPixels(startX,i,width,1, tdata);
        }
    }


  /**
   * Adds a tile observer.  If the observer is already present,
   * it receives multiple notifications.
   * <p>
   *  添加图块观察器。如果观察者已经存在,它接收多个通知。
   * 
   * 
   * @param to the specified {@link TileObserver}
   */
    public void addTileObserver (TileObserver to) {
    }

  /**
   * Removes a tile observer.  If the observer was not registered,
   * nothing happens.  If the observer was registered for multiple
   * notifications, it is now registered for one fewer notification.
   * <p>
   * 删除瓦片观察器。如果观察者没有注册,什么也没有发生。如果观察者注册了多个通知,则现在注册的通知减少了一个。
   * 
   * 
   * @param to the specified <code>TileObserver</code>.
   */
    public void removeTileObserver (TileObserver to) {
    }

    /**
     * Returns whether or not a tile is currently checked out for writing.
     * <p>
     *  返回图块当前是否已签出写入。
     * 
     * 
     * @param tileX the x index of the tile.
     * @param tileY the y index of the tile.
     * @return <code>true</code> if the tile specified by the specified
     *          indices is checked out for writing; <code>false</code>
     *          otherwise.
     * @exception ArrayIndexOutOfBoundsException if both
     *          <code>tileX</code> and <code>tileY</code> are not equal
     *          to 0
     */
    public boolean isTileWritable (int tileX, int tileY) {
        if (tileX == 0 && tileY == 0) {
            return true;
        }
        throw new IllegalArgumentException("Only 1 tile in image");
    }

    /**
     * Returns an array of {@link Point} objects indicating which tiles
     * are checked out for writing.  Returns <code>null</code> if none are
     * checked out.
     * <p>
     *  返回一个{@link Point}对象数组,指示哪些图块被签出写入。如果没有检出,则返回<code> null </code>。
     * 
     * 
     * @return a <code>Point</code> array that indicates the tiles that
     *          are checked out for writing, or <code>null</code> if no
     *          tiles are checked out for writing.
     */
    public Point[] getWritableTileIndices() {
        Point[] p = new Point[1];
        p[0] = new Point(0, 0);

        return p;
    }

    /**
     * Returns whether or not any tile is checked out for writing.
     * Semantically equivalent to
     * <pre>
     * (getWritableTileIndices() != null).
     * </pre>
     * <p>
     *  返回是否检出任何磁贴写入。语义上等同于
     * <pre>
     *  (getWritableTileIndices()！= null)。
     * </pre>
     * 
     * @return <code>true</code> if any tile is checked out for writing;
     *          <code>false</code> otherwise.
     */
    public boolean hasTileWriters () {
        return true;
    }

  /**
   * Checks out a tile for writing.  All registered
   * <code>TileObservers</code> are notified when a tile goes from having
   * no writers to having one writer.
   * <p>
   *  检查一个瓷砖写。当图块从没有写入者到具有一个写入者时,通知所有注册的<code> TileObservers </code>。
   * 
   * 
   * @param tileX the x index of the tile
   * @param tileY the y index of the tile
   * @return a <code>WritableRaster</code> that is the tile, indicated by
   *            the specified indices, to be checked out for writing.
   */
    public WritableRaster getWritableTile (int tileX, int tileY) {
        return raster;
    }

  /**
   * Relinquishes permission to write to a tile.  If the caller
   * continues to write to the tile, the results are undefined.
   * Calls to this method should only appear in matching pairs
   * with calls to {@link #getWritableTile(int, int) getWritableTile(int, int)}.  Any other leads
   * to undefined results.  All registered <code>TileObservers</code>
   * are notified when a tile goes from having one writer to having no
   * writers.
   * <p>
   *  放弃写入图块的权限。如果调用程序继续写入磁贴,则结果未定义。
   * 对此方法的调用应该只出现在与{@link #getWritableTile(int,int)getWritableTile(int,int)}的调用匹配的对中。任何其他导致未定义的结果。
   * 当图块从具有一个写入者到没有写入者时,通知所有注册的<code> TileObservers </code>。
   * 
   * 
   * @param tileX the x index of the tile
   * @param tileY the y index of the tile
   */
    public void releaseWritableTile (int tileX, int tileY) {
    }

    /**
     * Returns the transparency.  Returns either OPAQUE, BITMASK,
     * or TRANSLUCENT.
     * <p>
     * 
     * @return the transparency of this <code>BufferedImage</code>.
     * @see Transparency#OPAQUE
     * @see Transparency#BITMASK
     * @see Transparency#TRANSLUCENT
     * @since 1.5
     */
    public int getTransparency() {
        return colorModel.getTransparency();
    }
}
