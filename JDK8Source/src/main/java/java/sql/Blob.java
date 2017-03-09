/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.sql;

import java.io.InputStream;

/**
 * The representation (mapping) in
 * the Java&trade; programming
 * language of an SQL
 * <code>BLOB</code> value.  An SQL <code>BLOB</code> is a built-in type
 * that stores a Binary Large Object as a column value in a row of
 * a database table. By default drivers implement <code>Blob</code> using
 * an SQL <code>locator(BLOB)</code>, which means that a
 * <code>Blob</code> object contains a logical pointer to the
 * SQL <code>BLOB</code> data rather than the data itself.
 * A <code>Blob</code> object is valid for the duration of the
 * transaction in which is was created.
 *
 * <P>Methods in the interfaces {@link ResultSet},
 * {@link CallableStatement}, and {@link PreparedStatement}, such as
 * <code>getBlob</code> and <code>setBlob</code> allow a programmer to
 * access an SQL <code>BLOB</code> value.
 * The <code>Blob</code> interface provides methods for getting the
 * length of an SQL <code>BLOB</code> (Binary Large Object) value,
 * for materializing a <code>BLOB</code> value on the client, and for
 * determining the position of a pattern of bytes within a
 * <code>BLOB</code> value. In addition, this interface has methods for updating
 * a <code>BLOB</code> value.
 * <p>
 * All methods on the <code>Blob</code> interface must be fully implemented if the
 * JDBC driver supports the data type.
 *
 * <p>
 *  Java和贸易中的表示(映射) SQL <code> BLOB </code>值的编程语言。
 *  SQL <code> BLOB </code>是一种内置类型,它将二进制大对象作为列值存储在数据库表的行中。
 * 默认驱动程序使用SQL <code>定位器(BLOB)</code>实现<code> Blob </code>,这意味着<code> Blob </code>对象包含SQL < BLOB </code>
 * 数据,而不是数据本身。
 *  SQL <code> BLOB </code>是一种内置类型,它将二进制大对象作为列值存储在数据库表的行中。 <code> Blob </code>对象在创建其中的事务的有效期内有效。
 * 
 *  <P>接口{@link ResultSet},{@link CallableStatement}和{@link PreparedStatement}中的方法(如<code> getBlob </code>
 * 和<code> setBlob </code>)允许程序员访问一个SQL <code> BLOB </code>值。
 *  <code> Blob </code>接口提供了获取SQL <code> BLOB </code>(二进制大对象)值长度的方法,用于在客户端上实现<code> BLOB </code>并且用于确定<code>
 *  BLOB </code>值内的字节模式的位置。
 * 此外,该接口具有用于更新<code> BLOB </code>值的方法。
 * <p>
 *  如果JDBC驱动程序支持数据类型,则必须完全实现<code> Blob </code>接口上的所有方法。
 * 
 * 
 * @since 1.2
 */

public interface Blob {

  /**
   * Returns the number of bytes in the <code>BLOB</code> value
   * designated by this <code>Blob</code> object.
   * <p>
   *  返回由<code> Blob </code>对象指定的<code> BLOB </code>值中的字节数。
   * 
   * 
   * @return length of the <code>BLOB</code> in bytes
   * @exception SQLException if there is an error accessing the
   * length of the <code>BLOB</code>
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  long length() throws SQLException;

  /**
   * Retrieves all or part of the <code>BLOB</code>
   * value that this <code>Blob</code> object represents, as an array of
   * bytes.  This <code>byte</code> array contains up to <code>length</code>
   * consecutive bytes starting at position <code>pos</code>.
   *
   * <p>
   * 撷取这个<code> Blob </code>物件代表的全部或部分<code> BLOB </code>值,作为一个字节数组。
   * 这个<code> byte </code>数组包含从<code> pos </code>开始的<code> length </code>个连续字节。
   * 
   * 
   * @param pos the ordinal position of the first byte in the
   *        <code>BLOB</code> value to be extracted; the first byte is at
   *        position 1
   * @param length the number of consecutive bytes to be copied; the value
   * for length must be 0 or greater
   * @return a byte array containing up to <code>length</code>
   *         consecutive bytes from the <code>BLOB</code> value designated
   *         by this <code>Blob</code> object, starting with the
   *         byte at position <code>pos</code>
   * @exception SQLException if there is an error accessing the
   *            <code>BLOB</code> value; if pos is less than 1 or length is
   * less than 0
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @see #setBytes
   * @since 1.2
   */
  byte[] getBytes(long pos, int length) throws SQLException;

