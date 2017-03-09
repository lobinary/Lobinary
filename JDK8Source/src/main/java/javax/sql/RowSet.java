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

import java.sql.*;
import java.io.*;
import java.math.*;
import java.util.*;

/**
 * The interface that adds support to the JDBC API for the
 * JavaBeans&trade; component model.
 * A rowset, which can be used as a JavaBeans component in
 * a visual Bean development environment, can be created and
 * configured at design time and executed at run time.
 * <P>
 * The <code>RowSet</code>
 * interface provides a set of JavaBeans properties that allow a <code>RowSet</code>
 * instance to be configured to connect to a JDBC data source and read
 * some data from the data source.  A group of setter methods (<code>setInt</code>,
 * <code>setBytes</code>, <code>setString</code>, and so on)
 * provide a way to pass input parameters to a rowset's command property.
 * This command is the SQL query the rowset uses when it gets its data from
 * a relational database, which is generally the case.
 * <P>
 * The <code>RowSet</code>
 * interface supports JavaBeans events, allowing other components in an
 * application to be notified when an event occurs on a rowset,
 * such as a change in its value.
 *
 * <P>The <code>RowSet</code> interface is unique in that it is intended to be
 * implemented using the rest of the JDBC API.  In other words, a
 * <code>RowSet</code> implementation is a layer of software that executes "on top"
 * of a JDBC driver.  Implementations of the <code>RowSet</code> interface can
 * be provided by anyone, including JDBC driver vendors who want to
 * provide a <code>RowSet</code> implementation as part of their JDBC products.
 * <P>
 * A <code>RowSet</code> object may make a connection with a data source and
 * maintain that connection throughout its life cycle, in which case it is
 * called a <i>connected</i> rowset.  A rowset may also make a connection with
 * a data source, get data from it, and then close the connection. Such a rowset
 * is called a <i>disconnected</i> rowset.  A disconnected rowset may make
 * changes to its data while it is disconnected and then send the changes back
 * to the original source of the data, but it must reestablish a connection to do so.
 * <P>
 * A disconnected rowset may have a reader (a <code>RowSetReader</code> object)
 * and a writer (a <code>RowSetWriter</code> object) associated with it.
 * The reader may be implemented in many different ways to populate a rowset
 * with data, including getting data from a non-relational data source. The
 * writer can also be implemented in many different ways to propagate changes
 * made to the rowset's data back to the underlying data source.
 * <P>
 * Rowsets are easy to use.  The <code>RowSet</code> interface extends the standard
 * <code>java.sql.ResultSet</code> interface.  The <code>RowSetMetaData</code>
 * interface extends the <code>java.sql.ResultSetMetaData</code> interface.
 * Thus, developers familiar
 * with the JDBC API will have to learn a minimal number of new APIs to
 * use rowsets.  In addition, third-party software tools that work with
 * JDBC <code>ResultSet</code> objects will also easily be made to work with rowsets.
 *
 * <p>
 *  为JavaBeans&trade添加对JDBC API的支持的接口;组件模型。可以在可视Bean开发环境中用作JavaBeans组件的行集可以在设计时创建和配置,并在运行时执行。
 * <P>
 *  <code> RowSet </code>接口提供了一组JavaBeans属性,允许将<code> RowSet </code>实例配置为连接到JDBC数据源并从数据源读取一些数据。
 * 一组setter方法(<code> setInt </code>,<code> setBytes </code>,<code> setString </code>等)提供了一种将输入参数传递到行集命令属
 * 性的方法。
 *  <code> RowSet </code>接口提供了一组JavaBeans属性,允许将<code> RowSet </code>实例配置为连接到JDBC数据源并从数据源读取一些数据。
 * 此命令是行集从关系数据库获取其数据时使用的SQL查询,这通常是这种情况。
 * <P>
 *  <code> RowSet </code>接口支持JavaBeans事件,允许在行集上发生事件时通知应用程序中的其他组件,如值的更改。
 * 
 * <p> <code> RowSet </code>接口是唯一的,它旨在使用其余的JDBC API实现。
 * 换句话说,<code> RowSet </code>实现是一个执行JDBC驱动程序"顶部"的软件层。
 *  <code> RowSet </code>接口的实现可以由任何人提供,包括想要提供<code> RowSet </code>实现作为其JDBC产品的一部分的JDBC驱动程序供应商。
 * <P>
 *  <code> RowSet </code>对象可以与数据源建立连接,并在其整个生命周期内保持该连接,在这种情况下,它被称为<i>连接的</i>行集。
 * 行集还可以与数据源建立连接,从中获取数据,然后关闭连接。这样的行集被称为<i>断开</i>行集。
 * 断开的行集可以在其数据断开时对其数据进行更改,然后将更改发送回数据的原始源,但它必须重新建立连接才能这样做。
 * <P>
 *  断开的行集可以具有与其相关联的读取器(<code> RowSetReader </code>对象)和写入器(<code> RowSetWriter </code>对象)。
 * 读取器可以以许多不同的方式实现,以用数据填充行集,包括从非关系数据源获取数据。写入器也可以以许多不同的方式实现,以将对行集的数据所做的更改传播回底层数据源。
 * <P>
 * 行集很容易使用。 <code> RowSet </code>接口扩展了标准的<code> java.sql.ResultSet </code>接口。
 *  <code> RowSetMetaData </code>接口扩展了<code> java.sql.ResultSetMetaData </code>接口。
 * 因此,熟悉JDBC API的开发人员必须学习最少数量的新API才能使用行集。此外,使用JDBC <code> ResultSet </code>对象的第三方软件工具也很容易用来处理行集。
 * 
 * 
 * @since 1.4
 */

public interface RowSet extends ResultSet {

  //-----------------------------------------------------------------------
  // Properties
  //-----------------------------------------------------------------------

  //-----------------------------------------------------------------------
  // The following properties may be used to create a Connection.
  //-----------------------------------------------------------------------

  /**
   * Retrieves the url property this <code>RowSet</code> object will use to
   * create a connection if it uses the <code>DriverManager</code>
   * instead of a <code>DataSource</code> object to establish the connection.
   * The default value is <code>null</code>.
   *
   * <p>
   *  如果使用<code> DriverManager </code>而不是<code> DataSource </code>对象建立连接,则检索此<code> RowSet </code>对象将用于创建连
   * 接的url属性。
   * 默认值为<code> null </code>。
   * 
   * 
   * @return a string url
   * @exception SQLException if a database access error occurs
   * @see #setUrl
   */
  String getUrl() throws SQLException;

  /**
   * Sets the URL this <code>RowSet</code> object will use when it uses the
   * <code>DriverManager</code> to create a connection.
   *
   * Setting this property is optional.  If a URL is used, a JDBC driver
   * that accepts the URL must be loaded before the
   * rowset is used to connect to a database.  The rowset will use the URL
   * internally to create a database connection when reading or writing
   * data.  Either a URL or a data source name is used to create a
   * connection, whichever was set to non null value most recently.
   *
   * <p>
   *  设置<code> RowSet </code>对象在使用<code> DriverManager </code>创建连接时将使用的URL。
   * 
   *  设置此属性是可选的。如果使用URL,则必须在行集用于连接到数据库之前加载接受URL的JDBC驱动程序。当读取或写入数据时,行集将使用内部URL来创建数据库连接。
   * 使用URL或数据源名称创建连接,两者中最近设置为非空值。
   * 
   * 
   * @param url a string value; may be <code>null</code>
   * @exception SQLException if a database access error occurs
   * @see #getUrl
   */
  void setUrl(String url) throws SQLException;

  /**
   * Retrieves the logical name that identifies the data source for this
   * <code>RowSet</code> object.
   *
   * <p>
   *  检索标识此<code> RowSet </code>对象的数据源的逻辑名。
   * 
   * 
   * @return a data source name
   * @see #setDataSourceName
   * @see #setUrl
   */
  String getDataSourceName();

  /**
   * Sets the data source name property for this <code>RowSet</code> object to the
   * given <code>String</code>.
   * <P>
   * The value of the data source name property can be used to do a lookup of
   * a <code>DataSource</code> object that has been registered with a naming
   * service.  After being retrieved, the <code>DataSource</code> object can be
   * used to create a connection to the data source that it represents.
   *
   * <p>
   *  将此<code> RowSet </code>对象的数据源名称属性设置为给定的<code> String </code>。
   * <P>
   * 数据源名称属性的值可用于查找已使用命名服务注册的<code> DataSource </code>对象。
   * 在检索之后,可以使用<code> DataSource </code>对象来创建与其所代表的数据源的连接。
   * 
   * 
   * @param name the logical name of the data source for this <code>RowSet</code>
   *        object; may be <code>null</code>
   * @exception SQLException if a database access error occurs
   * @see #getDataSourceName
   */
  void setDataSourceName(String name) throws SQLException;

  /**
   * Retrieves the username used to create a database connection for this
   * <code>RowSet</code> object.
   * The username property is set at run time before calling the method
   * <code>execute</code>.  It is
   * not usually part of the serialized state of a <code>RowSet</code> object.
   *
   * <p>
   *  检索用于为此<code> RowSet </code>对象创建数据库连接的用户名。 username属性在调用方法<code> execute </code>之前在运行时设置。
   * 它通常不是<code> RowSet </code>对象的序列化状态的一部分。
   * 
   * 
   * @return the username property
   * @see #setUsername
   */
  String getUsername();

  /**
   * Sets the username property for this <code>RowSet</code> object to the
   * given <code>String</code>.
   *
   * <p>
   *  将<code> RowSet </code>对象的username属性设置为给定的<code> String </code>。
   * 
   * 
   * @param name a user name
   * @exception SQLException if a database access error occurs
   * @see #getUsername
   */
  void setUsername(String name) throws SQLException;

  /**
   * Retrieves the password used to create a database connection.
   * The password property is set at run time before calling the method
   * <code>execute</code>.  It is not usually part of the serialized state
   * of a <code>RowSet</code> object.
   *
   * <p>
   *  检索用于创建数据库连接的密码。 password属性在调用方法<code> execute </code>之前在运行时设置。
   * 它通常不是<code> RowSet </code>对象的序列化状态的一部分。
   * 
   * 
   * @return the password for making a database connection
   * @see #setPassword
   */
  String getPassword();

  /**
   * Sets the database password for this <code>RowSet</code> object to
   * the given <code>String</code>.
   *
   * <p>
   *  将<code> RowSet </code>对象的数据库密码设置为给定的<code> String </code>。
   * 
   * 
   * @param password the password string
   * @exception SQLException if a database access error occurs
   * @see #getPassword
   */
  void setPassword(String password) throws SQLException;

  /**
   * Retrieves the transaction isolation level set for this
   * <code>RowSet</code> object.
   *
   * <p>
   *  检索为此<code> RowSet </code>对象设置的事务隔离级别。
   * 
   * 
   * @return the transaction isolation level; one of
   *      <code>Connection.TRANSACTION_READ_UNCOMMITTED</code>,
   *      <code>Connection.TRANSACTION_READ_COMMITTED</code>,
   *      <code>Connection.TRANSACTION_REPEATABLE_READ</code>, or
   *      <code>Connection.TRANSACTION_SERIALIZABLE</code>
   * @see #setTransactionIsolation
   */
  int getTransactionIsolation();

  /**
   * Sets the transaction isolation level for this <code>RowSet</code> object.
   *
   * <p>
   *  为此<code> RowSet </code>对象设置事务隔离级别。
   * 
   * 
   * @param level the transaction isolation level; one of
   *      <code>Connection.TRANSACTION_READ_UNCOMMITTED</code>,
   *      <code>Connection.TRANSACTION_READ_COMMITTED</code>,
   *      <code>Connection.TRANSACTION_REPEATABLE_READ</code>, or
   *      <code>Connection.TRANSACTION_SERIALIZABLE</code>
   * @exception SQLException if a database access error occurs
   * @see #getTransactionIsolation
   */
  void setTransactionIsolation(int level) throws SQLException;

