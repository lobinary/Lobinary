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

package javax.sql.rowset.serial;

import java.sql.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.Arrays;


/**
 * A serialized mapping in the Java programming language of an SQL
 * <code>BLOB</code> value.
 * <P>
 * The <code>SerialBlob</code> class provides a constructor for creating
 * an instance from a <code>Blob</code> object.  Note that the
 * <code>Blob</code>
 * object should have brought the SQL <code>BLOB</code> value's data over
 * to the client before a <code>SerialBlob</code> object
 * is constructed from it.  The data of an SQL <code>BLOB</code> value can
 * be materialized on the client as an array of bytes (using the method
 * <code>Blob.getBytes</code>) or as a stream of uninterpreted bytes
 * (using the method <code>Blob.getBinaryStream</code>).
 * <P>
 * <code>SerialBlob</code> methods make it possible to make a copy of a
 * <code>SerialBlob</code> object as an array of bytes or as a stream.
 * They also make it possible to locate a given pattern of bytes or a
 * <code>Blob</code> object within a <code>SerialBlob</code> object
 * and to update or truncate a <code>Blob</code> object.
 *
 * <h3> Thread safety </h3>
 *
 * <p> A SerialBlob is not safe for use by multiple concurrent threads.  If a
 * SerialBlob is to be used by more than one thread then access to the SerialBlob
 * should be controlled by appropriate synchronization.
 *
 * <p>
 *  在Java编程语言中的SQL <code> BLOB </code>值的序列化映射。
 * <P>
 *  <code> SerialBlob </code>类提供了一个从<code> Blob </code>对象创建实例的构造函数。
 * 注意,在从<code> BLOB </code>对象构建一个<code> SerialBlob </code>对象之前,<code> Blob </code>对象应该将SQL <code> BLOB </code>
 *  SQL <code> BLOB </code>值的数据可以在客户端上作为字节数组实现(使用方法<code> Blob.getBytes </code>)或作为未解释字节流方法<code> Blob.g
 * etBinaryStream </code>)。
 *  <code> SerialBlob </code>类提供了一个从<code> Blob </code>对象创建实例的构造函数。
 * <P>
 *  <code> SerialBlob </code>方法可以将<code> SerialBlob </code>对象的副本作为字节数组或作为流。
 * 它们还使得可以在<code> SerialBlob </code>对象内定位给定字节模式或<code> Blob </code>对象,并更新或截断<code> Blob </code>对象。
 * 
 *  <h3>线程安全</h3>
 * 
 *  <p> SerialBlob不能安全地用于多个并发线程。如果一个SerialBlob要被多个线程使用,那么对SerialBlob的访问应该通过适当的同步来控制。
 * 
 * 
 * @author Jonathan Bruce
 */
public class SerialBlob implements Blob, Serializable, Cloneable {

    /**
     * A serialized array of uninterpreted bytes representing the
     * value of this <code>SerialBlob</code> object.
     * <p>
     *  表示此<serial> SerialBlob </code>对象的值的未解释字节的序列化数组。
     * 
     * 
     * @serial
     */
    private byte buf[];

    /**
     * The internal representation of the <code>Blob</code> object on which this
     * <code>SerialBlob</code> object is based.
     * <p>
     * SerialBlob </code>对象所基于的<code> Blob </code>对象的内部表示。
     * 
     */
    private Blob blob;

    /**
     * The number of bytes in this <code>SerialBlob</code> object's
     * array of bytes.
     * <p>
     *  在这个<code> SerialBlob </code>对象的字节数组中的字节数。
     * 
     * 
     * @serial
     */
    private long len;

    /**
     * The original number of bytes in this <code>SerialBlob</code> object's
     * array of bytes when it was first established.
     * <p>
     *  当首次建立时,这个<code> SerialBlob </code>对象的字节数组中的原始字节数。
     * 
     * 
     * @serial
     */
    private long origLen;

