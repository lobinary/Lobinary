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
 * 的未发表作品*国家代码保留所有权利******************************************** ******************** ******************
 * ************ **********************************。
 * 
 * 
 ******************************************************************/

package java.awt.image;

import java.util.Arrays;

/**
 *  This class represents image data which is stored such that each sample
 *  of a pixel occupies one data element of the DataBuffer.  It stores the
 *  N samples which make up a pixel in N separate data array elements.
 *  Different bands may be in different banks of the DataBuffer.
 *  Accessor methods are provided so that image data can be manipulated
 *  directly. This class can support different kinds of interleaving, e.g.
 *  band interleaving, scanline interleaving, and pixel interleaving.
 *  Pixel stride is the number of data array elements between two samples
 *  for the same band on the same scanline. Scanline stride is the number
 *  of data array elements between a given sample and the corresponding sample
 *  in the same column of the next scanline.  Band offsets denote the number
 *  of data array elements from the first data array element of the bank
 *  of the DataBuffer holding each band to the first sample of the band.
 *  The bands are numbered from 0 to N-1.  This class can represent image
 *  data for which each sample is an unsigned integral number which can be
 *  stored in 8, 16, or 32 bits (using <code>DataBuffer.TYPE_BYTE</code>,
 *  <code>DataBuffer.TYPE_USHORT</code>, or <code>DataBuffer.TYPE_INT</code>,
 *  respectively), data for which each sample is a signed integral number
 *  which can be stored in 16 bits (using <code>DataBuffer.TYPE_SHORT</code>),
 *  or data for which each sample is a signed float or double quantity
 *  (using <code>DataBuffer.TYPE_FLOAT</code> or
 *  <code>DataBuffer.TYPE_DOUBLE</code>, respectively).
 *  All samples of a given ComponentSampleModel
 *  are stored with the same precision.  All strides and offsets must be
 *  non-negative.  This class supports
 *  {@link DataBuffer#TYPE_BYTE TYPE_BYTE},
 *  {@link DataBuffer#TYPE_USHORT TYPE_USHORT},
 *  {@link DataBuffer#TYPE_SHORT TYPE_SHORT},
 *  {@link DataBuffer#TYPE_INT TYPE_INT},
 *  {@link DataBuffer#TYPE_FLOAT TYPE_FLOAT},
 *  {@link DataBuffer#TYPE_DOUBLE TYPE_DOUBLE},
 * <p>
 * 该类表示存储的图像数据,使得像素的每个样本占据DataBuffer的一个数据元素。它存储构成N个单独数据阵列元素中的像素的N个样本。
 * 不同的频带可以在DataBuffer访问器方法的不同存储体中以便可以直接操纵图像数据该类可以支持不同种类的交织,例如频带交织,扫描线交织和像素交织。
 * 像素跨越是在相同扫描线上的相同带的两个样本之间的数据阵列元素的数目。
 *  stride是给定采样和下一扫描线的同一列中的相应采样之间的数据阵列元素的数量带偏移表示从保持每个频带的DataBuffer的组的第一数据阵列元素到频带的第一采样的数据阵列元素的数目。
 * 频带从0到N-1编号。
 * 该类可以表示每个sample是一个无符号整数,可以分别存储在8,16或32位(分别使用<code> DataBufferTYPE_BYTE </code>,<code> DataBufferTYPE_U
 * SHORT </code>或<code> DataBufferTYPE_INT </code>每个样本是可以以16位存储的带符号整数数据(使用<code> DataBufferTYPE_SHORT </code>
 * )的数据,或者每个样本是有符号浮点数或双数量的数据(使用<code> DataBufferTYPE_FLOAT < / code>或<code> DataBufferTYPE_DOUBLE </code>
 * )给定ComponentSampleModel的所有样本以相同的精度存储所有步长和偏移必须是非负的。
 * 频带从0到N-1编号。
 * 此类支持{@link DataBuffer#TYPE_BYTE TYPE_BYTE},{@link DataBuffer#TYPE_USHORT TYPE_USHORT},{@link DataBuffer#TYPE_SHORT TYPE_SHORT}
 *  ,{@link DataBuffer#TYPE_INT TYPE_INT},{@link DataBuffer#TYPE_FLOAT TYPE_FLOAT},{@link DataBuffer#TYPE_DOUBLE TYPE_DOUBLE}
 * ,。
 * 频带从0到N-1编号。
 * 
 * 
 *  @see java.awt.image.PixelInterleavedSampleModel
 *  @see java.awt.image.BandedSampleModel
 */

public class ComponentSampleModel extends SampleModel
{
    /** Offsets for all bands in data array elements. */
    protected int bandOffsets[];

    /** Index for each bank storing a band of image data. */
    protected int[] bankIndices;

    /**
     * The number of bands in this
     * <code>ComponentSampleModel</code>.
     * <p>
     * 这个<code> ComponentSampleModel </code>中的波段数
     * 
     */
    protected int numBands = 1;

    /**
     * The number of banks in this
     * <code>ComponentSampleModel</code>.
     * <p>
     *  此<<code> ComponentSampleModel </code>中的库数
     * 
     */
    protected int numBanks = 1;

    /**
     *  Line stride (in data array elements) of the region of image
     *  data described by this ComponentSampleModel.
     * <p>
     *  此ComponentSampleModel描述的图像数据区域的行步长(在数据数组元素中)
     * 
     */
    protected int scanlineStride;

    /** Pixel stride (in data array elements) of the region of image
     *  data described by this ComponentSampleModel.
     * <p>
     *  此ComponentSampleModel描述的数据
     * 
     */
    protected int pixelStride;

    static private native void initIDs();
    static {
        ColorModel.loadLibraries();
        initIDs();
    }

