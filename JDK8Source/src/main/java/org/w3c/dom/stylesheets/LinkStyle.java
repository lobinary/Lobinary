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
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2000万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.stylesheets;

/**
 *  The <code>LinkStyle</code> interface provides a mechanism by which a style
 * sheet can be retrieved from the node responsible for linking it into a
 * document. An instance of the <code>LinkStyle</code> interface can be
 * obtained using binding-specific casting methods on an instance of a
 * linking node (<code>HTMLLinkElement</code>, <code>HTMLStyleElement</code>
 * or <code>ProcessingInstruction</code> in DOM Level 2).
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> LinkStyle </code>接口提供了一种机制,通过该机制可以从负责将样式表链接到文档的节点检索样式表。
 * 可以使用绑定特定的转换方法在链接节点的实例上获得<code> LinkStyle </code>接口的实例(<code> HTMLLinkElement </code>,<code> HTMLStyle
 * Element </code> > ProcessingInstruction </code>在DOM级别2)。
 *  <code> LinkStyle </code>接口提供了一种机制,通过该机制可以从负责将样式表链接到文档的节点检索样式表。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * @since DOM Level 2
 */
public interface LinkStyle {
    /**
     *  The style sheet.
     * <p>
     * 
     */
    public StyleSheet getSheet();

}
