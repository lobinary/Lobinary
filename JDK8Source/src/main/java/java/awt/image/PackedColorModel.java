/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2011, Oracle and/or its affiliates. All rights reserved.
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

/**
 * The <code>PackedColorModel</code> class is an abstract
 * {@link ColorModel} class that works with pixel values which represent
 * color and alpha information as separate samples and which pack all
 * samples for a single pixel into a single int, short, or byte quantity.
 * This class can be used with an arbitrary {@link ColorSpace}.  The number of
 * color samples in the pixel values must be the same as the number of color
 * components in the <code>ColorSpace</code>.  There can be a single alpha
 * sample.  The array length is always 1 for those methods that use a
 * primitive array pixel representation of type <code>transferType</code>.
 * The transfer types supported are DataBuffer.TYPE_BYTE,
 * DataBuffer.TYPE_USHORT, and DataBuffer.TYPE_INT.
 * Color and alpha samples are stored in the single element of the array
 * in bits indicated by bit masks.  Each bit mask must be contiguous and
 * masks must not overlap.  The same masks apply to the single int
 * pixel representation used by other methods.  The correspondence of
 * masks and color/alpha samples is as follows:
 * <ul>
 * <li> Masks are identified by indices running from 0 through
 * {@link ColorModel#getNumComponents() getNumComponents}&nbsp;-&nbsp;1.
 * <li> The first
 * {@link ColorModel#getNumColorComponents() getNumColorComponents}
 * indices refer to color samples.
 * <li> If an alpha sample is present, it corresponds the last index.
 * <li> The order of the color indices is specified
 * by the <code>ColorSpace</code>.  Typically, this reflects the name of
 * the color space type (for example, TYPE_RGB), index 0
 * corresponds to red, index 1 to green, and index 2 to blue.
 * </ul>
 * <p>
 * The translation from pixel values to color/alpha components for
 * display or processing purposes is a one-to-one correspondence of
 * samples to components.
 * A <code>PackedColorModel</code> is typically used with image data
 * that uses masks to define packed samples.  For example, a
 * <code>PackedColorModel</code> can be used in conjunction with a
 * {@link SinglePixelPackedSampleModel} to construct a
 * {@link BufferedImage}.  Normally the masks used by the
 * {@link SampleModel} and the <code>ColorModel</code> would be the same.
 * However, if they are different, the color interpretation of pixel data is
 * done according to the masks of the <code>ColorModel</code>.
 * <p>
 * A single <code>int</code> pixel representation is valid for all objects
 * of this class since it is always possible to represent pixel values
 * used with this class in a single <code>int</code>.  Therefore, methods
 * that use this representation do not throw an
 * <code>IllegalArgumentException</code> due to an invalid pixel value.
 * <p>
 * A subclass of <code>PackedColorModel</code> is {@link DirectColorModel},
 * which is similar to an X11 TrueColor visual.
 *
 * <p>
 *  <code> PackedColorModel </code>类是一个抽象的{@link ColorModel}类,它使用表示颜色和alpha信息的像素值作为单独的样本,并将单个像素的所有样本打包为单
 * 个int,短或字节数量。
 * 这个类可以与任意的{@link ColorSpace}一起使用。像素值中的颜色样本数必须与<code> ColorSpace </code>中的颜色分量数相同。可以有单个α样本。
 * 对于使用<code> transferType </code>类型的基本数组像素表示的那些方法,数组长度始终为1。
 * 支持的传输类型是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT和DataBuffer.TYPE_INT。颜色和α样本以由位掩码指示的位存储在阵列的单个元素中。
 * 每个位掩码必须是连续的,并且掩码不能重叠。相同的掩码应用于由其他方法使用的单个int像素表示。掩模和颜色/α样本的对应如下：。
 * <ul>
 * <li>掩码由从0到{@link ColorModel#getNumComponents()getNumComponents}&nbsp;  - &nbsp; 1的索引标识。
 *  <li>第一个{@link ColorModel#getNumColorComponents()getNumColorComponents}索引指的是颜色样本。
 *  <li>如果存在Alpha样本,则它对应于最后一个索引。 <li>颜色索引的顺序由<code> ColorSpace </code>指定。
 * 通常,这反映了颜色空间类型的名称(例如,TYPE_RGB),索引0对应于红色,索引1对应于绿色,索引2对应于蓝色。
 * </ul>
 * <p>
 *  从像素值到颜色/阿尔法分量的显示或处理目的的转换是样本与分量的一对一对应。 <code> PackedColorModel </code>通常与使用蒙版来定义打包样本的图像数据一起使用。
 * 例如,<code> PackedColorModel </code>可以与{@link SinglePixelPackedSampleModel}结合使用,以构造一个{@link BufferedImage}
 * 。
 *  从像素值到颜色/阿尔法分量的显示或处理目的的转换是样本与分量的一对一对应。 <code> PackedColorModel </code>通常与使用蒙版来定义打包样本的图像数据一起使用。
 * 通常,{@link SampleModel}和<code> ColorModel </code>使用的掩码将是相同的。
 * 然而,如果它们不同,则根据<code> ColorModel </code>的掩码来进行像素数据的颜色解释。
 * <p>
 * 单个<code> int </code>像素表示对于该类的所有对象是有效的,因为总是可以在单个<code> int </code>中表示与该类一起使用的像素值。
 * 因此,由于无效的像素值,使用此表示的方法不会抛出<code> IllegalArgumentException </code>。
 * <p>
 *  <code> PackedColorModel </code>的子类是{@link DirectColorModel},类似于X11 TrueColor视觉。
 * 
 * 
 * @see DirectColorModel
 * @see SinglePixelPackedSampleModel
 * @see BufferedImage
 */

