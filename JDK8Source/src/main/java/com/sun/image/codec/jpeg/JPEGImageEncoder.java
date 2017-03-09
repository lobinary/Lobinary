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
 * JPEGImageEncoder Interface
 *
 * JPEGImageEncoder compresses images into JPEG data streams and
 * writes the JPEG stream to an OutputStream.  Image data that is to
 * be encoded can be passed in as a Raster of image data or as a
 * BufferedImage.  Encoding or the image data into the output JPEG
 * stream is controlled by the parameters setting found in the
 * JPEGEncodeParam object.<P>
 *
 * ColorSpace comments: First off JPEG by specification is color
 * blind!  That said, this interface will perform some color space
 * conversion in the name of better compression ratios.  There is no
 * explicit mechanism in the JPEGEncodeParam interface for controlling
 * the Encoded ColorSpace of the data when it is written to the JPEG
 * data stream.  If an approriate colorspace setting is not already
 * defined it is recommended that colorspace unknown is used.  Some
 * updates to the standard color space designations have been made to
 * allow this decoder to handle alpha channels.  See the
 * JPEGEncodeParam description for more details on additional color
 * <p>
 *  JPEGImageEncoder接口
 * 
 *  JPEGImageEncoder将图像压缩为JPEG数据流,并将JPEG流写入OutputStream。要编码的图像数据可以作为图像数据的栅格或作为缓冲图像传递。
 * 编码或图像数据到输出JPEG流由JPEGEncodeParam对象中的参数设置控制。<P>。
 * 
 * ColorSpace的评论：首先JPEG的规格是色盲！也就是说,这个接口将以更好的压缩比的名义执行一些颜色空间转换。
 *  JPEGEncodeParam接口中没有明确的机制,用于在将数据写入JPEG数据流时控制数据的编码色彩空间。如果尚未定义颜色空间设置,则建议使用colorspace unknown。
 * 已经对标准颜色空间指定进行了一些更新以允许该解码器处理α通道。有关附加颜色的详细信息,请参阅JPEGEncodeParam描述。
 * 
 * 
 * space designations ( @see JPEGEncodeParam ).<P>
 *
 * This encoder will process interchange, and abbreviated JPEG
 * streams.
 */

import java.io.OutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;

/**
 * JPEGImageEncoder encodes buffers of image data into JPEG data
 * streams.  Users of this interface are required to provide image data in
 * a Raster or a BufferedImage, set the necessary parameters in the
 * JPEGEncodeParams object and successfully open the
 * <code>OutputStream</code> that is the destination of the encoded
 * JPEG stream.
 *
 * The JPEGImageEncoder interface can encode image data into interchange,
 * and abbreviated JPEG data streams that are written to the
 * OutputStream provided to the encoder.
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
 *  JPEGImageEncoder将图像数据的缓冲区编码为JPEG数据流。
 * 该接口的用户需要在Raster或BufferedImage中提供图像数据,在JPEGEncodeParams对象中设置必要的参数,并成功打开作为编码JPEG流目的地的<code> OutputStrea
 * m </code>。
 *  JPEGImageEncoder将图像数据的缓冲区编码为JPEG数据流。
 * 
 *  JPEGImageEncoder接口可以将图像数据编码为互换,并将缩写的JPEG数据流写入提供给编码器的OutputStream。
 * <p>
 * 请注意,com.sun.image.codec.jpeg包中的类不是核心Java API的一部分。它们是Sun的JDK和JRE发行版的一部分。
 * 虽然其他许可证持有者可能选择分发这些类,但开发人员不能依赖其在非Sun实施中的可用性。我们期望等效功能最终将在核心API或标准扩展中可用。
 * <p>
 * 
 * 
 * @see         JPEGCodec
 * @see         JPEGEncodeParam
 * @see         Raster
 * @see         BufferedImage
 * @see         OutputStream
 */

public interface JPEGImageEncoder {
        /**
         * Return the stream the Encoder is currenlt associated with.
         * <p>
         *  返回与编码器currenlt相关联的流。
         * 
         */
        public OutputStream getOutputStream();

        /**
         * Set the JPEGEncodeParam object that is to be used for future
         * encoding operations. 'jep' is copied so changes will not be
         * tracked, unless you call this method again.
         * <p>
         *  设置要用于将来编码操作的JPEGEncodeParam对象。 'jep'被复制,因此将不会跟踪更改,除非您再次调用此方法。
         * 
         * 
         * @param jep The JPEGEncodeParam object to use for future encodings.
         *
         */
        public void setJPEGEncodeParam(JPEGEncodeParam jep);

