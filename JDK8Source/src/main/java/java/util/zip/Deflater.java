/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

/**
 * This class provides support for general purpose compression using the
 * popular ZLIB compression library. The ZLIB compression library was
 * initially developed as part of the PNG graphics standard and is not
 * protected by patents. It is fully described in the specifications at
 * the <a href="package-summary.html#package_description">java.util.zip
 * package description</a>.
 *
 * <p>The following code fragment demonstrates a trivial compression
 * and decompression of a string using <tt>Deflater</tt> and
 * <tt>Inflater</tt>.
 *
 * <blockquote><pre>
 * try {
 *     // Encode a String into bytes
 *     String inputString = "blahblahblah";
 *     byte[] input = inputString.getBytes("UTF-8");
 *
 *     // Compress the bytes
 *     byte[] output = new byte[100];
 *     Deflater compresser = new Deflater();
 *     compresser.setInput(input);
 *     compresser.finish();
 *     int compressedDataLength = compresser.deflate(output);
 *     compresser.end();
 *
 *     // Decompress the bytes
 *     Inflater decompresser = new Inflater();
 *     decompresser.setInput(output, 0, compressedDataLength);
 *     byte[] result = new byte[100];
 *     int resultLength = decompresser.inflate(result);
 *     decompresser.end();
 *
 *     // Decode the bytes into a String
 *     String outputString = new String(result, 0, resultLength, "UTF-8");
 * } catch(java.io.UnsupportedEncodingException ex) {
 *     // handle
 * } catch (java.util.zip.DataFormatException ex) {
 *     // handle
 * }
 * </pre></blockquote>
 *
 * <p>
 *  此类提供对使用常用ZLIB压缩库的通用压缩的支持。 ZLIB压缩库最初是作为PNG图形标准的一部分开发的,不受专利保护。
 * 在<a href="package-summary.html#package_description"> java.util.zip软件包描述</a>的规范中对此进行了完整的说明。
 * 
 *  <p>以下代码片段演示了使用<tt> Deflater </tt>和<tt> Inflater </tt>对字符串进行简单压缩和解压缩。
 * 
 *  <blockquote> <pre> try {//将字符串编码为字节String inputString ="blahblahblah"; byte [] input = inputString.getBytes("UTF-8");。
 * 
 *  //压缩字节byte [] output = new byte [100]; Deflater compresser = new Deflater(); compresser.setInput(inp
 * ut); compresser.finish(); int compressedDataLength = compresser.deflate(output); compresser.end();。
 * 
 *  //解压缩字节Inflater decompresser = new Inflater(); decompresser.setInput(output,0,compressedDataLength);
 *  byte [] result = new byte [100]; int resultLength = decompresser.inflate(result); decompresser.end()
 * ;。
 * 
 *  //将字节解码为字符串String outputString = new String(result,0,resultLength,"UTF-8"); } catch(java.io.Unsuppor
 * tedEncodingException ex){// handle} catch(java.util.zip.DataFormatException ex){// handle} </pre> </blockquote>
 * 。
 * 
 * 
 * @see         Inflater
 * @author      David Connelly
 */
public
class Deflater {

    private final ZStreamRef zsRef;
    private byte[] buf = new byte[0];
    private int off, len;
    private int level, strategy;
    private boolean setParams;
    private boolean finish, finished;
    private long bytesRead;
    private long bytesWritten;

    /**
     * Compression method for the deflate algorithm (the only one currently
     * supported).
     * <p>
     * 压缩算法的压缩方法(目前唯一支持的算法)。
     * 
     */
    public static final int DEFLATED = 8;

    /**
     * Compression level for no compression.
     * <p>
     *  无压缩的压缩级别。
     * 
     */
    public static final int NO_COMPRESSION = 0;

    /**
     * Compression level for fastest compression.
     * <p>
     *  压缩级别,用于最快压缩。
     * 
     */
    public static final int BEST_SPEED = 1;

    /**
     * Compression level for best compression.
     * <p>
     *  最佳压缩的压缩级别。
     * 
     */
    public static final int BEST_COMPRESSION = 9;

