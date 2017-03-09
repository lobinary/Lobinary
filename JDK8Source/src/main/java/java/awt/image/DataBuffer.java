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

import sun.java2d.StateTrackable.State;
import static sun.java2d.StateTrackable.State.*;
import sun.java2d.StateTrackableDelegate;

import sun.awt.image.SunWritableRaster;

import java.lang.annotation.Native;

/**
 * This class exists to wrap one or more data arrays.  Each data array in
 * the DataBuffer is referred to as a bank.  Accessor methods for getting
 * and setting elements of the DataBuffer's banks exist with and without
 * a bank specifier.  The methods without a bank specifier use the default 0th
 * bank.  The DataBuffer can optionally take an offset per bank, so that
 * data in an existing array can be used even if the interesting data
 * doesn't start at array location zero.  Getting or setting the 0th
 * element of a bank, uses the (0+offset)th element of the array.  The
 * size field specifies how much of the data array is available for
 * use.  Size + offset for a given bank should never be greater
 * than the length of the associated data array.  The data type of
 * a data buffer indicates the type of the data array(s) and may also
 * indicate additional semantics, e.g. storing unsigned 8-bit data
 * in elements of a byte array.  The data type may be TYPE_UNDEFINED
 * or one of the types defined below.  Other types may be added in
 * the future.  Generally, an object of class DataBuffer will be cast down
 * to one of its data type specific subclasses to access data type specific
 * methods for improved performance.  Currently, the Java 2D(tm) API
 * image classes use TYPE_BYTE, TYPE_USHORT, TYPE_INT, TYPE_SHORT,
 * TYPE_FLOAT, and TYPE_DOUBLE DataBuffers to store image data.
 * <p>
 * 此类存在以包装一个或多个数据数组。 DataBuffer中的每个数据数组被称为一个bank。用于获取和设置DataBuffer的组元素的访问器方法存在有和没有银行说明符。
 * 没有银行说明符的方法使用默认的第0个银行。 DataBuffer可以可选地对每个库采取偏移量,使得即使感兴趣的数据不在数组位置零处开始,也可以使用现有阵列中的数据。
 * 获取或设置组的第0个元素,使用数组的(0 + offset)th元素。 size字段指定了可以使用的数据数组的大小。给定库的大小+偏移量不应该大于关联数据数组的长度。
 * 数据缓冲器的数据类型指示数据阵列的类型,并且还可以指示附加的语义,例如。将无符号8位数据存储在字节阵列的元件中。数据类型可以是TYPE_UNDEFINED或下面定义的类型之一。将来可能会添加其他类型。
 * 通常,DataBuffer类的对象将被强制转换为其数据类型特定子类之一,以访问特定于数据类型的方法,从而提高性能。
 * 目前,Java 2D(tm)API映像类使用TYPE_BYTE,TYPE_USHORT,TYPE_INT,TYPE_SHORT,TYPE_FLOAT和TYPE_DOUBLE DataBuffers来存储
 * 图像数据。
 * 通常,DataBuffer类的对象将被强制转换为其数据类型特定子类之一,以访问特定于数据类型的方法,从而提高性能。
 * 
 * 
 * @see java.awt.image.Raster
 * @see java.awt.image.SampleModel
 */
public abstract class DataBuffer {

    /** Tag for unsigned byte data. */
    @Native public static final int TYPE_BYTE  = 0;

    /** Tag for unsigned short data. */
    @Native public static final int TYPE_USHORT = 1;

    /** Tag for signed short data.  Placeholder for future use. */
    @Native public static final int TYPE_SHORT = 2;

    /** Tag for int data. */
    @Native public static final int TYPE_INT   = 3;

    /** Tag for float data.  Placeholder for future use. */
    @Native public static final int TYPE_FLOAT  = 4;

    /** Tag for double data.  Placeholder for future use. */
    @Native public static final int TYPE_DOUBLE  = 5;

    /** Tag for undefined data. */
    @Native public static final int TYPE_UNDEFINED = 32;

    /** The data type of this DataBuffer. */
    protected int dataType;

    /** The number of banks in this DataBuffer. */
    protected int banks;

    /** Offset into default (first) bank from which to get the first element. */
    protected int offset;

    /** Usable size of all banks. */
    protected int size;

    /** Offsets into all banks. */
    protected int offsets[];

