/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.logging.Logger;

/**
 * The interface that every driver class must implement.
 * <P>The Java SQL framework allows for multiple database drivers.
 *
 * <P>Each driver should supply a class that implements
 * the Driver interface.
 *
 * <P>The DriverManager will try to load as many drivers as it can
 * find and then for any given connection request, it will ask each
 * driver in turn to try to connect to the target URL.
 *
 * <P>It is strongly recommended that each Driver class should be
 * small and standalone so that the Driver class can be loaded and
 * queried without bringing in vast quantities of supporting code.
 *
 * <P>When a Driver class is loaded, it should create an instance of
 * itself and register it with the DriverManager. This means that a
 * user can load and register a driver by calling:
 * <p>
 * {@code Class.forName("foo.bah.Driver")}
 * <p>
 * A JDBC driver may create a {@linkplain DriverAction} implementation in order
 * to receive notifications when {@linkplain DriverManager#deregisterDriver} has
 * been called.
 * <p>
 *  每个驱动程序类必须实现的接口。 </p> Java SQL框架允许多个数据库驱动程序。
 * 
 *  <P>每个驱动程序都应提供一个实现驱动程序接口的类。
 * 
 *  <P> DriverManager将尝试加载与可以找到的驱动程序一样多的驱动程序,然后对于任何给定的连接请求,它会询问每个驱动程序以尝试连接到目标URL。
 * 
 *  <P>强烈建议每个驱动程序类都是小型和独立的,以便可以加载和查询驱动程序类,而不会带来大量的支持代码。
 * 
 *  <P>加载驱动程序类时,它应该创建一个自身的实例并向DriverManager注册它。这意味着用户可以通过调用来加载和注册驱动程序：
 * <p>
 *  {@code Class.forName("foo.bah.Driver")}
 * <p>
 *  JDBC驱动程序可以创建{@linkplain DriverAction}实现,以便在调用{@linkplain DriverManager#deregisterDriver}时接收通知。
 * 
 * 
 * @see DriverManager
 * @see Connection
 * @see DriverAction
 */
public interface Driver {

    /**
     * Attempts to make a database connection to the given URL.
     * The driver should return "null" if it realizes it is the wrong kind
     * of driver to connect to the given URL.  This will be common, as when
     * the JDBC driver manager is asked to connect to a given URL it passes
     * the URL to each loaded driver in turn.
     *
     * <P>The driver should throw an <code>SQLException</code> if it is the right
     * driver to connect to the given URL but has trouble connecting to
     * the database.
     *
     * <P>The {@code Properties} argument can be used to pass
     * arbitrary string tag/value pairs as connection arguments.
     * Normally at least "user" and "password" properties should be
     * included in the {@code Properties} object.
     * <p>
     * <B>Note:</B> If a property is specified as part of the {@code url} and
     * is also specified in the {@code Properties} object, it is
     * implementation-defined as to which value will take precedence. For
     * maximum portability, an application should only specify a property once.
     *
     * <p>
     *  尝试与给定的URL建立数据库连接。如果驱动程序意识到连接到给定的URL是错误的驱动程序,它应该返回"null"。
     * 这将是常见的,因为当要求JDBC驱动程序管理器连接到给定的URL时,它将URL传递给每个加载的驱动程序。
     * 
     * <P>如果驱动程序是连接到给定URL的正确驱动程序,但连接到数据库时遇到问题,驱动程序应抛出<code> SQLException </code>。
     * 
     *  <p> {@code属性}参数可用于传递任意字符串标签/值对作为连接参数。通常,至少"user"和"password"属性应包含在{@code Properties}对象中。
     * <p>
     *  <B>注意：</B>如果属性被指定为{@code url}的一部分,并且也在{@code Properties}对象中指定,那么它是由实现定义的,哪个值将优先。
     * 为了最大的可移植性,应用程序应该只指定一个属性。
     * 
     * 
     * @param url the URL of the database to which to connect
     * @param info a list of arbitrary string tag/value pairs as
     * connection arguments. Normally at least a "user" and
     * "password" property should be included.
     * @return a <code>Connection</code> object that represents a
     *         connection to the URL
     * @exception SQLException if a database access error occurs or the url is
     * {@code null}
     */
    Connection connect(String url, java.util.Properties info)
        throws SQLException;

