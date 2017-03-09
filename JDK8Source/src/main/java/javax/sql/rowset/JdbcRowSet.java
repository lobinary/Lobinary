/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql.rowset;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.io.*;
import java.math.*;
import java.io.*;

/**
 * The standard interface that all standard implementations of
 * <code>JdbcRowSet</code> must implement.
 *
 * <h3>1.0 Overview</h3>
 * A wrapper around a <code>ResultSet</code> object that makes it possible
 * to use the result set as a JavaBeans&trade;
 * component.  Thus, a <code>JdbcRowSet</code> object can be one of the Beans that
 * a tool makes available for composing an application.  Because
 * a <code>JdbcRowSet</code> is a connected rowset, that is, it continually
 * maintains its connection to a database using a JDBC technology-enabled
 * driver, it also effectively makes the driver a JavaBeans component.
 * <P>
 * Because it is always connected to its database, an instance of
 * <code>JdbcRowSet</code>
 * can simply take calls invoked on it and in turn call them on its
 * <code>ResultSet</code> object. As a consequence, a result set can, for
 * example, be a component in a Swing application.
 * <P>
 * Another advantage of a <code>JdbcRowSet</code> object is that it can be
 * used to make a <code>ResultSet</code> object scrollable and updatable.  All
 * <code>RowSet</code> objects are by default scrollable and updatable. If
 * the driver and database being used do not support scrolling and/or updating
 * of result sets, an application can populate a <code>JdbcRowSet</code> object
 * with the data of a <code>ResultSet</code> object and then operate on the
 * <code>JdbcRowSet</code> object as if it were the <code>ResultSet</code>
 * object.
 *
 * <h3>2.0 Creating a <code>JdbcRowSet</code> Object</h3>
 * The reference implementation of the <code>JdbcRowSet</code> interface,
 * <code>JdbcRowSetImpl</code>, provides an implementation of
 * the default constructor.  A new instance is initialized with
 * default values, which can be set with new values as needed. A
 * new instance is not really functional until its <code>execute</code>
 * method is called. In general, this method does the following:
 * <UL>
 *   <LI> establishes a connection with a database
 *   <LI> creates a <code>PreparedStatement</code> object and sets any of its
 *        placeholder parameters
 *   <LI> executes the statement to create a <code>ResultSet</code> object
 * </UL>
 * If the <code>execute</code> method is successful, it will set the
 * appropriate private <code>JdbcRowSet</code> fields with the following:
 * <UL>
 *  <LI> a <code>Connection</code> object -- the connection between the rowset
 *       and the database
 *  <LI> a <code>PreparedStatement</code> object -- the query that produces
 *       the result set
 *  <LI> a <code>ResultSet</code> object -- the result set that the rowset's
 *       command produced and that is being made, in effect, a JavaBeans
 *       component
 * </UL>
 * If these fields have not been set, meaning that the <code>execute</code>
 * method has not executed successfully, no methods other than
 * <code>execute</code> and <code>close</code> may be called on the
 * rowset.  All other public methods will throw an exception.
 * <P>
 * Before calling the <code>execute</code> method, however, the command
 * and properties needed for establishing a connection must be set.
 * The following code fragment creates a <code>JdbcRowSetImpl</code> object,
 * sets the command and connection properties, sets the placeholder parameter,
 * and then invokes the method <code>execute</code>.
 * <PRE>
 *     JdbcRowSetImpl jrs = new JdbcRowSetImpl();
 *     jrs.setCommand("SELECT * FROM TITLES WHERE TYPE = ?");
 *     jrs.setURL("jdbc:myDriver:myAttribute");
 *     jrs.setUsername("cervantes");
 *     jrs.setPassword("sancho");
 *     jrs.setString(1, "BIOGRAPHY");
 *     jrs.execute();
 * </PRE>
 * The variable <code>jrs</code> now represents an instance of
 * <code>JdbcRowSetImpl</code> that is a thin wrapper around the
 * <code>ResultSet</code> object containing all the rows in the
 * table <code>TITLES</code> where the type of book is biography.
 * At this point, operations called on <code>jrs</code> will
 * affect the rows in the result set, which is effectively a JavaBeans
 * component.
 * <P>
 * The implementation of the <code>RowSet</code> method <code>execute</code> in the
 * <code>JdbcRowSet</code> reference implementation differs from that in the
 * <code>CachedRowSet</code>&trade;
 * reference implementation to account for the different
 * requirements of connected and disconnected <code>RowSet</code> objects.
 * <p>
 *
 * <p>
 *  <code> JdbcRowSet </code>的所有标准实现必须实现的标准接口。
 * 
 *  <h3> 1.0概述</h3> <code> ResultSet </code>对象的包装器,可以将结果集用作JavaBeans&trade;零件。
 * 因此,一个<code> JdbcRowSet </code>对象可以是一个工具可用于编写应用程序的Bean之一。
 * 因为<code> JdbcRowSet </code>是一个连接的行集,也就是说,它使用支持JDBC技术的驱动程序持续维护与数据库的连接,因此它也有效地使驱动程序成为一个JavaBeans组件。
 * <P>
 *  因为它总是连接到它的数据库,<code> JdbcRowSet </code>的一个实例可以简单地接受调用的调用,然后调用它的<code> ResultSet </code>对象。
 * 因此,结果集可以例如是Swing应用程序中的组件。
 * <P>
 * <code> JdbcRowSet </code>对象的另一个优点是它可以用于使一个<code> ResultSet </code>对象可滚动和可更新。
 * 所有<code> RowSet </code>对象默认都是可滚动和可更新的。
 * 如果正在使用的驱动程序和数据库不支持滚动和/或更新结果集,则应用程序可以使用<code> ResultSet </code>对象的数据填充<code> JdbcRowSet </code>对象,在<code>
 *  JdbcRowSet </code>对象上,就像它是<code> ResultSet </code>对象。
 * 所有<code> RowSet </code>对象默认都是可滚动和可更新的。
 * 
 *  <h3> 2.0创建<code> JdbcRowSet </code>对象</h3> <code> JdbcRowSet </code>接口,<code> JdbcRowSetImpl </code>
 * 的引用实现提供了默认构造函数。
 * 使用默认值初始化新实例,可以根据需要使用新值设置。一个新的实例在它的<code> execute </code>方法被调用之前并不真正起作用。一般来说,此方法执行以下操作：。
 * <UL>
 *  <LI>与数据库建立连接<LI>创建<code> PreparedStatement </code>对象并设置其任何占位符参数<LI>执行语句以创建<code> ResultSet </code>对象
 * 。
 * </UL>
 *  如果<code> execute </code>方法成功,它将设置相应的私有<code> JdbcRowSet </code>字段：
 * <UL>
 * <LI> a <code> Connection </code>对象 - 行集和数据库之间的连接<LI> a <code> PreparedStatement </code>对象 - 产生结果集的查询<LI>
 *  code> ResultSet </code> object  - 行集的命令生成并正在进行的结果集,实际上是JavaBeans组件。
 * </UL>
 *  如果这些字段没有被设置,意味着<code> execute </code>方法没有成功执行,除了<code>执行</code>和<code> close </code>之外的方法可能被调用行集。
 * 所有其他公共方法都会抛出异常。
 * <P>
 *  然而,在调用<code> execute </code>方法之前,必须设置建立连接所需的命令和属性。
 * 以下代码片段创建一个<code> JdbcRowSetImpl </code>对象,设置命令和连接属性,设置placeholder参数,然后调用<code> execute </code>方法。
 * <PRE>
 *  JdbcRowSetImpl jrs = new JdbcRowSetImpl(); jrs.setCommand("SELECT * FROM TITLES WHERE TYPE =?"); jrs
 * .setURL("jdbc：myDriver：myAttribute"); jrs.setUsername("cervantes"); jrs.setPassword("sancho"); jrs.se
 * tString(1,"BIOGRAPHY"); jrs.execute()。
 * </PRE>
 * 变量<code> jrs </code>现在代表<code> JdbcRowSetImpl </code>的一个实例,它是包含表中所有行的<code> ResultSet </code> </code>
 * 其中书的类型是传记。
 * 在这一点上,调用<code> jrs </code>的操作将影响结果集中的行,这实际上是一个JavaBeans组件。
 * <P>
 *  在<code> JdbcRowSet </code>引用实现中的<code> RowSet </code>方法<code> execute </code>的实现与<code> CachedRowSet
 *  </code>&trade;参考实现来说明连接和断开的<code> RowSet </code>对象的不同要求。
 * <p>
 * 
 * 
 * @author Jonathan Bruce
 */

