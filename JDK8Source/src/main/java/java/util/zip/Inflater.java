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
 * This class provides support for general purpose decompression using the
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
 *     String inputString = "blahblahblah\u20AC\u20AC";
 *     byte[] input = inputString.getBytes("UTF-8");
 *
 *     // Compress the bytes
 *     byte[] output = new byte[100];
 *     Deflater compresser = new Deflater();
 *     compresser.setInput(input);
 *     compresser.finish();
 *     int compressedDataLength = compresser.deflate(output);
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
 *  此类提供对使用受欢迎的ZLIB压缩库的通用解压缩的支持。 ZLIB压缩库最初是作为PNG图形标准的一部分开发的,不受专利保护。
 * 在<a href="package-summary.html#package_description"> java.util.zip软件包描述</a>的规范中对此进行了完整的说明。
 * 
 *  <p>以下代码片段演示了使用<tt> Deflater </tt>和<tt> Inflater </tt>对字符串进行简单压缩和解压缩。
 * 
 *  <blockquote> <pre> try {//将字符串编码为字节String inputString ="blahblahblah \ u20AC \ u20AC"; byte [] input = inputString.getBytes("UTF-8");。
 * 
 *  //压缩字节byte [] output = new byte [100]; Deflater compresser = new Deflater(); compresser.setInput(inp
 * ut); compresser.finish(); int compressedDataLength = compresser.deflate(output);。
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
 * @see         Deflater
 * @author      David Connelly
 *
 */
public
class Inflater {

    private final ZStreamRef zsRef;
    private byte[] buf = defaultBuf;
    private int off, len;
    private boolean finished;
    private boolean needDict;
    private long bytesRead;
    private long bytesWritten;

    private static final byte[] defaultBuf = new byte[0];

    static {
        /* Zip library is loaded from System.initializeSystemClass */
        initIDs();
    }

    /**
     * Creates a new decompressor. If the parameter 'nowrap' is true then
     * the ZLIB header and checksum fields will not be used. This provides
     * compatibility with the compression format used by both GZIP and PKZIP.
     * <p>
     * Note: When using the 'nowrap' option it is also necessary to provide
     * an extra "dummy" byte as input. This is required by the ZLIB native
     * library in order to support certain optimizations.
     *
     * <p>
     * 创建一个新的解压缩程序。如果参数'nowrap'为true,则不会使用ZLIB头和校验和字段。这提供与GZIP和PKZIP使用的压缩格式的兼容性。
     * <p>
     *  注意：使用'nowrap'选项时,还需要提供一个额外的"dummy"字节作为输入。这是ZLIB本地库为了支持某些优化所必需的。
     * 
     * 
     * @param nowrap if true then support GZIP compatible compression
     */
    public Inflater(boolean nowrap) {
        zsRef = new ZStreamRef(init(nowrap));
    }

    /**
     * Creates a new decompressor.
     * <p>
     *  创建一个新的解压缩程序。
     * 
     */
    public Inflater() {
        this(false);
    }

