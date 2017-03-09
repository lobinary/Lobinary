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

import sun.awt.image.codec.JPEGImageDecoderImpl;
import sun.awt.image.codec.JPEGImageEncoderImpl;
import sun.awt.image.codec.JPEGParam;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.ColorModel;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class is a factory for implementations of the JPEG Image
 * Decoder/Encoder.
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
 *  这个类是实现JPEG图像解码器/编码器的工厂。
 * <p>
 *  请注意,com.sun.image.codec.jpeg包中的类不是核心Java API的一部分。它们是Sun的JDK和JRE发行版的一部分。
 * 虽然其他许可证持有者可能选择分发这些类,但开发人员不能依赖其在非Sun实施中的可用性。我们期望等效功能最终将在核心API或标准扩展中可用。
 * <p>
 * 
 * 
 * @see JPEGImageDecoder
 * @see JPEGImageEncoder
 */
public class JPEGCodec {
        private JPEGCodec() { }

        /**
         * This creates an instance of a JPEGImageDecoder that can be used
         * to decode JPEG Data streams.
         * <p>
         *  这将创建一个JPEGImageDecoder实例,可用于解码JPEG数据流。
         * 
         */
        public static JPEGImageDecoder createJPEGDecoder(InputStream src) {
                return new JPEGImageDecoderImpl(src);
        }

        /**
         * This creates an instance of a JPEGImageDecoder that can be used
         * to decode JPEG Data streams.
         * <p>
         *  这将创建一个JPEGImageDecoder实例,可用于解码JPEG数据流。
         * 
         */
        public static JPEGImageDecoder createJPEGDecoder(InputStream src,
                                                                                                         JPEGDecodeParam jdp) {
                return new JPEGImageDecoderImpl(src, jdp);
        }

        /**
         * This creates an instance of a JPEGImageEncoder that can be used
         * to encode image data as JPEG Data streams.
         * <p>
         *  这将创建一个JPEGImageEncoder实例,可用于将图像数据编码为JPEG数据流。
         * 
         */
        public static JPEGImageEncoder createJPEGEncoder(OutputStream dest) {
                return new JPEGImageEncoderImpl(dest);
        }
        /**
         * This creates an instance of a JPEGImageEncoder that can be used
         * to encode image data as JPEG Data streams.
         * <p>
         * 这将创建一个JPEGImageEncoder实例,可用于将图像数据编码为JPEG数据流。
         * 
         */
        public static JPEGImageEncoder createJPEGEncoder(OutputStream dest,
                                                                                                         JPEGEncodeParam jep) {
                return new JPEGImageEncoderImpl(dest, jep);
        }

        /**
          * This is a factory method for creating JPEGEncodeParam objects.
          * The returned object should do a credible job of encoding the
          * given BufferedImage.
          * <p>
          *  这是一个用于创建JPEGEncodeParam对象的工厂方法。返回的对象应该做一个可信的编码给定BufferedImage的工作。
          * 
          * 
          * @param bi A BufferedImage that is similar to the BufferedImage(s)
          * that will encoded using the returned JPEGEncodeParam object.
          */
        public static JPEGEncodeParam getDefaultJPEGEncodeParam(BufferedImage bi)
        {
        int colorID = JPEGParam.getDefaultColorId(bi.getColorModel());
        return getDefaultJPEGEncodeParam(bi.getRaster(), colorID);
        }

        /**
          * This is a factory method for creating JPEGEncodeParam objects.
          * It is the users responsiblity to match the colorID with the
          * data contained in the Raster.  Failure to do so may lead to
          * either poor compression or poor image quality.  If you don't
          * understand much about JPEG it is strongly recommended that you
          * stick to the BufferedImage interface.
          * <p>
          *  这是一个用于创建JPEGEncodeParam对象的工厂方法。用户负责将colorID与栅格中包含的数据进行匹配。否则可能导致压缩不良或图像质量差。
          * 如果你不太了解JPEG,强烈建议你坚持BufferedImage接口。
          * 
          * 
          * @param ras Raster that is similar to those to be encoded later.
          * @param colorID the COLOR_ID for the encoded data.  This should
          *        match the data in the raster.
          */
        public static JPEGEncodeParam getDefaultJPEGEncodeParam(Raster ras,
                                                                int colorID)
        {
        JPEGParam ret = new JPEGParam(colorID, ras.getNumBands());
        ret.setWidth(ras.getWidth());
        ret.setHeight(ras.getHeight());

        return ret;
        }

        /**
          * This is a factory method for creating JPEGEncodeParam objects.  It
          * is the users responsiblity to match the colorID with the given
          * number of bands, which should match the data being encoded.
          * Failure to do so may lead to poor compression and/or poor image
          * quality.  If you don't understand much about JPEG it is strongly
          * recommended that you stick to the BufferedImage interface.
          *
          * This can also be used as a factory for a JPEGDecodeParam object.
          * However this usage is extremely rare, as one needs to be decoding
          * abbreviated JPEG streams where the JPEG tables are coming from
          * some source other than a JPEG tables only stream.
          *
          * <p>
          *  这是一个用于创建JPEGEncodeParam对象的工厂方法。用户负责将colorID与给定数量的带匹配,应与要编码的数据匹配。否则可能导致压缩不良和/或图像质量差。
          * 如果你不太了解JPEG,强烈建议你坚持BufferedImage接口。
          * 
          *  这也可以用作JPEGDecodeParam对象的工厂。然而,这种使用是非常罕见的,因为人们需要解码简缩的JPEG流,其中JPEG表来自除了仅JPEG流之外的一些源。
          * 
          * 
          * @param numBands the number of bands that will be encoded (max of four).
          * @param colorID the COLOR_ID for the encoded data.  This is used to
          * set reasonable defaults in the parameter object.  This must match
          * the number of bands given.
          */
        public static JPEGEncodeParam getDefaultJPEGEncodeParam(int numBands,
                                                                int colorID)
            throws ImageFormatException
        {
        return new JPEGParam(colorID, numBands);
        }

        /**
         * This is a factory method for creating a JPEGEncodeParam from a
         * JPEGDecodeParam.  This will return a new JPEGEncodeParam object
         * that is initialized from the JPEGDecodeParam object.  All major
         * pieces of information will be initialized from the DecodeParam
         * (Markers, Tables, mappings).
         * <p>
         * 
         * @param jdp The JPEGDecodeParam object to copy.
         */

        public static JPEGEncodeParam getDefaultJPEGEncodeParam(JPEGDecodeParam jdp)
            throws ImageFormatException {
            return new JPEGParam(jdp);
        }
}
