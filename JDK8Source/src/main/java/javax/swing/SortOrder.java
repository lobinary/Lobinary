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
package javax.swing;

/**
 * SortOrder is an enumeration of the possible sort orderings.
 *
 * <p>
 *  SortOrder是可能的排序顺序的枚举。
 * 
 * 
 * @see RowSorter
 * @since 1.6
 */
public enum SortOrder {
    /**
     * Enumeration value indicating the items are sorted in increasing order.
     * For example, the set <code>1, 4, 0</code> sorted in
     * <code>ASCENDING</code> order is <code>0, 1, 4</code>.
     * <p>
     *  指示项目的枚举值按递增顺序排序。例如,在<code> ASCENDING </code>顺序中排序的<code> 1,4,0​​ </code>的集合是<code> 0,1,4 </code>。
     * 
     */
    ASCENDING,

    /**
     * Enumeration value indicating the items are sorted in decreasing order.
     * For example, the set <code>1, 4, 0</code> sorted in
     * <code>DESCENDING</code> order is <code>4, 1, 0</code>.
     * <p>
     *  指示项目的枚举值以递减顺序排序。例如,在<code> DESCENDING </code>顺序中排序的集合<code> 1,4,0​​ </code>是<code> 4,1,0 </code>。
     * 
     */
    DESCENDING,

    /**
     * Enumeration value indicating the items are unordered.
     * For example, the set <code>1, 4, 0</code> in
     * <code>UNSORTED</code> order is <code>1, 4, 0</code>.
     * <p>
     *  指示项目的枚举值无序。例如,<code> UNSORTED </code>顺序中的集合<code> 1,4,0​​ </code>是<code> 1,4,0​​ </code>。
     */
    UNSORTED
}