public abstract class PackedColorModel extends ColorModel {
    int[] maskArray;
    int[] maskOffsets;
    float[] scaleFactors;

    /**
     * Constructs a <code>PackedColorModel</code> from a color mask array,
     * which specifies which bits in an <code>int</code> pixel representation
     * contain each of the color samples, and an alpha mask.  Color
     * components are in the specified <code>ColorSpace</code>.  The length of
     * <code>colorMaskArray</code> should be the number of components in
     * the <code>ColorSpace</code>.  All of the bits in each mask
     * must be contiguous and fit in the specified number of least significant
     * bits of an <code>int</code> pixel representation.  If the
     * <code>alphaMask</code> is 0, there is no alpha.  If there is alpha,
     * the <code>boolean</code> <code>isAlphaPremultiplied</code> specifies
     * how to interpret color and alpha samples in pixel values.  If the
     * <code>boolean</code> is <code>true</code>, color samples are assumed
     * to have been multiplied by the alpha sample.  The transparency,
     * <code>trans</code>, specifies what alpha values can be represented
     * by this color model.  The transfer type is the type of primitive
     * array used to represent pixel values.
     * <p>
     *  从颜色掩码数组构造一个<code> PackedColorModel </code>,它指定<code> int </code>像素表示中的哪些位包含每个颜色样本和一个alpha蒙版。
     * 颜色组件在指定的<code> ColorSpace </code>中。
     *  <code> colorMaskArray </code>的长度应该是<code> ColorSpace </code>中的组件数。
     * 每个掩码中的所有位必须是连续的并且适合于<code> int </code>像素表示的指定数量的最低有效位。如果<code> alphaMask </code>为0,则没有alpha。
     * 如果有alpha,<code> boolean </code> <code> isAlphaPremultiplied </code>指定如何解释像素值中的颜色和alpha样本。
     * 如果<code> boolean </code>是<code> true </code>,则颜色样本假定已经乘以alpha样本。
     * 透明度<code> trans </code>指定此颜色模型可以表示什么alpha值。传输类型是用于表示像素值的基本数组的类型。
     * 
     * 
     * @param space the specified <code>ColorSpace</code>
     * @param bits the number of bits in the pixel values
     * @param colorMaskArray array that specifies the masks representing
     *         the bits of the pixel values that represent the color
     *         components
     * @param alphaMask specifies the mask representing
     *         the bits of the pixel values that represent the alpha
     *         component
     * @param isAlphaPremultiplied <code>true</code> if color samples are
     *        premultiplied by the alpha sample; <code>false</code> otherwise
     * @param trans specifies the alpha value that can be represented by
     *        this color model
     * @param transferType the type of array used to represent pixel values
     * @throws IllegalArgumentException if <code>bits</code> is less than
     *         1 or greater than 32
     */
    public PackedColorModel (ColorSpace space, int bits,
                             int[] colorMaskArray, int alphaMask,
                             boolean isAlphaPremultiplied,
                             int trans, int transferType) {
        super(bits, PackedColorModel.createBitsArray(colorMaskArray,
                                                     alphaMask),
              space, (alphaMask == 0 ? false : true),
              isAlphaPremultiplied, trans, transferType);
        if (bits < 1 || bits > 32) {
            throw new IllegalArgumentException("Number of bits must be between"
                                               +" 1 and 32.");
        }
        maskArray   = new int[numComponents];
        maskOffsets = new int[numComponents];
        scaleFactors = new float[numComponents];

        for (int i=0; i < numColorComponents; i++) {
            // Get the mask offset and #bits
            DecomposeMask(colorMaskArray[i], i, space.getName(i));
        }
        if (alphaMask != 0) {
            DecomposeMask(alphaMask, numColorComponents, "alpha");
            if (nBits[numComponents-1] == 1) {
                transparency = Transparency.BITMASK;
            }
        }
    }

