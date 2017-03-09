/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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

/*
 * Created on Apr 28, 2005
 * <p>
 *  创建于2005年4月28日
 * 
 */
package javax.sql;

/**
 * An object that registers to be notified of events that occur on PreparedStatements
 * that are in the Statement pool.
 * <p>
 * The JDBC 3.0 specification added the maxStatements
 * <code>ConnectionPooledDataSource</code> property to provide a standard mechanism for
 * enabling the pooling of <code>PreparedStatements</code>
 * and to specify the size of the statement
 * pool.  However, there was no way for a driver to notify an external
 * statement pool when a <code>PreparedStatement</code> becomes invalid.  For some databases, a
 * statement becomes invalid if a DDL operation is performed that affects the
 * table.  For example an application may create a temporary table to do some work
 * on the table and then destroy it.  It may later recreate the same table when
 * it is needed again.  Some databases will invalidate any prepared statements
 * that reference the temporary table when the table is dropped.
 * <p>
 * Similar to the methods defined in the <code>ConnectionEventListener</code> interface,
 * the driver will call the <code>StatementEventListener.statementErrorOccurred</code>
 * method prior to throwing any exceptions when it detects a statement is invalid.
 * The driver will also call the <code>StatementEventListener.statementClosed</code>
 * method when a <code>PreparedStatement</code> is closed.
 * <p>
 * Methods which allow a component to register a StatementEventListener with a
 * <code>PooledConnection</code> have been added to the <code>PooledConnection</code> interface.
 * <p>
 * <p>
 *  注册以通知在Statement池中的PreparedStatements上发生的事件的对象。
 * <p>
 *  JDBC 3.0规范添加了maxStatements <code> ConnectionPooledDataSource </code>属性,以提供用于启用<code> PreparedStateme
 * nts </code>的池化并指定语句池大小的标准机制。
 * 然而,当<code> PreparedStatement </code>变得无效时,驱动程序无法通知外部语句池。对于某些数据库,如果执行影响表的DDL操作,语句将无效。
 * 例如,应用程序可以创建一个临时表以对表进行一些工作,然后销毁它。它可能在以后再次需要时重新创建相同的表。在删除表时,某些数据库将使引用临时表的任何预准备语句无效。
 * <p>
 *  与<code> ConnectionEventListener </code>接口中定义的方法类似,驱动程序会在检测到语句无效时抛出任何异常之前调用<code> StatementEventListe
 * ner.statementErrorOccurred </code>方法。
 * 当<code> PreparedStatement </code>关闭时,驱动程序也将调用<code> StatementEventListener.statementClosed </code>方法。
 * <p>
 * 
 * @since 1.6
 */
public interface StatementEventListener  extends java.util.EventListener{
  /**
   * The driver calls this method on all <code>StatementEventListener</code>s registered on the connection when it detects that a
   * <code>PreparedStatement</code> is closed.
   *
   * <p>
   * 允许组件向<code> PooledConnection </code>中注册StatementEventListener的方法已添加到<code> PooledConnection </code>接口
   * 中。
   * <p>
   * 
   * @param event an event object describing the source of
   * the event and that the <code>PreparedStatement</code> was closed.
   * @since 1.6
   */
  void statementClosed(StatementEvent event);

        /**
         * The driver calls this method on all <code>StatementEventListener</code>s
         * registered on the connection when it detects that a
         * <code>PreparedStatement</code> is invalid. The driver calls this method
         * just before it throws the <code>SQLException</code>,
         * contained in the given event, to the application.
         * <p>
         * <p>
         *  当检测到<code> PreparedStatement </code>关闭时,驱动程序在连接上注册的所有<code> StatementEventListener </code>上调用此方法。
         * 
         * 
         * @param event         an event object describing the source of the event,
         *                                      the statement that is invalid and the exception the
         *                                      driver is about to throw.  The source of the event is
         *                                      the <code>PooledConnection</code> which the invalid <code>PreparedStatement</code>
         * is associated with.
         * <p>
         * @since 1.6
         */
        void statementErrorOccurred(StatementEvent event);

}
