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

/**
 *  This class represents image data which is stored in a band interleaved
 *  fashion and for
 *  which each sample of a pixel occupies one data element of the DataBuffer.
 *  It subclasses ComponentSampleModel but provides a more efficient
 *  implementation for accessing band interleaved image data than is provided
 *  by ComponentSampleModel.  This class should typically be used when working
 *  with images which store sample data for each band in a different bank of the
 *  DataBuffer. Accessor methods are provided so that image data can be
 *  manipulated directly. Pixel stride is the number of
 *  data array elements between two samples for the same band on the same
 *  scanline. The pixel stride for a BandedSampleModel is one.
 *  Scanline stride is the number of data array elements between
 *  a given sample and the corresponding sample in the same column of the next
 *  scanline.  Band offsets denote the number
 *  of data array elements from the first data array element of the bank
 *  of the DataBuffer holding each band to the first sample of the band.
 *  The bands are numbered from 0 to N-1.
 *  Bank indices denote the correspondence between a bank of the data buffer
 *  and a band of image data.  This class supports
 *  {@link DataBuffer#TYPE_BYTE TYPE_BYTE},
 *  {@link DataBuffer#TYPE_USHORT TYPE_USHORT},
 *  {@link DataBuffer#TYPE_SHORT TYPE_SHORT},
 *  {@link DataBuffer#TYPE_INT TYPE_INT},
 *  {@link DataBuffer#TYPE_FLOAT TYPE_FLOAT}, and
 *  {@link DataBuffer#TYPE_DOUBLE TYPE_DOUBLE} datatypes
 * <p>
 * 该类表示以带交织方式存储的图像数据,并且像素的每个样本占据DataBuffer的一个数据元素。
 * 它将ComponentSampleModel作为子类,但提供了一种比ComponentSampleModel提供的更高效的访问带交叉图像数据的实现。
 * 当使用存储DataBuffer的不同bank中每个频带的样本数据的图像时,通常应该使用这个类。提供访问器方法,使得可以直接操纵图像数据。
 * 像素跨距是在相同扫描线上的相同带的两个样本之间的数据阵列元素的数量。 BandedSampleModel的像素跨度为一。扫描线跨距是给定样本和下一扫描线的同一列中的相应样本之间的数据阵列元素的数量。
 * 带偏移表示从保持每个频带的DataBuffer的组的第一数据阵列元素到频带的第一样本的数据阵列元素的数目。频带从0到N-1编号。存储体索引表示数据缓冲器的存储体和图像数据的带之间的对应关系。
 * 此类支持{@link DataBuffer#TYPE_BYTE TYPE_BYTE},{@link DataBuffer#TYPE_USHORT TYPE_USHORT},{@link DataBuffer#TYPE_SHORT TYPE_SHORT}
 * ,{@link DataBuffer#TYPE_INT TYPE_INT},{@link DataBuffer#TYPE_FLOAT TYPE_FLOAT} @link DataBuffer#TYPE_
 * DOUBLE TYPE_DOUBLE}数据类型。
 * 带偏移表示从保持每个频带的DataBuffer的组的第一数据阵列元素到频带的第一样本的数据阵列元素的数目。频带从0到N-1编号。存储体索引表示数据缓冲器的存储体和图像数据的带之间的对应关系。
 * 
 */


public final class BandedSampleModel extends ComponentSampleModel
{

    /**
     * Constructs a BandedSampleModel with the specified parameters.
     * The pixel stride will be one data element.  The scanline stride
     * will be the same as the width.  Each band will be stored in
     * a separate bank and all band offsets will be zero.
     * <p>
     * 构造具有指定参数的BandedSampleModel。像素跨度将是一个数据元素。扫描线跨度将与宽度相同。每个频带将存储在单独的库中,并且所有频带偏移将为零。
     * 
     * 
     * @param dataType  The data type for storing samples.
     * @param w         The width (in pixels) of the region of
     *                  image data described.
     * @param h         The height (in pixels) of the region of image
     *                  data described.
     * @param numBands  The number of bands for the image data.
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported data types
     */
    public BandedSampleModel(int dataType, int w, int h, int numBands) {
        super(dataType, w, h, 1, w,
              BandedSampleModel.createIndicesArray(numBands),
              BandedSampleModel.createOffsetArray(numBands));
    }

