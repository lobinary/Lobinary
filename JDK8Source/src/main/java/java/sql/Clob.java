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

import java.io.Reader;

/**
 * The mapping in the Java&trade; programming language
 * for the SQL <code>CLOB</code> type.
 * An SQL <code>CLOB</code> is a built-in type
 * that stores a Character Large Object as a column value in a row of
 * a database table.
 * By default drivers implement a <code>Clob</code> object using an SQL
 * <code>locator(CLOB)</code>, which means that a <code>Clob</code> object
 * contains a logical pointer to the SQL <code>CLOB</code> data rather than
 * the data itself. A <code>Clob</code> object is valid for the duration
 * of the transaction in which it was created.
 * <P>The <code>Clob</code> interface provides methods for getting the
 * length of an SQL <code>CLOB</code> (Character Large Object) value,
 * for materializing a <code>CLOB</code> value on the client, and for
 * searching for a substring or <code>CLOB</code> object within a
 * <code>CLOB</code> value.
 * Methods in the interfaces {@link ResultSet},
 * {@link CallableStatement}, and {@link PreparedStatement}, such as
 * <code>getClob</code> and <code>setClob</code> allow a programmer to
 * access an SQL <code>CLOB</code> value.  In addition, this interface
 * has methods for updating a <code>CLOB</code> value.
 * <p>
 * All methods on the <code>Clob</code> interface must be fully implemented if the
 * JDBC driver supports the data type.
 *
 * <p>
 *  Java和trade中的映射;用于SQL <code> CLOB </code>类型的编程语言。
 *  SQL <code> CLOB </code>是一种内置类型,它将字符大对象作为列值存储在数据库表的行中。
 * 默认情况下,驱动程序使用SQL <code>定位器(CLOB)</code>实现<code> Clob </code>对象,这意味着<code> Clob </code>对象包含一个指向SQL <代码>
 *  CLOB </code>数据,而不是数据本身。
 *  SQL <code> CLOB </code>是一种内置类型,它将字符大对象作为列值存储在数据库表的行中。 <code> Clob </code>对象在创建它的事务的有效期内有效。
 *  <p> <code> Clob </code>接口提供了获取SQL <code> CLOB </code>(Character Large Object)值长度的方法,用于实现<code> CLOB 
 * </code>客户端,并且用于在<code> CLOB </code>值内搜索子串或<code> CLOB </code>对象。
 *  SQL <code> CLOB </code>是一种内置类型,它将字符大对象作为列值存储在数据库表的行中。 <code> Clob </code>对象在创建它的事务的有效期内有效。
 * 接口{@link ResultSet},{@link CallableStatement}和{@link PreparedStatement}中的方法(例如<code> getClob </code>和
 * <code> setClob </code>)允许程序员访问SQL <代码> CLOB </code>值。
 *  SQL <code> CLOB </code>是一种内置类型,它将字符大对象作为列值存储在数据库表的行中。 <code> Clob </code>对象在创建它的事务的有效期内有效。
 * 此外,该接口具有用于更新<code> CLOB </code>值的方法。
 * <p>
 *  如果JDBC驱动程序支持数据类型,则必须完全实现<code> Clob </code>接口上的所有方法。
 * 
 * 
 * @since 1.2
 */

public interface Clob {