  /**
   * Retrieves the <code>Map</code> object associated with this
   * <code>RowSet</code> object, which specifies the custom mapping
   * of SQL user-defined types, if any.  The default is for the
   * type map to be empty.
   *
   * <p>
   *  检索与此<code> RowSet </code>对象关联的<code> Map </code>对象,该对象指定SQL用户定义类型(如果有)的自定义映射。默认值为类型映射为空。
   * 
   * 
   * @return a <code>java.util.Map</code> object containing the names of
   *         SQL user-defined types and the Java classes to which they are
   *         to be mapped
   *
   * @exception SQLException if a database access error occurs
   * @see #setTypeMap
   */
   java.util.Map<String,Class<?>> getTypeMap() throws SQLException;

  /**
   * Installs the given <code>java.util.Map</code> object as the default
   * type map for this <code>RowSet</code> object. This type map will be
   * used unless another type map is supplied as a method parameter.
   *
   * <p>
   * 将给定的<code> java.util.Map </code>对象作为此<code> RowSet </code>对象的默认类型映射。除非另一个类型映射作为方法参数提供,否则将使用此类型映射。
   * 
   * 
   * @param map  a <code>java.util.Map</code> object containing the names of
   *         SQL user-defined types and the Java classes to which they are
   *         to be mapped
   * @exception SQLException if a database access error occurs
   * @see #getTypeMap
   */
   void setTypeMap(java.util.Map<String,Class<?>> map) throws SQLException;

  //-----------------------------------------------------------------------
  // The following properties may be used to create a Statement.
  //-----------------------------------------------------------------------

  /**
   * Retrieves this <code>RowSet</code> object's command property.
   *
   * The command property contains a command string, which must be an SQL
   * query, that can be executed to fill the rowset with data.
   * The default value is <code>null</code>.
   *
   * <p>
   *  检索此<<code> RowSet </code>对象的命令属性。
   * 
   *  command属性包含一个命令字符串,它必须是一个SQL查询,可以执行该字符串以填充行集合的数据。默认值为<code> null </code>。
   * 
   * 
   * @return the command string; may be <code>null</code>
   * @see #setCommand
   */
  String getCommand();

  /**
   * Sets this <code>RowSet</code> object's command property to the given
   * SQL query.
   *
   * This property is optional
   * when a rowset gets its data from a data source that does not support
   * commands, such as a spreadsheet.
   *
   * <p>
   *  将此<<code> RowSet </code>对象的命令属性设置为给定的SQL查询。
   * 
   *  当行集从不支持命令的数据源(例如电子表格)获取其数据时,此属性是可选的。
   * 
   * 
   * @param cmd the SQL query that will be used to get the data for this
   *        <code>RowSet</code> object; may be <code>null</code>
   * @exception SQLException if a database access error occurs
   * @see #getCommand
   */
  void setCommand(String cmd) throws SQLException;

  /**
   * Retrieves whether this <code>RowSet</code> object is read-only.
   * If updates are possible, the default is for a rowset to be
   * updatable.
   * <P>
   * Attempts to update a read-only rowset will result in an
   * <code>SQLException</code> being thrown.
   *
   * <p>
   *  检索此<> RowSet </code>对象是否为只读。如果可能进行更新,则缺省值是可更新的行集。
   * <P>
   *  尝试更新只读行集将导致抛出<code> SQLException </code>。
   * 
   * 
   * @return <code>true</code> if this <code>RowSet</code> object is
   *         read-only; <code>false</code> if it is updatable
   * @see #setReadOnly
   */
  boolean isReadOnly();

  /**
   * Sets whether this <code>RowSet</code> object is read-only to the
   * given <code>boolean</code>.
   *
   * <p>
   *  设置<code> RowSet </code>对象是否只读给给定的<code> boolean </code>。
   * 
   * 
   * @param value <code>true</code> if read-only; <code>false</code> if
   *        updatable
   * @exception SQLException if a database access error occurs
   * @see #isReadOnly
   */
  void setReadOnly(boolean value) throws SQLException;

  /**
   * Retrieves the maximum number of bytes that may be returned
   * for certain column values.
   * This limit applies only to <code>BINARY</code>,
   * <code>VARBINARY</code>, <code>LONGVARBINARYBINARY</code>, <code>CHAR</code>,
   * <code>VARCHAR</code>, <code>LONGVARCHAR</code>, <code>NCHAR</code>
   * and <code>NVARCHAR</code> columns.
   * If the limit is exceeded, the excess data is silently discarded.
   *
   * <p>
   *  检索某些列值可能返回的最大字节数。
   * 此限制仅适用于<code> BINARY </code>,<code> VARBINARY </code>,<code> LONGVARBINARYBINARY </code>,<code> CHAR 
   * </code>,<code> VARCHAR </code> <code> LONGVARCHAR </code>,<code> NCHAR </code>和<code> NVARCHAR </code>
   * 列。
   *  检索某些列值可能返回的最大字节数。如果超过限制,则超出的数据将被静默丢弃。
   * 
   * 
   * @return the current maximum column size limit; zero means that there
   *          is no limit
   * @exception SQLException if a database access error occurs
   * @see #setMaxFieldSize
   */
  int getMaxFieldSize() throws SQLException;

  /**
   * Sets the maximum number of bytes that can be returned for a column
   * value to the given number of bytes.
   * This limit applies only to <code>BINARY</code>,
   * <code>VARBINARY</code>, <code>LONGVARBINARYBINARY</code>, <code>CHAR</code>,
   * <code>VARCHAR</code>, <code>LONGVARCHAR</code>, <code>NCHAR</code>
   * and <code>NVARCHAR</code> columns.
   * If the limit is exceeded, the excess data is silently discarded.
   * For maximum portability, use values greater than 256.
   *
   * <p>
   * 将列值可返回的最大字节数设置为给定字节数。
   * 此限制仅适用于<code> BINARY </code>,<code> VARBINARY </code>,<code> LONGVARBINARYBINARY </code>,<code> CHAR 
   * </code>,<code> VARCHAR </code> <code> LONGVARCHAR </code>,<code> NCHAR </code>和<code> NVARCHAR </code>
   * 列。
   * 将列值可返回的最大字节数设置为给定字节数。如果超过限制,则超出的数据将被静默丢弃。为了实现最大可移植性,请使用大于256的值。
   * 
   * 
   * @param max the new max column size limit in bytes; zero means unlimited
   * @exception SQLException if a database access error occurs
   * @see #getMaxFieldSize
   */
  void setMaxFieldSize(int max) throws SQLException;

  /**
   * Retrieves the maximum number of rows that this <code>RowSet</code>
   * object can contain.
   * If the limit is exceeded, the excess rows are silently dropped.
   *
   * <p>
   *  检索此<code> RowSet </code>对象可以包含的最大行数。如果超过限制,则超出的行将被静默删除。
   * 
   * 
   * @return the current maximum number of rows that this <code>RowSet</code>
   *         object can contain; zero means unlimited
   * @exception SQLException if a database access error occurs
   * @see #setMaxRows
   */
  int getMaxRows() throws SQLException;

  /**
   * Sets the maximum number of rows that this <code>RowSet</code>
   * object can contain to the specified number.
   * If the limit is exceeded, the excess rows are silently dropped.
   *
   * <p>
   *  将此<code> RowSet </code>对象可以包含的最大行数设置为指定的数字。如果超过限制,则超出的行将被静默删除。
   * 
   * 
   * @param max the new maximum number of rows; zero means unlimited
   * @exception SQLException if a database access error occurs
   * @see #getMaxRows
   */
  void setMaxRows(int max) throws SQLException;

  /**
   * Retrieves whether escape processing is enabled for this
   * <code>RowSet</code> object.
   * If escape scanning is enabled, which is the default, the driver will do
   * escape substitution before sending an SQL statement to the database.
   *
   * <p>
   *  检索是否为此<code> RowSet </code>对象启用了转义处理。如果启用了逃生扫描(这是默认值),则在将SQL语句发送到数据库之前,驱动程序将执行转义替换。
   * 
   * 
   * @return <code>true</code> if escape processing is enabled;
   *         <code>false</code> if it is disabled
   * @exception SQLException if a database access error occurs
   * @see #setEscapeProcessing
   */
  boolean getEscapeProcessing() throws SQLException;

  /**
   * Sets escape processing for this <code>RowSet</code> object on or
   * off. If escape scanning is on (the default), the driver will do
   * escape substitution before sending an SQL statement to the database.
   *
   * <p>
   *  为此<<code> RowSet </code>对象设置或禁用转义处理。如果启用了逃生扫描(默认),驱动程序将在向数据库发送SQL语句之前执行转义替换。
   * 
   * 
   * @param enable <code>true</code> to enable escape processing;
   *        <code>false</code> to disable it
   * @exception SQLException if a database access error occurs
   * @see #getEscapeProcessing
   */
  void setEscapeProcessing(boolean enable) throws SQLException;

  /**
   * Retrieves the maximum number of seconds the driver will wait for
   * a statement to execute.
   * If this limit is exceeded, an <code>SQLException</code> is thrown.
   *
   * <p>
   *  检索驱动程序等待语句执行的最大秒数。如果超出此限制,则会抛出<code> SQLException </code>。
   * 
   * 
   * @return the current query timeout limit in seconds; zero means
   *          unlimited
   * @exception SQLException if a database access error occurs
   * @see #setQueryTimeout
   */
  int getQueryTimeout() throws SQLException;

  /**
   * Sets the maximum time the driver will wait for
   * a statement to execute to the given number of seconds.
   * If this limit is exceeded, an <code>SQLException</code> is thrown.
   *
   * <p>
   * 设置驱动程序等待语句执行到给定秒数的最大时间。如果超出此限制,则会抛出<code> SQLException </code>。
   * 
   * 
   * @param seconds the new query timeout limit in seconds; zero means
   *        that there is no limit
   * @exception SQLException if a database access error occurs
   * @see #getQueryTimeout
   */
  void setQueryTimeout(int seconds) throws SQLException;

  /**
   * Sets the type of this <code>RowSet</code> object to the given type.
   * This method is used to change the type of a rowset, which is by
   * default read-only and non-scrollable.
   *
   * <p>
   *  将此<code> RowSet </code>对象的类型设置为给定类型。此方法用于更改行集的类型,该行集默认为只读和不可滚动。
   * 
   * 
   * @param type one of the <code>ResultSet</code> constants specifying a type:
   *        <code>ResultSet.TYPE_FORWARD_ONLY</code>,
   *        <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
   *        <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
   * @exception SQLException if a database access error occurs
   * @see java.sql.ResultSet#getType
   */
  void setType(int type) throws SQLException;

  /**
   * Sets the concurrency of this <code>RowSet</code> object to the given
   * concurrency level. This method is used to change the concurrency level
   * of a rowset, which is by default <code>ResultSet.CONCUR_READ_ONLY</code>
   *
   * <p>
   *  将此<code> RowSet </code>对象的并发设置为给定的并发级别。此方法用于更改行集的并行级别,默认为<code> ResultSet.CONCUR_READ_ONLY </code>
   * 
   * 
   * @param concurrency one of the <code>ResultSet</code> constants specifying a
   *        concurrency level:  <code>ResultSet.CONCUR_READ_ONLY</code> or
   *        <code>ResultSet.CONCUR_UPDATABLE</code>
   * @exception SQLException if a database access error occurs
   * @see ResultSet#getConcurrency
   */
  void setConcurrency(int concurrency) throws SQLException;

  //-----------------------------------------------------------------------
  // Parameters
  //-----------------------------------------------------------------------

  /**
   * The <code>RowSet</code> setter methods are used to set any input parameters
   * needed by the <code>RowSet</code> object's command.
   * Parameters are set at run time, as opposed to design time.
   * <p>
   *  <code> RowSet </code> setter方法用于设置<code> RowSet </code>对象的命令所需的任何输入参数。参数在运行时设置,而不是设计时。
   * 
   */

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's SQL
   * command to SQL <code>NULL</code>.
   *
   * <P><B>Note:</B> You must specify the parameter's SQL type.
   *
   * <p>
   *  将此<<code> RowSet </code>对象的SQL命令中的指定参数设置为SQL <code> NULL </code>。
   * 
   *  <P> <B>注意：</B>您必须指定参数的SQL类型。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param sqlType a SQL type code defined by <code>java.sql.Types</code>
   * @exception SQLException if a database access error occurs
   */
  void setNull(int parameterIndex, int sqlType) throws SQLException;