public interface JdbcRowSet extends RowSet, Joinable {

    /**
     * Retrieves a <code>boolean</code> indicating whether rows marked
     * for deletion appear in the set of current rows. If <code>true</code> is
     * returned, deleted rows are visible with the current rows. If
     * <code>false</code> is returned, rows are not visible with the set of
     * current rows. The default value is <code>false</code>.
     * <P>
     * Standard rowset implementations may choose to restrict this behavior
     * for security considerations or for certain deployment
     * scenarios. The visibility of deleted rows is implementation-defined
     * and does not represent standard behavior.
     * <P>
     * Note: Allowing deleted rows to remain visible complicates the behavior
     * of some standard JDBC <code>RowSet</code> implementations methods.
     * However, most rowset users can simply ignore this extra detail because
     * only very specialized applications will likely want to take advantage of
     * this feature.
     *
     * <p>
     *  检索<code> boolean </code>,指示标记为删除的行是否显示在当前行集中。如果返回<code> true </code>,则删除的行对当前行可见。
     * 如果返回<code> false </code>,则行不会与当前行集合一起显示。默认值为<code> false </code>。
     * <P>
     *  标准行集实现可以选择限制此行为以用于安全考虑或某些部署场景。已删除行的可见性是实现定义的,不代表标准行为。
     * <P>
     * 注意：允许删除的行保持可见会使某些标准JDBC <code> RowSet </code>实现方法的行为复杂化。
     * 然而,大多数行集用户可以忽略这个额外的细节,因为只有非常专业的应用程序可能想利用这个功能。
     * 
     * 
     * @return <code>true</code> if deleted rows are visible;
     *         <code>false</code> otherwise
     * @exception SQLException if a rowset implementation is unable to
     *          to determine whether rows marked for deletion remain visible
     * @see #setShowDeleted
     */
    public boolean getShowDeleted() throws SQLException;

