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
 * The <code>MultiPixelPackedSampleModel</code> class represents
 * one-banded images and can pack multiple one-sample
 * pixels into one data element.  Pixels are not allowed to span data elements.
 * The data type can be DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT,
 * or DataBuffer.TYPE_INT.  Each pixel must be a power of 2 number of bits
 * and a power of 2 number of pixels must fit exactly in one data element.
 * Pixel bit stride is equal to the number of bits per pixel.  Scanline
 * stride is in data elements and the last several data elements might be
 * padded with unused pixels.  Data bit offset is the offset in bits from
 * the beginning of the {@link DataBuffer} to the first pixel and must be
 * a multiple of pixel bit stride.
 * <p>
 * The following code illustrates extracting the bits for pixel
 * <code>x,&nbsp;y</code> from <code>DataBuffer</code> <code>data</code>
 * and storing the pixel data in data elements of type
 * <code>dataType</code>:
 * <pre>{@code
 *      int dataElementSize = DataBuffer.getDataTypeSize(dataType);
 *      int bitnum = dataBitOffset + x*pixelBitStride;
 *      int element = data.getElem(y*scanlineStride + bitnum/dataElementSize);
 *      int shift = dataElementSize - (bitnum & (dataElementSize-1))
 *                  - pixelBitStride;
 *      int pixel = (element >> shift) & ((1 << pixelBitStride) - 1);
 * }</pre>
 * <p>
 *  <code> MultiPixelPackedSampleModel </code>类表示单带图像,可以将多个单样本像素打包到一个数据元素中。不允许像素跨越数据元素。
 * 数据类型可以是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT或DataBuffer.TYPE_INT。
 * 每个像素必须是2的位数的幂,并且2个像素的数量的幂必须恰好适合于一个数据元素。像素位步长等于每像素的位数。扫描线跨距在数据元素中,并且最后几个数据元素可以用未使用的像素填充。
 * 数据位偏移是从{@link DataBuffer}开始到第一个像素的位的偏移量,必须是像素位跨距的倍数。
 * <p>
 * 下面的代码示出从<code> DataBuffer </code> <code> data </code>中提取像素<code> x,</code>的像素位并将像素数据存储在<code> > dataT
 * ype </code>：<pre> {@ code int dataElementSize = DataBuffer.getDataTypeSize(dataType); int bitnum = dataBitOffset + x * pixelBitStride; int element = data.getElem(y * scanlineStride + bitnum / dataElementSize); int shift = dataElementSize  - (bitnum&(dataElementSize-1)) -  pixelBitStride; int pixel =(element >> shift)&((1 << pixelBitStride)-1); } </pre>
 * 。
 * 
 */

public class MultiPixelPackedSampleModel extends SampleModel
{
    /** The number of bits from one pixel to the next. */
    int pixelBitStride;

    /** Bitmask that extracts the rightmost pixel of a data element. */
    int bitMask;

    /**
      * The number of pixels that fit in a data element.  Also used
      * as the number of bits per pixel.
      * <p>
      *  适合数据元素的像素数。也用作每像素的位数。
      * 
      */
    int pixelsPerDataElement;

    /** The size of a data element in bits. */
    int dataElementSize;

    /** The bit offset into the data array where the first pixel begins.
    /* <p>
     */
    int dataBitOffset;

    /** ScanlineStride of the data buffer described in data array elements. */
    int scanlineStride;

