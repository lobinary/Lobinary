/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.plugins.jpeg;

import javax.imageio.ImageReadParam;

/**
 * This class adds the ability to set JPEG quantization and Huffman
 * tables when using the built-in JPEG reader plug-in.  An instance of
 * this class will be returned from the
 * <code>getDefaultImageReadParam</code> methods of the built-in JPEG
 * <code>ImageReader</code>.
 *
 * <p> The sole purpose of these additions is to allow the
 * specification of tables for use in decoding abbreviated streams.
 * The built-in JPEG reader will also accept an ordinary
 * <code>ImageReadParam</code>, which is sufficient for decoding
 * non-abbreviated streams.
 *
 * <p> While tables for abbreviated streams are often obtained by
 * first reading another abbreviated stream containing only the
 * tables, in some applications the tables are fixed ahead of time.
 * This class allows the tables to be specified directly from client
 * code.  If no tables are specified either in the stream or in a
 * <code>JPEGImageReadParam</code>, then the stream is presumed to use
 * the "standard" visually lossless tables.  See {@link JPEGQTable JPEGQTable}
 * and {@link JPEGHuffmanTable JPEGHuffmanTable} for more information
 *  on the default tables.
 *
 * <p> The default <code>JPEGImageReadParam</code> returned by the
 * <code>getDefaultReadParam</code> method of the builtin JPEG reader
 * contains no tables.  Default tables may be obtained from the table
 * classes {@link JPEGQTable JPEGQTable} and
 * {@link JPEGHuffmanTable JPEGHuffmanTable}.
 *
 * <p> If a stream does contain tables, the tables given in a
 * <code>JPEGImageReadParam</code> are ignored.  Furthermore, if the
 * first image in a stream does contain tables and subsequent ones do
 * not, then the tables given in the first image are used for all the
 * abbreviated images.  Once tables have been read from a stream, they
 * can be overridden only by tables subsequently read from the same
 * stream.  In order to specify new tables, the {@link
 * javax.imageio.ImageReader#setInput setInput} method of
 * the reader must be called to change the stream.
 *
 * <p> Note that this class does not provide a means for obtaining the
 * tables found in a stream.  These may be extracted from a stream by
 * consulting the IIOMetadata object returned by the reader.
 *
 * <p>
 * For more information about the operation of the built-in JPEG plug-ins,
 * see the <A HREF="../../metadata/doc-files/jpeg_metadata.html">JPEG
 * metadata format specification and usage notes</A>.
 *
 * <p>
 *  此类增加了在使用内置JPEG读取器插件时设置JPEG量化和霍夫曼表的能力。
 * 此类的实例将从内置的JPEG <code> ImageReader </code>的<code> getDefaultImageReadParam </code>方法中返回。
 * 
 *  这些添加的唯一目的是允许用于解码缩略流的表的规范。内置的JPEG读取器还将接受普通的<code> ImageReadParam </code>,这足以解码非缩写流。
 * 
 *  尽管通常通过首先读取仅包含表的另一缩略流来获得缩略流的表,但在一些应用中,这些表是提前固定的。这个类允许直接从客户端代码指定表。
 * 如果在流中或在<code> JPEGImageReadParam </code>中未指定表,则流被假定为使用"标准"视觉无损表。
 * 有关默认表的更多信息,请参阅{@link JPEGQTable JPEGQTable}和{@link JPEGHuffmanTable JPEGHuffmanTable}。
 * 
 * <p>内置JPEG阅读器的<code> getDefaultReadParam </code>方法返回的默认<code> JPEGImageReadParam </code>不包含表。
 * 默认表可以从表类{@link JPEGQTable JPEGQTable}和{@link JPEGHuffmanTable JPEGHuffmanTable}中获取。
 * 
 *  <p>如果流包含表,则会忽略<code> JPEGImageReadParam </code>中给出的表。
 * 此外,如果流中的第一图像包含表,并且后续的图像不包含表,则在第一图像中给出的表用于所有缩略图像。一旦从流中读取了表,它们只能由随后从同一流读取的表来重写。
 * 为了指定新表,必须调用阅读器的{@link javax.imageio.ImageReader#setInput setInput}方法来更改流。
 * 
 *  <p>请注意,此类不提供获取流中找到的表的方法。这些可以通过查阅由读取器返回的IIOMetadata对象从流中提取。
 * 
 * <p>
 *  有关内置JPEG插件操作的详情,请参阅<A HREF="../../metadata/doc-files/jpeg_metadata.html"> JPEG元数据格式规范和使用说明</A >。
 * 
 */
public class JPEGImageReadParam extends ImageReadParam {

    private JPEGQTable[] qTables = null;
    private JPEGHuffmanTable[] DCHuffmanTables = null;
    private JPEGHuffmanTable[] ACHuffmanTables = null;

    /**
     * Constructs a <code>JPEGImageReadParam</code>.
     * <p>
     *  构造一个<code> JPEGImageReadParam </code>。
     * 
     */
    public JPEGImageReadParam() {
        super();
    }

