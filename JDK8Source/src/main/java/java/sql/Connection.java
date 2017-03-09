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

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * <P>A connection (session) with a specific
 * database. SQL statements are executed and results are returned
 * within the context of a connection.
 * <P>
 * A <code>Connection</code> object's database is able to provide information
 * describing its tables, its supported SQL grammar, its stored
 * procedures, the capabilities of this connection, and so on. This
 * information is obtained with the <code>getMetaData</code> method.
 *
 * <P><B>Note:</B> When configuring a <code>Connection</code>, JDBC applications
 *  should use the appropriate <code>Connection</code> method such as
 *  <code>setAutoCommit</code> or <code>setTransactionIsolation</code>.
 *  Applications should not invoke SQL commands directly to change the connection's
 *   configuration when there is a JDBC method available.  By default a <code>Connection</code> object is in
 * auto-commit mode, which means that it automatically commits changes
 * after executing each statement. If auto-commit mode has been
 * disabled, the method <code>commit</code> must be called explicitly in
 * order to commit changes; otherwise, database changes will not be saved.
 * <P>
 * A new <code>Connection</code> object created using the JDBC 2.1 core API
 * has an initially empty type map associated with it. A user may enter a
 * custom mapping for a UDT in this type map.
 * When a UDT is retrieved from a data source with the
 * method <code>ResultSet.getObject</code>, the <code>getObject</code> method
 * will check the connection's type map to see if there is an entry for that
 * UDT.  If so, the <code>getObject</code> method will map the UDT to the
 * class indicated.  If there is no entry, the UDT will be mapped using the
 * standard mapping.
 * <p>
 * A user may create a new type map, which is a <code>java.util.Map</code>
 * object, make an entry in it, and pass it to the <code>java.sql</code>
 * methods that can perform custom mapping.  In this case, the method
 * will use the given type map instead of the one associated with
 * the connection.
 * <p>
 * For example, the following code fragment specifies that the SQL
 * type <code>ATHLETES</code> will be mapped to the class
 * <code>Athletes</code> in the Java programming language.
 * The code fragment retrieves the type map for the <code>Connection
 * </code> object <code>con</code>, inserts the entry into it, and then sets
 * the type map with the new entry as the connection's type map.
 * <pre>
 *      java.util.Map map = con.getTypeMap();
 *      map.put("mySchemaName.ATHLETES", Class.forName("Athletes"));
 *      con.setTypeMap(map);
 * </pre>
 *
 * <p>
 *  <P>与特定数据库的连接(会话)。执行SQL语句,并在连接的上下文中返回结果。
 * <P>
 *  <code> Connection </code>对象的数据库能够提供描述其表,其支持的SQL语法,其存储过程,此连接的能力等信息。
 * 此信息通过<code> getMetaData </code>方法获取。
 * 
 *  <P> <B>注意：</B>配置<code> Connection </code>时,JDBC应用程序应使用适当的<code> Connection </code>方法,例如<code> setAut
 * oCommit </code> <code> setTransactionIsolation </code>。
 * 当有可用的JDBC方法时,应用程序不应直接调用SQL命令来更改连接的配置。
 * 默认情况下,一个<code> Connection </code>对象处于自动提交模式,这意味着它在执行每个语句后自动提交更改。
 * 如果自动提交模式已禁用,则必须显式调用<code> commit </code>方法以提交更改;否则,数据库更改将不会保存。
 * <P>
 * 使用JDBC 2.1核心API创建的新<code> Connection </code>对象最初具有与之关联的空类型映射。用户可以在此类型映射中输入UDT的自定义映射。
 * 当使用方法<code> ResultSet.getObject </code>从数据源检索UDT时,<code> getObject </code>方法将检查连接的类型映射,以查看是否有该UDT的条目。
 * 使用JDBC 2.1核心API创建的新<code> Connection </code>对象最初具有与之关联的空类型映射。用户可以在此类型映射中输入UDT的自定义映射。
 * 如果是这样,<code> getObject </code>方法将UDT映射到指定的类。如果没有条目,则使用标准映射映射UDT。
 * <p>
 *  用户可以创建一个新的类型映射,它是一个<code> java.util.Map </code>对象,在其中创建一个条目,并将其传递给<code> java.sql </code>执行自定义映射。
 * 在这种情况下,该方法将使用给定的类型映射,而不是与连接相关联的映射。
 * <p>
 *  例如,以下代码片段指定SQL类型<code> ATHLETES </code>将映射到Java编程语言中的类<code> Athletes </code>。
 * 代码片段检索<code> Connection </code>对象<code> con </code>的类型映射,将条目插入其中,然后使用新条目设置类型映射作为连接的类型映射。
 * <pre>
 *  java.util.Map map = con.getTypeMap(); map.put("mySchemaName.ATHLETES",Class.forName("Athletes")); co
 * n.setTypeMap(map);。
 * </pre>
 * 
 * 
 * @see DriverManager#getConnection
 * @see Statement
 * @see ResultSet
 * @see DatabaseMetaData
 */
public interface Connection  extends Wrapper, AutoCloseable {

    /**
     * Creates a <code>Statement</code> object for sending
     * SQL statements to the database.
     * SQL statements without parameters are normally
     * executed using <code>Statement</code> objects. If the same SQL statement
     * is executed many times, it may be more efficient to use a
     * <code>PreparedStatement</code> object.
     * <P>
     * Result sets created using the returned <code>Statement</code>
     * object will by default be type <code>TYPE_FORWARD_ONLY</code>
     * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     * 创建用于将SQL语句发送到数据库的<code> Statement </code>对象。没有参数的SQL语句通常使用<code> Statement </code>对象来执行。
     * 如果同一个SQL语句执行多次,使用<code> PreparedStatement </code>对象可能更有效。
     * <P>
     *  使用返回的<code> Statement </code>对象创建的结果集将默认为<code> TYPE_FORWARD_ONLY </code>类型,并且并发级别为<code> CONCUR_REA
     * D_ONLY </code>。
     * 可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @return a new default <code>Statement</code> object
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     */
    Statement createStatement() throws SQLException;

    /**
     * Creates a <code>PreparedStatement</code> object for sending
     * parameterized SQL statements to the database.
     * <P>
     * A SQL statement with or without IN parameters can be
     * pre-compiled and stored in a <code>PreparedStatement</code> object. This
     * object can then be used to efficiently execute this statement
     * multiple times.
     *
     * <P><B>Note:</B> This method is optimized for handling
     * parametric SQL statements that benefit from precompilation. If
     * the driver supports precompilation,
     * the method <code>prepareStatement</code> will send
     * the statement to the database for precompilation. Some drivers
     * may not support precompilation. In this case, the statement may
     * not be sent to the database until the <code>PreparedStatement</code>
     * object is executed.  This has no direct effect on users; however, it does
     * affect which methods throw certain <code>SQLException</code> objects.
     * <P>
     * Result sets created using the returned <code>PreparedStatement</code>
     * object will by default be type <code>TYPE_FORWARD_ONLY</code>
     * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     *  创建用于将参数化的SQL语句发送到数据库的<code> PreparedStatement </code>对象。
     * <P>
     *  具有或不具有IN参数的SQL语句可以被预编译并存储在<code> PreparedStatement </code>对象中。然后可以使用此对象多次有效地执行此语句。
     * 
     *  <P> <B>注意：</B>此方法经过优化,用于处理受益于预编译的参数化SQL语句。
     * 如果驱动程序支持预编译,方法<code> prepareStatement </code>会将语句发送到数据库进行预编译。某些驱动程序可能不支持预编译。
     * 在这种情况下,在执行<code> PreparedStatement </code>对象之前,语句可能不会发送到数据库。
     * 这对用户没有直接影响;然而,它会影响哪些方法抛出某些<code> SQLException </code>对象。
     * <P>
     * 使用返回的<code> PreparedStatement </code>对象创建的结果集默认为类型<code> TYPE_FORWARD_ONLY </code>,并且并发级别为<code> CONC
     * UR_READ_ONLY </code>。
     * 可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @param sql an SQL statement that may contain one or more '?' IN
     * parameter placeholders
     * @return a new default <code>PreparedStatement</code> object containing the
     * pre-compiled SQL statement
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     */
    PreparedStatement prepareStatement(String sql)
        throws SQLException;

    /**
     * Creates a <code>CallableStatement</code> object for calling
     * database stored procedures.
     * The <code>CallableStatement</code> object provides
     * methods for setting up its IN and OUT parameters, and
     * methods for executing the call to a stored procedure.
     *
     * <P><B>Note:</B> This method is optimized for handling stored
     * procedure call statements. Some drivers may send the call
     * statement to the database when the method <code>prepareCall</code>
     * is done; others
     * may wait until the <code>CallableStatement</code> object
     * is executed. This has no
     * direct effect on users; however, it does affect which method
     * throws certain SQLExceptions.
     * <P>
     * Result sets created using the returned <code>CallableStatement</code>
     * object will by default be type <code>TYPE_FORWARD_ONLY</code>
     * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     *  创建用于调用数据库存储过程的<code> CallableStatement </code>对象。
     *  <code> CallableStatement </code>对象提供了设置其IN和OUT参数的方法以及执行对存储过程的调用的方法。
     * 
     *  <P> <B>注意：</B>此方法针对处理存储过程调用语句进行了优化。
     * 当方法<code> prepareCall </code>完成时,一些驱动程序可以将调用语句发送到数据库;其他人可能会等待,直到<code> CallableStatement </code>对象被执行
     * 。
     *  <P> <B>注意：</B>此方法针对处理存储过程调用语句进行了优化。这对用户没有直接影响;然而,它确实影响哪个方法抛出某些SQLExceptions。
     * <P>
     *  使用返回的<code> CallableStatement </code>对象创建的结果集默认为类型<code> TYPE_FORWARD_ONLY </code>,并且并发级别为<code> CON
     * CUR_READ_ONLY </code>。
     * 可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @param sql an SQL statement that may contain one or more '?'
     * parameter placeholders. Typically this statement is specified using JDBC
     * call escape syntax.
     * @return a new default <code>CallableStatement</code> object containing the
     * pre-compiled SQL statement
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     */
    CallableStatement prepareCall(String sql) throws SQLException;