    /**
     * Constructs a ComponentSampleModel with the specified parameters.
     * The number of bands will be given by the length of the bandOffsets array.
     * All bands will be stored in the first bank of the DataBuffer.
     * <p>
     *  构造具有指定参数的ComponentSampleModel频段数将由bandOffsets数组的长度给出所有频段将存储在DataBuffer的第一个库中
     * 
     * 
     * @param dataType  the data type for storing samples
     * @param w         the width (in pixels) of the region of
     *     image data described
     * @param h         the height (in pixels) of the region of
     *     image data described
     * @param pixelStride the pixel stride of the region of image
     *     data described
     * @param scanlineStride the line stride of the region of image
     *     data described
     * @param bandOffsets the offsets of all bands
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     * @throws IllegalArgumentException if <code>pixelStride</code>
     *         is less than 0
     * @throws IllegalArgumentException if <code>scanlineStride</code>
     *         is less than 0
     * @throws IllegalArgumentException if <code>numBands</code>
     *         is less than 1
     * @throws IllegalArgumentException if the product of <code>w</code>
     *         and <code>h</code> is greater than
     *         <code>Integer.MAX_VALUE</code>
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported data types
     */
    public ComponentSampleModel(int dataType,
                                int w, int h,
                                int pixelStride,
                                int scanlineStride,
                                int bandOffsets[]) {
        super(dataType, w, h, bandOffsets.length);
        this.dataType = dataType;
        this.pixelStride = pixelStride;
        this.scanlineStride  = scanlineStride;
        this.bandOffsets = (int[])bandOffsets.clone();
        numBands = this.bandOffsets.length;
        if (pixelStride < 0) {
            throw new IllegalArgumentException("Pixel stride must be >= 0");
        }
        // TODO - bug 4296691 - remove this check
        if (scanlineStride < 0) {
            throw new IllegalArgumentException("Scanline stride must be >= 0");
        }
        if (numBands < 1) {
            throw new IllegalArgumentException("Must have at least one band.");
        }
        if ((dataType < DataBuffer.TYPE_BYTE) ||
            (dataType > DataBuffer.TYPE_DOUBLE)) {
            throw new IllegalArgumentException("Unsupported dataType.");
        }
        bankIndices = new int[numBands];
        for (int i=0; i<numBands; i++) {
            bankIndices[i] = 0;
        }
        verify();
    }


    /**
     * Constructs a ComponentSampleModel with the specified parameters.
     * The number of bands will be given by the length of the bandOffsets array.
     * Different bands may be stored in different banks of the DataBuffer.
     *
     * <p>
     *  构造具有指定参数的ComponentSampleModel带数的数量将由bandOffsets数组的长度给出不同的带可以存储在DataBuffer的不同库中
     * 
     * 
     * @param dataType  the data type for storing samples
     * @param w         the width (in pixels) of the region of
     *     image data described
     * @param h         the height (in pixels) of the region of
     *     image data described
     * @param pixelStride the pixel stride of the region of image
     *     data described
     * @param scanlineStride The line stride of the region of image
     *     data described
     * @param bankIndices the bank indices of all bands
     * @param bandOffsets the band offsets of all bands
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     * @throws IllegalArgumentException if <code>pixelStride</code>
     *         is less than 0
     * @throws IllegalArgumentException if <code>scanlineStride</code>
     *         is less than 0
     * @throws IllegalArgumentException if the length of
     *         <code>bankIndices</code> does not equal the length of
     *         <code>bankOffsets</code>
     * @throws IllegalArgumentException if any of the bank indices
     *         of <code>bandIndices</code> is less than 0
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported data types
     */
    public ComponentSampleModel(int dataType,
                                int w, int h,
                                int pixelStride,
                                int scanlineStride,
                                int bankIndices[],
                                int bandOffsets[]) {
        super(dataType, w, h, bandOffsets.length);
        this.dataType = dataType;
        this.pixelStride = pixelStride;
        this.scanlineStride  = scanlineStride;
        this.bandOffsets = (int[])bandOffsets.clone();
        this.bankIndices = (int[]) bankIndices.clone();
        if (pixelStride < 0) {
            throw new IllegalArgumentException("Pixel stride must be >= 0");
        }
        // TODO - bug 4296691 - remove this check
        if (scanlineStride < 0) {
            throw new IllegalArgumentException("Scanline stride must be >= 0");
        }
        if ((dataType < DataBuffer.TYPE_BYTE) ||
            (dataType > DataBuffer.TYPE_DOUBLE)) {
            throw new IllegalArgumentException("Unsupported dataType.");
        }
        int maxBank = this.bankIndices[0];
        if (maxBank < 0) {
            throw new IllegalArgumentException("Index of bank 0 is less than "+
                                               "0 ("+maxBank+")");
        }
        for (int i=1; i < this.bankIndices.length; i++) {
            if (this.bankIndices[i] > maxBank) {
                maxBank = this.bankIndices[i];
            }
            else if (this.bankIndices[i] < 0) {
                throw new IllegalArgumentException("Index of bank "+i+
                                                   " is less than 0 ("+
                                                   maxBank+")");
            }
        }
        numBanks         = maxBank+1;
        numBands         = this.bandOffsets.length;
        if (this.bandOffsets.length != this.bankIndices.length) {
            throw new IllegalArgumentException("Length of bandOffsets must "+
                                               "equal length of bankIndices.");
        }
        verify();
    }

    private void verify() {
        int requiredSize = getBufferSize();
    }

