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
 * Class AccessibleTable describes a user-interface component that
 * presents data in a two-dimensional table format.
 *
 * <p>
 *  Class AccessibleTable描述了以二维表格式呈现数据的用户界面组件。
 * 
 * 
 * @author      Lynn Monsanto
 * @since 1.3
 */
public interface AccessibleTable {

    /**
     * Returns the caption for the table.
     *
     * <p>
     *  返回表的标题。
     * 
     * 
     * @return the caption for the table
     */
    public Accessible getAccessibleCaption();

    /**
     * Sets the caption for the table.
     *
     * <p>
     *  设置表的标题。
     * 
     * 
     * @param a the caption for the table
     */
    public void setAccessibleCaption(Accessible a);

    /**
     * Returns the summary description of the table.
     *
     * <p>
     *  返回表的摘要描述。
     * 
     * 
     * @return the summary description of the table
     */
    public Accessible getAccessibleSummary();

    /**
     * Sets the summary description of the table
     *
     * <p>
     *  设置表的摘要说明
     * 
     * 
     * @param a the summary description of the table
     */
    public void setAccessibleSummary(Accessible a);

    /**
     * Returns the number of rows in the table.
     *
     * <p>
     *  返回表中的行数。
     * 
     * 
     * @return the number of rows in the table
     */
    public int getAccessibleRowCount();

    /**
     * Returns the number of columns in the table.
     *
     * <p>
     *  返回表中的列数。
     * 
     * 
     * @return the number of columns in the table
     */
    public int getAccessibleColumnCount();

    /**
     * Returns the Accessible at a specified row and column
     * in the table.
     *
     * <p>
     *  返回表中指定行和列处的Accessible。
     * 
     * 
     * @param r zero-based row of the table
     * @param c zero-based column of the table
     * @return the Accessible at the specified row and column
     */
    public Accessible getAccessibleAt(int r, int c);

    /**
     * Returns the number of rows occupied by the Accessible at
     * a specified row and column in the table.
     *
     * <p>
     *  返回表中指定行和列处Accessible所占用的行数。
     * 
     * 
     * @param r zero-based row of the table
     * @param c zero-based column of the table
     * @return the number of rows occupied by the Accessible at a
     * given specified (row, column)
     */
    public int getAccessibleRowExtentAt(int r, int c);

    /**
     * Returns the number of columns occupied by the Accessible at
     * a specified row and column in the table.
     *
     * <p>
     *  返回表中指定行和列处Accessible所占用的列数。
     * 
     * 
     * @param r zero-based row of the table
     * @param c zero-based column of the table
     * @return the number of columns occupied by the Accessible at a
     * given specified row and column
     */
    public int getAccessibleColumnExtentAt(int r, int c);

    /**
     * Returns the row headers as an AccessibleTable.
     *
     * <p>
     *  将行标题作为AccessibleTable返回。
     * 
     * 
     * @return an AccessibleTable representing the row
     * headers
     */
    public AccessibleTable getAccessibleRowHeader();

    /**
     * Sets the row headers.
     *
     * <p>
     *  设置行标题。
     * 
     * 
     * @param table an AccessibleTable representing the
     * row headers
     */
    public void setAccessibleRowHeader(AccessibleTable table);

    /**
     * Returns the column headers as an AccessibleTable.
     *
     * <p>
     *  将列标题作为AccessibleTable返回。
     * 
     * 
     * @return an AccessibleTable representing the column
     * headers
     */
    public AccessibleTable getAccessibleColumnHeader();

    /**
     * Sets the column headers.
     *
     * <p>
     *  设置列标题。
     * 
     * 
     * @param table an AccessibleTable representing the
     * column headers
     */
    public void setAccessibleColumnHeader(AccessibleTable table);

    /**
     * Returns the description of the specified row in the table.
     *
     * <p>
     *  返回表中指定行的描述。
     * 
     * 
     * @param r zero-based row of the table
     * @return the description of the row
     */
    public Accessible getAccessibleRowDescription(int r);

    /**
     * Sets the description text of the specified row of the table.
     *
     * <p>
     *  设置表的指定行的描述文本。
     * 
     * 
     * @param r zero-based row of the table
     * @param a the description of the row
     */
    public void setAccessibleRowDescription(int r, Accessible a);

    /**
     * Returns the description text of the specified column in the table.
     *
     * <p>
     *  返回表中指定列的描述文本。
     * 
     * 
     * @param c zero-based column of the table
     * @return the text description of the column
     */
    public Accessible getAccessibleColumnDescription(int c);

    /**
     * Sets the description text of the specified column in the table.
     *
     * <p>
     *  设置表中指定列的描述文本。
     * 
     * 
     * @param c zero-based column of the table
     * @param a the text description of the column
     */
    public void setAccessibleColumnDescription(int c, Accessible a);

    /**
     * Returns a boolean value indicating whether the accessible at
     * a specified row and column is selected.
     *
     * <p>
     *  返回一个布尔值,指示是否选择了指定行和列处的可访问性。
     * 
     * 
     * @param r zero-based row of the table
     * @param c zero-based column of the table
     * @return the boolean value true if the accessible at the
     * row and column is selected. Otherwise, the boolean value
     * false
     */
    public boolean isAccessibleSelected(int r, int c);

    /**
     * Returns a boolean value indicating whether the specified row
     * is selected.
     *
     * <p>
     *  返回一个布尔值,指示是否选择指定的行。
     * 
     * 
     * @param r zero-based row of the table
     * @return the boolean value true if the specified row is selected.
     * Otherwise, false.
     */
    public boolean isAccessibleRowSelected(int r);

    /**
     * Returns a boolean value indicating whether the specified column
     * is selected.
     *
     * <p>
     *  返回一个布尔值,指示是否选择指定的列。
     * 
     * 
     * @param c zero-based column of the table
     * @return the boolean value true if the specified column is selected.
     * Otherwise, false.
     */
    public boolean isAccessibleColumnSelected(int c);

    /**
     * Returns the selected rows in a table.
     *
     * <p>
     *  返回表中选定的行。
     * 
     * 
     * @return an array of selected rows where each element is a
     * zero-based row of the table
     */
    public int [] getSelectedAccessibleRows();

    /**
     * Returns the selected columns in a table.
     *
     * <p>
     * 返回表中所选的列。
     * 
     * @return an array of selected columns where each element is a
     * zero-based column of the table
     */
    public int [] getSelectedAccessibleColumns();
}