  /**
   * Retrieves the <code>BLOB</code> value designated by this
   * <code>Blob</code> instance as a stream.
   *
   * <p>
   *  撷取由此<code> Blob </code>实例指定为作为流的<code> BLOB </code>值。
   * 
   * 
   * @return a stream containing the <code>BLOB</code> data
   * @exception SQLException if there is an error accessing the
   *            <code>BLOB</code> value
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @see #setBinaryStream
   * @since 1.2
   */
  java.io.InputStream getBinaryStream () throws SQLException;

  /**
   * Retrieves the byte position at which the specified byte array
   * <code>pattern</code> begins within the <code>BLOB</code>
   * value that this <code>Blob</code> object represents.  The
   * search for <code>pattern</code> begins at position
   * <code>start</code>.
   *
   * <p>
   *  检索指定的字节数组<code>模式</code>在<code> Blob </code>对象表示的<code> BLOB </code>值中开始的字节位置。
   * 搜索<code> pattern </code>开始于<code> start </code>。
   * 
   * 
   * @param pattern the byte array for which to search
   * @param start the position at which to begin searching; the
   *        first position is 1
   * @return the position at which the pattern appears, else -1
   * @exception SQLException if there is an error accessing the
   * <code>BLOB</code> or if start is less than 1
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  long position(byte pattern[], long start) throws SQLException;

  /**
   * Retrieves the byte position in the <code>BLOB</code> value
   * designated by this <code>Blob</code> object at which
   * <code>pattern</code> begins.  The search begins at position
   * <code>start</code>.
   *
   * <p>
   *  检索由<code>模式</code>开始的<code> Blob </code>对象指定的<code> BLOB </code>值中的字节位置。搜索从<code> start </code>开始。
   * 
   * 
   * @param pattern the <code>Blob</code> object designating
   * the <code>BLOB</code> value for which to search
   * @param start the position in the <code>BLOB</code> value
   *        at which to begin searching; the first position is 1
   * @return the position at which the pattern begins, else -1
   * @exception SQLException if there is an error accessing the
   *            <code>BLOB</code> value or if start is less than 1
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  long position(Blob pattern, long start) throws SQLException;

    // -------------------------- JDBC 3.0 -----------------------------------

    /**
     * Writes the given array of bytes to the <code>BLOB</code> value that
     * this <code>Blob</code> object represents, starting at position
     * <code>pos</code>, and returns the number of bytes written.
     * The array of bytes will overwrite the existing bytes
     * in the <code>Blob</code> object starting at the position
     * <code>pos</code>.  If the end of the <code>Blob</code> value is reached
     * while writing the array of bytes, then the length of the <code>Blob</code>
     * value will be increased to accommodate the extra bytes.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>BLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     *  将给定的字节数组写入这个<code> Blob </code>对象表示的<code> BLOB </code>值,从位置<code> pos </code>开始,并返回写入的字节数。
     * 字节数组将覆盖<code> Blob </code>对象中起始于<code> pos </code>位置的现有字节。
     * 如果在写入字节数组时达到<code> Blob </code>值的结尾,则<code> Blob </code>值的长度将增加以容纳额外的字节。
     * <p>
     * <b>注意：</b>如果<code> pos </code>指定的值大于<code> BLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param pos the position in the <code>BLOB</code> object at which
     *        to start writing; the first position is 1
     * @param bytes the array of bytes to be written to the <code>BLOB</code>
     *        value that this <code>Blob</code> object represents
     * @return the number of bytes written
     * @exception SQLException if there is an error accessing the
     *            <code>BLOB</code> value or if pos is less than 1
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getBytes
     * @since 1.4
     */
    int setBytes(long pos, byte[] bytes) throws SQLException;

    /**
     * Writes all or part of the given <code>byte</code> array to the
     * <code>BLOB</code> value that this <code>Blob</code> object represents
     * and returns the number of bytes written.
     * Writing starts at position <code>pos</code> in the <code>BLOB</code>
     * value; <code>len</code> bytes from the given byte array are written.
     * The array of bytes will overwrite the existing bytes
     * in the <code>Blob</code> object starting at the position
     * <code>pos</code>.  If the end of the <code>Blob</code> value is reached
     * while writing the array of bytes, then the length of the <code>Blob</code>
     * value will be increased to accommodate the extra bytes.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>BLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     *  将给定<code> byte </code>数组的全部或部分写入此<b> </code>对象表示的<code> BLOB </code>值,并返回写入的字节数。
     * 写入从<code> BLOB </code>值中的<code> pos </code>位置开始;来自给定字节数组的<code> len </code>字节被写入。
     * 字节数组将覆盖<code> Blob </code>对象中起始于<code> pos </code>位置的现有字节。
     * 如果在写入字节数组时达到<code> Blob </code>值的结尾,则<code> Blob </code>值的长度将增加以容纳额外的字节。
     * <p>
     *  <b>注意：</b>如果<code> pos </code>指定的值大于<code> BLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param pos the position in the <code>BLOB</code> object at which
     *        to start writing; the first position is 1
     * @param bytes the array of bytes to be written to this <code>BLOB</code>
     *        object
     * @param offset the offset into the array <code>bytes</code> at which
     *        to start reading the bytes to be set
     * @param len the number of bytes to be written to the <code>BLOB</code>
     *        value from the array of bytes <code>bytes</code>
     * @return the number of bytes written
     * @exception SQLException if there is an error accessing the
     *            <code>BLOB</code> value or if pos is less than 1
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getBytes
     * @since 1.4
     */
    int setBytes(long pos, byte[] bytes, int offset, int len) throws SQLException;