  /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     *
     * <P><B>Note:</B> You must specify the parameter's SQL type.
     *
     * <p>
     *  将指定的参数设置为SQL <code> NULL </code>。
     * 
     *  <P> <B>注意：</B>您必须指定参数的SQL类型。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType the SQL type code defined in <code>java.sql.Types</code>
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setNull(String parameterName, int sqlType) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's SQL
   * command to SQL <code>NULL</code>. This version of the method <code>setNull</code>
   * should  be used for SQL user-defined types (UDTs) and <code>REF</code> type
   * parameters.  Examples of UDTs include: <code>STRUCT</code>, <code>DISTINCT</code>,
   * <code>JAVA_OBJECT</code>, and named array types.
   *
   * <P><B>Note:</B> To be portable, applications must give the
   * SQL type code and the fully qualified SQL type name when specifying
   * a NULL UDT or <code>REF</code> parameter.  In the case of a UDT,
   * the name is the type name of the parameter itself.  For a <code>REF</code>
   * parameter, the name is the type name of the referenced type.  If
   * a JDBC driver does not need the type code or type name information,
   * it may ignore it.
   *
   * Although it is intended for UDT and <code>REF</code> parameters,
   * this method may be used to set a null parameter of any JDBC type.
   * If the parameter does not have a user-defined or <code>REF</code> type,
   * the typeName parameter is ignored.
   *
   *
   * <p>
   *  将此<<code> RowSet </code>对象的SQL命令中的指定参数设置为SQL <code> NULL </code>。
   * 此版本的方法<code> setNull </code>应用于SQL用户定义类型(UDT)和<code> REF </code>类型参数。
   *  UDT的示例包括：<code> STRUCT </code>,<code> DISTINCT </code>,<code> JAVA_OBJECT </code>和命名的数组类型。
   * 
   * <P> <B>注意：</B>为了便于移植,在指定NULL UDT或<code> REF </code>参数时,应用程序必须提供SQL类型代码和完全限定的SQL类型名称。
   * 在UDT的情况下,名称是参数本身的类型名称。对于<code> REF </code>参数,名称是引用类型的类型名称。如果JDBC驱动程序不需要类型代码或类型名称信息,它可能会忽略它。
   * 
   *  虽然它用于UDT和<code> REF </code>参数,但此方法可用于设置任何JDBC类型的空参数。如果参数没有用户定义或<code> REF </code>类型,则会忽略typeName参数。
   * 
   * 
   * @param paramIndex the first parameter is 1, the second is 2, ...
   * @param sqlType a value from <code>java.sql.Types</code>
   * @param typeName the fully qualified name of an SQL UDT or the type
   *        name of the SQL structured type being referenced by a <code>REF</code>
   *        type; ignored if the parameter is not a UDT or <code>REF</code> type
   * @exception SQLException if a database access error occurs
   */
  void setNull (int paramIndex, int sqlType, String typeName)
    throws SQLException;