    /**
     * Retrieves whether the driver thinks that it can open a connection
     * to the given URL.  Typically drivers will return <code>true</code> if they
     * understand the sub-protocol specified in the URL and <code>false</code> if
     * they do not.
     *
     * <p>
     *  检索驱动程序是否认为它可以打开与给定URL的连接。通常,如果驱动程序理解URL中指定的子协议,则返回<code> true </code>,如果没有,则返回<code> false </code>。
     * 
     * 
     * @param url the URL of the database
     * @return <code>true</code> if this driver understands the given URL;
     *         <code>false</code> otherwise
     * @exception SQLException if a database access error occurs or the url is
     * {@code null}
     */
    boolean acceptsURL(String url) throws SQLException;


    /**
     * Gets information about the possible properties for this driver.
     * <P>
     * The <code>getPropertyInfo</code> method is intended to allow a generic
     * GUI tool to discover what properties it should prompt
     * a human for in order to get
     * enough information to connect to a database.  Note that depending on
     * the values the human has supplied so far, additional values may become
     * necessary, so it may be necessary to iterate though several calls
     * to the <code>getPropertyInfo</code> method.
     *
     * <p>
     *  获取有关此驱动程序的可能属性的信息。
     * <P>
     *  <code> getPropertyInfo </code>方法旨在允许一个通用的GUI工具发现它应该提示一个人为了获得足够的信息连接到数据库的属性。
     * 注意,根据人类迄今提供的值,可能需要额外的值,因此可能有必要通过对<code> getPropertyInfo </code>方法的几次调用进行迭代。
     * 
     * 
     * @param url the URL of the database to which to connect
     * @param info a proposed list of tag/value pairs that will be sent on
     *          connect open
     * @return an array of <code>DriverPropertyInfo</code> objects describing
     *          possible properties.  This array may be an empty array if
     *          no properties are required.
     * @exception SQLException if a database access error occurs
     */
    DriverPropertyInfo[] getPropertyInfo(String url, java.util.Properties info)
                         throws SQLException;


    /**
     * Retrieves the driver's major version number. Initially this should be 1.
     *
     * <p>
     *  检索驱动程序的主要版本号。最初这应该是1。
     * 
     * 
     * @return this driver's major version number
     */
    int getMajorVersion();

    /**
     * Gets the driver's minor version number. Initially this should be 0.
     * <p>
     *  获取驱动程序的次要版本号。最初这应该是0。
     * 
     * 
     * @return this driver's minor version number
     */
    int getMinorVersion();


    /**
     * Reports whether this driver is a genuine JDBC
     * Compliant&trade; driver.
     * A driver may only report <code>true</code> here if it passes the JDBC
     * compliance tests; otherwise it is required to return <code>false</code>.
     * <P>
     * JDBC compliance requires full support for the JDBC API and full support
     * for SQL 92 Entry Level.  It is expected that JDBC compliant drivers will
     * be available for all the major commercial databases.
     * <P>
     * This method is not intended to encourage the development of non-JDBC
     * compliant drivers, but is a recognition of the fact that some vendors
     * are interested in using the JDBC API and framework for lightweight
     * databases that do not support full database functionality, or for
     * special databases such as document information retrieval where a SQL
     * implementation may not be feasible.
     * <p>
     * 报告这个驱动程序是否是一个真正的JDBC兼容和交易;驱动程序。
     * 如果驱动程序通过JDBC符合性测试,则此处只能报告<code> true </code>;否则需要返回<code> false </code>。
     * <P>
     *  JDBC合规性需要完全支持JDBC API并完全支持SQL 92入门级。预期JDBC兼容驱动程序将可用于所有主要的商业数据库。
     * <P>
     *  此方法不是为了鼓励开发非JDBC兼容的驱动程序,而是一个事实,即一些供应商有兴趣使用JDBC API和框架轻量级数据库,不支持完全数据库功能,或特殊数据库例如文档信息检索,其中SQL实现可能不可行。
     * 
     * @return <code>true</code> if this driver is JDBC Compliant; <code>false</code>
     *         otherwise
     */
    boolean jdbcCompliant();

    //------------------------- JDBC 4.1 -----------------------------------

    /**
     * Return the parent Logger of all the Loggers used by this driver. This
     * should be the Logger farthest from the root Logger that is
     * still an ancestor of all of the Loggers used by this driver. Configuring
     * this Logger will affect all of the log messages generated by the driver.
     * In the worst case, this may be the root Logger.
     *
     * <p>
     * 
     * 
     * @return the parent Logger for this driver
     * @throws SQLFeatureNotSupportedException if the driver does not use
     * {@code java.util.logging}.
     * @since 1.7
     */
    public Logger getParentLogger() throws SQLFeatureNotSupportedException;
}