    /**
     * Returns the size of the data buffer (in data elements) needed
     * for a data buffer that matches this ComponentSampleModel.
     * <p>
     * 返回与此ComponentSampleModel匹配的数据缓冲区所需的数据缓冲区的大小(在数据元素中)
     * 
     */
     private int getBufferSize() {
         int maxBandOff=bandOffsets[0];
         for (int i=1; i<bandOffsets.length; i++) {
             maxBandOff = Math.max(maxBandOff,bandOffsets[i]);
         }

         if (maxBandOff < 0 || maxBandOff > (Integer.MAX_VALUE - 1)) {
             throw new IllegalArgumentException("Invalid band offset");
         }

         if (pixelStride < 0 || pixelStride > (Integer.MAX_VALUE / width)) {
             throw new IllegalArgumentException("Invalid pixel stride");
         }

         if (scanlineStride < 0 || scanlineStride > (Integer.MAX_VALUE / height)) {
             throw new IllegalArgumentException("Invalid scanline stride");
         }

         int size = maxBandOff + 1;

         int val = pixelStride * (width - 1);

         if (val > (Integer.MAX_VALUE - size)) {
             throw new IllegalArgumentException("Invalid pixel stride");
         }

         size += val;

         val = scanlineStride * (height - 1);

         if (val > (Integer.MAX_VALUE - size)) {
             throw new IllegalArgumentException("Invalid scan stride");
         }

         size += val;

         return size;
     }

     /**
      * Preserves band ordering with new step factor...
      * <p>
      *  使用新的步进因子保持带宽排序
      * 
      */
    int []orderBands(int orig[], int step) {
        int map[] = new int[orig.length];
        int ret[] = new int[orig.length];

        for (int i=0; i<map.length; i++) map[i] = i;

        for (int i = 0; i < ret.length; i++) {
            int index = i;
            for (int j = i+1; j < ret.length; j++) {
                if (orig[map[index]] > orig[map[j]]) {
                    index = j;
                }
            }
            ret[map[index]] = i*step;
            map[index]  = map[i];
        }
        return ret;
    }

    /**
     * Creates a new <code>ComponentSampleModel</code> with the specified
     * width and height.  The new <code>SampleModel</code> will have the same
     * number of bands, storage data type, interleaving scheme, and
     * pixel stride as this <code>SampleModel</code>.
     * <p>
     *  创建具有指定宽度和高度的新<Component> SampleModel </code>新<code> SampleModel </code>将具有与此<code>相同数量的波段,存储数据类型,交叉方
     * 案和像素跨度, SampleModel </code>。
     * 
     * 
     * @param w the width of the resulting <code>SampleModel</code>
     * @param h the height of the resulting <code>SampleModel</code>
     * @return a new <code>ComponentSampleModel</code> with the specified size
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     */
    public SampleModel createCompatibleSampleModel(int w, int h) {
        SampleModel ret=null;
        long size;
        int minBandOff=bandOffsets[0];
        int maxBandOff=bandOffsets[0];
        for (int i=1; i<bandOffsets.length; i++) {
            minBandOff = Math.min(minBandOff,bandOffsets[i]);
            maxBandOff = Math.max(maxBandOff,bandOffsets[i]);
        }
        maxBandOff -= minBandOff;

        int bands   = bandOffsets.length;
        int bandOff[];
        int pStride = Math.abs(pixelStride);
        int lStride = Math.abs(scanlineStride);
        int bStride = Math.abs(maxBandOff);

        if (pStride > lStride) {
            if (pStride > bStride) {
                if (lStride > bStride) { // pix > line > band
                    bandOff = new int[bandOffsets.length];
                    for (int i=0; i<bands; i++)
                        bandOff[i] = bandOffsets[i]-minBandOff;
                    lStride = bStride+1;
                    pStride = lStride*h;
                } else { // pix > band > line
                    bandOff = orderBands(bandOffsets,lStride*h);
                    pStride = bands*lStride*h;
                }
            } else { // band > pix > line
                pStride = lStride*h;
                bandOff = orderBands(bandOffsets,pStride*w);
            }
        } else {
            if (pStride > bStride) { // line > pix > band
                bandOff = new int[bandOffsets.length];
                for (int i=0; i<bands; i++)
                    bandOff[i] = bandOffsets[i]-minBandOff;
                pStride = bStride+1;
                lStride = pStride*w;
            } else {
                if (lStride > bStride) { // line > band > pix
                    bandOff = orderBands(bandOffsets,pStride*w);
                    lStride = bands*pStride*w;
                } else { // band > line > pix
                    lStride = pStride*w;
                    bandOff = orderBands(bandOffsets,lStride*h);
                }
            }
        }

        // make sure we make room for negative offsets...
        int base = 0;
        if (scanlineStride < 0) {
            base += lStride*h;
            lStride *= -1;
        }
        if (pixelStride    < 0) {
            base += pStride*w;
            pStride *= -1;
        }

        for (int i=0; i<bands; i++)
            bandOff[i] += base;
        return new ComponentSampleModel(dataType, w, h, pStride,
                                        lStride, bankIndices, bandOff);
    }

    /**
     * Creates a new ComponentSampleModel with a subset of the bands
     * of this ComponentSampleModel.  The new ComponentSampleModel can be
     * used with any DataBuffer that the existing ComponentSampleModel
     * can be used with.  The new ComponentSampleModel/DataBuffer
     * combination will represent an image with a subset of the bands
     * of the original ComponentSampleModel/DataBuffer combination.
     * <p>
     *  使用此ComponentSampleModel的频带的子集创建新的ComponentSampleModel新的ComponentSampleModel可以与现有ComponentSampleModel
     * 可以一起使用的任何DataBuffer一起使用新的ComponentSampleModel / DataBuffer组合将表示具有原始ComponentSampleModel的频带的子集的图像/ Dat
     * aBuffer组合。
     * 
     * 
     * @param bands a subset of bands from this
     *              <code>ComponentSampleModel</code>
     * @return a <code>ComponentSampleModel</code> created with a subset
     *          of bands from this <code>ComponentSampleModel</code>.
     */
    public SampleModel createSubsetSampleModel(int bands[]) {
       if (bands.length > bankIndices.length)
            throw new RasterFormatException("There are only " +
                                            bankIndices.length +
                                            " bands");
        int newBankIndices[] = new int[bands.length];
        int newBandOffsets[] = new int[bands.length];

        for (int i=0; i<bands.length; i++) {
            newBankIndices[i] = bankIndices[bands[i]];
            newBandOffsets[i] = bandOffsets[bands[i]];
        }

        return new ComponentSampleModel(this.dataType, width, height,
                                        this.pixelStride,
                                        this.scanlineStride,
                                        newBankIndices, newBandOffsets);
    }