    /**
     * Constructs a <code>SerialBlob</code> object that is a serialized version of
     * the given <code>byte</code> array.
     * <p>
     * The new <code>SerialBlob</code> object is initialized with the data from the
     * <code>byte</code> array, thus allowing disconnected <code>RowSet</code>
     * objects to establish serialized <code>Blob</code> objects without
     * touching the data source.
     *
     * <p>
     *  构造一个<code> SerialBlob </code>对象,它是给定<code> byte </code>数组的序列化版本。
     * <p>
     *  新的<code> SerialBlob </code>对象用来自<code> byte </code>数组的数据初始化,因此允许断开<code> RowSet </code>对象来建立序列化的<code>
     *  Blob </code >对象而不触及数据源。
     * 
     * 
     * @param b the <code>byte</code> array containing the data for the
     *        <code>Blob</code> object to be serialized
     * @throws SerialException if an error occurs during serialization
     * @throws SQLException if a SQL errors occurs
     */
    public SerialBlob(byte[] b) throws SerialException, SQLException {

        len = b.length;
        buf = new byte[(int)len];
        for(int i = 0; i < len; i++) {
           buf[i] = b[i];
        }
        origLen = len;
    }


    /**
     * Constructs a <code>SerialBlob</code> object that is a serialized
     * version of the given <code>Blob</code> object.
     * <P>
     * The new <code>SerialBlob</code> object is initialized with the
     * data from the <code>Blob</code> object; therefore, the
     * <code>Blob</code> object should have previously brought the
     * SQL <code>BLOB</code> value's data over to the client from
     * the database. Otherwise, the new <code>SerialBlob</code> object
     * will contain no data.
     *
     * <p>
     *  构造一个<code> SerialBlob </code>对象,它是给定的<code> Blob </code>对象的一个​​序列化版本。
     * <P>
     *  新的<code> SerialBlob </code>对象用来自<code> Blob </code>对象的数据初始化;因此,<code> Blob </code>对象应该以前将SQL <code> 
     * BLOB </code>值的数据从数据库带到客户端。
     * 否则,新的<code> SerialBlob </code>对象将不包含数据。
     * 
     * 
     * @param blob the <code>Blob</code> object from which this
     *     <code>SerialBlob</code> object is to be constructed;
     *     cannot be null.
     * @throws SerialException if an error occurs during serialization
     * @throws SQLException if the <code>Blob</code> passed to this
     *     to this constructor is a <code>null</code>.
     * @see java.sql.Blob
     */
    public SerialBlob (Blob blob) throws SerialException, SQLException {

        if (blob == null) {
            throw new SQLException("Cannot instantiate a SerialBlob " +
                 "object with a null Blob object");
        }

        len = blob.length();
        buf = blob.getBytes(1, (int)len );
        this.blob = blob;

         //if ( len < 10240000)
         // len = 10240000;
        origLen = len;
    }

    /**
     * Copies the specified number of bytes, starting at the given
     * position, from this <code>SerialBlob</code> object to
     * another array of bytes.
     * <P>
     * Note that if the given number of bytes to be copied is larger than
     * the length of this <code>SerialBlob</code> object's array of
     * bytes, the given number will be shortened to the array's length.
     *
     * <p>
     *  从指定位置开始,从这个<code> SerialBlob </code>对象复制指定数量的字节到另一个字节数组。
     * <P>
     *  注意,如果要复制的给定字节数大于这个<code> SerialBlob </code>对象的字节数组的长度,给定的数字将被缩短为数组的长度。
     * 
     * 
     * @param pos the ordinal position of the first byte in this
     *            <code>SerialBlob</code> object to be copied;
     *            numbering starts at <code>1</code>; must not be less
     *            than <code>1</code> and must be less than or equal
     *            to the length of this <code>SerialBlob</code> object
     * @param length the number of bytes to be copied
     * @return an array of bytes that is a copy of a region of this
     *         <code>SerialBlob</code> object, starting at the given
     *         position and containing the given number of consecutive bytes
     * @throws SerialException if the given starting position is out of bounds;
     * if {@code free} had previously been called on this object
     */
    public byte[] getBytes(long pos, int length) throws SerialException {
        isValid();
        if (length > len) {
            length = (int)len;
        }

        if (pos < 1 || len - pos < 0 ) {
            throw new SerialException("Invalid arguments: position cannot be "
                    + "less than 1 or greater than the length of the SerialBlob");
        }

        pos--; // correct pos to array index

        byte[] b = new byte[length];

        for (int i = 0; i < length; i++) {
            b[i] = this.buf[(int)pos];
            pos++;
        }
        return b;
    }