  /**
   * Retrieves the number of characters
   * in the <code>CLOB</code> value
   * designated by this <code>Clob</code> object.
   *
   * <p>
   * 检索由此<c> clob </code>对象指定的<code> CLOB </code>值中的字符数。
   * 
   * 
   * @return length of the <code>CLOB</code> in characters
   * @exception SQLException if there is an error accessing the
   *            length of the <code>CLOB</code> value
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  long length() throws SQLException;

  /**
   * Retrieves a copy of the specified substring
   * in the <code>CLOB</code> value
   * designated by this <code>Clob</code> object.
   * The substring begins at position
   * <code>pos</code> and has up to <code>length</code> consecutive
   * characters.
   *
   * <p>
   *  检索由<code> Clob </code>对象指定的<code> CLOB </code>值中指定子字符串的副本。
   * 子串从<code> pos </code>开始,并且最多具有<code> length </code>个连续字符。
   * 
   * 
   * @param pos the first character of the substring to be extracted.
   *            The first character is at position 1.
   * @param length the number of consecutive characters to be copied;
   * the value for length must be 0 or greater
   * @return a <code>String</code> that is the specified substring in
   *         the <code>CLOB</code> value designated by this <code>Clob</code> object
   * @exception SQLException if there is an error accessing the
   *            <code>CLOB</code> value; if pos is less than 1 or length is
   * less than 0
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  String getSubString(long pos, int length) throws SQLException;

  /**
   * Retrieves the <code>CLOB</code> value designated by this <code>Clob</code>
   * object as a <code>java.io.Reader</code> object (or as a stream of
   * characters).
   *
   * <p>
   *  撷取由<code> Clob </code>物件指定为<code> java.io.Reader </code>物件(或字元串)的<code> CLOB </code>值。
   * 
   * 
   * @return a <code>java.io.Reader</code> object containing the
   *         <code>CLOB</code> data
   * @exception SQLException if there is an error accessing the
   *            <code>CLOB</code> value
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @see #setCharacterStream
   * @since 1.2
   */
  java.io.Reader getCharacterStream() throws SQLException;

  /**
   * Retrieves the <code>CLOB</code> value designated by this <code>Clob</code>
   * object as an ascii stream.
   *
   * <p>
   *  以ascii流的形式获取由此<c> clob </code>对象指定的<code> CLOB </code>值。
   * 
   * 
   * @return a <code>java.io.InputStream</code> object containing the
   *         <code>CLOB</code> data
   * @exception SQLException if there is an error accessing the
   *            <code>CLOB</code> value
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @see #setAsciiStream
   * @since 1.2
   */
  java.io.InputStream getAsciiStream() throws SQLException;

  /**
   * Retrieves the character position at which the specified substring
   * <code>searchstr</code> appears in the SQL <code>CLOB</code> value
   * represented by this <code>Clob</code> object.  The search
   * begins at position <code>start</code>.
   *
   * <p>
   *  检索此<c> </code>对象所表示的SQL <code> CLOB </code>值中指定的子字符串<code> searchstr </code>搜索从<code> start </code>开
   * 始。
   * 
   * 
   * @param searchstr the substring for which to search
   * @param start the position at which to begin searching; the first position
   *              is 1
   * @return the position at which the substring appears or -1 if it is not
   *         present; the first position is 1
   * @exception SQLException if there is an error accessing the
   *            <code>CLOB</code> value or if pos is less than 1
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  long position(String searchstr, long start) throws SQLException;

  /**
   * Retrieves the character position at which the specified
   * <code>Clob</code> object <code>searchstr</code> appears in this
   * <code>Clob</code> object.  The search begins at position
   * <code>start</code>.
   *
   * <p>
   *  检索指定的<code> Clob </code>对象<code> searchstr </code>在此<c> clob </code>对象中出现的字符位置。
   * 搜索从<code> start </code>开始。
   * 
   * 
   * @param searchstr the <code>Clob</code> object for which to search
   * @param start the position at which to begin searching; the first
   *              position is 1
   * @return the position at which the <code>Clob</code> object appears
   *              or -1 if it is not present; the first position is 1
   * @exception SQLException if there is an error accessing the
   *            <code>CLOB</code> value or if start is less than 1
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.2
   */
  long position(Clob searchstr, long start) throws SQLException;

    //---------------------------- jdbc 3.0 -----------------------------------

