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
 *  The anchor element. See the  A element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  锚元素。请参阅HTML 4.0中的元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLAnchorElement extends HTMLElement {
    /**
     *  A single character access key to give access to the form control. See
     * the  accesskey attribute definition in HTML 4.0.
     * <p>
     *  单个字符访问键,用于访问表单控件。请参阅HTML 4.0中的accesskey属性定义。
     * 
     */
    public String getAccessKey();
    public void setAccessKey(String accessKey);

    /**
     *  The character encoding of the linked resource. See the  charset
     * attribute definition in HTML 4.0.
     * <p>
     *  链接资源的字符编码。请参阅HTML 4.0中的charset属性定义。
     * 
     */
    public String getCharset();
    public void setCharset(String charset);

    /**
     *  Comma-separated list of lengths, defining an active region geometry.
     * See also <code>shape</code> for the shape of the region. See the
     * coords attribute definition in HTML 4.0.
     * <p>
     *  以逗号分隔的长度列表,定义活动区域几何。有关区域的形状,请参阅<code> shape </code>。请参阅HTML 4.0中的coords属性定义。
     * 
     */
    public String getCoords();
    public void setCoords(String coords);

    /**
     *  The URI of the linked resource. See the  href attribute definition in
     * HTML 4.0.
     * <p>
     *  链接资源的URI。请参阅HTML 4.0中的href属性定义。
     * 
     */
    public String getHref();
    public void setHref(String href);

    /**
     *  Language code of the linked resource. See the  hreflang attribute
     * definition in HTML 4.0.
     * <p>
     *  链接资源的语言代码。请参阅HTML 4.0中的hreflang属性定义。
     * 
     */
    public String getHreflang();
    public void setHreflang(String hreflang);

    /**
     *  Anchor name. See the  name attribute definition in HTML 4.0.
     * <p>
     *  锚名。请参阅HTML 4.0中的名称属性定义。
     * 
     */
    public String getName();
    public void setName(String name);

    /**
     *  Forward link type. See the  rel attribute definition in HTML 4.0.
     * <p>
     *  前向链路类型。请参阅HTML 4.0中的rel属性定义。
     * 
     */
    public String getRel();
    public void setRel(String rel);

    /**
     *  Reverse link type. See the  rev attribute definition in HTML 4.0.
     * <p>
     * 反向链路类型。请参阅HTML 4.0中的rev属性定义。
     * 
     */
    public String getRev();
    public void setRev(String rev);

    /**
     *  The shape of the active area. The coordinates are given by
     * <code>coords</code> . See the  shape attribute definition in HTML 4.0.
     * <p>
     *  活动区域的形状。坐标由<code> coords </code>给出。请参阅HTML 4.0中的形状属性定义。
     * 
     */
    public String getShape();
    public void setShape(String shape);

    /**
     *  Index that represents the element's position in the tabbing order. See
     * the  tabindex attribute definition in HTML 4.0.
     * <p>
     *  表示元素在制表顺序中的位置的索引。请参阅HTML 4.0中的tabindex属性定义。
     * 
     */
    public int getTabIndex();
    public void setTabIndex(int tabIndex);

    /**
     *  Frame to render the resource in. See the  target attribute definition
     * in HTML 4.0.
     * <p>
     *  呈现资源的框架。请参阅HTML 4.0中的目标属性定义。
     * 
     */
    public String getTarget();
    public void setTarget(String target);

    /**
     *  Advisory content type. See the  type attribute definition in HTML 4.0.
     * <p>
     *  咨询内容类型。请参阅HTML 4.0中的类型属性定义。
     * 
     */
    public String getType();
    public void setType(String type);

    /**
     *  Removes keyboard focus from this element.
     * <p>
     *  从此元素中删除键盘焦点。
     * 
     */
    public void blur();

    /**
     *  Gives keyboard focus to this element.
     * <p>
     *  为此元素提供键盘焦点。
     */
    public void focus();

}
