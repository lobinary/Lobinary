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

import java.awt.Component;
import javax.swing.*;

/**
 * This interface defines the method required by any object that
 * would like to be a renderer for cells in a <code>JTable</code>.
 *
 * <p>
 *  此接口定义任何对象想要成为<code> JTable </code>中的单元格的渲染器所需的方法。
 * 
 * 
 * @author Alan Chung
 */

public interface TableCellRenderer {

    /**
     * Returns the component used for drawing the cell.  This method is
     * used to configure the renderer appropriately before drawing.
     * <p>
     * The <code>TableCellRenderer</code> is also responsible for rendering the
     * the cell representing the table's current DnD drop location if
     * it has one. If this renderer cares about rendering
     * the DnD drop location, it should query the table directly to
     * see if the given row and column represent the drop location:
     * <pre>
     *     JTable.DropLocation dropLocation = table.getDropLocation();
     *     if (dropLocation != null
     *             &amp;&amp; !dropLocation.isInsertRow()
     *             &amp;&amp; !dropLocation.isInsertColumn()
     *             &amp;&amp; dropLocation.getRow() == row
     *             &amp;&amp; dropLocation.getColumn() == column) {
     *
     *         // this cell represents the current drop location
     *         // so render it specially, perhaps with a different color
     *     }
     * </pre>
     * <p>
     * During a printing operation, this method will be called with
     * <code>isSelected</code> and <code>hasFocus</code> values of
     * <code>false</code> to prevent selection and focus from appearing
     * in the printed output. To do other customization based on whether
     * or not the table is being printed, check the return value from
     * {@link javax.swing.JComponent#isPaintingForPrint()}.
     *
     * <p>
     *  返回用于绘制单元格的组件此方法用于在绘制之前正确配置渲染器
     * <p>
     *  <code> TableCellRenderer </code>也负责渲染表示表的当前DnD删除位置的单元格,如果它有一个如果这个渲染器关心渲染DnD删除位置,它应该直接查询表以查看给定行和列表示删除
     * 位置：。
     * <pre>
     * JTableDropLocation dropLocation = tablegetDropLocation(); if(dropLocation！= null&amp;&amp;！dropLocati
     * onisInsertRow()&amp;&amp;！dropLocationisInsertColumn()&amp;&amp; dropLocationgetRow()== row&amp;&amp;
     *  dropLocationgetColumn()== column){。
     * 
     * @param   table           the <code>JTable</code> that is asking the
     *                          renderer to draw; can be <code>null</code>
     * @param   value           the value of the cell to be rendered.  It is
     *                          up to the specific renderer to interpret
     *                          and draw the value.  For example, if
     *                          <code>value</code>
     *                          is the string "true", it could be rendered as a
     *                          string or it could be rendered as a check
     *                          box that is checked.  <code>null</code> is a
     *                          valid value
     * @param   isSelected      true if the cell is to be rendered with the
     *                          selection highlighted; otherwise false
     * @param   hasFocus        if true, render cell appropriately.  For
     *                          example, put a special border on the cell, if
     *                          the cell can be edited, render in the color used
     *                          to indicate editing
     * @param   row             the row index of the cell being drawn.  When
     *                          drawing the header, the value of
     *                          <code>row</code> is -1
     * @param   column          the column index of the cell being drawn
     * @see javax.swing.JComponent#isPaintingForPrint()
     */
    Component getTableCellRendererComponent(JTable table, Object value,
                                            boolean isSelected, boolean hasFocus,
                                            int row, int column);
}
