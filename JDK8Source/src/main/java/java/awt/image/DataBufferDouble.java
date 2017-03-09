/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
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

import static sun.java2d.StateTrackable.State.*;

/**
 * This class extends <code>DataBuffer</code> and stores data internally
 * in <code>double</code> form.
 * <p>
 * <a name="optimizations">
 * Note that some implementations may function more efficiently
 * if they can maintain control over how the data for an image is
 * stored.
 * For example, optimizations such as caching an image in video
 * memory require that the implementation track all modifications
 * to that data.
 * Other implementations may operate better if they can store the
 * data in locations other than a Java array.
 * To maintain optimum compatibility with various optimizations
 * it is best to avoid constructors and methods which expose the
 * underlying storage as a Java array as noted below in the
 * documentation for those methods.
 * </a>
 *
 * <p>
 *  这个类扩展了<code> DataBuffer </code>,并在<code> double </code>形式内部存储数据。
 * <p>
 * <a name="optimizations">
 *  注意,如果一些实现可以保持对如何存储图像的数据的控制,则它们可以更有效地工作。例如,诸如在视频存储器中高速缓存图像的优化需要实现跟踪对该数据的所有修改。
 * 如果其他实现可以将数据存储在除Java数组之外的位置中,则可以更好地操作。为了保持与各种优化的最佳兼容性,最好避免将底层存储暴露为Java数组的构造函数和方法,如下面在这些方法的文档中所述。
 * </a>
 * 
 * 
 * @since 1.4
 */

public final class DataBufferDouble extends DataBuffer {

    /** The array of data banks. */
    double bankdata[][];

    /** A reference to the default data bank. */
    double data[];

    /**
     * Constructs a <code>double</code>-based <code>DataBuffer</code>
     * with a specified size.
     *
     * <p>
     *  构造具有指定大小的<code> double </code>  - 基于<code> DataBuffer </code>。
     * 
     * 
     * @param size The number of elements in the <code>DataBuffer</code>.
     */
    public DataBufferDouble(int size) {
        super(STABLE, TYPE_DOUBLE, size);
        data = new double[size];
        bankdata = new double[1][];
        bankdata[0] = data;
    }

    /**
     * Constructs a <code>double</code>-based <code>DataBuffer</code>
     * with a specified number of banks, all of which are of a
     * specified size.
     *
     * <p>
     *  构造一个<code> double </code>  - 基于<code> DataBuffer </code>的指定数量的bank,所有这些都是指定大小。
     * 
     * 
     * @param size The number of elements in each bank of the
     *        <code>DataBuffer</code>.
     * @param numBanks The number of banks in the <code>DataBuffer</code>.
     */
    public DataBufferDouble(int size, int numBanks) {
        super(STABLE, TYPE_DOUBLE, size, numBanks);
        bankdata = new double[numBanks][];
        for (int i= 0; i < numBanks; i++) {
            bankdata[i] = new double[size];
        }
        data = bankdata[0];
    }

    /**
     * Constructs a <code>double</code>-based <code>DataBuffer</code>
     * with the specified data array.  Only the first
     * <code>size</code> elements are available for use by this
     * <code>DataBuffer</code>.  The array must be large enough to
     * hold <code>size</code> elements.
     * <p>
     * Note that {@code DataBuffer} objects created by this constructor
     * may be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  使用指定的数据数组构造<code> double </code> -based <code> DataBuffer </code>。
     * 只有第一个<code> size </code>元素可供此<code> DataBuffer </code>使用。数组必须足够大以容纳<code> size </code>元素。
     * <p>
     * 请注意,由此构造函数创建的{@code DataBuffer}对象可能与某些实施所使用的<a href="#optimizations">性能优化</a>不兼容(例如缓存视频内存中的关联图像)。
     * 
     * 
     * @param dataArray An array of <code>double</code>s to be used as the
     *                  first and only bank of this <code>DataBuffer</code>.
     * @param size The number of elements of the array to be used.
     */
    public DataBufferDouble(double dataArray[], int size) {
        super(UNTRACKABLE, TYPE_DOUBLE, size);
        data = dataArray;
        bankdata = new double[1][];
        bankdata[0] = data;
    }

