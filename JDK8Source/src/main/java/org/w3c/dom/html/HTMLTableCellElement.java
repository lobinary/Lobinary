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

/**
 *  The object used to represent the <code>TH</code> and <code>TD</code>
 * elements. See the  TD element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  用于表示<code> TH </code>和<code> TD </code>元素的对象。请参阅HTML 4.0中的TD元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLTableCellElement extends HTMLElement {
    /**
     *  The index of this cell in the row, starting from 0. This index is in
     * document tree order and not display order.
     * <p>
     *  行中此单元格的索引,从0开始。此索引以文档树顺序而不是显示顺序。
     * 
     */
    public int getCellIndex();

    /**
     *  Abbreviation for header cells. See the  abbr attribute definition in
     * HTML 4.0.
     * <p>
     *  标题单元格的缩写。请参阅HTML 4.0中的abbr属性定义。
     * 
     */
    public String getAbbr();
    public void setAbbr(String abbr);

    /**
     *  Horizontal alignment of data in cell. See the  align attribute
     * definition in HTML 4.0.
     * <p>
     *  单元格中数据的水平对齐。请参阅HTML 4.0中的align属性定义。
     * 
     */
    public String getAlign();
    public void setAlign(String align);

    /**
     *  Names group of related headers. See the  axis attribute definition in
     * HTML 4.0.
     * <p>
     *  命名相关标题组。请参阅HTML 4.0中的轴属性定义。
     * 
     */
    public String getAxis();
    public void setAxis(String axis);

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
     *  Alignment character for cells in a column. See the  char attribute
     * definition in HTML 4.0.
     * <p>
     *  列中单元格的对齐字符。请参阅HTML 4.0中的char属性定义。
     * 
     */
    public String getCh();
    public void setCh(String ch);

    /**
     *  Offset of alignment character. See the  charoff attribute definition
     * in HTML 4.0.
     * <p>
     * 对齐字符的偏移量。请参阅HTML 4.0中的charoff属性定义。
     * 
     */
    public String getChOff();
    public void setChOff(String chOff);

    /**
     *  Number of columns spanned by cell. See the  colspan attribute
     * definition in HTML 4.0.
     * <p>
     *  单元格跨越的列数。请参阅HTML 4.0中的colspan属性定义。
     * 
     */
    public int getColSpan();
    public void setColSpan(int colSpan);

    /**
     *  List of <code>id</code> attribute values for header cells. See the
     * headers attribute definition in HTML 4.0.
     * <p>
     *  标头单元的<code> id </code>属性值列表。请参阅HTML 4.0中的headers属性定义。
     * 
     */
    public String getHeaders();
    public void setHeaders(String headers);

    /**
     *  Cell height. See the  height attribute definition in HTML 4.0. This
     * attribute is deprecated in HTML 4.0.
     * <p>
     *  单元格高度。请参阅HTML 4.0中的height属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getHeight();
    public void setHeight(String height);

    /**
     *  Suppress word wrapping. See the  nowrap attribute definition in HTML
     * 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  抑制字包装。请参阅HTML 4.0中的nowrap属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public boolean getNoWrap();
    public void setNoWrap(boolean noWrap);

    /**
     *  Number of rows spanned by cell. See the  rowspan attribute definition
     * in HTML 4.0.
     * <p>
     *  单元格跨越的行数。请参阅HTML 4.0中的rowspan属性定义。
     * 
     */
    public int getRowSpan();
    public void setRowSpan(int rowSpan);

    /**
     *  Scope covered by header cells. See the  scope attribute definition in
     * HTML 4.0.
     * <p>
     *  范围由标题单元格覆盖。请参阅HTML 4.0中的范围属性定义。
     * 
     */
    public String getScope();
    public void setScope(String scope);

    /**
     *  Vertical alignment of data in cell. See the  valign attribute
     * definition in HTML 4.0.
     * <p>
     *  单元格中数据的垂直对齐。请参阅HTML 4.0中的valign属性定义。
     * 
     */
    public String getVAlign();
    public void setVAlign(String vAlign);

    /**
     *  Cell width. See the  width attribute definition in HTML 4.0. This
     * attribute is deprecated in HTML 4.0.
     * <p>
     *  单元格宽度。请参阅HTML 4.0中的width属性定义。此属性在HTML 4.0中已弃用。
     */
    public String getWidth();
    public void setWidth(String width);

}
