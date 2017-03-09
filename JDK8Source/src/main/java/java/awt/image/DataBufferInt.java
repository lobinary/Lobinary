/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2008, Oracle and/or its affiliates. All rights reserved.
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

/* ****************************************************************
 ******************************************************************
 ******************************************************************
 *** COPYRIGHT (c) Eastman Kodak Company, 1997
 *** As  an unpublished  work pursuant to Title 17 of the United
 *** States Code.  All rights reserved.
 ******************************************************************
 ******************************************************************
 * <p>
 *  **************************************************** ************** ********************************
 * **** **************************** * COPYRIGHT(c)Eastman Kodak Company,1997 *根据United Nations Title 17
 * 的未发表作品*国家代码。
 * 版权所有。
 *  **************************************************** ************** ********************************
 * **** ****************************。
 * 版权所有。
 * 
 * 
 ******************************************************************/

package java.awt.image;

import static sun.java2d.StateTrackable.State.*;

/**
 * This class extends <CODE>DataBuffer</CODE> and stores data internally
 * as integers.
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
 * <p>
 *  该类扩展了<CODE> DataBuffer </CODE>,并将数据内部存储为整数。
 * <p>
 * <a name="optimizations">
 *  注意,如果一些实现可以保持对如何存储图像的数据的控制,则它们可以更有效地工作。例如,诸如在视频存储器中高速缓存图像的优化需要实现跟踪对该数据的所有修改。
 * 如果其他实现可以将数据存储在除Java数组之外的位置中,则可以更好地操作。为了保持与各种优化的最佳兼容性,最好避免将底层存储暴露为Java数组的构造函数和方法,如下面在这些方法的文档中所述。
 * </a>
 */
public final class DataBufferInt extends DataBuffer
{
    /** The default data bank. */
    int data[];

    /** All data banks */
    int bankdata[][];

    /**
     * Constructs an integer-based <CODE>DataBuffer</CODE> with a single bank
     * and the specified size.
     *
     * <p>
     *  构造具有单个库和指定大小的基于整数的<CODE> DataBuffer </CODE>。
     * 
     * 
     * @param size The size of the <CODE>DataBuffer</CODE>.
     */
    public DataBufferInt(int size) {
        super(STABLE, TYPE_INT, size);
        data = new int[size];
        bankdata = new int[1][];
        bankdata[0] = data;
    }

    /**
     * Constructs an integer-based <CODE>DataBuffer</CODE> with the specified number of
     * banks, all of which are the specified size.
     *
     * <p>
     *  构造具有指定数量的库的基于整数的<CODE> DataBuffer </CODE>,所有这些都是指定的大小。
     * 
     * 
     * @param size The size of the banks in the <CODE>DataBuffer</CODE>.
     * @param numBanks The number of banks in the a<CODE>DataBuffer</CODE>.
     */
    public DataBufferInt(int size, int numBanks) {
        super(STABLE, TYPE_INT, size, numBanks);
        bankdata = new int[numBanks][];
        for (int i= 0; i < numBanks; i++) {
            bankdata[i] = new int[size];
        }
        data = bankdata[0];
    }

    /**
     * Constructs an integer-based <CODE>DataBuffer</CODE> with a single bank using the
     * specified array.
     * Only the first <CODE>size</CODE> elements should be used by accessors of
     * this <CODE>DataBuffer</CODE>.  <CODE>dataArray</CODE> must be large enough to
     * hold <CODE>size</CODE> elements.
     * <p>
     * Note that {@code DataBuffer} objects created by this constructor
     * may be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     * 使用指定的数组构造一个基于整数的<CODE> DataBuffer </CODE>。
     * 对于此<CODE> DataBuffer </CODE>的访问者,只能使用第一个<CODE> size </CODE>元素。
     *  <CODE> dataArray </CODE>必须足够大以容纳<CODE> size </CODE>元素。
     * <p>
     *  请注意,由此构造函数创建的{@code DataBuffer}对象可能与某些实施所使用的<a href="#optimizations">性能优化</a>不兼容(例如缓存视频内存中的关联图像)。
     * 
     * 
     * @param dataArray The integer array for the <CODE>DataBuffer</CODE>.
     * @param size The size of the <CODE>DataBuffer</CODE> bank.
     */
    public DataBufferInt(int dataArray[], int size) {
        super(UNTRACKABLE, TYPE_INT, size);
        data = dataArray;
        bankdata = new int[1][];
        bankdata[0] = data;
    }

