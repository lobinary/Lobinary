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

import javax.swing.*;
import javax.swing.event.*;

/**
 *  The <code>TableModel</code> interface specifies the methods the
 *  <code>JTable</code> will use to interrogate a tabular data model. <p>
 *
 *  The <code>JTable</code> can be set up to display any data
 *  model which implements the
 *  <code>TableModel</code> interface with a couple of lines of code:
 *  <pre>
 *      TableModel myData = new MyTableModel();
 *      JTable table = new JTable(myData);
 *  </pre><p>
 *
 * For further documentation, see <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data">Creating a Table Model</a>
 * in <em>The Java Tutorial</em>.
 *
 * <p>
 *  <code> TableModel </code>接口指定<code> JTable </code>将用于查询表格数据模型的方法。 <p>
 * 
 *  可以设置<code> JTable </code>以显示实现<code> TableModel </code>接口的任何数据模型,其中有几行代码：
 * <pre>
 *  TableModel myData = new MyTableModel(); JTable table = new JTable(myData); </pre> <p>
 * 
 *  有关其他文档,请参阅<em>中的<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data">
 * 创建表模型</a>。
 * Java教程</em>。
 * 
 * 
 * @author Philip Milne
 * @see JTable
 */

public interface TableModel
{
    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * <p>
     *  返回模型中的行数。 <code> JTable </code>使用此方法来确定应显示多少行。这种方法应该很快,因为它在渲染过程中经常调用。
     * 
     * 
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    public int getRowCount();

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * <p>
     *  返回模型中的列数。 <code> JTable </code>使用此方法来确定默认情况下应创建和显示的列数。
     * 
     * 
     * @return the number of columns in the model
     * @see #getRowCount
     */
    public int getColumnCount();

    /**
     * Returns the name of the column at <code>columnIndex</code>.  This is used
     * to initialize the table's column header name.  Note: this name does
     * not need to be unique; two columns in a table can have the same name.
     *
     * <p>
     *  返回<code> columnIndex </code>的列名称。这用于初始化表的列标题名称。注意：此名称不需要是唯一的;表中的两列可以具有相同的名称。
     * 
     * 
     * @param   columnIndex     the index of the column
     * @return  the name of the column
     */
    public String getColumnName(int columnIndex);

    /**
     * Returns the most specific superclass for all the cell values
     * in the column.  This is used by the <code>JTable</code> to set up a
     * default renderer and editor for the column.
     *
     * <p>
     *  返回列中所有单元格值的最特定的超类。这由<code> JTable </code>用来为列设置默认渲染器和编辑器。
     * 
     * 
     * @param columnIndex  the index of the column
     * @return the common ancestor class of the object values in the model.
     */
    public Class<?> getColumnClass(int columnIndex);

    /**
     * Returns true if the cell at <code>rowIndex</code> and
     * <code>columnIndex</code>
     * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
     * change the value of that cell.
     *
     * <p>
     * 如果<code> rowIndex </code>和<code> columnIndex </code>中的单元格是可编辑的,则返回true。
     * 否则,单元格上的<code> setValueAt </code>将不会更改该单元格的值。
     * 
     * 
     * @param   rowIndex        the row whose value to be queried
     * @param   columnIndex     the column whose value to be queried
     * @return  true if the cell is editable
     * @see #setValueAt
     */
    public boolean isCellEditable(int rowIndex, int columnIndex);

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * <p>
     *  返回<code> columnIndex </code>和<code> rowIndex </code>中单元格的值。
     * 
     * 
     * @param   rowIndex        the row whose value is to be queried
     * @param   columnIndex     the column whose value is to be queried
     * @return  the value Object at the specified cell
     */
    public Object getValueAt(int rowIndex, int columnIndex);

    /**
     * Sets the value in the cell at <code>columnIndex</code> and
     * <code>rowIndex</code> to <code>aValue</code>.
     *
     * <p>
     *  将<code> columnIndex </code>和<code> rowIndex </code>中的单元格中的值设置为<code> aValue </code>。
     * 
     * 
     * @param   aValue           the new value
     * @param   rowIndex         the row whose value is to be changed
     * @param   columnIndex      the column whose value is to be changed
     * @see #getValueAt
     * @see #isCellEditable
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex);

    /**
     * Adds a listener to the list that is notified each time a change
     * to the data model occurs.
     *
     * <p>
     *  将侦听器添加到每当发生对数据模型的更改时通知的列表中。
     * 
     * 
     * @param   l               the TableModelListener
     */
    public void addTableModelListener(TableModelListener l);

    /**
     * Removes a listener from the list that is notified each time a
     * change to the data model occurs.
     *
     * <p>
     *  从每当发生对数据模型的更改时通知的列表中删除侦听器。
     * 
     * @param   l               the TableModelListener
     */
    public void removeTableModelListener(TableModelListener l);
}
