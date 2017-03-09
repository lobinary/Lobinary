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

import org.w3c.dom.Element;
import org.w3c.dom.views.AbstractView;

/**
 *  This interface represents a CSS view. The <code>getComputedStyle</code>
 * method provides a read only access to the computed values of an element.
 * <p> The expectation is that an instance of the <code>ViewCSS</code>
 * interface can be obtained by using binding-specific casting methods on an
 * instance of the <code>AbstractView</code> interface.
 * <p> Since a computed style is related to an <code>Element</code> node, if
 * this element is removed from the document, the associated
 * <code>CSSStyleDeclaration</code> and <code>CSSValue</code> related to
 * this declaration are no longer valid.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  此接口代表一个CSS视图。 <code> getComputedStyle </code>方法提供对元素的计算值的只读访问。
 * 期望的是,可以通过对<code> AbstractView </code>接口的实例使用特定于绑定的转换方法来获得<code> ViewCSS </code>接口的实例。
 *  <p>由于计算样式与<code> Element </code>节点相关,如果此元素从文档中删除,则相关的<code> CSSStyleDeclaration </code>和<code> CSSVa
 * lue </code>对此声明不再有效。
 * 期望的是,可以通过对<code> AbstractView </code>接口的实例使用特定于绑定的转换方法来获得<code> ViewCSS </code>接口的实例。
 * 
 * @since DOM Level 2
 */
public interface ViewCSS extends AbstractView {
    /**
     *  This method is used to get the computed style as it is defined in [<a href='http://www.w3.org/TR/1998/REC-CSS2-19980512'>CSS2</a>].
     * <p>
     *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
     * 
     * 
     * @param elt  The element whose style is to be computed. This parameter
     *   cannot be null.
     * @param pseudoElt  The pseudo-element or <code>null</code> if none.
     * @return  The computed style. The <code>CSSStyleDeclaration</code> is
     *   read-only and contains only absolute values.
     */
    public CSSStyleDeclaration getComputedStyle(Element elt,
                                                String pseudoElt);

}