    /**
     * Constructs a <code>double</code>-based <code>DataBuffer</code>
     * with the specified data array.  Only the elements between
     * <code>offset</code> and <code>offset + size - 1</code> are
     * available for use by this <code>DataBuffer</code>.  The array
     * must be large enough to hold <code>offset + size</code> elements.
     * <p>
     * Note that {@code DataBuffer} objects created by this constructor
     * may be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  使用指定的数据数组构造<code> double </code> -based <code> DataBuffer </code>。
     * 只有<code> offset </code>和<code> offset + size  -  1 </code>之间的元素可供此<code> DataBuffer </code>使用。
     * 数组必须足够大以容纳<code> offset + size </code>元素。
     * <p>
     *  请注意,由此构造函数创建的{@code DataBuffer}对象可能与某些实施所使用的<a href="#optimizations">性能优化</a>不兼容(例如缓存视频内存中的关联图像)。
     * 
     * 
     * @param dataArray An array of <code>double</code>s to be used as the
     *                  first and only bank of this <code>DataBuffer</code>.
     * @param size The number of elements of the array to be used.
     * @param offset The offset of the first element of the array
     *               that will be used.
     */
    public DataBufferDouble(double dataArray[], int size, int offset) {
        super(UNTRACKABLE, TYPE_DOUBLE, size, 1, offset);
        data = dataArray;
        bankdata = new double[1][];
        bankdata[0] = data;
    }

    /**
     * Constructs a <code>double</code>-based <code>DataBuffer</code>
     * with the specified data arrays.  Only the first
     * <code>size</code> elements of each array are available for use
     * by this <code>DataBuffer</code>.  The number of banks will be
     * equal <code>to dataArray.length</code>.
     * <p>
     * Note that {@code DataBuffer} objects created by this constructor
     * may be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  使用指定的数据数组构造<code> double </code> -based <code> DataBuffer </code>。
     * 只有每个数组的第一个<code> size </code>元素可供此<code> DataBuffer </code>使用。
     * 存储区数将等于<code>到dataArray.length </code>。
     * <p>
     *  请注意,由此构造函数创建的{@code DataBuffer}对象可能与某些实施所使用的<a href="#optimizations">性能优化</a>不兼容(例如缓存视频内存中的关联图像)。
     * 
     * 
     * @param dataArray An array of arrays of <code>double</code>s to be
     *        used as the banks of this <code>DataBuffer</code>.
     * @param size The number of elements of each array to be used.
     */
    public DataBufferDouble(double dataArray[][], int size) {
        super(UNTRACKABLE, TYPE_DOUBLE, size, dataArray.length);
        bankdata = (double[][]) dataArray.clone();
        data = bankdata[0];
    }

    /**
     * Constructs a <code>double</code>-based <code>DataBuffer</code>
     * with the specified data arrays, size, and per-bank offsets.
     * The number of banks is equal to dataArray.length.  Each array
     * must be at least as large as <code>size</code> plus the
     * corresponding offset.  There must be an entry in the
     * <code>offsets</code> array for each data array.
     * <p>
     * Note that {@code DataBuffer} objects created by this constructor
     * may be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     * 使用指定的数据数组,大小和每个库偏移构造一个<code> double </code>  - 基于<code> DataBuffer </code>。存储区数等于dataArray.length。
     * 每个数组必须至少与<code> size </code>一样大,加上相应的偏移量。在每个数据数组的<code> offsets </code>数组中必须有一个条目。
     * <p>
     *  请注意,由此构造函数创建的{@code DataBuffer}对象可能与某些实施所使用的<a href="#optimizations">性能优化</a>不兼容(例如缓存视频内存中的关联图像)。
     * 
     * 
     * @param dataArray An array of arrays of <code>double</code>s to be
     *        used as the banks of this <code>DataBuffer</code>.
     * @param size The number of elements of each array to be used.
     * @param offsets An array of integer offsets, one for each bank.
     */
    public DataBufferDouble(double dataArray[][], int size, int offsets[]) {
        super(UNTRACKABLE, TYPE_DOUBLE, size, dataArray.length, offsets);
        bankdata = (double[][]) dataArray.clone();
        data = bankdata[0];
    }