    /**
     * Retrieves the number of bytes in this <code>SerialBlob</code>
     * object's array of bytes.
     *
     * <p>
     * 获取这个<code> SerialBlob </code>对象的字节数组中的字节数。
     * 
     * 
     * @return a <code>long</code> indicating the length in bytes of this
     *         <code>SerialBlob</code> object's array of bytes
     * @throws SerialException if an error occurs;
     * if {@code free} had previously been called on this object
     */
    public long length() throws SerialException {
        isValid();
        return len;
    }

    /**
     * Returns this <code>SerialBlob</code> object as an input stream.
     * Unlike the related method, <code>setBinaryStream</code>,
     * a stream is produced regardless of whether the <code>SerialBlob</code>
     * was created with a <code>Blob</code> object or a <code>byte</code> array.
     *
     * <p>
     *  返回这个<code> SerialBlob </code>对象作为输入流。
     * 与相关方法<code> setBinaryStream </code>不同,不管<code> SerialBlob </code>是用<code> Blob </code>对象还是<code> / co
     * de>数组。
     *  返回这个<code> SerialBlob </code>对象作为输入流。
     * 
     * 
     * @return a <code>java.io.InputStream</code> object that contains
     *         this <code>SerialBlob</code> object's array of bytes
     * @throws SerialException if an error occurs;
     * if {@code free} had previously been called on this object
     * @see #setBinaryStream
     */
    public java.io.InputStream getBinaryStream() throws SerialException {
        isValid();
        InputStream stream = new ByteArrayInputStream(buf);
        return stream;
    }

    /**
     * Returns the position in this <code>SerialBlob</code> object where
     * the given pattern of bytes begins, starting the search at the
     * specified position.
     *
     * <p>
     *  返回在这个<code> SerialBlob </code>对象中的位置,其中给定的字节模式开始,开始在指定位置搜索。
     * 
     * 
     * @param pattern the pattern of bytes for which to search
     * @param start the position of the byte in this
     *              <code>SerialBlob</code> object from which to begin
     *              the search; the first position is <code>1</code>;
     *              must not be less than <code>1</code> nor greater than
     *              the length of this <code>SerialBlob</code> object
     * @return the position in this <code>SerialBlob</code> object
     *         where the given pattern begins, starting at the specified
     *         position; <code>-1</code> if the pattern is not found
     *         or the given starting position is out of bounds; position
     *         numbering for the return value starts at <code>1</code>
     * @throws SerialException if an error occurs when serializing the blob;
     * if {@code free} had previously been called on this object
     * @throws SQLException if there is an error accessing the <code>BLOB</code>
     *         value from the database
     */
    public long position(byte[] pattern, long start)
                throws SerialException, SQLException {
        isValid();
        if (start < 1 || start > len) {
            return -1;
        }

        int pos = (int)start-1; // internally Blobs are stored as arrays.
        int i = 0;
        long patlen = pattern.length;

        while (pos < len) {
            if (pattern[i] == buf[pos]) {
                if (i + 1 == patlen) {
                    return (pos + 1) - (patlen - 1);
                }
                i++; pos++; // increment pos, and i
            } else if (pattern[i] != buf[pos]) {
                pos++; // increment pos only
            }
        }
        return -1; // not found
    }

    /**
     * Returns the position in this <code>SerialBlob</code> object where
     * the given <code>Blob</code> object begins, starting the search at the
     * specified position.
     *
     * <p>
     *  返回给定<code> Blob </code>对象开始的<code> SerialBlob </code>对象中的位置,开始在指定位置搜索。
     * 
     * 
     * @param pattern the <code>Blob</code> object for which to search;
     * @param start the position of the byte in this
     *              <code>SerialBlob</code> object from which to begin
     *              the search; the first position is <code>1</code>;
     *              must not be less than <code>1</code> nor greater than
     *              the length of this <code>SerialBlob</code> object
     * @return the position in this <code>SerialBlob</code> object
     *         where the given <code>Blob</code> object begins, starting
     *         at the specified position; <code>-1</code> if the pattern is
     *         not found or the given starting position is out of bounds;
     *         position numbering for the return value starts at <code>1</code>
     * @throws SerialException if an error occurs when serializing the blob;
     * if {@code free} had previously been called on this object
     * @throws SQLException if there is an error accessing the <code>BLOB</code>
     *         value from the database
     */
    public long position(Blob pattern, long start)
       throws SerialException, SQLException {
        isValid();
        return position(pattern.getBytes(1, (int)(pattern.length())), start);
    }