    /**
     * Creates a <code>DataBuffer</code> that corresponds to this
     * <code>ComponentSampleModel</code>.
     * The <code>DataBuffer</code> object's data type, number of banks,
     * and size are be consistent with this <code>ComponentSampleModel</code>.
     * <p>
     * 创建与此<code> ComponentSampleModel </code>对应的<code> DataBuffer </code> <code> DataBuffer </code>对象的数据类型,
     * 组数和大小与此<code> ComponentSampleModel </code>。
     * 
     * 
     * @return a <code>DataBuffer</code> whose data type, number of banks
     *         and size are consistent with this
     *         <code>ComponentSampleModel</code>.
     */
    public DataBuffer createDataBuffer() {
        DataBuffer dataBuffer = null;

        int size = getBufferSize();
        switch (dataType) {
        case DataBuffer.TYPE_BYTE:
            dataBuffer = new DataBufferByte(size, numBanks);
            break;
        case DataBuffer.TYPE_USHORT:
            dataBuffer = new DataBufferUShort(size, numBanks);
            break;
        case DataBuffer.TYPE_SHORT:
            dataBuffer = new DataBufferShort(size, numBanks);
            break;
        case DataBuffer.TYPE_INT:
            dataBuffer = new DataBufferInt(size, numBanks);
            break;
        case DataBuffer.TYPE_FLOAT:
            dataBuffer = new DataBufferFloat(size, numBanks);
            break;
        case DataBuffer.TYPE_DOUBLE:
            dataBuffer = new DataBufferDouble(size, numBanks);
            break;
        }

        return dataBuffer;
    }


    /** Gets the offset for the first band of pixel (x,y).
     *  A sample of the first band can be retrieved from a
     * <code>DataBuffer</code>
     *  <code>data</code> with a <code>ComponentSampleModel</code>
     * <code>csm</code> as
     * <pre>
     *        data.getElem(csm.getOffset(x, y));
     * </pre>
     * <p>
     *  第一频带的样本可以从具有<code> ComponentSampleModel </code> <code> csm </code>的<code> DataBuffer </code> <code> 
     * data </code>。
     * <pre>
     *  datagetElem(csmgetOffset(x,y));
     * </pre>
     * 
     * @param x the X location of the pixel
     * @param y the Y location of the pixel
     * @return the offset for the first band of the specified pixel.
     */
    public int getOffset(int x, int y) {
        int offset = y*scanlineStride + x*pixelStride + bandOffsets[0];
        return offset;
    }

    /** Gets the offset for band b of pixel (x,y).
     *  A sample of band <code>b</code> can be retrieved from a
     *  <code>DataBuffer</code> <code>data</code>
     *  with a <code>ComponentSampleModel</code> <code>csm</code> as
     * <pre>
     *       data.getElem(csm.getOffset(x, y, b));
     * </pre>
     * <p>
     *  可以从具有<code> ComponentSampleModel </code> <code> csm </code的<code> DataBuffer </code> <code> data </code>
     * 中检索频带<code> b </code> > as。
     * <pre>
     *  datagetElem(csmgetOffset(x,y,b));
     * </pre>
     * 
     * @param x the X location of the specified pixel
     * @param y the Y location of the specified pixel
     * @param b the specified band
     * @return the offset for the specified band of the specified pixel.
     */
    public int getOffset(int x, int y, int b) {
        int offset = y*scanlineStride + x*pixelStride + bandOffsets[b];
        return offset;
    }

    /** Returns the number of bits per sample for all bands.
    /* <p>
    /* 
     *  @return an array containing the number of bits per sample
     *          for all bands, where each element in the array
     *          represents a band.
     */
    public final int[] getSampleSize() {
        int sampleSize[] = new int [numBands];
        int sizeInBits = getSampleSize(0);

        for (int i=0; i<numBands; i++)
            sampleSize[i] = sizeInBits;

        return sampleSize;
    }

    /** Returns the number of bits per sample for the specified band.
    /* <p>
    /* 
     *  @param band the specified band
     *  @return the number of bits per sample for the specified band.
     */
    public final int getSampleSize(int band) {
        return DataBuffer.getDataTypeSize(dataType);
    }

    /** Returns the bank indices for all bands.
    /* <p>
    /* 
     *  @return the bank indices for all bands.
     */
    public final int [] getBankIndices() {
        return (int[]) bankIndices.clone();
    }

    /** Returns the band offset for all bands.
    /* <p>
    /* 
     *  @return the band offsets for all bands.
     */
    public final int [] getBandOffsets() {
        return (int[])bandOffsets.clone();
    }

    /** Returns the scanline stride of this ComponentSampleModel.
    /* <p>
    /* 
     *  @return the scanline stride of this <code>ComponentSampleModel</code>.
     */
    public final int getScanlineStride() {
        return scanlineStride;
    }

    /** Returns the pixel stride of this ComponentSampleModel.
    /* <p>
    /* 
     *  @return the pixel stride of this <code>ComponentSampleModel</code>.
     */
    public final int getPixelStride() {
        return pixelStride;
    }

    /**
     * Returns the number of data elements needed to transfer a pixel
     * with the
     * {@link #getDataElements(int, int, Object, DataBuffer) } and
     * {@link #setDataElements(int, int, Object, DataBuffer) }
     * methods.
     * For a <code>ComponentSampleModel</code>, this is identical to the
     * number of bands.
     * <p>
     * 返回使用{@link #getDataElements(int,int,Object,DataBuffer)}和{@link #setDataElements(int,int,Object,DataBuffer)}
     * 方法传输像素所需的数据元素数对于<code> ComponentSampleModel </code>,这与频带数量相同。
     * 
     * 
     * @return the number of data elements needed to transfer a pixel with
     *         the <code>getDataElements</code> and
     *         <code>setDataElements</code> methods.
     * @see java.awt.image.SampleModel#getNumDataElements
     * @see #getNumBands
     */
    public final int getNumDataElements() {
        return getNumBands();
    }

