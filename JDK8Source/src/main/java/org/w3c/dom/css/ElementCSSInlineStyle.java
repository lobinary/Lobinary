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

package org.w3c.dom.css;

/**
 *  Inline style information attached to elements is exposed through the
 * <code>style</code> attribute. This represents the contents of the STYLE
 * attribute for HTML elements (or elements in other schemas or DTDs which
 * use the STYLE attribute in the same way). The expectation is that an
 * instance of the ElementCSSInlineStyle interface can be obtained by using
 * binding-specific casting methods on an instance of the Element interface
 * when the element supports inline CSS style informations.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  附加到元素的内联样式信息通过<code> style </code>属性公开。这表示HTML元素(或其他模式或DTD中以相同方式使用STYLE属性的元素)的STYLE属性的内容。
 * 期望的是,当元素支持内联CSS样式信息时,可以通过对Element接口的实例使用特定于绑定的转换方法来获取ElementCSSInlineStyle接口的实例。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface ElementCSSInlineStyle {
    /**
     *  The style attribute.
     * <p>
     */
    public CSSStyleDeclaration getStyle();

}