    /**
     * Writes the given array of bytes to the <code>BLOB</code> value that
     * this <code>Blob</code> object represents, starting at position
     * <code>pos</code>, and returns the number of bytes written.
     *
     * <p>
     *  将给定的字节数组写入这个<code> Blob </code>对象表示的<code> BLOB </code>值,从位置<code> pos </code>开始,并返回写入的字节数。
     * 
     * 
     * @param pos the position in the SQL <code>BLOB</code> value at which
     *     to start writing. The first position is <code>1</code>;
     *     must not be less than <code>1</code> nor greater than
     *     the length of this <code>SerialBlob</code> object.
     * @param bytes the array of bytes to be written to the <code>BLOB</code>
     *        value that this <code>Blob</code> object represents
     * @return the number of bytes written
     * @throws SerialException if there is an error accessing the
     *     <code>BLOB</code> value; or if an invalid position is set; if an
     *     invalid offset value is set;
     * if {@code free} had previously been called on this object
     * @throws SQLException if there is an error accessing the <code>BLOB</code>
     *         value from the database
     * @see #getBytes
     */
    public int setBytes(long pos, byte[] bytes)
        throws SerialException, SQLException {
        return (setBytes(pos, bytes, 0, bytes.length));
    }

    /**
     * Writes all or part of the given <code>byte</code> array to the
     * <code>BLOB</code> value that this <code>Blob</code> object represents
     * and returns the number of bytes written.
     * Writing starts at position <code>pos</code> in the <code>BLOB</code>
     * value; <i>len</i> bytes from the given byte array are written.
     *
     * <p>
     *  将给定<code> byte </code>数组的全部或部分写入此<b> </code>对象表示的<code> BLOB </code>值,并返回写入的字节数。
     * 写入从<code> BLOB </code>值中的<code> pos </code>位置开始;从给定字节数组写入<i> len </i>字节。
     * 
     * 
     * @param pos the position in the <code>BLOB</code> object at which
     *     to start writing. The first position is <code>1</code>;
     *     must not be less than <code>1</code> nor greater than
     *     the length of this <code>SerialBlob</code> object.
     * @param bytes the array of bytes to be written to the <code>BLOB</code>
     *     value
     * @param offset the offset in the <code>byte</code> array at which
     *     to start reading the bytes. The first offset position is
     *     <code>0</code>; must not be less than <code>0</code> nor greater
     *     than the length of the <code>byte</code> array
     * @param length the number of bytes to be written to the
     *     <code>BLOB</code> value from the array of bytes <i>bytes</i>.
     *
     * @return the number of bytes written
     * @throws SerialException if there is an error accessing the
     *     <code>BLOB</code> value; if an invalid position is set; if an
     *     invalid offset value is set; if number of bytes to be written
     *     is greater than the <code>SerialBlob</code> length; or the combined
     *     values of the length and offset is greater than the Blob buffer;
     * if {@code free} had previously been called on this object
     * @throws SQLException if there is an error accessing the <code>BLOB</code>
     *         value from the database.
     * @see #getBytes
     */
    public int setBytes(long pos, byte[] bytes, int offset, int length)
        throws SerialException, SQLException {

        isValid();
        if (offset < 0 || offset > bytes.length) {
            throw new SerialException("Invalid offset in byte array set");
        }

        if (pos < 1 || pos > this.length()) {
            throw new SerialException("Invalid position in BLOB object set");
        }

        if ((long)(length) > origLen) {
            throw new SerialException("Buffer is not sufficient to hold the value");
        }

        if ((length + offset) > bytes.length) {
            throw new SerialException("Invalid OffSet. Cannot have combined offset " +
                "and length that is greater that the Blob buffer");
        }

        int i = 0;
        pos--; // correct to array indexing
        while ( i < length || (offset + i +1) < (bytes.length-offset) ) {
            this.buf[(int)pos + i] = bytes[offset + i ];
            i++;
        }
        return i;
    }