  /**
     * Sets the designated parameter to SQL <code>NULL</code>.
     * This version of the method <code>setNull</code> should
     * be used for user-defined types and REF type parameters.  Examples
     * of user-defined types include: STRUCT, DISTINCT, JAVA_OBJECT, and
     * named array types.
     *
     * <P><B>Note:</B> To be portable, applications must give the
     * SQL type code and the fully-qualified SQL type name when specifying
     * a NULL user-defined or REF parameter.  In the case of a user-defined type
     * the name is the type name of the parameter itself.  For a REF
     * parameter, the name is the type name of the referenced type.  If
     * a JDBC driver does not need the type code or type name information,
     * it may ignore it.
     *
     * Although it is intended for user-defined and Ref parameters,
     * this method may be used to set a null parameter of any JDBC type.
     * If the parameter does not have a user-defined or REF type, the given
     * typeName is ignored.
     *
     *
     * <p>
     *  将指定的参数设置为SQL <code> NULL </code>。此版本的方法<code> setNull </code>应用于用户定义的类型和REF类型参数。
     * 用户定义类型的示例包括：STRUCT,DISTINCT,JAVA_OBJECT和命名的数组类型。
     * 
     *  <P> <B>注意：</B>为了便于移植,当指定NULL用户定义或REF参数时,应用程序必须给出SQL类型代码和完全限定的SQL类型名称。在用户定义类型的情况下,名称是参数本身的类型名称。
     * 对于REF参数,名称是引用类型的类型名称。如果JDBC驱动程序不需要类型代码或类型名称信息,它可能会忽略它。
     * 
     * 虽然它用于用户定义和Ref参数,但此方法可用于设置任何JDBC类型的空参数。如果参数没有用户定义或REF类型,则将忽略给定的typeName。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param sqlType a value from <code>java.sql.Types</code>
     * @param typeName the fully-qualified name of an SQL user-defined type;
     *        ignored if the parameter is not a user-defined type or
     *        SQL <code>REF</code> value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setNull (String parameterName, int sqlType, String typeName)
        throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java <code>boolean</code> value. The driver converts this to
   * an SQL <code>BIT</code> value before sending it to the database.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code> boolean </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> BIT </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setBoolean(int parameterIndex, boolean x) throws SQLException;

  /**
     * Sets the designated parameter to the given Java <code>boolean</code> value.
     * The driver converts this
     * to an SQL <code>BIT</code> or <code>BOOLEAN</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> boolean </code>值。
     * 当驱动程序将其发送到数据库时,将其转换为SQL <code> BIT </code>或<code> BOOLEAN </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @see #getBoolean
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setBoolean(String parameterName, boolean x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java <code>byte</code> value. The driver converts this to
   * an SQL <code>TINYINT</code> value before sending it to the database.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code>字节</code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> TINYINT </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setByte(int parameterIndex, byte x) throws SQLException;

  /**
     * Sets the designated parameter to the given Java <code>byte</code> value.
     * The driver converts this
     * to an SQL <code>TINYINT</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code>字节</code>值。当驱动程序将其发送到数据库时,将其转换为SQL <code> TINYINT </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getByte
     * @since 1.4
     */
    void setByte(String parameterName, byte x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java <code>short</code> value. The driver converts this to
   * an SQL <code>SMALLINT</code> value before sending it to the database.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code> short </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> SMALLINT </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setShort(int parameterIndex, short x) throws SQLException;

  /**
     * Sets the designated parameter to the given Java <code>short</code> value.
     * The driver converts this
     * to an SQL <code>SMALLINT</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> short </code>值。当驱动程序将其发送到数据库时,将其转换为SQL <code> SMALLINT </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getShort
     * @since 1.4
     */
    void setShort(String parameterName, short x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java <code>int</code> value. The driver converts this to
   * an SQL <code>INTEGER</code> value before sending it to the database.
   *
   * <p>
   * 将此<<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code> int </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> INTEGER </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setInt(int parameterIndex, int x) throws SQLException;

  /**
     * Sets the designated parameter to the given Java <code>int</code> value.
     * The driver converts this
     * to an SQL <code>INTEGER</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> int </code>值。当驱动程序将它发送到数据库时,将其转换为SQL <code> INTEGER </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getInt
     * @since 1.4
     */
    void setInt(String parameterName, int x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java <code>long</code> value. The driver converts this to
   * an SQL <code>BIGINT</code> value before sending it to the database.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code> long </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> BIGINT </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setLong(int parameterIndex, long x) throws SQLException;

  /**
     * Sets the designated parameter to the given Java <code>long</code> value.
     * The driver converts this
     * to an SQL <code>BIGINT</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> long </code>值。当驱动程序将其发送到数据库时,将其转换为SQL <code> BIGINT </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getLong
     * @since 1.4
     */
    void setLong(String parameterName, long x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java <code>float</code> value. The driver converts this to
   * an SQL <code>REAL</code> value before sending it to the database.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code> float </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> REAL </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setFloat(int parameterIndex, float x) throws SQLException;

  /**
     * Sets the designated parameter to the given Java <code>float</code> value.
     * The driver converts this
     * to an SQL <code>FLOAT</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> float </code>值。当驱动程序将其发送到数据库时,将其转换为SQL <code> FLOAT </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getFloat
     * @since 1.4
     */
    void setFloat(String parameterName, float x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java <code>double</code> value. The driver converts this to
   * an SQL <code>DOUBLE</code> value before sending it to the database.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code> double </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> DOUBLE </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setDouble(int parameterIndex, double x) throws SQLException;

  /**
     * Sets the designated parameter to the given Java <code>double</code> value.
     * The driver converts this
     * to an SQL <code>DOUBLE</code> value when it sends it to the database.
     *
     * <p>
     * 将指定的参数设置为给定的Java <code> double </code>值。当驱动程序将其发送到数据库时,将其转换为SQL <code> DOUBLE </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getDouble
     * @since 1.4
     */
    void setDouble(String parameterName, double x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>java.math.BigDeciaml</code> value.
   * The driver converts this to
   * an SQL <code>NUMERIC</code> value before sending it to the database.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> java.math.BigDeciaml </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> NUMERIC </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException;

  /**
     * Sets the designated parameter to the given
     * <code>java.math.BigDecimal</code> value.
     * The driver converts this to an SQL <code>NUMERIC</code> value when
     * it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> java.math.BigDecimal </code>值。当驱动程序将其发送到数据库时,将其转换为SQL <code> NUMERIC </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getBigDecimal
     * @since 1.4
     */
    void setBigDecimal(String parameterName, BigDecimal x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java <code>String</code> value. Before sending it to the
   * database, the driver converts this to an SQL <code>VARCHAR</code> or
   * <code>LONGVARCHAR</code> value, depending on the argument's size relative
   * to the driver's limits on <code>VARCHAR</code> values.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code> String </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> VARCHAR </code>或<code> LONGVARCHAR </code>值,具体取决于参数的大小相对于驱动程序对<code> 
   * VARCHAR <代码>值。
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的Java <code> String </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setString(int parameterIndex, String x) throws SQLException;

  /**
     * Sets the designated parameter to the given Java <code>String</code> value.
     * The driver converts this
     * to an SQL <code>VARCHAR</code> or <code>LONGVARCHAR</code> value
     * (depending on the argument's
     * size relative to the driver's limits on <code>VARCHAR</code> values)
     * when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java <code> String </code>值。
     * 驱动程序在发送时将其转换为SQL <code> VARCHAR </code>或<code> LONGVARCHAR </code>值(取决于参数的大小相对于驱动程序对<code> VARCHAR </code>
     * 值的限制)它到数据库。
     *  将指定的参数设置为给定的Java <code> String </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getString
     * @since 1.4
     */
    void setString(String parameterName, String x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given Java array of <code>byte</code> values. Before sending it to the
   * database, the driver converts this to an SQL <code>VARBINARY</code> or
   * <code>LONGVARBINARY</code> value, depending on the argument's size relative
   * to the driver's limits on <code>VARBINARY</code> values.
   *
   * <p>
   * 将<code> RowSet </code>对象的命令中的指定参数设置为<code> byte </code>值的给定Java数组。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> VARBINARY </code>或<code> LONGVARBINARY </code>值,具体取决于参数的大小相对于驱动程序对<code>
   *  VARBINARY <代码>值。
   * 将<code> RowSet </code>对象的命令中的指定参数设置为<code> byte </code>值的给定Java数组。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setBytes(int parameterIndex, byte x[]) throws SQLException;

  /**
     * Sets the designated parameter to the given Java array of bytes.
     * The driver converts this to an SQL <code>VARBINARY</code> or
     * <code>LONGVARBINARY</code> (depending on the argument's size relative
     * to the driver's limits on <code>VARBINARY</code> values) when it sends
     * it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的Java字节数组。
     * 驱动程序将它发送到SQL <code> VARBINARY </code>或<code> LONGVARBINARY </code>(取决于参数的大小相对于驱动程序对<code> VARBINARY </code>
     * 值的限制)到数据库。
     *  将指定的参数设置为给定的Java字节数组。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getBytes
     * @since 1.4
     */
    void setBytes(String parameterName, byte x[]) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>java.sql.Date</code> value. The driver converts this to
   * an SQL <code>DATE</code> value before sending it to the database, using the
   * default <code>java.util.Calendar</code> to calculate the date.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> java.sql.Date </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> DATE </code>值,使用默认的<code> java.util.Calendar </code>来计算日期。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setDate(int parameterIndex, java.sql.Date x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>java.sql.Time</code> value. The driver converts this to
   * an SQL <code>TIME</code> value before sending it to the database, using the
   * default <code>java.util.Calendar</code> to calculate it.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> java.sql.Time </code>值。
   * 在将其发送到数据库之前,驱动程序将其转换为SQL <code> TIME </code>值,并使用默认的<code> java.util.Calendar </code>来计算。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setTime(int parameterIndex, java.sql.Time x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>java.sql.Timestamp</code> value. The driver converts this to
   * an SQL <code>TIMESTAMP</code> value before sending it to the database, using the
   * default <code>java.util.Calendar</code> to calculate it.
   *
   * <p>
   * 将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> java.sql.Timestamp </code>值。
   * 在将其发送到数据库之前,驱动程序将使用默认的<code> java.util.Calendar </code>来计算它,将其转换为SQL <code> TIMESTAMP </code>。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if a database access error occurs
   */
  void setTimestamp(int parameterIndex, java.sql.Timestamp x)
    throws SQLException;

  /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value.
     * The driver
     * converts this to an SQL <code>TIMESTAMP</code> value when it sends it to the
     * database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> java.sql.Timestamp </code>值。当驱动程序将其发送到数据库时,将其转换为SQL <code> TIMESTAMP </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getTimestamp
     * @since 1.4
     */
    void setTimestamp(String parameterName, java.sql.Timestamp x)
        throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>java.io.InputStream</code> value.
   * It may be more practical to send a very large ASCII value via a
   * <code>java.io.InputStream</code> rather than as a <code>LONGVARCHAR</code>
   * parameter. The driver will read the data from the stream
   * as needed until it reaches end-of-file.
   *
   * <P><B>Note:</B> This stream object can either be a standard
   * Java stream object or your own subclass that implements the
   * standard interface.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> java.io.InputStream </code>值。
   * 通过<code> java.io.InputStream </code>而不是作为<code> LONGVARCHAR </code>参数发送非常大的ASCII值可能更实用。
   * 驱动程序将根据需要从流中读取数据,直到它到达文件结束。
   * 
   *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the Java input stream that contains the ASCII parameter value
   * @param length the number of bytes in the stream
   * @exception SQLException if a database access error occurs
   */
  void setAsciiStream(int parameterIndex, java.io.InputStream x, int length)
    throws SQLException;

  /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     *  将指定的参数设置为给定的输入流,其将具有指定的字节数。
     * 当将非常大的ASCII值输入到<code> LONGVARCHAR </code>参数时,通过<code> java.io.InputStream </code>发送它可能更实用。
     * 将根据需要从流中读取数据,直到达到文件结束。 JDBC驱动程序将执行从ASCII到数据库字符格式的任何必要的转换。
     * 
     * <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the Java input stream that contains the ASCII parameter value
     * @param length the number of bytes in the stream
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setAsciiStream(String parameterName, java.io.InputStream x, int length)
        throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>java.io.InputStream</code> value.
   * It may be more practical to send a very large binary value via a
   * <code>java.io.InputStream</code> rather than as a <code>LONGVARBINARY</code>
   * parameter. The driver will read the data from the stream
   * as needed until it reaches end-of-file.
   *
   * <P><B>Note:</B> This stream object can either be a standard
   * Java stream object or your own subclass that implements the
   * standard interface.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> java.io.InputStream </code>值。
   * 通过<code> java.io.InputStream </code>而不是作为<code> LONGVARBINARY </code>参数发送非常大的二进制值可能更实用。
   * 驱动程序将根据需要从流中读取数据,直到它到达文件结束。
   * 
   *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the java input stream which contains the binary parameter value
   * @param length the number of bytes in the stream
   * @exception SQLException if a database access error occurs
   */
  void setBinaryStream(int parameterIndex, java.io.InputStream x,
                       int length) throws SQLException;

  /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     *  将指定的参数设置为给定的输入流,其将具有指定的字节数。
     * 当将非常大的二进制值输入到<code> LONGVARBINARY </code>参数时,通过<code> java.io.InputStream </code>对象发送它可能更实用。
     * 将根据需要从流中读取数据,直到达到文件结束。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the java input stream which contains the binary parameter value
     * @param length the number of bytes in the stream
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setBinaryStream(String parameterName, java.io.InputStream x,
                         int length) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>java.io.Reader</code> value.
   * It may be more practical to send a very large UNICODE value via a
   * <code>java.io.Reader</code> rather than as a <code>LONGVARCHAR</code>
   * parameter. The driver will read the data from the stream
   * as needed until it reaches end-of-file.
   *
   * <P><B>Note:</B> This stream object can either be a standard
   * Java stream object or your own subclass that implements the
   * standard interface.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> java.io.Reader </code>值。
   * 通过<code> java.io.Reader </code>而不是作为<code> LONGVARCHAR </code>参数发送非常大的UNICODE值可能更实用。
   * 驱动程序将根据需要从流中读取数据,直到它到达文件结束。
   * 
   * <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param reader the <code>Reader</code> object that contains the UNICODE data
   *        to be set
   * @param length the number of characters in the stream
   * @exception SQLException if a database access error occurs
   */
  void setCharacterStream(int parameterIndex,
                          Reader reader,
                          int length) throws SQLException;

  /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object, which is the given number of characters long.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     *
     * <p>
     *  将指定的参数设置为给定的<code> Reader </code>对象,这是给定的字符数。
     * 当将非常大的UNICODE值输入到<code> LONGVARCHAR </code>参数时,通过<code> java.io.Reader </code>对象发送它可能更实用。
     * 将根据需要从流中读取数据,直到达到文件结束。 JDBC驱动程序将执行从UNICODE到数据库char格式的任何必要的转换。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader the <code>java.io.Reader</code> object that
     *        contains the UNICODE data used as the designated parameter
     * @param length the number of characters in the stream
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void setCharacterStream(String parameterName,
                            java.io.Reader reader,
                            int length) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given input stream.
   * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
   * parameter, it may be more practical to send it via a
   * <code>java.io.InputStream</code>. Data will be read from the stream
   * as needed until end-of-file is reached.  The JDBC driver will
   * do any necessary conversion from ASCII to the database char format.
   *
   * <P><B>Note:</B> This stream object can either be a standard
   * Java stream object or your own subclass that implements the
   * standard interface.
   * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
   * it might be more efficient to use a version of
   * <code>setAsciiStream</code> which takes a length parameter.
   *
   * <p>
   *  将这个<code> RowSet </code>对象的命令中的指定参数设置为给定的输入流。
   * 当将非常大的ASCII值输入到<code> LONGVARCHAR </code>参数时,通过<code> java.io.InputStream </code>发送它可能更实用。
   * 将根据需要从流中读取数据,直到达到文件结束。 JDBC驱动程序将执行从ASCII到数据库字符格式的任何必要的转换。
   * 
   *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
   *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否可以使用一个使用length参数的<code> setAsciiStream </code>版本更高效。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the Java input stream that contains the ASCII parameter value
   * @exception SQLException if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
  void setAsciiStream(int parameterIndex, java.io.InputStream x)
                      throws SQLException;

   /**
     * Sets the designated parameter to the given input stream.
     * When a very large ASCII value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from ASCII to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setAsciiStream</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为给定的输入流。
     * 当将非常大的ASCII值输入到<code> LONGVARCHAR </code>参数时,通过<code> java.io.InputStream </code>发送它可能更实用。
     * 将根据需要从流中读取数据,直到达到文件结束。 JDBC驱动程序将执行从ASCII到数据库字符格式的任何必要的转换。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
     *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否可以使用一个使用length参数的<code> setAsciiStream </code>版本更高效。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the Java input stream that contains the ASCII parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
       * @since 1.6
    */
    void setAsciiStream(String parameterName, java.io.InputStream x)
            throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given input stream.
   * When a very large binary value is input to a <code>LONGVARBINARY</code>
   * parameter, it may be more practical to send it via a
   * <code>java.io.InputStream</code> object. The data will be read from the
   * stream as needed until end-of-file is reached.
   *
   * <P><B>Note:</B> This stream object can either be a standard
   * Java stream object or your own subclass that implements the
   * standard interface.
   * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
   * it might be more efficient to use a version of
   * <code>setBinaryStream</code> which takes a length parameter.
   *
   * <p>
   *  将这个<code> RowSet </code>对象的命令中的指定参数设置为给定的输入流。
   * 当将非常大的二进制值输入到<code> LONGVARBINARY </code>参数时,通过<code> java.io.InputStream </code>对象发送它可能更实用。
   * 将根据需要从流中读取数据,直到达到文件结束。
   * 
   *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
   *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否可以使用一个使用length参数的<code> setBinaryStream </code>版本更高效。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the java input stream which contains the binary parameter value
   * @exception SQLException if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
  void setBinaryStream(int parameterIndex, java.io.InputStream x)
                       throws SQLException;

  /**
     * Sets the designated parameter to the given input stream.
     * When a very large binary value is input to a <code>LONGVARBINARY</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.InputStream</code> object. The data will be read from the
     * stream as needed until end-of-file is reached.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBinaryStream</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为给定的输入流。
     * 当将非常大的二进制值输入到<code> LONGVARBINARY </code>参数时,通过<code> java.io.InputStream </code>对象发送它可能更实用。
     * 将根据需要从流中读取数据,直到达到文件结束。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
     *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否可以使用一个使用length参数的<code> setBinaryStream </code>版本更高效。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the java input stream which contains the binary parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
    void setBinaryStream(String parameterName, java.io.InputStream x)
    throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to the given <code>Reader</code>
   * object.
   * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
   * parameter, it may be more practical to send it via a
   * <code>java.io.Reader</code> object. The data will be read from the stream
   * as needed until end-of-file is reached.  The JDBC driver will
   * do any necessary conversion from UNICODE to the database char format.
   *
   * <P><B>Note:</B> This stream object can either be a standard
   * Java stream object or your own subclass that implements the
   * standard interface.
   * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
   * it might be more efficient to use a version of
   * <code>setCharacterStream</code> which takes a length parameter.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为给定的<code> Reader </code>对象。
   * 当将非常大的UNICODE值输入到<code> LONGVARCHAR </code>参数时,通过<code> java.io.Reader </code>对象发送它可能更实用。
   * 将根据需要从流中读取数据,直到达到文件结束。 JDBC驱动程序将执行从UNICODE到数据库char格式的任何必要的转换。
   * 
   *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
   *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否可以使用一个带有长度参数的<code> setCharacterStream </code>版本更高效。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param reader the <code>java.io.Reader</code> object that contains the
   *        Unicode data
   * @exception SQLException if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
  void setCharacterStream(int parameterIndex,
                          java.io.Reader reader) throws SQLException;

  /**
     * Sets the designated parameter to the given <code>Reader</code>
     * object.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached.  The JDBC driver will
     * do any necessary conversion from UNICODE to the database char format.
     *
     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setCharacterStream</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为给定的<code> Reader </code>对象。
     * 当将非常大的UNICODE值输入到<code> LONGVARCHAR </code>参数时,通过<code> java.io.Reader </code>对象发送它可能更实用。
     * 将根据需要从流中读取数据,直到达到文件结束。 JDBC驱动程序将执行从UNICODE到数据库char格式的任何必要的转换。
     * 
     *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
     *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否可以使用一个带有长度参数的<code> setCharacterStream </code>版本更高效。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader the <code>java.io.Reader</code> object that contains the
     *        Unicode data
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
    void setCharacterStream(String parameterName,
                          java.io.Reader reader) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * to a <code>Reader</code> object. The
   * <code>Reader</code> reads the data till end-of-file is reached. The
   * driver does the necessary conversion from Java character format to
   * the national character set in the database.

   * <P><B>Note:</B> This stream object can either be a standard
   * Java stream object or your own subclass that implements the
   * standard interface.
   * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
   * it might be more efficient to use a version of
   * <code>setNCharacterStream</code> which takes a length parameter.
   *
   * <p>
   *  将<code> RowSet </code>对象的命令中的指定参数设置为<code> Reader </code>对象。 <code> Reader </code>读取数据,直到达到文件结束。
   * 驱动程序执行必要的从Java字符格式到数据库中的国家字符集的转换。
   * 
   *  <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
   *  <P> <B>注意：</B>请参阅您的JDBC驱动程序文档,以确定是否可以使用一个使用length参数的<code> setNCharacterStream </code>版本更高效。
   * 
   * 
   * @param parameterIndex of the first parameter is 1, the second is 2, ...
   * @param value the parameter value
   * @throws SQLException if the driver does not support national
   *         character sets;  if the driver can detect that a data conversion
   *  error could occur ; if a database access error occurs; or
   * this method is called on a closed <code>PreparedStatement</code>
   * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
   * @since 1.6
   */
   void setNCharacterStream(int parameterIndex, Reader value) throws SQLException;



  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with the given Java <code>Object</code>.  For integral values, the
   * <code>java.lang</code> equivalent objects should be used (for example,
   * an instance of the class <code>Integer</code> for an <code>int</code>).
   *
   * If the second argument is an <code>InputStream</code> then the stream must contain
   * the number of bytes specified by scaleOrLength.  If the second argument is a
   * <code>Reader</code> then the reader must contain the number of characters specified    * by scaleOrLength. If these conditions are not true the driver will generate a
   * <code>SQLException</code> when the prepared statement is executed.
   *
   * <p>The given Java object will be converted to the targetSqlType
   * before being sent to the database.
   * <P>
   * If the object is of a class implementing <code>SQLData</code>,
   * the rowset should call the method <code>SQLData.writeSQL</code>
   * to write the object to an <code>SQLOutput</code> data stream.
   * If, on the other hand, the object is of a class implementing
   * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
   *  <code>Struct</code>, <code>java.net.URL</code>,
   * or <code>Array</code>, the driver should pass it to the database as a
   * value of the corresponding SQL type.
   *
   *
   * <p>Note that this method may be used to pass datatabase-specific
   * abstract data types.
   *
   * <p>
   * 使用给定的Java <code> Object </code>,在此<<code> RowSet </code>对象的命令中设置指定的参数。
   * 对于整数值,应该使用<code> java.lang </code>等效对象(例如,<code> int </code>类的<code> Integer </code>类的实例)。
   * 
   *  如果第二个参数是<code> InputStream </code>,那么流必须包含由scaleOrLength指定的字节数。
   * 如果第二个参数是<code> Reader </code>,则读者必须包含由scaleOrLength指定的字符数。
   * 如果这些条件不成立,驱动程序将在执行准备语句时生成<code> SQLException </code>。
   * 
   *  <p>给定的Java对象在发送到数据库之前将被转换为targetSqlType。
   * <P>
   *  如果对象是实现<code> SQLData </code>的类,则行集应调用<code> SQLData.writeSQL </code>方法将对象写入<code> SQLOutput </code>
   * 数据流。
   * 另一方面,如果对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> NClob </code> <code> St
   * ruct </code>,<code> java.net.URL </code>或<code> Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
   * 
   *  <p>请注意,此方法可用于传递特定于数据库的抽象数据类型。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the object containing the input parameter value
   * @param targetSqlType the SQL type (as defined in <code>java.sql.Types</code>)
   *        to be sent to the database. The scale argument may further qualify this
   *        type.
   * @param scaleOrLength for <code>java.sql.Types.DECIMAL</code>
   *          or <code>java.sql.Types.NUMERIC types</code>,
   *          this is the number of digits after the decimal point. For
   *          Java Object types <code>InputStream</code> and <code>Reader</code>,
   *          this is the length
   *          of the data in the stream or reader.  For all other types,
   *          this value will be ignored.
   * @exception SQLException if a database access error occurs
   * @see java.sql.Types
   */
  void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength)
            throws SQLException;

  /**
     * Sets the value of the designated parameter with the given object. The second
     * argument must be an object type; for integral values, the
     * <code>java.lang</code> equivalent objects should be used.
     *
     * <p>The given Java object will be converted to the given targetSqlType
     * before being sent to the database.
     *
     * If the object has a custom mapping (is of a class implementing the
     * interface <code>SQLData</code>),
     * the JDBC driver should call the method <code>SQLData.writeSQL</code> to write it
     * to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
     *  <code>Struct</code>, <code>java.net.URL</code>,
     * or <code>Array</code>, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <P>
     * Note that this method may be used to pass datatabase-
     * specific abstract data types.
     *
     * <p>
     * 使用给定对象设置指定参数的值。第二个参数必须是对象类型;对于整数值,应使用<code> java.lang </code>等效对象。
     * 
     *  <p>给定的Java对象在发送到数据库之前将被转换为给定的targetSqlType。
     * 
     *  如果对象具有自定义映射(是实现接口<code> SQLData </code>的类),JDBC驱动程序应调用<code> SQLData.writeSQL </code>方法将其写入SQL数据流。
     * 另一方面,如果对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> NClob </code> <code> St
     * ruct </code>,<code> java.net.URL </code>或<code> Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
     *  如果对象具有自定义映射(是实现接口<code> SQLData </code>的类),JDBC驱动程序应调用<code> SQLData.writeSQL </code>方法将其写入SQL数据流。
     * <P>
     *  请注意,此方法可用于传递特定于数据库的抽象数据类型。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be
     * sent to the database. The scale argument may further qualify this type.
     * @param scale for java.sql.Types.DECIMAL or java.sql.Types.NUMERIC types,
     *          this is the number of digits after the decimal point.  For all other
     *          types, this value will be ignored.
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if <code>targetSqlType</code> is
     * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     * or  <code>STRUCT</code> data type and the JDBC driver does not support
     * this data type
     * @see Types
     * @see #getObject
     * @since 1.4
     */
    void setObject(String parameterName, Object x, int targetSqlType, int scale)
        throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with a Java <code>Object</code>.  For integral values, the
   * <code>java.lang</code> equivalent objects should be used.
   * This method is like <code>setObject</code> above, but the scale used is the scale
   * of the second parameter.  Scalar values have a scale of zero.  Literal
   * values have the scale present in the literal.
   * <P>
   * Even though it is supported, it is not recommended that this method
   * be called with floating point input values.
   *
   * <p>
   *  使用Java <code> Object </code>设置此<code> RowSet </code>对象的命令中的指定参数。
   * 对于整数值,应使用<code> java.lang </code>等效对象。这个方法就像上面的<code> setObject </code>,但是使用的比例是第二个参数的比例。标量值的标度为零。
   * 字面值具有字面量表。
   * <P>
   *  即使支持它,也不建议使用浮点输入值来调用此方法。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the object containing the input parameter value
   * @param targetSqlType the SQL type (as defined in <code>java.sql.Types</code>)
   *        to be sent to the database
   * @exception SQLException if a database access error occurs
   */
  void setObject(int parameterIndex, Object x,
                 int targetSqlType) throws SQLException;

  /**
     * Sets the value of the designated parameter with the given object.
     * This method is like the method <code>setObject</code>
     * above, except that it assumes a scale of zero.
     *
     * <p>
     * 使用给定对象设置指定参数的值。这个方法就像上面的方法<code> setObject </code>,除了它假定一个零的标度。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the object containing the input parameter value
     * @param targetSqlType the SQL type (as defined in java.sql.Types) to be
     *                      sent to the database
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if <code>targetSqlType</code> is
     * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
     * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
     * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     * or  <code>STRUCT</code> data type and the JDBC driver does not support
     * this data type
     * @see #getObject
     * @since 1.4
     */
    void setObject(String parameterName, Object x, int targetSqlType)
        throws SQLException;

   /**
     * Sets the value of the designated parameter with the given object.
     * The second parameter must be of type <code>Object</code>; therefore, the
     * <code>java.lang</code> equivalent objects should be used for built-in types.
     *
     * <p>The JDBC specification specifies a standard mapping from
     * Java <code>Object</code> types to SQL types.  The given argument
     * will be converted to the corresponding SQL type before being
     * sent to the database.
     *
     * <p>Note that this method may be used to pass datatabase-
     * specific abstract data types, by using a driver-specific Java
     * type.
     *
     * If the object is of a class implementing the interface <code>SQLData</code>,
     * the JDBC driver should call the method <code>SQLData.writeSQL</code>
     * to write it to the SQL data stream.
     * If, on the other hand, the object is of a class implementing
     * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
     *  <code>Struct</code>, <code>java.net.URL</code>,
     * or <code>Array</code>, the driver should pass it to the database as a
     * value of the corresponding SQL type.
     * <P>
     * This method throws an exception if there is an ambiguity, for example, if the
     * object is of a class implementing more than one of the interfaces named above.
     *
     * <p>
     *  使用给定对象设置指定参数的值。第二个参数必须是<code> Object </code>类型;因此,<code> java.lang </code>等效对象应该用于内置类型。
     * 
     *  <p> JDBC规范指定从Java <code> Object </code>类型到SQL类型的标准映射。给定的参数在发送到数据库之前将被转换为相应的SQL类型。
     * 
     *  <p>请注意,此方法可用于通过使用特定于驱动程序的Java类型传递特定于数据库的抽象数据类型。
     * 
     *  如果对象是实现接口<code> SQLData </code>的类,JDBC驱动程序应调用<code> SQLData.writeSQL </code>方法将其写入SQL数据流。
     * 另一方面,如果对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> NClob </code> <code> St
     * ruct </code>,<code> java.net.URL </code>或<code> Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
     *  如果对象是实现接口<code> SQLData </code>的类,JDBC驱动程序应调用<code> SQLData.writeSQL </code>方法将其写入SQL数据流。
     * <P>
     *  该方法在存在歧义时抛出异常,例如,如果对象是实现上面命名的多个接口的类。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the object containing the input parameter value
     * @exception SQLException if a database access error occurs,
     * this method is called on a closed <code>CallableStatement</code> or if the given
     *            <code>Object</code> parameter is ambiguous
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getObject
     * @since 1.4
     */
    void setObject(String parameterName, Object x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with a Java <code>Object</code>.  For integral values, the
   * <code>java.lang</code> equivalent objects should be used.
   *
   * <p>The JDBC specification provides a standard mapping from
   * Java Object types to SQL types.  The driver will convert the
   * given Java object to its standard SQL mapping before sending it
   * to the database.
   *
   * <p>Note that this method may be used to pass datatabase-specific
   * abstract data types by using a driver-specific Java type.
   *
   * If the object is of a class implementing <code>SQLData</code>,
   * the rowset should call the method <code>SQLData.writeSQL</code>
   * to write the object to an <code>SQLOutput</code> data stream.
   * If, on the other hand, the object is of a class implementing
   * <code>Ref</code>, <code>Blob</code>, <code>Clob</code>,  <code>NClob</code>,
   *  <code>Struct</code>, <code>java.net.URL</code>,
   * or <code>Array</code>, the driver should pass it to the database as a
   * value of the corresponding SQL type.
   *
   * <P>
   * An exception is thrown if there is an ambiguity, for example, if the
   * object is of a class implementing more than one of these interfaces.
   *
   * <p>
   * 使用Java <code> Object </code>设置此<code> RowSet </code>对象的命令中的指定参数。
   * 对于整数值,应使用<code> java.lang </code>等效对象。
   * 
   *  <p> JDBC规范提供了从Java对象类型到SQL类型的标准映射。在将给定的Java对象发送到数据库之前,驱动程序将其转换为其标准SQL映射。
   * 
   *  <p>请注意,此方法可用于通过使用驱动程序特定的Java类型传递特定于数据库的抽象数据类型。
   * 
   *  如果对象是实现<code> SQLData </code>的类,则行集应调用<code> SQLData.writeSQL </code>方法将对象写入<code> SQLOutput </code>
   * 数据流。
   * 另一方面,如果对象是实现<code> Ref </code>,<code> Blob </code>,<code> Clob </code>,<code> NClob </code> <code> St
   * ruct </code>,<code> java.net.URL </code>或<code> Array </code>,驱动程序应将其作为相应SQL类型的值传递到数据库。
   * 
   * <P>
   *  如果存在歧义,则抛出异常,例如,如果对象是实现多个这些接口的类。
   * 
   * 
   * @param parameterIndex The first parameter is 1, the second is 2, ...
   * @param x The object containing the input parameter value
   * @exception SQLException if a database access error occurs
   */
  void setObject(int parameterIndex, Object x) throws SQLException;


  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with the given  <code>Ref</code> value.  The driver will convert this
   * to the appropriate <code>REF(&lt;structured-type&gt;)</code> value.
   *
   * <p>
   *  使用给定的<code> Ref </code>值,在<code> RowSet </code>对象的命令中设置指定的参数。
   * 驱动程序将其转换为适当的<code> REF(&lt; structured-type&gt;)</code>值。
   * 
   * 
   * @param i the first parameter is 1, the second is 2, ...
   * @param x an object representing data of an SQL <code>REF</code> type
   * @exception SQLException if a database access error occurs
   */
  void setRef (int i, Ref x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with the given  <code>Blob</code> value.  The driver will convert this
   * to the <code>BLOB</code> value that the <code>Blob</code> object
   * represents before sending it to the database.
   *
   * <p>
   * 使用给定的<code> Blob </code>值,在<code> RowSet </code>对象的命令中设置指定的参数。
   * 在将它发送到数据库之前,驱动程序会将其转换为<code> Blob </code>对象表示的<code> BLOB </code>值。
   * 
   * 
   * @param i the first parameter is 1, the second is 2, ...
   * @param x an object representing a BLOB
   * @exception SQLException if a database access error occurs
   */
  void setBlob (int i, Blob x) throws SQLException;

  /**
     * Sets the designated parameter to a <code>InputStream</code> object.  The inputstream must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>PreparedStatement</code> is executed.
     * This method differs from the <code>setBinaryStream (int, InputStream, int)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     * <p>
     *  将指定的参数设置为<code> InputStream </code>对象。
     * 输入流必须包含由长度指定的字符数,否则执行<code> PreparedStatement </code>时将生成<code> SQLException </code>。
     * 此方法与<code> setBinaryStream(int,InputStream,int)</code>方法不同,因为它通知驱动程序应将参数值作为<code> BLOB </code>发送到服务器。
     * 输入流必须包含由长度指定的字符数,否则执行<code> PreparedStatement </code>时将生成<code> SQLException </code>。
     * 当使用<code> setBinaryStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGVARBINARY </code>或<code> BL
     * OB </code>。
     * 输入流必须包含由长度指定的字符数,否则执行<code> PreparedStatement </code>时将生成<code> SQLException </code>。
     * 
     * 
     * @param parameterIndex index of the first parameter is 1,
     * the second is 2, ...
     * @param inputStream An object that contains the data to set the parameter
     * value to.
     * @param length the number of bytes in the parameter data.
     * @throws SQLException if a database access error occurs,
     * this method is called on a closed <code>PreparedStatement</code>,
     * if parameterIndex does not correspond
     * to a parameter marker in the SQL statement,  if the length specified
     * is less than zero or if the number of bytes in the inputstream does not match
     * the specified length.
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     *
     * @since 1.6
     */
     void setBlob(int parameterIndex, InputStream inputStream, long length)
        throws SQLException;

  /**
     * Sets the designated parameter to a <code>InputStream</code> object.
     * This method differs from the <code>setBinaryStream (int, InputStream)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBlob</code> which takes a length parameter.
     *
     * <p>
     *  将指定的参数设置为<code> InputStream </code>对象。
     * 此方法与<code> setBinaryStream(int,InputStream)</code>方法不同,因为它通知驱动程序应将参数值作为<code> BLOB </code>发送到服务器。
     * 当使用<code> setBinaryStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGVARBINARY </code>或<code> BL
     * OB </code>。
     * 此方法与<code> setBinaryStream(int,InputStream)</code>方法不同,因为它通知驱动程序应将参数值作为<code> BLOB </code>发送到服务器。
     * 
     * <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否使用一个带有长度参数的<code> setBlob </code>版本更高效。
     * 
     * 
     * @param parameterIndex index of the first parameter is 1,
     * the second is 2, ...
     * @param inputStream An object that contains the data to set the parameter
     * value to.
     * @throws SQLException if a database access error occurs,
     * this method is called on a closed <code>PreparedStatement</code> or
     * if parameterIndex does not correspond
     * to a parameter marker in the SQL statement,
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     *
     * @since 1.6
     */
     void setBlob(int parameterIndex, InputStream inputStream)
        throws SQLException;

  /**
     * Sets the designated parameter to a <code>InputStream</code> object.  The <code>inputstream</code> must contain  the number
     * of characters specified by length, otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setBinaryStream (int, InputStream, int)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     *
     * <p>
     *  将指定的参数设置为<code> InputStream </code>对象。
     *  <code> inputstream </code>必须包含由length指定的字符数,否则将在执行<code> CallableStatement </code>时生成<code> SQLExcep
     * tion </code>。
     *  将指定的参数设置为<code> InputStream </code>对象。
     * 此方法与<code> setBinaryStream(int,InputStream,int)</code>方法不同,因为它通知驱动程序应将参数值作为<code> BLOB </code>发送到服务器。
     *  将指定的参数设置为<code> InputStream </code>对象。
     * 当使用<code> setBinaryStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGVARBINARY </code>或<code> BL
     * OB </code>。
     *  将指定的参数设置为<code> InputStream </code>对象。
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * the second is 2, ...
     *
     * @param inputStream An object that contains the data to set the parameter
     * value to.
     * @param length the number of bytes in the parameter data.
     * @throws SQLException  if parameterIndex does not correspond
     * to a parameter marker in the SQL statement,  or if the length specified
     * is less than zero; if the number of bytes in the inputstream does not match
     * the specified length; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     *
     * @since 1.6
     */
     void setBlob(String parameterName, InputStream inputStream, long length)
        throws SQLException;

  /**
     * Sets the designated parameter to the given <code>java.sql.Blob</code> object.
     * The driver converts this to an SQL <code>BLOB</code> value when it
     * sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> java.sql.Blob </code>对象。当驱动程序将其发送到数据库时,将其转换为SQL <code> BLOB </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x a <code>Blob</code> object that maps an SQL <code>BLOB</code> value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setBlob (String parameterName, Blob x) throws SQLException;

  /**
     * Sets the designated parameter to a <code>InputStream</code> object.
     * This method differs from the <code>setBinaryStream (int, InputStream)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>BLOB</code>.  When the <code>setBinaryStream</code> method is used,
     * the driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARBINARY</code> or a <code>BLOB</code>
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setBlob</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为<code> InputStream </code>对象。
     * 此方法与<code> setBinaryStream(int,InputStream)</code>方法不同,因为它通知驱动程序应将参数值作为<code> BLOB </code>发送到服务器。
     * 当使用<code> setBinaryStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应作为<code> LONGVARBINARY </code>或<code> BL
     * OB </code>。
     * 此方法与<code> setBinaryStream(int,InputStream)</code>方法不同,因为它通知驱动程序应将参数值作为<code> BLOB </code>发送到服务器。
     * 
     *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否使用一个带有长度参数的<code> setBlob </code>版本更高效。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param inputStream An object that contains the data to set the parameter
     * value to.
     * @throws SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     *
     * @since 1.6
     */
     void setBlob(String parameterName, InputStream inputStream)
        throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with the given  <code>Clob</code> value.  The driver will convert this
   * to the <code>CLOB</code> value that the <code>Clob</code> object
   * represents before sending it to the database.
   *
   * <p>
   *  使用给定的<code> Clob </code>值在此<code> RowSet </code>对象的命令中设置指定的参数。
   * 在将它发送到数据库之前,驱动程序会将其转换为<code> Clob </code>对象表示的<code> CLOB </code>值。
   * 
   * 
   * @param i the first parameter is 1, the second is 2, ...
   * @param x an object representing a CLOB
   * @exception SQLException if a database access error occurs
   */
  void setClob (int i, Clob x) throws SQLException;

  /**
     * Sets the designated parameter to a <code>Reader</code> object.  The reader must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>PreparedStatement</code> is executed.
     *This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象。
     * 阅读器必须包含长度指定的字符数,否则执行<code> PreparedStatement </code>时会生成<code> SQLException </code>。
     * 他的方法不同于<code> setCharacterStream(int,Reader,int)</code>方法,因为它通知驱动程序应该将参数值作为<code> CLOB </code>发送到服务器。
     * 阅读器必须包含长度指定的字符数,否则执行<code> PreparedStatement </code>时会生成<code> SQLException </code>。
     * 当使用<code> setCharacterStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGVARCHAR </code>或<code> C
     * LOB发送到服务器</code>。
     * 阅读器必须包含长度指定的字符数,否则执行<code> PreparedStatement </code>时会生成<code> SQLException </code>。
     * 
     * 
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader An object that contains the data to set the parameter value to.
     * @param length the number of characters in the parameter data.
     * @throws SQLException if a database access error occurs, this method is called on
     * a closed <code>PreparedStatement</code>, if parameterIndex does not correspond to a parameter
     * marker in the SQL statement, or if the length specified is less than zero.
     *
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
     void setClob(int parameterIndex, Reader reader, long length)
       throws SQLException;

  /**
     * Sets the designated parameter to a <code>Reader</code> object.
     * This method differs from the <code>setCharacterStream (int, Reader)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setClob</code> which takes a length parameter.
     *
     * <p>
     *  将指定的参数设置为<code> Reader </code>对象。
     * 此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值作为<code> CLOB </code>发送到服务器。
     * 当使用<code> setCharacterStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGVARCHAR </code>或<code> C
     * LOB发送到服务器</code>。
     * 此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值作为<code> CLOB </code>发送到服务器。
     * 
     *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否可以使用一个使用length参数的<code> setClob </code>版本更高效。
     * 
     * 
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader An object that contains the data to set the parameter value to.
     * @throws SQLException if a database access error occurs, this method is called on
     * a closed <code>PreparedStatement</code>or if parameterIndex does not correspond to a parameter
     * marker in the SQL statement
     *
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
     void setClob(int parameterIndex, Reader reader)
       throws SQLException;

  /**
     * Sets the designated parameter to a <code>Reader</code> object.  The <code>reader</code> must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象。
     *  <code> reader </code>必须包含由长度指定的字符数,否则在执行<code> CallableStatement </code>时会生成<code> SQLException </code>
     * 。
     * 将指定的参数设置为<code> Reader </code>对象。
     * 此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序应将参数值作为<code> CLOB </code>发送到服务器。
     * 当使用<code> setCharacterStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应该作为<code> LONGVARCHAR </code>或<code> 
     * CLOB </code>。
     * 此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序应将参数值作为<code> CLOB </code>发送到服务器。
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * @param reader An object that contains the data to set the parameter value to.
     * @param length the number of characters in the parameter data.
     * @throws SQLException if parameterIndex does not correspond to a parameter
     * marker in the SQL statement; if the length specified is less than zero;
     * a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     *
     * @since 1.6
     */
     void setClob(String parameterName, Reader reader, long length)
       throws SQLException;

   /**
     * Sets the designated parameter to the given <code>java.sql.Clob</code> object.
     * The driver converts this to an SQL <code>CLOB</code> value when it
     * sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> java.sql.Clob </code>对象。当驱动程序将其发送到数据库时,将其转换为SQL <code> CLOB </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x a <code>Clob</code> object that maps an SQL <code>CLOB</code> value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
    void setClob (String parameterName, Clob x) throws SQLException;

  /**
     * Sets the designated parameter to a <code>Reader</code> object.
     * This method differs from the <code>setCharacterStream (int, Reader)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>CLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     *
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setClob</code> which takes a length parameter.
     *
     * <p>
     *  将指定的参数设置为<code> Reader </code>对象。
     * 此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值作为<code> CLOB </code>发送到服务器。
     * 当使用<code> setCharacterStream </code>方法时,驱动程序可能需要做额外的工作来确定参数数据是否应该作为<code> LONGVARCHAR </code>或<code> 
     * CLOB </code>。
     * 此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值作为<code> CLOB </code>发送到服务器。
     * 
     *  <P> <B>注意：</B>请查看您的JDBC驱动程序文档,以确定是否可以使用一个使用length参数的<code> setClob </code>版本更高效。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader An object that contains the data to set the parameter value to.
     * @throws SQLException if a database access error occurs or this method is called on
     * a closed <code>CallableStatement</code>
     *
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
     void setClob(String parameterName, Reader reader)
       throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with the given  <code>Array</code> value.  The driver will convert this
   * to the <code>ARRAY</code> value that the <code>Array</code> object
   * represents before sending it to the database.
   *
   * <p>
   * 使用给定的<code> Array </code>值设置此<<code> RowSet </code>对象的命令中的指定参数。
   * 驱动程序将它转换为<code> ARRAY </code>对象表示的<code> Array </code>值,然后发送到数据库。
   * 
   * 
   * @param i the first parameter is 1, the second is 2, ...
   * @param x an object representing an SQL array
   * @exception SQLException if a database access error occurs
   */
  void setArray (int i, Array x) throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with the given  <code>java.sql.Date</code> value.  The driver will convert this
   * to an SQL <code>DATE</code> value, using the given <code>java.util.Calendar</code>
   * object to calculate the date.
   *
   * <p>
   *  使用给定的<code> java.sql.Date </code>值设置此<<code> RowSet </code>对象的命令中的指定参数。
   * 驱动程序将使用给定的<code> java.util.Calendar </code>对象将此转换为SQL <code> DATE </code>值,以计算日期。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @param cal the <code>java.util.Calendar</code> object to use for calculating the date
   * @exception SQLException if a database access error occurs
   */
  void setDate(int parameterIndex, java.sql.Date x, Calendar cal)
    throws SQLException;

  /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value
     * using the default time zone of the virtual machine that is running
     * the application.
     * The driver converts this
     * to an SQL <code>DATE</code> value when it sends it to the database.
     *
     * <p>
     *  使用运行应用程序的虚拟机的默认时区将指定的参数设置为给定的<code> java.sql.Date </code>值。
     * 当驱动程序将其发送到数据库时,将其转换为SQL <code> DATE </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getDate
     * @since 1.4
     */
    void setDate(String parameterName, java.sql.Date x)
        throws SQLException;

  /**
     * Sets the designated parameter to the given <code>java.sql.Date</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>DATE</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the date
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * <p>
     *  使用给定的<code> Calendar </code>对象将指定的参数设置为给定的<code> java.sql.Date </code>值。
     * 驱动程序使用<code> Calendar </code>对象来构造一个SQL <code> DATE </code>值,然后驱动程序发送到数据库。
     * 使用<code> Calendar </code>对象,驱动程序可以计算考虑自定义时区的日期。
     * 如果未指定<code> Calendar </code>对象,则驱动程序将使用默认时区,即运行应用程序的虚拟机的时区。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the date
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getDate
     * @since 1.4
     */
    void setDate(String parameterName, java.sql.Date x, Calendar cal)
        throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with the given  <code>java.sql.Time</code> value.  The driver will convert this
   * to an SQL <code>TIME</code> value, using the given <code>java.util.Calendar</code>
   * object to calculate it, before sending it to the database.
   *
   * <p>
   * 使用给定的<code> java.sql.Time </code>值在此<<code> RowSet </code>对象的命令中设置指定的参数。
   * 在将其发送到数据库之前,驱动程序将使用给定的<code> java.util.Calendar </code>对象将其转换为SQL <code> TIME </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @param cal the <code>java.util.Calendar</code> object to use for calculating the time
   * @exception SQLException if a database access error occurs
   */
  void setTime(int parameterIndex, java.sql.Time x, Calendar cal)
    throws SQLException;

  /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value.
     * The driver converts this
     * to an SQL <code>TIME</code> value when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> java.sql.Time </code>值。当驱动程序将其发送到数据库时,将其转换为SQL <code> TIME </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getTime
     * @since 1.4
     */
    void setTime(String parameterName, java.sql.Time x)
        throws SQLException;

  /**
     * Sets the designated parameter to the given <code>java.sql.Time</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIME</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the time
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * <p>
     *  使用给定的<code> Calendar </code>对象将指定的参数设置为给定的<code> java.sql.Time </code>值。
     * 驱动程序使用<code> Calendar </code>对象来构造一个SQL <code> TIME </code>值,然后驱动程序发送到数据库。
     * 使用<code> Calendar </code>对象,驱动程序可以计算考虑自定义时区的时间。
     * 如果未指定<code> Calendar </code>对象,则驱动程序将使用默认时区,即运行应用程序的虚拟机的时区。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the time
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getTime
     * @since 1.4
     */
    void setTime(String parameterName, java.sql.Time x, Calendar cal)
        throws SQLException;

  /**
   * Sets the designated parameter in this <code>RowSet</code> object's command
   * with the given  <code>java.sql.Timestamp</code> value.  The driver will
   * convert this to an SQL <code>TIMESTAMP</code> value, using the given
   * <code>java.util.Calendar</code> object to calculate it, before sending it to the
   * database.
   *
   * <p>
   *  使用给定的<code> java.sql.Timestamp </code>值,在此<<code> RowSet </code>对象的命令中设置指定的参数。
   * 在将其发送到数据库之前,驱动程序将使用给定的<code> java.util.Calendar </code>对象将其转换为SQL <code> TIMESTAMP </code>值。
   * 
   * 
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @param cal the <code>java.util.Calendar</code> object to use for calculating the
   *        timestamp
   * @exception SQLException if a database access error occurs
   */
  void setTimestamp(int parameterIndex, java.sql.Timestamp x, Calendar cal)
    throws SQLException;

  /**
     * Sets the designated parameter to the given <code>java.sql.Timestamp</code> value,
     * using the given <code>Calendar</code> object.  The driver uses
     * the <code>Calendar</code> object to construct an SQL <code>TIMESTAMP</code> value,
     * which the driver then sends to the database.  With a
     * a <code>Calendar</code> object, the driver can calculate the timestamp
     * taking into account a custom timezone.  If no
     * <code>Calendar</code> object is specified, the driver uses the default
     * timezone, which is that of the virtual machine running the application.
     *
     * <p>
     * 使用给定的<code> Calendar </code>对象将指定的参数设置为给定的<code> java.sql.Timestamp </code>值。
     * 驱动程序使用<code> Calendar </code>对象来构造一个SQL <code> TIMESTAMP </code>值,驱动程序然后将其发送到数据库。
     * 使用<code> Calendar </code>对象,驱动程序可以计算考虑自定义时区的时间戳。
     * 如果未指定<code> Calendar </code>对象,则驱动程序将使用默认时区,即运行应用程序的虚拟机的时区。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param x the parameter value
     * @param cal the <code>Calendar</code> object the driver will use
     *            to construct the timestamp
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #getTimestamp
     * @since 1.4
     */
    void setTimestamp(String parameterName, java.sql.Timestamp x, Calendar cal)
        throws SQLException;

  /**
   * Clears the parameters set for this <code>RowSet</code> object's command.
   * <P>In general, parameter values remain in force for repeated use of a
   * <code>RowSet</code> object. Setting a parameter value automatically clears its
   * previous value.  However, in some cases it is useful to immediately
   * release the resources used by the current parameter values, which can
   * be done by calling the method <code>clearParameters</code>.
   *
   * <p>
   *  清除为此<code> RowSet </code>对象的命令设置的参数。 <P>通常,参数值对于重复使用<code> RowSet </code>对象仍然有效。设置参数值将自动清除其以前的值。
   * 然而,在某些情况下,立即释放当前参数值使用的资源是有用的,这可以通过调用方法<code> clearParameters </code>来完成。
   * 
   * 
   * @exception SQLException if a database access error occurs
   */
  void clearParameters() throws SQLException;

  //---------------------------------------------------------------------
  // Reading and writing data
  //---------------------------------------------------------------------

  /**
   * Fills this <code>RowSet</code> object with data.
   * <P>
   * The <code>execute</code> method may use the following properties
   * to create a connection for reading data: url, data source name,
   * user name, password, transaction isolation, and type map.
   *
   * The <code>execute</code> method  may use the following properties
   * to create a statement to execute a command:
   * command, read only, maximum field size,
   * maximum rows, escape processing, and query timeout.
   * <P>
   * If the required properties have not been set, an exception is
   * thrown.  If this method is successful, the current contents of the rowset are
   * discarded and the rowset's metadata is also (re)set.  If there are
   * outstanding updates, they are ignored.
   * <P>
   * If this <code>RowSet</code> object does not maintain a continuous connection
   * with its source of data, it may use a reader (a <code>RowSetReader</code>
   * object) to fill itself with data.  In this case, a reader will have been
   * registered with this <code>RowSet</code> object, and the method
   * <code>execute</code> will call on the reader's <code>readData</code>
   * method as part of its implementation.
   *
   * <p>
   *  用数据填充<code> RowSet </code>对象。
   * <P>
   *  <code> execute </code>方法可以使用以下属性创建用于读取数据的连接：url,数据源名称,用户名,密码,事务隔离和类型映射。
   * 
   *  <code> execute </code>方法可以使用以下属性来创建语句来执行命令：命令,只读,最大字段大小,最大行数,转义处理和查询超时。
   * <P>
   * 如果未设置必需的属性,则抛出异常。如果此方法成功,则会丢弃行集的当前内容,并且还会(重新)设置行集的元数据。如果有未完成的更新,它们将被忽略。
   * <P>
   *  如果这个<code> RowSet </code>对象不与其数据源保持连续的连接,它可以使用读取器(<code> RowSetReader </code>对象)来填充数据。
   * 在这种情况下,读取器将注册此<code> RowSet </code>对象,方法<code> execute </code>将调用读取器的<code> readData </code>方法其实现。
   * 
   * 
   * @exception SQLException if a database access error occurs or any of the
   *            properties necessary for making a connection and creating
   *            a statement have not been set
   */
  void execute() throws SQLException;

  //--------------------------------------------------------------------
  // Events
  //--------------------------------------------------------------------

  /**
   * Registers the given listener so that it will be notified of events
   * that occur on this <code>RowSet</code> object.
   *
   * <p>
   *  注册给定的侦听器,以便通知在此<code> RowSet </code>对象上发生的事件。
   * 
   * 
   * @param listener a component that has implemented the <code>RowSetListener</code>
   *        interface and wants to be notified when events occur on this
   *        <code>RowSet</code> object
   * @see #removeRowSetListener
   */
  void addRowSetListener(RowSetListener listener);

  /**
   * Removes the specified listener from the list of components that will be
   * notified when an event occurs on this <code>RowSet</code> object.
   *
   * <p>
   *  从在<code> RowSet </code>对象上发生事件时将通知的组件列表中删除指定的侦听器。
   * 
   * 
   * @param listener a component that has been registered as a listener for this
   *        <code>RowSet</code> object
   * @see #addRowSetListener
   */
  void removeRowSetListener(RowSetListener listener);

    /**
      * Sets the designated parameter to the given <code>java.sql.SQLXML</code> object. The driver converts this to an
      * SQL <code>XML</code> value when it sends it to the database.
      * <p>
      *  将指定的参数设置为给定的<code> java.sql.SQLXML </code>对象。当驱动程序将其发送到数据库时,将其转换为SQL <code> XML </code>值。
      * 
      * 
      * @param parameterIndex index of the first parameter is 1, the second is 2, ...
      * @param xmlObject a <code>SQLXML</code> object that maps an SQL <code>XML</code> value
      * @throws SQLException if a database access error occurs, this method
      *  is called on a closed result set,
      * the <code>java.xml.transform.Result</code>,
      *  <code>Writer</code> or <code>OutputStream</code> has not been closed
      * for the <code>SQLXML</code> object  or
      *  if there is an error processing the XML value.  The <code>getCause</code> method
      *  of the exception may provide a more detailed exception, for example, if the
      *  stream does not contain valid XML.
      * @since 1.6
      */
     void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.SQLXML</code> object. The driver converts this to an
     * <code>SQL XML</code> value when it sends it to the database.
     * <p>
     *  将指定的参数设置为给定的<code> java.sql.SQLXML </code>对象。当驱动程序将它发送到数据库时,将其转换为<code> SQL XML </code>值。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param xmlObject a <code>SQLXML</code> object that maps an <code>SQL XML</code> value
     * @throws SQLException if a database access error occurs, this method
     *  is called on a closed result set,
     * the <code>java.xml.transform.Result</code>,
     *  <code>Writer</code> or <code>OutputStream</code> has not been closed
     * for the <code>SQLXML</code> object  or
     *  if there is an error processing the XML value.  The <code>getCause</code> method
     *  of the exception may provide a more detailed exception, for example, if the
     *  stream does not contain valid XML.
     * @since 1.6
     */
    void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.sql.RowId</code> object. The
     * driver converts this to a SQL <code>ROWID</code> value when it sends it
     * to the database
     *
     * <p>
     *  将指定的参数设置为给定的<code> java.sql.RowId </code>对象。当驱动程序将其发送到数据库时,将其转换为SQL <code> ROWID </code>值
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the parameter value
     * @throws SQLException if a database access error occurs
     *
     * @since 1.6
     */
    void setRowId(int parameterIndex, RowId x) throws SQLException;

    /**
    * Sets the designated parameter to the given <code>java.sql.RowId</code> object. The
    * driver converts this to a SQL <code>ROWID</code> when it sends it to the
    * database.
    *
    * <p>
    * 将指定的参数设置为给定的<code> java.sql.RowId </code>对象。当它发送到数据库时,驱动程序将其转换为SQL <code> ROWID </code>。
    * 
    * 
    * @param parameterName the name of the parameter
    * @param x the parameter value
    * @throws SQLException if a database access error occurs
    * @since 1.6
    */
   void setRowId(String parameterName, RowId x) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>String</code> object.
     * The driver converts this to a SQL <code>NCHAR</code> or
     * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code> value
     * (depending on the argument's
     * size relative to the driver's limits on <code>NVARCHAR</code> values)
     * when it sends it to the database.
     *
     * <p>
     *  将指定的参数设置为给定的<code> String </code>对象。
     * 驱动程序将其转换为SQL <code> NCHAR </code>或<code> NVARCHAR </code>或<code> LONGNVARCHAR </code>值(取决于参数的大小相对于驱动程
     * 序在<code> </code>值),当它发送到数据库。
     *  将指定的参数设置为给定的<code> String </code>对象。
     * 
     * 
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value the parameter value
     * @throws SQLException if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur ; or if a database access error occurs
     * @since 1.6
     */
     void setNString(int parameterIndex, String value) throws SQLException;

    /**
     * Sets the designated parameter to the given <code>String</code> object.
     * The driver converts this to a SQL <code>NCHAR</code> or
     * <code>NVARCHAR</code> or <code>LONGNVARCHAR</code>
     * <p>
     *  将指定的参数设置为给定的<code> String </code>对象。
     * 驱动程序将此转换为SQL <code> NCHAR </code>或<code> NVARCHAR </code>或<code> LONGNVARCHAR </code>。
     * 
     * 
     * @param parameterName the name of the column to be set
     * @param value the parameter value
     * @throws SQLException if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; or if a database access error occurs
     * @since 1.6
     */
    public void setNString(String parameterName, String value)
            throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The
     * <code>Reader</code> reads the data till end-of-file is reached. The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     * <p>
     *  将指定的参数设置为<code> Reader </code>对象。 <code> Reader </code>读取数据,直到达到文件结束。
     * 驱动程序执行必要的从Java字符格式到数据库中的国家字符集的转换。
     * 
     * 
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value the parameter value
     * @param length the number of characters in the parameter data.
     * @throws SQLException if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur ; or if a database access error occurs
     * @since 1.6
     */
     void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The
     * <code>Reader</code> reads the data till end-of-file is reached. The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.
     * <p>
     *  将指定的参数设置为<code> Reader </code>对象。 <code> Reader </code>读取数据,直到达到文件结束。
     * 驱动程序执行必要的从Java字符格式到数据库中的国家字符集的转换。
     * 
     * 
     * @param parameterName the name of the column to be set
     * @param value the parameter value
     * @param length the number of characters in the parameter data.
     * @throws SQLException if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; or if a database access error occurs
     * @since 1.6
     */
    public void setNCharacterStream(String parameterName, Reader value, long length)
            throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The
     * <code>Reader</code> reads the data till end-of-file is reached. The
     * driver does the necessary conversion from Java character format to
     * the national character set in the database.

     * <P><B>Note:</B> This stream object can either be a standard
     * Java stream object or your own subclass that implements the
     * standard interface.
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNCharacterStream</code> which takes a length parameter.
     *
     * <p>
     *  将指定的参数设置为<code> Reader </code>对象。 <code> Reader </code>读取数据,直到达到文件结束。
     * 驱动程序执行必要的从Java字符格式到数据库中的国家字符集的转换。
     * 
     * <P> <B>注意：</B>此流对象可以是标准Java流对象或实现标准接口的自己的子类。
     *  <P> <B>注意：</B>请参阅您的JDBC驱动程序文档,以确定是否可以使用一个使用length参数的<code> setNCharacterStream </code>版本更高效。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param value the parameter value
     * @throws SQLException if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur ; if a database access error occurs; or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.6
     */
     void setNCharacterStream(String parameterName, Reader value) throws SQLException;

    /**
    * Sets the designated parameter to a <code>java.sql.NClob</code> object. The object
    * implements the <code>java.sql.NClob</code> interface. This <code>NClob</code>
    * object maps to a SQL <code>NCLOB</code>.
    * <p>
    *  将指定的参数设置为<code> java.sql.NClob </code>对象。该对象实现<code> java.sql.NClob </code>接口。
    * 这个<code> NClob </code>对象映射到SQL <code> NCLOB </code>。
    * 
    * 
    * @param parameterName the name of the column to be set
    * @param value the parameter value
    * @throws SQLException if the driver does not support national
    *         character sets;  if the driver can detect that a data conversion
    *  error could occur; or if a database access error occurs
    * @since 1.6
    */
    void setNClob(String parameterName, NClob value) throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The <code>reader</code> must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>CallableStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     *
     * <p>
     *  将指定的参数设置为<code> Reader </code>对象。
     *  <code> reader </code>必须包含由长度指定的字符数,否则在执行<code> CallableStatement </code>时会生成<code> SQLException </code>
     * 。
     *  将指定的参数设置为<code> Reader </code>对象。
     * 此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序应将参数值作为<code> NCLOB </code>发送到服务器。
     * 当使用<code> setCharacterStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGNVARCHAR </code>或<code> 
     * NCLOB发送到服务器</code>。
     * 此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序应将参数值作为<code> NCLOB </code>发送到服务器。
     * 
     * 
     * @param parameterName the name of the parameter to be set
     * @param reader An object that contains the data to set the parameter value to.
     * @param length the number of characters in the parameter data.
     * @throws SQLException if parameterIndex does not correspond to a parameter
     * marker in the SQL statement; if the length specified is less than zero;
     * if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur; if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.6
     */
     void setNClob(String parameterName, Reader reader, long length)
       throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object.
     * This method differs from the <code>setCharacterStream (int, Reader)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be send to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNClob</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象。
     * 此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值作为<code> NCLOB </code>发送到服务器。
     * 当使用<code> setCharacterStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGNVARCHAR </code>或<code> 
     * NCLOB发送到服务器</code> <P> <B>注意：</B>请参考您的JDBC驱动程序文档,以确定是否使用一个带有长度参数的<code> setNClob </code>版本更高效。
     * 此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值作为<code> NCLOB </code>发送到服务器。
     * 
     * 
     * @param parameterName the name of the parameter
     * @param reader An object that contains the data to set the parameter value to.
     * @throws SQLException if the driver does not support national character sets;
     * if the driver can detect that a data conversion
     *  error could occur;  if a database access error occurs or
     * this method is called on a closed <code>CallableStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     *
     * @since 1.6
     */
     void setNClob(String parameterName, Reader reader)
       throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object.  The reader must contain  the number
     * of characters specified by length otherwise a <code>SQLException</code> will be
     * generated when the <code>PreparedStatement</code> is executed.
     * This method differs from the <code>setCharacterStream (int, Reader, int)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     * <p>
     *  将指定的参数设置为<code> Reader </code>对象。
     * 阅读器必须包含长度指定的字符数,否则执行<code> PreparedStatement </code>时会生成<code> SQLException </code>。
     * 此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序应将参数值作为<code> NCLOB </code>发送到服务器。
     * 当使用<code> setCharacterStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGNVARCHAR </code>或<code> 
     * NCLOB发送到服务器</code>。
     * 此方法与<code> setCharacterStream(int,Reader,int)</code>方法不同,因为它通知驱动程序应将参数值作为<code> NCLOB </code>发送到服务器。
     * 
     * 
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader An object that contains the data to set the parameter value to.
     * @param length the number of characters in the parameter data.
     * @throws SQLException if parameterIndex does not correspond to a parameter
     * marker in the SQL statement; if the length specified is less than zero;
     * if the driver does not support national character sets;
     * if the driver can detect that a data conversion
     *  error could occur;  if a database access error occurs or
     * this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     *
     * @since 1.6
     */
     void setNClob(int parameterIndex, Reader reader, long length)
       throws SQLException;

    /**
     * Sets the designated parameter to a <code>java.sql.NClob</code> object. The driver converts this to a
     * SQL <code>NCLOB</code> value when it sends it to the database.
     * <p>
     *  将指定的参数设置为<code> java.sql.NClob </code>对象。当驱动程序将其发送到数据库时,将其转换为SQL <code> NCLOB </code>值。
     * 
     * 
     * @param parameterIndex of the first parameter is 1, the second is 2, ...
     * @param value the parameter value
     * @throws SQLException if the driver does not support national
     *         character sets;  if the driver can detect that a data conversion
     *  error could occur ; or if a database access error occurs
     * @since 1.6
     */
     void setNClob(int parameterIndex, NClob value) throws SQLException;

    /**
     * Sets the designated parameter to a <code>Reader</code> object.
     * This method differs from the <code>setCharacterStream (int, Reader)</code> method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>NCLOB</code>.  When the <code>setCharacterStream</code> method is used, the
     * driver may have to do extra work to determine whether the parameter
     * data should be sent to the server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     * <P><B>Note:</B> Consult your JDBC driver documentation to determine if
     * it might be more efficient to use a version of
     * <code>setNClob</code> which takes a length parameter.
     *
     * <p>
     * 将指定的参数设置为<code> Reader </code>对象。
     * 此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值作为<code> NCLOB </code>发送到服务器。
     * 当使用<code> setCharacterStream </code>方法时,驱动程序可能必须做额外的工作来确定参数数据是否应作为<code> LONGNVARCHAR </code>或<code> 
     * NCLOB发送到服务器</code> <P> <B>注意：</B>请参考您的JDBC驱动程序文档,以确定是否使用一个带有长度参数的<code> setNClob </code>版本更高效。
     * 此方法与<code> setCharacterStream(int,Reader)</code>方法不同,因为它通知驱动程序应将参数值作为<code> NCLOB </code>发送到服务器。
     * 
     * @param parameterIndex index of the first parameter is 1, the second is 2, ...
     * @param reader An object that contains the data to set the parameter value to.
     * @throws SQLException if parameterIndex does not correspond to a parameter
     * marker in the SQL statement;
     * if the driver does not support national character sets;
     * if the driver can detect that a data conversion
     *  error could occur;  if a database access error occurs or
     * this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     *
     * @since 1.6
     */
     void setNClob(int parameterIndex, Reader reader)
       throws SQLException;

    /**
     * Sets the designated parameter to the given <code>java.net.URL</code> value.
     * The driver converts this to an SQL <code>DATALINK</code> value
     * when it sends it to the database.
     *
     * <p>
     * 
     * 
     * @param parameterIndex the first parameter is 1, the second is 2, ...
     * @param x the <code>java.net.URL</code> object to be set
     * @exception SQLException if a database access error occurs or
     * this method is called on a closed <code>PreparedStatement</code>
     * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this method
     * @since 1.4
     */
    void setURL(int parameterIndex, java.net.URL x) throws SQLException;



}