    /**
     * Constructs a <code>PackedColorModel</code> from the specified
     * masks which indicate which bits in an <code>int</code> pixel
     * representation contain the alpha, red, green and blue color samples.
     * Color components are in the specified <code>ColorSpace</code>, which
     * must be of type ColorSpace.TYPE_RGB.  All of the bits in each
     * mask must be contiguous and fit in the specified number of
     * least significant bits of an <code>int</code> pixel representation.  If
     * <code>amask</code> is 0, there is no alpha.  If there is alpha,
     * the <code>boolean</code> <code>isAlphaPremultiplied</code>
     * specifies how to interpret color and alpha samples
     * in pixel values.  If the <code>boolean</code> is <code>true</code>,
     * color samples are assumed to have been multiplied by the alpha sample.
     * The transparency, <code>trans</code>, specifies what alpha values
     * can be represented by this color model.
     * The transfer type is the type of primitive array used to represent
     * pixel values.
     * <p>
     * 从指定的掩码中构造一个<code> PackedColorModel </code>,表示<code> int </code>像素表示中的哪些位包含alpha,红,绿和蓝色样本。
     * 颜色组件在指定的<code> ColorSpace </code>中,它必须是ColorSpace.TYPE_RGB类型。
     * 每个掩码中的所有位必须是连续的并且适合于<code> int </code>像素表示的指定数量的最低有效位。如果<code> amask </code>为0,则没有alpha。
     * 如果有alpha,<code> boolean </code> <code> isAlphaPremultiplied </code>指定如何解释像素值中的颜色和alpha样本。
     * 如果<code> boolean </code>是<code> true </code>,则颜色样本假定已经乘以alpha样本。
     * 透明度<code> trans </code>指定此颜色模型可以表示什么alpha值。传输类型是用于表示像素值的基本数组的类型。
     * 
     * 
     * @param space the specified <code>ColorSpace</code>
     * @param bits the number of bits in the pixel values
     * @param rmask specifies the mask representing
     *         the bits of the pixel values that represent the red
     *         color component
     * @param gmask specifies the mask representing
     *         the bits of the pixel values that represent the green
     *         color component
     * @param bmask specifies the mask representing
     *         the bits of the pixel values that represent
     *         the blue color component
     * @param amask specifies the mask representing
     *         the bits of the pixel values that represent
     *         the alpha component
     * @param isAlphaPremultiplied <code>true</code> if color samples are
     *        premultiplied by the alpha sample; <code>false</code> otherwise
     * @param trans specifies the alpha value that can be represented by
     *        this color model
     * @param transferType the type of array used to represent pixel values
     * @throws IllegalArgumentException if <code>space</code> is not a
     *         TYPE_RGB space
     * @see ColorSpace
     */
    public PackedColorModel(ColorSpace space, int bits, int rmask, int gmask,
                            int bmask, int amask,
                            boolean isAlphaPremultiplied,
                            int trans, int transferType) {
        super (bits, PackedColorModel.createBitsArray(rmask, gmask, bmask,
                                                      amask),
               space, (amask == 0 ? false : true),
               isAlphaPremultiplied, trans, transferType);

        if (space.getType() != ColorSpace.TYPE_RGB) {
            throw new IllegalArgumentException("ColorSpace must be TYPE_RGB.");
        }
        maskArray = new int[numComponents];
        maskOffsets = new int[numComponents];
        scaleFactors = new float[numComponents];

        DecomposeMask(rmask, 0, "red");

        DecomposeMask(gmask, 1, "green");

        DecomposeMask(bmask, 2, "blue");

        if (amask != 0) {
            DecomposeMask(amask, 3, "alpha");
            if (nBits[3] == 1) {
                transparency = Transparency.BITMASK;
            }
        }
    }