    /**
     * Sets the property <code>showDeleted</code> to the given
     * <code>boolean</code> value. This property determines whether
     * rows marked for deletion continue to appear in the set of current rows.
     * If the value is set to <code>true</code>, deleted rows are immediately
     * visible with the set of current rows. If the value is set to
     * <code>false</code>, the deleted rows are set as invisible with the
     * current set of rows.
     * <P>
     * Standard rowset implementations may choose to restrict this behavior
     * for security considerations or for certain deployment
     * scenarios. This is left as implementation-defined and does not
     * represent standard behavior.
     *
     * <p>
     *  将属性<code> showDeleted </code>设置为给定的<code> boolean </code>值。此属性确定标记为删除的行是否继续显示在当前行集中。
     * 如果值设置为<code> true </code>,则删除的行将立即与当前行集合一起显示。如果值设置为<code> false </code>,则删除的行将设置为与当前行不可见。
     * <P>
     *  标准行集实现可以选择限制此行为以用于安全考虑或某些部署场景。这留作实现定义,并不代表标准行为。
     * 
     * 
     * @param b <code>true</code> if deleted rows should be shown;
     *              <code>false</code> otherwise
     * @exception SQLException if a rowset implementation is unable to
     *          to reset whether deleted rows should be visible
     * @see #getShowDeleted
     */
    public void setShowDeleted(boolean b) throws SQLException;

