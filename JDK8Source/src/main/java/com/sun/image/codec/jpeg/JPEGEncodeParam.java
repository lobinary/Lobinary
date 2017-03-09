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
 * JPEGEncodeParam encapsulates tables and options necessary to
 * control encoding of JPEG data streams.  Parameters are either set
 * explicitly by the application for encoding, or read from another
 * JPEG header.<p>

 * When working with BufferedImages, the codec will attempt to match
 * the encoded JPEG COLOR_ID with the ColorModel in the BufferedImage.
 * This is not always possible (the default mappings are listed
 * below).  In cases where unsupported conversions are required (or
 * odd image colorspaces are in use) the user must either convert the
 * image data to a known ColorSpace or encode the data from a raster.
 * When encoding rasters no colorspace adjustments are made, so the
 * user must do any conversions required to get to the encoded
 * COLOR_ID.

 * The COLOR_ID for the encoded images is used to control the JPEG
 * codec's inital values for Huffman and Quantization Tables as well
 * as subsampling factors. It is also used to determine what color
 * conversion should be performed to obtain the best encoding.<p>

 * Note: The color ids described herein are simply enumerated values
 * that influence data processing by the JPEG codec.  JPEG compression
 * is, by definition, color blind.  These values are used as hints when
 * compressing JPEG data.  Through these values the JPEG codec can
 * perform some default rotation of data into spaces that will aid in
 * getting better compression ratios.<P>

 * Example behaviour is described below.  Since these mappings are
 * likely to change in the future it is strongly recommended that you
 * <p>
 *  JPEGEncodeParam封装控制JPEG数据流编码所需的表和选项。参数由应用程序显式设置,用于编码,或从另一个JPEG头中读取。<p>
 * 
 *  当使用BufferedImages时,编解码器将尝试将编码的JPEG COLOR_ID与BufferedImage中的ColorModel匹配。这不总是可能的(默认映射如下所示)。
 * 如果需要不支持的转换(或使用奇数图像颜色空间),用户必须将图像数据转换为已知的ColorSpace或对来自栅格的数据进行编码。
 * 当编码光栅时,不进行颜色空间调整,因此用户必须进行任何转换才能获得编码的COLOR_ID。
 * 
 * 编码图像的COLOR_ID用于控制用于Huffman和量化表的JPEG编解码器的初始值以及子采样因子。它也用于确定应该执行什么颜色转换以获得最佳编码
 * 
 *  注意：这里描述的颜色id是简单地枚举的值,其影响JPEG编解码器的数据处理。 JPEG压缩,根据定义,色盲。这些值在压缩JPEG数据时用作提示。
 * 通过这些值,JPEG编解码器可以执行一些数据到空间的默认旋转,这将有助于获得更好的压缩比。
 * 
 *  示例行为描述如下。由于这些映射在未来可能会改变,强烈建议您
 * 
 * 
 * make use of the @see JPEGImageEncoder.getDefaultParamBlock calls
 * and check the encodedColorID for your particular BufferedImage.

 * In extreme cases is may be necessary for the user to convert the
 * image to the desired colorspace, and encode it from a Raster.  In
 * this case the API programmer must specify the colorID of the data
 * in the Raster and no color conversion will take place.
 <pre>
 ENCODING:

 BufferedImage Type/Instance        JPEG (Encoded) Color ID
 ========================       =======================
 TYPE_GRAY                      COLOR_ID_GRAYSCALE
 TYPE_RGB                       COLOR_ID_YCbCr
 TYPE_YCbCr                     COLOR_ID_YCbCr
 TYPE_YCbCr/CS_PYCC             COLOR_ID_PYCC
 TYPE_CMYK                      COLOR_ID_CMYK
 TYPE_RGB   (w/ alpha)          COLOR_ID_YCbCrA
 TYPE_YCbCr (w/ alpha)          COLOR_ID_YCbCrA
 TYPE_YCbCr/CS_PYCC (w/ alpha)  COLOR_ID_PYCCA
 ** Any Other **                COLOR_ID_UNKNOWN
 </pre>

 * When the user wants more control than the BufferedImage conversions
 * provide, the user must encode the data from a Raster. In this case
 * the data undergoes no color conversion at all. It is the user's
 * responsiblity to perform the desired conversions.<P>

 * If you intend to write a JFIF image (by including the APP0_MARKER)
 * the encoded COLOR_ID must be one of: COLOR_ID_UNKNOWN,
 * COLOR_ID_GRAYSCALE, COLOR_ID_YCbCr, or COLOR_ID_CMYK. In all other
 * instances an ImageformatException will be thrown.<P>

 * <B>IMPORTANT:</B> an Alpha RGB BufferedImage will not map to a
 * valid JFIF stream, you must strip off the alpha prior to encoding
 * if you want a JFIF file.  If the APP0 marker is set and you do not
 * strip off the Alpha, an ImageFormatException will be thrown.
 * <p>
 * Note that the classes in the com.sun.image.codec.jpeg package are not
 * part of the core Java APIs.  They are a part of Sun's JDK and JRE
 * distributions.  Although other licensees may choose to distribute these
 * classes, developers cannot depend on their availability in non-Sun
 * implementations.  We expect that equivalent functionality will eventually
 * be available in a core API or standard extension.
 * <p>
 */