    /**
     * Converts the given SQL statement into the system's native SQL grammar.
     * A driver may convert the JDBC SQL grammar into its system's
     * native SQL grammar prior to sending it. This method returns the
     * native form of the statement that the driver would have sent.
     *
     * <p>
     *  将给定的SQL语句转换为系统的本机SQL语法。驱动程序可以在发送JDBC SQL语法之前将其转换为其系统的本机SQL语法。此方法返回驱动程序本应发送的语句的本机形式。
     * 
     * 
     * @param sql an SQL statement that may contain one or more '?'
     * parameter placeholders
     * @return the native form of this statement
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     */
    String nativeSQL(String sql) throws SQLException;

    /**
     * Sets this connection's auto-commit mode to the given state.
     * If a connection is in auto-commit mode, then all its SQL
     * statements will be executed and committed as individual
     * transactions.  Otherwise, its SQL statements are grouped into
     * transactions that are terminated by a call to either
     * the method <code>commit</code> or the method <code>rollback</code>.
     * By default, new connections are in auto-commit
     * mode.
     * <P>
     * The commit occurs when the statement completes. The time when the statement
     * completes depends on the type of SQL Statement:
     * <ul>
     * <li>For DML statements, such as Insert, Update or Delete, and DDL statements,
     * the statement is complete as soon as it has finished executing.
     * <li>For Select statements, the statement is complete when the associated result
     * set is closed.
     * <li>For <code>CallableStatement</code> objects or for statements that return
     * multiple results, the statement is complete
     * when all of the associated result sets have been closed, and all update
     * counts and output parameters have been retrieved.
     *</ul>
     * <P>
     * <B>NOTE:</B>  If this method is called during a transaction and the
     * auto-commit mode is changed, the transaction is committed.  If
     * <code>setAutoCommit</code> is called and the auto-commit mode is
     * not changed, the call is a no-op.
     *
     * <p>
     * 将此连接的自动提交模式设置为给定状态。如果连接处于自动提交模式,则其所有SQL语句将被执行并提交为单独的事务。
     * 否则,其SQL语句被分组为通过调用<code> commit </code>方法或<code> rollback </code>方法终止的事务。默认情况下,新连接处于自动提交模式。
     * <P>
     *  当语句完成时,提交发生。语句完成的时间取决于SQL语句的类型：
     * <ul>
     *  <li>对于DML语句,例如Insert,Update或Delete和DDL语句,语句在完成执行后即完成。 <li>对于Select语句,当关联的结果集关闭时,语句完成。
     *  <li>对于<code> CallableStatement </code>对象或对于返回多个结果的语句,当所有关联的结果集都已关闭,并且已检索到所有更新计数和输出参数时,语句完成。
     * /ul>
     * <P>
     *  <B>注意：</B>如果在事务期间调用此方法并且更改自动提交模式,则会提交事务。如果调用<code> setAutoCommit </code>并且自动提交模式未更改,则调用是无操作。
     * 
     * 
     * @param autoCommit <code>true</code> to enable auto-commit mode;
     *         <code>false</code> to disable it
     * @exception SQLException if a database access error occurs,
     *  setAutoCommit(true) is called while participating in a distributed transaction,
     * or this method is called on a closed connection
     * @see #getAutoCommit
     */
    void setAutoCommit(boolean autoCommit) throws SQLException;

    /**
     * Retrieves the current auto-commit mode for this <code>Connection</code>
     * object.
     *
     * <p>
     *  检索此<code> Connection </code>对象的当前自动提交模式。
     * 
     * 
     * @return the current state of this <code>Connection</code> object's
     *         auto-commit mode
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @see #setAutoCommit
     */
    boolean getAutoCommit() throws SQLException;

    /**
     * Makes all changes made since the previous
     * commit/rollback permanent and releases any database locks
     * currently held by this <code>Connection</code> object.
     * This method should be
     * used only when auto-commit mode has been disabled.
     *
     * <p>
     * 使自上次提交/回滚后所做的所有更改永久保留,并释放由此<code> Connection </code>对象当前持有的任何数据库锁。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * 
     * @exception SQLException if a database access error occurs,
     * this method is called while participating in a distributed transaction,
     * if this method is called on a closed connection or this
     *            <code>Connection</code> object is in auto-commit mode
     * @see #setAutoCommit
     */
    void commit() throws SQLException;

    /**
     * Undoes all changes made in the current transaction
     * and releases any database locks currently held
     * by this <code>Connection</code> object. This method should be
     * used only when auto-commit mode has been disabled.
     *
     * <p>
     *  撤销当前事务中所做的所有更改,并释放由此<code> Connection </code>对象当前持有的任何数据库锁。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * 
     * @exception SQLException if a database access error occurs,
     * this method is called while participating in a distributed transaction,
     * this method is called on a closed connection or this
     *            <code>Connection</code> object is in auto-commit mode
     * @see #setAutoCommit
     */
    void rollback() throws SQLException;

    /**
     * Releases this <code>Connection</code> object's database and JDBC resources
     * immediately instead of waiting for them to be automatically released.
     * <P>
     * Calling the method <code>close</code> on a <code>Connection</code>
     * object that is already closed is a no-op.
     * <P>
     * It is <b>strongly recommended</b> that an application explicitly
     * commits or rolls back an active transaction prior to calling the
     * <code>close</code> method.  If the <code>close</code> method is called
     * and there is an active transaction, the results are implementation-defined.
     * <P>
     *
     * <p>
     *  立即释放此<code> Connection </code>对象的数据库和JDBC资源,而不是等待它们被自动释放。
     * <P>
     *  调用已经关闭的<code> Connection </code>对象上的<code> close </code>方法是无操作。
     * <P>
     *  强烈建议</b>应用程序在调用<code> close </code>方法之前显式提交或回滚活动事务。如果调用<code> close </code>方法,并且有一个活动事务,则结果是实现定义的。
     * <P>
     * 
     * 
     * @exception SQLException SQLException if a database access error occurs
     */
    void close() throws SQLException;

    /**
     * Retrieves whether this <code>Connection</code> object has been
     * closed.  A connection is closed if the method <code>close</code>
     * has been called on it or if certain fatal errors have occurred.
     * This method is guaranteed to return <code>true</code> only when
     * it is called after the method <code>Connection.close</code> has
     * been called.
     * <P>
     * This method generally cannot be called to determine whether a
     * connection to a database is valid or invalid.  A typical client
     * can determine that a connection is invalid by catching any
     * exceptions that might be thrown when an operation is attempted.
     *
     * <p>
     *  检索此<> Connection </code>对象是否已关闭。如果已经调用了<code> close </code>方法或者发生了某些致命错误,则关闭连接。
     * 该方法保证只有在调用<code> Connection.close </code>方法之后调用<code> true </code>时才返回<code> true </code>。
     * <P>
     * 通常不能调用此方法来确定与数据库的连接是有效还是无效。典型的客户端可以通过捕获在尝试操作时可能抛出的任何异常来确定连接是无效的。
     * 
     * 
     * @return <code>true</code> if this <code>Connection</code> object
     *         is closed; <code>false</code> if it is still open
     * @exception SQLException if a database access error occurs
     */
    boolean isClosed() throws SQLException;

    //======================================================================
    // Advanced features:

    /**
     * Retrieves a <code>DatabaseMetaData</code> object that contains
     * metadata about the database to which this
     * <code>Connection</code> object represents a connection.
     * The metadata includes information about the database's
     * tables, its supported SQL grammar, its stored
     * procedures, the capabilities of this connection, and so on.
     *
     * <p>
     *  检索包含有关该<code> Connection </code>对象表示连接的数据库的元数据的<code> DatabaseMetaData </code>对象。
     * 元数据包括有关数据库表,其支持的SQL语法,其存储过程,此连接的能力等信息。
     * 
     * 
     * @return a <code>DatabaseMetaData</code> object for this
     *         <code>Connection</code> object
     * @exception  SQLException if a database access error occurs
     * or this method is called on a closed connection
     */
    DatabaseMetaData getMetaData() throws SQLException;

    /**
     * Puts this connection in read-only mode as a hint to the driver to enable
     * database optimizations.
     *
     * <P><B>Note:</B> This method cannot be called during a transaction.
     *
     * <p>
     *  将此连接置于只读模式,作为驱动程序启用数据库优化的提示。
     * 
     *  <P> <B>注意：</B>此方法无法在事务期间调用。
     * 
     * 
     * @param readOnly <code>true</code> enables read-only mode;
     *        <code>false</code> disables it
     * @exception SQLException if a database access error occurs, this
     *  method is called on a closed connection or this
     *            method is called during a transaction
     */
    void setReadOnly(boolean readOnly) throws SQLException;

    /**
     * Retrieves whether this <code>Connection</code>
     * object is in read-only mode.
     *
     * <p>
     *  检索此<code> Connection </code>对象是否处于只读模式。
     * 
     * 
     * @return <code>true</code> if this <code>Connection</code> object
     *         is read-only; <code>false</code> otherwise
     * @exception SQLException SQLException if a database access error occurs
     * or this method is called on a closed connection
     */
    boolean isReadOnly() throws SQLException;

    /**
     * Sets the given catalog name in order to select
     * a subspace of this <code>Connection</code> object's database
     * in which to work.
     * <P>
     * If the driver does not support catalogs, it will
     * silently ignore this request.
     * <p>
     * Calling {@code setCatalog} has no effect on previously created or prepared
     * {@code Statement} objects. It is implementation defined whether a DBMS
     * prepare operation takes place immediately when the {@code Connection}
     * method {@code prepareStatement} or {@code prepareCall} is invoked.
     * For maximum portability, {@code setCatalog} should be called before a
     * {@code Statement} is created or prepared.
     *
     * <p>
     *  设置给定的目录名称,以便选择此<...> </>> </>>对象的数据库的子空间。
     * <P>
     *  如果驱动程序不支持目录,它将默认忽略此请求。
     * <p>
     *  调用{@code setCatalog}对先前创建或准备的{@code Statement}对象没有影响。
     * 它是实现定义是否当调用{@code Connection}方法{@code prepareStatement}或{@code prepareCall}时立即执行DBMS准备操作。
     * 为了实现最大的可移植性,应在创建或准备{@code Statement}之前调用{@code setCatalog}。
     * 
     * 
     * @param catalog the name of a catalog (subspace in this
     *        <code>Connection</code> object's database) in which to work
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @see #getCatalog
     */
    void setCatalog(String catalog) throws SQLException;

