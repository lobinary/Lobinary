/***** Lobxxx Translate Finished ******/
/*
 *
 * Copyright (c) 2007, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**********************************************************************
 **********************************************************************
 **********************************************************************
 *** COPYRIGHT (c) 1997-1998 Eastman Kodak Company.                 ***
 *** As  an unpublished  work pursuant to Title 17 of the United    ***
 *** States Code.  All rights reserved.                             ***
 **********************************************************************
 **********************************************************************
 * <p>
 *  **************************************************** ****************** ****************************
 * **** ************************************ * COPYRIGHT(c)1997-1998 Eastman Kodak公司。
 *  *** *根据United *** *国家法典第17章的未发表的作品。版权所有。
 *  *** *********************************************** ********************* **************************
 * *** ***************************************。
 *  *** *根据United *** *国家法典第17章的未发表的作品。版权所有。
 * 
 * 
 **********************************************************************/

package com.sun.image.codec.jpeg;


/**
 * JPEGImageDecoder Interface
 *
 * JPEGImageDecoder decompresses an JPEG InputStream into a Raster or
 * a BufferedImage depending upon the method invoked. Decoding the
 * JPEG input stream is controlled by the parameters in the
 * JPEGDecodeParam object.  If no JPEGDecodeParam object has been
 * specified then one is created to contain information about a
 * decompressed JPEG stream.<P>
 *
 * The JPEGDecodeParam object is updated with information from the
 * file header during decompression. If the input stream contains
 * tables only information (no image data), the JPEGDecodeParam object
 * will be updated and NULL returned for the output image. If the
 * input stream contains only image data, the parameters and tables in
 * the current JPEGDecodeParam object will be used to decode in
 * decoding the JPEG stream. If no tables are set in the
 * JPEGDecodeParam object, an exception will be thrown.<P>
 *
 * ColorSpace comments: First off JPEG by specification is color
 * blind!  That said, some color space conversion is done in the name
 * of better compression ratios.  If a BufferedImage is requested
 * common color conversions will be applied. Some updates to the
 * standard color space designations have been made to allow this
 * decoder to handle alpha channels.  See the JPEGDecodeParam
 * description for more details on additional color space
 * <p>
 *  JPEGImageDecoder接口
 * 
 *  JPEGImageDecoder根据调用的方法将JPEG InputStream解压缩为栅格或缓冲图像。解码JPEG输入流由JPEGDecodeParam对象中的参数控制。
 * 如果没有指定JPEGDecodeParam对象,则创建一个包含关于解压缩的JPEG流的信息。<P>。
 * 
 *  在解压缩期间,JPEGDecodeParam对象使用文件头中的信息进行更新。如果输入流包含仅表信息(无图像数据),则JPEGDecodeParam对象将被更新,并为输出图像返回NULL。
 * 如果输入流仅包含图像数据,则当前JPEGDecodeParam对象中的参数和表将用于解码JPEG流中的解码。如果没有在JPEGDecodeParam对象中设置表,则会抛出异常。<P>。
 * 
 * ColorSpace的评论：首先JPEG的规格是色盲！也就是说,一些颜色空间转换是以更好的压缩比名称进行的。如果请求BufferedImage,将应用常见的颜色转换。
 * 已经对标准颜色空间指定进行了一些更新以允许该解码器处理α通道。有关附加颜色空间的更多详细信息,请参阅JPEGDecodeParam描述。
 * 
 * 
 * designations ( @see JPEGDecodeParam ).<P>
 *
 * This decoder can process interchange, abbreviated and progressive
 * jpeg streams.  However, progressive jpeg streams are treated as
 * interchange streams.  They return once with the entire image in the
 * image buffer.
 */

import java.io.InputStream;
import java.io.IOException;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;