public interface JPEGEncodeParam
        extends Cloneable, JPEGDecodeParam
{
        public Object clone();

        /**
         * Set the horizontal subsample factor for the given component.
         * Note that the subsample factor is the number of input pixels
         * that contribute to each output pixel (ussually 2 for YCC).
         * <p>
         *  设置给定组件的水平子样本因子。注意,子采样因子是对每个输出像素有贡献的输入像素的数目(对于YCC通常为2)。
         * 
         * 
         * @param component The component being specified.
         * @param subsample The subsampling factor being specified.
         */
        public void setHorizontalSubsampling(int component,
                                                                                 int subsample);

        /**
         * Set the vertical subsample factor for the given component.  Note that
         * the subsample factor is the number of input pixels that
         * contribute to each output pixel (ussually 2 for YCC).
         * <p>
         *  设置给定组件的垂直子采样因子。注意,子采样因子是对每个输出像素有贡献的输入像素的数目(对于YCC通常为2)。
         * 
         * 
         * @param component The component being specified.
         * @param subsample The subsampling factor being specified.
         */
        public void setVerticalSubsampling(int component,
                                                                           int subsample);

        /**
         * Sets the coefficient quantization tables at index
         * passed. tableNum must range in value from 0 - 3.
         * <p>
         *  设置索引传递时的系数量化表。 tableNum的值必须介于0  -  3之间。
         * 
         * 
         * @param qtable that will be used.
         * @param tableNum the index of the table to be set.
         */
        public void     setQTable( int tableNum, JPEGQTable qTable );

        /** Sets the DC Huffman coding table at index to the table provided.
        /* <p>
        /* 
         * @param huffTable JPEGHuffmanTable that will be assigned
         * to index tableNum.
         * @param tableNum - the index of the table to be set.
         * @exception IllegalArgumentException - thrown if the tableNum
         * is out of range.  Index must range in value from 0 - 3.
         */
        public void     setDCHuffmanTable( int tableNum,
                                                                   JPEGHuffmanTable huffTable);

        /** Sets the AC Huffman coding table at index to the table provided.
        /* <p>
        /* 
         * @param huffTable JPEGHuffmanTable that will be assigned
         * to index tableNum.
         * @param tableNum - the index of the table to be set.
         * @exception IllegalArgumentException - thrown if the tableNum
         * is out of range.  Index must range in value from 0 - 3.
         */
        public void     setACHuffmanTable( int tableNum,
                                                                   JPEGHuffmanTable huffTable);


        /**
         * Sets the mapping between a component and it's DC Huffman Table.
         * <p>
         *  设置组件与其DC Huffman表之间的映射。
         * 
         * 
         * @param component The component to set the mapping for
         * @param table The DC Huffman table to use for component
         */
        public void setDCHuffmanComponentMapping( int component, int table);
        /**
         * Sets the mapping between a component and it's AC Huffman Table.
         * <p>
         *  设置组件和它的AC Huffman表之间的映射。
         * 
         * 
         * @param component The component to set the mapping for
         * @param table The AC Huffman table to use for component
         */
        public void setACHuffmanComponentMapping( int component, int table);
        /**
         * Sets the mapping between a component and it's Quantization Table.
         * <p>
         *  设置组件与其量化表之间的映射。
         * 
         * 
         * @param component The component to set the mapping for
         * @param table The Quantization Table to use for component
         */
        public void setQTableComponentMapping( int component, int table);

        /**
         * Set the flag indicating the validity of the table information
         * in the ParamBlock.  This is used to indicate if tables should
         * be included when encoding.
         * <p>
         * 设置指示ParamBlock中的表信息的有效性的标志。这用于指示编码时是否应包含表。
         * 
         */
        public void setImageInfoValid(boolean flag);

        /**
         * Set the flag indicating the validity of the image information
         * in the ParamBlock.  This is used to indicates if image data
         * should be written when encoding.
         * <p>
         *  在ParamBlock中设置指示图像信息的有效性的标志。这用于指示在编码时是否应写入图像数据。
         * 
         */
        public void setTableInfoValid(boolean flag);

        /**
         * Sets the marker data to be written to the output data stream.
         * This removes any existing marker data in the JPEParm object.
         * This can be used to remove the default APP0 marker by calling
         * it with data set to null.
         * <p>
         *  设置要写入输出数据流的标记数据。这将删除JPEParm对象中的任何现有标记数据。这可以用于删除默认APP0标记,通过调用它的数据设置为null。
         * 
         * 
         * @param marker The marker to set the data for.
         * @param data the new set of data to be written.
         */
        public void setMarkerData(int marker, byte[][] data);

        /**
         * Appends 'data' to the array of byte[] associated with
         * marker. This will result in additional instance of the marker
         * being written (one for each byte[] in the array.).
         * <p>
         *  将数据追加到与标记相关联的byte []数组。这将导致写入标记的附加实例(对于数组中的每个字节[]一个)。
         * 
         * 
         * @param marker The marker to add and instance of.
         * @param data the data to be written.
         */
        public void addMarkerData(int marker, byte []data);

        /**
         * Set the MCUs per restart, or 0 for no restart markers.
         * <p>
         *  设置每次重新启动的MCU,或0表示无重新启动标记。
         * 
         * 
         * @param restartInterval number MCUs per restart marker.
         */
        public void setRestartInterval( int restartInterval );


        /**
         * Set the pixel size units This value is copied into the APP0
         * marker (if that marker is written). This value isn't used by
         * the JPEG code.
         * <p>
         *  设置像素大小单位此值被复制到APP0标记(如果写入了该标记)。 JPEG值不使用此值。
         * 
         * 
         * @param unit One of the DENSITY_UNIT_* values.
         */
        public void setDensityUnit( int unit);
        /**
         * Set the horizontal pixel density This value is written into the
         * APP0 marker. It isn't used by the JPEG code.
         * <p>
         *  设置水平像素密度该值写入APP0标记。它不被JPEG代码使用。
         * 
         * 
         * @param density the horizontal pixel density, in units
         * described by @see JPEGParam.getDensityUnit.
         */
        public void setXDensity( int density );
        /**
         * Set the vertical pixel density.  This value is copied into
         * the JFIF APP0 marker. It isn't used by the JPEG code.
         * <p>
         *  设置垂直像素密度。此值将复制到JFIF APP0标记中。它不被JPEG代码使用。
         * 
         * 
         * @param density The verticle pixel density, in units
         * described by @see JPEGParam.getDensityUnit.
         */
        public void setYDensity( int density );

        /**
         * This creates new Quantization tables that replace the currently
         * installed Quantization tables.  It also updates the Component
         * QTable mapping to the default for the current encoded COLOR_ID.

         * The Created Quantization table varies from very high
         * compression, very low quality, (0.0) to low compression, very
         * high quality (1.0) based on the quality parameter.<P>

         * At a quality level of 1.0 the table will be all 1's which will
         * lead to no loss of data due to quantization (however chrominace
         * subsampling, if used, and roundoff error in the DCT will still
         * degrade the image some what).<P>

         * This is a linear manipulation of the standard Chrominance
         * Q-Table.<P>

         * <pre>Some guidelines: 0.75 high quality
         *                 0.5  medium quality
         *                 0.25 low quality
         * </pre>
         * <p>
         *  这将创建新的量化表,替换当前安装的量化表。它还将组件QTable映射更新为当前编码的COLOR_ID的默认值。
         * 
         *  创建量化表基于质量参数从非常高的压缩,非常低的质量(0.0)到低压缩,非常高的质量(1.0)变化。
         * 
         * 在质量水平为1.0时,表将全部为1,这将导致由于量化(但是如果使用色度子采样,并且DCT中的舍入误差将仍然使图像劣化一些什么),则不会丢失数据。
         * 
         * @param quality 0.0-1.0 setting of desired quality level.
         * @param forceBaseline force baseline quantization table
         */
        public void setQuality(float quality, boolean forceBaseline );
}