    /**
     * Retrieves this <code>Connection</code> object's current catalog name.
     *
     * <p>
     *  检索此<> </>对象的当前目录名称。
     * 
     * 
     * @return the current catalog name or <code>null</code> if there is none
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @see #setCatalog
     */
    String getCatalog() throws SQLException;

    /**
     * A constant indicating that transactions are not supported.
     * <p>
     * 指示不支持事务的常量。
     * 
     */
    int TRANSACTION_NONE             = 0;

    /**
     * A constant indicating that
     * dirty reads, non-repeatable reads and phantom reads can occur.
     * This level allows a row changed by one transaction to be read
     * by another transaction before any changes in that row have been
     * committed (a "dirty read").  If any of the changes are rolled back,
     * the second transaction will have retrieved an invalid row.
     * <p>
     *  一个常数,指示可能发生脏读取,不可重复读取和幻像读取。此级别允许由一个事务更改的行在另一个事务在该行中的任何更改已提交之前读取("脏读")。如果回滚了任何更改,则第二个事务将检索到无效行。
     * 
     */
    int TRANSACTION_READ_UNCOMMITTED = 1;

    /**
     * A constant indicating that
     * dirty reads are prevented; non-repeatable reads and phantom
     * reads can occur.  This level only prohibits a transaction
     * from reading a row with uncommitted changes in it.
     * <p>
     *  指示防止脏读取的常量;不可重复读取和幻影读取可以发生。此级别仅禁止事务读取其中具有未提交更改的行。
     * 
     */
    int TRANSACTION_READ_COMMITTED   = 2;

    /**
     * A constant indicating that
     * dirty reads and non-repeatable reads are prevented; phantom
     * reads can occur.  This level prohibits a transaction from
     * reading a row with uncommitted changes in it, and it also
     * prohibits the situation where one transaction reads a row,
     * a second transaction alters the row, and the first transaction
     * rereads the row, getting different values the second time
     * (a "non-repeatable read").
     * <p>
     *  指示防止脏读取和不可重复读取的常量;幻像读取可以发生。此级别禁止事务读取其中未提交更改的行,并且还禁止一个事务读取行,第二个事务更改行,第一个事务重读行,第二次获取不同值的情况"不可重复读")。
     * 
     */
    int TRANSACTION_REPEATABLE_READ  = 4;

    /**
     * A constant indicating that
     * dirty reads, non-repeatable reads and phantom reads are prevented.
     * This level includes the prohibitions in
     * <code>TRANSACTION_REPEATABLE_READ</code> and further prohibits the
     * situation where one transaction reads all rows that satisfy
     * a <code>WHERE</code> condition, a second transaction inserts a row that
     * satisfies that <code>WHERE</code> condition, and the first transaction
     * rereads for the same condition, retrieving the additional
     * "phantom" row in the second read.
     * <p>
     * 指示脏读取,不可重复读取和幻影读取的常量被阻止。
     * 此级别包括<code> TRANSACTION_REPEATABLE_READ </code>中的禁止,并进一步禁止一个事务读取满足<code> WHERE </code>条件的所有行的情况,第二个事务
     * 插入满足<code> WHERE </code>条件,并且第一个事务重读相同的条件,在第二次读取中检索额外的"幻像"行。
     * 指示脏读取,不可重复读取和幻影读取的常量被阻止。
     * 
     */
    int TRANSACTION_SERIALIZABLE     = 8;

    /**
     * Attempts to change the transaction isolation level for this
     * <code>Connection</code> object to the one given.
     * The constants defined in the interface <code>Connection</code>
     * are the possible transaction isolation levels.
     * <P>
     * <B>Note:</B> If this method is called during a transaction, the result
     * is implementation-defined.
     *
     * <p>
     *  尝试将此<code> Connection </code>对象的事务隔离级别更改为给定的。接口<code> Connection </code>中定义的常量是可能的事务隔离级别。
     * <P>
     *  <B>注意：</B>如果在事务期间调用此方法,则结果是实现定义的。
     * 
     * 
     * @param level one of the following <code>Connection</code> constants:
     *        <code>Connection.TRANSACTION_READ_UNCOMMITTED</code>,
     *        <code>Connection.TRANSACTION_READ_COMMITTED</code>,
     *        <code>Connection.TRANSACTION_REPEATABLE_READ</code>, or
     *        <code>Connection.TRANSACTION_SERIALIZABLE</code>.
     *        (Note that <code>Connection.TRANSACTION_NONE</code> cannot be used
     *        because it specifies that transactions are not supported.)
     * @exception SQLException if a database access error occurs, this
     * method is called on a closed connection
     *            or the given parameter is not one of the <code>Connection</code>
     *            constants
     * @see DatabaseMetaData#supportsTransactionIsolationLevel
     * @see #getTransactionIsolation
     */
    void setTransactionIsolation(int level) throws SQLException;

    /**
     * Retrieves this <code>Connection</code> object's current
     * transaction isolation level.
     *
     * <p>
     *  检索此<<code> Connection </code>对象的当前事务隔离级别。
     * 
     * 
     * @return the current transaction isolation level, which will be one
     *         of the following constants:
     *        <code>Connection.TRANSACTION_READ_UNCOMMITTED</code>,
     *        <code>Connection.TRANSACTION_READ_COMMITTED</code>,
     *        <code>Connection.TRANSACTION_REPEATABLE_READ</code>,
     *        <code>Connection.TRANSACTION_SERIALIZABLE</code>, or
     *        <code>Connection.TRANSACTION_NONE</code>.
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @see #setTransactionIsolation
     */
    int getTransactionIsolation() throws SQLException;

    /**
     * Retrieves the first warning reported by calls on this
     * <code>Connection</code> object.  If there is more than one
     * warning, subsequent warnings will be chained to the first one
     * and can be retrieved by calling the method
     * <code>SQLWarning.getNextWarning</code> on the warning
     * that was retrieved previously.
     * <P>
     * This method may not be
     * called on a closed connection; doing so will cause an
     * <code>SQLException</code> to be thrown.
     *
     * <P><B>Note:</B> Subsequent warnings will be chained to this
     * SQLWarning.
     *
     * <p>
     *  检索此<code> Connection </code>对象上调用所报告的第一个警告。
     * 如果有多个警告,后续警告将链接到第一个警告,并可以通过调用先前检索的警告上的方法<code> SQLWarning.getNextWarning </code>检索。
     * <P>
     *  此方法可能不会在关闭连接上调用;这样做会导致抛出<code> SQLException </code>。
     * 
     *  <P> <B>注意：</B>后续警告将链接到此SQLWarning。
     * 
     * 
     * @return the first <code>SQLWarning</code> object or <code>null</code>
     *         if there are none
     * @exception SQLException if a database access error occurs or
     *            this method is called on a closed connection
     * @see SQLWarning
     */
    SQLWarning getWarnings() throws SQLException;

    /**
     * Clears all warnings reported for this <code>Connection</code> object.
     * After a call to this method, the method <code>getWarnings</code>
     * returns <code>null</code> until a new warning is
     * reported for this <code>Connection</code> object.
     *
     * <p>
     * 清除为此<code>连接</code>对象报告的所有警告。
     * 调用此方法后,方法<code> getWarnings </code>返回<code> null </code>,直到为此<code> Connection </code>对象报告新警告。
     * 
     * 
     * @exception SQLException SQLException if a database access error occurs
     * or this method is called on a closed connection
     */
    void clearWarnings() throws SQLException;


    //--------------------------JDBC 2.0-----------------------------

    /**
     * Creates a <code>Statement</code> object that will generate
     * <code>ResultSet</code> objects with the given type and concurrency.
     * This method is the same as the <code>createStatement</code> method
     * above, but it allows the default result set
     * type and concurrency to be overridden.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     *  创建将生成具有给定类型和并发性的<code> ResultSet </code>对象的<code> Statement </code>对象。
     * 此方法与上述<code> createStatement </code>方法相同,但它允许覆盖默认结果集类型和并发。
     * 可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @param resultSetType a result set type; one of
     *        <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *        <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *        <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @param resultSetConcurrency a concurrency type; one of
     *        <code>ResultSet.CONCUR_READ_ONLY</code> or
     *        <code>ResultSet.CONCUR_UPDATABLE</code>
     * @return a new <code>Statement</code> object that will generate
     *         <code>ResultSet</code> objects with the given type and
     *         concurrency
     * @exception SQLException if a database access error occurs, this
     * method is called on a closed connection
     *         or the given parameters are not <code>ResultSet</code>
     *         constants indicating type and concurrency
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method or this method is not supported for the specified result
     * set type and result set concurrency.
     * @since 1.2
     */
    Statement createStatement(int resultSetType, int resultSetConcurrency)
        throws SQLException;

    /**
     *
     * Creates a <code>PreparedStatement</code> object that will generate
     * <code>ResultSet</code> objects with the given type and concurrency.
     * This method is the same as the <code>prepareStatement</code> method
     * above, but it allows the default result set
     * type and concurrency to be overridden.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     *  创建将生成具有给定类型和并发性的<code> ResultSet </code>对象的<code> PreparedStatement </code>对象。
     * 此方法与上述<code> prepareStatement </code>方法相同,但它允许覆盖默认结果集类型和并发。
     * 可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @param sql a <code>String</code> object that is the SQL statement to
     *            be sent to the database; may contain one or more '?' IN
     *            parameters
     * @param resultSetType a result set type; one of
     *         <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *         <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *         <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @param resultSetConcurrency a concurrency type; one of
     *         <code>ResultSet.CONCUR_READ_ONLY</code> or
     *         <code>ResultSet.CONCUR_UPDATABLE</code>
     * @return a new PreparedStatement object containing the
     * pre-compiled SQL statement that will produce <code>ResultSet</code>
     * objects with the given type and concurrency
     * @exception SQLException if a database access error occurs, this
     * method is called on a closed connection
     *         or the given parameters are not <code>ResultSet</code>
     *         constants indicating type and concurrency
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method or this method is not supported for the specified result
     * set type and result set concurrency.
     * @since 1.2
     */
    PreparedStatement prepareStatement(String sql, int resultSetType,
                                       int resultSetConcurrency)
        throws SQLException;