    /* The current StateTrackable state. */
    StateTrackableDelegate theTrackable;

    /** Size of the data types indexed by DataType tags defined above. */
    private static final int dataTypeSize[] = {8,16,16,32,32,64};

    /** Returns the size (in bits) of the data type, given a datatype tag.
    /* <p>
    /* 
      * @param type the value of one of the defined datatype tags
      * @return the size of the data type
      * @throws IllegalArgumentException if <code>type</code> is less than
      *         zero or greater than {@link #TYPE_DOUBLE}
      */
    public static int getDataTypeSize(int type) {
        if (type < TYPE_BYTE || type > TYPE_DOUBLE) {
            throw new IllegalArgumentException("Unknown data type "+type);
        }
        return dataTypeSize[type];
    }

    /**
     *  Constructs a DataBuffer containing one bank of the specified
     *  data type and size.
     *
     * <p>
     *  构造一个包含指定数据类型和大小的一个库的DataBuffer。
     * 
     * 
     *  @param dataType the data type of this <code>DataBuffer</code>
     *  @param size the size of the banks
     */
    protected DataBuffer(int dataType, int size) {
        this(UNTRACKABLE, dataType, size);
    }

    /**
     *  Constructs a DataBuffer containing one bank of the specified
     *  data type and size with the indicated initial {@link State State}.
     *
     * <p>
     * 构造一个DataBuffer,它包含指定数据类型和大小的一个库,带有指定的初始{@link State State}。
     * 
     * 
     *  @param initialState the initial {@link State State} state of the data
     *  @param dataType the data type of this <code>DataBuffer</code>
     *  @param size the size of the banks
     *  @since 1.7
     */
    DataBuffer(State initialState,
               int dataType, int size)
    {
        this.theTrackable = StateTrackableDelegate.createInstance(initialState);
        this.dataType = dataType;
        this.banks = 1;
        this.size = size;
        this.offset = 0;
        this.offsets = new int[1];  // init to 0 by new
    }

    /**
     *  Constructs a DataBuffer containing the specified number of
     *  banks.  Each bank has the specified size and an offset of 0.
     *
     * <p>
     *  构造一个包含指定数量的bank的DataBuffer。每个存储区具有指定的大小和偏移量0。
     * 
     * 
     *  @param dataType the data type of this <code>DataBuffer</code>
     *  @param size the size of the banks
     *  @param numBanks the number of banks in this
     *         <code>DataBuffer</code>
     */
    protected DataBuffer(int dataType, int size, int numBanks) {
        this(UNTRACKABLE, dataType, size, numBanks);
    }

    /**
     *  Constructs a DataBuffer containing the specified number of
     *  banks with the indicated initial {@link State State}.
     *  Each bank has the specified size and an offset of 0.
     *
     * <p>
     *  构造一个DataBuffer,它包含指定的初始{@link State State}的指定数量的bank。每个存储区具有指定的大小和偏移量0。
     * 
     * 
     *  @param initialState the initial {@link State State} state of the data
     *  @param dataType the data type of this <code>DataBuffer</code>
     *  @param size the size of the banks
     *  @param numBanks the number of banks in this
     *         <code>DataBuffer</code>
     *  @since 1.7
     */
    DataBuffer(State initialState,
               int dataType, int size, int numBanks)
    {
        this.theTrackable = StateTrackableDelegate.createInstance(initialState);
        this.dataType = dataType;
        this.banks = numBanks;
        this.size = size;
        this.offset = 0;
        this.offsets = new int[banks]; // init to 0 by new
    }

    /**
     *  Constructs a DataBuffer that contains the specified number
     *  of banks.  Each bank has the specified datatype, size and offset.
     *
     * <p>
     *  构造一个包含指定数量的库的DataBuffer。每个bank具有指定的数据类型,大小和偏移量。
     * 
     * 
     *  @param dataType the data type of this <code>DataBuffer</code>
     *  @param size the size of the banks
     *  @param numBanks the number of banks in this
     *         <code>DataBuffer</code>
     *  @param offset the offset for each bank
     */
    protected DataBuffer(int dataType, int size, int numBanks, int offset) {
        this(UNTRACKABLE, dataType, size, numBanks, offset);
    }

