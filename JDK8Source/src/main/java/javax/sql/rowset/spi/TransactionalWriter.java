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

package javax.sql.rowset.spi;

import java.sql.SQLException;
import java.io.Reader;

import javax.sql.RowSetWriter;
import javax.sql.rowset.*;
import java.sql.Savepoint;

/**
 * A specialized interface that facilitates an extension of the standard
 * <code>SyncProvider</code> abstract class so that it has finer grained
 * transaction control.
 * <p>
 * If one or more disconnected <code>RowSet</code> objects are participating
 * in a global transaction, they may wish to coordinate their synchronization
 * commits to preserve data integrity and reduce the number of
 * synchronization exceptions. If this is the case, an application should set
 * the <code>CachedRowSet</code> constant <code>COMMIT_ON_ACCEPT_CHANGES</code>
 * to <code>false</code> and use the <code>commit</code> and <code>rollback</code>
 * methods defined in this interface to manage transaction boundaries.
 * <p>
 *  一个专用接口,方便标准的<code> SyncProvider </code>抽象类的扩展,以使它具有更细粒度的事务控制。
 * <p>
 *  如果一个或多个断开的<code> RowSet </code>对象参与全局事务,则他们可能希望协调它们的同步提交以保持数据完整性并减少同步异常的数目。
 * 如果是这种情况,应用程序应将<code> CachedRowSet </code>常量<code> COMMIT_ON_ACCEPT_CHANGES </code>设置为<code> false </code>
 * ,并使用<code> commit <代码> rollback </code>在此接口中定义的方法来管理事务边界。
 *  如果一个或多个断开的<code> RowSet </code>对象参与全局事务,则他们可能希望协调它们的同步提交以保持数据完整性并减少同步异常的数目。
 * 
 */
public interface TransactionalWriter extends RowSetWriter {

    /**
     * Makes permanent all changes that have been performed by the
     * <code>acceptChanges</code> method since the last call to either the
     * <code>commit</code> or <code>rollback</code> methods.
     * This method should be used only when auto-commit mode has been disabled.
     *
     * <p>
     *  自上次调用<code> commit </code>或<code> rollback </code>方法以来,对<code> acceptChanges </code>方法执行的所有更改都将永久生效。
     * 仅当禁用自动提交模式时,才应使用此方法。
     * 
     * 
     * @throws SQLException  if a database access error occurs or the
     *         <code>Connection</code> object within this <code>CachedRowSet</code>
     *         object is in auto-commit mode
     */
    public void commit() throws SQLException;

    /**
     * Undoes all changes made in the current transaction. This method should be
     * used only when auto-commit mode has been disabled.
     *
     * <p>
     *  撤销当前事务中所做的所有更改。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * 
     * @throws SQLException if a database access error occurs or the <code>Connection</code>
     *         object within this <code>CachedRowSet</code> object is in auto-commit mode
     */
    public void rollback() throws SQLException;

    /**
     * Undoes all changes made in the current transaction made prior to the given
     * <code>Savepoint</code> object.  This method should be used only when auto-commit
     * mode has been disabled.
     *
     * <p>
     *  撤销在给定的<code> Savepoint </code>对象之前对当前事务所做的所有更改。仅当禁用自动提交模式时,才应使用此方法。
     * 
     * @param s a <code>Savepoint</code> object marking a savepoint in the current
     *        transaction.  All changes made before <i>s</i> was set will be undone.
     *        All changes made after <i>s</i> was set will be made permanent.
     * @throws SQLException if a database access error occurs or the <code>Connection</code>
     *         object within this <code>CachedRowSet</code> object is in auto-commit mode
     */
    public void rollback(Savepoint s) throws SQLException;
}
