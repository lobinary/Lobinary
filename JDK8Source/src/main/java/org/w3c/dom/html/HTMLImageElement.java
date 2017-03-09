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
 *  Embedded image. See the  IMG element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  嵌入图像。请参阅HTML 4.0中的IMG元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLImageElement extends HTMLElement {
    /**
     *  URI designating the source of this image, for low-resolution output.
     * <p>
     *  URI指定此图像的源,用于低分辨率输出。
     * 
     */
    public String getLowSrc();
    public void setLowSrc(String lowSrc);

    /**
     *  The name of the element (for backwards compatibility).
     * <p>
     *  元素的名称(用于向后兼容性)。
     * 
     */
    public String getName();
    public void setName(String name);

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
     * this element. See the  alt attribute definition in HTML 4.0.
     * <p>
     *  不呈现此元素的正常内容的用户代理的替代文本。请参阅HTML 4.0中的alt属性定义。
     * 
     */
    public String getAlt();
    public void setAlt(String alt);

    /**
     *  Width of border around image. See the  border attribute definition in
     * HTML 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  图像周围的边框宽度。请参阅HTML 4.0中的边框属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getBorder();
    public void setBorder(String border);

    /**
     *  Override height. See the  height attribute definition in HTML 4.0.
     * <p>
     *  覆盖高度。请参阅HTML 4.0中的height属性定义。
     * 
     */
    public String getHeight();
    public void setHeight(String height);

    /**
     *  Horizontal space to the left and right of this image. See the  hspace
     * attribute definition in HTML 4.0. This attribute is deprecated in HTML
     * 4.0.
     * <p>
     * 此图像左侧和右侧的水平空间。请参阅HTML 4.0中的hspace属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getHspace();
    public void setHspace(String hspace);

    /**
     *  Use server-side image map. See the  ismap attribute definition in HTML
     * 4.0.
     * <p>
     *  使用服务器端映像映射。请参阅HTML 4.0中的ismap属性定义。
     * 
     */
    public boolean getIsMap();
    public void setIsMap(boolean isMap);

    /**
     *  URI designating a long description of this image or frame. See the
     * longdesc attribute definition in HTML 4.0.
     * <p>
     *  URI指定此图像或帧的长描述。请参阅HTML 4.0中的longdesc属性定义。
     * 
     */
    public String getLongDesc();
    public void setLongDesc(String longDesc);

    /**
     *  URI designating the source of this image. See the  src attribute
     * definition in HTML 4.0.
     * <p>
     *  URI指定此图像的来源。请参阅HTML 4.0中的src属性定义。
     * 
     */
    public String getSrc();
    public void setSrc(String src);

    /**
     *  Use client-side image map. See the  usemap attribute definition in
     * HTML 4.0.
     * <p>
     *  使用客户端图像映射。请参阅HTML 4.0中的usemap属性定义。
     * 
     */
    public String getUseMap();
    public void setUseMap(String useMap);

    /**
     *  Vertical space above and below this image. See the  vspace attribute
     * definition in HTML 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  此图像上方和下方的垂直空间。请参阅HTML 4.0中的vspace属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getVspace();
    public void setVspace(String vspace);

    /**
     *  Override width. See the  width attribute definition in HTML 4.0.
     * <p>
     *  覆盖宽度。请参阅HTML 4.0中的width属性定义。
     */
    public String getWidth();
    public void setWidth(String width);

}