    /**
     * Writes the given Java <code>String</code> to the <code>CLOB</code>
     * value that this <code>Clob</code> object designates at the position
     * <code>pos</code>. The string will overwrite the existing characters
     * in the <code>Clob</code> object starting at the position
     * <code>pos</code>.  If the end of the <code>Clob</code> value is reached
     * while writing the given string, then the length of the <code>Clob</code>
     * value will be increased to accommodate the extra characters.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>CLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     *  将给定的Java <code> String </code>写入此<c> </code>对象在<code> pos </code>位置处指定的<code> CLOB </code>值。
     * 该字符串将覆盖从<code> pos </code>位置开始的<code> Clob </code>对象中的现有字符。
     * 如果在写入给定字符串时达到<code> Clob </code>值的结尾,则<code> Clob </code>值的长度将增加以容纳额外的字符。
     * <p>
     * <b>注意：</b>如果<code> pos </code>指定的值大于<code> CLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param pos the position at which to start writing to the <code>CLOB</code>
     *         value that this <code>Clob</code> object represents;
     * The first position is 1
     * @param str the string to be written to the <code>CLOB</code>
     *        value that this <code>Clob</code> designates
     * @return the number of characters written
     * @exception SQLException if there is an error accessing the
     *            <code>CLOB</code> value or if pos is less than 1
     *
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    int setString(long pos, String str) throws SQLException;

    /**
     * Writes <code>len</code> characters of <code>str</code>, starting
     * at character <code>offset</code>, to the <code>CLOB</code> value
     * that this <code>Clob</code> represents.  The string will overwrite the existing characters
     * in the <code>Clob</code> object starting at the position
     * <code>pos</code>.  If the end of the <code>Clob</code> value is reached
     * while writing the given string, then the length of the <code>Clob</code>
     * value will be increased to accommodate the extra characters.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>CLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     *  从<code> offset </code>开始,将<code> len </code>字符写入<code> CLOB </code> / code>表示。
     * 该字符串将覆盖从<code> pos </code>位置开始的<code> Clob </code>对象中的现有字符。
     * 如果在写入给定字符串时达到<code> Clob </code>值的结尾,则<code> Clob </code>值的长度将增加以容纳额外的字符。
     * <p>
     *  <b>注意：</b>如果<code> pos </code>指定的值大于<code> CLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param pos the position at which to start writing to this
     *        <code>CLOB</code> object; The first position  is 1
     * @param str the string to be written to the <code>CLOB</code>
     *        value that this <code>Clob</code> object represents
     * @param offset the offset into <code>str</code> to start reading
     *        the characters to be written
     * @param len the number of characters to be written
     * @return the number of characters written
     * @exception SQLException if there is an error accessing the
     *            <code>CLOB</code> value or if pos is less than 1
     *
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    int setString(long pos, String str, int offset, int len) throws SQLException;

    /**
     * Retrieves a stream to be used to write Ascii characters to the
     * <code>CLOB</code> value that this <code>Clob</code> object represents,
     * starting at position <code>pos</code>.  Characters written to the stream
     * will overwrite the existing characters
     * in the <code>Clob</code> object starting at the position
     * <code>pos</code>.  If the end of the <code>Clob</code> value is reached
     * while writing characters to the stream, then the length of the <code>Clob</code>
     * value will be increased to accommodate the extra characters.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>CLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     * 从位置<code> pos </code>开始,检索要用于将Ascii字符写入此<c> </code>对象表示的<code> CLOB </code>值的流。
     * 写入流的字符将覆盖从<code> pos </code>位置开始的<code> Clob </code>对象中的现有字符。
     * 如果在向流写入字符时达到<code> Clob </code>值的结尾,则会增加<code> Clob </code>值的长度以容纳额外的字符。
     * <p>
     *  <b>注意：</b>如果<code> pos </code>指定的值大于<code> CLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param pos the position at which to start writing to this
     *        <code>CLOB</code> object; The first position is 1
     * @return the stream to which ASCII encoded characters can be written
     * @exception SQLException if there is an error accessing the
     *            <code>CLOB</code> value or if pos is less than 1
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getAsciiStream
     *
     * @since 1.4
     */
    java.io.OutputStream setAsciiStream(long pos) throws SQLException;

