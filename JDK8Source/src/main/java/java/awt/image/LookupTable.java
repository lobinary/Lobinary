/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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


/**
 * This abstract class defines a lookup table object.  ByteLookupTable
 * and ShortLookupTable are subclasses, which
 * contain byte and short data, respectively.  A lookup table
 * contains data arrays for one or more bands (or components) of an image
 * (for example, separate arrays for R, G, and B),
 * and it contains an offset which will be subtracted from the
 * input values before indexing into the arrays.  This allows an array
 * smaller than the native data size to be provided for a
 * constrained input.  If there is only one array in the lookup
 * table, it will be applied to all bands.  All arrays must be the
 * same size.
 *
 * <p>
 *  这个抽象类定义了一个查找表对象。 ByteLookupTable和ShortLookupTable是子类,分别包含字节和短数据。
 * 查找表包含用于图像的一个或多个带(或分量)的数据阵列(例如,用于R,G和B的单独的阵列),并且它包含将在索引到索引之前从输入值中减去的偏移数组。这允许为受限输入提供比本机数据大小更小的数组。
 * 如果查找表中只有一个数组,则它将应用于所有波段。所有数组必须大小相同。
 * 
 * 
 * @see ByteLookupTable
 * @see ShortLookupTable
 * @see LookupOp
 */
public abstract class LookupTable extends Object{

    /**
     * Constants
     * <p>
     *  常量
     * 
     */

    int  numComponents;
    int  offset;
    int  numEntries;

    /**
     * Constructs a new LookupTable from the number of components and an offset
     * into the lookup table.
     * <p>
     *  根据组件数量和查找表中的偏移量构造新的LookupTable。
     * 
     * 
     * @param offset the offset to subtract from input values before indexing
     *        into the data arrays for this <code>LookupTable</code>
     * @param numComponents the number of data arrays in this
     *        <code>LookupTable</code>
     * @throws IllegalArgumentException if <code>offset</code> is less than 0
     *         or if <code>numComponents</code> is less than 1
     */
    protected LookupTable(int offset, int numComponents) {
        if (offset < 0) {
            throw new
                IllegalArgumentException("Offset must be greater than 0");
        }
        if (numComponents < 1) {
            throw new IllegalArgumentException("Number of components must "+
                                               " be at least 1");
        }
        this.numComponents = numComponents;
        this.offset = offset;
    }

    /**
     * Returns the number of components in the lookup table.
     * <p>
     *  返回查找表中的组件数。
     * 
     * 
     * @return the number of components in this <code>LookupTable</code>.
     */
    public int getNumComponents() {
        return numComponents;
    }

    /**
     * Returns the offset.
     * <p>
     *  返回偏移量。
     * 
     * 
     * @return the offset of this <code>LookupTable</code>.
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Returns an <code>int</code> array of components for
     * one pixel.  The <code>dest</code> array contains the
     * result of the lookup and is returned.  If dest is
     * <code>null</code>, a new array is allocated.  The
     * source and destination can be equal.
     * <p>
     *  返回一个像素的<code> int </code>组件数组。 <code> dest </code>数组包含lookup的结果,并返回。
     * 如果dest是<code> null </code>,则会分配一个新数组。源和目标可以相等。
     * 
     * @param src the source array of components of one pixel
     * @param dest the destination array of components for one pixel,
     *        translated with this <code>LookupTable</code>
     * @return an <code>int</code> array of components for one
     *         pixel.
     */
    public abstract int[] lookupPixel(int[] src, int[] dest);

}
