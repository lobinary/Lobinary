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


/**
 * This class defines a lookup table object.  The output of a
 * lookup operation using an object of this class is interpreted
 * as an unsigned byte quantity.  The lookup table contains byte
 * data arrays for one or more bands (or components) of an image,
 * and it contains an offset which will be subtracted from the
 * input values before indexing the arrays.  This allows an array
 * smaller than the native data size to be provided for a
 * constrained input.  If there is only one array in the lookup
 * table, it will be applied to all bands.
 *
 * <p>
 *  这个类定义了一个查找表对象。使用此类的对象的查找操作的输出被解释为无符号字节数量。查找表包含用于图像的一个或多个带(或分量)的字节数据阵列,并且其包含将在索引阵列之前从输入值中减去的偏移。
 * 这允许为受限输入提供比本机数据大小更小的数组。如果查找表中只有一个数组,则它将应用于所有波段。
 * 
 * 
 * @see ShortLookupTable
 * @see LookupOp
 */
public class ByteLookupTable extends LookupTable {

    /**
     * Constants
     * <p>
     *  常量
     * 
     */

    byte data[][];

    /**
     * Constructs a ByteLookupTable object from an array of byte
     * arrays representing a lookup table for each
     * band.  The offset will be subtracted from input
     * values before indexing into the arrays.  The number of
     * bands is the length of the data argument.  The
     * data array for each band is stored as a reference.
     * <p>
     *  从表示每个频段的查找表的字节数组数组构造ByteLookupTable对象。在索引到数组之前,将从输入值中减去偏移量。带的数量是数据自变量的长度。每个频带的数据阵列被存储为参考。
     * 
     * 
     * @param offset the value subtracted from the input values
     *        before indexing into the arrays
     * @param data an array of byte arrays representing a lookup
     *        table for each band
     * @throws IllegalArgumentException if <code>offset</code> is
     *         is less than 0 or if the length of <code>data</code>
     *         is less than 1
     */
    public ByteLookupTable(int offset, byte data[][]) {
        super(offset,data.length);
        numComponents = data.length;
        numEntries    = data[0].length;
        this.data = new byte[numComponents][];
        // Allocate the array and copy the data reference
        for (int i=0; i < numComponents; i++) {
            this.data[i] = data[i];
        }
    }

    /**
     * Constructs a ByteLookupTable object from an array
     * of bytes representing a lookup table to be applied to all
     * bands.  The offset will be subtracted from input
     * values before indexing into the array.
     * The data array is stored as a reference.
     * <p>
     *  从字节数组构造一个ByteLookupTable对象,表示要应用于所有波段的查找表。在索引到数组之前,将从输入值中减去偏移量。数据数组被存储为参考。
     * 
     * 
     * @param offset the value subtracted from the input values
     *        before indexing into the array
     * @param data an array of bytes
     * @throws IllegalArgumentException if <code>offset</code> is
     *         is less than 0 or if the length of <code>data</code>
     *         is less than 1
     */
    public ByteLookupTable(int offset, byte data[]) {
        super(offset,data.length);
        numComponents = 1;
        numEntries    = data.length;
        this.data = new byte[1][];
        this.data[0] = data;
    }

    /**
     * Returns the lookup table data by reference.  If this ByteLookupTable
     * was constructed using a single byte array, the length of the returned
     * array is one.
     * <p>
     *  通过引用返回查找表数据。如果此ByteLookupTable是使用单字节数组构造的,则返回的数组的长度为1。
     * 
     * 
     * @return the data array of this <code>ByteLookupTable</code>.
     */
    public final byte[][] getTable(){
        return data;
    }

    /**
     * Returns an array of samples of a pixel, translated with the lookup
     * table. The source and destination array can be the same array.
     * Array <code>dst</code> is returned.
     *
     * <p>
     * 返回像素的样本数组,使用查找表进行翻译。源和目标数组可以是同一个数组。返回数组<code> dst </code>。
     * 
     * 
     * @param src the source array.
     * @param dst the destination array. This array must be at least as
     *         long as <code>src</code>.  If <code>dst</code> is
     *         <code>null</code>, a new array will be allocated having the
     *         same length as <code>src</code>.
     * @return the array <code>dst</code>, an <code>int</code> array of
     *         samples.
     * @exception ArrayIndexOutOfBoundsException if <code>src</code> is
     *            longer than <code>dst</code> or if for any element
     *            <code>i</code> of <code>src</code>,
     *            <code>src[i]-offset</code> is either less than zero or
     *            greater than or equal to the length of the lookup table
     *            for any band.
     */
    public int[] lookupPixel(int[] src, int[] dst){
        if (dst == null) {
            // Need to alloc a new destination array
            dst = new int[src.length];
        }

        if (numComponents == 1) {
            // Apply one LUT to all bands
            for (int i=0; i < src.length; i++) {
                int s = src[i] - offset;
                if (s < 0) {
                    throw new ArrayIndexOutOfBoundsException("src["+i+
                                                             "]-offset is "+
                                                             "less than zero");
                }
                dst[i] = (int) data[0][s];
            }
        }
        else {
            for (int i=0; i < src.length; i++) {
                int s = src[i] - offset;
                if (s < 0) {
                    throw new ArrayIndexOutOfBoundsException("src["+i+
                                                             "]-offset is "+
                                                             "less than zero");
                }
                dst[i] = (int) data[i][s];
            }
        }
        return dst;
    }

    /**
     * Returns an array of samples of a pixel, translated with the lookup
     * table. The source and destination array can be the same array.
     * Array <code>dst</code> is returned.
     *
     * <p>
     *  返回像素的样本数组,使用查找表进行翻译。源和目标数组可以是同一个数组。返回数组<code> dst </code>。
     * 
     * @param src the source array.
     * @param dst the destination array. This array must be at least as
     *         long as <code>src</code>.  If <code>dst</code> is
     *         <code>null</code>, a new array will be allocated having the
     *         same length as <code>src</code>.
     * @return the array <code>dst</code>, an <code>int</code> array of
     *         samples.
     * @exception ArrayIndexOutOfBoundsException if <code>src</code> is
     *            longer than <code>dst</code> or if for any element
     *            <code>i</code> of <code>src</code>,
     *            {@code (src[i]&0xff)-offset} is either less than
     *            zero or greater than or equal to the length of the
     *            lookup table for any band.
     */
    public byte[] lookupPixel(byte[] src, byte[] dst){
        if (dst == null) {
            // Need to alloc a new destination array
            dst = new byte[src.length];
        }

        if (numComponents == 1) {
            // Apply one LUT to all bands
            for (int i=0; i < src.length; i++) {
                int s = (src[i]&0xff) - offset;
                if (s < 0) {
                    throw new ArrayIndexOutOfBoundsException("src["+i+
                                                             "]-offset is "+
                                                             "less than zero");
                }
                dst[i] = data[0][s];
            }
        }
        else {
            for (int i=0; i < src.length; i++) {
                int s = (src[i]&0xff) - offset;
                if (s < 0) {
                    throw new ArrayIndexOutOfBoundsException("src["+i+
                                                             "]-offset is "+
                                                             "less than zero");
                }
                dst[i] = data[i][s];
            }
        }
        return dst;
    }

}
