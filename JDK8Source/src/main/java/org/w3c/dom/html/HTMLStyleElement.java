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
 *  Style information. See the  STYLE element definition in HTML 4.0, the
 * module and the <code>LinkStyle</code> interface in the  module.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  样式信息。请参阅HTML 4.0中的STYLE元素定义,模块中的模块和<code> LinkStyle </code>接口。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLStyleElement extends HTMLElement {
    /**
     *  Enables/disables the style sheet.
     * <p>
     *  启用/禁用样式表。
     * 
     */
    public boolean getDisabled();
    public void setDisabled(boolean disabled);

    /**
     *  Designed for use with one or more target media. See the  media
     * attribute definition in HTML 4.0.
     * <p>
     *  设计用于一个或多个目标介质。请参阅HTML 4.0中的媒体属性定义。
     * 
     */
    public String getMedia();
    public void setMedia(String media);

    /**
     *  The content type pf the style sheet language. See the  type attribute
     * definition in HTML 4.0.
     * <p>
     *  内容类型pf是样式表语言。请参阅HTML 4.0中的类型属性定义。
     */
    public String getType();
    public void setType(String type);

}