    /**
     * Constructs a <code>MultiPixelPackedSampleModel</code> with the
     * specified data type, width, height and number of bits per pixel.
     * <p>
     *  构造具有指定数据类型,宽度,高度和每像素位数的<code> MultiPixelPackedSampleModel </code>。
     * 
     * 
     * @param dataType  the data type for storing samples
     * @param w         the width, in pixels, of the region of
     *                  image data described
     * @param h         the height, in pixels, of the region of
     *                  image data described
     * @param numberOfBits the number of bits per pixel
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         either <code>DataBuffer.TYPE_BYTE</code>,
     *         <code>DataBuffer.TYPE_USHORT</code>, or
     *         <code>DataBuffer.TYPE_INT</code>
     */
    public MultiPixelPackedSampleModel(int dataType,
                                       int w,
                                       int h,
                                       int numberOfBits) {
        this(dataType,w,h,
             numberOfBits,
            (w*numberOfBits+DataBuffer.getDataTypeSize(dataType)-1)/
                DataBuffer.getDataTypeSize(dataType),
             0);
        if (dataType != DataBuffer.TYPE_BYTE &&
            dataType != DataBuffer.TYPE_USHORT &&
            dataType != DataBuffer.TYPE_INT) {
            throw new IllegalArgumentException("Unsupported data type "+
                                               dataType);
        }
    }

    /**
     * Constructs a <code>MultiPixelPackedSampleModel</code> with
     * specified data type, width, height, number of bits per pixel,
     * scanline stride and data bit offset.
     * <p>
     *  构造具有指定数据类型,宽度,高度,每像素位数,扫描线跨距和数据位偏移量的<code> MultiPixelPackedSampleModel </code>。
     * 
     * 
     * @param dataType  the data type for storing samples
     * @param w         the width, in pixels, of the region of
     *                  image data described
     * @param h         the height, in pixels, of the region of
     *                  image data described
     * @param numberOfBits the number of bits per pixel
     * @param scanlineStride the line stride of the image data
     * @param dataBitOffset the data bit offset for the region of image
     *                  data described
     * @exception RasterFormatException if the number of bits per pixel
     *                  is not a power of 2 or if a power of 2 number of
     *                  pixels do not fit in one data element.
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         either <code>DataBuffer.TYPE_BYTE</code>,
     *         <code>DataBuffer.TYPE_USHORT</code>, or
     *         <code>DataBuffer.TYPE_INT</code>
     */
    public MultiPixelPackedSampleModel(int dataType, int w, int h,
                                       int numberOfBits,
                                       int scanlineStride,
                                       int dataBitOffset) {
        super(dataType, w, h, 1);
        if (dataType != DataBuffer.TYPE_BYTE &&
            dataType != DataBuffer.TYPE_USHORT &&
            dataType != DataBuffer.TYPE_INT) {
            throw new IllegalArgumentException("Unsupported data type "+
                                               dataType);
        }
        this.dataType = dataType;
        this.pixelBitStride = numberOfBits;
        this.scanlineStride = scanlineStride;
        this.dataBitOffset = dataBitOffset;
        this.dataElementSize = DataBuffer.getDataTypeSize(dataType);
        this.pixelsPerDataElement = dataElementSize/numberOfBits;
        if (pixelsPerDataElement*numberOfBits != dataElementSize) {
           throw new RasterFormatException("MultiPixelPackedSampleModel " +
                                             "does not allow pixels to " +
                                             "span data element boundaries");
        }
        this.bitMask = (1 << numberOfBits) - 1;
    }


    /**
     * Creates a new <code>MultiPixelPackedSampleModel</code> with the
     * specified width and height.  The new
     * <code>MultiPixelPackedSampleModel</code> has the
     * same storage data type and number of bits per pixel as this
     * <code>MultiPixelPackedSampleModel</code>.
     * <p>
     *  使用指定的宽度和高度创建新的<code> MultiPixelPackedSampleModel </code>。
     * 新的<code> MultiPixelPackedSampleModel </code>具有与此<code> MultiPixelPackedSampleModel </code>相同的存储数据类型和每
     * 像素的位数。
     *  使用指定的宽度和高度创建新的<code> MultiPixelPackedSampleModel </code>。
     * 
     * 
     * @param w the specified width
     * @param h the specified height
     * @return a {@link SampleModel} with the specified width and height
     * and with the same storage data type and number of bits per pixel
     * as this <code>MultiPixelPackedSampleModel</code>.
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     */
    public SampleModel createCompatibleSampleModel(int w, int h) {
      SampleModel sampleModel =
            new MultiPixelPackedSampleModel(dataType, w, h, pixelBitStride);
      return sampleModel;
    }