    /**
     * Retrieves a stream to be used to write a stream of Unicode characters
     * to the <code>CLOB</code> value that this <code>Clob</code> object
     * represents, at position <code>pos</code>. Characters written to the stream
     * will overwrite the existing characters
     * in the <code>Clob</code> object starting at the position
     * <code>pos</code>.  If the end of the <code>Clob</code> value is reached
     * while writing characters to the stream, then the length of the <code>Clob</code>
     * value will be increased to accommodate the extra characters.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>CLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     *  检索用于将Unicode字符流写入此代码<code> Clob </code>所代表的<code> CLOB </code>值的流的位置<code> pos </code>。
     * 写入流的字符将覆盖从<code> pos </code>位置开始的<code> Clob </code>对象中的现有字符。
     * 如果在向流写入字符时达到<code> Clob </code>值的结尾,则会增加<code> Clob </code>值的长度以容纳额外的字符。
     * <p>
     * <b>注意：</b>如果<code> pos </code>指定的值大于<code> CLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param  pos the position at which to start writing to the
     *        <code>CLOB</code> value; The first position is 1
     *
     * @return a stream to which Unicode encoded characters can be written
     * @exception SQLException if there is an error accessing the
     *            <code>CLOB</code> value or if pos is less than 1
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getCharacterStream
     *
     * @since 1.4
     */
    java.io.Writer setCharacterStream(long pos) throws SQLException;

    /**
     * Truncates the <code>CLOB</code> value that this <code>Clob</code>
     * designates to have a length of <code>len</code>
     * characters.
     * <p>
     * <b>Note:</b> If the value specified for <code>pos</code>
     * is greater then the length+1 of the <code>CLOB</code> value then the
     * behavior is undefined. Some JDBC drivers may throw a
     * <code>SQLException</code> while other drivers may support this
     * operation.
     *
     * <p>
     *  截断此代码<code> Clob </code>指定的长度为<code> len </code>字符的<code> CLOB </code>
     * <p>
     *  <b>注意：</b>如果<code> pos </code>指定的值大于<code> CLOB </code>值的长度+1,那么行为是未定义的。
     * 一些JDBC驱动程序可能会抛出一个<code> SQLException </code>,而其他驱动程序可能支持此操作。
     * 
     * 
     * @param len the length, in characters, to which the <code>CLOB</code> value
     *        should be truncated
     * @exception SQLException if there is an error accessing the
     *            <code>CLOB</code> value or if len is less than 0
     *
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void truncate(long len) throws SQLException;

    /**
     * This method frees the <code>Clob</code> object and releases the resources the resources
     * that it holds.  The object is invalid once the <code>free</code> method
     * is called.
     * <p>
     * After <code>free</code> has been called, any attempt to invoke a
     * method other than <code>free</code> will result in a <code>SQLException</code>
     * being thrown.  If <code>free</code> is called multiple times, the subsequent
     * calls to <code>free</code> are treated as a no-op.
     * <p>
     * <p>
     *  此方法释放<code> Clob </code>对象并释放其拥有的资源。一旦调用<code> free </code>方法,对象就无效。
     * <p>
     *  在调用<code> free </code>之后,任何试图调用<code> free </code>以外的方法都会导致抛出<code> SQLException </code>。
     * 如果多次调用<code> free </code>,则对<code> free </code>的后续调用将被视为无操作。
     * 
     * @throws SQLException if an error occurs releasing
     * the Clob's resources
     *
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void free() throws SQLException;

    /**
     * Returns a <code>Reader</code> object that contains a partial <code>Clob</code> value, starting
     * with the character specified by pos, which is length characters in length.
     *
     * <p>
     * <p>
     * 
     * @param pos the offset to the first character of the partial value to
     * be retrieved.  The first character in the Clob is at position 1.
     * @param length the length in characters of the partial value to be retrieved.
     * @return <code>Reader</code> through which the partial <code>Clob</code> value can be read.
     * @throws SQLException if pos is less than 1 or if pos is greater than the number of
     * characters in the <code>Clob</code> or if pos + length is greater than the number of
     * characters in the <code>Clob</code>
     *
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    Reader getCharacterStream(long pos, long length) throws SQLException;

}