        /**
         * This returns a copy of the current JPEGEncodeParam object, if
         * you want changes to affect the encoding process you must 'set'
         * it back into the encoder (either through setJPEGEncodeParam or
         * by providing the modified param object in the call to encode.
         * <p>
         *  这将返回当前JPEGEncodeParam对象的副本,如果要更改影响编码过程,您必须将其设置回编码器(通过setJPEGEncodeParam或通过在调用调用中提供修改的param对象)。
         * 
         * 
         * @return A copy of the current JPEGEncodeParam object
         */
        public JPEGEncodeParam getJPEGEncodeParam();

        /**
         * This is a factory method for creating JPEGEncodeParam objects.
         * The returned object will do a credible job of encoding the
         * given BufferedImage.
         * <p>
         *  这是一个用于创建JPEGEncodeParam对象的工厂方法。返回的对象将做一个可信的编码给定的BufferedImage的工作。
         * 
         */
        public JPEGEncodeParam getDefaultJPEGEncodeParam(BufferedImage bi)
                throws ImageFormatException;

        /**
         * Encode a BufferedImage as a JPEG data stream.  Note, some color
         * conversions may takes place.  The current JPEGEncodeParam's
         * encoded COLOR_ID should match the value returned by
         * getDefaultColorID when given the BufferedImage's ColorModel.<P>

         * If no JPEGEncodeParam object has been provided yet a default
         * one will be created by calling getDefaultJPEGEncodeParam with
         * bi.

         * <p>
         *  将BufferedImage编码为JPEG数据流。请注意,可能会发生一些颜色转换。
         * 当给定BufferedImage的ColorModel时,当前JPEGEncodeParam的编码COLOR_ID应该与getDefaultColorID返回的值匹配。<P>。
         * 
         *  如果没有提供JPEGEncodeParam对象,则将通过调用带有bi的getDefaultJPEGEncodeParam创建一个默认的对象。
         * 
         * 
         * @param bi The BufferedImage to encode.
         */
        public void encode(BufferedImage bi)
                throws IOException, ImageFormatException;

        /**
         * Encode a BufferedImage as a JPEG data stream.  Note, some color
         * conversions may takes place.  The jep's encoded COLOR_ID should
         * match the value returned by getDefaultColorID when given the
         * BufferedImage's ColorModel.<P>

         * This call also sets the current JPEGEncodeParam object.  The
         * given JPEGEncodeParam object will be used for this and future
         * encodings.  If jep is null then a new JPEGEncodeParam object
         * will be created by calling getDefaultJPEGEncodeParam with bi.

         * <p>
         * 将BufferedImage编码为JPEG数据流。请注意,可能会发生一些颜色转换。
         * 给定BufferedImage的ColorModel时,jep的编码COLOR_ID应该与getDefaultColorID返回的值相匹配。<P>。
         * 
         *  此调用还设置当前JPEGEncodeParam对象。给定的JPEGEncodeParam对象将用于此和将来的编码。
         * 如果jep为null,那么将通过调用带有bi的getDefaultJPEGEncodeParam来创建一个新的JPEGEncodeParam对象。
         * 
         * 
         * @param bi  The BufferedImage to encode.
         * @param jep The JPEGEncodeParam object used to control the encoding.
         */
        public void encode(BufferedImage bi, JPEGEncodeParam jep)
                throws IOException, ImageFormatException;

        /**
         * Returns the 'default' encoded COLOR_ID for a given ColorModel.
         * This method is not needed in the simple case of encoding
         * Buffered Images (the library will figure things out for you).
         * It can be useful for encoding Rasters.  To determine what needs
         * to be done to the image prior to encoding.

         * <p>
         *  返回给定ColorModel的"默认"编码COLOR_ID。这种方法不需要在编码缓冲图像的简单情况下(图书馆会为你解决问题)。它可以用于编码Raster。以确定在编码之前需要对图像做什么。
         * 
         * 
         * @param cm The ColorModel to map to an jpeg encoded COLOR_ID.
         * @return The default mapping of cm to a jpeg Color_ID note that
         * in a few cases color conversion is required.
         */
        public int getDefaultColorId(ColorModel cm);