    /**
     * Sets input data for decompression. Should be called whenever
     * needsInput() returns true indicating that more input data is
     * required.
     * <p>
     *  设置解压缩的输入数据。应该在needInput()返回true时调用,指示需要更多输入数据。
     * 
     * 
     * @param b the input data bytes
     * @param off the start offset of the input data
     * @param len the length of the input data
     * @see Inflater#needsInput
     */
    public void setInput(byte[] b, int off, int len) {
        if (b == null) {
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
     * Sets input data for decompression. Should be called whenever
     * needsInput() returns true indicating that more input data is
     * required.
     * <p>
     *  设置解压缩的输入数据。应该在needInput()返回true时调用,指示需要更多输入数据。
     * 
     * 
     * @param b the input data bytes
     * @see Inflater#needsInput
     */
    public void setInput(byte[] b) {
        setInput(b, 0, b.length);
    }

    /**
     * Sets the preset dictionary to the given array of bytes. Should be
     * called when inflate() returns 0 and needsDictionary() returns true
     * indicating that a preset dictionary is required. The method getAdler()
     * can be used to get the Adler-32 value of the dictionary needed.
     * <p>
     *  将预设字典设置为给定的字节数组。应该在inflate()返回0时调用,而needsDictionary()返回true,表示需要预设字典。
     * 方法getAdler()可以用来获取字典所需的Adler-32值。
     * 
     * 
     * @param b the dictionary data bytes
     * @param off the start offset of the data
     * @param len the length of the data
     * @see Inflater#needsDictionary
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
            needDict = false;
        }
    }

    /**
     * Sets the preset dictionary to the given array of bytes. Should be
     * called when inflate() returns 0 and needsDictionary() returns true
     * indicating that a preset dictionary is required. The method getAdler()
     * can be used to get the Adler-32 value of the dictionary needed.
     * <p>
     *  将预设字典设置为给定的字节数组。应该在inflate()返回0时调用,而needsDictionary()返回true,表示需要预设字典。
     * 方法getAdler()可以用来获取字典所需的Adler-32值。
     * 
     * 
     * @param b the dictionary data bytes
     * @see Inflater#needsDictionary
     * @see Inflater#getAdler
     */
    public void setDictionary(byte[] b) {
        setDictionary(b, 0, b.length);
    }

    /**
     * Returns the total number of bytes remaining in the input buffer.
     * This can be used to find out what bytes still remain in the input
     * buffer after decompression has finished.
     * <p>
     *  返回输入缓冲区中剩余的字节总数。这可以用于找出解压缩完成后在输入缓冲区中仍然保留哪些字节。
     * 
     * 
     * @return the total number of bytes remaining in the input buffer
     */
    public int getRemaining() {
        synchronized (zsRef) {
            return len;
        }
    }

    /**
     * Returns true if no data remains in the input buffer. This can
     * be used to determine if #setInput should be called in order
     * to provide more input.
     * <p>
     * 如果输入缓冲区中没有数据,则返回true。这可以用于确定是否应调用#setInput以提供更多输入。
     * 
     * 
     * @return true if no data remains in the input buffer
     */
    public boolean needsInput() {
        synchronized (zsRef) {
            return len <= 0;
        }
    }

    /**
     * Returns true if a preset dictionary is needed for decompression.
     * <p>
     *  如果解压缩需要预设字典,则返回true。
     * 
     * 
     * @return true if a preset dictionary is needed for decompression
     * @see Inflater#setDictionary
     */
    public boolean needsDictionary() {
        synchronized (zsRef) {
            return needDict;
        }
    }

    /**
     * Returns true if the end of the compressed data stream has been
     * reached.
     * <p>
     *  如果已达到压缩数据流的结尾,则返回true。
     * 
     * 
     * @return true if the end of the compressed data stream has been
     * reached
     */
    public boolean finished() {
        synchronized (zsRef) {
            return finished;
        }
    }

    /**
     * Uncompresses bytes into specified buffer. Returns actual number
     * of bytes uncompressed. A return value of 0 indicates that
     * needsInput() or needsDictionary() should be called in order to
     * determine if more input data or a preset dictionary is required.
     * In the latter case, getAdler() can be used to get the Adler-32
     * value of the dictionary required.
     * <p>
     *  将字节解压缩到指定的缓冲区。返回未压缩的实际字节数。返回值为0表示需要调用needsInput()或needsDictionary(),以确定是否需要更多输入数据或预设字典。
     * 在后一种情况下,getAdler()可用于获取字典所需的Adler-32值。
     * 
     * 
     * @param b the buffer for the uncompressed data
     * @param off the start offset of the data
     * @param len the maximum number of uncompressed bytes
     * @return the actual number of uncompressed bytes
     * @exception DataFormatException if the compressed data format is invalid
     * @see Inflater#needsInput
     * @see Inflater#needsDictionary
     */
    public int inflate(byte[] b, int off, int len)
        throws DataFormatException
    {
        if (b == null) {
            throw new NullPointerException();
        }
        if (off < 0 || len < 0 || off > b.length - len) {
            throw new ArrayIndexOutOfBoundsException();
        }
        synchronized (zsRef) {
            ensureOpen();
            int thisLen = this.len;
            int n = inflateBytes(zsRef.address(), b, off, len);
            bytesWritten += n;
            bytesRead += (thisLen - this.len);
            return n;
        }
    }

    /**
     * Uncompresses bytes into specified buffer. Returns actual number
     * of bytes uncompressed. A return value of 0 indicates that
     * needsInput() or needsDictionary() should be called in order to
     * determine if more input data or a preset dictionary is required.
     * In the latter case, getAdler() can be used to get the Adler-32
     * value of the dictionary required.
     * <p>
     *  将字节解压缩到指定的缓冲区。返回未压缩的实际字节数。返回值为0表示需要调用needsInput()或needsDictionary(),以确定是否需要更多输入数据或预设字典。
     * 在后一种情况下,getAdler()可用于获取字典所需的Adler-32值。
     * 
     * 
     * @param b the buffer for the uncompressed data
     * @return the actual number of uncompressed bytes
     * @exception DataFormatException if the compressed data format is invalid
     * @see Inflater#needsInput
     * @see Inflater#needsDictionary
     */
    public int inflate(byte[] b) throws DataFormatException {
        return inflate(b, 0, b.length);
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
     * Returns the total number of compressed bytes input so far.
     *
     * <p>Since the number of bytes may be greater than
     * Integer.MAX_VALUE, the {@link #getBytesRead()} method is now
     * the preferred means of obtaining this information.</p>
     *
     * <p>
     *  返回到目前为止输入的压缩字节的总数。
     * 
     *  <p>由于字节数可能大于Integer.MAX_VALUE,因此{@link #getBytesRead()}方法是获取此信息的首选方法。</p>
     * 
     * 
     * @return the total number of compressed bytes input so far
     */
    public int getTotalIn() {
        return (int) getBytesRead();
    }

    /**
     * Returns the total number of compressed bytes input so far.
     *
     * <p>
     *  返回到目前为止输入的压缩字节的总数。
     * 
     * 
     * @return the total (non-negative) number of compressed bytes input so far
     * @since 1.5
     */
    public long getBytesRead() {
        synchronized (zsRef) {
            ensureOpen();
            return bytesRead;
        }
    }

    /**
     * Returns the total number of uncompressed bytes output so far.
     *
     * <p>Since the number of bytes may be greater than
     * Integer.MAX_VALUE, the {@link #getBytesWritten()} method is now
     * the preferred means of obtaining this information.</p>
     *
     * <p>
     *  返回到目前为止输出的未压缩字节的总数。
     * 
     * <p>由于字节数可能大于Integer.MAX_VALUE,因此{@link #getBytesWritten()}方法是获取此信息的首选方法。</p>
     * 
     * 
     * @return the total number of uncompressed bytes output so far
     */
    public int getTotalOut() {
        return (int) getBytesWritten();
    }

    /**
     * Returns the total number of uncompressed bytes output so far.
     *
     * <p>
     *  返回到目前为止输出的未压缩字节的总数。
     * 
     * 
     * @return the total (non-negative) number of uncompressed bytes output so far
     * @since 1.5
     */
    public long getBytesWritten() {
        synchronized (zsRef) {
            ensureOpen();
            return bytesWritten;
        }
    }

    /**
     * Resets inflater so that a new set of input data can be processed.
     * <p>
     *  重置充气器,以便可以处理一组新的输入数据。
     * 
     */
    public void reset() {
        synchronized (zsRef) {
            ensureOpen();
            reset(zsRef.address());
            buf = defaultBuf;
            finished = false;
            needDict = false;
            off = len = 0;
            bytesRead = bytesWritten = 0;
        }
    }

    /**
     * Closes the decompressor and discards any unprocessed input.
     * This method should be called when the decompressor is no longer
     * being used, but will also be called automatically by the finalize()
     * method. Once this method is called, the behavior of the Inflater
     * object is undefined.
     * <p>
     *  关闭解压缩器并丢弃任何未处理的输入。当解压缩器不再使用时,应调用此方法,但也将由finalize()方法自动调用此方法。调用此方法后,Inflater对象的行为是未定义的。
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
     * Closes the decompressor when garbage is collected.
     * <p>
     *  当收集垃圾时关闭解压缩器。
     */
    protected void finalize() {
        end();
    }

    private void ensureOpen () {
        assert Thread.holdsLock(zsRef);
        if (zsRef.address() == 0)
            throw new NullPointerException("Inflater has been closed");
    }

    boolean ended() {
        synchronized (zsRef) {
            return zsRef.address() == 0;
        }
    }

    private native static void initIDs();
    private native static long init(boolean nowrap);
    private native static void setDictionary(long addr, byte[] b, int off,
                                             int len);
    private native int inflateBytes(long addr, byte[] b, int off, int len)
            throws DataFormatException;
    private native static int getAdler(long addr);
    private native static void reset(long addr);
    private native static void end(long addr);
}
