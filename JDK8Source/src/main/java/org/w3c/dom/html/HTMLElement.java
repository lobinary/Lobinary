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

import org.w3c.dom.Element;

/**
 *  All HTML element interfaces derive from this class. Elements that only
 * expose the HTML core attributes are represented by the base
 * <code>HTMLElement</code> interface. These elements are as follows:  HEAD
 * special: SUB, SUP, SPAN, BDO font: TT, I, B, U, S, STRIKE, BIG, SMALL
 * phrase: EM, STRONG, DFN, CODE, SAMP, KBD, VAR, CITE, ACRONYM, ABBR list:
 * DD, DT NOFRAMES, NOSCRIPT ADDRESS, CENTER The <code>style</code> attribute
 * of an HTML element is accessible through the
 * <code>ElementCSSInlineStyle</code> interface which is defined in the  .
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  所有HTML元素接口派生自此类。仅公开HTML核心属性的元素由基本<code> HTMLElement </code>接口表示。
 * 这些元素如下：HEAD special：SUB,SUP,SPAN,BDO font：TT,I,B,U,S,STRIKE,BIG,SMALL phrase：EM,STRONG,DFN,CODE,SAMP,
 * KBD,VAR,CITE ,ACRONYM,ABBR list：DD,DT NOFRAMES,NOSCRIPT ADDRESS,CENTER HTML元素的<code> style </code>属性可
 * 以通过<code> ElementCSSInlineStyle </code> <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>
 * 文档对象模型(DOM)2级规范</a>。
 *  所有HTML元素接口派生自此类。仅公开HTML核心属性的元素由基本<code> HTMLElement </code>接口表示。
 * 
 */
public interface HTMLElement extends Element {
    /**
     *  The element's identifier. See the  id attribute definition in HTML 4.0.
     * <p>
     *  元素的标识符。请参阅HTML 4.0中的id属性定义。
     * 
     */
    public String getId();
    public void setId(String id);

    /**
     *  The element's advisory title. See the  title attribute definition in
     * HTML 4.0.
     * <p>
     *  元素的咨询标题。请参阅HTML 4.0中的标题属性定义。
     * 
     */
    public String getTitle();
    public void setTitle(String title);

    /**
     *  Language code defined in RFC 1766. See the  lang attribute definition
     * in HTML 4.0.
     * <p>
     *  在RFC 1766中定义的语言代码。请参阅HTML 4.0中的lang属性定义。
     * 
     */
    public String getLang();
    public void setLang(String lang);

    /**
     *  Specifies the base direction of directionally neutral text and the
     * directionality of tables. See the  dir attribute definition in HTML
     * 4.0.
     * <p>
     * 指定方向中性文本的基本方向和表的方向性。请参阅HTML 4.0中的dir属性定义。
     * 
     */
    public String getDir();
    public void setDir(String dir);

    /**
     *  The class attribute of the element. This attribute has been renamed
     * due to conflicts with the "class" keyword exposed by many languages.
     * See the  class attribute definition in HTML 4.0.
     * <p>
     *  元素的类属性。此属性已重命名,因为与许多语言公开的"类"关键字冲突。请参阅HTML 4.0中的类属性定义。
     */
    public String getClassName();
    public void setClassName(String className);

}