    /**
     * Returns the default (first) <code>double</code> data array.
     * <p>
     * Note that calling this method may cause this {@code DataBuffer}
     * object to be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  返回默认(第一个)<code> double </code>数据数组。
     * <p>
     *  请注意,调用此方法可能会导致{@code DataBuffer}对象与某些实施(例如缓存视频内存中的关联图像)所使用的<a href="#optimizations">性能优化</a>不兼容。
     * 
     * 
     * @return the first double data array.
     */
    public double[] getData() {
        theTrackable.setUntrackable();
        return data;
    }

    /**
     * Returns the data array for the specified bank.
     * <p>
     * Note that calling this method may cause this {@code DataBuffer}
     * object to be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  返回指定库的数据数组。
     * <p>
     *  请注意,调用此方法可能会导致{@code DataBuffer}对象与某些实施(例如缓存视频内存中的关联图像)所使用的<a href="#optimizations">性能优化</a>不兼容。
     * 
     * 
     * @param bank the data array
     * @return the data array specified by <code>bank</code>.
     */
    public double[] getData(int bank) {
        theTrackable.setUntrackable();
        return bankdata[bank];
    }

    /**
     * Returns the data array for all banks.
     * <p>
     * Note that calling this method may cause this {@code DataBuffer}
     * object to be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  返回所有库的数据数组。
     * <p>
     *  请注意,调用此方法可能会导致{@code DataBuffer}对象与某些实施(例如缓存视频内存中的关联图像)所使用的<a href="#optimizations">性能优化</a>不兼容。
     * 
     * 
     * @return all data arrays from this data buffer.
     */
    public double[][] getBankData() {
        theTrackable.setUntrackable();
        return (double[][]) bankdata.clone();
    }

    /**
     * Returns the requested data array element from the first
     * (default) bank as an <code>int</code>.
     *
     * <p>
     * 将第一个(默认)库中请求的数据数组元素作为<code> int </code>返回。
     * 
     * 
     * @param i The desired data array element.
     * @return The data entry as an <code>int</code>.
     * @see #setElem(int, int)
     * @see #setElem(int, int, int)
     */
    public int getElem(int i) {
        return (int)(data[i+offset]);
    }

    /**
     * Returns the requested data array element from the specified
     * bank as an <code>int</code>.
     *
     * <p>
     *  将指定库中请求的数据数组元素作为<code> int </code>返回。
     * 
     * 
     * @param bank The bank number.
     * @param i The desired data array element.
     *
     * @return The data entry as an <code>int</code>.
     * @see #setElem(int, int)
     * @see #setElem(int, int, int)
     */
    public int getElem(int bank, int i) {
        return (int)(bankdata[bank][i+offsets[bank]]);
    }

    /**
     * Sets the requested data array element in the first (default)
     * bank to the given <code>int</code>.
     *
     * <p>
     *  将第一个(默认)库中请求的数据数组元素设置为给定的<code> int </code>。
     * 
     * 
     * @param i The desired data array element.
     * @param val The value to be set.
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
    public void setElem(int i, int val) {
        data[i+offset] = (double)val;
        theTrackable.markDirty();
    }

    /**
     * Sets the requested data array element in the specified bank
     * to the given <code>int</code>.
     *
     * <p>
     *  将指定库中请求的数据数组元素设置为给定的<code> int </code>。
     * 
     * 
     * @param bank The bank number.
     * @param i The desired data array element.
     * @param val The value to be set.
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
    public void setElem(int bank, int i, int val) {
        bankdata[bank][i+offsets[bank]] = (double)val;
        theTrackable.markDirty();
    }

    /**
     * Returns the requested data array element from the first
     * (default) bank as a <code>float</code>.
     *
     * <p>
     *  将第一个(默认)库中请求的数据数组元素作为<code> float </code>返回。
     * 
     * 
     * @param i The desired data array element.
     *
     * @return The data entry as a <code>float</code>.
     * @see #setElemFloat(int, float)
     * @see #setElemFloat(int, int, float)
     */
    public float getElemFloat(int i) {
        return (float)data[i+offset];
    }

