/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.table;

import java.util.Enumeration;
import javax.swing.event.ChangeEvent;
import javax.swing.event.*;
import javax.swing.*;


/**
 * Defines the requirements for a table column model object suitable for
 * use with <code>JTable</code>.
 *
 * <p>
 *  定义适用于<code> JTable </code>的表列模型对象的要求。
 * 
 * 
 * @author Alan Chung
 * @author Philip Milne
 * @see DefaultTableColumnModel
 */
public interface TableColumnModel
{
//
// Modifying the model
//

    /**
     *  Appends <code>aColumn</code> to the end of the
     *  <code>tableColumns</code> array.
     *  This method posts a <code>columnAdded</code>
     *  event to its listeners.
     *
     * <p>
     *  将<code> aColumn </code>附加到<code> tableColumns </code>数组的末尾。此方法向其侦听器发出<code> columnAdded </code>事件。
     * 
     * 
     * @param   aColumn         the <code>TableColumn</code> to be added
     * @see     #removeColumn
     */
    public void addColumn(TableColumn aColumn);

    /**
     *  Deletes the <code>TableColumn</code> <code>column</code> from the
     *  <code>tableColumns</code> array.  This method will do nothing if
     *  <code>column</code> is not in the table's column list.
     *  This method posts a <code>columnRemoved</code>
     *  event to its listeners.
     *
     * <p>
     *  从<code> tableColumns </code>数组中删除<code> TableColumn </code> <code>列</code>如果<code> column </code>不在表
     * 的列表中,此方法将不执行任何操作。
     * 此方法将<code> columnRemoved </code>事件发布到其侦听器。
     * 
     * 
     * @param   column          the <code>TableColumn</code> to be removed
     * @see     #addColumn
     */
    public void removeColumn(TableColumn column);

    /**
     * Moves the column and its header at <code>columnIndex</code> to
     * <code>newIndex</code>.  The old column at <code>columnIndex</code>
     * will now be found at <code>newIndex</code>.  The column that used
     * to be at <code>newIndex</code> is shifted left or right
     * to make room.  This will not move any columns if
     * <code>columnIndex</code> equals <code>newIndex</code>.  This method
     * posts a <code>columnMoved</code> event to its listeners.
     *
     * <p>
     *  将列及其标头在<code> columnIndex </code>移动到<code> newIndex </code>。
     *  <code> columnIndex </code>上的旧列现在位于<code> newIndex </code>。
     * 以前在<code> newIndex </code>的列向左或向右移动,以腾出空间。
     * 如果<code> columnIndex </code>等于<code> newIndex </code>,则不会移动任何列。
     * 此方法向其侦听器发出<code> columnMoved </code>事件。
     * 
     * 
     * @param   columnIndex                     the index of column to be moved
     * @param   newIndex                        index of the column's new location
     * @exception IllegalArgumentException      if <code>columnIndex</code> or
     *                                          <code>newIndex</code>
     *                                          are not in the valid range
     */
    public void moveColumn(int columnIndex, int newIndex);

    /**
     * Sets the <code>TableColumn</code>'s column margin to
     * <code>newMargin</code>.  This method posts
     * a <code>columnMarginChanged</code> event to its listeners.
     *
     * <p>
     *  将<code> TableColumn </code>的列边距设置为<code> newMargin </code>。
     * 此方法向其侦听器发出<code> columnMarginChanged </code>事件。
     * 
     * 
     * @param   newMargin       the width, in pixels, of the new column margins
     * @see     #getColumnMargin
     */
    public void setColumnMargin(int newMargin);

//
// Querying the model
//

    /**
     * Returns the number of columns in the model.
     * <p>
     *  返回模型中的列数。
     * 
     * 
     * @return the number of columns in the model
     */
    public int getColumnCount();

    /**
     * Returns an <code>Enumeration</code> of all the columns in the model.
     * <p>
     *  返回模型中所有列的<code>枚举</code>。
     * 
     * 
     * @return an <code>Enumeration</code> of all the columns in the model
     */
    public Enumeration<TableColumn> getColumns();

    /**
     * Returns the index of the first column in the table
     * whose identifier is equal to <code>identifier</code>,
     * when compared using <code>equals</code>.
     *
     * <p>
     *  当使用<code> equals </code>进行比较时,返回其标识符等于<code> identifier </code>的表中第一列的索引。
     * 
     * 
     * @param           columnIdentifier        the identifier object
     * @return          the index of the first table column
     *                  whose identifier is equal to <code>identifier</code>
     * @exception IllegalArgumentException      if <code>identifier</code>
     *                          is <code>null</code>, or no
     *                          <code>TableColumn</code> has this
     *                          <code>identifier</code>
     * @see             #getColumn
     */
    public int getColumnIndex(Object columnIdentifier);