    /**
     * Default compression level.
     * <p>
     *  默认压缩级别。
     * 
     */
    public static final int DEFAULT_COMPRESSION = -1;

    /**
     * Compression strategy best used for data consisting mostly of small
     * values with a somewhat random distribution. Forces more Huffman coding
     * and less string matching.
     * <p>
     *  压缩策略最适用于主要由具有稍微随机分布的小值组成的数据。强制更多的霍夫曼编码和更少的字符串匹配。
     * 
     */
    public static final int FILTERED = 1;

    /**
     * Compression strategy for Huffman coding only.
     * <p>
     *  仅用于Huffman编码的压缩策略。
     * 
     */
    public static final int HUFFMAN_ONLY = 2;

    /**
     * Default compression strategy.
     * <p>
     *  默认压缩策略。
     * 
     */
    public static final int DEFAULT_STRATEGY = 0;

    /**
     * Compression flush mode used to achieve best compression result.
     *
     * <p>
     *  压缩冲洗模式用于实现最佳压缩结果。
     * 
     * 
     * @see Deflater#deflate(byte[], int, int, int)
     * @since 1.7
     */
    public static final int NO_FLUSH = 0;

    /**
     * Compression flush mode used to flush out all pending output; may
     * degrade compression for some compression algorithms.
     *
     * <p>
     *  压缩刷新模式用于刷新所有待处理的输出;可能对于一些压缩算法降级压缩。
     * 
     * 
     * @see Deflater#deflate(byte[], int, int, int)
     * @since 1.7
     */
    public static final int SYNC_FLUSH = 2;

    /**
     * Compression flush mode used to flush out all pending output and
     * reset the deflater. Using this mode too often can seriously degrade
     * compression.
     *
     * <p>
     *  压缩冲洗模式用于冲洗所有待定输出并复位除尘器。使用此模式太频繁可以严重降低压缩。
     * 
     * 
     * @see Deflater#deflate(byte[], int, int, int)
     * @since 1.7
     */
    public static final int FULL_FLUSH = 3;

    static {
        /* Zip library is loaded from System.initializeSystemClass */
        initIDs();
    }

    /**
     * Creates a new compressor using the specified compression level.
     * If 'nowrap' is true then the ZLIB header and checksum fields will
     * not be used in order to support the compression format used in
     * both GZIP and PKZIP.
     * <p>
     *  使用指定的压缩级别创建新的压缩程序。如果'nowrap'为true,则不会使用ZLIB头和校验和字段,以便支持在GZIP和PKZIP中使用的压缩格式。
     * 
     * 
     * @param level the compression level (0-9)
     * @param nowrap if true then use GZIP compatible compression
     */
    public Deflater(int level, boolean nowrap) {
        this.level = level;
        this.strategy = DEFAULT_STRATEGY;
        this.zsRef = new ZStreamRef(init(level, DEFAULT_STRATEGY, nowrap));
    }

    /**
     * Creates a new compressor using the specified compression level.
     * Compressed data will be generated in ZLIB format.
     * <p>
     *  使用指定的压缩级别创建新的压缩程序。压缩数据将以ZLIB格式生成。
     * 
     * 
     * @param level the compression level (0-9)
     */
    public Deflater(int level) {
        this(level, false);
    }

    /**
     * Creates a new compressor with the default compression level.
     * Compressed data will be generated in ZLIB format.
     * <p>
     *  创建具有默认压缩级别的新压缩器。压缩数据将以ZLIB格式生成。
     * 
     */
    public Deflater() {
        this(DEFAULT_COMPRESSION, false);
    }