    /**
     * Returns the requested data array element from the specified
     * bank as a <code>float</code>.
     *
     * <p>
     *  将指定库中请求的数据数组元素作为<code> float </code>返回。
     * 
     * 
     * @param bank The bank number.
     * @param i The desired data array element.
     *
     * @return The data entry as a <code>float</code>.
     * @see #setElemFloat(int, float)
     * @see #setElemFloat(int, int, float)
     */
    public float getElemFloat(int bank, int i) {
        return (float)bankdata[bank][i+offsets[bank]];
    }

    /**
     * Sets the requested data array element in the first (default)
     * bank to the given <code>float</code>.
     *
     * <p>
     *  将第一个(默认)库中请求的数据数组元素设置为给定的<code> float </code>。
     * 
     * 
     * @param i The desired data array element.
     * @param val The value to be set.
     * @see #getElemFloat(int)
     * @see #getElemFloat(int, int)
     */
    public void setElemFloat(int i, float val) {
        data[i+offset] = (double)val;
        theTrackable.markDirty();
    }

    /**
     * Sets the requested data array element in the specified bank to
     * the given <code>float</code>.
     *
     * <p>
     *  将指定库中请求的数据数组元素设置为给定的<code> float </code>。
     * 
     * 
     * @param bank The bank number.
     * @param i The desired data array element.
     * @param val The value to be set.
     * @see #getElemFloat(int)
     * @see #getElemFloat(int, int)
     */
    public void setElemFloat(int bank, int i, float val) {
        bankdata[bank][i+offsets[bank]] = (double)val;
        theTrackable.markDirty();
    }

    /**
     * Returns the requested data array element from the first
     * (default) bank as a <code>double</code>.
     *
     * <p>
     *  将第一个(默认)库中请求的数据数组元素作为<code> double </code>返回。
     * 
     * 
     * @param i The desired data array element.
     *
     * @return The data entry as a <code>double</code>.
     * @see #setElemDouble(int, double)
     * @see #setElemDouble(int, int, double)
     */
    public double getElemDouble(int i) {
        return data[i+offset];
    }

    /**
     * Returns the requested data array element from the specified
     * bank as a <code>double</code>.
     *
     * <p>
     *  将指定库中请求的数据数组元素作为<code> double </code>返回。
     * 
     * 
     * @param bank The bank number.
     * @param i The desired data array element.
     *
     * @return The data entry as a <code>double</code>.
     * @see #setElemDouble(int, double)
     * @see #setElemDouble(int, int, double)
     */
    public double getElemDouble(int bank, int i) {
        return bankdata[bank][i+offsets[bank]];
    }

    /**
     * Sets the requested data array element in the first (default)
     * bank to the given <code>double</code>.
     *
     * <p>
     *  将第一个(默认)库中请求的数据数组元素设置为给定的<code> double </code>。
     * 
     * 
     * @param i The desired data array element.
     * @param val The value to be set.
     * @see #getElemDouble(int)
     * @see #getElemDouble(int, int)
     */
    public void setElemDouble(int i, double val) {
        data[i+offset] = val;
        theTrackable.markDirty();
    }

    /**
     * Sets the requested data array element in the specified bank to
     * the given <code>double</code>.
     *
     * <p>
     *  将指定库中请求的数据数组元素设置为给定的<code> double </code>。
     * 
     * @param bank The bank number.
     * @param i The desired data array element.
     * @param val The value to be set.
     * @see #getElemDouble(int)
     * @see #getElemDouble(int, int)
     */
    public void setElemDouble(int bank, int i, double val) {
        bankdata[bank][i+offsets[bank]] = val;
        theTrackable.markDirty();
    }
}
