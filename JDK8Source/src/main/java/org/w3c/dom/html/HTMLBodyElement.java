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
 *  The HTML document body. This element is always present in the DOM API,
 * even if the tags are not present in the source document. See the  BODY
 * element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  HTML文档正文。即使标记不存在于源文档中,此元素也始终显示在DOM API中。请参阅HTML 4.0中的BODY元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLBodyElement extends HTMLElement {
    /**
     *  Color of active links (after mouse-button down, but before
     * mouse-button up). See the  alink attribute definition in HTML 4.0.
     * This attribute is deprecated in HTML 4.0.
     * <p>
     *  活动链接的颜色(在鼠标按钮关闭后,但在鼠标按钮向上之前)。请参阅HTML 4.0中的alink属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getALink();
    public void setALink(String aLink);

    /**
     *  URI of the background texture tile image. See the  background
     * attribute definition in HTML 4.0. This attribute is deprecated in HTML
     * 4.0.
     * <p>
     *  背景纹理瓦片图象的URI。请参阅HTML 4.0中的背景属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getBackground();
    public void setBackground(String background);

    /**
     *  Document background color. See the  bgcolor attribute definition in
     * HTML 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  文档背景颜色。请参阅HTML 4.0中的bgcolor属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getBgColor();
    public void setBgColor(String bgColor);

    /**
     *  Color of links that are not active and unvisited. See the  link
     * attribute definition in HTML 4.0. This attribute is deprecated in HTML
     * 4.0.
     * <p>
     *  未激活和未访问的链接的颜色。请参阅HTML 4.0中的链接属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getLink();
    public void setLink(String link);

    /**
     *  Document text color. See the  text attribute definition in HTML 4.0.
     * This attribute is deprecated in HTML 4.0.
     * <p>
     * 文档文本颜色。请参阅HTML 4.0中的文本属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getText();
    public void setText(String text);

    /**
     *  Color of links that have been visited by the user. See the  vlink
     * attribute definition in HTML 4.0. This attribute is deprecated in HTML
     * 4.0.
     * <p>
     *  用户访问的链接的颜色。请参阅HTML 4.0中的vlink属性定义。此属性在HTML 4.0中已弃用。
     */
    public String getVLink();
    public void setVLink(String vLink);

}