    /**
     * Retrieves the first warning reported by calls on this <code>JdbcRowSet</code>
     * object.
     * If a second warning was reported on this <code>JdbcRowSet</code> object,
     * it will be chained to the first warning and can be retrieved by
     * calling the method <code>RowSetWarning.getNextWarning</code> on the
     * first warning. Subsequent warnings on this <code>JdbcRowSet</code>
     * object will be chained to the <code>RowSetWarning</code> objects
     * returned by the method <code>RowSetWarning.getNextWarning</code>.
     *
     * The warning chain is automatically cleared each time a new row is read.
     * This method may not be called on a <code>RowSet</code> object
     * that has been closed;
     * doing so will cause an <code>SQLException</code> to be thrown.
     * <P>
     * Because it is always connected to its data source, a <code>JdbcRowSet</code>
     * object can rely on the presence of active
     * <code>Statement</code>, <code>Connection</code>, and <code>ResultSet</code>
     * instances. This means that  applications can obtain additional
     * <code>SQLWarning</code>
     * notifications by calling the <code>getNextWarning</code> methods that
     * they provide.
     * Disconnected <code>Rowset</code> objects, such as a
     * <code>CachedRowSet</code> object, do not have access to
     * these <code>getNextWarning</code> methods.
     *
     * <p>
     *  检索此<d> JdbcRowSet </code>对象上调用报告的第一个警告。
     * 如果在<code> JdbcRowSet </code>对象上报告了第二个警告,它将链接到第一个警告,并可以通过在第一个警告中调用<code> RowSetWarning.getNextWarning 
     * </code>方法来检索。
     *  检索此<d> JdbcRowSet </code>对象上调用报告的第一个警告。
     * 此<code> JdbcRowSet </code>对象上的后续警告将链接到方法<code> RowSetWarning.getNextWarning </code>返回的<code> RowSetWa
     * rning </code>对象。
     *  检索此<d> JdbcRowSet </code>对象上调用报告的第一个警告。
     * 
     * 每次读取新行时,警告链都会自动清除。此方法不能在已关闭的<code> RowSet </code>对象上调用;这样做会导致抛出<code> SQLException </code>。
     * <P>
     *  因为它总是连接到它的数据源,所以<code> JdbcRowSet </code>对象可以依赖于活动的<code> Statement </code>,<code> Connection </code>
     *  </code>实例。
     * 这意味着应用程序可以通过调用它们提供的<code> getNextWarning </code>方法来获取额外的<code> SQLWarning </code>通知。
     * 已断开的<code> Rowset </code>对象(如<code> CachedRowSet </code>对象)无权访问这些<code> getNextWarning </code>方法。
     * 
     * 
     * @return the first <code>RowSetWarning</code>
     * object reported on this <code>JdbcRowSet</code> object
     * or <code>null</code> if there are none
     * @throws SQLException if this method is called on a closed
     * <code>JdbcRowSet</code> object
     * @see RowSetWarning
     */
    public RowSetWarning getRowSetWarnings() throws SQLException;

   /**
    * Each <code>JdbcRowSet</code> contains a <code>Connection</code> object from
    * the <code>ResultSet</code> or JDBC properties passed to it's constructors.
    * This method wraps the <code>Connection</code> commit method to allow flexible
    * auto commit or non auto commit transactional control support.
    * <p>
    * Makes all changes made since the previous commit/rollback permanent
    * and releases any database locks currently held by this Connection
    * object. This method should be used only when auto-commit mode has
    * been disabled.
    *
    * <p>
    *  每个<code> JdbcRowSet </code>包含来自<code> ResultSet </code>或传递给它的构造函数的JDBC属性的<code> Connection </code>对象
    * 。
    * 此方法包装<code> Connection </code>提交方法以允许灵活的自动提交或非自动提交事务控制支持。
    * <p>
    *  使自上次提交/回滚后进行的所有更改都永久,并释放此Connection对象当前持有的任何数据库锁。仅当禁用自动提交模式时,才应使用此方法。
    * 
    * 
    * @throws SQLException if a database access error occurs or this
    * Connection object within this <code>JdbcRowSet</code> is in auto-commit mode
    * @see java.sql.Connection#setAutoCommit
    */
    public void commit() throws SQLException;


