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
 * An object that implements the <code>RowSetWriter</code> interface,
 * called a <i>writer</i>. A writer may be registered with a <code>RowSet</code>
 * object that supports the reader/writer paradigm.
 * <P>
 * If a disconnected <code>RowSet</code> object modifies some of its data,
 * and it has a writer associated with it, it may be implemented so that it
 * calls on the writer's <code>writeData</code> method internally
 * to write the updates back to the data source. In order to do this, the writer
 * must first establish a connection with the rowset's data source.
 * <P>
 * If the data to be updated has already been changed in the data source, there
 * is a conflict, in which case the writer will not write
 * the changes to the data source.  The algorithm the writer uses for preventing
 * or limiting conflicts depends entirely on its implementation.
 *
 * <p>
 *  实现<code> RowSetWriter </code>接口的对象,称为<i> writer </i>。写入器可以向支持读取器/写入器范例的<code> RowSet </code>对象注册。
 * <P>
 *  如果一个断开连接的<code> RowSet </code>对象修改了它的一些数据,并且它有一个与它相关联的写入器,它可以被实现,使得它在内部调用写入器的<code> writeData </code>
 * 更新回到数据源。
 * 为了做到这一点,作者必须首先建立与行集的数据源的连接。
 * <P>
 * 
 * @since 1.4
 */

public interface RowSetWriter {

  /**
   * Writes the changes in this <code>RowSetWriter</code> object's
   * rowset back to the data source from which it got its data.
   *
   * <p>
   *  如果要更新的数据已在数据源中更改,则存在冲突,在这种情况下,写入器不会将更改写入数据源。写入器用于防止或限制冲突的算法完全取决于其实现。
   * 
   * 
   * @param caller the <code>RowSet</code> object (1) that has implemented the
   *         <code>RowSetInternal</code> interface, (2) with which this writer is
   *        registered, and (3) that called this method internally
   * @return <code>true</code> if the modified data was written; <code>false</code>
   *          if not, which will be the case if there is a conflict
   * @exception SQLException if a database access error occurs
   */
  boolean writeData(RowSetInternal caller) throws SQLException;

}