    /**
     *  Constructs a DataBuffer that contains the specified number
     *  of banks with the indicated initial {@link State State}.
     *  Each bank has the specified datatype, size and offset.
     *
     * <p>
     *  构造一个DataBuffer,它包含指定的初始{@link State State}的指定数量的bank。每个bank具有指定的数据类型,大小和偏移量。
     * 
     * 
     *  @param initialState the initial {@link State State} state of the data
     *  @param dataType the data type of this <code>DataBuffer</code>
     *  @param size the size of the banks
     *  @param numBanks the number of banks in this
     *         <code>DataBuffer</code>
     *  @param offset the offset for each bank
     *  @since 1.7
     */
    DataBuffer(State initialState,
               int dataType, int size, int numBanks, int offset)
    {
        this.theTrackable = StateTrackableDelegate.createInstance(initialState);
        this.dataType = dataType;
        this.banks = numBanks;
        this.size = size;
        this.offset = offset;
        this.offsets = new int[numBanks];
        for (int i = 0; i < numBanks; i++) {
            this.offsets[i] = offset;
        }
    }

    /**
     *  Constructs a DataBuffer which contains the specified number
     *  of banks.  Each bank has the specified datatype and size.  The
     *  offset for each bank is specified by its respective entry in
     *  the offsets array.
     *
     * <p>
     *  构造一个包含指定数量的库的DataBuffer。每个bank都有指定的数据类型和大小。每个库的偏移量由偏移数组中的相应条目指定。
     * 
     * 
     *  @param dataType the data type of this <code>DataBuffer</code>
     *  @param size the size of the banks
     *  @param numBanks the number of banks in this
     *         <code>DataBuffer</code>
     *  @param offsets an array containing an offset for each bank.
     *  @throws ArrayIndexOutOfBoundsException if <code>numBanks</code>
     *          does not equal the length of <code>offsets</code>
     */
    protected DataBuffer(int dataType, int size, int numBanks, int offsets[]) {
        this(UNTRACKABLE, dataType, size, numBanks, offsets);
    }

    /**
     *  Constructs a DataBuffer which contains the specified number
     *  of banks with the indicated initial {@link State State}.
     *  Each bank has the specified datatype and size.  The
     *  offset for each bank is specified by its respective entry in
     *  the offsets array.
     *
     * <p>
     *  构造一个DataBuffer,它包含指定的初始{@link State State}的指定数量的bank。每个bank都有指定的数据类型和大小。每个库的偏移量由偏移数组中的相应条目指定。
     * 
     * 
     *  @param initialState the initial {@link State State} state of the data
     *  @param dataType the data type of this <code>DataBuffer</code>
     *  @param size the size of the banks
     *  @param numBanks the number of banks in this
     *         <code>DataBuffer</code>
     *  @param offsets an array containing an offset for each bank.
     *  @throws ArrayIndexOutOfBoundsException if <code>numBanks</code>
     *          does not equal the length of <code>offsets</code>
     *  @since 1.7
     */
    DataBuffer(State initialState,
               int dataType, int size, int numBanks, int offsets[])
    {
        if (numBanks != offsets.length) {
            throw new ArrayIndexOutOfBoundsException("Number of banks" +
                 " does not match number of bank offsets");
        }
        this.theTrackable = StateTrackableDelegate.createInstance(initialState);
        this.dataType = dataType;
        this.banks = numBanks;
        this.size = size;
        this.offset = offsets[0];
        this.offsets = (int[])offsets.clone();
    }

    /**  Returns the data type of this DataBuffer.
    /* <p>
    /* 
     *   @return the data type of this <code>DataBuffer</code>.
     */
    public int getDataType() {
        return dataType;
    }

    /**  Returns the size (in array elements) of all banks.
    /* <p>
    /* 
     *   @return the size of all banks.
     */
    public int getSize() {
        return size;
    }

    /** Returns the offset of the default bank in array elements.
    /* <p>
    /* 
     *  @return the offset of the default bank.
     */
    public int getOffset() {
        return offset;
    }

    /** Returns the offsets (in array elements) of all the banks.
    /* <p>
    /* 
     *  @return the offsets of all banks.
     */
    public int[] getOffsets() {
        return (int[])offsets.clone();
    }

    /** Returns the number of banks in this DataBuffer.
    /* <p>
    /* 
     *  @return the number of banks.
     */
    public int getNumBanks() {
        return banks;
    }