    /**
     * Creates a <code>DataBuffer</code> that corresponds to this
     * <code>MultiPixelPackedSampleModel</code>.  The
     * <code>DataBuffer</code> object's data type and size
     * is consistent with this <code>MultiPixelPackedSampleModel</code>.
     * The <code>DataBuffer</code> has a single bank.
     * <p>
     *  创建与此<code> MultiPixelPackedSampleModel </code>对应的<code> DataBuffer </code>。
     *  <code> DataBuffer </code>对象的数据类型和大小与此<code> MultiPixelPackedSampleModel </code>一致。
     *  <code> DataBuffer </code>有一个银行。
     * 
     * 
     * @return a <code>DataBuffer</code> with the same data type and
     * size as this <code>MultiPixelPackedSampleModel</code>.
     */
    public DataBuffer createDataBuffer() {
        DataBuffer dataBuffer = null;

        int size = (int)scanlineStride*height;
        switch (dataType) {
        case DataBuffer.TYPE_BYTE:
            dataBuffer = new DataBufferByte(size+(dataBitOffset+7)/8);
            break;
        case DataBuffer.TYPE_USHORT:
            dataBuffer = new DataBufferUShort(size+(dataBitOffset+15)/16);
            break;
        case DataBuffer.TYPE_INT:
            dataBuffer = new DataBufferInt(size+(dataBitOffset+31)/32);
            break;
        }
        return dataBuffer;
    }

    /**
     * Returns the number of data elements needed to transfer one pixel
     * via the {@link #getDataElements} and {@link #setDataElements}
     * methods.  For a <code>MultiPixelPackedSampleModel</code>, this is
     * one.
     * <p>
     * 返回通过{@link #getDataElements}和{@link #setDataElements}方法传输一个像素所需的数据元素数。
     * 对于<code> MultiPixelPackedSampleModel </code>,这是一个。
     * 
     * 
     * @return the number of data elements.
     */
    public int getNumDataElements() {
        return 1;
    }

    /**
     * Returns the number of bits per sample for all bands.
     * <p>
     *  返回所有波段的每个样本的比特数。
     * 
     * 
     * @return the number of bits per sample.
     */
    public int[] getSampleSize() {
        int sampleSize[] = {pixelBitStride};
        return sampleSize;
    }

    /**
     * Returns the number of bits per sample for the specified band.
     * <p>
     *  返回指定波段的每个样本的比特数。
     * 
     * 
     * @param band the specified band
     * @return the number of bits per sample for the specified band.
     */
    public int getSampleSize(int band) {
        return pixelBitStride;
    }

    /**
     * Returns the offset of pixel (x,&nbsp;y) in data array elements.
     * <p>
     *  返回数据数组元素中像素(x,&nbsp; y)的偏移量。
     * 
     * 
     * @param x the X coordinate of the specified pixel
     * @param y the Y coordinate of the specified pixel
     * @return the offset of the specified pixel.
     */
    public int getOffset(int x, int y) {
        int offset = y * scanlineStride;
        offset +=  (x*pixelBitStride+dataBitOffset)/dataElementSize;
        return offset;
    }

    /**
     *  Returns the offset, in bits, into the data element in which it is
     *  stored for the <code>x</code>th pixel of a scanline.
     *  This offset is the same for all scanlines.
     * <p>
     *  返回存储为扫描线的<code> x </code>像素的数据元素的偏移量(以位为单位)。该偏移对于所有扫描线是相同的。
     * 
     * 
     *  @param x the specified pixel
     *  @return the bit offset of the specified pixel.
     */
    public int getBitOffset(int x){
       return  (x*pixelBitStride+dataBitOffset)%dataElementSize;
    }

    /**
     * Returns the scanline stride.
     * <p>
     *  返回扫描线跨度。
     * 
     * 
     * @return the scanline stride of this
     * <code>MultiPixelPackedSampleModel</code>.
     */
    public int getScanlineStride() {
        return scanlineStride;
    }

