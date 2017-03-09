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

package java.sql;

import java.util.*;

/**
 * Enumeration for RowId life-time values.
 *
 * <p>
 *  RowId生命时值的枚举。
 * 
 * 
 * @since 1.6
 */

public enum RowIdLifetime {

    /**
     * Indicates that this data source does not support the ROWID type.
     * <p>
     *  表示此数据源不支持ROWID类型。
     * 
     */
    ROWID_UNSUPPORTED,

    /**
     * Indicates that the lifetime of a RowId from this data source is indeterminate;
     * but not one of ROWID_VALID_TRANSACTION, ROWID_VALID_SESSION, or,
     * ROWID_VALID_FOREVER.
     * <p>
     *  表示此数据源的RowId的生存期是不确定的;但不是ROWID_VALID_TRANSACTION,ROWID_VALID_SESSION或ROWID_VALID_FOREVER中的一个。
     * 
     */
    ROWID_VALID_OTHER,

    /**
     * Indicates that the lifetime of a RowId from this data source is at least the
     * containing session.
     * <p>
     *  表示此数据源的RowId的生存期至少为包含会话。
     * 
     */
    ROWID_VALID_SESSION,

    /**
     * Indicates that the lifetime of a RowId from this data source is at least the
     * containing transaction.
     * <p>
     *  指示此数据源的RowId的生存期至少为包含事务。
     * 
     */
    ROWID_VALID_TRANSACTION,

    /**
     * Indicates that the lifetime of a RowId from this data source is, effectively,
     * unlimited.
     * <p>
     *  表示此数据源的RowId的生命周期实际上是无限制的。
     */
    ROWID_VALID_FOREVER
}