    /**
     * Returns data for a single pixel in a primitive array of type
     * <code>TransferType</code>.  For a <code>ComponentSampleModel</code>,
     * this is the same as the data type, and samples are returned
     * one per array element.  Generally, <code>obj</code> should
     * be passed in as <code>null</code>, so that the <code>Object</code>
     * is created automatically and is the right primitive data type.
     * <p>
     * The following code illustrates transferring data for one pixel from
     * <code>DataBuffer</code> <code>db1</code>, whose storage layout is
     * described by <code>ComponentSampleModel</code> <code>csm1</code>,
     * to <code>DataBuffer</code> <code>db2</code>, whose storage layout
     * is described by <code>ComponentSampleModel</code> <code>csm2</code>.
     * The transfer is usually more efficient than using
     * <code>getPixel</code> and <code>setPixel</code>.
     * <pre>
     *       ComponentSampleModel csm1, csm2;
     *       DataBufferInt db1, db2;
     *       csm2.setDataElements(x, y,
     *                            csm1.getDataElements(x, y, null, db1), db2);
     * </pre>
     *
     * Using <code>getDataElements</code> and <code>setDataElements</code>
     * to transfer between two <code>DataBuffer/SampleModel</code>
     * pairs is legitimate if the <code>SampleModel</code> objects have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the <code>TransferType</code>s are the same.
     * <p>
     * If <code>obj</code> is not <code>null</code>, it should be a
     * primitive array of type <code>TransferType</code>.
     * Otherwise, a <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> might be thrown if the
     * coordinates are not in bounds, or if <code>obj</code> is not
     * <code>null</code> and is not large enough to hold
     * the pixel data.
     *
     * <p>
     *  返回<code> TransferType </code>类型的基本数组中的单个像素的数据对于<code> ComponentSampleModel </code>,这与数据类型相同,并且每个数组元素
     * 返回一个样本。
     *  <code> obj </code>应该作为<code> null </code>传递,以便自动创建<code> Object </code>,并且是正确的基本数据类型。
     * <p>
     * 以下代码说明从<code> DataBuffer </code> <code> db1 </code>传输一个像素的数据,其存储布局由<code> ComponentSampleModel </code>
     *  <code> csm1 </code>到<code> DataBuffer </code> <code> db2 </code>,其存储布局由<code> ComponentSampleModel </code>
     *  <code> csm2 </code>描述。
     *  > getPixel </code>和<code> setPixel </code>。
     * <pre>
     *  ComponentSampleModel csm1,csm2; DataBufferInt db1,db2; csm2setDataElements(x,y,csm1getDataElements(x
     * ,y,null,db1),db2);。
     * </pre>
     * 
     * 如果<code> SampleModel </code>对象具有相同的数字,则使用<code> getDataElements </code>和<code> setDataElements </code>
     * 在两个<code> DataBuffer / SampleModel </code>的带,对应的带具有每个样本相同的位数,并且<code> TransferType </code>是相同的。
     * <p>
     *  如果<code> obj </code>不是<code> null </code>,它应该是类型<code> TransferType </code>的原始数组否则,抛出一个<code> ClassC
     * astException </code>如果坐标不在边界中,或者<code> obj </code>不是<code> null </code>且不足以容纳像素数据,则可能会抛出<code> ArrayI
     * ndexOutOfBoundsException </code>。
     * 
     * 
     * @param x         the X coordinate of the pixel location
     * @param y         the Y coordinate of the pixel location
     * @param obj       if non-<code>null</code>, a primitive array
     *                  in which to return the pixel data
     * @param data      the <code>DataBuffer</code> containing the image data
     * @return the data of the specified pixel
     * @see #setDataElements(int, int, Object, DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if obj is too small to hold the output.
     */
    public Object getDataElements(int x, int y, Object obj, DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        int type = getTransferType();
        int numDataElems = getNumDataElements();
        int pixelOffset = y*scanlineStride + x*pixelStride;

        switch(type) {

        case DataBuffer.TYPE_BYTE:

            byte[] bdata;

            if (obj == null)
                bdata = new byte[numDataElems];
            else
                bdata = (byte[])obj;

            for (int i=0; i<numDataElems; i++) {
                bdata[i] = (byte)data.getElem(bankIndices[i],
                                              pixelOffset + bandOffsets[i]);
            }

            obj = (Object)bdata;
            break;

        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:

            short[] sdata;

            if (obj == null)
                sdata = new short[numDataElems];
            else
                sdata = (short[])obj;

            for (int i=0; i<numDataElems; i++) {
                sdata[i] = (short)data.getElem(bankIndices[i],
                                               pixelOffset + bandOffsets[i]);
            }

            obj = (Object)sdata;
            break;

        case DataBuffer.TYPE_INT:

            int[] idata;

            if (obj == null)
                idata = new int[numDataElems];
            else
                idata = (int[])obj;

            for (int i=0; i<numDataElems; i++) {
                idata[i] = data.getElem(bankIndices[i],
                                        pixelOffset + bandOffsets[i]);
            }

            obj = (Object)idata;
            break;

        case DataBuffer.TYPE_FLOAT:

            float[] fdata;

            if (obj == null)
                fdata = new float[numDataElems];
            else
                fdata = (float[])obj;

            for (int i=0; i<numDataElems; i++) {
                fdata[i] = data.getElemFloat(bankIndices[i],
                                             pixelOffset + bandOffsets[i]);
            }

            obj = (Object)fdata;
            break;

        case DataBuffer.TYPE_DOUBLE:

            double[] ddata;

            if (obj == null)
                ddata = new double[numDataElems];
            else
                ddata = (double[])obj;

            for (int i=0; i<numDataElems; i++) {
                ddata[i] = data.getElemDouble(bankIndices[i],
                                              pixelOffset + bandOffsets[i]);
            }

            obj = (Object)ddata;
            break;
        }

        return obj;
    }

