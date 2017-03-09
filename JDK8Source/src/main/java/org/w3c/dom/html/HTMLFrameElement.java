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

import org.w3c.dom.Document;

/**
 *  Create a frame. See the  FRAME element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  创建框架。请参阅HTML 4.0中的FRAME元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLFrameElement extends HTMLElement {
    /**
     *  Request frame borders. See the  frameborder attribute definition in
     * HTML 4.0.
     * <p>
     *  请求帧边框。请参阅HTML 4.0中的frameborder属性定义。
     * 
     */
    public String getFrameBorder();
    public void setFrameBorder(String frameBorder);

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
     *  Frame margin height, in pixels. See the  marginheight attribute
     * definition in HTML 4.0.
     * <p>
     *  帧边距高度,以像素为单位。请参阅HTML 4.0中的marginheight属性定义。
     * 
     */
    public String getMarginHeight();
    public void setMarginHeight(String marginHeight);

    /**
     *  Frame margin width, in pixels. See the  marginwidth attribute
     * definition in HTML 4.0.
     * <p>
     *  帧边距宽度,以像素为单位。请参阅HTML 4.0中的marginwidth属性定义。
     * 
     */
    public String getMarginWidth();
    public void setMarginWidth(String marginWidth);

    /**
     *  The frame name (object of the <code>target</code> attribute). See the
     * name attribute definition in HTML 4.0.
     * <p>
     *  框架名称(<code>目标</code>属性的对象)。请参阅HTML 4.0中的名称属性定义。
     * 
     */
    public String getName();
    public void setName(String name);

    /**
     *  When true, forbid user from resizing frame. See the  noresize
     * attribute definition in HTML 4.0.
     * <p>
     *  为true时,禁止用户调整框架大小。请参阅HTML 4.0中的noresize属性定义。
     * 
     */
    public boolean getNoResize();
    public void setNoResize(boolean noResize);

    /**
     *  Specify whether or not the frame should have scrollbars. See the
     * scrolling attribute definition in HTML 4.0.
     * <p>
     *  指定框架是否应具有滚动条。请参阅HTML 4.0中的滚动属性定义。
     * 
     */
    public String getScrolling();
    public void setScrolling(String scrolling);

    /**
     *  A URI designating the initial frame contents. See the  src attribute
     * definition in HTML 4.0.
     * <p>
     * 指定初始帧内容的URI。请参阅HTML 4.0中的src属性定义。
     * 
     */
    public String getSrc();
    public void setSrc(String src);

    /**
     *  The document this frame contains, if there is any and it is available,
     * or <code>null</code> otherwise.
     * <p>
     *  这个框架包含的文档,如果有任何和它是可用的,否则<code> null </code>。
     * 
     * @since DOM Level 2
     */
    public Document getContentDocument();

}