    /**
     * Retrieves a stream that can be used to write to the <code>BLOB</code>
     * value that this <code>Blob</code> object represents.  The stream begins
     * at position <code>pos</code>.
     * The  bytes written to the stream will overwrite the existing bytes
     * in the <code>Blob</code> object starting at the position
     * <code>pos</code>.  If the end of the <code>Blob</code> value is reached
     * while writing to the stream, then the length of the <code>Blob</code>
     * value will be increased to accommodate the extra bytes.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>BLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     * 检索可用于写入此<b> <b> </code>对象表示的<code> BLOB </code>值的流。流开始于位置<code> pos </code>。
     * 写入流的字节将覆盖从<code> pos </code>位置开始的<code> Blob </code>对象中的现有字节。
     * 如果在写入流时达到<code> Blob </code>值的结尾,则<code> Blob </code>值的长度将增加以容纳额外的字节。
     * <p>
     *  <b>注意：</b>如果<code> pos </code>指定的值大于<code> BLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param pos the position in the <code>BLOB</code> value at which
     *        to start writing; the first position is 1
     * @return a <code>java.io.OutputStream</code> object to which data can
     *         be written
     * @exception SQLException if there is an error accessing the
     *            <code>BLOB</code> value or if pos is less than 1
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getBinaryStream
     * @since 1.4
     */
    java.io.OutputStream setBinaryStream(long pos) throws SQLException;

    /**
     * Truncates the <code>BLOB</code> value that this <code>Blob</code>
     * object represents to be <code>len</code> bytes in length.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>BLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     *  截断<code> BLOB </code>值,这个<code> Blob </code>对象表示长度为<code> len </code>个字节。
     * <p>
     *  <b>注意：</b>如果<code> pos </code>指定的值大于<code> BLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param len the length, in bytes, to which the <code>BLOB</code> value
     *        that this <code>Blob</code> object represents should be truncated
     * @exception SQLException if there is an error accessing the
     *            <code>BLOB</code> value or if len is less than 0
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void truncate(long len) throws SQLException;

    /**
     * This method frees the <code>Blob</code> object and releases the resources that
     * it holds. The object is invalid once the <code>free</code>
     * method is called.
     *<p>
     * After <code>free</code> has been called, any attempt to invoke a
     * method other than <code>free</code> will result in a <code>SQLException</code>
     * being thrown.  If <code>free</code> is called multiple times, the subsequent
     * calls to <code>free</code> are treated as a no-op.
     *<p>
     *
     * <p>
     *  此方法释放<code> Blob </code>对象并释放其保存的资源。一旦调用<code> free </code>方法,对象就无效。
     * p>
     * 在调用<code> free </code>之后,任何调用除<code> free </code>之外的方法的尝试都会导致抛出<code> SQLException </code>。
     * 如果多次调用<code> free </code>,则对<code> free </code>的后续调用将被视为无操作。
     * p>
     * 
     * @throws SQLException if an error occurs releasing
     * the Blob's resources
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void free() throws SQLException;

    /**
     * Returns an <code>InputStream</code> object that contains a partial <code>Blob</code> value,
     * starting  with the byte specified by pos, which is length bytes in length.
     *
     * <p>
     * 
     * 
     * @param pos the offset to the first byte of the partial value to be retrieved.
     *  The first byte in the <code>Blob</code> is at position 1
     * @param length the length in bytes of the partial value to be retrieved
     * @return <code>InputStream</code> through which the partial <code>Blob</code> value can be read.
     * @throws SQLException if pos is less than 1 or if pos is greater than the number of bytes
     * in the <code>Blob</code> or if pos + length is greater than the number of bytes
     * in the <code>Blob</code>
     *
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    InputStream getBinaryStream(long pos, long length) throws SQLException;
}