    /**
     * Creates a <code>CallableStatement</code> object that will generate
     * <code>ResultSet</code> objects with the given type and concurrency.
     * This method is the same as the <code>prepareCall</code> method
     * above, but it allows the default result set
     * type and concurrency to be overridden.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     *  创建将生成具有给定类型和并发性的<code> ResultSet </code>对象的<code> CallableStatement </code>对象。
     * 此方法与上述<code> prepareCall </code>方法相同,但它允许覆盖默认结果集类型和并发。可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @param sql a <code>String</code> object that is the SQL statement to
     *            be sent to the database; may contain on or more '?' parameters
     * @param resultSetType a result set type; one of
     *         <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *         <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *         <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @param resultSetConcurrency a concurrency type; one of
     *         <code>ResultSet.CONCUR_READ_ONLY</code> or
     *         <code>ResultSet.CONCUR_UPDATABLE</code>
     * @return a new <code>CallableStatement</code> object containing the
     * pre-compiled SQL statement that will produce <code>ResultSet</code>
     * objects with the given type and concurrency
     * @exception SQLException if a database access error occurs, this method
     * is called on a closed connection
     *         or the given parameters are not <code>ResultSet</code>
     *         constants indicating type and concurrency
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method or this method is not supported for the specified result
     * set type and result set concurrency.
     * @since 1.2
     */
    CallableStatement prepareCall(String sql, int resultSetType,
                                  int resultSetConcurrency) throws SQLException;

    /**
     * Retrieves the <code>Map</code> object associated with this
     * <code>Connection</code> object.
     * Unless the application has added an entry, the type map returned
     * will be empty.
     * <p>
     * You must invoke <code>setTypeMap</code> after making changes to the
     * <code>Map</code> object returned from
     *  <code>getTypeMap</code> as a JDBC driver may create an internal
     * copy of the <code>Map</code> object passed to <code>setTypeMap</code>:
     *
     * <pre>
     *      Map&lt;String,Class&lt;?&gt;&gt; myMap = con.getTypeMap();
     *      myMap.put("mySchemaName.ATHLETES", Athletes.class);
     *      con.setTypeMap(myMap);
     * </pre>
     * <p>
     * 检索与此<code> Connection </code>对象关联的<code> Map </code>对象。除非应用程序已添加条目,否则返回的类型映射将为空。
     * <p>
     *  在对<code> getTypeMap </code>返回的<code> Map </code>对象进行更改后,必须调用<code> setTypeMap </code>作为JDBC驱动程序可以创建<code>
     *  Map </code>对象传递给<code> setTypeMap </code>：。
     * 
     * <pre>
     *  Map&lt; String,Class&lt;?&gt;&gt; myMap = con.getTypeMap(); myMap.put("mySchemaName.ATHLETES",Athlet
     * es.class); con.setTypeMap(myMap);。
     * </pre>
     * 
     * @return the <code>java.util.Map</code> object associated
     *         with this <code>Connection</code> object
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     * @see #setTypeMap
     */
    java.util.Map<String,Class<?>> getTypeMap() throws SQLException;

    /**
     * Installs the given <code>TypeMap</code> object as the type map for
     * this <code>Connection</code> object.  The type map will be used for the
     * custom mapping of SQL structured types and distinct types.
     *<p>
     * You must set the the values for the <code>TypeMap</code> prior to
     * callng <code>setMap</code> as a JDBC driver may create an internal copy
     * of the <code>TypeMap</code>:
     *
     * <pre>
     *      Map myMap&lt;String,Class&lt;?&gt;&gt; = new HashMap&lt;String,Class&lt;?&gt;&gt;();
     *      myMap.put("mySchemaName.ATHLETES", Athletes.class);
     *      con.setTypeMap(myMap);
     * </pre>
     * <p>
     *  安装给定的<code> TypeMap </code>对象作为<code> Connection </code>对象的类型映射。类型映射将用于SQL结构类型和不同类型的自定义映射。
     * p>
     *  您必须在调用<code> setMap </code>之前设置<code> TypeMap </code>的值,因为JDBC驱动程序可能会创建<code> TypeMap </code>的内部副本：。
     * 
     * <pre>
     *  映射myMap&lt; String,Class&lt;?&gt;&gt; = new HashMap&lt; String,Class&lt;?&gt;&gt;(); myMap.put("mySc
     * hemaName.ATHLETES",Athletes.class); con.setTypeMap(myMap);。
     * </pre>
     * 
     * @param map the <code>java.util.Map</code> object to install
     *        as the replacement for this <code>Connection</code>
     *        object's default type map
     * @exception SQLException if a database access error occurs, this
     * method is called on a closed connection or
     *        the given parameter is not a <code>java.util.Map</code>
     *        object
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.2
     * @see #getTypeMap
     */
    void setTypeMap(java.util.Map<String,Class<?>> map) throws SQLException;

    //--------------------------JDBC 3.0-----------------------------


    /**
     * Changes the default holdability of <code>ResultSet</code> objects
     * created using this <code>Connection</code> object to the given
     * holdability.  The default holdability of <code>ResultSet</code> objects
     * can be be determined by invoking
     * {@link DatabaseMetaData#getResultSetHoldability}.
     *
     * <p>
     *  将使用此<code> Connection </code>对象创建的<code> ResultSet </code>对象的默认保持性更改为给定的可保留性。
     *  <code> ResultSet </code>对象的默认保持性可以通过调用{@link DatabaseMetaData#getResultSetHoldability}来确定。
     * 
     * 
     * @param holdability a <code>ResultSet</code> holdability constant; one of
     *        <code>ResultSet.HOLD_CURSORS_OVER_COMMIT</code> or
     *        <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
     * @throws SQLException if a database access occurs, this method is called
     * on a closed connection, or the given parameter
     *         is not a <code>ResultSet</code> constant indicating holdability
     * @exception SQLFeatureNotSupportedException if the given holdability is not supported
     * @see #getHoldability
     * @see DatabaseMetaData#getResultSetHoldability
     * @see ResultSet
     * @since 1.4
     */
    void setHoldability(int holdability) throws SQLException;

    /**
     * Retrieves the current holdability of <code>ResultSet</code> objects
     * created using this <code>Connection</code> object.
     *
     * <p>
     *  检索使用此<code> Connection </code>对象创建的<code> ResultSet </code>对象的当前可保留性。
     * 
     * 
     * @return the holdability, one of
     *        <code>ResultSet.HOLD_CURSORS_OVER_COMMIT</code> or
     *        <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
     * @throws SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @see #setHoldability
     * @see DatabaseMetaData#getResultSetHoldability
     * @see ResultSet
     * @since 1.4
     */
    int getHoldability() throws SQLException;

    /**
     * Creates an unnamed savepoint in the current transaction and
     * returns the new <code>Savepoint</code> object that represents it.
     *
     *<p> if setSavepoint is invoked outside of an active transaction, a transaction will be started at this newly created
     *savepoint.
     *
     * <p>
     * 在当前事务中创建一个未命名的保存点,并返回代表它的新的<code> Savepoint </code>对象。
     * 
     *  p>如果setSavepoint在活动事务之外被调用,事务将在这个新创建的avepoint被启动。
     * 
     * 
     * @return the new <code>Savepoint</code> object
     * @exception SQLException if a database access error occurs,
     * this method is called while participating in a distributed transaction,
     * this method is called on a closed connection
     *            or this <code>Connection</code> object is currently in
     *            auto-commit mode
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see Savepoint
     * @since 1.4
     */
    Savepoint setSavepoint() throws SQLException;

    /**
     * Creates a savepoint with the given name in the current transaction
     * and returns the new <code>Savepoint</code> object that represents it.
     *
     * <p> if setSavepoint is invoked outside of an active transaction, a transaction will be started at this newly created
     *savepoint.
     *
     * <p>
     *  在当前事务中创建具有给定名称的保存点,并返回代表它的新<code> Savepoint </code>对象。
     * 
     *  <p>如果setSavepoint在活动事务之外调用,事务将在此新创建的avepoint上启动。
     * 
     * 
     * @param name a <code>String</code> containing the name of the savepoint
     * @return the new <code>Savepoint</code> object
     * @exception SQLException if a database access error occurs,
          * this method is called while participating in a distributed transaction,
     * this method is called on a closed connection
     *            or this <code>Connection</code> object is currently in
     *            auto-commit mode
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see Savepoint
     * @since 1.4
     */
    Savepoint setSavepoint(String name) throws SQLException;

    /**
     * Undoes all changes made after the given <code>Savepoint</code> object
     * was set.
     * <P>
     * This method should be used only when auto-commit has been disabled.
     *
     * <p>
     *  撤消对给定的<code> Savepoint </code>对象设置后所做的所有更改。
     * <P>
     *  仅当禁用自动提交时才应使用此方法。
     * 
     * 
     * @param savepoint the <code>Savepoint</code> object to roll back to
     * @exception SQLException if a database access error occurs,
     * this method is called while participating in a distributed transaction,
     * this method is called on a closed connection,
     *            the <code>Savepoint</code> object is no longer valid,
     *            or this <code>Connection</code> object is currently in
     *            auto-commit mode
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see Savepoint
     * @see #rollback
     * @since 1.4
     */
    void rollback(Savepoint savepoint) throws SQLException;

    /**
     * Removes the specified <code>Savepoint</code>  and subsequent <code>Savepoint</code> objects from the current
     * transaction. Any reference to the savepoint after it have been removed
     * will cause an <code>SQLException</code> to be thrown.
     *
     * <p>
     *  从当前事务中删除指定的<code> Savepoint </code>和后续<code> Savepoint </code>对象。
     * 对保存点的任何引用在删除后将导致抛出<code> SQLException </code>。
     * 
     * 
     * @param savepoint the <code>Savepoint</code> object to be removed
     * @exception SQLException if a database access error occurs, this
     *  method is called on a closed connection or
     *            the given <code>Savepoint</code> object is not a valid
     *            savepoint in the current transaction
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @since 1.4
     */
    void releaseSavepoint(Savepoint savepoint) throws SQLException;

