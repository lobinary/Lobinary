/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import static java.util.zip.ZipUtils.*;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.util.zip.ZipConstants64.*;

/**
 * This class is used to represent a ZIP file entry.
 *
 * <p>
 *  此类用于表示ZIP文件条目。
 * 
 * 
 * @author      David Connelly
 */
public
class ZipEntry implements ZipConstants, Cloneable {

    String name;        // entry name
    long time = -1;     // last modification time
    FileTime mtime;     // last modification time, from extra field data
    FileTime atime;     // last access time, from extra field data
    FileTime ctime;     // creation time, from extra field data
    long crc = -1;      // crc-32 of entry data
    long size = -1;     // uncompressed size of entry data
    long csize = -1;    // compressed size of entry data
    int method = -1;    // compression method
    int flag = 0;       // general purpose flag
    byte[] extra;       // optional extra field data for entry
    String comment;     // optional comment string for entry

    /**
     * Compression method for uncompressed entries.
     * <p>
     *  未压缩条目的压缩方法。
     * 
     */
    public static final int STORED = 0;

    /**
     * Compression method for compressed (deflated) entries.
     * <p>
     *  压缩方法用于压缩(缩小)条目。
     * 
     */
    public static final int DEFLATED = 8;

    /**
     * Creates a new zip entry with the specified name.
     *
     * <p>
     *  创建具有指定名称的新zip条目。
     * 
     * 
     * @param  name
     *         The entry name
     *
     * @throws NullPointerException if the entry name is null
     * @throws IllegalArgumentException if the entry name is longer than
     *         0xFFFF bytes
     */
    public ZipEntry(String name) {
        Objects.requireNonNull(name, "name");
        if (name.length() > 0xFFFF) {
            throw new IllegalArgumentException("entry name too long");
        }
        this.name = name;
    }

    /**
     * Creates a new zip entry with fields taken from the specified
     * zip entry.
     *
     * <p>
     *  创建一个新的zip条目,其中的字段取自指定的zip条目。
     * 
     * 
     * @param  e
     *         A zip Entry object
     *
     * @throws NullPointerException if the entry object is null
     */
    public ZipEntry(ZipEntry e) {
        Objects.requireNonNull(e, "entry");
        name = e.name;
        time = e.time;
        mtime = e.mtime;
        atime = e.atime;
        ctime = e.ctime;
        crc = e.crc;
        size = e.size;
        csize = e.csize;
        method = e.method;
        flag = e.flag;
        extra = e.extra;
        comment = e.comment;
    }

    /**
     * Creates a new un-initialized zip entry
     * <p>
     *  创建新的未初始化zip条目
     * 
     */
    ZipEntry() {}

    /**
     * Returns the name of the entry.
     * <p>
     *  返回条目的名称。
     * 
     * 
     * @return the name of the entry
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the last modification time of the entry.
     *
     * <p> If the entry is output to a ZIP file or ZIP file formatted
     * output stream the last modification time set by this method will
     * be stored into the {@code date and time fields} of the zip file
     * entry and encoded in standard {@code MS-DOS date and time format}.
     * The {@link java.util.TimeZone#getDefault() default TimeZone} is
     * used to convert the epoch time to the MS-DOS data and time.
     *
     * <p>
     *  设置条目的最后修改时间。
     * 
     *  <p>如果条目输出到ZIP文件或ZIP文件格式的输出流,则此方法设置的最后修改时间将存储到zip文件条目的{@code日期和时间字段}中,并以标准{代码MS-DOS日期和时间格式}。
     *  {@link java.util.TimeZone#getDefault()default TimeZone}用于将纪元时间转换为MS-DOS数据和时间。
     * 
     * 
     * @param  time
     *         The last modification time of the entry in milliseconds
     *         since the epoch
     *
     * @see #getTime()
     * @see #getLastModifiedTime()
     */
    public void setTime(long time) {
        this.time = time;
        this.mtime = null;
    }

