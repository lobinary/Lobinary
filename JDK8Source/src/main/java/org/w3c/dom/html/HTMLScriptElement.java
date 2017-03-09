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
 *  Script statements. See the  SCRIPT element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  脚本语句。请参阅HTML 4.0中的SCRIPT元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLScriptElement extends HTMLElement {
    /**
     *  The script content of the element.
     * <p>
     *  元素的脚本内容。
     * 
     */
    public String getText();
    public void setText(String text);

    /**
     *  Reserved for future use.
     * <p>
     *  保留供将来使用。
     * 
     */
    public String getHtmlFor();
    public void setHtmlFor(String htmlFor);

    /**
     *  Reserved for future use.
     * <p>
     *  保留供将来使用。
     * 
     */
    public String getEvent();
    public void setEvent(String event);

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
     *  Indicates that the user agent can defer processing of the script.  See
     * the  defer attribute definition in HTML 4.0.
     * <p>
     *  表示用户代理可以推迟脚本的处理。请参阅HTML 4.0中的defer属性定义。
     * 
     */
    public boolean getDefer();
    public void setDefer(boolean defer);

    /**
     *  URI designating an external script. See the  src attribute definition
     * in HTML 4.0.
     * <p>
     *  URI指定外部脚本。请参阅HTML 4.0中的src属性定义。
     * 
     */
    public String getSrc();
    public void setSrc(String src);

    /**
     *  The content type of the script language. See the  type attribute
     * definition in HTML 4.0.
     * <p>
     *  脚本语言的内容类型。请参阅HTML 4.0中的类型属性定义。
     */
    public String getType();
    public void setType(String type);

}