    /**
     * Retrieves a stream that can be used to write to the <code>BLOB</code>
     * value that this <code>Blob</code> object represents.  The stream begins
     * at position <code>pos</code>. This method forwards the
     * <code>setBinaryStream()</code> call to the underlying <code>Blob</code> in
     * the event that this <code>SerialBlob</code> object is instantiated with a
     * <code>Blob</code>. If this <code>SerialBlob</code> is instantiated with
     * a <code>byte</code> array, a <code>SerialException</code> is thrown.
     *
     * <p>
     * 检索可用于写入此<b> <b> </code>对象表示的<code> BLOB </code>值的流。流开始于位置<code> pos </code>。
     * 在该<code> SerialBlob </code>对象被实例化为<code> Blob </code>的情况下,此方法将<code> setBinaryStream()</code>代码>。
     * 如果这个<code> SerialBlob </code>用一个<code> byte </code>数组实例化,则抛出一个<code> SerialException </code>。
     * 
     * 
     * @param pos the position in the <code>BLOB</code> value at which
     *        to start writing
     * @return a <code>java.io.OutputStream</code> object to which data can
     *         be written
     * @throws SQLException if there is an error accessing the
     *            <code>BLOB</code> value
     * @throws SerialException if the SerialBlob in not instantiated with a
     *     <code>Blob</code> object that supports <code>setBinaryStream()</code>;
     * if {@code free} had previously been called on this object
     * @see #getBinaryStream
     */
    public java.io.OutputStream setBinaryStream(long pos)
        throws SerialException, SQLException {
        isValid();
        if (this.blob != null) {
            return this.blob.setBinaryStream(pos);
        } else {
            throw new SerialException("Unsupported operation. SerialBlob cannot " +
                "return a writable binary stream, unless instantiated with a Blob object " +
                "that provides a setBinaryStream() implementation");
        }
    }

    /**
     * Truncates the <code>BLOB</code> value that this <code>Blob</code>
     * object represents to be <code>len</code> bytes in length.
     *
     * <p>
     *  截断<code> BLOB </code>值,这个<code> Blob </code>对象表示长度为<code> len </code>个字节。
     * 
     * 
     * @param length the length, in bytes, to which the <code>BLOB</code>
     *        value that this <code>Blob</code> object represents should be
     *        truncated
     * @throws SerialException if there is an error accessing the Blob value;
     *     or the length to truncate is greater that the SerialBlob length;
     * if {@code free} had previously been called on this object
     */
    public void truncate(long length) throws SerialException {

        isValid();
        if (length > len) {
           throw new SerialException
              ("Length more than what can be truncated");
        } else if((int)length == 0) {
             buf = new byte[0];
             len = length;
        } else {
             len = length;
             buf = this.getBytes(1, (int)len);
        }
    }


    /**
     * Returns an
     * <code>InputStream</code> object that contains a partial
     * {@code Blob} value, starting with the byte specified by pos, which is
     * length bytes in length.
     *
     * <p>
     *  返回一个包含部分{@code Blob}值的<code> InputStream </code>对象,从由pos指定的字节开始,长度为字节长度。
     * 
     * 
     * @param pos the offset to the first byte of the partial value to be
     * retrieved. The first byte in the {@code Blob} is at position 1
     * @param length the length in bytes of the partial value to be retrieved
     * @return
     * <code>InputStream</code> through which the partial {@code Blob} value can
     * be read.
     * @throws SQLException if pos is less than 1 or if pos is greater than the
     * number of bytes in the {@code Blob} or if pos + length is greater than
     * the number of bytes in the {@code Blob}
     * @throws SerialException if the {@code free} method had been previously
     * called on this object
     *
     * @since 1.6
     */
    public InputStream getBinaryStream(long pos, long length) throws SQLException {
        isValid();
        if (pos < 1 || pos > this.length()) {
            throw new SerialException("Invalid position in BLOB object set");
        }
        if (length < 1 || length > len - pos + 1) {
            throw new SerialException("length is < 1 or pos + length >"
                    + "total number of bytes");
        }
        return new ByteArrayInputStream(buf, (int) pos - 1, (int) length);
    }


    /**
     * This method frees the {@code SeriableBlob} object and releases the
     * resources that it holds. The object is invalid once the {@code free}
     * method is called. <p> If {@code free} is called multiple times, the
     * subsequent calls to {@code free} are treated as a no-op. </P>
     *
     * <p>
     *  此方法释放{@code SeriableBlob}对象并释放其保存的资源。调用{@code free}方法后,对象无效。
     *  <p>如果{@code free}被多次调用,则对{@code free}的后续调用将被视为无操作。 </P>。
     * 
     * 
     * @throws SQLException if an error occurs releasing the Blob's resources
     * @since 1.6
     */
    public void free() throws SQLException {
        if (buf != null) {
            buf = null;
            if (blob != null) {
                blob.free();
            }
            blob = null;
        }
    }

