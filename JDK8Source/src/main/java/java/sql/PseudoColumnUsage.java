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

package java.sql;


/**
 * Enumeration for pseudo/hidden column usage.
 *
 * <p>
 *  伪/隐藏列使用的枚举。
 * 
 * 
 * @since 1.7
 * @see DatabaseMetaData#getPseudoColumns
 */
public enum PseudoColumnUsage {

    /**
     * The pseudo/hidden column may only be used in a SELECT list.
     * <p>
     *  伪/隐藏列只能在SELECT列表中使用。
     * 
     */
    SELECT_LIST_ONLY,

    /**
     * The pseudo/hidden column may only be used in a WHERE clause.
     * <p>
     *  伪/隐藏列只能在WHERE子句中使用。
     * 
     */
    WHERE_CLAUSE_ONLY,

    /**
     * There are no restrictions on the usage of the pseudo/hidden columns.
     * <p>
     *  对伪/隐藏列的使用没有限制。
     * 
     */
    NO_USAGE_RESTRICTIONS,

    /**
     * The usage of the pseudo/hidden column cannot be determined.
     * <p>
     *  无法确定伪/隐藏列的用法。
     */
    USAGE_UNKNOWN

}