    /**
     * Returns the requested data array element from the first (default) bank
     * as an integer.
     * <p>
     *  将第一个(默认)库中请求的数据数组元素作为整数返回。
     * 
     * 
     * @param i the index of the requested data array element
     * @return the data array element at the specified index.
     * @see #setElem(int, int)
     * @see #setElem(int, int, int)
     */
    public int getElem(int i) {
        return getElem(0,i);
    }

    /**
     * Returns the requested data array element from the specified bank
     * as an integer.
     * <p>
     *  将指定库中请求的数据数组元素作为整数返回。
     * 
     * 
     * @param bank the specified bank
     * @param i the index of the requested data array element
     * @return the data array element at the specified index from the
     *         specified bank at the specified index.
     * @see #setElem(int, int)
     * @see #setElem(int, int, int)
     */
    public abstract int getElem(int bank, int i);

    /**
     * Sets the requested data array element in the first (default) bank
     * from the given integer.
     * <p>
     *  在给定整数的第一个(默认)库中设置请求的数据数组元素。
     * 
     * 
     * @param i the specified index into the data array
     * @param val the data to set the element at the specified index in
     * the data array
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
    public void  setElem(int i, int val) {
        setElem(0,i,val);
    }

    /**
     * Sets the requested data array element in the specified bank
     * from the given integer.
     * <p>
     * 从给定的整数设置指定库中请求的数据数组元素。
     * 
     * 
     * @param bank the specified bank
     * @param i the specified index into the data array
     * @param val  the data to set the element in the specified bank
     * at the specified index in the data array
     * @see #getElem(int)
     * @see #getElem(int, int)
     */
    public abstract void setElem(int bank, int i, int val);

    /**
     * Returns the requested data array element from the first (default) bank
     * as a float.  The implementation in this class is to cast getElem(i)
     * to a float.  Subclasses may override this method if another
     * implementation is needed.
     * <p>
     *  从第一个(默认)库返回请求的数据数组元素为float。这个类中的实现是将getElem(i)转换为float。如果需要另一个实现,子类可以覆盖此方法。
     * 
     * 
     * @param i the index of the requested data array element
     * @return a float value representing the data array element at the
     *  specified index.
     * @see #setElemFloat(int, float)
     * @see #setElemFloat(int, int, float)
     */
    public float getElemFloat(int i) {
        return (float)getElem(i);
    }

    /**
     * Returns the requested data array element from the specified bank
     * as a float.  The implementation in this class is to cast
     * {@link #getElem(int, int)}
     * to a float.  Subclasses can override this method if another
     * implementation is needed.
     * <p>
     *  从指定的bank返回请求的数据数组元素为float。这个类中的实现是将{@link #getElem(int,int)}强制转换为float。如果需要另一个实现,子类可以覆盖此方法。
     * 
     * 
     * @param bank the specified bank
     * @param i the index of the requested data array element
     * @return a float value representing the data array element from the
     * specified bank at the specified index.
     * @see #setElemFloat(int, float)
     * @see #setElemFloat(int, int, float)
     */
    public float getElemFloat(int bank, int i) {
        return (float)getElem(bank,i);
    }

    /**
     * Sets the requested data array element in the first (default) bank
     * from the given float.  The implementation in this class is to cast
     * val to an int and call {@link #setElem(int, int)}.  Subclasses
     * can override this method if another implementation is needed.
     * <p>
     *  在给定浮点数的第一个(默认)库中设置请求的数据数组元素。这个类中的实现是将val转换为int并调用{@link #setElem(int,int)}。如果需要另一个实现,子类可以覆盖此方法。
     * 
     * 
     * @param i the specified index
     * @param val the value to set the element at the specified index in
     * the data array
     * @see #getElemFloat(int)
     * @see #getElemFloat(int, int)
     */
    public void setElemFloat(int i, float val) {
        setElem(i,(int)val);
    }

    /**
     * Sets the requested data array element in the specified bank
     * from the given float.  The implementation in this class is to cast
     * val to an int and call {@link #setElem(int, int)}.  Subclasses can
     * override this method if another implementation is needed.
     * <p>
     *  从给定的浮点数中设置指定库中请求的数据数组元素。这个类中的实现是将val转换为int并调用{@link #setElem(int,int)}。如果需要另一个实现,子类可以覆盖此方法。
     * 
     * 
     * @param bank the specified bank
     * @param i the specified index
     * @param val the value to set the element in the specified bank at
     * the specified index in the data array
     * @see #getElemFloat(int)
     * @see #getElemFloat(int, int)
     */
    public void setElemFloat(int bank, int i, float val) {
        setElem(bank,i,(int)val);
    }

