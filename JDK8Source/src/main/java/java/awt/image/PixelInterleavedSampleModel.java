/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
 *  This class represents image data which is stored in a pixel interleaved
 *  fashion and for
 *  which each sample of a pixel occupies one data element of the DataBuffer.
 *  It subclasses ComponentSampleModel but provides a more efficient
 *  implementation for accessing pixel interleaved image data than is provided
 *  by ComponentSampleModel.  This class
 *  stores sample data for all bands in a single bank of the
 *  DataBuffer. Accessor methods are provided so that image data can be
 *  manipulated directly. Pixel stride is the number of
 *  data array elements between two samples for the same band on the same
 *  scanline. Scanline stride is the number of data array elements between
 *  a given sample and the corresponding sample in the same column of the next
 *  scanline.  Band offsets denote the number
 *  of data array elements from the first data array element of the bank
 *  of the DataBuffer holding each band to the first sample of the band.
 *  The bands are numbered from 0 to N-1.
 *  Bank indices denote the correspondence between a bank of the data buffer
 *  and a band of image data.
 *  This class supports
 *  {@link DataBuffer#TYPE_BYTE TYPE_BYTE},
 *  {@link DataBuffer#TYPE_USHORT TYPE_USHORT},
 *  {@link DataBuffer#TYPE_SHORT TYPE_SHORT},
 *  {@link DataBuffer#TYPE_INT TYPE_INT},
 *  {@link DataBuffer#TYPE_FLOAT TYPE_FLOAT} and
 *  {@link DataBuffer#TYPE_DOUBLE TYPE_DOUBLE} datatypes.
 * <p>
 *  该类表示以像素交错方式存储的图像数据,并且像素的每个样本占据DataBuffer的一个数据元素。
 * 它子类化ComponentSampleModel,但提供了一个比ComponentSampleModel提供的更高效的访问像素交错图像数据的实现。
 * 这个类存储DataBuffer的单个区域中的所有频带的样本数据。提供访问器方法,使得可以直接操纵图像数据。像素跨距是在相同扫描线上的相同带的两个样本之间的数据阵列元素的数量。
 * 扫描线跨距是给定样本和下一扫描线的同一列中的相应样本之间的数据阵列元素的数量。带偏移表示从保持每个频带的DataBuffer的组的第一数据阵列元素到频带的第一样本的数据阵列元素的数目。
 * 频带从0到N-1编号。存储体索引表示数据缓冲器的存储体和图像数据的带之间的对应关系。
 * 此类支持{@link DataBuffer#TYPE_BYTE TYPE_BYTE},{@link DataBuffer#TYPE_USHORT TYPE_USHORT},{@link DataBuffer#TYPE_SHORT TYPE_SHORT}
 * ,{@link DataBuffer#TYPE_INT TYPE_INT},{@link DataBuffer#TYPE_FLOAT TYPE_FLOAT}和{@链接DataBuffer#TYPE_DOUBLE TYPE_DOUBLE}
 * 数据类型。
 * 频带从0到N-1编号。存储体索引表示数据缓冲器的存储体和图像数据的带之间的对应关系。
 * 
 */

public class PixelInterleavedSampleModel extends ComponentSampleModel
{
    /**
     * Constructs a PixelInterleavedSampleModel with the specified parameters.
     * The number of bands will be given by the length of the bandOffsets
     * array.
     * <p>
     * 构造具有指定参数的PixelInterleavedSampleModel。带的数量将由bandOffsets数组的长度给出。
     * 
     * 
     * @param dataType  The data type for storing samples.
     * @param w         The width (in pixels) of the region of
     *                  image data described.
     * @param h         The height (in pixels) of the region of
     *                  image data described.
     * @param pixelStride The pixel stride of the image data.
     * @param scanlineStride The line stride of the image data.
     * @param bandOffsets The offsets of all bands.
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     * @throws IllegalArgumentException if any offset between bands is
     *         greater than the scanline stride
     * @throws IllegalArgumentException if the product of
     *         <code>pixelStride</code> and <code>w</code> is greater
     *         than <code>scanlineStride</code>
     * @throws IllegalArgumentException if <code>pixelStride</code> is
     *         less than any offset between bands
     * @throws IllegalArgumentException if <code>dataType</code> is not
     *         one of the supported data types
     */
    public PixelInterleavedSampleModel(int dataType,
                                       int w, int h,
                                       int pixelStride,
                                       int scanlineStride,
                                       int bandOffsets[]) {
        super(dataType, w, h, pixelStride, scanlineStride, bandOffsets);
        int minBandOff=this.bandOffsets[0];
        int maxBandOff=this.bandOffsets[0];
        for (int i=1; i<this.bandOffsets.length; i++) {
            minBandOff = Math.min(minBandOff,this.bandOffsets[i]);
            maxBandOff = Math.max(maxBandOff,this.bandOffsets[i]);
        }
        maxBandOff -= minBandOff;
        if (maxBandOff > scanlineStride) {
            throw new IllegalArgumentException("Offsets between bands must be"+
                                               " less than the scanline "+
                                               " stride");
        }
        if (pixelStride*w > scanlineStride) {
            throw new IllegalArgumentException("Pixel stride times width "+
                                               "must be less than or "+
                                               "equal to the scanline "+
                                               "stride");
        }
        if (pixelStride < maxBandOff) {
            throw new IllegalArgumentException("Pixel stride must be greater"+
                                               " than or equal to the offsets"+
                                               " between bands");
        }
    }

    /**
     * Creates a new PixelInterleavedSampleModel with the specified
     * width and height.  The new PixelInterleavedSampleModel will have the
     * same number of bands, storage data type, and pixel stride
     * as this PixelInterleavedSampleModel.  The band offsets may be
     * compressed such that the minimum of all of the band offsets is zero.
     * <p>
     *  创建具有指定宽度和高度的新PixelInterleavedSampleModel。
     * 新的PixelInterleavedSampleModel将具有与此PixelInterleavedSampleModel相同的波段数,存储数据类型和像素跨度。
     * 可以压缩频带偏移,使得所有频带偏移的最小值为零。
     * 
     * 
     * @param w the width of the resulting <code>SampleModel</code>
     * @param h the height of the resulting <code>SampleModel</code>
     * @return a new <code>SampleModel</code> with the specified width
     *         and height.
     * @throws IllegalArgumentException if <code>w</code> or
     *         <code>h</code> is not greater than 0
     */
    public SampleModel createCompatibleSampleModel(int w, int h) {
        int minBandoff=bandOffsets[0];
        int numBands = bandOffsets.length;
        for (int i=1; i < numBands; i++) {
            if (bandOffsets[i] < minBandoff) {
                minBandoff = bandOffsets[i];
            }
        }
        int[] bandOff;
        if (minBandoff > 0) {
            bandOff = new int[numBands];
            for (int i=0; i < numBands; i++) {
                bandOff[i] = bandOffsets[i] - minBandoff;
            }
        }
        else {
            bandOff = bandOffsets;
        }
        return new PixelInterleavedSampleModel(dataType, w, h, pixelStride,
                                               pixelStride*w, bandOff);
    }

    /**
     * Creates a new PixelInterleavedSampleModel with a subset of the
     * bands of this PixelInterleavedSampleModel.  The new
     * PixelInterleavedSampleModel can be used with any DataBuffer that the
     * existing PixelInterleavedSampleModel can be used with.  The new
     * PixelInterleavedSampleModel/DataBuffer combination will represent
     * an image with a subset of the bands of the original
     * PixelInterleavedSampleModel/DataBuffer combination.
     * <p>
     *  使用此PixelInterleavedSampleModel的波段子集创建一个新的PixelInterleavedSampleModel。
     * 新的PixelInterleavedSampleModel可以与现有PixelInterleavedSampleModel一起使用的任何DataBuffer一起使用。
     * 新的PixelInterleavedSampleModel / DataBuffer组合将表示具有原始PixelInterleavedSampleModel / DataBuffer组合的带的子集的图像
     */
    public SampleModel createSubsetSampleModel(int bands[]) {
        int newBandOffsets[] = new int[bands.length];
        for (int i=0; i<bands.length; i++) {
            newBandOffsets[i] = bandOffsets[bands[i]];
        }
        return new PixelInterleavedSampleModel(this.dataType, width, height,
                                               this.pixelStride,
                                               scanlineStride, newBandOffsets);
    }

    // Differentiate hash code from other ComponentSampleModel subclasses
    public int hashCode() {
        return super.hashCode() ^ 0x1;
    }
}
