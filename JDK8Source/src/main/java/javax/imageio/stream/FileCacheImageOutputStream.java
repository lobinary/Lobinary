/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.stream;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import com.sun.imageio.stream.StreamCloser;

/**
 * An implementation of <code>ImageOutputStream</code> that writes its
 * output to a regular <code>OutputStream</code>.  A file is used to
 * cache data until it is flushed to the output stream.
 *
 * <p>
 *  <code> ImageOutputStream </code>的实现,将其输出写入常规<code> OutputStream </code>。文件用于缓存数据,直到它被刷新到输出流。
 * 
 */
public class FileCacheImageOutputStream extends ImageOutputStreamImpl {

    private OutputStream stream;

    private File cacheFile;

    private RandomAccessFile cache;

    // Pos after last (rightmost) byte written
    private long maxStreamPos = 0L;

    /** The CloseAction that closes the stream in
    /* <p>
    /* 
     *  the StreamCloser's shutdown hook                     */
    private final StreamCloser.CloseAction closeAction;

    /**
     * Constructs a <code>FileCacheImageOutputStream</code> that will write
     * to a given <code>outputStream</code>.
     *
     * <p> A temporary file is used as a cache.  If
     * <code>cacheDir</code>is non-<code>null</code> and is a
     * directory, the file will be created there.  If it is
     * <code>null</code>, the system-dependent default temporary-file
     * directory will be used (see the documentation for
     * <code>File.createTempFile</code> for details).
     *
     * <p>
     *  构造将写入给定<code> outputStream </code>的<code> FileCacheImageOutputStream </code>。
     * 
     *  <p>临时文件用作缓存。如果<code> cacheDir </code>是非<code> null </code>并且是目录,那么将在那里创建文件。
     * 如果是<code> null </code>,将使用系统相关的默认临时文件目录(有关详细信息,请参阅<code> File.createTempFile </code>的文档)。
     * 
     * 
     * @param stream an <code>OutputStream</code> to write to.
     * @param cacheDir a <code>File</code> indicating where the
     * cache file should be created, or <code>null</code> to use the
     * system directory.
     *
     * @exception IllegalArgumentException if <code>stream</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>cacheDir</code> is
     * non-<code>null</code> but is not a directory.
     * @exception IOException if a cache file cannot be created.
     */
    public FileCacheImageOutputStream(OutputStream stream, File cacheDir)
        throws IOException {
        if (stream == null) {
            throw new IllegalArgumentException("stream == null!");
        }
        if ((cacheDir != null) && !(cacheDir.isDirectory())) {
            throw new IllegalArgumentException("Not a directory!");
        }
        this.stream = stream;
        if (cacheDir == null)
            this.cacheFile = Files.createTempFile("imageio", ".tmp").toFile();
        else
            this.cacheFile = Files.createTempFile(cacheDir.toPath(), "imageio", ".tmp")
                                  .toFile();
        this.cache = new RandomAccessFile(cacheFile, "rw");

        this.closeAction = StreamCloser.createCloseAction(this);
        StreamCloser.addToQueue(closeAction);
    }

    public int read() throws IOException {
        checkClosed();
        bitOffset = 0;
        int val =  cache.read();
        if (val != -1) {
            ++streamPos;
        }
        return val;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        checkClosed();

        if (b == null) {
            throw new NullPointerException("b == null!");
        }
        if (off < 0 || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off+len > b.length || off+len < 0!");
        }

        bitOffset = 0;

        if (len == 0) {
            return 0;
        }

        int nbytes = cache.read(b, off, len);
        if (nbytes != -1) {
            streamPos += nbytes;
        }
        return nbytes;
    }

    public void write(int b) throws IOException {
        flushBits(); // this will call checkClosed() for us
        cache.write(b);
        ++streamPos;
        maxStreamPos = Math.max(maxStreamPos, streamPos);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        flushBits(); // this will call checkClosed() for us
        cache.write(b, off, len);
        streamPos += len;
        maxStreamPos = Math.max(maxStreamPos, streamPos);
    }