    /**
     * Returns the last modification time of the entry.
     *
     * <p> If the entry is read from a ZIP file or ZIP file formatted
     * input stream, this is the last modification time from the {@code
     * date and time fields} of the zip file entry. The
     * {@link java.util.TimeZone#getDefault() default TimeZone} is used
     * to convert the standard MS-DOS formatted date and time to the
     * epoch time.
     *
     * <p>
     *  返回条目的上次修改时间。
     * 
     *  <p>如果从ZIP文件或ZIP文件格式化的输入流读取条目,则这是从zip文件条目的{@code日期和时间字段}的最后一次修改时间。
     *  {@link java.util.TimeZone#getDefault()default TimeZone}用于将标准MS-DOS格式的日期和时间转换为纪元时间。
     * 
     * 
     * @return  The last modification time of the entry in milliseconds
     *          since the epoch, or -1 if not specified
     *
     * @see #setTime(long)
     * @see #setLastModifiedTime(FileTime)
     */
    public long getTime() {
        return time;
    }

    /**
     * Sets the last modification time of the entry.
     *
     * <p> When output to a ZIP file or ZIP file formatted output stream
     * the last modification time set by this method will be stored into
     * zip file entry's {@code date and time fields} in {@code standard
     * MS-DOS date and time format}), and the extended timestamp fields
     * in {@code optional extra data} in UTC time.
     *
     * <p>
     *  设置条目的最后修改时间。
     * 
     * <p>当输出到ZIP文件或ZIP文件格式化的输出流时,此方法设置的最后修改时间将存储在{@code标准MS-DOS日期和时间格式中的zip文件条目的{@code日期和时间字段} })和UTC时间中{@code可选的额外数据}
     * 中的扩展时间戳字段。
     * 
     * 
     * @param  time
     *         The last modification time of the entry
     * @return This zip entry
     *
     * @throws NullPointerException if the {@code time} is null
     *
     * @see #getLastModifiedTime()
     * @since 1.8
     */
    public ZipEntry setLastModifiedTime(FileTime time) {
        Objects.requireNonNull(name, "time");
        this.mtime = time;
        this.time = time.to(TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * Returns the last modification time of the entry.
     *
     * <p> If the entry is read from a ZIP file or ZIP file formatted
     * input stream, this is the last modification time from the zip
     * file entry's {@code optional extra data} if the extended timestamp
     * fields are present. Otherwise the last modification time is read
     * from the entry's {@code date and time fields}, the {@link
     * java.util.TimeZone#getDefault() default TimeZone} is used to convert
     * the standard MS-DOS formatted date and time to the epoch time.
     *
     * <p>
     *  返回条目的上次修改时间。
     * 
     *  <p>如果从ZIP文件或ZIP文件格式化的输入流读取条目,则这是从zip文件条目的{@code可选额外数据}(如果扩展时间戳字段存在)的最后一次修改时间。
     * 否则,从条目的{@code日期和时间字段}中读取最后一次修改时间,{@link java.util.TimeZone#getDefault()default TimeZone}用于将标准MS-DOS格式
     * 的日期和时间转换为时代。
     *  <p>如果从ZIP文件或ZIP文件格式化的输入流读取条目,则这是从zip文件条目的{@code可选额外数据}(如果扩展时间戳字段存在)的最后一次修改时间。
     * 
     * 
     * @return The last modification time of the entry, null if not specified
     *
     * @see #setLastModifiedTime(FileTime)
     * @since 1.8
     */
    public FileTime getLastModifiedTime() {
        if (mtime != null)
            return mtime;
        if (time == -1)
            return null;
        return FileTime.from(time, TimeUnit.MILLISECONDS);
    }

    /**
     * Sets the last access time of the entry.
     *
     * <p> If set, the last access time will be stored into the extended
     * timestamp fields of entry's {@code optional extra data}, when output
     * to a ZIP file or ZIP file formatted stream.
     *
     * <p>
     *  设置条目的最后访问时间。
     * 
     *  <p>如果设置,当输出到ZIP文件或ZIP文件格式的流时,最后访问时间将存储在条目的{@code可选额外数据}的扩展时间戳字段中。
     * 
     * 
     * @param  time
     *         The last access time of the entry
     * @return This zip entry
     *
     * @throws NullPointerException if the {@code time} is null
     *
     * @see #getLastAccessTime()
     * @since 1.8
     */
    public ZipEntry setLastAccessTime(FileTime time) {
        Objects.requireNonNull(name, "time");
        this.atime = time;
        return this;
    }

    /**
     * Returns the last access time of the entry.
     *
     * <p> The last access time is from the extended timestamp fields
     * of entry's {@code optional extra data} when read from a ZIP file
     * or ZIP file formatted stream.
     *
     * <p>
     *  返回条目的最后访问时间。
     * 
     *  <p>从ZIP文件或ZIP文件格式的流读取时,最后一次访问时间来自条目的{@code可选额外数据}的扩展时间戳字段。
     * 
     * 
     * @return The last access time of the entry, null if not specified

     * @see #setLastAccessTime(FileTime)
     * @since 1.8
     */
    public FileTime getLastAccessTime() {
        return atime;
    }

    /**
     * Sets the creation time of the entry.
     *
     * <p> If set, the creation time will be stored into the extended
     * timestamp fields of entry's {@code optional extra data}, when
     * output to a ZIP file or ZIP file formatted stream.
     *
     * <p>
     *  设置条目的创建时间。
     * 
     *  <p>如果设置,当输出到ZIP文件或ZIP文件格式的流时,创建时间将存储在条目的{@code可选额外数据}的扩展时间戳字段中。
     * 
     * 
     * @param  time
     *         The creation time of the entry
     * @return This zip entry
     *
     * @throws NullPointerException if the {@code time} is null
     *
     * @see #getCreationTime()
     * @since 1.8
     */
    public ZipEntry setCreationTime(FileTime time) {
        Objects.requireNonNull(name, "time");
        this.ctime = time;
        return this;
    }

    /**
     * Returns the creation time of the entry.
     *
     * <p> The creation time is from the extended timestamp fields of
     * entry's {@code optional extra data} when read from a ZIP file
     * or ZIP file formatted stream.
     *
     * <p>
     *  返回条目的创建时间。
     * 
     * <p>创建时间是从ZIP文件或ZIP文件格式的流读取时条目的{@code可选的额外数据}的扩展时间戳字段。
     * 
     * 
     * @return the creation time of the entry, null if not specified
     * @see #setCreationTime(FileTime)
     * @since 1.8
     */
    public FileTime getCreationTime() {
        return ctime;
    }

    /**
     * Sets the uncompressed size of the entry data.
     *
     * <p>
     *  设置条目数据的未压缩大小。
     * 
     * 
     * @param size the uncompressed size in bytes
     *
     * @throws IllegalArgumentException if the specified size is less
     *         than 0, is greater than 0xFFFFFFFF when
     *         <a href="package-summary.html#zip64">ZIP64 format</a> is not supported,
     *         or is less than 0 when ZIP64 is supported
     * @see #getSize()
     */
    public void setSize(long size) {
        if (size < 0) {
            throw new IllegalArgumentException("invalid entry size");
        }
        this.size = size;
    }

    /**
     * Returns the uncompressed size of the entry data.
     *
     * <p>
     *  返回条目数据的未压缩大小。
     * 
     * 
     * @return the uncompressed size of the entry data, or -1 if not known
     * @see #setSize(long)
     */
    public long getSize() {
        return size;
    }

    /**
     * Returns the size of the compressed entry data.
     *
     * <p> In the case of a stored entry, the compressed size will be the same
     * as the uncompressed size of the entry.
     *
     * <p>
     *  返回压缩条目数据的大小。
     * 
     *  <p>在存储条目的情况下,压缩大小将与条目的未压缩大小相同。
     * 
     * 
     * @return the size of the compressed entry data, or -1 if not known
     * @see #setCompressedSize(long)
     */
    public long getCompressedSize() {
        return csize;
    }

    /**
     * Sets the size of the compressed entry data.
     *
     * <p>
     *  设置压缩条目数据的大小。
     * 
     * 
     * @param csize the compressed size to set to
     *
     * @see #getCompressedSize()
     */
    public void setCompressedSize(long csize) {
        this.csize = csize;
    }

    /**
     * Sets the CRC-32 checksum of the uncompressed entry data.
     *
     * <p>
     *  设置未压缩条目数据的CRC-32校验和。
     * 
     * 
     * @param crc the CRC-32 value
     *
     * @throws IllegalArgumentException if the specified CRC-32 value is
     *         less than 0 or greater than 0xFFFFFFFF
     * @see #getCrc()
     */
    public void setCrc(long crc) {
        if (crc < 0 || crc > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("invalid entry crc-32");
        }
        this.crc = crc;
    }

    /**
     * Returns the CRC-32 checksum of the uncompressed entry data.
     *
     * <p>
     *  返回未压缩条目数据的CRC-32校验和。
     * 
     * 
     * @return the CRC-32 checksum of the uncompressed entry data, or -1 if
     * not known
     *
     * @see #setCrc(long)
     */
    public long getCrc() {
        return crc;
    }

    /**
     * Sets the compression method for the entry.
     *
     * <p>
     *  设置条目的压缩方法。
     * 
     * 
     * @param method the compression method, either STORED or DEFLATED
     *
     * @throws  IllegalArgumentException if the specified compression
     *          method is invalid
     * @see #getMethod()
     */
    public void setMethod(int method) {
        if (method != STORED && method != DEFLATED) {
            throw new IllegalArgumentException("invalid compression method");
        }
        this.method = method;
    }

    /**
     * Returns the compression method of the entry.
     *
     * <p>
     *  返回条目的压缩方法。
     * 
     * 
     * @return the compression method of the entry, or -1 if not specified
     * @see #setMethod(int)
     */
    public int getMethod() {
        return method;
    }

    /**
     * Sets the optional extra field data for the entry.
     *
     * <p> Invoking this method may change this entry's last modification
     * time, last access time and creation time, if the {@code extra} field
     * data includes the extensible timestamp fields, such as {@code NTFS tag
     * 0x0001} or {@code Info-ZIP Extended Timestamp}, as specified in
     * <a href="http://www.info-zip.org/doc/appnote-19970311-iz.zip">Info-ZIP
     * Application Note 970311</a>.
     *
     * <p>
     *  为条目设置可选的附加字段数据。
     * 
     *  <p>如果{@code extra}字段数据包含可扩展时间戳字段(如{@code NTFS tag 0x0001}或{@code Info}),则调用此方法可能会更改此条目的上次修改时间,上次访问时间
     * 和创建时间-ZIP扩展时间戳},如<a href="http://www.info-zip.org/doc/appnote-19970311-iz.zip"> Info-ZIP应用笔记970311中所述
     * </a>。
     * 
     * 
     * @param  extra
     *         The extra field data bytes
     *
     * @throws IllegalArgumentException if the length of the specified
     *         extra field data is greater than 0xFFFF bytes
     *
     * @see #getExtra()
     */
    public void setExtra(byte[] extra) {
        setExtra0(extra, false);
    }

    /**
     * Sets the optional extra field data for the entry.
     *
     * <p>
     *  为条目设置可选的附加字段数据。
     * 
     * 
     * @param extra
     *        the extra field data bytes
     * @param doZIP64
     *        if true, set size and csize from ZIP64 fields if present
     */
    void setExtra0(byte[] extra, boolean doZIP64) {
        if (extra != null) {
            if (extra.length > 0xFFFF) {
                throw new IllegalArgumentException("invalid extra field length");
            }
            // extra fields are in "HeaderID(2)DataSize(2)Data... format
            int off = 0;
            int len = extra.length;
            while (off + 4 < len) {
                int tag = get16(extra, off);
                int sz = get16(extra, off + 2);
                off += 4;
                if (off + sz > len)         // invalid data
                    break;
                switch (tag) {
                case EXTID_ZIP64:
                    if (doZIP64) {
                        // LOC extra zip64 entry MUST include BOTH original
                        // and compressed file size fields.
                        // If invalid zip64 extra fields, simply skip. Even
                        // it's rare, it's possible the entry size happens to
                        // be the magic value and it "accidently" has some
                        // bytes in extra match the id.
                        if (sz >= 16) {
                            size = get64(extra, off);
                            csize = get64(extra, off + 8);
                        }
                    }
                    break;
                case EXTID_NTFS:
                    int pos = off + 4;               // reserved 4 bytes
                    if (get16(extra, pos) !=  0x0001 || get16(extra, pos + 2) != 24)
                        break;
                    mtime = winTimeToFileTime(get64(extra, pos + 4));
                    atime = winTimeToFileTime(get64(extra, pos + 12));
                    ctime = winTimeToFileTime(get64(extra, pos + 20));
                    break;
                case EXTID_EXTT:
                    int flag = Byte.toUnsignedInt(extra[off]);
                    int sz0 = 1;
                    // The CEN-header extra field contains the modification
                    // time only, or no timestamp at all. 'sz' is used to
                    // flag its presence or absence. But if mtime is present
                    // in LOC it must be present in CEN as well.
                    if ((flag & 0x1) != 0 && (sz0 + 4) <= sz) {
                        mtime = unixTimeToFileTime(get32(extra, off + sz0));
                        sz0 += 4;
                    }
                    if ((flag & 0x2) != 0 && (sz0 + 4) <= sz) {
                        atime = unixTimeToFileTime(get32(extra, off + sz0));
                        sz0 += 4;
                    }
                    if ((flag & 0x4) != 0 && (sz0 + 4) <= sz) {
                        ctime = unixTimeToFileTime(get32(extra, off + sz0));
                        sz0 += 4;
                    }
                    break;
                 default:
                }
                off += sz;
            }
        }
        this.extra = extra;
    }

    /**
     * Returns the extra field data for the entry.
     *
     * <p>
     *  返回条目的额外字段数据。
     * 
     * 
     * @return the extra field data for the entry, or null if none
     *
     * @see #setExtra(byte[])
     */
    public byte[] getExtra() {
        return extra;
    }

    /**
     * Sets the optional comment string for the entry.
     *
     * <p>ZIP entry comments have maximum length of 0xffff. If the length of the
     * specified comment string is greater than 0xFFFF bytes after encoding, only
     * the first 0xFFFF bytes are output to the ZIP file entry.
     *
     * <p>
     *  设置条目的可选注释字符串。
     * 
     *  <p> ZIP输入注释的最大长度为0xffff。如果编码后指定的注释字符串的长度大于0xFFFF字节,则只有第一个0xFFFF字节输出到ZIP文件条目。
     * 
     * 
     * @param comment the comment string
     *
     * @see #getComment()
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns the comment string for the entry.
     *
     * <p>
     *  返回条目的注释字符串。
     * 
     * 
     * @return the comment string for the entry, or null if none
     *
     * @see #setComment(String)
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns true if this is a directory entry. A directory entry is
     * defined to be one whose name ends with a '/'.
     * <p>
     * 如果这是目录条目,则返回true。目录条目定义为其名称以"/"结尾的目录条目。
     * 
     * 
     * @return true if this is a directory entry
     */
    public boolean isDirectory() {
        return name.endsWith("/");
    }

    /**
     * Returns a string representation of the ZIP entry.
     * <p>
     *  返回ZIP条目的字符串表示形式。
     * 
     */
    public String toString() {
        return getName();
    }

    /**
     * Returns the hash code value for this entry.
     * <p>
     *  返回此条目的哈希码值。
     * 
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns a copy of this entry.
     * <p>
     *  返回此条目的副本。
     */
    public Object clone() {
        try {
            ZipEntry e = (ZipEntry)super.clone();
            e.extra = (extra == null) ? null : extra.clone();
            return e;
        } catch (CloneNotSupportedException e) {
            // This should never happen, since we are Cloneable
            throw new InternalError(e);
        }
    }
}
