/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

import java.sql.SQLException;

/**
 * An interface that defines the implementation of a factory that is used
 * to obtain different types of {@code RowSet} implementations.
 *
 * <p>
 *  定义用于获取不同类型的{@code RowSet}实现的工厂实现的接口。
 * 
 * 
 * @author Lance Andersen
 * @since 1.7
 */
public interface RowSetFactory{

    /**
     * <p>Creates a new instance of a CachedRowSet.</p>
     *
     * <p>
     *  <p>创建CachedRowSet的新实例。</p>
     * 
     * 
     * @return A new instance of a CachedRowSet.
     *
     * @throws SQLException if a CachedRowSet cannot
     *   be created.
     *
     * @since 1.7
     */
    public CachedRowSet createCachedRowSet() throws SQLException;

    /**
     * <p>Creates a new instance of a FilteredRowSet.</p>
     *
     * <p>
     *  <p>创建FilteredRowSet的新实例。</p>
     * 
     * 
     * @return A new instance of a FilteredRowSet.
     *
     * @throws SQLException if a FilteredRowSet cannot
     *   be created.
     *
     * @since 1.7
     */
    public FilteredRowSet createFilteredRowSet() throws SQLException;

    /**
     * <p>Creates a new instance of a JdbcRowSet.</p>
     *
     * <p>
     *  <p>创建JdbcRowSet的新实例。</p>
     * 
     * 
     * @return A new instance of a JdbcRowSet.
     *
     * @throws SQLException if a JdbcRowSet cannot
     *   be created.
     *
     * @since 1.7
     */
    public  JdbcRowSet createJdbcRowSet() throws SQLException;

    /**
     * <p>Creates a new instance of a JoinRowSet.</p>
     *
     * <p>
     *  <p>创建JoinRowSet的新实例。</p>
     * 
     * 
     * @return A new instance of a JoinRowSet.
     *
     * @throws SQLException if a JoinRowSet cannot
     *   be created.
     *
     * @since 1.7
     */
    public  JoinRowSet createJoinRowSet() throws SQLException;

    /**
     * <p>Creates a new instance of a WebRowSet.</p>
     *
     * <p>
     *  <p>创建WebRowSet的新实例。</p>
     * 
     * @return A new instance of a WebRowSet.
     *
     * @throws SQLException if a WebRowSet cannot
     *   be created.
     *
     * @since 1.7
     */
    public  WebRowSet createWebRowSet() throws SQLException;

}
