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
 *  The <code>CSSRuleList</code> interface provides the abstraction of an
 * ordered collection of CSS rules.
 * <p> The items in the <code>CSSRuleList</code> are accessible via an
 * integral index, starting from 0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> CSSRuleList </code>接口提供了CSS规则的有序集合的抽象。 <p> <code> CSSRuleList </code>中的项目可以通过整数索引访问,从0开始。
 * <p>另请参阅<a href ='http：//www.w3.org/TR/2000 / REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范</a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface CSSRuleList {
    /**
     *  The number of <code>CSSRules</code> in the list. The range of valid
     * child rule indices is <code>0</code> to <code>length-1</code>
     * inclusive.
     * <p>
     *  列表中<code> CSSRules </code>的数量。有效子规则索引的范围是<code> 0 </code>到<code> length-1 </code>(包括)。
     * 
     */
    public int getLength();

    /**
     *  Used to retrieve a CSS rule by ordinal index. The order in this
     * collection represents the order of the rules in the CSS style sheet.
     * If index is greater than or equal to the number of rules in the list,
     * this returns <code>null</code>.
     * <p>
     *  用于通过序数索引检索CSS规则。此集合中的顺序表示CSS样式表中规则的顺序。如果index大于或等于列表中的规则数,则返回<code> null </code>。
     * 
     * @param index Index into the collection
     * @return The style rule at the <code>index</code> position in the
     *   <code>CSSRuleList</code>, or <code>null</code> if that is not a
     *   valid index.
     */
    public CSSRule item(int index);

}
