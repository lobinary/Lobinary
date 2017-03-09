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
 *  An embedded Java applet. See the  APPLET element definition in HTML 4.0.
 * This element is deprecated in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  嵌入式Java小程序。请参阅HTML 4.0中的APPLET元素定义。此元素在HTML 4.0中已弃用。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLAppletElement extends HTMLElement {
    /**
     *  Aligns this object (vertically or horizontally)  with respect to its
     * surrounding text. See the  align attribute definition in HTML 4.0.
     * This attribute is deprecated in HTML 4.0.
     * <p>
     *  将此对象(垂直或水平)与其周围文本对齐。请参阅HTML 4.0中的align属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getAlign();
    public void setAlign(String align);

    /**
     *  Alternate text for user agents not rendering the normal content of
     * this element. See the  alt attribute definition in HTML 4.0. This
     * attribute is deprecated in HTML 4.0.
     * <p>
     *  不呈现此元素的正常内容的用户代理的替代文本。请参阅HTML 4.0中的alt属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getAlt();
    public void setAlt(String alt);

    /**
     *  Comma-separated archive list. See the  archive attribute definition in
     * HTML 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  逗号分隔的归档列表。请参阅HTML 4.0中的归档属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getArchive();
    public void setArchive(String archive);

    /**
     *  Applet class file.  See the  code attribute definition in HTML 4.0.
     * This attribute is deprecated in HTML 4.0.
     * <p>
     *  Applet类文件。请参阅HTML 4.0中的代码属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getCode();
    public void setCode(String code);

    /**
     *  Optional base URI for applet. See the  codebase attribute definition
     * in HTML 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     * applet的可选基本URI。请参阅HTML 4.0中的codebase属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getCodeBase();
    public void setCodeBase(String codeBase);

    /**
     *  Override height. See the  height attribute definition in HTML 4.0.
     * This attribute is deprecated in HTML 4.0.
     * <p>
     *  覆盖高度。请参阅HTML 4.0中的height属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getHeight();
    public void setHeight(String height);

    /**
     *  Horizontal space to the left and right of this image, applet, or
     * object. See the  hspace attribute definition in HTML 4.0. This
     * attribute is deprecated in HTML 4.0.
     * <p>
     *  此图像,小程序或对象左右的水平空间。请参阅HTML 4.0中的hspace属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getHspace();
    public void setHspace(String hspace);

    /**
     *  The name of the applet. See the  name attribute definition in HTML
     * 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  小程序的名称。请参阅HTML 4.0中的名称属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getName();
    public void setName(String name);

    /**
     *  Serialized applet file. See the  object attribute definition in HTML
     * 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  序列化applet文件。请参阅HTML 4.0中的对象属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getObject();
    public void setObject(String object);

    /**
     *  Vertical space above and below this image, applet, or object. See the
     * vspace attribute definition in HTML 4.0. This attribute is deprecated
     * in HTML 4.0.
     * <p>
     *  该图像上方和下方的垂直空间,小程序或对象。请参阅HTML 4.0中的vspace属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getVspace();
    public void setVspace(String vspace);

    /**
     *  Override width. See the  width attribute definition in HTML 4.0. This
     * attribute is deprecated in HTML 4.0.
     * <p>
     *  覆盖宽度。请参阅HTML 4.0中的width属性定义。此属性在HTML 4.0中已弃用。
     */
    public String getWidth();
    public void setWidth(String width);

}
