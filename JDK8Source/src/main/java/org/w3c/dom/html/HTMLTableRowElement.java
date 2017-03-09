/***** Lobxxx Translate Finished ******/
/*
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
 *
 *
 *
 *
 *
 * Copyright (c) 2000 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See W3C License http://www.w3.org/Consortium/Legal/ for more
 * details.
 * <p>
 *  版权所有(c)2000万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.html;

import org.w3c.dom.DOMException;

/**
 *  A row in a table. See the  TR element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  表中的一行。请参阅HTML 4.0中的TR元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLTableRowElement extends HTMLElement {
    /**
     *  The index of this row, relative to the entire table, starting from 0.
     * This is in document tree order and not display order. The
     * <code>rowIndex</code> does not take into account sections (
     * <code>THEAD</code> , <code>TFOOT</code> , or <code>TBODY</code> )
     * within the table.
     * <p>
     *  此行的索引,相对于整个表,从0开始。这是以文档树顺序而不是显示顺序。
     *  <code> rowIndex </code>不会考虑表中的部分(<code> THEAD </code>,<code> TFOOT </code>或<code> TBODY </code>)。
     * 
     */
    public int getRowIndex();

    /**
     *  The index of this row, relative to the current section (
     * <code>THEAD</code> , <code>TFOOT</code> , or <code>TBODY</code> ),
     * starting from 0.
     * <p>
     *  此行的索引,相对于从0开始的当前部分(<code> THEAD </code>,<code> TFOOT </code>或<code> TBODY </code>)。
     * 
     */
    public int getSectionRowIndex();

    /**
     *  The collection of cells in this row.
     * <p>
     *  此行中的单元格的集合。
     * 
     */
    public HTMLCollection getCells();

    /**
     *  Horizontal alignment of data within cells of this row. See the  align
     * attribute definition in HTML 4.0.
     * <p>
     *  此行的单元格内的数据的水平对齐。请参阅HTML 4.0中的align属性定义。
     * 
     */
    public String getAlign();
    public void setAlign(String align);

    /**
     *  Background color for rows. See the  bgcolor attribute definition in
     * HTML 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  行的背景颜色。请参阅HTML 4.0中的bgcolor属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getBgColor();
    public void setBgColor(String bgColor);

    /**
     *  Alignment character for cells in a column. See the  char attribute
     * definition in HTML 4.0.
     * <p>
     * 列中单元格的对齐字符。请参阅HTML 4.0中的char属性定义。
     * 
     */
    public String getCh();
    public void setCh(String ch);

    /**
     *  Offset of alignment character. See the  charoff attribute definition
     * in HTML 4.0.
     * <p>
     *  对齐字符的偏移量。请参阅HTML 4.0中的charoff属性定义。
     * 
     */
    public String getChOff();
    public void setChOff(String chOff);

    /**
     *  Vertical alignment of data within cells of this row. See the  valign
     * attribute definition in HTML 4.0.
     * <p>
     *  此行的单元格内的数据的垂直对齐。请参阅HTML 4.0中的valign属性定义。
     * 
     */
    public String getVAlign();
    public void setVAlign(String vAlign);

    /**
     *  Insert an empty <code>TD</code> cell into this row. If
     * <code>index</code> is equal to the number of cells, the new cell is
     * appended
     * <p>
     *  在此行中插入一个空的<code> TD </code>单元格。如果<code> index </code>等于单元格数,则添加新单元格
     * 
     * 
     * @param index  The place to insert the cell, starting from 0.
     * @return  The newly created cell.
     * @exception DOMException
     *    INDEX_SIZE_ERR: Raised if the specified <code>index</code> is
     *   greater than the number of cells or if the index is negative.
     */
    public HTMLElement insertCell(int index)
                                  throws DOMException;

    /**
     *  Delete a cell from the current row.
     * <p>
     *  从当前行中删除单元格。
     * 
     * @param index  The index of the cell to delete, starting from 0.
     * @exception DOMException
     *    INDEX_SIZE_ERR: Raised if the specified <code>index</code> is
     *   greater than or equal to the number of cells or if the index is
     *   negative.
     */
    public void deleteCell(int index)
                           throws DOMException;

}
