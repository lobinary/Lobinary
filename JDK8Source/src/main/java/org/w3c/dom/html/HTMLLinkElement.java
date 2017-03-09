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
 *  The <code>LINK</code> element specifies a link to an external resource,
 * and defines this document's relationship to that resource (or vice versa).
 *  See the  LINK element definition in HTML 4.0  (see also the
 * <code>LinkStyle</code> interface in the  module).
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  <code> LINK </code>元素指定到外部资源的链接,并定义此文档与该资源的关系(反之亦然)。
 * 请参阅HTML 4.0中的LINK元素定义(另请参阅模块中的<code> LinkStyle </code>界面)。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLLinkElement extends HTMLElement {
    /**
     *  Enables/disables the link. This is currently only used for style sheet
     * links, and may be used to activate or deactivate style sheets.
     * <p>
     *  启用/禁用链接。它目前仅用于样式表链接,并且可用于激活或停用样式表。
     * 
     */
    public boolean getDisabled();
    public void setDisabled(boolean disabled);

    /**
     *  The character encoding of the resource being linked to. See the
     * charset attribute definition in HTML 4.0.
     * <p>
     *  链接到的资源的字符编码。请参阅HTML 4.0中的charset属性定义。
     * 
     */
    public String getCharset();
    public void setCharset(String charset);

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
     *  Designed for use with one or more target media. See the  media
     * attribute definition in HTML 4.0.
     * <p>
     *  设计用于一个或多个目标介质。请参阅HTML 4.0中的媒体属性定义。
     * 
     */
    public String getMedia();
    public void setMedia(String media);

    /**
     *  Forward link type. See the  rel attribute definition in HTML 4.0.
     * <p>
     * 前向链路类型。请参阅HTML 4.0中的rel属性定义。
     * 
     */
    public String getRel();
    public void setRel(String rel);

    /**
     *  Reverse link type. See the  rev attribute definition in HTML 4.0.
     * <p>
     *  反向链路类型。请参阅HTML 4.0中的rev属性定义。
     * 
     */
    public String getRev();
    public void setRev(String rev);

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
     */
    public String getType();
    public void setType(String type);

}
