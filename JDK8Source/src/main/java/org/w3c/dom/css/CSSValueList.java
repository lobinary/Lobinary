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
 * The <code>CSSValueList</code> interface provides the abstraction of an
 * ordered collection of CSS values.
 * <p> Some properties allow an empty list into their syntax. In that case,
 * these properties take the <code>none</code> identifier. So, an empty list
 * means that the property has the value <code>none</code>.
 * <p> The items in the <code>CSSValueList</code> are accessible via an
 * integral index, starting from 0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> CSSValueList </code>接口提供了CSS值的有序集合的抽象。 <p>一些属性允许一个空列表的语法。
 * 在这种情况下,这些属性采用<code> none </code>标识符。因此,空列表意味着属性具有值<code> none </code>。
 *  <p> <code> CSSValueList </code>中的项目可以通过整数索引从0开始访问。
 * <p>另请参阅<a href ='http：//www.w3.org/TR/2000 / REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范</a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface CSSValueList extends CSSValue {
    /**
     * The number of <code>CSSValues</code> in the list. The range of valid
     * values of the indices is <code>0</code> to <code>length-1</code>
     * inclusive.
     * <p>
     *  列表中<code> CSSValues </code>的数量。索引的有效值的范围是<code> 0 </code>到<code> length-1 </code>(包括)。
     * 
     */
    public int getLength();

    /**
     * Used to retrieve a <code>CSSValue</code> by ordinal index. The order in
     * this collection represents the order of the values in the CSS style
     * property. If index is greater than or equal to the number of values
     * in the list, this returns <code>null</code>.
     * <p>
     * 用于通过序数索引检索<code> CSSValue </code>。此集合中的顺序表示CSS样式属性中值的顺序。如果index大于或等于列表中的值的数量,则返回<code> null </code>。
     * 
     * @param index Index into the collection.
     * @return The <code>CSSValue</code> at the <code>index</code> position
     *   in the <code>CSSValueList</code>, or <code>null</code> if that is
     *   not a valid index.
     */
    public CSSValue item(int index);

}
