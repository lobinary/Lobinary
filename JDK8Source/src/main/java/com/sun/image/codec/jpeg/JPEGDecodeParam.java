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

 * JPEGDecodeParam encapsulates tables and options necessary to
 * control decoding JPEG datastreams. Parameters are either set explicitly
 * by the application for encoding, or read from the JPEG header for
 * decoding.  In the case of decoding abbreviated data streams the
 * application may need to set some/all of the values it's self.  <p>

 * When working with BufferedImages ({@link
 * com.sun.image.codec.jpeg.JPEGImageDecoder#decodeAsBufferedImage}),
 * the codec will attempt to
 * generate an appropriate ColorModel for the JPEG COLOR_ID. This is
 * not always possible (example mappings are listed below) .  In cases
 * where unsupported conversions are required, or unknown encoded
 * COLOR_ID's are in use, the user must request the data as a Raster
 * and perform the transformations themselves.  When decoding into a
 * raster ({@link com.sun.image.codec.jpeg.JPEGImageDecoder#decodeAsRaster})
 * no ColorSpace adjustments are made.

 * Note: The color ids described herein are simply enumerated values
 * that influence data processing by the JPEG codec.  JPEG compression
 * is by definition color blind.  These values are used as hints when
 * decompressing JPEG data.  Of particular interest is the default
 * conversion from YCbCr to sRGB when decoding buffered Images.<P>

 * Note: because JPEG is mostly color-blind color fidelity can not be
 * garunteed.  This will hopefully be rectified in the near future by
 * the wide spread inclusion of ICC-profiles in the JPEG data stream
 * (as a special marker).

 * The following is an example of the conversions that take place.
 * This is only a guide to the types of conversions that are allowed.
 * This list is likely to change in the future so it is
 * <B>strongly</B> recommended that you check for thrown
 * ImageFormatExceptions and check the actual ColorModel associated
 * with the BufferedImage returned rather than make assumtions.
 * <pre>
    DECODING:

    JPEG (Encoded) Color ID         BufferedImage ColorSpace
    =======================         ========================
    COLOR_ID_UNKNOWN                ** Invalid **
    COLOR_ID_GRAY                   CS_GRAY
    COLOR_ID_RGB                    CS_sRGB
    COLOR_ID_YCbCr                  CS_sRGB
    COLOR_ID_CMYK                   ** Invalid **
    COLOR_ID_PYCC                   CS_PYCC
    COLOR_ID_RGBA                   CS_sRGB (w/ alpha)
    COLOR_ID_YCbCrA                 CS_sRGB (w/ alpha)
    COLOR_ID_RGBA_INVERTED          ** Invalid **
    COLOR_ID_YCbCrA_INVERTED        ** Invalid **
    COLOR_ID_PYCCA                  CS_PYCC (w/ alpha)
    COLOR_ID_YCCK                   ** Invalid **
        </pre>

 * If the user needs better control over conversion, the user must
 * request the data as a Raster and handle the conversion of the image
 * data themselves.<p>

 * When decoding JFIF files the encoded COLOR_ID will always be one
 * of: COLOR_ID_UNKNOWN, COLOR_ID_GRAY, COLOR_ID_RGB, COLOR_ID_YCbCr,
 * COLOR_ID_CMYK, or COLOR_ID_YCCK
 * <p>
 * Note that the classes in the com.sun.image.codec.jpeg package are not
 * part of the core Java APIs.  They are a part of Sun's JDK and JRE
 * distributions.  Although other licensees may choose to distribute these
 * classes, developers cannot depend on their availability in non-Sun
 * implementations.  We expect that equivalent functionality will eventually
 * be available in a core API or standard extension.
 * <p>
 * <p>
 *  JPEGDecodeParam封装控制解码JPEG数据流所需的表和选项。参数由应用程序显式地设置用于编码,或者从JPEG头部读取用于解码。
 * 在解码简缩数据流的情况下,应用可能需要设置它自己的一些/所有值。 <p>。
 * 
 * 当使用BufferedImages({@link com.sun.image.codec.jpeg.JPEGImageDecoder#decodeAsBufferedImage})时,编解码器将尝试为J
 * PEG COLOR_ID生成适当的ColorModel。
 * 这不总是可能的(示例映射如下所示)。如果需要不支持的转化,或未使用编码的COLOR_ID,则用户必须将数据请求为栅格并自行执行转换。
 * 当解码为光栅({@link com.sun.image.codec.jpeg.JPEGImageDecoder#decodeAsRaster})时,不会进行ColorSpace调整。
 * 
 *  注意：这里描述的颜色id是简单地枚举的值,其影响JPEG编解码器的数据处理。 JPEG压缩是根据定义的色盲。这些值在解压缩JPEG数据时用作提示。
 * 特别感兴趣的是当解码缓冲的图像时从YCbCr到sRGB的默认转换。
 * 
 *  注意：因为JPEG主要是色盲的,色彩保真度不能适用。这将有希望在不久的将来通过在JPEG数据流中广泛地包括ICC简档(作为特殊标记)来纠正。
 * 
 *  以下是发生的转换的示例。这只是指导允许的转换类型。
 * 这个列表可能会在将来更改,因此它是<B>强烈</B>建议您检查抛出ImageFormatExceptions并检查与返回的BufferedImage相关联的实际ColorModel,而不是假设。
 * <pre>
 * 解码：
 * 
 *  JPEG(Encoded)Color ID BufferedImage ColorSpace ===================== ===================== ===== COL
 * OR_ID_UNKNOWN ** **无效COLOR_ID_GRAY CS_GRAY COLOR_ID_RGB CS_sRGB,则COLOR_ID_YCbCr CS_sRGB,则COLOR_ID_CMY
 * K ** **无效COLOR_ID_PYCC CS_PYCC COLOR_ID_RGBA CS_sRGB,则(W /阿尔法)COLOR_ID_RGBA_INVERTED COLOR_ID_YCbCrA 
 * CS_sRGB,则(W /阿尔法)** **无效** COLOR_ID_YCbCrA_INVERTED *无效* COLOR_ID_PYCCA CS_PYCC(w / alpha)COLOR_ID_YC
 * CK **无效**。
 * </pre>
 * 
 *  如果用户需要更好地控制转换,则用户必须将数据请求为光栅,并处理图像数据本身的转换。<p>
 * 
 *  当解码JFIF文件的编码COLOR_ID将永远之一：COLOR_ID_UNKNOWN,COLOR_ID_GRAY,COLOR_ID_RGB,COLOR_ID_YCbCr,COLOR_ID_CMYK或C
 * OLOR_ID_YCCK。
 * <p>
 *  请注意,com.sun.image.codec.jpeg包中的类不是核心Java API的一部分。它们是Sun的JDK和JRE发行版的一部分。
 * 虽然其他许可证持有者可能选择分发这些类,但开发人员不能依赖其在非Sun实施中的可用性。我们期望等效功能最终将在核心API或标准扩展中可用。
 * <p>
 */

public interface JPEGDecodeParam extends Cloneable {
    /** Unknown or Undefined Color ID */
    public final static int COLOR_ID_UNKNOWN = 0;

    /** Monochrome */
    public final static int COLOR_ID_GRAY = 1;

    /** Red, Green, and Blue */
    public final static int COLOR_ID_RGB = 2;

    /** YCbCr */
    public final static int COLOR_ID_YCbCr = 3;

    /** CMYK */
    public final static int COLOR_ID_CMYK = 4;

    /** PhotoYCC */
    public final static int COLOR_ID_PYCC = 5;

    /** RGB-Alpha */
        public final static int COLOR_ID_RGBA = 6;

    /** YCbCr-Alpha */
    public final static int COLOR_ID_YCbCrA = 7;

    /** RGB-Alpha with R, G, and B inverted.*/
    public final static int COLOR_ID_RGBA_INVERTED = 8;

    /** YCbCr-Alpha with Y, Cb, and Cr inverted.*/
    public final static int COLOR_ID_YCbCrA_INVERTED = 9;

    /** PhotoYCC-Alpha */
    public final static int COLOR_ID_PYCCA = 10;

    /** YCbCrK */
    public final static int COLOR_ID_YCCK = 11;

    /** Number of color ids defined. */
    final static int NUM_COLOR_ID = 12;

        /** Number of allowed Huffman and Quantization Tables */
    final static int  NUM_TABLES = 4;

        /** The X and Y units simply indicate the aspect ratio of the pixels. */
        public  final static int DENSITY_UNIT_ASPECT_RATIO = 0;
        /** Pixel density is in pixels per inch. */
        public  final static int DENSITY_UNIT_DOTS_INCH    = 1;
        /** Pixel density is in pixels per centemeter. */
        public  final static int DENSITY_UNIT_DOTS_CM      = 2;
        /** The max known value for DENSITY_UNIT */
        final static int NUM_DENSITY_UNIT = 3;

        /** APP0 marker - JFIF info */
        public final static int APP0_MARKER  = 0xE0;
        /** APP1 marker */
        public final static int APP1_MARKER  = 0xE1;
        /** APP2 marker */
        public final static int APP2_MARKER  = 0xE2;
        /** APP3 marker */
        public final static int APP3_MARKER  = 0xE3;
        /** APP4 marker */
        public final static int APP4_MARKER  = 0xE4;
        /** APP5 marker */
        public final static int APP5_MARKER  = 0xE5;
        /** APP6 marker */
        public final static int APP6_MARKER  = 0xE6;
        /** APP7 marker */
        public final static int APP7_MARKER  = 0xE7;
        /** APP8 marker */
        public final static int APP8_MARKER  = 0xE8;
        /** APP9 marker */
        public final static int APP9_MARKER  = 0xE9;
        /** APPA marker */
        public final static int APPA_MARKER  = 0xEA;
        /** APPB marker */
        public final static int APPB_MARKER  = 0xEB;
        /** APPC marker */
        public final static int APPC_MARKER  = 0xEC;
        /** APPD marker */
        public final static int APPD_MARKER  = 0xED;
        /** APPE marker - Adobe info */
        public final static int APPE_MARKER  = 0xEE;
        /** APPF marker */
        public final static int APPF_MARKER  = 0xEF;

        /** Adobe marker indicates presence/need for Adobe marker. */
        public final static int COMMENT_MARKER = 0XFE;

        public Object clone();

        /**
         * Get the image width
         * <p>
         *  获取图像宽度
         * 
         * 
         * @return int the width of the image data in pixels.
         */
        public int  getWidth();
        /** Get the image height
        /* <p>
        /* 
         * @return The height of the image data in pixels.
         */
        public int  getHeight();

        /**
         * Return the Horizontal subsampling factor for requested
         * Component.  The Subsample factor is the number of input pixels
         * that contribute to each output pixel.  This is distinct from
         * the way the JPEG to each output pixel.  This is distinct from
         * the way the JPEG standard defines this quantity, because
         * fractional subsampling factors are not allowed, and it was felt
         * <p>
         * 返回请求的组件的水平子采样因子。子采样因子是对每个输出像素有贡献的输入像素的数量。这与JPEG到每个输出像素的方式不同。这与JPEG标准定义此数量的方式不同,因为不允许使用部分子采样因子,而是感觉到
         * 
         * 
         * @param component The component of the encoded image to return
         * the subsampling factor for.
         * @return The subsample factor.
         */
        public int getHorizontalSubsampling(int component);

        /**
         * Return the Vertical subsampling factor for requested
         * Component.  The Subsample factor is the number of input pixels
         * that contribute to each output pixel.  This is distinct from
         * the way the JPEG to each output pixel.  This is distinct from
         * the way the JPEG standard defines this quantity, because
         * fractional subsampling factors are not allowed, and it was felt
         * <p>
         *  返回请求的组件的垂直子采样因子。子采样因子是对每个输出像素有贡献的输入像素的数量。这与JPEG到每个输出像素的方式不同。
         * 这与JPEG标准定义此数量的方式不同,因为不允许使用部分子采样因子,而是感觉到。
         * 
         * 
         * @param component The component of the encoded image to return
         * the subsampling factor for.
         * @return The subsample factor.
         */
        public int getVerticalSubsampling(int component);


        /**
         * Returns the coefficient quantization tables or NULL if not
         * defined. tableNum must range in value from 0 - 3.
         * <p>
         *  返回系数量化表,如果未定义,则返回NULL。 tableNum的值必须介于0  -  3之间。
         * 
         * 
         * @param tableNum the index of the table to be returned.
         * @return Quantization table stored at index tableNum.
         */
        public JPEGQTable  getQTable(int tableNum );

        /**
         * Returns the Quantization table for the requested component.
         * <p>
         *  返回请求的组件的量化表。
         * 
         * 
         * @param component the image component of interest.
         * @return Quantization table associated with component
         */
        public JPEGQTable getQTableForComponent(int component);

        /**
         * Returns the DC Huffman coding table requested or null if
         * not defined
         * <p>
         *  返回请求的DC霍夫曼编码表,如果没有定义,返回null
         * 
         * 
         * @param tableNum the index of the table to be returned.
         * @return Huffman table stored at index tableNum.
         */
        public JPEGHuffmanTable getDCHuffmanTable( int tableNum );
        /**
         * Returns the DC Huffman coding table for the requested component.
         * <p>
         *  返回请求的组件的DC霍夫曼编码表。
         * 
         * 
         * @param component the image component of interest.
         * @return Huffman table associated with component
         */
        public JPEGHuffmanTable getDCHuffmanTableForComponent(int component);


        /**
         * Returns the AC Huffman coding table requested or null if
         * not defined
         * <p>
         *  返回请求的AC Huffman编码表,如果没有定义,返回null
         * 
         * 
         * @param tableNum the index of the table to be returned.
         * @return Huffman table stored at index tableNum.
         */
        public JPEGHuffmanTable getACHuffmanTable( int tableNum );
        /**
         * Returns the AC Huffman coding table for the requested component.
         * <p>
         *  返回请求的组件的AC Huffman编码表。
         * 
         * 
         * @param component the image component of interest.
         * @return Huffman table associated with component
         */
        public JPEGHuffmanTable getACHuffmanTableForComponent(int component);



        /**
         * Get the number of the DC Huffman table that will be used for
         * a particular component.
         * <p>
         *  获取将用于特定组件的DC Huffman表的编号。
         * 
         * 
         * @param component The Component of interest.
         * @return The table number of the DC Huffman table for component.
         */
        public int getDCHuffmanComponentMapping(int component);
        /**
         * Get the number of the AC Huffman table that will be used for
         * a particular component.
         * <p>
         *  获取将用于特定组件的AC Huffman表的编号。
         * 
         * 
         * @param component The Component of interest.
         * @return The table number of the AC Huffman table for component.
         */
        public int getACHuffmanComponentMapping(int component);
        /**
         * Get the number of the quantization table that will be used for
         * a particular component.
         * <p>
         *  获取将用于特定组件的量化表的编号。
         * 
         * 
         * @param component The Component of interest.
         * @return The table number of the Quantization table for component.
         */
        public int getQTableComponentMapping(int component);

        /**
         * Returns true if the image information in the ParamBlock is
         * currently valid.  This indicates if image data was read from
         * the stream for decoding and weather image data should be
         * written when encoding.
         * <p>
         * 如果ParamBlock中的图像信息当前有效,则返回true。这指示是否从用于解码的流中读取图像数据,并且在编码时应当写入天气图像数据。
         * 
         */
        public boolean isImageInfoValid();

        /**
         * Returns true if the tables in the ParamBlock are currently
         * valid.  This indicates that tables were read from the stream
         * for decoding. When encoding this indicates wether tables should
         * be written to the stream.
         * <p>
         *  如果ParamBlock中的表当前有效,则返回true。这指示从用于解码的流中读取表。当编码时,这表明更多的表应该写入流。
         * 
         */
        public boolean isTableInfoValid();

        /**
         * Returns true if at least one instance of the marker is present
         * in the Parameter object.  For encoding returns true if there
         * is at least one instance of the marker to be written.
         * <p>
         *  如果参数对象中至少存在一个标记实例,则返回true。对于编码,如果至少有一个要写入的标记的实例,则返回true。
         * 
         * 
         * @param marker The marker of interest.
         */
        public boolean getMarker(int marker);

        /**
         * Returns a 'byte[][]' associated with the requested marker in
         * the parameter object.  Each entry in the 'byte[][]' is the data
         * associated with one instance of the marker (each marker can
         * theoretically appear any number of times in a stream).
         * <p>
         *  返回与参数对象中请求的标记关联的"byte [] []"。 'byte [] []'中的每个条目都是与标记的一个实例相关联的数据(每个标记在流中理论上可以出现任意次数)。
         * 
         * 
         * @param marker The marker of interest.
         * @return The 'byte[][]' for this marker or null if none
         *         available.
         */
        public byte[][] getMarkerData(int marker);

        /**
         * Returns the JPEG Encoded color id. This is generally
         * speaking only used if you are decoding into Rasters.  Note
         * that when decoding into a Raster no color conversion is
         * performed.
         * <p>
         *  返回JPEG编码颜色ID。这通常只用于如果你解码成光栅。注意,当解码成光栅时,不执行颜色转换。
         * 
         * 
         * @return The value of the JPEG encoded data's color id.
         */
    public int getEncodedColorID();

        /**
         * Returns the number of components for the current encoding
         * COLOR_ID.
         * <p>
         *  返回当前编码COLOR_ID的组件数。
         * 
         * 
         * @return the number of Components
         */
        public int getNumComponents();

        /**
         * Get the MCUs per restart marker.
         * <p>
         *  获取每个重新启动标记的MCU。
         * 
         * 
         * @return The number of MCUs between restart markers.
         */
        public int getRestartInterval();

        /**
         * Get the code for pixel size units This value is copied from the
         * APP0 marker. It isn't used by the JPEG codec.  If the APP0
         * marker wasn't present then you can not rely on this value.
         * <p>
         *  获取像素大小单位的代码此值从APP0标记复制。它不被JPEG编解码器使用。如果APP0标记不存在,那么你不能依赖这个值。
         * 
         * 
         * @return Value indicating the density unit one of the
         * DENSITY_UNIT_* constants.
         */
        public int getDensityUnit();

        /**
         * Get the horizontal pixel density This value is copied from the
         * APP0 marker. It isn't used by the JPEG code.  If the APP0
         * marker wasn't present then you can not rely on this value.
         * <p>
         *  获取水平像素密度此值从APP0标记复制。它不被JPEG代码使用。如果APP0标记不存在,那么你不能依赖这个值。
         * 
         * 
         * @return The horizontal pixel density, in units described by
         * @see #getDensityUnit()
         */
        public int getXDensity();
        /**
         * Get the vertical pixel density This value is copied into the
         * APP0 marker. It isn't used by the JPEG code. If the APP0 marker
         * wasn't present then you can not rely on this value.
         * <p>
         * 获取垂直像素密度此值被复制到APP0标记中。它不被JPEG代码使用。如果APP0标记不存在,那么你不能依赖这个值。
         * 
         * @return The verticle pixel density, in units described by
         * @see #getDensityUnit()
         */
        public int getYDensity();


}