    /**
     * Returns the mask indicating which bits in a pixel
     * contain the specified color/alpha sample.  For color
     * samples, <code>index</code> corresponds to the placement of color
     * sample names in the color space.  Thus, an <code>index</code>
     * equal to 0 for a CMYK ColorSpace would correspond to
     * Cyan and an <code>index</code> equal to 1 would correspond to
     * Magenta.  If there is alpha, the alpha <code>index</code> would be:
     * <pre>
     *      alphaIndex = numComponents() - 1;
     * </pre>
     * <p>
     *  返回掩码,指示像素中的哪些位包含指定的颜色/ alpha样本。对于颜色样本,<code> index </code>对应于颜色样本名称在颜色空间中的位置。
     * 因此,对于CMYK ColorSpace等于0的<code> index </code>对应于Cyan,等于1的<code> index </code>对应于Magenta。
     * 如果有alpha,alpha <code> index </code>将是：。
     * <pre>
     *  alphaIndex = numComponents() -  1;
     * </pre>
     * 
     * @param index the specified color or alpha sample
     * @return the mask, which indicates which bits of the <code>int</code>
     *         pixel representation contain the color or alpha sample specified
     *         by <code>index</code>.
     * @throws ArrayIndexOutOfBoundsException if <code>index</code> is
     *         greater than the number of components minus 1 in this
     *         <code>PackedColorModel</code> or if <code>index</code> is
     *         less than zero
     */
    final public int getMask(int index) {
        return maskArray[index];
    }

    /**
     * Returns a mask array indicating which bits in a pixel
     * contain the color and alpha samples.
     * <p>
     *  返回掩码数组,指示像素中的哪些位包含颜色和alpha样本。
     * 
     * 
     * @return the mask array , which indicates which bits of the
     *         <code>int</code> pixel
     *         representation contain the color or alpha samples.
     */
    final public int[] getMasks() {
        return (int[]) maskArray.clone();
    }