    /**
     * Returns <code>true</code> if tables are currently set.
     *
     * <p>
     *  如果当前设置了表,则返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if tables are present.
     */
    public boolean areTablesSet() {
        return (qTables != null);
    }

    /**
     * Sets the quantization and Huffman tables to use in decoding
     * abbreviated streams.  There may be a maximum of 4 tables of
     * each type.  These tables are ignored once tables are
     * encountered in the stream.  All arguments must be
     * non-<code>null</code>.  The two arrays of Huffman tables must
     * have the same number of elements.  The table specifiers in the
     * frame and scan headers in the stream are assumed to be
     * equivalent to indices into these arrays.  The argument arrays
     * are copied by this method.
     *
     * <p>
     * 设置用于解码缩略流的量化和霍夫曼表。每种类型最多可以有4个表。一旦在流中遇到表,则忽略这些表。所有参数必须为非<code> null </code>。两个霍夫曼表的数组必须具有相同数量的元素。
     * 假定流中的帧和扫描头中的表指定符等价于这些数组中的索引。参数数组由此方法复制。
     * 
     * 
     * @param qTables an array of quantization table objects.
     * @param DCHuffmanTables an array of Huffman table objects.
     * @param ACHuffmanTables an array of Huffman table objects.
     *
     * @exception IllegalArgumentException if any of the arguments
     * is <code>null</code>, has more than 4 elements, or if the
     * numbers of DC and AC tables differ.
     *
     * @see #unsetDecodeTables
     */
    public void setDecodeTables(JPEGQTable[] qTables,
                                JPEGHuffmanTable[] DCHuffmanTables,
                                JPEGHuffmanTable[] ACHuffmanTables) {
        if ((qTables == null) ||
            (DCHuffmanTables == null) ||
            (ACHuffmanTables == null) ||
            (qTables.length > 4) ||
            (DCHuffmanTables.length > 4) ||
            (ACHuffmanTables.length > 4) ||
            (DCHuffmanTables.length != ACHuffmanTables.length)) {
                throw new IllegalArgumentException
                    ("Invalid JPEG table arrays");
        }
        this.qTables = (JPEGQTable[])qTables.clone();
        this.DCHuffmanTables = (JPEGHuffmanTable[])DCHuffmanTables.clone();
        this.ACHuffmanTables = (JPEGHuffmanTable[])ACHuffmanTables.clone();
    }

    /**
     * Removes any quantization and Huffman tables that are currently
     * set.
     *
     * <p>
     *  删除当前设置的任何量化和霍夫曼表。
     * 
     * 
     * @see #setDecodeTables
     */
    public void unsetDecodeTables() {
        this.qTables = null;
        this.DCHuffmanTables = null;
        this.ACHuffmanTables = null;
    }

    /**
     * Returns a copy of the array of quantization tables set on the
     * most recent call to <code>setDecodeTables</code>, or
     * <code>null</code> if tables are not currently set.
     *
     * <p>
     *  返回在最近一次调用<code> setDecodeTables </code>时设置的量化表数组的副本,如果当前未设置表,则返回<code> null </code>。
     * 
     * 
     * @return an array of <code>JPEGQTable</code> objects, or
     * <code>null</code>.
     *
     * @see #setDecodeTables
     */
    public JPEGQTable[] getQTables() {
        return (qTables != null) ? (JPEGQTable[])qTables.clone() : null;
    }

    /**
     * Returns a copy of the array of DC Huffman tables set on the
     * most recent call to <code>setDecodeTables</code>, or
     * <code>null</code> if tables are not currently set.
     *
     * <p>
     *  返回在最近一次调用<code> setDecodeTables </code>时设置的DC Huffman表数组的副本,如果当前未设置表,则返回<code> null </code>。
     * 
     * 
     * @return an array of <code>JPEGHuffmanTable</code> objects, or
     * <code>null</code>.
     *
     * @see #setDecodeTables
     */
    public JPEGHuffmanTable[] getDCHuffmanTables() {
        return (DCHuffmanTables != null)
            ? (JPEGHuffmanTable[])DCHuffmanTables.clone()
            : null;
    }

    /**
     * Returns a copy of the array of AC Huffman tables set on the
     * most recent call to <code>setDecodeTables</code>, or
     * <code>null</code> if tables are not currently set.
     *
     * <p>
     *  返回在最近一次调用<code> setDecodeTables </code>时设置的AC Huffman表数组的副本,如果当前未设置表,则返回<code> null </code>。
     * 
     * @return an array of <code>JPEGHuffmanTable</code> objects, or
     * <code>null</code>.
     *
     * @see #setDecodeTables
     */
    public JPEGHuffmanTable[] getACHuffmanTables() {
        return (ACHuffmanTables != null)
            ? (JPEGHuffmanTable[])ACHuffmanTables.clone()
            : null;
    }
}
