/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * An object that provides support for distributed
 * transactions.  An <code>XAConnection</code> object  may be enlisted
 * in a distributed transaction by means of an <code>XAResource</code> object.
 * A transaction manager, usually part of a middle tier server, manages an
 * <code>XAConnection</code> object through the <code>XAResource</code> object.
 * <P>
 * An application programmer does not use this interface directly; rather,
 * it is used by a transaction manager working in the middle tier server.
 *
 * <p>
 *  一个为分布式事务提供支持的对​​象。可以通过<code> XAResource </code>对象在分布式事务中登记<code> XAConnection </code>对象。
 * 事务管理器通常是中间层服务器的一部分,通过<code> XAResource </code>对象管理一个<code> XAConnection </code>对象。
 * <P>
 *  应用程序员不直接使用这个接口;相反,它由在中间层服务器中工作的事务管理器使用。
 * 
 * @since 1.4
 */

public interface XAConnection extends PooledConnection {


  /**
   * Retrieves an <code>XAResource</code> object that
   * the transaction manager will use
   * to manage this <code>XAConnection</code> object's participation in a
   * distributed transaction.
   *
   * <p>
   * 
   * 
   * @return the <code>XAResource</code> object
   * @exception SQLException if a database access error occurs
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.4
   */
  javax.transaction.xa.XAResource getXAResource() throws SQLException;

 }