    /**
     * Compares this SerialBlob to the specified object.  The result is {@code
     * true} if and only if the argument is not {@code null} and is a {@code
     * SerialBlob} object that represents the same sequence of bytes as this
     * object.
     *
     * <p>
     *  将此SerialBlob与指定的对象进行比较。结果是{@code true}当且仅当参数不是{@code null},并且是一个代表与此对象相同的字节序列的{@code SerialBlob}对象。
     * 
     * 
     * @param  obj The object to compare this {@code SerialBlob} against
     *
     * @return {@code true} if the given object represents a {@code SerialBlob}
     *          equivalent to this SerialBlob, {@code false} otherwise
     *
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SerialBlob) {
            SerialBlob sb = (SerialBlob)obj;
            if (this.len == sb.len) {
                return Arrays.equals(buf, sb.buf);
            }
        }
        return false;
    }

    /**
     * Returns a hash code for this {@code SerialBlob}.
     * <p>
     *  返回此{@code SerialBlob}的哈希码。
     * 
     * 
     * @return  a hash code value for this object.
     */
    public int hashCode() {
       return ((31 + Arrays.hashCode(buf)) * 31 + (int)len) * 31 + (int)origLen;
    }

    /**
     * Returns a clone of this {@code SerialBlob}. The copy will contain a
     * reference to a clone of the internal byte array, not a reference
     * to the original internal byte array of this {@code SerialBlob} object.
     * The underlying {@code Blob} object will be set to null.
     *
     * <p>
     * 返回此{@code SerialBlob}的克隆。副本将包含对内部字节数组的克隆的引用,而不是对此{@code SerialBlob}对象的原始内部字节数组的引用。
     * 底层的{@code Blob}对象将设置为null。
     * 
     * 
     * @return  a clone of this SerialBlob
     */
    public Object clone() {
        try {
            SerialBlob sb = (SerialBlob) super.clone();
            sb.buf =  (buf != null) ? Arrays.copyOf(buf, (int)len) : null;
            sb.blob = null;
            return sb;
        } catch (CloneNotSupportedException ex) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }

    }

    /**
     * readObject is called to restore the state of the SerialBlob from
     * a stream.
     * <p>
     *  readObject被调用以从流中恢复SerialBlob的状态。
     * 
     */
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {

        ObjectInputStream.GetField fields = s.readFields();
       byte[] tmp = (byte[])fields.get("buf", null);
       if (tmp == null)
           throw new InvalidObjectException("buf is null and should not be!");
       buf = tmp.clone();
       len = fields.get("len", 0L);
       if (buf.length != len)
           throw new InvalidObjectException("buf is not the expected size");
       origLen = fields.get("origLen", 0L);
       blob = (Blob) fields.get("blob", null);
    }

    /**
     * writeObject is called to save the state of the SerialBlob
     * to a stream.
     * <p>
     *  writeObject被调用以将SerialBlob的状态保存到流。
     * 
     */
    private void writeObject(ObjectOutputStream s)
            throws IOException, ClassNotFoundException {

        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("buf", buf);
        fields.put("len", len);
        fields.put("origLen", origLen);
        // Note: this check to see if it is an instance of Serializable
        // is for backwards compatibiity
        fields.put("blob", blob instanceof Serializable ? blob : null);
        s.writeFields();
    }

    /**
     * Check to see if this object had previously had its {@code free} method
     * called
     *
     * <p>
     *  检查此对象之前是否已调用其{@code free}方法
     * 
     * 
     * @throws SerialException
     */
    private void isValid() throws SerialException {
        if (buf == null) {
            throw new SerialException("Error: You cannot call a method on a "
                    + "SerialBlob instance once free() has been called.");
        }
    }

    /**
     * The identifier that assists in the serialization of this
     * {@code SerialBlob} object.
     * <p>
     *  有助于序列化此{@code SerialBlob}对象的标识符。
     */
    static final long serialVersionUID = -8144641928112860441L;
}