    /**
     * Constructs a BandedSampleModel with the specified parameters.
     * The number of bands will be inferred from the lengths of the
     * bandOffsets bankIndices arrays, which must be equal.  The pixel
     * stride will be one data element.
     * <p>
     *  构造具有指定参数的BandedSampleModel。带的数量将从bandOffsets bankIndices数组的长度推断,这些数组必须相等。像素跨度将是一个数据元素。
     * 
     * 
     * @param dataType  The data type for storing samples.
     * @param w         The width (in pixels) of the region of
     *                  image data described.
     * @param h         The height (in pixels) of the region of
     *                  image data described.
     * @param scanlineStride The line stride of the of the image data.
     * @param bankIndices The bank index for each band.
     * @param bandOffsets The band offset for each band.
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported data types
     */
    public BandedSampleModel(int dataType,
                             int w, int h,
                             int scanlineStride,
                             int bankIndices[],
                             int bandOffsets[]) {

        super(dataType, w, h, 1,scanlineStride, bankIndices, bandOffsets);
    }

    /**
     * Creates a new BandedSampleModel with the specified
     * width and height.  The new BandedSampleModel will have the same
     * number of bands, storage data type, and bank indices
     * as this BandedSampleModel.  The band offsets will be compressed
     * such that the offset between bands will be w*pixelStride and
     * the minimum of all of the band offsets is zero.
     * <p>
     *  创建具有指定宽度和高度的新BandedSampleModel。新的BandedSampleModel将具有与此BandedSampleModel相同的频带数,存储数据类型和bank索引。
     * 带偏移将被压缩,使得带之间的偏移将是w * pixelStride,并且所有带偏移的最小值是零。
     * 
     * 
     * @param w the width of the resulting <code>BandedSampleModel</code>
     * @param h the height of the resulting <code>BandedSampleModel</code>
     * @return a new <code>BandedSampleModel</code> with the specified
     *         width and height.
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> equals either
     *         <code>Integer.MAX_VALUE</code> or
     *         <code>Integer.MIN_VALUE</code>
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported data types
     */
    public SampleModel createCompatibleSampleModel(int w, int h) {
        int[] bandOffs;

        if (numBanks == 1) {
            bandOffs = orderBands(bandOffsets, w*h);
        }
        else {
            bandOffs = new int[bandOffsets.length];
        }

        SampleModel sampleModel =
            new BandedSampleModel(dataType, w, h, w, bankIndices, bandOffs);
        return sampleModel;
    }

    /**
     * Creates a new BandedSampleModel with a subset of the bands of this
     * BandedSampleModel.  The new BandedSampleModel can be
     * used with any DataBuffer that the existing BandedSampleModel
     * can be used with.  The new BandedSampleModel/DataBuffer
     * combination will represent an image with a subset of the bands
     * of the original BandedSampleModel/DataBuffer combination.
     * <p>
     *  使用此BandedSampleModel的波段子集创建新的BandedSampleModel。
     * 新的BandedSampleModel可以与现有的BandedSampleModel一起使用的任何DataBuffer一起使用。
     * 新的BandedSampleModel / DataBuffer组合将表示具有原始BandedSampleModel / DataBuffer组合的带的子集的图像。
     * 
     * 
     * @throws RasterFormatException if the number of bands is greater than
     *                               the number of banks in this sample model.
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported data types
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

        return new BandedSampleModel(this.dataType, width, height,
                                     this.scanlineStride,
                                     newBankIndices, newBandOffsets);
    }

    /**
     * Creates a DataBuffer that corresponds to this BandedSampleModel,
     * The DataBuffer's data type, number of banks, and size
     * will be consistent with this BandedSampleModel.
     * <p>
     *  创建与此BandedSampleModel对应的DataBuffer,DataBuffer的数据类型,组数和大小将与此BandedSampleModel一致。
     * 
     * 
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported types.
     */
    public DataBuffer createDataBuffer() {
        DataBuffer dataBuffer = null;

        int size = scanlineStride * height;
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
        default:
            throw new IllegalArgumentException("dataType is not one " +
                "of the supported types.");
        }

