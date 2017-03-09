/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.sql.Connection;
import java.sql.SQLException;

/**
 * An object that provides hooks for connection pool management.
 * A <code>PooledConnection</code> object
 * represents a physical connection to a data source.  The connection
 * can be recycled rather than being closed when an application is
 * finished with it, thus reducing the number of connections that
 * need to be made.
 * <P>
 * An application programmer does not use the <code>PooledConnection</code>
 * interface directly; rather, it is used by a middle tier infrastructure
 * that manages the pooling of connections.
 * <P>
 * When an application calls the method <code>DataSource.getConnection</code>,
 * it gets back a <code>Connection</code> object.  If connection pooling is
 * being done, that <code>Connection</code> object is actually a handle to
 * a <code>PooledConnection</code> object, which is a physical connection.
 * <P>
 * The connection pool manager, typically the application server, maintains
 * a pool of <code>PooledConnection</code> objects.  If there is a
 * <code>PooledConnection</code> object available in the pool, the
 * connection pool manager returns a <code>Connection</code> object that
 * is a handle to that physical connection.
 * If no <code>PooledConnection</code> object is available, the
 * connection pool manager calls the <code>ConnectionPoolDataSource</code>
 * method <code>getPoolConnection</code> to create a new physical connection.  The
 *  JDBC driver implementing <code>ConnectionPoolDataSource</code> creates a
 *  new <code>PooledConnection</code> object and returns a handle to it.
 * <P>
 * When an application closes a connection, it calls the <code>Connection</code>
 * method <code>close</code>. When connection pooling is being done,
 * the connection pool manager is notified because it has registered itself as
 * a <code>ConnectionEventListener</code> object using the
 * <code>ConnectionPool</code> method <code>addConnectionEventListener</code>.
 * The connection pool manager deactivates the handle to
 * the <code>PooledConnection</code> object and  returns the
 * <code>PooledConnection</code> object to the pool of connections so that
 * it can be used again.  Thus, when an application closes its connection,
 * the underlying physical connection is recycled rather than being closed.
 * <P>
 * The physical connection is not closed until the connection pool manager
 * calls the <code>PooledConnection</code> method <code>close</code>.
 * This method is generally called to have an orderly shutdown of the server or
 * if a fatal error has made the connection unusable.
 *
 * <p>
 * A connection pool manager is often also a statement pool manager, maintaining
 *  a pool of <code>PreparedStatement</code> objects.
 *  When an application closes a prepared statement, it calls the
 *  <code>PreparedStatement</code>
 * method <code>close</code>. When <code>Statement</code> pooling is being done,
 * the pool manager is notified because it has registered itself as
 * a <code>StatementEventListener</code> object using the
 * <code>ConnectionPool</code> method <code>addStatementEventListener</code>.
 *  Thus, when an application closes its  <code>PreparedStatement</code>,
 * the underlying prepared statement is recycled rather than being closed.
 * <P>
 *
 * <p>
 *  一个为连接池管理提供钩子的对象。 <code> PooledConnection </code>对象表示到数据源的物理连接。
 * 当应用程序完成连接时,连接可以被再循环而不是关闭,从而减少需要进行的连接的数量。
 * <P>
 *  应用程序员不直接使用<code> PooledConnection </code>接口;相反,它由管理连接池的中间层基础设施使用。
 * <P>
 *  当应用程序调用<code> DataSource.getConnection </code>方法时,它会返回一个<code> Connection </code>对象。
 * 如果正在进行连接池,那么<code> Connection </code>对象实际上是一个<code> PooledConnection </code>对象的句柄,它是一个物理连接。
 * <P>
 * 连接池管理器(通常是应用程序服务器)维护一个<code> PooledConnection </code>对象池。
 * 如果池中有一个<code> PooledConnection </code>对象,连接池管理器返回一个<code> Connection </code>对象,该对象是该物理连接的句柄。
 * 如果没有<code> PooledConnection </code>对象可用,则连接池管理器调用<code> ConnectionPoolDataSource </code>方法<code> getP
 * oolConnection </code>创建一个新的物理连接。
 * 如果池中有一个<code> PooledConnection </code>对象,连接池管理器返回一个<code> Connection </code>对象,该对象是该物理连接的句柄。
 * 实现<code> ConnectionPoolDataSource </code>的JDBC驱动程序创建一个新的<code> PooledConnection </code>对象并返回一个句柄。
 * <P>
 *  当应用程序关闭连接时,它会调用<code> Connection </code>方法<code> close </code>。
 * 当连接池被完成时,连接池管理器被通知,因为它使用<code> ConnectionPool </code>方法<code> addConnectionEventListener </code>将自己注册
 * 为<code> ConnectionEventListener </code>对象。
 *  当应用程序关闭连接时,它会调用<code> Connection </code>方法<code> close </code>。
 * 连接池管理器取消激活<code> PooledConnection </code>对象的句柄,并将<code> PooledConnection </code>对象返回到连接池,以便可以再次使用它。
 * 因此,当应用程序关闭其连接时,底层物理连接被回收而不是被关闭。
 * <P>
 * 在连接池管理器调用<code> PooledConnection </code>方法<code> close </code>之前,物理连接不会关闭。
 * 通常调用此方法以有序关闭服务器,或者如果致命错误使连接不可用。
 * 
 * <p>
 *  连接池管理器通常也是语句池管理器,维护一个<code> PreparedStatement </code>对象池。
 * 当应用程序关闭预准备语句时,它会调用<code> PreparedStatement </code>方法<code> close </code>。
 * 当执行<code> Statement </code> pooling时,池管理器被通知,因为它已经使用<code> ConnectionPool </code>方法将其自身注册为<code> Stat
 * ementEventListener </code>对象<code> addStatementEventListener </code>。
 * 当应用程序关闭预准备语句时,它会调用<code> PreparedStatement </code>方法<code> close </code>。
 * 
 * @since 1.4
 */

