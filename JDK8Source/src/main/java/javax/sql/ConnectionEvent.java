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

import java.sql.SQLException;

/**
 * <P>An <code>Event</code> object that provides information about the
 * source of a connection-related event.  <code>ConnectionEvent</code>
 * objects are generated when an application closes a pooled connection
 * and when an error occurs.  The <code>ConnectionEvent</code> object
 * contains two kinds of information:
 * <UL>
 *   <LI>The pooled connection closed by the application
 *   <LI>In the case of an error event, the <code>SQLException</code>
 *       about to be thrown to the application
 * </UL>
 *
 * <p>
 *  <P>一个<code> Event </code>对象,提供有关连接相关事件源的信息。 <code> ConnectionEvent </code>对象在应用程序关闭池连接时发生错误时生成。
 *  <code> ConnectionEvent </code>对象包含两种信息：。
 * <UL>
 *  <LI>由应用程序关闭的池连接<LI>在错误事件的情况下,要向应用程序抛出的<code> SQLException </code>
 * </UL>
 * 
 * 
 * @since 1.4
 */

public class ConnectionEvent extends java.util.EventObject {

  /**
   * <P>Constructs a <code>ConnectionEvent</code> object initialized with
   * the given <code>PooledConnection</code> object. <code>SQLException</code>
   * defaults to <code>null</code>.
   *
   * <p>
   *  <P>构造用给定的<code> PooledConnection </code>对象初始化的<code> ConnectionEvent </code>对象。
   *  <code> SQLException </code>默认为<code> null </code>。
   * 
   * 
   * @param con the pooled connection that is the source of the event
   * @throws IllegalArgumentException if <code>con</code> is null.
   */
  public ConnectionEvent(PooledConnection con) {
    super(con);
  }

  /**
   * <P>Constructs a <code>ConnectionEvent</code> object initialized with
   * the given <code>PooledConnection</code> object and
   * <code>SQLException</code> object.
   *
   * <p>
   *  <P>构造用给定的<code> PooledConnection </code>对象和<code> SQLException </code>对象初始化的<code> ConnectionEvent </code>
   * 对象。
   * 
   * 
   * @param con the pooled connection that is the source of the event
   * @param ex the SQLException about to be thrown to the application
   * @throws IllegalArgumentException if <code>con</code> is null.
   */
  public ConnectionEvent(PooledConnection con, SQLException ex) {
    super(con);
    this.ex = ex;
  }

  /**
   * <P>Retrieves the <code>SQLException</code> for this
   * <code>ConnectionEvent</code> object. May be <code>null</code>.
   *
   * <p>
   *  <P>检索<code> ConnectionEvent </code>对象的<code> SQLException </code>。可以<code> null </code>。
   * 
   * 
   * @return the SQLException about to be thrown or <code>null</code>
   */
  public SQLException getSQLException() { return ex; }

  /**
   * The <code>SQLException</code> that the driver will throw to the
   * application when an error occurs and the pooled connection is no
   * longer usable.
   * <p>
   *  当发生错误并且池化连接不再可用时,驱动程序将向应用程序抛出的<code> SQLException </code>。
   * 
   * 
   * @serial
   */
  private SQLException ex = null;

  /**
   * Private serial version unique ID to ensure serialization
   * compatibility.
   * <p>
   *  私有串口版本唯一的ID,以确保序列化兼容性。
   */
  static final long serialVersionUID = -4843217645290030002L;

 }