        return dataBuffer;
    }


    /**
     * Returns data for a single pixel in a primitive array of type
     * TransferType.  For a BandedSampleModel, this will be the same
     * as the data type, and samples will be returned one per array
     * element.  Generally, obj
     * should be passed in as null, so that the Object will be created
     * automatically and will be of the right primitive data type.
     * <p>
     * The following code illustrates transferring data for one pixel from
     * DataBuffer <code>db1</code>, whose storage layout is described by
     * BandedSampleModel <code>bsm1</code>, to DataBuffer <code>db2</code>,
     * whose storage layout is described by
     * BandedSampleModel <code>bsm2</code>.
     * The transfer will generally be more efficient than using
     * getPixel/setPixel.
     * <pre>
     *       BandedSampleModel bsm1, bsm2;
     *       DataBufferInt db1, db2;
     *       bsm2.setDataElements(x, y, bsm1.getDataElements(x, y, null, db1),
     *                            db2);
     * </pre>
     * Using getDataElements/setDataElements to transfer between two
     * DataBuffer/SampleModel pairs is legitimate if the SampleModels have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the TransferTypes are the same.
     * <p>
     * If obj is non-null, it should be a primitive array of type TransferType.
     * Otherwise, a ClassCastException is thrown.  An
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds, or if obj is non-null and is not large enough to hold
     * the pixel data.
     * <p>
     * 返回类型TransferType的基本数组中单个像素的数据。对于BandedSampleModel,这将与数据类型相同,每个数组元素将返回一个样本。
     * 通常,obj应该作为null传递,以便对象将被自动创建并且将是正确的基本数据类型。
     * <p>
     *  以下代码示出了将一个像素的数据从DataBuffer <code> db1 </code>传送到DataBuffer <code> db2 </code>,其存储布局由BandedSampleMode
     * l <code> bsm1 </code>描述,由BandedSampleModel <code> bsm2 </code>描述。
     * 传输通常比使用getPixel / setPixel更有效。
     * <pre>
     *  BandedSampleModel bsm1,bsm2; DataBufferInt db1,db2; bsm2.setDataElements(x,y,bsm1.getDataElements(x,
     * y,null,db1),db2);。
     * </pre>
     *  如果SampleModel具有相同数量的波段,对应的波段具有每个样本相同的位数,并且TransferTypes相同,则使用getDataElements / setDataElements在两个Dat
     * aBuffer / SampleModel对之间进行传输是合法的。
     * <p>
     *  如果obj是非空的,它应该是TransferType类型的原始数组。否则,抛出ClassCastException。
     * 如果坐标不在边界中,或者如果obj非空并且不足以容纳像素数据,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param obj       If non-null, a primitive array in which to return
     *                  the pixel data.
     * @param data      The DataBuffer containing the image data.
     * @return the data for the specified pixel.
     * @see #setDataElements(int, int, Object, DataBuffer)
     */
    public Object getDataElements(int x, int y, Object obj, DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int type = getTransferType();
        int numDataElems = getNumDataElements();
        int pixelOffset = y*scanlineStride + x;

        switch(type) {

        case DataBuffer.TYPE_BYTE:

            byte[] bdata;

            if (obj == null) {
                bdata = new byte[numDataElems];
            } else {
                bdata = (byte[])obj;
            }

            for (int i=0; i<numDataElems; i++) {
                bdata[i] = (byte)data.getElem(bankIndices[i],
                                              pixelOffset + bandOffsets[i]);
            }

            obj = (Object)bdata;
            break;

        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:

            short[] sdata;

            if (obj == null) {
                sdata = new short[numDataElems];
            } else {
                sdata = (short[])obj;
            }

            for (int i=0; i<numDataElems; i++) {
                sdata[i] = (short)data.getElem(bankIndices[i],
                                               pixelOffset + bandOffsets[i]);
            }

            obj = (Object)sdata;
            break;

        case DataBuffer.TYPE_INT:

            int[] idata;

            if (obj == null) {
                idata = new int[numDataElems];
            } else {
                idata = (int[])obj;
            }

            for (int i=0; i<numDataElems; i++) {
                idata[i] = data.getElem(bankIndices[i],
                                        pixelOffset + bandOffsets[i]);
            }

            obj = (Object)idata;
            break;

        case DataBuffer.TYPE_FLOAT:

            float[] fdata;

            if (obj == null) {
                fdata = new float[numDataElems];
            } else {
                fdata = (float[])obj;
            }

            for (int i=0; i<numDataElems; i++) {
                fdata[i] = data.getElemFloat(bankIndices[i],
                                             pixelOffset + bandOffsets[i]);
            }

            obj = (Object)fdata;
            break;

        case DataBuffer.TYPE_DOUBLE:

            double[] ddata;

            if (obj == null) {
                ddata = new double[numDataElems];
            } else {
                ddata = (double[])obj;
            }

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
     * Returns all samples for the specified pixel in an int array.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回int数组中指定像素的所有样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param iArray    If non-null, returns the samples in this array
     * @param data      The DataBuffer containing the image data
     * @return the samples for the specified pixel.
     * @see #setPixel(int, int, int[], DataBuffer)
     */
    public int[] getPixel(int x, int y, int iArray[], DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        int[] pixels;

        if (iArray != null) {
           pixels = iArray;
        } else {
           pixels = new int [numBands];
        }

        int pixelOffset = y*scanlineStride + x;
        for (int i=0; i<numBands; i++) {
            pixels[i] = data.getElem(bankIndices[i],
                                     pixelOffset + bandOffsets[i]);
        }
        return pixels;
    }

    /**
     * Returns all samples for the specified rectangle of pixels in
     * an int array, one sample per data array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     * 返回int数组中指定的像素矩形的所有样本,每个数据数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location
     * @param y         The Y coordinate of the upper left pixel location
     * @param w         The width of the pixel rectangle
     * @param h         The height of the pixel rectangle
     * @param iArray    If non-null, returns the samples in this array
     * @param data      The DataBuffer containing the image data
     * @return the samples for the pixels within the specified region.
     * @see #setPixels(int, int, int, int, int[], DataBuffer)
     */
    public int[] getPixels(int x, int y, int w, int h,
                           int iArray[], DataBuffer data) {
        int x1 = x + w;
        int y1 = y + h;

        if (x < 0 || x >= width || w > width || x1 < 0 || x1 > width ||
            y < 0 || y >= height || h > height || y1 < 0 || y1 >  height)
        {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int[] pixels;

        if (iArray != null) {
           pixels = iArray;
        } else {
           pixels = new int[w*h*numBands];
        }

        for (int k = 0; k < numBands; k++) {
            int lineOffset = y*scanlineStride + x + bandOffsets[k];
            int srcOffset = k;
            int bank = bankIndices[k];

            for (int i = 0; i < h; i++) {
                int pixelOffset = lineOffset;
                for (int j = 0; j < w; j++) {
                    pixels[srcOffset] = data.getElem(bank, pixelOffset++);
                    srcOffset += numBands;
                }
                lineOffset += scanlineStride;
            }
        }
        return pixels;
    }

    /**
     * Returns as int the sample in a specified band for the pixel
     * located at (x,y).
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  对于位于(x,y)的像素,返回指定带中的样本的int。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         The band to return
     * @param data      The DataBuffer containing the image data
     * @return the sample in the specified band for the specified pixel.
     * @see #setSample(int, int, int, int, DataBuffer)
     */
    public int getSample(int x, int y, int b, DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int sample =
            data.getElem(bankIndices[b],
                         y*scanlineStride + x + bandOffsets[b]);
        return sample;
    }

    /**
     * Returns the sample in a specified band
     * for the pixel located at (x,y) as a float.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  以位于(x,y)的像素作为浮点返回指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         The band to return
     * @param data      The DataBuffer containing the image data
     * @return a float value that represents the sample in the specified
     * band for the specified pixel.
     */
    public float getSampleFloat(int x, int y, int b, DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        float sample = data.getElemFloat(bankIndices[b],
                                    y*scanlineStride + x + bandOffsets[b]);
        return sample;
    }

    /**
     * Returns the sample in a specified band
     * for a pixel located at (x,y) as a double.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  将位于(x,y)的像素的指定频带中的样本返回为double。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         The band to return
     * @param data      The DataBuffer containing the image data
     * @return a double value that represents the sample in the specified
     * band for the specified pixel.
     */
    public double getSampleDouble(int x, int y, int b, DataBuffer data) {
        // Bounds check for 'b' will be performed automatically
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        double sample = data.getElemDouble(bankIndices[b],
                                       y*scanlineStride + x + bandOffsets[b]);
        return sample;
    }

    /**
     * Returns the samples in a specified band for the specified rectangle
     * of pixels in an int array, one sample per data array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  返回int阵列中指定像素矩形的指定带中的样本,每个数据数组元素一个样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the upper left pixel location
     * @param y         The Y coordinate of the upper left pixel location
     * @param w         The width of the pixel rectangle
     * @param h         The height of the pixel rectangle
     * @param b         The band to return
     * @param iArray    If non-null, returns the samples in this array
     * @param data      The DataBuffer containing the image data
     * @return the samples in the specified band for the pixels within
     * the specified region.
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

        int lineOffset = y*scanlineStride + x + bandOffsets[b];
        int srcOffset = 0;
        int bank = bankIndices[b];

        for (int i = 0; i < h; i++) {
           int sampleOffset = lineOffset;
           for (int j = 0; j < w; j++) {
               samples[srcOffset++] = data.getElem(bank, sampleOffset++);
           }
           lineOffset += scanlineStride;
        }
        return samples;
    }

    /**
     * Sets the data for a single pixel in the specified DataBuffer from a
     * primitive array of type TransferType.  For a BandedSampleModel,
     * this will be the same as the data type, and samples are transferred
     * one per array element.
     * <p>
     * The following code illustrates transferring data for one pixel from
     * DataBuffer <code>db1</code>, whose storage layout is described by
     * BandedSampleModel <code>bsm1</code>, to DataBuffer <code>db2</code>,
     * whose storage layout is described by
     * BandedSampleModel <code>bsm2</code>.
     * The transfer will generally be more efficient than using
     * getPixel/setPixel.
     * <pre>
     *       BandedSampleModel bsm1, bsm2;
     *       DataBufferInt db1, db2;
     *       bsm2.setDataElements(x, y, bsm1.getDataElements(x, y, null, db1),
     *                            db2);
     * </pre>
     * Using getDataElements/setDataElements to transfer between two
     * DataBuffer/SampleModel pairs is legitimate if the SampleModels have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the TransferTypes are the same.
     * <p>
     * obj must be a primitive array of type TransferType.  Otherwise,
     * a ClassCastException is thrown.  An
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds, or if obj is not large enough to hold the pixel data.
     * <p>
     *  从TransferType类型的基本数组设置指定DataBuffer中单个像素的数据。对于BandedSampleModel,这将与数据类型相同,并且每个数组元素传输一个样本。
     * <p>
     *  以下代码示出了将一个像素的数据从DataBuffer <code> db1 </code>传送到DataBuffer <code> db2 </code>,其存储布局由BandedSampleMode
     * l <code> bsm1 </code>描述,由BandedSampleModel <code> bsm2 </code>描述。
     * 传输通常比使用getPixel / setPixel更有效。
     * <pre>
     * BandedSampleModel bsm1,bsm2; DataBufferInt db1,db2; bsm2.setDataElements(x,y,bsm1.getDataElements(x,y
     * ,null,db1),db2);。
     * </pre>
     *  如果SampleModel具有相同数量的波段,对应的波段具有每个样本相同的位数,并且TransferTypes相同,则使用getDataElements / setDataElements在两个Dat
     * aBuffer / SampleModel对之间进行传输是合法的。
     * <p>
     *  obj必须是TransferType类型的原始数组。否则,抛出ClassCastException。
     * 如果坐标不在边界中,或者如果obj不足够大以容纳像素数据,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param obj       If non-null, returns the primitive array in this
     *                  object
     * @param data      The DataBuffer containing the image data
     * @see #getDataElements(int, int, Object, DataBuffer)
     */
    public void setDataElements(int x, int y, Object obj, DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int type = getTransferType();
        int numDataElems = getNumDataElements();
        int pixelOffset = y*scanlineStride + x;

        switch(type) {

        case DataBuffer.TYPE_BYTE:

            byte[] barray = (byte[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElem(bankIndices[i], pixelOffset + bandOffsets[i],
                             barray[i] & 0xff);
            }
            break;

        case DataBuffer.TYPE_USHORT:
        case DataBuffer.TYPE_SHORT:

            short[] sarray = (short[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElem(bankIndices[i], pixelOffset + bandOffsets[i],
                             sarray[i] & 0xffff);
            }
            break;

        case DataBuffer.TYPE_INT:

            int[] iarray = (int[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElem(bankIndices[i], pixelOffset + bandOffsets[i],
                             iarray[i]);
            }
            break;

        case DataBuffer.TYPE_FLOAT:

            float[] farray = (float[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElemFloat(bankIndices[i], pixelOffset + bandOffsets[i],
                                  farray[i]);
            }
            break;

        case DataBuffer.TYPE_DOUBLE:

            double[] darray = (double[])obj;

            for (int i=0; i<numDataElems; i++) {
                data.setElemDouble(bankIndices[i], pixelOffset + bandOffsets[i],
                                   darray[i]);
            }
            break;

        }
    }

    /**
     * Sets a pixel in the DataBuffer using an int array of samples for input.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  设置DataBuffer中的一个像素,使用int数组样本进行输入。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
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
       int pixelOffset = y*scanlineStride + x;
       for (int i=0; i<numBands; i++) {
           data.setElem(bankIndices[i], pixelOffset + bandOffsets[i],
                        iArray[i]);
       }
    }

    /**
     * Sets all samples for a rectangle of pixels from an int array containing
     * one sample per array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  为包含每个数组元素一个样本的int数组设置像素矩形的所有样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
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

        for (int k = 0; k < numBands; k++) {
            int lineOffset = y*scanlineStride + x + bandOffsets[k];
            int srcOffset = k;
            int bank = bankIndices[k];

            for (int i = 0; i < h; i++) {
                int pixelOffset = lineOffset;
                for (int j = 0; j < w; j++) {
                    data.setElem(bank, pixelOffset++, iArray[srcOffset]);
                    srcOffset += numBands;
                }
                lineOffset += scanlineStride;
           }
        }
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using an int for input.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  使用int作为输入,为DataBuffer中位于(x,y)的像素设置指定频带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * 
     * @param x         The X coordinate of the pixel location
     * @param y         The Y coordinate of the pixel location
     * @param b         The band to set
     * @param s         The input sample as an int
     * @param data      The DataBuffer containing the image data
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
                     y*scanlineStride + x + bandOffsets[b], s);
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using a float for input.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  使用float作为输入,为DataBuffer中位于(x,y)的像素设置指定频带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
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
                          y*scanlineStride + x + bandOffsets[b], s);
    }

    /**
     * Sets a sample in the specified band for the pixel located at (x,y)
     * in the DataBuffer using a double for input.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     * 在指定的频带中为位于(x,y)的像素在DataBuffer中使用double输入设置样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
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
                          y*scanlineStride + x + bandOffsets[b], s);
    }

    /**
     * Sets the samples in the specified band for the specified rectangle
     * of pixels from an int array containing one sample per data array element.
     * ArrayIndexOutOfBoundsException may be thrown if the coordinates are
     * not in bounds.
     * <p>
     *  为来自包含每个数据数组元素一个样本的int数组的指定像素矩形的指定带中的样本。如果坐标不在边界中,则可能抛出ArrayIndexOutOfBoundsException。
     * 
     * @param x         The X coordinate of the upper left pixel location
     * @param y         The Y coordinate of the upper left pixel location
     * @param w         The width of the pixel rectangle
     * @param h         The height of the pixel rectangle
     * @param b         The band to set
     * @param iArray    The input sample array
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
        int lineOffset = y*scanlineStride + x + bandOffsets[b];
        int srcOffset = 0;
        int bank = bankIndices[b];

        for (int i = 0; i < h; i++) {
           int sampleOffset = lineOffset;
           for (int j = 0; j < w; j++) {
              data.setElem(bank, sampleOffset++, iArray[srcOffset++]);
           }
           lineOffset += scanlineStride;
        }
    }

    private static int[] createOffsetArray(int numBands) {
        int[] bandOffsets = new int[numBands];
        for (int i=0; i < numBands; i++) {
            bandOffsets[i] = 0;
        }
        return bandOffsets;
    }

    private static int[] createIndicesArray(int numBands) {
        int[] bankIndices = new int[numBands];
        for (int i=0; i < numBands; i++) {
            bankIndices[i] = i;
        }
        return bankIndices;
    }

    // Differentiate hash code from other ComponentSampleModel subclasses
    public int hashCode() {
        return super.hashCode() ^ 0x2;
    }
}