public interface PooledConnection {

  /**
   * Creates and returns a <code>Connection</code> object that is a handle
   * for the physical connection that
   * this <code>PooledConnection</code> object represents.
   * The connection pool manager calls this method when an application has
   * called the method <code>DataSource.getConnection</code> and there are
   * no <code>PooledConnection</code> objects available. See the
   * {@link PooledConnection interface description} for more information.
   *
   * <p>
   * 因此,当应用程序关闭其<code> PreparedStatement </code>时,底层的准备语句被回收而不是关闭。
   * <P>
   * 
   * 
   * @return  a <code>Connection</code> object that is a handle to
   *          this <code>PooledConnection</code> object
   * @exception SQLException if a database access error occurs
   * @exception java.sql.SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.4
   */
  Connection getConnection() throws SQLException;

  /**
   * Closes the physical connection that this <code>PooledConnection</code>
   * object represents.  An application never calls this method directly;
   * it is called by the connection pool module, or manager.
   * <P>
   * See the {@link PooledConnection interface description} for more
   * information.
   *
   * <p>
   *  创建并返回一个<code> Connection </code>对象,它是这个<code> PooledConnection </code>对象表示的物理连接的句柄。
   * 当应用程序调用方法<code> DataSource.getConnection </code>并且没有可用的<code> PooledConnection </code>对象时,连接池管理器调用此方法
   * 。
   *  创建并返回一个<code> Connection </code>对象,它是这个<code> PooledConnection </code>对象表示的物理连接的句柄。
   * 有关详细信息,请参阅{@link PooledConnection接口描述}。
   * 
   * 
   * @exception SQLException if a database access error occurs
   * @exception java.sql.SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method
   * @since 1.4
   */
  void close() throws SQLException;

  /**
   * Registers the given event listener so that it will be notified
   * when an event occurs on this <code>PooledConnection</code> object.
   *
   * <p>
   * 关闭此<code> PooledConnection </code>对象表示的物理连接。应用程序从不直接调用此方法;它由连接池模块或管理器调用。
   * <P>
   *  有关详细信息,请参阅{@link PooledConnection接口描述}。
   * 
   * 
   * @param listener a component, usually the connection pool manager,
   *        that has implemented the
   *        <code>ConnectionEventListener</code> interface and wants to be
   *        notified when the connection is closed or has an error
   * @see #removeConnectionEventListener
   */
  void addConnectionEventListener(ConnectionEventListener listener);

  /**
   * Removes the given event listener from the list of components that
   * will be notified when an event occurs on this
   * <code>PooledConnection</code> object.
   *
   * <p>
   *  注册给定的事件侦听器,以便在此<code> PooledConnection </code>对象上发生事件时通知它。
   * 
   * 
   * @param listener a component, usually the connection pool manager,
   *        that has implemented the
   *        <code>ConnectionEventListener</code> interface and
   *        been registered with this <code>PooledConnection</code> object as
   *        a listener
   * @see #addConnectionEventListener
   */
  void removeConnectionEventListener(ConnectionEventListener listener);

        /**
         * Registers a <code>StatementEventListener</code> with this <code>PooledConnection</code> object.  Components that
         * wish to be notified when  <code>PreparedStatement</code>s created by the
         * connection are closed or are detected to be invalid may use this method
         * to register a <code>StatementEventListener</code> with this <code>PooledConnection</code> object.
         * <p>
         * <p>
         *  从在<code> PooledConnection </code>对象上发生事件时将通知的组件列表中删除给定的事件侦听器。
         * 
         * 
         * @param listener      an component which implements the <code>StatementEventListener</code>
         *                                      interface that is to be registered with this <code>PooledConnection</code> object
         * <p>
         * @since 1.6
         */
        public void addStatementEventListener(StatementEventListener listener);

        /**
         * Removes the specified <code>StatementEventListener</code> from the list of
         * components that will be notified when the driver detects that a
         * <code>PreparedStatement</code> has been closed or is invalid.
         * <p>
         * <p>
         *  使用此<code> PooledConnection </code>对象注册<code> StatementEventListener </code>。
         * 当连接创建的<code> PreparedStatement </code>被关闭或被检测为无效时希望被通知的组件可以使用此方法向此<code> PooledConnection </code>注册<code>
         *  StatementEventListener </code> / code>对象。
         *  使用此<code> PooledConnection </code>对象注册<code> StatementEventListener </code>。
         * 
         * @param listener      the component which implements the
         *                                      <code>StatementEventListener</code> interface that was previously
         *                                      registered with this <code>PooledConnection</code> object
         * <p>
         * @since 1.6
         */
        public void removeStatementEventListener(StatementEventListener listener);

 }
