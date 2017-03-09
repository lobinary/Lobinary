/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql;

import java.sql.*;

/**
 * The interface that a <code>RowSet</code> object implements in order to
 * present itself to a <code>RowSetReader</code> or <code>RowSetWriter</code>
 * object. The <code>RowSetInternal</code> interface contains
 * methods that let the reader or writer access and modify the internal
 * state of the rowset.
 *
 * <p>
 *  <code> RowSet </code>对象实现的接口,以便将自身呈现给一个<code> RowSetReader </code>或<code> RowSetWriter </code>对象。
 *  <code> RowSetInternal </code>接口包含允许读写器访问和修改行集的内部状态的方法。
 * 
 * 
 * @since 1.4
 */

public interface RowSetInternal {

  /**
   * Retrieves the parameters that have been set for this
   * <code>RowSet</code> object's command.
   *
   * <p>
   *  检索已为此<code> RowSet </code>对象的命令设置的参数。
   * 
   * 
   * @return an array of the current parameter values for this <code>RowSet</code>
   *         object's command
   * @exception SQLException if a database access error occurs
   */
  Object[] getParams() throws SQLException;

  /**
   * Retrieves the <code>Connection</code> object that was passed to this
   * <code>RowSet</code> object.
   *
   * <p>
   *  检索传递给此<code> RowSet </code>对象的<code> Connection </code>对象。
   * 
   * 
   * @return the <code>Connection</code> object passed to the rowset
   *      or <code>null</code> if none was passed
   * @exception SQLException if a database access error occurs
   */
  Connection getConnection() throws SQLException;

  /**
   * Sets the given <code>RowSetMetaData</code> object as the
   * <code>RowSetMetaData</code> object for this <code>RowSet</code>
   * object. The <code>RowSetReader</code> object associated with the rowset
   * will use <code>RowSetMetaData</code> methods to set the values giving
   * information about the rowset's columns.
   *
   * <p>
   *  将给定的<code> RowSetMetaData </code>对象设置为<code> RowSet </code>对象的<code> RowSetMetaData </code>对象。
   * 与行集关联的<code> RowSetReader </code>对象将使用<code> RowSetMetaData </code>方法设置提供有关行集的列的信息的值。
   * 
   * 
   * @param md the <code>RowSetMetaData</code> object that will be set with
   *        information about the rowset's columns
   *
   * @exception SQLException if a database access error occurs
   */
  void setMetaData(RowSetMetaData md) throws SQLException;

  /**
   * Retrieves a <code>ResultSet</code> object containing the original
   * value of this <code>RowSet</code> object.
   * <P>
   * The cursor is positioned before the first row in the result set.
   * Only rows contained in the result set returned by the method
   * <code>getOriginal</code> are said to have an original value.
   *
   * <p>
   *  检索包含此<code> RowSet </code>对象的原始值的<code> ResultSet </code>对象。
   * <P>
   *  光标位于结果集中的第一行之前。只有由方法<code> getOriginal </code>返回的结果集中包含的行才被称为具有原始值。
   * 
   * 
   * @return the original value of the rowset
   * @exception SQLException if a database access error occurs
   */
  public ResultSet getOriginal() throws SQLException;

  /**
   * Retrieves a <code>ResultSet</code> object containing the original value
   * of the current row only.  If the current row has no original value,
   * an empty result set is returned. If there is no current row,
   * an exception is thrown.
   *
   * <p>
   * 
   * @return the original value of the current row as a <code>ResultSet</code>
   *          object
   * @exception SQLException if a database access error occurs or this method
   *           is called while the cursor is on the insert row, before the
   *           first row, or after the last row
   */
  public ResultSet getOriginalRow() throws SQLException;

}
