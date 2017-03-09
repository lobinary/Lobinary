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
 *  The <code>Rect</code> interface is used to represent any rect value. This
 * interface reflects the values in the underlying style property. Hence,
 * modifications made to the <code>CSSPrimitiveValue</code> objects modify
 * the style property.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> Rect </code>接口用于表示任何rect值。此界面反映底层样式属性中的值。
 * 因此,对<code> CSSPrimitiveValue </code>对象所做的修改会修改style属性。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface Rect {
    /**
     *  This attribute is used for the top of the rect.
     * <p>
     *  此属性用于rect的顶部。
     * 
     */
    public CSSPrimitiveValue getTop();

    /**
     *  This attribute is used for the right of the rect.
     * <p>
     *  此属性用于rect的右侧。
     * 
     */
    public CSSPrimitiveValue getRight();

    /**
     *  This attribute is used for the bottom of the rect.
     * <p>
     *  此属性用于rect的底部。
     * 
     */
    public CSSPrimitiveValue getBottom();

    /**
     *  This attribute is used for the left of the rect.
     * <p>
     *  此属性用于rect的左侧。
     */
    public CSSPrimitiveValue getLeft();

}
