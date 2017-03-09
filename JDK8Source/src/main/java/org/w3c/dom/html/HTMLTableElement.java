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
 *  The create* and delete* methods on the table allow authors to construct
 * and modify tables. HTML 4.0 specifies that only one of each of the
 * <code>CAPTION</code> , <code>THEAD</code> , and <code>TFOOT</code>
 * elements may exist in a table. Therefore, if one exists, and the
 * createTHead() or createTFoot() method is called, the method returns the
 * existing THead or TFoot element. See the  TABLE element definition in HTML
 * 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  表上的create *和delete *方法允许作者构造和修改表。
 *  HTML 4.0指定每个<code> CAPTION </code>,<code> THEAD </code>和<code> TFOOT </code>元素中只有一个可能存在于表中。
 * 因此,如果存在,并且调用createTHead()或createTFoot()方法,该方法将返回现有的THead或TFoot元素。请参阅HTML 4.0中的TABLE元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLTableElement extends HTMLElement {
    /**
     *  Returns the table's <code>CAPTION</code> , or void if none exists.
     * <p>
     *  返回表的<code> CAPTION </code>,如果没有则返回void。
     * 
     */
    public HTMLTableCaptionElement getCaption();
    public void setCaption(HTMLTableCaptionElement caption);

    /**
     *  Returns the table's <code>THEAD</code> , or <code>null</code> if none
     * exists.
     * <p>
     *  返回表的<code> THEAD </code>或<code> null </code>(如果不存在)。
     * 
     */
    public HTMLTableSectionElement getTHead();
    public void setTHead(HTMLTableSectionElement tHead);

    /**
     *  Returns the table's <code>TFOOT</code> , or <code>null</code> if none
     * exists.
     * <p>
     *  返回表的<code> TFOOT </code>或<code> null </code>(如果不存在)。
     * 
     */
    public HTMLTableSectionElement getTFoot();
    public void setTFoot(HTMLTableSectionElement tFoot);

    /**
     *  Returns a collection of all the rows in the table, including all in
     * <code>THEAD</code> , <code>TFOOT</code> , all <code>TBODY</code>
     * elements.
     * <p>
     * 返回表中所有行的集合,包括<code> THEAD </code>,<code> TFOOT </code>,<code> TBODY </code>元素中的所有行。
     * 
     */
    public HTMLCollection getRows();

    /**
     *  Returns a collection of the defined table bodies.
     * <p>
     *  返回定义的表体的集合。
     * 
     */
    public HTMLCollection getTBodies();

    /**
     *  Specifies the table's position with respect to the rest of the
     * document. See the  align attribute definition in HTML 4.0. This
     * attribute is deprecated in HTML 4.0.
     * <p>
     *  指定表相对于文档其余部分的位置。请参阅HTML 4.0中的align属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getAlign();
    public void setAlign(String align);

    /**
     *  Cell background color. See the  bgcolor attribute definition in HTML
     * 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  细胞背景颜色。请参阅HTML 4.0中的bgcolor属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getBgColor();
    public void setBgColor(String bgColor);

    /**
     *  The width of the border around the table. See the  border attribute
     * definition in HTML 4.0.
     * <p>
     *  表格周围的边框宽度。请参阅HTML 4.0中的边框属性定义。
     * 
     */
    public String getBorder();
    public void setBorder(String border);

    /**
     *  Specifies the horizontal and vertical space between cell content and
     * cell borders. See the  cellpadding attribute definition in HTML 4.0.
     * <p>
     *  指定单元格内容和单元格边框之间的水平和垂直间距。请参阅HTML 4.0中的cellpadding属性定义。
     * 
     */
    public String getCellPadding();
    public void setCellPadding(String cellPadding);

    /**
     *  Specifies the horizontal and vertical separation between cells. See
     * the  cellspacing attribute definition in HTML 4.0.
     * <p>
     *  指定单元格之间的水平和垂直分隔。请参阅HTML 4.0中的单元格间距属性定义。
     * 
     */
    public String getCellSpacing();
    public void setCellSpacing(String cellSpacing);

    /**
     *  Specifies which external table borders to render. See the  frame
     * attribute definition in HTML 4.0.
     * <p>
     *  指定要呈现的外部表边框。请参阅HTML 4.0中的框架属性定义。
     * 
     */
    public String getFrame();
    public void setFrame(String frame);

    /**
     *  Specifies which internal table borders to render. See the  rules
     * attribute definition in HTML 4.0.
     * <p>
     *  指定要呈现的内部表边框。请参阅HTML 4.0中的规则属性定义。
     * 
     */
    public String getRules();
    public void setRules(String rules);

    /**
     *  Description about the purpose or structure of a table. See the
     * summary attribute definition in HTML 4.0.
     * <p>
     *  关于表的目的或结构的说明。请参阅HTML 4.0中的摘要属性定义。
     * 
     */
    public String getSummary();
    public void setSummary(String summary);

    /**
     *  Specifies the desired table width. See the  width attribute definition
     * in HTML 4.0.
     * <p>
     *  指定所需的表格宽度。请参阅HTML 4.0中的width属性定义。
     * 
     */
    public String getWidth();
    public void setWidth(String width);

    /**
     *  Create a table header row or return an existing one.
     * <p>
     *  创建表标题行或返回一个现有的表标题行。
     * 
     * 
     * @return  A new table header element (<code>THEAD</code> ).
     */
    public HTMLElement createTHead();

    /**
     *  Delete the header from the table, if one exists.
     * <p>
     *  从表中删除标头(如果存在)。
     * 
     */
    public void deleteTHead();

    /**
     *  Create a table footer row or return an existing one.
     * <p>
     *  创建表页脚行或返回现有的一行。
     * 
     * 
     * @return  A footer element (<code>TFOOT</code> ).
     */
    public HTMLElement createTFoot();

    /**
     *  Delete the footer from the table, if one exists.
     * <p>
     *  从表中删除页脚(如果存在)。
     * 
     */
    public void deleteTFoot();

    /**
     *  Create a new table caption object or return an existing one.
     * <p>
     *  创建一个新的表标题对象或返回一个现有的。
     * 
     * 
     * @return  A <code>CAPTION</code> element.
     */
    public HTMLElement createCaption();

    /**
     *  Delete the table caption, if one exists.
     * <p>
     * 删除表标题(如果存在)。
     * 
     */
    public void deleteCaption();

    /**
     *  Insert a new empty row in the table. The new row is inserted
     * immediately before and in the same section as the current
     * <code>index</code> th row in the table. If <code>index</code> is equal
     * to the number of rows, the new row is appended. In addition, when the
     * table is empty the row is inserted into a <code>TBODY</code> which is
     * created and inserted into the table. Note. A table row cannot be empty
     * according to HTML 4.0 Recommendation.
     * <p>
     *  在表中插入新的空行。新行紧接在表中当前<code> index </code> th行之前和之前插入。如果<code> index </code>等于行数,则会追加新行。
     * 另外,当表为空时,将该行插入创建并插入到表中的<code> TBODY </code>中。注意。根据HTML 4.0建议,表行不能为空。
     * 
     * 
     * @param index  The row number where to insert a new row. This index
     *   starts from 0 and is relative to all the rows contained inside the
     *   table, regardless of section parentage.
     * @return  The newly created row.
     * @exception DOMException
     *    INDEX_SIZE_ERR: Raised if the specified index is greater than the
     *   number of rows or if the index is negative.
     */
    public HTMLElement insertRow(int index)
                                 throws DOMException;

    /**
     *  Delete a table row.
     * <p>
     * 
     * @param index  The index of the row to be deleted. This index starts
     *   from 0 and is relative to all the rows contained inside the table,
     *   regardless of section parentage.
     * @exception DOMException
     *    INDEX_SIZE_ERR: Raised if the specified index is greater than or
     *   equal to the number of rows or if the index is negative.
     */
    public void deleteRow(int index)
                          throws DOMException;

}
