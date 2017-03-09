/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.plugins.bmp;

import java.util.Locale;
import javax.imageio.ImageWriteParam;

import com.sun.imageio.plugins.bmp.BMPConstants;
import com.sun.imageio.plugins.bmp.BMPCompressionTypes;

/**
 * A subclass of <code>ImageWriteParam</code> for encoding images in
 * the BMP format.
 *
 * <p> This class allows for the specification of various parameters
 * while writing a BMP format image file.  By default, the data layout
 * is bottom-up, such that the pixels are stored in bottom-up order,
 * the first scanline being stored last.
 *
 * <p>The particular compression scheme to be used can be specified by using
 * the <code>setCompressionType()</code> method with the appropriate type
 * string.  The compression scheme specified will be honored if and only if it
 * is compatible with the type of image being written. If the specified
 * compression scheme is not compatible with the type of image being written
 * then the <code>IOException</code> will be thrown by the BMP image writer.
 * If the compression type is not set explicitly then <code>getCompressionType()</code>
 * will return <code>null</code>. In this case the BMP image writer will select
 * a compression type that supports encoding of the given image without loss
 * of the color resolution.
 * <p>The compression type strings and the image type(s) each supports are
 * listed in the following
 * table:
 *
 * <p><table border=1>
 * <caption><b>Compression Types</b></caption>
 * <tr><th>Type String</th> <th>Description</th>  <th>Image Types</th></tr>
 * <tr><td>BI_RGB</td>  <td>Uncompressed RLE</td> <td>{@literal <= } 8-bits/sample</td></tr>
 * <tr><td>BI_RLE8</td> <td>8-bit Run Length Encoding</td> <td>{@literal <=} 8-bits/sample</td></tr>
 * <tr><td>BI_RLE4</td> <td>4-bit Run Length Encoding</td> <td>{@literal <=} 4-bits/sample</td></tr>
 * <tr><td>BI_BITFIELDS</td> <td>Packed data</td> <td> 16 or 32 bits/sample</td></tr>
 * </table>
 * <p>
 *  用于以BMP格式编码图像的<code> ImageWriteParam </code>的子类。
 * 
 *  <p>此类允许在编写BMP格式图像文件时指定各种参数。默认情况下,数据布局是自底向上的,使得像素以自下而上的顺序存储,第一扫描线被存储在最后。
 * 
 *  <p>要使用的特定压缩方案可以通过使用具有适当类型字符串的<code> setCompressionType()</code>方法来指定。
 * 当且仅当所指定的压缩方案与正在写入的图像的类型兼容时,才会遵守指定的压缩方案。
 * 如果指定的压缩方案与正在写入的映像的类型不兼容,那么BMP图像写入器将抛出<code> IOException </code>。
 * 如果压缩类型没有明确设置,那么<code> getCompressionType()</code>将返回<code> null </code>。
 * 在这种情况下,BMP图像写入器将选择支持给定图像的编码而不损失颜色分辨率的压缩类型。 <p>每个支持的压缩类型字符串和图像类型在下表中列出：。
 * 
 * <p> <table border = 1> <caption> <b>压缩类型</b> </caption> <tr> <th>类型字符串</th> <th>描述</th> <th> </tr> <td>
 *  BI_RGB </td> <td>未压缩的RLE </td> <td> {@ literal <=} 8位/示例</td> </tr> <tr> <td> BI_RLE8 </td> <td> 8位运
 * 行长度编码</td> <td> {@ literal <=} 8位/ sample </td> </tr> <tr> td> BI_RLE4 </td> <td> 4位运行长度编码</td> <td> 
 * {@ literal <=} 4位/样本</td> </tr> <tr> <td> BI_BITFIELDS < / td> <td>打包数据</td> <td> 16或32位/样本</td> </tr>
 */
public class BMPImageWriteParam extends ImageWriteParam {

    private boolean topDown = false;

    /**
     * Constructs a <code>BMPImageWriteParam</code> set to use a given
     * <code>Locale</code> and with default values for all parameters.
     *
     * <p>
     * 。
     * </table>
     * 
     * @param locale a <code>Locale</code> to be used to localize
     * compression type names and quality descriptions, or
     * <code>null</code>.
     */
    public BMPImageWriteParam(Locale locale) {
        super(locale);

        // Set compression types ("BI_RGB" denotes uncompressed).
        compressionTypes = BMPCompressionTypes.getCompressionTypes();

        // Set compression flag.
        canWriteCompressed = true;
        compressionMode = MODE_COPY_FROM_METADATA;
        compressionType = compressionTypes[BMPConstants.BI_RGB];
    }

    /**
     * Constructs an <code>BMPImageWriteParam</code> object with default
     * values for all parameters and a <code>null</code> <code>Locale</code>.
     * <p>
     *  构造一个<code> BMPImageWriteParam </code>设置为使用给定的<code> Locale </code>,并为所有参数使用默认值。
     * 
     */
    public BMPImageWriteParam() {
        this(null);
    }

    /**
     * If set, the data will be written out in a top-down manner, the first
     * scanline being written first.
     *
     * <p>
     *  构造具有所有参数的默认值和<code> null </code> <code> Locale </code>的<code> BMPImageWriteParam </code>对象。
     * 
     * 
     * @param topDown whether the data are written in top-down order.
     */
    public void setTopDown(boolean topDown) {
        this.topDown = topDown;
    }

    /**
     * Returns the value of the <code>topDown</code> parameter.
     * The default is <code>false</code>.
     *
     * <p>
     *  如果设置,数据将以自上而下的方式写出,第一扫描线被首先写入。
     * 
     * 
     * @return whether the data are written in top-down order.
     */
    public boolean isTopDown() {
        return topDown;
    }
}