    /*
     * A utility function to compute the mask offset and scalefactor,
     * store these and the mask in instance arrays, and verify that
     * the mask fits in the specified pixel size.
     * <p>
     * 用于计算掩模偏移和比例因子的效用函数,将这些和掩模存储在实例数组中,并验证掩模是否符合指定的像素大小。
     * 
     */
    private void DecomposeMask(int mask,  int idx, String componentName) {
        int off = 0;
        int count = nBits[idx];

        // Store the mask
        maskArray[idx]   = mask;

        // Now find the shift
        if (mask != 0) {
            while ((mask & 1) == 0) {
                mask >>>= 1;
                off++;
            }
        }

        if (off + count > pixel_bits) {
            throw new IllegalArgumentException(componentName + " mask "+
                                        Integer.toHexString(maskArray[idx])+
                                               " overflows pixel (expecting "+
                                               pixel_bits+" bits");
        }

        maskOffsets[idx] = off;
        if (count == 0) {
            // High enough to scale any 0-ff value down to 0.0, but not
            // high enough to get Infinity when scaling back to pixel bits
            scaleFactors[idx] = 256.0f;
        } else {
            scaleFactors[idx] = 255.0f / ((1 << count) - 1);
        }

    }

    /**
     * Creates a <code>SampleModel</code> with the specified width and
     * height that has a data layout compatible with this
     * <code>ColorModel</code>.
     * <p>
     *  创建一个具有指定宽度和高度的<code> SampleModel </code>,其数据布局与此<code> ColorModel </code>兼容。
     * 
     * 
     * @param w the width (in pixels) of the region of the image data
     *          described
     * @param h the height (in pixels) of the region of the image data
     *          described
     * @return the newly created <code>SampleModel</code>.
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     * @see SampleModel
     */
    public SampleModel createCompatibleSampleModel(int w, int h) {
        return new SinglePixelPackedSampleModel(transferType, w, h,
                                                maskArray);
    }