/**
 * This interface describes a JPEG data stream decoder.  This decoder
 * takes an InputStream that contains JPEG encoded image data.  The
 * JPEGImageDecoder will decode the JPEG image data according to the
 * parameters set in a JPEGDecodeParam object.  The resulting image
 * data is returned in either a Raster or a BufferedImage.
 * <p>
 * Note that the classes in the com.sun.image.codec.jpeg package are not
 * part of the core Java APIs.  They are a part of Sun's JDK and JRE
 * distributions.  Although other licensees may choose to distribute these
 * classes, developers cannot depend on their availability in non-Sun
 * implementations.  We expect that equivalent functionality will eventually
 * be available in a core API or standard extension.
 * <p>
 *
 * <p>
 *  此接口描述JPEG数据流解码器。该解码器接收包含JPEG编码的图像数据的InputStream。
 *  JPEGImageDecoder将根据在JPEGDecodeParam对象中设置的参数对JPEG图像数据进行解码。生成的图像数据以Raster或BufferedImage返回。
 * <p>
 *  请注意,com.sun.image.codec.jpeg包中的类不是核心Java API的一部分。它们是Sun的JDK和JRE发行版的一部分。
 * 虽然其他许可证持有者可能选择分发这些类,但开发人员不能依赖其在非Sun实施中的可用性。我们期望等效功能最终将在核心API或标准扩展中可用。
 * <p>
 * 
 * 
 * @see JPEGCodec
 * @see JPEGDecodeParam
 * @see Raster
 * @see BufferedImage
 */

public interface JPEGImageDecoder {

    /**
     * Returns the JPEGDecodeParam object that resulted from the most
     * recent decoding event.
     * <p>
     *  返回从最近的解码事件产生的JPEGDecodeParam对象。
     * 
     */
    public JPEGDecodeParam getJPEGDecodeParam();

    /**
     * Sets the JPEGDecodeParam object used to determine the features
     * of the decompression performed on the JPEG encoded data.  This
     * is ussually only needed for decoding abbreviated JPEG data
     * streams.
     * <p>
     *  设置用于确定对JPEG编码数据执行的解压缩的功能的JPEGDecodeParam对象。这通常仅需要解码缩略的JPEG数据流。
     * 
     * 
     * @param jdp JPEGDecodeParam object
     */
    public void setJPEGDecodeParam(JPEGDecodeParam jdp);

        /**
         * Get the input stream that decoding will occur from.
         * <p>
         *  获取将从中进行解码的输入流。
         * 
         * 
         * @return The stream that the decoder is currently assciated with.
         */
        public InputStream getInputStream();

    /**
     * Decode the JPEG stream that was passed as part of
     * construction.  The JPEG decompression will be performed
     * according to the current settings of the JPEGDecodeParam
     * object.  For a tables only stream this will return null.
     * <p>
     * 解码作为构造的一部分传递的JPEG流。 JPEG解压缩将根据JPEGDecodeParam对象的当前设置执行。对于只有流的表,它将返回null。
     * 
     * 
     * @return Raster containg the image data.  Colorspace and other
     *         pertinent information can be obtained from the
     *         JPEGDecodeParam object.
     * @exception ImageFormatException if irregularities in the JPEG
     *            stream or an unknown condition is encountered.
     */
    public Raster decodeAsRaster()
                throws IOException, ImageFormatException;

    /**
     * Decodes the current JPEG data stream.  The result of decoding
     * this InputStream is a BufferedImage the ColorModel associated
     * with this BufferedImage is determined based on the encoded
     * COLOR_ID of the JPEGDecodeParam object.  For a tables only
     * stream this will return null.
     * <p>
     *  解码当前JPEG数据流。
     * 解码此InputStream的结果是BufferedImage,与此BufferedImage相关联的ColorModel是基于JPEGDecodeParam对象的编码COLOR_ID确定的。
     * 
     * @return BufferedImage containing the image data.
     * @exception ImageFormatException if irregularities in the JPEG
     *            stream or an unknown condition is encountered.
     */
    public BufferedImage decodeAsBufferedImage()
                throws IOException, ImageFormatException;

} // end class JPEGImageDecoder