    /**
     * Sets input data for compression. This should be called whenever
     * needsInput() returns true indicating that more input data is required.
     * <p>
     *  设置压缩的输入数据。当needsInput()返回true表示需要更多输入数据时,应调用此方法。
     * 
     * 
     * @param b the input data bytes
     * @param off the start offset of the data
     * @param len the length of the data
     * @see Deflater#needsInput
     */
    public void setInput(byte[] b, int off, int len) {
        if (b== null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > b.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        synchronized (zsRef) {
            this.buf = b;
            this.off = off;
            this.len = len;
        }
    }

    /**
     * Sets input data for compression. This should be called whenever
     * needsInput() returns true indicating that more input data is required.
     * <p>
     * 设置压缩的输入数据。当needsInput()返回true表示需要更多输入数据时,应调用此方法。
     * 
     * 
     * @param b the input data bytes
     * @see Deflater#needsInput
     */
    public void setInput(byte[] b) {
        setInput(b, 0, b.length);
    }

    /**
     * Sets preset dictionary for compression. A preset dictionary is used
     * when the history buffer can be predetermined. When the data is later
     * uncompressed with Inflater.inflate(), Inflater.getAdler() can be called
     * in order to get the Adler-32 value of the dictionary required for
     * decompression.
     * <p>
     *  设置压缩的预设字典。当可以预定历史缓冲器时使用预设字典。
     * 当数据以后用Inflater.inflate()解压缩时,可以调用Inflater.getAdler()以获取解压缩所需的字典的Adler-32值。
     * 
     * 
     * @param b the dictionary data bytes
     * @param off the start offset of the data
     * @param len the length of the data
     * @see Inflater#inflate
     * @see Inflater#getAdler
     */
    public void setDictionary(byte[] b, int off, int len) {
        if (b == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > b.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        synchronized (zsRef) {
            ensureOpen();
            setDictionary(zsRef.address(), b, off, len);
        }
    }

    /**
     * Sets preset dictionary for compression. A preset dictionary is used
     * when the history buffer can be predetermined. When the data is later
     * uncompressed with Inflater.inflate(), Inflater.getAdler() can be called
     * in order to get the Adler-32 value of the dictionary required for
     * decompression.
     * <p>
     *  设置压缩的预设字典。当可以预定历史缓冲器时使用预设字典。
     * 当数据以后用Inflater.inflate()解压缩时,可以调用Inflater.getAdler()以获取解压缩所需的字典的Adler-32值。
     * 
     * 
     * @param b the dictionary data bytes
     * @see Inflater#inflate
     * @see Inflater#getAdler
     */
    public void setDictionary(byte[] b) {
        setDictionary(b, 0, b.length);
    }

    /**
     * Sets the compression strategy to the specified value.
     *
     * <p> If the compression strategy is changed, the next invocation
     * of {@code deflate} will compress the input available so far with
     * the old strategy (and may be flushed); the new strategy will take
     * effect only after that invocation.
     *
     * <p>
     *  将压缩策略设置为指定的值。
     * 
     *  <p>如果压缩策略改变了,下一次调用{@code deflate}会压缩到目前为止可用的旧策略输入(可能被刷新)。新策略将仅在该调用后生效。
     * 
     * 
     * @param strategy the new compression strategy
     * @exception IllegalArgumentException if the compression strategy is
     *                                     invalid
     */
    public void setStrategy(int strategy) {
        switch (strategy) {
          case DEFAULT_STRATEGY:
          case FILTERED:
          case HUFFMAN_ONLY:
            break;
          default:
            throw new IllegalArgumentException();
        }
        synchronized (zsRef) {
            if (this.strategy != strategy) {
                this.strategy = strategy;
                setParams = true;
            }
        }
    }

    /**
     * Sets the compression level to the specified value.
     *
     * <p> If the compression level is changed, the next invocation
     * of {@code deflate} will compress the input available so far
     * with the old level (and may be flushed); the new level will
     * take effect only after that invocation.
     *
     * <p>
     *  将压缩级别设置为指定的值。
     * 
     *  <p>如果压缩级别改变,下一次调用{@code deflate}会压缩到目前为止可用的旧级别的输入(可能会被刷新)。新级别将仅在该调用后生效。
     * 
     * 
     * @param level the new compression level (0-9)
     * @exception IllegalArgumentException if the compression level is invalid
     */
    public void setLevel(int level) {
        if ((level < 0 || level > 9) && level != DEFAULT_COMPRESSION) {
            throw new IllegalArgumentException("invalid compression level");
        }
        synchronized (zsRef) {
            if (this.level != level) {
                this.level = level;
                setParams = true;
            }
        }
    }

    /**
     * Returns true if the input data buffer is empty and setInput()
     * should be called in order to provide more input.
     * <p>
     *  如果输入数据缓冲区为空,则返回true,并且应调用setInput()以提供更多输入。
     * 
     * 
     * @return true if the input data buffer is empty and setInput()
     * should be called in order to provide more input
     */
    public boolean needsInput() {
        return len <= 0;
    }

    /**
     * When called, indicates that compression should end with the current
     * contents of the input buffer.
     * <p>
     *  调用时,表示压缩应以输入缓冲区的当前内容结束。
     * 
     */
    public void finish() {
        synchronized (zsRef) {
            finish = true;
        }
    }

    /**
     * Returns true if the end of the compressed data output stream has
     * been reached.
     * <p>
     * 如果已达到压缩数据输出流的结尾,则返回true。
     * 
     * 
     * @return true if the end of the compressed data output stream has
     * been reached
     */
    public boolean finished() {
        synchronized (zsRef) {
            return finished;
        }
    }

    /**
     * Compresses the input data and fills specified buffer with compressed
     * data. Returns actual number of bytes of compressed data. A return value
     * of 0 indicates that {@link #needsInput() needsInput} should be called
     * in order to determine if more input data is required.
     *
     * <p>This method uses {@link #NO_FLUSH} as its compression flush mode.
     * An invocation of this method of the form {@code deflater.deflate(b, off, len)}
     * yields the same result as the invocation of
     * {@code deflater.deflate(b, off, len, Deflater.NO_FLUSH)}.
     *
     * <p>
     *  压缩输入数据并使用压缩数据填充指定的缓冲区。返回压缩数据的实际字节数。返回值为0表示应调用{@link #needsInput()needsInput},以确定是否需要更多输入数据。
     * 
     *  <p>此方法使用{@link #NO_FLUSH}作为其压缩冲洗模式。
     * 对{@code deflater.deflate(b,off,len)}形式的此方法的调用产生与调用{@code deflater.deflate(b,off,len,Deflater.NO_FLUSH)}
     * 相同的结果。
     *  <p>此方法使用{@link #NO_FLUSH}作为其压缩冲洗模式。
     * 
     * 
     * @param b the buffer for the compressed data
     * @param off the start offset of the data
     * @param len the maximum number of bytes of compressed data
     * @return the actual number of bytes of compressed data written to the
     *         output buffer
     */
    public int deflate(byte[] b, int off, int len) {
        return deflate(b, off, len, NO_FLUSH);
    }

    /**
     * Compresses the input data and fills specified buffer with compressed
     * data. Returns actual number of bytes of compressed data. A return value
     * of 0 indicates that {@link #needsInput() needsInput} should be called
     * in order to determine if more input data is required.
     *
     * <p>This method uses {@link #NO_FLUSH} as its compression flush mode.
     * An invocation of this method of the form {@code deflater.deflate(b)}
     * yields the same result as the invocation of
     * {@code deflater.deflate(b, 0, b.length, Deflater.NO_FLUSH)}.
     *
     * <p>
     *  压缩输入数据并使用压缩数据填充指定的缓冲区。返回压缩数据的实际字节数。返回值为0表示应调用{@link #needsInput()needsInput},以确定是否需要更多输入数据。
     * 
     *  <p>此方法使用{@link #NO_FLUSH}作为其压缩冲洗模式。
     * 对{@code deflater.deflate(b)}形式的此方法的调用产生与调用{@code deflater.deflate(b,0,b.length,Deflater.NO_FLUSH)}相同的
     * 结果。
     *  <p>此方法使用{@link #NO_FLUSH}作为其压缩冲洗模式。
     * 
     * 
     * @param b the buffer for the compressed data
     * @return the actual number of bytes of compressed data written to the
     *         output buffer
     */
    public int deflate(byte[] b) {
        return deflate(b, 0, b.length, NO_FLUSH);
    }

    /**
     * Compresses the input data and fills the specified buffer with compressed
     * data. Returns actual number of bytes of data compressed.
     *
     * <p>Compression flush mode is one of the following three modes:
     *
     * <ul>
     * <li>{@link #NO_FLUSH}: allows the deflater to decide how much data
     * to accumulate, before producing output, in order to achieve the best
     * compression (should be used in normal use scenario). A return value
     * of 0 in this flush mode indicates that {@link #needsInput()} should
     * be called in order to determine if more input data is required.
     *
     * <li>{@link #SYNC_FLUSH}: all pending output in the deflater is flushed,
     * to the specified output buffer, so that an inflater that works on
     * compressed data can get all input data available so far (In particular
     * the {@link #needsInput()} returns {@code true} after this invocation
     * if enough output space is provided). Flushing with {@link #SYNC_FLUSH}
     * may degrade compression for some compression algorithms and so it
     * should be used only when necessary.
     *
     * <li>{@link #FULL_FLUSH}: all pending output is flushed out as with
     * {@link #SYNC_FLUSH}. The compression state is reset so that the inflater
     * that works on the compressed output data can restart from this point
     * if previous compressed data has been damaged or if random access is
     * desired. Using {@link #FULL_FLUSH} too often can seriously degrade
     * compression.
     * </ul>
     *
     * <p>In the case of {@link #FULL_FLUSH} or {@link #SYNC_FLUSH}, if
     * the return value is {@code len}, the space available in output
     * buffer {@code b}, this method should be invoked again with the same
     * {@code flush} parameter and more output space.
     *
     * <p>
     *  压缩输入数据并用压缩数据填充指定的缓冲区。返回压缩的数据的实际字节数。
     * 
     *  <p>压缩冲洗模式是以下三种模式之一：
     * 
     * <ul>
     * <li> {@ link #NO_FLUSH}：允许deflater在生成输出之前决定累积多少数据,以便实现最佳压缩(应在正常使用情况下使用)。
     * 在此刷新模式下返回值为0表示应调用{@link #needsInput()},以确定是否需要更多输入数据。
     * 
     *  <li> {@ link #SYNC_FLUSH}：将deflater中的所有未决输出刷新到指定的输出缓冲区,以便对压缩数据进行处理的inflater可以获取所有可用的输入数据(特别是{@link#如果提供了足够的输出空间,needsInput()}
     * 将在此调用后返回{@code true}。
     * 使用{@link #SYNC_FLUSH}刷新可能会降低某些压缩算法的压缩率,因此只应在必要时使用。
     * 
     *  <li> {@ link #FULL_FLUSH}：所有待处理的输出都会像{@link #SYNC_FLUSH}一样刷新。
     * 重置压缩状态,使得如果先前的压缩数据已经损坏或者如果需要随机存取,则对压缩的输出数据工作的充气机可以从这一点重新开始。频繁使用{@link #FULL_FLUSH}可能会严重降低压缩率。
     * </ul>
     * 
     *  <p>在{@link #FULL_FLUSH}或{@link #SYNC_FLUSH}的情况下,如果返回值为{@code len},输出缓冲区{@code b}中的可用空间,则应重新调用此方法具有相同
     * 的{@code flush}参数和更多的输出空间。
     * 
     * 
     * @param b the buffer for the compressed data
     * @param off the start offset of the data
     * @param len the maximum number of bytes of compressed data
     * @param flush the compression flush mode
     * @return the actual number of bytes of compressed data written to
     *         the output buffer
     *
     * @throws IllegalArgumentException if the flush mode is invalid
     * @since 1.7
     */
    public int deflate(byte[] b, int off, int len, int flush) {
        if (b == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > b.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        synchronized (zsRef) {
            ensureOpen();
            if (flush == NO_FLUSH || flush == SYNC_FLUSH ||
                flush == FULL_FLUSH) {
                int thisLen = this.len;
                int n = deflateBytes(zsRef.address(), b, off, len, flush);
                bytesWritten += n;
                bytesRead += (thisLen - this.len);
                return n;
            }
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns the ADLER-32 value of the uncompressed data.
     * <p>
     *  返回未压缩数据的ADLER-32值。
     * 
     * 
     * @return the ADLER-32 value of the uncompressed data
     */
    public int getAdler() {
        synchronized (zsRef) {
            ensureOpen();
            return getAdler(zsRef.address());
        }
    }

    /**
     * Returns the total number of uncompressed bytes input so far.
     *
     * <p>Since the number of bytes may be greater than
     * Integer.MAX_VALUE, the {@link #getBytesRead()} method is now
     * the preferred means of obtaining this information.</p>
     *
     * <p>
     * 返回到目前为止输入的未压缩字节的总数。
     * 
     *  <p>由于字节数可能大于Integer.MAX_VALUE,因此{@link #getBytesRead()}方法是获取此信息的首选方法。</p>
     * 
     * 
     * @return the total number of uncompressed bytes input so far
     */
    public int getTotalIn() {
        return (int) getBytesRead();
    }

    /**
     * Returns the total number of uncompressed bytes input so far.
     *
     * <p>
     *  返回到目前为止输入的未压缩字节的总数。
     * 
     * 
     * @return the total (non-negative) number of uncompressed bytes input so far
     * @since 1.5
     */
    public long getBytesRead() {
        synchronized (zsRef) {
            ensureOpen();
            return bytesRead;
        }
    }

    /**
     * Returns the total number of compressed bytes output so far.
     *
     * <p>Since the number of bytes may be greater than
     * Integer.MAX_VALUE, the {@link #getBytesWritten()} method is now
     * the preferred means of obtaining this information.</p>
     *
     * <p>
     *  返回到目前为止输出的压缩字节的总数。
     * 
     *  <p>由于字节数可能大于Integer.MAX_VALUE,因此{@link #getBytesWritten()}方法是获取此信息的首选方法。</p>
     * 
     * 
     * @return the total number of compressed bytes output so far
     */
    public int getTotalOut() {
        return (int) getBytesWritten();
    }

    /**
     * Returns the total number of compressed bytes output so far.
     *
     * <p>
     *  返回到目前为止输出的压缩字节的总数。
     * 
     * 
     * @return the total (non-negative) number of compressed bytes output so far
     * @since 1.5
     */
    public long getBytesWritten() {
        synchronized (zsRef) {
            ensureOpen();
            return bytesWritten;
        }
    }

    /**
     * Resets deflater so that a new set of input data can be processed.
     * Keeps current compression level and strategy settings.
     * <p>
     *  重置缩放器,以便可以处理一组新的输入数据。保持当前的压缩级别和策略设置。
     * 
     */
    public void reset() {
        synchronized (zsRef) {
            ensureOpen();
            reset(zsRef.address());
            finish = false;
            finished = false;
            off = len = 0;
            bytesRead = bytesWritten = 0;
        }
    }

    /**
     * Closes the compressor and discards any unprocessed input.
     * This method should be called when the compressor is no longer
     * being used, but will also be called automatically by the
     * finalize() method. Once this method is called, the behavior
     * of the Deflater object is undefined.
     * <p>
     *  关闭压缩机并丢弃任何未处理的输入。当压缩器不再使用时,应调用此方法,但也将通过finalize()方法自动调用此方法。调用此方法后,Deflater对象的行为是未定义的。
     * 
     */
    public void end() {
        synchronized (zsRef) {
            long addr = zsRef.address();
            zsRef.clear();
            if (addr != 0) {
                end(addr);
                buf = null;
            }
        }
    }

    /**
     * Closes the compressor when garbage is collected.
     * <p>
     *  收集垃圾时关闭压缩机。
     */
    protected void finalize() {
        end();
    }

    private void ensureOpen() {
        assert Thread.holdsLock(zsRef);
        if (zsRef.address() == 0)
            throw new NullPointerException("Deflater has been closed");
    }

    private static native void initIDs();
    private native static long init(int level, int strategy, boolean nowrap);
    private native static void setDictionary(long addr, byte[] b, int off, int len);
    private native int deflateBytes(long addr, byte[] b, int off, int len,
                                    int flush);
    private native static int getAdler(long addr);
    private native static void reset(long addr);
    private native static void end(long addr);
}