    /**
     * Returns the pixel bit stride in bits.  This value is the same as
     * the number of bits per pixel.
     * <p>
     *  返回以位为单位的像素位跨距。该值与每个像素的位数相同。
     * 
     * 
     * @return the <code>pixelBitStride</code> of this
     * <code>MultiPixelPackedSampleModel</code>.
     */
    public int getPixelBitStride() {
        return pixelBitStride;
    }

    /**
     * Returns the data bit offset in bits.
     * <p>
     *  返回以位为单位的数据位偏移量。
     * 
     * 
     * @return the <code>dataBitOffset</code> of this
     * <code>MultiPixelPackedSampleModel</code>.
     */
    public int getDataBitOffset() {
        return dataBitOffset;
    }

    /**
     *  Returns the TransferType used to transfer pixels by way of the
     *  <code>getDataElements</code> and <code>setDataElements</code>
     *  methods. The TransferType might or might not be the same as the
     *  storage DataType.  The TransferType is one of
     *  DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT,
     *  or DataBuffer.TYPE_INT.
     * <p>
     *  通过<code> getDataElements </code>和<code> setDataElements </code>方法返回用于传输像素的TransferType。
     *  TransferType可能与存储DataType相同,也可能不相同。
     *  TransferType是DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHORT或DataBuffer.TYPE_INT中的一个。
     * 
     * 
     *  @return the transfertype.
     */
    public int getTransferType() {
        if (pixelBitStride > 16)
            return DataBuffer.TYPE_INT;
        else if (pixelBitStride > 8)
            return DataBuffer.TYPE_USHORT;
        else
            return DataBuffer.TYPE_BYTE;
    }

    /**
     * Creates a new <code>MultiPixelPackedSampleModel</code> with a
     * subset of the bands of this
     * <code>MultiPixelPackedSampleModel</code>.  Since a
     * <code>MultiPixelPackedSampleModel</code> only has one band, the
     * bands argument must have a length of one and indicate the zeroth
     * band.
     * <p>
     *  使用此<code> MultiPixelPackedSampleModel </code>的波段子集创建新的<code> MultiPixelPackedSampleModel </code>。
     * 由于<code> MultiPixelPackedSampleModel </code>只有一个band,bands参数的长度必须为1,并指示第零个波段。
     * 
     * 
     * @param bands the specified bands
     * @return a new <code>SampleModel</code> with a subset of bands of
     * this <code>MultiPixelPackedSampleModel</code>.
     * @exception RasterFormatException if the number of bands requested
     * is not one.
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     */
    public SampleModel createSubsetSampleModel(int bands[]) {
        if (bands != null) {
           if (bands.length != 1)
            throw new RasterFormatException("MultiPixelPackedSampleModel has "
                                            + "only one band.");
        }
        SampleModel sm = createCompatibleSampleModel(width, height);
        return sm;
    }