    /**
     * Creates a <code>Statement</code> object that will generate
     * <code>ResultSet</code> objects with the given type, concurrency,
     * and holdability.
     * This method is the same as the <code>createStatement</code> method
     * above, but it allows the default result set
     * type, concurrency, and holdability to be overridden.
     *
     * <p>
     *  创建一个<code> Statement </code>对象,它将生成具有给定类型,并发性和可持续性的<code> ResultSet </code>对象。
     * 此方法与上述<code> createStatement </code>方法相同,但它允许覆盖默认结果集类型,并发和保持性。
     * 
     * 
     * @param resultSetType one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *         <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *         <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @param resultSetConcurrency one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.CONCUR_READ_ONLY</code> or
     *         <code>ResultSet.CONCUR_UPDATABLE</code>
     * @param resultSetHoldability one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.HOLD_CURSORS_OVER_COMMIT</code> or
     *         <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
     * @return a new <code>Statement</code> object that will generate
     *         <code>ResultSet</code> objects with the given type,
     *         concurrency, and holdability
     * @exception SQLException if a database access error occurs, this
     * method is called on a closed connection
     *            or the given parameters are not <code>ResultSet</code>
     *            constants indicating type, concurrency, and holdability
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method or this method is not supported for the specified result
     * set type, result set holdability and result set concurrency.
     * @see ResultSet
     * @since 1.4
     */
    Statement createStatement(int resultSetType, int resultSetConcurrency,
                              int resultSetHoldability) throws SQLException;

    /**
     * Creates a <code>PreparedStatement</code> object that will generate
     * <code>ResultSet</code> objects with the given type, concurrency,
     * and holdability.
     * <P>
     * This method is the same as the <code>prepareStatement</code> method
     * above, but it allows the default result set
     * type, concurrency, and holdability to be overridden.
     *
     * <p>
     *  创建一个<code> PreparedStatement </code>对象,将生成具有给定类型,并发性和可持续性的<code> ResultSet </code>对象。
     * <P>
     * 此方法与上述<code> prepareStatement </code>方法相同,但它允许覆盖默认结果集类型,并发和保持性。
     * 
     * 
     * @param sql a <code>String</code> object that is the SQL statement to
     *            be sent to the database; may contain one or more '?' IN
     *            parameters
     * @param resultSetType one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *         <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *         <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @param resultSetConcurrency one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.CONCUR_READ_ONLY</code> or
     *         <code>ResultSet.CONCUR_UPDATABLE</code>
     * @param resultSetHoldability one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.HOLD_CURSORS_OVER_COMMIT</code> or
     *         <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
     * @return a new <code>PreparedStatement</code> object, containing the
     *         pre-compiled SQL statement, that will generate
     *         <code>ResultSet</code> objects with the given type,
     *         concurrency, and holdability
     * @exception SQLException if a database access error occurs, this
     * method is called on a closed connection
     *            or the given parameters are not <code>ResultSet</code>
     *            constants indicating type, concurrency, and holdability
      * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method or this method is not supported for the specified result
     * set type, result set holdability and result set concurrency.
     * @see ResultSet
     * @since 1.4
     */
    PreparedStatement prepareStatement(String sql, int resultSetType,
                                       int resultSetConcurrency, int resultSetHoldability)
        throws SQLException;

    /**
     * Creates a <code>CallableStatement</code> object that will generate
     * <code>ResultSet</code> objects with the given type and concurrency.
     * This method is the same as the <code>prepareCall</code> method
     * above, but it allows the default result set
     * type, result set concurrency type and holdability to be overridden.
     *
     * <p>
     *  创建将生成具有给定类型和并发性的<code> ResultSet </code>对象的<code> CallableStatement </code>对象。
     * 此方法与上述<code> prepareCall </code>方法相同,但它允许覆盖默认结果集类型,结果集并发类型和保持性。
     * 
     * 
     * @param sql a <code>String</code> object that is the SQL statement to
     *            be sent to the database; may contain on or more '?' parameters
     * @param resultSetType one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.TYPE_FORWARD_ONLY</code>,
     *         <code>ResultSet.TYPE_SCROLL_INSENSITIVE</code>, or
     *         <code>ResultSet.TYPE_SCROLL_SENSITIVE</code>
     * @param resultSetConcurrency one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.CONCUR_READ_ONLY</code> or
     *         <code>ResultSet.CONCUR_UPDATABLE</code>
     * @param resultSetHoldability one of the following <code>ResultSet</code>
     *        constants:
     *         <code>ResultSet.HOLD_CURSORS_OVER_COMMIT</code> or
     *         <code>ResultSet.CLOSE_CURSORS_AT_COMMIT</code>
     * @return a new <code>CallableStatement</code> object, containing the
     *         pre-compiled SQL statement, that will generate
     *         <code>ResultSet</code> objects with the given type,
     *         concurrency, and holdability
     * @exception SQLException if a database access error occurs, this
     * method is called on a closed connection
     *            or the given parameters are not <code>ResultSet</code>
     *            constants indicating type, concurrency, and holdability
      * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method or this method is not supported for the specified result
     * set type, result set holdability and result set concurrency.
     * @see ResultSet
     * @since 1.4
     */
    CallableStatement prepareCall(String sql, int resultSetType,
                                  int resultSetConcurrency,
                                  int resultSetHoldability) throws SQLException;


    /**
     * Creates a default <code>PreparedStatement</code> object that has
     * the capability to retrieve auto-generated keys. The given constant
     * tells the driver whether it should make auto-generated keys
     * available for retrieval.  This parameter is ignored if the SQL statement
     * is not an <code>INSERT</code> statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <P>
     * <B>Note:</B> This method is optimized for handling
     * parametric SQL statements that benefit from precompilation. If
     * the driver supports precompilation,
     * the method <code>prepareStatement</code> will send
     * the statement to the database for precompilation. Some drivers
     * may not support precompilation. In this case, the statement may
     * not be sent to the database until the <code>PreparedStatement</code>
     * object is executed.  This has no direct effect on users; however, it does
     * affect which methods throw certain SQLExceptions.
     * <P>
     * Result sets created using the returned <code>PreparedStatement</code>
     * object will by default be type <code>TYPE_FORWARD_ONLY</code>
     * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     *  创建一个默认的<code> PreparedStatement </code>对象,可以检索自动生成的键。给定常量告诉驱动程序是否应该使自动生成的键可用于检索。
     * 如果SQL语句不是<code> INSERT </code>语句或能够返回自动生成的键的SQL语句(此类语句的列表是供应商特定的),则忽略此参数。
     * <P>
     *  <B>注意：</B>此方法经过优化,用于处理受益于预编译的参数化SQL语句。如果驱动程序支持预编译,方法<code> prepareStatement </code>会将语句发送到数据库进行预编译。
     * 某些驱动程序可能不支持预编译。在这种情况下,在执行<code> PreparedStatement </code>对象之前,语句可能不会发送到数据库。
     * 这对用户没有直接影响;然而,它确实影响哪些方法抛出某些SQLExceptions。
     * <P>
     * 使用返回的<code> PreparedStatement </code>对象创建的结果集默认为类型<code> TYPE_FORWARD_ONLY </code>,并且并发级别为<code> CONC
     * UR_READ_ONLY </code>。
     * 可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @param sql an SQL statement that may contain one or more '?' IN
     *        parameter placeholders
     * @param autoGeneratedKeys a flag indicating whether auto-generated keys
     *        should be returned; one of
     *        <code>Statement.RETURN_GENERATED_KEYS</code> or
     *        <code>Statement.NO_GENERATED_KEYS</code>
     * @return a new <code>PreparedStatement</code> object, containing the
     *         pre-compiled SQL statement, that will have the capability of
     *         returning auto-generated keys
     * @exception SQLException if a database access error occurs, this
     *  method is called on a closed connection
     *         or the given parameter is not a <code>Statement</code>
     *         constant indicating whether auto-generated keys should be
     *         returned
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method with a constant of Statement.RETURN_GENERATED_KEYS
     * @since 1.4
     */
    PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
        throws SQLException;

    /**
     * Creates a default <code>PreparedStatement</code> object capable
     * of returning the auto-generated keys designated by the given array.
     * This array contains the indexes of the columns in the target
     * table that contain the auto-generated keys that should be made
     * available.  The driver will ignore the array if the SQL statement
     * is not an <code>INSERT</code> statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     *<p>
     * An SQL statement with or without IN parameters can be
     * pre-compiled and stored in a <code>PreparedStatement</code> object. This
     * object can then be used to efficiently execute this statement
     * multiple times.
     * <P>
     * <B>Note:</B> This method is optimized for handling
     * parametric SQL statements that benefit from precompilation. If
     * the driver supports precompilation,
     * the method <code>prepareStatement</code> will send
     * the statement to the database for precompilation. Some drivers
     * may not support precompilation. In this case, the statement may
     * not be sent to the database until the <code>PreparedStatement</code>
     * object is executed.  This has no direct effect on users; however, it does
     * affect which methods throw certain SQLExceptions.
     * <P>
     * Result sets created using the returned <code>PreparedStatement</code>
     * object will by default be type <code>TYPE_FORWARD_ONLY</code>
     * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     *  创建一个默认的<code> PreparedStatement </code>对象,能够返回由给定数组指定的自动生成的键。此数组包含目标表中包含应该可用的自动生成的键的列的索引。
     * 如果SQL语句不是<code> INSERT </code>语句或能够返回自动生成的键的SQL语句(此类语句的列表是供应商特定的),则驱动程序将忽略该数组。
     * p>
     *  具有或不具有IN参数的SQL语句可以被预编译并存储在<code> PreparedStatement </code>对象中。然后可以使用此对象多次有效地执行此语句。
     * <P>
     *  <B>注意：</B>此方法经过优化,用于处理受益于预编译的参数化SQL语句。如果驱动程序支持预编译,方法<code> prepareStatement </code>会将语句发送到数据库进行预编译。
     * 某些驱动程序可能不支持预编译。在这种情况下,在执行<code> PreparedStatement </code>对象之前,语句可能不会发送到数据库。
     * 这对用户没有直接影响;然而,它确实影响哪些方法抛出某些SQLExceptions。
     * <P>
     * 使用返回的<code> PreparedStatement </code>对象创建的结果集默认为类型<code> TYPE_FORWARD_ONLY </code>,并且并发级别为<code> CONC
     * UR_READ_ONLY </code>。
     * 可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @param sql an SQL statement that may contain one or more '?' IN
     *        parameter placeholders
     * @param columnIndexes an array of column indexes indicating the columns
     *        that should be returned from the inserted row or rows
     * @return a new <code>PreparedStatement</code> object, containing the
     *         pre-compiled statement, that is capable of returning the
     *         auto-generated keys designated by the given array of column
     *         indexes
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     *
     * @since 1.4
     */
    PreparedStatement prepareStatement(String sql, int columnIndexes[])
        throws SQLException;