    /**
     * Constructs an integer-based <CODE>DataBuffer</CODE> with a single bank using the
     * specified array, size, and offset.  <CODE>dataArray</CODE> must have at least
     * <CODE>offset</CODE> + <CODE>size</CODE> elements.  Only elements <CODE>offset</CODE>
     * through <CODE>offset</CODE> + <CODE>size</CODE> - 1
     * should be used by accessors of this <CODE>DataBuffer</CODE>.
     * <p>
     * Note that {@code DataBuffer} objects created by this constructor
     * may be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  使用指定的数组,大小和偏移量构造一个基于整数的<CODE> DataBuffer </CODE>。
     *  <CODE> dataArray </CODE>必须至少有<CODE> offset </CODE> + <CODE> size </CODE>元素。
     * 该<CODE> DataBuffer </CODE>的访问器应该使用<CODE>偏移</CODE>元素<CODE>偏移</CODE> + <CODE> size </CODE>。
     * <p>
     *  请注意,由此构造函数创建的{@code DataBuffer}对象可能与某些实施所使用的<a href="#optimizations">性能优化</a>不兼容(例如缓存视频内存中的关联图像)。
     * 
     * 
     * @param dataArray The integer array for the <CODE>DataBuffer</CODE>.
     * @param size The size of the <CODE>DataBuffer</CODE> bank.
     * @param offset The offset into the <CODE>dataArray</CODE>.
     */
    public DataBufferInt(int dataArray[], int size, int offset) {
        super(UNTRACKABLE, TYPE_INT, size, 1, offset);
        data = dataArray;
        bankdata = new int[1][];
        bankdata[0] = data;
    }

    /**
     * Constructs an integer-based <CODE>DataBuffer</CODE> with the specified arrays.
     * The number of banks will be equal to <CODE>dataArray.length</CODE>.
     * Only the first <CODE>size</CODE> elements of each array should be used by
     * accessors of this <CODE>DataBuffer</CODE>.
     * <p>
     * Note that {@code DataBuffer} objects created by this constructor
     * may be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  使用指定的数组构造一个基于整数的<CODE> DataBuffer </CODE>。存储区数将等于<CODE> dataArray.length </CODE>。
     * 每个数组的第一个<CODE> size </CODE>元素应该被此<CODE> DataBuffer </CODE>的访问器使用。
     * <p>
     * 请注意,由此构造函数创建的{@code DataBuffer}对象可能与某些实施所使用的<a href="#optimizations">性能优化</a>不兼容(例如缓存视频内存中的关联图像)。
     * 
     * 
     * @param dataArray The integer arrays for the <CODE>DataBuffer</CODE>.
     * @param size The size of the banks in the <CODE>DataBuffer</CODE>.
     */
    public DataBufferInt(int dataArray[][], int size) {
        super(UNTRACKABLE, TYPE_INT, size, dataArray.length);
        bankdata = (int [][]) dataArray.clone();
        data = bankdata[0];
    }

