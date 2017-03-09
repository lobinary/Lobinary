/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2012, Oracle and/or its affiliates. All rights reserved.
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

package java.util.jar;

import java.util.zip.*;
import java.io.*;

/**
 * The <code>JarOutputStream</code> class is used to write the contents
 * of a JAR file to any output stream. It extends the class
 * <code>java.util.zip.ZipOutputStream</code> with support
 * for writing an optional <code>Manifest</code> entry. The
 * <code>Manifest</code> can be used to specify meta-information about
 * the JAR file and its entries.
 *
 * <p>
 *  <code> JarOutputStream </code>类用于将JAR文件的内容写入任何输出流。
 * 它扩展了<code> java.util.zip.ZipOutputStream </code>类,支持编写一个可选的<code> Manifest </code>条目。
 *  <code> Manifest </code>可用于指定有关JAR文件及其条目的元信息。
 * 
 * 
 * @author  David Connelly
 * @see     Manifest
 * @see     java.util.zip.ZipOutputStream
 * @since   1.2
 */
public
class JarOutputStream extends ZipOutputStream {
    private static final int JAR_MAGIC = 0xCAFE;

    /**
     * Creates a new <code>JarOutputStream</code> with the specified
     * <code>Manifest</code>. The manifest is written as the first
     * entry to the output stream.
     *
     * <p>
     *  使用指定的<code> Manifest </code>创建新的<code> JarOutputStream </code>。清单被写为输出流的第一个条目。
     * 
     * 
     * @param out the actual output stream
     * @param man the optional <code>Manifest</code>
     * @exception IOException if an I/O error has occurred
     */
    public JarOutputStream(OutputStream out, Manifest man) throws IOException {
        super(out);
        if (man == null) {
            throw new NullPointerException("man");
        }
        ZipEntry e = new ZipEntry(JarFile.MANIFEST_NAME);
        putNextEntry(e);
        man.write(new BufferedOutputStream(this));
        closeEntry();
    }

    /**
     * Creates a new <code>JarOutputStream</code> with no manifest.
     * <p>
     *  创建一个没有清单的新<code> JarOutputStream </code>。
     * 
     * 
     * @param out the actual output stream
     * @exception IOException if an I/O error has occurred
     */
    public JarOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    /**
     * Begins writing a new JAR file entry and positions the stream
     * to the start of the entry data. This method will also close
     * any previous entry. The default compression method will be
     * used if no compression method was specified for the entry.
     * The current time will be used if the entry has no set modification
     * time.
     *
     * <p>
     *  开始编写新的JAR文件条目,并将流定位到条目数据的开头。此方法还将关闭任何先前的条目。如果未为条目指定压缩方法,则将使用默认压缩方法。如果条目没有设置修改时间,将使用当前时间。
     * 
     * 
     * @param ze the ZIP/JAR entry to be written
     * @exception ZipException if a ZIP error has occurred
     * @exception IOException if an I/O error has occurred
     */
    public void putNextEntry(ZipEntry ze) throws IOException {
        if (firstEntry) {
            // Make sure that extra field data for first JAR
            // entry includes JAR magic number id.
            byte[] edata = ze.getExtra();
            if (edata == null || !hasMagic(edata)) {
                if (edata == null) {
                    edata = new byte[4];
                } else {
                    // Prepend magic to existing extra data
                    byte[] tmp = new byte[edata.length + 4];
                    System.arraycopy(edata, 0, tmp, 4, edata.length);
                    edata = tmp;
                }
                set16(edata, 0, JAR_MAGIC); // extra field id
                set16(edata, 2, 0);         // extra field size
                ze.setExtra(edata);
            }
            firstEntry = false;
        }
        super.putNextEntry(ze);
    }

    private boolean firstEntry = true;

    /*
     * Returns true if specified byte array contains the
     * jar magic extra field id.
     * <p>
     *  如果指定的字节数组包含jar魔术额外字段id,则返回true。
     * 
     */
    private static boolean hasMagic(byte[] edata) {
        try {
            int i = 0;
            while (i < edata.length) {
                if (get16(edata, i) == JAR_MAGIC) {
                    return true;
                }
                i += get16(edata, i + 2) + 4;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Invalid extra field data
        }
        return false;
    }

    /*
     * Fetches unsigned 16-bit value from byte array at specified offset.
     * The bytes are assumed to be in Intel (little-endian) byte order.
     * <p>
     *  从指定偏移量的字节数组中读取无符号16位值。这些字节假定是以英特尔(小端)字节顺序。
     * 
     */
    private static int get16(byte[] b, int off) {
        return Byte.toUnsignedInt(b[off]) | ( Byte.toUnsignedInt(b[off+1]) << 8);
    }

    /*
     * Sets 16-bit value at specified offset. The bytes are assumed to
     * be in Intel (little-endian) byte order.
     * <p>
     *  在指定偏移处设置16位值。这些字节假定是以英特尔(小端)字节顺序。
     */
    private static void set16(byte[] b, int off, int value) {
        b[off+0] = (byte)value;
        b[off+1] = (byte)(value >> 8);
    }
}