    /**
     * Creates a default <code>PreparedStatement</code> object capable
     * of returning the auto-generated keys designated by the given array.
     * This array contains the names of the columns in the target
     * table that contain the auto-generated keys that should be returned.
     * The driver will ignore the array if the SQL statement
     * is not an <code>INSERT</code> statement, or an SQL statement able to return
     * auto-generated keys (the list of such statements is vendor-specific).
     * <P>
     * An SQL statement with or without IN parameters can be
     * pre-compiled and stored in a <code>PreparedStatement</code> object. This
     * object can then be used to efficiently execute this statement
     * multiple times.
     * <P>
     * <B>Note:</B> This method is optimized for handling
     * parametric SQL statements that benefit from precompilation. If
     * the driver supports precompilation,
     * the method <code>prepareStatement</code> will send
     * the statement to the database for precompilation. Some drivers
     * may not support precompilation. In this case, the statement may
     * not be sent to the database until the <code>PreparedStatement</code>
     * object is executed.  This has no direct effect on users; however, it does
     * affect which methods throw certain SQLExceptions.
     * <P>
     * Result sets created using the returned <code>PreparedStatement</code>
     * object will by default be type <code>TYPE_FORWARD_ONLY</code>
     * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
     * The holdability of the created result sets can be determined by
     * calling {@link #getHoldability}.
     *
     * <p>
     *  创建一个默认的<code> PreparedStatement </code>对象,能够返回由给定数组指定的自动生成的键。此数组包含目标表中包含应返回的自动生成的键的列的名称。
     * 如果SQL语句不是<code> INSERT </code>语句或能够返回自动生成的键的SQL语句(此类语句的列表是供应商特定的),则驱动程序将忽略该数组。
     * <P>
     *  具有或不具有IN参数的SQL语句可以被预编译并存储在<code> PreparedStatement </code>对象中。然后可以使用此对象多次有效地执行此语句。
     * <P>
     *  <B>注意：</B>此方法经过优化,用于处理受益于预编译的参数化SQL语句。如果驱动程序支持预编译,方法<code> prepareStatement </code>会将语句发送到数据库进行预编译。
     * 某些驱动程序可能不支持预编译。在这种情况下,在执行<code> PreparedStatement </code>对象之前,语句可能不会发送到数据库。
     * 这对用户没有直接影响;然而,它确实影响哪些方法抛出某些SQLExceptions。
     * <P>
     * 使用返回的<code> PreparedStatement </code>对象创建的结果集默认为类型<code> TYPE_FORWARD_ONLY </code>,并且并发级别为<code> CONC
     * UR_READ_ONLY </code>。
     * 可以通过调用{@link #getHoldability}来确定已创建结果集的可保留性。
     * 
     * 
     * @param sql an SQL statement that may contain one or more '?' IN
     *        parameter placeholders
     * @param columnNames an array of column names indicating the columns
     *        that should be returned from the inserted row or rows
     * @return a new <code>PreparedStatement</code> object, containing the
     *         pre-compiled statement, that is capable of returning the
     *         auto-generated keys designated by the given array of column
     *         names
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     *
     * @since 1.4
     */
    PreparedStatement prepareStatement(String sql, String columnNames[])
        throws SQLException;

    /**
     * Constructs an object that implements the <code>Clob</code> interface. The object
     * returned initially contains no data.  The <code>setAsciiStream</code>,
     * <code>setCharacterStream</code> and <code>setString</code> methods of
     * the <code>Clob</code> interface may be used to add data to the <code>Clob</code>.
     * <p>
     *  构造实现<code> Clob </code>接口的对象。返回的对象最初不包含数据。
     *  <code> Clob </code>接口的<code> setAsciiStream </code>,<code> setCharacterStream </code>和<code> setStri
     * ng </code>方法可用于将数据添加到<code> Clob </code>。
     *  构造实现<code> Clob </code>接口的对象。返回的对象最初不包含数据。
     * 
     * 
     * @return An object that implements the <code>Clob</code> interface
     * @throws SQLException if an object that implements the
     * <code>Clob</code> interface can not be constructed, this method is
     * called on a closed connection or a database access error occurs.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this data type
     *
     * @since 1.6
     */
    Clob createClob() throws SQLException;

    /**
     * Constructs an object that implements the <code>Blob</code> interface. The object
     * returned initially contains no data.  The <code>setBinaryStream</code> and
     * <code>setBytes</code> methods of the <code>Blob</code> interface may be used to add data to
     * the <code>Blob</code>.
     * <p>
     *  构造一个实现<code> Blob </code>接口的对象。返回的对象最初不包含数据。
     * 接口<code> Blob </code>的<code> setBinaryStream </code>和<code> setBytes </code>方法可用于向<code> Blob </code>
     * 添加数据。
     *  构造一个实现<code> Blob </code>接口的对象。返回的对象最初不包含数据。
     * 
     * 
     * @return  An object that implements the <code>Blob</code> interface
     * @throws SQLException if an object that implements the
     * <code>Blob</code> interface can not be constructed, this method is
     * called on a closed connection or a database access error occurs.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this data type
     *
     * @since 1.6
     */
    Blob createBlob() throws SQLException;

    /**
     * Constructs an object that implements the <code>NClob</code> interface. The object
     * returned initially contains no data.  The <code>setAsciiStream</code>,
     * <code>setCharacterStream</code> and <code>setString</code> methods of the <code>NClob</code> interface may
     * be used to add data to the <code>NClob</code>.
     * <p>
     *  构造实现<code> NClob </code>接口的对象。返回的对象最初不包含数据。
     *  <code> NClob </code>接口的<code> setAsciiStream </code>,<code> setCharacterStream </code>和<code> setStr
     * ing </code>方法可用于将数据添加到<code> NClob </code>。
     *  构造实现<code> NClob </code>接口的对象。返回的对象最初不包含数据。
     * 
     * 
     * @return An object that implements the <code>NClob</code> interface
     * @throws SQLException if an object that implements the
     * <code>NClob</code> interface can not be constructed, this method is
     * called on a closed connection or a database access error occurs.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this data type
     *
     * @since 1.6
     */
    NClob createNClob() throws SQLException;

    /**
     * Constructs an object that implements the <code>SQLXML</code> interface. The object
     * returned initially contains no data. The <code>createXmlStreamWriter</code> object and
     * <code>setString</code> method of the <code>SQLXML</code> interface may be used to add data to the <code>SQLXML</code>
     * object.
     * <p>
     *  构造实现<code> SQLXML </code>接口的对象。返回的对象最初不包含数据。
     * 可以使用<code> SQLXML </code>接口的<code> createXmlStreamWriter </code>对象和<code> setString </code>方法向<code> 
     * SQLXML </code>对象添加数据。
     *  构造实现<code> SQLXML </code>接口的对象。返回的对象最初不包含数据。
     * 
     * 
     * @return An object that implements the <code>SQLXML</code> interface
     * @throws SQLException if an object that implements the <code>SQLXML</code> interface can not
     * be constructed, this method is
     * called on a closed connection or a database access error occurs.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this data type
     * @since 1.6
     */
    SQLXML createSQLXML() throws SQLException;

        /**
         * Returns true if the connection has not been closed and is still valid.
         * The driver shall submit a query on the connection or use some other
         * mechanism that positively verifies the connection is still valid when
         * this method is called.
         * <p>
         * The query submitted by the driver to validate the connection shall be
         * executed in the context of the current transaction.
         *
         * <p>
         * 如果连接尚未关闭并仍然有效,则返回true。驱动程序应该提交一个关于连接的查询或使用一些其他机制,当这个方法被调用时,肯定地验证连接是否仍然有效。
         * <p>
         *  由驱动程序提交以验证连接的查询应在当前事务的上下文中执行。
         * 
         * 
         * @param timeout -             The time in seconds to wait for the database operation
         *                                              used to validate the connection to complete.  If
         *                                              the timeout period expires before the operation
         *                                              completes, this method returns false.  A value of
         *                                              0 indicates a timeout is not applied to the
         *                                              database operation.
         * <p>
         * @return true if the connection is valid, false otherwise
         * @exception SQLException if the value supplied for <code>timeout</code>
         * is less then 0
         * @since 1.6
         *
         * @see java.sql.DatabaseMetaData#getClientInfoProperties
         */
         boolean isValid(int timeout) throws SQLException;

