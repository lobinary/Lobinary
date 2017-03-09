/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

/**
 * The AccessibleTableModelChange interface describes a change to
 * the table model.  The attributes of the model change can be
 * obtained by the following methods:
 * <ul>
 * <li> public int getType()
 * <li> public int getFirstRow();
 * <li> public int getLastRow();
 * <li> public int getFirstColumn();
 * <li> public int getLastColumn();
 * </ul>
 * The model change type returned by getType() will be one of:
 * <ul>
 * <li> INSERT - one or more rows and/or columns have been inserted
 * <li> UPDATE - some of the table data has changed
 * <li> DELETE - one or more rows and/or columns have been deleted
 * </ul>
 * The affected area of the table can be determined by the other
 * four methods which specify ranges of rows and columns
 *
 * <p>
 *  AccessibleTableModelChange接口描述对表模型的更改。模型变化的属性可以通过以下方法获得：
 * <ul>
 *  <li> public int getType()<li> public int getFirstRow(); <li> public int getLastRow(); <li> public in
 * t getFirstColumn(); <li> public int getLastColumn();。
 * </ul>
 *  getType()返回的模型更改类型将是以下之一：
 * <ul>
 *  <li> INSERT  - 已插入一个或多个行和/或列<li> UPDATE  - 某些表数据已更改<li> DELETE  - 一个或多个行和/或列已删除
 * </ul>
 *  表的受影响区域可以由指定行和列的范围的其他四个方法确定
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleContext#getAccessibleTable
 *
 * @author      Lynn Monsanto
 * @since 1.3
 */
public interface AccessibleTableModelChange {

    /**
     * Identifies the insertion of new rows and/or columns.
     * <p>
     *  标识新行和/或列的插入。
     * 
     */
    public static final int INSERT =  1;

    /**
     * Identifies a change to existing data.
     * <p>
     *  标识对现有数据的更改。
     * 
     */
    public static final int UPDATE =  0;

    /**
     * Identifies the deletion of rows and/or columns.
     * <p>
     *  标识删除行和/或列。
     * 
     */
    public static final int DELETE = -1;

    /**
     *  Returns the type of event.
     * <p>
     *  返回事件的类型。
     * 
     * 
     *  @return the type of event
     *  @see #INSERT
     *  @see #UPDATE
     *  @see #DELETE
     */
    public int getType();

    /**
     * Returns the first row that changed.
     * <p>
     *  返回已更改的第一行。
     * 
     * 
     * @return the first row that changed
     */
    public int getFirstRow();

    /**
     * Returns the last row that changed.
     * <p>
     *  返回更改的最后一行。
     * 
     * 
     * @return the last row that changed
     */
    public int getLastRow();

    /**
     * Returns the first column that changed.
     * <p>
     *  返回已更改的第一列。
     * 
     * 
     * @return the first column that changed
     */
    public int getFirstColumn();

    /**
     * Returns the last column that changed.
     * <p>
     *  返回更改的最后一列。
     * 
     * @return the last column that changed
     */
    public int getLastColumn();
}