   /**
    * Each <code>JdbcRowSet</code> contains a <code>Connection</code> object from
    * the original <code>ResultSet</code> or JDBC properties passed to it. This
    * method wraps the <code>Connection</code>'s <code>getAutoCommit</code> method
    * to allow an application to determine the <code>JdbcRowSet</code> transaction
    * behavior.
    * <p>
    * Sets this connection's auto-commit mode to the given state. If a
    * connection is in auto-commit mode, then all its SQL statements will
    * be executed and committed as individual transactions. Otherwise, its
    * SQL statements are grouped into transactions that are terminated by a
    * call to either the method commit or the method rollback. By default,
    * new connections are in auto-commit mode.
    *
    * <p>
    * 每个<code> JdbcRowSet </code>包含来自原始<code> ResultSet </code>或传递给它的JDBC属性的<code> Connection </code>对象。
    * 此方法包装<code> Connection </code>的<code> getAutoCommit </code>方法,以允许应用程序确定<code> JdbcRowSet </code>事务行为。
    * 每个<code> JdbcRowSet </code>包含来自原始<code> ResultSet </code>或传递给它的JDBC属性的<code> Connection </code>对象。
    * <p>
    *  将此连接的自动提交模式设置为给定状态。如果连接处于自动提交模式,则其所有SQL语句将被执行并提交为单独的事务。否则,其SQL语句被分组为通过调用方法commit或方法rollback终止的事务。
    * 默认情况下,新连接处于自动提交模式。
    * 
    * 
    * @return {@code true} if auto-commit is enabled; {@code false} otherwise
    * @throws SQLException if a database access error occurs
    * @see java.sql.Connection#getAutoCommit()
    */
    public boolean getAutoCommit() throws SQLException;


   /**
    * Each <code>JdbcRowSet</code> contains a <code>Connection</code> object from
    * the original <code>ResultSet</code> or JDBC properties passed to it. This
    * method wraps the <code>Connection</code>'s <code>getAutoCommit</code> method
    * to allow an application to set the <code>JdbcRowSet</code> transaction behavior.
    * <p>
    * Sets the current auto-commit mode for this <code>Connection</code> object.
    * <p>
    *  每个<code> JdbcRowSet </code>包含来自原始<code> ResultSet </code>或传递给它的JDBC属性的<code> Connection </code>对象。
    * 此方法包装<code> Connection </code>的<code> getAutoCommit </code>方法,以允许应用程序设置<code> JdbcRowSet </code>事务行为。
    *  每个<code> JdbcRowSet </code>包含来自原始<code> ResultSet </code>或传递给它的JDBC属性的<code> Connection </code>对象。
    * <p>
    *  为此<code> Connection </code>对象设置当前自动提交模式。
    * 
    * 
    * @param autoCommit {@code true} to enable auto-commit; {@code false} to
    * disable auto-commit
    * @throws SQLException if a database access error occurs
    * @see java.sql.Connection#setAutoCommit(boolean)
    */
    public void setAutoCommit(boolean autoCommit) throws SQLException;

    /**
     * Each <code>JdbcRowSet</code> contains a <code>Connection</code> object from
     * the original <code>ResultSet</code> or JDBC properties passed to it.
     * Undoes all changes made in the current transaction and releases any
     * database locks currently held by this <code>Connection</code> object. This method
     * should be used only when auto-commit mode has been disabled.
     *
     * <p>
     *  每个<code> JdbcRowSet </code>包含来自原始<code> ResultSet </code>或传递给它的JDBC属性的<code> Connection </code>对象。
     * 撤销当前事务中所做的所有更改,并释放由此<code> Connection </code>对象当前持有的任何数据库锁。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * 
     * @throws SQLException if a database access error occurs or this <code>Connection</code>
     * object within this <code>JdbcRowSet</code> is in auto-commit mode.
     * @see #rollback(Savepoint)
     */
     public void rollback() throws SQLException;


    /**
     * Each <code>JdbcRowSet</code> contains a <code>Connection</code> object from
     * the original <code>ResultSet</code> or JDBC properties passed to it.
     * Undoes all changes made in the current transaction to the last set savepoint
     * and releases any database locks currently held by this <code>Connection</code>
     * object. This method should be used only when auto-commit mode has been disabled.
     * <p>
     * 每个<code> JdbcRowSet </code>包含来自原始<code> ResultSet </code>或传递给它的JDBC属性的<code> Connection </code>对象。
     * 将当前事务中所做的所有更改撤销到最后设置的保存点,并释放由此<code> Connection </code>对象当前持有的任何数据库锁。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * @param s The {@code Savepoint} to rollback to
     * @throws SQLException if a database access error occurs or this <code>Connection</code>
     * object within this <code>JdbcRowSet</code> is in auto-commit mode.
     * @see #rollback
     */
    public void rollback(Savepoint s) throws SQLException;

}