        /**
         * Sets the value of the client info property specified by name to the
         * value specified by value.
         * <p>
         * Applications may use the <code>DatabaseMetaData.getClientInfoProperties</code>
         * method to determine the client info properties supported by the driver
         * and the maximum length that may be specified for each property.
         * <p>
         * The driver stores the value specified in a suitable location in the
         * database.  For example in a special register, session parameter, or
         * system table column.  For efficiency the driver may defer setting the
         * value in the database until the next time a statement is executed or
         * prepared.  Other than storing the client information in the appropriate
         * place in the database, these methods shall not alter the behavior of
         * the connection in anyway.  The values supplied to these methods are
         * used for accounting, diagnostics and debugging purposes only.
         * <p>
         * The driver shall generate a warning if the client info name specified
         * is not recognized by the driver.
         * <p>
         * If the value specified to this method is greater than the maximum
         * length for the property the driver may either truncate the value and
         * generate a warning or generate a <code>SQLClientInfoException</code>.  If the driver
         * generates a <code>SQLClientInfoException</code>, the value specified was not set on the
         * connection.
         * <p>
         * The following are standard client info properties.  Drivers are not
         * required to support these properties however if the driver supports a
         * client info property that can be described by one of the standard
         * properties, the standard property name should be used.
         *
         * <ul>
         * <li>ApplicationName  -       The name of the application currently utilizing
         *                                                      the connection</li>
         * <li>ClientUser               -       The name of the user that the application using
         *                                                      the connection is performing work for.  This may
         *                                                      not be the same as the user name that was used
         *                                                      in establishing the connection.</li>
         * <li>ClientHostname   -       The hostname of the computer the application
         *                                                      using the connection is running on.</li>
         * </ul>
         * <p>
         * <p>
         *  将name指定的客户机info属性的值设置为value指定的值。
         * <p>
         *  应用程序可以使用<code> DatabaseMetaData.getClientInfoProperties </code>方法来确定驱动程序支持的客户端信息属性以及可为每个属性指定的最大长度。
         * <p>
         *  驱动程序存储在数据库中合适位置中指定的值。例如在特殊寄存器,会话参数或系统表列中。为了效率,驱动程序可以推迟设置数据库中的值,直到下一次执行或准备语句。
         * 除了将客户端信息存储在数据库中的适当位置之外,这些方法不会改变连接的行为。提供给这些方法的值仅用于记帐,诊断和调试目的。
         * <p>
         *  如果指定的客户端信息名称未被驱动程序识别,驱动程序将生成警告。
         * <p>
         * 如果为此方法指定的值大于属性的最大长度,驱动程序可能会截断该值并生成警告或生成<code> SQLClientInfoException </code>。
         * 如果驱动程序生成<code> SQLClientInfoException </code>,则未在连接上设置指定的值。
         * <p>
         *  以下是标准客户端信息属性。驱动程序不需要支持这些属性,但是如果驱动程序支持可以由标准属性之一描述的客户机信息属性,则应使用标准属性名称。
         * 
         * <ul>
         *  <li> ApplicationName  - 当前正在使用连接的应用程序的名称</li> <li> ClientUser  - 使用连接的应用程序执行工作的用户的名称。
         * 这可能与在建立连接时使用的用户名不同。</li> <li> ClientHostname  - 正在使用连接的应用程序正在运行的计算机的主机名。</li>。
         * </ul>
         * <p>
         * 
         * @param name          The name of the client info property to set
         * @param value         The value to set the client info property to.  If the
         *                                      value is null, the current value of the specified
         *                                      property is cleared.
         * <p>
         * @throws      SQLClientInfoException if the database server returns an error while
         *                      setting the client info value on the database server or this method
         * is called on a closed connection
         * <p>
         * @since 1.6
         */
         void setClientInfo(String name, String value)
                throws SQLClientInfoException;

        /**
     * Sets the value of the connection's client info properties.  The
     * <code>Properties</code> object contains the names and values of the client info
     * properties to be set.  The set of client info properties contained in
     * the properties list replaces the current set of client info properties
     * on the connection.  If a property that is currently set on the
     * connection is not present in the properties list, that property is
     * cleared.  Specifying an empty properties list will clear all of the
     * properties on the connection.  See <code>setClientInfo (String, String)</code> for
     * more information.
     * <p>
     * If an error occurs in setting any of the client info properties, a
     * <code>SQLClientInfoException</code> is thrown. The <code>SQLClientInfoException</code>
     * contains information indicating which client info properties were not set.
     * The state of the client information is unknown because
     * some databases do not allow multiple client info properties to be set
     * atomically.  For those databases, one or more properties may have been
     * set before the error occurred.
     * <p>
     *
     * <p>
     * 设置连接的客户端信息属性的值。 <code> Properties </code>对象包含要设置的客户端信息属性的名称和值。属性列表中包含的一组客户端信息属性替换连接上的当前客户端信息属性集。
     * 如果属性列表中不存在当前在连接上设置的属性,则该属性将被清除。指定空属性列表将清除连接上的所有属性。
     * 有关详细信息,请参阅<code> setClientInfo(String,String)</code>。
     * <p>
     *  如果在设置任何客户端信息属性时发生错误,则会抛出<code> SQLClientInfoException </code>。
     *  <code> SQLClientInfoException </code>包含指示未设置哪些客户端信息属性的信息。客户端信息的状态未知,因为一些数据库不允许原子地设置多个客户端信息属性。
     * 对于这些数据库,可能在错误发生之前设置了一个或多个属性。
     * <p>
     * 
     * 
     * @param properties                the list of client info properties to set
     * <p>
     * @see java.sql.Connection#setClientInfo(String, String) setClientInfo(String, String)
     * @since 1.6
     * <p>
     * @throws SQLClientInfoException if the database server returns an error while
     *                  setting the clientInfo values on the database server or this method
     * is called on a closed connection
     *
     */
         void setClientInfo(Properties properties)
                throws SQLClientInfoException;

        /**
         * Returns the value of the client info property specified by name.  This
         * method may return null if the specified client info property has not
         * been set and does not have a default value.  This method will also
         * return null if the specified client info property name is not supported
         * by the driver.
         * <p>
         * Applications may use the <code>DatabaseMetaData.getClientInfoProperties</code>
         * method to determine the client info properties supported by the driver.
         * <p>
         * <p>
         *  返回由name指定的客户端info属性的值。如果未设置指定的客户机info属性并且没有默认值,则此方法可能返回null。如果驱动程序不支持指定的客户端信息属性名称,此方法也将返回null。
         * <p>
         *  应用程序可以使用<code> DatabaseMetaData.getClientInfoProperties </code>方法来确定驱动程序支持的客户端信息属性。
         * <p>
         * 
         * @param name          The name of the client info property to retrieve
         * <p>
         * @return                      The value of the client info property specified
         * <p>
         * @throws SQLException         if the database server returns an error when
         *                                                      fetching the client info value from the database
         *or this method is called on a closed connection
         * <p>
         * @since 1.6
         *
         * @see java.sql.DatabaseMetaData#getClientInfoProperties
         */
         String getClientInfo(String name)
                throws SQLException;

        /**
         * Returns a list containing the name and current value of each client info
         * property supported by the driver.  The value of a client info property
         * may be null if the property has not been set and does not have a
         * default value.
         * <p>
         * <p>
         * 返回包含驱动程序支持的每个客户端info属性的名称和当前值的列表。如果未设置属性并且没有默认值,则客户端info属性的值可以为null。
         * <p>
         * 
         * @return      A <code>Properties</code> object that contains the name and current value of
         *                      each of the client info properties supported by the driver.
         * <p>
         * @throws      SQLException if the database server returns an error when
         *                      fetching the client info values from the database
         * or this method is called on a closed connection
         * <p>
         * @since 1.6
         */
         Properties getClientInfo()
                throws SQLException;

/**
  * Factory method for creating Array objects.
  *<p>
  * <b>Note: </b>When <code>createArrayOf</code> is used to create an array object
  * that maps to a primitive data type, then it is implementation-defined
  * whether the <code>Array</code> object is an array of that primitive
  * data type or an array of <code>Object</code>.
  * <p>
  * <b>Note: </b>The JDBC driver is responsible for mapping the elements
  * <code>Object</code> array to the default JDBC SQL type defined in
  * java.sql.Types for the given class of <code>Object</code>. The default
  * mapping is specified in Appendix B of the JDBC specification.  If the
  * resulting JDBC type is not the appropriate type for the given typeName then
  * it is implementation defined whether an <code>SQLException</code> is
  * thrown or the driver supports the resulting conversion.
  *
  * <p>
  *  创建Array对象的工厂方法。
  * p>
  *  <b>注意：</b>当<code> createArrayOf </code>用于创建映射到原始数据类型的数组对象时,无论<code> Array </code>对象是该原始数据类型的数组或<code>
  *  Object </code>的数组。
  * <p>
  *  <b>注意：</b> JDBC驱动程序负责将元素<code> Object </code>映射到java.sql.Types中定义的默认JDBC SQL类型, / code>。
  * 默认映射在JDBC规范的附录B中指定。
  * 如果生成的JDBC类型不是给定typeName的适当类型,那么它是由实现定义的是否抛出<code> SQLException </code>或驱动程序支持生成的转换。
  * 
  * 
  * @param typeName the SQL name of the type the elements of the array map to. The typeName is a
  * database-specific name which may be the name of a built-in type, a user-defined type or a standard  SQL type supported by this database. This
  *  is the value returned by <code>Array.getBaseTypeName</code>
  * @param elements the elements that populate the returned object
  * @return an Array object whose elements map to the specified SQL type
  * @throws SQLException if a database error occurs, the JDBC type is not
  *  appropriate for the typeName and the conversion is not supported, the typeName is null or this method is called on a closed connection
  * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this data type
  * @since 1.6
  */
 Array createArrayOf(String typeName, Object[] elements) throws
SQLException;

/**
  * Factory method for creating Struct objects.
  *
  * <p>
  *  用于创建Struct对象的工厂方法。
  * 
  * 
  * @param typeName the SQL type name of the SQL structured type that this <code>Struct</code>
  * object maps to. The typeName is the name of  a user-defined type that
  * has been defined for this database. It is the value returned by
  * <code>Struct.getSQLTypeName</code>.

  * @param attributes the attributes that populate the returned object
  *  @return a Struct object that maps to the given SQL type and is populated with the given attributes
  * @throws SQLException if a database error occurs, the typeName is null or this method is called on a closed connection
  * @throws SQLFeatureNotSupportedException  if the JDBC driver does not support this data type
  * @since 1.6
  */
 Struct createStruct(String typeName, Object[] attributes)
throws SQLException;

   //--------------------------JDBC 4.1 -----------------------------

   /**
    * Sets the given schema name to access.
    * <P>
    * If the driver does not support schemas, it will
    * silently ignore this request.
    * <p>
    * Calling {@code setSchema} has no effect on previously created or prepared
    * {@code Statement} objects. It is implementation defined whether a DBMS
    * prepare operation takes place immediately when the {@code Connection}
    * method {@code prepareStatement} or {@code prepareCall} is invoked.
    * For maximum portability, {@code setSchema} should be called before a
    * {@code Statement} is created or prepared.
    *
    * <p>
    *  设置要访问的给定模式名称。
    * <P>
    *  如果驱动程序不支持模式,它将默认忽略此请求。
    * <p>
    * 调用{@code setSchema}对先前创建或准备的{@code Statement}对象没有影响。
    * 它是实现定义是否当调用{@code Connection}方法{@code prepareStatement}或{@code prepareCall}时立即执行DBMS准备操作。
    * 为了实现最大的可移植性,应在创建或准备{@code Statement}之前调用{@code setSchema}。
    * 
    * 
    * @param schema the name of a schema  in which to work
    * @exception SQLException if a database access error occurs
    * or this method is called on a closed connection
    * @see #getSchema
    * @since 1.7
    */
    void setSchema(String schema) throws SQLException;