    /**
     * Returns the requested data array element from the first (default) bank
     * as a double.  The implementation in this class is to cast
     * {@link #getElem(int)}
     * to a double.  Subclasses can override this method if another
     * implementation is needed.
     * <p>
     *  将第一个(默认)库中请求的数据数组元素作为double返回。这个类中的实现是将{@link #getElem(int)}转换为double。如果需要另一个实现,子类可以覆盖此方法。
     * 
     * 
     * @param i the specified index
     * @return a double value representing the element at the specified
     * index in the data array.
     * @see #setElemDouble(int, double)
     * @see #setElemDouble(int, int, double)
     */
    public double getElemDouble(int i) {
        return (double)getElem(i);
    }

    /**
     * Returns the requested data array element from the specified bank as
     * a double.  The implementation in this class is to cast getElem(bank, i)
     * to a double.  Subclasses may override this method if another
     * implementation is needed.
     * <p>
     * 从指定的bank返回请求的数据数组元素为double。在这个类中的实现是将getElem(bank,i)转换为double。如果需要另一个实现,子类可以覆盖此方法。
     * 
     * 
     * @param bank the specified bank
     * @param i the specified index
     * @return a double value representing the element from the specified
     * bank at the specified index in the data array.
     * @see #setElemDouble(int, double)
     * @see #setElemDouble(int, int, double)
     */
    public double getElemDouble(int bank, int i) {
        return (double)getElem(bank,i);
    }

    /**
     * Sets the requested data array element in the first (default) bank
     * from the given double.  The implementation in this class is to cast
     * val to an int and call {@link #setElem(int, int)}.  Subclasses can
     * override this method if another implementation is needed.
     * <p>
     *  在给定的double中,在第一个(默认)bank中设置请求的数据数组元素。这个类中的实现是将val转换为int并调用{@link #setElem(int,int)}。
     * 如果需要另一个实现,子类可以覆盖此方法。
     * 
     * 
     * @param i the specified index
     * @param val the value to set the element at the specified index
     * in the data array
     * @see #getElemDouble(int)
     * @see #getElemDouble(int, int)
     */
    public void setElemDouble(int i, double val) {
        setElem(i,(int)val);
    }

    /**
     * Sets the requested data array element in the specified bank
     * from the given double.  The implementation in this class is to cast
     * val to an int and call {@link #setElem(int, int)}.  Subclasses can
     * override this method if another implementation is needed.
     * <p>
     * 
     * @param bank the specified bank
     * @param i the specified index
     * @param val the value to set the element in the specified bank
     * at the specified index of the data array
     * @see #getElemDouble(int)
     * @see #getElemDouble(int, int)
     */
    public void setElemDouble(int bank, int i, double val) {
        setElem(bank,i,(int)val);
    }

    static int[] toIntArray(Object obj) {
        if (obj instanceof int[]) {
            return (int[])obj;
        } else if (obj == null) {
            return null;
        } else if (obj instanceof short[]) {
            short sdata[] = (short[])obj;
            int idata[] = new int[sdata.length];
            for (int i = 0; i < sdata.length; i++) {
                idata[i] = (int)sdata[i] & 0xffff;
            }
            return idata;
        } else if (obj instanceof byte[]) {
            byte bdata[] = (byte[])obj;
            int idata[] = new int[bdata.length];
            for (int i = 0; i < bdata.length; i++) {
                idata[i] = 0xff & (int)bdata[i];
            }
            return idata;
        }
        return null;
    }

    static {
        SunWritableRaster.setDataStealer(new SunWritableRaster.DataStealer() {
            public byte[] getData(DataBufferByte dbb, int bank) {
                return dbb.bankdata[bank];
            }

            public short[] getData(DataBufferUShort dbus, int bank) {
                return dbus.bankdata[bank];
            }

            public int[] getData(DataBufferInt dbi, int bank) {
                return dbi.bankdata[bank];
            }

            public StateTrackableDelegate getTrackable(DataBuffer db) {
                return db.theTrackable;
            }

            public void setTrackable(DataBuffer db,
                                     StateTrackableDelegate trackable)
            {
                db.theTrackable = trackable;
            }
        });
    }
}