    /**
     * Returns as <code>int</code> the sample in a specified band for the
     * pixel located at (x,&nbsp;y).  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if the
     * coordinates are not in bounds.
     * <p>
     *  作为<code> int </code>返回位于(x,&nbsp; y)的像素的指定带中的样本。
     * 如果坐标不在边界中,则抛出<code> ArrayIndexOutOfBoundsException </code>。
     * 
     * 
     * @param x         the X coordinate of the specified pixel
     * @param y         the Y coordinate of the specified pixel
     * @param b         the band to return, which is assumed to be 0
     * @param data      the <code>DataBuffer</code> containing the image
     *                  data
     * @return the specified band containing the sample of the specified
     * pixel.
     * @exception ArrayIndexOutOfBoundsException if the specified
     *          coordinates are not in bounds.
     * @see #setSample(int, int, int, int, DataBuffer)
     */
    public int getSample(int x, int y, int b, DataBuffer data) {
        // 'b' must be 0
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height) ||
            (b != 0)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int bitnum = dataBitOffset + x*pixelBitStride;
        int element = data.getElem(y*scanlineStride + bitnum/dataElementSize);
        int shift = dataElementSize - (bitnum & (dataElementSize-1))
                    - pixelBitStride;
        return (element >> shift) & bitMask;
    }

    /**
     * Sets a sample in the specified band for the pixel located at
     * (x,&nbsp;y) in the <code>DataBuffer</code> using an
     * <code>int</code> for input.
     * An <code>ArrayIndexOutOfBoundsException</code> is thrown if the
     * coordinates are not in bounds.
     * <p>
     * 使用<code> int </code>为输入在<code> DataBuffer </code>中位于(x,&nbsp; y)的像素设置指定频带中的样本。
     * 如果坐标不在边界中,则抛出<code> ArrayIndexOutOfBoundsException </code>。
     * 
     * 
     * @param x the X coordinate of the specified pixel
     * @param y the Y coordinate of the specified pixel
     * @param b the band to return, which is assumed to be 0
     * @param s the input sample as an <code>int</code>
     * @param data the <code>DataBuffer</code> where image data is stored
     * @exception ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds.
     * @see #getSample(int, int, int, DataBuffer)
     */
    public void setSample(int x, int y, int b, int s,
                          DataBuffer data) {
        // 'b' must be 0
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height) ||
            (b != 0)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int bitnum = dataBitOffset + x * pixelBitStride;
        int index = y * scanlineStride + (bitnum / dataElementSize);
        int shift = dataElementSize - (bitnum & (dataElementSize-1))
                    - pixelBitStride;
        int element = data.getElem(index);
        element &= ~(bitMask << shift);
        element |= (s & bitMask) << shift;
        data.setElem(index,element);
    }

    /**
     * Returns data for a single pixel in a primitive array of type
     * TransferType.  For a <code>MultiPixelPackedSampleModel</code>,
     * the array has one element, and the type is the smallest of
     * DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT, or DataBuffer.TYPE_INT
     * that can hold a single pixel.  Generally, <code>obj</code>
     * should be passed in as <code>null</code>, so that the
     * <code>Object</code> is created automatically and is the
     * correct primitive data type.
     * <p>
     * The following code illustrates transferring data for one pixel from
     * <code>DataBuffer</code> <code>db1</code>, whose storage layout is
     * described by <code>MultiPixelPackedSampleModel</code>
     * <code>mppsm1</code>, to <code>DataBuffer</code> <code>db2</code>,
     * whose storage layout is described by
     * <code>MultiPixelPackedSampleModel</code> <code>mppsm2</code>.
     * The transfer is generally more efficient than using
     * <code>getPixel</code> or <code>setPixel</code>.
     * <pre>
     *       MultiPixelPackedSampleModel mppsm1, mppsm2;
     *       DataBufferInt db1, db2;
     *       mppsm2.setDataElements(x, y, mppsm1.getDataElements(x, y, null,
     *                              db1), db2);
     * </pre>
     * Using <code>getDataElements</code> or <code>setDataElements</code>
     * to transfer between two <code>DataBuffer/SampleModel</code> pairs
     * is legitimate if the <code>SampleModels</code> have the same number
     * of bands, corresponding bands have the same number of
     * bits per sample, and the TransferTypes are the same.
     * <p>
     * If <code>obj</code> is not <code>null</code>, it should be a
     * primitive array of type TransferType.  Otherwise, a
     * <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if the
     * coordinates are not in bounds, or if <code>obj</code> is not
     * <code>null</code> and is not large enough to hold the pixel data.
     * <p>
     *  返回类型TransferType的基本数组中单个像素的数据。
     * 对于<code> MultiPixelPackedSampleModel </code>,该数组有一个元素,类型是最小的DataBuffer.TYPE_BYTE,DataBuffer.TYPE_USHO
     * RT或DataBuffer.TYPE_INT,可以容纳单个像素。
     *  返回类型TransferType的基本数组中单个像素的数据。
     * 通常,<code> obj </code>应该作为<code> null </code>传递,以便自动创建<code> Object </code>,并且是正确的基本数据类型。
     * <p>
     *  以下代码说明从<code> DataBuffer </code> <code> db1 </code>传输一个像素的数据,其存储布局由<code> MultiPixelPackedSampleMode
     * l </code> <code> mppsm1 </code>到<code> DataBuffer </code> <code> db2 </code>,其存储布局由<code> MultiPixelP
     * ackedSampleModel </code> <code> mppsm2 </code>描述。
     * 传输通常比使用<code> getPixel </code>或<code> setPixel </code>更有效。
     * <pre>
     *  MultiPixelPackedSampleModel mppsm1,mppsm2; DataBufferInt db1,db2; mppsm2.setDataElements(x,y,mppsm1.
     * getDataElements(x,y,null,db1),db2);。
     * </pre>
     * 如果<code> SampleModels </code>具有相同的数目,则使用<code> getDataElements </code>或<code> setDataElements </code>
     * 在两个<code> DataBuffer / SampleModel </code>每个样本具有相同的比特数,并且TransferTypes是相同的。
     * <p>
     *  如果<code> obj </code>不是<code> null </code>,它应该是TransferType类型的原始数组。
     * 否则,抛出<code> ClassCastException </code>。
     * 如果坐标不在边界中,或者<code> obj </code>不是<code> null </code>且不足以容纳像素数据,则会抛出<code> ArrayIndexOutOfBoundsExcepti
     * on </code>。
     * 否则,抛出<code> ClassCastException </code>。
     * 
     * 
     * @param x the X coordinate of the specified pixel
     * @param y the Y coordinate of the specified pixel
     * @param obj a primitive array in which to return the pixel data or
     *          <code>null</code>.
     * @param data the <code>DataBuffer</code> containing the image data.
     * @return an <code>Object</code> containing data for the specified
     *  pixel.
     * @exception ClassCastException if <code>obj</code> is not a
     *  primitive array of type TransferType or is not <code>null</code>
     * @exception ArrayIndexOutOfBoundsException if the coordinates are
     * not in bounds, or if <code>obj</code> is not <code>null</code> or
     * not large enough to hold the pixel data
     * @see #setDataElements(int, int, Object, DataBuffer)
     */
    public Object getDataElements(int x, int y, Object obj, DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        int type = getTransferType();
        int bitnum = dataBitOffset + x*pixelBitStride;
        int shift = dataElementSize - (bitnum & (dataElementSize-1))
                    - pixelBitStride;
        int element = 0;

        switch(type) {

        case DataBuffer.TYPE_BYTE:

            byte[] bdata;

            if (obj == null)
                bdata = new byte[1];
            else
                bdata = (byte[])obj;

            element = data.getElem(y*scanlineStride +
                                    bitnum/dataElementSize);
            bdata[0] = (byte)((element >> shift) & bitMask);

            obj = (Object)bdata;
            break;

        case DataBuffer.TYPE_USHORT:

            short[] sdata;

            if (obj == null)
                sdata = new short[1];
            else
                sdata = (short[])obj;

            element = data.getElem(y*scanlineStride +
                                   bitnum/dataElementSize);
            sdata[0] = (short)((element >> shift) & bitMask);

            obj = (Object)sdata;
            break;

        case DataBuffer.TYPE_INT:

            int[] idata;

            if (obj == null)
                idata = new int[1];
            else
                idata = (int[])obj;

            element = data.getElem(y*scanlineStride +
                                   bitnum/dataElementSize);
            idata[0] = (element >> shift) & bitMask;

            obj = (Object)idata;
            break;
        }

        return obj;
    }

    /**
     * Returns the specified single band pixel in the first element
     * of an <code>int</code> array.
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if the
     * coordinates are not in bounds.
     * <p>
     *  返回<code> int </code>数组的第一个元素中指定的单频带像素。如果坐标不在边界中,则抛出<code> ArrayIndexOutOfBoundsException </code>。
     * 
     * 
     * @param x the X coordinate of the specified pixel
     * @param y the Y coordinate of the specified pixel
     * @param iArray the array containing the pixel to be returned or
     *  <code>null</code>
     * @param data the <code>DataBuffer</code> where image data is stored
     * @return an array containing the specified pixel.
     * @exception ArrayIndexOutOfBoundsException if the coordinates
     *  are not in bounds
     * @see #setPixel(int, int, int[], DataBuffer)
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
        int bitnum = dataBitOffset + x*pixelBitStride;
        int element = data.getElem(y*scanlineStride + bitnum/dataElementSize);
        int shift = dataElementSize - (bitnum & (dataElementSize-1))
                    - pixelBitStride;
        pixels[0] = (element >> shift) & bitMask;
        return pixels;
    }

    /**
     * Sets the data for a single pixel in the specified
     * <code>DataBuffer</code> from a primitive array of type
     * TransferType.  For a <code>MultiPixelPackedSampleModel</code>,
     * only the first element of the array holds valid data,
     * and the type must be the smallest of
     * DataBuffer.TYPE_BYTE, DataBuffer.TYPE_USHORT, or DataBuffer.TYPE_INT
     * that can hold a single pixel.
     * <p>
     * The following code illustrates transferring data for one pixel from
     * <code>DataBuffer</code> <code>db1</code>, whose storage layout is
     * described by <code>MultiPixelPackedSampleModel</code>
     * <code>mppsm1</code>, to <code>DataBuffer</code> <code>db2</code>,
     * whose storage layout is described by
     * <code>MultiPixelPackedSampleModel</code> <code>mppsm2</code>.
     * The transfer is generally more efficient than using
     * <code>getPixel</code> or <code>setPixel</code>.
     * <pre>
     *       MultiPixelPackedSampleModel mppsm1, mppsm2;
     *       DataBufferInt db1, db2;
     *       mppsm2.setDataElements(x, y, mppsm1.getDataElements(x, y, null,
     *                              db1), db2);
     * </pre>
     * Using <code>getDataElements</code> or <code>setDataElements</code> to
     * transfer between two <code>DataBuffer/SampleModel</code> pairs is
     * legitimate if the <code>SampleModel</code> objects have
     * the same number of bands, corresponding bands have the same number of
     * bits per sample, and the TransferTypes are the same.
     * <p>
     * <code>obj</code> must be a primitive array of type TransferType.
     * Otherwise, a <code>ClassCastException</code> is thrown.  An
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if the
     * coordinates are not in bounds, or if <code>obj</code> is not large
     * enough to hold the pixel data.
     * <p>
     *  在TransferType类型的基本数组中设置指定<code> DataBuffer </code>中单个像素的数据。
     * 对于<code> MultiPixelPackedSampleModel </code>,只有数组的第一个元素保存有效数据,并且类型必须是可以容纳单个像素的DataBuffer.TYPE_BYTE,Da
     * taBuffer.TYPE_USHORT或DataBuffer.TYPE_INT中最小的。
     *  在TransferType类型的基本数组中设置指定<code> DataBuffer </code>中单个像素的数据。
     * <p>
     * 以下代码说明从<code> DataBuffer </code> <code> db1 </code>传输一个像素的数据,其存储布局由<code> MultiPixelPackedSampleModel
     *  </code> <code> mppsm1 </code>到<code> DataBuffer </code> <code> db2 </code>,其存储布局由<code> MultiPixelPa
     * ckedSampleModel </code> <code> mppsm2 </code>描述。
     * 传输通常比使用<code> getPixel </code>或<code> setPixel </code>更有效。
     * <pre>
     *  MultiPixelPackedSampleModel mppsm1,mppsm2; DataBufferInt db1,db2; mppsm2.setDataElements(x,y,mppsm1.
     * getDataElements(x,y,null,db1),db2);。
     * 
     * @param x the X coordinate of the pixel location
     * @param y the Y coordinate of the pixel location
     * @param obj a primitive array containing pixel data
     * @param data the <code>DataBuffer</code> containing the image data
     * @see #getDataElements(int, int, Object, DataBuffer)
     */
    public void setDataElements(int x, int y, Object obj, DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }

        int type = getTransferType();
        int bitnum = dataBitOffset + x * pixelBitStride;
        int index = y * scanlineStride + (bitnum / dataElementSize);
        int shift = dataElementSize - (bitnum & (dataElementSize-1))
                    - pixelBitStride;
        int element = data.getElem(index);
        element &= ~(bitMask << shift);

        switch(type) {

        case DataBuffer.TYPE_BYTE:

            byte[] barray = (byte[])obj;
            element |= ( ((int)(barray[0])&0xff) & bitMask) << shift;
            data.setElem(index, element);
            break;

        case DataBuffer.TYPE_USHORT:

            short[] sarray = (short[])obj;
            element |= ( ((int)(sarray[0])&0xffff) & bitMask) << shift;
            data.setElem(index, element);
            break;

        case DataBuffer.TYPE_INT:

            int[] iarray = (int[])obj;
            element |= (iarray[0] & bitMask) << shift;
            data.setElem(index, element);
            break;
        }
    }

    /**
     * Sets a pixel in the <code>DataBuffer</code> using an
     * <code>int</code> array for input.
     * <code>ArrayIndexOutOfBoundsException</code> is thrown if
     * the coordinates are not in bounds.
     * <p>
     * </pre>
     *  如果<code> SampleModel </code>对象具有相同的数字,则使用<code> getDataElements </code>或<code> setDataElements </code>
     * 在两个<code> DataBuffer / SampleModel </code>的带,相应的带具有相同数量的每样本的比特,并且TransferTypes是相同的。
     * <p>
     *  <code> obj </code>必须是TransferType类型的原始数组。否则,抛出<code> ClassCastException </code>。
     * 如果坐标不在边界中,或者如果<code> obj </code>不足够大以容纳像素数据,则会抛出<code> ArrayIndexOutOfBoundsException </code>。
     * 
     * @param x the X coordinate of the pixel location
     * @param y the Y coordinate of the pixel location
     * @param iArray the input pixel in an <code>int</code> array
     * @param data the <code>DataBuffer</code> containing the image data
     * @see #getPixel(int, int, int[], DataBuffer)
     */
    public void setPixel(int x, int y, int[] iArray, DataBuffer data) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            throw new ArrayIndexOutOfBoundsException
                ("Coordinate out of bounds!");
        }
        int bitnum = dataBitOffset + x * pixelBitStride;
        int index = y * scanlineStride + (bitnum / dataElementSize);
        int shift = dataElementSize - (bitnum & (dataElementSize-1))
                    - pixelBitStride;
        int element = data.getElem(index);
        element &= ~(bitMask << shift);
        element |= (iArray[0] & bitMask) << shift;
        data.setElem(index,element);
    }

    public boolean equals(Object o) {
        if ((o == null) || !(o instanceof MultiPixelPackedSampleModel)) {
            return false;
        }

        MultiPixelPackedSampleModel that = (MultiPixelPackedSampleModel)o;
        return this.width == that.width &&
            this.height == that.height &&
            this.numBands == that.numBands &&
            this.dataType == that.dataType &&
            this.pixelBitStride == that.pixelBitStride &&
            this.bitMask == that.bitMask &&
            this.pixelsPerDataElement == that.pixelsPerDataElement &&
            this.dataElementSize == that.dataElementSize &&
            this.dataBitOffset == that.dataBitOffset &&
            this.scanlineStride == that.scanlineStride;
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
        hash ^= pixelBitStride;
        hash <<= 8;
        hash ^= bitMask;
        hash <<= 8;
        hash ^= pixelsPerDataElement;
        hash <<= 8;
        hash ^= dataElementSize;
        hash <<= 8;
        hash ^= dataBitOffset;
        hash <<= 8;
        hash ^= scanlineStride;
        return hash;
    }
}
