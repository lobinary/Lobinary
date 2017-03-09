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
 *  List item. See the  LI element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  列表项。请参阅HTML 4.0中的LI元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLLIElement extends HTMLElement {
    /**
     *  List item bullet style. See the  type attribute definition in HTML
     * 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     *  列表项目项目符号样式。请参阅HTML 4.0中的类型属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getType();
    public void setType(String type);

    /**
     *  Reset sequence number when used in <code>OL</code> . See the  value
     * attribute definition in HTML 4.0. This attribute is deprecated in HTML
     * 4.0.
     * <p>
     *  在<code> OL </code>中使用时,重置序列号。请参阅HTML 4.0中的值属性定义。此属性在HTML 4.0中已弃用。
     */
    public int getValue();
    public void setValue(int value);

}