    /**
     * Checks if the specified <code>SampleModel</code> is compatible
     * with this <code>ColorModel</code>.  If <code>sm</code> is
     * <code>null</code>, this method returns <code>false</code>.
     * <p>
     *  检查指定的<code> SampleModel </code>是否与此<code> ColorModel </code>兼容。
     * 如果<code> sm </code>是<code> null </code>,则此方法返回<code> false </code>。
     * 
     * 
     * @param sm the specified <code>SampleModel</code>,
     * or <code>null</code>
     * @return <code>true</code> if the specified <code>SampleModel</code>
     *         is compatible with this <code>ColorModel</code>;
     *         <code>false</code> otherwise.
     * @see SampleModel
     */
    public boolean isCompatibleSampleModel(SampleModel sm) {
        if (! (sm instanceof SinglePixelPackedSampleModel)) {
            return false;
        }

        // Must have the same number of components
        if (numComponents != sm.getNumBands()) {
            return false;
        }

        // Transfer type must be the same
        if (sm.getTransferType() != transferType) {
            return false;
        }

        SinglePixelPackedSampleModel sppsm = (SinglePixelPackedSampleModel) sm;
        // Now compare the specific masks
        int[] bitMasks = sppsm.getBitMasks();
        if (bitMasks.length != maskArray.length) {
            return false;
        }

        /* compare 'effective' masks only, i.e. only part of the mask
         * which fits the capacity of the transfer type.
         * <p>
         *  其适合于转印类型的容量。
         * 
         */
        int maxMask = (int)((1L << DataBuffer.getDataTypeSize(transferType)) - 1);
        for (int i=0; i < bitMasks.length; i++) {
            if ((maxMask & bitMasks[i]) != (maxMask & maskArray[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a {@link WritableRaster} representing the alpha channel of
     * an image, extracted from the input <code>WritableRaster</code>.
     * This method assumes that <code>WritableRaster</code> objects
     * associated with this <code>ColorModel</code> store the alpha band,
     * if present, as the last band of image data.  Returns <code>null</code>
     * if there is no separate spatial alpha channel associated with this
     * <code>ColorModel</code>.  This method creates a new
     * <code>WritableRaster</code>, but shares the data array.
     * <p>
     *  返回表示从输入<code> WritableRaster </code>中提取的图像的Alpha通道的{@link WritableRaster}。
     * 该方法假定与此<code> ColorModel </code>相关联的<code> WritableRaster </code>对象将alpha带(如果存在)存储为图像数据的最后一个带。
     * 如果没有与此<code> ColorModel </code>关联的单独的空间Alpha通道,则返回<code> null </code>。
     * 此方法创建一个新的<code> WritableRaster </code>,但共享数据数组。
     * 
     * @param raster a <code>WritableRaster</code> containing an image
     * @return a <code>WritableRaster</code> that represents the alpha
     *         channel of the image contained in <code>raster</code>.
     */
    public WritableRaster getAlphaRaster(WritableRaster raster) {
        if (hasAlpha() == false) {
            return null;
        }

        int x = raster.getMinX();
        int y = raster.getMinY();
        int[] band = new int[1];
        band[0] = raster.getNumBands() - 1;
        return raster.createWritableChild(x, y, raster.getWidth(),
                                          raster.getHeight(), x, y,
                                          band);
    }

    /**
     * Tests if the specified <code>Object</code> is an instance
     * of <code>PackedColorModel</code> and equals this
     * <code>PackedColorModel</code>.
     * <p>
     * 
     * 
     * @param obj the <code>Object</code> to test for equality
     * @return <code>true</code> if the specified <code>Object</code>
     * is an instance of <code>PackedColorModel</code> and equals this
     * <code>PackedColorModel</code>; <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof PackedColorModel)) {
            return false;
        }

        if (!super.equals(obj)) {
            return false;
        }

        PackedColorModel cm = (PackedColorModel) obj;
        int numC = cm.getNumComponents();
        if (numC != numComponents) {
            return false;
        }
        for(int i=0; i < numC; i++) {
            if (maskArray[i] != cm.getMask(i)) {
                return false;
            }
        }
        return true;
    }

    private final static int[] createBitsArray(int[]colorMaskArray,
                                               int alphaMask) {
        int numColors = colorMaskArray.length;
        int numAlpha = (alphaMask == 0 ? 0 : 1);
        int[] arr = new int[numColors+numAlpha];
        for (int i=0; i < numColors; i++) {
            arr[i] = countBits(colorMaskArray[i]);
            if (arr[i] < 0) {
                throw new IllegalArgumentException("Noncontiguous color mask ("
                                     + Integer.toHexString(colorMaskArray[i])+
                                     "at index "+i);
            }
        }
        if (alphaMask != 0) {
            arr[numColors] = countBits(alphaMask);
            if (arr[numColors] < 0) {
                throw new IllegalArgumentException("Noncontiguous alpha mask ("
                                     + Integer.toHexString(alphaMask));
            }
        }
        return arr;
    }

    private final static int[] createBitsArray(int rmask, int gmask, int bmask,
                                         int amask) {
        int[] arr = new int[3 + (amask == 0 ? 0 : 1)];
        arr[0] = countBits(rmask);
        arr[1] = countBits(gmask);
        arr[2] = countBits(bmask);
        if (arr[0] < 0) {
            throw new IllegalArgumentException("Noncontiguous red mask ("
                                     + Integer.toHexString(rmask));
        }
        else if (arr[1] < 0) {
            throw new IllegalArgumentException("Noncontiguous green mask ("
                                     + Integer.toHexString(gmask));
        }
        else if (arr[2] < 0) {
            throw new IllegalArgumentException("Noncontiguous blue mask ("
                                     + Integer.toHexString(bmask));
        }
        if (amask != 0) {
            arr[3] = countBits(amask);
            if (arr[3] < 0) {
                throw new IllegalArgumentException("Noncontiguous alpha mask ("
                                     + Integer.toHexString(amask));
            }
        }
        return arr;
    }

    private final static int countBits(int mask) {
        int count = 0;
        if (mask != 0) {
            while ((mask & 1) == 0) {
                mask >>>= 1;
            }
            while ((mask & 1) == 1) {
                mask >>>= 1;
                count++;
            }
        }
        if (mask != 0) {
            return -1;
        }
        return count;
    }

}
