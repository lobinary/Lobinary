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
import java.sql.Wrapper;

/**
 * <p>A factory for connections to the physical data source that this
 * {@code DataSource} object represents.  An alternative to the
 * {@code DriverManager} facility, a {@code DataSource} object
 * is the preferred means of getting a connection. An object that implements
 * the {@code DataSource} interface will typically be
 * registered with a naming service based on the
 * Java&trade; Naming and Directory (JNDI) API.
 * <P>
 * The {@code DataSource} interface is implemented by a driver vendor.
 * There are three types of implementations:
 * <OL>
 *   <LI>Basic implementation -- produces a standard {@code Connection}
 *       object
 *   <LI>Connection pooling implementation -- produces a {@code Connection}
 *       object that will automatically participate in connection pooling.  This
 *       implementation works with a middle-tier connection pooling manager.
 *   <LI>Distributed transaction implementation -- produces a
 *       {@code Connection} object that may be used for distributed
 *       transactions and almost always participates in connection pooling.
 *       This implementation works with a middle-tier
 *       transaction manager and almost always with a connection
 *       pooling manager.
 * </OL>
 * <P>
 * A {@code DataSource} object has properties that can be modified
 * when necessary.  For example, if the data source is moved to a different
 * server, the property for the server can be changed.  The benefit is that
 * because the data source's properties can be changed, any code accessing
 * that data source does not need to be changed.
 * <P>
 * A driver that is accessed via a {@code DataSource} object does not
 * register itself with the {@code DriverManager}.  Rather, a
 * {@code DataSource} object is retrieved though a lookup operation
 * and then used to create a {@code Connection} object.  With a basic
 * implementation, the connection obtained through a {@code DataSource}
 * object is identical to a connection obtained through the
 * {@code DriverManager} facility.
 * <p>
 * An implementation of {@code DataSource} must include a public no-arg
 * constructor.
 *
 * <p>
 *  <p>这个{@code DataSource}物件所代表的物理资料来源的连结工厂。
 * 作为{@code DriverManager}工具的替代方法,{@code DataSource}对象是获取连接的首选方法。
 * 实现{@code DataSource}接口的对象通常将使用基于Java&trade的命名服务注册;命名和目录(JNDI)API。
 * <P>
 *  {@code DataSource}接口由驱动程序供应商实现。有三种类型的实现：
 * <OL>
 *  <LI>基本实现 - 生成标准的{@code Connection}对象<LI>连接池实现 - 生成一个{@code Connection}对象,它将自动参与连接池。
 * 此实现与中间层连接池管理器一起工作。 <LI>分布式事务实现 - 生成可用于分布式事务的{@code Connection}对象,并且几乎总是参与连接池。
 * 此实现与中间层事务管理器和几乎总是与连接池管理器一起工作。
 * </OL>
 * <P>
 * {@code DataSource}对象具有必要时可以修改的属性。例如,如果数据源移动到不同的服务器,则可以更改服务器的属性。好处是,因为数据源的属性可以更改,访问该数据源的任何代码不需要更改。
 * <P>
 * 
 * @since 1.4
 */

public interface DataSource  extends CommonDataSource, Wrapper {

  /**
   * <p>Attempts to establish a connection with the data source that
   * this {@code DataSource} object represents.
   *
   * <p>
   *  通过{@code DataSource}对象访问的驱动程序不会向{@code DriverManager}注册自身。
   * 相反,通过查找操作检索{@code DataSource}对象,然后用于创建{@code Connection}对象。
   * 使用基本实现,通过{@code DataSource}对象获得的连接与通过{@code DriverManager}工具获取的连接相同。
   * <p>
   *  {@code DataSource}的实现必须包含公共无参构造函数。
   * 
   * 
   * @return  a connection to the data source
   * @exception SQLException if a database access error occurs
   * @throws java.sql.SQLTimeoutException  when the driver has determined that the
   * timeout value specified by the {@code setLoginTimeout} method
   * has been exceeded and has at least tried to cancel the
   * current database connection attempt
   */
  Connection getConnection() throws SQLException;

  /**
   * <p>Attempts to establish a connection with the data source that
   * this {@code DataSource} object represents.
   *
   * <p>
   * 
   * @param username the database user on whose behalf the connection is
   *  being made
   * @param password the user's password
   * @return  a connection to the data source
   * @exception SQLException if a database access error occurs
   * @throws java.sql.SQLTimeoutException  when the driver has determined that the
   * timeout value specified by the {@code setLoginTimeout} method
   * has been exceeded and has at least tried to cancel the
   * current database connection attempt
   * @since 1.4
   */
  Connection getConnection(String username, String password)
    throws SQLException;
}