        /**
         * This is a factory method for creating JPEGEncodeParam objects.
         * It is the users responsiblity to match the colorID with the
         * data contained in the Raster.  Failure to do so may lead to
         * either poor compression or poor image quality.  If you don't
         * understand much about JPEG it is strongly reccomended that you
         * stick to the BufferedImage interfaces.
         * <p>
         *  这是一个用于创建JPEGEncodeParam对象的工厂方法。用户负责将colorID与栅格中包含的数据进行匹配。否则可能导致压缩不良或图像质量差。
         * 如果你不太了解JPEG,强烈建议你坚持BufferedImage接口。
         * 
         */
        public JPEGEncodeParam getDefaultJPEGEncodeParam(Raster ras, int colorID)
                throws ImageFormatException;

        /**
          * This is a factory method for creating JPEGEncodeParam objects.  It
          * is the users responsiblity to match the colorID with the given
          * number of bands, which should match the data being encoded.
          * Failure to do so may lead to poor compression and/or poor image
          * quality.  If you don't understand much about JPEG it is strongly
          * recommended that you stick to the BufferedImage interface.
          *
          * <p>
          *  这是一个用于创建JPEGEncodeParam对象的工厂方法。用户负责将colorID与给定数量的带匹配,应与要编码的数据匹配。否则可能导致压缩不良和/或图像质量差。
          * 如果你不太了解JPEG,强烈建议你坚持BufferedImage接口。
          * 
          * 
          * @param numBands the number of bands that will be encoded (max of
          * four).
          * @param colorID the COLOR_ID for the encoded data.  This is used to
          * set reasonable defaults in the parameter object.  This must match
          * the number of bands given.
          */
        public JPEGEncodeParam getDefaultJPEGEncodeParam(int numBands,
                                                         int colorID)
                throws ImageFormatException;

        /**
         * This is a factory method for creating a JPEGEncodeParam from a
         * JPEGDecodeParam.  This will return a new JPEGEncodeParam object
         * that is initialized from the JPEGDecodeParam object.  All major
         * pieces of information will be initialized from the DecodeParam
         * (Markers, Tables, mappings).
         * <p>
         * 这是一个从JPEGDecodeParam创建JPEGEncodeParam的工厂方法。这将返回一个新的JPEGEncodeParam对象,该对象从JPEGDecodeParam对象初始化。
         * 所有主要的信息将从DecodeParam(标记,表,映射)初始化。
         * 
         * 
         * @param jdp The JPEGDecodeParam object to copy.
         */
        public JPEGEncodeParam getDefaultJPEGEncodeParam(JPEGDecodeParam jdp)
            throws ImageFormatException;

        /**
         * Encode a Raster as a JPEG data stream.  Note that no color
         * conversion takes place.  It is required that you match the
         * Raster to the encoded COLOR_ID contained in the current
         * JPEGEncodeParam object.<P>

         * If no JPEGEncodeParam object has been provided yet a
         * new JPEGEncodeParam object will be created by calling
         * getDefaultJPEGEncodeParam with ras and COLOR_ID_UNKNOWN.

         * <p>
         *  将栅格编码为JPEG数据流。请注意,不会进行颜色转换。您需要将栅格与当前JPEGEncodeParam对象中包含的编码COLOR_ID匹配。<P>
         * 
         *  如果没有提供JPEGEncodeParam对象,则将通过调用具有ras和COLOR_ID_UNKNOWN的getDefaultJPEGEncodeParam来创建新的JPEGEncodeParam对象
         * 。
         * 
         * 
         * @param ras The Raster to encode.
         */
        public void encode(Raster ras)
          throws IOException, ImageFormatException;

        /**
         * Encode a Raster as a JPEG data stream.  Note that no color
         * conversion takes place.  It is required that you match the
         * Raster to the encoded COLOR_ID contained in the JPEGEncodeParam
         * object.

         * If jep is null a new JPEGEncodeParam object will be created by
         * calling getDefaultJPEGEncodeParam with ras and
         * COLOR_ID_UNKNOWN.

         * <p>
         * 
         * @param ras The Raster to encode.
         * @param jep The JPEGEncodeParam object used to control the encoding.
         */
        public void encode(Raster ras, JPEGEncodeParam jep)
                throws IOException, ImageFormatException;
}