    /**
     * Retrieves this <code>Connection</code> object's current schema name.
     *
     * <p>
     *  检索此<<code> Connection </code>对象的当前模式名称。
     * 
     * 
     * @return the current schema name or <code>null</code> if there is none
     * @exception SQLException if a database access error occurs
     * or this method is called on a closed connection
     * @see #setSchema
     * @since 1.7
     */
    String getSchema() throws SQLException;

    /**
     * Terminates an open connection.  Calling <code>abort</code> results in:
     * <ul>
     * <li>The connection marked as closed
     * <li>Closes any physical connection to the database
     * <li>Releases resources used by the connection
     * <li>Insures that any thread that is currently accessing the connection
     * will either progress to completion or throw an <code>SQLException</code>.
     * </ul>
     * <p>
     * Calling <code>abort</code> marks the connection closed and releases any
     * resources. Calling <code>abort</code> on a closed connection is a
     * no-op.
     * <p>
     * It is possible that the aborting and releasing of the resources that are
     * held by the connection can take an extended period of time.  When the
     * <code>abort</code> method returns, the connection will have been marked as
     * closed and the <code>Executor</code> that was passed as a parameter to abort
     * may still be executing tasks to release resources.
     * <p>
     * This method checks to see that there is an <code>SQLPermission</code>
     * object before allowing the method to proceed.  If a
     * <code>SecurityManager</code> exists and its
     * <code>checkPermission</code> method denies calling <code>abort</code>,
     * this method throws a
     * <code>java.lang.SecurityException</code>.
     * <p>
     *  终止打开的连接。调用<code> abort </code>结果：
     * <ul>
     *  <li>关闭标记为已关闭的连接<li>关闭与数据库的任何物理连接<li>释放连接使用的资源<li>确保当前正在访问连接的任何线程都将进度完成或抛出<code > SQLException </code>
     * 。
     * </ul>
     * <p>
     *  调用<code> abort </code>标记连接关闭并释放任何资源。在关闭的连接上调用<code> abort </code>是一个无操作。
     * <p>
     *  可能的是,由连接持有的资源的中止和释放可能花费延长的时间段。
     * 当<code> abort </code>方法返回时,连接将被标记为已关闭,并且作为参数传递到中止的<code> Executor </code>可能仍在执行任务以释放资源。
     * <p>
     * 此方法检查以查看是否有一个<code> SQLPermission </code>对象,然后才允许该方法继续。
     * 如果存在<code> SecurityManager </code>并且其<code> checkPermission </code>方法拒绝调用<code> abort </code>,此方法会抛出<code>
     *  java.lang.SecurityException </code>。
     * 此方法检查以查看是否有一个<code> SQLPermission </code>对象,然后才允许该方法继续。
     * 
     * 
     * @param executor  The <code>Executor</code>  implementation which will
     * be used by <code>abort</code>.
     * @throws java.sql.SQLException if a database access error occurs or
     * the {@code executor} is {@code null},
     * @throws java.lang.SecurityException if a security manager exists and its
     *    <code>checkPermission</code> method denies calling <code>abort</code>
     * @see SecurityManager#checkPermission
     * @see Executor
     * @since 1.7
     */
    void abort(Executor executor) throws SQLException;

    /**
     *
     * Sets the maximum period a <code>Connection</code> or
     * objects created from the <code>Connection</code>
     * will wait for the database to reply to any one request. If any
     *  request remains unanswered, the waiting method will
     * return with a <code>SQLException</code>, and the <code>Connection</code>
     * or objects created from the <code>Connection</code>  will be marked as
     * closed. Any subsequent use of
     * the objects, with the exception of the <code>close</code>,
     * <code>isClosed</code> or <code>Connection.isValid</code>
     * methods, will result in  a <code>SQLException</code>.
     * <p>
     * <b>Note</b>: This method is intended to address a rare but serious
     * condition where network partitions can cause threads issuing JDBC calls
     * to hang uninterruptedly in socket reads, until the OS TCP-TIMEOUT
     * (typically 10 minutes). This method is related to the
     * {@link #abort abort() } method which provides an administrator
     * thread a means to free any such threads in cases where the
     * JDBC connection is accessible to the administrator thread.
     * The <code>setNetworkTimeout</code> method will cover cases where
     * there is no administrator thread, or it has no access to the
     * connection. This method is severe in it's effects, and should be
     * given a high enough value so it is never triggered before any more
     * normal timeouts, such as transaction timeouts.
     * <p>
     * JDBC driver implementations  may also choose to support the
     * {@code setNetworkTimeout} method to impose a limit on database
     * response time, in environments where no network is present.
     * <p>
     * Drivers may internally implement some or all of their API calls with
     * multiple internal driver-database transmissions, and it is left to the
     * driver implementation to determine whether the limit will be
     * applied always to the response to the API call, or to any
     * single  request made during the API call.
     * <p>
     *
     * This method can be invoked more than once, such as to set a limit for an
     * area of JDBC code, and to reset to the default on exit from this area.
     * Invocation of this method has no impact on already outstanding
     * requests.
     * <p>
     * The {@code Statement.setQueryTimeout()} timeout value is independent of the
     * timeout value specified in {@code setNetworkTimeout}. If the query timeout
     * expires  before the network timeout then the
     * statement execution will be canceled. If the network is still
     * active the result will be that both the statement and connection
     * are still usable. However if the network timeout expires before
     * the query timeout or if the statement timeout fails due to network
     * problems, the connection will be marked as closed, any resources held by
     * the connection will be released and both the connection and
     * statement will be unusable.
     *<p>
     * When the driver determines that the {@code setNetworkTimeout} timeout
     * value has expired, the JDBC driver marks the connection
     * closed and releases any resources held by the connection.
     * <p>
     *
     * This method checks to see that there is an <code>SQLPermission</code>
     * object before allowing the method to proceed.  If a
     * <code>SecurityManager</code> exists and its
     * <code>checkPermission</code> method denies calling
     * <code>setNetworkTimeout</code>, this method throws a
     * <code>java.lang.SecurityException</code>.
     *
     * <p>
     *  设置<code> Connection </code>或从<code> Connection </code>创建的对象将等待数据库回复任何一个请求的最长时间。
     * 如果任何请求仍未被应答,则等待方法将返回<code> SQLException </code>,而<code> Connection </code>或从<code> Connection </code>
     * 创建的对象将被标记为关闭。
     *  设置<code> Connection </code>或从<code> Connection </code>创建的对象将等待数据库回复任何一个请求的最长时间。
     * 除了<code> close </code>,<code> isClosed </code>或<code> Connection.isValid </code>方法之外,对象的任何后续使用都将导致<code>
     *  SQLException </code>。
     *  设置<code> Connection </code>或从<code> Connection </code>创建的对象将等待数据库回复任何一个请求的最长时间。
     * <p>
     * <b>注意</b>：此方法旨在解决一个罕见但严重的情况,其中网络分区可能导致线程发出JDBC调用,不间断地在套接字读取中挂起,直到操作系统TCP-TIMEOUT(通常为10分钟)。
     * 此方法与{@link #abort abort()}方法相关,该方法在管理员线程可访问JDBC连接的情况下,为管理员线程提供释放任何此类线程的方法。
     *  <code> setNetworkTimeout </code>方法将涵盖没有管理员线程或无法访问连接的情况。
     * 这种方法在它的效果是严重的,应该给一个足够高的值,所以它从来没有触发任何更正常的超时,如事务超时。
     * <p>
     *  JDBC驱动程序实现也可以选择支持{@code setNetworkTimeout}方法,在没有网络的环境中对数据库响应时间施加限制。
     * <p>
     *  驱动程序可以使用多个内部驱动程序数据库传输在内部实现它们的一些或所有API调用,并且留给驱动程序实现以确定该限制是否将总是应用于对API调用的响应,或者任何单个请求在API调用期间。
     * <p>
     * 
     * @param executor  The <code>Executor</code>  implementation which will
     * be used by <code>setNetworkTimeout</code>.
     * @param milliseconds The time in milliseconds to wait for the database
     * operation
     *  to complete.  If the JDBC driver does not support milliseconds, the
     * JDBC driver will round the value up to the nearest second.  If the
     * timeout period expires before the operation
     * completes, a SQLException will be thrown.
     * A value of 0 indicates that there is not timeout for database operations.
     * @throws java.sql.SQLException if a database access error occurs, this
     * method is called on a closed connection,
     * the {@code executor} is {@code null},
     * or the value specified for <code>seconds</code> is less than 0.
     * @throws java.lang.SecurityException if a security manager exists and its
     *    <code>checkPermission</code> method denies calling
     * <code>setNetworkTimeout</code>.
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see SecurityManager#checkPermission
     * @see Statement#setQueryTimeout
     * @see #getNetworkTimeout
     * @see #abort
     * @see Executor
     * @since 1.7
     */
    void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException;


    /**
     * Retrieves the number of milliseconds the driver will
     * wait for a database request to complete.
     * If the limit is exceeded, a
     * <code>SQLException</code> is thrown.
     *
     * <p>
     * 
     *  此方法可以多次调用,例如设置JDBC代码区域的限制,并在退出此区域时重置为默认值。调用此方法对已经存在的请求没有影响。
     * <p>
     * {@code Statement.setQueryTimeout()}超时值与{@code setNetworkTimeout}中指定的超时值无关。如果查询超时在网络超时之前超时,则语句执行将被取消。
     * 如果网络仍然活动,结果将是语句和连接仍然可用。但是,如果网络超时在查询超时之前超时或如果语句超时由于网络问题失败,则连接将标记为已关闭,连接保存的任何资源将被释放,并且连接和语句将不可用。
     * p>
     *  当驱动程序确定{@code setNetworkTimeout}超时值已过期时,JDBC驱动程序会将连接标记为已关闭,并释放连接持有的任何资源。
     * <p>
     * 
     * 
     * @return the current timeout limit in milliseconds; zero means there is
     *         no limit
     * @throws SQLException if a database access error occurs or
     * this method is called on a closed <code>Connection</code>
     * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
     * this method
     * @see #setNetworkTimeout
     * @since 1.7
     */
    int getNetworkTimeout() throws SQLException;
}
