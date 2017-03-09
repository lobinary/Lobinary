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
 *  This contains generic meta-information about the document. See the  META
 * element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  这包含有关文档的通用元信息。请参阅HTML 4.0中的META元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLMetaElement extends HTMLElement {
    /**
     *  Associated information. See the  content attribute definition in HTML
     * 4.0.
     * <p>
     *  相关信息。请参阅HTML 4.0中的内容属性定义。
     * 
     */
    public String getContent();
    public void setContent(String content);

    /**
     *  HTTP response header name. See the  http-equiv attribute definition in
     * HTML 4.0.
     * <p>
     *  HTTP响应头名称。请参阅HTML 4.0中的http-equiv属性定义。
     * 
     */
    public String getHttpEquiv();
    public void setHttpEquiv(String httpEquiv);

    /**
     *  Meta information name. See the  name attribute definition in HTML 4.0.
     * <p>
     *  元信息名称。请参阅HTML 4.0中的名称属性定义。
     * 
     */
    public String getName();
    public void setName(String name);

    /**
     *  Select form of content. See the  scheme attribute definition in HTML
     * 4.0.
     * <p>
     *  选择内容形式。请参阅HTML 4.0中的scheme属性定义。
     */
    public String getScheme();
    public void setScheme(String scheme);

}