    /**
     * Returns the <code>TableColumn</code> object for the column at
     * <code>columnIndex</code>.
     *
     * <p>
     * 返回<code> columnIndex </code>中列的<code> TableColumn </code>对象。
     * 
     * 
     * @param   columnIndex     the index of the desired column
     * @return  the <code>TableColumn</code> object for
     *                          the column at <code>columnIndex</code>
     */
    public TableColumn getColumn(int columnIndex);

    /**
     * Returns the width between the cells in each column.
     * <p>
     *  返回每列中单元格之间的宽度。
     * 
     * 
     * @return the margin, in pixels, between the cells
     */
    public int getColumnMargin();

    /**
     * Returns the index of the column that lies on the
     * horizontal point, <code>xPosition</code>;
     * or -1 if it lies outside the any of the column's bounds.
     *
     * In keeping with Swing's separable model architecture, a
     * TableColumnModel does not know how the table columns actually appear on
     * screen.  The visual presentation of the columns is the responsibility
     * of the view/controller object using this model (typically JTable).  The
     * view/controller need not display the columns sequentially from left to
     * right.  For example, columns could be displayed from right to left to
     * accommodate a locale preference or some columns might be hidden at the
     * request of the user.  Because the model does not know how the columns
     * are laid out on screen, the given <code>xPosition</code> should not be
     * considered to be a coordinate in 2D graphics space.  Instead, it should
     * be considered to be a width from the start of the first column in the
     * model.  If the column index for a given X coordinate in 2D space is
     * required, <code>JTable.columnAtPoint</code> can be used instead.
     *
     * <p>
     *  返回位于水平点上的列的索引,<code> xPosition </code>;或-1,如果它位于任何列的边界之外。
     * 
     *  为了与Swing的可分离模型架构保持一致,TableColumnModel不知道表列实际上如何显示在屏幕上。列的可视化呈现是使用此模型(通常为JTable)的视图/控制器对象的责任。
     * 视图/控制器不需要从左到右顺序地显示列。例如,可以从右到左显示列,以适应区域设置首选项,或者可以根据用户的请求隐藏某些列。
     * 因为模型不知道列如何在屏幕上布局,给定的<code> xPosition </code>不应该被认为是2D图形空间中的坐标。相反,它应该被认为是从模型中第一列开始的宽度。
     * 如果需要2D空间中给定X坐标的列索引,则可以使用<code> JTable.columnAtPoint </code>。
     * 
     * 
     * @return  the index of the column; or -1 if no column is found
     * @see javax.swing.JTable#columnAtPoint
     */
    public int getColumnIndexAtX(int xPosition);

    /**
     * Returns the total width of all the columns.
     * <p>
     *  返回所有列的总宽度。
     * 
     * 
     * @return the total computed width of all columns
     */
    public int getTotalColumnWidth();

//
// Selection
//

    /**
     * Sets whether the columns in this model may be selected.
     * <p>
     *  设置是否可以选择此模型中的列。
     * 
     * 
     * @param flag   true if columns may be selected; otherwise false
     * @see #getColumnSelectionAllowed
     */
    public void setColumnSelectionAllowed(boolean flag);

    /**
     * Returns true if columns may be selected.
     * <p>
     *  如果可以选择列,则返回true。
     * 
     * 
     * @return true if columns may be selected
     * @see #setColumnSelectionAllowed
     */
    public boolean getColumnSelectionAllowed();

    /**
     * Returns an array of indicies of all selected columns.
     * <p>
     *  返回所有选定列的指数数组。
     * 
     * 
     * @return an array of integers containing the indicies of all
     *          selected columns; or an empty array if nothing is selected
     */
    public int[] getSelectedColumns();

    /**
     * Returns the number of selected columns.
     *
     * <p>
     *  返回所选列的数量。
     * 
     * 
     * @return the number of selected columns; or 0 if no columns are selected
     */
    public int getSelectedColumnCount();

    /**
     * Sets the selection model.
     *
     * <p>
     *  设置选择模型。
     * 
     * 
     * @param newModel  a <code>ListSelectionModel</code> object
     * @see #getSelectionModel
     */
    public void setSelectionModel(ListSelectionModel newModel);

    /**
     * Returns the current selection model.
     *
     * <p>
     *  返回当前选择模型。
     * 
     * 
     * @return a <code>ListSelectionModel</code> object
     * @see #setSelectionModel
     */
    public ListSelectionModel getSelectionModel();

//
// Listener
//

    /**
     * Adds a listener for table column model events.
     *
     * <p>
     * 为表列模型事件添加侦听器。
     * 
     * 
     * @param x  a <code>TableColumnModelListener</code> object
     */
    public void addColumnModelListener(TableColumnModelListener x);

    /**
     * Removes a listener for table column model events.
     *
     * <p>
     *  删除表列模型事件的侦听器。
     * 
     * @param x  a <code>TableColumnModelListener</code> object
     */
    public void removeColumnModelListener(TableColumnModelListener x);
}