    /**
     * Returns all samples for the specified pixel in an int array,
     * one sample per array element.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if
     * the coordinates are not in bounds.
     * <p>
     * 返回int数组中指定像素的所有样本,每个数组元素一个样本如果坐标不在边界中,可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 
     * 
     * @param x         the X coordinate of the pixel location
     * @param y         the Y coordinate of the pixel location
     * @param iArray    If non-null, returns the samples in this array
     * @param data      The DataBuffer containing the image data
     * @return the samples of the specified pixel.
     * @see #setPixel(int, int, int[], DataBuffer)
     *
     * @throws NullPointerException if data is null.
     * @throws ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if iArray is too small to hold the output.
     */
    public int[] getPixel(int x, int y, int iArray[], DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int pixels[];
        if (iArray != null) {
           pixels = iArray;
        } else {
           pixels = new int [numBands];
        }
        int pixelOffset = y*scanlineStride + x*pixelStride;
        for (int i=0; i<numBands; i++) {
            pixels[i] = data.getElem(bankIndices[i],
                                     pixelOffset + bandOffsets[i]);
        }
        return pixels;
    }

    /**
     * Returns all samples for the specified rectangle of pixels in
     * an int array, one sample per array element.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if
     * the coordinates are not in bounds.
     * <p>
     *  返回int数组中指定的像素矩形的所有样本,每个数组元素一个样本如果坐标不在边界中,可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location
     * @param y         The Y coordinate of the upper left pixel location
     * @param w         The width of the pixel rectangle
     * @param h         The height of the pixel rectangle
     * @param iArray    If non-null, returns the samples in this array
     * @param data      The DataBuffer containing the image data
     * @return the samples of the pixels within the specified region.
     * @see #setPixels(int, int, int, int, int[], DataBuffer)
     */
    public int[] getPixels(int x, int y, int w, int h,
                           int iArray[], DataBuffer data) {
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || y > height || y1 < 0 || y1 >  height)
        {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int pixels[];
        if (iArray != null) {
           pixels = iArray;
        } else {
           pixels = new int [w*h*numBands];
        }
        int lineOffset = y*scanlineStride + x*pixelStride;
        int srcOffset = 0;

        for (int i = 0; i < h; i++) {
           int pixelOffset = lineOffset;
           for (int j = 0; j < w; j++) {
              for (int k=0; k < numBands; k++) {
                 pixels[srcOffset++] =
                    data.getElem(bankIndices[k], pixelOffset + bandOffsets[k]);
              }
              pixelOffset += pixelStride;
           }
           lineOffset += scanlineStride;
        }
        return pixels;
    }

    /**
     * Returns as int the sample in a specified band for the pixel
     * located at (x,y).
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if
     * the coordinates are not in bounds.
     * <p>
     *  以位于(x,y)的像素的指定带中的样本为int返回如果坐标不在边界中,可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 
     * 
     * @param x         the X coordinate of the pixel location
     * @param y         the Y coordinate of the pixel location
     * @param b         the band to return
     * @param data      the <code>DataBuffer</code> containing the image data
     * @return the sample in a specified band for the specified pixel
     * @see #setSample(int, int, int, int, DataBuffer)
     */
    public int getSample(int x, int y, int b, DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int sample = data.getElem(bankIndices[b],
                                  y*scanlineStride + x*pixelStride +
                                  bandOffsets[b]);
        return sample;
    }

    /**
     * Returns the sample in a specified band
     * for the pixel located at (x,y) as a float.
     * An <code>ArrayIndexOutOfBoundsException</code> might be
     * thrown if the coordinates are not in bounds.
     * <p>
     *  以位于(x,y)的像素作为浮点返回指定带中的样本如果坐标不在边界中,可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         The band to return
     * @param data      The DataBuffer containing the image data
     * @return a float value representing the sample in the specified
     * band for the specified pixel.
     */
    public float getSampleFloat(int x, int y, int b, DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        float sample = data.getElemFloat(bankIndices[b],
                                         y*scanlineStride + x*pixelStride +
                                         bandOffsets[b]);
        return sample;
    }

    /**
     * Returns the sample in a specified band
     * for a pixel located at (x,y) as a double.
     * An <code>ArrayIndexOutOfBoundsException</code> might be
     * thrown if the coordinates are not in bounds.
     * <p>
     * 以位于(x,y)的像素为单位返回指定带中的样本作为double如果坐标不在边界中,则可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         The band to return
     * @param data      The DataBuffer containing the image data
     * @return a double value representing the sample in the specified
     * band for the specified pixel.
     */
    public double getSampleDouble(int x, int y, int b, DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        double sample = data.getElemDouble(bankIndices[b],
                                           y*scanlineStride + x*pixelStride +
                                           bandOffsets[b]);
        return sample;
    }

    /**
     * Returns the samples in a specified band for the specified rectangle
     * of pixels in an int array, one sample per data array element.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if
     * the coordinates are not in bounds.
     * <p>
     *  返回在int数组中指定像素矩形的指定带中的样本,每个数据数组元素一个样本如果坐标不在边界中,可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location
     * @param y         The Y coordinate of the upper left pixel location
     * @param w         the width of the pixel rectangle
     * @param h         the height of the pixel rectangle
     * @param b         the band to return
     * @param iArray    if non-<code>null</code>, returns the samples
     *                  in this array
     * @param data      the <code>DataBuffer</code> containing the image data
     * @return the samples in the specified band of the specified pixel
     * @see #setSamples(int, int, int, int, int, int[], DataBuffer)
     */
    public int[] getSamples(int x, int y, int w, int h, int b,
                            int iArray[], DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x + w > width) || (y + h > height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int samples[];
        if (iArray != null) {
           samples = iArray;
        } else {
           samples = new int [w*h];
        }
        int lineOffset = y*scanlineStride + x*pixelStride +  bandOffsets[b];
        int srcOffset = 0;

        for (int i = 0; i < h; i++) {
           int sampleOffset = lineOffset;
           for (int j = 0; j < w; j++) {
              samples[srcOffset++] = data.getElem(bankIndices[b],
                                                  sampleOffset);
              sampleOffset += pixelStride;
           }
           lineOffset += scanlineStride;
        }
        return samples;
    }