    /**
     * Constructs an integer-based <CODE>DataBuffer</CODE> with the specified arrays, size,
     * and offsets.
     * The number of banks is equal to <CODE>dataArray.length</CODE>.  Each array must
     * be at least as large as <CODE>size</CODE> + the corresponding offset.   There must
     * be an entry in the offset array for each <CODE>dataArray</CODE> entry.  For each
     * bank, only elements <CODE>offset</CODE> through
     * <CODE>offset</CODE> + <CODE>size</CODE> - 1 should be
     * used by accessors of this <CODE>DataBuffer</CODE>.
     * <p>
     * Note that {@code DataBuffer} objects created by this constructor
     * may be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  构造具有指定数组,大小和偏移量的基于整数的<CODE> DataBuffer </CODE>。存储区数等于<CODE> dataArray.length </CODE>。
     * 每个数组必须至少与<CODE> size </CODE> +相应的偏移量一样大。每个<CODE> dataArray </CODE>条目的偏移数组中必须有一个条目。
     * 对于每个库,只有<CODE>偏移量</CODE>通过<CODE>偏移量</CODE> + <CODE> size </CODE>  -  1的元素才能被此<CODE> DataBuffer </CODE>
     * 的访问者使用。
     * 每个数组必须至少与<CODE> size </CODE> +相应的偏移量一样大。每个<CODE> dataArray </CODE>条目的偏移数组中必须有一个条目。
     * <p>
     *  请注意,由此构造函数创建的{@code DataBuffer}对象可能与某些实施所使用的<a href="#optimizations">性能优化</a>不兼容(例如缓存视频内存中的关联图像)。
     * 
     * 
     * @param dataArray The integer arrays for the <CODE>DataBuffer</CODE>.
     * @param size The size of the banks in the <CODE>DataBuffer</CODE>.
     * @param offsets The offsets into each array.
     */
    public DataBufferInt(int dataArray[][], int size, int offsets[]) {
        super(UNTRACKABLE, TYPE_INT, size, dataArray.length, offsets);
        bankdata = (int [][]) dataArray.clone();
        data = bankdata[0];
    }

    /**
     * Returns the default (first) int data array in <CODE>DataBuffer</CODE>.
     * <p>
     * Note that calling this method may cause this {@code DataBuffer}
     * object to be incompatible with <a href="#optimizations">performance
     * optimizations</a> used by some implementations (such as caching
     * an associated image in video memory).
     *
     * <p>
     *  返回<CODE> DataBuffer </CODE>中的默认(第一个)int数据数组。
     * <p>
     *  请注意,调用此方法可能会导致{@code DataBuffer}对象与某些实施(例如缓存视频内存中的关联图像)所使用的<a href="#optimizations">性能优化</a>不兼容。
     * 
     * 
     * @return The first integer data array.
     */
    public int[] getData() {
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
     * 请注意,调用此方法可能会导致{@code DataBuffer}对象与某些实施(例如缓存视频内存中的关联图像)所使用的<a href="#optimizations">性能优化</a>不兼容。
     * 
     * 
     * @param bank The bank whose data array you want to get.
     * @return The data array for the specified bank.
     */
    public int[] getData(int bank) {
        theTrackable.setUntrackable();
        return bankdata[bank];
    }

    /**
     * Returns the data arrays for all banks.
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
     * @return All of the data arrays.
     */
    public int[][] getBankData() {
        theTrackable.setUntrackable();
        return (int [][]) bankdata.clone();
    }

    /**
     * Returns the requested data array element from the first (default) bank.
     *
     * <p>
     *  从第一个(默认)库返回请求的数据数组元素。
     * 
     * 
     * @param i The data array element you want to get.
     * @return The requested data array element as an integer.
     * @see #setElem(int, int)
     * @see #setElem(int, int, int)
     */
    public int getElem(int i) {
        return data[i+offset];
    }

    /**
     * Returns the requested data array element from the specified bank.
     *
     * <p>
     *  从指定的bank返回请求的数据数组元素。
     * 
     * 
     * @param bank The bank from which you want to get a data array element.
     * @param i The data array element you want to get.
     * @return The requested data array element as an integer.
     * @see #setElem(int, int)
     * @see #setElem(int, int, int)
     */
    public int getElem(int bank, int i) {
        return bankdata[bank][i+offsets[bank]];
    }

    /**
     * Sets the requested data array element in the first (default) bank
     * to the specified value.
     *
     * <p>
     *  将第一个(默认)库中请求的数据数组元素设置为指定的值。
     * 
     * 
     * @param i The data array element you want to set.
     * @param val The integer value to which you want to set the data array element.
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
    public void setElem(int i, int val) {
        data[i+offset] = val;
        theTrackable.markDirty();
    }

    /**
     * Sets the requested data array element in the specified bank
     * to the integer value <CODE>i</CODE>.
     * <p>
     *  将指定库中请求的数据数组元素设置为整数值<CODE> i </CODE>。
     * 
     * @param bank The bank in which you want to set the data array element.
     * @param i The data array element you want to set.
     * @param val The integer value to which you want to set the specified data array element.
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
    public void setElem(int bank, int i, int val) {
        bankdata[bank][i+offsets[bank]] = (int)val;
        theTrackable.markDirty();
    }
}