    public long length() {
        try {
            checkClosed();
            return cache.length();
        } catch (IOException e) {
            return -1L;
        }
    }

    /**
     * Sets the current stream position and resets the bit offset to
     * 0.  It is legal to seek past the end of the file; an
     * <code>EOFException</code> will be thrown only if a read is
     * performed.  The file length will not be increased until a write
     * is performed.
     *
     * <p>
     *  设置当前流位置,并将位偏移重置为0.在文件末尾查找是合法的;只有在执行读取时才会抛出<code> EOFException </code>。在执行写操作之前,文件长度不会增加。
     * 
     * 
     * @exception IndexOutOfBoundsException if <code>pos</code> is smaller
     * than the flushed position.
     * @exception IOException if any other I/O error occurs.
     */
    public void seek(long pos) throws IOException {
        checkClosed();

        if (pos < flushedPos) {
            throw new IndexOutOfBoundsException();
        }

        cache.seek(pos);
        this.streamPos = cache.getFilePointer();
        maxStreamPos = Math.max(maxStreamPos, streamPos);
        this.bitOffset = 0;
    }

    /**
     * Returns <code>true</code> since this
     * <code>ImageOutputStream</code> caches data in order to allow
     * seeking backwards.
     *
     * <p>
     *  返回<code> true </code>,因为<code> ImageOutputStream </code>缓存数据以允许向后搜索。
     * 
     * 
     * @return <code>true</code>.
     *
     * @see #isCachedMemory
     * @see #isCachedFile
     */
    public boolean isCached() {
        return true;
    }

    /**
     * Returns <code>true</code> since this
     * <code>ImageOutputStream</code> maintains a file cache.
     *
     * <p>
     *  返回<code> true </code>,因为此<code> ImageOutputStream </code>维护文件缓存。
     * 
     * 
     * @return <code>true</code>.
     *
     * @see #isCached
     * @see #isCachedMemory
     */
    public boolean isCachedFile() {
        return true;
    }

    /**
     * Returns <code>false</code> since this
     * <code>ImageOutputStream</code> does not maintain a main memory
     * cache.
     *
     * <p>
     *  返回<code> false </code>,因为此<code> ImageOutputStream </code>不维护主内存缓存。
     * 
     * 
     * @return <code>false</code>.
     *
     * @see #isCached
     * @see #isCachedFile
     */
    public boolean isCachedMemory() {
        return false;
    }

    /**
     * Closes this <code>FileCacheImageOutputStream</code>.  All
     * pending data is flushed to the output, and the cache file
     * is closed and removed.  The destination <code>OutputStream</code>
     * is not closed.
     *
     * <p>
     * 关闭此<code> FileCacheImageOutputStream </code>。所有挂起的数据都将刷新到输出,并关闭并删除缓存文件。
     * 目标<code> OutputStream </code>未关闭。
     * 
     * @exception IOException if an error occurs.
     */
    public void close() throws IOException {
        maxStreamPos = cache.length();

        seek(maxStreamPos);
        flushBefore(maxStreamPos);
        super.close();
        cache.close();
        cache = null;
        cacheFile.delete();
        cacheFile = null;
        stream.flush();
        stream = null;
        StreamCloser.removeFromQueue(closeAction);
    }

    public void flushBefore(long pos) throws IOException {
        long oFlushedPos = flushedPos;
        super.flushBefore(pos); // this will call checkClosed() for us

        long flushBytes = flushedPos - oFlushedPos;
        if (flushBytes > 0) {
            int bufLen = 512;
            byte[] buf = new byte[bufLen];
            cache.seek(oFlushedPos);
            while (flushBytes > 0) {
                int len = (int)Math.min(flushBytes, bufLen);
                cache.readFully(buf, 0, len);
                stream.write(buf, 0, len);
                flushBytes -= len;
            }
            stream.flush();
        }
    }
}