    /**
     * Sets the data for a single pixel in the specified
     * <code>DataBuffer</code> from a primitive array of type
     * <code>TransferType</code>.  For a <code>ComponentSampleModel</code>,
     * this is the same as the data type, and samples are transferred
     * one per array element.
     * <p>
     * The following code illustrates transferring data for one pixel from
     * <code>DataBuffer</code> <code>db1</code>, whose storage layout is
     * described by <code>ComponentSampleModel</code> <code>csm1</code>,
     * to <code>DataBuffer</code> <code>db2</code>, whose storage layout
     * is described by <code>ComponentSampleModel</code> <code>csm2</code>.
     * The transfer is usually more efficient than using
     * <code>getPixel</code> and <code>setPixel</code>.
     * <pre>
     *       ComponentSampleModel csm1, csm2;
     *       DataBufferInt db1, db2;
     *       csm2.setDataElements(x, y, csm1.getDataElements(x, y, null, db1),
     *                            db2);
     * </pre>
     * Using <code>getDataElements</code> and <code>setDataElements</code>
     * to transfer between two <code>DataBuffer/SampleModel</code> pairs
     * is legitimate if the <code>SampleModel</code> objects have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the <code>TransferType</code>s are the same.
     * <p>
     * A <code>ClassCastException</code> is thrown if <code>obj</code> is not
     * a primitive array of type <code>TransferType</code>.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if
     * the coordinates are not in bounds, or if <code>obj</code> is not large
     * enough to hold the pixel data.
     * <p>
     *  在<code> TransferType </code>类型的基本数组中为指定的<code> DataBuffer </code>中的单个像素设置数据。
     * 对于<code> ComponentSampleModel </code>,这与数据相同类型,并且每个数组元素传输一个样本。
     * <p>
     * 以下代码说明从<code> DataBuffer </code> <code> db1 </code>传输一个像素的数据,其存储布局由<code> ComponentSampleModel </code>
     *  <code> csm1 </code>到<code> DataBuffer </code> <code> db2 </code>,其存储布局由<code> ComponentSampleModel </code>
     *  <code> csm2 </code>描述。
     *  > getPixel </code>和<code> setPixel </code>。
     * <pre>
     *  ComponentSampleModel csm1,csm2; DataBufferInt db1,db2; csm2setDataElements(x,y,csm1getDataElements(x
     * ,y,null,db1),db2);。
     * </pre>
     * 如果<code> SampleModel </code>对象具有相同的数字,则使用<code> getDataElements </code>和<code> setDataElements </code>
     * 在两个<code> DataBuffer / SampleModel </code>的带,对应的带具有每个样本相同的位数,并且<code> TransferType </code>是相同的。
     * <p>
     *  如果<code> obj </code>不是类型<code> TransferType </code>的原始数组,则会抛出<class> ClassCastException </code> </code>
     * 如果坐标为true,则可能会抛出<code> ArrayIndexOutOfBoundsException </code>不在边界中,或者如果<code> obj </code>不够大,不足以容纳像素数
     * 据。
     * 
     * 
     * @param x         the X coordinate of the pixel location
     * @param y         the Y coordinate of the pixel location
     * @param obj       a primitive array containing pixel data
     * @param data      the DataBuffer containing the image data
     * @see #getDataElements(int, int, Object, DataBuffer)
     */
    public void setDataElements(int x, int y, Object obj, DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        int type = getTransferType();
        int numDataElems = getNumDataElements();
        int pixelOffset = y*scanlineStride + x*pixelStride;

        switch(type) {

        case DataBuffer.TYPE_BYTE:

            byte[] barray = (byte[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElem(bankIndices[i], pixelOffset + bandOffsets[i],
                           ((int)barray[i])&0xff);
            }
            break;

        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:

            short[] sarray = (short[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElem(bankIndices[i], pixelOffset + bandOffsets[i],
                           ((int)sarray[i])&0xffff);
            }
            break;

        case DataBuffer.TYPE_INT:

            int[] iarray = (int[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElem(bankIndices[i],
                             pixelOffset + bandOffsets[i], iarray[i]);
            }
            break;

        case DataBuffer.TYPE_FLOAT:

            float[] farray = (float[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElemFloat(bankIndices[i],
                             pixelOffset + bandOffsets[i], farray[i]);
            }
            break;

        case DataBuffer.TYPE_DOUBLE:

            double[] darray = (double[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElemDouble(bankIndices[i],
                             pixelOffset + bandOffsets[i], darray[i]);
            }
            break;

        }
    }

    /**
     * Sets a pixel in the <code>DataBuffer</code> using an int array of
     * samples for input.  An <code>ArrayIndexOutOfBoundsException</code>
     * might be thrown if the coordinates are
     * not in bounds.
     * <p>
     * 设置<code> DataBuffer </code>中的像素使用输入的样本的int数组如果坐标不在边界中,可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param iArray    The input samples in an int array
     * @param data      The DataBuffer containing the image data
     * @see #getPixel(int, int, int[], DataBuffer)
     */
    public void setPixel(int x, int y, int iArray[], DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
       int pixelOffset = y*scanlineStride + x*pixelStride;
       for (int i=0; i<numBands; i++) {
           data.setElem(bankIndices[i],
                        pixelOffset + bandOffsets[i],iArray[i]);
       }
    }

    /**
     * Sets all samples for a rectangle of pixels from an int array containing
     * one sample per array element.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if the
     * coordinates are not in bounds.
     * <p>
     *  设置来自包含每个数组元素一个样本的int数组的像素矩形的所有样本如果坐标不在边界中,可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location
     * @param y         The Y coordinate of the upper left pixel location
     * @param w         The width of the pixel rectangle
     * @param h         The height of the pixel rectangle
     * @param iArray    The input samples in an int array
     * @param data      The DataBuffer containing the image data
     * @see #getPixels(int, int, int, int, int[], DataBuffer)
     */
    public void setPixels(int x, int y, int w, int h,
                          int iArray[], DataBuffer data) {
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 >  height)
        {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        int lineOffset = y*scanlineStride + x*pixelStride;
        int srcOffset = 0;

        for (int i = 0; i < h; i++) {
           int pixelOffset = lineOffset;
           for (int j = 0; j < w; j++) {
              for (int k=0; k < numBands; k++) {
                 data.setElem(bankIndices[k], pixelOffset + bandOffsets[k],
                              iArray[srcOffset++]);
              }
              pixelOffset += pixelStride;
           }
           lineOffset += scanlineStride;
        }
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the <code>DataBuffer</code> using an int for input.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if the
     * coordinates are not in bounds.
     * <p>
     *  使用输入的int为<code> DataBuffer </code>中位于(x,y)的像素设置指定频带中的样本。
     * 如果坐标不在,则可能会抛出<code> ArrayIndexOutOfBoundsException </code>边界。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         the band to set
     * @param s         the input sample as an int
     * @param data      the DataBuffer containing the image data
     * @see #getSample(int, int, int, DataBuffer)
     */
    public void setSample(int x, int y, int b, int s,
                          DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        data.setElem(bankIndices[b],
                     y*scanlineStride + x*pixelStride + bandOffsets[b], s);
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the <code>DataBuffer</code> using a float for input.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if
     * the coordinates are not in bounds.
     * <p>
     * 使用输入的float为<code> DataBuffer </code>中的(x,y)处的像素设置指定频带中的样本。
     * 如果坐标不在,则可能会抛出<code> ArrayIndexOutOfBoundsException </code>边界。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         The band to set
     * @param s         The input sample as a float
     * @param data      The DataBuffer containing the image data
     * @see #getSample(int, int, int, DataBuffer)
     */
    public void setSample(int x, int y, int b,
                          float s ,
                          DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        data.setElemFloat(bankIndices[b],
                          y*scanlineStride + x*pixelStride + bandOffsets[b],
                          s);
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the <code>DataBuffer</code> using a double for input.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if
     * the coordinates are not in bounds.
     * <p>
     *  在<code> DataBuffer </code>中为(x,y)的像素设置指定频带中的样本,使用double作为输入。
     * 如果坐标不在,则可能会抛出<code> ArrayIndexOutOfBoundsException </code>边界。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         The band to set
     * @param s         The input sample as a double
     * @param data      The DataBuffer containing the image data
     * @see #getSample(int, int, int, DataBuffer)
     */
    public void setSample(int x, int y, int b,
                          double s,
                          DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        data.setElemDouble(bankIndices[b],
                          y*scanlineStride + x*pixelStride + bandOffsets[b],
                          s);
    }

    /**
     * Sets the samples in the specified band for the specified rectangle
     * of pixels from an int array containing one sample per data array element.
     * An <code>ArrayIndexOutOfBoundsException</code> might be thrown if the
     * coordinates are not in bounds.
     * <p>
     *  对于指定的像素矩形,从一个包含每个数据数组元素一个样本的int数组中设置指定范围的样本如果坐标不在边界中,可能会抛出<code> ArrayIndexOutOfBoundsException </code>
     * 。
     * 
     * @param x         The X coordinate of the upper left pixel location
     * @param y         The Y coordinate of the upper left pixel location
     * @param w         The width of the pixel rectangle
     * @param h         The height of the pixel rectangle
     * @param b         The band to set
     * @param iArray    The input samples in an int array
     * @param data      The DataBuffer containing the image data
     * @see #getSamples(int, int, int, int, int, int[], DataBuffer)
     */
    public void setSamples(int x, int y, int w, int h, int b,
                           int iArray[], DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x + w > width) || (y + h > height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int lineOffset = y*scanlineStride + x*pixelStride + bandOffsets[b];
        int srcOffset = 0;

        for (int i = 0; i < h; i++) {
           int sampleOffset = lineOffset;
           for (int j = 0; j < w; j++) {
              data.setElem(bankIndices[b], sampleOffset, iArray[srcOffset++]);
              sampleOffset += pixelStride;
           }
           lineOffset += scanlineStride;
        }
    }

    public boolean equals(Object o) {
        if ((o == null) || !(o instanceof ComponentSampleModel)) {
            return false;
        }

        ComponentSampleModel that = (ComponentSampleModel)o;
        return this.width == that.width &&
            this.height == that.height &&
            this.numBands == that.numBands &&
            this.dataType == that.dataType &&
            Arrays.equals(this.bandOffsets, that.bandOffsets) &&
            Arrays.equals(this.bankIndices, that.bankIndices) &&
            this.numBands == that.numBands &&
            this.numBanks == that.numBanks &&
            this.scanlineStride == that.scanlineStride &&
            this.pixelStride == that.pixelStride;
    }

    // If we implement equals() we must also implement hashCode
    public int hashCode() {
        int hash = 0;
        hash = width;
        hash <<= 8;
        hash ^= height;
        hash <<= 8;
        hash ^= numBands;
        hash <<= 8;
        hash ^= dataType;
        hash <<= 8;
        for (int i = 0; i < bandOffsets.length; i++) {
            hash ^= bandOffsets[i];
            hash <<= 8;
        }
        for (int i = 0; i < bankIndices.length; i++) {
            hash ^= bankIndices[i];
            hash <<= 8;
        }
        hash ^= numBands;
        hash <<= 8;
        hash ^= numBanks;
        hash <<= 8;
        hash ^= scanlineStride;
        hash <<= 8;
        hash ^= pixelStride;
        return hash;
    }
}
